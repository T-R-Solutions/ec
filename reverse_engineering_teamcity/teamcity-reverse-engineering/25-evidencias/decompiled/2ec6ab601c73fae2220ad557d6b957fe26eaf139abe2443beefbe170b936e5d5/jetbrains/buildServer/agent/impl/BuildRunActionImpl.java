/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.intellij.openapi.diagnostic.Logger
 *  jetbrains.buildServer.agent.AgentLifeCycleListener
 *  jetbrains.buildServer.agent.AgentRunningBuild
 *  jetbrains.buildServer.agent.AgentRunningBuildEx
 *  jetbrains.buildServer.agent.BuildFinishedStatus
 *  jetbrains.buildServer.agent.BuildInterruptReason
 *  jetbrains.buildServer.agent.impl.BuildExceptionsHandler
 *  jetbrains.buildServer.agent.impl.BuildFinishContext
 *  jetbrains.buildServer.agent.impl.BuildRunAction
 *  jetbrains.buildServer.agent.impl.buildStages.BuildFinishStage
 *  jetbrains.buildServer.agent.impl.buildStages.BuildStage
 *  jetbrains.buildServer.agent.impl.buildStages.BuildStageExecutionException
 *  jetbrains.buildServer.agent.impl.buildStages.BuildStagesExecutor
 *  jetbrains.buildServer.agent.impl.operationModes.AgentOperationModeHolder
 *  jetbrains.buildServer.util.EventDispatcher
 *  jetbrains.buildServer.util.ExceptionUtil
 *  jetbrains.buildServer.util.Interruptable
 *  org.jetbrains.annotations.NotNull
 */
package jetbrains.buildServer.agent.impl;

import com.intellij.openapi.diagnostic.Logger;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import jetbrains.buildServer.agent.AgentLifeCycleListener;
import jetbrains.buildServer.agent.AgentRunningBuild;
import jetbrains.buildServer.agent.AgentRunningBuildEx;
import jetbrains.buildServer.agent.BuildFinishedStatus;
import jetbrains.buildServer.agent.BuildInterruptReason;
import jetbrains.buildServer.agent.impl.BuildExceptionsHandler;
import jetbrains.buildServer.agent.impl.BuildFinishContext;
import jetbrains.buildServer.agent.impl.BuildRunAction;
import jetbrains.buildServer.agent.impl.buildStages.BuildFinishStage;
import jetbrains.buildServer.agent.impl.buildStages.BuildStage;
import jetbrains.buildServer.agent.impl.buildStages.BuildStageExecutionException;
import jetbrains.buildServer.agent.impl.buildStages.BuildStagesExecutor;
import jetbrains.buildServer.agent.impl.operationModes.AgentOperationModeHolder;
import jetbrains.buildServer.util.EventDispatcher;
import jetbrains.buildServer.util.ExceptionUtil;
import jetbrains.buildServer.util.Interruptable;
import org.jetbrains.annotations.NotNull;

public class BuildRunActionImpl
implements BuildRunAction {
    private static final Logger LOG = Logger.getInstance((String)BuildRunActionImpl.class.getName());
    private final AgentRunningBuildEx myBuild;
    private final EventDispatcher<AgentLifeCycleListener> myEvents;
    private final BuildStagesExecutor myRunBuildExecutor;
    private final List<BuildStage> myRunBuildStages;
    private final List<BuildFinishStage> myFinishBuildStages;
    private final AtomicBoolean myIsRunBuildCalled;
    private final BuildExceptionsHandler myExceptionsHandler;
    private final AgentOperationModeHolder myAgentOperationModeHolder;
    private volatile BuildFinishStage myCurrentFinishStage;
    private BuildFinishContext myFinishContext;

    public BuildRunActionImpl(@NotNull AgentRunningBuildEx build, @NotNull EventDispatcher<AgentLifeCycleListener> events, @NotNull List<BuildStage> runBuildStages, @NotNull List<BuildFinishStage> finishBuildStages, @NotNull BuildExceptionsHandler exceptionsHandler, @NotNull BuildStagesExecutor runBuildExecutor, @NotNull AgentOperationModeHolder agentOperationModeHolder) {
        if (build == null) {
            BuildRunActionImpl.$$$reportNull$$$0(0);
        }
        if (events == null) {
            BuildRunActionImpl.$$$reportNull$$$0(1);
        }
        if (runBuildStages == null) {
            BuildRunActionImpl.$$$reportNull$$$0(2);
        }
        if (finishBuildStages == null) {
            BuildRunActionImpl.$$$reportNull$$$0(3);
        }
        if (exceptionsHandler == null) {
            BuildRunActionImpl.$$$reportNull$$$0(4);
        }
        if (runBuildExecutor == null) {
            BuildRunActionImpl.$$$reportNull$$$0(5);
        }
        if (agentOperationModeHolder == null) {
            BuildRunActionImpl.$$$reportNull$$$0(6);
        }
        this.myIsRunBuildCalled = new AtomicBoolean();
        this.myCurrentFinishStage = null;
        this.myBuild = build;
        this.myEvents = events;
        this.myRunBuildStages = runBuildStages;
        this.myFinishBuildStages = finishBuildStages;
        this.myExceptionsHandler = exceptionsHandler;
        this.myRunBuildExecutor = runBuildExecutor;
        this.myAgentOperationModeHolder = agentOperationModeHolder;
    }

    @NotNull
    public AgentRunningBuildEx getRunningBuild() {
        AgentRunningBuildEx agentRunningBuildEx = this.myBuild;
        if (agentRunningBuildEx == null) {
            BuildRunActionImpl.$$$reportNull$$$0(7);
        }
        return agentRunningBuildEx;
    }

    public void runBuild() {
        this.checkRunCalled();
        BuildFinishedStatus finishedStatus = this.myAgentOperationModeHolder.getOperationMode().executeRunnerStages(this.myBuild, this.myRunBuildStages, this::doStages);
        BuildInterruptReason interruptReason = this.myBuild.getInterruptReason();
        if (finishedStatus == BuildFinishedStatus.INTERRUPTED && interruptReason == null) {
            LOG.info("Stage returned interrupted status, but interrupt reason was not specified. Interrupt reason is set to unknown.");
            interruptReason = BuildInterruptReason.UNKNOWN_REASON;
        }
        if (!this.myAgentOperationModeHolder.getOperationMode().onRunnersFinished((AgentRunningBuild)this.myBuild, finishedStatus)) {
            return;
        }
        this.myFinishContext = new BuildFinishContext(finishedStatus, interruptReason);
        try {
            this.doBuildFinishStages();
        }
        finally {
            this.myFinishContext = null;
        }
    }

    private void checkRunCalled() {
        if (!this.myIsRunBuildCalled.compareAndSet(false, true)) {
            throw new IllegalStateException("runBuild has already been called");
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NotNull
    private BuildFinishedStatus doStages(List<BuildStage> buildStages) {
        BuildFinishedStatus buildFinishedStatus;
        try {
            BuildFinishedStatus status = this.myRunBuildExecutor.doStages(buildStages);
            BuildFinishedStatus buildFinishedStatus2 = status == null ? BuildFinishedStatus.FINISHED_SUCCESS : status;
            buildFinishedStatus = buildFinishedStatus2;
        }
        catch (BuildStageExecutionException e) {
            BuildFinishedStatus buildFinishedStatus3 = this.myExceptionsHandler.handleBuildStageException(e);
            BuildFinishedStatus buildFinishedStatus4 = buildFinishedStatus3;
            if (buildFinishedStatus4 == null) {
                BuildRunActionImpl.$$$reportNull$$$0(9);
            }
            return buildFinishedStatus4;
        }
        catch (Throwable t) {
            BuildFinishedStatus buildFinishedStatus5 = this.myExceptionsHandler.handleThrowable(t);
            this.myBuild.setInAlwaysExecutingStage(false);
            BuildFinishedStatus buildFinishedStatus6 = buildFinishedStatus5;
            if (buildFinishedStatus6 == null) {
                BuildRunActionImpl.$$$reportNull$$$0(10);
            }
            return buildFinishedStatus6;
        }
        finally {
            this.myBuild.setInAlwaysExecutingStage(false);
        }
        if (buildFinishedStatus == null) {
            BuildRunActionImpl.$$$reportNull$$$0(8);
        }
        return buildFinishedStatus;
    }

    private void doBuildFinishStages() {
        for (BuildFinishStage stage : this.myFinishBuildStages) {
            if (!stage.isAvailable(this.myFinishContext)) continue;
            this.myCurrentFinishStage = stage;
            this.runCurrentFinishStage();
        }
    }

    private void runCurrentFinishStage() {
        try {
            LOG.info("Call finish stage " + this.finishStageName());
            this.myCurrentFinishStage.doFinishStage(this.myFinishContext);
        }
        catch (Throwable t) {
            String message = "Failed to perform build finish stage " + this.finishStageName() + ". " + t.getMessage();
            LOG.warn(message, t);
            this.myBuild.getBuildLogger().warning(message);
            this.myBuild.getBuildLogger().warning(t.toString());
        }
    }

    public void stopBuild(@NotNull BuildInterruptReason reason) {
        if (reason == null) {
            BuildRunActionImpl.$$$reportNull$$$0(11);
        }
        if (this.myBuild.isInAlwaysExecutingStage() && reason != BuildInterruptReason.EXECUTION_TIMEOUT) {
            String message = "Cannot stop build in an always executing stage. Interrupt reason: " + String.valueOf(reason) + ".";
            this.myBuild.getBuildLogger().warning(message);
            LOG.info(message);
            return;
        }
        ((AgentLifeCycleListener)this.myEvents.getMulticaster()).beforeBuildInterrupted((AgentRunningBuild)this.myBuild, reason);
        try {
            boolean hasCurrentRunStage = this.myRunBuildExecutor.getCurrentStage() != null;
            this.myRunBuildExecutor.interrupt();
            if (!hasCurrentRunStage) {
                this.interruptBuildFinishStage();
            }
        }
        catch (Exception e) {
            ExceptionUtil.rethrowAsRuntimeException((Throwable)e);
        }
    }

    private void interruptBuildFinishStage() {
        BuildFinishStage currentFinishStage;
        BuildFinishContext finishContext = this.myFinishContext;
        if (finishContext != null) {
            finishContext.setInterrupted();
        }
        if ((currentFinishStage = this.myCurrentFinishStage) instanceof Interruptable) {
            LOG.info("Interrupting build finish stage " + this.finishStageName());
            ((Interruptable)currentFinishStage).interrupt();
        } else if (currentFinishStage != null) {
            LOG.info("Cannot interrupt build finish stage " + this.finishStageName());
        }
    }

    private String finishStageName() {
        BuildFinishStage currentFinishStage = this.myCurrentFinishStage;
        return currentFinishStage == null ? "<no finish stage>" : currentFinishStage.getClass().getName();
    }

    private static /* synthetic */ void $$$reportNull$$$0(int n) {
        Object[] objectArray;
        Object[] objectArray2;
        Object[] objectArray3 = new Object[switch (n) {
            default -> 3;
            case 7, 8, 9, 10 -> 2;
        }];
        switch (n) {
            default: {
                objectArray2 = objectArray3;
                objectArray3[0] = "build";
                break;
            }
            case 1: {
                objectArray2 = objectArray3;
                objectArray3[0] = "events";
                break;
            }
            case 2: {
                objectArray2 = objectArray3;
                objectArray3[0] = "runBuildStages";
                break;
            }
            case 3: {
                objectArray2 = objectArray3;
                objectArray3[0] = "finishBuildStages";
                break;
            }
            case 4: {
                objectArray2 = objectArray3;
                objectArray3[0] = "exceptionsHandler";
                break;
            }
            case 5: {
                objectArray2 = objectArray3;
                objectArray3[0] = "runBuildExecutor";
                break;
            }
            case 6: {
                objectArray2 = objectArray3;
                objectArray3[0] = "agentOperationModeHolder";
                break;
            }
            case 7: 
            case 8: 
            case 9: 
            case 10: {
                objectArray2 = objectArray3;
                objectArray3[0] = "jetbrains/buildServer/agent/impl/BuildRunActionImpl";
                break;
            }
            case 11: {
                objectArray2 = objectArray3;
                objectArray3[0] = "reason";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray2;
                objectArray2[1] = "jetbrains/buildServer/agent/impl/BuildRunActionImpl";
                break;
            }
            case 7: {
                objectArray = objectArray2;
                objectArray2[1] = "getRunningBuild";
                break;
            }
            case 8: 
            case 9: 
            case 10: {
                objectArray = objectArray2;
                objectArray2[1] = "doStages";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray;
                objectArray[2] = "<init>";
                break;
            }
            case 7: 
            case 8: 
            case 9: 
            case 10: {
                break;
            }
            case 11: {
                objectArray = objectArray;
                objectArray[2] = "stopBuild";
                break;
            }
        }
        String string = String.format(v0, objectArray);
        throw switch (n) {
            default -> new IllegalArgumentException(string);
            case 7, 8, 9, 10 -> new IllegalStateException(string);
        };
    }
}
