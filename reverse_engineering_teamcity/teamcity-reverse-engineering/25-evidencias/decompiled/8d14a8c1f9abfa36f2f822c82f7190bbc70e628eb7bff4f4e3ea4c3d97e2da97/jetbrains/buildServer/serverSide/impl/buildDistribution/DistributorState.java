/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jetbrains.buildServer.serverSide.buildDistribution.QueuedBuildInfo
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.BuildStarter
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.Distributor
 *  org.jetbrains.annotations.NotNull
 */
package jetbrains.buildServer.serverSide.impl.buildDistribution;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import jetbrains.buildServer.serverSide.buildDistribution.QueuedBuildInfo;
import jetbrains.buildServer.serverSide.impl.buildDistribution.BuildStarter;
import jetbrains.buildServer.serverSide.impl.buildDistribution.Distributor;
import org.jetbrains.annotations.NotNull;

/*
 * Exception performing whole class analysis ignored.
 */
public class DistributorState<T> {
    @NotNull
    private final Distributor<T> myDistributor;
    @NotNull
    private final BuildStarter<T> myBuildStarter;
    @NotNull
    private final Map<QueuedBuildInfo, T> myDistributedBuilds;
    @NotNull
    private final Set<T> myDistributedAgents;

    public DistributorState(@NotNull Distributor<T> distributor, @NotNull BuildStarter<T> buildStarter) {
        if (distributor == null) {
            DistributorState.$$$reportNull$$$0(0);
        }
        if (buildStarter == null) {
            DistributorState.$$$reportNull$$$0(1);
        }
        this.myDistributor = distributor;
        this.myBuildStarter = buildStarter;
        this.myDistributedBuilds = new HashMap<QueuedBuildInfo, T>();
        this.myDistributedAgents = new HashSet<T>();
    }

    @NotNull
    public Distributor<T> getDistributor() {
        Distributor<T> distributor;
        try {
            distributor = this.myDistributor;
            if (distributor == null) {
                DistributorState.$$$reportNull$$$0(2);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw DistributorState.a(illegalArgumentException);
        }
        return distributor;
    }

    @NotNull
    public BuildStarter<T> getBuildStarter() {
        BuildStarter<T> buildStarter;
        try {
            buildStarter = this.myBuildStarter;
            if (buildStarter == null) {
                DistributorState.$$$reportNull$$$0(3);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw DistributorState.a(illegalArgumentException);
        }
        return buildStarter;
    }

    @NotNull
    public Set<T> getDistributedAgents() {
        Set<T> set;
        try {
            set = Collections.unmodifiableSet(this.myDistributedAgents);
            if (set == null) {
                DistributorState.$$$reportNull$$$0(4);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw DistributorState.a(illegalArgumentException);
        }
        return set;
    }

    @NotNull
    public Map<QueuedBuildInfo, T> getDistributedBuilds() {
        Map<QueuedBuildInfo, T> map;
        try {
            map = Collections.unmodifiableMap(this.myDistributedBuilds);
            if (map == null) {
                DistributorState.$$$reportNull$$$0(5);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw DistributorState.a(illegalArgumentException);
        }
        return map;
    }

    public void addDistributedBuild(@NotNull QueuedBuildInfo queuedBuildInfo, T t) {
        try {
            if (queuedBuildInfo == null) {
                DistributorState.$$$reportNull$$$0(6);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw DistributorState.a(illegalArgumentException);
        }
        this.myDistributedBuilds.put(queuedBuildInfo, t);
        this.myDistributedAgents.add(t);
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
