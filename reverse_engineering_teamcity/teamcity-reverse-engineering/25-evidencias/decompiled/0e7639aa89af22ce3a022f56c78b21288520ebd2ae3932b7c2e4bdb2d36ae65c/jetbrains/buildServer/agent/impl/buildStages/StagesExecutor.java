/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.intellij.openapi.diagnostic.Logger
 *  jetbrains.buildServer.agent.BuildFinishedStatus
 *  jetbrains.buildServer.agent.impl.buildStages.InterruptableStage
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package jetbrains.buildServer.agent.impl.buildStages;

import com.intellij.openapi.diagnostic.Logger;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import jetbrains.buildServer.agent.BuildFinishedStatus;
import jetbrains.buildServer.agent.impl.buildStages.InterruptableStage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class StagesExecutor<T extends InterruptableStage> {
    private final Logger LOG = Logger.getInstance((String)this.getClass().getName());
    private final AtomicReference<T> myCurrentStartBuildStage = new AtomicReference();
    private final AtomicBoolean myIsInterrupted = new AtomicBoolean();

    protected abstract BuildFinishedStatus callStage(@NotNull T var1) throws Throwable;

    protected boolean shouldRunAllStages() {
        return false;
    }

    @Nullable
    protected final BuildFinishedStatus doStages(@NotNull Collection<? extends T> stages) throws Throwable {
        if (stages == null) {
            StagesExecutor.$$$reportNull$$$0(0);
        }
        BuildFinishedStatus result = null;
        for (InterruptableStage stage : stages) {
            if (this.shouldExit()) break;
            BuildFinishedStatus status = this.callRunStage(stage);
            if (this.shouldExit()) break;
            if (status == null || status == BuildFinishedStatus.FINISHED_SUCCESS) continue;
            result = status;
            if (this.shouldRunAllStages()) continue;
            break;
        }
        return this.isInterrupted() ? BuildFinishedStatus.INTERRUPTED : result;
    }

    private boolean shouldExit() {
        if (this.isInterrupted()) {
            if (this.shouldRunAllStages()) {
                this.LOG.info("Build was interrupted, but all stages will be executed as there are 'always execute' steps");
                return false;
            }
            return true;
        }
        return false;
    }

    @Nullable
    public T getCurrentStage() {
        return (T)((InterruptableStage)this.myCurrentStartBuildStage.get());
    }

    public final void interrupt() {
        this.myIsInterrupted.set(true);
        T stage = this.getCurrentStage();
        if (stage != null) {
            this.LOG.info("Interrupt command sent to " + String.valueOf(stage.getClass()));
            stage.interrupt();
        } else {
            this.LOG.info("There is no currently running stage to interrupt");
        }
    }

    public final boolean isInterrupted() {
        return this.myIsInterrupted.get();
    }

    private BuildFinishedStatus callRunStage(@NotNull T stage) throws Throwable {
        if (stage == null) {
            StagesExecutor.$$$reportNull$$$0(1);
        }
        if (!stage.isAvailable()) {
            return null;
        }
        this.myCurrentStartBuildStage.set(stage);
        this.LOG.info("Call stage " + stage.getClass().getName());
        try {
            BuildFinishedStatus buildFinishedStatus = this.callStage(stage);
            return buildFinishedStatus;
        }
        finally {
            this.myCurrentStartBuildStage.set(null);
        }
    }

    private static /* synthetic */ void $$$reportNull$$$0(int n) {
        Object[] objectArray;
        Object[] objectArray2;
        Object[] objectArray3 = new Object[3];
        switch (n) {
            default: {
                objectArray2 = objectArray3;
                objectArray3[0] = "stages";
                break;
            }
            case 1: {
                objectArray2 = objectArray3;
                objectArray3[0] = "stage";
                break;
            }
        }
        objectArray2[1] = "jetbrains/buildServer/agent/impl/buildStages/StagesExecutor";
        switch (n) {
            default: {
                objectArray = objectArray2;
                objectArray2[2] = "doStages";
                break;
            }
            case 1: {
                objectArray = objectArray2;
                objectArray2[2] = "callRunStage";
                break;
            }
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objectArray));
    }
}
