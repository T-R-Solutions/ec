/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jetbrains.buildServer.AlreadyRunningBuildException
 *  jetbrains.buildServer.Build
 *  jetbrains.buildServer.BuildProblemData
 *  jetbrains.buildServer.BuildProblemDataEx
 *  jetbrains.buildServer.ShutdownInProgressException
 *  jetbrains.buildServer.TeamCityRuntimeException
 *  jetbrains.buildServer.agentServer.AgentBuild
 *  jetbrains.buildServer.agentServer.AgentBuildResult
 *  jetbrains.buildServer.agentServer.AgentCheckoutDirRevisionInfo
 *  jetbrains.buildServer.log.Loggers
 *  jetbrains.buildServer.messages.ErrorData
 *  jetbrains.buildServer.messages.Status
 *  jetbrains.buildServer.metrics.ServerBuildDelayMetrics
 *  jetbrains.buildServer.serverSide.BuildAgentEx
 *  jetbrains.buildServer.serverSide.BuildAgentManagerEx
 *  jetbrains.buildServer.serverSide.BuildEx
 *  jetbrains.buildServer.serverSide.BuildPromotion
 *  jetbrains.buildServer.serverSide.BuildPromotionEx
 *  jetbrains.buildServer.serverSide.BuildServerEx
 *  jetbrains.buildServer.serverSide.BuildServerListener
 *  jetbrains.buildServer.serverSide.DependencyConsumer$Result
 *  jetbrains.buildServer.serverSide.IOGuard
 *  jetbrains.buildServer.serverSide.NodeResponsibility
 *  jetbrains.buildServer.serverSide.PatchMapLogger
 *  jetbrains.buildServer.serverSide.RunningBuildEx
 *  jetbrains.buildServer.serverSide.SBuild
 *  jetbrains.buildServer.serverSide.SRunningBuild
 *  jetbrains.buildServer.serverSide.TeamCityNode
 *  jetbrains.buildServer.serverSide.TeamCityNodes
 *  jetbrains.buildServer.serverSide.TeamCityProperties
 *  jetbrains.buildServer.serverSide.VcsStatusLogger
 *  jetbrains.buildServer.serverSide.agentless.AgentlessBuildExecutionManager
 *  jetbrains.buildServer.serverSide.buildLog.MessageAttrs
 *  jetbrains.buildServer.serverSide.comments.Comment
 *  jetbrains.buildServer.serverSide.impl.BuildChangesCollectException
 *  jetbrains.buildServer.serverSide.impl.BuildTypeImpl
 *  jetbrains.buildServer.serverSide.impl.CancelCheck
 *  jetbrains.buildServer.serverSide.impl.CancelableTaskHolder
 *  jetbrains.buildServer.serverSide.impl.DependentBuildStartPredicate
 *  jetbrains.buildServer.serverSide.impl.LogUtil
 *  jetbrains.buildServer.serverSide.impl.NodeStats
 *  jetbrains.buildServer.serverSide.impl.NodesBuildsEvents
 *  jetbrains.buildServer.serverSide.impl.StartingBuildNodeChooser
 *  jetbrains.buildServer.serverSide.impl.agent.AgentTimeoutException
 *  jetbrains.buildServer.serverSide.vcs.FullCheckoutFlag
 *  jetbrains.buildServer.users.User
 *  jetbrains.buildServer.util.EventDispatcher
 *  jetbrains.buildServer.util.NamedThreadFactory
 *  jetbrains.buildServer.util.StringUtil
 *  jetbrains.buildServer.util.TimeService
 *  jetbrains.buildServer.vcs.VcsChangesCollectorFactory
 *  jetbrains.buildServer.vcs.VcsException
 *  jetbrains.buildServer.vcs.VcsRootEntry
 *  jetbrains.buildServer.vcs.patches.LowLevelPatchBuilder
 *  jetbrains.buildServer.vcs.patches.LowLevelPatchBuilderImpl
 *  org.jetbrains.annotations.NonNls
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package jetbrains.buildServer.serverSide.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;
import jetbrains.buildServer.AlreadyRunningBuildException;
import jetbrains.buildServer.Build;
import jetbrains.buildServer.BuildProblemData;
import jetbrains.buildServer.BuildProblemDataEx;
import jetbrains.buildServer.ShutdownInProgressException;
import jetbrains.buildServer.TeamCityRuntimeException;
import jetbrains.buildServer.agentServer.AgentBuild;
import jetbrains.buildServer.agentServer.AgentBuildResult;
import jetbrains.buildServer.agentServer.AgentCheckoutDirRevisionInfo;
import jetbrains.buildServer.log.Loggers;
import jetbrains.buildServer.messages.ErrorData;
import jetbrains.buildServer.messages.Status;
import jetbrains.buildServer.metrics.ServerBuildDelayMetrics;
import jetbrains.buildServer.serverSide.BuildAgentEx;
import jetbrains.buildServer.serverSide.BuildAgentManagerEx;
import jetbrains.buildServer.serverSide.BuildEx;
import jetbrains.buildServer.serverSide.BuildPromotion;
import jetbrains.buildServer.serverSide.BuildPromotionEx;
import jetbrains.buildServer.serverSide.BuildServerEx;
import jetbrains.buildServer.serverSide.BuildServerListener;
import jetbrains.buildServer.serverSide.DependencyConsumer;
import jetbrains.buildServer.serverSide.IOGuard;
import jetbrains.buildServer.serverSide.NodeResponsibility;
import jetbrains.buildServer.serverSide.PatchMapLogger;
import jetbrains.buildServer.serverSide.RunningBuildEx;
import jetbrains.buildServer.serverSide.SBuild;
import jetbrains.buildServer.serverSide.SRunningBuild;
import jetbrains.buildServer.serverSide.TeamCityNode;
import jetbrains.buildServer.serverSide.TeamCityNodes;
import jetbrains.buildServer.serverSide.TeamCityProperties;
import jetbrains.buildServer.serverSide.VcsStatusLogger;
import jetbrains.buildServer.serverSide.agentless.AgentlessBuildExecutionManager;
import jetbrains.buildServer.serverSide.buildLog.MessageAttrs;
import jetbrains.buildServer.serverSide.comments.Comment;
import jetbrains.buildServer.serverSide.impl.BuildChangesCollectException;
import jetbrains.buildServer.serverSide.impl.BuildTypeImpl;
import jetbrains.buildServer.serverSide.impl.CancelCheck;
import jetbrains.buildServer.serverSide.impl.CancelableTaskHolder;
import jetbrains.buildServer.serverSide.impl.DependentBuildStartPredicate;
import jetbrains.buildServer.serverSide.impl.LogUtil;
import jetbrains.buildServer.serverSide.impl.NodeStats;
import jetbrains.buildServer.serverSide.impl.NodesBuildsEvents;
import jetbrains.buildServer.serverSide.impl.StartingBuildNodeChooser;
import jetbrains.buildServer.serverSide.impl.agent.AgentTimeoutException;
import jetbrains.buildServer.serverSide.vcs.FullCheckoutFlag;
import jetbrains.buildServer.users.User;
import jetbrains.buildServer.util.EventDispatcher;
import jetbrains.buildServer.util.NamedThreadFactory;
import jetbrains.buildServer.util.StringUtil;
import jetbrains.buildServer.util.TimeService;
import jetbrains.buildServer.vcs.VcsChangesCollectorFactory;
import jetbrains.buildServer.vcs.VcsException;
import jetbrains.buildServer.vcs.VcsRootEntry;
import jetbrains.buildServer.vcs.patches.LowLevelPatchBuilder;
import jetbrains.buildServer.vcs.patches.LowLevelPatchBuilderImpl;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Exception performing whole class analysis ignored.
 */
public class BuildStarter {
    public static final String STARTING_BUILD_ON_AGENT = "Starting the build on the agent ";
    public static final String BUILD_HAS_BEEN_STARTED_BY = "The build has been started by: ";
    public static final String WAIT_FOR_CHANGES_COLLECTING_DELAY_PROP = "teamcity.runningBuild.waitForChangesCollectingDelay";
    @NonNls
    public static final String PERCENTAGE_LIMIT = "percentageLimit";
    private final CancelableTaskHolder myPatchLock;
    private final CancelableTaskHolder myCheckingForChangesLocks;
    private final ExecutorService myExecutor;
    @NotNull
    private final BuildServerEx myServer;
    @NotNull
    private final VcsStatusLogger myVcsStatusLogger;
    @NotNull
    private final BuildAgentManagerEx myAgentManager;
    @NotNull
    private final TimeService myTimeService;
    @NotNull
    private final TeamCityNodes myTeamCityNodes;
    @NotNull
    private final EventDispatcher<BuildServerListener> myDispatcher;
    @NotNull
    private final NodesBuildsEvents myNodesBuildsEvents;
    @NotNull
    private final VcsChangesCollectorFactory myVcsChangesCollectorFactory;
    @NotNull
    private final NodeStats myNodeStats;
    @NotNull
    private final ServerBuildDelayMetrics myServerBuildDelayMetrics;
    @NotNull
    private final AgentlessBuildExecutionManager myAgentlessBuildExecutionManager;

    public BuildStarter(@NotNull BuildServerEx buildServerEx, @NotNull VcsStatusLogger vcsStatusLogger, @NotNull ExecutorService executorService, @NotNull BuildAgentManagerEx buildAgentManagerEx, @NotNull TeamCityNodes teamCityNodes, @NotNull EventDispatcher<BuildServerListener> eventDispatcher, @NotNull NodesBuildsEvents nodesBuildsEvents, @NotNull VcsChangesCollectorFactory vcsChangesCollectorFactory, @NotNull NodeStats nodeStats, @NotNull ServerBuildDelayMetrics serverBuildDelayMetrics, @NotNull AgentlessBuildExecutionManager agentlessBuildExecutionManager) {
        if (buildServerEx == null) {
            BuildStarter.$$$reportNull$$$0(0);
        }
        if (vcsStatusLogger == null) {
            BuildStarter.$$$reportNull$$$0(1);
        }
        if (executorService == null) {
            BuildStarter.$$$reportNull$$$0(2);
        }
        if (buildAgentManagerEx == null) {
            BuildStarter.$$$reportNull$$$0(3);
        }
        if (teamCityNodes == null) {
            BuildStarter.$$$reportNull$$$0(4);
        }
        if (eventDispatcher == null) {
            BuildStarter.$$$reportNull$$$0(5);
        }
        if (nodesBuildsEvents == null) {
            BuildStarter.$$$reportNull$$$0(6);
        }
        if (vcsChangesCollectorFactory == null) {
            BuildStarter.$$$reportNull$$$0(7);
        }
        if (nodeStats == null) {
            BuildStarter.$$$reportNull$$$0(8);
        }
        if (serverBuildDelayMetrics == null) {
            BuildStarter.$$$reportNull$$$0(9);
        }
        if (agentlessBuildExecutionManager == null) {
            BuildStarter.$$$reportNull$$$0(10);
        }
        this.myPatchLock = new CancelableTaskHolder();
        this.myCheckingForChangesLocks = new CancelableTaskHolder();
        this.myServer = buildServerEx;
        this.myVcsStatusLogger = vcsStatusLogger;
        this.myExecutor = executorService;
        this.myAgentManager = buildAgentManagerEx;
        this.myTeamCityNodes = teamCityNodes;
        this.myTimeService = buildServerEx.getTimeService();
        this.myDispatcher = eventDispatcher;
        this.myNodesBuildsEvents = nodesBuildsEvents;
        this.myVcsChangesCollectorFactory = vcsChangesCollectorFactory;
        this.myNodeStats = nodeStats;
        this.myServerBuildDelayMetrics = serverBuildDelayMetrics;
        this.myAgentlessBuildExecutionManager = agentlessBuildExecutionManager;
    }

    @NotNull
    public CompletableFuture<Void> startBuildAsync(@NotNull RunningBuildEx runningBuildEx) {
        CompletableFuture<Void> completableFuture;
        String string;
        try {
            if (runningBuildEx == null) {
                BuildStarter.$$$reportNull$$$0(11);
            }
        }
        catch (RejectedExecutionException rejectedExecutionException) {
            throw BuildStarter.a(rejectedExecutionException);
        }
        try {
            string = runningBuildEx.isCompositeBuild() ? "composite " : "";
        }
        catch (RejectedExecutionException rejectedExecutionException) {
            throw BuildStarter.a(rejectedExecutionException);
        }
        String string2 = string;
        try {
            completableFuture = CompletableFuture.runAsync(() -> NamedThreadFactory.executeWithNewThreadName((String)("Starting " + string2 + "build " + LogUtil.describe((Build)runningBuildEx)), () -> {
                try {
                    if (this.myServer.isShuttingDown()) {
                        return;
                    }
                }
                catch (RejectedExecutionException rejectedExecutionException) {
                    throw BuildStarter.a(rejectedExecutionException);
                }
                this.logBuildStarted(runningBuildEx);
                this.handleBuildStartAction(runningBuildEx, () -> {
                    try {
                        if (!this.beforeChangesCollecting(runningBuildEx)) {
                            return;
                        }
                    }
                    catch (RejectedExecutionException rejectedExecutionException) {
                        throw BuildStarter.a(rejectedExecutionException);
                    }
                    Future future = this.myVcsChangesCollectorFactory.createCollectorForStartingBuild().scheduleCheckingForChanges(runningBuildEx.getBuildPromotion(), (CancelCheck)this.myCheckingForChangesLocks);
                    this.waitForChangesCollectingAndContinue(future, runningBuildEx);
                });
            }), this.myExecutor);
            if (completableFuture == null) {
                BuildStarter.$$$reportNull$$$0(12);
            }
        }
        catch (RejectedExecutionException rejectedExecutionException) {
            throw BuildStarter.a(rejectedExecutionException);
        }
        return completableFuture;
    }

    private void logBuildStarted(@NotNull RunningBuildEx runningBuildEx) {
        block6: {
            block5: {
                try {
                    if (runningBuildEx == null) {
                        BuildStarter.$$$reportNull$$$0(13);
                    }
                }
                catch (RejectedExecutionException rejectedExecutionException) {
                    throw BuildStarter.a(rejectedExecutionException);
                }
                try {
                    if (!runningBuildEx.isCompositeBuild()) break block5;
                    Loggers.ACTIVITIES.info("Composite build started; " + LogUtil.describeInDetail((SBuild)runningBuildEx));
                    break block6;
                }
                catch (RejectedExecutionException rejectedExecutionException) {
                    throw BuildStarter.a(rejectedExecutionException);
                }
            }
            Loggers.ACTIVITIES.info("Build started; " + LogUtil.describeInDetail((SBuild)runningBuildEx));
        }
    }

    /*
     * Loose catch block
     */
    private void waitForChangesCollectingAndContinue(@NotNull Future<Void> future, @NotNull RunningBuildEx runningBuildEx) {
        block15: {
            block16: {
                try {
                    if (future == null) {
                        BuildStarter.$$$reportNull$$$0(14);
                    }
                }
                catch (TimeoutException timeoutException) {
                    throw BuildStarter.a(timeoutException);
                }
                try {
                    if (runningBuildEx == null) {
                        BuildStarter.$$$reportNull$$$0(15);
                    }
                }
                catch (TimeoutException timeoutException) {
                    throw BuildStarter.a(timeoutException);
                }
                boolean bl = true;
                try {
                    future.get(TeamCityProperties.getInteger((String)"teamcity.runningBuild.waitForChangesCollectingDelay", (int)5000), TimeUnit.MILLISECONDS);
                }
                catch (TimeoutException timeoutException) {
                    bl = false;
                }
                catch (InterruptedException | ExecutionException exception) {
                    Loggers.SERVER.warnAndDebugDetails("Exception while waiting for checking for changes to complete for build: " + LogUtil.describe((Build)runningBuildEx), (Throwable)exception);
                    runningBuildEx.getBuildPromotion().setFailedToCollectChanges((Throwable)exception);
                }
                if (bl) break block15;
                try {
                    block17: {
                        if (!this.rescheduleWaitForChangesCollecting(future, runningBuildEx)) break block16;
                        break block17;
                        catch (TimeoutException timeoutException) {
                            throw BuildStarter.a(timeoutException);
                        }
                    }
                    return;
                }
                catch (TimeoutException timeoutException) {
                    throw BuildStarter.a(timeoutException);
                }
            }
            try {
                future.get();
            }
            catch (Throwable throwable) {
                Loggers.SERVER.warnAndDebugDetails("Exception while waiting for checking for changes to complete for build: " + LogUtil.describe((Build)runningBuildEx), throwable);
                runningBuildEx.getBuildPromotion().setFailedToCollectChanges(throwable);
            }
        }
        this.afterChangesCollecting(runningBuildEx);
    }

    private boolean rescheduleWaitForChangesCollecting(@NotNull Future<Void> future, @NotNull RunningBuildEx runningBuildEx) {
        try {
            if (future == null) {
                BuildStarter.$$$reportNull$$$0(16);
            }
        }
        catch (RejectedExecutionException rejectedExecutionException) {
            throw BuildStarter.a(rejectedExecutionException);
        }
        try {
            if (runningBuildEx == null) {
                BuildStarter.$$$reportNull$$$0(17);
            }
        }
        catch (RejectedExecutionException rejectedExecutionException) {
            throw BuildStarter.a(rejectedExecutionException);
        }
        try {
            this.myExecutor.submit(() -> this.handleBuildStartAction(runningBuildEx, () -> this.waitForChangesCollectingAndContinue(future, runningBuildEx)));
            return true;
        }
        catch (RejectedExecutionException rejectedExecutionException) {
            Loggers.SERVER.warnAndDebugDetails("Could not reschedule the waiting for checking for changes operation for build: " + LogUtil.describe((Build)runningBuildEx), (Throwable)rejectedExecutionException);
            return false;
        }
    }

    public void startBuild(@NotNull RunningBuildEx runningBuildEx) {
        try {
            if (runningBuildEx == null) {
                BuildStarter.$$$reportNull$$$0(18);
            }
        }
        catch (RejectedExecutionException rejectedExecutionException) {
            throw BuildStarter.a(rejectedExecutionException);
        }
        this.logBuildStarted(runningBuildEx);
        this.handleBuildStartAction(runningBuildEx, () -> {
            try {
                if (!this.beforeChangesCollecting(runningBuildEx)) {
                    return;
                }
            }
            catch (RejectedExecutionException rejectedExecutionException) {
                throw BuildStarter.a(rejectedExecutionException);
            }
            long l = this.myTimeService.now();
            try {
                this.myVcsChangesCollectorFactory.createCollectorForStartingBuild().scheduleCheckingForChangesAndWait(runningBuildEx.getBuildPromotion(), (CancelCheck)this.myCheckingForChangesLocks);
            }
            finally {
                this.updateCheckingForChangesDelayMetric(l);
            }
            this.afterChangesCollecting(runningBuildEx);
        });
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     */
    private boolean beforeChangesCollecting(@NotNull RunningBuildEx runningBuildEx) {
        try {
            if (runningBuildEx == null) {
                BuildStarter.$$$reportNull$$$0(19);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildStarter.a(illegalStateException);
        }
        try {
            Comment comment;
            block27: {
                runningBuildEx.prepareArtifactDependencies();
                comment = runningBuildEx.getBuildPromotion().getBuildComment();
                try {
                    if (comment != null) {
                        ((BuildServerListener)this.myDispatcher.getMulticaster()).buildCommented((SBuild)runningBuildEx, (User)comment.getUser(), comment.getComment());
                    }
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildStarter.a(illegalStateException);
                }
                runningBuildEx.getBuildLog().messageAsync("The build is removed from the queue to be prepared for the start", Status.NORMAL, MessageAttrs.serverMessage());
                if (!runningBuildEx.isCompositeBuild()) break block27;
                try {
                    block28: {
                        if (!this.hasUnfinishedDependencies(runningBuildEx)) break block27;
                        break block28;
                        catch (IllegalStateException illegalStateException) {
                            throw BuildStarter.a(illegalStateException);
                        }
                    }
                    runningBuildEx.getBuildLog().messageAsync("This build is composite, it aggregates results from all its dependencies and finishes as soon as all dependencies are finished", Status.NORMAL, MessageAttrs.serverMessage());
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildStarter.a(illegalStateException);
                }
            }
            boolean bl = runningBuildEx.getBuildPromotion().isAgentLessBuild();
            if (!bl) {
                BuildAgentEx buildAgentEx = (BuildAgentEx)runningBuildEx.getAgent();
                buildAgentEx.buildStartedOnAgent((SRunningBuild)runningBuildEx);
            }
            try {
                runningBuildEx.writeSettingsDigestFile();
            }
            catch (IllegalStateException illegalStateException) {
                Loggers.SERVER.warnAndDebugDetails("Could not write the settings digest file", (Throwable)illegalStateException);
            }
            try {
                ((BuildServerListener)this.myDispatcher.getMulticaster()).buildStarted((SRunningBuild)runningBuildEx);
                this.myNodesBuildsEvents.publishBuildStarted((SRunningBuild)runningBuildEx);
                if (comment != null) {
                    this.myNodesBuildsEvents.publishCommentUpdated((BuildPromotion)runningBuildEx.getBuildPromotion());
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildStarter.a(illegalStateException);
            }
            if (runningBuildEx.isCompositeBuild()) {
                boolean bl2 = true;
                return bl2;
            }
            if (this.finishBuildIfWasCanceled(runningBuildEx)) {
                boolean bl3 = false;
                return bl3;
            }
            if (!this.processBuildProblems(runningBuildEx)) {
                boolean bl4 = false;
                return bl4;
            }
            if (this.checkoutRulesContainWildcards((SRunningBuild)runningBuildEx)) {
                this.failBuildWithException(null, (Throwable)new VcsException("Checkout rules shouldn't contain wildcards"), runningBuildEx);
                boolean bl5 = false;
                return bl5;
            }
            boolean bl6 = true;
            return bl6;
        }
        finally {
            this.updateBuildStartDelayMetric(runningBuildEx.getServerStartDate().getTime());
        }
    }

    private boolean hasUnfinishedDependencies(@NotNull RunningBuildEx runningBuildEx) {
        try {
            if (runningBuildEx == null) {
                BuildStarter.$$$reportNull$$$0(20);
            }
        }
        catch (RejectedExecutionException rejectedExecutionException) {
            throw BuildStarter.a(rejectedExecutionException);
        }
        boolean[] blArray = new boolean[1];
        runningBuildEx.getBuildPromotion().traverseDependencies(buildPromotionEx -> {
            try {
                if (buildPromotionEx.getAssociatedBuild() instanceof SRunningBuild) {
                    blArray[0] = true;
                    return DependencyConsumer.Result.STOP;
                }
            }
            catch (RejectedExecutionException rejectedExecutionException) {
                throw BuildStarter.a(rejectedExecutionException);
            }
            return DependencyConsumer.Result.CONTINUE;
        });
        return blArray[0];
    }

    private void updateBuildStartDelayMetric(long l) {
        long l2 = Math.max(0L, this.myTimeService.now() - l);
        this.myServerBuildDelayMetrics.getBuildStartDelayMetric().increment((double)l2);
    }

    private void updateCheckingForChangesDelayMetric(long l) {
        long l2 = Math.max(0L, this.myTimeService.now() - l);
        this.myServerBuildDelayMetrics.getCheckingForChangesDelayMetric().increment((double)l2);
    }

    private void updateAgentRunBuildDelayMetric(long l) {
        long l2 = Math.max(0L, this.myTimeService.now() - l);
        this.myServerBuildDelayMetrics.getAgentRunBuildDelayMetric().increment((double)l2);
    }

    private void afterChangesCollecting(@NotNull RunningBuildEx runningBuildEx) {
        block16: {
            block15: {
                block14: {
                    try {
                        if (runningBuildEx == null) {
                            BuildStarter.$$$reportNull$$$0(21);
                        }
                    }
                    catch (RejectedExecutionException rejectedExecutionException) {
                        throw BuildStarter.a(rejectedExecutionException);
                    }
                    try {
                        if (runningBuildEx.isCompositeBuild() || !runningBuildEx.getBuildPromotion().isFailedToCollectChanges()) break block14;
                    }
                    catch (RejectedExecutionException rejectedExecutionException) {
                        throw BuildStarter.a(rejectedExecutionException);
                    }
                    Object object = "Failed to collect changes for unknown reason.";
                    String string = runningBuildEx.getBuildPromotion().getCollectChangesError();
                    if (string != null) {
                        object = "Failed to collect changes, error: " + string;
                    }
                    Integer n = runningBuildEx.getBuildPromotion().getCollectChangesErrorAnchor();
                    this.failBuildWithException("CHECKING_FOR_CHANGES_ERROR", (Throwable)new BuildChangesCollectException((String)object), runningBuildEx, n);
                    return;
                }
                try {
                    ((BuildServerListener)this.myDispatcher.getMulticaster()).changesLoaded((SRunningBuild)runningBuildEx);
                    this.myNodesBuildsEvents.publishChangesLoaded((SRunningBuild)runningBuildEx);
                    if (runningBuildEx.isCompositeBuild()) {
                        return;
                    }
                }
                catch (RejectedExecutionException rejectedExecutionException) {
                    throw BuildStarter.a(rejectedExecutionException);
                }
                try {
                    if (this.finishBuildIfWasCanceled(runningBuildEx)) {
                        return;
                    }
                }
                catch (RejectedExecutionException rejectedExecutionException) {
                    throw BuildStarter.a(rejectedExecutionException);
                }
                boolean bl = runningBuildEx.getBuildPromotion().isAgentLessBuild();
                try {
                    if (bl) break block15;
                    this.startBuildOnAgent(runningBuildEx);
                    break block16;
                }
                catch (RejectedExecutionException rejectedExecutionException) {
                    throw BuildStarter.a(rejectedExecutionException);
                }
            }
            this.startAgentLessBuild(runningBuildEx);
        }
    }

    private void startAgentLessBuild(@NotNull RunningBuildEx runningBuildEx) {
        try {
            if (runningBuildEx == null) {
                BuildStarter.$$$reportNull$$$0(22);
            }
        }
        catch (RejectedExecutionException rejectedExecutionException) {
            throw BuildStarter.a(rejectedExecutionException);
        }
        BuildAgentEx buildAgentEx = (BuildAgentEx)runningBuildEx.getAgent();
        try {
            runningBuildEx.getBuildLog().flushIfNeeded();
            if (!this.processBuildProblems(runningBuildEx)) {
                return;
            }
        }
        catch (RejectedExecutionException rejectedExecutionException) {
            throw BuildStarter.a(rejectedExecutionException);
        }
        runningBuildEx.getBuildLog().messageAsync("The build has been started by: " + buildAgentEx.getName(), Status.NORMAL, MessageAttrs.serverMessage());
        runningBuildEx.getBuildLog().flushIfNeeded();
        this.assignBuildToBuildMessagesProcessingNode(runningBuildEx);
        Date date = this.now();
        runningBuildEx.setStartTimes(date, date, TimeZone.getDefault());
        this.myAgentlessBuildExecutionManager.tryAndStartBuildExecution(runningBuildEx.getBuildPromotion());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void startBuildOnAgent(@NotNull RunningBuildEx runningBuildEx) {
        try {
            if (runningBuildEx == null) {
                BuildStarter.$$$reportNull$$$0(23);
            }
        }
        catch (RejectedExecutionException rejectedExecutionException) {
            throw BuildStarter.a(rejectedExecutionException);
        }
        long l = this.myTimeService.now();
        try {
            String string;
            AgentBuild agentBuild;
            BuildAgentEx buildAgentEx = (BuildAgentEx)runningBuildEx.getAgent();
            runningBuildEx.getBuildLog().messageAsync("Starting the build on the agent \"" + buildAgentEx.getName() + "\"", Status.NORMAL, MessageAttrs.serverMessage());
            AgentBuild agentBuild2 = runningBuildEx.createAgentBuild();
            runningBuildEx.getBuildLog().flushIfNeeded();
            if (!this.processBuildProblems(runningBuildEx)) {
                return;
            }
            this.assignBuildToBuildMessagesProcessingNode(runningBuildEx);
            TeamCityNode teamCityNode = runningBuildEx.getBuildPromotion().getOwnerNode();
            try {
                agentBuild = agentBuild2;
                string = teamCityNode == null ? null : teamCityNode.getId();
            }
            catch (RejectedExecutionException rejectedExecutionException) {
                throw BuildStarter.a(rejectedExecutionException);
            }
            agentBuild.setOwnerNodeId(string);
            AgentBuildResult agentBuildResult = buildAgentEx.runBuild(agentBuild2);
            runningBuildEx.setStartTimes(agentBuildResult.getAgentBuildStartTimeUTC(), this.now(), agentBuildResult.getAgentTimeZone());
        }
        finally {
            this.updateAgentRunBuildDelayMetric(l);
        }
    }

    private void handleBuildStartAction(@NotNull RunningBuildEx runningBuildEx, @NotNull Runnable runnable) {
        try {
            if (runningBuildEx == null) {
                BuildStarter.$$$reportNull$$$0(24);
            }
        }
        catch (AlreadyRunningBuildException alreadyRunningBuildException) {
            throw BuildStarter.a(alreadyRunningBuildException);
        }
        try {
            if (runnable == null) {
                BuildStarter.$$$reportNull$$$0(25);
            }
        }
        catch (AlreadyRunningBuildException alreadyRunningBuildException) {
            throw BuildStarter.a(alreadyRunningBuildException);
        }
        try {
            runnable.run();
        }
        catch (AlreadyRunningBuildException alreadyRunningBuildException) {
            try {
                if (runningBuildEx.isCompositeBuild()) {
                    return;
                }
            }
            catch (AlreadyRunningBuildException alreadyRunningBuildException2) {
                throw BuildStarter.a(alreadyRunningBuildException2);
            }
            this.cancelBuildAndUnregisterAgent("Agent runs unknown build we're not aware of", (TeamCityRuntimeException)((Object)alreadyRunningBuildException), runningBuildEx);
        }
        catch (ShutdownInProgressException shutdownInProgressException) {
            try {
                if (runningBuildEx.isCompositeBuild()) {
                    return;
                }
            }
            catch (AlreadyRunningBuildException alreadyRunningBuildException) {
                throw BuildStarter.a(alreadyRunningBuildException);
            }
            this.cancelBuildAndUnregisterAgent("Build agent shutdown started", (TeamCityRuntimeException)((Object)shutdownInProgressException), runningBuildEx);
        }
        catch (AgentTimeoutException agentTimeoutException) {
            try {
                if (runningBuildEx.isCompositeBuild()) {
                    return;
                }
            }
            catch (AlreadyRunningBuildException alreadyRunningBuildException) {
                throw BuildStarter.a(alreadyRunningBuildException);
            }
            this.cancelBuildAndUnregisterAgent("Could not connect to build agent", (TeamCityRuntimeException)((Object)agentTimeoutException), runningBuildEx);
        }
        catch (TeamCityRuntimeException teamCityRuntimeException) {
            try {
                if (runningBuildEx.isCompositeBuild()) {
                    return;
                }
            }
            catch (AlreadyRunningBuildException alreadyRunningBuildException) {
                throw BuildStarter.a(alreadyRunningBuildException);
            }
            BuildStarter.logError(teamCityRuntimeException, runningBuildEx);
            String string = teamCityRuntimeException.getLocalizedMessage();
            if (string == null) {
                string = teamCityRuntimeException.toString();
            }
            runningBuildEx.markAsFinishedUnexpectedly(string);
        }
        catch (Throwable throwable) {
            this.failBuildWithException(null, throwable, runningBuildEx);
        }
    }

    private void assignBuildToBuildMessagesProcessingNode(@NotNull RunningBuildEx runningBuildEx) {
        BuildPromotionEx buildPromotionEx;
        block10: {
            try {
                if (runningBuildEx == null) {
                    BuildStarter.$$$reportNull$$$0(26);
                }
            }
            catch (RejectedExecutionException rejectedExecutionException) {
                throw BuildStarter.a(rejectedExecutionException);
            }
            buildPromotionEx = runningBuildEx.getBuildPromotion();
            String string = buildPromotionEx.getOwnerNodeUrl();
            try {
                if (string != null) {
                    return;
                }
            }
            catch (RejectedExecutionException rejectedExecutionException) {
                throw BuildStarter.a(rejectedExecutionException);
            }
            int n = TeamCityProperties.getInteger((String)"teamcity.multiNode.nodeInactivityTime.seconds", (int)120);
            List list = this.myTeamCityNodes.getOnlineNodes(n).stream().filter(teamCityNode -> teamCityNode.canAcceptHTTPRequests()).filter(teamCityNode -> teamCityNode.responsibilityEnabled(NodeResponsibility.CAN_PROCESS_BUILD_MESSAGES)).filter(teamCityNode -> teamCityNode.getEffectiveResponsibilities().contains(NodeResponsibility.CAN_PROCESS_BUILD_MESSAGES)).filter(teamCityNode -> {
                boolean bl;
                try {
                    bl = !teamCityNode.isStopping();
                }
                catch (RejectedExecutionException rejectedExecutionException) {
                    throw BuildStarter.a(rejectedExecutionException);
                }
                return bl;
            }).sorted(Comparator.comparing(TeamCityNode::getId)).collect(Collectors.toList());
            TeamCityNode teamCityNode2 = StartingBuildNodeChooser.selectNode(list, (Map)this.myNodeStats.getAssignedBuildsStats((Collection)this.myTeamCityNodes.getNodes()));
            try {
                try {
                    if (teamCityNode2 == null || !teamCityNode2.isSecondaryNode()) break block10;
                }
                catch (RejectedExecutionException rejectedExecutionException) {
                    throw BuildStarter.a(rejectedExecutionException);
                }
                if (!this.buildCanBeProcessedOnMessagesNode((SRunningBuild)runningBuildEx, teamCityNode2)) break block10;
            }
            catch (RejectedExecutionException rejectedExecutionException) {
                throw BuildStarter.a(rejectedExecutionException);
            }
            string = teamCityNode2.getUrl();
            runningBuildEx.getBuildLog().messageAsync("Build has been assigned to the secondary node (node URL: " + string + ")", Status.NORMAL, MessageAttrs.serverMessage().internalize());
            runningBuildEx.getBuildLog().flush();
            buildPromotionEx.setOwnerNodeUrl(string);
            return;
        }
        buildPromotionEx.setOwnerNodeUrl("#");
    }

    private boolean buildCanBeProcessedOnMessagesNode(@NotNull SRunningBuild sRunningBuild, @NotNull TeamCityNode teamCityNode) {
        try {
            if (sRunningBuild == null) {
                BuildStarter.$$$reportNull$$$0(27);
            }
        }
        catch (RejectedExecutionException rejectedExecutionException) {
            throw BuildStarter.a(rejectedExecutionException);
        }
        try {
            if (teamCityNode == null) {
                BuildStarter.$$$reportNull$$$0(28);
            }
        }
        catch (RejectedExecutionException rejectedExecutionException) {
            throw BuildStarter.a(rejectedExecutionException);
        }
        BuildPromotionEx buildPromotionEx = (BuildPromotionEx)sRunningBuild.getBuildPromotion();
        try {
            if (buildPromotionEx.getAttribute("teamcity.build.buildTypeArtifactsPath") == null) {
                Loggers.SERVER.warn("Cannot redirect build to " + teamCityNode.getId() + " because it does not have artifacts path attribute, build: " + LogUtil.describe((Build)sRunningBuild));
                return false;
            }
        }
        catch (RejectedExecutionException rejectedExecutionException) {
            throw BuildStarter.a(rejectedExecutionException);
        }
        try {
            if (!buildPromotionEx.hasFrozenSettings()) {
                Loggers.SERVER.warn("Cannot redirect build to " + teamCityNode.getId() + " because its settings are not frozen, build: " + LogUtil.describe((Build)sRunningBuild));
                return false;
            }
        }
        catch (RejectedExecutionException rejectedExecutionException) {
            throw BuildStarter.a(rejectedExecutionException);
        }
        return true;
    }

    private void cancelBuildAndUnregisterAgent(@NotNull String string, TeamCityRuntimeException teamCityRuntimeException, @NotNull RunningBuildEx runningBuildEx) {
        try {
            if (string == null) {
                BuildStarter.$$$reportNull$$$0(29);
            }
        }
        catch (Exception exception) {
            throw BuildStarter.a(exception);
        }
        try {
            if (runningBuildEx == null) {
                BuildStarter.$$$reportNull$$$0(30);
            }
        }
        catch (Exception exception) {
            throw BuildStarter.a(exception);
        }
        try {
            BuildStarter.logError(teamCityRuntimeException, runningBuildEx);
            runningBuildEx.runUnlessFinished(() -> runningBuildEx.getBuildLog().messageAsync(string + ". Details: " + teamCityRuntimeException.getLocalizedMessage(), Status.ERROR, MessageAttrs.serverMessage()));
            this.myAgentManager.unregisterAgent(runningBuildEx.getAgentId(), string);
            runningBuildEx.markAsFinishedUnexpectedly(string);
        }
        catch (Exception exception) {
            Loggers.SERVER.warn("Problem while trying to fail/cancel build " + LogUtil.describeInDetail((SBuild)runningBuildEx) + " because of " + ((Object)((Object)teamCityRuntimeException)).getClass().getName(), (Throwable)exception);
        }
    }

    private static void logError(TeamCityRuntimeException teamCityRuntimeException, @NotNull RunningBuildEx runningBuildEx) {
        try {
            if (runningBuildEx == null) {
                BuildStarter.$$$reportNull$$$0(31);
            }
        }
        catch (RejectedExecutionException rejectedExecutionException) {
            throw BuildStarter.a(rejectedExecutionException);
        }
        Loggers.SERVER.warnAndDebugDetails("Error while starting build " + LogUtil.describeInDetail((SBuild)runningBuildEx), (Throwable)teamCityRuntimeException);
    }

    private boolean processBuildProblems(@NotNull RunningBuildEx runningBuildEx) {
        try {
            if (runningBuildEx == null) {
                BuildStarter.$$$reportNull$$$0(32);
            }
        }
        catch (RejectedExecutionException rejectedExecutionException) {
            throw BuildStarter.a(rejectedExecutionException);
        }
        try {
            if (runningBuildEx.isCompositeBuild()) {
                return true;
            }
        }
        catch (RejectedExecutionException rejectedExecutionException) {
            throw BuildStarter.a(rejectedExecutionException);
        }
        runningBuildEx.getBuildPromotion().markAsIncompleteIfHasFailedToStartProblems();
        List list = runningBuildEx.getBuildPromotion().getFailureReasons();
        try {
            if (!list.isEmpty()) {
                ((BuildEx)runningBuildEx).buildProblemsChanged(Collections.emptyList(), list);
            }
        }
        catch (RejectedExecutionException rejectedExecutionException) {
            throw BuildStarter.a(rejectedExecutionException);
        }
        try {
            if (runningBuildEx.isInternalError()) {
                runningBuildEx.markAsFinished(new Date(), false);
                return false;
            }
        }
        catch (RejectedExecutionException rejectedExecutionException) {
            throw BuildStarter.a(rejectedExecutionException);
        }
        try {
            if (!new DependentBuildStartPredicate().continueBuild(runningBuildEx)) {
                runningBuildEx.markAsFinished(new Date(), false);
                return false;
            }
        }
        catch (RejectedExecutionException rejectedExecutionException) {
            throw BuildStarter.a(rejectedExecutionException);
        }
        return true;
    }

    private boolean finishBuildIfWasCanceled(RunningBuildEx runningBuildEx) {
        try {
            if (runningBuildEx.isCompositeBuild()) {
                return false;
            }
        }
        catch (RejectedExecutionException rejectedExecutionException) {
            throw BuildStarter.a(rejectedExecutionException);
        }
        try {
            if (runningBuildEx.isInterrupted()) {
                runningBuildEx.markAsFinished(new Date(), false);
                return true;
            }
        }
        catch (RejectedExecutionException rejectedExecutionException) {
            throw BuildStarter.a(rejectedExecutionException);
        }
        return false;
    }

    private boolean checkoutRulesContainWildcards(@NotNull SRunningBuild sRunningBuild) {
        try {
            if (sRunningBuild == null) {
                BuildStarter.$$$reportNull$$$0(33);
            }
        }
        catch (RejectedExecutionException rejectedExecutionException) {
            throw BuildStarter.a(rejectedExecutionException);
        }
        List list = sRunningBuild.getVcsRootEntries();
        for (VcsRootEntry vcsRootEntry : list) {
            try {
                if (!this.containsWildcard(vcsRootEntry)) continue;
                return true;
            }
            catch (RejectedExecutionException rejectedExecutionException) {
                throw BuildStarter.a(rejectedExecutionException);
            }
        }
        return false;
    }

    private boolean containsWildcard(@NotNull VcsRootEntry vcsRootEntry) {
        try {
            if (vcsRootEntry == null) {
                BuildStarter.$$$reportNull$$$0(34);
            }
        }
        catch (RejectedExecutionException rejectedExecutionException) {
            throw BuildStarter.a(rejectedExecutionException);
        }
        return vcsRootEntry.getCheckoutRules().containsWildcards();
    }

    private boolean isPreparationStageDone(@NotNull RunningBuildEx runningBuildEx) {
        try {
            if (runningBuildEx == null) {
                BuildStarter.$$$reportNull$$$0(35);
            }
        }
        catch (RejectedExecutionException rejectedExecutionException) {
            throw BuildStarter.a(rejectedExecutionException);
        }
        return runningBuildEx.getBuildLog().containsMessageWithHint("helper");
    }

    private void failBuildWithException(@Nullable String string, @NotNull Throwable throwable, @NotNull RunningBuildEx runningBuildEx) {
        try {
            if (throwable == null) {
                BuildStarter.$$$reportNull$$$0(36);
            }
        }
        catch (RejectedExecutionException rejectedExecutionException) {
            throw BuildStarter.a(rejectedExecutionException);
        }
        try {
            if (runningBuildEx == null) {
                BuildStarter.$$$reportNull$$$0(37);
            }
        }
        catch (RejectedExecutionException rejectedExecutionException) {
            throw BuildStarter.a(rejectedExecutionException);
        }
        this.failBuildWithException(string, throwable, runningBuildEx, null);
    }

    private void failBuildWithException(@Nullable String string, @NotNull Throwable throwable, @NotNull RunningBuildEx runningBuildEx, @Nullable Integer n) {
        block23: {
            String string2;
            String string3;
            block22: {
                block19: {
                    block21: {
                        block20: {
                            try {
                                if (throwable == null) {
                                    BuildStarter.$$$reportNull$$$0(38);
                                }
                            }
                            catch (RejectedExecutionException rejectedExecutionException) {
                                throw BuildStarter.a(rejectedExecutionException);
                            }
                            try {
                                if (runningBuildEx == null) {
                                    BuildStarter.$$$reportNull$$$0(39);
                                }
                            }
                            catch (RejectedExecutionException rejectedExecutionException) {
                                throw BuildStarter.a(rejectedExecutionException);
                            }
                            try {
                                try {
                                    Loggers.SERVER.warnAndDebugDetails("Error while starting build {id=" + runningBuildEx.getBuildId() + "}", throwable);
                                    if (!StringUtil.isEmptyOrSpaces((String)string)) break block19;
                                    if (this.isPreparationStageDone(runningBuildEx)) break block20;
                                }
                                catch (RejectedExecutionException rejectedExecutionException) {
                                    throw BuildStarter.a(rejectedExecutionException);
                                }
                                if (!runningBuildEx.isInternalError()) break block21;
                            }
                            catch (RejectedExecutionException rejectedExecutionException) {
                                throw BuildStarter.a(rejectedExecutionException);
                            }
                        }
                        string = "UNKNOWN";
                        break block19;
                    }
                    string = "PREPARATION_FAILURE_TYPE";
                }
                if (StringUtil.isEmptyOrSpaces((String)(string3 = throwable.getMessage()))) {
                    string3 = (String)ErrorData.TYPE_DESCRIPTIONS.get(string);
                }
                string2 = StringUtil.notNullize((String)string3);
                try {
                    if (n == null) break block22;
                    runningBuildEx.addBuildProblem((BuildProblemData)new BuildProblemDataEx(BuildProblemData.createBuildProblem((String)String.valueOf(string2.hashCode()), (String)string, (String)string2), n));
                    break block23;
                }
                catch (RejectedExecutionException rejectedExecutionException) {
                    throw BuildStarter.a(rejectedExecutionException);
                }
            }
            try {
                runningBuildEx.getBuildLog().messageAsync(string3, Status.ERROR, MessageAttrs.serverMessage().renderingHint("stacktrace"));
                if (ErrorData.isInternalError((String)string)) {
                    runningBuildEx.addBuildProblem(BuildProblemData.createBuildProblem((String)String.valueOf(string2.hashCode()), (String)string, (String)string2));
                }
            }
            catch (RejectedExecutionException rejectedExecutionException) {
                throw BuildStarter.a(rejectedExecutionException);
            }
        }
        try {
            if (runningBuildEx.isCompositeBuild()) {
                return;
            }
        }
        catch (RejectedExecutionException rejectedExecutionException) {
            throw BuildStarter.a(rejectedExecutionException);
        }
        runningBuildEx.markAsFinished(new Date(), false);
    }

    private Date now() {
        return new Date(this.myTimeService.now());
    }

    private boolean shouldSkipPersonalPatch(@NotNull RunningBuildEx runningBuildEx) {
        try {
            if (runningBuildEx == null) {
                BuildStarter.$$$reportNull$$$0(40);
            }
        }
        catch (RejectedExecutionException rejectedExecutionException) {
            throw BuildStarter.a(rejectedExecutionException);
        }
        return "true".equals(runningBuildEx.getBuildPromotion().getAttribute("teamcity.build.skipPersonalPatchApplication"));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void buildPatch(@NotNull RunningBuildEx runningBuildEx, @NotNull OutputStream outputStream, @NotNull FullCheckoutFlag fullCheckoutFlag, boolean bl, @Nullable Long l, @NotNull AgentCheckoutDirRevisionInfo agentCheckoutDirRevisionInfo) throws IOException {
        try {
            if (runningBuildEx == null) {
                BuildStarter.$$$reportNull$$$0(41);
            }
        }
        catch (IOException iOException) {
            throw BuildStarter.a(iOException);
        }
        try {
            if (outputStream == null) {
                BuildStarter.$$$reportNull$$$0(42);
            }
        }
        catch (IOException iOException) {
            throw BuildStarter.a(iOException);
        }
        try {
            if (fullCheckoutFlag == null) {
                BuildStarter.$$$reportNull$$$0(43);
            }
        }
        catch (IOException iOException) {
            throw BuildStarter.a(iOException);
        }
        try {
            if (agentCheckoutDirRevisionInfo == null) {
                BuildStarter.$$$reportNull$$$0(44);
            }
        }
        catch (IOException iOException) {
            throw BuildStarter.a(iOException);
        }
        try {
            if (this.myServer.isShuttingDown()) {
                return;
            }
            Callable<Object> callable = () -> this.lambda$buildPatch$12(runningBuildEx, bl, outputStream, l, fullCheckoutFlag, agentCheckoutDirRevisionInfo);
            try {
                this.myPatchLock.waitForTaskToComplete(this.myExecutor.submit(() -> {
                    String string;
                    try {
                        string = runningBuildEx.isPersonal() ? "personal " : "";
                    }
                    catch (Exception exception) {
                        throw BuildStarter.a(exception);
                    }
                    String string2 = "BuildPatch " + string + "for " + LogUtil.describe((Build)runningBuildEx);
                    return IOGuard.allowNetworkAndCommandLine(() -> NamedThreadFactory.executeWithNewThreadName((String)string2, (Callable)callable));
                }));
                if (this.myPatchLock.isExecutionFailed()) {
                    this.processUnhandledError((SRunningBuild)runningBuildEx, this.myPatchLock.getExecutionException().getCause());
                }
            }
            catch (IOException iOException) {
                throw BuildStarter.a(iOException);
            }
        }
        finally {
            outputStream.close();
        }
    }

    private void closePatchBuilder(@NotNull LowLevelPatchBuilder lowLevelPatchBuilder, @NotNull SRunningBuild sRunningBuild) throws IOException {
        block9: {
            block8: {
                try {
                    if (lowLevelPatchBuilder == null) {
                        BuildStarter.$$$reportNull$$$0(45);
                    }
                }
                catch (IOException iOException) {
                    throw BuildStarter.a(iOException);
                }
                try {
                    if (sRunningBuild == null) {
                        BuildStarter.$$$reportNull$$$0(46);
                    }
                }
                catch (IOException iOException) {
                    throw BuildStarter.a(iOException);
                }
                try {
                    if (!sRunningBuild.isInterrupted()) break block8;
                    lowLevelPatchBuilder.interrupt();
                    break block9;
                }
                catch (IOException iOException) {
                    throw BuildStarter.a(iOException);
                }
            }
            lowLevelPatchBuilder.exit("");
        }
        lowLevelPatchBuilder.close();
    }

    private void processUnhandledError(SRunningBuild sRunningBuild, Throwable throwable) {
        String string;
        String string2;
        String string3 = throwable.getLocalizedMessage();
        try {
            string2 = LogUtil.describe((Build)sRunningBuild);
            string = StringUtil.isNotEmpty((String)string3) ? string3 : throwable.toString();
        }
        catch (RejectedExecutionException rejectedExecutionException) {
            throw BuildStarter.a(rejectedExecutionException);
        }
        String string4 = "Failed to build patch for build " + string2 + ", due to error: " + string;
        Loggers.VCS.warn(string4, throwable);
        sRunningBuild.getBuildLog().messageAsync(string4, Status.WARNING, MessageAttrs.serverMessage());
    }

    public synchronized boolean stopBuild() {
        boolean bl;
        block5: {
            block4: {
                try {
                    try {
                        if (!this.myCheckingForChangesLocks.cancel() && !this.myPatchLock.cancel()) break block4;
                    }
                    catch (RejectedExecutionException rejectedExecutionException) {
                        throw BuildStarter.a(rejectedExecutionException);
                    }
                    bl = true;
                    break block5;
                }
                catch (RejectedExecutionException rejectedExecutionException) {
                    throw BuildStarter.a(rejectedExecutionException);
                }
            }
            bl = false;
        }
        return bl;
    }

    private static PatchMapLogger createLogger(SRunningBuild sRunningBuild) {
        return new /* Unavailable Anonymous Inner Class!! */;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Unable to fully structure code
     */
    private /* synthetic */ Object lambda$buildPatch$12(RunningBuildEx var1_1, boolean var2_2, OutputStream var3_3, Long var4_4, FullCheckoutFlag var5_5, AgentCheckoutDirRevisionInfo var6_6) throws Exception {
        block20: {
            block18: {
                block21: {
                    block19: {
                        try {
                            if (!var1_1.isPersonal() || !var2_2) break block18;
                        }
                        catch (Throwable v0) {
                            throw BuildStarter.a(v0);
                        }
                        var7_7 = this.myServer.getPersonalBuildManager();
                        if (!var1_1.getBuildPromotion().hasCustomPatch()) ** GOTO lbl14
                        try {
                            block22: {
                                if (!this.shouldSkipPersonalPatch(var1_1)) break block19;
                                break block22;
                                catch (Throwable v1) {
                                    throw BuildStarter.a(v1);
                                }
                            }
                            var7_7.buildEmptyPatch(var3_3, (SRunningBuild)var1_1);
                            break block20;
                        }
                        catch (Throwable v2) {
                            throw BuildStarter.a(v2);
                        }
                    }
                    try {
                        if (var4_4 != null) break block21;
                        var7_7.buildPatch(var3_3, (SRunningBuild)var1_1, var1_1.getBuildType(), BuildStarter.createLogger((SRunningBuild)var1_1));
                        break block20;
                    }
                    catch (Throwable v3) {
                        throw BuildStarter.a(v3);
                    }
                }
                var7_7.buildPersonalPatch(var3_3, (SRunningBuild)var1_1, var4_4.longValue(), BuildStarter.createLogger((SRunningBuild)var1_1));
                break block20;
            }
            var7_8 = (BuildTypeImpl)var1_1.getBuildType();
            var8_9 = new LowLevelPatchBuilderImpl(var3_3);
            try {
                if (var7_8 == null) break block20;
                try {
                    block23: {
                        if (var1_1.isInterrupted()) break block20;
                        break block23;
                        catch (Throwable v4) {
                            throw BuildStarter.a(v4);
                        }
                    }
                    var7_8.buildPatch(var1_1, var5_5, (LowLevelPatchBuilder)var8_9, this.myServer, this.myVcsStatusLogger, var6_6);
                }
                catch (Throwable v5) {
                    throw BuildStarter.a(v5);
                }
            }
            catch (Throwable var9_10) {
                if (!var1_1.isInterrupted()) {
                    var10_11 = "Unhandled error occurred, patch construction failed: " + var9_10.getMessage();
                    Loggers.VCS.error(var10_11, var9_10);
                    var8_9.fail(var10_11);
                }
            }
            finally {
                this.closePatchBuilder((LowLevelPatchBuilder)var8_9, (SRunningBuild)var1_1);
            }
        }
        return null;
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

    private static Throwable a(Throwable throwable) {
        return throwable;
    }
}
