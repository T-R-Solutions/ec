/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.util.concurrent.Striped
 *  com.intellij.openapi.diagnostic.Logger
 *  jetbrains.buildServer.BuildAgent
 *  jetbrains.buildServer.buildFeatures.approvals.ApprovalConstants
 *  jetbrains.buildServer.notification.NotificationProcessor
 *  jetbrains.buildServer.problems.BuildProblemUtil
 *  jetbrains.buildServer.serverSide.BuildPromotion
 *  jetbrains.buildServer.serverSide.BuildPromotionEx
 *  jetbrains.buildServer.serverSide.BuildServerListener
 *  jetbrains.buildServer.serverSide.DependencyConsumer$Result
 *  jetbrains.buildServer.serverSide.ProjectManager
 *  jetbrains.buildServer.serverSide.QueuedBuildEx
 *  jetbrains.buildServer.serverSide.SProject
 *  jetbrains.buildServer.serverSide.SQueuedBuild
 *  jetbrains.buildServer.serverSide.buildDistribution.BuildDistributorInput
 *  jetbrains.buildServer.serverSide.buildDistribution.QueuedBuildInfo
 *  jetbrains.buildServer.serverSide.buildDistribution.SimpleWaitReason
 *  jetbrains.buildServer.serverSide.buildDistribution.StartBuildPrecondition
 *  jetbrains.buildServer.serverSide.buildDistribution.WaitReason
 *  jetbrains.buildServer.serverSide.executors.ExecutorServices
 *  jetbrains.buildServer.serverSide.impl.approval.ApprovableBuildManager
 *  jetbrains.buildServer.serverSide.impl.approval.ApprovalBuildFeatureConfiguration
 *  jetbrains.buildServer.serverSide.impl.approval.ApprovalResult
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.BuildDependenciesPrecondition
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.CheckingForChangesPrecondition
 *  jetbrains.buildServer.serverSide.impl.untrustedBuilds.ApprovableBuildChain
 *  jetbrains.buildServer.serverSide.impl.untrustedBuilds.ApprovableBuildChain$ApprovalBuildGroupImpl
 *  jetbrains.buildServer.serverSide.impl.untrustedBuilds.UntrustedBuildsManager
 *  jetbrains.buildServer.serverSide.impl.untrustedBuilds.UntrustedBuildsSettings
 *  jetbrains.buildServer.users.SUser
 *  jetbrains.buildServer.util.EventDispatcher
 *  jetbrains.buildServer.util.positioning.PositionAware
 *  jetbrains.buildServer.util.positioning.PositionConstraint
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package jetbrains.buildServer.serverSide.impl.buildDistribution;

import com.google.common.util.concurrent.Striped;
import com.intellij.openapi.diagnostic.Logger;
import java.util.Collections;
import java.util.EventListener;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import jetbrains.buildServer.BuildAgent;
import jetbrains.buildServer.buildFeatures.approvals.ApprovalConstants;
import jetbrains.buildServer.notification.NotificationProcessor;
import jetbrains.buildServer.problems.BuildProblemUtil;
import jetbrains.buildServer.serverSide.BuildPromotion;
import jetbrains.buildServer.serverSide.BuildPromotionEx;
import jetbrains.buildServer.serverSide.BuildServerListener;
import jetbrains.buildServer.serverSide.DependencyConsumer;
import jetbrains.buildServer.serverSide.ProjectManager;
import jetbrains.buildServer.serverSide.QueuedBuildEx;
import jetbrains.buildServer.serverSide.SProject;
import jetbrains.buildServer.serverSide.SQueuedBuild;
import jetbrains.buildServer.serverSide.buildDistribution.BuildDistributorInput;
import jetbrains.buildServer.serverSide.buildDistribution.QueuedBuildInfo;
import jetbrains.buildServer.serverSide.buildDistribution.SimpleWaitReason;
import jetbrains.buildServer.serverSide.buildDistribution.StartBuildPrecondition;
import jetbrains.buildServer.serverSide.buildDistribution.WaitReason;
import jetbrains.buildServer.serverSide.executors.ExecutorServices;
import jetbrains.buildServer.serverSide.impl.approval.ApprovableBuildManager;
import jetbrains.buildServer.serverSide.impl.approval.ApprovalBuildFeatureConfiguration;
import jetbrains.buildServer.serverSide.impl.approval.ApprovalResult;
import jetbrains.buildServer.serverSide.impl.buildDistribution.BuildDependenciesPrecondition;
import jetbrains.buildServer.serverSide.impl.buildDistribution.CheckingForChangesPrecondition;
import jetbrains.buildServer.serverSide.impl.untrustedBuilds.ApprovableBuildChain;
import jetbrains.buildServer.serverSide.impl.untrustedBuilds.UntrustedBuildsManager;
import jetbrains.buildServer.serverSide.impl.untrustedBuilds.UntrustedBuildsSettings;
import jetbrains.buildServer.users.SUser;
import jetbrains.buildServer.util.EventDispatcher;
import jetbrains.buildServer.util.positioning.PositionAware;
import jetbrains.buildServer.util.positioning.PositionConstraint;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Exception performing whole class analysis ignored.
 */
public class WaitApprovalBuildPrecondition
implements StartBuildPrecondition,
PositionAware {
    public static final String ORDER_ID = WaitApprovalBuildPrecondition.class.getSimpleName();
    private final NotificationProcessor myNotificationProcessor;
    private final ApprovableBuildManager myApprovableBuildManager;
    private final UntrustedBuildsManager myUntrustedBuildsManager;
    private final ProjectManager myProjectManager;
    private final Logger LOG;
    private final Map<Long, Boolean> myWaitingForChangesCollectionPromotions;
    private final Map<Long, Boolean> myProcessedGroupTops;
    private final Map<Long, Boolean> myNotificationNotSentPromotions;
    private final Striped<Lock> myLocks;
    private final ExecutorService myExecutorService;

    public WaitApprovalBuildPrecondition(@NotNull NotificationProcessor notificationProcessor, @NotNull EventDispatcher<BuildServerListener> eventDispatcher, @NotNull UntrustedBuildsManager untrustedBuildsManager, @NotNull ApprovableBuildManager approvableBuildManager, @NotNull ProjectManager projectManager, @NotNull ExecutorServices executorServices) {
        if (notificationProcessor == null) {
            WaitApprovalBuildPrecondition.$$$reportNull$$$0(0);
        }
        if (eventDispatcher == null) {
            WaitApprovalBuildPrecondition.$$$reportNull$$$0(1);
        }
        if (untrustedBuildsManager == null) {
            WaitApprovalBuildPrecondition.$$$reportNull$$$0(2);
        }
        if (approvableBuildManager == null) {
            WaitApprovalBuildPrecondition.$$$reportNull$$$0(3);
        }
        if (projectManager == null) {
            WaitApprovalBuildPrecondition.$$$reportNull$$$0(4);
        }
        if (executorServices == null) {
            WaitApprovalBuildPrecondition.$$$reportNull$$$0(5);
        }
        this.LOG = Logger.getInstance((String)this.getClass().getName());
        this.myLocks = Striped.lock((int)1000);
        this.myProjectManager = projectManager;
        this.myNotificationProcessor = notificationProcessor;
        this.myApprovableBuildManager = approvableBuildManager;
        this.myUntrustedBuildsManager = untrustedBuildsManager;
        this.myExecutorService = executorServices.getLowPriorityExecutorService();
        this.myWaitingForChangesCollectionPromotions = Collections.synchronizedMap(new /* Unavailable Anonymous Inner Class!! */);
        this.myProcessedGroupTops = Collections.synchronizedMap(new /* Unavailable Anonymous Inner Class!! */);
        this.myNotificationNotSentPromotions = Collections.synchronizedMap(new /* Unavailable Anonymous Inner Class!! */);
        eventDispatcher.addListener((EventListener)new /* Unavailable Anonymous Inner Class!! */);
    }

    private void submitSendNotificationTask(BuildPromotionEx buildPromotionEx) {
        try {
            this.myExecutorService.submit(() -> this.trySendNotificationForBuildChainThatRequiresApproval(buildPromotionEx));
        }
        catch (RejectedExecutionException rejectedExecutionException) {
            this.trySendNotificationForBuildChainThatRequiresApproval(buildPromotionEx);
        }
    }

    @Nullable
    private ApprovalResult getApprovalStatusForBuildChain(@NotNull BuildPromotionEx buildPromotionEx2) {
        try {
            if (buildPromotionEx2 == null) {
                WaitApprovalBuildPrecondition.$$$reportNull$$$0(6);
            }
        }
        catch (RejectedExecutionException rejectedExecutionException) {
            throw WaitApprovalBuildPrecondition.a(rejectedExecutionException);
        }
        AtomicReference<ApprovalResult> atomicReference = new AtomicReference<ApprovalResult>(this.myApprovableBuildManager.getApprovalStatus((BuildPromotion)buildPromotionEx2));
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        try {
            buildPromotionEx2.traverseDependencies(buildPromotionEx -> {
                block10: {
                    ApprovalResult approvalResult;
                    block9: {
                        try {
                            if (buildPromotionEx.isChangeCollectingNeeded()) {
                                this.myWaitingForChangesCollectionPromotions.put(buildPromotionEx.getId(), true);
                                atomicBoolean.set(true);
                                return DependencyConsumer.Result.STOP;
                            }
                        }
                        catch (RejectedExecutionException rejectedExecutionException) {
                            throw WaitApprovalBuildPrecondition.a(rejectedExecutionException);
                        }
                        approvalResult = this.myApprovableBuildManager.getApprovalStatus((BuildPromotion)buildPromotionEx);
                        try {
                            if (!approvalResult.waiting()) break block9;
                            atomicReference.set(ApprovalResult.WAITING);
                            break block10;
                        }
                        catch (RejectedExecutionException rejectedExecutionException) {
                            throw WaitApprovalBuildPrecondition.a(rejectedExecutionException);
                        }
                    }
                    try {
                        try {
                            if (!approvalResult.requiresApproval() || ((ApprovalResult)atomicReference.get()).waiting()) break block10;
                        }
                        catch (RejectedExecutionException rejectedExecutionException) {
                            throw WaitApprovalBuildPrecondition.a(rejectedExecutionException);
                        }
                        atomicReference.set(ApprovalResult.REQUIRES_APPROVAL);
                    }
                    catch (RejectedExecutionException rejectedExecutionException) {
                        throw WaitApprovalBuildPrecondition.a(rejectedExecutionException);
                    }
                }
                return DependencyConsumer.Result.CONTINUE;
            });
            if (atomicBoolean.get()) {
                return null;
            }
        }
        catch (RejectedExecutionException rejectedExecutionException) {
            throw WaitApprovalBuildPrecondition.a(rejectedExecutionException);
        }
        return atomicReference.get();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private synchronized void trySendNotificationForBuildChainThatRequiresApproval(@NotNull BuildPromotionEx buildPromotionEx) {
        ApprovalResult approvalResult;
        block21: {
            block20: {
                try {
                    if (buildPromotionEx == null) {
                        WaitApprovalBuildPrecondition.$$$reportNull$$$0(7);
                    }
                }
                catch (RejectedExecutionException rejectedExecutionException) {
                    throw WaitApprovalBuildPrecondition.a(rejectedExecutionException);
                }
                try {
                    if (this.myProcessedGroupTops.containsKey(buildPromotionEx.getId())) {
                        return;
                    }
                }
                catch (RejectedExecutionException rejectedExecutionException) {
                    throw WaitApprovalBuildPrecondition.a(rejectedExecutionException);
                }
                approvalResult = this.getApprovalStatusForBuildChain(buildPromotionEx);
                if (approvalResult == null) return;
                try {
                    if (approvalResult != ApprovalResult.WITHOUT_APPROVAL) break block20;
                    return;
                    catch (RejectedExecutionException rejectedExecutionException) {
                        throw WaitApprovalBuildPrecondition.a(rejectedExecutionException);
                    }
                }
                catch (RejectedExecutionException rejectedExecutionException) {
                    throw WaitApprovalBuildPrecondition.a(rejectedExecutionException);
                }
            }
            if (approvalResult.waiting()) {
                Lock lock = (Lock)this.myLocks.get((Object)buildPromotionEx.getId());
                lock.lock();
                try {
                    approvalResult = this.getApprovalStatusForBuildChain(buildPromotionEx);
                    try {
                        if (approvalResult == null || !approvalResult.waiting()) break block21;
                    }
                    catch (RejectedExecutionException rejectedExecutionException) {
                        throw WaitApprovalBuildPrecondition.a(rejectedExecutionException);
                    }
                    this.myNotificationNotSentPromotions.put(buildPromotionEx.getId(), true);
                    return;
                }
                finally {
                    lock.unlock();
                }
            }
        }
        try {
            try {
                if (approvalResult == null || !approvalResult.requiresApproval()) return;
            }
            catch (RejectedExecutionException rejectedExecutionException) {
                throw WaitApprovalBuildPrecondition.a(rejectedExecutionException);
            }
            this.sendNotificationForBuildchain(buildPromotionEx);
            return;
        }
        catch (RejectedExecutionException rejectedExecutionException) {
            throw WaitApprovalBuildPrecondition.a(rejectedExecutionException);
        }
    }

    private void sendNotificationForBuildchain(@NotNull BuildPromotionEx buildPromotionEx) {
        try {
            if (buildPromotionEx == null) {
                WaitApprovalBuildPrecondition.$$$reportNull$$$0(8);
            }
        }
        catch (RejectedExecutionException rejectedExecutionException) {
            throw WaitApprovalBuildPrecondition.a(rejectedExecutionException);
        }
        QueuedBuildEx queuedBuildEx = buildPromotionEx.getQueuedBuild();
        try {
            if (queuedBuildEx == null) {
                return;
            }
        }
        catch (RejectedExecutionException rejectedExecutionException) {
            throw WaitApprovalBuildPrecondition.a(rejectedExecutionException);
        }
        ApprovableBuildChain approvableBuildChain = new ApprovableBuildChain(buildPromotionEx, this.myApprovableBuildManager);
        approvableBuildChain.getGroups().forEach(approvalBuildGroupImpl -> {
            block10: {
                block9: {
                    try {
                        if (approvalBuildGroupImpl.getPromotions().isEmpty()) {
                            return;
                        }
                    }
                    catch (RejectedExecutionException rejectedExecutionException) {
                        throw WaitApprovalBuildPrecondition.a(rejectedExecutionException);
                    }
                    BuildPromotionEx buildPromotionEx = (BuildPromotionEx)approvalBuildGroupImpl.getPromotions().get(0);
                    QueuedBuildEx queuedBuildEx = buildPromotionEx.getQueuedBuild();
                    try {
                        try {
                            try {
                                if (queuedBuildEx == null) break block9;
                                if (this.myProcessedGroupTops.containsKey(buildPromotionEx.getId())) break block10;
                            }
                            catch (RejectedExecutionException rejectedExecutionException) {
                                throw WaitApprovalBuildPrecondition.a(rejectedExecutionException);
                            }
                            this.myProcessedGroupTops.put(buildPromotionEx.getId(), true);
                            if (!this.approveManuallyStartedBuildsIfNecessary((ApprovableBuildChain.ApprovalBuildGroupImpl)approvalBuildGroupImpl)) break block10;
                        }
                        catch (RejectedExecutionException rejectedExecutionException) {
                            throw WaitApprovalBuildPrecondition.a(rejectedExecutionException);
                        }
                        this.myNotificationProcessor.queuedBuildRequiresApproval((SQueuedBuild)queuedBuildEx);
                        break block10;
                    }
                    catch (RejectedExecutionException rejectedExecutionException) {
                        throw WaitApprovalBuildPrecondition.a(rejectedExecutionException);
                    }
                }
                this.LOG.warn(String.format("Failed to find queued build for promotion(id: %s) that requires approval", approvalBuildGroupImpl.getPromotions().get(0)));
            }
        });
    }

    private boolean approveManuallyStartedBuildsIfNecessary(@NotNull ApprovableBuildChain.ApprovalBuildGroupImpl approvalBuildGroupImpl) {
        SUser sUser;
        block13: {
            try {
                if (approvalBuildGroupImpl == null) {
                    WaitApprovalBuildPrecondition.$$$reportNull$$$0(9);
                }
            }
            catch (RejectedExecutionException rejectedExecutionException) {
                throw WaitApprovalBuildPrecondition.a(rejectedExecutionException);
            }
            sUser = approvalBuildGroupImpl.getUser();
            try {
                try {
                    if (sUser != null && approvalBuildGroupImpl.isTreatManualStartOfBuildAsApproval()) break block13;
                }
                catch (RejectedExecutionException rejectedExecutionException) {
                    throw WaitApprovalBuildPrecondition.a(rejectedExecutionException);
                }
                return true;
            }
            catch (RejectedExecutionException rejectedExecutionException) {
                throw WaitApprovalBuildPrecondition.a(rejectedExecutionException);
            }
        }
        if (approvalBuildGroupImpl.getApprovalRules().stream().anyMatch(approvalRule -> approvalRule.isUserAllowedToApprove(approvalBuildGroupImpl.getUser()))) {
            boolean bl;
            for (BuildPromotionEx buildPromotionEx : approvalBuildGroupImpl.getPromotions()) {
                try {
                    if (this.myApprovableBuildManager.isApprovedByUser(buildPromotionEx, sUser).booleanValue()) continue;
                    this.myApprovableBuildManager.addApprovedByUser(buildPromotionEx, sUser);
                }
                catch (RejectedExecutionException rejectedExecutionException) {
                    throw WaitApprovalBuildPrecondition.a(rejectedExecutionException);
                }
            }
            try {
                bl = !approvalBuildGroupImpl.getApprovalRules().stream().allMatch(approvalRule -> {
                    boolean bl;
                    block5: {
                        block4: {
                            try {
                                try {
                                    if (!approvalRule.isUserAllowedToApprove(approvalBuildGroupImpl.getUser()) || !approvalRule.singleUserApprovalSatisfiesRule(approvalBuildGroupImpl.getUser())) break block4;
                                }
                                catch (RejectedExecutionException rejectedExecutionException) {
                                    throw WaitApprovalBuildPrecondition.a(rejectedExecutionException);
                                }
                                bl = true;
                                break block5;
                            }
                            catch (RejectedExecutionException rejectedExecutionException) {
                                throw WaitApprovalBuildPrecondition.a(rejectedExecutionException);
                            }
                        }
                        bl = false;
                    }
                    return bl;
                });
            }
            catch (RejectedExecutionException rejectedExecutionException) {
                throw WaitApprovalBuildPrecondition.a(rejectedExecutionException);
            }
            return bl;
        }
        return true;
    }

    private void approveManualStartedBuildIfNecessary(@NotNull BuildPromotionEx buildPromotionEx) {
        block7: {
            try {
                if (buildPromotionEx == null) {
                    WaitApprovalBuildPrecondition.$$$reportNull$$$0(10);
                }
            }
            catch (RejectedExecutionException rejectedExecutionException) {
                throw WaitApprovalBuildPrecondition.a(rejectedExecutionException);
            }
            ApprovalBuildFeatureConfiguration approvalBuildFeatureConfiguration = this.myApprovableBuildManager.getApprovalConfiguration(buildPromotionEx);
            QueuedBuildEx queuedBuildEx = buildPromotionEx.getQueuedBuild();
            try {
                try {
                    if (!approvalBuildFeatureConfiguration.treatManualStartOfBuildAsApproval().booleanValue() || queuedBuildEx == null) break block7;
                }
                catch (RejectedExecutionException rejectedExecutionException) {
                    throw WaitApprovalBuildPrecondition.a(rejectedExecutionException);
                }
                this.approveManuallyStartedQueuedBuild((SQueuedBuild)queuedBuildEx, buildPromotionEx);
            }
            catch (RejectedExecutionException rejectedExecutionException) {
                throw WaitApprovalBuildPrecondition.a(rejectedExecutionException);
            }
        }
    }

    private void approveManuallyStartedQueuedBuild(@NotNull SQueuedBuild sQueuedBuild, @NotNull BuildPromotionEx buildPromotionEx) {
        try {
            if (sQueuedBuild == null) {
                WaitApprovalBuildPrecondition.$$$reportNull$$$0(11);
            }
        }
        catch (RejectedExecutionException rejectedExecutionException) {
            throw WaitApprovalBuildPrecondition.a(rejectedExecutionException);
        }
        try {
            if (buildPromotionEx == null) {
                WaitApprovalBuildPrecondition.$$$reportNull$$$0(12);
            }
        }
        catch (RejectedExecutionException rejectedExecutionException) {
            throw WaitApprovalBuildPrecondition.a(rejectedExecutionException);
        }
        try {
            if (!sQueuedBuild.getTriggeredBy().isTriggeredByUser()) {
                return;
            }
        }
        catch (RejectedExecutionException rejectedExecutionException) {
            throw WaitApprovalBuildPrecondition.a(rejectedExecutionException);
        }
        SUser sUser = sQueuedBuild.getTriggeredBy().getUser();
        try {
            if (sUser == null) {
                return;
            }
        }
        catch (RejectedExecutionException rejectedExecutionException) {
            throw WaitApprovalBuildPrecondition.a(rejectedExecutionException);
        }
        try {
            if (this.myApprovableBuildManager.isApprovableByUser(buildPromotionEx, sUser).booleanValue()) {
                this.myApprovableBuildManager.addApprovedByUser(buildPromotionEx, sUser);
            }
        }
        catch (RejectedExecutionException rejectedExecutionException) {
            throw WaitApprovalBuildPrecondition.a(rejectedExecutionException);
        }
    }

    private void failQueuedBuild(@NotNull QueuedBuildInfo queuedBuildInfo, String string) {
        try {
            if (queuedBuildInfo == null) {
                WaitApprovalBuildPrecondition.$$$reportNull$$$0(13);
            }
        }
        catch (RejectedExecutionException rejectedExecutionException) {
            throw WaitApprovalBuildPrecondition.a(rejectedExecutionException);
        }
        try {
            if (queuedBuildInfo instanceof QueuedBuildEx) {
                ((QueuedBuildEx)queuedBuildInfo).terminate(false, string, BuildProblemUtil.createBuildProblem((String)"PREPARATION_FAILURE_TYPE", (String)string));
            }
        }
        catch (RejectedExecutionException rejectedExecutionException) {
            throw WaitApprovalBuildPrecondition.a(rejectedExecutionException);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     */
    @Nullable
    public WaitReason canStart(@NotNull QueuedBuildInfo queuedBuildInfo, @NotNull Map<QueuedBuildInfo, BuildAgent> map, @NotNull BuildDistributorInput buildDistributorInput, boolean bl) {
        try {
            if (queuedBuildInfo == null) {
                WaitApprovalBuildPrecondition.$$$reportNull$$$0(14);
            }
        }
        catch (Exception exception) {
            throw WaitApprovalBuildPrecondition.a(exception);
        }
        try {
            if (map == null) {
                WaitApprovalBuildPrecondition.$$$reportNull$$$0(15);
            }
        }
        catch (Exception exception) {
            throw WaitApprovalBuildPrecondition.a(exception);
        }
        try {
            if (buildDistributorInput == null) {
                WaitApprovalBuildPrecondition.$$$reportNull$$$0(16);
            }
        }
        catch (Exception exception) {
            throw WaitApprovalBuildPrecondition.a(exception);
        }
        BuildPromotionEx buildPromotionEx = (BuildPromotionEx)queuedBuildInfo.getBuildPromotionInfo();
        try {
            block38: {
                boolean bl2;
                block39: {
                    block36: {
                        block35: {
                            block32: {
                                block34: {
                                    SProject sProject;
                                    block33: {
                                        block31: {
                                            if (!ApprovalConstants.isApprovalFeatureEnabledGlobally().booleanValue()) {
                                                return null;
                                            }
                                            ApprovalResult approvalResult = this.myApprovableBuildManager.getApprovalStatus((BuildPromotion)buildPromotionEx);
                                            if (approvalResult.waiting()) {
                                                return new SimpleWaitReason("Failed to determine if build requires approval");
                                            }
                                            if (!approvalResult.requiresApproval()) {
                                                return null;
                                            }
                                            Lock lock = (Lock)this.myLocks.get((Object)buildPromotionEx.getId());
                                            lock.lock();
                                            try {
                                                if (bl) break block31;
                                                try {
                                                    if (this.myNotificationNotSentPromotions.containsKey(buildPromotionEx.getId())) {
                                                        this.submitSendNotificationTask(buildPromotionEx);
                                                    }
                                                }
                                                catch (Exception exception) {
                                                    throw WaitApprovalBuildPrecondition.a(exception);
                                                }
                                            }
                                            finally {
                                                lock.unlock();
                                            }
                                        }
                                        if (this.myApprovableBuildManager.areApprovalRulesValid(buildPromotionEx).booleanValue()) break block32;
                                        try {
                                            block37: {
                                                if (!this.myApprovableBuildManager.hasApprovalFeature(buildPromotionEx)) break block33;
                                                break block37;
                                                catch (Exception exception) {
                                                    throw WaitApprovalBuildPrecondition.a(exception);
                                                }
                                            }
                                            this.failQueuedBuild(queuedBuildInfo, "This build is unable to start: the Approval Rules configured in the Build Approval build feature are invalid.");
                                            break block34;
                                        }
                                        catch (Exception exception) {
                                            throw WaitApprovalBuildPrecondition.a(exception);
                                        }
                                    }
                                    UntrustedBuildsSettings untrustedBuildsSettings = this.myUntrustedBuildsManager.getUntrustedBuildsSettings(buildPromotionEx);
                                    String string = "_UNKNOWN_";
                                    if (untrustedBuildsSettings != null && (sProject = this.myProjectManager.findProjectById(untrustedBuildsSettings.getProjectId())) != null) {
                                        string = sProject.getFullName();
                                    }
                                    this.failQueuedBuild(queuedBuildInfo, String.format("This build is unable to start: the Approval Rules configured in the %s project\u2019s Untrusted Builds section are invalid.", string));
                                }
                                this.setRequiresApprovalAttribute(buildPromotionEx, false);
                                return null;
                            }
                            if (!this.myApprovableBuildManager.hasTimedOut(buildPromotionEx).booleanValue()) break block38;
                            bl2 = false;
                            if (this.myApprovableBuildManager.allApprovalRulesAreMet(buildPromotionEx).booleanValue()) break block39;
                            try {
                                block40: {
                                    if (!this.myApprovableBuildManager.hasApprovalFeature(buildPromotionEx)) break block35;
                                    break block40;
                                    catch (Exception exception) {
                                        throw WaitApprovalBuildPrecondition.a(exception);
                                    }
                                }
                                this.failQueuedBuild(queuedBuildInfo, "Build was not approved in specified time");
                                break block36;
                            }
                            catch (Exception exception) {
                                throw WaitApprovalBuildPrecondition.a(exception);
                            }
                        }
                        this.failQueuedBuild(queuedBuildInfo, "Untrusted build wasn\u2019t approved in time and was canceled");
                    }
                    bl2 = true;
                }
                this.setRequiresApprovalAttribute(buildPromotionEx, bl2);
                return null;
            }
            if (this.myApprovableBuildManager.allApprovalRulesAreMet(buildPromotionEx).booleanValue()) {
                this.setRequiresApprovalAttribute(buildPromotionEx, false);
                return null;
            }
            if (this.myApprovableBuildManager.hasApprovalFeature(buildPromotionEx)) {
                return new SimpleWaitReason("This build should be approved before it may start");
            }
            return new SimpleWaitReason("Build marked as untrusted based on current project settings: manual approval required to start");
        }
        catch (Exception exception) {
            this.LOG.warn("Error while processing the manual approval precondition for build " + queuedBuildInfo.describe(false), (Throwable)exception);
            String string = "Caught an exception while processing the approval precondition logic";
            this.failQueuedBuild(queuedBuildInfo, string);
            return new SimpleWaitReason(string);
        }
    }

    public void setRequiresApprovalAttribute(@NotNull BuildPromotionEx buildPromotionEx, boolean bl) {
        block7: {
            try {
                if (buildPromotionEx == null) {
                    WaitApprovalBuildPrecondition.$$$reportNull$$$0(17);
                }
            }
            catch (RejectedExecutionException rejectedExecutionException) {
                throw WaitApprovalBuildPrecondition.a(rejectedExecutionException);
            }
            try {
                try {
                    if (this.myApprovableBuildManager.hasApprovalFeature(buildPromotionEx) || buildPromotionEx.getAttribute("teamcity.build.untrusted.requiresApproval") != null) break block7;
                }
                catch (RejectedExecutionException rejectedExecutionException) {
                    throw WaitApprovalBuildPrecondition.a(rejectedExecutionException);
                }
                buildPromotionEx.setAttribute("teamcity.build.untrusted.requiresApproval", (Object)bl);
                buildPromotionEx.persist();
            }
            catch (RejectedExecutionException rejectedExecutionException) {
                throw WaitApprovalBuildPrecondition.a(rejectedExecutionException);
            }
        }
    }

    @NotNull
    public PositionConstraint getConstraint() {
        PositionConstraint positionConstraint;
        try {
            positionConstraint = PositionConstraint.between(Collections.singletonList(CheckingForChangesPrecondition.ORDER_ID), Collections.singletonList(BuildDependenciesPrecondition.ORDER_ID));
            if (positionConstraint == null) {
                WaitApprovalBuildPrecondition.$$$reportNull$$$0(18);
            }
        }
        catch (RejectedExecutionException rejectedExecutionException) {
            throw WaitApprovalBuildPrecondition.a(rejectedExecutionException);
        }
        return positionConstraint;
    }

    @NotNull
    public String getOrderId() {
        String string;
        try {
            string = ORDER_ID;
            if (string == null) {
                WaitApprovalBuildPrecondition.$$$reportNull$$$0(19);
            }
        }
        catch (RejectedExecutionException rejectedExecutionException) {
            throw WaitApprovalBuildPrecondition.a(rejectedExecutionException);
        }
        return string;
    }

    /*
     * Exception decompiling
     */
    private static /* synthetic */ void $$$reportNull$$$0(int var0) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 6[SWITCH]
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:435)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:484)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1050)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doClass(Driver.java:84)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:78)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    private static Exception a(Exception exception) {
        return exception;
    }
}
