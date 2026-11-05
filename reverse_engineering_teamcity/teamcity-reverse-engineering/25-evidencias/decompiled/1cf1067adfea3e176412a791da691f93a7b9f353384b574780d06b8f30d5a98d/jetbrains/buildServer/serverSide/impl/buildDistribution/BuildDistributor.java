/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jetbrains.buildServer.serverSide.SBuildAgent
 *  jetbrains.buildServer.serverSide.ServerExtension
 *  jetbrains.buildServer.serverSide.buildDistribution.QueuedBuildInfo
 *  jetbrains.buildServer.serverSide.buildDistribution.WaitReason
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.AgentlessBuildStarter
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.BuildDistributor$QueuedBuildOptimizer
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.BuildDistributor$QueuedBuildTerminator
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.BuildDistributorInputEx
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.BuildStarter
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.CompositeBuildStarter
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.DistributionResult
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.SetWaitReasonCallback
 *  jetbrains.buildServer.serverSide.impl.executors.ExecutorDescription
 *  jetbrains.buildServer.serverSide.impl.virtualAgent.VirtualAgent
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package jetbrains.buildServer.serverSide.impl.buildDistribution;

import java.util.Map;
import jetbrains.buildServer.serverSide.SBuildAgent;
import jetbrains.buildServer.serverSide.ServerExtension;
import jetbrains.buildServer.serverSide.buildDistribution.QueuedBuildInfo;
import jetbrains.buildServer.serverSide.buildDistribution.WaitReason;
import jetbrains.buildServer.serverSide.impl.buildDistribution.AgentlessBuildStarter;
import jetbrains.buildServer.serverSide.impl.buildDistribution.BuildDistributor;
import jetbrains.buildServer.serverSide.impl.buildDistribution.BuildDistributorInputEx;
import jetbrains.buildServer.serverSide.impl.buildDistribution.BuildStarter;
import jetbrains.buildServer.serverSide.impl.buildDistribution.CompositeBuildStarter;
import jetbrains.buildServer.serverSide.impl.buildDistribution.DistributionResult;
import jetbrains.buildServer.serverSide.impl.buildDistribution.SetWaitReasonCallback;
import jetbrains.buildServer.serverSide.impl.executors.ExecutorDescription;
import jetbrains.buildServer.serverSide.impl.virtualAgent.VirtualAgent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface BuildDistributor
extends ServerExtension {
    @NotNull
    public DistributionResult<SBuildAgent> assignBuildsToAgents(@NotNull BuildDistributorInputEx var1, @Nullable Map<QueuedBuildInfo, WaitReason> var2, @NotNull BuildStarter<SBuildAgent> var3, @Nullable BuildStarter<VirtualAgent> var4, @Nullable BuildStarter<ExecutorDescription> var5, @NotNull SetWaitReasonCallback var6, @NotNull CompositeBuildStarter var7, @NotNull AgentlessBuildStarter var8, @NotNull QueuedBuildOptimizer var9, @NotNull QueuedBuildTerminator var10, boolean var11);
}
