/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jetbrains.buildServer.BuildAgent
 *  jetbrains.buildServer.serverSide.BuildPromotionEx
 *  jetbrains.buildServer.serverSide.DependencyConsumer$Result
 *  jetbrains.buildServer.serverSide.QueuedBuildEx
 *  jetbrains.buildServer.serverSide.TeamCityProperties
 *  jetbrains.buildServer.serverSide.buildDistribution.BuildDistributorInput
 *  jetbrains.buildServer.serverSide.buildDistribution.BuildPromotionInfo
 *  jetbrains.buildServer.serverSide.buildDistribution.QueuedBuildInfo
 *  jetbrains.buildServer.serverSide.buildDistribution.StartBuildPrecondition
 *  jetbrains.buildServer.serverSide.buildDistribution.WaitReason
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.BuildDistributorInputEx
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.CheckingForChangesPrecondition
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.StandardWaitReasons
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.VersionedSettingsPrecondition
 *  jetbrains.buildServer.util.positioning.PositionAware
 *  jetbrains.buildServer.util.positioning.PositionConstraint
 *  org.jetbrains.annotations.NotNull
 */
package jetbrains.buildServer.serverSide.impl.buildDistribution;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import jetbrains.buildServer.BuildAgent;
import jetbrains.buildServer.serverSide.BuildPromotionEx;
import jetbrains.buildServer.serverSide.DependencyConsumer;
import jetbrains.buildServer.serverSide.QueuedBuildEx;
import jetbrains.buildServer.serverSide.TeamCityProperties;
import jetbrains.buildServer.serverSide.buildDistribution.BuildDistributorInput;
import jetbrains.buildServer.serverSide.buildDistribution.BuildPromotionInfo;
import jetbrains.buildServer.serverSide.buildDistribution.QueuedBuildInfo;
import jetbrains.buildServer.serverSide.buildDistribution.StartBuildPrecondition;
import jetbrains.buildServer.serverSide.buildDistribution.WaitReason;
import jetbrains.buildServer.serverSide.impl.buildDistribution.BuildDistributorInputEx;
import jetbrains.buildServer.serverSide.impl.buildDistribution.CheckingForChangesPrecondition;
import jetbrains.buildServer.serverSide.impl.buildDistribution.StandardWaitReasons;
import jetbrains.buildServer.serverSide.impl.buildDistribution.VersionedSettingsPrecondition;
import jetbrains.buildServer.util.positioning.PositionAware;
import jetbrains.buildServer.util.positioning.PositionConstraint;
import org.jetbrains.annotations.NotNull;

/*
 * Exception performing whole class analysis ignored.
 */
public class BuildDependenciesPrecondition
implements StartBuildPrecondition,
PositionAware {
    public static final String ORDER_ID = BuildDependenciesPrecondition.class.getSimpleName();

    public WaitReason canStart(@NotNull QueuedBuildInfo queuedBuildInfo, @NotNull Map<QueuedBuildInfo, BuildAgent> map, @NotNull BuildDistributorInput buildDistributorInput, boolean bl) {
        block15: {
            block16: {
                boolean bl2;
                try {
                    if (queuedBuildInfo == null) {
                        BuildDependenciesPrecondition.$$$reportNull$$$0(0);
                    }
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw BuildDependenciesPrecondition.a(illegalArgumentException);
                }
                try {
                    if (map == null) {
                        BuildDependenciesPrecondition.$$$reportNull$$$0(1);
                    }
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw BuildDependenciesPrecondition.a(illegalArgumentException);
                }
                try {
                    if (buildDistributorInput == null) {
                        BuildDependenciesPrecondition.$$$reportNull$$$0(2);
                    }
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw BuildDependenciesPrecondition.a(illegalArgumentException);
                }
                try {
                    bl2 = bl ? this.canStart(queuedBuildInfo, buildDistributorInput, map) : this.canStart(queuedBuildInfo, (BuildDistributorInputEx)buildDistributorInput);
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw BuildDependenciesPrecondition.a(illegalArgumentException);
                }
                boolean bl3 = bl2;
                try {
                    try {
                        if (bl3) break block15;
                        if (!queuedBuildInfo.getBuildPromotionInfo().isCompositeBuild()) break block16;
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        throw BuildDependenciesPrecondition.a(illegalArgumentException);
                    }
                    return StandardWaitReasons.WAITING_FOR_THE_FIRST_DEPENDENCY_TO_START;
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw BuildDependenciesPrecondition.a(illegalArgumentException);
                }
            }
            return StandardWaitReasons.DEPENDENCY_NOT_READY;
        }
        return null;
    }

    private boolean canStart(@NotNull QueuedBuildInfo queuedBuildInfo, @NotNull BuildDistributorInput buildDistributorInput, @NotNull Map<QueuedBuildInfo, BuildAgent> map) {
        Set set;
        try {
            if (queuedBuildInfo == null) {
                BuildDependenciesPrecondition.$$$reportNull$$$0(3);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildDependenciesPrecondition.a(illegalArgumentException);
        }
        try {
            if (buildDistributorInput == null) {
                BuildDependenciesPrecondition.$$$reportNull$$$0(4);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildDependenciesPrecondition.a(illegalArgumentException);
        }
        try {
            if (map == null) {
                BuildDependenciesPrecondition.$$$reportNull$$$0(5);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildDependenciesPrecondition.a(illegalArgumentException);
        }
        BuildPromotionInfo buildPromotionInfo = queuedBuildInfo.getBuildPromotionInfo();
        if (buildPromotionInfo.isCompositeBuild()) {
            for (QueuedBuildInfo string2 : map.keySet()) {
                set = (BuildPromotionEx)string2.getBuildPromotionInfo();
                Object object = new boolean[]{false};
                try {
                    set.traverseDependedOnMe(arg_0 -> BuildDependenciesPrecondition.lambda$canStart$0(buildPromotionInfo, (boolean[])object, arg_0));
                    if (object[0] == false) continue;
                    return true;
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw BuildDependenciesPrecondition.a(illegalArgumentException);
                }
            }
        }
        BuildDistributorInputEx buildDistributorInputEx = (BuildDistributorInputEx)buildDistributorInput;
        String string = this.getClass().getName();
        set = (Set)buildDistributorInputEx.getCustomData(string, () -> {
            HashSet<Long> hashSet = new HashSet<Long>();
            for (QueuedBuildInfo queuedBuildInfo : buildDistributorInput.getQueue()) {
                hashSet.add(queuedBuildInfo.getBuildPromotionInfo().getId());
            }
            for (QueuedBuildInfo queuedBuildInfo : buildDistributorInput.getRunningBuilds()) {
                hashSet.add(queuedBuildInfo.getBuildPromotionInfo().getId());
            }
            return hashSet;
        });
        for (BuildPromotionInfo buildPromotionInfo2 : queuedBuildInfo.getBuildPromotionInfo().getDirectDependencies()) {
            try {
                if (!set.contains(buildPromotionInfo2.getId())) continue;
                return false;
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw BuildDependenciesPrecondition.a(illegalArgumentException);
            }
        }
        return true;
    }

    private boolean canStart(@NotNull QueuedBuildInfo queuedBuildInfo, @NotNull BuildDistributorInputEx buildDistributorInputEx) {
        try {
            if (queuedBuildInfo == null) {
                BuildDependenciesPrecondition.$$$reportNull$$$0(6);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildDependenciesPrecondition.a(illegalArgumentException);
        }
        try {
            if (buildDistributorInputEx == null) {
                BuildDependenciesPrecondition.$$$reportNull$$$0(7);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildDependenciesPrecondition.a(illegalArgumentException);
        }
        if (queuedBuildInfo.getBuildPromotionInfo().isCompositeBuild()) {
            boolean bl = TeamCityProperties.getBooleanOrTrue((String)"teamcity.queueDistribution.checkStartedDependenciesForComposite");
            try {
                if (bl) {
                    return this.dependenciesStartedOrDone(queuedBuildInfo, buildDistributorInputEx);
                }
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw BuildDependenciesPrecondition.a(illegalArgumentException);
            }
        }
        return this.allDependenciesFinished(queuedBuildInfo);
    }

    private boolean allDependenciesFinished(@NotNull QueuedBuildInfo queuedBuildInfo) {
        try {
            if (queuedBuildInfo == null) {
                BuildDependenciesPrecondition.$$$reportNull$$$0(8);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildDependenciesPrecondition.a(illegalArgumentException);
        }
        BuildPromotionEx buildPromotionEx = (BuildPromotionEx)queuedBuildInfo.getBuildPromotionInfo();
        return buildPromotionEx.allDependenciesFinished();
    }

    private boolean dependenciesStartedOrDone(@NotNull QueuedBuildInfo queuedBuildInfo, @NotNull BuildDistributorInputEx buildDistributorInputEx) {
        try {
            if (queuedBuildInfo == null) {
                BuildDependenciesPrecondition.$$$reportNull$$$0(9);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildDependenciesPrecondition.a(illegalArgumentException);
        }
        try {
            if (buildDistributorInputEx == null) {
                BuildDependenciesPrecondition.$$$reportNull$$$0(10);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildDependenciesPrecondition.a(illegalArgumentException);
        }
        int[] nArray = new int[]{0};
        boolean[] blArray = new boolean[1];
        HashSet hashSet = new HashSet();
        try {
            ((BuildPromotionEx)queuedBuildInfo.getBuildPromotionInfo()).traverseDependencies(buildPromotionEx -> {
                block7: {
                    nArray[0] = nArray[0] + 1;
                    Date date = buildPromotionEx.getServerStartDate();
                    try {
                        try {
                            if (date == null || !date.after(((QueuedBuildEx)queuedBuildInfo).getWhenQueued())) break block7;
                        }
                        catch (IllegalArgumentException illegalArgumentException) {
                            throw BuildDependenciesPrecondition.a(illegalArgumentException);
                        }
                        blArray[0] = true;
                        return DependencyConsumer.Result.STOP;
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        throw BuildDependenciesPrecondition.a(illegalArgumentException);
                    }
                }
                try {
                    if (buildPromotionEx.getAssociatedBuildId() != null) {
                        hashSet.add(buildPromotionEx.getAssociatedBuildId());
                    }
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw BuildDependenciesPrecondition.a(illegalArgumentException);
                }
                return DependencyConsumer.Result.CONTINUE;
            });
            if (blArray[0]) {
                return true;
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildDependenciesPrecondition.a(illegalArgumentException);
        }
        try {
            if (hashSet.size() == nArray[0]) {
                return true;
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildDependenciesPrecondition.a(illegalArgumentException);
        }
        Iterator iterator = hashSet.iterator();
        while (iterator.hasNext()) {
            long l = (Long)iterator.next();
            try {
                if (buildDistributorInputEx.findRunningBuildById(l) == null) continue;
                return true;
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw BuildDependenciesPrecondition.a(illegalArgumentException);
            }
        }
        return false;
    }

    @NotNull
    public String getOrderId() {
        String string;
        try {
            string = ORDER_ID;
            if (string == null) {
                BuildDependenciesPrecondition.$$$reportNull$$$0(11);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildDependenciesPrecondition.a(illegalArgumentException);
        }
        return string;
    }

    @NotNull
    public PositionConstraint getConstraint() {
        PositionConstraint positionConstraint;
        try {
            positionConstraint = PositionConstraint.after((String[])new String[]{CheckingForChangesPrecondition.ORDER_ID, VersionedSettingsPrecondition.ORDER_ID});
            if (positionConstraint == null) {
                BuildDependenciesPrecondition.$$$reportNull$$$0(12);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildDependenciesPrecondition.a(illegalArgumentException);
        }
        return positionConstraint;
    }

    private static /* synthetic */ DependencyConsumer.Result lambda$canStart$0(BuildPromotionInfo buildPromotionInfo, boolean[] blArray, BuildPromotionEx buildPromotionEx) {
        try {
            if (buildPromotionEx.getId() == buildPromotionInfo.getId()) {
                blArray[0] = true;
                return DependencyConsumer.Result.STOP;
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildDependenciesPrecondition.a(illegalArgumentException);
        }
        return DependencyConsumer.Result.CONTINUE;
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
