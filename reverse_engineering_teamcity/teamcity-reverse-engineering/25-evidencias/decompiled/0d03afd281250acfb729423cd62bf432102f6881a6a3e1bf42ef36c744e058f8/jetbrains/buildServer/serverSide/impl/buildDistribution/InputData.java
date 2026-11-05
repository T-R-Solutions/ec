/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jetbrains.buildServer.serverSide.BuildPromotion
 *  jetbrains.buildServer.serverSide.SBuildAgent
 *  jetbrains.buildServer.serverSide.SBuildType
 *  jetbrains.buildServer.serverSide.buildDistribution.QueuedBuildInfo
 *  jetbrains.buildServer.serverSide.buildDistribution.RunningBuildInfo
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.BuildDistributorInputEx
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package jetbrains.buildServer.serverSide.impl.buildDistribution;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import jetbrains.buildServer.serverSide.BuildPromotion;
import jetbrains.buildServer.serverSide.SBuildAgent;
import jetbrains.buildServer.serverSide.SBuildType;
import jetbrains.buildServer.serverSide.buildDistribution.QueuedBuildInfo;
import jetbrains.buildServer.serverSide.buildDistribution.RunningBuildInfo;
import jetbrains.buildServer.serverSide.impl.buildDistribution.BuildDistributorInputEx;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Exception performing whole class analysis ignored.
 */
public class InputData
implements BuildDistributorInputEx {
    @NotNull
    private final List<QueuedBuildInfo> myQueue;
    @NotNull
    private final Collection<RunningBuildInfo> myRunningBuilds;
    @NotNull
    private final Map<Long, RunningBuildInfo> myRunningBuildsMap;
    @NotNull
    private final Map<String, List<RunningBuildInfo>> myBuildTypeRunningBuilds;
    @NotNull
    private final Collection<SBuildAgent> myAgents;
    @NotNull
    private final Map<String, Object> myCustomData;
    @NotNull
    private final Map<QueuedBuildInfo, SBuildAgent> myDistributedBuilds;
    private int myAverageDistributionTime;

    public InputData(@NotNull List<QueuedBuildInfo> list, @NotNull Collection<RunningBuildInfo> collection, @NotNull Collection<SBuildAgent> collection2) {
        if (list == null) {
            InputData.$$$reportNull$$$0(0);
        }
        if (collection == null) {
            InputData.$$$reportNull$$$0(1);
        }
        if (collection2 == null) {
            InputData.$$$reportNull$$$0(2);
        }
        this.myCustomData = new HashMap<String, Object>();
        this.myAverageDistributionTime = 0;
        this.myQueue = list;
        this.myRunningBuilds = collection;
        this.myRunningBuildsMap = new HashMap<Long, RunningBuildInfo>();
        this.myBuildTypeRunningBuilds = new HashMap<String, List<RunningBuildInfo>>();
        this.myRunningBuilds.forEach(runningBuildInfo -> {
            BuildPromotion buildPromotion = (BuildPromotion)runningBuildInfo.getBuildPromotionInfo();
            this.myRunningBuildsMap.put(buildPromotion.getId(), (RunningBuildInfo)runningBuildInfo);
            this.myBuildTypeRunningBuilds.computeIfAbsent(buildPromotion.getParentBuildTypeId(), string -> new ArrayList()).add(runningBuildInfo);
        });
        this.myAgents = collection2;
        this.myDistributedBuilds = new LinkedHashMap<QueuedBuildInfo, SBuildAgent>();
    }

    @NotNull
    public List<QueuedBuildInfo> getQueue() {
        List<QueuedBuildInfo> list;
        try {
            list = this.myQueue;
            if (list == null) {
                InputData.$$$reportNull$$$0(3);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw InputData.a(illegalArgumentException);
        }
        return list;
    }

    @NotNull
    public Collection<RunningBuildInfo> getRunningBuilds() {
        Collection<RunningBuildInfo> collection;
        try {
            collection = this.myRunningBuilds;
            if (collection == null) {
                InputData.$$$reportNull$$$0(4);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw InputData.a(illegalArgumentException);
        }
        return collection;
    }

    @Nullable
    public RunningBuildInfo findRunningBuildById(long l) {
        return this.myRunningBuildsMap.get(l);
    }

    @NotNull
    public List<RunningBuildInfo> getBuildTypeRunningBuilds(@NotNull SBuildType sBuildType) {
        List<RunningBuildInfo> list;
        try {
            if (sBuildType == null) {
                InputData.$$$reportNull$$$0(5);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw InputData.a(illegalArgumentException);
        }
        try {
            list = this.myBuildTypeRunningBuilds.getOrDefault(sBuildType.getBuildTypeId(), Collections.emptyList());
            if (list == null) {
                InputData.$$$reportNull$$$0(6);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw InputData.a(illegalArgumentException);
        }
        return list;
    }

    @NotNull
    public Collection<SBuildAgent> getAgents() {
        Collection<SBuildAgent> collection;
        try {
            collection = this.myAgents;
            if (collection == null) {
                InputData.$$$reportNull$$$0(7);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw InputData.a(illegalArgumentException);
        }
        return collection;
    }

    @NotNull
    public Set<SBuildAgent> getBusyAgents() {
        HashSet<SBuildAgent> hashSet;
        HashSet<SBuildAgent> hashSet2 = new HashSet<SBuildAgent>();
        for (RunningBuildInfo runningBuildInfo : this.getRunningBuilds()) {
            try {
                if (!runningBuildInfo.occupiesAgent()) {
                    continue;
                }
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw InputData.a(illegalArgumentException);
            }
            hashSet2.add(runningBuildInfo.getAgent());
        }
        try {
            this.myDistributedBuilds.values().forEach(sBuildAgent -> {
                try {
                    if (sBuildAgent != null) {
                        hashSet2.add((SBuildAgent)sBuildAgent);
                    }
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw InputData.a(illegalArgumentException);
                }
            });
            hashSet = hashSet2;
            if (hashSet == null) {
                InputData.$$$reportNull$$$0(8);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw InputData.a(illegalArgumentException);
        }
        return hashSet;
    }

    public void setCustomData(@NotNull String string, @Nullable Object object) {
        block6: {
            block5: {
                try {
                    if (string == null) {
                        InputData.$$$reportNull$$$0(9);
                    }
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw InputData.a(illegalArgumentException);
                }
                try {
                    if (object != null) break block5;
                    this.myCustomData.remove(string);
                    break block6;
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw InputData.a(illegalArgumentException);
                }
            }
            this.myCustomData.put(string, object);
        }
    }

    @Nullable
    public <T> T getCustomData(@NotNull String string, @NotNull Class<T> clazz) {
        try {
            if (string == null) {
                InputData.$$$reportNull$$$0(10);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw InputData.a(illegalArgumentException);
        }
        try {
            if (clazz == null) {
                InputData.$$$reportNull$$$0(11);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw InputData.a(illegalArgumentException);
        }
        return (T)this.myCustomData.get(string);
    }

    @NotNull
    public <T> T getCustomData(@NotNull String string2, @NotNull Supplier<T> supplier) {
        Object object;
        try {
            if (string2 == null) {
                InputData.$$$reportNull$$$0(12);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw InputData.a(illegalArgumentException);
        }
        try {
            if (supplier == null) {
                InputData.$$$reportNull$$$0(13);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw InputData.a(illegalArgumentException);
        }
        try {
            object = this.myCustomData.computeIfAbsent(string2, string -> supplier.get());
            if (object == null) {
                InputData.$$$reportNull$$$0(14);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw InputData.a(illegalArgumentException);
        }
        return (T)object;
    }

    @NotNull
    public Map<QueuedBuildInfo, SBuildAgent> getDistributedBuilds() {
        Map<QueuedBuildInfo, SBuildAgent> map;
        try {
            map = Collections.unmodifiableMap(this.myDistributedBuilds);
            if (map == null) {
                InputData.$$$reportNull$$$0(15);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw InputData.a(illegalArgumentException);
        }
        return map;
    }

    public void setAverageDistributionTime(int n) {
        this.myAverageDistributionTime = n;
    }

    public int getAverageDistributionTime() {
        return this.myAverageDistributionTime;
    }

    public void setDistributedBuilds(@NotNull Map<QueuedBuildInfo, SBuildAgent> map) {
        try {
            if (map == null) {
                InputData.$$$reportNull$$$0(16);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw InputData.a(illegalArgumentException);
        }
        this.myDistributedBuilds.clear();
        this.myDistributedBuilds.putAll(map);
    }

    public String toString() {
        return "InputData{myQueue=" + this.myQueue.size() + ", myRunningBuilds=" + this.myRunningBuilds.size() + ", myAgents=" + this.myAgents.size() + "}";
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
