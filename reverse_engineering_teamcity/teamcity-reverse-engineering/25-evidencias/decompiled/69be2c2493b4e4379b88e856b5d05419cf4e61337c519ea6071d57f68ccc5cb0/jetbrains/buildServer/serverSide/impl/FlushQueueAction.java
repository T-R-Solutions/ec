/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Stopwatch
 *  jetbrains.buildServer.ExtensionsProvider
 *  jetbrains.buildServer.log.Loggers
 *  jetbrains.buildServer.metrics.Stoppable
 *  jetbrains.buildServer.serverSide.BuildAgentEx
 *  jetbrains.buildServer.serverSide.BuildAgentFactory
 *  jetbrains.buildServer.serverSide.BuildAgentManager
 *  jetbrains.buildServer.serverSide.BuildAgentManagerEx
 *  jetbrains.buildServer.serverSide.BuildAgentSpecialState
 *  jetbrains.buildServer.serverSide.BuildQueueEx
 *  jetbrains.buildServer.serverSide.BuildServerEx
 *  jetbrains.buildServer.serverSide.BuildServerListener
 *  jetbrains.buildServer.serverSide.QueuedBuildEx
 *  jetbrains.buildServer.serverSide.QueuedBuildsSettingsFreezer
 *  jetbrains.buildServer.serverSide.SBuildAgent
 *  jetbrains.buildServer.serverSide.TeamCityProperties
 *  jetbrains.buildServer.serverSide.agentTypes.AgentTypeManager
 *  jetbrains.buildServer.serverSide.buildDistribution.QueuedBuildInfo
 *  jetbrains.buildServer.serverSide.buildDistribution.SimpleWaitReason
 *  jetbrains.buildServer.serverSide.buildDistribution.WaitReason
 *  jetbrains.buildServer.serverSide.cleanup.ServerCleanupManager
 *  jetbrains.buildServer.serverSide.impl.AgentParametersProvidersProcessor
 *  jetbrains.buildServer.serverSide.impl.BuildEstimatorFeeder
 *  jetbrains.buildServer.serverSide.impl.ExecutorBuildStarter
 *  jetbrains.buildServer.serverSide.impl.FlushQueueAction$AbstractParallelTask
 *  jetbrains.buildServer.serverSide.impl.FlushQueueAction$AgentlessBuildStarterImpl
 *  jetbrains.buildServer.serverSide.impl.FlushQueueAction$ChangesAndSettingsFreezeTask
 *  jetbrains.buildServer.serverSide.impl.FlushQueueAction$CompatibleAgentsCalculatorTask
 *  jetbrains.buildServer.serverSide.impl.FlushQueueAction$CompositeBuildStarterImpl
 *  jetbrains.buildServer.serverSide.impl.FlushQueueAction$DistributionStats
 *  jetbrains.buildServer.serverSide.impl.FlushQueueAction$DistributionTimeTracker
 *  jetbrains.buildServer.serverSide.impl.QueuedBuildStarter
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.AgentlessBuildStarter
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.BuildDistributionCallbackDispatcher
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.BuildDistributor
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.BuildDistributor$QueuedBuildOptimizer
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.BuildDistributor$QueuedBuildTerminator
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.BuildDistributorInputEx
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.BuildQueueProcessingMetrics
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.BuildStarter
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.CompositeBuildStarter
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.DistributionDecision
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.DistributionResult
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.FlushQueueExecutor
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.InputData
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.QueuedBuildOptimizerImpl
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.QueuedBuildTerminatorImpl
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.StartingBuildsSupplier
 *  jetbrains.buildServer.serverSide.impl.virtualAgent.VirtualAgent
 *  jetbrains.buildServer.serverSide.impl.virtualAgent.VirtualAgentsManager
 *  jetbrains.buildServer.util.Disposable
 *  jetbrains.buildServer.util.EventDispatcher
 *  jetbrains.buildServer.util.NamedThreadFactory
 *  jetbrains.buildServer.util.SystemTimeService
 *  jetbrains.buildServer.util.TimeIntervalAction
 *  jetbrains.buildServer.util.TimePrinter
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package jetbrains.buildServer.serverSide.impl;

import com.google.common.base.Stopwatch;
import java.lang.invoke.LambdaMetafactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import jetbrains.buildServer.ExtensionsProvider;
import jetbrains.buildServer.log.Loggers;
import jetbrains.buildServer.metrics.Stoppable;
import jetbrains.buildServer.serverSide.BuildAgentEx;
import jetbrains.buildServer.serverSide.BuildAgentFactory;
import jetbrains.buildServer.serverSide.BuildAgentManager;
import jetbrains.buildServer.serverSide.BuildAgentManagerEx;
import jetbrains.buildServer.serverSide.BuildAgentSpecialState;
import jetbrains.buildServer.serverSide.BuildQueueEx;
import jetbrains.buildServer.serverSide.BuildServerEx;
import jetbrains.buildServer.serverSide.BuildServerListener;
import jetbrains.buildServer.serverSide.QueuedBuildEx;
import jetbrains.buildServer.serverSide.QueuedBuildsSettingsFreezer;
import jetbrains.buildServer.serverSide.SBuildAgent;
import jetbrains.buildServer.serverSide.TeamCityProperties;
import jetbrains.buildServer.serverSide.agentTypes.AgentTypeManager;
import jetbrains.buildServer.serverSide.buildDistribution.QueuedBuildInfo;
import jetbrains.buildServer.serverSide.buildDistribution.SimpleWaitReason;
import jetbrains.buildServer.serverSide.buildDistribution.WaitReason;
import jetbrains.buildServer.serverSide.cleanup.ServerCleanupManager;
import jetbrains.buildServer.serverSide.impl.AgentParametersProvidersProcessor;
import jetbrains.buildServer.serverSide.impl.BuildEstimatorFeeder;
import jetbrains.buildServer.serverSide.impl.ExecutorBuildStarter;
import jetbrains.buildServer.serverSide.impl.FlushQueueAction;
import jetbrains.buildServer.serverSide.impl.QueuedBuildStarter;
import jetbrains.buildServer.serverSide.impl.buildDistribution.AgentlessBuildStarter;
import jetbrains.buildServer.serverSide.impl.buildDistribution.BuildDistributionCallbackDispatcher;
import jetbrains.buildServer.serverSide.impl.buildDistribution.BuildDistributor;
import jetbrains.buildServer.serverSide.impl.buildDistribution.BuildDistributorInputEx;
import jetbrains.buildServer.serverSide.impl.buildDistribution.BuildQueueProcessingMetrics;
import jetbrains.buildServer.serverSide.impl.buildDistribution.BuildStarter;
import jetbrains.buildServer.serverSide.impl.buildDistribution.CompositeBuildStarter;
import jetbrains.buildServer.serverSide.impl.buildDistribution.DistributionDecision;
import jetbrains.buildServer.serverSide.impl.buildDistribution.DistributionResult;
import jetbrains.buildServer.serverSide.impl.buildDistribution.FlushQueueExecutor;
import jetbrains.buildServer.serverSide.impl.buildDistribution.InputData;
import jetbrains.buildServer.serverSide.impl.buildDistribution.QueuedBuildOptimizerImpl;
import jetbrains.buildServer.serverSide.impl.buildDistribution.QueuedBuildTerminatorImpl;
import jetbrains.buildServer.serverSide.impl.buildDistribution.StartingBuildsSupplier;
import jetbrains.buildServer.serverSide.impl.virtualAgent.VirtualAgent;
import jetbrains.buildServer.serverSide.impl.virtualAgent.VirtualAgentsManager;
import jetbrains.buildServer.util.Disposable;
import jetbrains.buildServer.util.EventDispatcher;
import jetbrains.buildServer.util.NamedThreadFactory;
import jetbrains.buildServer.util.SystemTimeService;
import jetbrains.buildServer.util.TimeIntervalAction;
import jetbrains.buildServer.util.TimePrinter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Exception performing whole class analysis ignored.
 */
public class FlushQueueAction {
    public static final String SCHEDULE_FREEZE_AND_CHECKING_FOR_CHANGES_IN_PARALLEL_PROP = "teamcity.flushQueue.scheduleFreezeSettingsInParallel";
    @NotNull
    private final BuildServerEx myBuildServerEx;
    @NotNull
    private final BuildDistributor myBuildDistributor;
    @NotNull
    private final VirtualAgentsManager myVirualAgents;
    @NotNull
    private final BuildEstimatorFeeder myFeeder;
    @NotNull
    private final ServerCleanupManager myServerCleanupManager;
    @NotNull
    private final BuildDistributionCallbackDispatcher myDistributionListenerDispatcher;
    @NotNull
    private final BuildQueueEx myQueue;
    @NotNull
    private final QueuedBuildsSettingsFreezer myFreezer;
    @NotNull
    private final BuildAgentFactory myBuildAgentFactory;
    @NotNull
    private final AgentParametersProvidersProcessor myAgentParametersProvidersProcessor;
    @NotNull
    private final QueuedBuildStarter myQueuedBuildStarter;
    @NotNull
    private final DistributionTimeTracker myDistributionTimeTracker;
    @NotNull
    private final QueuedBuildOptimizerImpl myQueuedBuildOptimizer;
    @NotNull
    private final QueuedBuildTerminatorImpl myQueuedBuildTerminator;
    @NotNull
    private final TimeIntervalAction myLogQueueStats;
    @NotNull
    private final BuildQueueProcessingMetrics myBuildQueueProcessingMetrics;
    @NotNull
    private final FlushQueueExecutor myFlushQueueExecutor;
    private volatile AbstractParallelTask myCompatibleAgentsTask;
    private volatile AbstractParallelTask myChangesAndSettingsFreezeTask;

    public FlushQueueAction(@NotNull BuildDistributor buildDistributor, @NotNull BuildAgentManagerEx buildAgentManagerEx, @NotNull VirtualAgentsManager virtualAgentsManager, @NotNull BuildServerEx buildServerEx, @NotNull BuildEstimatorFeeder buildEstimatorFeeder, @NotNull ServerCleanupManager serverCleanupManager, @NotNull BuildDistributionCallbackDispatcher buildDistributionCallbackDispatcher, @NotNull BuildQueueEx buildQueueEx, @NotNull QueuedBuildsSettingsFreezer queuedBuildsSettingsFreezer, @NotNull BuildAgentFactory buildAgentFactory, @NotNull BuildQueueProcessingMetrics buildQueueProcessingMetrics, @NotNull AgentTypeManager agentTypeManager, @NotNull EventDispatcher<BuildServerListener> eventDispatcher, @NotNull FlushQueueExecutor flushQueueExecutor) {
        if (buildDistributor == null) {
            FlushQueueAction.$$$reportNull$$$0(0);
        }
        if (buildAgentManagerEx == null) {
            FlushQueueAction.$$$reportNull$$$0(1);
        }
        if (virtualAgentsManager == null) {
            FlushQueueAction.$$$reportNull$$$0(2);
        }
        if (buildServerEx == null) {
            FlushQueueAction.$$$reportNull$$$0(3);
        }
        if (buildEstimatorFeeder == null) {
            FlushQueueAction.$$$reportNull$$$0(4);
        }
        if (serverCleanupManager == null) {
            FlushQueueAction.$$$reportNull$$$0(5);
        }
        if (buildDistributionCallbackDispatcher == null) {
            FlushQueueAction.$$$reportNull$$$0(6);
        }
        if (buildQueueEx == null) {
            FlushQueueAction.$$$reportNull$$$0(7);
        }
        if (queuedBuildsSettingsFreezer == null) {
            FlushQueueAction.$$$reportNull$$$0(8);
        }
        if (buildAgentFactory == null) {
            FlushQueueAction.$$$reportNull$$$0(9);
        }
        if (buildQueueProcessingMetrics == null) {
            FlushQueueAction.$$$reportNull$$$0(10);
        }
        if (agentTypeManager == null) {
            FlushQueueAction.$$$reportNull$$$0(11);
        }
        if (eventDispatcher == null) {
            FlushQueueAction.$$$reportNull$$$0(12);
        }
        if (flushQueueExecutor == null) {
            FlushQueueAction.$$$reportNull$$$0(13);
        }
        this.myBuildDistributor = buildDistributor;
        this.myVirualAgents = virtualAgentsManager;
        this.myBuildServerEx = buildServerEx;
        this.myFeeder = buildEstimatorFeeder;
        this.myServerCleanupManager = serverCleanupManager;
        this.myDistributionListenerDispatcher = buildDistributionCallbackDispatcher;
        this.myQueue = buildQueueEx;
        this.myFreezer = queuedBuildsSettingsFreezer;
        this.myBuildAgentFactory = buildAgentFactory;
        this.myBuildQueueProcessingMetrics = buildQueueProcessingMetrics;
        this.myFlushQueueExecutor = flushQueueExecutor;
        this.myAgentParametersProvidersProcessor = new AgentParametersProvidersProcessor(SystemTimeService.getInstance(), agentTypeManager);
        this.myQueuedBuildStarter = new QueuedBuildStarter(() -> this.myBuildServerEx.getTimeService(), (BuildAgentManager)buildAgentManagerEx, buildQueueEx, this.myBuildQueueProcessingMetrics.getStartNewBuildsInsideQueueLoopMetric(), this.myBuildQueueProcessingMetrics.getStartNewBuildsOutsideQueueLoopMetric(), this.myFlushQueueExecutor.getParallelTasksExecutor());
        this.myQueuedBuildOptimizer = new QueuedBuildOptimizerImpl(this.myQueue, this.myFlushQueueExecutor.getParallelTasksExecutor(), eventDispatcher, this.myBuildQueueProcessingMetrics.getBuildsOptimizationTimeMetric());
        this.myQueuedBuildTerminator = new QueuedBuildTerminatorImpl(this.myFlushQueueExecutor.getParallelTasksExecutor(), this.myQueue);
        this.myDistributionTimeTracker = new DistributionTimeTracker();
        this.myLogQueueStats = new TimeIntervalAction(() -> TeamCityProperties.getLong((String)"teamcity.flushQueue.logQueueProcessingStats.interval", (long)30000L));
        this.myBuildServerEx.addListener((BuildServerListener)new /* Unavailable Anonymous Inner Class!! */);
    }

    public boolean flushQueue() {
        boolean bl;
        block5: {
            block4: {
                AtomicInteger atomicInteger = new AtomicInteger();
                this.myDistributionListenerDispatcher.distributionStarted();
                boolean bl2 = this.myServerCleanupManager.executeWithInactiveCleanup(this.flushQueueTask(atomicInteger), false);
                try {
                    try {
                        this.myDistributionListenerDispatcher.distributionFinished();
                        if (!bl2 || atomicInteger.get() == 0) break block4;
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        throw FlushQueueAction.a(illegalArgumentException);
                    }
                    bl = true;
                    break block5;
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw FlushQueueAction.a(illegalArgumentException);
                }
            }
            bl = false;
        }
        return bl;
    }

    @NotNull
    private Runnable flushQueueTask(@NotNull AtomicInteger atomicInteger) {
        Runnable runnable;
        try {
            if (atomicInteger == null) {
                FlushQueueAction.$$$reportNull$$$0(14);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw FlushQueueAction.a(illegalArgumentException);
        }
        try {
            runnable = () -> this.lambda$flushQueueTask$3(atomicInteger);
            if (runnable == null) {
                FlushQueueAction.$$$reportNull$$$0(15);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw FlushQueueAction.a(illegalArgumentException);
        }
        return runnable;
    }

    @NotNull
    private Disposable metricsDiff() {
        long l = (long)this.myBuildQueueProcessingMetrics.getTotalQueueProcessingMetric().doubleValue();
        long l2 = (long)this.myBuildQueueProcessingMetrics.getCompatibleAgentsMetric(false).doubleValue();
        long l3 = (long)this.myBuildQueueProcessingMetrics.getCompatibleVirtualAgentsMetric(false).doubleValue();
        long l4 = (long)this.myBuildQueueProcessingMetrics.getBuildsOptimizationTimeMetric().doubleValue();
        long l5 = (long)this.myBuildQueueProcessingMetrics.getPreconditionsTimeMetric(false).doubleValue();
        long l6 = (long)this.myBuildQueueProcessingMetrics.getStartNewBuildsInsideQueueLoopMetric().doubleValue();
        long l7 = (long)this.myBuildQueueProcessingMetrics.getOtherActivitiesMetric(false).doubleValue();
        return new /* Unavailable Anonymous Inner Class!! */;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void scheduleSettingsFreezeTask() {
        block10: {
            block8: {
                block9: {
                    try {
                        if (!FlushQueueAction.isSchedulingSettingsFreezeInParallelEnabled()) break block8;
                        if (this.myChangesAndSettingsFreezeTask == null) break block9;
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        throw FlushQueueAction.a(illegalArgumentException);
                    }
                    AbstractParallelTask abstractParallelTask = this.myChangesAndSettingsFreezeTask;
                    synchronized (abstractParallelTask) {
                        this.myChangesAndSettingsFreezeTask.notify();
                    }
                    return;
                }
                ExecutorService executorService = this.myFlushQueueExecutor.getParallelTasksExecutor();
                this.myChangesAndSettingsFreezeTask = new ChangesAndSettingsFreezeTask(this, executorService);
                this.myChangesAndSettingsFreezeTask.submit(executorService);
                break block10;
            }
            try {
                if (this.myChangesAndSettingsFreezeTask != null) {
                    this.myChangesAndSettingsFreezeTask.cancel();
                    this.myChangesAndSettingsFreezeTask = null;
                }
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw FlushQueueAction.a(illegalArgumentException);
            }
        }
    }

    private void scheduleCompatibleAgentsCalculationTask() {
        block4: {
            try {
                try {
                    if (this.myCompatibleAgentsTask == null || this.myCompatibleAgentsTask.isFinished()) break block4;
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw FlushQueueAction.a(illegalArgumentException);
                }
                this.myCompatibleAgentsTask.wakeUp();
                return;
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw FlushQueueAction.a(illegalArgumentException);
            }
        }
        ExecutorService executorService = this.myFlushQueueExecutor.getParallelTasksExecutor();
        this.myCompatibleAgentsTask = new CompatibleAgentsCalculatorTask(this.myFeeder, this.myVirualAgents, executorService);
        this.myCompatibleAgentsTask.submit(executorService);
    }

    @NotNull
    private BuildDistributorInputEx createDistributorInputData() {
        InputData inputData;
        InputData inputData2 = this.myFeeder.makeInputForDistributor();
        Map map = this.myQueuedBuildStarter.getStartingBuilds();
        if (!map.isEmpty()) {
            ArrayList<QueuedBuildInfo> arrayList = new ArrayList<QueuedBuildInfo>();
            for (QueuedBuildInfo queuedBuildInfo : inputData2.getQueue()) {
                try {
                    if (map.containsKey(queuedBuildInfo)) {
                        continue;
                    }
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw FlushQueueAction.a(illegalArgumentException);
                }
                arrayList.add(queuedBuildInfo);
            }
            inputData2 = new InputData(arrayList, inputData2.getRunningBuilds(), inputData2.getAgents());
            inputData2.setDistributedBuilds(map);
        }
        try {
            inputData2.setAverageDistributionTime(Double.valueOf(this.myDistributionTimeTracker.getAverage() / 1000.0).intValue());
            inputData = inputData2;
            if (inputData == null) {
                FlushQueueAction.$$$reportNull$$$0(16);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw FlushQueueAction.a(illegalArgumentException);
        }
        return inputData;
    }

    private void performQueuedBuildChainsMergeOptimization() {
        try (Stoppable stoppable = this.myBuildQueueProcessingMetrics.getBuildsOptimizationTimeMetric().startMsecsTimer();){
            Set set = this.myQueuedBuildStarter.getStartingBuilds().keySet();
            this.myQueue.mergeBuilds(sQueuedBuild -> {
                boolean bl;
                try {
                    bl = !set.contains(sQueuedBuild);
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw FlushQueueAction.a(illegalArgumentException);
                }
                return bl;
            });
        }
    }

    private synchronized void scheduleCheckingForChangesAndSettingsFreeze(@NotNull List<QueuedBuildEx> list) {
        try {
            if (list == null) {
                FlushQueueAction.$$$reportNull$$$0(17);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw FlushQueueAction.a(illegalArgumentException);
        }
        try {
            if (list.isEmpty()) {
                return;
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw FlushQueueAction.a(illegalArgumentException);
        }
        ArrayList<QueuedBuildEx> arrayList = new ArrayList<QueuedBuildEx>();
        for (QueuedBuildInfo object : list) {
            try {
                if (!(object instanceof QueuedBuildEx)) continue;
                arrayList.add((QueuedBuildEx)object);
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw FlushQueueAction.a(illegalArgumentException);
            }
        }
        Set set = this.myFreezer.getInProgressPromotions();
        this.myQueue.scheduleCheckingForChangesForQueuedBuilds((Collection)arrayList.stream().filter(queuedBuildEx -> {
            boolean bl;
            try {
                bl = !set.contains(queuedBuildEx.getBuildPromotion());
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw FlushQueueAction.a(illegalArgumentException);
            }
            return bl;
        }).collect(Collectors.toList()));
        for (QueuedBuildEx queuedBuildEx2 : arrayList) {
            this.myFreezer.scheduleSettingsFreeze(queuedBuildEx2);
        }
    }

    @NotNull
    private DistributionResult<SBuildAgent> doDistribute(@NotNull BuildStarter<SBuildAgent> buildStarter, @Nullable BuildStarter<VirtualAgent> buildStarter2, @Nullable ExecutorBuildStarter executorBuildStarter, @NotNull CompositeBuildStarter compositeBuildStarter, @NotNull AgentlessBuildStarter agentlessBuildStarter, @NotNull BuildDistributor.QueuedBuildOptimizer queuedBuildOptimizer, @NotNull BuildDistributor.QueuedBuildTerminator queuedBuildTerminator, @NotNull BuildDistributorInputEx buildDistributorInputEx) {
        DistributionResult distributionResult;
        try {
            if (buildStarter == null) {
                FlushQueueAction.$$$reportNull$$$0(18);
            }
        }
        catch (Exception exception) {
            throw FlushQueueAction.a(exception);
        }
        try {
            if (compositeBuildStarter == null) {
                FlushQueueAction.$$$reportNull$$$0(19);
            }
        }
        catch (Exception exception) {
            throw FlushQueueAction.a(exception);
        }
        try {
            if (agentlessBuildStarter == null) {
                FlushQueueAction.$$$reportNull$$$0(20);
            }
        }
        catch (Exception exception) {
            throw FlushQueueAction.a(exception);
        }
        try {
            if (queuedBuildOptimizer == null) {
                FlushQueueAction.$$$reportNull$$$0(21);
            }
        }
        catch (Exception exception) {
            throw FlushQueueAction.a(exception);
        }
        try {
            if (queuedBuildTerminator == null) {
                FlushQueueAction.$$$reportNull$$$0(22);
            }
        }
        catch (Exception exception) {
            throw FlushQueueAction.a(exception);
        }
        try {
            if (buildDistributorInputEx == null) {
                FlushQueueAction.$$$reportNull$$$0(23);
            }
        }
        catch (Exception exception) {
            throw FlushQueueAction.a(exception);
        }
        try {
            distributionResult = (DistributionResult)NamedThreadFactory.executeWithNewThreadName((String)("Assigning queued builds to agents (queue size: " + buildDistributorInputEx.getQueue().size() + ")"), () -> {
                try (Stoppable stoppable = this.myBuildQueueProcessingMetrics.getOtherActivitiesMetric(false).startMsecsTimer();){
                    HashSet hashSet = new HashSet(buildDistributorInputEx.getDistributedBuilds().values());
                    buildDistributorInputEx.getAgents().forEach(sBuildAgent -> {
                        try {
                            if (hashSet.contains(sBuildAgent)) {
                                return;
                            }
                        }
                        catch (IllegalArgumentException illegalArgumentException) {
                            throw FlushQueueAction.a(illegalArgumentException);
                        }
                        ((BuildAgentEx)sBuildAgent).clearSpecialState(BuildAgentSpecialState.willRunBuild);
                    });
                }
                return this.myBuildDistributor.assignBuildsToAgents(buildDistributorInputEx, null, buildStarter, buildStarter2, (BuildStarter)executorBuildStarter, (arg_0, arg_1) -> ((BuildDistributionCallbackDispatcher)this.myDistributionListenerDispatcher).waitReasonSet(arg_0, arg_1), compositeBuildStarter, agentlessBuildStarter, queuedBuildOptimizer, queuedBuildTerminator, false);
            });
        }
        catch (Exception exception) {
            Loggers.SERVER.error("Failed to assign queued builds to agents", (Throwable)exception);
            return new DistributionResult();
        }
        if (distributionResult == null) {
            FlushQueueAction.$$$reportNull$$$0(24);
        }
        return distributionResult;
    }

    @NotNull
    private static DistributionDecision startAgentlessBuild(@NotNull QueuedBuildStarter queuedBuildStarter, @NotNull QueuedBuildEx queuedBuildEx, @Nullable SBuildAgent sBuildAgent) {
        String string;
        SimpleWaitReason simpleWaitReason;
        SimpleWaitReason simpleWaitReason2;
        boolean bl;
        DistributionDecision distributionDecision;
        DistributionDecision distributionDecision2;
        try {
            if (queuedBuildStarter == null) {
                FlushQueueAction.$$$reportNull$$$0(25);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw FlushQueueAction.a(illegalArgumentException);
        }
        try {
            if (queuedBuildEx == null) {
                FlushQueueAction.$$$reportNull$$$0(26);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw FlushQueueAction.a(illegalArgumentException);
        }
        boolean bl2 = queuedBuildStarter.startAgentLessBuild(queuedBuildEx, sBuildAgent);
        try {
            SimpleWaitReason simpleWaitReason3;
            DistributionDecision distributionDecision3;
            distributionDecision2 = distributionDecision3;
            distributionDecision = distributionDecision3;
            bl = bl2;
            simpleWaitReason2 = simpleWaitReason3;
            simpleWaitReason = simpleWaitReason3;
            string = queuedBuildEx.getBuildPromotion().isCompositeBuild() ? "Preparing the composite build for a start" : "Preparing the build for a start by an executor";
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw FlushQueueAction.a(illegalArgumentException);
        }
        simpleWaitReason2(string);
        distributionDecision2(bl, (WaitReason)simpleWaitReason);
        return distributionDecision;
    }

    public static boolean isSchedulingSettingsFreezeInParallelEnabled() {
        return TeamCityProperties.getBooleanOrTrue((String)"teamcity.flushQueue.scheduleFreezeSettingsInParallel");
    }

    @NotNull
    public StartingBuildsSupplier getStartingBuildsSupplier() {
        QueuedBuildStarter queuedBuildStarter;
        try {
            queuedBuildStarter = this.myQueuedBuildStarter;
            if (queuedBuildStarter == null) {
                FlushQueueAction.$$$reportNull$$$0(27);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw FlushQueueAction.a(illegalArgumentException);
        }
        return queuedBuildStarter;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Unable to fully structure code
     */
    private /* synthetic */ void lambda$flushQueueTask$3(AtomicInteger var1_1) {
        var2_2 = Stopwatch.createStarted();
        var5_3 = this.metricsDiff();
        var6_4 = NamedThreadFactory.patchThreadName((String)"Running preparation tasks before assigning queued builds to agents");
        try {
            var7_5 = this.myBuildQueueProcessingMetrics.getOtherActivitiesMetric(false).startMsecsTimer();
            try {
                block44: {
                    block38: {
                        block40: {
                            block39: {
                                this.myFlushQueueExecutor.adjustParallelTasksExecutorThreadPoolSize();
                                this.scheduleCompatibleAgentsCalculationTask();
                                this.scheduleSettingsFreezeTask();
                                this.myAgentParametersProvidersProcessor.process();
                                var8_6 = this.myBuildServerEx.getLicensingPolicy().getAgentsLicensesLeft();
                                if (var8_6 == 0) break block38;
                                try {
                                    block43: {
                                        v0 = v1;
                                        v2 = v1;
                                        v3 = this.myVirualAgents;
                                        if (var8_6 != -1) break block39;
                                        break block43;
                                        catch (Throwable v4) {
                                            throw FlushQueueAction.a(v4);
                                        }
                                    }
                                    v5 = null;
                                    break block40;
                                }
                                catch (Throwable v6) {
                                    throw FlushQueueAction.a(v6);
                                }
                            }
                            v5 = var8_6;
                        }
                        v0(v3, v5, (ExtensionsProvider)this.myBuildServerEx, this.myAgentParametersProvidersProcessor);
                        break block44;
                    }
                    v2 = null;
                }
                var3_9 = v2;
                try {
                    v7 = var8_6 != 0 ? new ExecutorBuildStarter() : null;
                }
                catch (Throwable v8) {
                    throw FlushQueueAction.a(v8);
                }
                var4_10 = v7;
                try {
                    if (var3_9 != null) {
                        var3_9.fetchPropertiesIfNotKnown();
                    }
                }
                catch (Throwable v9) {
                    throw FlushQueueAction.a(v9);
                }
            }
            catch (Throwable var8_7) {
                if (var7_5 != null) {
                    try {
                        var7_5.close();
                    }
                    catch (Throwable var9_11) {
                        var8_7.addSuppressed(var9_11);
                    }
                }
                throw var8_7;
            }
            try {
                if (var7_5 == null) ** GOTO lbl61
                var7_5.close();
            }
            catch (Throwable v10) {
                throw FlushQueueAction.a(v10);
            }
lbl61:
            // 2 sources

            this.performQueuedBuildChainsMergeOptimization();
        }
        finally {
            var6_4.dispose();
        }
        var8_8 = this.myBuildQueueProcessingMetrics.getOtherActivitiesMetric(false).startMsecsTimer();
        try {
            var7_5 = this.createDistributorInputData();
            try {
                if (!FlushQueueAction.isSchedulingSettingsFreezeInParallelEnabled()) {
                    this.scheduleCheckingForChangesAndSettingsFreeze(this.myQueue.getPersistedItemsEx());
                }
            }
            catch (Throwable v11) {
                throw FlushQueueAction.a(v11);
            }
        }
        catch (Throwable var9_12) {
            if (var8_8 != null) {
                try {
                    var8_8.close();
                }
                catch (Throwable var10_15) {
                    var9_12.addSuppressed(var10_15);
                }
            }
            throw var9_12;
        }
        try {
            if (var8_8 == null) ** GOTO lbl90
            var8_8.close();
        }
        catch (Throwable v12) {
            throw FlushQueueAction.a(v12);
        }
lbl90:
        // 2 sources

        var8_8 = this.doDistribute((BuildStarter<SBuildAgent>)this.myQueuedBuildStarter, (BuildStarter<VirtualAgent>)var3_9, var4_10, (CompositeBuildStarter)new CompositeBuildStarterImpl(this.myQueuedBuildStarter), (AgentlessBuildStarter)new AgentlessBuildStarterImpl(this.myQueuedBuildStarter, this.myBuildAgentFactory), (BuildDistributor.QueuedBuildOptimizer)this.myQueuedBuildOptimizer, (BuildDistributor.QueuedBuildTerminator)this.myQueuedBuildTerminator, (BuildDistributorInputEx)var7_5);
        var10_16 = this.myBuildQueueProcessingMetrics.getOtherActivitiesMetric(false).startMsecsTimer();
        try {
            var9_13 = new DistributionStats(var8_8.getDistributedBuilds(), (BuildDistributorInputEx)var7_5, this.myVirualAgents);
            var1_1.set(var9_13.myBuildsAssignedToIdleAgents);
        }
        finally {
            if (var10_16 != null) {
                var10_16.close();
            }
        }
        var2_2.stop();
        var10_17 = var2_2.elapsed(TimeUnit.MILLISECONDS);
        try {
            this.myBuildQueueProcessingMetrics.getTotalQueueProcessingMetric().increment((double)var10_17);
            this.myDistributionTimeTracker.addPoint(var10_17);
            if (var9_13.myTotalDistributedBuilds > 0) {
                this.myLogQueueStats.executeCustomAction((Runnable)LambdaMetafactory.metafactory(null, null, null, ()V, lambda$flushQueueTask$2(long jetbrains.buildServer.serverSide.impl.FlushQueueAction$DistributionStats jetbrains.buildServer.util.Disposable ), ()V)((long)var10_17, (DistributionStats)var9_13, (Disposable)var5_3));
            }
        }
        catch (Throwable v13) {
            throw FlushQueueAction.a(v13);
        }
    }

    private static /* synthetic */ void lambda$flushQueueTask$2(long l, DistributionStats distributionStats, Disposable disposable) {
        Loggers.SERVER.info("Build queue processing finished in " + TimePrinter.formatMillis((long)l) + ", " + distributionStats.asString());
        disposable.dispose();
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
