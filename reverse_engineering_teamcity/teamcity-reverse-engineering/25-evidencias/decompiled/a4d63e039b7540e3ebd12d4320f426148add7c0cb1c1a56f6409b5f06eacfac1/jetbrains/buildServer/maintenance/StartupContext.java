/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableSortedSet
 *  com.intellij.openapi.diagnostic.Logger
 *  javax.annotation.concurrent.GuardedBy
 *  javax.annotation.concurrent.ThreadSafe
 *  jetbrains.buildServer.DevelopmentMode
 *  jetbrains.buildServer.https.HttpsConfigurator
 *  jetbrains.buildServer.log.Loggers
 *  jetbrains.buildServer.maintenance.StartupContext$StartupPhase
 *  jetbrains.buildServer.maintenance.StartupContext$StartupPhaseRecording
 *  jetbrains.buildServer.maintenance.StartupStage
 *  jetbrains.buildServer.maintenance.TeamCityDataDirectoryManager
 *  jetbrains.buildServer.maintenance.XmxWarningUtil
 *  jetbrains.buildServer.maintenance.configs.CentralRepositoryStartupUtils
 *  jetbrains.buildServer.serverSide.CurrentNodeInfo
 *  jetbrains.buildServer.serverSide.InstallationsManager
 *  jetbrains.buildServer.serverSide.LicenseAgreement
 *  jetbrains.buildServer.serverSide.NodeSpecificConfigs
 *  jetbrains.buildServer.serverSide.PreferencesInstallationsManagerImpl
 *  jetbrains.buildServer.serverSide.ServerPaths
 *  jetbrains.buildServer.serverSide.StartupConfiguration
 *  jetbrains.buildServer.serverSide.StubInstallationsManagerImpl
 *  jetbrains.buildServer.serverSide.TeamCityProperties
 *  jetbrains.buildServer.serverSide.db.DatabasePropertiesFile
 *  jetbrains.buildServer.serverSide.db.DatabaseType
 *  jetbrains.buildServer.serverSide.db.JdbcConnectionString
 *  jetbrains.buildServer.serverSide.db.JdbcDriverDownloader$ProgressListener
 *  jetbrains.buildServer.serverSide.db.SessionInfo
 *  jetbrains.buildServer.serverSide.db.TeamCityDatabaseManager
 *  jetbrains.buildServer.serverSide.db.jdbcLoader.JdbcDrivers
 *  jetbrains.buildServer.serverSide.impl.SecondaryNodeDBAccess
 *  jetbrains.buildServer.serverSide.impl.SecondaryNodeSecurityManager
 *  jetbrains.buildServer.serverSide.maintenance.BackupConfig
 *  jetbrains.buildServer.serverSide.maintenance.BackupProcess
 *  jetbrains.buildServer.serverSide.maintenance.BackupProcessInfo
 *  jetbrains.buildServer.util.Dates
 *  jetbrains.buildServer.util.FileUtil
 *  jetbrains.buildServer.util.Property
 *  jetbrains.buildServer.util.RandomGenerator
 *  jetbrains.buildServer.util.StringUtil
 *  jetbrains.buildServer.util.ThreadUtil
 *  jetbrains.buildServer.util.TimeService
 *  jetbrains.buildServer.version.ServerVersionHolder
 *  jetbrains.buildServer.version.ServerVersionInfo
 *  jetbrains.buildServer.xstream.JdbcDriver
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package jetbrains.buildServer.maintenance;

import com.google.common.collect.ImmutableSortedSet;
import com.intellij.openapi.diagnostic.Logger;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;
import jetbrains.buildServer.DevelopmentMode;
import jetbrains.buildServer.https.HttpsConfigurator;
import jetbrains.buildServer.log.Loggers;
import jetbrains.buildServer.maintenance.StartupContext;
import jetbrains.buildServer.maintenance.StartupStage;
import jetbrains.buildServer.maintenance.TeamCityDataDirectoryManager;
import jetbrains.buildServer.maintenance.XmxWarningUtil;
import jetbrains.buildServer.maintenance.configs.CentralRepositoryStartupUtils;
import jetbrains.buildServer.serverSide.CurrentNodeInfo;
import jetbrains.buildServer.serverSide.InstallationsManager;
import jetbrains.buildServer.serverSide.LicenseAgreement;
import jetbrains.buildServer.serverSide.NodeSpecificConfigs;
import jetbrains.buildServer.serverSide.PreferencesInstallationsManagerImpl;
import jetbrains.buildServer.serverSide.ServerPaths;
import jetbrains.buildServer.serverSide.StartupConfiguration;
import jetbrains.buildServer.serverSide.StubInstallationsManagerImpl;
import jetbrains.buildServer.serverSide.TeamCityProperties;
import jetbrains.buildServer.serverSide.db.DatabasePropertiesFile;
import jetbrains.buildServer.serverSide.db.DatabaseType;
import jetbrains.buildServer.serverSide.db.JdbcConnectionString;
import jetbrains.buildServer.serverSide.db.JdbcDriverDownloader;
import jetbrains.buildServer.serverSide.db.SessionInfo;
import jetbrains.buildServer.serverSide.db.TeamCityDatabaseManager;
import jetbrains.buildServer.serverSide.db.jdbcLoader.JdbcDrivers;
import jetbrains.buildServer.serverSide.impl.SecondaryNodeDBAccess;
import jetbrains.buildServer.serverSide.impl.SecondaryNodeSecurityManager;
import jetbrains.buildServer.serverSide.maintenance.BackupConfig;
import jetbrains.buildServer.serverSide.maintenance.BackupProcess;
import jetbrains.buildServer.serverSide.maintenance.BackupProcessInfo;
import jetbrains.buildServer.util.Dates;
import jetbrains.buildServer.util.FileUtil;
import jetbrains.buildServer.util.Property;
import jetbrains.buildServer.util.RandomGenerator;
import jetbrains.buildServer.util.StringUtil;
import jetbrains.buildServer.util.ThreadUtil;
import jetbrains.buildServer.util.TimeService;
import jetbrains.buildServer.version.ServerVersionHolder;
import jetbrains.buildServer.version.ServerVersionInfo;
import jetbrains.buildServer.xstream.JdbcDriver;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Exception performing whole class analysis ignored.
 */
@ThreadSafe
public final class StartupContext {
    public static final String TEAMCITY_READ_ONLY_NODE_IGNORE_UPGRADE_CHECK = "teamcity.readOnlyNode.ignoreUpgradeCheck";
    public static final String TEAMCITY_CACHES_PATH_PROP = "teamcity.caches.path";
    public static final String CUSTOM_BUNDLED_PLUGINS_PATH_PROP = "teamcity.webinf.plugins.path";
    @NotNull
    private final Logger myLogger;
    private final Map<String, String> myEnv;
    private final AtomicInteger myStateRevision;
    @NotNull
    @GuardedBy(value="myStageLock")
    private final Deque<StartupStage> myPreviousStages;
    @NotNull
    @GuardedBy(value="myStageLock")
    private StartupStage myCurrStage;
    @NotNull
    @GuardedBy(value="myStageLock")
    private final Deque<StartupStage> myNextStages;
    @NotNull
    private final Object myStageLock;
    @GuardedBy(value="myStageLock")
    private boolean myStarting;
    @GuardedBy(value="myStageLock")
    private boolean myShutdownRequested;
    @Nullable
    private String myServletRealPath;
    @Nullable
    private ServerPaths myServerPaths;
    @Nullable
    private TeamCityDataDirectoryManager myDataDirManager;
    private boolean myAllowOverrideDataDirectory;
    @Nullable
    private String myDataDirectoryPathFromUI;
    @Nullable
    private String mySuggestedFirstStartDataDirectoryPath;
    private String myIPAddress;
    private String myServletContainerInfo;
    private boolean myIsStartingAsMain;
    private boolean myAskedForNodeType;
    private boolean myDevelopmentMode;
    private boolean myBypassingMaintenanceQuestions;
    private volatile boolean myFirstStart;
    @Nullable
    private Set<Object> myAdminsLoggedIn;
    @Nullable
    private volatile DatabasePropertiesFile myDatabasePropertiesFile;
    @Nullable
    private String myInternalDatabaseFileVersion;
    private boolean myDatabaseExternal;
    @NotNull
    private JdbcConnectionString myConnectionString;
    @Nullable
    private JdbcDrivers myJdbcDrivers;
    @Nullable
    private TeamCityDatabaseManager myDatabaseManager;
    @NotNull
    private final InstallationsManager myInstallationsManager;
    @NotNull
    private final ServerVersionInfo myProductVersion;
    private volatile int mySoftwareVersion;
    private volatile int myDataDirVersion;
    private volatile int myDatabaseVersion;
    private volatile boolean myIsIncompatibleDatabaseVersion;
    private volatile boolean myDatabaseVersionIsGuessed;
    private volatile boolean myDatabaseAccessible;
    @NotNull
    private volatile Set<String> myDatabaseTables;
    private volatile boolean mySchemaBelongsToTeamcity;
    private volatile boolean myDatabaseSettingsFromUISpecified;
    private volatile boolean myBackupPossible;
    @Nullable
    private volatile BackupConfig myBackupConfig;
    @Nullable
    private volatile BackupProcess myBackupProcess;
    @Nullable
    private volatile BackupProcessInfo myBackupProcessInfo;
    private final List<String> restoreProcessInfo;
    @Nullable
    private Path backupFilePath;
    @Nullable
    private JdbcConnectionString connectionStringFromBackup;
    private volatile boolean cancelRestoreRequested;
    private volatile boolean myMaintenanceAsked;
    private volatile String myRecommendedMemorySettings;
    @NotNull
    private final LinkedList<Throwable> myExceptions;
    private final Date myServerStartupTimestamp;
    private volatile Date myServerStartupFinishedTimestamp;
    @NotNull
    @GuardedBy(value="myStartupPhases")
    private final Map<String, StartupPhase> myStartupPhases;
    private volatile Collection<String> myPreviousInstallationFiles;
    private volatile String myCurrentJavaVersion;
    private volatile String myCurrentJavaLocation;
    private volatile boolean myUnsupoprtedJava;
    private volatile boolean myUnsupportedServerRole;
    private volatile String myServerRole;
    private volatile String myDistributionType;
    private volatile String myUUID;
    private volatile String mySelectedDirError;
    private volatile SessionInfo myCompetitorSession;
    private Integer myPort;
    private String myScheme;
    private String myServletContextPath;
    @Nullable
    private volatile DatabaseType myJdbcDriverType;
    @Nullable
    private volatile JdbcDriverDownloader.ProgressListener myJdbcDriverDownloadProgressListener;
    @NotNull
    private final Map<DatabaseType, JdbcDriver> myJdbcDriverMetadata;
    private volatile boolean restoreProcess;
    @Nullable
    private HttpsConfigurator myConfigurator;
    @Nullable
    private LicenseAgreement myLicenseAgreement;
    private volatile boolean myUsageStatisticsEnabled;
    private volatile boolean myLicenseAgreementAccepted;
    private volatile boolean myDatabaseCreatedOnStartup;
    @NotNull
    private final AtomicBoolean myDataDirPrefilled;
    private volatile String myKeysSource;
    public final Property<String> myMaintenanceAuthenticationToken;
    static final /* synthetic */ boolean $assertionsDisabled;

    public StartupContext(@NotNull Logger logger, @NotNull TimeService timeService) {
        if (logger == null) {
            StartupContext.$$$reportNull$$$0(0);
        }
        if (timeService == null) {
            StartupContext.$$$reportNull$$$0(1);
        }
        this(logger, timeService, System.getenv());
    }

    public StartupContext(@NotNull Logger logger, @NotNull TimeService timeService, @NotNull Map<String, String> map) {
        block7: {
            block6: {
                if (logger == null) {
                    StartupContext.$$$reportNull$$$0(2);
                }
                if (timeService == null) {
                    StartupContext.$$$reportNull$$$0(3);
                }
                if (map == null) {
                    StartupContext.$$$reportNull$$$0(4);
                }
                this.myStateRevision = new AtomicInteger(1);
                this.myPreviousStages = new LinkedList<StartupStage>();
                this.myCurrStage = StartupStage.INITIAL_STAGE;
                this.myNextStages = new LinkedList<StartupStage>();
                this.myStageLock = new Object();
                this.myStarting = true;
                this.myServletRealPath = null;
                this.myServerPaths = null;
                this.myDataDirManager = null;
                this.myAllowOverrideDataDirectory = false;
                this.myDataDirectoryPathFromUI = null;
                this.myIsStartingAsMain = true;
                this.myAskedForNodeType = false;
                this.myDevelopmentMode = false;
                this.myBypassingMaintenanceQuestions = false;
                this.myFirstStart = false;
                this.myAdminsLoggedIn = null;
                this.myDatabasePropertiesFile = null;
                this.myConnectionString = new JdbcConnectionString(DatabaseType.UNKNOWN, false);
                this.myProductVersion = ServerVersionHolder.getVersion();
                this.mySoftwareVersion = 0;
                this.myDataDirVersion = 0;
                this.myDatabaseVersion = 0;
                this.myDatabaseVersionIsGuessed = false;
                this.myDatabaseAccessible = false;
                this.myDatabaseTables = Collections.emptySet();
                this.mySchemaBelongsToTeamcity = false;
                this.myDatabaseSettingsFromUISpecified = false;
                this.myBackupPossible = false;
                this.myBackupConfig = null;
                this.myBackupProcess = null;
                this.myBackupProcessInfo = null;
                this.restoreProcessInfo = new CopyOnWriteArrayList<String>();
                this.backupFilePath = null;
                this.connectionStringFromBackup = null;
                this.cancelRestoreRequested = false;
                this.myMaintenanceAsked = false;
                this.myExceptions = new LinkedList();
                this.myStartupPhases = new LinkedHashMap<String, StartupPhase>();
                this.myPreviousInstallationFiles = new HashSet<String>();
                this.myJdbcDriverMetadata = new ConcurrentHashMap<DatabaseType, JdbcDriver>();
                this.myUsageStatisticsEnabled = false;
                this.myLicenseAgreementAccepted = false;
                this.myDatabaseCreatedOnStartup = false;
                this.myDataDirPrefilled = new AtomicBoolean(false);
                this.myMaintenanceAuthenticationToken = new Property((Object)StartupContext.produceMaintenanceAuthenticationToken());
                this.myLogger = logger;
                this.myEnv = Collections.unmodifiableMap(map);
                this.myServerStartupTimestamp = new Date(timeService.now());
                if (CurrentNodeInfo.isMainNode()) {
                    try {
                        if (DevelopmentMode.isEnabled) break block6;
                        this.myInstallationsManager = new PreferencesInstallationsManagerImpl();
                        break block7;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw StartupContext.a(illegalStateException);
                    }
                }
            }
            this.myInstallationsManager = new StubInstallationsManagerImpl();
        }
    }

    public Map<String, String> getEnv() {
        return this.myEnv;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static String produceMaintenanceAuthenticationToken() {
        String string2 = TeamCityProperties.getPropertyOrNull((String)"teamcity.superUser.token");
        try {
            if (string2 != null) {
                return string2.trim();
            }
        }
        catch (IOException iOException) {
            throw StartupContext.a(iOException);
        }
        String string = StartupConfiguration.getProperty((String)"teamcity.oneTimeSuperUserToken");
        try {
            if (string == null) return Long.toString(StartupContext.generateMaintenanceAuthenticationToken());
            try {
                StartupConfiguration.removeProperty((String)"teamcity.oneTimeSuperUserToken");
                return string;
            }
            catch (IOException iOException) {
                Loggers.STARTUP.warnAndDebugDetails("Failed to cleanup one-time super user token", (Throwable)iOException);
                return string;
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupContext.a(illegalStateException);
        }
    }

    public static long generateMaintenanceAuthenticationToken() {
        return new RandomGenerator().getRandom().nextLong() & Long.MAX_VALUE | 0x1000000000000000L;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void proposeNextStage(@NotNull StartupStage startupStage, StartupStage ... startupStageArray) {
        try {
            if (startupStage == null) {
                StartupContext.$$$reportNull$$$0(5);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupContext.a(illegalStateException);
        }
        try {
            if (startupStageArray == null) {
                StartupContext.$$$reportNull$$$0(6);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupContext.a(illegalStateException);
        }
        Object object = this.myStageLock;
        synchronized (object) {
            this.proposeNextStageInternally(startupStage, startupStageArray);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void proposeNextStageIfCurrent(@NotNull StartupStage startupStage, @NotNull StartupStage startupStage2) {
        StartupStage startupStage3;
        try {
            if (startupStage == null) {
                StartupContext.$$$reportNull$$$0(7);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupContext.a(illegalStateException);
        }
        try {
            if (startupStage2 == null) {
                StartupContext.$$$reportNull$$$0(8);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupContext.a(illegalStateException);
        }
        Object object = this.myStageLock;
        synchronized (object) {
            startupStage3 = this.myCurrStage;
            try {
                if (startupStage3 == startupStage) {
                    this.proposeNextStageInternally(startupStage2, new StartupStage[0]);
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw StartupContext.a(illegalStateException);
            }
        }
        try {
            if (startupStage3 != startupStage) {
                this.myLogger.debug("Attempted to propose the " + String.valueOf(startupStage2) + " stage after the " + String.valueOf(startupStage) + " stage when the real current stage is " + String.valueOf(startupStage3) + ".");
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupContext.a(illegalStateException);
        }
    }

    @GuardedBy(value="myStageLock")
    private void proposeNextStageInternally(@NotNull StartupStage startupStage, StartupStage ... startupStageArray) {
        try {
            if (startupStage == null) {
                StartupContext.$$$reportNull$$$0(9);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupContext.a(illegalStateException);
        }
        try {
            if (startupStageArray == null) {
                StartupContext.$$$reportNull$$$0(10);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupContext.a(illegalStateException);
        }
        boolean bl = true;
        if (this.myShutdownRequested) {
            bl = startupStage.shuttingDown;
        }
        if (bl) {
            this.myNextStages.pollFirst();
            this.myNextStages.addFirst(startupStage);
            for (StartupStage startupStage2 : startupStageArray) {
                this.myNextStages.addLast(startupStage2);
            }
            this.myStateRevision.incrementAndGet();
            this.myLogger.debug("The next stage: " + startupStage.description);
            this.myStageLock.notifyAll();
        } else {
            this.myLogger.debug("Attempted to propose the " + String.valueOf(startupStage) + " stage but the server is in the shutting down mode.");
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void requestShutdown() {
        block9: {
            block8: {
                boolean bl;
                Object object = this.myStageLock;
                synchronized (object) {
                    bl = this.myShutdownRequested;
                    try {
                        if (!bl) {
                            this.myShutdownRequested = true;
                            this.myNextStages.pollFirst();
                            this.myNextStages.addFirst(StartupStage.SHUTTING_DOWN);
                            this.myStarting = false;
                            this.myStateRevision.incrementAndGet();
                            this.myStageLock.notifyAll();
                        }
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw StartupContext.a(illegalStateException);
                    }
                }
                try {
                    if (!bl) break block8;
                    this.myLogger.debug("Secondary server shutdown event received");
                    break block9;
                }
                catch (IllegalStateException illegalStateException) {
                    throw StartupContext.a(illegalStateException);
                }
            }
            this.myLogger.info("Server shutdown event received");
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void switchToTheNextStage() {
        String string;
        String string2;
        boolean bl;
        ArrayList<StartupStage> arrayList;
        StartupStage startupStage;
        ArrayList<StartupStage> arrayList2;
        block18: {
            block17: {
                Object object = this.myStageLock;
                synchronized (object) {
                    if (this.myNextStages.isEmpty()) {
                        throw new IllegalStateException("Startup stage " + String.valueOf(this.myCurrStage) + " has not set the next stage. It is probably an internal TeamCity error during startup logic.");
                    }
                    this.myPreviousStages.addLast(this.myCurrStage);
                    arrayList2 = new ArrayList<StartupStage>(this.myPreviousStages);
                    startupStage = this.myCurrStage = this.myNextStages.removeFirst();
                    arrayList = new ArrayList<StartupStage>(this.myNextStages);
                    this.myStateRevision.incrementAndGet();
                    this.myStageLock.notifyAll();
                }
                try {
                    try {
                        try {
                            try {
                                if (!startupStage.adminRequires || startupStage.active) break block17;
                            }
                            catch (IllegalStateException illegalStateException) {
                                throw StartupContext.a(illegalStateException);
                            }
                            if (startupStage == StartupStage.EXCEPTION) break block17;
                        }
                        catch (IllegalStateException illegalStateException) {
                            throw StartupContext.a(illegalStateException);
                        }
                        if (startupStage == StartupStage.FATAL_ERROR) break block17;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw StartupContext.a(illegalStateException);
                    }
                    bl = true;
                    break block18;
                }
                catch (IllegalStateException illegalStateException) {
                    throw StartupContext.a(illegalStateException);
                }
            }
            bl = false;
        }
        boolean bl2 = bl;
        try {
            string2 = startupStage.description;
            string = bl2 ? " (administrator login is required to proceed)" : "";
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupContext.a(illegalStateException);
        }
        String string3 = "Current stage: " + string2 + string;
        try {
            this.myLogger.info(string3);
            if (this.myLogger.isDebugEnabled()) {
                this.myLogger.debug(String.format("%s -> %s (current) -> %s", arrayList2, startupStage, arrayList));
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupContext.a(illegalStateException);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void waitForFollowingStage() {
        Object object = this.myStageLock;
        synchronized (object) {
            try {
                while (this.myNextStages.isEmpty()) {
                    this.waitForStageLock();
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw StartupContext.a(illegalStateException);
            }
        }
    }

    /*
     * Exception decompiling
     */
    void waitWhileProcessing(long var1_1) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [5[DOLOOP]], but top level block is 1[TRYBLOCK]
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:435)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:484)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doClass(Driver.java:84)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:78)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * Exception decompiling
     */
    void waitWhileProcessingInfinitely() {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Nonsensical loop would be emitted - failure
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.LoopIdentifier.considerAsDoLoopStart(LoopIdentifier.java:438)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.LoopIdentifier.identifyLoops1(LoopIdentifier.java:65)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:681)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doClass(Driver.java:84)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:78)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    @GuardedBy(value="myStageLock")
    private void waitForStageLock() {
        try {
            this.myStageLock.wait(3000L);
        }
        catch (InterruptedException interruptedException) {
            // empty catch block
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void setStarting(boolean bl) {
        Object object = this.myStageLock;
        synchronized (object) {
            this.myStarting = bl;
        }
    }

    void setDevelopmentMode(boolean bl) {
        this.myDevelopmentMode = bl;
    }

    void setBypassingMaintenanceQuestions(boolean bl) {
        this.myBypassingMaintenanceQuestions = bl;
    }

    void setFirstStart(boolean bl) {
        this.myFirstStart = bl;
    }

    void addAdminLoggedIn(@NotNull Object object) {
        try {
            if (object == null) {
                StartupContext.$$$reportNull$$$0(11);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupContext.a(illegalStateException);
        }
        try {
            if (this.myAdminsLoggedIn == null) {
                this.myAdminsLoggedIn = new HashSet<Object>();
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupContext.a(illegalStateException);
        }
        boolean bl = this.myAdminsLoggedIn.add(object);
        try {
            if (bl) {
                this.myStateRevision.incrementAndGet();
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupContext.a(illegalStateException);
        }
    }

    void setDataDir(@NotNull File file) throws IOException {
        String string;
        block16: {
            block13: {
                String string2;
                block15: {
                    block14: {
                        try {
                            if (file == null) {
                                StartupContext.$$$reportNull$$$0(12);
                            }
                        }
                        catch (IOException iOException) {
                            throw StartupContext.a(iOException);
                        }
                        string = System.getProperty("teamcity.caches.path");
                        try {
                            try {
                                if (!CentralRepositoryStartupUtils.isLoadingFromCentralRepositoryEnabled()) break block13;
                                if (string == null) break block14;
                            }
                            catch (IOException iOException) {
                                throw StartupContext.a(iOException);
                            }
                            string2 = string;
                            break block15;
                        }
                        catch (IOException iOException) {
                            throw StartupContext.a(iOException);
                        }
                    }
                    string2 = new File(NodeSpecificConfigs.getNodeDataDirectory((File)new File(file.getAbsolutePath())), "system" + File.separator + "caches").getAbsolutePath();
                }
                String string3 = string2;
                try {
                    if (CurrentNodeInfo.isMainNode()) {
                        CentralRepositoryStartupUtils.configureMainNodeSecurityManager((File)new File(file, "config"));
                    }
                }
                catch (IOException iOException) {
                    throw StartupContext.a(iOException);
                }
                this.myServerPaths = new ServerPaths(new File(file.getAbsolutePath()), CentralRepositoryStartupUtils.getCentralConfigsRepositoryCheckoutDir((String)string3));
                break block16;
            }
            this.myServerPaths = new ServerPaths(file.getAbsolutePath());
        }
        try {
            if (string != null) {
                this.initCustomCachesPath(this.myServerPaths, string);
            }
        }
        catch (IOException iOException) {
            throw StartupContext.a(iOException);
        }
        this.myDataDirManager = new TeamCityDataDirectoryManager(this.myServerPaths, this.myLogger);
        this.myDatabasePropertiesFile = new DatabasePropertiesFile(file, this.getEnv(), new File(this.myServerPaths.getConfigDir(), "database.properties"));
        this.myDataDirManager.setDatabasePropertiesFile(this.myDatabasePropertiesFile.getFile());
        this.myBackupPossible = false;
        this.myInternalDatabaseFileVersion = null;
        this.myDataDirVersion = 0;
        this.myStateRevision.incrementAndGet();
    }

    private void initCustomCachesPath(@NotNull ServerPaths serverPaths, @NotNull String string) throws IOException {
        try {
            if (serverPaths == null) {
                StartupContext.$$$reportNull$$$0(13);
            }
        }
        catch (IOException iOException) {
            throw StartupContext.a(iOException);
        }
        try {
            if (string == null) {
                StartupContext.$$$reportNull$$$0(14);
            }
        }
        catch (IOException iOException) {
            throw StartupContext.a(iOException);
        }
        File file = new File(string);
        SecondaryNodeSecurityManager.addWritablePath((File)file);
        FileUtil.createDir((File)file);
        String string2 = file.getCanonicalPath();
        serverPaths.setCachesDir(string2);
        this.myLogger.info("The TeamCity server caches directory is set to the path: " + string2);
    }

    public void setAllowOverrideDataDirectory(boolean bl) {
        this.myAllowOverrideDataDirectory = bl;
    }

    public void setSelectedDataDirPath(@NotNull String string) {
        block7: {
            try {
                if (string == null) {
                    StartupContext.$$$reportNull$$$0(15);
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw StartupContext.a(illegalStateException);
            }
            try {
                try {
                    if ($assertionsDisabled || this.myAllowOverrideDataDirectory) break block7;
                }
                catch (IllegalStateException illegalStateException) {
                    throw StartupContext.a(illegalStateException);
                }
                throw new AssertionError((Object)"Data directory location can't be changed.");
            }
            catch (IllegalStateException illegalStateException) {
                throw StartupContext.a(illegalStateException);
            }
        }
        this.myDataDirectoryPathFromUI = string;
    }

    public void setSuggestedFirstStartDataDirLocation(@NotNull String string) {
        try {
            if (string == null) {
                StartupContext.$$$reportNull$$$0(16);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupContext.a(illegalStateException);
        }
        this.mySuggestedFirstStartDataDirectoryPath = string;
    }

    void setIPAddress(String string) {
        this.myIPAddress = string;
        this.myStateRevision.incrementAndGet();
    }

    public boolean isStartingAsMain() {
        return this.myIsStartingAsMain;
    }

    public boolean isAskedForNodeType() {
        return this.myAskedForNodeType;
    }

    public void setAskedForNodeType(boolean bl) {
        this.myAskedForNodeType = bl;
    }

    void setStartingAsMain(boolean bl) {
        try {
            this.myIsStartingAsMain = bl;
            if (bl) {
                CurrentNodeInfo.setMainNode((boolean)true);
                SecondaryNodeSecurityManager.reset();
                SecondaryNodeDBAccess.reset();
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupContext.a(illegalStateException);
        }
    }

    void setServletContainerInfo(String string) {
        this.myServletContainerInfo = string;
        this.myStateRevision.incrementAndGet();
    }

    void setServletRealPath(@Nullable String string) {
        this.myServletRealPath = string;
        this.myStateRevision.incrementAndGet();
    }

    void refreshDatabasePropertiesFile() {
        boolean bl;
        DatabasePropertiesFile databasePropertiesFile;
        block6: {
            try {
                try {
                    if ($assertionsDisabled || this.myDatabasePropertiesFile != null) break block6;
                }
                catch (IllegalStateException illegalStateException) {
                    throw StartupContext.a(illegalStateException);
                }
                throw new AssertionError();
            }
            catch (IllegalStateException illegalStateException) {
                throw StartupContext.a(illegalStateException);
            }
        }
        try {
            databasePropertiesFile = this.myDatabasePropertiesFile;
            bl = this.myConnectionString.getDBType() == DatabaseType.UNKNOWN;
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupContext.a(illegalStateException);
        }
        databasePropertiesFile.refreshState(bl);
        this.myStateRevision.incrementAndGet();
    }

    public void setInternalDatabaseFileVersion(@Nullable String string) {
        try {
            if (StringUtil.areEqual((String)this.myInternalDatabaseFileVersion, (String)string)) {
                return;
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupContext.a(illegalStateException);
        }
        this.myInternalDatabaseFileVersion = string;
        this.myStateRevision.incrementAndGet();
    }

    void setDatabaseExternal(boolean bl) {
        try {
            if (this.myDatabaseExternal == bl) {
                return;
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupContext.a(illegalStateException);
        }
        this.myDatabaseExternal = bl;
        this.myStateRevision.incrementAndGet();
    }

    public void setConnectionString(@NotNull JdbcConnectionString jdbcConnectionString) {
        try {
            if (jdbcConnectionString == null) {
                StartupContext.$$$reportNull$$$0(17);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupContext.a(illegalStateException);
        }
        this.myConnectionString = jdbcConnectionString.clone();
        this.myStateRevision.incrementAndGet();
    }

    public void setJdbcDrivers(@Nullable JdbcDrivers jdbcDrivers) {
        try {
            if (jdbcDrivers == this.myJdbcDrivers) {
                return;
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupContext.a(illegalStateException);
        }
        try {
            if (this.myDatabaseManager != null) {
                this.myDatabaseManager.disconnect();
                this.myDatabaseManager = null;
                ThreadUtil.sleep((long)100L);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupContext.a(illegalStateException);
        }
        this.myJdbcDrivers = jdbcDrivers;
        this.myStateRevision.incrementAndGet();
    }

    public void setDatabaseManager(@Nullable TeamCityDatabaseManager teamCityDatabaseManager) {
        this.myDatabaseManager = teamCityDatabaseManager;
        this.myStateRevision.incrementAndGet();
    }

    void setSoftwareVersion(int n) {
        try {
            if (this.mySoftwareVersion == n) {
                return;
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupContext.a(illegalStateException);
        }
        this.mySoftwareVersion = n;
        this.myStateRevision.incrementAndGet();
    }

    void setDataDirVersion(int n) {
        try {
            if (this.myDataDirVersion == n) {
                return;
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupContext.a(illegalStateException);
        }
        this.myDataDirVersion = n;
        this.myStateRevision.incrementAndGet();
    }

    void setDatabaseVersion(int n) {
        try {
            if (this.myDatabaseVersion == n) {
                return;
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupContext.a(illegalStateException);
        }
        this.myDatabaseVersion = n;
        this.myStateRevision.incrementAndGet();
    }

    public boolean isIncompatibleDatabaseVersion() {
        return this.myIsIncompatibleDatabaseVersion;
    }

    public void setIncompatibleDatabaseVersion(boolean bl) {
        this.myIsIncompatibleDatabaseVersion = bl;
    }

    void setDatabaseVersionIsGuessed(boolean bl) {
        try {
            if (this.myDatabaseVersionIsGuessed == bl) {
                return;
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupContext.a(illegalStateException);
        }
        this.myDatabaseVersionIsGuessed = bl;
        this.myStateRevision.incrementAndGet();
    }

    void setDatabaseAccessible(boolean bl) {
        try {
            if (this.myDatabaseAccessible == bl) {
                return;
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupContext.a(illegalStateException);
        }
        this.myDatabaseAccessible = bl;
        this.myStateRevision.incrementAndGet();
    }

    public void setDatabaseTables(@NotNull Set<String> set) {
        try {
            if (set == null) {
                StartupContext.$$$reportNull$$$0(18);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupContext.a(illegalStateException);
        }
        this.myDatabaseTables = ImmutableSortedSet.copyOf(set);
        this.myStateRevision.incrementAndGet();
    }

    void setSchemaBelongsToTeamcity(boolean bl) {
        try {
            if (this.mySchemaBelongsToTeamcity == bl) {
                return;
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupContext.a(illegalStateException);
        }
        this.mySchemaBelongsToTeamcity = bl;
        this.myStateRevision.incrementAndGet();
    }

    public void setDatabaseSettingsFromUISpecified(boolean bl) {
        this.myDatabaseSettingsFromUISpecified = bl;
        this.myStateRevision.incrementAndGet();
    }

    void setBackupPossible(boolean bl) {
        this.myBackupPossible = bl;
    }

    public void setBackupConfig(@Nullable BackupConfig backupConfig) {
        this.myBackupConfig = backupConfig;
    }

    public void setBackupProcess(@Nullable BackupProcess backupProcess) {
        this.myBackupProcess = backupProcess;
    }

    public void setBackupProcessInfo(@Nullable BackupProcessInfo backupProcessInfo) {
        this.myBackupProcessInfo = backupProcessInfo;
    }

    public void setBackupFilePath(@Nullable Path path) {
        this.backupFilePath = path;
    }

    public void setConnectionStringFromBackup(@Nullable JdbcConnectionString jdbcConnectionString) {
        this.connectionStringFromBackup = jdbcConnectionString;
    }

    public void setCancelRestoreRequested(boolean bl) {
        this.cancelRestoreRequested = bl;
    }

    public void addException(@NotNull Throwable throwable) {
        try {
            if (throwable == null) {
                StartupContext.$$$reportNull$$$0(19);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupContext.a(illegalStateException);
        }
        this.myExceptions.add(throwable);
        this.myStateRevision.incrementAndGet();
    }

    void clearExceptions() {
        int n = this.myExceptions.size();
        try {
            if (n > 0) {
                this.myLogger.debug("Clearing " + n + " exception(s).");
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupContext.a(illegalStateException);
        }
        this.myExceptions.clear();
        this.myStateRevision.incrementAndGet();
    }

    public void setMaintenanceAsked(boolean bl) {
        String string;
        Logger logger;
        try {
            if (this.myMaintenanceAsked == bl) {
                return;
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupContext.a(illegalStateException);
        }
        try {
            this.myMaintenanceAsked = bl;
            this.myStateRevision.incrementAndGet();
            logger = this.myLogger;
            string = bl ? "Asked for maintenance" : "No more asking for maintenance";
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupContext.a(illegalStateException);
        }
        logger.debug(string);
    }

    public void setPreviousInstallationFiles(Collection<String> collection) {
        this.myPreviousInstallationFiles = collection;
    }

    public void setUnsupportedJava(@NotNull String string, @NotNull String string2) {
        try {
            if (string == null) {
                StartupContext.$$$reportNull$$$0(20);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupContext.a(illegalStateException);
        }
        try {
            if (string2 == null) {
                StartupContext.$$$reportNull$$$0(21);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupContext.a(illegalStateException);
        }
        this.myCurrentJavaVersion = string;
        this.myCurrentJavaLocation = string2;
        this.myUnsupoprtedJava = true;
    }

    public void setUnsupportedServerRole(@NotNull String string) {
        try {
            if (string == null) {
                StartupContext.$$$reportNull$$$0(22);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupContext.a(illegalStateException);
        }
        this.myUnsupportedServerRole = true;
        this.myServerRole = string;
    }

    public int getStateRevision() {
        return this.myStateRevision.intValue();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NotNull
    public StartupStage getPreviousStage() {
        StartupStage startupStage;
        StartupStage startupStage2;
        Object object = this.myStageLock;
        synchronized (object) {
            startupStage2 = this.myPreviousStages.peekLast();
        }
        try {
            if (startupStage2 == null) {
                throw new IllegalStateException(String.format("Previous stage has not yet been set: %s -> %s -> %s", null, this.getCurrStage(), this.getNextStage()));
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupContext.a(illegalStateException);
        }
        try {
            startupStage = startupStage2;
            if (startupStage == null) {
                StartupContext.$$$reportNull$$$0(23);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupContext.a(illegalStateException);
        }
        return startupStage;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NotNull
    public StartupStage getCurrStage() {
        StartupStage startupStage;
        StartupStage startupStage2;
        Object object = this.myStageLock;
        synchronized (object) {
            startupStage2 = this.myCurrStage;
        }
        try {
            startupStage = startupStage2;
            if (startupStage == null) {
                StartupContext.$$$reportNull$$$0(24);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupContext.a(illegalStateException);
        }
        return startupStage;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Nullable
    public StartupStage getNextStage() {
        StartupStage startupStage;
        Object object = this.myStageLock;
        synchronized (object) {
            startupStage = this.myNextStages.peekFirst();
        }
        return startupStage;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean isStarting() {
        boolean bl;
        Object object = this.myStageLock;
        synchronized (object) {
            bl = this.myStarting;
        }
        return bl;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean isShutdownRequested() {
        boolean bl;
        Object object = this.myStageLock;
        synchronized (object) {
            bl = this.myShutdownRequested;
        }
        return bl;
    }

    @NotNull
    Logger getLogger() {
        Logger logger;
        try {
            logger = this.myLogger;
            if (logger == null) {
                StartupContext.$$$reportNull$$$0(25);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupContext.a(illegalStateException);
        }
        return logger;
    }

    @Nullable
    public String getDataDirPathSelectedFromUI() {
        return this.myDataDirectoryPathFromUI;
    }

    @NotNull
    public String getSuggestedFirstStartDataDirectoryPath() {
        String string;
        String string2;
        block13: {
            String string3;
            block14: {
                block11: {
                    String string4;
                    block12: {
                        try {
                            try {
                                if (this.myDataDirectoryPathFromUI == null) break block11;
                                string4 = this.myDataDirectoryPathFromUI;
                                if (string4 != null) break block12;
                            }
                            catch (IllegalStateException illegalStateException) {
                                throw StartupContext.a(illegalStateException);
                            }
                            StartupContext.$$$reportNull$$$0(26);
                        }
                        catch (IllegalStateException illegalStateException) {
                            throw StartupContext.a(illegalStateException);
                        }
                    }
                    return string4;
                }
                string2 = this.mySuggestedFirstStartDataDirectoryPath;
                try {
                    try {
                        if (string2 != null) break block13;
                        string3 = this.getDataDirManager().getRootDir().getAbsolutePath();
                        if (string3 != null) break block14;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw StartupContext.a(illegalStateException);
                    }
                    StartupContext.$$$reportNull$$$0(27);
                }
                catch (IllegalStateException illegalStateException) {
                    throw StartupContext.a(illegalStateException);
                }
            }
            return string3;
        }
        try {
            string = string2;
            if (string == null) {
                StartupContext.$$$reportNull$$$0(28);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupContext.a(illegalStateException);
        }
        return string;
    }

    public boolean isAllowOverrideDataDirectory() {
        return this.myAllowOverrideDataDirectory;
    }

    public boolean isDataDirSpecified() {
        boolean bl;
        block5: {
            block4: {
                try {
                    try {
                        if (this.myServerPaths == null || this.myDataDirManager == null) break block4;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw StartupContext.a(illegalStateException);
                    }
                    bl = true;
                    break block5;
                }
                catch (IllegalStateException illegalStateException) {
                    throw StartupContext.a(illegalStateException);
                }
            }
            bl = false;
        }
        return bl;
    }

    @NotNull
    public ServerPaths getServerPaths() {
        ServerPaths serverPaths;
        block7: {
            try {
                try {
                    if ($assertionsDisabled || this.myServerPaths != null) break block7;
                }
                catch (IllegalStateException illegalStateException) {
                    throw StartupContext.a(illegalStateException);
                }
                throw new AssertionError((Object)"TeamCity Data Directory is not specified yet");
            }
            catch (IllegalStateException illegalStateException) {
                throw StartupContext.a(illegalStateException);
            }
        }
        try {
            serverPaths = this.myServerPaths;
            if (serverPaths == null) {
                StartupContext.$$$reportNull$$$0(29);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupContext.a(illegalStateException);
        }
        return serverPaths;
    }

    @NotNull
    public TeamCityDataDirectoryManager getDataDirManager() {
        TeamCityDataDirectoryManager teamCityDataDirectoryManager;
        block7: {
            try {
                try {
                    if ($assertionsDisabled || this.myDataDirManager != null) break block7;
                }
                catch (IllegalStateException illegalStateException) {
                    throw StartupContext.a(illegalStateException);
                }
                throw new AssertionError((Object)"TeamCity Data Directory is not specified yet");
            }
            catch (IllegalStateException illegalStateException) {
                throw StartupContext.a(illegalStateException);
            }
        }
        try {
            teamCityDataDirectoryManager = this.myDataDirManager;
            if (teamCityDataDirectoryManager == null) {
                StartupContext.$$$reportNull$$$0(30);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupContext.a(illegalStateException);
        }
        return teamCityDataDirectoryManager;
    }

    public String getIPAddress() {
        return this.myIPAddress;
    }

    public String getServletContainerInfo() {
        return this.myServletContainerInfo;
    }

    @Nullable
    public String getServletRealPath() {
        String string;
        try {
            string = this.myServletRealPath != null ? FileUtil.getCanonicalFile((File)new File(this.myServletRealPath)).getPath() : null;
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupContext.a(illegalStateException);
        }
        return string;
    }

    public boolean isDevelopmentMode() {
        return this.myDevelopmentMode;
    }

    public boolean isFirstStart() {
        return this.myFirstStart;
    }

    public boolean isBypassingMaintenanceQuestions() {
        return this.myBypassingMaintenanceQuestions;
    }

    public int getCountOfAdminsLoggedIn() {
        int n;
        try {
            n = this.myAdminsLoggedIn == null ? 0 : this.myAdminsLoggedIn.size();
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupContext.a(illegalStateException);
        }
        return n;
    }

    @Nullable
    public DatabasePropertiesFile getDatabasePropertiesFile() {
        return this.myDatabasePropertiesFile;
    }

    public boolean isDatabaseExplicitlyConfigured() {
        boolean bl;
        block5: {
            block4: {
                DatabasePropertiesFile databasePropertiesFile = this.myDatabasePropertiesFile;
                try {
                    try {
                        if (databasePropertiesFile == null || !databasePropertiesFile.isConfigurationPresent()) break block4;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw StartupContext.a(illegalStateException);
                    }
                    bl = true;
                    break block5;
                }
                catch (IllegalStateException illegalStateException) {
                    throw StartupContext.a(illegalStateException);
                }
            }
            bl = false;
        }
        return bl;
    }

    @Deprecated
    public boolean isDatabasePropertiesFileExists() {
        boolean bl;
        block5: {
            block4: {
                DatabasePropertiesFile databasePropertiesFile = this.myDatabasePropertiesFile;
                try {
                    try {
                        if (databasePropertiesFile == null || !databasePropertiesFile.isFileExists()) break block4;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw StartupContext.a(illegalStateException);
                    }
                    bl = true;
                    break block5;
                }
                catch (IllegalStateException illegalStateException) {
                    throw StartupContext.a(illegalStateException);
                }
            }
            bl = false;
        }
        return bl;
    }

    public boolean isInternalDatabaseFileExists() {
        boolean bl;
        try {
            bl = this.myInternalDatabaseFileVersion != null;
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupContext.a(illegalStateException);
        }
        return bl;
    }

    @Nullable
    public String getInternalDatabaseFileVersion() {
        return this.myInternalDatabaseFileVersion;
    }

    public boolean isDatabaseExternal() {
        return this.myDatabaseExternal;
    }

    @NotNull
    public JdbcConnectionString getConnectionString() {
        JdbcConnectionString jdbcConnectionString;
        try {
            jdbcConnectionString = this.myConnectionString;
            if (jdbcConnectionString == null) {
                StartupContext.$$$reportNull$$$0(31);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupContext.a(illegalStateException);
        }
        return jdbcConnectionString;
    }

    @Nullable
    public JdbcDrivers getJdbcDrivers() {
        return this.myJdbcDrivers;
    }

    @Nullable
    public TeamCityDatabaseManager getDatabaseManager() {
        return this.myDatabaseManager;
    }

    @NotNull
    public ServerVersionInfo getProductVersion() {
        ServerVersionInfo serverVersionInfo;
        try {
            serverVersionInfo = this.myProductVersion;
            if (serverVersionInfo == null) {
                StartupContext.$$$reportNull$$$0(32);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupContext.a(illegalStateException);
        }
        return serverVersionInfo;
    }

    public int getSoftwareVersion() {
        return this.mySoftwareVersion;
    }

    public int getDataDirVersion() {
        return this.myDataDirVersion;
    }

    public int getDatabaseVersion() {
        return this.myDatabaseVersion;
    }

    public boolean showJava21UpgradeWarning() {
        boolean bl;
        try {
            bl = this.myDatabaseVersion < 1035;
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupContext.a(illegalStateException);
        }
        return bl;
    }

    public String getDatabaseVersionString() {
        String string;
        try {
            string = this.myDatabaseVersionIsGuessed ? "probably " : "";
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupContext.a(illegalStateException);
        }
        return "Database version is " + string + this.myDatabaseVersion;
    }

    public boolean isDatabaseVersionIsGuessed() {
        return this.myDatabaseVersionIsGuessed;
    }

    public boolean isDatabaseAccessible() {
        return this.myDatabaseAccessible;
    }

    @NotNull
    public Set<String> getDatabaseTables() {
        Set<String> set;
        try {
            set = this.myDatabaseTables;
            if (set == null) {
                StartupContext.$$$reportNull$$$0(33);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupContext.a(illegalStateException);
        }
        return set;
    }

    public int getCountOfTables() {
        return this.myDatabaseTables.size();
    }

    public boolean isSchemaBelongsToTeamcity() {
        return this.mySchemaBelongsToTeamcity;
    }

    public boolean isDatabaseSettingsFromUISpecified() {
        return this.myDatabaseSettingsFromUISpecified;
    }

    public boolean isBackupPossible() {
        return this.myBackupPossible;
    }

    @Nullable
    public BackupConfig getBackupConfig() {
        return this.myBackupConfig;
    }

    @Nullable
    public BackupProcess getBackupProcess() {
        return this.myBackupProcess;
    }

    @Nullable
    public BackupProcessInfo getBackupProcessInfo() {
        return this.myBackupProcessInfo;
    }

    @NotNull
    public List<String> getRestoreProcessInfo() {
        List<String> list;
        try {
            list = this.restoreProcessInfo;
            if (list == null) {
                StartupContext.$$$reportNull$$$0(34);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupContext.a(illegalStateException);
        }
        return list;
    }

    @Nullable
    public Path getBackupFilePath() {
        return this.backupFilePath;
    }

    @Nullable
    public JdbcConnectionString getConnectionStringFromBackup() {
        return this.connectionStringFromBackup;
    }

    public boolean isCancelRestoreRequested() {
        return this.cancelRestoreRequested;
    }

    public String getMaintenanceAuthenticationToken() {
        return (String)this.myMaintenanceAuthenticationToken.get();
    }

    public boolean isValidMaintenanceAuthenticationToken(@Nullable String string) {
        try {
            if (StringUtil.isEmpty((String)string)) {
                return false;
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupContext.a(illegalStateException);
        }
        String string2 = this.getMaintenanceAuthenticationToken();
        try {
            if (StringUtil.isEmpty((String)string2)) {
                return false;
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupContext.a(illegalStateException);
        }
        return MessageDigest.isEqual(string.trim().getBytes(StandardCharsets.UTF_8), string2.getBytes(StandardCharsets.UTF_8));
    }

    public void logAuthenticationToken() {
        String string = this.getMaintenanceAuthenticationToken();
        Object object = StringUtil.isEmpty((String)string) ? "Super user authentication token is disabled on this server" : "Super user authentication token: " + string + " (use empty username with the token as the password to access the server)";
        Loggers.SERVER.info((String)object);
        System.out.println("[TeamCity] " + (String)object);
    }

    public boolean isMaintenanceAsked() {
        return this.myMaintenanceAsked;
    }

    public boolean hasExceptions() {
        boolean bl;
        try {
            bl = !this.myExceptions.isEmpty();
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupContext.a(illegalStateException);
        }
        return bl;
    }

    @NotNull
    public LinkedList<Throwable> getExceptions() {
        return new LinkedList<Throwable>(this.myExceptions);
    }

    @NotNull
    public Date getServerStartupTimestamp() {
        Date date;
        try {
            date = this.myServerStartupTimestamp;
            if (date == null) {
                StartupContext.$$$reportNull$$$0(35);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupContext.a(illegalStateException);
        }
        return date;
    }

    public long getServerStartupDuration() {
        try {
            if (this.myServerStartupFinishedTimestamp == null) {
                return Dates.now().getTime() - this.myServerStartupTimestamp.getTime();
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupContext.a(illegalStateException);
        }
        return this.myServerStartupFinishedTimestamp.getTime() - this.myServerStartupTimestamp.getTime();
    }

    public void setServerStartupFinishedTimestamp(@NotNull Date date) {
        try {
            if (date == null) {
                StartupContext.$$$reportNull$$$0(36);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupContext.a(illegalStateException);
        }
        this.myServerStartupFinishedTimestamp = date;
    }

    @Nullable
    public Date getServerStartupFinishedTimestamp() {
        return this.myServerStartupFinishedTimestamp;
    }

    public void recordStartupPhase(@NotNull String string, @NotNull Instant instant, @NotNull Instant instant2) {
        try {
            if (string == null) {
                StartupContext.$$$reportNull$$$0(37);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupContext.a(illegalStateException);
        }
        try {
            if (instant == null) {
                StartupContext.$$$reportNull$$$0(38);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupContext.a(illegalStateException);
        }
        try {
            if (instant2 == null) {
                StartupContext.$$$reportNull$$$0(39);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupContext.a(illegalStateException);
        }
        this.recordStartupPhase(string, instant, instant2, -1L);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void recordStartupPhase(@NotNull String string, @NotNull Instant instant, @NotNull Instant instant2, long l) {
        try {
            if (string == null) {
                StartupContext.$$$reportNull$$$0(40);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupContext.a(illegalStateException);
        }
        try {
            if (instant == null) {
                StartupContext.$$$reportNull$$$0(41);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupContext.a(illegalStateException);
        }
        try {
            if (instant2 == null) {
                StartupContext.$$$reportNull$$$0(42);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupContext.a(illegalStateException);
        }
        Map<String, StartupPhase> map = this.myStartupPhases;
        synchronized (map) {
            this.myStartupPhases.putIfAbsent(string, new StartupPhase(string, instant, instant2, l));
        }
    }

    @NotNull
    public StartupPhaseRecording startPhase(@NotNull String string) {
        try {
            if (string == null) {
                StartupContext.$$$reportNull$$$0(43);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupContext.a(illegalStateException);
        }
        return new StartupPhaseRecording(this, string, Instant.now());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @NotNull
    public List<StartupPhase> getStartupPhases() {
        Map<String, StartupPhase> map = this.myStartupPhases;
        // MONITORENTER : map
        List<StartupPhase> list = Collections.unmodifiableList(new ArrayList<StartupPhase>(this.myStartupPhases.values()));
        // MONITOREXIT : map
        if (list != null) return list;
        StartupContext.$$$reportNull$$$0(44);
        return list;
    }

    @NotNull
    public Collection<String> getPreviousInstallationFiles() {
        Collection<String> collection;
        try {
            collection = this.myPreviousInstallationFiles;
            if (collection == null) {
                StartupContext.$$$reportNull$$$0(45);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupContext.a(illegalStateException);
        }
        return collection;
    }

    public String getCurrentJavaVersion() {
        return this.myCurrentJavaVersion;
    }

    public String getCurrentJavaLocation() {
        return this.myCurrentJavaLocation;
    }

    public boolean isUnsupportedJava() {
        return this.myUnsupoprtedJava;
    }

    public boolean isUnsupportedServerRole() {
        return this.myUnsupportedServerRole;
    }

    public String getServerRole() {
        return this.myServerRole;
    }

    public String getUUID() {
        return this.myUUID;
    }

    public void setUUID(String string) {
        this.myUUID = string;
    }

    public void setSelectedDirError(String string) {
        this.mySelectedDirError = string;
    }

    public String getSelectedDirError() {
        return this.mySelectedDirError;
    }

    public String getNodeId() {
        return CurrentNodeInfo.getNodeId();
    }

    public String getNodeIdProp() {
        return "teamcity.server.nodeId";
    }

    public List<SessionInfo> getOtherNodes() {
        TeamCityDatabaseManager teamCityDatabaseManager;
        block7: {
            teamCityDatabaseManager = this.myDatabaseManager;
            try {
                try {
                    if (teamCityDatabaseManager != null && teamCityDatabaseManager.getHeartbeat() != null) break block7;
                }
                catch (IllegalStateException illegalStateException) {
                    throw StartupContext.a(illegalStateException);
                }
                return Collections.emptyList();
            }
            catch (IllegalStateException illegalStateException) {
                throw StartupContext.a(illegalStateException);
            }
        }
        List list = teamCityDatabaseManager.getHeartbeat().getValidSessions();
        try {
            if (list.size() == 1) {
                return Collections.emptyList();
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupContext.a(illegalStateException);
        }
        return list.subList(1, list.size());
    }

    public void setCompetitorSession(SessionInfo sessionInfo) {
        this.myCompetitorSession = sessionInfo;
    }

    public SessionInfo getCompetitorSession() {
        return this.myCompetitorSession;
    }

    public void setPort(Integer n) {
        this.myPort = n;
    }

    @Nullable
    public Integer getPort() {
        return this.myPort;
    }

    public void setScheme(@NotNull String string) {
        try {
            if (string == null) {
                StartupContext.$$$reportNull$$$0(46);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupContext.a(illegalStateException);
        }
        this.myScheme = string;
    }

    @Nullable
    public String getScheme() {
        return this.myScheme;
    }

    public void setServletContextPath(String string) {
        this.myServletContextPath = string;
    }

    @NotNull
    public String getServletContextPath() {
        String string;
        String string2;
        try {
            string2 = this.myServletContextPath == null ? "" : this.myServletContextPath;
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupContext.a(illegalStateException);
        }
        try {
            string = string2;
            if (string2 == null) {
                StartupContext.$$$reportNull$$$0(47);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupContext.a(illegalStateException);
        }
        return string;
    }

    public String getDistributionType() {
        return this.myDistributionType;
    }

    public void setDistributionType(String string) {
        this.myDistributionType = string;
    }

    public void setNodeSpecificConfigs(@NotNull File file) throws IOException {
        block16: {
            File file2;
            block15: {
                try {
                    if (file == null) {
                        StartupContext.$$$reportNull$$$0(48);
                    }
                }
                catch (IOException iOException) {
                    throw StartupContext.a(iOException);
                }
                File file3 = new File(file, "config");
                FileUtil.createDir((File)file3);
                file2 = new File(file3, "database.properties");
                try {
                    try {
                        if ($assertionsDisabled || this.myDataDirManager != null) break block15;
                    }
                    catch (IOException iOException) {
                        throw StartupContext.a(iOException);
                    }
                    throw new AssertionError();
                }
                catch (IOException iOException) {
                    throw StartupContext.a(iOException);
                }
            }
            try {
                if (file2.exists()) {
                    this.myLogger.info("Will use custom path for the database properties file: " + String.valueOf(file2));
                    this.myDatabasePropertiesFile = new DatabasePropertiesFile(file, this.getEnv(), DatabasePropertiesFile.getDefaultDatabasePropertiesFile((File)file));
                    this.myDataDirManager.setDatabasePropertiesFile(this.myDatabasePropertiesFile.getFile());
                }
            }
            catch (IOException iOException) {
                throw StartupContext.a(iOException);
            }
            try {
                try {
                    if ($assertionsDisabled || this.myServerPaths != null) break block16;
                }
                catch (IOException iOException) {
                    throw StartupContext.a(iOException);
                }
                throw new AssertionError();
            }
            catch (IOException iOException) {
                throw StartupContext.a(iOException);
            }
        }
        if (!this.myServerPaths.isCustomCachesDir()) {
            File file4 = new File(file, "system" + File.separator + "caches");
            this.initCustomCachesPath(this.myServerPaths, file4.getAbsolutePath());
        }
    }

    @NotNull
    public InstallationsManager getInstallationsManager() {
        InstallationsManager installationsManager;
        try {
            installationsManager = this.myInstallationsManager;
            if (installationsManager == null) {
                StartupContext.$$$reportNull$$$0(49);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupContext.a(illegalStateException);
        }
        return installationsManager;
    }

    @Nullable
    DatabaseType getJdbcDriverType() {
        Object object;
        Object object2;
        block12: {
            DatabasePropertiesFile databasePropertiesFile = this.myDatabasePropertiesFile;
            try {
                if (databasePropertiesFile == null || !databasePropertiesFile.isConfigurationPresent()) break block12;
            }
            catch (IllegalStateException illegalStateException) {
                throw StartupContext.a(illegalStateException);
            }
            object2 = databasePropertiesFile.getConnectionUrl();
            if (!((String)object2).isEmpty()) {
                DatabaseType databaseType;
                block13: {
                    JdbcConnectionString jdbcConnectionString = new JdbcConnectionString((String)object2);
                    databaseType = jdbcConnectionString.getDBType();
                    try {
                        try {
                            if (databaseType != DatabaseType.MSSQL || jdbcConnectionString.isNative()) break block13;
                        }
                        catch (IllegalStateException illegalStateException) {
                            throw StartupContext.a(illegalStateException);
                        }
                        return null;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw StartupContext.a(illegalStateException);
                    }
                }
                try {
                    if (databaseType != DatabaseType.UNKNOWN) {
                        return databaseType;
                    }
                }
                catch (IllegalStateException illegalStateException) {
                    throw StartupContext.a(illegalStateException);
                }
            }
        }
        object2 = this.myJdbcDriverType;
        try {
            object = object2 != DatabaseType.UNKNOWN ? object2 : null;
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupContext.a(illegalStateException);
        }
        return object;
    }

    void setJdbcDriverType(@NotNull DatabaseType databaseType) {
        block11: {
            try {
                if (databaseType == null) {
                    StartupContext.$$$reportNull$$$0(50);
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw StartupContext.a(illegalStateException);
            }
            DatabasePropertiesFile databasePropertiesFile = this.myDatabasePropertiesFile;
            try {
                if (databasePropertiesFile == null || !databasePropertiesFile.isFileExists()) break block11;
            }
            catch (IllegalStateException illegalStateException) {
                throw StartupContext.a(illegalStateException);
            }
            DatabaseType databaseType2 = databasePropertiesFile.getDatabaseType();
            try {
                if (databaseType2 != DatabaseType.UNKNOWN) {
                    this.myLogger.warn(String.format("Ignoring the supplied JDBC driver type: %s; as %s already specifies %s", databaseType, "database.properties", databaseType2));
                    return;
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw StartupContext.a(illegalStateException);
            }
        }
        try {
            if (databaseType == DatabaseType.UNKNOWN) {
                this.myLogger.warn(String.format("Ignoring the supplied JDBC driver type: %s", DatabaseType.UNKNOWN));
                return;
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupContext.a(illegalStateException);
        }
        this.myJdbcDriverType = databaseType;
    }

    @Nullable
    JdbcDriverDownloader.ProgressListener getJdbcDriverDownloadProgressListener() {
        return this.myJdbcDriverDownloadProgressListener;
    }

    void setJdbcDriverDownloadProgressListener(@NotNull JdbcDriverDownloader.ProgressListener progressListener) {
        try {
            if (progressListener == null) {
                StartupContext.$$$reportNull$$$0(51);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupContext.a(illegalStateException);
        }
        this.myJdbcDriverDownloadProgressListener = progressListener;
    }

    @NotNull
    Map<DatabaseType, JdbcDriver> getJdbcDriverMetadata() {
        Map<DatabaseType, JdbcDriver> map;
        try {
            map = Collections.unmodifiableMap(this.myJdbcDriverMetadata);
            if (map == null) {
                StartupContext.$$$reportNull$$$0(52);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupContext.a(illegalStateException);
        }
        return map;
    }

    void setJdbcDriverMetadata(@NotNull Map<DatabaseType, JdbcDriver> map) {
        try {
            if (map == null) {
                StartupContext.$$$reportNull$$$0(53);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupContext.a(illegalStateException);
        }
        this.myJdbcDriverMetadata.putAll(map);
    }

    public String getRecommendedMemorySettings() {
        return StringUtil.emptyIfNull((String)this.myRecommendedMemorySettings);
    }

    public void setRecommendedMemorySettings(String string) {
        this.myRecommendedMemorySettings = string;
    }

    public String getCurrentXmx() {
        return XmxWarningUtil.getUsedXmx();
    }

    public String getRecommendedMemOptsValue() {
        String string = System.getenv("TEAMCITY_SERVER_MEM_OPTS");
        return XmxWarningUtil.getRecommendedMemOpts((String)string, (String)this.myRecommendedMemorySettings);
    }

    public boolean isXmxFromMemOptsUsed() {
        String string = System.getenv("TEAMCITY_SERVER_MEM_OPTS");
        return XmxWarningUtil.isXmxUsedFromMemOptsVar((String)string);
    }

    public boolean isRestoreProcess() {
        return this.restoreProcess;
    }

    public void setRestoreProcess(boolean bl) {
        this.restoreProcess = bl;
    }

    public String getLogsDir() {
        return System.getProperty("teamcity_logs");
    }

    public void setHttpsConfigurator(@NotNull HttpsConfigurator httpsConfigurator) {
        try {
            if (httpsConfigurator == null) {
                StartupContext.$$$reportNull$$$0(54);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupContext.a(illegalStateException);
        }
        this.myConfigurator = httpsConfigurator;
    }

    @Nullable
    public HttpsConfigurator getHttpsConfigurator() {
        return this.myConfigurator;
    }

    @Nullable
    public LicenseAgreement getLicenseAgreement() {
        return this.myLicenseAgreement;
    }

    public void setLicenseAgreement(@Nullable LicenseAgreement licenseAgreement) {
        this.myLicenseAgreement = licenseAgreement;
    }

    public void enableUsageStatistics() {
        this.myUsageStatisticsEnabled = true;
    }

    public boolean isUsageStatisticsEnabledOnStartup() {
        return this.myUsageStatisticsEnabled;
    }

    public void setLicenseAgreementAccepted() {
        this.myLicenseAgreementAccepted = true;
    }

    public boolean isLicenseAgreementAcceptedOnStartup() {
        return this.myLicenseAgreementAccepted;
    }

    public boolean isDatabaseCreatedOnStartup() {
        return this.myDatabaseCreatedOnStartup;
    }

    public void setDatabaseCreatedOnStartup(boolean bl) {
        this.myDatabaseCreatedOnStartup = bl;
    }

    @NotNull
    public AtomicBoolean getDataDirPrefilled() {
        AtomicBoolean atomicBoolean;
        try {
            atomicBoolean = this.myDataDirPrefilled;
            if (atomicBoolean == null) {
                StartupContext.$$$reportNull$$$0(55);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupContext.a(illegalStateException);
        }
        return atomicBoolean;
    }

    public void setEncryptionKeysSource(String string) {
        this.myKeysSource = string;
    }

    public boolean isEnvKeysSource() {
        return "ENV".equalsIgnoreCase(this.myKeysSource);
    }

    static {
        boolean bl;
        try {
            bl = !StartupContext.class.desiredAssertionStatus();
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupContext.a(illegalStateException);
        }
        $assertionsDisabled = bl;
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

    private static Exception a(Exception exception) {
        return exception;
    }
}
