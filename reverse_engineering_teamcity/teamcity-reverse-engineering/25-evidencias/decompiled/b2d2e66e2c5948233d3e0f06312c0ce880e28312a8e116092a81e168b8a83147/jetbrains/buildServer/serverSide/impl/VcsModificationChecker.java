/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.annotations.VisibleForTesting
 *  com.intellij.openapi.diagnostic.Logger
 *  jetbrains.buildServer.Used
 *  jetbrains.buildServer.log.Loggable
 *  jetbrains.buildServer.log.Loggers
 *  jetbrains.buildServer.metrics.Counter
 *  jetbrains.buildServer.metrics.MetricDataType
 *  jetbrains.buildServer.metrics.ServerLoadMetrics
 *  jetbrains.buildServer.metrics.ServerMetrics
 *  jetbrains.buildServer.metrics.Stoppable
 *  jetbrains.buildServer.serverSide.BuildServerListener
 *  jetbrains.buildServer.serverSide.BuildServerState
 *  jetbrains.buildServer.serverSide.CurrentNodeInfo
 *  jetbrains.buildServer.serverSide.MultiNodeTasks
 *  jetbrains.buildServer.serverSide.MultiNodeTasks$SubmittedTask
 *  jetbrains.buildServer.serverSide.MultiNodeTasks$Task
 *  jetbrains.buildServer.serverSide.MultiNodeTasks$TaskConsumer
 *  jetbrains.buildServer.serverSide.MultiNodeTasks$TaskData
 *  jetbrains.buildServer.serverSide.MultiNodesEvents
 *  jetbrains.buildServer.serverSide.NodeResponsibility
 *  jetbrains.buildServer.serverSide.ProjectManager
 *  jetbrains.buildServer.serverSide.SBuildType
 *  jetbrains.buildServer.serverSide.SProject
 *  jetbrains.buildServer.serverSide.TeamCityNode
 *  jetbrains.buildServer.serverSide.TeamCityNodes
 *  jetbrains.buildServer.serverSide.TeamCityProperties
 *  jetbrains.buildServer.serverSide.executors.VcsExecutionServices
 *  jetbrains.buildServer.serverSide.impl.CheckingForChangesScheduler
 *  jetbrains.buildServer.serverSide.impl.DependencyParametersCalculationContext
 *  jetbrains.buildServer.serverSide.impl.LogUtil
 *  jetbrains.buildServer.serverSide.impl.PeriodicalCheckParameters
 *  jetbrains.buildServer.serverSide.impl.SettingsRootInfo
 *  jetbrains.buildServer.serverSide.impl.VcsModificationChecker$CollectChangesAction
 *  jetbrains.buildServer.serverSide.impl.VcsModificationChecker$ExplicitCheckInfo
 *  jetbrains.buildServer.serverSide.impl.VcsModificationChecker$ProjectSettingsRootInfo
 *  jetbrains.buildServer.serverSide.versionedSettings.VersionedSettingsManager
 *  jetbrains.buildServer.util.EventDispatcher
 *  jetbrains.buildServer.util.ExceptionUtil
 *  jetbrains.buildServer.util.NamedDaemonThreadFactory
 *  jetbrains.buildServer.util.StringUtil
 *  jetbrains.buildServer.util.SystemTimeService
 *  jetbrains.buildServer.util.TimeIntervalAction
 *  jetbrains.buildServer.util.TimeService
 *  jetbrains.buildServer.util.WatchDog
 *  jetbrains.buildServer.vcs.ChangesCheckingService
 *  jetbrains.buildServer.vcs.CheckoutRules
 *  jetbrains.buildServer.vcs.OperationRequestor
 *  jetbrains.buildServer.vcs.RecurringChangesLoader
 *  jetbrains.buildServer.vcs.SVcsRoot
 *  jetbrains.buildServer.vcs.VcsChangesLoaderMetrics
 *  jetbrains.buildServer.vcs.VcsModificationsStorage
 *  jetbrains.buildServer.vcs.VcsRootInstance
 *  jetbrains.buildServer.vcs.VcsRootInstanceEntry
 *  jetbrains.buildServer.vcs.VcsRootInstanceEx
 *  jetbrains.buildServer.vcs.VcsRootInstancesManager
 *  jetbrains.buildServer.vcs.VcsRootStatus$Type
 *  jetbrains.buildServer.vcs.impl.CheckingForChangesTaskPrecondition
 *  jetbrains.buildServer.vcs.spec.BranchSpecs
 *  org.apache.commons.codec.digest.DigestUtils
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package jetbrains.buildServer.serverSide.impl;

import com.google.common.annotations.VisibleForTesting;
import com.intellij.openapi.diagnostic.Logger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.EventListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import jetbrains.buildServer.Used;
import jetbrains.buildServer.log.Loggable;
import jetbrains.buildServer.log.Loggers;
import jetbrains.buildServer.metrics.Counter;
import jetbrains.buildServer.metrics.MetricDataType;
import jetbrains.buildServer.metrics.ServerLoadMetrics;
import jetbrains.buildServer.metrics.ServerMetrics;
import jetbrains.buildServer.metrics.Stoppable;
import jetbrains.buildServer.serverSide.BuildServerListener;
import jetbrains.buildServer.serverSide.BuildServerState;
import jetbrains.buildServer.serverSide.CurrentNodeInfo;
import jetbrains.buildServer.serverSide.MultiNodeTasks;
import jetbrains.buildServer.serverSide.MultiNodesEvents;
import jetbrains.buildServer.serverSide.NodeResponsibility;
import jetbrains.buildServer.serverSide.ProjectManager;
import jetbrains.buildServer.serverSide.SBuildType;
import jetbrains.buildServer.serverSide.SProject;
import jetbrains.buildServer.serverSide.TeamCityNode;
import jetbrains.buildServer.serverSide.TeamCityNodes;
import jetbrains.buildServer.serverSide.TeamCityProperties;
import jetbrains.buildServer.serverSide.executors.VcsExecutionServices;
import jetbrains.buildServer.serverSide.impl.CheckingForChangesScheduler;
import jetbrains.buildServer.serverSide.impl.DependencyParametersCalculationContext;
import jetbrains.buildServer.serverSide.impl.LogUtil;
import jetbrains.buildServer.serverSide.impl.PeriodicalCheckParameters;
import jetbrains.buildServer.serverSide.impl.SettingsRootInfo;
import jetbrains.buildServer.serverSide.impl.VcsModificationChecker;
import jetbrains.buildServer.serverSide.versionedSettings.VersionedSettingsManager;
import jetbrains.buildServer.util.EventDispatcher;
import jetbrains.buildServer.util.ExceptionUtil;
import jetbrains.buildServer.util.NamedDaemonThreadFactory;
import jetbrains.buildServer.util.StringUtil;
import jetbrains.buildServer.util.SystemTimeService;
import jetbrains.buildServer.util.TimeIntervalAction;
import jetbrains.buildServer.util.TimeService;
import jetbrains.buildServer.util.WatchDog;
import jetbrains.buildServer.vcs.ChangesCheckingService;
import jetbrains.buildServer.vcs.CheckoutRules;
import jetbrains.buildServer.vcs.OperationRequestor;
import jetbrains.buildServer.vcs.RecurringChangesLoader;
import jetbrains.buildServer.vcs.SVcsRoot;
import jetbrains.buildServer.vcs.VcsChangesLoaderMetrics;
import jetbrains.buildServer.vcs.VcsModificationsStorage;
import jetbrains.buildServer.vcs.VcsRootInstance;
import jetbrains.buildServer.vcs.VcsRootInstanceEntry;
import jetbrains.buildServer.vcs.VcsRootInstanceEx;
import jetbrains.buildServer.vcs.VcsRootInstancesManager;
import jetbrains.buildServer.vcs.VcsRootStatus;
import jetbrains.buildServer.vcs.impl.CheckingForChangesTaskPrecondition;
import jetbrains.buildServer.vcs.spec.BranchSpecs;
import org.apache.commons.codec.digest.DigestUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Exception performing whole class analysis ignored.
 */
public class VcsModificationChecker
implements ChangesCheckingService {
    public static final Logger LOG;
    private static final Logger LOG_DIAG;
    public static final String TEAMCITY_VCS_MONITOR_CHECKING_FOR_CHANGES_DELAY = "teamcity.vcsMonitor.checkingForChangesPeriod";
    public static final String PERIODICAL_CHECK_TASK_RECREATION_DELAY_PROP = "teamcity.vcsMonitor.recreatePeriodicalTaskDelay";
    public static final String VCS_PERIODICAL_CHECKING_FOR_CHANGES_PROP = "teamcity.vcsMonitor.enabled";
    private final EventDispatcher<BuildServerListener> myMulticaster;
    private final VersionedSettingsManager mySettingsRootManager;
    private final MultiNodeTasks myNodeTasks;
    private final MultiNodesEvents myNodesEvents;
    private final TimeIntervalAction myPeriodicalVcsCheckAction;
    private final VcsRootInstancesManager myVcsRootInstanceManager;
    private final BranchSpecs myBranchSpecs;
    private final CheckingForChangesTaskPrecondition myCheckingForChangesTaskPrecondition;
    private final TeamCityNodes myTeamCityNodes;
    private final VcsChangesLoaderMetrics myVcsChangesLoaderMetrics;
    private final ServerMetrics myServerMetrics;
    private VcsExecutionServices myExecutorServices;
    private BuildServerState myServer;
    private VcsModificationsStorage myVcsModificationsStorage;
    private RecurringChangesLoader myVcsChangesLoader;
    private ProjectManager myProjectManager;
    private final AtomicInteger myActiveVcsRootInstances;
    private final Object myLock;
    private final CheckingForChangesScheduler myCheckingForChangesScheduler;
    private final Map<Long, Object> myInProgress;
    private final Map<VcsRootInstance, PeriodicalCheckParameters> myVcsRootsToCheck;
    private final Map<VcsRootInstance, OperationRequestor> myMissedChecks;
    private final Map<VcsRootInstance, ExplicitCheckInfo> myChecksFromTasks;
    private final Map<OperationRequestor, Map<String, Counter>> myOperationsDelay;
    private final Thread myVcsMonitorThread;
    private final AtomicBoolean myVcsMonitorThreadStopped;
    private volatile TimeService myTimeService;
    static final /* synthetic */ boolean $assertionsDisabled;

    public VcsModificationChecker(@NotNull EventDispatcher<BuildServerListener> eventDispatcher, @NotNull VersionedSettingsManager versionedSettingsManager, @NotNull BranchSpecs branchSpecs, @NotNull CheckingForChangesScheduler checkingForChangesScheduler, @NotNull MultiNodeTasks multiNodeTasks, @NotNull VcsChangesLoaderMetrics vcsChangesLoaderMetrics, @NotNull MultiNodesEvents multiNodesEvents, @NotNull TeamCityNodes teamCityNodes, @NotNull CheckingForChangesTaskPrecondition checkingForChangesTaskPrecondition, @NotNull VcsRootInstancesManager vcsRootInstancesManager, @NotNull ServerMetrics serverMetrics) {
        if (eventDispatcher == null) {
            VcsModificationChecker.$$$reportNull$$$0(0);
        }
        if (versionedSettingsManager == null) {
            VcsModificationChecker.$$$reportNull$$$0(1);
        }
        if (branchSpecs == null) {
            VcsModificationChecker.$$$reportNull$$$0(2);
        }
        if (checkingForChangesScheduler == null) {
            VcsModificationChecker.$$$reportNull$$$0(3);
        }
        if (multiNodeTasks == null) {
            VcsModificationChecker.$$$reportNull$$$0(4);
        }
        if (vcsChangesLoaderMetrics == null) {
            VcsModificationChecker.$$$reportNull$$$0(5);
        }
        if (multiNodesEvents == null) {
            VcsModificationChecker.$$$reportNull$$$0(6);
        }
        if (teamCityNodes == null) {
            VcsModificationChecker.$$$reportNull$$$0(7);
        }
        if (checkingForChangesTaskPrecondition == null) {
            VcsModificationChecker.$$$reportNull$$$0(8);
        }
        if (vcsRootInstancesManager == null) {
            VcsModificationChecker.$$$reportNull$$$0(9);
        }
        if (serverMetrics == null) {
            VcsModificationChecker.$$$reportNull$$$0(10);
        }
        this.myActiveVcsRootInstances = new AtomicInteger();
        this.myLock = new Object();
        this.myInProgress = new HashMap<Long, Object>();
        this.myVcsRootsToCheck = new HashMap<VcsRootInstance, PeriodicalCheckParameters>();
        this.myMissedChecks = new ConcurrentHashMap<VcsRootInstance, OperationRequestor>();
        this.myChecksFromTasks = new ConcurrentHashMap<VcsRootInstance, ExplicitCheckInfo>();
        this.myOperationsDelay = new HashMap<OperationRequestor, Map<String, Counter>>();
        this.myVcsMonitorThreadStopped = new AtomicBoolean();
        this.myTimeService = new SystemTimeService();
        this.myMulticaster = eventDispatcher;
        this.mySettingsRootManager = versionedSettingsManager;
        this.myBranchSpecs = branchSpecs;
        this.myCheckingForChangesScheduler = checkingForChangesScheduler;
        this.myNodeTasks = multiNodeTasks;
        this.myNodesEvents = multiNodesEvents;
        this.myVcsRootInstanceManager = vcsRootInstancesManager;
        this.myCheckingForChangesTaskPrecondition = checkingForChangesTaskPrecondition;
        this.myTeamCityNodes = teamCityNodes;
        this.myVcsChangesLoaderMetrics = vcsChangesLoaderMetrics;
        this.myServerMetrics = serverMetrics;
        serverMetrics.metricBuilder("vcsRootInstances.active").description("Number of active of VCS root instances").buildGauge(this.myActiveVcsRootInstances::doubleValue);
        multiNodeTasks.subscribe("explicitChangesCollectingRequest", (MultiNodeTasks.TaskConsumer)new /* Unavailable Anonymous Inner Class!! */);
        this.myVcsMonitorThread = new NamedDaemonThreadFactory("Checking for VCS changes monitor").newThread(this::vcsMonitorAction);
        this.myMulticaster.addListener((EventListener)new /* Unavailable Anonymous Inner Class!! */);
        this.myPeriodicalVcsCheckAction = new TimeIntervalAction(() -> TeamCityProperties.getLong((String)"teamcity.vcsMonitor.checkingForChangesPeriod", (long)10000L), () -> {
            block4: {
                try {
                    try {
                        this.rescheduleMissedChecks();
                        if (!this.checkingForChangesEnabled() || !checkingForChangesTaskPrecondition.checkingForChangesEnabledOnThisNode()) break block4;
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        throw VcsModificationChecker.a(illegalArgumentException);
                    }
                    this.findAndRegisterVcsRootsForPeriodicalCheck();
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw VcsModificationChecker.a(illegalArgumentException);
                }
            }
        });
    }

    @NotNull
    private static List<String> getKnownVcsHostingNames() {
        List list;
        try {
            list = StringUtil.split((String)TeamCityProperties.getProperty((String)"teamcity.vcsMonitor.metrics.vcsHostingNames", (String)"github,gitlab,bitbucket,jetbrains.team"), (boolean)true, (char[])new char[]{','});
            if (list == null) {
                VcsModificationChecker.$$$reportNull$$$0(11);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VcsModificationChecker.a(illegalArgumentException);
        }
        return list;
    }

    public boolean isMultipleNodesVcsPollingResponsibility() {
        return VcsModificationChecker.isMultipleNodesWithCheckingForChangesResponsibility(this.myTeamCityNodes.getNodes());
    }

    public static boolean isMultipleNodesWithCheckingForChangesResponsibility(@NotNull List<TeamCityNode> list) {
        boolean bl;
        try {
            if (list == null) {
                VcsModificationChecker.$$$reportNull$$$0(12);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VcsModificationChecker.a(illegalArgumentException);
        }
        try {
            bl = list.stream().filter(teamCityNode -> teamCityNode.responsibilityEnabled(NodeResponsibility.CAN_CHECK_FOR_CHANGES)).count() > 1L;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VcsModificationChecker.a(illegalArgumentException);
        }
        return bl;
    }

    private void rescheduleMissedChecks() {
        try {
            if (this.myMissedChecks.isEmpty()) {
                return;
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VcsModificationChecker.a(illegalArgumentException);
        }
        HashMap<OperationRequestor, Set> hashMap = new HashMap<OperationRequestor, Set>();
        this.myMissedChecks.forEach((vcsRootInstance, operationRequestor2) -> hashMap.compute((OperationRequestor)operationRequestor2, (operationRequestor, hashSet) -> {
            if (hashSet == null) {
                hashSet = new HashSet<VcsRootInstance>();
            }
            hashSet.add(vcsRootInstance);
            return hashSet;
        }));
        hashMap.forEach((operationRequestor, set) -> {
            LOG.info("Re-sceduling the checking for changes tasks created by \"" + String.valueOf(operationRequestor) + "\" for " + set.size() + " VCS roots, which could not be started because the \"" + NodeResponsibility.CAN_CHECK_FOR_CHANGES.getDisplayName() + "\" responsibility was un-assigned from the current node");
            this.createMultiNodeTask((Collection<VcsRootInstance>)set, (OperationRequestor)operationRequestor);
            this.myMissedChecks.keySet().removeIf(vcsRootInstance -> set.contains(vcsRootInstance));
        });
    }

    private boolean checkingForChangesEnabled() {
        return TeamCityProperties.getBooleanOrTrue((String)"teamcity.vcsMonitor.enabled");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void vcsMonitorAction() {
        while (!this.myVcsMonitorThreadStopped.get()) {
            try {
                block16: {
                    try {
                        if (this.myVcsModificationsStorage == null || !this.myVcsModificationsStorage.isVcsChangesCacheInitialized()) break block16;
                    }
                    catch (InterruptedException interruptedException) {
                        throw VcsModificationChecker.a(interruptedException);
                    }
                    this.processChecksRequestedViaTasks();
                    this.myPeriodicalVcsCheckAction.execute();
                    boolean bl = false;
                    Object object = this.myLock;
                    synchronized (object) {
                        if (!this.myVcsRootsToCheck.isEmpty()) {
                            bl = true;
                        }
                    }
                    try {
                        if (bl) {
                            this.checkVcsRootsInMultipleThreads();
                        }
                    }
                    catch (InterruptedException interruptedException) {
                        throw VcsModificationChecker.a(interruptedException);
                    }
                }
                Object object = this.myLock;
                synchronized (object) {
                    this.myLock.wait(50L);
                }
            }
            catch (InterruptedException interruptedException) {
                LOG.warn(Thread.currentThread().getName() + " was interrupted, exiting");
                return;
            }
            catch (Throwable throwable) {
                LOG.warnAndDebugDetails("Exception while scheduling VCS modifications check", throwable);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void processChecksRequestedViaTasks() {
        HashMap<ExplicitCheckInfo, List> hashMap = new HashMap<ExplicitCheckInfo, List>();
        Object object = this.myLock;
        synchronized (object) {
            if (!this.myChecksFromTasks.isEmpty()) {
                HashMap<VcsRootInstance, ExplicitCheckInfo> hashMap2 = new HashMap<VcsRootInstance, ExplicitCheckInfo>(this.myChecksFromTasks);
                hashMap2.forEach((vcsRootInstance, explicitCheckInfo2) -> hashMap.computeIfAbsent((ExplicitCheckInfo)explicitCheckInfo2, explicitCheckInfo -> new ArrayList()).add(vcsRootInstance));
            }
        }
        hashMap.forEach((explicitCheckInfo, list) -> {
            this.registerVcsRootsForCheckingForChanges((Collection<VcsRootInstance>)list, explicitCheckInfo.myRequestor, explicitCheckInfo.myRequestTime);
            Object object = this.myLock;
            synchronized (object) {
                for (VcsRootInstance vcsRootInstance : list) {
                    this.myChecksFromTasks.remove(vcsRootInstance, explicitCheckInfo);
                }
            }
        });
    }

    @Used(value="spring")
    public void setVcsChangesLoader(RecurringChangesLoader recurringChangesLoader) {
        this.myVcsChangesLoader = recurringChangesLoader;
    }

    @Used(value="spring")
    public void setBuildServer(BuildServerState buildServerState) {
        this.myServer = buildServerState;
    }

    @Used(value="spring")
    public void setProjectManager(ProjectManager projectManager) {
        this.myProjectManager = projectManager;
    }

    @Used(value="spring")
    public void setExecutorServices(VcsExecutionServices vcsExecutionServices) {
        this.myExecutorServices = vcsExecutionServices;
    }

    @Used(value="spring")
    public void setVcsModificationsStorage(VcsModificationsStorage vcsModificationsStorage) {
        this.myVcsModificationsStorage = vcsModificationsStorage;
    }

    @VisibleForTesting
    public void setTimeService(@NotNull TimeService timeService) {
        try {
            if (timeService == null) {
                VcsModificationChecker.$$$reportNull$$$0(13);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VcsModificationChecker.a(illegalArgumentException);
        }
        this.myTimeService = timeService;
    }

    public void checkForModificationsAsync() {
        this.myPeriodicalVcsCheckAction.resetLastActionTime();
        this.notifyVcsMonitor();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void findAndRegisterVcsRootsForPeriodicalCheck() {
        DependencyParametersCalculationContext.enter();
        try {
            HashSet hashSet = new HashSet();
            HashMap<VcsRootInstance, Map<SBuildType, CheckoutRules>> hashMap = new HashMap<VcsRootInstance, Map<SBuildType, CheckoutRules>>();
            for (SBuildType sBuildType : this.myProjectManager.getActiveBuildTypes()) {
                for (VcsRootInstanceEntry vcsRootInstanceEntry : sBuildType.getVcsRootInstanceEntries()) {
                    hashMap.computeIfAbsent(vcsRootInstanceEntry.getVcsRoot(), vcsRootInstance -> {
                        HashMap hashMap = new HashMap();
                        hashSet.add(vcsRootInstance.getParent());
                        return hashMap;
                    }).put(sBuildType, vcsRootInstanceEntry.getCheckoutRules());
                }
            }
            for (SBuildType sBuildType : this.myProjectManager.getArchivedBuildTypes()) {
                try {
                    if (Collections.disjoint(hashSet, sBuildType.getVcsRoots())) {
                        continue;
                    }
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw VcsModificationChecker.a(illegalArgumentException);
                }
                for (VcsRootInstanceEntry vcsRootInstanceEntry : sBuildType.getVcsRootInstanceEntries()) {
                    Map map = (Map)hashMap.get(vcsRootInstanceEntry.getVcsRoot());
                    try {
                        if (map == null) continue;
                        map.put(sBuildType, vcsRootInstanceEntry.getCheckoutRules());
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        throw VcsModificationChecker.a(illegalArgumentException);
                    }
                }
            }
            SettingsRootInfo settingsRootInfo = this.getSettingRootsInfoForServer(hashMap);
            settingsRootInfo.fillImplicitUsages(hashMap);
            this.registerVcsRootsForPeriodicalCheck(settingsRootInfo, hashMap, null);
            this.myActiveVcsRootInstances.set(hashMap.size());
        }
        finally {
            DependencyParametersCalculationContext.exit();
        }
    }

    @NotNull
    public Future checkForModificationsAsync(@NotNull Collection<VcsRootInstance> collection, @NotNull OperationRequestor operationRequestor) {
        CompletableFuture completableFuture;
        block23: {
            CompletableFuture<Object> completableFuture2;
            block24: {
                block21: {
                    CompletableFuture<Object> completableFuture3;
                    block22: {
                        try {
                            if (collection == null) {
                                VcsModificationChecker.$$$reportNull$$$0(14);
                            }
                        }
                        catch (IllegalArgumentException illegalArgumentException) {
                            throw VcsModificationChecker.a(illegalArgumentException);
                        }
                        try {
                            if (operationRequestor == null) {
                                VcsModificationChecker.$$$reportNull$$$0(15);
                            }
                        }
                        catch (IllegalArgumentException illegalArgumentException) {
                            throw VcsModificationChecker.a(illegalArgumentException);
                        }
                        try {
                            try {
                                if (!collection.isEmpty()) break block21;
                                completableFuture3 = CompletableFuture.completedFuture(null);
                                if (completableFuture3 != null) break block22;
                            }
                            catch (IllegalArgumentException illegalArgumentException) {
                                throw VcsModificationChecker.a(illegalArgumentException);
                            }
                            VcsModificationChecker.$$$reportNull$$$0(16);
                        }
                        catch (IllegalArgumentException illegalArgumentException) {
                            throw VcsModificationChecker.a(illegalArgumentException);
                        }
                    }
                    return completableFuture3;
                }
                try {
                    try {
                        try {
                            try {
                                if (!CurrentNodeInfo.isMainNode() || !this.myCheckingForChangesTaskPrecondition.canAcceptTask()) break block23;
                            }
                            catch (IllegalArgumentException illegalArgumentException) {
                                throw VcsModificationChecker.a(illegalArgumentException);
                            }
                            if (this.isMultipleNodesVcsPollingResponsibility()) break block23;
                        }
                        catch (IllegalArgumentException illegalArgumentException) {
                            throw VcsModificationChecker.a(illegalArgumentException);
                        }
                        this.registerVcsRootsForCheckingForChanges(collection, operationRequestor, this.myTimeService.nowDate());
                        completableFuture2 = CompletableFuture.completedFuture(null);
                        if (completableFuture2 != null) break block24;
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        throw VcsModificationChecker.a(illegalArgumentException);
                    }
                    VcsModificationChecker.$$$reportNull$$$0(17);
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw VcsModificationChecker.a(illegalArgumentException);
                }
            }
            return completableFuture2;
        }
        MultiNodeTasks.SubmittedTask submittedTask = this.createMultiNodeTask(collection, operationRequestor);
        CompletableFuture completableFuture4 = new CompletableFuture();
        try {
            submittedTask.whenComplete(() -> completableFuture4.complete(null));
            completableFuture = completableFuture4;
            if (completableFuture == null) {
                VcsModificationChecker.$$$reportNull$$$0(18);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VcsModificationChecker.a(illegalArgumentException);
        }
        return completableFuture;
    }

    @NotNull
    private MultiNodeTasks.SubmittedTask createMultiNodeTask(@NotNull Collection<VcsRootInstance> collection, @NotNull OperationRequestor operationRequestor) {
        MultiNodeTasks.SubmittedTask submittedTask;
        try {
            if (collection == null) {
                VcsModificationChecker.$$$reportNull$$$0(19);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VcsModificationChecker.a(illegalArgumentException);
        }
        try {
            if (operationRequestor == null) {
                VcsModificationChecker.$$$reportNull$$$0(20);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VcsModificationChecker.a(illegalArgumentException);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(operationRequestor.name()).append(",").append(StringUtil.join((String)",", (Iterable)collection.stream().map(vcsRootInstance -> vcsRootInstance.getId()).sorted().collect(Collectors.toList())));
        String string = stringBuilder.toString();
        String string2 = DigestUtils.shaHex((String)string);
        try {
            submittedTask = this.myNodeTasks.submit((MultiNodeTasks.Task)new MultiNodeTasks.TaskData("explicitChangesCollectingRequest", string2, string));
            if (submittedTask == null) {
                VcsModificationChecker.$$$reportNull$$$0(21);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VcsModificationChecker.a(illegalArgumentException);
        }
        return submittedTask;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     */
    private void registerVcsRootsForCheckingForChanges(@NotNull Collection<VcsRootInstance> collection, @NotNull OperationRequestor operationRequestor, @NotNull Date date) {
        block30: {
            try {
                if (collection == null) {
                    VcsModificationChecker.$$$reportNull$$$0(22);
                }
            }
            catch (Throwable throwable) {
                throw VcsModificationChecker.a(throwable);
            }
            try {
                if (operationRequestor == null) {
                    VcsModificationChecker.$$$reportNull$$$0(23);
                }
            }
            catch (Throwable throwable) {
                throw VcsModificationChecker.a(throwable);
            }
            try {
                if (date == null) {
                    VcsModificationChecker.$$$reportNull$$$0(24);
                }
            }
            catch (Throwable throwable) {
                throw VcsModificationChecker.a(throwable);
            }
            if (operationRequestor != OperationRequestor.COMMIT_HOOK) break block30;
            try {
                block34: {
                    ServerLoadMetrics.VCS_COMMIT_HOOKS_RATE.increment(1.0);
                    if (!TeamCityProperties.getBooleanOrTrue((String)"teamcity.modificationChecker.disablePollingOnCommitHookRightAway")) break block30;
                    break block34;
                    catch (Throwable throwable) {
                        throw VcsModificationChecker.a(throwable);
                    }
                }
                this.disablePolling(collection);
            }
            catch (Throwable throwable) {
                throw VcsModificationChecker.a(throwable);
            }
        }
        WatchDog watchDog = new WatchDog("Scheduling checking for changes for " + collection.size() + " VCS root instances", LOG, false);
        try {
            Stoppable stoppable;
            block32: {
                stoppable = this.myVcsChangesLoaderMetrics.getPrepareForCheckingForChangesSchedulingMetric().startMsecsTimer();
                try {
                    SettingsRootInfo settingsRootInfo;
                    Map<VcsRootInstance, Map<SBuildType, CheckoutRules>> map;
                    block31: {
                        DependencyParametersCalculationContext.enter();
                        map = this.computeUsagesOfVcsRootInstancesBatch(collection);
                        settingsRootInfo = this.getSettingRootsInfoForRoots(collection, map);
                        try {
                            settingsRootInfo.fillImplicitUsages(map);
                            this.processArchivedProjects(map, settingsRootInfo);
                            if (operationRequestor != OperationRequestor.SCHEDULE) break block31;
                            this.registerVcsRootsForPeriodicalCheck(settingsRootInfo, map, date);
                            break block32;
                        }
                        catch (Throwable throwable) {
                            throw VcsModificationChecker.a(throwable);
                        }
                    }
                    String string = "r-" + UUID.randomUUID().toString().substring(0, 8);
                    LOG.info("Started processing changes collection operation with id " + string + ". Number of vcs roots: " + map.size() + ". Request start time: " + date.getTime());
                    Object object = this.myLock;
                    synchronized (object) {
                        for (Map.Entry<VcsRootInstance, Map<SBuildType, CheckoutRules>> entry : map.entrySet()) {
                            VcsRootInstance vcsRootInstance = entry.getKey();
                            Consumer<VcsRootInstance> consumer = this.createDelayMetricUpdater(operationRequestor, date, string);
                            this.myVcsRootsToCheck.put(vcsRootInstance, new PeriodicalCheckParameters(vcsRootInstance, entry.getValue(), operationRequestor, settingsRootInfo, consumer, string));
                        }
                    }
                }
                catch (Throwable throwable) {
                    if (stoppable != null) {
                        try {
                            stoppable.close();
                        }
                        catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                    }
                    throw throwable;
                }
            }
            try {
                if (stoppable != null) {
                    stoppable.close();
                }
            }
            catch (Throwable throwable) {
                throw VcsModificationChecker.a(throwable);
            }
        }
        finally {
            DependencyParametersCalculationContext.exit();
            watchDog.stop();
        }
        this.notifyVcsMonitor();
    }

    @NotNull
    private Consumer<VcsRootInstance> createDelayMetricUpdater(@NotNull OperationRequestor operationRequestor, @NotNull Date date, @Nullable String string) {
        Consumer<VcsRootInstance> consumer;
        try {
            if (operationRequestor == null) {
                VcsModificationChecker.$$$reportNull$$$0(25);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VcsModificationChecker.a(illegalArgumentException);
        }
        try {
            if (date == null) {
                VcsModificationChecker.$$$reportNull$$$0(26);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VcsModificationChecker.a(illegalArgumentException);
        }
        try {
            consumer = vcsRootInstance -> {
                Object object;
                String string2;
                Logger logger;
                long l = Math.max(0L, this.myTimeService.now() - date.getTime());
                try {
                    logger = LOG;
                    string2 = LogUtil.describe((Loggable)vcsRootInstance);
                    object = string == null ? "" : " with id " + string;
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw VcsModificationChecker.a(illegalArgumentException);
                }
                logger.info("Changes collection operation for vcs root " + string2 + (String)object + " finished in " + l + " ms");
                this.getCounter(operationRequestor, (VcsRootInstance)vcsRootInstance).increment((double)l);
            };
            if (consumer == null) {
                VcsModificationChecker.$$$reportNull$$$0(27);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VcsModificationChecker.a(illegalArgumentException);
        }
        return consumer;
    }

    @NotNull
    private Counter getCounter(@NotNull OperationRequestor operationRequestor2, @NotNull VcsRootInstance vcsRootInstance) {
        Counter counter;
        try {
            if (operationRequestor2 == null) {
                VcsModificationChecker.$$$reportNull$$$0(28);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VcsModificationChecker.a(illegalArgumentException);
        }
        try {
            if (vcsRootInstance == null) {
                VcsModificationChecker.$$$reportNull$$$0(29);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VcsModificationChecker.a(illegalArgumentException);
        }
        Map map = this.myOperationsDelay.computeIfAbsent(operationRequestor2, operationRequestor -> new ConcurrentHashMap());
        for (String string2 : VcsModificationChecker.getKnownVcsHostingNames()) {
            for (String string3 : vcsRootInstance.getProperties().values()) {
                Counter counter2;
                block15: {
                    try {
                        try {
                            if (!string3.contains(string2)) continue;
                            counter2 = map.computeIfAbsent(string2, string -> this.buildOperationDelayCounter(operationRequestor2, (String)string));
                            if (counter2 != null) break block15;
                        }
                        catch (IllegalArgumentException illegalArgumentException) {
                            throw VcsModificationChecker.a(illegalArgumentException);
                        }
                        VcsModificationChecker.$$$reportNull$$$0(30);
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        throw VcsModificationChecker.a(illegalArgumentException);
                    }
                }
                return counter2;
            }
        }
        try {
            counter = map.computeIfAbsent("unknown", string -> this.buildOperationDelayCounter(operationRequestor2, (String)string));
            if (counter == null) {
                VcsModificationChecker.$$$reportNull$$$0(31);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VcsModificationChecker.a(illegalArgumentException);
        }
        return counter;
    }

    @NotNull
    private Counter buildOperationDelayCounter(@NotNull OperationRequestor operationRequestor, @NotNull String string) {
        Counter counter;
        try {
            if (operationRequestor == null) {
                VcsModificationChecker.$$$reportNull$$$0(32);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VcsModificationChecker.a(illegalArgumentException);
        }
        try {
            if (string == null) {
                VcsModificationChecker.$$$reportNull$$$0(33);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VcsModificationChecker.a(illegalArgumentException);
        }
        try {
            counter = this.myServerMetrics.metricBuilder("vcsChangesCollection.delay").tags(new String[]{"requestor", operationRequestor.name(), "vcsHosting", string}).description("Delay of the checking for changes operations invoked by: " + operationRequestor.getDescription()).dataType(MetricDataType.MILLISECONDS).experimental(true).buildCounter();
            if (counter == null) {
                VcsModificationChecker.$$$reportNull$$$0(34);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VcsModificationChecker.a(illegalArgumentException);
        }
        return counter;
    }

    private void disablePolling(@NotNull Collection<VcsRootInstance> collection) {
        try {
            if (collection == null) {
                VcsModificationChecker.$$$reportNull$$$0(35);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VcsModificationChecker.a(illegalArgumentException);
        }
        collection.forEach(vcsRootInstance -> {
            try {
                ((VcsRootInstanceEx)vcsRootInstance).setPollingMode(false);
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Disable polling mode for " + vcsRootInstance.describe(false) + " on commit hook request");
                }
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw VcsModificationChecker.a(illegalArgumentException);
            }
        });
    }

    @NotNull
    private List<VcsRootInstance> findVcsRootInstances(@NotNull List<Long> list) {
        ArrayList<VcsRootInstance> arrayList;
        try {
            if (list == null) {
                VcsModificationChecker.$$$reportNull$$$0(36);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VcsModificationChecker.a(illegalArgumentException);
        }
        ArrayList<VcsRootInstance> arrayList2 = new ArrayList<VcsRootInstance>();
        try {
            list.forEach(l -> {
                VcsRootInstance vcsRootInstance = this.myVcsRootInstanceManager.findRootInstanceById(l.longValue());
                try {
                    if (vcsRootInstance != null) {
                        arrayList2.add(vcsRootInstance);
                    }
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw VcsModificationChecker.a(illegalArgumentException);
                }
            });
            arrayList = arrayList2;
            if (arrayList == null) {
                VcsModificationChecker.$$$reportNull$$$0(37);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VcsModificationChecker.a(illegalArgumentException);
        }
        return arrayList;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void notifyVcsMonitor() {
        Object object = this.myLock;
        synchronized (object) {
            this.myLock.notify();
        }
    }

    @NotNull
    private SettingsRootInfo getSettingRootsInfoForServer(@NotNull Map<VcsRootInstance, Map<SBuildType, CheckoutRules>> map) {
        SettingsRootInfo settingsRootInfo;
        SettingsRootInfo settingsRootInfo2;
        block16: {
            SettingsRootInfo settingsRootInfo3;
            block17: {
                try {
                    if (map == null) {
                        VcsModificationChecker.$$$reportNull$$$0(38);
                    }
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw VcsModificationChecker.a(illegalArgumentException);
                }
                settingsRootInfo2 = new SettingsRootInfo();
                try {
                    try {
                        if (this.collectTeamCitySettingsChanges()) break block16;
                        settingsRootInfo3 = settingsRootInfo2;
                        if (settingsRootInfo3 != null) break block17;
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        throw VcsModificationChecker.a(illegalArgumentException);
                    }
                    VcsModificationChecker.$$$reportNull$$$0(39);
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw VcsModificationChecker.a(illegalArgumentException);
                }
            }
            return settingsRootInfo3;
        }
        this.findAffectedBuildTypes(this.myProjectManager.getActiveProjects(), settingsRootInfo2).forEach((vcsRootInstance, set) -> {
            Map map2 = (Map)map.get(vcsRootInstance);
            this.mySettingsRootManager.getSettingsRootCheckoutRules((Collection)set, vcsRootInstance).forEach((sBuildType, checkoutRules) -> {
                boolean bl;
                block5: {
                    block4: {
                        try {
                            try {
                                if (map2 == null || map2.get(sBuildType) == null) break block4;
                            }
                            catch (IllegalArgumentException illegalArgumentException) {
                                throw VcsModificationChecker.a(illegalArgumentException);
                            }
                            bl = true;
                            break block5;
                        }
                        catch (IllegalArgumentException illegalArgumentException) {
                            throw VcsModificationChecker.a(illegalArgumentException);
                        }
                    }
                    bl = false;
                }
                boolean bl2 = bl;
                settingsRootInfo2.addSettingsRootUsage(vcsRootInstance, sBuildType, checkoutRules, bl2);
            });
        });
        ArrayList<SProject> arrayList = new ArrayList<SProject>();
        for (ProjectSettingsRootInfo projectSettingsRootInfo : this.getProjectSettingsRoots(this.myProjectManager.getArchivedProjects())) {
            Map<SBuildType, CheckoutRules> map2 = map.get(projectSettingsRootInfo.settingsRoot());
            try {
                try {
                    if (map2 == null && !settingsRootInfo2.containsSettingsRoot(projectSettingsRootInfo.settingsRoot())) {
                        continue;
                    }
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw VcsModificationChecker.a(illegalArgumentException);
                }
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw VcsModificationChecker.a(illegalArgumentException);
            }
            arrayList.add(projectSettingsRootInfo.project());
        }
        try {
            this.findAffectedBuildTypes(arrayList, settingsRootInfo2).forEach((vcsRootInstance, set) -> {
                Map map2 = (Map)map.get(vcsRootInstance);
                this.mySettingsRootManager.getSettingsRootCheckoutRules((Collection)set, vcsRootInstance).forEach((sBuildType, checkoutRules) -> {
                    boolean bl;
                    block5: {
                        block4: {
                            try {
                                try {
                                    if (map2 == null || map2.get(sBuildType) == null) break block4;
                                }
                                catch (IllegalArgumentException illegalArgumentException) {
                                    throw VcsModificationChecker.a(illegalArgumentException);
                                }
                                bl = true;
                                break block5;
                            }
                            catch (IllegalArgumentException illegalArgumentException) {
                                throw VcsModificationChecker.a(illegalArgumentException);
                            }
                        }
                        bl = false;
                    }
                    boolean bl2 = bl;
                    settingsRootInfo2.addSettingsRootUsage(vcsRootInstance, sBuildType, checkoutRules, bl2);
                });
            });
            settingsRootInfo = settingsRootInfo2;
            if (settingsRootInfo == null) {
                VcsModificationChecker.$$$reportNull$$$0(40);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VcsModificationChecker.a(illegalArgumentException);
        }
        return settingsRootInfo;
    }

    @NotNull
    private Map<VcsRootInstance, Set<SBuildType>> findAffectedBuildTypes(List<SProject> list, SettingsRootInfo settingsRootInfo) {
        HashMap<VcsRootInstance, Set<SBuildType>> hashMap;
        HashMap<VcsRootInstance, Set<SBuildType>> hashMap2 = new HashMap<VcsRootInstance, Set<SBuildType>>();
        for (ProjectSettingsRootInfo projectSettingsRootInfo : this.getProjectSettingsRoots(list)) {
            settingsRootInfo.addSettingsRoot(projectSettingsRootInfo.settingsRoot());
            hashMap2.compute(projectSettingsRootInfo.settingsRoot(), (vcsRootInstance, hashSet) -> {
                if (hashSet == null) {
                    hashSet = new HashSet();
                }
                hashSet.addAll(projectSettingsRootInfo.project().getOwnBuildTypes());
                return hashSet;
            });
        }
        try {
            hashMap = hashMap2;
            if (hashMap == null) {
                VcsModificationChecker.$$$reportNull$$$0(41);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VcsModificationChecker.a(illegalArgumentException);
        }
        return hashMap;
    }

    @NotNull
    private SettingsRootInfo getSettingRootsInfoForRoots(@NotNull Collection<VcsRootInstance> collection, @NotNull Map<VcsRootInstance, Map<SBuildType, CheckoutRules>> map) {
        SettingsRootInfo settingsRootInfo;
        SettingsRootInfo settingsRootInfo2;
        block14: {
            SettingsRootInfo settingsRootInfo3;
            block15: {
                try {
                    if (collection == null) {
                        VcsModificationChecker.$$$reportNull$$$0(42);
                    }
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw VcsModificationChecker.a(illegalArgumentException);
                }
                try {
                    if (map == null) {
                        VcsModificationChecker.$$$reportNull$$$0(43);
                    }
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw VcsModificationChecker.a(illegalArgumentException);
                }
                settingsRootInfo2 = new SettingsRootInfo();
                try {
                    try {
                        if (this.collectTeamCitySettingsChanges()) break block14;
                        settingsRootInfo3 = settingsRootInfo2;
                        if (settingsRootInfo3 != null) break block15;
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        throw VcsModificationChecker.a(illegalArgumentException);
                    }
                    VcsModificationChecker.$$$reportNull$$$0(44);
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw VcsModificationChecker.a(illegalArgumentException);
                }
            }
            return settingsRootInfo3;
        }
        for (VcsRootInstance vcsRootInstance : collection) {
            Map<SBuildType, CheckoutRules> map2 = map.get(vcsRootInstance);
            Set set = this.mySettingsRootManager.getProjectsBySettingsRootInstance(vcsRootInstance);
            if (set.isEmpty()) continue;
            settingsRootInfo2.addSettingsRoot(vcsRootInstance);
            HashSet hashSet = new HashSet();
            set.forEach(sProject -> hashSet.addAll(sProject.getOwnBuildTypes()));
            this.mySettingsRootManager.getSettingsRootCheckoutRules(hashSet, vcsRootInstance).forEach((sBuildType, checkoutRules) -> {
                boolean bl;
                block5: {
                    block4: {
                        try {
                            try {
                                if (map2 == null || map2.get(sBuildType) == null) break block4;
                            }
                            catch (IllegalArgumentException illegalArgumentException) {
                                throw VcsModificationChecker.a(illegalArgumentException);
                            }
                            bl = true;
                            break block5;
                        }
                        catch (IllegalArgumentException illegalArgumentException) {
                            throw VcsModificationChecker.a(illegalArgumentException);
                        }
                    }
                    bl = false;
                }
                boolean bl2 = bl;
                settingsRootInfo2.addSettingsRootUsage(vcsRootInstance, sBuildType, checkoutRules, bl2);
            });
        }
        try {
            settingsRootInfo = settingsRootInfo2;
            if (settingsRootInfo == null) {
                VcsModificationChecker.$$$reportNull$$$0(45);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VcsModificationChecker.a(illegalArgumentException);
        }
        return settingsRootInfo;
    }

    @NotNull
    private List<ProjectSettingsRootInfo> getProjectSettingsRoots(@NotNull List<SProject> list) {
        ArrayList<ProjectSettingsRootInfo> arrayList;
        try {
            if (list == null) {
                VcsModificationChecker.$$$reportNull$$$0(46);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VcsModificationChecker.a(illegalArgumentException);
        }
        ArrayList<ProjectSettingsRootInfo> arrayList2 = new ArrayList<ProjectSettingsRootInfo>();
        for (SProject sProject : list) {
            VcsRootInstance vcsRootInstance = this.mySettingsRootManager.getVersionedSettingsVcsRootInstance(sProject);
            try {
                if (vcsRootInstance == null) continue;
                arrayList2.add(new ProjectSettingsRootInfo(sProject, vcsRootInstance));
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw VcsModificationChecker.a(illegalArgumentException);
            }
        }
        try {
            arrayList = arrayList2;
            if (arrayList == null) {
                VcsModificationChecker.$$$reportNull$$$0(47);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VcsModificationChecker.a(illegalArgumentException);
        }
        return arrayList;
    }

    public void forceCheckingFor(@NotNull Collection<VcsRootInstance> collection) {
        try {
            if (collection == null) {
                VcsModificationChecker.$$$reportNull$$$0(48);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VcsModificationChecker.a(illegalArgumentException);
        }
        this.forceCheckingFor(collection, OperationRequestor.UNKNOWN);
    }

    public void forceCheckingFor(@NotNull Collection<VcsRootInstance> collection, @NotNull OperationRequestor operationRequestor) {
        try {
            if (collection == null) {
                VcsModificationChecker.$$$reportNull$$$0(49);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VcsModificationChecker.a(illegalArgumentException);
        }
        try {
            if (operationRequestor == null) {
                VcsModificationChecker.$$$reportNull$$$0(50);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VcsModificationChecker.a(illegalArgumentException);
        }
        this.checkForModificationsAsync(collection, operationRequestor);
    }

    private void processArchivedProjects(@NotNull Map<VcsRootInstance, Map<SBuildType, CheckoutRules>> map, @NotNull SettingsRootInfo settingsRootInfo) {
        try {
            if (map == null) {
                VcsModificationChecker.$$$reportNull$$$0(51);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VcsModificationChecker.a(illegalArgumentException);
        }
        try {
            if (settingsRootInfo == null) {
                VcsModificationChecker.$$$reportNull$$$0(52);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VcsModificationChecker.a(illegalArgumentException);
        }
        Iterator<VcsRootInstance> iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            VcsRootInstance vcsRootInstance = iterator.next();
            try {
                if (settingsRootInfo.containsSettingsRoot(vcsRootInstance)) {
                    continue;
                }
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw VcsModificationChecker.a(illegalArgumentException);
            }
            Map<SBuildType, CheckoutRules> map2 = map.get(vcsRootInstance);
            try {
                if (this.hasActiveBuildType(map2.keySet())) {
                    continue;
                }
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw VcsModificationChecker.a(illegalArgumentException);
            }
            iterator.remove();
        }
    }

    private boolean hasActiveBuildType(@NotNull Collection<SBuildType> collection) {
        try {
            if (collection == null) {
                VcsModificationChecker.$$$reportNull$$$0(53);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VcsModificationChecker.a(illegalArgumentException);
        }
        for (SBuildType sBuildType : collection) {
            try {
                if (sBuildType.getProject().isArchived()) continue;
                return true;
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw VcsModificationChecker.a(illegalArgumentException);
            }
        }
        return false;
    }

    @NotNull
    private Map<VcsRootInstance, Map<SBuildType, CheckoutRules>> computeUsagesOfVcsRootInstancesBatch(@NotNull Collection<VcsRootInstance> collection) {
        HashMap<VcsRootInstance, Map<SBuildType, CheckoutRules>> hashMap;
        try {
            if (collection == null) {
                VcsModificationChecker.$$$reportNull$$$0(54);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VcsModificationChecker.a(illegalArgumentException);
        }
        HashMap<VcsRootInstance, Map<SBuildType, CheckoutRules>> hashMap2 = new HashMap<VcsRootInstance, Map<SBuildType, CheckoutRules>>();
        HashSet<VcsRootInstance> hashSet = new HashSet<VcsRootInstance>(collection);
        HashSet<SBuildType> hashSet2 = new HashSet<SBuildType>();
        for (SVcsRoot sVcsRoot : this.collectVcsRootParents(collection)) {
            for (SBuildType sBuildType : sVcsRoot.getUsagesInConfigurations()) {
                if (!hashSet2.add(sBuildType)) continue;
                for (VcsRootInstanceEntry vcsRootInstanceEntry : sBuildType.getVcsRootInstanceEntries()) {
                    VcsRootInstance vcsRootInstance2 = vcsRootInstanceEntry.getVcsRoot();
                    try {
                        if (!hashSet.contains(vcsRootInstance2)) continue;
                        hashMap2.computeIfAbsent(vcsRootInstance2, vcsRootInstance -> new HashMap()).put(sBuildType, vcsRootInstanceEntry.getCheckoutRules());
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        throw VcsModificationChecker.a(illegalArgumentException);
                    }
                }
            }
        }
        try {
            hashMap = hashMap2;
            if (hashMap == null) {
                VcsModificationChecker.$$$reportNull$$$0(55);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VcsModificationChecker.a(illegalArgumentException);
        }
        return hashMap;
    }

    @NotNull
    private Set<SVcsRoot> collectVcsRootParents(@NotNull Collection<VcsRootInstance> collection) {
        HashSet<SVcsRoot> hashSet;
        try {
            if (collection == null) {
                VcsModificationChecker.$$$reportNull$$$0(56);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VcsModificationChecker.a(illegalArgumentException);
        }
        HashSet<SVcsRoot> hashSet2 = new HashSet<SVcsRoot>();
        for (VcsRootInstance vcsRootInstance : collection) {
            hashSet2.add(vcsRootInstance.getParent());
        }
        try {
            hashSet = hashSet2;
            if (hashSet == null) {
                VcsModificationChecker.$$$reportNull$$$0(57);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VcsModificationChecker.a(illegalArgumentException);
        }
        return hashSet;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void registerVcsRootsForPeriodicalCheck(@NotNull SettingsRootInfo settingsRootInfo, @NotNull Map<VcsRootInstance, Map<SBuildType, CheckoutRules>> map, @Nullable Date date) {
        try {
            if (settingsRootInfo == null) {
                VcsModificationChecker.$$$reportNull$$$0(58);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VcsModificationChecker.a(illegalArgumentException);
        }
        try {
            if (map == null) {
                VcsModificationChecker.$$$reportNull$$$0(59);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VcsModificationChecker.a(illegalArgumentException);
        }
        AtomicInteger atomicInteger = new AtomicInteger();
        HashMap<VcsRootInstance, Map<SBuildType, CheckoutRules>> hashMap = new HashMap<VcsRootInstance, Map<SBuildType, CheckoutRules>>();
        HashMap<VcsRootInstance, PeriodicalCheckParameters> hashMap2 = this.myLock;
        synchronized (hashMap2) {
            for (Map.Entry<VcsRootInstance, Map<SBuildType, CheckoutRules>> object2 : map.entrySet()) {
                VcsRootInstance vcsRootInstance = object2.getKey();
                try {
                    if (this.myInProgress.containsKey(vcsRootInstance.getId())) {
                        continue;
                    }
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw VcsModificationChecker.a(illegalArgumentException);
                }
                hashMap.put(object2.getKey(), object2.getValue());
            }
        }
        hashMap2 = new HashMap<VcsRootInstance, PeriodicalCheckParameters>();
        for (Map.Entry<Object, Map<Object, Object>> entry : hashMap.entrySet()) {
            VcsRootInstance vcsRootInstance = (VcsRootInstance)entry.getKey();
            try {
                if (!this.myCheckingForChangesScheduler.shouldCheckForChangesNow(vcsRootInstance, map.size())) continue;
                hashMap2.computeIfAbsent(vcsRootInstance, vcsRootInstance3 -> {
                    Consumer<VcsRootInstance> consumer;
                    try {
                        consumer = date == null ? vcsRootInstance -> {} : this.createDelayMetricUpdater(OperationRequestor.SCHEDULE, date, null);
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        throw VcsModificationChecker.a(illegalArgumentException);
                    }
                    Consumer<VcsRootInstance> consumer2 = consumer;
                    return new PeriodicalCheckParameters(vcsRootInstance, (Map)entry.getValue(), OperationRequestor.SCHEDULE, settingsRootInfo, consumer2, null);
                });
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw VcsModificationChecker.a(illegalArgumentException);
            }
        }
        Object object3 = this.myLock;
        synchronized (object3) {
            for (Map.Entry entry : hashMap2.entrySet()) {
                VcsRootInstance vcsRootInstance = (VcsRootInstance)entry.getKey();
                try {
                    if (this.myInProgress.containsKey(vcsRootInstance.getId())) {
                        continue;
                    }
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw VcsModificationChecker.a(illegalArgumentException);
                }
                atomicInteger.getAndIncrement();
                this.myVcsRootsToCheck.put((VcsRootInstance)entry.getKey(), (PeriodicalCheckParameters)entry.getValue());
            }
        }
        try {
            if (atomicInteger.get() > 0) {
                Loggers.VCS.info("Found " + atomicInteger.get() + " VCS root instances where periodical checking for changes is required");
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VcsModificationChecker.a(illegalArgumentException);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     */
    private void checkVcsRootsInMultipleThreads() {
        Iterator iterator;
        Object object3222;
        HashMap<VcsRootInstance, PeriodicalCheckParameters> hashMap;
        HashMap<Long, Runnable> hashMap2 = new HashMap<Long, Runnable>();
        Object object2 = this.myLock;
        synchronized (object2) {
            if (this.myVcsRootsToCheck.isEmpty()) {
                return;
            }
            hashMap = new HashMap<VcsRootInstance, PeriodicalCheckParameters>(this.myVcsRootsToCheck);
            for (Object object3222 : hashMap.keySet()) {
                iterator = new Iterator();
                this.myInProgress.put(object3222.getId(), iterator);
                hashMap2.put(object3222.getId(), () -> this.lambda$checkVcsRootsInMultipleThreads$29((VcsRootInstance)object3222, iterator));
            }
            this.myVcsRootsToCheck.clear();
        }
        int n = 0;
        Object object4 = null;
        object3222 = this.sortVcsRootInstances(hashMap.keySet());
        iterator = object3222.iterator();
        while (iterator.hasNext()) {
            Object object5;
            PeriodicalCheckParameters periodicalCheckParameters;
            VcsRootInstance vcsRootInstance;
            block24: {
                vcsRootInstance = (VcsRootInstance)iterator.next();
                periodicalCheckParameters = (PeriodicalCheckParameters)hashMap.get(vcsRootInstance);
                if ($assertionsDisabled) break block24;
                try {
                    block25: {
                        if (periodicalCheckParameters != null) break block24;
                        break block25;
                        catch (Throwable throwable2) {
                            throw VcsModificationChecker.a(throwable2);
                        }
                    }
                    throw new AssertionError();
                }
                catch (Throwable throwable3) {
                    throw VcsModificationChecker.a(throwable3);
                }
            }
            Runnable runnable = (Runnable)hashMap2.get(vcsRootInstance.getId());
            try {
                VcsRootInstanceEx vcsRootInstanceEx = (VcsRootInstanceEx)vcsRootInstance;
                object5 = vcsRootInstanceEx.getStatus();
                if (LOG.isDebugEnabled()) {
                    String string;
                    String string2 = periodicalCheckParameters.getOperationId();
                    try {
                        string = string2 != null ? String.format("(operation id: %s)", string2) : "";
                    }
                    catch (Throwable throwable4) {
                        throw VcsModificationChecker.a(throwable4);
                    }
                    String string3 = string;
                    LOG.debug(String.format("Adding %s to the changes collection executor queue%s", vcsRootInstance.describe(false), string3));
                }
                CompletableFuture.runAsync((Runnable)new CollectChangesAction(this, vcsRootInstance, periodicalCheckParameters), this.getExecutor()).whenComplete((void_, throwable) -> {
                    try {
                        runnable.run();
                        if (throwable != null) {
                            LOG.warnAndDebugDetails("Unhandled error in CollectChangesAction", throwable);
                        }
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        throw VcsModificationChecker.a(illegalArgumentException);
                    }
                });
                vcsRootInstanceEx.compareAndSetStatus(object5, VcsRootStatus.Type.SCHEDULED);
            }
            catch (Throwable throwable5) {
                runnable.run();
                object4 = throwable5;
                ++n;
                if (periodicalCheckParameters.getRequestor() != OperationRequestor.SCHEDULE) {
                    object5 = this.myLock;
                    synchronized (object5) {
                        this.myVcsRootsToCheck.putIfAbsent(vcsRootInstance, periodicalCheckParameters);
                    }
                }
                try {
                    if (!LOG.isDebugEnabled()) continue;
                    LOG.debug("Failed to queue checking for changes for VCS root " + vcsRootInstance.describe(false) + " because of the error: " + throwable5.toString());
                }
                catch (Throwable throwable6) {
                    throw VcsModificationChecker.a(throwable6);
                }
            }
        }
        try {
            if (n > 0) {
                LOG.warn("Failed to queue checking for changes for " + n + " VCS roots, will repeat next time. Last error: " + ((Throwable)object4).toString());
            }
        }
        catch (Throwable throwable7) {
            throw VcsModificationChecker.a(throwable7);
        }
    }

    @NotNull
    private List<VcsRootInstance> sortVcsRootInstances(@NotNull Set<VcsRootInstance> set) {
        ArrayList<VcsRootInstance> arrayList;
        try {
            if (set == null) {
                VcsModificationChecker.$$$reportNull$$$0(60);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VcsModificationChecker.a(illegalArgumentException);
        }
        HashMap hashMap = new HashMap();
        set.forEach(vcsRootInstance -> hashMap.put(vcsRootInstance, ((VcsRootInstanceEx)vcsRootInstance).getLastFinishChangesCollectingTime()));
        ArrayList<VcsRootInstance> arrayList2 = new ArrayList<VcsRootInstance>(set);
        try {
            arrayList2.sort((vcsRootInstance, vcsRootInstance2) -> {
                Long l;
                Long l2;
                Date date = (Date)hashMap.get(vcsRootInstance);
                Date date2 = (Date)hashMap.get(vcsRootInstance2);
                try {
                    l2 = date == null ? null : Long.valueOf(date.getTime());
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw VcsModificationChecker.a(illegalArgumentException);
                }
                Long l3 = l2;
                try {
                    Long l4 = l = date2 == null ? null : Long.valueOf(date2.getTime());
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw VcsModificationChecker.a(illegalArgumentException);
                }
                if (l3 == null) {
                    l3 = -1L;
                }
                if (l == null) {
                    l = -1L;
                }
                try {
                    if (l3 < l) {
                        return -1;
                    }
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw VcsModificationChecker.a(illegalArgumentException);
                }
                try {
                    if (l3 > l) {
                        return 1;
                    }
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw VcsModificationChecker.a(illegalArgumentException);
                }
                return Long.compare(vcsRootInstance.getId(), vcsRootInstance2.getId());
            });
            arrayList = arrayList2;
            if (arrayList == null) {
                VcsModificationChecker.$$$reportNull$$$0(61);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VcsModificationChecker.a(illegalArgumentException);
        }
        return arrayList;
    }

    @Used(value="tests")
    public void checkForModifications(@NotNull Collection<VcsRootInstance> collection, @NotNull OperationRequestor operationRequestor) {
        try {
            if (collection == null) {
                VcsModificationChecker.$$$reportNull$$$0(62);
            }
        }
        catch (Exception exception) {
            throw VcsModificationChecker.a(exception);
        }
        try {
            if (operationRequestor == null) {
                VcsModificationChecker.$$$reportNull$$$0(63);
            }
        }
        catch (Exception exception) {
            throw VcsModificationChecker.a(exception);
        }
        try {
            this.checkForModificationsAsync(collection, operationRequestor).get();
            this.ensureModificationChecksComplete();
        }
        catch (Exception exception) {
            ExceptionUtil.rethrowAsRuntimeException((Throwable)exception);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Used(value="tests")
    public void ensureModificationChecksComplete() {
        new /* Unavailable Anonymous Inner Class!! */;
        Object object = this.myLock;
        synchronized (object) {
            try {
                while (!this.isStale()) {
                    try {
                        this.myLock.wait(100L);
                    }
                    catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                }
                return;
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw VcsModificationChecker.a(illegalArgumentException);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean isStale() {
        Object object = this.myLock;
        synchronized (object) {
            boolean bl;
            block7: {
                block6: {
                    if (this.myInProgress.isEmpty()) {
                        try {
                            if (!this.myVcsRootsToCheck.isEmpty()) break block6;
                            bl = true;
                            break block7;
                        }
                        catch (IllegalArgumentException illegalArgumentException) {
                            throw VcsModificationChecker.a(illegalArgumentException);
                        }
                    }
                }
                bl = false;
            }
            return bl;
        }
    }

    public ExecutorService getExecutor() {
        return this.myExecutorServices.getPeriodicalChangesCollectorService();
    }

    private boolean collectTeamCitySettingsChanges() {
        return TeamCityProperties.getBooleanOrTrue((String)"teamcity.modificationChecker.collectTeamCitySettingsChanges");
    }

    @VisibleForTesting
    public long getTotalDelay(@NotNull OperationRequestor operationRequestor) {
        try {
            if (operationRequestor == null) {
                VcsModificationChecker.$$$reportNull$$$0(64);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VcsModificationChecker.a(illegalArgumentException);
        }
        Map map = this.myOperationsDelay.getOrDefault(operationRequestor, Collections.emptyMap());
        return map.values().stream().mapToLong(counter -> (long)counter.doubleValue()).sum();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private /* synthetic */ void lambda$checkVcsRootsInMultipleThreads$29(VcsRootInstance vcsRootInstance, Object object) {
        Object object2 = this.myLock;
        synchronized (object2) {
            this.myInProgress.remove(vcsRootInstance.getId(), object);
            this.myLock.notifyAll();
        }
    }

    static {
        boolean bl;
        try {
            bl = !VcsModificationChecker.class.desiredAssertionStatus();
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VcsModificationChecker.a(illegalArgumentException);
        }
        $assertionsDisabled = bl;
        LOG = Loggers.VCS;
        LOG_DIAG = Logger.getInstance((String)"jetbrains.buildServer.diagnostic.VcsModificationChecker");
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
