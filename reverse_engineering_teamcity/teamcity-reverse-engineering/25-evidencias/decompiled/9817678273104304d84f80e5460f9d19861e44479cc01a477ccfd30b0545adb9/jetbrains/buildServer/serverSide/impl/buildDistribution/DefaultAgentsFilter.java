/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jetbrains.buildServer.ExtensionHolder
 *  jetbrains.buildServer.log.Loggers
 *  jetbrains.buildServer.serverSide.buildDistribution.AgentsFilterContext
 *  jetbrains.buildServer.serverSide.buildDistribution.AgentsFilterResult
 *  jetbrains.buildServer.serverSide.buildDistribution.SimpleWaitReason
 *  jetbrains.buildServer.serverSide.buildDistribution.StartingBuildAgentsFilter
 *  jetbrains.buildServer.serverSide.buildDistribution.WaitReason
 *  org.jetbrains.annotations.NotNull
 */
package jetbrains.buildServer.serverSide.impl.buildDistribution;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import jetbrains.buildServer.ExtensionHolder;
import jetbrains.buildServer.log.Loggers;
import jetbrains.buildServer.serverSide.buildDistribution.AgentsFilterContext;
import jetbrains.buildServer.serverSide.buildDistribution.AgentsFilterResult;
import jetbrains.buildServer.serverSide.buildDistribution.SimpleWaitReason;
import jetbrains.buildServer.serverSide.buildDistribution.StartingBuildAgentsFilter;
import jetbrains.buildServer.serverSide.buildDistribution.WaitReason;
import org.jetbrains.annotations.NotNull;

/*
 * Exception performing whole class analysis ignored.
 */
public class DefaultAgentsFilter
implements StartingBuildAgentsFilter {
    @NotNull
    private final Collection<StartingBuildAgentsFilter> myBuildAgentsFilters;

    public DefaultAgentsFilter(@NotNull ExtensionHolder extensionHolder) {
        if (extensionHolder == null) {
            DefaultAgentsFilter.$$$reportNull$$$0(0);
        }
        this.myBuildAgentsFilters = extensionHolder.getExtensions(StartingBuildAgentsFilter.class);
    }

    /*
     * Loose catch block
     */
    @NotNull
    public AgentsFilterResult filterAgents(@NotNull AgentsFilterContext agentsFilterContext) {
        AgentsFilterResult agentsFilterResult;
        try {
            if (agentsFilterContext == null) {
                DefaultAgentsFilter.$$$reportNull$$$0(1);
            }
        }
        catch (Exception exception) {
            throw DefaultAgentsFilter.a(exception);
        }
        ArrayList arrayList = new ArrayList(agentsFilterContext.getAgentsForStartingBuild());
        for (StartingBuildAgentsFilter startingBuildAgentsFilter : this.myBuildAgentsFilters) {
            List list;
            block29: {
                block30: {
                    block32: {
                        AgentsFilterResult agentsFilterResult2;
                        block25: {
                            block28: {
                                block27: {
                                    WaitReason waitReason;
                                    block26: {
                                        agentsFilterResult2 = startingBuildAgentsFilter.filterAgents((AgentsFilterContext)new /* Unavailable Anonymous Inner Class!! */);
                                        waitReason = agentsFilterResult2.getWaitReason();
                                        if (waitReason == null) break block25;
                                        try {
                                            block31: {
                                                if (agentsFilterContext.isEmulationMode()) break block26;
                                                break block31;
                                                catch (Exception exception) {
                                                    throw DefaultAgentsFilter.a(exception);
                                                }
                                            }
                                            Loggers.SERVER.debug("Extension blocked starting of the build " + String.valueOf(agentsFilterContext.getStartingBuild()) + ", reason: " + waitReason.getDescription());
                                        }
                                        catch (Exception exception) {
                                            throw DefaultAgentsFilter.a(exception);
                                        }
                                    }
                                    list = new AgentsFilterResult();
                                    try {
                                        list.setWaitReason(waitReason);
                                        if (agentsFilterResult2.getFilteredConnectedAgents() != null) break block27;
                                        list.setFilteredConnectedAgents(arrayList);
                                        break block28;
                                    }
                                    catch (Exception exception) {
                                        throw DefaultAgentsFilter.a(exception);
                                    }
                                }
                                list.setFilteredConnectedAgents(agentsFilterResult2.getFilteredConnectedAgents());
                            }
                            List list2 = list;
                            if (list2 == null) {
                                DefaultAgentsFilter.$$$reportNull$$$0(2);
                            }
                            return list2;
                        }
                        list = agentsFilterResult2.getFilteredConnectedAgents();
                        if (list == null) break block29;
                        if (!list.isEmpty()) break block29;
                        break block32;
                        catch (Exception exception) {
                            throw DefaultAgentsFilter.a(exception);
                        }
                    }
                    try {
                        block33: {
                            if (agentsFilterContext.isEmulationMode()) break block30;
                            break block33;
                            catch (Exception exception) {
                                throw DefaultAgentsFilter.a(exception);
                            }
                        }
                        Loggers.SERVER.debug("Extension blocked starting of the build " + String.valueOf(agentsFilterContext.getStartingBuild()) + " but did not provide a reason, extension class: " + startingBuildAgentsFilter.getClass().getName());
                    }
                    catch (Exception exception) {
                        throw DefaultAgentsFilter.a(exception);
                    }
                }
                AgentsFilterResult agentsFilterResult3 = new AgentsFilterResult();
                agentsFilterResult3.setWaitReason((WaitReason)new SimpleWaitReason("Build startup blocked for an unknown reason, see teamcity-server.log for details"));
                agentsFilterResult3.setFilteredConnectedAgents(Collections.emptyList());
                AgentsFilterResult agentsFilterResult4 = agentsFilterResult3;
                if (agentsFilterResult4 == null) {
                    DefaultAgentsFilter.$$$reportNull$$$0(3);
                }
                return agentsFilterResult4;
            }
            try {
                if (list == null) continue;
                arrayList.clear();
                arrayList.addAll(list);
            }
            catch (Exception exception) {
                Loggers.SERVER.warnAndDebugDetails("Exception in build agents filter extension: " + startingBuildAgentsFilter.getClass().getName(), (Throwable)exception);
            }
        }
        AgentsFilterResult agentsFilterResult5 = new AgentsFilterResult();
        try {
            agentsFilterResult5.setFilteredConnectedAgents(arrayList);
            agentsFilterResult = agentsFilterResult5;
            if (agentsFilterResult == null) {
                DefaultAgentsFilter.$$$reportNull$$$0(4);
            }
        }
        catch (Exception exception) {
            throw DefaultAgentsFilter.a(exception);
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

    private static Exception a(Exception exception) {
        return exception;
    }
}
