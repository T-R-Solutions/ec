/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jetbrains.buildServer.serverSide.SBuildAgent
 *  jetbrains.buildServer.serverSide.buildDistribution.BuildDistributorInput
 *  jetbrains.buildServer.serverSide.buildDistribution.QueuedBuildInfo
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.VirtualAgentsFilterContext
 *  jetbrains.buildServer.serverSide.impl.virtualAgent.VirtualAgent
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package jetbrains.buildServer.serverSide.impl.buildDistribution;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import jetbrains.buildServer.serverSide.SBuildAgent;
import jetbrains.buildServer.serverSide.buildDistribution.BuildDistributorInput;
import jetbrains.buildServer.serverSide.buildDistribution.QueuedBuildInfo;
import jetbrains.buildServer.serverSide.impl.buildDistribution.VirtualAgentsFilterContext;
import jetbrains.buildServer.serverSide.impl.virtualAgent.VirtualAgent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Exception performing whole class analysis ignored.
 */
public class VirtualAgentsFilterContextImpl
implements VirtualAgentsFilterContext {
    @NotNull
    private final Map<String, Object> myCustomData;
    @NotNull
    private final Map<QueuedBuildInfo, SBuildAgent> myDistributedBuilds;
    @NotNull
    private final BuildDistributorInput myDistributorInput;
    private final boolean myIsEmulationMode;
    @NotNull
    private final List<VirtualAgent> myAvailableAgents;
    private final QueuedBuildInfo myStartingBuild;

    public VirtualAgentsFilterContextImpl(@NotNull QueuedBuildInfo queuedBuildInfo, @NotNull Map<String, Object> map, @NotNull Map<QueuedBuildInfo, SBuildAgent> map2, @NotNull BuildDistributorInput buildDistributorInput, boolean bl, @NotNull List<VirtualAgent> list) {
        if (queuedBuildInfo == null) {
            VirtualAgentsFilterContextImpl.$$$reportNull$$$0(0);
        }
        if (map == null) {
            VirtualAgentsFilterContextImpl.$$$reportNull$$$0(1);
        }
        if (map2 == null) {
            VirtualAgentsFilterContextImpl.$$$reportNull$$$0(2);
        }
        if (buildDistributorInput == null) {
            VirtualAgentsFilterContextImpl.$$$reportNull$$$0(3);
        }
        if (list == null) {
            VirtualAgentsFilterContextImpl.$$$reportNull$$$0(4);
        }
        this.myStartingBuild = queuedBuildInfo;
        this.myCustomData = map;
        this.myDistributedBuilds = map2;
        this.myDistributorInput = buildDistributorInput;
        this.myIsEmulationMode = bl;
        this.myAvailableAgents = list;
    }

    @NotNull
    public QueuedBuildInfo getStartingBuild() {
        QueuedBuildInfo queuedBuildInfo;
        try {
            queuedBuildInfo = this.myStartingBuild;
            if (queuedBuildInfo == null) {
                VirtualAgentsFilterContextImpl.$$$reportNull$$$0(5);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VirtualAgentsFilterContextImpl.a(illegalArgumentException);
        }
        return queuedBuildInfo;
    }

    @NotNull
    public Collection<VirtualAgent> getAgentsForStartingBuild() {
        List<VirtualAgent> list;
        try {
            list = Collections.unmodifiableList(this.myAvailableAgents);
            if (list == null) {
                VirtualAgentsFilterContextImpl.$$$reportNull$$$0(6);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VirtualAgentsFilterContextImpl.a(illegalArgumentException);
        }
        return list;
    }

    @NotNull
    public Map<QueuedBuildInfo, SBuildAgent> getDistributedBuilds() {
        Map<QueuedBuildInfo, SBuildAgent> map;
        try {
            map = Collections.unmodifiableMap(this.myDistributedBuilds);
            if (map == null) {
                VirtualAgentsFilterContextImpl.$$$reportNull$$$0(7);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VirtualAgentsFilterContextImpl.a(illegalArgumentException);
        }
        return map;
    }

    @NotNull
    public BuildDistributorInput getDistributorInput() {
        BuildDistributorInput buildDistributorInput;
        try {
            buildDistributorInput = this.myDistributorInput;
            if (buildDistributorInput == null) {
                VirtualAgentsFilterContextImpl.$$$reportNull$$$0(8);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VirtualAgentsFilterContextImpl.a(illegalArgumentException);
        }
        return buildDistributorInput;
    }

    public boolean isEmulationMode() {
        return this.myIsEmulationMode;
    }

    @Nullable
    public Object getCustomData(@NotNull String string) {
        try {
            if (string == null) {
                VirtualAgentsFilterContextImpl.$$$reportNull$$$0(9);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VirtualAgentsFilterContextImpl.a(illegalArgumentException);
        }
        return this.myCustomData.get(string);
    }

    public void setCustomData(@NotNull String string, @Nullable Object object) {
        try {
            if (string == null) {
                VirtualAgentsFilterContextImpl.$$$reportNull$$$0(10);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VirtualAgentsFilterContextImpl.a(illegalArgumentException);
        }
        this.myCustomData.put(string, object);
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
