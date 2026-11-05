/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jetbrains.buildServer.serverSide.buildDistribution.QueuedBuildInfo
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.DistributionDecision
 *  org.jetbrains.annotations.NotNull
 */
package jetbrains.buildServer.serverSide.impl.buildDistribution;

import jetbrains.buildServer.serverSide.buildDistribution.QueuedBuildInfo;
import jetbrains.buildServer.serverSide.impl.buildDistribution.DistributionDecision;
import org.jetbrains.annotations.NotNull;

public interface BuildStarter<T> {
    public static final BuildStarter NO_OP = (queuedBuildInfo, object) -> DistributionDecision.BUILD_STARTED_DECISION;

    public static <T> BuildStarter<T> noOp() {
        return NO_OP;
    }

    @NotNull
    public DistributionDecision distributeBuild(@NotNull QueuedBuildInfo var1, @NotNull T var2);
}
