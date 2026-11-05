/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jetbrains.buildServer.AgentRestrictor
 *  jetbrains.buildServer.log.Loggable
 *  jetbrains.buildServer.serverSide.SBuildAgent
 *  jetbrains.buildServer.serverSide.buildDistribution.BuildConfigurationInfo
 *  jetbrains.buildServer.serverSide.buildDistribution.BuildPromotionInfo
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package jetbrains.buildServer.serverSide.buildDistribution;

import java.util.Collection;
import jetbrains.buildServer.AgentRestrictor;
import jetbrains.buildServer.log.Loggable;
import jetbrains.buildServer.serverSide.SBuildAgent;
import jetbrains.buildServer.serverSide.buildDistribution.BuildConfigurationInfo;
import jetbrains.buildServer.serverSide.buildDistribution.BuildPromotionInfo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface QueuedBuildInfo
extends Loggable {
    @NotNull
    public String getItemId();

    @NotNull
    public BuildConfigurationInfo getBuildConfiguration();

    @Deprecated
    @Nullable
    public Integer getBuildAgentId();

    @Nullable
    public AgentRestrictor getAgentRestrictor();

    @NotNull
    public BuildPromotionInfo getBuildPromotionInfo();

    @NotNull
    public Collection<SBuildAgent> getCompatibleAgents(@NotNull Collection<SBuildAgent> var1);
}
