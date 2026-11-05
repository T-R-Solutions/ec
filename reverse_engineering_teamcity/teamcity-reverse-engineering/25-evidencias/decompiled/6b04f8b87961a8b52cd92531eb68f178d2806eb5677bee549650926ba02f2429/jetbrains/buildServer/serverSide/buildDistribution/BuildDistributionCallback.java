/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jetbrains.buildServer.TeamCityExtension
 *  jetbrains.buildServer.serverSide.buildDistribution.QueuedBuildInfo
 *  jetbrains.buildServer.serverSide.buildDistribution.WaitReason
 *  org.jetbrains.annotations.NotNull
 */
package jetbrains.buildServer.serverSide.buildDistribution;

import jetbrains.buildServer.TeamCityExtension;
import jetbrains.buildServer.serverSide.buildDistribution.QueuedBuildInfo;
import jetbrains.buildServer.serverSide.buildDistribution.WaitReason;
import org.jetbrains.annotations.NotNull;

public interface BuildDistributionCallback
extends TeamCityExtension {
    public void distributionStarted();

    public void distributionFinished();

    public void waitReasonSet(@NotNull QueuedBuildInfo var1, @NotNull WaitReason var2);
}
