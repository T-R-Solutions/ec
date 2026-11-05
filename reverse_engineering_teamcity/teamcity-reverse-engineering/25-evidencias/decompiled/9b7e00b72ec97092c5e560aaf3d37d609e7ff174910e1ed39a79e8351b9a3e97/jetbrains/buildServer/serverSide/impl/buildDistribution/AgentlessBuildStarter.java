/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jetbrains.buildServer.serverSide.buildDistribution.QueuedBuildInfo
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.DistributionDecision
 *  org.jetbrains.annotations.NotNull
 */
package jetbrains.buildServer.serverSide.impl.buildDistribution;

import jetbrains.buildServer.serverSide.buildDistribution.QueuedBuildInfo;
import jetbrains.buildServer.serverSide.impl.buildDistribution.DistributionDecision;
import org.jetbrains.annotations.NotNull;

public interface AgentlessBuildStarter {
    public static final AgentlessBuildStarter NO_OP = (queuedBuildInfo, string) -> DistributionDecision.BUILD_STARTED_DECISION;

    public DistributionDecision startBuild(@NotNull QueuedBuildInfo var1, @NotNull String var2);
}
