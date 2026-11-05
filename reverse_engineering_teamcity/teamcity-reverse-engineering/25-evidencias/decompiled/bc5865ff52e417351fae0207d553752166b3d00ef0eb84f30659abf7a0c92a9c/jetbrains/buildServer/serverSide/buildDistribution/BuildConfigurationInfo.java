/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jetbrains.buildServer.BuildAgent
 *  jetbrains.buildServer.serverSide.buildDistribution.BuildPromotionInfo
 *  org.jetbrains.annotations.NotNull
 */
package jetbrains.buildServer.serverSide.buildDistribution;

import jetbrains.buildServer.BuildAgent;
import jetbrains.buildServer.serverSide.buildDistribution.BuildPromotionInfo;
import org.jetbrains.annotations.NotNull;

public interface BuildConfigurationInfo {
    @NotNull
    public String getId();

    public int getMaximumNumberOfBuilds();

    @Deprecated(since="2026.2")
    public long getBuildDuration(@NotNull BuildAgent var1);

    default public long getBuildDuration(@NotNull BuildPromotionInfo promotion, @NotNull BuildAgent agent) {
        if (promotion == null) {
            BuildConfigurationInfo.$$$reportNull$$$0(0);
        }
        if (agent == null) {
            BuildConfigurationInfo.$$$reportNull$$$0(1);
        }
        return this.getBuildDuration(agent);
    }

    private static /* synthetic */ void $$$reportNull$$$0(int n) {
        Object[] objectArray;
        Object[] objectArray2 = new Object[3];
        switch (n) {
            default: {
                objectArray = objectArray2;
                objectArray2[0] = "promotion";
                break;
            }
            case 1: {
                objectArray = objectArray2;
                objectArray2[0] = "agent";
                break;
            }
        }
        objectArray[1] = "jetbrains/buildServer/serverSide/buildDistribution/BuildConfigurationInfo";
        objectArray[2] = "getBuildDuration";
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objectArray));
    }
}
