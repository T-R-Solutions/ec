/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jetbrains.buildServer.RunBuildException
 *  jetbrains.buildServer.agent.AgentRunningBuildEx
 *  jetbrains.buildServer.agent.BuildFinishedStatus
 *  jetbrains.buildServer.agent.BuildProgressLogger
 *  jetbrains.buildServer.agent.BuildRunnerSettings
 *  jetbrains.buildServer.agent.impl.buildStages.BuildStage
 *  jetbrains.buildServer.agent.impl.buildStages.BuildStageExecutionException
 *  jetbrains.buildServer.agent.impl.buildStages.StagesExecutor
 *  jetbrains.buildServer.agent.impl.buildStages.startStages.steps.RunAllBuildStepsStage
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package jetbrains.buildServer.agent.impl.buildStages;

import java.util.List;
import jetbrains.buildServer.RunBuildException;
import jetbrains.buildServer.agent.AgentRunningBuildEx;
import jetbrains.buildServer.agent.BuildFinishedStatus;
import jetbrains.buildServer.agent.BuildProgressLogger;
import jetbrains.buildServer.agent.BuildRunnerSettings;
import jetbrains.buildServer.agent.impl.buildStages.BuildStage;
import jetbrains.buildServer.agent.impl.buildStages.BuildStageExecutionException;
import jetbrains.buildServer.agent.impl.buildStages.StagesExecutor;
import jetbrains.buildServer.agent.impl.buildStages.startStages.steps.RunAllBuildStepsStage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BuildStagesExecutor
extends StagesExecutor<BuildStage> {
    private final BuildProgressLogger myLogger;
    private final AgentRunningBuildEx myBuild;

    public BuildStagesExecutor(BuildProgressLogger logger, AgentRunningBuildEx build) {
        this.myLogger = logger;
        this.myBuild = build;
    }

    protected boolean shouldRunAllStages() {
        boolean hasBootstrapStep = false;
        boolean hasAlwaysExecuteStep = false;
        for (BuildRunnerSettings runner : this.myBuild.getBuildRunners()) {
            if (!RunAllBuildStepsStage.isBootstrapStep((BuildRunnerSettings)runner) && BuildStagesExecutor.isAlwaysExecuteStep(runner)) {
                hasAlwaysExecuteStep = true;
            }
            if (!RunAllBuildStepsStage.isBootstrapStep((BuildRunnerSettings)runner)) continue;
            hasBootstrapStep = true;
        }
        return hasBootstrapStep && hasAlwaysExecuteStep;
    }

    private static boolean isAlwaysExecuteStep(BuildRunnerSettings runner) {
        return "execute_always".equals(runner.getRunnerParameters().get("teamcity.step.mode"));
    }

    protected BuildFinishedStatus callStage(@NotNull BuildStage stage) throws Throwable {
        if (stage == null) {
            BuildStagesExecutor.$$$reportNull$$$0(0);
        }
        try {
            BuildFinishedStatus buildFinishedStatus = stage.doBuildStage();
            return buildFinishedStatus;
        }
        catch (Throwable t) {
            throw new BuildStageExecutionException(stage, t);
        }
        finally {
            this.myLogger.progressFinished();
        }
    }

    @Nullable
    public final BuildFinishedStatus doStages(@NotNull List<BuildStage> stages) throws BuildStageExecutionException, RunBuildException {
        if (stages == null) {
            BuildStagesExecutor.$$$reportNull$$$0(1);
        }
        try {
            return super.doStages(stages);
        }
        catch (BuildStageExecutionException e) {
            throw e;
        }
        catch (Throwable t) {
            throw new RunBuildException("Failed to execute stages. " + t.getMessage(), t);
        }
    }

    private static /* synthetic */ void $$$reportNull$$$0(int n) {
        Object[] objectArray;
        Object[] objectArray2;
        Object[] objectArray3 = new Object[3];
        switch (n) {
            default: {
                objectArray2 = objectArray3;
                objectArray3[0] = "stage";
                break;
            }
            case 1: {
                objectArray2 = objectArray3;
                objectArray3[0] = "stages";
                break;
            }
        }
        objectArray2[1] = "jetbrains/buildServer/agent/impl/buildStages/BuildStagesExecutor";
        switch (n) {
            default: {
                objectArray = objectArray2;
                objectArray2[2] = "callStage";
                break;
            }
            case 1: {
                objectArray = objectArray2;
                objectArray2[2] = "doStages";
                break;
            }
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objectArray));
    }
}
