/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.intellij.openapi.diagnostic.Logger
 *  jetbrains.buildServer.AlreadyExistsException
 *  jetbrains.buildServer.AlreadyRunningBuildException
 *  jetbrains.buildServer.ExtensionHolder
 *  jetbrains.buildServer.InvalidRunningBuildException
 *  jetbrains.buildServer.ShutdownInProgressException
 *  jetbrains.buildServer.Used
 *  jetbrains.buildServer.XmlRpcHandlerManager
 *  jetbrains.buildServer.agent.AgentBuildRunner
 *  jetbrains.buildServer.agent.AgentBuildSettings
 *  jetbrains.buildServer.agent.AgentCommandlineArguments
 *  jetbrains.buildServer.agent.AgentLifeCycleListener
 *  jetbrains.buildServer.agent.AgentLogProxy
 *  jetbrains.buildServer.agent.AgentRunningBuild
 *  jetbrains.buildServer.agent.AgentRunningBuildEx
 *  jetbrains.buildServer.agent.BaseServerLoggerFacade
 *  jetbrains.buildServer.agent.BuildAgent
 *  jetbrains.buildServer.agent.BuildAgentConfigurationEx
 *  jetbrains.buildServer.agent.BuildInterruptReason
 *  jetbrains.buildServer.agent.BuildProgressLogger
 *  jetbrains.buildServer.agent.CurrentBuildHolder
 *  jetbrains.buildServer.agent.UpgradableAgent
 *  jetbrains.buildServer.agent.impl.AgentDetailsFactory
 *  jetbrains.buildServer.agent.impl.AgentLogProxyImpl
 *  jetbrains.buildServer.agent.impl.BuildAgentEx
 *  jetbrains.buildServer.agent.impl.BuildAgentImpl$3
 *  jetbrains.buildServer.agent.impl.BuildAgentImpl$AgentServerLoggerFacade
 *  jetbrains.buildServer.agent.impl.BuildAgentPreInitAction
 *  jetbrains.buildServer.agent.impl.BuildAgentShutdownTask
 *  jetbrains.buildServer.agent.impl.BuildAgentShutdownTask$BuildAgentShutdownCallback
 *  jetbrains.buildServer.agent.impl.BuildAgentStateHolder
 *  jetbrains.buildServer.agent.impl.BuildRunAction
 *  jetbrains.buildServer.agent.impl.BuildRunAgentState
 *  jetbrains.buildServer.agent.impl.BuildRunAgentStateImpl
 *  jetbrains.buildServer.agent.impl.BuildRunnerRegistry
 *  jetbrains.buildServer.agent.impl.BuildRunnerRegistryEx
 *  jetbrains.buildServer.agent.impl.NoServerMonitor
 *  jetbrains.buildServer.agent.impl.RegistrationLogger
 *  jetbrains.buildServer.agent.impl.ServerLogger
 *  jetbrains.buildServer.agent.impl.ServerMonitor
 *  jetbrains.buildServer.agent.impl.ServerMonitorImpl
 *  jetbrains.buildServer.agent.impl.ServerXmlRpcProxy
 *  jetbrains.buildServer.agent.impl.ServerXmlRpcProxy$BuildCredentialsProvider
 *  jetbrains.buildServer.agent.impl.XmlRpcLogHandler
 *  jetbrains.buildServer.agent.impl.config.AgentConfigurationStatePersistenceManager
 *  jetbrains.buildServer.agent.impl.config.AgentContextEx
 *  jetbrains.buildServer.agent.impl.messagesSources.PersistedMessagesLoader
 *  jetbrains.buildServer.agent.impl.messagesSources.XmlMessagesLoader
 *  jetbrains.buildServer.agent.impl.operationModes.AgentOperationModeHolder
 *  jetbrains.buildServer.agent.impl.runContext.RunningBuildContext
 *  jetbrains.buildServer.agent.impl.runContext.RunningBuildContextFactory
 *  jetbrains.buildServer.agent.impl.serverCommunication.CommunicationProtocol
 *  jetbrains.buildServer.agent.impl.serverCommunication.IServerCommunicationProtocolResolver
 *  jetbrains.buildServer.agent.impl.serverCommunication.ProtocolDoesNotWorkException
 *  jetbrains.buildServer.agent.impl.upgrade.AgentExitCode
 *  jetbrains.buildServer.agent.impl.upgrade.AgentVersionController
 *  jetbrains.buildServer.agent.impl.upgrade.UpgradeThread
 *  jetbrains.buildServer.agent.messages.BuildMessagesTranslator
 *  jetbrains.buildServer.agentServer.AgentBuild
 *  jetbrains.buildServer.agentServer.AgentBuildResult
 *  jetbrains.buildServer.agentServer.AgentDetails
 *  jetbrains.buildServer.agentServer.AgentRegistrationDetails
 *  jetbrains.buildServer.agentServer.Server
 *  jetbrains.buildServer.configuration.ChangeListener
 *  jetbrains.buildServer.configuration.FileWatcher
 *  jetbrains.buildServer.log.LogInitializer
 *  jetbrains.buildServer.log.Loggers
 *  jetbrains.buildServer.messages.BuildMessage1
 *  jetbrains.buildServer.messages.DefaultMessagesInfo
 *  jetbrains.buildServer.messages.MessagesPersistence
 *  jetbrains.buildServer.messages.Status
 *  jetbrains.buildServer.messages.XStreamHolder
 *  jetbrains.buildServer.plugins.AgentUpdateInfo
 *  jetbrains.buildServer.serverSide.TeamCityProperties
 *  jetbrains.buildServer.util.Action
 *  jetbrains.buildServer.util.CollectionsUtil
 *  jetbrains.buildServer.util.DiagnosticUtil
 *  jetbrains.buildServer.util.DiagnosticUtil$ThreadDumpData
 *  jetbrains.buildServer.util.Disposable
 *  jetbrains.buildServer.util.EventDispatcher
 *  jetbrains.buildServer.util.FileUtil
 *  jetbrains.buildServer.util.NamedThreadUtil
 *  jetbrains.buildServer.util.StringUtil
 *  jetbrains.buildServer.util.ssl.SSLTrustStoreProvider
 *  jetbrains.buildServer.version.ServerVersionHolder
 *  jetbrains.buildServer.xstream.XStreamWrapper
 *  org.apache.xmlrpc.WebServer
 *  org.jetbrains.annotations.Contract
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package jetbrains.buildServer.agent.impl;

import com.intellij.openapi.diagnostic.Logger;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.Security;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.Vector;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import jetbrains.buildServer.AlreadyExistsException;
import jetbrains.buildServer.AlreadyRunningBuildException;
import jetbrains.buildServer.ExtensionHolder;
import jetbrains.buildServer.InvalidRunningBuildException;
import jetbrains.buildServer.ShutdownInProgressException;
import jetbrains.buildServer.Used;
import jetbrains.buildServer.XmlRpcHandlerManager;
import jetbrains.buildServer.agent.AgentBuildRunner;
import jetbrains.buildServer.agent.AgentBuildSettings;
import jetbrains.buildServer.agent.AgentCommandlineArguments;
import jetbrains.buildServer.agent.AgentLifeCycleListener;
import jetbrains.buildServer.agent.AgentLogProxy;
import jetbrains.buildServer.agent.AgentRunningBuild;
import jetbrains.buildServer.agent.AgentRunningBuildEx;
import jetbrains.buildServer.agent.BaseServerLoggerFacade;
import jetbrains.buildServer.agent.BuildAgent;
import jetbrains.buildServer.agent.BuildAgentConfigurationEx;
import jetbrains.buildServer.agent.BuildInterruptReason;
import jetbrains.buildServer.agent.BuildProgressLogger;
import jetbrains.buildServer.agent.CurrentBuildHolder;
import jetbrains.buildServer.agent.UpgradableAgent;
import jetbrains.buildServer.agent.impl.AgentDetailsFactory;
import jetbrains.buildServer.agent.impl.AgentLogProxyImpl;
import jetbrains.buildServer.agent.impl.BuildAgentEx;
import jetbrains.buildServer.agent.impl.BuildAgentImpl;
import jetbrains.buildServer.agent.impl.BuildAgentPreInitAction;
import jetbrains.buildServer.agent.impl.BuildAgentShutdownTask;
import jetbrains.buildServer.agent.impl.BuildAgentStateHolder;
import jetbrains.buildServer.agent.impl.BuildRunAction;
import jetbrains.buildServer.agent.impl.BuildRunAgentState;
import jetbrains.buildServer.agent.impl.BuildRunAgentStateImpl;
import jetbrains.buildServer.agent.impl.BuildRunnerRegistry;
import jetbrains.buildServer.agent.impl.BuildRunnerRegistryEx;
import jetbrains.buildServer.agent.impl.NoServerMonitor;
import jetbrains.buildServer.agent.impl.RegistrationLogger;
import jetbrains.buildServer.agent.impl.ServerLogger;
import jetbrains.buildServer.agent.impl.ServerMonitor;
import jetbrains.buildServer.agent.impl.ServerMonitorImpl;
import jetbrains.buildServer.agent.impl.ServerXmlRpcProxy;
import jetbrains.buildServer.agent.impl.XmlRpcLogHandler;
import jetbrains.buildServer.agent.impl.config.AgentConfigurationStatePersistenceManager;
import jetbrains.buildServer.agent.impl.config.AgentContextEx;
import jetbrains.buildServer.agent.impl.messagesSources.PersistedMessagesLoader;
import jetbrains.buildServer.agent.impl.messagesSources.XmlMessagesLoader;
import jetbrains.buildServer.agent.impl.operationModes.AgentOperationModeHolder;
import jetbrains.buildServer.agent.impl.runContext.RunningBuildContext;
import jetbrains.buildServer.agent.impl.runContext.RunningBuildContextFactory;
import jetbrains.buildServer.agent.impl.serverCommunication.CommunicationProtocol;
import jetbrains.buildServer.agent.impl.serverCommunication.IServerCommunicationProtocolResolver;
import jetbrains.buildServer.agent.impl.serverCommunication.ProtocolDoesNotWorkException;
import jetbrains.buildServer.agent.impl.upgrade.AgentExitCode;
import jetbrains.buildServer.agent.impl.upgrade.AgentVersionController;
import jetbrains.buildServer.agent.impl.upgrade.UpgradeThread;
import jetbrains.buildServer.agent.messages.BuildMessagesTranslator;
import jetbrains.buildServer.agentServer.AgentBuild;
import jetbrains.buildServer.agentServer.AgentBuildResult;
import jetbrains.buildServer.agentServer.AgentDetails;
import jetbrains.buildServer.agentServer.AgentRegistrationDetails;
import jetbrains.buildServer.agentServer.Server;
import jetbrains.buildServer.configuration.ChangeListener;
import jetbrains.buildServer.configuration.FileWatcher;
import jetbrains.buildServer.log.LogInitializer;
import jetbrains.buildServer.log.Loggers;
import jetbrains.buildServer.messages.BuildMessage1;
import jetbrains.buildServer.messages.DefaultMessagesInfo;
import jetbrains.buildServer.messages.MessagesPersistence;
import jetbrains.buildServer.messages.Status;
import jetbrains.buildServer.messages.XStreamHolder;
import jetbrains.buildServer.plugins.AgentUpdateInfo;
import jetbrains.buildServer.serverSide.TeamCityProperties;
import jetbrains.buildServer.util.Action;
import jetbrains.buildServer.util.CollectionsUtil;
import jetbrains.buildServer.util.DiagnosticUtil;
import jetbrains.buildServer.util.Disposable;
import jetbrains.buildServer.util.EventDispatcher;
import jetbrains.buildServer.util.FileUtil;
import jetbrains.buildServer.util.NamedThreadUtil;
import jetbrains.buildServer.util.StringUtil;
import jetbrains.buildServer.util.ssl.SSLTrustStoreProvider;
import jetbrains.buildServer.version.ServerVersionHolder;
import jetbrains.buildServer.xstream.XStreamWrapper;
import org.apache.xmlrpc.WebServer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BuildAgentImpl
implements BuildAgentEx,
UpgradableAgent {
    private static final Logger LOG = Loggers.AGENT;
    public static final String TEAMCITY_AGENT_DUMP_ON_OOM = "teamcity.agent.dump.on.oom";
    public static final int POST_PROCESSING_TASK_TIMEOUT = TeamCityProperties.getInteger((String)"teamcity.agent.postProcessingTasks.timeoutSec", (int)60) * 1000;
    private final BuildRunnerRegistryEx myRunners;
    private final BuildAgentConfigurationEx myConfiguration;
    private final Object myIdLock;
    private WebServer myWebServer;
    private Integer myId;
    private volatile AgentDetails myAgentDetailsSnapshot;
    private ServerMonitor myServerMonitor;
    private final BuildAgentStateHolder myRunningState;
    private AgentLogProxy myLogProxy;
    private final BaseServerLoggerFacade myLogger;
    @Nullable
    private ServerXmlRpcProxy myServerProxy;
    private volatile IServerCommunicationProtocolResolver myServerCommunicationProtocolResolver;
    private final SSLTrustStoreProvider mySslTrustStoreProvider;
    private FileWatcher myPropertiesFileWatcher;
    private final BuildAgentShutdownTask myShutdownTask;
    private final Semaphore myRegisterInProgress;
    private volatile boolean myRegistered;
    private final Set<String> myStartedBuilds;
    private Thread myShutdownHook;
    @Nullable
    private volatile Thread myUpgradeThread;
    private final ExtensionHolder myAgentExtensions;
    private final EventDispatcher<AgentLifeCycleListener> myEventDispatcher;
    private XmlRpcHandlerManager myXmlRpcHandlerManager;
    private final AgentDetailsFactory myDetailsFactory;
    private final AgentConfigurationStatePersistenceManager myAgentConfigurationStatePersistence;
    private final AgentOperationModeHolder myAgentOperationModeHolder;
    private boolean myIsInitialized;
    private static final String JAVA_DNS_CACHE_TTL_PROPERTY = "networkaddress.cache.ttl";
    private static final String JAVA_DNS_CACHE_NEGATIVE_TTL_PROPERTY = "networkaddress.cache.negative.ttl";
    private volatile Action<AgentBuild> myOnRunBuildCommandListener;
    private volatile CommunicationProtocol myUsedProtocol;
    private volatile boolean myCriticalErrorRestart;
    private volatile Long myFirstDuplicatedAgentRegistrationErrorTime;
    private volatile boolean myAgentStarted;
    private final Map<InetAddress, Date> myLastFailedXmlRpcLoggingTime;

    public BuildAgentImpl(@NotNull BuildAgentConfigurationEx configuration, @NotNull EventDispatcher<AgentLifeCycleListener> agentLifeCycleDispatcher, @NotNull ExtensionHolder agentExtensionsLocator, @NotNull BuildRunnerRegistryEx runnersRegistry, @NotNull AgentDetailsFactory detailsFactory, @NotNull IServerCommunicationProtocolResolver serverCommunicationProtocolResolver, @NotNull SSLTrustStoreProvider sslTrustStoreProvider, @NotNull AgentConfigurationStatePersistenceManager agentConfigurationStatePersistence, @NotNull AgentOperationModeHolder agentOperationModeHolder) {
        if (configuration == null) {
            BuildAgentImpl.$$$reportNull$$$0(0);
        }
        if (agentLifeCycleDispatcher == null) {
            BuildAgentImpl.$$$reportNull$$$0(1);
        }
        if (agentExtensionsLocator == null) {
            BuildAgentImpl.$$$reportNull$$$0(2);
        }
        if (runnersRegistry == null) {
            BuildAgentImpl.$$$reportNull$$$0(3);
        }
        if (detailsFactory == null) {
            BuildAgentImpl.$$$reportNull$$$0(4);
        }
        if (serverCommunicationProtocolResolver == null) {
            BuildAgentImpl.$$$reportNull$$$0(5);
        }
        if (sslTrustStoreProvider == null) {
            BuildAgentImpl.$$$reportNull$$$0(6);
        }
        if (agentConfigurationStatePersistence == null) {
            BuildAgentImpl.$$$reportNull$$$0(7);
        }
        if (agentOperationModeHolder == null) {
            BuildAgentImpl.$$$reportNull$$$0(8);
        }
        this.myIdLock = new Object();
        this.myAgentDetailsSnapshot = null;
        this.myServerMonitor = new NoServerMonitor();
        this.myRunningState = new BuildAgentStateHolder();
        this.myPropertiesFileWatcher = null;
        this.myRegisterInProgress = new Semaphore(1);
        this.myRegistered = false;
        this.myStartedBuilds = new HashSet<String>();
        this.myFirstDuplicatedAgentRegistrationErrorTime = null;
        this.myAgentStarted = false;
        this.myLastFailedXmlRpcLoggingTime = new HashMap<InetAddress, Date>();
        this.myConfiguration = configuration;
        this.myServerCommunicationProtocolResolver = serverCommunicationProtocolResolver;
        this.mySslTrustStoreProvider = sslTrustStoreProvider;
        this.myAgentConfigurationStatePersistence = agentConfigurationStatePersistence;
        this.myAgentOperationModeHolder = agentOperationModeHolder;
        this.configureDNSCacheTTL();
        this.myRunners = runnersRegistry;
        this.myDetailsFactory = detailsFactory;
        this.myAgentExtensions = agentExtensionsLocator;
        this.myEventDispatcher = agentLifeCycleDispatcher;
        this.myLogProxy = new AgentLogProxyImpl((BuildAgentEx)this, LogInitializer.isUnitTest() ? 300L : 5000L, (PersistedMessagesLoader)new XmlMessagesLoader());
        this.myLogger = new AgentServerLoggerFacade(this);
        this.myShutdownTask = this.createShutdownTask();
    }

    private void configureDNSCacheTTL() {
        String dnsTTL = this.myConfiguration.getInternalProperty("teamcity.dns.cache.ttl", "1200");
        if ("1200".equals(dnsTTL)) {
            return;
        }
        this.setSecurityProperty(JAVA_DNS_CACHE_TTL_PROPERTY, dnsTTL);
        String dnsNegativeTTL = this.myConfiguration.getInternalProperty("teamcity.dns.cache.negative.ttl", "0");
        this.setSecurityProperty(JAVA_DNS_CACHE_NEGATIVE_TTL_PROPERTY, dnsNegativeTTL);
    }

    private void setSecurityProperty(String propName, String value) {
        Security.setProperty(propName, value);
    }

    public void setLogProxy(@NotNull AgentLogProxy logProxy) {
        if (logProxy == null) {
            BuildAgentImpl.$$$reportNull$$$0(9);
        }
        if (this.myLogProxy != null) {
            this.myLogProxy.dispose();
        }
        this.myLogProxy = logProxy;
    }

    @NotNull
    public String getAgentKindInLowerCase() {
        return "build agent";
    }

    public boolean stopBuild() {
        return this.stopBuild(BuildInterruptReason.SERVER_STOP_BUILD);
    }

    public boolean stopBuild(@NotNull BuildInterruptReason reason) {
        BuildRunAgentState state;
        if (reason == null) {
            BuildAgentImpl.$$$reportNull$$$0(10);
        }
        if ((state = this.getRunningState()) != null) {
            state.stopBuild(reason);
            return true;
        }
        LOG.info("Stop command was not performed. No build to stop with reason: " + String.valueOf(reason));
        return false;
    }

    public boolean upgrade(Vector pluginNames) {
        return this.doUpgrade();
    }

    public boolean doUpgrade() {
        if (this.isShutdownScheduled()) {
            return true;
        }
        Thread upgradeThread = ((UpgradeThread)this.myAgentExtensions.getSingletonService(UpgradeThread.class)).startUpgrade();
        if (upgradeThread != null) {
            this.myUpgradeThread = upgradeThread;
        }
        return true;
    }

    public void awaitUpgradeFinished() throws InterruptedException {
        Thread upgradeThread = this.myUpgradeThread;
        if (upgradeThread == null) {
            return;
        }
        upgradeThread.join();
    }

    public boolean isAtLeastOneUpgradeStarted() {
        return this.myUpgradeThread != null;
    }

    public boolean isRegistered() {
        return this.myRegistered;
    }

    @Deprecated
    public boolean ping(int agentId) {
        if (agentId > 0 && !new Integer(agentId).equals(this.getId()) && !this.isRegisterInProgress()) {
            LOG.warn("Server's agent id " + agentId + " does not match my agent id " + this.getId());
            return false;
        }
        this.myServerMonitor.ping();
        return true;
    }

    public String ping2() {
        this.myServerMonitor.ping();
        return this.myConfiguration.getPingCode();
    }

    @NotNull
    public BuildRunnerRegistry getRunnersRegistry() {
        BuildRunnerRegistryEx buildRunnerRegistryEx = this.myRunners;
        if (buildRunnerRegistryEx == null) {
            BuildAgentImpl.$$$reportNull$$$0(11);
        }
        return buildRunnerRegistryEx;
    }

    public String runBuild(@NotNull String serializedAgentBuild) {
        if (serializedAgentBuild == null) {
            BuildAgentImpl.$$$reportNull$$$0(12);
        }
        try {
            return this.runBuild(AgentBuild.deserialize((String)serializedAgentBuild)).serialize();
        }
        catch (RuntimeException t) {
            LOG.warn("Failed to start build on agent. " + t.toString(), (Throwable)t);
            throw t;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @NotNull
    public AgentBuildResult runBuild(@NotNull AgentBuild agentBuild) {
        AgentBuildResult agentBuildResult;
        Action<AgentBuild> onRunBuildCommandListener;
        if (agentBuild == null) {
            BuildAgentImpl.$$$reportNull$$$0(13);
        }
        if ((onRunBuildCommandListener = this.myOnRunBuildCommandListener) != null) {
            onRunBuildCommandListener.apply((Object)agentBuild);
        }
        if (this.isShutdownScheduled()) {
            throw new ShutdownInProgressException();
        }
        BuildAgentImpl buildAgentImpl = this;
        synchronized (buildAgentImpl) {
            this.checkAlreadyRunningBuild();
            LOG.info("\n\n\n");
            LOG.info("===================================================");
            LOG.info("Starting " + agentBuild.describe(true));
            if (this.getId() == null) {
                // empty if block
            }
            this.checkAlreadyRunningBuild();
            this.myLogger.buildStarted();
            ServerLogger.reset();
            this.myStartedBuilds.add(String.valueOf(agentBuild.getBuildId()));
            this.myLogProxy.onBuildStart(String.valueOf(agentBuild.getBuildId()));
            BuildRunAgentStateImpl state = new BuildRunAgentStateImpl(agentBuild);
            this.myRunningState.setRunningState((BuildRunAgentState)state, (Disposable)state);
            AgentBuildResult result = new AgentBuildResult(new Date());
            try {
                Thread buildThread = new Thread((Runnable)new /* Unavailable Anonymous Inner Class!! */);
                buildThread.setName(NamedThreadUtil.getTcThreadPrefix() + agentBuild.describe(false));
                buildThread.setDaemon(false);
                buildThread.start();
                agentBuildResult = result;
            }
            catch (Throwable e) {
                this.releaseBuildData();
                throw e;
            }
        }
        if (agentBuildResult == null) {
            BuildAgentImpl.$$$reportNull$$$0(14);
        }
        return agentBuildResult;
    }

    private void checkAlreadyRunningBuild() {
        if (this.isRunning()) {
            LOG.warn("Failed to start build. Build with id " + this.getBuildId() + " is running on agent");
            throw new AlreadyRunningBuildException(this.getBuildId(), this.myLogProxy.hasPendingCommands());
        }
    }

    private void doActualBuild(@NotNull BuildRunAgentStateImpl state, @NotNull AgentBuild agentBuild) {
        if (state == null) {
            BuildAgentImpl.$$$reportNull$$$0(15);
        }
        if (agentBuild == null) {
            BuildAgentImpl.$$$reportNull$$$0(16);
        }
        RunningBuildContextFactory buildContextFactory = (RunningBuildContextFactory)this.myAgentExtensions.getSingletonService(RunningBuildContextFactory.class);
        RunningBuildContext buildContext = buildContextFactory.createContext(agentBuild, this.getLogger(), state);
        BuildRunAction runAction = buildContext.getBuildRunAction();
        state.initAndCheckForInterrupt(runAction, (Disposable)buildContext);
        BuildProgressLogger buildLogger = ((AgentBuildSettings)buildContext.getPerBuildServiceLocator().getSingletonService(AgentBuildSettings.class)).getBuildLogger();
        this.waitForPreviousBuildPostprocessing(buildLogger);
        ServerXmlRpcProxy serverProxy = this.myServerProxy;
        if (serverProxy != null) {
            serverProxy.onBuildStart(runAction.getRunningBuild());
        }
        BuildMessage1 message = DefaultMessagesInfo.createTextMessage((String)("Agent time zone: " + TimeZone.getDefault().getID()), (Status)Status.NORMAL).updateTags(Arrays.asList("tc:server", "tc:internal"));
        buildLogger.logMessage(message);
        this.myRunningState.processStarted(runAction.getRunningBuild());
        ((CurrentBuildHolder)this.myAgentExtensions.getSingletonService(CurrentBuildHolder.class)).setCurrentBuild(runAction.getRunningBuild());
        runAction.runBuild();
    }

    private void waitForPreviousBuildPostprocessing(BuildProgressLogger buildLogger) {
        while (this.myRunningState.isProcessRunning()) {
            buildLogger.message("Waiting for the previous build post-processing tasks to finish");
            new /* Unavailable Anonymous Inner Class!! */;
            if (!this.myRunningState.isProcessRunning()) {
                return;
            }
            String message = "Waiting for the previous build post-processing tasks takes longer than expected.";
            File file = DiagnosticUtil.threadDumpToDirectory((File)this.myConfiguration.getAgentLogsDirectory(), (DiagnosticUtil.ThreadDumpData)new DiagnosticUtil.ThreadDumpData().withSummary("Waiting for the previous build post-processing tasks takes longer than expected."));
            buildLogger.message("Waiting for the previous build post-processing tasks takes longer than expected. Thread dump is saved to log file " + file.getAbsolutePath());
        }
    }

    private void cleanupWorkingDir() {
        File workDirPath = this.myConfiguration.getWorkDirectory();
        try {
            File workDir = workDirPath.getCanonicalFile();
            if (!workDir.isDirectory()) {
                return;
            }
            File agentBat = new File(workDir, "../bin/agent.bat");
            if (!agentBat.isFile()) {
                return;
            }
            File backupDir = new File(workDir, ".old");
            if (!backupDir.mkdirs()) {
                if (backupDir.isDirectory()) {
                    return;
                }
                LOG.warn("Unable to create directory: " + backupDir.getAbsolutePath());
                return;
            }
            3 filter = new /* Unavailable Anonymous Inner Class!! */;
            File[] files = workDir.listFiles((FilenameFilter)filter);
            if (files != null) {
                for (File file : files) {
                    if (!file.isDirectory()) continue;
                    File newName = new File(backupDir, file.getName());
                    try {
                        FileUtil.rename((File)file, (File)newName);
                    }
                    catch (IOException e) {
                        LOG.warn("Failed to rename directory " + file.getAbsolutePath() + " to " + newName.getAbsolutePath());
                    }
                }
            }
        }
        catch (IOException e) {
            LOG.warn(e.toString());
        }
    }

    public boolean isBuildFailing(String buildId) throws InterruptedException {
        return this.isStarted(buildId) && this.myLogProxy.isBuildFailing(buildId);
    }

    public boolean buildInterrupted(String buildId) {
        return this.buildInterrupted(buildId, "", true);
    }

    public boolean buildInterrupted(String buildId, String comment, boolean needRequeue) {
        return this.isStarted(buildId) && this.myLogProxy.buildInterrupted(buildId, comment, needRequeue);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public synchronized boolean buildFinished(String buildId, Date finishDate, boolean buildFailed) {
        if (!this.isStarted(buildId)) {
            return false;
        }
        try {
            LOG.info("Build finished, build id " + buildId);
            boolean bl = this.myLogProxy.buildFinished(buildId, finishDate, buildFailed);
            return bl;
        }
        finally {
            this.ensureBuildFinishedDeliveredAndReleaseBuildData();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public synchronized boolean buildDetachedFromAgent(String buildId, String lastStepId) {
        if (!this.isStarted(buildId)) {
            return false;
        }
        try {
            LOG.info("Build detached from agent, build id " + buildId + ", last step id: " + lastStepId);
            boolean bl = this.myLogProxy.buildDetachedFromAgent(buildId, lastStepId);
            return bl;
        }
        finally {
            this.ensureBuildFinishedDeliveredAndReleaseBuildData();
        }
    }

    private void ensureBuildFinishedDeliveredAndReleaseBuildData() {
        int attempts = 1;
        try {
            while (this.myLogProxy.hasPendingBuildFinishedCommand()) {
                if (attempts == 2) {
                    LOG.warn("Failed to send \"buildFinished\" event to the server, will try again");
                }
                if (attempts > 1) {
                    Thread.sleep(this.myLogProxy.hasConnection2Server() ? 100L : 5000L);
                }
                this.myLogger.flush();
                ++attempts;
            }
        }
        catch (InterruptedException e) {
            LOG.warn("Waiting for \"buildFinished\" event delivery has been interrupted, releasing build data");
        }
        if (!this.myLogProxy.hasPendingBuildFinishedCommand() && attempts > 2) {
            LOG.warn("Event \"buildFinished\" was delivered to the server");
        }
        this.releaseBuildData();
    }

    private void releaseBuildData() {
        this.myLogProxy.killAllPendingAndIgnore(this.getBuildId());
        this.myRunningState.releaseState();
        this.myStartedBuilds.clear();
    }

    public boolean log(String buildId, Vector messagesXml) {
        if (!this.isStarted(buildId)) {
            return false;
        }
        try {
            return this.sendSerializedMessages(buildId, messagesXml);
        }
        catch (Error e) {
            this.restartOnError("Problem logging a message", e);
            return false;
        }
    }

    private boolean sendSingleMessage(String buildId, @NotNull BuildMessage1 message) {
        if (message == null) {
            BuildAgentImpl.$$$reportNull$$$0(17);
        }
        if (!this.isStarted(buildId)) {
            return false;
        }
        AgentRunningBuildEx build = this.myRunningState.getBuild();
        boolean sameBuild = build != null && buildId.equals(String.valueOf(build.getBuildId()));
        List<BuildMessagesTranslator> translators = this.getBuildMessagesTranslators();
        if (!sameBuild || translators.isEmpty()) {
            return this.log2Proxy(buildId, Arrays.asList(message));
        }
        try {
            return this.translateAndSend((AgentRunningBuild)build, translators, Arrays.asList(message));
        }
        catch (Error e) {
            this.restartOnError("Problem logging a message", e);
            return false;
        }
    }

    private boolean sendSerializedMessages(String buildId, @NotNull Vector messagesXml) {
        if (messagesXml == null) {
            BuildAgentImpl.$$$reportNull$$$0(18);
        }
        if (!this.isStarted(buildId) || messagesXml.isEmpty()) {
            return false;
        }
        AgentRunningBuildEx build = this.myRunningState.getBuild();
        boolean sameBuild = build != null && buildId.equals(String.valueOf(build.getBuildId()));
        List<BuildMessagesTranslator> translators = this.getBuildMessagesTranslators();
        if (!sameBuild || translators.isEmpty()) {
            return this.log2Proxy(buildId, this.deserializeMessages(messagesXml));
        }
        try {
            return this.translateAndSend((AgentRunningBuild)build, translators, this.deserializeMessages(messagesXml));
        }
        catch (Error e) {
            this.restartOnError("Problem logging a message", e);
            return false;
        }
    }

    private boolean translateAndSend(@NotNull AgentRunningBuild build, @NotNull List<BuildMessagesTranslator> translators, @NotNull List<BuildMessage1> messagesToTranslate) {
        if (build == null) {
            BuildAgentImpl.$$$reportNull$$$0(19);
        }
        if (translators == null) {
            BuildAgentImpl.$$$reportNull$$$0(20);
        }
        if (messagesToTranslate == null) {
            BuildAgentImpl.$$$reportNull$$$0(21);
        }
        List<BuildMessage1> afterTranslate = this.translate(build, messagesToTranslate, translators);
        return this.log2Proxy(String.valueOf(build.getBuildId()), afterTranslate);
    }

    private boolean log2Proxy(String buildId, List<BuildMessage1> messages) {
        this.fireMessageLogged(messages);
        return this.myLogProxy.logMessages(buildId, messages);
    }

    private void fireMessageLogged(List<BuildMessage1> messages) {
        for (BuildMessage1 message : messages) {
            ((AgentLifeCycleListener)this.myEventDispatcher.getMulticaster()).messageLogged(message);
        }
    }

    @NotNull
    private List<BuildMessage1> deserializeMessages(@NotNull Collection serialized) {
        if (serialized == null) {
            BuildAgentImpl.$$$reportNull$$$0(22);
        }
        List<Object> copy = Arrays.asList(serialized.toArray());
        List list = XStreamWrapper.deserializeObjects(copy, (XStreamHolder)MessagesPersistence.getMessagesXStreamHolder());
        if (list == null) {
            BuildAgentImpl.$$$reportNull$$$0(23);
        }
        return list;
    }

    @NotNull
    private List<BuildMessage1> translate(@NotNull AgentRunningBuild build, @NotNull List<BuildMessage1> messages, @NotNull List<BuildMessagesTranslator> translators) {
        if (build == null) {
            BuildAgentImpl.$$$reportNull$$$0(24);
        }
        if (messages == null) {
            BuildAgentImpl.$$$reportNull$$$0(25);
        }
        if (translators == null) {
            BuildAgentImpl.$$$reportNull$$$0(26);
        }
        List result = messages;
        for (BuildMessagesTranslator translator : translators) {
            try {
                result = translator.translateMessages(build, result);
            }
            catch (Throwable e) {
                Loggers.AGENT.warnAndDebugDetails("Error in build messages translator while processing messages: " + translator.getClass().getName() + ", build id: " + build.getBuildId() + ", messages: " + CollectionsUtil.asString((Collection)messages, (int)10) + ", error: " + String.valueOf(e), e);
            }
        }
        List list = result;
        if (list == null) {
            BuildAgentImpl.$$$reportNull$$$0(27);
        }
        return list;
    }

    @NotNull
    private List<BuildMessagesTranslator> getBuildMessagesTranslators() {
        Collection extensions = this.myAgentExtensions.getExtensions(BuildMessagesTranslator.class);
        return new ArrayList<BuildMessagesTranslator>(extensions);
    }

    private boolean isStarted(String buildId) {
        return this.myStartedBuilds.contains(buildId);
    }

    @NotNull
    public BuildProgressLogger getLogger() {
        BaseServerLoggerFacade baseServerLoggerFacade = this.myLogger;
        if (baseServerLoggerFacade == null) {
            BuildAgentImpl.$$$reportNull$$$0(28);
        }
        return baseServerLoggerFacade;
    }

    public void start() {
        LOG.info("Start build agent");
        this.cleanupWorkingDir();
        this.startAgentWebServer();
        this.myShutdownHook = new Thread((Runnable)new /* Unavailable Anonymous Inner Class!! */);
        Runtime.getRuntime().addShutdownHook(this.myShutdownHook);
        this.myAgentOperationModeHolder.getOperationMode().beforeAgentStart();
        this.myAgentStarted = true;
        ((AgentLifeCycleListener)this.myEventDispatcher.getMulticaster()).agentStarted((BuildAgent)this);
        LOG.info("Build agent started");
        this.myAgentOperationModeHolder.getOperationMode().onAgentStart();
    }

    public void applyConfigurationToAgent() {
        Map configuration = this.myAgentConfigurationStatePersistence.fetchConfiguration();
        this.applyConfigurationToAgent(configuration);
    }

    public void applyConfigurationToAgent(Map<String, String> configuration) {
        AgentContextEx agentContext = this.myConfiguration.getAgentContext();
        Map buildParameters = agentContext.getBuildParameters().getAllParameters();
        Map configurationParameters = agentContext.getConfigurationParameters();
        for (Map.Entry<String, String> configurationParameter : configuration.entrySet()) {
            String key = configurationParameter.getKey();
            String value = configurationParameter.getValue();
            if (key.startsWith("system.") && !buildParameters.containsKey(key)) {
                agentContext.addSystemProperty(key.substring("system.".length()), value);
                continue;
            }
            if (key.startsWith("env.") && !buildParameters.containsKey(key)) {
                agentContext.addEnvironmentVariable(key.substring("env.".length()), value);
                continue;
            }
            if (configurationParameters.containsKey(key)) continue;
            agentContext.addConfigurationParameter(key, value);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Integer getId() {
        Object object = this.myIdLock;
        synchronized (object) {
            return this.myId;
        }
    }

    @Nullable
    public String getBuildId() {
        BuildRunAgentState state = this.getRunningState();
        if (state != null) {
            return state.getBuildId();
        }
        return null;
    }

    @Nullable
    public BuildRunAgentState getRunningState() {
        return this.myRunningState.getState();
    }

    private String getVersionIfNotDisabled(String version) {
        if (this.myConfiguration.isUpgradeDisabled()) {
            return "unknown";
        }
        return version;
    }

    @Used(value="Can be called from older server to get version")
    @NotNull
    public String getVersion() {
        String string = this.getVersionIfNotDisabled(ServerVersionHolder.getVersion().getBuildNumber());
        if (string == null) {
            BuildAgentImpl.$$$reportNull$$$0(29);
        }
        return string;
    }

    @Used(value="Can be called from older server to get version")
    @NotNull
    public String getPluginsSignature() {
        String string = this.getVersionIfNotDisabled(this.doGetPluginsSignature());
        if (string == null) {
            BuildAgentImpl.$$$reportNull$$$0(30);
        }
        return string;
    }

    @Nullable
    public AgentUpdateInfo loadAgentUpdateInfo() {
        return ((AgentVersionController)this.myAgentExtensions.getSingletonService(AgentVersionController.class)).loadAgentUpdateInfo();
    }

    @NotNull
    private String doGetPluginsSignature() {
        if (LogInitializer.isUnitTest()) {
            return "test mode";
        }
        AgentUpdateInfo inf = this.loadAgentUpdateInfo();
        String string = inf == null ? "NA" : inf.getRootHash();
        if (string == null) {
            BuildAgentImpl.$$$reportNull$$$0(31);
        }
        return string;
    }

    @NotNull
    public String getOperatingSystemName() {
        Object arch = StringUtil.emptyIfNull((String)System.getProperty("os.arch"));
        if (!StringUtil.isEmpty((String)arch)) {
            arch = " " + (String)arch;
        }
        String string = System.getProperty("os.name") + (String)arch + ", version " + System.getProperty("os.version");
        if (string == null) {
            BuildAgentImpl.$$$reportNull$$$0(32);
        }
        return string;
    }

    @Used(value="launcher")
    public boolean shutdown() {
        LOG.info("Received \"stop force\" command from launcher" + (this.getRunningState() != null ? ". Will stop currently running build and exit" : ""));
        this.shutdownInternal(true, !LogInitializer.isUnitTest(), AgentExitCode.DIE_USER_COMMAND);
        return true;
    }

    @Used(value="launcher")
    public boolean shutdownWaitForBuild() {
        LOG.info("Received stop command from launcher" + (this.getRunningState() != null ? ". Will wait for currently running build to finish before exiting" : ""));
        this.shutdownInternal(false, !LogInitializer.isUnitTest(), AgentExitCode.DIE_USER_COMMAND);
        return true;
    }

    public void shutdownAndWait() {
        this.shutdownInternal(true, false, AgentExitCode.DIE_USER_COMMAND);
        try {
            this.myShutdownTask.waitForShutdown();
        }
        catch (InterruptedException interruptedException) {
            // empty catch block
        }
    }

    @Used(value="tests")
    public boolean isShutdownScheduled() {
        return this.myShutdownTask.isShutdownScheduled();
    }

    @Used(value="tests")
    public boolean isShutdownFinished() {
        return this.myShutdownTask.isShutdownFinished();
    }

    public void shutdownInternal(boolean killBuild, boolean callSystemExit, @NotNull AgentExitCode exitCode) {
        if (exitCode == null) {
            BuildAgentImpl.$$$reportNull$$$0(33);
        }
        if (killBuild) {
            this.stopBuild(BuildInterruptReason.AGENT_SHUTDOWN);
        }
        this.myShutdownTask.shutdownAgent(callSystemExit, exitCode);
    }

    private BuildAgentShutdownTask createShutdownTask() {
        return new BuildAgentShutdownTask((BuildAgentShutdownTask.BuildAgentShutdownCallback)new /* Unavailable Anonymous Inner Class!! */);
    }

    public void shutdownAgentWebServer() {
        if (this.myWebServer != null) {
            LOG.info("Shutdown agent WebServer start");
            this.myWebServer.shutdown();
            this.myWebServer = null;
            LOG.info("Shutdown agent WebServer finish");
        }
    }

    public boolean isAgentWebServerRunning() {
        return this.myWebServer != null;
    }

    @Deprecated
    public void init(String[] args) {
    }

    public void preInit(@NotNull AgentCommandlineArguments args) {
        File configurationFile;
        if (args == null) {
            BuildAgentImpl.$$$reportNull$$$0(34);
        }
        if ((configurationFile = args.getConfigurationFile()) != null) {
            try {
                this.myConfiguration.initFrom(configurationFile);
            }
            catch (Throwable e) {
                LOG.warn("Failed to initialize build agent configuration from " + String.valueOf(configurationFile), e);
            }
        }
        this.myAgentOperationModeHolder.setOperationMode(args.buildOperationMode(this.myAgentExtensions));
        this.runAgentPreInitActions();
    }

    public void init(@NotNull AgentCommandlineArguments args) {
        if (args == null) {
            BuildAgentImpl.$$$reportNull$$$0(35);
        }
        if (this.myIsInitialized) {
            throw new IllegalStateException("Agent init method has already been called");
        }
        this.myIsInitialized = true;
        LOG.info("Build Agent version: " + this.getVersion() + ", plugins signature: " + this.getPluginsSignature());
        ((AgentLifeCycleListener)this.myEventDispatcher.getMulticaster()).beforeAgentConfigurationLoaded((BuildAgent)this);
        this.myServerMonitor = this.myConfiguration.getOperationMode().isRegistrationOnServerSupported() ? new ServerMonitorImpl((BuildAgentEx)this) : new NoServerMonitor();
        this.parseCommandLineAndFillConfiguration(args);
        ((AgentLifeCycleListener)this.myEventDispatcher.getMulticaster()).afterAgentConfigurationLoaded((BuildAgent)this);
        this.myRunners.loadExtensions(this.myAgentExtensions);
        this.myConfiguration.validateParameters();
        this.myServerProxy = new ServerXmlRpcProxy(this.myConfiguration.getServerUrl(), this.myConfiguration.getServerConnectionTimeout(), (ServerXmlRpcProxy.BuildCredentialsProvider)new /* Unavailable Anonymous Inner Class!! */, this.myConfiguration.getServerProxyHost(), this.myConfiguration.getServerProxyPort(), this.myConfiguration.getServerProxyCredentials(), this.mySslTrustStoreProvider.getTrustStore());
        ((AgentLifeCycleListener)this.myEventDispatcher.getMulticaster()).agentInitialized((BuildAgent)this);
    }

    private void runAgentPreInitActions() {
        for (BuildAgentPreInitAction action : this.myAgentExtensions.getExtensions(BuildAgentPreInitAction.class)) {
            try {
                action.preInitAction();
            }
            catch (Throwable t) {
                LOG.warn("Failed to execute agent pre-init action: " + action.getClass().getSimpleName() + ". " + t.toString(), t);
            }
        }
    }

    public void parseCommandLineAndFillConfiguration(AgentCommandlineArguments args) {
        File buildAgentProperties = args.getConfigurationFile();
        if (buildAgentProperties != null) {
            this.myConfiguration.initFrom(buildAgentProperties);
            this.startFileWatcher(buildAgentProperties);
        }
        args.setupConfigurationParameters(this.myConfiguration);
    }

    private void startFileWatcher(File configFile) {
        if (this.myPropertiesFileWatcher != null) {
            this.myPropertiesFileWatcher.stop();
        }
        this.myPropertiesFileWatcher = new FileWatcher(configFile);
        this.myPropertiesFileWatcher.registerListener((ChangeListener)new /* Unavailable Anonymous Inner Class!! */);
        this.myPropertiesFileWatcher.start();
    }

    @Nullable
    public Server getServerProxy() {
        return this.myServerProxy;
    }

    public BuildAgentConfigurationEx getConfiguration() {
        return this.myConfiguration;
    }

    public Collection<AgentBuildRunner> getRunners() {
        return this.myRunners.getAvailableRunners();
    }

    public void startAgentWebServer() {
        if (!TeamCityProperties.getBooleanOrTrue((String)"teamcity.agent.rpcWebServer.enabled")) {
            return;
        }
        this.myWebServer = WebServer.createStartedServer((int)this.myConfiguration.getOwnPort(), (boolean)TeamCityProperties.getBooleanOrTrue((String)"teamcity.agent.startWebServerOnLocalhost"));
        this.myWebServer.setKeepAlive(true);
        BuildAgentImpl handler = TeamCityProperties.getBooleanOrTrue((String)"teamcity.agent.allowOnlyLogMessagesForWebServer") ? new XmlRpcLogHandler(this) : this;
        this.myWebServer.addHandler("buildAgent", (Object)handler);
        this.myWebServer.setSocketReadTimeout(this.myConfiguration.getServerConnectionTimeout() * 1000);
        this.myConfiguration.setOwnPort(this.myWebServer.getPort());
        new /* Unavailable Anonymous Inner Class!! */;
        LOG.info("Agent Web server started on port " + this.myWebServer.getPort());
        this.myXmlRpcHandlerManager = new /* Unavailable Anonymous Inner Class!! */;
    }

    public XmlRpcHandlerManager getXmlRpcHandlerManager() {
        return this.myXmlRpcHandlerManager;
    }

    public void updateBuildAgentParametersOnServer() {
        if (this.isAgentStarted()) {
            AgentDetails ad = this.myDetailsFactory.createAgentDetails((BuildAgentEx)this, this.getBuildId());
            ServerXmlRpcProxy serverProxy = this.myServerProxy;
            if (!(serverProxy == null || this.myAgentDetailsSnapshot != null && ad.equals((Object)this.myAgentDetailsSnapshot))) {
                LOG.info("Updating agent parameters on the server: " + String.valueOf(ad));
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Updating agent parameters on the server: " + ad.serializeToString());
                }
                serverProxy.updateAgentParameteres(ad.serializeToString());
                this.myAgentDetailsSnapshot = ad;
            }
        }
    }

    private boolean isAgentStarted() {
        return this.myAgentStarted && this.getId() != null;
    }

    @Nullable
    public CommunicationProtocol getProtocol() {
        return this.myUsedProtocol;
    }

    public boolean registerOnBuildServer(String buildId) {
        if (!this.myRegisterInProgress.tryAcquire()) {
            LOG.warn("Registration attempt for buildId: " + buildId + " was ignored. RegistrationInProgress lock was held");
            return this.myRegistered;
        }
        try {
            this.myRegistered = this.doRegisterOnBuildServer(buildId);
            if (this.myRegistered) {
                this.myServerMonitor.ping();
            }
            boolean bl = this.myRegistered;
            return bl;
        }
        finally {
            this.myRegisterInProgress.release();
        }
    }

    public boolean isRegisterInProgress() {
        return this.myRegisterInProgress.availablePermits() == 0;
    }

    public void waitForRegistrationToFinish() throws InterruptedException {
        LOG.info("Wait for registration to finish, currently in progress: " + this.isRegisterInProgress());
        int count = 0;
        while (!this.isRegisterInProgress() && count++ < 50) {
            Thread.sleep(10L);
        }
        this.myRegisterInProgress.acquire();
        this.myRegisterInProgress.release();
        LOG.info("Done waiting for registration to finish");
    }

    private boolean doRegisterOnBuildServer(String buildId) {
        try {
            AgentDetails ad = this.myDetailsFactory.createAgentDetails((BuildAgentEx)this, buildId);
            RegistrationLogger.startRegistration((BuildAgentConfigurationEx)this.myConfiguration, (AgentDetails)ad);
            this.myAgentDetailsSnapshot = ad;
            AgentRegistrationDetails details = null;
            Iterable supportedProtocols = this.myServerCommunicationProtocolResolver.resolve();
            CommunicationProtocol successfulProtocol = null;
            for (CommunicationProtocol protocol : supportedProtocols) {
                try {
                    RegistrationLogger.info((String)("Trying to register on server using '" + protocol.getId() + "' protocol."));
                    details = protocol.registerOnServer(ad, this);
                    this.myConfiguration.addConfigurationParameter("teamcity.agent.protocol", protocol.getId());
                    successfulProtocol = protocol;
                    break;
                }
                catch (ProtocolDoesNotWorkException e) {
                    if (e.getCause() != null) {
                        RegistrationLogger.info((String)("Registration using '" + protocol.getId() + "' protocol failed"), (Throwable)e.getCause());
                        continue;
                    }
                    RegistrationLogger.info((String)("Registration using '" + protocol.getId() + "' protocol failed: " + e.getMessage()));
                }
            }
            if (details == null) {
                RegistrationLogger.failToRegister((String)this.myConfiguration.getServerUrl(), null);
                return false;
            }
            this.myUsedProtocol = successfulProtocol;
            RegistrationLogger.registered(details, successfulProtocol);
            this.updateSettingsIfNeeded(details);
            this._setId(details.getAgentId());
            this.myFirstDuplicatedAgentRegistrationErrorTime = null;
            return true;
        }
        catch (AlreadyExistsException e) {
            Integer serverId = e.getBuildAgentId();
            if (serverId.equals(this.getId())) {
                RegistrationLogger.info((String)("Agent is still registered with id " + this.getId()));
                return true;
            }
            if (this.myFirstDuplicatedAgentRegistrationErrorTime == null) {
                this.myFirstDuplicatedAgentRegistrationErrorTime = System.nanoTime();
            }
            if (TimeUnit.NANOSECONDS.toSeconds(System.nanoTime() - this.myFirstDuplicatedAgentRegistrationErrorTime) > 900L) {
                Loggers.REGISTRATION.warn("Failed to register on the server: another agent with the same authorization token and name is registered on the server. Ensure that there is only one agent process started from the installation directory. Shutting down, because this errors repeats for more than 15 minutes");
                this.shutdownInternal(true, !LogInitializer.isUnitTest(), AgentExitCode.DIE_USER_COMMAND);
            } else {
                RegistrationLogger.warn((String)"Failed to register on the server: another agent with the same authorization token and name is registered on the server. Continue attempts to register...");
            }
            return false;
        }
        catch (InvalidRunningBuildException e) {
            RegistrationLogger.warn((String)("My running build " + buildId + " is obsolete. Will try to stop it and reregister cleanly"));
            this.myLogProxy.killAllPendingAndIgnore(buildId);
            this.stopBuild(BuildInterruptReason.NOT_RUNNING_BUILD_ON_SERVER);
            this.myServerMonitor.scheduleReregister();
            return false;
        }
        catch (Exception e) {
            RegistrationLogger.failToRegister((String)this.myConfiguration.getServerUrl(), (String)e.getLocalizedMessage());
            return false;
        }
    }

    private void updateSettingsIfNeeded(@NotNull AgentRegistrationDetails details) {
        if (details == null) {
            BuildAgentImpl.$$$reportNull$$$0(36);
        }
        this.runWithDisabledFileWatcher((Runnable)new /* Unavailable Anonymous Inner Class!! */);
    }

    public void runWithDisabledFileWatcher(@NotNull Runnable action) {
        if (action == null) {
            BuildAgentImpl.$$$reportNull$$$0(37);
        }
        if (this.myPropertiesFileWatcher != null) {
            this.myPropertiesFileWatcher.runActionWithDisabledObserver(action);
        } else {
            action.run();
        }
    }

    @Nullable
    public String getOwnAddress() {
        if (!TeamCityProperties.getBoolean((String)"teamcity.agent.resolveOwnAddress")) {
            return this.myConfiguration.getOwnAddress();
        }
        String address = this.myConfiguration.getOwnAddress();
        if (address != null) {
            try {
                return InetAddress.getByName(address).getHostAddress();
            }
            catch (UnknownHostException e) {
                Loggers.AGENT.warn("Failed to resolve own address: " + address + ", error: " + e.toString());
            }
        }
        return address;
    }

    public AgentConfigurationStatePersistenceManager getAgentConfigurationStatePersistence() {
        return this.myAgentConfigurationStatePersistence;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void unregisterFromBuildServer() {
        Object object = this.myIdLock;
        synchronized (object) {
            if (this.myId != null) {
                LOG.info("Unregistering from the server. Agent id: " + this.myId);
                ServerXmlRpcProxy serverProxy = this.myServerProxy;
                if (serverProxy != null) {
                    LOG.info("Before unregistering from the server.");
                    serverProxy.unregister(this.myId.intValue(), this.myConfiguration.getAuthorizationToken());
                    LOG.info("Unregistered from the server.");
                }
                this.myId = null;
            }
        }
    }

    public boolean isRunning() {
        return this.getBuildId() != null || this.myLogProxy.hasPendingCommands();
    }

    public void ensureIdle() {
        while (this.isRunning()) {
            try {
                this.myLogProxy.wakeUpFlush();
                Thread.sleep(100L);
            }
            catch (InterruptedException interruptedException) {}
        }
    }

    public void ensureIdleAndShutdown() {
        this.ensureIdle();
        this.shutdownInternal(true, false, AgentExitCode.RESTART_UPGRADE_FAILED);
    }

    public AgentLogProxy _getLogProxy() {
        return this.myLogProxy;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void _setId(@Nullable Integer id) {
        Object object = this.myIdLock;
        synchronized (object) {
            this.myId = id;
        }
    }

    public void _setRunBuildCommandListener(Action<AgentBuild> action) {
        this.myOnRunBuildCommandListener = action;
    }

    public ServerMonitor getServerMonitor() {
        return this.myServerMonitor;
    }

    public void restart() {
        AgentExitCode.RESTART.exitApplication();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Contract(value="_ -> exit")
    public void restartOnError(@NotNull String logMessage, @NotNull Throwable error) {
        if (logMessage == null) {
            BuildAgentImpl.$$$reportNull$$$0(38);
        }
        if (error == null) {
            BuildAgentImpl.$$$reportNull$$$0(39);
        }
        String enableDumpOption = this.myConfiguration.getInternalProperty(TEAMCITY_AGENT_DUMP_ON_OOM, null);
        if (error instanceof OutOfMemoryError && "false".equals(enableDumpOption)) {
            throw (OutOfMemoryError)error;
        }
        try {
            if (error instanceof OutOfMemoryError) {
                if (this.shouldCreateDump(enableDumpOption)) {
                    this.createMemoryDumpFile();
                } else {
                    Loggers.AGENT.warn("OutOfMemoryError detected, to create memory dump, add teamcity.agent.dump.on.oom property to agent");
                }
            }
        }
        finally {
            Loggers.AGENT.warn("Restarting agent due to a critical error. " + logMessage, error);
            this.myCriticalErrorRestart = true;
            this.restart();
        }
    }

    private boolean shouldCreateDump(@Nullable String enableDumpsOption) {
        try {
            if ("true".equals(enableDumpsOption)) {
                return true;
            }
            if ("false".equals(enableDumpsOption)) {
                return false;
            }
            List<String> inputArguments = ManagementFactory.getRuntimeMXBean().getInputArguments();
            return inputArguments.contains("-XX:+HeapDumpOnOutOfMemoryError");
        }
        catch (Throwable e) {
            Loggers.AGENT.error(e);
            return false;
        }
    }

    private void createMemoryDumpFile() {
        File dumpsDir = new File(this.myConfiguration.getAgentLogsDirectory(), "memoryDumps");
        LOG.warn("OutOfMemoryError detected, saving memory dump to " + dumpsDir.getAbsolutePath());
        try {
            dumpsDir.mkdirs();
            File dump = DiagnosticUtil.memoryDump((File)dumpsDir, (boolean)DiagnosticUtil.getDefaultLiveObjectsOnly());
            LOG.warn("Memory dump file was created: " + dump.getAbsolutePath());
        }
        catch (Throwable e) {
            LOG.warn("Cannot create memory dump file", e);
        }
    }

    @Used(value="test")
    public void checkPropertiesChanged() {
        this.myPropertiesFileWatcher.checkForModifications();
    }

    @Used(value="test")
    public void setServerCommunicationProtocolResolver(IServerCommunicationProtocolResolver serverCommunicationProtocolResolver) {
        this.myServerCommunicationProtocolResolver = serverCommunicationProtocolResolver;
    }

    private static /* synthetic */ void $$$reportNull$$$0(int n) {
        Object[] objectArray;
        Object[] objectArray2;
        Object[] objectArray3 = new Object[switch (n) {
            default -> 3;
            case 11, 14, 23, 27, 28, 29, 30, 31, 32 -> 2;
        }];
        switch (n) {
            default: {
                objectArray2 = objectArray3;
                objectArray3[0] = "configuration";
                break;
            }
            case 1: {
                objectArray2 = objectArray3;
                objectArray3[0] = "agentLifeCycleDispatcher";
                break;
            }
            case 2: {
                objectArray2 = objectArray3;
                objectArray3[0] = "agentExtensionsLocator";
                break;
            }
            case 3: {
                objectArray2 = objectArray3;
                objectArray3[0] = "runnersRegistry";
                break;
            }
            case 4: {
                objectArray2 = objectArray3;
                objectArray3[0] = "detailsFactory";
                break;
            }
            case 5: {
                objectArray2 = objectArray3;
                objectArray3[0] = "serverCommunicationProtocolResolver";
                break;
            }
            case 6: {
                objectArray2 = objectArray3;
                objectArray3[0] = "sslTrustStoreProvider";
                break;
            }
            case 7: {
                objectArray2 = objectArray3;
                objectArray3[0] = "agentConfigurationStatePersistence";
                break;
            }
            case 8: {
                objectArray2 = objectArray3;
                objectArray3[0] = "agentOperationModeHolder";
                break;
            }
            case 9: {
                objectArray2 = objectArray3;
                objectArray3[0] = "logProxy";
                break;
            }
            case 10: {
                objectArray2 = objectArray3;
                objectArray3[0] = "reason";
                break;
            }
            case 11: 
            case 14: 
            case 23: 
            case 27: 
            case 28: 
            case 29: 
            case 30: 
            case 31: 
            case 32: {
                objectArray2 = objectArray3;
                objectArray3[0] = "jetbrains/buildServer/agent/impl/BuildAgentImpl";
                break;
            }
            case 12: {
                objectArray2 = objectArray3;
                objectArray3[0] = "serializedAgentBuild";
                break;
            }
            case 13: 
            case 16: {
                objectArray2 = objectArray3;
                objectArray3[0] = "agentBuild";
                break;
            }
            case 15: {
                objectArray2 = objectArray3;
                objectArray3[0] = "state";
                break;
            }
            case 17: {
                objectArray2 = objectArray3;
                objectArray3[0] = "message";
                break;
            }
            case 18: {
                objectArray2 = objectArray3;
                objectArray3[0] = "messagesXml";
                break;
            }
            case 19: 
            case 24: {
                objectArray2 = objectArray3;
                objectArray3[0] = "build";
                break;
            }
            case 20: 
            case 26: {
                objectArray2 = objectArray3;
                objectArray3[0] = "translators";
                break;
            }
            case 21: {
                objectArray2 = objectArray3;
                objectArray3[0] = "messagesToTranslate";
                break;
            }
            case 22: {
                objectArray2 = objectArray3;
                objectArray3[0] = "serialized";
                break;
            }
            case 25: {
                objectArray2 = objectArray3;
                objectArray3[0] = "messages";
                break;
            }
            case 33: {
                objectArray2 = objectArray3;
                objectArray3[0] = "exitCode";
                break;
            }
            case 34: 
            case 35: {
                objectArray2 = objectArray3;
                objectArray3[0] = "args";
                break;
            }
            case 36: {
                objectArray2 = objectArray3;
                objectArray3[0] = "details";
                break;
            }
            case 37: {
                objectArray2 = objectArray3;
                objectArray3[0] = "action";
                break;
            }
            case 38: {
                objectArray2 = objectArray3;
                objectArray3[0] = "logMessage";
                break;
            }
            case 39: {
                objectArray2 = objectArray3;
                objectArray3[0] = "error";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray2;
                objectArray2[1] = "jetbrains/buildServer/agent/impl/BuildAgentImpl";
                break;
            }
            case 11: {
                objectArray = objectArray2;
                objectArray2[1] = "getRunnersRegistry";
                break;
            }
            case 14: {
                objectArray = objectArray2;
                objectArray2[1] = "runBuild";
                break;
            }
            case 23: {
                objectArray = objectArray2;
                objectArray2[1] = "deserializeMessages";
                break;
            }
            case 27: {
                objectArray = objectArray2;
                objectArray2[1] = "translate";
                break;
            }
            case 28: {
                objectArray = objectArray2;
                objectArray2[1] = "getLogger";
                break;
            }
            case 29: {
                objectArray = objectArray2;
                objectArray2[1] = "getVersion";
                break;
            }
            case 30: {
                objectArray = objectArray2;
                objectArray2[1] = "getPluginsSignature";
                break;
            }
            case 31: {
                objectArray = objectArray2;
                objectArray2[1] = "doGetPluginsSignature";
                break;
            }
            case 32: {
                objectArray = objectArray2;
                objectArray2[1] = "getOperatingSystemName";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray;
                objectArray[2] = "<init>";
                break;
            }
            case 9: {
                objectArray = objectArray;
                objectArray[2] = "setLogProxy";
                break;
            }
            case 10: {
                objectArray = objectArray;
                objectArray[2] = "stopBuild";
                break;
            }
            case 11: 
            case 14: 
            case 23: 
            case 27: 
            case 28: 
            case 29: 
            case 30: 
            case 31: 
            case 32: {
                break;
            }
            case 12: 
            case 13: {
                objectArray = objectArray;
                objectArray[2] = "runBuild";
                break;
            }
            case 15: 
            case 16: {
                objectArray = objectArray;
                objectArray[2] = "doActualBuild";
                break;
            }
            case 17: {
                objectArray = objectArray;
                objectArray[2] = "sendSingleMessage";
                break;
            }
            case 18: {
                objectArray = objectArray;
                objectArray[2] = "sendSerializedMessages";
                break;
            }
            case 19: 
            case 20: 
            case 21: {
                objectArray = objectArray;
                objectArray[2] = "translateAndSend";
                break;
            }
            case 22: {
                objectArray = objectArray;
                objectArray[2] = "deserializeMessages";
                break;
            }
            case 24: 
            case 25: 
            case 26: {
                objectArray = objectArray;
                objectArray[2] = "translate";
                break;
            }
            case 33: {
                objectArray = objectArray;
                objectArray[2] = "shutdownInternal";
                break;
            }
            case 34: {
                objectArray = objectArray;
                objectArray[2] = "preInit";
                break;
            }
            case 35: {
                objectArray = objectArray;
                objectArray[2] = "init";
                break;
            }
            case 36: {
                objectArray = objectArray;
                objectArray[2] = "updateSettingsIfNeeded";
                break;
            }
            case 37: {
                objectArray = objectArray;
                objectArray[2] = "runWithDisabledFileWatcher";
                break;
            }
            case 38: 
            case 39: {
                objectArray = objectArray;
                objectArray[2] = "restartOnError";
                break;
            }
        }
        String string = String.format(v0, objectArray);
        throw switch (n) {
            default -> new IllegalArgumentException(string);
            case 11, 14, 23, 27, 28, 29, 30, 31, 32 -> new IllegalStateException(string);
        };
    }
}
