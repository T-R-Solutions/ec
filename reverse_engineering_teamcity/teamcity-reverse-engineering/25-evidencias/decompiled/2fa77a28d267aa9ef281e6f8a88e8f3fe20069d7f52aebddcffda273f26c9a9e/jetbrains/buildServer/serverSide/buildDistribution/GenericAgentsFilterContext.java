/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jetbrains.buildServer.serverSide.SBuildAgent
 *  jetbrains.buildServer.serverSide.buildDistribution.BuildDistributorInput
 *  jetbrains.buildServer.serverSide.buildDistribution.GenericAgentsFilterContext$Requestor
 *  jetbrains.buildServer.serverSide.buildDistribution.QueuedBuildInfo
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package jetbrains.buildServer.serverSide.buildDistribution;

import java.util.Collection;
import java.util.Map;
import jetbrains.buildServer.serverSide.SBuildAgent;
import jetbrains.buildServer.serverSide.buildDistribution.BuildDistributorInput;
import jetbrains.buildServer.serverSide.buildDistribution.GenericAgentsFilterContext;
import jetbrains.buildServer.serverSide.buildDistribution.QueuedBuildInfo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface GenericAgentsFilterContext<T> {
    @NotNull
    public QueuedBuildInfo getStartingBuild();

    @NotNull
    public Collection<T> getAgentsForStartingBuild();

    @NotNull
    public Map<QueuedBuildInfo, SBuildAgent> getDistributedBuilds();

    @NotNull
    public BuildDistributorInput getDistributorInput();

    public boolean isEmulationMode();

    @Nullable
    public Object getCustomData(@NotNull String var1);

    public void setCustomData(@NotNull String var1, @Nullable Object var2);

    @NotNull
    default public Requestor getRequestor() {
        Requestor requestor = this.isEmulationMode() ? Requestor.QUEUE_ESTIMATOR : Requestor.QUEUE_PROCESSOR;
        if (requestor == null) {
            GenericAgentsFilterContext.$$$reportNull$$$0(0);
        }
        return requestor;
    }

    private static /* synthetic */ void $$$reportNull$$$0(int n) {
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "jetbrains/buildServer/serverSide/buildDistribution/GenericAgentsFilterContext", "getRequestor"));
    }
}
