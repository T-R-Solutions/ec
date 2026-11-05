/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.intellij.openapi.util.Pair
 *  jetbrains.buildServer.ExtensionHolder
 *  jetbrains.buildServer.log.Loggers
 *  jetbrains.buildServer.metrics.Counter
 *  jetbrains.buildServer.metrics.ServerLoadMetrics
 *  jetbrains.buildServer.metrics.Stoppable
 *  jetbrains.buildServer.serverSide.BuildPromotionEx
 *  jetbrains.buildServer.serverSide.DependencyConsumer$Result
 *  jetbrains.buildServer.serverSide.SBuildAgent
 *  jetbrains.buildServer.serverSide.SBuildType
 *  jetbrains.buildServer.serverSide.SRunningBuild
 *  jetbrains.buildServer.serverSide.TeamCityProperties
 *  jetbrains.buildServer.serverSide.agentless.AgentlessBuildExecutionManager
 *  jetbrains.buildServer.serverSide.buildDistribution.BuildConfigurationInfo
 *  jetbrains.buildServer.serverSide.buildDistribution.BuildDistributorInput
 *  jetbrains.buildServer.serverSide.buildDistribution.QueuedBuildInfo
 *  jetbrains.buildServer.serverSide.buildDistribution.SimpleWaitReason
 *  jetbrains.buildServer.serverSide.buildDistribution.StartBuildPrecondition
 *  jetbrains.buildServer.serverSide.buildDistribution.WaitReason
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.AgentlessBuildStarter
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.BuildDistributor$QueuedBuildOptimizer
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.BuildDistributor$QueuedBuildTerminator
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.BuildDistributorInputEx
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.BuildQueueDistributionProcessor$NotNullCallable
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.BuildQueueDistributionProcessor$ReadonlyMap
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.BuildQueueProcessingMetrics
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.BuildStarter
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.CompatibleAgentsResult
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.CompositeBuildStarter
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.DistributionDecision
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.DistributionResult
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.DistributorState
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.SetWaitReasonCallback
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.StandardWaitReasons
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.VirtualAgentDistributor
 *  jetbrains.buildServer.util.ExceptionUtil
 *  jetbrains.buildServer.util.NamedThreadFactory
 *  jetbrains.buildServer.util.ThreadStat
 *  jetbrains.buildServer.util.impl.Lazy
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package jetbrains.buildServer.serverSide.impl.buildDistribution;

import com.intellij.openapi.util.Pair;
import java.lang.invoke.LambdaMetafactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Collectors;
import jetbrains.buildServer.ExtensionHolder;
import jetbrains.buildServer.log.Loggers;
import jetbrains.buildServer.metrics.Counter;
import jetbrains.buildServer.metrics.ServerLoadMetrics;
import jetbrains.buildServer.metrics.Stoppable;
import jetbrains.buildServer.serverSide.BuildPromotionEx;
import jetbrains.buildServer.serverSide.DependencyConsumer;
import jetbrains.buildServer.serverSide.SBuildAgent;
import jetbrains.buildServer.serverSide.SBuildType;
import jetbrains.buildServer.serverSide.SRunningBuild;
import jetbrains.buildServer.serverSide.TeamCityProperties;
import jetbrains.buildServer.serverSide.agentless.AgentlessBuildExecutionManager;
import jetbrains.buildServer.serverSide.buildDistribution.BuildConfigurationInfo;
import jetbrains.buildServer.serverSide.buildDistribution.BuildDistributorInput;
import jetbrains.buildServer.serverSide.buildDistribution.QueuedBuildInfo;
import jetbrains.buildServer.serverSide.buildDistribution.SimpleWaitReason;
import jetbrains.buildServer.serverSide.buildDistribution.StartBuildPrecondition;
import jetbrains.buildServer.serverSide.buildDistribution.WaitReason;
import jetbrains.buildServer.serverSide.impl.buildDistribution.AgentlessBuildStarter;
import jetbrains.buildServer.serverSide.impl.buildDistribution.BuildDistributor;
import jetbrains.buildServer.serverSide.impl.buildDistribution.BuildDistributorInputEx;
import jetbrains.buildServer.serverSide.impl.buildDistribution.BuildQueueDistributionProcessor;
import jetbrains.buildServer.serverSide.impl.buildDistribution.BuildQueueProcessingMetrics;
import jetbrains.buildServer.serverSide.impl.buildDistribution.BuildStarter;
import jetbrains.buildServer.serverSide.impl.buildDistribution.CompatibleAgentsResult;
import jetbrains.buildServer.serverSide.impl.buildDistribution.CompositeBuildStarter;
import jetbrains.buildServer.serverSide.impl.buildDistribution.DistributionDecision;
import jetbrains.buildServer.serverSide.impl.buildDistribution.DistributionResult;
import jetbrains.buildServer.serverSide.impl.buildDistribution.DistributorState;
import jetbrains.buildServer.serverSide.impl.buildDistribution.SetWaitReasonCallback;
import jetbrains.buildServer.serverSide.impl.buildDistribution.StandardWaitReasons;
import jetbrains.buildServer.serverSide.impl.buildDistribution.VirtualAgentDistributor;
import jetbrains.buildServer.util.ExceptionUtil;
import jetbrains.buildServer.util.NamedThreadFactory;
import jetbrains.buildServer.util.ThreadStat;
import jetbrains.buildServer.util.impl.Lazy;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Exception performing whole class analysis ignored.
 */
public class BuildQueueDistributionProcessor {
    @NotNull
    private final Map<QueuedBuildInfo, WaitReason> myWaitReasons;
    @NotNull
    private final SetWaitReasonCallback mySetWaitReasonCallback;
    @NotNull
    private final CompositeBuildStarter myCompositeBuildStarter;
    @NotNull
    private final AgentlessBuildStarter myAgentlessBuildStarter;
    @NotNull
    protected final ExtensionHolder myExtensionHolder;
    @NotNull
    protected final BuildDistributorInputEx myDistributorInput;
    @NotNull
    private final BuildDistributor.QueuedBuildOptimizer myQueuedBuildOptimizer;
    @NotNull
    private final BuildDistributor.QueuedBuildTerminator myQueuedBuildTerminator;
    @NotNull
    private final AgentlessBuildExecutionManager myAgentlessBuildExecutionManager;
    @NotNull
    private final List<DistributorState<?>> myDistributorStates;
    @NotNull
    private final Map<QueuedBuildInfo, SBuildAgent> myDistributedBuilds;
    protected final boolean myEmulationMode;
    private final BuildQueueProcessingMetrics myBuildQueueProcessingMetrics;
    static final /* synthetic */ boolean $assertionsDisabled;

    public BuildQueueDistributionProcessor(@NotNull BuildDistributorInputEx buildDistributorInputEx, @Nullable Map<QueuedBuildInfo, WaitReason> hashMap, @NotNull List<DistributorState<?>> list, @NotNull SetWaitReasonCallback setWaitReasonCallback, @NotNull CompositeBuildStarter compositeBuildStarter, @NotNull AgentlessBuildStarter agentlessBuildStarter, @NotNull ExtensionHolder extensionHolder, @NotNull BuildQueueProcessingMetrics buildQueueProcessingMetrics, @NotNull BuildDistributor.QueuedBuildOptimizer queuedBuildOptimizer, @NotNull BuildDistributor.QueuedBuildTerminator queuedBuildTerminator, @NotNull AgentlessBuildExecutionManager agentlessBuildExecutionManager, boolean bl) {
        if (buildDistributorInputEx == null) {
            BuildQueueDistributionProcessor.$$$reportNull$$$0(0);
        }
        if (list == null) {
            BuildQueueDistributionProcessor.$$$reportNull$$$0(1);
        }
        if (setWaitReasonCallback == null) {
            BuildQueueDistributionProcessor.$$$reportNull$$$0(2);
        }
        if (compositeBuildStarter == null) {
            BuildQueueDistributionProcessor.$$$reportNull$$$0(3);
        }
        if (agentlessBuildStarter == null) {
            BuildQueueDistributionProcessor.$$$reportNull$$$0(4);
        }
        if (extensionHolder == null) {
            BuildQueueDistributionProcessor.$$$reportNull$$$0(5);
        }
        if (buildQueueProcessingMetrics == null) {
            BuildQueueDistributionProcessor.$$$reportNull$$$0(6);
        }
        if (queuedBuildOptimizer == null) {
            BuildQueueDistributionProcessor.$$$reportNull$$$0(7);
        }
        if (queuedBuildTerminator == null) {
            BuildQueueDistributionProcessor.$$$reportNull$$$0(8);
        }
        if (agentlessBuildExecutionManager == null) {
            BuildQueueDistributionProcessor.$$$reportNull$$$0(9);
        }
        this.myDistributedBuilds = new LinkedHashMap<QueuedBuildInfo, SBuildAgent>();
        this.myDistributorInput = buildDistributorInputEx;
        this.mySetWaitReasonCallback = setWaitReasonCallback;
        this.myCompositeBuildStarter = compositeBuildStarter;
        this.myAgentlessBuildStarter = agentlessBuildStarter;
        this.myExtensionHolder = extensionHolder;
        this.myAgentlessBuildExecutionManager = agentlessBuildExecutionManager;
        this.myEmulationMode = bl;
        this.myWaitReasons = hashMap == null ? new HashMap() : hashMap;
        this.myDistributorStates = list;
        this.myQueuedBuildOptimizer = queuedBuildOptimizer;
        this.myQueuedBuildTerminator = queuedBuildTerminator;
        this.myDistributedBuilds.putAll(buildDistributorInputEx.getDistributedBuilds());
        this.myBuildQueueProcessingMetrics = buildQueueProcessingMetrics;
    }

    /*
     * Unable to fully structure code
     */
    public DistributionResult<SBuildAgent> doDistribute() {
        var1_1 = this.myExtensionHolder.getExtensions(StartBuildPrecondition.class);
        var2_2 = new HashMap<String, Object>();
        var3_3 = this.myDistributorInput.getQueue();
        var4_4 = var3_3.stream().collect(Collectors.toMap((Function<QueuedBuildInfo, Long>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)Ljava/lang/Object;, lambda$doDistribute$0(jetbrains.buildServer.serverSide.buildDistribution.QueuedBuildInfo ), (Ljetbrains/buildServer/serverSide/buildDistribution/QueuedBuildInfo;)Ljava/lang/Long;)(), (Function<QueuedBuildInfo, QueuedBuildInfo>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)Ljava/lang/Object;, lambda$doDistribute$1(jetbrains.buildServer.serverSide.buildDistribution.QueuedBuildInfo ), (Ljetbrains/buildServer/serverSide/buildDistribution/QueuedBuildInfo;)Ljetbrains/buildServer/serverSide/buildDistribution/QueuedBuildInfo;)()));
        block36: for (QueuedBuildInfo var6_6 : var3_3) {
            block53: {
                block51: {
                    block49: {
                        block48: {
                            var7_7 = (BuildPromotionEx)var6_6.getBuildPromotionInfo();
                            try {
                                if (var7_7.isDeleted()) {
                                    continue;
                                }
                            }
                            catch (Throwable v0) {
                                throw BuildQueueDistributionProcessor.a(v0);
                            }
                            try {
                                if (!this.myEmulationMode) {
                                    ServerLoadMetrics.QUEUE_PROCESSING.increment(1.0);
                                }
                            }
                            catch (Throwable v1) {
                                throw BuildQueueDistributionProcessor.a(v1);
                            }
                            if (this.isDistributed(var6_6)) continue;
                            try {
                                if (this.myWaitReasons.containsKey(var6_6)) {
                                    continue;
                                }
                            }
                            catch (Throwable v2) {
                                throw BuildQueueDistributionProcessor.a(v2);
                            }
                            var8_8 = this.myBuildQueueProcessingMetrics.getOtherActivitiesMetric(this.myEmulationMode).startMsecsTimer();
                            try {
                                if (this.myEmulationMode || !this.myQueuedBuildTerminator.scheduledForTermination(var6_6)) break block48;
                                if (var8_8 == null) continue;
                            }
                            catch (Throwable var9_10) {
                                if (var8_8 != null) {
                                    try {
                                        var8_8.close();
                                    }
                                    catch (Throwable var10_12) {
                                        var9_10.addSuppressed(var10_12);
                                    }
                                }
                                throw var9_10;
                            }
                            var8_8.close();
                            continue;
                        }
                        try {
                            if (var8_8 == null) ** GOTO lbl47
                            var8_8.close();
                        }
                        catch (Throwable v3) {
                            throw BuildQueueDistributionProcessor.a(v3);
                        }
lbl47:
                        // 2 sources

                        var8_8 = this.executeWithThreadName("Checking preconditions for build with id " + var6_6.getBuildPromotionInfo().getId(), (Callable<Pair>)LambdaMetafactory.metafactory(null, null, null, ()Ljava/lang/Object;, lambda$doDistribute$2(jetbrains.buildServer.serverSide.buildDistribution.QueuedBuildInfo java.util.Collection ), ()Lcom/intellij/openapi/util/Pair;)((BuildQueueDistributionProcessor)this, (QueuedBuildInfo)var6_6, (Collection)var1_1));
                        try {
                            try {
                                if (BuildQueueDistributionProcessor.$assertionsDisabled || var8_8 != null) break block49;
                            }
                            catch (Throwable v4) {
                                throw BuildQueueDistributionProcessor.a(v4);
                            }
                            throw new AssertionError();
                        }
                        catch (Throwable v5) {
                            throw BuildQueueDistributionProcessor.a(v5);
                        }
                    }
                    try {
                        try {
                            if (this.myQueuedBuildOptimizer.canRunOptimization(var6_6, (WaitReason)var8_8.getFirst(), (Set)var8_8.getSecond()) && this.myQueuedBuildOptimizer.performOptimization(var6_6, this.myDistributorInput)) {
                                continue;
                            }
                        }
                        catch (Throwable v6) {
                            throw BuildQueueDistributionProcessor.a(v6);
                        }
                    }
                    catch (Throwable v7) {
                        throw BuildQueueDistributionProcessor.a(v7);
                    }
                    var9_9 = (WaitReason)var8_8.getFirst();
                    try {
                        if (var9_9 == null) break block51;
                        this.setReason(var6_6, var9_9);
                        if (!this.showDebugLog()) continue;
                    }
                    catch (Throwable v8) {
                        throw BuildQueueDistributionProcessor.a(v8);
                    }
                    Loggers.SERVER.debug("A precondition has blocked the start of the build: " + String.valueOf(var6_6) + ", wait reason: " + var9_9.getDescription());
                    continue;
                }
                try {
                    block52: {
                        try {
                            try {
                                if (!TeamCityProperties.getBooleanOrTrue((String)"teamcity.queueDistribution.enableExecutor") && var7_7.isAgentLessBuild()) break block52;
                            }
                            catch (Throwable v9) {
                                throw BuildQueueDistributionProcessor.a(v9);
                            }
                            if (!var7_7.isCompositeBuild()) break block53;
                        }
                        catch (Throwable v10) {
                            throw BuildQueueDistributionProcessor.a(v10);
                        }
                    }
                    this.distributeAgentlessBuild(var6_6, var2_2);
                    continue;
                }
                catch (Throwable v11) {
                    throw BuildQueueDistributionProcessor.a(v11);
                }
            }
            var10_11 = this.analyzePreconditionsOfDependentCompositeBuilds(var7_7, var4_4, var1_1);
            try {
                if (this.myWaitReasons.containsKey(var6_6)) {
                    continue;
                }
            }
            catch (Throwable v12) {
                throw BuildQueueDistributionProcessor.a(v12);
            }
            for (DistributorState<?> var12_14 : this.myDistributorStates) {
                this.addDistributorCompatibleAgentsComputationToContext(var2_2, var6_6, var12_14);
            }
            for (DistributorState<?> var12_14 : this.myDistributorStates) {
                if (!var12_14.getDistributor().canProcessQueuedBuild(var6_6, this.myWaitReasons)) continue;
                var13_15 = this.callDistributor(var6_6, var2_2, var12_14);
                try {
                    if (var13_15.getWaitReason() != null) {
                        this.setReason(var6_6, var13_15.getWaitReason());
                    }
                }
                catch (Throwable v13) {
                    throw BuildQueueDistributionProcessor.a(v13);
                }
                if (var13_15.isBuildStarted()) {
                    for (BuildPromotionEx var15_17 : var10_11) {
                        var16_18 = var4_4.get(var15_17.getId());
                        try {
                            if (var16_18 == null) continue;
                            this.distributeAgentlessBuild(var16_18, var2_2);
                        }
                        catch (Throwable v14) {
                            throw BuildQueueDistributionProcessor.a(v14);
                        }
                    }
                    continue block36;
                }
                try {
                    if (!var13_15.isBuildDistributed()) continue;
                    continue block36;
                }
                catch (Throwable v15) {
                    throw BuildQueueDistributionProcessor.a(v15);
                }
            }
        }
        return this.createResult();
    }

    @NotNull
    private DistributionResult<SBuildAgent> createResult() {
        LinkedHashMap<QueuedBuildInfo, SBuildAgent> linkedHashMap = new LinkedHashMap<QueuedBuildInfo, SBuildAgent>(this.myDistributedBuilds);
        linkedHashMap.keySet().removeAll(this.myDistributorInput.getDistributedBuilds().keySet());
        return new DistributionResult(linkedHashMap, this.myWaitReasons);
    }

    private boolean isDistributed(@NotNull QueuedBuildInfo queuedBuildInfo) {
        try {
            if (queuedBuildInfo == null) {
                BuildQueueDistributionProcessor.$$$reportNull$$$0(10);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildQueueDistributionProcessor.a(illegalStateException);
        }
        return this.myDistributedBuilds.containsKey(queuedBuildInfo);
    }

    @NotNull
    private List<BuildPromotionEx> analyzePreconditionsOfDependentCompositeBuilds(@NotNull BuildPromotionEx buildPromotionEx2, @NotNull Map<Long, QueuedBuildInfo> map, @NotNull Collection<StartBuildPrecondition> collection) {
        ArrayList<BuildPromotionEx> arrayList;
        ArrayList<BuildPromotionEx> arrayList2;
        block19: {
            try {
                if (buildPromotionEx2 == null) {
                    BuildQueueDistributionProcessor.$$$reportNull$$$0(11);
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildQueueDistributionProcessor.a(illegalStateException);
            }
            try {
                if (map == null) {
                    BuildQueueDistributionProcessor.$$$reportNull$$$0(12);
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildQueueDistributionProcessor.a(illegalStateException);
            }
            try {
                if (collection == null) {
                    BuildQueueDistributionProcessor.$$$reportNull$$$0(13);
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildQueueDistributionProcessor.a(illegalStateException);
            }
            QueuedBuildInfo queuedBuildInfo = map.get(buildPromotionEx2.getId());
            arrayList2 = new ArrayList<BuildPromotionEx>();
            AtomicReference atomicReference = new AtomicReference();
            boolean bl = buildPromotionEx2.getPipelineView().isJob();
            try {
                buildPromotionEx2.traverseDependedOnMe(buildPromotionEx -> {
                    block21: {
                        WaitReason waitReason;
                        QueuedBuildInfo queuedBuildInfo2;
                        block20: {
                            block19: {
                                block18: {
                                    try {
                                        if (!buildPromotionEx.isCompositeBuild()) {
                                            return DependencyConsumer.Result.CONTINUE;
                                        }
                                    }
                                    catch (IllegalStateException illegalStateException) {
                                        throw BuildQueueDistributionProcessor.a(illegalStateException);
                                    }
                                    queuedBuildInfo2 = (QueuedBuildInfo)map.get(buildPromotionEx.getId());
                                    try {
                                        try {
                                            if (queuedBuildInfo2 != null && !this.isDistributed(queuedBuildInfo2)) break block18;
                                        }
                                        catch (IllegalStateException illegalStateException) {
                                            throw BuildQueueDistributionProcessor.a(illegalStateException);
                                        }
                                        return DependencyConsumer.Result.STOP;
                                    }
                                    catch (IllegalStateException illegalStateException) {
                                        throw BuildQueueDistributionProcessor.a(illegalStateException);
                                    }
                                }
                                try {
                                    try {
                                        if (bl || !BuildQueueDistributionProcessor.isPipelineHead(queuedBuildInfo2)) break block19;
                                    }
                                    catch (IllegalStateException illegalStateException) {
                                        throw BuildQueueDistributionProcessor.a(illegalStateException);
                                    }
                                    return DependencyConsumer.Result.STOP;
                                }
                                catch (IllegalStateException illegalStateException) {
                                    throw BuildQueueDistributionProcessor.a(illegalStateException);
                                }
                            }
                            waitReason = this.myWaitReasons.get(queuedBuildInfo2);
                            if (StandardWaitReasons.WAITING_FOR_THE_FIRST_DEPENDENCY_TO_START.equals((Object)waitReason)) {
                                waitReason = null;
                            }
                            if (waitReason == null) {
                                waitReason = this.executeWithThreadName("Checking preconditions for build with id " + queuedBuildInfo.getBuildPromotionInfo().getId(), () -> (WaitReason)this.checkHasWaitReason(queuedBuildInfo2, collection, StandardWaitReasons.WAITING_FOR_THE_FIRST_DEPENDENCY_TO_START).getFirst());
                            }
                            try {
                                if (waitReason != null) break block20;
                                arrayList2.add((BuildPromotionEx)buildPromotionEx);
                                break block21;
                            }
                            catch (IllegalStateException illegalStateException) {
                                throw BuildQueueDistributionProcessor.a(illegalStateException);
                            }
                        }
                        try {
                            if (atomicReference.get() == null) {
                                this.setReason(queuedBuildInfo2, waitReason);
                                atomicReference.set(Pair.create((Object)queuedBuildInfo2, (Object)waitReason));
                            }
                        }
                        catch (IllegalStateException illegalStateException) {
                            throw BuildQueueDistributionProcessor.a(illegalStateException);
                        }
                        return DependencyConsumer.Result.SKIP;
                    }
                    return DependencyConsumer.Result.CONTINUE;
                });
                if (!arrayList2.isEmpty() || atomicReference.get() == null) break block19;
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildQueueDistributionProcessor.a(illegalStateException);
            }
            QueuedBuildInfo queuedBuildInfo2 = (QueuedBuildInfo)((Pair)atomicReference.get()).getFirst();
            BuildConfigurationInfo buildConfigurationInfo = queuedBuildInfo2.getBuildConfiguration();
            if (buildConfigurationInfo instanceof SBuildType) {
                SBuildType sBuildType = (SBuildType)buildConfigurationInfo;
                WaitReason waitReason = (WaitReason)((Pair)atomicReference.get()).getSecond();
                this.setReason(queuedBuildInfo2, waitReason);
                if (!this.hasRunningComposite(buildPromotionEx2, bl)) {
                    String string = sBuildType.getFullName();
                    try {
                        this.setReason(queuedBuildInfo, (WaitReason)new SimpleWaitReason("Start of the dependent composite build \"" + string + "\" is blocked, reason: " + waitReason.getDescription()));
                        if (this.showDebugLog()) {
                            Loggers.SERVER.debug("Build cannot be started: " + String.valueOf(queuedBuildInfo) + " because precondition blocked the start of the depending composite build: " + String.valueOf(queuedBuildInfo2) + ", wait reason: " + waitReason.getDescription());
                        }
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildQueueDistributionProcessor.a(illegalStateException);
                    }
                }
            }
        }
        try {
            arrayList = arrayList2;
            if (arrayList == null) {
                BuildQueueDistributionProcessor.$$$reportNull$$$0(14);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildQueueDistributionProcessor.a(illegalStateException);
        }
        return arrayList;
    }

    private static boolean isPipelineHead(QueuedBuildInfo queuedBuildInfo) {
        boolean bl;
        block5: {
            block4: {
                try {
                    try {
                        if (!(queuedBuildInfo.getBuildPromotionInfo() instanceof BuildPromotionEx) || !((BuildPromotionEx)queuedBuildInfo.getBuildPromotionInfo()).getPipelineView().isHead()) break block4;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildQueueDistributionProcessor.a(illegalStateException);
                    }
                    bl = true;
                    break block5;
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildQueueDistributionProcessor.a(illegalStateException);
                }
            }
            bl = false;
        }
        return bl;
    }

    private boolean hasRunningComposite(@NotNull BuildPromotionEx buildPromotionEx, boolean bl) {
        try {
            if (buildPromotionEx == null) {
                BuildQueueDistributionProcessor.$$$reportNull$$$0(15);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildQueueDistributionProcessor.a(illegalStateException);
        }
        for (BuildPromotionEx buildPromotionEx2 : buildPromotionEx.getDependentCompositePromotions()) {
            block10: {
                try {
                    try {
                        try {
                            if (!(buildPromotionEx2.getAssociatedBuild() instanceof SRunningBuild)) continue;
                            if (!bl) break block10;
                        }
                        catch (IllegalStateException illegalStateException) {
                            throw BuildQueueDistributionProcessor.a(illegalStateException);
                        }
                        if (!buildPromotionEx2.getPipelineView().isHead()) continue;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildQueueDistributionProcessor.a(illegalStateException);
                    }
                    return true;
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildQueueDistributionProcessor.a(illegalStateException);
                }
            }
            return true;
        }
        return false;
    }

    private void distributeAgentlessBuild(@NotNull QueuedBuildInfo queuedBuildInfo, @NotNull Map<String, Object> map) {
        WaitReason waitReason;
        try {
            if (queuedBuildInfo == null) {
                BuildQueueDistributionProcessor.$$$reportNull$$$0(16);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildQueueDistributionProcessor.a(illegalStateException);
        }
        try {
            if (map == null) {
                BuildQueueDistributionProcessor.$$$reportNull$$$0(17);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildQueueDistributionProcessor.a(illegalStateException);
        }
        BuildPromotionEx buildPromotionEx = (BuildPromotionEx)queuedBuildInfo.getBuildPromotionInfo();
        if (buildPromotionEx.isCompositeBuild()) {
            DistributionDecision distributionDecision = this.distributeCompositeBuild(queuedBuildInfo, map, this.myCompositeBuildStarter);
            waitReason = distributionDecision.getWaitReason();
        } else {
            DistributionDecision distributionDecision = this.myAgentlessBuildExecutionManager.tryAndExecuteBuild(queuedBuildInfo, this.myAgentlessBuildStarter);
            waitReason = distributionDecision.getWaitReason();
            try {
                if (distributionDecision.isBuildStarted()) {
                    this.myDistributedBuilds.put(queuedBuildInfo, null);
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildQueueDistributionProcessor.a(illegalStateException);
            }
        }
        try {
            if (waitReason != null) {
                this.setReason(queuedBuildInfo, waitReason);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildQueueDistributionProcessor.a(illegalStateException);
        }
    }

    private void addDistributorCompatibleAgentsComputationToContext(@NotNull Map<String, Object> map, @NotNull QueuedBuildInfo queuedBuildInfo, @NotNull DistributorState<?> distributorState) {
        try {
            if (map == null) {
                BuildQueueDistributionProcessor.$$$reportNull$$$0(18);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildQueueDistributionProcessor.a(illegalStateException);
        }
        try {
            if (queuedBuildInfo == null) {
                BuildQueueDistributionProcessor.$$$reportNull$$$0(19);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildQueueDistributionProcessor.a(illegalStateException);
        }
        try {
            if (distributorState == null) {
                BuildQueueDistributionProcessor.$$$reportNull$$$0(20);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildQueueDistributionProcessor.a(illegalStateException);
        }
        map.put(this.compatibleAgentsContextKey(distributorState), Lazy.create(() -> (CompatibleAgentsResult)this.executeWithThreadNameNotNull("Calculating compatible agents for build with id  " + queuedBuildInfo.getBuildPromotionInfo().getId(), () -> this.getDistributorCompatibleAgentsResult(queuedBuildInfo, map, this.mySetWaitReasonCallback, distributorState))));
    }

    @NotNull
    private String compatibleAgentsContextKey(@NotNull DistributorState<?> distributorState) {
        String string;
        try {
            if (distributorState == null) {
                BuildQueueDistributionProcessor.$$$reportNull$$$0(21);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildQueueDistributionProcessor.a(illegalStateException);
        }
        try {
            string = distributorState.getDistributor().getName();
            if (string == null) {
                BuildQueueDistributionProcessor.$$$reportNull$$$0(22);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildQueueDistributionProcessor.a(illegalStateException);
        }
        return string;
    }

    @Nullable
    private <T> CompatibleAgentsResult<T> getDistributorCompatibleAgentsFromContext(@NotNull DistributorState<T> distributorState, @NotNull Map<String, Object> map) {
        try {
            if (distributorState == null) {
                BuildQueueDistributionProcessor.$$$reportNull$$$0(23);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildQueueDistributionProcessor.a(illegalStateException);
        }
        try {
            if (map == null) {
                BuildQueueDistributionProcessor.$$$reportNull$$$0(24);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildQueueDistributionProcessor.a(illegalStateException);
        }
        return (CompatibleAgentsResult)((Lazy)map.get(this.compatibleAgentsContextKey(distributorState))).getValue();
    }

    @NotNull
    private <T> DistributionDecision callDistributor(@NotNull QueuedBuildInfo queuedBuildInfo, @NotNull Map<String, Object> map, @NotNull DistributorState<T> distributorState) {
        try {
            if (queuedBuildInfo == null) {
                BuildQueueDistributionProcessor.$$$reportNull$$$0(25);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildQueueDistributionProcessor.a(illegalStateException);
        }
        try {
            if (map == null) {
                BuildQueueDistributionProcessor.$$$reportNull$$$0(26);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildQueueDistributionProcessor.a(illegalStateException);
        }
        try {
            if (distributorState == null) {
                BuildQueueDistributionProcessor.$$$reportNull$$$0(27);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildQueueDistributionProcessor.a(illegalStateException);
        }
        CompatibleAgentsResult compatibleAgentsResult = this.getDistributorCompatibleAgentsFromContext(distributorState, map);
        try {
            if (compatibleAgentsResult == null) {
                throw new IllegalStateException("Distributor context was modified by a custom filter");
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildQueueDistributionProcessor.a(illegalStateException);
        }
        return (DistributionDecision)this.executeWithThreadNameNotNull("Starting build with id " + queuedBuildInfo.getBuildPromotionInfo().getId(), () -> this.distributeBuild(queuedBuildInfo, compatibleAgentsResult, distributorState.getBuildStarter(), distributorState));
    }

    @NotNull
    private <T> CompatibleAgentsResult<T> getDistributorCompatibleAgentsResult(@NotNull QueuedBuildInfo queuedBuildInfo2, @NotNull Map<String, Object> map, @NotNull SetWaitReasonCallback setWaitReasonCallback, @NotNull DistributorState<T> distributorState) {
        CompatibleAgentsResult compatibleAgentsResult;
        ArrayList arrayList;
        Stoppable stoppable;
        block50: {
            CompatibleAgentsResult compatibleAgentsResult2;
            Collection collection;
            block48: {
                CompatibleAgentsResult compatibleAgentsResult3;
                CompatibleAgentsResult compatibleAgentsResult4;
                Counter counter;
                try {
                    if (queuedBuildInfo2 == null) {
                        BuildQueueDistributionProcessor.$$$reportNull$$$0(28);
                    }
                }
                catch (Throwable throwable) {
                    throw BuildQueueDistributionProcessor.a(throwable);
                }
                try {
                    if (map == null) {
                        BuildQueueDistributionProcessor.$$$reportNull$$$0(29);
                    }
                }
                catch (Throwable throwable) {
                    throw BuildQueueDistributionProcessor.a(throwable);
                }
                try {
                    if (setWaitReasonCallback == null) {
                        BuildQueueDistributionProcessor.$$$reportNull$$$0(30);
                    }
                }
                catch (Throwable throwable) {
                    throw BuildQueueDistributionProcessor.a(throwable);
                }
                try {
                    if (distributorState == null) {
                        BuildQueueDistributionProcessor.$$$reportNull$$$0(31);
                    }
                }
                catch (Throwable throwable) {
                    throw BuildQueueDistributionProcessor.a(throwable);
                }
                try {
                    counter = distributorState.getDistributor() instanceof VirtualAgentDistributor ? this.myBuildQueueProcessingMetrics.getCompatibleVirtualAgentsMetric(this.myEmulationMode) : this.myBuildQueueProcessingMetrics.getCompatibleAgentsMetric(this.myEmulationMode);
                }
                catch (Throwable throwable) {
                    throw BuildQueueDistributionProcessor.a(throwable);
                }
                Counter counter2 = counter;
                stoppable = counter2.startMsecsTimer();
                try {
                    WaitReason waitReason2;
                    block49: {
                        CompatibleAgentsResult compatibleAgentsResult5 = distributorState.getDistributor().getCompatibleCandidates(queuedBuildInfo2, distributorState.getDistributedAgents());
                        waitReason2 = compatibleAgentsResult5.getWaitReason();
                        try {
                            if (waitReason2 != null) {
                                this.setReason(queuedBuildInfo2, waitReason2, (queuedBuildInfo, waitReason) -> {});
                            }
                        }
                        catch (Throwable throwable) {
                            throw BuildQueueDistributionProcessor.a(throwable);
                        }
                        collection = compatibleAgentsResult5.getAgents();
                        try {
                            if (this.showDebugLog()) {
                                Loggers.SERVER.debug("Compatible/can run on agents for: " + String.valueOf(queuedBuildInfo2) + " are: " + String.valueOf(collection) + ", already distributed agents: " + String.valueOf(this.myDistributedBuilds.values()) + ", inputData: " + String.valueOf(this.myDistributorInput));
                            }
                        }
                        catch (Throwable throwable) {
                            throw BuildQueueDistributionProcessor.a(throwable);
                        }
                        try {
                            if (!collection.isEmpty()) break block48;
                            if (waitReason2 != null) break block49;
                        }
                        catch (Throwable throwable) {
                            throw BuildQueueDistributionProcessor.a(throwable);
                        }
                        waitReason2 = this.myWaitReasons.get(queuedBuildInfo2);
                    }
                    if (waitReason2 == null) {
                        waitReason2 = StandardWaitReasons.NO_COMPATIBLE_AGENTS_AVAILABLE;
                    }
                    setWaitReasonCallback.waitReasonSet(queuedBuildInfo2, waitReason2);
                    compatibleAgentsResult4 = CompatibleAgentsResult.noAgents();
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
                try {
                    if (stoppable != null) {
                        stoppable.close();
                    }
                }
                catch (Throwable throwable) {
                    throw BuildQueueDistributionProcessor.a(throwable);
                }
                try {
                    compatibleAgentsResult3 = compatibleAgentsResult4;
                    if (compatibleAgentsResult3 == null) {
                        BuildQueueDistributionProcessor.$$$reportNull$$$0(32);
                    }
                }
                catch (Throwable throwable) {
                    throw BuildQueueDistributionProcessor.a(throwable);
                }
                return compatibleAgentsResult3;
            }
            arrayList = new ArrayList(collection);
            distributorState.getDistributor().sortAvailableAgents(arrayList, queuedBuildInfo2);
            WaitReason waitReason3 = distributorState.getDistributor().filterAvailableAgents((BuildDistributorInput)this.myDistributorInput, queuedBuildInfo2, arrayList, this.myDistributedBuilds, map);
            if (waitReason3 == null) break block50;
            CompatibleAgentsResult compatibleAgentsResult6 = new CompatibleAgentsResult(waitReason3);
            try {
                if (stoppable != null) {
                    stoppable.close();
                }
            }
            catch (Throwable throwable) {
                throw BuildQueueDistributionProcessor.a(throwable);
            }
            try {
                compatibleAgentsResult2 = compatibleAgentsResult6;
                if (compatibleAgentsResult2 == null) {
                    BuildQueueDistributionProcessor.$$$reportNull$$$0(33);
                }
            }
            catch (Throwable throwable) {
                throw BuildQueueDistributionProcessor.a(throwable);
            }
            return compatibleAgentsResult2;
        }
        CompatibleAgentsResult compatibleAgentsResult7 = new CompatibleAgentsResult(arrayList);
        try {
            if (stoppable != null) {
                stoppable.close();
            }
        }
        catch (Throwable throwable) {
            throw BuildQueueDistributionProcessor.a(throwable);
        }
        try {
            compatibleAgentsResult = compatibleAgentsResult7;
            if (compatibleAgentsResult == null) {
                BuildQueueDistributionProcessor.$$$reportNull$$$0(34);
            }
        }
        catch (Throwable throwable) {
            throw BuildQueueDistributionProcessor.a(throwable);
        }
        return compatibleAgentsResult;
    }

    /*
     * Unable to fully structure code
     */
    @NotNull
    private <T> DistributionDecision distributeBuild(@NotNull QueuedBuildInfo var1_1, @NotNull CompatibleAgentsResult<T> var2_2, @NotNull BuildStarter<T> var3_3, @NotNull DistributorState<T> var4_4) {
        block58: {
            block57: {
                block56: {
                    block55: {
                        try {
                            if (var1_1 == null) {
                                BuildQueueDistributionProcessor.$$$reportNull$$$0(35);
                            }
                        }
                        catch (Throwable v0) {
                            throw BuildQueueDistributionProcessor.a(v0);
                        }
                        try {
                            if (var2_2 == null) {
                                BuildQueueDistributionProcessor.$$$reportNull$$$0(36);
                            }
                        }
                        catch (Throwable v1) {
                            throw BuildQueueDistributionProcessor.a(v1);
                        }
                        try {
                            if (var3_3 == null) {
                                BuildQueueDistributionProcessor.$$$reportNull$$$0(37);
                            }
                        }
                        catch (Throwable v2) {
                            throw BuildQueueDistributionProcessor.a(v2);
                        }
                        try {
                            if (var4_4 == null) {
                                BuildQueueDistributionProcessor.$$$reportNull$$$0(38);
                            }
                        }
                        catch (Throwable v3) {
                            throw BuildQueueDistributionProcessor.a(v3);
                        }
                        var6_5 = this.myBuildQueueProcessingMetrics.getOtherActivitiesMetric(this.myEmulationMode).startMsecsTimer();
                        try {
                            if (var2_2.getWaitReason() == null) break block55;
                            var7_6 = new DistributionDecision(false, var2_2.getWaitReason());
                        }
                        catch (Throwable var7_8) {
                            if (var6_5 != null) {
                                try {
                                    var6_5.close();
                                }
                                catch (Throwable var8_11) {
                                    var7_8.addSuppressed(var8_11);
                                }
                            }
                            throw var7_8;
                        }
                        try {
                            if (var6_5 != null) {
                                var6_5.close();
                            }
                        }
                        catch (Throwable v4) {
                            throw BuildQueueDistributionProcessor.a(v4);
                        }
                        try {
                            v5 = var7_6;
                            if (v5 == null) {
                                BuildQueueDistributionProcessor.$$$reportNull$$$0(39);
                            }
                        }
                        catch (Throwable v6) {
                            throw BuildQueueDistributionProcessor.a(v6);
                        }
                        return v5;
                    }
                    var7_7 = var2_2.getAgents();
                    if (!var7_7.isEmpty()) break block56;
                    var8_9 = DistributionDecision.BUILD_SKIPPED_DECISION;
                    try {
                        if (var6_5 != null) {
                            var6_5.close();
                        }
                    }
                    catch (Throwable v7) {
                        throw BuildQueueDistributionProcessor.a(v7);
                    }
                    try {
                        v8 = var8_9;
                        if (v8 == null) {
                            BuildQueueDistributionProcessor.$$$reportNull$$$0(40);
                        }
                    }
                    catch (Throwable v9) {
                        throw BuildQueueDistributionProcessor.a(v9);
                    }
                    return v8;
                }
                var8_10 = var4_4.getDistributor().chooseAgentForBuild(var1_1, var2_2);
                try {
                    if (var8_10.first != null || var8_10.second != null) break block57;
                }
                catch (Throwable v10) {
                    throw BuildQueueDistributionProcessor.a(v10);
                }
                var9_12 = DistributionDecision.BUILD_SKIPPED_DECISION;
                try {
                    if (var6_5 != null) {
                        var6_5.close();
                    }
                }
                catch (Throwable v11) {
                    throw BuildQueueDistributionProcessor.a(v11);
                }
                try {
                    v12 = var9_12;
                    if (v12 == null) {
                        BuildQueueDistributionProcessor.$$$reportNull$$$0(41);
                    }
                }
                catch (Throwable v13) {
                    throw BuildQueueDistributionProcessor.a(v13);
                }
                return v12;
            }
            if (var8_10.first != null) break block58;
            var9_13 = new DistributionDecision(false, (WaitReason)var8_10.second);
            try {
                if (var6_5 != null) {
                    var6_5.close();
                }
            }
            catch (Throwable v14) {
                throw BuildQueueDistributionProcessor.a(v14);
            }
            try {
                v15 = var9_13;
                if (v15 == null) {
                    BuildQueueDistributionProcessor.$$$reportNull$$$0(42);
                }
            }
            catch (Throwable v16) {
                throw BuildQueueDistributionProcessor.a(v16);
            }
            return v15;
        }
        var5_14 = var8_10.first;
        try {
            if (var6_5 == null) ** GOTO lbl120
            var6_5.close();
        }
        catch (Throwable v17) {
            throw BuildQueueDistributionProcessor.a(v17);
        }
lbl120:
        // 2 sources

        var6_5 = var3_3.distributeBuild(var1_1, var5_14);
        try {
            if (var6_5.isBuildDistributed()) {
                this.myDistributedBuilds.put(var1_1, BuildQueueDistributionProcessor.toBuildAgent(var5_14));
                var4_4.addDistributedBuild(var1_1, var5_14);
            }
        }
        catch (Throwable v18) {
            throw BuildQueueDistributionProcessor.a(v18);
        }
        try {
            v19 = var6_5;
            if (v19 == null) {
                BuildQueueDistributionProcessor.$$$reportNull$$$0(43);
            }
        }
        catch (Throwable v20) {
            throw BuildQueueDistributionProcessor.a(v20);
        }
        return v19;
    }

    private DistributionDecision distributeCompositeBuild(@NotNull QueuedBuildInfo queuedBuildInfo, @NotNull Map<String, Object> map, @NotNull CompositeBuildStarter compositeBuildStarter) {
        try {
            if (queuedBuildInfo == null) {
                BuildQueueDistributionProcessor.$$$reportNull$$$0(44);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildQueueDistributionProcessor.a(illegalStateException);
        }
        try {
            if (map == null) {
                BuildQueueDistributionProcessor.$$$reportNull$$$0(45);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildQueueDistributionProcessor.a(illegalStateException);
        }
        try {
            if (compositeBuildStarter == null) {
                BuildQueueDistributionProcessor.$$$reportNull$$$0(46);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildQueueDistributionProcessor.a(illegalStateException);
        }
        this.myDistributedBuilds.put(queuedBuildInfo, null);
        return (DistributionDecision)this.executeWithThreadNameNotNull("Starting composite build with id " + queuedBuildInfo.getBuildPromotionInfo().getId(), () -> compositeBuildStarter.startBuild(queuedBuildInfo, map));
    }

    private boolean showDebugLog() {
        boolean bl;
        block5: {
            block4: {
                try {
                    try {
                        if (!Loggers.SERVER.isDebugEnabled() || this.myEmulationMode) break block4;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildQueueDistributionProcessor.a(illegalStateException);
                    }
                    bl = true;
                    break block5;
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildQueueDistributionProcessor.a(illegalStateException);
                }
            }
            bl = false;
        }
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @NotNull
    private Pair<WaitReason, Set<Class<? extends StartBuildPrecondition>>> checkHasWaitReason(@NotNull QueuedBuildInfo queuedBuildInfo, @NotNull Collection<StartBuildPrecondition> collection, @Nullable WaitReason waitReason) {
        HashSet<Class> hashSet;
        Stoppable stoppable;
        block27: {
            Pair pair;
            block26: {
                try {
                    if (queuedBuildInfo == null) {
                        BuildQueueDistributionProcessor.$$$reportNull$$$0(47);
                    }
                }
                catch (Exception exception) {
                    throw BuildQueueDistributionProcessor.a(exception);
                }
                try {
                    if (collection == null) {
                        BuildQueueDistributionProcessor.$$$reportNull$$$0(48);
                    }
                }
                catch (Exception exception) {
                    throw BuildQueueDistributionProcessor.a(exception);
                }
                stoppable = this.myBuildQueueProcessingMetrics.getPreconditionsTimeMetric(this.myEmulationMode).startMsecsTimer();
                try {
                    hashSet = new HashSet<Class>();
                    ReadonlyMap readonlyMap = new ReadonlyMap(this.myDistributedBuilds);
                    for (StartBuildPrecondition startBuildPrecondition : collection) {
                        try {
                            WaitReason waitReason2 = startBuildPrecondition.canStart(queuedBuildInfo, (Map)readonlyMap, (BuildDistributorInput)this.myDistributorInput, this.myEmulationMode);
                            try {
                                hashSet.add(startBuildPrecondition.getClass());
                                if (waitReason2 == null || waitReason2.equals((Object)waitReason)) continue;
                            }
                            catch (Exception exception) {
                                throw BuildQueueDistributionProcessor.a(exception);
                            }
                            pair = Pair.create((Object)waitReason2, hashSet);
                            break block26;
                        }
                        catch (Exception exception) {
                            Loggers.SERVER.warnAndDebugDetails("Exception in " + startBuildPrecondition.getClass().getName(), (Throwable)exception);
                        }
                    }
                    break block27;
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
            }
            try {
                if (stoppable != null) {
                    stoppable.close();
                }
            }
            catch (Exception exception) {
                throw BuildQueueDistributionProcessor.a(exception);
            }
            try {
                Pair pair2 = pair;
                if (pair2 != null) Pair pair3;
                return pair3;
                BuildQueueDistributionProcessor.$$$reportNull$$$0(49);
                return pair2;
            }
            catch (Exception exception) {
                throw BuildQueueDistributionProcessor.a(exception);
            }
        }
        Pair pair = Pair.create(null, hashSet);
        try {
            if (stoppable != null) {
                stoppable.close();
            }
        }
        catch (Exception exception) {
            throw BuildQueueDistributionProcessor.a(exception);
        }
        try {
            Pair pair4 = pair;
            if (pair4 != null) Pair pair5;
            return pair5;
            BuildQueueDistributionProcessor.$$$reportNull$$$0(50);
            return pair4;
        }
        catch (Exception exception) {
            throw BuildQueueDistributionProcessor.a(exception);
        }
    }

    private <T> T executeWithThreadName(String string, Callable<T> callable) {
        try {
            block4: {
                try {
                    if (!ThreadStat.isPerfStatEnabled() || this.myEmulationMode) break block4;
                }
                catch (Exception exception) {
                    throw BuildQueueDistributionProcessor.a(exception);
                }
                return (T)NamedThreadFactory.executeWithNewThreadName((String)string, callable);
            }
            return callable.call();
        }
        catch (Exception exception) {
            ExceptionUtil.rethrowAsRuntimeException((Throwable)exception);
            return null;
        }
    }

    @NotNull
    private <T> T executeWithThreadNameNotNull(String string, NotNullCallable<T> notNullCallable) {
        block7: {
            Object object;
            try {
                try {
                    if (!ThreadStat.isPerfStatEnabled() || this.myEmulationMode) break block7;
                }
                catch (Exception exception) {
                    throw BuildQueueDistributionProcessor.a(exception);
                }
                object = NamedThreadFactory.executeWithNewThreadName((String)string, notNullCallable);
            }
            catch (Exception exception) {
                ExceptionUtil.rethrowAsRuntimeException((Throwable)exception);
                throw new IllegalStateException("Unreachable statement");
            }
            if (object == null) {
                BuildQueueDistributionProcessor.$$$reportNull$$$0(51);
            }
            return (T)object;
        }
        Object object = notNullCallable.call();
        if (object == null) {
            BuildQueueDistributionProcessor.$$$reportNull$$$0(52);
        }
        return (T)object;
    }

    protected void setReason(@NotNull QueuedBuildInfo queuedBuildInfo, @NotNull WaitReason waitReason, @NotNull SetWaitReasonCallback setWaitReasonCallback) {
        try {
            if (queuedBuildInfo == null) {
                BuildQueueDistributionProcessor.$$$reportNull$$$0(53);
            }
        }
        catch (Throwable throwable) {
            throw BuildQueueDistributionProcessor.a(throwable);
        }
        try {
            if (waitReason == null) {
                BuildQueueDistributionProcessor.$$$reportNull$$$0(54);
            }
        }
        catch (Throwable throwable) {
            throw BuildQueueDistributionProcessor.a(throwable);
        }
        try {
            if (setWaitReasonCallback == null) {
                BuildQueueDistributionProcessor.$$$reportNull$$$0(55);
            }
        }
        catch (Throwable throwable) {
            throw BuildQueueDistributionProcessor.a(throwable);
        }
        try (Stoppable stoppable = this.myBuildQueueProcessingMetrics.getOtherActivitiesMetric(this.myEmulationMode).startMsecsTimer();){
            this.myWaitReasons.put(queuedBuildInfo, waitReason);
            setWaitReasonCallback.waitReasonSet(queuedBuildInfo, waitReason);
        }
    }

    private void setReason(@NotNull QueuedBuildInfo queuedBuildInfo, @NotNull WaitReason waitReason) {
        try {
            if (queuedBuildInfo == null) {
                BuildQueueDistributionProcessor.$$$reportNull$$$0(56);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildQueueDistributionProcessor.a(illegalStateException);
        }
        try {
            if (waitReason == null) {
                BuildQueueDistributionProcessor.$$$reportNull$$$0(57);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildQueueDistributionProcessor.a(illegalStateException);
        }
        this.setReason(queuedBuildInfo, waitReason, this.mySetWaitReasonCallback);
    }

    private static SBuildAgent toBuildAgent(Object object) {
        try {
            if (object instanceof SBuildAgent) {
                return (SBuildAgent)object;
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildQueueDistributionProcessor.a(illegalStateException);
        }
        return null;
    }

    private /* synthetic */ Pair lambda$doDistribute$2(QueuedBuildInfo queuedBuildInfo, Collection collection) throws Exception {
        return this.checkHasWaitReason(queuedBuildInfo, collection, null);
    }

    private static /* synthetic */ QueuedBuildInfo lambda$doDistribute$1(QueuedBuildInfo queuedBuildInfo) {
        return queuedBuildInfo;
    }

    private static /* synthetic */ Long lambda$doDistribute$0(QueuedBuildInfo queuedBuildInfo) {
        return queuedBuildInfo.getBuildPromotionInfo().getId();
    }

    static {
        boolean bl;
        try {
            bl = !BuildQueueDistributionProcessor.class.desiredAssertionStatus();
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildQueueDistributionProcessor.a(illegalStateException);
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

    private static Throwable a(Throwable throwable) {
        return throwable;
    }
}
