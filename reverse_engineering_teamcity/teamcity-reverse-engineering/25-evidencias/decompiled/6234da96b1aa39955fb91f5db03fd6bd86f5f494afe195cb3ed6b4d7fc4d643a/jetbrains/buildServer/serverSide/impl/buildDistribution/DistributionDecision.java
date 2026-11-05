/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jetbrains.buildServer.serverSide.buildDistribution.WaitReason
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package jetbrains.buildServer.serverSide.impl.buildDistribution;

import jetbrains.buildServer.serverSide.buildDistribution.WaitReason;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DistributionDecision {
    private final boolean myBuildStarted;
    private final boolean myBuildDistributed;
    @Nullable
    private final WaitReason myWaitReason;
    @NotNull
    public static final DistributionDecision BUILD_STARTED_DECISION = new DistributionDecision(true, null);
    @NotNull
    public static final DistributionDecision BUILD_DISTRIBUTED_NOT_STARTED_DECISION = new DistributionDecision(false, true, null);
    @NotNull
    public static final DistributionDecision BUILD_SKIPPED_DECISION = new DistributionDecision(false, null);

    public DistributionDecision(boolean bl, @Nullable WaitReason waitReason) {
        this(bl, bl, waitReason);
    }

    public DistributionDecision(boolean bl, boolean bl2, @Nullable WaitReason waitReason) {
        this.myBuildStarted = bl;
        this.myBuildDistributed = bl2;
        this.myWaitReason = waitReason;
    }

    public boolean isBuildStarted() {
        return this.myBuildStarted;
    }

    public boolean isBuildDistributed() {
        return this.myBuildDistributed;
    }

    @Nullable
    public WaitReason getWaitReason() {
        return this.myWaitReason;
    }
}
