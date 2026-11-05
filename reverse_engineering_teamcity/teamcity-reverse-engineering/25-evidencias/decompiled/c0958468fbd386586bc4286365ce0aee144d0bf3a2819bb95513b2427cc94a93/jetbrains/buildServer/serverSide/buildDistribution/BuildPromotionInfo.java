/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jetbrains.buildServer.serverSide.SBuildAgent
 *  org.jetbrains.annotations.NotNull
 */
package jetbrains.buildServer.serverSide.buildDistribution;

import java.util.Collection;
import java.util.Map;
import jetbrains.buildServer.serverSide.SBuildAgent;
import org.jetbrains.annotations.NotNull;

public interface BuildPromotionInfo {
    public long getId();

    public Collection<BuildPromotionInfo> getDirectDependencies();

    @NotNull
    public Map<String, String> getParameters();

    @NotNull
    public Map<String, String> getBuildParameters();

    @NotNull
    public Collection<SBuildAgent> getCanRunOnAgents(@NotNull Collection<SBuildAgent> var1);

    public boolean isAgentLessBuild();

    public boolean isCompositeBuild();
}
