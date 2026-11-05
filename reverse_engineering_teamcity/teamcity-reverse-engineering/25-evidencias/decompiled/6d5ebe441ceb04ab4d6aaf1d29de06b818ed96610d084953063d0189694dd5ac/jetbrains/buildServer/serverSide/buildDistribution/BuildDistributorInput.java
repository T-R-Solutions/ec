/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jetbrains.buildServer.serverSide.SBuildAgent
 *  jetbrains.buildServer.serverSide.SBuildType
 *  jetbrains.buildServer.serverSide.buildDistribution.BuildsInfo
 *  jetbrains.buildServer.serverSide.buildDistribution.RunningBuildInfo
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package jetbrains.buildServer.serverSide.buildDistribution;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import jetbrains.buildServer.serverSide.SBuildAgent;
import jetbrains.buildServer.serverSide.SBuildType;
import jetbrains.buildServer.serverSide.buildDistribution.BuildsInfo;
import jetbrains.buildServer.serverSide.buildDistribution.RunningBuildInfo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface BuildDistributorInput
extends BuildsInfo {
    @NotNull
    public Collection<SBuildAgent> getAgents();

    @NotNull
    public Set<SBuildAgent> getBusyAgents();

    @Nullable
    public RunningBuildInfo findRunningBuildById(long var1);

    @NotNull
    public List<RunningBuildInfo> getBuildTypeRunningBuilds(@NotNull SBuildType var1);
}
