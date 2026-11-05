/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.intellij.openapi.diagnostic.Logger
 *  jetbrains.buildServer.buildTriggers.BuildTriggerException
 *  jetbrains.buildServer.buildTriggers.PolledBuildTrigger
 *  jetbrains.buildServer.buildTriggers.PolledTriggerContext
 *  jetbrains.buildServer.buildTriggers.vcs.BranchFilterTriggerHelper
 *  jetbrains.buildServer.buildTriggers.vcs.BranchStateCalculator
 *  jetbrains.buildServer.buildTriggers.vcs.RepositoryStateUpdateListener
 *  jetbrains.buildServer.buildTriggers.vcs.SharedTriggeringRulesState
 *  jetbrains.buildServer.buildTriggers.vcs.SingleBranchVcsTrigger
 *  jetbrains.buildServer.buildTriggers.vcs.VcsBuildTriggerState
 *  jetbrains.buildServer.buildTriggers.vcs.VcsTriggerContext
 *  jetbrains.buildServer.buildTriggers.vcs.VcsTriggerDescriptor
 *  jetbrains.buildServer.buildTriggers.vcs.VcsTriggeringContext
 *  jetbrains.buildServer.serverSide.Branch
 *  jetbrains.buildServer.serverSide.BranchEx
 *  jetbrains.buildServer.serverSide.BuildTypeEx
 *  jetbrains.buildServer.serverSide.SBuildType
 *  jetbrains.buildServer.util.EventDispatcher
 *  jetbrains.buildServer.util.NamedThreadFactory
 *  jetbrains.buildServer.util.StringUtil
 *  jetbrains.buildServer.vcs.RepositoryStateListener
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package jetbrains.buildServer.buildTriggers.vcs;

import com.intellij.openapi.diagnostic.Logger;
import java.util.Collections;
import java.util.EventListener;
import java.util.LinkedHashMap;
import java.util.Map;
import jetbrains.buildServer.buildTriggers.BuildTriggerException;
import jetbrains.buildServer.buildTriggers.PolledBuildTrigger;
import jetbrains.buildServer.buildTriggers.PolledTriggerContext;
import jetbrains.buildServer.buildTriggers.vcs.BranchFilterTriggerHelper;
import jetbrains.buildServer.buildTriggers.vcs.BranchStateCalculator;
import jetbrains.buildServer.buildTriggers.vcs.RepositoryStateUpdateListener;
import jetbrains.buildServer.buildTriggers.vcs.SharedTriggeringRulesState;
import jetbrains.buildServer.buildTriggers.vcs.SingleBranchVcsTrigger;
import jetbrains.buildServer.buildTriggers.vcs.VcsBuildTriggerState;
import jetbrains.buildServer.buildTriggers.vcs.VcsTriggerContext;
import jetbrains.buildServer.buildTriggers.vcs.VcsTriggerDescriptor;
import jetbrains.buildServer.buildTriggers.vcs.VcsTriggeringContext;
import jetbrains.buildServer.serverSide.Branch;
import jetbrains.buildServer.serverSide.BranchEx;
import jetbrains.buildServer.serverSide.BuildTypeEx;
import jetbrains.buildServer.serverSide.SBuildType;
import jetbrains.buildServer.util.EventDispatcher;
import jetbrains.buildServer.util.NamedThreadFactory;
import jetbrains.buildServer.util.StringUtil;
import jetbrains.buildServer.vcs.RepositoryStateListener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Exception performing whole class analysis ignored.
 */
class AllBranchesVcsTrigger
extends PolledBuildTrigger {
    private final EventDispatcher<RepositoryStateListener> myRepositoryStateEvents;
    private final BranchFilterTriggerHelper myBranchFilterHelper;
    private final VcsTriggeringContext myTriggeringContext;

    public AllBranchesVcsTrigger(@NotNull VcsTriggeringContext vcsTriggeringContext) {
        if (vcsTriggeringContext == null) {
            AllBranchesVcsTrigger.$$$reportNull$$$0(0);
        }
        this.myTriggeringContext = vcsTriggeringContext;
        this.myRepositoryStateEvents = vcsTriggeringContext.getRepositoryStateEvents();
        this.myBranchFilterHelper = new BranchFilterTriggerHelper(vcsTriggeringContext.getSpecs(), "+:*");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void triggerBuild(@NotNull PolledTriggerContext polledTriggerContext) throws BuildTriggerException {
        try {
            if (polledTriggerContext == null) {
                AllBranchesVcsTrigger.$$$reportNull$$$0(1);
            }
        }
        catch (BuildTriggerException buildTriggerException) {
            throw AllBranchesVcsTrigger.a((RuntimeException)((Object)buildTriggerException));
        }
        VcsTriggerDescriptor vcsTriggerDescriptor = new VcsTriggerDescriptor(polledTriggerContext.getTriggerDescriptor(), this.getDefaultQuietPeriod());
        Map map = BranchStateCalculator.getCurrentBranchSpecs((SBuildType)polledTriggerContext.getBuildType(), (boolean)vcsTriggerDescriptor.isWatchChangesInDependencies());
        VcsBuildTriggerState vcsBuildTriggerState = new VcsBuildTriggerState(polledTriggerContext.getCustomDataStorage(), map, this.myTriggeringContext.getTimeService());
        RepositoryStateUpdateListener repositoryStateUpdateListener = new RepositoryStateUpdateListener(map.keySet(), polledTriggerContext.getLogger());
        try {
            this.myRepositoryStateEvents.addListener((EventListener)repositoryStateUpdateListener);
            Logger logger = polledTriggerContext.getLogger();
            VcsTriggerContext vcsTriggerContext = new VcsTriggerContext(polledTriggerContext, repositoryStateUpdateListener, this.getDefaultQuietPeriod(), this.myTriggeringContext);
            int n = this.getBranchLimit((BuildTypeEx)polledTriggerContext.getBuildType());
            Map<String, SingleBranchVcsTrigger> map2 = this.getPerBranchTriggers(polledTriggerContext, vcsBuildTriggerState, logger);
            try {
                if (map2.size() > 1) {
                    logger.debug("Will process " + map2.size() + " branches");
                }
            }
            catch (BuildTriggerException buildTriggerException) {
                throw AllBranchesVcsTrigger.a((RuntimeException)((Object)buildTriggerException));
            }
            NamedThreadFactory.executeWithNewThreadName((String)("Processing " + StringUtil.withPlural((long)map2.size(), (String)"branch")), () -> {
                int n2 = 0;
                for (SingleBranchVcsTrigger singleBranchVcsTrigger : map2.values()) {
                    try {
                        singleBranchVcsTrigger.triggerBuild(vcsTriggerContext);
                        if (singleBranchVcsTrigger.isBuildTriggered()) {
                            ++n2;
                        }
                    }
                    catch (NumberFormatException numberFormatException) {
                        throw AllBranchesVcsTrigger.a(numberFormatException);
                    }
                    try {
                        if (n2 >= n) {
                            logger.warn("Reached the limit for a number of branches where builds were triggered: " + n + ", stop processing the rest of the branches");
                            break;
                        }
                    }
                    catch (NumberFormatException numberFormatException) {
                        throw AllBranchesVcsTrigger.a(numberFormatException);
                    }
                    try {
                        try {
                            if (!vcsTriggerDescriptor.isQuietPeriodEnabled() || !vcsTriggerContext.isRepositoryStateUpdated()) continue;
                        }
                        catch (NumberFormatException numberFormatException) {
                            throw AllBranchesVcsTrigger.a(numberFormatException);
                        }
                        logger.warn("Some repository state updated during the trigger execution, skip triggering till next time");
                        break;
                    }
                    catch (NumberFormatException numberFormatException) {
                        throw AllBranchesVcsTrigger.a(numberFormatException);
                    }
                }
            });
            vcsBuildTriggerState.persist(map2.keySet());
        }
        finally {
            this.myRepositoryStateEvents.removeListener((EventListener)repositoryStateUpdateListener);
        }
    }

    private int getDefaultQuietPeriod() {
        return this.myTriggeringContext.getVcsManager().getDefaultQuietPeriod();
    }

    public void triggerActivated(@NotNull PolledTriggerContext polledTriggerContext) throws BuildTriggerException {
        try {
            if (polledTriggerContext == null) {
                AllBranchesVcsTrigger.$$$reportNull$$$0(2);
            }
        }
        catch (BuildTriggerException buildTriggerException) {
            throw AllBranchesVcsTrigger.a((RuntimeException)((Object)buildTriggerException));
        }
        VcsBuildTriggerState vcsBuildTriggerState = new VcsBuildTriggerState(polledTriggerContext.getCustomDataStorage(), Collections.emptyMap(), this.myTriggeringContext.getTimeService());
        Logger logger = polledTriggerContext.getLogger();
        Map<String, SingleBranchVcsTrigger> map = this.getPerBranchTriggers(polledTriggerContext, vcsBuildTriggerState, logger);
        logger.info("Initializing the VCS trigger state for " + map.size() + " branches");
        for (SingleBranchVcsTrigger singleBranchVcsTrigger : map.values()) {
            singleBranchVcsTrigger.triggerActivated(logger);
        }
    }

    public void triggerDeactivated(@NotNull PolledTriggerContext polledTriggerContext) throws BuildTriggerException {
        try {
            if (polledTriggerContext == null) {
                AllBranchesVcsTrigger.$$$reportNull$$$0(3);
            }
        }
        catch (BuildTriggerException buildTriggerException) {
            throw AllBranchesVcsTrigger.a((RuntimeException)((Object)buildTriggerException));
        }
        polledTriggerContext.getLogger().info("Clearing the VCS trigger state because the trigger is deactivated");
        polledTriggerContext.getCustomDataStorage().clear();
    }

    @NotNull
    private Map<String, SingleBranchVcsTrigger> getPerBranchTriggers(@NotNull PolledTriggerContext polledTriggerContext, @NotNull VcsBuildTriggerState vcsBuildTriggerState, @NotNull Logger logger) {
        LinkedHashMap<String, SingleBranchVcsTrigger> linkedHashMap;
        try {
            if (polledTriggerContext == null) {
                AllBranchesVcsTrigger.$$$reportNull$$$0(4);
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw AllBranchesVcsTrigger.a(numberFormatException);
        }
        try {
            if (vcsBuildTriggerState == null) {
                AllBranchesVcsTrigger.$$$reportNull$$$0(5);
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw AllBranchesVcsTrigger.a(numberFormatException);
        }
        try {
            if (logger == null) {
                AllBranchesVcsTrigger.$$$reportNull$$$0(6);
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw AllBranchesVcsTrigger.a(numberFormatException);
        }
        VcsTriggerDescriptor vcsTriggerDescriptor = new VcsTriggerDescriptor(polledTriggerContext.getTriggerDescriptor(), this.getDefaultQuietPeriod());
        LinkedHashMap<String, SingleBranchVcsTrigger> linkedHashMap2 = new LinkedHashMap<String, SingleBranchVcsTrigger>();
        BuildTypeEx buildTypeEx = (BuildTypeEx)polledTriggerContext.getBuildType();
        SharedTriggeringRulesState sharedTriggeringRulesState = new SharedTriggeringRulesState();
        for (Branch branch : this.myBranchFilterHelper.getBranches(polledTriggerContext, Boolean.valueOf(vcsTriggerDescriptor.isWatchChangesInDependencies()), logger)) {
            linkedHashMap2.put(branch.getName(), new SingleBranchVcsTrigger(this.myTriggeringContext, buildTypeEx, (BranchEx)branch, vcsBuildTriggerState, sharedTriggeringRulesState, vcsTriggerDescriptor));
        }
        try {
            linkedHashMap = linkedHashMap2;
            if (linkedHashMap == null) {
                AllBranchesVcsTrigger.$$$reportNull$$$0(7);
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw AllBranchesVcsTrigger.a(numberFormatException);
        }
        return linkedHashMap;
    }

    private int getBranchLimit(@NotNull BuildTypeEx buildTypeEx) {
        try {
            if (buildTypeEx == null) {
                AllBranchesVcsTrigger.$$$reportNull$$$0(8);
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw AllBranchesVcsTrigger.a(numberFormatException);
        }
        String string = buildTypeEx.getInternalParameterValue("vcsTrigger.branchLimit", "10");
        try {
            return Integer.parseInt(string);
        }
        catch (NumberFormatException numberFormatException) {
            return 10;
        }
    }

    @Nullable
    public Map<String, String> getTriggerStateProperties(@NotNull PolledTriggerContext polledTriggerContext) {
        try {
            if (polledTriggerContext == null) {
                AllBranchesVcsTrigger.$$$reportNull$$$0(9);
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw AllBranchesVcsTrigger.a(numberFormatException);
        }
        String string = this.myBranchFilterHelper.getNormalizedBranchFilterAsString(polledTriggerContext.getTriggerDescriptor());
        try {
            if (string == null) {
                return null;
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw AllBranchesVcsTrigger.a(numberFormatException);
        }
        return Collections.singletonMap("branchFilter", string);
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

    private static RuntimeException a(RuntimeException runtimeException) {
        return runtimeException;
    }
}
