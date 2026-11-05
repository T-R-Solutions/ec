/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.intellij.openapi.util.Pair
 *  jetbrains.buildServer.log.Loggers
 *  jetbrains.buildServer.serverSide.CompatibilityResult
 *  jetbrains.buildServer.serverSide.SBuildAgent
 *  jetbrains.buildServer.serverSide.buildDistribution.BuildDistributorInput
 *  jetbrains.buildServer.serverSide.buildDistribution.QueuedBuildInfo
 *  jetbrains.buildServer.serverSide.buildDistribution.WaitReason
 *  jetbrains.buildServer.serverSide.buildDistribution.WaitReasonEx
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.CompatibleAgentsResult
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.Distributor
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.agentsComparators.BuildAgentsComparatorProvider
 *  jetbrains.buildServer.serverSide.impl.executors.ExecutorCompatibilityManager
 *  jetbrains.buildServer.serverSide.impl.executors.ExecutorDescription
 *  org.jetbrains.annotations.NotNull
 */
package jetbrains.buildServer.serverSide.impl.buildDistribution;

import com.intellij.openapi.util.Pair;
import java.util.List;
import java.util.Map;
import java.util.Set;
import jetbrains.buildServer.log.Loggers;
import jetbrains.buildServer.serverSide.CompatibilityResult;
import jetbrains.buildServer.serverSide.SBuildAgent;
import jetbrains.buildServer.serverSide.buildDistribution.BuildDistributorInput;
import jetbrains.buildServer.serverSide.buildDistribution.QueuedBuildInfo;
import jetbrains.buildServer.serverSide.buildDistribution.WaitReason;
import jetbrains.buildServer.serverSide.buildDistribution.WaitReasonEx;
import jetbrains.buildServer.serverSide.impl.buildDistribution.CompatibleAgentsResult;
import jetbrains.buildServer.serverSide.impl.buildDistribution.Distributor;
import jetbrains.buildServer.serverSide.impl.buildDistribution.agentsComparators.BuildAgentsComparatorProvider;
import jetbrains.buildServer.serverSide.impl.executors.ExecutorCompatibilityManager;
import jetbrains.buildServer.serverSide.impl.executors.ExecutorDescription;
import org.jetbrains.annotations.NotNull;

/*
 * Exception performing whole class analysis ignored.
 */
public class ExecutorDistributor
implements Distributor<ExecutorDescription> {
    private static final CompatibleAgentsResult<ExecutorDescription> NO_AGENTS = CompatibleAgentsResult.noAgents();
    @NotNull
    private final BuildAgentsComparatorProvider myAgentsComparatorProvider;
    @NotNull
    private final ExecutorCompatibilityManager myExecutorCompatibilityManager;

    public ExecutorDistributor(@NotNull BuildAgentsComparatorProvider buildAgentsComparatorProvider, @NotNull ExecutorCompatibilityManager executorCompatibilityManager) {
        if (buildAgentsComparatorProvider == null) {
            ExecutorDistributor.$$$reportNull$$$0(0);
        }
        if (executorCompatibilityManager == null) {
            ExecutorDistributor.$$$reportNull$$$0(1);
        }
        this.myExecutorCompatibilityManager = executorCompatibilityManager;
        this.myAgentsComparatorProvider = buildAgentsComparatorProvider;
    }

    /*
     * Exception decompiling
     */
    @NotNull
    public CompatibleAgentsResult<ExecutorDescription> getCompatibleCandidates(@NotNull QueuedBuildInfo var1_1, @NotNull Set<ExecutorDescription> var2_2) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [4[TRYBLOCK]], but top level block is 13[SWITCH]
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:435)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:484)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doClass(Driver.java:84)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:78)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    @NotNull
    public Pair<ExecutorDescription, WaitReason> chooseAgentForBuild(@NotNull QueuedBuildInfo queuedBuildInfo, @NotNull CompatibleAgentsResult<ExecutorDescription> compatibleAgentsResult) {
        Pair pair;
        try {
            if (queuedBuildInfo == null) {
                ExecutorDistributor.$$$reportNull$$$0(8);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw ExecutorDistributor.a(illegalArgumentException);
        }
        try {
            if (compatibleAgentsResult == null) {
                ExecutorDistributor.$$$reportNull$$$0(9);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw ExecutorDistributor.a(illegalArgumentException);
        }
        try {
            pair = Pair.create((Object)compatibleAgentsResult.getAgents().stream().findFirst().orElse(null), null);
            if (pair == null) {
                ExecutorDistributor.$$$reportNull$$$0(10);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw ExecutorDistributor.a(illegalArgumentException);
        }
        return pair;
    }

    public WaitReason filterAvailableAgents(@NotNull BuildDistributorInput buildDistributorInput, @NotNull QueuedBuildInfo queuedBuildInfo, @NotNull List<ExecutorDescription> list, @NotNull Map<QueuedBuildInfo, SBuildAgent> map, @NotNull Map<String, Object> map2) {
        try {
            if (buildDistributorInput == null) {
                ExecutorDistributor.$$$reportNull$$$0(11);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw ExecutorDistributor.a(illegalArgumentException);
        }
        try {
            if (queuedBuildInfo == null) {
                ExecutorDistributor.$$$reportNull$$$0(12);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw ExecutorDistributor.a(illegalArgumentException);
        }
        try {
            if (list == null) {
                ExecutorDistributor.$$$reportNull$$$0(13);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw ExecutorDistributor.a(illegalArgumentException);
        }
        try {
            if (map == null) {
                ExecutorDistributor.$$$reportNull$$$0(14);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw ExecutorDistributor.a(illegalArgumentException);
        }
        try {
            if (map2 == null) {
                ExecutorDistributor.$$$reportNull$$$0(15);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw ExecutorDistributor.a(illegalArgumentException);
        }
        return null;
    }

    public void sortAvailableAgents(@NotNull List<ExecutorDescription> list, @NotNull QueuedBuildInfo queuedBuildInfo) {
        try {
            if (list == null) {
                ExecutorDistributor.$$$reportNull$$$0(16);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw ExecutorDistributor.a(illegalArgumentException);
        }
        try {
            if (queuedBuildInfo == null) {
                ExecutorDistributor.$$$reportNull$$$0(17);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw ExecutorDistributor.a(illegalArgumentException);
        }
        list.sort(this.myAgentsComparatorProvider.getStartableExecutorsComparatorForQueuedBuild(queuedBuildInfo));
    }

    public boolean canProcessQueuedBuild(@NotNull QueuedBuildInfo queuedBuildInfo, @NotNull Map<QueuedBuildInfo, WaitReason> map) {
        block15: {
            WaitReason waitReason;
            block16: {
                try {
                    if (queuedBuildInfo == null) {
                        ExecutorDistributor.$$$reportNull$$$0(18);
                    }
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw ExecutorDistributor.a(illegalArgumentException);
                }
                try {
                    if (map == null) {
                        ExecutorDistributor.$$$reportNull$$$0(19);
                    }
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw ExecutorDistributor.a(illegalArgumentException);
                }
                waitReason = map.get(queuedBuildInfo);
                try {
                    try {
                        try {
                            if (waitReason == null) break block15;
                            if (!(waitReason instanceof WaitReasonEx)) break block16;
                        }
                        catch (IllegalArgumentException illegalArgumentException) {
                            throw ExecutorDistributor.a(illegalArgumentException);
                        }
                        if (!((WaitReasonEx)waitReason).notEnoughAgents()) break block16;
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        throw ExecutorDistributor.a(illegalArgumentException);
                    }
                    return true;
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw ExecutorDistributor.a(illegalArgumentException);
                }
            }
            try {
                if (Loggers.SERVER.isDebugEnabled()) {
                    Loggers.SERVER.debug(String.format("Won't distribute build '%s', because previous step returned: '%s'", queuedBuildInfo.describe(false), waitReason.getDescription()));
                }
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw ExecutorDistributor.a(illegalArgumentException);
            }
            return false;
        }
        return true;
    }

    @NotNull
    public String getName() {
        String string;
        try {
            string = this.getClass().getName();
            if (string == null) {
                ExecutorDistributor.$$$reportNull$$$0(20);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw ExecutorDistributor.a(illegalArgumentException);
        }
        return string;
    }

    private static /* synthetic */ ExecutorDescription lambda$getCompatibleCandidates$1(Map.Entry entry) {
        return (ExecutorDescription)entry.getKey();
    }

    private static /* synthetic */ boolean lambda$getCompatibleCandidates$0(Map.Entry entry) {
        return ((CompatibilityResult)entry.getValue()).isCompatible();
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
