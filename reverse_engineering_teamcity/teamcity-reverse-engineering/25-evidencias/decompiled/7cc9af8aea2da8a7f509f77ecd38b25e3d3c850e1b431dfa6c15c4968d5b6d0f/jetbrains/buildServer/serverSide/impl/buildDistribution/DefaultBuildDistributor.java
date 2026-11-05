/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.intellij.openapi.util.Pair
 *  jetbrains.buildServer.ExtensionHolder
 *  jetbrains.buildServer.log.Loggers
 *  jetbrains.buildServer.metrics.ServerMetrics
 *  jetbrains.buildServer.metrics.Stoppable
 *  jetbrains.buildServer.serverSide.SBuildAgent
 *  jetbrains.buildServer.serverSide.TeamCityProperties
 *  jetbrains.buildServer.serverSide.agentTypes.AgentTypeFinder
 *  jetbrains.buildServer.serverSide.agentless.AgentlessBuildExecutionManager
 *  jetbrains.buildServer.serverSide.buildDistribution.BuildPromotionInfo
 *  jetbrains.buildServer.serverSide.buildDistribution.QueuedBuildInfo
 *  jetbrains.buildServer.serverSide.buildDistribution.WaitReason
 *  jetbrains.buildServer.serverSide.impl.LogUtil
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.AgentlessBuildStarter
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.BuildDistributor
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.BuildDistributor$QueuedBuildOptimizer
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.BuildDistributor$QueuedBuildTerminator
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.BuildDistributorInputEx
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.BuildQueueDistributionProcessor
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.BuildQueueProcessingMetrics
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.BuildStarter
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.CompositeBuildStarter
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.ConnectedAgentsDistributor
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.DistributionDecision
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.DistributionResult
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.Distributor
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.DistributorState
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.ExecutorDistributor
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.SetWaitReasonCallback
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.StandardWaitReasons
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.VirtualAgentDistributor
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.agentsComparators.BuildAgentsComparatorProvider
 *  jetbrains.buildServer.serverSide.impl.executors.ExecutorCompatibilityManager
 *  jetbrains.buildServer.serverSide.impl.executors.ExecutorDescription
 *  jetbrains.buildServer.serverSide.impl.virtualAgent.VirtualAgent
 *  jetbrains.buildServer.serverSide.impl.virtualAgent.VirtualAgentsManager
 *  jetbrains.buildServer.util.TimeIntervalAction
 *  jetbrains.buildServer.util.TimeService
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package jetbrains.buildServer.serverSide.impl.buildDistribution;

import com.intellij.openapi.util.Pair;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jetbrains.buildServer.ExtensionHolder;
import jetbrains.buildServer.log.Loggers;
import jetbrains.buildServer.metrics.ServerMetrics;
import jetbrains.buildServer.metrics.Stoppable;
import jetbrains.buildServer.serverSide.SBuildAgent;
import jetbrains.buildServer.serverSide.TeamCityProperties;
import jetbrains.buildServer.serverSide.agentTypes.AgentTypeFinder;
import jetbrains.buildServer.serverSide.agentless.AgentlessBuildExecutionManager;
import jetbrains.buildServer.serverSide.buildDistribution.BuildPromotionInfo;
import jetbrains.buildServer.serverSide.buildDistribution.QueuedBuildInfo;
import jetbrains.buildServer.serverSide.buildDistribution.WaitReason;
import jetbrains.buildServer.serverSide.impl.LogUtil;
import jetbrains.buildServer.serverSide.impl.buildDistribution.AgentlessBuildStarter;
import jetbrains.buildServer.serverSide.impl.buildDistribution.BuildDistributor;
import jetbrains.buildServer.serverSide.impl.buildDistribution.BuildDistributorInputEx;
import jetbrains.buildServer.serverSide.impl.buildDistribution.BuildQueueDistributionProcessor;
import jetbrains.buildServer.serverSide.impl.buildDistribution.BuildQueueProcessingMetrics;
import jetbrains.buildServer.serverSide.impl.buildDistribution.BuildStarter;
import jetbrains.buildServer.serverSide.impl.buildDistribution.CompositeBuildStarter;
import jetbrains.buildServer.serverSide.impl.buildDistribution.ConnectedAgentsDistributor;
import jetbrains.buildServer.serverSide.impl.buildDistribution.DistributionDecision;
import jetbrains.buildServer.serverSide.impl.buildDistribution.DistributionResult;
import jetbrains.buildServer.serverSide.impl.buildDistribution.Distributor;
import jetbrains.buildServer.serverSide.impl.buildDistribution.DistributorState;
import jetbrains.buildServer.serverSide.impl.buildDistribution.ExecutorDistributor;
import jetbrains.buildServer.serverSide.impl.buildDistribution.SetWaitReasonCallback;
import jetbrains.buildServer.serverSide.impl.buildDistribution.StandardWaitReasons;
import jetbrains.buildServer.serverSide.impl.buildDistribution.VirtualAgentDistributor;
import jetbrains.buildServer.serverSide.impl.buildDistribution.agentsComparators.BuildAgentsComparatorProvider;
import jetbrains.buildServer.serverSide.impl.executors.ExecutorCompatibilityManager;
import jetbrains.buildServer.serverSide.impl.executors.ExecutorDescription;
import jetbrains.buildServer.serverSide.impl.virtualAgent.VirtualAgent;
import jetbrains.buildServer.serverSide.impl.virtualAgent.VirtualAgentsManager;
import jetbrains.buildServer.util.TimeIntervalAction;
import jetbrains.buildServer.util.TimeService;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Exception performing whole class analysis ignored.
 */
public class DefaultBuildDistributor
implements BuildDistributor {
    public static final String TEAMCITY_BUILD_DISTRIBUTION_EXECUTOR = "teamcity.queueDistribution.enableExecutor";
    @NotNull
    private final ExtensionHolder myExtensionHolder;
    @NotNull
    private final VirtualAgentsManager myVirualAgents;
    @NotNull
    private final BuildAgentsComparatorProvider myAgentsComparatorProvider;
    @NotNull
    private final ExecutorCompatibilityManager myExecutorsManager;
    @NotNull
    private final AgentlessBuildExecutionManager myAgentlessBuildExecutionManager;
    @NotNull
    private final TimeIntervalAction myCheckTopologicalOrderAction;
    private final BuildQueueProcessingMetrics myBuildQueueProcessingMetrics;
    private final AgentTypeFinder myAgentTypeFinder;
    private static final String CHECK_TOPOLOGICAL_ORDER_INTERVAL = "teamcity.queueDistribution.checkTopologicalOrderIntervalSec";

    public DefaultBuildDistributor(@NotNull ExtensionHolder extensionHolder, @NotNull TimeService timeService, @NotNull VirtualAgentsManager virtualAgentsManager, @NotNull BuildAgentsComparatorProvider buildAgentsComparatorProvider, @NotNull ExecutorCompatibilityManager executorCompatibilityManager, @NotNull AgentlessBuildExecutionManager agentlessBuildExecutionManager, @NotNull BuildQueueProcessingMetrics buildQueueProcessingMetrics, @NotNull AgentTypeFinder agentTypeFinder) {
        if (extensionHolder == null) {
            DefaultBuildDistributor.$$$reportNull$$$0(0);
        }
        if (timeService == null) {
            DefaultBuildDistributor.$$$reportNull$$$0(1);
        }
        if (virtualAgentsManager == null) {
            DefaultBuildDistributor.$$$reportNull$$$0(2);
        }
        if (buildAgentsComparatorProvider == null) {
            DefaultBuildDistributor.$$$reportNull$$$0(3);
        }
        if (executorCompatibilityManager == null) {
            DefaultBuildDistributor.$$$reportNull$$$0(4);
        }
        if (agentlessBuildExecutionManager == null) {
            DefaultBuildDistributor.$$$reportNull$$$0(5);
        }
        if (buildQueueProcessingMetrics == null) {
            DefaultBuildDistributor.$$$reportNull$$$0(6);
        }
        if (agentTypeFinder == null) {
            DefaultBuildDistributor.$$$reportNull$$$0(7);
        }
        this.myExtensionHolder = extensionHolder;
        this.myVirualAgents = virtualAgentsManager;
        this.myExecutorsManager = executorCompatibilityManager;
        this.myAgentsComparatorProvider = buildAgentsComparatorProvider;
        this.myAgentlessBuildExecutionManager = agentlessBuildExecutionManager;
        this.myBuildQueueProcessingMetrics = buildQueueProcessingMetrics;
        this.myAgentTypeFinder = agentTypeFinder;
        this.myCheckTopologicalOrderAction = new TimeIntervalAction(TeamCityProperties.getLong((String)"teamcity.queueDistribution.checkTopologicalOrderIntervalSec", (long)300L) * 1000L);
    }

    protected DefaultBuildDistributor(@NotNull ExtensionHolder extensionHolder, @NotNull TimeService timeService, @NotNull VirtualAgentsManager virtualAgentsManager, @NotNull BuildAgentsComparatorProvider buildAgentsComparatorProvider, @NotNull ServerMetrics serverMetrics, @NotNull ExecutorCompatibilityManager executorCompatibilityManager, @NotNull AgentlessBuildExecutionManager agentlessBuildExecutionManager, @NotNull AgentTypeFinder agentTypeFinder) {
        if (extensionHolder == null) {
            DefaultBuildDistributor.$$$reportNull$$$0(8);
        }
        if (timeService == null) {
            DefaultBuildDistributor.$$$reportNull$$$0(9);
        }
        if (virtualAgentsManager == null) {
            DefaultBuildDistributor.$$$reportNull$$$0(10);
        }
        if (buildAgentsComparatorProvider == null) {
            DefaultBuildDistributor.$$$reportNull$$$0(11);
        }
        if (serverMetrics == null) {
            DefaultBuildDistributor.$$$reportNull$$$0(12);
        }
        if (executorCompatibilityManager == null) {
            DefaultBuildDistributor.$$$reportNull$$$0(13);
        }
        if (agentlessBuildExecutionManager == null) {
            DefaultBuildDistributor.$$$reportNull$$$0(14);
        }
        if (agentTypeFinder == null) {
            DefaultBuildDistributor.$$$reportNull$$$0(15);
        }
        this(extensionHolder, timeService, virtualAgentsManager, buildAgentsComparatorProvider, executorCompatibilityManager, agentlessBuildExecutionManager, new BuildQueueProcessingMetrics(serverMetrics), agentTypeFinder);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @NotNull
    public DistributionResult<SBuildAgent> assignBuildsToAgents(@NotNull BuildDistributorInputEx buildDistributorInputEx, @Nullable Map<QueuedBuildInfo, WaitReason> map, @NotNull BuildStarter<SBuildAgent> buildStarter, @Nullable BuildStarter<VirtualAgent> buildStarter2, @Nullable BuildStarter<ExecutorDescription> buildStarter3, @NotNull SetWaitReasonCallback setWaitReasonCallback, @NotNull CompositeBuildStarter compositeBuildStarter, @NotNull AgentlessBuildStarter agentlessBuildStarter, @NotNull BuildDistributor.QueuedBuildOptimizer queuedBuildOptimizer, @NotNull BuildDistributor.QueuedBuildTerminator queuedBuildTerminator, boolean bl) {
        List<DistributorState<?>> list;
        try {
            if (buildDistributorInputEx == null) {
                DefaultBuildDistributor.$$$reportNull$$$0(16);
            }
        }
        catch (Throwable throwable) {
            throw DefaultBuildDistributor.a(throwable);
        }
        try {
            if (buildStarter == null) {
                DefaultBuildDistributor.$$$reportNull$$$0(17);
            }
        }
        catch (Throwable throwable) {
            throw DefaultBuildDistributor.a(throwable);
        }
        try {
            if (setWaitReasonCallback == null) {
                DefaultBuildDistributor.$$$reportNull$$$0(18);
            }
        }
        catch (Throwable throwable) {
            throw DefaultBuildDistributor.a(throwable);
        }
        try {
            if (compositeBuildStarter == null) {
                DefaultBuildDistributor.$$$reportNull$$$0(19);
            }
        }
        catch (Throwable throwable) {
            throw DefaultBuildDistributor.a(throwable);
        }
        try {
            if (agentlessBuildStarter == null) {
                DefaultBuildDistributor.$$$reportNull$$$0(20);
            }
        }
        catch (Throwable throwable) {
            throw DefaultBuildDistributor.a(throwable);
        }
        try {
            if (queuedBuildOptimizer == null) {
                DefaultBuildDistributor.$$$reportNull$$$0(21);
            }
        }
        catch (Throwable throwable) {
            throw DefaultBuildDistributor.a(throwable);
        }
        try {
            if (queuedBuildTerminator == null) {
                DefaultBuildDistributor.$$$reportNull$$$0(22);
            }
        }
        catch (Throwable throwable) {
            throw DefaultBuildDistributor.a(throwable);
        }
        Stoppable stoppable = this.myBuildQueueProcessingMetrics.getOtherActivitiesMetric(bl).startMsecsTimer();
        try {
            try {
                if (!bl) {
                    this.myCheckTopologicalOrderAction.executeCustomAction(() -> this.checkTopologicalOrder(buildDistributorInputEx.getQueue()));
                }
            }
            catch (Throwable throwable) {
                throw DefaultBuildDistributor.a(throwable);
            }
            list = this.createDistributorStates(buildDistributorInputEx, map, buildStarter, buildStarter2, buildStarter3, agentlessBuildStarter, bl);
        }
        catch (Throwable throwable) {
            if (stoppable == null) throw throwable;
            try {
                stoppable.close();
                throw throwable;
            }
            catch (Throwable throwable2) {
                throwable.addSuppressed(throwable2);
            }
            throw throwable;
        }
        try {
            if (stoppable != null) {
                stoppable.close();
            }
        }
        catch (Throwable throwable) {
            throw DefaultBuildDistributor.a(throwable);
        }
        stoppable = new BuildQueueDistributionProcessor(buildDistributorInputEx, map, list, setWaitReasonCallback, compositeBuildStarter, agentlessBuildStarter, this.myExtensionHolder, this.myBuildQueueProcessingMetrics, queuedBuildOptimizer, queuedBuildTerminator, this.myAgentlessBuildExecutionManager, bl);
        try {
            DistributionResult distributionResult = stoppable.doDistribute();
            if (distributionResult != null) DistributionResult distributionResult2;
            return distributionResult2;
            DefaultBuildDistributor.$$$reportNull$$$0(23);
            return distributionResult;
        }
        catch (Throwable throwable) {
            throw DefaultBuildDistributor.a(throwable);
        }
    }

    @NotNull
    private List<DistributorState<?>> createDistributorStates(@NotNull BuildDistributorInputEx buildDistributorInputEx, @Nullable Map<QueuedBuildInfo, WaitReason> map, @NotNull BuildStarter<SBuildAgent> buildStarter, @Nullable BuildStarter<VirtualAgent> buildStarter2, @Nullable BuildStarter<ExecutorDescription> buildStarter3, @NotNull AgentlessBuildStarter agentlessBuildStarter, boolean bl) {
        ArrayList arrayList;
        try {
            if (buildDistributorInputEx == null) {
                DefaultBuildDistributor.$$$reportNull$$$0(24);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw DefaultBuildDistributor.a(illegalArgumentException);
        }
        try {
            if (buildStarter == null) {
                DefaultBuildDistributor.$$$reportNull$$$0(25);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw DefaultBuildDistributor.a(illegalArgumentException);
        }
        try {
            if (agentlessBuildStarter == null) {
                DefaultBuildDistributor.$$$reportNull$$$0(26);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw DefaultBuildDistributor.a(illegalArgumentException);
        }
        ArrayList arrayList2 = new ArrayList();
        DistributorState distributorState = new DistributorState((Distributor)new ConnectedAgentsDistributor(buildDistributorInputEx, map, bl, this.myExtensionHolder, this.myAgentsComparatorProvider), buildStarter);
        buildDistributorInputEx.getDistributedBuilds().forEach((queuedBuildInfo, sBuildAgent) -> distributorState.addDistributedBuild(queuedBuildInfo, sBuildAgent));
        arrayList2.add(distributorState);
        DistributorState<VirtualAgent> distributorState2 = this.createVirtualAgentDistributorState(buildStarter2, bl);
        try {
            if (distributorState2 != null) {
                arrayList2.add(distributorState2);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw DefaultBuildDistributor.a(illegalArgumentException);
        }
        if (TeamCityProperties.getBooleanOrTrue((String)"teamcity.queueDistribution.enableExecutor")) {
            DistributorState<ExecutorDescription> distributorState3 = this.createExecutorDistributorState(buildStarter3, agentlessBuildStarter);
            try {
                if (distributorState3 != null) {
                    arrayList2.add(distributorState3);
                }
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw DefaultBuildDistributor.a(illegalArgumentException);
            }
        }
        try {
            arrayList = arrayList2;
            if (arrayList == null) {
                DefaultBuildDistributor.$$$reportNull$$$0(27);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw DefaultBuildDistributor.a(illegalArgumentException);
        }
        return arrayList;
    }

    @Nullable
    private DistributorState<ExecutorDescription> createExecutorDistributorState(@Nullable BuildStarter<ExecutorDescription> buildStarter, @NotNull AgentlessBuildStarter agentlessBuildStarter) {
        try {
            if (agentlessBuildStarter == null) {
                DefaultBuildDistributor.$$$reportNull$$$0(28);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw DefaultBuildDistributor.a(illegalArgumentException);
        }
        try {
            if (buildStarter == null) {
                return null;
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw DefaultBuildDistributor.a(illegalArgumentException);
        }
        ExecutorDistributor executorDistributor = new ExecutorDistributor(this.myAgentsComparatorProvider, this.myExecutorsManager);
        return new DistributorState((Distributor)executorDistributor, (queuedBuildInfo, executorDescription) -> {
            DistributionDecision distributionDecision = buildStarter.distributeBuild(queuedBuildInfo, executorDescription);
            try {
                if (distributionDecision.isBuildDistributed()) {
                    agentlessBuildStarter.startBuild(queuedBuildInfo, executorDescription.getBuildExecutor().getExecutorType());
                    return new DistributionDecision(false, true, StandardWaitReasons.WAITING_FOR_CLOUD_AGENT);
                }
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw DefaultBuildDistributor.a(illegalArgumentException);
            }
            return distributionDecision;
        });
    }

    @Nullable
    private DistributorState<VirtualAgent> createVirtualAgentDistributorState(@Nullable BuildStarter<VirtualAgent> buildStarter, boolean bl) {
        try {
            if (buildStarter == null) {
                return null;
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw DefaultBuildDistributor.a(illegalArgumentException);
        }
        VirtualAgentDistributor virtualAgentDistributor = new VirtualAgentDistributor(() -> this.myVirualAgents.getStartableAgentTypes(), this.myAgentsComparatorProvider, this.myExtensionHolder, bl);
        return new DistributorState((Distributor)virtualAgentDistributor, (queuedBuildInfo, virtualAgent) -> {
            DistributionDecision distributionDecision = buildStarter.distributeBuild(queuedBuildInfo, virtualAgent);
            try {
                if (distributionDecision.isBuildDistributed()) {
                    return new DistributionDecision(false, true, StandardWaitReasons.WAITING_FOR_CLOUD_AGENT);
                }
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw DefaultBuildDistributor.a(illegalArgumentException);
            }
            return distributionDecision;
        });
    }

    private void checkTopologicalOrder(List<QueuedBuildInfo> list) {
        Pair<BuildPromotionInfo, BuildPromotionInfo> pair = this.findNotTopologicallyOrderedBuilds(list);
        try {
            if (pair != null) {
                Loggers.SERVER.warn("Topological order violated for build " + LogUtil.describe((Object)pair.first) + " and build " + LogUtil.describe((Object)pair.second));
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw DefaultBuildDistributor.a(illegalArgumentException);
        }
    }

    @Nullable
    private Pair<BuildPromotionInfo, BuildPromotionInfo> findNotTopologicallyOrderedBuilds(@NotNull List<QueuedBuildInfo> list) {
        int n;
        try {
            if (list == null) {
                DefaultBuildDistributor.$$$reportNull$$$0(29);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw DefaultBuildDistributor.a(illegalArgumentException);
        }
        HashMap<BuildPromotionInfo, Integer> hashMap = new HashMap<BuildPromotionInfo, Integer>();
        try {
            for (n = 0; n < list.size(); ++n) {
                hashMap.put(list.get(n).getBuildPromotionInfo(), n);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw DefaultBuildDistributor.a(illegalArgumentException);
        }
        for (n = 0; n < list.size(); ++n) {
            QueuedBuildInfo queuedBuildInfo = list.get(n);
            for (BuildPromotionInfo buildPromotionInfo : queuedBuildInfo.getBuildPromotionInfo().getDirectDependencies()) {
                Integer n2 = (Integer)hashMap.get(buildPromotionInfo);
                try {
                    try {
                        if (n2 == null || n2 <= n) continue;
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        throw DefaultBuildDistributor.a(illegalArgumentException);
                    }
                    return new Pair((Object)queuedBuildInfo.getBuildPromotionInfo(), (Object)buildPromotionInfo);
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw DefaultBuildDistributor.a(illegalArgumentException);
                }
            }
        }
        return null;
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
