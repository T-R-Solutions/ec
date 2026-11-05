/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jetbrains.buildServer.serverSide.SBuildAgent
 *  jetbrains.buildServer.serverSide.buildDistribution.BuildConfigurationInfo
 *  jetbrains.buildServer.serverSide.buildDistribution.BuildPromotionInfo
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package jetbrains.buildServer.serverSide.buildDistribution;

import jetbrains.buildServer.serverSide.SBuildAgent;
import jetbrains.buildServer.serverSide.buildDistribution.BuildConfigurationInfo;
import jetbrains.buildServer.serverSide.buildDistribution.BuildPromotionInfo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface RunningBuildInfo {
    @Nullable
    public BuildConfigurationInfo getBuildConfiguration();

    @NotNull
    public SBuildAgent getAgent();

    public long getEstimationForTimeLeft();

    @NotNull
    public BuildPromotionInfo getBuildPromotionInfo();

    public boolean occupiesAgent();
}
