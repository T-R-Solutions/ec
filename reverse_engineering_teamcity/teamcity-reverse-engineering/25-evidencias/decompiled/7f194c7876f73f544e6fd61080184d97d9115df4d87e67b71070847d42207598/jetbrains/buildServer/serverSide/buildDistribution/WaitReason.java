/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package jetbrains.buildServer.serverSide.buildDistribution;

import org.jetbrains.annotations.NotNull;

public interface WaitReason {
    @NotNull
    public String getDescription();
}
