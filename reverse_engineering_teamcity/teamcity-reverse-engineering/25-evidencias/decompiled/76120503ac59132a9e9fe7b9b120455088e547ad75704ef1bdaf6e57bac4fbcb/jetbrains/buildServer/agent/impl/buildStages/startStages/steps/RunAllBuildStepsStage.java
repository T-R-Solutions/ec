/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jetbrains.buildServer.RunBuildException
 *  jetbrains.buildServer.agent.AgentRunningBuildEx
 *  jetbrains.buildServer.agent.BuildFinishedStatus
 *  jetbrains.buildServer.agent.BuildProgressLogger
 *  jetbrains.buildServer.agent.BuildRunnerContext
 *  jetbrains.buildServer.agent.BuildRunnerSettings
 *  jetbrains.buildServer.agent.impl.buildStages.BuildStage
 *  jetbrains.buildServer.agent.impl.buildStages.RunnerFinishInfo
 *  jetbrains.buildServer.agent.impl.buildStages.RunnerStageInfo
 *  jetbrains.buildServer.agent.impl.buildStages.startStages.steps.RunAllBuildStepsStage$2
 *  jetbrains.buildServer.agent.impl.buildStages.startStages.steps.RunnerContextExecutor
 *  jetbrains.buildServer.agent.impl.buildStages.startStages.steps.RunnerFinishedInfoImpl
 *  jetbrains.buildServer.agent.impl.buildStages.startStages.steps.RunnerStageLogger
 *  jetbrains.buildServer.agent.impl.buildStages.startStages.steps.StepExecutionOraculum
 *  jetbrains.buildServer.agent.impl.buildStages.startStages.steps.StepsPhase
 *  jetbrains.buildServer.agent.impl.operationModes.ExecutorRunnerMode
 *  jetbrains.buildServer.agent.impl.operationModes.executor.BuildState
 *  jetbrains.buildServer.agent.impl.operationModes.executor.BuildStateCacheManager
 *  jetbrains.buildServer.agent.impl.runContext.RunnerContextFactory
 *  jetbrains.buildServer.agent.impl.runContext.RunningRunnerContext
 *  org.jetbrains.annotations.NotNull
 */
package jetbrains.buildServer.agent.impl.buildStages.startStages.steps;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;
import jetbrains.buildServer.RunBuildException;
import jetbrains.buildServer.agent.AgentRunningBuildEx;
import jetbrains.buildServer.agent.BuildFinishedStatus;
import jetbrains.buildServer.agent.BuildProgressLogger;
import jetbrains.buildServer.agent.BuildRunnerContext;
import jetbrains.buildServer.agent.BuildRunnerSettings;
import jetbrains.buildServer.agent.impl.buildStages.BuildStage;
import jetbrains.buildServer.agent.impl.buildStages.RunnerFinishInfo;
import jetbrains.buildServer.agent.impl.buildStages.RunnerStageInfo;
import jetbrains.buildServer.agent.impl.buildStages.startStages.steps.RunAllBuildStepsStage;
import jetbrains.buildServer.agent.impl.buildStages.startStages.steps.RunnerContextExecutor;
import jetbrains.buildServer.agent.impl.buildStages.startStages.steps.RunnerFinishedInfoImpl;
import jetbrains.buildServer.agent.impl.buildStages.startStages.steps.RunnerStageLogger;
import jetbrains.buildServer.agent.impl.buildStages.startStages.steps.StepExecutionOraculum;
import jetbrains.buildServer.agent.impl.buildStages.startStages.steps.StepsPhase;
import jetbrains.buildServer.agent.impl.operationModes.ExecutorRunnerMode;
import jetbrains.buildServer.agent.impl.operationModes.executor.BuildState;
import jetbrains.buildServer.agent.impl.operationModes.executor.BuildStateCacheManager;
import jetbrains.buildServer.agent.impl.runContext.RunnerContextFactory;
import jetbrains.buildServer.agent.impl.runContext.RunningRunnerContext;
import org.jetbrains.annotations.NotNull;

public class RunAllBuildStepsStage
implements BuildStage {
    private final AtomicBoolean myIsInterrupted;
    private final BuildStateCacheManager myBuildStateCacheManager;
    private final StepsPhase myPhase;
    private volatile RunnerContextExecutor myExecutor;
    private final RunnerContextFactory myRunnerContextFactory;
    private final AgentRunningBuildEx myBuild;
    private final StepExecutionOraculum myOraculum;
    private final BuildProgressLogger myLogger;

    public RunAllBuildStepsStage(@NotNull RunnerContextFactory runnerContextFactory, @NotNull AgentRunningBuildEx build, @NotNull StepExecutionOraculum oraculum, @NotNull BuildProgressLogger logger, @NotNull StepsPhase phase) {
        if (runnerContextFactory == null) {
            RunAllBuildStepsStage.$$$reportNull$$$0(0);
        }
        if (build == null) {
            RunAllBuildStepsStage.$$$reportNull$$$0(1);
        }
        if (oraculum == null) {
            RunAllBuildStepsStage.$$$reportNull$$$0(2);
        }
        if (logger == null) {
            RunAllBuildStepsStage.$$$reportNull$$$0(3);
        }
        if (phase == null) {
            RunAllBuildStepsStage.$$$reportNull$$$0(4);
        }
        this.myIsInterrupted = new AtomicBoolean();
        this.myExecutor = null;
        this.myRunnerContextFactory = runnerContextFactory;
        this.myBuild = build;
        this.myOraculum = oraculum;
        this.myLogger = logger;
        this.myBuildStateCacheManager = new BuildStateCacheManager(this.myBuild.getAgentConfiguration());
        this.myPhase = phase;
    }

    public static boolean isBootstrapStep(BuildRunnerSettings runner) {
        return "bootstrap".equals(runner.getRunnerParameters().get("teamcity.step.phase"));
    }

    public BuildFinishedStatus doBuildStage() throws RunBuildException {
        List<RunnerStageInfo> allSteps = this.createStepInfos(this.myBuild.getBuildRunners(), true);
        BuildFinishedStatus status = this.isInterrupted() ? BuildFinishedStatus.INTERRUPTED : this.executeBuildRunners(allSteps);
        this.myOraculum.setLastStageFinishStatus(status);
        return status;
    }

    @NotNull
    private List<RunnerStageInfo> createStepInfos(@NotNull List<BuildRunnerSettings> datas, boolean topLevel) {
        if (datas == null) {
            RunAllBuildStepsStage.$$$reportNull$$$0(5);
        }
        ArrayList<RunnerStageInfo> infos = new ArrayList<RunnerStageInfo>();
        for (BuildRunnerSettings runner : datas) {
            if (topLevel && this.myPhase == StepsPhase.BOOTSTRAP ^ RunAllBuildStepsStage.isBootstrapStep(runner)) continue;
            RunnerStageInfo info = new RunnerStageInfo(runner);
            if (runner.hasChildren()) {
                List<RunnerStageInfo> childrenInfo = this.createStepInfos(runner.getChildren(), false);
                for (RunnerStageInfo child : childrenInfo) {
                    child.setParent(info);
                }
                info.setChildren(childrenInfo);
            }
            infos.add(info);
        }
        RunnerStageInfo prev = null;
        for (RunnerStageInfo info : infos) {
            info.setPrev(prev);
            if (prev != null) {
                prev.setNext(info);
            }
            prev = info;
        }
        if (this.myBuild.getAgentConfiguration().getOperationMode() instanceof ExecutorRunnerMode) {
            this.updateStepsBasedOnCache(infos);
        }
        ArrayList<RunnerStageInfo> arrayList = infos;
        if (arrayList == null) {
            RunAllBuildStepsStage.$$$reportNull$$$0(6);
        }
        return arrayList;
    }

    private void updateStepsBasedOnCache(List<RunnerStageInfo> infos) {
        BuildState buildState = this.myBuildStateCacheManager.getStepsData();
        if (buildState == null) {
            return;
        }
        if (buildState.getRunnerToExecuteIndex() != -1 && buildState.getRunnerToExecuteIndex() < infos.size()) {
            RunnerFinishedInfoImpl runnerFinishedInfo = new RunnerFinishedInfoImpl();
            runnerFinishedInfo.setStatus(buildState.getBuildFinishedStatus());
            infos.get(buildState.getRunnerToExecuteIndex()).setFinishInfo((RunnerFinishInfo)runnerFinishedInfo);
        } else if (buildState.getBuildFinishedStatus().isFailed()) {
            this.myIsInterrupted.set(true);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NotNull
    private BuildFinishedStatus executeBuildRunners(@NotNull List<RunnerStageInfo> allSteps) throws RunBuildException {
        if (allSteps == null) {
            RunAllBuildStepsStage.$$$reportNull$$$0(7);
        }
        for (RunnerStageInfo info : allSteps) {
            RunnerStageLogger logger = new RunnerStageLogger(this.myLogger, info, this.myPhase);
            if (this.isInactive(info)) {
                logger.logInactiveBuildStep();
                continue;
            }
            logger.logOpenStepBlock();
            try {
                this.runStep(info, logger);
            }
            finally {
                logger.logCloseStepBlock();
            }
        }
        if (this.isInterrupted()) {
            BuildFinishedStatus buildFinishedStatus = BuildFinishedStatus.INTERRUPTED;
            if (buildFinishedStatus == null) {
                RunAllBuildStepsStage.$$$reportNull$$$0(8);
            }
            return buildFinishedStatus;
        }
        BuildFinishedStatus buildFinishedStatus = this.getRunnerFinishInfo(allSteps).getFinishedStatus();
        if (buildFinishedStatus == null) {
            RunAllBuildStepsStage.$$$reportNull$$$0(9);
        }
        return buildFinishedStatus;
    }

    private boolean isInactive(RunnerStageInfo info) {
        return this.getBuildRunnerSettingsAndChildren(this.myBuild.getRunnersToExecute().stream()).noneMatch(settings -> settings.getId().equals(info.getStepSettings().getId()));
    }

    @NotNull
    private Stream<BuildRunnerSettings> getBuildRunnerSettingsAndChildren(Stream<BuildRunnerSettings> runnerSettings) {
        Stream<BuildRunnerSettings> stream = runnerSettings.flatMap(settings -> Stream.concat(Stream.of(settings), this.getBuildRunnerSettingsAndChildren(settings.getChildren().stream())));
        if (stream == null) {
            RunAllBuildStepsStage.$$$reportNull$$$0(10);
        }
        return stream;
    }

    private void runStep(@NotNull RunnerStageInfo info, @NotNull RunnerStageLogger logger) throws RunBuildException {
        if (info == null) {
            RunAllBuildStepsStage.$$$reportNull$$$0(11);
        }
        if (logger == null) {
            RunAllBuildStepsStage.$$$reportNull$$$0(12);
        }
        if (!info.getStepSettings().isEnabled()) {
            logger.logStepDisabled();
            return;
        }
        switch (2.$SwitchMap$jetbrains$buildServer$agent$impl$buildStages$startStages$steps$StepExecutionOraculum$Outcome[this.myOraculum.decideForStep(logger, info).ordinal()]) {
            case 1: {
                this.interrupt();
                return;
            }
            case 2: {
                return;
            }
        }
        if (info.getStepSettings().hasChildren()) {
            Collection childStages = info.getChildStages();
            if (childStages.size() > 0) {
                this.executeBuildRunners(new LinkedList<RunnerStageInfo>(childStages));
                info.setFinishInfo(this.getRunnerFinishInfo(childStages));
            } else {
                logger.logEmptyGroupStep();
            }
            return;
        }
        this.runBuildStep(info, logger);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void runBuildStep(@NotNull RunnerStageInfo info, Object ... extras) throws RunBuildException {
        if (info == null) {
            RunAllBuildStepsStage.$$$reportNull$$$0(13);
        }
        if (extras == null) {
            RunAllBuildStepsStage.$$$reportNull$$$0(14);
        }
        this.myBuild.selectRunnerState(info.getStepSettings());
        RunningRunnerContext rc = this.myRunnerContextFactory.createRunnerContext((BuildRunnerContext)this.myBuild.getCurrentRunnerContext(), info, extras);
        try {
            if (this.isInterrupted() && info.isFirstEnabledStep()) {
                return;
            }
            this.myExecutor = rc.getExecutor();
            this.myExecutor.executeBuildStepStages();
        }
        finally {
            this.myExecutor = null;
            rc.dispose();
        }
    }

    @NotNull
    private RunnerFinishInfo getRunnerFinishInfo(@NotNull Collection<RunnerStageInfo> steps) {
        if (steps == null) {
            RunAllBuildStepsStage.$$$reportNull$$$0(15);
        }
        for (RunnerStageInfo step : steps) {
            BuildFinishedStatus status;
            RunnerFinishInfo res = step.getStepExecutionResult();
            if (res == null || (status = res.getFinishedStatus()) == BuildFinishedStatus.FINISHED_SUCCESS) continue;
            RunnerFinishInfo runnerFinishInfo = res;
            if (runnerFinishInfo == null) {
                RunAllBuildStepsStage.$$$reportNull$$$0(16);
            }
            return runnerFinishInfo;
        }
        return new /* Unavailable Anonymous Inner Class!! */;
    }

    private boolean isInterrupted() {
        return this.myIsInterrupted.get();
    }

    public boolean isAvailable() {
        return true;
    }

    public void interrupt() {
        this.myIsInterrupted.set(true);
        RunnerContextExecutor exec = this.myExecutor;
        if (exec != null) {
            exec.interrupt();
        }
    }

    private static /* synthetic */ void $$$reportNull$$$0(int n) {
        Object[] objectArray;
        Object[] objectArray2;
        Object[] objectArray3 = new Object[switch (n) {
            default -> 3;
            case 6, 8, 9, 10, 16 -> 2;
        }];
        switch (n) {
            default: {
                objectArray2 = objectArray3;
                objectArray3[0] = "runnerContextFactory";
                break;
            }
            case 1: {
                objectArray2 = objectArray3;
                objectArray3[0] = "build";
                break;
            }
            case 2: {
                objectArray2 = objectArray3;
                objectArray3[0] = "oraculum";
                break;
            }
            case 3: 
            case 12: {
                objectArray2 = objectArray3;
                objectArray3[0] = "logger";
                break;
            }
            case 4: {
                objectArray2 = objectArray3;
                objectArray3[0] = "phase";
                break;
            }
            case 5: {
                objectArray2 = objectArray3;
                objectArray3[0] = "datas";
                break;
            }
            case 6: 
            case 8: 
            case 9: 
            case 10: 
            case 16: {
                objectArray2 = objectArray3;
                objectArray3[0] = "jetbrains/buildServer/agent/impl/buildStages/startStages/steps/RunAllBuildStepsStage";
                break;
            }
            case 7: {
                objectArray2 = objectArray3;
                objectArray3[0] = "allSteps";
                break;
            }
            case 11: 
            case 13: {
                objectArray2 = objectArray3;
                objectArray3[0] = "info";
                break;
            }
            case 14: {
                objectArray2 = objectArray3;
                objectArray3[0] = "extras";
                break;
            }
            case 15: {
                objectArray2 = objectArray3;
                objectArray3[0] = "steps";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray2;
                objectArray2[1] = "jetbrains/buildServer/agent/impl/buildStages/startStages/steps/RunAllBuildStepsStage";
                break;
            }
            case 6: {
                objectArray = objectArray2;
                objectArray2[1] = "createStepInfos";
                break;
            }
            case 8: 
            case 9: {
                objectArray = objectArray2;
                objectArray2[1] = "executeBuildRunners";
                break;
            }
            case 10: {
                objectArray = objectArray2;
                objectArray2[1] = "getBuildRunnerSettingsAndChildren";
                break;
            }
            case 16: {
                objectArray = objectArray2;
                objectArray2[1] = "getRunnerFinishInfo";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray;
                objectArray[2] = "<init>";
                break;
            }
            case 5: {
                objectArray = objectArray;
                objectArray[2] = "createStepInfos";
                break;
            }
            case 6: 
            case 8: 
            case 9: 
            case 10: 
            case 16: {
                break;
            }
            case 7: {
                objectArray = objectArray;
                objectArray[2] = "executeBuildRunners";
                break;
            }
            case 11: 
            case 12: {
                objectArray = objectArray;
                objectArray[2] = "runStep";
                break;
            }
            case 13: 
            case 14: {
                objectArray = objectArray;
                objectArray[2] = "runBuildStep";
                break;
            }
            case 15: {
                objectArray = objectArray;
                objectArray[2] = "getRunnerFinishInfo";
                break;
            }
        }
        String string = String.format(v0, objectArray);
        throw switch (n) {
            default -> new IllegalArgumentException(string);
            case 6, 8, 9, 10, 16 -> new IllegalStateException(string);
        };
    }
}
