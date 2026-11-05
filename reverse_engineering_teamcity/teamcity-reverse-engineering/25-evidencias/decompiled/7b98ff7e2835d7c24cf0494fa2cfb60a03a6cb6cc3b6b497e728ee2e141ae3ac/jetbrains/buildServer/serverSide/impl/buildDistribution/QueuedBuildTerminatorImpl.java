/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.intellij.openapi.diagnostic.Logger
 *  jetbrains.buildServer.serverSide.BuildPromotion
 *  jetbrains.buildServer.serverSide.BuildPromotionEx
 *  jetbrains.buildServer.serverSide.BuildQueueEx
 *  jetbrains.buildServer.serverSide.QueuedBuildEx
 *  jetbrains.buildServer.serverSide.SBuild
 *  jetbrains.buildServer.serverSide.TeamCityProperties
 *  jetbrains.buildServer.serverSide.buildDistribution.QueuedBuildInfo
 *  jetbrains.buildServer.serverSide.dependency.BuildDependency
 *  jetbrains.buildServer.serverSide.dependency.DependencyOptions$BuildContinuationMode
 *  jetbrains.buildServer.serverSide.impl.DependentBuildStartPredicate
 *  jetbrains.buildServer.serverSide.impl.DependentBuildStartPredicate$BuildContinuationInterruptedInfo
 *  jetbrains.buildServer.serverSide.impl.LogUtil
 *  jetbrains.buildServer.serverSide.impl.QueuedTasksProcessor
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.BuildDistributor$QueuedBuildTerminator
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.QueuedBuildTerminatorImpl$TerminationTaskMetadata
 *  org.jetbrains.annotations.NotNull
 */
package jetbrains.buildServer.serverSide.impl.buildDistribution;

import com.intellij.openapi.diagnostic.Logger;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import jetbrains.buildServer.serverSide.BuildPromotion;
import jetbrains.buildServer.serverSide.BuildPromotionEx;
import jetbrains.buildServer.serverSide.BuildQueueEx;
import jetbrains.buildServer.serverSide.QueuedBuildEx;
import jetbrains.buildServer.serverSide.SBuild;
import jetbrains.buildServer.serverSide.TeamCityProperties;
import jetbrains.buildServer.serverSide.buildDistribution.QueuedBuildInfo;
import jetbrains.buildServer.serverSide.dependency.BuildDependency;
import jetbrains.buildServer.serverSide.dependency.DependencyOptions;
import jetbrains.buildServer.serverSide.impl.DependentBuildStartPredicate;
import jetbrains.buildServer.serverSide.impl.LogUtil;
import jetbrains.buildServer.serverSide.impl.QueuedTasksProcessor;
import jetbrains.buildServer.serverSide.impl.buildDistribution.BuildDistributor;
import jetbrains.buildServer.serverSide.impl.buildDistribution.QueuedBuildTerminatorImpl;
import org.jetbrains.annotations.NotNull;

/*
 * Exception performing whole class analysis ignored.
 */
public class QueuedBuildTerminatorImpl
implements BuildDistributor.QueuedBuildTerminator {
    private static final Logger LOG = Logger.getInstance(QueuedBuildTerminatorImpl.class);
    public static final String PREEMPTIVE_CANCELATION_ENABLED_PROPERTY = "teamcity.queueDistribution.preemptiveBuildsCancellation.enabled";
    public static final String PREEMPTIVE_START_FAILURE_ENABLED_PROPERTY = "teamcity.queueDistribution.preemptiveBuildsStartFailure.enabled";
    public static final String PREEMPTIVE_TERMINATION_IGNORES_QUEUED_DEPENDENCIES_PROPERTY = "teamcity.queueDistribution.preemptiveTerminationIgnoresQueuedDependencies.enabled";
    public static final String TERMINATION_TASKS_QUEUE_SIZE_PROPERTY = "teamcity.queuedBuildsTerminationTasksQueue.queueSize";
    public static final String TERMINATION_TASKS_QUEUE_MAX_TASKS_TO_HANDLE_PROPERTY = "teamcity.queuedBuildsTerminationTasksQueue.maxTasksToHandle";
    private static final String DEFAULT_TERMINATION_COMMENT = "Build was terminated because one of the builds it depends on failed or was canceled.";
    @NotNull
    private final BuildQueueEx myBuildQueue;
    @NotNull
    private final QueuedTasksProcessor<String> myScheduledTerminationsProcessor;
    @NotNull
    private final ConcurrentHashMap<String, TerminationTaskMetadata> myScheduledTerminationTasks;

    public QueuedBuildTerminatorImpl(@NotNull ExecutorService executorService, @NotNull BuildQueueEx buildQueueEx) {
        if (executorService == null) {
            QueuedBuildTerminatorImpl.$$$reportNull$$$0(0);
        }
        if (buildQueueEx == null) {
            QueuedBuildTerminatorImpl.$$$reportNull$$$0(1);
        }
        this.myScheduledTerminationTasks = new ConcurrentHashMap();
        int n = TeamCityProperties.getInteger((String)"teamcity.queuedBuildsTerminationTasksQueue.queueSize", (int)1000);
        int n2 = TeamCityProperties.getInteger((String)"teamcity.queuedBuildsTerminationTasksQueue.maxTasksToHandle", (int)n);
        this.myScheduledTerminationsProcessor = new QueuedTasksProcessor(list -> this.terminateQueuedBuilds(list), executorService, n, n2, "Queued builds termination");
        this.myBuildQueue = buildQueueEx;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void terminateQueuedBuilds(List<String> list) {
        for (String string : list) {
            try {
                QueuedBuildEx queuedBuildEx = this.myBuildQueue.findQueued(string);
                if (queuedBuildEx == null) {
                    LOG.warn("Termination was scheduled for queued build with id " + string + " but no such queued build found.");
                    continue;
                }
                TerminationTaskMetadata terminationTaskMetadata = this.myScheduledTerminationTasks.get(string);
                if (terminationTaskMetadata == null) {
                    LOG.warn("Termination was scheduled for queued build associated with promotion " + LogUtil.describe((BuildPromotion)queuedBuildEx.getBuildPromotion()) + " but metadata required for the operation is abscent. Will skip the termination.");
                    continue;
                }
                queuedBuildEx.terminate(terminationTaskMetadata.myIsCanceled, terminationTaskMetadata.myComment, terminationTaskMetadata.myBuildProblemData);
            }
            catch (Exception exception) {
                LOG.warnAndDebugDetails("Error occured during queued build with id " + string + " termination.", (Throwable)exception);
            }
            finally {
                this.myScheduledTerminationTasks.remove(string);
            }
        }
    }

    public boolean scheduledForTermination(@NotNull QueuedBuildInfo queuedBuildInfo) {
        try {
            if (queuedBuildInfo == null) {
                QueuedBuildTerminatorImpl.$$$reportNull$$$0(2);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw QueuedBuildTerminatorImpl.a(illegalArgumentException);
        }
        String string = queuedBuildInfo.getItemId();
        try {
            if (this.myScheduledTerminationTasks.containsKey(string)) {
                return true;
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw QueuedBuildTerminatorImpl.a(illegalArgumentException);
        }
        try {
            if (this.scheduleForTerminationIfNeeded(queuedBuildInfo, true)) {
                ((BuildPromotionEx)queuedBuildInfo.getBuildPromotionInfo()).resetDirectDependenciesCache();
                return this.scheduleForTerminationIfNeeded(queuedBuildInfo, false);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw QueuedBuildTerminatorImpl.a(illegalArgumentException);
        }
        return false;
    }

    private boolean scheduleForTerminationIfNeeded(@NotNull QueuedBuildInfo queuedBuildInfo, boolean bl) {
        boolean bl2;
        boolean bl3;
        boolean bl4;
        String string;
        block42: {
            try {
                if (queuedBuildInfo == null) {
                    QueuedBuildTerminatorImpl.$$$reportNull$$$0(3);
                }
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw QueuedBuildTerminatorImpl.a(illegalArgumentException);
            }
            string = queuedBuildInfo.getItemId();
            bl4 = TeamCityProperties.getBooleanOrTrue((String)"teamcity.queueDistribution.preemptiveBuildsCancellation.enabled");
            bl3 = TeamCityProperties.getBooleanOrTrue((String)"teamcity.queueDistribution.preemptiveBuildsStartFailure.enabled");
            bl2 = TeamCityProperties.getBooleanOrTrue((String)"teamcity.queueDistribution.preemptiveTerminationIgnoresQueuedDependencies.enabled");
            try {
                try {
                    if (bl4 || bl3) break block42;
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw QueuedBuildTerminatorImpl.a(illegalArgumentException);
                }
                return false;
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw QueuedBuildTerminatorImpl.a(illegalArgumentException);
            }
        }
        BuildPromotionEx buildPromotionEx = (BuildPromotionEx)queuedBuildInfo.getBuildPromotionInfo();
        try {
            if (buildPromotionEx.isCompositeBuild()) {
                return false;
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw QueuedBuildTerminatorImpl.a(illegalArgumentException);
        }
        try {
            if (buildPromotionEx.getAssociatedBuildId() != null) {
                return false;
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw QueuedBuildTerminatorImpl.a(illegalArgumentException);
        }
        for (BuildDependency buildDependency : buildPromotionEx.getDependencies()) {
            boolean bl5;
            block50: {
                block49: {
                    DependentBuildStartPredicate.BuildContinuationInterruptedInfo buildContinuationInterruptedInfo;
                    BuildPromotionEx buildPromotionEx2;
                    block44: {
                        boolean bl6;
                        block47: {
                            block46: {
                                buildPromotionEx2 = (BuildPromotionEx)buildDependency.getDependOn();
                                try {
                                    try {
                                        if (bl2 && buildPromotionEx2.getAssociatedBuildId() == null) {
                                            continue;
                                        }
                                    }
                                    catch (IllegalArgumentException illegalArgumentException) {
                                        throw QueuedBuildTerminatorImpl.a(illegalArgumentException);
                                    }
                                }
                                catch (IllegalArgumentException illegalArgumentException) {
                                    throw QueuedBuildTerminatorImpl.a(illegalArgumentException);
                                }
                                buildContinuationInterruptedInfo = DependentBuildStartPredicate.shouldInterruptExecution((BuildDependency)buildDependency);
                                try {
                                    if (buildContinuationInterruptedInfo == null) {
                                        continue;
                                    }
                                }
                                catch (IllegalArgumentException illegalArgumentException) {
                                    throw QueuedBuildTerminatorImpl.a(illegalArgumentException);
                                }
                                try {
                                    block45: {
                                        try {
                                            try {
                                                try {
                                                    try {
                                                        if (!bl4 || buildContinuationInterruptedInfo.getBuildContinuationMode() != DependencyOptions.BuildContinuationMode.CANCEL) break block44;
                                                    }
                                                    catch (IllegalArgumentException illegalArgumentException) {
                                                        throw QueuedBuildTerminatorImpl.a(illegalArgumentException);
                                                    }
                                                    if (!this.isFinished((BuildPromotion)buildPromotionEx2)) break block44;
                                                }
                                                catch (IllegalArgumentException illegalArgumentException) {
                                                    throw QueuedBuildTerminatorImpl.a(illegalArgumentException);
                                                }
                                                if (bl) break block45;
                                            }
                                            catch (IllegalArgumentException illegalArgumentException) {
                                                throw QueuedBuildTerminatorImpl.a(illegalArgumentException);
                                            }
                                            if (!this.scheduleTermination(string, buildPromotionEx, buildPromotionEx2, buildContinuationInterruptedInfo)) break block46;
                                        }
                                        catch (IllegalArgumentException illegalArgumentException) {
                                            throw QueuedBuildTerminatorImpl.a(illegalArgumentException);
                                        }
                                    }
                                    bl6 = true;
                                    break block47;
                                }
                                catch (IllegalArgumentException illegalArgumentException) {
                                    throw QueuedBuildTerminatorImpl.a(illegalArgumentException);
                                }
                            }
                            bl6 = false;
                        }
                        return bl6;
                    }
                    try {
                        block48: {
                            try {
                                try {
                                    try {
                                        try {
                                            if (!bl3 || buildContinuationInterruptedInfo.getBuildContinuationMode() != DependencyOptions.BuildContinuationMode.MAKE_FAILED_TO_START) continue;
                                        }
                                        catch (IllegalArgumentException illegalArgumentException) {
                                            throw QueuedBuildTerminatorImpl.a(illegalArgumentException);
                                        }
                                        if (!this.isFinished((BuildPromotion)buildPromotionEx2)) continue;
                                    }
                                    catch (IllegalArgumentException illegalArgumentException) {
                                        throw QueuedBuildTerminatorImpl.a(illegalArgumentException);
                                    }
                                    if (bl) break block48;
                                }
                                catch (IllegalArgumentException illegalArgumentException) {
                                    throw QueuedBuildTerminatorImpl.a(illegalArgumentException);
                                }
                                if (!this.scheduleTermination(string, buildPromotionEx, buildPromotionEx2, buildContinuationInterruptedInfo)) break block49;
                            }
                            catch (IllegalArgumentException illegalArgumentException) {
                                throw QueuedBuildTerminatorImpl.a(illegalArgumentException);
                            }
                        }
                        bl5 = true;
                        break block50;
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        throw QueuedBuildTerminatorImpl.a(illegalArgumentException);
                    }
                }
                bl5 = false;
            }
            return bl5;
        }
        return false;
    }

    private boolean isFinished(@NotNull BuildPromotion buildPromotion) {
        boolean bl;
        block8: {
            block7: {
                try {
                    if (buildPromotion == null) {
                        QueuedBuildTerminatorImpl.$$$reportNull$$$0(4);
                    }
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw QueuedBuildTerminatorImpl.a(illegalArgumentException);
                }
                SBuild sBuild = buildPromotion.getAssociatedBuild();
                try {
                    try {
                        if (sBuild == null || !sBuild.isFinished()) break block7;
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        throw QueuedBuildTerminatorImpl.a(illegalArgumentException);
                    }
                    bl = true;
                    break block8;
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw QueuedBuildTerminatorImpl.a(illegalArgumentException);
                }
            }
            bl = false;
        }
        return bl;
    }

    private boolean scheduleTermination(@NotNull String string, @NotNull BuildPromotionEx buildPromotionEx, @NotNull BuildPromotionEx buildPromotionEx2, @NotNull DependentBuildStartPredicate.BuildContinuationInterruptedInfo buildContinuationInterruptedInfo) {
        String string2;
        boolean bl;
        TerminationTaskMetadata terminationTaskMetadata;
        TerminationTaskMetadata terminationTaskMetadata2;
        boolean bl2;
        try {
            if (string == null) {
                QueuedBuildTerminatorImpl.$$$reportNull$$$0(5);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw QueuedBuildTerminatorImpl.a(illegalArgumentException);
        }
        try {
            if (buildPromotionEx == null) {
                QueuedBuildTerminatorImpl.$$$reportNull$$$0(6);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw QueuedBuildTerminatorImpl.a(illegalArgumentException);
        }
        try {
            if (buildPromotionEx2 == null) {
                QueuedBuildTerminatorImpl.$$$reportNull$$$0(7);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw QueuedBuildTerminatorImpl.a(illegalArgumentException);
        }
        try {
            if (buildContinuationInterruptedInfo == null) {
                QueuedBuildTerminatorImpl.$$$reportNull$$$0(8);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw QueuedBuildTerminatorImpl.a(illegalArgumentException);
        }
        try {
            if (this.myScheduledTerminationsProcessor.isQueueFull()) {
                LOG.info("Unable to schedule queued " + LogUtil.describe((BuildPromotion)buildPromotionEx) + " for termination as the current termination tasks queue is full.");
                return false;
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw QueuedBuildTerminatorImpl.a(illegalArgumentException);
        }
        try {
            bl2 = buildContinuationInterruptedInfo.getBuildContinuationMode() == DependencyOptions.BuildContinuationMode.CANCEL;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw QueuedBuildTerminatorImpl.a(illegalArgumentException);
        }
        boolean bl3 = bl2;
        String string3 = buildContinuationInterruptedInfo.getDependencyFailureComment();
        try {
            TerminationTaskMetadata terminationTaskMetadata3;
            terminationTaskMetadata2 = terminationTaskMetadata3;
            terminationTaskMetadata = terminationTaskMetadata3;
            bl = bl3;
            string2 = string3 != null ? string3 : "Build was terminated because one of the builds it depends on failed or was canceled.";
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw QueuedBuildTerminatorImpl.a(illegalArgumentException);
        }
        terminationTaskMetadata2(bl, string2, buildContinuationInterruptedInfo.getBuildProblem());
        TerminationTaskMetadata terminationTaskMetadata4 = terminationTaskMetadata;
        LOG.info("Queued " + LogUtil.describe((BuildPromotion)buildPromotionEx) + " is scheduled for termination due to dependency " + LogUtil.describe((BuildPromotion)buildPromotionEx2) + " failure");
        this.myScheduledTerminationTasks.put(string, terminationTaskMetadata4);
        this.myScheduledTerminationsProcessor.addToQueue((Object)string);
        return true;
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
