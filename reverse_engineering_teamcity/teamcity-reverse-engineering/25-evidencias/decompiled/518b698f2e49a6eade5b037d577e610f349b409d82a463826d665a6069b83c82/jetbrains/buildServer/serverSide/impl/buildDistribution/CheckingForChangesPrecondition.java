/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jetbrains.buildServer.BuildAgent
 *  jetbrains.buildServer.log.ThrottleLogger
 *  jetbrains.buildServer.serverSide.BuildPromotionEx
 *  jetbrains.buildServer.serverSide.BuildQueueEx
 *  jetbrains.buildServer.serverSide.DependencyConsumer$Result
 *  jetbrains.buildServer.serverSide.TeamCityProperties
 *  jetbrains.buildServer.serverSide.buildDistribution.BuildDistributorInput
 *  jetbrains.buildServer.serverSide.buildDistribution.QueuedBuildInfo
 *  jetbrains.buildServer.serverSide.buildDistribution.SimpleWaitReason
 *  jetbrains.buildServer.serverSide.buildDistribution.WaitReason
 *  jetbrains.buildServer.serverSide.impl.QueuedBuildsChangesCollector
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.BuildQueueOptimizerAwarePrecondition
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.StandardWaitReasons
 *  jetbrains.buildServer.util.Dates
 *  jetbrains.buildServer.util.TimePrinter
 *  jetbrains.buildServer.util.positioning.PositionAware
 *  jetbrains.buildServer.util.positioning.PositionConstraint
 *  org.jetbrains.annotations.NotNull
 */
package jetbrains.buildServer.serverSide.impl.buildDistribution;

import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import jetbrains.buildServer.BuildAgent;
import jetbrains.buildServer.log.ThrottleLogger;
import jetbrains.buildServer.serverSide.BuildPromotionEx;
import jetbrains.buildServer.serverSide.BuildQueueEx;
import jetbrains.buildServer.serverSide.DependencyConsumer;
import jetbrains.buildServer.serverSide.TeamCityProperties;
import jetbrains.buildServer.serverSide.buildDistribution.BuildDistributorInput;
import jetbrains.buildServer.serverSide.buildDistribution.QueuedBuildInfo;
import jetbrains.buildServer.serverSide.buildDistribution.SimpleWaitReason;
import jetbrains.buildServer.serverSide.buildDistribution.WaitReason;
import jetbrains.buildServer.serverSide.impl.QueuedBuildsChangesCollector;
import jetbrains.buildServer.serverSide.impl.buildDistribution.BuildQueueOptimizerAwarePrecondition;
import jetbrains.buildServer.serverSide.impl.buildDistribution.StandardWaitReasons;
import jetbrains.buildServer.util.Dates;
import jetbrains.buildServer.util.TimePrinter;
import jetbrains.buildServer.util.positioning.PositionAware;
import jetbrains.buildServer.util.positioning.PositionConstraint;
import org.jetbrains.annotations.NotNull;

/*
 * Exception performing whole class analysis ignored.
 */
public class CheckingForChangesPrecondition
extends BuildQueueOptimizerAwarePrecondition
implements PositionAware {
    private static final ThrottleLogger LOG = ThrottleLogger.getLogger(CheckingForChangesPrecondition.class);
    public static final String ORDER_ID = CheckingForChangesPrecondition.class.getSimpleName();
    public static final SimpleWaitReason CHANGES_NOT_COLLECTED_IN_OPTIMIZATION_CANDIDATE_BUILDS = new SimpleWaitReason("Waiting for the changes collection in other queued builds");
    public static final Set<WaitReason> ALL_WAIT_REASONS = new HashSet<WaitReason>();

    public CheckingForChangesPrecondition(@NotNull BuildQueueEx buildQueueEx) {
        if (buildQueueEx == null) {
            CheckingForChangesPrecondition.$$$reportNull$$$0(0);
        }
        super(buildQueueEx);
    }

    public WaitReason canStart(@NotNull QueuedBuildInfo queuedBuildInfo, @NotNull Map<QueuedBuildInfo, BuildAgent> map, @NotNull BuildDistributorInput buildDistributorInput, boolean bl) {
        try {
            if (queuedBuildInfo == null) {
                CheckingForChangesPrecondition.$$$reportNull$$$0(1);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw CheckingForChangesPrecondition.b(illegalArgumentException);
        }
        try {
            if (map == null) {
                CheckingForChangesPrecondition.$$$reportNull$$$0(2);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw CheckingForChangesPrecondition.b(illegalArgumentException);
        }
        try {
            if (buildDistributorInput == null) {
                CheckingForChangesPrecondition.$$$reportNull$$$0(3);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw CheckingForChangesPrecondition.b(illegalArgumentException);
        }
        BuildPromotionEx buildPromotionEx2 = (BuildPromotionEx)queuedBuildInfo.getBuildPromotionInfo();
        if (!buildPromotionEx2.isChangeCollectingNeeded()) {
            WaitReason waitReason = this.checkChangesCollectionStatusInDependentBuilds(buildPromotionEx2);
            try {
                if (waitReason != null) {
                    return waitReason;
                }
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw CheckingForChangesPrecondition.b(illegalArgumentException);
            }
            try {
                if (!TeamCityProperties.getBoolean((String)"teamcity.checkingForChangesPrecondition.waitForQueuedBuildsBefore")) {
                    return null;
                }
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw CheckingForChangesPrecondition.b(illegalArgumentException);
            }
            try {
                if (this.processPotentialOptimizationCandidates(buildPromotionEx2, buildPromotionEx -> buildPromotionEx.isChangeCollectingNeeded())) {
                    return CHANGES_NOT_COLLECTED_IN_OPTIMIZATION_CANDIDATE_BUILDS;
                }
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw CheckingForChangesPrecondition.b(illegalArgumentException);
            }
            return null;
        }
        try {
            if (buildPromotionEx2.isChangesCollectingInProgress()) {
                return StandardWaitReasons.CHECKING_FOR_CHANGES_IN_PROGRESS;
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw CheckingForChangesPrecondition.b(illegalArgumentException);
        }
        if (this.myBuildQueue.shouldCollectChangesInQueue(buildPromotionEx2)) {
            WaitReason waitReason = this.checkChangesCollectionStatusInDependentBuilds(buildPromotionEx2);
            try {
                if (waitReason != null) {
                    return waitReason;
                }
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw CheckingForChangesPrecondition.b(illegalArgumentException);
            }
            return StandardWaitReasons.WAITING_CHECKING_FOR_CHANGES;
        }
        return null;
    }

    private WaitReason checkChangesCollectionStatusInDependentBuilds(@NotNull BuildPromotionEx buildPromotionEx2) {
        try {
            if (buildPromotionEx2 == null) {
                CheckingForChangesPrecondition.$$$reportNull$$$0(4);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw CheckingForChangesPrecondition.b(illegalArgumentException);
        }
        BuildPromotionEx[] buildPromotionExArray = new BuildPromotionEx[]{null};
        BuildPromotionEx[] buildPromotionExArray2 = new BuildPromotionEx[]{null};
        try {
            buildPromotionEx2.traverseDependedOnMe(buildPromotionEx -> {
                try {
                    if (CheckingForChangesPrecondition.isBrokenBuild(buildPromotionEx)) {
                        return DependencyConsumer.Result.CONTINUE;
                    }
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw CheckingForChangesPrecondition.b(illegalArgumentException);
                }
                try {
                    if (buildPromotionEx.isChangeCollectingNeeded()) {
                        buildPromotionExArray[0] = buildPromotionEx;
                    }
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw CheckingForChangesPrecondition.b(illegalArgumentException);
                }
                try {
                    if (buildPromotionEx.isChangesCollectingInProgress()) {
                        buildPromotionExArray2[0] = buildPromotionEx;
                        return DependencyConsumer.Result.STOP;
                    }
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw CheckingForChangesPrecondition.b(illegalArgumentException);
                }
                return DependencyConsumer.Result.CONTINUE;
            });
            if (buildPromotionExArray[0] != null) {
                CheckingForChangesPrecondition.logDelay(buildPromotionEx2, " awaits checking for changes in ", buildPromotionExArray);
                return StandardWaitReasons.CHECKING_FOR_CHANGES_IN_PROGRESS_IN_DEPS;
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw CheckingForChangesPrecondition.b(illegalArgumentException);
        }
        try {
            if (buildPromotionExArray2[0] != null) {
                CheckingForChangesPrecondition.logDelay(buildPromotionEx2, " awaits starting checking for changes in ", buildPromotionExArray2);
                return StandardWaitReasons.WAITING_CHECKING_FOR_CHANGES_IN_DEPS;
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw CheckingForChangesPrecondition.b(illegalArgumentException);
        }
        return null;
    }

    public static boolean isBrokenBuild(@NotNull BuildPromotionEx buildPromotionEx) {
        Date date;
        block12: {
            try {
                if (buildPromotionEx == null) {
                    CheckingForChangesPrecondition.$$$reportNull$$$0(5);
                }
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw CheckingForChangesPrecondition.b(illegalArgumentException);
            }
            date = buildPromotionEx.getQueuedDate();
            try {
                try {
                    try {
                        if (date == null || buildPromotionEx.getQueuedBuild() != null) break block12;
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        throw CheckingForChangesPrecondition.b(illegalArgumentException);
                    }
                    if (Math.abs(Dates.now().getTime() - date.getTime()) <= CheckingForChangesPrecondition.getQueuedTimeDelay()) break block12;
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw CheckingForChangesPrecondition.b(illegalArgumentException);
                }
                return true;
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw CheckingForChangesPrecondition.b(illegalArgumentException);
            }
        }
        try {
            if (date == null) {
                return true;
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw CheckingForChangesPrecondition.b(illegalArgumentException);
        }
        return false;
    }

    private static void logDelay(@NotNull BuildPromotionEx buildPromotionEx, String string, BuildPromotionEx[] buildPromotionExArray) {
        long l;
        try {
            if (buildPromotionEx == null) {
                CheckingForChangesPrecondition.$$$reportNull$$$0(6);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw CheckingForChangesPrecondition.b(illegalArgumentException);
        }
        try {
            l = buildPromotionEx.getQueuedDate() != null ? Dates.now().getTime() - buildPromotionEx.getQueuedDate().getTime() : 0L;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw CheckingForChangesPrecondition.b(illegalArgumentException);
        }
        long l2 = l;
        long l3 = TeamCityProperties.getLong((String)"teamcity.checkingForChangesPrecondition.queuedTime.warn.millis", (long)600000L);
        if (l2 > l3) {
            String string2 = "Promotion {} " + string + "{} for {}";
            LOG.info(string2.intern(), new Object[]{buildPromotionEx, buildPromotionExArray[0], TimePrinter.formatMillis((long)l2)});
        }
    }

    private static long getQueuedTimeDelay() {
        return TeamCityProperties.getLong((String)"teamcity.checkingForChangesPrecondition.queuedTime.maxDelay.millis", (long)30000L);
    }

    @NotNull
    public String getOrderId() {
        String string;
        try {
            string = ORDER_ID;
            if (string == null) {
                CheckingForChangesPrecondition.$$$reportNull$$$0(7);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw CheckingForChangesPrecondition.b(illegalArgumentException);
        }
        return string;
    }

    @NotNull
    public PositionConstraint getConstraint() {
        PositionConstraint positionConstraint;
        try {
            positionConstraint = PositionConstraint.after((String[])new String[]{QueuedBuildsChangesCollector.ORDER_ID});
            if (positionConstraint == null) {
                CheckingForChangesPrecondition.$$$reportNull$$$0(8);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw CheckingForChangesPrecondition.b(illegalArgumentException);
        }
        return positionConstraint;
    }

    static {
        ALL_WAIT_REASONS.add(StandardWaitReasons.CHECKING_FOR_CHANGES_IN_PROGRESS);
        ALL_WAIT_REASONS.add(StandardWaitReasons.WAITING_CHECKING_FOR_CHANGES);
        ALL_WAIT_REASONS.add(StandardWaitReasons.CHECKING_FOR_CHANGES_IN_PROGRESS_IN_DEPS);
        ALL_WAIT_REASONS.add(StandardWaitReasons.WAITING_CHECKING_FOR_CHANGES_IN_DEPS);
        ALL_WAIT_REASONS.add((WaitReason)CHANGES_NOT_COLLECTED_IN_OPTIMIZATION_CANDIDATE_BUILDS);
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

    private static IllegalArgumentException b(IllegalArgumentException illegalArgumentException) {
        return illegalArgumentException;
    }
}
