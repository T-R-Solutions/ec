/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jetbrains.buildServer.serverSide.buildDistribution.WaitReason
 *  jetbrains.buildServer.serverSide.impl.virtualAgent.VirtualAgent
 *  org.jetbrains.annotations.Nullable
 */
package jetbrains.buildServer.serverSide.impl.buildDistribution;

import java.util.List;
import jetbrains.buildServer.serverSide.buildDistribution.WaitReason;
import jetbrains.buildServer.serverSide.impl.virtualAgent.VirtualAgent;
import org.jetbrains.annotations.Nullable;

public class VirtualAgentsFilterResult {
    private WaitReason myWaitReason;
    private List<VirtualAgent> myFilteredVirtualAgents;

    @Nullable
    public WaitReason getWaitReason() {
        return this.myWaitReason;
    }

    public void setWaitReason(@Nullable WaitReason waitReason) {
        this.myWaitReason = waitReason;
    }

    @Nullable
    public List<VirtualAgent> getFilteredVirtualAgents() {
        return this.myFilteredVirtualAgents;
    }

    public void setFilteredVirtualAgents(@Nullable List<VirtualAgent> list) {
        this.myFilteredVirtualAgents = list;
    }
}
