/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jetbrains.buildServer.serverSide.buildDistribution.QueuedBuildInfo
 *  jetbrains.buildServer.serverSide.buildDistribution.RunningBuildInfo
 *  org.jetbrains.annotations.NotNull
 */
package jetbrains.buildServer.serverSide.buildDistribution;

import java.util.Collection;
import java.util.List;
import jetbrains.buildServer.serverSide.buildDistribution.QueuedBuildInfo;
import jetbrains.buildServer.serverSide.buildDistribution.RunningBuildInfo;
import org.jetbrains.annotations.NotNull;

public interface BuildsInfo {
    @NotNull
    public List<QueuedBuildInfo> getQueue();

    @NotNull
    public Collection<RunningBuildInfo> getRunningBuilds();
}
