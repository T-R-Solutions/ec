/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jetbrains.buildServer.serverSide.ServerExtension
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.VirtualAgentsFilterContext
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.VirtualAgentsFilterResult
 *  org.jetbrains.annotations.NotNull
 */
package jetbrains.buildServer.serverSide.impl.buildDistribution;

import jetbrains.buildServer.serverSide.ServerExtension;
import jetbrains.buildServer.serverSide.impl.buildDistribution.VirtualAgentsFilterContext;
import jetbrains.buildServer.serverSide.impl.buildDistribution.VirtualAgentsFilterResult;
import org.jetbrains.annotations.NotNull;

public interface StartingBuildVirtualAgentsFilter
extends ServerExtension {
    @NotNull
    public VirtualAgentsFilterResult filterAgents(@NotNull VirtualAgentsFilterContext var1);
}
