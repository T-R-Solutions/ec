/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.intellij.openapi.diagnostic.Logger
 *  gnu.trove.TLongHashSet
 *  jetbrains.buildServer.buildTriggers.BuildTriggerException
 *  jetbrains.buildServer.buildTriggers.LoggerWithPrefix
 *  jetbrains.buildServer.buildTriggers.triggerRules.TriggerRules
 *  jetbrains.buildServer.buildTriggers.triggerRules.TriggerRulesFilter
 *  jetbrains.buildServer.buildTriggers.vcs.BranchChangesCalculator
 *  jetbrains.buildServer.buildTriggers.vcs.BranchChangesCalculator$Changes
 *  jetbrains.buildServer.buildTriggers.vcs.BranchStateCalculator
 *  jetbrains.buildServer.buildTriggers.vcs.BranchStateCalculator$BranchState
 *  jetbrains.buildServer.buildTriggers.vcs.BranchedParamState
 *  jetbrains.buildServer.buildTriggers.vcs.InvalidQuietPeriodException
 *  jetbrains.buildServer.buildTriggers.vcs.SharedTriggeringRulesState
 *  jetbrains.buildServer.buildTriggers.vcs.SingleBranchVcsTrigger$MoreRecentChangeFoundException
 *  jetbrains.buildServer.buildTriggers.vcs.SingleBranchVcsTrigger$RunBuildDecision
 *  jetbrains.buildServer.buildTriggers.vcs.SingleBranchVcsTrigger$TriggerStateUpdate
 *  jetbrains.buildServer.buildTriggers.vcs.SlowVcsRootInstanceTracker
 *  jetbrains.buildServer.buildTriggers.vcs.VcsBuildTriggerState
 *  jetbrains.buildServer.buildTriggers.vcs.VcsTriggerContext
 *  jetbrains.buildServer.buildTriggers.vcs.VcsTriggerDescriptor
 *  jetbrains.buildServer.buildTriggers.vcs.VcsTriggeringContext
 *  jetbrains.buildServer.log.LogUtil
 *  jetbrains.buildServer.log.Loggers
 *  jetbrains.buildServer.serverSide.Branch
 *  jetbrains.buildServer.serverSide.BranchEx
 *  jetbrains.buildServer.serverSide.BuildCustomizer
 *  jetbrains.buildServer.serverSide.BuildPromotionEx
 *  jetbrains.buildServer.serverSide.BuildTypeEx
 *  jetbrains.buildServer.serverSide.ChangeDescriptor
 *  jetbrains.buildServer.serverSide.ChangesCalculationOptionsEx
 *  jetbrains.buildServer.serverSide.SBuildType
 *  jetbrains.buildServer.serverSide.TeamCityProperties
 *  jetbrains.buildServer.serverSide.TriggeredByBuilder
 *  jetbrains.buildServer.serverSide.VcsModificationProcessor
 *  jetbrains.buildServer.serverSide.impl.CancelCheck
 *  jetbrains.buildServer.serverSide.impl.CancelableTaskHolder
 *  jetbrains.buildServer.util.CollectionsUtil
 *  jetbrains.buildServer.util.Objects
 *  jetbrains.buildServer.util.Predicate
 *  jetbrains.buildServer.util.StringUtil
 *  jetbrains.buildServer.util.TimeService
 *  jetbrains.buildServer.util.graph.DAG
 *  jetbrains.buildServer.vcs.OperationRequestor
 *  jetbrains.buildServer.vcs.RelationType
 *  jetbrains.buildServer.vcs.SVcsModification
 *  jetbrains.buildServer.vcs.SelectPrevBuildPolicy
 *  jetbrains.buildServer.vcs.VcsChangesCollectorFactory
 *  jetbrains.buildServer.vcs.VcsRoot
 *  jetbrains.buildServer.vcs.VcsRootInstance
 *  jetbrains.buildServer.vcs.VcsRootInstanceEx
 *  jetbrains.buildServer.vcs.impl.HistoryChangesCollector
 *  jetbrains.buildServer.vcs.impl.RevisionsNotFoundException
 *  jetbrains.buildServer.vcs.impl.VcsModificationEx
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package jetbrains.buildServer.buildTriggers.vcs;

import com.intellij.openapi.diagnostic.Logger;
import gnu.trove.TLongHashSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import jetbrains.buildServer.buildTriggers.BuildTriggerException;
import jetbrains.buildServer.buildTriggers.LoggerWithPrefix;
import jetbrains.buildServer.buildTriggers.triggerRules.TriggerRules;
import jetbrains.buildServer.buildTriggers.triggerRules.TriggerRulesFilter;
import jetbrains.buildServer.buildTriggers.vcs.BranchChangesCalculator;
import jetbrains.buildServer.buildTriggers.vcs.BranchStateCalculator;
import jetbrains.buildServer.buildTriggers.vcs.BranchedParamState;
import jetbrains.buildServer.buildTriggers.vcs.InvalidQuietPeriodException;
import jetbrains.buildServer.buildTriggers.vcs.SharedTriggeringRulesState;
import jetbrains.buildServer.buildTriggers.vcs.SingleBranchVcsTrigger;
import jetbrains.buildServer.buildTriggers.vcs.SlowVcsRootInstanceTracker;
import jetbrains.buildServer.buildTriggers.vcs.VcsBuildTriggerState;
import jetbrains.buildServer.buildTriggers.vcs.VcsTriggerContext;
import jetbrains.buildServer.buildTriggers.vcs.VcsTriggerDescriptor;
import jetbrains.buildServer.buildTriggers.vcs.VcsTriggeringContext;
import jetbrains.buildServer.log.LogUtil;
import jetbrains.buildServer.log.Loggers;
import jetbrains.buildServer.serverSide.Branch;
import jetbrains.buildServer.serverSide.BranchEx;
import jetbrains.buildServer.serverSide.BuildCustomizer;
import jetbrains.buildServer.serverSide.BuildPromotionEx;
import jetbrains.buildServer.serverSide.BuildTypeEx;
import jetbrains.buildServer.serverSide.ChangeDescriptor;
import jetbrains.buildServer.serverSide.ChangesCalculationOptionsEx;
import jetbrains.buildServer.serverSide.SBuildType;
import jetbrains.buildServer.serverSide.TeamCityProperties;
import jetbrains.buildServer.serverSide.TriggeredByBuilder;
import jetbrains.buildServer.serverSide.VcsModificationProcessor;
import jetbrains.buildServer.serverSide.impl.CancelCheck;
import jetbrains.buildServer.serverSide.impl.CancelableTaskHolder;
import jetbrains.buildServer.util.CollectionsUtil;
import jetbrains.buildServer.util.Objects;
import jetbrains.buildServer.util.Predicate;
import jetbrains.buildServer.util.StringUtil;
import jetbrains.buildServer.util.TimeService;
import jetbrains.buildServer.util.graph.DAG;
import jetbrains.buildServer.vcs.OperationRequestor;
import jetbrains.buildServer.vcs.RelationType;
import jetbrains.buildServer.vcs.SVcsModification;
import jetbrains.buildServer.vcs.SelectPrevBuildPolicy;
import jetbrains.buildServer.vcs.VcsChangesCollectorFactory;
import jetbrains.buildServer.vcs.VcsRoot;
import jetbrains.buildServer.vcs.VcsRootInstance;
import jetbrains.buildServer.vcs.VcsRootInstanceEx;
import jetbrains.buildServer.vcs.impl.HistoryChangesCollector;
import jetbrains.buildServer.vcs.impl.RevisionsNotFoundException;
import jetbrains.buildServer.vcs.impl.VcsModificationEx;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Exception performing whole class analysis ignored.
 */
class SingleBranchVcsTrigger {
    public static final String FORCE_CHANGES_CHECKING_FOR_QUIET_PERIOD = "teamcity.vcsTrigger.forceChangesCheckingForQuietPeriod";
    public static final String TRIGGER_UNKNOWN_NEW_BRANCH_MAX_CHANGES_TO_HANDLE_PER_VCS_ROOT_PROP = "teamcity.vcsTrigger.unknownNewBranch.maxChangesToHandlePerVcsRoot";
    private final TimeService myTimeService;
    private final VcsChangesCollectorFactory myChangesCollectorFactory;
    private final BuildTypeEx myBuildType;
    private final BranchEx myBranch;
    private final VcsBuildTriggerState myState;
    private final VcsTriggeringContext myTriggeringContext;
    private final SharedTriggeringRulesState mySharedTriggeringRulesState;
    private final BranchChangesCalculator myBranchChangesCalculator;
    private final VcsTriggerDescriptor myTriggerDescriptor;
    private final BranchStateCalculator myBranchStateCalculator;
    private boolean myBuildTriggered;
    static final /* synthetic */ boolean $assertionsDisabled;

    SingleBranchVcsTrigger(@NotNull VcsTriggeringContext vcsTriggeringContext, @NotNull BuildTypeEx buildTypeEx, @NotNull BranchEx branchEx, @NotNull VcsBuildTriggerState vcsBuildTriggerState, @NotNull SharedTriggeringRulesState sharedTriggeringRulesState, @NotNull VcsTriggerDescriptor vcsTriggerDescriptor) {
        if (vcsTriggeringContext == null) {
            SingleBranchVcsTrigger.$$$reportNull$$$0(0);
        }
        if (buildTypeEx == null) {
            SingleBranchVcsTrigger.$$$reportNull$$$0(1);
        }
        if (branchEx == null) {
            SingleBranchVcsTrigger.$$$reportNull$$$0(2);
        }
        if (vcsBuildTriggerState == null) {
            SingleBranchVcsTrigger.$$$reportNull$$$0(3);
        }
        if (sharedTriggeringRulesState == null) {
            SingleBranchVcsTrigger.$$$reportNull$$$0(4);
        }
        if (vcsTriggerDescriptor == null) {
            SingleBranchVcsTrigger.$$$reportNull$$$0(5);
        }
        this.myBuildTriggered = false;
        this.myTriggeringContext = vcsTriggeringContext;
        this.myChangesCollectorFactory = vcsTriggeringContext.getChangesCollectorFactory();
        this.myTimeService = vcsTriggeringContext.getTimeService();
        this.mySharedTriggeringRulesState = sharedTriggeringRulesState;
        this.myTriggerDescriptor = vcsTriggerDescriptor;
        this.myBuildType = buildTypeEx;
        this.myBranch = branchEx;
        this.myState = vcsBuildTriggerState;
        this.myBranchChangesCalculator = new BranchChangesCalculator(branchEx, vcsTriggeringContext.getChangesCalculationOptionsFactory(), vcsTriggeringContext.getVcsRootInstancesManager(), this.myTimeService);
        this.myBranchStateCalculator = new BranchStateCalculator((Branch)this.myBranch, this.myBuildType, this.myTimeService);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void triggerBuild(@NotNull VcsTriggerContext vcsTriggerContext) {
        List<RunBuildDecision> list;
        BranchChangesCalculator.Changes changes;
        long l3;
        TriggerStateUpdate triggerStateUpdate;
        int n;
        VcsTriggerDescriptor vcsTriggerDescriptor;
        Logger logger;
        block143: {
            SVcsModification sVcsModification;
            List<Object> list2;
            ArrayList<SVcsModification> arrayList;
            TriggerRulesFilter triggerRulesFilter;
            Long l42;
            block142: {
                HashMap<Long, Long> hashMap;
                block141: {
                    block140: {
                        TriggerRules triggerRules;
                        block138: {
                            block139: {
                                block154: {
                                    block153: {
                                        boolean bl;
                                        block152: {
                                            List<Object> list3;
                                            block136: {
                                                block137: {
                                                    boolean bl2;
                                                    block135: {
                                                        block134: {
                                                            block132: {
                                                                block133: {
                                                                    block131: {
                                                                        block130: {
                                                                            boolean bl3;
                                                                            Set<Long> set;
                                                                            block129: {
                                                                                boolean bl4;
                                                                                block128: {
                                                                                    block127: {
                                                                                        block146: {
                                                                                            block126: {
                                                                                                try {
                                                                                                    if (vcsTriggerContext == null) {
                                                                                                        SingleBranchVcsTrigger.$$$reportNull$$$0(6);
                                                                                                    }
                                                                                                }
                                                                                                catch (NumberFormatException numberFormatException) {
                                                                                                    throw SingleBranchVcsTrigger.a(numberFormatException);
                                                                                                }
                                                                                                logger = vcsTriggerContext.getLogger((Branch)this.myBranch);
                                                                                                vcsTriggerDescriptor = vcsTriggerContext.getVcsTriggerDescriptor();
                                                                                                n = this.getQuietPeriodTimeout(vcsTriggerDescriptor);
                                                                                                try {
                                                                                                    if (n < 0) {
                                                                                                        logger.warn("VCS trigger has negative quiet period: " + n + ", skip triggering");
                                                                                                        return;
                                                                                                    }
                                                                                                }
                                                                                                catch (NumberFormatException numberFormatException) {
                                                                                                    throw SingleBranchVcsTrigger.a(numberFormatException);
                                                                                                }
                                                                                                long l5 = this.myTriggeringContext.getChangesCalculationTimestamps().getNextScheduled(this.myBuildType, (Branch)this.myBranch);
                                                                                                if (n <= 0) break block126;
                                                                                                try {
                                                                                                    block145: {
                                                                                                        if (l5 <= this.myTimeService.now()) break block126;
                                                                                                        break block145;
                                                                                                        catch (NumberFormatException numberFormatException) {
                                                                                                            throw SingleBranchVcsTrigger.a(numberFormatException);
                                                                                                        }
                                                                                                    }
                                                                                                    logger.debug("Quiet period is not passed from last changes calculation, skip trigger processing");
                                                                                                    return;
                                                                                                }
                                                                                                catch (NumberFormatException numberFormatException) {
                                                                                                    throw SingleBranchVcsTrigger.a(numberFormatException);
                                                                                                }
                                                                                            }
                                                                                            set = this.getVcsRootIds(vcsTriggerContext);
                                                                                            triggerStateUpdate = new TriggerStateUpdate(this, this.myState, set);
                                                                                            if (this.myState.getLastTriggeredBy(this.myBranch.getName()) != null) break block127;
                                                                                            if (this.myState.getLastProcessedModId(this.myBranch.getName()) != null) break block127;
                                                                                            break block146;
                                                                                            catch (NumberFormatException numberFormatException) {
                                                                                                throw SingleBranchVcsTrigger.a(numberFormatException);
                                                                                            }
                                                                                        }
                                                                                        try {
                                                                                            block147: {
                                                                                                if (triggerStateUpdate.isBranchMarkedAsOld()) break block127;
                                                                                                break block147;
                                                                                                catch (NumberFormatException numberFormatException) {
                                                                                                    throw SingleBranchVcsTrigger.a(numberFormatException);
                                                                                                }
                                                                                            }
                                                                                            bl4 = true;
                                                                                            break block128;
                                                                                        }
                                                                                        catch (NumberFormatException numberFormatException) {
                                                                                            throw SingleBranchVcsTrigger.a(numberFormatException);
                                                                                        }
                                                                                    }
                                                                                    bl4 = false;
                                                                                }
                                                                                bl3 = bl4;
                                                                                l3 = this.myTimeService.now() + 1000L * (long)n;
                                                                                changes = this.getPendingChanges(logger, vcsTriggerDescriptor, triggerStateUpdate, set);
                                                                                if (changes.isEmpty()) break block129;
                                                                                try {
                                                                                    block148: {
                                                                                        if (!logger.isDebugEnabled()) break block129;
                                                                                        break block148;
                                                                                        catch (NumberFormatException numberFormatException) {
                                                                                            throw SingleBranchVcsTrigger.a(numberFormatException);
                                                                                        }
                                                                                    }
                                                                                    logger.debug("Trigger found " + changes.getChanges().size() + " pending changes since the last change with id: " + String.valueOf(triggerStateUpdate.getLastProcessedModId()) + ", changes: " + CollectionsUtil.asString((Collection)changes.getChanges(), (int)10));
                                                                                }
                                                                                catch (NumberFormatException numberFormatException) {
                                                                                    throw SingleBranchVcsTrigger.a(numberFormatException);
                                                                                }
                                                                            }
                                                                            hashMap = new HashMap<Long, Long>(changes.getMostRecentVisitedCommits());
                                                                            for (Long l42 : set) {
                                                                                hashMap.putIfAbsent(l42, triggerStateUpdate.getLastProcessedModId().getOrDefault(l42, -1L));
                                                                            }
                                                                            if (!bl3) break block130;
                                                                            try {
                                                                                block149: {
                                                                                    if (this.myBranch.isDefaultBranch()) break block130;
                                                                                    break block149;
                                                                                    catch (NumberFormatException numberFormatException) {
                                                                                        throw SingleBranchVcsTrigger.a(numberFormatException);
                                                                                    }
                                                                                }
                                                                                if (!this.handleUnknownBranch(vcsTriggerContext, logger, triggerStateUpdate, changes)) break block130;
                                                                            }
                                                                            catch (NumberFormatException numberFormatException) {
                                                                                throw SingleBranchVcsTrigger.a(numberFormatException);
                                                                            }
                                                                            this.adjustLastProcessedModId(triggerStateUpdate, hashMap);
                                                                            if (triggerStateUpdate.isShouldUpdateState()) {
                                                                                triggerStateUpdate.getVcsRootModIdChangeReasons().forEach((l, string) -> logger.info(string));
                                                                                this.myState.updateStateForBranch(this.myBranch.getName(), triggerStateUpdate.getLastTriggeredBy(), triggerStateUpdate.getLastProcessedModId(), triggerStateUpdate.isBranchMarkedAsOld(), logger);
                                                                            }
                                                                            try {
                                                                                if (n <= 0) return;
                                                                                this.myTriggeringContext.getChangesCalculationTimestamps().persistNextScheduled(this.myBuildType, this.myBranch, l3);
                                                                                return;
                                                                            }
                                                                            catch (NumberFormatException numberFormatException) {
                                                                                throw SingleBranchVcsTrigger.a(numberFormatException);
                                                                            }
                                                                        }
                                                                        if (!changes.isEmpty()) break block131;
                                                                        this.adjustLastProcessedModId(triggerStateUpdate, hashMap);
                                                                        if (triggerStateUpdate.isShouldUpdateState()) {
                                                                            triggerStateUpdate.getVcsRootModIdChangeReasons().forEach((l, string) -> logger.info(string));
                                                                            this.myState.updateStateForBranch(this.myBranch.getName(), triggerStateUpdate.getLastTriggeredBy(), triggerStateUpdate.getLastProcessedModId(), triggerStateUpdate.isBranchMarkedAsOld(), logger);
                                                                        }
                                                                        try {
                                                                            if (n <= 0) return;
                                                                            this.myTriggeringContext.getChangesCalculationTimestamps().persistNextScheduled(this.myBuildType, this.myBranch, l3);
                                                                            return;
                                                                        }
                                                                        catch (NumberFormatException numberFormatException) {
                                                                            throw SingleBranchVcsTrigger.a(numberFormatException);
                                                                        }
                                                                    }
                                                                    list3 = changes.getChanges();
                                                                    l42 = this.getLastChange((List<SVcsModification>)list3, vcsTriggerDescriptor);
                                                                    l3 = l42.getRegistrationDate().getTime() + (long)n * 1000L;
                                                                    if (this.isQuietPeriodPassed((SVcsModification)l42, n)) break block132;
                                                                    try {
                                                                        block150: {
                                                                            triggerStateUpdate.setShouldUpdateState(false);
                                                                            if (!logger.isDebugEnabled()) break block133;
                                                                            break block150;
                                                                            catch (NumberFormatException numberFormatException) {
                                                                                throw SingleBranchVcsTrigger.a(numberFormatException);
                                                                            }
                                                                        }
                                                                        logger.debug("Quiet period is not passed yet since the last detected change: " + LogUtil.describe((Object)l42) + ", quiet period: " + n + ", skip triggering");
                                                                    }
                                                                    catch (NumberFormatException numberFormatException) {
                                                                        throw SingleBranchVcsTrigger.a(numberFormatException);
                                                                    }
                                                                }
                                                                try {
                                                                    if (triggerStateUpdate.isShouldUpdateState()) {
                                                                        triggerStateUpdate.getVcsRootModIdChangeReasons().forEach((l, string) -> logger.info(string));
                                                                        this.myState.updateStateForBranch(this.myBranch.getName(), triggerStateUpdate.getLastTriggeredBy(), triggerStateUpdate.getLastProcessedModId(), triggerStateUpdate.isBranchMarkedAsOld(), logger);
                                                                    }
                                                                }
                                                                catch (NumberFormatException numberFormatException) {
                                                                    throw SingleBranchVcsTrigger.a(numberFormatException);
                                                                }
                                                                try {
                                                                    if (n <= 0) return;
                                                                    this.myTriggeringContext.getChangesCalculationTimestamps().persistNextScheduled(this.myBuildType, this.myBranch, l3);
                                                                    return;
                                                                }
                                                                catch (NumberFormatException numberFormatException) {
                                                                    throw SingleBranchVcsTrigger.a(numberFormatException);
                                                                }
                                                            }
                                                            try {
                                                                if (!vcsTriggerDescriptor.isAssociateChangeWithBuild() || this.isAllVcsRootsCheckedForChanges(vcsTriggerContext, (SVcsModification)l42, n)) break block134;
                                                            }
                                                            catch (NumberFormatException numberFormatException) {
                                                                throw SingleBranchVcsTrigger.a(numberFormatException);
                                                            }
                                                            triggerStateUpdate.setShouldUpdateState(false);
                                                            this.logChangesCollectingInProgress(logger);
                                                            if (triggerStateUpdate.isShouldUpdateState()) {
                                                                triggerStateUpdate.getVcsRootModIdChangeReasons().forEach((l, string) -> logger.info(string));
                                                                this.myState.updateStateForBranch(this.myBranch.getName(), triggerStateUpdate.getLastTriggeredBy(), triggerStateUpdate.getLastProcessedModId(), triggerStateUpdate.isBranchMarkedAsOld(), logger);
                                                            }
                                                            try {
                                                                if (n <= 0) return;
                                                                this.myTriggeringContext.getChangesCalculationTimestamps().persistNextScheduled(this.myBuildType, this.myBranch, l3);
                                                                return;
                                                            }
                                                            catch (NumberFormatException numberFormatException) {
                                                                throw SingleBranchVcsTrigger.a(numberFormatException);
                                                            }
                                                        }
                                                        if (!this.handleUnreachableLastProcessedChange(triggerStateUpdate, hashMap, (List<SVcsModification>)list3)) break block135;
                                                        try {
                                                            if (triggerStateUpdate.isShouldUpdateState()) {
                                                                triggerStateUpdate.getVcsRootModIdChangeReasons().forEach((l, string) -> logger.info(string));
                                                                this.myState.updateStateForBranch(this.myBranch.getName(), triggerStateUpdate.getLastTriggeredBy(), triggerStateUpdate.getLastProcessedModId(), triggerStateUpdate.isBranchMarkedAsOld(), logger);
                                                            }
                                                        }
                                                        catch (NumberFormatException numberFormatException) {
                                                            throw SingleBranchVcsTrigger.a(numberFormatException);
                                                        }
                                                        try {
                                                            if (n <= 0) return;
                                                            this.myTriggeringContext.getChangesCalculationTimestamps().persistNextScheduled(this.myBuildType, this.myBranch, l3);
                                                            return;
                                                        }
                                                        catch (NumberFormatException numberFormatException) {
                                                            throw SingleBranchVcsTrigger.a(numberFormatException);
                                                        }
                                                    }
                                                    try {
                                                        bl2 = !this.myBranch.isDefaultBranch();
                                                    }
                                                    catch (NumberFormatException numberFormatException) {
                                                        throw SingleBranchVcsTrigger.a(numberFormatException);
                                                    }
                                                    bl = bl2;
                                                    List list4 = vcsTriggerContext.watchedBuildTypes();
                                                    triggerRules = vcsTriggerContext.getTriggerRules();
                                                    triggerRulesFilter = new TriggerRulesFilter(triggerRules, (Collection)list4, this.getMergedBranchesThreshold(), "VCS trigger (branch: " + this.myBranch.getName() + ")", (SBuildType)this.myBuildType, this.mySharedTriggeringRulesState, logger);
                                                    triggerRulesFilter.setLowerBound(changes.getLowerBoundIds());
                                                    triggerRulesFilter.setAnalyzeFullHistoryForMergeCommits(this.isAnalyzeFullHistoryForMergeCommits(this.myBuildType));
                                                    triggerRulesFilter.setMaxChangesToProcessPerVcsRoot(this.getTriggeringRulesChangesLimit());
                                                    arrayList = new ArrayList<SVcsModification>(list3);
                                                    list2 = list3;
                                                    if (!vcsTriggerDescriptor.isPerCheckinTriggeringEnabled()) break block136;
                                                    try {
                                                        block151: {
                                                            if (!this.myBuildType.getBooleanInternalParameterOrTrue("vcsTrigger.perCheckin.filterOutChangesFromBranch")) break block137;
                                                            break block151;
                                                            catch (NumberFormatException numberFormatException) {
                                                                throw SingleBranchVcsTrigger.a(numberFormatException);
                                                            }
                                                        }
                                                        this.filterOutChangesFromBranch((List<SVcsModification>)list2, this.getBranchedParamState());
                                                    }
                                                    catch (NumberFormatException numberFormatException) {
                                                        throw SingleBranchVcsTrigger.a(numberFormatException);
                                                    }
                                                }
                                                triggerRulesFilter.filterModifications((List)list2);
                                                break block152;
                                            }
                                            if (bl) {
                                                list2 = triggerRulesFilter.findFirstInEachVcsRoot((List)list3);
                                            } else {
                                                List<Object> list5;
                                                sVcsModification = triggerRulesFilter.findFirst((List)list3);
                                                try {
                                                    list5 = sVcsModification == null ? Collections.emptyList() : Collections.singletonList(sVcsModification);
                                                }
                                                catch (NumberFormatException numberFormatException) {
                                                    throw SingleBranchVcsTrigger.a(numberFormatException);
                                                }
                                                list2 = list5;
                                            }
                                        }
                                        if (list2.isEmpty()) break block138;
                                        if (!bl) break block138;
                                        break block153;
                                        catch (NumberFormatException numberFormatException) {
                                            throw SingleBranchVcsTrigger.a(numberFormatException);
                                        }
                                    }
                                    if (!changes.getChangesFromVcsRootsWithoutDefaultBranchFallback(list2).isEmpty()) break block138;
                                    break block154;
                                    catch (NumberFormatException numberFormatException) {
                                        throw SingleBranchVcsTrigger.a(numberFormatException);
                                    }
                                }
                                try {
                                    block155: {
                                        if (!logger.isDebugEnabled()) break block139;
                                        break block155;
                                        catch (NumberFormatException numberFormatException) {
                                            throw SingleBranchVcsTrigger.a(numberFormatException);
                                        }
                                    }
                                    logger.debug("There are pending changes, but all of them were detected in VCS roots where current branch either does not exist or is not included into the branch specificaton");
                                }
                                catch (NumberFormatException numberFormatException) {
                                    throw SingleBranchVcsTrigger.a(numberFormatException);
                                }
                            }
                            try {
                                if (triggerStateUpdate.isShouldUpdateState()) {
                                    triggerStateUpdate.getVcsRootModIdChangeReasons().forEach((l, string) -> logger.info(string));
                                    this.myState.updateStateForBranch(this.myBranch.getName(), triggerStateUpdate.getLastTriggeredBy(), triggerStateUpdate.getLastProcessedModId(), triggerStateUpdate.isBranchMarkedAsOld(), logger);
                                }
                            }
                            catch (NumberFormatException numberFormatException) {
                                throw SingleBranchVcsTrigger.a(numberFormatException);
                            }
                            try {
                                if (n <= 0) return;
                                this.myTriggeringContext.getChangesCalculationTimestamps().persistNextScheduled(this.myBuildType, this.myBranch, l3);
                                return;
                            }
                            catch (NumberFormatException numberFormatException) {
                                throw SingleBranchVcsTrigger.a(numberFormatException);
                            }
                        }
                        this.adjustLastProcessedModId(triggerStateUpdate, hashMap);
                        if (!list2.isEmpty()) break block140;
                        logger.info("There are new pending changes found, but none of them were matched by triggering rules: " + String.valueOf(triggerRules) + ", skip triggering");
                        if (triggerStateUpdate.isShouldUpdateState()) {
                            triggerStateUpdate.getVcsRootModIdChangeReasons().forEach((l, string) -> logger.info(string));
                            this.myState.updateStateForBranch(this.myBranch.getName(), triggerStateUpdate.getLastTriggeredBy(), triggerStateUpdate.getLastProcessedModId(), triggerStateUpdate.isBranchMarkedAsOld(), logger);
                        }
                        try {
                            if (n <= 0) return;
                            this.myTriggeringContext.getChangesCalculationTimestamps().persistNextScheduled(this.myBuildType, this.myBranch, l3);
                            return;
                        }
                        catch (NumberFormatException numberFormatException) {
                            throw SingleBranchVcsTrigger.a(numberFormatException);
                        }
                    }
                    if (!this.checkTriggeredInObsoleteBranchOnTheSameCommit(vcsTriggerDescriptor, hashMap, logger)) break block141;
                    try {
                        if (triggerStateUpdate.isShouldUpdateState()) {
                            triggerStateUpdate.getVcsRootModIdChangeReasons().forEach((l, string) -> logger.info(string));
                            this.myState.updateStateForBranch(this.myBranch.getName(), triggerStateUpdate.getLastTriggeredBy(), triggerStateUpdate.getLastProcessedModId(), triggerStateUpdate.isBranchMarkedAsOld(), logger);
                        }
                    }
                    catch (NumberFormatException numberFormatException) {
                        throw SingleBranchVcsTrigger.a(numberFormatException);
                    }
                    try {
                        if (n <= 0) return;
                        this.myTriggeringContext.getChangesCalculationTimestamps().persistNextScheduled(this.myBuildType, this.myBranch, l3);
                        return;
                    }
                    catch (NumberFormatException numberFormatException) {
                        throw SingleBranchVcsTrigger.a(numberFormatException);
                    }
                }
                hashMap.forEach((l, l2) -> triggerStateUpdate.putLastTriggeredBy(l.longValue(), l2));
                if (triggerStateUpdate.isStateChanged()) break block142;
                logger.warn("VCS trigger detected incorrect pending changes, triggering will be skipped; pending changes: " + CollectionsUtil.asString((Collection)changes.getChanges(), (int)10) + ", last processed mod id: " + String.valueOf(triggerStateUpdate.getLastProcessedModId()) + ", visited commits: " + String.valueOf(changes.getMostRecentVisitedCommits()));
                triggerStateUpdate.setShouldUpdateState(false);
                if (triggerStateUpdate.isShouldUpdateState()) {
                    triggerStateUpdate.getVcsRootModIdChangeReasons().forEach((l, string) -> logger.info(string));
                    this.myState.updateStateForBranch(this.myBranch.getName(), triggerStateUpdate.getLastTriggeredBy(), triggerStateUpdate.getLastProcessedModId(), triggerStateUpdate.isBranchMarkedAsOld(), logger);
                }
                try {
                    if (n <= 0) return;
                    this.myTriggeringContext.getChangesCalculationTimestamps().persistNextScheduled(this.myBuildType, this.myBranch, l3);
                    return;
                }
                catch (NumberFormatException numberFormatException) {
                    throw SingleBranchVcsTrigger.a(numberFormatException);
                }
            }
            sVcsModification = (SVcsModification)list2.getFirst();
            try {
                if (vcsTriggerDescriptor.isGroupCheckinsByCommitter()) {
                    this.filterOutUnnecessaryCheckins(list2);
                }
            }
            catch (NumberFormatException numberFormatException) {
                throw SingleBranchVcsTrigger.a(numberFormatException);
            }
            list = this.prepareBuilds(vcsTriggerDescriptor, list2, (SVcsModification)l42, sVcsModification, triggerRulesFilter, vcsTriggerContext, triggerStateUpdate, arrayList);
            if (list.isEmpty()) break block143;
            try {
                block156: {
                    if (!vcsTriggerDescriptor.isQuietPeriodEnabled()) break block143;
                    break block156;
                    catch (NumberFormatException numberFormatException) {
                        throw SingleBranchVcsTrigger.a(numberFormatException);
                    }
                }
                if (!vcsTriggerContext.isRepositoryStateUpdated()) break block143;
            }
            catch (NumberFormatException numberFormatException) {
                throw SingleBranchVcsTrigger.a(numberFormatException);
            }
            this.logRepositoryStateUpdated(logger);
            triggerStateUpdate.setShouldUpdateState(false);
            if (triggerStateUpdate.isShouldUpdateState()) {
                triggerStateUpdate.getVcsRootModIdChangeReasons().forEach((l, string) -> logger.info(string));
                this.myState.updateStateForBranch(this.myBranch.getName(), triggerStateUpdate.getLastTriggeredBy(), triggerStateUpdate.getLastProcessedModId(), triggerStateUpdate.isBranchMarkedAsOld(), logger);
            }
            try {
                if (n <= 0) return;
                this.myTriggeringContext.getChangesCalculationTimestamps().persistNextScheduled(this.myBuildType, this.myBranch, l3);
                return;
            }
            catch (NumberFormatException numberFormatException) {
                throw SingleBranchVcsTrigger.a(numberFormatException);
            }
        }
        try {
            block144: {
                block157: {
                    try {
                        if (logger.isDebugEnabled()) {
                            logger.debug("VCS trigger decided to trigger " + list.size() + " builds");
                        }
                    }
                    catch (NumberFormatException numberFormatException) {
                        throw SingleBranchVcsTrigger.a(numberFormatException);
                    }
                    if (vcsTriggerDescriptor.isQuietPeriodEnabled()) break block144;
                    if (vcsTriggerDescriptor.isPerCheckinTriggeringEnabled()) break block144;
                    break block157;
                    catch (NumberFormatException numberFormatException) {
                        throw SingleBranchVcsTrigger.a(numberFormatException);
                    }
                }
                try {
                    block158: {
                        if (changes.getMostRecentVisitedCommits().isEmpty()) break block144;
                        break block158;
                        catch (NumberFormatException numberFormatException) {
                            throw SingleBranchVcsTrigger.a(numberFormatException);
                        }
                    }
                    this.updateTriggeringDelayMetric(changes);
                }
                catch (NumberFormatException numberFormatException) {
                    throw SingleBranchVcsTrigger.a(numberFormatException);
                }
            }
            for (RunBuildDecision runBuildDecision : list) {
                runBuildDecision.execute();
                this.myBuildTriggered = true;
            }
            l3 = this.myTimeService.now() + 1000L * (long)n;
        }
        catch (Throwable throwable) {
            try {
                if (triggerStateUpdate.isShouldUpdateState()) {
                    triggerStateUpdate.getVcsRootModIdChangeReasons().forEach((l, string) -> logger.info(string));
                    this.myState.updateStateForBranch(this.myBranch.getName(), triggerStateUpdate.getLastTriggeredBy(), triggerStateUpdate.getLastProcessedModId(), triggerStateUpdate.isBranchMarkedAsOld(), logger);
                }
            }
            catch (NumberFormatException numberFormatException) {
                throw SingleBranchVcsTrigger.a(numberFormatException);
            }
            try {
                if (n <= 0) throw throwable;
                this.myTriggeringContext.getChangesCalculationTimestamps().persistNextScheduled(this.myBuildType, this.myBranch, l3);
                throw throwable;
            }
            catch (NumberFormatException numberFormatException) {
                throw SingleBranchVcsTrigger.a(numberFormatException);
            }
        }
        try {
            if (triggerStateUpdate.isShouldUpdateState()) {
                triggerStateUpdate.getVcsRootModIdChangeReasons().forEach((l, string) -> logger.info(string));
                this.myState.updateStateForBranch(this.myBranch.getName(), triggerStateUpdate.getLastTriggeredBy(), triggerStateUpdate.getLastProcessedModId(), triggerStateUpdate.isBranchMarkedAsOld(), logger);
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw SingleBranchVcsTrigger.a(numberFormatException);
        }
        try {
            if (n <= 0) return;
            this.myTriggeringContext.getChangesCalculationTimestamps().persistNextScheduled(this.myBuildType, this.myBranch, l3);
            return;
        }
        catch (NumberFormatException numberFormatException) {
            throw SingleBranchVcsTrigger.a(numberFormatException);
        }
    }

    private void updateTriggeringDelayMetric(@NotNull BranchChangesCalculator.Changes changes) {
        try {
            if (changes == null) {
                SingleBranchVcsTrigger.$$$reportNull$$$0(7);
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw SingleBranchVcsTrigger.a(numberFormatException);
        }
        long l = (Long)Collections.max(changes.getMostRecentVisitedCommits().values());
        SVcsModification sVcsModification = this.myTriggeringContext.getVcsHistory().findChangeById(l);
        if (sVcsModification != null) {
            long l2 = Math.max(0L, this.myTimeService.now() - sVcsModification.getRegistrationDate().getTime());
            this.myTriggeringContext.getTriggeringDelayMetric().increment((double)l2);
        }
    }

    @NotNull
    private Set<Long> getVcsRootIds(@NotNull VcsTriggerContext vcsTriggerContext) {
        Set<Long> set;
        try {
            if (vcsTriggerContext == null) {
                SingleBranchVcsTrigger.$$$reportNull$$$0(8);
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw SingleBranchVcsTrigger.a(numberFormatException);
        }
        Set<Long> set2 = vcsTriggerContext.getRootsInChain().stream().map(VcsRoot::getId).collect(Collectors.toSet());
        BranchedParamState branchedParamState = this.myState.getLastProcessedModId(this.myBranch.getName());
        try {
            if (branchedParamState != null) {
                set2.addAll(branchedParamState.getVcsRootIds());
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw SingleBranchVcsTrigger.a(numberFormatException);
        }
        try {
            set = set2;
            if (set == null) {
                SingleBranchVcsTrigger.$$$reportNull$$$0(9);
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw SingleBranchVcsTrigger.a(numberFormatException);
        }
        return set;
    }

    private int getTriggeringRulesChangesLimit() {
        int n = 10000;
        try {
            return Integer.parseInt(this.myBuildType.getInternalParameterValue("vcsTrigger.triggeringRules.maxChangesToProcess", String.valueOf(n)));
        }
        catch (NumberFormatException numberFormatException) {
            return n;
        }
    }

    private void filterOutChangesFromBranch(List<SVcsModification> list, BranchedParamState branchedParamState) {
        VcsRootInstance vcsRootInstance;
        HashMap<VcsRootInstanceEx, List> hashMap = new HashMap<VcsRootInstanceEx, List>();
        HashSet<Long> hashSet = new HashSet<Long>();
        for (SVcsModification object : list) {
            block6: {
                vcsRootInstance = object.getVcsRoot();
                try {
                    if (!vcsRootInstance.isDagBased() || !(vcsRootInstance instanceof VcsRootInstanceEx)) break block6;
                }
                catch (NumberFormatException numberFormatException) {
                    throw SingleBranchVcsTrigger.a(numberFormatException);
                }
                List list2 = hashMap.computeIfAbsent((VcsRootInstanceEx)vcsRootInstance, vcsRootInstanceEx -> new ArrayList());
                list2.add(object);
                continue;
            }
            hashSet.add(object.getId());
        }
        for (Map.Entry entry : hashMap.entrySet()) {
            List list3;
            vcsRootInstance = ((VcsRootInstanceEx)entry.getKey()).getDag();
            long l = branchedParamState.getModificationId(((VcsRootInstanceEx)entry.getKey()).getId());
            List list4 = (List)entry.getValue();
            if (l == -1L) {
                l = ((SVcsModification)list4.getLast()).getId();
            }
            long l2 = ((SVcsModification)list4.getFirst()).getId();
            do {
                hashSet.add(l2);
            } while (!(list3 = vcsRootInstance.getParents((Object)l2)).isEmpty() && (l2 = ((Long)list3.getFirst()).longValue()) >= l);
        }
        list.removeIf(sVcsModification -> {
            boolean bl;
            try {
                bl = !hashSet.contains(sVcsModification.getId());
            }
            catch (NumberFormatException numberFormatException) {
                throw SingleBranchVcsTrigger.a(numberFormatException);
            }
            return bl;
        });
    }

    private boolean handleUnreachableLastProcessedChange(@NotNull TriggerStateUpdate triggerStateUpdate, @NotNull Map<Long, Long> map, @NotNull List<SVcsModification> list) {
        try {
            if (triggerStateUpdate == null) {
                SingleBranchVcsTrigger.$$$reportNull$$$0(10);
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw SingleBranchVcsTrigger.a(numberFormatException);
        }
        try {
            if (map == null) {
                SingleBranchVcsTrigger.$$$reportNull$$$0(11);
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw SingleBranchVcsTrigger.a(numberFormatException);
        }
        try {
            if (list == null) {
                SingleBranchVcsTrigger.$$$reportNull$$$0(12);
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw SingleBranchVcsTrigger.a(numberFormatException);
        }
        try {
            if (triggerStateUpdate.isPerRootState()) {
                return false;
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw SingleBranchVcsTrigger.a(numberFormatException);
        }
        boolean bl = false;
        for (Map.Entry<Long, Long> entry : map.entrySet()) {
            boolean bl2;
            SVcsModification sVcsModification;
            long l;
            block27: {
                block26: {
                    l = entry.getKey();
                    sVcsModification = (SVcsModification)triggerStateUpdate.getPreviousLastProcessedChange().get(l);
                    try {
                        if (!triggerStateUpdate.isLastProcessedModIdWasReset(l)) {
                            continue;
                        }
                    }
                    catch (NumberFormatException numberFormatException) {
                        throw SingleBranchVcsTrigger.a(numberFormatException);
                    }
                    try {
                        try {
                            if (sVcsModification != null && this.myState.getBranchSpecs().containsKey(sVcsModification.getVcsRoot())) break block26;
                        }
                        catch (NumberFormatException numberFormatException) {
                            throw SingleBranchVcsTrigger.a(numberFormatException);
                        }
                        bl2 = true;
                        break block27;
                    }
                    catch (NumberFormatException numberFormatException) {
                        throw SingleBranchVcsTrigger.a(numberFormatException);
                    }
                }
                bl2 = false;
            }
            boolean bl3 = bl2;
            boolean bl4 = false;
            if (sVcsModification != null) {
                for (SVcsModification sVcsModification2 : list) {
                    try {
                        if (sVcsModification2.getId() <= sVcsModification.getId() || sVcsModification2.getVcsRoot().getId() != l) continue;
                    }
                    catch (NumberFormatException numberFormatException) {
                        throw SingleBranchVcsTrigger.a(numberFormatException);
                    }
                    bl4 = true;
                    break;
                }
            }
            try {
                if (!bl3 || bl4) continue;
            }
            catch (NumberFormatException numberFormatException) {
                throw SingleBranchVcsTrigger.a(numberFormatException);
            }
            triggerStateUpdate.putLastProcessedModId(l, entry.getValue().longValue(), "The previously rememebered last processed change was set incorrectly: " + String.valueOf(sVcsModification));
            bl = true;
        }
        return bl;
    }

    @NotNull
    private BranchChangesCalculator.Changes getPendingChanges(@NotNull Logger logger, @NotNull VcsTriggerDescriptor vcsTriggerDescriptor, @NotNull TriggerStateUpdate triggerStateUpdate, @NotNull Collection<Long> collection) {
        BranchChangesCalculator.Changes changes;
        try {
            if (logger == null) {
                SingleBranchVcsTrigger.$$$reportNull$$$0(13);
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw SingleBranchVcsTrigger.a(numberFormatException);
        }
        try {
            if (vcsTriggerDescriptor == null) {
                SingleBranchVcsTrigger.$$$reportNull$$$0(14);
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw SingleBranchVcsTrigger.a(numberFormatException);
        }
        try {
            if (triggerStateUpdate == null) {
                SingleBranchVcsTrigger.$$$reportNull$$$0(15);
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw SingleBranchVcsTrigger.a(numberFormatException);
        }
        try {
            if (collection == null) {
                SingleBranchVcsTrigger.$$$reportNull$$$0(16);
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw SingleBranchVcsTrigger.a(numberFormatException);
        }
        BranchedParamState branchedParamState = this.getBranchedParamState();
        BranchChangesCalculator.Changes changes2 = this.myBranchChangesCalculator.getChanges(branchedParamState, this.getPrevBuildPolicy(this.myBuildType), vcsTriggerDescriptor.isWatchChangesInDependencies(), true, false);
        List<Long> list = SingleBranchVcsTrigger.getVcsRootsWithUnreachableLastProcessedModId(collection, branchedParamState, changes2);
        HashMap<Long, Long> hashMap = new HashMap<Long, Long>();
        if (!list.isEmpty()) {
            for (Long object2 : list) {
                triggerStateUpdate.putLastProcessedModId(object2.longValue(), 0L, "lastProcessedModId=" + branchedParamState.getModificationId(object2.longValue()) + " for VCS root id=" + object2 + " has become unreachable; visited change ids: " + String.valueOf(changes2.getMostRecentVisitedCommits()));
            }
            for (Long l : list) {
                hashMap.put(l, 0L);
            }
        }
        Iterator<Long> iterator = this.groupChangesByVcsRoots(changes2.getChanges());
        Map<VcsRootInstance, Long> map = this.findVcsRootsWithNewMergeCommits(changes2.getVisitedAndNewerThanLowerBoundsCommits(), (Predicate<VcsRootInstance>)((Predicate)vcsRootInstance -> iterator.getOrDefault(vcsRootInstance, Collections.emptyList()).isEmpty()));
        try {
            if (!map.isEmpty()) {
                logger.info("Detected new merge commits in some of the VCS roots: " + String.valueOf(map) + ", will re-calculate changes with lastProcessedModId=0 set for these VCS roots to ensure we did not miss any interesting changes; visited change ids: " + String.valueOf(changes2.getMostRecentVisitedCommits()));
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw SingleBranchVcsTrigger.a(numberFormatException);
        }
        for (Map.Entry<VcsRootInstance, Long> entry : map.entrySet()) {
            hashMap.put(entry.getKey().getId(), 0L);
        }
        if (!hashMap.isEmpty()) {
            Iterator<Object> iterator2 = collection.iterator();
            while (iterator2.hasNext()) {
                long l = (Long)iterator2.next();
                hashMap.putIfAbsent(l, branchedParamState.getModificationId(l));
            }
            changes2 = this.myBranchChangesCalculator.getChanges(BranchedParamState.createNewState((long)branchedParamState.getLastSeenTime(), hashMap), this.getPrevBuildPolicy(this.myBuildType), vcsTriggerDescriptor.isWatchChangesInDependencies(), false, false);
        }
        try {
            if (changes2.isEmpty()) {
                logger.debug("No pending changes found since mod id: " + String.valueOf(triggerStateUpdate.getLastProcessedModId()));
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw SingleBranchVcsTrigger.a(numberFormatException);
        }
        try {
            changes = changes2;
            if (changes == null) {
                SingleBranchVcsTrigger.$$$reportNull$$$0(17);
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw SingleBranchVcsTrigger.a(numberFormatException);
        }
        return changes;
    }

    @NotNull
    private Map<VcsRootInstance, Long> findVcsRootsWithNewMergeCommits(@NotNull Map<Long, TLongHashSet> map, @NotNull Predicate<VcsRootInstance> predicate) {
        HashMap<VcsRootInstance, Long> hashMap;
        try {
            if (map == null) {
                SingleBranchVcsTrigger.$$$reportNull$$$0(18);
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw SingleBranchVcsTrigger.a(numberFormatException);
        }
        try {
            if (predicate == null) {
                SingleBranchVcsTrigger.$$$reportNull$$$0(19);
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw SingleBranchVcsTrigger.a(numberFormatException);
        }
        HashMap<VcsRootInstance, Long> hashMap2 = new HashMap<VcsRootInstance, Long>();
        for (Map.Entry<Long, TLongHashSet> entry : map.entrySet()) {
            long l2 = entry.getKey();
            TLongHashSet tLongHashSet = entry.getValue();
            try {
                if (tLongHashSet.isEmpty()) {
                    continue;
                }
            }
            catch (NumberFormatException numberFormatException) {
                throw SingleBranchVcsTrigger.a(numberFormatException);
            }
            VcsRootInstance vcsRootInstance = this.myTriggeringContext.getVcsManager().findRootInstanceById(l2);
            try {
                if (!predicate.apply((Object)vcsRootInstance)) {
                    continue;
                }
            }
            catch (NumberFormatException numberFormatException) {
                throw SingleBranchVcsTrigger.a(numberFormatException);
            }
            if (vcsRootInstance == null) continue;
            try {
                if (!vcsRootInstance.isDagBased()) {
                    continue;
                }
            }
            catch (NumberFormatException numberFormatException) {
                throw SingleBranchVcsTrigger.a(numberFormatException);
            }
            DAG dAG = vcsRootInstance.getDag();
            tLongHashSet.forEach(l -> {
                List list = dAG.getParents((Object)l);
                try {
                    if (list.size() > 1) {
                        hashMap2.put(vcsRootInstance, l);
                        return false;
                    }
                }
                catch (NumberFormatException numberFormatException) {
                    throw SingleBranchVcsTrigger.a(numberFormatException);
                }
                return true;
            });
        }
        try {
            hashMap = hashMap2;
            if (hashMap == null) {
                SingleBranchVcsTrigger.$$$reportNull$$$0(20);
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw SingleBranchVcsTrigger.a(numberFormatException);
        }
        return hashMap;
    }

    @NotNull
    private Map<VcsRootInstance, List<SVcsModification>> groupChangesByVcsRoots(@NotNull List<SVcsModification> list) {
        HashMap<VcsRootInstance, List<SVcsModification>> hashMap;
        try {
            if (list == null) {
                SingleBranchVcsTrigger.$$$reportNull$$$0(21);
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw SingleBranchVcsTrigger.a(numberFormatException);
        }
        HashMap<VcsRootInstance, List<SVcsModification>> hashMap2 = new HashMap<VcsRootInstance, List<SVcsModification>>();
        for (SVcsModification sVcsModification : list) {
            hashMap2.computeIfAbsent(sVcsModification.getVcsRoot(), vcsRootInstance -> new ArrayList()).add(sVcsModification);
        }
        try {
            hashMap = hashMap2;
            if (hashMap == null) {
                SingleBranchVcsTrigger.$$$reportNull$$$0(22);
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw SingleBranchVcsTrigger.a(numberFormatException);
        }
        return hashMap;
    }

    @NotNull
    private static List<Long> getVcsRootsWithUnreachableLastProcessedModId(@NotNull Collection<Long> collection, @NotNull BranchedParamState branchedParamState, @NotNull BranchChangesCalculator.Changes changes) {
        ArrayList<Long> arrayList;
        try {
            if (collection == null) {
                SingleBranchVcsTrigger.$$$reportNull$$$0(23);
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw SingleBranchVcsTrigger.a(numberFormatException);
        }
        try {
            if (branchedParamState == null) {
                SingleBranchVcsTrigger.$$$reportNull$$$0(24);
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw SingleBranchVcsTrigger.a(numberFormatException);
        }
        try {
            if (changes == null) {
                SingleBranchVcsTrigger.$$$reportNull$$$0(25);
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw SingleBranchVcsTrigger.a(numberFormatException);
        }
        ArrayList<Long> arrayList2 = new ArrayList<Long>();
        try {
            collection.forEach(l -> {
                block6: {
                    try {
                        try {
                            try {
                                if (!TeamCityProperties.getBooleanOrTrue((String)"teamcity.vcsTrigger.checkLastProcessedModId.reachable") || branchedParamState.getModificationId(l.longValue()) <= 0L) break block6;
                            }
                            catch (NumberFormatException numberFormatException) {
                                throw SingleBranchVcsTrigger.a(numberFormatException);
                            }
                            if (changes.isLowerBoundReachable(l.longValue())) break block6;
                        }
                        catch (NumberFormatException numberFormatException) {
                            throw SingleBranchVcsTrigger.a(numberFormatException);
                        }
                        arrayList2.add((Long)l);
                    }
                    catch (NumberFormatException numberFormatException) {
                        throw SingleBranchVcsTrigger.a(numberFormatException);
                    }
                }
            });
            arrayList = arrayList2;
            if (arrayList == null) {
                SingleBranchVcsTrigger.$$$reportNull$$$0(26);
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw SingleBranchVcsTrigger.a(numberFormatException);
        }
        return arrayList;
    }

    @NotNull
    private BranchedParamState getBranchedParamState() {
        BranchedParamState branchedParamState;
        BranchedParamState branchedParamState2 = this.myState.getLastProcessedModId(this.myBranch.getName());
        BranchedParamState branchedParamState3 = this.myState.getLastTriggeredBy(this.myBranch.getName());
        try {
            branchedParamState = (BranchedParamState)Objects.ifNull((Object)branchedParamState2, (Object)((BranchedParamState)Objects.ifNull((Object)branchedParamState3, (Object)BranchedParamState.EMPTY_STATE)));
            if (branchedParamState == null) {
                SingleBranchVcsTrigger.$$$reportNull$$$0(27);
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw SingleBranchVcsTrigger.a(numberFormatException);
        }
        return branchedParamState;
    }

    private void adjustLastProcessedModId(@NotNull TriggerStateUpdate triggerStateUpdate, @NotNull Map<Long, Long> map) {
        try {
            if (triggerStateUpdate == null) {
                SingleBranchVcsTrigger.$$$reportNull$$$0(28);
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw SingleBranchVcsTrigger.a(numberFormatException);
        }
        try {
            if (map == null) {
                SingleBranchVcsTrigger.$$$reportNull$$$0(29);
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw SingleBranchVcsTrigger.a(numberFormatException);
        }
        map.forEach((l, l2) -> {
            block4: {
                try {
                    try {
                        if (l2 < 0L || l2.equals(triggerStateUpdate.getLastProcessedModId().getOrDefault(l, -1L))) break block4;
                    }
                    catch (NumberFormatException numberFormatException) {
                        throw SingleBranchVcsTrigger.a(numberFormatException);
                    }
                    triggerStateUpdate.putLastProcessedModId(l.longValue(), l2.longValue(), "lastProcessedModId is set to the most recently visited modification id: " + l2 + " for VCS root id: " + l);
                }
                catch (NumberFormatException numberFormatException) {
                    throw SingleBranchVcsTrigger.a(numberFormatException);
                }
            }
        });
    }

    private boolean handleUnknownBranch(@NotNull VcsTriggerContext vcsTriggerContext, @NotNull Logger logger, @NotNull TriggerStateUpdate triggerStateUpdate, @NotNull BranchChangesCalculator.Changes changes) {
        BranchStateCalculator.BranchState branchState;
        block27: {
            VcsRootInstance vcsRootInstance;
            try {
                if (vcsTriggerContext == null) {
                    SingleBranchVcsTrigger.$$$reportNull$$$0(30);
                }
            }
            catch (NumberFormatException numberFormatException) {
                throw SingleBranchVcsTrigger.a(numberFormatException);
            }
            try {
                if (logger == null) {
                    SingleBranchVcsTrigger.$$$reportNull$$$0(31);
                }
            }
            catch (NumberFormatException numberFormatException) {
                throw SingleBranchVcsTrigger.a(numberFormatException);
            }
            try {
                if (triggerStateUpdate == null) {
                    SingleBranchVcsTrigger.$$$reportNull$$$0(32);
                }
            }
            catch (NumberFormatException numberFormatException) {
                throw SingleBranchVcsTrigger.a(numberFormatException);
            }
            try {
                if (changes == null) {
                    SingleBranchVcsTrigger.$$$reportNull$$$0(33);
                }
            }
            catch (NumberFormatException numberFormatException) {
                throw SingleBranchVcsTrigger.a(numberFormatException);
            }
            logger.info("The branch has not been known before");
            branchState = this.myBranchStateCalculator.getBranchState(this.myState.getBranchSpecs(), changes, logger);
            try {
                if (branchState == BranchStateCalculator.BranchState.OLD_BRANCH) {
                    return true;
                }
            }
            catch (NumberFormatException numberFormatException) {
                throw SingleBranchVcsTrigger.a(numberFormatException);
            }
            List<VcsRootInstance> list = this.getVcsRootsWithCurrentBranch();
            try {
                vcsRootInstance = list.isEmpty() ? null : list.getFirst();
            }
            catch (NumberFormatException numberFormatException) {
                throw SingleBranchVcsTrigger.a(numberFormatException);
            }
            VcsRootInstance vcsRootInstance2 = vcsRootInstance;
            try {
                if (vcsRootInstance2 == null) {
                    triggerStateUpdate.markBranchAsOld();
                }
            }
            catch (NumberFormatException numberFormatException) {
                throw SingleBranchVcsTrigger.a(numberFormatException);
            }
            try {
                try {
                    if (vcsRootInstance2 == null || !this.triggerBuildInNewEmptyBranch(this.myBuildType)) break block27;
                }
                catch (NumberFormatException numberFormatException) {
                    throw SingleBranchVcsTrigger.a(numberFormatException);
                }
                if (!this.isEmptyOrContainsOnlySettingsChanges(changes.getChangesFromVcsRootsWithoutDefaultBranchFallback(), vcsTriggerContext)) break block27;
            }
            catch (NumberFormatException numberFormatException) {
                throw SingleBranchVcsTrigger.a(numberFormatException);
            }
            BuildPromotionEx buildPromotionEx = this.createBuildPromotion(vcsTriggerContext);
            logger.info("Enqueue " + LogUtil.describe((Object)buildPromotionEx) + " reason: new branch '" + this.myBranch.getName() + "' is detected in VCS root " + LogUtil.describe((Object)vcsRootInstance2));
            this.myBuildType.addToQueue(buildPromotionEx, this.getRequestorForNewBranch(vcsRootInstance2, vcsTriggerContext).toString());
            this.myBuildTriggered = true;
            triggerStateUpdate.markBranchAsOld();
            return true;
        }
        try {
            if (branchState == BranchStateCalculator.BranchState.UNKNOWN) {
                changes.retainNLatestChanges(TeamCityProperties.getInteger((String)"teamcity.vcsTrigger.unknownNewBranch.maxChangesToHandlePerVcsRoot", (int)1000));
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw SingleBranchVcsTrigger.a(numberFormatException);
        }
        return false;
    }

    private boolean isEmptyOrContainsOnlySettingsChanges(@NotNull List<SVcsModification> list, @NotNull VcsTriggerContext vcsTriggerContext) {
        try {
            if (list == null) {
                SingleBranchVcsTrigger.$$$reportNull$$$0(34);
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw SingleBranchVcsTrigger.a(numberFormatException);
        }
        try {
            if (vcsTriggerContext == null) {
                SingleBranchVcsTrigger.$$$reportNull$$$0(35);
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw SingleBranchVcsTrigger.a(numberFormatException);
        }
        try {
            if (list.isEmpty()) {
                return true;
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw SingleBranchVcsTrigger.a(numberFormatException);
        }
        Set set = vcsTriggerContext.getSettingsRootIdsToBeExcluded();
        for (SVcsModification sVcsModification : list) {
            try {
                if (set.contains(sVcsModification.getVcsRoot().getId())) continue;
                return false;
            }
            catch (NumberFormatException numberFormatException) {
                throw SingleBranchVcsTrigger.a(numberFormatException);
            }
        }
        return true;
    }

    private boolean checkTriggeredInObsoleteBranchOnTheSameCommit(@NotNull VcsTriggerDescriptor vcsTriggerDescriptor, Map<Long, Long> map, @NotNull Logger logger) {
        try {
            if (vcsTriggerDescriptor == null) {
                SingleBranchVcsTrigger.$$$reportNull$$$0(36);
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw SingleBranchVcsTrigger.a(numberFormatException);
        }
        try {
            if (logger == null) {
                SingleBranchVcsTrigger.$$$reportNull$$$0(37);
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw SingleBranchVcsTrigger.a(numberFormatException);
        }
        String string = null;
        int n = TeamCityProperties.getInteger((String)"teamcity.vcsTrigger.branchNameChangeDetector.minCommonPartSize", (int)3);
        for (Map.Entry entry2 : this.myState.getLastTriggeredByMap().entrySet()) {
            String string2;
            block14: {
                BranchedParamState branchedParamState = (BranchedParamState)entry2.getValue();
                boolean bl = map.entrySet().stream().allMatch(entry -> {
                    boolean bl;
                    try {
                        bl = branchedParamState.getModificationId(((Long)entry.getKey()).longValue()) == ((Long)entry.getValue()).longValue();
                    }
                    catch (NumberFormatException numberFormatException) {
                        throw SingleBranchVcsTrigger.a(numberFormatException);
                    }
                    return bl;
                });
                if (!bl) continue;
                string2 = (String)entry2.getKey();
                try {
                    try {
                        if (!string2.equals(this.myBranch.getName()) && !this.myBuildType.isValidVcsBranch(string2, vcsTriggerDescriptor.isWatchChangesInDependencies())) break block14;
                    }
                    catch (NumberFormatException numberFormatException) {
                        throw SingleBranchVcsTrigger.a(numberFormatException);
                    }
                    return false;
                }
                catch (NumberFormatException numberFormatException) {
                    throw SingleBranchVcsTrigger.a(numberFormatException);
                }
            }
            if (!this.branchNameIntersects(this.myBranch.getName(), string2, n)) continue;
            string = string2;
        }
        try {
            if (string != null) {
                logger.info("VCS trigger already triggered a build on the changes: " + String.valueOf(map) + " in branch: " + string + " which is no longer valid, skip triggering");
                return true;
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw SingleBranchVcsTrigger.a(numberFormatException);
        }
        return false;
    }

    private boolean branchNameIntersects(@NotNull String string, @NotNull String string2, int n) {
        boolean bl;
        block33: {
            block32: {
                block31: {
                    block30: {
                        block29: {
                            block28: {
                                try {
                                    if (string == null) {
                                        SingleBranchVcsTrigger.$$$reportNull$$$0(38);
                                    }
                                }
                                catch (NumberFormatException numberFormatException) {
                                    throw SingleBranchVcsTrigger.a(numberFormatException);
                                }
                                try {
                                    if (string2 == null) {
                                        SingleBranchVcsTrigger.$$$reportNull$$$0(39);
                                    }
                                }
                                catch (NumberFormatException numberFormatException) {
                                    throw SingleBranchVcsTrigger.a(numberFormatException);
                                }
                                try {
                                    try {
                                        if (string.length() >= n && string2.length() >= n) break block28;
                                    }
                                    catch (NumberFormatException numberFormatException) {
                                        throw SingleBranchVcsTrigger.a(numberFormatException);
                                    }
                                    return false;
                                }
                                catch (NumberFormatException numberFormatException) {
                                    throw SingleBranchVcsTrigger.a(numberFormatException);
                                }
                            }
                            try {
                                try {
                                    if (!string.contains(string2) && !string2.contains(string)) break block29;
                                }
                                catch (NumberFormatException numberFormatException) {
                                    throw SingleBranchVcsTrigger.a(numberFormatException);
                                }
                                return true;
                            }
                            catch (NumberFormatException numberFormatException) {
                                throw SingleBranchVcsTrigger.a(numberFormatException);
                            }
                        }
                        CharSequence charSequence = this.getNumbers(string);
                        CharSequence charSequence2 = this.getNumbers(string2);
                        try {
                            try {
                                try {
                                    if (charSequence.length() <= 0 && charSequence2.length() <= 0) break block30;
                                }
                                catch (NumberFormatException numberFormatException) {
                                    throw SingleBranchVcsTrigger.a(numberFormatException);
                                }
                                if (charSequence.equals(charSequence2)) break block30;
                            }
                            catch (NumberFormatException numberFormatException) {
                                throw SingleBranchVcsTrigger.a(numberFormatException);
                            }
                            return false;
                        }
                        catch (NumberFormatException numberFormatException) {
                            throw SingleBranchVcsTrigger.a(numberFormatException);
                        }
                    }
                    try {
                        try {
                            if (!string.startsWith(string2.substring(0, n)) && !string.endsWith(string2.substring(string2.length() - n))) break block31;
                        }
                        catch (NumberFormatException numberFormatException) {
                            throw SingleBranchVcsTrigger.a(numberFormatException);
                        }
                        return true;
                    }
                    catch (NumberFormatException numberFormatException) {
                        throw SingleBranchVcsTrigger.a(numberFormatException);
                    }
                }
                try {
                    try {
                        if (!string2.startsWith(string.substring(0, n)) && !string2.endsWith(string.substring(string.length() - n))) break block32;
                    }
                    catch (NumberFormatException numberFormatException) {
                        throw SingleBranchVcsTrigger.a(numberFormatException);
                    }
                    bl = true;
                    break block33;
                }
                catch (NumberFormatException numberFormatException) {
                    throw SingleBranchVcsTrigger.a(numberFormatException);
                }
            }
            bl = false;
        }
        return bl;
    }

    @NotNull
    private CharSequence getNumbers(@NotNull String string) {
        StringBuilder stringBuilder;
        try {
            if (string == null) {
                SingleBranchVcsTrigger.$$$reportNull$$$0(40);
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw SingleBranchVcsTrigger.a(numberFormatException);
        }
        StringBuilder stringBuilder2 = new StringBuilder();
        for (int i = string.length() - 1; i >= 0; --i) {
            char c = string.charAt(i);
            try {
                if (!Character.isDigit(c)) {
                    continue;
                }
            }
            catch (NumberFormatException numberFormatException) {
                throw SingleBranchVcsTrigger.a(numberFormatException);
            }
            stringBuilder2.append(c);
        }
        try {
            stringBuilder = stringBuilder2;
            if (stringBuilder == null) {
                SingleBranchVcsTrigger.$$$reportNull$$$0(41);
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw SingleBranchVcsTrigger.a(numberFormatException);
        }
        return stringBuilder;
    }

    @NotNull
    private SVcsModification getLastChange(@NotNull List<SVcsModification> list, @NotNull VcsTriggerDescriptor vcsTriggerDescriptor) {
        SVcsModification sVcsModification;
        List list2;
        block26: {
            SVcsModification sVcsModification2;
            block27: {
                SVcsModification sVcsModification3;
                block24: {
                    SVcsModification sVcsModification4;
                    block25: {
                        block23: {
                            try {
                                if (list == null) {
                                    SingleBranchVcsTrigger.$$$reportNull$$$0(42);
                                }
                            }
                            catch (NumberFormatException numberFormatException) {
                                throw SingleBranchVcsTrigger.a(numberFormatException);
                            }
                            try {
                                if (vcsTriggerDescriptor == null) {
                                    SingleBranchVcsTrigger.$$$reportNull$$$0(43);
                                }
                            }
                            catch (NumberFormatException numberFormatException) {
                                throw SingleBranchVcsTrigger.a(numberFormatException);
                            }
                            try {
                                try {
                                    if ($assertionsDisabled || !list.isEmpty()) break block23;
                                }
                                catch (NumberFormatException numberFormatException) {
                                    throw SingleBranchVcsTrigger.a(numberFormatException);
                                }
                                throw new AssertionError();
                            }
                            catch (NumberFormatException numberFormatException) {
                                throw SingleBranchVcsTrigger.a(numberFormatException);
                            }
                        }
                        sVcsModification3 = list.getFirst();
                        try {
                            try {
                                try {
                                    if (vcsTriggerDescriptor.isQuietPeriodEnabled() && !this.myBranch.isDefaultBranch()) break block24;
                                }
                                catch (NumberFormatException numberFormatException) {
                                    throw SingleBranchVcsTrigger.a(numberFormatException);
                                }
                                sVcsModification4 = sVcsModification3;
                                if (sVcsModification4 != null) break block25;
                            }
                            catch (NumberFormatException numberFormatException) {
                                throw SingleBranchVcsTrigger.a(numberFormatException);
                            }
                            SingleBranchVcsTrigger.$$$reportNull$$$0(44);
                        }
                        catch (NumberFormatException numberFormatException) {
                            throw SingleBranchVcsTrigger.a(numberFormatException);
                        }
                    }
                    return sVcsModification4;
                }
                list2 = this.myBranchChangesCalculator.getChanges(BranchedParamState.createNewState((long)-1L, (long)sVcsModification3.getId()), SelectPrevBuildPolicy.SINCE_NULL_BUILD, vcsTriggerDescriptor.isWatchChangesInDependencies(), false, false).getChanges();
                try {
                    try {
                        if (!list2.isEmpty()) break block26;
                        sVcsModification2 = sVcsModification3;
                        if (sVcsModification2 != null) break block27;
                    }
                    catch (NumberFormatException numberFormatException) {
                        throw SingleBranchVcsTrigger.a(numberFormatException);
                    }
                    SingleBranchVcsTrigger.$$$reportNull$$$0(45);
                }
                catch (NumberFormatException numberFormatException) {
                    throw SingleBranchVcsTrigger.a(numberFormatException);
                }
            }
            return sVcsModification2;
        }
        try {
            sVcsModification = (SVcsModification)list2.getFirst();
            if (sVcsModification == null) {
                SingleBranchVcsTrigger.$$$reportNull$$$0(46);
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw SingleBranchVcsTrigger.a(numberFormatException);
        }
        return sVcsModification;
    }

    private int getQuietPeriodTimeout(@NotNull VcsTriggerDescriptor vcsTriggerDescriptor) {
        try {
            if (vcsTriggerDescriptor == null) {
                SingleBranchVcsTrigger.$$$reportNull$$$0(47);
            }
        }
        catch (InvalidQuietPeriodException invalidQuietPeriodException) {
            throw SingleBranchVcsTrigger.a((Exception)((Object)invalidQuietPeriodException));
        }
        try {
            int n;
            try {
                n = vcsTriggerDescriptor.isQuietPeriodEnabled() ? vcsTriggerDescriptor.getQuietPeriod() : 0;
            }
            catch (InvalidQuietPeriodException invalidQuietPeriodException) {
                throw SingleBranchVcsTrigger.a((Exception)((Object)invalidQuietPeriodException));
            }
            return n;
        }
        catch (InvalidQuietPeriodException invalidQuietPeriodException) {
            throw new BuildTriggerException(invalidQuietPeriodException.getMessage());
        }
    }

    private boolean isAnalyzeFullHistoryForMergeCommits(@NotNull BuildTypeEx buildTypeEx) {
        try {
            if (buildTypeEx == null) {
                SingleBranchVcsTrigger.$$$reportNull$$$0(48);
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw SingleBranchVcsTrigger.a(numberFormatException);
        }
        return buildTypeEx.getBooleanInternalParameterOrTrue("vcsTrigger.analyzeFullHistoryForMergeCommits");
    }

    private int getMergedBranchesThreshold() {
        String string = this.myBuildType.getInternalParameterValue("vcsTrigger.mergedBranchesThreshold", String.valueOf(1));
        try {
            return Integer.parseInt(string);
        }
        catch (NumberFormatException numberFormatException) {
            return 1;
        }
    }

    private boolean isQuietPeriodPassed(@NotNull SVcsModification sVcsModification, int n) {
        boolean bl;
        try {
            if (sVcsModification == null) {
                SingleBranchVcsTrigger.$$$reportNull$$$0(49);
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw SingleBranchVcsTrigger.a(numberFormatException);
        }
        Date date = sVcsModification.getRegistrationDate();
        try {
            bl = this.myTimeService.now() - date.getTime() >= 1000L * (long)n;
        }
        catch (NumberFormatException numberFormatException) {
            throw SingleBranchVcsTrigger.a(numberFormatException);
        }
        return bl;
    }

    private boolean triggerBuildInNewEmptyBranch(@NotNull BuildTypeEx buildTypeEx) {
        try {
            if (buildTypeEx == null) {
                SingleBranchVcsTrigger.$$$reportNull$$$0(50);
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw SingleBranchVcsTrigger.a(numberFormatException);
        }
        return buildTypeEx.getBooleanInternalParameter("vcsTrigger.runBuildInNewEmptyBranch");
    }

    @NotNull
    private List<VcsRootInstance> getVcsRootsWithCurrentBranch() {
        ArrayList<VcsRootInstance> arrayList;
        Map map = BranchStateCalculator.computeVcsBranchNames((Branch)this.myBranch, (Map)this.myState.getBranchSpecs());
        ArrayList<VcsRootInstance> arrayList2 = new ArrayList<VcsRootInstance>(map.keySet());
        try {
            arrayList2.sort(Comparator.comparingLong(VcsRoot::getId));
            arrayList = arrayList2;
            if (arrayList == null) {
                SingleBranchVcsTrigger.$$$reportNull$$$0(51);
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw SingleBranchVcsTrigger.a(numberFormatException);
        }
        return arrayList;
    }

    private BuildPromotionEx createBuildPromotion(VcsTriggerContext vcsTriggerContext) {
        BuildCustomizer buildCustomizer = vcsTriggerContext.createBuildCustomizer(null);
        buildCustomizer.setDesiredBranchName(this.myBranch.getName());
        return (BuildPromotionEx)buildCustomizer.createPromotion();
    }

    public void triggerActivated(@NotNull Logger logger) {
        LoggerWithPrefix loggerWithPrefix;
        block7: {
            block8: {
                try {
                    if (logger == null) {
                        SingleBranchVcsTrigger.$$$reportNull$$$0(52);
                    }
                }
                catch (NumberFormatException numberFormatException) {
                    throw SingleBranchVcsTrigger.a(numberFormatException);
                }
                loggerWithPrefix = new LoggerWithPrefix("[" + this.myBranch.getName() + "] ", logger);
                try {
                    try {
                        if (!this.myState.hasStateForBranch(this.myBranch.getName())) break block7;
                        if (!logger.isDebugEnabled()) break block8;
                    }
                    catch (NumberFormatException numberFormatException) {
                        throw SingleBranchVcsTrigger.a(numberFormatException);
                    }
                    loggerWithPrefix.debug("Skip initialization of VCS trigger state for the branch because it is already initialized");
                }
                catch (NumberFormatException numberFormatException) {
                    throw SingleBranchVcsTrigger.a(numberFormatException);
                }
            }
            return;
        }
        BranchChangesCalculator.Changes changes = this.myBranchChangesCalculator.getChanges(BranchedParamState.EMPTY_STATE, this.getPrevBuildPolicy(this.myBuildType), this.myTriggerDescriptor.isWatchChangesInDependencies(), false, true);
        this.myState.updateStateForBranch(this.myBranch.getName(), changes.getMostRecentVisitedCommits(), changes.getMostRecentVisitedCommits(), false, (Logger)loggerWithPrefix);
    }

    private SelectPrevBuildPolicy getPrevBuildPolicy(@NotNull BuildTypeEx buildTypeEx) {
        SelectPrevBuildPolicy selectPrevBuildPolicy;
        try {
            if (buildTypeEx == null) {
                SingleBranchVcsTrigger.$$$reportNull$$$0(53);
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw SingleBranchVcsTrigger.a(numberFormatException);
        }
        boolean bl = this.getRunBuildInEveryBranchBuildTypeSetting(buildTypeEx);
        try {
            selectPrevBuildPolicy = bl ? SelectPrevBuildPolicy.SINCE_LAST_BUILD : SelectPrevBuildPolicy.SINCE_LAST_BUILD_IN_ANY_BRANCH;
        }
        catch (NumberFormatException numberFormatException) {
            throw SingleBranchVcsTrigger.a(numberFormatException);
        }
        return selectPrevBuildPolicy;
    }

    private boolean getRunBuildInEveryBranchBuildTypeSetting(@NotNull BuildTypeEx buildTypeEx) {
        try {
            if (buildTypeEx == null) {
                SingleBranchVcsTrigger.$$$reportNull$$$0(54);
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw SingleBranchVcsTrigger.a(numberFormatException);
        }
        String string = buildTypeEx.getInternalParameterValue("vcsTrigger.runBuildOnSameRevisionInEveryBranch", "true");
        try {
            if ("true".equals(string)) {
                return true;
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw SingleBranchVcsTrigger.a(numberFormatException);
        }
        return Boolean.parseBoolean(buildTypeEx.getValueResolver().resolve(string).getResult());
    }

    private void filterOutUnnecessaryCheckins(@NotNull List<SVcsModification> list) {
        try {
            if (list == null) {
                SingleBranchVcsTrigger.$$$reportNull$$$0(55);
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw SingleBranchVcsTrigger.a(numberFormatException);
        }
        SVcsModification sVcsModification = null;
        Iterator<SVcsModification> iterator = list.iterator();
        while (iterator.hasNext()) {
            SVcsModification sVcsModification2 = iterator.next();
            if (sVcsModification == null) {
                sVcsModification = sVcsModification2;
                continue;
            }
            try {
                if (this.isSameCommitter(sVcsModification, sVcsModification2)) {
                    iterator.remove();
                    continue;
                }
            }
            catch (NumberFormatException numberFormatException) {
                throw SingleBranchVcsTrigger.a(numberFormatException);
            }
            sVcsModification = sVcsModification2;
        }
    }

    private boolean isSameCommitter(@NotNull SVcsModification sVcsModification, @NotNull SVcsModification sVcsModification2) {
        boolean bl;
        Object object;
        Object object2;
        block13: {
            try {
                if (sVcsModification == null) {
                    SingleBranchVcsTrigger.$$$reportNull$$$0(56);
                }
            }
            catch (NumberFormatException numberFormatException) {
                throw SingleBranchVcsTrigger.a(numberFormatException);
            }
            try {
                if (sVcsModification2 == null) {
                    SingleBranchVcsTrigger.$$$reportNull$$$0(57);
                }
            }
            catch (NumberFormatException numberFormatException) {
                throw SingleBranchVcsTrigger.a(numberFormatException);
            }
            if (sVcsModification.getVcsRoot().getId() == sVcsModification2.getVcsRoot().getId()) {
                object2 = sVcsModification.getUserName();
                object = sVcsModification2.getUserName();
                try {
                    try {
                        if (object2 == null || !((String)object2).equals(object)) break block13;
                    }
                    catch (NumberFormatException numberFormatException) {
                        throw SingleBranchVcsTrigger.a(numberFormatException);
                    }
                    return true;
                }
                catch (NumberFormatException numberFormatException) {
                    throw SingleBranchVcsTrigger.a(numberFormatException);
                }
            }
        }
        object2 = sVcsModification.getCommitterIds();
        object = sVcsModification2.getCommitterIds();
        HashSet hashSet = new HashSet(object2);
        try {
            hashSet.addAll(object);
            bl = hashSet.size() < object2.size() + object.size();
        }
        catch (NumberFormatException numberFormatException) {
            throw SingleBranchVcsTrigger.a(numberFormatException);
        }
        return bl;
    }

    private boolean isAllVcsRootsCheckedForChanges(@NotNull VcsTriggerContext vcsTriggerContext, @NotNull SVcsModification sVcsModification, int n) {
        boolean bl;
        block42: {
            try {
                if (vcsTriggerContext == null) {
                    SingleBranchVcsTrigger.$$$reportNull$$$0(58);
                }
            }
            catch (NumberFormatException numberFormatException) {
                throw SingleBranchVcsTrigger.a(numberFormatException);
            }
            try {
                if (sVcsModification == null) {
                    SingleBranchVcsTrigger.$$$reportNull$$$0(59);
                }
            }
            catch (NumberFormatException numberFormatException) {
                throw SingleBranchVcsTrigger.a(numberFormatException);
            }
            HashSet<VcsRootInstanceEx> hashSet = new HashSet<VcsRootInstanceEx>();
            bl = true;
            for (VcsRootInstance vcsRootInstance : vcsTriggerContext.getRootsInChain()) {
                block38: {
                    VcsRootInstanceEx vcsRootInstanceEx;
                    block39: {
                        String string;
                        block41: {
                            Date date;
                            block40: {
                                boolean bl2;
                                block37: {
                                    block36: {
                                        vcsRootInstanceEx = (VcsRootInstanceEx)vcsRootInstance;
                                        try {
                                            try {
                                                if (n <= vcsRootInstanceEx.getEffectiveModificationCheckInterval() && n != 0) break block36;
                                            }
                                            catch (NumberFormatException numberFormatException) {
                                                throw SingleBranchVcsTrigger.a(numberFormatException);
                                            }
                                            bl2 = true;
                                            break block37;
                                        }
                                        catch (NumberFormatException numberFormatException) {
                                            throw SingleBranchVcsTrigger.a(numberFormatException);
                                        }
                                    }
                                    bl2 = false;
                                }
                                boolean bl3 = bl2;
                                try {
                                    if (bl3) {
                                        this.getSlowRootTracker().removeRoot((SBuildType)this.myBuildType, vcsRootInstance);
                                    }
                                }
                                catch (NumberFormatException numberFormatException) {
                                    throw SingleBranchVcsTrigger.a(numberFormatException);
                                }
                                try {
                                    if (!vcsRootInstanceEx.isPollingMode()) {
                                        this.getSlowRootTracker().removeRoot((SBuildType)this.myBuildType, (VcsRootInstance)vcsRootInstanceEx);
                                        continue;
                                    }
                                }
                                catch (NumberFormatException numberFormatException) {
                                    throw SingleBranchVcsTrigger.a(numberFormatException);
                                }
                                try {
                                    if (sVcsModification.getVcsRoot().equals((Object)vcsRootInstance)) {
                                        continue;
                                    }
                                }
                                catch (NumberFormatException numberFormatException) {
                                    throw SingleBranchVcsTrigger.a(numberFormatException);
                                }
                                date = vcsRootInstanceEx.getLastFinishChangesCollectingTime();
                                try {
                                    try {
                                        try {
                                            try {
                                                try {
                                                    try {
                                                        if (date != null && sVcsModification.getRegistrationDate().getTime() <= date.getTime()) continue;
                                                    }
                                                    catch (NumberFormatException numberFormatException) {
                                                        throw SingleBranchVcsTrigger.a(numberFormatException);
                                                    }
                                                    if (bl3) break block38;
                                                }
                                                catch (NumberFormatException numberFormatException) {
                                                    throw SingleBranchVcsTrigger.a(numberFormatException);
                                                }
                                                this.getSlowRootTracker().addRootWithLargeChangesCheckingInterval((SBuildType)this.myBuildType, (VcsRootInstance)vcsRootInstanceEx);
                                                if (!this.isForceCheckingForChangesForSlowRoots()) break block39;
                                            }
                                            catch (NumberFormatException numberFormatException) {
                                                throw SingleBranchVcsTrigger.a(numberFormatException);
                                            }
                                            if (!this.getSlowRootTracker().shouldForceChangesChecking(vcsRootInstanceEx)) break block38;
                                        }
                                        catch (NumberFormatException numberFormatException) {
                                            throw SingleBranchVcsTrigger.a(numberFormatException);
                                        }
                                        hashSet.add(vcsRootInstanceEx);
                                        if (date != null) break block40;
                                    }
                                    catch (NumberFormatException numberFormatException) {
                                        throw SingleBranchVcsTrigger.a(numberFormatException);
                                    }
                                    string = "<never>";
                                    break block41;
                                }
                                catch (NumberFormatException numberFormatException) {
                                    throw SingleBranchVcsTrigger.a(numberFormatException);
                                }
                            }
                            string = StringUtil.dateToString((Date)date);
                        }
                        String string2 = string;
                        vcsTriggerContext.getLogger().info("Forcing the checking for changes for VCS root " + String.valueOf(vcsRootInstanceEx) + " because it was not checked for changes during the quiet period (" + n + "), last checking for changes time: " + string2);
                        break block38;
                    }
                    this.getSlowRootTracker().removeFromForcedChecks((VcsRootInstance)vcsRootInstanceEx);
                }
                bl = false;
            }
            try {
                try {
                    if (!this.isForceCheckingForChangesForSlowRoots() || hashSet.isEmpty()) break block42;
                }
                catch (NumberFormatException numberFormatException) {
                    throw SingleBranchVcsTrigger.a(numberFormatException);
                }
                this.myTriggeringContext.getChangesCheckingService().forceCheckingFor(hashSet, OperationRequestor.VCS_TRIGGER_QUIET_PERIOD);
                vcsTriggerContext.getLogger().info("Force checking for changes for " + hashSet.size() + " VCS roots");
                Loggers.VCS.info("Force checking for changes from VCS trigger with id " + vcsTriggerContext.getTriggerDescriptor().getId() + " in " + LogUtil.describe((Object)vcsTriggerContext.getBuildType()) + " for " + hashSet.size() + " VCS roots");
            }
            catch (NumberFormatException numberFormatException) {
                throw SingleBranchVcsTrigger.a(numberFormatException);
            }
        }
        return bl;
    }

    private boolean isForceCheckingForChangesForSlowRoots() {
        return TeamCityProperties.getBooleanOrTrue((String)"teamcity.vcsTrigger.forceChangesCheckingForQuietPeriod");
    }

    private SlowVcsRootInstanceTracker getSlowRootTracker() {
        return this.myTriggeringContext.getSlowVcsRootInstanceTracker();
    }

    @NotNull
    private List<RunBuildDecision> prepareBuilds(@NotNull VcsTriggerDescriptor vcsTriggerDescriptor, @NotNull List<SVcsModification> list, @NotNull SVcsModification sVcsModification, @NotNull SVcsModification sVcsModification2, @NotNull TriggerRulesFilter triggerRulesFilter, @NotNull VcsTriggerContext vcsTriggerContext, @NotNull TriggerStateUpdate triggerStateUpdate, @NotNull List<SVcsModification> list2) {
        List<RunBuildDecision> list3;
        boolean bl;
        block39: {
            block38: {
                try {
                    if (vcsTriggerDescriptor == null) {
                        SingleBranchVcsTrigger.$$$reportNull$$$0(60);
                    }
                }
                catch (NumberFormatException numberFormatException) {
                    throw SingleBranchVcsTrigger.a(numberFormatException);
                }
                try {
                    if (list == null) {
                        SingleBranchVcsTrigger.$$$reportNull$$$0(61);
                    }
                }
                catch (NumberFormatException numberFormatException) {
                    throw SingleBranchVcsTrigger.a(numberFormatException);
                }
                try {
                    if (sVcsModification == null) {
                        SingleBranchVcsTrigger.$$$reportNull$$$0(62);
                    }
                }
                catch (NumberFormatException numberFormatException) {
                    throw SingleBranchVcsTrigger.a(numberFormatException);
                }
                try {
                    if (sVcsModification2 == null) {
                        SingleBranchVcsTrigger.$$$reportNull$$$0(63);
                    }
                }
                catch (NumberFormatException numberFormatException) {
                    throw SingleBranchVcsTrigger.a(numberFormatException);
                }
                try {
                    if (triggerRulesFilter == null) {
                        SingleBranchVcsTrigger.$$$reportNull$$$0(64);
                    }
                }
                catch (NumberFormatException numberFormatException) {
                    throw SingleBranchVcsTrigger.a(numberFormatException);
                }
                try {
                    if (vcsTriggerContext == null) {
                        SingleBranchVcsTrigger.$$$reportNull$$$0(65);
                    }
                }
                catch (NumberFormatException numberFormatException) {
                    throw SingleBranchVcsTrigger.a(numberFormatException);
                }
                try {
                    if (triggerStateUpdate == null) {
                        SingleBranchVcsTrigger.$$$reportNull$$$0(66);
                    }
                }
                catch (NumberFormatException numberFormatException) {
                    throw SingleBranchVcsTrigger.a(numberFormatException);
                }
                try {
                    if (list2 == null) {
                        SingleBranchVcsTrigger.$$$reportNull$$$0(67);
                    }
                }
                catch (NumberFormatException numberFormatException) {
                    throw SingleBranchVcsTrigger.a(numberFormatException);
                }
                try {
                    try {
                        if (vcsTriggerDescriptor.isQueueOptimizationEnabled() && !vcsTriggerDescriptor.isPerCheckinTriggeringEnabled()) break block38;
                    }
                    catch (NumberFormatException numberFormatException) {
                        throw SingleBranchVcsTrigger.a(numberFormatException);
                    }
                    bl = true;
                    break block39;
                }
                catch (NumberFormatException numberFormatException) {
                    throw SingleBranchVcsTrigger.a(numberFormatException);
                }
            }
            bl = false;
        }
        boolean bl2 = bl;
        try {
            if (vcsTriggerDescriptor.isPerCheckinTriggeringEnabled()) {
                return this.prepareBuilds(list, bl2, sVcsModification2, triggerRulesFilter, vcsTriggerContext, triggerStateUpdate, list2);
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw SingleBranchVcsTrigger.a(numberFormatException);
        }
        try {
            if (vcsTriggerDescriptor.isQuietPeriodEnabled()) {
                return this.prepareBuilds(Collections.singletonList(sVcsModification), bl2, sVcsModification2, triggerRulesFilter, vcsTriggerContext, triggerStateUpdate, list2);
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw SingleBranchVcsTrigger.a(numberFormatException);
        }
        String string = this.getRequestor(sVcsModification2, bl2, vcsTriggerContext);
        BuildPromotionEx buildPromotionEx = this.createBuildPromotion(vcsTriggerContext);
        String string2 = "Enqueue " + LogUtil.describe((Object)buildPromotionEx) + " reason: pending changes detected, the last change accepted by trigger rules " + LogUtil.describe((Object)sVcsModification2) + ", trigger rules: " + String.valueOf(triggerRulesFilter.getTriggerRules());
        String string3 = triggerRulesFilter.getMatchDetails(sVcsModification2);
        if (string3 != null) {
            string2 = string2 + " (" + string3 + ")";
        }
        try {
            list3 = Collections.singletonList(new RunBuildDecision(this, buildPromotionEx, string, string2, false, vcsTriggerContext.getLogger((Branch)this.myBranch)));
            if (list3 == null) {
                SingleBranchVcsTrigger.$$$reportNull$$$0(68);
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw SingleBranchVcsTrigger.a(numberFormatException);
        }
        return list3;
    }

    /*
     * Unable to fully structure code
     */
    @NotNull
    private List<RunBuildDecision> prepareBuilds(@NotNull List<SVcsModification> var1_1, boolean var2_2, @Nullable SVcsModification var3_3, @NotNull TriggerRulesFilter var4_4, @NotNull VcsTriggerContext var5_5, @NotNull TriggerStateUpdate var6_6, @NotNull List<SVcsModification> var7_7) {
        block46: {
            block45: {
                block49: {
                    try {
                        if (var1_1 == null) {
                            SingleBranchVcsTrigger.$$$reportNull$$$0(69);
                        }
                    }
                    catch (RevisionsNotFoundException v0) {
                        throw SingleBranchVcsTrigger.a((Exception)v0);
                    }
                    try {
                        if (var4_4 == null) {
                            SingleBranchVcsTrigger.$$$reportNull$$$0(70);
                        }
                    }
                    catch (RevisionsNotFoundException v1) {
                        throw SingleBranchVcsTrigger.a((Exception)v1);
                    }
                    try {
                        if (var5_5 == null) {
                            SingleBranchVcsTrigger.$$$reportNull$$$0(71);
                        }
                    }
                    catch (RevisionsNotFoundException v2) {
                        throw SingleBranchVcsTrigger.a((Exception)v2);
                    }
                    try {
                        if (var6_6 == null) {
                            SingleBranchVcsTrigger.$$$reportNull$$$0(72);
                        }
                    }
                    catch (RevisionsNotFoundException v3) {
                        throw SingleBranchVcsTrigger.a((Exception)v3);
                    }
                    try {
                        if (var7_7 == null) {
                            SingleBranchVcsTrigger.$$$reportNull$$$0(73);
                        }
                    }
                    catch (RevisionsNotFoundException v4) {
                        throw SingleBranchVcsTrigger.a((Exception)v4);
                    }
                    if (!this.myBuildType.getBooleanInternalParameterOrTrue("vcsTrigger.maxModId.takeDependenciesChangesIntoAccount")) break block45;
                    if (var5_5.getVcsTriggerDescriptor().isWatchChangesInDependencies()) break block45;
                    break block49;
                    catch (RevisionsNotFoundException v5) {
                        throw SingleBranchVcsTrigger.a((Exception)v5);
                    }
                }
                try {
                    block50: {
                        if (!var5_5.getVcsTriggerDescriptor().isQuietPeriodEnabled()) break block45;
                        break block50;
                        catch (RevisionsNotFoundException v6) {
                            throw SingleBranchVcsTrigger.a((Exception)v6);
                        }
                    }
                    v7 = true;
                    break block46;
                }
                catch (RevisionsNotFoundException v8) {
                    throw SingleBranchVcsTrigger.a((Exception)v8);
                }
            }
            v7 = false;
        }
        var8_8 = v7;
        var9_9 = new ArrayList<RunBuildDecision>();
        try {
            var10_10 = new HashSet<List>();
            var11_14 = this.getMaxBuildCount();
            try {
                v9 = var11_14 < var1_1.size() ? var11_14 - 1 : var1_1.size() - 1;
            }
            catch (RevisionsNotFoundException v10) {
                throw SingleBranchVcsTrigger.a((Exception)v10);
            }
            for (var13_19 = var12_17 = (v15432); var13_19 >= 0; --var13_19) {
                block48: {
                    block47: {
                        block51: {
                            var14_20 = var1_1.get(var13_19);
                            if (!var8_8) break block47;
                            if (!var5_5.getVcsTriggerDescriptor().isPerCheckinTriggeringEnabled()) ** GOTO lbl76
                            break block51;
                            catch (RevisionsNotFoundException v11) {
                                throw SingleBranchVcsTrigger.a((Exception)v11);
                            }
                        }
                        try {
                            block52: {
                                if (var13_19 != 0) break block47;
                                break block52;
                                catch (RevisionsNotFoundException v12) {
                                    throw SingleBranchVcsTrigger.a((Exception)v12);
                                }
                            }
                            v13 = true;
                            break block48;
                        }
                        catch (RevisionsNotFoundException v14) {
                            throw SingleBranchVcsTrigger.a((Exception)v14);
                        }
                    }
                    v13 = false;
                }
                var8_8 = v13;
                try {
                    v15 = var3_3 != null ? this.getRequestor(var3_3, var2_2, var5_5) : this.getRequestor(var14_20, var2_2, var5_5);
                }
                catch (RevisionsNotFoundException v16) {
                    throw SingleBranchVcsTrigger.a((Exception)v16);
                }
                var15_21 = v15;
                var16_22 = this.createPromotionWithAssociatedChanges(var14_20, var5_5, var8_8, var7_7);
                var17_23 = this.getBuildLogMessage(var16_22, var3_3, var14_20, var2_2, var4_4);
                if (var10_10.add(var16_22.getRevisions())) ** GOTO lbl99
                try {
                    block53: {
                        if (!var16_22.getRevisions().isEmpty()) continue;
                        break block53;
                        catch (RevisionsNotFoundException v17) {
                            throw SingleBranchVcsTrigger.a((Exception)v17);
                        }
                    }
                    var9_9.add(new RunBuildDecision(this, var16_22, var15_21, var17_23, false, var5_5.getLogger((Branch)this.myBranch)));
                    continue;
                }
                catch (RevisionsNotFoundException v18) {
                    throw SingleBranchVcsTrigger.a((Exception)v18);
                }
            }
        }
        catch (RevisionsNotFoundException var10_11) {
            var11_15 = "Unable to find suitable revisions to trigger build on a specific change for the following build configurations: " + String.valueOf(var10_11.getBuildTypes()) + "\nBuild configuration " + this.myBuildType.getFullName() + " will be triggered on the latest changes.";
            try {
                v19 = var3_3 != null ? this.getRequestor(var3_3, var2_2, var5_5) : this.getRequestor(var1_1.getFirst(), var2_2, var5_5);
            }
            catch (RevisionsNotFoundException v20) {
                throw SingleBranchVcsTrigger.a((Exception)v20);
            }
            var12_18 = v19;
            var9_9.add(new RunBuildDecision(this, this.myBuildType.createBuildPromotion(), var12_18, var11_15, true, var5_5.getLogger((Branch)this.myBranch)));
        }
        catch (MoreRecentChangeFoundException var10_12) {
            var6_6.setShouldUpdateState(false);
            var5_5.getLogger((Branch)this.myBranch).warn("Skip triggering, reason: " + var10_12.getMessage());
        }
        catch (Throwable var10_13) {
            var11_16 = "Exception occurred while computing a revision for a newly triggered build in VCS trigger: " + String.valueOf(var10_13);
            Loggers.SERVER.warnAndDebugDetails(var11_16, var10_13);
            var5_5.getLogger((Branch)this.myBranch).warnAndDebugDetails(var11_16, var10_13);
        }
        try {
            v21 = var9_9;
            if (v21 == null) {
                SingleBranchVcsTrigger.$$$reportNull$$$0(74);
            }
        }
        catch (RevisionsNotFoundException v22) {
            throw SingleBranchVcsTrigger.a((Exception)v22);
        }
        return v21;
    }

    @NotNull
    private String getBuildLogMessage(@NotNull BuildPromotionEx buildPromotionEx, @Nullable SVcsModification sVcsModification, @NotNull SVcsModification sVcsModification2, boolean bl, @NotNull TriggerRulesFilter triggerRulesFilter) {
        String string;
        SVcsModification sVcsModification3;
        try {
            if (buildPromotionEx == null) {
                SingleBranchVcsTrigger.$$$reportNull$$$0(75);
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw SingleBranchVcsTrigger.a(numberFormatException);
        }
        try {
            if (sVcsModification2 == null) {
                SingleBranchVcsTrigger.$$$reportNull$$$0(76);
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw SingleBranchVcsTrigger.a(numberFormatException);
        }
        try {
            if (triggerRulesFilter == null) {
                SingleBranchVcsTrigger.$$$reportNull$$$0(77);
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw SingleBranchVcsTrigger.a(numberFormatException);
        }
        if (bl) {
            String string2;
            String string3 = "Enqueue " + LogUtil.describe((Object)buildPromotionEx) + " reason: per-checking triggering on the change " + LogUtil.describe((Object)sVcsModification2);
            String string4 = triggerRulesFilter.getMatchDetails(sVcsModification2);
            if (string4 != null) {
                string3 = string3 + " (" + string4 + ")";
            }
            try {
                string2 = string3;
                if (string2 == null) {
                    SingleBranchVcsTrigger.$$$reportNull$$$0(78);
                }
            }
            catch (NumberFormatException numberFormatException) {
                throw SingleBranchVcsTrigger.a(numberFormatException);
            }
            return string2;
        }
        String string5 = "Enqueue " + LogUtil.describe((Object)buildPromotionEx) + " reason: pending changes detected, the last change accepted by trigger rules ";
        try {
            sVcsModification3 = sVcsModification != null ? sVcsModification : sVcsModification2;
        }
        catch (NumberFormatException numberFormatException) {
            throw SingleBranchVcsTrigger.a(numberFormatException);
        }
        SVcsModification sVcsModification4 = sVcsModification3;
        string5 = string5 + LogUtil.describe((Object)sVcsModification4);
        String string6 = triggerRulesFilter.getMatchDetails(sVcsModification4);
        if (string6 != null) {
            string5 = string5 + " (" + string6 + ")";
        }
        try {
            string = string5;
            if (string == null) {
                SingleBranchVcsTrigger.$$$reportNull$$$0(79);
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw SingleBranchVcsTrigger.a(numberFormatException);
        }
        return string;
    }

    private int getMaxBuildCount() {
        return TeamCityProperties.getInteger((String)"teamcity.vcsBuildTrigger.perCheckin.maxBuildCount", (int)50);
    }

    @NotNull
    private BuildPromotionEx createPromotionWithAssociatedChanges(@NotNull SVcsModification sVcsModification, @NotNull VcsTriggerContext vcsTriggerContext, boolean bl, @NotNull List<SVcsModification> list) throws MoreRecentChangeFoundException {
        BuildPromotionEx buildPromotionEx;
        HistoryChangesCollector historyChangesCollector;
        VcsModificationEx vcsModificationEx;
        try {
            if (sVcsModification == null) {
                SingleBranchVcsTrigger.$$$reportNull$$$0(80);
            }
        }
        catch (MoreRecentChangeFoundException moreRecentChangeFoundException) {
            throw SingleBranchVcsTrigger.a((Exception)((Object)moreRecentChangeFoundException));
        }
        try {
            if (vcsTriggerContext == null) {
                SingleBranchVcsTrigger.$$$reportNull$$$0(81);
            }
        }
        catch (MoreRecentChangeFoundException moreRecentChangeFoundException) {
            throw SingleBranchVcsTrigger.a((Exception)((Object)moreRecentChangeFoundException));
        }
        try {
            if (list == null) {
                SingleBranchVcsTrigger.$$$reportNull$$$0(82);
            }
        }
        catch (MoreRecentChangeFoundException moreRecentChangeFoundException) {
            throw SingleBranchVcsTrigger.a((Exception)((Object)moreRecentChangeFoundException));
        }
        BuildPromotionEx buildPromotionEx2 = this.createBuildPromotion(vcsTriggerContext);
        long l = sVcsModification.getId();
        if (bl) {
            block26: {
                vcsModificationEx = this.getLatestChangeInBranch(sVcsModification);
                try {
                    try {
                        try {
                            try {
                                if (vcsModificationEx.getId() == sVcsModification.getId() || vcsModificationEx.getRelation(this.myBuildType.getBuildTypeId()) != RelationType.REGULAR) break block26;
                            }
                            catch (MoreRecentChangeFoundException moreRecentChangeFoundException) {
                                throw SingleBranchVcsTrigger.a((Exception)((Object)moreRecentChangeFoundException));
                            }
                            if (!vcsTriggerContext.getVcsTriggerDescriptor().isQuietPeriodEnabled()) break block26;
                        }
                        catch (MoreRecentChangeFoundException moreRecentChangeFoundException) {
                            throw SingleBranchVcsTrigger.a((Exception)((Object)moreRecentChangeFoundException));
                        }
                        if (list.contains(vcsModificationEx)) break block26;
                    }
                    catch (MoreRecentChangeFoundException moreRecentChangeFoundException) {
                        throw SingleBranchVcsTrigger.a((Exception)((Object)moreRecentChangeFoundException));
                    }
                    throw new MoreRecentChangeFoundException("More recent change detected: " + String.valueOf(vcsModificationEx));
                }
                catch (MoreRecentChangeFoundException moreRecentChangeFoundException) {
                    throw SingleBranchVcsTrigger.a((Exception)((Object)moreRecentChangeFoundException));
                }
            }
            l = vcsModificationEx.getId();
            try {
                if (l != sVcsModification.getId()) {
                    vcsTriggerContext.getLogger((Branch)this.myBranch).info("Will set max modification id " + l + " to the triggered build instead of " + sVcsModification.getId() + " because there were more recent changes in dependencies");
                }
            }
            catch (MoreRecentChangeFoundException moreRecentChangeFoundException) {
                throw SingleBranchVcsTrigger.a((Exception)((Object)moreRecentChangeFoundException));
            }
        }
        try {
            historyChangesCollector = vcsTriggerContext.getVcsTriggerDescriptor().isPerCheckinTriggeringEnabled() ? this.myChangesCollectorFactory.createHistoryChangesCollector(l, true) : this.myChangesCollectorFactory.createHistoryChangesCollectorForQuietPeriod(l, true);
        }
        catch (MoreRecentChangeFoundException moreRecentChangeFoundException) {
            throw SingleBranchVcsTrigger.a((Exception)((Object)moreRecentChangeFoundException));
        }
        vcsModificationEx = historyChangesCollector;
        try {
            vcsModificationEx.scheduleCheckingForChangesAndWait(buildPromotionEx2, (CancelCheck)new CancelableTaskHolder());
            buildPromotionEx = buildPromotionEx2;
            if (buildPromotionEx == null) {
                SingleBranchVcsTrigger.$$$reportNull$$$0(83);
            }
        }
        catch (MoreRecentChangeFoundException moreRecentChangeFoundException) {
            throw SingleBranchVcsTrigger.a((Exception)((Object)moreRecentChangeFoundException));
        }
        return buildPromotionEx;
    }

    @NotNull
    private VcsModificationEx getLatestChangeInBranch(@NotNull SVcsModification sVcsModification) {
        VcsModificationEx vcsModificationEx;
        try {
            if (sVcsModification == null) {
                SingleBranchVcsTrigger.$$$reportNull$$$0(84);
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw SingleBranchVcsTrigger.a(numberFormatException);
        }
        long l = sVcsModification.getId();
        ChangesCalculationOptionsEx changesCalculationOptionsEx = this.myTriggeringContext.getChangesCalculationOptionsFactory().create();
        changesCalculationOptionsEx.setPrevBuildPolicy(SelectPrevBuildPolicy.SINCE_NULL_BUILD);
        changesCalculationOptionsEx.setIncludeDependencyChanges(Boolean.valueOf(true));
        changesCalculationOptionsEx.setIncludeUnreachableChangesFromDetachedRoots(Boolean.valueOf(false));
        changesCalculationOptionsEx.setCallback((VcsModificationProcessor)new /* Unavailable Anonymous Inner Class!! */);
        for (ChangeDescriptor changeDescriptor : this.myBranch.getDetectedChanges(changesCalculationOptionsEx)) {
            VcsModificationEx vcsModificationEx2;
            VcsModificationEx vcsModificationEx3 = (VcsModificationEx)changeDescriptor.getRelatedVcsChange();
            try {
                if (vcsModificationEx3 == null) {
                    continue;
                }
            }
            catch (NumberFormatException numberFormatException) {
                throw SingleBranchVcsTrigger.a(numberFormatException);
            }
            try {
                vcsModificationEx2 = vcsModificationEx3;
                if (vcsModificationEx2 == null) {
                    SingleBranchVcsTrigger.$$$reportNull$$$0(85);
                }
            }
            catch (NumberFormatException numberFormatException) {
                throw SingleBranchVcsTrigger.a(numberFormatException);
            }
            return vcsModificationEx2;
        }
        try {
            vcsModificationEx = (VcsModificationEx)sVcsModification;
            if (vcsModificationEx == null) {
                SingleBranchVcsTrigger.$$$reportNull$$$0(86);
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw SingleBranchVcsTrigger.a(numberFormatException);
        }
        return vcsModificationEx;
    }

    @NotNull
    private String getRequestor(@NotNull SVcsModification sVcsModification, boolean bl, VcsTriggerContext vcsTriggerContext) {
        String string;
        try {
            if (sVcsModification == null) {
                SingleBranchVcsTrigger.$$$reportNull$$$0(87);
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw SingleBranchVcsTrigger.a(numberFormatException);
        }
        TriggeredByBuilder triggeredByBuilder = this.getRequestorForNewBranch(sVcsModification.getVcsRoot(), vcsTriggerContext);
        try {
            if (bl) {
                triggeredByBuilder.addParameter("queueMergingEnabled", "false");
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw SingleBranchVcsTrigger.a(numberFormatException);
        }
        try {
            string = triggeredByBuilder.toString();
            if (string == null) {
                SingleBranchVcsTrigger.$$$reportNull$$$0(88);
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw SingleBranchVcsTrigger.a(numberFormatException);
        }
        return string;
    }

    @NotNull
    private TriggeredByBuilder getRequestorForNewBranch(@NotNull VcsRootInstance vcsRootInstance, VcsTriggerContext vcsTriggerContext) {
        TriggeredByBuilder triggeredByBuilder;
        try {
            if (vcsRootInstance == null) {
                SingleBranchVcsTrigger.$$$reportNull$$$0(89);
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw SingleBranchVcsTrigger.a(numberFormatException);
        }
        TriggeredByBuilder triggeredByBuilder2 = new TriggeredByBuilder();
        try {
            triggeredByBuilder2.addParameter("vcsName", vcsRootInstance.getVcsName());
            triggeredByBuilder2.addParameter("type", "vcs");
            triggeredByBuilder2.addParameter("triggerId", vcsTriggerContext.getTriggerDescriptor().getId());
            triggeredByBuilder = triggeredByBuilder2;
            if (triggeredByBuilder == null) {
                SingleBranchVcsTrigger.$$$reportNull$$$0(90);
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw SingleBranchVcsTrigger.a(numberFormatException);
        }
        return triggeredByBuilder;
    }

    public boolean isBuildTriggered() {
        return this.myBuildTriggered;
    }

    private void logChangesCollectingInProgress(@NotNull Logger logger) {
        try {
            if (logger == null) {
                SingleBranchVcsTrigger.$$$reportNull$$$0(91);
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw SingleBranchVcsTrigger.a(numberFormatException);
        }
        try {
            if (logger.isDebugEnabled()) {
                logger.debug("Quiet period passed but some VCS root is still collecting changes, skip triggering");
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw SingleBranchVcsTrigger.a(numberFormatException);
        }
    }

    private void logRepositoryStateUpdated(@NotNull Logger logger) {
        try {
            if (logger == null) {
                SingleBranchVcsTrigger.$$$reportNull$$$0(92);
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw SingleBranchVcsTrigger.a(numberFormatException);
        }
        try {
            if (logger.isDebugEnabled()) {
                logger.debug("Some repository state updated during trigger execution, skip triggering");
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw SingleBranchVcsTrigger.a(numberFormatException);
        }
    }

    static {
        boolean bl;
        try {
            bl = !SingleBranchVcsTrigger.class.desiredAssertionStatus();
        }
        catch (NumberFormatException numberFormatException) {
            throw SingleBranchVcsTrigger.a(numberFormatException);
        }
        $assertionsDisabled = bl;
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
