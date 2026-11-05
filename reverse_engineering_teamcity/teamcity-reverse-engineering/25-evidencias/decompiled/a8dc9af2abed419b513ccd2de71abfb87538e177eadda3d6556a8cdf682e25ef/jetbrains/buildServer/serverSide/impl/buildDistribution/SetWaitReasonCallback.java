/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jetbrains.buildServer.serverSide.buildDistribution.QueuedBuildInfo
 *  jetbrains.buildServer.serverSide.buildDistribution.WaitReason
 *  org.jetbrains.annotations.NotNull
 */
package jetbrains.buildServer.serverSide.impl.buildDistribution;

import jetbrains.buildServer.serverSide.buildDistribution.QueuedBuildInfo;
import jetbrains.buildServer.serverSide.buildDistribution.WaitReason;
import org.jetbrains.annotations.NotNull;

public interface SetWaitReasonCallback {
    public static final SetWaitReasonCallback NO_OP = (queuedBuildInfo, waitReason) -> {};

    public void waitReasonSet(@NotNull QueuedBuildInfo var1, @NotNull WaitReason var2);
}
