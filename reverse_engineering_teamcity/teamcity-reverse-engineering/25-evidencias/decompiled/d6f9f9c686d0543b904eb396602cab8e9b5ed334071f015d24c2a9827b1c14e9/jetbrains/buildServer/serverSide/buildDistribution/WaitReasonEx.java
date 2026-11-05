/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jetbrains.buildServer.serverSide.buildDistribution.WaitReason
 */
package jetbrains.buildServer.serverSide.buildDistribution;

import jetbrains.buildServer.serverSide.buildDistribution.WaitReason;

public interface WaitReasonEx
extends WaitReason {
    public boolean notEnoughAgents();
}
