/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.intellij.openapi.diagnostic.Logger
 *  com.jetbrains.launcher.AppConfigFilesEx
 *  com.jetbrains.launcher.AppConfigFilesImpl
 *  com.jetbrains.launcher.AppFiles
 *  com.jetbrains.launcher.AppFilesImpl
 *  com.jetbrains.launcher.AppFixedFilesEx
 *  com.jetbrains.launcher.AppFixedFilesImpl
 *  jetbrains.buildServer.agent.AgentCommandlineArguments
 *  jetbrains.buildServer.agent.AgentFiles
 *  jetbrains.buildServer.agent.AgentMain
 *  jetbrains.buildServer.agent.AgentPluginsLoadedEventFire
 *  jetbrains.buildServer.agent.AgentStartCallback
 *  jetbrains.buildServer.agent.BuildAgent
 *  jetbrains.buildServer.agent.impl.BuildAgentEx
 *  jetbrains.buildServer.agent.impl.BuildAgentStartFailedException
 *  jetbrains.buildServer.agent.impl.upgrade.AgentExitCode
 *  jetbrains.buildServer.agent.plugins.AgentPluginsLoader
 *  jetbrains.buildServer.log.LogInitializer
 *  jetbrains.buildServer.serverSide.StartupEnvironmentVariable
 *  jetbrains.buildServer.serverSide.TeamCityProperties
 *  jetbrains.buildServer.util.DiagnosticUtil
 *  jetbrains.buildServer.util.FileUtil
 *  jetbrains.buildServer.util.StringUtil
 *  jetbrains.buildServer.version.ServerVersionHolder
 *  jetbrains.buildServer.version.ServerVersionInfo
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 *  org.springframework.context.support.ClassPathXmlApplicationContext
 */
package jetbrains.buildServer.agent;

import com.intellij.openapi.diagnostic.Logger;
import com.jetbrains.launcher.AppConfigFilesEx;
import com.jetbrains.launcher.AppConfigFilesImpl;
import com.jetbrains.launcher.AppFiles;
import com.jetbrains.launcher.AppFilesImpl;
import com.jetbrains.launcher.AppFixedFilesEx;
import com.jetbrains.launcher.AppFixedFilesImpl;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import jetbrains.buildServer.agent.AgentCommandlineArguments;
import jetbrains.buildServer.agent.AgentFiles;
import jetbrains.buildServer.agent.AgentMain;
import jetbrains.buildServer.agent.AgentPluginsLoadedEventFire;
import jetbrains.buildServer.agent.AgentStartCallback;
import jetbrains.buildServer.agent.BuildAgent;
import jetbrains.buildServer.agent.impl.BuildAgentEx;
import jetbrains.buildServer.agent.impl.BuildAgentStartFailedException;
import jetbrains.buildServer.agent.impl.upgrade.AgentExitCode;
import jetbrains.buildServer.agent.plugins.AgentPluginsLoader;
import jetbrains.buildServer.log.LogInitializer;
import jetbrains.buildServer.serverSide.StartupEnvironmentVariable;
import jetbrains.buildServer.serverSide.TeamCityProperties;
import jetbrains.buildServer.util.DiagnosticUtil;
import jetbrains.buildServer.util.FileUtil;
import jetbrains.buildServer.util.StringUtil;
import jetbrains.buildServer.version.ServerVersionHolder;
import jetbrains.buildServer.version.ServerVersionInfo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AgentMain2 {
    private static Logger LOG = Logger.getInstance((String)AgentMain2.class.getName());

    @Deprecated
    public static void main(@NotNull String[] args) {
        String logsPath;
        if (args == null) {
            AgentMain2.$$$reportNull$$$0(0);
        }
        AgentMain2.main2(args, null, (logsPath = System.getProperty("teamcity_logs")) == null ? null : AgentMain2.patchPath(logsPath), AgentStartCallback.MOCK);
    }

    @NotNull
    private static String patchPath(@NotNull String oldPath) {
        if (oldPath == null) {
            AgentMain2.$$$reportNull$$$0(1);
        }
        if (new File(oldPath).isAbsolute()) {
            String string = oldPath;
            if (string == null) {
                AgentMain2.$$$reportNull$$$0(2);
            }
            return string;
        }
        String string = "bin" + File.separator + oldPath;
        if (string == null) {
            AgentMain2.$$$reportNull$$$0(3);
        }
        return string;
    }

    public static void main2(@NotNull String[] args, @Nullable AgentFiles _agentFiles, @Nullable String appLogsDirPath, @NotNull AgentStartCallback callback) {
        if (args == null) {
            AgentMain2.$$$reportNull$$$0(4);
        }
        if (callback == null) {
            AgentMain2.$$$reportNull$$$0(5);
        }
        LogInitializer.initBootstrapLogging();
        AgentFiles agentFiles = _agentFiles == null ? AgentMain2.getAgentFiles(appLogsDirPath) : _agentFiles;
        AgentCommandlineArguments commandlineArguments = new AgentCommandlineArguments(args);
        AgentMain2.setupTeamCityProperties(commandlineArguments);
        LogInitializer.initDistAgentLogConfiguration((Path)agentFiles.getAppConfFolder().toPath());
        LogInitializer.initAgentLogging();
        LOG = Logger.getInstance((String)AgentMain2.class.getName());
        LOG.info("\n\n\n\n\n\n");
        LOG.info("===========================================================");
        LOG.info("TeamCity Build Agent " + AgentMain2.getAgentVersion());
        LOG.info(DiagnosticUtil.getOsEnvironmentDetails());
        LOG.info(DiagnosticUtil.getJavaDetails());
        LOG.info("Agent home is \"" + String.valueOf(AgentMain.getAgentHome()) + "\"");
        LOG.info("Starting...");
        AgentMain2.checkForSensitiveEnvironmentVariables();
        if (TeamCityProperties.getBooleanOrTrue((String)"teamcity.setDefaultUncaughtExceptionHandler")) {
            Thread.UncaughtExceptionHandler oldHandler = Thread.getDefaultUncaughtExceptionHandler();
            Thread.setDefaultUncaughtExceptionHandler((Thread.UncaughtExceptionHandler)new /* Unavailable Anonymous Inner Class!! */);
        }
        AgentMain2.processUpgradeErrors(agentFiles);
        ClassPathXmlApplicationContext beanFactory = AgentMain2.createRootApplicationContext(agentFiles);
        AgentMain2.preInitAgent(beanFactory, commandlineArguments);
        AgentMain2.loadPlugins(beanFactory);
        AgentMain2.firePluginsLoaded(beanFactory);
        AgentMain2.startAgent(beanFactory, commandlineArguments, callback);
    }

    private static void checkForSensitiveEnvironmentVariables() {
        for (StartupEnvironmentVariable env : StartupEnvironmentVariable.values()) {
            if (StringUtil.isEmpty((String)System.getenv(env.name()))) continue;
            LOG.warn("The environment variable " + env.name() + " is set and is visible to the TeamCity agent.\nThis environment variable is sensitive and its value should not be exposed to the builds executed by the agent.\nPlease make sure that the agent is using some other user account to avoid sharing sensitive variables with builds.");
            AgentExitCode.START_ERROR.exitApplication();
        }
    }

    private static void setupTeamCityProperties(@NotNull AgentCommandlineArguments commandlineArguments) {
        if (commandlineArguments == null) {
            AgentMain2.$$$reportNull$$$0(6);
        }
        new /* Unavailable Anonymous Inner Class!! */;
    }

    private static void processUpgradeErrors(AgentFiles agentFiles) {
        File errorFile = agentFiles.getUpgradeErrorLogFile();
        if (!errorFile.isFile()) {
            return;
        }
        try {
            String error = new String(FileUtil.loadFileText((File)errorFile, (String)"utf-8"));
            if (error.length() > 0) {
                LOG.error("\n\n\nPrevious upgrade attempt failed with message (check upgrade.log for more details): \n\n" + error);
            }
        }
        catch (IOException e) {
            LOG.debug("Failed to read " + String.valueOf(errorFile));
        }
        FileUtil.delete((File)errorFile);
    }

    private static String getAgentVersion() {
        try {
            ServerVersionInfo version = ServerVersionHolder.getVersion();
            return version.getDisplayVersion() + " (build " + version.getBuildNumber() + ")";
        }
        catch (Throwable t) {
            LOG.warn("Failed to get agent version: " + t.getMessage(), t);
            return "(version is unknown)";
        }
    }

    @Deprecated
    private static AgentFiles getAgentFiles(@Nullable String appLogsDirPath) {
        try {
            File home = AgentMain.getAgentHome();
            LOG.debug("Agent home is " + String.valueOf(home));
            return new AgentFiles((AppFiles)new AppFilesImpl((AppConfigFilesEx)new AppConfigFilesImpl((AppFixedFilesEx)new AppFixedFilesImpl(home), null), null, null, appLogsDirPath, null));
        }
        catch (Throwable e) {
            LOG.error("Failed to start build agent. " + e.getMessage(), e);
            AgentExitCode.CORRUPTED_AGENT_ERROR.exitApplication();
            throw new RuntimeException("Agent installation is corrupted");
        }
    }

    @NotNull
    private static ClassPathXmlApplicationContext createRootApplicationContext(Object ... wellknownBeans) {
        if (wellknownBeans == null) {
            AgentMain2.$$$reportNull$$$0(7);
        }
        try {
            return new /* Unavailable Anonymous Inner Class!! */;
        }
        catch (Throwable e) {
            LOG.error("Failed to create Spring context.", e);
            AgentExitCode.SPRING_ERROR.exitApplication();
            throw new RuntimeException("Failed to initialize Root Spring context.");
        }
    }

    private static void loadPlugins(ClassPathXmlApplicationContext beanFactory) {
        try {
            AgentPluginsLoader loader = (AgentPluginsLoader)beanFactory.getBean("agentPluginsLoader", AgentPluginsLoader.class);
            loader.initializePlugins();
        }
        catch (Throwable e) {
            LOG.error("Failed to initialize agent plugins.", e);
        }
    }

    private static void firePluginsLoaded(ClassPathXmlApplicationContext beanFactory) {
        try {
            AgentPluginsLoadedEventFire loader = (AgentPluginsLoadedEventFire)beanFactory.getBean("agentPluginsLoaded", AgentPluginsLoadedEventFire.class);
            loader.fireEvent();
        }
        catch (Throwable e) {
            LOG.error("Failed to initialize agent plugins.", e);
        }
    }

    private static void preInitAgent(@NotNull ClassPathXmlApplicationContext beanFactory, @NotNull AgentCommandlineArguments args) {
        if (beanFactory == null) {
            AgentMain2.$$$reportNull$$$0(8);
        }
        if (args == null) {
            AgentMain2.$$$reportNull$$$0(9);
        }
        try {
            BuildAgentEx buildAgent = AgentMain2.getAgent(beanFactory);
            buildAgent.preInit(args);
            LOG.info("Working dir: " + buildAgent.getConfiguration().getWorkDirectory().getAbsolutePath());
            LOG.info("Temp dir: " + buildAgent.getConfiguration().getTempDirectory().getAbsolutePath());
        }
        catch (Throwable t) {
            LOG.error("Failed to call agent preInit. " + t.getMessage());
            LOG.debug("Failed to call agent preInit. " + t.getMessage(), t);
            AgentExitCode.START_ERROR.exitApplication();
        }
    }

    private static void startAgent(ClassPathXmlApplicationContext beanFactory, AgentCommandlineArguments args, @NotNull AgentStartCallback callback) {
        if (callback == null) {
            AgentMain2.$$$reportNull$$$0(10);
        }
        try {
            BuildAgentEx buildAgent = AgentMain2.getAgent(beanFactory);
            buildAgent.init(args);
            buildAgent.start();
            callback.agentStarted((BuildAgent)buildAgent);
        }
        catch (BuildAgentStartFailedException e) {
            LOG.error("\n\n\n");
            LOG.error("Failed to start build agent. " + e.getMessage());
            LOG.debug("Failed to start build agent. " + e.getMessage(), (Throwable)e);
            AgentExitCode.START_ERROR.exitApplication();
        }
        catch (Throwable e) {
            LOG.error("Failed to start build agent. " + e.getMessage(), e);
            AgentExitCode.START_ERROR.exitApplication();
        }
    }

    @NotNull
    private static BuildAgentEx getAgent(@NotNull ClassPathXmlApplicationContext beanFactory) {
        if (beanFactory == null) {
            AgentMain2.$$$reportNull$$$0(11);
        }
        BuildAgentEx buildAgentEx = (BuildAgentEx)beanFactory.getBean("buildAgent", BuildAgentEx.class);
        if (buildAgentEx == null) {
            AgentMain2.$$$reportNull$$$0(12);
        }
        return buildAgentEx;
    }

    private static /* synthetic */ void $$$reportNull$$$0(int n) {
        Object[] objectArray;
        Object[] objectArray2;
        Object[] objectArray3 = new Object[switch (n) {
            default -> 3;
            case 2, 3, 12 -> 2;
        }];
        switch (n) {
            default: {
                objectArray2 = objectArray3;
                objectArray3[0] = "args";
                break;
            }
            case 1: {
                objectArray2 = objectArray3;
                objectArray3[0] = "oldPath";
                break;
            }
            case 2: 
            case 3: 
            case 12: {
                objectArray2 = objectArray3;
                objectArray3[0] = "jetbrains/buildServer/agent/AgentMain2";
                break;
            }
            case 5: 
            case 10: {
                objectArray2 = objectArray3;
                objectArray3[0] = "callback";
                break;
            }
            case 6: {
                objectArray2 = objectArray3;
                objectArray3[0] = "commandlineArguments";
                break;
            }
            case 7: {
                objectArray2 = objectArray3;
                objectArray3[0] = "wellknownBeans";
                break;
            }
            case 8: 
            case 11: {
                objectArray2 = objectArray3;
                objectArray3[0] = "beanFactory";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray2;
                objectArray2[1] = "jetbrains/buildServer/agent/AgentMain2";
                break;
            }
            case 2: 
            case 3: {
                objectArray = objectArray2;
                objectArray2[1] = "patchPath";
                break;
            }
            case 12: {
                objectArray = objectArray2;
                objectArray2[1] = "getAgent";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray;
                objectArray[2] = "main";
                break;
            }
            case 1: {
                objectArray = objectArray;
                objectArray[2] = "patchPath";
                break;
            }
            case 2: 
            case 3: 
            case 12: {
                break;
            }
            case 4: 
            case 5: {
                objectArray = objectArray;
                objectArray[2] = "main2";
                break;
            }
            case 6: {
                objectArray = objectArray;
                objectArray[2] = "setupTeamCityProperties";
                break;
            }
            case 7: {
                objectArray = objectArray;
                objectArray[2] = "createRootApplicationContext";
                break;
            }
            case 8: 
            case 9: {
                objectArray = objectArray;
                objectArray[2] = "preInitAgent";
                break;
            }
            case 10: {
                objectArray = objectArray;
                objectArray[2] = "startAgent";
                break;
            }
            case 11: {
                objectArray = objectArray;
                objectArray[2] = "getAgent";
                break;
            }
        }
        String string = String.format(v0, objectArray);
        throw switch (n) {
            default -> new IllegalArgumentException(string);
            case 2, 3, 12 -> new IllegalStateException(string);
        };
    }
}
