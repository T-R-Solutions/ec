/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jetbrains.buildServer.BuildAgent
 *  jetbrains.buildServer.serverSide.ServerExtension
 *  jetbrains.buildServer.serverSide.buildDistribution.BuildDistributorInput
 *  jetbrains.buildServer.serverSide.buildDistribution.QueuedBuildInfo
 *  jetbrains.buildServer.serverSide.buildDistribution.WaitReason
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package jetbrains.buildServer.serverSide.buildDistribution;

import java.util.Map;
import jetbrains.buildServer.BuildAgent;
import jetbrains.buildServer.serverSide.ServerExtension;
import jetbrains.buildServer.serverSide.buildDistribution.BuildDistributorInput;
import jetbrains.buildServer.serverSide.buildDistribution.QueuedBuildInfo;
import jetbrains.buildServer.serverSide.buildDistribution.WaitReason;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface StartBuildPrecondition
extends ServerExtension {
    @Nullable
    public WaitReason canStart(@NotNull QueuedBuildInfo var1, @NotNull Map<QueuedBuildInfo, BuildAgent> var2, @NotNull BuildDistributorInput var3, boolean var4);
}
