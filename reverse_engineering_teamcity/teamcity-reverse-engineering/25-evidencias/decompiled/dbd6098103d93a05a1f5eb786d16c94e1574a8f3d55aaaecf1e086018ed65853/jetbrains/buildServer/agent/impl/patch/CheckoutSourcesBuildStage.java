/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.intellij.openapi.diagnostic.Logger
 *  jetbrains.buildServer.RunBuildException
 *  jetbrains.buildServer.agent.AgentCheckoutMode
 *  jetbrains.buildServer.agent.AgentLifeCycleListener
 *  jetbrains.buildServer.agent.AgentRunningBuild
 *  jetbrains.buildServer.agent.AgentRunningBuildEx
 *  jetbrains.buildServer.agent.BuildAgent
 *  jetbrains.buildServer.agent.BuildFinishedStatus
 *  jetbrains.buildServer.agent.BuildProgressLogger
 *  jetbrains.buildServer.agent.impl.buildStages.startStages.RecoverableBuildStage
 *  jetbrains.buildServer.agent.impl.buildStages.startStages.RecoverableStageFailure
 *  jetbrains.buildServer.agent.impl.patch.AbstractSourcesUpdater
 *  jetbrains.buildServer.agent.impl.patch.AgentBuildCheckoutTypeResolver
 *  jetbrains.buildServer.agent.impl.patch.CheckoutSourcesBuildStage$CheckoutAction
 *  jetbrains.buildServer.agent.impl.patch.UpdateSourcesFromAgent
 *  jetbrains.buildServer.agent.impl.patch.UpdateSourcesFromServer
 *  jetbrains.buildServer.agentServer.AgentBuild$CheckoutType
 *  jetbrains.buildServer.util.EventDispatcher
 *  jetbrains.buildServer.vcs.AutoCheckoutModeDecision
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package jetbrains.buildServer.agent.impl.patch;

import com.intellij.openapi.diagnostic.Logger;
import jetbrains.buildServer.RunBuildException;
import jetbrains.buildServer.agent.AgentCheckoutMode;
import jetbrains.buildServer.agent.AgentLifeCycleListener;
import jetbrains.buildServer.agent.AgentRunningBuild;
import jetbrains.buildServer.agent.AgentRunningBuildEx;
import jetbrains.buildServer.agent.BuildAgent;
import jetbrains.buildServer.agent.BuildFinishedStatus;
import jetbrains.buildServer.agent.BuildProgressLogger;
import jetbrains.buildServer.agent.impl.buildStages.startStages.RecoverableBuildStage;
import jetbrains.buildServer.agent.impl.buildStages.startStages.RecoverableStageFailure;
import jetbrains.buildServer.agent.impl.patch.AbstractSourcesUpdater;
import jetbrains.buildServer.agent.impl.patch.AgentBuildCheckoutTypeResolver;
import jetbrains.buildServer.agent.impl.patch.CheckoutSourcesBuildStage;
import jetbrains.buildServer.agent.impl.patch.UpdateSourcesFromAgent;
import jetbrains.buildServer.agent.impl.patch.UpdateSourcesFromServer;
import jetbrains.buildServer.agentServer.AgentBuild;
import jetbrains.buildServer.util.EventDispatcher;
import jetbrains.buildServer.vcs.AutoCheckoutModeDecision;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CheckoutSourcesBuildStage
extends RecoverableBuildStage {
    private static final Logger LOG = Logger.getInstance((String)AbstractSourcesUpdater.class.getName());
    private static final String AGENT_SIDE_CHECKOUT = "agent side checkout";
    private static final String SERVER_SIDE_CHECKOUT = "server side checkout";
    private static final String AUTO_CHECKOUT = "auto checkout";
    @NotNull
    private final AgentRunningBuildEx myBuild;
    @NotNull
    private final EventDispatcher<AgentLifeCycleListener> myDispatcher;
    @NotNull
    private final UpdateSourcesFromAgent myUpdateSourcesFromAgent;
    @NotNull
    private final UpdateSourcesFromServer myUpdateSourcesFromServer;
    @NotNull
    private final AgentBuildCheckoutTypeResolver myCheckoutTypeResolver;
    @Nullable
    private volatile CheckoutAction myRunningCheckout;

    public CheckoutSourcesBuildStage(@NotNull BuildAgent agent, @NotNull BuildProgressLogger logger, @NotNull AgentRunningBuildEx build, @NotNull EventDispatcher<AgentLifeCycleListener> agentDispatcher, @NotNull UpdateSourcesFromAgent updateSourcesFromAgent, @NotNull UpdateSourcesFromServer updateSourcesFromServer, @NotNull AgentBuildCheckoutTypeResolver checkoutTypeResolver) {
        if (agent == null) {
            CheckoutSourcesBuildStage.$$$reportNull$$$0(0);
        }
        if (logger == null) {
            CheckoutSourcesBuildStage.$$$reportNull$$$0(1);
        }
        if (build == null) {
            CheckoutSourcesBuildStage.$$$reportNull$$$0(2);
        }
        if (agentDispatcher == null) {
            CheckoutSourcesBuildStage.$$$reportNull$$$0(3);
        }
        if (updateSourcesFromAgent == null) {
            CheckoutSourcesBuildStage.$$$reportNull$$$0(4);
        }
        if (updateSourcesFromServer == null) {
            CheckoutSourcesBuildStage.$$$reportNull$$$0(5);
        }
        if (checkoutTypeResolver == null) {
            CheckoutSourcesBuildStage.$$$reportNull$$$0(6);
        }
        super(agent, 10, 3, true, logger);
        this.myBuild = build;
        this.myDispatcher = agentDispatcher;
        this.myUpdateSourcesFromAgent = updateSourcesFromAgent;
        this.myUpdateSourcesFromServer = updateSourcesFromServer;
        this.myCheckoutTypeResolver = checkoutTypeResolver;
    }

    @Nullable
    protected BuildFinishedStatus doRecoverableStage() throws RunBuildException, RecoverableStageFailure {
        if (AgentBuild.CheckoutType.ON_AGENT == this.myBuild.getCheckoutType()) {
            return this.doCheckout(new CheckoutAction(this, (AbstractSourcesUpdater)this.myUpdateSourcesFromAgent, AGENT_SIDE_CHECKOUT));
        }
        if (AgentBuild.CheckoutType.ON_SERVER == this.myBuild.getCheckoutType()) {
            return this.doCheckout(new CheckoutAction(this, (AbstractSourcesUpdater)this.myUpdateSourcesFromServer, SERVER_SIDE_CHECKOUT));
        }
        if (AgentBuild.CheckoutType.AUTO == this.myBuild.getCheckoutType()) {
            AutoCheckoutModeDecision decision = this.myCheckoutTypeResolver.getAutoCheckoutDecision();
            assert (decision != null);
            UpdateSourcesFromAgent updater = decision.getResolvedCheckoutMode() == AgentCheckoutMode.ON_AGENT ? this.myUpdateSourcesFromAgent : this.myUpdateSourcesFromServer;
            String descriptionSuffix = decision.getResolvedCheckoutMode() == AgentCheckoutMode.ON_AGENT ? " (on agent)" : " (on server)";
            return this.doCheckout((CheckoutAction)new /* Unavailable Anonymous Inner Class!! */);
        }
        return null;
    }

    @Nullable
    private BuildFinishedStatus doCheckout(CheckoutAction checkoutAction) throws RecoverableStageFailure {
        BuildFinishedStatus status;
        this.getLogger().activityStarted("Updating sources", checkoutAction.getDescription(), "checkout");
        LOG.info("Updating sources: " + checkoutAction.getDescription());
        try {
            this.myRunningCheckout = checkoutAction;
            status = checkoutAction.checkout();
        }
        finally {
            this.getLogger().activityFinished("Updating sources", "checkout");
        }
        if (status == null) {
            ((AgentLifeCycleListener)this.myDispatcher.getMulticaster()).sourcesUpdated((AgentRunningBuild)this.myBuild);
        }
        return status;
    }

    public void interrupt() {
        super.interrupt();
        CheckoutAction runningCheckout = this.myRunningCheckout;
        if (runningCheckout != null) {
            runningCheckout.interrupt();
        }
    }

    public boolean isAvailable() {
        return AgentCheckoutMode.DO_NOT_CHECKOUT != this.myBuild.getEffectiveCheckoutMode() && !this.myBuild.getVcsRootEntries().isEmpty();
    }

    private static /* synthetic */ void $$$reportNull$$$0(int n) {
        Object[] objectArray;
        Object[] objectArray2 = new Object[3];
        switch (n) {
            default: {
                objectArray = objectArray2;
                objectArray2[0] = "agent";
                break;
            }
            case 1: {
                objectArray = objectArray2;
                objectArray2[0] = "logger";
                break;
            }
            case 2: {
                objectArray = objectArray2;
                objectArray2[0] = "build";
                break;
            }
            case 3: {
                objectArray = objectArray2;
                objectArray2[0] = "agentDispatcher";
                break;
            }
            case 4: {
                objectArray = objectArray2;
                objectArray2[0] = "updateSourcesFromAgent";
                break;
            }
            case 5: {
                objectArray = objectArray2;
                objectArray2[0] = "updateSourcesFromServer";
                break;
            }
            case 6: {
                objectArray = objectArray2;
                objectArray2[0] = "checkoutTypeResolver";
                break;
            }
        }
        objectArray[1] = "jetbrains/buildServer/agent/impl/patch/CheckoutSourcesBuildStage";
        objectArray[2] = "<init>";
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objectArray));
    }
}
