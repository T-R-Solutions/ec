/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.intellij.openapi.util.text.StringUtil
 *  jetbrains.buildServer.AgentRestrictorType
 *  jetbrains.buildServer.BuildProblemData
 *  jetbrains.buildServer.BuildTypeDescriptor
 *  jetbrains.buildServer.BuildTypeDescriptor$CheckoutType
 *  jetbrains.buildServer.log.Loggers
 *  jetbrains.buildServer.serverSide.AgentDescription
 *  jetbrains.buildServer.serverSide.BuildAgentFactory
 *  jetbrains.buildServer.serverSide.BuildAgentManager
 *  jetbrains.buildServer.serverSide.BuildEstimates
 *  jetbrains.buildServer.serverSide.BuildPromotion
 *  jetbrains.buildServer.serverSide.BuildPromotionEx
 *  jetbrains.buildServer.serverSide.BuildQueueEx
 *  jetbrains.buildServer.serverSide.BuildServerEx
 *  jetbrains.buildServer.serverSide.BuildTypeEx
 *  jetbrains.buildServer.serverSide.BuildTypeNotFoundException
 *  jetbrains.buildServer.serverSide.Compatibility
 *  jetbrains.buildServer.serverSide.CompatibilityResult
 *  jetbrains.buildServer.serverSide.QueuedBuildEx
 *  jetbrains.buildServer.serverSide.RunningBuildEx
 *  jetbrains.buildServer.serverSide.SAgentRestrictor
 *  jetbrains.buildServer.serverSide.SBuild
 *  jetbrains.buildServer.serverSide.SBuildAgent
 *  jetbrains.buildServer.serverSide.SBuildType
 *  jetbrains.buildServer.serverSide.SQueuedBuild
 *  jetbrains.buildServer.serverSide.TriggeredBy
 *  jetbrains.buildServer.serverSide.agentPools.AgentPoolManager
 *  jetbrains.buildServer.serverSide.agentTypes.SAgentType
 *  jetbrains.buildServer.serverSide.agentTypes.SAgentTypeComparator
 *  jetbrains.buildServer.serverSide.buildDistribution.BuildConfigurationInfo
 *  jetbrains.buildServer.serverSide.buildDistribution.BuildPromotionInfo
 *  jetbrains.buildServer.serverSide.buildDistribution.QueuedBuildInfo
 *  jetbrains.buildServer.serverSide.impl.CanRunOnAgentsCalculator
 *  jetbrains.buildServer.serverSide.impl.LogUtil
 *  jetbrains.buildServer.serverSide.impl.TriggeredByParser
 *  jetbrains.buildServer.serverSide.impl.WaitReasonStatisticsCollector
 *  jetbrains.buildServer.serverSide.impl.timeEstimation.BuildEstimator
 *  jetbrains.buildServer.serverSide.impl.virtualAgent.StartableAgentType
 *  jetbrains.buildServer.serverSide.impl.virtualAgent.VirtualAgentsManager
 *  jetbrains.buildServer.users.SUser
 *  jetbrains.buildServer.users.User
 *  jetbrains.buildServer.util.impl.Lazy
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package jetbrains.buildServer.serverSide.impl;

import com.intellij.openapi.util.text.StringUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.stream.Collectors;
import jetbrains.buildServer.AgentRestrictorType;
import jetbrains.buildServer.BuildProblemData;
import jetbrains.buildServer.BuildTypeDescriptor;
import jetbrains.buildServer.log.Loggers;
import jetbrains.buildServer.serverSide.AgentDescription;
import jetbrains.buildServer.serverSide.BuildAgentFactory;
import jetbrains.buildServer.serverSide.BuildAgentManager;
import jetbrains.buildServer.serverSide.BuildEstimates;
import jetbrains.buildServer.serverSide.BuildPromotion;
import jetbrains.buildServer.serverSide.BuildPromotionEx;
import jetbrains.buildServer.serverSide.BuildQueueEx;
import jetbrains.buildServer.serverSide.BuildServerEx;
import jetbrains.buildServer.serverSide.BuildTypeEx;
import jetbrains.buildServer.serverSide.BuildTypeNotFoundException;
import jetbrains.buildServer.serverSide.Compatibility;
import jetbrains.buildServer.serverSide.CompatibilityResult;
import jetbrains.buildServer.serverSide.QueuedBuildEx;
import jetbrains.buildServer.serverSide.RunningBuildEx;
import jetbrains.buildServer.serverSide.SAgentRestrictor;
import jetbrains.buildServer.serverSide.SBuild;
import jetbrains.buildServer.serverSide.SBuildAgent;
import jetbrains.buildServer.serverSide.SBuildType;
import jetbrains.buildServer.serverSide.SQueuedBuild;
import jetbrains.buildServer.serverSide.TriggeredBy;
import jetbrains.buildServer.serverSide.agentPools.AgentPoolManager;
import jetbrains.buildServer.serverSide.agentTypes.SAgentType;
import jetbrains.buildServer.serverSide.agentTypes.SAgentTypeComparator;
import jetbrains.buildServer.serverSide.buildDistribution.BuildConfigurationInfo;
import jetbrains.buildServer.serverSide.buildDistribution.BuildPromotionInfo;
import jetbrains.buildServer.serverSide.buildDistribution.QueuedBuildInfo;
import jetbrains.buildServer.serverSide.impl.CanRunOnAgentsCalculator;
import jetbrains.buildServer.serverSide.impl.LogUtil;
import jetbrains.buildServer.serverSide.impl.TriggeredByParser;
import jetbrains.buildServer.serverSide.impl.WaitReasonStatisticsCollector;
import jetbrains.buildServer.serverSide.impl.timeEstimation.BuildEstimator;
import jetbrains.buildServer.serverSide.impl.virtualAgent.StartableAgentType;
import jetbrains.buildServer.serverSide.impl.virtualAgent.VirtualAgentsManager;
import jetbrains.buildServer.users.SUser;
import jetbrains.buildServer.users.User;
import jetbrains.buildServer.util.impl.Lazy;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Exception performing whole class analysis ignored.
 */
public class QueuedBuildImpl
implements QueuedBuildEx {
    private final BuildEstimator myBuildEstimator;
    private final BuildQueueEx myBuildQueue;
    private final BuildAgentManager myAgentManager;
    private final AgentPoolManager myAgentPoolManager;
    private final BuildAgentFactory myBuildAgentFactory;
    private final BuildPromotionEx myBuildPromotion;
    private final TriggeredByParser myTriggeredByParser;
    private final BuildServerEx myServer;
    private final CanRunOnAgentsCalculator myCanRunOnCalculator;
    private final WaitReasonStatisticsCollector myWaitReasonsCollector;
    private final Semaphore myStarting;
    private volatile boolean myStarted;
    private volatile boolean myPersisted;
    private final SAgentRestrictor myAgentRestrictor;
    private final Lazy<Boolean> myCanBeReplacedByMergeOptimization;
    private final String myRawTriggeredBy;
    private volatile TriggeredBy myTriggeredBy;

    public QueuedBuildImpl(@NotNull BuildPromotion buildPromotion, @Nullable SAgentRestrictor sAgentRestrictor, @NotNull String string, @NotNull BuildEstimator buildEstimator, @NotNull BuildQueueEx buildQueueEx, @NotNull BuildAgentManager buildAgentManager, @NotNull AgentPoolManager agentPoolManager, @NotNull BuildAgentFactory buildAgentFactory, @NotNull BuildServerEx buildServerEx, @NotNull TriggeredByParser triggeredByParser, @NotNull WaitReasonStatisticsCollector waitReasonStatisticsCollector, @NotNull CanRunOnAgentsCalculator canRunOnAgentsCalculator) {
        if (buildPromotion == null) {
            QueuedBuildImpl.$$$reportNull$$$0(0);
        }
        if (string == null) {
            QueuedBuildImpl.$$$reportNull$$$0(1);
        }
        if (buildEstimator == null) {
            QueuedBuildImpl.$$$reportNull$$$0(2);
        }
        if (buildQueueEx == null) {
            QueuedBuildImpl.$$$reportNull$$$0(3);
        }
        if (buildAgentManager == null) {
            QueuedBuildImpl.$$$reportNull$$$0(4);
        }
        if (agentPoolManager == null) {
            QueuedBuildImpl.$$$reportNull$$$0(5);
        }
        if (buildAgentFactory == null) {
            QueuedBuildImpl.$$$reportNull$$$0(6);
        }
        if (buildServerEx == null) {
            QueuedBuildImpl.$$$reportNull$$$0(7);
        }
        if (triggeredByParser == null) {
            QueuedBuildImpl.$$$reportNull$$$0(8);
        }
        if (waitReasonStatisticsCollector == null) {
            QueuedBuildImpl.$$$reportNull$$$0(9);
        }
        if (canRunOnAgentsCalculator == null) {
            QueuedBuildImpl.$$$reportNull$$$0(10);
        }
        this.myStarting = new Semaphore(1);
        this.myStarted = false;
        this.myPersisted = false;
        this.myBuildPromotion = (BuildPromotionEx)buildPromotion;
        this.myRawTriggeredBy = string;
        this.myBuildEstimator = buildEstimator;
        this.myBuildQueue = buildQueueEx;
        this.myTriggeredByParser = triggeredByParser;
        this.myAgentManager = buildAgentManager;
        this.myAgentPoolManager = agentPoolManager;
        this.myBuildAgentFactory = buildAgentFactory;
        this.myServer = buildServerEx;
        this.myAgentRestrictor = sAgentRestrictor;
        this.myCanRunOnCalculator = canRunOnAgentsCalculator;
        this.myWaitReasonsCollector = waitReasonStatisticsCollector;
        this.myCanBeReplacedByMergeOptimization = new /* Unavailable Anonymous Inner Class!! */;
    }

    @NotNull
    public SBuildType getBuildType() {
        SBuildType sBuildType;
        try {
            sBuildType = this.getType();
            if (sBuildType == null) {
                QueuedBuildImpl.$$$reportNull$$$0(11);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw QueuedBuildImpl.a(buildTypeNotFoundException);
        }
        return sBuildType;
    }

    @NotNull
    public List<SBuildAgent> getCanRunOnAgents() {
        List<SBuildAgent> list;
        block7: {
            List<SBuildAgent> list2;
            block8: {
                try {
                    try {
                        if (!this.myBuildPromotion.isAgentLessBuild()) break block7;
                        list2 = Collections.emptyList();
                        if (list2 != null) break block8;
                    }
                    catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                        throw QueuedBuildImpl.a(buildTypeNotFoundException);
                    }
                    QueuedBuildImpl.$$$reportNull$$$0(12);
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw QueuedBuildImpl.a(buildTypeNotFoundException);
                }
            }
            return list2;
        }
        try {
            list = this.getCanRunOnAgents(this.myAgentManager.getRegisteredAgents());
            if (list == null) {
                QueuedBuildImpl.$$$reportNull$$$0(13);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw QueuedBuildImpl.a(buildTypeNotFoundException);
        }
        return list;
    }

    @NotNull
    public List<StartableAgentType> getCanRunOnAgentTypes() {
        List<StartableAgentType> list;
        block8: {
            List<StartableAgentType> list2;
            block9: {
                try {
                    try {
                        if (!this.myBuildPromotion.isAgentLessBuild()) break block8;
                        list2 = Collections.emptyList();
                        if (list2 != null) break block9;
                    }
                    catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                        throw QueuedBuildImpl.a(buildTypeNotFoundException);
                    }
                    QueuedBuildImpl.$$$reportNull$$$0(14);
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw QueuedBuildImpl.a(buildTypeNotFoundException);
                }
            }
            return list2;
        }
        HashMap<SAgentType, StartableAgentType> hashMap = new HashMap<SAgentType, StartableAgentType>();
        for (StartableAgentType startableAgentType : ((VirtualAgentsManager)this.myServer.getSingletonService(VirtualAgentsManager.class)).getStartableAgentTypes()) {
            hashMap.put(startableAgentType.getAgentType(), startableAgentType);
        }
        ArrayList arrayList = new ArrayList(this.myBuildPromotion.getCanRunOnAgentTypes(hashMap.keySet()));
        try {
            Collections.sort(arrayList, new SAgentTypeComparator());
            list = arrayList.stream().map(sAgentType -> (StartableAgentType)hashMap.get(sAgentType)).collect(Collectors.toList());
            if (list == null) {
                QueuedBuildImpl.$$$reportNull$$$0(15);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw QueuedBuildImpl.a(buildTypeNotFoundException);
        }
        return list;
    }

    @NotNull
    public List<SBuildAgent> getCanRunOnAgents(@NotNull List<SBuildAgent> list) {
        List list2;
        List list3;
        block12: {
            List<SBuildAgent> list4;
            block13: {
                try {
                    if (list == null) {
                        QueuedBuildImpl.$$$reportNull$$$0(16);
                    }
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw QueuedBuildImpl.a(buildTypeNotFoundException);
                }
                try {
                    try {
                        if (!this.myBuildPromotion.isAgentLessBuild()) break block12;
                        list4 = Collections.emptyList();
                        if (list4 != null) break block13;
                    }
                    catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                        throw QueuedBuildImpl.a(buildTypeNotFoundException);
                    }
                    QueuedBuildImpl.$$$reportNull$$$0(17);
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw QueuedBuildImpl.a(buildTypeNotFoundException);
                }
            }
            return list4;
        }
        Map map = this.myCanRunOnCalculator.getCanRunOnAgents(Collections.singletonList(this), list);
        List list5 = (List)map.get(this);
        try {
            list3 = list5 == null ? Collections.emptyList() : list5;
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw QueuedBuildImpl.a(buildTypeNotFoundException);
        }
        try {
            list2 = list3;
            if (list3 == null) {
                QueuedBuildImpl.$$$reportNull$$$0(18);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw QueuedBuildImpl.a(buildTypeNotFoundException);
        }
        return list2;
    }

    public boolean canRunInPool(int n) {
        SAgentRestrictor sAgentRestrictor = this.getAgentRestrictor();
        try {
            if (sAgentRestrictor != null) {
                return sAgentRestrictor.isRelatedToPool(n);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw QueuedBuildImpl.a(buildTypeNotFoundException);
        }
        return this.myAgentPoolManager.agentPoolContainsProject(n, this.getProjectId());
    }

    @NotNull
    public Collection<SBuildAgent> getCompatibleAgents(@NotNull Collection<SBuildAgent> collection) {
        Collection collection2;
        block10: {
            List<SBuildAgent> list;
            block11: {
                try {
                    if (collection == null) {
                        QueuedBuildImpl.$$$reportNull$$$0(19);
                    }
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw QueuedBuildImpl.a(buildTypeNotFoundException);
                }
                try {
                    try {
                        if (!this.myBuildPromotion.isAgentLessBuild()) break block10;
                        list = Collections.emptyList();
                        if (list != null) break block11;
                    }
                    catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                        throw QueuedBuildImpl.a(buildTypeNotFoundException);
                    }
                    QueuedBuildImpl.$$$reportNull$$$0(20);
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw QueuedBuildImpl.a(buildTypeNotFoundException);
                }
            }
            return list;
        }
        try {
            collection2 = this.myBuildPromotion.getCompatibleAgents(collection);
            if (collection2 == null) {
                QueuedBuildImpl.$$$reportNull$$$0(21);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw QueuedBuildImpl.a(buildTypeNotFoundException);
        }
        return collection2;
    }

    @NotNull
    public Map<SBuildAgent, CompatibilityResult> getCompatibilityMap() {
        LinkedHashMap<SBuildAgent, CompatibilityResult> linkedHashMap;
        block8: {
            Map<SBuildAgent, CompatibilityResult> map;
            block9: {
                try {
                    try {
                        if (!this.myBuildPromotion.isAgentLessBuild()) break block8;
                        map = Collections.emptyMap();
                        if (map != null) break block9;
                    }
                    catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                        throw QueuedBuildImpl.a(buildTypeNotFoundException);
                    }
                    QueuedBuildImpl.$$$reportNull$$$0(22);
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw QueuedBuildImpl.a(buildTypeNotFoundException);
                }
            }
            return map;
        }
        LinkedHashMap<SBuildAgent, CompatibilityResult> linkedHashMap2 = new LinkedHashMap<SBuildAgent, CompatibilityResult>();
        ArrayList arrayList = new ArrayList(this.myAgentManager.getRegisteredAgents());
        arrayList.addAll(this.myAgentManager.getUnregisteredAgents());
        Collections.sort(arrayList);
        Map map = this.myBuildPromotion.getCompatibilityMap(arrayList);
        for (SBuildAgent sBuildAgent : arrayList) {
            linkedHashMap2.put(sBuildAgent, (CompatibilityResult)map.getOrDefault(sBuildAgent, new Compatibility()));
        }
        try {
            linkedHashMap = linkedHashMap2;
            if (linkedHashMap == null) {
                QueuedBuildImpl.$$$reportNull$$$0(23);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw QueuedBuildImpl.a(buildTypeNotFoundException);
        }
        return linkedHashMap;
    }

    @NotNull
    public Collection<SBuildAgent> getCompatibleAgents() {
        ArrayList<SBuildAgent> arrayList;
        block10: {
            List<SBuildAgent> list;
            block11: {
                try {
                    try {
                        if (!this.myBuildPromotion.isAgentLessBuild()) break block10;
                        list = Collections.emptyList();
                        if (list != null) break block11;
                    }
                    catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                        throw QueuedBuildImpl.a(buildTypeNotFoundException);
                    }
                    QueuedBuildImpl.$$$reportNull$$$0(24);
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw QueuedBuildImpl.a(buildTypeNotFoundException);
                }
            }
            return list;
        }
        ArrayList<SBuildAgent> arrayList2 = new ArrayList<SBuildAgent>();
        for (Map.Entry<SBuildAgent, CompatibilityResult> entry : this.getCompatibilityMap().entrySet()) {
            try {
                if (!entry.getValue().isCompatible()) continue;
                arrayList2.add(entry.getKey());
            }
            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                throw QueuedBuildImpl.a(buildTypeNotFoundException);
            }
        }
        try {
            arrayList = arrayList2;
            if (arrayList == null) {
                QueuedBuildImpl.$$$reportNull$$$0(25);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw QueuedBuildImpl.a(buildTypeNotFoundException);
        }
        return arrayList;
    }

    @NotNull
    public CompatibilityResult getAgentCompatibility(@NotNull AgentDescription agentDescription) {
        CompatibilityResult compatibilityResult;
        try {
            if (agentDescription == null) {
                QueuedBuildImpl.$$$reportNull$$$0(26);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw QueuedBuildImpl.a(buildTypeNotFoundException);
        }
        try {
            compatibilityResult = this.myBuildPromotion.getAgentCompatibility(agentDescription);
            if (compatibilityResult == null) {
                QueuedBuildImpl.$$$reportNull$$$0(27);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw QueuedBuildImpl.a(buildTypeNotFoundException);
        }
        return compatibilityResult;
    }

    @NotNull
    public String describe(boolean bl) {
        String string;
        try {
            string = "Queued build {item id=" + this.getItemId() + ", promotion=" + LogUtil.describe((BuildPromotion)this.myBuildPromotion) + "}";
            if (string == null) {
                QueuedBuildImpl.$$$reportNull$$$0(28);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw QueuedBuildImpl.a(buildTypeNotFoundException);
        }
        return string;
    }

    @Nullable
    public SBuildAgent getBuildAgent() {
        SBuildAgent sBuildAgent;
        Integer n = this.getBuildAgentId();
        try {
            sBuildAgent = n != null ? this.myAgentManager.findAgentById(n.intValue(), true) : null;
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw QueuedBuildImpl.a(buildTypeNotFoundException);
        }
        return sBuildAgent;
    }

    @NotNull
    public BuildPromotionInfo getBuildPromotionInfo() {
        BuildPromotionEx buildPromotionEx;
        try {
            buildPromotionEx = this.getBuildPromotion();
            if (buildPromotionEx == null) {
                QueuedBuildImpl.$$$reportNull$$$0(29);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw QueuedBuildImpl.a(buildTypeNotFoundException);
        }
        return buildPromotionEx;
    }

    @NotNull
    public BuildPromotionEx getBuildPromotion() {
        BuildPromotionEx buildPromotionEx;
        try {
            buildPromotionEx = this.myBuildPromotion;
            if (buildPromotionEx == null) {
                QueuedBuildImpl.$$$reportNull$$$0(30);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw QueuedBuildImpl.a(buildTypeNotFoundException);
        }
        return buildPromotionEx;
    }

    /*
     * Loose catch block
     */
    public boolean tryLockBuildStarting() {
        try {
            boolean bl;
            block9: {
                block8: {
                    block10: {
                        if (this.myStarted) break block8;
                        if (this.myBuildQueue.findQueued(this.getItemId()) == null) break block8;
                        break block10;
                        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                            throw QueuedBuildImpl.a(buildTypeNotFoundException);
                        }
                    }
                    try {
                        block11: {
                            if (!this.myStarting.tryAcquire()) break block8;
                            break block11;
                            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                                throw QueuedBuildImpl.a(buildTypeNotFoundException);
                            }
                        }
                        bl = true;
                        break block9;
                    }
                    catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                        throw QueuedBuildImpl.a(buildTypeNotFoundException);
                    }
                }
                bl = false;
            }
            return bl;
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            return false;
        }
    }

    public void startChangesCollecting() {
        this.myBuildQueue.scheduleCheckingForChangesForQueuedBuilds(Collections.singleton(this));
    }

    public void unlockBuildStarting() {
        this.myStarting.release();
    }

    @NotNull
    public BuildTypeEx getBuildTypeEx() throws BuildTypeNotFoundException {
        BuildTypeEx buildTypeEx;
        BuildTypeEx buildTypeEx2 = this.myBuildPromotion.getBuildType();
        try {
            if (buildTypeEx2 == null) {
                throw new BuildTypeNotFoundException(LogUtil.describeMissingBuildTypeForUI((String)this.myBuildPromotion.getBuildTypeId()));
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw QueuedBuildImpl.a(buildTypeNotFoundException);
        }
        try {
            buildTypeEx = buildTypeEx2;
            if (buildTypeEx == null) {
                QueuedBuildImpl.$$$reportNull$$$0(31);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw QueuedBuildImpl.a(buildTypeNotFoundException);
        }
        return buildTypeEx;
    }

    public boolean isPersisted() {
        return this.myPersisted;
    }

    public void markPersisted() {
        this.myPersisted = true;
    }

    @NotNull
    public BuildConfigurationInfo getBuildConfiguration() {
        block4: {
            BuildTypeEx buildTypeEx;
            block5: {
                BuildTypeEx buildTypeEx2 = this.myBuildPromotion.getBuildType();
                try {
                    try {
                        if (buildTypeEx2 == null) break block4;
                        buildTypeEx = buildTypeEx2;
                        if (buildTypeEx != null) break block5;
                    }
                    catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                        throw QueuedBuildImpl.a(buildTypeNotFoundException);
                    }
                    QueuedBuildImpl.$$$reportNull$$$0(32);
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw QueuedBuildImpl.a(buildTypeNotFoundException);
                }
            }
            return buildTypeEx;
        }
        return new /* Unavailable Anonymous Inner Class!! */;
    }

    @Nullable
    public Integer getBuildAgentId() {
        Integer n;
        block5: {
            block4: {
                try {
                    try {
                        if (this.myAgentRestrictor == null || this.myAgentRestrictor.getType() != AgentRestrictorType.SINGLE_AGENT) break block4;
                    }
                    catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                        throw QueuedBuildImpl.a(buildTypeNotFoundException);
                    }
                    n = this.myAgentRestrictor.getId();
                    break block5;
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw QueuedBuildImpl.a(buildTypeNotFoundException);
                }
            }
            n = null;
        }
        return n;
    }

    @Nullable
    public SAgentRestrictor getAgentRestrictor() {
        return this.myAgentRestrictor;
    }

    @NotNull
    public Date getWhenQueued() {
        Date date;
        Date date2;
        block7: {
            Date date3;
            block8: {
                date2 = this.myBuildPromotion.getQueuedDate();
                try {
                    try {
                        if (date2 != null) break block7;
                        Loggers.SERVER.error("Promotion returns null queued date for a queued build: " + String.valueOf(this));
                        date3 = this.myServer.getTimeService().nowDate();
                        if (date3 != null) break block8;
                    }
                    catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                        throw QueuedBuildImpl.a(buildTypeNotFoundException);
                    }
                    QueuedBuildImpl.$$$reportNull$$$0(33);
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw QueuedBuildImpl.a(buildTypeNotFoundException);
                }
            }
            return date3;
        }
        try {
            date = date2;
            if (date == null) {
                QueuedBuildImpl.$$$reportNull$$$0(34);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw QueuedBuildImpl.a(buildTypeNotFoundException);
        }
        return date;
    }

    @NotNull
    public String getItemId() {
        String string;
        try {
            string = String.valueOf(this.getBuildPromotion().getId());
            if (string == null) {
                QueuedBuildImpl.$$$reportNull$$$0(35);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw QueuedBuildImpl.a(buildTypeNotFoundException);
        }
        return string;
    }

    @NotNull
    public String getRequestor() {
        String string;
        try {
            string = this.getTriggeredBy().getAsString();
            if (string == null) {
                QueuedBuildImpl.$$$reportNull$$$0(36);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw QueuedBuildImpl.a(buildTypeNotFoundException);
        }
        return string;
    }

    public TriggeredBy getTriggeredBy() {
        try {
            if (this.myTriggeredBy == null) {
                this.myTriggeredBy = this.myTriggeredByParser.getForQueuedBuild((SQueuedBuild)this, this.myRawTriggeredBy);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw QueuedBuildImpl.a(buildTypeNotFoundException);
        }
        return this.myTriggeredBy;
    }

    @NotNull
    public String getName() {
        String string;
        try {
            string = this.getType().getName();
            if (string == null) {
                QueuedBuildImpl.$$$reportNull$$$0(37);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw QueuedBuildImpl.a(buildTypeNotFoundException);
        }
        return string;
    }

    public String getFullName() {
        try {
            return this.getType().getFullName();
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            return "Unknown build configuration";
        }
    }

    public String getDescription() {
        return this.getType().getDescription();
    }

    @NotNull
    public String getProjectName() {
        String string;
        try {
            string = this.getType().getProjectName();
            if (string == null) {
                QueuedBuildImpl.$$$reportNull$$$0(38);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw QueuedBuildImpl.a(buildTypeNotFoundException);
        }
        return string;
    }

    public Collection<String> getRunnerTypes() {
        return this.getType().getRunnerTypes();
    }

    public String toString() {
        return this.describe(true);
    }

    @NotNull
    public String getBuildTypeId() {
        String string;
        try {
            string = this.myBuildPromotion.getBuildTypeId();
            if (string == null) {
                QueuedBuildImpl.$$$reportNull$$$0(39);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw QueuedBuildImpl.a(buildTypeNotFoundException);
        }
        return string;
    }

    @NotNull
    public String getExternalId() {
        String string;
        try {
            string = this.myBuildPromotion.getBuildTypeExternalId();
            if (string == null) {
                QueuedBuildImpl.$$$reportNull$$$0(40);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw QueuedBuildImpl.a(buildTypeNotFoundException);
        }
        return string;
    }

    private SBuildType getType() {
        return this.getBuildTypeEx();
    }

    public boolean isPersonal() {
        return this.myBuildPromotion.isPersonal();
    }

    public int getOrderNumber() {
        return this.myBuildQueue.getOrderNumber((SQueuedBuild)this);
    }

    public BuildTypeDescriptor.CheckoutType getCheckoutType() {
        return this.getType().getCheckoutType();
    }

    public boolean isPaused() {
        return this.getType().isPaused();
    }

    @NotNull
    public String getProjectId() {
        String string;
        try {
            string = StringUtil.notNullize((String)this.myBuildPromotion.getProjectId());
            if (string == null) {
                QueuedBuildImpl.$$$reportNull$$$0(41);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw QueuedBuildImpl.a(buildTypeNotFoundException);
        }
        return string;
    }

    @NotNull
    public String getProjectExternalId() {
        String string;
        try {
            string = StringUtil.notNullize((String)this.myBuildPromotion.getProjectExternalId());
            if (string == null) {
                QueuedBuildImpl.$$$reportNull$$$0(42);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw QueuedBuildImpl.a(buildTypeNotFoundException);
        }
        return string;
    }

    public int compareTo(BuildTypeDescriptor buildTypeDescriptor) {
        return this.getType().compareTo((Object)buildTypeDescriptor);
    }

    @Nullable
    public BuildEstimates getBuildEstimates() {
        return this.myBuildEstimator.getEstimates((QueuedBuildInfo)this);
    }

    public void removeFromQueue(User user, String string) {
        this.myBuildQueue.removeQueuedBuilds(Collections.singleton(this), (SUser)user, string);
    }

    public void markAsStarted() {
        this.myStarted = true;
    }

    public void removeFailedToStartFromQueue() {
        this.myBuildQueue.removeItems(Collections.singleton(this.getItemId()), null, null);
    }

    public void terminate(boolean bl, @Nullable String string, @NotNull BuildProblemData buildProblemData) {
        try {
            if (buildProblemData == null) {
                QueuedBuildImpl.$$$reportNull$$$0(43);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw QueuedBuildImpl.a(buildTypeNotFoundException);
        }
        this.myBuildQueue.terminateQueuedBuild((SQueuedBuild)this, null, bl, string, buildProblemData);
    }

    public boolean isStarted() {
        return this.myStarted;
    }

    @Nullable
    public SBuild getSequenceBuild() {
        return this.myBuildPromotion.getSequenceBuild();
    }

    public boolean isCanBeReplacedByMergeOptimization() {
        return (Boolean)this.myCanBeReplacedByMergeOptimization.get();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NotNull
    public RunningBuildEx startBuild(@NotNull String string) {
        RunningBuildEx runningBuildEx;
        RunningBuildEx runningBuildEx2;
        try {
            if (string == null) {
                QueuedBuildImpl.$$$reportNull$$$0(44);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw QueuedBuildImpl.a(buildTypeNotFoundException);
        }
        try {
            if (!this.myBuildPromotion.isAgentLessBuild()) {
                throw new IllegalStateException("The build is not agent-less");
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw QueuedBuildImpl.a(buildTypeNotFoundException);
        }
        try {
            if (!this.tryLockBuildStarting()) {
                throw new IllegalStateException("The queued build is already starting");
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw QueuedBuildImpl.a(buildTypeNotFoundException);
        }
        try {
            if (this.isStarted()) {
                throw new IllegalStateException("The queued build is already started");
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw QueuedBuildImpl.a(buildTypeNotFoundException);
        }
        RunningBuildEx runningBuildEx3 = null;
        try {
            BuildTypeEx buildTypeEx = (BuildTypeEx)this.getBuildType();
            runningBuildEx3 = buildTypeEx.newRunningBuild((QueuedBuildEx)this, (SBuildAgent)this.myBuildAgentFactory.createFakeAgent(string), this.myServer.getTimeService().nowDate());
            runningBuildEx3.start(true);
            this.markAsStarted();
            runningBuildEx2 = runningBuildEx3;
        }
        catch (Throwable throwable) {
            try {
                this.unlockBuildStarting();
                if (runningBuildEx3 != null) {
                    this.removeFromQueue(null, "Build started");
                }
            }
            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                throw QueuedBuildImpl.a(buildTypeNotFoundException);
            }
            throw throwable;
        }
        try {
            this.unlockBuildStarting();
            if (runningBuildEx3 != null) {
                this.removeFromQueue(null, "Build started");
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw QueuedBuildImpl.a(buildTypeNotFoundException);
        }
        try {
            runningBuildEx = runningBuildEx2;
            if (runningBuildEx == null) {
                QueuedBuildImpl.$$$reportNull$$$0(45);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw QueuedBuildImpl.a(buildTypeNotFoundException);
        }
        return runningBuildEx;
    }

    @NotNull
    public Map<String, Long> getWaitReasons() {
        Map map;
        try {
            map = this.myWaitReasonsCollector.getCurrentWaitReasons((SQueuedBuild)this);
            if (map == null) {
                QueuedBuildImpl.$$$reportNull$$$0(46);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw QueuedBuildImpl.a(buildTypeNotFoundException);
        }
        return map;
    }

    @Nullable
    public String getLatestWaitReason() {
        return this.myWaitReasonsCollector.getLatestWaitReason((SQueuedBuild)this);
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

    private static BuildTypeNotFoundException a(BuildTypeNotFoundException buildTypeNotFoundException) {
        return buildTypeNotFoundException;
    }
}
