/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jetbrains.buildServer.serverSide.ServerExtension
 *  jetbrains.buildServer.serverSide.buildDistribution.AgentsFilterContext
 *  jetbrains.buildServer.serverSide.buildDistribution.AgentsFilterResult
 *  org.jetbrains.annotations.NotNull
 */
package jetbrains.buildServer.serverSide.buildDistribution;

import jetbrains.buildServer.serverSide.ServerExtension;
import jetbrains.buildServer.serverSide.buildDistribution.AgentsFilterContext;
import jetbrains.buildServer.serverSide.buildDistribution.AgentsFilterResult;
import org.jetbrains.annotations.NotNull;

public interface StartingBuildAgentsFilter
extends ServerExtension {
    @NotNull
    public AgentsFilterResult filterAgents(@NotNull AgentsFilterContext var1);
}
