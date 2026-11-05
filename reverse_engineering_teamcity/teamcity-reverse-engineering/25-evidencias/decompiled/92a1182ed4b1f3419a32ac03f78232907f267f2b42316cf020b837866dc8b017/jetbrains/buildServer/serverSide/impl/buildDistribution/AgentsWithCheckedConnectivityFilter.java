/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jetbrains.buildServer.serverSide.BuildAgentEx
 *  jetbrains.buildServer.serverSide.SBuildAgent
 *  jetbrains.buildServer.serverSide.buildDistribution.AgentsFilterContext
 *  jetbrains.buildServer.serverSide.buildDistribution.AgentsFilterResult
 *  jetbrains.buildServer.serverSide.buildDistribution.SimpleWaitReason
 *  jetbrains.buildServer.serverSide.buildDistribution.StartingBuildAgentsFilter
 *  jetbrains.buildServer.serverSide.buildDistribution.WaitReason
 *  org.jetbrains.annotations.NotNull
 */
package jetbrains.buildServer.serverSide.impl.buildDistribution;

import java.util.ArrayList;
import jetbrains.buildServer.serverSide.BuildAgentEx;
import jetbrains.buildServer.serverSide.SBuildAgent;
import jetbrains.buildServer.serverSide.buildDistribution.AgentsFilterContext;
import jetbrains.buildServer.serverSide.buildDistribution.AgentsFilterResult;
import jetbrains.buildServer.serverSide.buildDistribution.SimpleWaitReason;
import jetbrains.buildServer.serverSide.buildDistribution.StartingBuildAgentsFilter;
import jetbrains.buildServer.serverSide.buildDistribution.WaitReason;
import org.jetbrains.annotations.NotNull;

/*
 * Exception performing whole class analysis ignored.
 */
public class AgentsWithCheckedConnectivityFilter
implements StartingBuildAgentsFilter {
    @NotNull
    public AgentsFilterResult filterAgents(@NotNull AgentsFilterContext agentsFilterContext) {
        AgentsFilterResult agentsFilterResult;
        try {
            if (agentsFilterContext == null) {
                AgentsWithCheckedConnectivityFilter.$$$reportNull$$$0(0);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw AgentsWithCheckedConnectivityFilter.a(illegalArgumentException);
        }
        ArrayList<SBuildAgent> arrayList = new ArrayList<SBuildAgent>();
        for (SBuildAgent sBuildAgent : agentsFilterContext.getAgentsForStartingBuild()) {
            try {
                try {
                    if (!(sBuildAgent instanceof BuildAgentEx) || sBuildAgent.getLastSuccessfulPingTime() == null) continue;
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw AgentsWithCheckedConnectivityFilter.a(illegalArgumentException);
                }
                arrayList.add(sBuildAgent);
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw AgentsWithCheckedConnectivityFilter.a(illegalArgumentException);
            }
        }
        AgentsFilterResult agentsFilterResult2 = new AgentsFilterResult();
        try {
            agentsFilterResult2.setFilteredConnectedAgents(arrayList);
            if (arrayList.isEmpty()) {
                agentsFilterResult2.setWaitReason((WaitReason)new SimpleWaitReason("The agents connectivity with the server has not been verified yet"));
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw AgentsWithCheckedConnectivityFilter.a(illegalArgumentException);
        }
        try {
            agentsFilterResult = agentsFilterResult2;
            if (agentsFilterResult == null) {
                AgentsWithCheckedConnectivityFilter.$$$reportNull$$$0(1);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw AgentsWithCheckedConnectivityFilter.a(illegalArgumentException);
        }
        return agentsFilterResult;
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
