/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jetbrains.buildServer.agent.AgentRunningBuild
 *  jetbrains.buildServer.agent.AgentRunningBuildEx
 *  jetbrains.buildServer.agent.BuildAgent
 *  jetbrains.buildServer.agent.impl.BuildFinishContext
 *  jetbrains.buildServer.agent.impl.artifacts.ArtifactsWatcherEx
 *  jetbrains.buildServer.agent.impl.buildStages.BuildFinishStage
 *  jetbrains.buildServer.util.Interruptable
 *  org.jetbrains.annotations.NotNull
 */
package jetbrains.buildServer.agent.impl.buildStages.finishStages;

import java.util.Date;
import jetbrains.buildServer.agent.AgentRunningBuild;
import jetbrains.buildServer.agent.AgentRunningBuildEx;
import jetbrains.buildServer.agent.BuildAgent;
import jetbrains.buildServer.agent.impl.BuildFinishContext;
import jetbrains.buildServer.agent.impl.artifacts.ArtifactsWatcherEx;
import jetbrains.buildServer.agent.impl.buildStages.BuildFinishStage;
import jetbrains.buildServer.util.Interruptable;
import org.jetbrains.annotations.NotNull;

public class FinishBuildOnAgentFStage
implements BuildFinishStage,
Interruptable {
    private final BuildAgent myAgent;
    private final AgentRunningBuild myBuild;
    private final ArtifactsWatcherEx myArtifactsWatcher;

    public FinishBuildOnAgentFStage(@NotNull BuildAgent agent, @NotNull AgentRunningBuild build, @NotNull ArtifactsWatcherEx artifactsWatcher) {
        if (agent == null) {
            FinishBuildOnAgentFStage.$$$reportNull$$$0(0);
        }
        if (build == null) {
            FinishBuildOnAgentFStage.$$$reportNull$$$0(1);
        }
        if (artifactsWatcher == null) {
            FinishBuildOnAgentFStage.$$$reportNull$$$0(2);
        }
        this.myAgent = agent;
        this.myBuild = build;
        this.myArtifactsWatcher = artifactsWatcher;
    }

    public boolean isAvailable(@NotNull BuildFinishContext status) {
        if (status == null) {
            FinishBuildOnAgentFStage.$$$reportNull$$$0(3);
        }
        return true;
    }

    public void doFinishStage(@NotNull BuildFinishContext status) {
        if (status == null) {
            FinishBuildOnAgentFStage.$$$reportNull$$$0(4);
        }
        if (status.getFinishedStatus().isFinished()) {
            this.myArtifactsWatcher.waitForPublishingFinish();
        }
        String buildId = String.valueOf(this.myBuild.getBuildId());
        if (this.myBuild.getInterruptReason() == null && ((AgentRunningBuildEx)this.myBuild).isDetachedFromAgent()) {
            this.myAgent.buildDetachedFromAgent(buildId, ((AgentRunningBuildEx)this.myBuild).getCurrentRunnerContext().getId());
        } else {
            this.myAgent.buildFinished(buildId, new Date(), status.isCanceledOrFailedForServer());
        }
    }

    public void interrupt() {
        this.myArtifactsWatcher.interruptPublishing();
    }

    private static /* synthetic */ void $$$reportNull$$$0(int n) {
        Object[] objectArray;
        Object[] objectArray2;
        Object[] objectArray3 = new Object[3];
        switch (n) {
            default: {
                objectArray2 = objectArray3;
                objectArray3[0] = "agent";
                break;
            }
            case 1: {
                objectArray2 = objectArray3;
                objectArray3[0] = "build";
                break;
            }
            case 2: {
                objectArray2 = objectArray3;
                objectArray3[0] = "artifactsWatcher";
                break;
            }
            case 3: 
            case 4: {
                objectArray2 = objectArray3;
                objectArray3[0] = "status";
                break;
            }
        }
        objectArray2[1] = "jetbrains/buildServer/agent/impl/buildStages/finishStages/FinishBuildOnAgentFStage";
        switch (n) {
            default: {
                objectArray = objectArray2;
                objectArray2[2] = "<init>";
                break;
            }
            case 3: {
                objectArray = objectArray2;
                objectArray2[2] = "isAvailable";
                break;
            }
            case 4: {
                objectArray = objectArray2;
                objectArray2[2] = "doFinishStage";
                break;
            }
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objectArray));
    }
}
