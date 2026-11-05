/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jetbrains.buildServer.BuildAgent
 *  jetbrains.buildServer.log.ThrottleLogger
 *  jetbrains.buildServer.serverSide.BuildPromotion
 *  jetbrains.buildServer.serverSide.BuildPromotionEx
 *  jetbrains.buildServer.serverSide.BuildQueueEx
 *  jetbrains.buildServer.serverSide.BuildTypeEx
 *  jetbrains.buildServer.serverSide.BuildTypeNotFoundException
 *  jetbrains.buildServer.serverSide.DependencyConsumer
 *  jetbrains.buildServer.serverSide.SBuildType
 *  jetbrains.buildServer.serverSide.TeamCityProperties
 *  jetbrains.buildServer.serverSide.build.BuildSettingsOptions
 *  jetbrains.buildServer.serverSide.buildDistribution.BuildDistributorInput
 *  jetbrains.buildServer.serverSide.buildDistribution.QueuedBuildInfo
 *  jetbrains.buildServer.serverSide.buildDistribution.SimpleWaitReason
 *  jetbrains.buildServer.serverSide.buildDistribution.WaitReason
 *  jetbrains.buildServer.serverSide.impl.LogUtil
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.BuildDistributorInputEx
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.BuildQueueOptimizerAwarePrecondition
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.CheckingForChangesPrecondition
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.VersionedSettingsPrecondition$1
 *  jetbrains.buildServer.serverSide.versionedSettings.VersionedSettingsManager
 *  jetbrains.buildServer.util.Dates
 *  jetbrains.buildServer.util.TimePrinter
 *  jetbrains.buildServer.util.positioning.PositionAware
 *  jetbrains.buildServer.util.positioning.PositionConstraint
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package jetbrains.buildServer.serverSide.impl.buildDistribution;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import jetbrains.buildServer.BuildAgent;
import jetbrains.buildServer.log.ThrottleLogger;
import jetbrains.buildServer.serverSide.BuildPromotion;
import jetbrains.buildServer.serverSide.BuildPromotionEx;
import jetbrains.buildServer.serverSide.BuildQueueEx;
import jetbrains.buildServer.serverSide.BuildTypeEx;
import jetbrains.buildServer.serverSide.BuildTypeNotFoundException;
import jetbrains.buildServer.serverSide.DependencyConsumer;
import jetbrains.buildServer.serverSide.SBuildType;
import jetbrains.buildServer.serverSide.TeamCityProperties;
import jetbrains.buildServer.serverSide.build.BuildSettingsOptions;
import jetbrains.buildServer.serverSide.buildDistribution.BuildDistributorInput;
import jetbrains.buildServer.serverSide.buildDistribution.QueuedBuildInfo;
import jetbrains.buildServer.serverSide.buildDistribution.SimpleWaitReason;
import jetbrains.buildServer.serverSide.buildDistribution.WaitReason;
import jetbrains.buildServer.serverSide.impl.LogUtil;
import jetbrains.buildServer.serverSide.impl.buildDistribution.BuildDistributorInputEx;
import jetbrains.buildServer.serverSide.impl.buildDistribution.BuildQueueOptimizerAwarePrecondition;
import jetbrains.buildServer.serverSide.impl.buildDistribution.CheckingForChangesPrecondition;
import jetbrains.buildServer.serverSide.impl.buildDistribution.VersionedSettingsPrecondition;
import jetbrains.buildServer.serverSide.versionedSettings.VersionedSettingsManager;
import jetbrains.buildServer.util.Dates;
import jetbrains.buildServer.util.TimePrinter;
import jetbrains.buildServer.util.positioning.PositionAware;
import jetbrains.buildServer.util.positioning.PositionConstraint;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Exception performing whole class analysis ignored.
 */
public class VersionedSettingsPrecondition
extends BuildQueueOptimizerAwarePrecondition
implements PositionAware {
    private static final ThrottleLogger LOG = ThrottleLogger.get1HrLogger(VersionedSettingsPrecondition.class);
    public static final SimpleWaitReason SETTINGS_NOT_FINALIZED = new SimpleWaitReason("Build settings have not been finalized");
    public static final SimpleWaitReason SETTINGS_NOT_FINALIZED_IN_OTHER_BUILDS_OF_THE_CHAIN = new SimpleWaitReason("Build settings have not been finalized in some of the build chain builds");
    public static final SimpleWaitReason SETTINGS_NOT_FINALIZED_IN_OPTIMIZATION_CANDIDATE_BUILDS = new SimpleWaitReason("Waiting for the build settings finalization in other queued builds");
    public static final Set<WaitReason> ALL_WAIT_REASONS = new HashSet<WaitReason>();
    private final VersionedSettingsManager myVersionedSettingsManager;
    private final BuildSettingsOptions myBuildSettingsOptions;
    public static final String ORDER_ID;

    public VersionedSettingsPrecondition(@NotNull VersionedSettingsManager versionedSettingsManager, @NotNull BuildSettingsOptions buildSettingsOptions, @NotNull BuildQueueEx buildQueueEx) {
        if (versionedSettingsManager == null) {
            VersionedSettingsPrecondition.$$$reportNull$$$0(0);
        }
        if (buildSettingsOptions == null) {
            VersionedSettingsPrecondition.$$$reportNull$$$0(1);
        }
        if (buildQueueEx == null) {
            VersionedSettingsPrecondition.$$$reportNull$$$0(2);
        }
        super(buildQueueEx);
        this.myVersionedSettingsManager = versionedSettingsManager;
        this.myBuildSettingsOptions = buildSettingsOptions;
    }

    @Nullable
    public WaitReason canStart(@NotNull QueuedBuildInfo queuedBuildInfo, @NotNull Map<QueuedBuildInfo, BuildAgent> map, @NotNull BuildDistributorInput buildDistributorInput, boolean bl) {
        block32: {
            boolean bl2;
            BuildPromotionEx buildPromotionEx2;
            block30: {
                block31: {
                    block29: {
                        try {
                            if (queuedBuildInfo == null) {
                                VersionedSettingsPrecondition.$$$reportNull$$$0(3);
                            }
                        }
                        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                            throw VersionedSettingsPrecondition.a(buildTypeNotFoundException);
                        }
                        try {
                            if (map == null) {
                                VersionedSettingsPrecondition.$$$reportNull$$$0(4);
                            }
                        }
                        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                            throw VersionedSettingsPrecondition.a(buildTypeNotFoundException);
                        }
                        try {
                            if (buildDistributorInput == null) {
                                VersionedSettingsPrecondition.$$$reportNull$$$0(5);
                            }
                        }
                        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                            throw VersionedSettingsPrecondition.a(buildTypeNotFoundException);
                        }
                        buildPromotionEx2 = (BuildPromotionEx)queuedBuildInfo.getBuildPromotionInfo();
                        try {
                            try {
                                if (!buildPromotionEx2.isFailedToCollectChanges() || buildPromotionEx2.isCompositeBuild()) break block29;
                            }
                            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                                throw VersionedSettingsPrecondition.a(buildTypeNotFoundException);
                            }
                            return null;
                        }
                        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                            throw VersionedSettingsPrecondition.a(buildTypeNotFoundException);
                        }
                    }
                    bl2 = this.requiresSettingsFreeze(buildPromotionEx2);
                    try {
                        try {
                            try {
                                if (!bl2 || buildPromotionEx2.settingsFinalizationComplete()) break block30;
                            }
                            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                                throw VersionedSettingsPrecondition.a(buildTypeNotFoundException);
                            }
                            if (bl) break block31;
                        }
                        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                            throw VersionedSettingsPrecondition.a(buildTypeNotFoundException);
                        }
                        VersionedSettingsPrecondition.logDelayIfNeeded(buildPromotionEx2);
                    }
                    catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                        throw VersionedSettingsPrecondition.a(buildTypeNotFoundException);
                    }
                }
                VersionedSettingsPrecondition.getIncompleteFinalizationCache((BuildDistributorInputEx)buildDistributorInput).add(buildPromotionEx2.getId());
                return SETTINGS_NOT_FINALIZED;
            }
            try {
                if (this.settingsFreezeInProgressInOtherPartsOfBuildChain(buildPromotionEx2, (BuildDistributorInputEx)buildDistributorInput)) {
                    return SETTINGS_NOT_FINALIZED_IN_OTHER_BUILDS_OF_THE_CHAIN;
                }
            }
            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                throw VersionedSettingsPrecondition.a(buildTypeNotFoundException);
            }
            try {
                if (!TeamCityProperties.getBoolean((String)"teamcity.versionedSettingsPrecondition.waitForQueuedBuildsBefore")) {
                    return null;
                }
            }
            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                throw VersionedSettingsPrecondition.a(buildTypeNotFoundException);
            }
            try {
                try {
                    if (!bl2 || !this.processPotentialOptimizationCandidates(buildPromotionEx2, buildPromotionEx -> {
                        boolean bl;
                        block5: {
                            block4: {
                                try {
                                    try {
                                        if (buildPromotionEx.settingsFinalizationComplete() || !this.requiresSettingsFreeze((BuildPromotionEx)buildPromotionEx)) break block4;
                                    }
                                    catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                                        throw VersionedSettingsPrecondition.a(buildTypeNotFoundException);
                                    }
                                    bl = true;
                                    break block5;
                                }
                                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                                    throw VersionedSettingsPrecondition.a(buildTypeNotFoundException);
                                }
                            }
                            bl = false;
                        }
                        return bl;
                    })) break block32;
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw VersionedSettingsPrecondition.a(buildTypeNotFoundException);
                }
                return SETTINGS_NOT_FINALIZED_IN_OPTIMIZATION_CANDIDATE_BUILDS;
            }
            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                throw VersionedSettingsPrecondition.a(buildTypeNotFoundException);
            }
        }
        return null;
    }

    public boolean requiresSettingsFreeze(@NotNull BuildPromotionEx buildPromotionEx) {
        boolean bl;
        block13: {
            block12: {
                BuildTypeEx buildTypeEx;
                try {
                    if (buildPromotionEx == null) {
                        VersionedSettingsPrecondition.$$$reportNull$$$0(6);
                    }
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw VersionedSettingsPrecondition.a(buildTypeNotFoundException);
                }
                try {
                    buildTypeEx = buildPromotionEx.getParentBuildType();
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    return false;
                }
                try {
                    if (buildTypeEx == null) {
                        return false;
                    }
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw VersionedSettingsPrecondition.a(buildTypeNotFoundException);
                }
                try {
                    try {
                        if (!this.myBuildSettingsOptions.shouldFreezeCurrentSettings() && !this.myVersionedSettingsManager.shouldTakeSettingsFromVcs((SBuildType)buildTypeEx, buildPromotionEx)) break block12;
                    }
                    catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                        throw VersionedSettingsPrecondition.a(buildTypeNotFoundException);
                    }
                    bl = true;
                    break block13;
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw VersionedSettingsPrecondition.a(buildTypeNotFoundException);
                }
            }
            bl = false;
        }
        return bl;
    }

    private boolean settingsFreezeInProgressInOtherPartsOfBuildChain(@NotNull BuildPromotionEx buildPromotionEx, @NotNull BuildDistributorInputEx buildDistributorInputEx) {
        try {
            if (buildPromotionEx == null) {
                VersionedSettingsPrecondition.$$$reportNull$$$0(7);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw VersionedSettingsPrecondition.a(buildTypeNotFoundException);
        }
        try {
            if (buildDistributorInputEx == null) {
                VersionedSettingsPrecondition.$$$reportNull$$$0(8);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw VersionedSettingsPrecondition.a(buildTypeNotFoundException);
        }
        HashSet<Long> hashSet = VersionedSettingsPrecondition.getIncompleteFinalizationCache(buildDistributorInputEx);
        AtomicBoolean atomicBoolean = new AtomicBoolean();
        HashSet hashSet2 = new HashSet();
        ArrayList arrayList = new ArrayList();
        1 var7_7 = new /* Unavailable Anonymous Inner Class!! */;
        try {
            buildPromotionEx.traverseDependedOnMe((DependencyConsumer)var7_7);
            if (atomicBoolean.get()) {
                return true;
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw VersionedSettingsPrecondition.a(buildTypeNotFoundException);
        }
        for (BuildPromotionEx buildPromotionEx2 : new ArrayList(arrayList)) {
            try {
                buildPromotionEx2.traverseDependencies((DependencyConsumer)var7_7);
                if (!atomicBoolean.get()) continue;
                return true;
            }
            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                throw VersionedSettingsPrecondition.a(buildTypeNotFoundException);
            }
        }
        return false;
    }

    @NotNull
    private static HashSet<Long> getIncompleteFinalizationCache(@NotNull BuildDistributorInputEx buildDistributorInputEx) {
        HashSet hashSet;
        try {
            if (buildDistributorInputEx == null) {
                VersionedSettingsPrecondition.$$$reportNull$$$0(9);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw VersionedSettingsPrecondition.a(buildTypeNotFoundException);
        }
        try {
            hashSet = (HashSet)buildDistributorInputEx.getCustomData(VersionedSettingsPrecondition.class.getSimpleName() + "_freeze_not_finished", () -> new HashSet());
            if (hashSet == null) {
                VersionedSettingsPrecondition.$$$reportNull$$$0(10);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw VersionedSettingsPrecondition.a(buildTypeNotFoundException);
        }
        return hashSet;
    }

    private static void logDelayIfNeeded(@NotNull BuildPromotionEx buildPromotionEx) {
        long l;
        try {
            if (buildPromotionEx == null) {
                VersionedSettingsPrecondition.$$$reportNull$$$0(11);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw VersionedSettingsPrecondition.a(buildTypeNotFoundException);
        }
        try {
            l = buildPromotionEx.getQueuedDate() != null ? Dates.now().getTime() - buildPromotionEx.getQueuedDate().getTime() : 0L;
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw VersionedSettingsPrecondition.a(buildTypeNotFoundException);
        }
        long l2 = l;
        long l3 = TeamCityProperties.getLong((String)"teamcity.buildSettingsPrecondition.queuedTime.warn.millis", (long)(2L * Dates.ONE_HOUR));
        try {
            if (l2 > l3) {
                LOG.info("Promotion " + LogUtil.describe((BuildPromotion)buildPromotionEx) + " awaits for build settings finalization for more than " + TimePrinter.formatMillis((long)l3), new Object[0]);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw VersionedSettingsPrecondition.a(buildTypeNotFoundException);
        }
    }

    @NotNull
    public String getOrderId() {
        String string;
        try {
            string = ORDER_ID;
            if (string == null) {
                VersionedSettingsPrecondition.$$$reportNull$$$0(12);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw VersionedSettingsPrecondition.a(buildTypeNotFoundException);
        }
        return string;
    }

    @NotNull
    public PositionConstraint getConstraint() {
        PositionConstraint positionConstraint;
        try {
            positionConstraint = PositionConstraint.after((String[])new String[]{CheckingForChangesPrecondition.ORDER_ID});
            if (positionConstraint == null) {
                VersionedSettingsPrecondition.$$$reportNull$$$0(13);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw VersionedSettingsPrecondition.a(buildTypeNotFoundException);
        }
        return positionConstraint;
    }

    static {
        ALL_WAIT_REASONS.add((WaitReason)SETTINGS_NOT_FINALIZED);
        ALL_WAIT_REASONS.add((WaitReason)SETTINGS_NOT_FINALIZED_IN_OTHER_BUILDS_OF_THE_CHAIN);
        ALL_WAIT_REASONS.add((WaitReason)SETTINGS_NOT_FINALIZED_IN_OPTIMIZATION_CANDIDATE_BUILDS);
        ORDER_ID = VersionedSettingsPrecondition.class.getSimpleName();
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

    private static BuildTypeNotFoundException a(BuildTypeNotFoundException buildTypeNotFoundException) {
        return buildTypeNotFoundException;
    }
}
