/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.annotations.VisibleForTesting
 *  com.google.common.base.Stopwatch
 *  com.google.common.io.CountingOutputStream
 *  com.intellij.openapi.diagnostic.Logger
 *  com.intellij.openapi.util.Pair
 *  javax.annotation.Nonnegative
 *  jetbrains.buildServer.AgentRestrictorType
 *  jetbrains.buildServer.Build
 *  jetbrains.buildServer.BuildAgent
 *  jetbrains.buildServer.BuildAuthUtil
 *  jetbrains.buildServer.BuildProblemData
 *  jetbrains.buildServer.StatusDescriptor
 *  jetbrains.buildServer.Used
 *  jetbrains.buildServer.UserDataStorage
 *  jetbrains.buildServer.UserDataStorageImpl
 *  jetbrains.buildServer.agentServer.AgentBuild
 *  jetbrains.buildServer.agentServer.AgentCheckoutDirRevisionInfo
 *  jetbrains.buildServer.agentServer.BuildRunnerData
 *  jetbrains.buildServer.artifacts.ArtifactData
 *  jetbrains.buildServer.artifacts.util.ArtifactListUtil
 *  jetbrains.buildServer.artifacts.util.SerializableArtifactListData
 *  jetbrains.buildServer.log.LogInitializer
 *  jetbrains.buildServer.log.Loggers
 *  jetbrains.buildServer.messages.BuildMessage1
 *  jetbrains.buildServer.messages.Status
 *  jetbrains.buildServer.metrics.ServerLoadMetrics
 *  jetbrains.buildServer.metrics.ServerLoadMetricsAwareOutputStream
 *  jetbrains.buildServer.parameters.ParametersUtil
 *  jetbrains.buildServer.parameters.ProcessingResult
 *  jetbrains.buildServer.parameters.ValueResolver
 *  jetbrains.buildServer.problems.BuildProblemUtil
 *  jetbrains.buildServer.serverSide.BuildAgentEx
 *  jetbrains.buildServer.serverSide.BuildPromotion
 *  jetbrains.buildServer.serverSide.BuildPromotionEx
 *  jetbrains.buildServer.serverSide.BuildPromotionEx$UpdateReason
 *  jetbrains.buildServer.serverSide.BuildServerListener
 *  jetbrains.buildServer.serverSide.BuildSettings
 *  jetbrains.buildServer.serverSide.BuildStartContext
 *  jetbrains.buildServer.serverSide.BuildStartContextProcessor
 *  jetbrains.buildServer.serverSide.BuildTypeEx
 *  jetbrains.buildServer.serverSide.BuildTypeNotFoundException
 *  jetbrains.buildServer.serverSide.BuildTypeSettings
 *  jetbrains.buildServer.serverSide.ControlDescription
 *  jetbrains.buildServer.serverSide.HistoryBuild
 *  jetbrains.buildServer.serverSide.InaccessibleArtifactDependencyException
 *  jetbrains.buildServer.serverSide.Parameter
 *  jetbrains.buildServer.serverSide.ParametersPreprocessor
 *  jetbrains.buildServer.serverSide.RunningBuildEx
 *  jetbrains.buildServer.serverSide.SAgentRestrictor
 *  jetbrains.buildServer.serverSide.SBuild
 *  jetbrains.buildServer.serverSide.SBuildStepDescriptor
 *  jetbrains.buildServer.serverSide.SBuildType
 *  jetbrains.buildServer.serverSide.SRunningBuild
 *  jetbrains.buildServer.serverSide.ShortStatistics
 *  jetbrains.buildServer.serverSide.TeamCityProperties
 *  jetbrains.buildServer.serverSide.artifacts.SArtifactDependency
 *  jetbrains.buildServer.serverSide.artifacts.limits.ArtifactsUploadLimit
 *  jetbrains.buildServer.serverSide.build.steps.BuildStepsEditor
 *  jetbrains.buildServer.serverSide.build.steps.GroupStepContext
 *  jetbrains.buildServer.serverSide.buildDistribution.BuildConfigurationInfo
 *  jetbrains.buildServer.serverSide.buildDistribution.BuildPromotionInfo
 *  jetbrains.buildServer.serverSide.buildLog.BlockDescriptor
 *  jetbrains.buildServer.serverSide.buildLog.BuildLogEx
 *  jetbrains.buildServer.serverSide.buildLog.MessageAttrs
 *  jetbrains.buildServer.serverSide.db.DBDuplicatedRowException
 *  jetbrains.buildServer.serverSide.db.DBException
 *  jetbrains.buildServer.serverSide.db.MySQL.MySqlIncorrectStringValueException
 *  jetbrains.buildServer.serverSide.db.SQLRunnerEx
 *  jetbrains.buildServer.serverSide.db.UnicodeString
 *  jetbrains.buildServer.serverSide.impl.BaseRunningBuild
 *  jetbrains.buildServer.serverSide.impl.BaseRunningBuild$ArtifactsStats
 *  jetbrains.buildServer.serverSide.impl.BuildPromotionImpl
 *  jetbrains.buildServer.serverSide.impl.BuildStarter
 *  jetbrains.buildServer.serverSide.impl.CompositeRunningBuild
 *  jetbrains.buildServer.serverSide.impl.LogUtil
 *  jetbrains.buildServer.serverSide.impl.RestoreBuildInQueue
 *  jetbrains.buildServer.serverSide.impl.RunningBuildState
 *  jetbrains.buildServer.serverSide.impl.artifacts.PublishArtifactProgressListener
 *  jetbrains.buildServer.serverSide.impl.beans.BuildContext
 *  jetbrains.buildServer.serverSide.impl.build.steps.BuildStartContextImpl
 *  jetbrains.buildServer.serverSide.impl.history.HistoryBuildSnapshot
 *  jetbrains.buildServer.serverSide.impl.history.RunningBuildData
 *  jetbrains.buildServer.serverSide.impl.history.RunningBuildProgressData
 *  jetbrains.buildServer.serverSide.impl.projectSources.PatchEventsListener
 *  jetbrains.buildServer.serverSide.impl.query.RunningBuildDeleteQuery
 *  jetbrains.buildServer.serverSide.impl.query.RunningBuildInsertQuery
 *  jetbrains.buildServer.serverSide.impl.timeEstimation.BuildDurationEstimateContext
 *  jetbrains.buildServer.serverSide.mute.TestRunMuter
 *  jetbrains.buildServer.serverSide.problems.BuildProblem
 *  jetbrains.buildServer.serverSide.problems.BuildProblemProvider
 *  jetbrains.buildServer.serverSide.util.ChainBuildNotFoundException
 *  jetbrains.buildServer.serverSide.vcs.FullCheckoutFlag
 *  jetbrains.buildServer.tests.TestInfo
 *  jetbrains.buildServer.users.User
 *  jetbrains.buildServer.util.ArchiveUtil
 *  jetbrains.buildServer.util.Disposable
 *  jetbrains.buildServer.util.ExceptionUtil
 *  jetbrains.buildServer.util.FileUtil
 *  jetbrains.buildServer.util.NamedDaemonThreadFactory
 *  jetbrains.buildServer.util.NamedThreadFactory
 *  jetbrains.buildServer.util.StringUtil
 *  jetbrains.buildServer.util.TCStreamUtil
 *  jetbrains.buildServer.util.TCStreamUtil$WriteProgressListener
 *  jetbrains.buildServer.util.ThreadUtil
 *  jetbrains.buildServer.util.TimePrinter
 *  jetbrains.buildServer.util.impl.Lazy
 *  jetbrains.buildServer.vcs.VcsChangeInfo
 *  jetbrains.buildServer.vcs.VcsChangeInfo$Type
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package jetbrains.buildServer.serverSide.impl;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Stopwatch;
import com.google.common.io.CountingOutputStream;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.util.Pair;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.invoke.LambdaMetafactory;
import java.math.BigDecimal;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.annotation.Nonnegative;
import jetbrains.buildServer.AgentRestrictorType;
import jetbrains.buildServer.Build;
import jetbrains.buildServer.BuildAgent;
import jetbrains.buildServer.BuildAuthUtil;
import jetbrains.buildServer.BuildProblemData;
import jetbrains.buildServer.StatusDescriptor;
import jetbrains.buildServer.Used;
import jetbrains.buildServer.UserDataStorage;
import jetbrains.buildServer.UserDataStorageImpl;
import jetbrains.buildServer.agentServer.AgentBuild;
import jetbrains.buildServer.agentServer.AgentCheckoutDirRevisionInfo;
import jetbrains.buildServer.agentServer.BuildRunnerData;
import jetbrains.buildServer.artifacts.ArtifactData;
import jetbrains.buildServer.artifacts.util.ArtifactListUtil;
import jetbrains.buildServer.artifacts.util.SerializableArtifactListData;
import jetbrains.buildServer.log.LogInitializer;
import jetbrains.buildServer.log.Loggers;
import jetbrains.buildServer.messages.BuildMessage1;
import jetbrains.buildServer.messages.Status;
import jetbrains.buildServer.metrics.ServerLoadMetrics;
import jetbrains.buildServer.metrics.ServerLoadMetricsAwareOutputStream;
import jetbrains.buildServer.parameters.ParametersUtil;
import jetbrains.buildServer.parameters.ProcessingResult;
import jetbrains.buildServer.parameters.ValueResolver;
import jetbrains.buildServer.problems.BuildProblemUtil;
import jetbrains.buildServer.serverSide.BuildAgentEx;
import jetbrains.buildServer.serverSide.BuildPromotion;
import jetbrains.buildServer.serverSide.BuildPromotionEx;
import jetbrains.buildServer.serverSide.BuildServerListener;
import jetbrains.buildServer.serverSide.BuildSettings;
import jetbrains.buildServer.serverSide.BuildStartContext;
import jetbrains.buildServer.serverSide.BuildStartContextProcessor;
import jetbrains.buildServer.serverSide.BuildTypeEx;
import jetbrains.buildServer.serverSide.BuildTypeNotFoundException;
import jetbrains.buildServer.serverSide.BuildTypeSettings;
import jetbrains.buildServer.serverSide.ControlDescription;
import jetbrains.buildServer.serverSide.HistoryBuild;
import jetbrains.buildServer.serverSide.InaccessibleArtifactDependencyException;
import jetbrains.buildServer.serverSide.Parameter;
import jetbrains.buildServer.serverSide.ParametersPreprocessor;
import jetbrains.buildServer.serverSide.RunningBuildEx;
import jetbrains.buildServer.serverSide.SAgentRestrictor;
import jetbrains.buildServer.serverSide.SBuild;
import jetbrains.buildServer.serverSide.SBuildStepDescriptor;
import jetbrains.buildServer.serverSide.SBuildType;
import jetbrains.buildServer.serverSide.SRunningBuild;
import jetbrains.buildServer.serverSide.ShortStatistics;
import jetbrains.buildServer.serverSide.TeamCityProperties;
import jetbrains.buildServer.serverSide.artifacts.SArtifactDependency;
import jetbrains.buildServer.serverSide.artifacts.limits.ArtifactsUploadLimit;
import jetbrains.buildServer.serverSide.build.steps.BuildStepsEditor;
import jetbrains.buildServer.serverSide.build.steps.GroupStepContext;
import jetbrains.buildServer.serverSide.buildDistribution.BuildConfigurationInfo;
import jetbrains.buildServer.serverSide.buildDistribution.BuildPromotionInfo;
import jetbrains.buildServer.serverSide.buildLog.BlockDescriptor;
import jetbrains.buildServer.serverSide.buildLog.BuildLogEx;
import jetbrains.buildServer.serverSide.buildLog.MessageAttrs;
import jetbrains.buildServer.serverSide.db.DBDuplicatedRowException;
import jetbrains.buildServer.serverSide.db.DBException;
import jetbrains.buildServer.serverSide.db.MySQL.MySqlIncorrectStringValueException;
import jetbrains.buildServer.serverSide.db.SQLRunnerEx;
import jetbrains.buildServer.serverSide.db.UnicodeString;
import jetbrains.buildServer.serverSide.impl.BaseRunningBuild;
import jetbrains.buildServer.serverSide.impl.BuildPromotionImpl;
import jetbrains.buildServer.serverSide.impl.BuildStarter;
import jetbrains.buildServer.serverSide.impl.CompositeRunningBuild;
import jetbrains.buildServer.serverSide.impl.LogUtil;
import jetbrains.buildServer.serverSide.impl.RestoreBuildInQueue;
import jetbrains.buildServer.serverSide.impl.RunningBuildState;
import jetbrains.buildServer.serverSide.impl.artifacts.PublishArtifactProgressListener;
import jetbrains.buildServer.serverSide.impl.beans.BuildContext;
import jetbrains.buildServer.serverSide.impl.build.steps.BuildStartContextImpl;
import jetbrains.buildServer.serverSide.impl.history.HistoryBuildSnapshot;
import jetbrains.buildServer.serverSide.impl.history.RunningBuildData;
import jetbrains.buildServer.serverSide.impl.history.RunningBuildProgressData;
import jetbrains.buildServer.serverSide.impl.projectSources.PatchEventsListener;
import jetbrains.buildServer.serverSide.impl.query.RunningBuildDeleteQuery;
import jetbrains.buildServer.serverSide.impl.query.RunningBuildInsertQuery;
import jetbrains.buildServer.serverSide.impl.timeEstimation.BuildDurationEstimateContext;
import jetbrains.buildServer.serverSide.mute.TestRunMuter;
import jetbrains.buildServer.serverSide.problems.BuildProblem;
import jetbrains.buildServer.serverSide.problems.BuildProblemProvider;
import jetbrains.buildServer.serverSide.util.ChainBuildNotFoundException;
import jetbrains.buildServer.serverSide.vcs.FullCheckoutFlag;
import jetbrains.buildServer.tests.TestInfo;
import jetbrains.buildServer.users.User;
import jetbrains.buildServer.util.ArchiveUtil;
import jetbrains.buildServer.util.Disposable;
import jetbrains.buildServer.util.ExceptionUtil;
import jetbrains.buildServer.util.FileUtil;
import jetbrains.buildServer.util.NamedDaemonThreadFactory;
import jetbrains.buildServer.util.NamedThreadFactory;
import jetbrains.buildServer.util.StringUtil;
import jetbrains.buildServer.util.TCStreamUtil;
import jetbrains.buildServer.util.ThreadUtil;
import jetbrains.buildServer.util.TimePrinter;
import jetbrains.buildServer.util.impl.Lazy;
import jetbrains.buildServer.vcs.VcsChangeInfo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Exception performing whole class analysis ignored.
 */
public class RunningBuildImpl
extends BaseRunningBuild
implements RunningBuildEx {
    private static final Logger LOG;
    private final ReentrantLock myPublishArtifactsLock;
    private final int myAgentId;
    private final int myAgentTypeId;
    private final String myAgentName;
    private long myBuildCounter;
    private volatile String myRawBuildNumber;
    private volatile String myProbablyResolvedBuildNumber;
    private final AtomicReference<ShortStatistics> myShortStatistics;
    private final AtomicReference<Map<String, Boolean>> myLazyRunnersState;
    private volatile Date myLastBuildActivityTimestamp;
    private final String myAgentAccessCode;
    private final Pair<Integer, Integer> myQueuedAgentRestrictorData;
    private volatile BuildStarter myBuildStarter;
    private volatile BuildAgentEx myAgent;
    private final AtomicInteger myAgentResetCount;
    private final AtomicBoolean myPersisted;
    @NotNull
    private final TestRunMuter myTestRunMuter;
    @NotNull
    private final UserDataStorage myUserDataStorage;
    private volatile long myHangingBuildsCalculationEnabledSince;
    private volatile Date myFinishTimeServer;
    private volatile boolean myStartingBuild;
    private volatile String myProgressText;
    private volatile String myCurrentPathText;
    @NotNull
    private final BaseRunningBuild.ArtifactsStats myArtifactsStats;
    private final Lazy<ArtifactsUploadLimit> myArtifactsLimit;
    @Nullable
    private volatile Long myDurationEstimate;
    private static final int EXCEEDED_ESTIMATION_ABSOLUTE_THRESHOLD = 300;
    private static final int TIME_SPENT_SINCE_LAST_ACTIVITY_ABSOLUTE_THRESHOLD = 900;
    private static final float EXCEEDED_ESTIMATION_PERCENT_THRESHOLD = 0.1f;
    private static final float TIME_SPENT_SINCE_LAST_ACTIVITY_PERCENT_THRESHOLD = 0.2f;
    static final /* synthetic */ boolean $assertionsDisabled;

    protected RunningBuildImpl(@NotNull BuildPromotionEx buildPromotionEx, @Nullable SAgentRestrictor sAgentRestrictor, String string, int n, int n2, @Nullable String string2, @NotNull BuildContext buildContext) {
        Pair pair;
        if (buildPromotionEx == null) {
            RunningBuildImpl.$$$reportNull$$$0(0);
        }
        if (buildContext == null) {
            RunningBuildImpl.$$$reportNull$$$0(1);
        }
        super(buildPromotionEx.getId(), string, buildPromotionEx, buildContext);
        this.myPublishArtifactsLock = new ReentrantLock();
        this.myShortStatistics = new AtomicReference();
        this.myLazyRunnersState = new AtomicReference();
        this.myAgentResetCount = new AtomicInteger();
        this.myPersisted = new AtomicBoolean();
        this.myUserDataStorage = new UserDataStorageImpl();
        this.myHangingBuildsCalculationEnabledSince = -1L;
        this.myFinishTimeServer = null;
        this.myStartingBuild = false;
        this.myArtifactsStats = new BaseRunningBuild.ArtifactsStats();
        this.myArtifactsLimit = new /* Unavailable Anonymous Inner Class!! */;
        if (buildPromotionEx.isCompositeBuild()) {
            Thread thread = Thread.currentThread();
            throw new IllegalArgumentException(String.format("Unable to create a regular running build from a composite promotion (id: %d, build type id: %s); thread id: %d; thread name: %s", buildPromotionEx.getId(), buildPromotionEx.getBuildTypeExternalId(), thread.getId(), thread.getName()));
        }
        try {
            this.myAgentId = n;
            this.myAgentTypeId = n2;
            this.myAgentName = string2;
            RunningBuildImpl runningBuildImpl = this;
            pair = sAgentRestrictor != null ? new Pair((Object)sAgentRestrictor.getType().getTypeId(), (Object)sAgentRestrictor.getId()) : null;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunningBuildImpl.b(illegalArgumentException);
        }
        runningBuildImpl.myQueuedAgentRestrictorData = pair;
        this.myAgentAccessCode = this.getBuildPromotion().generateAgentAccessCode();
        this.checkBuildTypeExistence();
        this.myTestRunMuter = buildContext.getLowLevelProblemMutingService().makeTestRunMuter(buildPromotionEx);
        NamedDaemonThreadFactory.executeWithNewThreadName((String)("Initializing a new running build with id: " + this.getId() + " in build type: " + this.getBuildTypeExternalId()), (Runnable)new /* Unavailable Anonymous Inner Class!! */);
    }

    protected RunningBuildImpl(@NotNull BuildPromotionEx buildPromotionEx, @NotNull RunningBuildData runningBuildData, @NotNull BuildContext buildContext) {
        Pair pair;
        block9: {
            block8: {
                if (buildPromotionEx == null) {
                    RunningBuildImpl.$$$reportNull$$$0(2);
                }
                if (runningBuildData == null) {
                    RunningBuildImpl.$$$reportNull$$$0(3);
                }
                if (buildContext == null) {
                    RunningBuildImpl.$$$reportNull$$$0(4);
                }
                super(runningBuildData.myBuildId, runningBuildData.myRequestor, buildPromotionEx, buildContext);
                this.myPublishArtifactsLock = new ReentrantLock();
                this.myShortStatistics = new AtomicReference();
                this.myLazyRunnersState = new AtomicReference();
                this.myAgentResetCount = new AtomicInteger();
                this.myPersisted = new AtomicBoolean();
                this.myUserDataStorage = new UserDataStorageImpl();
                this.myHangingBuildsCalculationEnabledSince = -1L;
                this.myFinishTimeServer = null;
                this.myStartingBuild = false;
                this.myArtifactsStats = new BaseRunningBuild.ArtifactsStats();
                this.myArtifactsLimit = new /* Unavailable Anonymous Inner Class!! */;
                if (buildPromotionEx.isCompositeBuild()) {
                    Thread thread = Thread.currentThread();
                    throw new IllegalArgumentException(String.format("Unable to create a regular running build from a composite promotion (id: %d, build type id: %s); thread id: %d; thread name: %s", buildPromotionEx.getId(), buildPromotionEx.getBuildTypeExternalId(), thread.getId(), thread.getName()));
                }
                try {
                    try {
                        this.myAgentId = runningBuildData.myAgentId;
                        this.myAgentTypeId = runningBuildData.myAgentTypeId;
                        this.myAgentName = runningBuildData.myAgentName;
                        RunningBuildImpl runningBuildImpl = this;
                        if (runningBuildData.myAgentRestrictorTypeId == null || runningBuildData.myAgentRestrictorId == null) break block8;
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        throw RunningBuildImpl.b(illegalArgumentException);
                    }
                    pair = new Pair((Object)runningBuildData.myAgentRestrictorTypeId, (Object)runningBuildData.myAgentRestrictorId);
                    break block9;
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw RunningBuildImpl.b(illegalArgumentException);
                }
            }
            pair = null;
        }
        runningBuildImpl.myQueuedAgentRestrictorData = pair;
        this.myAgentAccessCode = runningBuildData.myAccessCode;
        this.myRawBuildNumber = runningBuildData.myBuildNumber;
        this.myBuildCounter = runningBuildData.myBuildCounter;
        this.myPersisted.set(true);
        this.setBuildStartTimes(runningBuildData.getBuildStartTimeAgent(), runningBuildData.getBuildStartTimeServer(), null);
        this.myFinishTimeServer = runningBuildData.myBuildFinishTimeServer;
        this.myLastBuildActivityTimestamp = runningBuildData.getLastBuildActivityTimeServer();
        this.myTestRunMuter = buildContext.getLowLevelProblemMutingService().makeTestRunMuter(buildPromotionEx);
        this.initializeExistingBuild(runningBuildData);
    }

    protected void initializeNewBuild() {
        BuildTypeEx buildTypeEx = this.getBuildType();
        try {
            if (buildTypeEx == null) {
                throw new BuildTypeNotFoundException("Build type with id '" + this.getBuildTypeId() + "' is not registered within the server");
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunningBuildImpl.b(illegalArgumentException);
        }
        this.myBuildCounter = this.getBuildCounterForStartingBuild(() -> buildTypeEx.getBuildNumbers().getCounterAndIncrement());
        this.myProbablyResolvedBuildNumber = this.myRawBuildNumber = this.getBuildPromotion().getBuildSettings().getBuildNumberPattern();
        this.myStartingBuild = true;
        this.myLastBuildActivityTimestamp = this.nowDate();
        this.createSettingsDigest();
        this.getBuildPromotion().setAssociatedBuild((SBuild)this);
        this.startBuildFailureChecker();
    }

    protected void initializeExistingBuild(@NotNull RunningBuildData runningBuildData) {
        try {
            if (runningBuildData == null) {
                RunningBuildImpl.$$$reportNull$$$0(5);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunningBuildImpl.b(illegalArgumentException);
        }
        this.updateBuildInfo();
        this.myProbablyResolvedBuildNumber = this.myRawBuildNumber;
        this.getBuildPromotion().setAssociatedBuild((SBuild)this);
        this.initCurrentStatisticValues(runningBuildData.getStatisticValues());
        this.getBuildPromotion().initBuildProblems();
        this.updateExternalArtifactsInfoOnAccess();
    }

    public void prepareArtifactDependencies() {
        this.resolveArtifactDependencies((sArtifactDependency, throwable) -> {
            block8: {
                block7: {
                    try {
                        try {
                            if (!(throwable instanceof BuildTypeNotFoundException) && !(throwable instanceof ChainBuildNotFoundException)) break block7;
                        }
                        catch (IllegalArgumentException illegalArgumentException) {
                            throw RunningBuildImpl.b(illegalArgumentException);
                        }
                        this.reportArtifactResolveBuildProblem((SArtifactDependency)sArtifactDependency, (Throwable)throwable, "ARTIFACT_DEPENDENCY_ERROR");
                        break block8;
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        throw RunningBuildImpl.b(illegalArgumentException);
                    }
                }
                try {
                    if (throwable instanceof InaccessibleArtifactDependencyException) {
                        this.reportArtifactResolveBuildProblem((SArtifactDependency)sArtifactDependency, (Throwable)throwable, "INACCESSIBLE_EXTERNAL_DEPENDENCY");
                    }
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw RunningBuildImpl.b(illegalArgumentException);
                }
            }
            String string = String.format("Failed to resolve artifact dependency <%s, %s> for running build %s: %s", sArtifactDependency.getSourceName(), sArtifactDependency.getRevisionRule().getDescription().toLowerCase(), this.getBuildId(), throwable.getMessage());
            Loggers.SERVER.warn(string);
        });
    }

    private void reportArtifactResolveBuildProblem(SArtifactDependency sArtifactDependency, Throwable throwable, String string) {
        String string2 = String.format("Failed to resolve artifact dependency <%s, %s>: %s", sArtifactDependency.getSourceName(), sArtifactDependency.getRevisionRule().getDescription().toLowerCase(), throwable.getMessage());
        BuildProblemData buildProblemData = BuildProblemData.createBuildProblem((String)String.valueOf(string2.hashCode()), (String)string, (String)string2);
        this.addBuildProblem(buildProblemData);
    }

    private void checkBuildTypeExistence() {
        try {
            if (this.getBuildType() == null) {
                throw new BuildTypeNotFoundException("Build type with id '" + this.getBuildTypeId() + "' is not registered within the server");
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunningBuildImpl.b(illegalArgumentException);
        }
    }

    @Nullable
    public BuildConfigurationInfo getBuildConfiguration() {
        return this.getBuildType();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @NotNull
    public BuildAgentEx getAgent() {
        BuildAgentEx buildAgentEx = this.myAgent;
        if (buildAgentEx == null) {
            int n = this.myAgentResetCount.get();
            buildAgentEx = this.computeAgent();
            AtomicInteger atomicInteger = this.myAgentResetCount;
            synchronized (atomicInteger) {
                if (this.myAgent != null) {
                    BuildAgentEx buildAgentEx2 = this.myAgent;
                    // MONITOREXIT @DISABLED, blocks:[5, 9, 10] lbl9 : MonitorExitStatement: MONITOREXIT : var3_3
                    if (buildAgentEx2 != null) return buildAgentEx2;
                    RunningBuildImpl.$$$reportNull$$$0(6);
                    return buildAgentEx2;
                }
                if (this.myAgentResetCount.get() == n) {
                    this.myAgent = buildAgentEx;
                    BuildAgentEx buildAgentEx3 = buildAgentEx;
                    // MONITOREXIT @DISABLED, blocks:[5, 8, 10] lbl16 : MonitorExitStatement: MONITOREXIT : var3_3
                    if (buildAgentEx3 != null) return buildAgentEx3;
                    RunningBuildImpl.$$$reportNull$$$0(7);
                    return buildAgentEx3;
                }
            }
            try {
                BuildAgentEx buildAgentEx4 = this.getAgent();
                if (buildAgentEx4 != null) BuildAgentEx buildAgentEx5;
                return buildAgentEx5;
                RunningBuildImpl.$$$reportNull$$$0(8);
                return buildAgentEx4;
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw RunningBuildImpl.b(illegalArgumentException);
            }
        }
        try {
            BuildAgentEx buildAgentEx6 = buildAgentEx;
            if (buildAgentEx6 != null) BuildAgentEx buildAgentEx7;
            return buildAgentEx7;
            RunningBuildImpl.$$$reportNull$$$0(9);
            return buildAgentEx6;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunningBuildImpl.b(illegalArgumentException);
        }
    }

    @NotNull
    private BuildAgentEx computeAgent() {
        BuildAgentEx buildAgentEx;
        BuildAgentEx buildAgentEx2;
        block5: {
            block6: {
                buildAgentEx2 = this.myContext.getBuildAgentManager().findAgentById(this.myAgentId, true);
                try {
                    if (buildAgentEx2 != null) break block5;
                    if (!this.getBuildPromotion().isAgentLessBuild()) break block6;
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw RunningBuildImpl.b(illegalArgumentException);
                }
                buildAgentEx2 = this.computeAgentlessBuildAgent();
                break block5;
            }
            buildAgentEx2 = this.myContext.getBuildAgentFactory().createDeadAgent(this.myAgentId, this.myAgentTypeId, this.myAgentName);
        }
        try {
            buildAgentEx = buildAgentEx2;
            if (buildAgentEx == null) {
                RunningBuildImpl.$$$reportNull$$$0(10);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunningBuildImpl.b(illegalArgumentException);
        }
        return buildAgentEx;
    }

    @NotNull
    public BuildAgentEx computeAgentlessBuildAgent() {
        BuildAgentEx buildAgentEx;
        String string;
        block10: {
            BuildAgentEx buildAgentEx2;
            block11: {
                string = (String)this.getBuildPromotion().getAttribute("teamcity.build.agentLessExecutorName");
                try {
                    try {
                        if (string != null) break block10;
                        if (this.getBuildPromotion().isCompositeBuild()) break block11;
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        throw RunningBuildImpl.b(illegalArgumentException);
                    }
                    LOG.warn(String.format("Failed to fetch Executor Mode executor name for build %d", this.getBuildId()));
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw RunningBuildImpl.b(illegalArgumentException);
                }
            }
            try {
                buildAgentEx2 = this.myContext.getBuildAgentFactory().createDeadAgent(this.myAgentId, this.myAgentTypeId, this.myAgentName);
                if (buildAgentEx2 == null) {
                    RunningBuildImpl.$$$reportNull$$$0(11);
                }
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw RunningBuildImpl.b(illegalArgumentException);
            }
            return buildAgentEx2;
        }
        try {
            buildAgentEx = this.myContext.getBuildAgentFactory().createFakeAgent(string);
            if (buildAgentEx == null) {
                RunningBuildImpl.$$$reportNull$$$0(12);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunningBuildImpl.b(illegalArgumentException);
        }
        return buildAgentEx;
    }

    @Nullable
    public SAgentRestrictor getQueuedAgentRestrictor() {
        SAgentRestrictor sAgentRestrictor;
        try {
            sAgentRestrictor = this.myQueuedAgentRestrictorData == null ? null : this.myContext.getAgentRestrictorFactory().createFor(((Integer)this.myQueuedAgentRestrictorData.first).intValue(), ((Integer)this.myQueuedAgentRestrictorData.second).intValue());
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunningBuildImpl.b(illegalArgumentException);
        }
        return sAgentRestrictor;
    }

    public Integer getQueuedAgentId() {
        Integer n;
        block5: {
            block4: {
                SAgentRestrictor sAgentRestrictor = this.getQueuedAgentRestrictor();
                try {
                    try {
                        if (sAgentRestrictor == null || sAgentRestrictor.getType() != AgentRestrictorType.SINGLE_AGENT) break block4;
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        throw RunningBuildImpl.b(illegalArgumentException);
                    }
                    n = sAgentRestrictor.getId();
                    break block5;
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw RunningBuildImpl.b(illegalArgumentException);
                }
            }
            n = null;
        }
        return n;
    }

    public void addBuildMessages(@NotNull List<BuildMessage1> list) {
        try {
            if (list == null) {
                RunningBuildImpl.$$$reportNull$$$0(13);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunningBuildImpl.b(illegalArgumentException);
        }
        Loggers.SERVER.warnAndDebugDetails("Method SRunningBuild.addBuildMessages(BuildMessage1 message) is not supported anymore", (Throwable)new RuntimeException());
    }

    public void addBuildMessage(@NotNull BuildMessage1 buildMessage1) {
        try {
            if (buildMessage1 == null) {
                RunningBuildImpl.$$$reportNull$$$0(14);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunningBuildImpl.b(illegalArgumentException);
        }
        Loggers.SERVER.warnAndDebugDetails("Method SRunningBuild.addBuildMessage(BuildMessage1 message) is not supported anymore", (Throwable)new RuntimeException());
    }

    @NotNull
    public String getProgressText() {
        String string;
        try {
            string = StringUtil.emptyIfNull((String)this.myProgressText);
            if (string == null) {
                RunningBuildImpl.$$$reportNull$$$0(15);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunningBuildImpl.b(illegalArgumentException);
        }
        return string;
    }

    public String getCurrentPath() {
        return StringUtil.emptyIfNull((String)this.myCurrentPathText);
    }

    @Nullable
    public Date getFinishDate() {
        return null;
    }

    public String toString() {
        return LogUtil.describe((Build)this);
    }

    static boolean shouldCancelBuildOnMissingRevision() {
        return TeamCityProperties.getBooleanOrTrue((String)"teamcity.build.cancelBuildOnMissingRevision");
    }

    protected boolean doBuildFinish() {
        super.doBuildFinish();
        return this.runUnlessFinished(this::lambda$doBuildFinish$3);
    }

    public void finishedOnOtherNode() {
        super.finishedOnOtherNode();
        this.updateBuildFinishDelayMetric();
    }

    private long updateBuildFinishDelayMetric() {
        try {
            if (this.myFinishTimeServer == null) {
                return -1L;
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunningBuildImpl.b(illegalArgumentException);
        }
        long l = Math.max(0L, this.myContext.getTimeService().now() - this.myFinishTimeServer.getTime());
        this.myContext.getServerBuildDelayMetrics().getBuildFinishDelayMetric().increment((double)l);
        return l;
    }

    private void finalSteps() {
        BuildPromotionEx buildPromotionEx = this.getBuildPromotion();
        buildPromotionEx.resetArtifactsState(true);
        RunningBuildImpl.finalizeExternalArtifactsState(buildPromotionEx);
        try {
            this.getBuildLog().flush();
        }
        catch (Exception exception) {
            Loggers.SERVER.warnAndDebugDetails("Could not flush build log for build: " + LogUtil.describe((Build)this), (Throwable)exception);
        }
        try {
            ((BuildServerListener)this.myContext.getEventDispatcher().getMulticaster()).beforeBuildFinish((SRunningBuild)this);
            if (!this.isInterrupted()) {
                this.processBuildFailureConditionsOnBuildFinish();
                this.processBuildProblemProviders();
            }
        }
        catch (Exception exception) {
            throw RunningBuildImpl.b(exception);
        }
        this.storeFinalTestCount();
        if (this.myFinishTimeServer == null) {
            Date date = this.nowDate();
            Loggers.SERVER.warn("Build finish date has not been set for build: " + LogUtil.describe((Build)this) + ", setting it to: " + LogUtil.describe((Date)date));
            this.myFinishTimeServer = date;
        }
    }

    private static void finalizeExternalArtifactsState(BuildPromotionEx buildPromotionEx) {
        try {
            if (!"true".equals(buildPromotionEx.getAttribute("teamcity.build.hasExternalArtifacts"))) {
                buildPromotionEx.setAttribute("teamcity.build.hasExternalArtifacts", (Object)"false");
                buildPromotionEx.persist();
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunningBuildImpl.b(illegalArgumentException);
        }
    }

    private void storeFinalTestCount() {
        int n = this.getShortStatistics().getAllTestCount();
        this.myContext.getSQLRunner().runAndRetryNoResult(dBFunctions -> {
            try {
                dBFunctions.executeDml("insert into stats (build_id, test_count) values (?, ?)", new Object[]{this.myBuildId, n});
            }
            catch (DBDuplicatedRowException dBDuplicatedRowException) {
                dBFunctions.executeDml("update stats set test_count=? where build_id=?", new Object[]{n, this.myBuildId});
            }
        });
    }

    private void processBuildProblemProviders() {
        ArrayList arrayList = new ArrayList();
        for (BuildProblemProvider buildProblemProvider : this.myContext.getBuildProblemProviders()) {
            try {
                arrayList.addAll(buildProblemProvider.getBuildProblems((SBuild)this));
            }
            catch (Throwable throwable) {
                LOG.warnAndDebugDetails("Exception in the extension " + buildProblemProvider.getClass().getName() + " when called for the build " + LogUtil.describe((Build)this), throwable);
            }
        }
        for (BuildProblemProvider buildProblemProvider : arrayList) {
            Loggers.SERVER.info("Adding \"" + buildProblemProvider.getType() + "\" build problem to the build " + LogUtil.describe((Build)this) + " upon finishing");
            this.addBuildProblem((BuildProblemData)buildProblemProvider);
        }
    }

    public StatusDescriptor getStatusDescriptor() {
        return new StatusDescriptor(this.getBuildStatus(), this.myContext.getBuildStatisticsPrinterFactory().createForRunningBuild((SRunningBuild)this).getText());
    }

    @Nullable
    public ShortStatistics getCachedShortStatistics(boolean bl) {
        ShortStatistics shortStatistics;
        block8: {
            this.myStatsCallCounter.increment(1.0);
            shortStatistics = this.myShortStatistics.get();
            try {
                if (!bl) {
                    return shortStatistics;
                }
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw RunningBuildImpl.b(illegalArgumentException);
            }
            try {
                if (shortStatistics != null && this.myPersisted.get()) break block8;
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw RunningBuildImpl.b(illegalArgumentException);
            }
            ShortStatistics shortStatistics2 = this.createShortStatistics(false);
            try {
                if (this.myShortStatistics.compareAndSet(shortStatistics, shortStatistics2)) {
                    return shortStatistics2;
                }
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw RunningBuildImpl.b(illegalArgumentException);
            }
            return this.myShortStatistics.get();
        }
        return shortStatistics;
    }

    public static void updateBuilds(@NotNull Collection<RunningBuildEx> collection, @NotNull SQLRunnerEx sQLRunnerEx) {
        try {
            if (collection == null) {
                RunningBuildImpl.$$$reportNull$$$0(16);
            }
        }
        catch (Exception exception) {
            throw RunningBuildImpl.b(exception);
        }
        try {
            if (sQLRunnerEx == null) {
                RunningBuildImpl.$$$reportNull$$$0(17);
            }
        }
        catch (Exception exception) {
            throw RunningBuildImpl.b(exception);
        }
        try {
            if (collection.isEmpty()) {
                return;
            }
        }
        catch (Exception exception) {
            throw RunningBuildImpl.b(exception);
        }
        try {
            BuildPromotionImpl.updatePromotions((Collection)collection.stream().map(runningBuildEx -> runningBuildEx.getBuildPromotion()).collect(Collectors.toSet()), (SQLRunnerEx)sQLRunnerEx, (BuildPromotionEx.UpdateReason)BuildPromotionEx.UpdateReason.OTHER);
        }
        catch (Exception exception) {
            Loggers.SERVER.warnAndDebugDetails("Could not update build promotions of some of the running builds", (Throwable)exception);
        }
        for (RunningBuildEx runningBuildEx2 : collection) {
            try {
                if (!(runningBuildEx2 instanceof RunningBuildImpl)) continue;
                ((RunningBuildImpl)runningBuildEx2).updateBuildInternal(runningBuildEx2.isFinished(), false);
            }
            catch (Throwable throwable) {
                Loggers.SERVER.warnAndDebugDetails("Could not update running build data: " + LogUtil.describe((Build)runningBuildEx2), throwable);
            }
        }
    }

    public void updateBuild() {
        this.updateBuildInternal(this.isFinished(), true);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     */
    private void updateBuildInternal(boolean bl, boolean bl2) {
        block7: {
            Disposable disposable = NamedThreadFactory.patchThreadName((String)("Updating running build data for build with id " + this.getBuildId()));
            try {
                boolean[] blArray = new boolean[1];
                boolean bl3 = this.runUnlessFinished((Runnable)new /* Unavailable Anonymous Inner Class!! */);
                if (!bl3) break block7;
                try {
                    block8: {
                        if (!blArray[0]) break block7;
                        break block8;
                        catch (IllegalArgumentException illegalArgumentException) {
                            throw RunningBuildImpl.b(illegalArgumentException);
                        }
                    }
                    this.myContext.getBuildTestsListenerNotifier().notifyListeners((SRunningBuild)this);
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw RunningBuildImpl.b(illegalArgumentException);
                }
            }
            finally {
                disposable.dispose();
            }
        }
    }

    private void notifyRunningCompositeBuilds() {
        for (BuildPromotionEx buildPromotionEx : this.getBuildPromotion().getDependentCompositePromotions()) {
            SBuild sBuild = buildPromotionEx.getAssociatedBuild();
            try {
                if (!(sBuild instanceof CompositeRunningBuild)) continue;
                ((CompositeRunningBuild)sBuild).notifyOnDependencyUpdate();
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw RunningBuildImpl.b(illegalArgumentException);
            }
        }
    }

    private void updateBuildActivityTimestamp() {
        try {
            if (this.myLastBuildActivityTimestamp != null) {
                return;
            }
        }
        catch (Exception exception) {
            throw RunningBuildImpl.b(exception);
        }
        try {
            Date date = this.getBuildLog().getLastMessageTimestamp();
            try {
                if (date != null) {
                    this.myLastBuildActivityTimestamp = date;
                }
            }
            catch (Exception exception) {
                throw RunningBuildImpl.b(exception);
            }
            try {
                if (this.myLastBuildActivityTimestamp == null) {
                    this.myLastBuildActivityTimestamp = this.getServerStartDate();
                }
            }
            catch (Exception exception) {
                throw RunningBuildImpl.b(exception);
            }
        }
        catch (Exception exception) {
            Loggers.SERVER.warnAndDebugDetails("Failed to calculate build activity timestamp for build: " + LogUtil.describe((Build)this), (Throwable)exception);
        }
    }

    public boolean updateBuildInfo() {
        return (Boolean)this.myContext.getSQLRunner().runAndRetry(dBFunctions -> {
            boolean bl;
            Collection collection = dBFunctions.queryForTuples(new ArrayList(), RunningBuildProgressData.class, "fromDB", "select build_start_time_agent, build_start_time_server, build_finish_time_server, last_build_activity_time, user_status_text, progress_text, current_path_text, build_number from running where build_id = ?", new Object[]{this.myBuildId});
            if (!collection.isEmpty()) {
                RunningBuildProgressData runningBuildProgressData = (RunningBuildProgressData)collection.iterator().next();
                this.updateProgressData(runningBuildProgressData);
            }
            try {
                this.loadCancelledInfoFromDB(dBFunctions);
                bl = !collection.isEmpty();
            }
            catch (DBException dBException) {
                throw RunningBuildImpl.b(dBException);
            }
            return bl;
        });
    }

    private void updateProgressData(@NotNull RunningBuildProgressData runningBuildProgressData) {
        block24: {
            block23: {
                Date date;
                Date date2;
                try {
                    if (runningBuildProgressData == null) {
                        RunningBuildImpl.$$$reportNull$$$0(18);
                    }
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw RunningBuildImpl.b(illegalArgumentException);
                }
                try {
                    this.myStatusText = runningBuildProgressData.myUserText;
                    this.myProgressText = runningBuildProgressData.myProgressText;
                    this.myCurrentPathText = runningBuildProgressData.myCurrentPathText;
                    date2 = runningBuildProgressData.myAgentStartTime.getTime() > 0L ? runningBuildProgressData.myAgentStartTime : null;
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw RunningBuildImpl.b(illegalArgumentException);
                }
                Date date3 = date2;
                try {
                    date = runningBuildProgressData.myServerStartTime.getTime() > 0L ? runningBuildProgressData.myServerStartTime : this.getStartDate();
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw RunningBuildImpl.b(illegalArgumentException);
                }
                Date date4 = date;
                try {
                    try {
                        if (date3 == null || this.isStartedOnAgent()) break block23;
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        throw RunningBuildImpl.b(illegalArgumentException);
                    }
                    this.setBuildStartTimes(date3, date4, null);
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw RunningBuildImpl.b(illegalArgumentException);
                }
            }
            try {
                if (runningBuildProgressData.myBuildFinishTimeServer.getTime() > 0L) {
                    this.myFinishTimeServer = runningBuildProgressData.myBuildFinishTimeServer;
                }
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw RunningBuildImpl.b(illegalArgumentException);
            }
            try {
                if (runningBuildProgressData.myLastBuildActivityTimeServer.getTime() > 0L) {
                    this.myLastBuildActivityTimestamp = runningBuildProgressData.myLastBuildActivityTimeServer;
                }
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw RunningBuildImpl.b(illegalArgumentException);
            }
            try {
                try {
                    try {
                        if (!this.isStartedOnAgent() || StringUtil.isEmpty((String)runningBuildProgressData.myBuildNumber)) break block24;
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        throw RunningBuildImpl.b(illegalArgumentException);
                    }
                    if (this.myRawBuildNumber.equals(runningBuildProgressData.myBuildNumber)) break block24;
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw RunningBuildImpl.b(illegalArgumentException);
                }
                this.myProbablyResolvedBuildNumber = this.myRawBuildNumber = runningBuildProgressData.myBuildNumber;
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw RunningBuildImpl.b(illegalArgumentException);
            }
        }
    }

    public void clearCachedEstimate() {
        this.myDurationEstimate = null;
    }

    @Nullable
    public String getProjectId() {
        return this.myContext.getProjectManager().findProjectId(this.getBuildTypeId());
    }

    @Nullable
    public String getProjectExternalId() {
        return this.myContext.getProjectManager().findProjectExternalId(this.getBuildTypeId());
    }

    @NotNull
    public String getAgentName() {
        String string;
        try {
            string = this.getAgent().getName();
            if (string == null) {
                RunningBuildImpl.$$$reportNull$$$0(19);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunningBuildImpl.b(illegalArgumentException);
        }
        return string;
    }

    public void setStartTimes(@NotNull Date date, @NotNull Date date2, @NotNull TimeZone timeZone) {
        try {
            if (date == null) {
                RunningBuildImpl.$$$reportNull$$$0(20);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunningBuildImpl.b(illegalArgumentException);
        }
        try {
            if (date2 == null) {
                RunningBuildImpl.$$$reportNull$$$0(21);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunningBuildImpl.b(illegalArgumentException);
        }
        try {
            if (timeZone == null) {
                RunningBuildImpl.$$$reportNull$$$0(22);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunningBuildImpl.b(illegalArgumentException);
        }
        this.myContext.getSQLRunner().runAndRetryNoResult(dBFunctions -> dBFunctions.executeDml("update running set build_start_time_agent = ?, build_start_time_server = ? where build_id = ?", new Object[]{date.getTime(), date2.getTime(), this.myBuildId}));
        this.setBuildStartTimes(date, date2, timeZone);
        this.setHangingBuildsCalculationEnabledSince(date2);
    }

    public int getCompletedPercent() {
        return this.getCompletedPercent(this.now());
    }

    public int getCompletedPercent(long l) {
        long l2 = this.getEstimationForTimeLeft(l);
        try {
            if (l2 == -1L) {
                return -1;
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunningBuildImpl.b(illegalArgumentException);
        }
        long l3 = this.getElapsedTime(l);
        long l4 = l3 + l2;
        try {
            if (l4 <= 0L) {
                return 0;
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunningBuildImpl.b(illegalArgumentException);
        }
        int n = (int)(100L - 100L * l2 / l4);
        return Math.min(n, 100);
    }

    public long getEstimationForTimeLeft() {
        return this.getEstimationForTimeLeft(this.now());
    }

    @NotNull
    public BuildPromotionInfo getBuildPromotionInfo() {
        BuildPromotionEx buildPromotionEx;
        try {
            buildPromotionEx = this.getBuildPromotion();
            if (buildPromotionEx == null) {
                RunningBuildImpl.$$$reportNull$$$0(23);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunningBuildImpl.b(illegalArgumentException);
        }
        return buildPromotionEx;
    }

    public boolean occupiesAgent() {
        boolean bl;
        block5: {
            block4: {
                try {
                    try {
                        if (this.isAgentLessBuild() || this.isDetachedFromAgent()) break block4;
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        throw RunningBuildImpl.b(illegalArgumentException);
                    }
                    bl = true;
                    break block5;
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw RunningBuildImpl.b(illegalArgumentException);
                }
            }
            bl = false;
        }
        return bl;
    }

    public long getEstimationForTimeLeft(long l) {
        return this.myContext.getBuildDurationCalculator().estimateTimeLeftFor((RunningBuildEx)this, l);
    }

    public boolean isFinishReceived() {
        boolean bl;
        try {
            bl = this.myFinishTimeServer != null;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunningBuildImpl.b(illegalArgumentException);
        }
        return bl;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void publishArtifact(@NotNull String string, @NotNull byte[] byArray) throws IOException {
        try {
            if (string == null) {
                RunningBuildImpl.$$$reportNull$$$0(24);
            }
        }
        catch (Throwable throwable) {
            throw RunningBuildImpl.b(throwable);
        }
        try {
            if (byArray == null) {
                RunningBuildImpl.$$$reportNull$$$0(25);
            }
        }
        catch (Throwable throwable) {
            throw RunningBuildImpl.b(throwable);
        }
        this.myPublishArtifactsLock.lock();
        try {
            File file = this.preparePath(string);
            Stopwatch stopwatch = Stopwatch.createStarted();
            if (this.shouldWriteToCustomStorage(string)) {
                try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byArray);){
                    this.writeToCustomStorage(byteArrayInputStream);
                }
            }
            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(byArray);
            }
            catch (Throwable throwable) {
                FileUtil.close(fileOutputStream);
                throw throwable;
            }
            FileUtil.close((Closeable)fileOutputStream);
            this.artifactPublished(file, string, stopwatch.elapsed(TimeUnit.MILLISECONDS), "memory");
            return;
        }
        finally {
            this.myPublishArtifactsLock.unlock();
        }
    }

    private boolean shouldWriteToCustomStorage(@NotNull String string) {
        try {
            if (string == null) {
                RunningBuildImpl.$$$reportNull$$$0(26);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunningBuildImpl.b(illegalArgumentException);
        }
        return string.equals(".teamcity/artifacts.json");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    final void artifactPublished(@NotNull File file, @NotNull String string, @Nonnegative long l, @NotNull String string2) {
        try {
            if (file == null) {
                RunningBuildImpl.$$$reportNull$$$0(27);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunningBuildImpl.b(illegalArgumentException);
        }
        try {
            if (string == null) {
                RunningBuildImpl.$$$reportNull$$$0(28);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunningBuildImpl.b(illegalArgumentException);
        }
        try {
            if (string2 == null) {
                RunningBuildImpl.$$$reportNull$$$0(29);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunningBuildImpl.b(illegalArgumentException);
        }
        try {
            long l2 = file.length();
            try {
                if (Loggers.SERVER.isDebugEnabled()) {
                    Loggers.SERVER.debug(String.format("Finished writing artifact \"%s\" (size: %s) in %s via %s for build %s, into directory: %s", file.getName(), StringUtil.formatFileSize((long)l2), TimePrinter.formatMillis((long)l), string2, LogUtil.describe((Build)this), file.getParentFile().getAbsolutePath()));
                }
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw RunningBuildImpl.b(illegalArgumentException);
            }
            this.myArtifactsStats.newFilePublished(l2);
            if (!string.contains(".teamcity/properties/")) return;
        }
        catch (Throwable throwable) {
            try {
                if (!string.contains(".teamcity/properties/")) throw throwable;
                this.resetBuildFinalParameters();
                throw throwable;
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw RunningBuildImpl.b(illegalArgumentException);
            }
        }
        this.resetBuildFinalParameters();
    }

    private void updateBuildExternalArtifactsAttribute() {
        this.getBuildPromotion().setAttribute("teamcity.build.hasExternalArtifacts", (Object)"true");
        this.getBuildPromotion().persist();
    }

    @NotNull
    public final BaseRunningBuild.ArtifactsStats getArtifactsStats() {
        BaseRunningBuild.ArtifactsStats artifactsStats;
        try {
            artifactsStats = this.myArtifactsStats;
            if (artifactsStats == null) {
                RunningBuildImpl.$$$reportNull$$$0(30);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunningBuildImpl.b(illegalArgumentException);
        }
        return artifactsStats;
    }

    public final void publishArtifact(@NotNull String string, @NotNull File file) throws IOException {
        try {
            if (string == null) {
                RunningBuildImpl.$$$reportNull$$$0(31);
            }
        }
        catch (IOException iOException) {
            throw RunningBuildImpl.b(iOException);
        }
        try {
            if (file == null) {
                RunningBuildImpl.$$$reportNull$$$0(32);
            }
        }
        catch (IOException iOException) {
            throw RunningBuildImpl.b(iOException);
        }
        this.publishArtifact(string, file, null);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void publishArtifact(@NotNull String string, @NotNull File file, @Nullable PublishArtifactProgressListener publishArtifactProgressListener) throws IOException {
        try {
            if (string == null) {
                RunningBuildImpl.$$$reportNull$$$0(33);
            }
        }
        catch (Throwable throwable) {
            throw RunningBuildImpl.b(throwable);
        }
        try {
            if (file == null) {
                RunningBuildImpl.$$$reportNull$$$0(34);
            }
        }
        catch (Throwable throwable) {
            throw RunningBuildImpl.b(throwable);
        }
        this.myPublishArtifactsLock.lock();
        try {
            Stopwatch stopwatch = Stopwatch.createStarted();
            File file2 = this.preparePath(string);
            if (this.shouldWriteToCustomStorage(string)) {
                try (InputStream inputStream = Files.newInputStream(file.toPath(), new OpenOption[0]);){
                    this.writeToCustomStorage(inputStream);
                }
            }
            int n = 0;
            try {
                while (!this.writeArtifactFile(file, file2, publishArtifactProgressListener, n)) {
                    ++n;
                    ThreadUtil.sleep((long)TeamCityProperties.getLong((String)"teamcity.internal.artifactsPublishing.accessDeniedHandler.sleepInterval", (long)1000L));
                }
            }
            catch (Throwable throwable) {
                throw RunningBuildImpl.b(throwable);
            }
            this.artifactPublished(file2, string, stopwatch.elapsed(TimeUnit.MILLISECONDS), "temp file \"" + file.getAbsolutePath() + "\"");
            FileUtil.delete((File)file);
        }
        finally {
            this.myPublishArtifactsLock.unlock();
        }
    }

    /*
     * Loose catch block
     */
    private boolean writeArtifactFile(@NotNull File file, @NotNull File file2, @Nullable PublishArtifactProgressListener publishArtifactProgressListener, int n) throws IOException {
        block32: {
            try {
                if (file == null) {
                    RunningBuildImpl.$$$reportNull$$$0(35);
                }
            }
            catch (Throwable throwable) {
                throw RunningBuildImpl.b(throwable);
            }
            try {
                if (file2 == null) {
                    RunningBuildImpl.$$$reportNull$$$0(36);
                }
            }
            catch (Throwable throwable) {
                throw RunningBuildImpl.b(throwable);
            }
            try {
                block31: {
                    FileUtil.delete((File)file2);
                    LOG.debug(() -> "Trying to move '" + String.valueOf(file) + "' to '" + String.valueOf(file2) + "' using rename");
                    if (!file.renameTo(file2)) break block31;
                    try {
                        block34: {
                            LOG.debug(() -> "Successfully moved '" + String.valueOf(file) + "' to '" + String.valueOf(file2) + "' using rename");
                            if (publishArtifactProgressListener == null) break block32;
                            break block34;
                            catch (Throwable throwable) {
                                throw RunningBuildImpl.b(throwable);
                            }
                        }
                        publishArtifactProgressListener.processed(file2.length());
                        break block32;
                    }
                    catch (Throwable throwable) {
                        throw RunningBuildImpl.b(throwable);
                    }
                }
                try {
                    LOG.debug(() -> "Could not move '" + String.valueOf(file) + "' to '" + String.valueOf(file2) + "' using rename.  The destination is probably on a network drive or another disk. Copying the file");
                    if (!file.exists() || file.equals(file2)) break block32;
                }
                catch (Throwable throwable) {
                    throw RunningBuildImpl.b(throwable);
                }
                FileUtil.delete((File)file2);
                try (FileInputStream fileInputStream = new FileInputStream(file);
                     FileOutputStream fileOutputStream = new FileOutputStream(file2);){
                    TCStreamUtil.writeBinary((InputStream)fileInputStream, (OutputStream)fileOutputStream, (TCStreamUtil.WriteProgressListener)PublishArtifactProgressListener.toWriteProgressListener((PublishArtifactProgressListener)publishArtifactProgressListener), (long)1L);
                }
            }
            catch (FileNotFoundException | AccessDeniedException iOException) {
                block33: {
                    try {
                        try {
                            LOG.infoAndDebugDetails("Unable to write artifacts file '" + String.valueOf(file2) + "'", (Throwable)iOException);
                            if (iOException instanceof AccessDeniedException || StringUtil.containsIgnoreCase((String)StringUtil.emptyIfNull((String)iOException.getMessage()), (String)"access is denied")) break block33;
                        }
                        catch (Throwable throwable) {
                            throw RunningBuildImpl.b(throwable);
                        }
                        throw iOException;
                    }
                    catch (Throwable throwable) {
                        throw RunningBuildImpl.b(throwable);
                    }
                }
                try {
                    if (n >= TeamCityProperties.getInteger((String)"teamcity.internal.artifactsPublishing.accessDeniedHandler.retriesCount", (int)3)) {
                        LOG.warnAndDebugDetails("Could not write the artifact file '" + String.valueOf(file2) + "' after " + n + " retries, giving up", (Throwable)iOException);
                        throw iOException;
                    }
                }
                catch (Throwable throwable) {
                    throw RunningBuildImpl.b(throwable);
                }
                LOG.info("Will retry writing artifacts file '" + String.valueOf(file2) + "'");
                return false;
            }
        }
        return true;
    }

    public final void publishArtifact(@NotNull String string, @NotNull InputStream inputStream) throws IOException {
        try {
            if (string == null) {
                RunningBuildImpl.$$$reportNull$$$0(37);
            }
        }
        catch (IOException iOException) {
            throw RunningBuildImpl.b(iOException);
        }
        try {
            if (inputStream == null) {
                RunningBuildImpl.$$$reportNull$$$0(38);
            }
        }
        catch (IOException iOException) {
            throw RunningBuildImpl.b(iOException);
        }
        this.publishArtifact(string, inputStream, null);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void publishArtifact(@NotNull String string, @NotNull InputStream inputStream, @Nullable PublishArtifactProgressListener publishArtifactProgressListener) throws IOException {
        try {
            if (string == null) {
                RunningBuildImpl.$$$reportNull$$$0(39);
            }
        }
        catch (IOException iOException) {
            throw RunningBuildImpl.b(iOException);
        }
        try {
            if (inputStream == null) {
                RunningBuildImpl.$$$reportNull$$$0(40);
            }
        }
        catch (IOException iOException) {
            throw RunningBuildImpl.b(iOException);
        }
        this.myPublishArtifactsLock.lock();
        try {
            Stopwatch stopwatch;
            File file;
            block12: {
                block11: {
                    file = this.preparePath(string);
                    stopwatch = Stopwatch.createStarted();
                    try {
                        if (!this.shouldWriteToCustomStorage(string)) break block11;
                        this.writeToCustomStorage(inputStream);
                        break block12;
                    }
                    catch (IOException iOException) {
                        throw RunningBuildImpl.b(iOException);
                    }
                }
                this.writeToFile(inputStream, publishArtifactProgressListener, file);
            }
            this.artifactPublished(file, string, stopwatch.elapsed(TimeUnit.MILLISECONDS), "stream");
        }
        finally {
            this.myPublishArtifactsLock.unlock();
        }
    }

    private void writeToFile(@NotNull InputStream inputStream, @Nullable PublishArtifactProgressListener publishArtifactProgressListener, File file) throws IOException {
        try {
            if (inputStream == null) {
                RunningBuildImpl.$$$reportNull$$$0(41);
            }
        }
        catch (Throwable throwable) {
            throw RunningBuildImpl.b(throwable);
        }
        FileUtil.createParentDirs((File)file);
        try (FileOutputStream fileOutputStream = new FileOutputStream(file);){
            TCStreamUtil.writeBinary((InputStream)inputStream, (OutputStream)fileOutputStream, (TCStreamUtil.WriteProgressListener)PublishArtifactProgressListener.toWriteProgressListener((PublishArtifactProgressListener)publishArtifactProgressListener), (long)1L);
        }
    }

    private void writeToCustomStorage(@NotNull InputStream inputStream) {
        try {
            if (inputStream == null) {
                RunningBuildImpl.$$$reportNull$$$0(42);
            }
        }
        catch (Throwable throwable) {
            throw RunningBuildImpl.b(throwable);
        }
        try {
            SerializableArtifactListData serializableArtifactListData = ArtifactListUtil.readArtifactList((InputStream)inputStream);
            if (serializableArtifactListData != null) {
                this.updateBuildExternalArtifactsAttribute();
                HashMap<String, String> hashMap = new HashMap<String, String>(serializableArtifactListData.getCommonProperties());
                hashMap.put("storageSettingsId", serializableArtifactListData.getStorageSettingsId());
                hashMap.put("lastmodified", String.valueOf(System.currentTimeMillis()));
                this.getCustomDataStorage("externalArtifacts:commonProperties").putValuesAndFlush(hashMap);
                HashMap<String, String> hashMap2 = new HashMap<String, String>();
                for (ArtifactData artifactData : serializableArtifactListData.getArtifactList()) {
                    String string = artifactData.getPath();
                    if (!ArchiveUtil.isSafeRelativePath((String)string)) {
                        String string2 = "External artifact path '" + string + "' is unsafe and will be ignored";
                        LOG.warn(string2);
                        this.getBuildLog().messageAsync(string2, Status.WARNING, MessageAttrs.serverMessage());
                        continue;
                    }
                    hashMap2.put(string, String.valueOf(artifactData.getSize()));
                }
                this.getCustomDataStorage("externalArtifacts:artifactList").putValuesAndFlush(hashMap2);
            }
        }
        catch (Throwable throwable) {
            LOG.warnAndDebugDetails("Could not persist artifacts list to CustomDataStorage", throwable);
            this.getBuildPromotion().getBuildLog().messageAsync("Internal error: could not persist artifacts list to the database: " + throwable.getMessage(), Status.WARNING, MessageAttrs.serverMessage());
        }
    }

    public long getDurationEstimate() {
        long l;
        Long l2 = this.myDurationEstimate;
        try {
            if (l2 != null) {
                return l2;
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunningBuildImpl.b(illegalArgumentException);
        }
        long l3 = this.myContext.getBuildDurationCalculator().calculateBuildDuration(BuildDurationEstimateContext.fromBuildPromotion((BuildPromotionEx)this.getBuildPromotion(), (BuildAgent)this.getAgent()), (BuildAgent)this.getAgent());
        try {
            l = l3 < 0L ? -1L : l3 / 1000L;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunningBuildImpl.b(illegalArgumentException);
        }
        this.myDurationEstimate = l2 = Long.valueOf(l);
        return l2;
    }

    public long getDurationOvertime() {
        return this.getEstimatedDurationExceededTime(this.now());
    }

    public long getEstimatedDurationExceededTime(long l) {
        long l2 = this.getDurationEstimate();
        try {
            if (l2 < 0L) {
                return -1L;
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunningBuildImpl.b(illegalArgumentException);
        }
        return Math.max(0L, this.getElapsedTime(l) - l2);
    }

    public long getElapsedTime() {
        return this.getElapsedTime(this.now());
    }

    public long getElapsedTime(long l) {
        return (l - this.getStartDate().getTime()) / 1000L;
    }

    @NotNull
    public String getBuildNumber() {
        String string;
        try {
            string = RunningBuildImpl.getBuildNumberWithoutRefs((String)this.myProbablyResolvedBuildNumber);
            if (string == null) {
                RunningBuildImpl.$$$reportNull$$$0(43);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunningBuildImpl.b(illegalArgumentException);
        }
        return string;
    }

    public void setBuildNumber(@NotNull String string) {
        try {
            if (string == null) {
                RunningBuildImpl.$$$reportNull$$$0(44);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunningBuildImpl.b(illegalArgumentException);
        }
        try {
            if (this.myRawBuildNumber.equals(string)) {
                return;
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunningBuildImpl.b(illegalArgumentException);
        }
        String string2 = this.myRawBuildNumber;
        String string3 = string.replace("{build.number}", string2);
        try {
            if (!string3.equals(this.myRawBuildNumber)) {
                this.myRawBuildNumber = this.myProbablyResolvedBuildNumber = string3;
                this.persistBuildNumber();
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunningBuildImpl.b(illegalArgumentException);
        }
    }

    public List<TestInfo> getTestMessages(int n, int n2) {
        List list = super.getTestMessages(n, n2);
        List list2 = this.getBuildLog().getPendingOpenBlocks(null, "$TEST_BLOCK$");
        for (BlockDescriptor blockDescriptor : list2) {
            list.add(new TestInfo(blockDescriptor.getText(), null, null, null, null, null, Status.NORMAL, 0, false, null, (String)null));
        }
        return list;
    }

    @NotNull
    public BuildLogEx getBuildLog() {
        BuildLogEx buildLogEx;
        try {
            buildLogEx = this.getBuildPromotion().getBuildLog();
            if (buildLogEx == null) {
                RunningBuildImpl.$$$reportNull$$$0(45);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunningBuildImpl.b(illegalArgumentException);
        }
        return buildLogEx;
    }

    @Nullable
    public BuildLogEx getBuildLogIfInitialized() {
        return this.getBuildPromotion().getBuildLogIfInitialized();
    }

    public String getAgentAccessCode() {
        return this.myAgentAccessCode;
    }

    /*
     * Exception decompiling
     */
    @NotNull
    public AgentBuild createAgentBuild() throws BuildTypeNotFoundException {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [4[TRYBLOCK]], but top level block is 17[SWITCH]
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

    @NotNull
    private Map<String, String> createStorageSettingsMap(@Nullable String string) {
        Map map;
        try {
            map = this.myContext.getArtifactsStorageSettingsProvider().getStorageSettingsForAgent(string, this.getProjectExternalId());
            if (map == null) {
                RunningBuildImpl.$$$reportNull$$$0(47);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunningBuildImpl.b(illegalArgumentException);
        }
        return map;
    }

    private boolean shouldCleanSources(@NotNull BuildPromotionEx buildPromotionEx) {
        try {
            if (buildPromotionEx == null) {
                RunningBuildImpl.$$$reportNull$$$0(48);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunningBuildImpl.b(illegalArgumentException);
        }
        Object object = buildPromotionEx.getAttribute("teamcity.cleanSources");
        try {
            if (object != null) {
                return Boolean.parseBoolean(object.toString());
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunningBuildImpl.b(illegalArgumentException);
        }
        return (Boolean)this.getBuildPromotion().getBuildSettings().getOptions().getOption(BuildTypeSettings.BT_CLEAN_BUILD);
    }

    private List<VcsChangeInfo> truncateList(List<VcsChangeInfo> list, int n) {
        List<VcsChangeInfo> list2;
        try {
            list2 = list.size() > n ? list.subList(0, n) : list;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunningBuildImpl.b(illegalArgumentException);
        }
        return list2;
    }

    private List<VcsChangeInfo> mergeChangedFiles(List<VcsChangeInfo> list) {
        ArrayList<String> arrayList = new ArrayList<String>();
        LinkedHashMap<String, VcsChangeInfo> linkedHashMap = new LinkedHashMap<String, VcsChangeInfo>();
        for (VcsChangeInfo vcsChangeInfo : list) {
            String string;
            block14: {
                boolean bl;
                block13: {
                    VcsChangeInfo vcsChangeInfo2;
                    block12: {
                        string = vcsChangeInfo.getRelativeFileName();
                        bl = true;
                        vcsChangeInfo2 = (VcsChangeInfo)linkedHashMap.get(string);
                        try {
                            if (vcsChangeInfo2 != null) break block12;
                            if (this.pathDeleted(string, arrayList)) break block13;
                        }
                        catch (IllegalArgumentException illegalArgumentException) {
                            throw RunningBuildImpl.b(illegalArgumentException);
                        }
                        bl = false;
                        break block13;
                    }
                    try {
                        if (!vcsChangeInfo.getType().equals((Object)VcsChangeInfo.Type.ADDED) || vcsChangeInfo2.getType().equals((Object)VcsChangeInfo.Type.REMOVED)) break block13;
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        throw RunningBuildImpl.b(illegalArgumentException);
                    }
                    bl = false;
                }
                try {
                    if (bl) {
                        continue;
                    }
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw RunningBuildImpl.b(illegalArgumentException);
                }
                try {
                    try {
                        if (!vcsChangeInfo.getType().isDirectory() || !vcsChangeInfo.getType().equals((Object)VcsChangeInfo.Type.DIRECTORY_REMOVED)) break block14;
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        throw RunningBuildImpl.b(illegalArgumentException);
                    }
                    arrayList.add(this.normalizeDirPath(string));
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw RunningBuildImpl.b(illegalArgumentException);
                }
            }
            linkedHashMap.put(string, vcsChangeInfo);
        }
        return new ArrayList<VcsChangeInfo>(linkedHashMap.values());
    }

    private boolean pathDeleted(@NotNull String string, @NotNull List<String> list) {
        try {
            if (string == null) {
                RunningBuildImpl.$$$reportNull$$$0(49);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunningBuildImpl.b(illegalArgumentException);
        }
        try {
            if (list == null) {
                RunningBuildImpl.$$$reportNull$$$0(50);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunningBuildImpl.b(illegalArgumentException);
        }
        for (String string2 : list) {
            try {
                if (!this.isSubDir(string2, string)) continue;
                return true;
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw RunningBuildImpl.b(illegalArgumentException);
            }
        }
        return false;
    }

    private boolean isSubDir(@NotNull String string, @NotNull String string2) {
        try {
            if (string == null) {
                RunningBuildImpl.$$$reportNull$$$0(51);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunningBuildImpl.b(illegalArgumentException);
        }
        try {
            if (string2 == null) {
                RunningBuildImpl.$$$reportNull$$$0(52);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunningBuildImpl.b(illegalArgumentException);
        }
        try {
            if (string2.length() < string.length()) {
                return false;
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunningBuildImpl.b(illegalArgumentException);
        }
        String string3 = string2.replace('\\', '/');
        return string3.startsWith(string);
    }

    @NotNull
    private String normalizeDirPath(@NotNull String string) {
        Object object;
        try {
            if (string == null) {
                RunningBuildImpl.$$$reportNull$$$0(53);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunningBuildImpl.b(illegalArgumentException);
        }
        Object object2 = string.replace('\\', '/');
        if (!((String)object2).endsWith("/")) {
            object2 = (String)object2 + "/";
        }
        try {
            object = object2;
            if (object == null) {
                RunningBuildImpl.$$$reportNull$$$0(54);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunningBuildImpl.b(illegalArgumentException);
        }
        return object;
    }

    @NotNull
    private List<BuildRunnerData> prepareRunners(@NotNull BuildTypeEx buildTypeEx, @NotNull Map<String, String> map, @NotNull BuildSettings buildSettings) {
        try {
            if (buildTypeEx == null) {
                RunningBuildImpl.$$$reportNull$$$0(55);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunningBuildImpl.b(illegalArgumentException);
        }
        try {
            if (map == null) {
                RunningBuildImpl.$$$reportNull$$$0(56);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunningBuildImpl.b(illegalArgumentException);
        }
        try {
            if (buildSettings == null) {
                RunningBuildImpl.$$$reportNull$$$0(57);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunningBuildImpl.b(illegalArgumentException);
        }
        BuildStartContextImpl buildStartContextImpl = new BuildStartContextImpl(this.myContext.getRunTypePerProjectRegistry().getBuildTypeRegistry((SBuildType)buildTypeEx), (SRunningBuild)this, new TreeMap<String, String>(map));
        buildStartContextImpl.setSteps(buildSettings.getAllBuildRunners());
        for (BuildStartContextProcessor buildStartContextProcessor : this.myContext.getExtensionsProvider().getExtensions(BuildStartContextProcessor.class)) {
            buildStartContextProcessor.updateParameters((BuildStartContext)buildStartContextImpl);
        }
        map.clear();
        map.putAll(buildStartContextImpl.getSharedParameters());
        for (BuildStartContextProcessor buildStartContextProcessor : buildStartContextImpl.getEnabledBuildRunners()) {
            TreeMap treeMap = new TreeMap(buildStartContextProcessor.getParameters());
            for (ParametersPreprocessor parametersPreprocessor : this.myContext.getExtensionsProvider().getExtensions(ParametersPreprocessor.class)) {
                parametersPreprocessor.fixRunBuildParameters((SRunningBuild)this, treeMap, map);
            }
            buildStartContextProcessor.setRunnerParameters(treeMap);
        }
        return this.createBuildRunnerData((BuildStepsEditor)buildStartContextImpl.getRootSteps());
    }

    @NotNull
    private List<BuildRunnerData> createBuildRunnerData(@NotNull BuildStepsEditor buildStepsEditor) {
        ArrayList<BuildRunnerData> arrayList;
        try {
            if (buildStepsEditor == null) {
                RunningBuildImpl.$$$reportNull$$$0(58);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunningBuildImpl.b(illegalArgumentException);
        }
        ArrayList<BuildRunnerData> arrayList2 = new ArrayList<BuildRunnerData>();
        for (SBuildStepDescriptor sBuildStepDescriptor : buildStepsEditor.getSteps()) {
            BuildRunnerData buildRunnerData = this.createBuildRunnerData(sBuildStepDescriptor);
            try {
                arrayList2.add(buildRunnerData);
                if (!(sBuildStepDescriptor instanceof GroupStepContext)) continue;
                buildRunnerData.setChildren(this.createBuildRunnerData((BuildStepsEditor)sBuildStepDescriptor));
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw RunningBuildImpl.b(illegalArgumentException);
            }
        }
        try {
            arrayList = arrayList2;
            if (arrayList == null) {
                RunningBuildImpl.$$$reportNull$$$0(59);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunningBuildImpl.b(illegalArgumentException);
        }
        return arrayList;
    }

    @NotNull
    private BuildRunnerData createBuildRunnerData(@NotNull SBuildStepDescriptor sBuildStepDescriptor) {
        boolean bl;
        String string;
        String string2;
        String string3;
        BuildRunnerData buildRunnerData;
        BuildRunnerData buildRunnerData2;
        try {
            if (sBuildStepDescriptor == null) {
                RunningBuildImpl.$$$reportNull$$$0(60);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunningBuildImpl.b(illegalArgumentException);
        }
        try {
            BuildRunnerData buildRunnerData3;
            buildRunnerData2 = buildRunnerData3;
            buildRunnerData = buildRunnerData3;
            string3 = sBuildStepDescriptor.getId();
            string2 = sBuildStepDescriptor.getRunType().getType();
            string = sBuildStepDescriptor.getFullDisplayName();
            bl = !sBuildStepDescriptor.isEnabled();
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunningBuildImpl.b(illegalArgumentException);
        }
        buildRunnerData2(string3, string2, string, bl, sBuildStepDescriptor.getParameters(), sBuildStepDescriptor.getOwnBuildParameters());
        return buildRunnerData;
    }

    private void finalizeBuildParameters(Map<String, String> map) {
        this.addAuthenticationParameters(map);
    }

    private void fillParameterMaps(@NotNull Map<String, String> map, @NotNull Map<String, String> map2) {
        try {
            if (map == null) {
                RunningBuildImpl.$$$reportNull$$$0(61);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunningBuildImpl.b(illegalArgumentException);
        }
        try {
            if (map2 == null) {
                RunningBuildImpl.$$$reportNull$$$0(62);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunningBuildImpl.b(illegalArgumentException);
        }
        this.resolveBuildNumber();
        Map map3 = this.getParametersProvider().getAll();
        map.putAll(map3);
        Collection collection = this.getBuildPromotion().getParametersCollection();
        for (Parameter parameter : collection) {
            try {
                map.put(parameter.getName(), parameter.getValue());
                if (parameter.getControlDescription() == null) continue;
                map2.put(parameter.getName(), ParametersUtil.toString((ControlDescription)parameter.getControlDescription()));
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw RunningBuildImpl.b(illegalArgumentException);
            }
        }
    }

    public String getRawBuildNumber() {
        return this.myRawBuildNumber;
    }

    public long getBuildCounter() {
        return this.myBuildCounter;
    }

    private void resolveBuildNumber() {
        boolean bl;
        String string = this.tryResolvingBuildNumber();
        try {
            bl = !this.myRawBuildNumber.equals(string);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunningBuildImpl.b(illegalArgumentException);
        }
        boolean bl2 = bl;
        try {
            if (bl2) {
                this.myRawBuildNumber = this.myProbablyResolvedBuildNumber = string;
                this.persistBuildNumber();
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunningBuildImpl.b(illegalArgumentException);
        }
    }

    @NotNull
    private String tryResolvingBuildNumber() {
        String string;
        String string2 = this.myRawBuildNumber.replaceAll("\\{(build.vcs.number[^}]*)\\}", "%$1%");
        ValueResolver valueResolver = this.getValueResolver();
        ProcessingResult processingResult = valueResolver.resolve(string2);
        try {
            string = processingResult.getResult();
            if (string == null) {
                RunningBuildImpl.$$$reportNull$$$0(63);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunningBuildImpl.b(illegalArgumentException);
        }
        return string;
    }

    private void persistBuildNumber() {
        this.myContext.getSQLRunner().withDBNoResult(dBFunctions -> {
            String string = "update running set build_number=? where build_id=?";
            try {
                dBFunctions.executeDml(string, new Object[]{UnicodeString.unicode((String)this.myRawBuildNumber, (int)512), this.getBuildId()});
            }
            catch (MySqlIncorrectStringValueException mySqlIncorrectStringValueException) {
                dBFunctions.executeDml(string, new Object[]{UnicodeString.unicode((String)StringUtil.removeNonBMPCharacters((String)this.myRawBuildNumber), (int)512), this.getBuildId()});
            }
        });
    }

    private void addAuthenticationParameters(Map<String, String> map) {
        map.put("system.teamcity.auth.userId", BuildAuthUtil.makeUserId((long)this.getBuildId()));
        map.put("system.teamcity.auth.password", this.getAgentAccessCode());
    }

    public boolean isProbablyHanging() {
        boolean bl;
        try {
            bl = this.getBuildPromotion().getAttribute("teamcity.build.hangingSince") != null;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunningBuildImpl.b(illegalArgumentException);
        }
        return bl;
    }

    private void refreshProbablyHangingAttribute() {
        try {
            if (this.myContext.getServerResponsibility().isResponsibleForBuild((SBuild)this)) {
                this.updateProbablyHangingAttribute();
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunningBuildImpl.b(illegalArgumentException);
        }
    }

    private void updateProbablyHangingAttribute() {
        block17: {
            Long l;
            String string;
            BuildPromotionEx buildPromotionEx;
            String string2;
            boolean bl;
            long l2;
            block19: {
                block18: {
                    boolean bl2;
                    boolean bl3;
                    block16: {
                        block15: {
                            l2 = this.now();
                            try {
                                try {
                                    if (!this.isHangingBuildsCalculationEnabled() || !this.computeProbablyHanging(l2)) break block15;
                                }
                                catch (IllegalArgumentException illegalArgumentException) {
                                    throw RunningBuildImpl.b(illegalArgumentException);
                                }
                                bl3 = true;
                                break block16;
                            }
                            catch (IllegalArgumentException illegalArgumentException) {
                                throw RunningBuildImpl.b(illegalArgumentException);
                            }
                        }
                        bl3 = false;
                    }
                    bl = bl3;
                    try {
                        bl2 = this.getBuildPromotion().getAttribute("teamcity.build.hangingSince") != null;
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        throw RunningBuildImpl.b(illegalArgumentException);
                    }
                    try {
                        try {
                            if (bl2 == bl) break block17;
                            LOG.info("Updating probably hanging attribute: " + LogUtil.describe((Build)this) + ", hanging = " + bl);
                            if (!bl) break block18;
                        }
                        catch (IllegalArgumentException illegalArgumentException) {
                            throw RunningBuildImpl.b(illegalArgumentException);
                        }
                        string2 = "This build is hanging";
                        break block19;
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        throw RunningBuildImpl.b(illegalArgumentException);
                    }
                }
                string2 = "This build is not hanging anymore";
            }
            String string3 = string2;
            try {
                this.getBuildLog().messageAsync(string3, Status.NORMAL, MessageAttrs.serverMessage());
                buildPromotionEx = this.getBuildPromotion();
                string = "teamcity.build.hangingSince";
                l = bl ? Long.valueOf(l2) : null;
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw RunningBuildImpl.b(illegalArgumentException);
            }
            try {
                buildPromotionEx.setAttribute(string, (Object)l);
                if (bl) {
                    this.getBuildPromotion().setAttribute("teamcity.build.hangingSinceLast", (Object)l2);
                }
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw RunningBuildImpl.b(illegalArgumentException);
            }
            this.getBuildPromotion().persist();
        }
    }

    private boolean isHangingBuildsCalculationEnabled() {
        boolean bl;
        block5: {
            block4: {
                BuildTypeEx buildTypeEx = this.getBuildType();
                try {
                    try {
                        if (buildTypeEx == null || !((Boolean)buildTypeEx.getOption(SBuildType.BT_HANGING_BUILDS_DETECTION_ENABLED)).booleanValue()) break block4;
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        throw RunningBuildImpl.b(illegalArgumentException);
                    }
                    bl = true;
                    break block5;
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw RunningBuildImpl.b(illegalArgumentException);
                }
            }
            bl = false;
        }
        return bl;
    }

    @VisibleForTesting
    long getHangingBuildsCalculationEnabledSince() {
        Date date = this.getBuildContext().getRunningBuildsManager().getServerReadyDate();
        try {
            if (date == null) {
                return this.myHangingBuildsCalculationEnabledSince;
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunningBuildImpl.b(illegalArgumentException);
        }
        return Math.max(this.myHangingBuildsCalculationEnabledSince, date.getTime() + 900000L);
    }

    @VisibleForTesting
    boolean computeProbablyHanging(long l) {
        boolean bl;
        block25: {
            block24: {
                block23: {
                    long l2 = this.getHangingBuildsCalculationEnabledSince();
                    try {
                        try {
                            if (l2 >= 0L && l >= l2) break block23;
                        }
                        catch (IllegalArgumentException illegalArgumentException) {
                            throw RunningBuildImpl.b(illegalArgumentException);
                        }
                        return false;
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        throw RunningBuildImpl.b(illegalArgumentException);
                    }
                }
                try {
                    if (!this.isStartedOnAgent()) {
                        return false;
                    }
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw RunningBuildImpl.b(illegalArgumentException);
                }
                try {
                    if (this.getLastBuildActivityTimestamp() == null) {
                        return false;
                    }
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw RunningBuildImpl.b(illegalArgumentException);
                }
                long l3 = this.getEstimatedDurationExceededTime(l);
                try {
                    if (l3 <= 0L) {
                        return false;
                    }
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw RunningBuildImpl.b(illegalArgumentException);
                }
                try {
                    if (l3 < 300L) {
                        return false;
                    }
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw RunningBuildImpl.b(illegalArgumentException);
                }
                long l4 = Math.max(1L, this.getDurationEstimate());
                long l5 = this.getElapsedTime(l);
                try {
                    if ((float)l3 / (float)l4 < 0.1f) {
                        return false;
                    }
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw RunningBuildImpl.b(illegalArgumentException);
                }
                long l6 = this.getLastBuildActivityTimestamp().getTime();
                long l7 = (l - l6) / 1000L;
                try {
                    try {
                        if (l7 <= 900L || !((float)l7 / (float)l5 > 0.2f)) break block24;
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        throw RunningBuildImpl.b(illegalArgumentException);
                    }
                    bl = true;
                    break block25;
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw RunningBuildImpl.b(illegalArgumentException);
                }
            }
            bl = false;
        }
        return bl;
    }

    @Used(value="tests")
    void setLastBuildActivityTimestamp(Date date) {
        this.myLastBuildActivityTimestamp = date;
    }

    public Date getLastBuildActivityTimestamp() {
        try {
            if (this.myLastBuildActivityTimestamp == null) {
                this.updateBuildActivityTimestamp();
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunningBuildImpl.b(illegalArgumentException);
        }
        return this.myLastBuildActivityTimestamp;
    }

    public long getTimeSpentSinceLastBuildActivity() {
        return (this.now() - this.getLastBuildActivityTimestamp().getTime()) / 1000L;
    }

    public void start(boolean bl) {
        block8: {
            try {
                block7: {
                    BuildTypeEx buildTypeEx = this.getBuildType();
                    try {
                        if (buildTypeEx == null) {
                            throw new BuildTypeNotFoundException("Cannot start new build. " + LogUtil.describeMissingBuildTypeForUI((String)this.getBuildTypeId()));
                        }
                    }
                    catch (Throwable throwable) {
                        throw RunningBuildImpl.b(throwable);
                    }
                    try {
                        this.persist();
                        this.myProbablyResolvedBuildNumber = this.tryResolvingBuildNumber();
                        if (!bl) break block7;
                        this.getBuildStarter().startBuildAsync((RunningBuildEx)this);
                        break block8;
                    }
                    catch (Throwable throwable) {
                        throw RunningBuildImpl.b(throwable);
                    }
                }
                this.getBuildStarter().startBuild((RunningBuildEx)this);
            }
            catch (Throwable throwable) {
                BuildPromotionEx buildPromotionEx = this.getBuildPromotion();
                buildPromotionEx.setAssociatedBuild(null);
                buildPromotionEx.setRemoveFromQueueDate(null);
                buildPromotionEx.persist();
                new RunningBuildDeleteQuery(this.myContext.getSQLRunner()).deleteBuild(this.myBuildId);
                this.myContext.getRunningBuildsManager().removeRunningBuild((RunningBuildEx)this);
                ExceptionUtil.rethrowAsRuntimeException((Throwable)throwable);
            }
        }
    }

    @NotNull
    BuildStarter getBuildStarter() {
        BuildStarter buildStarter;
        try {
            if (this.myBuildStarter == null) {
                this.myBuildStarter = this.myContext.getBuildStarterFactory().createBuildStarter();
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunningBuildImpl.b(illegalArgumentException);
        }
        try {
            buildStarter = this.myBuildStarter;
            if (buildStarter == null) {
                RunningBuildImpl.$$$reportNull$$$0(64);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunningBuildImpl.b(illegalArgumentException);
        }
        return buildStarter;
    }

    public boolean isStarting() {
        boolean bl;
        block5: {
            block4: {
                try {
                    try {
                        if (!this.myStartingBuild || this.isStartedOnAgent()) break block4;
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        throw RunningBuildImpl.b(illegalArgumentException);
                    }
                    bl = true;
                    break block5;
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw RunningBuildImpl.b(illegalArgumentException);
                }
            }
            bl = false;
        }
        return bl;
    }

    public void stop(@NotNull RunningBuildState runningBuildState, @Nullable User user, @NotNull String string, boolean bl) {
        try {
            if (runningBuildState == null) {
                RunningBuildImpl.$$$reportNull$$$0(65);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunningBuildImpl.b(illegalArgumentException);
        }
        try {
            if (string == null) {
                RunningBuildImpl.$$$reportNull$$$0(66);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunningBuildImpl.b(illegalArgumentException);
        }
        try {
            this.setInterrupted(runningBuildState, user, string);
            if (!this.isInterrupted()) {
                return;
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunningBuildImpl.b(illegalArgumentException);
        }
        try {
            this.stopNoInterrupt();
            if (bl) {
                this.restoreInQueue(user, false);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunningBuildImpl.b(illegalArgumentException);
        }
    }

    public void finish(@NotNull Date date) {
        block10: {
            try {
                if (date == null) {
                    RunningBuildImpl.$$$reportNull$$$0(67);
                }
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw RunningBuildImpl.b(illegalArgumentException);
            }
            try {
                if (this.isFinished()) {
                    LOG.warn("Attempt to finish an already finished build " + LogUtil.describe((Build)this));
                    return;
                }
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw RunningBuildImpl.b(illegalArgumentException);
            }
            try {
                try {
                    if (this.getDetachFromAgentReceivedTimestamp() != null || this.getFinishOnAgentDate() != null) break block10;
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw RunningBuildImpl.b(illegalArgumentException);
                }
                throw new IllegalStateException("Build is still running on agent");
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw RunningBuildImpl.b(illegalArgumentException);
            }
        }
        this.myContext.getAgentMessagesQueue().buildFinished((RunningBuildEx)this, date, false);
    }

    public final void stopNoInterrupt() {
        this.doSubmitStopTask();
    }

    private void doSubmitStopTask() {
        this.myContext.getStopBuildTasks().submit((SBuild)this);
    }

    public void buildPatch(OutputStream outputStream, @NotNull FullCheckoutFlag fullCheckoutFlag, boolean bl, @NotNull AgentCheckoutDirRevisionInfo agentCheckoutDirRevisionInfo) throws IOException {
        try {
            if (fullCheckoutFlag == null) {
                RunningBuildImpl.$$$reportNull$$$0(68);
            }
        }
        catch (IOException iOException) {
            throw RunningBuildImpl.b(iOException);
        }
        try {
            if (agentCheckoutDirRevisionInfo == null) {
                RunningBuildImpl.$$$reportNull$$$0(69);
            }
        }
        catch (IOException iOException) {
            throw RunningBuildImpl.b(iOException);
        }
        CountingOutputStream countingOutputStream = new CountingOutputStream(outputStream);
        this.getBuildStarter().buildPatch((RunningBuildEx)this, (OutputStream)new ServerLoadMetricsAwareOutputStream((OutputStream)countingOutputStream, ServerLoadMetrics.PATCH), fullCheckoutFlag, bl, null, agentCheckoutDirRevisionInfo);
        ((PatchEventsListener)this.myContext.getPatchEventsDispatcher().getMulticaster()).patchSent((SRunningBuild)this, countingOutputStream.getCount());
    }

    public void buildPersonalPatch(OutputStream outputStream, long l, @NotNull AgentCheckoutDirRevisionInfo agentCheckoutDirRevisionInfo) throws IOException {
        try {
            if (agentCheckoutDirRevisionInfo == null) {
                RunningBuildImpl.$$$reportNull$$$0(70);
            }
        }
        catch (IOException iOException) {
            throw RunningBuildImpl.b(iOException);
        }
        CountingOutputStream countingOutputStream = new CountingOutputStream(outputStream);
        this.getBuildStarter().buildPatch((RunningBuildEx)this, (OutputStream)new ServerLoadMetricsAwareOutputStream((OutputStream)countingOutputStream, ServerLoadMetrics.PATCH), new FullCheckoutFlag(), true, Long.valueOf(l), agentCheckoutDirRevisionInfo);
        ((PatchEventsListener)this.myContext.getPatchEventsDispatcher().getMulticaster()).patchSent((SRunningBuild)this, countingOutputStream.getCount());
    }

    public int getAgentId() {
        return this.myAgentId;
    }

    @NotNull
    public Map<String, String> getBuildOwnParameters() {
        HashMap<String, String> hashMap;
        HashMap<String, String> hashMap2 = new HashMap<String, String>(super.getBuildOwnParameters());
        try {
            this.addAuthenticationParameters(hashMap2);
            hashMap = hashMap2;
            if (hashMap == null) {
                RunningBuildImpl.$$$reportNull$$$0(71);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunningBuildImpl.b(illegalArgumentException);
        }
        return hashMap;
    }

    private void persist() {
        try {
            if (this.myPersisted.get()) {
                return;
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunningBuildImpl.b(illegalArgumentException);
        }
        this.getBuildPromotion().persist();
        this.myContext.getSQLRunner().runAndRetryNoResult(dBFunctions -> {
            try {
                new RunningBuildInsertQuery(dBFunctions).insertBuild(this.getBuildPromotion().getParentBuildTypeId(), (RunningBuildEx)this, this.myAgentId, this.myAgentTypeId);
            }
            catch (DBDuplicatedRowException dBDuplicatedRowException) {
                Loggers.SERVER.debug("Running build is already persisted: " + this.myBuildId);
            }
        });
        Loggers.SERVER.info("Running build saved to DB: " + LogUtil.describe((Build)this));
        this.myPersisted.set(true);
    }

    @NotNull
    public TestRunMuter getTestRunMuter() {
        TestRunMuter testRunMuter;
        try {
            testRunMuter = this.myTestRunMuter;
            if (testRunMuter == null) {
                RunningBuildImpl.$$$reportNull$$$0(72);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunningBuildImpl.b(illegalArgumentException);
        }
        return testRunMuter;
    }

    @NotNull
    public UserDataStorage getUserDataStorage() {
        UserDataStorage userDataStorage;
        try {
            userDataStorage = this.myUserDataStorage;
            if (userDataStorage == null) {
                RunningBuildImpl.$$$reportNull$$$0(73);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunningBuildImpl.b(illegalArgumentException);
        }
        return userDataStorage;
    }

    public void setBuildStatus(Status status) {
        block9: {
            block7: {
                block8: {
                    try {
                        try {
                            if (!status.isFailed()) break block7;
                            if (status != Status.ERROR) break block8;
                        }
                        catch (IllegalArgumentException illegalArgumentException) {
                            throw RunningBuildImpl.b(illegalArgumentException);
                        }
                        LOG.debug("Setting ERROR status to build " + LogUtil.describe((Build)this));
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        throw RunningBuildImpl.b(illegalArgumentException);
                    }
                }
                this.addBuildProblem(BuildProblemUtil.createSingletonBuildProblem((String)"TC_UNKNOWN", (String)"Unknown build problem"));
                break block9;
            }
            try {
                if (status == Status.NORMAL) {
                    this.muteBuildProblems(true);
                }
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw RunningBuildImpl.b(illegalArgumentException);
            }
        }
    }

    public void markAsFinished(@NotNull Date date, boolean bl) {
        try {
            if (date == null) {
                RunningBuildImpl.$$$reportNull$$$0(74);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunningBuildImpl.b(illegalArgumentException);
        }
        this.doMarkAsFinished(date, bl, true);
    }

    public void markAsFinishedUnexpectedly(@NotNull String string) {
        try {
            if (string == null) {
                RunningBuildImpl.$$$reportNull$$$0(75);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunningBuildImpl.b(illegalArgumentException);
        }
        this.runUnlessFinished(() -> this.setInterrupted(RunningBuildState.INTERRUPTED_WITH_RERUN, null, string));
        this.doMarkAsFinished(this.nowDate(), false, true);
    }

    @Used(value="tests")
    public void finishImmediately(@NotNull Date date, boolean bl) {
        block7: {
            try {
                if (date == null) {
                    RunningBuildImpl.$$$reportNull$$$0(76);
                }
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw RunningBuildImpl.b(illegalArgumentException);
            }
            try {
                try {
                    if ($assertionsDisabled || LogInitializer.isUnitTest()) break block7;
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw RunningBuildImpl.b(illegalArgumentException);
                }
                throw new AssertionError();
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw RunningBuildImpl.b(illegalArgumentException);
            }
        }
        this.doMarkAsFinished(date, bl, false);
        this.finish();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void doMarkAsFinished(@NotNull Date date, boolean bl, boolean bl2) {
        block22: {
            try {
                if (date == null) {
                    RunningBuildImpl.$$$reportNull$$$0(77);
                }
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw RunningBuildImpl.b(illegalArgumentException);
            }
            this.persist();
            try {
                block21: {
                    block24: {
                        block23: {
                            boolean bl3 = (Boolean)this.myContext.getSQLRunner().runAndRetry(dBFunctions -> {
                                boolean bl;
                                int n = dBFunctions.executeDml("update running set build_finish_time_server = ? where build_id = ? and build_finish_time_server is null", new Object[]{date.getTime(), this.myBuildId});
                                try {
                                    bl = n != 0;
                                }
                                catch (DBException dBException) {
                                    throw RunningBuildImpl.b(dBException);
                                }
                                return bl;
                            });
                            if (!bl3) break block21;
                            this.myFinishTimeServer = date;
                            Loggers.SERVER.info("Build finished received for build: " + LogUtil.describe((Build)this));
                            if (!bl) break block22;
                            break block23;
                            catch (IllegalArgumentException illegalArgumentException) {
                                throw RunningBuildImpl.b(illegalArgumentException);
                            }
                        }
                        if (this.isInterrupted()) break block22;
                        break block24;
                        catch (IllegalArgumentException illegalArgumentException) {
                            throw RunningBuildImpl.b(illegalArgumentException);
                        }
                    }
                    try {
                        block25: {
                            if (this.getBuildStatus().isFailed()) break block22;
                            break block25;
                            catch (IllegalArgumentException illegalArgumentException) {
                                throw RunningBuildImpl.b(illegalArgumentException);
                            }
                        }
                        this.addBuildProblem(BuildProblemUtil.createSingletonBuildProblem((String)"TC_UNKNOWN", (String)"Unknown build problem reported on agent"));
                        break block22;
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        throw RunningBuildImpl.b(illegalArgumentException);
                    }
                }
                Loggers.SERVER.info("Build was already marked as finished: " + LogUtil.describe((Build)this));
                if (!this.updateBuildInfo()) break block22;
                try {
                    block26: {
                        if (this.myFinishTimeServer != null) break block22;
                        break block26;
                        catch (IllegalArgumentException illegalArgumentException) {
                            throw RunningBuildImpl.b(illegalArgumentException);
                        }
                    }
                    this.myFinishTimeServer = date;
                    Loggers.SERVER.warn("Build was marked as finished, but finishTimeServer field was not set");
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw RunningBuildImpl.b(illegalArgumentException);
                }
            }
            catch (Throwable throwable) {
                try {
                    if (!bl2) throw throwable;
                    this.myContext.getRunningBuildsManager().scheduleBuildFinishing((RunningBuildEx)this);
                    throw throwable;
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw RunningBuildImpl.b(illegalArgumentException);
                }
            }
        }
        try {
            if (!bl2) return;
            this.myContext.getRunningBuildsManager().scheduleBuildFinishing((RunningBuildEx)this);
            return;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunningBuildImpl.b(illegalArgumentException);
        }
    }

    void setHangingBuildsCalculationEnabledSince(@Nullable Date date) {
        long l;
        try {
            RunningBuildImpl runningBuildImpl = this;
            l = date == null ? -1L : date.getTime();
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunningBuildImpl.b(illegalArgumentException);
        }
        runningBuildImpl.myHangingBuildsCalculationEnabledSince = l;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void resetAgentCache() {
        AtomicInteger atomicInteger = this.myAgentResetCount;
        synchronized (atomicInteger) {
            this.myAgent = null;
            this.myAgentResetCount.incrementAndGet();
        }
    }

    public void serverShutdown() {
        try {
            if (!this.myContext.getServerResponsibility().isResponsibleForBuild((SBuild)this)) {
                return;
            }
        }
        catch (Exception exception) {
            throw RunningBuildImpl.b(exception);
        }
        try {
            BuildLogEx buildLogEx = this.getBuildLog();
            buildLogEx.flush();
        }
        catch (Exception exception) {
            Loggers.SERVER.warnAndDebugDetails("Exception on shutdown in running build: " + this.myBuildId, (Throwable)exception);
        }
    }

    public boolean isCompositeBuild() {
        return false;
    }

    public void closeBuildLog() {
        BaseRunningBuild.closeBuildLog((BuildPromotion)this.getBuildPromotion(), (Date)this.myFinishTimeServer);
    }

    @NotNull
    public ArtifactsUploadLimit getArtifactsLimit() {
        ArtifactsUploadLimit artifactsUploadLimit;
        try {
            artifactsUploadLimit = ((ArtifactsUploadLimit)this.myArtifactsLimit.getValue()).decreaseTotalArtifactsSizeLimit(this.getArtifactsStats().getTotalSize());
            if (artifactsUploadLimit == null) {
                RunningBuildImpl.$$$reportNull$$$0(78);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunningBuildImpl.b(illegalArgumentException);
        }
        return artifactsUploadLimit;
    }

    @NotNull
    public Map<String, Boolean> getRunnersState() throws BuildTypeNotFoundException {
        Map<String, Boolean> map2;
        try {
            map2 = this.myLazyRunnersState.updateAndGet(map -> {
                Map map2;
                try {
                    map2 = map != null ? map : super.getRunnersState();
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw RunningBuildImpl.b(illegalArgumentException);
                }
                return map2;
            });
            if (map2 == null) {
                RunningBuildImpl.$$$reportNull$$$0(79);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw RunningBuildImpl.b(buildTypeNotFoundException);
        }
        return map2;
    }

    public void detachedFromAgent() {
        try {
            if (this.isDetachedFromAgent()) {
                return;
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunningBuildImpl.b(illegalArgumentException);
        }
        BuildPromotionEx buildPromotionEx = this.getBuildPromotion();
        buildPromotionEx.setAttribute("teamcity.build.detachedFromAgentTimestamp", (Object)this.now());
        buildPromotionEx.persist();
        this.myContext.getRunningBuildsManager().detachFromAgent((RunningBuildEx)this);
        this.getBuildLog().messageAsync("Build detached from agent", Status.NORMAL, MessageAttrs.serverMessage());
        ((BuildServerListener)this.myContext.getEventDispatcher().getMulticaster()).buildDetachedFromAgent((SRunningBuild)this);
        this.myContext.getNodesBuildsEvents().publishBuildDetachedFromAgent((SRunningBuild)this);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Unable to fully structure code
     */
    private /* synthetic */ void lambda$doBuildFinish$3() {
        block16: {
            block15: {
                this.updateBuildInternal(true, true);
                if (!RunningBuildImpl.shouldCancelBuildOnMissingRevision()) break block15;
                try {
                    block17: {
                        if (!this.getBuildProblems().stream().anyMatch((Predicate<BuildProblem>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)Z, lambda$doBuildFinish$2(jetbrains.buildServer.serverSide.problems.BuildProblem ), (Ljetbrains/buildServer/serverSide/problems/BuildProblem;)Z)())) break block15;
                        break block17;
                        catch (Throwable v0) {
                            throw RunningBuildImpl.b(v0);
                        }
                    }
                    this.setInterrupted(RunningBuildState.INTERRUPTED_BY_SYSTEM, null, "Build revision not found");
                }
                catch (Throwable v1) {
                    throw RunningBuildImpl.b(v1);
                }
            }
            try {
                this.finalSteps();
                var1_1 = this.getCanceledInfo();
                var2_2 = "Finishing build " + LogUtil.describe((BuildTypeEx)this.getBuildType()) + " # " + LogUtil.describe((Build)this) + ", personal: " + this.isPersonal() + ", interrupted: " + this.isInterrupted() + ", cancelled: " + String.valueOf(var1_1);
                try {
                    Loggers.ACTIVITIES.debug(var2_2);
                    if (var1_1 == null || this.getRunningState() != RunningBuildState.INTERRUPTED_WITH_RERUN) break block16;
                }
                catch (Throwable v2) {
                    throw RunningBuildImpl.b(v2);
                }
                var3_4 = new RestoreBuildInQueue((RunningBuildEx)this, this.getBuildContext().getQueue(), true);
                try {
                    var3_4.restoreBuildInQueue(null);
                }
                catch (Throwable var4_5) {
                    Loggers.SERVER.warnAndDebugDetails("Failed to restore build in the queue: " + LogUtil.describe((Build)this), var4_5);
                }
            }
            catch (Throwable var5_6) {
                this.writeFinishProperties();
                this.writeOutputProperties();
                var6_7 = new HistoryBuildSnapshot(this.myContext.getBuildStatisticsPrinterFactory(), (RunningBuildEx)this);
                this.logFinalMessages();
                this.myContext.getHistory().createEntry((HistoryBuild)var6_7, this.myFinishTimeServer);
                var7_8 = this.updateBuildFinishDelayMetric();
                try {
                    if (var7_8 >= 0L) {
                        this.publishStatisticValue("serverSideBuildFinishing", new BigDecimal(var7_8));
                    }
                }
                catch (Throwable v3) {
                    throw RunningBuildImpl.b(v3);
                }
                throw var5_6;
            }
        }
        this.writeFinishProperties();
        this.writeOutputProperties();
        var1_1 = new HistoryBuildSnapshot(this.myContext.getBuildStatisticsPrinterFactory(), (RunningBuildEx)this);
        this.logFinalMessages();
        this.myContext.getHistory().createEntry((HistoryBuild)var1_1, this.myFinishTimeServer);
        var2_3 = this.updateBuildFinishDelayMetric();
        try {
            if (var2_3 < 0L) ** GOTO lbl59
            this.publishStatisticValue("serverSideBuildFinishing", new BigDecimal(var2_3));
        }
        catch (Throwable v4) {
            throw RunningBuildImpl.b(v4);
        }
lbl59:
        // 2 sources

        this.propagateProblemsAndRemoveRunningBuild();
    }

    private static /* synthetic */ boolean lambda$doBuildFinish$2(BuildProblem buildProblem) {
        return "TC_BUILD_REVISION_NOT_FOUND".equals(buildProblem.getBuildProblemData().getType());
    }

    static {
        boolean bl;
        try {
            bl = !RunningBuildImpl.class.desiredAssertionStatus();
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw RunningBuildImpl.b(illegalArgumentException);
        }
        $assertionsDisabled = bl;
        LOG = Logger.getInstance((String)RunningBuildImpl.class.getName());
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

    private static Throwable b(Throwable throwable) {
        return throwable;
    }
}
