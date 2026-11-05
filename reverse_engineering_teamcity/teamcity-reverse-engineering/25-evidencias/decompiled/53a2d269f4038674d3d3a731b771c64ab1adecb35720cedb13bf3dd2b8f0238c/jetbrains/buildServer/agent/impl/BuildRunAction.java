/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jetbrains.buildServer.agent.AgentRunningBuildEx
 *  jetbrains.buildServer.agent.BuildInterruptReason
 *  org.jetbrains.annotations.NotNull
 */
package jetbrains.buildServer.agent.impl;

import jetbrains.buildServer.agent.AgentRunningBuildEx;
import jetbrains.buildServer.agent.BuildInterruptReason;
import org.jetbrains.annotations.NotNull;

public interface BuildRunAction {
    @NotNull
    public AgentRunningBuildEx getRunningBuild();

    public void runBuild();

    public void stopBuild(@NotNull BuildInterruptReason var1);
}
