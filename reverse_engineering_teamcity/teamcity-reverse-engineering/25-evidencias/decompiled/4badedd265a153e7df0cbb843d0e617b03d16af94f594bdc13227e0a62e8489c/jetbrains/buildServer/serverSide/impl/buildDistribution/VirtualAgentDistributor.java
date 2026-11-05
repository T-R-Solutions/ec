/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.intellij.openapi.util.Pair
 *  jetbrains.buildServer.ExtensionHolder
 *  jetbrains.buildServer.log.Loggers
 *  jetbrains.buildServer.serverSide.SBuildAgent
 *  jetbrains.buildServer.serverSide.SBuildType
 *  jetbrains.buildServer.serverSide.TeamCityProperties
 *  jetbrains.buildServer.serverSide.agentTypes.SAgentType
 *  jetbrains.buildServer.serverSide.buildDistribution.BuildDistributorInput
 *  jetbrains.buildServer.serverSide.buildDistribution.QueuedBuildInfo
 *  jetbrains.buildServer.serverSide.buildDistribution.SimpleWaitReason
 *  jetbrains.buildServer.serverSide.buildDistribution.WaitReason
 *  jetbrains.buildServer.serverSide.buildDistribution.WaitReasonEx
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.BuildTypePredicates
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.CompatibleAgentsResult
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.Distributor
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.StartingBuildVirtualAgentsFilter
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.VirtualAgentsFilterContext
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.VirtualAgentsFilterContextImpl
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.VirtualAgentsFilterResult
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.agentsComparators.BuildAgentsComparatorProvider
 *  jetbrains.buildServer.serverSide.impl.virtualAgent.StartableAgentType
 *  jetbrains.buildServer.serverSide.impl.virtualAgent.VirtualAgent
 *  jetbrains.buildServer.util.StringUtil
 *  jetbrains.buildServer.util.impl.Lazy
 *  org.jetbrains.annotations.NotNull
 */
package jetbrains.buildServer.serverSide.impl.buildDistribution;

import com.intellij.openapi.util.Pair;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Supplier;
import jetbrains.buildServer.ExtensionHolder;
import jetbrains.buildServer.log.Loggers;
import jetbrains.buildServer.serverSide.SBuildAgent;
import jetbrains.buildServer.serverSide.SBuildType;
import jetbrains.buildServer.serverSide.TeamCityProperties;
import jetbrains.buildServer.serverSide.agentTypes.SAgentType;
import jetbrains.buildServer.serverSide.buildDistribution.BuildDistributorInput;
import jetbrains.buildServer.serverSide.buildDistribution.QueuedBuildInfo;
import jetbrains.buildServer.serverSide.buildDistribution.SimpleWaitReason;
import jetbrains.buildServer.serverSide.buildDistribution.WaitReason;
import jetbrains.buildServer.serverSide.buildDistribution.WaitReasonEx;
import jetbrains.buildServer.serverSide.impl.buildDistribution.BuildTypePredicates;
import jetbrains.buildServer.serverSide.impl.buildDistribution.CompatibleAgentsResult;
import jetbrains.buildServer.serverSide.impl.buildDistribution.Distributor;
import jetbrains.buildServer.serverSide.impl.buildDistribution.StartingBuildVirtualAgentsFilter;
import jetbrains.buildServer.serverSide.impl.buildDistribution.VirtualAgentsFilterContext;
import jetbrains.buildServer.serverSide.impl.buildDistribution.VirtualAgentsFilterContextImpl;
import jetbrains.buildServer.serverSide.impl.buildDistribution.VirtualAgentsFilterResult;
import jetbrains.buildServer.serverSide.impl.buildDistribution.agentsComparators.BuildAgentsComparatorProvider;
import jetbrains.buildServer.serverSide.impl.virtualAgent.StartableAgentType;
import jetbrains.buildServer.serverSide.impl.virtualAgent.VirtualAgent;
import jetbrains.buildServer.util.StringUtil;
import jetbrains.buildServer.util.impl.Lazy;
import org.jetbrains.annotations.NotNull;

/*
 * Exception performing whole class analysis ignored.
 */
public class VirtualAgentDistributor
implements Distributor<VirtualAgent> {
    private static final CompatibleAgentsResult<VirtualAgent> NO_AGENTS;
    @NotNull
    private final Lazy<Map<Integer, List<VirtualAgent>>> myStartingAgents;
    @NotNull
    private final Lazy<Map<Integer, StartableAgentType>> myStartableAgents;
    @NotNull
    private final BuildAgentsComparatorProvider myAgentsComparatorProvider;
    private final boolean myEmulationMode;
    @NotNull
    private final Collection<StartingBuildVirtualAgentsFilter> myBuildAgentsFilters;
    private final Lazy<Map<Predicate<SBuildType>, List<SAgentType>>> myBuildTypePredicates;
    static final /* synthetic */ boolean $assertionsDisabled;

    public VirtualAgentDistributor(@NotNull Supplier<Collection<StartableAgentType>> supplier, @NotNull BuildAgentsComparatorProvider buildAgentsComparatorProvider, @NotNull ExtensionHolder extensionHolder, boolean bl) {
        if (supplier == null) {
            VirtualAgentDistributor.$$$reportNull$$$0(0);
        }
        if (buildAgentsComparatorProvider == null) {
            VirtualAgentDistributor.$$$reportNull$$$0(1);
        }
        if (extensionHolder == null) {
            VirtualAgentDistributor.$$$reportNull$$$0(2);
        }
        this.myStartingAgents = Lazy.create(() -> this.getStartingAgentsMap((Collection)supplier.get()));
        this.myStartableAgents = Lazy.create(() -> this.getStartableAgentTypesMap((Collection)supplier.get()));
        this.myEmulationMode = bl;
        this.myBuildTypePredicates = Lazy.create(() -> {
            HashSet<SAgentType> hashSet = new HashSet<SAgentType>();
            for (StartableAgentType startableAgentType : ((Map)this.myStartableAgents.get()).values()) {
                SAgentType sAgentType = startableAgentType.getAgentType();
                hashSet.add(sAgentType);
            }
            return BuildTypePredicates.createBuildTypePredicatesForAgentTypes(hashSet);
        });
        this.myAgentsComparatorProvider = buildAgentsComparatorProvider;
        this.myBuildAgentsFilters = extensionHolder.getExtensions(StartingBuildVirtualAgentsFilter.class);
    }

    /*
     * Exception decompiling
     */
    @NotNull
    public CompatibleAgentsResult<VirtualAgent> getCompatibleCandidates(@NotNull QueuedBuildInfo var1_1, @NotNull Set<VirtualAgent> var2_2) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [4[TRYBLOCK]], but top level block is 14[SWITCH]
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

    @NotNull
    private Set<String> getAgentTypesProvidingWithWaitReason() {
        List list;
        block4: {
            Set<String> set;
            block5: {
                list = StringUtil.split((String)TeamCityProperties.getProperty((String)"teamcity.agentTypes.thatProvideWaitReason", (String)""), (boolean)true, (char[])new char[]{','});
                try {
                    try {
                        if (!list.isEmpty()) break block4;
                        set = Collections.emptySet();
                        if (set != null) break block5;
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        throw VirtualAgentDistributor.a(illegalArgumentException);
                    }
                    VirtualAgentDistributor.$$$reportNull$$$0(7);
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw VirtualAgentDistributor.a(illegalArgumentException);
                }
            }
            return set;
        }
        return new HashSet<String>(list);
    }

    /*
     * Loose catch block
     */
    public WaitReason filterAvailableAgents(@NotNull BuildDistributorInput buildDistributorInput, @NotNull QueuedBuildInfo queuedBuildInfo, @NotNull List<VirtualAgent> list, @NotNull Map<QueuedBuildInfo, SBuildAgent> map, @NotNull Map<String, Object> map2) {
        block38: {
            try {
                if (buildDistributorInput == null) {
                    VirtualAgentDistributor.$$$reportNull$$$0(8);
                }
            }
            catch (Throwable throwable) {
                throw VirtualAgentDistributor.a(throwable);
            }
            try {
                if (queuedBuildInfo == null) {
                    VirtualAgentDistributor.$$$reportNull$$$0(9);
                }
            }
            catch (Throwable throwable) {
                throw VirtualAgentDistributor.a(throwable);
            }
            try {
                if (list == null) {
                    VirtualAgentDistributor.$$$reportNull$$$0(10);
                }
            }
            catch (Throwable throwable) {
                throw VirtualAgentDistributor.a(throwable);
            }
            try {
                if (map == null) {
                    VirtualAgentDistributor.$$$reportNull$$$0(11);
                }
            }
            catch (Throwable throwable) {
                throw VirtualAgentDistributor.a(throwable);
            }
            try {
                if (map2 == null) {
                    VirtualAgentDistributor.$$$reportNull$$$0(12);
                }
            }
            catch (Throwable throwable) {
                throw VirtualAgentDistributor.a(throwable);
            }
            if ($assertionsDisabled) break block38;
            try {
                block42: {
                    if (!list.isEmpty()) break block38;
                    break block42;
                    catch (Throwable throwable) {
                        throw VirtualAgentDistributor.a(throwable);
                    }
                }
                throw new AssertionError();
            }
            catch (Throwable throwable) {
                throw VirtualAgentDistributor.a(throwable);
            }
        }
        ArrayList<Object> arrayList = new ArrayList<VirtualAgent>(list);
        for (StartingBuildVirtualAgentsFilter startingBuildVirtualAgentsFilter : this.myBuildAgentsFilters) {
            block41: {
                VirtualAgentsFilterResult virtualAgentsFilterResult;
                block39: {
                    WaitReason waitReason;
                    block40: {
                        VirtualAgentsFilterContextImpl virtualAgentsFilterContextImpl = new VirtualAgentsFilterContextImpl(queuedBuildInfo, map2, map, buildDistributorInput, this.myEmulationMode, arrayList);
                        virtualAgentsFilterResult = null;
                        try {
                            virtualAgentsFilterResult = startingBuildVirtualAgentsFilter.filterAgents((VirtualAgentsFilterContext)virtualAgentsFilterContextImpl);
                        }
                        catch (Throwable throwable) {
                            Loggers.SERVER.warnAndDebugDetails("Exception in build agents filter extension: " + startingBuildVirtualAgentsFilter.getClass().getName(), throwable);
                        }
                        try {
                            if (virtualAgentsFilterResult == null) {
                                continue;
                            }
                        }
                        catch (Throwable throwable) {
                            throw VirtualAgentDistributor.a(throwable);
                        }
                        waitReason = virtualAgentsFilterResult.getWaitReason();
                        try {
                            try {
                                try {
                                    if (waitReason == null) break block39;
                                    list.clear();
                                    if (this.myEmulationMode) break block40;
                                }
                                catch (Throwable throwable) {
                                    throw VirtualAgentDistributor.a(throwable);
                                }
                                if (!Loggers.SERVER.isDebugEnabled()) break block40;
                            }
                            catch (Throwable throwable) {
                                throw VirtualAgentDistributor.a(throwable);
                            }
                            Loggers.SERVER.debug("Extension blocked starting of the build " + String.valueOf(queuedBuildInfo) + ", reason: " + waitReason.getDescription());
                        }
                        catch (Throwable throwable) {
                            throw VirtualAgentDistributor.a(throwable);
                        }
                    }
                    return waitReason;
                }
                if (virtualAgentsFilterResult.getFilteredVirtualAgents() != null) {
                    arrayList = new ArrayList(virtualAgentsFilterResult.getFilteredVirtualAgents());
                }
                try {
                    try {
                        try {
                            if (!arrayList.isEmpty()) continue;
                            if (this.myEmulationMode) break block41;
                        }
                        catch (Throwable throwable) {
                            throw VirtualAgentDistributor.a(throwable);
                        }
                        if (!Loggers.SERVER.isDebugEnabled()) break block41;
                    }
                    catch (Throwable throwable) {
                        throw VirtualAgentDistributor.a(throwable);
                    }
                    Loggers.SERVER.debug("Extension blocked starting of the build " + String.valueOf(queuedBuildInfo) + " but did not provide a reason, extension class: " + startingBuildVirtualAgentsFilter.getClass().getName());
                }
                catch (Throwable throwable) {
                    throw VirtualAgentDistributor.a(throwable);
                }
            }
            return new SimpleWaitReason("Build startup blocked for an unknown reason, see teamcity-server.log for details");
        }
        list.clear();
        list.addAll(arrayList);
        return null;
    }

    public void sortAvailableAgents(@NotNull List<VirtualAgent> list, @NotNull QueuedBuildInfo queuedBuildInfo) {
        try {
            if (list == null) {
                VirtualAgentDistributor.$$$reportNull$$$0(13);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VirtualAgentDistributor.a(illegalArgumentException);
        }
        try {
            if (queuedBuildInfo == null) {
                VirtualAgentDistributor.$$$reportNull$$$0(14);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VirtualAgentDistributor.a(illegalArgumentException);
        }
        list.sort(this.myAgentsComparatorProvider.getVirtualAgentsComparatorForQueuedBuild(queuedBuildInfo));
    }

    public boolean canProcessQueuedBuild(@NotNull QueuedBuildInfo queuedBuildInfo, @NotNull Map<QueuedBuildInfo, WaitReason> map) {
        block18: {
            WaitReason waitReason;
            block19: {
                try {
                    if (queuedBuildInfo == null) {
                        VirtualAgentDistributor.$$$reportNull$$$0(15);
                    }
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw VirtualAgentDistributor.a(illegalArgumentException);
                }
                try {
                    if (map == null) {
                        VirtualAgentDistributor.$$$reportNull$$$0(16);
                    }
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw VirtualAgentDistributor.a(illegalArgumentException);
                }
                try {
                    if (queuedBuildInfo.getBuildPromotionInfo().isAgentLessBuild()) {
                        return false;
                    }
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw VirtualAgentDistributor.a(illegalArgumentException);
                }
                waitReason = map.get(queuedBuildInfo);
                try {
                    try {
                        try {
                            if (waitReason == null) break block18;
                            if (!(waitReason instanceof WaitReasonEx)) break block19;
                        }
                        catch (IllegalArgumentException illegalArgumentException) {
                            throw VirtualAgentDistributor.a(illegalArgumentException);
                        }
                        if (!((WaitReasonEx)waitReason).notEnoughAgents()) break block19;
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        throw VirtualAgentDistributor.a(illegalArgumentException);
                    }
                    return true;
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw VirtualAgentDistributor.a(illegalArgumentException);
                }
            }
            try {
                if (Loggers.SERVER.isDebugEnabled()) {
                    Loggers.SERVER.debug(String.format("Won't distribute build '%s', because previous step returned: '%s'", queuedBuildInfo.describe(false), waitReason.getDescription()));
                }
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw VirtualAgentDistributor.a(illegalArgumentException);
            }
            return false;
        }
        return true;
    }

    @NotNull
    public Pair<VirtualAgent, WaitReason> chooseAgentForBuild(@NotNull QueuedBuildInfo queuedBuildInfo, @NotNull CompatibleAgentsResult<VirtualAgent> compatibleAgentsResult) {
        Pair pair;
        try {
            if (queuedBuildInfo == null) {
                VirtualAgentDistributor.$$$reportNull$$$0(17);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VirtualAgentDistributor.a(illegalArgumentException);
        }
        try {
            if (compatibleAgentsResult == null) {
                VirtualAgentDistributor.$$$reportNull$$$0(18);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VirtualAgentDistributor.a(illegalArgumentException);
        }
        try {
            pair = Pair.create((Object)compatibleAgentsResult.getAgents().stream().filter(virtualAgent -> {
                boolean bl;
                try {
                    bl = !virtualAgent.hasConnectedAgents();
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw VirtualAgentDistributor.a(illegalArgumentException);
                }
                return bl;
            }).findFirst().orElse(null), null);
            if (pair == null) {
                VirtualAgentDistributor.$$$reportNull$$$0(19);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VirtualAgentDistributor.a(illegalArgumentException);
        }
        return pair;
    }

    @NotNull
    public String getName() {
        String string;
        try {
            string = this.getClass().getName();
            if (string == null) {
                VirtualAgentDistributor.$$$reportNull$$$0(20);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VirtualAgentDistributor.a(illegalArgumentException);
        }
        return string;
    }

    @NotNull
    private Collection<VirtualAgent> getStartingAgentsMap(@NotNull StartableAgentType startableAgentType) {
        ArrayList<VirtualAgent> arrayList;
        try {
            if (startableAgentType == null) {
                VirtualAgentDistributor.$$$reportNull$$$0(21);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VirtualAgentDistributor.a(illegalArgumentException);
        }
        ArrayList<VirtualAgent> arrayList2 = new ArrayList<VirtualAgent>();
        for (VirtualAgent virtualAgent : startableAgentType.getRunningVirtualAgents()) {
            try {
                try {
                    if (virtualAgent.hasRunningBuilds() || virtualAgent.isDisabled()) continue;
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw VirtualAgentDistributor.a(illegalArgumentException);
                }
                arrayList2.add(virtualAgent);
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw VirtualAgentDistributor.a(illegalArgumentException);
            }
        }
        try {
            arrayList = arrayList2;
            if (arrayList == null) {
                VirtualAgentDistributor.$$$reportNull$$$0(22);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VirtualAgentDistributor.a(illegalArgumentException);
        }
        return arrayList;
    }

    @NotNull
    private Map<Integer, List<VirtualAgent>> getStartingAgentsMap(@NotNull Collection<StartableAgentType> collection) {
        HashMap<Integer, List<VirtualAgent>> hashMap;
        try {
            if (collection == null) {
                VirtualAgentDistributor.$$$reportNull$$$0(23);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VirtualAgentDistributor.a(illegalArgumentException);
        }
        HashMap<Integer, List<VirtualAgent>> hashMap2 = new HashMap<Integer, List<VirtualAgent>>();
        for (StartableAgentType startableAgentType : collection) {
            hashMap2.computeIfAbsent(startableAgentType.getAgentType().getAgentTypeId(), n -> new ArrayList()).addAll(this.getStartingAgentsMap(startableAgentType));
        }
        try {
            hashMap = hashMap2;
            if (hashMap == null) {
                VirtualAgentDistributor.$$$reportNull$$$0(24);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VirtualAgentDistributor.a(illegalArgumentException);
        }
        return hashMap;
    }

    @NotNull
    private Map<Integer, StartableAgentType> getStartableAgentTypesMap(@NotNull Collection<StartableAgentType> collection) {
        HashMap<Integer, StartableAgentType> hashMap;
        try {
            if (collection == null) {
                VirtualAgentDistributor.$$$reportNull$$$0(25);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VirtualAgentDistributor.a(illegalArgumentException);
        }
        HashMap<Integer, StartableAgentType> hashMap2 = new HashMap<Integer, StartableAgentType>();
        for (StartableAgentType startableAgentType : collection) {
            hashMap2.put(startableAgentType.getAgentType().getAgentTypeId(), startableAgentType);
        }
        try {
            hashMap = hashMap2;
            if (hashMap == null) {
                VirtualAgentDistributor.$$$reportNull$$$0(26);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VirtualAgentDistributor.a(illegalArgumentException);
        }
        return hashMap;
    }

    private static /* synthetic */ String lambda$getCompatibleCandidates$4(String string) {
        return string;
    }

    private static /* synthetic */ void lambda$getCompatibleCandidates$3(SBuildType sBuildType, Set set, Predicate predicate, List list) {
        try {
            if (!predicate.test(sBuildType)) {
                return;
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VirtualAgentDistributor.a(illegalArgumentException);
        }
        set.addAll(list);
    }

    static {
        boolean bl;
        try {
            bl = !VirtualAgentDistributor.class.desiredAssertionStatus();
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VirtualAgentDistributor.a(illegalArgumentException);
        }
        $assertionsDisabled = bl;
        NO_AGENTS = CompatibleAgentsResult.noAgents();
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

    private static Throwable a(Throwable throwable) {
        return throwable;
    }
}
