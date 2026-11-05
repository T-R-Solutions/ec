/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletContext
 *  jetbrains.buildServer.BuildProject
 *  jetbrains.buildServer.log.LogInitializer
 *  jetbrains.buildServer.log.Loggers
 *  jetbrains.buildServer.serverSide.ConfigActionFactory
 *  jetbrains.buildServer.serverSide.ProjectManagerEx
 *  jetbrains.buildServer.serverSide.SBuildServer
 *  jetbrains.buildServer.serverSide.SPersistentEntity
 *  jetbrains.buildServer.serverSide.ServerPaths
 *  jetbrains.buildServer.serverSide.ServerSettings
 *  jetbrains.buildServer.serverSide.TestServerMode
 *  jetbrains.buildServer.serverSide.crypt.RSACipher
 *  jetbrains.buildServer.serverSide.impl.BuildServerImpl
 *  jetbrains.buildServer.serverSide.impl.BuildServerLifecycleProcessor
 *  jetbrains.buildServer.serverSide.impl.ProjectFactory
 *  jetbrains.buildServer.serverSide.impl.projects.ConfigurationPersister
 *  jetbrains.buildServer.serverSide.impl.projects.ProjectConfigFilesCache
 *  jetbrains.buildServer.serverSide.impl.projects.ProjectImpl
 *  jetbrains.buildServer.serverSide.impl.xml.ConfigurationInitializer
 *  jetbrains.buildServer.util.FileUtil
 *  jetbrains.buildServer.util.NamedDaemonThreadFactory
 *  jetbrains.buildServer.web.impl.WebResourcesUnpacker
 *  jetbrains.buildServer.web.plugins.PluginManagerConfigurator
 *  org.jetbrains.annotations.NonNls
 *  org.jetbrains.annotations.NotNull
 *  org.springframework.beans.BeansException
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.ApplicationContextAware
 *  org.springframework.web.context.WebApplicationContext
 */
package jetbrains.buildServer.web.impl;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Map;
import javax.servlet.ServletContext;
import jetbrains.buildServer.BuildProject;
import jetbrains.buildServer.log.LogInitializer;
import jetbrains.buildServer.log.Loggers;
import jetbrains.buildServer.serverSide.ConfigActionFactory;
import jetbrains.buildServer.serverSide.ProjectManagerEx;
import jetbrains.buildServer.serverSide.SBuildServer;
import jetbrains.buildServer.serverSide.SPersistentEntity;
import jetbrains.buildServer.serverSide.ServerPaths;
import jetbrains.buildServer.serverSide.ServerSettings;
import jetbrains.buildServer.serverSide.TestServerMode;
import jetbrains.buildServer.serverSide.crypt.RSACipher;
import jetbrains.buildServer.serverSide.impl.BuildServerImpl;
import jetbrains.buildServer.serverSide.impl.BuildServerLifecycleProcessor;
import jetbrains.buildServer.serverSide.impl.ProjectFactory;
import jetbrains.buildServer.serverSide.impl.projects.ConfigurationPersister;
import jetbrains.buildServer.serverSide.impl.projects.ProjectConfigFilesCache;
import jetbrains.buildServer.serverSide.impl.projects.ProjectImpl;
import jetbrains.buildServer.serverSide.impl.xml.ConfigurationInitializer;
import jetbrains.buildServer.util.FileUtil;
import jetbrains.buildServer.util.NamedDaemonThreadFactory;
import jetbrains.buildServer.web.impl.WebResourcesUnpacker;
import jetbrains.buildServer.web.plugins.PluginManagerConfigurator;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.WebApplicationContext;

/*
 * Exception performing whole class analysis ignored.
 */
public class BuildServerConfigurator
implements ApplicationContextAware {
    @NonNls
    private static final String BUILD_SERVER_RESOURCES = "buildServerResources/";
    private final BuildServerImpl myServer;
    private final ServerPaths myServerPaths;
    private final ConfigurationInitializer myConfigurationInitializer;
    private final ProjectFactory myProjectFactory;
    private final ProjectConfigFilesCache myProjectConfigFilesCache;
    private final ConfigurationPersister myConfigurationPersister;
    private final ConfigActionFactory myConfigActionFactory;
    private ApplicationContext myApplicationContext;
    private final WebResourcesUnpacker myResourcesUnpacker;
    private final PluginManagerConfigurator myPluginConfigurator;
    private final ServerSettings myServerSettings;
    private final TestServerMode myTestServerMode;

    public BuildServerConfigurator(@NotNull BuildServerImpl buildServerImpl, @NotNull ServerPaths serverPaths, @NotNull ConfigurationInitializer configurationInitializer, @NotNull WebResourcesUnpacker webResourcesUnpacker, @NotNull PluginManagerConfigurator pluginManagerConfigurator, @NotNull ServerSettings serverSettings, @NotNull BuildServerLifecycleProcessor buildServerLifecycleProcessor, @NotNull ProjectFactory projectFactory, @NotNull TestServerMode testServerMode, @NotNull ProjectConfigFilesCache projectConfigFilesCache, @NotNull ConfigurationPersister configurationPersister, @NotNull ConfigActionFactory configActionFactory) {
        if (buildServerImpl == null) {
            BuildServerConfigurator.$$$reportNull$$$0(0);
        }
        if (serverPaths == null) {
            BuildServerConfigurator.$$$reportNull$$$0(1);
        }
        if (configurationInitializer == null) {
            BuildServerConfigurator.$$$reportNull$$$0(2);
        }
        if (webResourcesUnpacker == null) {
            BuildServerConfigurator.$$$reportNull$$$0(3);
        }
        if (pluginManagerConfigurator == null) {
            BuildServerConfigurator.$$$reportNull$$$0(4);
        }
        if (serverSettings == null) {
            BuildServerConfigurator.$$$reportNull$$$0(5);
        }
        if (buildServerLifecycleProcessor == null) {
            BuildServerConfigurator.$$$reportNull$$$0(6);
        }
        if (projectFactory == null) {
            BuildServerConfigurator.$$$reportNull$$$0(7);
        }
        if (testServerMode == null) {
            BuildServerConfigurator.$$$reportNull$$$0(8);
        }
        if (projectConfigFilesCache == null) {
            BuildServerConfigurator.$$$reportNull$$$0(9);
        }
        if (configurationPersister == null) {
            BuildServerConfigurator.$$$reportNull$$$0(10);
        }
        if (configActionFactory == null) {
            BuildServerConfigurator.$$$reportNull$$$0(11);
        }
        this.myServer = buildServerImpl;
        this.myPluginConfigurator = pluginManagerConfigurator;
        this.myServerPaths = serverPaths;
        this.myConfigurationInitializer = configurationInitializer;
        this.myResourcesUnpacker = webResourcesUnpacker;
        this.myServerSettings = serverSettings;
        this.myProjectFactory = projectFactory;
        this.myTestServerMode = testServerMode;
        this.myProjectConfigFilesCache = projectConfigFilesCache;
        this.myConfigurationPersister = configurationPersister;
        this.myConfigActionFactory = configActionFactory;
        buildServerLifecycleProcessor.registerStartupAction(this::loadConfiguration);
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.myApplicationContext = applicationContext;
    }

    private void loadConfiguration() {
        ApplicationContext applicationContext = this.myApplicationContext;
        if (applicationContext instanceof WebApplicationContext) {
            WebApplicationContext webApplicationContext = (WebApplicationContext)applicationContext;
            try {
                this.createRootProjectIfNeeded();
                this.myProjectConfigFilesCache.startAsyncInitialization();
                this.copyPreInstalledFiles();
                this.createLibJdbcDirectory();
                RSACipher.generateKeys();
                this.myPluginConfigurator.initializePlugins();
                Thread thread = this.schedulePluginResourcesUnpacking(webApplicationContext);
                this.myConfigurationInitializer.initServer();
                thread.join();
                this.logServerStartup();
                this.myTestServerMode.init();
            }
            catch (Throwable throwable) {
                String string = "Failed to load TeamCity configuration. " + throwable.getMessage();
                Loggers.SERVER.error(string, throwable);
                throw new RuntimeException(string, throwable);
            }
        }
    }

    @NotNull
    private Thread schedulePluginResourcesUnpacking(WebApplicationContext webApplicationContext) {
        Thread thread;
        Thread thread2 = new NamedDaemonThreadFactory("Unpacking plugin resources").newThread((Runnable)new /* Unavailable Anonymous Inner Class!! */);
        try {
            thread2.start();
            thread = thread2;
            if (thread == null) {
                BuildServerConfigurator.$$$reportNull$$$0(12);
            }
        }
        catch (RuntimeException runtimeException) {
            throw BuildServerConfigurator.a(runtimeException);
        }
        return thread;
    }

    private void unpackPluginResources(ServletContext servletContext) {
        this.myResourcesUnpacker.start((SBuildServer)this.myServer);
        File file = new File(servletContext.getRealPath("/plugins/"));
        File file2 = new File(servletContext.getRealPath("/"), "WEB-INF/lib");
        this.myResourcesUnpacker.unpackPlugins(file, file2, "buildServerResources/");
    }

    private void createRootProjectIfNeeded() {
        ProjectManagerEx projectManagerEx = this.myServer.getProjectManager();
        File file = projectManagerEx.ensureProjectsDirectoryExists();
        File file2 = new File(file, BuildProject.ROOT_PROJECT_ID);
        File file3 = new File(file2, "project-config.xml");
        if (!file3.isFile()) {
            Loggers.SERVER.debug("No Root project configuration file");
            ProjectImpl projectImpl = this.myProjectFactory.createRootProject(projectManagerEx.getProjectDataModel());
            projectImpl.setCleanupOption("preventDependenciesArtifactsFromCleanup", Boolean.valueOf(false));
            projectImpl.addFeature("ReportTab", (Map)new /* Unavailable Anonymous Inner Class!! */);
            try {
                this.myConfigurationPersister.saveEntity((SPersistentEntity)projectImpl, this.myConfigActionFactory.createAction("Root project created"));
                Loggers.SERVER.info("Root project configuration files created");
            }
            catch (IOException iOException) {
                Loggers.SERVER.error("Could not create root project", (Throwable)iOException);
            }
        }
    }

    private void logServerStartup() {
        String string;
        Object[] objectArray;
        String string2;
        String string3 = "TeamCity initialized, server UUID: " + this.myServerSettings.getServerUUID() + ", URL: " + this.myServerSettings.getRootUrl();
        try {
            string2 = "TeamCity is running in {0}";
            Object[] objectArray2 = new Object[1];
            Object[] objectArray3 = objectArray2;
            objectArray = objectArray2;
            int n = 0;
            string = this.myServer.getLicensingPolicy().isEnterpriseMode() ? "enterprise mode" : "professional mode";
        }
        catch (RuntimeException runtimeException) {
            throw BuildServerConfigurator.a(runtimeException);
        }
        objectArray3[n] = string;
        String string4 = MessageFormat.format(string2, objectArray);
        try {
            Loggers.SERVER.info(string3);
            Loggers.SERVER.info(string4);
            if (!LogInitializer.isUnitTest()) {
                System.out.println("=======================================================================\n" + string3 + "\n" + string4);
            }
        }
        catch (RuntimeException runtimeException) {
            throw BuildServerConfigurator.a(runtimeException);
        }
    }

    private void copyPreInstalledFiles() {
        if (this.myServer.getServerResponsibility().canWriteToConfigDirectory()) {
            String[] stringArray;
            for (String string : stringArray = new String[]{"main-config.dtd", "auth-config.dtd", "database.mysql.properties.dist", "database.oracle.properties.dist", "database.postgresql.properties.dist", "database.mssql.properties.dist", "database.hsqldb.properties.dist"}) {
                this.writeDefaultContent(string, true);
            }
            this.writeDefaultContent("backup-config.xml", false);
        }
    }

    private void createLibJdbcDirectory() {
        File file = new File(this.myServerPaths.getLibDir(), "jdbc");
        if (!file.exists()) {
            try {
                file.mkdirs();
                file.mkdir();
            }
            catch (Exception exception) {
                Loggers.SERVER.warn("Failed to create directory for jdbc drivers \"" + file.getAbsolutePath() + "\": " + exception.getMessage());
            }
        }
    }

    private void writeDefaultContent(String string, boolean bl) {
        block4: {
            File file = new File(this.myServerPaths.getConfigDir(), string);
            try {
                try {
                    if (file.exists() && !bl) break block4;
                }
                catch (RuntimeException runtimeException) {
                    throw BuildServerConfigurator.a(runtimeException);
                }
                this.writeDefaultContent(file, "/" + string);
            }
            catch (RuntimeException runtimeException) {
                throw BuildServerConfigurator.a(runtimeException);
            }
        }
    }

    private void writeDefaultContent(File file, String string) {
        file.mkdirs();
        file.delete();
        FileUtil.copyResource(this.getClass(), (String)string, (File)file);
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

    private static RuntimeException a(RuntimeException runtimeException) {
        return runtimeException;
    }
}
