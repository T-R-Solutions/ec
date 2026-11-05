/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jetbrains.buildServer.serverSide.BuildPromotion
 *  jetbrains.buildServer.serverSide.BuildPromotionEx
 *  jetbrains.buildServer.serverSide.BuildPromotionManager
 *  jetbrains.buildServer.serverSide.SBuildAgent
 *  jetbrains.buildServer.serverSide.SQueuedBuild
 *  jetbrains.buildServer.serverSide.buildDistribution.AgentsFilterContext
 *  jetbrains.buildServer.serverSide.buildDistribution.AgentsFilterResult
 *  jetbrains.buildServer.serverSide.buildDistribution.QueuedBuildInfo
 *  jetbrains.buildServer.serverSide.buildDistribution.SimpleWaitReason
 *  jetbrains.buildServer.serverSide.buildDistribution.StartingBuildAgentsFilter
 *  jetbrains.buildServer.serverSide.buildDistribution.WaitReason
 *  jetbrains.buildServer.serverSide.dependency.BuildDependency
 *  jetbrains.buildServer.serverSide.dependency.DependencyOptions
 *  jetbrains.buildServer.util.filters.Filter
 *  jetbrains.buildServer.util.filters.FilterUtil
 *  org.jetbrains.annotations.NotNull
 */
package jetbrains.buildServer.serverSide.impl.buildDistribution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import jetbrains.buildServer.serverSide.BuildPromotion;
import jetbrains.buildServer.serverSide.BuildPromotionEx;
import jetbrains.buildServer.serverSide.BuildPromotionManager;
import jetbrains.buildServer.serverSide.SBuildAgent;
import jetbrains.buildServer.serverSide.SQueuedBuild;
import jetbrains.buildServer.serverSide.buildDistribution.AgentsFilterContext;
import jetbrains.buildServer.serverSide.buildDistribution.AgentsFilterResult;
import jetbrains.buildServer.serverSide.buildDistribution.QueuedBuildInfo;
import jetbrains.buildServer.serverSide.buildDistribution.SimpleWaitReason;
import jetbrains.buildServer.serverSide.buildDistribution.StartingBuildAgentsFilter;
import jetbrains.buildServer.serverSide.buildDistribution.WaitReason;
import jetbrains.buildServer.serverSide.dependency.BuildDependency;
import jetbrains.buildServer.serverSide.dependency.DependencyOptions;
import jetbrains.buildServer.util.filters.Filter;
import jetbrains.buildServer.util.filters.FilterUtil;
import org.jetbrains.annotations.NotNull;

/*
 * Exception performing whole class analysis ignored.
 */
public class RunOnTheSameAgentFilter
implements StartingBuildAgentsFilter {
    public static final String WAITING_FOR_AGENT_FROM_INTERSECTED_SET = "There are no agents suitable for this build and builds depending on it.";
    public static final String WAITING_FOR_AGENT_WHERE_DEPENDENCY_STARTED = "This build is waiting for the agent used for building its dependency.";
    private final BuildPromotionManager myPromotionManager;

    public RunOnTheSameAgentFilter(BuildPromotionManager buildPromotionManager) {
        this.myPromotionManager = buildPromotionManager;
    }

    @NotNull
    public AgentsFilterResult filterAgents(@NotNull AgentsFilterContext agentsFilterContext) {
        AgentsFilterResult agentsFilterResult;
        Collection<BuildPromotion> collection;
        BuildPromotionEx buildPromotionEx;
        AgentsFilterResult agentsFilterResult2;
        List<SBuildAgent> list;
        block33: {
            AgentsFilterResult agentsFilterResult3;
            block34: {
                block31: {
                    AgentsFilterResult agentsFilterResult4;
                    block32: {
                        block29: {
                            AgentsFilterResult agentsFilterResult5;
                            block30: {
                                try {
                                    if (agentsFilterContext == null) {
                                        RunOnTheSameAgentFilter.$$$reportNull$$$0(0);
                                    }
                                }
                                catch (IllegalArgumentException illegalArgumentException) {
                                    throw RunOnTheSameAgentFilter.a(illegalArgumentException);
                                }
                                list = new ArrayList<SBuildAgent>(agentsFilterContext.getAgentsForStartingBuild());
                                agentsFilterResult2 = new AgentsFilterResult();
                                QueuedBuildInfo queuedBuildInfo = agentsFilterContext.getStartingBuild();
                                buildPromotionEx = this.findBuildPromotion(queuedBuildInfo);
                                try {
                                    try {
                                        if (buildPromotionEx != null) break block29;
                                        agentsFilterResult2.setWaitReason((WaitReason)new SimpleWaitReason("Cannot find build promotion for the queued build: " + queuedBuildInfo.describe(false)));
                                        agentsFilterResult5 = agentsFilterResult2;
                                        if (agentsFilterResult5 != null) break block30;
                                    }
                                    catch (IllegalArgumentException illegalArgumentException) {
                                        throw RunOnTheSameAgentFilter.a(illegalArgumentException);
                                    }
                                    RunOnTheSameAgentFilter.$$$reportNull$$$0(1);
                                }
                                catch (IllegalArgumentException illegalArgumentException) {
                                    throw RunOnTheSameAgentFilter.a(illegalArgumentException);
                                }
                            }
                            return agentsFilterResult5;
                        }
                        try {
                            try {
                                if (buildPromotionEx.isPartOfBuildChain()) break block31;
                                agentsFilterResult4 = agentsFilterResult2;
                                if (agentsFilterResult4 != null) break block32;
                            }
                            catch (IllegalArgumentException illegalArgumentException) {
                                throw RunOnTheSameAgentFilter.a(illegalArgumentException);
                            }
                            RunOnTheSameAgentFilter.$$$reportNull$$$0(2);
                        }
                        catch (IllegalArgumentException illegalArgumentException) {
                            throw RunOnTheSameAgentFilter.a(illegalArgumentException);
                        }
                    }
                    return agentsFilterResult4;
                }
                collection = this.findRelatedSameAgentDependencies(buildPromotionEx, agentsFilterContext);
                try {
                    try {
                        if (!collection.isEmpty()) break block33;
                        agentsFilterResult3 = agentsFilterResult2;
                        if (agentsFilterResult3 != null) break block34;
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        throw RunOnTheSameAgentFilter.a(illegalArgumentException);
                    }
                    RunOnTheSameAgentFilter.$$$reportNull$$$0(3);
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw RunOnTheSameAgentFilter.a(illegalArgumentException);
                }
            }
            return agentsFilterResult3;
        }
        if ((list = this.intersectRelatedPromotionsAgents(collection, list, agentsFilterContext)).isEmpty()) {
            AgentsFilterResult agentsFilterResult6;
            SimpleWaitReason simpleWaitReason = null;
            for (BuildDependency buildDependency : buildPromotionEx.getDependencies()) {
                BuildPromotion buildPromotion = buildDependency.getDependOn();
                try {
                    if (!collection.contains(buildPromotion) || buildPromotion.getAssociatedBuildId() == null) continue;
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw RunOnTheSameAgentFilter.a(illegalArgumentException);
                }
                simpleWaitReason = new SimpleWaitReason("This build is waiting for the agent used for building its dependency.");
            }
            if (simpleWaitReason == null) {
                simpleWaitReason = new SimpleWaitReason("There are no agents suitable for this build and builds depending on it.");
            }
            try {
                agentsFilterResult2.setFilteredConnectedAgents(list);
                agentsFilterResult2.setWaitReason((WaitReason)simpleWaitReason);
                agentsFilterResult6 = agentsFilterResult2;
                if (agentsFilterResult6 == null) {
                    RunOnTheSameAgentFilter.$$$reportNull$$$0(4);
                }
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw RunOnTheSameAgentFilter.a(illegalArgumentException);
            }
            return agentsFilterResult6;
        }
        try {
            list.removeAll(agentsFilterContext.getDistributedBuilds().values());
            agentsFilterResult2.setFilteredConnectedAgents(list);
            if (list.isEmpty()) {
                agentsFilterResult2.setWaitReason((WaitReason)new SimpleWaitReason("This build cannot start because it's agents are reserved by other builds."));
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunOnTheSameAgentFilter.a(illegalArgumentException);
        }
        try {
            agentsFilterResult = agentsFilterResult2;
            if (agentsFilterResult == null) {
                RunOnTheSameAgentFilter.$$$reportNull$$$0(5);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunOnTheSameAgentFilter.a(illegalArgumentException);
        }
        return agentsFilterResult;
    }

    private List<SBuildAgent> intersectRelatedPromotionsAgents(@NotNull Collection<BuildPromotion> collection, @NotNull List<SBuildAgent> list, @NotNull AgentsFilterContext agentsFilterContext) {
        try {
            if (collection == null) {
                RunOnTheSameAgentFilter.$$$reportNull$$$0(6);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunOnTheSameAgentFilter.a(illegalArgumentException);
        }
        try {
            if (list == null) {
                RunOnTheSameAgentFilter.$$$reportNull$$$0(7);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunOnTheSameAgentFilter.a(illegalArgumentException);
        }
        try {
            if (agentsFilterContext == null) {
                RunOnTheSameAgentFilter.$$$reportNull$$$0(8);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunOnTheSameAgentFilter.a(illegalArgumentException);
        }
        try {
            if (collection.isEmpty()) {
                return list;
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunOnTheSameAgentFilter.a(illegalArgumentException);
        }
        List<SBuildAgent> list2 = list;
        for (BuildPromotion buildPromotion : collection) {
            block34: {
                QueuedBuildInfo queuedBuildInfo2;
                try {
                    if (buildPromotion.isCompositeBuild()) {
                        continue;
                    }
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw RunOnTheSameAgentFilter.a(illegalArgumentException);
                }
                boolean bl = false;
                for (QueuedBuildInfo queuedBuildInfo2 : agentsFilterContext.getDistributedBuilds().keySet()) {
                    if (queuedBuildInfo2.getBuildPromotionInfo().getId() != buildPromotion.getId()) continue;
                    list2 = this.intersect(list2, Collections.singleton((SBuildAgent)agentsFilterContext.getDistributedBuilds().get(queuedBuildInfo2)));
                    bl = true;
                    break;
                }
                try {
                    if (list2.isEmpty()) {
                        break;
                    }
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw RunOnTheSameAgentFilter.a(illegalArgumentException);
                }
                try {
                    if (bl) {
                        continue;
                    }
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw RunOnTheSameAgentFilter.a(illegalArgumentException);
                }
                SQueuedBuild sQueuedBuild = buildPromotion.getQueuedBuild();
                if (sQueuedBuild != null) {
                    queuedBuildInfo2 = sQueuedBuild.getAgentRestrictor();
                    list2 = queuedBuildInfo2 != null ? (List<SBuildAgent>)FilterUtil.filterAndCopy(list2, new ArrayList(), (Filter)queuedBuildInfo2) : this.intersect(list2, buildPromotion.getCanRunOnAgents(list2));
                    bl = true;
                }
                try {
                    if (list2.isEmpty()) {
                        break;
                    }
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw RunOnTheSameAgentFilter.a(illegalArgumentException);
                }
                try {
                    if (bl) {
                        continue;
                    }
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw RunOnTheSameAgentFilter.a(illegalArgumentException);
                }
                queuedBuildInfo2 = buildPromotion.getAssociatedBuild();
                try {
                    if (queuedBuildInfo2 == null || queuedBuildInfo2.getCanceledInfo() != null) break block34;
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw RunOnTheSameAgentFilter.a(illegalArgumentException);
                }
                list2 = this.intersect((Collection<SBuildAgent>)list2, Collections.singleton(queuedBuildInfo2.getAgent()));
            }
            try {
                if (!list2.isEmpty()) continue;
                break;
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw RunOnTheSameAgentFilter.a(illegalArgumentException);
            }
        }
        return list2;
    }

    @NotNull
    private Collection<BuildPromotion> findRelatedSameAgentDependencies(@NotNull BuildPromotionEx buildPromotionEx, @NotNull AgentsFilterContext agentsFilterContext) {
        HashSet<BuildPromotion> hashSet;
        try {
            if (buildPromotionEx == null) {
                RunOnTheSameAgentFilter.$$$reportNull$$$0(9);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunOnTheSameAgentFilter.a(illegalArgumentException);
        }
        try {
            if (agentsFilterContext == null) {
                RunOnTheSameAgentFilter.$$$reportNull$$$0(10);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunOnTheSameAgentFilter.a(illegalArgumentException);
        }
        HashSet<BuildPromotion> hashSet2 = new HashSet<BuildPromotion>();
        List<Set<BuildPromotion>> list = this.getSameAgentGroups(buildPromotionEx.findTops(), agentsFilterContext);
        for (Set<BuildPromotion> set : list) {
            try {
                if (!set.contains(buildPromotionEx)) continue;
                hashSet2.addAll(set);
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw RunOnTheSameAgentFilter.a(illegalArgumentException);
            }
        }
        try {
            hashSet = hashSet2;
            if (hashSet == null) {
                RunOnTheSameAgentFilter.$$$reportNull$$$0(11);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunOnTheSameAgentFilter.a(illegalArgumentException);
        }
        return hashSet;
    }

    @NotNull
    private List<Set<BuildPromotion>> getSameAgentGroups(@NotNull BuildPromotionEx[] buildPromotionExArray, @NotNull AgentsFilterContext agentsFilterContext) {
        ArrayList<Set<BuildPromotion>> arrayList;
        try {
            if (buildPromotionExArray == null) {
                RunOnTheSameAgentFilter.$$$reportNull$$$0(12);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunOnTheSameAgentFilter.a(illegalArgumentException);
        }
        try {
            if (agentsFilterContext == null) {
                RunOnTheSameAgentFilter.$$$reportNull$$$0(13);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunOnTheSameAgentFilter.a(illegalArgumentException);
        }
        Arrays.sort(buildPromotionExArray, Comparator.comparingLong(BuildPromotion::getId));
        StringBuilder stringBuilder = new StringBuilder("runOnTheSameAgent:");
        for (BuildPromotionEx buildPromotionEx : buildPromotionExArray) {
            stringBuilder.append(buildPromotionEx.getId()).append(":");
        }
        String string = stringBuilder.toString();
        ArrayList<Set<BuildPromotion>> arrayList2 = (ArrayList<Set<BuildPromotion>>)agentsFilterContext.getCustomData(string);
        if (arrayList2 != null) {
            block27: {
                ArrayList<Set<BuildPromotion>> arrayList3;
                block28: {
                    int hashSet = 1;
                    for (Set set : arrayList2) {
                        for (BuildPromotion buildPromotion2 : set) {
                            if (!((BuildPromotionEx)buildPromotion2).isDeleted()) continue;
                            hashSet = 0;
                            break;
                        }
                        try {
                            if (hashSet != 0) continue;
                            break;
                        }
                        catch (IllegalArgumentException illegalArgumentException) {
                            throw RunOnTheSameAgentFilter.a(illegalArgumentException);
                        }
                    }
                    try {
                        try {
                            if (hashSet == 0) break block27;
                            arrayList3 = arrayList2;
                            if (arrayList3 != null) break block28;
                        }
                        catch (IllegalArgumentException illegalArgumentException) {
                            throw RunOnTheSameAgentFilter.a(illegalArgumentException);
                        }
                        RunOnTheSameAgentFilter.$$$reportNull$$$0(14);
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        throw RunOnTheSameAgentFilter.a(illegalArgumentException);
                    }
                }
                return arrayList3;
            }
            agentsFilterContext.setCustomData(string, null);
        }
        arrayList2 = new ArrayList<Set<BuildPromotion>>();
        HashSet<BuildPromotion> hashSet = new HashSet<BuildPromotion>();
        LinkedList<BuildPromotionEx> linkedList = new LinkedList<BuildPromotionEx>(Arrays.asList(buildPromotionExArray));
        while (!linkedList.isEmpty()) {
            BuildPromotion buildPromotion = (BuildPromotion)linkedList.poll();
            try {
                if (!hashSet.add(buildPromotion)) {
                    continue;
                }
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw RunOnTheSameAgentFilter.a(illegalArgumentException);
            }
            for (BuildPromotion buildPromotion2 : buildPromotion.getDependencies()) {
                BuildPromotion buildPromotion3 = buildPromotion2.getDependOn();
                linkedList.add((BuildPromotionEx)buildPromotion3);
                if (!this.isSameAgentDependency((BuildDependency)buildPromotion2).booleanValue()) continue;
                boolean bl = false;
                for (Set set : arrayList2) {
                    try {
                        if (!set.contains(buildPromotion) && !set.contains(buildPromotion3)) continue;
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        throw RunOnTheSameAgentFilter.a(illegalArgumentException);
                    }
                    set.add(buildPromotion);
                    set.add(buildPromotion3);
                    bl = true;
                }
                if (bl) continue;
                HashSet hashSet2 = new HashSet();
                arrayList2.add(hashSet2);
                hashSet2.add(buildPromotion);
                hashSet2.add(buildPromotion3);
            }
        }
        try {
            agentsFilterContext.setCustomData(stringBuilder.toString(), arrayList2);
            arrayList = arrayList2;
            if (arrayList == null) {
                RunOnTheSameAgentFilter.$$$reportNull$$$0(15);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunOnTheSameAgentFilter.a(illegalArgumentException);
        }
        return arrayList;
    }

    private BuildPromotionEx findBuildPromotion(QueuedBuildInfo queuedBuildInfo) {
        long l = queuedBuildInfo.getBuildPromotionInfo().getId();
        return (BuildPromotionEx)this.myPromotionManager.findPromotionById(l);
    }

    private Boolean isSameAgentDependency(BuildDependency buildDependency) {
        return (Boolean)buildDependency.getOption(DependencyOptions.RUN_BUILD_ON_THE_SAME_AGENT);
    }

    private List<SBuildAgent> intersect(@NotNull Collection<SBuildAgent> collection, @NotNull Collection<SBuildAgent> collection2) {
        try {
            if (collection == null) {
                RunOnTheSameAgentFilter.$$$reportNull$$$0(16);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunOnTheSameAgentFilter.a(illegalArgumentException);
        }
        try {
            if (collection2 == null) {
                RunOnTheSameAgentFilter.$$$reportNull$$$0(17);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunOnTheSameAgentFilter.a(illegalArgumentException);
        }
        ArrayList<SBuildAgent> arrayList = new ArrayList<SBuildAgent>();
        for (SBuildAgent sBuildAgent : collection) {
            try {
                if (!collection2.contains(sBuildAgent)) continue;
                arrayList.add(sBuildAgent);
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw RunOnTheSameAgentFilter.a(illegalArgumentException);
            }
        }
        return arrayList;
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

    private static IllegalArgumentException a(IllegalArgumentException illegalArgumentException) {
        return illegalArgumentException;
    }
}
