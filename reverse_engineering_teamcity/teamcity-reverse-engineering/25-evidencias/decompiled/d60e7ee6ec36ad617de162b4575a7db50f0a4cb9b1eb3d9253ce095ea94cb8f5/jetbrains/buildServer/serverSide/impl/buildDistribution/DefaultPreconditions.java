/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jetbrains.buildServer.ExtensionHolder
 *  jetbrains.buildServer.TeamCityExtension
 *  jetbrains.buildServer.serverSide.BuildQueueEx
 *  jetbrains.buildServer.serverSide.buildDistribution.StartBuildPrecondition
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.BuildDependenciesPrecondition
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.CheckingForChangesPrecondition
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.MaxRunningBuildsPrecondition
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.StartingBuildsLimitPrecondition
 *  org.jetbrains.annotations.NotNull
 */
package jetbrains.buildServer.serverSide.impl.buildDistribution;

import jetbrains.buildServer.ExtensionHolder;
import jetbrains.buildServer.TeamCityExtension;
import jetbrains.buildServer.serverSide.BuildQueueEx;
import jetbrains.buildServer.serverSide.buildDistribution.StartBuildPrecondition;
import jetbrains.buildServer.serverSide.impl.buildDistribution.BuildDependenciesPrecondition;
import jetbrains.buildServer.serverSide.impl.buildDistribution.CheckingForChangesPrecondition;
import jetbrains.buildServer.serverSide.impl.buildDistribution.MaxRunningBuildsPrecondition;
import jetbrains.buildServer.serverSide.impl.buildDistribution.StartingBuildsLimitPrecondition;
import org.jetbrains.annotations.NotNull;

/*
 * Exception performing whole class analysis ignored.
 */
public class DefaultPreconditions {
    public DefaultPreconditions(@NotNull ExtensionHolder extensionHolder, @NotNull BuildQueueEx buildQueueEx) {
        if (extensionHolder == null) {
            DefaultPreconditions.$$$reportNull$$$0(0);
        }
        if (buildQueueEx == null) {
            DefaultPreconditions.$$$reportNull$$$0(1);
        }
        extensionHolder.registerExtension(StartBuildPrecondition.class, MaxRunningBuildsPrecondition.class.getName(), (TeamCityExtension)new MaxRunningBuildsPrecondition());
        extensionHolder.registerExtension(StartBuildPrecondition.class, BuildDependenciesPrecondition.class.getName(), (TeamCityExtension)new BuildDependenciesPrecondition());
        extensionHolder.registerExtension(StartBuildPrecondition.class, CheckingForChangesPrecondition.class.getName(), (TeamCityExtension)new CheckingForChangesPrecondition(buildQueueEx));
        extensionHolder.registerExtension(StartBuildPrecondition.class, StartingBuildsLimitPrecondition.class.getName(), (TeamCityExtension)new StartingBuildsLimitPrecondition());
    }

    /*
     * Exception decompiling
     */
    private static /* synthetic */ void $$$reportNull$$$0(int var0) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 1[SWITCH]
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
