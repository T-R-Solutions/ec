/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jetbrains.buildServer.serverSide.SBuildAgent
 *  jetbrains.buildServer.serverSide.buildDistribution.QueuedBuildInfo
 *  jetbrains.buildServer.serverSide.impl.executors.ExecutorDescription
 *  jetbrains.buildServer.serverSide.impl.virtualAgent.VirtualAgent
 *  org.jetbrains.annotations.NotNull
 */
package jetbrains.buildServer.serverSide.impl.buildDistribution.agentsComparators;

import java.util.Comparator;
import jetbrains.buildServer.serverSide.SBuildAgent;
import jetbrains.buildServer.serverSide.buildDistribution.QueuedBuildInfo;
import jetbrains.buildServer.serverSide.impl.executors.ExecutorDescription;
import jetbrains.buildServer.serverSide.impl.virtualAgent.VirtualAgent;
import org.jetbrains.annotations.NotNull;

public interface BuildAgentsComparatorProvider {
    @NotNull
    public Comparator<SBuildAgent> getConnectedAgentsComparatorForQueuedBuild(@NotNull QueuedBuildInfo var1);

    @NotNull
    public Comparator<VirtualAgent> getVirtualAgentsComparatorForQueuedBuild(@NotNull QueuedBuildInfo var1);

    @NotNull
    public Comparator<ExecutorDescription> getStartableExecutorsComparatorForQueuedBuild(@NotNull QueuedBuildInfo var1);
}
