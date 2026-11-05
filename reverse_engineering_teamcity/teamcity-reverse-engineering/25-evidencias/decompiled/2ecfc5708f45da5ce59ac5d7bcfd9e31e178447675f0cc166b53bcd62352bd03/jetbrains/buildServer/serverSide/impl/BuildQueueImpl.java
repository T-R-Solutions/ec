/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.annotations.VisibleForTesting
 *  com.intellij.openapi.diagnostic.Logger
 *  com.intellij.openapi.util.Pair
 *  gnu.trove.map.TLongIntMap
 *  gnu.trove.map.hash.TLongIntHashMap
 *  jetbrains.buildServer.AgentRestrictor
 *  jetbrains.buildServer.AgentRestrictorType
 *  jetbrains.buildServer.BuildProblemData
 *  jetbrains.buildServer.ExtensionsProvider
 *  jetbrains.buildServer.QueuedBuild
 *  jetbrains.buildServer.Used
 *  jetbrains.buildServer.artifacts.ArtifactDependency
 *  jetbrains.buildServer.artifacts.RevisionRule
 *  jetbrains.buildServer.artifacts.RevisionRules
 *  jetbrains.buildServer.log.LogUtil
 *  jetbrains.buildServer.log.Loggable
 *  jetbrains.buildServer.log.Loggers
 *  jetbrains.buildServer.messages.ErrorData
 *  jetbrains.buildServer.messages.Status
 *  jetbrains.buildServer.metrics.ServerLoadMetrics
 *  jetbrains.buildServer.parameters.ReferencesResolverUtil
 *  jetbrains.buildServer.serverSide.AgentRestrictorFactory
 *  jetbrains.buildServer.serverSide.BuildHistoryEx
 *  jetbrains.buildServer.serverSide.BuildPromotion
 *  jetbrains.buildServer.serverSide.BuildPromotionEx
 *  jetbrains.buildServer.serverSide.BuildPromotionManager$BuildPromotionInitFromDbHint
 *  jetbrains.buildServer.serverSide.BuildPromotionReplacement
 *  jetbrains.buildServer.serverSide.BuildQueueEx
 *  jetbrains.buildServer.serverSide.BuildQueueInit
 *  jetbrains.buildServer.serverSide.BuildQueueOrderingStrategy
 *  jetbrains.buildServer.serverSide.BuildServerListener
 *  jetbrains.buildServer.serverSide.BuildTypeEx
 *  jetbrains.buildServer.serverSide.BuildTypeNotFoundException
 *  jetbrains.buildServer.serverSide.ChangesCollectionCondition
 *  jetbrains.buildServer.serverSide.ChangesCollectionCondition$Result
 *  jetbrains.buildServer.serverSide.DependencyConsumer$Result
 *  jetbrains.buildServer.serverSide.MultiNodeLocks
 *  jetbrains.buildServer.serverSide.MultiNodesEvents
 *  jetbrains.buildServer.serverSide.QueuedBuildEx
 *  jetbrains.buildServer.serverSide.QueuedBuildFactory
 *  jetbrains.buildServer.serverSide.ReadOnlyBuildTypeSettings
 *  jetbrains.buildServer.serverSide.SAgentRestrictor
 *  jetbrains.buildServer.serverSide.SBuild
 *  jetbrains.buildServer.serverSide.SBuildType
 *  jetbrains.buildServer.serverSide.SQueuedBuild
 *  jetbrains.buildServer.serverSide.SecurityContextEx
 *  jetbrains.buildServer.serverSide.ServerResponsibility
 *  jetbrains.buildServer.serverSide.TeamCityProperties
 *  jetbrains.buildServer.serverSide.TriggerTask
 *  jetbrains.buildServer.serverSide.TriggeredBy
 *  jetbrains.buildServer.serverSide.TriggeredByBuilder
 *  jetbrains.buildServer.serverSide.WebLinks
 *  jetbrains.buildServer.serverSide.agentPools.AgentPool
 *  jetbrains.buildServer.serverSide.agentTypes.SAgentType
 *  jetbrains.buildServer.serverSide.artifacts.SArtifactDependency
 *  jetbrains.buildServer.serverSide.audit.ActionType
 *  jetbrains.buildServer.serverSide.audit.AuditLogFactory
 *  jetbrains.buildServer.serverSide.buildLog.MessageAttrs
 *  jetbrains.buildServer.serverSide.cleanup.ServerCleanupManager
 *  jetbrains.buildServer.serverSide.db.DBException
 *  jetbrains.buildServer.serverSide.db.DBFunctions
 *  jetbrains.buildServer.serverSide.db.SQLRunnerEx
 *  jetbrains.buildServer.serverSide.dependency.BuildDependency
 *  jetbrains.buildServer.serverSide.dependency.BuildDependencyEx
 *  jetbrains.buildServer.serverSide.dependency.BuildDependencyGraph
 *  jetbrains.buildServer.serverSide.dependency.TopBuildDependencyGraph
 *  jetbrains.buildServer.serverSide.executors.ExecutorServices
 *  jetbrains.buildServer.serverSide.impl.BatchTriggerTaskImpl
 *  jetbrains.buildServer.serverSide.impl.BuildOrderTuple
 *  jetbrains.buildServer.serverSide.impl.BuildPromotionDeletedException
 *  jetbrains.buildServer.serverSide.impl.BuildPromotionImpl
 *  jetbrains.buildServer.serverSide.impl.BuildPromotionManagerEx
 *  jetbrains.buildServer.serverSide.impl.BuildPromotionReplacementLog$ReplacementReason
 *  jetbrains.buildServer.serverSide.impl.BuildQueueImpl$5
 *  jetbrains.buildServer.serverSide.impl.BuildQueueImpl$ApplyCurrentOrder
 *  jetbrains.buildServer.serverSide.impl.BuildQueueImpl$ApplyOrder
 *  jetbrains.buildServer.serverSide.impl.BuildQueueImpl$BuildChainInfo
 *  jetbrains.buildServer.serverSide.impl.BuildQueueImpl$BuildQueueDefaultOrdering
 *  jetbrains.buildServer.serverSide.impl.BuildQueueImpl$CanceledDequeuedBuildHandler
 *  jetbrains.buildServer.serverSide.impl.BuildQueueImpl$ChangeOrderAction
 *  jetbrains.buildServer.serverSide.impl.BuildQueueImpl$DequeuedBuildHandler
 *  jetbrains.buildServer.serverSide.impl.BuildQueueImpl$EventInfo
 *  jetbrains.buildServer.serverSide.impl.BuildQueueImpl$EventType
 *  jetbrains.buildServer.serverSide.impl.BuildQueueImpl$MoveBottom
 *  jetbrains.buildServer.serverSide.impl.BuildQueueImpl$MoveDependenciesToTop
 *  jetbrains.buildServer.serverSide.impl.BuildQueueImpl$MoveToTop
 *  jetbrains.buildServer.serverSide.impl.BuildQueueImpl$NodesSynchronizationTask
 *  jetbrains.buildServer.serverSide.impl.BuildQueueImpl$QueuedBuildSettings
 *  jetbrains.buildServer.serverSide.impl.BuildQueueImpl$QueuedBuildTuple
 *  jetbrains.buildServer.serverSide.impl.BuildQueueImpl$RedundantDequeuedBuildHandler
 *  jetbrains.buildServer.serverSide.impl.BuildQueueImpl$RemovedDequeuedBuildHandler
 *  jetbrains.buildServer.serverSide.impl.ChainRemainingTimeComparator
 *  jetbrains.buildServer.serverSide.impl.DependenciesUtil
 *  jetbrains.buildServer.serverSide.impl.DependentBuildStartPredicate
 *  jetbrains.buildServer.serverSide.impl.DependentBuildsStopper
 *  jetbrains.buildServer.serverSide.impl.DuplicatePersonalBuildException
 *  jetbrains.buildServer.serverSide.impl.IntersectingPersonalChainsPreprocessor
 *  jetbrains.buildServer.serverSide.impl.LogUtil
 *  jetbrains.buildServer.serverSide.impl.QueuedBuildsChangesCollector
 *  jetbrains.buildServer.serverSide.impl.QueuedTasksProcessor
 *  jetbrains.buildServer.serverSide.impl.QueuedTasksProcessor$ManyTasksProcessor
 *  jetbrains.buildServer.serverSide.impl.RemoteBuildType
 *  jetbrains.buildServer.serverSide.impl.RemoteBuildTypeIdUtil
 *  jetbrains.buildServer.serverSide.impl.TriggeredByParser
 *  jetbrains.buildServer.serverSide.impl.WaitReasonStatisticsCollector
 *  jetbrains.buildServer.serverSide.impl.beans.BuildPromotionContext
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.restrictors.SingleAgentRestrictorInfo
 *  jetbrains.buildServer.serverSide.impl.dependency.BuildChainsOptimizer
 *  jetbrains.buildServer.serverSide.impl.dependency.BuildDependencyImpl
 *  jetbrains.buildServer.serverSide.impl.dependency.OptimizationListener
 *  jetbrains.buildServer.serverSide.impl.timeEstimation.BuildDurationCalculator
 *  jetbrains.buildServer.serverSide.stat.BuildStatisticsFactory
 *  jetbrains.buildServer.serverSide.versionedSettings.VersionedSettingsManager
 *  jetbrains.buildServer.users.SUser
 *  jetbrains.buildServer.users.User
 *  jetbrains.buildServer.util.CollectionsUtil
 *  jetbrains.buildServer.util.Disposable
 *  jetbrains.buildServer.util.EventDispatcher
 *  jetbrains.buildServer.util.ItemProcessor
 *  jetbrains.buildServer.util.NamedThreadFactory
 *  jetbrains.buildServer.util.OptionSupport
 *  jetbrains.buildServer.util.Predicate
 *  jetbrains.buildServer.util.StringUtil
 *  jetbrains.buildServer.util.TimeService
 *  jetbrains.buildServer.util.filters.Filter
 *  jetbrains.buildServer.util.filters.FilterUtil
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package jetbrains.buildServer.serverSide.impl;

import com.google.common.annotations.VisibleForTesting;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.util.Pair;
import gnu.trove.map.TLongIntMap;
import gnu.trove.map.hash.TLongIntHashMap;
import java.lang.invoke.LambdaMetafactory;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.EventListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import jetbrains.buildServer.AgentRestrictor;
import jetbrains.buildServer.AgentRestrictorType;
import jetbrains.buildServer.BuildProblemData;
import jetbrains.buildServer.ExtensionsProvider;
import jetbrains.buildServer.QueuedBuild;
import jetbrains.buildServer.Used;
import jetbrains.buildServer.artifacts.ArtifactDependency;
import jetbrains.buildServer.artifacts.RevisionRule;
import jetbrains.buildServer.artifacts.RevisionRules;
import jetbrains.buildServer.log.Loggable;
import jetbrains.buildServer.log.Loggers;
import jetbrains.buildServer.messages.ErrorData;
import jetbrains.buildServer.messages.Status;
import jetbrains.buildServer.metrics.ServerLoadMetrics;
import jetbrains.buildServer.parameters.ReferencesResolverUtil;
import jetbrains.buildServer.serverSide.AgentRestrictorFactory;
import jetbrains.buildServer.serverSide.BuildHistoryEx;
import jetbrains.buildServer.serverSide.BuildPromotion;
import jetbrains.buildServer.serverSide.BuildPromotionEx;
import jetbrains.buildServer.serverSide.BuildPromotionManager;
import jetbrains.buildServer.serverSide.BuildPromotionReplacement;
import jetbrains.buildServer.serverSide.BuildQueueEx;
import jetbrains.buildServer.serverSide.BuildQueueInit;
import jetbrains.buildServer.serverSide.BuildQueueOrderingStrategy;
import jetbrains.buildServer.serverSide.BuildServerListener;
import jetbrains.buildServer.serverSide.BuildTypeEx;
import jetbrains.buildServer.serverSide.BuildTypeNotFoundException;
import jetbrains.buildServer.serverSide.ChangesCollectionCondition;
import jetbrains.buildServer.serverSide.DependencyConsumer;
import jetbrains.buildServer.serverSide.MultiNodeLocks;
import jetbrains.buildServer.serverSide.MultiNodesEvents;
import jetbrains.buildServer.serverSide.QueuedBuildEx;
import jetbrains.buildServer.serverSide.QueuedBuildFactory;
import jetbrains.buildServer.serverSide.ReadOnlyBuildTypeSettings;
import jetbrains.buildServer.serverSide.SAgentRestrictor;
import jetbrains.buildServer.serverSide.SBuild;
import jetbrains.buildServer.serverSide.SBuildType;
import jetbrains.buildServer.serverSide.SQueuedBuild;
import jetbrains.buildServer.serverSide.SecurityContextEx;
import jetbrains.buildServer.serverSide.ServerResponsibility;
import jetbrains.buildServer.serverSide.TeamCityProperties;
import jetbrains.buildServer.serverSide.TriggerTask;
import jetbrains.buildServer.serverSide.TriggeredBy;
import jetbrains.buildServer.serverSide.TriggeredByBuilder;
import jetbrains.buildServer.serverSide.WebLinks;
import jetbrains.buildServer.serverSide.agentPools.AgentPool;
import jetbrains.buildServer.serverSide.agentTypes.SAgentType;
import jetbrains.buildServer.serverSide.artifacts.SArtifactDependency;
import jetbrains.buildServer.serverSide.audit.ActionType;
import jetbrains.buildServer.serverSide.audit.AuditLogFactory;
import jetbrains.buildServer.serverSide.buildLog.MessageAttrs;
import jetbrains.buildServer.serverSide.cleanup.ServerCleanupManager;
import jetbrains.buildServer.serverSide.db.DBException;
import jetbrains.buildServer.serverSide.db.DBFunctions;
import jetbrains.buildServer.serverSide.db.SQLRunnerEx;
import jetbrains.buildServer.serverSide.dependency.BuildDependency;
import jetbrains.buildServer.serverSide.dependency.BuildDependencyEx;
import jetbrains.buildServer.serverSide.dependency.BuildDependencyGraph;
import jetbrains.buildServer.serverSide.dependency.TopBuildDependencyGraph;
import jetbrains.buildServer.serverSide.executors.ExecutorServices;
import jetbrains.buildServer.serverSide.impl.BatchTriggerTaskImpl;
import jetbrains.buildServer.serverSide.impl.BuildOrderTuple;
import jetbrains.buildServer.serverSide.impl.BuildPromotionDeletedException;
import jetbrains.buildServer.serverSide.impl.BuildPromotionImpl;
import jetbrains.buildServer.serverSide.impl.BuildPromotionManagerEx;
import jetbrains.buildServer.serverSide.impl.BuildPromotionReplacementLog;
import jetbrains.buildServer.serverSide.impl.BuildQueueImpl;
import jetbrains.buildServer.serverSide.impl.ChainRemainingTimeComparator;
import jetbrains.buildServer.serverSide.impl.DependenciesUtil;
import jetbrains.buildServer.serverSide.impl.DependentBuildStartPredicate;
import jetbrains.buildServer.serverSide.impl.DependentBuildsStopper;
import jetbrains.buildServer.serverSide.impl.DuplicatePersonalBuildException;
import jetbrains.buildServer.serverSide.impl.IntersectingPersonalChainsPreprocessor;
import jetbrains.buildServer.serverSide.impl.LogUtil;
import jetbrains.buildServer.serverSide.impl.QueuedBuildsChangesCollector;
import jetbrains.buildServer.serverSide.impl.QueuedTasksProcessor;
import jetbrains.buildServer.serverSide.impl.RemoteBuildType;
import jetbrains.buildServer.serverSide.impl.RemoteBuildTypeIdUtil;
import jetbrains.buildServer.serverSide.impl.TriggeredByParser;
import jetbrains.buildServer.serverSide.impl.WaitReasonStatisticsCollector;
import jetbrains.buildServer.serverSide.impl.beans.BuildPromotionContext;
import jetbrains.buildServer.serverSide.impl.buildDistribution.restrictors.SingleAgentRestrictorInfo;
import jetbrains.buildServer.serverSide.impl.dependency.BuildChainsOptimizer;
import jetbrains.buildServer.serverSide.impl.dependency.BuildDependencyImpl;
import jetbrains.buildServer.serverSide.impl.dependency.OptimizationListener;
import jetbrains.buildServer.serverSide.impl.timeEstimation.BuildDurationCalculator;
import jetbrains.buildServer.serverSide.stat.BuildStatisticsFactory;
import jetbrains.buildServer.serverSide.versionedSettings.VersionedSettingsManager;
import jetbrains.buildServer.users.SUser;
import jetbrains.buildServer.users.User;
import jetbrains.buildServer.util.CollectionsUtil;
import jetbrains.buildServer.util.Disposable;
import jetbrains.buildServer.util.EventDispatcher;
import jetbrains.buildServer.util.ItemProcessor;
import jetbrains.buildServer.util.NamedThreadFactory;
import jetbrains.buildServer.util.OptionSupport;
import jetbrains.buildServer.util.Predicate;
import jetbrains.buildServer.util.StringUtil;
import jetbrains.buildServer.util.TimeService;
import jetbrains.buildServer.util.filters.Filter;
import jetbrains.buildServer.util.filters.FilterUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Exception performing whole class analysis ignored.
 */
public class BuildQueueImpl
implements BuildQueueEx,
BuildQueueInit {
    static final String TEAMCITY_CHAINS_CHECK_FOR_CHANGES_EARLY_PROP = "teamcity.queue.chainedBuilds.startCheckingForChangesEarly";
    public static final String TEAMCITY_BUILD_CHAINS_REUSE_QUEUED_BUILDS_PROP = "teamcity.buildChains.reuseQueuedBuilds";
    public static final String TRIGGERED_BY_QUEUE_OPTIMIZATION_ENABLED_PARAM = "queueMergingEnabled";
    static final String TRIGGERED_BY_SNAPSHOT_DEPENDENCY = "snapshotDependency";
    private static final long NOT_DEFINED_VERSION = 0L;
    private static final char BUILD_QUEUE_ORDER_ID_DELIMITER = ',';
    public static final String ALWAYS_COLLECT_CHANGES_IN_QUEUE_PROP = "teamcity.queue.alwaysCollectChangesInQueue";
    public static final String BUILD_QUEUE_ASYNC_EVENT_HANDLING_ENABLED = "teamcity.buildQueue.asyncEventHandling.enabled";
    private final AtomicReference<List<QueuedBuildEx>> myQueue = new AtomicReference(Collections.emptyList());
    private final AtomicReference<Map<Long, QueuedBuildEx>> myQueueIndex = new AtomicReference(new ConcurrentHashMap());
    private final AtomicReference<Map<String, List<QueuedBuildEx>>> myQueueBuildTypeIndex = new AtomicReference(new ConcurrentHashMap());
    private EventDispatcher<BuildServerListener> myEventDispatcher;
    private SQLRunnerEx mySqlRunner;
    private QueuedBuildFactory myQueuedBuildFactory;
    private AgentRestrictorFactory myAgentRestrictorFactory;
    private BuildHistoryEx myHistory;
    private SecurityContextEx mySecurityContext;
    private ServerResponsibility myServerResponsibility;
    private MultiNodesEvents myMultiNodesEvents;
    private ServerCleanupManager myCleanupManager;
    private boolean myQueueRestored = false;
    private AuditLogFactory myAuditLogFactory;
    private BuildQueueOrderingStrategy myOrderingStrategy = new BuildQueueDefaultOrdering();
    private QueuedBuildsChangesCollector myQueuedBuildsChangesCollector;
    private BuildStatisticsFactory myBuildStatisticsFactory;
    private ExtensionsProvider myExtensionsProvider;
    private BuildDurationCalculator myBuildDurationCalculator;
    private BuildPromotionReplacement myBuildPromotionReplacementLog;
    private VersionedSettingsManager myVersionedSettingsManager;
    private ExecutorServices myExecutorServices;
    private BuildPromotionManagerEx myBuildPromotionManager;
    private BuildChainsOptimizer myBuildChainsOptimizer;
    private MultiNodeLocks myMultiNodeLocks;
    private TimeService myTimeService;
    private WaitReasonStatisticsCollector myWaitReasonStatisticsCollector;
    private final NodesSynchronizationTask myNodesSynchronizationTask = new NodesSynchronizationTask(this);
    private final AtomicLong myOrderVersion = new AtomicLong(-1L);
    private QueuedTasksProcessor<EventInfo> myAsyncEventHandler;
    private WebLinks myWebLinks;
    private static final Set<String> PARAMETER_PREFIXES_REQUIRING_CHANGES_COLLECTING;
    private boolean myMergeBuildsInQueue = TeamCityProperties.getBooleanOrTrue((String)"teamcity.queue.mergeBuilds");
    private final boolean myAvoidDelayingOfStartedChains = TeamCityProperties.getBooleanOrTrue((String)"teamcity.queue.mergeBuilds.avoidDelayingOfStartedChains");
    private static final Logger LOG;
    static final /* synthetic */ boolean $assertionsDisabled;

    public void setBuildStatisticsFactory(BuildStatisticsFactory buildStatisticsFactory) {
        this.myBuildStatisticsFactory = buildStatisticsFactory;
    }

    public void setEventDispatcher(EventDispatcher<BuildServerListener> eventDispatcher) {
        this.myEventDispatcher = eventDispatcher;
        eventDispatcher.addListener((EventListener)new /* Unavailable Anonymous Inner Class!! */);
    }

    public void setSqlRunner(SQLRunnerEx sQLRunnerEx) {
        this.mySqlRunner = sQLRunnerEx;
    }

    public void setQueuedBuildFactory(QueuedBuildFactory queuedBuildFactory) {
        this.myQueuedBuildFactory = queuedBuildFactory;
    }

    public void setAgentRestrictorFactory(AgentRestrictorFactory agentRestrictorFactory) {
        this.myAgentRestrictorFactory = agentRestrictorFactory;
    }

    public void setHistory(BuildHistoryEx buildHistoryEx) {
        this.myHistory = buildHistoryEx;
    }

    public void setSecurityContext(SecurityContextEx securityContextEx) {
        this.mySecurityContext = securityContextEx;
    }

    public void setCleanupManager(ServerCleanupManager serverCleanupManager) {
        this.myCleanupManager = serverCleanupManager;
    }

    public void setAuditLogFactory(AuditLogFactory auditLogFactory) {
        this.myAuditLogFactory = auditLogFactory;
    }

    public void setExtensionsProvider(ExtensionsProvider extensionsProvider) {
        this.myExtensionsProvider = extensionsProvider;
    }

    public void setBuildDurationCalculator(BuildDurationCalculator buildDurationCalculator) {
        this.myBuildDurationCalculator = buildDurationCalculator;
    }

    public void setBuildPromotionReplacementLog(BuildPromotionReplacement buildPromotionReplacement) {
        this.myBuildPromotionReplacementLog = buildPromotionReplacement;
    }

    public void setVersionedSettingsManager(VersionedSettingsManager versionedSettingsManager) {
        this.myVersionedSettingsManager = versionedSettingsManager;
    }

    public void setQueuedBuildsChangesCollector(QueuedBuildsChangesCollector queuedBuildsChangesCollector) {
        this.myQueuedBuildsChangesCollector = queuedBuildsChangesCollector;
    }

    public void setWaitReasonStatisticsCollector(WaitReasonStatisticsCollector waitReasonStatisticsCollector) {
        this.myWaitReasonStatisticsCollector = waitReasonStatisticsCollector;
    }

    public void setExecutorServices(ExecutorServices executorServices) {
        this.myExecutorServices = executorServices;
        this.initAsyncEventHandler();
    }

    public void setMultiNodeLocks(MultiNodeLocks multiNodeLocks) {
        this.myMultiNodeLocks = multiNodeLocks;
    }

    public void setWebLinks(@NotNull WebLinks webLinks) {
        try {
            if (webLinks == null) {
                BuildQueueImpl.$$$reportNull$$$0(0);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        this.myWebLinks = webLinks;
    }

    private void initAsyncEventHandler() {
        int n = TeamCityProperties.getInteger((String)"teamcity.buildQueue.asyncEventsHandler.queueSize", (int)500);
        int n2 = TeamCityProperties.getInteger((String)"teamcity.buildQueue.asyncEventsHandler.tasksPackSize", (int)500);
        this.myAsyncEventHandler = new QueuedTasksProcessor((QueuedTasksProcessor.ManyTasksProcessor)new /* Unavailable Anonymous Inner Class!! */, this.myExecutorServices.getLowPriorityExecutorService(), n, n2, "Build queue events handler");
    }

    /*
     * Exception decompiling
     */
    private void sendEventNow(EventInfo var1_1) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [9[CASE]], but top level block is 1[TRYBLOCK]
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

    public void setServerResponsibility(ServerResponsibility serverResponsibility) {
        this.myServerResponsibility = serverResponsibility;
    }

    public void setMultiNodesEvents(MultiNodesEvents multiNodesEvents) {
        this.myMultiNodesEvents = multiNodesEvents;
    }

    public void setBuildPromotionManager(BuildPromotionManagerEx buildPromotionManagerEx) {
        this.myBuildPromotionManager = buildPromotionManagerEx;
    }

    public void setBuildChainsOptimizer(BuildChainsOptimizer buildChainsOptimizer) {
        this.myBuildChainsOptimizer = buildChainsOptimizer;
    }

    public void setTimeService(TimeService timeService) {
        this.myTimeService = timeService;
    }

    @NotNull
    public List<SQueuedBuild> getItems() {
        return new ArrayList<SQueuedBuild>((Collection)this.myQueue.get());
    }

    @NotNull
    private List<QueuedBuildEx> getQueuedBuildsCopy() {
        return new ArrayList<QueuedBuildEx>((Collection)this.myQueue.get());
    }

    @NotNull
    public List<SQueuedBuild> getItems(String string) {
        ArrayList<SQueuedBuild> arrayList;
        ArrayList<SQueuedBuild> arrayList2;
        block7: {
            ArrayList<SQueuedBuild> arrayList3;
            block8: {
                arrayList2 = new ArrayList<SQueuedBuild>();
                try {
                    try {
                        if (string != null) break block7;
                        arrayList3 = arrayList2;
                        if (arrayList3 != null) break block8;
                    }
                    catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                        throw BuildQueueImpl.a(buildTypeNotFoundException);
                    }
                    BuildQueueImpl.$$$reportNull$$$0(1);
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw BuildQueueImpl.a(buildTypeNotFoundException);
                }
            }
            return arrayList3;
        }
        try {
            this.processBuildTypeItems(string, (ItemProcessor<QueuedBuildEx>)((ItemProcessor)arrayList2::add));
            arrayList = arrayList2;
            if (arrayList == null) {
                BuildQueueImpl.$$$reportNull$$$0(2);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        return arrayList;
    }

    private void processBuildTypeItems(@NotNull String string, @NotNull ItemProcessor<QueuedBuildEx> itemProcessor) {
        try {
            if (string == null) {
                BuildQueueImpl.$$$reportNull$$$0(3);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        try {
            if (itemProcessor == null) {
                BuildQueueImpl.$$$reportNull$$$0(4);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        String string2 = string;
        boolean bl = false;
        if (RemoteBuildTypeIdUtil.isValidRemoteBuildTypeId((String)string)) {
            string2 = RemoteBuildTypeIdUtil.getParentBuildTypeId((String)string);
            bl = true;
        }
        for (QueuedBuildEx queuedBuildEx : this.myQueueBuildTypeIndex.get().getOrDefault(string2, Collections.emptyList())) {
            try {
                try {
                    block16: {
                        try {
                            try {
                                if (!bl) break block16;
                                if (!queuedBuildEx.isPersonal()) continue;
                            }
                            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                                throw BuildQueueImpl.a(buildTypeNotFoundException);
                            }
                            if (!queuedBuildEx.getBuildTypeId().equals(string)) continue;
                        }
                        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                            throw BuildQueueImpl.a(buildTypeNotFoundException);
                        }
                    }
                    if (itemProcessor.processItem((Object)queuedBuildEx)) continue;
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw BuildQueueImpl.a(buildTypeNotFoundException);
                }
                return;
            }
            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                throw BuildQueueImpl.a(buildTypeNotFoundException);
            }
        }
    }

    @NotNull
    public List<SQueuedBuild> getItemsByPool(int n) {
        List list;
        try {
            list = (List)FilterUtil.filterAndCopy((Iterable)this.myQueue.get(), new ArrayList(), queuedBuildEx -> queuedBuildEx.canRunInPool(n));
            if (list == null) {
                BuildQueueImpl.$$$reportNull$$$0(5);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        return list;
    }

    public int getNumberOfItemsByPool(int n) {
        return this.getItemsByPool(n).size();
    }

    public void scheduleCheckingForChangesForQueuedBuilds(@NotNull Collection<QueuedBuildEx> collection) {
        try {
            if (collection == null) {
                BuildQueueImpl.$$$reportNull$$$0(6);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        this.forceScheduleCheckingForChanges(collection);
    }

    @Nullable
    public QueuedBuildEx findQueued(@NotNull BuildPromotion buildPromotion) {
        try {
            if (buildPromotion == null) {
                BuildQueueImpl.$$$reportNull$$$0(7);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        return this.getIfNotStarted(buildPromotion.getId());
    }

    @Nullable
    private QueuedBuildEx getIfNotStarted(long l) {
        QueuedBuildEx queuedBuildEx;
        block5: {
            QueuedBuildEx queuedBuildEx2;
            block4: {
                queuedBuildEx2 = this.myQueueIndex.get().get(l);
                try {
                    try {
                        if (queuedBuildEx2 == null || !queuedBuildEx2.isStarted()) break block4;
                    }
                    catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                        throw BuildQueueImpl.a(buildTypeNotFoundException);
                    }
                    queuedBuildEx = null;
                    break block5;
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw BuildQueueImpl.a(buildTypeNotFoundException);
                }
            }
            queuedBuildEx = queuedBuildEx2;
        }
        return queuedBuildEx;
    }

    public int getNumberOfItems() {
        return this.myQueue.get().size();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     */
    @Nullable
    private QueuedBuildEx findSimilarItem(@NotNull QueuedBuildEx queuedBuildEx) {
        try {
            if (queuedBuildEx == null) {
                BuildQueueImpl.$$$reportNull$$$0(8);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        Disposable disposable = NamedThreadFactory.patchThreadName((String)("Looking for already queued builds similar to a newly triggered item: " + LogUtil.describe((Loggable)queuedBuildEx)));
        try {
            TopBuildDependencyGraph topBuildDependencyGraph = queuedBuildEx.getBuildPromotion().getTopDependencyGraph();
            List<QueuedBuildEx> list = this.getItems(queuedBuildEx.getBuildTypeId(), (AgentRestrictor)queuedBuildEx.getAgentRestrictor());
            SUser sUser = queuedBuildEx.getTriggeredBy().getUser();
            for (QueuedBuildEx queuedBuildEx2 : list) {
                SUser sUser2;
                TopBuildDependencyGraph topBuildDependencyGraph2;
                block18: {
                    block17: {
                        BuildPromotionEx buildPromotionEx = queuedBuildEx2.getBuildPromotion();
                        topBuildDependencyGraph2 = buildPromotionEx.getTopDependencyGraph();
                        try {
                            if (sUser != null || !this.isEquivalentPartOf(topBuildDependencyGraph, topBuildDependencyGraph2)) break block17;
                        }
                        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                            throw BuildQueueImpl.a(buildTypeNotFoundException);
                        }
                        sUser2 = queuedBuildEx2;
                        return sUser2;
                    }
                    sUser2 = queuedBuildEx2.getTriggeredBy().getUser();
                    if (sUser == null) continue;
                    if (sUser2 == null) continue;
                    break block18;
                    catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                        throw BuildQueueImpl.a(buildTypeNotFoundException);
                    }
                }
                try {
                    block19: {
                        if (sUser.getId() != sUser2.getId()) continue;
                        break block19;
                        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                            throw BuildQueueImpl.a(buildTypeNotFoundException);
                        }
                    }
                    if (!this.isEquivalentPartOf(topBuildDependencyGraph, topBuildDependencyGraph2)) continue;
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw BuildQueueImpl.a(buildTypeNotFoundException);
                }
                QueuedBuildEx queuedBuildEx3 = queuedBuildEx2;
                return queuedBuildEx3;
            }
            Iterator<QueuedBuildEx> iterator = null;
            return iterator;
        }
        finally {
            disposable.dispose();
        }
    }

    private boolean isEquivalentPartOf(@NotNull TopBuildDependencyGraph topBuildDependencyGraph, @NotNull TopBuildDependencyGraph topBuildDependencyGraph2) {
        boolean bl;
        try {
            if (topBuildDependencyGraph == null) {
                BuildQueueImpl.$$$reportNull$$$0(9);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        try {
            if (topBuildDependencyGraph2 == null) {
                BuildQueueImpl.$$$reportNull$$$0(10);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        boolean bl2 = topBuildDependencyGraph.isEquivalentPartOf((BuildDependencyGraph)topBuildDependencyGraph2);
        try {
            if (!bl2) {
                return false;
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        Map map = topBuildDependencyGraph2.getNodesMap();
        boolean[] blArray = new boolean[1];
        try {
            topBuildDependencyGraph.traverseTopDown(buildPromotion -> {
                block4: {
                    BuildPromotionEx buildPromotionEx = (BuildPromotionEx)buildPromotion;
                    BuildPromotionEx buildPromotionEx2 = (BuildPromotionEx)map.get(buildPromotion.getParentBuildTypeId());
                    try {
                        try {
                            if (buildPromotionEx2 == null || buildPromotionEx.getRequirements().equals(buildPromotionEx2.getRequirements())) break block4;
                        }
                        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                            throw BuildQueueImpl.a(buildTypeNotFoundException);
                        }
                        blArray[0] = true;
                        return false;
                    }
                    catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                        throw BuildQueueImpl.a(buildTypeNotFoundException);
                    }
                }
                return true;
            });
            bl = !blArray[0];
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        return bl;
    }

    private void notifyAddedToQueue(@NotNull Collection<QueuedBuildEx> collection) {
        try {
            if (collection == null) {
                BuildQueueImpl.$$$reportNull$$$0(11);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        ServerLoadMetrics.BUILD_QUEUE_RATE.increment((double)collection.size());
        for (SQueuedBuild sQueuedBuild : collection) {
            TriggeredBy triggeredBy = sQueuedBuild.getTriggeredBy();
            BuildPromotionEx buildPromotionEx = (BuildPromotionEx)sQueuedBuild.getBuildPromotion();
            try {
                if (triggeredBy.isTriggeredByUser()) {
                    this.myAuditLogFactory.createForBuildPromotion((BuildPromotion)buildPromotionEx).log(ActionType.BUILD_ADD_TO_QUEUE, null, sQueuedBuild.getBuildTypeId(), triggeredBy.getUser(), new Object[0]);
                }
            }
            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                throw BuildQueueImpl.a(buildTypeNotFoundException);
            }
            this.fireBuildAddedToQueue(sQueuedBuild);
            Loggers.ACTIVITIES.info("Build added to queue; " + LogUtil.describeInDetail((SQueuedBuild)sQueuedBuild));
        }
        this.scheduleCheckingForChanges(collection);
    }

    private void scheduleCheckingForChanges(@NotNull Collection<QueuedBuildEx> collection) {
        block7: {
            try {
                if (collection == null) {
                    BuildQueueImpl.$$$reportNull$$$0(12);
                }
            }
            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                throw BuildQueueImpl.a(buildTypeNotFoundException);
            }
            try {
                try {
                    if (!collection.isEmpty() && TeamCityProperties.getBooleanOrTrue((String)"teamcity.queue.chainedBuilds.startCheckingForChangesEarly")) break block7;
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw BuildQueueImpl.a(buildTypeNotFoundException);
                }
                return;
            }
            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                throw BuildQueueImpl.a(buildTypeNotFoundException);
            }
        }
        this.forceScheduleCheckingForChanges(collection);
    }

    private void forceScheduleCheckingForChanges(@NotNull Collection<QueuedBuildEx> collection) {
        try {
            if (collection == null) {
                BuildQueueImpl.$$$reportNull$$$0(13);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        ArrayList<QueuedBuildEx> arrayList = new ArrayList<QueuedBuildEx>();
        for (QueuedBuildEx queuedBuildEx : collection) {
            try {
                if (!this.shouldCollectChangesInQueue(queuedBuildEx.getBuildPromotion())) continue;
                arrayList.add(queuedBuildEx);
            }
            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                throw BuildQueueImpl.a(buildTypeNotFoundException);
            }
        }
        this.myQueuedBuildsChangesCollector.startChangesCollecting(arrayList);
    }

    private void fireBuildAddedToQueue(@NotNull SQueuedBuild sQueuedBuild) {
        try {
            if (sQueuedBuild == null) {
                BuildQueueImpl.$$$reportNull$$$0(14);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        try {
            this.scheduleEvent(new EventInfo(EventType.ADDED, sQueuedBuild, null, null));
            if (!((BuildPromotionEx)sQueuedBuild.getBuildPromotion()).isChangeCollectingNeeded()) {
                this.scheduleEvent(new EventInfo(EventType.CHANGES_LOADED, sQueuedBuild, null, null));
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
    }

    private void scheduleEvent(@NotNull EventInfo eventInfo) {
        block6: {
            block5: {
                try {
                    if (eventInfo == null) {
                        BuildQueueImpl.$$$reportNull$$$0(15);
                    }
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw BuildQueueImpl.a(buildTypeNotFoundException);
                }
                try {
                    if (!TeamCityProperties.getBooleanOrTrue((String)"teamcity.buildQueue.asyncEventHandling.enabled")) break block5;
                    this.myAsyncEventHandler.addToQueue((Object)eventInfo);
                    break block6;
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw BuildQueueImpl.a(buildTypeNotFoundException);
                }
            }
            this.sendEventNow(eventInfo);
        }
    }

    public boolean shouldCollectChangesInQueue(@NotNull BuildPromotionEx buildPromotionEx) {
        block49: {
            BuildTypeEx buildTypeEx;
            block48: {
                block45: {
                    boolean bl;
                    block47: {
                        block46: {
                            try {
                                if (buildPromotionEx == null) {
                                    BuildQueueImpl.$$$reportNull$$$0(16);
                                }
                            }
                            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                                throw BuildQueueImpl.a(buildTypeNotFoundException);
                            }
                            try {
                                if (!buildPromotionEx.isChangeCollectingNeeded()) {
                                    return false;
                                }
                            }
                            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                                throw BuildQueueImpl.a(buildTypeNotFoundException);
                            }
                            buildTypeEx = buildPromotionEx.getBuildType();
                            try {
                                if (buildTypeEx == null) {
                                    return false;
                                }
                            }
                            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                                throw BuildQueueImpl.a(buildTypeNotFoundException);
                            }
                            ChangesCollectionCondition.Result result = this.shouldCollectChangesNow(buildPromotionEx);
                            try {
                                try {
                                    if (result == ChangesCollectionCondition.Result.UNKNOWN) break block45;
                                    if (result != ChangesCollectionCondition.Result.YES) break block46;
                                }
                                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                                    throw BuildQueueImpl.a(buildTypeNotFoundException);
                                }
                                bl = true;
                                break block47;
                            }
                            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                                throw BuildQueueImpl.a(buildTypeNotFoundException);
                            }
                        }
                        bl = false;
                    }
                    return bl;
                }
                try {
                    if (buildPromotionEx.isMaximumNumberOfBuildsPerBranchLimited()) {
                        return true;
                    }
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw BuildQueueImpl.a(buildTypeNotFoundException);
                }
                try {
                    if (TeamCityProperties.getBoolean((String)"teamcity.queue.alwaysCollectChangesInQueue")) {
                        return true;
                    }
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw BuildQueueImpl.a(buildTypeNotFoundException);
                }
                Object object = buildPromotionEx.getAttribute("teamcity.build.freezeRequiresCollectedChanges");
                try {
                    try {
                        if (!(object instanceof String) || !Boolean.parseBoolean((String)object)) break block48;
                    }
                    catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                        throw BuildQueueImpl.a(buildTypeNotFoundException);
                    }
                    return true;
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw BuildQueueImpl.a(buildTypeNotFoundException);
                }
            }
            try {
                if (buildPromotionEx.isPartOfBuildChain()) {
                    return true;
                }
            }
            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                throw BuildQueueImpl.a(buildTypeNotFoundException);
            }
            try {
                if (this.myVersionedSettingsManager.shouldTakeSettingsFromVcs((SBuildType)buildTypeEx, buildPromotionEx)) {
                    return true;
                }
            }
            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                throw BuildQueueImpl.a(buildTypeNotFoundException);
            }
            try {
                try {
                    if (buildTypeEx.getDependedOnMe().isEmpty() || !TeamCityProperties.getBooleanOrTrue((String)"teamcity.queue.singleQueuedLeafBuild.collectChangesInQueue")) break block49;
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw BuildQueueImpl.a(buildTypeNotFoundException);
                }
                return true;
            }
            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                throw BuildQueueImpl.a(buildTypeNotFoundException);
            }
        }
        HashSet hashSet = new HashSet();
        ReadOnlyBuildTypeSettings readOnlyBuildTypeSettings = buildPromotionEx.getBuildSettings().getParentSettings();
        List list = readOnlyBuildTypeSettings.getRequirements();
        for (Object object : list) {
            try {
                try {
                    if (object.getId() != null && !readOnlyBuildTypeSettings.isEnabled(object.getId())) {
                        continue;
                    }
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw BuildQueueImpl.a(buildTypeNotFoundException);
                }
            }
            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                throw BuildQueueImpl.a(buildTypeNotFoundException);
            }
            String string = object.getPropertyName();
            String string2 = object.getPropertyValue();
            try {
                hashSet.addAll(ReferencesResolverUtil.getReferences((String)string));
                if (string2 == null) continue;
                hashSet.addAll(ReferencesResolverUtil.getReferences((String)string2));
            }
            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                throw BuildQueueImpl.a(buildTypeNotFoundException);
            }
        }
        for (Object object : hashSet) {
            for (String string2 : PARAMETER_PREFIXES_REQUIRING_CHANGES_COLLECTING) {
                try {
                    if (!((String)object).startsWith(string2)) continue;
                    return true;
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw BuildQueueImpl.a(buildTypeNotFoundException);
                }
            }
        }
        return false;
    }

    @NotNull
    private ChangesCollectionCondition.Result shouldCollectChangesNow(@NotNull BuildPromotionEx buildPromotionEx) {
        ChangesCollectionCondition.Result result;
        try {
            if (buildPromotionEx == null) {
                BuildQueueImpl.$$$reportNull$$$0(17);
            }
        }
        catch (Throwable throwable) {
            throw BuildQueueImpl.a(throwable);
        }
        Collection collection = this.myExtensionsProvider.getExtensions(ChangesCollectionCondition.class);
        for (ChangesCollectionCondition changesCollectionCondition : collection) {
            ChangesCollectionCondition.Result result2;
            try {
                ChangesCollectionCondition.Result result3 = changesCollectionCondition.shouldCollectChangesNow((BuildPromotion)buildPromotionEx);
                if (result3 == ChangesCollectionCondition.Result.UNKNOWN) continue;
                result2 = result3;
            }
            catch (Throwable throwable) {
                Loggers.SERVER.debug("Error while checking the ChangesCollectionCondition for " + String.valueOf(changesCollectionCondition.getClass()), throwable);
                continue;
            }
            if (result2 == null) {
                BuildQueueImpl.$$$reportNull$$$0(18);
            }
            return result2;
        }
        try {
            result = ChangesCollectionCondition.Result.UNKNOWN;
            if (result == null) {
                BuildQueueImpl.$$$reportNull$$$0(19);
            }
        }
        catch (Throwable throwable) {
            throw BuildQueueImpl.a(throwable);
        }
        return result;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NotNull
    public List<QueuedBuildEx> addToQueue(@NotNull Map<BuildPromotionEx, SAgentRestrictor> map, @NotNull String string) {
        List<QueuedBuildEx> list;
        List<QueuedBuildEx> list2;
        try {
            if (map == null) {
                BuildQueueImpl.$$$reportNull$$$0(20);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        try {
            if (string == null) {
                BuildQueueImpl.$$$reportNull$$$0(21);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        Map<BuildPromotionEx, SAgentRestrictor> map2 = this.beforeAddToQueue(map, string);
        Disposable disposable = NamedThreadFactory.patchThreadName((String)("Adding " + map.size() + " new builds to the queue"));
        try {
            list2 = this.doAddToQueue(map2, string);
        }
        catch (Throwable throwable) {
            disposable.dispose();
            throw throwable;
        }
        try {
            disposable.dispose();
            list = list2;
            if (list == null) {
                BuildQueueImpl.$$$reportNull$$$0(22);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        return list;
    }

    @NotNull
    private List<QueuedBuildEx> doAddToQueue(@NotNull Map<BuildPromotionEx, SAgentRestrictor> map, @NotNull String string) {
        ArrayList<QueuedBuildEx> arrayList;
        try {
            if (map == null) {
                BuildQueueImpl.$$$reportNull$$$0(23);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        try {
            if (string == null) {
                BuildQueueImpl.$$$reportNull$$$0(24);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        Date date = this.myTimeService.nowDate();
        ArrayList<QueuedBuildEx> arrayList2 = new ArrayList<QueuedBuildEx>();
        ArrayList arrayList3 = new ArrayList();
        try {
            this.runAsSystem(() -> {
                this.myCleanupManager.executeWithInactiveCleanup(() -> this.lambda$doAddToQueue$5(map, string, date, arrayList2, arrayList3), true);
                this.notifyAddedToQueue(arrayList3);
            });
            arrayList = arrayList2;
            if (arrayList == null) {
                BuildQueueImpl.$$$reportNull$$$0(25);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        return arrayList;
    }

    @NotNull
    private static Set<BuildPromotionEx> findEndpoints(@NotNull Collection<BuildPromotionEx> collection) {
        HashSet<BuildPromotionEx> hashSet;
        try {
            if (collection == null) {
                BuildQueueImpl.$$$reportNull$$$0(26);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        HashSet<BuildPromotionEx> hashSet2 = new HashSet<BuildPromotionEx>();
        for (BuildPromotionEx buildPromotionEx2 : DependenciesUtil.findAllTopPromotions(collection)) {
            buildPromotionEx2.traverseSelfAndDependencies(buildPromotionEx -> {
                block7: {
                    try {
                        if (hashSet2.contains(buildPromotionEx)) {
                            return DependencyConsumer.Result.SKIP;
                        }
                    }
                    catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                        throw BuildQueueImpl.a(buildTypeNotFoundException);
                    }
                    try {
                        try {
                            if (buildPromotionEx.getQueuedDate() == null && buildPromotionEx.getAssociatedBuildId() == null) break block7;
                        }
                        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                            throw BuildQueueImpl.a(buildTypeNotFoundException);
                        }
                        return DependencyConsumer.Result.CONTINUE;
                    }
                    catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                        throw BuildQueueImpl.a(buildTypeNotFoundException);
                    }
                }
                hashSet2.add((BuildPromotionEx)buildPromotionEx);
                return DependencyConsumer.Result.SKIP;
            });
        }
        try {
            hashSet = hashSet2;
            if (hashSet == null) {
                BuildQueueImpl.$$$reportNull$$$0(27);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        return hashSet;
    }

    private boolean shouldAlwaysPersistOrderWhenNewBuildsAdded() {
        return TeamCityProperties.getBoolean((String)"teamcity.queue.addNewBuildsToQueue.alwaysPersistOrder");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     */
    private void changeOrderAndSendEvent(@Nullable ChangeOrderAction changeOrderAction, boolean bl) {
        block21: {
            int n;
            block20: {
                try {
                    if (changeOrderAction == null) {
                        this.myNodesSynchronizationTask.publishEvent();
                        this.fireQueueOrderChanged();
                        return;
                    }
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw BuildQueueImpl.a(buildTypeNotFoundException);
                }
                try {
                    if (bl) {
                        this.runAsSystem(() -> this.reloadQueueFromDB(1));
                    }
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw BuildQueueImpl.a(buildTypeNotFoundException);
                }
                boolean bl2 = false;
                n = TeamCityProperties.getInteger((String)"teamcity.queue.attemptsToStoreOrder", (int)3);
                for (int i = 0; i < n; ++i) {
                    AtomicReference<List<QueuedBuildEx>> atomicReference = this.myQueue;
                    synchronized (atomicReference) {
                        block18: {
                            block19: {
                                if (!this.isQueueRestored()) {
                                    return;
                                }
                                bl2 = changeOrderAction.changeOrder();
                                if (bl2) break block18;
                                try {
                                    block22: {
                                        if (!bl) break block19;
                                        break block22;
                                        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                                            throw BuildQueueImpl.a(buildTypeNotFoundException);
                                        }
                                    }
                                    this.myNodesSynchronizationTask.publishEvent();
                                }
                                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                                    throw BuildQueueImpl.a(buildTypeNotFoundException);
                                }
                            }
                            return;
                        }
                        long l = this.persistOrder();
                        if (l > 0L) {
                            this.myOrderVersion.set(l);
                            this.myNodesSynchronizationTask.publishEvent();
                            break;
                        }
                    }
                    this.runAsSystem(() -> this.reloadQueueFromDB(1));
                }
                try {
                    this.myNodesSynchronizationTask.publishEvent();
                    if (!bl2) break block20;
                    this.fireQueueOrderChanged();
                    break block21;
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw BuildQueueImpl.a(buildTypeNotFoundException);
                }
            }
            LOG.info("Could not apply own order to the build queue after " + n + " attempts because of the constant changes of the queue order by other TeamCity nodes");
        }
    }

    private boolean persistCurrentQueueOrder() {
        boolean bl;
        long l = this.persistOrder();
        try {
            bl = l > 0L;
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        boolean bl2 = bl;
        try {
            if (bl2) {
                this.myOrderVersion.set(l);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        return bl2;
    }

    private boolean persistAddedAndRemovedItems(@NotNull List<QueuedBuildEx> list, @NotNull Collection<QueuedBuildEx> collection) {
        try {
            if (list == null) {
                BuildQueueImpl.$$$reportNull$$$0(28);
            }
        }
        catch (Throwable throwable) {
            throw BuildQueueImpl.a(throwable);
        }
        try {
            if (collection == null) {
                BuildQueueImpl.$$$reportNull$$$0(29);
            }
        }
        catch (Throwable throwable) {
            throw BuildQueueImpl.a(throwable);
        }
        try {
            if (!this.isQueueRestored()) {
                return false;
            }
        }
        catch (Throwable throwable) {
            throw BuildQueueImpl.a(throwable);
        }
        try {
            NamedThreadFactory.executeWithNewThreadName((String)("Persisting build queue: new items: " + list.size() + ", removed items: " + collection.size()), () -> this.runAsSystem(() -> {
                this.mySqlRunner.withDBNoResult(dBFunctions -> {
                    try {
                        if (!collection.isEmpty()) {
                            dBFunctions.executeBatchForLongs("delete from build_queue where build_state_id = ?", (Collection)collection.stream().map(queuedBuildEx -> queuedBuildEx.getBuildPromotion().getId()).collect(Collectors.toList()));
                        }
                    }
                    catch (DBException dBException) {
                        throw BuildQueueImpl.a(dBException);
                    }
                    try {
                        if (!list.isEmpty()) {
                            this.insertNewItems(dBFunctions, list);
                        }
                    }
                    catch (DBException dBException) {
                        throw BuildQueueImpl.a(dBException);
                    }
                });
                for (QueuedBuildEx queuedBuildEx : list) {
                    queuedBuildEx.markPersisted();
                }
            }));
        }
        catch (Throwable throwable) {
            Loggers.SERVER.error("Error persisting queue: " + String.valueOf(throwable), throwable);
        }
        return true;
    }

    @NotNull
    private Map<BuildPromotionEx, SAgentRestrictor> beforeAddToQueue(@NotNull Map<BuildPromotionEx, SAgentRestrictor> map, @NotNull String string) {
        Map map2;
        try {
            if (map == null) {
                BuildQueueImpl.$$$reportNull$$$0(30);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        try {
            if (string == null) {
                BuildQueueImpl.$$$reportNull$$$0(31);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        AtomicReference atomicReference = new AtomicReference();
        try {
            this.runAsSystem((Runnable)new /* Unavailable Anonymous Inner Class!! */);
            map2 = (Map)atomicReference.get();
            if (map2 == null) {
                BuildQueueImpl.$$$reportNull$$$0(32);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        return map2;
    }

    @NotNull
    public static List<String> getBuildIds(@NotNull List<SQueuedBuild> list) {
        List list2;
        try {
            if (list == null) {
                BuildQueueImpl.$$$reportNull$$$0(33);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        try {
            list2 = CollectionsUtil.convertCollection(list, QueuedBuild::getItemId);
            if (list2 == null) {
                BuildQueueImpl.$$$reportNull$$$0(34);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        return list2;
    }

    @NotNull
    private List<QueuedBuildEx> getItemsToAdd(@NotNull Map.Entry<QueuedBuildEx, List<QueuedBuildEx>> entry) {
        ArrayList<QueuedBuildEx> arrayList;
        try {
            if (entry == null) {
                BuildQueueImpl.$$$reportNull$$$0(35);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        ArrayList<QueuedBuildEx> arrayList2 = new ArrayList<QueuedBuildEx>((Collection)entry.getValue());
        try {
            arrayList2.add(entry.getKey());
            arrayList = arrayList2;
            if (arrayList == null) {
                BuildQueueImpl.$$$reportNull$$$0(36);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        return arrayList;
    }

    private void checkPersonalBuildsAlreadyAdded(@NotNull Collection<QueuedBuildEx> collection) {
        try {
            if (collection == null) {
                BuildQueueImpl.$$$reportNull$$$0(37);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        for (QueuedBuildEx queuedBuildEx : collection) {
            if (!queuedBuildEx.isPersonal()) continue;
            for (SQueuedBuild sQueuedBuild : this.myQueue.get()) {
                try {
                    if (sQueuedBuild.getBuildPromotion().getAssociatedBuildId() != null) {
                        continue;
                    }
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw BuildQueueImpl.a(buildTypeNotFoundException);
                }
                try {
                    try {
                        try {
                            if (!sQueuedBuild.isPersonal() || !sQueuedBuild.getBuildTypeId().equals(queuedBuildEx.getBuildTypeId())) continue;
                        }
                        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                            throw BuildQueueImpl.a(buildTypeNotFoundException);
                        }
                        if (!IntersectingPersonalChainsPreprocessor.isPersonalPromotionsEquals((BuildPromotion)sQueuedBuild.getBuildPromotion(), (BuildPromotion)queuedBuildEx.getBuildPromotion())) continue;
                    }
                    catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                        throw BuildQueueImpl.a(buildTypeNotFoundException);
                    }
                    Loggers.SERVER.warn("Cannot add a personal build: " + LogUtil.describe((Loggable)queuedBuildEx) + " to the queue, because another personal build for the same change list and in the same build configuration is already in the queue: " + LogUtil.describe((Object)sQueuedBuild));
                    throw new DuplicatePersonalBuildException("Attempt to add personal build in queue while there exists another personal build for the same build configuration and change list");
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw BuildQueueImpl.a(buildTypeNotFoundException);
                }
            }
        }
    }

    @NotNull
    private Collection<BuildPromotionEx> collectChildrenNonQueuedBuilds(@NotNull BuildPromotionEx buildPromotionEx) {
        LinkedHashSet<BuildPromotionEx> linkedHashSet;
        try {
            if (buildPromotionEx == null) {
                BuildQueueImpl.$$$reportNull$$$0(38);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        LinkedHashSet<BuildPromotionEx> linkedHashSet2 = new LinkedHashSet<BuildPromotionEx>();
        try {
            buildPromotionEx.getDependencyGraph().traverseBottomUp(buildPromotion -> {
                try {
                    if (buildPromotion.getId() == buildPromotionEx.getId()) {
                        return false;
                    }
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw BuildQueueImpl.a(buildTypeNotFoundException);
                }
                try {
                    if (buildPromotion.getQueuedDate() != null) {
                        return true;
                    }
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw BuildQueueImpl.a(buildTypeNotFoundException);
                }
                try {
                    if (buildPromotion.getAssociatedBuildId() != null) {
                        return true;
                    }
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw BuildQueueImpl.a(buildTypeNotFoundException);
                }
                linkedHashSet2.add((BuildPromotionEx)buildPromotion);
                return true;
            });
            linkedHashSet = linkedHashSet2;
            if (linkedHashSet == null) {
                BuildQueueImpl.$$$reportNull$$$0(39);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        return linkedHashSet;
    }

    @NotNull
    private QueuedBuildEx createQueuedBuild(@NotNull BuildPromotionEx buildPromotionEx, @Nullable SAgentRestrictor sAgentRestrictor, @NotNull String string, @NotNull Date date) {
        QueuedBuildEx queuedBuildEx;
        try {
            if (buildPromotionEx == null) {
                BuildQueueImpl.$$$reportNull$$$0(40);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        try {
            if (string == null) {
                BuildQueueImpl.$$$reportNull$$$0(41);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        try {
            if (date == null) {
                BuildQueueImpl.$$$reportNull$$$0(42);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        try {
            queuedBuildEx = this.myQueuedBuildFactory.createQueuedBuild((BuildPromotion)buildPromotionEx, sAgentRestrictor, string, date);
            if (queuedBuildEx == null) {
                BuildQueueImpl.$$$reportNull$$$0(43);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        return queuedBuildEx;
    }

    private void runAsSystem(@NotNull Runnable runnable) {
        try {
            if (runnable == null) {
                BuildQueueImpl.$$$reportNull$$$0(44);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        this.mySecurityContext.runAsSystemUnchecked(runnable::run);
    }

    private void postProcessBuildsRemovedByOptimizer(@NotNull Collection<QueuedBuildEx> collection, String string) {
        try {
            if (collection == null) {
                BuildQueueImpl.$$$reportNull$$$0(45);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        RedundantDequeuedBuildHandler redundantDequeuedBuildHandler = new RedundantDequeuedBuildHandler(this, new Date(), null, string);
        for (QueuedBuildEx queuedBuildEx : collection) {
            redundantDequeuedBuildHandler.onRemoveFromQueue(queuedBuildEx);
        }
        try {
            if (collection.size() > 0) {
                this.fireQueueOrderChanged();
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
    }

    private String getDependentBuildsTriggeredBy(@NotNull String string, BuildPromotion buildPromotion) {
        try {
            if (string == null) {
                BuildQueueImpl.$$$reportNull$$$0(46);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        SBuildType sBuildType = buildPromotion.getParentBuildType();
        try {
            if (sBuildType == null) {
                return string;
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        Map map = TriggeredByParser.extractParameters((String)string);
        TriggeredByBuilder triggeredByBuilder = new TriggeredByBuilder();
        triggeredByBuilder.addParameters(map);
        triggeredByBuilder.addParameter("buildTypeId", sBuildType.getBuildTypeId());
        triggeredByBuilder.addParameter("buildId", String.valueOf(buildPromotion.getId()));
        triggeredByBuilder.addParameter("type", "snapshotDependency");
        return triggeredByBuilder.toString();
    }

    @Nullable
    public QueuedBuildEx findQueued(@NotNull String string, Integer n) {
        QueuedBuildEx queuedBuildEx;
        SingleAgentRestrictorInfo singleAgentRestrictorInfo;
        String string2;
        BuildQueueImpl buildQueueImpl;
        try {
            if (string == null) {
                BuildQueueImpl.$$$reportNull$$$0(47);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        try {
            buildQueueImpl = this;
            string2 = string;
            singleAgentRestrictorInfo = n == null ? null : new SingleAgentRestrictorInfo(n.intValue());
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        List<QueuedBuildEx> list = buildQueueImpl.getItems(string2, (AgentRestrictor)singleAgentRestrictorInfo);
        try {
            queuedBuildEx = list.isEmpty() ? null : list.get(0);
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        return queuedBuildEx;
    }

    private List<QueuedBuildEx> getItems(@NotNull String string, @Nullable AgentRestrictor agentRestrictor) {
        try {
            if (string == null) {
                BuildQueueImpl.$$$reportNull$$$0(48);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        List<SQueuedBuild> list = this.getItems(string);
        List list2 = (List)FilterUtil.filterCollection(list, sQueuedBuild -> BuildQueueImpl.areTheSame(agentRestrictor, sQueuedBuild.getAgentRestrictor()));
        return CollectionsUtil.convertCollection((Collection)list2, sQueuedBuild -> (QueuedBuildEx)sQueuedBuild);
    }

    public boolean isInQueue(@NotNull String string) {
        try {
            if (string == null) {
                BuildQueueImpl.$$$reportNull$$$0(49);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        boolean[] blArray = new boolean[1];
        this.processBuildTypeItems(string, (ItemProcessor<QueuedBuildEx>)((ItemProcessor)queuedBuildEx -> {
            blArray[0] = true;
            return false;
        }));
        return blArray[0];
    }

    @Nullable
    public QueuedBuildEx findQueued(@NotNull String string) {
        try {
            if (string == null) {
                BuildQueueImpl.$$$reportNull$$$0(50);
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw BuildQueueImpl.a(numberFormatException);
        }
        try {
            return this.getIfNotStarted(Long.parseLong(string));
        }
        catch (NumberFormatException numberFormatException) {
            Loggers.SERVER.debug("Invalid queue item id: " + string);
            return null;
        }
    }

    public boolean isQueueEmpty() {
        return this.myQueue.get().isEmpty();
    }

    @Nullable
    public SQueuedBuild getFirst() {
        SQueuedBuild sQueuedBuild;
        List<QueuedBuildEx> list = this.getQueuedBuildsCopy();
        try {
            sQueuedBuild = list.isEmpty() ? null : (SQueuedBuild)list.get(0);
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        return sQueuedBuild;
    }

    @Nullable
    public SQueuedBuild getLast() {
        SQueuedBuild sQueuedBuild;
        List<QueuedBuildEx> list = this.getQueuedBuildsCopy();
        try {
            sQueuedBuild = list.isEmpty() ? null : (SQueuedBuild)list.get(list.size() - 1);
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        return sQueuedBuild;
    }

    public void removeItems(@NotNull Collection<String> collection, @Nullable SUser sUser, @Nullable String string) {
        try {
            if (collection == null) {
                BuildQueueImpl.$$$reportNull$$$0(51);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        this.removeMatchedItems((Filter<SQueuedBuild>)((Filter)sQueuedBuild -> collection.contains(sQueuedBuild.getItemId())), sUser, string);
    }

    public void removeMatchedItems(@NotNull Filter<SQueuedBuild> filter, @Nullable SUser sUser, @Nullable String string) {
        try {
            if (filter == null) {
                BuildQueueImpl.$$$reportNull$$$0(52);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        this.removeQueuedBuilds(this.filterItems(filter), sUser, string);
    }

    public void cancelMatchedItems(@NotNull Filter<SQueuedBuild> filter, @Nullable SUser sUser, @Nullable String string) {
        try {
            if (filter == null) {
                BuildQueueImpl.$$$reportNull$$$0(53);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        CanceledDequeuedBuildHandler canceledDequeuedBuildHandler = new CanceledDequeuedBuildHandler(this, this.myTimeService.nowDate(), sUser, string);
        this.removeQueuedBuilds(this.filterItems(filter), (DequeuedBuildHandler)canceledDequeuedBuildHandler, (Predicate<QueuedBuildEx>)((Predicate)queuedBuildEx -> {
            boolean bl;
            block5: {
                block4: {
                    try {
                        try {
                            if (!queuedBuildEx.tryLockBuildStarting() && !queuedBuildEx.isStarted()) break block4;
                        }
                        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                            throw BuildQueueImpl.a(buildTypeNotFoundException);
                        }
                        bl = true;
                        break block5;
                    }
                    catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                        throw BuildQueueImpl.a(buildTypeNotFoundException);
                    }
                }
                bl = false;
            }
            return bl;
        }));
    }

    public void removeQueuedBuilds(@NotNull Collection<SQueuedBuild> collection, @Nullable SUser sUser, @Nullable String string) {
        try {
            if (collection == null) {
                BuildQueueImpl.$$$reportNull$$$0(54);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        RemovedDequeuedBuildHandler removedDequeuedBuildHandler = new RemovedDequeuedBuildHandler(this, this.myTimeService.nowDate(), sUser, string);
        this.removeQueuedBuilds(collection, (DequeuedBuildHandler)removedDequeuedBuildHandler, (Predicate<QueuedBuildEx>)((Predicate)queuedBuildEx -> {
            boolean bl;
            block5: {
                block4: {
                    try {
                        try {
                            if (!queuedBuildEx.tryLockBuildStarting() && !queuedBuildEx.isStarted()) break block4;
                        }
                        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                            throw BuildQueueImpl.a(buildTypeNotFoundException);
                        }
                        bl = true;
                        break block5;
                    }
                    catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                        throw BuildQueueImpl.a(buildTypeNotFoundException);
                    }
                }
                bl = false;
            }
            return bl;
        }));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Unable to fully structure code
     */
    public void terminateQueuedBuild(@NotNull SQueuedBuild var1_1, @Nullable SUser var2_2, boolean var3_3, @Nullable String var4_4, @NotNull BuildProblemData var5_5) {
        block21: {
            try {
                if (var1_1 == null) {
                    BuildQueueImpl.$$$reportNull$$$0(55);
                }
            }
            catch (BuildTypeNotFoundException v0) {
                throw BuildQueueImpl.a(v0);
            }
            try {
                if (var5_5 == null) {
                    BuildQueueImpl.$$$reportNull$$$0(56);
                }
            }
            catch (BuildTypeNotFoundException v1) {
                throw BuildQueueImpl.a(v1);
            }
            var6_6 = var5_5.getType();
            if (var3_3) break block21;
            try {
                block25: {
                    if (ErrorData.isInternalError((String)var6_6)) break block21;
                    break block25;
                    catch (BuildTypeNotFoundException v2) {
                        throw BuildQueueImpl.a(v2);
                    }
                }
                throw new IllegalArgumentException("Build problem data with type of internal error is required for queued build termination in case build is not canceled. Actual type is: " + var6_6 + ".");
            }
            catch (BuildTypeNotFoundException v3) {
                throw BuildQueueImpl.a(v3);
            }
        }
        try {
            block23: {
                block22: {
                    block27: {
                        block26: {
                            var7_7 = (java.util.function.Predicate<BuildProblemData>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)Z, lambda$terminateQueuedBuild$21(jetbrains.buildServer.BuildProblemData ), (Ljetbrains/buildServer/BuildProblemData;)Z)();
                            if (!var3_3) break block22;
                            if (var2_2 != null) break block22;
                            break block26;
                            catch (BuildTypeNotFoundException v4) {
                                throw BuildQueueImpl.a(v4);
                            }
                        }
                        if (var7_7.test(var5_5)) ** GOTO lbl47
                        break block27;
                        catch (BuildTypeNotFoundException v5) {
                            throw BuildQueueImpl.a(v5);
                        }
                    }
                    try {
                        block28: {
                            if (((BuildPromotionEx)var1_1.getBuildPromotion()).findSelfOrDependencyWithProblem(var7_7) == null) break block22;
                            break block28;
                            catch (BuildTypeNotFoundException v6) {
                                throw BuildQueueImpl.a(v6);
                            }
                        }
                        v7 = true;
                        break block23;
                    }
                    catch (BuildTypeNotFoundException v8) {
                        throw BuildQueueImpl.a(v8);
                    }
                }
                v7 = false;
            }
            var8_8 = v7;
            var9_9 = new /* Unavailable Anonymous Inner Class!! */;
            this.removeQueuedBuilds(Collections.singleton(var1_1), (DequeuedBuildHandler)var9_9, (Predicate<QueuedBuildEx>)(Predicate)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)Z, lambda$terminateQueuedBuild$22(jetbrains.buildServer.serverSide.QueuedBuildEx ), (Ljetbrains/buildServer/serverSide/QueuedBuildEx;)Z)());
        }
        finally {
            ((QueuedBuildEx)var1_1).unlockBuildStarting();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NotNull
    private Set<SQueuedBuild> filterItems(@NotNull Filter<SQueuedBuild> filter) {
        HashSet<SQueuedBuild> hashSet;
        try {
            if (filter == null) {
                BuildQueueImpl.$$$reportNull$$$0(57);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        Filter filter2 = sQueuedBuild -> {
            try {
                return filter.accept(sQueuedBuild);
            }
            catch (Exception exception) {
                Loggers.SERVER.warnAndDebugDetails("Exception in filter while searching for a queued build to remove, queued build: " + jetbrains.buildServer.log.LogUtil.describe((Object)sQueuedBuild), (Throwable)exception);
                return false;
            }
        };
        HashSet<SQueuedBuild> hashSet2 = new HashSet<SQueuedBuild>();
        AtomicReference<List<QueuedBuildEx>> atomicReference = this.myQueue;
        synchronized (atomicReference) {
            for (QueuedBuildEx queuedBuildEx : this.myQueue.get()) {
                try {
                    if (!filter2.accept((Object)queuedBuildEx)) continue;
                    hashSet2.add((SQueuedBuild)queuedBuildEx);
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw BuildQueueImpl.a(buildTypeNotFoundException);
                }
            }
        }
        try {
            hashSet = hashSet2;
            if (hashSet == null) {
                BuildQueueImpl.$$$reportNull$$$0(58);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        return hashSet;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     */
    private void removeQueuedBuilds(@NotNull Collection<SQueuedBuild> collection, @NotNull DequeuedBuildHandler dequeuedBuildHandler, Predicate<QueuedBuildEx> predicate) {
        try {
            if (collection == null) {
                BuildQueueImpl.$$$reportNull$$$0(59);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        try {
            if (dequeuedBuildHandler == null) {
                BuildQueueImpl.$$$reportNull$$$0(60);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        try {
            if (collection.isEmpty()) {
                return;
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        HashSet<QueuedBuildEx> hashSet = new HashSet<QueuedBuildEx>();
        AtomicReference<List<QueuedBuildEx>> atomicReference = this.myQueue;
        synchronized (atomicReference) {
            block23: {
                for (SQueuedBuild sQueuedBuild : collection) {
                    QueuedBuildEx queuedBuildEx = (QueuedBuildEx)sQueuedBuild;
                    BuildPromotionEx buildPromotionEx = (BuildPromotionEx)sQueuedBuild.getBuildPromotion();
                    try {
                        if (predicate.apply((Object)queuedBuildEx)) {
                            hashSet.add(queuedBuildEx);
                        }
                    }
                    catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                        throw BuildQueueImpl.a(buildTypeNotFoundException);
                    }
                    try {
                        if (!dequeuedBuildHandler.shouldRemoveDependencies(queuedBuildEx)) continue;
                        this.collectQueuedBuildsToRemove(hashSet, buildPromotionEx);
                    }
                    catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                        throw BuildQueueImpl.a(buildTypeNotFoundException);
                    }
                }
                this.removeItems(hashSet);
                if (hashSet.size() <= 0) break block23;
                try {
                    block24: {
                        if (!this.persistAddedAndRemovedItems(Collections.emptyList(), hashSet)) break block23;
                        break block24;
                        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                            throw BuildQueueImpl.a(buildTypeNotFoundException);
                        }
                    }
                    this.myNodesSynchronizationTask.publishEvent();
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw BuildQueueImpl.a(buildTypeNotFoundException);
                }
            }
        }
        for (QueuedBuildEx queuedBuildEx : hashSet) {
            dequeuedBuildHandler.onRemoveFromQueue(queuedBuildEx);
        }
    }

    private void collectQueuedBuildsToRemove(@NotNull Set<QueuedBuildEx> set, @NotNull BuildPromotionEx buildPromotionEx) {
        try {
            if (set == null) {
                BuildQueueImpl.$$$reportNull$$$0(61);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        try {
            if (buildPromotionEx == null) {
                BuildQueueImpl.$$$reportNull$$$0(62);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        for (BuildPromotionEx buildPromotionEx2 : this.getSafeToRemoveDirectDependencies(buildPromotionEx)) {
            QueuedBuildEx queuedBuildEx = buildPromotionEx2.getQueuedBuild();
            try {
                try {
                    if (queuedBuildEx == null || !queuedBuildEx.tryLockBuildStarting()) continue;
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw BuildQueueImpl.a(buildTypeNotFoundException);
                }
                set.add(queuedBuildEx);
                this.collectQueuedBuildsToRemove(set, buildPromotionEx2);
            }
            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                throw BuildQueueImpl.a(buildTypeNotFoundException);
            }
        }
    }

    @NotNull
    private Collection<BuildPromotionEx> getSafeToRemoveDirectDependencies(@NotNull BuildPromotionEx buildPromotionEx) {
        Collection collection;
        try {
            if (buildPromotionEx == null) {
                BuildQueueImpl.$$$reportNull$$$0(63);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        try {
            collection = new DependentBuildsStopper(buildPromotionEx, (ExecutorService)this.myExecutorServices.getNormalExecutorService()).getSafeToRemoveDirectDependencies();
            if (collection == null) {
                BuildQueueImpl.$$$reportNull$$$0(64);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        return collection;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void storeItems(@NotNull List<QueuedBuildEx> list) {
        try {
            if (list == null) {
                BuildQueueImpl.$$$reportNull$$$0(65);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        AtomicReference<List<QueuedBuildEx>> atomicReference = this.myQueue;
        synchronized (atomicReference) {
            List<QueuedBuildEx> list2 = this.getQueuedBuildsCopy();
            for (QueuedBuildEx queuedBuildEx : list) {
                QueuedBuildEx queuedBuildEx2 = this.myQueueIndex.get().put(queuedBuildEx.getBuildPromotion().getId(), queuedBuildEx);
                try {
                    if (queuedBuildEx2 != null) {
                        list2.remove(queuedBuildEx2);
                        this.logDuplicateBuild(queuedBuildEx2, queuedBuildEx);
                        continue;
                    }
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw BuildQueueImpl.a(buildTypeNotFoundException);
                }
                String string = queuedBuildEx.getBuildPromotion().getParentBuildTypeId();
                List<QueuedBuildEx> list3 = this.myQueueBuildTypeIndex.get().get(string);
                list3 = list3 == null ? new ArrayList<QueuedBuildEx>() : new ArrayList<QueuedBuildEx>(list3);
                list3.add(queuedBuildEx);
                this.myQueueBuildTypeIndex.get().put(string, list3);
            }
            list2.addAll(list);
            this.myQueue.set(Collections.unmodifiableList(list2));
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void removeItems(@NotNull Collection<QueuedBuildEx> collection) {
        try {
            if (collection == null) {
                BuildQueueImpl.$$$reportNull$$$0(66);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        AtomicReference<List<QueuedBuildEx>> atomicReference = this.myQueue;
        synchronized (atomicReference) {
            HashMap<String, List<QueuedBuildEx>> hashMap = new HashMap<String, List<QueuedBuildEx>>();
            for (QueuedBuildEx object : collection) {
                this.myQueueIndex.get().remove(object.getBuildPromotion().getId());
                hashMap.computeIfAbsent(object.getBuildPromotion().getParentBuildTypeId(), string -> new ArrayList());
            }
            ArrayList arrayList = new ArrayList();
            for (QueuedBuildEx queuedBuildEx : this.myQueue.get()) {
                if (collection.contains(queuedBuildEx)) continue;
                arrayList.add(queuedBuildEx);
                String string2 = queuedBuildEx.getBuildPromotion().getParentBuildTypeId();
                try {
                    if (!hashMap.containsKey(string2)) continue;
                    ((List)hashMap.get(string2)).add(queuedBuildEx);
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw BuildQueueImpl.a(buildTypeNotFoundException);
                }
            }
            this.updateBuildTypeIndex(hashMap);
            this.myQueue.set(Collections.unmodifiableList(arrayList));
        }
    }

    private void updateBuildTypeIndex(@NotNull Map<String, List<QueuedBuildEx>> map) {
        try {
            if (map == null) {
                BuildQueueImpl.$$$reportNull$$$0(67);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        map.forEach((string, list) -> {
            block3: {
                block2: {
                    List list2 = (List)map.get(string);
                    try {
                        if (list2.isEmpty()) break block2;
                        this.myQueueBuildTypeIndex.get().put((String)string, list2);
                        break block3;
                    }
                    catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                        throw BuildQueueImpl.a(buildTypeNotFoundException);
                    }
                }
                this.myQueueBuildTypeIndex.get().remove(string);
            }
        });
    }

    public void removeAllFromQueue(String string, SUser sUser, String string2) {
        this.removeMatchedItems((Filter<SQueuedBuild>)((Filter)sQueuedBuild -> {
            boolean bl;
            block5: {
                block4: {
                    try {
                        try {
                            if (!string.equals(sQueuedBuild.getBuildPromotion().getParentBuildTypeId()) && !string.equals(sQueuedBuild.getBuildTypeId())) break block4;
                        }
                        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                            throw BuildQueueImpl.a(buildTypeNotFoundException);
                        }
                        bl = true;
                        break block5;
                    }
                    catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                        throw BuildQueueImpl.a(buildTypeNotFoundException);
                    }
                }
                bl = false;
            }
            return bl;
        }), sUser, string2);
    }

    public int getOrderNumber(@NotNull SQueuedBuild sQueuedBuild) {
        try {
            if (sQueuedBuild == null) {
                BuildQueueImpl.$$$reportNull$$$0(68);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        return this.myQueue.get().indexOf(sQueuedBuild) + 1;
    }

    public List<SQueuedBuild> findPersonalBuildsByPersonalChangeListId(long l) {
        ArrayList<SQueuedBuild> arrayList = new ArrayList<SQueuedBuild>();
        for (QueuedBuildEx queuedBuildEx : this.myQueue.get()) {
            try {
                if (!queuedBuildEx.isPersonal()) {
                    continue;
                }
            }
            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                throw BuildQueueImpl.a(buildTypeNotFoundException);
            }
            RemoteBuildType remoteBuildType = (RemoteBuildType)queuedBuildEx.getBuildType();
            try {
                if (l != remoteBuildType.getChangesId()) continue;
                arrayList.add((SQueuedBuild)queuedBuildEx);
            }
            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                throw BuildQueueImpl.a(buildTypeNotFoundException);
            }
        }
        return arrayList;
    }

    public void removeAllFromQueue(String string) {
        this.removeAllFromQueue(string, null, null);
    }

    public void removeAllFromQueue() {
        this.removeMatchedItems((Filter<SQueuedBuild>)((Filter)sQueuedBuild -> true), null, null);
    }

    public void applyOrder(@NotNull String[] stringArray) {
        block6: {
            String string;
            block5: {
                try {
                    if (stringArray == null) {
                        BuildQueueImpl.$$$reportNull$$$0(69);
                    }
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw BuildQueueImpl.a(buildTypeNotFoundException);
                }
                List<String> list = BuildQueueImpl.getBuildIds(this.getItems());
                this.applyOrderAndSendEvent(stringArray);
                User user = this.mySecurityContext.getAuthorityHolder().getAssociatedUser();
                List<String> list2 = BuildQueueImpl.getBuildIds(this.getItems());
                string = ", old id order: " + this.collectionToString(list, 100) + ", new id order: " + this.collectionToString(list2, 100);
                try {
                    if (user == null) break block5;
                    Loggers.ACTIVITIES.info("Build queue order changed by " + LogUtil.describe((Loggable)user) + string);
                    break block6;
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw BuildQueueImpl.a(buildTypeNotFoundException);
                }
            }
            Loggers.SERVER.debug("Build queue order changed by system" + string);
        }
    }

    private void applyOrderAndSendEvent(@NotNull String[] stringArray) {
        try {
            if (stringArray == null) {
                BuildQueueImpl.$$$reportNull$$$0(70);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        ApplyOrder applyOrder = new ApplyOrder(this, stringArray);
        this.changeOrderAndSendEvent((ChangeOrderAction)applyOrder, false);
    }

    @NotNull
    private String collectionToString(@NotNull List<String> list, int n) {
        String string;
        Object object;
        String string2;
        try {
            if (list == null) {
                BuildQueueImpl.$$$reportNull$$$0(71);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        List<String> list2 = list.subList(0, Math.min(n, list.size()));
        try {
            string2 = String.valueOf(list2);
            object = list2.size() < list.size() ? " ... and " + (list.size() - list2.size()) + " more" : "";
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        try {
            string = string2 + (String)object;
            if (string == null) {
                BuildQueueImpl.$$$reportNull$$$0(72);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        return string;
    }

    private void doApplyOrder(@NotNull String[] stringArray, boolean bl) {
        try {
            if (stringArray == null) {
                BuildQueueImpl.$$$reportNull$$$0(73);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        List<QueuedBuildEx> list = this.reorderQueue(stringArray, bl);
        this.setQueuedBuilds(list);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @VisibleForTesting
    void setQueuedBuilds(@NotNull List<QueuedBuildEx> list) {
        try {
            if (list == null) {
                BuildQueueImpl.$$$reportNull$$$0(74);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        AtomicReference<List<QueuedBuildEx>> atomicReference = this.myQueue;
        synchronized (atomicReference) {
            ConcurrentHashMap<String, List> concurrentHashMap = new ConcurrentHashMap<String, List>();
            ConcurrentHashMap<Long, QueuedBuildEx> concurrentHashMap2 = new ConcurrentHashMap<Long, QueuedBuildEx>();
            ArrayList<QueuedBuildEx> arrayList = new ArrayList<QueuedBuildEx>();
            for (QueuedBuildEx queuedBuildEx : list) {
                QueuedBuildEx queuedBuildEx2 = concurrentHashMap2.putIfAbsent(queuedBuildEx.getBuildPromotion().getId(), queuedBuildEx);
                try {
                    if (queuedBuildEx2 != null) {
                        this.logDuplicateBuild(queuedBuildEx2, queuedBuildEx);
                        continue;
                    }
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw BuildQueueImpl.a(buildTypeNotFoundException);
                }
                concurrentHashMap.computeIfAbsent(queuedBuildEx.getBuildPromotion().getParentBuildTypeId(), string -> new ArrayList()).add(queuedBuildEx);
                arrayList.add(queuedBuildEx);
            }
            this.myQueueIndex.set(concurrentHashMap2);
            this.myQueueBuildTypeIndex.set(concurrentHashMap);
            this.myQueue.set(Collections.unmodifiableList(arrayList));
        }
    }

    private void logDuplicateBuild(QueuedBuildEx queuedBuildEx, QueuedBuildEx queuedBuildEx2) {
        boolean bl;
        String string;
        String string2;
        try {
            string2 = LogUtil.describeInDetail((SQueuedBuild)queuedBuildEx);
            string = LogUtil.describeInDetail((SQueuedBuild)queuedBuildEx2);
            bl = queuedBuildEx.getBuildPromotion() == queuedBuildEx2.getBuildPromotion();
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        String string3 = "Attempted to add duplicate queued build in queue, previous queued build: " + string2 + ", new build: " + string + ", same promotions: " + bl + ", prev promotion is used: " + queuedBuildEx.getBuildPromotion().isUsed();
        Loggers.SERVER.warnAndDebugDetails(string3, (Throwable)new Exception());
    }

    @NotNull
    private List<QueuedBuildEx> reorderQueue(@NotNull String[] stringArray, boolean bl) {
        List list;
        HashMap<String, QueuedBuildEx> hashMap;
        List<QueuedBuildEx> list2;
        List<Object> list3;
        block29: {
            List list4;
            block30: {
                List<Object> list5;
                try {
                    if (stringArray == null) {
                        BuildQueueImpl.$$$reportNull$$$0(75);
                    }
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw BuildQueueImpl.a(buildTypeNotFoundException);
                }
                try {
                    list5 = bl ? this.createDependenciesEdgesForQueuedBuilds() : Collections.emptyList();
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw BuildQueueImpl.a(buildTypeNotFoundException);
                }
                list3 = list5;
                list2 = this.myQueue.get();
                hashMap = new HashMap<String, QueuedBuildEx>();
                for (QueuedBuildEx queuedBuildEx : list2) {
                    hashMap.put(queuedBuildEx.getItemId(), queuedBuildEx);
                }
                try {
                    try {
                        if (!list3.isEmpty()) break block29;
                        list4 = CollectionsUtil.applyOrder((Object[])stringArray, list2, hashMap);
                        if (list4 != null) break block30;
                    }
                    catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                        throw BuildQueueImpl.a(buildTypeNotFoundException);
                    }
                    BuildQueueImpl.$$$reportNull$$$0(76);
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw BuildQueueImpl.a(buildTypeNotFoundException);
                }
            }
            return list4;
        }
        HashMap hashMap2 = new HashMap();
        try {
            for (int i = 0; i < stringArray.length; ++i) {
                hashMap2.put(stringArray[i], i * 1000);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        Map map = CollectionsUtil.groupBy(list3, Pair::getSecond);
        boolean bl2 = true;
        while (bl2) {
            bl2 = false;
            for (List list6 : map.values()) {
                int n = 0;
                for (Pair pair : list6) {
                    int n2 = ++n;
                    Integer n3 = (Integer)hashMap2.get(pair.first);
                    Integer n4 = (Integer)hashMap2.get(pair.second);
                    try {
                        try {
                            if (n3 == null && n4 == null) {
                                continue;
                            }
                        }
                        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                            throw BuildQueueImpl.a(buildTypeNotFoundException);
                        }
                    }
                    catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                        throw BuildQueueImpl.a(buildTypeNotFoundException);
                    }
                    if (n3 == null) {
                        n3 = n4 - n2;
                        hashMap2.put((String)pair.first, n3);
                        bl2 = true;
                        continue;
                    }
                    if (n4 == null) {
                        n4 = n3 + n2;
                        hashMap2.put((String)pair.second, n4);
                        bl2 = true;
                        continue;
                    }
                    try {
                        if (n3 < n4) {
                            continue;
                        }
                    }
                    catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                        throw BuildQueueImpl.a(buildTypeNotFoundException);
                    }
                    n3 = n4 - n2;
                    hashMap2.put((String)pair.first, n3);
                    bl2 = true;
                }
            }
        }
        ArrayList arrayList = new ArrayList(hashMap2.keySet());
        try {
            arrayList.sort((string, string2) -> {
                block4: {
                    int n;
                    block6: {
                        block5: {
                            float f = (Integer)hashMap2.get(string) - (Integer)hashMap2.get(string2);
                            try {
                                try {
                                    if (f == 0.0f) break block4;
                                    if (!(f > 0.0f)) break block5;
                                }
                                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                                    throw BuildQueueImpl.a(buildTypeNotFoundException);
                                }
                                n = 1;
                                break block6;
                            }
                            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                                throw BuildQueueImpl.a(buildTypeNotFoundException);
                            }
                        }
                        n = -1;
                    }
                    return n;
                }
                return string.compareTo((String)string2);
            });
            list = CollectionsUtil.applyOrder((Object[])arrayList.toArray(new String[0]), list2, hashMap);
            if (list == null) {
                BuildQueueImpl.$$$reportNull$$$0(77);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        return list;
    }

    @NotNull
    private List<Pair<String, String>> createDependenciesEdgesForQueuedBuilds() {
        ArrayList<Pair<String, String>> arrayList;
        List<QueuedBuildEx> list = this.myQueue.get();
        Map<Long, QueuedBuildEx> map = this.myQueueIndex.get();
        HashMap<Long, List> hashMap = new HashMap<Long, List>();
        ArrayList<Pair<String, String>> arrayList2 = new ArrayList<Pair<String, String>>();
        for (QueuedBuildEx queuedBuildEx : list) {
            long[] lArray = queuedBuildEx.getBuildPromotion().getDependenciesIds();
            int n = lArray.length;
            for (int i = 0; i < n; ++i) {
                Long l2 = lArray[i];
                QueuedBuildEx queuedBuildEx2 = map.get(l2);
                try {
                    if (queuedBuildEx2 != null) {
                        arrayList2.add((Pair<String, String>)new Pair((Object)queuedBuildEx2.getItemId(), (Object)queuedBuildEx.getItemId()));
                        continue;
                    }
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw BuildQueueImpl.a(buildTypeNotFoundException);
                }
                arrayList2.add((Pair<String, String>)new Pair((Object)String.valueOf(l2), (Object)queuedBuildEx.getItemId()));
                hashMap.computeIfAbsent(l2, l -> new ArrayList()).add(arrayList2.size() - 1);
            }
        }
        if (!hashMap.isEmpty()) {
            for (QueuedBuildEx queuedBuildEx : this.myBuildPromotionManager.findPromotionsByIds(hashMap.keySet(), new BuildPromotionManager.BuildPromotionInitFromDbHint[0])) {
                ((BuildPromotionEx)queuedBuildEx).traverseDependencies(arg_0 -> BuildQueueImpl.lambda$createDependenciesEdgesForQueuedBuilds$31(map, hashMap, (BuildPromotion)queuedBuildEx, arrayList2, arg_0));
            }
        }
        try {
            arrayList = arrayList2;
            if (arrayList == null) {
                BuildQueueImpl.$$$reportNull$$$0(78);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        return arrayList;
    }

    public void moveTop(String string) {
        this.changeOrderAndSendEvent((ChangeOrderAction)new MoveToTop(this, string), false);
    }

    public void moveBottom(String string) {
        this.changeOrderAndSendEvent((ChangeOrderAction)new MoveBottom(this, string), false);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void resetQueue() {
        AtomicReference<List<QueuedBuildEx>> atomicReference = this.myQueue;
        synchronized (atomicReference) {
            this.myQueueIndex.set(new ConcurrentHashMap());
            this.myQueueBuildTypeIndex.set(new ConcurrentHashMap());
            this.myQueue.set(Collections.emptyList());
        }
        this.myQueueRestored = false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Unable to fully structure code
     */
    private boolean reloadQueueFromDB(int var1_1) {
        Loggers.SERVER.debug("Start reloading build queue from the database");
        for (var2_2 = 0; var2_2 < var1_1; ++var2_2) {
            var3_3 = NamedThreadFactory.patchThreadName((String)("Reloading the build queue from the database, attempt num: " + (var2_2 + 1)));
            try {
                block13: {
                    block12: {
                        var4_4 = new ArrayList<QueuedBuildEx>();
                        var5_5 = new ArrayList<QueuedBuildEx>();
                        var6_6 = new AtomicBoolean(false);
                        if (!this.tryToSubstituteQueueFromDB(var4_4, var5_5, var6_6)) continue;
                        try {
                            block14: {
                                ServerLoadMetrics.BUILD_QUEUE_RATE.increment((double)var4_4.size());
                                var4_4.forEach((Consumer<QueuedBuildEx>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)V, fireBuildAddedToQueue(jetbrains.buildServer.serverSide.SQueuedBuild ), (Ljetbrains/buildServer/serverSide/QueuedBuildEx;)V)((BuildQueueImpl)this));
                                var5_5.forEach((Consumer<QueuedBuildEx>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)V, lambda$reloadQueueFromDB$32(jetbrains.buildServer.serverSide.QueuedBuildEx ), (Ljetbrains/buildServer/serverSide/QueuedBuildEx;)V)((BuildQueueImpl)this));
                                if (!var6_6.get()) break block12;
                                break block14;
                                catch (BuildTypeNotFoundException v0) {
                                    throw BuildQueueImpl.a(v0);
                                }
                            }
                            this.fireQueueOrderChanged();
                        }
                        catch (BuildTypeNotFoundException v1) {
                            throw BuildQueueImpl.a(v1);
                        }
                    }
                    if (!var4_4.isEmpty()) ** GOTO lbl31
                    try {
                        block15: {
                            if (var5_5.isEmpty()) break block13;
                            break block15;
                            catch (BuildTypeNotFoundException v2) {
                                throw BuildQueueImpl.a(v2);
                            }
                        }
                        Loggers.SERVER.info(var4_4.size() + " newly queued build(s) loaded and " + var5_5.size() + " queued build(s) removed during the last sync with the database");
                    }
                    catch (BuildTypeNotFoundException v3) {
                        throw BuildQueueImpl.a(v3);
                    }
                }
                var7_7 = true;
                return var7_7;
            }
            finally {
                var3_3.dispose();
            }
        }
        return false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean tryToSubstituteQueueFromDB(@NotNull List<QueuedBuildEx> list, @NotNull List<QueuedBuildEx> list2, @NotNull AtomicBoolean atomicBoolean) {
        boolean bl;
        AtomicBoolean atomicBoolean2;
        Object object2;
        int n;
        try {
            if (list == null) {
                BuildQueueImpl.$$$reportNull$$$0(79);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        try {
            if (list2 == null) {
                BuildQueueImpl.$$$reportNull$$$0(80);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        try {
            if (atomicBoolean == null) {
                BuildQueueImpl.$$$reportNull$$$0(81);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        ArrayList<QueuedBuildEx> arrayList = new ArrayList<QueuedBuildEx>();
        AtomicReference<List<QueuedBuildEx>> atomicReference = this.myQueue;
        synchronized (atomicReference) {
            n = this.calcQueueSignature();
        }
        atomicReference = this.getBuildQueueInfoFromDB();
        Map<Long, QueuedBuildEx> map = this.myQueueIndex.get();
        ArrayList<Long> arrayList2 = new ArrayList<Long>();
        for (Object object2 : (List)((Pair)atomicReference).second) {
            try {
                if (map.containsKey(object2.myBuildStateId)) continue;
                arrayList2.add(object2.myBuildStateId);
            }
            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                throw BuildQueueImpl.a(buildTypeNotFoundException);
            }
        }
        HashMap hashMap = new HashMap();
        try {
            if (!arrayList2.isEmpty()) {
                this.myBuildPromotionManager.findPromotionsByIds(arrayList2, new BuildPromotionManager.BuildPromotionInitFromDbHint[0]).forEach(buildPromotion -> hashMap.put(buildPromotion.getId(), (BuildPromotionEx)buildPromotion));
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        ((List)((Pair)atomicReference).second).forEach(queuedBuildTuple -> {
            BuildPromotionEx buildPromotionEx;
            QueuedBuildEx queuedBuildEx = (QueuedBuildEx)map.get(queuedBuildTuple.myBuildStateId);
            if (queuedBuildEx == null && (buildPromotionEx = (BuildPromotionEx)hashMap.get(queuedBuildTuple.myBuildStateId)) != null) {
                queuedBuildEx = this.createRestoredQueuedBuild(buildPromotionEx, (QueuedBuildTuple)queuedBuildTuple);
                list.add(queuedBuildEx);
            }
            try {
                if (queuedBuildEx != null) {
                    queuedBuildEx.markPersisted();
                    arrayList.add(queuedBuildEx);
                }
            }
            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                throw BuildQueueImpl.a(buildTypeNotFoundException);
            }
        });
        object2 = arrayList.stream().map(queuedBuildEx -> queuedBuildEx.getBuildPromotion().getId()).collect(Collectors.toSet());
        try {
            list2.addAll(this.myQueue.get().stream().filter(arg_0 -> BuildQueueImpl.lambda$tryToSubstituteQueueFromDB$36((Set)object2, arg_0)).collect(Collectors.toList()));
            atomicBoolean2 = atomicBoolean;
            bl = !this.myQueue.get().equals(arrayList);
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        atomicBoolean2.set(bl);
        AtomicReference<List<QueuedBuildEx>> atomicReference2 = this.myQueue;
        synchronized (atomicReference2) {
            if (this.calcQueueSignature() != n) {
                return false;
            }
            this.setQueuedBuilds(arrayList);
            for (QueuedBuildEx queuedBuildEx2 : list) {
                this.myWaitReasonStatisticsCollector.onBuildAddedToQueue((SQueuedBuild)queuedBuildEx2);
            }
            this.myOrderingStrategy.restoreQueue(this.getItems());
            this.myOrderVersion.set((Long)((Pair)atomicReference).first);
        }
        return true;
    }

    @NotNull
    private Pair<Long, List<QueuedBuildTuple>> getBuildQueueInfoFromDB() {
        Pair pair = (Pair)this.mySqlRunner.runAndRetry(dBFunctions -> {
            List list = (List)dBFunctions.queryForTuples(new ArrayList(), QueuedBuildTuple.class, null, "select build_state_id, build_type_id, agent_restrictor_type_id, agent_restrictor_id, requestor from build_queue", new Object[0]);
            List list2 = (List)dBFunctions.queryForTuples(new ArrayList(), BuildOrderTuple.class, null, "select version, line_num, promotion_ids from build_queue_order order by line_num", new Object[0]);
            return Pair.create((Object)list, (Object)list2);
        });
        List list = (List)pair.first;
        List list2 = (List)pair.second;
        long l = this.getActualVersion(list2);
        TLongIntMap tLongIntMap = this.deserializeOrder(list2.stream().filter(buildOrderTuple -> {
            boolean bl;
            try {
                bl = buildOrderTuple.getVersion() == l;
            }
            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                throw BuildQueueImpl.a(buildTypeNotFoundException);
            }
            return bl;
        }).map(BuildOrderTuple::getPromotionIds).collect(Collectors.toList()));
        list.sort((queuedBuildTuple, queuedBuildTuple2) -> {
            int n;
            block12: {
                int n2;
                block11: {
                    n = tLongIntMap.get(queuedBuildTuple.myBuildStateId);
                    n2 = tLongIntMap.get(queuedBuildTuple2.myBuildStateId);
                    try {
                        try {
                            if (n <= 0 || n2 <= 0) break block11;
                        }
                        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                            throw BuildQueueImpl.a(buildTypeNotFoundException);
                        }
                        return Integer.compare(n, n2);
                    }
                    catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                        throw BuildQueueImpl.a(buildTypeNotFoundException);
                    }
                }
                try {
                    try {
                        if (n > 0 || n2 > 0) break block12;
                    }
                    catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                        throw BuildQueueImpl.a(buildTypeNotFoundException);
                    }
                    return Long.compare(queuedBuildTuple.myBuildStateId, queuedBuildTuple2.myBuildStateId);
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw BuildQueueImpl.a(buildTypeNotFoundException);
                }
            }
            try {
                if (n <= 0) {
                    return 1;
                }
            }
            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                throw BuildQueueImpl.a(buildTypeNotFoundException);
            }
            return -1;
        });
        return new Pair((Object)l, (Object)list);
    }

    private long getActualVersion(@NotNull List<BuildOrderTuple> list) {
        long l;
        try {
            if (list == null) {
                BuildQueueImpl.$$$reportNull$$$0(82);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        try {
            if (list.isEmpty()) {
                return -1L;
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        List list2 = list.stream().map(BuildOrderTuple::getVersion).distinct().collect(Collectors.toList());
        if (list2.size() != 1) {
            list2.sort(Long::compareTo);
            l = (Long)list2.get(list2.size() - 1);
            LOG.warn("Found more than one (" + list2.size() + ") version of the build queue order, will use the latest one: " + l);
        } else {
            l = (Long)list2.get(0);
        }
        return l;
    }

    public void loadQueue() {
        ArrayList<QueuedBuildEx> arrayList;
        block24: {
            long l;
            block23: {
                BuildPromotionEx buildPromotionEx;
                Pair<Long, List<QueuedBuildTuple>> pair = this.getBuildQueueInfoFromDB();
                List list = (List)pair.second;
                arrayList = new ArrayList<QueuedBuildEx>();
                Set set = list.stream().map(queuedBuildTuple -> queuedBuildTuple.myBuildStateId).collect(Collectors.toSet());
                Map<Long, BuildPromotionEx> map = this.myBuildPromotionManager.findPromotionsByIds(set, new BuildPromotionManager.BuildPromotionInitFromDbHint[0]).stream().collect(Collectors.toMap(BuildPromotion::getId, buildPromotion -> (BuildPromotionEx)buildPromotion));
                for (QueuedBuildTuple queuedBuildTuple2 : list) {
                    QueuedBuildEx queuedBuildEx;
                    block19: {
                        block17: {
                            block18: {
                                buildPromotionEx = map.get(queuedBuildTuple2.myBuildStateId);
                                try {
                                    try {
                                        if (buildPromotionEx != null) break block17;
                                        if (!this.myServerResponsibility.canManageBuilds()) break block18;
                                    }
                                    catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                                        throw BuildQueueImpl.a(buildTypeNotFoundException);
                                    }
                                    Loggers.SERVER.warn("Unable to load a queued build, cannot find build promotion with id: " + queuedBuildTuple2.myBuildStateId);
                                    continue;
                                }
                                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                                    throw BuildQueueImpl.a(buildTypeNotFoundException);
                                }
                            }
                            Loggers.SERVER.debug("Unable to load a queued build, cannot find build promotion with id: " + queuedBuildTuple2.myBuildStateId);
                            continue;
                        }
                        queuedBuildEx = this.createRestoredQueuedBuild(buildPromotionEx, queuedBuildTuple2);
                        try {
                            if (buildPromotionEx.getBuildType() != null) break block19;
                            if (!this.myServerResponsibility.canManageBuilds()) continue;
                        }
                        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                            throw BuildQueueImpl.a(buildTypeNotFoundException);
                        }
                        Loggers.SERVER.warn("Found a build in the queue without a corresponding build configuration: " + LogUtil.describe((Loggable)queuedBuildEx) + ". Will remove the queued build.");
                        if (buildPromotionEx.getAssociatedBuildId() != null) continue;
                        buildPromotionEx.markAsDeleted();
                        continue;
                    }
                    arrayList.add(queuedBuildEx);
                }
                Iterator iterator = arrayList.iterator();
                while (iterator.hasNext()) {
                    QueuedBuildTuple queuedBuildTuple2;
                    block20: {
                        block22: {
                            block21: {
                                queuedBuildTuple2 = (QueuedBuildEx)iterator.next();
                                buildPromotionEx = queuedBuildTuple2.getBuildPromotion();
                                try {
                                    try {
                                        if (buildPromotionEx.getAssociatedBuildId() == null) break block20;
                                        if (!this.myServerResponsibility.canManageBuilds()) break block21;
                                    }
                                    catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                                        throw BuildQueueImpl.a(buildTypeNotFoundException);
                                    }
                                    Loggers.SERVER.warn("Found a build in the queue (" + LogUtil.describe((Loggable)queuedBuildTuple2) + ") for which a running build or a build in history was already created. Most likely this happened because of incorrect server shutdown or JVM crash. Will remove the queued build.");
                                    break block22;
                                }
                                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                                    throw BuildQueueImpl.a(buildTypeNotFoundException);
                                }
                            }
                            try {
                                if (Loggers.SERVER.isDebugEnabled()) {
                                    Loggers.SERVER.debug("Found a build in the queue (" + LogUtil.describe((Loggable)queuedBuildTuple2) + ") for which a running build or a build in history was already created. Probably processed on another node.");
                                }
                            }
                            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                                throw BuildQueueImpl.a(buildTypeNotFoundException);
                            }
                        }
                        iterator.remove();
                    }
                    queuedBuildTuple2.markPersisted();
                }
                this.myWaitReasonStatisticsCollector.onQueuedBuildsRestoreAfterStartup(arrayList);
                this.setQueuedBuilds(arrayList);
                l = (Long)pair.first;
                try {
                    if (l != -1L) break block23;
                    this.persistNotDefinedVersion();
                    this.myOrderVersion.set(0L);
                    break block24;
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw BuildQueueImpl.a(buildTypeNotFoundException);
                }
            }
            this.myOrderVersion.set(l);
        }
        this.myQueueRestored = true;
        LOG.info("Build queue loaded: " + StringUtil.withPlural((long)arrayList.size(), (String)"build"));
    }

    private void persistNotDefinedVersion() {
        try {
            this.mySqlRunner.withDBNoResult(dBFunctions -> dBFunctions.executeDml("insert into build_queue_order (version, line_num, promotion_ids) values (?,?,?)", new Object[]{0L, 1, ""}));
        }
        catch (DBException dBException) {
            LOG.error("Unable to initialize build queue order table. Build queue order can be broken", (Throwable)dBException);
        }
    }

    @NotNull
    private QueuedBuildEx createRestoredQueuedBuild(@NotNull BuildPromotionEx buildPromotionEx, @NotNull QueuedBuildTuple queuedBuildTuple) {
        QueuedBuildEx queuedBuildEx;
        SAgentRestrictor sAgentRestrictor;
        block11: {
            try {
                if (buildPromotionEx == null) {
                    BuildQueueImpl.$$$reportNull$$$0(83);
                }
            }
            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                throw BuildQueueImpl.a(buildTypeNotFoundException);
            }
            try {
                if (queuedBuildTuple == null) {
                    BuildQueueImpl.$$$reportNull$$$0(84);
                }
            }
            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                throw BuildQueueImpl.a(buildTypeNotFoundException);
            }
            sAgentRestrictor = null;
            try {
                if (queuedBuildTuple.myAgentRestrictorTypeId == null || queuedBuildTuple.myAgentRestrictorId == null) break block11;
            }
            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                throw BuildQueueImpl.a(buildTypeNotFoundException);
            }
            sAgentRestrictor = this.myAgentRestrictorFactory.createFor(queuedBuildTuple.myAgentRestrictorTypeId.intValue(), queuedBuildTuple.myAgentRestrictorId.intValue());
        }
        try {
            queuedBuildEx = this.myQueuedBuildFactory.createRestoredQueuedBuild(buildPromotionEx, sAgentRestrictor, queuedBuildTuple.myRequestor);
            if (queuedBuildEx == null) {
                BuildQueueImpl.$$$reportNull$$$0(85);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        return queuedBuildEx;
    }

    private long persistOrder() {
        long l = this.myOrderVersion.get();
        long l2 = l + 1L;
        List<BuildOrderTuple> list = this.createQueueOrderTuples(l2);
        try {
            if (list.isEmpty()) {
                list.add(new BuildOrderTuple(l2, 1, ""));
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        boolean bl = (Boolean)this.mySqlRunner.runAndRetry(dBFunctions -> {
            BuildOrderTuple buildOrderTuple = (BuildOrderTuple)list.get(0);
            int n = dBFunctions.executeDml("update build_queue_order set version = ?, promotion_ids = ? where version = ? and line_num = ?", new Object[]{buildOrderTuple.getVersion(), buildOrderTuple.getPromotionIds(), l, buildOrderTuple.getLineNum()});
            try {
                if (n == 0) {
                    return false;
                }
            }
            catch (DBException dBException) {
                throw BuildQueueImpl.a(dBException);
            }
            dBFunctions.executeDml("delete from build_queue_order where version < ?", new Object[]{l2});
            dBFunctions.executeBatchForTuples("insert into build_queue_order (version, line_num, promotion_ids) values (:version, :lineNum, :promotionIds)", BuildOrderTuple.class, list.subList(1, list.size()));
            return true;
        });
        try {
            if (!bl) {
                return -1L;
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        return l2;
    }

    @NotNull
    private TLongIntMap deserializeOrder(@NotNull List<String> list) {
        TLongIntHashMap tLongIntHashMap;
        try {
            if (list == null) {
                BuildQueueImpl.$$$reportNull$$$0(86);
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw BuildQueueImpl.a(numberFormatException);
        }
        TLongIntHashMap tLongIntHashMap2 = new TLongIntHashMap();
        int n = 1;
        for (String string : list) {
            try {
                if (StringUtil.isEmpty((String)string)) {
                    continue;
                }
            }
            catch (NumberFormatException numberFormatException) {
                throw BuildQueueImpl.a(numberFormatException);
            }
            for (String string2 : StringUtil.split((String)string, (boolean)true, (char[])new char[]{','})) {
                try {
                    tLongIntHashMap2.put(Long.parseLong(string2), n);
                    ++n;
                }
                catch (NumberFormatException numberFormatException) {
                    LOG.warnAndDebugDetails("Could not parse build promotion id from the build queue order: " + string2, (Throwable)numberFormatException);
                }
            }
        }
        try {
            tLongIntHashMap = tLongIntHashMap2;
            if (tLongIntHashMap == null) {
                BuildQueueImpl.$$$reportNull$$$0(87);
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw BuildQueueImpl.a(numberFormatException);
        }
        return tLongIntHashMap;
    }

    @NotNull
    private List<BuildOrderTuple> createQueueOrderTuples(long l) {
        ArrayList<BuildOrderTuple> arrayList;
        int n = 2000;
        ArrayList<BuildOrderTuple> arrayList2 = new ArrayList<BuildOrderTuple>();
        int n2 = 1;
        StringBuilder stringBuilder = new StringBuilder(2000);
        for (QueuedBuildEx queuedBuildEx : this.myQueue.get()) {
            String string = String.valueOf(queuedBuildEx.getBuildPromotion().getId());
            try {
                if (stringBuilder.length() == 0) {
                    stringBuilder.append(string);
                    continue;
                }
            }
            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                throw BuildQueueImpl.a(buildTypeNotFoundException);
            }
            try {
                if (stringBuilder.length() + string.length() + 1 <= 2000) {
                    stringBuilder.append(',').append(string);
                    continue;
                }
            }
            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                throw BuildQueueImpl.a(buildTypeNotFoundException);
            }
            arrayList2.add(new BuildOrderTuple(l, n2, stringBuilder.toString()));
            ++n2;
            stringBuilder.setLength(0);
            stringBuilder.append(string);
        }
        try {
            if (stringBuilder.length() > 0) {
                arrayList2.add(new BuildOrderTuple(l, n2, stringBuilder.toString()));
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        try {
            arrayList = arrayList2;
            if (arrayList == null) {
                BuildQueueImpl.$$$reportNull$$$0(88);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        return arrayList;
    }

    private void insertNewItems(@NotNull DBFunctions dBFunctions, @NotNull List<QueuedBuildEx> list) {
        try {
            if (dBFunctions == null) {
                BuildQueueImpl.$$$reportNull$$$0(89);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        try {
            if (list == null) {
                BuildQueueImpl.$$$reportNull$$$0(90);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        ArrayList arrayList = new ArrayList();
        list.forEach(queuedBuildEx -> {
            SAgentRestrictor sAgentRestrictor = queuedBuildEx.getAgentRestrictor();
            String string = StringUtil.truncateStringValue((String)queuedBuildEx.getTriggeredBy().getRawTriggeredBy(), (int)1024);
            QueuedBuildTuple queuedBuildTuple = sAgentRestrictor != null ? new QueuedBuildTuple(queuedBuildEx.getBuildPromotion().getId(), queuedBuildEx.getBuildTypeId(), Integer.valueOf(sAgentRestrictor.getType().getTypeId()), Integer.valueOf(sAgentRestrictor.getId()), string) : new QueuedBuildTuple(queuedBuildEx.getBuildPromotion().getId(), queuedBuildEx.getBuildTypeId(), null, null, string);
            arrayList.add(queuedBuildTuple);
        });
        dBFunctions.executeBatchForTuples("insert into build_queue (build_state_id, build_type_id, agent_restrictor_type_id, agent_restrictor_id, requestor) values (:myBuildStateId, :myBuildTypeId, :myAgentRestrictorTypeId, :myAgentRestrictorId, :myRequestor)", QueuedBuildTuple.class, arrayList);
    }

    public void setMergeBuildsInQueue(boolean bl) {
        this.myMergeBuildsInQueue = bl;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean mergeBuilds(@Nullable java.util.function.Predicate<SQueuedBuild> predicate) {
        try {
            if (!this.myMergeBuildsInQueue) {
                return false;
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        Disposable disposable = NamedThreadFactory.patchThreadName((String)("Performing build queue merge optimization, queue size: " + this.myQueue.get().size()));
        try {
            boolean bl;
            try {
                bl = this.mergeAndPersist(predicate).size() > 0;
            }
            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                throw BuildQueueImpl.a(buildTypeNotFoundException);
            }
            boolean bl2 = bl;
            return bl2;
        }
        finally {
            disposable.dispose();
        }
    }

    private boolean doOptimizeChains(@NotNull List<BuildPromotionEx> list, boolean bl) {
        try {
            if (list == null) {
                BuildQueueImpl.$$$reportNull$$$0(91);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        try {
            if (list.isEmpty()) {
                return false;
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        return (Boolean)this.mySecurityContext.runAsSystemUnchecked(() -> {
            boolean bl2;
            BuildPromotion buildPromotion;
            HashMap<BuildPromotion, BuildPromotion> hashMap;
            HashMap hashMap2 = new HashMap();
            5 var4_4 = new /* Unavailable Anonymous Inner Class!! */;
            List list2 = DependenciesUtil.findAllTopPromotions((Collection)list).stream().filter(buildPromotionEx -> buildPromotionEx.isPartOfBuildChain()).collect(Collectors.toList());
            try {
                if (list2.isEmpty()) {
                    return false;
                }
            }
            catch (Throwable throwable) {
                throw BuildQueueImpl.a(throwable);
            }
            Set set = this.myBuildChainsOptimizer.optimizeBuildChains(list2, bl, (OptimizationListener)var4_4);
            HashSet<QueuedBuildEx> hashSet = new HashSet<QueuedBuildEx>();
            HashSet<BuildPromotion> hashSet2 = new HashSet<BuildPromotion>();
            if (!set.isEmpty()) {
                hashMap = new HashMap<BuildPromotion, BuildPromotion>();
                DependenciesUtil.findUnusedBuildPromotions((Set)set).forEach(buildPromotionEx -> {
                    block12: {
                        QueuedBuildEx queuedBuildEx = this.myQueueIndex.get().get(buildPromotionEx.getId());
                        try {
                            if (!(buildPromotionEx instanceof BuildPromotionImpl)) {
                                return;
                            }
                        }
                        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                            throw BuildQueueImpl.a(buildTypeNotFoundException);
                        }
                        try {
                            block11: {
                                try {
                                    try {
                                        try {
                                            if (queuedBuildEx == null && buildPromotionEx.getAssociatedBuildId() == null) break block11;
                                        }
                                        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                                            throw BuildQueueImpl.a(buildTypeNotFoundException);
                                        }
                                        if (queuedBuildEx == null) break block12;
                                    }
                                    catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                                        throw BuildQueueImpl.a(buildTypeNotFoundException);
                                    }
                                    if (!queuedBuildEx.tryLockBuildStarting()) break block12;
                                }
                                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                                    throw BuildQueueImpl.a(buildTypeNotFoundException);
                                }
                            }
                            hashMap.put((BuildPromotion)((BuildPromotionImpl)buildPromotionEx), (BuildPromotion)queuedBuildEx);
                        }
                        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                            throw BuildQueueImpl.a(buildTypeNotFoundException);
                        }
                    }
                });
                BuildPromotionImpl.markAsDeleted(hashMap.keySet(), (BuildPromotionContext)this.myBuildPromotionManager.getContext());
                for (BuildPromotion buildPromotion2 : hashMap.keySet()) {
                    block26: {
                        buildPromotion = (QueuedBuildEx)hashMap.get(buildPromotion2);
                        try {
                            if (buildPromotion2.isDeleted()) break block26;
                            if (buildPromotion == null) continue;
                        }
                        catch (Throwable throwable) {
                            throw BuildQueueImpl.a(throwable);
                        }
                        buildPromotion.unlockBuildStarting();
                        continue;
                    }
                    try {
                        if (buildPromotion == null) {
                            hashSet2.add(buildPromotion2);
                            continue;
                        }
                    }
                    catch (Throwable throwable) {
                        throw BuildQueueImpl.a(throwable);
                    }
                    hashSet.add((QueuedBuildEx)buildPromotion);
                }
                if (!hashSet.isEmpty()) {
                    AtomicReference<List<QueuedBuildEx>> atomicReference = this.myQueue;
                    synchronized (atomicReference) {
                        this.removeItems(hashSet);
                        if (this.persistAddedAndRemovedItems(Collections.emptyList(), hashSet)) {
                            this.myNodesSynchronizationTask.publishEvent();
                        }
                    }
                }
            }
            hashMap = new HashMap();
            for (BuildPromotion buildPromotion2 : CollectionsUtil.join((Collection)hashSet.stream().map(queuedBuildEx -> queuedBuildEx.getBuildPromotion()).collect(Collectors.toList()), hashSet2)) {
                buildPromotion = (BuildPromotion)hashMap2.get(buildPromotion2);
                try {
                    if (buildPromotion == null) continue;
                    hashMap.put(buildPromotion2, buildPromotion);
                }
                catch (Throwable throwable) {
                    throw BuildQueueImpl.a(throwable);
                }
            }
            try {
                if (hashMap.size() > 0) {
                    this.myBuildPromotionReplacementLog.rememberReplacements(hashMap, BuildPromotionReplacementLog.ReplacementReason.BUILD_WITH_SAME_REVISIONS_AVAILABLE);
                }
            }
            catch (Throwable throwable) {
                throw BuildQueueImpl.a(throwable);
            }
            try {
                if (hashSet.size() > 0) {
                    Loggers.SERVER.info("Build queue optimizer substituted " + hashSet.size() + " queued builds with already started or other queued builds");
                }
            }
            catch (Throwable throwable) {
                throw BuildQueueImpl.a(throwable);
            }
            try {
                this.postProcessBuildsRemovedByOptimizer(hashSet, "optimized to already started or another queued build");
                bl2 = !hashSet.isEmpty();
            }
            catch (Throwable throwable) {
                throw BuildQueueImpl.a(throwable);
            }
            return bl2;
        });
    }

    private static boolean optimizerCanReuseQueuedBuilds() {
        return TeamCityProperties.getBooleanOrTrue((String)"teamcity.buildChains.reuseQueuedBuilds");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean optimizeChains(@NotNull List<BuildPromotionEx> list) {
        try {
            if (list == null) {
                BuildQueueImpl.$$$reportNull$$$0(92);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        try {
            if (list.isEmpty()) {
                return false;
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        Disposable disposable = NamedThreadFactory.patchThreadName((String)("Performing queued build chains optimization for " + list.size() + " builds"));
        try {
            boolean bl = this.doOptimizeChains(list, BuildQueueImpl.optimizerCanReuseQueuedBuilds());
            return bl;
        }
        finally {
            disposable.dispose();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     */
    @NotNull
    public List<QueuedBuildEx> restoreInQueue(@NotNull BuildPromotionEx buildPromotionEx, @Nullable SAgentRestrictor sAgentRestrictor, @NotNull String string) {
        List<QueuedBuildEx> list;
        List<QueuedBuildEx> list2;
        BuildPromotionEx buildPromotionEx2;
        List<Disposable> list3;
        block19: {
            List<QueuedBuildEx> list4;
            block20: {
                List<Disposable> list5;
                try {
                    if (buildPromotionEx == null) {
                        BuildQueueImpl.$$$reportNull$$$0(93);
                    }
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw BuildQueueImpl.a(buildTypeNotFoundException);
                }
                try {
                    if (string == null) {
                        BuildQueueImpl.$$$reportNull$$$0(94);
                    }
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw BuildQueueImpl.a(buildTypeNotFoundException);
                }
                try {
                    list5 = TeamCityProperties.getBooleanOrTrue((String)"teamcity.buildPromotion.restoreInQueue.lockBuildChains") ? this.tryLockDependingPromotions(buildPromotionEx) : Collections.singletonList(() -> {});
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw BuildQueueImpl.a(buildTypeNotFoundException);
                }
                list3 = list5;
                if (!list3.isEmpty()) break block19;
                try {
                    block22: {
                        LOG.warn("Won't restore the build " + LogUtil.describe((BuildPromotion)buildPromotionEx) + " in the queue because we could not obtain a lock to change dependencies for all the build chains where it participates");
                        list4 = Collections.emptyList();
                        if (list4 != null) break block20;
                        break block22;
                        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                            throw BuildQueueImpl.a(buildTypeNotFoundException);
                        }
                    }
                    BuildQueueImpl.$$$reportNull$$$0(95);
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw BuildQueueImpl.a(buildTypeNotFoundException);
                }
            }
            return list4;
        }
        try {
            buildPromotionEx2 = this.copyAndFixDependencies(buildPromotionEx);
            buildPromotionEx2.setAttribute("teamcity.build.freezeSettings.forcePostprocessing", null);
            list2 = this.doAddToQueue(Collections.singletonMap(buildPromotionEx2, sAgentRestrictor), string);
            buildPromotionEx2.postProcessingFinished();
            buildPromotionEx2.getDependedOnMe().forEach(buildDependencyEx -> buildDependencyEx.getDependent().persist());
        }
        finally {
            BuildQueueImpl.disposeAll(list3);
        }
        new DependentBuildStartPredicate().propagateProblemsToDependencies(buildPromotionEx2, false);
        if (!list2.isEmpty()) {
            LOG.info("Build " + LogUtil.describe((BuildPromotion)buildPromotionEx2) + " was re-added to build queue instead of build " + LogUtil.describe((BuildPromotion)buildPromotionEx));
            String string2 = this.myWebLinks.getBuildUrl(buildPromotionEx2.getId(), buildPromotionEx2.getBuildTypeId());
            String string3 = this.myWebLinks.getBuildUrl(buildPromotionEx.getId(), buildPromotionEx.getBuildTypeId());
            buildPromotionEx.getBuildLog().messageAsync("This build has been restarted, new build: " + string2, Status.WARNING, MessageAttrs.serverMessage());
            buildPromotionEx2.getBuildLog().messageAsync("This build was re-added to the build queue instead of build " + string3, Status.WARNING, MessageAttrs.serverMessage());
        }
        try {
            list = list2;
            if (list == null) {
                BuildQueueImpl.$$$reportNull$$$0(96);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        return list;
    }

    private static void disposeAll(@NotNull List<Disposable> list) {
        try {
            if (list == null) {
                BuildQueueImpl.$$$reportNull$$$0(97);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        for (Disposable disposable : list) {
            disposable.dispose();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Unable to fully structure code
     */
    @NotNull
    private List<Disposable> tryLockDependingPromotions(@NotNull BuildPromotionEx var1_1) {
        block21: {
            block22: {
                try {
                    if (var1_1 == null) {
                        BuildQueueImpl.$$$reportNull$$$0(98);
                    }
                }
                catch (InterruptedException v0) {
                    throw BuildQueueImpl.a(v0);
                }
                var2_2 = new ArrayList<Disposable>();
                var3_3 = var1_1.getDependedOnMe().stream().map((Function<BuildDependencyEx, BuildPromotionEx>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)Ljava/lang/Object;, lambda$tryLockDependingPromotions$51(jetbrains.buildServer.serverSide.dependency.BuildDependencyEx ), (Ljetbrains/buildServer/serverSide/dependency/BuildDependencyEx;)Ljetbrains/buildServer/serverSide/BuildPromotionEx;)()).sorted((Comparator)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;Ljava/lang/Object;)I, lambda$tryLockDependingPromotions$52(jetbrains.buildServer.serverSide.BuildPromotionEx jetbrains.buildServer.serverSide.BuildPromotionEx ), (Ljetbrains/buildServer/serverSide/BuildPromotionEx;Ljetbrains/buildServer/serverSide/BuildPromotionEx;)I)()).collect(Collectors.toList());
                if (!var3_3.isEmpty()) break block21;
                try {
                    block23: {
                        v1 = Collections.singletonList(new /* Unavailable Anonymous Inner Class!! */);
                        if (v1 != null) break block22;
                        break block23;
                        catch (InterruptedException v2) {
                            throw BuildQueueImpl.a(v2);
                        }
                    }
                    BuildQueueImpl.$$$reportNull$$$0(99);
                }
                catch (InterruptedException v3) {
                    throw BuildQueueImpl.a(v3);
                }
            }
            return v1;
        }
        var4_4 = new ArrayDeque<T>(var3_3);
        var5_5 = TeamCityProperties.getInteger((String)"teamcity.buildPromotion.lockDependencies.maxAttempts", (int)30);
        var6_6 = 0;
        while (!var4_4.isEmpty()) {
            var7_7 = (BuildPromotionEx)var4_4.peek();
            try {
                var8_8 = TeamCityProperties.getInteger((String)"teamcity.buildPromotion.lockDependencies.timeoutMillis", (int)5000);
                var9_10 = this.myMultiNodeLocks.tryLock("dependencies", var7_7.getId(), (long)var8_8);
                if (var9_10 != null) {
                    var2_2.add((Disposable)new /* Unavailable Anonymous Inner Class!! */);
                    var4_4.pop();
                    var6_6 = 0;
                    continue;
                }
                if (++var6_6 < var5_5) continue;
                BuildQueueImpl.LOG.warn("Could not obtain a lock to change dependencies for the build " + LogUtil.describe((BuildPromotion)var7_7) + " after " + var5_5 + " attempts, giving up");
            }
            catch (InterruptedException var8_9) {
                BuildQueueImpl.LOG.warn("Interrupted while trying to obtain a lock to change dependencies of a " + LogUtil.describe((BuildPromotion)var7_7));
            }
        }
lbl47:
        // 3 sources

        try {
            if (var2_2.size() >= var3_3.size()) ** GOTO lbl64
            BuildQueueImpl.disposeAll(var2_2);
            var2_2.clear();
        }
        catch (InterruptedException v4) {
            throw BuildQueueImpl.a(v4);
        }
        catch (Throwable var10_11) {
            try {
                if (var2_2.size() < var3_3.size()) {
                    BuildQueueImpl.disposeAll(var2_2);
                    var2_2.clear();
                }
            }
            catch (InterruptedException v5) {
                throw BuildQueueImpl.a(v5);
            }
            throw var10_11;
        }
        ** GOTO lbl47
lbl64:
        // 2 sources

        try {
            v6 = var2_2;
            if (v6 == null) {
                BuildQueueImpl.$$$reportNull$$$0(100);
            }
        }
        catch (InterruptedException v7) {
            throw BuildQueueImpl.a(v7);
        }
        return v6;
    }

    public void setOrderingStrategy(@NotNull BuildQueueOrderingStrategy buildQueueOrderingStrategy) {
        try {
            if (buildQueueOrderingStrategy == null) {
                BuildQueueImpl.$$$reportNull$$$0(101);
            }
        }
        catch (Throwable throwable) {
            throw BuildQueueImpl.a(throwable);
        }
        this.myOrderingStrategy = buildQueueOrderingStrategy;
        if (this.myServerResponsibility.canQueueBuilds()) {
            try {
                this.myOrderingStrategy.addBuilds(this.getItems(), new ArrayList());
            }
            catch (Throwable throwable) {
                LOG.error("Error while changing build order, work as if no reordering was performed", throwable);
            }
        }
        Loggers.SERVER.info("Ordering strategy changed to " + this.myOrderingStrategy.getClass().getCanonicalName());
    }

    @NotNull
    public List<QueuedBuildEx> getItemsEx() {
        return this.getQueuedBuildsCopy();
    }

    @NotNull
    public List<QueuedBuildEx> getPersistedItemsEx() {
        List list;
        try {
            list = (List)FilterUtil.filterCollection(this.getQueuedBuildsCopy(), queuedBuildEx -> {
                boolean bl;
                block7: {
                    block6: {
                        try {
                            try {
                                try {
                                    if (!queuedBuildEx.isPersisted() || queuedBuildEx.isStarted()) break block6;
                                }
                                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                                    throw BuildQueueImpl.a(buildTypeNotFoundException);
                                }
                                if (queuedBuildEx.getBuildPromotion().isDeleted()) break block6;
                            }
                            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                                throw BuildQueueImpl.a(buildTypeNotFoundException);
                            }
                            bl = true;
                            break block7;
                        }
                        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                            throw BuildQueueImpl.a(buildTypeNotFoundException);
                        }
                    }
                    bl = false;
                }
                return bl;
            });
            if (list == null) {
                BuildQueueImpl.$$$reportNull$$$0(102);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        return list;
    }

    private BuildPromotionEx copyAndFixDependencies(@NotNull BuildPromotionEx buildPromotionEx) {
        boolean bl;
        block14: {
            block13: {
                try {
                    if (buildPromotionEx == null) {
                        BuildQueueImpl.$$$reportNull$$$0(103);
                    }
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw BuildQueueImpl.a(buildTypeNotFoundException);
                }
                try {
                    try {
                        if (!buildPromotionEx.isPartOfBuildChain() && !buildPromotionEx.isOutOfChangesSequence()) break block13;
                    }
                    catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                        throw BuildQueueImpl.a(buildTypeNotFoundException);
                    }
                    bl = true;
                    break block14;
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw BuildQueueImpl.a(buildTypeNotFoundException);
                }
            }
            bl = false;
        }
        boolean bl2 = bl;
        buildPromotionEx.resetDependentOnMeCache();
        BuildPromotionEx buildPromotionEx2 = buildPromotionEx.copy(bl2);
        for (Object object : buildPromotionEx.getDependedOnMe()) {
            BuildPromotionEx buildPromotionEx3 = (BuildPromotionEx)object.getDependent();
            buildPromotionEx3.replaceDependency(buildPromotionEx, buildPromotionEx2, BuildDependencyImpl.copyOptions((OptionSupport)object));
        }
        RevisionRule revisionRule = RevisionRules.newBuildIdRule((long)buildPromotionEx.getId(), null);
        for (BuildPromotionEx buildPromotionEx3 : buildPromotionEx2.getDependentCompositePromotions()) {
            ArrayList<SArtifactDependency> arrayList = new ArrayList<SArtifactDependency>();
            boolean bl3 = false;
            for (SArtifactDependency sArtifactDependency : buildPromotionEx3.getFinalArtifactDependencies()) {
                if (sArtifactDependency.getRevisionRule().getRevision().equals(revisionRule.getRevision())) {
                    SArtifactDependency sArtifactDependency2 = sArtifactDependency.createCopy();
                    sArtifactDependency2.setRevisionRule(RevisionRules.newBuildIdRule((long)buildPromotionEx2.getId(), null));
                    arrayList.add(sArtifactDependency2);
                    bl3 = true;
                    continue;
                }
                arrayList.add(sArtifactDependency);
            }
            try {
                if (!bl3) continue;
                buildPromotionEx3.setFinalArtifactDependencies(arrayList);
                buildPromotionEx3.persist();
            }
            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                throw BuildQueueImpl.a(buildTypeNotFoundException);
            }
        }
        return buildPromotionEx2;
    }

    @NotNull
    private Collection<QueuedBuildEx> mergeAndPersist(@Nullable java.util.function.Predicate<SQueuedBuild> predicate) {
        Collection collection;
        try {
            collection = (Collection)this.mySecurityContext.runAsSystem(() -> {
                Collection<QueuedBuildEx> collection;
                ApplyCurrentOrder applyCurrentOrder = null;
                AtomicReference<List<QueuedBuildEx>> atomicReference = this.myQueue;
                synchronized (atomicReference) {
                    block12: {
                        if (!this.isQueueRestored()) {
                            return Collections.emptyList();
                        }
                        collection = this.doMergeBuilds(predicate, false);
                        try {
                            if (collection.isEmpty()) break block12;
                            this.persistAddedAndRemovedItems(Collections.emptyList(), collection);
                            if (this.persistCurrentQueueOrder()) break block12;
                        }
                        catch (Throwable throwable) {
                            throw BuildQueueImpl.a(throwable);
                        }
                        applyCurrentOrder = new ApplyCurrentOrder(this);
                    }
                }
                try {
                    if (!collection.isEmpty()) {
                        this.changeOrderAndSendEvent((ChangeOrderAction)applyCurrentOrder, true);
                    }
                }
                catch (Throwable throwable) {
                    throw BuildQueueImpl.a(throwable);
                }
                try {
                    this.markAsDeleted(collection);
                    if (collection.size() > 0) {
                        Loggers.SERVER.info("Build queue optimizer substituted " + collection.size() + " builds in the queue with builds containing more changes");
                    }
                }
                catch (Throwable throwable) {
                    throw BuildQueueImpl.a(throwable);
                }
                this.postProcessBuildsRemovedByOptimizer(collection, "substituted by build with more changes");
                return collection;
            });
        }
        catch (Throwable throwable) {
            List<QueuedBuildEx> list;
            try {
                Loggers.SERVER.error("Error merging and persisting queue: " + throwable.toString(), throwable);
                list = Collections.emptyList();
                if (list == null) {
                    BuildQueueImpl.$$$reportNull$$$0(105);
                }
            }
            catch (Throwable throwable2) {
                throw BuildQueueImpl.a(throwable2);
            }
            return list;
        }
        if (collection == null) {
            BuildQueueImpl.$$$reportNull$$$0(104);
        }
        return collection;
    }

    private void markAsDeleted(@NotNull Collection<QueuedBuildEx> collection) {
        try {
            if (collection == null) {
                BuildQueueImpl.$$$reportNull$$$0(106);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        ArrayList<BuildPromotionImpl> arrayList = new ArrayList<BuildPromotionImpl>();
        for (QueuedBuildEx queuedBuildEx : collection) {
            BuildPromotionEx buildPromotionEx = queuedBuildEx.getBuildPromotion();
            try {
                if (!(buildPromotionEx instanceof BuildPromotionImpl)) continue;
                arrayList.add((BuildPromotionImpl)buildPromotionEx);
            }
            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                throw BuildQueueImpl.a(buildTypeNotFoundException);
            }
        }
        BuildPromotionImpl.markAsDeleted(arrayList, (BuildPromotionContext)this.myBuildPromotionManager.getContext());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Used(value="tests")
    @NotNull
    public Collection<QueuedBuildEx> computeBuildsToMerge() {
        Collection collection;
        AtomicReference<List<QueuedBuildEx>> atomicReference = this.myQueue;
        synchronized (atomicReference) {
            try {
                collection = (Collection)this.mySecurityContext.runAsSystem(() -> {
                    try {
                        if (!this.isQueueRestored()) {
                            return Collections.emptyList();
                        }
                    }
                    catch (Throwable throwable) {
                        throw BuildQueueImpl.a(throwable);
                    }
                    return this.doMergeBuilds(null, true);
                });
            }
            catch (Throwable throwable) {
                Loggers.SERVER.error("Error merging and persisting queue: " + throwable.toString(), throwable);
                // MONITOREXIT @DISABLED, blocks:[3, 4] lbl9 : MonitorExitStatement: MONITOREXIT : atomicReference
                try {
                    List<QueuedBuildEx> list = Collections.emptyList();
                    if (list != null) List<QueuedBuildEx> list2;
                    return list2;
                    BuildQueueImpl.$$$reportNull$$$0(108);
                    return list;
                }
                catch (Throwable throwable2) {
                    throw BuildQueueImpl.a(throwable2);
                }
            }
        }
        if (collection != null) return collection;
        BuildQueueImpl.$$$reportNull$$$0(107);
        return collection;
    }

    private boolean isQueueRestored() {
        return this.myQueueRestored;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Could not resolve type clashes
     * Loose catch block
     */
    @NotNull
    private Collection<QueuedBuildEx> doMergeBuilds(@Nullable java.util.function.Predicate<SQueuedBuild> predicate, boolean bl) {
        Object object;
        Object object2;
        block44: {
            List<QueuedBuildEx> list;
            block45: {
                if (this.myQueue.get().size() >= 2) break block44;
                try {
                    block53: {
                        list = Collections.emptyList();
                        if (list != null) break block45;
                        break block53;
                        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                            throw BuildQueueImpl.a(buildTypeNotFoundException);
                        }
                    }
                    BuildQueueImpl.$$$reportNull$$$0(109);
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw BuildQueueImpl.a(buildTypeNotFoundException);
                }
            }
            return list;
        }
        List<QueuedBuildEx> list = this.findBuildsForMerging(predicate);
        try {
            Set<QueuedBuildEx> set;
            block51: {
                Collection collection;
                Set<QueuedBuildEx> set2;
                Object object3;
                Map<QueuedBuildEx, Set<QueuedBuildEx>> map;
                block49: {
                    block50: {
                        block56: {
                            block47: {
                                block46: {
                                    if (!LOG.isDebugEnabled()) break block46;
                                    try {
                                        block54: {
                                            if (list.isEmpty()) break block46;
                                            break block54;
                                            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                                                throw BuildQueueImpl.a(buildTypeNotFoundException);
                                            }
                                        }
                                        LOG.debug("Queued builds selected for merge operation (personal and starting builds excluded): " + this.describeQueuedBuilds(list));
                                    }
                                    catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                                        throw BuildQueueImpl.a(buildTypeNotFoundException);
                                    }
                                }
                                List<List<QueuedBuildEx>> list3 = this.divideByGroups(list);
                                ArrayList<QueuedBuildEx> arrayList = new ArrayList<QueuedBuildEx>();
                                list3.forEach(list2 -> arrayList.addAll((Collection<QueuedBuildEx>)list2));
                                Map<BuildPromotionEx, BuildChainInfo> map2 = this.createBuildChainsMap(arrayList);
                                map = this.computeBuildsToReplace(list3, queuedBuildEx -> (BuildChainInfo)map2.get(queuedBuildEx.getBuildPromotion()));
                                if (!LOG.isDebugEnabled()) break block47;
                                try {
                                    block55: {
                                        if (map.isEmpty()) break block47;
                                        break block55;
                                        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                                            throw BuildQueueImpl.a(buildTypeNotFoundException);
                                        }
                                    }
                                    LOG.debug("Queued builds which can be replaced with replacement candidates: " + this.dumpReplacementMap(map));
                                }
                                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                                    throw BuildQueueImpl.a(buildTypeNotFoundException);
                                }
                            }
                            try {
                                if (this.myAvoidDelayingOfStartedChains) {
                                    this.processStartedChains(map);
                                }
                            }
                            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                                throw BuildQueueImpl.a(buildTypeNotFoundException);
                            }
                            this.removeBuildsWithoutCandidates(map);
                            if (map.isEmpty()) break block56;
                            set = map.keySet();
                            object2 = this.findChainedBuilds(set);
                            object3 = object2.iterator();
                            while (object3.hasNext()) {
                                block48: {
                                    set2 = object3.next();
                                    if (set.containsAll(set2)) continue;
                                    boolean bl2 = set.removeAll(set2);
                                    if (!LOG.isDebugEnabled()) break block48;
                                    try {
                                        block57: {
                                            if (!bl2) break block48;
                                            break block57;
                                            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                                                throw BuildQueueImpl.a(buildTypeNotFoundException);
                                            }
                                        }
                                        LOG.debug("The following builds belonging to the same chain cannot be replaced: " + this.describeQueuedBuilds(set2) + ", because not all of them have replacement candidates");
                                    }
                                    catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                                        throw BuildQueueImpl.a(buildTypeNotFoundException);
                                    }
                                }
                                try {
                                    if (!set.isEmpty()) continue;
                                    break;
                                }
                                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                                    throw BuildQueueImpl.a(buildTypeNotFoundException);
                                }
                            }
                        }
                        if (!LOG.isDebugEnabled()) break block49;
                        try {
                            block58: {
                                if (map.isEmpty()) break block50;
                                break block58;
                                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                                    throw BuildQueueImpl.a(buildTypeNotFoundException);
                                }
                            }
                            LOG.debug("Queued builds which can be replaced with replacement candidates (final): " + this.dumpReplacementMap(map));
                            break block49;
                        }
                        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                            throw BuildQueueImpl.a(buildTypeNotFoundException);
                        }
                    }
                    LOG.debug("None of the queued builds can be merged");
                }
                set = new HashSet<QueuedBuildEx>(map.keySet());
                try {
                    if (map.isEmpty() || bl) break block51;
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw BuildQueueImpl.a(buildTypeNotFoundException);
                }
                object2 = this.compactReplacementMap(map);
                object3 = new HashMap();
                set2 = new LinkedHashSet<QueuedBuildEx>();
                for (Object object4 : this.myQueue.get()) {
                    block52: {
                        Object object5 = map.get(object4);
                        try {
                            if (object5 == null || object5.size() <= 0) break block52;
                        }
                        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                            throw BuildQueueImpl.a(buildTypeNotFoundException);
                        }
                        collection = new ArrayList(object5);
                        collection.sort((queuedBuildEx, queuedBuildEx2) -> (int)(queuedBuildEx2.getBuildPromotion().getId() - queuedBuildEx.getBuildPromotion().getId()));
                        QueuedBuildEx queuedBuildEx3 = (QueuedBuildEx)collection.getFirst();
                        ((HashSet)set2).add((QueuedBuildEx)queuedBuildEx3.getItemId());
                        object3.put(object4, queuedBuildEx3);
                        continue;
                    }
                    ((HashSet)set2).add((QueuedBuildEx)object4.getItemId());
                }
                HashMap hashMap = new HashMap();
                for (Object object5 : object3.entrySet()) {
                    hashMap.put(((QueuedBuildEx)object5.getKey()).getBuildPromotion(), ((QueuedBuildEx)object5.getValue()).getBuildPromotion());
                }
                block39: for (Object object5 : set) {
                    try {
                        if (map.containsKey(object5)) {
                            continue;
                        }
                    }
                    catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                        throw BuildQueueImpl.a(buildTypeNotFoundException);
                    }
                    collection = (Set)object2.get(object5);
                    if (collection == null) continue;
                    for (QueuedBuildEx queuedBuildEx4 : collection) {
                        try {
                            if (!object3.values().contains(queuedBuildEx4)) continue;
                            hashMap.put(object5.getBuildPromotion(), queuedBuildEx4.getBuildPromotion());
                            continue block39;
                        }
                        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                            throw BuildQueueImpl.a(buildTypeNotFoundException);
                        }
                    }
                }
                this.myBuildPromotionReplacementLog.rememberReplacements((Map)hashMap, BuildPromotionReplacementLog.ReplacementReason.BUILD_WITH_MORE_CHANGES_AVAILABLE);
                this.doApplyOrder(((HashSet)set2).toArray((String[])new String[0]), false);
                this.removeItems(set);
                list.removeAll(set);
            }
            object2 = set;
        }
        catch (Throwable throwable) {
            this.unlockBuilds(list);
            throw throwable;
        }
        try {
            this.unlockBuilds(list);
            object = object2;
            if (object == null) {
                BuildQueueImpl.$$$reportNull$$$0(110);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        return object;
    }

    @NotNull
    private List<QueuedBuildEx> findBuildsForMerging(@Nullable java.util.function.Predicate<SQueuedBuild> predicate) {
        ArrayList<QueuedBuildEx> arrayList;
        List<QueuedBuildEx> list = this.myQueue.get();
        ArrayList<QueuedBuildEx> arrayList2 = new ArrayList<QueuedBuildEx>();
        HashMap<QueuedBuildEx, Integer> hashMap = new HashMap<QueuedBuildEx, Integer>();
        int n = 0;
        for (QueuedBuildEx queuedBuildEx3 : list) {
            try {
                try {
                    if (predicate != null && !predicate.test((SQueuedBuild)queuedBuildEx3)) {
                        continue;
                    }
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw BuildQueueImpl.a(buildTypeNotFoundException);
                }
            }
            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                throw BuildQueueImpl.a(buildTypeNotFoundException);
            }
            ++n;
            BuildPromotionEx buildPromotionEx = queuedBuildEx3.getBuildPromotion();
            try {
                try {
                    try {
                        if (buildPromotionEx.isPersonal() || !queuedBuildEx3.isCanBeReplacedByMergeOptimization()) continue;
                    }
                    catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                        throw BuildQueueImpl.a(buildTypeNotFoundException);
                    }
                    if (!queuedBuildEx3.tryLockBuildStarting()) continue;
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw BuildQueueImpl.a(buildTypeNotFoundException);
                }
                arrayList2.add(queuedBuildEx3);
                hashMap.put(queuedBuildEx3, n);
            }
            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                throw BuildQueueImpl.a(buildTypeNotFoundException);
            }
        }
        try {
            arrayList2.sort((queuedBuildEx, queuedBuildEx2) -> {
                int n = (Integer)hashMap.get(queuedBuildEx);
                int n2 = (Integer)hashMap.get(queuedBuildEx2);
                return n - n2;
            });
            arrayList = arrayList2;
            if (arrayList == null) {
                BuildQueueImpl.$$$reportNull$$$0(111);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        return arrayList;
    }

    private String dumpReplacementMap(@NotNull Map<QueuedBuildEx, Set<QueuedBuildEx>> map) {
        String string;
        try {
            if (map == null) {
                BuildQueueImpl.$$$reportNull$$$0(112);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<QueuedBuildEx, Set<QueuedBuildEx>> entry : map.entrySet()) {
            stringBuilder.append(this.describeQueuedBuild(entry.getKey())).append(" <= ");
            for (QueuedBuildEx queuedBuildEx : entry.getValue()) {
                stringBuilder.append(this.describeQueuedBuild(queuedBuildEx)).append(", ");
            }
            stringBuilder.append("\n");
        }
        try {
            string = stringBuilder.length() > 0 ? stringBuilder.deleteCharAt(stringBuilder.length() - 1).toString() : stringBuilder.toString();
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        return string;
    }

    private String describeQueuedBuild(QueuedBuildEx queuedBuildEx) {
        return queuedBuildEx.getBuildTypeId() + " (promo id: " + queuedBuildEx.getBuildPromotion().getId() + ")";
    }

    private String describeQueuedBuilds(Collection<QueuedBuildEx> collection) {
        StringBuilder stringBuilder = new StringBuilder();
        for (QueuedBuildEx queuedBuildEx : collection) {
            stringBuilder.append(this.describeQueuedBuild(queuedBuildEx)).append(", ");
        }
        return stringBuilder.toString();
    }

    private void processStartedChains(@NotNull Map<QueuedBuildEx, Set<QueuedBuildEx>> map) {
        try {
            if (map == null) {
                BuildQueueImpl.$$$reportNull$$$0(113);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        for (Map.Entry<QueuedBuildEx, Set<QueuedBuildEx>> entry : map.entrySet()) {
            QueuedBuildEx queuedBuildEx = entry.getKey();
            if (!queuedBuildEx.getBuildPromotion().isPartOfBuildChain()) continue;
            try {
                if (!queuedBuildEx.getBuildPromotion().getDependedOnMe().isEmpty()) {
                    continue;
                }
            }
            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                throw BuildQueueImpl.a(buildTypeNotFoundException);
            }
            BuildPromotionEx buildPromotionEx = queuedBuildEx.getBuildPromotion();
            BuildDependencyGraph buildDependencyGraph = buildPromotionEx.getDependencyGraph();
            ChainRemainingTimeComparator chainRemainingTimeComparator = new ChainRemainingTimeComparator(buildDependencyGraph, this.myBuildDurationCalculator);
            HashSet<QueuedBuildEx> hashSet = new HashSet<QueuedBuildEx>();
            for (QueuedBuildEx queuedBuildEx2 : entry.getValue()) {
                String string;
                block17: {
                    BuildDependencyGraph buildDependencyGraph2 = queuedBuildEx2.getBuildPromotion().getDependencyGraph();
                    string = "such replacement would delay finishing of the chain";
                    try {
                        try {
                            if (chainRemainingTimeComparator.compareTo(buildDependencyGraph2) < 0) break block17;
                            if (!this.worseChainStatus((BuildPromotion)buildPromotionEx, buildDependencyGraph2.getNodesMap(), new HashSet<BuildPromotion>())) {
                                continue;
                            }
                        }
                        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                            throw BuildQueueImpl.a(buildTypeNotFoundException);
                        }
                    }
                    catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                        throw BuildQueueImpl.a(buildTypeNotFoundException);
                    }
                    string = "replacement chain has worse build status";
                }
                try {
                    hashSet.add(queuedBuildEx2);
                    if (!LOG.isDebugEnabled()) continue;
                    LOG.debug("Build chain " + this.describeQueuedBuild(queuedBuildEx2) + " removed from the list of candidates to replace: " + this.describeQueuedBuild(queuedBuildEx) + ", because " + string);
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw BuildQueueImpl.a(buildTypeNotFoundException);
                }
            }
            try {
                if (hashSet.isEmpty()) continue;
                entry.getValue().removeAll(hashSet);
            }
            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                throw BuildQueueImpl.a(buildTypeNotFoundException);
            }
        }
    }

    private boolean worseChainStatus(@NotNull BuildPromotion buildPromotion, @NotNull Map<String, BuildPromotion> map, @NotNull Set<BuildPromotion> set) {
        try {
            if (buildPromotion == null) {
                BuildQueueImpl.$$$reportNull$$$0(114);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        try {
            if (map == null) {
                BuildQueueImpl.$$$reportNull$$$0(115);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        try {
            if (set == null) {
                BuildQueueImpl.$$$reportNull$$$0(116);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        for (BuildDependency buildDependency : buildPromotion.getDependencies()) {
            BuildPromotion buildPromotion2 = buildDependency.getDependOn();
            try {
                if (!set.add(buildPromotion2)) {
                    continue;
                }
            }
            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                throw BuildQueueImpl.a(buildTypeNotFoundException);
            }
            SBuild sBuild = buildPromotion2.getAssociatedBuild();
            Status status = this.getStatus(sBuild, Status.NORMAL);
            BuildPromotion buildPromotion3 = map.get(buildPromotion2.getParentBuildTypeId());
            try {
                if (buildPromotion3 == null) {
                    continue;
                }
            }
            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                throw BuildQueueImpl.a(buildTypeNotFoundException);
            }
            SBuild sBuild2 = buildPromotion3.getAssociatedBuild();
            Status status2 = this.getStatus(sBuild2, Status.NORMAL);
            try {
                if (status2.above(status)) {
                    return true;
                }
            }
            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                throw BuildQueueImpl.a(buildTypeNotFoundException);
            }
            try {
                try {
                    try {
                        if (sBuild != null || sBuild2 != null) continue;
                    }
                    catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                        throw BuildQueueImpl.a(buildTypeNotFoundException);
                    }
                    if (!this.worseChainStatus(buildPromotion2, map, set)) continue;
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw BuildQueueImpl.a(buildTypeNotFoundException);
                }
                return true;
            }
            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                throw BuildQueueImpl.a(buildTypeNotFoundException);
            }
        }
        return false;
    }

    private Status getStatus(@Nullable SBuild sBuild, @NotNull Status status) {
        try {
            if (status == null) {
                BuildQueueImpl.$$$reportNull$$$0(117);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        try {
            if (sBuild == null) {
                return status;
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        try {
            if (sBuild.getBuildStatus().isFailed()) {
                return Status.FAILURE;
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        return Status.NORMAL;
    }

    private void removeBuildsWithoutCandidates(Map<QueuedBuildEx, Set<QueuedBuildEx>> map) {
        Iterator<QueuedBuildEx> iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            QueuedBuildEx queuedBuildEx = iterator.next();
            Set<QueuedBuildEx> set = map.get(queuedBuildEx);
            try {
                if (!set.isEmpty()) continue;
                iterator.remove();
            }
            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                throw BuildQueueImpl.a(buildTypeNotFoundException);
            }
        }
    }

    private void unlockBuilds(List<QueuedBuildEx> list) {
        for (QueuedBuildEx queuedBuildEx : list) {
            queuedBuildEx.unlockBuildStarting();
        }
    }

    @NotNull
    private Map<QueuedBuildEx, Set<QueuedBuildEx>> compactReplacementMap(Map<QueuedBuildEx, Set<QueuedBuildEx>> map) {
        HashMap<QueuedBuildEx, Set<QueuedBuildEx>> hashMap;
        boolean bl;
        HashMap<QueuedBuildEx, Set<QueuedBuildEx>> hashMap2 = new HashMap<QueuedBuildEx, Set<QueuedBuildEx>>();
        do {
            bl = false;
            Map<QueuedBuildEx, Set<QueuedBuildEx>> map2 = this.createCopy(map);
            for (Map.Entry<QueuedBuildEx, Set<QueuedBuildEx>> entry : map2.entrySet()) {
                try {
                    if (!map.containsKey(entry.getKey())) {
                        continue;
                    }
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw BuildQueueImpl.a(buildTypeNotFoundException);
                }
                Iterator<QueuedBuildEx> iterator = entry.getValue().iterator();
                while (iterator.hasNext()) {
                    QueuedBuildEx queuedBuildEx = iterator.next();
                    Set<QueuedBuildEx> set = map.remove(queuedBuildEx);
                    if (set == null) continue;
                    hashMap2.put(queuedBuildEx, new HashSet<QueuedBuildEx>(set));
                    iterator.remove();
                    set.remove(entry.getKey());
                    map.get(entry.getKey()).addAll(set);
                    bl = true;
                }
            }
        } while (bl);
        try {
            hashMap = hashMap2;
            if (hashMap == null) {
                BuildQueueImpl.$$$reportNull$$$0(118);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        return hashMap;
    }

    private Map<QueuedBuildEx, Set<QueuedBuildEx>> createCopy(Map<QueuedBuildEx, Set<QueuedBuildEx>> map) {
        HashMap<QueuedBuildEx, Set<QueuedBuildEx>> hashMap = new HashMap<QueuedBuildEx, Set<QueuedBuildEx>>();
        for (Map.Entry<QueuedBuildEx, Set<QueuedBuildEx>> entry : map.entrySet()) {
            hashMap.put(entry.getKey(), new HashSet(entry.getValue()));
        }
        return hashMap;
    }

    @NotNull
    private Collection<Set<QueuedBuildEx>> findChainedBuilds(@NotNull Collection<QueuedBuildEx> collection) {
        ArrayList<Set<QueuedBuildEx>> arrayList;
        try {
            if (collection == null) {
                BuildQueueImpl.$$$reportNull$$$0(119);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        ArrayList<Set<QueuedBuildEx>> arrayList2 = new ArrayList<Set<QueuedBuildEx>>();
        HashSet<BuildPromotionEx> hashSet = new HashSet<BuildPromotionEx>();
        for (QueuedBuildEx queuedBuildEx : collection) {
            BuildPromotionEx buildPromotionEx2 = queuedBuildEx.getBuildPromotion();
            try {
                if (!buildPromotionEx2.isPartOfBuildChain()) {
                    continue;
                }
            }
            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                throw BuildQueueImpl.a(buildTypeNotFoundException);
            }
            HashSet hashSet2 = new HashSet();
            for (BuildPromotionEx buildPromotionEx3 : buildPromotionEx2.findTops()) {
                try {
                    if (!hashSet.add(buildPromotionEx3)) {
                        continue;
                    }
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw BuildQueueImpl.a(buildTypeNotFoundException);
                }
                buildPromotionEx3.traverseSelfAndDependencies(buildPromotionEx -> {
                    QueuedBuildEx queuedBuildEx = buildPromotionEx.getQueuedBuild();
                    try {
                        if (queuedBuildEx == null) {
                            return DependencyConsumer.Result.CONTINUE;
                        }
                    }
                    catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                        throw BuildQueueImpl.a(buildTypeNotFoundException);
                    }
                    hashSet2.add(queuedBuildEx);
                    return DependencyConsumer.Result.CONTINUE;
                });
            }
            try {
                if (hashSet2.isEmpty()) continue;
                arrayList2.add(hashSet2);
            }
            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                throw BuildQueueImpl.a(buildTypeNotFoundException);
            }
        }
        try {
            arrayList = arrayList2;
            if (arrayList == null) {
                BuildQueueImpl.$$$reportNull$$$0(120);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        return arrayList;
    }

    @NotNull
    private Map<QueuedBuildEx, Set<QueuedBuildEx>> computeBuildsToReplace(@NotNull List<List<QueuedBuildEx>> list, @NotNull Function<QueuedBuildEx, BuildChainInfo> function) {
        LinkedHashMap<QueuedBuildEx, Set<QueuedBuildEx>> linkedHashMap;
        try {
            if (list == null) {
                BuildQueueImpl.$$$reportNull$$$0(121);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        try {
            if (function == null) {
                BuildQueueImpl.$$$reportNull$$$0(122);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        HashMap<QueuedBuildEx, QueuedBuildSettings> hashMap = new HashMap<QueuedBuildEx, QueuedBuildSettings>();
        LinkedHashMap<QueuedBuildEx, Set<QueuedBuildEx>> linkedHashMap2 = new LinkedHashMap<QueuedBuildEx, Set<QueuedBuildEx>>();
        for (List<QueuedBuildEx> list2 : list) {
            block13: for (int i = 0; i < list2.size(); ++i) {
                QueuedBuildEx queuedBuildEx2 = list2.get(i);
                int n = 0;
                while (true) {
                    block21: {
                        QueuedBuildEx queuedBuildEx3;
                        QueuedBuildEx queuedBuildEx4;
                        QueuedBuildEx queuedBuildEx5;
                        QueuedBuildEx queuedBuildEx6;
                        block22: {
                            block20: {
                                try {
                                    try {
                                        if (n >= list2.size()) continue block13;
                                        if (i < n) break block20;
                                        break block21;
                                    }
                                    catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                                        throw BuildQueueImpl.a(buildTypeNotFoundException);
                                    }
                                }
                                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                                    throw BuildQueueImpl.a(buildTypeNotFoundException);
                                }
                            }
                            queuedBuildEx6 = list2.get(n);
                            queuedBuildEx5 = this.getCanBeSubstituted(hashMap.computeIfAbsent(queuedBuildEx2, queuedBuildEx -> new QueuedBuildSettings(queuedBuildEx, (BuildChainInfo)function.apply((QueuedBuildEx)queuedBuildEx))), hashMap.computeIfAbsent(queuedBuildEx6, queuedBuildEx -> new QueuedBuildSettings(queuedBuildEx, (BuildChainInfo)function.apply((QueuedBuildEx)queuedBuildEx))));
                            try {
                                if (queuedBuildEx5 != null) break block22;
                                break block21;
                            }
                            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                                throw BuildQueueImpl.a(buildTypeNotFoundException);
                            }
                        }
                        if (queuedBuildEx5 == queuedBuildEx2) {
                            queuedBuildEx4 = queuedBuildEx2;
                            queuedBuildEx3 = queuedBuildEx6;
                        } else {
                            queuedBuildEx4 = queuedBuildEx6;
                            queuedBuildEx3 = queuedBuildEx2;
                        }
                        Set set = linkedHashMap2.computeIfAbsent(queuedBuildEx4, queuedBuildEx -> new HashSet());
                        set.add(queuedBuildEx3);
                    }
                    ++n;
                }
            }
        }
        try {
            linkedHashMap = linkedHashMap2;
            if (linkedHashMap == null) {
                BuildQueueImpl.$$$reportNull$$$0(123);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        return linkedHashMap;
    }

    @NotNull
    private Map<BuildPromotionEx, BuildChainInfo> createBuildChainsMap(@NotNull List<QueuedBuildEx> list) {
        HashMap<BuildPromotionEx, BuildChainInfo> hashMap;
        try {
            if (list == null) {
                BuildQueueImpl.$$$reportNull$$$0(124);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        HashMap<BuildPromotionEx, BuildChainInfo> hashMap2 = new HashMap<BuildPromotionEx, BuildChainInfo>();
        List list2 = DependenciesUtil.findAllTopPromotions((Collection)list.stream().map(queuedBuildEx -> queuedBuildEx.getBuildPromotion()).collect(Collectors.toList()));
        for (BuildPromotionEx buildPromotionEx : list2) {
            HashSet hashSet = new HashSet();
            BuildChainInfo buildChainInfo = new BuildChainInfo(hashSet);
            HashSet hashSet2 = new HashSet();
            buildPromotionEx.traverseSelfAndDependencies(buildPromotionEx2 -> {
                BuildChainInfo buildChainInfo2 = hashMap2.computeIfAbsent((BuildPromotionEx)buildPromotionEx2, buildPromotionEx -> buildChainInfo);
                try {
                    if (buildChainInfo2 != buildChainInfo) {
                        hashSet2.add(buildChainInfo2);
                    }
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw BuildQueueImpl.a(buildTypeNotFoundException);
                }
                hashSet.add(buildPromotionEx2.getParentBuildTypeId());
                return DependencyConsumer.Result.CONTINUE;
            });
            for (BuildChainInfo buildChainInfo2 : hashSet2) {
                hashSet.addAll(buildChainInfo2.myChainBuildTypeIds);
            }
            for (BuildChainInfo buildChainInfo2 : hashSet2) {
                buildChainInfo2.myChainBuildTypeIds.addAll(hashSet);
            }
        }
        try {
            hashMap = hashMap2;
            if (hashMap == null) {
                BuildQueueImpl.$$$reportNull$$$0(125);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        return hashMap;
    }

    @Nullable
    private QueuedBuildEx getCanBeSubstituted(@NotNull QueuedBuildSettings queuedBuildSettings, @NotNull QueuedBuildSettings queuedBuildSettings2) {
        BuildPromotionEx buildPromotionEx;
        BuildPromotionEx buildPromotionEx2;
        block66: {
            block64: {
                block63: {
                    block62: {
                        try {
                            if (queuedBuildSettings == null) {
                                BuildQueueImpl.$$$reportNull$$$0(126);
                            }
                        }
                        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                            throw BuildQueueImpl.a(buildTypeNotFoundException);
                        }
                        try {
                            if (queuedBuildSettings2 == null) {
                                BuildQueueImpl.$$$reportNull$$$0(127);
                            }
                        }
                        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                            throw BuildQueueImpl.a(buildTypeNotFoundException);
                        }
                        try {
                            if (!this.isSameAgentAssigned(queuedBuildSettings.getQueuedBuild(), queuedBuildSettings2.getQueuedBuild())) {
                                return null;
                            }
                        }
                        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                            throw BuildQueueImpl.a(buildTypeNotFoundException);
                        }
                        try {
                            try {
                                try {
                                    if (!queuedBuildSettings.isTriggeredByUser() || !queuedBuildSettings2.isTriggeredByUser()) break block62;
                                }
                                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                                    throw BuildQueueImpl.a(buildTypeNotFoundException);
                                }
                                if (Objects.equals(queuedBuildSettings.getTriggeredByUser(), queuedBuildSettings2.getTriggeredByUser())) break block62;
                            }
                            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                                throw BuildQueueImpl.a(buildTypeNotFoundException);
                            }
                            return null;
                        }
                        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                            throw BuildQueueImpl.a(buildTypeNotFoundException);
                        }
                    }
                    buildPromotionEx2 = queuedBuildSettings.getQueuedBuild().getBuildPromotion();
                    buildPromotionEx = queuedBuildSettings2.getQueuedBuild().getBuildPromotion();
                    try {
                        try {
                            if (!queuedBuildSettings.hasStartedParent() && !queuedBuildSettings2.hasStartedParent()) break block63;
                        }
                        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                            throw BuildQueueImpl.a(buildTypeNotFoundException);
                        }
                        return null;
                    }
                    catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                        throw BuildQueueImpl.a(buildTypeNotFoundException);
                    }
                }
                try {
                    block65: {
                        try {
                            try {
                                try {
                                    try {
                                        if (!TeamCityProperties.getBooleanOrTrue((String)"teamcity.queue.mergeBuilds.ignoreChainsWithoutChangesCollected") || !queuedBuildSettings.isPartOfBuildChain()) break block64;
                                    }
                                    catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                                        throw BuildQueueImpl.a(buildTypeNotFoundException);
                                    }
                                    if (!queuedBuildSettings2.isPartOfBuildChain()) break block64;
                                }
                                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                                    throw BuildQueueImpl.a(buildTypeNotFoundException);
                                }
                                if (queuedBuildSettings.isChangeCollectingNeeded()) break block65;
                            }
                            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                                throw BuildQueueImpl.a(buildTypeNotFoundException);
                            }
                            if (!queuedBuildSettings2.isChangeCollectingNeeded()) break block64;
                        }
                        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                            throw BuildQueueImpl.a(buildTypeNotFoundException);
                        }
                    }
                    return null;
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw BuildQueueImpl.a(buildTypeNotFoundException);
                }
            }
            try {
                if (!queuedBuildSettings.getInternalBranchName().equals(queuedBuildSettings2.getInternalBranchName())) {
                    return null;
                }
            }
            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                throw BuildQueueImpl.a(buildTypeNotFoundException);
            }
            try {
                if (!this.compareAttributes(buildPromotionEx2, buildPromotionEx)) {
                    return null;
                }
            }
            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                throw BuildQueueImpl.a(buildTypeNotFoundException);
            }
            try {
                if (!this.compareCustomArtifactDependencies(queuedBuildSettings, queuedBuildSettings2)) {
                    return null;
                }
            }
            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                throw BuildQueueImpl.a(buildTypeNotFoundException);
            }
            try {
                if (!queuedBuildSettings.getParameters().equals(queuedBuildSettings2.getParameters())) {
                    return null;
                }
            }
            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                throw BuildQueueImpl.a(buildTypeNotFoundException);
            }
            try {
                try {
                    try {
                        if (queuedBuildSettings.isChangeCollectingNeeded() || queuedBuildSettings2.isChangeCollectingNeeded()) break block66;
                    }
                    catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                        throw BuildQueueImpl.a(buildTypeNotFoundException);
                    }
                    if (queuedBuildSettings.getVcsRootEntries().equals(queuedBuildSettings2.getVcsRootEntries())) break block66;
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw BuildQueueImpl.a(buildTypeNotFoundException);
                }
                return null;
            }
            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                throw BuildQueueImpl.a(buildTypeNotFoundException);
            }
        }
        long l = this.normalizeModId(buildPromotionEx2.getLastModificationId());
        long l2 = this.normalizeModId(buildPromotionEx.getLastModificationId());
        if (l == l2) {
            QueuedBuildEx queuedBuildEx;
            Integer n = this.compareBuildChains(queuedBuildSettings, queuedBuildSettings2);
            try {
                if (n == null) {
                    return null;
                }
            }
            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                throw BuildQueueImpl.a(buildTypeNotFoundException);
            }
            try {
                if (n > 0) {
                    return this.getQueuedBuildIfTriggeredAutomatically(queuedBuildSettings2);
                }
            }
            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                throw BuildQueueImpl.a(buildTypeNotFoundException);
            }
            try {
                if (n < 0) {
                    return this.getQueuedBuildIfTriggeredAutomatically(queuedBuildSettings);
                }
            }
            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                throw BuildQueueImpl.a(buildTypeNotFoundException);
            }
            try {
                queuedBuildEx = buildPromotionEx2.getId() > buildPromotionEx.getId() ? queuedBuildSettings2.getQueuedBuild() : queuedBuildSettings.getQueuedBuild();
            }
            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                throw BuildQueueImpl.a(buildTypeNotFoundException);
            }
            return queuedBuildEx;
        }
        try {
            if (l < l2) {
                return this.getQueuedBuildIfTriggeredAutomatically(queuedBuildSettings);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        return this.getQueuedBuildIfTriggeredAutomatically(queuedBuildSettings2);
    }

    private boolean compareCustomArtifactDependencies(@NotNull QueuedBuildSettings queuedBuildSettings, @NotNull QueuedBuildSettings queuedBuildSettings2) {
        try {
            if (queuedBuildSettings == null) {
                BuildQueueImpl.$$$reportNull$$$0(128);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        try {
            if (queuedBuildSettings2 == null) {
                BuildQueueImpl.$$$reportNull$$$0(129);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        List list = queuedBuildSettings.getCustomArtifactDependencies().stream().filter(sArtifactDependency -> {
            boolean bl;
            try {
                bl = !this.isSelfReferencingDependency(queuedBuildSettings, (SArtifactDependency)sArtifactDependency);
            }
            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                throw BuildQueueImpl.a(buildTypeNotFoundException);
            }
            return bl;
        }).collect(Collectors.toList());
        List list2 = queuedBuildSettings2.getCustomArtifactDependencies().stream().filter(sArtifactDependency -> {
            boolean bl;
            try {
                bl = !this.isSelfReferencingDependency(queuedBuildSettings2, (SArtifactDependency)sArtifactDependency);
            }
            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                throw BuildQueueImpl.a(buildTypeNotFoundException);
            }
            return bl;
        }).collect(Collectors.toList());
        try {
            if (list.size() != list2.size()) {
                return false;
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        boolean bl = true;
        for (int i = 0; i < list.size(); ++i) {
            SArtifactDependency sArtifactDependency2;
            SArtifactDependency sArtifactDependency3 = (SArtifactDependency)list.get(i);
            if (sArtifactDependency3.isSimilarTo((ArtifactDependency)(sArtifactDependency2 = (SArtifactDependency)list2.get(i)))) continue;
            bl = false;
            break;
        }
        return bl;
    }

    private boolean isSelfReferencingDependency(QueuedBuildSettings queuedBuildSettings, SArtifactDependency sArtifactDependency) {
        boolean bl;
        block5: {
            block4: {
                BuildPromotionEx buildPromotionEx = queuedBuildSettings.getQueuedBuild().getBuildPromotion();
                RevisionRule revisionRule = sArtifactDependency.getRevisionRule();
                try {
                    try {
                        if (!revisionRule.getName().equals("buildId") || !revisionRule.getRevision().equals(buildPromotionEx.getId() + ".tcbuildid")) break block4;
                    }
                    catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                        throw BuildQueueImpl.a(buildTypeNotFoundException);
                    }
                    bl = true;
                    break block5;
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw BuildQueueImpl.a(buildTypeNotFoundException);
                }
            }
            bl = false;
        }
        return bl;
    }

    private boolean compareAttributes(@NotNull BuildPromotionEx buildPromotionEx, @NotNull BuildPromotionEx buildPromotionEx2) {
        try {
            if (buildPromotionEx == null) {
                BuildQueueImpl.$$$reportNull$$$0(130);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        try {
            if (buildPromotionEx2 == null) {
                BuildQueueImpl.$$$reportNull$$$0(131);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        for (String string : Arrays.asList("teamcity.cleanSources", "teamcity.addToFavorites", "teamcity.build.scheduled.time")) {
            Object object = buildPromotionEx.getAttribute(string);
            Object object2 = buildPromotionEx2.getAttribute(string);
            try {
                if (Objects.equals(object, object2)) continue;
                return false;
            }
            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                throw BuildQueueImpl.a(buildTypeNotFoundException);
            }
        }
        return true;
    }

    @Nullable
    private Integer compareBuildChains(@NotNull QueuedBuildSettings queuedBuildSettings, @NotNull QueuedBuildSettings queuedBuildSettings2) {
        Set set;
        Set set2;
        block26: {
            block25: {
                BuildChainInfo buildChainInfo;
                BuildChainInfo buildChainInfo2;
                block24: {
                    try {
                        if (queuedBuildSettings == null) {
                            BuildQueueImpl.$$$reportNull$$$0(132);
                        }
                    }
                    catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                        throw BuildQueueImpl.a(buildTypeNotFoundException);
                    }
                    try {
                        if (queuedBuildSettings2 == null) {
                            BuildQueueImpl.$$$reportNull$$$0(133);
                        }
                    }
                    catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                        throw BuildQueueImpl.a(buildTypeNotFoundException);
                    }
                    buildChainInfo2 = queuedBuildSettings.getChainInfo();
                    buildChainInfo = queuedBuildSettings2.getChainInfo();
                    try {
                        try {
                            if (buildChainInfo2 != null && buildChainInfo != null) break block24;
                        }
                        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                            throw BuildQueueImpl.a(buildTypeNotFoundException);
                        }
                        return null;
                    }
                    catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                        throw BuildQueueImpl.a(buildTypeNotFoundException);
                    }
                }
                try {
                    if (buildChainInfo2 == buildChainInfo) {
                        return null;
                    }
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw BuildQueueImpl.a(buildTypeNotFoundException);
                }
                set2 = buildChainInfo2.getChainBuildTypeIds();
                set = buildChainInfo.getChainBuildTypeIds();
                try {
                    try {
                        if (set2.size() <= set.size() || !set2.containsAll(set)) break block25;
                    }
                    catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                        throw BuildQueueImpl.a(buildTypeNotFoundException);
                    }
                    return 1;
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw BuildQueueImpl.a(buildTypeNotFoundException);
                }
            }
            try {
                try {
                    if (set.size() <= set2.size() || !set.containsAll(set2)) break block26;
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw BuildQueueImpl.a(buildTypeNotFoundException);
                }
                return -1;
            }
            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                throw BuildQueueImpl.a(buildTypeNotFoundException);
            }
        }
        try {
            if (set2.equals(set)) {
                return 0;
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        return null;
    }

    private boolean isSameAgentAssigned(@NotNull QueuedBuildEx queuedBuildEx, @NotNull QueuedBuildEx queuedBuildEx2) {
        try {
            if (queuedBuildEx == null) {
                BuildQueueImpl.$$$reportNull$$$0(134);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        try {
            if (queuedBuildEx2 == null) {
                BuildQueueImpl.$$$reportNull$$$0(135);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        return BuildQueueImpl.areTheSame((AgentRestrictor)queuedBuildEx.getAgentRestrictor(), (AgentRestrictor)queuedBuildEx2.getAgentRestrictor());
    }

    private static boolean areTheSame(@Nullable AgentRestrictor agentRestrictor, @Nullable AgentRestrictor agentRestrictor2) {
        boolean bl;
        block12: {
            block13: {
                block10: {
                    block11: {
                        try {
                            try {
                                if (agentRestrictor != null) break block10;
                                if (agentRestrictor2 != null) break block11;
                            }
                            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                                throw BuildQueueImpl.a(buildTypeNotFoundException);
                            }
                            bl = true;
                            break block12;
                        }
                        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                            throw BuildQueueImpl.a(buildTypeNotFoundException);
                        }
                    }
                    bl = false;
                    break block12;
                }
                try {
                    try {
                        try {
                            if (agentRestrictor2 == null || agentRestrictor.getType().getTypeId() != agentRestrictor2.getType().getTypeId()) break block13;
                        }
                        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                            throw BuildQueueImpl.a(buildTypeNotFoundException);
                        }
                        if (agentRestrictor.getId() != agentRestrictor2.getId()) break block13;
                    }
                    catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                        throw BuildQueueImpl.a(buildTypeNotFoundException);
                    }
                    bl = true;
                    break block12;
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw BuildQueueImpl.a(buildTypeNotFoundException);
                }
            }
            bl = false;
        }
        return bl;
    }

    private long normalizeModId(Long l) {
        long l2;
        try {
            l2 = l == null ? Long.MAX_VALUE : l;
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        return l2;
    }

    @Nullable
    private QueuedBuildEx getQueuedBuildIfTriggeredAutomatically(@NotNull QueuedBuildSettings queuedBuildSettings) {
        QueuedBuildEx queuedBuildEx;
        try {
            if (queuedBuildSettings == null) {
                BuildQueueImpl.$$$reportNull$$$0(136);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        try {
            queuedBuildEx = queuedBuildSettings.isTriggeredByUser() ? null : queuedBuildSettings.getQueuedBuild();
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        return queuedBuildEx;
    }

    @NotNull
    private List<List<QueuedBuildEx>> divideByGroups(@NotNull List<QueuedBuildEx> list) {
        Object object;
        try {
            if (list == null) {
                BuildQueueImpl.$$$reportNull$$$0(137);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        LinkedHashMap<String, List> linkedHashMap = new LinkedHashMap<String, List>();
        for (QueuedBuildEx queuedBuildEx : list) {
            String string2 = queuedBuildEx.getBuildTypeId() + ":" + queuedBuildEx.getBuildPromotion().getInternalBranchName();
            List list3 = linkedHashMap.computeIfAbsent(string2, string -> new ArrayList());
            list3.add(queuedBuildEx);
        }
        ArrayList arrayList = new ArrayList();
        try {
            linkedHashMap.forEach((string, list2) -> {
                try {
                    if (list2.size() > 1) {
                        arrayList.add(list2);
                    }
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw BuildQueueImpl.a(buildTypeNotFoundException);
                }
            });
            object = arrayList;
            if (object == null) {
                BuildQueueImpl.$$$reportNull$$$0(138);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        return object;
    }

    private int calcQueueSignature() {
        long l = 0L;
        for (QueuedBuildEx queuedBuildEx : this.myQueue.get()) {
            l = 31L * l + queuedBuildEx.getBuildPromotion().getId();
        }
        return Long.hashCode(l);
    }

    @NotNull
    public TriggerTask newTriggerTask(@NotNull BuildPromotion buildPromotion) {
        try {
            if (buildPromotion == null) {
                BuildQueueImpl.$$$reportNull$$$0(139);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        return new BatchTriggerTaskImpl(buildPromotion);
    }

    @NotNull
    public List<SQueuedBuild> processTasks(@NotNull Collection<TriggerTask> collection, @NotNull String string) {
        SAgentType sAgentType;
        Object object;
        Object object2;
        Object object3;
        try {
            if (collection == null) {
                BuildQueueImpl.$$$reportNull$$$0(140);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        try {
            if (string == null) {
                BuildQueueImpl.$$$reportNull$$$0(141);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        HashMap<BuildPromotionEx, SAgentRestrictor> hashMap = new HashMap<BuildPromotionEx, SAgentRestrictor>();
        for (TriggerTask object42 : collection) {
            object3 = object42.getBuildPromotion();
            object2 = null;
            object = object42.getAgentToRunOn();
            AgentPool agentPool = object42.getAgentPoolToRunOn();
            sAgentType = object42.getAgentTypeToRunOn();
            if (sAgentType != null) {
                object2 = this.myAgentRestrictorFactory.createFor(AgentRestrictorType.CLOUD_IMAGE, sAgentType.getAgentTypeId());
            } else if (object != null) {
                object2 = this.myAgentRestrictorFactory.createFor(AgentRestrictorType.SINGLE_AGENT, object.getId());
            } else if (agentPool != null) {
                object2 = this.myAgentRestrictorFactory.createFor(AgentRestrictorType.AGENT_POOL, agentPool.getAgentPoolId());
            }
            hashMap.put((BuildPromotionEx)object3, (SAgentRestrictor)object2);
        }
        List<QueuedBuildEx> list = this.addToQueue(hashMap, string);
        ArrayList<SAgentType> arrayList = new ArrayList<SAgentType>();
        object3 = new HashMap();
        object2 = list.iterator();
        while (object2.hasNext()) {
            object = (QueuedBuildEx)object2.next();
            object3.put(object.getBuildPromotion(), object);
        }
        object2 = new LinkedHashSet();
        for (TriggerTask triggerTask : collection) {
            sAgentType = (QueuedBuildEx)object3.get(triggerTask.getBuildPromotion());
            try {
                if (sAgentType == null) {
                    continue;
                }
            }
            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                throw BuildQueueImpl.a(buildTypeNotFoundException);
            }
            try {
                if (triggerTask.isMoveToTheQueueTop()) {
                    object2.add(sAgentType.getItemId());
                }
            }
            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                throw BuildQueueImpl.a(buildTypeNotFoundException);
            }
            try {
                if (!triggerTask.isCollectChangesEarly()) continue;
                arrayList.add(sAgentType);
            }
            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                throw BuildQueueImpl.a(buildTypeNotFoundException);
            }
        }
        if (!object2.isEmpty()) {
            for (SQueuedBuild sQueuedBuild : this.getItems()) {
                object2.add(sQueuedBuild.getItemId());
            }
            this.applyOrderAndSendEvent(object2.toArray(new String[0]));
        }
        try {
            if (!arrayList.isEmpty()) {
                this.myQueuedBuildsChangesCollector.startChangesCollecting(arrayList);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        return new ArrayList<SQueuedBuild>(list);
    }

    public void moveDependenciesToTop(@NotNull BuildPromotion buildPromotion) {
        try {
            if (buildPromotion == null) {
                BuildQueueImpl.$$$reportNull$$$0(142);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        QueuedBuildEx queuedBuildEx = this.findQueued(buildPromotion);
        this.changeOrderAndSendEvent((ChangeOrderAction)new MoveDependenciesToTop(this, queuedBuildEx, buildPromotion), false);
    }

    private void fireQueueOrderChanged() {
        this.scheduleEvent(new EventInfo(EventType.QUEUE_ORDER_CHANGED, null, null, null));
    }

    public boolean hasPendingReloadTask() {
        return (Boolean)this.myNodesSynchronizationTask.myNeedToReload.getReference();
    }

    public void scheduleBuildQueueReload() {
        this.myNodesSynchronizationTask.publishEvent();
        this.myNodesSynchronizationTask.setNeedToReload();
    }

    private static /* synthetic */ int lambda$tryLockDependingPromotions$52(BuildPromotionEx buildPromotionEx, BuildPromotionEx buildPromotionEx2) {
        return Long.compare(buildPromotionEx.getId(), buildPromotionEx2.getId());
    }

    private static /* synthetic */ BuildPromotionEx lambda$tryLockDependingPromotions$51(BuildDependencyEx buildDependencyEx) {
        return buildDependencyEx.getDependent();
    }

    private static /* synthetic */ boolean lambda$tryToSubstituteQueueFromDB$36(Set set, QueuedBuildEx queuedBuildEx) {
        boolean bl;
        try {
            bl = !set.contains(queuedBuildEx.getBuildPromotion().getId());
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        return bl;
    }

    private /* synthetic */ void lambda$reloadQueueFromDB$32(QueuedBuildEx queuedBuildEx) {
        this.scheduleEvent(new EventInfo(EventType.REMOVED, (SQueuedBuild)queuedBuildEx, null, "remove from queue on another node"));
    }

    private static /* synthetic */ DependencyConsumer.Result lambda$createDependenciesEdgesForQueuedBuilds$31(Map map, Map map2, BuildPromotion buildPromotion, List list, BuildPromotionEx buildPromotionEx) {
        QueuedBuildEx queuedBuildEx = (QueuedBuildEx)map.get(buildPromotionEx.getId());
        if (queuedBuildEx != null) {
            Iterator iterator = ((List)map2.get(buildPromotion.getId())).iterator();
            while (iterator.hasNext()) {
                int n = (Integer)iterator.next();
                Pair pair = (Pair)list.get(n);
                list.set(n, Pair.create((Object)queuedBuildEx.getItemId(), (Object)((String)pair.getSecond())));
            }
            return DependencyConsumer.Result.STOP;
        }
        return DependencyConsumer.Result.CONTINUE;
    }

    private static /* synthetic */ boolean lambda$terminateQueuedBuild$22(QueuedBuildEx queuedBuildEx) {
        return queuedBuildEx.tryLockBuildStarting();
    }

    private static /* synthetic */ boolean lambda$terminateQueuedBuild$21(BuildProblemData buildProblemData) {
        return "Dependency build configuration does not exist".equals(buildProblemData.getDescription());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Could not resolve type clashes
     * Unable to fully structure code
     */
    private /* synthetic */ void lambda$doAddToQueue$5(Map var1_1, String var2_2, Date var3_3, List var4_4, List var5_5) {
        var6_6 = new HashSet<BuildPromotionEx>();
        var7_7 = new LinkedHashMap<QueuedBuildEx, Iterator<Map.Entry<K, V>>>();
        var8_8 = new ArrayList<QueuedBuildEx>();
        var9_9 = BuildQueueImpl.findEndpoints(var1_1.keySet());
        for (Object var11_11 : var9_9) {
            var12_14 /* !! */  = (SAgentRestrictor)var1_1.get(var11_11);
            var13_15 = this.getDependentBuildsTriggeredBy(var2_2, (BuildPromotion)var11_11);
            var14_16 = new ArrayList<E>();
            var15_17 = this.collectChildrenNonQueuedBuilds((BuildPromotionEx)var11_11);
            var16_18 = var15_17.iterator();
            while (var16_18.hasNext()) {
                var17_19 = var16_18.next();
                try {
                    if (!var6_6.add((BuildPromotionEx)var17_19)) continue;
                    var14_16.add((QueuedBuildEx)this.createQueuedBuild((BuildPromotionEx)var17_19, (SAgentRestrictor)var1_1.get(var17_19), (String)var13_15, var3_3));
                }
                catch (BuildPromotionDeletedException v0) {
                    throw BuildQueueImpl.a(v0);
                }
            }
            var16_18 = this.createQueuedBuild((BuildPromotionEx)var11_11, var12_14 /* !! */ , var2_2, var3_3);
            var7_7.put(var16_18, var14_16);
            var8_8.add(var16_18);
            var8_8.addAll((Collection<QueuedBuildEx>)var14_16);
        }
        this.checkPersonalBuildsAlreadyAdded(var8_8);
        var10_10 = var7_7.keySet().iterator();
        while (var10_10.hasNext()) {
            block35: {
                var11_11 = (QueuedBuildEx)var10_10.next();
                try {
                    if (var11_11.isPersonal() || var11_11.getBuildPromotion().getNumberOfDependedOnMe() != 0) continue;
                }
                catch (BuildPromotionDeletedException v1) {
                    throw BuildQueueImpl.a(v1);
                }
                var12_14 /* !! */  = this.findSimilarItem((QueuedBuildEx)var11_11);
                if (var12_14 /* !! */  == null) continue;
                try {
                    block37: {
                        if (!BuildQueueImpl.LOG.isDebugEnabled()) break block35;
                        break block37;
                        catch (BuildPromotionDeletedException v2) {
                            throw BuildQueueImpl.a(v2);
                        }
                    }
                    BuildQueueImpl.LOG.debug("Newly triggered build queue item: " + String.valueOf(var11_11) + " ignored because there is a similar item in the queue: " + String.valueOf(var12_14 /* !! */ ));
                }
                catch (BuildPromotionDeletedException v3) {
                    throw BuildQueueImpl.a(v3);
                }
            }
            var4_4.add(var12_14 /* !! */ );
            var10_10.remove();
        }
        try {
            for (SAgentRestrictor var12_14 : var7_7.entrySet()) {
                var13_15 = this.getItemsToAdd((Map.Entry<QueuedBuildEx, List<QueuedBuildEx>>)var12_14 /* !! */ );
                var14_16 = var13_15.iterator();
                while (var14_16.hasNext()) {
                    var15_17 = (QueuedBuildEx)var14_16.next();
                    var15_17.getBuildPromotion().persist();
                }
                var14_16 = var13_15.iterator();
                while (var14_16.hasNext()) {
                    var15_17 = (QueuedBuildEx)var14_16.next();
                    var15_17.getBuildPromotion().resetDependenciesHardRefs();
                }
            }
        }
        catch (BuildPromotionDeletedException var11_12) {
            var7_7.values().forEach((Consumer<List>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)V, lambda$doAddToQueue$2(java.util.List ), (Ljava/util/List;)V)());
            throw var11_12;
        }
        var11_13 = false;
        var12_14 /* !! */  = null;
        var13_15 = this.myQueue;
        synchronized (var13_15) {
            block36: {
                block38: {
                    for (Object var15_17 : var7_7.entrySet()) {
                        var16_18 = (QueuedBuildEx)var15_17.getKey();
                        var17_19 = this.getItemsToAdd((Map.Entry<QueuedBuildEx, List<QueuedBuildEx>>)var15_17).stream().filter((java.util.function.Predicate<QueuedBuildEx>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)Z, lambda$doAddToQueue$3(jetbrains.buildServer.serverSide.QueuedBuildEx ), (Ljetbrains/buildServer/serverSide/QueuedBuildEx;)Z)((BuildQueueImpl)this)).collect(Collectors.toList());
                        try {
                            if (var17_19.isEmpty()) {
                                continue;
                            }
                        }
                        catch (BuildPromotionDeletedException v4) {
                            throw BuildQueueImpl.a(v4);
                        }
                        try {
                            var18_20 = this.myOrderingStrategy.addBuilds(new ArrayList<E>(var17_19), this.getItems());
                        }
                        catch (Throwable var19_21) {
                            BuildQueueImpl.LOG.error("Error while changing build order, work as if no reordering was performed", var19_21);
                            var18_20 = Collections.emptyList();
                        }
                        this.storeItems((List<QueuedBuildEx>)var17_19);
                        var17_19.forEach((Consumer<QueuedBuildEx>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)V, lambda$doAddToQueue$4(jetbrains.buildServer.serverSide.QueuedBuildEx ), (Ljetbrains/buildServer/serverSide/QueuedBuildEx;)V)((BuildQueueImpl)this));
                        if (!var18_20.isEmpty()) {
                            this.doApplyOrder(BuildQueueImpl.getBuildIds(var18_20).toArray(new String[var18_20.size()]), true);
                            var11_13 = true;
                        }
                        var5_5.addAll(var17_19);
                        var4_4.add(var16_18);
                    }
                    if (var5_5.isEmpty()) break block36;
                    this.persistAddedAndRemovedItems(var5_5, Collections.emptyList());
                    if (var11_13) ** GOTO lbl118
                    break block38;
                    catch (BuildPromotionDeletedException v5) {
                        throw BuildQueueImpl.a(v5);
                    }
                }
                try {
                    block39: {
                        if (!this.shouldAlwaysPersistOrderWhenNewBuildsAdded()) break block36;
                        break block39;
                        catch (BuildPromotionDeletedException v6) {
                            throw BuildQueueImpl.a(v6);
                        }
                    }
                    if (this.persistCurrentQueueOrder()) break block36;
                }
                catch (BuildPromotionDeletedException v7) {
                    throw BuildQueueImpl.a(v7);
                }
                var12_14 /* !! */  = new ApplyCurrentOrder(this);
            }
        }
        try {
            if (!var5_5.isEmpty()) {
                this.changeOrderAndSendEvent((ChangeOrderAction)var12_14 /* !! */ , true);
            }
        }
        catch (BuildPromotionDeletedException v8) {
            throw BuildQueueImpl.a(v8);
        }
    }

    private /* synthetic */ void lambda$doAddToQueue$4(QueuedBuildEx queuedBuildEx) {
        this.myWaitReasonStatisticsCollector.onBuildAddedToQueue((SQueuedBuild)queuedBuildEx);
    }

    private /* synthetic */ boolean lambda$doAddToQueue$3(QueuedBuildEx queuedBuildEx) {
        boolean bl;
        try {
            bl = !this.myQueueIndex.get().containsKey(queuedBuildEx.getBuildPromotion().getId());
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        return bl;
    }

    private static /* synthetic */ void lambda$doAddToQueue$2(List list) {
        for (QueuedBuildEx queuedBuildEx : list) {
            queuedBuildEx.getBuildPromotion().markAsDeleted();
        }
    }

    static {
        boolean bl;
        try {
            bl = !BuildQueueImpl.class.desiredAssertionStatus();
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildQueueImpl.a(buildTypeNotFoundException);
        }
        $assertionsDisabled = bl;
        PARAMETER_PREFIXES_REQUIRING_CHANGES_COLLECTING = new HashSet<String>();
        PARAMETER_PREFIXES_REQUIRING_CHANGES_COLLECTING.add("teamcity.build.branch");
        PARAMETER_PREFIXES_REQUIRING_CHANGES_COLLECTING.add("teamcity.build.branch.is_default");
        PARAMETER_PREFIXES_REQUIRING_CHANGES_COLLECTING.add("teamcity.build.vcs.branch");
        PARAMETER_PREFIXES_REQUIRING_CHANGES_COLLECTING.add("system.teamcity.build.checkoutDir");
        PARAMETER_PREFIXES_REQUIRING_CHANGES_COLLECTING.add("build.vcs.number");
        PARAMETER_PREFIXES_REQUIRING_CHANGES_COLLECTING.add("BUILD_VCS_NUMBER");
        PARAMETER_PREFIXES_REQUIRING_CHANGES_COLLECTING.add("system.build.vcs.number");
        LOG = Logger.getInstance((String)BuildQueueImpl.class.getName());
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
