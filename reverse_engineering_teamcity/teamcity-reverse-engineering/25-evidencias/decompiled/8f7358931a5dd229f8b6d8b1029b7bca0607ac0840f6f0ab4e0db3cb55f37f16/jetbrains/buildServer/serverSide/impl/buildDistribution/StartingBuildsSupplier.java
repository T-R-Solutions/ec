/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jetbrains.buildServer.serverSide.SBuildAgent
 *  jetbrains.buildServer.serverSide.buildDistribution.QueuedBuildInfo
 *  org.jetbrains.annotations.NotNull
 */
package jetbrains.buildServer.serverSide.impl.buildDistribution;

import java.util.Map;
import jetbrains.buildServer.serverSide.SBuildAgent;
import jetbrains.buildServer.serverSide.buildDistribution.QueuedBuildInfo;
import org.jetbrains.annotations.NotNull;

public interface StartingBuildsSupplier {
    @NotNull
    public Map<QueuedBuildInfo, SBuildAgent> getStartingBuilds();

    @NotNull
    public Map<QueuedBuildInfo, SBuildAgent> getStartingBuilds(@NotNull String var1);
}
