/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Sets
 *  com.intellij.openapi.diagnostic.Logger
 *  com.intellij.openapi.util.Pair
 *  gnu.trove.THashMap
 *  javax.annotation.concurrent.GuardedBy
 *  jetbrains.buildServer.AgentRestrictorType
 *  jetbrains.buildServer.BuildProblemData
 *  jetbrains.buildServer.BuildTypeDescriptor$CheckoutType
 *  jetbrains.buildServer.artifacts.RevisionRule
 *  jetbrains.buildServer.artifacts.RevisionRules
 *  jetbrains.buildServer.log.Loggable
 *  jetbrains.buildServer.log.Loggers
 *  jetbrains.buildServer.messages.ErrorData
 *  jetbrains.buildServer.messages.Status
 *  jetbrains.buildServer.parameters.ReferencesResolverUtil
 *  jetbrains.buildServer.serverSide.AgentRestrictorFactory
 *  jetbrains.buildServer.serverSide.ArtifactDependencyFactory
 *  jetbrains.buildServer.serverSide.Branch
 *  jetbrains.buildServer.serverSide.BuildPromotion
 *  jetbrains.buildServer.serverSide.BuildPromotionEx
 *  jetbrains.buildServer.serverSide.BuildPromotionEx$ArtifactsState
 *  jetbrains.buildServer.serverSide.BuildPromotionEx$ArtifactsStateInfo
 *  jetbrains.buildServer.serverSide.BuildPromotionEx$PromotionPersistMode
 *  jetbrains.buildServer.serverSide.BuildPromotionEx$UpdateReason
 *  jetbrains.buildServer.serverSide.BuildPromotionManager$BuildPromotionInitFromDbHint
 *  jetbrains.buildServer.serverSide.BuildPromotionOrderInfo
 *  jetbrains.buildServer.serverSide.BuildRevision
 *  jetbrains.buildServer.serverSide.BuildRevisionCheckoutMode
 *  jetbrains.buildServer.serverSide.BuildRevisionEx
 *  jetbrains.buildServer.serverSide.BuildRevisionVcsRootType
 *  jetbrains.buildServer.serverSide.BuildServerListener
 *  jetbrains.buildServer.serverSide.BuildSettings
 *  jetbrains.buildServer.serverSide.BuildTypeEx
 *  jetbrains.buildServer.serverSide.BuildTypeNotFoundException
 *  jetbrains.buildServer.serverSide.BuildTypeOptions
 *  jetbrains.buildServer.serverSide.BuildTypeOrderedBuilds
 *  jetbrains.buildServer.serverSide.BuildTypeSettingsEx
 *  jetbrains.buildServer.serverSide.ChangesCalculationOptionsEx
 *  jetbrains.buildServer.serverSide.CheckoutRulesFactory
 *  jetbrains.buildServer.serverSide.CurrentNodeInfo
 *  jetbrains.buildServer.serverSide.DependencyConsumer
 *  jetbrains.buildServer.serverSide.DependencyConsumer$Result
 *  jetbrains.buildServer.serverSide.MultiNodeTasks
 *  jetbrains.buildServer.serverSide.MultiNodeTasks$SubmittedTask
 *  jetbrains.buildServer.serverSide.MultiNodeTasks$Task
 *  jetbrains.buildServer.serverSide.MultiNodeTasks$TaskData
 *  jetbrains.buildServer.serverSide.OrderedBuild
 *  jetbrains.buildServer.serverSide.Parameter
 *  jetbrains.buildServer.serverSide.PipelineView
 *  jetbrains.buildServer.serverSide.QueuedBuildEx
 *  jetbrains.buildServer.serverSide.ReadOnlyBuildTypeSettings
 *  jetbrains.buildServer.serverSide.ReadOnlyUserParameters
 *  jetbrains.buildServer.serverSide.RepositoryVersion
 *  jetbrains.buildServer.serverSide.SBuild
 *  jetbrains.buildServer.serverSide.SBuildAgent
 *  jetbrains.buildServer.serverSide.SBuildFeatureDescriptor
 *  jetbrains.buildServer.serverSide.SBuildType
 *  jetbrains.buildServer.serverSide.SProject
 *  jetbrains.buildServer.serverSide.SQLRunner
 *  jetbrains.buildServer.serverSide.SQueuedBuild
 *  jetbrains.buildServer.serverSide.SimpleParameter
 *  jetbrains.buildServer.serverSide.TagData
 *  jetbrains.buildServer.serverSide.TagDataUtil
 *  jetbrains.buildServer.serverSide.TeamCityNode
 *  jetbrains.buildServer.serverSide.TeamCityNodes
 *  jetbrains.buildServer.serverSide.TeamCityProperties
 *  jetbrains.buildServer.serverSide.artifacts.BuildArtifact
 *  jetbrains.buildServer.serverSide.artifacts.BuildArtifacts
 *  jetbrains.buildServer.serverSide.artifacts.BuildArtifactsViewMode
 *  jetbrains.buildServer.serverSide.artifacts.SArtifactDependency
 *  jetbrains.buildServer.serverSide.audit.ActionType
 *  jetbrains.buildServer.serverSide.audit.AuditLog
 *  jetbrains.buildServer.serverSide.audit.AuditLogAction
 *  jetbrains.buildServer.serverSide.audit.AuditLogFilter
 *  jetbrains.buildServer.serverSide.auth.AuthUtil
 *  jetbrains.buildServer.serverSide.auth.AuthorityHolder
 *  jetbrains.buildServer.serverSide.auth.Permission
 *  jetbrains.buildServer.serverSide.buildDistribution.BuildPromotionInfo
 *  jetbrains.buildServer.serverSide.buildLog.BuildLogEx
 *  jetbrains.buildServer.serverSide.buildLog.MessageAttrs
 *  jetbrains.buildServer.serverSide.comments.Comment
 *  jetbrains.buildServer.serverSide.db.DBAction
 *  jetbrains.buildServer.serverSide.db.DBException
 *  jetbrains.buildServer.serverSide.db.DBFunctions
 *  jetbrains.buildServer.serverSide.db.InParams
 *  jetbrains.buildServer.serverSide.db.MySQL.MySqlIncorrectStringValueException
 *  jetbrains.buildServer.serverSide.db.SQLRunnerEx
 *  jetbrains.buildServer.serverSide.db.UnicodeString
 *  jetbrains.buildServer.serverSide.db.queries.GenericQuery
 *  jetbrains.buildServer.serverSide.dependency.BuildDependency
 *  jetbrains.buildServer.serverSide.dependency.BuildDependencyEx
 *  jetbrains.buildServer.serverSide.dependency.BuildDependencyGraph
 *  jetbrains.buildServer.serverSide.dependency.Dependency
 *  jetbrains.buildServer.serverSide.dependency.DependencyOptions
 *  jetbrains.buildServer.serverSide.dependency.OrganizationProjectAccessViolationException
 *  jetbrains.buildServer.serverSide.identifiers.BuildTypeIdentifiersManager
 *  jetbrains.buildServer.serverSide.impl.AbstractBuildPromotion
 *  jetbrains.buildServer.serverSide.impl.ArtifactsUtil
 *  jetbrains.buildServer.serverSide.impl.BaseBuild
 *  jetbrains.buildServer.serverSide.impl.BranchImpl
 *  jetbrains.buildServer.serverSide.impl.BranchNameGenerator
 *  jetbrains.buildServer.serverSide.impl.BuildChangesCalculator
 *  jetbrains.buildServer.serverSide.impl.BuildPromotionDeletedException
 *  jetbrains.buildServer.serverSide.impl.BuildPromotionImpl$ArtifactDepTuple
 *  jetbrains.buildServer.serverSide.impl.BuildPromotionImpl$ArtifactsStateInfoImpl
 *  jetbrains.buildServer.serverSide.impl.BuildPromotionImpl$AttrTuple
 *  jetbrains.buildServer.serverSide.impl.BuildPromotionImpl$BranchOrderedBuildsSupplier
 *  jetbrains.buildServer.serverSide.impl.BuildPromotionImpl$BuildLogRef
 *  jetbrains.buildServer.serverSide.impl.BuildPromotionImpl$BuildProjectEntry
 *  jetbrains.buildServer.serverSide.impl.BuildPromotionImpl$CachedBuildArtifacts
 *  jetbrains.buildServer.serverSide.impl.BuildPromotionImpl$DependencyTuple
 *  jetbrains.buildServer.serverSide.impl.BuildPromotionImpl$DepsSupplier
 *  jetbrains.buildServer.serverSide.impl.BuildPromotionImpl$IncompleteInfo
 *  jetbrains.buildServer.serverSide.impl.BuildPromotionImpl$ModIdAndBranchTuple
 *  jetbrains.buildServer.serverSide.impl.BuildPromotionImpl$NonDefaultBranch
 *  jetbrains.buildServer.serverSide.impl.BuildPromotionImpl$PromoId
 *  jetbrains.buildServer.serverSide.impl.BuildPromotionImpl$VcsRootEntryTuple
 *  jetbrains.buildServer.serverSide.impl.BuildPromotionListener
 *  jetbrains.buildServer.serverSide.impl.BuildPromotionManagerEx
 *  jetbrains.buildServer.serverSide.impl.BuildPromotionParameters
 *  jetbrains.buildServer.serverSide.impl.BuildPromotionProblems
 *  jetbrains.buildServer.serverSide.impl.BuildPromotionRevisionsQuery
 *  jetbrains.buildServer.serverSide.impl.BuildRevisionData
 *  jetbrains.buildServer.serverSide.impl.BuildTypeImpl
 *  jetbrains.buildServer.serverSide.impl.CachingAuditLogActions
 *  jetbrains.buildServer.serverSide.impl.DependencyCheckResult
 *  jetbrains.buildServer.serverSide.impl.DependencyType
 *  jetbrains.buildServer.serverSide.impl.DummyBuild
 *  jetbrains.buildServer.serverSide.impl.EquivalenceCheckResult
 *  jetbrains.buildServer.serverSide.impl.InsertBuildPromotionQuery
 *  jetbrains.buildServer.serverSide.impl.InvalidOverridenRootsException
 *  jetbrains.buildServer.serverSide.impl.LogUtil
 *  jetbrains.buildServer.serverSide.impl.MaxRunningBuildsPerBranchProvider
 *  jetbrains.buildServer.serverSide.impl.NoOpBuildLog
 *  jetbrains.buildServer.serverSide.impl.PipelineViewImpl
 *  jetbrains.buildServer.serverSide.impl.ProjectEx
 *  jetbrains.buildServer.serverSide.impl.RemoteBuildType
 *  jetbrains.buildServer.serverSide.impl.RemoteBuildTypeIdUtil
 *  jetbrains.buildServer.serverSide.impl.VersionedSettingsChangesProvider
 *  jetbrains.buildServer.serverSide.impl.artifacts.BuildArtifactsFactory
 *  jetbrains.buildServer.serverSide.impl.audit.filters.ActionTypesFilter
 *  jetbrains.buildServer.serverSide.impl.beans.BuildPromotionContext
 *  jetbrains.buildServer.serverSide.impl.dependency.BuildPromotionDependencies
 *  jetbrains.buildServer.serverSide.impl.history.AbstractBuildData
 *  jetbrains.buildServer.serverSide.problems.BuildProblem
 *  jetbrains.buildServer.users.SUser
 *  jetbrains.buildServer.users.User
 *  jetbrains.buildServer.util.CollectionsUtil
 *  jetbrains.buildServer.util.ExceptionUtil
 *  jetbrains.buildServer.util.FileUtil
 *  jetbrains.buildServer.util.Option
 *  jetbrains.buildServer.util.StringPool
 *  jetbrains.buildServer.util.StringUtil
 *  jetbrains.buildServer.util.filters.FilterUtil
 *  jetbrains.buildServer.util.graph.BFSVisitor
 *  jetbrains.buildServer.vcs.BranchSpec
 *  jetbrains.buildServer.vcs.CheckoutRules
 *  jetbrains.buildServer.vcs.RepositoryState
 *  jetbrains.buildServer.vcs.SVcsModification
 *  jetbrains.buildServer.vcs.SVcsRoot
 *  jetbrains.buildServer.vcs.SVcsRootEx
 *  jetbrains.buildServer.vcs.SelectPrevBuildPolicy
 *  jetbrains.buildServer.vcs.VcsException
 *  jetbrains.buildServer.vcs.VcsRoot
 *  jetbrains.buildServer.vcs.VcsRootInstance
 *  jetbrains.buildServer.vcs.VcsRootInstanceEntry
 *  jetbrains.buildServer.vcs.VcsRootInstanceEx
 *  jetbrains.buildServer.vcs.VcsRootVcsException
 *  jetbrains.buildServer.vcs.VcsSupportCore
 *  jetbrains.buildServer.vcs.VcsUtil
 *  jetbrains.buildServer.vcs.impl.DeletedVcsRoot
 *  jetbrains.buildServer.vcs.impl.SVcsRootImpl
 *  jetbrains.buildServer.vcs.impl.VcsRootInstanceWithParent
 *  jetbrains.buildServer.version.ServerVersionHolder
 *  org.apache.commons.codec.DecoderException
 *  org.apache.commons.codec.binary.Hex
 *  org.apache.commons.codec.digest.DigestUtils
 *  org.apache.commons.io.FileUtils
 *  org.jetbrains.annotations.Contract
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package jetbrains.buildServer.serverSide.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.util.Pair;
import gnu.trove.THashMap;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.lang.invoke.LambdaMetafactory;
import java.lang.ref.SoftReference;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import javax.annotation.concurrent.GuardedBy;
import jetbrains.buildServer.AgentRestrictorType;
import jetbrains.buildServer.BuildProblemData;
import jetbrains.buildServer.BuildTypeDescriptor;
import jetbrains.buildServer.artifacts.RevisionRule;
import jetbrains.buildServer.artifacts.RevisionRules;
import jetbrains.buildServer.log.Loggable;
import jetbrains.buildServer.log.Loggers;
import jetbrains.buildServer.messages.ErrorData;
import jetbrains.buildServer.messages.Status;
import jetbrains.buildServer.parameters.ReferencesResolverUtil;
import jetbrains.buildServer.serverSide.AgentRestrictorFactory;
import jetbrains.buildServer.serverSide.ArtifactDependencyFactory;
import jetbrains.buildServer.serverSide.Branch;
import jetbrains.buildServer.serverSide.BuildPromotion;
import jetbrains.buildServer.serverSide.BuildPromotionEx;
import jetbrains.buildServer.serverSide.BuildPromotionManager;
import jetbrains.buildServer.serverSide.BuildPromotionOrderInfo;
import jetbrains.buildServer.serverSide.BuildRevision;
import jetbrains.buildServer.serverSide.BuildRevisionCheckoutMode;
import jetbrains.buildServer.serverSide.BuildRevisionEx;
import jetbrains.buildServer.serverSide.BuildRevisionVcsRootType;
import jetbrains.buildServer.serverSide.BuildServerListener;
import jetbrains.buildServer.serverSide.BuildSettings;
import jetbrains.buildServer.serverSide.BuildTypeEx;
import jetbrains.buildServer.serverSide.BuildTypeNotFoundException;
import jetbrains.buildServer.serverSide.BuildTypeOptions;
import jetbrains.buildServer.serverSide.BuildTypeOrderedBuilds;
import jetbrains.buildServer.serverSide.BuildTypeSettingsEx;
import jetbrains.buildServer.serverSide.ChangesCalculationOptionsEx;
import jetbrains.buildServer.serverSide.CheckoutRulesFactory;
import jetbrains.buildServer.serverSide.CurrentNodeInfo;
import jetbrains.buildServer.serverSide.DependencyConsumer;
import jetbrains.buildServer.serverSide.MultiNodeTasks;
import jetbrains.buildServer.serverSide.OrderedBuild;
import jetbrains.buildServer.serverSide.Parameter;
import jetbrains.buildServer.serverSide.PipelineView;
import jetbrains.buildServer.serverSide.QueuedBuildEx;
import jetbrains.buildServer.serverSide.ReadOnlyBuildTypeSettings;
import jetbrains.buildServer.serverSide.ReadOnlyUserParameters;
import jetbrains.buildServer.serverSide.RepositoryVersion;
import jetbrains.buildServer.serverSide.SBuild;
import jetbrains.buildServer.serverSide.SBuildAgent;
import jetbrains.buildServer.serverSide.SBuildFeatureDescriptor;
import jetbrains.buildServer.serverSide.SBuildType;
import jetbrains.buildServer.serverSide.SProject;
import jetbrains.buildServer.serverSide.SQLRunner;
import jetbrains.buildServer.serverSide.SQueuedBuild;
import jetbrains.buildServer.serverSide.SimpleParameter;
import jetbrains.buildServer.serverSide.TagData;
import jetbrains.buildServer.serverSide.TagDataUtil;
import jetbrains.buildServer.serverSide.TeamCityNode;
import jetbrains.buildServer.serverSide.TeamCityNodes;
import jetbrains.buildServer.serverSide.TeamCityProperties;
import jetbrains.buildServer.serverSide.artifacts.BuildArtifact;
import jetbrains.buildServer.serverSide.artifacts.BuildArtifacts;
import jetbrains.buildServer.serverSide.artifacts.BuildArtifactsViewMode;
import jetbrains.buildServer.serverSide.artifacts.SArtifactDependency;
import jetbrains.buildServer.serverSide.audit.ActionType;
import jetbrains.buildServer.serverSide.audit.AuditLog;
import jetbrains.buildServer.serverSide.audit.AuditLogAction;
import jetbrains.buildServer.serverSide.audit.AuditLogFilter;
import jetbrains.buildServer.serverSide.auth.AuthUtil;
import jetbrains.buildServer.serverSide.auth.AuthorityHolder;
import jetbrains.buildServer.serverSide.auth.Permission;
import jetbrains.buildServer.serverSide.buildDistribution.BuildPromotionInfo;
import jetbrains.buildServer.serverSide.buildLog.BuildLogEx;
import jetbrains.buildServer.serverSide.buildLog.MessageAttrs;
import jetbrains.buildServer.serverSide.comments.Comment;
import jetbrains.buildServer.serverSide.db.DBAction;
import jetbrains.buildServer.serverSide.db.DBException;
import jetbrains.buildServer.serverSide.db.DBFunctions;
import jetbrains.buildServer.serverSide.db.InParams;
import jetbrains.buildServer.serverSide.db.MySQL.MySqlIncorrectStringValueException;
import jetbrains.buildServer.serverSide.db.SQLRunnerEx;
import jetbrains.buildServer.serverSide.db.UnicodeString;
import jetbrains.buildServer.serverSide.db.queries.GenericQuery;
import jetbrains.buildServer.serverSide.dependency.BuildDependency;
import jetbrains.buildServer.serverSide.dependency.BuildDependencyEx;
import jetbrains.buildServer.serverSide.dependency.BuildDependencyGraph;
import jetbrains.buildServer.serverSide.dependency.Dependency;
import jetbrains.buildServer.serverSide.dependency.DependencyOptions;
import jetbrains.buildServer.serverSide.dependency.OrganizationProjectAccessViolationException;
import jetbrains.buildServer.serverSide.identifiers.BuildTypeIdentifiersManager;
import jetbrains.buildServer.serverSide.impl.AbstractBuildPromotion;
import jetbrains.buildServer.serverSide.impl.ArtifactsUtil;
import jetbrains.buildServer.serverSide.impl.BaseBuild;
import jetbrains.buildServer.serverSide.impl.BranchImpl;
import jetbrains.buildServer.serverSide.impl.BranchNameGenerator;
import jetbrains.buildServer.serverSide.impl.BuildChangesCalculator;
import jetbrains.buildServer.serverSide.impl.BuildPromotionDeletedException;
import jetbrains.buildServer.serverSide.impl.BuildPromotionImpl;
import jetbrains.buildServer.serverSide.impl.BuildPromotionListener;
import jetbrains.buildServer.serverSide.impl.BuildPromotionManagerEx;
import jetbrains.buildServer.serverSide.impl.BuildPromotionParameters;
import jetbrains.buildServer.serverSide.impl.BuildPromotionProblems;
import jetbrains.buildServer.serverSide.impl.BuildPromotionRevisionsQuery;
import jetbrains.buildServer.serverSide.impl.BuildRevisionData;
import jetbrains.buildServer.serverSide.impl.BuildTypeImpl;
import jetbrains.buildServer.serverSide.impl.CachingAuditLogActions;
import jetbrains.buildServer.serverSide.impl.DependencyCheckResult;
import jetbrains.buildServer.serverSide.impl.DependencyType;
import jetbrains.buildServer.serverSide.impl.DummyBuild;
import jetbrains.buildServer.serverSide.impl.EquivalenceCheckResult;
import jetbrains.buildServer.serverSide.impl.InsertBuildPromotionQuery;
import jetbrains.buildServer.serverSide.impl.InvalidOverridenRootsException;
import jetbrains.buildServer.serverSide.impl.LogUtil;
import jetbrains.buildServer.serverSide.impl.MaxRunningBuildsPerBranchProvider;
import jetbrains.buildServer.serverSide.impl.NoOpBuildLog;
import jetbrains.buildServer.serverSide.impl.PipelineViewImpl;
import jetbrains.buildServer.serverSide.impl.ProjectEx;
import jetbrains.buildServer.serverSide.impl.RemoteBuildType;
import jetbrains.buildServer.serverSide.impl.RemoteBuildTypeIdUtil;
import jetbrains.buildServer.serverSide.impl.VersionedSettingsChangesProvider;
import jetbrains.buildServer.serverSide.impl.artifacts.BuildArtifactsFactory;
import jetbrains.buildServer.serverSide.impl.audit.filters.ActionTypesFilter;
import jetbrains.buildServer.serverSide.impl.beans.BuildPromotionContext;
import jetbrains.buildServer.serverSide.impl.dependency.BuildPromotionDependencies;
import jetbrains.buildServer.serverSide.impl.history.AbstractBuildData;
import jetbrains.buildServer.serverSide.problems.BuildProblem;
import jetbrains.buildServer.users.SUser;
import jetbrains.buildServer.users.User;
import jetbrains.buildServer.util.CollectionsUtil;
import jetbrains.buildServer.util.ExceptionUtil;
import jetbrains.buildServer.util.FileUtil;
import jetbrains.buildServer.util.Option;
import jetbrains.buildServer.util.StringPool;
import jetbrains.buildServer.util.StringUtil;
import jetbrains.buildServer.util.filters.FilterUtil;
import jetbrains.buildServer.util.graph.BFSVisitor;
import jetbrains.buildServer.vcs.BranchSpec;
import jetbrains.buildServer.vcs.CheckoutRules;
import jetbrains.buildServer.vcs.RepositoryState;
import jetbrains.buildServer.vcs.SVcsModification;
import jetbrains.buildServer.vcs.SVcsRoot;
import jetbrains.buildServer.vcs.SVcsRootEx;
import jetbrains.buildServer.vcs.SelectPrevBuildPolicy;
import jetbrains.buildServer.vcs.VcsException;
import jetbrains.buildServer.vcs.VcsRoot;
import jetbrains.buildServer.vcs.VcsRootInstance;
import jetbrains.buildServer.vcs.VcsRootInstanceEntry;
import jetbrains.buildServer.vcs.VcsRootInstanceEx;
import jetbrains.buildServer.vcs.VcsRootVcsException;
import jetbrains.buildServer.vcs.VcsSupportCore;
import jetbrains.buildServer.vcs.VcsUtil;
import jetbrains.buildServer.vcs.impl.DeletedVcsRoot;
import jetbrains.buildServer.vcs.impl.SVcsRootImpl;
import jetbrains.buildServer.vcs.impl.VcsRootInstanceWithParent;
import jetbrains.buildServer.version.ServerVersionHolder;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Exception performing whole class analysis ignored.
 */
public class BuildPromotionImpl
extends AbstractBuildPromotion {
    public static final String SETTINGS_CHANGES_DETACH_UNRELATED_PROP = "teamcity.versionedSettings.changes.detachUnrelated";
    public static final String SETTINGS_CHANGES_DETACH_UNRELATED_USE_LAST_BUILD_IN_ANY_BRANCH_PROP = "teamcity.versionedSettings.changes.detachUnrelated.defaultBranchDisabled.useLastBuildInAnyBranch";
    public static final String IS_EQUIVALENT_CHECKS_REVISIONS = "teamcity.promotion.equivalentCheck.compareRevisionsForDagBasedRoots";
    public static final String EXCLUDE_DEFAULT_BRANCH_CHANGES_PARAM = "teamcity.build.defaultBranchChangesMode";
    public static final String RESET_BRANCH_TO_DEFAULT_IF_POSSIBLE = "teamcity.internal.promotion.branch.resetToDefaultIfPossible";
    public static final String GENERATE_AUTO_BRANCH_IF_BRANCH_SPEC_EXISTS = "teamcity.internal.promotion.autoBranchName.generateEvenIfBranchSpecDefined";
    public static final String ALLOW_RESETTING_ACTIVE_REVISIONS = "teamcity.internal.build.resetRevisions.allow";
    public static final String ALWAYS_ACCEPT_CUSTOM_BRANCH = "teamcity.build.branch.alwaysAccept";
    @NotNull
    private static final Logger LOG;
    private static final int PARAMS_DIRTY = 1;
    private static final int VCS_SETTINGS_DIRTY = 2;
    static final int DEPENDENCIES_DIRTY = 4;
    private static final int CUSTOM_ART_DEPS_DIRTY = 8;
    private static final int ATTRS_DIRTY = 16;
    private static final int DATES_DIRTY = 32;
    private static final int FINAL_ART_DEPS_DIRTY = 64;
    private static final int NEW_PROMOTION = -268435456;
    static final int ALL_DIRTY = -1;
    private static final int CHECKOUT_RULES_LENGTH_LIMIT = 16000;
    public static final int MAX_ATTR_VALUE_LENGTH = 1000;
    public static final ActionType[] ACTION_TYPES;
    public static final String INVALID_BRANCH_NAME_PROBLEM_TYPE = "invalid_branch_name";
    public static final String INACCESSIBLE_EXTERNAL_DEPENDENCY_IDENTITY_PREFIX = "inaccessible_external_dependency_";
    public static final String PROMOTION_COMPOSITE_BUILD_ARTIFACTS_CACHE_TTL_MILLIS_PROP = "teamcity.buildPromotion.compositeBuild.artifactsCache.ttlMillis";
    public static final String ARTIFACTS_PATH_VALIDATION_ENABLED = "teamcity.buildPromotion.artifactsPathValidation.enabled";
    private int myDirtyState;
    private final long myId;
    private final String myBuildTypeId;
    private final boolean myPersonal;
    private final Long myPersonalChangeId;
    private final Long myOwnerId;
    private volatile Long myModificationId;
    private volatile Long myChainModificationId;
    private Boolean myIncomplete;
    private volatile boolean myChangesDetached;
    private volatile boolean myCreatedInDb;
    private final BuildPromotionDependencies myDependencies;
    private final BuildPromotionParameters myUserParameters;
    private Map<VcsRootInstance, VcsRootInstance> myOverridenVcsRoots;
    private final Object myAssociatedBuildIdLock;
    private volatile Long myAssociatedBuildId;
    private volatile Date myRemoveFromQueueTime;
    private volatile Date myQueuedTime;
    private volatile boolean myAttributesRead;
    private volatile boolean myAssociatedBuildFinished;
    private final Lock myOptimizingDependenciesLock;
    private volatile boolean myDeleted;
    private List<SArtifactDependency> myFinalArtifactDependencies;
    private List<SArtifactDependency> myCustomArtifactDependencies;
    private final BuildLogRef myBuildLogRef;
    private final AtomicReference<List<String>> myProjectPathIds;
    private final AtomicReference<List<CachedBuildArtifacts>> myCachedArtifacts;
    private final CachingAuditLogActions myAuditLogActions;
    private volatile List<BuildRevisionEx> myRevisions;
    private final AtomicReference<List<VcsRootInstanceEntry>> myComputedVcsRootEntries;
    private final PipelineView myPipelineView;
    @NotNull
    private final AtomicReference<THashMap<String, Object>> myAttributes;
    @NotNull
    @GuardedBy(value="this")
    private final Set<String> myNotPersistedAttributes;
    private final AtomicReference<Boolean> myOutOfChangesSequence;
    private volatile String myFinalBranchName;
    private final BuildPromotionProblems myProblems;
    private final AtomicStampedReference<BuildSettings> myBuildSettings;
    private final Object myTagsLock;
    @NotNull
    private SoftReference<Collection<TagData>> myTagDatasRef;
    private static final SoftReference<Collection<TagData>> EMPTY_TAGS;
    private final AtomicReference<MultiNodeTasks.SubmittedTask> myCheckingForChangesTask;
    private volatile int myExpirationCounter;
    private MaxRunningBuildsPerBranchProvider myMaxRunningBuildsPerBranchProvider;
    private final ReadOnlyUserParameters myBuildParametersHolder;
    private static final Set<String> NON_COPYABLE_ATTRIBUTES;
    static final int LAST_COMPLETE_SEARCH_DEPTH;
    private final AtomicReference<ArtifactsStateInfoImpl> myArtifactsStateRef;
    private static final GenericQuery SET_CHANGES_DETACHED_QUERY;
    static final GenericQuery SET_BUILD_ID_QUERY;
    private static final GenericQuery UPDATE_DATES_QUERY;
    private static final GenericQuery SAVE_ROOT_REVISION_QUERY;
    private static final GenericQuery<Map<Long, Long>> GET_OVERRIDEN_ROOTS;
    private static final String INSERT_ATTR_VALUE_QUERY = "insert into build_attrs (build_state_id, attr_name, attr_value, attr_num_value) values (?, ?, ?, ?)";
    static final GenericQuery DELETE_ATTRS_QUERY;
    static final /* synthetic */ boolean $assertionsDisabled;

    public BuildPromotionImpl(@NotNull SBuildType sBuildType, @NotNull BuildPromotionContext buildPromotionContext) {
        block11: {
            block12: {
                if (sBuildType == null) {
                    BuildPromotionImpl.$$$reportNull$$$0(0);
                }
                if (buildPromotionContext == null) {
                    BuildPromotionImpl.$$$reportNull$$$0(1);
                }
                super(buildPromotionContext);
                this.myDirtyState = 0;
                this.myIncomplete = null;
                this.myOverridenVcsRoots = null;
                this.myAssociatedBuildIdLock = new Object();
                this.myAssociatedBuildId = null;
                this.myAttributesRead = false;
                this.myAssociatedBuildFinished = false;
                this.myOptimizingDependenciesLock = new ReentrantLock();
                this.myDeleted = false;
                this.myBuildLogRef = new BuildLogRef();
                this.myProjectPathIds = new AtomicReference();
                this.myCachedArtifacts = new AtomicReference();
                this.myComputedVcsRootEntries = new AtomicReference<Object>(null);
                this.myAttributes = new AtomicReference();
                this.myNotPersistedAttributes = new HashSet<String>();
                this.myOutOfChangesSequence = new AtomicReference<Object>(null);
                this.myBuildSettings = new AtomicStampedReference<Object>(null, 0);
                this.myTagsLock = new Object();
                this.myTagDatasRef = new SoftReference<Object>(null);
                this.myCheckingForChangesTask = new AtomicReference();
                this.myBuildParametersHolder = new /* Unavailable Anonymous Inner Class!! */;
                this.myArtifactsStateRef = new AtomicReference();
                this.myId = buildPromotionContext.getIdSequences().nextOne("promotion_id").longValue();
                this.myPersonal = sBuildType.isPersonal();
                if (this.myPersonal) {
                    RemoteBuildType remoteBuildType = (RemoteBuildType)sBuildType;
                    this.myPersonalChangeId = remoteBuildType.getChangesId();
                    this.myOwnerId = remoteBuildType.getUserId();
                    this.myBuildTypeId = RemoteBuildTypeIdUtil.getParentBuildTypeId((String)remoteBuildType.getBuildTypeId());
                } else {
                    this.myPersonalChangeId = null;
                    this.myOwnerId = null;
                    this.myBuildTypeId = sBuildType.getBuildTypeId();
                }
                try {
                    try {
                        this.myDependencies = new BuildPromotionDependencies((BuildPromotionEx)this, buildPromotionContext, true, null);
                        this.myProblems = new BuildPromotionProblems((BuildPromotionEx)this, buildPromotionContext, true);
                        this.myOverridenVcsRoots = Collections.emptyMap();
                        this.myChangesDetached = this.myPersonal;
                        this.myAttributes.set((THashMap<String, Object>)new THashMap());
                        this.initArtifactsDirectoryAttr();
                        this.myUserParameters = new BuildPromotionParameters(this.myId, buildPromotionContext, this.myBuildParametersHolder);
                        this.myUserParameters.initCustomParameters(Collections.emptyMap());
                        if (!((BuildTypeEx)sBuildType).isAgentLessBuildType()) break block11;
                        if (!sBuildType.isCompositeBuildType()) break block12;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildPromotionImpl.a(illegalStateException);
                    }
                    this.setAttribute("teamcity.build.composite", true);
                    break block11;
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
            }
            this.setAttribute("teamcity.build.agentLess", true);
        }
        try {
            this.setAttribute("teamcity.build.creatorNodeId", CurrentNodeInfo.getNodeId());
            this.setAttribute("teamcity.build.serverBuildNumber", ServerVersionHolder.getVersion().getBuildNumber());
            this.setAttribute("teamcity.build.freezeSettings.forcePostprocessing", "true");
            if (((BuildTypeEx)sBuildType).isPipelineHead()) {
                PipelineViewImpl.markPipelineHead((BuildPromotionEx)this);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        this.myCustomArtifactDependencies = Collections.emptyList();
        this.myFinalArtifactDependencies = Collections.emptyList();
        this.myRevisions = Collections.emptyList();
        this.myTagDatasRef = EMPTY_TAGS;
        this.myAuditLogActions = this.createCachingAuditLogActions(true);
        this.myIncomplete = false;
        this.myPipelineView = new PipelineViewImpl((BuildPromotionEx)this, this.getContext().getPipelineYamlParser());
        this.markDirty(-268435456);
    }

    @NotNull
    private CachingAuditLogActions createCachingAuditLogActions(boolean bl) {
        return new /* Unavailable Anonymous Inner Class!! */;
    }

    /*
     * Unable to fully structure code
     */
    public BuildPromotionImpl(long var1_1, @NotNull String var3_2, @Nullable Long var4_3, @Nullable Long var5_4, boolean var6_5, @Nullable Long var7_6, @Nullable Long var8_7, boolean var9_8, boolean var10_9, boolean var11_10, @Nullable String var12_11, @Nullable Long var13_12, @Nullable Date var14_13, @Nullable Date var15_14, @NotNull BuildPromotionContext var16_15, @NotNull Map<String, Object> var17_16, @Nullable Map<String, String> var18_17, @Nullable Map<Long, Integer> var19_18, @Nullable List<BuildRevisionData> var20_19, boolean var21_20) {
        block18: {
            block17: {
                if (var3_2 == null) {
                    BuildPromotionImpl.$$$reportNull$$$0(2);
                }
                if (var16_15 == null) {
                    BuildPromotionImpl.$$$reportNull$$$0(3);
                }
                if (var17_16 == null) {
                    BuildPromotionImpl.$$$reportNull$$$0(4);
                }
                super(var16_15);
                this.myDirtyState = 0;
                this.myIncomplete = null;
                this.myOverridenVcsRoots = null;
                this.myAssociatedBuildIdLock = new Object();
                this.myAssociatedBuildId = null;
                this.myAttributesRead = false;
                this.myAssociatedBuildFinished = false;
                this.myOptimizingDependenciesLock = new ReentrantLock();
                this.myDeleted = false;
                this.myBuildLogRef = new BuildLogRef();
                this.myProjectPathIds = new AtomicReference<V>();
                this.myCachedArtifacts = new AtomicReference<V>();
                this.myComputedVcsRootEntries = new AtomicReference<Object>(null);
                this.myAttributes = new AtomicReference<V>();
                this.myNotPersistedAttributes = new HashSet<String>();
                this.myOutOfChangesSequence = new AtomicReference<Object>(null);
                this.myBuildSettings = new AtomicStampedReference<Object>(null, 0);
                this.myTagsLock = new Object();
                this.myTagDatasRef = new SoftReference<Object>(null);
                this.myCheckingForChangesTask = new AtomicReference<V>();
                this.myBuildParametersHolder = new /* Unavailable Anonymous Inner Class!! */;
                this.myArtifactsStateRef = new AtomicReference<V>();
                this.myId = var1_1;
                this.myBuildTypeId = var3_2;
                this.myPersonal = var6_5;
                this.myOwnerId = var8_7;
                this.myPersonalChangeId = var7_6;
                this.myChangesDetached = var9_8;
                this.myModificationId = var4_3;
                this.myChainModificationId = var5_4;
                this.myAssociatedBuildId = var13_12;
                this.myDirtyState = 0;
                this.myQueuedTime = var14_13;
                this.myRemoveFromQueueTime = var15_14;
                this.myDependencies = new BuildPromotionDependencies((BuildPromotionEx)this, var16_15, false, var19_18);
                this.myProblems = new BuildPromotionProblems((BuildPromotionEx)this, var16_15, false);
                this.myDeleted = var10_9;
                this.myIncomplete = var11_10;
                if (var12_11 != null) ** GOTO lbl49
                try {
                    if (var4_3 == null) break block17;
lbl49:
                    // 2 sources

                    this.myFinalBranchName = StringUtil.emptyIfNull((String)var12_11);
                }
                catch (IllegalStateException v0) {
                    throw BuildPromotionImpl.a(v0);
                }
            }
            var22_21 = new THashMap();
            for (Map.Entry<String, Object> var24_23 : var17_16.entrySet()) {
                var22_21.put((Object)StringPool.getFromPool((String)var24_23.getKey()), BuildPromotionImpl.pooledAttrValue(var24_23.getValue()));
            }
            try {
                try {
                    this.myAttributes.set((THashMap<String, Object>)var22_21);
                    if (!this.needToFreezeSettings() || this.myRemoveFromQueueTime != null) break block18;
                }
                catch (IllegalStateException v1) {
                    throw BuildPromotionImpl.a(v1);
                }
                this.setAttribute("teamcity.build.freezeSettings.forcePostprocessing", "true");
            }
            catch (IllegalStateException v2) {
                throw BuildPromotionImpl.a(v2);
            }
        }
        try {
            this.myUserParameters = new BuildPromotionParameters(this.myId, var16_15, this.myBuildParametersHolder);
            if (var18_17 != null) {
                this.myUserParameters.initCustomParameters(var18_17);
            }
        }
        catch (IllegalStateException v3) {
            throw BuildPromotionImpl.a(v3);
        }
        try {
            this.myAuditLogActions = this.createCachingAuditLogActions(false);
            if (var20_19 != null) {
                this.initRevisionsFromTuples(var20_19);
            }
        }
        catch (IllegalStateException v4) {
            throw BuildPromotionImpl.a(v4);
        }
        this.myAssociatedBuildFinished = var21_20;
        this.myCreatedInDb = true;
        this.myPipelineView = new PipelineViewImpl((BuildPromotionEx)this, this.getContext().getPipelineYamlParser());
    }

    @Nullable
    @Contract(value="null->null;!null->!null")
    private static Object pooledAttrValue(@Nullable Object object) {
        block4: {
            String string;
            block6: {
                block5: {
                    try {
                        try {
                            if (!(object instanceof String)) break block4;
                            if (!object.equals("")) break block5;
                        }
                        catch (IllegalStateException illegalStateException) {
                            throw BuildPromotionImpl.a(illegalStateException);
                        }
                        string = "";
                        break block6;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildPromotionImpl.a(illegalStateException);
                    }
                }
                string = StringPool.getFromPool((String)((String)object));
            }
            object = string;
        }
        return object;
    }

    public long getId() {
        return this.myId;
    }

    public Collection<BuildPromotionInfo> getDirectDependencies() {
        ArrayList<BuildPromotionInfo> arrayList = new ArrayList<BuildPromotionInfo>();
        for (BuildDependencyEx buildDependencyEx : this.getDependencies()) {
            arrayList.add((BuildPromotionInfo)buildDependencyEx.getDependOn());
        }
        return arrayList;
    }

    public boolean isPersonal() {
        return this.myPersonal;
    }

    public Long getPersonalChangeId() {
        return this.myPersonalChangeId;
    }

    public boolean hasCustomPatch() {
        boolean bl;
        block7: {
            block6: {
                try {
                    try {
                        try {
                            if (this.myPersonalChangeId == null || "false".equals(this.getAttribute("teamcity.build.personalPatchApplicable"))) break block6;
                        }
                        catch (IllegalStateException illegalStateException) {
                            throw BuildPromotionImpl.a(illegalStateException);
                        }
                        if (this.getContext().getPersonalBuildManager().isEmptyPersonalPatch(this.myPersonalChangeId.longValue())) break block6;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildPromotionImpl.a(illegalStateException);
                    }
                    bl = true;
                    break block7;
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
            }
            bl = false;
        }
        return bl;
    }

    @NotNull
    public Collection<Parameter> getCustomParametersCollection() {
        Collection collection;
        try {
            collection = this.myUserParameters.getCustomParametersCollection();
            if (collection == null) {
                BuildPromotionImpl.$$$reportNull$$$0(5);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return collection;
    }

    public void setBuildRevisions(@NotNull Collection<BuildRevisionEx> collection, long l, long l2) {
        boolean bl;
        long l3;
        long l4;
        Collection<BuildRevisionEx> collection2;
        BuildPromotionImpl buildPromotionImpl;
        try {
            if (collection == null) {
                BuildPromotionImpl.$$$reportNull$$$0(6);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        try {
            buildPromotionImpl = this;
            collection2 = collection;
            l4 = l;
            l3 = l2;
            bl = !this.getPipelineView().isJob();
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        buildPromotionImpl.setBuildRevisions(collection2, l4, l3, bl);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Unable to fully structure code
     */
    public void setBuildRevisions(@NotNull Collection<BuildRevisionEx> var1_1, long var2_2, long var4_3, boolean var6_4) {
        block47: {
            try {
                if (var1_1 == null) {
                    BuildPromotionImpl.$$$reportNull$$$0(7);
                }
            }
            catch (IllegalStateException v0) {
                throw BuildPromotionImpl.a(v0);
            }
            this.checkHasPermissionForProject(Permission.CUSTOMIZE_BUILD_REVISIONS);
            if (this.getRevisions().isEmpty()) break block47;
            try {
                block51: {
                    if (this.getLastModificationId() == null) break block47;
                    break block51;
                    catch (IllegalStateException v1) {
                        throw BuildPromotionImpl.a(v1);
                    }
                }
                throw new IllegalStateException("Build revisions in " + String.valueOf((Object)this) + " have been set already to: " + String.valueOf(this.getRevisions()) + "\nNew value: " + String.valueOf(var1_1));
            }
            catch (IllegalStateException v2) {
                throw BuildPromotionImpl.a(v2);
            }
        }
        try {
            if (var4_3 < var2_2) {
                throw new IllegalArgumentException("Chain modification id " + var4_3 + " is smaller than max modification id " + var2_2 + ", promotion: " + String.valueOf((Object)this));
            }
        }
        catch (IllegalStateException v3) {
            throw BuildPromotionImpl.a(v3);
        }
        var7_5 = null;
        var8_6 = this.getOverridenVcsRoots();
        var9_7 = this;
        synchronized (var9_7) {
            block50: {
                block59: {
                    block58: {
                        block57: {
                            block56: {
                                block55: {
                                    block54: {
                                        block49: {
                                            block48: {
                                                if (!this.isChangeCollectingNeeded()) {
                                                    return;
                                                }
                                                if (this.myOverridenVcsRoots == null) ** GOTO lbl40
                                                try {
                                                    block52: {
                                                        if (!this.myOverridenVcsRoots.isEmpty()) break block48;
                                                        break block52;
                                                        catch (IllegalStateException v4) {
                                                            throw BuildPromotionImpl.a(v4);
                                                        }
                                                    }
                                                    this.myOverridenVcsRoots = var8_6;
                                                }
                                                catch (IllegalStateException v5) {
                                                    throw BuildPromotionImpl.a(v5);
                                                }
                                            }
                                            var10_8 = this.checkVcsBranchNamesAndSetMissingModIds(var1_1);
                                            var11_9 = this.getAutogeneratedBranchName();
                                            var12_10 = this.getBuildType();
                                            if (var12_10 == null) break block49;
                                            try {
                                                block53: {
                                                    if (var11_9 != null) break block49;
                                                    break block53;
                                                    catch (IllegalStateException v6) {
                                                        throw BuildPromotionImpl.a(v6);
                                                    }
                                                }
                                                if (!var6_4) break block49;
                                            }
                                            catch (IllegalStateException v7) {
                                                throw BuildPromotionImpl.a(v7);
                                            }
                                            var7_5 = this.checkDesiredBranch(var12_10, var10_8);
                                        }
                                        if (var12_10 == null) break block50;
                                        if (var11_9 != null) break block50;
                                        break block54;
                                        catch (IllegalStateException v8) {
                                            throw BuildPromotionImpl.a(v8);
                                        }
                                    }
                                    if (var7_5 != null) break block50;
                                    break block55;
                                    catch (IllegalStateException v9) {
                                        throw BuildPromotionImpl.a(v9);
                                    }
                                }
                                if (!this.allDependenciesAreInDefaultBranch()) break block50;
                                break block56;
                                catch (IllegalStateException v10) {
                                    throw BuildPromotionImpl.a(v10);
                                }
                            }
                            if (var10_8.isEmpty()) break block50;
                            break block57;
                            catch (IllegalStateException v11) {
                                throw BuildPromotionImpl.a(v11);
                            }
                        }
                        if (StringUtil.isEmpty((String)StringUtil.objectToString((Object)this.getAttribute("teamcity.build.desiredBranchName")))) break block50;
                        break block58;
                        catch (IllegalStateException v12) {
                            throw BuildPromotionImpl.a(v12);
                        }
                    }
                    if (this.getDependedOnMe().isEmpty()) break block50;
                    break block59;
                    catch (IllegalStateException v13) {
                        throw BuildPromotionImpl.a(v13);
                    }
                }
                try {
                    block60: {
                        if (var12_10.isDefaultBranchExcluded()) break block50;
                        break block60;
                        catch (IllegalStateException v14) {
                            throw BuildPromotionImpl.a(v14);
                        }
                    }
                    if (!this.getBooleanInternalParameter("teamcity.internal.promotion.branch.resetToDefaultIfPossible")) break block50;
                }
                catch (IllegalStateException v15) {
                    throw BuildPromotionImpl.a(v15);
                }
                var10_8 = this.changeBranchToDefaultIfAllRevisionsAreInDefaultBranches(var12_10, var10_8);
            }
            try {
                this.myRevisions = var10_8;
                this.myRevisions.sort(this.getComparatorAsVcsRootEntries());
                this.finalizeBranchName(var11_9);
                if (var12_10 != null) {
                    this.resolveDefaultBranchDisplayName(var12_10, var10_8);
                }
            }
            catch (IllegalStateException v16) {
                throw BuildPromotionImpl.a(v16);
            }
        }
        this.rememberServerCheckoutProperties();
        var9_7 = this;
        synchronized (var9_7) {
            this.markDirty(2);
            this.myChainModificationId = var4_3;
            this.myModificationId = var2_2;
        }
        try {
            this.resetAllCompatibilityCaches();
            if (var7_5 != null) {
                this.addBuildProblem(var7_5);
            }
        }
        catch (IllegalStateException v17) {
            throw BuildPromotionImpl.a(v17);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     */
    public void resetBuildRevisions() {
        BuildPromotionImpl buildPromotionImpl = this;
        synchronized (buildPromotionImpl) {
            block9: {
                block10: {
                    if (this.isChangeCollectingNeeded()) {
                        return;
                    }
                    if (this.getAssociatedBuildId() == null) break block9;
                    try {
                        block11: {
                            if (!this.getBooleanInternalParameter("teamcity.internal.build.resetRevisions.allow")) break block10;
                            break block11;
                            catch (IllegalStateException illegalStateException) {
                                throw BuildPromotionImpl.a(illegalStateException);
                            }
                        }
                        if (!this.isFinished()) break block9;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildPromotionImpl.a(illegalStateException);
                    }
                }
                LOG.warnAndDebugDetails("Ignore attempt to reset revisions of a started build: " + LogUtil.describe((BuildPromotion)this), (Throwable)new Exception());
                return;
            }
            this.removeVcsRootsRelatedDataFromDB();
            for (String string : Arrays.asList("teamcity.collectChanges.error", "teamcity.checkout.properties.hash", "teamcity.build.finalBranchName", "teamcity.build.autoBranchName")) {
                this.setAttribute(string, null);
            }
            this.myModificationId = null;
            this.myChainModificationId = null;
            this.myComputedVcsRootEntries.set(null);
            this.myOverridenVcsRoots = null;
            this.myRevisions = null;
            this.myOutOfChangesSequence.set(null);
        }
        this.resetChangesCache();
        this.resetAllCompatibilityCaches();
        this.persist();
    }

    private void removeVcsRootsRelatedDataFromDB() {
        this.getContext().getSQLRunner().runAndRetryNoResult(dBFunctions -> {
            dBFunctions.executeDml("delete from build_checkout_rules where build_state_id = ?", new Object[]{this.myId});
            dBFunctions.executeDml("delete from build_revisions where build_state_id = ?", new Object[]{this.myId});
            dBFunctions.executeDml("delete from build_overriden_roots where build_state_id = ?", new Object[]{this.myId});
            dBFunctions.executeDml("update build_state set modification_id = null, chain_modification_id = null where id = ?", new Object[]{this.myId});
        });
    }

    private void rememberServerCheckoutProperties() {
        this.setAttribute("teamcity.checkout.properties.hash", this.getDefaultCheckoutDirectory(BuildTypeDescriptor.CheckoutType.ON_SERVER));
    }

    @NotNull
    private Comparator<BuildRevision> getComparatorAsVcsRootEntries() {
        Comparator comparator;
        List<Object> list;
        BuildTypeEx buildTypeEx = this.getBuildType();
        try {
            list = buildTypeEx != null ? buildTypeEx.getVcsRoots().stream().map(VcsRoot::getId).toList() : Collections.emptyList();
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        List list2 = list;
        try {
            comparator = (buildRevision, buildRevision2) -> {
                long l = buildRevision.getRoot().getParentId();
                long l2 = buildRevision2.getRoot().getParentId();
                int n = list2.indexOf(l);
                int n2 = list2.indexOf(l2);
                try {
                    if (n != n2) {
                        return n - n2;
                    }
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
                return (int)(buildRevision.getRoot().getId() - buildRevision2.getRoot().getId());
            };
            if (comparator == null) {
                BuildPromotionImpl.$$$reportNull$$$0(8);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return comparator;
    }

    @Nullable
    private BuildProblemData checkDesiredBranch(@NotNull BuildTypeEx buildTypeEx, @NotNull Collection<BuildRevisionEx> collection) {
        block33: {
            Object object;
            Object object2;
            String string;
            Object object32;
            String string2;
            List<BuildRevisionEx> list;
            block34: {
                block32: {
                    try {
                        if (buildTypeEx == null) {
                            BuildPromotionImpl.$$$reportNull$$$0(9);
                        }
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildPromotionImpl.a(illegalStateException);
                    }
                    try {
                        if (collection == null) {
                            BuildPromotionImpl.$$$reportNull$$$0(10);
                        }
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildPromotionImpl.a(illegalStateException);
                    }
                    list = collection.stream().filter(buildRevisionEx -> {
                        boolean bl;
                        block5: {
                            block4: {
                                try {
                                    try {
                                        if (buildRevisionEx.getType() != BuildRevisionVcsRootType.REGULAR && buildRevisionEx.getType() != BuildRevisionVcsRootType.PROJECT_SETTINGS) break block4;
                                    }
                                    catch (IllegalStateException illegalStateException) {
                                        throw BuildPromotionImpl.a(illegalStateException);
                                    }
                                    bl = true;
                                    break block5;
                                }
                                catch (IllegalStateException illegalStateException) {
                                    throw BuildPromotionImpl.a(illegalStateException);
                                }
                            }
                            bl = false;
                        }
                        return bl;
                    }).filter(buildRevisionEx -> buildRevisionEx.getRoot().isDagBased()).toList();
                    try {
                        try {
                            if (list.size() != 1 || !((BuildRevisionEx)list.iterator().next()).isSettingsRevision()) break block32;
                        }
                        catch (IllegalStateException illegalStateException) {
                            throw BuildPromotionImpl.a(illegalStateException);
                        }
                        return null;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildPromotionImpl.a(illegalStateException);
                    }
                }
                string2 = StringUtil.objectToString((Object)this.getAttribute("teamcity.build.desiredBranchName"));
                try {
                    try {
                        if (StringUtil.isEmpty((String)string2)) break block33;
                        if (this.allDependenciesAreInDefaultBranch()) break block34;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildPromotionImpl.a(illegalStateException);
                    }
                    return null;
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
            }
            for (Object object32 : list) {
                string = object32.getRepositoryVersion().getVcsBranch();
                if (string == null) continue;
                try {
                    if ("<unspecified>".equals(string)) {
                        continue;
                    }
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
                object2 = object32.getRoot();
                if (!(object2 instanceof VcsRootInstanceEx)) continue;
                object = (VcsRootInstanceEx)object2;
                String string3 = object.getDefaultBranchName();
                try {
                    if (string3 == null) {
                        continue;
                    }
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
                try {
                    if (Objects.equals(string3, string)) {
                        continue;
                    }
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
                return null;
            }
            try {
                if (!this.getDependedOnMe().isEmpty()) {
                    this.resetBranchToDefault(buildTypeEx, "the branch \"" + string2 + "\" is not applicable to the build VCS roots");
                    return null;
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
            HashSet hashSet = new HashSet();
            try {
                list.forEach(buildRevisionEx -> {
                    block4: {
                        String string = buildRevisionEx.getRepositoryVersion().getVcsBranch();
                        try {
                            try {
                                if (string == null || "<unspecified>".equals(string)) break block4;
                            }
                            catch (IllegalStateException illegalStateException) {
                                throw BuildPromotionImpl.a(illegalStateException);
                            }
                            hashSet.add(string);
                        }
                        catch (IllegalStateException illegalStateException) {
                            throw BuildPromotionImpl.a(illegalStateException);
                        }
                    }
                });
                if (hashSet.isEmpty()) {
                    return null;
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
            object32 = "The build was triggered in the branch " + string2 + " which does not correspond to any branch monitored by the build VCS roots (the branch is either closed or excluded / not matched by any branch specification). Because of that default branch revisions were set to this build.";
            string = null;
            if (this.shouldRaiseProblemIfBranchIsInvalid()) {
                object2 = "invalid_branch_" + DigestUtils.shaHex((String)string2);
                string = BuildProblemData.createBuildProblem((String)object2, (String)"invalid_branch_name", (String)object32);
            } else {
                this.getBuildLog().messageAsync((String)object32, Status.WARNING, MessageAttrs.serverMessage());
            }
            object2 = buildTypeEx.getLastUsedBranchSpecMap();
            object = new StringBuilder();
            object2.forEach((arg_0, arg_1) -> BuildPromotionImpl.lambda$checkDesiredBranch$5((StringBuilder)object, arg_0, arg_1));
            Loggers.VCS.warn("The build promotion branch does not correspond to its revisions (the branch is either closed or excluded by branch specifications): " + LogUtil.describeInDetail((BuildPromotion)this) + ", last used branch specs: " + String.valueOf(object));
            return string;
        }
        return null;
    }

    private boolean allDependenciesAreInDefaultBranch() {
        for (BuildDependencyEx buildDependencyEx : this.getDependencies()) {
            Branch branch = buildDependencyEx.getDependOn().getBranch();
            if (branch == null) continue;
            try {
                if (branch.isDefaultBranch()) {
                    continue;
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
            return false;
        }
        return true;
    }

    private void resetBranchToDefault(@NotNull BuildTypeEx buildTypeEx, @NotNull String string) {
        block9: {
            block8: {
                try {
                    if (buildTypeEx == null) {
                        BuildPromotionImpl.$$$reportNull$$$0(11);
                    }
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
                try {
                    if (string == null) {
                        BuildPromotionImpl.$$$reportNull$$$0(12);
                    }
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
                try {
                    if (!buildTypeEx.isBranchSpecDefined(false)) break block8;
                    this.setAttribute("teamcity.build.desiredBranchName", "");
                    break block9;
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
            }
            this.setAttribute("teamcity.build.desiredBranchName", null);
        }
        this.getBuildLog().messageAsync("The build branch is set to <default>, reason: " + string, Status.NORMAL, MessageAttrs.serverMessage().internalize());
    }

    @NotNull
    private List<BuildRevisionEx> changeBranchToDefaultIfAllRevisionsAreInDefaultBranches(@NotNull BuildTypeEx buildTypeEx, @NotNull List<BuildRevisionEx> list) {
        Object object;
        RepositoryState repositoryState;
        String string;
        try {
            if (buildTypeEx == null) {
                BuildPromotionImpl.$$$reportNull$$$0(13);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        try {
            if (list == null) {
                BuildPromotionImpl.$$$reportNull$$$0(14);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        HashMap<BuildRevisionEx, String> hashMap = new HashMap<BuildRevisionEx, String>();
        for (BuildRevisionEx object2 : list) {
            block50: {
                String string2;
                VcsRootInstanceEx vcsRootInstanceEx;
                block44: {
                    List<BuildRevisionEx> list2;
                    block45: {
                        block42: {
                            List<BuildRevisionEx> list3;
                            block43: {
                                block40: {
                                    List<BuildRevisionEx> list4;
                                    block41: {
                                        vcsRootInstanceEx = (VcsRootInstanceEx)object2.getRoot();
                                        try {
                                            try {
                                                if (vcsRootInstanceEx.isDagBased()) break block40;
                                                list4 = list;
                                                if (list4 != null) break block41;
                                            }
                                            catch (IllegalStateException illegalStateException) {
                                                throw BuildPromotionImpl.a(illegalStateException);
                                            }
                                            BuildPromotionImpl.$$$reportNull$$$0(15);
                                        }
                                        catch (IllegalStateException illegalStateException) {
                                            throw BuildPromotionImpl.a(illegalStateException);
                                        }
                                    }
                                    return list4;
                                }
                                string = object2.getRepositoryVersion().getVcsBranch();
                                try {
                                    try {
                                        if (string != null) break block42;
                                        list3 = list;
                                        if (list3 != null) break block43;
                                    }
                                    catch (IllegalStateException illegalStateException) {
                                        throw BuildPromotionImpl.a(illegalStateException);
                                    }
                                    BuildPromotionImpl.$$$reportNull$$$0(16);
                                }
                                catch (IllegalStateException illegalStateException) {
                                    throw BuildPromotionImpl.a(illegalStateException);
                                }
                            }
                            return list3;
                        }
                        repositoryState = vcsRootInstanceEx.getLastUsedState();
                        try {
                            if (repositoryState.getDefaultBranchName().equals(string)) {
                                continue;
                            }
                        }
                        catch (IllegalStateException illegalStateException) {
                            throw BuildPromotionImpl.a(illegalStateException);
                        }
                        string2 = repositoryState.getDefaultBranchRevision();
                        try {
                            try {
                                if (string2 != null) break block44;
                                list2 = list;
                                if (list2 != null) break block45;
                            }
                            catch (IllegalStateException illegalStateException) {
                                throw BuildPromotionImpl.a(illegalStateException);
                            }
                            BuildPromotionImpl.$$$reportNull$$$0(17);
                        }
                        catch (IllegalStateException illegalStateException) {
                            throw BuildPromotionImpl.a(illegalStateException);
                        }
                    }
                    return list2;
                }
                if (!object2.getRevision().equals(string2)) {
                    List<BuildRevisionEx> list5;
                    block51: {
                        SVcsModification sVcsModification2;
                        long l;
                        Set set;
                        block48: {
                            List<BuildRevisionEx> list6;
                            block49: {
                                block46: {
                                    List<BuildRevisionEx> list7;
                                    block47: {
                                        List list8 = this.getContext().getVcsHistory().findModificationsByVersion(object2.getRevision());
                                        set = list8.stream().filter(sVcsModification -> {
                                            boolean bl;
                                            try {
                                                bl = sVcsModification.getVcsRoot().getParentId() == vcsRootInstanceEx.getParentId();
                                            }
                                            catch (IllegalStateException illegalStateException) {
                                                throw BuildPromotionImpl.a(illegalStateException);
                                            }
                                            return bl;
                                        }).map(sVcsModification -> sVcsModification.getId()).collect(Collectors.toSet());
                                        try {
                                            try {
                                                if (!set.isEmpty()) break block46;
                                                list7 = list;
                                                if (list7 != null) break block47;
                                            }
                                            catch (IllegalStateException illegalStateException) {
                                                throw BuildPromotionImpl.a(illegalStateException);
                                            }
                                            BuildPromotionImpl.$$$reportNull$$$0(18);
                                        }
                                        catch (IllegalStateException illegalStateException) {
                                            throw BuildPromotionImpl.a(illegalStateException);
                                        }
                                    }
                                    return list7;
                                }
                                l = (Long)Collections.min(set);
                                sVcsModification2 = this.findVcsModificationInVcsRootOrItsParents((VcsRootInstance)vcsRootInstanceEx, string2);
                                try {
                                    try {
                                        if (sVcsModification2 != null) break block48;
                                        list6 = list;
                                        if (list6 != null) break block49;
                                    }
                                    catch (IllegalStateException illegalStateException) {
                                        throw BuildPromotionImpl.a(illegalStateException);
                                    }
                                    BuildPromotionImpl.$$$reportNull$$$0(19);
                                }
                                catch (IllegalStateException illegalStateException) {
                                    throw BuildPromotionImpl.a(illegalStateException);
                                }
                            }
                            return list6;
                        }
                        boolean bl = (Boolean)vcsRootInstanceEx.withConnectedDAG(vcsRootInstance -> {
                            boolean bl;
                            try {
                                bl = vcsRootInstance.getParentId() == vcsRootInstanceEx.getParentId();
                            }
                            catch (IllegalStateException illegalStateException) {
                                throw BuildPromotionImpl.a(illegalStateException);
                            }
                            return bl;
                        }, dAG -> {
                            AtomicBoolean atomicBoolean = new AtomicBoolean();
                            dAG.breadthFirstSearch((Object)sVcsModification2.getId(), (BFSVisitor)new /* Unavailable Anonymous Inner Class!! */);
                            return atomicBoolean.get();
                        });
                        try {
                            try {
                                if (bl) break block50;
                                list5 = list;
                                if (list5 != null) break block51;
                            }
                            catch (IllegalStateException illegalStateException) {
                                throw BuildPromotionImpl.a(illegalStateException);
                            }
                            BuildPromotionImpl.$$$reportNull$$$0(20);
                        }
                        catch (IllegalStateException illegalStateException) {
                            throw BuildPromotionImpl.a(illegalStateException);
                        }
                    }
                    return list5;
                }
            }
            hashMap.put(object2, repositoryState.getDefaultBranchName());
        }
        ArrayList arrayList2 = new ArrayList();
        for (BuildRevisionEx buildRevisionEx : list) {
            string = (String)hashMap.get(buildRevisionEx);
            if (string != null) {
                repositoryState = new RepositoryVersion(buildRevisionEx.getRepositoryVersion().getVersion(), buildRevisionEx.getRepositoryVersion().getDisplayVersion(), string);
                arrayList2.add(BuildPromotionImpl.createAdjustedBuildRevision(buildRevisionEx, (RepositoryVersion)repositoryState, buildRevisionEx.getModificationId()));
                continue;
            }
            arrayList2.add(buildRevisionEx);
        }
        try {
            this.resetBranchToDefault(buildTypeEx, "all the build revisions are reachable from the default branch(es)");
            object = arrayList2;
            if (object == null) {
                BuildPromotionImpl.$$$reportNull$$$0(21);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return object;
    }

    @Nullable
    private SVcsModification findVcsModificationInVcsRootOrItsParents(@NotNull VcsRootInstance vcsRootInstance, @NotNull String string) {
        try {
            if (vcsRootInstance == null) {
                BuildPromotionImpl.$$$reportNull$$$0(22);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        try {
            if (string == null) {
                BuildPromotionImpl.$$$reportNull$$$0(23);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        List list = this.getContext().getVcsHistory().findModificationsByVersion(string);
        try {
            if (list.isEmpty()) {
                return null;
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        for (SVcsModification sVcsModification : list) {
            try {
                if (sVcsModification.getVcsRoot().getId() != vcsRootInstance.getId()) continue;
                return sVcsModification;
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
        }
        for (SVcsModification sVcsModification : list) {
            try {
                if (sVcsModification.getVcsRoot().getParentId() != vcsRootInstance.getParentId()) continue;
                return sVcsModification;
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
        }
        return null;
    }

    private boolean shouldRaiseProblemIfBranchIsInvalid() {
        String string = this.getParameterValue("teamcity.build.invalidBranchReporting");
        if (string == null) {
            string = TeamCityProperties.getProperty((String)"teamcity.build.invalidBranchReporting", (String)"raiseProblem");
        }
        return "raiseProblem".equals(string);
    }

    @Nullable
    public String getAutogeneratedBranchName() {
        List<VcsRootInstanceEntry> list;
        List list2;
        block15: {
            BuildTypeEx buildTypeEx;
            block14: {
                buildTypeEx = this.getBuildType();
                try {
                    if (buildTypeEx == null) {
                        return null;
                    }
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
                try {
                    if (!this.vcsSettingsAffectedByCustomParameters(buildTypeEx)) {
                        return null;
                    }
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
                try {
                    try {
                        if (this.getBooleanInternalParameter("teamcity.internal.promotion.autoBranchName.generateEvenIfBranchSpecDefined") || !buildTypeEx.isBranchSpecDefined(false)) break block14;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildPromotionImpl.a(illegalStateException);
                    }
                    return null;
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
            }
            list2 = buildTypeEx.getVcsRootInstanceEntries();
            list = this.getVcsRootEntries();
            Set<String> set = this.signatures(list2);
            Set<String> set2 = this.signatures(list);
            try {
                try {
                    if (!set.containsAll(set2) && !set2.containsAll(set)) break block15;
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
                return null;
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
        }
        return StringUtil.truncateStringValue((String)BranchNameGenerator.generateBranchName((Collection)list2, list, (String)"<unspecified>"), (int)1000);
    }

    private boolean vcsSettingsAffectedByCustomParameters(@NotNull BuildTypeEx buildTypeEx) {
        boolean bl;
        try {
            if (buildTypeEx == null) {
                BuildPromotionImpl.$$$reportNull$$$0(24);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        try {
            if (this.myUserParameters.getCustomParameters().isEmpty()) {
                return false;
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        HashSet hashSet = new HashSet();
        for (VcsRootInstanceEntry vcsRootInstanceEntry : this.getVcsRootEntries()) {
            SVcsRoot sVcsRoot = vcsRootInstanceEntry.getVcsRoot().getParent();
            for (String string : VcsUtil.getVcsRootPublicProperties((VcsRoot)sVcsRoot).values()) {
                hashSet.addAll(ReferencesResolverUtil.getReferences((String)string));
            }
            CheckoutRules checkoutRules = buildTypeEx.getCheckoutRules((VcsRoot)sVcsRoot);
            try {
                if (checkoutRules == null) continue;
                hashSet.addAll(ReferencesResolverUtil.getReferences((String)checkoutRules.getAsString()));
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
        }
        try {
            bl = !hashSet.isEmpty();
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return bl;
    }

    private void resolveDefaultBranchDisplayName(@NotNull BuildTypeEx buildTypeEx, @NotNull Collection<BuildRevisionEx> collection) {
        try {
            if (buildTypeEx == null) {
                BuildPromotionImpl.$$$reportNull$$$0(25);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        try {
            if (collection == null) {
                BuildPromotionImpl.$$$reportNull$$$0(26);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        if (this.myFinalBranchName.equals("")) {
            String string = this.getDefaultBranchDisplayName(buildTypeEx, collection);
            try {
                if (string != null) {
                    this.setAttribute("teamcity.build.branchDisplayName", string);
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
        }
    }

    @Nullable
    private String getDefaultBranchDisplayName(@NotNull BuildTypeEx buildTypeEx, @NotNull Collection<BuildRevisionEx> collection) {
        try {
            if (buildTypeEx == null) {
                BuildPromotionImpl.$$$reportNull$$$0(27);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        try {
            if (collection == null) {
                BuildPromotionImpl.$$$reportNull$$$0(28);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        Collection<String> collection2 = this.getLogicalNames(buildTypeEx, collection.stream().filter(buildRevisionEx -> {
            boolean bl;
            try {
                bl = buildRevisionEx.getType() == BuildRevisionVcsRootType.REGULAR;
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
            return bl;
        }).collect(Collectors.toList()));
        try {
            if (collection2.size() == 1) {
                return collection2.iterator().next();
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return null;
    }

    @NotNull
    private Collection<String> getLogicalNames(@NotNull BuildTypeEx buildTypeEx, @NotNull Collection<BuildRevisionEx> collection) {
        HashSet<String> hashSet;
        try {
            if (buildTypeEx == null) {
                BuildPromotionImpl.$$$reportNull$$$0(29);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        try {
            if (collection == null) {
                BuildPromotionImpl.$$$reportNull$$$0(30);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        Map<VcsRootInstanceEx, String> map = this.vcsBranchesMap(collection);
        HashSet<String> hashSet2 = new HashSet<String>();
        for (Map.Entry<VcsRootInstanceEx, String> entry : map.entrySet()) {
            VcsRootInstanceEx vcsRootInstanceEx = entry.getKey();
            String string = entry.getValue();
            Set set = buildTypeEx.getLogicalBranchNames((VcsRootInstance)vcsRootInstanceEx, string);
            try {
                if (set == null) {
                    continue;
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
            try {
                if (set.isEmpty()) {
                    set.add(string);
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
            hashSet2.addAll(set);
        }
        try {
            hashSet = hashSet2;
            if (hashSet == null) {
                BuildPromotionImpl.$$$reportNull$$$0(31);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return hashSet;
    }

    @NotNull
    private Map<VcsRootInstanceEx, String> vcsBranchesMap(@NotNull Collection<BuildRevisionEx> collection) {
        HashMap<VcsRootInstanceEx, String> hashMap;
        try {
            if (collection == null) {
                BuildPromotionImpl.$$$reportNull$$$0(32);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        HashMap<VcsRootInstanceEx, String> hashMap2 = new HashMap<VcsRootInstanceEx, String>();
        for (BuildRevisionEx buildRevisionEx : collection) {
            try {
                if (buildRevisionEx.getType() == BuildRevisionVcsRootType.SUBREPO) {
                    continue;
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
            VcsRootInstanceEx vcsRootInstanceEx = (VcsRootInstanceEx)buildRevisionEx.getEntry().getVcsRoot();
            RepositoryVersion repositoryVersion = buildRevisionEx.getRepositoryVersion();
            String string = repositoryVersion.getVcsBranch();
            try {
                if (string == null) {
                    continue;
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
            hashMap2.put(vcsRootInstanceEx, string);
        }
        try {
            hashMap = hashMap2;
            if (hashMap == null) {
                BuildPromotionImpl.$$$reportNull$$$0(33);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return hashMap;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean finalizeBranchName(@Nullable String string) {
        BuildPromotionImpl buildPromotionImpl = this;
        synchronized (buildPromotionImpl) {
            String string2;
            boolean bl;
            block19: {
                block20: {
                    block18: {
                        if (this.myFinalBranchName != null) {
                            return false;
                        }
                        bl = false;
                        string2 = this.getInternalDesiredBranchName();
                        try {
                            if (!"".equals(string2) || string == null) break block18;
                        }
                        catch (IllegalStateException illegalStateException) {
                            throw BuildPromotionImpl.a(illegalStateException);
                        }
                        string2 = string;
                        bl = true;
                        break block20;
                    }
                    if (string2 != null) {
                        string2 = this.getRealOrDummyBuild().getValueResolver().resolve(string2).getResult();
                    } else {
                        boolean bl2;
                        string2 = string;
                        try {
                            bl2 = string != null;
                        }
                        catch (IllegalStateException illegalStateException) {
                            throw BuildPromotionImpl.a(illegalStateException);
                        }
                        bl = bl2;
                    }
                }
                try {
                    if (string2 == null || string2.length() <= 255) break block19;
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
                string2 = string2.substring(0, 255);
            }
            try {
                this.myFinalBranchName = StringPool.getFromPool((String)StringUtil.emptyIfNull((String)string2));
                if (string2 != null) {
                    this.setAttribute("teamcity.build.finalBranchName", this.myFinalBranchName);
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
            try {
                if (bl) {
                    this.setAttribute("teamcity.build.autoBranchName", string);
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
            return true;
        }
    }

    @NotNull
    private List<BuildRevisionEx> checkVcsBranchNamesAndSetMissingModIds(@NotNull Collection<BuildRevisionEx> collection) {
        ArrayList<BuildRevisionEx> arrayList;
        try {
            if (collection == null) {
                BuildPromotionImpl.$$$reportNull$$$0(34);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        ArrayList<BuildRevisionEx> arrayList2 = new ArrayList<BuildRevisionEx>(collection.size());
        for (BuildRevisionEx buildRevisionEx : collection) {
            Long l;
            String string;
            RepositoryVersion repositoryVersion;
            block14: {
                block15: {
                    VcsRootInstance vcsRootInstance;
                    block13: {
                        vcsRootInstance = buildRevisionEx.getEntry().getVcsRoot();
                        repositoryVersion = buildRevisionEx.getRepositoryVersion();
                        string = repositoryVersion.getVcsBranch();
                        try {
                            if (string != null || !vcsRootInstance.isDagBased()) break block13;
                        }
                        catch (IllegalStateException illegalStateException) {
                            throw BuildPromotionImpl.a(illegalStateException);
                        }
                        string = "<unspecified>";
                    }
                    l = buildRevisionEx.getModificationId();
                    try {
                        if (l != null || !vcsRootInstance.isDagBased()) break block14;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildPromotionImpl.a(illegalStateException);
                    }
                    l = buildRevisionEx.findModificationId();
                    try {
                        if (l == null) break block15;
                        LOG.warn("The revision object " + String.valueOf(buildRevisionEx) + " set to the build " + LogUtil.describe((BuildPromotion)this) + " does not have an associated modification id even though there is a VCS change in the database which corresponds to this revision: " + l);
                        break block14;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildPromotionImpl.a(illegalStateException);
                    }
                }
                LOG.debug("The revision object " + String.valueOf(buildRevisionEx) + " set to the build " + LogUtil.describe((BuildPromotion)this) + " does not have an associated modification id");
            }
            RepositoryVersion repositoryVersion2 = new RepositoryVersion(StringPool.getFromPool((String)repositoryVersion.getVersion()), StringPool.getFromPool((String)repositoryVersion.getDisplayVersion()), StringPool.getFromPool((String)string));
            BuildRevisionEx buildRevisionEx2 = BuildPromotionImpl.createAdjustedBuildRevision(buildRevisionEx, repositoryVersion2, l);
            arrayList2.add(buildRevisionEx2);
        }
        try {
            arrayList = arrayList2;
            if (arrayList == null) {
                BuildPromotionImpl.$$$reportNull$$$0(35);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return arrayList;
    }

    @NotNull
    private static BuildRevisionEx createAdjustedBuildRevision(@NotNull BuildRevisionEx buildRevisionEx, @NotNull RepositoryVersion repositoryVersion, @Nullable Long l) {
        BuildRevisionEx buildRevisionEx2;
        try {
            if (buildRevisionEx == null) {
                BuildPromotionImpl.$$$reportNull$$$0(36);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        try {
            if (repositoryVersion == null) {
                BuildPromotionImpl.$$$reportNull$$$0(37);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        BuildRevisionEx buildRevisionEx3 = new BuildRevisionEx(buildRevisionEx.getEntry(), repositoryVersion, l);
        try {
            buildRevisionEx3.setType(buildRevisionEx.getType());
            buildRevisionEx3.setCheckoutMode(buildRevisionEx.getCheckoutMode());
            buildRevisionEx2 = buildRevisionEx3;
            if (buildRevisionEx2 == null) {
                BuildPromotionImpl.$$$reportNull$$$0(38);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return buildRevisionEx2;
    }

    @NotNull
    private Set<String> signatures(@NotNull Collection<VcsRootInstanceEntry> collection) {
        HashSet<String> hashSet;
        try {
            if (collection == null) {
                BuildPromotionImpl.$$$reportNull$$$0(39);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        HashSet<String> hashSet2 = new HashSet<String>();
        for (VcsRootInstanceEntry vcsRootInstanceEntry : collection) {
            VcsRootInstance vcsRootInstance = vcsRootInstanceEntry.getVcsRoot();
            hashSet2.add(vcsRootInstance.getParentId() + "_" + vcsRootInstance.getVcsName() + "_" + this.getPublicProperties(vcsRootInstance) + "_" + String.valueOf(vcsRootInstanceEntry.getCheckoutRules()));
        }
        try {
            hashSet = hashSet2;
            if (hashSet == null) {
                BuildPromotionImpl.$$$reportNull$$$0(40);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return hashSet;
    }

    @NotNull
    private String getPublicProperties(@NotNull VcsRootInstance vcsRootInstance) {
        String string;
        try {
            if (vcsRootInstance == null) {
                BuildPromotionImpl.$$$reportNull$$$0(41);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        Map map = VcsUtil.getVcsRootPublicProperties((VcsRoot)vcsRootInstance);
        ArrayList arrayList = new ArrayList(map.keySet());
        Collections.sort(arrayList);
        StringBuilder stringBuilder = new StringBuilder();
        for (String string2 : arrayList) {
            stringBuilder.append(string2).append((String)map.get(string2));
        }
        try {
            string = stringBuilder.toString();
            if (string == null) {
                BuildPromotionImpl.$$$reportNull$$$0(42);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return string;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void markDirty(int n) {
        BuildPromotionImpl buildPromotionImpl = this;
        synchronized (buildPromotionImpl) {
            this.myDirtyState |= n;
        }
        this.myComputedVcsRootEntries.set(null);
    }

    private void markDirtyAndResetCompatibilityCaches(int n) {
        this.markDirty(n);
        this.resetAllCompatibilityCaches();
    }

    @NotNull
    public List<BuildRevision> getRevisions() {
        List<BuildRevisionEx> list;
        try {
            list = Collections.unmodifiableList(this.getRevisionsForCheckout());
            if (list == null) {
                BuildPromotionImpl.$$$reportNull$$$0(43);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return list;
    }

    @NotNull
    private List<BuildRevisionEx> getRevisionsForCheckout() {
        ArrayList<BuildRevisionEx> arrayList;
        List<BuildRevisionEx> list = this.getRevisionsInternal();
        ArrayList<BuildRevisionEx> arrayList2 = new ArrayList<BuildRevisionEx>(list.size());
        for (BuildRevisionEx buildRevisionEx : list) {
            try {
                if (buildRevisionEx.getCheckoutMode() == BuildRevisionCheckoutMode.MANUAL) continue;
                arrayList2.add(buildRevisionEx);
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
        }
        try {
            arrayList = arrayList2;
            if (arrayList == null) {
                BuildPromotionImpl.$$$reportNull$$$0(44);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return arrayList;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private List<BuildRevisionEx> getRevisionsInternal() {
        this.loadRevisionsIfNeeded();
        BuildPromotionImpl buildPromotionImpl = this;
        synchronized (buildPromotionImpl) {
            return this.myRevisions;
        }
    }

    @NotNull
    public Map<VcsRootInstance, BuildRevision> getRevisionsMap() {
        HashMap<VcsRootInstance, BuildRevision> hashMap;
        HashMap<VcsRootInstance, BuildRevision> hashMap2 = new HashMap<VcsRootInstance, BuildRevision>();
        for (BuildRevision buildRevision : this.getRevisions()) {
            hashMap2.put(buildRevision.getRoot(), buildRevision);
        }
        try {
            hashMap = hashMap2;
            if (hashMap == null) {
                BuildPromotionImpl.$$$reportNull$$$0(45);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return hashMap;
    }

    @NotNull
    public Map<VcsRootInstance, BuildRevisionEx> getAllRevisionsMap() {
        HashMap<VcsRootInstance, BuildRevisionEx> hashMap;
        HashMap<VcsRootInstance, BuildRevisionEx> hashMap2 = new HashMap<VcsRootInstance, BuildRevisionEx>();
        for (BuildRevisionEx buildRevisionEx : this.getRevisionsInternal()) {
            hashMap2.put(buildRevisionEx.getRoot(), buildRevisionEx);
        }
        try {
            hashMap = hashMap2;
            if (hashMap == null) {
                BuildPromotionImpl.$$$reportNull$$$0(46);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return hashMap;
    }

    @NotNull
    public List<VcsRootInstanceEntry> getVcsRootEntries() {
        ArrayList<VcsRootInstanceEntry> arrayList;
        List<BuildRevisionEx> list;
        block8: {
            list = this.getRevisionsForCheckout();
            try {
                try {
                    if (this.myModificationId != null || !list.isEmpty()) break block8;
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
                return this.computePromotionVcsRoots();
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
        }
        ArrayList<VcsRootInstanceEntry> arrayList2 = new ArrayList<VcsRootInstanceEntry>(list.size());
        for (BuildRevision buildRevision : list) {
            arrayList2.add(buildRevision.getEntry());
        }
        try {
            arrayList = arrayList2;
            if (arrayList == null) {
                BuildPromotionImpl.$$$reportNull$$$0(47);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return arrayList;
    }

    @NotNull
    public List<VcsRootInstanceEntry> getVcsRootEntries(boolean bl) {
        List<VcsRootInstanceEntry> list;
        List<VcsRootInstanceEntry> list2 = this.getVcsRootEntries();
        if (bl) {
            for (BuildRevisionEx buildRevisionEx : this.getRevisionsInternal()) {
                try {
                    try {
                        if (buildRevisionEx.getType() != BuildRevisionVcsRootType.PROJECT_SETTINGS || buildRevisionEx.getCheckoutMode() != BuildRevisionCheckoutMode.MANUAL) continue;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildPromotionImpl.a(illegalStateException);
                    }
                    list2.add(buildRevisionEx.getEntry());
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
            }
        }
        try {
            list = list2;
            if (list == null) {
                BuildPromotionImpl.$$$reportNull$$$0(48);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return list;
    }

    @NotNull
    private List<VcsRootInstanceEntry> computePromotionVcsRoots() {
        List<VcsRootInstanceEntry> list;
        block21: {
            List<VcsRootInstanceEntry> list2;
            block22: {
                block19: {
                    List<Object> list3;
                    List<Object> list4;
                    block20: {
                        List<Object> list5;
                        List<VcsRootInstanceEntry> list6 = this.myComputedVcsRootEntries.get();
                        try {
                            if (list6 != null) {
                                return new ArrayList<VcsRootInstanceEntry>(list6);
                            }
                        }
                        catch (IllegalStateException illegalStateException) {
                            throw BuildPromotionImpl.a(illegalStateException);
                        }
                        try {
                            list5 = this.isPersisted() ? this.loadVcsRootInstanceEntriesFromDB() : Collections.emptyList();
                        }
                        catch (IllegalStateException illegalStateException) {
                            throw BuildPromotionImpl.a(illegalStateException);
                        }
                        list4 = list5;
                        try {
                            try {
                                if (list4.isEmpty()) break block19;
                                if (this.myComputedVcsRootEntries.compareAndSet(null, list4)) break block20;
                            }
                            catch (IllegalStateException illegalStateException) {
                                throw BuildPromotionImpl.a(illegalStateException);
                            }
                            return this.computePromotionVcsRoots();
                        }
                        catch (IllegalStateException illegalStateException) {
                            throw BuildPromotionImpl.a(illegalStateException);
                        }
                    }
                    try {
                        list3 = list4;
                        if (list3 == null) {
                            BuildPromotionImpl.$$$reportNull$$$0(49);
                        }
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildPromotionImpl.a(illegalStateException);
                    }
                    return list3;
                }
                list = this.computeVcsRootsFromParentSettings();
                try {
                    try {
                        if (!this.isInsideDependencyParametersProvider()) break block21;
                        list2 = list;
                        if (list2 != null) break block22;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildPromotionImpl.a(illegalStateException);
                    }
                    BuildPromotionImpl.$$$reportNull$$$0(50);
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
            }
            return list2;
        }
        try {
            if (!this.myComputedVcsRootEntries.compareAndSet(null, list)) {
                return this.computePromotionVcsRoots();
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return new ArrayList<VcsRootInstanceEntry>(list);
    }

    @NotNull
    private List<VcsRootInstanceEntry> computeVcsRootsFromParentSettings() {
        List list;
        BuildTypeEx buildTypeEx;
        DummyBuild dummyBuild;
        block15: {
            List<VcsRootInstanceEntry> list2;
            block16: {
                Object object;
                try {
                    object = TeamCityProperties.getBooleanOrTrue((String)"teamcity.buildPromotion.useDummyBuildForVcsRootsCalculation") ? this.getDummyBuild() : this.getRealOrDummyBuild();
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
                dummyBuild = object;
                buildTypeEx = this.getBuildType();
                try {
                    try {
                        if (buildTypeEx != null) break block15;
                        list2 = Collections.emptyList();
                        if (list2 != null) break block16;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildPromotionImpl.a(illegalStateException);
                    }
                    BuildPromotionImpl.$$$reportNull$$$0(51);
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
            }
            return list2;
        }
        List list3 = this.getBuildSettings().getParentSettings().getVcsRootEntries();
        List list4 = BuildTypeImpl.resolveCheckoutRulesConflict((List)list3, (SVcsRoot)this.getContext().getSettingsRootManager().getVersionedSettingsVcsRoot((SProject)buildTypeEx.getProject()));
        List list5 = this.getContext().getVcsRootInstancesManager().getVcsRootInstanceEntries(list4, dummyBuild.getValueResolver());
        for (int i = 0; i < list5.size(); ++i) {
            VcsRootInstanceEntry vcsRootInstanceEntry = (VcsRootInstanceEntry)list5.get(i);
            list5.set(i, new VcsRootInstanceEntry((VcsRootInstance)this.wrapVcsRootInstanceWithUnknownParent((VcsRootInstanceEx)vcsRootInstanceEntry.getVcsRoot()), vcsRootInstanceEntry.getCheckoutRules()));
        }
        Map<VcsRootInstance, VcsRootInstance> map = this.getCustomOverriddenRoots();
        if (map != null) {
            for (Map.Entry entry : map.entrySet()) {
                for (int i = 0; i < list5.size(); ++i) {
                    VcsRootInstanceEntry vcsRootInstanceEntry = (VcsRootInstanceEntry)list5.get(i);
                    try {
                        if (vcsRootInstanceEntry.getVcsRoot().getParentId() != ((VcsRootInstance)entry.getKey()).getParentId()) continue;
                        list5.set(i, new VcsRootInstanceEntry((VcsRootInstance)entry.getValue(), vcsRootInstanceEntry.getCheckoutRules()));
                        continue;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildPromotionImpl.a(illegalStateException);
                    }
                }
            }
        }
        try {
            list = list5;
            if (list == null) {
                BuildPromotionImpl.$$$reportNull$$$0(52);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return list;
    }

    private boolean isInsideDependencyParametersProvider() {
        return this.getContext().getDependencyParametersProvider().isInsideParametersProvider();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void loadRevisionsIfNeeded() {
        BuildPromotionImpl buildPromotionImpl = this;
        synchronized (buildPromotionImpl) {
            if (this.myRevisions != null) {
                return;
            }
        }
        this.loadRevisionsFromDB();
    }

    private void loadRevisionsFromDB() {
        Map map;
        block4: {
            map = (Map)this.getContext().getSQLRunner().withDB(dBFunctions -> new BuildPromotionRevisionsQuery().loadRevisions(dBFunctions, Collections.singleton(this.myId), 1));
            try {
                try {
                    if ($assertionsDisabled || map.containsKey(this.myId)) break block4;
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
                throw new AssertionError();
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
        }
        this.initRevisionsFromTuples((List)map.get(this.myId));
    }

    @NotNull
    private List<VcsRootInstanceEntry> loadVcsRootInstanceEntriesFromDB() {
        ArrayList<VcsRootInstanceEntry> arrayList;
        List list;
        block13: {
            List<VcsRootInstanceEntry> list2;
            block14: {
                list = (List)this.getContext().getSQLRunner().withDB(dBFunctions -> (ArrayList)dBFunctions.queryForTuples(new ArrayList(), VcsRootEntryTuple.class, null, "select vcs_root_id, checkout_rules from build_checkout_rules where build_state_id = ?", new Object[]{this.myId}));
                try {
                    try {
                        if (!list.isEmpty()) break block13;
                        list2 = Collections.emptyList();
                        if (list2 != null) break block14;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildPromotionImpl.a(illegalStateException);
                    }
                    BuildPromotionImpl.$$$reportNull$$$0(53);
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
            }
            return list2;
        }
        ArrayList<VcsRootInstanceEntry> arrayList2 = new ArrayList<VcsRootInstanceEntry>();
        for (VcsRootEntryTuple vcsRootEntryTuple : list) {
            String string;
            VcsRootInstanceEx vcsRootInstanceEx = this.findVcsRootInstanceById(vcsRootEntryTuple.id);
            try {
                if (vcsRootInstanceEx == null) {
                    continue;
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
            UnicodeString unicodeString = vcsRootEntryTuple.checkoutRules;
            try {
                string = unicodeString == null ? "" : unicodeString.string;
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
            CheckoutRules checkoutRules = CheckoutRulesFactory.createFromString((String)string);
            arrayList2.add(new VcsRootInstanceEntry((VcsRootInstance)vcsRootInstanceEx, checkoutRules));
        }
        try {
            arrayList = arrayList2;
            if (arrayList == null) {
                BuildPromotionImpl.$$$reportNull$$$0(54);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return arrayList;
    }

    @Nullable
    private VcsRootInstanceEx findVcsRootInstanceById(long l) {
        VcsRootInstanceEx vcsRootInstanceEx = (VcsRootInstanceEx)this.getContext().getVcsRootInstancesManager().findRootInstanceById(l);
        try {
            if (vcsRootInstanceEx == null) {
                return null;
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return this.wrapVcsRootInstanceWithUnknownParent(vcsRootInstanceEx);
    }

    @NotNull
    private VcsRootInstanceEx wrapVcsRootInstanceWithUnknownParent(@NotNull VcsRootInstanceEx vcsRootInstanceEx) {
        VcsRootInstanceEx vcsRootInstanceEx2;
        block13: {
            VcsRootInstanceEx vcsRootInstanceEx3;
            block14: {
                try {
                    if (vcsRootInstanceEx == null) {
                        BuildPromotionImpl.$$$reportNull$$$0(55);
                    }
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
                try {
                    try {
                        if (!(vcsRootInstanceEx instanceof VcsRootInstanceWithParent)) break block13;
                        vcsRootInstanceEx3 = vcsRootInstanceEx;
                        if (vcsRootInstanceEx3 != null) break block14;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildPromotionImpl.a(illegalStateException);
                    }
                    BuildPromotionImpl.$$$reportNull$$$0(56);
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
            }
            return vcsRootInstanceEx3;
        }
        SVcsRoot sVcsRoot = vcsRootInstanceEx.getParent();
        try {
            if (sVcsRoot instanceof DeletedVcsRoot) {
                return new VcsRootInstanceWithParent(vcsRootInstanceEx, (SVcsRoot)new /* Unavailable Anonymous Inner Class!! */);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        try {
            vcsRootInstanceEx2 = vcsRootInstanceEx;
            if (vcsRootInstanceEx2 == null) {
                BuildPromotionImpl.$$$reportNull$$$0(57);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return vcsRootInstanceEx2;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void initRevisionsFromTuples(@NotNull List<BuildRevisionData> list) {
        try {
            if (list == null) {
                BuildPromotionImpl.$$$reportNull$$$0(58);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        List<BuildRevisionEx> list2 = this.createRevisions(list);
        Map<VcsRootInstance, VcsRootInstance> map = this.createOverriddenRootsMap(list);
        BuildPromotionImpl buildPromotionImpl = this;
        synchronized (buildPromotionImpl) {
            this.myRevisions = list2;
            if (!list2.isEmpty()) {
                this.myOverridenVcsRoots = map;
            }
        }
    }

    @NotNull
    private List<BuildRevisionEx> createRevisions(@NotNull List<BuildRevisionData> list) {
        ArrayList<BuildRevisionEx> arrayList;
        try {
            if (list == null) {
                BuildPromotionImpl.$$$reportNull$$$0(59);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        ArrayList<BuildRevisionEx> arrayList2 = new ArrayList<BuildRevisionEx>(list.size());
        for (BuildRevisionData buildRevisionData : list) {
            VcsRootInstanceEx vcsRootInstanceEx = this.findVcsRootInstanceById(buildRevisionData.myVcsRootId);
            try {
                if (vcsRootInstanceEx == null) {
                    continue;
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
            String string = buildRevisionData.myCheckoutRules;
            BuildRevisionEx buildRevisionEx = new BuildRevisionEx(new VcsRootInstanceEntry((VcsRootInstance)vcsRootInstanceEx, CheckoutRulesFactory.createFromString((String)string)), new RepositoryVersion(buildRevisionData.myRevision, buildRevisionData.myRevisionDisplayName, buildRevisionData.myVcsBranchName), buildRevisionData.myVcsRootModificationId);
            buildRevisionEx.setType(BuildRevisionVcsRootType.create((Integer)buildRevisionData.myVcsRootType));
            buildRevisionEx.setCheckoutMode(BuildRevisionCheckoutMode.fromId((Integer)buildRevisionData.myCheckoutMode));
            arrayList2.add(buildRevisionEx);
        }
        try {
            arrayList2.sort(this.getComparatorAsVcsRootEntries());
            arrayList = arrayList2;
            if (arrayList == null) {
                BuildPromotionImpl.$$$reportNull$$$0(60);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return arrayList;
    }

    @NotNull
    private Map<VcsRootInstance, VcsRootInstance> createOverriddenRootsMap(@NotNull List<BuildRevisionData> list) {
        HashMap<VcsRootInstance, VcsRootInstance> hashMap;
        try {
            if (list == null) {
                BuildPromotionImpl.$$$reportNull$$$0(61);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        HashMap<VcsRootInstance, VcsRootInstance> hashMap2 = new HashMap<VcsRootInstance, VcsRootInstance>();
        for (BuildRevisionData buildRevisionData : list) {
            VcsRootInstanceEx vcsRootInstanceEx = this.findVcsRootInstanceById(buildRevisionData.myVcsRootId);
            try {
                if (vcsRootInstanceEx == null) {
                    continue;
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
            VcsRootInstanceEx vcsRootInstanceEx2 = null;
            if (buildRevisionData.myOriginalVcsRootId != null) {
                vcsRootInstanceEx2 = this.findVcsRootInstanceById(buildRevisionData.myOriginalVcsRootId);
            }
            try {
                if (vcsRootInstanceEx2 == null) continue;
                hashMap2.put((VcsRootInstance)vcsRootInstanceEx2, (VcsRootInstance)vcsRootInstanceEx);
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
        }
        try {
            hashMap = hashMap2;
            if (hashMap == null) {
                BuildPromotionImpl.$$$reportNull$$$0(62);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return hashMap;
    }

    @Nullable
    public static Long modId(Long l) {
        Long l2;
        block5: {
            block4: {
                try {
                    try {
                        if (l != null && l != -1L) break block4;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildPromotionImpl.a(illegalStateException);
                    }
                    l2 = null;
                    break block5;
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
            }
            l2 = l;
        }
        return l2;
    }

    public Long getLastModificationId() {
        return this.myModificationId;
    }

    @Nullable
    public Long getChainModificationId() {
        return this.myChainModificationId;
    }

    @Nullable
    public SBuild getAssociatedBuild() {
        Long l = this.getAssociatedBuildId();
        try {
            if (l == null) {
                return null;
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return this.getContext().getBuildsManager().findBuildInstanceById(l.longValue());
    }

    public QueuedBuildEx getQueuedBuild() {
        return this.getContext().getQueue().findQueued((BuildPromotion)this);
    }

    public Comment getBuildComment() {
        Comment comment;
        AuditLogAction auditLogAction = this.myAuditLogActions.findLastAction(new ActionTypesFilter(new ActionType[]{ActionType.BUILD_COMMENT, ActionType.BUILD_COMMENT_REMOVE}));
        try {
            comment = auditLogAction != null ? auditLogAction.getComment() : null;
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return comment;
    }

    public void setBuildComment(@NotNull User user, @Nullable String string) {
        ActionType actionType;
        try {
            if (user == null) {
                BuildPromotionImpl.$$$reportNull$$$0(63);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        try {
            this.checkHasPermissionForProject(Permission.COMMENT_BUILD);
            actionType = this.isNullOrEmpty(string) ? ActionType.BUILD_COMMENT_REMOVE : ActionType.BUILD_COMMENT;
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        ActionType actionType2 = actionType;
        this.getAuditLog().log(actionType2, string, this.getBuildTypeId(), (SUser)user, new Object[0]);
        SBuild sBuild = this.getAssociatedBuild();
        try {
            if (sBuild != null) {
                ((BuildServerListener)this.getContext().getEventDispatcher().getMulticaster()).buildCommented(sBuild, user, string);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        this.getContext().getNodesBuildsEvents().publishCommentUpdated((BuildPromotion)this);
    }

    private boolean isNullOrEmpty(@Nullable String string) {
        boolean bl;
        block5: {
            block4: {
                try {
                    try {
                        if (string != null && !string.isEmpty()) break block4;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildPromotionImpl.a(illegalStateException);
                    }
                    bl = true;
                    break block5;
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
            }
            bl = false;
        }
        return bl;
    }

    @NotNull
    public List<AuditLogAction> getAuditLogActions(@Nullable AuditLogFilter auditLogFilter) {
        List list;
        try {
            list = this.myAuditLogActions.getLogActions(auditLogFilter);
            if (list == null) {
                BuildPromotionImpl.$$$reportNull$$$0(64);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return list;
    }

    @NotNull
    public AuditLog getAuditLog() {
        AuditLog auditLog;
        try {
            auditLog = this.myAuditLogActions.getAuditLog();
            if (auditLog == null) {
                BuildPromotionImpl.$$$reportNull$$$0(65);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return auditLog;
    }

    public void resetLogActionsCache() {
        this.myAuditLogActions.resetLogActions();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void resetTagsCache() {
        Object object = this.myTagsLock;
        synchronized (object) {
            this.myTagDatasRef = new SoftReference<Object>(null);
        }
    }

    @Nullable
    public String getCollectChangesError() {
        return StringUtil.objectToString((Object)this.getAttribute("teamcity.collectChanges.error"));
    }

    @Nullable
    public Integer getCollectChangesErrorAnchor() {
        String string = StringUtil.objectToString((Object)this.getAttribute("teamcity.collectChanges.error.anchor"));
        try {
            if (string == null) {
                return null;
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return Integer.parseInt(string);
    }

    public boolean isFailedToCollectChanges() {
        boolean bl;
        try {
            bl = this.getAttribute("teamcity.collectChanges.error") != null;
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return bl;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void setFailedToCollectChanges(@Nullable Throwable throwable) {
        BuildPromotionImpl buildPromotionImpl = this;
        synchronized (buildPromotionImpl) {
            Object object;
            if (!this.isChangeCollectingNeeded()) {
                return;
            }
            if (!(throwable instanceof VcsException)) {
                object = "Unexpected error while checking for changes for the build promotion: " + LogUtil.describe((BuildPromotion)this);
                try {
                    Loggers.VCS.warn((String)object + "; check teamcity-vcs.log for stacktrace");
                    if (throwable != null) {
                        Loggers.VCS.info((String)object + ": " + throwable.getMessage(), throwable);
                    }
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
            }
            try {
                Object object2 = object = throwable != null ? throwable.getMessage() : null;
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
            if (object == null) {
                object = "unknown error";
            }
            if (throwable instanceof VcsRootVcsException) {
                VcsRoot vcsRoot = ((VcsRootVcsException)throwable).getRoot();
                object = (String)object + ", VCS root: " + vcsRoot.describe(false);
            }
            this.getBuildLog().messageAsync("Failed to collect changes, error: " + (String)object, Status.ERROR, MessageAttrs.serverMessage().flowId("_ChangesBlockFlow_"), n -> this.setAttribute("teamcity.collectChanges.error.anchor", n));
            this.setAttribute("teamcity.collectChanges.error", StringUtil.truncateStringValueWithDotsAtCenter((String)object, (int)1000));
            this.myRevisions = this.createNotAvailableRevisions();
            this.myModificationId = null;
        }
        this.detachChanges();
    }

    @NotNull
    private List<BuildRevisionEx> createNotAvailableRevisions() {
        ArrayList<BuildRevisionEx> arrayList;
        ArrayList<BuildRevisionEx> arrayList2 = new ArrayList<BuildRevisionEx>();
        for (VcsRootInstanceEntry vcsRootInstanceEntry : this.getVcsRootEntries()) {
            arrayList2.add(new BuildRevisionEx(vcsRootInstanceEntry, new RepositoryVersion("N/A", "N/A")));
        }
        try {
            arrayList = arrayList2;
            if (arrayList == null) {
                BuildPromotionImpl.$$$reportNull$$$0(66);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return arrayList;
    }

    public Long getAssociatedBuildId() {
        return this.myAssociatedBuildId;
    }

    public boolean isLaterThan(@NotNull BuildPromotion buildPromotion) {
        try {
            if (buildPromotion == null) {
                BuildPromotionImpl.$$$reportNull$$$0(67);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return new BuildPromotionOrderInfo((BuildPromotionEx)this).isLaterThan(new BuildPromotionOrderInfo((BuildPromotionEx)buildPromotion));
    }

    @NotNull
    public Map<String, String> getCustomParameters() {
        Map map;
        try {
            map = this.myUserParameters.getCustomParameters();
            if (map == null) {
                BuildPromotionImpl.$$$reportNull$$$0(68);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return map;
    }

    @NotNull
    public Map<String, String> getDefaultParameters() {
        Map map;
        try {
            map = this.myUserParameters.getDefaultParameters();
            if (map == null) {
                BuildPromotionImpl.$$$reportNull$$$0(69);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return map;
    }

    @NotNull
    public Map<String, String> getParameters() {
        Map map;
        try {
            map = this.myUserParameters.getParameters();
            if (map == null) {
                BuildPromotionImpl.$$$reportNull$$$0(70);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return map;
    }

    @Nullable
    public String getParameterValue(@NotNull String string) {
        try {
            if (string == null) {
                BuildPromotionImpl.$$$reportNull$$$0(71);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return this.myUserParameters.getParameterValue(string);
    }

    @NotNull
    public Collection<Parameter> getParametersCollection() {
        Collection collection;
        try {
            collection = this.myUserParameters.getParametersCollection();
            if (collection == null) {
                BuildPromotionImpl.$$$reportNull$$$0(72);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return collection;
    }

    @Nullable
    public Parameter getParameter(@NotNull String string) {
        try {
            if (string == null) {
                BuildPromotionImpl.$$$reportNull$$$0(73);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return this.myUserParameters.getParameter(string);
    }

    @Nullable
    public Branch getBranch() {
        String string = BuildPromotionImpl.toExternalName((String)this.getInternalBranchName());
        try {
            if (!string.equals("<default>")) {
                return new NonDefaultBranch(this, string);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        try {
            if (this.getAttribute("teamcity.build.desiredBranchName") == null) {
                return null;
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return new BranchImpl(string, this.getBranchDisplayName(string));
    }

    @NotNull
    private String getBranchDisplayName(@NotNull String string) {
        String string2;
        try {
            if (string == null) {
                BuildPromotionImpl.$$$reportNull$$$0(74);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        String string3 = StringUtil.objectToString((Object)this.getAttribute("teamcity.build.branchDisplayName"));
        if (string3 == null) {
            string3 = string;
        }
        try {
            string2 = string3;
            if (string2 == null) {
                BuildPromotionImpl.$$$reportNull$$$0(75);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return string2;
    }

    @NotNull
    private static String toInternalName(@NotNull String string) {
        String string2;
        String string3;
        try {
            if (string == null) {
                BuildPromotionImpl.$$$reportNull$$$0(76);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        try {
            string3 = string.equals("<default>") ? "" : string;
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        try {
            string2 = string3;
            if (string3 == null) {
                BuildPromotionImpl.$$$reportNull$$$0(77);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return string2;
    }

    @Nullable
    public String getCheckoutDirectory() {
        try {
            return this.getBuildSettings().getCheckoutDirectory();
        }
        catch (Exception exception) {
            return null;
        }
    }

    @NotNull
    public BuildSettings getBuildSettings() throws BuildTypeNotFoundException {
        BuildSettings buildSettings;
        int[] nArray = new int[1];
        BuildSettings buildSettings2 = this.myBuildSettings.get(nArray);
        if (buildSettings2 == null) {
            buildSettings2 = this.getContext().getPromotionBuildSettingsFactory().createBuildSettings((BuildPromotionEx)this);
            this.myBuildSettings.compareAndSet(null, buildSettings2, nArray[0], nArray[0] + 1);
        }
        try {
            buildSettings = buildSettings2;
            if (buildSettings == null) {
                BuildPromotionImpl.$$$reportNull$$$0(78);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildPromotionImpl.a(buildTypeNotFoundException);
        }
        return buildSettings;
    }

    @NotNull
    public File getFrozenSettingsFile() {
        return this.getFrozenSettingsFile(this.getArtifactsDirectory());
    }

    @NotNull
    private File getFrozenSettingsFile(@NotNull File file) {
        try {
            if (file == null) {
                BuildPromotionImpl.$$$reportNull$$$0(79);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        File file2 = new File(file, ".teamcity" + File.separator + "settings");
        return new File(file2, "buildSettings.xml");
    }

    public boolean hasFrozenSettings() {
        return this.hasFrozenSettings(this.getAttribute("teamcity.build.hasFrozenSettings"));
    }

    public boolean hasFrozenSettingsFromVcs() {
        return this.hasFrozenSettingsFromVcs(this.getAttribute("teamcity.build.hasFrozenSettings"));
    }

    public boolean needToFreezeSettings() {
        boolean bl;
        try {
            bl = this.getAttribute("teamcity.build.hasFrozenSettings") == null;
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return bl;
    }

    private boolean hasFrozenSettings(@Nullable Object object) {
        boolean bl;
        block7: {
            block6: {
                try {
                    try {
                        try {
                            if (object == null || "".equals(object)) break block6;
                        }
                        catch (IllegalStateException illegalStateException) {
                            throw BuildPromotionImpl.a(illegalStateException);
                        }
                        if ("false".equals(object)) break block6;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildPromotionImpl.a(illegalStateException);
                    }
                    bl = true;
                    break block7;
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
            }
            bl = false;
        }
        return bl;
    }

    private boolean hasFrozenSettingsFromVcs(@Nullable Object object) {
        try {
            if ("vcs".equals(object)) {
                return true;
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return this.hasFrozenSettingsFromVcsLegacy(object);
    }

    private boolean hasFrozenSettingsFromVcsLegacy(@Nullable Object object) {
        try {
            if (object == null) {
                return false;
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return Boolean.parseBoolean(object.toString());
    }

    @NotNull
    public String getDefaultCheckoutDirectory(BuildTypeDescriptor.CheckoutType checkoutType) {
        String string;
        try {
            if (checkoutType == BuildTypeDescriptor.CheckoutType.AUTO) {
                throw new IllegalArgumentException("Default checkout directory can't be calculated for auto checkout type.");
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        BuildTypeEx buildTypeEx = this.getBuildType();
        try {
            if (buildTypeEx == null) {
                return "";
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        try {
            string = BuildTypeImpl.getDefaultVcsSettingsHash(this.getVcsRootEntries(), (SBuildType)buildTypeEx, (BuildTypeDescriptor.CheckoutType)checkoutType);
            if (string == null) {
                BuildPromotionImpl.$$$reportNull$$$0(80);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return string;
    }

    @NotNull
    public Map<String, String> getBuildParameters() {
        Map map;
        try {
            map = FilterUtil.filterMapByKey(this.getParameters(), string -> {
                boolean bl;
                block5: {
                    block4: {
                        try {
                            try {
                                if (!SimpleParameter.isSystemProperty((String)string) && !SimpleParameter.isEnvironmentVariable((String)string)) break block4;
                            }
                            catch (IllegalStateException illegalStateException) {
                                throw BuildPromotionImpl.a(illegalStateException);
                            }
                            bl = true;
                            break block5;
                        }
                        catch (IllegalStateException illegalStateException) {
                            throw BuildPromotionImpl.a(illegalStateException);
                        }
                    }
                    bl = false;
                }
                return bl;
            });
            if (map == null) {
                BuildPromotionImpl.$$$reportNull$$$0(81);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return map;
    }

    public void setCustomParameters(@NotNull Map<String, String> map) {
        try {
            if (map == null) {
                BuildPromotionImpl.$$$reportNull$$$0(82);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        this.checkHasPermissionForProject(Permission.CUSTOMIZE_BUILD_PARAMETERS);
        this.myUserParameters.setCustomParameters(map);
        this.markDirtyAndResetCompatibilityCaches(1);
    }

    public void setCustomParameters(@NotNull Collection<Parameter> collection) {
        try {
            if (collection == null) {
                BuildPromotionImpl.$$$reportNull$$$0(83);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        this.checkHasPermissionForProject(Permission.CUSTOMIZE_BUILD_PARAMETERS);
        this.myUserParameters.setCustomParameters(collection);
        this.markDirtyAndResetCompatibilityCaches(1);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void setOverridenVcsRoots(@NotNull Map<VcsRootInstance, VcsRootInstance> map) throws InvalidOverridenRootsException {
        try {
            if (map == null) {
                BuildPromotionImpl.$$$reportNull$$$0(84);
            }
        }
        catch (InvalidOverridenRootsException invalidOverridenRootsException) {
            throw BuildPromotionImpl.a(invalidOverridenRootsException);
        }
        this.checkHasPermissionForProject(Permission.CUSTOMIZE_BUILD_PARAMETERS);
        BuildPromotionImpl buildPromotionImpl = this;
        synchronized (buildPromotionImpl) {
            for (Map.Entry<VcsRootInstance, VcsRootInstance> entry : map.entrySet()) {
                try {
                    if (entry.getKey() == null) {
                        throw new InvalidOverridenRootsException("Original root id is null");
                    }
                }
                catch (InvalidOverridenRootsException invalidOverridenRootsException) {
                    throw BuildPromotionImpl.a(invalidOverridenRootsException);
                }
                try {
                    if (entry.getValue() == null) {
                        throw new InvalidOverridenRootsException("Substitution root id is null");
                    }
                }
                catch (InvalidOverridenRootsException invalidOverridenRootsException) {
                    throw BuildPromotionImpl.a(invalidOverridenRootsException);
                }
                try {
                    if (entry.getKey().getParentId() == entry.getValue().getParentId()) continue;
                    throw new InvalidOverridenRootsException("Overridden root must have the same parent as original VCS root");
                }
                catch (InvalidOverridenRootsException invalidOverridenRootsException) {
                    throw BuildPromotionImpl.a(invalidOverridenRootsException);
                }
            }
            this.myOverridenVcsRoots = new HashMap<VcsRootInstance, VcsRootInstance>(map);
        }
        this.markDirtyAndResetCompatibilityCaches(2);
        for (BuildDependency buildDependency : this.getDependencies()) {
            BuildPromotionEx buildPromotionEx = (BuildPromotionEx)buildDependency.getDependOn();
            HashMap<VcsRootInstance, VcsRootInstance> hashMap = new HashMap<VcsRootInstance, VcsRootInstance>();
            for (VcsRootInstanceEntry vcsRootInstanceEntry : buildPromotionEx.getVcsRootEntries()) {
                for (Map.Entry<VcsRootInstance, VcsRootInstance> entry : map.entrySet()) {
                    try {
                        if (vcsRootInstanceEntry.getVcsRoot().getParentId() != entry.getKey().getParentId()) continue;
                        hashMap.put(vcsRootInstanceEntry.getVcsRoot(), entry.getValue());
                    }
                    catch (InvalidOverridenRootsException invalidOverridenRootsException) {
                        throw BuildPromotionImpl.a(invalidOverridenRootsException);
                    }
                }
            }
            buildPromotionEx.setOverridenVcsRoots(hashMap);
        }
    }

    private void checkHasPermissionForProject(@NotNull Permission permission) {
        try {
            if (permission == null) {
                BuildPromotionImpl.$$$reportNull$$$0(85);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        String string = this.getProjectId();
        try {
            if (string != null) {
                this.getContext().getSecurityContext().getAccessChecker().checkHasPermissionsForProject(string, new Permission[]{permission});
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
    }

    @NotNull
    public Map<VcsRootInstance, VcsRootInstance> getOverridenVcsRoots() {
        Map<VcsRootInstance, VcsRootInstance> map;
        block26: {
            block24: {
                Map<VcsRootInstance, VcsRootInstance> map2;
                block25: {
                    block22: {
                        Map<VcsRootInstance, VcsRootInstance> map3;
                        block23: {
                            Map<VcsRootInstance, VcsRootInstance> map4 = this.getCustomOverriddenRoots();
                            try {
                                try {
                                    if (map4 == null) break block22;
                                    map3 = map4;
                                    if (map3 != null) break block23;
                                }
                                catch (IllegalStateException illegalStateException) {
                                    throw BuildPromotionImpl.a(illegalStateException);
                                }
                                BuildPromotionImpl.$$$reportNull$$$0(86);
                            }
                            catch (IllegalStateException illegalStateException) {
                                throw BuildPromotionImpl.a(illegalStateException);
                            }
                        }
                        return map3;
                    }
                    try {
                        try {
                            if (this.isChangeCollectingNeeded()) break block24;
                            map2 = Collections.emptyMap();
                            if (map2 != null) break block25;
                        }
                        catch (IllegalStateException illegalStateException) {
                            throw BuildPromotionImpl.a(illegalStateException);
                        }
                        BuildPromotionImpl.$$$reportNull$$$0(87);
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildPromotionImpl.a(illegalStateException);
                    }
                }
                return map2;
            }
            BuildTypeEx buildTypeEx = this.getBuildType();
            if (buildTypeEx != null) {
                HashMap<VcsRootInstance, VcsRootInstance> hashMap;
                block27: {
                    HashMap<VcsRootInstance, VcsRootInstance> hashMap2 = new HashMap<VcsRootInstance, VcsRootInstance>();
                    List list = buildTypeEx.getVcsRootInstanceEntries();
                    for (VcsRootInstanceEntry vcsRootInstanceEntry : this.computePromotionVcsRoots()) {
                        for (VcsRootInstanceEntry vcsRootInstanceEntry2 : list) {
                            try {
                                try {
                                    if (vcsRootInstanceEntry.getVcsRoot().getParentId() != vcsRootInstanceEntry2.getVcsRoot().getParentId() || VcsUtil.getVcsRootPropertiesHash((VcsRoot)vcsRootInstanceEntry.getVcsRoot()) == VcsUtil.getVcsRootPropertiesHash((VcsRoot)vcsRootInstanceEntry2.getVcsRoot())) continue;
                                }
                                catch (IllegalStateException illegalStateException) {
                                    throw BuildPromotionImpl.a(illegalStateException);
                                }
                                hashMap2.put(vcsRootInstanceEntry2.getVcsRoot(), vcsRootInstanceEntry.getVcsRoot());
                            }
                            catch (IllegalStateException illegalStateException) {
                                throw BuildPromotionImpl.a(illegalStateException);
                            }
                        }
                    }
                    try {
                        try {
                            if (hashMap2.isEmpty()) break block26;
                            hashMap = hashMap2;
                            if (hashMap != null) break block27;
                        }
                        catch (IllegalStateException illegalStateException) {
                            throw BuildPromotionImpl.a(illegalStateException);
                        }
                        BuildPromotionImpl.$$$reportNull$$$0(88);
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildPromotionImpl.a(illegalStateException);
                    }
                }
                return hashMap;
            }
        }
        try {
            map = Collections.emptyMap();
            if (map == null) {
                BuildPromotionImpl.$$$reportNull$$$0(89);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return map;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void markAttributesReadFromDB() {
        BuildPromotionImpl buildPromotionImpl = this;
        synchronized (buildPromotionImpl) {
            this.myAttributesRead = true;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Unable to fully structure code
     */
    public final void setAttribute(@NotNull String var1_1, @Nullable Object var2_2) {
        block28: {
            try {
                if (var1_1 == null) {
                    BuildPromotionImpl.$$$reportNull$$$0(90);
                }
            }
            catch (IllegalStateException v0) {
                throw BuildPromotionImpl.a(v0);
            }
            try {
                if (StringUtil.isEmpty((String)var1_1)) {
                    throw new IllegalArgumentException("Empty attribute names are not allowed");
                }
            }
            catch (IllegalStateException v1) {
                throw BuildPromotionImpl.a(v1);
            }
            try {
                if (var1_1.length() > 70) {
                    throw new IllegalArgumentException("Attribute name must not exceed 70 characters");
                }
            }
            catch (IllegalStateException v2) {
                throw BuildPromotionImpl.a(v2);
            }
            if (!(var2_2 instanceof String)) break block28;
            try {
                block33: {
                    if (((String)var2_2).length() <= 1000) break block28;
                    break block33;
                    catch (IllegalStateException v3) {
                        throw BuildPromotionImpl.a(v3);
                    }
                }
                throw new IllegalArgumentException("Attribute value must not exceed 1000 characters");
            }
            catch (IllegalStateException v4) {
                throw BuildPromotionImpl.a(v4);
            }
        }
        var3_3 = this;
        synchronized (var3_3) {
            block32: {
                block31: {
                    block29: {
                        block30: {
                            block36: {
                                block35: {
                                    block34: {
                                        var4_4 = this.myAttributes.get();
                                        if (var2_2 == null) ** GOTO lbl42
                                        if (var2_2.equals(var4_4.get((Object)var1_1))) ** GOTO lbl54
                                        break block34;
                                        catch (IllegalStateException v5) {
                                            throw BuildPromotionImpl.a(v5);
                                        }
                                    }
                                    if (var2_2 != null) break block29;
                                    break block35;
                                    catch (IllegalStateException v6) {
                                        throw BuildPromotionImpl.a(v6);
                                    }
                                }
                                if (var4_4.containsKey((Object)var1_1)) break block29;
                                break block36;
                                catch (IllegalStateException v7) {
                                    throw BuildPromotionImpl.a(v7);
                                }
                            }
                            try {
                                block37: {
                                    if (!BuildPromotionImpl.LOG.isDebugEnabled()) break block30;
                                    break block37;
                                    catch (IllegalStateException v8) {
                                        throw BuildPromotionImpl.a(v8);
                                    }
                                }
                                BuildPromotionImpl.LOG.debug("Attempt to set the same attribute value for the build promotion with id " + this.myId + " is ignored, attr name: " + var1_1 + ", value: " + String.valueOf(var2_2));
                            }
                            catch (IllegalStateException v9) {
                                throw BuildPromotionImpl.a(v9);
                            }
                        }
                        return;
                    }
                    var5_5 = var4_4.clone();
                    try {
                        if (var2_2 != null) break block31;
                        var5_5.remove((Object)var1_1);
                        break block32;
                    }
                    catch (IllegalStateException v10) {
                        throw BuildPromotionImpl.a(v10);
                    }
                }
                var5_5.put((Object)StringPool.getFromPool((String)var1_1), BuildPromotionImpl.pooledAttrValue(var2_2));
            }
            this.myAttributes.set((THashMap<String, Object>)var5_5);
            this.myNotPersistedAttributes.add(var1_1);
        }
        this.markDirty(16);
    }

    @NotNull
    public Map<String, Object> getAttributes() {
        THashMap tHashMap;
        try {
            tHashMap = this.myAttributes.get().clone();
            if (tHashMap == null) {
                BuildPromotionImpl.$$$reportNull$$$0(91);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return tHashMap;
    }

    @Nullable
    public Object getAttribute(@NotNull String string) {
        try {
            if (string == null) {
                BuildPromotionImpl.$$$reportNull$$$0(92);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return this.myAttributes.get().get((Object)string);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Nullable
    private Map<VcsRootInstance, VcsRootInstance> getCustomOverriddenRoots() {
        this.loadOverridenRootsIfNeeded();
        BuildPromotionImpl buildPromotionImpl = this;
        synchronized (buildPromotionImpl) {
            if (this.myOverridenVcsRoots == null || this.myOverridenVcsRoots.isEmpty()) {
                return null;
            }
            return new HashMap<VcsRootInstance, VcsRootInstance>(this.myOverridenVcsRoots);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     */
    private void loadOverridenRootsIfNeeded() {
        Object object = this;
        synchronized (object) {
            if (this.myOverridenVcsRoots != null) {
                return;
            }
        }
        object = (Map)GET_OVERRIDEN_ROOTS.execute(this.getContext().getSQLRunner(), new Object[]{this.myId});
        HashMap<VcsRootInstanceEx, VcsRootInstanceEx> hashMap = new HashMap<VcsRootInstanceEx, VcsRootInstanceEx>(object.size());
        for (Map.Entry entry : object.entrySet()) {
            VcsRootInstanceEx vcsRootInstanceEx = this.findVcsRootInstanceById((Long)entry.getKey());
            VcsRootInstanceEx vcsRootInstanceEx2 = this.findVcsRootInstanceById((Long)entry.getValue());
            if (vcsRootInstanceEx == null) continue;
            try {
                block12: {
                    if (vcsRootInstanceEx2 == null) continue;
                    break block12;
                    catch (IllegalStateException illegalStateException) {
                        throw BuildPromotionImpl.a(illegalStateException);
                    }
                }
                hashMap.put(vcsRootInstanceEx, vcsRootInstanceEx2);
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
        }
        Object object2 = this;
        synchronized (object2) {
            this.myOverridenVcsRoots = hashMap.isEmpty() ? Collections.emptyMap() : hashMap;
        }
    }

    public String getProjectId() {
        String string;
        BuildTypeImpl buildTypeImpl = this.findBuildTypeInProjectsModel();
        try {
            string = buildTypeImpl == null ? null : buildTypeImpl.getProjectId();
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return string;
    }

    public String getProjectExternalId() {
        String string;
        BuildTypeImpl buildTypeImpl = this.findBuildTypeInProjectsModel();
        try {
            string = buildTypeImpl == null ? null : buildTypeImpl.getProjectExternalId();
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return string;
    }

    @Nullable
    private BuildTypeImpl findBuildTypeInProjectsModel() {
        return (BuildTypeImpl)this.getContext().getProjectManager().getProjectDataModel().getBuildTypes().find((Object)this.getParentBuildTypeId());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Unable to fully structure code
     */
    public void setAssociatedBuild(SBuild var1_1) {
        block23: {
            block26: {
                if (!TeamCityProperties.getBooleanOrTrue((String)"teamcity.promotion.resetLoadedParametersOnSettingAssociatedBuild.enabled")) break block23;
                if (!CurrentNodeInfo.isSecondaryNode()) break block23;
                break block26;
                catch (IllegalStateException v0) {
                    throw BuildPromotionImpl.a(v0);
                }
            }
            try {
                block27: {
                    if ((this.myDirtyState & 1) != 0) break block23;
                    break block27;
                    catch (IllegalStateException v1) {
                        throw BuildPromotionImpl.a(v1);
                    }
                }
                this.myUserParameters.resetLoadedParameters();
            }
            catch (IllegalStateException v2) {
                throw BuildPromotionImpl.a(v2);
            }
        }
        var2_2 = this.myAssociatedBuildIdLock;
        synchronized (var2_2) {
            block25: {
                block31: {
                    block30: {
                        block24: {
                            block28: {
                                var3_3 = var1_1 == null ? null : Long.valueOf(var1_1.getBuildId());
                                if (var3_3 == null) break block24;
                                if (this.myAssociatedBuildId == null) break block24;
                                break block28;
                                catch (IllegalStateException v3) {
                                    throw BuildPromotionImpl.a(v3);
                                }
                            }
                            try {
                                block29: {
                                    if (var3_3.equals(this.myAssociatedBuildId)) break block24;
                                    break block29;
                                    catch (IllegalStateException v4) {
                                        throw BuildPromotionImpl.a(v4);
                                    }
                                }
                                throw new RuntimeException("Build promotion " + String.valueOf((Object)this) + " cannot be associated with build " + var1_1.getBuildId() + ", because it is already associated with build: " + this.myAssociatedBuildId);
                            }
                            catch (IllegalStateException v5) {
                                throw BuildPromotionImpl.a(v5);
                            }
                        }
                        if (var3_3 != null) ** GOTO lbl49
                        if (this.myAssociatedBuildId != null) ** GOTO lbl61
                        break block30;
                        catch (IllegalStateException v6) {
                            throw BuildPromotionImpl.a(v6);
                        }
                    }
                    if (var3_3 == null) break block25;
                    break block31;
                    catch (IllegalStateException v7) {
                        throw BuildPromotionImpl.a(v7);
                    }
                }
                try {
                    block32: {
                        if (this.myAssociatedBuildId != null) break block25;
                        break block32;
                        catch (IllegalStateException v8) {
                            throw BuildPromotionImpl.a(v8);
                        }
                    }
                    Loggers.SERVER.debug("Associating build promotion " + this.myId + " with build " + var3_3);
                    this.createPromotionInDbIfNeeded();
                    this.storeBuildId(var3_3);
                }
                catch (IllegalStateException v9) {
                    throw BuildPromotionImpl.a(v9);
                }
            }
        }
    }

    private void storeBuildId(Long l) {
        this.getContext().getSQLRunner().runAndRetryNoResult(dBFunctions -> {
            block5: {
                String string;
                block4: {
                    try {
                        if (dBFunctions.executeDml("update build_state set build_id = ? where id = ? and is_deleted = 0", new Object[]{l, this.myId}) <= 0) break block4;
                        this.myAssociatedBuildId = l;
                        break block5;
                    }
                    catch (DBException dBException) {
                        throw BuildPromotionImpl.a(dBException);
                    }
                }
                boolean bl = this.existsInDb(dBFunctions);
                try {
                    string = bl ? "the build promotion is marked as deleted" : "the build promotion does not exist in the database";
                }
                catch (DBException dBException) {
                    throw BuildPromotionImpl.a(dBException);
                }
                String string2 = string;
                LOG.warn("Failed to associate build promotion " + LogUtil.describe((BuildPromotion)this) + " with build with id " + l + ", reason: " + string2);
                throw new BuildPromotionDeletedException("Failed to associate build with promotion " + this.myId + ", reason: " + string2);
            }
        });
    }

    private boolean existsInDb(@NotNull DBFunctions dBFunctions) {
        try {
            if (dBFunctions == null) {
                BuildPromotionImpl.$$$reportNull$$$0(93);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return dBFunctions.queryForSingleBoolean("select 1 from build_state where id = ?", false, new Object[]{this.myId});
    }

    public void addDependency(@NotNull BuildPromotionEx buildPromotionEx2, @NotNull DependencyOptions dependencyOptions) throws IllegalArgumentException {
        try {
            if (buildPromotionEx2 == null) {
                BuildPromotionImpl.$$$reportNull$$$0(94);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildPromotionImpl.a(illegalArgumentException);
        }
        try {
            if (dependencyOptions == null) {
                BuildPromotionImpl.$$$reportNull$$$0(95);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildPromotionImpl.a(illegalArgumentException);
        }
        try {
            if (this.dependenciesShouldNotBeChanged()) {
                LOG.warn("Attempt to add a dependency to a started build: " + this.getId() + ", new dependency: " + LogUtil.describe((BuildPromotion)buildPromotionEx2), (Throwable)new RuntimeException());
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildPromotionImpl.a(illegalArgumentException);
        }
        DependencyCheckResult dependencyCheckResult = this.checkDependency(buildPromotionEx2);
        try {
            if (dependencyCheckResult == DependencyCheckResult.ORGANIZATIONS_VIOLATION) {
                throw new OrganizationProjectAccessViolationException(String.format("Build configuration \"%s\" has a prohibited snapshot dependency", this.getBuildType()));
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildPromotionImpl.a(illegalArgumentException);
        }
        if (dependencyCheckResult == DependencyCheckResult.UNTRUSTED) {
            String string = "inaccessible_external_dependency_" + buildPromotionEx2.getProjectId() + "_" + buildPromotionEx2.getId();
            this.addBuildProblem(BuildProblemData.createBuildProblem((String)string, (String)"INACCESSIBLE_EXTERNAL_DEPENDENCY", (String)"Attempt to add a dependency on a build configuration which belongs to an inaccessible project. Please refer to teamcity-server.log for details"));
            Loggers.SERVER.info(String.format("Attempt to add a dependency in %s on a build configuration %s which belongs to an inaccessible project", LogUtil.describe((BuildPromotion)this), LogUtil.describe((BuildTypeEx)buildPromotionEx2.getBuildType())));
            buildPromotionEx2.traverseSelfAndDependencies(buildPromotionEx -> {
                try {
                    if (buildPromotionEx.getAttribute("teamcity.build.untrustedDependency") != null) {
                        return DependencyConsumer.Result.SKIP;
                    }
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
                try {
                    if (buildPromotionEx.getAssociatedBuild() != null) {
                        return DependencyConsumer.Result.CONTINUE;
                    }
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
                buildPromotionEx.setAttribute("teamcity.build.untrustedDependency", (Object)Boolean.TRUE);
                return DependencyConsumer.Result.CONTINUE;
            });
            return;
        }
        this.myDependencies.addDependency(buildPromotionEx2, dependencyOptions);
        this.resetAllCompatibilityCaches();
    }

    @NotNull
    private DependencyCheckResult checkDependency(@NotNull BuildPromotionEx buildPromotionEx) {
        DependencyCheckResult dependencyCheckResult;
        BuildTypeEx buildTypeEx;
        BuildTypeEx buildTypeEx2;
        block10: {
            try {
                if (buildPromotionEx == null) {
                    BuildPromotionImpl.$$$reportNull$$$0(96);
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
            buildTypeEx2 = this.getBuildType();
            buildTypeEx = buildPromotionEx.getBuildType();
            try {
                try {
                    if (buildTypeEx2 != null && buildTypeEx != null) break block10;
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
                throw new RuntimeException("Cannot find build types for build promotions \"" + String.valueOf(buildPromotionEx) + "\" and \"" + String.valueOf((Object)this) + "\"");
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
        }
        try {
            dependencyCheckResult = this.getContext().getDependencyChecker().checkDependency((SProject)buildTypeEx.getProject(), (SProject)buildTypeEx2.getProject(), DependencyType.SNAPSHOT);
            if (dependencyCheckResult == null) {
                BuildPromotionImpl.$$$reportNull$$$0(97);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return dependencyCheckResult;
    }

    public boolean isUsed() {
        boolean bl;
        block5: {
            block4: {
                try {
                    try {
                        if (this.getAssociatedBuildId() == null && this.getQueuedBuild() == null) break block4;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildPromotionImpl.a(illegalStateException);
                    }
                    bl = true;
                    break block5;
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
            }
            bl = false;
        }
        return bl;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Could not resolve type clashes
     * Loose catch block
     */
    @NotNull
    public BuildPromotionEx copy(boolean bl) {
        BuildPromotionImpl buildPromotionImpl;
        Object object4;
        BuildPromotionImpl buildPromotionImpl2;
        block34: {
            Object object22;
            BuildTypeEx buildTypeEx = this.getBuildTypeSafe();
            buildPromotionImpl2 = (BuildPromotionImpl)this.getBuildPromotionManager().createNewPromotion((SBuildType)buildTypeEx);
            buildPromotionImpl2.setCustomParameters(this.getCustomParameters());
            this.loadOverridenRootsIfNeeded();
            Object object3 = this;
            synchronized (object3) {
                if (!this.myOverridenVcsRoots.isEmpty()) {
                    buildPromotionImpl2.setOverridenVcsRoots(this.myOverridenVcsRoots);
                }
            }
            for (Object object22 : this.getAttributes().entrySet()) {
                block32: {
                    try {
                        if (NON_COPYABLE_ATTRIBUTES.contains(object22.getKey())) {
                            continue;
                        }
                    }
                    catch (IOException iOException) {
                        throw BuildPromotionImpl.a(iOException);
                    }
                    if (!((String)object22.getKey()).equals("teamcity.build.finalBranchName")) break block32;
                    try {
                        if (!bl) {
                            continue;
                        }
                        break block32;
                        catch (IOException iOException) {
                            throw BuildPromotionImpl.a(iOException);
                        }
                    }
                    catch (IOException iOException) {
                        throw BuildPromotionImpl.a(iOException);
                    }
                }
                buildPromotionImpl2.setAttribute((String)object22.getKey(), object22.getValue());
            }
            object3 = this.getAttribute("teamcity.build.hasFrozenSettings");
            if (this.hasFrozenSettings(object3)) {
                try {
                    try {
                        buildPromotionImpl2.freezeSettings(this.getFrozenSettingsFile(), object3.toString());
                        if (this.getAttribute("teamcity.build.freezeSettings.forcePostprocessing") == null) {
                            buildPromotionImpl2.setAttribute("teamcity.build.freezeSettings.forcePostprocessing", null);
                        }
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildPromotionImpl.a(illegalStateException);
                    }
                }
                catch (IOException iOException) {
                    LOG.warnAndDebugDetails("Failed to copy build settings from " + LogUtil.describe((BuildPromotion)this) + " to " + LogUtil.describe((BuildPromotion)buildPromotionImpl2) + ", will use current configuration settings", (Throwable)iOException);
                }
            }
            buildPromotionImpl2.setAttribute("teamcity.build.creatorNodeId", CurrentNodeInfo.getNodeId());
            for (Object object4 : this.getDependencies()) {
                buildPromotionImpl2.addDependency(object4.getDependOn(), (DependencyOptions)object4);
            }
            object22 = this.getLastModificationId();
            try {
                try {
                    if (!bl || this.isChangeCollectingNeeded()) break block34;
                }
                catch (IOException iOException) {
                    throw BuildPromotionImpl.a(iOException);
                }
                if (object22 == null) break block34;
            }
            catch (IOException iOException) {
                throw BuildPromotionImpl.a(iOException);
            }
            object4 = this.getChainModificationId();
            if (object4 == null) {
                object4 = object22;
                for (BuildPromotionEx buildPromotionEx : this.getAllDependencies()) {
                    Long l = buildPromotionEx.getLastModificationId();
                    if (l == null) continue;
                    object4 = Math.max((Long)object4, l);
                }
            }
            buildPromotionImpl2.setBuildRevisions(this.getRevisionsInternal(), (Long)object22, (Long)object4, false);
        }
        object4 = this.getCustomArtifactDependencies();
        try {
            if (!object4.isEmpty()) {
                buildPromotionImpl2.setCustomArtifactDependencies((List<SArtifactDependency>)object4);
            }
        }
        catch (IOException iOException) {
            throw BuildPromotionImpl.a(iOException);
        }
        try {
            buildPromotionImpl = buildPromotionImpl2;
            if (buildPromotionImpl == null) {
                BuildPromotionImpl.$$$reportNull$$$0(98);
            }
        }
        catch (IOException iOException) {
            throw BuildPromotionImpl.a(iOException);
        }
        return buildPromotionImpl;
    }

    public boolean isOutOfChangesSequence() {
        block27: {
            BuildTypeEx buildTypeEx;
            block24: {
                Boolean bl = this.myOutOfChangesSequence.get();
                try {
                    if (bl != null) {
                        return bl;
                    }
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
                buildTypeEx = this.getBuildType();
                try {
                    if (buildTypeEx == null) {
                        return false;
                    }
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
                try {
                    try {
                        if (!this.isCanceled() || !this.isChangeCollectingNeeded()) break block24;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildPromotionImpl.a(illegalStateException);
                    }
                    return false;
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
            }
            BuildTypeOrderedBuilds buildTypeOrderedBuilds = buildTypeEx.getBuildTypeOrderedBuilds();
            SBuild sBuild = this.getRealOrDummyBuild();
            List list = buildTypeOrderedBuilds.getBuildsAfter(sBuild);
            long l = this.getStartedTimestampOrFuture();
            for (OrderedBuild orderedBuild : list) {
                boolean bl;
                block26: {
                    block25: {
                        try {
                            if (orderedBuild.isDetachedChanges()) {
                                continue;
                            }
                        }
                        catch (IllegalStateException illegalStateException) {
                            throw BuildPromotionImpl.a(illegalStateException);
                        }
                        try {
                            try {
                                try {
                                    if (orderedBuild.getStartTimestamp() <= 0L || orderedBuild.getStartTimestamp() >= l) continue;
                                }
                                catch (IllegalStateException illegalStateException) {
                                    throw BuildPromotionImpl.a(illegalStateException);
                                }
                                if (this.isFailedToCollectChanges()) break block25;
                            }
                            catch (IllegalStateException illegalStateException) {
                                throw BuildPromotionImpl.a(illegalStateException);
                            }
                            bl = true;
                            break block26;
                        }
                        catch (IllegalStateException illegalStateException) {
                            throw BuildPromotionImpl.a(illegalStateException);
                        }
                    }
                    bl = false;
                }
                return bl;
            }
            try {
                try {
                    if (sBuild.getBuildId() <= 0L || this.isChangeCollectingNeeded()) break block27;
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
                this.myOutOfChangesSequence.compareAndSet(null, false);
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
        }
        return false;
    }

    public boolean isPartOfBuildChain() {
        boolean bl;
        block5: {
            block4: {
                try {
                    try {
                        if (!this.myDependencies.hasDependencies() && !this.getPipelineView().isPartOfPipeline()) break block4;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildPromotionImpl.a(illegalStateException);
                    }
                    bl = true;
                    break block5;
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
            }
            bl = false;
        }
        return bl;
    }

    @NotNull
    public Collection<BuildDependencyEx> getDependencies() {
        Collection collection;
        try {
            collection = this.myDependencies.getDependencies();
            if (collection == null) {
                BuildPromotionImpl.$$$reportNull$$$0(99);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return collection;
    }

    @NotNull
    public long[] getDependedOnMeIds() {
        long[] lArray;
        try {
            lArray = this.myDependencies.getDependedOnMeIds();
            if (lArray == null) {
                BuildPromotionImpl.$$$reportNull$$$0(100);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return lArray;
    }

    @NotNull
    public long[] getDependenciesIds() {
        long[] lArray;
        try {
            lArray = this.myDependencies.getDependenciesIds();
            if (lArray == null) {
                BuildPromotionImpl.$$$reportNull$$$0(101);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return lArray;
    }

    public boolean hasDependencyOn(long l) {
        return this.myDependencies.hasDependencyOn(l);
    }

    public int getNumberOfDependencies() {
        return this.myDependencies.getNumberOfDependencies();
    }

    public BuildPromotionEx[] findTops() {
        List<BuildPromotionEx> list = this.collectTopPromotions();
        list.sort((buildPromotion, buildPromotion2) -> (int)(buildPromotion2.getId() - buildPromotion.getId()));
        return list.toArray(new BuildPromotionEx[0]);
    }

    @NotNull
    public Collection<BuildDependencyEx> getDependedOnMe() {
        Collection collection;
        try {
            collection = this.myDependencies.getDependedOnMe();
            if (collection == null) {
                BuildPromotionImpl.$$$reportNull$$$0(102);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return collection;
    }

    public void traverseDependedOnMe(@NotNull DependencyConsumer<BuildPromotionEx> dependencyConsumer) {
        try {
            if (dependencyConsumer == null) {
                BuildPromotionImpl.$$$reportNull$$$0(103);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        this.traverseSelfAndDependedOnMe((DependencyConsumer<BuildPromotionEx>)new /* Unavailable Anonymous Inner Class!! */);
    }

    public void traverseSelfAndDependedOnMe(@NotNull DependencyConsumer<BuildPromotionEx> dependencyConsumer) {
        try {
            if (dependencyConsumer == null) {
                BuildPromotionImpl.$$$reportNull$$$0(104);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        this.visitSelfAndDependencies(dependencyConsumer, (DepsSupplier)new /* Unavailable Anonymous Inner Class!! */);
    }

    private void visitSelfAndDependencies(@NotNull DependencyConsumer<BuildPromotionEx> dependencyConsumer, @NotNull DepsSupplier depsSupplier) {
        try {
            if (dependencyConsumer == null) {
                BuildPromotionImpl.$$$reportNull$$$0(105);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        try {
            if (depsSupplier == null) {
                BuildPromotionImpl.$$$reportNull$$$0(106);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        LinkedList<BuildPromotionImpl> linkedList = new LinkedList<BuildPromotionImpl>();
        linkedList.add(this);
        BuildPromotionManagerEx buildPromotionManagerEx = this.getBuildPromotionManager();
        HashSet<BuildPromotionEx> hashSet = new HashSet<BuildPromotionEx>();
        while (!linkedList.isEmpty()) {
            HashSet hashSet2;
            BuildPromotionEx buildPromotionEx2 = (BuildPromotionEx)linkedList.pop();
            try {
                if (!hashSet.add(buildPromotionEx2)) {
                    continue;
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
            DependencyConsumer.Result result = dependencyConsumer.consume((Object)buildPromotionEx2);
            try {
                if (result == DependencyConsumer.Result.STOP) {
                    return;
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
            try {
                if (result == DependencyConsumer.Result.SKIP) {
                    continue;
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
            try {
                hashSet2 = result == DependencyConsumer.Result.CONTINUE_PREFETCH ? new HashSet() : null;
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
            HashSet hashSet3 = hashSet2;
            boolean[] blArray = new boolean[1];
            try {
                depsSupplier.getDepsStream(buildPromotionEx2).forEach(buildPromotionEx -> {
                    linkedList.add((BuildPromotionImpl)((Object)buildPromotionEx));
                    blArray[0] = true;
                    if (hashSet3 != null) {
                        for (long l : depsSupplier.getDepsIds(buildPromotionEx)) {
                            hashSet3.add(l);
                        }
                    }
                });
                if (!blArray[0]) {
                    dependencyConsumer.endpoint((Object)buildPromotionEx2);
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
            try {
                try {
                    if (hashSet3 == null || hashSet3.isEmpty()) continue;
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
                buildPromotionManagerEx.findPromotionsByIds(hashSet3, new BuildPromotionManager.BuildPromotionInitFromDbHint[]{BuildPromotionManager.BuildPromotionInitFromDbHint.PREFETCH_MINIMAL});
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
        }
    }

    @NotNull
    public Predicate<BuildPromotion> createBuildPromotionAccessChecker() {
        Predicate<BuildPromotion> predicate;
        boolean bl = this.getContext().getSecurityContext().isSystemAccess();
        HashSet hashSet = new HashSet();
        try {
            predicate = buildPromotion -> {
                block10: {
                    try {
                        if (bl) {
                            return true;
                        }
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildPromotionImpl.a(illegalStateException);
                    }
                    try {
                        if (hashSet.contains(buildPromotion.getParentBuildTypeId())) {
                            return true;
                        }
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildPromotionImpl.a(illegalStateException);
                    }
                    String string = buildPromotion.getProjectId();
                    try {
                        try {
                            if (string == null || !AuthUtil.hasReadAccessTo((AuthorityHolder)this.getContext().getSecurityContext().getAuthorityHolder(), (String)string)) break block10;
                        }
                        catch (IllegalStateException illegalStateException) {
                            throw BuildPromotionImpl.a(illegalStateException);
                        }
                        hashSet.add(buildPromotion.getParentBuildTypeId());
                        return true;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildPromotionImpl.a(illegalStateException);
                    }
                }
                return false;
            };
            if (predicate == null) {
                BuildPromotionImpl.$$$reportNull$$$0(107);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return predicate;
    }

    public void traverseDependencies(@NotNull DependencyConsumer<BuildPromotionEx> dependencyConsumer) {
        try {
            if (dependencyConsumer == null) {
                BuildPromotionImpl.$$$reportNull$$$0(108);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        this.traverseSelfAndDependencies((DependencyConsumer<BuildPromotionEx>)new /* Unavailable Anonymous Inner Class!! */);
    }

    public void traverseSelfAndDependencies(@NotNull DependencyConsumer<BuildPromotionEx> dependencyConsumer) {
        try {
            if (dependencyConsumer == null) {
                BuildPromotionImpl.$$$reportNull$$$0(109);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        this.visitSelfAndDependencies(dependencyConsumer, (DepsSupplier)new /* Unavailable Anonymous Inner Class!! */);
    }

    public int getNumberOfDependedOnMe() {
        return this.myDependencies.getNumberOfDependedOnMe();
    }

    public void removeDependency(@NotNull BuildPromotionEx buildPromotionEx) {
        try {
            if (buildPromotionEx == null) {
                BuildPromotionImpl.$$$reportNull$$$0(110);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        try {
            if (this.dependenciesShouldNotBeChanged()) {
                LOG.warn("Attempt to remove a dependency of a started build: " + this.getId() + ", dependency: " + LogUtil.describe((BuildPromotion)buildPromotionEx), (Throwable)new RuntimeException());
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        this.myDependencies.removeDependency(buildPromotionEx);
        this.resetAllCompatibilityCaches();
    }

    public void replaceDependency(@NotNull BuildPromotionEx buildPromotionEx, @NotNull BuildPromotionEx buildPromotionEx2, @NotNull DependencyOptions dependencyOptions) {
        block16: {
            try {
                if (buildPromotionEx == null) {
                    BuildPromotionImpl.$$$reportNull$$$0(111);
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
            try {
                if (buildPromotionEx2 == null) {
                    BuildPromotionImpl.$$$reportNull$$$0(112);
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
            try {
                if (dependencyOptions == null) {
                    BuildPromotionImpl.$$$reportNull$$$0(113);
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
            try {
                if (this.dependenciesShouldNotBeChanged()) {
                    LOG.warn("Attempt to replace a dependency of a started build: " + this.getId() + ", new dependency: " + LogUtil.describe((BuildPromotion)buildPromotionEx2) + ", previous dependency: " + LogUtil.describe((BuildPromotion)buildPromotionEx), (Throwable)new RuntimeException());
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
            try {
                try {
                    if (!TeamCityProperties.getBooleanOrTrue((String)"teamcity.isolatedBuildConfigurations.enabled") || buildPromotionEx2.getParentBuildTypeId().equals(buildPromotionEx.getParentBuildTypeId())) break block16;
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
                LOG.warn("Attempt to replace dependency for a new one, which belongs to a different build configuration. New dependency configuration: " + LogUtil.describe((BuildPromotion)buildPromotionEx2) + ", original dependency configuration: " + LogUtil.describe((BuildPromotion)buildPromotionEx), (Throwable)new RuntimeException());
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
        }
        this.myDependencies.replaceDependency(buildPromotionEx, buildPromotionEx2, dependencyOptions);
        this.resetAllCompatibilityCaches();
    }

    private boolean dependenciesShouldNotBeChanged() {
        boolean bl;
        block7: {
            block6: {
                try {
                    try {
                        try {
                            if (this.isCompositeBuild() || this.myAssociatedBuildId == null) break block6;
                        }
                        catch (IllegalStateException illegalStateException) {
                            throw BuildPromotionImpl.a(illegalStateException);
                        }
                        if (!TeamCityProperties.getBooleanOrTrue((String)"teamcity.promotion.dependencies.reportInvalidChangesAttempts")) break block6;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildPromotionImpl.a(illegalStateException);
                    }
                    bl = true;
                    break block7;
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
            }
            bl = false;
        }
        return bl;
    }

    @Nullable
    public SQueuedBuild addToQueue(@NotNull String string) {
        SQueuedBuild sQueuedBuild;
        try {
            if (string == null) {
                BuildPromotionImpl.$$$reportNull$$$0(114);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        BuildTypeEx buildTypeEx = this.getBuildType();
        try {
            sQueuedBuild = buildTypeEx != null ? buildTypeEx.addToQueue((BuildPromotionEx)this, string) : null;
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return sQueuedBuild;
    }

    @Nullable
    public SQueuedBuild addToQueue(@NotNull SBuildAgent sBuildAgent, @NotNull String string) {
        SQueuedBuild sQueuedBuild;
        try {
            if (sBuildAgent == null) {
                BuildPromotionImpl.$$$reportNull$$$0(115);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        try {
            if (string == null) {
                BuildPromotionImpl.$$$reportNull$$$0(116);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        BuildTypeEx buildTypeEx = this.getBuildType();
        AgentRestrictorFactory agentRestrictorFactory = this.getContext().getAgentRestrictorFactory();
        try {
            sQueuedBuild = buildTypeEx != null ? buildTypeEx.addToQueue(agentRestrictorFactory.createFor(AgentRestrictorType.SINGLE_AGENT, sBuildAgent.getId()), (BuildPromotionEx)this, string) : null;
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return sQueuedBuild;
    }

    @Nullable
    public BuildTypeEx getBuildType() {
        BuildTypeEx buildTypeEx = this.getParentBuildType();
        try {
            if (buildTypeEx == null) {
                return null;
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        try {
            if (!this.myPersonal) {
                return buildTypeEx;
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return this.getContext().getBuildTypeFactory().createRemoteBuildType(buildTypeEx, this.myPersonalChangeId.longValue(), this.myOwnerId.longValue());
    }

    @Nullable
    public SUser getOwner() {
        try {
            if (this.myOwnerId != null) {
                return this.findUser(this.myOwnerId);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return null;
    }

    private SUser findUser(long l) {
        return this.getContext().getUserModel().findUserById(l);
    }

    @NotNull
    public String getBuildTypeId() {
        String string;
        block7: {
            String string2;
            block8: {
                try {
                    try {
                        if (this.isPersonal()) break block7;
                        string2 = this.getParentBuildTypeId();
                        if (string2 != null) break block8;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildPromotionImpl.a(illegalStateException);
                    }
                    BuildPromotionImpl.$$$reportNull$$$0(117);
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
            }
            return string2;
        }
        try {
            string = RemoteBuildTypeIdUtil.getRemoteBuildTypeId((String)this.getParentBuildTypeId(), (long)this.myPersonalChangeId);
            if (string == null) {
                BuildPromotionImpl.$$$reportNull$$$0(118);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return string;
    }

    @NotNull
    public String getParentBuildTypeId() {
        String string;
        try {
            string = this.myBuildTypeId;
            if (string == null) {
                BuildPromotionImpl.$$$reportNull$$$0(119);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return string;
    }

    @NotNull
    public String getBuildTypeExternalId() {
        return BuildPromotionImpl.getBuildTypeExternalId(this.getParentBuildTypeId(), this.getContext().getBuildTypeIdentityIdentifiersManager());
    }

    @NotNull
    public static String getBuildTypeExternalId(@NotNull String string, @NotNull BuildTypeIdentifiersManager buildTypeIdentifiersManager) {
        String string2;
        String string3;
        try {
            if (string == null) {
                BuildPromotionImpl.$$$reportNull$$$0(120);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        try {
            if (buildTypeIdentifiersManager == null) {
                BuildPromotionImpl.$$$reportNull$$$0(121);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        String string4 = buildTypeIdentifiersManager.internalToExternal((Object)string);
        try {
            string3 = string4 == null ? "_NotExisting_" : string4;
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        try {
            string2 = string3;
            if (string3 == null) {
                BuildPromotionImpl.$$$reportNull$$$0(122);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return string2;
    }

    @Nullable
    public BuildTypeEx getParentBuildType() {
        return this.getContext().getProjectManager().findBuildTypeById(this.getParentBuildTypeId());
    }

    public boolean isEquivalentTo(@NotNull BuildPromotion buildPromotion) {
        try {
            if (buildPromotion == null) {
                BuildPromotionImpl.$$$reportNull$$$0(123);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return this.isEquivalentTo(buildPromotion, this.shouldCompareBranches()).isEquivalent();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     */
    @NotNull
    public EquivalenceCheckResult isEquivalentTo(@NotNull BuildPromotion buildPromotion, @Nullable Boolean bl) {
        EquivalenceCheckResult equivalenceCheckResult;
        EquivalenceCheckResult equivalenceCheckResult2;
        block57: {
            BuildPromotionImpl buildPromotionImpl;
            EquivalenceCheckResult equivalenceCheckResult3;
            block55: {
                EquivalenceCheckResult equivalenceCheckResult4;
                EquivalenceCheckResult equivalenceCheckResult5;
                block56: {
                    block53: {
                        EquivalenceCheckResult equivalenceCheckResult6;
                        EquivalenceCheckResult equivalenceCheckResult7;
                        block54: {
                            block51: {
                                EquivalenceCheckResult equivalenceCheckResult8;
                                EquivalenceCheckResult equivalenceCheckResult9;
                                block52: {
                                    block49: {
                                        EquivalenceCheckResult equivalenceCheckResult10;
                                        EquivalenceCheckResult equivalenceCheckResult11;
                                        block50: {
                                            try {
                                                if (buildPromotion == null) {
                                                    BuildPromotionImpl.$$$reportNull$$$0(124);
                                                }
                                            }
                                            catch (IllegalStateException illegalStateException) {
                                                throw BuildPromotionImpl.a(illegalStateException);
                                            }
                                            if (bl == null) {
                                                bl = this.shouldCompareBranches();
                                            }
                                            equivalenceCheckResult3 = EquivalenceCheckResult.equivalent();
                                            try {
                                                buildPromotionImpl = (BuildPromotionImpl)buildPromotion;
                                                String string = buildPromotionImpl.getParentBuildTypeId();
                                                if (this.getParentBuildTypeId().equals(string)) break block49;
                                                equivalenceCheckResult11 = equivalenceCheckResult3 = EquivalenceCheckResult.nonEquivalent((String)("different build configurations: " + this.getBuildTypeId() + " != " + string));
                                            }
                                            catch (Throwable throwable) {
                                                block58: {
                                                    try {
                                                        try {
                                                            if (equivalenceCheckResult3.isEquivalent() || !LOG.isDebugEnabled()) break block58;
                                                        }
                                                        catch (IllegalStateException illegalStateException) {
                                                            throw BuildPromotionImpl.a(illegalStateException);
                                                        }
                                                        LOG.debug(this.equivalenceLoggingPrefix(buildPromotion.getId()) + equivalenceCheckResult3.getReason());
                                                    }
                                                    catch (IllegalStateException illegalStateException) {
                                                        throw BuildPromotionImpl.a(illegalStateException);
                                                    }
                                                }
                                                throw throwable;
                                            }
                                            if (equivalenceCheckResult3.isEquivalent()) break block50;
                                            try {
                                                block59: {
                                                    if (!LOG.isDebugEnabled()) break block50;
                                                    break block59;
                                                    catch (IllegalStateException illegalStateException) {
                                                        throw BuildPromotionImpl.a(illegalStateException);
                                                    }
                                                }
                                                LOG.debug(this.equivalenceLoggingPrefix(buildPromotion.getId()) + equivalenceCheckResult3.getReason());
                                            }
                                            catch (IllegalStateException illegalStateException) {
                                                throw BuildPromotionImpl.a(illegalStateException);
                                            }
                                        }
                                        try {
                                            equivalenceCheckResult10 = equivalenceCheckResult11;
                                            if (equivalenceCheckResult10 == null) {
                                                BuildPromotionImpl.$$$reportNull$$$0(125);
                                            }
                                        }
                                        catch (IllegalStateException illegalStateException) {
                                            throw BuildPromotionImpl.a(illegalStateException);
                                        }
                                        return equivalenceCheckResult10;
                                    }
                                    equivalenceCheckResult3 = this.sameRevisions(buildPromotionImpl);
                                    if (equivalenceCheckResult3.isEquivalent()) break block51;
                                    equivalenceCheckResult9 = equivalenceCheckResult3;
                                    if (equivalenceCheckResult3.isEquivalent()) break block52;
                                    try {
                                        block60: {
                                            if (!LOG.isDebugEnabled()) break block52;
                                            break block60;
                                            catch (IllegalStateException illegalStateException) {
                                                throw BuildPromotionImpl.a(illegalStateException);
                                            }
                                        }
                                        LOG.debug(this.equivalenceLoggingPrefix(buildPromotion.getId()) + equivalenceCheckResult3.getReason());
                                    }
                                    catch (IllegalStateException illegalStateException) {
                                        throw BuildPromotionImpl.a(illegalStateException);
                                    }
                                }
                                try {
                                    equivalenceCheckResult8 = equivalenceCheckResult9;
                                    if (equivalenceCheckResult8 == null) {
                                        BuildPromotionImpl.$$$reportNull$$$0(126);
                                    }
                                }
                                catch (IllegalStateException illegalStateException) {
                                    throw BuildPromotionImpl.a(illegalStateException);
                                }
                                return equivalenceCheckResult8;
                            }
                            if (!bl.booleanValue() || (equivalenceCheckResult3 = this.sameBranches(buildPromotionImpl)).isEquivalent()) break block53;
                            equivalenceCheckResult7 = equivalenceCheckResult3;
                            if (equivalenceCheckResult3.isEquivalent()) break block54;
                            try {
                                block61: {
                                    if (!LOG.isDebugEnabled()) break block54;
                                    break block61;
                                    catch (IllegalStateException illegalStateException) {
                                        throw BuildPromotionImpl.a(illegalStateException);
                                    }
                                }
                                LOG.debug(this.equivalenceLoggingPrefix(buildPromotion.getId()) + equivalenceCheckResult3.getReason());
                            }
                            catch (IllegalStateException illegalStateException) {
                                throw BuildPromotionImpl.a(illegalStateException);
                            }
                        }
                        try {
                            equivalenceCheckResult6 = equivalenceCheckResult7;
                            if (equivalenceCheckResult6 == null) {
                                BuildPromotionImpl.$$$reportNull$$$0(127);
                            }
                        }
                        catch (IllegalStateException illegalStateException) {
                            throw BuildPromotionImpl.a(illegalStateException);
                        }
                        return equivalenceCheckResult6;
                    }
                    equivalenceCheckResult3 = this.sameAttributes((BuildPromotionEx)buildPromotionImpl);
                    if (equivalenceCheckResult3.isEquivalent()) break block55;
                    equivalenceCheckResult5 = equivalenceCheckResult3;
                    if (equivalenceCheckResult3.isEquivalent()) break block56;
                    try {
                        block62: {
                            if (!LOG.isDebugEnabled()) break block56;
                            break block62;
                            catch (IllegalStateException illegalStateException) {
                                throw BuildPromotionImpl.a(illegalStateException);
                            }
                        }
                        LOG.debug(this.equivalenceLoggingPrefix(buildPromotion.getId()) + equivalenceCheckResult3.getReason());
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildPromotionImpl.a(illegalStateException);
                    }
                }
                try {
                    equivalenceCheckResult4 = equivalenceCheckResult5;
                    if (equivalenceCheckResult4 == null) {
                        BuildPromotionImpl.$$$reportNull$$$0(128);
                    }
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
                return equivalenceCheckResult4;
            }
            equivalenceCheckResult2 = equivalenceCheckResult3 = this.sameDigests(buildPromotionImpl);
            if (equivalenceCheckResult3.isEquivalent()) break block57;
            try {
                block63: {
                    if (!LOG.isDebugEnabled()) break block57;
                    break block63;
                    catch (IllegalStateException illegalStateException) {
                        throw BuildPromotionImpl.a(illegalStateException);
                    }
                }
                LOG.debug(this.equivalenceLoggingPrefix(buildPromotion.getId()) + equivalenceCheckResult3.getReason());
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
        }
        try {
            equivalenceCheckResult = equivalenceCheckResult2;
            if (equivalenceCheckResult == null) {
                BuildPromotionImpl.$$$reportNull$$$0(129);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return equivalenceCheckResult;
    }

    @NotNull
    private String equivalenceLoggingPrefix(long l) {
        String string;
        try {
            string = "isEquivalentTo(" + this.getId() + ", " + l + "): ";
            if (string == null) {
                BuildPromotionImpl.$$$reportNull$$$0(130);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return string;
    }

    @NotNull
    private EquivalenceCheckResult sameRevisions(@NotNull BuildPromotionImpl buildPromotionImpl) {
        EquivalenceCheckResult equivalenceCheckResult;
        block59: {
            EquivalenceCheckResult equivalenceCheckResult2;
            block60: {
                OrderedBuild orderedBuild;
                OrderedBuild orderedBuild2;
                Map<Long, VcsRootInstance> map;
                block57: {
                    EquivalenceCheckResult equivalenceCheckResult3;
                    block58: {
                        block54: {
                            EquivalenceCheckResult equivalenceCheckResult4;
                            BuildPromotionImpl buildPromotionImpl2;
                            block56: {
                                block55: {
                                    block50: {
                                        EquivalenceCheckResult equivalenceCheckResult5;
                                        BuildPromotionImpl buildPromotionImpl3;
                                        block52: {
                                            block51: {
                                                block48: {
                                                    EquivalenceCheckResult equivalenceCheckResult6;
                                                    block49: {
                                                        block46: {
                                                            EquivalenceCheckResult equivalenceCheckResult7;
                                                            block47: {
                                                                try {
                                                                    if (buildPromotionImpl == null) {
                                                                        BuildPromotionImpl.$$$reportNull$$$0(131);
                                                                    }
                                                                }
                                                                catch (IllegalStateException illegalStateException) {
                                                                    throw BuildPromotionImpl.a(illegalStateException);
                                                                }
                                                                try {
                                                                    try {
                                                                        try {
                                                                            if (!this.isPersonal() || !this.hasCustomPatch()) break block46;
                                                                        }
                                                                        catch (IllegalStateException illegalStateException) {
                                                                            throw BuildPromotionImpl.a(illegalStateException);
                                                                        }
                                                                        equivalenceCheckResult7 = EquivalenceCheckResult.nonEquivalent((String)("build promotion " + this.getId() + " is personal and has a non empty patch"));
                                                                        if (equivalenceCheckResult7 != null) break block47;
                                                                    }
                                                                    catch (IllegalStateException illegalStateException) {
                                                                        throw BuildPromotionImpl.a(illegalStateException);
                                                                    }
                                                                    BuildPromotionImpl.$$$reportNull$$$0(132);
                                                                }
                                                                catch (IllegalStateException illegalStateException) {
                                                                    throw BuildPromotionImpl.a(illegalStateException);
                                                                }
                                                            }
                                                            return equivalenceCheckResult7;
                                                        }
                                                        map = this.vcsRootsForEquivalenceCheck();
                                                        try {
                                                            try {
                                                                if (!this.hasVcsRootsWithUnpredictableCheckoutRevision(map.values())) break block48;
                                                                equivalenceCheckResult6 = EquivalenceCheckResult.nonEquivalent((String)"build promotion has a VCS root which can checkout different source code even if no changes found");
                                                                if (equivalenceCheckResult6 != null) break block49;
                                                            }
                                                            catch (IllegalStateException illegalStateException) {
                                                                throw BuildPromotionImpl.a(illegalStateException);
                                                            }
                                                            BuildPromotionImpl.$$$reportNull$$$0(133);
                                                        }
                                                        catch (IllegalStateException illegalStateException) {
                                                            throw BuildPromotionImpl.a(illegalStateException);
                                                        }
                                                    }
                                                    return equivalenceCheckResult6;
                                                }
                                                orderedBuild2 = this.createOrderedBuild();
                                                orderedBuild = buildPromotionImpl.createOrderedBuild();
                                                try {
                                                    try {
                                                        if (!orderedBuild.isDetachedChanges()) break block50;
                                                        if (!orderedBuild2.isDetachedChanges()) break block51;
                                                    }
                                                    catch (IllegalStateException illegalStateException) {
                                                        throw BuildPromotionImpl.a(illegalStateException);
                                                    }
                                                    buildPromotionImpl3 = this;
                                                    break block52;
                                                }
                                                catch (IllegalStateException illegalStateException) {
                                                    throw BuildPromotionImpl.a(illegalStateException);
                                                }
                                            }
                                            buildPromotionImpl3 = buildPromotionImpl;
                                        }
                                        try {
                                            equivalenceCheckResult5 = EquivalenceCheckResult.nonEquivalent((String)("build promotion: " + LogUtil.describe((BuildPromotion)buildPromotionImpl3) + " is cancelled, personal or failed to start"));
                                            if (equivalenceCheckResult5 == null) {
                                                BuildPromotionImpl.$$$reportNull$$$0(134);
                                            }
                                        }
                                        catch (IllegalStateException illegalStateException) {
                                            throw BuildPromotionImpl.a(illegalStateException);
                                        }
                                        return equivalenceCheckResult5;
                                    }
                                    try {
                                        try {
                                            block53: {
                                                try {
                                                    try {
                                                        try {
                                                            if (orderedBuild2.getModificationId() == null && orderedBuild.getModificationId() != null) break block53;
                                                        }
                                                        catch (IllegalStateException illegalStateException) {
                                                            throw BuildPromotionImpl.a(illegalStateException);
                                                        }
                                                        if (orderedBuild2.getModificationId() == null) break block54;
                                                    }
                                                    catch (IllegalStateException illegalStateException) {
                                                        throw BuildPromotionImpl.a(illegalStateException);
                                                    }
                                                    if (orderedBuild.getModificationId() != null) break block54;
                                                }
                                                catch (IllegalStateException illegalStateException) {
                                                    throw BuildPromotionImpl.a(illegalStateException);
                                                }
                                            }
                                            if (orderedBuild2.getModificationId() != null) break block55;
                                        }
                                        catch (IllegalStateException illegalStateException) {
                                            throw BuildPromotionImpl.a(illegalStateException);
                                        }
                                        buildPromotionImpl2 = this;
                                        break block56;
                                    }
                                    catch (IllegalStateException illegalStateException) {
                                        throw BuildPromotionImpl.a(illegalStateException);
                                    }
                                }
                                buildPromotionImpl2 = buildPromotionImpl;
                            }
                            try {
                                equivalenceCheckResult4 = EquivalenceCheckResult.nonEquivalent((String)("changes are not yet collected for the build promotion: " + String.valueOf((Object)buildPromotionImpl2)));
                                if (equivalenceCheckResult4 == null) {
                                    BuildPromotionImpl.$$$reportNull$$$0(135);
                                }
                            }
                            catch (IllegalStateException illegalStateException) {
                                throw BuildPromotionImpl.a(illegalStateException);
                            }
                            return equivalenceCheckResult4;
                        }
                        try {
                            if (orderedBuild2.getModificationId() != null || orderedBuild.getModificationId() != null) break block57;
                        }
                        catch (IllegalStateException illegalStateException) {
                            throw BuildPromotionImpl.a(illegalStateException);
                        }
                        Map map2 = this.getProvidedUpperLimitRevisions();
                        Map map3 = buildPromotionImpl.getProvidedUpperLimitRevisions();
                        try {
                            try {
                                if (map2.equals(map3)) break block57;
                                equivalenceCheckResult3 = EquivalenceCheckResult.nonEquivalent((String)"provided upper limit revisions are different");
                                if (equivalenceCheckResult3 != null) break block58;
                            }
                            catch (IllegalStateException illegalStateException) {
                                throw BuildPromotionImpl.a(illegalStateException);
                            }
                            BuildPromotionImpl.$$$reportNull$$$0(136);
                        }
                        catch (IllegalStateException illegalStateException) {
                            throw BuildPromotionImpl.a(illegalStateException);
                        }
                    }
                    return equivalenceCheckResult3;
                }
                try {
                    try {
                        if (orderedBuild.sameRevisions(orderedBuild2, map::containsKey)) break block59;
                        this.logDifferentRevisions(orderedBuild2, orderedBuild, map);
                        equivalenceCheckResult2 = EquivalenceCheckResult.nonEquivalent((String)"revisions are different");
                        if (equivalenceCheckResult2 != null) break block60;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildPromotionImpl.a(illegalStateException);
                    }
                    BuildPromotionImpl.$$$reportNull$$$0(137);
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
            }
            return equivalenceCheckResult2;
        }
        try {
            equivalenceCheckResult = EquivalenceCheckResult.equivalent();
            if (equivalenceCheckResult == null) {
                BuildPromotionImpl.$$$reportNull$$$0(138);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return equivalenceCheckResult;
    }

    @NotNull
    private EquivalenceCheckResult sameBranches(@NotNull BuildPromotionImpl buildPromotionImpl) {
        EquivalenceCheckResult equivalenceCheckResult;
        block10: {
            EquivalenceCheckResult equivalenceCheckResult2;
            block11: {
                try {
                    if (buildPromotionImpl == null) {
                        BuildPromotionImpl.$$$reportNull$$$0(139);
                    }
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
                try {
                    try {
                        if (this.getInternalBranchName().equals(buildPromotionImpl.getInternalBranchName())) break block10;
                        equivalenceCheckResult2 = EquivalenceCheckResult.nonEquivalent((String)("branches are different: " + this.getInternalBranchName() + " != " + buildPromotionImpl.getInternalBranchName()));
                        if (equivalenceCheckResult2 != null) break block11;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildPromotionImpl.a(illegalStateException);
                    }
                    BuildPromotionImpl.$$$reportNull$$$0(140);
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
            }
            return equivalenceCheckResult2;
        }
        try {
            equivalenceCheckResult = EquivalenceCheckResult.equivalent();
            if (equivalenceCheckResult == null) {
                BuildPromotionImpl.$$$reportNull$$$0(141);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return equivalenceCheckResult;
    }

    private EquivalenceCheckResult sameAttributes(@NotNull BuildPromotionEx buildPromotionEx) {
        String[] stringArray;
        block8: {
            try {
                if (buildPromotionEx == null) {
                    BuildPromotionImpl.$$$reportNull$$$0(142);
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
            stringArray = new String[]{"teamcity.checkout.properties.hash"};
            try {
                if (this.myAssociatedBuildId != null || buildPromotionEx.getAssociatedBuildId() != null) break block8;
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
            stringArray = new String[]{"teamcity.checkout.properties.hash", "teamcity.cleanSources", "teamcity.addToFavorites", "teamcity.build.scheduled.time"};
        }
        for (String string : stringArray) {
            Object object = this.getAttribute(string);
            Object object2 = buildPromotionEx.getAttribute(string);
            try {
                if (Objects.equals(object, object2)) continue;
                return EquivalenceCheckResult.nonEquivalent((String)("attribute " + string + " differs, " + String.valueOf(object) + " <=> " + String.valueOf(object2)));
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
        }
        return EquivalenceCheckResult.equivalent();
    }

    @NotNull
    private EquivalenceCheckResult sameDigests(@NotNull BuildPromotionImpl buildPromotionImpl) {
        EquivalenceCheckResult equivalenceCheckResult;
        EquivalenceCheckResult equivalenceCheckResult2;
        byte[] byArray;
        byte[] byArray2;
        block14: {
            EquivalenceCheckResult equivalenceCheckResult3;
            block15: {
                try {
                    if (buildPromotionImpl == null) {
                        BuildPromotionImpl.$$$reportNull$$$0(143);
                    }
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
                byArray2 = this.getSettingsDigest(false);
                byArray = buildPromotionImpl.getSettingsDigest(false);
                try {
                    try {
                        try {
                            if (byArray2.length != 0 || byArray.length != 0) break block14;
                        }
                        catch (IllegalStateException illegalStateException) {
                            throw BuildPromotionImpl.a(illegalStateException);
                        }
                        equivalenceCheckResult3 = EquivalenceCheckResult.equivalent();
                        if (equivalenceCheckResult3 != null) break block15;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildPromotionImpl.a(illegalStateException);
                    }
                    BuildPromotionImpl.$$$reportNull$$$0(144);
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
            }
            return equivalenceCheckResult3;
        }
        try {
            equivalenceCheckResult2 = Arrays.equals(byArray2, byArray) ? EquivalenceCheckResult.equivalent() : EquivalenceCheckResult.nonEquivalent((String)("Settings digests are different: " + new String(Hex.encodeHex((byte[])byArray2)) + " != " + new String(Hex.encodeHex((byte[])byArray))));
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        try {
            equivalenceCheckResult = equivalenceCheckResult2;
            if (equivalenceCheckResult2 == null) {
                BuildPromotionImpl.$$$reportNull$$$0(145);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return equivalenceCheckResult;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @NotNull
    public byte[] getSettingsDigest(boolean bl) {
        block22: {
            String string = (String)this.getAttribute("teamcity.build.settingsDigest");
            try {
                byte[] byArray;
                try {
                    byte[] byArray2;
                    if (string == null) break block22;
                    try {
                        byArray2 = Hex.decodeHex((char[])string.toCharArray());
                    }
                    catch (DecoderException decoderException) {
                        // empty catch block
                        break block22;
                    }
                    byArray = byArray2;
                    if (byArray2 != null) return byArray;
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
                BuildPromotionImpl.$$$reportNull$$$0(146);
                return byArray;
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
        }
        try {
            if (this.getBuildType() == null) {
                return new byte[0];
            }
        }
        catch (DecoderException decoderException) {
            throw BuildPromotionImpl.a(decoderException);
        }
        boolean bl2 = this.hasFrozenSettings();
        byte[] byArray = null;
        Long l = this.getAssociatedBuildId();
        if (l != null) {
            byArray = BaseBuild.getSettingsDigestFromFile((File)this.getArtifactsDirectory());
            try {
                try {
                    if (byArray == null && !bl) {
                        return new byte[0];
                    }
                }
                catch (DecoderException decoderException) {
                    throw BuildPromotionImpl.a(decoderException);
                }
            }
            catch (DecoderException decoderException) {
                throw BuildPromotionImpl.a(decoderException);
            }
            if (byArray != null) {
                bl2 = true;
            }
        }
        if (byArray == null) {
            byArray = this.getBuildSettings().getDigest();
        }
        try {
            if (bl2) {
                this.setAttribute("teamcity.build.settingsDigest", new String(Hex.encodeHex((byte[])byArray)));
            }
        }
        catch (DecoderException decoderException) {
            throw BuildPromotionImpl.a(decoderException);
        }
        try {
            byte[] byArray3 = byArray;
            if (byArray != null) return byArray3;
            BuildPromotionImpl.$$$reportNull$$$0(147);
            return byArray3;
        }
        catch (DecoderException decoderException) {
            throw BuildPromotionImpl.a(decoderException);
        }
    }

    public void setQueuedAt(Date date) {
        this.myQueuedTime = date;
        this.markDirty(32);
    }

    public void setRemoveFromQueueDate(@Nullable Date date) {
        block6: {
            try {
                block7: {
                    try {
                        try {
                            this.myRemoveFromQueueTime = date;
                            if (date == null) break block6;
                            if (this.myQueuedTime == null) break block7;
                        }
                        catch (IllegalStateException illegalStateException) {
                            throw BuildPromotionImpl.a(illegalStateException);
                        }
                        if (!this.myQueuedTime.after(date)) break block6;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildPromotionImpl.a(illegalStateException);
                    }
                }
                this.myQueuedTime = date;
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
        }
        this.markDirty(32);
    }

    @NotNull
    public File getArtifactsDirectory() throws BuildTypeNotFoundException {
        File file;
        File file2;
        File file3;
        block22: {
            block21: {
                String string = (String)this.getAttribute("teamcity.build.buildTypeArtifactsPath");
                try {
                    if (string == null) {
                        return this.computeBuildArtifactsDir();
                    }
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw BuildPromotionImpl.a(buildTypeNotFoundException);
                }
                file3 = null;
                file2 = null;
                for (File file4 : this.getContext().getServerSettings().getArtifactDirectories()) {
                    File file5;
                    block19: {
                        File file6;
                        block20: {
                            file5 = FileUtil.getCanonicalFile((File)new File(file4, string + File.separator + this.artifactsDirName()));
                            try {
                                try {
                                    if (!file5.isDirectory()) break block19;
                                    BuildPromotionImpl.validateArtifactsDirectory(file4, file5);
                                    file6 = file5;
                                    if (file6 != null) break block20;
                                }
                                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                                    throw BuildPromotionImpl.a(buildTypeNotFoundException);
                                }
                                BuildPromotionImpl.$$$reportNull$$$0(148);
                            }
                            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                                throw BuildPromotionImpl.a(buildTypeNotFoundException);
                            }
                        }
                        return file6;
                    }
                    if (file3 != null) continue;
                    file3 = file5;
                    file2 = file4;
                }
                try {
                    try {
                        if ($assertionsDisabled || file3 != null) break block21;
                    }
                    catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                        throw BuildPromotionImpl.a(buildTypeNotFoundException);
                    }
                    throw new AssertionError();
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw BuildPromotionImpl.a(buildTypeNotFoundException);
                }
            }
            try {
                try {
                    if ($assertionsDisabled || file2 != null) break block22;
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw BuildPromotionImpl.a(buildTypeNotFoundException);
                }
                throw new AssertionError();
            }
            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                throw BuildPromotionImpl.a(buildTypeNotFoundException);
            }
        }
        try {
            BuildPromotionImpl.validateArtifactsDirectory(file2, file3);
            file = file3;
            if (file == null) {
                BuildPromotionImpl.$$$reportNull$$$0(149);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildPromotionImpl.a(buildTypeNotFoundException);
        }
        return file;
    }

    private static void validateArtifactsDirectory(@NotNull File file, @NotNull File file2) {
        block10: {
            try {
                if (file == null) {
                    BuildPromotionImpl.$$$reportNull$$$0(150);
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
            try {
                if (file2 == null) {
                    BuildPromotionImpl.$$$reportNull$$$0(151);
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
            try {
                try {
                    if (!TeamCityProperties.getBooleanOrTrue((String)"teamcity.buildPromotion.artifactsPathValidation.enabled") || FileUtil.isAncestor((File)FileUtil.getCanonicalFile((File)file), (File)file2, (boolean)false)) break block10;
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
                throw new IllegalStateException("Build attribute 'teamcity.build.buildTypeArtifactsPath' resolves artifacts directory outside configured artifacts root: " + String.valueOf(file2));
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
        }
    }

    @NotNull
    private File computeBuildArtifactsDir() throws BuildTypeNotFoundException {
        File file;
        File file2;
        block14: {
            String string = this.artifactsDirName();
            file2 = null;
            for (File file3 : this.getBuildTypeSafe().getArtifactDirectories()) {
                File file4;
                block12: {
                    File file5;
                    block13: {
                        file4 = FileUtil.getCanonicalFile((File)new File(file3, string));
                        try {
                            try {
                                if (!file4.isDirectory()) break block12;
                                file5 = file4;
                                if (file5 != null) break block13;
                            }
                            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                                throw BuildPromotionImpl.a(buildTypeNotFoundException);
                            }
                            BuildPromotionImpl.$$$reportNull$$$0(152);
                        }
                        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                            throw BuildPromotionImpl.a(buildTypeNotFoundException);
                        }
                    }
                    return file5;
                }
                if (file2 != null) continue;
                file2 = file4;
            }
            try {
                try {
                    if ($assertionsDisabled || file2 != null) break block14;
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                    throw BuildPromotionImpl.a(buildTypeNotFoundException);
                }
                throw new AssertionError();
            }
            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                throw BuildPromotionImpl.a(buildTypeNotFoundException);
            }
        }
        try {
            file = file2;
            if (file == null) {
                BuildPromotionImpl.$$$reportNull$$$0(153);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildPromotionImpl.a(buildTypeNotFoundException);
        }
        return file;
    }

    private String artifactsDirName() {
        String string;
        try {
            string = this.myAssociatedBuildId != null ? String.valueOf(this.myAssociatedBuildId) : String.valueOf(this.myId);
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return string;
    }

    private void initArtifactsDirectoryAttr() throws BuildTypeNotFoundException {
        File file = this.computeBuildArtifactsDir();
        this.removeStaleDataOnBuildStart(file);
        this.setAttribute("teamcity.build.buildTypeArtifactsPath", ArtifactsUtil.relativeBuildTypeArtifactsPath((File)file));
    }

    public void setArtifactsDirectory(@NotNull File file) {
        try {
            if (file == null) {
                BuildPromotionImpl.$$$reportNull$$$0(154);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        this.setAttribute("teamcity.build.buildTypeArtifactsPath", ArtifactsUtil.relativeBuildTypeArtifactsPath((File)file));
        this.persist();
        this.resetArtifactsState(false);
        this.resetBuildLog();
    }

    public boolean isFinished() {
        Long l = this.myAssociatedBuildId;
        try {
            if (l == null) {
                return false;
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        try {
            if (this.myAssociatedBuildFinished) {
                return true;
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return (Boolean)this.getContext().getSQLRunner().withDB(dBFunctions -> {
            boolean bl;
            try {
                bl = dBFunctions.queryForSingleLong("select build_id from history where build_id = ?", 0L, new Object[]{l}) != 0L;
            }
            catch (DBException dBException) {
                throw BuildPromotionImpl.a(dBException);
            }
            return bl;
        });
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Unable to fully structure code
     */
    @NotNull
    public List<String> getProjectPathIds() {
        block10: {
            block11: {
                var1_1 = this.myProjectPathIds.get();
                if (var1_1 == null) break block10;
                try {
                    block13: {
                        v0 = var1_1;
                        if (v0 != null) break block11;
                        break block13;
                        catch (IllegalStateException v1) {
                            throw BuildPromotionImpl.a(v1);
                        }
                    }
                    BuildPromotionImpl.$$$reportNull$$$0(155);
                }
                catch (IllegalStateException v2) {
                    throw BuildPromotionImpl.a(v2);
                }
            }
            return v0;
        }
        var2_2 = this.myProjectPathIds;
        synchronized (var2_2) {
            var1_1 = this.myProjectPathIds.get();
            if (var1_1 == null) ** break block12
            v3 = var1_1;
            ** if (v3 != null) goto lbl26
        }
lbl-1000:
        // 1 sources

        {
            BuildPromotionImpl.$$$reportNull$$$0(156);
        }
lbl26:
        // 2 sources

        return v3;
        {
            var3_3 = (List)this.getContext().getSQLRunner().withDB((DBAction)LambdaMetafactory.metafactory(null, null, null, (Ljetbrains/buildServer/serverSide/db/DBFunctions;)Ljava/lang/Object;, lambda$getProjectPathIds$21(jetbrains.buildServer.serverSide.db.DBFunctions ), (Ljetbrains/buildServer/serverSide/db/DBFunctions;)Ljava/util/List;)((BuildPromotionImpl)this));
            this.myProjectPathIds.set(var3_3);
            v4 = var3_3;
            ** if (v4 != null) goto lbl34
        }
lbl-1000:
        // 1 sources

        {
            BuildPromotionImpl.$$$reportNull$$$0(157);
        }
lbl34:
        // 2 sources

        return v4;
        {
            catch (Throwable var4_4) {
                throw var4_4;
            }
        }
    }

    @Nullable
    public String getOwnerNodeUrl() {
        return (String)this.getAttribute("teamcity.build.ownerNodeUrl");
    }

    @Nullable
    public TeamCityNode getOwnerNode() {
        String string = this.getOwnerNodeUrl();
        try {
            if (string == null) {
                return null;
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        boolean bl = "#".equals(string);
        Predicate<TeamCityNode> predicate = teamCityNode -> {
            boolean bl2;
            block8: {
                block7: {
                    try {
                        block6: {
                            try {
                                try {
                                    if (bl && teamCityNode.isMainNode()) break block6;
                                }
                                catch (IllegalStateException illegalStateException) {
                                    throw BuildPromotionImpl.a(illegalStateException);
                                }
                                if (!string.equals(teamCityNode.getUrl())) break block7;
                            }
                            catch (IllegalStateException illegalStateException) {
                                throw BuildPromotionImpl.a(illegalStateException);
                            }
                        }
                        bl2 = true;
                        break block8;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildPromotionImpl.a(illegalStateException);
                    }
                }
                bl2 = false;
            }
            return bl2;
        };
        TeamCityNodes teamCityNodes = this.getContext().getTeamCityNodes();
        TeamCityNode teamCityNode2 = teamCityNodes.getCurrentNode();
        try {
            if (predicate.test(teamCityNode2)) {
                return teamCityNode2;
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return teamCityNodes.getOnlineNodes().stream().filter(predicate).findFirst().orElse(null);
    }

    public void setOwnerNodeUrl(@Nullable String string) {
        String string2;
        block6: {
            string2 = this.getOwnerNodeUrl();
            try {
                try {
                    try {
                        this.setAttribute("teamcity.build.ownerNodeUrl", string);
                        this.persist();
                        if (!"#".equals(string) || string2 == null) break block6;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildPromotionImpl.a(illegalStateException);
                    }
                    if (string2.equals("#")) break block6;
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
                this.refreshBuildLog();
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
        }
        this.logOwnerUrlChange(string, string2);
    }

    private void logOwnerUrlChange(@Nullable String string, String string2) {
        block10: {
            block11: {
                block9: {
                    try {
                        if (Objects.equals(string, string2)) {
                            return;
                        }
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildPromotionImpl.a(illegalStateException);
                    }
                    try {
                        try {
                            if (!"#".equals(string) || string2 == null) break block9;
                        }
                        catch (IllegalStateException illegalStateException) {
                            throw BuildPromotionImpl.a(illegalStateException);
                        }
                        Loggers.SERVER.info("Build has been un-assigned from node with URL \"" + string2 + "\". Build: " + LogUtil.describe((BuildPromotion)this));
                        break block10;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildPromotionImpl.a(illegalStateException);
                    }
                }
                try {
                    if (!"#".equals(string)) break block11;
                    Loggers.SERVER.info("Build has been assigned to main node. Build: " + LogUtil.describe((BuildPromotion)this));
                    break block10;
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
            }
            Loggers.SERVER.info("Build has been assigned to node with URL \"" + string + "\". Build: " + LogUtil.describe((BuildPromotion)this));
        }
    }

    @NotNull
    private BuildTypeEx getBuildTypeSafe() throws BuildTypeNotFoundException {
        BuildTypeEx buildTypeEx;
        BuildTypeEx buildTypeEx2 = this.getBuildType();
        try {
            if (buildTypeEx2 == null) {
                throw new BuildTypeNotFoundException(LogUtil.describeMissingBuildTypeForUI((String)this.getBuildTypeId()));
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildPromotionImpl.a(buildTypeNotFoundException);
        }
        try {
            buildTypeEx = buildTypeEx2;
            if (buildTypeEx == null) {
                BuildPromotionImpl.$$$reportNull$$$0(158);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildPromotionImpl.a(buildTypeNotFoundException);
        }
        return buildTypeEx;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @NotNull
    public BuildLogEx getBuildLog() {
        try {
            if (this.isDeleted()) {
                return new NoOpBuildLog();
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        BuildLogRef buildLogRef = this.myBuildLogRef;
        // MONITORENTER : buildLogRef
        BuildLogEx buildLogEx2 = this.myBuildLogRef.getLog();
        if (buildLogEx2 == null) {
            boolean bl;
            BuildLogEx buildLogEx;
            BuildLogRef buildLogRef2;
            buildLogEx2 = this.getContext().getBuildLogFactory().openOrCreate((BuildPromotionEx)this, this.getContext().getServerResponsibility().canQueueBuilds());
            try {
                buildLogRef2 = this.myBuildLogRef;
                buildLogEx = buildLogEx2;
                bl = !this.isFinished();
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
            buildLogRef2.setBuildLog(buildLogEx, bl);
        }
        BuildLogEx buildLogEx = buildLogEx2;
        // MONITOREXIT : buildLogRef
        if (buildLogEx != null) return buildLogEx;
        BuildPromotionImpl.$$$reportNull$$$0(159);
        return buildLogEx;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Nullable
    public BuildLogEx getBuildLogIfInitialized() {
        try {
            if (this.isDeleted()) {
                return null;
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        BuildLogRef buildLogRef = this.myBuildLogRef;
        synchronized (buildLogRef) {
            return this.myBuildLogRef.getLog();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void resetBuildLog() {
        BuildLogRef buildLogRef = this.myBuildLogRef;
        synchronized (buildLogRef) {
            this.myBuildLogRef.setBuildLog(null, false);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void refreshBuildLog() {
        BuildLogRef buildLogRef = this.myBuildLogRef;
        synchronized (buildLogRef) {
            BuildLogEx buildLogEx = this.myBuildLogRef.getLog();
            if (buildLogEx == null) {
                return;
            }
            buildLogEx.flush();
            this.myBuildLogRef.setBuildLog(null, false);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void buildFinished() {
        this.resetAllCompatibilityCaches();
        this.resetBuildLog();
        this.myProjectPathIds.set(null);
        this.myUserParameters.resetLoadedParameters();
        BuildPromotionImpl buildPromotionImpl = this;
        synchronized (buildPromotionImpl) {
            this.resetBuildSettings();
        }
    }

    public void buildEvictedFromCache() {
        try {
            if (!TeamCityProperties.getBooleanOrTrue((String)"teamcity.buildPromotion.resetCachesWhenBuildEvictedFromCache")) {
                return;
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        this.resetLogActionsCache();
        this.resetTagsCache();
        this.myProblems.tryResetBuildProblemsCache();
        this.clearPrevPromotionCache(null);
    }

    public void associatedBuildFinished() {
        try {
            if (!this.myAssociatedBuildFinished) {
                this.myAssociatedBuildFinished = true;
                this.myProblems.resetInit();
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
    }

    public boolean isAssociatedBuildFinished() {
        return this.myAssociatedBuildFinished;
    }

    public boolean isChangeCollectingNeeded() {
        boolean bl;
        block5: {
            block4: {
                try {
                    try {
                        if (this.getLastModificationId() != null || this.isFailedToCollectChanges()) break block4;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildPromotionImpl.a(illegalStateException);
                    }
                    bl = true;
                    break block5;
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
            }
            bl = false;
        }
        return bl;
    }

    /*
     * Loose catch block
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean isChangeCollectingNeeded(boolean bl) {
        AtomicBoolean atomicBoolean;
        block6: {
            atomicBoolean = new AtomicBoolean(this.isChangeCollectingNeeded());
            if (!bl) return atomicBoolean.get();
            try {
                if (!atomicBoolean.get()) break block6;
                return atomicBoolean.get();
                catch (Throwable throwable) {
                    throw BuildPromotionImpl.a(throwable);
                }
            }
            catch (Throwable throwable) {
                throw BuildPromotionImpl.a(throwable);
            }
        }
        try {
            this.getContext().getSecurityContext().runAsSystem(() -> this.traverseDependencies((DependencyConsumer<BuildPromotionEx>)((DependencyConsumer)buildPromotionEx -> {
                try {
                    if (buildPromotionEx.isChangeCollectingNeeded()) {
                        atomicBoolean.set(true);
                        return DependencyConsumer.Result.STOP;
                    }
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
                return DependencyConsumer.Result.CONTINUE;
            })));
            return atomicBoolean.get();
        }
        catch (Throwable throwable) {
            ExceptionUtil.rethrowAsRuntimeException((Throwable)throwable);
        }
        return atomicBoolean.get();
    }

    public boolean isCanceled() {
        boolean bl;
        block8: {
            block7: {
                try {
                    if (!this.isChangesDetached()) {
                        return false;
                    }
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
                SBuild sBuild = this.getAssociatedBuild();
                try {
                    try {
                        if (sBuild == null || sBuild.getCanceledInfo() == null) break block7;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildPromotionImpl.a(illegalStateException);
                    }
                    bl = true;
                    break block8;
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
            }
            bl = false;
        }
        return bl;
    }

    @NotNull
    public List<BuildPromotionEx> getStartedEquivalentPromotions(int n2) {
        BuildTypeEx buildTypeEx;
        block4: {
            List<BuildPromotionEx> list;
            block5: {
                buildTypeEx = this.getBuildType();
                try {
                    try {
                        if (buildTypeEx != null) break block4;
                        list = Collections.emptyList();
                        if (list != null) break block5;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildPromotionImpl.a(illegalStateException);
                    }
                    BuildPromotionImpl.$$$reportNull$$$0(160);
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
            }
            return list;
        }
        BuildTypeOrderedBuilds buildTypeOrderedBuilds = buildTypeEx.getBuildTypeOrderedBuilds();
        return this.computeEquivalentPromotions(n2, (string, n) -> {
            ArrayList arrayList = new ArrayList();
            buildTypeOrderedBuilds.processBuildsInBranch(string, orderedBuild -> {
                boolean bl;
                block5: {
                    block4: {
                        try {
                            try {
                                arrayList.add(orderedBuild);
                                if (n >= 0 && arrayList.size() >= n) break block4;
                            }
                            catch (IllegalStateException illegalStateException) {
                                throw BuildPromotionImpl.a(illegalStateException);
                            }
                            bl = true;
                            break block5;
                        }
                        catch (IllegalStateException illegalStateException) {
                            throw BuildPromotionImpl.a(illegalStateException);
                        }
                    }
                    bl = false;
                }
                return bl;
            });
            return arrayList;
        }, "started");
    }

    @NotNull
    public List<BuildPromotionEx> getEquivalentPromotions(@NotNull List<BuildPromotionEx> list) {
        HashMap<String, List> hashMap;
        block11: {
            List<BuildPromotionEx> list2;
            block12: {
                try {
                    if (list == null) {
                        BuildPromotionImpl.$$$reportNull$$$0(161);
                    }
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
                hashMap = new HashMap<String, List>();
                for (BuildPromotionEx buildPromotionEx : list) {
                    try {
                        if (buildPromotionEx == this) {
                            continue;
                        }
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildPromotionImpl.a(illegalStateException);
                    }
                    String string2 = BuildPromotionImpl.toExternalName((String)buildPromotionEx.getInternalBranchName());
                    hashMap.computeIfAbsent(string2, string -> new ArrayList()).add(buildPromotionEx.createOrderedBuild());
                }
                try {
                    try {
                        if (!hashMap.isEmpty()) break block11;
                        list2 = Collections.emptyList();
                        if (list2 != null) break block12;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildPromotionImpl.a(illegalStateException);
                    }
                    BuildPromotionImpl.$$$reportNull$$$0(162);
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
            }
            return list2;
        }
        return this.computeEquivalentPromotions(-1, (string, n) -> {
            List list;
            List list2 = (List)hashMap.get(string);
            try {
                list = list2 == null ? Collections.emptyList() : list2;
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
            return list;
        }, "queued");
    }

    /*
     * Exception decompiling
     */
    @NotNull
    private List<BuildPromotionEx> computeEquivalentPromotions(int var1_1, @NotNull BranchOrderedBuildsSupplier var2_2, @NotNull String var3_3) {
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

    @NotNull
    private List<Long> findSuitablePromotionIds(long l, int n, @NotNull BranchOrderedBuildsSupplier branchOrderedBuildsSupplier) {
        ArrayList<Long> arrayList;
        Map<Long, VcsRootInstance> map;
        OrderedBuild orderedBuild;
        ArrayList<Long> arrayList2;
        HashSet<String> hashSet;
        block36: {
            List<Long> list;
            block37: {
                String string;
                block35: {
                    try {
                        if (branchOrderedBuildsSupplier == null) {
                            BuildPromotionImpl.$$$reportNull$$$0(170);
                        }
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildPromotionImpl.a(illegalStateException);
                    }
                    string = BuildPromotionImpl.toExternalName((String)this.getInternalBranchName());
                    hashSet = new HashSet<String>();
                    try {
                        try {
                            if (string.equals("<default>") || this.shouldCompareBranches()) break block35;
                        }
                        catch (IllegalStateException illegalStateException) {
                            throw BuildPromotionImpl.a(illegalStateException);
                        }
                        hashSet.add("<default>");
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildPromotionImpl.a(illegalStateException);
                    }
                }
                hashSet.add(string);
                arrayList2 = new ArrayList<Long>();
                orderedBuild = this.createOrderedBuild();
                map = this.vcsRootsForEquivalenceCheck();
                try {
                    try {
                        if (!this.hasVcsRootsWithUnpredictableCheckoutRevision(map.values())) break block36;
                        if (!LOG.isDebugEnabled()) break block37;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildPromotionImpl.a(illegalStateException);
                    }
                    LOG.debug("isEquivalentTo(): build promotion " + LogUtil.describe((BuildPromotion)this) + " contains a VCS root for which sourcesUpdatePossibleIfChangesNotFound returns true");
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
            }
            try {
                list = Collections.emptyList();
                if (list == null) {
                    BuildPromotionImpl.$$$reportNull$$$0(171);
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
            return list;
        }
        block28: for (String string : hashSet) {
            int n2 = 0;
            for (OrderedBuild orderedBuild2 : branchOrderedBuildsSupplier.getBuilds(string, n)) {
                block41: {
                    Long l2;
                    block40: {
                        block39: {
                            try {
                                if (orderedBuild2.getPromotionId() == this.getId()) {
                                    continue;
                                }
                            }
                            catch (IllegalStateException illegalStateException) {
                                throw BuildPromotionImpl.a(illegalStateException);
                            }
                            try {
                                try {
                                    if (n >= 0 && ++n2 >= n) {
                                        continue block28;
                                    }
                                }
                                catch (IllegalStateException illegalStateException) {
                                    throw BuildPromotionImpl.a(illegalStateException);
                                }
                            }
                            catch (IllegalStateException illegalStateException) {
                                throw BuildPromotionImpl.a(illegalStateException);
                            }
                            l2 = orderedBuild2.getModificationId();
                            try {
                                if (l2 != null) break block39;
                                if (!LOG.isDebugEnabled()) continue;
                            }
                            catch (IllegalStateException illegalStateException) {
                                throw BuildPromotionImpl.a(illegalStateException);
                            }
                            LOG.debug(this.equivalenceLoggingPrefix(orderedBuild2.getPromotionId()) + "changes are not yet collected for the build promotion with id: " + orderedBuild2.getPromotionId());
                            continue;
                        }
                        try {
                            if (!orderedBuild2.isDetachedChanges()) break block40;
                            if (!LOG.isDebugEnabled()) continue;
                        }
                        catch (IllegalStateException illegalStateException) {
                            throw BuildPromotionImpl.a(illegalStateException);
                        }
                        LOG.debug(this.equivalenceLoggingPrefix(orderedBuild2.getPromotionId()) + "build promotion with id: " + orderedBuild2.getPromotionId() + " has detached changes (cancelled, personal or failed to start)");
                        continue;
                    }
                    try {
                        try {
                            if (l2 > l) continue;
                            if (orderedBuild2.sameRevisions(orderedBuild, map::containsKey)) break block41;
                        }
                        catch (IllegalStateException illegalStateException) {
                            throw BuildPromotionImpl.a(illegalStateException);
                        }
                        this.logDifferentRevisions(orderedBuild, orderedBuild2, map);
                        continue;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildPromotionImpl.a(illegalStateException);
                    }
                }
                arrayList2.add(orderedBuild2.getPromotionId());
            }
        }
        try {
            arrayList2.sort(Comparator.reverseOrder());
            arrayList = arrayList2;
            if (arrayList == null) {
                BuildPromotionImpl.$$$reportNull$$$0(172);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return arrayList;
    }

    private void logDifferentRevisions(@NotNull OrderedBuild orderedBuild, @NotNull OrderedBuild orderedBuild2, @NotNull Map<Long, VcsRootInstance> map) {
        try {
            if (orderedBuild == null) {
                BuildPromotionImpl.$$$reportNull$$$0(173);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        try {
            if (orderedBuild2 == null) {
                BuildPromotionImpl.$$$reportNull$$$0(174);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        try {
            if (map == null) {
                BuildPromotionImpl.$$$reportNull$$$0(175);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        if (LOG.isDebugEnabled()) {
            for (long l : map.keySet()) {
                String string;
                String string2 = orderedBuild.getRevision(l);
                if (Objects.equals(string2, string = orderedBuild2.getRevision(l))) continue;
                VcsRootInstance vcsRootInstance = map.get(l);
                LOG.debug(this.equivalenceLoggingPrefix(orderedBuild2.getPromotionId()) + "revisions differ for VCS root: " + LogUtil.describe((Loggable)vcsRootInstance) + ": " + string2 + " != " + string);
            }
        }
    }

    private boolean hasVcsRootsWithUnpredictableCheckoutRevision(@NotNull Collection<VcsRootInstance> collection) {
        try {
            if (collection == null) {
                BuildPromotionImpl.$$$reportNull$$$0(176);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        for (VcsRootInstance vcsRootInstance : collection) {
            VcsSupportCore vcsSupportCore = this.getContext().getVcsManager().findVcsByName(vcsRootInstance.getVcsName());
            try {
                try {
                    if (vcsSupportCore != null && !vcsSupportCore.sourcesUpdatePossibleIfChangesNotFound((VcsRoot)vcsRootInstance)) continue;
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
                return true;
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
        }
        return false;
    }

    @NotNull
    private Map<Long, VcsRootInstance> vcsRootsForEquivalenceCheck() {
        HashMap<Long, VcsRootInstance> hashMap;
        List<BuildRevisionEx> list;
        boolean bl = TeamCityProperties.getBooleanOrTrue((String)"teamcity.promotion.isEquivalentIgnoresManualRevisions");
        HashMap<Long, VcsRootInstance> hashMap2 = new HashMap<Long, VcsRootInstance>();
        try {
            list = bl ? this.getRevisionsForCheckout() : this.getRevisionsInternal();
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        List<BuildRevisionEx> list2 = list;
        try {
            list2.forEach(buildRevisionEx -> hashMap2.put(buildRevisionEx.getRoot().getId(), buildRevisionEx.getRoot()));
            hashMap = hashMap2;
            if (hashMap == null) {
                BuildPromotionImpl.$$$reportNull$$$0(177);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return hashMap;
    }

    private boolean shouldCompareBranches() {
        return Boolean.parseBoolean(this.getParameterValue("teamcity.promotion.isEquivalentChecksBranch"));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     */
    public void markIncomplete() {
        block7: {
            this.cancelCheckingForChanges();
            this.finalizeBranchName(null);
            if (!Loggers.SERVER.isDebugEnabled()) break block7;
            try {
                block8: {
                    if (!CurrentNodeInfo.isMainNode()) break block7;
                    break block8;
                    catch (IllegalStateException illegalStateException) {
                        throw BuildPromotionImpl.a(illegalStateException);
                    }
                }
                Loggers.SERVER.debug("Detaching changes from build promotion " + LogUtil.describeInDetail((BuildPromotion)this) + " because promotion is marked as incomplete");
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
        }
        this.myChangesDetached = true;
        this.getContext().getSQLRunner().withDBNoResult(dBFunctions -> {
            try {
                dBFunctions.executeDml("update build_state set is_changes_detached = 1, is_canceled = 1, branch_name = ? where id = ?", new Object[]{UnicodeString.unicodeOrNull((String)StringUtil.nullIfEmpty((String)this.myFinalBranchName)), this.myId});
            }
            catch (MySqlIncorrectStringValueException mySqlIncorrectStringValueException) {
                String string = StringUtil.removeNonBMPCharacters((String)this.myFinalBranchName);
                dBFunctions.executeDml("update build_state set is_changes_detached = 1, is_canceled = 1, branch_name = ? where id = ?", new Object[]{UnicodeString.unicodeOrNull((String)string), this.myId});
                this.logInvalidCharsInBranchName(this.myFinalBranchName, mySqlIncorrectStringValueException);
            }
        });
        this.commitCurrentTransaction();
        this.notifyChangesDetached();
        BuildPromotionImpl buildPromotionImpl = this;
        synchronized (buildPromotionImpl) {
            this.myIncomplete = true;
        }
    }

    private void logInvalidCharsInBranchName(@NotNull String string, MySqlIncorrectStringValueException mySqlIncorrectStringValueException) {
        try {
            if (string == null) {
                BuildPromotionImpl.$$$reportNull$$$0(178);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        LOG.warnAndDebugDetails("Could not store branch name " + string + " for build promotion with id " + this.myId + " because it contains characters which are not supported by current database charset", (Throwable)mySqlIncorrectStringValueException);
    }

    public void markAsIncompleteIfHasFailedToStartProblems() {
        this.myProblems.markAsIncompleteIfHasFailedToStartProblems(this.getFailureReasons());
    }

    private void commitCurrentTransaction() {
        this.getContext().getSQLRunner().commitCurrentTransaction();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean isIncomplete() {
        BuildPromotionImpl buildPromotionImpl = this;
        synchronized (buildPromotionImpl) {
            if (this.myIncomplete != null) {
                return this.myIncomplete;
            }
        }
        try {
            if (this.isCanceled()) {
                return true;
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        buildPromotionImpl = this;
        synchronized (buildPromotionImpl) {
            if (this.myIncomplete == null) {
                this.myIncomplete = (Boolean)this.getContext().getSQLRunner().withDB(dBFunctions -> dBFunctions.queryForSingleBoolean("select is_canceled from build_state where id = ?", false, new Object[]{this.myId}));
            }
            return this.myIncomplete;
        }
    }

    public void markAsDeleted() {
        try {
            if (this.isDeleted()) {
                return;
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        try {
            BuildPromotionImpl.markAsDeleted(Collections.singleton(this), this.getContext());
            if (!this.isDeleted()) {
                throw new IllegalStateException("Build promotion " + LogUtil.describe((BuildPromotion)this) + " cannot be marked as deleted");
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void disposeBuildLog() {
        BuildLogRef buildLogRef = this.myBuildLogRef;
        synchronized (buildLogRef) {
            BuildLogEx buildLogEx = this.myBuildLogRef.getLog();
            try {
                if (buildLogEx != null) {
                    buildLogEx.dispose();
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Could not resolve type clashes
     * Loose catch block
     */
    public static void markAsDeleted(@NotNull Collection<BuildPromotionImpl> collection, @NotNull BuildPromotionContext buildPromotionContext) {
        BuildPromotionImpl buildPromotionImpl3;
        Object object2;
        try {
            if (collection == null) {
                BuildPromotionImpl.$$$reportNull$$$0(179);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        try {
            if (buildPromotionContext == null) {
                BuildPromotionImpl.$$$reportNull$$$0(180);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        try {
            if (collection.isEmpty()) {
                return;
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        HashMap<Long, Collection> hashMap = new HashMap<Long, Collection>();
        for (Object object2 : collection) {
            hashMap.put(((BuildPromotionImpl)((Object)object2)).getId(), LongStream.of(((BuildPromotionImpl)((Object)object2)).getDependedOnMeIds()).boxed().collect(Collectors.toList()));
        }
        List<BuildPromotionImpl> list = collection.stream().filter(buildPromotionImpl -> {
            boolean bl;
            block5: {
                block4: {
                    try {
                        try {
                            if (buildPromotionImpl.isDeleted() || buildPromotionImpl.myAssociatedBuildId != null) break block4;
                        }
                        catch (IllegalStateException illegalStateException) {
                            throw BuildPromotionImpl.a(illegalStateException);
                        }
                        bl = true;
                        break block5;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildPromotionImpl.a(illegalStateException);
                    }
                }
                bl = false;
            }
            return bl;
        }).sorted((buildPromotionImpl, buildPromotionImpl2) -> Long.compare(buildPromotionImpl.getId(), buildPromotionImpl2.getId())).toList();
        try {
            if (list.isEmpty()) {
                return;
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        object2 = collection.stream().filter(buildPromotionImpl -> {
            boolean bl;
            try {
                bl = !buildPromotionImpl.isPersisted();
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
            return bl;
        }).map(BuildPromotionImpl::getId).collect(Collectors.toSet());
        Set set = (Set)buildPromotionContext.getSQLRunner().runAndRetry(arg_0 -> BuildPromotionImpl.lambda$markAsDeleted$36(list, hashMap, (Set)object2, arg_0));
        BuildPromotionManagerEx buildPromotionManagerEx = buildPromotionContext.getBuildPromotionManager();
        HashSet<Long> hashSet = new HashSet<Long>();
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            buildPromotionImpl3 = (BuildPromotionImpl)((Object)iterator.next());
            try {
                if (buildPromotionImpl3.isDeleted()) {
                    continue;
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
            try {
                if (set.contains(buildPromotionImpl3.getId())) {
                    continue;
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
            BuildPromotionImpl buildPromotionImpl4 = buildPromotionImpl3;
            synchronized (buildPromotionImpl4) {
                block39: {
                    boolean bl;
                    block41: {
                        boolean bl2;
                        block40: {
                            if (!buildPromotionImpl3.isPersisted()) break block39;
                            if (!object2.contains(buildPromotionImpl3.getId())) break block39;
                            try {
                                block42: {
                                    if ((Integer)buildPromotionContext.getSQLRunner().runAndRetry(dBFunctions -> dBFunctions.executeDml("update build_state set is_deleted = 1 where id = ? and build_id is null", new Object[]{buildPromotionImpl3.getId()})) <= 0) break block40;
                                    break block42;
                                    catch (IllegalStateException illegalStateException) {
                                        throw BuildPromotionImpl.a(illegalStateException);
                                    }
                                }
                                bl2 = true;
                                break block41;
                            }
                            catch (IllegalStateException illegalStateException) {
                                throw BuildPromotionImpl.a(illegalStateException);
                            }
                        }
                        bl2 = bl = false;
                    }
                    if (!bl) {
                        LOG.warn("Could not mark " + LogUtil.describe((BuildPromotion)buildPromotionImpl3) + " as deleted because it is associated with a build");
                        continue;
                    }
                }
                buildPromotionImpl3.myDeleted = true;
            }
            buildPromotionImpl3.cancelCheckingForChanges();
            for (BuildPromotion buildPromotion : buildPromotionManagerEx.findPromotionsByIds((Collection)hashMap.get(buildPromotionImpl3.getId()), new BuildPromotionManager.BuildPromotionInitFromDbHint[0])) {
                ((BuildPromotionEx)buildPromotion).removeDependency((BuildPromotionEx)buildPromotionImpl3);
            }
            hashSet.add(buildPromotionImpl3.getId());
        }
        try {
            if (hashSet.isEmpty()) {
                return;
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        buildPromotionContext.getSQLRunner().runAndRetryNoResult(dBFunctions -> {
            dBFunctions.executeBatchForLongs("delete from build_dependency where depends_on = ?", (Collection)hashSet);
            dBFunctions.executeBatchForLongs("delete from build_dependency where build_state_id = ?", (Collection)hashSet);
        });
        iterator = list.iterator();
        while (iterator.hasNext()) {
            buildPromotionImpl3 = (BuildPromotionImpl)((Object)iterator.next());
            try {
                if (!hashSet.contains(buildPromotionImpl3.getId())) {
                    continue;
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
            try {
                buildPromotionImpl3.resetDirectDependenciesCache();
                buildPromotionManagerEx.promotionDeleted((BuildPromotionEx)buildPromotionImpl3);
                ((BuildPromotionListener)buildPromotionManagerEx.getDispatcher().getMulticaster()).buildPromotionDeleted((BuildPromotion)buildPromotionImpl3);
                if (!buildPromotionImpl3.isPersisted()) continue;
                buildPromotionContext.getNodesBuildsEvents().publishBuildPromotionDeleted((BuildPromotion)buildPromotionImpl3);
            }
            finally {
                buildPromotionImpl3.disposeBuildLog();
                buildPromotionImpl3.scheduleArtifactsStateUpdate();
                buildPromotionImpl3.buildFinished();
            }
        }
    }

    @NotNull
    private static Set<Long> tryMarkingAsDeleted(@NotNull DBFunctions dBFunctions, @NotNull List<Long> list) {
        HashSet<Long> hashSet;
        HashSet<Long> hashSet2;
        block15: {
            try {
                if (dBFunctions == null) {
                    BuildPromotionImpl.$$$reportNull$$$0(181);
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
            try {
                if (list == null) {
                    BuildPromotionImpl.$$$reportNull$$$0(182);
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
            List<PromoId> list2 = list.stream().map(l -> new PromoId(l.longValue())).toList();
            int[] nArray = dBFunctions.executeBatchForTuples("update build_state set is_deleted = 1 where id = :myId and build_id is null", PromoId.class, list2);
            boolean bl = Arrays.stream(nArray).noneMatch(n -> {
                boolean bl;
                try {
                    bl = n < 0;
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
                return bl;
            });
            hashSet2 = new HashSet<Long>();
            if (bl) {
                int n2 = 0;
                while (true) {
                    block16: {
                        try {
                            try {
                                if (n2 >= nArray.length) break block15;
                                if (nArray[n2] != 0) break block16;
                            }
                            catch (IllegalStateException illegalStateException) {
                                throw BuildPromotionImpl.a(illegalStateException);
                            }
                            hashSet2.add(list2.get((int)n2).myId);
                        }
                        catch (IllegalStateException illegalStateException) {
                            throw BuildPromotionImpl.a(illegalStateException);
                        }
                    }
                    ++n2;
                }
            }
            hashSet2.addAll(BuildPromotionImpl.getNotMarkedAsDeleted(dBFunctions, list));
        }
        try {
            hashSet = hashSet2;
            if (hashSet == null) {
                BuildPromotionImpl.$$$reportNull$$$0(183);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return hashSet;
    }

    @NotNull
    private static Set<Long> getNotMarkedAsDeleted(@NotNull DBFunctions dBFunctions, @NotNull List<Long> list) {
        HashSet<Long> hashSet;
        try {
            if (dBFunctions == null) {
                BuildPromotionImpl.$$$reportNull$$$0(184);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        try {
            if (list == null) {
                BuildPromotionImpl.$$$reportNull$$$0(185);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        HashSet<Long> hashSet2 = new HashSet<Long>();
        int n = TeamCityProperties.getInteger((String)"teamcity.buildPromotion.markAsDeleted.packSize", (int)100);
        String string = "select id from build_state where is_deleted = 0 and #IN{id}";
        try {
            hashSet2.addAll(dBFunctions.queryForLongs(string, new Object[]{InParams.ofLong(list).autoSplitBy(n)}));
            hashSet = hashSet2;
            if (hashSet == null) {
                BuildPromotionImpl.$$$reportNull$$$0(186);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return hashSet;
    }

    @NotNull
    private static Set<Long> getUndeletableIds(@NotNull DBFunctions dBFunctions, @NotNull List<Long> list, @NotNull Map<Long, Collection<Long>> map) {
        HashSet<Long> hashSet;
        try {
            if (dBFunctions == null) {
                BuildPromotionImpl.$$$reportNull$$$0(187);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        try {
            if (list == null) {
                BuildPromotionImpl.$$$reportNull$$$0(188);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        try {
            if (map == null) {
                BuildPromotionImpl.$$$reportNull$$$0(189);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        HashSet<Long> hashSet2 = new HashSet<Long>();
        int n = TeamCityProperties.getInteger((String)"teamcity.buildPromotion.markAsDeleted.packSize", (int)100);
        String string = "select build_state_id, depends_on from build_dependency where #IN{depends_on}";
        for (List list2 : Lists.partition(list, (int)n)) {
            List list3 = dBFunctions.queryForPairsOfObjects(string, Long.class, Long.class, new Object[]{InParams.ofLong((Collection)list2)});
            Iterator iterator = list3.iterator();
            while (iterator.hasNext()) {
                Pair pair2 = (Pair)iterator.next();
                try {
                    if (!map.get(pair2.second).contains(pair2.first)) continue;
                    iterator.remove();
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
            }
            list3.forEach(pair -> hashSet2.add((Long)pair.second));
        }
        try {
            hashSet = hashSet2;
            if (hashSet == null) {
                BuildPromotionImpl.$$$reportNull$$$0(190);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return hashSet;
    }

    public boolean isDeleted() {
        return this.myDeleted;
    }

    public boolean isPersisted() {
        return this.myCreatedInDb;
    }

    public boolean hasStartedParent() {
        try {
            return (Boolean)this.getContext().getSecurityContext().runAsSystem(() -> {
                for (BuildDependencyEx buildDependencyEx : this.getDependedOnMe()) {
                    Long l = buildDependencyEx.getDependent().getAssociatedBuildId();
                    try {
                        if (l == null) continue;
                        return true;
                    }
                    catch (Throwable throwable) {
                        throw BuildPromotionImpl.a(throwable);
                    }
                }
                return false;
            });
        }
        catch (Throwable throwable) {
            ExceptionUtil.rethrowAsRuntimeException((Throwable)throwable);
            return false;
        }
    }

    public void persist() {
        this.persist(BuildPromotionEx.PromotionPersistMode.PERSIST_IF_DIRTY);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void persist(@NotNull BuildPromotionEx.PromotionPersistMode promotionPersistMode) {
        int n;
        try {
            if (promotionPersistMode == null) {
                BuildPromotionImpl.$$$reportNull$$$0(191);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        BuildPromotionImpl buildPromotionImpl = this;
        synchronized (buildPromotionImpl) {
            if (promotionPersistMode == BuildPromotionEx.PromotionPersistMode.PERSIST_IF_EXIST_IN_DB && !this.myCreatedInDb) {
                return;
            }
            boolean bl = this.createPromotionInDbIfNeeded();
            if (this.myDeleted) {
                return;
            }
            n = this.myDirtyState;
            try {
                if (!bl) {
                    this.initFields();
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
            if (this.myDependencies.persistDependencies()) {
                n |= 4;
            }
            if (n == 0) {
                return;
            }
            try {
                if ((n & 2) > 0) {
                    this.persistVcsRelatedData();
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
            try {
                if ((n & 1) > 0) {
                    this.myUserParameters.persistParameters();
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
            try {
                if ((n & 8) > 0) {
                    this.persistCustomArtifactDependencies();
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
            try {
                if ((n & 0x40) > 0) {
                    this.persistFinalArtifactDependencies();
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
            try {
                if ((n & 0x10) > 0) {
                    this.persistAttributes();
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
            try {
                if ((n & 0x20) > 0) {
                    this.persistDates();
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
            this.myDirtyState = 0;
        }
        this.getBuildPromotionManager().promotionPersisted((BuildPromotionEx)this);
        this.getContext().getNodesBuildsEvents().publishBuildPromotionPersisted((BuildPromotion)this, n);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean hasDirtyState() {
        BuildPromotionImpl buildPromotionImpl = this;
        synchronized (buildPromotionImpl) {
            if (this.myDirtyState != 0) return true;
            try {
                if (!this.myDependencies.hasNonPersistedDependencies()) return false;
                return true;
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
        }
    }

    private void removeStaleDataOnBuildStart(@NotNull File file) {
        try {
            if (file == null) {
                BuildPromotionImpl.$$$reportNull$$$0(192);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        if (file.exists()) {
            String string = this.getId() + "_old";
            File file2 = new File(file.getParent(), string);
            try {
                Loggers.SERVER.warn("Renaming old build log and artifacts; possible incorrect restoring from backup: " + String.valueOf(file) + " => " + string + " in the same location");
                if (!file.renameTo(file2)) {
                    Loggers.SERVER.error("Renaming " + String.valueOf(file) + " to " + String.valueOf(file2) + " failed, build results may be unexpected");
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
        }
    }

    private synchronized void persistAttributes() {
        try {
            if (this.myNotPersistedAttributes.isEmpty()) {
                return;
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        this.getContext().getSQLRunner().runAndRetryNoResult(dBFunctions -> this.storeAttributes(this.myNotPersistedAttributes, dBFunctions));
        this.myAttributesRead = false;
        this.myNotPersistedAttributes.clear();
    }

    private void storeAttributes(@NotNull Set<String> set, @NotNull DBFunctions dBFunctions) {
        try {
            if (set == null) {
                BuildPromotionImpl.$$$reportNull$$$0(193);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        try {
            if (dBFunctions == null) {
                BuildPromotionImpl.$$$reportNull$$$0(194);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        Map map = (Map)this.myAttributes.get();
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        HashSet<String> hashSet = new HashSet<String>();
        for (String string2 : set) {
            hashSet.add(string2);
            Object v = map.get(string2);
            try {
                if (v == null) continue;
                hashMap.put(string2, v);
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
        }
        DELETE_ATTRS_QUERY.executeBatch(dBFunctions, (Iterator)new /* Unavailable Anonymous Inner Class!! */);
        hashMap.forEach((string, object) -> {
            Number number;
            try {
                number = object instanceof Number ? (Number)((Number)object) : (Number)null;
            }
            catch (MySqlIncorrectStringValueException mySqlIncorrectStringValueException) {
                throw BuildPromotionImpl.a(mySqlIncorrectStringValueException);
            }
            Number number2 = number;
            UnicodeString unicodeString = UnicodeString.unicodeOrNull((String)StringUtil.objectToString((Object)object));
            try {
                dBFunctions.executeDml("insert into build_attrs (build_state_id, attr_name, attr_value, attr_num_value) values (?, ?, ?, ?)", new Object[]{this.myId, string, unicodeString, number2});
            }
            catch (MySqlIncorrectStringValueException mySqlIncorrectStringValueException) {
                try {
                    if (unicodeString == null) {
                        throw mySqlIncorrectStringValueException;
                    }
                }
                catch (MySqlIncorrectStringValueException mySqlIncorrectStringValueException2) {
                    throw BuildPromotionImpl.a(mySqlIncorrectStringValueException2);
                }
                UnicodeString unicodeString2 = UnicodeString.unicodeOrNull((String)StringUtil.removeNonBMPCharacters((String)unicodeString.string));
                dBFunctions.executeDml("insert into build_attrs (build_state_id, attr_name, attr_value, attr_num_value) values (?, ?, ?, ?)", new Object[]{this.myId, string, unicodeString2, number2});
                LOG.warnAndDebugDetails("Could not store value of attribute " + string + " for build promotion with id " + this.myId + " because it contains characters which are not supported by current database charset", (Throwable)mySqlIncorrectStringValueException);
            }
        });
    }

    private void persistDates() {
        Long l;
        Object[] objectArray;
        Long l2;
        Object[] objectArray2;
        SQLRunnerEx sQLRunnerEx;
        GenericQuery genericQuery;
        try {
            genericQuery = UPDATE_DATES_QUERY;
            sQLRunnerEx = this.getContext().getSQLRunner();
            Object[] objectArray3 = new Object[3];
            Object[] objectArray4 = objectArray3;
            objectArray2 = objectArray3;
            int n = 0;
            l2 = this.myQueuedTime == null ? null : Long.valueOf(this.myQueuedTime.getTime());
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        try {
            objectArray4[n] = l2;
            Object[] objectArray5 = objectArray2;
            objectArray = objectArray2;
            int n = 1;
            l = this.myRemoveFromQueueTime == null ? null : Long.valueOf(this.myRemoveFromQueueTime.getTime());
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        objectArray5[n] = l;
        objectArray[2] = this.myId;
        genericQuery.execute(sQLRunnerEx, objectArray);
    }

    private void initFields() {
        this.loadRevisionsIfNeeded();
        this.loadOverridenRootsIfNeeded();
        this.myUserParameters.loadParametersIfNeeded();
        this.loadArtifactDependenciesIfNeeded();
    }

    private void persistVcsRelatedData() {
        List<BuildRevisionEx> list = this.getRevisionsInternal();
        this.getContext().getSQLRunner().runAndRetryNoResult(dBFunctions -> {
            this.persistRevisions(dBFunctions, list);
            this.persistOverridenRoots(dBFunctions);
            this.persistModificationIdsAndBranch(dBFunctions);
        });
    }

    public void updateFromDB(@NotNull BuildPromotionEx.UpdateReason updateReason) {
        try {
            if (updateReason == null) {
                BuildPromotionImpl.$$$reportNull$$$0(195);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        this.updateInternalState(updateReason, this::loadAttributesFromDb, this::loadIncompleteInfoFromDB, this::loadArtifactDependenciesFromDB, this::loadModIdAndBranchInfoFromDB, () -> null);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Unable to fully structure code
     */
    private void updateInternalState(@NotNull BuildPromotionEx.UpdateReason var1_1, @NotNull Supplier<Map<String, Object>> var2_2, @NotNull Supplier<IncompleteInfo> var3_3, @NotNull Supplier<List<ArtifactDepTuple>> var4_4, @NotNull Supplier<ModIdAndBranchTuple> var5_5, @NotNull Supplier<List<BuildRevisionData>> var6_6) {
        block34: {
            block33: {
                try {
                    if (var1_1 == null) {
                        BuildPromotionImpl.$$$reportNull$$$0(196);
                    }
                }
                catch (IllegalStateException v0) {
                    throw BuildPromotionImpl.a(v0);
                }
                try {
                    if (var2_2 == null) {
                        BuildPromotionImpl.$$$reportNull$$$0(197);
                    }
                }
                catch (IllegalStateException v1) {
                    throw BuildPromotionImpl.a(v1);
                }
                try {
                    if (var3_3 == null) {
                        BuildPromotionImpl.$$$reportNull$$$0(198);
                    }
                }
                catch (IllegalStateException v2) {
                    throw BuildPromotionImpl.a(v2);
                }
                try {
                    if (var4_4 == null) {
                        BuildPromotionImpl.$$$reportNull$$$0(199);
                    }
                }
                catch (IllegalStateException v3) {
                    throw BuildPromotionImpl.a(v3);
                }
                try {
                    if (var5_5 == null) {
                        BuildPromotionImpl.$$$reportNull$$$0(200);
                    }
                }
                catch (IllegalStateException v4) {
                    throw BuildPromotionImpl.a(v4);
                }
                try {
                    if (var6_6 == null) {
                        BuildPromotionImpl.$$$reportNull$$$0(201);
                    }
                }
                catch (IllegalStateException v5) {
                    throw BuildPromotionImpl.a(v5);
                }
                this.updateQueuedTime();
                this.myProblems.updateBuildProblems(var1_1);
                var7_7 = this.getOwnerNodeUrl();
                var8_8 = this.updateAttributes(var2_2);
                if (var8_8.contains("teamcity.build.freezeSettings")) ** GOTO lbl48
                try {
                    block35: {
                        if (!var8_8.contains("teamcity.build.hasFrozenSettings")) break block33;
                        break block35;
                        catch (IllegalStateException v6) {
                            throw BuildPromotionImpl.a(v6);
                        }
                    }
                    this.resetBuildSettings();
                }
                catch (IllegalStateException v7) {
                    throw BuildPromotionImpl.a(v7);
                }
            }
            this.updateRevisions(var8_8, var5_5, var6_6);
            var9_9 = this;
            synchronized (var9_9) {
                var10_10 = var4_4.get();
                try {
                    if (var10_10 != null) {
                        this.updateArtifactDependenciesFromTuples(var10_10);
                    }
                }
                catch (IllegalStateException v8) {
                    throw BuildPromotionImpl.a(v8);
                }
            }
            this.updateIncompleteState(var3_3.get());
            if (var8_8.contains("teamcity.build.ownerNodeUrl")) {
                this.logOwnerUrlChange(this.getOwnerNodeUrl(), var7_7);
                var9_9 = this.getOwnerNode();
                try {
                    try {
                        if (var9_9 == null || !var9_9.isCurrent()) break block34;
                    }
                    catch (IllegalStateException v9) {
                        throw BuildPromotionImpl.a(v9);
                    }
                    this.refreshBuildLog();
                    this.resetBuildSettings();
                }
                catch (IllegalStateException v10) {
                    throw BuildPromotionImpl.a(v10);
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     */
    private void updateRevisions(@NotNull Set<String> set, @NotNull Supplier<ModIdAndBranchTuple> supplier, @NotNull Supplier<List<BuildRevisionData>> supplier2) {
        block30: {
            ModIdAndBranchTuple modIdAndBranchTuple;
            block31: {
                try {
                    if (set == null) {
                        BuildPromotionImpl.$$$reportNull$$$0(202);
                    }
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
                try {
                    if (supplier == null) {
                        BuildPromotionImpl.$$$reportNull$$$0(203);
                    }
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
                try {
                    if (supplier2 == null) {
                        BuildPromotionImpl.$$$reportNull$$$0(204);
                    }
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
                try {
                    if (this.isBuildRevisionsChangedAndNotPersisted()) {
                        return;
                    }
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
                try {
                    if (set.contains("teamcity.collectChanges.error")) {
                        Loggers.SERVER.debug("Detected collect changes error for " + LogUtil.describe((BuildPromotion)this) + ": " + this.getCollectChangesError());
                        this.myRevisions = this.createNotAvailableRevisions();
                        this.myChainModificationId = null;
                        this.myModificationId = null;
                        this.myChangesDetached = true;
                        this.resetChangesCache();
                        return;
                    }
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
                modIdAndBranchTuple = supplier.get();
                if (modIdAndBranchTuple == null) break block30;
                try {
                    block35: {
                        if (!Objects.equals(modIdAndBranchTuple.getModId(), this.myModificationId)) break block31;
                        break block35;
                        catch (IllegalStateException illegalStateException) {
                            throw BuildPromotionImpl.a(illegalStateException);
                        }
                    }
                    if (Objects.equals(modIdAndBranchTuple.myChainModId, this.myChainModificationId)) break block30;
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
            }
            List<BuildRevisionData> list = supplier2.get();
            List<BuildRevisionEx> list2 = null;
            Map<VcsRootInstance, VcsRootInstance> map = null;
            if (list != null) {
                list2 = this.createRevisions(list);
                map = this.createOverriddenRootsMap(list);
            }
            BuildPromotionImpl buildPromotionImpl = this;
            synchronized (buildPromotionImpl) {
                block34: {
                    block33: {
                        block32: {
                            if (this.isBuildRevisionsChangedAndNotPersisted()) {
                                return;
                            }
                            if (modIdAndBranchTuple.getModId() == null) {
                                modIdAndBranchTuple = BuildPromotionImpl.getModIdAndBranchInfo(this.getContext().getSQLRunner(), Collections.singletonList(this.myId)).get(this.myId);
                                if (modIdAndBranchTuple == null) {
                                    return;
                                }
                                try {
                                    if (!Objects.equals(modIdAndBranchTuple.getModId(), this.myModificationId) || !Objects.equals(modIdAndBranchTuple.myChainModId, this.myChainModificationId)) break block32;
                                }
                                catch (IllegalStateException illegalStateException) {
                                    throw BuildPromotionImpl.a(illegalStateException);
                                }
                                return;
                            }
                        }
                        try {
                            if (list2 == null) break block33;
                            this.myRevisions = list2;
                            this.myOverridenVcsRoots = map;
                            break block34;
                        }
                        catch (IllegalStateException illegalStateException) {
                            throw BuildPromotionImpl.a(illegalStateException);
                        }
                    }
                    this.myRevisions = null;
                    this.myOverridenVcsRoots = null;
                }
                this.myFinalBranchName = StringPool.getFromPool((String)modIdAndBranchTuple.getBranchName());
                this.myChainModificationId = modIdAndBranchTuple.getChainModId();
                this.myModificationId = modIdAndBranchTuple.getModId();
            }
        }
        this.myComputedVcsRootEntries.set(null);
        this.resetChangesCache();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean isBuildRevisionsChangedAndNotPersisted() {
        BuildPromotionImpl buildPromotionImpl = this;
        synchronized (buildPromotionImpl) {
            return (this.myDirtyState & 2) > 0;
        }
    }

    private void updateQueuedTime() {
        if (this.myQueuedTime == null) {
            long l = (Long)this.getContext().getSQLRunner().withDB(dBFunctions -> dBFunctions.queryForSingleLong("select queued_time from build_state where id = ?", 0L, new Object[]{this.myId}));
            try {
                if (l > 0L) {
                    this.myQueuedTime = new Date(l);
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void updateStateFromBuildData(@NotNull AbstractBuildData abstractBuildData) {
        block15: {
            try {
                if (abstractBuildData == null) {
                    BuildPromotionImpl.$$$reportNull$$$0(205);
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
            try {
                if (this.myAssociatedBuildId != null || abstractBuildData.myBuildId <= 0L) break block15;
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
            Object object = this.myAssociatedBuildIdLock;
            synchronized (object) {
                if (this.myAssociatedBuildId == null) {
                    this.myAssociatedBuildId = abstractBuildData.myBuildId;
                }
            }
        }
        try {
            if (this.myQueuedTime == null) {
                this.myQueuedTime = abstractBuildData.myQueuedTime;
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        try {
            if (this.myRemoveFromQueueTime == null) {
                this.myRemoveFromQueueTime = abstractBuildData.myRemoveFromQueueTime;
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Set<String> updateAttributes(@NotNull Supplier<Map<String, Object>> supplier) {
        try {
            if (supplier == null) {
                BuildPromotionImpl.$$$reportNull$$$0(206);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        BuildPromotionImpl buildPromotionImpl = this;
        synchronized (buildPromotionImpl) {
            THashMap tHashMap = new THashMap(supplier.get());
            for (String string2 : this.myNotPersistedAttributes) {
                Object object2 = this.myAttributes.get().get((Object)string2);
                try {
                    if (object2 == null) {
                        tHashMap.remove((Object)string2);
                        continue;
                    }
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
                tHashMap.put((Object)string2, object2);
            }
            HashSet hashSet = new HashSet();
            this.myAttributes.get().forEach((string, object) -> {
                try {
                    if (!object.equals(tHashMap.get(string))) {
                        hashSet.add(string);
                    }
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
            });
            tHashMap.forEach((string, object) -> {
                try {
                    if (!object.equals(this.myAttributes.get().get(string))) {
                        hashSet.add(string);
                    }
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
            });
            this.myAttributes.set((THashMap<String, Object>)tHashMap);
            return hashSet;
        }
    }

    @NotNull
    private Map<String, Object> loadAttributesFromDb() {
        Map map;
        Map<Long, Map<String, Object>> map2 = BuildPromotionImpl.getAttributesForPromos(this.getContext().getSQLRunner(), Collections.singletonList(this.myId));
        try {
            map = map2.getOrDefault(this.myId, new HashMap());
            if (map == null) {
                BuildPromotionImpl.$$$reportNull$$$0(207);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return map;
    }

    private void persistRevisions(@NotNull DBFunctions dBFunctions, @NotNull Collection<BuildRevisionEx> collection) {
        try {
            if (dBFunctions == null) {
                BuildPromotionImpl.$$$reportNull$$$0(208);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        try {
            if (collection == null) {
                BuildPromotionImpl.$$$reportNull$$$0(209);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        dBFunctions.executeDml("delete from build_revisions where build_state_id = ?", new Object[]{this.myId});
        SAVE_ROOT_REVISION_QUERY.executeBatch(dBFunctions, (Iterator)new /* Unavailable Anonymous Inner Class!! */);
        ArrayList<VcsRootInstanceEntry> arrayList = new ArrayList<VcsRootInstanceEntry>();
        collection.forEach(buildRevisionEx -> arrayList.add(buildRevisionEx.getEntry()));
        this.persistVcsRootInstanceEntries(dBFunctions, arrayList, true);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void persistVcsRootEntries() {
        boolean bl;
        try {
            if (!this.isChangeCollectingNeeded()) {
                return;
            }
        }
        catch (Throwable throwable) {
            throw BuildPromotionImpl.a(throwable);
        }
        List<VcsRootInstanceEntry> list = this.computeVcsRootsFromParentSettings();
        try {
            if (list.isEmpty()) {
                return;
            }
        }
        catch (Throwable throwable) {
            throw BuildPromotionImpl.a(throwable);
        }
        try {
            BuildPromotionImpl buildPromotionImpl = this;
            synchronized (buildPromotionImpl) {
                if (!this.isChangeCollectingNeeded()) {
                    return;
                }
                bl = (Boolean)this.getContext().getSQLRunner().runAndRetry(dBFunctions -> {
                    boolean bl;
                    try {
                        bl = dBFunctions.queryForSingleInt("select count(*) from build_checkout_rules where build_state_id = ?", 0, new Object[]{this.myId}) > 0;
                    }
                    catch (DBException dBException) {
                        throw BuildPromotionImpl.a(dBException);
                    }
                    boolean bl2 = bl;
                    try {
                        if (bl2) {
                            return false;
                        }
                    }
                    catch (DBException dBException) {
                        throw BuildPromotionImpl.a(dBException);
                    }
                    this.persistVcsRootInstanceEntries(dBFunctions, list, false);
                    return true;
                });
            }
        }
        catch (Throwable throwable) {
            LOG.warnAndDebugDetails("Could not store VCS root instance entries: " + String.valueOf(list) + " into the build promotion: " + LogUtil.describe((BuildPromotion)this), throwable);
            return;
        }
        try {
            this.myComputedVcsRootEntries.set(null);
            if (!bl) {
                return;
            }
        }
        catch (Throwable throwable) {
            throw BuildPromotionImpl.a(throwable);
        }
        this.getContext().getNodesBuildsEvents().publishBuildPromotionPersisted((BuildPromotion)this, 2);
    }

    public void persistDependencies() {
        this.myDependencies.persistDependencies();
    }

    private void persistVcsRootInstanceEntries(@NotNull DBFunctions dBFunctions, @NotNull List<VcsRootInstanceEntry> list, boolean bl) {
        try {
            if (dBFunctions == null) {
                BuildPromotionImpl.$$$reportNull$$$0(210);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        try {
            if (list == null) {
                BuildPromotionImpl.$$$reportNull$$$0(211);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        ArrayList<VcsRootEntryTuple> arrayList = new ArrayList<VcsRootEntryTuple>();
        for (VcsRootInstanceEntry vcsRootInstanceEntry : list) {
            String string;
            block14: {
                String string2;
                block16: {
                    block15: {
                        string = vcsRootInstanceEntry.getCheckoutRules().getAsString();
                        try {
                            try {
                                if (string.length() <= 16000) break block14;
                                if (bl) break block15;
                            }
                            catch (IllegalStateException illegalStateException) {
                                throw BuildPromotionImpl.a(illegalStateException);
                            }
                            LOG.warn(String.format("Cannot persist VCS roots for the build promotion with id %d because length of the checkout rules for the VCS root instance with id %d exceeds maximum column length in the database, checkout rules length: %d, maximum length: %d", this.myId, vcsRootInstanceEntry.getVcsRoot().getId(), string.length(), 16000));
                            return;
                        }
                        catch (IllegalStateException illegalStateException) {
                            throw BuildPromotionImpl.a(illegalStateException);
                        }
                    }
                    string2 = String.format("Length of the checkout rules for the VCS root instance with id %d exceeds maximum column length in the database, checkout rules length: %d, maximum length: %d. Checkout rules will be truncated.", vcsRootInstanceEntry.getVcsRoot().getId(), string.length(), 16000);
                    try {
                        if (!LOG.isDebugEnabled()) break block16;
                        LOG.debug(string2 + " Rules before truncation: " + string);
                        break block14;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildPromotionImpl.a(illegalStateException);
                    }
                }
                LOG.warn(string2);
            }
            arrayList.add(new VcsRootEntryTuple(vcsRootInstanceEntry.getVcsRoot().getId(), UnicodeString.unicode((String)string, (int)16000)));
        }
        dBFunctions.executeDml("delete from build_checkout_rules where build_state_id = ?", new Object[]{this.myId});
        for (VcsRootEntryTuple vcsRootEntryTuple : arrayList) {
            dBFunctions.executeDml("insert into build_checkout_rules (build_state_id, vcs_root_id, checkout_rules) values (?, ?, ?)", new Object[]{this.myId, vcsRootEntryTuple.id, vcsRootEntryTuple.checkoutRules});
        }
    }

    private void persistModificationIdsAndBranch(@NotNull DBFunctions dBFunctions) {
        block10: {
            try {
                if (dBFunctions == null) {
                    BuildPromotionImpl.$$$reportNull$$$0(212);
                }
            }
            catch (MySqlIncorrectStringValueException mySqlIncorrectStringValueException) {
                throw BuildPromotionImpl.a(mySqlIncorrectStringValueException);
            }
            try {
                if (this.myModificationId == null && this.myChainModificationId == null) break block10;
            }
            catch (MySqlIncorrectStringValueException mySqlIncorrectStringValueException) {
                throw BuildPromotionImpl.a(mySqlIncorrectStringValueException);
            }
            String string = "update build_state set modification_id = ?, chain_modification_id = ?, branch_name = ? where id = ?";
            try {
                dBFunctions.executeDml("update build_state set modification_id = ?, chain_modification_id = ?, branch_name = ? where id = ?", new Object[]{this.myModificationId, this.myChainModificationId, UnicodeString.unicodeOrNull((String)StringUtil.nullIfEmpty((String)this.myFinalBranchName)), this.myId});
            }
            catch (MySqlIncorrectStringValueException mySqlIncorrectStringValueException) {
                try {
                    if (this.myFinalBranchName == null) {
                        throw mySqlIncorrectStringValueException;
                    }
                }
                catch (MySqlIncorrectStringValueException mySqlIncorrectStringValueException2) {
                    throw BuildPromotionImpl.a(mySqlIncorrectStringValueException2);
                }
                String string2 = StringUtil.removeNonBMPCharacters((String)this.myFinalBranchName);
                dBFunctions.executeDml("update build_state set modification_id = ?, chain_modification_id = ?, branch_name = ? where id = ?", new Object[]{this.myModificationId, this.myChainModificationId, UnicodeString.unicodeOrNull((String)string2), this.myId});
                this.logInvalidCharsInBranchName(this.myFinalBranchName, mySqlIncorrectStringValueException);
            }
        }
    }

    private void persistOverridenRoots(@NotNull DBFunctions dBFunctions) {
        try {
            if (dBFunctions == null) {
                BuildPromotionImpl.$$$reportNull$$$0(213);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        dBFunctions.executeDml("delete from build_overriden_roots where build_state_id = ?", new Object[]{this.myId});
        if (this.hasOverridenVcsRoots()) {
            for (Map.Entry<VcsRootInstance, VcsRootInstance> entry : this.myOverridenVcsRoots.entrySet()) {
                dBFunctions.executeDml("insert into build_overriden_roots (build_state_id, original_vcs_root_id, substitution_vcs_root_id) values (?, ?, ?)", new Object[]{this.myId, entry.getKey().getId(), entry.getValue().getId()});
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean createPromotionInDbIfNeeded() {
        BuildPromotionImpl buildPromotionImpl = this;
        synchronized (buildPromotionImpl) {
            if (this.myCreatedInDb) {
                return false;
            }
            Pair pair = this.myDependencies.getNotPersistedDependencies();
            this.getContext().getSQLRunner().runAndRetryNoResult(dBFunctions -> {
                new InsertBuildPromotionQuery(dBFunctions).insert(this.myId, this.getParentBuildTypeId(), this.myPersonal, this.myPersonalChangeId, this.myOwnerId, this.myChangesDetached, this.myFinalBranchName, this.myAssociatedBuildId, this.myQueuedTime, this.myRemoveFromQueueTime);
                this.storeAttributes(this.myNotPersistedAttributes, dBFunctions);
                this.storeProjectHierarchy(dBFunctions);
                this.myProjectPathIds.set(null);
                this.myDependencies.persistDependencies((Map)pair.first, (Set)pair.second, dBFunctions);
            });
            this.myCreatedInDb = true;
            this.resetDirtyFlag(32);
            this.myNotPersistedAttributes.clear();
            this.myDependencies.resetNotPersistedDependencies();
            LOG.info(LogUtil.describe((BuildPromotion)this) + " stored in the database");
            return true;
        }
    }

    private void resetDirtyFlag(int n) {
        try {
            if ((this.myDirtyState & n) > 0) {
                this.myDirtyState ^= n;
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
    }

    private void storeProjectHierarchy(@NotNull DBFunctions dBFunctions) {
        try {
            if (dBFunctions == null) {
                BuildPromotionImpl.$$$reportNull$$$0(214);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        List<BuildProjectEntry> list = this.getBuildProjectEntries();
        dBFunctions.executeBatchForTuples("insert into build_project (build_id,project_level,project_int_id) values (:buildId,:level,:projectIntId)", BuildProjectEntry.class, list);
    }

    @NotNull
    private List<BuildProjectEntry> getBuildProjectEntries() {
        ArrayList<BuildProjectEntry> arrayList;
        List list;
        ProjectEx projectEx;
        BuildTypeEx buildTypeEx = this.getBuildType();
        try {
            projectEx = buildTypeEx != null ? buildTypeEx.getProject() : null;
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        ProjectEx projectEx2 = projectEx;
        try {
            list = projectEx2 != null ? projectEx2.getProjectPath() : Collections.emptyList();
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        List list2 = list;
        ArrayList<BuildProjectEntry> arrayList2 = new ArrayList<BuildProjectEntry>(list2.size());
        int n = list2.size();
        for (int i = 0; i < n; ++i) {
            SProject sProject = (SProject)list2.get(i);
            arrayList2.add(new BuildProjectEntry(this.myId, i, sProject.getProjectId()));
        }
        try {
            arrayList = arrayList2;
            if (arrayList == null) {
                BuildPromotionImpl.$$$reportNull$$$0(215);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return arrayList;
    }

    private long getStartedTimestampOrFuture() {
        long l;
        long l2 = System.currentTimeMillis() + 600000L;
        try {
            l = this.myRemoveFromQueueTime == null ? l2 : this.myRemoveFromQueueTime.getTime();
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return l;
    }

    public void detachChanges() {
        this.myChangesDetached = true;
        SET_CHANGES_DETACHED_QUERY.executeUpdate((SQLRunner)this.getContext().getSQLRunner(), new Object[]{1, this.myId});
        this.commitCurrentTransaction();
        this.notifyChangesDetached();
    }

    private void notifyChangesDetached() {
        ((BuildPromotionListener)this.getContext().getPromotionEventDispatcher().getMulticaster()).buildPromotionChangesDetached((BuildPromotion)this);
    }

    public boolean isChangesDetached() {
        return this.myChangesDetached;
    }

    @Nullable
    public SBuild getSequenceBuild() {
        OrderedBuild orderedBuild;
        block16: {
            SBuild sBuild;
            BuildTypeOrderedBuilds buildTypeOrderedBuilds;
            block15: {
                Long l = BuildPromotionImpl.modId(this.getLastModificationId());
                try {
                    if (l == null) {
                        return this.getAssociatedBuild();
                    }
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
                BuildTypeEx buildTypeEx = this.getBuildType();
                try {
                    if (buildTypeEx == null) {
                        return null;
                    }
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
                buildTypeOrderedBuilds = buildTypeEx.getBuildTypeOrderedBuilds();
                sBuild = this.getRealOrDummyBuild();
                List list = buildTypeOrderedBuilds.getBuildsBefore(sBuild);
                orderedBuild = null;
                try {
                    if (list.isEmpty() || !l.equals(((OrderedBuild)list.get(0)).getModificationId())) break block15;
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
                orderedBuild = (OrderedBuild)list.get(0);
                int n = 1;
                while (true) {
                    try {
                        if (n >= list.size() || !l.equals(((OrderedBuild)list.get(n)).getModificationId())) break block16;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildPromotionImpl.a(illegalStateException);
                    }
                    orderedBuild = (OrderedBuild)list.get(n);
                    ++n;
                }
            }
            List list = buildTypeOrderedBuilds.getBuildsAfter(sBuild);
            if (!list.isEmpty()) {
                orderedBuild = (OrderedBuild)list.get(0);
            }
        }
        try {
            if (orderedBuild == null) {
                return this.getAssociatedBuild();
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return this.getContext().getBuildsManager().findBuildInstanceById(orderedBuild.getBuildId());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void setFinalArtifactDependencies(@NotNull List<SArtifactDependency> list) {
        try {
            if (list == null) {
                BuildPromotionImpl.$$$reportNull$$$0(216);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        try {
            if (list.isEmpty()) {
                throw new IllegalArgumentException("Dependencies list must not be empty");
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        BuildPromotionImpl buildPromotionImpl = this;
        synchronized (buildPromotionImpl) {
            this.myFinalArtifactDependencies = new ArrayList<SArtifactDependency>(list);
        }
        this.markDirtyAndResetCompatibilityCaches(64);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void setCustomArtifactDependencies(@NotNull List<SArtifactDependency> list) {
        try {
            if (list == null) {
                BuildPromotionImpl.$$$reportNull$$$0(217);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        BuildPromotionImpl buildPromotionImpl = this;
        synchronized (buildPromotionImpl) {
            this.myCustomArtifactDependencies = new ArrayList<SArtifactDependency>(list);
        }
        this.markDirtyAndResetCompatibilityCaches(8);
    }

    private void persistCustomArtifactDependencies() {
        this.getContext().getSQLRunner().runAndRetryNoResult(dBFunctions -> {
            dBFunctions.executeDml("delete from build_artifact_dependency where build_state_id = ?", new Object[]{this.myId});
            this.storeDependencies(dBFunctions, this.myCustomArtifactDependencies, "build_artifact_dependency");
        });
    }

    private void persistFinalArtifactDependencies() {
        this.getContext().getSQLRunner().runAndRetryNoResult(dBFunctions -> {
            dBFunctions.executeDml("delete from final_artifact_dependency where build_state_id = ?", new Object[]{this.myId});
            this.storeDependencies(dBFunctions, this.myFinalArtifactDependencies, "final_artifact_dependency");
        });
    }

    private void storeDependencies(@NotNull DBFunctions dBFunctions, @NotNull List<SArtifactDependency> list, @NotNull String string) {
        try {
            if (dBFunctions == null) {
                BuildPromotionImpl.$$$reportNull$$$0(218);
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw BuildPromotionImpl.a(numberFormatException);
        }
        try {
            if (list == null) {
                BuildPromotionImpl.$$$reportNull$$$0(219);
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw BuildPromotionImpl.a(numberFormatException);
        }
        try {
            if (string == null) {
                BuildPromotionImpl.$$$reportNull$$$0(220);
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw BuildPromotionImpl.a(numberFormatException);
        }
        try {
            if (list.isEmpty()) {
                return;
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw BuildPromotionImpl.a(numberFormatException);
        }
        ArrayList<ArtifactDepTuple> arrayList = new ArrayList<ArtifactDepTuple>(list.size());
        for (SArtifactDependency sArtifactDependency : list) {
            UnicodeString unicodeString;
            Long l;
            String string2;
            String string3;
            long l2;
            String string4;
            ArtifactDepTuple artifactDepTuple;
            ArtifactDepTuple artifactDepTuple2;
            ArrayList<ArtifactDepTuple> arrayList2;
            String string5;
            Long l3 = null;
            String string6 = sArtifactDependency.getRevisionRule().getRevision();
            if (string6.endsWith(".tcbuildid")) {
                string5 = string6.substring(0, string6.length() - ".tcbuildid".length());
                try {
                    l3 = Long.parseLong(string5);
                }
                catch (NumberFormatException numberFormatException) {
                    LOG.warn("Invalid revision rule '" + string6 + "' in the artifact dependency of the build promotion: " + LogUtil.describe((BuildPromotion)this));
                }
            }
            string5 = sArtifactDependency.getRevisionRule().getBranch();
            try {
                ArtifactDepTuple artifactDepTuple3;
                arrayList2 = arrayList;
                artifactDepTuple2 = artifactDepTuple3;
                artifactDepTuple = artifactDepTuple3;
                string4 = this.truncateAndLog(sArtifactDependency.getId(), 255, "Artifact Dependency Id");
                l2 = this.myId;
                string3 = this.truncateAndLog(sArtifactDependency.getSourceBuildTypeId(), 80, "Source build type id");
                string2 = this.truncateAndLog(string6, 80, "Revision Rule");
                l = l3;
                unicodeString = string5 == null ? null : new UnicodeString(this.truncateAndLog(string5, 255, "Branch"));
            }
            catch (NumberFormatException numberFormatException) {
                throw BuildPromotionImpl.a(numberFormatException);
            }
            artifactDepTuple2(string4, l2, string3, string2, l, unicodeString, sArtifactDependency.getSourcePaths(), false);
            arrayList2.add(artifactDepTuple);
        }
        dBFunctions.executeBatchForTuples("insert into " + string + " (artif_dep_id, build_state_id, source_build_type_id, revision_rule, branch_name, source_build_state_id, src_paths) values (:myId, :myBuildStateId, :mySourceBuildTypeId, :myRevisionRule, :myBranch, :mySourceBuildStateId, :mySrcPaths)", ArtifactDepTuple.class, arrayList);
    }

    private String truncateAndLog(String string, int n, String string2) {
        try {
            if (string == null) {
                return null;
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        int n2 = string.length();
        try {
            if (n2 <= n) {
                return string;
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        String string3 = "The " + string2 + " \"" + string + "\" is too long (" + n2 + " characters) and will be truncated to " + n + " characters.";
        LOG.warn(string3);
        return string.substring(0, n);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @NotNull
    public synchronized List<SArtifactDependency> getArtifactDependencies() {
        try {
            List list;
            try {
                list = this.getBuildSettings().getArtifactDependencies();
            }
            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                try {
                    List<SArtifactDependency> list2 = Collections.emptyList();
                    if (list2 != null) List<SArtifactDependency> list3;
                    return list3;
                    BuildPromotionImpl.$$$reportNull$$$0(222);
                    return list2;
                }
                catch (BuildTypeNotFoundException buildTypeNotFoundException2) {
                    throw BuildPromotionImpl.a(buildTypeNotFoundException2);
                }
            }
            if (list != null) List list4;
            return list4;
            BuildPromotionImpl.$$$reportNull$$$0(221);
            return list;
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
    }

    private void loadArtifactDependenciesIfNeeded() {
        block4: {
            try {
                try {
                    if (this.myCustomArtifactDependencies != null && this.myFinalArtifactDependencies != null) break block4;
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
                this.updateArtifactDependenciesFromTuples(this.loadArtifactDependenciesFromDB());
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
        }
    }

    private synchronized boolean hasCachedArtifactDependenciesInfo() {
        boolean bl;
        block5: {
            block4: {
                try {
                    try {
                        if (this.myCustomArtifactDependencies == null || this.myFinalArtifactDependencies == null) break block4;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildPromotionImpl.a(illegalStateException);
                    }
                    bl = true;
                    break block5;
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
            }
            bl = false;
        }
        return bl;
    }

    private void updateArtifactDependenciesFromTuples(@NotNull List<ArtifactDepTuple> list) {
        try {
            if (list == null) {
                BuildPromotionImpl.$$$reportNull$$$0(223);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        this.myFinalArtifactDependencies = list.stream().filter(artifactDepTuple -> artifactDepTuple.myFinalDependency).map(this::toArtifactDependency).collect(Collectors.toList());
        this.myCustomArtifactDependencies = list.stream().filter(artifactDepTuple -> {
            boolean bl;
            try {
                bl = !artifactDepTuple.myFinalDependency;
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
            return bl;
        }).map(this::toArtifactDependency).collect(Collectors.toList());
    }

    @NotNull
    private List<ArtifactDepTuple> loadArtifactDependenciesFromDB() {
        List<ArtifactDepTuple> list;
        List<Object> list2;
        Map<Long, List<ArtifactDepTuple>> map = BuildPromotionImpl.getArtifactDependenciesInfo(this.getContext().getSQLRunner(), Collections.singletonList(this.myId));
        List<ArtifactDepTuple> list3 = map.get(this.myId);
        try {
            list2 = list3 == null ? Collections.emptyList() : list3;
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        try {
            list = list2;
            if (list2 == null) {
                BuildPromotionImpl.$$$reportNull$$$0(224);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return list;
    }

    @NotNull
    public synchronized List<SArtifactDependency> getCustomArtifactDependencies() {
        List<SArtifactDependency> list;
        try {
            this.loadArtifactDependenciesIfNeeded();
            list = this.myCustomArtifactDependencies;
            if (list == null) {
                BuildPromotionImpl.$$$reportNull$$$0(225);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return list;
    }

    @NotNull
    public synchronized List<SArtifactDependency> getFinalArtifactDependencies() {
        List<SArtifactDependency> list;
        try {
            this.loadArtifactDependenciesIfNeeded();
            list = this.myFinalArtifactDependencies;
            if (list == null) {
                BuildPromotionImpl.$$$reportNull$$$0(226);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return list;
    }

    public synchronized void setDesiredBranchName(@NotNull String string) {
        Map<String, Object> map;
        block10: {
            Object object;
            block11: {
                try {
                    if (string == null) {
                        BuildPromotionImpl.$$$reportNull$$$0(227);
                    }
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
                string = BuildPromotionImpl.toInternalName(string);
                BuildPromotionImpl.checkBranchNameLength(string);
                map = this.getAttributes();
                object = map.get("teamcity.build.finalBranchName");
                try {
                    try {
                        if (object == null) break block10;
                        if (!string.equals(object)) break block11;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildPromotionImpl.a(illegalStateException);
                    }
                    return;
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
            }
            throw new IllegalStateException("Final branch name for the build has been set already, branch name can't be changed: " + string + ", final branch: " + String.valueOf(object));
        }
        Object object = map.get("teamcity.build.desiredBranchName");
        try {
            if (string.equals(object)) {
                return;
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        this.setAttribute("teamcity.build.desiredBranchName", string);
    }

    @NotNull
    public String getInternalBranchName() {
        block10: {
            String string;
            block11: {
                block8: {
                    String string2;
                    block9: {
                        try {
                            try {
                                if (this.myFinalBranchName == null) break block8;
                                string2 = this.myFinalBranchName;
                                if (string2 != null) break block9;
                            }
                            catch (IllegalStateException illegalStateException) {
                                throw BuildPromotionImpl.a(illegalStateException);
                            }
                            BuildPromotionImpl.$$$reportNull$$$0(228);
                        }
                        catch (IllegalStateException illegalStateException) {
                            throw BuildPromotionImpl.a(illegalStateException);
                        }
                    }
                    return string2;
                }
                String string3 = this.getInternalDesiredBranchName();
                try {
                    try {
                        if (string3 == null) break block10;
                        string = string3;
                        if (string != null) break block11;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildPromotionImpl.a(illegalStateException);
                    }
                    BuildPromotionImpl.$$$reportNull$$$0(229);
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
            }
            return string;
        }
        return "";
    }

    @Nullable
    private String getInternalDesiredBranchName() {
        block6: {
            String string = this.getParameterValue("teamcity.build.branch");
            try {
                block7: {
                    try {
                        try {
                            if (string == null) break block6;
                            if (TeamCityProperties.getBooleanOrTrue((String)"teamcity.build.branch.alwaysAccept")) break block7;
                        }
                        catch (IllegalStateException illegalStateException) {
                            throw BuildPromotionImpl.a(illegalStateException);
                        }
                        if (!this.hasOverridenVcsRoots()) break block6;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildPromotionImpl.a(illegalStateException);
                    }
                }
                return BuildPromotionImpl.toInternalName(string);
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
        }
        return StringUtil.objectToString((Object)this.getAttribute("teamcity.build.desiredBranchName"));
    }

    private boolean hasOverridenVcsRoots() {
        boolean bl;
        block5: {
            block4: {
                try {
                    try {
                        if (this.myOverridenVcsRoots == null || this.myOverridenVcsRoots.isEmpty()) break block4;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildPromotionImpl.a(illegalStateException);
                    }
                    bl = true;
                    break block5;
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
            }
            bl = false;
        }
        return bl;
    }

    public boolean isCompositeBuild() {
        boolean bl;
        block5: {
            block4: {
                Object object = this.getAttribute("teamcity.build.composite");
                try {
                    try {
                        if (object == null || !Boolean.parseBoolean(object.toString())) break block4;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildPromotionImpl.a(illegalStateException);
                    }
                    bl = true;
                    break block5;
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
            }
            bl = false;
        }
        return bl;
    }

    @NotNull
    public String getCreatorNodeId() {
        block4: {
            String string;
            block5: {
                Object object = this.getAttribute("teamcity.build.creatorNodeId");
                try {
                    try {
                        if (object == null) break block4;
                        string = object.toString();
                        if (string != null) break block5;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildPromotionImpl.a(illegalStateException);
                    }
                    BuildPromotionImpl.$$$reportNull$$$0(230);
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
            }
            return string;
        }
        return "MAIN_SERVER";
    }

    public boolean allDependenciesFinished() {
        for (BuildDependency buildDependency : this.myDependencies.getDependencies()) {
            BuildPromotion buildPromotion = buildDependency.getDependOn();
            SBuild sBuild = buildPromotion.getAssociatedBuild();
            try {
                try {
                    if (sBuild != null && sBuild.isFinished()) continue;
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
                return false;
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
        }
        return true;
    }

    @NotNull
    public List<BuildPromotionEx> getDependentCompositePromotions() {
        ArrayList<BuildPromotionEx> arrayList;
        ArrayList<BuildPromotionEx> arrayList2 = new ArrayList<BuildPromotionEx>();
        try {
            this.traverseDependedOnMe((DependencyConsumer<BuildPromotionEx>)((DependencyConsumer)buildPromotionEx -> {
                try {
                    if (buildPromotionEx.isCompositeBuild()) {
                        arrayList2.add((BuildPromotionEx)buildPromotionEx);
                    }
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
                return DependencyConsumer.Result.CONTINUE;
            }));
            arrayList = arrayList2;
            if (arrayList == null) {
                BuildPromotionImpl.$$$reportNull$$$0(231);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return arrayList;
    }

    public int decreaseExpirationCounter(int n) {
        return this.myExpirationCounter -= n;
    }

    public void setExpirationCounter(int n) {
        this.myExpirationCounter = n;
    }

    @NotNull
    public BuildPromotionEx.ArtifactsStateInfo getArtifactStateInfo() {
        BuildPromotionEx.ArtifactsStateInfo artifactsStateInfo;
        block7: {
            ArtifactsStateInfoImpl artifactsStateInfoImpl;
            block8: {
                ArtifactsStateInfoImpl artifactsStateInfoImpl2 = this.myArtifactsStateRef.get();
                try {
                    try {
                        if (artifactsStateInfoImpl2 == null) break block7;
                        this.getContext().getSecurityContext().runAsSystemUnchecked(() -> {
                            block10: {
                                boolean bl;
                                block9: {
                                    block8: {
                                        try {
                                            try {
                                                if (this.myAssociatedBuildId != null && this.myAssociatedBuildFinished) break block8;
                                            }
                                            catch (Throwable throwable) {
                                                throw BuildPromotionImpl.a(throwable);
                                            }
                                            bl = true;
                                            break block9;
                                        }
                                        catch (Throwable throwable) {
                                            throw BuildPromotionImpl.a(throwable);
                                        }
                                    }
                                    bl = false;
                                }
                                boolean bl2 = bl;
                                try {
                                    try {
                                        if (!bl2 && artifactsStateInfoImpl2.stateAgeSeconds() <= TeamCityProperties.getInteger((String)"teamcity.finishedBuild.artifactsUpdate.intervalSec", (int)300)) break block10;
                                    }
                                    catch (Throwable throwable) {
                                        throw BuildPromotionImpl.a(throwable);
                                    }
                                    this.scheduleArtifactsStateUpdate();
                                }
                                catch (Throwable throwable) {
                                    throw BuildPromotionImpl.a(throwable);
                                }
                            }
                        });
                        artifactsStateInfoImpl = artifactsStateInfoImpl2;
                        if (artifactsStateInfoImpl != null) break block8;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildPromotionImpl.a(illegalStateException);
                    }
                    BuildPromotionImpl.$$$reportNull$$$0(232);
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
            }
            return artifactsStateInfoImpl;
        }
        try {
            this.updateArtifactsState();
            artifactsStateInfo = this.getArtifactStateInfo();
            if (artifactsStateInfo == null) {
                BuildPromotionImpl.$$$reportNull$$$0(233);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return artifactsStateInfo;
    }

    public boolean hasComputedArtifactsState() {
        boolean bl;
        try {
            bl = this.myArtifactsStateRef.get() != null;
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return bl;
    }

    private void scheduleArtifactsStateUpdate() {
        this.getContext().getBackgroundArtifactsStateUpdater().scheduleArtifactsStateUpdate((BuildPromotion)this, this::updateArtifactsState);
    }

    /*
     * Exception decompiling
     */
    @NotNull
    private BuildPromotionEx.ArtifactsState computeArtifactsState() {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 5 blocks at once
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

    @NotNull
    private BuildPromotionEx.ArtifactsState computeStateFromBuildArtifacts(@NotNull BuildArtifacts buildArtifacts) {
        BuildPromotionEx.ArtifactsState artifactsState;
        try {
            if (buildArtifacts == null) {
                BuildPromotionImpl.$$$reportNull$$$0(239);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        try {
            artifactsState = (BuildPromotionEx.ArtifactsState)this.getContext().getSecurityContext().runAsSystemUnchecked(() -> {
                BuildPromotionEx.ArtifactsState artifactsState = BuildPromotionEx.ArtifactsState.NO_ARTIFACTS;
                Collection collection = buildArtifacts.getRootArtifact().getChildren();
                for (BuildArtifact buildArtifact : collection) {
                    block3: {
                        String string = buildArtifact.getName();
                        try {
                            if (!".teamcity".equals(string) && !string.startsWith(".teamcity.")) break block3;
                        }
                        catch (Throwable throwable) {
                            throw BuildPromotionImpl.a(throwable);
                        }
                        artifactsState = BuildPromotionEx.ArtifactsState.HIDDEN_ONLY;
                        continue;
                    }
                    artifactsState = BuildPromotionEx.ArtifactsState.HAS_ARTIFACTS;
                    break;
                }
                return artifactsState;
            });
            if (artifactsState == null) {
                BuildPromotionImpl.$$$reportNull$$$0(240);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return artifactsState;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void artifactsChanged(boolean bl) {
        SBuild sBuild = this.getAssociatedBuild();
        try {
            this.resetArtifactsState(bl);
            if (sBuild == null) return;
        }
        catch (Throwable throwable) {
            try {
                if (sBuild == null) throw throwable;
                ((BuildServerListener)this.getContext().getEventDispatcher().getMulticaster()).buildArtifactsChanged(sBuild);
                this.getContext().getNodesBuildsEvents().publishBuildArtifactsChanged((BuildPromotion)this);
                throw throwable;
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
        }
        ((BuildServerListener)this.getContext().getEventDispatcher().getMulticaster()).buildArtifactsChanged(sBuild);
        this.getContext().getNodesBuildsEvents().publishBuildArtifactsChanged((BuildPromotion)this);
    }

    public boolean hasUnpublishedPromotionPersistingEvent() {
        return this.getContext().getNodesBuildsEvents().hasUnpublishedPromotionPersistingEvent((BuildPromotion)this);
    }

    public void resetArtifactsState(boolean bl) {
        try {
            this.myArtifactsStateRef.set(null);
            if (!bl) {
                return;
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        this.scheduleArtifactsStateUpdate();
    }

    private void updateArtifactsState() {
        ArtifactsStateInfoImpl artifactsStateInfoImpl = this.myArtifactsStateRef.get();
        ArtifactsStateInfoImpl artifactsStateInfoImpl2 = new ArtifactsStateInfoImpl(this.computeArtifactsState());
        try {
            if (!this.myArtifactsStateRef.compareAndSet(artifactsStateInfoImpl, artifactsStateInfoImpl2)) {
                this.updateArtifactsState();
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
    }

    @NotNull
    public BuildArtifacts getArtifacts(@NotNull BuildArtifactsViewMode buildArtifactsViewMode) {
        BuildArtifacts buildArtifacts;
        BuildArtifacts buildArtifacts2;
        block10: {
            try {
                if (buildArtifactsViewMode == null) {
                    BuildPromotionImpl.$$$reportNull$$$0(241);
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
            buildArtifacts2 = this.createBuildArtifacts(buildArtifactsViewMode);
            try {
                try {
                    if (this.myArtifactsStateRef.get() != null || !buildArtifactsViewMode.isViewPublic()) break block10;
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
                if (!buildArtifactsViewMode.isViewHidden()) break block10;
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
            BuildPromotionEx.ArtifactsState artifactsState = this.computeStateFromBuildArtifacts(buildArtifacts2);
            this.myArtifactsStateRef.compareAndSet(null, new ArtifactsStateInfoImpl(artifactsState));
        }
        try {
            buildArtifacts = buildArtifacts2;
            if (buildArtifacts == null) {
                BuildPromotionImpl.$$$reportNull$$$0(242);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return buildArtifacts;
    }

    /*
     * Could not resolve type clashes
     * Loose catch block
     */
    @NotNull
    private BuildArtifacts createBuildArtifacts(@NotNull BuildArtifactsViewMode buildArtifactsViewMode) {
        BuildArtifacts buildArtifacts;
        BuildArtifacts buildArtifacts2;
        Serializable serializable2;
        List<CachedBuildArtifacts> list;
        block22: {
            try {
                if (buildArtifactsViewMode == null) {
                    BuildPromotionImpl.$$$reportNull$$$0(243);
                }
            }
            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                throw BuildPromotionImpl.a(buildTypeNotFoundException);
            }
            list = this.myCachedArtifacts.get();
            if (list == null) break block22;
            for (Serializable serializable2 : list) {
                BuildArtifacts buildArtifacts3;
                block21: {
                    block23: {
                        if (!buildArtifactsViewMode.equals((Object)((CachedBuildArtifacts)serializable2).myMode)) continue;
                        if (serializable2.isExpired()) continue;
                        break block23;
                        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                            throw BuildPromotionImpl.a(buildTypeNotFoundException);
                        }
                    }
                    try {
                        block24: {
                            buildArtifacts3 = ((CachedBuildArtifacts)serializable2).myArtifacts;
                            if (buildArtifacts3 != null) break block21;
                            break block24;
                            catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                                throw BuildPromotionImpl.a(buildTypeNotFoundException);
                            }
                        }
                        BuildPromotionImpl.$$$reportNull$$$0(244);
                    }
                    catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                        throw BuildPromotionImpl.a(buildTypeNotFoundException);
                    }
                }
                return buildArtifacts3;
            }
        }
        BuildArtifactsFactory buildArtifactsFactory = this.getContext().getBuildArtifactsFactory();
        try {
            serializable2 = this.getArtifactsDirectory();
            buildArtifacts2 = buildArtifactsFactory.createBuildArtifacts((BuildPromotion)this, (File)serializable2, buildArtifactsViewMode);
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            buildArtifacts2 = buildArtifactsFactory.createEmptyArtifacts((BuildPromotion)this);
        }
        if (this.buildArtifactsCanBeCached()) {
            serializable2 = new ArrayList();
            if (list != null) {
                for (CachedBuildArtifacts cachedBuildArtifacts : list) {
                    try {
                        if (cachedBuildArtifacts.isExpired()) continue;
                        serializable2.add(cachedBuildArtifacts);
                    }
                    catch (BuildTypeNotFoundException buildTypeNotFoundException) {
                        throw BuildPromotionImpl.a(buildTypeNotFoundException);
                    }
                }
            }
            serializable2.add(new CachedBuildArtifacts(buildArtifactsViewMode, buildArtifacts2));
            this.myCachedArtifacts.compareAndSet(list, (List<CachedBuildArtifacts>)((Object)serializable2));
        } else {
            this.myCachedArtifacts.set(null);
        }
        try {
            buildArtifacts = buildArtifacts2;
            if (buildArtifacts == null) {
                BuildPromotionImpl.$$$reportNull$$$0(245);
            }
        }
        catch (BuildTypeNotFoundException buildTypeNotFoundException) {
            throw BuildPromotionImpl.a(buildTypeNotFoundException);
        }
        return buildArtifacts;
    }

    private boolean buildArtifactsCanBeCached() {
        boolean bl;
        block9: {
            block8: {
                try {
                    try {
                        try {
                            try {
                                if (!this.isCompositeBuild() || !TeamCityProperties.getBooleanOrTrue((String)"teamcity.buildPromotion.compositeBuild.artifactsCache.enabled")) break block8;
                            }
                            catch (IllegalStateException illegalStateException) {
                                throw BuildPromotionImpl.a(illegalStateException);
                            }
                            if (!this.isAssociatedBuildFinished()) break block8;
                        }
                        catch (IllegalStateException illegalStateException) {
                            throw BuildPromotionImpl.a(illegalStateException);
                        }
                        if (!this.allDependenciesFinished()) break block8;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildPromotionImpl.a(illegalStateException);
                    }
                    bl = true;
                    break block9;
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
            }
            bl = false;
        }
        return bl;
    }

    private boolean findCurrentCleanDestinationSetting(@NotNull String string, @NotNull String string2) {
        try {
            if (string == null) {
                BuildPromotionImpl.$$$reportNull$$$0(246);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        try {
            if (string2 == null) {
                BuildPromotionImpl.$$$reportNull$$$0(247);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        BuildTypeEx buildTypeEx = this.getBuildType();
        if (buildTypeEx != null) {
            for (SArtifactDependency sArtifactDependency : this.getBuildSettings().getParentSettings().getArtifactDependencies()) {
                try {
                    try {
                        try {
                            if (!buildTypeEx.isEnabled(sArtifactDependency.getId()) || !string.equals(sArtifactDependency.getSourceBuildTypeId())) continue;
                        }
                        catch (IllegalStateException illegalStateException) {
                            throw BuildPromotionImpl.a(illegalStateException);
                        }
                        if (!string2.equals(sArtifactDependency.getSourcePaths())) continue;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildPromotionImpl.a(illegalStateException);
                    }
                    return sArtifactDependency.isCleanDestinationFolder();
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
            }
        }
        return false;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("BuildPromotion");
        stringBuilder.append("{myId=").append(this.myId);
        stringBuilder.append(", myBuildTypeId='").append(this.getParentBuildTypeId()).append('\'');
        stringBuilder.append(", buildTypeExtId='").append(this.getBuildTypeExternalId()).append('\'');
        stringBuilder.append(", myModificationId=").append(this.myModificationId);
        stringBuilder.append(", myRevisions=").append(this.myRevisions);
        stringBuilder.append(", myPersonal=").append(this.myPersonal);
        stringBuilder.append(", myAssociatedBuildId=").append(this.myAssociatedBuildId);
        stringBuilder.append(", myBranch=").append(this.myFinalBranchName);
        Object object = this.getAttribute("teamcity.build.desiredBranchName");
        try {
            if (object instanceof String) {
                stringBuilder.append(", myDesiredBranchName=").append(object);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    public int compareTo(@NotNull BuildPromotion buildPromotion) {
        int n;
        try {
            if (buildPromotion == null) {
                BuildPromotionImpl.$$$reportNull$$$0(248);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        try {
            if (this.getId() == buildPromotion.getId()) {
                return 0;
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        try {
            if (this.isLaterThan(buildPromotion)) {
                return -1;
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        try {
            if (buildPromotion.isLaterThan((BuildPromotion)this)) {
                return 1;
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        try {
            n = this.getId() > buildPromotion.getId() ? -1 : 1;
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return n;
    }

    public boolean isChangesCollectingInProgress() {
        boolean bl;
        block5: {
            block4: {
                MultiNodeTasks.SubmittedTask submittedTask = this.getCheckingForChangesTask();
                try {
                    try {
                        if (submittedTask == null || submittedTask.isDone()) break block4;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildPromotionImpl.a(illegalStateException);
                    }
                    bl = true;
                    break block5;
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
            }
            bl = false;
        }
        return bl;
    }

    public void waitForChangesCollectingToFinish(long l) throws ExecutionException, InterruptedException, TimeoutException {
        MultiNodeTasks.SubmittedTask submittedTask = this.getCheckingForChangesTask();
        try {
            if (submittedTask != null) {
                submittedTask.waitFor(l, TimeUnit.MILLISECONDS);
            }
        }
        catch (ExecutionException executionException) {
            throw BuildPromotionImpl.a(executionException);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Nullable
    private MultiNodeTasks.SubmittedTask getCheckingForChangesTask() {
        AtomicReference<MultiNodeTasks.SubmittedTask> atomicReference = this.myCheckingForChangesTask;
        synchronized (atomicReference) {
            return this.myCheckingForChangesTask.get();
        }
    }

    public boolean scheduleCheckingForChangesIfNeeded() {
        return (Boolean)this.getContext().getSecurityContext().runAsSystemUnchecked(() -> {
            boolean bl = this.isChangeCollectingNeeded(true);
            try {
                if (!bl) {
                    return false;
                }
            }
            catch (Throwable throwable) {
                throw BuildPromotionImpl.a(throwable);
            }
            try {
                if (this.myCheckingForChangesTask.get() == null) {
                    this.flushBuildChainBuildLogs((BuildPromotionEx)this);
                    this.traverseSelfAndDependencies((DependencyConsumer<BuildPromotionEx>)((DependencyConsumer)buildPromotionEx -> {
                        try {
                            if (buildPromotionEx.isChangeCollectingNeeded()) {
                                buildPromotionEx.persistVcsRootEntries();
                            }
                        }
                        catch (IllegalStateException illegalStateException) {
                            throw BuildPromotionImpl.a(illegalStateException);
                        }
                        return DependencyConsumer.Result.CONTINUE;
                    }));
                }
            }
            catch (Throwable throwable) {
                throw BuildPromotionImpl.a(throwable);
            }
            AtomicReference<MultiNodeTasks.SubmittedTask> atomicReference = this.myCheckingForChangesTask;
            synchronized (atomicReference) {
                block14: {
                    MultiNodeTasks.SubmittedTask submittedTask;
                    block16: {
                        block15: {
                            submittedTask = this.myCheckingForChangesTask.get();
                            if (submittedTask == null) {
                                submittedTask = this.getContext().getMultiNodeTasks().findTask("buildChainChangesCollecting", String.valueOf(this.getId()));
                            }
                            try {
                                if (submittedTask == null) break block14;
                                if (submittedTask.isDone()) break block15;
                            }
                            catch (Throwable throwable) {
                                throw BuildPromotionImpl.a(throwable);
                            }
                            this.myCheckingForChangesTask.compareAndSet(null, submittedTask);
                            return true;
                        }
                        try {
                            this.myCheckingForChangesTask.set(null);
                            if (!submittedTask.isDoneSuccessfully()) break block14;
                            this.updateSelfAndDependenciesFromDB();
                            if (this.isChangeCollectingNeeded(true)) break block16;
                        }
                        catch (Throwable throwable) {
                            throw BuildPromotionImpl.a(throwable);
                        }
                        return false;
                    }
                    LOG.info("Creating another checking for changes task for the build promotion " + LogUtil.describe((BuildPromotion)this) + ", previous task: " + String.valueOf(submittedTask));
                }
                MultiNodeTasks multiNodeTasks = this.getContext().getMultiNodeTasks();
                this.myCheckingForChangesTask.set(multiNodeTasks.submit((MultiNodeTasks.Task)new MultiNodeTasks.TaskData("buildChainChangesCollecting", String.valueOf(this.getId()), Long.valueOf(this.getId()))));
                return true;
            }
        });
    }

    public void updateSelfAndDependenciesFromDB() {
        ArrayList<BuildPromotionEx> arrayList = new ArrayList<BuildPromotionEx>();
        this.traverseSelfAndDependencies((DependencyConsumer<BuildPromotionEx>)((DependencyConsumer)buildPromotionEx -> {
            arrayList.add((BuildPromotionEx)buildPromotionEx);
            return DependencyConsumer.Result.CONTINUE;
        }));
        BuildPromotionImpl.updatePromotions(arrayList, this.getContext().getSQLRunner(), BuildPromotionEx.UpdateReason.BUILD_PROMOTION_CHANGED);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     */
    public void cancelCheckingForChanges() {
        AtomicReference<MultiNodeTasks.SubmittedTask> atomicReference = this.myCheckingForChangesTask;
        synchronized (atomicReference) {
            block9: {
                MultiNodeTasks.SubmittedTask submittedTask;
                block10: {
                    submittedTask = this.myCheckingForChangesTask.get();
                    if (submittedTask == null) break block9;
                    if (submittedTask.isDone()) break block9;
                    break block10;
                    catch (IllegalStateException illegalStateException) {
                        throw BuildPromotionImpl.a(illegalStateException);
                    }
                }
                try {
                    block11: {
                        if (submittedTask.isCancelled()) break block9;
                        break block11;
                        catch (IllegalStateException illegalStateException) {
                            throw BuildPromotionImpl.a(illegalStateException);
                        }
                    }
                    submittedTask.cancel();
                    Loggers.SERVER.info("Checking for changes task for build promotion: " + LogUtil.describe((BuildPromotion)this) + " has been cancelled");
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
            }
        }
    }

    private void flushBuildChainBuildLogs(@NotNull BuildPromotionEx buildPromotionEx2) {
        try {
            if (buildPromotionEx2 == null) {
                BuildPromotionImpl.$$$reportNull$$$0(249);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        buildPromotionEx2.traverseSelfAndDependencies(buildPromotionEx -> {
            block4: {
                try {
                    try {
                        if (!buildPromotionEx.isCanceled() && !buildPromotionEx.isDeleted()) break block4;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildPromotionImpl.a(illegalStateException);
                    }
                    return DependencyConsumer.Result.CONTINUE;
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
            }
            buildPromotionEx.ensureBuildLogFlushed();
            return DependencyConsumer.Result.CONTINUE;
        });
    }

    @NotNull
    private List<BuildPromotionEx> collectTopPromotions() {
        ArrayList<BuildPromotionEx> arrayList;
        ArrayList<BuildPromotionEx> arrayList2 = new ArrayList<BuildPromotionEx>();
        try {
            this.traverseSelfAndDependedOnMe((DependencyConsumer<BuildPromotionEx>)new /* Unavailable Anonymous Inner Class!! */);
            arrayList = arrayList2;
            if (arrayList == null) {
                BuildPromotionImpl.$$$reportNull$$$0(250);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return arrayList;
    }

    public void lockDependenciesOptimizing() {
        this.myOptimizingDependenciesLock.lock();
    }

    public void unlockDependenciesOptimizing() {
        this.myOptimizingDependenciesLock.unlock();
    }

    public void addDependedOnMe(@NotNull BuildPromotionEx buildPromotionEx, @NotNull DependencyOptions dependencyOptions) {
        try {
            if (buildPromotionEx == null) {
                BuildPromotionImpl.$$$reportNull$$$0(251);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        try {
            if (dependencyOptions == null) {
                BuildPromotionImpl.$$$reportNull$$$0(252);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        this.myDependencies.addDependedOnMe((BuildPromotion)buildPromotionEx, dependencyOptions);
    }

    public void addDependedOnMeIfCached(@NotNull BuildPromotionEx buildPromotionEx, @NotNull DependencyOptions dependencyOptions) {
        try {
            if (buildPromotionEx == null) {
                BuildPromotionImpl.$$$reportNull$$$0(253);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        try {
            if (dependencyOptions == null) {
                BuildPromotionImpl.$$$reportNull$$$0(254);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        this.myDependencies.addDependedOnMeIfCached((BuildPromotion)buildPromotionEx, dependencyOptions);
    }

    public void removeDependedOnMe(@NotNull BuildPromotion buildPromotion) {
        try {
            if (buildPromotion == null) {
                BuildPromotionImpl.$$$reportNull$$$0(255);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        this.myDependencies.removeDependedOnMe(buildPromotion);
    }

    private static void checkBranchNameLength(@Nullable String string) {
        block4: {
            try {
                try {
                    if (string == null || string.length() <= 255) break block4;
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
                throw new IllegalArgumentException("Branch name is too long: " + string);
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
        }
    }

    public void addBuildProblem(@NotNull BuildProblemData buildProblemData) {
        block7: {
            block8: {
                try {
                    if (buildProblemData == null) {
                        BuildPromotionImpl.$$$reportNull$$$0(256);
                    }
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
                boolean bl = this.hasFailureReasons();
                try {
                    try {
                        if (!this.myProblems.addBuildProblem(buildProblemData)) break block7;
                        if (bl == this.hasFailureReasons()) break block8;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildPromotionImpl.a(illegalStateException);
                    }
                    LOG.info("Changed build status of the build " + LogUtil.describe((BuildPromotion)this) + " due to a build problem " + LogUtil.describe((Object)buildProblemData));
                    this.debugToBuildLog("Changed build status to failed due to build problem: " + buildProblemData.getIdentity());
                    this.getContext().getNodesBuildsEvents().publishBuildStatusChanged((BuildPromotion)this);
                    break block7;
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
            }
            this.getContext().getNodesBuildsEvents().publishBuildProblemsUpdated((BuildPromotion)this);
        }
    }

    private void debugToBuildLog(@NotNull String string) {
        try {
            if (string == null) {
                BuildPromotionImpl.$$$reportNull$$$0(257);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        BuildLogEx buildLogEx = this.getBuildLog();
        buildLogEx.messageAsync(string, Status.NORMAL, MessageAttrs.serverMessage().internalize());
    }

    @NotNull
    public List<BuildProblemData> getFailureReasons() {
        List list;
        try {
            list = this.myProblems.getFailureReasons();
            if (list == null) {
                BuildPromotionImpl.$$$reportNull$$$0(258);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return list;
    }

    public boolean hasFailureReasons() {
        return this.myProblems.hasFailureReasons();
    }

    public void muteBuildProblems(boolean bl, @Nullable SUser sUser, @Nullable String string) {
        block6: {
            Object object;
            Object object2;
            String string2;
            BuildPromotionImpl buildPromotionImpl;
            block8: {
                block7: {
                    List<BuildProblemData> list = this.getFailureReasons();
                    try {
                        try {
                            LOG.info("Muting build problems for the build " + LogUtil.describe((BuildPromotion)this) + ": " + CollectionsUtil.to_s(list));
                            if (list.isEmpty()) break block6;
                            buildPromotionImpl = this;
                            string2 = CollectionsUtil.to_s(list);
                            if (sUser == null) break block7;
                        }
                        catch (IllegalStateException illegalStateException) {
                            throw BuildPromotionImpl.a(illegalStateException);
                        }
                        object2 = " by " + LogUtil.describe((Loggable)sUser);
                        break block8;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildPromotionImpl.a(illegalStateException);
                    }
                }
                object2 = "";
            }
            try {
                object = string != null ? " (" + string + ")" : "";
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
            buildPromotionImpl.debugToBuildLog("Changed build status to successful after build problem muting: " + string2 + "\n" + (String)object2 + (String)object);
        }
        this.myProblems.muteBuildProblems(bl, sUser, string);
        this.getContext().getNodesBuildsEvents().publishBuildProblemsUpdated((BuildPromotion)this);
    }

    @NotNull
    public List<BuildProblem> getBuildProblems() {
        List list;
        try {
            list = this.myProblems.getBuildProblems(true);
            if (list == null) {
                BuildPromotionImpl.$$$reportNull$$$0(259);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return list;
    }

    @NotNull
    public Collection<BuildProblem> getUnsortedBuildProblems() {
        List list;
        try {
            list = this.myProblems.getBuildProblems(false);
            if (list == null) {
                BuildPromotionImpl.$$$reportNull$$$0(260);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return list;
    }

    /*
     * Loose catch block
     */
    @NotNull
    public List<BuildProblem> getBuildChainProblems(boolean bl) {
        ArrayList<BuildProblem> arrayList;
        BuildDependencyGraph buildDependencyGraph;
        ArrayList<BuildProblem> arrayList2;
        block18: {
            ArrayList<BuildProblem> arrayList3;
            block19: {
                arrayList2 = new ArrayList<BuildProblem>();
                buildDependencyGraph = this.getDependencyGraph();
                if (bl) break block18;
                try {
                    block21: {
                        buildDependencyGraph.traverseTopDown(buildPromotion -> {
                            arrayList2.addAll(((BuildPromotionEx)buildPromotion).getBuildProblems());
                            return true;
                        });
                        arrayList3 = arrayList2;
                        if (arrayList3 != null) break block19;
                        break block21;
                        catch (NumberFormatException numberFormatException) {
                            throw BuildPromotionImpl.a(numberFormatException);
                        }
                    }
                    BuildPromotionImpl.$$$reportNull$$$0(261);
                }
                catch (NumberFormatException numberFormatException) {
                    throw BuildPromotionImpl.a(numberFormatException);
                }
            }
            return arrayList3;
        }
        Map<Long, BuildPromotionEx> map = buildDependencyGraph.getNodes().stream().collect(Collectors.toMap(BuildPromotion::getId, buildPromotion -> (BuildPromotionEx)buildPromotion));
        LinkedList<BuildPromotionImpl> linkedList = new LinkedList<BuildPromotionImpl>();
        linkedList.add(this);
        HashSet<BuildPromotionEx> hashSet = new HashSet<BuildPromotionEx>();
        while (!linkedList.isEmpty()) {
            BuildPromotionEx buildPromotionEx = (BuildPromotionEx)linkedList.poll();
            try {
                if (!hashSet.add(buildPromotionEx)) {
                    continue;
                }
            }
            catch (NumberFormatException numberFormatException) {
                throw BuildPromotionImpl.a(numberFormatException);
            }
            for (BuildProblem buildProblem : buildPromotionEx.getBuildProblems()) {
                block20: {
                    try {
                        if (!ErrorData.isSnapshotDependencyError((String)buildProblem.getBuildProblemData().getType()) || buildProblem.isMutedInBuild()) break block20;
                    }
                    catch (NumberFormatException numberFormatException) {
                        throw BuildPromotionImpl.a(numberFormatException);
                    }
                    String string = buildProblem.getBuildProblemData().getAdditionalData();
                    try {
                        long l = Long.parseLong(string);
                        BuildPromotionEx buildPromotionEx2 = map.get(l);
                        try {
                            if (buildPromotionEx2 == null) continue;
                            linkedList.add((BuildPromotionImpl)buildPromotionEx2);
                            continue;
                        }
                        catch (NumberFormatException numberFormatException) {
                            throw BuildPromotionImpl.a(numberFormatException);
                        }
                    }
                    catch (NumberFormatException numberFormatException) {
                        Loggers.SERVER.warn("Build problem of type: " + buildProblem.getBuildProblemData().getType() + " has incorrect value stored in additional data: " + string);
                        continue;
                    }
                }
                arrayList2.add(buildProblem);
            }
        }
        try {
            arrayList = arrayList2;
            if (arrayList == null) {
                BuildPromotionImpl.$$$reportNull$$$0(262);
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw BuildPromotionImpl.a(numberFormatException);
        }
        return arrayList;
    }

    public boolean hasBuildProblemsOfType(@NotNull String string) {
        try {
            if (string == null) {
                BuildPromotionImpl.$$$reportNull$$$0(263);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return this.myProblems.hasBuildProblemsOfType(string);
    }

    public void deleteBuildProblem(@NotNull String string) {
        boolean bl;
        block10: {
            try {
                if (string == null) {
                    BuildPromotionImpl.$$$reportNull$$$0(264);
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
            bl = this.myProblems.deleteBuildProblem(string);
            try {
                try {
                    if (!bl || this.hasFailureReasons()) break block10;
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
                LOG.info("Changed build status of the build " + LogUtil.describe((BuildPromotion)this) + " due to build problem removal: " + string);
                this.debugToBuildLog("Changed build status to successful after build problem removal: " + string);
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
        }
        try {
            if (bl) {
                this.getContext().getNodesBuildsEvents().publishBuildProblemsUpdated((BuildPromotion)this);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
    }

    public void initBuildProblems() {
        this.myProblems.initIfNeeded();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     */
    private void updateIncompleteState(@Nullable IncompleteInfo incompleteInfo) {
        block17: {
            block18: {
                block20: {
                    if (incompleteInfo == null) break block17;
                    if (this.myChangesDetached) break block18;
                    break block20;
                    catch (IllegalStateException illegalStateException) {
                        throw BuildPromotionImpl.a(illegalStateException);
                    }
                }
                try {
                    block21: {
                        if (!incompleteInfo.is_changes_detached) break block18;
                        break block21;
                        catch (IllegalStateException illegalStateException) {
                            throw BuildPromotionImpl.a(illegalStateException);
                        }
                    }
                    this.myChangesDetached = true;
                    this.notifyChangesDetached();
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
            }
            BuildPromotionImpl buildPromotionImpl = this;
            synchronized (buildPromotionImpl) {
                block19: {
                    if (this.myIncomplete != null) {
                        try {
                            if (incompleteInfo.is_canceled) {
                                this.myIncomplete = true;
                            }
                        }
                        catch (IllegalStateException illegalStateException) {
                            throw BuildPromotionImpl.a(illegalStateException);
                        }
                    }
                    if (this.myDeleted) break block19;
                    try {
                        block22: {
                            if (!incompleteInfo.is_deleted) break block19;
                            break block22;
                            catch (IllegalStateException illegalStateException) {
                                throw BuildPromotionImpl.a(illegalStateException);
                            }
                        }
                        this.myDeleted = true;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildPromotionImpl.a(illegalStateException);
                    }
                }
            }
        }
    }

    @Nullable
    private IncompleteInfo loadIncompleteInfoFromDB() {
        return BuildPromotionImpl.getIncompleteInfos(this.getContext().getSQLRunner(), Collections.singletonList(this.myId)).get(this.myId);
    }

    public void resetDependentOnMeCache() {
        this.myDependencies.resetDependentOnMeCache();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void ensureBuildLogFlushed() {
        BuildLogEx buildLogEx;
        BuildPromotionImpl buildPromotionImpl = this;
        synchronized (buildPromotionImpl) {
            buildLogEx = this.myBuildLogRef.getLog();
            if (buildLogEx == null) {
                return;
            }
        }
        try {
            buildLogEx.flush();
        }
        catch (Exception exception) {
            Loggers.SERVER.warnAndDebugDetails("Exception while flushing build log of build promotion: " + LogUtil.describe((BuildPromotion)this), (Throwable)exception);
        }
    }

    public void resetDirectDependenciesCache() {
        this.myDependencies.resetDependenciesCache();
    }

    public void updateDependenciesCache(@NotNull Supplier<Map<Long, Integer>> supplier) {
        try {
            if (supplier == null) {
                BuildPromotionImpl.$$$reportNull$$$0(265);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        Set set = null;
        if (this.myDependencies.isDependenciesCached()) {
            set = LongStream.of(this.getDependenciesIds()).boxed().collect(Collectors.toSet());
        }
        Map<Long, Integer> map = supplier.get();
        this.myDependencies.setDependencies(map);
        if (set != null) {
            for (BuildPromotionEx buildPromotionEx : this.getBuildPromotionManager().findCached((Collection)Sets.symmetricDifference(set, map.keySet()))) {
                buildPromotionEx.resetDependentOnMeCache();
            }
        }
    }

    public void resetDependenciesHardRefs() {
        this.myDependencies.resetDependenciesHardRefs();
    }

    public void setTagDatas(@NotNull Collection<TagData> collection) {
        try {
            if (collection == null) {
                BuildPromotionImpl.$$$reportNull$$$0(266);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        this.checkHasPermissionForProject(Permission.TAG_BUILD);
        User user = this.getContext().getSecurityContext().getAuthorityHolder().getAssociatedUser();
        this.getContext().getTagsManager().persistBuildPromotionTags(user, (BuildPromotionEx)this, collection);
        this.getContext().getNodesBuildsEvents().publishTagsUpdated((BuildPromotion)this);
    }

    @NotNull
    public Collection<TagData> getTagDatas() {
        return this.getTagsData();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean hasLoadedTags() {
        Object object = this.myTagsLock;
        synchronized (object) {
            if (this.myTagDatasRef == EMPTY_TAGS) return true;
            try {
                if (this.myTagDatasRef.get() == null) return false;
                return true;
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @NotNull
    private Collection<TagData> getTagsData() {
        Collection collection;
        block8: {
            SoftReference<Collection> softReference;
            Object object = this.myTagsLock;
            // MONITORENTER : object
            if (this.myTagDatasRef == EMPTY_TAGS) {
                List<TagData> list = Collections.emptyList();
                // MONITOREXIT : object
                if (list != null) return list;
                BuildPromotionImpl.$$$reportNull$$$0(267);
                return list;
            }
            collection = this.myTagDatasRef.get();
            try {
                if (collection != null && !this.hasChangedUsers(collection)) break block8;
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
            collection = this.getContext().getTagsManager().getTagDatas((BuildPromotion)this);
            try {
                BuildPromotionImpl buildPromotionImpl = this;
                softReference = collection.isEmpty() ? EMPTY_TAGS : new SoftReference<Collection>(collection);
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
            buildPromotionImpl.myTagDatasRef = softReference;
        }
        Collection collection2 = collection;
        // MONITOREXIT : object
        if (collection2 != null) return collection2;
        BuildPromotionImpl.$$$reportNull$$$0(268);
        return collection2;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void initTagDatas(@NotNull Collection<TagData> collection) {
        try {
            if (collection == null) {
                BuildPromotionImpl.$$$reportNull$$$0(269);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        Object object = this.myTagsLock;
        synchronized (object) {
            this.rememberTagDatas(collection);
        }
    }

    private void rememberTagDatas(@NotNull Collection<TagData> collection) {
        SoftReference<Collection<TagData>> softReference;
        try {
            if (collection == null) {
                BuildPromotionImpl.$$$reportNull$$$0(270);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        try {
            BuildPromotionImpl buildPromotionImpl = this;
            softReference = collection.isEmpty() ? EMPTY_TAGS : new SoftReference<Collection<TagData>>(collection);
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        buildPromotionImpl.myTagDatasRef = softReference;
    }

    private boolean hasChangedUsers(@NotNull Collection<TagData> collection) {
        try {
            if (collection == null) {
                BuildPromotionImpl.$$$reportNull$$$0(271);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        for (TagData tagData : collection) {
            SUser sUser = tagData.getOwner();
            try {
                if (sUser == null) {
                    continue;
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
            SUser sUser2 = this.getContext().getUserModel().findUserById(sUser.getId());
            try {
                if (sUser.equals((Object)sUser2)) continue;
                return true;
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
        }
        return false;
    }

    @NotNull
    public List<String> getPrivateTags(@NotNull SUser sUser) {
        List list;
        try {
            if (sUser == null) {
                BuildPromotionImpl.$$$reportNull$$$0(272);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        try {
            list = TagDataUtil.getPrivateTags(this.getTagsData(), (SUser)sUser);
            if (list == null) {
                BuildPromotionImpl.$$$reportNull$$$0(273);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return list;
    }

    @NotNull
    public List<String> getTags() {
        List list;
        try {
            list = TagDataUtil.getPublicTags(this.getTagsData());
            if (list == null) {
                BuildPromotionImpl.$$$reportNull$$$0(274);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return list;
    }

    public void setPrivateTags(@Nullable List<String> list, @NotNull SUser sUser) {
        try {
            if (sUser == null) {
                BuildPromotionImpl.$$$reportNull$$$0(275);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        HashSet<TagData> hashSet = new HashSet<TagData>(TagDataUtil.getPublicTagDatas(this.getTagDatas()));
        List list2 = TagDataUtil.getPrivateTagDatas(this.getTagDatas());
        for (TagData object : list2) {
            try {
                if (sUser.equals((Object)object.getOwner())) continue;
                hashSet.add(object);
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
        }
        if (list != null) {
            for (String string : list) {
                hashSet.add(TagData.createPrivateTag((String)string, (SUser)sUser));
            }
        }
        this.setTagDatas(hashSet);
    }

    public void setTags(List<String> list) {
        List list2 = TagDataUtil.getPrivateTagDatas(this.getTagDatas());
        HashSet<TagData> hashSet = new HashSet<TagData>(list2);
        hashSet.addAll(TagData.createPublicTags(list));
        this.setTagDatas(hashSet);
    }

    @Nullable
    public Date getQueuedDate() {
        return this.myQueuedTime;
    }

    @Nullable
    public Date getServerStartDate() {
        return this.myRemoveFromQueueTime;
    }

    public void freezeSettings() throws IOException {
        BuildTypeEx buildTypeEx = this.getBuildTypeSafe();
        this.freezeSettings(buildTypeEx, "current");
    }

    public void freezeSettings(@NotNull BuildTypeSettingsEx buildTypeSettingsEx, @NotNull String string) throws IOException {
        try {
            if (buildTypeSettingsEx == null) {
                BuildPromotionImpl.$$$reportNull$$$0(276);
            }
        }
        catch (IOException iOException) {
            throw BuildPromotionImpl.a(iOException);
        }
        try {
            if (string == null) {
                BuildPromotionImpl.$$$reportNull$$$0(277);
            }
        }
        catch (IOException iOException) {
            throw BuildPromotionImpl.a(iOException);
        }
        this.freezeInlinedSettings(buildTypeSettingsEx, string);
    }

    public void postProcessingFinished() {
        this.setAttribute("teamcity.build.freezeSettings.forcePostprocessing", null);
        this.sendSettingsFinalizedEvent();
    }

    private void sendSettingsFinalizedEvent() {
        ((BuildServerListener)this.getContext().getEventDispatcher().getMulticaster()).buildPromotionSettingsFinalized((BuildPromotion)this);
        this.getContext().getNodesBuildsEvents().publishSettingsFinalized((BuildPromotion)this);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void freezeSettings(@NotNull File file, @NotNull String string) throws IOException {
        try {
            if (file == null) {
                BuildPromotionImpl.$$$reportNull$$$0(278);
            }
        }
        catch (IOException iOException) {
            throw BuildPromotionImpl.a(iOException);
        }
        try {
            if (string == null) {
                BuildPromotionImpl.$$$reportNull$$$0(279);
            }
        }
        catch (IOException iOException) {
            throw BuildPromotionImpl.a(iOException);
        }
        try {
            if (this.isDeleted()) {
                return;
            }
        }
        catch (IOException iOException) {
            throw BuildPromotionImpl.a(iOException);
        }
        this.persist();
        File file2 = this.getFrozenSettingsFile(this.getArtifactsDirectory());
        FileUtil.copy((File)file, (File)file2);
        File file3 = new File(file.getParentFile(), "vcsRoots");
        try {
            if (file3.isDirectory()) {
                FileUtils.copyDirectory((File)file3, (File)new File(file2.getParentFile(), "vcsRoots"));
            }
        }
        catch (IOException iOException) {
            throw BuildPromotionImpl.a(iOException);
        }
        BuildPromotionImpl buildPromotionImpl = this;
        synchronized (buildPromotionImpl) {
            this.markSettingsAsFrozen(string, null);
        }
        this.persist();
    }

    public void freezeSettings(@NotNull BuildTypeEx buildTypeEx, @NotNull String string) throws IOException {
        try {
            if (buildTypeEx == null) {
                BuildPromotionImpl.$$$reportNull$$$0(280);
            }
        }
        catch (IOException iOException) {
            throw BuildPromotionImpl.a(iOException);
        }
        try {
            if (string == null) {
                BuildPromotionImpl.$$$reportNull$$$0(281);
            }
        }
        catch (IOException iOException) {
            throw BuildPromotionImpl.a(iOException);
        }
        this.getContext().getSecurityContext().getAccessChecker().checkCanEditBuildType((SBuildType)this.getBuildTypeSafe());
        BuildTypeEx buildTypeEx2 = buildTypeEx.inline();
        try {
            this.freezeInlinedSettings(buildTypeEx2.getSettings(), string);
            if (buildTypeEx.isPipelineHead()) {
                PipelineViewImpl.markPipelineHead((BuildPromotionEx)this);
            }
        }
        catch (IOException iOException) {
            throw BuildPromotionImpl.a(iOException);
        }
    }

    public void setFreezeSettings(@Nullable Boolean bl) {
        this.setAttribute("teamcity.build.freezeSettings", bl);
    }

    @Nullable
    public Boolean getFreezeSettings() {
        Object object = this.getAttribute("teamcity.build.freezeSettings");
        try {
            if (object == null) {
                return null;
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return Boolean.valueOf(object.toString());
    }

    public boolean requiresFrozenSettingsPostProcessing() {
        Object object = this.getAttribute("teamcity.build.freezeSettings.forcePostprocessing");
        try {
            if (object == null) {
                return false;
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return Boolean.parseBoolean(object.toString());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     */
    private void freezeInlinedSettings(@NotNull BuildTypeSettingsEx buildTypeSettingsEx, @NotNull String string) throws IOException {
        try {
            if (buildTypeSettingsEx == null) {
                BuildPromotionImpl.$$$reportNull$$$0(282);
            }
        }
        catch (IOException iOException) {
            throw BuildPromotionImpl.a(iOException);
        }
        try {
            if (string == null) {
                BuildPromotionImpl.$$$reportNull$$$0(283);
            }
        }
        catch (IOException iOException) {
            throw BuildPromotionImpl.a(iOException);
        }
        try {
            if (this.isDeleted()) {
                return;
            }
        }
        catch (IOException iOException) {
            throw BuildPromotionImpl.a(iOException);
        }
        this.persist();
        try {
            block40: {
                BuildTypeEx buildTypeEx;
                block44: {
                    String string2;
                    boolean bl;
                    block39: {
                        block38: {
                            boolean bl2;
                            block37: {
                                block36: {
                                    File file = this.getFrozenSettingsFile();
                                    this.getContext().getConfigurationPersister().saveEntity((Object)buildTypeSettingsEx, file);
                                    this.saveVcsRoots(buildTypeSettingsEx.getVcsRoots(), file.getParentFile());
                                    this.savePipelineYamlForDebug(file.getParentFile(), buildTypeSettingsEx);
                                    buildTypeEx = this.getParentBuildType();
                                    if (buildTypeEx == null) break block36;
                                    try {
                                        block42: {
                                            if (!buildTypeEx.isCompositeBuildType()) break block36;
                                            break block42;
                                            catch (IOException iOException) {
                                                throw BuildPromotionImpl.a(iOException);
                                            }
                                        }
                                        bl2 = true;
                                        break block37;
                                    }
                                    catch (IOException iOException) {
                                        throw BuildPromotionImpl.a(iOException);
                                    }
                                }
                                bl2 = false;
                            }
                            boolean bl3 = bl2;
                            if (bl3) break block38;
                            try {
                                block43: {
                                    if (!this.isCompositeBuild()) break block38;
                                    break block43;
                                    catch (IOException iOException) {
                                        throw BuildPromotionImpl.a(iOException);
                                    }
                                }
                                bl = true;
                                break block39;
                            }
                            catch (IOException iOException) {
                                throw BuildPromotionImpl.a(iOException);
                            }
                        }
                        bl = false;
                    }
                    boolean bl4 = bl;
                    VcsRootInstance vcsRootInstance = this.getSettingsVcsRootInstance();
                    try {
                        string2 = vcsRootInstance != null ? this.getVersionedSettingsRevision(vcsRootInstance) : null;
                    }
                    catch (IOException iOException) {
                        throw BuildPromotionImpl.a(iOException);
                    }
                    String string3 = string2;
                    BuildPromotionImpl buildPromotionImpl = this;
                    synchronized (buildPromotionImpl) {
                        if (!bl4) {
                            Boolean bl5;
                            String string4;
                            BuildPromotionImpl buildPromotionImpl2;
                            try {
                                buildPromotionImpl2 = this;
                                string4 = "teamcity.build.composite";
                                bl5 = buildTypeSettingsEx.isCompositeBuildType() ? Boolean.valueOf(true) : null;
                            }
                            catch (IOException iOException) {
                                throw BuildPromotionImpl.a(iOException);
                            }
                            buildPromotionImpl2.setAttribute(string4, bl5);
                        }
                        this.markSettingsAsFrozen(string, string3);
                    }
                    if (buildTypeEx == null) break block40;
                    if (string3 == null) break block40;
                    break block44;
                    catch (IOException iOException) {
                        throw BuildPromotionImpl.a(iOException);
                    }
                }
                try {
                    block45: {
                        if (this.getAttribute("teamcity.build.frozenSettingsDigest") == null) break block40;
                        break block45;
                        catch (IOException iOException) {
                            throw BuildPromotionImpl.a(iOException);
                        }
                    }
                    this.scheduleDetachOfUnrelatedSettingsChanges(buildTypeEx);
                }
                catch (IOException iOException) {
                    throw BuildPromotionImpl.a(iOException);
                }
            }
            this.persist();
        }
        catch (IOException iOException) {
            try {
                if (this.isDeleted()) {
                    return;
                }
            }
            catch (IOException iOException2) {
                throw BuildPromotionImpl.a(iOException2);
            }
            throw iOException;
        }
        this.resetAllCompatibilityCaches();
    }

    private void savePipelineYamlForDebug(@NotNull File file, @NotNull BuildTypeSettingsEx buildTypeSettingsEx) {
        block12: {
            SBuildFeatureDescriptor sBuildFeatureDescriptor;
            try {
                if (file == null) {
                    BuildPromotionImpl.$$$reportNull$$$0(284);
                }
            }
            catch (IOException iOException) {
                throw BuildPromotionImpl.a(iOException);
            }
            try {
                if (buildTypeSettingsEx == null) {
                    BuildPromotionImpl.$$$reportNull$$$0(285);
                }
            }
            catch (IOException iOException) {
                throw BuildPromotionImpl.a(iOException);
            }
            Collection collection = buildTypeSettingsEx.getBuildFeaturesOfType("tc-pipeline");
            try {
                sBuildFeatureDescriptor = collection.isEmpty() ? null : (SBuildFeatureDescriptor)collection.iterator().next();
            }
            catch (IOException iOException) {
                throw BuildPromotionImpl.a(iOException);
            }
            SBuildFeatureDescriptor sBuildFeatureDescriptor2 = sBuildFeatureDescriptor;
            try {
                if (sBuildFeatureDescriptor2 == null || !sBuildFeatureDescriptor2.getParameters().containsKey("yaml")) break block12;
            }
            catch (IOException iOException) {
                throw BuildPromotionImpl.a(iOException);
            }
            try {
                String string = (String)sBuildFeatureDescriptor2.getParameters().get("yaml");
                FileUtil.writeFileAndReportErrors((File)new File(file, "pipeline.yml"), (String)string);
            }
            catch (IOException iOException) {
                LOG.infoAndDebugDetails("Cannot store pipeline.yaml in artifacts for " + LogUtil.describe((BuildPromotion)this), (Throwable)iOException);
            }
        }
    }

    private void markSettingsAsFrozen(@NotNull String string, @Nullable String string2) {
        BuildSettings buildSettings;
        block7: {
            try {
                if (string == null) {
                    BuildPromotionImpl.$$$reportNull$$$0(286);
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
            this.resetBuildSettings();
            this.myComputedVcsRootEntries.set(null);
            this.setAttribute("teamcity.build.hasFrozenSettings", string);
            buildSettings = this.getBuildSettings();
            ReadOnlyBuildTypeSettings readOnlyBuildTypeSettings = buildSettings.getParentSettings();
            try {
                try {
                    if (!string.equals("vcs") || string2 == null) break block7;
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
                this.setAttribute("teamcity.build.frozenSettingsDigest", this.computeFrozenSettingsDigest(readOnlyBuildTypeSettings));
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
        }
        this.updateFeatureTypesAttribute(buildSettings);
    }

    private void scheduleDetachOfUnrelatedSettingsChanges(@NotNull BuildTypeEx buildTypeEx) {
        ChangesCalculationOptionsEx changesCalculationOptionsEx;
        String string;
        block30: {
            String string2;
            try {
                if (buildTypeEx == null) {
                    BuildPromotionImpl.$$$reportNull$$$0(287);
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
            Object object = this.getAttribute("teamcity.build.frozenSettingsDigest");
            try {
                string2 = object == null ? null : object.toString();
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
            string = string2;
            try {
                if (string == null) {
                    return;
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
            boolean bl = TeamCityProperties.getBooleanOrTrue((String)"teamcity.versionedSettings.changes.detachUnrelated");
            try {
                if (!bl) {
                    return;
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
            changesCalculationOptionsEx = this.getContext().getChangesCalculationOptionsFactory().create();
            try {
                try {
                    if (!buildTypeEx.isDefaultBranchExcluded() || !TeamCityProperties.getBooleanOrTrue((String)"teamcity.versionedSettings.changes.detachUnrelated.defaultBranchDisabled.useLastBuildInAnyBranch")) break block30;
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
                changesCalculationOptionsEx.setPrevBuildPolicy(SelectPrevBuildPolicy.SINCE_LAST_BUILD_IN_ANY_BRANCH);
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
        }
        BuildChangesCalculator buildChangesCalculator = this.getContext().getChangesCalculatorFactory().createCalculator((BuildPromotionEx)this, this.getPrevPromotionRevisionsCache(), this.createVcsChangesCacheKey(changesCalculationOptionsEx), changesCalculationOptionsEx);
        VersionedSettingsChangesProvider versionedSettingsChangesProvider = buildChangesCalculator.getVersionedSettingsChangesProvider(changesCalculationOptionsEx.getCallback());
        try {
            if (versionedSettingsChangesProvider == null) {
                return;
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        List list = versionedSettingsChangesProvider.getChanges();
        try {
            if (list.isEmpty()) {
                return;
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        Set set = versionedSettingsChangesProvider.getVisistedPreviousBuildIds();
        try {
            if (set.isEmpty()) {
                return;
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        for (BuildPromotion buildPromotion : this.getContext().getBuildPromotionManager().findPromotionsByIds((Collection)set, new BuildPromotionManager.BuildPromotionInitFromDbHint[0])) {
            Object object = ((BuildPromotionEx)buildPromotion).getAttribute("teamcity.build.frozenSettingsDigest");
            try {
                if (object == null) {
                    return;
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
            try {
                if (string.equals(object.toString())) continue;
                return;
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
        }
        this.getContext().getVcsHistory().scheduleDetachOfChangesFromBuildType((SBuildType)buildTypeEx, (Collection)list.stream().map(sVcsModification -> sVcsModification.getId()).collect(Collectors.toSet()));
    }

    @NotNull
    private String computeFrozenSettingsDigest(@NotNull ReadOnlyBuildTypeSettings readOnlyBuildTypeSettings) {
        try {
            if (readOnlyBuildTypeSettings == null) {
                BuildPromotionImpl.$$$reportNull$$$0(288);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        MessageDigest messageDigest = new /* Unavailable Anonymous Inner Class!! */.createDigestFromSettings();
        ArrayList arrayList = new ArrayList(readOnlyBuildTypeSettings.getDependencies());
        arrayList.sort((dependency, dependency2) -> dependency.getDependOnExternalId().compareTo(dependency2.getDependOnExternalId()));
        for (Dependency dependency3 : arrayList) {
            messageDigest.update(dependency3.getDependOnExternalId().getBytes(StandardCharsets.UTF_8));
            ArrayList<Option> arrayList2 = new ArrayList<Option>(dependency3.getOptions());
            arrayList2.sort(Comparator.comparing(Option::getKey));
            for (Option option : arrayList2) {
                messageDigest.update(option.getKey().getBytes(StandardCharsets.UTF_8));
                messageDigest.update(dependency3.getOption(option).toString().getBytes(StandardCharsets.UTF_8));
            }
        }
        return new String(Hex.encodeHex((byte[])messageDigest.digest()));
    }

    private void updateFeatureTypesAttribute(BuildSettings buildSettings) {
        Set set = buildSettings.getBuildFeatures().stream().map(sBuildFeatureDescriptor -> sBuildFeatureDescriptor.getType()).collect(Collectors.toSet());
        String string = StringUtil.join(set, (String)",");
        try {
            if (string.length() < 1000) {
                this.setAttribute("teamcity.build.availableFeatureTypes", string);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
    }

    @NotNull
    private List<File> saveVcsRoots(@NotNull List<SVcsRoot> list, @NotNull File file) throws IOException {
        ArrayList<File> arrayList;
        ArrayList<File> arrayList2;
        block21: {
            BuildTypeEx buildTypeEx;
            block19: {
                List<File> list2;
                block20: {
                    try {
                        if (list == null) {
                            BuildPromotionImpl.$$$reportNull$$$0(289);
                        }
                    }
                    catch (IOException iOException) {
                        throw BuildPromotionImpl.a(iOException);
                    }
                    try {
                        if (file == null) {
                            BuildPromotionImpl.$$$reportNull$$$0(290);
                        }
                    }
                    catch (IOException iOException) {
                        throw BuildPromotionImpl.a(iOException);
                    }
                    buildTypeEx = this.getParentBuildType();
                    try {
                        try {
                            if (buildTypeEx != null) break block19;
                            list2 = Collections.emptyList();
                            if (list2 != null) break block20;
                        }
                        catch (IOException iOException) {
                            throw BuildPromotionImpl.a(iOException);
                        }
                        BuildPromotionImpl.$$$reportNull$$$0(291);
                    }
                    catch (IOException iOException) {
                        throw BuildPromotionImpl.a(iOException);
                    }
                }
                return list2;
            }
            arrayList2 = new ArrayList<File>();
            ProjectEx projectEx = buildTypeEx.getProject();
            try {
                if (list.isEmpty() || !BuildPromotionImpl.isVcsRootsFreezeEnabled()) break block21;
            }
            catch (IOException iOException) {
                throw BuildPromotionImpl.a(iOException);
            }
            File file2 = new File(file, "vcsRoots");
            file2.mkdirs();
            for (SVcsRoot sVcsRoot : list) {
                try {
                    if (!(sVcsRoot instanceof SVcsRootEx)) {
                        continue;
                    }
                }
                catch (IOException iOException) {
                    throw BuildPromotionImpl.a(iOException);
                }
                SVcsRootEx sVcsRootEx = (SVcsRootEx)sVcsRoot;
                File file3 = new File(file2, sVcsRoot.getExternalId() + ".xml");
                SVcsRootEx sVcsRootEx2 = this.createVcsRootCopyWithBuildProject(sVcsRootEx, projectEx);
                this.getContext().getConfigurationPersister().saveEntity((Object)sVcsRootEx2, file3);
                arrayList2.add(file3);
            }
        }
        try {
            arrayList = arrayList2;
            if (arrayList == null) {
                BuildPromotionImpl.$$$reportNull$$$0(292);
            }
        }
        catch (IOException iOException) {
            throw BuildPromotionImpl.a(iOException);
        }
        return arrayList;
    }

    @NotNull
    private SVcsRootEx createVcsRootCopyWithBuildProject(@NotNull SVcsRootEx sVcsRootEx, @NotNull ProjectEx projectEx) {
        SVcsRootImpl sVcsRootImpl;
        try {
            if (sVcsRootEx == null) {
                BuildPromotionImpl.$$$reportNull$$$0(293);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        try {
            if (projectEx == null) {
                BuildPromotionImpl.$$$reportNull$$$0(294);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        try {
            sVcsRootImpl = this.getContext().getVcsRootFactory().createVcsRoot(sVcsRootEx.getEntityId(), sVcsRootEx.getName(), sVcsRootEx.getVcsName(), sVcsRootEx.getProperties(), projectEx, sVcsRootEx.isEmptyUuid());
            if (sVcsRootImpl == null) {
                BuildPromotionImpl.$$$reportNull$$$0(295);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return sVcsRootImpl;
    }

    public static boolean isVcsRootsFreezeEnabled() {
        return TeamCityProperties.getBooleanOrTrue((String)"teamcity.buildPromotion.freezeVcsRoots.enabled");
    }

    public static int compareStartTime(@NotNull BuildPromotion buildPromotion, @NotNull BuildPromotion buildPromotion2) {
        SQueuedBuild sQueuedBuild;
        block18: {
            block17: {
                try {
                    if (buildPromotion == null) {
                        BuildPromotionImpl.$$$reportNull$$$0(296);
                    }
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
                try {
                    if (buildPromotion2 == null) {
                        BuildPromotionImpl.$$$reportNull$$$0(297);
                    }
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
                Date date = buildPromotion.getServerStartDate();
                Date date2 = buildPromotion2.getServerStartDate();
                try {
                    try {
                        if (date == null || date2 == null) break block17;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildPromotionImpl.a(illegalStateException);
                    }
                    return date.compareTo(date2);
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
            }
            sQueuedBuild = buildPromotion.getQueuedBuild();
            SQueuedBuild sQueuedBuild2 = buildPromotion2.getQueuedBuild();
            try {
                try {
                    if (sQueuedBuild == null || sQueuedBuild2 == null) break block18;
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
                return sQueuedBuild.getOrderNumber() - sQueuedBuild2.getOrderNumber();
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
        }
        try {
            if (sQueuedBuild != null) {
                return 1;
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return Long.compare(buildPromotion.getId(), buildPromotion2.getId());
    }

    /*
     * Exception decompiling
     */
    public void resetBuildSettings() {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [2[DOLOOP]], but top level block is 0[TRYBLOCK]
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

    public int getExecutionTimeoutMin() {
        int n;
        Integer n2 = (Integer)this.getBuildSettings().getOption(BuildTypeOptions.BT_EXECUTION_TIMEOUT);
        try {
            if (n2 > 0) {
                return n2;
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        try {
            n = this.isCompositeBuild() ? 0 : this.getContext().getServerSettings().getDefaultExecutionTimeout();
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return n;
    }

    public int getMaximumNumberOfBuildsPerBranch() {
        block17: {
            String string;
            block16: {
                block15: {
                    BuildTypeEx buildTypeEx = this.getBuildType();
                    try {
                        if (buildTypeEx == null) {
                            throw new BuildTypeNotFoundException("Build type " + this.getBuildTypeId() + " no longer exists");
                        }
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildPromotionImpl.a(illegalStateException);
                    }
                    string = buildTypeEx.getMaximumNumberOfBuildsPerBranch();
                    try {
                        try {
                            if (!StringUtil.isEmptyOrSpaces((String)string) && !"*:0".equals(string)) break block15;
                        }
                        catch (IllegalStateException illegalStateException) {
                            throw BuildPromotionImpl.a(illegalStateException);
                        }
                        return 0;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildPromotionImpl.a(illegalStateException);
                    }
                }
                string = this.getRealOrDummyBuild().getValueResolver().resolve(string).getResult();
                try {
                    try {
                        if (!StringUtil.isEmptyOrSpaces((String)string) && !"*:0".equals(string)) break block16;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildPromotionImpl.a(illegalStateException);
                    }
                    return 0;
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
            }
            try {
                try {
                    if (this.myMaxRunningBuildsPerBranchProvider != null && string.equals(this.myMaxRunningBuildsPerBranchProvider.getRulesStr())) break block17;
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
                this.myMaxRunningBuildsPerBranchProvider = new MaxRunningBuildsPerBranchProvider(this.getContext().getBranchSpecs(), string, LogUtil.describe((BuildPromotion)this));
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
        }
        return this.myMaxRunningBuildsPerBranchProvider.getMaximumNumberOfBuildsPerBranch(this.getBranch());
    }

    public boolean isMaximumNumberOfBuildsPerBranchLimited() {
        boolean bl;
        block13: {
            block12: {
                String string;
                block11: {
                    BuildTypeEx buildTypeEx = this.getBuildType();
                    try {
                        if (buildTypeEx == null) {
                            return false;
                        }
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildPromotionImpl.a(illegalStateException);
                    }
                    string = buildTypeEx.getMaximumNumberOfBuildsPerBranch();
                    try {
                        try {
                            if (!StringUtil.isEmptyOrSpaces((String)string) && !"*:0".equals(string)) break block11;
                        }
                        catch (IllegalStateException illegalStateException) {
                            throw BuildPromotionImpl.a(illegalStateException);
                        }
                        return false;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildPromotionImpl.a(illegalStateException);
                    }
                }
                string = this.getRealOrDummyBuild().getValueResolver().resolve(string).getResult();
                try {
                    try {
                        if (!StringUtil.isNotEmpty((String)string) || "*:0".equals(string)) break block12;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildPromotionImpl.a(illegalStateException);
                    }
                    bl = true;
                    break block13;
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
            }
            bl = false;
        }
        return bl;
    }

    @Nullable
    private ModIdAndBranchTuple loadModIdAndBranchInfoFromDB() {
        return BuildPromotionImpl.getModIdAndBranchInfo(this.getContext().getSQLRunner(), Collections.singletonList(this.myId)).get(this.myId);
    }

    public static void updatePromotionsDependencies(@NotNull Collection<BuildPromotionEx> collection, @NotNull SQLRunnerEx sQLRunnerEx) {
        try {
            if (collection == null) {
                BuildPromotionImpl.$$$reportNull$$$0(298);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        try {
            if (sQLRunnerEx == null) {
                BuildPromotionImpl.$$$reportNull$$$0(299);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        try {
            if (collection.isEmpty()) {
                return;
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        List<Long> list = collection.stream().map(buildPromotionEx -> buildPromotionEx.getId()).collect(Collectors.toList());
        Map<Long, Map<Long, Integer>> map = BuildPromotionImpl.getDependenciesInfo(list, sQLRunnerEx);
        for (BuildPromotionEx buildPromotionEx2 : collection) {
            buildPromotionEx2.updateDependenciesCache(() -> map.getOrDefault(buildPromotionEx2.getId(), new HashMap()));
        }
    }

    @NotNull
    private static Map<Long, Map<Long, Integer>> getDependenciesInfo(@NotNull List<Long> list, @NotNull SQLRunnerEx sQLRunnerEx) {
        HashMap<Long, Map<Long, Integer>> hashMap;
        try {
            if (list == null) {
                BuildPromotionImpl.$$$reportNull$$$0(300);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        try {
            if (sQLRunnerEx == null) {
                BuildPromotionImpl.$$$reportNull$$$0(301);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        int n = BuildPromotionImpl.getInParamsSize();
        String string = "select d.build_state_id, d.depends_on, d.dependency_options from build_dependency d join build_state b on b.id = d.depends_on where #IN{d.build_state_id} and b.is_deleted = 0";
        ArrayList arrayList = (ArrayList)sQLRunnerEx.runAndRetry(dBFunctions -> (ArrayList)dBFunctions.queryForTuples(new ArrayList(), DependencyTuple.class, null, string, new Object[]{InParams.ofLong((Collection)list).autoSplitBy(n)}));
        HashMap<Long, Map<Long, Integer>> hashMap2 = new HashMap<Long, Map<Long, Integer>>();
        for (DependencyTuple dependencyTuple : arrayList) {
            hashMap2.computeIfAbsent(dependencyTuple.myId, l -> new HashMap()).put(dependencyTuple.myDependsOnId, dependencyTuple.myOptions);
        }
        try {
            hashMap = hashMap2;
            if (hashMap == null) {
                BuildPromotionImpl.$$$reportNull$$$0(302);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return hashMap;
    }

    public static void updatePromotions(@NotNull Collection<BuildPromotionEx> collection, @NotNull SQLRunnerEx sQLRunnerEx, @NotNull BuildPromotionEx.UpdateReason updateReason) {
        Map map;
        Object object;
        Object object22;
        try {
            if (collection == null) {
                BuildPromotionImpl.$$$reportNull$$$0(303);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        try {
            if (sQLRunnerEx == null) {
                BuildPromotionImpl.$$$reportNull$$$0(304);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        try {
            if (updateReason == null) {
                BuildPromotionImpl.$$$reportNull$$$0(305);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        ArrayList<BuildPromotionImpl> arrayList = new ArrayList<BuildPromotionImpl>();
        ArrayList<Long> arrayList2 = new ArrayList<Long>();
        for (Object object22 : collection) {
            if (object22 instanceof BuildPromotionImpl) {
                object = (BuildPromotionImpl)((Object)object22);
                object.markAttributesReadFromDB();
                arrayList2.add(object22.getId());
                arrayList.add((BuildPromotionImpl)((Object)object));
                continue;
            }
            object22.updateFromDB(updateReason);
            object22.resetAllCompatibilityCaches();
        }
        try {
            if (arrayList2.isEmpty()) {
                return;
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        Map<Long, IncompleteInfo> map2 = BuildPromotionImpl.getIncompleteInfos(sQLRunnerEx, arrayList2);
        object22 = BuildPromotionImpl.getAttributesForPromos(sQLRunnerEx, arrayList2);
        object = BuildPromotionImpl.getArtifactDependenciesInfo(sQLRunnerEx, arrayList.stream().filter(BuildPromotionImpl::hasCachedArtifactDependenciesInfo).map(BuildPromotionImpl::getId).collect(Collectors.toList()));
        Map<Long, ModIdAndBranchTuple> map3 = BuildPromotionImpl.getModIdAndBranchInfo(sQLRunnerEx, arrayList.stream().filter(buildPromotionImpl -> {
            boolean bl;
            block8: {
                block7: {
                    try {
                        block6: {
                            try {
                                try {
                                    if (buildPromotionImpl.isChangeCollectingNeeded() || buildPromotionImpl.getAssociatedBuildId() == null) break block6;
                                }
                                catch (IllegalStateException illegalStateException) {
                                    throw BuildPromotionImpl.a(illegalStateException);
                                }
                                if (!buildPromotionImpl.getBooleanInternalParameter("teamcity.internal.build.resetRevisions.allow")) break block7;
                            }
                            catch (IllegalStateException illegalStateException) {
                                throw BuildPromotionImpl.a(illegalStateException);
                            }
                        }
                        bl = true;
                        break block8;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildPromotionImpl.a(illegalStateException);
                    }
                }
                bl = false;
            }
            return bl;
        }).map(BuildPromotionImpl::getId).collect(Collectors.toList()));
        Set set = map3.values().stream().filter(modIdAndBranchTuple -> {
            boolean bl;
            try {
                bl = modIdAndBranchTuple.myModId != null;
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
            return bl;
        }).map(modIdAndBranchTuple -> modIdAndBranchTuple.myId).collect(Collectors.toSet());
        try {
            map = set.isEmpty() ? Collections.emptyMap() : (Map)sQLRunnerEx.withDB(dBFunctions -> new BuildPromotionRevisionsQuery().loadRevisions(dBFunctions, (Collection)set, BuildPromotionImpl.getInParamsSize()));
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        Map map4 = map;
        for (BuildPromotionImpl buildPromotionImpl2 : arrayList) {
            buildPromotionImpl2.updateInternalState(updateReason, () -> BuildPromotionImpl.lambda$updatePromotions$78(buildPromotionImpl2, (Map)object22), () -> (IncompleteInfo)map2.get(buildPromotionImpl.myId), () -> BuildPromotionImpl.lambda$updatePromotions$80((Map)object, buildPromotionImpl2), () -> (ModIdAndBranchTuple)map3.get(buildPromotionImpl.myId), () -> (List)map4.get(buildPromotionImpl.myId));
            buildPromotionImpl2.resetAllCompatibilityCaches();
        }
    }

    @NotNull
    private static Map<Long, Map<String, Object>> getAttributesForPromos(@NotNull SQLRunnerEx sQLRunnerEx, @NotNull List<Long> list) {
        try {
            if (sQLRunnerEx == null) {
                BuildPromotionImpl.$$$reportNull$$$0(306);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        try {
            if (list == null) {
                BuildPromotionImpl.$$$reportNull$$$0(307);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        int n = BuildPromotionImpl.getInParamsSize();
        String string = "select build_state_id, attr_name, attr_value, attr_num_value from build_attrs where #IN{build_state_id}";
        ArrayList arrayList = (ArrayList)sQLRunnerEx.runAndRetry(dBFunctions -> (ArrayList)dBFunctions.queryForTuples(new ArrayList(), AttrTuple.class, null, string, new Object[]{InParams.ofLong((Collection)list).autoSplitBy(n)}));
        return BuildPromotionImpl.attrTuplesToMap(arrayList);
    }

    @NotNull
    private static Map<Long, Map<String, Object>> attrTuplesToMap(@NotNull List<AttrTuple> list) {
        HashMap<Long, Map<String, Object>> hashMap;
        try {
            if (list == null) {
                BuildPromotionImpl.$$$reportNull$$$0(308);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        HashMap<Long, Map<String, Object>> hashMap2 = new HashMap<Long, Map<String, Object>>();
        for (AttrTuple attrTuple : list) {
            Object object;
            String string;
            block14: {
                Long l2;
                block11: {
                    String string2;
                    block13: {
                        block12: {
                            string = StringPool.reuse((String)attrTuple.myName);
                            l2 = attrTuple.myNumValue;
                            try {
                                try {
                                    if (l2 != null) break block11;
                                    if (attrTuple.myValue == null) break block12;
                                }
                                catch (IllegalStateException illegalStateException) {
                                    throw BuildPromotionImpl.a(illegalStateException);
                                }
                                string2 = attrTuple.myValue.string;
                                break block13;
                            }
                            catch (IllegalStateException illegalStateException) {
                                throw BuildPromotionImpl.a(illegalStateException);
                            }
                        }
                        string2 = null;
                    }
                    object = StringUtil.emptyIfNull(string2);
                    break block14;
                }
                object = l2;
            }
            hashMap2.computeIfAbsent(attrTuple.myId, l -> new HashMap()).put(string, BuildPromotionImpl.pooledAttrValue(object));
        }
        try {
            hashMap = hashMap2;
            if (hashMap == null) {
                BuildPromotionImpl.$$$reportNull$$$0(309);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return hashMap;
    }

    @NotNull
    private static Map<Long, IncompleteInfo> getIncompleteInfos(@NotNull SQLRunnerEx sQLRunnerEx, @NotNull List<Long> list) {
        Map<Long, IncompleteInfo> map;
        try {
            if (sQLRunnerEx == null) {
                BuildPromotionImpl.$$$reportNull$$$0(310);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        try {
            if (list == null) {
                BuildPromotionImpl.$$$reportNull$$$0(311);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        int n = BuildPromotionImpl.getInParamsSize();
        String string = "select id, is_canceled, is_changes_detached, is_deleted from build_state where #IN{id}";
        ArrayList arrayList = (ArrayList)sQLRunnerEx.runAndRetry(dBFunctions -> (ArrayList)dBFunctions.queryForTuples(new ArrayList(), IncompleteInfo.class, null, string, new Object[]{InParams.ofLong((Collection)list).autoSplitBy(n)}));
        try {
            map = arrayList.stream().collect(Collectors.toMap(incompleteInfo -> incompleteInfo.id, incompleteInfo -> incompleteInfo, (incompleteInfo, incompleteInfo2) -> incompleteInfo));
            if (map == null) {
                BuildPromotionImpl.$$$reportNull$$$0(312);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return map;
    }

    @NotNull
    private static Map<Long, ModIdAndBranchTuple> getModIdAndBranchInfo(@NotNull SQLRunnerEx sQLRunnerEx, @NotNull List<Long> list) {
        Map<Long, ModIdAndBranchTuple> map;
        block13: {
            Map<Long, ModIdAndBranchTuple> map2;
            block14: {
                try {
                    if (sQLRunnerEx == null) {
                        BuildPromotionImpl.$$$reportNull$$$0(313);
                    }
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
                try {
                    if (list == null) {
                        BuildPromotionImpl.$$$reportNull$$$0(314);
                    }
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
                try {
                    try {
                        if (!list.isEmpty()) break block13;
                        map2 = Collections.emptyMap();
                        if (map2 != null) break block14;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildPromotionImpl.a(illegalStateException);
                    }
                    BuildPromotionImpl.$$$reportNull$$$0(315);
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
            }
            return map2;
        }
        int n = BuildPromotionImpl.getInParamsSize();
        String string = "select id, modification_id, chain_modification_id, branch_name from build_state where #IN{id}";
        ArrayList arrayList = (ArrayList)sQLRunnerEx.runAndRetry(dBFunctions -> (ArrayList)dBFunctions.queryForTuples(new ArrayList(), ModIdAndBranchTuple.class, null, string, new Object[]{InParams.ofLong((Collection)list).autoSplitBy(n)}));
        try {
            map = arrayList.stream().collect(Collectors.toMap(modIdAndBranchTuple -> modIdAndBranchTuple.myId, modIdAndBranchTuple -> modIdAndBranchTuple, (modIdAndBranchTuple, modIdAndBranchTuple2) -> modIdAndBranchTuple));
            if (map == null) {
                BuildPromotionImpl.$$$reportNull$$$0(316);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return map;
    }

    private static int getInParamsSize() {
        return Math.max(1, TeamCityProperties.getInteger((String)"teamcity.buildPromotions.update.maxParamsForInQuery", (int)20));
    }

    @NotNull
    public SArtifactDependency toArtifactDependency(@NotNull ArtifactDepTuple artifactDepTuple) {
        SArtifactDependency sArtifactDependency;
        RevisionRule revisionRule;
        String string;
        String string2;
        try {
            if (artifactDepTuple == null) {
                BuildPromotionImpl.$$$reportNull$$$0(317);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        try {
            string2 = artifactDepTuple.myRevisionRule;
            string = artifactDepTuple.myBranch == null ? null : artifactDepTuple.myBranch.string;
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        RevisionRule revisionRule2 = RevisionRules.newBranchRevisionRule((String)string2, string);
        try {
            revisionRule = revisionRule2 != null ? revisionRule2 : RevisionRules.newBuildNumberRule((String)artifactDepTuple.myRevisionRule);
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        RevisionRule revisionRule3 = revisionRule;
        ArtifactDependencyFactory artifactDependencyFactory = this.getContext().getArtifactDependencyFactory();
        SArtifactDependency sArtifactDependency2 = artifactDependencyFactory.createArtifactDependencyByInternalId(artifactDepTuple.myId, artifactDepTuple.mySourceBuildTypeId, null, artifactDepTuple.mySrcPaths, revisionRule3);
        try {
            sArtifactDependency2.setCleanDestinationFolder(this.findCurrentCleanDestinationSetting(artifactDepTuple.mySourceBuildTypeId, artifactDepTuple.mySrcPaths));
            sArtifactDependency = sArtifactDependency2;
            if (sArtifactDependency == null) {
                BuildPromotionImpl.$$$reportNull$$$0(318);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return sArtifactDependency;
    }

    @NotNull
    public List<BuildPromotion> getBuildPromotionsWithArtifactDependenciesOnMe(@NotNull List<BuildPromotion> list) {
        ArrayList<BuildPromotion> arrayList;
        Object object2;
        Long l;
        block21: {
            List<BuildPromotion> list2;
            block22: {
                try {
                    if (list == null) {
                        BuildPromotionImpl.$$$reportNull$$$0(319);
                    }
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
                l = this.getAssociatedBuildId();
                try {
                    try {
                        if (l != null) break block21;
                        list2 = Collections.emptyList();
                        if (list2 != null) break block22;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildPromotionImpl.a(illegalStateException);
                    }
                    BuildPromotionImpl.$$$reportNull$$$0(320);
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
            }
            return list2;
        }
        ArrayList<BuildPromotion> arrayList2 = new ArrayList<BuildPromotion>();
        ArrayList<Long> arrayList3 = new ArrayList<Long>();
        for (Object object2 : list) {
            block23: {
                try {
                    try {
                        try {
                            if (!(object2 instanceof BuildPromotionImpl) || !((BuildPromotionImpl)((Object)object2)).hasCachedArtifactDependenciesInfo()) break block23;
                        }
                        catch (IllegalStateException illegalStateException) {
                            throw BuildPromotionImpl.a(illegalStateException);
                        }
                        if (!((BuildPromotionImpl)((Object)object2)).hasArtifactDependencyOn((BuildPromotion)this)) continue;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildPromotionImpl.a(illegalStateException);
                    }
                    arrayList2.add((BuildPromotion)object2);
                    continue;
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
            }
            arrayList3.add(object2.getId());
        }
        Map<Long, List<ArtifactDepTuple>> map = BuildPromotionImpl.getArtifactDependenciesInfo(this.getContext().getSQLRunner(), arrayList3);
        object2 = artifactDepTuple -> {
            block7: {
                String string;
                String string2;
                try {
                    if (!this.getParentBuildTypeId().equals(artifactDepTuple.mySourceBuildTypeId)) {
                        return false;
                    }
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
                try {
                    string2 = artifactDepTuple.myRevisionRule;
                    string = artifactDepTuple.myBranch == null ? null : artifactDepTuple.myBranch.string;
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
                RevisionRule revisionRule = RevisionRules.newBranchRevisionRule((String)string2, string);
                try {
                    if (revisionRule == null || !revisionRule.getName().equals("buildId")) break block7;
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
                Long l2 = RevisionRules.extractBuildIdFromRule((RevisionRule)revisionRule);
                return l2.equals(l);
            }
            return false;
        };
        for (BuildPromotion buildPromotion : list) {
            List list3 = map.getOrDefault(buildPromotion.getId(), Collections.emptyList());
            try {
                if (list3.stream().noneMatch(object2)) {
                    continue;
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildPromotionImpl.a(illegalStateException);
            }
            arrayList2.add(buildPromotion);
        }
        try {
            arrayList = arrayList2;
            if (arrayList == null) {
                BuildPromotionImpl.$$$reportNull$$$0(321);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return arrayList;
    }

    @NotNull
    public PipelineView getPipelineView() {
        PipelineView pipelineView;
        try {
            pipelineView = this.myPipelineView;
            if (pipelineView == null) {
                BuildPromotionImpl.$$$reportNull$$$0(322);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return pipelineView;
    }

    @NotNull
    private static Map<Long, List<ArtifactDepTuple>> getArtifactDependenciesInfo(@NotNull SQLRunnerEx sQLRunnerEx, @NotNull List<Long> list) {
        HashMap<Long, List<ArtifactDepTuple>> hashMap;
        block14: {
            Map<Long, List<ArtifactDepTuple>> map;
            block15: {
                try {
                    if (sQLRunnerEx == null) {
                        BuildPromotionImpl.$$$reportNull$$$0(323);
                    }
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
                try {
                    if (list == null) {
                        BuildPromotionImpl.$$$reportNull$$$0(324);
                    }
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
                try {
                    try {
                        if (!list.isEmpty()) break block14;
                        map = Collections.emptyMap();
                        if (map != null) break block15;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildPromotionImpl.a(illegalStateException);
                    }
                    BuildPromotionImpl.$$$reportNull$$$0(325);
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildPromotionImpl.a(illegalStateException);
                }
            }
            return map;
        }
        int n = BuildPromotionImpl.getInParamsSize();
        ArrayList arrayList2 = (ArrayList)sQLRunnerEx.runAndRetry(dBFunctions -> {
            String string = "select artif_dep_id, build_state_id, source_build_type_id, revision_rule, source_build_state_id, branch_name, src_paths, 1 from final_artifact_dependency where #IN{build_state_id} union all select artif_dep_id, build_state_id, source_build_type_id, revision_rule, source_build_state_id, branch_name, src_paths, 0 from build_artifact_dependency where #IN{build_state_id} order by artif_dep_id";
            InParams inParams = InParams.ofLong((Collection)list).autoSplitBy(n);
            return (ArrayList)dBFunctions.queryForTuples(new ArrayList(), ArtifactDepTuple.class, null, string, new Object[]{inParams, inParams});
        });
        HashMap<Long, List<ArtifactDepTuple>> hashMap2 = new HashMap<Long, List<ArtifactDepTuple>>();
        for (ArtifactDepTuple artifactDepTuple : arrayList2) {
            hashMap2.compute(artifactDepTuple.myBuildStateId, (l, arrayList) -> {
                if (arrayList == null) {
                    arrayList = new ArrayList<ArtifactDepTuple>();
                }
                arrayList.add(artifactDepTuple);
                return arrayList;
            });
        }
        try {
            hashMap = hashMap2;
            if (hashMap == null) {
                BuildPromotionImpl.$$$reportNull$$$0(326);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return hashMap;
    }

    public String generateAgentAccessCode() {
        String string;
        Object object = this.getAttribute("teamcity.build.agentAccessCode");
        if (object == null) {
            string = StringUtil.generateUniqueHash();
            this.setAttribute("teamcity.build.agentAccessCode", string);
            this.persist();
        } else {
            string = object.toString();
        }
        return string;
    }

    private static /* synthetic */ List lambda$updatePromotions$80(Map map, BuildPromotionImpl buildPromotionImpl) {
        return (List)map.get(buildPromotionImpl.myId);
    }

    private static /* synthetic */ Map lambda$updatePromotions$78(BuildPromotionImpl buildPromotionImpl, Map map) {
        try {
            if (buildPromotionImpl.myAttributesRead) {
                buildPromotionImpl.myAttributesRead = false;
                return map.getOrDefault(buildPromotionImpl.myId, Collections.emptyMap());
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        return buildPromotionImpl.loadAttributesFromDb();
    }

    private /* synthetic */ List lambda$getProjectPathIds$21(DBFunctions dBFunctions) throws DBException {
        return dBFunctions.queryForStrings("select project_int_id from build_project where build_id = ? order by project_level", new Object[]{this.myId});
    }

    private static /* synthetic */ void lambda$checkDesiredBranch$5(StringBuilder stringBuilder, VcsRootInstance vcsRootInstance, BranchSpec branchSpec) {
        SVcsRoot sVcsRoot = vcsRootInstance.getParent();
        stringBuilder.append(sVcsRoot.getExternalId()).append("=");
        String string = branchSpec.asString();
        if (StringUtil.isEmpty((String)string)) {
            string = "<empty>";
        }
        stringBuilder.append(string).append("\n");
    }

    static {
        boolean bl;
        try {
            bl = !BuildPromotionImpl.class.desiredAssertionStatus();
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildPromotionImpl.a(illegalStateException);
        }
        $assertionsDisabled = bl;
        LOG = Logger.getInstance((String)BuildPromotionImpl.class.getName());
        ACTION_TYPES = new ActionType[]{ActionType.BUILD_COMMENT, ActionType.BUILD_COMMENT_REMOVE, ActionType.BUILD_MARKED_AS_FAILED, ActionType.BUILD_MARKED_AS_SUCCESSFUL, ActionType.BUILD_PROMOTION_TAG_CHANGE, ActionType.BUILD_ADD_TO_QUEUE, ActionType.BUILD_REMOVE_FROM_QUEUE};
        EMPTY_TAGS = new SoftReference<Object>(null);
        NON_COPYABLE_ATTRIBUTES = new HashSet<String>();
        NON_COPYABLE_ATTRIBUTES.add("teamcity.build.creatorNodeId");
        NON_COPYABLE_ATTRIBUTES.add("teamcity.build.agentAccessCode");
        NON_COPYABLE_ATTRIBUTES.add("teamcity.collectChanges.error");
        NON_COPYABLE_ATTRIBUTES.add("teamcity.build.buildTypeArtifactsPath");
        NON_COPYABLE_ATTRIBUTES.add("teamcity.build.ownerNodeUrl");
        NON_COPYABLE_ATTRIBUTES.add("teamcity.build.detachedFromAgentAtStep");
        NON_COPYABLE_ATTRIBUTES.add("teamcity.build.detachedFromAgentReceivedTimestamp");
        NON_COPYABLE_ATTRIBUTES.add("teamcity.build.detachedFromAgentTimestamp");
        NON_COPYABLE_ATTRIBUTES.add("teamcity.build.detachedFromAgentRunnerType");
        NON_COPYABLE_ATTRIBUTES.add("teamcity.build.detachedFromAgentTrackingInfo");
        LAST_COMPLETE_SEARCH_DEPTH = TeamCityProperties.getInteger((String)"teamcity.lastCompleteBuild.searchDepth", (int)50);
        SET_CHANGES_DETACHED_QUERY = new GenericQuery("update build_state set is_changes_detached = ? where id = ?");
        SET_BUILD_ID_QUERY = new GenericQuery("update build_state set build_id = ? where id = ?");
        UPDATE_DATES_QUERY = new GenericQuery("update build_state set queued_time = ?, remove_from_queue_time = ? where id = ?");
        SAVE_ROOT_REVISION_QUERY = new GenericQuery("insert into build_revisions (build_state_id, vcs_root_id, vcs_revision,vcs_revision_display_name, vcs_branch_name, modification_id, vcs_root_type,checkout_mode ) values (?,?,?,?,?,?,?,?)");
        GET_OVERRIDEN_ROOTS = new GenericQuery("select original_vcs_root_id, substitution_vcs_root_id from build_overriden_roots where build_state_id = ?", resultSet -> {
            HashMap<Long, Long> hashMap = new HashMap<Long, Long>();
            while (resultSet.next()) {
                Long l = resultSet.getLong(1);
                Long l2 = resultSet.getLong(2);
                hashMap.put(l, l2);
            }
            return hashMap;
        });
        DELETE_ATTRS_QUERY = new GenericQuery("delete from build_attrs where build_state_id = ? and attr_name = ?");
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
