/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.intellij.openapi.util.Pair
 *  jetbrains.buildServer.log.Loggers
 *  jetbrains.buildServer.serverSide.SBuildAgent
 *  jetbrains.buildServer.serverSide.buildDistribution.AgentsFilterContext
 *  jetbrains.buildServer.serverSide.buildDistribution.AgentsFilterResult
 *  jetbrains.buildServer.serverSide.buildDistribution.GenericAgentsFilterContext$Requestor
 *  jetbrains.buildServer.serverSide.buildDistribution.StartingBuildAgentsFilter
 *  jetbrains.buildServer.util.StringUtil
 *  jetbrains.buildServer.util.positioning.PositionAware
 *  jetbrains.buildServer.util.positioning.PositionConstraint
 *  org.jetbrains.annotations.NotNull
 */
package jetbrains.buildServer.serverSide.impl.buildDistribution;

import com.intellij.openapi.util.Pair;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import jetbrains.buildServer.log.Loggers;
import jetbrains.buildServer.serverSide.SBuildAgent;
import jetbrains.buildServer.serverSide.buildDistribution.AgentsFilterContext;
import jetbrains.buildServer.serverSide.buildDistribution.AgentsFilterResult;
import jetbrains.buildServer.serverSide.buildDistribution.GenericAgentsFilterContext;
import jetbrains.buildServer.serverSide.buildDistribution.StartingBuildAgentsFilter;
import jetbrains.buildServer.util.StringUtil;
import jetbrains.buildServer.util.positioning.PositionAware;
import jetbrains.buildServer.util.positioning.PositionConstraint;
import org.jetbrains.annotations.NotNull;

/*
 * Exception performing whole class analysis ignored.
 */
public class PriorityAgentsFilter
implements StartingBuildAgentsFilter,
PositionAware {
    @NotNull
    public String getOrderId() {
        String string;
        try {
            string = PriorityAgentsFilter.class.getName();
            if (string == null) {
                PriorityAgentsFilter.$$$reportNull$$$0(0);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw PriorityAgentsFilter.a(illegalStateException);
        }
        return string;
    }

    @NotNull
    public PositionConstraint getConstraint() {
        PositionConstraint positionConstraint;
        try {
            positionConstraint = PositionConstraint.last();
            if (positionConstraint == null) {
                PriorityAgentsFilter.$$$reportNull$$$0(1);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw PriorityAgentsFilter.a(illegalStateException);
        }
        return positionConstraint;
    }

    @NotNull
    public AgentsFilterResult filterAgents(@NotNull AgentsFilterContext agentsFilterContext) {
        AgentsFilterResult agentsFilterResult;
        Object object2;
        ArrayList<Pair> arrayList;
        AgentsFilterResult agentsFilterResult2;
        block15: {
            AgentsFilterResult agentsFilterResult3;
            block16: {
                try {
                    if (agentsFilterContext == null) {
                        PriorityAgentsFilter.$$$reportNull$$$0(2);
                    }
                }
                catch (IllegalStateException illegalStateException) {
                    throw PriorityAgentsFilter.a(illegalStateException);
                }
                agentsFilterResult2 = new AgentsFilterResult();
                arrayList = new ArrayList<Pair>();
                boolean bl = true;
                if (agentsFilterContext.getRequestor() != GenericAgentsFilterContext.Requestor.OTHER) {
                    for (Object object2 : agentsFilterContext.getAgentsForStartingBuild()) {
                        int n;
                        block14: {
                            n = this.getAgentPriority((SBuildAgent)object2);
                            try {
                                if (!bl || n == 0) break block14;
                            }
                            catch (IllegalStateException illegalStateException) {
                                throw PriorityAgentsFilter.a(illegalStateException);
                            }
                            bl = false;
                        }
                        arrayList.add(new Pair(object2, (Object)n));
                    }
                }
                try {
                    try {
                        if (!bl) break block15;
                        agentsFilterResult2.setFilteredConnectedAgents(new ArrayList(agentsFilterContext.getAgentsForStartingBuild()));
                        agentsFilterResult3 = agentsFilterResult2;
                        if (agentsFilterResult3 != null) break block16;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw PriorityAgentsFilter.a(illegalStateException);
                    }
                    PriorityAgentsFilter.$$$reportNull$$$0(3);
                }
                catch (IllegalStateException illegalStateException) {
                    throw PriorityAgentsFilter.a(illegalStateException);
                }
            }
            return agentsFilterResult3;
        }
        Comparator<Pair> comparator = Comparator.comparingInt(Pair::getSecond);
        object2 = arrayList.stream().sorted(comparator.reversed()).map(Pair::getFirst).collect(Collectors.toList());
        try {
            agentsFilterResult2.setFilteredConnectedAgents((List)object2);
            agentsFilterResult = agentsFilterResult2;
            if (agentsFilterResult == null) {
                PriorityAgentsFilter.$$$reportNull$$$0(4);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw PriorityAgentsFilter.a(illegalStateException);
        }
        return agentsFilterResult;
    }

    private int getAgentPriority(@NotNull SBuildAgent sBuildAgent) {
        try {
            if (sBuildAgent == null) {
                PriorityAgentsFilter.$$$reportNull$$$0(5);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw PriorityAgentsFilter.a(illegalStateException);
        }
        String string = sBuildAgent.getAvailableParameterValue("teamcity.agent.priority");
        try {
            if (StringUtil.isEmpty((String)string)) {
                return 0;
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw PriorityAgentsFilter.a(illegalStateException);
        }
        int n = StringUtil.parseInt((String)string, (int)0);
        int n2 = Integer.min(Integer.max(n, -10000), 10000);
        try {
            if (n != n2) {
                Loggers.AGENT.warn(String.format("Agent priority value is out of bounds [%d..%d]. Value: %d. Resulting value: %d. Agent: %s (id: %s).", -10000, 10000, n, n2, sBuildAgent.getName(), sBuildAgent.getId()));
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw PriorityAgentsFilter.a(illegalStateException);
        }
        return n2;
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

    private static IllegalStateException a(IllegalStateException illegalStateException) {
        return illegalStateException;
    }
}
