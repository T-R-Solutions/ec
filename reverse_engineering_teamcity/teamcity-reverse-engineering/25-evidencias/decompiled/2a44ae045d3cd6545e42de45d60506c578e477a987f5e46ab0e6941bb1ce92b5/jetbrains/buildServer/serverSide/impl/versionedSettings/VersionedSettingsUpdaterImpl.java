/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.util.concurrent.Striped
 *  com.google.gson.Gson
 *  com.intellij.openapi.diagnostic.Logger
 *  com.intellij.openapi.util.Pair
 *  jetbrains.buildServer.BuildProject
 *  jetbrains.buildServer.concurrent.DeduplicatingExecutor
 *  jetbrains.buildServer.log.Loggable
 *  jetbrains.buildServer.metrics.MetricDataType
 *  jetbrains.buildServer.metrics.ServerMetrics
 *  jetbrains.buildServer.serverSide.BuildServerListener
 *  jetbrains.buildServer.serverSide.MissingServerResponsibilityException
 *  jetbrains.buildServer.serverSide.MultiNodeTasks
 *  jetbrains.buildServer.serverSide.MultiNodeTasks$SubmittedTask
 *  jetbrains.buildServer.serverSide.MultiNodeTasks$Task
 *  jetbrains.buildServer.serverSide.MultiNodeTasks$TaskConsumer
 *  jetbrains.buildServer.serverSide.MultiNodeTasks$TaskData
 *  jetbrains.buildServer.serverSide.MultiNodesEvents
 *  jetbrains.buildServer.serverSide.PersistTask
 *  jetbrains.buildServer.serverSide.ProjectManager
 *  jetbrains.buildServer.serverSide.SPersistentEntity
 *  jetbrains.buildServer.serverSide.SProject
 *  jetbrains.buildServer.serverSide.ServerPaths
 *  jetbrains.buildServer.serverSide.ServerResponsibility
 *  jetbrains.buildServer.serverSide.TeamCityProperties
 *  jetbrains.buildServer.serverSide.VersionedSettingsRegistryEx
 *  jetbrains.buildServer.serverSide.audit.ActionType
 *  jetbrains.buildServer.serverSide.audit.AuditLogFactory
 *  jetbrains.buildServer.serverSide.identifiers.ProjectIdentifiersManager
 *  jetbrains.buildServer.serverSide.impl.LogUtil
 *  jetbrains.buildServer.serverSide.impl.projects.ConfigBackupException
 *  jetbrains.buildServer.serverSide.impl.projects.ConfigCauseInfo
 *  jetbrains.buildServer.serverSide.impl.projects.ConfigFilesUpdateOptions
 *  jetbrains.buildServer.serverSide.impl.projects.ConfigFilesUpdater
 *  jetbrains.buildServer.serverSide.impl.projects.ConfigGenerationException
 *  jetbrains.buildServer.serverSide.impl.projects.ConfigGenerationException$DisposableContinuation
 *  jetbrains.buildServer.serverSide.impl.projects.ConfigGenerator
 *  jetbrains.buildServer.serverSide.impl.projects.ConfigRollbackException
 *  jetbrains.buildServer.serverSide.impl.projects.ConfigUpdateCallback
 *  jetbrains.buildServer.serverSide.impl.projects.DeletedEntityTracker
 *  jetbrains.buildServer.serverSide.impl.projects.ProjectEventsNotifier
 *  jetbrains.buildServer.serverSide.impl.projects.ProjectManagerErrors
 *  jetbrains.buildServer.serverSide.impl.projects.ProjectsWatcher
 *  jetbrains.buildServer.serverSide.impl.projects.UuidChecker
 *  jetbrains.buildServer.serverSide.impl.versionedSettings.ConverterChangesStorage
 *  jetbrains.buildServer.serverSide.impl.versionedSettings.CurrentVersionTracker
 *  jetbrains.buildServer.serverSide.impl.versionedSettings.GenOptions
 *  jetbrains.buildServer.serverSide.impl.versionedSettings.GeneratedSettingsCache
 *  jetbrains.buildServer.serverSide.impl.versionedSettings.OutdatedProjectSettingsHealthReport
 *  jetbrains.buildServer.serverSide.impl.versionedSettings.ProjectSettingsGenerationResult
 *  jetbrains.buildServer.serverSide.impl.versionedSettings.ProjectSettingsGenerator
 *  jetbrains.buildServer.serverSide.impl.versionedSettings.ProjectSettingsGeneratorRegistry
 *  jetbrains.buildServer.serverSide.impl.versionedSettings.ProjectsBuilderFactory
 *  jetbrains.buildServer.serverSide.impl.versionedSettings.TeamCityCommits
 *  jetbrains.buildServer.serverSide.impl.versionedSettings.TeamCityCommits$CommitDetails
 *  jetbrains.buildServer.serverSide.impl.versionedSettings.UuidCheckerImpl
 *  jetbrains.buildServer.serverSide.impl.versionedSettings.VersionedSettingsConfig
 *  jetbrains.buildServer.serverSide.impl.versionedSettings.VersionedSettingsConfig$DslExecutionMode
 *  jetbrains.buildServer.serverSide.impl.versionedSettings.VersionedSettingsConfigErrorHealthReport
 *  jetbrains.buildServer.serverSide.impl.versionedSettings.VersionedSettingsConfigStorageImpl
 *  jetbrains.buildServer.serverSide.impl.versionedSettings.VersionedSettingsContentManager
 *  jetbrains.buildServer.serverSide.impl.versionedSettings.VersionedSettingsError
 *  jetbrains.buildServer.serverSide.impl.versionedSettings.VersionedSettingsError$Type
 *  jetbrains.buildServer.serverSide.impl.versionedSettings.VersionedSettingsException
 *  jetbrains.buildServer.serverSide.impl.versionedSettings.VersionedSettingsOptions
 *  jetbrains.buildServer.serverSide.impl.versionedSettings.VersionedSettingsPendingTransactions
 *  jetbrains.buildServer.serverSide.impl.versionedSettings.VersionedSettingsStatus
 *  jetbrains.buildServer.serverSide.impl.versionedSettings.VersionedSettingsStatus$Type
 *  jetbrains.buildServer.serverSide.impl.versionedSettings.VersionedSettingsStatusTracker
 *  jetbrains.buildServer.serverSide.impl.versionedSettings.VersionedSettingsUpdateConfig
 *  jetbrains.buildServer.serverSide.impl.versionedSettings.VersionedSettingsUpdater
 *  jetbrains.buildServer.serverSide.impl.versionedSettings.VersionedSettingsUpdaterImpl$3
 *  jetbrains.buildServer.serverSide.impl.versionedSettings.VersionedSettingsUpdaterImpl$4
 *  jetbrains.buildServer.serverSide.impl.versionedSettings.VersionedSettingsUpdaterImpl$AffectedProjectsGroup
 *  jetbrains.buildServer.serverSide.impl.versionedSettings.VersionedSettingsUpdaterImpl$ConfigUpdateCallback
 *  jetbrains.buildServer.serverSide.impl.versionedSettings.VersionedSettingsUpdaterImpl$LoadSettingsExecutorTaskKey
 *  jetbrains.buildServer.serverSide.impl.versionedSettings.VersionedSettingsUpdaterImpl$MultiProjectVcsConfigGenerator
 *  jetbrains.buildServer.serverSide.impl.versionedSettings.VersionedSettingsUtil
 *  jetbrains.buildServer.serverSide.versionedSettings.VersionedSettingsManager
 *  jetbrains.buildServer.users.SUser
 *  jetbrains.buildServer.users.UserModel
 *  jetbrains.buildServer.util.CollectionsUtil
 *  jetbrains.buildServer.util.Disposable
 *  jetbrains.buildServer.util.EventDispatcher
 *  jetbrains.buildServer.util.FileUtil
 *  jetbrains.buildServer.util.NamedDaemonThreadFactory
 *  jetbrains.buildServer.util.NamedThreadFactory
 *  jetbrains.buildServer.util.StringUtil
 *  jetbrains.buildServer.util.ThreadPoolMetricsInitializer
 *  jetbrains.buildServer.util.TimePrinter
 *  jetbrains.buildServer.util.executors.TeamCityThreadPoolExecutor
 *  jetbrains.buildServer.vcs.SVcsModification
 *  jetbrains.buildServer.vcs.VcsModificationHistoryEx
 *  jetbrains.buildServer.vcs.VcsRootInstance
 *  jetbrains.buildServer.vcs.VcsRootInstancesManager
 *  org.apache.commons.codec.digest.DigestUtils
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package jetbrains.buildServer.serverSide.impl.versionedSettings;

import com.google.common.util.concurrent.Striped;
import com.google.gson.Gson;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.util.Pair;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EventListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import jetbrains.buildServer.BuildProject;
import jetbrains.buildServer.concurrent.DeduplicatingExecutor;
import jetbrains.buildServer.log.Loggable;
import jetbrains.buildServer.metrics.MetricDataType;
import jetbrains.buildServer.metrics.ServerMetrics;
import jetbrains.buildServer.serverSide.BuildServerListener;
import jetbrains.buildServer.serverSide.MissingServerResponsibilityException;
import jetbrains.buildServer.serverSide.MultiNodeTasks;
import jetbrains.buildServer.serverSide.MultiNodesEvents;
import jetbrains.buildServer.serverSide.PersistTask;
import jetbrains.buildServer.serverSide.ProjectManager;
import jetbrains.buildServer.serverSide.SPersistentEntity;
import jetbrains.buildServer.serverSide.SProject;
import jetbrains.buildServer.serverSide.ServerPaths;
import jetbrains.buildServer.serverSide.ServerResponsibility;
import jetbrains.buildServer.serverSide.TeamCityProperties;
import jetbrains.buildServer.serverSide.VersionedSettingsRegistryEx;
import jetbrains.buildServer.serverSide.audit.ActionType;
import jetbrains.buildServer.serverSide.audit.AuditLogFactory;
import jetbrains.buildServer.serverSide.identifiers.ProjectIdentifiersManager;
import jetbrains.buildServer.serverSide.impl.LogUtil;
import jetbrains.buildServer.serverSide.impl.projects.ConfigBackupException;
import jetbrains.buildServer.serverSide.impl.projects.ConfigCauseInfo;
import jetbrains.buildServer.serverSide.impl.projects.ConfigFilesUpdateOptions;
import jetbrains.buildServer.serverSide.impl.projects.ConfigFilesUpdater;
import jetbrains.buildServer.serverSide.impl.projects.ConfigGenerationException;
import jetbrains.buildServer.serverSide.impl.projects.ConfigGenerator;
import jetbrains.buildServer.serverSide.impl.projects.ConfigRollbackException;
import jetbrains.buildServer.serverSide.impl.projects.DeletedEntityTracker;
import jetbrains.buildServer.serverSide.impl.projects.ProjectEventsNotifier;
import jetbrains.buildServer.serverSide.impl.projects.ProjectManagerErrors;
import jetbrains.buildServer.serverSide.impl.projects.ProjectsWatcher;
import jetbrains.buildServer.serverSide.impl.projects.UuidChecker;
import jetbrains.buildServer.serverSide.impl.versionedSettings.ConverterChangesStorage;
import jetbrains.buildServer.serverSide.impl.versionedSettings.CurrentVersionTracker;
import jetbrains.buildServer.serverSide.impl.versionedSettings.GenOptions;
import jetbrains.buildServer.serverSide.impl.versionedSettings.GeneratedSettingsCache;
import jetbrains.buildServer.serverSide.impl.versionedSettings.OutdatedProjectSettingsHealthReport;
import jetbrains.buildServer.serverSide.impl.versionedSettings.ProjectSettingsGenerationResult;
import jetbrains.buildServer.serverSide.impl.versionedSettings.ProjectSettingsGenerator;
import jetbrains.buildServer.serverSide.impl.versionedSettings.ProjectSettingsGeneratorRegistry;
import jetbrains.buildServer.serverSide.impl.versionedSettings.ProjectsBuilderFactory;
import jetbrains.buildServer.serverSide.impl.versionedSettings.TeamCityCommits;
import jetbrains.buildServer.serverSide.impl.versionedSettings.UuidCheckerImpl;
import jetbrains.buildServer.serverSide.impl.versionedSettings.VersionedSettingsConfig;
import jetbrains.buildServer.serverSide.impl.versionedSettings.VersionedSettingsConfigErrorHealthReport;
import jetbrains.buildServer.serverSide.impl.versionedSettings.VersionedSettingsConfigStorageImpl;
import jetbrains.buildServer.serverSide.impl.versionedSettings.VersionedSettingsContentManager;
import jetbrains.buildServer.serverSide.impl.versionedSettings.VersionedSettingsError;
import jetbrains.buildServer.serverSide.impl.versionedSettings.VersionedSettingsException;
import jetbrains.buildServer.serverSide.impl.versionedSettings.VersionedSettingsOptions;
import jetbrains.buildServer.serverSide.impl.versionedSettings.VersionedSettingsPendingTransactions;
import jetbrains.buildServer.serverSide.impl.versionedSettings.VersionedSettingsStatus;
import jetbrains.buildServer.serverSide.impl.versionedSettings.VersionedSettingsStatusTracker;
import jetbrains.buildServer.serverSide.impl.versionedSettings.VersionedSettingsUpdateConfig;
import jetbrains.buildServer.serverSide.impl.versionedSettings.VersionedSettingsUpdater;
import jetbrains.buildServer.serverSide.impl.versionedSettings.VersionedSettingsUpdaterImpl;
import jetbrains.buildServer.serverSide.impl.versionedSettings.VersionedSettingsUtil;
import jetbrains.buildServer.serverSide.versionedSettings.VersionedSettingsManager;
import jetbrains.buildServer.users.SUser;
import jetbrains.buildServer.users.UserModel;
import jetbrains.buildServer.util.CollectionsUtil;
import jetbrains.buildServer.util.Disposable;
import jetbrains.buildServer.util.EventDispatcher;
import jetbrains.buildServer.util.FileUtil;
import jetbrains.buildServer.util.NamedDaemonThreadFactory;
import jetbrains.buildServer.util.NamedThreadFactory;
import jetbrains.buildServer.util.StringUtil;
import jetbrains.buildServer.util.ThreadPoolMetricsInitializer;
import jetbrains.buildServer.util.TimePrinter;
import jetbrains.buildServer.util.executors.TeamCityThreadPoolExecutor;
import jetbrains.buildServer.vcs.SVcsModification;
import jetbrains.buildServer.vcs.VcsModificationHistoryEx;
import jetbrains.buildServer.vcs.VcsRootInstance;
import jetbrains.buildServer.vcs.VcsRootInstancesManager;
import org.apache.commons.codec.digest.DigestUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Exception performing whole class analysis ignored.
 */
public class VersionedSettingsUpdaterImpl
implements VersionedSettingsUpdater {
    private static final Logger LOG = Logger.getInstance((String)VersionedSettingsUpdaterImpl.class.getName());
    public static final String PENDING_UI_CHANGES_MSG = "There were UI changes and these changes are not yet committed to VCS";
    public static final String PENDING_UI_COMMIT_MSG_PREFIX = "There is a more recent commit made by the server as a result of UI changes: ";
    public static final String UPDATE_FROM_VCS_TASK = "updateFromVcs";
    private final ProjectManager myProjectManager;
    private final ProjectManagerErrors myProjectManagerErrors;
    private final ProjectIdentifiersManager myProjectIdentifiersManager;
    private final VcsModificationHistoryEx myVcsHistory;
    private final VersionedSettingsManager myVersionedSettingsManager;
    private final VersionedSettingsStatusTracker myStatusTracker;
    private final VersionedSettingsRegistryEx myVersionedSettingsRegistry;
    private final ConfigFilesUpdater myConfigFilesUpdater;
    private final CurrentVersionTracker myCurrentVersionTracker;
    private final VersionedSettingsOptions myOptions;
    private final VersionedSettingsConfigErrorHealthReport myHealthReport;
    private final ServerPaths myServerPaths;
    private final TeamCityCommits myTeamCityCommits;
    private final VersionedSettingsPendingTransactions myPendingTransactions;
    private final VersionedSettingsContentManager myVersionedSettingsContentManager;
    private final ProjectSettingsGeneratorRegistry myProjectSettingsGeneratorRegistry;
    private final ProjectsWatcher myProjectsWatcher;
    private final ConverterChangesStorage myConverterChangesStorage;
    private final OutdatedProjectSettingsHealthReport myOutdatedProjectSettingsHealthReport;
    private final DeletedEntityTracker myDeletedEntityTracker;
    private final ProjectsBuilderFactory myProjectsBuilderFactory;
    private final ConcurrentMap<String, Boolean> myUpdatingProjectExtIds;
    private final Striped<Semaphore> myUpdateSemaphore;
    private final AuditLogFactory myAuditLogFactory;
    private final ServerResponsibility myServerResponsibility;
    private final VcsRootInstancesManager myVcsRootInstanceManager;
    private final UserModel myUserModel;
    private final MultiNodeTasks myMultiNodeTasks;
    private final MultiNodesEvents myNodeEvents;
    private final DeduplicatingExecutor<LoadSettingsExecutorTaskKey> myLoadSettingsExecutor;
    private final ProjectEventsNotifier myProjectEventsNotifier;
    private final ServerMetrics myServerMetrics;
    private final GeneratedSettingsCache myGeneratedSettingsCache;
    private final Gson myGson;
    public static final String EXECUTOR_NAME = "Versioned settings update";
    private final ConcurrentMap<String, Long> myRecentTimes;

    public VersionedSettingsUpdaterImpl(@NotNull ProjectManager projectManager, @NotNull ProjectManagerErrors projectManagerErrors, @NotNull ProjectIdentifiersManager projectIdentifiersManager, @NotNull VcsModificationHistoryEx vcsModificationHistoryEx, @NotNull VersionedSettingsManager versionedSettingsManager, @NotNull VersionedSettingsStatusTracker versionedSettingsStatusTracker, @NotNull VersionedSettingsRegistryEx versionedSettingsRegistryEx, @NotNull ConfigFilesUpdater configFilesUpdater, @NotNull CurrentVersionTracker currentVersionTracker, @NotNull VersionedSettingsOptions versionedSettingsOptions, @NotNull VersionedSettingsConfigErrorHealthReport versionedSettingsConfigErrorHealthReport, @NotNull ServerPaths serverPaths, @NotNull TeamCityCommits teamCityCommits, @NotNull VersionedSettingsPendingTransactions versionedSettingsPendingTransactions, @NotNull VersionedSettingsContentManager versionedSettingsContentManager, @NotNull ProjectSettingsGeneratorRegistry projectSettingsGeneratorRegistry, @NotNull ProjectsWatcher projectsWatcher, @NotNull ConverterChangesStorage converterChangesStorage, @NotNull OutdatedProjectSettingsHealthReport outdatedProjectSettingsHealthReport, @NotNull DeletedEntityTracker deletedEntityTracker, @NotNull ProjectsBuilderFactory projectsBuilderFactory, @NotNull AuditLogFactory auditLogFactory, @NotNull EventDispatcher<BuildServerListener> eventDispatcher, @NotNull ServerResponsibility serverResponsibility, @NotNull VcsRootInstancesManager vcsRootInstancesManager, @NotNull UserModel userModel, @NotNull MultiNodeTasks multiNodeTasks, @NotNull MultiNodesEvents multiNodesEvents, @NotNull ProjectEventsNotifier projectEventsNotifier, @NotNull ServerMetrics serverMetrics, @NotNull GeneratedSettingsCache generatedSettingsCache) {
        if (projectManager == null) {
            VersionedSettingsUpdaterImpl.$$$reportNull$$$0(0);
        }
        if (projectManagerErrors == null) {
            VersionedSettingsUpdaterImpl.$$$reportNull$$$0(1);
        }
        if (projectIdentifiersManager == null) {
            VersionedSettingsUpdaterImpl.$$$reportNull$$$0(2);
        }
        if (vcsModificationHistoryEx == null) {
            VersionedSettingsUpdaterImpl.$$$reportNull$$$0(3);
        }
        if (versionedSettingsManager == null) {
            VersionedSettingsUpdaterImpl.$$$reportNull$$$0(4);
        }
        if (versionedSettingsStatusTracker == null) {
            VersionedSettingsUpdaterImpl.$$$reportNull$$$0(5);
        }
        if (versionedSettingsRegistryEx == null) {
            VersionedSettingsUpdaterImpl.$$$reportNull$$$0(6);
        }
        if (configFilesUpdater == null) {
            VersionedSettingsUpdaterImpl.$$$reportNull$$$0(7);
        }
        if (currentVersionTracker == null) {
            VersionedSettingsUpdaterImpl.$$$reportNull$$$0(8);
        }
        if (versionedSettingsOptions == null) {
            VersionedSettingsUpdaterImpl.$$$reportNull$$$0(9);
        }
        if (versionedSettingsConfigErrorHealthReport == null) {
            VersionedSettingsUpdaterImpl.$$$reportNull$$$0(10);
        }
        if (serverPaths == null) {
            VersionedSettingsUpdaterImpl.$$$reportNull$$$0(11);
        }
        if (teamCityCommits == null) {
            VersionedSettingsUpdaterImpl.$$$reportNull$$$0(12);
        }
        if (versionedSettingsPendingTransactions == null) {
            VersionedSettingsUpdaterImpl.$$$reportNull$$$0(13);
        }
        if (versionedSettingsContentManager == null) {
            VersionedSettingsUpdaterImpl.$$$reportNull$$$0(14);
        }
        if (projectSettingsGeneratorRegistry == null) {
            VersionedSettingsUpdaterImpl.$$$reportNull$$$0(15);
        }
        if (projectsWatcher == null) {
            VersionedSettingsUpdaterImpl.$$$reportNull$$$0(16);
        }
        if (converterChangesStorage == null) {
            VersionedSettingsUpdaterImpl.$$$reportNull$$$0(17);
        }
        if (outdatedProjectSettingsHealthReport == null) {
            VersionedSettingsUpdaterImpl.$$$reportNull$$$0(18);
        }
        if (deletedEntityTracker == null) {
            VersionedSettingsUpdaterImpl.$$$reportNull$$$0(19);
        }
        if (projectsBuilderFactory == null) {
            VersionedSettingsUpdaterImpl.$$$reportNull$$$0(20);
        }
        if (auditLogFactory == null) {
            VersionedSettingsUpdaterImpl.$$$reportNull$$$0(21);
        }
        if (eventDispatcher == null) {
            VersionedSettingsUpdaterImpl.$$$reportNull$$$0(22);
        }
        if (serverResponsibility == null) {
            VersionedSettingsUpdaterImpl.$$$reportNull$$$0(23);
        }
        if (vcsRootInstancesManager == null) {
            VersionedSettingsUpdaterImpl.$$$reportNull$$$0(24);
        }
        if (userModel == null) {
            VersionedSettingsUpdaterImpl.$$$reportNull$$$0(25);
        }
        if (multiNodeTasks == null) {
            VersionedSettingsUpdaterImpl.$$$reportNull$$$0(26);
        }
        if (multiNodesEvents == null) {
            VersionedSettingsUpdaterImpl.$$$reportNull$$$0(27);
        }
        if (projectEventsNotifier == null) {
            VersionedSettingsUpdaterImpl.$$$reportNull$$$0(28);
        }
        if (serverMetrics == null) {
            VersionedSettingsUpdaterImpl.$$$reportNull$$$0(29);
        }
        if (generatedSettingsCache == null) {
            VersionedSettingsUpdaterImpl.$$$reportNull$$$0(30);
        }
        this.myUpdatingProjectExtIds = new ConcurrentHashMap<String, Boolean>();
        this.myUpdateSemaphore = Striped.lazyWeakSemaphore((int)256, (int)1);
        this.myGson = new Gson();
        this.myRecentTimes = new ConcurrentHashMap<String, Long>();
        this.myProjectManager = projectManager;
        this.myProjectManagerErrors = projectManagerErrors;
        this.myProjectIdentifiersManager = projectIdentifiersManager;
        this.myVcsHistory = vcsModificationHistoryEx;
        this.myVersionedSettingsManager = versionedSettingsManager;
        this.myStatusTracker = versionedSettingsStatusTracker;
        this.myVersionedSettingsRegistry = versionedSettingsRegistryEx;
        this.myConfigFilesUpdater = configFilesUpdater;
        this.myCurrentVersionTracker = currentVersionTracker;
        this.myOptions = versionedSettingsOptions;
        this.myHealthReport = versionedSettingsConfigErrorHealthReport;
        this.myServerPaths = serverPaths;
        this.myTeamCityCommits = teamCityCommits;
        this.myPendingTransactions = versionedSettingsPendingTransactions;
        this.myVersionedSettingsContentManager = versionedSettingsContentManager;
        this.myProjectSettingsGeneratorRegistry = projectSettingsGeneratorRegistry;
        this.myProjectsWatcher = projectsWatcher;
        this.myConverterChangesStorage = converterChangesStorage;
        this.myOutdatedProjectSettingsHealthReport = outdatedProjectSettingsHealthReport;
        this.myDeletedEntityTracker = deletedEntityTracker;
        this.myProjectsBuilderFactory = projectsBuilderFactory;
        this.myAuditLogFactory = auditLogFactory;
        this.myServerResponsibility = serverResponsibility;
        this.myMultiNodeTasks = multiNodeTasks;
        this.myNodeEvents = multiNodesEvents;
        this.myVcsRootInstanceManager = vcsRootInstancesManager;
        this.myUserModel = userModel;
        this.myServerMetrics = serverMetrics;
        this.myLoadSettingsExecutor = this.createExecutor(versionedSettingsOptions);
        this.myProjectEventsNotifier = projectEventsNotifier;
        this.myGeneratedSettingsCache = generatedSettingsCache;
        eventDispatcher.addListener((EventListener)new /* Unavailable Anonymous Inner Class!! */);
        multiNodeTasks.subscribe("updateFromVcs", (MultiNodeTasks.TaskConsumer)new /* Unavailable Anonymous Inner Class!! */);
    }

    public void updateFromVcs(@NotNull VersionedSettingsUpdateConfig versionedSettingsUpdateConfig, @Nullable Supplier<String> supplier) {
        try {
            if (versionedSettingsUpdateConfig == null) {
                VersionedSettingsUpdaterImpl.$$$reportNull$$$0(31);
            }
        }
        catch (MissingServerResponsibilityException missingServerResponsibilityException) {
            throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
        }
        Set<AffectedProjectsGroup> set = this.getAffectedProjectsGroups(versionedSettingsUpdateConfig);
        set.forEach(affectedProjectsGroup -> this.myStatusTracker.setStatusForConfigOwners((Collection)affectedProjectsGroup.getAffectedAndImportedProjects(), "Waiting for update from VCS for the projects with the same Settings Root"));
        set.forEach(affectedProjectsGroup -> this.runUpdateFromVcs(versionedSettingsUpdateConfig, (AffectedProjectsGroup)affectedProjectsGroup, supplier, true));
    }

    private void runUpdateFromVcs(@NotNull VersionedSettingsUpdateConfig versionedSettingsUpdateConfig, @NotNull AffectedProjectsGroup affectedProjectsGroup, @Nullable Supplier<String> supplier, boolean bl) {
        try {
            if (versionedSettingsUpdateConfig == null) {
                VersionedSettingsUpdaterImpl.$$$reportNull$$$0(32);
            }
        }
        catch (MissingServerResponsibilityException missingServerResponsibilityException) {
            throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
        }
        try {
            if (affectedProjectsGroup == null) {
                VersionedSettingsUpdaterImpl.$$$reportNull$$$0(33);
            }
        }
        catch (MissingServerResponsibilityException missingServerResponsibilityException) {
            throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
        }
        try {
            this.checkCanRunUpdateFromVcs(supplier);
            if (!this.myServerResponsibility.canWriteToConfigDirectory()) {
                this.submitMultiNodeTaskAndWait(versionedSettingsUpdateConfig, bl);
                return;
            }
        }
        catch (MissingServerResponsibilityException missingServerResponsibilityException) {
            throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
        }
        Supplier<String> supplier2 = () -> {
            try {
                if (!this.myServerResponsibility.canWriteToConfigDirectory()) {
                    return "This node cannot change configuration files on disk";
                }
            }
            catch (MissingServerResponsibilityException missingServerResponsibilityException) {
                throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
            }
            try {
                if (supplier != null) {
                    return (String)supplier.get();
                }
            }
            catch (MissingServerResponsibilityException missingServerResponsibilityException) {
                throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
            }
            return null;
        };
        VcsRootInstance vcsRootInstance = this.getVcsRootInstance(versionedSettingsUpdateConfig);
        this.doUpdateFromVcs(versionedSettingsUpdateConfig, vcsRootInstance, affectedProjectsGroup, supplier2, bl);
    }

    /*
     * Loose catch block
     */
    private void submitMultiNodeTaskAndWait(@NotNull VersionedSettingsUpdateConfig versionedSettingsUpdateConfig, boolean bl) {
        block9: {
            try {
                if (versionedSettingsUpdateConfig == null) {
                    VersionedSettingsUpdaterImpl.$$$reportNull$$$0(34);
                }
            }
            catch (Throwable throwable) {
                throw VersionedSettingsUpdaterImpl.a(throwable);
            }
            try {
                MultiNodeTasks.SubmittedTask submittedTask = this.submitMultiNodeTask(versionedSettingsUpdateConfig);
                if (!bl) break block9;
                try {
                    block10: {
                        submittedTask.waitFor();
                        if (versionedSettingsUpdateConfig.getImportedProjectExtId() == null) break block9;
                        break block10;
                        catch (Throwable throwable) {
                            throw VersionedSettingsUpdaterImpl.a(throwable);
                        }
                    }
                    this.myNodeEvents.ensureEventsProcessed("projectConfigChanged");
                }
                catch (Throwable throwable) {
                    throw VersionedSettingsUpdaterImpl.a(throwable);
                }
            }
            catch (Throwable throwable) {
                LOG.warnAndDebugDetails("Could not wait for an update from VCS task to complete", throwable);
            }
        }
    }

    public void scheduleUpdateFromVcs(@NotNull VersionedSettingsUpdateConfig versionedSettingsUpdateConfig, @Nullable Supplier<String> supplier) {
        try {
            if (versionedSettingsUpdateConfig == null) {
                VersionedSettingsUpdaterImpl.$$$reportNull$$$0(35);
            }
        }
        catch (MissingServerResponsibilityException missingServerResponsibilityException) {
            throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
        }
        this.checkCanRunUpdateFromVcs(supplier);
        this.adjustThreadPoolSize();
        VcsRootInstance vcsRootInstance = this.getVcsRootInstance(versionedSettingsUpdateConfig);
        Set<AffectedProjectsGroup> set = this.getAffectedProjectsGroups(versionedSettingsUpdateConfig);
        set.forEach(affectedProjectsGroup -> {
            LoadSettingsExecutorTaskKey loadSettingsExecutorTaskKey = new LoadSettingsExecutorTaskKey(this, vcsRootInstance, affectedProjectsGroup.getSettingsRules(), affectedProjectsGroup.getDslExecutionMode());
            this.scheduleTask(loadSettingsExecutorTaskKey, () -> this.lambda$scheduleUpdateFromVcs$3(versionedSettingsUpdateConfig, affectedProjectsGroup, (Supplier)supplier), "update settings to revision " + versionedSettingsUpdateConfig.getTargetRevision());
        });
    }

    private void adjustThreadPoolSize() {
        int n = this.myOptions.getApplySettingsChangesThreadCount();
        ExecutorService executorService = this.myLoadSettingsExecutor.getDelegate();
        if (executorService instanceof TeamCityThreadPoolExecutor) {
            TeamCityThreadPoolExecutor teamCityThreadPoolExecutor = (TeamCityThreadPoolExecutor)executorService;
            try {
                if (teamCityThreadPoolExecutor.getMaximumPoolSize() != n) {
                    teamCityThreadPoolExecutor.setPoolSize(n);
                }
            }
            catch (MissingServerResponsibilityException missingServerResponsibilityException) {
                throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void scheduleTask(@NotNull LoadSettingsExecutorTaskKey loadSettingsExecutorTaskKey, @NotNull Runnable runnable, @NotNull String string) {
        try {
            if (loadSettingsExecutorTaskKey == null) {
                VersionedSettingsUpdaterImpl.$$$reportNull$$$0(36);
            }
        }
        catch (MissingServerResponsibilityException missingServerResponsibilityException) {
            throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
        }
        try {
            if (runnable == null) {
                VersionedSettingsUpdaterImpl.$$$reportNull$$$0(37);
            }
        }
        catch (MissingServerResponsibilityException missingServerResponsibilityException) {
            throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
        }
        try {
            if (string == null) {
                VersionedSettingsUpdaterImpl.$$$reportNull$$$0(38);
            }
        }
        catch (MissingServerResponsibilityException missingServerResponsibilityException) {
            throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
        }
        boolean bl = runnable instanceof ConfigGenerationException.DisposableContinuation;
        3 var5_5 = new /* Unavailable Anonymous Inner Class!! */;
        if (bl) {
            boolean bl2 = false;
            try {
                bl2 = this.myLoadSettingsExecutor.submitIfAbsent((Object)loadSettingsExecutorTaskKey, (Runnable)var5_5);
            }
            catch (Throwable throwable) {
                try {
                    if (bl2) throw throwable;
                    var5_5.dispose("The task has become obsolete because another task is already running");
                    throw throwable;
                }
                catch (MissingServerResponsibilityException missingServerResponsibilityException) {
                    throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
                }
            }
            try {
                if (bl2) return;
                var5_5.dispose("The task has become obsolete because another task is already running");
                return;
            }
            catch (MissingServerResponsibilityException missingServerResponsibilityException) {
                throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
            }
        }
        Runnable runnable2 = this.myLoadSettingsExecutor.submit((Object)loadSettingsExecutorTaskKey, (Runnable)var5_5);
        if (!(runnable2 instanceof ConfigGenerationException.DisposableContinuation)) return;
        ConfigGenerationException.DisposableContinuation disposableContinuation = (ConfigGenerationException.DisposableContinuation)runnable2;
        disposableContinuation.dispose("Made obsolete by a newer task: " + string);
    }

    private void checkCanRunUpdateFromVcs(@Nullable Supplier<String> supplier) {
        block4: {
            try {
                try {
                    if (this.myServerResponsibility.canWriteToConfigDirectory() || supplier == null) break block4;
                }
                catch (MissingServerResponsibilityException missingServerResponsibilityException) {
                    throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
                }
                throw new MissingServerResponsibilityException("This node cannot write to project configuration files");
            }
            catch (MissingServerResponsibilityException missingServerResponsibilityException) {
                throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
            }
        }
    }

    @NotNull
    private MultiNodeTasks.SubmittedTask submitMultiNodeTask(@NotNull VersionedSettingsUpdateConfig versionedSettingsUpdateConfig) {
        MultiNodeTasks.SubmittedTask submittedTask;
        try {
            if (versionedSettingsUpdateConfig == null) {
                VersionedSettingsUpdaterImpl.$$$reportNull$$$0(39);
            }
        }
        catch (MissingServerResponsibilityException missingServerResponsibilityException) {
            throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
        }
        String string = this.serializeUpdateConfig(versionedSettingsUpdateConfig);
        String string2 = DigestUtils.sha1Hex((String)string);
        try {
            submittedTask = this.myMultiNodeTasks.submit((MultiNodeTasks.Task)new MultiNodeTasks.TaskData("updateFromVcs", string2, string));
            if (submittedTask == null) {
                VersionedSettingsUpdaterImpl.$$$reportNull$$$0(40);
            }
        }
        catch (MissingServerResponsibilityException missingServerResponsibilityException) {
            throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
        }
        return submittedTask;
    }

    @NotNull
    private String serializeUpdateConfig(@NotNull VersionedSettingsUpdateConfig versionedSettingsUpdateConfig) {
        String string;
        try {
            if (versionedSettingsUpdateConfig == null) {
                VersionedSettingsUpdaterImpl.$$$reportNull$$$0(41);
            }
        }
        catch (MissingServerResponsibilityException missingServerResponsibilityException) {
            throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
        }
        try {
            string = this.myGson.toJson((Object)versionedSettingsUpdateConfig);
            if (string == null) {
                VersionedSettingsUpdaterImpl.$$$reportNull$$$0(42);
            }
        }
        catch (MissingServerResponsibilityException missingServerResponsibilityException) {
            throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
        }
        return string;
    }

    @NotNull
    private VersionedSettingsUpdateConfig deserializeUpdateConfig(@NotNull String string) {
        VersionedSettingsUpdateConfig versionedSettingsUpdateConfig;
        try {
            if (string == null) {
                VersionedSettingsUpdaterImpl.$$$reportNull$$$0(43);
            }
        }
        catch (MissingServerResponsibilityException missingServerResponsibilityException) {
            throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
        }
        try {
            versionedSettingsUpdateConfig = (VersionedSettingsUpdateConfig)this.myGson.fromJson(string, VersionedSettingsUpdateConfig.class);
            if (versionedSettingsUpdateConfig == null) {
                VersionedSettingsUpdaterImpl.$$$reportNull$$$0(44);
            }
        }
        catch (MissingServerResponsibilityException missingServerResponsibilityException) {
            throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
        }
        return versionedSettingsUpdateConfig;
    }

    @NotNull
    private VcsRootInstance getVcsRootInstance(@NotNull VersionedSettingsUpdateConfig versionedSettingsUpdateConfig) {
        VcsRootInstance vcsRootInstance;
        try {
            if (versionedSettingsUpdateConfig == null) {
                VersionedSettingsUpdaterImpl.$$$reportNull$$$0(45);
            }
        }
        catch (MissingServerResponsibilityException missingServerResponsibilityException) {
            throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
        }
        VcsRootInstance vcsRootInstance2 = this.myVcsRootInstanceManager.findRootInstanceById(versionedSettingsUpdateConfig.getSettingsRootId());
        try {
            if (vcsRootInstance2 == null) {
                throw new IllegalArgumentException("Could not find VcsRootInstance with id: " + versionedSettingsUpdateConfig.getSettingsRootId());
            }
        }
        catch (MissingServerResponsibilityException missingServerResponsibilityException) {
            throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
        }
        try {
            vcsRootInstance = vcsRootInstance2;
            if (vcsRootInstance == null) {
                VersionedSettingsUpdaterImpl.$$$reportNull$$$0(46);
            }
        }
        catch (MissingServerResponsibilityException missingServerResponsibilityException) {
            throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
        }
        return vcsRootInstance;
    }

    @NotNull
    private DeduplicatingExecutor<LoadSettingsExecutorTaskKey> createExecutor(@NotNull VersionedSettingsOptions versionedSettingsOptions) {
        try {
            if (versionedSettingsOptions == null) {
                VersionedSettingsUpdaterImpl.$$$reportNull$$$0(47);
            }
        }
        catch (MissingServerResponsibilityException missingServerResponsibilityException) {
            throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
        }
        int n = versionedSettingsOptions.getApplySettingsChangesQueueSize();
        int n2 = versionedSettingsOptions.getApplySettingsChangesThreadCount();
        ArrayBlockingQueue arrayBlockingQueue = new ArrayBlockingQueue(n);
        TeamCityThreadPoolExecutor teamCityThreadPoolExecutor = new TeamCityThreadPoolExecutor(n2, n2, 60L, TimeUnit.SECONDS, arrayBlockingQueue, (ThreadFactory)new NamedDaemonThreadFactory("Versioned settings update"));
        teamCityThreadPoolExecutor.allowCoreThreadTimeOut(true);
        new ThreadPoolMetricsInitializer(this.myServerMetrics).initExecutorServiceMetrics("versionedSettingsUpdate", (ExecutorService)teamCityThreadPoolExecutor);
        return new DeduplicatingExecutor((ExecutorService)teamCityThreadPoolExecutor);
    }

    private void doUpdateFromVcs(@NotNull VersionedSettingsUpdateConfig versionedSettingsUpdateConfig, @NotNull VcsRootInstance vcsRootInstance, @NotNull AffectedProjectsGroup affectedProjectsGroup, @Nullable Supplier<String> supplier, boolean bl) {
        try {
            if (versionedSettingsUpdateConfig == null) {
                VersionedSettingsUpdaterImpl.$$$reportNull$$$0(48);
            }
        }
        catch (MissingServerResponsibilityException missingServerResponsibilityException) {
            throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
        }
        try {
            if (vcsRootInstance == null) {
                VersionedSettingsUpdaterImpl.$$$reportNull$$$0(49);
            }
        }
        catch (MissingServerResponsibilityException missingServerResponsibilityException) {
            throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
        }
        try {
            if (affectedProjectsGroup == null) {
                VersionedSettingsUpdaterImpl.$$$reportNull$$$0(50);
            }
        }
        catch (MissingServerResponsibilityException missingServerResponsibilityException) {
            throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
        }
        try {
            if (this.checkSkipReason(versionedSettingsUpdateConfig, vcsRootInstance, supplier) != null) {
                return;
            }
        }
        catch (MissingServerResponsibilityException missingServerResponsibilityException) {
            throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
        }
        String string = affectedProjectsGroup.getSettingsRules();
        long l = System.nanoTime();
        String string2 = versionedSettingsUpdateConfig.getTargetRevision();
        LOG.info("Start updating settings from VCS in root " + vcsRootInstance.describe(false) + ", target revision: " + string2 + ", settings path: " + String.valueOf(affectedProjectsGroup.getSettingsRules()));
        String string3 = this.getLastTeamCityCommit(vcsRootInstance);
        AtomicReference atomicReference = new AtomicReference();
        LoadSettingsExecutorTaskKey loadSettingsExecutorTaskKey = new LoadSettingsExecutorTaskKey(this, vcsRootInstance, string, affectedProjectsGroup.getDslExecutionMode());
        AtomicReference atomicReference2 = new AtomicReference();
        AtomicReference atomicReference3 = new AtomicReference();
        4 var15_14 = new /* Unavailable Anonymous Inner Class!! */;
        var15_14.run();
    }

    @NotNull
    private List<ProjectSettingsGenerationResult> waitFor(@NotNull Future<List<ProjectSettingsGenerationResult>> future, boolean bl) throws ExecutionException, InterruptedException, TimeoutException {
        List<ProjectSettingsGenerationResult> list;
        block10: {
            List<ProjectSettingsGenerationResult> list2;
            block11: {
                try {
                    if (future == null) {
                        VersionedSettingsUpdaterImpl.$$$reportNull$$$0(51);
                    }
                }
                catch (ExecutionException executionException) {
                    throw VersionedSettingsUpdaterImpl.a(executionException);
                }
                try {
                    try {
                        if (!bl) break block10;
                        list2 = future.get();
                        if (list2 != null) break block11;
                    }
                    catch (ExecutionException executionException) {
                        throw VersionedSettingsUpdaterImpl.a(executionException);
                    }
                    VersionedSettingsUpdaterImpl.$$$reportNull$$$0(52);
                }
                catch (ExecutionException executionException) {
                    throw VersionedSettingsUpdaterImpl.a(executionException);
                }
            }
            return list2;
        }
        try {
            list = future.get(VersionedSettingsUpdaterImpl.getTimeoutMillis(), TimeUnit.MILLISECONDS);
            if (list == null) {
                VersionedSettingsUpdaterImpl.$$$reportNull$$$0(53);
            }
        }
        catch (ExecutionException executionException) {
            throw VersionedSettingsUpdaterImpl.a(executionException);
        }
        return list;
    }

    private static long getTimeoutMillis() {
        return TeamCityProperties.getIntervalMilliseconds((String)"teamcity.versionedSettings.generator.timeout", (long)5000L);
    }

    private static long getLargeTimeoutMillis() {
        return TeamCityProperties.getLong((String)"teamcity.configsUpdater.maxWaitTime", (long)TimeUnit.MINUTES.toMillis(30L));
    }

    @NotNull
    private PersistTask scheduleConfigUpdatingTask(@NotNull List<ProjectSettingsGenerationResult> list2, @NotNull File file, @NotNull GenOptions genOptions, long l, @NotNull VersionedSettingsUpdateConfig versionedSettingsUpdateConfig, @NotNull VcsRootInstance vcsRootInstance, @NotNull AffectedProjectsGroup affectedProjectsGroup, @NotNull Set<SProject> set, @Nullable Supplier<String> supplier, @NotNull ConfigUpdateCallback configUpdateCallback, @NotNull Disposable disposable) {
        PersistTask persistTask;
        boolean bl;
        ConfigFilesUpdateOptions configFilesUpdateOptions;
        ConfigFilesUpdateOptions configFilesUpdateOptions2;
        HashSet hashSet;
        String string;
        block36: {
            block35: {
                try {
                    if (list2 == null) {
                        VersionedSettingsUpdaterImpl.$$$reportNull$$$0(54);
                    }
                }
                catch (MissingServerResponsibilityException missingServerResponsibilityException) {
                    throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
                }
                try {
                    if (file == null) {
                        VersionedSettingsUpdaterImpl.$$$reportNull$$$0(55);
                    }
                }
                catch (MissingServerResponsibilityException missingServerResponsibilityException) {
                    throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
                }
                try {
                    if (genOptions == null) {
                        VersionedSettingsUpdaterImpl.$$$reportNull$$$0(56);
                    }
                }
                catch (MissingServerResponsibilityException missingServerResponsibilityException) {
                    throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
                }
                try {
                    if (versionedSettingsUpdateConfig == null) {
                        VersionedSettingsUpdaterImpl.$$$reportNull$$$0(57);
                    }
                }
                catch (MissingServerResponsibilityException missingServerResponsibilityException) {
                    throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
                }
                try {
                    if (vcsRootInstance == null) {
                        VersionedSettingsUpdaterImpl.$$$reportNull$$$0(58);
                    }
                }
                catch (MissingServerResponsibilityException missingServerResponsibilityException) {
                    throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
                }
                try {
                    if (affectedProjectsGroup == null) {
                        VersionedSettingsUpdaterImpl.$$$reportNull$$$0(59);
                    }
                }
                catch (MissingServerResponsibilityException missingServerResponsibilityException) {
                    throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
                }
                try {
                    if (set == null) {
                        VersionedSettingsUpdaterImpl.$$$reportNull$$$0(60);
                    }
                }
                catch (MissingServerResponsibilityException missingServerResponsibilityException) {
                    throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
                }
                try {
                    if (configUpdateCallback == null) {
                        VersionedSettingsUpdaterImpl.$$$reportNull$$$0(61);
                    }
                }
                catch (MissingServerResponsibilityException missingServerResponsibilityException) {
                    throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
                }
                try {
                    if (disposable == null) {
                        VersionedSettingsUpdaterImpl.$$$reportNull$$$0(62);
                    }
                }
                catch (MissingServerResponsibilityException missingServerResponsibilityException) {
                    throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
                }
                string = versionedSettingsUpdateConfig.getTargetRevision();
                LOG.info("Settings from VCS are generated (root: " + vcsRootInstance.describe(false) + ", revision: " + string + ")");
                hashSet = new HashSet();
                for (ProjectSettingsGenerationResult projectSettingsGenerationResult : list2) {
                    projectSettingsGenerationResult.getProjectsWithMissingContextParameters().forEach((string2, list) -> {
                        block4: {
                            SProject sProject;
                            block6: {
                                block5: {
                                    sProject = this.myProjectManager.findProjectByExternalId(string2);
                                    try {
                                        try {
                                            if (sProject == null) break block4;
                                            hashSet.add(sProject);
                                            if (sProject.equals((Object)this.myVersionedSettingsManager.getVersionedSettingsProject(sProject))) break block5;
                                        }
                                        catch (MissingServerResponsibilityException missingServerResponsibilityException) {
                                            throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
                                        }
                                        if (!sProject.getExternalId().equals(versionedSettingsUpdateConfig.getImportedProjectExtId())) break block6;
                                    }
                                    catch (MissingServerResponsibilityException missingServerResponsibilityException) {
                                        throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
                                    }
                                }
                                VersionedSettingsStatus versionedSettingsStatus = new VersionedSettingsStatus(this.getFailedToApplyMessage("Required context parameters not specified", string, false), VersionedSettingsStatus.Type.WARN);
                                versionedSettingsStatus.setRequiredContextParameters(list);
                                this.myStatusTracker.setStatusForConfigOwners(Collections.singletonList(sProject), versionedSettingsStatus);
                                break block4;
                            }
                            VersionedSettingsStatus versionedSettingsStatus = new VersionedSettingsStatus(this.getFailedToApplyMessage("Required context parameters not specified (see parent project status)", string, false), VersionedSettingsStatus.Type.WARN);
                            this.myStatusTracker.setStatusForConfigOwners(Collections.singletonList(sProject), versionedSettingsStatus);
                        }
                    });
                }
                configUpdateCallback.setGeneratedConfigsDir(file);
                configUpdateCallback.setBeforeUpdateCallback(() -> list2.forEach(ProjectSettingsGenerationResult::runBeforeUpdateCallback));
                long l2 = System.nanoTime();
                configUpdateCallback.setAfterUpdateCallback(() -> {
                    try {
                        try {
                            list2.forEach(ProjectSettingsGenerationResult::runAfterUpdateCallback);
                            if (versionedSettingsUpdateConfig.isReloadUnchangedProjects()) {
                                this.myGeneratedSettingsCache.cleanupCache(vcsRootInstance.getId());
                            }
                        }
                        catch (MissingServerResponsibilityException missingServerResponsibilityException) {
                            throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
                        }
                        this.myProjectEventsNotifier.ensureCollectedEventsPublished(TeamCityProperties.getLong((String)"teamcity.configsUpdater.maxWaitUnpublishedEventsTime", (long)30000L));
                    }
                    finally {
                        disposable.dispose();
                        String string = this.getMetricsProjectId(vcsRootInstance, set);
                        this.recordExecutionTime(string, "apply", l2);
                    }
                });
                configUpdateCallback.setSuccessfulUpdateCallback(set2 -> {
                    list2.forEach(projectSettingsGenerationResult -> projectSettingsGenerationResult.projectSettingsUpdated(set2));
                    Set<SProject> set3 = CollectionsUtil.minus((Collection)affectedProjectsGroup.getAffectedAndImportedProjects(), (Collection)hashSet).stream().map(sProject -> this.myProjectManager.findProjectByExternalId(sProject.getExternalId())).filter(Objects::nonNull).collect(Collectors.toSet());
                    SVcsModification sVcsModification = this.myVcsHistory.findModificationByVersion(vcsRootInstance, string);
                    this.updateStatus(set3, string, sVcsModification, l);
                    this.auditProjectSettingsUpdate(set3, versionedSettingsUpdateConfig);
                    this.myOutdatedProjectSettingsHealthReport.addGenerationResults(vcsRootInstance, list2);
                });
                configUpdateCallback.setFailedUpdateCallback(throwable -> {
                    list2.forEach(ProjectSettingsGenerationResult::projectSettingsUpdateFailed);
                    this.handleUpdateError((Throwable)throwable, affectedProjectsGroup.getAffectedAndImportedProjects(), string, vcsRootInstance, configUpdateCallback);
                });
                configFilesUpdateOptions2 = new ConfigFilesUpdateOptions();
                try {
                    try {
                        configFilesUpdateOptions = configFilesUpdateOptions2;
                        if (!versionedSettingsUpdateConfig.isReloadUnchangedProjects() && !this.hasErrors(vcsRootInstance, affectedProjectsGroup.getAffectedAndImportedProjects())) break block35;
                    }
                    catch (MissingServerResponsibilityException missingServerResponsibilityException) {
                        throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
                    }
                    bl = true;
                    break block36;
                }
                catch (MissingServerResponsibilityException missingServerResponsibilityException) {
                    throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
                }
            }
            bl = false;
        }
        configFilesUpdateOptions.setReloadUnchangedProjects(bl);
        this.configureUuidChecker(affectedProjectsGroup.getAffectedAndImportedProjects(), configFilesUpdateOptions2);
        ConfigCauseInfo configCauseInfo = new ConfigCauseInfo();
        configCauseInfo.setTargetRevision(string);
        configCauseInfo.setTopProjects(set);
        MultiProjectVcsConfigGenerator multiProjectVcsConfigGenerator = new MultiProjectVcsConfigGenerator(this, versionedSettingsUpdateConfig, vcsRootInstance, genOptions, file, affectedProjectsGroup.getAffectedProjects(), set, hashSet);
        try {
            persistTask = this.myConfigFilesUpdater.scheduleUpdateConfigTask((ConfigGenerator)multiProjectVcsConfigGenerator, (jetbrains.buildServer.serverSide.impl.projects.ConfigUpdateCallback)configUpdateCallback, configFilesUpdateOptions2, configCauseInfo);
            if (persistTask == null) {
                VersionedSettingsUpdaterImpl.$$$reportNull$$$0(63);
            }
        }
        catch (MissingServerResponsibilityException missingServerResponsibilityException) {
            throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
        }
        return persistTask;
    }

    @NotNull
    private String getMetricsProjectId(@NotNull VcsRootInstance vcsRootInstance, Set<SProject> set) {
        String string;
        try {
            if (vcsRootInstance == null) {
                VersionedSettingsUpdaterImpl.$$$reportNull$$$0(64);
            }
        }
        catch (MissingServerResponsibilityException missingServerResponsibilityException) {
            throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
        }
        String string2 = vcsRootInstance.getParent().getProject().getExternalId();
        if (set.size() == 1) {
            string2 = set.iterator().next().getExternalId();
        }
        try {
            string = string2;
            if (string == null) {
                VersionedSettingsUpdaterImpl.$$$reportNull$$$0(65);
            }
        }
        catch (MissingServerResponsibilityException missingServerResponsibilityException) {
            throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
        }
        return string;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void measureExecutionTime(@NotNull String string, @NotNull String string2, @NotNull Callable<Void> callable) throws Exception {
        try {
            if (string == null) {
                VersionedSettingsUpdaterImpl.$$$reportNull$$$0(66);
            }
        }
        catch (Exception exception) {
            throw VersionedSettingsUpdaterImpl.a(exception);
        }
        try {
            if (string2 == null) {
                VersionedSettingsUpdaterImpl.$$$reportNull$$$0(67);
            }
        }
        catch (Exception exception) {
            throw VersionedSettingsUpdaterImpl.a(exception);
        }
        try {
            if (callable == null) {
                VersionedSettingsUpdaterImpl.$$$reportNull$$$0(68);
            }
        }
        catch (Exception exception) {
            throw VersionedSettingsUpdaterImpl.a(exception);
        }
        long l = System.nanoTime();
        try {
            callable.call();
        }
        finally {
            this.recordExecutionTime(string, string2, l);
        }
    }

    private void recordExecutionTime(@NotNull String string, @NotNull String string2, long l) {
        try {
            if (string == null) {
                VersionedSettingsUpdaterImpl.$$$reportNull$$$0(69);
            }
        }
        catch (MissingServerResponsibilityException missingServerResponsibilityException) {
            throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
        }
        try {
            if (string2 == null) {
                VersionedSettingsUpdaterImpl.$$$reportNull$$$0(70);
            }
        }
        catch (MissingServerResponsibilityException missingServerResponsibilityException) {
            throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
        }
        String string3 = string + "_" + string2;
        Long l2 = this.myRecentTimes.put(string3, TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - l));
        try {
            if (l2 == null) {
                this.myServerMetrics.projectMetricBuilder("versionedSettings.apply.time", string, null).description("Calculate times for different stages of VCS project settings applying").experimental(true).dataType(MetricDataType.MILLISECONDS).tags(new String[]{"stage", string2}).buildGauge(() -> ((Long)this.myRecentTimes.get(string3)).longValue());
            }
        }
        catch (MissingServerResponsibilityException missingServerResponsibilityException) {
            throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
        }
    }

    private void removeDirectory(@NotNull File file) {
        try {
            if (file == null) {
                VersionedSettingsUpdaterImpl.$$$reportNull$$$0(71);
            }
        }
        catch (MissingServerResponsibilityException missingServerResponsibilityException) {
            throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
        }
        NamedThreadFactory.executeWithNewThreadName((String)("Removing directory: " + file.getAbsolutePath()), () -> FileUtil.delete((File)file));
    }

    private boolean checkProjectsSettingsWereChangedByConverters(@NotNull String string, @NotNull Set<SProject> set, @NotNull Set<SProject> set2) {
        try {
            if (string == null) {
                VersionedSettingsUpdaterImpl.$$$reportNull$$$0(72);
            }
        }
        catch (MissingServerResponsibilityException missingServerResponsibilityException) {
            throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
        }
        try {
            if (set == null) {
                VersionedSettingsUpdaterImpl.$$$reportNull$$$0(73);
            }
        }
        catch (MissingServerResponsibilityException missingServerResponsibilityException) {
            throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
        }
        try {
            if (set2 == null) {
                VersionedSettingsUpdaterImpl.$$$reportNull$$$0(74);
            }
        }
        catch (MissingServerResponsibilityException missingServerResponsibilityException) {
            throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
        }
        for (SProject sProject : set) {
            if (!this.myConverterChangesStorage.hasConvertedFiles(sProject)) continue;
            String string2 = "Project '" + sProject.getExternalId() + "' or some of its subprojects were modified during TeamCity upgrade. To preserve these changes, skipping settings update to revision " + string;
            LOG.info(string2);
            this.myStatusTracker.setStatusForConfigOwners(set2, string2);
            return true;
        }
        return false;
    }

    /*
     * Loose catch block
     */
    private void handleUpdateError(@NotNull Throwable throwable, @NotNull Collection<SProject> collection, @NotNull String string, @NotNull VcsRootInstance vcsRootInstance, @NotNull ConfigUpdateCallback configUpdateCallback) {
        boolean bl;
        String string2;
        String string3;
        VersionedSettingsUpdaterImpl versionedSettingsUpdaterImpl;
        VersionedSettingsStatus versionedSettingsStatus;
        VersionedSettingsStatus versionedSettingsStatus2;
        block40: {
            Object object;
            List list;
            block41: {
                Object object22222;
                try {
                    if (throwable == null) {
                        VersionedSettingsUpdaterImpl.$$$reportNull$$$0(75);
                    }
                }
                catch (IOException iOException) {
                    throw VersionedSettingsUpdaterImpl.a(iOException);
                }
                try {
                    if (collection == null) {
                        VersionedSettingsUpdaterImpl.$$$reportNull$$$0(76);
                    }
                }
                catch (IOException iOException) {
                    throw VersionedSettingsUpdaterImpl.a(iOException);
                }
                try {
                    if (string == null) {
                        VersionedSettingsUpdaterImpl.$$$reportNull$$$0(77);
                    }
                }
                catch (IOException iOException) {
                    throw VersionedSettingsUpdaterImpl.a(iOException);
                }
                try {
                    if (vcsRootInstance == null) {
                        VersionedSettingsUpdaterImpl.$$$reportNull$$$0(78);
                    }
                }
                catch (IOException iOException) {
                    throw VersionedSettingsUpdaterImpl.a(iOException);
                }
                try {
                    if (configUpdateCallback == null) {
                        VersionedSettingsUpdaterImpl.$$$reportNull$$$0(79);
                    }
                }
                catch (IOException iOException) {
                    throw VersionedSettingsUpdaterImpl.a(iOException);
                }
                if (throwable instanceof ConfigBackupException) {
                    String string4 = "Failed to backup project settings before update from VCS (revision " + string + "): " + throwable.getMessage() + " " + throwable.getCause().getMessage() + ".";
                    this.myStatusTracker.setStatusForConfigOwners(collection, new VersionedSettingsStatus(string4, VersionedSettingsStatus.Type.WARN));
                    return;
                }
                if (!(throwable instanceof ConfigGenerationException)) break block40;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Failed to generate updated settings for revision ").append(string);
                if (throwable instanceof VersionedSettingsException) {
                    stringBuilder.append(": ").append(throwable.getMessage());
                    list = ((VersionedSettingsException)throwable).getErrors();
                    if (!list.isEmpty()) {
                        object = (VersionedSettingsError)list.getFirst();
                        try {
                            stringBuilder.append("\n").append(object.getDescription());
                            if (list.size() > 1) {
                                stringBuilder.append("\nand ").append(StringUtil.withPlural((long)(list.size() - 1), (String)"more error"));
                            }
                        }
                        catch (IOException iOException) {
                            throw VersionedSettingsUpdaterImpl.a(iOException);
                        }
                    }
                    if (LOG.isDebugEnabled()) {
                        stringBuilder.append("\nstackTrace:");
                        for (Object object22222 : ((VersionedSettingsException)throwable).getStacktraceLines()) {
                            stringBuilder.append("\n").append((String)object22222);
                        }
                    }
                    LOG.warn(stringBuilder.toString());
                } else {
                    LOG.warnAndDebugDetails(stringBuilder.toString(), throwable);
                }
                list = new ArrayList();
                if (!(throwable instanceof VersionedSettingsException)) break block41;
                object = (VersionedSettingsException)throwable;
                list = object.getErrors();
                object22222 = new HashSet();
                for (VersionedSettingsError versionedSettingsError : list) {
                    SProject sProject;
                    block39: {
                        block42: {
                            String string5 = versionedSettingsError.getProjectId();
                            sProject = this.myProjectManager.findProjectByExternalId(string5);
                            if (sProject == null) continue;
                            if (!collection.contains(sProject)) break block39;
                            break block42;
                            catch (IOException iOException) {
                                throw VersionedSettingsUpdaterImpl.a(iOException);
                            }
                        }
                        try {
                            block43: {
                                if (!object22222.add(sProject)) break block39;
                                break block43;
                                catch (IOException iOException) {
                                    throw VersionedSettingsUpdaterImpl.a(iOException);
                                }
                            }
                            this.myHealthReport.resetErrors(sProject);
                        }
                        catch (IOException iOException) {
                            throw VersionedSettingsUpdaterImpl.a(iOException);
                        }
                    }
                    String string6 = versionedSettingsError.getLocation();
                    try {
                        if (string6 == null) {
                            this.myHealthReport.reportError(vcsRootInstance, sProject, versionedSettingsError.getSource(), versionedSettingsError.getMessage());
                            continue;
                        }
                    }
                    catch (IOException iOException) {
                        throw VersionedSettingsUpdaterImpl.a(iOException);
                    }
                    this.myHealthReport.reportError(vcsRootInstance, sProject, versionedSettingsError.getSource() + " " + string6, versionedSettingsError.getMessage());
                }
            }
            object = new VersionedSettingsStatus(this.getFailedToApplyMessage(throwable.getMessage(), string, true), VersionedSettingsStatus.Type.WARN);
            object.setConfigErrors(list);
            this.myStatusTracker.setStatusForConfigOwners(collection, (VersionedSettingsStatus)object);
            return;
        }
        if (throwable instanceof ConfigRollbackException) {
            ConfigRollbackException configRollbackException = (ConfigRollbackException)throwable;
            Exception exception = configRollbackException.getRollbackCause();
            ArrayList arrayList = new ArrayList(configRollbackException.getFailedRollbackFiles());
            Collections.sort(arrayList);
            String string7 = "Failed to apply changes from VCS to project settings (revision " + string + "): " + exception.getMessage() + ". Failed to rollback changes in files " + String.valueOf(arrayList) + ", please restore them manually from backup " + String.valueOf(configRollbackException.getBackupDir()) + ".";
            this.myStatusTracker.setStatusForConfigOwners(collection, new VersionedSettingsStatus(string7, VersionedSettingsStatus.Type.WARN));
            return;
        }
        Map map = configUpdateCallback.getConfigErrors();
        try {
            VersionedSettingsStatus versionedSettingsStatus3;
            versionedSettingsStatus2 = versionedSettingsStatus3;
            versionedSettingsStatus = versionedSettingsStatus3;
            versionedSettingsUpdaterImpl = this;
            string3 = throwable.getMessage();
            string2 = string;
            bl = !map.isEmpty();
        }
        catch (IOException iOException) {
            throw VersionedSettingsUpdaterImpl.a(iOException);
        }
        versionedSettingsStatus2(versionedSettingsUpdaterImpl.getFailedToApplyMessage(string3, string2, bl), VersionedSettingsStatus.Type.WARN);
        VersionedSettingsStatus versionedSettingsStatus4 = versionedSettingsStatus;
        try {
            String string8 = this.myServerPaths.getProjectsDir().getCanonicalPath();
            versionedSettingsStatus4.setConfigErrors(VersionedSettingsError.Type.LOADING_ERROR, map, string8);
        }
        catch (IOException iOException) {
            throw new ConfigGenerationException("Project settings directory not found", (Throwable)iOException);
        }
        this.myStatusTracker.setStatusForConfigOwners(collection, versionedSettingsStatus4);
    }

    private void auditProjectSettingsUpdate(@NotNull Collection<SProject> collection, @NotNull VersionedSettingsUpdateConfig versionedSettingsUpdateConfig) {
        try {
            if (collection == null) {
                VersionedSettingsUpdaterImpl.$$$reportNull$$$0(80);
            }
        }
        catch (MissingServerResponsibilityException missingServerResponsibilityException) {
            throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
        }
        try {
            if (versionedSettingsUpdateConfig == null) {
                VersionedSettingsUpdaterImpl.$$$reportNull$$$0(81);
            }
        }
        catch (MissingServerResponsibilityException missingServerResponsibilityException) {
            throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
        }
        if (versionedSettingsUpdateConfig.getUserId() != null) {
            SUser sUser = this.myUserModel.findUserById(versionedSettingsUpdateConfig.getUserId().longValue());
            if (sUser != null) {
                for (SProject sProject : collection) {
                    this.myAuditLogFactory.createForProject(sProject).log(ActionType.LOAD_SETTINGS_FROM_VCS, null, sProject.getName(), sUser, new Object[0]);
                }
            } else {
                LOG.warn("Could not find user account by id: " + versionedSettingsUpdateConfig.getUserId() + ", as a result update from VCS will not be logged in audit");
            }
        }
    }

    @Nullable
    private String checkSkipReason(@NotNull VersionedSettingsUpdateConfig versionedSettingsUpdateConfig, @NotNull VcsRootInstance vcsRootInstance, @Nullable Supplier<String> supplier) {
        String string;
        try {
            if (versionedSettingsUpdateConfig == null) {
                VersionedSettingsUpdaterImpl.$$$reportNull$$$0(82);
            }
        }
        catch (MissingServerResponsibilityException missingServerResponsibilityException) {
            throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
        }
        try {
            if (vcsRootInstance == null) {
                VersionedSettingsUpdaterImpl.$$$reportNull$$$0(83);
            }
        }
        catch (MissingServerResponsibilityException missingServerResponsibilityException) {
            throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
        }
        try {
            string = supplier != null ? supplier.get() : null;
        }
        catch (MissingServerResponsibilityException missingServerResponsibilityException) {
            throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
        }
        String string2 = string;
        try {
            if (string2 != null) {
                LOG.info("Skip updating settings to revision " + versionedSettingsUpdateConfig.getTargetRevision() + " in settings root " + LogUtil.describe((Loggable)vcsRootInstance) + ", reason: " + string2);
                return string2;
            }
        }
        catch (MissingServerResponsibilityException missingServerResponsibilityException) {
            throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
        }
        return null;
    }

    private void configureRelativeIdProjects(@NotNull VersionedSettingsUpdateConfig versionedSettingsUpdateConfig, @NotNull VcsRootInstance vcsRootInstance, @NotNull GenOptions genOptions, @NotNull Set<SProject> set) {
        String string;
        SProject sProject;
        VersionedSettingsConfig versionedSettingsConfig;
        SProject sProject22;
        try {
            if (versionedSettingsUpdateConfig == null) {
                VersionedSettingsUpdaterImpl.$$$reportNull$$$0(84);
            }
        }
        catch (MissingServerResponsibilityException missingServerResponsibilityException) {
            throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
        }
        try {
            if (vcsRootInstance == null) {
                VersionedSettingsUpdaterImpl.$$$reportNull$$$0(85);
            }
        }
        catch (MissingServerResponsibilityException missingServerResponsibilityException) {
            throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
        }
        try {
            if (genOptions == null) {
                VersionedSettingsUpdaterImpl.$$$reportNull$$$0(86);
            }
        }
        catch (MissingServerResponsibilityException missingServerResponsibilityException) {
            throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
        }
        try {
            if (set == null) {
                VersionedSettingsUpdaterImpl.$$$reportNull$$$0(87);
            }
        }
        catch (MissingServerResponsibilityException missingServerResponsibilityException) {
            throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
        }
        SProject sProject3 = vcsRootInstance.getParent().getProject();
        for (SProject sProject22 : set) {
            String string2;
            versionedSettingsConfig = this.myVersionedSettingsManager.readConfig(sProject22);
            try {
                if (!versionedSettingsConfig.isEnabled() || !versionedSettingsConfig.isUseRelativeIds()) continue;
            }
            catch (MissingServerResponsibilityException missingServerResponsibilityException) {
                throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
            }
            sProject = sProject22.getParentProject();
            try {
                string2 = sProject != null ? sProject.getExternalId() : "";
            }
            catch (MissingServerResponsibilityException missingServerResponsibilityException) {
                throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
            }
            string = string2;
            genOptions.addRelativeIdProject(string, sProject22.getExternalId(), sProject22.getName(), VersionedSettingsConfigStorageImpl.getVersionedSettingsFeatureParams((SProject)sProject22), VersionedSettingsConfigStorageImpl.getContextParameters((SProject)sProject22));
            genOptions.setSettingsRoot(this.myProjectsBuilderFactory.getRootBuilder(vcsRootInstance.getParent()), sProject3.equals((Object)sProject22));
        }
        String string3 = versionedSettingsUpdateConfig.getImportedProjectExtId();
        sProject22 = versionedSettingsUpdateConfig.getImportedProjectVcsSettings();
        if (sProject22 != null && (versionedSettingsConfig = this.myProjectManager.findProjectByExternalId(string3)) != null) {
            String string4;
            sProject = versionedSettingsConfig.getParentProject();
            try {
                string4 = sProject != null ? sProject.getExternalId() : "";
            }
            catch (MissingServerResponsibilityException missingServerResponsibilityException) {
                throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
            }
            string = string4;
            genOptions.addRelativeIdProject(string, versionedSettingsConfig.getExternalId(), versionedSettingsConfig.getName(), VersionedSettingsConfigStorageImpl.getVersionedSettingsFeatureParams((VersionedSettingsConfig)sProject22), sProject22.getDslContextParameters());
            genOptions.setSettingsRoot(this.myProjectsBuilderFactory.getRootBuilder(vcsRootInstance.getParent()), sProject3.equals((Object)versionedSettingsConfig));
        }
    }

    private void configureUuidChecker(@NotNull Set<SProject> set, @NotNull ConfigFilesUpdateOptions configFilesUpdateOptions) {
        try {
            if (set == null) {
                VersionedSettingsUpdaterImpl.$$$reportNull$$$0(88);
            }
        }
        catch (MissingServerResponsibilityException missingServerResponsibilityException) {
            throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
        }
        try {
            if (configFilesUpdateOptions == null) {
                VersionedSettingsUpdaterImpl.$$$reportNull$$$0(89);
            }
        }
        catch (MissingServerResponsibilityException missingServerResponsibilityException) {
            throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
        }
        if (this.myOptions.isCheckAllowedUuids()) {
            UuidCheckerImpl uuidCheckerImpl = new UuidCheckerImpl();
            set.stream().flatMap(sProject -> sProject.getOwnBuildTypes().stream()).map(SPersistentEntity::getConfigId).forEach(arg_0 -> ((UuidCheckerImpl)uuidCheckerImpl).addAllowedBuildTypeUuid(arg_0));
            set.stream().flatMap(sProject -> sProject.getOwnBuildTypeTemplates().stream()).map(SPersistentEntity::getConfigId).forEach(arg_0 -> ((UuidCheckerImpl)uuidCheckerImpl).addAllowedBuildTypeUuid(arg_0));
            set.stream().flatMap(sProject -> sProject.getOwnVcsRoots().stream()).map(SPersistentEntity::getConfigId).forEach(arg_0 -> ((UuidCheckerImpl)uuidCheckerImpl).addAllowedVcsRootUuid(arg_0));
            set.stream().map(SPersistentEntity::getConfigId).forEach(arg_0 -> ((UuidCheckerImpl)uuidCheckerImpl).addAllowedProjectUuid(arg_0));
            HashSet hashSet = new HashSet();
            set.stream().map(BuildProject::getProjectId).forEach(hashSet::add);
            this.myDeletedEntityTracker.consumeDeletedProjectUuids(hashSet, entityId -> {
                uuidCheckerImpl.addAllowedProjectUuid(entityId.getConfigId());
                hashSet.add((String)entityId.getInternalId());
            });
            this.myDeletedEntityTracker.consumeDeletedBuildUuids(hashSet, arg_0 -> ((UuidCheckerImpl)uuidCheckerImpl).addAllowedBuildTypeUuid(arg_0));
            this.myDeletedEntityTracker.consumeDeletedVcsRootUuids(hashSet, arg_0 -> ((UuidCheckerImpl)uuidCheckerImpl).addAllowedVcsRootUuid(arg_0));
            configFilesUpdateOptions.setUuidChecker((UuidChecker)uuidCheckerImpl);
        }
    }

    private boolean hasErrors(@NotNull VcsRootInstance vcsRootInstance, @NotNull Collection<SProject> collection) {
        try {
            if (vcsRootInstance == null) {
                VersionedSettingsUpdaterImpl.$$$reportNull$$$0(90);
            }
        }
        catch (Exception exception) {
            throw VersionedSettingsUpdaterImpl.a(exception);
        }
        try {
            if (collection == null) {
                VersionedSettingsUpdaterImpl.$$$reportNull$$$0(91);
            }
        }
        catch (Exception exception) {
            throw VersionedSettingsUpdaterImpl.a(exception);
        }
        try {
            if (this.myHealthReport.hasErrors(vcsRootInstance)) {
                return true;
            }
        }
        catch (Exception exception) {
            throw VersionedSettingsUpdaterImpl.a(exception);
        }
        List list = this.myProjectManagerErrors.getBrokenConfigFiles();
        try {
            if (list.isEmpty()) {
                return false;
            }
        }
        catch (Exception exception) {
            throw VersionedSettingsUpdaterImpl.a(exception);
        }
        try {
            Set set = list.stream().map(file -> {
                try {
                    return file.getCanonicalPath();
                }
                catch (IOException iOException) {
                    throw new UncheckedIOException(iOException);
                }
            }).collect(Collectors.toSet());
            for (SProject sProject : collection) {
                String string = sProject.getConfigDirectory().getCanonicalPath();
                for (String string2 : set) {
                    if (!string2.startsWith(string)) continue;
                    return true;
                }
            }
            return false;
        }
        catch (Exception exception) {
            return true;
        }
    }

    private String getFailedToApplyMessage(@NotNull String string, @NotNull String string2, boolean bl) {
        try {
            if (string == null) {
                VersionedSettingsUpdaterImpl.$$$reportNull$$$0(92);
            }
        }
        catch (MissingServerResponsibilityException missingServerResponsibilityException) {
            throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
        }
        try {
            if (string2 == null) {
                VersionedSettingsUpdaterImpl.$$$reportNull$$$0(93);
            }
        }
        catch (MissingServerResponsibilityException missingServerResponsibilityException) {
            throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
        }
        StringBuilder stringBuilder = new StringBuilder();
        try {
            stringBuilder.append("Failed to apply changes from VCS to project settings (revision ").append(string2).append("): ").append(string);
            if (bl) {
                stringBuilder.append(". Please fix the errors in the script and make a new commit.");
            }
        }
        catch (MissingServerResponsibilityException missingServerResponsibilityException) {
            throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
        }
        return stringBuilder.toString();
    }

    @NotNull
    private Set<SProject> getAffectedProjects(@NotNull VersionedSettingsUpdateConfig versionedSettingsUpdateConfig, @NotNull VcsRootInstance vcsRootInstance) {
        HashSet<SProject> hashSet;
        try {
            if (versionedSettingsUpdateConfig == null) {
                VersionedSettingsUpdaterImpl.$$$reportNull$$$0(94);
            }
        }
        catch (MissingServerResponsibilityException missingServerResponsibilityException) {
            throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
        }
        try {
            if (vcsRootInstance == null) {
                VersionedSettingsUpdaterImpl.$$$reportNull$$$0(95);
            }
        }
        catch (MissingServerResponsibilityException missingServerResponsibilityException) {
            throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
        }
        Set<Object> set = new HashSet<SProject>();
        set.addAll(this.myVersionedSettingsManager.getProjectsBySettingsRootInstance(vcsRootInstance));
        if (versionedSettingsUpdateConfig.isUpdateOnlySpecifiedProjects()) {
            Set set2;
            block14: {
                HashSet<SProject> hashSet2;
                block15: {
                    set2 = versionedSettingsUpdateConfig.getProjectsExtIdsToUpdate();
                    try {
                        try {
                            if (set2 != null) break block14;
                            hashSet2 = set;
                            if (hashSet2 != null) break block15;
                        }
                        catch (MissingServerResponsibilityException missingServerResponsibilityException) {
                            throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
                        }
                        VersionedSettingsUpdaterImpl.$$$reportNull$$$0(96);
                    }
                    catch (MissingServerResponsibilityException missingServerResponsibilityException) {
                        throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
                    }
                }
                return hashSet2;
            }
            set = set.stream().filter(sProject -> set2.contains(sProject.getExternalId())).collect(Collectors.toSet());
        }
        try {
            hashSet = set;
            if (hashSet == null) {
                VersionedSettingsUpdaterImpl.$$$reportNull$$$0(97);
            }
        }
        catch (MissingServerResponsibilityException missingServerResponsibilityException) {
            throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
        }
        return hashSet;
    }

    @NotNull
    private Set<SProject> getImportedProjects(@Nullable String string) {
        HashSet<SProject> hashSet;
        HashSet<SProject> hashSet2 = new HashSet<SProject>();
        if (string != null) {
            SProject sProject = this.myProjectManager.findProjectByExternalId(string);
            try {
                if (sProject != null) {
                    hashSet2.add(sProject);
                    hashSet2.addAll(sProject.getProjects());
                }
            }
            catch (MissingServerResponsibilityException missingServerResponsibilityException) {
                throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
            }
        }
        try {
            hashSet = hashSet2;
            if (hashSet == null) {
                VersionedSettingsUpdaterImpl.$$$reportNull$$$0(98);
            }
        }
        catch (MissingServerResponsibilityException missingServerResponsibilityException) {
            throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
        }
        return hashSet;
    }

    @NotNull
    private Set<AffectedProjectsGroup> getAffectedProjectsGroups(@NotNull VersionedSettingsUpdateConfig versionedSettingsUpdateConfig) {
        Object object;
        Object object2;
        ProjectSettingsGenerator projectSettingsGenerator;
        try {
            if (versionedSettingsUpdateConfig == null) {
                VersionedSettingsUpdaterImpl.$$$reportNull$$$0(99);
            }
        }
        catch (MissingServerResponsibilityException missingServerResponsibilityException) {
            throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
        }
        VcsRootInstance vcsRootInstance = this.getVcsRootInstance(versionedSettingsUpdateConfig);
        Set<SProject> set = this.getAffectedProjects(versionedSettingsUpdateConfig, vcsRootInstance);
        Set<SProject> set2 = this.getImportedProjects(versionedSettingsUpdateConfig.getImportedProjectExtId());
        HashSet<SProject> hashSet = new HashSet<SProject>(set);
        hashSet.addAll(set2);
        HashMap<SProject, VersionedSettingsConfig> hashMap = new HashMap<SProject, VersionedSettingsConfig>();
        this.myVersionedSettingsManager.visitProjectsInSettingsRootInstance(vcsRootInstance, (sProject, versionedSettingsConfig) -> hashMap.put(sProject, (VersionedSettingsConfig)versionedSettingsConfig));
        String string = null;
        VersionedSettingsConfig versionedSettingsConfig2 = versionedSettingsUpdateConfig.getImportedProjectVcsSettings();
        if (versionedSettingsConfig2 != null) {
            projectSettingsGenerator = this.myVersionedSettingsManager.findGenerator(versionedSettingsConfig2.getFormat());
            if (projectSettingsGenerator != null) {
                string = projectSettingsGenerator.getSettingsPathsProvider().getProjectSettingsPaths(versionedSettingsConfig2.getSettingsPath()).getSettingApplyCheckoutRules();
            }
            for (SProject object3 : set2) {
                hashMap.put(object3, versionedSettingsConfig2);
            }
        }
        projectSettingsGenerator = new HashMap();
        for (SProject sProject2 : hashSet) {
            VersionedSettingsConfig.DslExecutionMode dslExecutionMode;
            block14: {
                block13: {
                    dslExecutionMode = VersionedSettingsUtil.getEffectiveExecutionMode((VersionedSettingsConfig)((VersionedSettingsConfig)hashMap.get(sProject2)), (VersionedSettingsOptions)this.myOptions);
                    try {
                        if (string == null || !set2.contains(sProject2)) break block13;
                    }
                    catch (MissingServerResponsibilityException missingServerResponsibilityException) {
                        throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
                    }
                    object2 = string;
                    break block14;
                }
                object2 = this.myVersionedSettingsManager.getEffectiveProjectSettingsPaths(sProject2).getSettingApplyCheckoutRules();
            }
            projectSettingsGenerator.computeIfAbsent(Pair.create((Object)dslExecutionMode, (Object)object2), pair -> new HashSet()).add(sProject2);
        }
        HashSet hashSet2 = new HashSet();
        for (Map.Entry entry : projectSettingsGenerator.entrySet()) {
            object2 = new HashSet((Collection)entry.getValue());
            object2.retainAll(set);
            hashSet2.add(new AffectedProjectsGroup((String)((Pair)entry.getKey()).second, (Set)object2, (Set)entry.getValue(), (VersionedSettingsConfig.DslExecutionMode)((Pair)entry.getKey()).first));
        }
        try {
            object = hashSet2;
            if (object == null) {
                VersionedSettingsUpdaterImpl.$$$reportNull$$$0(100);
            }
        }
        catch (MissingServerResponsibilityException missingServerResponsibilityException) {
            throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
        }
        return object;
    }

    @NotNull
    private Collection<ProjectSettingsGenerator> getProjectSettingsGenerators(@NotNull VersionedSettingsUpdateConfig versionedSettingsUpdateConfig, @NotNull Set<SProject> set) {
        ArrayList<ProjectSettingsGenerator> arrayList;
        SProject sProject2;
        try {
            if (versionedSettingsUpdateConfig == null) {
                VersionedSettingsUpdaterImpl.$$$reportNull$$$0(101);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsUpdaterImpl.a(illegalArgumentException);
        }
        try {
            if (set == null) {
                VersionedSettingsUpdaterImpl.$$$reportNull$$$0(102);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsUpdaterImpl.a(illegalArgumentException);
        }
        String string = "xml";
        HashSet<Object> hashSet = new HashSet<Object>();
        ArrayList<ProjectSettingsGenerator> arrayList2 = new ArrayList<ProjectSettingsGenerator>();
        for (SProject sProject2 : set) {
            ProjectSettingsGenerator projectSettingsGenerator3;
            try {
                projectSettingsGenerator3 = this.myVersionedSettingsManager.getEffectiveGenerator(sProject2);
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw new ConfigGenerationException("Generator not found");
            }
            try {
                if (!hashSet.add(projectSettingsGenerator3)) continue;
                arrayList2.add(projectSettingsGenerator3);
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw VersionedSettingsUpdaterImpl.a(illegalArgumentException);
            }
        }
        String string2 = versionedSettingsUpdateConfig.getSettingsFormat();
        if (string2 != null) {
            sProject2 = this.myProjectSettingsGeneratorRegistry.findGenerator(string2);
            try {
                if (sProject2 == null) {
                    throw new ConfigGenerationException("Generator not found: '" + (String)string2 + "'");
                }
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw VersionedSettingsUpdaterImpl.a(illegalArgumentException);
            }
            try {
                if (hashSet.add(sProject2)) {
                    arrayList2.add((ProjectSettingsGenerator)sProject2);
                }
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw VersionedSettingsUpdaterImpl.a(illegalArgumentException);
            }
        }
        if (versionedSettingsUpdateConfig.getImportedProjectExtId() != null) {
            sProject2 = this.myProjectSettingsGeneratorRegistry.findGenerator(string);
            try {
                if (sProject2 == null) {
                    throw new ConfigGenerationException("Generator not found: '" + string + "'");
                }
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw VersionedSettingsUpdaterImpl.a(illegalArgumentException);
            }
            try {
                if (hashSet.add(sProject2)) {
                    arrayList2.add((ProjectSettingsGenerator)sProject2);
                }
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw VersionedSettingsUpdaterImpl.a(illegalArgumentException);
            }
        }
        try {
            arrayList2.sort((projectSettingsGenerator, projectSettingsGenerator2) -> {
                block9: {
                    block8: {
                        try {
                            try {
                                if (!string.equals(projectSettingsGenerator.getFormat()) || string.equals(projectSettingsGenerator2.getFormat())) break block8;
                            }
                            catch (MissingServerResponsibilityException missingServerResponsibilityException) {
                                throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
                            }
                            return -1;
                        }
                        catch (MissingServerResponsibilityException missingServerResponsibilityException) {
                            throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
                        }
                    }
                    try {
                        try {
                            if (!string.equals(projectSettingsGenerator2.getFormat()) || string.equals(projectSettingsGenerator.getFormat())) break block9;
                        }
                        catch (MissingServerResponsibilityException missingServerResponsibilityException) {
                            throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
                        }
                        return 1;
                    }
                    catch (MissingServerResponsibilityException missingServerResponsibilityException) {
                        throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
                    }
                }
                return projectSettingsGenerator.getFormat().compareTo(projectSettingsGenerator2.getFormat());
            });
            arrayList = arrayList2;
            if (arrayList == null) {
                VersionedSettingsUpdaterImpl.$$$reportNull$$$0(103);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsUpdaterImpl.a(illegalArgumentException);
        }
        return arrayList;
    }

    private void updateStatus(@NotNull Collection<SProject> collection, @NotNull String string, @Nullable SVcsModification sVcsModification, long l) {
        block11: {
            String string2;
            block10: {
                Object object;
                try {
                    if (collection == null) {
                        VersionedSettingsUpdaterImpl.$$$reportNull$$$0(104);
                    }
                }
                catch (MissingServerResponsibilityException missingServerResponsibilityException) {
                    throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
                }
                try {
                    if (string == null) {
                        VersionedSettingsUpdaterImpl.$$$reportNull$$$0(105);
                    }
                }
                catch (MissingServerResponsibilityException missingServerResponsibilityException) {
                    throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
                }
                long l2 = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - l);
                try {
                    object = l2 > 0L ? ", time spent: " + TimePrinter.formatMillis((long)l2) : "";
                }
                catch (MissingServerResponsibilityException missingServerResponsibilityException) {
                    throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
                }
                string2 = object;
                try {
                    if (sVcsModification == null) break block10;
                    this.myStatusTracker.setStatusForConfigOwners(collection, "Changes from VCS are applied to project settings, last change '" + sVcsModification.getDescription().trim() + "', revision " + string + string2);
                    break block11;
                }
                catch (MissingServerResponsibilityException missingServerResponsibilityException) {
                    throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
                }
            }
            this.myStatusTracker.setStatusForConfigOwners(collection, "Changes from VCS are applied to project settings, revision " + string + string2);
        }
    }

    @Nullable
    private String getLastTeamCityCommit(@NotNull VcsRootInstance vcsRootInstance) {
        try {
            if (vcsRootInstance == null) {
                VersionedSettingsUpdaterImpl.$$$reportNull$$$0(106);
            }
        }
        catch (MissingServerResponsibilityException missingServerResponsibilityException) {
            throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
        }
        List list = this.myTeamCityCommits.get(vcsRootInstance);
        try {
            if (list.isEmpty()) {
                return null;
            }
        }
        catch (MissingServerResponsibilityException missingServerResponsibilityException) {
            throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
        }
        return ((TeamCityCommits.CommitDetails)list.getLast()).getCommitRevision();
    }

    public boolean isSettingsUpdateInProgress(@NotNull String string) {
        try {
            if (string == null) {
                VersionedSettingsUpdaterImpl.$$$reportNull$$$0(107);
            }
        }
        catch (MissingServerResponsibilityException missingServerResponsibilityException) {
            throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
        }
        Boolean bl = (Boolean)this.myUpdatingProjectExtIds.get(string);
        try {
            if (bl == null) {
                return false;
            }
        }
        catch (MissingServerResponsibilityException missingServerResponsibilityException) {
            throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
        }
        return bl;
    }

    public void runAsUpdateFromVcs(@NotNull String string, @NotNull Runnable runnable) {
        try {
            if (string == null) {
                VersionedSettingsUpdaterImpl.$$$reportNull$$$0(108);
            }
        }
        catch (MissingServerResponsibilityException missingServerResponsibilityException) {
            throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
        }
        try {
            if (runnable == null) {
                VersionedSettingsUpdaterImpl.$$$reportNull$$$0(109);
            }
        }
        catch (MissingServerResponsibilityException missingServerResponsibilityException) {
            throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
        }
        this.myUpdatingProjectExtIds.put(string, true);
        try {
            runnable.run();
        }
        finally {
            this.myUpdatingProjectExtIds.remove(string);
        }
    }

    @NotNull
    static String describeProjectIds(@NotNull Collection<SProject> collection) {
        String string;
        try {
            if (collection == null) {
                VersionedSettingsUpdaterImpl.$$$reportNull$$$0(110);
            }
        }
        catch (MissingServerResponsibilityException missingServerResponsibilityException) {
            throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
        }
        int n = 10;
        Object object = collection.stream().map(SPersistentEntity::getExternalId).sorted().limit(n).collect(Collectors.joining(", "));
        if (collection.size() > n) {
            int n2 = collection.size() - n;
            object = (String)object + ", and " + n2 + " more " + StringUtil.pluralize((String)"project", (int)n2);
        }
        try {
            string = "[" + (String)object + "]";
            if (string == null) {
                VersionedSettingsUpdaterImpl.$$$reportNull$$$0(111);
            }
        }
        catch (MissingServerResponsibilityException missingServerResponsibilityException) {
            throw VersionedSettingsUpdaterImpl.a(missingServerResponsibilityException);
        }
        return string;
    }

    private /* synthetic */ void lambda$scheduleUpdateFromVcs$3(VersionedSettingsUpdateConfig versionedSettingsUpdateConfig, AffectedProjectsGroup affectedProjectsGroup, Supplier supplier) {
        this.runUpdateFromVcs(versionedSettingsUpdateConfig, affectedProjectsGroup, supplier, false);
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
