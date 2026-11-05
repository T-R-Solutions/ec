/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.google.gson.Gson
 *  com.intellij.openapi.diagnostic.Logger
 *  freemarker.cache.ClassTemplateLoader
 *  freemarker.cache.TemplateLoader
 *  freemarker.template.Configuration
 *  freemarker.template.DefaultObjectWrapper
 *  freemarker.template.ObjectWrapper
 *  freemarker.template.Template
 *  javax.servlet.ServletConfig
 *  javax.servlet.ServletException
 *  javax.servlet.annotation.MultipartConfig
 *  javax.servlet.http.Cookie
 *  javax.servlet.http.HttpServlet
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  javax.servlet.http.HttpSession
 *  jetbrains.buildServer.Used
 *  jetbrains.buildServer.controllers.FormUtil
 *  jetbrains.buildServer.log.LogInitializationException
 *  jetbrains.buildServer.log.LogInitializer
 *  jetbrains.buildServer.maintenance.DebugBean
 *  jetbrains.buildServer.maintenance.StartupContext
 *  jetbrains.buildServer.maintenance.StartupContextHolder
 *  jetbrains.buildServer.maintenance.StartupHelper
 *  jetbrains.buildServer.maintenance.StartupPageModel
 *  jetbrains.buildServer.maintenance.StartupPageSpecificInfo
 *  jetbrains.buildServer.maintenance.StartupServlet$AjaxBean
 *  jetbrains.buildServer.maintenance.StartupStage
 *  jetbrains.buildServer.maintenance.StartupUserSession
 *  jetbrains.buildServer.maintenance.exceptions.KnownStartupError
 *  jetbrains.buildServer.serverSide.RelativeWebLinks
 *  jetbrains.buildServer.serverSide.TeamCityProperties
 *  jetbrains.buildServer.serverSide.db.DatabaseType
 *  jetbrains.buildServer.serverSide.db.JdbcConnectionString
 *  jetbrains.buildServer.serverSide.db.MajorMinorVersion
 *  jetbrains.buildServer.serverSide.db.TeamCityDatabaseManager
 *  jetbrains.buildServer.serverSide.db.jdbcLoader.JdbcDrivers
 *  jetbrains.buildServer.serverSide.db.jdbcLoader.NoSuitableJdbcDrivers
 *  jetbrains.buildServer.serverSide.maintenance.ProgressInfo
 *  jetbrains.buildServer.util.StringUtil
 *  jetbrains.buildServer.web.CSRFFilter
 *  jetbrains.buildServer.web.CsrfTokenCheck
 *  jetbrains.buildServer.web.util.WebUtil
 *  jetbrains.buildServer.xstream.JdbcDriver
 *  org.apache.commons.beanutils.PropertyUtilsBean
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package jetbrains.buildServer.maintenance;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.intellij.openapi.diagnostic.Logger;
import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.ObjectWrapper;
import freemarker.template.Template;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Driver;
import java.util.Collections;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jetbrains.buildServer.Used;
import jetbrains.buildServer.controllers.FormUtil;
import jetbrains.buildServer.log.LogInitializationException;
import jetbrains.buildServer.log.LogInitializer;
import jetbrains.buildServer.maintenance.DebugBean;
import jetbrains.buildServer.maintenance.StartupContext;
import jetbrains.buildServer.maintenance.StartupContextHolder;
import jetbrains.buildServer.maintenance.StartupHelper;
import jetbrains.buildServer.maintenance.StartupPageModel;
import jetbrains.buildServer.maintenance.StartupPageSpecificInfo;
import jetbrains.buildServer.maintenance.StartupServlet;
import jetbrains.buildServer.maintenance.StartupStage;
import jetbrains.buildServer.maintenance.StartupUserSession;
import jetbrains.buildServer.maintenance.exceptions.KnownStartupError;
import jetbrains.buildServer.serverSide.RelativeWebLinks;
import jetbrains.buildServer.serverSide.TeamCityProperties;
import jetbrains.buildServer.serverSide.db.DatabaseType;
import jetbrains.buildServer.serverSide.db.JdbcConnectionString;
import jetbrains.buildServer.serverSide.db.MajorMinorVersion;
import jetbrains.buildServer.serverSide.db.TeamCityDatabaseManager;
import jetbrains.buildServer.serverSide.db.jdbcLoader.JdbcDrivers;
import jetbrains.buildServer.serverSide.db.jdbcLoader.NoSuitableJdbcDrivers;
import jetbrains.buildServer.serverSide.maintenance.ProgressInfo;
import jetbrains.buildServer.util.StringUtil;
import jetbrains.buildServer.web.CSRFFilter;
import jetbrains.buildServer.web.CsrfTokenCheck;
import jetbrains.buildServer.web.util.WebUtil;
import jetbrains.buildServer.xstream.JdbcDriver;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Exception performing whole class analysis ignored.
 */
@MultipartConfig
public class StartupServlet
extends HttpServlet {
    public static final String MAINTENANCE_TOKEN_COOKIE = "maintenanceToken";
    static final String USER_SESSION_ATTRIBUTE_NAME = "userSession";
    public static final String USER_SESSION_ORIGINAL_INACTIVITY_INTERVAL_ATTR_NAME = "teamcity.session.originalInactivityInterval";
    static final PropertyUtilsBean ourPropertyUtilsBean;
    private static final String DB_SETTINGS_PAGE_USER_INPUT_VAR = "userDBsettingsInput";
    private final Configuration myFmConfig;
    @NotNull
    private final Logger myLogger;
    @NotNull
    private final StartupContext myContext;
    static final /* synthetic */ boolean $assertionsDisabled;

    @Used(value="web.xml")
    public StartupServlet() {
        this(StartupContextHolder.ourLogger, StartupContextHolder.ourContext);
    }

    StartupServlet(@NotNull Logger logger, @NotNull StartupContext startupContext) {
        if (logger == null) {
            StartupServlet.$$$reportNull$$$0(0);
        }
        if (startupContext == null) {
            StartupServlet.$$$reportNull$$$0(1);
        }
        this.myFmConfig = new Configuration();
        this.myLogger = logger;
        this.myContext = startupContext;
    }

    public void init(ServletConfig servletConfig) throws ServletException {
        StartupServlet.setupFreeMarkerConfig(this.myFmConfig);
    }

    public void destroy() {
        super.destroy();
    }

    static void setupFreeMarkerConfig(Configuration configuration) {
        configuration.setTemplateLoader((TemplateLoader)new ClassTemplateLoader(StartupServlet.class, "/maintenance"));
        configuration.setLocalizedLookup(false);
        configuration.setObjectWrapper(StartupServlet.prepareObjectWrapper());
        configuration.setBooleanFormat("yes,no");
        configuration.setNumberFormat("0.######");
    }

    private static ObjectWrapper prepareObjectWrapper() {
        DefaultObjectWrapper defaultObjectWrapper = new DefaultObjectWrapper();
        defaultObjectWrapper.setExposeFields(true);
        return defaultObjectWrapper;
    }

    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        this.doGetOrPost(httpServletRequest, httpServletResponse);
    }

    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        WebUtil.preventCaching((HttpServletResponse)httpServletResponse);
        this.doGetOrPost(httpServletRequest, httpServletResponse);
    }

    /*
     * Loose catch block
     */
    protected void doGetOrPost(@NotNull HttpServletRequest httpServletRequest, @NotNull HttpServletResponse httpServletResponse) throws ServletException, IOException {
        try {
            if (httpServletRequest == null) {
                StartupServlet.$$$reportNull$$$0(2);
            }
        }
        catch (Throwable throwable) {
            throw StartupServlet.a(throwable);
        }
        try {
            if (httpServletResponse == null) {
                StartupServlet.$$$reportNull$$$0(3);
            }
        }
        catch (Throwable throwable) {
            throw StartupServlet.a(throwable);
        }
        try {
            PrintWriter printWriter;
            block53: {
                String string;
                String string2;
                String string3;
                StartupUserSession startupUserSession;
                String string4;
                block51: {
                    block50: {
                        String string5 = WebUtil.getUserAgent((HttpServletRequest)httpServletRequest);
                        try {
                            if (string5 == null || !string5.startsWith("TeamCity Agent")) break block50;
                        }
                        catch (Throwable throwable) {
                            throw StartupServlet.a(throwable);
                        }
                        httpServletResponse.getWriter().write("TeamCity Server is starting");
                        return;
                    }
                    string4 = StringUtil.emptyIfNull((String)httpServletRequest.getPathInfo());
                    startupUserSession = this.obtainUserSession(httpServletRequest);
                    if (string4.startsWith("/")) {
                        string4 = string4.substring(1);
                    }
                    if (string4.endsWith("/")) {
                        string4 = string4.substring(0, string4.length() - 1);
                    }
                    try {
                        if (!TeamCityProperties.getBooleanOrTrue((String)"teamcity.csrf.check.startupPages.enabled") || CSRFFilter.validateRequest(Collections.singletonList(new CsrfTokenCheck()), (HttpServletRequest)httpServletRequest, (HttpServletResponse)httpServletResponse)) break block51;
                    }
                    catch (Throwable throwable) {
                        throw StartupServlet.a(throwable);
                    }
                    return;
                }
                String[] stringArray = string4.split("/");
                try {
                    string3 = stringArray.length > 0 ? stringArray[0] : "";
                }
                catch (Throwable throwable) {
                    throw StartupServlet.a(throwable);
                }
                String string6 = string3;
                try {
                    string2 = stringArray.length > 1 ? stringArray[1] : "";
                }
                catch (Throwable throwable) {
                    throw StartupServlet.a(throwable);
                }
                String string7 = string2;
                try {
                    string = stringArray.length > 2 ? stringArray[2] : "";
                }
                catch (Throwable throwable) {
                    throw StartupServlet.a(throwable);
                }
                String string8 = string;
                httpServletResponse.setCharacterEncoding("UTF-8");
                StartupStage startupStage = this.myContext.getCurrStage();
                int n = this.myContext.getStateRevision();
                printWriter = httpServletResponse.getWriter();
                try {
                    block58: {
                        block63: {
                            block57: {
                                block56: {
                                    block55: {
                                        block54: {
                                            block52: {
                                                if (!string6.equals("get")) break block52;
                                                try {
                                                    block60: {
                                                        if (string7.length() <= 0) break block52;
                                                        break block60;
                                                        catch (Throwable throwable) {
                                                            throw StartupServlet.a(throwable);
                                                        }
                                                    }
                                                    this.doProcessAjaxQuery(startupUserSession, string7, printWriter, httpServletRequest, httpServletResponse);
                                                    break block53;
                                                }
                                                catch (Throwable throwable) {
                                                    throw StartupServlet.a(throwable);
                                                }
                                            }
                                            if (!string6.equals("do")) break block54;
                                            try {
                                                block61: {
                                                    if (string7.length() <= 0) break block54;
                                                    break block61;
                                                    catch (Throwable throwable) {
                                                        throw StartupServlet.a(throwable);
                                                    }
                                                }
                                                this.doProcessAjaxCommand(startupUserSession, string7, printWriter, httpServletRequest, httpServletResponse);
                                                break block53;
                                            }
                                            catch (Throwable throwable) {
                                                throw StartupServlet.a(throwable);
                                            }
                                        }
                                        if (!string6.equals("debug")) break block55;
                                        try {
                                            block62: {
                                                if (!this.myContext.isDevelopmentMode()) break block55;
                                                break block62;
                                                catch (Throwable throwable) {
                                                    throw StartupServlet.a(throwable);
                                                }
                                            }
                                            this.tryToAuthenticateAutomatically(httpServletRequest, httpServletResponse, startupUserSession);
                                            this.doProcessDebugPage(startupUserSession, string7, string8, httpServletRequest, httpServletResponse, printWriter, startupStage, n);
                                            break block53;
                                        }
                                        catch (Throwable throwable) {
                                            throw StartupServlet.a(throwable);
                                        }
                                    }
                                    try {
                                        if (startupStage != StartupStage.FATAL_ERROR) break block56;
                                        this.doProcessFatalPage(startupUserSession, httpServletRequest, httpServletResponse, printWriter);
                                        break block53;
                                    }
                                    catch (Throwable throwable) {
                                        throw StartupServlet.a(throwable);
                                    }
                                }
                                try {
                                    if (!string6.endsWith("welcome")) break block57;
                                    this.doProcessWelcomePage(startupUserSession, httpServletRequest, httpServletResponse, printWriter, startupStage, n);
                                    break block53;
                                }
                                catch (Throwable throwable) {
                                    throw StartupServlet.a(throwable);
                                }
                            }
                            if (this.myContext.isDevelopmentMode()) break block58;
                            if (startupStage != StartupStage.READY) break block58;
                            break block63;
                            catch (Throwable throwable) {
                                throw StartupServlet.a(throwable);
                            }
                        }
                        try {
                            block64: {
                                if (WebUtil.isInclude((HttpServletRequest)httpServletRequest)) break block58;
                                break block64;
                                catch (Throwable throwable) {
                                    throw StartupServlet.a(throwable);
                                }
                            }
                            this.doRedirectToNonMaintenancepage(startupUserSession, httpServletRequest, httpServletResponse);
                            break block53;
                        }
                        catch (Throwable throwable) {
                            throw StartupServlet.a(throwable);
                        }
                    }
                    this.tryToAuthenticateAutomatically(httpServletRequest, httpServletResponse, startupUserSession);
                    this.doProcessStagePage(startupUserSession, httpServletRequest, httpServletResponse, printWriter, startupStage, n);
                }
                catch (Throwable throwable) {
                    if (printWriter != null) {
                        try {
                            printWriter.close();
                        }
                        catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                    }
                    throw throwable;
                }
            }
            try {
                if (printWriter != null) {
                    printWriter.close();
                }
            }
            catch (Throwable throwable) {
                throw StartupServlet.a(throwable);
            }
        }
        catch (Throwable throwable) {
            this.myLogger.warnAndDebugDetails("Error processing request during server startup: " + throwable.toString() + ", request details: " + WebUtil.getRequestDump((HttpServletRequest)httpServletRequest), throwable);
            throw throwable;
        }
    }

    private StartupUserSession obtainUserSession(HttpServletRequest httpServletRequest) {
        HttpSession httpSession;
        block5: {
            httpSession = httpServletRequest.getSession(true);
            try {
                try {
                    CSRFFilter.setSessionAttribute((HttpSession)httpSession);
                    if ($assertionsDisabled || httpSession != null) break block5;
                }
                catch (NoSuitableJdbcDrivers noSuitableJdbcDrivers) {
                    throw StartupServlet.a(noSuitableJdbcDrivers);
                }
                throw new AssertionError();
            }
            catch (NoSuitableJdbcDrivers noSuitableJdbcDrivers) {
                throw StartupServlet.a(noSuitableJdbcDrivers);
            }
        }
        StartupUserSession startupUserSession = (StartupUserSession)httpSession.getAttribute("userSession");
        if (startupUserSession == null) {
            startupUserSession = new StartupUserSession();
            httpSession.setAttribute("userSession", (Object)startupUserSession);
            httpSession.setAttribute("teamcity.session.originalInactivityInterval", (Object)httpSession.getMaxInactiveInterval());
            httpSession.setMaxInactiveInterval(TeamCityProperties.getInteger((String)"teamcity.session.startup.timeout", (int)60));
        }
        return startupUserSession;
    }

    private void tryToAuthenticateAutomatically(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, StartupUserSession startupUserSession) {
        Cookie[] cookieArray;
        String string;
        block19: {
            block18: {
                string = this.getInetAddressAsString(httpServletRequest);
                try {
                    try {
                        if (!this.myContext.isFirstStart() || startupUserSession.isAuthenticated()) break block18;
                    }
                    catch (NoSuitableJdbcDrivers noSuitableJdbcDrivers) {
                        throw StartupServlet.a(noSuitableJdbcDrivers);
                    }
                    startupUserSession.setAuthenticated(true);
                    this.myContext.addAdminLoggedIn((Object)startupUserSession);
                    this.myLogger.debug("Bypassing authentication for " + string + " because of the first start for request" + WebUtil.getRequestDump((HttpServletRequest)httpServletRequest));
                }
                catch (NoSuitableJdbcDrivers noSuitableJdbcDrivers) {
                    throw StartupServlet.a(noSuitableJdbcDrivers);
                }
            }
            try {
                try {
                    try {
                        if (!this.myContext.isDevelopmentMode() || !StartupServlet.isLocalHost(httpServletRequest)) break block19;
                    }
                    catch (NoSuitableJdbcDrivers noSuitableJdbcDrivers) {
                        throw StartupServlet.a(noSuitableJdbcDrivers);
                    }
                    if (startupUserSession.isAuthenticated()) break block19;
                }
                catch (NoSuitableJdbcDrivers noSuitableJdbcDrivers) {
                    throw StartupServlet.a(noSuitableJdbcDrivers);
                }
                startupUserSession.setAuthenticated(true);
                this.myContext.addAdminLoggedIn((Object)startupUserSession);
                this.myLogger.info("Bypassing authentication for localhost in development mode for request " + WebUtil.getRequestDump((HttpServletRequest)httpServletRequest));
            }
            catch (NoSuitableJdbcDrivers noSuitableJdbcDrivers) {
                throw StartupServlet.a(noSuitableJdbcDrivers);
            }
        }
        if (!startupUserSession.isAuthenticated() && (cookieArray = httpServletRequest.getCookies()) != null) {
            for (Cookie cookie : cookieArray) {
                block20: {
                    try {
                        try {
                            try {
                                if (!cookie.getName().equals("maintenanceToken")) continue;
                                if (cookie.getValue() == null) break block20;
                            }
                            catch (NoSuitableJdbcDrivers noSuitableJdbcDrivers) {
                                throw StartupServlet.a(noSuitableJdbcDrivers);
                            }
                            if (!this.myContext.isValidMaintenanceAuthenticationToken(cookie.getValue())) break block20;
                        }
                        catch (NoSuitableJdbcDrivers noSuitableJdbcDrivers) {
                            throw StartupServlet.a(noSuitableJdbcDrivers);
                        }
                        startupUserSession.setAuthenticated(true);
                        this.myContext.addAdminLoggedIn((Object)startupUserSession);
                        this.myLogger.debug("Authenticate session for " + string + " from the Cookie for request " + WebUtil.getRequestDump((HttpServletRequest)httpServletRequest));
                    }
                    catch (NoSuitableJdbcDrivers noSuitableJdbcDrivers) {
                        throw StartupServlet.a(noSuitableJdbcDrivers);
                    }
                }
                httpServletResponse.addCookie(WebUtil.createCookie((HttpServletRequest)httpServletRequest, (String)"maintenanceToken", (String)"", (int)0));
            }
        }
    }

    private boolean isInitialStage(StartupStage startupStage) {
        boolean bl;
        block5: {
            block4: {
                try {
                    try {
                        if (startupStage != StartupStage.INITIAL_STAGE && startupStage != StartupStage.LOOKING_FOR_DATA_DIR) break block4;
                    }
                    catch (NoSuitableJdbcDrivers noSuitableJdbcDrivers) {
                        throw StartupServlet.a(noSuitableJdbcDrivers);
                    }
                    bl = true;
                    break block5;
                }
                catch (NoSuitableJdbcDrivers noSuitableJdbcDrivers) {
                    throw StartupServlet.a(noSuitableJdbcDrivers);
                }
            }
            bl = false;
        }
        return bl;
    }

    private static boolean isLocalHost(HttpServletRequest httpServletRequest) {
        try {
            InetAddress inetAddress = StartupServlet.getInetAddress(httpServletRequest);
            return inetAddress.isLoopbackAddress();
        }
        catch (UnknownHostException unknownHostException) {
            unknownHostException.printStackTrace(System.err);
            return false;
        }
    }

    private static InetAddress getInetAddress(HttpServletRequest httpServletRequest) throws UnknownHostException {
        String string = httpServletRequest.getRemoteAddr();
        return InetAddress.getByName(string);
    }

    private String getInetAddressAsString(HttpServletRequest httpServletRequest) {
        try {
            return StartupServlet.getInetAddress(httpServletRequest).toString();
        }
        catch (UnknownHostException unknownHostException) {
            this.myLogger.error("Could not obtain IP address from the Web request: " + unknownHostException.getMessage());
            return "<unknown address>";
        }
    }

    private void doProcessAjaxQuery(@NotNull StartupUserSession startupUserSession, @NotNull String string, @NotNull PrintWriter printWriter, @NotNull HttpServletRequest httpServletRequest, @NotNull HttpServletResponse httpServletResponse) {
        Object object;
        Object object2;
        try {
            if (startupUserSession == null) {
                StartupServlet.$$$reportNull$$$0(4);
            }
        }
        catch (Exception exception) {
            throw StartupServlet.a(exception);
        }
        try {
            if (string == null) {
                StartupServlet.$$$reportNull$$$0(5);
            }
        }
        catch (Exception exception) {
            throw StartupServlet.a(exception);
        }
        try {
            if (printWriter == null) {
                StartupServlet.$$$reportNull$$$0(6);
            }
        }
        catch (Exception exception) {
            throw StartupServlet.a(exception);
        }
        try {
            if (httpServletRequest == null) {
                StartupServlet.$$$reportNull$$$0(7);
            }
        }
        catch (Exception exception) {
            throw StartupServlet.a(exception);
        }
        try {
            if (httpServletResponse == null) {
                StartupServlet.$$$reportNull$$$0(8);
            }
        }
        catch (Exception exception) {
            throw StartupServlet.a(exception);
        }
        try {
            httpServletResponse.setContentType("text/plain");
            object2 = new AjaxBean(this, startupUserSession, httpServletRequest);
            object = ourPropertyUtilsBean.getProperty(object2, string);
        }
        catch (Exception exception) {
            ImmutableMap immutableMap;
            try {
                immutableMap = this.myLogger.isDebugEnabled() ? Collections.singletonMap("error", exception.getMessage()) : ImmutableMap.of((Object)"error", (Object)exception.getMessage(), (Object)"exception", (Object)exception.getStackTrace());
            }
            catch (Exception exception2) {
                throw StartupServlet.a(exception2);
            }
            object = immutableMap;
        }
        object2 = this.packToJson(object);
        printWriter.print((String)object2);
    }

    private String packToJson(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    private void doProcessAjaxCommand(@NotNull StartupUserSession startupUserSession, @NotNull String string, @NotNull PrintWriter printWriter, @NotNull HttpServletRequest httpServletRequest, @NotNull HttpServletResponse httpServletResponse) {
        try {
            if (startupUserSession == null) {
                StartupServlet.$$$reportNull$$$0(9);
            }
        }
        catch (NoSuchMethodException noSuchMethodException) {
            throw StartupServlet.a(noSuchMethodException);
        }
        try {
            if (string == null) {
                StartupServlet.$$$reportNull$$$0(10);
            }
        }
        catch (NoSuchMethodException noSuchMethodException) {
            throw StartupServlet.a(noSuchMethodException);
        }
        try {
            if (printWriter == null) {
                StartupServlet.$$$reportNull$$$0(11);
            }
        }
        catch (NoSuchMethodException noSuchMethodException) {
            throw StartupServlet.a(noSuchMethodException);
        }
        try {
            if (httpServletRequest == null) {
                StartupServlet.$$$reportNull$$$0(12);
            }
        }
        catch (NoSuchMethodException noSuchMethodException) {
            throw StartupServlet.a(noSuchMethodException);
        }
        try {
            if (httpServletResponse == null) {
                StartupServlet.$$$reportNull$$$0(13);
            }
        }
        catch (NoSuchMethodException noSuchMethodException) {
            throw StartupServlet.a(noSuchMethodException);
        }
        try {
            httpServletResponse.setContentType("text/plain");
            Method method = AjaxBean.class.getMethod(string, new Class[0]);
            AjaxBean ajaxBean = new AjaxBean(this, startupUserSession, httpServletRequest);
            method.invoke((Object)ajaxBean, new Object[0]);
            printWriter.print("OK");
        }
        catch (NoSuchMethodException noSuchMethodException) {
            printWriter.write("Not Found");
            this.myLogger.warn("Unexpected AJAX request: " + WebUtil.getRequestDump((HttpServletRequest)httpServletRequest));
            httpServletResponse.setStatus(404);
        }
        catch (InvocationTargetException invocationTargetException) {
            Throwable throwable = invocationTargetException.getTargetException();
            this.handleExceptionInsideAjaxCall(throwable, printWriter);
        }
        catch (Exception exception) {
            this.handleExceptionInsideAjaxCall(exception, printWriter);
        }
    }

    private void handleExceptionInsideAjaxCall(@NotNull Throwable throwable, @NotNull PrintWriter printWriter) {
        block9: {
            block8: {
                try {
                    if (throwable == null) {
                        StartupServlet.$$$reportNull$$$0(14);
                    }
                }
                catch (NoSuitableJdbcDrivers noSuitableJdbcDrivers) {
                    throw StartupServlet.a(noSuitableJdbcDrivers);
                }
                try {
                    if (printWriter == null) {
                        StartupServlet.$$$reportNull$$$0(15);
                    }
                }
                catch (NoSuitableJdbcDrivers noSuitableJdbcDrivers) {
                    throw StartupServlet.a(noSuitableJdbcDrivers);
                }
                try {
                    if (!(throwable instanceof KnownStartupError)) break block8;
                    this.myLogger.info("Responding with " + throwable.toString());
                    printWriter.print(throwable.getMessage());
                    break block9;
                }
                catch (NoSuitableJdbcDrivers noSuitableJdbcDrivers) {
                    throw StartupServlet.a(noSuitableJdbcDrivers);
                }
            }
            this.myLogger.warn("Error while processing AJAX request: " + throwable.toString(), throwable);
            throwable.printStackTrace(printWriter);
        }
    }

    private void doProcessWelcomePage(@NotNull StartupUserSession startupUserSession, @NotNull HttpServletRequest httpServletRequest, @NotNull HttpServletResponse httpServletResponse, @NotNull PrintWriter printWriter, @NotNull StartupStage startupStage, int n) {
        try {
            if (startupUserSession == null) {
                StartupServlet.$$$reportNull$$$0(16);
            }
        }
        catch (NoSuitableJdbcDrivers noSuitableJdbcDrivers) {
            throw StartupServlet.a(noSuitableJdbcDrivers);
        }
        try {
            if (httpServletRequest == null) {
                StartupServlet.$$$reportNull$$$0(17);
            }
        }
        catch (NoSuitableJdbcDrivers noSuitableJdbcDrivers) {
            throw StartupServlet.a(noSuitableJdbcDrivers);
        }
        try {
            if (httpServletResponse == null) {
                StartupServlet.$$$reportNull$$$0(18);
            }
        }
        catch (NoSuitableJdbcDrivers noSuitableJdbcDrivers) {
            throw StartupServlet.a(noSuitableJdbcDrivers);
        }
        try {
            if (printWriter == null) {
                StartupServlet.$$$reportNull$$$0(19);
            }
        }
        catch (NoSuitableJdbcDrivers noSuitableJdbcDrivers) {
            throw StartupServlet.a(noSuitableJdbcDrivers);
        }
        try {
            if (startupStage == null) {
                StartupServlet.$$$reportNull$$$0(20);
            }
        }
        catch (NoSuitableJdbcDrivers noSuitableJdbcDrivers) {
            throw StartupServlet.a(noSuitableJdbcDrivers);
        }
        this.doProcessPage(startupUserSession, httpServletRequest, httpServletResponse, "maintenance-welcome", printWriter, startupStage, n);
    }

    private void doProcessStagePage(@NotNull StartupUserSession startupUserSession, @NotNull HttpServletRequest httpServletRequest, @NotNull HttpServletResponse httpServletResponse, @NotNull PrintWriter printWriter, @NotNull StartupStage startupStage, int n) {
        String string;
        block20: {
            block19: {
                try {
                    if (startupUserSession == null) {
                        StartupServlet.$$$reportNull$$$0(21);
                    }
                }
                catch (NoSuitableJdbcDrivers noSuitableJdbcDrivers) {
                    throw StartupServlet.a(noSuitableJdbcDrivers);
                }
                try {
                    if (httpServletRequest == null) {
                        StartupServlet.$$$reportNull$$$0(22);
                    }
                }
                catch (NoSuitableJdbcDrivers noSuitableJdbcDrivers) {
                    throw StartupServlet.a(noSuitableJdbcDrivers);
                }
                try {
                    if (httpServletResponse == null) {
                        StartupServlet.$$$reportNull$$$0(23);
                    }
                }
                catch (NoSuitableJdbcDrivers noSuitableJdbcDrivers) {
                    throw StartupServlet.a(noSuitableJdbcDrivers);
                }
                try {
                    if (printWriter == null) {
                        StartupServlet.$$$reportNull$$$0(24);
                    }
                }
                catch (NoSuitableJdbcDrivers noSuitableJdbcDrivers) {
                    throw StartupServlet.a(noSuitableJdbcDrivers);
                }
                try {
                    if (startupStage == null) {
                        StartupServlet.$$$reportNull$$$0(25);
                    }
                }
                catch (NoSuitableJdbcDrivers noSuitableJdbcDrivers) {
                    throw StartupServlet.a(noSuitableJdbcDrivers);
                }
                try {
                    try {
                        if (!startupUserSession.isAuthenticated() && !this.isInitialStage(startupStage)) break block19;
                    }
                    catch (NoSuitableJdbcDrivers noSuitableJdbcDrivers) {
                        throw StartupServlet.a(noSuitableJdbcDrivers);
                    }
                    string = startupStage.page;
                    break block20;
                }
                catch (NoSuitableJdbcDrivers noSuitableJdbcDrivers) {
                    throw StartupServlet.a(noSuitableJdbcDrivers);
                }
            }
            string = "maintenance-authenticate";
        }
        String string2 = string;
        this.doProcessPage(startupUserSession, httpServletRequest, httpServletResponse, string2, printWriter, startupStage, n);
    }

    private void doProcessDebugPage(@NotNull StartupUserSession startupUserSession, @NotNull String string, @NotNull String string2, @NotNull HttpServletRequest httpServletRequest, @NotNull HttpServletResponse httpServletResponse, @NotNull PrintWriter printWriter, @NotNull StartupStage startupStage, int n) {
        block26: {
            block27: {
                block25: {
                    try {
                        if (startupUserSession == null) {
                            StartupServlet.$$$reportNull$$$0(26);
                        }
                    }
                    catch (NoSuitableJdbcDrivers noSuitableJdbcDrivers) {
                        throw StartupServlet.a(noSuitableJdbcDrivers);
                    }
                    try {
                        if (string == null) {
                            StartupServlet.$$$reportNull$$$0(27);
                        }
                    }
                    catch (NoSuitableJdbcDrivers noSuitableJdbcDrivers) {
                        throw StartupServlet.a(noSuitableJdbcDrivers);
                    }
                    try {
                        if (string2 == null) {
                            StartupServlet.$$$reportNull$$$0(28);
                        }
                    }
                    catch (NoSuitableJdbcDrivers noSuitableJdbcDrivers) {
                        throw StartupServlet.a(noSuitableJdbcDrivers);
                    }
                    try {
                        if (httpServletRequest == null) {
                            StartupServlet.$$$reportNull$$$0(29);
                        }
                    }
                    catch (NoSuitableJdbcDrivers noSuitableJdbcDrivers) {
                        throw StartupServlet.a(noSuitableJdbcDrivers);
                    }
                    try {
                        if (httpServletResponse == null) {
                            StartupServlet.$$$reportNull$$$0(30);
                        }
                    }
                    catch (NoSuitableJdbcDrivers noSuitableJdbcDrivers) {
                        throw StartupServlet.a(noSuitableJdbcDrivers);
                    }
                    try {
                        if (printWriter == null) {
                            StartupServlet.$$$reportNull$$$0(31);
                        }
                    }
                    catch (NoSuitableJdbcDrivers noSuitableJdbcDrivers) {
                        throw StartupServlet.a(noSuitableJdbcDrivers);
                    }
                    try {
                        if (startupStage == null) {
                            StartupServlet.$$$reportNull$$$0(32);
                        }
                    }
                    catch (NoSuitableJdbcDrivers noSuitableJdbcDrivers) {
                        throw StartupServlet.a(noSuitableJdbcDrivers);
                    }
                    try {
                        if (string.length() != 0) break block25;
                        this.doProcessPage(startupUserSession, httpServletRequest, httpServletResponse, "debug", printWriter, startupStage, n);
                        break block26;
                    }
                    catch (NoSuitableJdbcDrivers noSuitableJdbcDrivers) {
                        throw StartupServlet.a(noSuitableJdbcDrivers);
                    }
                }
                try {
                    if (!string.equals("page")) break block27;
                    this.doProcessPage(startupUserSession, httpServletRequest, httpServletResponse, string2, printWriter, startupStage, n, true);
                    break block26;
                }
                catch (NoSuitableJdbcDrivers noSuitableJdbcDrivers) {
                    throw StartupServlet.a(noSuitableJdbcDrivers);
                }
            }
            printWriter.println("Invalid page request: " + string);
        }
    }

    private void doProcessFatalPage(@NotNull StartupUserSession startupUserSession, @NotNull HttpServletRequest httpServletRequest, @NotNull HttpServletResponse httpServletResponse, @NotNull PrintWriter printWriter) {
        try {
            if (startupUserSession == null) {
                StartupServlet.$$$reportNull$$$0(33);
            }
        }
        catch (NoSuitableJdbcDrivers noSuitableJdbcDrivers) {
            throw StartupServlet.a(noSuitableJdbcDrivers);
        }
        try {
            if (httpServletRequest == null) {
                StartupServlet.$$$reportNull$$$0(34);
            }
        }
        catch (NoSuitableJdbcDrivers noSuitableJdbcDrivers) {
            throw StartupServlet.a(noSuitableJdbcDrivers);
        }
        try {
            if (httpServletResponse == null) {
                StartupServlet.$$$reportNull$$$0(35);
            }
        }
        catch (NoSuitableJdbcDrivers noSuitableJdbcDrivers) {
            throw StartupServlet.a(noSuitableJdbcDrivers);
        }
        try {
            if (printWriter == null) {
                StartupServlet.$$$reportNull$$$0(36);
            }
        }
        catch (NoSuitableJdbcDrivers noSuitableJdbcDrivers) {
            throw StartupServlet.a(noSuitableJdbcDrivers);
        }
        this.doProcessPage(startupUserSession, httpServletRequest, httpServletResponse, "fatal-error", printWriter, StartupStage.FATAL_ERROR, this.myContext.getStateRevision());
    }

    private void doProcessPage(@NotNull StartupUserSession startupUserSession, @NotNull HttpServletRequest httpServletRequest, @NotNull HttpServletResponse httpServletResponse, @NotNull String string, @NotNull PrintWriter printWriter, @NotNull StartupStage startupStage, int n) {
        try {
            if (startupUserSession == null) {
                StartupServlet.$$$reportNull$$$0(37);
            }
        }
        catch (NoSuitableJdbcDrivers noSuitableJdbcDrivers) {
            throw StartupServlet.a(noSuitableJdbcDrivers);
        }
        try {
            if (httpServletRequest == null) {
                StartupServlet.$$$reportNull$$$0(38);
            }
        }
        catch (NoSuitableJdbcDrivers noSuitableJdbcDrivers) {
            throw StartupServlet.a(noSuitableJdbcDrivers);
        }
        try {
            if (httpServletResponse == null) {
                StartupServlet.$$$reportNull$$$0(39);
            }
        }
        catch (NoSuitableJdbcDrivers noSuitableJdbcDrivers) {
            throw StartupServlet.a(noSuitableJdbcDrivers);
        }
        try {
            if (string == null) {
                StartupServlet.$$$reportNull$$$0(40);
            }
        }
        catch (NoSuitableJdbcDrivers noSuitableJdbcDrivers) {
            throw StartupServlet.a(noSuitableJdbcDrivers);
        }
        try {
            if (printWriter == null) {
                StartupServlet.$$$reportNull$$$0(41);
            }
        }
        catch (NoSuitableJdbcDrivers noSuitableJdbcDrivers) {
            throw StartupServlet.a(noSuitableJdbcDrivers);
        }
        try {
            if (startupStage == null) {
                StartupServlet.$$$reportNull$$$0(42);
            }
        }
        catch (NoSuitableJdbcDrivers noSuitableJdbcDrivers) {
            throw StartupServlet.a(noSuitableJdbcDrivers);
        }
        this.doProcessPage(startupUserSession, httpServletRequest, httpServletResponse, string, printWriter, startupStage, n, false);
    }

    private void doProcessPage(@NotNull StartupUserSession startupUserSession, @NotNull HttpServletRequest httpServletRequest, @NotNull HttpServletResponse httpServletResponse, @NotNull String string, @NotNull PrintWriter printWriter, @NotNull StartupStage startupStage, int n, boolean bl) {
        try {
            if (startupUserSession == null) {
                StartupServlet.$$$reportNull$$$0(43);
            }
        }
        catch (Exception exception) {
            throw StartupServlet.a(exception);
        }
        try {
            if (httpServletRequest == null) {
                StartupServlet.$$$reportNull$$$0(44);
            }
        }
        catch (Exception exception) {
            throw StartupServlet.a(exception);
        }
        try {
            if (httpServletResponse == null) {
                StartupServlet.$$$reportNull$$$0(45);
            }
        }
        catch (Exception exception) {
            throw StartupServlet.a(exception);
        }
        try {
            if (string == null) {
                StartupServlet.$$$reportNull$$$0(46);
            }
        }
        catch (Exception exception) {
            throw StartupServlet.a(exception);
        }
        try {
            if (printWriter == null) {
                StartupServlet.$$$reportNull$$$0(47);
            }
        }
        catch (Exception exception) {
            throw StartupServlet.a(exception);
        }
        try {
            if (startupStage == null) {
                StartupServlet.$$$reportNull$$$0(48);
            }
        }
        catch (Exception exception) {
            throw StartupServlet.a(exception);
        }
        try {
            String string2 = string + ".ftl";
            Template template = this.myFmConfig.getTemplate(string2);
            StartupPageModel startupPageModel = this.preparePageModel(startupUserSession, httpServletRequest, string, startupStage, n, bl);
            httpServletResponse.setContentType("text/html;charset=UTF-8");
            template.process((Object)startupPageModel, (Writer)printWriter);
        }
        catch (Exception exception) {
            this.showLowLevelError(printWriter, exception);
        }
    }

    /*
     * Loose catch block
     */
    private StartupPageModel preparePageModel(@NotNull StartupUserSession startupUserSession, @NotNull HttpServletRequest httpServletRequest, @NotNull String string, @NotNull StartupStage startupStage, int n, boolean bl) {
        ProgressInfo progressInfo;
        ProgressInfo progressInfo2;
        Object object;
        TeamCityDatabaseManager teamCityDatabaseManager;
        File file;
        StartupPageSpecificInfo startupPageSpecificInfo;
        DebugBean debugBean;
        String string2;
        block40: {
            block44: {
                block41: {
                    block39: {
                        DebugBean debugBean2;
                        block38: {
                            block37: {
                                try {
                                    if (startupUserSession == null) {
                                        StartupServlet.$$$reportNull$$$0(49);
                                    }
                                }
                                catch (Exception exception) {
                                    throw StartupServlet.a(exception);
                                }
                                try {
                                    if (httpServletRequest == null) {
                                        StartupServlet.$$$reportNull$$$0(50);
                                    }
                                }
                                catch (Exception exception) {
                                    throw StartupServlet.a(exception);
                                }
                                try {
                                    if (string == null) {
                                        StartupServlet.$$$reportNull$$$0(51);
                                    }
                                }
                                catch (Exception exception) {
                                    throw StartupServlet.a(exception);
                                }
                                try {
                                    if (startupStage == null) {
                                        StartupServlet.$$$reportNull$$$0(52);
                                    }
                                }
                                catch (Exception exception) {
                                    throw StartupServlet.a(exception);
                                }
                                string2 = httpServletRequest.getContextPath();
                                if (!startupUserSession.isAuthenticated()) break block37;
                                try {
                                    block42: {
                                        if (!this.myContext.isDevelopmentMode()) break block37;
                                        break block42;
                                        catch (Exception exception) {
                                            throw StartupServlet.a(exception);
                                        }
                                    }
                                    debugBean2 = new DebugBean();
                                    break block38;
                                }
                                catch (Exception exception) {
                                    throw StartupServlet.a(exception);
                                }
                            }
                            debugBean2 = null;
                        }
                        debugBean = debugBean2;
                        startupPageSpecificInfo = new StartupPageSpecificInfo();
                        startupPageSpecificInfo.currentStage = startupStage;
                        startupPageSpecificInfo.stateRevision = n;
                        if (this.myContext.isDataDirSpecified()) {
                            file = this.myContext.getDataDirManager().getRootDir();
                            startupPageSpecificInfo.myDataDirectory = StartupServlet.getAbsolutePath(file);
                            startupPageSpecificInfo.myJdbcDirectory = StartupServlet.getAbsolutePath(new File(file, "lib/jdbc"));
                        }
                        if (string.equals("db-settings")) break block39;
                        try {
                            block43: {
                                if (string.equals("restore-db-settings")) break block39;
                                break block43;
                                catch (Exception exception) {
                                    throw StartupServlet.a(exception);
                                }
                            }
                            if (!string.equals("debug")) break block40;
                        }
                        catch (Exception exception) {
                            throw StartupServlet.a(exception);
                        }
                    }
                    StartupHelper.reloadJdbcDriversIfNeeded((StartupContext)this.myContext);
                    this.checkJdbcDriverVersions(startupPageSpecificInfo);
                    file = StartupServlet.getFromSession(httpServletRequest, JdbcConnectionString.class, "userDBsettingsInput");
                    try {
                        if (file == null) break block41;
                        startupPageSpecificInfo.myConnectionString = file;
                        break block40;
                    }
                    catch (Exception exception) {
                        throw StartupServlet.a(exception);
                    }
                }
                teamCityDatabaseManager = this.myContext.getDatabaseManager();
                if (teamCityDatabaseManager == null) break block44;
                object = teamCityDatabaseManager.getConnectionString();
                if (object != null) {
                    try {
                        startupPageSpecificInfo.myConnectionString.parse((String)object);
                    }
                    catch (Exception exception) {
                        this.myLogger.debug("Problem with parsing connection string (" + (String)object + "): " + exception.getMessage(), (Throwable)exception);
                    }
                }
                break block40;
            }
            if (!string.equals("restore-db-settings")) break block40;
            object = this.myContext.getConnectionStringFromBackup();
            if (object == null) break block40;
            try {
                block45: {
                    if (object.getDBType() == DatabaseType.HSQLDB2) break block40;
                    break block45;
                    catch (Exception exception) {
                        throw StartupServlet.a(exception);
                    }
                }
                startupPageSpecificInfo.myConnectionString = object;
            }
            catch (Exception exception) {
                throw StartupServlet.a(exception);
            }
        }
        file = this.myContext.getBackupProcess();
        try {
            StartupPageSpecificInfo startupPageSpecificInfo2 = startupPageSpecificInfo;
            progressInfo2 = file == null ? null : file.getProgressInfo();
        }
        catch (Exception exception) {
            throw StartupServlet.a(exception);
        }
        startupPageSpecificInfo2.myBackupProgressInfo = progressInfo2;
        try {
            startupPageSpecificInfo.myLogPath = LogInitializer.getLogDir().getAbsolutePath();
        }
        catch (LogInitializationException logInitializationException) {
            startupPageSpecificInfo.myLogFailedDirs = logInitializationException.getCheckedDirs();
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        startupPageSpecificInfo.isDatabaseCreatedAtStartup = this.myContext.isDatabaseCreatedOnStartup();
        teamCityDatabaseManager = this.myContext.getJdbcDriverDownloadProgressListener();
        try {
            StartupPageSpecificInfo startupPageSpecificInfo3 = startupPageSpecificInfo;
            progressInfo = teamCityDatabaseManager == null ? null : teamCityDatabaseManager.getProgress();
        }
        catch (Exception exception) {
            throw StartupServlet.a(exception);
        }
        startupPageSpecificInfo3.myDriverDownloadProgress = progressInfo;
        object = WebUtil.isProbablyBrowser((HttpServletRequest)httpServletRequest);
        String string3 = this.getCsrfToken(httpServletRequest.getSession());
        StartupPageModel startupPageModel = new StartupPageModel(this.myContext, startupUserSession, string2, string, startupPageSpecificInfo, ((Boolean)object).booleanValue(), debugBean, bl, string3);
        return startupPageModel;
    }

    @NotNull
    private String getCsrfToken(@NotNull HttpSession httpSession) {
        String string;
        try {
            if (httpSession == null) {
                StartupServlet.$$$reportNull$$$0(53);
            }
        }
        catch (NoSuitableJdbcDrivers noSuitableJdbcDrivers) {
            throw StartupServlet.a(noSuitableJdbcDrivers);
        }
        Object object = httpSession.getAttribute("tc-csrf-token");
        try {
            if (object == null) {
                return "";
            }
        }
        catch (NoSuitableJdbcDrivers noSuitableJdbcDrivers) {
            throw StartupServlet.a(noSuitableJdbcDrivers);
        }
        try {
            string = object.toString();
            if (string == null) {
                StartupServlet.$$$reportNull$$$0(54);
            }
        }
        catch (NoSuitableJdbcDrivers noSuitableJdbcDrivers) {
            throw StartupServlet.a(noSuitableJdbcDrivers);
        }
        return string;
    }

    private static String getAbsolutePath(File file) {
        return file.getAbsolutePath();
    }

    @Nullable
    private static <T> T getFromSession(@NotNull HttpServletRequest httpServletRequest, Class<T> clazz, String string) {
        try {
            if (httpServletRequest == null) {
                StartupServlet.$$$reportNull$$$0(55);
            }
        }
        catch (NoSuitableJdbcDrivers noSuitableJdbcDrivers) {
            throw StartupServlet.a(noSuitableJdbcDrivers);
        }
        HttpSession httpSession = httpServletRequest.getSession(false);
        try {
            if (httpSession == null) {
                return null;
            }
        }
        catch (NoSuitableJdbcDrivers noSuitableJdbcDrivers) {
            throw StartupServlet.a(noSuitableJdbcDrivers);
        }
        return (T)FormUtil.getFromSession((HttpSession)httpSession, clazz, (String)string);
    }

    void checkJdbcDriverVersions(@NotNull StartupPageSpecificInfo startupPageSpecificInfo) {
        try {
            if (startupPageSpecificInfo == null) {
                StartupServlet.$$$reportNull$$$0(56);
            }
        }
        catch (NoSuitableJdbcDrivers noSuitableJdbcDrivers) {
            throw StartupServlet.a(noSuitableJdbcDrivers);
        }
        startupPageSpecificInfo.myJdbcDriverVersionForPostgre = this.checkJdbcDriverVersionFor(DatabaseType.POSTGRESQL);
        startupPageSpecificInfo.myJdbcDriverVersionForMySQL = this.checkJdbcDriverVersionFor(DatabaseType.MYSQL);
        startupPageSpecificInfo.myJdbcDriverVersionForOracle = this.checkJdbcDriverVersionFor(DatabaseType.ORACLE);
        startupPageSpecificInfo.myJdbcDriverVersionForMSSQL = this.checkJdbcDriverVersionFor(DatabaseType.MSSQL);
        startupPageSpecificInfo.myJdbcDriverMetadataForPostgreSql = this.checkJdbcDriverMetadataFor(DatabaseType.POSTGRESQL);
        startupPageSpecificInfo.myJdbcDriverMetadataForMySql = this.checkJdbcDriverMetadataFor(DatabaseType.MYSQL);
        startupPageSpecificInfo.myJdbcDriverMetadataForOracle = this.checkJdbcDriverMetadataFor(DatabaseType.ORACLE);
        startupPageSpecificInfo.myJdbcDriverMetadataForMsSql = this.checkJdbcDriverMetadataFor(DatabaseType.MSSQL);
    }

    @Nullable
    private MajorMinorVersion checkJdbcDriverVersionFor(DatabaseType databaseType) {
        try {
            JdbcDrivers jdbcDrivers = this.myContext.getJdbcDrivers();
            if (jdbcDrivers == null) {
                return null;
            }
            Driver driver = jdbcDrivers.findDefaultDriver(databaseType);
            if (driver == null) {
                return null;
            }
            return new MajorMinorVersion(driver.getMajorVersion(), driver.getMinorVersion());
        }
        catch (NoSuitableJdbcDrivers noSuitableJdbcDrivers) {
            return null;
        }
        catch (Throwable throwable) {
            this.myLogger.warn(throwable.getMessage(), throwable);
            return null;
        }
    }

    @Nullable
    private JdbcDriver checkJdbcDriverMetadataFor(@NotNull DatabaseType databaseType) {
        try {
            if (databaseType == null) {
                StartupServlet.$$$reportNull$$$0(57);
            }
        }
        catch (NoSuitableJdbcDrivers noSuitableJdbcDrivers) {
            throw StartupServlet.a(noSuitableJdbcDrivers);
        }
        return (JdbcDriver)this.myContext.getJdbcDriverMetadata().get(databaseType);
    }

    private void doRedirectToNonMaintenancepage(@NotNull StartupUserSession startupUserSession, @NotNull HttpServletRequest httpServletRequest, @NotNull HttpServletResponse httpServletResponse) {
        try {
            if (startupUserSession == null) {
                StartupServlet.$$$reportNull$$$0(58);
            }
        }
        catch (IOException iOException) {
            throw StartupServlet.a(iOException);
        }
        try {
            if (httpServletRequest == null) {
                StartupServlet.$$$reportNull$$$0(59);
            }
        }
        catch (IOException iOException) {
            throw StartupServlet.a(iOException);
        }
        try {
            if (httpServletResponse == null) {
                StartupServlet.$$$reportNull$$$0(60);
            }
        }
        catch (IOException iOException) {
            throw StartupServlet.a(iOException);
        }
        Object object = startupUserSession.getRedirectedFrom();
        if (object == null) {
            object = httpServletRequest.getContextPath() + new RelativeWebLinks().getOverviewUrl();
        }
        try {
            httpServletResponse.sendRedirect((String)object);
        }
        catch (IOException iOException) {
            this.myLogger.error("Failed to send redirect: " + iOException.getMessage(), (Throwable)iOException);
        }
        startupUserSession.setRedirectedFrom(null);
    }

    private void showLowLevelError(@NotNull PrintWriter printWriter, @NotNull Exception exception) {
        try {
            if (printWriter == null) {
                StartupServlet.$$$reportNull$$$0(61);
            }
        }
        catch (NoSuitableJdbcDrivers noSuitableJdbcDrivers) {
            throw StartupServlet.a(noSuitableJdbcDrivers);
        }
        try {
            if (exception == null) {
                StartupServlet.$$$reportNull$$$0(62);
            }
        }
        catch (NoSuitableJdbcDrivers noSuitableJdbcDrivers) {
            throw StartupServlet.a(noSuitableJdbcDrivers);
        }
        this.myLogger.error("Error when processing FreeMarker template: " + exception.getClass().getSimpleName() + ": " + exception.getMessage(), (Throwable)exception);
        String string = exception.getClass().getSimpleName() + "\n" + exception.getMessage();
        StringBuilder stringBuilder = new StringBuilder(1024);
        stringBuilder.append("<html>\n<head><title>TeamCity maintenance page error</title></head>\n");
        stringBuilder.append("<body>\n<h3>FreeMarker page processing error</h3>\n");
        stringBuilder.append("<pre>").append(string).append("\n</pre>\n");
        stringBuilder.append("</body>\n");
        stringBuilder.append("</html>\n");
        printWriter.println(stringBuilder.toString());
    }

    void setupDatabasePropertiesFromRequest(HttpServletRequest httpServletRequest) {
        JdbcConnectionString jdbcConnectionString = StartupServlet.peekConnectionStringFromRequest(httpServletRequest, this.myLogger);
        try {
            if (jdbcConnectionString != null) {
                this.setupDatabaseProperties(jdbcConnectionString);
            }
        }
        catch (NoSuitableJdbcDrivers noSuitableJdbcDrivers) {
            throw StartupServlet.a(noSuitableJdbcDrivers);
        }
    }

    void setupDatabaseProperties(@NotNull JdbcConnectionString jdbcConnectionString) {
        boolean bl;
        StartupContext startupContext;
        try {
            if (jdbcConnectionString == null) {
                StartupServlet.$$$reportNull$$$0(63);
            }
        }
        catch (NoSuitableJdbcDrivers noSuitableJdbcDrivers) {
            throw StartupServlet.a(noSuitableJdbcDrivers);
        }
        try {
            this.myContext.setConnectionString(jdbcConnectionString);
            this.myContext.setDatabaseSettingsFromUISpecified(true);
            startupContext = this.myContext;
            bl = jdbcConnectionString.getDBType() != DatabaseType.HSQLDB2;
        }
        catch (NoSuitableJdbcDrivers noSuitableJdbcDrivers) {
            throw StartupServlet.a(noSuitableJdbcDrivers);
        }
        try {
            startupContext.setDatabaseExternal(bl);
            if (jdbcConnectionString.getPassword() != null) {
                jdbcConnectionString.setPassword("********");
            }
        }
        catch (NoSuitableJdbcDrivers noSuitableJdbcDrivers) {
            throw StartupServlet.a(noSuitableJdbcDrivers);
        }
        String string = jdbcConnectionString.build(true);
        this.myLogger.info("Generated JDBC connection string: " + string);
    }

    @Nullable
    static DatabaseType peekDatabaseTypeFromRequest(@NotNull HttpServletRequest httpServletRequest, @NotNull Logger logger) {
        try {
            if (httpServletRequest == null) {
                StartupServlet.$$$reportNull$$$0(64);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw StartupServlet.a(illegalArgumentException);
        }
        try {
            if (logger == null) {
                StartupServlet.$$$reportNull$$$0(65);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw StartupServlet.a(illegalArgumentException);
        }
        String string = httpServletRequest.getParameter("dbType");
        try {
            if (string == null) {
                logger.warn("GoNewDatabase: the dbType is not specified");
                return null;
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw StartupServlet.a(illegalArgumentException);
        }
        try {
            return DatabaseType.valueOf((String)string.toUpperCase());
        }
        catch (IllegalArgumentException illegalArgumentException) {
            logger.warn("GoNewDatabase: wrong dbType code: '" + string + "'");
            return null;
        }
    }

    @Nullable
    private static JdbcConnectionString peekConnectionStringFromRequest(@NotNull HttpServletRequest httpServletRequest, @NotNull Logger logger) {
        boolean bl;
        JdbcConnectionString jdbcConnectionString;
        JdbcConnectionString jdbcConnectionString2;
        block25: {
            block24: {
                String string;
                String string2;
                boolean bl2;
                try {
                    if (httpServletRequest == null) {
                        StartupServlet.$$$reportNull$$$0(66);
                    }
                }
                catch (NoSuitableJdbcDrivers noSuitableJdbcDrivers) {
                    throw StartupServlet.a(noSuitableJdbcDrivers);
                }
                try {
                    if (logger == null) {
                        StartupServlet.$$$reportNull$$$0(67);
                    }
                }
                catch (NoSuitableJdbcDrivers noSuitableJdbcDrivers) {
                    throw StartupServlet.a(noSuitableJdbcDrivers);
                }
                String string3 = httpServletRequest.getParameter("dbType");
                try {
                    if (string3 == null) {
                        logger.warn("GoNewDatabase: the dbType is not specified");
                        return null;
                    }
                }
                catch (NoSuitableJdbcDrivers noSuitableJdbcDrivers) {
                    throw StartupServlet.a(noSuitableJdbcDrivers);
                }
                DatabaseType databaseType = StartupServlet.peekDatabaseTypeFromRequest(httpServletRequest, logger);
                try {
                    if (databaseType == null) {
                        return null;
                    }
                }
                catch (NoSuitableJdbcDrivers noSuitableJdbcDrivers) {
                    throw StartupServlet.a(noSuitableJdbcDrivers);
                }
                String string4 = httpServletRequest.getParameter("connHost");
                String string5 = httpServletRequest.getParameter("connInst");
                String string6 = httpServletRequest.getParameter("connDB");
                String string7 = httpServletRequest.getParameter("connIntegratedSecurity");
                String string8 = httpServletRequest.getParameter("connUser");
                String string9 = httpServletRequest.getParameter("connPwd");
                try {
                    bl2 = databaseType == DatabaseType.MSSQL;
                }
                catch (NoSuitableJdbcDrivers noSuitableJdbcDrivers) {
                    throw StartupServlet.a(noSuitableJdbcDrivers);
                }
                boolean bl3 = bl2;
                try {
                    string2 = databaseType == DatabaseType.HSQLDB2 ? "file" : null;
                }
                catch (NoSuitableJdbcDrivers noSuitableJdbcDrivers) {
                    throw StartupServlet.a(noSuitableJdbcDrivers);
                }
                String string10 = string2;
                String string11 = StringUtil.nullIfEmpty((String)StringUtil.peekNthDelimitedField((String)string4, (char)':', (int)1, (boolean)true));
                String string12 = StringUtil.nullIfEmpty((String)StringUtil.peekNthDelimitedField((String)string4, (char)':', (int)2, (boolean)true));
                try {
                    string = databaseType == DatabaseType.HSQLDB2 ? "$TEAMCITY_SYSTEM_PATH/buildserver" : string6;
                }
                catch (NoSuitableJdbcDrivers noSuitableJdbcDrivers) {
                    throw StartupServlet.a(noSuitableJdbcDrivers);
                }
                String string13 = string;
                string5 = StringUtil.nullIfEmpty((String)string5);
                string12 = StringUtil.nullIfEmpty((String)string12);
                string8 = StringUtil.nullIfEmpty((String)string8);
                string9 = StringUtil.nullIfEmpty((String)string9);
                jdbcConnectionString2 = new JdbcConnectionString(databaseType, bl3, string10, string11, string12, string5, string13, string8, string9);
                try {
                    try {
                        try {
                            jdbcConnectionString = jdbcConnectionString2;
                            if (string7 == null || string7.isEmpty()) break block24;
                        }
                        catch (NoSuitableJdbcDrivers noSuitableJdbcDrivers) {
                            throw StartupServlet.a(noSuitableJdbcDrivers);
                        }
                        if (string7.charAt(0) != '1') break block24;
                    }
                    catch (NoSuitableJdbcDrivers noSuitableJdbcDrivers) {
                        throw StartupServlet.a(noSuitableJdbcDrivers);
                    }
                    bl = true;
                    break block25;
                }
                catch (NoSuitableJdbcDrivers noSuitableJdbcDrivers) {
                    throw StartupServlet.a(noSuitableJdbcDrivers);
                }
            }
            bl = false;
        }
        jdbcConnectionString.setIntegratedSecurity(bl);
        return jdbcConnectionString2;
    }

    static {
        boolean bl;
        try {
            bl = !StartupServlet.class.desiredAssertionStatus();
        }
        catch (NoSuitableJdbcDrivers noSuitableJdbcDrivers) {
            throw StartupServlet.a(noSuitableJdbcDrivers);
        }
        $assertionsDisabled = bl;
        ourPropertyUtilsBean = new PropertyUtilsBean();
    }

    /*
     * Exception decompiling
     */
    private static /* synthetic */ void $$$reportNull$$$0(int var0) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 6[SWITCH]
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:435)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:484)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1050)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doClass(Driver.java:84)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:78)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    private static Throwable a(Throwable throwable) {
        return throwable;
    }
}
