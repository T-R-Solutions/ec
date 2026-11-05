/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jetbrains.buildServer.metrics.Counter
 *  jetbrains.buildServer.metrics.MetricDataType
 *  jetbrains.buildServer.metrics.NoOpCounter
 *  jetbrains.buildServer.metrics.ServerMetrics
 *  jetbrains.buildServer.util.ThreadPoolMetricsInitializer
 *  org.jetbrains.annotations.NotNull
 */
package jetbrains.buildServer.serverSide.impl.buildDistribution;

import java.util.concurrent.ExecutorService;
import jetbrains.buildServer.metrics.Counter;
import jetbrains.buildServer.metrics.MetricDataType;
import jetbrains.buildServer.metrics.NoOpCounter;
import jetbrains.buildServer.metrics.ServerMetrics;
import jetbrains.buildServer.util.ThreadPoolMetricsInitializer;
import org.jetbrains.annotations.NotNull;

/*
 * Exception performing whole class analysis ignored.
 */
public class BuildQueueProcessingMetrics {
    private final Counter myTotalQueueProcessingMetric;
    private final Counter myBuildsOptimizationTimeMetric;
    private final Counter myStartNewBuildsMetric;
    private final Counter myOtherActivitiesMetric;
    private final Counter myPreconditionsTimeMetric;
    private final Counter myCompatibleAgentsMetric;
    private final Counter myCompatibleVirtualAgentsMetric;
    private final Counter myStartNewBuildsOutsideQueueLoopMetric;
    private final ServerMetrics myServerMetrics;

    public BuildQueueProcessingMetrics(@NotNull ServerMetrics serverMetrics) {
        if (serverMetrics == null) {
            BuildQueueProcessingMetrics.$$$reportNull$$$0(0);
        }
        this.myServerMetrics = serverMetrics;
        this.myTotalQueueProcessingMetric = serverMetrics.metricBuilder("process.queue").description("Time to process build queue and start builds").experimental(true).dataType(MetricDataType.MILLISECONDS).buildCounter();
        this.myBuildsOptimizationTimeMetric = BuildQueueProcessingMetrics.createPartMetric("optimization", "Time spent on builds optimization while processing the build queue", serverMetrics);
        this.myStartNewBuildsMetric = BuildQueueProcessingMetrics.createPartMetric("startBuilds", "Time spent on creating and starting new builds (inside build queue processing)", serverMetrics);
        this.myStartNewBuildsOutsideQueueLoopMetric = BuildQueueProcessingMetrics.createPartMetric("startBuildsOutsideQueueLoop", "Time spent on creating and starting new builds (outside of build queue processing)", serverMetrics);
        this.myOtherActivitiesMetric = BuildQueueProcessingMetrics.createPartMetric("other", "Time spent on some other activities while processing the build queue", serverMetrics);
        this.myPreconditionsTimeMetric = BuildQueueProcessingMetrics.createPartMetric("preconditions", "Time spent on preconditions while processing the build queue", serverMetrics);
        this.myCompatibleAgentsMetric = BuildQueueProcessingMetrics.createPartMetric("compatibleAgents", "Time spent calculating compatible agents (including filtering) while processing the build queue", serverMetrics);
        this.myCompatibleVirtualAgentsMetric = BuildQueueProcessingMetrics.createPartMetric("compatibleVirtualAgents", "Time spent calculating compatible virtual agents (including filtering) while processing the build queue", serverMetrics);
    }

    @NotNull
    private static Counter createPartMetric(String string, String string2, @NotNull ServerMetrics serverMetrics) {
        Counter counter;
        try {
            if (serverMetrics == null) {
                BuildQueueProcessingMetrics.$$$reportNull$$$0(1);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildQueueProcessingMetrics.a(illegalArgumentException);
        }
        try {
            counter = serverMetrics.metricBuilder("process.queue.parts").description(string2).experimental(true).tags(new String[]{"part", string}).dataType(MetricDataType.MILLISECONDS).buildCounter();
            if (counter == null) {
                BuildQueueProcessingMetrics.$$$reportNull$$$0(2);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildQueueProcessingMetrics.a(illegalArgumentException);
        }
        return counter;
    }

    @NotNull
    public Counter getTotalQueueProcessingMetric() {
        Counter counter;
        try {
            counter = this.myTotalQueueProcessingMetric;
            if (counter == null) {
                BuildQueueProcessingMetrics.$$$reportNull$$$0(3);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildQueueProcessingMetrics.a(illegalArgumentException);
        }
        return counter;
    }

    @NotNull
    public Counter getBuildsOptimizationTimeMetric() {
        Counter counter;
        try {
            counter = this.myBuildsOptimizationTimeMetric;
            if (counter == null) {
                BuildQueueProcessingMetrics.$$$reportNull$$$0(4);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildQueueProcessingMetrics.a(illegalArgumentException);
        }
        return counter;
    }

    @NotNull
    public Counter getStartNewBuildsInsideQueueLoopMetric() {
        Counter counter;
        try {
            counter = this.myStartNewBuildsMetric;
            if (counter == null) {
                BuildQueueProcessingMetrics.$$$reportNull$$$0(5);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildQueueProcessingMetrics.a(illegalArgumentException);
        }
        return counter;
    }

    @NotNull
    public Counter getStartNewBuildsOutsideQueueLoopMetric() {
        Counter counter;
        try {
            counter = this.myStartNewBuildsOutsideQueueLoopMetric;
            if (counter == null) {
                BuildQueueProcessingMetrics.$$$reportNull$$$0(6);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildQueueProcessingMetrics.a(illegalArgumentException);
        }
        return counter;
    }

    @NotNull
    public Counter getOtherActivitiesMetric(boolean bl) {
        NoOpCounter noOpCounter;
        Object object;
        try {
            object = bl ? new NoOpCounter() : this.myOtherActivitiesMetric;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildQueueProcessingMetrics.a(illegalArgumentException);
        }
        try {
            noOpCounter = object;
            if (object == null) {
                BuildQueueProcessingMetrics.$$$reportNull$$$0(7);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildQueueProcessingMetrics.a(illegalArgumentException);
        }
        return noOpCounter;
    }

    @NotNull
    public Counter getPreconditionsTimeMetric(boolean bl) {
        NoOpCounter noOpCounter;
        Object object;
        try {
            object = bl ? new NoOpCounter() : this.myPreconditionsTimeMetric;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildQueueProcessingMetrics.a(illegalArgumentException);
        }
        try {
            noOpCounter = object;
            if (object == null) {
                BuildQueueProcessingMetrics.$$$reportNull$$$0(8);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildQueueProcessingMetrics.a(illegalArgumentException);
        }
        return noOpCounter;
    }

    @NotNull
    public Counter getCompatibleAgentsMetric(boolean bl) {
        NoOpCounter noOpCounter;
        Object object;
        try {
            object = bl ? new NoOpCounter() : this.myCompatibleAgentsMetric;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildQueueProcessingMetrics.a(illegalArgumentException);
        }
        try {
            noOpCounter = object;
            if (object == null) {
                BuildQueueProcessingMetrics.$$$reportNull$$$0(9);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildQueueProcessingMetrics.a(illegalArgumentException);
        }
        return noOpCounter;
    }

    @NotNull
    public Counter getCompatibleVirtualAgentsMetric(boolean bl) {
        NoOpCounter noOpCounter;
        Object object;
        try {
            object = bl ? new NoOpCounter() : this.myCompatibleVirtualAgentsMetric;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildQueueProcessingMetrics.a(illegalArgumentException);
        }
        try {
            noOpCounter = object;
            if (object == null) {
                BuildQueueProcessingMetrics.$$$reportNull$$$0(10);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildQueueProcessingMetrics.a(illegalArgumentException);
        }
        return noOpCounter;
    }

    public void initParallelPoolMetrics(@NotNull ExecutorService executorService) {
        try {
            if (executorService == null) {
                BuildQueueProcessingMetrics.$$$reportNull$$$0(11);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildQueueProcessingMetrics.a(illegalArgumentException);
        }
        new ThreadPoolMetricsInitializer(this.myServerMetrics).initExecutorServiceMetrics("flushQueueTaskExecutor", executorService);
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
