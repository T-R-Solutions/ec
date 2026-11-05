/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.intellij.openapi.diagnostic.Logger
 *  jetbrains.buildServer.ExtensionHolder
 *  jetbrains.buildServer.TeamCityExtension
 *  jetbrains.buildServer.log.Loggable
 *  jetbrains.buildServer.serverSide.Branch
 *  jetbrains.buildServer.serverSide.BuildPromotion
 *  jetbrains.buildServer.serverSide.BuildPromotionEx
 *  jetbrains.buildServer.serverSide.BuildTypeEx
 *  jetbrains.buildServer.serverSide.BuildTypeNotFoundException
 *  jetbrains.buildServer.serverSide.BuildTypeOptions
 *  jetbrains.buildServer.serverSide.BuildsLimitingMode
 *  jetbrains.buildServer.serverSide.RunningBuildEx
 *  jetbrains.buildServer.serverSide.SBuildType
 *  jetbrains.buildServer.serverSide.ServerResponsibility
 *  jetbrains.buildServer.serverSide.TeamCityProperties
 *  jetbrains.buildServer.serverSide.buildDistribution.BuildDistributionCallback
 *  jetbrains.buildServer.serverSide.buildDistribution.QueuedBuildInfo
 *  jetbrains.buildServer.serverSide.buildDistribution.WaitReason
 *  jetbrains.buildServer.serverSide.impl.LogUtil
 *  jetbrains.buildServer.serverSide.impl.QueuedTasksProcessor
 *  jetbrains.buildServer.serverSide.impl.QueuedTasksProcessor$ManyTasksProcessor
 *  jetbrains.buildServer.serverSide.impl.RunningBuildState
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.FlushQueueExecutor
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.MaxRunningBuildsPrecondition
 *  jetbrains.buildServer.util.Option
 *  jetbrains.buildServer.util.Predicate
 *  org.jetbrains.annotations.NotNull
 */
package jetbrains.buildServer.serverSide.impl.buildDistribution;

import com.intellij.openapi.diagnostic.Logger;
import java.util.List;
import java.util.stream.Collectors;
import jetbrains.buildServer.ExtensionHolder;
import jetbrains.buildServer.TeamCityExtension;
import jetbrains.buildServer.log.Loggable;
import jetbrains.buildServer.serverSide.Branch;
import jetbrains.buildServer.serverSide.BuildPromotion;
import jetbrains.buildServer.serverSide.BuildPromotionEx;
import jetbrains.buildServer.serverSide.BuildTypeEx;
import jetbrains.buildServer.serverSide.BuildTypeNotFoundException;
import jetbrains.buildServer.serverSide.BuildTypeOptions;
import jetbrains.buildServer.serverSide.BuildsLimitingMode;
import jetbrains.buildServer.serverSide.RunningBuildEx;
import jetbrains.buildServer.serverSide.SBuildType;
import jetbrains.buildServer.serverSide.ServerResponsibility;
import jetbrains.buildServer.serverSide.TeamCityProperties;
import jetbrains.buildServer.serverSide.buildDistribution.BuildDistributionCallback;
import jetbrains.buildServer.serverSide.buildDistribution.QueuedBuildInfo;
import jetbrains.buildServer.serverSide.buildDistribution.WaitReason;
import jetbrains.buildServer.serverSide.impl.LogUtil;
import jetbrains.buildServer.serverSide.impl.QueuedTasksProcessor;
import jetbrains.buildServer.serverSide.impl.RunningBuildState;
import jetbrains.buildServer.serverSide.impl.buildDistribution.FlushQueueExecutor;
import jetbrains.buildServer.serverSide.impl.buildDistribution.MaxRunningBuildsPrecondition;
import jetbrains.buildServer.util.Option;
import jetbrains.buildServer.util.Predicate;
import org.jetbrains.annotations.NotNull;

/*
 * Exception performing whole class analysis ignored.
 */
public class RunningBuildsLimiter
implements BuildDistributionCallback {
    private static final Logger LOG = Logger.getInstance(RunningBuildsLimiter.class);
    private final QueuedTasksProcessor<BuildPromotionEx> myProcessor;
    public static final String RUNNING_BUILDS_LIMITING_ENABLED = "teamcity.runningBuildsLimiting.enable";
    private final ServerResponsibility myServerResponsibility;

    public RunningBuildsLimiter(@NotNull ExtensionHolder extensionHolder, @NotNull FlushQueueExecutor flushQueueExecutor, @NotNull ServerResponsibility serverResponsibility) {
        if (extensionHolder == null) {
            RunningBuildsLimiter.$$$reportNull$$$0(0);
        }
        if (flushQueueExecutor == null) {
            RunningBuildsLimiter.$$$reportNull$$$0(1);
        }
        if (serverResponsibility == null) {
            RunningBuildsLimiter.$$$reportNull$$$0(2);
        }
        this.myServerResponsibility = serverResponsibility;
        extensionHolder.registerExtension(BuildDistributionCallback.class, RunningBuildsLimiter.class.getName(), (TeamCityExtension)this);
        int n = TeamCityProperties.getInteger((String)"teamcity.runningBuildsLimiter.queue.size", (int)2000);
        int n2 = TeamCityProperties.getInteger((String)"teamcity.runningBuildsLimiter.maxTasks", (int)1000);
        this.myProcessor = new QueuedTasksProcessor((QueuedTasksProcessor.ManyTasksProcessor)new /* Unavailable Anonymous Inner Class!! */, flushQueueExecutor.getParallelTasksExecutor(), n, n2, "Running builds limiter");
    }

    public void stopObsoleteRunningBuilds(@NotNull List<BuildPromotionEx> list) {
        try {
            if (list == null) {
                RunningBuildsLimiter.$$$reportNull$$$0(3);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw RunningBuildsLimiter.a(buildTypeNotFoundException);
        }
        for (BuildPromotionEx buildPromotionEx : list) {
            RunningBuildEx runningBuildEx;
            List list2;
            int n;
            try {
                if (!this.shouldCancelObsoleteRunningBuilds((BuildPromotion)buildPromotionEx)) {
                    continue;
                }
            }
            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                throw RunningBuildsLimiter.a(buildTypeNotFoundException);
            }
            BuildTypeEx buildTypeEx = buildPromotionEx.getParentBuildType();
            try {
                if (buildTypeEx == null) {
                    return;
                }
            }
            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                throw RunningBuildsLimiter.a(buildTypeNotFoundException);
            }
            Branch branch = buildPromotionEx.getBranch();
            int n2 = buildTypeEx.getMaximumNumberOfBuilds();
            try {
                n = buildPromotionEx.getMaximumNumberOfBuildsPerBranch();
            }
            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                return;
            }
            Predicate predicate = sRunningBuild -> {
                block15: {
                    Branch branch2;
                    block14: {
                        try {
                            if (sRunningBuild.isInterrupted()) {
                                return false;
                            }
                        }
                        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                            throw RunningBuildsLimiter.a(buildTypeNotFoundException);
                        }
                        try {
                            if (sRunningBuild.getBuildId() == buildPromotionEx.getId()) {
                                return false;
                            }
                        }
                        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                            throw RunningBuildsLimiter.a(buildTypeNotFoundException);
                        }
                        branch2 = sRunningBuild.getBuildPromotion().getBranch();
                        try {
                            try {
                                if (branch2 != null || branch != null) break block14;
                            }
                            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                                throw RunningBuildsLimiter.a(buildTypeNotFoundException);
                            }
                            return true;
                        }
                        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                            throw RunningBuildsLimiter.a(buildTypeNotFoundException);
                        }
                    }
                    try {
                        try {
                            if (branch2 == null || !branch2.equals((Object)branch)) break block15;
                        }
                        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                            throw RunningBuildsLimiter.a(buildTypeNotFoundException);
                        }
                        return true;
                    }
                    catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                        throw RunningBuildsLimiter.a(buildTypeNotFoundException);
                    }
                }
                return false;
            };
            Predicate predicate2 = sRunningBuild -> {
                boolean bl;
                try {
                    if (sRunningBuild.isInterrupted()) {
                        return false;
                    }
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw RunningBuildsLimiter.a(buildTypeNotFoundException);
                }
                try {
                    bl = sRunningBuild.getBuildId() != buildPromotionEx.getId();
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw RunningBuildsLimiter.a(buildTypeNotFoundException);
                }
                return bl;
            };
            if (n > 0) {
                while ((list2 = buildTypeEx.getRunningBuilds().stream().filter(sRunningBuild -> predicate.apply(sRunningBuild)).collect(Collectors.toList())).size() >= n) {
                    LOG.info("The number of running builds for \"" + LogUtil.describe((SBuildType)buildTypeEx) + "\" in branch \"" + LogUtil.describe((Loggable)branch) + "\" has exceeded the limit: " + n + ", cancelling the oldest build");
                    runningBuildEx = (RunningBuildEx)list2.get(list2.size() - 1);
                    runningBuildEx.stop(RunningBuildState.INTERRUPTED_BY_SYSTEM, null, "Canceled because the running build limit was reached", false);
                }
            }
            if (n2 <= 0) continue;
            while ((list2 = buildTypeEx.getRunningBuilds().stream().filter(sRunningBuild -> predicate2.apply(sRunningBuild)).collect(Collectors.toList())).size() >= n2) {
                LOG.info("The number of running builds for \"" + LogUtil.describe((SBuildType)buildTypeEx) + "\" has exceeded the limit: " + n2 + ", cancelling the oldest build");
                runningBuildEx = (RunningBuildEx)list2.get(list2.size() - 1);
                runningBuildEx.stop(RunningBuildState.INTERRUPTED_BY_SYSTEM, null, "Canceled because the running build limit was reached", false);
            }
        }
    }

    private boolean isEnabled() {
        boolean bl;
        block5: {
            block4: {
                try {
                    try {
                        if (!TeamCityProperties.getBooleanOrTrue((String)"teamcity.runningBuildsLimiting.enable") || !this.myServerResponsibility.canManageBuilds()) break block4;
                    }
                    catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                        throw RunningBuildsLimiter.a(buildTypeNotFoundException);
                    }
                    bl = true;
                    break block5;
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw RunningBuildsLimiter.a(buildTypeNotFoundException);
                }
            }
            bl = false;
        }
        return bl;
    }

    public void distributionStarted() {
    }

    public void distributionFinished() {
    }

    public void waitReasonSet(@NotNull QueuedBuildInfo queuedBuildInfo, @NotNull WaitReason waitReason) {
        block10: {
            try {
                if (queuedBuildInfo == null) {
                    RunningBuildsLimiter.$$$reportNull$$$0(4);
                }
            }
            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                throw RunningBuildsLimiter.a(buildTypeNotFoundException);
            }
            try {
                if (waitReason == null) {
                    RunningBuildsLimiter.$$$reportNull$$$0(5);
                }
            }
            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                throw RunningBuildsLimiter.a(buildTypeNotFoundException);
            }
            try {
                try {
                    if (!MaxRunningBuildsPrecondition.ALL_WAIT_REASONS.contains(waitReason) || !this.shouldCancelObsoleteRunningBuilds((BuildPromotion)((BuildPromotionEx)queuedBuildInfo.getBuildPromotionInfo()))) break block10;
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw RunningBuildsLimiter.a(buildTypeNotFoundException);
                }
                this.myProcessor.addToQueue((Object)((BuildPromotionEx)queuedBuildInfo.getBuildPromotionInfo()));
            }
            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                throw RunningBuildsLimiter.a(buildTypeNotFoundException);
            }
        }
    }

    private boolean shouldCancelObsoleteRunningBuilds(@NotNull BuildPromotion buildPromotion) {
        boolean bl;
        try {
            if (buildPromotion == null) {
                RunningBuildsLimiter.$$$reportNull$$$0(6);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw RunningBuildsLimiter.a(buildTypeNotFoundException);
        }
        try {
            if (!this.isEnabled()) {
                return false;
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw RunningBuildsLimiter.a(buildTypeNotFoundException);
        }
        SBuildType sBuildType = buildPromotion.getParentBuildType();
        try {
            if (sBuildType == null) {
                return false;
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw RunningBuildsLimiter.a(buildTypeNotFoundException);
        }
        try {
            bl = sBuildType.getOption((Option)BuildTypeOptions.BT_RUNNING_BUILDS_LIMITING_MODE) == BuildsLimitingMode.CANCEL_OLDEST_RUNNING_BUILD;
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw RunningBuildsLimiter.a(buildTypeNotFoundException);
        }
        return bl;
    }

    /*
     * Exception decompiling
     */
    private static /* synthetic */ void $$$reportNull$$$0(int var0) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 2[SWITCH]
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

    private static BuildTypeNotFoundException a(BuildTypeNotFoundException buildTypeNotFoundException) {
        return buildTypeNotFoundException;
    }
}
