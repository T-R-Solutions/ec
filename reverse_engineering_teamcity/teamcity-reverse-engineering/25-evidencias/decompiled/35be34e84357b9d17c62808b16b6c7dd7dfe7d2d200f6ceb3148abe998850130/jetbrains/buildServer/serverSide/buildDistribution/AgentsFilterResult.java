/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jetbrains.buildServer.serverSide.SBuildAgent
 *  jetbrains.buildServer.serverSide.buildDistribution.WaitReason
 *  org.jetbrains.annotations.Nullable
 */
package jetbrains.buildServer.serverSide.buildDistribution;

import java.util.List;
import jetbrains.buildServer.serverSide.SBuildAgent;
import jetbrains.buildServer.serverSide.buildDistribution.WaitReason;
import org.jetbrains.annotations.Nullable;

public class AgentsFilterResult {
    private WaitReason myWaitReason;
    private List<SBuildAgent> myFilteredConnectedAgents;

    @Nullable
    public WaitReason getWaitReason() {
        return this.myWaitReason;
    }

    public void setWaitReason(@Nullable WaitReason waitReason) {
        this.myWaitReason = waitReason;
    }

    @Nullable
    public List<SBuildAgent> getFilteredConnectedAgents() {
        return this.myFilteredConnectedAgents;
    }

    public void setFilteredConnectedAgents(@Nullable List<SBuildAgent> filteredConnectedAgents) {
        this.myFilteredConnectedAgents = filteredConnectedAgents;
    }
}
