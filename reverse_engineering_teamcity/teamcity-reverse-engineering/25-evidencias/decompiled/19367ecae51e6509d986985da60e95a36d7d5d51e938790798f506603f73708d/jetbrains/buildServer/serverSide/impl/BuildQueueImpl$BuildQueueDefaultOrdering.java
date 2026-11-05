/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jetbrains.buildServer.serverSide.BuildQueueOrderingStrategy
 *  jetbrains.buildServer.serverSide.SQueuedBuild
 *  org.jetbrains.annotations.NotNull
 */
package jetbrains.buildServer.serverSide.impl;

import java.util.Collections;
import java.util.List;
import jetbrains.buildServer.serverSide.BuildQueueOrderingStrategy;
import jetbrains.buildServer.serverSide.SQueuedBuild;
import org.jetbrains.annotations.NotNull;

/*
 * Exception performing whole class analysis ignored.
 */
private static class BuildQueueImpl.BuildQueueDefaultOrdering
implements BuildQueueOrderingStrategy {
    private BuildQueueImpl.BuildQueueDefaultOrdering() {
    }

    @NotNull
    public List<SQueuedBuild> addBuilds(@NotNull List<SQueuedBuild> list, @NotNull List<SQueuedBuild> list2) {
        List<SQueuedBuild> list3;
        try {
            if (list == null) {
                BuildQueueImpl.BuildQueueDefaultOrdering.$$$reportNull$$$0(0);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildQueueImpl.BuildQueueDefaultOrdering.a(illegalArgumentException);
        }
        try {
            if (list2 == null) {
                BuildQueueImpl.BuildQueueDefaultOrdering.$$$reportNull$$$0(1);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildQueueImpl.BuildQueueDefaultOrdering.a(illegalArgumentException);
        }
        try {
            list3 = Collections.emptyList();
            if (list3 == null) {
                BuildQueueImpl.BuildQueueDefaultOrdering.$$$reportNull$$$0(2);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildQueueImpl.BuildQueueDefaultOrdering.a(illegalArgumentException);
        }
        return list3;
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
