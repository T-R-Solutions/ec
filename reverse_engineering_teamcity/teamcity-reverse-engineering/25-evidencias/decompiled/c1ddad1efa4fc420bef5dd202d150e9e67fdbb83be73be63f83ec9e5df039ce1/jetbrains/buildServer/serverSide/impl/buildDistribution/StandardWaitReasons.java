/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jetbrains.buildServer.serverSide.buildDistribution.SimpleWaitReason
 *  jetbrains.buildServer.serverSide.buildDistribution.WaitReason
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.WaitReasonImpl
 */
package jetbrains.buildServer.serverSide.impl.buildDistribution;

import jetbrains.buildServer.serverSide.buildDistribution.SimpleWaitReason;
import jetbrains.buildServer.serverSide.buildDistribution.WaitReason;
import jetbrains.buildServer.serverSide.impl.buildDistribution.WaitReasonImpl;

public class StandardWaitReasons {
    public static final WaitReason MAX_RUNNING_BUILDS_NUMBER_REACHED = new SimpleWaitReason("The maximum number of running builds for this configuration is reached");
    public static final WaitReason MAX_RUNNING_BUILDS_NUMBER_PER_BRANCH_REACHED = new SimpleWaitReason("The maximum number of running builds in this branch is reached");
    public static final WaitReason WAITING_CHECKING_FOR_CHANGES = new SimpleWaitReason("Waiting to start checking for changes");
    public static final WaitReason WAITING_CHECKING_FOR_CHANGES_IN_DEPS = new SimpleWaitReason("A build that is dependent on this one is waiting to start collecting changes");
    public static final WaitReason DEPENDENCY_NOT_READY = new SimpleWaitReason("Build dependencies have not been built yet");
    public static final WaitReason WAITING_FOR_THE_FIRST_DEPENDENCY_TO_START = new SimpleWaitReason("Waiting for the start of the first build in the build chain");
    public static final WaitReason CHECKING_FOR_CHANGES_IN_PROGRESS = new SimpleWaitReason("Checking for changes is in progress");
    public static final WaitReason CHECKING_FOR_CHANGES_IN_PROGRESS_IN_DEPS = new SimpleWaitReason("A build that is dependent on this one is collecting changes");
    public static final WaitReason NO_COMPATIBLE_AGENTS_AVAILABLE = new WaitReasonImpl("There are no idle compatible agents which can run this build", true);
    public static final WaitReason NO_AGENTS_IN_THE_SELECTED_POOL = new WaitReasonImpl("There are no idle compatible agents in the selected agent pool which can run this build", true);
    public static final WaitReason NO_AGENTS_FOR_CLOUD_IMAGE = new WaitReasonImpl("There are no idle compatible agents with the selected cloud image which can run this build", true);
    public static final WaitReason TARGET_AGENT_UNAVAILABLE = new SimpleWaitReason("The agent selected for the build is either incompatible or unavailable");
    public static final WaitReason AGENTS_OUTDATED = new SimpleWaitReason("All compatible agents are outdated, waiting for upgrade");
    public static final WaitReason AGENTS_OUTDATED_UPGRADE_DISABLED = new SimpleWaitReason("All compatible agents are outdated and cannot be upgraded to a newer version");
    public static final WaitReason NO_ENABLED_COMPATIBLE_AGENTS = new WaitReasonImpl("There are no enabled compatible agents which can run this build", true);
    public static final WaitReason WAITING_FOR_CLOUD_AGENT = new SimpleWaitReason("Waiting for starting agent");
    public static final WaitReason WAITING_FOR_EXECUTOR = new SimpleWaitReason("Waiting for starting executor build");
    @Deprecated
    public static final WaitReason TARGET_AGENT_DISCONNECTED = new SimpleWaitReason("The selected agent is disconnected");
    @Deprecated
    public static final WaitReason TARGET_AGENT_INCOMPATIBLE = new SimpleWaitReason("The selected agent is not compatible");
    @Deprecated
    public static final WaitReason TARGET_AGENT_INACCESSIBLE = new SimpleWaitReason("The selected agent is from inaccessible pool of agents");
    @Deprecated
    public static final WaitReason TARGET_AGENT_OUTDATED = new SimpleWaitReason("The selected agent is outdated, waiting for upgrade");
    @Deprecated
    public static final WaitReason TARGET_AGENT_OUTDATED_UPGRADE_DISABLED = new SimpleWaitReason("The selected agent is outdated and cannot be upgraded to a newer version");
    @Deprecated
    public static final WaitReason NO_AGENTS_WITH_MATCHED_RESTRICTIONS = new WaitReasonImpl("There are no compatible agents which can run this build and match the specified restrictions", true);
}
