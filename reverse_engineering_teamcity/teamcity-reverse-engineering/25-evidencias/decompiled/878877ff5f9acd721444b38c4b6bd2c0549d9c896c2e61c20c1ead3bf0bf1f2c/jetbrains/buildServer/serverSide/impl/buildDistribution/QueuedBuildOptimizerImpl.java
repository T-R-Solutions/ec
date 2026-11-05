/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.intellij.openapi.diagnostic.Logger
 *  com.intellij.openapi.util.Pair
 *  jetbrains.buildServer.metrics.Counter
 *  jetbrains.buildServer.metrics.Stoppable
 *  jetbrains.buildServer.serverSide.BuildPromotion
 *  jetbrains.buildServer.serverSide.BuildPromotionEx
 *  jetbrains.buildServer.serverSide.BuildQueueEx
 *  jetbrains.buildServer.serverSide.BuildRevisionEx
 *  jetbrains.buildServer.serverSide.BuildServerListener
 *  jetbrains.buildServer.serverSide.BuildTypeEx
 *  jetbrains.buildServer.serverSide.BuildTypeIds
 *  jetbrains.buildServer.serverSide.DependencyConsumer
 *  jetbrains.buildServer.serverSide.DependencyConsumer$Result
 *  jetbrains.buildServer.serverSide.QueuedBuildEx
 *  jetbrains.buildServer.serverSide.SBuild
 *  jetbrains.buildServer.serverSide.SQueuedBuild
 *  jetbrains.buildServer.serverSide.SRunningBuild
 *  jetbrains.buildServer.serverSide.TeamCityProperties
 *  jetbrains.buildServer.serverSide.buildDistribution.QueuedBuildInfo
 *  jetbrains.buildServer.serverSide.buildDistribution.StartBuildPrecondition
 *  jetbrains.buildServer.serverSide.buildDistribution.WaitReason
 *  jetbrains.buildServer.serverSide.impl.DependenciesUtil
 *  jetbrains.buildServer.serverSide.impl.LogUtil
 *  jetbrains.buildServer.serverSide.impl.QueuedTasksProcessor
 *  jetbrains.buildServer.serverSide.impl.QueuedTasksProcessor$ManyTasksProcessor
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.BuildDistributor$QueuedBuildOptimizer
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.BuildDistributorInputEx
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.CheckingForChangesPrecondition
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.VersionedSettingsPrecondition
 *  jetbrains.buildServer.util.EventDispatcher
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package jetbrains.buildServer.serverSide.impl.buildDistribution;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.util.Pair;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EventListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import jetbrains.buildServer.metrics.Counter;
import jetbrains.buildServer.metrics.Stoppable;
import jetbrains.buildServer.serverSide.BuildPromotion;
import jetbrains.buildServer.serverSide.BuildPromotionEx;
import jetbrains.buildServer.serverSide.BuildQueueEx;
import jetbrains.buildServer.serverSide.BuildRevisionEx;
import jetbrains.buildServer.serverSide.BuildServerListener;
import jetbrains.buildServer.serverSide.BuildTypeEx;
import jetbrains.buildServer.serverSide.BuildTypeIds;
import jetbrains.buildServer.serverSide.DependencyConsumer;
import jetbrains.buildServer.serverSide.QueuedBuildEx;
import jetbrains.buildServer.serverSide.SBuild;
import jetbrains.buildServer.serverSide.SQueuedBuild;
import jetbrains.buildServer.serverSide.SRunningBuild;
import jetbrains.buildServer.serverSide.TeamCityProperties;
import jetbrains.buildServer.serverSide.buildDistribution.QueuedBuildInfo;
import jetbrains.buildServer.serverSide.buildDistribution.StartBuildPrecondition;
import jetbrains.buildServer.serverSide.buildDistribution.WaitReason;
import jetbrains.buildServer.serverSide.impl.DependenciesUtil;
import jetbrains.buildServer.serverSide.impl.LogUtil;
import jetbrains.buildServer.serverSide.impl.QueuedTasksProcessor;
import jetbrains.buildServer.serverSide.impl.buildDistribution.BuildDistributor;
import jetbrains.buildServer.serverSide.impl.buildDistribution.BuildDistributorInputEx;
import jetbrains.buildServer.serverSide.impl.buildDistribution.CheckingForChangesPrecondition;
import jetbrains.buildServer.serverSide.impl.buildDistribution.VersionedSettingsPrecondition;
import jetbrains.buildServer.util.EventDispatcher;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Exception performing whole class analysis ignored.
 */
public class QueuedBuildOptimizerImpl
implements BuildDistributor.QueuedBuildOptimizer {
    public static final String MODIFIED_PROMOTIONS_ASYNC_PERSIST = "teamcity.buildQueue.optimization.modifiedPromotions.asyncPersist";
    public static final String ALREADY_OPTIMIZED_BUILDS_CLEANUP_MARKER = "buildQueueOptimizer.alreadyOptimizedTopBuildsCleanupExecuted";
    public static final String ALREADY_CHECKED_POSITIONS_TOP_PROMOTIONS = "buildQueueOptimizer.alreadyCheckedPositions";
    public static final String BUILD_QUEUE_OPTIMIZE_CHAINS_ONCE_PROP = "teamcity.buildQueue.optimization.optimizeChainsOnce";
    @NotNull
    private final BuildQueueEx myQueue;
    @NotNull
    private final Counter myOptimizationMetric;
    private static final Logger LOG = Logger.getInstance(QueuedBuildOptimizerImpl.class);
    private final QueuedTasksProcessor<BuildPromotionEx> myModifiedBuildChainsPersister;
    private final Map<BuildPromotionEx, Pair<BuildTypeIds, Set<Long>>> myAlreadyOptimizedBuildChains;
    private final Set<Long> myAlreadyOptimizedBuilds;
    public static final BuildDistributor.QueuedBuildOptimizer NO_OP = new /* Unavailable Anonymous Inner Class!! */;

    public QueuedBuildOptimizerImpl(@NotNull BuildQueueEx buildQueueEx, @NotNull ExecutorService executorService, @NotNull EventDispatcher<BuildServerListener> eventDispatcher, @NotNull Counter counter) {
        if (buildQueueEx == null) {
            QueuedBuildOptimizerImpl.$$$reportNull$$$0(0);
        }
        if (executorService == null) {
            QueuedBuildOptimizerImpl.$$$reportNull$$$0(1);
        }
        if (eventDispatcher == null) {
            QueuedBuildOptimizerImpl.$$$reportNull$$$0(2);
        }
        if (counter == null) {
            QueuedBuildOptimizerImpl.$$$reportNull$$$0(3);
        }
        this.myAlreadyOptimizedBuildChains = new ConcurrentHashMap<BuildPromotionEx, Pair<BuildTypeIds, Set<Long>>>();
        this.myAlreadyOptimizedBuilds = ConcurrentHashMap.newKeySet();
        this.myQueue = buildQueueEx;
        this.myOptimizationMetric = counter;
        int n = TeamCityProperties.getInteger((String)"teamcity.buildQueue.optimization.modifiedPromotions.asyncPersist.queueSize", (int)2000);
        this.myModifiedBuildChainsPersister = new QueuedTasksProcessor((QueuedTasksProcessor.ManyTasksProcessor)new /* Unavailable Anonymous Inner Class!! */, executorService, n, n, "Persisting modified queued build promotions");
        eventDispatcher.addListener((EventListener)new /* Unavailable Anonymous Inner Class!! */);
    }

    private static void persistQueuedDependencies(@NotNull Collection<BuildPromotionEx> collection) {
        try {
            if (collection == null) {
                QueuedBuildOptimizerImpl.$$$reportNull$$$0(4);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw QueuedBuildOptimizerImpl.a(illegalArgumentException);
        }
        ArrayList arrayList = new ArrayList();
        Predicate<BuildPromotion> predicate = buildPromotion -> {
            boolean bl;
            block11: {
                block10: {
                    BuildPromotionEx buildPromotionEx = (BuildPromotionEx)buildPromotion;
                    try {
                        if (buildPromotionEx.getQueuedBuild() != null) {
                            return true;
                        }
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        throw QueuedBuildOptimizerImpl.a(illegalArgumentException);
                    }
                    try {
                        if (!buildPromotionEx.isCompositeBuild()) {
                            return false;
                        }
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        throw QueuedBuildOptimizerImpl.a(illegalArgumentException);
                    }
                    SBuild sBuild = buildPromotionEx.getAssociatedBuild();
                    try {
                        try {
                            if (sBuild == null || sBuild.isFinished()) break block10;
                        }
                        catch (IllegalArgumentException illegalArgumentException) {
                            throw QueuedBuildOptimizerImpl.a(illegalArgumentException);
                        }
                        bl = true;
                        break block11;
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        throw QueuedBuildOptimizerImpl.a(illegalArgumentException);
                    }
                }
                bl = false;
            }
            return bl;
        };
        DependenciesUtil.consumeAll(new HashSet<BuildPromotionEx>(collection), buildPromotion -> {
            try {
                if (predicate.test((BuildPromotion)buildPromotion)) {
                    arrayList.add((BuildPromotionEx)buildPromotion);
                }
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw QueuedBuildOptimizerImpl.a(illegalArgumentException);
            }
        });
        List list = DependenciesUtil.findAllTopPromotions(arrayList);
        DependenciesUtil.consumeAll((Collection)list, buildPromotion -> {
            try {
                if (predicate.test((BuildPromotion)buildPromotion)) {
                    ((BuildPromotionEx)buildPromotion).persist();
                }
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw QueuedBuildOptimizerImpl.a(illegalArgumentException);
            }
        });
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     */
    public boolean performOptimization(@NotNull QueuedBuildInfo queuedBuildInfo, @NotNull BuildDistributorInputEx buildDistributorInputEx) {
        Stoppable stoppable;
        block46: {
            List list;
            Pair<List<BuildPromotionEx>, Set<Long>> pair;
            block44: {
                QueuedBuildEx queuedBuildEx;
                block47: {
                    block45: {
                        block43: {
                            BuildPromotionEx buildPromotionEx2;
                            block42: {
                                block41: {
                                    try {
                                        if (queuedBuildInfo == null) {
                                            QueuedBuildOptimizerImpl.$$$reportNull$$$0(5);
                                        }
                                    }
                                    catch (Throwable throwable) {
                                        throw QueuedBuildOptimizerImpl.a(throwable);
                                    }
                                    try {
                                        if (buildDistributorInputEx == null) {
                                            QueuedBuildOptimizerImpl.$$$reportNull$$$0(6);
                                        }
                                    }
                                    catch (Throwable throwable) {
                                        throw QueuedBuildOptimizerImpl.a(throwable);
                                    }
                                    stoppable = this.myOptimizationMetric.startMsecsTimer();
                                    queuedBuildEx = (QueuedBuildEx)queuedBuildInfo;
                                    buildPromotionEx2 = queuedBuildEx.getBuildPromotion();
                                    BuildTypeEx buildTypeEx = buildPromotionEx2.getParentBuildType();
                                    if (buildTypeEx != null) break block41;
                                    boolean bl2 = false;
                                    try {
                                        if (stoppable != null) {
                                            stoppable.close();
                                        }
                                    }
                                    catch (Throwable throwable) {
                                        throw QueuedBuildOptimizerImpl.a(throwable);
                                    }
                                    return bl2;
                                }
                                this.cleanupAlreadyOptimizedBuilds(buildDistributorInputEx);
                                if (!this.myAlreadyOptimizedBuilds.contains(buildPromotionEx2.getId())) break block42;
                                boolean bl3 = false;
                                try {
                                    if (stoppable != null) {
                                        stoppable.close();
                                    }
                                }
                                catch (Throwable throwable) {
                                    throw QueuedBuildOptimizerImpl.a(throwable);
                                }
                                return bl3;
                            }
                            pair = this.getTopPromotionsAndPathToThem(buildPromotionEx2);
                            this.cleanupPossiblyAffectedOptimizedBuildChains((Collection)pair.first, buildDistributorInputEx);
                            list = ((List)pair.first).stream().filter(buildPromotionEx -> {
                                boolean bl;
                                try {
                                    bl = !this.myAlreadyOptimizedBuildChains.containsKey(buildPromotionEx);
                                }
                                catch (IllegalArgumentException illegalArgumentException) {
                                    throw QueuedBuildOptimizerImpl.a(illegalArgumentException);
                                }
                                return bl;
                            }).collect(Collectors.toList());
                            if (!list.isEmpty()) break block43;
                            boolean bl4 = false;
                            try {
                                if (stoppable != null) {
                                    stoppable.close();
                                }
                            }
                            catch (Throwable throwable) {
                                throw QueuedBuildOptimizerImpl.a(throwable);
                            }
                            return bl4;
                        }
                        try {
                            if (!this.myQueue.optimizeChains(list)) break block44;
                            if (!QueuedBuildOptimizerImpl.isAsyncPersistingEnabled()) break block45;
                        }
                        catch (Throwable throwable) {
                            throw QueuedBuildOptimizerImpl.a(throwable);
                        }
                        for (Object object : (List)pair.first) {
                            this.myModifiedBuildChainsPersister.addToQueue(object);
                        }
                        break block47;
                    }
                    QueuedBuildOptimizerImpl.persistQueuedDependencies((Collection)pair.first);
                }
                boolean bl5 = queuedBuildEx.getBuildPromotion().isDeleted();
                this.myAlreadyOptimizedBuilds.addAll((Collection)pair.second);
                for (BuildPromotionEx buildPromotionEx3 : list) {
                    Map<String, BuildPromotionEx> map = QueuedBuildOptimizerImpl.getBuildChainPromotionsMap(buildPromotionEx3);
                    this.myAlreadyOptimizedBuildChains.put(buildPromotionEx3, (Pair<BuildTypeIds, Set<Long>>)Pair.create((Object)BuildTypeIds.create(map.keySet()), (Object)((Set)pair.second)));
                }
                try {
                    if (stoppable != null) {
                        stoppable.close();
                    }
                }
                catch (Throwable throwable) {
                    throw QueuedBuildOptimizerImpl.a(throwable);
                }
                return bl5;
            }
            this.myAlreadyOptimizedBuilds.addAll((Collection)pair.second);
            for (BuildPromotionEx buildPromotionEx4 : list) {
                Map<String, BuildPromotionEx> map = QueuedBuildOptimizerImpl.getBuildChainPromotionsMap(buildPromotionEx4);
                this.myAlreadyOptimizedBuildChains.put(buildPromotionEx4, (Pair<BuildTypeIds, Set<Long>>)Pair.create((Object)BuildTypeIds.create(map.keySet()), (Object)((Set)pair.second)));
            }
            {
                break block46;
                catch (Throwable throwable) {
                    try {
                        LOG.warnAndDebugDetails("Failed to perform optimization for the top build promotions: " + LogUtil.describe(list), throwable);
                    }
                    catch (Throwable throwable2) {
                        throw throwable2;
                    }
                    finally {
                        this.myAlreadyOptimizedBuilds.addAll((Collection)pair.second);
                        for (BuildPromotionEx buildPromotionEx5 : list) {
                            Map<String, BuildPromotionEx> map = QueuedBuildOptimizerImpl.getBuildChainPromotionsMap(buildPromotionEx5);
                            this.myAlreadyOptimizedBuildChains.put(buildPromotionEx5, (Pair<BuildTypeIds, Set<Long>>)Pair.create((Object)BuildTypeIds.create(map.keySet()), (Object)((Set)pair.second)));
                        }
                    }
                }
            }
        }
        boolean bl = false;
        try {
            if (stoppable != null) {
                stoppable.close();
            }
        }
        catch (Throwable throwable) {
            throw QueuedBuildOptimizerImpl.a(throwable);
        }
        return bl;
        catch (Throwable throwable) {
            if (stoppable != null) {
                try {
                    stoppable.close();
                }
                catch (Throwable throwable3) {
                    throwable.addSuppressed(throwable3);
                }
            }
            throw throwable;
        }
    }

    public boolean canRunOptimization(@NotNull QueuedBuildInfo queuedBuildInfo, @Nullable WaitReason waitReason, @NotNull Set<Class<? extends StartBuildPrecondition>> set) {
        block18: {
            block17: {
                try {
                    if (queuedBuildInfo == null) {
                        QueuedBuildOptimizerImpl.$$$reportNull$$$0(7);
                    }
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw QueuedBuildOptimizerImpl.a(illegalArgumentException);
                }
                try {
                    if (set == null) {
                        QueuedBuildOptimizerImpl.$$$reportNull$$$0(8);
                    }
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw QueuedBuildOptimizerImpl.a(illegalArgumentException);
                }
                try {
                    try {
                        if (waitReason == null || !TeamCityProperties.getBoolean((String)"teamcity.buildQueue.optimization.skipIfWaitReasonIsSet")) break block17;
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        throw QueuedBuildOptimizerImpl.a(illegalArgumentException);
                    }
                    return false;
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw QueuedBuildOptimizerImpl.a(illegalArgumentException);
                }
            }
            try {
                try {
                    if (!CheckingForChangesPrecondition.ALL_WAIT_REASONS.contains(waitReason) && !VersionedSettingsPrecondition.ALL_WAIT_REASONS.contains(waitReason)) break block18;
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw QueuedBuildOptimizerImpl.a(illegalArgumentException);
                }
                return false;
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw QueuedBuildOptimizerImpl.a(illegalArgumentException);
            }
        }
        for (Class clazz : Arrays.asList(CheckingForChangesPrecondition.class, VersionedSettingsPrecondition.class)) {
            try {
                if (set.contains(clazz)) continue;
                return false;
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw QueuedBuildOptimizerImpl.a(illegalArgumentException);
            }
        }
        return true;
    }

    @NotNull
    private Pair<List<BuildPromotionEx>, Set<Long>> getTopPromotionsAndPathToThem(@NotNull BuildPromotionEx buildPromotionEx) {
        Pair pair;
        try {
            if (buildPromotionEx == null) {
                QueuedBuildOptimizerImpl.$$$reportNull$$$0(9);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw QueuedBuildOptimizerImpl.a(illegalArgumentException);
        }
        ArrayList arrayList = new ArrayList();
        HashSet hashSet = new HashSet();
        try {
            buildPromotionEx.traverseSelfAndDependedOnMe((DependencyConsumer)new /* Unavailable Anonymous Inner Class!! */);
            pair = Pair.create(arrayList, hashSet);
            if (pair == null) {
                QueuedBuildOptimizerImpl.$$$reportNull$$$0(10);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw QueuedBuildOptimizerImpl.a(illegalArgumentException);
        }
        return pair;
    }

    private void cleanupPossiblyAffectedOptimizedBuildChains(@NotNull Collection<BuildPromotionEx> collection, @NotNull BuildDistributorInputEx buildDistributorInputEx) {
        block35: {
            try {
                if (collection == null) {
                    QueuedBuildOptimizerImpl.$$$reportNull$$$0(11);
                }
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw QueuedBuildOptimizerImpl.a(illegalArgumentException);
            }
            try {
                if (buildDistributorInputEx == null) {
                    QueuedBuildOptimizerImpl.$$$reportNull$$$0(12);
                }
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw QueuedBuildOptimizerImpl.a(illegalArgumentException);
            }
            try {
                try {
                    if (!this.myAlreadyOptimizedBuildChains.isEmpty() && TeamCityProperties.getBooleanOrTrue((String)"teamcity.buildQueue.optimization.optimizeChainsOnce")) break block35;
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw QueuedBuildOptimizerImpl.a(illegalArgumentException);
                }
                return;
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw QueuedBuildOptimizerImpl.a(illegalArgumentException);
            }
        }
        Set set = (Set)buildDistributorInputEx.getCustomData("buildQueueOptimizer.alreadyCheckedPositions", () -> new HashSet());
        HashSet hashSet = new HashSet();
        HashSet<BuildPromotionEx> hashSet2 = new HashSet<BuildPromotionEx>();
        for (BuildPromotionEx buildPromotionEx2 : collection) {
            BuildTypeIds buildTypeIds;
            try {
                if (!set.add(buildPromotionEx2.getId())) {
                    continue;
                }
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw QueuedBuildOptimizerImpl.a(illegalArgumentException);
            }
            try {
                if (this.myAlreadyOptimizedBuildChains.containsKey(buildPromotionEx2)) {
                    continue;
                }
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw QueuedBuildOptimizerImpl.a(illegalArgumentException);
            }
            Map<String, BuildPromotionEx> map = QueuedBuildOptimizerImpl.getBuildChainPromotionsMap(buildPromotionEx2);
            BuildTypeIds buildTypeIds2 = BuildTypeIds.create(map.keySet());
            HashSet hashSet3 = new HashSet();
            for (Map.Entry<BuildPromotionEx, Pair<BuildTypeIds, Set<Long>>> entry : this.myAlreadyOptimizedBuildChains.entrySet()) {
                buildTypeIds = (BuildTypeIds)entry.getValue().first;
                buildTypeIds.processIds(buildTypeIds2, string -> {
                    hashSet3.add(string);
                    return true;
                });
            }
            for (String string2 : hashSet3) {
                buildTypeIds = map.get(string2);
                if (buildTypeIds == null) continue;
                try {
                    if (buildTypeIds.isPersonal()) {
                        continue;
                    }
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw QueuedBuildOptimizerImpl.a(illegalArgumentException);
                }
                List list = this.myQueue.getItems(string2);
                try {
                    if (list.size() < 2) {
                        continue;
                    }
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw QueuedBuildOptimizerImpl.a(illegalArgumentException);
                }
                ArrayList<BuildPromotionEx> arrayList = new ArrayList<BuildPromotionEx>();
                boolean bl = false;
                for (SQueuedBuild sQueuedBuild : list) {
                    if (sQueuedBuild.getBuildPromotion().getId() == buildTypeIds.getId()) {
                        bl = true;
                        continue;
                    }
                    try {
                        try {
                            if (!bl || sQueuedBuild.isPersonal()) continue;
                        }
                        catch (IllegalArgumentException illegalArgumentException) {
                            throw QueuedBuildOptimizerImpl.a(illegalArgumentException);
                        }
                        arrayList.add((BuildPromotionEx)sQueuedBuild.getBuildPromotion());
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        throw QueuedBuildOptimizerImpl.a(illegalArgumentException);
                    }
                }
                for (BuildPromotionEx buildPromotionEx3 : arrayList) {
                    try {
                        if (!this.sameRevisions((BuildPromotionEx)buildTypeIds, buildPromotionEx3)) {
                            continue;
                        }
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        throw QueuedBuildOptimizerImpl.a(illegalArgumentException);
                    }
                    buildPromotionEx3.traverseSelfAndDependedOnMe(buildPromotionEx -> {
                        try {
                            if (!hashSet.add(buildPromotionEx)) {
                                return DependencyConsumer.Result.SKIP;
                            }
                        }
                        catch (IllegalArgumentException illegalArgumentException) {
                            throw QueuedBuildOptimizerImpl.a(illegalArgumentException);
                        }
                        try {
                            if (this.myAlreadyOptimizedBuildChains.containsKey(buildPromotionEx)) {
                                hashSet2.add((BuildPromotionEx)buildPromotionEx);
                                return DependencyConsumer.Result.SKIP;
                            }
                        }
                        catch (IllegalArgumentException illegalArgumentException) {
                            throw QueuedBuildOptimizerImpl.a(illegalArgumentException);
                        }
                        return DependencyConsumer.Result.CONTINUE;
                    });
                }
            }
        }
        this.removeOptimizedChainMarker(hashSet2);
    }

    private boolean sameRevisions(@NotNull BuildPromotionEx buildPromotionEx, @NotNull BuildPromotionEx buildPromotionEx2) {
        try {
            if (buildPromotionEx == null) {
                QueuedBuildOptimizerImpl.$$$reportNull$$$0(13);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw QueuedBuildOptimizerImpl.a(illegalArgumentException);
        }
        try {
            if (buildPromotionEx2 == null) {
                QueuedBuildOptimizerImpl.$$$reportNull$$$0(14);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw QueuedBuildOptimizerImpl.a(illegalArgumentException);
        }
        Map map = buildPromotionEx.getAllRevisionsMap();
        Map map2 = buildPromotionEx2.getAllRevisionsMap();
        try {
            if (map.size() != map2.size()) {
                return false;
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw QueuedBuildOptimizerImpl.a(illegalArgumentException);
        }
        for (Map.Entry entry : map.entrySet()) {
            BuildRevisionEx buildRevisionEx = (BuildRevisionEx)entry.getValue();
            BuildRevisionEx buildRevisionEx2 = (BuildRevisionEx)map2.get(entry.getKey());
            try {
                if (buildRevisionEx2 == null) {
                    return false;
                }
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw QueuedBuildOptimizerImpl.a(illegalArgumentException);
            }
            try {
                if (buildRevisionEx.getRevision().equals(buildRevisionEx2.getRevision())) continue;
                return false;
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw QueuedBuildOptimizerImpl.a(illegalArgumentException);
            }
        }
        return true;
    }

    private void cleanupAlreadyOptimizedBuildChainsHavingTheSameBuildType(@NotNull String string) {
        try {
            if (string == null) {
                QueuedBuildOptimizerImpl.$$$reportNull$$$0(15);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw QueuedBuildOptimizerImpl.a(illegalArgumentException);
        }
        try {
            if (this.myAlreadyOptimizedBuildChains.isEmpty()) {
                return;
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw QueuedBuildOptimizerImpl.a(illegalArgumentException);
        }
        HashSet<BuildPromotionEx> hashSet = new HashSet<BuildPromotionEx>();
        for (Map.Entry<BuildPromotionEx, Pair<BuildTypeIds, Set<Long>>> entry : this.myAlreadyOptimizedBuildChains.entrySet()) {
            try {
                if (((BuildTypeIds)entry.getValue().first).indexOf(string) == -1) continue;
                hashSet.add(entry.getKey());
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw QueuedBuildOptimizerImpl.a(illegalArgumentException);
            }
        }
        this.removeOptimizedChainMarker(hashSet);
    }

    @NotNull
    private static Map<String, BuildPromotionEx> getBuildChainPromotionsMap(@NotNull BuildPromotionEx buildPromotionEx) {
        HashMap<String, BuildPromotionEx> hashMap;
        try {
            if (buildPromotionEx == null) {
                QueuedBuildOptimizerImpl.$$$reportNull$$$0(16);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw QueuedBuildOptimizerImpl.a(illegalArgumentException);
        }
        HashMap<String, BuildPromotionEx> hashMap2 = new HashMap<String, BuildPromotionEx>();
        try {
            DependenciesUtil.consumeAll(Collections.singleton(buildPromotionEx), buildPromotion -> hashMap2.put(buildPromotion.getParentBuildTypeId(), (BuildPromotionEx)buildPromotion));
            hashMap = hashMap2;
            if (hashMap == null) {
                QueuedBuildOptimizerImpl.$$$reportNull$$$0(17);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw QueuedBuildOptimizerImpl.a(illegalArgumentException);
        }
        return hashMap;
    }

    private void cleanupAlreadyOptimizedBuilds(@NotNull BuildDistributorInputEx buildDistributorInputEx) {
        block13: {
            block16: {
                block14: {
                    try {
                        if (buildDistributorInputEx == null) {
                            QueuedBuildOptimizerImpl.$$$reportNull$$$0(18);
                        }
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        throw QueuedBuildOptimizerImpl.a(illegalArgumentException);
                    }
                    boolean bl = TeamCityProperties.getBooleanOrTrue((String)"teamcity.buildQueue.optimization.optimizeChainsOnce");
                    Boolean bl2 = (Boolean)buildDistributorInputEx.getCustomData("buildQueueOptimizer.alreadyOptimizedTopBuildsCleanupExecuted", Boolean.class);
                    try {
                        if (bl2 != null) break block13;
                        if (!bl) break block14;
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        throw QueuedBuildOptimizerImpl.a(illegalArgumentException);
                    }
                    HashSet<BuildPromotionEx> hashSet = new HashSet<BuildPromotionEx>();
                    for (Map.Entry<BuildPromotionEx, Pair<BuildTypeIds, Set<Long>>> entry : this.myAlreadyOptimizedBuildChains.entrySet()) {
                        BuildPromotionEx buildPromotionEx = entry.getKey();
                        try {
                            try {
                                if (buildPromotionEx.isCompositeBuild() && buildPromotionEx.getAssociatedBuild() instanceof SRunningBuild) {
                                    continue;
                                }
                            }
                            catch (IllegalArgumentException illegalArgumentException) {
                                throw QueuedBuildOptimizerImpl.a(illegalArgumentException);
                            }
                        }
                        catch (IllegalArgumentException illegalArgumentException) {
                            throw QueuedBuildOptimizerImpl.a(illegalArgumentException);
                        }
                        try {
                            if (this.myQueue.findQueued((BuildPromotion)buildPromotionEx) != null) continue;
                            hashSet.add(buildPromotionEx);
                        }
                        catch (IllegalArgumentException illegalArgumentException) {
                            throw QueuedBuildOptimizerImpl.a(illegalArgumentException);
                        }
                    }
                    this.removeOptimizedChainMarker(hashSet);
                    break block16;
                }
                this.myAlreadyOptimizedBuildChains.clear();
                this.myAlreadyOptimizedBuilds.clear();
            }
            buildDistributorInputEx.setCustomData("buildQueueOptimizer.alreadyOptimizedTopBuildsCleanupExecuted", (Object)true);
        }
    }

    private void removeOptimizedChainMarker(@NotNull Set<BuildPromotionEx> set) {
        try {
            if (set == null) {
                QueuedBuildOptimizerImpl.$$$reportNull$$$0(19);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw QueuedBuildOptimizerImpl.a(illegalArgumentException);
        }
        try {
            if (set.isEmpty()) {
                return;
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw QueuedBuildOptimizerImpl.a(illegalArgumentException);
        }
        for (BuildPromotionEx buildPromotionEx : set) {
            Pair<BuildTypeIds, Set<Long>> pair = this.myAlreadyOptimizedBuildChains.remove(buildPromotionEx);
            try {
                if (pair == null) continue;
                this.myAlreadyOptimizedBuilds.removeAll((Collection)pair.second);
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw QueuedBuildOptimizerImpl.a(illegalArgumentException);
            }
        }
    }

    public static boolean isAsyncPersistingEnabled() {
        return TeamCityProperties.getBooleanOrTrue((String)"teamcity.buildQueue.optimization.modifiedPromotions.asyncPersist");
    }

    public void serverShutdown() {
        QueuedBuildOptimizerImpl.persistQueuedDependencies(this.myModifiedBuildChainsPersister.getQueuedTasks());
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
