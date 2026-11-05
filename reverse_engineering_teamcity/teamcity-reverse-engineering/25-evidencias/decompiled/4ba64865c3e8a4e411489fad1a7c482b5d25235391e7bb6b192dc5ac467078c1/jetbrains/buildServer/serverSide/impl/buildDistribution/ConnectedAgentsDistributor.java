/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.intellij.openapi.util.Pair
 *  jetbrains.buildServer.AgentRestrictor
 *  jetbrains.buildServer.DevelopmentMode
 *  jetbrains.buildServer.ExtensionHolder
 *  jetbrains.buildServer.log.Loggers
 *  jetbrains.buildServer.serverSide.BuildPromotionEx
 *  jetbrains.buildServer.serverSide.BuildTypeNotFoundException
 *  jetbrains.buildServer.serverSide.SBuildAgent
 *  jetbrains.buildServer.serverSide.TeamCityProperties
 *  jetbrains.buildServer.serverSide.agentTypes.SAgentType
 *  jetbrains.buildServer.serverSide.buildDistribution.AgentsFilterContext
 *  jetbrains.buildServer.serverSide.buildDistribution.AgentsFilterResult
 *  jetbrains.buildServer.serverSide.buildDistribution.BuildDistributorInput
 *  jetbrains.buildServer.serverSide.buildDistribution.QueuedBuildInfo
 *  jetbrains.buildServer.serverSide.buildDistribution.SimpleWaitReason
 *  jetbrains.buildServer.serverSide.buildDistribution.WaitReason
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.BuildDistributorInputEx
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.CompatibleAgentsResult
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.ConnectedAgentsDistributor$CompatibleAgentsCalculator
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.ConnectedAgentsDistributor$CompatibleAgentsCalculatorWithFullReasons
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.ConnectedAgentsDistributor$CompatibleAgentsCalculatorWithPartialReasons
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.DefaultAgentsFilter
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.Distributor
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.StandardWaitReasons
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.agentsComparators.BuildAgentsComparatorProvider
 *  jetbrains.buildServer.util.impl.Lazy
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package jetbrains.buildServer.serverSide.impl.buildDistribution;

import com.intellij.openapi.util.Pair;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import jetbrains.buildServer.AgentRestrictor;
import jetbrains.buildServer.DevelopmentMode;
import jetbrains.buildServer.ExtensionHolder;
import jetbrains.buildServer.log.Loggers;
import jetbrains.buildServer.serverSide.BuildPromotionEx;
import jetbrains.buildServer.serverSide.BuildTypeNotFoundException;
import jetbrains.buildServer.serverSide.SBuildAgent;
import jetbrains.buildServer.serverSide.TeamCityProperties;
import jetbrains.buildServer.serverSide.agentTypes.SAgentType;
import jetbrains.buildServer.serverSide.buildDistribution.AgentsFilterContext;
import jetbrains.buildServer.serverSide.buildDistribution.AgentsFilterResult;
import jetbrains.buildServer.serverSide.buildDistribution.BuildDistributorInput;
import jetbrains.buildServer.serverSide.buildDistribution.QueuedBuildInfo;
import jetbrains.buildServer.serverSide.buildDistribution.SimpleWaitReason;
import jetbrains.buildServer.serverSide.buildDistribution.WaitReason;
import jetbrains.buildServer.serverSide.impl.buildDistribution.BuildDistributorInputEx;
import jetbrains.buildServer.serverSide.impl.buildDistribution.CompatibleAgentsResult;
import jetbrains.buildServer.serverSide.impl.buildDistribution.ConnectedAgentsDistributor;
import jetbrains.buildServer.serverSide.impl.buildDistribution.DefaultAgentsFilter;
import jetbrains.buildServer.serverSide.impl.buildDistribution.Distributor;
import jetbrains.buildServer.serverSide.impl.buildDistribution.StandardWaitReasons;
import jetbrains.buildServer.serverSide.impl.buildDistribution.agentsComparators.BuildAgentsComparatorProvider;
import jetbrains.buildServer.util.impl.Lazy;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Exception performing whole class analysis ignored.
 */
public class ConnectedAgentsDistributor
implements Distributor<SBuildAgent> {
    public static final String ALLOW_START_BUIDLS_ON_OUTDATED_AGENTS = "teamcity.buildsDistribution.allowStartBuidlsOnOutdatedAgents";
    public static final String DISTRIBUTE_TO_AGENTS_WHERE_BUILDS_ARE_FINISHING_SOON = "teamcity.buildsDistribution.distributeToAgentsWithFinishingBuilds";
    public static final String SOON_TO_BECOME_IDLE_MAX_TIME_LEFT_SECONDS = "teamcity.buildsDistribution.soonToBecomeIdle.maxTimeLeft.seconds";
    @NotNull
    private final Lazy<CompatibleAgentsCalculator> myCompatibleAgentsCalculator;
    @NotNull
    private final BuildAgentsComparatorProvider myAgentsComparatorProvider;
    private final boolean myEmulationMode;
    @NotNull
    private final DefaultAgentsFilter myDefaultAgentsFilter;
    static final /* synthetic */ boolean $assertionsDisabled;

    public ConnectedAgentsDistributor(@NotNull BuildDistributorInputEx buildDistributorInputEx, @Nullable Map<QueuedBuildInfo, WaitReason> map, boolean bl, @NotNull ExtensionHolder extensionHolder, @NotNull BuildAgentsComparatorProvider buildAgentsComparatorProvider) {
        if (buildDistributorInputEx == null) {
            ConnectedAgentsDistributor.$$$reportNull$$$0(0);
        }
        if (extensionHolder == null) {
            ConnectedAgentsDistributor.$$$reportNull$$$0(1);
        }
        if (buildAgentsComparatorProvider == null) {
            ConnectedAgentsDistributor.$$$reportNull$$$0(2);
        }
        this.myEmulationMode = bl;
        this.myCompatibleAgentsCalculator = map == null ? Lazy.create(() -> new CompatibleAgentsCalculatorWithPartialReasons(this, buildDistributorInputEx)) : Lazy.create(() -> new CompatibleAgentsCalculatorWithFullReasons(this, (BuildDistributorInput)buildDistributorInputEx));
        this.myAgentsComparatorProvider = buildAgentsComparatorProvider;
        this.myDefaultAgentsFilter = new DefaultAgentsFilter(extensionHolder);
    }

    @NotNull
    public CompatibleAgentsResult<SBuildAgent> getCompatibleCandidates(@NotNull QueuedBuildInfo queuedBuildInfo, @NotNull Set<SBuildAgent> set) {
        CompatibleAgentsResult compatibleAgentsResult;
        try {
            if (queuedBuildInfo == null) {
                ConnectedAgentsDistributor.$$$reportNull$$$0(3);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw ConnectedAgentsDistributor.a(buildTypeNotFoundException);
        }
        try {
            if (set == null) {
                ConnectedAgentsDistributor.$$$reportNull$$$0(4);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw ConnectedAgentsDistributor.a(buildTypeNotFoundException);
        }
        try {
            compatibleAgentsResult = ((CompatibleAgentsCalculator)this.myCompatibleAgentsCalculator.get()).getCompatibleAgents(queuedBuildInfo, set);
            if (compatibleAgentsResult == null) {
                ConnectedAgentsDistributor.$$$reportNull$$$0(5);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw ConnectedAgentsDistributor.a(buildTypeNotFoundException);
        }
        return compatibleAgentsResult;
    }

    public WaitReason filterAvailableAgents(@NotNull BuildDistributorInput buildDistributorInput, @NotNull QueuedBuildInfo queuedBuildInfo, @NotNull List<SBuildAgent> list, @NotNull Map<QueuedBuildInfo, SBuildAgent> map, @NotNull Map<String, Object> map2) {
        List list2;
        block34: {
            block35: {
                AgentsFilterResult agentsFilterResult;
                block32: {
                    WaitReason waitReason;
                    block33: {
                        try {
                            if (buildDistributorInput == null) {
                                ConnectedAgentsDistributor.$$$reportNull$$$0(6);
                            }
                        }
                        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                            throw ConnectedAgentsDistributor.a(buildTypeNotFoundException);
                        }
                        try {
                            if (queuedBuildInfo == null) {
                                ConnectedAgentsDistributor.$$$reportNull$$$0(7);
                            }
                        }
                        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                            throw ConnectedAgentsDistributor.a(buildTypeNotFoundException);
                        }
                        try {
                            if (list == null) {
                                ConnectedAgentsDistributor.$$$reportNull$$$0(8);
                            }
                        }
                        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                            throw ConnectedAgentsDistributor.a(buildTypeNotFoundException);
                        }
                        try {
                            if (map == null) {
                                ConnectedAgentsDistributor.$$$reportNull$$$0(9);
                            }
                        }
                        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                            throw ConnectedAgentsDistributor.a(buildTypeNotFoundException);
                        }
                        try {
                            if (map2 == null) {
                                ConnectedAgentsDistributor.$$$reportNull$$$0(10);
                            }
                        }
                        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                            throw ConnectedAgentsDistributor.a(buildTypeNotFoundException);
                        }
                        agentsFilterResult = this.myDefaultAgentsFilter.filterAgents((AgentsFilterContext)new /* Unavailable Anonymous Inner Class!! */);
                        waitReason = agentsFilterResult.getWaitReason();
                        try {
                            try {
                                try {
                                    if (waitReason == null) break block32;
                                    if (this.myEmulationMode) break block33;
                                }
                                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                                    throw ConnectedAgentsDistributor.a(buildTypeNotFoundException);
                                }
                                if (!Loggers.SERVER.isDebugEnabled()) break block33;
                            }
                            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                                throw ConnectedAgentsDistributor.a(buildTypeNotFoundException);
                            }
                            Loggers.SERVER.debug("Extension blocked starting of the build " + String.valueOf(queuedBuildInfo) + ", reason: " + waitReason.getDescription());
                        }
                        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                            throw ConnectedAgentsDistributor.a(buildTypeNotFoundException);
                        }
                    }
                    return waitReason;
                }
                list2 = agentsFilterResult.getFilteredConnectedAgents();
                try {
                    try {
                        try {
                            try {
                                if (list2 == null || !list2.isEmpty()) break block34;
                            }
                            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                                throw ConnectedAgentsDistributor.a(buildTypeNotFoundException);
                            }
                            if (this.myEmulationMode) break block35;
                        }
                        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                            throw ConnectedAgentsDistributor.a(buildTypeNotFoundException);
                        }
                        if (!Loggers.SERVER.isDebugEnabled()) break block35;
                    }
                    catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                        throw ConnectedAgentsDistributor.a(buildTypeNotFoundException);
                    }
                    Loggers.SERVER.debug("Extension blocked starting of the build " + String.valueOf(queuedBuildInfo) + " but did not provide a reason, extension class: " + this.myDefaultAgentsFilter.getClass().getName());
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw ConnectedAgentsDistributor.a(buildTypeNotFoundException);
                }
            }
            return new SimpleWaitReason("Build startup blocked for an unknown reason, see teamcity-server.log for details");
        }
        try {
            if (list2 == null) {
                return null;
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw ConnectedAgentsDistributor.a(buildTypeNotFoundException);
        }
        list.clear();
        list.addAll(list2);
        return null;
    }

    public void sortAvailableAgents(@NotNull List<SBuildAgent> list, @NotNull QueuedBuildInfo queuedBuildInfo) {
        try {
            if (list == null) {
                ConnectedAgentsDistributor.$$$reportNull$$$0(11);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw ConnectedAgentsDistributor.a(buildTypeNotFoundException);
        }
        try {
            if (queuedBuildInfo == null) {
                ConnectedAgentsDistributor.$$$reportNull$$$0(12);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw ConnectedAgentsDistributor.a(buildTypeNotFoundException);
        }
        Comparator comparator = this.myAgentsComparatorProvider.getConnectedAgentsComparatorForQueuedBuild(queuedBuildInfo);
        Set set = ((CompatibleAgentsCalculator)this.myCompatibleAgentsCalculator.get()).getSoonToBecomeIdleAgents();
        if (!set.isEmpty()) {
            Comparator comparator2 = comparator;
            comparator = new /* Unavailable Anonymous Inner Class!! */;
        }
        list.sort(comparator);
    }

    public boolean canProcessQueuedBuild(@NotNull QueuedBuildInfo queuedBuildInfo, @NotNull Map<QueuedBuildInfo, WaitReason> map) {
        boolean bl;
        try {
            if (queuedBuildInfo == null) {
                ConnectedAgentsDistributor.$$$reportNull$$$0(13);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw ConnectedAgentsDistributor.a(buildTypeNotFoundException);
        }
        try {
            if (map == null) {
                ConnectedAgentsDistributor.$$$reportNull$$$0(14);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw ConnectedAgentsDistributor.a(buildTypeNotFoundException);
        }
        try {
            bl = !queuedBuildInfo.getBuildPromotionInfo().isAgentLessBuild();
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw ConnectedAgentsDistributor.a(buildTypeNotFoundException);
        }
        return bl;
    }

    @NotNull
    public Pair<SBuildAgent, WaitReason> chooseAgentForBuild(@NotNull QueuedBuildInfo queuedBuildInfo, @NotNull CompatibleAgentsResult<SBuildAgent> compatibleAgentsResult) {
        Pair pair;
        Object object;
        SBuildAgent sBuildAgent;
        SBuildAgent sBuildAgent2;
        block21: {
            Collection collection;
            block20: {
                try {
                    if (queuedBuildInfo == null) {
                        ConnectedAgentsDistributor.$$$reportNull$$$0(15);
                    }
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw ConnectedAgentsDistributor.a(buildTypeNotFoundException);
                }
                try {
                    if (compatibleAgentsResult == null) {
                        ConnectedAgentsDistributor.$$$reportNull$$$0(16);
                    }
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw ConnectedAgentsDistributor.a(buildTypeNotFoundException);
                }
                collection = compatibleAgentsResult.getAgents();
                try {
                    try {
                        if ($assertionsDisabled || !collection.isEmpty()) break block20;
                    }
                    catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                        throw ConnectedAgentsDistributor.a(buildTypeNotFoundException);
                    }
                    throw new AssertionError();
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw ConnectedAgentsDistributor.a(buildTypeNotFoundException);
                }
            }
            sBuildAgent2 = (SBuildAgent)collection.iterator().next();
            try {
                if (!sBuildAgent2.isCloudAgent() || !queuedBuildInfo.getCompatibleAgents(Collections.singleton(sBuildAgent2)).isEmpty()) break block21;
            }
            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                throw ConnectedAgentsDistributor.a(buildTypeNotFoundException);
            }
            HashSet hashSet = new HashSet(queuedBuildInfo.getCompatibleAgents(collection));
            if (hashSet.isEmpty()) {
                sBuildAgent2 = null;
            } else {
                for (SBuildAgent sBuildAgent3 : collection) {
                    if (!hashSet.contains(sBuildAgent3)) continue;
                    sBuildAgent2 = sBuildAgent3;
                    break;
                }
            }
        }
        try {
            sBuildAgent = sBuildAgent2;
            object = sBuildAgent2 == null ? StandardWaitReasons.NO_COMPATIBLE_AGENTS_AVAILABLE : null;
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw ConnectedAgentsDistributor.a(buildTypeNotFoundException);
        }
        try {
            pair = Pair.create((Object)sBuildAgent, (Object)object);
            if (pair == null) {
                ConnectedAgentsDistributor.$$$reportNull$$$0(17);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw ConnectedAgentsDistributor.a(buildTypeNotFoundException);
        }
        return pair;
    }

    @NotNull
    public String getName() {
        String string;
        try {
            string = this.getClass().getName();
            if (string == null) {
                ConnectedAgentsDistributor.$$$reportNull$$$0(18);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw ConnectedAgentsDistributor.a(buildTypeNotFoundException);
        }
        return string;
    }

    @NotNull
    private Collection<SBuildAgent> getQueuedBuildCompatibleAgents(@NotNull QueuedBuildInfo queuedBuildInfo, @NotNull Map<SAgentType, List<SBuildAgent>> map) {
        ArrayList<SBuildAgent> arrayList;
        try {
            if (queuedBuildInfo == null) {
                ConnectedAgentsDistributor.$$$reportNull$$$0(19);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw ConnectedAgentsDistributor.a(buildTypeNotFoundException);
        }
        try {
            if (map == null) {
                ConnectedAgentsDistributor.$$$reportNull$$$0(20);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw ConnectedAgentsDistributor.a(buildTypeNotFoundException);
        }
        BuildPromotionEx buildPromotionEx = (BuildPromotionEx)queuedBuildInfo.getBuildPromotionInfo();
        try {
            ArrayList arrayList2 = new ArrayList();
            ArrayList arrayList3 = new ArrayList();
            map.forEach((sAgentType, list3) -> {
                for (SBuildAgent sBuildAgent : list3) {
                    try {
                        if (sBuildAgent.isCloudAgent()) {
                            arrayList3.add(sAgentType);
                            break;
                        }
                    }
                    catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                        throw ConnectedAgentsDistributor.a(buildTypeNotFoundException);
                    }
                    arrayList2.add(sBuildAgent);
                }
            });
            ArrayList<SBuildAgent> arrayList4 = new ArrayList<SBuildAgent>();
            try {
                if (!arrayList2.isEmpty()) {
                    arrayList4.addAll(buildPromotionEx.getCompatibleAgents(arrayList2));
                }
            }
            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                throw ConnectedAgentsDistributor.a(buildTypeNotFoundException);
            }
            if (!arrayList3.isEmpty()) {
                for (SAgentType sAgentType2 : buildPromotionEx.getCompatibleAgentTypes(arrayList3)) {
                    arrayList4.addAll((Collection<SBuildAgent>)map.get(sAgentType2));
                }
            }
            arrayList = arrayList4;
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            List<SBuildAgent> list;
            try {
                Loggers.SERVER.warn("Cannot compute compatible agents for queued build with id: " + queuedBuildInfo.getItemId() + ", build type id: " + buildPromotionEx.getBuildTypeExternalId() + ", because the build configuration does not exist");
                list = Collections.emptyList();
                if (list == null) {
                    ConnectedAgentsDistributor.$$$reportNull$$$0(22);
                }
            }
            catch (BuildTypeNotFoundException buildTypeNotFoundException2) {
                throw ConnectedAgentsDistributor.a(buildTypeNotFoundException2);
            }
            return list;
        }
        if (arrayList == null) {
            ConnectedAgentsDistributor.$$$reportNull$$$0(21);
        }
        return arrayList;
    }

    private static boolean agentIsOutdated(@NotNull SBuildAgent sBuildAgent) {
        boolean bl;
        block8: {
            block7: {
                try {
                    if (sBuildAgent == null) {
                        ConnectedAgentsDistributor.$$$reportNull$$$0(23);
                    }
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw ConnectedAgentsDistributor.a(buildTypeNotFoundException);
                }
                try {
                    try {
                        if (!sBuildAgent.isOutdated() && !sBuildAgent.isPluginsOutdated()) break block7;
                    }
                    catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                        throw ConnectedAgentsDistributor.a(buildTypeNotFoundException);
                    }
                    bl = true;
                    break block8;
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw ConnectedAgentsDistributor.a(buildTypeNotFoundException);
                }
            }
            bl = false;
        }
        return bl;
    }

    /*
     * Exception decompiling
     */
    @NotNull
    private static CompatibleAgentsResult<SBuildAgent> getNoAgentsResult(@Nullable AgentRestrictor var0) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [1[TRYBLOCK]], but top level block is 2[SWITCH]
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:435)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:484)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doClass(Driver.java:84)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:78)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    public boolean shouldFilterOutOutdatedAgents() {
        boolean bl;
        block5: {
            block4: {
                try {
                    try {
                        if (DevelopmentMode.isEnabled || TeamCityProperties.getBoolean((String)"teamcity.buildsDistribution.allowStartBuidlsOnOutdatedAgents")) break block4;
                    }
                    catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                        throw ConnectedAgentsDistributor.a(buildTypeNotFoundException);
                    }
                    bl = true;
                    break block5;
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw ConnectedAgentsDistributor.a(buildTypeNotFoundException);
                }
            }
            bl = false;
        }
        return bl;
    }

    static {
        boolean bl;
        try {
            bl = !ConnectedAgentsDistributor.class.desiredAssertionStatus();
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw ConnectedAgentsDistributor.a(buildTypeNotFoundException);
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

    private static BuildTypeNotFoundException a(BuildTypeNotFoundException buildTypeNotFoundException) {
        return buildTypeNotFoundException;
    }
}
