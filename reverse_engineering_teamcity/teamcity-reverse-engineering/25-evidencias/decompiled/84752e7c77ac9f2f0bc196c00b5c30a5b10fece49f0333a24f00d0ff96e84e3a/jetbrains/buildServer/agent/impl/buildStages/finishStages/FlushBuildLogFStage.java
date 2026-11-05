/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jetbrains.buildServer.agent.BuildProgressLogger
 *  jetbrains.buildServer.agent.impl.BuildFinishContext
 *  jetbrains.buildServer.agent.impl.buildStages.BuildFinishStage
 *  org.jetbrains.annotations.NotNull
 */
package jetbrains.buildServer.agent.impl.buildStages.finishStages;

import jetbrains.buildServer.agent.BuildProgressLogger;
import jetbrains.buildServer.agent.impl.BuildFinishContext;
import jetbrains.buildServer.agent.impl.buildStages.BuildFinishStage;
import org.jetbrains.annotations.NotNull;

public class FlushBuildLogFStage
implements BuildFinishStage {
    private final BuildProgressLogger myLogger;

    public FlushBuildLogFStage(@NotNull BuildProgressLogger logger) {
        if (logger == null) {
            FlushBuildLogFStage.$$$reportNull$$$0(0);
        }
        this.myLogger = logger;
    }

    public boolean isAvailable(@NotNull BuildFinishContext status) {
        if (status == null) {
            FlushBuildLogFStage.$$$reportNull$$$0(1);
        }
        return true;
    }

    public void doFinishStage(@NotNull BuildFinishContext status) {
        if (status == null) {
            FlushBuildLogFStage.$$$reportNull$$$0(2);
        }
        this.myLogger.flush();
    }

    private static /* synthetic */ void $$$reportNull$$$0(int n) {
        Object[] objectArray;
        Object[] objectArray2;
        Object[] objectArray3 = new Object[3];
        switch (n) {
            default: {
                objectArray2 = objectArray3;
                objectArray3[0] = "logger";
                break;
            }
            case 1: 
            case 2: {
                objectArray2 = objectArray3;
                objectArray3[0] = "status";
                break;
            }
        }
        objectArray2[1] = "jetbrains/buildServer/agent/impl/buildStages/finishStages/FlushBuildLogFStage";
        switch (n) {
            default: {
                objectArray = objectArray2;
                objectArray2[2] = "<init>";
                break;
            }
            case 1: {
                objectArray = objectArray2;
                objectArray2[2] = "isAvailable";
                break;
            }
            case 2: {
                objectArray = objectArray2;
                objectArray2[2] = "doFinishStage";
                break;
            }
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objectArray));
    }
}
