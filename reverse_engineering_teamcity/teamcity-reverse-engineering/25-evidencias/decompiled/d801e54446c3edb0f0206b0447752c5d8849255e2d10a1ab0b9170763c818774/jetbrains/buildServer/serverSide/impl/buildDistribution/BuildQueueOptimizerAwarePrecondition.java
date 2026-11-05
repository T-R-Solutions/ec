/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jetbrains.buildServer.serverSide.Branch
 *  jetbrains.buildServer.serverSide.BuildPromotionEx
 *  jetbrains.buildServer.serverSide.BuildQueueEx
 *  jetbrains.buildServer.serverSide.SQueuedBuild
 *  jetbrains.buildServer.serverSide.buildDistribution.StartBuildPrecondition
 *  org.jetbrains.annotations.NotNull
 */
package jetbrains.buildServer.serverSide.impl.buildDistribution;

import java.util.List;
import java.util.function.Predicate;
import jetbrains.buildServer.serverSide.Branch;
import jetbrains.buildServer.serverSide.BuildPromotionEx;
import jetbrains.buildServer.serverSide.BuildQueueEx;
import jetbrains.buildServer.serverSide.SQueuedBuild;
import jetbrains.buildServer.serverSide.buildDistribution.StartBuildPrecondition;
import org.jetbrains.annotations.NotNull;

/*
 * Exception performing whole class analysis ignored.
 */
public abstract class BuildQueueOptimizerAwarePrecondition
implements StartBuildPrecondition {
    protected final BuildQueueEx myBuildQueue;

    protected BuildQueueOptimizerAwarePrecondition(@NotNull BuildQueueEx buildQueueEx) {
        if (buildQueueEx == null) {
            BuildQueueOptimizerAwarePrecondition.$$$reportNull$$$0(0);
        }
        this.myBuildQueue = buildQueueEx;
    }

    public boolean processPotentialOptimizationCandidates(@NotNull BuildPromotionEx buildPromotionEx2, @NotNull Predicate<BuildPromotionEx> predicate) {
        boolean bl;
        Branch branch;
        List list;
        block25: {
            block24: {
                try {
                    if (buildPromotionEx2 == null) {
                        BuildQueueOptimizerAwarePrecondition.$$$reportNull$$$0(1);
                    }
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw BuildQueueOptimizerAwarePrecondition.a(illegalArgumentException);
                }
                try {
                    if (predicate == null) {
                        BuildQueueOptimizerAwarePrecondition.$$$reportNull$$$0(2);
                    }
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw BuildQueueOptimizerAwarePrecondition.a(illegalArgumentException);
                }
                list = this.myBuildQueue.getItems(buildPromotionEx2.getParentBuildTypeId());
                try {
                    if (list.size() < 2) {
                        return false;
                    }
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw BuildQueueOptimizerAwarePrecondition.a(illegalArgumentException);
                }
                branch = buildPromotionEx2.getBranch();
                try {
                    try {
                        if (branch != null && !branch.isDefaultBranch()) break block24;
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        throw BuildQueueOptimizerAwarePrecondition.a(illegalArgumentException);
                    }
                    bl = true;
                    break block25;
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw BuildQueueOptimizerAwarePrecondition.a(illegalArgumentException);
                }
            }
            bl = false;
        }
        boolean bl2 = bl;
        Predicate<BuildPromotionEx> predicate2 = buildPromotionEx -> {
            boolean bl2;
            block10: {
                block9: {
                    Branch branch2;
                    block8: {
                        branch2 = buildPromotionEx.getBranch();
                        try {
                            try {
                                if (branch2 != null && !branch2.isDefaultBranch()) break block8;
                            }
                            catch (IllegalArgumentException illegalArgumentException) {
                                throw BuildQueueOptimizerAwarePrecondition.a(illegalArgumentException);
                            }
                            return true;
                        }
                        catch (IllegalArgumentException illegalArgumentException) {
                            throw BuildQueueOptimizerAwarePrecondition.a(illegalArgumentException);
                        }
                    }
                    try {
                        try {
                            if (bl2 || !branch2.getName().equals(branch.getName())) break block9;
                        }
                        catch (IllegalArgumentException illegalArgumentException) {
                            throw BuildQueueOptimizerAwarePrecondition.a(illegalArgumentException);
                        }
                        bl2 = true;
                        break block10;
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        throw BuildQueueOptimizerAwarePrecondition.a(illegalArgumentException);
                    }
                }
                bl2 = false;
            }
            return bl2;
        };
        for (SQueuedBuild sQueuedBuild : list) {
            try {
                if (sQueuedBuild.isPersonal()) {
                    continue;
                }
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw BuildQueueOptimizerAwarePrecondition.a(illegalArgumentException);
            }
            try {
                if (sQueuedBuild.getBuildPromotion().getId() == buildPromotionEx2.getId()) {
                    return false;
                }
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw BuildQueueOptimizerAwarePrecondition.a(illegalArgumentException);
            }
            BuildPromotionEx buildPromotionEx3 = (BuildPromotionEx)sQueuedBuild.getBuildPromotion();
            try {
                try {
                    if (!predicate.test(buildPromotionEx3) || !predicate2.test(buildPromotionEx3)) continue;
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw BuildQueueOptimizerAwarePrecondition.a(illegalArgumentException);
                }
                return true;
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw BuildQueueOptimizerAwarePrecondition.a(illegalArgumentException);
            }
        }
        return false;
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

    private static IllegalArgumentException a(IllegalArgumentException illegalArgumentException) {
        return illegalArgumentException;
    }
}
