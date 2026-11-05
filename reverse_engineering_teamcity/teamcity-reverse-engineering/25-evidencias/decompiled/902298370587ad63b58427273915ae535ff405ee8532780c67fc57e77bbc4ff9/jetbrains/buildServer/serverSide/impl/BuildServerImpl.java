/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jetbrains.buildServer.Build
 *  jetbrains.buildServer.ExtensionsProvider$ExtensionAction
 *  jetbrains.buildServer.ServiceLocator
 *  jetbrains.buildServer.StampedExtensionsSupplier
 *  jetbrains.buildServer.StampedExtensionsSupplier$StoredData
 *  jetbrains.buildServer.TeamCityCloud
 *  jetbrains.buildServer.TeamCityExtension
 *  jetbrains.buildServer.Used
 *  jetbrains.buildServer.a.j
 *  jetbrains.buildServer.a.o
 *  jetbrains.buildServer.http.HttpUserAgent
 *  jetbrains.buildServer.metrics.ServerMetrics
 *  jetbrains.buildServer.responsibility.ResponsibilityEntry
 *  jetbrains.buildServer.responsibility.ResponsibilityFacadeEx
 *  jetbrains.buildServer.responsibility.impl.Util
 *  jetbrains.buildServer.serverSide.BuildAgentManagerEx
 *  jetbrains.buildServer.serverSide.BuildDataFilter
 *  jetbrains.buildServer.serverSide.BuildHistoryEx
 *  jetbrains.buildServer.serverSide.BuildQueryOptions
 *  jetbrains.buildServer.serverSide.BuildQueueEx
 *  jetbrains.buildServer.serverSide.BuildServerEx
 *  jetbrains.buildServer.serverSide.BuildServerListener
 *  jetbrains.buildServer.serverSide.CustomDataStorageManager
 *  jetbrains.buildServer.serverSide.DataFetcher
 *  jetbrains.buildServer.serverSide.LicenseKey
 *  jetbrains.buildServer.serverSide.LicenseKeysManager
 *  jetbrains.buildServer.serverSide.LicensingPolicyEx
 *  jetbrains.buildServer.serverSide.MultiNodesEvents
 *  jetbrains.buildServer.serverSide.ProjectManagerEx
 *  jetbrains.buildServer.serverSide.RunTypeRegistry
 *  jetbrains.buildServer.serverSide.RunningBuildEx
 *  jetbrains.buildServer.serverSide.SBuild
 *  jetbrains.buildServer.serverSide.SBuildAgent
 *  jetbrains.buildServer.serverSide.SBuildType
 *  jetbrains.buildServer.serverSide.SRunningBuild
 *  jetbrains.buildServer.serverSide.SecurityContextEx
 *  jetbrains.buildServer.serverSide.ServerPaths
 *  jetbrains.buildServer.serverSide.ServerResponsibility
 *  jetbrains.buildServer.serverSide.SettingsPersister
 *  jetbrains.buildServer.serverSide.SwitchableExternalActivityRegistry
 *  jetbrains.buildServer.serverSide.SwitchableExternalActivityRegistry$ExternalActivity
 *  jetbrains.buildServer.serverSide.TeamCityProperties
 *  jetbrains.buildServer.serverSide.auth.LoginConfiguration
 *  jetbrains.buildServer.serverSide.buildDistribution.SimpleWaitReason
 *  jetbrains.buildServer.serverSide.buildDistribution.StartBuildPrecondition
 *  jetbrains.buildServer.serverSide.crypt.EncryptionManager
 *  jetbrains.buildServer.serverSide.db.SQLRunnerEx
 *  jetbrains.buildServer.serverSide.executors.ExecutorServices
 *  jetbrains.buildServer.serverSide.impl.BuildServerLifecycleProcessor
 *  jetbrains.buildServer.serverSide.impl.BuildServerServiceLocator
 *  jetbrains.buildServer.serverSide.impl.BuildsManagerImpl
 *  jetbrains.buildServer.serverSide.impl.CompositeRunningBuild
 *  jetbrains.buildServer.serverSide.impl.FileWatcherFactory
 *  jetbrains.buildServer.serverSide.impl.FlushQueueAction
 *  jetbrains.buildServer.serverSide.impl.LicensingInfo
 *  jetbrains.buildServer.serverSide.impl.RunningBuildsManagerImpl
 *  jetbrains.buildServer.serverSide.impl.ServerRootUrlHolder
 *  jetbrains.buildServer.serverSide.impl.auth.SecuredLicenseKeysManager
 *  jetbrains.buildServer.serverSide.impl.executors.ExecutorServiceManager
 *  jetbrains.buildServer.serverSide.impl.personal.PersonalBuildManagerEx
 *  jetbrains.buildServer.serverSide.impl.versionedSettings.ProjectSettingsGeneratorRegistry
 *  jetbrains.buildServer.serverSide.reEncryption.EncryptionEventDispatcher
 *  jetbrains.buildServer.serverSide.versioning.VersionManager
 *  jetbrains.buildServer.spring.candidates.ExcludedFromAutowiring
 *  jetbrains.buildServer.status.StatusProvider
 *  jetbrains.buildServer.users.SUser
 *  jetbrains.buildServer.users.User
 *  jetbrains.buildServer.users.UserModel
 *  jetbrains.buildServer.users.UserModelEx
 *  jetbrains.buildServer.util.EventDispatcher
 *  jetbrains.buildServer.util.FileUtil
 *  jetbrains.buildServer.util.IdeaThreadPoolsShutdown
 *  jetbrains.buildServer.util.ItemProcessor
 *  jetbrains.buildServer.util.TimeService
 *  jetbrains.buildServer.vcs.VcsManagerEx
 *  jetbrains.buildServer.vcs.VcsModificationHistoryEx
 *  jetbrains.buildServer.version.ServerVersionHolder
 *  jetbrains.buildServer.version.ServerVersionInfo
 *  org.jetbrains.annotations.NonNls
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package jetbrains.buildServer.serverSide.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.EventListener;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import jetbrains.buildServer.Build;
import jetbrains.buildServer.ExtensionsProvider;
import jetbrains.buildServer.ServiceLocator;
import jetbrains.buildServer.StampedExtensionsSupplier;
import jetbrains.buildServer.TeamCityCloud;
import jetbrains.buildServer.TeamCityExtension;
import jetbrains.buildServer.Used;
import jetbrains.buildServer.a.j;
import jetbrains.buildServer.a.o;
import jetbrains.buildServer.http.HttpUserAgent;
import jetbrains.buildServer.metrics.ServerMetrics;
import jetbrains.buildServer.responsibility.ResponsibilityEntry;
import jetbrains.buildServer.responsibility.ResponsibilityFacadeEx;
import jetbrains.buildServer.responsibility.impl.Util;
import jetbrains.buildServer.serverSide.BuildAgentManagerEx;
import jetbrains.buildServer.serverSide.BuildDataFilter;
import jetbrains.buildServer.serverSide.BuildHistoryEx;
import jetbrains.buildServer.serverSide.BuildQueryOptions;
import jetbrains.buildServer.serverSide.BuildQueueEx;
import jetbrains.buildServer.serverSide.BuildServerEx;
import jetbrains.buildServer.serverSide.BuildServerListener;
import jetbrains.buildServer.serverSide.CustomDataStorageManager;
import jetbrains.buildServer.serverSide.DataFetcher;
import jetbrains.buildServer.serverSide.LicenseKey;
import jetbrains.buildServer.serverSide.LicenseKeysManager;
import jetbrains.buildServer.serverSide.LicensingPolicyEx;
import jetbrains.buildServer.serverSide.MultiNodesEvents;
import jetbrains.buildServer.serverSide.ProjectManagerEx;
import jetbrains.buildServer.serverSide.RunTypeRegistry;
import jetbrains.buildServer.serverSide.RunningBuildEx;
import jetbrains.buildServer.serverSide.SBuild;
import jetbrains.buildServer.serverSide.SBuildAgent;
import jetbrains.buildServer.serverSide.SBuildType;
import jetbrains.buildServer.serverSide.SRunningBuild;
import jetbrains.buildServer.serverSide.SecurityContextEx;
import jetbrains.buildServer.serverSide.ServerPaths;
import jetbrains.buildServer.serverSide.ServerResponsibility;
import jetbrains.buildServer.serverSide.SettingsPersister;
import jetbrains.buildServer.serverSide.SwitchableExternalActivityRegistry;
import jetbrains.buildServer.serverSide.TeamCityProperties;
import jetbrains.buildServer.serverSide.auth.LoginConfiguration;
import jetbrains.buildServer.serverSide.buildDistribution.SimpleWaitReason;
import jetbrains.buildServer.serverSide.buildDistribution.StartBuildPrecondition;
import jetbrains.buildServer.serverSide.crypt.EncryptionManager;
import jetbrains.buildServer.serverSide.db.SQLRunnerEx;
import jetbrains.buildServer.serverSide.executors.ExecutorServices;
import jetbrains.buildServer.serverSide.impl.BuildServerLifecycleProcessor;
import jetbrains.buildServer.serverSide.impl.BuildServerServiceLocator;
import jetbrains.buildServer.serverSide.impl.BuildsManagerImpl;
import jetbrains.buildServer.serverSide.impl.CompositeRunningBuild;
import jetbrains.buildServer.serverSide.impl.FileWatcherFactory;
import jetbrains.buildServer.serverSide.impl.FlushQueueAction;
import jetbrains.buildServer.serverSide.impl.LicensingInfo;
import jetbrains.buildServer.serverSide.impl.RunningBuildsManagerImpl;
import jetbrains.buildServer.serverSide.impl.ServerRootUrlHolder;
import jetbrains.buildServer.serverSide.impl.auth.SecuredLicenseKeysManager;
import jetbrains.buildServer.serverSide.impl.executors.ExecutorServiceManager;
import jetbrains.buildServer.serverSide.impl.personal.PersonalBuildManagerEx;
import jetbrains.buildServer.serverSide.impl.versionedSettings.ProjectSettingsGeneratorRegistry;
import jetbrains.buildServer.serverSide.reEncryption.EncryptionEventDispatcher;
import jetbrains.buildServer.serverSide.versioning.VersionManager;
import jetbrains.buildServer.spring.candidates.ExcludedFromAutowiring;
import jetbrains.buildServer.status.StatusProvider;
import jetbrains.buildServer.users.SUser;
import jetbrains.buildServer.users.User;
import jetbrains.buildServer.users.UserModel;
import jetbrains.buildServer.users.UserModelEx;
import jetbrains.buildServer.util.EventDispatcher;
import jetbrains.buildServer.util.FileUtil;
import jetbrains.buildServer.util.IdeaThreadPoolsShutdown;
import jetbrains.buildServer.util.ItemProcessor;
import jetbrains.buildServer.util.TimeService;
import jetbrains.buildServer.vcs.VcsManagerEx;
import jetbrains.buildServer.vcs.VcsModificationHistoryEx;
import jetbrains.buildServer.version.ServerVersionHolder;
import jetbrains.buildServer.version.ServerVersionInfo;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Exception performing whole class analysis ignored.
 */
@ExcludedFromAutowiring(types={BuildServerServiceLocator.class, RunningBuildsManagerImpl.class, BuildsManagerImpl.class, ServerRootUrlHolder.class, BuildServerLifecycleProcessor.class})
public class BuildServerImpl
implements BuildServerEx {
    public static final int TRIAL_EXPIRE_DAYS = TeamCityProperties.getInteger((String)"teamcity.licenseHealthReport.evalExpireDays", (int)10);
    @Deprecated
    @NonNls
    public static final String UNEXPECTED_FINISH = "unexpectedFinish";
    private ExecutorServiceManager myExecutorServiceManager;
    private BuildQueueEx myQueue;
    private SQLRunnerEx myDb;
    private BuildHistoryEx myBuildHistory;
    private VcsModificationHistoryEx myVcsModificationHistory;
    @NotNull
    private final ServerRootUrlHolder myServerRootUrlHolder;
    private final EventDispatcher<BuildServerListener> myDispatcher;
    @NotNull
    private final ServerPaths myServerPaths;
    @NotNull
    private final BuildServerServiceLocator myServiceLocator;
    @NotNull
    private RunningBuildsManagerImpl myRunningBuilds;
    private UserModelEx myUserModel;
    private VcsManagerEx myVcsManager;
    private RunTypeRegistry myRunTypeRegistry;
    private StatusProvider myStatusProvider;
    private LoginConfiguration myLoginConfiguration;
    private o myServerLicenseManager;
    private PersonalBuildManagerEx myPersonalBuildManager;
    private ProjectManagerEx myProjectManager;
    private String myRootPath;
    private BuildAgentManagerEx myAgentManager;
    @NotNull
    private final SecurityContextEx mySecurityContext;
    @NotNull
    private SecuredLicenseKeysManager myLicenseKeysManager;
    @NotNull
    private FileWatcherFactory myFileWatcherFactory;
    @NotNull
    private ResponsibilityFacadeEx myResponsibilityFacade;
    @NotNull
    private TimeService myTimeService;
    @NotNull
    private BuildsManagerImpl myBuildsManager;
    @NotNull
    private FlushQueueAction myFlushQueueAction;
    @NotNull
    private final BuildServerLifecycleProcessor myStateProcessor;
    private final AtomicReference<LicensingInfo> myLicensingInfo;
    private final ServerResponsibility myServerResponsibility;
    @NotNull
    private ProjectSettingsGeneratorRegistry myProjectSettingsGeneratorRegistry;
    @NotNull
    private ServerMetrics myServerMetrics;
    @NotNull
    private CustomDataStorageManager myCustomDataStorageManager;

    public BuildServerImpl(@NotNull BuildServerLifecycleProcessor buildServerLifecycleProcessor, @NotNull ServerPaths serverPaths, @NotNull SecurityContextEx securityContextEx, @NotNull BuildServerServiceLocator buildServerServiceLocator, @NotNull ServerRootUrlHolder serverRootUrlHolder, @NotNull EventDispatcher<BuildServerListener> eventDispatcher, @NotNull ServerResponsibility serverResponsibility) throws IOException {
        if (buildServerLifecycleProcessor == null) {
            BuildServerImpl.$$$reportNull$$$0(0);
        }
        if (serverPaths == null) {
            BuildServerImpl.$$$reportNull$$$0(1);
        }
        if (securityContextEx == null) {
            BuildServerImpl.$$$reportNull$$$0(2);
        }
        if (buildServerServiceLocator == null) {
            BuildServerImpl.$$$reportNull$$$0(3);
        }
        if (serverRootUrlHolder == null) {
            BuildServerImpl.$$$reportNull$$$0(4);
        }
        if (eventDispatcher == null) {
            BuildServerImpl.$$$reportNull$$$0(5);
        }
        if (serverResponsibility == null) {
            BuildServerImpl.$$$reportNull$$$0(6);
        }
        this.myLicensingInfo = new AtomicReference();
        this.myStateProcessor = buildServerLifecycleProcessor;
        this.myServerRootUrlHolder = serverRootUrlHolder;
        this.myDispatcher = eventDispatcher;
        this.myServerPaths = serverPaths;
        this.mySecurityContext = securityContextEx;
        this.myServiceLocator = buildServerServiceLocator;
        this.myServerResponsibility = serverResponsibility;
        FileUtil.createDir((File)new File(this.myServerPaths.getSystemDir()));
        FileUtil.createDir((File)new File(this.myServerPaths.getConfigDir()));
        HttpUserAgent.setServerFlag();
    }

    @Used(value="spring")
    public void setExecutorServiceManager(@NotNull ExecutorServiceManager executorServiceManager) {
        try {
            if (executorServiceManager == null) {
                BuildServerImpl.$$$reportNull$$$0(7);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        this.myExecutorServiceManager = executorServiceManager;
    }

    public void setFlushQueueAction(@NotNull FlushQueueAction flushQueueAction) {
        try {
            if (flushQueueAction == null) {
                BuildServerImpl.$$$reportNull$$$0(8);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        this.myFlushQueueAction = flushQueueAction;
    }

    @NotNull
    public BuildAgentManagerEx getBuildAgentManager() {
        BuildAgentManagerEx buildAgentManagerEx;
        try {
            buildAgentManagerEx = this.myAgentManager;
            if (buildAgentManagerEx == null) {
                BuildServerImpl.$$$reportNull$$$0(9);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        return buildAgentManagerEx;
    }

    public List<SBuild> getEntriesSince(@Nullable SBuild sBuild, SBuildType sBuildType) {
        return this.myRunningBuilds.getEntriesSince(sBuild, sBuildType);
    }

    @Used(value="spring")
    public void setAgentManager(BuildAgentManagerEx buildAgentManagerEx) {
        this.myAgentManager = buildAgentManagerEx;
    }

    @Used(value="spring")
    public void setDb(@NotNull SQLRunnerEx sQLRunnerEx) {
        try {
            if (sQLRunnerEx == null) {
                BuildServerImpl.$$$reportNull$$$0(10);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        this.myDb = sQLRunnerEx;
    }

    @Used(value="spring")
    public void setPersonalBuildManagerEx(@NotNull PersonalBuildManagerEx personalBuildManagerEx) {
        try {
            if (personalBuildManagerEx == null) {
                BuildServerImpl.$$$reportNull$$$0(11);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        this.myPersonalBuildManager = personalBuildManagerEx;
    }

    @Used(value="spring")
    public void setFileWatcherFactory(@NotNull FileWatcherFactory fileWatcherFactory) {
        try {
            if (fileWatcherFactory == null) {
                BuildServerImpl.$$$reportNull$$$0(12);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        this.myFileWatcherFactory = fileWatcherFactory;
    }

    @Used(value="spring")
    public void setHistoryEx(BuildHistoryEx buildHistoryEx) {
        this.myBuildHistory = buildHistoryEx;
    }

    @Used(value="spring")
    public void setCustomDataStorageManager(@NotNull CustomDataStorageManager customDataStorageManager) {
        try {
            if (customDataStorageManager == null) {
                BuildServerImpl.$$$reportNull$$$0(13);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        this.myCustomDataStorageManager = customDataStorageManager;
    }

    public void initServer() {
        this.myServerLicenseManager = new o(this.myDb, this.myServerPaths, this.mySecurityContext, this.myServerResponsibility, this.myProjectManager, this.myFileWatcherFactory, this.myDispatcher, (ServiceLocator)this, this.myCustomDataStorageManager, this.getSingletonService(MultiNodesEvents.class), this.getSingletonService(SettingsPersister.class), (ExecutorServices)this.myExecutorServiceManager, this.getSingletonService(EncryptionManager.class), this.myServerMetrics, (EventDispatcher)this.getSingletonService(EncryptionEventDispatcher.class));
        this.myLicenseKeysManager = new SecuredLicenseKeysManager((LicenseKeysManager)this.myServerLicenseManager, this.mySecurityContext);
        this.myUserModel.setLicenseKeysManager((LicenseKeysManager)this.myServerLicenseManager);
        this.myStateProcessor.registerShutdownAction(this::shutdown);
    }

    @Used(value="spring")
    public void setUserModelEx(UserModelEx userModelEx) {
        this.myUserModel = userModelEx;
    }

    @Used(value="spring")
    public void setBuildQueue(BuildQueueEx buildQueueEx) {
        this.myQueue = buildQueueEx;
    }

    @Used(value="spring")
    public void setRunTypeRegistry(@NotNull RunTypeRegistry runTypeRegistry) {
        try {
            if (runTypeRegistry == null) {
                BuildServerImpl.$$$reportNull$$$0(14);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        this.myRunTypeRegistry = runTypeRegistry;
    }

    @Used(value="spring")
    public void setVcsManagerEx(@NotNull VcsManagerEx vcsManagerEx) {
        try {
            if (vcsManagerEx == null) {
                BuildServerImpl.$$$reportNull$$$0(15);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        this.myVcsManager = vcsManagerEx;
    }

    @Used(value="spring")
    public void setVcsModificationHistory(@NotNull VcsModificationHistoryEx vcsModificationHistoryEx) {
        try {
            if (vcsModificationHistoryEx == null) {
                BuildServerImpl.$$$reportNull$$$0(16);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        this.myVcsModificationHistory = vcsModificationHistoryEx;
    }

    @Used(value="spring")
    public void setProjectManagerImpl(@NotNull ProjectManagerEx projectManagerEx) {
        try {
            if (projectManagerEx == null) {
                BuildServerImpl.$$$reportNull$$$0(17);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        this.myProjectManager = projectManagerEx;
    }

    @Used(value="spring")
    public void setLoginConfiguration(@NotNull LoginConfiguration loginConfiguration) {
        try {
            if (loginConfiguration == null) {
                BuildServerImpl.$$$reportNull$$$0(18);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        this.myLoginConfiguration = loginConfiguration;
    }

    @Used(value="spring")
    public void setResponsibilityFacade(@NotNull ResponsibilityFacadeEx responsibilityFacadeEx) {
        try {
            if (responsibilityFacadeEx == null) {
                BuildServerImpl.$$$reportNull$$$0(19);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        this.myResponsibilityFacade = responsibilityFacadeEx;
    }

    @Used(value="spring")
    public void setRunningBuildsManager(@NotNull RunningBuildsManagerImpl runningBuildsManagerImpl) {
        try {
            if (runningBuildsManagerImpl == null) {
                BuildServerImpl.$$$reportNull$$$0(20);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        this.myRunningBuilds = runningBuildsManagerImpl;
    }

    public void setBuildsManager(@NotNull BuildsManagerImpl buildsManagerImpl) {
        try {
            if (buildsManagerImpl == null) {
                BuildServerImpl.$$$reportNull$$$0(21);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        this.myBuildsManager = buildsManagerImpl;
    }

    public void setSwitchableExternalActivityRegistry(@NotNull SwitchableExternalActivityRegistry switchableExternalActivityRegistry) {
        try {
            if (switchableExternalActivityRegistry == null) {
                BuildServerImpl.$$$reportNull$$$0(22);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        this.registerExtension(StartBuildPrecondition.class, "teamcity", (queuedBuildInfo, map, buildDistributorInput, bl) -> {
            SimpleWaitReason simpleWaitReason;
            try {
                simpleWaitReason = atomicBoolean.get() ? new SimpleWaitReason("External activity is disabled") : null;
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw BuildServerImpl.a(illegalArgumentException);
            }
            return simpleWaitReason;
        });
        switchableExternalActivityRegistry.register((SwitchableExternalActivityRegistry.ExternalActivity)new /* Unavailable Anonymous Inner Class!! */);
    }

    public void setProjectSettingsGeneratorRegistry(@NotNull ProjectSettingsGeneratorRegistry projectSettingsGeneratorRegistry) {
        try {
            if (projectSettingsGeneratorRegistry == null) {
                BuildServerImpl.$$$reportNull$$$0(23);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        this.myProjectSettingsGeneratorRegistry = projectSettingsGeneratorRegistry;
    }

    @NotNull
    public SecurityContextEx getSecurityContext() {
        SecurityContextEx securityContextEx;
        try {
            securityContextEx = this.mySecurityContext;
            if (securityContextEx == null) {
                BuildServerImpl.$$$reportNull$$$0(24);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        return securityContextEx;
    }

    @NotNull
    public ServerResponsibility getServerResponsibility() {
        ServerResponsibility serverResponsibility;
        try {
            serverResponsibility = this.myServerResponsibility;
            if (serverResponsibility == null) {
                BuildServerImpl.$$$reportNull$$$0(25);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        return serverResponsibility;
    }

    @NotNull
    public LoginConfiguration getLoginConfiguration() {
        LoginConfiguration loginConfiguration;
        try {
            loginConfiguration = this.myLoginConfiguration;
            if (loginConfiguration == null) {
                BuildServerImpl.$$$reportNull$$$0(26);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        return loginConfiguration;
    }

    public ScheduledExecutorService getExecutor() {
        return this.myExecutorServiceManager.getNormalExecutorService();
    }

    public void removeRunningBuild(@NotNull RunningBuildEx runningBuildEx) {
        try {
            if (runningBuildEx == null) {
                BuildServerImpl.$$$reportNull$$$0(27);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        this.myRunningBuilds.removeRunningBuild(runningBuildEx);
    }

    @NotNull
    public UserModel getUserModel() {
        UserModelEx userModelEx;
        try {
            userModelEx = this.myUserModel;
            if (userModelEx == null) {
                BuildServerImpl.$$$reportNull$$$0(28);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        return userModelEx;
    }

    @NotNull
    public VcsManagerEx getVcsManager() {
        VcsManagerEx vcsManagerEx;
        try {
            vcsManagerEx = this.myVcsManager;
            if (vcsManagerEx == null) {
                BuildServerImpl.$$$reportNull$$$0(29);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        return vcsManagerEx;
    }

    private void shutdown() {
        try {
            if (this.myServerLicenseManager != null) {
                this.myServerLicenseManager.d();
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        IdeaThreadPoolsShutdown.shutdownStaticPools();
    }

    @NotNull
    private LicensingInfo updateLicensingInfo() {
        LicensingInfo licensingInfo;
        LicensingPolicyEx licensingPolicyEx = this.getLicensingPolicy();
        LicensingInfo licensingInfo2 = new LicensingInfo(licensingPolicyEx.isMaxNumberOfBuildTypesExceeded(), licensingPolicyEx.isMaxNumberOfAuthorizedAgentsExceeded(), licensingPolicyEx.isMaxNumberOfPipelinesExceeded());
        try {
            this.myLicensingInfo.set(licensingInfo2);
            licensingInfo = licensingInfo2;
            if (licensingInfo == null) {
                BuildServerImpl.$$$reportNull$$$0(30);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        return licensingInfo;
    }

    @NotNull
    public LicensingInfo getLicensingInfo() {
        block4: {
            LicensingInfo licensingInfo;
            block5: {
                LicensingInfo licensingInfo2 = this.myLicensingInfo.get();
                try {
                    try {
                        if (licensingInfo2 == null) break block4;
                        licensingInfo = licensingInfo2;
                        if (licensingInfo != null) break block5;
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        throw BuildServerImpl.a(illegalArgumentException);
                    }
                    BuildServerImpl.$$$reportNull$$$0(31);
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw BuildServerImpl.a(illegalArgumentException);
                }
            }
            return licensingInfo;
        }
        return this.updateLicensingInfo();
    }

    public boolean flushQueue() {
        block7: {
            LicensingInfo licensingInfo = this.updateLicensingInfo();
            try {
                block6: {
                    try {
                        try {
                            if (licensingInfo.isMaxNumberOfBuildTypesExceeded() || licensingInfo.isMaxNumberOfAuthorizedAgentsExceeded()) break block6;
                        }
                        catch (IllegalArgumentException illegalArgumentException) {
                            throw BuildServerImpl.a(illegalArgumentException);
                        }
                        if (!licensingInfo.isMaxNumberOfPipelinesExceeded()) break block7;
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        throw BuildServerImpl.a(illegalArgumentException);
                    }
                }
                return false;
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw BuildServerImpl.a(illegalArgumentException);
            }
        }
        return this.myFlushQueueAction.flushQueue();
    }

    @NotNull
    public TimeService getTimeService() {
        TimeService timeService;
        try {
            timeService = this.myTimeService;
            if (timeService == null) {
                BuildServerImpl.$$$reportNull$$$0(32);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        return timeService;
    }

    @NotNull
    public List<RunningBuildEx> getRunningBuildsEx() {
        List list;
        try {
            list = this.myRunningBuilds.getRunningBuildsEx();
            if (list == null) {
                BuildServerImpl.$$$reportNull$$$0(33);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        return list;
    }

    @Used(value="Spring")
    public void setTimeService(@NotNull TimeService timeService) {
        try {
            if (timeService == null) {
                BuildServerImpl.$$$reportNull$$$0(34);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        this.myTimeService = timeService;
    }

    public boolean isStarted() {
        return this.myStateProcessor.isStarted();
    }

    public boolean isShuttingDown() {
        return this.myStateProcessor.isShuttingDown();
    }

    public boolean isDatabaseCreatedOnStartup() {
        return VersionManager.isDatabaseCreatedOnStartup();
    }

    @NotNull
    public BuildQueueEx getQueue() {
        BuildQueueEx buildQueueEx;
        try {
            buildQueueEx = this.myQueue;
            if (buildQueueEx == null) {
                BuildServerImpl.$$$reportNull$$$0(35);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        return buildQueueEx;
    }

    @Nullable
    public final RunningBuildEx findRunningBuildById(long l) {
        return this.myRunningBuilds.findRunningBuildById(l);
    }

    @Nullable
    public RunningBuildEx getRunningBuildOnAgent(SBuildAgent sBuildAgent) {
        return this.myRunningBuilds.getRunningBuildOnAgent(sBuildAgent);
    }

    @NotNull
    public List<SRunningBuild> getRunningBuilds(@Nullable User user, @NotNull SBuildType sBuildType) {
        List list;
        try {
            if (sBuildType == null) {
                BuildServerImpl.$$$reportNull$$$0(36);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        try {
            list = this.myRunningBuilds.getRunningBuilds(user, sBuildType);
            if (list == null) {
                BuildServerImpl.$$$reportNull$$$0(37);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        return list;
    }

    @NotNull
    public CompletableFuture<Void> processBuildFinishedOnOtherNode(long l) {
        CompletableFuture completableFuture;
        try {
            completableFuture = this.myRunningBuilds.processBuildFinishedOnOtherNode(l);
            if (completableFuture == null) {
                BuildServerImpl.$$$reportNull$$$0(38);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        return completableFuture;
    }

    @NotNull
    public CompletableFuture<Void> processBuildInterruptedOnOtherNode(long l) {
        CompletableFuture completableFuture;
        try {
            completableFuture = this.myRunningBuilds.processBuildInterruptedOnOtherNode(l);
            if (completableFuture == null) {
                BuildServerImpl.$$$reportNull$$$0(39);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        return completableFuture;
    }

    @NotNull
    public List<SRunningBuild> getRunningBuilds(@Nullable User user, @Nullable BuildDataFilter buildDataFilter) {
        List list;
        try {
            list = this.myRunningBuilds.getRunningBuilds(user, buildDataFilter);
            if (list == null) {
                BuildServerImpl.$$$reportNull$$$0(40);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        return list;
    }

    @NotNull
    public List<SRunningBuild> getRunningBuilds() {
        List list;
        try {
            list = this.myRunningBuilds.getRunningBuilds();
            if (list == null) {
                BuildServerImpl.$$$reportNull$$$0(41);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        return list;
    }

    public int getNumberOfRunningBuilds() {
        return this.myRunningBuilds.getNumberOfRunningBuilds();
    }

    public Map<SBuildType, List<SRunningBuild>> getRunningStatus(@Nullable User user, @Nullable BuildDataFilter buildDataFilter) {
        return this.myRunningBuilds.getRunningStatus(user, buildDataFilter);
    }

    @NotNull
    public BuildHistoryEx getHistory() {
        BuildHistoryEx buildHistoryEx;
        try {
            buildHistoryEx = this.myBuildHistory;
            if (buildHistoryEx == null) {
                BuildServerImpl.$$$reportNull$$$0(42);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        return buildHistoryEx;
    }

    @NotNull
    public VcsModificationHistoryEx getVcsHistory() {
        VcsModificationHistoryEx vcsModificationHistoryEx;
        try {
            vcsModificationHistoryEx = this.myVcsModificationHistory;
            if (vcsModificationHistoryEx == null) {
                BuildServerImpl.$$$reportNull$$$0(43);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        return vcsModificationHistoryEx;
    }

    @NotNull
    public RunTypeRegistry getRunTypeRegistry() {
        RunTypeRegistry runTypeRegistry;
        try {
            runTypeRegistry = this.myRunTypeRegistry;
            if (runTypeRegistry == null) {
                BuildServerImpl.$$$reportNull$$$0(44);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        return runTypeRegistry;
    }

    public void scheduleBuildFinishing(@NotNull RunningBuildEx runningBuildEx) {
        try {
            if (runningBuildEx == null) {
                BuildServerImpl.$$$reportNull$$$0(45);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        this.myRunningBuilds.scheduleBuildFinishing(runningBuildEx);
    }

    public boolean isCompositeBuildReadyToFinish(@NotNull CompositeRunningBuild compositeRunningBuild) {
        try {
            if (compositeRunningBuild == null) {
                BuildServerImpl.$$$reportNull$$$0(46);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        return this.myRunningBuilds.isCompositeBuildReadyToFinish(compositeRunningBuild);
    }

    public boolean hasFinishingBuilds() {
        return this.myRunningBuilds.hasFinishingBuilds();
    }

    public boolean isBuildFinishing(long l) {
        return this.myRunningBuilds.isBuildFinishing(l);
    }

    @Nullable
    public RunningBuildEx getRunningBuildOrWrapper(long l) {
        return this.myRunningBuilds.getRunningBuildOrWrapper(l);
    }

    public void detachFromAgent(@NotNull RunningBuildEx runningBuildEx) {
        try {
            if (runningBuildEx == null) {
                BuildServerImpl.$$$reportNull$$$0(47);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        this.myRunningBuilds.detachFromAgent(runningBuildEx);
    }

    @Nullable
    public Date getServerReadyDate() {
        return this.myRunningBuilds.getServerReadyDate();
    }

    @Deprecated
    public SQLRunnerEx getSQLRunner() {
        return this.myDb;
    }

    @NotNull
    public LicenseKeysManager getLicenseKeysManager() {
        SecuredLicenseKeysManager securedLicenseKeysManager;
        try {
            securedLicenseKeysManager = this.myLicenseKeysManager;
            if (securedLicenseKeysManager == null) {
                BuildServerImpl.$$$reportNull$$$0(48);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        return securedLicenseKeysManager;
    }

    public j getLicenseTokenManager() {
        return this.myServerLicenseManager;
    }

    public void updateLicenseKeys() {
        this.myServerLicenseManager.h();
    }

    public final LicensingPolicyEx getLicensingPolicy() {
        return this.myServerLicenseManager.getLicensingPolicy();
    }

    public UserModelEx getUserModelEx() {
        return this.myUserModel;
    }

    @Deprecated
    public final void addListener(BuildServerListener buildServerListener) {
        this.myDispatcher.addListener((EventListener)buildServerListener);
    }

    @Deprecated
    public final void removeListener(BuildServerListener buildServerListener) {
        this.myDispatcher.removeListener((EventListener)buildServerListener);
    }

    @Deprecated
    public BuildServerListener getMulticaster() {
        return (BuildServerListener)this.myDispatcher.getMulticaster();
    }

    public void setServerRootPath(String string) {
        this.myRootPath = string;
    }

    public byte[] fetchData(long l, long l2, String string, String string2) {
        try {
            if (this.getUserModel().findUserById(l) == null) {
                return new byte[0];
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        SBuild sBuild = this.findBuildInstanceById(l2);
        try {
            if (sBuild == null) {
                return new byte[0];
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        this.mySecurityContext.getAccessChecker().checkCanViewBuild((Build)sBuild);
        DataFetcher dataFetcher = this.getExtension(DataFetcher.class, string);
        try {
            if (dataFetcher != null) {
                return dataFetcher.fetchData(l2, string2);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        return new byte[0];
    }

    @Nullable
    public SBuild getLastStartedBuild(String string) {
        return this.myRunningBuilds.getLastStartedBuild(string);
    }

    public void addRunningBuild(@NotNull RunningBuildEx runningBuildEx) {
        try {
            if (runningBuildEx == null) {
                BuildServerImpl.$$$reportNull$$$0(49);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        this.myRunningBuilds.addRunningBuild(runningBuildEx);
    }

    @NotNull
    public ProjectManagerEx getProjectManager() {
        ProjectManagerEx projectManagerEx;
        try {
            projectManagerEx = this.myProjectManager;
            if (projectManagerEx == null) {
                BuildServerImpl.$$$reportNull$$$0(50);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        return projectManagerEx;
    }

    private static boolean isResponsible(User user, SBuildType sBuildType) {
        block5: {
            ResponsibilityEntry responsibilityEntry = sBuildType.getResponsibilityInfo();
            if (Util.isActive((ResponsibilityEntry)responsibilityEntry)) {
                boolean bl;
                block7: {
                    block6: {
                        User user2 = Util.getUser((ResponsibilityEntry)responsibilityEntry);
                        try {
                            try {
                                if (user2 == null) break block5;
                                if (user2.getId() != user.getId()) break block6;
                            }
                            catch (IllegalArgumentException illegalArgumentException) {
                                throw BuildServerImpl.a(illegalArgumentException);
                            }
                            bl = true;
                            break block7;
                        }
                        catch (IllegalArgumentException illegalArgumentException) {
                            throw BuildServerImpl.a(illegalArgumentException);
                        }
                    }
                    bl = false;
                }
                return bl;
            }
        }
        return false;
    }

    public List<String> getResponsibilityIds(long l) {
        SUser sUser = this.getUserModel().findUserById(l);
        ArrayList<String> arrayList = new ArrayList<String>();
        if (sUser != null) {
            List list = this.myProjectManager.getAllBuildTypes();
            for (SBuildType sBuildType : list) {
                try {
                    if (!BuildServerImpl.isResponsible((User)sUser, sBuildType)) continue;
                    arrayList.add(sBuildType.getBuildTypeId());
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw BuildServerImpl.a(illegalArgumentException);
                }
            }
        }
        return arrayList;
    }

    @NotNull
    public String getRootUrl() {
        String string;
        try {
            string = this.myServerRootUrlHolder.getRootUrl();
            if (string == null) {
                BuildServerImpl.$$$reportNull$$$0(51);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        return string;
    }

    public void setRootUrl(@NotNull String string) {
        try {
            if (string == null) {
                BuildServerImpl.$$$reportNull$$$0(52);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        this.myServerRootUrlHolder.setRootUrl(string);
    }

    public boolean isDefaultRootUrl() {
        return this.myServerRootUrlHolder.isDefaultRootUrl();
    }

    @Used(value="spring")
    public void setStatusProvider(@NotNull StatusProvider statusProvider) {
        try {
            if (statusProvider == null) {
                BuildServerImpl.$$$reportNull$$$0(53);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        this.myStatusProvider = statusProvider;
    }

    @NotNull
    public StatusProvider getStatusProvider() {
        StatusProvider statusProvider;
        try {
            statusProvider = this.myStatusProvider;
            if (statusProvider == null) {
                BuildServerImpl.$$$reportNull$$$0(54);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        return statusProvider;
    }

    public PersonalBuildManagerEx getPersonalBuildManager() {
        return this.myPersonalBuildManager;
    }

    @NotNull
    public ResponsibilityFacadeEx getResposibilityFacade() {
        ResponsibilityFacadeEx responsibilityFacadeEx;
        try {
            responsibilityFacadeEx = this.myResponsibilityFacade;
            if (responsibilityFacadeEx == null) {
                BuildServerImpl.$$$reportNull$$$0(55);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        return responsibilityFacadeEx;
    }

    public String getBuildNumber() {
        return ServerVersionHolder.getVersion().getBuildNumber();
    }

    public Date getBuildDate() {
        return ServerVersionHolder.getVersion().getBuildDate();
    }

    public final byte getServerMajorVersion() {
        int n = ServerVersionHolder.getVersion().getDisplayVersionMajor();
        try {
            if (n > 2000) {
                return (byte)(n - 2000);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        return (byte)n;
    }

    public final byte getServerMinorVersion() {
        return (byte)ServerVersionHolder.getVersion().getDisplayVersionMinor();
    }

    @NotNull
    public ServerVersionInfo getVersion() {
        ServerVersionInfo serverVersionInfo;
        try {
            serverVersionInfo = ServerVersionHolder.getVersion();
            if (serverVersionInfo == null) {
                BuildServerImpl.$$$reportNull$$$0(56);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        return serverVersionInfo;
    }

    public String getFullServerVersion() {
        String string;
        try {
            string = TeamCityCloud.isCloud() ? " (build " + this.getBuildNumber() + ")" : ServerVersionHolder.getVersion().getDisplayVersion() + " (build " + this.getBuildNumber() + ")";
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        return string;
    }

    public int getDaysToLicenseExpiration() {
        return this.myServerLicenseManager.o();
    }

    public boolean isLicenseExpiringSoon() {
        boolean bl;
        block5: {
            block4: {
                int n = this.getDaysToLicenseExpiration();
                try {
                    try {
                        if (n <= 0 || n > TRIAL_EXPIRE_DAYS) break block4;
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        throw BuildServerImpl.a(illegalArgumentException);
                    }
                    bl = true;
                    break block5;
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw BuildServerImpl.a(illegalArgumentException);
                }
            }
            bl = false;
        }
        return bl;
    }

    @Nullable
    public LicenseKey findPerUsageLicense() {
        return this.myServerLicenseManager.getLicenseList().getActiveLicenses().stream().filter(LicenseKey::isPerUsageLicense).findFirst().orElse(null);
    }

    public String getServerRootPath() {
        return this.myRootPath;
    }

    public final <T extends TeamCityExtension> void registerExtension(@NotNull Class<T> clazz, @NonNls @NotNull String string, @NotNull T t) {
        try {
            if (clazz == null) {
                BuildServerImpl.$$$reportNull$$$0(57);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        try {
            if (string == null) {
                BuildServerImpl.$$$reportNull$$$0(58);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        try {
            if (t == null) {
                BuildServerImpl.$$$reportNull$$$0(59);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        this.myServiceLocator.registerExtension(clazz, string, t);
    }

    public final <T extends TeamCityExtension> void unregisterExtension(@NotNull Class<T> clazz, @NonNls @NotNull String string) {
        try {
            if (clazz == null) {
                BuildServerImpl.$$$reportNull$$$0(60);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        try {
            if (string == null) {
                BuildServerImpl.$$$reportNull$$$0(61);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        this.myServiceLocator.unregisterExtension(clazz, string);
    }

    @NotNull
    public final <T extends TeamCityExtension> Collection<T> getExtensions(@NotNull Class<T> clazz) {
        Collection collection;
        try {
            if (clazz == null) {
                BuildServerImpl.$$$reportNull$$$0(62);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        try {
            collection = this.myServiceLocator.getExtensions(clazz);
            if (collection == null) {
                BuildServerImpl.$$$reportNull$$$0(63);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        return collection;
    }

    public final <T extends TeamCityExtension> void foreachExtension(@NotNull Class<T> clazz, @NotNull ExtensionsProvider.ExtensionAction<T> extensionAction) {
        try {
            if (clazz == null) {
                BuildServerImpl.$$$reportNull$$$0(64);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        try {
            if (extensionAction == null) {
                BuildServerImpl.$$$reportNull$$$0(65);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        this.myServiceLocator.foreachExtension(clazz, extensionAction);
    }

    @NotNull
    public final <T extends TeamCityExtension> Collection<String> getExtensionSources(@NotNull Class<T> clazz) {
        Collection collection;
        try {
            if (clazz == null) {
                BuildServerImpl.$$$reportNull$$$0(66);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        try {
            collection = this.myServiceLocator.getExtensionSources(clazz);
            if (collection == null) {
                BuildServerImpl.$$$reportNull$$$0(67);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        return collection;
    }

    @Nullable
    public final <T extends TeamCityExtension> T getExtension(@NotNull Class<T> clazz, @NotNull String string) {
        try {
            if (clazz == null) {
                BuildServerImpl.$$$reportNull$$$0(68);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        try {
            if (string == null) {
                BuildServerImpl.$$$reportNull$$$0(69);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        return (T)this.myServiceLocator.getExtension(clazz, string);
    }

    @NotNull
    public <T extends TeamCityExtension, CONTEXT, TARGET> StampedExtensionsSupplier<CONTEXT, TARGET> getStampedExtensionsSupplier(@NotNull Class<T> clazz, @NotNull Function<StampedExtensionsSupplier.StoredData<CONTEXT, Collection<T>>, TARGET> function) {
        StampedExtensionsSupplier stampedExtensionsSupplier;
        try {
            if (clazz == null) {
                BuildServerImpl.$$$reportNull$$$0(70);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        try {
            if (function == null) {
                BuildServerImpl.$$$reportNull$$$0(71);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        try {
            stampedExtensionsSupplier = this.myServiceLocator.getStampedExtensionsSupplier(clazz, function);
            if (stampedExtensionsSupplier == null) {
                BuildServerImpl.$$$reportNull$$$0(72);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        return stampedExtensionsSupplier;
    }

    @NotNull
    public final <T> T getSingletonService(@NotNull Class<T> clazz) {
        Object object;
        try {
            if (clazz == null) {
                BuildServerImpl.$$$reportNull$$$0(73);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        try {
            object = this.myServiceLocator.getSingletonService(clazz);
            if (object == null) {
                BuildServerImpl.$$$reportNull$$$0(74);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        return (T)object;
    }

    @Nullable
    public final <T> T findSingletonService(@NotNull Class<T> clazz) {
        try {
            if (clazz == null) {
                BuildServerImpl.$$$reportNull$$$0(75);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        return (T)this.myServiceLocator.findSingletonService(clazz);
    }

    @NotNull
    public final <T> Collection<T> getServices(@NotNull Class<T> clazz) {
        Collection collection;
        try {
            if (clazz == null) {
                BuildServerImpl.$$$reportNull$$$0(76);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        try {
            collection = this.myServiceLocator.getServices(clazz);
            if (collection == null) {
                BuildServerImpl.$$$reportNull$$$0(77);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        return collection;
    }

    @Nullable
    public SBuild findBuildInstanceById(long l) {
        return this.myBuildsManager.findBuildInstanceById(l);
    }

    @Nullable
    public SBuild findPreviousBuild(@NotNull SBuild sBuild) {
        try {
            if (sBuild == null) {
                BuildServerImpl.$$$reportNull$$$0(78);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        return this.myBuildsManager.findPreviousBuild(sBuild);
    }

    @Nullable
    public SBuild findPreviousBuild(@NotNull SBuild sBuild, @NotNull BuildDataFilter buildDataFilter) {
        try {
            if (sBuild == null) {
                BuildServerImpl.$$$reportNull$$$0(79);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        try {
            if (buildDataFilter == null) {
                BuildServerImpl.$$$reportNull$$$0(80);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        return this.myBuildsManager.findPreviousBuild(sBuild, buildDataFilter);
    }

    @Nullable
    public SBuild findNextBuild(@NotNull SBuild sBuild, @NotNull BuildDataFilter buildDataFilter) {
        try {
            if (sBuild == null) {
                BuildServerImpl.$$$reportNull$$$0(81);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        try {
            if (buildDataFilter == null) {
                BuildServerImpl.$$$reportNull$$$0(82);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        return this.myBuildsManager.findNextBuild(sBuild, buildDataFilter);
    }

    public Collection<SBuild> findBuildInstances(Collection<Long> collection) {
        return this.myBuildsManager.findBuildInstances(collection);
    }

    @Nullable
    public SBuild findBuildInstanceByBuildNumber(@NotNull String string, @NotNull String string2) {
        try {
            if (string == null) {
                BuildServerImpl.$$$reportNull$$$0(83);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        try {
            if (string2 == null) {
                BuildServerImpl.$$$reportNull$$$0(84);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        return this.myBuildsManager.findBuildInstanceByBuildNumber(string, string2);
    }

    @NotNull
    public List<SBuild> findBuildInstancesByBuildNumber(@NotNull String string, @NotNull String string2) {
        List list;
        try {
            if (string == null) {
                BuildServerImpl.$$$reportNull$$$0(85);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        try {
            if (string2 == null) {
                BuildServerImpl.$$$reportNull$$$0(86);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        try {
            list = this.myBuildsManager.findBuildInstancesByBuildNumber(string, string2);
            if (list == null) {
                BuildServerImpl.$$$reportNull$$$0(87);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        return list;
    }

    public void processBuilds(@NotNull BuildQueryOptions buildQueryOptions, @NotNull ItemProcessor<SBuild> itemProcessor) {
        try {
            if (buildQueryOptions == null) {
                BuildServerImpl.$$$reportNull$$$0(88);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        try {
            if (itemProcessor == null) {
                BuildServerImpl.$$$reportNull$$$0(89);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        this.myBuildsManager.processBuilds(buildQueryOptions, itemProcessor);
    }

    @NotNull
    public ProjectSettingsGeneratorRegistry getProjectSettingsGeneratorRegistry() {
        ProjectSettingsGeneratorRegistry projectSettingsGeneratorRegistry;
        try {
            projectSettingsGeneratorRegistry = this.myProjectSettingsGeneratorRegistry;
            if (projectSettingsGeneratorRegistry == null) {
                BuildServerImpl.$$$reportNull$$$0(90);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        return projectSettingsGeneratorRegistry;
    }

    public void setServerMetrics(@NotNull ServerMetrics serverMetrics) {
        try {
            if (serverMetrics == null) {
                BuildServerImpl.$$$reportNull$$$0(91);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildServerImpl.a(illegalArgumentException);
        }
        this.myServerMetrics = serverMetrics;
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

    private static IllegalArgumentException a(IllegalArgumentException illegalArgumentException) {
        return illegalArgumentException;
    }
}
