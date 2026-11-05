/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jetbrains.buildServer.serverSide.buildDistribution.AgentsFilterContext
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package jetbrains.buildServer.serverSide.buildDistribution;

import java.util.HashMap;
import java.util.Map;
import jetbrains.buildServer.serverSide.buildDistribution.AgentsFilterContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class DefaultAgentsFilterContext
implements AgentsFilterContext {
    private final Map<String, Object> customData;

    public DefaultAgentsFilterContext() {
        this.customData = new HashMap<String, Object>();
    }

    public DefaultAgentsFilterContext(@NotNull Map<String, Object> customData) {
        if (customData == null) {
            DefaultAgentsFilterContext.$$$reportNull$$$0(0);
        }
        this.customData = customData;
    }

    @Nullable
    public final Object getCustomData(@NotNull String key) {
        if (key == null) {
            DefaultAgentsFilterContext.$$$reportNull$$$0(1);
        }
        return this.customData.get(key);
    }

    public final void setCustomData(@NotNull String key, @Nullable Object data) {
        if (key == null) {
            DefaultAgentsFilterContext.$$$reportNull$$$0(2);
        }
        this.customData.put(key, data);
    }

    private static /* synthetic */ void $$$reportNull$$$0(int n) {
        Object[] objectArray;
        Object[] objectArray2;
        Object[] objectArray3 = new Object[3];
        switch (n) {
            default: {
                objectArray2 = objectArray3;
                objectArray3[0] = "customData";
                break;
            }
            case 1: 
            case 2: {
                objectArray2 = objectArray3;
                objectArray3[0] = "key";
                break;
            }
        }
        objectArray2[1] = "jetbrains/buildServer/serverSide/buildDistribution/DefaultAgentsFilterContext";
        switch (n) {
            default: {
                objectArray = objectArray2;
                objectArray2[2] = "<init>";
                break;
            }
            case 1: {
                objectArray = objectArray2;
                objectArray2[2] = "getCustomData";
                break;
            }
            case 2: {
                objectArray = objectArray2;
                objectArray2[2] = "setCustomData";
                break;
            }
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objectArray));
    }
}
