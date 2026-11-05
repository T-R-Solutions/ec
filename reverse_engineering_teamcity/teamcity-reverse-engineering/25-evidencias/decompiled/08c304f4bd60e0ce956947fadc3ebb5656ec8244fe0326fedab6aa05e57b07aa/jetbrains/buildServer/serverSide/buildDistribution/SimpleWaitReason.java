/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jetbrains.buildServer.serverSide.buildDistribution.WaitReason
 *  org.jetbrains.annotations.NotNull
 */
package jetbrains.buildServer.serverSide.buildDistribution;

import jetbrains.buildServer.serverSide.buildDistribution.WaitReason;
import org.jetbrains.annotations.NotNull;

public class SimpleWaitReason
implements WaitReason {
    private final String myDescription;

    public SimpleWaitReason(@NotNull String description) {
        if (description == null) {
            SimpleWaitReason.$$$reportNull$$$0(0);
        }
        this.myDescription = description;
    }

    @NotNull
    public String getDescription() {
        String string = this.myDescription;
        if (string == null) {
            SimpleWaitReason.$$$reportNull$$$0(1);
        }
        return string;
    }

    public String toString() {
        return "SimpleWaitReason{myDescription='" + this.myDescription + "'}";
    }

    private static /* synthetic */ void $$$reportNull$$$0(int n) {
        Object[] objectArray;
        Object[] objectArray2;
        Object[] objectArray3 = new Object[switch (n) {
            default -> 3;
            case 1 -> 2;
        }];
        switch (n) {
            default: {
                objectArray2 = objectArray3;
                objectArray3[0] = "description";
                break;
            }
            case 1: {
                objectArray2 = objectArray3;
                objectArray3[0] = "jetbrains/buildServer/serverSide/buildDistribution/SimpleWaitReason";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray2;
                objectArray2[1] = "jetbrains/buildServer/serverSide/buildDistribution/SimpleWaitReason";
                break;
            }
            case 1: {
                objectArray = objectArray2;
                objectArray2[1] = "getDescription";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray;
                objectArray[2] = "<init>";
                break;
            }
            case 1: {
                break;
            }
        }
        String string = String.format(v0, objectArray);
        throw switch (n) {
            default -> new IllegalArgumentException(string);
            case 1 -> new IllegalStateException(string);
        };
    }
}
