/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jetbrains.buildServer.ExtensionsCollection
 *  jetbrains.buildServer.ExtensionsProvider
 *  jetbrains.buildServer.log.Loggers
 *  jetbrains.buildServer.serverSide.buildDistribution.BuildDistributionCallback
 *  jetbrains.buildServer.serverSide.buildDistribution.QueuedBuildInfo
 *  jetbrains.buildServer.serverSide.buildDistribution.WaitReason
 *  org.jetbrains.annotations.NotNull
 */
package jetbrains.buildServer.serverSide.impl.buildDistribution;

import java.util.function.Consumer;
import jetbrains.buildServer.ExtensionsCollection;
import jetbrains.buildServer.ExtensionsProvider;
import jetbrains.buildServer.log.Loggers;
import jetbrains.buildServer.serverSide.buildDistribution.BuildDistributionCallback;
import jetbrains.buildServer.serverSide.buildDistribution.QueuedBuildInfo;
import jetbrains.buildServer.serverSide.buildDistribution.WaitReason;
import org.jetbrains.annotations.NotNull;

/*
 * Exception performing whole class analysis ignored.
 */
public class BuildDistributionCallbackDispatcher
implements BuildDistributionCallback {
    private final ExtensionsCollection<BuildDistributionCallback> myExtensionsCollection;

    public BuildDistributionCallbackDispatcher(@NotNull ExtensionsProvider extensionsProvider) {
        if (extensionsProvider == null) {
            BuildDistributionCallbackDispatcher.$$$reportNull$$$0(0);
        }
        this.myExtensionsCollection = extensionsProvider.getExtensionsCollection(BuildDistributionCallback.class);
    }

    public void distributionStarted() {
        this.processAllListeners(BuildDistributionCallback::distributionStarted, "BuildDistributionListener.distributionStarted");
    }

    public void distributionFinished() {
        this.processAllListeners(BuildDistributionCallback::distributionFinished, "BuildDistributionListener.distributionFinished");
    }

    public void waitReasonSet(@NotNull QueuedBuildInfo queuedBuildInfo, @NotNull WaitReason waitReason) {
        try {
            if (queuedBuildInfo == null) {
                BuildDistributionCallbackDispatcher.$$$reportNull$$$0(1);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildDistributionCallbackDispatcher.a(illegalArgumentException);
        }
        try {
            if (waitReason == null) {
                BuildDistributionCallbackDispatcher.$$$reportNull$$$0(2);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildDistributionCallbackDispatcher.a(illegalArgumentException);
        }
        this.processAllListeners(buildDistributionCallback -> buildDistributionCallback.waitReasonSet(queuedBuildInfo, waitReason), "BuildDistributionListener.waitReasonSet");
    }

    private void processAllListeners(@NotNull Consumer<BuildDistributionCallback> consumer, @NotNull String string) {
        try {
            if (consumer == null) {
                BuildDistributionCallbackDispatcher.$$$reportNull$$$0(3);
            }
        }
        catch (Throwable throwable) {
            throw BuildDistributionCallbackDispatcher.a(throwable);
        }
        try {
            if (string == null) {
                BuildDistributionCallbackDispatcher.$$$reportNull$$$0(4);
            }
        }
        catch (Throwable throwable) {
            throw BuildDistributionCallbackDispatcher.a(throwable);
        }
        for (BuildDistributionCallback buildDistributionCallback : this.myExtensionsCollection.getExtensions()) {
            try {
                if (buildDistributionCallback == this) {
                    continue;
                }
            }
            catch (Throwable throwable) {
                throw BuildDistributionCallbackDispatcher.a(throwable);
            }
            try {
                consumer.accept(buildDistributionCallback);
            }
            catch (Throwable throwable) {
                Loggers.SERVER.warnAndDebugDetails("Exception when calling " + string + " in " + buildDistributionCallback.getClass().getName(), throwable);
            }
        }
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

    private static Throwable a(Throwable throwable) {
        return throwable;
    }
}
