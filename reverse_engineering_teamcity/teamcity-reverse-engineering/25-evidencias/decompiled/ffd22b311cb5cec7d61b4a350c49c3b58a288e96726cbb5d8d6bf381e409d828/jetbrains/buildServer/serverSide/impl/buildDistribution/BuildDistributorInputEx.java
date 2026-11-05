/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jetbrains.buildServer.serverSide.SBuildAgent
 *  jetbrains.buildServer.serverSide.buildDistribution.BuildDistributorInput
 *  jetbrains.buildServer.serverSide.buildDistribution.QueuedBuildInfo
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package jetbrains.buildServer.serverSide.impl.buildDistribution;

import java.util.Map;
import java.util.function.Supplier;
import jetbrains.buildServer.serverSide.SBuildAgent;
import jetbrains.buildServer.serverSide.buildDistribution.BuildDistributorInput;
import jetbrains.buildServer.serverSide.buildDistribution.QueuedBuildInfo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface BuildDistributorInputEx
extends BuildDistributorInput {
    public void setCustomData(@NotNull String var1, @Nullable Object var2);

    @Nullable
    public <T> T getCustomData(@NotNull String var1, @NotNull Class<T> var2);

    @NotNull
    public <T> T getCustomData(@NotNull String var1, @NotNull Supplier<T> var2);

    @NotNull
    public Map<QueuedBuildInfo, SBuildAgent> getDistributedBuilds();

    default public int getAverageDistributionTime() {
        return 0;
    }
}
