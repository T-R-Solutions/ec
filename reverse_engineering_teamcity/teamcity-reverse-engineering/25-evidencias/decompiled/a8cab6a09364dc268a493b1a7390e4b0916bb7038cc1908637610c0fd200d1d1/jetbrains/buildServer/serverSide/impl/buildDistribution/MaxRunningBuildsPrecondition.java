/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jetbrains.buildServer.BuildAgent
 *  jetbrains.buildServer.serverSide.Branch
 *  jetbrains.buildServer.serverSide.BuildPromotion
 *  jetbrains.buildServer.serverSide.BuildPromotionEx
 *  jetbrains.buildServer.serverSide.BuildTypeEx
 *  jetbrains.buildServer.serverSide.BuildTypeNotFoundException
 *  jetbrains.buildServer.serverSide.SBuildType
 *  jetbrains.buildServer.serverSide.buildDistribution.BuildDistributorInput
 *  jetbrains.buildServer.serverSide.buildDistribution.BuildPromotionInfo
 *  jetbrains.buildServer.serverSide.buildDistribution.QueuedBuildInfo
 *  jetbrains.buildServer.serverSide.buildDistribution.SimpleWaitReason
 *  jetbrains.buildServer.serverSide.buildDistribution.StartBuildPrecondition
 *  jetbrains.buildServer.serverSide.buildDistribution.WaitReason
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.StandardWaitReasons
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package jetbrains.buildServer.serverSide.impl.buildDistribution;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import jetbrains.buildServer.BuildAgent;
import jetbrains.buildServer.serverSide.Branch;
import jetbrains.buildServer.serverSide.BuildPromotion;
import jetbrains.buildServer.serverSide.BuildPromotionEx;
import jetbrains.buildServer.serverSide.BuildTypeEx;
import jetbrains.buildServer.serverSide.BuildTypeNotFoundException;
import jetbrains.buildServer.serverSide.SBuildType;
import jetbrains.buildServer.serverSide.buildDistribution.BuildDistributorInput;
import jetbrains.buildServer.serverSide.buildDistribution.BuildPromotionInfo;
import jetbrains.buildServer.serverSide.buildDistribution.QueuedBuildInfo;
import jetbrains.buildServer.serverSide.buildDistribution.SimpleWaitReason;
import jetbrains.buildServer.serverSide.buildDistribution.StartBuildPrecondition;
import jetbrains.buildServer.serverSide.buildDistribution.WaitReason;
import jetbrains.buildServer.serverSide.impl.buildDistribution.StandardWaitReasons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Exception performing whole class analysis ignored.
 */
public class MaxRunningBuildsPrecondition
implements StartBuildPrecondition {
    public static final Set<WaitReason> ALL_WAIT_REASONS = new HashSet<WaitReason>();

    public WaitReason canStart(@NotNull QueuedBuildInfo queuedBuildInfo, @NotNull Map<QueuedBuildInfo, BuildAgent> map, @NotNull BuildDistributorInput buildDistributorInput, boolean bl) {
        block33: {
            int n;
            try {
                if (queuedBuildInfo == null) {
                    MaxRunningBuildsPrecondition.$$$reportNull$$$0(0);
                }
            }
            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                throw MaxRunningBuildsPrecondition.a(buildTypeNotFoundException);
            }
            try {
                if (map == null) {
                    MaxRunningBuildsPrecondition.$$$reportNull$$$0(1);
                }
            }
            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                throw MaxRunningBuildsPrecondition.a(buildTypeNotFoundException);
            }
            try {
                if (buildDistributorInput == null) {
                    MaxRunningBuildsPrecondition.$$$reportNull$$$0(2);
                }
            }
            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                throw MaxRunningBuildsPrecondition.a(buildTypeNotFoundException);
            }
            BuildPromotionEx buildPromotionEx = (BuildPromotionEx)queuedBuildInfo.getBuildPromotionInfo();
            BuildTypeEx buildTypeEx = buildPromotionEx.getParentBuildType();
            try {
                if (buildTypeEx == null) {
                    return new SimpleWaitReason("Build configuration with id " + buildPromotionEx.getBuildTypeExternalId() + " does not exist");
                }
            }
            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                throw MaxRunningBuildsPrecondition.a(buildTypeNotFoundException);
            }
            Branch branch = buildPromotionEx.getBranch();
            int n2 = buildTypeEx.getMaximumNumberOfBuilds();
            try {
                n = buildPromotionEx.getMaximumNumberOfBuildsPerBranch();
            }
            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                return StandardWaitReasons.MAX_RUNNING_BUILDS_NUMBER_PER_BRANCH_REACHED;
            }
            try {
                if (n2 <= 0 && n <= 0) break block33;
            }
            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                throw MaxRunningBuildsPrecondition.a(buildTypeNotFoundException);
            }
            for (QueuedBuildInfo queuedBuildInfo2 : map.keySet()) {
                block34: {
                    try {
                        try {
                            if (!this.isOfSameType((BuildPromotion)buildPromotionEx, (BuildPromotion)queuedBuildInfo2.getBuildPromotionInfo())) continue;
                            if (--n2 != 0) break block34;
                        }
                        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                            throw MaxRunningBuildsPrecondition.a(buildTypeNotFoundException);
                        }
                        return StandardWaitReasons.MAX_RUNNING_BUILDS_NUMBER_REACHED;
                    }
                    catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                        throw MaxRunningBuildsPrecondition.a(buildTypeNotFoundException);
                    }
                }
                try {
                    try {
                        if (!MaxRunningBuildsPrecondition.isSameBranch(branch, MaxRunningBuildsPrecondition.getBranch(queuedBuildInfo2.getBuildPromotionInfo())) || --n != 0) continue;
                    }
                    catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                        throw MaxRunningBuildsPrecondition.a(buildTypeNotFoundException);
                    }
                    return StandardWaitReasons.MAX_RUNNING_BUILDS_NUMBER_PER_BRANCH_REACHED;
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw MaxRunningBuildsPrecondition.a(buildTypeNotFoundException);
                }
            }
            for (QueuedBuildInfo queuedBuildInfo2 : buildDistributorInput.getBuildTypeRunningBuilds((SBuildType)buildTypeEx)) {
                try {
                    if (--n2 == 0) {
                        return StandardWaitReasons.MAX_RUNNING_BUILDS_NUMBER_REACHED;
                    }
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw MaxRunningBuildsPrecondition.a(buildTypeNotFoundException);
                }
                try {
                    try {
                        if (!MaxRunningBuildsPrecondition.isSameBranch(branch, MaxRunningBuildsPrecondition.getBranch(queuedBuildInfo2.getBuildPromotionInfo())) || --n != 0) continue;
                    }
                    catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                        throw MaxRunningBuildsPrecondition.a(buildTypeNotFoundException);
                    }
                    return StandardWaitReasons.MAX_RUNNING_BUILDS_NUMBER_PER_BRANCH_REACHED;
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw MaxRunningBuildsPrecondition.a(buildTypeNotFoundException);
                }
            }
        }
        return null;
    }

    private static boolean isSameBranch(@Nullable Branch branch, @Nullable Branch branch2) {
        block9: {
            boolean bl;
            block12: {
                block11: {
                    try {
                        block10: {
                            try {
                                try {
                                    if (branch != null) break block9;
                                    if (branch2 == null) break block10;
                                }
                                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                                    throw MaxRunningBuildsPrecondition.a(buildTypeNotFoundException);
                                }
                                if (!branch2.isDefaultBranch()) break block11;
                            }
                            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                                throw MaxRunningBuildsPrecondition.a(buildTypeNotFoundException);
                            }
                        }
                        bl = true;
                        break block12;
                    }
                    catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                        throw MaxRunningBuildsPrecondition.a(buildTypeNotFoundException);
                    }
                }
                bl = false;
            }
            return bl;
        }
        try {
            if (branch2 == null) {
                return branch.isDefaultBranch();
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw MaxRunningBuildsPrecondition.a(buildTypeNotFoundException);
        }
        return branch.equals((Object)branch2);
    }

    private boolean isOfSameType(@NotNull BuildPromotion buildPromotion, @NotNull BuildPromotion buildPromotion2) {
        try {
            if (buildPromotion == null) {
                MaxRunningBuildsPrecondition.$$$reportNull$$$0(3);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw MaxRunningBuildsPrecondition.a(buildTypeNotFoundException);
        }
        try {
            if (buildPromotion2 == null) {
                MaxRunningBuildsPrecondition.$$$reportNull$$$0(4);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw MaxRunningBuildsPrecondition.a(buildTypeNotFoundException);
        }
        return buildPromotion.getParentBuildTypeId().equals(buildPromotion2.getParentBuildTypeId());
    }

    @Nullable
    private static Branch getBranch(@NotNull BuildPromotionInfo buildPromotionInfo) {
        try {
            if (buildPromotionInfo == null) {
                MaxRunningBuildsPrecondition.$$$reportNull$$$0(5);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw MaxRunningBuildsPrecondition.a(buildTypeNotFoundException);
        }
        return ((BuildPromotionEx)buildPromotionInfo).getBranch();
    }

    static {
        ALL_WAIT_REASONS.add(StandardWaitReasons.MAX_RUNNING_BUILDS_NUMBER_REACHED);
        ALL_WAIT_REASONS.add(StandardWaitReasons.MAX_RUNNING_BUILDS_NUMBER_PER_BRANCH_REACHED);
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
