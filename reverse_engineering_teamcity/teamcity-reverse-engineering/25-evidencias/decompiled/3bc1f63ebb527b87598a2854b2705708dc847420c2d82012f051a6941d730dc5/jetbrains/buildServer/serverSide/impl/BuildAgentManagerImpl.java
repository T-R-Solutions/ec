/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.annotations.VisibleForTesting
 *  com.intellij.openapi.diagnostic.Logger
 *  jetbrains.buildServer.AlreadyExistsException
 *  jetbrains.buildServer.Build
 *  jetbrains.buildServer.BuildAgent
 *  jetbrains.buildServer.BuildTypeDescriptor
 *  jetbrains.buildServer.DevelopmentMode
 *  jetbrains.buildServer.InvalidRunningBuildException
 *  jetbrains.buildServer.LicenseNotGrantedException
 *  jetbrains.buildServer.TeamCityRuntimeException
 *  jetbrains.buildServer.Used
 *  jetbrains.buildServer.agentServer.AgentDetails
 *  jetbrains.buildServer.log.LogInitializer
 *  jetbrains.buildServer.log.Loggable
 *  jetbrains.buildServer.log.Loggers
 *  jetbrains.buildServer.serverSide.AgentCannotBeRemovedException
 *  jetbrains.buildServer.serverSide.AgentCompatibility
 *  jetbrains.buildServer.serverSide.AgentDescription
 *  jetbrains.buildServer.serverSide.AgentDistributionMonitor
 *  jetbrains.buildServer.serverSide.AgentPersister
 *  jetbrains.buildServer.serverSide.BuildAgentEx
 *  jetbrains.buildServer.serverSide.BuildAgentFactory
 *  jetbrains.buildServer.serverSide.BuildAgentManager$AgentGroup
 *  jetbrains.buildServer.serverSide.BuildAgentManager$RunConfigurationPolicy
 *  jetbrains.buildServer.serverSide.BuildAgentManagerEx
 *  jetbrains.buildServer.serverSide.BuildServerListener
 *  jetbrains.buildServer.serverSide.MissingServerResponsibilityException
 *  jetbrains.buildServer.serverSide.MultiNodesEvents
 *  jetbrains.buildServer.serverSide.MultiNodesEvents$EventData
 *  jetbrains.buildServer.serverSide.RunningBuildEx
 *  jetbrains.buildServer.serverSide.SBuildAgent
 *  jetbrains.buildServer.serverSide.SRunningBuild
 *  jetbrains.buildServer.serverSide.ServerResponsibilityEx
 *  jetbrains.buildServer.serverSide.TeamCityProperties
 *  jetbrains.buildServer.serverSide.UnregisteredAgentsManager
 *  jetbrains.buildServer.serverSide.agentPools.AgentPoolManager
 *  jetbrains.buildServer.serverSide.agentTypes.AgentTypeDatabaseStorage
 *  jetbrains.buildServer.serverSide.agentTypes.AgentTypeManager
 *  jetbrains.buildServer.serverSide.agentTypes.AgentTypeStorage
 *  jetbrains.buildServer.serverSide.audit.ActionType
 *  jetbrains.buildServer.serverSide.audit.AuditLogFactory
 *  jetbrains.buildServer.serverSide.comments.Comment
 *  jetbrains.buildServer.serverSide.db.InParams
 *  jetbrains.buildServer.serverSide.db.SQLRunnerEx
 *  jetbrains.buildServer.serverSide.executors.ExecutorServices
 *  jetbrains.buildServer.serverSide.impl.AgentData
 *  jetbrains.buildServer.serverSide.impl.AutomaticAgentAuthorizationTokens
 *  jetbrains.buildServer.serverSide.impl.BuildAgentManagerImpl$AgentVersions
 *  jetbrains.buildServer.serverSide.impl.BuildAgentManagerImpl$OtherNodeEventsHandler
 *  jetbrains.buildServer.serverSide.impl.BuildAgentUpgrader
 *  jetbrains.buildServer.serverSide.impl.IndexedCollection
 *  jetbrains.buildServer.serverSide.impl.IndexedCollection$CollectionListener
 *  jetbrains.buildServer.serverSide.impl.LogUtil
 *  jetbrains.buildServer.serverSide.impl.RunningBuildsManagerEx
 *  jetbrains.buildServer.serverSide.impl.agent.PollingRemoteAgentConnection
 *  jetbrains.buildServer.users.SUser
 *  jetbrains.buildServer.users.User
 *  jetbrains.buildServer.util.CollectionsUtil
 *  jetbrains.buildServer.util.EventDispatcher
 *  jetbrains.buildServer.util.NamedThreadFactory
 *  jetbrains.buildServer.util.StringUtil
 *  jetbrains.buildServer.util.SystemTimeService
 *  jetbrains.buildServer.util.TimeService
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package jetbrains.buildServer.serverSide.impl;

import com.google.common.annotations.VisibleForTesting;
import com.intellij.openapi.diagnostic.Logger;
import java.lang.invoke.LambdaMetafactory;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.EventListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Collectors;
import jetbrains.buildServer.AlreadyExistsException;
import jetbrains.buildServer.Build;
import jetbrains.buildServer.BuildAgent;
import jetbrains.buildServer.BuildTypeDescriptor;
import jetbrains.buildServer.DevelopmentMode;
import jetbrains.buildServer.InvalidRunningBuildException;
import jetbrains.buildServer.LicenseNotGrantedException;
import jetbrains.buildServer.TeamCityRuntimeException;
import jetbrains.buildServer.Used;
import jetbrains.buildServer.agentServer.AgentDetails;
import jetbrains.buildServer.log.LogInitializer;
import jetbrains.buildServer.log.Loggable;
import jetbrains.buildServer.log.Loggers;
import jetbrains.buildServer.serverSide.AgentCannotBeRemovedException;
import jetbrains.buildServer.serverSide.AgentCompatibility;
import jetbrains.buildServer.serverSide.AgentDescription;
import jetbrains.buildServer.serverSide.AgentDistributionMonitor;
import jetbrains.buildServer.serverSide.AgentPersister;
import jetbrains.buildServer.serverSide.BuildAgentEx;
import jetbrains.buildServer.serverSide.BuildAgentFactory;
import jetbrains.buildServer.serverSide.BuildAgentManager;
import jetbrains.buildServer.serverSide.BuildAgentManagerEx;
import jetbrains.buildServer.serverSide.BuildServerListener;
import jetbrains.buildServer.serverSide.MissingServerResponsibilityException;
import jetbrains.buildServer.serverSide.MultiNodesEvents;
import jetbrains.buildServer.serverSide.RunningBuildEx;
import jetbrains.buildServer.serverSide.SBuildAgent;
import jetbrains.buildServer.serverSide.SRunningBuild;
import jetbrains.buildServer.serverSide.ServerResponsibilityEx;
import jetbrains.buildServer.serverSide.TeamCityProperties;
import jetbrains.buildServer.serverSide.UnregisteredAgentsManager;
import jetbrains.buildServer.serverSide.agentPools.AgentPoolManager;
import jetbrains.buildServer.serverSide.agentTypes.AgentTypeDatabaseStorage;
import jetbrains.buildServer.serverSide.agentTypes.AgentTypeManager;
import jetbrains.buildServer.serverSide.agentTypes.AgentTypeStorage;
import jetbrains.buildServer.serverSide.audit.ActionType;
import jetbrains.buildServer.serverSide.audit.AuditLogFactory;
import jetbrains.buildServer.serverSide.comments.Comment;
import jetbrains.buildServer.serverSide.db.InParams;
import jetbrains.buildServer.serverSide.db.SQLRunnerEx;
import jetbrains.buildServer.serverSide.executors.ExecutorServices;
import jetbrains.buildServer.serverSide.impl.AgentData;
import jetbrains.buildServer.serverSide.impl.AutomaticAgentAuthorizationTokens;
import jetbrains.buildServer.serverSide.impl.BuildAgentManagerImpl;
import jetbrains.buildServer.serverSide.impl.BuildAgentUpgrader;
import jetbrains.buildServer.serverSide.impl.IndexedCollection;
import jetbrains.buildServer.serverSide.impl.LogUtil;
import jetbrains.buildServer.serverSide.impl.RunningBuildsManagerEx;
import jetbrains.buildServer.serverSide.impl.agent.PollingRemoteAgentConnection;
import jetbrains.buildServer.users.SUser;
import jetbrains.buildServer.users.User;
import jetbrains.buildServer.util.CollectionsUtil;
import jetbrains.buildServer.util.EventDispatcher;
import jetbrains.buildServer.util.NamedThreadFactory;
import jetbrains.buildServer.util.StringUtil;
import jetbrains.buildServer.util.SystemTimeService;
import jetbrains.buildServer.util.TimeService;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Exception performing whole class analysis ignored.
 */
public class BuildAgentManagerImpl
implements BuildAgentManagerEx {
    private static final Comparator<BuildAgent> AGENTS_COMPARATOR;
    public static final String AGENT_WILL_UPGRADE = "Agent has unregistered (will upgrade)";
    public static final String TEAMCITY_REGISTER_AGENTS_ON_SECONDARY_NODES = "teamcity.agent.registerOnSecondaryNodes";
    public static final String TEAMCITY_UNREGISTER_AGENTS_ON_SECONDARY_NODES = "teamcity.agent.unregisterOnSecondaryNodes";
    public static final String TEAMCITY_UPDATE_AGENT_PARAMS_ON_SECONDARY_NODES = "teamcity.agent.updateParameters.allowOnSecondaryNodes";
    public static final String AGENT_PING_GRACE_PERIOD_PROP = "teamcity.agent.ping.gracePeriodAfterStartup.millis";
    public static final String TEAMCITY_POLL_AGENTS_FROM_DB_ENABLED = "teamcity.agent.pollFromDB.enabled";
    public static final String TEAMCITY_POLL_AGENTS_FROM_DB_DELAY = "teamcity.agent.pollFromDB.delay";
    public static final String AGENT_COMMANDS_REQUIRE_AUTHORIZATION_PROP = "teamcity.agent.commands.requireAuthorization";
    public static final long TEAMCITY_POLL_AGENTS_FROM_DB_DELAY_DEFAULT_MILLIS = 20000L;
    public static final String TEAMCITY_POLL_AGENTS_FROM_DB_BATCH_SIZE = "teamcity.agent.pollFromDB.batchSize";
    public static final String SERVER_INITIALIZING_REASON = "Build agent cannot be registered because TeamCity server is still initializing. Try again later.";
    @NotNull
    private final ServerResponsibilityEx myServerResponsibility;
    @NotNull
    private final MultiNodesEvents myMultiNodesEvents;
    @NotNull
    private final ExecutorServices myExecutorServices;
    @NotNull
    private final SQLRunnerEx mySqlRunner;
    private final IndexedCollection<Integer, BuildAgentEx> myAgents;
    private final ConcurrentHashMap<String, Integer> myAgentNames;
    private final Map<Integer, Long> myAgentsWithFailedPing;
    private final Map<String, BuildAgentEx> myProcessingAuthTokens;
    private UnregisteredAgentsManager myUnregisteredAgentsManager;
    private AgentPersister myAgentPersister;
    private EventDispatcher<BuildServerListener> myDispatcher;
    private BuildAgentUpgrader myAgentUpgrader;
    private RunningBuildsManagerEx myRunningBuildManager;
    private AgentTypeManager myAgentTypeManager;
    private AgentTypeStorage myAgentTypeStorage;
    private AgentDistributionMonitor myAgentDistributionMonitor;
    private BuildAgentFactory myBuildAgentFactory;
    private AuditLogFactory myAuditLogFactory;
    private AutomaticAgentAuthorizationTokens myAutomaticAgentAuthorizationTokens;
    private AgentPoolManager myAgentPoolManager;
    private final AtomicInteger myConcurrentAgentRegistrationsNumber;
    private volatile TimeService myTimeService;
    private volatile long myServerStartTime;
    private volatile long myLastSuccessfulPollTimestamp;
    static final /* synthetic */ boolean $assertionsDisabled;

    public BuildAgentManagerImpl(@NotNull SQLRunnerEx sQLRunnerEx, @NotNull ServerResponsibilityEx serverResponsibilityEx, @NotNull EventDispatcher<BuildServerListener> eventDispatcher, @NotNull MultiNodesEvents multiNodesEvents, @NotNull ExecutorServices executorServices) {
        if (sQLRunnerEx == null) {
            BuildAgentManagerImpl.$$$reportNull$$$0(0);
        }
        if (serverResponsibilityEx == null) {
            BuildAgentManagerImpl.$$$reportNull$$$0(1);
        }
        if (eventDispatcher == null) {
            BuildAgentManagerImpl.$$$reportNull$$$0(2);
        }
        if (multiNodesEvents == null) {
            BuildAgentManagerImpl.$$$reportNull$$$0(3);
        }
        if (executorServices == null) {
            BuildAgentManagerImpl.$$$reportNull$$$0(4);
        }
        this.myAgents = new IndexedCollection();
        this.myAgentNames = new ConcurrentHashMap();
        this.myAgentsWithFailedPing = new ConcurrentHashMap<Integer, Long>();
        this.myProcessingAuthTokens = Collections.synchronizedMap(new HashMap());
        this.myConcurrentAgentRegistrationsNumber = new AtomicInteger();
        this.myTimeService = SystemTimeService.getInstance();
        this.myLastSuccessfulPollTimestamp = 0L;
        this.myServerResponsibility = serverResponsibilityEx;
        this.myMultiNodesEvents = multiNodesEvents;
        this.myExecutorServices = executorServices;
        this.mySqlRunner = sQLRunnerEx;
        this.myAgents.setComparator(AGENTS_COMPARATOR);
        this.myAgents.addListener((IndexedCollection.CollectionListener)new /* Unavailable Anonymous Inner Class!! */);
        eventDispatcher.addListener((EventListener)new /* Unavailable Anonymous Inner Class!! */);
        new OtherNodeEventsHandler(this, executorServices).start();
        long l = TeamCityProperties.getLong((String)"teamcity.agent.unregisterAgents.delayMillis", (long)(LogInitializer.isUnitTest() ? 100L : 20000L));
        this.myExecutorServices.getNormalExecutorService().scheduleWithFixedDelay(this::unregisterAgentsWithFailedPing, l, l, TimeUnit.MILLISECONDS);
    }

    /*
     * Loose catch block
     */
    @VisibleForTesting
    void unregisterAgentsWithFailedPing() {
        try {
            HashSet<Integer> hashSet = new HashSet<Integer>(this.myAgentsWithFailedPing.keySet());
            for (BuildAgentEx buildAgentEx : (ArrayList)this.myAgents.findAll(new ArrayList(), hashSet)) {
                block22: {
                    Long l = this.myAgentsWithFailedPing.get(buildAgentEx.getId());
                    try {
                        if (l == null) {
                            continue;
                        }
                    }
                    catch (Throwable throwable) {
                        throw BuildAgentManagerImpl.a(throwable);
                    }
                    if (!BuildAgentManagerImpl.isPollBasedCommunicationTimestampEnabled()) break block22;
                    try {
                        if (this.myLastSuccessfulPollTimestamp <= l) {
                            continue;
                        }
                        break block22;
                        catch (Throwable throwable) {
                            throw BuildAgentManagerImpl.a(throwable);
                        }
                    }
                    catch (Throwable throwable) {
                        throw BuildAgentManagerImpl.a(throwable);
                    }
                }
                try {
                    if (this.myAgentsWithFailedPing.remove(buildAgentEx.getId()) == null) {
                        continue;
                    }
                }
                catch (Throwable throwable) {
                    throw BuildAgentManagerImpl.a(throwable);
                }
                try {
                    if (buildAgentEx.ping()) {
                        continue;
                    }
                }
                catch (Throwable throwable) {
                    throw BuildAgentManagerImpl.a(throwable);
                }
                try {
                    if (this.findAgentById(buildAgentEx.getId(), false) != buildAgentEx) {
                        continue;
                    }
                }
                catch (Throwable throwable) {
                    throw BuildAgentManagerImpl.a(throwable);
                }
                try {
                    String string = "Unregistered because the agent has not communicated with the server since: " + LogUtil.describe((Date)buildAgentEx.getLastCommunicationTimestamp());
                    this.unregisterAgent(buildAgentEx, string, false);
                }
                catch (Throwable throwable) {
                    Loggers.AGENT.warnAndDebugDetails("Error while unregistering agent " + LogUtil.describe((Loggable)buildAgentEx), throwable);
                }
            }
        }
        catch (Throwable throwable) {
            Loggers.AGENT.warnAndDebugDetails("Error while processing agents marked for unregistration", throwable);
        }
    }

    @NotNull
    private Set<Integer> unregisterObsoleteAgents(@NotNull SQLRunnerEx sQLRunnerEx, @NotNull List<AgentData> list) {
        Set<Integer> set;
        try {
            if (sQLRunnerEx == null) {
                BuildAgentManagerImpl.$$$reportNull$$$0(5);
            }
        }
        catch (RuntimeException runtimeException) {
            throw BuildAgentManagerImpl.a(runtimeException);
        }
        try {
            if (list == null) {
                BuildAgentManagerImpl.$$$reportNull$$$0(6);
            }
        }
        catch (RuntimeException runtimeException) {
            throw BuildAgentManagerImpl.a(runtimeException);
        }
        if (this.myServerResponsibility.canManageAgents()) {
            ArrayList<Integer> arrayList = new ArrayList<Integer>();
            for (AgentData agentData : list) {
                try {
                    if (agentData.version != null) continue;
                    arrayList.add(agentData.id);
                }
                catch (RuntimeException runtimeException) {
                    throw BuildAgentManagerImpl.a(runtimeException);
                }
            }
            if (!arrayList.isEmpty()) {
                sQLRunnerEx.withDBNoResult(dBFunctions -> {
                    String string = "update agent set registered=0, unregistered_reason='Unregistered because of unknown version' where registered=1 and #IN{id}";
                    dBFunctions.executeDml(string, new Object[]{InParams.ofInt((Collection)arrayList).autoSplitBy(50)});
                });
                Iterator<Object> iterator = arrayList.iterator();
                while (iterator.hasNext()) {
                    int n = (Integer)iterator.next();
                    this.myMultiNodesEvents.publish(new MultiNodesEvents.EventData("agentUnregistered", Long.valueOf(n), null, "Unregistered because of unknown version"));
                }
                Loggers.AGENT.info("Agents with ids " + CollectionsUtil.asString(arrayList, (int)20) + " were unregistered because they have unknown version in database and should re-register");
            }
            return new HashSet<Integer>(arrayList);
        }
        try {
            set = Collections.emptySet();
            if (set == null) {
                BuildAgentManagerImpl.$$$reportNull$$$0(7);
            }
        }
        catch (RuntimeException runtimeException) {
            throw BuildAgentManagerImpl.a(runtimeException);
        }
        return set;
    }

    @Used(value="spring")
    public void setAuditLogFactory(@NotNull AuditLogFactory auditLogFactory) {
        try {
            if (auditLogFactory == null) {
                BuildAgentManagerImpl.$$$reportNull$$$0(8);
            }
        }
        catch (RuntimeException runtimeException) {
            throw BuildAgentManagerImpl.a(runtimeException);
        }
        this.myAuditLogFactory = auditLogFactory;
    }

    @Used(value="spring")
    public void setBuildAgentUpgrader(@NotNull BuildAgentUpgrader buildAgentUpgrader) {
        try {
            if (buildAgentUpgrader == null) {
                BuildAgentManagerImpl.$$$reportNull$$$0(9);
            }
        }
        catch (RuntimeException runtimeException) {
            throw BuildAgentManagerImpl.a(runtimeException);
        }
        this.myAgentUpgrader = buildAgentUpgrader;
    }

    @Used(value="spring")
    public void setRunningBuildManager(@NotNull RunningBuildsManagerEx runningBuildsManagerEx) {
        try {
            if (runningBuildsManagerEx == null) {
                BuildAgentManagerImpl.$$$reportNull$$$0(10);
            }
        }
        catch (RuntimeException runtimeException) {
            throw BuildAgentManagerImpl.a(runtimeException);
        }
        this.myRunningBuildManager = runningBuildsManagerEx;
    }

    @Used(value="spring")
    public void setDispatcher(@NotNull EventDispatcher<BuildServerListener> eventDispatcher) {
        try {
            if (eventDispatcher == null) {
                BuildAgentManagerImpl.$$$reportNull$$$0(11);
            }
        }
        catch (RuntimeException runtimeException) {
            throw BuildAgentManagerImpl.a(runtimeException);
        }
        this.myDispatcher = eventDispatcher;
    }

    @Used(value="spring")
    public void setUnregisteredAgentsManager(@NotNull UnregisteredAgentsManager unregisteredAgentsManager) {
        try {
            if (unregisteredAgentsManager == null) {
                BuildAgentManagerImpl.$$$reportNull$$$0(12);
            }
        }
        catch (RuntimeException runtimeException) {
            throw BuildAgentManagerImpl.a(runtimeException);
        }
        this.myUnregisteredAgentsManager = unregisteredAgentsManager;
    }

    @Used(value="spring")
    public void setAgentPersister(AgentPersister agentPersister) {
        this.myAgentPersister = agentPersister;
    }

    @Used(value="spring")
    public void setAgentTypeManager(AgentTypeManager agentTypeManager) {
        this.myAgentTypeManager = agentTypeManager;
    }

    @Used(value="spring")
    public void setAgentTypeStorage(AgentTypeStorage agentTypeStorage) {
        this.myAgentTypeStorage = agentTypeStorage;
    }

    @Used(value="spring")
    public void setBuildAgentFactory(BuildAgentFactory buildAgentFactory) {
        this.myBuildAgentFactory = buildAgentFactory;
    }

    @Used(value="spring")
    public void setAgentDistributionMonitor(@NotNull AgentDistributionMonitor agentDistributionMonitor) {
        try {
            if (agentDistributionMonitor == null) {
                BuildAgentManagerImpl.$$$reportNull$$$0(13);
            }
        }
        catch (RuntimeException runtimeException) {
            throw BuildAgentManagerImpl.a(runtimeException);
        }
        this.myAgentDistributionMonitor = agentDistributionMonitor;
    }

    @Used(value="spring")
    public void setAutomaticAgentAuthorizationTokens(@NotNull AutomaticAgentAuthorizationTokens automaticAgentAuthorizationTokens) {
        try {
            if (automaticAgentAuthorizationTokens == null) {
                BuildAgentManagerImpl.$$$reportNull$$$0(14);
            }
        }
        catch (RuntimeException runtimeException) {
            throw BuildAgentManagerImpl.a(runtimeException);
        }
        this.myAutomaticAgentAuthorizationTokens = automaticAgentAuthorizationTokens;
    }

    @Used(value="spring")
    public void setAgentPoolManager(@NotNull AgentPoolManager agentPoolManager) {
        try {
            if (agentPoolManager == null) {
                BuildAgentManagerImpl.$$$reportNull$$$0(15);
            }
        }
        catch (RuntimeException runtimeException) {
            throw BuildAgentManagerImpl.a(runtimeException);
        }
        this.myAgentPoolManager = agentPoolManager;
    }

    public boolean registrationIsTemporallyNotAvailable() {
        boolean bl;
        try {
            bl = this.getRegistrationIsTemporarilyUnavailableReason() != null;
        }
        catch (RuntimeException runtimeException) {
            throw BuildAgentManagerImpl.a(runtimeException);
        }
        return bl;
    }

    @Nullable
    private String getRegistrationIsTemporarilyUnavailableReason() {
        block7: {
            try {
                try {
                    if (this.myAgentDistributionMonitor != null && this.myAgentDistributionMonitor.isReady()) break block7;
                }
                catch (RuntimeException runtimeException) {
                    throw BuildAgentManagerImpl.a(runtimeException);
                }
                return "Build agent cannot be registered because TeamCity server is still initializing. Try again later.";
            }
            catch (RuntimeException runtimeException) {
                throw BuildAgentManagerImpl.a(runtimeException);
            }
        }
        int n = TeamCityProperties.getInteger((String)"teamcity.agents.concurrentRegistrations.limit", (int)30);
        int n2 = this.myConcurrentAgentRegistrationsNumber.get();
        try {
            if (n2 >= n) {
                return "Number of concurrent agent registrations (" + n2 + ") reached the limit: " + n;
            }
        }
        catch (RuntimeException runtimeException) {
            throw BuildAgentManagerImpl.a(runtimeException);
        }
        return null;
    }

    private void scheduleAgentsPollUpdate() {
        long l = TeamCityProperties.getIntervalMilliseconds((String)"teamcity.agent.pollFromDB.delay", (long)20000L);
        this.myExecutorServices.getNormalExecutorService().scheduleWithFixedDelay(this::updateAgentsFromDB, l, l, TimeUnit.MILLISECONDS);
    }

    private void updateAgentsFromDB() {
        try {
            if (!TeamCityProperties.getBoolean((String)"teamcity.agent.pollFromDB.enabled")) {
                return;
            }
        }
        catch (Throwable throwable) {
            throw BuildAgentManagerImpl.a(throwable);
        }
        try {
            this.pollAgentsFromDB();
        }
        catch (Throwable throwable) {
            Loggers.AGENT.warnAndDebugDetails("Agents DB polling: unable to fetch agents information from the database", throwable);
        }
    }

    /*
     * Could not resolve type clashes
     * Unable to fully structure code
     */
    private void pollAgentsFromDB() {
        block27: {
            block26: {
                var1_1 = this.myTimeService.now();
                var3_2 = this.fetchRegisteredAgentsWithTimestamps();
                var4_3 = var3_2.keySet();
                var5_4 = this.myAgents.getAll().stream().map((Function<BuildAgentEx, Integer>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)Ljava/lang/Object;, getId(), (Ljetbrains/buildServer/serverSide/BuildAgentEx;)Ljava/lang/Integer;)()).collect(Collectors.toSet());
                var6_5 = new ArrayList<Object>();
                for (Object var8_7 : var4_3) {
                    try {
                        if (var5_4.contains(var8_7)) continue;
                        var6_5.add(var8_7);
                    }
                    catch (Throwable v0) {
                        throw BuildAgentManagerImpl.a(v0);
                    }
                }
                var7_6 = new ArrayList<E>();
                for (Integer var9_8 : var5_4) {
                    try {
                        if (var4_3.contains(var9_8)) continue;
                        var7_6.add(var9_8);
                    }
                    catch (Throwable v1) {
                        throw BuildAgentManagerImpl.a(v1);
                    }
                }
                if (!var6_5.isEmpty()) break block26;
                try {
                    block28: {
                        if (!var7_6.isEmpty()) break block26;
                        break block28;
                        catch (Throwable v2) {
                            throw BuildAgentManagerImpl.a(v2);
                        }
                    }
                    this.syncAgentNamesFromDB(var4_3);
                    this.finishPoll(var3_2, var1_1);
                    return;
                }
                catch (Throwable v3) {
                    throw BuildAgentManagerImpl.a(v3);
                }
            }
            var8_7 = "Agents DB polling: " + var6_5.size() + " newly registered, " + var7_6.size() + " removed, " + var5_4.size() + " in memory";
            var9_8 = NamedThreadFactory.patchThreadName((String)var8_7);
            try {
                var10_9 = 0;
                for (Integer var12_14 : var6_5) {
                    try {
                        if (!this.addPolledAgent(var12_14)) continue;
                        ++var10_9;
                    }
                    catch (Throwable v4) {
                        throw BuildAgentManagerImpl.a(v4);
                    }
                }
                try {
                    v5 = var7_6.isEmpty() != false ? 0 : this.applyRemovals((List<Integer>)var7_6);
                }
                catch (Throwable v6) {
                    throw BuildAgentManagerImpl.a(v6);
                }
                var11_12 = v5;
                this.syncAgentNamesFromDB(var4_3);
                if (var10_9 > 0) ** GOTO lbl61
                try {
                    block29: {
                        if (var11_12 <= 0) break block27;
                        break block29;
                        catch (Throwable v7) {
                            throw BuildAgentManagerImpl.a(v7);
                        }
                    }
                    Loggers.AGENT.info("Agents DB polling: synchronization finished, newly registered agents: " + var10_9 + ", removed agents: " + var11_12);
                }
                catch (Throwable v8) {
                    throw BuildAgentManagerImpl.a(v8);
                }
            }
            catch (Throwable var10_10) {
                if (var9_8 != null) {
                    try {
                        var9_8.close();
                    }
                    catch (Throwable var11_13) {
                        var10_10.addSuppressed(var11_13);
                    }
                }
                throw var10_10;
            }
        }
        try {
            if (var9_8 == null) ** GOTO lbl81
            var9_8.close();
        }
        catch (Throwable v9) {
            throw BuildAgentManagerImpl.a(v9);
        }
lbl81:
        // 2 sources

        this.finishPoll(var3_2, var1_1);
    }

    private void finishPoll(@NotNull Map<Integer, Long> map, long l) {
        try {
            if (map == null) {
                BuildAgentManagerImpl.$$$reportNull$$$0(16);
            }
        }
        catch (RuntimeException runtimeException) {
            throw BuildAgentManagerImpl.a(runtimeException);
        }
        for (BuildAgentEx buildAgentEx : this.myAgents.getAll()) {
            Long l2 = map.get(buildAgentEx.getId());
            try {
                if (l2 == null) continue;
                buildAgentEx.updateCommunicationTimestampInMemory(new Date(l2));
            }
            catch (RuntimeException runtimeException) {
                throw BuildAgentManagerImpl.a(runtimeException);
            }
        }
        this.myLastSuccessfulPollTimestamp = l;
    }

    @NotNull
    private Map<Integer, Long> fetchRegisteredAgentsWithTimestamps() {
        Map map;
        try {
            map = (Map)this.mySqlRunner.withDB(dBFunctions -> dBFunctions.queryForMap("select id, last_binding_timestamp from agent where registered = 1", Integer.class, Long.class, new Object[0]));
            if (map == null) {
                BuildAgentManagerImpl.$$$reportNull$$$0(17);
            }
        }
        catch (RuntimeException runtimeException) {
            throw BuildAgentManagerImpl.a(runtimeException);
        }
        return map;
    }

    private void syncAgentNamesFromDB(@NotNull Set<Integer> set) {
        try {
            if (set == null) {
                BuildAgentManagerImpl.$$$reportNull$$$0(18);
            }
        }
        catch (RuntimeException runtimeException) {
            throw BuildAgentManagerImpl.a(runtimeException);
        }
        Map map = (Map)this.mySqlRunner.withDB(dBFunctions -> dBFunctions.queryForMap("select name, id from agent where registered <> 1", String.class, Integer.class, new Object[0]));
        int n = 0;
        for (Map.Entry object : map.entrySet()) {
            try {
                if (this.myAgentNames.putIfAbsent((String)object.getKey(), (Integer)object.getValue()) != null) continue;
                ++n;
            }
            catch (RuntimeException runtimeException) {
                throw BuildAgentManagerImpl.a(runtimeException);
            }
        }
        try {
            if (n > 0) {
                Loggers.AGENT.debug("Agents DB polling: fetched names of " + n + " previously unknown unregistered agents from DB");
            }
        }
        catch (RuntimeException runtimeException) {
            throw BuildAgentManagerImpl.a(runtimeException);
        }
        HashSet<Integer> hashSet = new HashSet<Integer>(set);
        hashSet.addAll(map.values());
        for (Map.Entry<String, Integer> entry : new HashMap<String, Integer>(this.myAgentNames).entrySet()) {
            Integer n2 = entry.getValue();
            if (hashSet.contains(n2)) continue;
            try {
                if (this.myAgents.find((Object)n2) != null) {
                    continue;
                }
            }
            catch (RuntimeException runtimeException) {
                throw BuildAgentManagerImpl.a(runtimeException);
            }
            try {
                if (!this.myAgentNames.remove(entry.getKey(), n2)) continue;
                this.myAgentPersister.resetAgentCaches(n2.intValue());
                Loggers.AGENT.debug("Agents DB polling: cleared stale name index entry '" + entry.getKey() + "' -> " + n2);
            }
            catch (RuntimeException runtimeException) {
                throw BuildAgentManagerImpl.a(runtimeException);
            }
        }
    }

    @VisibleForTesting
    boolean isNameIndexed(@NotNull String string) {
        try {
            if (string == null) {
                BuildAgentManagerImpl.$$$reportNull$$$0(19);
            }
        }
        catch (RuntimeException runtimeException) {
            throw BuildAgentManagerImpl.a(runtimeException);
        }
        return this.myAgentNames.containsKey(string);
    }

    @VisibleForTesting
    void pollAgentsFromDBNow() {
        this.pollAgentsFromDB();
    }

    private int applyRemovals(@NotNull List<Integer> list) {
        try {
            if (list == null) {
                BuildAgentManagerImpl.$$$reportNull$$$0(20);
            }
        }
        catch (RuntimeException runtimeException) {
            throw BuildAgentManagerImpl.a(runtimeException);
        }
        int n = TeamCityProperties.getInteger((String)"teamcity.agent.pollFromDB.batchSize", (int)1000);
        String string = "select id, registered from agent where #IN{id}";
        InParams inParams = InParams.ofInt(list).autoSplitBy(n);
        Map map = (Map)this.mySqlRunner.withDB(dBFunctions -> dBFunctions.queryForMap(string, Integer.class, Integer.class, new Object[]{inParams}));
        int n2 = 0;
        for (Integer n3 : list) {
            block16: {
                BuildAgentEx buildAgentEx;
                block15: {
                    Integer n4 = (Integer)map.get(n3);
                    try {
                        try {
                            if (n4 != null && n4 == 1) {
                                continue;
                            }
                        }
                        catch (RuntimeException runtimeException) {
                            throw BuildAgentManagerImpl.a(runtimeException);
                        }
                    }
                    catch (RuntimeException runtimeException) {
                        throw BuildAgentManagerImpl.a(runtimeException);
                    }
                    buildAgentEx = this.findAgentById(n3, false);
                    try {
                        if (buildAgentEx == null) {
                            continue;
                        }
                    }
                    catch (RuntimeException runtimeException) {
                        throw BuildAgentManagerImpl.a(runtimeException);
                    }
                    try {
                        if (n4 == null) break block15;
                        this.processUnregisteredAgent(buildAgentEx);
                        break block16;
                    }
                    catch (RuntimeException runtimeException) {
                        throw BuildAgentManagerImpl.a(runtimeException);
                    }
                }
                this.processRemovedAgent(buildAgentEx);
            }
            ++n2;
        }
        return n2;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void processUnregisteredAgent(@NotNull BuildAgentEx buildAgentEx) {
        try {
            if (buildAgentEx == null) {
                BuildAgentManagerImpl.$$$reportNull$$$0(21);
            }
        }
        catch (RuntimeException runtimeException) {
            throw BuildAgentManagerImpl.a(runtimeException);
        }
        BuildAgentManagerImpl buildAgentManagerImpl = this;
        synchronized (buildAgentManagerImpl) {
            if (this.myAgents.find((Object)buildAgentEx.getId()) != buildAgentEx) {
                return;
            }
            this.myAgents.remove((Object)buildAgentEx.getId());
        }
        this.myAgentPersister.invalidateCaches(buildAgentEx);
        Loggers.AGENT.info("Agents DB polling: the agent " + LogUtil.describe((Loggable)buildAgentEx) + " is no longer marked as registered in the database");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void processRemovedAgent(@NotNull BuildAgentEx buildAgentEx) {
        try {
            if (buildAgentEx == null) {
                BuildAgentManagerImpl.$$$reportNull$$$0(22);
            }
        }
        catch (RuntimeException runtimeException) {
            throw BuildAgentManagerImpl.a(runtimeException);
        }
        BuildAgentManagerImpl buildAgentManagerImpl = this;
        synchronized (buildAgentManagerImpl) {
            if (this.myAgents.find((Object)buildAgentEx.getId()) != buildAgentEx) {
                return;
            }
            buildAgentEx.markAsRemoved();
            this.myAgents.remove((Object)buildAgentEx.getId());
            this.myAgentNames.remove(buildAgentEx.getName(), buildAgentEx.getId());
        }
        this.myAgentPersister.resetAgentCaches(buildAgentEx.getId());
        Loggers.AGENT.info("Agents DB polling: the agent " + LogUtil.describe((Loggable)buildAgentEx) + " no longer exists in the database");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean addPolledAgent(int n) {
        AgentData agentData = this.myAgentPersister.loadAgent(n, false);
        try {
            if (agentData == null) {
                return false;
            }
        }
        catch (RuntimeException runtimeException) {
            throw BuildAgentManagerImpl.a(runtimeException);
        }
        BuildAgentEx buildAgentEx = this.myBuildAgentFactory.createRegisteredAgentWithPollingConnection(agentData, agentData.version, agentData.pluginsVersion);
        this.myAgentTypeManager.assignAgentType(buildAgentEx);
        BuildAgentManagerImpl buildAgentManagerImpl = this;
        synchronized (buildAgentManagerImpl) {
            if (this.myAgents.find((Object)n) != null) {
                return false;
            }
            this.myAgents.put((Object)n, (Comparable)buildAgentEx);
        }
        this.myAgentPersister.invalidateCaches(buildAgentEx);
        Loggers.AGENT.info("Agents DB polling: detected a newly registered agent in the database: " + LogUtil.describe((Loggable)buildAgentEx));
        return true;
    }

    public List<BuildAgentEx> getRegisteredAgents() {
        return this.getRegisteredAgents(false);
    }

    public List<BuildAgentEx> getRegisteredAgents(boolean bl) {
        ArrayList<BuildAgentEx> arrayList = new ArrayList<BuildAgentEx>();
        this.myAgents.getSorted().forEach(buildAgentEx -> {
            block4: {
                try {
                    try {
                        if (!bl && !buildAgentEx.isAuthorized()) break block4;
                    }
                    catch (RuntimeException runtimeException) {
                        throw BuildAgentManagerImpl.a(runtimeException);
                    }
                    arrayList.add((BuildAgentEx)buildAgentEx);
                }
                catch (RuntimeException runtimeException) {
                    throw BuildAgentManagerImpl.a(runtimeException);
                }
            }
        });
        return arrayList;
    }

    public List<BuildAgentEx> getUnregisteredAgents() {
        return this.getUnregisteredAgents(false);
    }

    public List<BuildAgentEx> getUnregisteredAgents(boolean bl) {
        return this.toUnregisteredAgents(this.myUnregisteredAgentsManager.getUnregisteredAgents(bl));
    }

    @NotNull
    private List<BuildAgentEx> toUnregisteredAgents(@NotNull List<AgentData> list) {
        ArrayList<BuildAgentEx> arrayList;
        try {
            if (list == null) {
                BuildAgentManagerImpl.$$$reportNull$$$0(23);
            }
        }
        catch (RuntimeException runtimeException) {
            throw BuildAgentManagerImpl.a(runtimeException);
        }
        ArrayList<BuildAgentEx> arrayList2 = new ArrayList<BuildAgentEx>();
        Map map = this.myAgentTypeStorage.findAgentTypesByIds((Collection)list.stream().map(agentData -> agentData.agentTypeId).collect(Collectors.toSet()));
        for (AgentData agentData2 : list) {
            AgentDescription agentDescription = (AgentDescription)map.get(agentData2.agentTypeId);
            if (agentDescription == null) {
                Loggers.SERVER.warn("Failed to find an agent type for the unregistered agent with id: " + agentData2.id + " by agent type id: " + agentData2.agentTypeId);
                agentDescription = this.myBuildAgentFactory.createAgentDescription(new AgentDetails(agentData2.name, agentData2.hostAddress, agentData2.port, agentData2.authorizationToken, "N/A"));
            }
            arrayList2.add(this.myBuildAgentFactory.createUnregisteredAgent(agentData2, agentDescription));
        }
        try {
            arrayList = arrayList2;
            if (arrayList == null) {
                BuildAgentManagerImpl.$$$reportNull$$$0(24);
            }
        }
        catch (RuntimeException runtimeException) {
            throw BuildAgentManagerImpl.a(runtimeException);
        }
        return arrayList;
    }

    @Nullable
    private BuildAgentEx findUnregisteredAgentById(int n) {
        AgentData agentData = this.myAgentPersister.loadAgent(n, true);
        try {
            if (agentData == null) {
                return null;
            }
        }
        catch (RuntimeException runtimeException) {
            throw BuildAgentManagerImpl.a(runtimeException);
        }
        this.myAgentNames.put(agentData.name, n);
        return this.toUnregisteredAgents(Collections.singletonList(agentData)).iterator().next();
    }

    public List<BuildAgentEx> getAllAgents() {
        return this.getAllAgents(false);
    }

    public List<BuildAgentEx> getAllAgents(boolean bl) {
        List<BuildAgentEx> list = this.getRegisteredAgents(bl);
        List<BuildAgentEx> list2 = this.getUnregisteredAgents(bl);
        ArrayList<BuildAgentEx> arrayList = new ArrayList<BuildAgentEx>(list.size() + list2.size());
        arrayList.addAll(list);
        arrayList.addAll(list2);
        return arrayList;
    }

    @Nullable
    public BuildAgentEx findAgentById(int n, boolean bl) {
        BuildAgentEx buildAgentEx;
        block4: {
            buildAgentEx = (BuildAgentEx)this.myAgents.find((Object)n);
            try {
                try {
                    if (buildAgentEx != null || !bl) break block4;
                }
                catch (RuntimeException runtimeException) {
                    throw BuildAgentManagerImpl.a(runtimeException);
                }
                return this.findUnregisteredAgentById(n);
            }
            catch (RuntimeException runtimeException) {
                throw BuildAgentManagerImpl.a(runtimeException);
            }
        }
        return buildAgentEx;
    }

    @Nullable
    public BuildAgentEx findAgentByName(@NotNull String string, boolean bl) {
        block13: {
            try {
                if (string == null) {
                    BuildAgentManagerImpl.$$$reportNull$$$0(25);
                }
            }
            catch (RuntimeException runtimeException) {
                throw BuildAgentManagerImpl.a(runtimeException);
            }
            Integer n = this.myAgentNames.get(string);
            if (n != null) {
                block14: {
                    BuildAgentEx buildAgentEx2;
                    block12: {
                        buildAgentEx2 = this.findAgentById(n, bl);
                        try {
                            try {
                                if (buildAgentEx2 != null || !bl) break block12;
                            }
                            catch (RuntimeException runtimeException) {
                                throw BuildAgentManagerImpl.a(runtimeException);
                            }
                            this.myAgentNames.remove(string);
                            return null;
                        }
                        catch (RuntimeException runtimeException) {
                            throw BuildAgentManagerImpl.a(runtimeException);
                        }
                    }
                    try {
                        try {
                            if (buildAgentEx2 == null) break block13;
                            if (!buildAgentEx2.getName().equals(string)) break block14;
                        }
                        catch (RuntimeException runtimeException) {
                            throw BuildAgentManagerImpl.a(runtimeException);
                        }
                        return buildAgentEx2;
                    }
                    catch (RuntimeException runtimeException) {
                        throw BuildAgentManagerImpl.a(runtimeException);
                    }
                }
                this.myAgentNames.remove(string);
                AtomicReference atomicReference = new AtomicReference();
                this.myAgents.processItems(buildAgentEx -> {
                    try {
                        if (string.equals(buildAgentEx.getName())) {
                            this.myAgentNames.put(string, buildAgentEx.getId());
                            atomicReference.set(buildAgentEx);
                            return false;
                        }
                    }
                    catch (RuntimeException runtimeException) {
                        throw BuildAgentManagerImpl.a(runtimeException);
                    }
                    return true;
                });
                return (BuildAgentEx)atomicReference.get();
            }
        }
        return null;
    }

    @NotNull
    public List<BuildAgentEx> findAgentsByAgentTypeIds(@NotNull Set<Integer> set, boolean bl) {
        ArrayList<BuildAgentEx> arrayList;
        ArrayList<BuildAgentEx> arrayList2;
        HashSet<Integer> hashSet;
        block12: {
            ArrayList<BuildAgentEx> arrayList3;
            block13: {
                try {
                    if (set == null) {
                        BuildAgentManagerImpl.$$$reportNull$$$0(26);
                    }
                }
                catch (RuntimeException runtimeException) {
                    throw BuildAgentManagerImpl.a(runtimeException);
                }
                hashSet = new HashSet<Integer>(set);
                arrayList2 = new ArrayList<BuildAgentEx>();
                try {
                    try {
                        try {
                            this.myAgents.processItems(buildAgentEx -> {
                                try {
                                    if (set.contains(buildAgentEx.getAgentTypeId())) {
                                        arrayList2.add((BuildAgentEx)buildAgentEx);
                                        hashSet.remove(buildAgentEx.getAgentTypeId());
                                    }
                                }
                                catch (RuntimeException runtimeException) {
                                    throw BuildAgentManagerImpl.a(runtimeException);
                                }
                                return true;
                            });
                            if (bl && !hashSet.isEmpty()) break block12;
                        }
                        catch (RuntimeException runtimeException) {
                            throw BuildAgentManagerImpl.a(runtimeException);
                        }
                        arrayList3 = arrayList2;
                        if (arrayList3 != null) break block13;
                    }
                    catch (RuntimeException runtimeException) {
                        throw BuildAgentManagerImpl.a(runtimeException);
                    }
                    BuildAgentManagerImpl.$$$reportNull$$$0(27);
                }
                catch (RuntimeException runtimeException) {
                    throw BuildAgentManagerImpl.a(runtimeException);
                }
            }
            return arrayList3;
        }
        try {
            arrayList2.addAll(this.toUnregisteredAgents(this.myUnregisteredAgentsManager.getUnregisteredAgents(hashSet, true)));
            arrayList = arrayList2;
            if (arrayList == null) {
                BuildAgentManagerImpl.$$$reportNull$$$0(28);
            }
        }
        catch (RuntimeException runtimeException) {
            throw BuildAgentManagerImpl.a(runtimeException);
        }
        return arrayList;
    }

    @NotNull
    public BuildAgentEx registerAgent(BuildAgentEx buildAgentEx, long l) throws TeamCityRuntimeException {
        try {
            return this.registerAgentImpl(buildAgentEx, l);
        }
        catch (RuntimeException runtimeException) {
            block8: {
                String string;
                block7: {
                    try {
                        if (runtimeException instanceof MissingServerResponsibilityException) {
                            throw runtimeException;
                        }
                    }
                    catch (RuntimeException runtimeException2) {
                        throw BuildAgentManagerImpl.a(runtimeException2);
                    }
                    string = MessageFormat.format("Registration of agent: {0} failed: {1}", LogUtil.describeInDetail((Loggable)buildAgentEx), runtimeException.getMessage());
                    try {
                        if (!(runtimeException instanceof TeamCityRuntimeException)) break block7;
                        Loggers.AGENT.debug(string);
                        break block8;
                    }
                    catch (RuntimeException runtimeException3) {
                        throw BuildAgentManagerImpl.a(runtimeException3);
                    }
                }
                Loggers.AGENT.warnAndDebugDetails(string, (Throwable)runtimeException);
            }
            throw runtimeException;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     */
    public void updateAgentParameters(int n, @NotNull String string, @NotNull AgentDescription agentDescription) {
        BuildAgentEx buildAgentEx;
        block33: {
            block32: {
                block34: {
                    try {
                        if (string == null) {
                            BuildAgentManagerImpl.$$$reportNull$$$0(29);
                        }
                    }
                    catch (RuntimeException runtimeException) {
                        throw BuildAgentManagerImpl.a(runtimeException);
                    }
                    try {
                        if (agentDescription == null) {
                            BuildAgentManagerImpl.$$$reportNull$$$0(30);
                        }
                    }
                    catch (RuntimeException runtimeException) {
                        throw BuildAgentManagerImpl.a(runtimeException);
                    }
                    if (this.myServerResponsibility.canManageAgents()) break block32;
                    if (this.myServerResponsibility.canModifyDataInDatabase()) break block32;
                    break block34;
                    catch (RuntimeException runtimeException) {
                        throw BuildAgentManagerImpl.a(runtimeException);
                    }
                }
                try {
                    block35: {
                        if (TeamCityProperties.getBooleanOrTrue((String)"teamcity.agent.updateParameters.allowOnSecondaryNodes")) break block32;
                        break block35;
                        catch (RuntimeException runtimeException) {
                            throw BuildAgentManagerImpl.a(runtimeException);
                        }
                    }
                    throw new MissingServerResponsibilityException("This server operation mode does not support updating agent parameters");
                }
                catch (RuntimeException runtimeException) {
                    throw BuildAgentManagerImpl.a(runtimeException);
                }
            }
            buildAgentEx = this.findAgentById(n, false);
            try {
                if (buildAgentEx == null) {
                    return;
                }
            }
            catch (RuntimeException runtimeException) {
                throw BuildAgentManagerImpl.a(runtimeException);
            }
            String string2 = buildAgentEx.getAuthorizationToken();
            try {
                if (!string.equals(string2)) {
                    Loggers.AGENT.warn("Authorization token provided by agent: " + LogUtil.describe((Loggable)buildAgentEx) + " doesn't match to token on server-side. The update parameters request is ignored. Token on server: " + string2 + ", token from agent: " + string);
                    return;
                }
            }
            catch (RuntimeException runtimeException) {
                throw BuildAgentManagerImpl.a(runtimeException);
            }
            try {
                if (AgentTypeDatabaseStorage.sameDescriptions((AgentDescription)buildAgentEx, (AgentDescription)agentDescription)) {
                    Loggers.AGENT.debug("Request to updateAgentParameters for agent: " + LogUtil.describe((Loggable)buildAgentEx) + " ignored because new parameters do not differ from the old ones");
                    return;
                }
            }
            catch (RuntimeException runtimeException) {
                throw BuildAgentManagerImpl.a(runtimeException);
            }
            if ($assertionsDisabled) break block33;
            try {
                block36: {
                    if (this.myAgentUpgrader != null) break block33;
                    break block36;
                    catch (RuntimeException runtimeException) {
                        throw BuildAgentManagerImpl.a(runtimeException);
                    }
                }
                throw new AssertionError();
            }
            catch (RuntimeException runtimeException) {
                throw BuildAgentManagerImpl.a(runtimeException);
            }
        }
        Loggers.AGENT.debug("Agent description updated for agent: " + LogUtil.describe((Loggable)buildAgentEx));
        BuildAgentEx buildAgentEx2 = buildAgentEx;
        synchronized (buildAgentEx2) {
            buildAgentEx.setAgentDescription(agentDescription);
            if (!this.myAgentUpgrader.isUpgradeRequired(buildAgentEx)) {
                try {
                    if (!buildAgentEx.isUpgrading()) {
                        this.myAgentTypeManager.updateAgentType(buildAgentEx, false);
                    }
                }
                catch (RuntimeException runtimeException) {
                    throw BuildAgentManagerImpl.a(runtimeException);
                }
            }
            this.sendAgentDescriptionUpdated(buildAgentEx);
        }
    }

    private void sendAgentDescriptionUpdated(@NotNull BuildAgentEx buildAgentEx) {
        try {
            if (buildAgentEx == null) {
                BuildAgentManagerImpl.$$$reportNull$$$0(31);
            }
        }
        catch (RuntimeException runtimeException) {
            throw BuildAgentManagerImpl.a(runtimeException);
        }
        buildAgentEx.incrementParametersChangedCounter();
        ((BuildServerListener)this.myDispatcher.getMulticaster()).agentDescriptionUpdated((SBuildAgent)buildAgentEx);
        ((BuildServerListener)this.myDispatcher.getMulticaster()).agentsDescriptionUpdated(Collections.singleton(buildAgentEx));
        this.publishAgentDescriptionUpdatedToOtherNodes(buildAgentEx);
    }

    /*
     * Exception decompiling
     */
    @NotNull
    private BuildAgentEx registerAgentImpl(@NotNull BuildAgentEx var1_1, long var2_2) throws TeamCityRuntimeException {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
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

    private static boolean sameVersion(@NotNull BuildAgentEx buildAgentEx, @NotNull BuildAgentEx buildAgentEx2) {
        boolean bl;
        block11: {
            block10: {
                try {
                    if (buildAgentEx == null) {
                        BuildAgentManagerImpl.$$$reportNull$$$0(36);
                    }
                }
                catch (RuntimeException runtimeException) {
                    throw BuildAgentManagerImpl.a(runtimeException);
                }
                try {
                    if (buildAgentEx2 == null) {
                        BuildAgentManagerImpl.$$$reportNull$$$0(37);
                    }
                }
                catch (RuntimeException runtimeException) {
                    throw BuildAgentManagerImpl.a(runtimeException);
                }
                try {
                    try {
                        if (!buildAgentEx.getVersion().equals(buildAgentEx2.getVersion()) || !buildAgentEx.getPluginsSignature().equals(buildAgentEx2.getPluginsSignature())) break block10;
                    }
                    catch (RuntimeException runtimeException) {
                        throw BuildAgentManagerImpl.a(runtimeException);
                    }
                    bl = true;
                    break block11;
                }
                catch (RuntimeException runtimeException) {
                    throw BuildAgentManagerImpl.a(runtimeException);
                }
            }
            bl = false;
        }
        return bl;
    }

    @NotNull
    private BuildAgentEx handleRegistrationOnTheSecondaryNode(@NotNull BuildAgentEx buildAgentEx, long l, int n, String string) {
        BuildAgentEx buildAgentEx2;
        BuildAgentEx buildAgentEx3;
        block30: {
            block29: {
                try {
                    if (buildAgentEx == null) {
                        BuildAgentManagerImpl.$$$reportNull$$$0(38);
                    }
                }
                catch (RuntimeException runtimeException) {
                    throw BuildAgentManagerImpl.a(runtimeException);
                }
                try {
                    block28: {
                        try {
                            try {
                                if (n <= 0) break block28;
                                if (this.myServerResponsibility.canModifyDataInDatabase()) break block29;
                            }
                            catch (RuntimeException runtimeException) {
                                throw BuildAgentManagerImpl.a(runtimeException);
                            }
                            if (TeamCityProperties.getBooleanOrTrue((String)"teamcity.agent.registerOnSecondaryNodes")) break block29;
                        }
                        catch (RuntimeException runtimeException) {
                            throw BuildAgentManagerImpl.a(runtimeException);
                        }
                    }
                    throw new MissingServerResponsibilityException("The server does not have responsibility to register new agents");
                }
                catch (RuntimeException runtimeException) {
                    throw BuildAgentManagerImpl.a(runtimeException);
                }
            }
            buildAgentEx3 = this.findOrLoadRegisteredAgent(n);
            try {
                if (buildAgentEx3 == null) {
                    throw new MissingServerResponsibilityException("The server could not find an agent with id " + n + " and does not have a responsibility to proceed further with agent registration");
                }
            }
            catch (RuntimeException runtimeException) {
                throw BuildAgentManagerImpl.a(runtimeException);
            }
            try {
                if (!buildAgentEx3.getAuthorizationToken().equals(string)) {
                    throw new MissingServerResponsibilityException("The provided authorization token for the agent with id " + n + " does not match the token stored in the database. This server does not have a responsibility to proceed further with agent registration");
                }
            }
            catch (RuntimeException runtimeException) {
                throw BuildAgentManagerImpl.a(runtimeException);
            }
            try {
                if (!buildAgentEx3.getCommunicationProtocolType().equals(buildAgentEx.getCommunicationProtocolType())) {
                    throw new MissingServerResponsibilityException("The provided communication protocol \"" + buildAgentEx.getCommunicationProtocolType() + "\" for the agent with id " + n + " does not match the stored protocol. This server does not have a responsibility to proceed further with agent registration");
                }
            }
            catch (RuntimeException runtimeException) {
                throw BuildAgentManagerImpl.a(runtimeException);
            }
            try {
                try {
                    if (StringUtil.isEmpty((String)buildAgentEx.getName()) || buildAgentEx3.getName().equals(buildAgentEx.getName())) break block30;
                }
                catch (RuntimeException runtimeException) {
                    throw BuildAgentManagerImpl.a(runtimeException);
                }
                throw new MissingServerResponsibilityException("The provided agent name \"" + buildAgentEx.getName() + "\" for the agent with id " + n + " does not match the name stored in the database. This server does not have a responsibility to proceed further with agent registration");
            }
            catch (RuntimeException runtimeException) {
                throw BuildAgentManagerImpl.a(runtimeException);
            }
        }
        try {
            if (!BuildAgentManagerImpl.sameVersion(buildAgentEx, buildAgentEx3)) {
                throw new MissingServerResponsibilityException("The provided version and plugin signature \"" + buildAgentEx.getVersion() + "/" + buildAgentEx.getPluginsSignature() + "\" for the agent with id " + n + " does not match the version stored in the database. This server does not have a responsibility to proceed further with agent registration");
            }
        }
        catch (RuntimeException runtimeException) {
            throw BuildAgentManagerImpl.a(runtimeException);
        }
        try {
            BuildAgentManagerImpl.checkPingCode(buildAgentEx, buildAgentEx3);
            this.checkRunningBuild(l, (SBuildAgent)buildAgentEx3);
            buildAgentEx.setCommunicationTimestamp(this.myTimeService.nowDate());
            buildAgentEx2 = buildAgentEx3;
            if (buildAgentEx2 == null) {
                BuildAgentManagerImpl.$$$reportNull$$$0(39);
            }
        }
        catch (RuntimeException runtimeException) {
            throw BuildAgentManagerImpl.a(runtimeException);
        }
        return buildAgentEx2;
    }

    private static void checkPingCode(@NotNull BuildAgentEx buildAgentEx, @NotNull BuildAgentEx buildAgentEx2) {
        try {
            if (buildAgentEx == null) {
                BuildAgentManagerImpl.$$$reportNull$$$0(40);
            }
        }
        catch (RuntimeException runtimeException) {
            throw BuildAgentManagerImpl.a(runtimeException);
        }
        try {
            if (buildAgentEx2 == null) {
                BuildAgentManagerImpl.$$$reportNull$$$0(41);
            }
        }
        catch (RuntimeException runtimeException) {
            throw BuildAgentManagerImpl.a(runtimeException);
        }
        try {
            if (buildAgentEx2.getPingCode().equals("N/A")) {
                buildAgentEx2.setPingCode(buildAgentEx.getPingCode());
                return;
            }
        }
        catch (RuntimeException runtimeException) {
            throw BuildAgentManagerImpl.a(runtimeException);
        }
        try {
            if (!buildAgentEx2.getPingCode().equals(buildAgentEx.getPingCode())) {
                Loggers.AGENT.warn("Agent " + LogUtil.describe((Loggable)buildAgentEx) + " tries to register while another agent with the same authorization token and name is already registered: " + LogUtil.describe((Loggable)buildAgentEx2) + ", rejecting the registration attempt. Ensure that there is only one agent process started from the same installation directory.");
                throw new AlreadyExistsException("Another agent with the same authorization token and name is already registered", buildAgentEx2.getId());
            }
        }
        catch (RuntimeException runtimeException) {
            throw BuildAgentManagerImpl.a(runtimeException);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Nullable
    private BuildAgentEx findOrLoadRegisteredAgent(int n) {
        AgentData agentData;
        BuildAgentEx buildAgentEx = this.findAgentById(n, false);
        if (buildAgentEx == null && (agentData = this.myAgentPersister.loadAgent(n, false)) != null) {
            buildAgentEx = this.myBuildAgentFactory.createRegisteredAgentWithPollingConnection(agentData, "unknown", "unknown");
            BuildAgentManagerImpl buildAgentManagerImpl = this;
            synchronized (buildAgentManagerImpl) {
                this.myAgents.put((Object)n, (Comparable)buildAgentEx);
            }
        }
        return buildAgentEx;
    }

    private void sendAgentRegistered(@NotNull BuildAgentEx buildAgentEx, long l) {
        try {
            if (buildAgentEx == null) {
                BuildAgentManagerImpl.$$$reportNull$$$0(42);
            }
        }
        catch (RuntimeException runtimeException) {
            throw BuildAgentManagerImpl.a(runtimeException);
        }
        ((BuildServerListener)this.myDispatcher.getMulticaster()).agentRegistered((SBuildAgent)buildAgentEx, l);
        String string = AgentVersions.fromAgent((BuildAgentEx)buildAgentEx);
        this.myMultiNodesEvents.publish(new MultiNodesEvents.EventData("agentRegistered", Long.valueOf(buildAgentEx.getId()), null, string));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     */
    @NotNull
    private BuildAgentEx processNewRegistrationRequest(@NotNull BuildAgentEx buildAgentEx, long l) {
        BuildAgentEx buildAgentEx2;
        block65: {
            boolean bl2;
            boolean bl3;
            block64: {
                block62: {
                    block63: {
                        block70: {
                            block69: {
                                block68: {
                                    String string;
                                    block60: {
                                        try {
                                            if (buildAgentEx == null) {
                                                BuildAgentManagerImpl.$$$reportNull$$$0(43);
                                            }
                                        }
                                        catch (Throwable throwable) {
                                            throw BuildAgentManagerImpl.a(throwable);
                                        }
                                        if ($assertionsDisabled) break block60;
                                        try {
                                            block66: {
                                                if (this.myAgentUpgrader != null) break block60;
                                                break block66;
                                                catch (Throwable throwable) {
                                                    throw BuildAgentManagerImpl.a(throwable);
                                                }
                                            }
                                            throw new AssertionError();
                                        }
                                        catch (Throwable throwable) {
                                            throw BuildAgentManagerImpl.a(throwable);
                                        }
                                    }
                                    try {
                                        if (buildAgentEx.getName().length() > 80) {
                                            throw new TeamCityRuntimeException("Agent name is too long, the maximum allowed length is: 80");
                                        }
                                    }
                                    catch (Throwable throwable) {
                                        throw BuildAgentManagerImpl.a(throwable);
                                    }
                                    try {
                                        string = this.myAutomaticAgentAuthorizationTokens.isValidTokenFormat(buildAgentEx.getAuthorizationToken()) ? buildAgentEx.getAuthorizationToken() : null;
                                    }
                                    catch (Throwable throwable) {
                                        throw BuildAgentManagerImpl.a(throwable);
                                    }
                                    String string2 = string;
                                    bl3 = true;
                                    BuildAgentManagerImpl buildAgentManagerImpl = this;
                                    synchronized (buildAgentManagerImpl) {
                                        block61: {
                                            if (string2 != null) {
                                                buildAgentEx.setAuthorizationToken("");
                                            }
                                            if (!StringUtil.isEmptyOrSpaces((String)buildAgentEx.getName())) break block61;
                                            try {
                                                block67: {
                                                    if (StringUtil.isEmpty((String)buildAgentEx.getAuthorizationToken())) break block61;
                                                    break block67;
                                                    catch (Throwable throwable) {
                                                        throw BuildAgentManagerImpl.a(throwable);
                                                    }
                                                }
                                                Loggers.AGENT.info("Removing authorization token '" + buildAgentEx.getAuthorizationToken() + "' from agent with no name: " + buildAgentEx.describe(true));
                                                buildAgentEx.setAuthorizationToken("");
                                            }
                                            catch (Throwable throwable) {
                                                throw BuildAgentManagerImpl.a(throwable);
                                            }
                                        }
                                        try {
                                            if (!buildAgentEx.getAuthorizationToken().isEmpty()) {
                                                this.ensureAgentHasUniqueAuthorizationToken(buildAgentEx);
                                            }
                                        }
                                        catch (Throwable throwable) {
                                            throw BuildAgentManagerImpl.a(throwable);
                                        }
                                        if (buildAgentEx.restoreAgent()) {
                                            bl3 = false;
                                            this.unregisterAgent(buildAgentEx.getId());
                                        }
                                    }
                                    this.createAgentTypeIfNotExists(buildAgentEx);
                                    this.checkRunningBuild(l, (SBuildAgent)buildAgentEx);
                                    if (string2 == null) break block68;
                                    try {
                                        try {
                                            if (!TeamCityProperties.getBoolean((String)"teamcity.agents.automaticAgentAuthorizationTokens.enabled")) {
                                                throw new Exception("Automatic agent authorization tokens usage is disabled");
                                            }
                                        }
                                        catch (Throwable throwable) {
                                            throw BuildAgentManagerImpl.a(throwable);
                                        }
                                        try {
                                            if (buildAgentEx.isCloudAgent()) {
                                                throw new Exception("Automatic agent authorization tokens are not supported for cloud agents");
                                            }
                                        }
                                        catch (Throwable throwable) {
                                            throw BuildAgentManagerImpl.a(throwable);
                                        }
                                        buildAgentManagerImpl = this.myAutomaticAgentAuthorizationTokens.resolveToken(string2);
                                        try {
                                            if (buildAgentManagerImpl == null) {
                                                throw new Exception("Provided automatic agent authorization token is invalid");
                                            }
                                        }
                                        catch (Throwable throwable) {
                                            throw BuildAgentManagerImpl.a(throwable);
                                        }
                                        this.myAgentPoolManager.moveAgentToPool(buildAgentManagerImpl.getAgentPool().getAgentPoolId(), buildAgentEx);
                                        buildAgentEx.setAuthorized(true, buildAgentManagerImpl.getUser(), "Agent is authorized because it used an automatic authorization token");
                                    }
                                    catch (Throwable throwable) {
                                        buildAgentEx.setAuthorized(false, null, throwable.getMessage());
                                        Loggers.AGENT.warnAndDebugDetails("Failed to automatically authorize agent " + buildAgentEx.describe(false) + " during registration", throwable);
                                    }
                                    break block62;
                                }
                                if (StringUtil.isEmpty((String)buildAgentEx.getPingCode())) break block62;
                                if (buildAgentEx.isAuthorized()) break block62;
                                break block69;
                                catch (Throwable throwable) {
                                    throw BuildAgentManagerImpl.a(throwable);
                                }
                            }
                            if (buildAgentEx.isCloudAgent()) break block62;
                            break block70;
                            catch (Throwable throwable) {
                                throw BuildAgentManagerImpl.a(throwable);
                            }
                        }
                        try {
                            block71: {
                                if (BuildAgentManagerImpl.agentCommandsRequireAuthorization()) break block63;
                                break block71;
                                catch (Throwable throwable) {
                                    throw BuildAgentManagerImpl.a(throwable);
                                }
                            }
                            buildAgentEx.isLocal(bl -> {
                                try {
                                    if (bl == false) return;
                                    try {
                                        buildAgentEx.setAuthorized(true, null, "Locally installed agent is authorized by default");
                                        return;
                                    }
                                    catch (LicenseNotGrantedException licenseNotGrantedException) {
                                        buildAgentEx.setAuthorized(false, null, licenseNotGrantedException.getMessage());
                                        Loggers.AGENT.warn("Failed to authorize locally installed agent: " + licenseNotGrantedException.getMessage());
                                        return;
                                    }
                                }
                                catch (MissingServerResponsibilityException missingServerResponsibilityException) {
                                    throw BuildAgentManagerImpl.a(missingServerResponsibilityException);
                                }
                            });
                        }
                        catch (Throwable throwable) {
                            throw BuildAgentManagerImpl.a(throwable);
                        }
                    }
                    try {
                        if (!buildAgentEx.isAuthorized()) {
                            BuildAgentManagerImpl.printAgentWarning(buildAgentEx, buildAgentEx.getAuthorizeComment(), " is unauthorized");
                        }
                    }
                    catch (Throwable throwable) {
                        throw BuildAgentManagerImpl.a(throwable);
                    }
                }
                try {
                    if (!buildAgentEx.isEnabled()) {
                        BuildAgentManagerImpl.printAgentWarning(buildAgentEx, buildAgentEx.getStatusComment(), " is disabled");
                    }
                }
                catch (Throwable throwable) {
                    throw BuildAgentManagerImpl.a(throwable);
                }
                this.myAgentUpgrader.scheduleUpgradeIfNecessary(buildAgentEx);
                bl2 = false;
                try {
                    if (this.myAgentUpgrader.isUpgradeRequired(buildAgentEx) || buildAgentEx.isUpgrading()) break block64;
                }
                catch (Throwable throwable) {
                    throw BuildAgentManagerImpl.a(throwable);
                }
                try {
                    this.myAgentTypeManager.updateAgentType(buildAgentEx, bl3);
                    bl2 = true;
                }
                catch (Exception exception) {
                    Loggers.AGENT.warn("Failed to update agent type description of " + LogUtil.describe((Loggable)buildAgentEx) + ": " + exception.getMessage(), (Throwable)exception);
                }
            }
            try {
                try {
                    this.rememberAndSendRegistered(buildAgentEx);
                    if (bl3 || !bl2) break block65;
                }
                catch (Throwable throwable) {
                    throw BuildAgentManagerImpl.a(throwable);
                }
                this.sendAgentDescriptionUpdated(buildAgentEx);
            }
            catch (Throwable throwable) {
                throw BuildAgentManagerImpl.a(throwable);
            }
        }
        try {
            buildAgentEx2 = buildAgentEx;
            if (buildAgentEx2 == null) {
                BuildAgentManagerImpl.$$$reportNull$$$0(44);
            }
        }
        catch (Throwable throwable) {
            throw BuildAgentManagerImpl.a(throwable);
        }
        return buildAgentEx2;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @VisibleForTesting
    public void rememberAndSendRegistered(@NotNull BuildAgentEx buildAgentEx) {
        try {
            if (buildAgentEx == null) {
                BuildAgentManagerImpl.$$$reportNull$$$0(45);
            }
        }
        catch (RuntimeException runtimeException) {
            throw BuildAgentManagerImpl.a(runtimeException);
        }
        long l = -1L;
        BuildAgentManagerImpl buildAgentManagerImpl = this;
        synchronized (buildAgentManagerImpl) {
            Object object;
            String string;
            Logger logger;
            this.myAgents.put((Object)buildAgentEx.getId(), (Comparable)buildAgentEx);
            buildAgentEx.register();
            SRunningBuild sRunningBuild = buildAgentEx.getRunningBuild();
            if (sRunningBuild != null) {
                l = sRunningBuild.getBuildId();
            }
            try {
                logger = Loggers.AGENT;
                string = buildAgentEx.describe(true);
                object = sRunningBuild != null ? ", currently running a build " + LogUtil.describe((Build)sRunningBuild) : ", not running a build";
            }
            catch (RuntimeException runtimeException) {
                throw BuildAgentManagerImpl.a(runtimeException);
            }
            logger.info("Agent has been registered: " + string + (String)object);
        }
        this.sendAgentRegistered(buildAgentEx, l);
    }

    private void ensureAgentHasUniqueAuthorizationToken(BuildAgentEx buildAgentEx) throws AlreadyExistsException {
        String string = buildAgentEx.getAuthorizationToken();
        for (BuildAgentEx buildAgentEx2 : this.myAgents.getAll()) {
            if (!string.equals(buildAgentEx2.getAuthorizationToken())) continue;
            String string2 = MessageFormat.format("While registering agent {0} (pingCode: {1}), found another agent {2} (pingCode: {3}) with the same authorization token \"{4}\", new authorization token has been generated for the registering agent", buildAgentEx.describe(true), buildAgentEx.getPingCode(), buildAgentEx2.describe(true), buildAgentEx2.getPingCode(), string);
            Loggers.AGENT.info(string2);
            buildAgentEx.generateUniqueAgentAuthorizationToken();
            break;
        }
    }

    private void createAgentTypeIfNotExists(@NotNull BuildAgentEx buildAgentEx) {
        try {
            if (buildAgentEx == null) {
                BuildAgentManagerImpl.$$$reportNull$$$0(46);
            }
        }
        catch (RuntimeException runtimeException) {
            throw BuildAgentManagerImpl.a(runtimeException);
        }
        this.myAgentTypeManager.assignAgentType(buildAgentEx);
    }

    private static void printAgentWarning(BuildAgentEx buildAgentEx, Comment comment, String string) {
        Object object;
        block5: {
            block4: {
                String string2 = comment.getComment();
                try {
                    try {
                        if (string2 == null || string2.length() <= 0) break block4;
                    }
                    catch (RuntimeException runtimeException) {
                        throw BuildAgentManagerImpl.a(runtimeException);
                    }
                    object = ", last comment: " + string2;
                    break block5;
                }
                catch (RuntimeException runtimeException) {
                    throw BuildAgentManagerImpl.a(runtimeException);
                }
            }
            object = "";
        }
        String string3 = object;
        Loggers.AGENT.warn("Agent " + buildAgentEx.describe(false) + string + " on registration" + string3);
    }

    public synchronized void unregisterAgent(int n) {
        block4: {
            String string;
            BuildAgentEx buildAgentEx;
            BuildAgentManagerImpl buildAgentManagerImpl;
            block6: {
                block5: {
                    BuildAgentEx buildAgentEx2 = this.findAgentById(n, false);
                    try {
                        try {
                            if (buildAgentEx2 == null) break block4;
                            buildAgentManagerImpl = this;
                            buildAgentEx = buildAgentEx2;
                            if (!buildAgentEx2.isUpgrading()) break block5;
                        }
                        catch (RuntimeException runtimeException) {
                            throw BuildAgentManagerImpl.a(runtimeException);
                        }
                        string = "Agent has unregistered (will upgrade)";
                        break block6;
                    }
                    catch (RuntimeException runtimeException) {
                        throw BuildAgentManagerImpl.a(runtimeException);
                    }
                }
                string = "Agent has unregistered";
            }
            buildAgentManagerImpl.unregisterAgent(buildAgentEx, string, true);
        }
    }

    public synchronized void unregisterAgent(int n, String string) {
        BuildAgentEx buildAgentEx = this.findAgentById(n, false);
        try {
            if (buildAgentEx != null) {
                this.unregisterAgent(buildAgentEx, string, false);
            }
        }
        catch (RuntimeException runtimeException) {
            throw BuildAgentManagerImpl.a(runtimeException);
        }
    }

    public synchronized void unregisterAllAgents(String string, boolean bl) {
        for (BuildAgentEx buildAgentEx : this.myAgents.getAll()) {
            this.unregisterAgent(buildAgentEx, string, bl);
        }
    }

    public BuildAgentManager.RunConfigurationPolicy getRunConfigurationPolicy(SBuildAgent sBuildAgent) {
        return this.myAgentTypeManager.getRunConfigurationPolicy(sBuildAgent.getAgentTypeId());
    }

    @Deprecated
    public void setCanRunConfiguration(@NotNull SBuildAgent sBuildAgent, String string, boolean bl) {
        block11: {
            int n;
            block10: {
                block9: {
                    try {
                        if (sBuildAgent == null) {
                            BuildAgentManagerImpl.$$$reportNull$$$0(47);
                        }
                    }
                    catch (RuntimeException runtimeException) {
                        throw BuildAgentManagerImpl.a(runtimeException);
                    }
                    n = sBuildAgent.getAgentTypeId();
                    try {
                        try {
                            if ($assertionsDisabled || this.myAgentTypeStorage != null) break block9;
                        }
                        catch (RuntimeException runtimeException) {
                            throw BuildAgentManagerImpl.a(runtimeException);
                        }
                        throw new AssertionError();
                    }
                    catch (RuntimeException runtimeException) {
                        throw BuildAgentManagerImpl.a(runtimeException);
                    }
                }
                try {
                    if (!bl) break block10;
                    this.myAgentTypeStorage.includeBuildTypesToAllowed(n, Collections.singleton(string));
                    break block11;
                }
                catch (RuntimeException runtimeException) {
                    throw BuildAgentManagerImpl.a(runtimeException);
                }
            }
            this.myAgentTypeStorage.excludeBuildTypesFromAllowed(n, Collections.singleton(string));
        }
    }

    public boolean isCanRunConfiguration(@NotNull SBuildAgent sBuildAgent, BuildTypeDescriptor buildTypeDescriptor) {
        try {
            if (sBuildAgent == null) {
                BuildAgentManagerImpl.$$$reportNull$$$0(48);
            }
        }
        catch (RuntimeException runtimeException) {
            throw BuildAgentManagerImpl.a(runtimeException);
        }
        return this.myAgentTypeManager.isCanRunConfiguration(sBuildAgent.getAgentTypeId(), buildTypeDescriptor);
    }

    public Set<String> getCanRunConfigurations(SBuildAgent sBuildAgent) {
        return this.myAgentTypeManager.getCanRunConfigurations(sBuildAgent.getAgentTypeId());
    }

    public void setRunConfigurationPolicy(@NotNull SBuildAgent sBuildAgent, @NotNull BuildAgentManager.RunConfigurationPolicy runConfigurationPolicy) {
        try {
            if (sBuildAgent == null) {
                BuildAgentManagerImpl.$$$reportNull$$$0(49);
            }
        }
        catch (RuntimeException runtimeException) {
            throw BuildAgentManagerImpl.a(runtimeException);
        }
        try {
            if (runConfigurationPolicy == null) {
                BuildAgentManagerImpl.$$$reportNull$$$0(50);
            }
        }
        catch (RuntimeException runtimeException) {
            throw BuildAgentManagerImpl.a(runtimeException);
        }
        this.myAgentTypeManager.setRunConfigurationPolicy(sBuildAgent.getAgentTypeId(), runConfigurationPolicy);
    }

    /*
     * Loose catch block
     */
    public void pingAllAgents() {
        boolean bl;
        try {
            bl = this.myTimeService.now() - this.myServerStartTime > this.getGracePeriodMillis();
        }
        catch (Throwable throwable) {
            throw BuildAgentManagerImpl.a(throwable);
        }
        boolean bl2 = bl;
        boolean bl3 = this.isPollFreshEnoughForPing();
        for (BuildAgentEx buildAgentEx : this.myAgents.getAll()) {
            try {
                if (buildAgentEx.ping()) {
                    this.myAgentsWithFailedPing.remove(buildAgentEx.getId());
                    continue;
                }
                if (!bl2) continue;
                try {
                    block10: {
                        if (!bl3) continue;
                        break block10;
                        catch (Throwable throwable) {
                            throw BuildAgentManagerImpl.a(throwable);
                        }
                    }
                    this.myAgentsWithFailedPing.put(buildAgentEx.getId(), this.myTimeService.now());
                }
                catch (Throwable throwable) {
                    throw BuildAgentManagerImpl.a(throwable);
                }
            }
            catch (Throwable throwable) {
                Loggers.AGENT.warnAndDebugDetails("Error while checking connectivity with the agent " + LogUtil.describe((Loggable)buildAgentEx), throwable);
            }
        }
    }

    public static boolean isPollBasedCommunicationTimestampEnabled() {
        return TeamCityProperties.getBoolean((String)"teamcity.agent.pollFromDB.enabled");
    }

    private boolean isPollFreshEnoughForPing() {
        boolean bl;
        try {
            if (!BuildAgentManagerImpl.isPollBasedCommunicationTimestampEnabled()) {
                return true;
            }
        }
        catch (RuntimeException runtimeException) {
            throw BuildAgentManagerImpl.a(runtimeException);
        }
        long l = TeamCityProperties.getIntervalMilliseconds((String)"teamcity.agent.pollFromDB.delay", (long)20000L);
        long l2 = TeamCityProperties.getIntervalMilliseconds((String)"teamcity.agentsMonitor.intervalMillis", (long)20000L);
        try {
            bl = this.myTimeService.now() - this.myLastSuccessfulPollTimestamp < l + l2;
        }
        catch (RuntimeException runtimeException) {
            throw BuildAgentManagerImpl.a(runtimeException);
        }
        return bl;
    }

    @VisibleForTesting
    boolean hasFailedPing(int n) {
        return this.myAgentsWithFailedPing.containsKey(n);
    }

    @VisibleForTesting
    void setLastSuccessfulPollTimestamp(long l) {
        this.myLastSuccessfulPollTimestamp = l;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     */
    private synchronized void unregisterAgent(@NotNull BuildAgentEx buildAgentEx, String string, boolean bl) {
        block18: {
            block19: {
                block17: {
                    block20: {
                        try {
                            if (buildAgentEx == null) {
                                BuildAgentManagerImpl.$$$reportNull$$$0(51);
                            }
                        }
                        catch (RuntimeException runtimeException) {
                            throw BuildAgentManagerImpl.a(runtimeException);
                        }
                        if (this.myServerResponsibility.canManageAgents()) break block17;
                        if (this.myServerResponsibility.canModifyDataInDatabase()) break block17;
                        break block20;
                        catch (RuntimeException runtimeException) {
                            throw BuildAgentManagerImpl.a(runtimeException);
                        }
                    }
                    try {
                        block21: {
                            if (TeamCityProperties.getBooleanOrTrue((String)"teamcity.agent.unregisterOnSecondaryNodes")) break block17;
                            break block21;
                            catch (RuntimeException runtimeException) {
                                throw BuildAgentManagerImpl.a(runtimeException);
                            }
                        }
                        throw new MissingServerResponsibilityException("This server operation mode does not support agents registration");
                    }
                    catch (RuntimeException runtimeException) {
                        throw BuildAgentManagerImpl.a(runtimeException);
                    }
                }
                this.myAgentsWithFailedPing.remove(buildAgentEx.getId());
                if (this.findAgentById(buildAgentEx.getId(), false) == buildAgentEx) break block18;
                try {
                    block22: {
                        Loggers.AGENT.warn("Attempt to unregister already unregistered agent: " + buildAgentEx.describe(true));
                        if (!bl) break block19;
                        break block22;
                        catch (RuntimeException runtimeException) {
                            throw BuildAgentManagerImpl.a(runtimeException);
                        }
                    }
                    this.cancelBuild((SBuildAgent)buildAgentEx, string);
                }
                catch (RuntimeException runtimeException) {
                    throw BuildAgentManagerImpl.a(runtimeException);
                }
            }
            return;
        }
        ((BuildServerListener)this.myDispatcher.getMulticaster()).beforeAgentUnregistered((SBuildAgent)buildAgentEx);
        Object object = buildAgentEx;
        synchronized (object) {
            buildAgentEx.unregister(string);
            this.myAgents.remove((Object)buildAgentEx.getId());
            if (bl) {
                this.cancelBuild((SBuildAgent)buildAgentEx, string);
            }
        }
        ((BuildServerListener)this.myDispatcher.getMulticaster()).agentUnregistered((SBuildAgent)buildAgentEx);
        ((BuildServerListener)this.myDispatcher.getMulticaster()).agentUnregistered((SBuildAgent)buildAgentEx, string);
        object = StringUtil.truncateStringValue((String)string, (int)255);
        this.myMultiNodesEvents.publish(new MultiNodesEvents.EventData("agentUnregistered", Long.valueOf(buildAgentEx.getId()), null, (String)object));
        Loggers.AGENT.info("Agent " + LogUtil.describe((Loggable)buildAgentEx) + " has been unregistered. Reason: " + string);
    }

    private void cancelBuild(SBuildAgent sBuildAgent, String string) {
        block4: {
            RunningBuildEx runningBuildEx = this.myRunningBuildManager.getRunningBuildOnAgent(sBuildAgent);
            try {
                try {
                    if (runningBuildEx == null) break block4;
                    runningBuildEx.updateBuild();
                    if (runningBuildEx.isFinishReceived()) break block4;
                }
                catch (RuntimeException runtimeException) {
                    throw BuildAgentManagerImpl.a(runtimeException);
                }
                runningBuildEx.markAsFinishedUnexpectedly("Agent " + runningBuildEx.getAgentName() + " unregistered: " + string);
            }
            catch (RuntimeException runtimeException) {
                throw BuildAgentManagerImpl.a(runtimeException);
            }
        }
    }

    private void checkRunningBuild(long l, @NotNull SBuildAgent sBuildAgent) {
        block11: {
            try {
                if (sBuildAgent == null) {
                    BuildAgentManagerImpl.$$$reportNull$$$0(52);
                }
            }
            catch (RuntimeException runtimeException) {
                throw BuildAgentManagerImpl.a(runtimeException);
            }
            if (l >= 0L) {
                RunningBuildEx runningBuildEx;
                block10: {
                    runningBuildEx = this.myRunningBuildManager.findRunningBuildById(l);
                    try {
                        if (runningBuildEx == null || runningBuildEx.getBuildType() != null) break block10;
                    }
                    catch (RuntimeException runtimeException) {
                        throw BuildAgentManagerImpl.a(runtimeException);
                    }
                    runningBuildEx.markAsFinishedUnexpectedly(LogUtil.describeMissingBuildTypeForUI((Build)runningBuildEx));
                    runningBuildEx = null;
                }
                try {
                    try {
                        if (l <= 0L || runningBuildEx != null) break block11;
                    }
                    catch (RuntimeException runtimeException) {
                        throw BuildAgentManagerImpl.a(runtimeException);
                    }
                    Loggers.AGENT.warn(MessageFormat.format("Error on agent registration: No running build with id {0} found. Agent: {1}", String.valueOf(l), sBuildAgent.describe(true)));
                    throw new InvalidRunningBuildException(l);
                }
                catch (RuntimeException runtimeException) {
                    throw BuildAgentManagerImpl.a(runtimeException);
                }
            }
            this.removeZombieBuilds(sBuildAgent);
        }
    }

    private void removeZombieBuilds(@NotNull SBuildAgent sBuildAgent) {
        try {
            if (sBuildAgent == null) {
                BuildAgentManagerImpl.$$$reportNull$$$0(53);
            }
        }
        catch (RuntimeException runtimeException) {
            throw BuildAgentManagerImpl.a(runtimeException);
        }
        for (RunningBuildEx runningBuildEx : this.myRunningBuildManager.getRunningBuildsEx()) {
            if (runningBuildEx.isDetachedFromAgent()) continue;
            try {
                if (runningBuildEx.isAgentLessBuild()) {
                    continue;
                }
            }
            catch (RuntimeException runtimeException) {
                throw BuildAgentManagerImpl.a(runtimeException);
            }
            try {
                try {
                    if (runningBuildEx.getAgentId() != sBuildAgent.getId() || !runningBuildEx.updateBuildInfo()) {
                        continue;
                    }
                }
                catch (RuntimeException runtimeException) {
                    throw BuildAgentManagerImpl.a(runtimeException);
                }
            }
            catch (RuntimeException runtimeException) {
                throw BuildAgentManagerImpl.a(runtimeException);
            }
            try {
                if (runningBuildEx.isFinishReceived()) {
                    continue;
                }
            }
            catch (RuntimeException runtimeException) {
                throw BuildAgentManagerImpl.a(runtimeException);
            }
            try {
                if (runningBuildEx.isStarting()) {
                    continue;
                }
            }
            catch (RuntimeException runtimeException) {
                throw BuildAgentManagerImpl.a(runtimeException);
            }
            Loggers.AGENT.warn("Agent (" + LogUtil.describe((Loggable)sBuildAgent) + ") is not running build (" + LogUtil.describe((Build)runningBuildEx) + ") anymore, build will be stopped.");
            runningBuildEx.markAsFinishedUnexpectedly("Build and agent have finished unexpectedly or were killed. Please check agent logs for details.");
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     */
    public void removeAgent(@NotNull SBuildAgent sBuildAgent, @Nullable SUser sUser) throws AgentCannotBeRemovedException {
        block21: {
            BuildAgentEx buildAgentEx;
            block20: {
                try {
                    if (sBuildAgent == null) {
                        BuildAgentManagerImpl.$$$reportNull$$$0(54);
                    }
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildAgentManagerImpl.a(illegalStateException);
                }
                buildAgentEx = this.findAgentById(sBuildAgent.getId(), true);
                try {
                    if (buildAgentEx == null) {
                        return;
                    }
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildAgentManagerImpl.a(illegalStateException);
                }
                try {
                    if (buildAgentEx.isRegistered()) {
                        throw new AgentCannotBeRemovedException("Agent \"" + buildAgentEx.getName() + "\" cannot be removed because it is currently connected to the server, please shutdown agent first.");
                    }
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildAgentManagerImpl.a(illegalStateException);
                }
                SRunningBuild sRunningBuild = buildAgentEx.getRunningBuild();
                String string = "Agent removed";
                if (sRunningBuild == null) break block20;
                try {
                    block22: {
                        if (sRunningBuild.isDetachedFromAgent()) break block20;
                        break block22;
                        catch (IllegalStateException illegalStateException) {
                            throw BuildAgentManagerImpl.a(illegalStateException);
                        }
                    }
                    sRunningBuild.stop((User)sUser, "Agent removed");
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildAgentManagerImpl.a(illegalStateException);
                }
            }
            BuildAgentEx buildAgentEx2 = buildAgentEx;
            synchronized (buildAgentEx2) {
                this.myAgents.remove((Object)buildAgentEx.getId());
                buildAgentEx.markAsRemoved();
                this.myAgentPersister.removeAgent(buildAgentEx);
                this.myAgentNames.remove(buildAgentEx.getName());
            }
            this.myMultiNodesEvents.publish("agentRemoved", Long.valueOf(buildAgentEx.getId()));
            this.myAuditLogFactory.createForServer().logUserAction(ActionType.AGENT_DELETE, null, null, new Object[]{buildAgentEx});
            buildAgentEx2 = buildAgentEx.getAgentType();
            try {
                if (buildAgentEx2.isCloud()) break block21;
                Loggers.SERVER.info("Removing the agent type with id " + buildAgentEx2.getAgentTypeId() + " (key: " + String.valueOf(buildAgentEx2.getAgentTypeKey()) + ") associated with the just removed agent: " + LogUtil.describe((Loggable)buildAgentEx));
                this.myAgentTypeStorage.deleteAgentType(buildAgentEx.getAgentTypeId());
                break block21;
            }
            catch (AgentCannotBeRemovedException agentCannotBeRemovedException) {
                throw BuildAgentManagerImpl.a(agentCannotBeRemovedException);
            }
            {
                catch (IllegalStateException illegalStateException) {
                    Loggers.SERVER.warnAndDebugDetails("Agent type claims it's not cloud, while in fact it is being used by several agents: " + String.valueOf(buildAgentEx2), (Throwable)illegalStateException);
                }
            }
        }
    }

    public <T extends SBuildAgent> int getNumberOfCompatibleConfigurations(T t) {
        return this.myAgentTypeManager.getNumberOfCompatibleConfigurations(((BuildAgentEx)t).getAgentType());
    }

    public <T extends SBuildAgent> int getNumberOfIncompatibleConfigurations(T t) {
        return this.myAgentTypeManager.getNumberOfIncompatibleConfigurations(((BuildAgentEx)t).getAgentType());
    }

    public <T extends SBuildAgent> List<AgentCompatibility> getAgentCompatibilities(T t) {
        return this.myAgentTypeManager.getAgentTypeCompatibilities(((BuildAgentEx)t).getAgentType());
    }

    public Map<BuildAgentManager.AgentGroup, Integer> getAgentStats() {
        HashMap<BuildAgentManager.AgentGroup, Integer> hashMap = new HashMap<BuildAgentManager.AgentGroup, Integer>();
        int[] nArray = new int[2];
        this.myAgents.processItems(buildAgentEx -> {
            int n;
            int[] nArray2;
            try {
                nArray2 = nArray;
                n = buildAgentEx.isAuthorized() ? 0 : 1;
            }
            catch (RuntimeException runtimeException) {
                throw BuildAgentManagerImpl.a(runtimeException);
            }
            nArray2[n] = nArray2[n] + 1;
            return true;
        });
        hashMap.put(BuildAgentManager.AgentGroup.REGISTERED_AUTHORIZED, nArray[0]);
        hashMap.put(BuildAgentManager.AgentGroup.REGISTERED_UNAUTHORIZED, nArray[1]);
        nArray[0] = 0;
        nArray[1] = 0;
        List list = this.myUnregisteredAgentsManager.getUnregisteredAgents(true);
        for (AgentData agentData : list) {
            int n;
            int[] nArray2;
            try {
                nArray2 = nArray;
                n = agentData.authorized ? 0 : 1;
            }
            catch (RuntimeException runtimeException) {
                throw BuildAgentManagerImpl.a(runtimeException);
            }
            nArray2[n] = nArray2[n] + 1;
        }
        hashMap.put(BuildAgentManager.AgentGroup.UNREGISTERED_AUTHORIZED, nArray[0]);
        hashMap.put(BuildAgentManager.AgentGroup.UNREGISTERED_UNAUTHORIZED, nArray[1]);
        return hashMap;
    }

    private void publishAgentDescriptionUpdatedToOtherNodes(@NotNull BuildAgentEx buildAgentEx) {
        try {
            if (buildAgentEx == null) {
                BuildAgentManagerImpl.$$$reportNull$$$0(55);
            }
        }
        catch (RuntimeException runtimeException) {
            throw BuildAgentManagerImpl.a(runtimeException);
        }
        this.myMultiNodesEvents.publish(new MultiNodesEvents.EventData("agentDescriptionUpdated", Long.valueOf(buildAgentEx.getId()), Long.valueOf(buildAgentEx.getAgentTypeId()), null));
    }

    private long getGracePeriodMillis() {
        return TeamCityProperties.getLong((String)"teamcity.agent.ping.gracePeriodAfterStartup.millis", (long)(5L * PollingRemoteAgentConnection.getAcceptableAgentInactivityMillis()));
    }

    @VisibleForTesting
    public void setTimeService(@NotNull TimeService timeService) {
        try {
            if (timeService == null) {
                BuildAgentManagerImpl.$$$reportNull$$$0(56);
            }
        }
        catch (RuntimeException runtimeException) {
            throw BuildAgentManagerImpl.a(runtimeException);
        }
        this.myTimeService = timeService;
    }

    public static boolean agentCommandsRequireAuthorization() {
        boolean bl;
        String string;
        try {
            string = "teamcity.agent.commands.requireAuthorization";
            bl = !DevelopmentMode.isEnabled;
        }
        catch (RuntimeException runtimeException) {
            throw BuildAgentManagerImpl.a(runtimeException);
        }
        return TeamCityProperties.getBoolean((String)string, (boolean)bl);
    }

    static {
        boolean bl;
        try {
            bl = !BuildAgentManagerImpl.class.desiredAssertionStatus();
        }
        catch (RuntimeException runtimeException) {
            throw BuildAgentManagerImpl.a(runtimeException);
        }
        $assertionsDisabled = bl;
        AGENTS_COMPARATOR = (buildAgent, buildAgent2) -> buildAgent.getName().compareToIgnoreCase(buildAgent2.getName());
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
