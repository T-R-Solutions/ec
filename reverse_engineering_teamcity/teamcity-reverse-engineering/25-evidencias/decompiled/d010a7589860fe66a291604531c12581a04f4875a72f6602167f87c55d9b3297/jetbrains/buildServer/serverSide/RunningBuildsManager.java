/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jetbrains.buildServer.serverSide.BuildDataFilter
 *  jetbrains.buildServer.serverSide.SBuildAgent
 *  jetbrains.buildServer.serverSide.SBuildType
 *  jetbrains.buildServer.serverSide.SRunningBuild
 *  jetbrains.buildServer.users.User
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package jetbrains.buildServer.serverSide;

import java.util.List;
import java.util.Map;
import jetbrains.buildServer.serverSide.BuildDataFilter;
import jetbrains.buildServer.serverSide.SBuildAgent;
import jetbrains.buildServer.serverSide.SBuildType;
import jetbrains.buildServer.serverSide.SRunningBuild;
import jetbrains.buildServer.users.User;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface RunningBuildsManager {
    @Nullable
    public SRunningBuild findRunningBuildById(long var1);

    @Nullable
    public SRunningBuild getRunningBuildOnAgent(SBuildAgent var1);

    @NotNull
    public List<SRunningBuild> getRunningBuilds(@Nullable User var1, @Nullable BuildDataFilter var2);

    @NotNull
    public List<SRunningBuild> getRunningBuilds(@Nullable User var1, @NotNull SBuildType var2);

    @NotNull
    public List<SRunningBuild> getRunningBuilds();

    public int getNumberOfRunningBuilds();

    public Map<SBuildType, List<SRunningBuild>> getRunningStatus(@Nullable User var1, @Nullable BuildDataFilter var2);
}
