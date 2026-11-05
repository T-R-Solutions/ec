/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.intellij.openapi.util.Pair
 *  jetbrains.buildServer.serverSide.SBuildAgent
 *  jetbrains.buildServer.serverSide.buildDistribution.BuildDistributorInput
 *  jetbrains.buildServer.serverSide.buildDistribution.QueuedBuildInfo
 *  jetbrains.buildServer.serverSide.buildDistribution.WaitReason
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.CompatibleAgentsResult
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package jetbrains.buildServer.serverSide.impl.buildDistribution;

import com.intellij.openapi.util.Pair;
import java.util.List;
import java.util.Map;
import java.util.Set;
import jetbrains.buildServer.serverSide.SBuildAgent;
import jetbrains.buildServer.serverSide.buildDistribution.BuildDistributorInput;
import jetbrains.buildServer.serverSide.buildDistribution.QueuedBuildInfo;
import jetbrains.buildServer.serverSide.buildDistribution.WaitReason;
import jetbrains.buildServer.serverSide.impl.buildDistribution.CompatibleAgentsResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Distributor<T> {
    @NotNull
    public CompatibleAgentsResult<T> getCompatibleCandidates(@NotNull QueuedBuildInfo var1, @NotNull Set<T> var2);

    @Nullable
    public WaitReason filterAvailableAgents(@NotNull BuildDistributorInput var1, @NotNull QueuedBuildInfo var2, @NotNull List<T> var3, @NotNull Map<QueuedBuildInfo, SBuildAgent> var4, @NotNull Map<String, Object> var5);

    public void sortAvailableAgents(@NotNull List<T> var1, @NotNull QueuedBuildInfo var2);

    public boolean canProcessQueuedBuild(@NotNull QueuedBuildInfo var1, @NotNull Map<QueuedBuildInfo, WaitReason> var2);

    @NotNull
    public Pair<T, WaitReason> chooseAgentForBuild(@NotNull QueuedBuildInfo var1, @NotNull CompatibleAgentsResult<T> var2);

    @NotNull
    public String getName();
}
