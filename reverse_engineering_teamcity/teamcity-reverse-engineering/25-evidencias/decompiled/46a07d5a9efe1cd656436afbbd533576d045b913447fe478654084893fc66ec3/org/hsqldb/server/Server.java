/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.hsqldb.DatabaseManager
 *  org.hsqldb.DatabaseURL
 *  org.hsqldb.HsqlDateTime$SystemTimeString
 *  org.hsqldb.HsqlException
 *  org.hsqldb.error.Error
 *  org.hsqldb.lib.ArrayUtil
 *  org.hsqldb.lib.FileUtil
 *  org.hsqldb.lib.HashSet
 *  org.hsqldb.lib.IntKeyHashMap
 *  org.hsqldb.lib.Iterator
 *  org.hsqldb.lib.Notified
 *  org.hsqldb.lib.StopWatch
 *  org.hsqldb.lib.StringUtil
 *  org.hsqldb.persist.HsqlProperties
 *  org.hsqldb.resources.ResourceBundleHandler
 *  org.hsqldb.result.Result
 *  org.hsqldb.server.HsqlSocketFactory
 *  org.hsqldb.server.HsqlSocketRequestHandler
 *  org.hsqldb.server.Server$ServerThread
 *  org.hsqldb.server.ServerAcl
 *  org.hsqldb.server.ServerAcl$AclFormatException
 *  org.hsqldb.server.ServerConfiguration
 *  org.hsqldb.server.ServerConnection
 *  org.hsqldb.server.ServerProperties
 *  org.hsqldb.server.WebServer
 *  org.hsqldb.server.WebServerConnection
 */
package org.hsqldb.server;

import java.io.File;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.DriverManager;
import java.util.Enumeration;
import java.util.Properties;
import java.util.StringTokenizer;
import org.hsqldb.DatabaseManager;
import org.hsqldb.DatabaseURL;
import org.hsqldb.HsqlDateTime;
import org.hsqldb.HsqlException;
import org.hsqldb.error.Error;
import org.hsqldb.lib.ArrayUtil;
import org.hsqldb.lib.FileUtil;
import org.hsqldb.lib.HashSet;
import org.hsqldb.lib.IntKeyHashMap;
import org.hsqldb.lib.Iterator;
import org.hsqldb.lib.Notified;
import org.hsqldb.lib.StopWatch;
import org.hsqldb.lib.StringUtil;
import org.hsqldb.persist.HsqlProperties;
import org.hsqldb.resources.ResourceBundleHandler;
import org.hsqldb.result.Result;
import org.hsqldb.server.HsqlSocketFactory;
import org.hsqldb.server.HsqlSocketRequestHandler;
import org.hsqldb.server.Server;
import org.hsqldb.server.ServerAcl;
import org.hsqldb.server.ServerConfiguration;
import org.hsqldb.server.ServerConnection;
import org.hsqldb.server.ServerProperties;
import org.hsqldb.server.WebServer;
import org.hsqldb.server.WebServerConnection;

public class Server
implements HsqlSocketRequestHandler,
Notified {
    protected static final int serverBundleHandle = ResourceBundleHandler.getBundleHandle((String)"org_hsqldb_server_Server_messages", null);
    ServerProperties serverProperties;
    HashSet serverConnSet;
    final Object serverConnSetSync = new Object();
    protected String[] dbAlias;
    protected String[] dbType;
    protected String[] dbPath;
    protected HsqlProperties[] dbProps;
    protected int[] dbID;
    protected long[] dbActionSequence;
    HashSet aliasSet = new HashSet();
    protected int maxConnections;
    volatile long actionSequence;
    protected String serverId;
    protected int serverProtocol;
    protected ThreadGroup serverConnectionThreadGroup;
    protected HsqlSocketFactory socketFactory;
    protected volatile ServerSocket socket;
    private Thread serverThread;
    private Throwable serverError;
    private volatile int serverState;
    private volatile boolean isSilent;
    protected volatile boolean isRemoteOpen;
    protected boolean isDaemon;
    private PrintWriter logWriter;
    private PrintWriter errWriter;
    private ServerAcl acl = null;
    private volatile boolean isShuttingDown;
    private HsqlDateTime.SystemTimeString sysTime = new HsqlDateTime.SystemTimeString();

    public Thread getServerThread() {
        return this.serverThread;
    }

    public Server() {
        this(1);
    }

    protected Server(int n) {
        this.init(n);
    }

    public void checkRunning(boolean bl) {
        boolean bl2;
        this.printWithThread("checkRunning(" + bl + ") entered");
        int n = this.getState();
        boolean bl3 = bl2 = bl && n != 1 || !bl && n != 16;
        if (bl2) {
            String string = "server is " + (bl ? "not " : "") + "running";
            throw Error.error((int)458, (String)string);
        }
        this.printWithThread("checkRunning(" + bl + ") exited");
    }

    public boolean isNotRunning() {
        int n = this.getState();
        return n == 16;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public synchronized void signalCloseAllServerConnections() {
        Object[] objectArray;
        this.printWithThread("signalCloseAllServerConnections() entered");
        Object object = this.serverConnSetSync;
        synchronized (object) {
            objectArray = new ServerConnection[this.serverConnSet.size()];
            this.serverConnSet.toArray(objectArray);
        }
        for (int i = 0; i < objectArray.length; ++i) {
            Object object2 = objectArray[i];
            this.printWithThread("Closing " + (ServerConnection)object2);
            object2.signalClose();
        }
        this.printWithThread("signalCloseAllServerConnections() exited");
    }

    public String getAddress() {
        return this.socket == null ? this.serverProperties.getProperty("server.address") : this.socket.getInetAddress().getHostAddress();
    }

    public String getDatabaseName(int n, boolean bl) {
        if (bl) {
            return this.serverProperties.getProperty("server.dbname." + n);
        }
        if (this.getState() == 1) {
            return this.dbAlias == null || n < 0 || n >= this.dbAlias.length ? null : this.dbAlias[n];
        }
        return null;
    }

    public String getDatabasePath(int n, boolean bl) {
        if (bl) {
            return this.serverProperties.getProperty("server.database." + n);
        }
        if (this.getState() == 1) {
            return this.dbPath == null || n < 0 || n >= this.dbPath.length ? null : this.dbPath[n];
        }
        return null;
    }

    public String getDatabaseType(int n) {
        return this.dbType == null || n < 0 || n >= this.dbType.length ? null : this.dbType[n];
    }

    public String getDefaultWebPage() {
        return "[IGNORED]";
    }

    public String getHelpString() {
        return ResourceBundleHandler.getString((int)serverBundleHandle, (String)"server.help");
    }

    public PrintWriter getErrWriter() {
        return this.errWriter;
    }

    public PrintWriter getLogWriter() {
        return this.logWriter;
    }

    public int getPort() {
        return this.serverProperties.getIntegerProperty("server.port", ServerConfiguration.getDefaultPort((int)this.serverProtocol, (boolean)this.isTls()));
    }

    public int getLocalPort() {
        return this.socket == null ? -1 : this.socket.getLocalPort();
    }

    public String getProductName() {
        return "HSQLDB server";
    }

    public String getProductVersion() {
        return "2.7.2";
    }

    public String getProtocol() {
        return this.isTls() ? "HSQLS" : "HSQL";
    }

    public Throwable getServerError() {
        return this.serverError;
    }

    public String getServerId() {
        return this.serverId;
    }

    public int getState() {
        return this.serverState;
    }

    public String getStateDescriptor() {
        String string;
        switch (this.serverState) {
            case 16: {
                string = "SHUTDOWN";
                break;
            }
            case 4: {
                string = "OPENING";
                break;
            }
            case 8: {
                string = "CLOSING";
                break;
            }
            case 1: {
                string = "ONLINE";
                break;
            }
            default: {
                string = "UNKNOWN";
            }
        }
        return string;
    }

    public String getWebRoot() {
        return "[IGNORED]";
    }

    public void handleConnection(Socket socket) {
        String string;
        ServerConnection serverConnection;
        this.printWithThread("handleConnection(" + socket + ") entered");
        if (!this.allowConnection(socket)) {
            try {
                socket.close();
            }
            catch (Exception exception) {
                // empty catch block
            }
            this.printWithThread("allowConnection(): connection refused");
            this.printWithThread("handleConnection() exited");
            return;
        }
        if (this.socketFactory != null) {
            this.socketFactory.configureSocket(socket);
        }
        if (this.serverProtocol == 1) {
            serverConnection = new ServerConnection(socket, this);
            string = serverConnection.getConnectionThreadName();
        } else {
            serverConnection = new WebServerConnection(socket, (WebServer)this);
            string = ((WebServerConnection)serverConnection).getConnectionThreadName();
        }
        Thread thread = new Thread(this.serverConnectionThreadGroup, (Runnable)serverConnection, string);
        thread.start();
        this.printWithThread("handleConnection() exited");
    }

    public boolean isNoSystemExit() {
        return this.serverProperties.isPropertyTrue("server.no_system_exit");
    }

    public boolean isRestartOnShutdown() {
        return this.serverProperties.isPropertyTrue("server.restart_on_shutdown");
    }

    public boolean isSilent() {
        return this.isSilent;
    }

    public boolean isTls() {
        return this.serverProperties.isPropertyTrue("server.tls");
    }

    public boolean isTrace() {
        return this.serverProperties.isPropertyTrue("server.trace");
    }

    public boolean putPropertiesFromFile(String string) {
        return this.putPropertiesFromFile(string, ".properties");
    }

    public boolean putPropertiesFromFile(String string, String string2) {
        if (this.getState() != 16) {
            throw Error.error((int)458, (String)"server properties");
        }
        string = FileUtil.getFileUtil().canonicalOrAbsolutePath(string);
        ServerProperties serverProperties = ServerConfiguration.getPropertiesFromFile((int)1, (String)string, (String)string2);
        if (serverProperties == null || serverProperties.isEmpty()) {
            return false;
        }
        this.printWithThread("putPropertiesFromFile(): [" + string + ".properties]");
        try {
            this.setProperties((HsqlProperties)serverProperties);
        }
        catch (Exception exception) {
            throw Error.error((Throwable)exception, (int)458, (int)26, (String[])new String[]{"Failed to set properties"});
        }
        return true;
    }

    public void putPropertiesFromString(String string) {
        if (this.getState() != 16) {
            throw Error.error((int)458);
        }
        if (StringUtil.isEmpty((String)string)) {
            return;
        }
        this.printWithThread("putPropertiesFromString(): [" + string + "]");
        HsqlProperties hsqlProperties = HsqlProperties.delimitedArgPairsToProps((String)string, (String)"=", (String)";", (String)"server");
        try {
            this.setProperties(hsqlProperties);
        }
        catch (Exception exception) {
            throw Error.error((Throwable)exception, (int)458, (int)26, (String[])new String[]{"Failed to set properties"});
        }
    }

    public void setAddress(String string) {
        this.checkRunning(false);
        if (StringUtil.isEmpty((String)string)) {
            string = "0.0.0.0";
        }
        this.printWithThread("setAddress(" + string + ")");
        this.serverProperties.setProperty("server.address", string);
    }

    public void setDatabaseName(int n, String string) {
        this.checkRunning(false);
        this.printWithThread("setDatabaseName(" + n + "," + string + ")");
        this.serverProperties.setProperty("server.dbname." + n, string);
    }

    public void setDatabasePath(int n, String string) {
        this.checkRunning(false);
        this.printWithThread("setDatabasePath(" + n + "," + string + ")");
        this.serverProperties.setProperty("server.database." + n, string);
    }

    public void setDefaultWebPage(String string) {
        this.checkRunning(false);
        this.printWithThread("setDefaultWebPage(" + string + ")");
        if (this.serverProtocol != 0) {
            return;
        }
        this.serverProperties.setProperty("server.default_page", string);
    }

    public void setPort(int n) {
        this.checkRunning(false);
        this.printWithThread("setPort(" + n + ")");
        this.serverProperties.setProperty("server.port", n);
    }

    public void setErrWriter(PrintWriter printWriter) {
        this.errWriter = printWriter;
    }

    public void setLogWriter(PrintWriter printWriter) {
        this.logWriter = printWriter;
    }

    public void setNoSystemExit(boolean bl) {
        this.printWithThread("setNoSystemExit(" + bl + ")");
        this.serverProperties.setProperty("server.no_system_exit", bl);
    }

    public void setRestartOnShutdown(boolean bl) {
        this.printWithThread("setRestartOnShutdown(" + bl + ")");
        this.serverProperties.setProperty("server.restart_on_shutdown", bl);
    }

    public void setSilent(boolean bl) {
        this.serverProperties.setProperty("server.silent", bl);
        this.isSilent = bl;
        this.printWithThread("setSilent(" + bl + ")");
    }

    public void setTls(boolean bl) {
        this.checkRunning(false);
        this.printWithThread("setTls(" + bl + ")");
        this.serverProperties.setProperty("server.tls", bl);
    }

    public void setTrace(boolean bl) {
        this.printWithThread("setTrace(" + bl + ")");
        this.serverProperties.setProperty("server.trace", bl);
        Server.setLogToSystem(bl);
    }

    public void setDaemon(boolean bl) {
        this.checkRunning(false);
        this.printWithThread("setDaemon(" + bl + ")");
        this.serverProperties.setProperty("server.daemon", bl);
    }

    public void setWebRoot(String string) {
        this.checkRunning(false);
        string = new File(string).getAbsolutePath();
        this.printWithThread("setWebRoot(" + string + ")");
        if (this.serverProtocol != 0) {
            return;
        }
        this.serverProperties.setProperty("server.root", string);
    }

    public void setProperties(Properties properties) throws IOException, ServerAcl.AclFormatException {
        HsqlProperties hsqlProperties = new HsqlProperties(properties);
        this.setProperties(hsqlProperties);
    }

    public void setProperties(HsqlProperties hsqlProperties) throws IOException, ServerAcl.AclFormatException {
        Object object;
        if (!this.isNotRunning()) {
            this.checkRunning(false);
        }
        if (hsqlProperties != null) {
            hsqlProperties.validate();
            object = hsqlProperties.getErrorKeys();
            if (((String[])object).length > 0) {
                throw Error.error((int)407, (String)object[0]);
            }
            this.serverProperties.addProperties(hsqlProperties);
        }
        this.maxConnections = this.serverProperties.getIntegerProperty("server.maxconnections", 16);
        Server.setLogToSystem(this.isTrace());
        this.isSilent = this.serverProperties.isPropertyTrue("server.silent");
        this.isRemoteOpen = this.serverProperties.isPropertyTrue("server.remote_open");
        this.isDaemon = this.serverProperties.isPropertyTrue("server.daemon");
        object = this.serverProperties.getProperty("server.acl");
        if (object != null) {
            this.acl = new ServerAcl(new File((String)object));
            if (this.logWriter != null && !this.isSilent) {
                this.acl.setPrintWriter(this.logWriter);
            }
        }
    }

    public int start() {
        this.printWithThread("start() entered");
        int n = this.getState();
        if (this.serverThread != null) {
            this.printWithThread("start(): serverThread != null; no action taken");
            return n;
        }
        this.setState(4);
        this.serverThread = new ServerThread(this, "HSQLDB Server ");
        if (this.isDaemon) {
            this.serverThread.setDaemon(true);
        }
        this.serverThread.start();
        while (this.getState() == 4) {
            try {
                Thread.sleep(100L);
            }
            catch (InterruptedException interruptedException) {}
        }
        this.printWithThread("start() exiting");
        return n;
    }

    public int stop() {
        this.printWithThread("stop() entered");
        int n = this.getState();
        if (this.serverThread == null) {
            this.printWithThread("stop() serverThread is null; no action taken");
            return n;
        }
        this.releaseServerSocket();
        this.printWithThread("stop() exiting");
        return n;
    }

    protected boolean allowConnection(Socket socket) {
        if (this.isShuttingDown) {
            return false;
        }
        return this.acl == null ? true : this.acl.permitAccess(socket.getInetAddress().getAddress());
    }

    protected void init(int n) {
        this.serverState = 16;
        this.serverConnSet = new HashSet();
        this.serverId = this.toString();
        this.serverId = this.serverId.substring(this.serverId.lastIndexOf(46) + 1);
        this.serverProtocol = n;
        this.serverProperties = ServerConfiguration.newDefaultProperties((int)n);
        this.logWriter = new PrintWriter(System.out);
        this.errWriter = new PrintWriter(System.err);
        Server.setLogToSystem(this.isTrace());
    }

    protected synchronized void setState(int n) {
        this.serverState = n;
    }

    public final void notify(int n) {
        this.printWithThread("notify( database shutdown," + n + ") entered");
        this.releaseDatabase(n);
        boolean bl = true;
        for (int i = 0; i < this.dbID.length; ++i) {
            if (this.dbAlias[i] == null) continue;
            bl = false;
            break;
        }
        if (!this.isRemoteOpen && bl) {
            this.stop();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    final synchronized void releaseDatabase(int n) {
        Object[] objectArray;
        this.printWithThread("releaseDatabase(" + n + ") entered");
        for (int i = 0; i < this.dbID.length; ++i) {
            if (this.dbID[i] != n || this.dbAlias[i] == null) continue;
            this.dbID[i] = 0;
            this.dbActionSequence[i] = 0L;
            this.dbAlias[i] = null;
            this.dbPath[i] = null;
            this.dbType[i] = null;
            this.dbProps[i] = null;
        }
        Object object = this.serverConnSetSync;
        synchronized (object) {
            objectArray = new ServerConnection[this.serverConnSet.size()];
            this.serverConnSet.toArray(objectArray);
        }
        for (int i = 0; i < objectArray.length; ++i) {
            Object object2 = objectArray[i];
            if (((ServerConnection)object2).dbID != n) continue;
            object2.signalClose();
        }
        this.printWithThread("releaseDatabase(" + n + ") exiting");
    }

    protected void print(String string) {
        PrintWriter printWriter = this.logWriter;
        if (printWriter != null) {
            printWriter.println("[" + this.serverId + "]: " + string);
            printWriter.flush();
        }
    }

    final void printResource(String string) {
        if (serverBundleHandle < 0) {
            return;
        }
        String string2 = ResourceBundleHandler.getString((int)serverBundleHandle, (String)string);
        if (string2 == null) {
            return;
        }
        StringTokenizer stringTokenizer = new StringTokenizer(string2, "\n\r");
        while (stringTokenizer.hasMoreTokens()) {
            this.print(stringTokenizer.nextToken());
        }
    }

    protected void printStackTrace(Throwable throwable) {
        if (this.errWriter != null) {
            throwable.printStackTrace(this.errWriter);
            this.errWriter.flush();
        }
    }

    final void printWithTimestamp(String string) {
        this.print(this.sysTime.getTimestampString() + " " + string);
    }

    protected void printWithThread(String string) {
        if (!this.isSilent()) {
            this.print("[" + Thread.currentThread() + "]: " + string);
        }
    }

    protected void printError(String string) {
        PrintWriter printWriter = this.errWriter;
        if (printWriter != null) {
            printWriter.print("[" + this.serverId + "]: ");
            printWriter.print("[" + Thread.currentThread() + "]: ");
            printWriter.println(string);
            printWriter.flush();
        }
    }

    final void printRequest(long l, Result result) {
        if (this.isSilent()) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(l);
        stringBuilder.append(':');
        block0 : switch (result.getType()) {
            case 37: {
                stringBuilder.append("SQLCLI:SQLPREPARE ");
                stringBuilder.append(result.getMainString());
                break;
            }
            case 34: {
                stringBuilder.append(result.getMainString());
                break;
            }
            case 21: 
            case 35: {
                stringBuilder.append("SQLCLI:SQLEXECUTE:");
                stringBuilder.append(result.getStatementID());
                break;
            }
            case 9: {
                stringBuilder.append("SQLCLI:SQLEXECUTE:");
                stringBuilder.append("BATCHMODE:");
                stringBuilder.append(result.getStatementID());
                break;
            }
            case 41: {
                stringBuilder.append("SQLCLI:RESULTUPDATE:");
                stringBuilder.append(result.getStatementID());
                break;
            }
            case 36: {
                stringBuilder.append("SQLCLI:SQLFREESTMT:");
                stringBuilder.append(result.getStatementID());
                break;
            }
            case 7: {
                stringBuilder.append("HSQLCLI:GETSESSIONATTR");
                break;
            }
            case 6: {
                stringBuilder.append("HSQLCLI:SETSESSIONATTR:");
                break;
            }
            case 33: {
                stringBuilder.append("SQLCLI:SQLENDTRAN:");
                switch (result.getActionType()) {
                    case 0: {
                        stringBuilder.append("COMMIT");
                        break block0;
                    }
                    case 1: {
                        stringBuilder.append("ROLLBACK");
                        break block0;
                    }
                    case 4: {
                        stringBuilder.append("SAVEPOINT_NAME_RELEASE ");
                        stringBuilder.append(result.getMainString());
                        break block0;
                    }
                    case 2: {
                        stringBuilder.append("SAVEPOINT_NAME_ROLLBACK ");
                        stringBuilder.append(result.getMainString());
                        break block0;
                    }
                }
                stringBuilder.append(result.getActionType());
                break;
            }
            case 39: {
                stringBuilder.append("SQLCLI:SQLSTARTTRAN");
                break;
            }
            case 32: {
                stringBuilder.append("SQLCLI:SQLDISCONNECT");
                break;
            }
            case 38: {
                stringBuilder.append("SQLCLI:SQLSETCONNECTATTR:");
                switch (result.getConnectionAttrType()) {
                    case 10027: {
                        stringBuilder.append("SQL_ATTR_SAVEPOINT_NAME ");
                        stringBuilder.append(result.getMainString());
                        break block0;
                    }
                }
                stringBuilder.append(result.getConnectionAttrType());
                break;
            }
            case 40: {
                stringBuilder.append("HQLCLI:CLOSE_RESULT:RESULT_ID ");
                stringBuilder.append(result.getResultId());
                break;
            }
            case 13: {
                stringBuilder.append("HQLCLI:REQUESTDATA:RESULT_ID ");
                stringBuilder.append(result.getResultId());
                stringBuilder.append(" ROWOFFSET ");
                stringBuilder.append(result.getUpdateCount());
                stringBuilder.append(" ROWCOUNT ");
                stringBuilder.append(result.getFetchSize());
                break;
            }
            default: {
                stringBuilder.append("SQLCLI:MODE:");
                stringBuilder.append(result.getType());
            }
        }
        this.print(stringBuilder.toString());
    }

    final synchronized int getDBIndex(String string) {
        int n;
        int n2 = string.indexOf(59);
        String string2 = string;
        String string3 = null;
        if (n2 != -1) {
            string2 = string.substring(0, n2);
            string3 = string.substring(n2 + 1);
        }
        if ((n = ArrayUtil.find((Object[])this.dbAlias, (Object)string2)) == -1) {
            if (string3 == null) {
                HsqlException hsqlException = Error.error((int)458, (String)"database alias does not exist");
                this.printError("database alias=" + string2 + " does not exist");
                this.setServerError(hsqlException);
                throw hsqlException;
            }
            return this.openDatabase(string2, string3);
        }
        return n;
    }

    final int openDatabase(String string, String string2) {
        if (!this.isRemoteOpen) {
            HsqlException hsqlException = Error.error((int)458, (String)"remote open not allowed");
            this.printError("Remote database open not allowed");
            this.setServerError(hsqlException);
            throw hsqlException;
        }
        int n = this.getFirstEmptyDatabaseIndex();
        if (n < -1 && (n = this.closeOldestDatabase()) < -1) {
            HsqlException hsqlException = Error.error((int)458, (String)"limit of open databases reached");
            this.printError("limit of open databases reached");
            this.setServerError(hsqlException);
            throw hsqlException;
        }
        HsqlProperties hsqlProperties = DatabaseURL.parseURL((String)string2, (boolean)false, (boolean)false);
        if (hsqlProperties == null) {
            HsqlException hsqlException = Error.error((int)458, (String)"invalid database path");
            this.printError("invalid database path");
            this.setServerError(hsqlException);
            throw hsqlException;
        }
        String string3 = hsqlProperties.getProperty("database");
        String string4 = hsqlProperties.getProperty("connection_type");
        try {
            int n2;
            this.dbID[n] = n2 = DatabaseManager.getDatabase((String)string4, (String)string3, (Notified)this, (HsqlProperties)hsqlProperties);
            this.dbActionSequence[n] = this.actionSequence;
            this.dbAlias[n] = string;
            this.dbPath[n] = string3;
            this.dbType[n] = string4;
            this.dbProps[n] = hsqlProperties;
            return n;
        }
        catch (HsqlException hsqlException) {
            this.printError("Database [index=" + n + ", db=" + this.dbType[n] + this.dbPath[n] + ", alias=" + this.dbAlias[n] + "] did not open: " + hsqlException.toString());
            this.setServerError(hsqlException);
            throw hsqlException;
        }
    }

    final int getFirstEmptyDatabaseIndex() {
        for (int i = 0; i < this.dbAlias.length; ++i) {
            if (this.dbAlias[i] != null) continue;
            return i;
        }
        return -1;
    }

    final boolean openDatabases() {
        this.printWithThread("openDatabases() entered");
        boolean bl = false;
        this.setDBInfoArrays();
        for (int i = 0; i < this.dbAlias.length; ++i) {
            int n;
            if (this.dbAlias[i] == null) continue;
            this.printWithThread("Opening database: [" + this.dbType[i] + this.dbPath[i] + "]");
            StopWatch stopWatch = new StopWatch();
            try {
                this.dbID[i] = n = DatabaseManager.getDatabase((String)this.dbType[i], (String)this.dbPath[i], (Notified)this, (HsqlProperties)this.dbProps[i]);
                bl = true;
            }
            catch (HsqlException hsqlException) {
                this.printError("Database [index=" + i + ", db=" + this.dbType[i] + this.dbPath[i] + ", alias=" + this.dbAlias[i] + "] did not open: " + hsqlException.toString());
                this.setServerError(hsqlException);
                this.dbAlias[i] = null;
                this.dbPath[i] = null;
                this.dbType[i] = null;
                this.dbProps[i] = null;
                continue;
            }
            stopWatch.stop();
            String string = "Database [index=" + i + ", id=" + n + ", db=" + this.dbType[i] + this.dbPath[i] + ", alias=" + this.dbAlias[i] + "] opened successfully";
            this.print(stopWatch.elapsedTimeToMessage(string));
        }
        this.printWithThread("openDatabases() exiting");
        if (this.isRemoteOpen) {
            bl = true;
        }
        if (!bl && this.getServerError() == null) {
            this.setServerError(Error.error((int)407));
        }
        return bl;
    }

    private void setDBInfoArrays() {
        int n;
        IntKeyHashMap intKeyHashMap = this.getDBNameArray();
        int n2 = intKeyHashMap.size();
        if (this.serverProperties.isPropertyTrue("server.remote_open") && n2 < (n = this.serverProperties.getIntegerProperty("server.maxdatabases", 10))) {
            n2 = n;
        }
        this.dbAlias = new String[n2];
        this.dbPath = new String[this.dbAlias.length];
        this.dbType = new String[this.dbAlias.length];
        this.dbID = new int[this.dbAlias.length];
        this.dbActionSequence = new long[this.dbAlias.length];
        this.dbProps = new HsqlProperties[this.dbAlias.length];
        Iterator iterator = intKeyHashMap.keySet().iterator();
        int n3 = 0;
        while (iterator.hasNext()) {
            int n4 = iterator.nextInt();
            String string = this.getDatabasePath(n4, true);
            if (string == null) {
                this.printWithThread("missing database path: " + intKeyHashMap.get(n4));
                continue;
            }
            HsqlProperties hsqlProperties = DatabaseURL.parseURL((String)string, (boolean)false, (boolean)false);
            if (hsqlProperties == null) {
                this.printWithThread("malformed database path: " + string);
                continue;
            }
            this.dbAlias[n3] = (String)intKeyHashMap.get(n4);
            this.dbPath[n3] = hsqlProperties.getProperty("database");
            this.dbType[n3] = hsqlProperties.getProperty("connection_type");
            this.dbProps[n3] = hsqlProperties;
            ++n3;
        }
    }

    private IntKeyHashMap getDBNameArray() {
        int n = "server.dbname.".length();
        IntKeyHashMap intKeyHashMap = new IntKeyHashMap();
        Enumeration enumeration = this.serverProperties.propertyNames();
        while (enumeration.hasMoreElements()) {
            Object object;
            int n2;
            String string = (String)enumeration.nextElement();
            if (!string.startsWith("server.dbname.")) continue;
            try {
                n2 = Integer.parseInt(string.substring(n));
            }
            catch (NumberFormatException numberFormatException) {
                this.printWithThread("malformed database enumerator: " + string);
                continue;
            }
            String string2 = this.serverProperties.getProperty(string).toLowerCase();
            if (!this.aliasSet.add((Object)string2)) {
                this.printWithThread("duplicate alias: " + string2);
            }
            if ((object = intKeyHashMap.put(n2, (Object)string2)) == null) continue;
            this.printWithThread("duplicate database enumerator: " + string);
        }
        return intKeyHashMap;
    }

    private void openServerSocket() throws Exception {
        this.printWithThread("openServerSocket() entered");
        if (this.isTls()) {
            this.printWithThread("Requesting TLS/SSL-encrypted JDBC");
        }
        StopWatch stopWatch = new StopWatch();
        this.socketFactory = HsqlSocketFactory.getInstance((boolean)this.isTls());
        String string = this.getAddress();
        int n = this.getPort();
        if (StringUtil.isEmpty((String)string) || "0.0.0.0".equalsIgnoreCase(string.trim())) {
            this.socket = this.socketFactory.createServerSocket(n);
        } else {
            try {
                this.socket = this.socketFactory.createServerSocket(n, string);
            }
            catch (UnknownHostException unknownHostException) {
                String[] stringArray;
                int n2;
                String[] stringArray2 = ServerConfiguration.listLocalInetAddressNames();
                if (stringArray2.length > 0) {
                    n2 = 61;
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 0; i < stringArray2.length; ++i) {
                        if (stringBuilder.length() > 0) {
                            stringBuilder.append(", ");
                        }
                        stringBuilder.append(stringArray2[i]);
                    }
                    stringArray = new String[]{string, stringBuilder.toString()};
                } else {
                    n2 = 62;
                    stringArray = new String[]{string};
                }
                throw new UnknownHostException(Error.getMessage((int)n2, (int)0, (String[])stringArray));
            }
        }
        this.socket.setSoTimeout(1000);
        this.printWithThread("Got server socket: " + this.socket);
        this.print(stopWatch.elapsedTimeToMessage("Server socket opened successfully"));
        if (this.socketFactory.isSecure()) {
            this.print("Using TLS/SSL-encrypted JDBC");
        }
        this.printWithThread("openServerSocket() exiting");
    }

    private void printServerOnlineMessage() {
        String string = this.getProductName() + " " + this.getProductVersion() + " is online on port " + this.getPort();
        this.printWithTimestamp(string);
        this.printResource("online.help");
    }

    protected void printProperties() {
        if (this.isSilent()) {
            return;
        }
        Enumeration enumeration = this.serverProperties.propertyNames();
        while (enumeration.hasMoreElements()) {
            String string = (String)enumeration.nextElement();
            String string2 = this.serverProperties.getProperty(string);
            this.printWithThread(string + "=" + string2);
        }
    }

    private synchronized void releaseServerSocket() {
        this.printWithThread("releaseServerSocket() entered");
        if (this.socket != null) {
            this.printWithThread("Releasing server socket: [" + this.socket + "]");
            this.setState(8);
            try {
                this.socket.close();
            }
            catch (IOException iOException) {
                this.printError("Exception closing server socket");
                this.printError("releaseServerSocket(): " + iOException);
            }
            this.socket = null;
        }
        this.printWithThread("releaseServerSocket() exited");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void run() {
        this.printWithThread("run() entered");
        this.print("Initiating startup sequence...");
        this.printProperties();
        StopWatch stopWatch = new StopWatch();
        this.setServerError(null);
        try {
            this.openServerSocket();
        }
        catch (Exception exception) {
            this.setServerError(exception);
            this.printError("run()/openServerSocket(): ");
            this.printStackTrace(exception);
            this.shutdown(true);
            return;
        }
        String string = "HSQLDB Connections @" + Integer.toString(this.hashCode(), 16);
        this.serverConnectionThreadGroup = new ThreadGroup(string);
        if (!this.openDatabases()) {
            this.setServerError(null);
            this.printError("Shutting down because there are no open databases");
            this.shutdown(true);
            return;
        }
        this.setState(1);
        this.print(stopWatch.elapsedTimeToMessage("Startup sequence completed"));
        this.printServerOnlineMessage();
        this.isShuttingDown = false;
        try {
            while (this.socket != null) {
                try {
                    this.handleConnection(this.socket.accept());
                }
                catch (InterruptedIOException interruptedIOException) {}
            }
        }
        catch (IOException iOException) {
            if (this.getState() == 1) {
                this.setServerError(iOException);
                this.printError(this + ".run()/handleConnection(): ");
                this.printStackTrace(iOException);
            }
        }
        catch (Throwable throwable) {
            this.printWithThread(throwable.toString());
        }
        finally {
            this.shutdown(false);
        }
    }

    protected void setServerError(Throwable throwable) {
        this.serverError = throwable;
    }

    public void shutdownCatalogs(int n) {
        if (n < 1 || n > 4) {
            throw new IllegalArgumentException("must be between 1 and 4");
        }
        DatabaseManager.shutdownDatabases((Notified)this, (int)n);
    }

    public void shutdownWithCatalogs(int n) {
        if (n < 1 || n > 4) {
            throw new IllegalArgumentException("must be between 1 and 4");
        }
        this.isShuttingDown = true;
        DatabaseManager.shutdownDatabases((Notified)this, (int)n);
        this.shutdown(false);
        this.isShuttingDown = false;
    }

    public void shutdown() {
        this.shutdown(false);
    }

    protected synchronized void shutdown(boolean bl) {
        int n;
        if (this.serverState == 16) {
            return;
        }
        this.printWithThread("shutdown() entered");
        this.print("Initiating shutdown sequence...");
        StopWatch stopWatch = new StopWatch();
        this.releaseServerSocket();
        DatabaseManager.deRegisterServer((Notified)this);
        if (this.dbPath != null) {
            for (n = 0; n < this.dbPath.length; ++n) {
                this.releaseDatabase(this.dbID[n]);
            }
        }
        if (this.serverConnectionThreadGroup != null) {
            for (n = this.serverConnectionThreadGroup.activeCount(); n > 0 && this.serverConnectionThreadGroup.activeCount() > 0; --n) {
                try {
                    Thread.sleep(100L);
                    continue;
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
            this.serverConnectionThreadGroup = null;
        }
        this.serverThread = null;
        this.setState(16);
        this.print(stopWatch.elapsedTimeToMessage("Shutdown sequence completed"));
        if (this.isNoSystemExit()) {
            this.printWithTimestamp("SHUTDOWN : System.exit() was not called");
            this.printWithThread("shutdown() exited");
        } else {
            this.printWithTimestamp("SHUTDOWN : System.exit() is called next");
            this.printWithThread("shutdown() exiting...");
            try {
                System.exit(0);
            }
            catch (Throwable throwable) {
                this.printWithThread(throwable.toString());
            }
        }
    }

    synchronized void setActionSequence(int n) {
        ++this.actionSequence;
    }

    protected int closeOldestDatabase() {
        return -1;
    }

    protected static void printHelp(String string) {
        System.out.println(ResourceBundleHandler.getString((int)serverBundleHandle, (String)string));
    }

    public static void main(String[] stringArray) {
        HsqlProperties hsqlProperties = HsqlProperties.argArrayToProps((String[])stringArray, (String)"server");
        String[] stringArray2 = hsqlProperties.getErrorKeys();
        if (stringArray2.length != 0) {
            System.out.println("no value for argument:" + stringArray2[0]);
            Server.printHelp("server.help");
            return;
        }
        String string = hsqlProperties.getProperty("server.props");
        String string2 = "";
        if (string == null) {
            string = "server";
            string2 = ".properties";
        } else {
            hsqlProperties.removeProperty("server.props");
        }
        string = FileUtil.getFileUtil().canonicalOrAbsolutePath(string);
        ServerProperties serverProperties = ServerConfiguration.getPropertiesFromFile((int)1, (String)string, (String)string2);
        ServerProperties serverProperties2 = serverProperties == null ? new ServerProperties(1) : serverProperties;
        serverProperties2.addProperties(hsqlProperties);
        ServerConfiguration.translateDefaultDatabaseProperty((HsqlProperties)serverProperties2);
        ServerConfiguration.translateDefaultNoSystemExitProperty((HsqlProperties)serverProperties2);
        ServerConfiguration.translateAddressProperty((HsqlProperties)serverProperties2);
        Server server = new Server();
        try {
            server.setProperties((HsqlProperties)serverProperties2);
        }
        catch (Exception exception) {
            server.printError("Failed to set properties");
            server.printStackTrace(exception);
            return;
        }
        server.print("Startup sequence initiated from main() method");
        if (serverProperties != null) {
            server.print("Loaded properties from [" + string + string2 + "]");
        } else {
            server.print("Could not load properties from file");
            server.print("Using cli/default properties only");
        }
        server.start();
    }

    private static void setLogToSystem(boolean bl) {
        try {
            PrintWriter printWriter = bl ? new PrintWriter(System.out) : null;
            DriverManager.setLogWriter(printWriter);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}
