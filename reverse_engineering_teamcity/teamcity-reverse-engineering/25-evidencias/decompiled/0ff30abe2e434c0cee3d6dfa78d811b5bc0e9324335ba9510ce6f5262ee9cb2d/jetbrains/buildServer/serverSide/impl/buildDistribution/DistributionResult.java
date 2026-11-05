/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jetbrains.buildServer.serverSide.buildDistribution.QueuedBuildInfo
 *  jetbrains.buildServer.serverSide.buildDistribution.WaitReason
 *  org.jetbrains.annotations.NotNull
 */
package jetbrains.buildServer.serverSide.impl.buildDistribution;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import jetbrains.buildServer.serverSide.buildDistribution.QueuedBuildInfo;
import jetbrains.buildServer.serverSide.buildDistribution.WaitReason;
import org.jetbrains.annotations.NotNull;

/*
 * Exception performing whole class analysis ignored.
 */
public class DistributionResult<T> {
    private final Map<QueuedBuildInfo, T> myDistributedBuilds;
    private final Map<QueuedBuildInfo, WaitReason> myWaitReasons;

    public DistributionResult() {
        this.myDistributedBuilds = new HashMap<QueuedBuildInfo, T>();
        this.myWaitReasons = new HashMap<QueuedBuildInfo, WaitReason>();
    }

    public DistributionResult(@NotNull Map<QueuedBuildInfo, T> map, @NotNull Map<QueuedBuildInfo, WaitReason> map2) {
        if (map == null) {
            DistributionResult.$$$reportNull$$$0(0);
        }
        if (map2 == null) {
            DistributionResult.$$$reportNull$$$0(1);
        }
        this.myDistributedBuilds = map;
        this.myWaitReasons = map2;
    }

    @NotNull
    public Map<QueuedBuildInfo, T> getDistributedBuilds() {
        Map<QueuedBuildInfo, T> map;
        try {
            map = Collections.unmodifiableMap(this.myDistributedBuilds);
            if (map == null) {
                DistributionResult.$$$reportNull$$$0(2);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw DistributionResult.a(illegalArgumentException);
        }
        return map;
    }

    @NotNull
    public Map<QueuedBuildInfo, WaitReason> getWaitReasons() {
        Map<QueuedBuildInfo, WaitReason> map;
        try {
            map = Collections.unmodifiableMap(this.myWaitReasons);
            if (map == null) {
                DistributionResult.$$$reportNull$$$0(3);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw DistributionResult.a(illegalArgumentException);
        }
        return map;
    }

    public void mergeWith(@NotNull DistributionResult<T> distributionResult) {
        try {
            if (distributionResult == null) {
                DistributionResult.$$$reportNull$$$0(4);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw DistributionResult.a(illegalArgumentException);
        }
        this.myWaitReasons.keySet().removeAll(distributionResult.myDistributedBuilds.keySet());
        this.myWaitReasons.putAll(distributionResult.myWaitReasons);
        this.myDistributedBuilds.putAll(distributionResult.myDistributedBuilds);
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
