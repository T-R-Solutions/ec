/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jetbrains.buildServer.serverSide.BuildAgentEx
 *  jetbrains.buildServer.serverSide.SBuildAgent
 *  jetbrains.buildServer.serverSide.SBuildType
 *  jetbrains.buildServer.serverSide.agentTypes.AgentTypePolicy
 *  jetbrains.buildServer.serverSide.agentTypes.SAgentType
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.BuildTypePredicates$AgentPoolPredicate
 *  org.jetbrains.annotations.NotNull
 */
package jetbrains.buildServer.serverSide.impl.buildDistribution;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import jetbrains.buildServer.serverSide.BuildAgentEx;
import jetbrains.buildServer.serverSide.SBuildAgent;
import jetbrains.buildServer.serverSide.SBuildType;
import jetbrains.buildServer.serverSide.agentTypes.AgentTypePolicy;
import jetbrains.buildServer.serverSide.agentTypes.SAgentType;
import jetbrains.buildServer.serverSide.impl.buildDistribution.BuildTypePredicates;
import org.jetbrains.annotations.NotNull;

/*
 * Exception performing whole class analysis ignored.
 */
public class BuildTypePredicates {
    @NotNull
    public static Map<Predicate<SBuildType>, Map<SAgentType, List<SBuildAgent>>> createBuildTypePredicatesForAgents(@NotNull Collection<SBuildAgent> collection) {
        HashMap<Predicate<SBuildType>, Map<SAgentType, List<SBuildAgent>>> hashMap;
        try {
            if (collection == null) {
                BuildTypePredicates.$$$reportNull$$$0(0);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildTypePredicates.a(illegalArgumentException);
        }
        Map<Integer, List<SBuildAgent>> map = collection.stream().collect(Collectors.groupingBy(SBuildAgent::getAgentTypeId));
        HashMap<Predicate<SBuildType>, Map<SAgentType, List<SBuildAgent>>> hashMap2 = new HashMap<Predicate<SBuildType>, Map<SAgentType, List<SBuildAgent>>>();
        try {
            map.forEach((n, list) -> {
                block6: {
                    AgentTypePolicy agentTypePolicy;
                    SAgentType sAgentType;
                    block5: {
                        try {
                            if (list.isEmpty()) {
                                return;
                            }
                        }
                        catch (IllegalArgumentException illegalArgumentException) {
                            throw BuildTypePredicates.a(illegalArgumentException);
                        }
                        sAgentType = ((BuildAgentEx)list.get(0)).getAgentType();
                        agentTypePolicy = sAgentType.getPolicy();
                        try {
                            if (agentTypePolicy.getPolicyKind() != 1) break block5;
                            hashMap2.compute((Predicate<SBuildType>)new AgentPoolPredicate(sAgentType.getAgentPoolId(), agentTypePolicy), (predicate, hashMap) -> {
                                try {
                                    if (hashMap != null) {
                                        hashMap.computeIfAbsent(sAgentType, sAgentType -> new ArrayList()).addAll(list);
                                        return hashMap;
                                    }
                                }
                                catch (IllegalArgumentException illegalArgumentException) {
                                    throw BuildTypePredicates.a(illegalArgumentException);
                                }
                                hashMap = new HashMap<SAgentType, List>();
                                hashMap.put(sAgentType, list);
                                return hashMap;
                            });
                            break block6;
                        }
                        catch (IllegalArgumentException illegalArgumentException) {
                            throw BuildTypePredicates.a(illegalArgumentException);
                        }
                    }
                    HashMap<SAgentType, List> hashMap2 = new HashMap<SAgentType, List>();
                    hashMap2.put(sAgentType, (List)list);
                    hashMap2.put(sBuildType -> agentTypePolicy.isAllowedToRun(sBuildType.getProjectId(), sBuildType.getBuildTypeId()), hashMap2);
                }
            });
            hashMap = hashMap2;
            if (hashMap == null) {
                BuildTypePredicates.$$$reportNull$$$0(1);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildTypePredicates.a(illegalArgumentException);
        }
        return hashMap;
    }

    @NotNull
    public static Map<Predicate<SBuildType>, List<SAgentType>> createBuildTypePredicatesForAgentTypes(@NotNull Collection<SAgentType> collection) {
        HashMap<Predicate<SBuildType>, List<SAgentType>> hashMap;
        try {
            if (collection == null) {
                BuildTypePredicates.$$$reportNull$$$0(2);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildTypePredicates.a(illegalArgumentException);
        }
        HashMap<Predicate<SBuildType>, List<SAgentType>> hashMap2 = new HashMap<Predicate<SBuildType>, List<SAgentType>>();
        for (SAgentType sAgentType : collection) {
            AgentTypePolicy agentTypePolicy = sAgentType.getPolicy();
            try {
                if (agentTypePolicy.getPolicyKind() == 1) {
                    hashMap2.compute((Predicate<SBuildType>)new AgentPoolPredicate(sAgentType.getAgentPoolId(), agentTypePolicy), (predicate, list) -> {
                        try {
                            if (list != null) {
                                list.add(sAgentType);
                                return list;
                            }
                        }
                        catch (IllegalArgumentException illegalArgumentException) {
                            throw BuildTypePredicates.a(illegalArgumentException);
                        }
                        ArrayList<SAgentType> arrayList = new ArrayList<SAgentType>();
                        arrayList.add(sAgentType);
                        return arrayList;
                    });
                    continue;
                }
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw BuildTypePredicates.a(illegalArgumentException);
            }
            hashMap2.put(sBuildType -> agentTypePolicy.isAllowedToRun(sBuildType.getProjectId(), sBuildType.getBuildTypeId()), Collections.singletonList(sAgentType));
        }
        try {
            hashMap = hashMap2;
            if (hashMap == null) {
                BuildTypePredicates.$$$reportNull$$$0(3);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildTypePredicates.a(illegalArgumentException);
        }
        return hashMap;
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
