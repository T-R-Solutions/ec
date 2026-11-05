/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.intellij.openapi.diagnostic.Logger
 *  gnu.trove.decorator.TLongSetDecorator
 *  gnu.trove.set.TLongSet
 *  gnu.trove.set.hash.TLongHashSet
 *  jetbrains.buildServer.AgentRestrictorType
 *  jetbrains.buildServer.Build
 *  jetbrains.buildServer.BuildAgent
 *  jetbrains.buildServer.BuildType
 *  jetbrains.buildServer.BuildTypeDescriptor
 *  jetbrains.buildServer.BuildTypeDescriptor$CheckoutType
 *  jetbrains.buildServer.BuildTypeStatusDescriptor
 *  jetbrains.buildServer.Used
 *  jetbrains.buildServer.agentServer.AgentCheckoutDirRevisionInfo
 *  jetbrains.buildServer.buildTriggers.BuildTriggerDescriptor
 *  jetbrains.buildServer.log.Loggable
 *  jetbrains.buildServer.log.Loggers
 *  jetbrains.buildServer.messages.Status
 *  jetbrains.buildServer.parameters.ParametersProvider
 *  jetbrains.buildServer.parameters.ReferencesResolverUtil
 *  jetbrains.buildServer.parameters.ValueResolver
 *  jetbrains.buildServer.pipeline.PipelineProject
 *  jetbrains.buildServer.pipeline.impl.PipelineDescription
 *  jetbrains.buildServer.requirements.Requirement
 *  jetbrains.buildServer.responsibility.BuildTypeResponsibilityEntry
 *  jetbrains.buildServer.responsibility.ResponsibilityEntry
 *  jetbrains.buildServer.responsibility.ResponsibilityEntry$RemoveMethod
 *  jetbrains.buildServer.responsibility.ResponsibilityEntry$State
 *  jetbrains.buildServer.responsibility.ResponsibilityEntryFactory
 *  jetbrains.buildServer.serverSide.AgentCompatibility
 *  jetbrains.buildServer.serverSide.AgentDescription
 *  jetbrains.buildServer.serverSide.Branch
 *  jetbrains.buildServer.serverSide.BranchCalculationOptions
 *  jetbrains.buildServer.serverSide.BranchEx
 *  jetbrains.buildServer.serverSide.BranchFilter
 *  jetbrains.buildServer.serverSide.BranchSpecProvider
 *  jetbrains.buildServer.serverSide.BranchesPolicy
 *  jetbrains.buildServer.serverSide.BuildAgentEx
 *  jetbrains.buildServer.serverSide.BuildCustomizer
 *  jetbrains.buildServer.serverSide.BuildNumbers
 *  jetbrains.buildServer.serverSide.BuildPromotion
 *  jetbrains.buildServer.serverSide.BuildPromotionEx
 *  jetbrains.buildServer.serverSide.BuildPromotionInitializer
 *  jetbrains.buildServer.serverSide.BuildRunnerDescriptor
 *  jetbrains.buildServer.serverSide.BuildServerEx
 *  jetbrains.buildServer.serverSide.BuildServerListener
 *  jetbrains.buildServer.serverSide.BuildSettings
 *  jetbrains.buildServer.serverSide.BuildTypeCopySettings
 *  jetbrains.buildServer.serverSide.BuildTypeEx
 *  jetbrains.buildServer.serverSide.BuildTypeOptions
 *  jetbrains.buildServer.serverSide.BuildTypeSettings
 *  jetbrains.buildServer.serverSide.BuildTypeSettingsCopyOptions
 *  jetbrains.buildServer.serverSide.BuildTypeSettingsEx
 *  jetbrains.buildServer.serverSide.BuildTypeSettingsListener
 *  jetbrains.buildServer.serverSide.BuildTypeTemplate
 *  jetbrains.buildServer.serverSide.BuildTypeTemplateEx
 *  jetbrains.buildServer.serverSide.CannotAttachToTemplateException
 *  jetbrains.buildServer.serverSide.ChangeDescriptor
 *  jetbrains.buildServer.serverSide.Compatibility
 *  jetbrains.buildServer.serverSide.CompatibilityResult
 *  jetbrains.buildServer.serverSide.ConfigAction
 *  jetbrains.buildServer.serverSide.ConfigActionEx
 *  jetbrains.buildServer.serverSide.ConfigActionsServerListener
 *  jetbrains.buildServer.serverSide.CustomDataStorage
 *  jetbrains.buildServer.serverSide.CustomDataStorage$ConflictResolution
 *  jetbrains.buildServer.serverSide.DependencyConsumer
 *  jetbrains.buildServer.serverSide.DependencyConsumer$Result
 *  jetbrains.buildServer.serverSide.DuplicateIdException
 *  jetbrains.buildServer.serverSide.InvalidIdentifierException
 *  jetbrains.buildServer.serverSide.InvalidVcsRootScopeException
 *  jetbrains.buildServer.serverSide.MatchedSettingsVisitor
 *  jetbrains.buildServer.serverSide.ObsoleteEntityException
 *  jetbrains.buildServer.serverSide.Parameter
 *  jetbrains.buildServer.serverSide.ParametersDescriptor
 *  jetbrains.buildServer.serverSide.ParametersSupport
 *  jetbrains.buildServer.serverSide.PersistFailedException
 *  jetbrains.buildServer.serverSide.ProjectManager
 *  jetbrains.buildServer.serverSide.QueuedBuildEx
 *  jetbrains.buildServer.serverSide.ReadOnlyEntityException
 *  jetbrains.buildServer.serverSide.ReadOnlyReason
 *  jetbrains.buildServer.serverSide.ResolvedSettings
 *  jetbrains.buildServer.serverSide.RunningBuildEx
 *  jetbrains.buildServer.serverSide.SAgentRestrictor
 *  jetbrains.buildServer.serverSide.SBuild
 *  jetbrains.buildServer.serverSide.SBuildAgent
 *  jetbrains.buildServer.serverSide.SBuildFeatureDescriptor
 *  jetbrains.buildServer.serverSide.SBuildRunnerDescriptor
 *  jetbrains.buildServer.serverSide.SBuildType
 *  jetbrains.buildServer.serverSide.SFinishedBuild
 *  jetbrains.buildServer.serverSide.SPersistentEntity
 *  jetbrains.buildServer.serverSide.SProject
 *  jetbrains.buildServer.serverSide.SQueuedBuild
 *  jetbrains.buildServer.serverSide.SRunningBuild
 *  jetbrains.buildServer.serverSide.SecurityContextEx
 *  jetbrains.buildServer.serverSide.SimpleParameter
 *  jetbrains.buildServer.serverSide.TagData
 *  jetbrains.buildServer.serverSide.TagDataUtil
 *  jetbrains.buildServer.serverSide.TeamCityProperties
 *  jetbrains.buildServer.serverSide.TemplateBecomesInaccessibleException
 *  jetbrains.buildServer.serverSide.VcsModificationProcessor
 *  jetbrains.buildServer.serverSide.VcsStatusLogger
 *  jetbrains.buildServer.serverSide.VcsWorkspaceAccess
 *  jetbrains.buildServer.serverSide.agentPools.AgentPoolsActiveFilter
 *  jetbrains.buildServer.serverSide.agentPools.AgentPoolsActiveFilterImpl
 *  jetbrains.buildServer.serverSide.agentTypes.SAgentType
 *  jetbrains.buildServer.serverSide.agentTypes.SAgentTypeComparator
 *  jetbrains.buildServer.serverSide.artifacts.SArtifactDependency
 *  jetbrains.buildServer.serverSide.audit.ActionType
 *  jetbrains.buildServer.serverSide.audit.AuditLog
 *  jetbrains.buildServer.serverSide.audit.AuditLogAction
 *  jetbrains.buildServer.serverSide.buildDistribution.BuildPromotionInfo
 *  jetbrains.buildServer.serverSide.comments.Comment
 *  jetbrains.buildServer.serverSide.config.ConfigModification
 *  jetbrains.buildServer.serverSide.config.ConfigModificationFactory
 *  jetbrains.buildServer.serverSide.db.DBException
 *  jetbrains.buildServer.serverSide.db.DBFunctionsProvider
 *  jetbrains.buildServer.serverSide.dependency.BuildTypeDependencyGraph
 *  jetbrains.buildServer.serverSide.dependency.BuildTypeDependencyGraphImpl
 *  jetbrains.buildServer.serverSide.dependency.DependenciesSupplier
 *  jetbrains.buildServer.serverSide.dependency.Dependency
 *  jetbrains.buildServer.serverSide.dependency.DependencyOptions
 *  jetbrains.buildServer.serverSide.dependency.DependencySettings
 *  jetbrains.buildServer.serverSide.dependency.Dependent
 *  jetbrains.buildServer.serverSide.dependency.OrganizationProjectAccessViolationException
 *  jetbrains.buildServer.serverSide.identifiers.BuildTypeIdentifiersManager
 *  jetbrains.buildServer.serverSide.identifiers.CustomIdChangeApplier
 *  jetbrains.buildServer.serverSide.identifiers.EntityId
 *  jetbrains.buildServer.serverSide.impl.BaseBuildTypeIdentity
 *  jetbrains.buildServer.serverSide.impl.BaseDummyBuildPromotion
 *  jetbrains.buildServer.serverSide.impl.BranchImpl
 *  jetbrains.buildServer.serverSide.impl.BuildFactory
 *  jetbrains.buildServer.serverSide.impl.BuildTypeBranches
 *  jetbrains.buildServer.serverSide.impl.BuildTypeImpl$4
 *  jetbrains.buildServer.serverSide.impl.BuildTypeOrderedBuildsImpl
 *  jetbrains.buildServer.serverSide.impl.BuildTypeSettingsImpl
 *  jetbrains.buildServer.serverSide.impl.BuildTypeStatusDescriptorImpl
 *  jetbrains.buildServer.serverSide.impl.CompositeBranchSpec
 *  jetbrains.buildServer.serverSide.impl.DependencyCheckResult
 *  jetbrains.buildServer.serverSide.impl.DependencyCollector
 *  jetbrains.buildServer.serverSide.impl.DependencyType
 *  jetbrains.buildServer.serverSide.impl.DummyBuild
 *  jetbrains.buildServer.serverSide.impl.DummyBuildPromotion
 *  jetbrains.buildServer.serverSide.impl.EditableBuildTypeCopy
 *  jetbrains.buildServer.serverSide.impl.IllegalPersonalBuildException
 *  jetbrains.buildServer.serverSide.impl.InMemoryBuildPromotion
 *  jetbrains.buildServer.serverSide.impl.IncorrectArchivationCommentsRemapper
 *  jetbrains.buildServer.serverSide.impl.IsEnabledFilter
 *  jetbrains.buildServer.serverSide.impl.LazyValueResolver
 *  jetbrains.buildServer.serverSide.impl.LogUtil
 *  jetbrains.buildServer.serverSide.impl.NullComment
 *  jetbrains.buildServer.serverSide.impl.OrganizationProjects
 *  jetbrains.buildServer.serverSide.impl.PersonalBuildPromotionFilter
 *  jetbrains.buildServer.serverSide.impl.ProjectEx
 *  jetbrains.buildServer.serverSide.impl.ReadOnlyUtil
 *  jetbrains.buildServer.serverSide.impl.RemoteBuildType
 *  jetbrains.buildServer.serverSide.impl.RemoteBuildTypeEx
 *  jetbrains.buildServer.serverSide.impl.ReverseDepParametersProcessor
 *  jetbrains.buildServer.serverSide.impl.SecureDataStorage
 *  jetbrains.buildServer.serverSide.impl.TriggerWithNameFilter
 *  jetbrains.buildServer.serverSide.impl.ValidationProvider$ValidationError
 *  jetbrains.buildServer.serverSide.impl.audit.ActionTypeSet
 *  jetbrains.buildServer.serverSide.impl.beans.BuildTypeContext
 *  jetbrains.buildServer.serverSide.impl.beans.BuildTypeIdentityContext
 *  jetbrains.buildServer.serverSide.impl.cleanup.HistoryRetentionPolicy
 *  jetbrains.buildServer.serverSide.impl.cleanup.v2019.settings.ClashingCleanupRules
 *  jetbrains.buildServer.serverSide.impl.cleanup.v2019.settings.CleanupProperties
 *  jetbrains.buildServer.serverSide.impl.dependency.BuildPromotionGraphFactory
 *  jetbrains.buildServer.serverSide.impl.dependency.UnresolvedDependency
 *  jetbrains.buildServer.serverSide.impl.persisting.projects.BackgroundPersistTask
 *  jetbrains.buildServer.serverSide.impl.projectSources.PatchCache
 *  jetbrains.buildServer.serverSide.impl.projectSources.PatchComposer
 *  jetbrains.buildServer.serverSide.impl.projects.ReloadProjectsConfig
 *  jetbrains.buildServer.serverSide.impl.timeEstimation.BuildDurationEstimateContext
 *  jetbrains.buildServer.serverSide.impl.virtualAgent.StartableAgentType
 *  jetbrains.buildServer.serverSide.parameters.ParameterFactory
 *  jetbrains.buildServer.serverSide.parameters.types.PasswordType
 *  jetbrains.buildServer.serverSide.util.FileHistory
 *  jetbrains.buildServer.serverSide.vcs.FullCheckoutFlag
 *  jetbrains.buildServer.serverSide.vcs.VcsLabelingSettings$LabelingType
 *  jetbrains.buildServer.serverSide.versionedSettings.VersionedSettingsManager
 *  jetbrains.buildServer.step.conditions.BuildStepConditions
 *  jetbrains.buildServer.users.SUser
 *  jetbrains.buildServer.users.User
 *  jetbrains.buildServer.util.CollectionsUtil
 *  jetbrains.buildServer.util.ExceptionUtil
 *  jetbrains.buildServer.util.FileUtil
 *  jetbrains.buildServer.util.Hash
 *  jetbrains.buildServer.util.ItemProcessor
 *  jetbrains.buildServer.util.NameGenerator
 *  jetbrains.buildServer.util.NamedThreadFactory
 *  jetbrains.buildServer.util.Option
 *  jetbrains.buildServer.util.StringUtil
 *  jetbrains.buildServer.util.browser.Browser
 *  jetbrains.buildServer.util.filters.Filter
 *  jetbrains.buildServer.util.filters.FilterUtil
 *  jetbrains.buildServer.vcs.BranchSpec
 *  jetbrains.buildServer.vcs.CheckoutRules
 *  jetbrains.buildServer.vcs.InvalidBranchSpecException
 *  jetbrains.buildServer.vcs.LVcsRootInstance
 *  jetbrains.buildServer.vcs.OperationRequestor
 *  jetbrains.buildServer.vcs.PathMapping
 *  jetbrains.buildServer.vcs.RepositoryStateImpl
 *  jetbrains.buildServer.vcs.SVcsModification
 *  jetbrains.buildServer.vcs.SVcsRoot
 *  jetbrains.buildServer.vcs.SVcsRootEx
 *  jetbrains.buildServer.vcs.SelectPrevBuildPolicy
 *  jetbrains.buildServer.vcs.VcsException
 *  jetbrains.buildServer.vcs.VcsFile
 *  jetbrains.buildServer.vcs.VcsModificationHistory
 *  jetbrains.buildServer.vcs.VcsRoot
 *  jetbrains.buildServer.vcs.VcsRootEntry
 *  jetbrains.buildServer.vcs.VcsRootInstance
 *  jetbrains.buildServer.vcs.VcsRootInstanceEntry
 *  jetbrains.buildServer.vcs.VcsRootInstanceEx
 *  jetbrains.buildServer.vcs.impl.SVcsRootImpl
 *  jetbrains.buildServer.vcs.patches.LowLevelPatchBuilder
 *  jetbrains.vcs.api.VcsServiceFactory
 *  jetbrains.vcs.api.data.VcsRepositoryState
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package jetbrains.buildServer.serverSide.impl;

import com.intellij.openapi.diagnostic.Logger;
import gnu.trove.decorator.TLongSetDecorator;
import gnu.trove.set.TLongSet;
import gnu.trove.set.hash.TLongHashSet;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import jetbrains.buildServer.AgentRestrictorType;
import jetbrains.buildServer.Build;
import jetbrains.buildServer.BuildAgent;
import jetbrains.buildServer.BuildType;
import jetbrains.buildServer.BuildTypeDescriptor;
import jetbrains.buildServer.BuildTypeStatusDescriptor;
import jetbrains.buildServer.Used;
import jetbrains.buildServer.agentServer.AgentCheckoutDirRevisionInfo;
import jetbrains.buildServer.buildTriggers.BuildTriggerDescriptor;
import jetbrains.buildServer.log.Loggable;
import jetbrains.buildServer.log.Loggers;
import jetbrains.buildServer.messages.Status;
import jetbrains.buildServer.parameters.ParametersProvider;
import jetbrains.buildServer.parameters.ReferencesResolverUtil;
import jetbrains.buildServer.parameters.ValueResolver;
import jetbrains.buildServer.pipeline.PipelineProject;
import jetbrains.buildServer.pipeline.impl.PipelineDescription;
import jetbrains.buildServer.requirements.Requirement;
import jetbrains.buildServer.responsibility.BuildTypeResponsibilityEntry;
import jetbrains.buildServer.responsibility.ResponsibilityEntry;
import jetbrains.buildServer.responsibility.ResponsibilityEntryFactory;
import jetbrains.buildServer.serverSide.AgentCompatibility;
import jetbrains.buildServer.serverSide.AgentDescription;
import jetbrains.buildServer.serverSide.Branch;
import jetbrains.buildServer.serverSide.BranchCalculationOptions;
import jetbrains.buildServer.serverSide.BranchEx;
import jetbrains.buildServer.serverSide.BranchFilter;
import jetbrains.buildServer.serverSide.BranchSpecProvider;
import jetbrains.buildServer.serverSide.BranchesPolicy;
import jetbrains.buildServer.serverSide.BuildAgentEx;
import jetbrains.buildServer.serverSide.BuildCustomizer;
import jetbrains.buildServer.serverSide.BuildNumbers;
import jetbrains.buildServer.serverSide.BuildPromotion;
import jetbrains.buildServer.serverSide.BuildPromotionEx;
import jetbrains.buildServer.serverSide.BuildPromotionInitializer;
import jetbrains.buildServer.serverSide.BuildRunnerDescriptor;
import jetbrains.buildServer.serverSide.BuildServerEx;
import jetbrains.buildServer.serverSide.BuildServerListener;
import jetbrains.buildServer.serverSide.BuildSettings;
import jetbrains.buildServer.serverSide.BuildTypeCopySettings;
import jetbrains.buildServer.serverSide.BuildTypeEx;
import jetbrains.buildServer.serverSide.BuildTypeOptions;
import jetbrains.buildServer.serverSide.BuildTypeSettings;
import jetbrains.buildServer.serverSide.BuildTypeSettingsCopyOptions;
import jetbrains.buildServer.serverSide.BuildTypeSettingsEx;
import jetbrains.buildServer.serverSide.BuildTypeSettingsListener;
import jetbrains.buildServer.serverSide.BuildTypeTemplate;
import jetbrains.buildServer.serverSide.BuildTypeTemplateEx;
import jetbrains.buildServer.serverSide.CannotAttachToTemplateException;
import jetbrains.buildServer.serverSide.ChangeDescriptor;
import jetbrains.buildServer.serverSide.Compatibility;
import jetbrains.buildServer.serverSide.CompatibilityResult;
import jetbrains.buildServer.serverSide.ConfigAction;
import jetbrains.buildServer.serverSide.ConfigActionEx;
import jetbrains.buildServer.serverSide.ConfigActionsServerListener;
import jetbrains.buildServer.serverSide.CustomDataStorage;
import jetbrains.buildServer.serverSide.DependencyConsumer;
import jetbrains.buildServer.serverSide.DuplicateIdException;
import jetbrains.buildServer.serverSide.InvalidIdentifierException;
import jetbrains.buildServer.serverSide.InvalidVcsRootScopeException;
import jetbrains.buildServer.serverSide.MatchedSettingsVisitor;
import jetbrains.buildServer.serverSide.ObsoleteEntityException;
import jetbrains.buildServer.serverSide.Parameter;
import jetbrains.buildServer.serverSide.ParametersDescriptor;
import jetbrains.buildServer.serverSide.ParametersSupport;
import jetbrains.buildServer.serverSide.PersistFailedException;
import jetbrains.buildServer.serverSide.ProjectManager;
import jetbrains.buildServer.serverSide.QueuedBuildEx;
import jetbrains.buildServer.serverSide.ReadOnlyEntityException;
import jetbrains.buildServer.serverSide.ReadOnlyReason;
import jetbrains.buildServer.serverSide.ResolvedSettings;
import jetbrains.buildServer.serverSide.RunningBuildEx;
import jetbrains.buildServer.serverSide.SAgentRestrictor;
import jetbrains.buildServer.serverSide.SBuild;
import jetbrains.buildServer.serverSide.SBuildAgent;
import jetbrains.buildServer.serverSide.SBuildFeatureDescriptor;
import jetbrains.buildServer.serverSide.SBuildRunnerDescriptor;
import jetbrains.buildServer.serverSide.SBuildType;
import jetbrains.buildServer.serverSide.SFinishedBuild;
import jetbrains.buildServer.serverSide.SPersistentEntity;
import jetbrains.buildServer.serverSide.SProject;
import jetbrains.buildServer.serverSide.SQueuedBuild;
import jetbrains.buildServer.serverSide.SRunningBuild;
import jetbrains.buildServer.serverSide.SecurityContextEx;
import jetbrains.buildServer.serverSide.SimpleParameter;
import jetbrains.buildServer.serverSide.TagData;
import jetbrains.buildServer.serverSide.TagDataUtil;
import jetbrains.buildServer.serverSide.TeamCityProperties;
import jetbrains.buildServer.serverSide.TemplateBecomesInaccessibleException;
import jetbrains.buildServer.serverSide.VcsModificationProcessor;
import jetbrains.buildServer.serverSide.VcsStatusLogger;
import jetbrains.buildServer.serverSide.VcsWorkspaceAccess;
import jetbrains.buildServer.serverSide.agentPools.AgentPoolsActiveFilter;
import jetbrains.buildServer.serverSide.agentPools.AgentPoolsActiveFilterImpl;
import jetbrains.buildServer.serverSide.agentTypes.SAgentType;
import jetbrains.buildServer.serverSide.agentTypes.SAgentTypeComparator;
import jetbrains.buildServer.serverSide.artifacts.SArtifactDependency;
import jetbrains.buildServer.serverSide.audit.ActionType;
import jetbrains.buildServer.serverSide.audit.AuditLog;
import jetbrains.buildServer.serverSide.audit.AuditLogAction;
import jetbrains.buildServer.serverSide.buildDistribution.BuildPromotionInfo;
import jetbrains.buildServer.serverSide.comments.Comment;
import jetbrains.buildServer.serverSide.config.ConfigModification;
import jetbrains.buildServer.serverSide.config.ConfigModificationFactory;
import jetbrains.buildServer.serverSide.db.DBException;
import jetbrains.buildServer.serverSide.db.DBFunctionsProvider;
import jetbrains.buildServer.serverSide.dependency.BuildTypeDependencyGraph;
import jetbrains.buildServer.serverSide.dependency.BuildTypeDependencyGraphImpl;
import jetbrains.buildServer.serverSide.dependency.DependenciesSupplier;
import jetbrains.buildServer.serverSide.dependency.Dependency;
import jetbrains.buildServer.serverSide.dependency.DependencyOptions;
import jetbrains.buildServer.serverSide.dependency.DependencySettings;
import jetbrains.buildServer.serverSide.dependency.Dependent;
import jetbrains.buildServer.serverSide.dependency.OrganizationProjectAccessViolationException;
import jetbrains.buildServer.serverSide.identifiers.BuildTypeIdentifiersManager;
import jetbrains.buildServer.serverSide.identifiers.CustomIdChangeApplier;
import jetbrains.buildServer.serverSide.identifiers.EntityId;
import jetbrains.buildServer.serverSide.impl.BaseBuildTypeIdentity;
import jetbrains.buildServer.serverSide.impl.BaseDummyBuildPromotion;
import jetbrains.buildServer.serverSide.impl.BranchImpl;
import jetbrains.buildServer.serverSide.impl.BuildFactory;
import jetbrains.buildServer.serverSide.impl.BuildTypeBranches;
import jetbrains.buildServer.serverSide.impl.BuildTypeImpl;
import jetbrains.buildServer.serverSide.impl.BuildTypeOrderedBuildsImpl;
import jetbrains.buildServer.serverSide.impl.BuildTypeSettingsImpl;
import jetbrains.buildServer.serverSide.impl.BuildTypeStatusDescriptorImpl;
import jetbrains.buildServer.serverSide.impl.CompositeBranchSpec;
import jetbrains.buildServer.serverSide.impl.DependencyCheckResult;
import jetbrains.buildServer.serverSide.impl.DependencyCollector;
import jetbrains.buildServer.serverSide.impl.DependencyType;
import jetbrains.buildServer.serverSide.impl.DummyBuild;
import jetbrains.buildServer.serverSide.impl.DummyBuildPromotion;
import jetbrains.buildServer.serverSide.impl.EditableBuildTypeCopy;
import jetbrains.buildServer.serverSide.impl.IllegalPersonalBuildException;
import jetbrains.buildServer.serverSide.impl.InMemoryBuildPromotion;
import jetbrains.buildServer.serverSide.impl.IncorrectArchivationCommentsRemapper;
import jetbrains.buildServer.serverSide.impl.IsEnabledFilter;
import jetbrains.buildServer.serverSide.impl.LazyValueResolver;
import jetbrains.buildServer.serverSide.impl.LogUtil;
import jetbrains.buildServer.serverSide.impl.NullComment;
import jetbrains.buildServer.serverSide.impl.OrganizationProjects;
import jetbrains.buildServer.serverSide.impl.PersonalBuildPromotionFilter;
import jetbrains.buildServer.serverSide.impl.ProjectEx;
import jetbrains.buildServer.serverSide.impl.ReadOnlyUtil;
import jetbrains.buildServer.serverSide.impl.RemoteBuildType;
import jetbrains.buildServer.serverSide.impl.RemoteBuildTypeEx;
import jetbrains.buildServer.serverSide.impl.ReverseDepParametersProcessor;
import jetbrains.buildServer.serverSide.impl.SecureDataStorage;
import jetbrains.buildServer.serverSide.impl.TriggerWithNameFilter;
import jetbrains.buildServer.serverSide.impl.ValidationProvider;
import jetbrains.buildServer.serverSide.impl.audit.ActionTypeSet;
import jetbrains.buildServer.serverSide.impl.beans.BuildTypeContext;
import jetbrains.buildServer.serverSide.impl.beans.BuildTypeIdentityContext;
import jetbrains.buildServer.serverSide.impl.cleanup.HistoryRetentionPolicy;
import jetbrains.buildServer.serverSide.impl.cleanup.v2019.settings.ClashingCleanupRules;
import jetbrains.buildServer.serverSide.impl.cleanup.v2019.settings.CleanupProperties;
import jetbrains.buildServer.serverSide.impl.dependency.BuildPromotionGraphFactory;
import jetbrains.buildServer.serverSide.impl.dependency.UnresolvedDependency;
import jetbrains.buildServer.serverSide.impl.persisting.projects.BackgroundPersistTask;
import jetbrains.buildServer.serverSide.impl.projectSources.PatchCache;
import jetbrains.buildServer.serverSide.impl.projectSources.PatchComposer;
import jetbrains.buildServer.serverSide.impl.projects.ReloadProjectsConfig;
import jetbrains.buildServer.serverSide.impl.timeEstimation.BuildDurationEstimateContext;
import jetbrains.buildServer.serverSide.impl.virtualAgent.StartableAgentType;
import jetbrains.buildServer.serverSide.parameters.ParameterFactory;
import jetbrains.buildServer.serverSide.parameters.types.PasswordType;
import jetbrains.buildServer.serverSide.util.FileHistory;
import jetbrains.buildServer.serverSide.vcs.FullCheckoutFlag;
import jetbrains.buildServer.serverSide.vcs.VcsLabelingSettings;
import jetbrains.buildServer.serverSide.versionedSettings.VersionedSettingsManager;
import jetbrains.buildServer.step.conditions.BuildStepConditions;
import jetbrains.buildServer.users.SUser;
import jetbrains.buildServer.users.User;
import jetbrains.buildServer.util.CollectionsUtil;
import jetbrains.buildServer.util.ExceptionUtil;
import jetbrains.buildServer.util.FileUtil;
import jetbrains.buildServer.util.Hash;
import jetbrains.buildServer.util.ItemProcessor;
import jetbrains.buildServer.util.NameGenerator;
import jetbrains.buildServer.util.NamedThreadFactory;
import jetbrains.buildServer.util.Option;
import jetbrains.buildServer.util.StringUtil;
import jetbrains.buildServer.util.browser.Browser;
import jetbrains.buildServer.util.filters.Filter;
import jetbrains.buildServer.util.filters.FilterUtil;
import jetbrains.buildServer.vcs.BranchSpec;
import jetbrains.buildServer.vcs.CheckoutRules;
import jetbrains.buildServer.vcs.InvalidBranchSpecException;
import jetbrains.buildServer.vcs.LVcsRootInstance;
import jetbrains.buildServer.vcs.OperationRequestor;
import jetbrains.buildServer.vcs.PathMapping;
import jetbrains.buildServer.vcs.RepositoryStateImpl;
import jetbrains.buildServer.vcs.SVcsModification;
import jetbrains.buildServer.vcs.SVcsRoot;
import jetbrains.buildServer.vcs.SVcsRootEx;
import jetbrains.buildServer.vcs.SelectPrevBuildPolicy;
import jetbrains.buildServer.vcs.VcsException;
import jetbrains.buildServer.vcs.VcsFile;
import jetbrains.buildServer.vcs.VcsModificationHistory;
import jetbrains.buildServer.vcs.VcsRoot;
import jetbrains.buildServer.vcs.VcsRootEntry;
import jetbrains.buildServer.vcs.VcsRootInstance;
import jetbrains.buildServer.vcs.VcsRootInstanceEntry;
import jetbrains.buildServer.vcs.VcsRootInstanceEx;
import jetbrains.buildServer.vcs.impl.SVcsRootImpl;
import jetbrains.buildServer.vcs.patches.LowLevelPatchBuilder;
import jetbrains.vcs.api.VcsServiceFactory;
import jetbrains.vcs.api.data.VcsRepositoryState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Exception performing whole class analysis ignored.
 */
public class BuildTypeImpl
extends BaseBuildTypeIdentity
implements BuildTypeEx,
BuildTypeSettingsListener {
    private static final Logger LOG;
    public static final String BRANCH_SPEC_STORAGE_ID = "jetbrains.buildServer.buildTriggers.vcs.VcsRootChangesLoader";
    public static final String SHOW_WORSE_BRANCH_STATUS = "teamcity.buildTypeStatus.showWorseBranchStatus";
    private static final String PREV_BRANCH_SPEC_PROP_PREFIX = "prevBranchSpec-";
    private static final String PREV_ROOT_INSTANCE_PROP_PREFIX = "prevRootInstance-";
    private final AtomicBoolean myPaused;
    private volatile BuildNumbers myBuildNumbers;
    private String myDescription;
    private final AtomicBoolean myAttachedToModel;
    private final List<Runnable> myDelayedTasks;
    private volatile BuildTypeSettingsEx myBuildTypeSettings;
    @NotNull
    protected final BuildTypeContext myContext;
    private static final Comment NULL_COMMENT;
    private final AtomicReference<Comment> myPauseActivateComment;
    private final BuildTypeBranches myBranches;
    private final BuildTypeOrderedBuildsImpl myBuildTypeOrderedBuilds;
    private final Object myTagsLock;
    private Set<TagData> myTagsData;
    private final DependencyCollector myDependencyCollector;
    private final List<File> myOldArtifactDirectories;
    private final boolean myEmptyUuid;
    private final AtomicReference<Map<Long, VcsRootInstanceEntry>> myFullyResolvedVcsRootInstancesCache;
    public static final String OVERRIDE_DEP_PARAMETER_PREFIX = "override.dep.";
    public static final String REVERSE_DEP_PARAMETER_PREFIX = "reverse.dep.";
    private static final ThreadLocal<AtomicInteger> ourResolvingCounter;
    private static final String CHECKOUT_RULES_CONFLICT_PROP = "##teamcity:settingsRevisionConflict";
    static final /* synthetic */ boolean $assertionsDisabled;

    protected BuildTypeImpl(@NotNull BuildTypeContext buildTypeContext, @NotNull EntityId<String> entityId, @NotNull String string, @NotNull BuildTypeSettingsEx buildTypeSettingsEx, @NotNull ProjectEx projectEx, boolean bl) {
        if (buildTypeContext == null) {
            BuildTypeImpl.$$$reportNull$$$0(0);
        }
        if (entityId == null) {
            BuildTypeImpl.$$$reportNull$$$0(1);
        }
        if (string == null) {
            BuildTypeImpl.$$$reportNull$$$0(2);
        }
        if (buildTypeSettingsEx == null) {
            BuildTypeImpl.$$$reportNull$$$0(3);
        }
        if (projectEx == null) {
            BuildTypeImpl.$$$reportNull$$$0(4);
        }
        super((BuildTypeIdentityContext)buildTypeContext, projectEx, entityId, string);
        this.myPaused = new AtomicBoolean(false);
        this.myAttachedToModel = new AtomicBoolean(false);
        this.myDelayedTasks = new ArrayList<Runnable>();
        this.myPauseActivateComment = new AtomicReference();
        this.myTagsLock = new Object();
        this.myOldArtifactDirectories = new ArrayList<File>();
        this.myFullyResolvedVcsRootInstancesCache = new AtomicReference(new HashMap());
        this.myEmptyUuid = bl;
        if (projectEx.isRootProject()) {
            throw new IllegalStateException("Attempted to create a build configuration in Root project. Root project cannot contain build configurations.");
        }
        this.myContext = buildTypeContext;
        this.changeSettingsTo(buildTypeSettingsEx);
        this.myBranches = new BuildTypeBranches((BuildTypeEx)this, buildTypeContext);
        this.myBuildTypeOrderedBuilds = new BuildTypeOrderedBuildsImpl(this.getInternalId(), (DBFunctionsProvider)this.myContext.getSQLRunner());
        this.myDependencyCollector = new DependencyCollector();
        this.markEdited();
    }

    @NotNull
    private AgentPoolsActiveFilter getAgentPoolsFilter() {
        return new AgentPoolsActiveFilterImpl((SProject)this.getProject());
    }

    private void changeSettingsTo(@NotNull BuildTypeSettingsEx buildTypeSettingsEx) {
        try {
            if (buildTypeSettingsEx == null) {
                BuildTypeImpl.$$$reportNull$$$0(5);
            }
        }
        catch (Exception exception) {
            throw BuildTypeImpl.a(exception);
        }
        List list = this.myProject.getEnforcedSettings().validate((BuildTypeSettings)buildTypeSettingsEx);
        for (ValidationProvider.ValidationError validationError : list) {
            try {
                if (validationError == null) continue;
                Loggers.SERVER.warn("Error validating " + validationError.getItemName() + " in " + this.getFullName() + ": " + validationError.getErrorCause() + ". Enforced value will be used.");
            }
            catch (Exception exception) {
                throw BuildTypeImpl.a(exception);
            }
        }
        if (this.myBuildTypeSettings != null) {
            this.myBuildTypeSettings.removeListener((BuildTypeSettingsListener)this);
            for (ValidationProvider.ValidationError validationError : this.myContext.getVcsRootAssociationHolder().getAttachedRoots((SBuildType)this)) {
                try {
                    if (buildTypeSettingsEx.containsVcsRoot(validationError.getId())) continue;
                    this.beforeRemoveVcsRoot((SVcsRoot)validationError);
                    this.afterRemoveVcsRoot((SVcsRoot)validationError);
                }
                catch (Exception exception) {
                    throw BuildTypeImpl.a(exception);
                }
            }
            for (ValidationProvider.ValidationError validationError : this.myBuildTypeSettings.getDependencies()) {
                this.beforeRemoveDependency((Dependency)validationError);
                this.afterRemoveDependency((Dependency)validationError);
            }
        }
        try {
            this.attachNewSettings(buildTypeSettingsEx);
        }
        catch (Exception exception) {
            try {
                if (this.myBuildTypeSettings != null) {
                    this.attachNewSettings(this.myBuildTypeSettings);
                    this.resetDummyBuilds();
                }
            }
            catch (Exception exception2) {
                throw BuildTypeImpl.a(exception2);
            }
            ExceptionUtil.rethrowAsRuntimeException((Throwable)exception);
        }
        try {
            this.myBuildTypeSettings = buildTypeSettingsEx;
            if (this.myAttachedToModel.get()) {
                this.myBuildTypeSettings.attachedToModel();
            }
        }
        catch (Exception exception) {
            throw BuildTypeImpl.a(exception);
        }
        try {
            if (!buildTypeSettingsEx.getArtifactDependencies().isEmpty()) {
                this.artifactDependenciesChanged();
            }
        }
        catch (Exception exception) {
            throw BuildTypeImpl.a(exception);
        }
        this.activateTriggersIfSettingsChanged();
        this.resetDummyBuilds();
    }

    private void attachNewSettings(@NotNull BuildTypeSettingsEx buildTypeSettingsEx) {
        try {
            if (buildTypeSettingsEx == null) {
                BuildTypeImpl.$$$reportNull$$$0(6);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        buildTypeSettingsEx.setParametersSupport((ParametersSupport)this);
        for (SVcsRoot sVcsRoot : buildTypeSettingsEx.getVcsRoots()) {
            this.beforeAddVcsRoot(sVcsRoot);
            this.afterAddVcsRoot(sVcsRoot);
        }
        for (SVcsRoot sVcsRoot : buildTypeSettingsEx.getDependencies()) {
            this.beforeAddDependency((Dependency)sVcsRoot);
            this.afterAddDependency((Dependency)sVcsRoot);
        }
        buildTypeSettingsEx.addListener((BuildTypeSettingsListener)this, true);
    }

    public boolean isShouldFailBuildIfTestsFailed() {
        return (Boolean)this.myBuildTypeSettings.getOption(BT_FAIL_IF_TESTS_FAIL);
    }

    public boolean isAllowExternalStatus() {
        return (Boolean)this.myBuildTypeSettings.getOption(BT_ALLOW_EXTERNAL_STATUS);
    }

    public int getExecutionTimeoutMin() {
        return (Integer)this.myBuildTypeSettings.getOption(BT_EXECUTION_TIMEOUT);
    }

    @NotNull
    public String getBuildTypeId() {
        String string;
        try {
            string = this.getInternalId();
            if (string == null) {
                BuildTypeImpl.$$$reportNull$$$0(7);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return string;
    }

    @NotNull
    public String getProjectExternalId() {
        String string;
        try {
            string = this.myProject.getExternalId();
            if (string == null) {
                BuildTypeImpl.$$$reportNull$$$0(8);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return string;
    }

    @NotNull
    public String getProjectName() {
        String string;
        try {
            string = this.getProject().getName();
            if (string == null) {
                BuildTypeImpl.$$$reportNull$$$0(9);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return string;
    }

    @NotNull
    public static String getFullName(String string, String string2) {
        String string3;
        try {
            string3 = string + " / " + string2;
            if (string3 == null) {
                BuildTypeImpl.$$$reportNull$$$0(10);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return string3;
    }

    public void setDescription(String string) {
        this.beforeModification();
        this.markEdited();
        String string2 = this.myDescription;
        this.myDescription = string;
        ((BuildServerListener)this.myContext.getEventDispatcher().getMulticaster()).buildTypeDescriptionUpdated((SBuildType)this, string2, string);
    }

    public void setCheckoutType(BuildTypeDescriptor.CheckoutType checkoutType) {
        this.setOption(BT_CHECKOUT_MODE, checkoutType.name());
    }

    public void setCheckoutDirectory(String string) {
        this.setOption(BT_CHECKOUT_DIR, StringUtil.emptyIfNull((String)string));
    }

    public void removeRequirement(String string) {
        this.beforeModification();
        this.myBuildTypeSettings.removeRequirement(string);
        this.setDirtyCompatibility();
    }

    public void removeRequirement(Requirement requirement) {
        this.beforeModification();
        this.markEdited();
        this.myBuildTypeSettings.removeRequirement(requirement);
        this.setDirtyCompatibility();
    }

    @NotNull
    public String getBuildNumberPattern() {
        String string;
        try {
            string = this.myBuildTypeSettings.getBuildNumberPattern();
            if (string == null) {
                BuildTypeImpl.$$$reportNull$$$0(11);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return string;
    }

    public void setBuildNumberPattern(@NotNull String string) {
        try {
            if (string == null) {
                BuildTypeImpl.$$$reportNull$$$0(12);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        this.beforeModification();
        this.markEdited();
        this.myBuildTypeSettings.setBuildNumberPattern(string);
    }

    public String getDescription() {
        return this.myDescription;
    }

    @NotNull
    public Collection<String> getRunnerTypes() {
        Collection collection;
        try {
            collection = this.myBuildTypeSettings.getRunnerTypes();
            if (collection == null) {
                BuildTypeImpl.$$$reportNull$$$0(13);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return collection;
    }

    @NotNull
    public String getId() {
        String string;
        try {
            string = this.getInternalId();
            if (string == null) {
                BuildTypeImpl.$$$reportNull$$$0(14);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return string;
    }

    public int getMaximumNumberOfBuilds() {
        return (Integer)this.myBuildTypeSettings.getOption(BT_MAX_RUNNING_BUILDS);
    }

    @NotNull
    public String getMaximumNumberOfBuildsPerBranch() {
        String string;
        try {
            string = (String)this.myBuildTypeSettings.getOption(BT_MAX_RUNNING_BUILDS_PER_BRANCH);
            if (string == null) {
                BuildTypeImpl.$$$reportNull$$$0(15);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return string;
    }

    @NotNull
    public List<Requirement> getRequirements() {
        List list;
        try {
            list = this.myBuildTypeSettings.getRequirements();
            if (list == null) {
                BuildTypeImpl.$$$reportNull$$$0(16);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return list;
    }

    @NotNull
    public List<Requirement> getImplicitRequirements() {
        List list;
        try {
            list = this.myBuildTypeSettings.getImplicitRequirements();
            if (list == null) {
                BuildTypeImpl.$$$reportNull$$$0(17);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return list;
    }

    @NotNull
    public List<Requirement> getRunTypeRequirements() {
        List list;
        try {
            list = this.myBuildTypeSettings.getRunTypeRequirements();
            if (list == null) {
                BuildTypeImpl.$$$reportNull$$$0(18);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return list;
    }

    @NotNull
    public List<Requirement> getBuildFeatureRequirements() {
        List list;
        try {
            list = this.myBuildTypeSettings.getBuildFeatureRequirements();
            if (list == null) {
                BuildTypeImpl.$$$reportNull$$$0(19);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return list;
    }

    @NotNull
    public SBuildRunnerDescriptor addBuildRunner(@NotNull BuildRunnerDescriptor buildRunnerDescriptor) {
        SBuildRunnerDescriptor sBuildRunnerDescriptor;
        try {
            if (buildRunnerDescriptor == null) {
                BuildTypeImpl.$$$reportNull$$$0(20);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            this.beforeModification();
            this.markEdited();
            sBuildRunnerDescriptor = this.myBuildTypeSettings.addBuildRunner(buildRunnerDescriptor);
            if (sBuildRunnerDescriptor == null) {
                BuildTypeImpl.$$$reportNull$$$0(21);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return sBuildRunnerDescriptor;
    }

    @NotNull
    public SBuildRunnerDescriptor addBuildRunner(@NotNull String string, @NotNull String string2, @NotNull Map<String, String> map) {
        SBuildRunnerDescriptor sBuildRunnerDescriptor;
        try {
            if (string == null) {
                BuildTypeImpl.$$$reportNull$$$0(22);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            if (string2 == null) {
                BuildTypeImpl.$$$reportNull$$$0(23);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            if (map == null) {
                BuildTypeImpl.$$$reportNull$$$0(24);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            this.beforeModification();
            this.markEdited();
            sBuildRunnerDescriptor = this.myBuildTypeSettings.addBuildRunner(string, string2, map);
            if (sBuildRunnerDescriptor == null) {
                BuildTypeImpl.$$$reportNull$$$0(25);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return sBuildRunnerDescriptor;
    }

    @NotNull
    public SBuildRunnerDescriptor addBuildRunner(@NotNull String string, @NotNull String string2, @NotNull String string3, @NotNull Map<String, String> map) throws DuplicateIdException, InvalidIdentifierException, ReadOnlyEntityException {
        SBuildRunnerDescriptor sBuildRunnerDescriptor;
        try {
            if (string == null) {
                BuildTypeImpl.$$$reportNull$$$0(26);
            }
        }
        catch (DuplicateIdException duplicateIdException) {
            throw BuildTypeImpl.a(duplicateIdException);
        }
        try {
            if (string2 == null) {
                BuildTypeImpl.$$$reportNull$$$0(27);
            }
        }
        catch (DuplicateIdException duplicateIdException) {
            throw BuildTypeImpl.a(duplicateIdException);
        }
        try {
            if (string3 == null) {
                BuildTypeImpl.$$$reportNull$$$0(28);
            }
        }
        catch (DuplicateIdException duplicateIdException) {
            throw BuildTypeImpl.a(duplicateIdException);
        }
        try {
            if (map == null) {
                BuildTypeImpl.$$$reportNull$$$0(29);
            }
        }
        catch (DuplicateIdException duplicateIdException) {
            throw BuildTypeImpl.a(duplicateIdException);
        }
        try {
            this.beforeModification();
            this.markEdited();
            sBuildRunnerDescriptor = this.myBuildTypeSettings.addBuildRunner(string, string2, string3, map);
            if (sBuildRunnerDescriptor == null) {
                BuildTypeImpl.$$$reportNull$$$0(30);
            }
        }
        catch (DuplicateIdException duplicateIdException) {
            throw BuildTypeImpl.a(duplicateIdException);
        }
        return sBuildRunnerDescriptor;
    }

    public SBuildRunnerDescriptor removeBuildRunner(@NotNull String string) {
        try {
            if (string == null) {
                BuildTypeImpl.$$$reportNull$$$0(31);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        this.beforeModification();
        SBuildRunnerDescriptor sBuildRunnerDescriptor = this.myBuildTypeSettings.removeBuildRunner(string);
        try {
            if (sBuildRunnerDescriptor != null) {
                this.markEdited();
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return sBuildRunnerDescriptor;
    }

    public boolean updateBuildRunner(@NotNull String string, @NotNull String string2, @NotNull String string3, @NotNull Map<String, String> map) {
        try {
            if (string == null) {
                BuildTypeImpl.$$$reportNull$$$0(32);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            if (string2 == null) {
                BuildTypeImpl.$$$reportNull$$$0(33);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            if (string3 == null) {
                BuildTypeImpl.$$$reportNull$$$0(34);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            if (map == null) {
                BuildTypeImpl.$$$reportNull$$$0(35);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        this.beforeModification();
        boolean bl = this.myBuildTypeSettings.updateBuildRunner(string, string2, string3, map);
        try {
            if (bl) {
                this.markEdited();
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return bl;
    }

    @NotNull
    public BuildStepConditions getRunnerConditions(@NotNull SBuildRunnerDescriptor sBuildRunnerDescriptor) {
        BuildStepConditions buildStepConditions;
        try {
            if (sBuildRunnerDescriptor == null) {
                BuildTypeImpl.$$$reportNull$$$0(36);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            buildStepConditions = this.myBuildTypeSettings.getRunnerConditions(sBuildRunnerDescriptor);
            if (buildStepConditions == null) {
                BuildTypeImpl.$$$reportNull$$$0(37);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return buildStepConditions;
    }

    @NotNull
    public List<SBuildRunnerDescriptor> getBuildRunners() {
        List list;
        try {
            list = this.myBuildTypeSettings.getBuildRunners();
            if (list == null) {
                BuildTypeImpl.$$$reportNull$$$0(38);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return list;
    }

    @Nullable
    public SBuildRunnerDescriptor findBuildRunnerByType(@NotNull String string) {
        try {
            if (string == null) {
                BuildTypeImpl.$$$reportNull$$$0(39);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return this.myBuildTypeSettings.findBuildRunnerByType(string);
    }

    public SBuildRunnerDescriptor findBuildRunnerById(@NotNull String string) {
        try {
            if (string == null) {
                BuildTypeImpl.$$$reportNull$$$0(40);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return this.myBuildTypeSettings.findBuildRunnerById(string);
    }

    public String findRunnerParameter(@NotNull String string) {
        try {
            if (string == null) {
                BuildTypeImpl.$$$reportNull$$$0(41);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return this.myBuildTypeSettings.findRunnerParameter(string);
    }

    public void addRequirement(@NotNull Requirement requirement) {
        try {
            if (requirement == null) {
                BuildTypeImpl.$$$reportNull$$$0(42);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        this.beforeModification();
        this.markEdited();
        this.myBuildTypeSettings.addRequirement(requirement);
        this.setDirtyCompatibility();
    }

    public void addRunParameter(@NotNull Parameter parameter) {
        try {
            if (parameter == null) {
                BuildTypeImpl.$$$reportNull$$$0(43);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        this.beforeModification();
        SBuildRunnerDescriptor sBuildRunnerDescriptor = this.getFirstRunnerOrThrowError();
        HashMap<String, String> hashMap = new HashMap<String, String>(sBuildRunnerDescriptor.getParameters());
        hashMap.put(parameter.getName(), parameter.getValue());
        this.updateBuildRunner(sBuildRunnerDescriptor.getId(), sBuildRunnerDescriptor.getName(), sBuildRunnerDescriptor.getRunType().getType(), hashMap);
        this.textValueChanged();
    }

    private SBuildRunnerDescriptor getFirstRunnerOrThrowError() {
        SBuildRunnerDescriptor sBuildRunnerDescriptor = this.getFirstRunner();
        try {
            if (sBuildRunnerDescriptor == null) {
                throw new IllegalStateException("Build runner is not attached");
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return sBuildRunnerDescriptor;
    }

    @Nullable
    private SBuildRunnerDescriptor getFirstRunner() {
        List<SBuildRunnerDescriptor> list = this.getBuildRunners();
        try {
            if (list.isEmpty()) {
                return null;
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return list.get(0);
    }

    public void addBuildParameter(Parameter parameter) {
        this.beforeModification();
        this.myProject.getEnforcedSettings().validate(parameter);
        this.markEdited();
        this.myBuildTypeSettings.addBuildParameter(parameter);
    }

    @NotNull
    public Map<SBuildAgent, CompatibilityResult> getCompatibilityMap() {
        Map map;
        block7: {
            Map<SBuildAgent, CompatibilityResult> map2;
            block8: {
                try {
                    try {
                        if (!this.isAgentLessBuildType()) break block7;
                        map2 = Collections.emptyMap();
                        if (map2 != null) break block8;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildTypeImpl.a(illegalStateException);
                    }
                    BuildTypeImpl.$$$reportNull$$$0(44);
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildTypeImpl.a(illegalStateException);
                }
            }
            return map2;
        }
        ArrayList arrayList = new ArrayList(this.myContext.getBuildAgentManager().getAllAgents());
        try {
            map = this.getDummyBuildPromotion().getCompatibilityMap(arrayList);
            if (map == null) {
                BuildTypeImpl.$$$reportNull$$$0(45);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return map;
    }

    @NotNull
    public Collection<SBuildAgent> getCompatibleAgents() {
        ArrayList<SBuildAgent> arrayList;
        Map<SBuildAgent, CompatibilityResult> map = this.getCompatibilityMap();
        ArrayList<SBuildAgent> arrayList2 = new ArrayList<SBuildAgent>();
        for (Map.Entry<SBuildAgent, CompatibilityResult> entry : map.entrySet()) {
            try {
                if (!entry.getValue().isCompatible()) continue;
                arrayList2.add(entry.getKey());
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildTypeImpl.a(illegalStateException);
            }
        }
        try {
            arrayList = arrayList2;
            if (arrayList == null) {
                BuildTypeImpl.$$$reportNull$$$0(46);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return arrayList;
    }

    public AgentCompatibility getAgentCompatibility(@NotNull SBuildAgent sBuildAgent) {
        try {
            if (sBuildAgent == null) {
                BuildTypeImpl.$$$reportNull$$$0(47);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            if (this.isAgentLessBuildType()) {
                return new AgentCompatibility((CompatibilityResult)BuildTypeImpl.createIncompatibleResult(), (AgentDescription)sBuildAgent, (SBuildType)this, false);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        CompatibilityResult compatibilityResult = this.getDummyBuildPromotion().getAgentCompatibility((AgentDescription)sBuildAgent);
        return new AgentCompatibility(compatibilityResult, (AgentDescription)sBuildAgent, (SBuildType)this, this.getAgentPoolsFilter().getActive().getAgentFilter().accept((Object)sBuildAgent));
    }

    @NotNull
    public AgentCompatibility getAgentTypeCompatibility(@NotNull SAgentType sAgentType) {
        try {
            if (sAgentType == null) {
                BuildTypeImpl.$$$reportNull$$$0(48);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            if (this.isAgentLessBuildType()) {
                return new AgentCompatibility((CompatibilityResult)BuildTypeImpl.createIncompatibleResult(), (AgentDescription)sAgentType, (SBuildType)this, false);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        CompatibilityResult compatibilityResult = this.getDummyBuildPromotion().getAgentCompatibility((AgentDescription)sAgentType);
        return new AgentCompatibility(compatibilityResult, (AgentDescription)sAgentType, (SBuildType)this, this.getAgentPoolsFilter().getActive().getTypeFilter().accept((Object)sAgentType));
    }

    @NotNull
    public List<StartableAgentType> getCanRunAndCompatibleAgentTypes() {
        List<StartableAgentType> list;
        block8: {
            List<StartableAgentType> list2;
            block9: {
                try {
                    try {
                        if (!this.isAgentLessBuildType()) break block8;
                        list2 = Collections.emptyList();
                        if (list2 != null) break block9;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildTypeImpl.a(illegalStateException);
                    }
                    BuildTypeImpl.$$$reportNull$$$0(49);
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildTypeImpl.a(illegalStateException);
                }
            }
            return list2;
        }
        HashMap<SAgentType, StartableAgentType> hashMap = new HashMap<SAgentType, StartableAgentType>();
        for (StartableAgentType startableAgentType : this.myContext.getVirtualAgentsManager().getStartableAgentTypes()) {
            SAgentType sAgentType = startableAgentType.getAgentType();
            hashMap.put(sAgentType, startableAgentType);
        }
        ArrayList arrayList = new ArrayList(this.getDummyBuildPromotion().getCanRunOnAgentTypes(hashMap.keySet()));
        try {
            arrayList.sort(new SAgentTypeComparator());
            list = arrayList.stream().map(hashMap::get).collect(Collectors.toList());
            if (list == null) {
                BuildTypeImpl.$$$reportNull$$$0(50);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return list;
    }

    @NotNull
    public CompatibilityResult getAgentCompatibility(@NotNull AgentDescription agentDescription) {
        CompatibilityResult compatibilityResult;
        try {
            if (agentDescription == null) {
                BuildTypeImpl.$$$reportNull$$$0(51);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            if (this.isAgentLessBuildType()) {
                return BuildTypeImpl.createIncompatibleResult();
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            compatibilityResult = this.getDummyBuildPromotion().getAgentCompatibility(agentDescription);
            if (compatibilityResult == null) {
                BuildTypeImpl.$$$reportNull$$$0(52);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return compatibilityResult;
    }

    @NotNull
    public static Compatibility createIncompatibleResult() {
        return new /* Unavailable Anonymous Inner Class!! */;
    }

    @NotNull
    public BuildTypeDependencyGraph getDependencyGraph() {
        return new BuildTypeDependencyGraphImpl((BuildTypeEx)this);
    }

    @NotNull
    public Collection<BuildTypeEx> findTops() {
        HashSet<BuildTypeEx> hashSet;
        HashSet<BuildTypeEx> hashSet2 = new HashSet<BuildTypeEx>();
        HashSet hashSet3 = new HashSet();
        LinkedList<BuildTypeImpl> linkedList = new LinkedList<BuildTypeImpl>();
        linkedList.add(this);
        while (!linkedList.isEmpty()) {
            BuildTypeEx buildTypeEx = (BuildTypeEx)linkedList.poll();
            List list = buildTypeEx.getDependencyReferences();
            try {
                if (list.isEmpty()) {
                    hashSet2.add(buildTypeEx);
                    continue;
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildTypeImpl.a(illegalStateException);
            }
            list.forEach(sBuildType -> {
                try {
                    if (hashSet3.add(sBuildType.getBuildTypeId())) {
                        linkedList.push((BuildTypeImpl)((BuildTypeEx)sBuildType));
                    }
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildTypeImpl.a(illegalStateException);
                }
            });
        }
        try {
            hashSet = hashSet2;
            if (hashSet == null) {
                BuildTypeImpl.$$$reportNull$$$0(53);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return hashSet;
    }

    public void removeBuildParameter(String string) {
        this.beforeModification();
        this.markEdited();
        this.myBuildTypeSettings.removeBuildParameter(string);
    }

    @NotNull
    public List<SRunningBuild> getRunningBuilds(@Nullable User user) {
        List list;
        try {
            list = this.myContext.getRunningBuildsManager().getRunningBuilds(user, (SBuildType)this);
            if (list == null) {
                BuildTypeImpl.$$$reportNull$$$0(54);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return list;
    }

    @NotNull
    public List<SRunningBuild> getRunningBuilds() {
        List<SRunningBuild> list;
        try {
            list = this.getRunningBuilds(null);
            if (list == null) {
                BuildTypeImpl.$$$reportNull$$$0(55);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return list;
    }

    public void setPaused(boolean bl, User user) {
        this.setPaused(bl, user, "");
    }

    public void setPaused(boolean bl, User user, String string) {
        boolean bl2;
        AtomicBoolean atomicBoolean;
        try {
            atomicBoolean = this.myPaused;
            bl2 = !bl;
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            if (atomicBoolean.compareAndSet(bl2, bl)) {
                this.markEdited();
                this.doSetPaused(bl, user, string);
                ((BuildServerListener)this.myContext.getEventDispatcher().getMulticaster()).buildTypeActiveStatusChanged((SBuildType)this);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
    }

    public void init(boolean bl, String string) {
        this.myDescription = string;
        this.myPaused.set(bl);
    }

    @Nullable
    public Set<String> getLogicalBranchNames(@NotNull VcsRootInstance vcsRootInstance, @NotNull String string) {
        Object object;
        try {
            if (vcsRootInstance == null) {
                BuildTypeImpl.$$$reportNull$$$0(56);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            if (string == null) {
                BuildTypeImpl.$$$reportNull$$$0(57);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        boolean bl = false;
        BranchSpec branchSpec = this.getLastUsedBranchSpec(vcsRootInstance.getParent());
        if (!branchSpec.isEmpty()) {
            bl = true;
            object = branchSpec.getLogicalBranchName(string);
            try {
                if (object != null) {
                    return CollectionsUtil.setOf((Object[])new String[]{object});
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildTypeImpl.a(illegalStateException);
            }
        }
        object = new RepositoryStateImpl("<unspecified>");
        object.setBranchRevision(string, "some revision");
        List<BranchSpec> list = this.getAdditionalBranchSpecs(vcsRootInstance, (VcsRepositoryState)object);
        HashSet<String> hashSet = new HashSet<String>();
        for (BranchSpec branchSpec2 : list) {
            if (!branchSpec2.isEmpty()) {
                bl = true;
            }
            String string2 = branchSpec2.getLogicalBranchName(string);
            try {
                if (string2 == null) continue;
                hashSet.add(string2);
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildTypeImpl.a(illegalStateException);
            }
        }
        try {
            if (bl) {
                return hashSet;
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return null;
    }

    @NotNull
    public String getDefaultBranchDisplayName() {
        String string;
        HashSet<VcsRootInstance> hashSet = new HashSet<VcsRootInstance>();
        for (VcsRootInstance vcsRootInstance : this.getVcsRootInstances()) {
            try {
                if (!vcsRootInstance.isDagBased()) continue;
                hashSet.add(vcsRootInstance);
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildTypeImpl.a(illegalStateException);
            }
        }
        try {
            string = this.getDefaultBranchDisplayName(hashSet);
            if (string == null) {
                BuildTypeImpl.$$$reportNull$$$0(58);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return string;
    }

    @NotNull
    String getDefaultBranchDisplayName(@NotNull Collection<VcsRootInstance> collection) {
        block23: {
            String string;
            block24: {
                HashSet<String> hashSet;
                block21: {
                    String string2;
                    block22: {
                        try {
                            if (collection == null) {
                                BuildTypeImpl.$$$reportNull$$$0(59);
                            }
                        }
                        catch (IllegalStateException illegalStateException) {
                            throw BuildTypeImpl.a(illegalStateException);
                        }
                        Map<VcsRootInstance, String> map = this.getDefaultVcsBranches(collection);
                        HashSet<String> hashSet2 = new HashSet<String>();
                        hashSet = new HashSet<String>();
                        for (Map.Entry<VcsRootInstance, String> entry : map.entrySet()) {
                            block20: {
                                Set<String> set;
                                block19: {
                                    String string3 = entry.getValue();
                                    hashSet.add(string3);
                                    set = this.getLogicalBranchNames(entry.getKey(), string3);
                                    try {
                                        if (set == null) {
                                            continue;
                                        }
                                    }
                                    catch (IllegalStateException illegalStateException) {
                                        throw BuildTypeImpl.a(illegalStateException);
                                    }
                                    try {
                                        if (!set.isEmpty()) break block19;
                                        hashSet2.add(string3);
                                        break block20;
                                    }
                                    catch (IllegalStateException illegalStateException) {
                                        throw BuildTypeImpl.a(illegalStateException);
                                    }
                                }
                                hashSet2.addAll(set);
                            }
                            try {
                                if (hashSet2.size() <= 1) continue;
                                return "<default>";
                            }
                            catch (IllegalStateException illegalStateException) {
                                throw BuildTypeImpl.a(illegalStateException);
                            }
                        }
                        try {
                            try {
                                if (hashSet2.size() != 1) break block21;
                                string2 = (String)hashSet2.iterator().next();
                                if (string2 != null) break block22;
                            }
                            catch (IllegalStateException illegalStateException) {
                                throw BuildTypeImpl.a(illegalStateException);
                            }
                            BuildTypeImpl.$$$reportNull$$$0(60);
                        }
                        catch (IllegalStateException illegalStateException) {
                            throw BuildTypeImpl.a(illegalStateException);
                        }
                    }
                    return string2;
                }
                try {
                    try {
                        if (hashSet.size() != 1) break block23;
                        string = (String)hashSet.iterator().next();
                        if (string != null) break block24;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildTypeImpl.a(illegalStateException);
                    }
                    BuildTypeImpl.$$$reportNull$$$0(61);
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildTypeImpl.a(illegalStateException);
                }
            }
            return string;
        }
        return "<default>";
    }

    @NotNull
    public BranchEx getBranchByDisplayName(@NotNull String string) {
        BranchEx branchEx;
        block16: {
            BranchEx branchEx2;
            block17: {
                block14: {
                    BranchEx branchEx3;
                    block15: {
                        try {
                            if (string == null) {
                                BuildTypeImpl.$$$reportNull$$$0(62);
                            }
                        }
                        catch (IllegalStateException illegalStateException) {
                            throw BuildTypeImpl.a(illegalStateException);
                        }
                        try {
                            try {
                                if (!string.equals("<default>")) break block14;
                                branchEx3 = this.getBranch("<default>");
                                if (branchEx3 != null) break block15;
                            }
                            catch (IllegalStateException illegalStateException) {
                                throw BuildTypeImpl.a(illegalStateException);
                            }
                            BuildTypeImpl.$$$reportNull$$$0(63);
                        }
                        catch (IllegalStateException illegalStateException) {
                            throw BuildTypeImpl.a(illegalStateException);
                        }
                    }
                    return branchEx3;
                }
                String string2 = this.getDefaultBranchDisplayName();
                try {
                    try {
                        if (!string.equals(string2)) break block16;
                        branchEx2 = this.getBranch("<default>");
                        if (branchEx2 != null) break block17;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildTypeImpl.a(illegalStateException);
                    }
                    BuildTypeImpl.$$$reportNull$$$0(64);
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildTypeImpl.a(illegalStateException);
                }
            }
            return branchEx2;
        }
        try {
            branchEx = this.getBranch(string);
            if (branchEx == null) {
                BuildTypeImpl.$$$reportNull$$$0(65);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return branchEx;
    }

    public boolean hasBuilds() {
        return this.myBuildTypeOrderedBuilds.hasBuilds();
    }

    @NotNull
    private Map<VcsRootInstance, String> getDefaultVcsBranches(@NotNull Collection<VcsRootInstance> collection) {
        HashMap<VcsRootInstance, String> hashMap;
        try {
            if (collection == null) {
                BuildTypeImpl.$$$reportNull$$$0(66);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        HashMap<VcsRootInstance, String> hashMap2 = new HashMap<VcsRootInstance, String>();
        for (VcsRootInstance vcsRootInstance : collection) {
            String string = ((VcsRootInstanceEx)vcsRootInstance).getDefaultBranchName();
            try {
                if (string == null) continue;
                hashMap2.put(vcsRootInstance, string);
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildTypeImpl.a(illegalStateException);
            }
        }
        try {
            hashMap = hashMap2;
            if (hashMap == null) {
                BuildTypeImpl.$$$reportNull$$$0(67);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return hashMap;
    }

    private void doSetPaused(boolean bl, User user, String string) {
        ActionType actionType;
        AuditLog auditLog;
        for (BuildTriggerDescriptor buildTriggerDescriptor : this.getBuildTriggersCollection()) {
            try {
                if (bl) {
                    this.deactivateBuildTriggers(buildTriggerDescriptor.getId(), true);
                    continue;
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildTypeImpl.a(illegalStateException);
            }
            this.activateTriggerIfNeeded(buildTriggerDescriptor.getId());
        }
        try {
            auditLog = this.auditLog();
            actionType = bl ? ActionType.BUILD_TYPE_PAUSE : ActionType.BUILD_TYPE_ACTIVATE;
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        auditLog.log(actionType, string, null, (SUser)user, new Object[0]);
        this.myPauseActivateComment.set(null);
    }

    @NotNull
    private AuditLog auditLog() {
        AuditLog auditLog;
        try {
            auditLog = this.myContext.getAuditLogFactory().createForBuildType((BuildType)this);
            if (auditLog == null) {
                BuildTypeImpl.$$$reportNull$$$0(68);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return auditLog;
    }

    @Nullable
    public Comment getPauseComment() {
        Comment comment = this.myPauseActivateComment.get();
        if (comment == null) {
            block8: {
                comment = this.myContext.getAuditLogProvider().findLastComment(this.auditLog().getObject(), new ActionType[]{ActionType.BUILD_TYPE_PAUSE, ActionType.BUILD_TYPE_ACTIVATE});
                if (comment == null) {
                    comment = NULL_COMMENT;
                } else {
                    String string = IncorrectArchivationCommentsRemapper.remapBuildTypeComment((String)comment.getComment());
                    try {
                        if (string == null || string.equals(comment.getComment())) break block8;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildTypeImpl.a(illegalStateException);
                    }
                    Date date = comment.getTimestamp();
                    comment.setCurrentInfo(comment.getUser(), "automatically paused on archiving the project");
                    comment.setTimestamp(date);
                }
            }
            try {
                if (this.myPauseActivateComment.compareAndSet(null, comment)) {
                    return this.unwrap(comment);
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildTypeImpl.a(illegalStateException);
            }
            return this.getPauseComment();
        }
        return this.unwrap(comment);
    }

    @Nullable
    public Comment getLastPauseComment() {
        return this.myContext.getAuditLogProvider().findLastComment(this.auditLog().getObject(), new ActionType[]{ActionType.BUILD_TYPE_PAUSE});
    }

    @Nullable
    private Comment unwrap(Comment comment) {
        Comment comment2;
        try {
            comment2 = comment == NULL_COMMENT ? null : comment;
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return comment2;
    }

    public boolean isPaused() {
        return this.myPaused.get();
    }

    public List<SVcsModification> getModificationsSinceLastSuccessful() {
        return this.getDummyBuild().getChanges(SelectPrevBuildPolicy.SINCE_LAST_SUCCESSFULLY_FINISHED_BUILD, false);
    }

    @Nullable
    public SQueuedBuild addToQueue(@NotNull String string) {
        try {
            if (string == null) {
                BuildTypeImpl.$$$reportNull$$$0(69);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return this.doAddToQueue(null, this.createBuildPromotion(), string);
    }

    @Nullable
    public SQueuedBuild addToQueue(@NotNull BuildPromotionEx buildPromotionEx, @NotNull String string) {
        try {
            if (buildPromotionEx == null) {
                BuildTypeImpl.$$$reportNull$$$0(70);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            if (string == null) {
                BuildTypeImpl.$$$reportNull$$$0(71);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return this.doAddToQueue(null, buildPromotionEx, string);
    }

    @Nullable
    public SQueuedBuild addToQueue(@NotNull BuildAgent buildAgent, @NotNull String string) {
        try {
            if (buildAgent == null) {
                BuildTypeImpl.$$$reportNull$$$0(72);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            if (string == null) {
                BuildTypeImpl.$$$reportNull$$$0(73);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return this.doAddToQueue(this.myContext.getAgentRestrictorFactory().createFor(AgentRestrictorType.SINGLE_AGENT, buildAgent.getId()), this.createBuildPromotion(), string);
    }

    @Nullable
    public SQueuedBuild addToQueue(@NotNull SAgentRestrictor sAgentRestrictor, @NotNull BuildPromotionEx buildPromotionEx, @NotNull String string) {
        try {
            if (sAgentRestrictor == null) {
                BuildTypeImpl.$$$reportNull$$$0(74);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            if (buildPromotionEx == null) {
                BuildTypeImpl.$$$reportNull$$$0(75);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            if (string == null) {
                BuildTypeImpl.$$$reportNull$$$0(76);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return this.doAddToQueue(sAgentRestrictor, buildPromotionEx, string);
    }

    @Nullable
    private SQueuedBuild doAddToQueue(@Nullable SAgentRestrictor sAgentRestrictor, @NotNull BuildPromotionEx buildPromotionEx, @NotNull String string) {
        try {
            if (buildPromotionEx == null) {
                BuildTypeImpl.$$$reportNull$$$0(77);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            if (string == null) {
                BuildTypeImpl.$$$reportNull$$$0(78);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        List list = this.myContext.getQueue().addToQueue(Collections.singletonMap(buildPromotionEx, sAgentRestrictor), string);
        try {
            if (!list.isEmpty()) {
                return (SQueuedBuild)list.get(0);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return null;
    }

    @NotNull
    public BuildTypeSettingsEx getSettings() {
        BuildTypeSettingsEx buildTypeSettingsEx;
        try {
            buildTypeSettingsEx = this.myBuildTypeSettings;
            if (buildTypeSettingsEx == null) {
                BuildTypeImpl.$$$reportNull$$$0(79);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return buildTypeSettingsEx;
    }

    public void dispose() {
        this.myBuildTypeSettings.removeListener((BuildTypeSettingsListener)this);
        this.myDelayedTasks.clear();
    }

    @NotNull
    public List<SQueuedBuild> getQueuedBuilds(@Nullable User user) {
        List list;
        List list2;
        block7: {
            List list3;
            block8: {
                list2 = this.myContext.getQueue().getItems(this.getInternalId());
                try {
                    try {
                        if (user != null) break block7;
                        list3 = list2;
                        if (list3 != null) break block8;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildTypeImpl.a(illegalStateException);
                    }
                    BuildTypeImpl.$$$reportNull$$$0(80);
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildTypeImpl.a(illegalStateException);
                }
            }
            return list3;
        }
        PersonalBuildPromotionFilter personalBuildPromotionFilter = new PersonalBuildPromotionFilter(user);
        Filter filter = sQueuedBuild -> {
            BuildPromotion buildPromotion = sQueuedBuild.getBuildPromotion();
            return personalBuildPromotionFilter.accept(buildPromotion);
        };
        try {
            list = (List)FilterUtil.filterCollection((Collection)list2, (Filter)filter);
            if (list == null) {
                BuildTypeImpl.$$$reportNull$$$0(81);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return list;
    }

    @NotNull
    public List<SQueuedBuild> getQueuedBuilds() {
        List<SQueuedBuild> list;
        try {
            list = this.getQueuedBuilds(null);
            if (list == null) {
                BuildTypeImpl.$$$reportNull$$$0(82);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return list;
    }

    public List<SVcsModification> getPendingChanges() {
        return this.getDummyBuild().getChanges(SelectPrevBuildPolicy.SINCE_LAST_BUILD, true);
    }

    public Collection<SUser> getPendingChangesCommitters() {
        return this.getDummyBuild().getCommitters(SelectPrevBuildPolicy.SINCE_LAST_BUILD).getUsers();
    }

    @NotNull
    public Map<String, String> getBuildParameters() {
        Map map;
        try {
            map = this.myBuildTypeSettings.getBuildParameters();
            if (map == null) {
                BuildTypeImpl.$$$reportNull$$$0(83);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return map;
    }

    @NotNull
    public Map<String, String> getParameters() {
        Map map;
        try {
            map = this.myBuildTypeSettings.getParameters();
            if (map == null) {
                BuildTypeImpl.$$$reportNull$$$0(84);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return map;
    }

    @Nullable
    public String getParameterValue(@NotNull String string) {
        try {
            if (string == null) {
                BuildTypeImpl.$$$reportNull$$$0(85);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return this.myBuildTypeSettings.getParameterValue(string);
    }

    @NotNull
    public Collection<Parameter> getOwnParametersCollection() {
        Collection collection;
        try {
            collection = this.myBuildTypeSettings.getOwnParametersCollection();
            if (collection == null) {
                BuildTypeImpl.$$$reportNull$$$0(86);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return collection;
    }

    @NotNull
    public Map<String, String> getOwnParameters() {
        Map map;
        try {
            map = this.myBuildTypeSettings.getOwnParameters();
            if (map == null) {
                BuildTypeImpl.$$$reportNull$$$0(87);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return map;
    }

    @NotNull
    public Collection<Parameter> getBuildParametersCollection() {
        Collection collection;
        try {
            collection = this.myBuildTypeSettings.getBuildParametersCollection();
            if (collection == null) {
                BuildTypeImpl.$$$reportNull$$$0(88);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return collection;
    }

    public boolean isInQueue() {
        return this.myContext.getQueue().isInQueue(this.getInternalId());
    }

    public int getNumberQueued() {
        return this.getQueuedBuilds(null).size();
    }

    public Status getStatus() {
        Status status;
        block8: {
            status = this.getBranch("<default>").getStatus();
            try {
                if (status.isFailed() || !this.showWorseBranchStatus()) break block8;
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildTypeImpl.a(illegalStateException);
            }
            Status status2 = Status.UNKNOWN;
            for (BranchEx branchEx : this.getBranchesForStatus()) {
                Status status3 = branchEx.getStatus();
                if (!status3.above(status2)) continue;
                status2 = status3;
                try {
                    if (!this.isWorseStatus(status2)) continue;
                    return status2;
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildTypeImpl.a(illegalStateException);
                }
            }
            return status2;
        }
        try {
            if (!this.getExcludedDefaultRootInstanceIds().isEmpty()) {
                return Status.UNKNOWN;
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return status;
    }

    @NotNull
    public BuildTypeStatusDescriptor getStatusDescriptor() {
        BuildTypeStatusDescriptor buildTypeStatusDescriptor;
        block17: {
            BuildTypeStatusDescriptor buildTypeStatusDescriptor2;
            block18: {
                if (this.showWorseBranchStatus()) {
                    BuildTypeStatusDescriptor buildTypeStatusDescriptor3;
                    BuildTypeStatusDescriptor buildTypeStatusDescriptor4 = BuildTypeStatusDescriptorImpl.UNKNOWN;
                    Status status = Status.UNKNOWN;
                    for (BranchEx branchEx : this.getBranchesForStatus()) {
                        BuildTypeStatusDescriptor buildTypeStatusDescriptor5;
                        block16: {
                            BuildTypeStatusDescriptor buildTypeStatusDescriptor6 = branchEx.getStatusDescriptor();
                            Status status2 = buildTypeStatusDescriptor6.getStatusDescriptor().getStatus();
                            if (!status2.above(status)) continue;
                            status = status2;
                            buildTypeStatusDescriptor4 = buildTypeStatusDescriptor6;
                            try {
                                try {
                                    if (!this.isWorseStatus(status)) continue;
                                    buildTypeStatusDescriptor5 = buildTypeStatusDescriptor4;
                                    if (buildTypeStatusDescriptor5 != null) break block16;
                                }
                                catch (IllegalStateException illegalStateException) {
                                    throw BuildTypeImpl.a(illegalStateException);
                                }
                                BuildTypeImpl.$$$reportNull$$$0(89);
                            }
                            catch (IllegalStateException illegalStateException) {
                                throw BuildTypeImpl.a(illegalStateException);
                            }
                        }
                        return buildTypeStatusDescriptor5;
                    }
                    try {
                        buildTypeStatusDescriptor3 = buildTypeStatusDescriptor4;
                        if (buildTypeStatusDescriptor3 == null) {
                            BuildTypeImpl.$$$reportNull$$$0(90);
                        }
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildTypeImpl.a(illegalStateException);
                    }
                    return buildTypeStatusDescriptor3;
                }
                try {
                    try {
                        if (this.getExcludedDefaultRootInstanceIds().isEmpty()) break block17;
                        buildTypeStatusDescriptor2 = BuildTypeStatusDescriptorImpl.UNKNOWN;
                        if (buildTypeStatusDescriptor2 != null) break block18;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildTypeImpl.a(illegalStateException);
                    }
                    BuildTypeImpl.$$$reportNull$$$0(91);
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildTypeImpl.a(illegalStateException);
                }
            }
            return buildTypeStatusDescriptor2;
        }
        try {
            buildTypeStatusDescriptor = this.getBranch("<default>").getStatusDescriptor();
            if (buildTypeStatusDescriptor == null) {
                BuildTypeImpl.$$$reportNull$$$0(92);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return buildTypeStatusDescriptor;
    }

    private boolean isWorseStatus(@NotNull Status status) {
        try {
            if (status == null) {
                BuildTypeImpl.$$$reportNull$$$0(93);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return status.isFailed();
    }

    @NotNull
    private List<BranchEx> getBranchesForStatus() {
        List<BranchEx> list;
        try {
            list = this.getBranches(BranchesPolicy.ACTIVE_HISTORY_BRANCHES, false);
            if (list == null) {
                BuildTypeImpl.$$$reportNull$$$0(94);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return list;
    }

    private boolean showWorseBranchStatus() {
        String string = this.getParameterValue("teamcity.buildTypeStatus.showWorseBranchStatus");
        try {
            if (string != null) {
                return Boolean.parseBoolean(string);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return TeamCityProperties.getBoolean((String)"teamcity.buildTypeStatus.showWorseBranchStatus");
    }

    @NotNull
    public List<SBuild> getLatestBuilds() {
        List list;
        try {
            list = this.getBranch("<default>").getLatestBuilds();
            if (list == null) {
                BuildTypeImpl.$$$reportNull$$$0(95);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return list;
    }

    public String toString() {
        return LogUtil.describe((BuildTypeEx)this);
    }

    public boolean addBuildTrigger(@NotNull BuildTriggerDescriptor buildTriggerDescriptor) {
        try {
            if (buildTriggerDescriptor == null) {
                BuildTypeImpl.$$$reportNull$$$0(96);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        this.beforeModification();
        this.markEdited();
        return this.myBuildTypeSettings.addBuildTrigger(buildTriggerDescriptor);
    }

    @NotNull
    public BuildTriggerDescriptor addBuildTrigger(@NotNull String string, @NotNull Map<String, String> map) {
        BuildTriggerDescriptor buildTriggerDescriptor;
        try {
            if (string == null) {
                BuildTypeImpl.$$$reportNull$$$0(97);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            if (map == null) {
                BuildTypeImpl.$$$reportNull$$$0(98);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            this.beforeModification();
            this.markEdited();
            buildTriggerDescriptor = this.myBuildTypeSettings.addBuildTrigger(string, map);
            if (buildTriggerDescriptor == null) {
                BuildTypeImpl.$$$reportNull$$$0(99);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return buildTriggerDescriptor;
    }

    public boolean removeBuildTrigger(@NotNull BuildTriggerDescriptor buildTriggerDescriptor) {
        try {
            if (buildTriggerDescriptor == null) {
                BuildTypeImpl.$$$reportNull$$$0(100);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        this.beforeModification();
        boolean bl = this.myBuildTypeSettings.removeBuildTrigger(buildTriggerDescriptor);
        try {
            if (bl) {
                this.markEdited();
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return bl;
    }

    public boolean updateBuildTrigger(@NotNull String string, @NotNull String string2, @NotNull Map<String, String> map) {
        try {
            if (string == null) {
                BuildTypeImpl.$$$reportNull$$$0(101);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            if (string2 == null) {
                BuildTypeImpl.$$$reportNull$$$0(102);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            if (map == null) {
                BuildTypeImpl.$$$reportNull$$$0(103);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        this.beforeModification();
        boolean bl = this.myBuildTypeSettings.updateBuildTrigger(string, string2, map);
        try {
            if (bl) {
                this.markEdited();
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return bl;
    }

    public boolean updateBuildTrigger(@NotNull BuildTriggerDescriptor buildTriggerDescriptor) {
        try {
            if (buildTriggerDescriptor == null) {
                BuildTypeImpl.$$$reportNull$$$0(104);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        this.beforeModification();
        boolean bl = this.myBuildTypeSettings.updateBuildTrigger(buildTriggerDescriptor);
        try {
            if (bl) {
                this.markEdited();
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return bl;
    }

    @NotNull
    public Collection<BuildTriggerDescriptor> getBuildTriggersCollection() {
        Collection collection;
        try {
            collection = this.myBuildTypeSettings.getBuildTriggersCollection();
            if (collection == null) {
                BuildTypeImpl.$$$reportNull$$$0(105);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return collection;
    }

    public BuildTriggerDescriptor findTriggerById(@NotNull String string) {
        try {
            if (string == null) {
                BuildTypeImpl.$$$reportNull$$$0(106);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return this.myBuildTypeSettings.findTriggerById(string);
    }

    public void attachedToModel() {
        if (this.myAttachedToModel.compareAndSet(false, true)) {
            this.myBuildTypeSettings.attachedToModel();
            for (Runnable runnable : this.myDelayedTasks) {
                runnable.run();
            }
            this.myDelayedTasks.clear();
        }
    }

    public List<SFinishedBuild> getHistory() {
        return this.getHistory(null, false, false);
    }

    public List<SFinishedBuild> getHistoryFull(boolean bl) {
        ArrayList<SFinishedBuild> arrayList = new ArrayList<SFinishedBuild>();
        this.myContext.getHistory().processEntries(this.getInternalId(), null, true, true, bl, sFinishedBuild -> {
            arrayList.add((SFinishedBuild)sFinishedBuild);
            return true;
        });
        return arrayList;
    }

    public List<SFinishedBuild> getHistory(@Nullable User user, boolean bl, boolean bl2) {
        ArrayList<SFinishedBuild> arrayList = new ArrayList<SFinishedBuild>();
        this.myContext.getHistory().processEntries(this.getInternalId(), user, false, bl, bl2, sFinishedBuild -> {
            arrayList.add((SFinishedBuild)sFinishedBuild);
            return true;
        });
        return arrayList;
    }

    public void processHistoryEntries(@NotNull ItemProcessor<SFinishedBuild> itemProcessor) {
        try {
            if (itemProcessor == null) {
                BuildTypeImpl.$$$reportNull$$$0(107);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        this.myContext.getHistory().processEntries(this.getInternalId(), null, false, false, true, itemProcessor);
    }

    @NotNull
    public List<ChangeDescriptor> getDetectedChanges(@NotNull SelectPrevBuildPolicy selectPrevBuildPolicy) {
        List<ChangeDescriptor> list;
        try {
            if (selectPrevBuildPolicy == null) {
                BuildTypeImpl.$$$reportNull$$$0(108);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            list = this.getDetectedChanges(selectPrevBuildPolicy, null);
            if (list == null) {
                BuildTypeImpl.$$$reportNull$$$0(109);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return list;
    }

    @NotNull
    public List<ChangeDescriptor> getDetectedChanges(@NotNull SelectPrevBuildPolicy selectPrevBuildPolicy, @Nullable Boolean bl) {
        List list;
        try {
            if (selectPrevBuildPolicy == null) {
                BuildTypeImpl.$$$reportNull$$$0(110);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        BaseDummyBuildPromotion baseDummyBuildPromotion = this.getDummyBuildPromotion();
        try {
            list = baseDummyBuildPromotion.getDetectedChanges(selectPrevBuildPolicy, bl);
            if (list == null) {
                BuildTypeImpl.$$$reportNull$$$0(111);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return list;
    }

    @Nullable
    public SBuild getLastChangesStartedBuild() {
        return this.getBranch("<default>").getLastChangesStarted();
    }

    @Nullable
    public SFinishedBuild getLastChangesFinished() {
        return this.getBranch("<default>").getLastChangesFinished();
    }

    @Nullable
    public SFinishedBuild getLastChangesSuccessfullyFinished() {
        return this.getBranch("<default>").getLastChangesSuccessfullyFinished();
    }

    @NotNull
    public List<Dependency> getOwnDependencies() {
        List list;
        try {
            list = this.myBuildTypeSettings.getOwnDependencies();
            if (list == null) {
                BuildTypeImpl.$$$reportNull$$$0(112);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return list;
    }

    @NotNull
    public CustomDataStorage getCustomDataStorage(@NotNull String string) {
        CustomDataStorage customDataStorage;
        try {
            if (string == null) {
                BuildTypeImpl.$$$reportNull$$$0(113);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            customDataStorage = this.myContext.getCustomDataStorageManager().getBuildTypeStorage(this.getBuildTypeId(), string);
            if (customDataStorage == null) {
                BuildTypeImpl.$$$reportNull$$$0(114);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return customDataStorage;
    }

    protected void checkObsolete() {
        block4: {
            BuildTypeImpl buildTypeImpl = (BuildTypeImpl)((Object)this.myContext.getProjectManager().getProjectDataModel().getBuildTypes().find((Object)this.getId()));
            try {
                try {
                    if (buildTypeImpl == null || buildTypeImpl == this) break block4;
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildTypeImpl.a(illegalStateException);
                }
                throw new ObsoleteEntityException("Obsolete build configuration " + this.getExternalId());
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildTypeImpl.a(illegalStateException);
            }
        }
    }

    protected void warnObsolete() {
        try {
            this.checkObsolete();
        }
        catch (ObsoleteEntityException obsoleteEntityException) {
            LOG.warnAndDebugDetails("Persisting obsolete instance of build configuration while there is a more recent instance of the same build configuration in memory, build type ID: " + this.getExternalId(), (Throwable)new IllegalStateException("Obsolete buildType"));
        }
    }

    protected void persistInternal(@NotNull ConfigAction configAction) throws PersistFailedException {
        try {
            if (configAction == null) {
                BuildTypeImpl.$$$reportNull$$$0(115);
            }
        }
        catch (PersistFailedException persistFailedException) {
            throw BuildTypeImpl.a(persistFailedException);
        }
        this.warnObsolete();
        this.makeSureUsedEntitiesPersisted(configAction);
        this.doPersist(configAction);
    }

    protected void persistInternal(@NotNull BackgroundPersistTask backgroundPersistTask) throws PersistFailedException {
        try {
            if (backgroundPersistTask == null) {
                BuildTypeImpl.$$$reportNull$$$0(116);
            }
        }
        catch (PersistFailedException persistFailedException) {
            throw BuildTypeImpl.a(persistFailedException);
        }
        this.warnObsolete();
        this.doPersist(backgroundPersistTask);
        this.makeSureUsedEntitiesPersisted(backgroundPersistTask);
    }

    private void makeSureUsedEntitiesPersisted(@NotNull ConfigAction configAction) {
        try {
            if (configAction == null) {
                BuildTypeImpl.$$$reportNull$$$0(117);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        for (SVcsRoot object2 : this.getVcsRoots()) {
            try {
                if (object2.getConfigurationFile().isFile()) {
                    continue;
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildTypeImpl.a(illegalStateException);
            }
            object2.persist(configAction);
        }
        List<BuildTypeTemplateEx> list = this.getTemplates();
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            BuildTypeTemplate buildTypeTemplate = (BuildTypeTemplate)iterator.next();
            try {
                if (buildTypeTemplate.getConfigurationFile().isFile()) continue;
                buildTypeTemplate.persist(configAction);
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildTypeImpl.a(illegalStateException);
            }
        }
    }

    private void makeSureUsedEntitiesPersisted(@NotNull BackgroundPersistTask backgroundPersistTask) {
        try {
            if (backgroundPersistTask == null) {
                BuildTypeImpl.$$$reportNull$$$0(118);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        for (SVcsRoot sVcsRoot : this.getVcsRoots()) {
            try {
                try {
                    try {
                        if (sVcsRoot.getConfigurationFile().isFile() || !(sVcsRoot instanceof SVcsRootImpl)) continue;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildTypeImpl.a(illegalStateException);
                    }
                    if (backgroundPersistTask.affects((SPersistentEntity)sVcsRoot)) continue;
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildTypeImpl.a(illegalStateException);
                }
                ((SVcsRootImpl)sVcsRoot).schedulePersisting(backgroundPersistTask);
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildTypeImpl.a(illegalStateException);
            }
        }
        for (BuildTypeTemplate buildTypeTemplate : this.getTemplates()) {
            try {
                try {
                    try {
                        if (buildTypeTemplate.getConfigurationFile().isFile() || !(buildTypeTemplate instanceof BaseBuildTypeIdentity)) continue;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildTypeImpl.a(illegalStateException);
                    }
                    if (backgroundPersistTask.affects((SPersistentEntity)buildTypeTemplate)) continue;
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildTypeImpl.a(illegalStateException);
                }
                ((BaseBuildTypeIdentity)buildTypeTemplate).schedulePersisting(backgroundPersistTask);
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildTypeImpl.a(illegalStateException);
            }
        }
    }

    @NotNull
    protected FileHistory getConfigHistory() throws IOException {
        FileHistory fileHistory;
        try {
            fileHistory = this.myContext.getConfigHistoryManager().getBuildTypeConfigHistory((SBuildType)this);
            if (fileHistory == null) {
                BuildTypeImpl.$$$reportNull$$$0(119);
            }
        }
        catch (IOException iOException) {
            throw BuildTypeImpl.a(iOException);
        }
        return fileHistory;
    }

    protected void logChangedConfigToAudit(@NotNull ConfigAction configAction, @Nullable String string, int n) {
        try {
            if (configAction == null) {
                BuildTypeImpl.$$$reportNull$$$0(120);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        ConfigModification configModification = ConfigModificationFactory.createForBuildType((SBuildType)this, (int)n);
        this.auditLog().logUserAction(configAction, ActionType.BUILD_TYPE_EDIT_SETTINGS, string, null, new Object[]{configModification});
    }

    public void persistAuxiliaryData() {
        this.processArtifactsLocationChange();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void processArtifactsLocationChange() {
        ArrayList<File> arrayList;
        List<File> list = this.myOldArtifactDirectories;
        synchronized (list) {
            arrayList = new ArrayList<File>(this.myOldArtifactDirectories);
            this.myOldArtifactDirectories.clear();
        }
        try {
            if (!arrayList.isEmpty()) {
                this.myContext.getBuildArtifactsLocationChecker().buildTypeArtifactsLocationChanged((SBuildType)this, arrayList);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
    }

    @NotNull
    private List<BuildTriggerDescriptor> resolveBuildTriggersParameters(@NotNull ParametersProvider parametersProvider, @NotNull Collection<BuildTriggerDescriptor> collection) {
        try {
            if (parametersProvider == null) {
                BuildTypeImpl.$$$reportNull$$$0(121);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            if (collection == null) {
                BuildTypeImpl.$$$reportNull$$$0(122);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        ValueResolver valueResolver = this.getValueResolver(parametersProvider);
        return this.resolveBuildTriggersParameters(valueResolver, collection);
    }

    @NotNull
    private List<BuildTriggerDescriptor> resolveBuildTriggersParameters(@NotNull ValueResolver valueResolver, @NotNull Collection<BuildTriggerDescriptor> collection) {
        List list;
        try {
            if (valueResolver == null) {
                BuildTypeImpl.$$$reportNull$$$0(123);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            if (collection == null) {
                BuildTypeImpl.$$$reportNull$$$0(124);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            list = (List)this.myContext.getSecurityContext().runAsSystemUnchecked(() -> {
                ArrayList<BuildTriggerDescriptor> arrayList = new ArrayList<BuildTriggerDescriptor>();
                for (BuildTriggerDescriptor buildTriggerDescriptor : collection) {
                    arrayList.add(this.myContext.getTriggerDescriptorFactory().createTriggerDescriptor(buildTriggerDescriptor.getId(), buildTriggerDescriptor.getTriggerName(), valueResolver.resolve(buildTriggerDescriptor.getParameters()), buildTriggerDescriptor.getBuildCustomizationSettings()));
                }
                return arrayList;
            });
            if (list == null) {
                BuildTypeImpl.$$$reportNull$$$0(125);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return list;
    }

    private ValueResolver getValueResolver(ParametersProvider parametersProvider) {
        return this.getDummyBuild().getValueResolver(parametersProvider);
    }

    @NotNull
    public ResolvedSettings getResolvedSettings() {
        return new /* Unavailable Anonymous Inner Class!! */;
    }

    @NotNull
    public Collection<BuildTriggerDescriptor> getBuildTriggerDescriptors(@NotNull String string, boolean bl) {
        List list;
        try {
            if (string == null) {
                BuildTypeImpl.$$$reportNull$$$0(126);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        TriggerWithNameFilter triggerWithNameFilter = new TriggerWithNameFilter(string);
        if (bl) {
            triggerWithNameFilter = FilterUtil.and((Filter[])new Filter[]{triggerWithNameFilter, new IsEnabledFilter((BuildTypeEx)this)});
        }
        try {
            list = CollectionsUtil.filterCollection(this.getBuildTriggersCollection(), (Filter)triggerWithNameFilter);
            if (list == null) {
                BuildTypeImpl.$$$reportNull$$$0(127);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return list;
    }

    @NotNull
    public Collection<BuildTriggerDescriptor> getResolvedBuildTriggerDescriptors(boolean bl) {
        Collection<BuildTriggerDescriptor> collection;
        Collection<BuildTriggerDescriptor> collection2;
        Collection<BuildTriggerDescriptor> collection3 = collection2 = this.getBuildTriggersCollection();
        if (this.requireResolving(collection2)) {
            collection3 = this.resolveBuildTriggersParameters(this.getValueResolver(), collection2);
        }
        try {
            if (bl) {
                this.filterOutDisabledDescriptors(collection3);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            collection = collection3;
            if (collection == null) {
                BuildTypeImpl.$$$reportNull$$$0(128);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return collection;
    }

    @NotNull
    public Collection<BuildTriggerDescriptor> getResolvedBuildTriggerDescriptors(@NotNull String string, boolean bl) {
        Collection collection;
        TriggerWithNameFilter triggerWithNameFilter;
        block19: {
            List<BuildTriggerDescriptor> list;
            block20: {
                try {
                    if (string == null) {
                        BuildTypeImpl.$$$reportNull$$$0(129);
                    }
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildTypeImpl.a(illegalStateException);
                }
                Collection<BuildTriggerDescriptor> collection2 = this.getBuildTriggersCollection();
                triggerWithNameFilter = new TriggerWithNameFilter(string);
                boolean bl2 = false;
                for (BuildTriggerDescriptor buildTriggerDescriptor : collection2) {
                    block18: {
                        try {
                            try {
                                try {
                                    if (!triggerWithNameFilter.accept((Object)buildTriggerDescriptor)) continue;
                                    if (!bl) break block18;
                                }
                                catch (IllegalStateException illegalStateException) {
                                    throw BuildTypeImpl.a(illegalStateException);
                                }
                                if (!this.isEnabled(buildTriggerDescriptor.getId())) {
                                    continue;
                                }
                            }
                            catch (IllegalStateException illegalStateException) {
                                throw BuildTypeImpl.a(illegalStateException);
                            }
                        }
                        catch (IllegalStateException illegalStateException) {
                            throw BuildTypeImpl.a(illegalStateException);
                        }
                    }
                    bl2 = true;
                    break;
                }
                try {
                    try {
                        if (bl2) break block19;
                        list = Collections.emptyList();
                        if (list != null) break block20;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildTypeImpl.a(illegalStateException);
                    }
                    BuildTypeImpl.$$$reportNull$$$0(130);
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildTypeImpl.a(illegalStateException);
                }
            }
            return list;
        }
        try {
            collection = FilterUtil.filterCollection(this.getResolvedBuildTriggerDescriptors(bl), (Filter)triggerWithNameFilter);
            if (collection == null) {
                BuildTypeImpl.$$$reportNull$$$0(131);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return collection;
    }

    @NotNull
    public List<ChangeDescriptor> getPendingChangesInAllBranches(@Nullable Boolean bl) {
        List<ChangeDescriptor> list;
        try {
            list = this.getPendingChangesInAllBranches(bl, VcsModificationProcessor.ACCEPT_ALL);
            if (list == null) {
                BuildTypeImpl.$$$reportNull$$$0(132);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return list;
    }

    @NotNull
    public List<ChangeDescriptor> getPendingChangesInAllBranches(@Nullable Boolean bl, @NotNull VcsModificationProcessor vcsModificationProcessor) {
        List list;
        try {
            if (vcsModificationProcessor == null) {
                BuildTypeImpl.$$$reportNull$$$0(133);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            list = this.myBranches.getPendingChangesInAllBranches(bl, vcsModificationProcessor);
            if (list == null) {
                BuildTypeImpl.$$$reportNull$$$0(134);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return list;
    }

    @NotNull
    public DummyBuild getDummyBuild() {
        DummyBuild dummyBuild;
        try {
            dummyBuild = this.getBranch("<default>").getDummyBuild();
            if (dummyBuild == null) {
                BuildTypeImpl.$$$reportNull$$$0(135);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return dummyBuild;
    }

    @NotNull
    public BaseDummyBuildPromotion getDummyBuildPromotion() {
        DummyBuildPromotion dummyBuildPromotion;
        block9: {
            InMemoryBuildPromotion inMemoryBuildPromotion;
            block10: {
                try {
                    try {
                        try {
                            if (!this.isPipelineHead() || !TeamCityProperties.getBooleanOrTrue((String)"teamcity.pipelines.replaceDummyPromotionsWithUnprocessedInMemory")) break block9;
                        }
                        catch (IllegalStateException illegalStateException) {
                            throw BuildTypeImpl.a(illegalStateException);
                        }
                        inMemoryBuildPromotion = this.getInMemoryBuildPromotion();
                        if (inMemoryBuildPromotion != null) break block10;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildTypeImpl.a(illegalStateException);
                    }
                    BuildTypeImpl.$$$reportNull$$$0(136);
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildTypeImpl.a(illegalStateException);
                }
            }
            return inMemoryBuildPromotion;
        }
        try {
            dummyBuildPromotion = this.myBranches.getDummyPromotionFromCache("<default>");
            if (dummyBuildPromotion == null) {
                BuildTypeImpl.$$$reportNull$$$0(137);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return dummyBuildPromotion;
    }

    @Nullable
    public BaseDummyBuildPromotion getDummyBuildPromotion(boolean bl) {
        block4: {
            try {
                try {
                    if (!this.isPipelineHead() || !TeamCityProperties.getBooleanOrTrue((String)"teamcity.pipelines.replaceDummyPromotionsWithUnprocessedInMemory")) break block4;
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildTypeImpl.a(illegalStateException);
                }
                return this.getInMemoryBuildPromotion();
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildTypeImpl.a(illegalStateException);
            }
        }
        return this.myBranches.getDummyPromotionFromCache("<default>", bl);
    }

    @NotNull
    public InMemoryBuildPromotion getInMemoryBuildPromotion() {
        InMemoryBuildPromotion inMemoryBuildPromotion;
        try {
            inMemoryBuildPromotion = this.myContext.getBuildPromotionManager().createInMemoryBuildPromotion("<default>", (BuildTypeEx)this);
            if (inMemoryBuildPromotion == null) {
                BuildTypeImpl.$$$reportNull$$$0(138);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return inMemoryBuildPromotion;
    }

    @NotNull
    public InMemoryBuildPromotion getInMemoryBuildPromotion(@NotNull String string) {
        InMemoryBuildPromotion inMemoryBuildPromotion;
        try {
            if (string == null) {
                BuildTypeImpl.$$$reportNull$$$0(139);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            if (!this.isPipelineHead()) {
                throw new IllegalStateException("Cannot create in-memory build promotion with a modified pipeline YAML for non-pipeline head build type");
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            inMemoryBuildPromotion = this.myContext.getBuildPromotionManager().createInMemoryBuildPromotion("<default>", (BuildTypeEx)this, string);
            if (inMemoryBuildPromotion == null) {
                BuildTypeImpl.$$$reportNull$$$0(140);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return inMemoryBuildPromotion;
    }

    public void resetPendingChanges() {
        this.resetDummyBuilds();
    }

    private void filterOutDisabledDescriptors(@NotNull Collection<? extends ParametersDescriptor> collection) {
        try {
            if (collection == null) {
                BuildTypeImpl.$$$reportNull$$$0(141);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        FilterUtil.filterCollection(collection, (Filter)new IsEnabledFilter((BuildTypeEx)this));
    }

    private boolean requireResolving(@NotNull Collection<? extends ParametersDescriptor> collection) {
        try {
            if (collection == null) {
                BuildTypeImpl.$$$reportNull$$$0(142);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        for (ParametersDescriptor parametersDescriptor : collection) {
            try {
                if (!BuildTypeImpl.needsResolving(parametersDescriptor.getParameters().values())) continue;
                return true;
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildTypeImpl.a(illegalStateException);
            }
        }
        return false;
    }

    private boolean requireRunnersResolving(@NotNull Collection<? extends SBuildRunnerDescriptor> collection) {
        try {
            if (collection == null) {
                BuildTypeImpl.$$$reportNull$$$0(143);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        for (SBuildRunnerDescriptor sBuildRunnerDescriptor : collection) {
            try {
                if (BuildTypeImpl.needsResolving(sBuildRunnerDescriptor.getParameters().values())) {
                    return true;
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildTypeImpl.a(illegalStateException);
            }
            try {
                if (!BuildTypeImpl.needsResolving(sBuildRunnerDescriptor.getOwnBuildParameters().values())) continue;
                return true;
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildTypeImpl.a(illegalStateException);
            }
        }
        return false;
    }

    @NotNull
    public ValueResolver getValueResolver() {
        return this.createValueResolver();
    }

    @NotNull
    private LazyValueResolver createValueResolver() {
        return new LazyValueResolver(() -> this.getDummyBuild().getValueResolver());
    }

    public void moveToProject(@NotNull SProject sProject, boolean bl) throws InvalidVcsRootScopeException {
        try {
            if (sProject == null) {
                BuildTypeImpl.$$$reportNull$$$0(144);
            }
        }
        catch (InvalidVcsRootScopeException invalidVcsRootScopeException) {
            throw BuildTypeImpl.a(invalidVcsRootScopeException);
        }
        this.moveToProject(sProject);
    }

    public void moveToProject(@NotNull SProject sProject) throws InvalidVcsRootScopeException {
        try {
            if (sProject == null) {
                BuildTypeImpl.$$$reportNull$$$0(145);
            }
        }
        catch (InvalidVcsRootScopeException invalidVcsRootScopeException) {
            throw BuildTypeImpl.a(invalidVcsRootScopeException);
        }
        ConfigActionEx configActionEx = this.myContext.getConfigActionFactory().createAction((SProject)this.getProject(), "'" + this.getName() + "' build configuration was moved to '" + sProject.getFullName() + "' project");
        this.moveToProject((ConfigAction)configActionEx, sProject);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     */
    public void moveToProject(@NotNull ConfigAction configAction, @NotNull SProject sProject) {
        Object object;
        Object object2;
        Object object32222;
        block41: {
            try {
                if (configAction == null) {
                    BuildTypeImpl.$$$reportNull$$$0(146);
                }
            }
            catch (Throwable throwable) {
                throw BuildTypeImpl.a(throwable);
            }
            try {
                if (sProject == null) {
                    BuildTypeImpl.$$$reportNull$$$0(147);
                }
            }
            catch (Throwable throwable) {
                throw BuildTypeImpl.a(throwable);
            }
            try {
                if (sProject instanceof ProjectEx) {
                    ((ProjectEx)sProject).checkCyclicDependencies((SBuildType)this);
                }
            }
            catch (Throwable throwable) {
                throw BuildTypeImpl.a(throwable);
            }
            try {
                if (sProject.isRootProject()) {
                    throw new IllegalArgumentException("Attempted to create a build configuration in Root project. Root project cannot contain build configurations.");
                }
            }
            catch (Throwable throwable) {
                throw BuildTypeImpl.a(throwable);
            }
            try {
                if (((ProjectEx)sProject).isPipeline()) {
                    throw new IllegalArgumentException("Attempted to create a build configuration in pipeline project. Pipeline project cannot contain build configurations.");
                }
            }
            catch (Throwable throwable) {
                throw BuildTypeImpl.a(throwable);
            }
            if (!OrganizationProjects.isOrganizationProjectsEnabledGlobally()) break block41;
            try {
                block43: {
                    if (OrganizationProjects.isTheSameOrganization((SProject)this.getProject(), (SProject)sProject)) break block41;
                    break block43;
                    catch (Throwable throwable) {
                        throw BuildTypeImpl.a(throwable);
                    }
                }
                throw new OrganizationProjectAccessViolationException("Cannot move the build configuration to another organization");
            }
            catch (Throwable throwable) {
                throw BuildTypeImpl.a(throwable);
            }
        }
        try {
            if (!this.myBuildTypeSettings.vcsRootsAccessibleInProject(sProject)) {
                throw new InvalidVcsRootScopeException("Build configuration cannot be moved because it uses some VCS roots that are not accessible in the target project: " + sProject.getFullName());
            }
        }
        catch (Throwable throwable) {
            throw BuildTypeImpl.a(throwable);
        }
        ArrayList<String> arrayList = new ArrayList<String>();
        for (Object object32222 : this.getOwnTemplates()) {
            if (TeamCityProperties.getBoolean((String)"teamcity.template.ignoreDeps")) continue;
            object2 = object32222.getProject();
            if (sProject.belongsTo((SProject)object2)) continue;
            try {
                block44: {
                    if (!object2.belongsTo((SProject)this.myProject)) continue;
                    break block44;
                    catch (Throwable throwable) {
                        throw BuildTypeImpl.a(throwable);
                    }
                }
                arrayList.add(object32222.getFullName());
            }
            catch (Throwable throwable) {
                throw BuildTypeImpl.a(throwable);
            }
        }
        if (!arrayList.isEmpty()) {
            String string;
            Object[] objectArray;
            String string2;
            object = String.join((CharSequence)", ", arrayList);
            try {
                string2 = "Build configuration %s cannot be moved because it is inherited from template%s %s that will become unavailable in the target project %s";
                Object[] objectArray2 = new Object[4];
                objectArray2[0] = this.getFullName();
                Object[] objectArray3 = objectArray2;
                objectArray = objectArray2;
                int n = 1;
                string = arrayList.size() < 2 ? "" : "s";
            }
            catch (Throwable throwable) {
                throw BuildTypeImpl.a(throwable);
            }
            objectArray3[n] = string;
            objectArray[2] = object;
            objectArray[3] = sProject.getFullName();
            object32222 = String.format(string2, objectArray);
            throw new TemplateBecomesInaccessibleException((String)object32222);
        }
        ReadOnlyUtil.checkModificationAllowed((SPersistentEntity)sProject);
        this.beforeModification();
        this.markEdited();
        object = this.myName;
        object32222 = this.myProject;
        object2 = this.getArtifactDirectories();
        ClashingCleanupRules clashingCleanupRules = this.myOldArtifactDirectories;
        synchronized (clashingCleanupRules) {
            this.myOldArtifactDirectories.addAll((Collection<File>)object2);
        }
        clashingCleanupRules = this.myContext.getKeepRulesManager().computeClashingCleanupRulesAfterMove((BuildTypeEx)this, (ProjectEx)sProject);
        try {
            this.moveConfigFilesAndUpdateModel((ProjectEx)sProject, configAction);
        }
        catch (Throwable throwable) {
            String string = "Failed to move build configuration " + LogUtil.describe((BuildTypeEx)this) + " to project " + LogUtil.describe((Loggable)sProject);
            Loggers.SERVER.warnAndDebugDetails(string, throwable);
            throw new PersistFailedException(string, throwable);
        }
        finally {
            this.resetDummyBuilds();
        }
        try {
            if (this.adjustCleanupRulesOnMove(clashingCleanupRules, (ProjectEx)sProject)) {
                this.schedulePersisting(configAction);
            }
        }
        catch (Throwable throwable) {
            throw BuildTypeImpl.a(throwable);
        }
        this.myProject.getProjectDataModel().resortBuildTypes();
        String string = StringUtil.doubleQuote((String)(object32222.getFullName() + " / " + (String)object));
        this.auditLog().logUserAction(configAction, ActionType.BUILD_TYPE_MOVE, null, null, new Object[]{string});
        ((BuildServerListener)this.myContext.getEventDispatcher().getMulticaster()).buildTypeMoved((SBuildType)this, (SProject)object32222);
        this.processArtifactsLocationChange();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void moveConfigFilesAndUpdateModel(@NotNull ProjectEx projectEx, @NotNull ConfigAction configAction) throws IOException {
        try {
            if (projectEx == null) {
                BuildTypeImpl.$$$reportNull$$$0(148);
            }
        }
        catch (Throwable throwable) {
            throw BuildTypeImpl.a(throwable);
        }
        try {
            if (configAction == null) {
                BuildTypeImpl.$$$reportNull$$$0(149);
            }
        }
        catch (Throwable throwable) {
            throw BuildTypeImpl.a(throwable);
        }
        Object object = this.myExtIdChangeLock;
        synchronized (object) {
            BackgroundPersistTask backgroundPersistTask = this.myContext.getProjectPersistingHandler().schedulePersistTask(configAction);
            ProjectEx projectEx2 = this.myProject;
            String string2 = this.myName;
            String string3 = NameGenerator.generateName((String)this.myName, (String)"", string -> {
                boolean bl;
                try {
                    bl = projectEx.findBuildTypeByName(string) != null;
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildTypeImpl.a(illegalStateException);
                }
                return bl;
            });
            try {
                this.copyInheritedParametersSpec();
                this.updateModelAndFillTask(projectEx, projectEx2, string3, backgroundPersistTask);
            }
            catch (Throwable throwable) {
                backgroundPersistTask.cancel();
                this.updateModelAndFillTask(projectEx2, projectEx, string2, null);
                throw throwable;
            }
            finally {
                backgroundPersistTask.setComplete();
            }
        }
    }

    private boolean adjustCleanupRulesOnMove(@NotNull ClashingCleanupRules clashingCleanupRules, @NotNull ProjectEx projectEx) {
        Collection collection;
        Collection collection2;
        try {
            if (clashingCleanupRules == null) {
                BuildTypeImpl.$$$reportNull$$$0(150);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            if (projectEx == null) {
                BuildTypeImpl.$$$reportNull$$$0(151);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        boolean bl = false;
        Collection collection3 = clashingCleanupRules.getJunkDisableRuleIds();
        if (!collection3.isEmpty()) {
            collection3.forEach(this::removeBuildFeature);
            bl = true;
            try {
                if (Loggers.SERVER.isDebugEnabled()) {
                    Loggers.SERVER.debug(String.format("Cleanup rules change was made on moving build configuration %s to project %s: there were %s that have no parental rule to disable. The disable rules were removed. Affected rule ids: %s", LogUtil.describe((BuildTypeEx)this), LogUtil.describe((Loggable)projectEx), StringUtil.withPlural((long)collection3.size(), (String)"disabling only cleanup rule"), StringUtil.join((String)", ", (Iterable)collection3)));
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildTypeImpl.a(illegalStateException);
            }
        }
        if (!(collection2 = clashingCleanupRules.getClashingDisableRuleIds()).isEmpty()) {
            collection2.forEach(this::removeBuildFeature);
            bl = true;
            Loggers.SERVER.info(String.format("Cleanup rules change was made on moving build configuration %s to project %s: there were %s conflicting by id with target project cleanup rules. Disabled flag for these rules was removed and the rules became enabled to prevent unexpected data deletion. Affected rule ids: %s", LogUtil.describe((BuildTypeEx)this), LogUtil.describe((Loggable)projectEx), StringUtil.withPlural((long)collection2.size(), (String)"disabling cleanup rule"), StringUtil.join((String)", ", (Iterable)collection2)));
        }
        if (!(collection = clashingCleanupRules.getClashingOverridenRuleIds()).isEmpty()) {
            for (String string : collection) {
                SBuildFeatureDescriptor sBuildFeatureDescriptor = this.findBuildFeatureById(string);
                try {
                    if (sBuildFeatureDescriptor == null || this.removeBuildFeature(string) == null) continue;
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildTypeImpl.a(illegalStateException);
                }
                this.addBuildFeature(sBuildFeatureDescriptor.getType(), sBuildFeatureDescriptor.getParameters());
                bl = true;
            }
            Loggers.SERVER.info(String.format("Cleanup rules ids change was made on moving build configuration %s to project %s: there were %s conflicting by id with target project cleanup rules. New ids were assigned to these rules. Affected source rule ids: %s", LogUtil.describe((BuildTypeEx)this), LogUtil.describe((Loggable)projectEx), StringUtil.withPlural((long)collection.size(), (String)"cleanup rule"), StringUtil.join((String)", ", (Iterable)collection)));
        }
        return bl;
    }

    private void updateProjectInBuildSteps() {
        ((BuildTypeSettingsImpl)this.myBuildTypeSettings).recreateBuildRunners();
    }

    private void updateModelAndFillTask(@NotNull ProjectEx projectEx, @NotNull ProjectEx projectEx2, @NotNull String string, @Nullable BackgroundPersistTask backgroundPersistTask) throws IOException {
        block18: {
            try {
                if (projectEx == null) {
                    BuildTypeImpl.$$$reportNull$$$0(152);
                }
            }
            catch (IOException iOException) {
                throw BuildTypeImpl.a(iOException);
            }
            try {
                if (projectEx2 == null) {
                    BuildTypeImpl.$$$reportNull$$$0(153);
                }
            }
            catch (IOException iOException) {
                throw BuildTypeImpl.a(iOException);
            }
            try {
                if (string == null) {
                    BuildTypeImpl.$$$reportNull$$$0(154);
                }
            }
            catch (IOException iOException) {
                throw BuildTypeImpl.a(iOException);
            }
            try {
                if (backgroundPersistTask != null) {
                    backgroundPersistTask.delete((SPersistentEntity)this);
                }
            }
            catch (IOException iOException) {
                throw BuildTypeImpl.a(iOException);
            }
            try {
                try {
                    try {
                        this.myName = string;
                        this.myProject = projectEx;
                        this.getProject().getProjectDataModel().moveBuildType(this, projectEx2, projectEx);
                        this.myBuildTypeSettings.setProject(this.myProject);
                        this.updateProjectInBuildSteps();
                        if (backgroundPersistTask == null) break block18;
                        this.myContext.getConfigurationPersister().saveEntity((SPersistentEntity)this, backgroundPersistTask);
                        if (projectEx.getCredentialsStorageType() == null) break block18;
                    }
                    catch (IOException iOException) {
                        throw BuildTypeImpl.a(iOException);
                    }
                    if (!TeamCityProperties.getBooleanOrTrue((String)"teamcity.buildType.persistProjectOnMove.enabled")) break block18;
                }
                catch (IOException iOException) {
                    throw BuildTypeImpl.a(iOException);
                }
                projectEx.schedulePersisting(backgroundPersistTask.getCause());
            }
            catch (IOException iOException) {
                throw BuildTypeImpl.a(iOException);
            }
        }
    }

    private void copyInheritedParametersSpec() {
        Collection<Parameter> collection = this.getOwnParametersCollection();
        for (Parameter parameter : collection) {
            this.addParameter(parameter);
        }
    }

    @NotNull
    public ParametersProvider getParametersProvider() {
        ParametersProvider parametersProvider;
        try {
            parametersProvider = this.myBuildTypeSettings.createParametersProvider((SBuild)this.getDummyBuild());
            if (parametersProvider == null) {
                BuildTypeImpl.$$$reportNull$$$0(155);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return parametersProvider;
    }

    @Nullable
    public Build getBuildByBuildNumber(@NotNull String string) {
        try {
            if (string == null) {
                BuildTypeImpl.$$$reportNull$$$0(156);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return this.myContext.getBuildsManager().findBuildInstanceByBuildNumber(this.getInternalId(), string);
    }

    public String getBuildParameter(String string) {
        return this.myBuildTypeSettings.getBuildParameter(string);
    }

    public List<AgentCompatibility> getAgentCompatibilities() {
        try {
            if (this.isAgentLessBuildType()) {
                return Collections.emptyList();
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        List list = this.myContext.getBuildAgentManager().getAllAgents();
        ArrayList<AgentCompatibility> arrayList = new ArrayList<AgentCompatibility>(list.size());
        for (BuildAgentEx buildAgentEx : list) {
            arrayList.add(this.getAgentCompatibility((SBuildAgent)buildAgentEx));
        }
        return arrayList;
    }

    public List<BuildAgentEx> getCanRunAgents() {
        try {
            if (this.isAgentLessBuildType()) {
                return Collections.emptyList();
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        ArrayList<BuildAgentEx> arrayList = new ArrayList<BuildAgentEx>();
        for (BuildAgentEx buildAgentEx : this.myContext.getBuildAgentManager().getAllAgents()) {
            try {
                if (!buildAgentEx.getAgentType().getPolicy().isBuildTypeAllowed(this.getBuildTypeId())) continue;
                arrayList.add(buildAgentEx);
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildTypeImpl.a(illegalStateException);
            }
        }
        Collections.sort(arrayList);
        return arrayList;
    }

    public List<BuildAgentEx> getCanRunAndCompatibleAgents(boolean bl) {
        try {
            if (this.isAgentLessBuildType()) {
                return Collections.emptyList();
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        List list = this.myContext.getBuildAgentManager().getRegisteredAgents().stream().filter(buildAgentEx -> {
            boolean bl2;
            block5: {
                block4: {
                    try {
                        try {
                            if (!bl && !buildAgentEx.isEnabled()) break block4;
                        }
                        catch (IllegalStateException illegalStateException) {
                            throw BuildTypeImpl.a(illegalStateException);
                        }
                        bl2 = true;
                        break block5;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildTypeImpl.a(illegalStateException);
                    }
                }
                bl2 = false;
            }
            return bl2;
        }).collect(Collectors.toList());
        Collection collection = this.getDummyBuildPromotion().getCanRunOnAgents(list);
        ArrayList<BuildAgentEx> arrayList = new ArrayList<BuildAgentEx>();
        for (SBuildAgent sBuildAgent3 : collection) {
            arrayList.add((BuildAgentEx)sBuildAgent3);
        }
        arrayList.sort((sBuildAgent, sBuildAgent2) -> {
            int n = sBuildAgent.getName().compareToIgnoreCase(sBuildAgent2.getName());
            if (n == 0) {
                n = sBuildAgent.getId() - sBuildAgent2.getId();
            }
            return n;
        });
        return Collections.unmodifiableList(arrayList);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public BuildNumbers getBuildNumbers() {
        try {
            if (this.myBuildNumbers != null) {
                return this.myBuildNumbers;
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        BuildTypeImpl buildTypeImpl = this;
        synchronized (buildTypeImpl) {
            this.myBuildNumbers = new /* Unavailable Anonymous Inner Class!! */;
        }
        return this.myBuildNumbers;
    }

    @NotNull
    public ResponsibilityEntry getResponsibilityInfo() {
        ResponsibilityEntry responsibilityEntry;
        Object object;
        ResponsibilityEntry responsibilityEntry2 = this.myContext.getResponsibilityFacade().findBuildTypeResponsibility((BuildType)this);
        try {
            object = responsibilityEntry2 != null ? responsibilityEntry2 : ResponsibilityEntryFactory.createEntry((BuildType)this);
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            responsibilityEntry = object;
            if (object == null) {
                BuildTypeImpl.$$$reportNull$$$0(157);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return responsibilityEntry;
    }

    public void setResponsible(@NotNull User user, @Nullable String string, @Nullable User user2) {
        try {
            if (user == null) {
                BuildTypeImpl.$$$reportNull$$$0(158);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        BuildTypeResponsibilityEntry buildTypeResponsibilityEntry = ResponsibilityEntryFactory.createEntry((BuildType)this, (ResponsibilityEntry.State)ResponsibilityEntry.State.TAKEN, (User)user, (User)user2, (Date)new Date(), (String)StringUtil.notNullize((String)string), (ResponsibilityEntry.RemoveMethod)ResponsibilityEntry.RemoveMethod.WHEN_FIXED);
        this.updateResponsibility((ResponsibilityEntry)buildTypeResponsibilityEntry, true);
    }

    public void removeResponsible(boolean bl, @Nullable User user, @Nullable String string, @Nullable User user2) {
        boolean bl2;
        ResponsibilityEntry responsibilityEntry;
        BuildTypeImpl buildTypeImpl;
        ResponsibilityEntry responsibilityEntry2;
        block10: {
            try {
                if (user != null || !bl) break block10;
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildTypeImpl.a(illegalStateException);
            }
            responsibilityEntry2 = this.getResponsibilityInfo();
            try {
                User user3 = user = responsibilityEntry2.getState() != ResponsibilityEntry.State.NONE ? responsibilityEntry2.getResponsibleUser() : null;
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildTypeImpl.a(illegalStateException);
            }
        }
        if (user == null) {
            responsibilityEntry2 = null;
        } else {
            ResponsibilityEntry.State state;
            try {
                state = bl ? ResponsibilityEntry.State.FIXED : ResponsibilityEntry.State.GIVEN_UP;
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildTypeImpl.a(illegalStateException);
            }
            ResponsibilityEntry.State state2 = state;
            responsibilityEntry2 = ResponsibilityEntryFactory.createEntry((BuildType)this, (ResponsibilityEntry.State)state2, (User)user, (User)user2, (Date)new Date(), (String)StringUtil.notNullize((String)string), (ResponsibilityEntry.RemoveMethod)ResponsibilityEntry.RemoveMethod.WHEN_FIXED);
        }
        try {
            buildTypeImpl = this;
            responsibilityEntry = responsibilityEntry2;
            bl2 = user2 != null;
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        buildTypeImpl.updateResponsibility(responsibilityEntry, bl2);
    }

    private void updateResponsibility(@Nullable ResponsibilityEntry responsibilityEntry, boolean bl) {
        block10: {
            block9: {
                try {
                    block8: {
                        try {
                            try {
                                try {
                                    if (responsibilityEntry == null) break block8;
                                    if (bl) break block9;
                                }
                                catch (IllegalStateException illegalStateException) {
                                    throw BuildTypeImpl.a(illegalStateException);
                                }
                                if (this.getStatus().isSuccessful()) break block8;
                            }
                            catch (IllegalStateException illegalStateException) {
                                throw BuildTypeImpl.a(illegalStateException);
                            }
                            if (!this.getStatus().isIgnored()) break block9;
                        }
                        catch (IllegalStateException illegalStateException) {
                            throw BuildTypeImpl.a(illegalStateException);
                        }
                    }
                    this.myContext.getResponsibilityFacade().removeBuildTypeResponsibility((BuildType)this);
                    break block10;
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildTypeImpl.a(illegalStateException);
                }
            }
            this.myContext.getResponsibilityFacade().setBuildTypeResponsibility((BuildType)this, responsibilityEntry);
        }
    }

    @NotNull
    public BuildTypeDescriptor.CheckoutType getCheckoutType() {
        BuildTypeDescriptor.CheckoutType checkoutType;
        try {
            checkoutType = this.myBuildTypeSettings.getCheckoutType();
            if (checkoutType == null) {
                BuildTypeImpl.$$$reportNull$$$0(159);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return checkoutType;
    }

    public String getCheckoutDirectory() {
        return this.myBuildTypeSettings.getCheckoutDirectory();
    }

    public void setArtifactPaths(@Nullable String string) {
        try {
            if (!Objects.equals(string, this.getArtifactPaths())) {
                this.beforeModification();
                this.markEdited();
                this.myBuildTypeSettings.setArtifactPaths(string);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
    }

    @Nullable
    public String getArtifactPaths() {
        return this.myBuildTypeSettings.getArtifactPaths();
    }

    @NotNull
    public SBuildType getSourceBuildType() {
        BuildTypeImpl buildTypeImpl;
        try {
            buildTypeImpl = this;
            if (buildTypeImpl == null) {
                BuildTypeImpl.$$$reportNull$$$0(160);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return buildTypeImpl;
    }

    @NotNull
    public ProjectEx getProject() {
        ProjectEx projectEx;
        try {
            projectEx = this.myProject;
            if (projectEx == null) {
                BuildTypeImpl.$$$reportNull$$$0(161);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return projectEx;
    }

    public void buildPatch(RunningBuildEx runningBuildEx, FullCheckoutFlag fullCheckoutFlag, LowLevelPatchBuilder lowLevelPatchBuilder, BuildServerEx buildServerEx, VcsStatusLogger vcsStatusLogger, @NotNull AgentCheckoutDirRevisionInfo agentCheckoutDirRevisionInfo) throws IOException {
        try {
            if (agentCheckoutDirRevisionInfo == null) {
                BuildTypeImpl.$$$reportNull$$$0(162);
            }
        }
        catch (IOException iOException) {
            throw BuildTypeImpl.a(iOException);
        }
        PatchComposer patchComposer = new PatchComposer(runningBuildEx, fullCheckoutFlag, vcsStatusLogger, (VcsServiceFactory)buildServerEx.getVcsManager(), (VcsModificationHistory)buildServerEx.getVcsHistory(), (PatchCache)buildServerEx.getSingletonService(PatchCache.class), this.myContext.getPatchKeepAlive());
        patchComposer.buildPatch(lowLevelPatchBuilder, agentCheckoutDirRevisionInfo);
    }

    @NotNull
    public RunningBuildEx newRunningBuild(@NotNull QueuedBuildEx queuedBuildEx, @Nullable SBuildAgent sBuildAgent, @NotNull Date date) {
        RunningBuildEx runningBuildEx;
        String string;
        Integer n;
        Integer n2;
        String string2;
        Date date2;
        SAgentRestrictor sAgentRestrictor;
        BuildPromotionEx buildPromotionEx;
        BuildFactory buildFactory;
        try {
            if (queuedBuildEx == null) {
                BuildTypeImpl.$$$reportNull$$$0(163);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            if (date == null) {
                BuildTypeImpl.$$$reportNull$$$0(164);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        BuildPromotionEx buildPromotionEx2 = queuedBuildEx.getBuildPromotion();
        try {
            if (this.isStartedInDB(buildPromotionEx2.getId())) {
                throw new IllegalStateException("There is already an existing build for queued build with ID " + buildPromotionEx2.getId());
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            buildFactory = this.myContext.getBuildFactory();
            buildPromotionEx = buildPromotionEx2;
            sAgentRestrictor = queuedBuildEx.getAgentRestrictor();
            date2 = date;
            string2 = queuedBuildEx.getTriggeredBy().getRawTriggeredBy();
            n2 = sBuildAgent == null ? null : Integer.valueOf(sBuildAgent.getId());
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            n = sBuildAgent == null ? null : Integer.valueOf(sBuildAgent.getAgentTypeId());
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            string = sBuildAgent == null ? null : sBuildAgent.getName();
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        RunningBuildEx runningBuildEx2 = buildFactory.createRunningBuild(buildPromotionEx, sAgentRestrictor, date2, string2, n2, n, string);
        try {
            this.myContext.getRunningBuildsManager().addRunningBuild(runningBuildEx2);
            this.myBuildTypeOrderedBuilds.updateBuildForPromotion((BuildPromotion)buildPromotionEx2);
            runningBuildEx = runningBuildEx2;
            if (runningBuildEx == null) {
                BuildTypeImpl.$$$reportNull$$$0(165);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return runningBuildEx;
    }

    private boolean isStartedInDB(long l) {
        return (Boolean)this.myContext.getSQLRunner().withDB(dBFunctions -> {
            boolean bl;
            try {
                bl = 1 == dBFunctions.queryForSingleInt("select 1 from build_state where id = ? and build_id is not null", 0, new Object[]{l});
            }
            catch (DBException dBException) {
                throw BuildTypeImpl.a(dBException);
            }
            return bl;
        });
    }

    @NotNull
    public RunningBuildEx newAgentLessBuild(@NotNull QueuedBuildEx queuedBuildEx, @Nullable SBuildAgent sBuildAgent, @NotNull Date date) {
        RunningBuildEx runningBuildEx;
        try {
            if (queuedBuildEx == null) {
                BuildTypeImpl.$$$reportNull$$$0(166);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            if (date == null) {
                BuildTypeImpl.$$$reportNull$$$0(167);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            if (sBuildAgent != null) {
                queuedBuildEx.getBuildPromotion().setAttribute("teamcity.build.agentLessExecutorName", (Object)sBuildAgent.getName());
                queuedBuildEx.getBuildPromotion().persist();
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            runningBuildEx = this.newRunningBuild(queuedBuildEx, sBuildAgent, date);
            if (runningBuildEx == null) {
                BuildTypeImpl.$$$reportNull$$$0(168);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return runningBuildEx;
    }

    public boolean isPersonal() {
        return false;
    }

    public boolean isCleanBuild() {
        return (Boolean)this.myBuildTypeSettings.getOption(BT_CLEAN_BUILD);
    }

    @NotNull
    public <T> T getOption(@NotNull Option<T> option) {
        Object object;
        try {
            if (option == null) {
                BuildTypeImpl.$$$reportNull$$$0(169);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            object = this.myBuildTypeSettings.getOption(option);
            if (object == null) {
                BuildTypeImpl.$$$reportNull$$$0(170);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return (T)object;
    }

    @NotNull
    public <T> T getOptionDefaultValue(@NotNull Option<T> option) {
        Object object;
        try {
            if (option == null) {
                BuildTypeImpl.$$$reportNull$$$0(171);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            object = this.myBuildTypeSettings.getOptionDefaultValue(option);
            if (object == null) {
                BuildTypeImpl.$$$reportNull$$$0(172);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return (T)object;
    }

    public <T> void setOption(@NotNull Option<T> option, @NotNull T t) {
        block10: {
            try {
                if (option == null) {
                    BuildTypeImpl.$$$reportNull$$$0(173);
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildTypeImpl.a(illegalStateException);
            }
            try {
                if (t == null) {
                    BuildTypeImpl.$$$reportNull$$$0(174);
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildTypeImpl.a(illegalStateException);
            }
            try {
                try {
                    this.beforeModification();
                    this.myProject.getEnforcedSettings().validate(option, t);
                    this.markEdited();
                    this.myBuildTypeSettings.setOption(option, t);
                    if (!option.equals((Object)BT_CHECKOUT_MODE) && !option.equals((Object)BT_CHECKOUT_DIR)) break block10;
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildTypeImpl.a(illegalStateException);
                }
                this.resetDummyBuilds();
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildTypeImpl.a(illegalStateException);
            }
        }
    }

    @NotNull
    public Collection<Option> getOwnOptions() {
        Collection collection;
        try {
            collection = this.myBuildTypeSettings.getOwnOptions();
            if (collection == null) {
                BuildTypeImpl.$$$reportNull$$$0(175);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return collection;
    }

    @NotNull
    public Collection<Option> getOptions() {
        Collection collection;
        try {
            collection = this.myBuildTypeSettings.getOptions();
            if (collection == null) {
                BuildTypeImpl.$$$reportNull$$$0(176);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return collection;
    }

    @NotNull
    public Option[] getChangedOptions() {
        Option[] optionArray;
        try {
            optionArray = this.myBuildTypeSettings.getChangedOptions();
            if (optionArray == null) {
                BuildTypeImpl.$$$reportNull$$$0(177);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return optionArray;
    }

    @NotNull
    public List<Dependency> getDependencies() {
        List list;
        try {
            list = this.myBuildTypeSettings.getDependencies();
            if (list == null) {
                BuildTypeImpl.$$$reportNull$$$0(178);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return list;
    }

    @NotNull
    public List<SBuildType> getDependencyReferences() {
        return new ArrayList<SBuildType>(this.myContext.getProjectManager().findBuildTypes(this.dependedOnMeIds()));
    }

    @NotNull
    public List<SBuildType> getTransitiveDependedOnMe() {
        List list;
        try {
            list = this.myDependencyCollector.getDependentBuildTypes((SBuildType)this);
            if (list == null) {
                BuildTypeImpl.$$$reportNull$$$0(179);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return list;
    }

    public void traverseDependedOnMe(@NotNull DependencyConsumer<BuildTypeEx> dependencyConsumer) {
        try {
            if (dependencyConsumer == null) {
                BuildTypeImpl.$$$reportNull$$$0(180);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        this.myDependencyCollector.traverseDependentBuildTypes((BuildTypeEx)this, dependencyConsumer);
    }

    public void traverseDependencies(@NotNull DependencyConsumer<BuildTypeEx> dependencyConsumer) {
        try {
            if (dependencyConsumer == null) {
                BuildTypeImpl.$$$reportNull$$$0(181);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        this.myDependencyCollector.traverseDependencies((DependencySettings)this, dependencyConsumer);
    }

    public void traverseSelfAndDependencies(@NotNull DependencyConsumer<BuildTypeEx> dependencyConsumer) {
        block7: {
            try {
                if (dependencyConsumer == null) {
                    BuildTypeImpl.$$$reportNull$$$0(182);
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildTypeImpl.a(illegalStateException);
            }
            DependencyConsumer.Result result = dependencyConsumer.consume((Object)this);
            try {
                try {
                    if (result != DependencyConsumer.Result.STOP && result != DependencyConsumer.Result.SKIP) break block7;
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildTypeImpl.a(illegalStateException);
                }
                return;
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildTypeImpl.a(illegalStateException);
            }
        }
        this.traverseDependencies(dependencyConsumer);
    }

    public void traverseSelfAndDependedOnMe(@NotNull DependencyConsumer<BuildTypeEx> dependencyConsumer) {
        block7: {
            try {
                if (dependencyConsumer == null) {
                    BuildTypeImpl.$$$reportNull$$$0(183);
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildTypeImpl.a(illegalStateException);
            }
            DependencyConsumer.Result result = dependencyConsumer.consume((Object)this);
            try {
                try {
                    if (result != DependencyConsumer.Result.STOP && result != DependencyConsumer.Result.SKIP) break block7;
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildTypeImpl.a(illegalStateException);
                }
                return;
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildTypeImpl.a(illegalStateException);
            }
        }
        this.traverseDependedOnMe(dependencyConsumer);
    }

    @NotNull
    private Set<String> dependedOnMeIds() {
        Set<String> set;
        try {
            set = this.myContext.getBuildTypeDependencies().getDependingOn((SBuildType)this).keySet();
            if (set == null) {
                BuildTypeImpl.$$$reportNull$$$0(184);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return set;
    }

    @NotNull
    public Map<String, DependencyOptions> getRawDependedOnMe() {
        Map map;
        try {
            map = this.myContext.getBuildTypeDependencies().getDependingOn((SBuildType)this);
            if (map == null) {
                BuildTypeImpl.$$$reportNull$$$0(185);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return map;
    }

    @NotNull
    public Map<SBuildType, DependencyOptions> getDependedOnMe() {
        LinkedHashMap<SBuildType, DependencyOptions> linkedHashMap;
        Map<String, DependencyOptions> map = this.getRawDependedOnMe();
        LinkedHashMap<SBuildType, DependencyOptions> linkedHashMap2 = new LinkedHashMap<SBuildType, DependencyOptions>(map.size());
        for (SBuildType sBuildType : this.myContext.getProjectManager().findBuildTypes(map.keySet())) {
            linkedHashMap2.put(sBuildType, map.get(sBuildType.getBuildTypeId()));
        }
        try {
            linkedHashMap = linkedHashMap2;
            if (linkedHashMap == null) {
                BuildTypeImpl.$$$reportNull$$$0(186);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return linkedHashMap;
    }

    @NotNull
    public Collection<SBuildType> getChildDependencies() {
        List list;
        try {
            list = this.myDependencyCollector.getDependencies((DependencySettings)this.myBuildTypeSettings);
            if (list == null) {
                BuildTypeImpl.$$$reportNull$$$0(187);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return list;
    }

    public int getNumberOfDependencyReferences() {
        int[] nArray = new int[]{0};
        try {
            this.myContext.getSecurityContext().runAsSystem(() -> {
                nArray[0] = this.getDependencyReferences().size();
            });
        }
        catch (Throwable throwable) {
            ExceptionUtil.rethrowAsRuntimeException((Throwable)throwable);
        }
        return nArray[0];
    }

    public boolean isReadOnly() {
        boolean bl;
        try {
            bl = this.getReadOnlyReason() != null;
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return bl;
    }

    @Nullable
    public ReadOnlyReason getReadOnlyReason() {
        return new ReadOnlyUtil(this.myContext.getSettingsRootManager()).getReadOnlyReason((SPersistentEntity)this);
    }

    public void addOutputParameter(@NotNull Parameter parameter) {
        try {
            if (parameter == null) {
                BuildTypeImpl.$$$reportNull$$$0(188);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        this.beforeModification();
        this.markEdited();
        this.myBuildTypeSettings.addOutputParameter(parameter);
    }

    public void removeOutputParameter(@NotNull String string) throws ReadOnlyEntityException {
        try {
            if (string == null) {
                BuildTypeImpl.$$$reportNull$$$0(189);
            }
        }
        catch (ReadOnlyEntityException readOnlyEntityException) {
            throw BuildTypeImpl.a(readOnlyEntityException);
        }
        this.beforeModification();
        this.markEdited();
        this.myBuildTypeSettings.removeOutputParameter(string);
    }

    @NotNull
    public Collection<Parameter> getOutputParametersCollection() {
        Collection collection;
        try {
            collection = this.myBuildTypeSettings.getOutputParametersCollection();
            if (collection == null) {
                BuildTypeImpl.$$$reportNull$$$0(190);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return collection;
    }

    @NotNull
    public Collection<Parameter> getOwnOutputParametersCollection() {
        Collection collection;
        try {
            collection = this.myBuildTypeSettings.getOwnOutputParametersCollection();
            if (collection == null) {
                BuildTypeImpl.$$$reportNull$$$0(191);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return collection;
    }

    public void addDependency(@NotNull Dependency dependency) {
        block7: {
            try {
                if (dependency == null) {
                    BuildTypeImpl.$$$reportNull$$$0(192);
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildTypeImpl.a(illegalStateException);
            }
            SBuildType sBuildType = (SBuildType)this.myContext.getSecurityContext().runAsSystemUnchecked(() -> dependency.getDependOn());
            try {
                try {
                    if (sBuildType == null || this.myContext.getDependencyChecker().checkDependency(sBuildType.getProject(), (SProject)this.getProject(), DependencyType.SNAPSHOT) != DependencyCheckResult.ORGANIZATIONS_VIOLATION) break block7;
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildTypeImpl.a(illegalStateException);
                }
                throw new OrganizationProjectAccessViolationException(String.format("Cannot add a dependency on %s to the build configuration with id %s", sBuildType.getExternalId(), this.getExternalId()));
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildTypeImpl.a(illegalStateException);
            }
        }
        this.beforeModification();
        this.markEdited();
        this.myBuildTypeSettings.addDependency(dependency);
    }

    public boolean removeDependency(@NotNull Dependency dependency) {
        try {
            if (dependency == null) {
                BuildTypeImpl.$$$reportNull$$$0(193);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        this.beforeModification();
        this.markEdited();
        return this.myBuildTypeSettings.removeDependency(dependency);
    }

    public boolean intersectsWith(@NotNull Dependent dependent) {
        boolean bl;
        try {
            if (dependent == null) {
                BuildTypeImpl.$$$reportNull$$$0(194);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            bl = !Collections.disjoint(this.getChildDependencies(), dependent.getChildDependencies());
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return bl;
    }

    @NotNull
    public List<SArtifactDependency> getArtifactDependencies() {
        List list;
        try {
            list = this.myBuildTypeSettings.getArtifactDependencies();
            if (list == null) {
                BuildTypeImpl.$$$reportNull$$$0(195);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return list;
    }

    public List<SBuildType> getArtifactsReferences() {
        List list = this.myContext.getBuildTypesArtifactDependenciesCache().getDependingOn((SBuildType)this);
        list.sort(this.myContext.getProjectManager().getBuildTypesComparator());
        return list;
    }

    public int getNumberOfArtifactReferences() {
        int[] nArray = new int[]{0};
        try {
            this.myContext.getSecurityContext().runAsSystem(() -> {
                nArray[0] = this.myContext.getBuildTypesArtifactDependenciesCache().getDependingOn((SBuildType)this).size();
            });
        }
        catch (Throwable throwable) {
            ExceptionUtil.rethrowAsRuntimeException((Throwable)throwable);
        }
        return nArray[0];
    }

    public void setArtifactDependencies(@NotNull List<SArtifactDependency> list) {
        try {
            if (list == null) {
                BuildTypeImpl.$$$reportNull$$$0(196);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        for (SArtifactDependency sArtifactDependency : list) {
            SBuildType sBuildType = (SBuildType)this.myContext.getSecurityContext().runAsSystemUnchecked(() -> sArtifactDependency.getSourceBuildType());
            try {
                try {
                    if (sBuildType == null || this.myContext.getDependencyChecker().checkDependency(sBuildType.getProject(), (SProject)this.getProject(), DependencyType.ARTIFACT) != DependencyCheckResult.ORGANIZATIONS_VIOLATION) continue;
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildTypeImpl.a(illegalStateException);
                }
                throw new OrganizationProjectAccessViolationException(String.format("Cannot add an artifact dependency on %s to the build configuration with id %s", sBuildType.getExternalId(), this.getExternalId()));
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildTypeImpl.a(illegalStateException);
            }
        }
        this.beforeModification();
        this.markEdited();
        this.myBuildTypeSettings.setArtifactDependencies(list);
    }

    public void addArtifactDependency(@NotNull SArtifactDependency sArtifactDependency) {
        block7: {
            try {
                if (sArtifactDependency == null) {
                    BuildTypeImpl.$$$reportNull$$$0(197);
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildTypeImpl.a(illegalStateException);
            }
            SBuildType sBuildType = (SBuildType)this.myContext.getSecurityContext().runAsSystemUnchecked(() -> sArtifactDependency.getSourceBuildType());
            try {
                try {
                    if (sBuildType == null || this.myContext.getDependencyChecker().checkDependency(sBuildType.getProject(), (SProject)this.getProject(), DependencyType.ARTIFACT) != DependencyCheckResult.ORGANIZATIONS_VIOLATION) break block7;
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildTypeImpl.a(illegalStateException);
                }
                throw new OrganizationProjectAccessViolationException(String.format("Cannot add an artifact dependency on %s to the build configuration with id %s", sBuildType.getExternalId(), this.getExternalId()));
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildTypeImpl.a(illegalStateException);
            }
        }
        this.beforeModification();
        this.markEdited();
        this.myBuildTypeSettings.addArtifactDependency(sArtifactDependency);
    }

    public void removeArtifactDependency(@NotNull SArtifactDependency sArtifactDependency) {
        try {
            if (sArtifactDependency == null) {
                BuildTypeImpl.$$$reportNull$$$0(198);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        this.beforeModification();
        this.markEdited();
        this.myBuildTypeSettings.removeArtifactDependency(sArtifactDependency);
    }

    @NotNull
    public File getArtifactsDirectory() {
        File file;
        try {
            file = this.getArtifactDirectories().get(0);
            if (file == null) {
                BuildTypeImpl.$$$reportNull$$$0(199);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return file;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void nameChanged(@NotNull String string, @NotNull String string2) {
        try {
            if (string == null) {
                BuildTypeImpl.$$$reportNull$$$0(200);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            if (string2 == null) {
                BuildTypeImpl.$$$reportNull$$$0(201);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        this.myProject.resortBuildTypes();
        ArrayList<File> arrayList = new ArrayList<File>();
        for (File file : this.myProject.getArtifactDirectories()) {
            arrayList.add(new File(file, FileUtil.fixDirectoryName((String)string)));
        }
        List<File> list = this.myOldArtifactDirectories;
        synchronized (list) {
            this.myOldArtifactDirectories.addAll(arrayList);
        }
        ((BuildServerListener)this.myContext.getEventDispatcher().getMulticaster()).buildTypeRenamed((SBuildType)this, string, string2);
    }

    protected void sendPersistedEvents(@NotNull ConfigAction configAction) {
        try {
            if (configAction == null) {
                BuildTypeImpl.$$$reportNull$$$0(202);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        ((BuildServerListener)this.myContext.getEventDispatcher().getMulticaster()).buildTypePersisted((SBuildType)this);
    }

    public void addCleanupPolicy(@NotNull HistoryRetentionPolicy historyRetentionPolicy) {
        try {
            if (historyRetentionPolicy == null) {
                BuildTypeImpl.$$$reportNull$$$0(203);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        this.markEdited();
        this.myBuildTypeSettings.addCleanupPolicy(historyRetentionPolicy);
    }

    public void removeCleanupPolicies() {
        this.markEdited();
        this.myBuildTypeSettings.removeCleanupPolicies();
    }

    public void setCleanupOption(@NotNull String string, @Nullable Boolean bl) {
        try {
            if (string == null) {
                BuildTypeImpl.$$$reportNull$$$0(204);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        this.markEdited();
        this.myBuildTypeSettings.setCleanupOption(string, bl);
    }

    @NotNull
    public Collection<HistoryRetentionPolicy> getCleanupPolicies() {
        Collection collection;
        try {
            collection = this.myBuildTypeSettings.getCleanupPolicies();
            if (collection == null) {
                BuildTypeImpl.$$$reportNull$$$0(205);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return collection;
    }

    @NotNull
    public Collection<HistoryRetentionPolicy> getOwnCleanupPolicies() {
        Collection collection;
        try {
            collection = this.myBuildTypeSettings.getOwnCleanupPolicies();
            if (collection == null) {
                BuildTypeImpl.$$$reportNull$$$0(206);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return collection;
    }

    @NotNull
    public Collection<HistoryRetentionPolicy> getInheritedCleanupPolicies() {
        Collection collection;
        try {
            collection = this.myBuildTypeSettings.getInheritedCleanupPolicies();
            if (collection == null) {
                BuildTypeImpl.$$$reportNull$$$0(207);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return collection;
    }

    @NotNull
    public Map<String, Boolean> getInheritedCleanupOptions() {
        Map map;
        try {
            map = this.myBuildTypeSettings.getInheritedCleanupOptions();
            if (map == null) {
                BuildTypeImpl.$$$reportNull$$$0(208);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return map;
    }

    @NotNull
    public Map<String, Boolean> getCleanupOptions() {
        Map map;
        try {
            map = this.myBuildTypeSettings.getCleanupOptions();
            if (map == null) {
                BuildTypeImpl.$$$reportNull$$$0(209);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return map;
    }

    @NotNull
    public Map<String, Boolean> getOwnCleanupOptions() {
        Map map;
        try {
            map = this.myBuildTypeSettings.getOwnCleanupOptions();
            if (map == null) {
                BuildTypeImpl.$$$reportNull$$$0(210);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return map;
    }

    public void setDirtyCompatibility() {
        try {
            if (this.myBuildTypeSettings == null) {
                return;
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        DummyBuildPromotion dummyBuildPromotion = this.myBranches.getDummyPromotionFromCache("<default>", false);
        try {
            if (dummyBuildPromotion != null) {
                dummyBuildPromotion.resetAllCompatibilityCaches();
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        this.resetResolvedVcsRootInstancesCache();
    }

    public int compareTo(@NotNull BuildTypeDescriptor buildTypeDescriptor) {
        try {
            if (buildTypeDescriptor == null) {
                BuildTypeImpl.$$$reportNull$$$0(211);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return BuildTypeImpl.compareBuildTypes((BuildTypeDescriptor)this, buildTypeDescriptor);
    }

    public static int compareBuildTypes(@NotNull BuildTypeDescriptor buildTypeDescriptor, @NotNull BuildTypeDescriptor buildTypeDescriptor2) {
        try {
            if (buildTypeDescriptor == null) {
                BuildTypeImpl.$$$reportNull$$$0(212);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            if (buildTypeDescriptor2 == null) {
                BuildTypeImpl.$$$reportNull$$$0(213);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            if (buildTypeDescriptor == buildTypeDescriptor2) {
                return 0;
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        int n = buildTypeDescriptor.getName().compareToIgnoreCase(buildTypeDescriptor2.getName());
        try {
            if (n == 0) {
                return buildTypeDescriptor.getBuildTypeId().compareTo(buildTypeDescriptor2.getBuildTypeId());
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return n;
    }

    @NotNull
    public List<BranchEx> getBranches(@NotNull BranchesPolicy branchesPolicy, @Nullable Boolean bl) {
        List list;
        try {
            if (branchesPolicy == null) {
                BuildTypeImpl.$$$reportNull$$$0(214);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            list = this.myBranches.getBranches(new BranchCalculationOptions().setBranchesPolicy(branchesPolicy).setIncludeBranchesFromDependencies(bl).setComputeTimestamps(false));
            if (list == null) {
                BuildTypeImpl.$$$reportNull$$$0(215);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return list;
    }

    @NotNull
    public List<BranchEx> getBranches(@NotNull BranchesPolicy branchesPolicy, @Nullable Boolean bl, boolean bl2) {
        List list;
        try {
            if (branchesPolicy == null) {
                BuildTypeImpl.$$$reportNull$$$0(216);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        BranchCalculationOptions branchCalculationOptions = new BranchCalculationOptions().setBranchesPolicy(branchesPolicy).setIncludeBranchesFromDependencies(bl).setComputeTimestamps(bl2).setSortBranches(false);
        try {
            list = this.myBranches.getBranches(branchCalculationOptions);
            if (list == null) {
                BuildTypeImpl.$$$reportNull$$$0(217);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return list;
    }

    @NotNull
    public List<BranchEx> getBranches(@NotNull BranchCalculationOptions branchCalculationOptions) {
        List list;
        try {
            if (branchCalculationOptions == null) {
                BuildTypeImpl.$$$reportNull$$$0(218);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            list = this.myBranches.getBranches(branchCalculationOptions);
            if (list == null) {
                BuildTypeImpl.$$$reportNull$$$0(219);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return list;
    }

    @NotNull
    public BranchEx getBranch(@NotNull String string) {
        BranchEx branchEx;
        try {
            if (string == null) {
                BuildTypeImpl.$$$reportNull$$$0(220);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            branchEx = this.myBranches.getBranch(string);
            if (branchEx == null) {
                BuildTypeImpl.$$$reportNull$$$0(221);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return branchEx;
    }

    public boolean isValidVcsBranch(@NotNull String string, boolean bl) {
        try {
            if (string == null) {
                BuildTypeImpl.$$$reportNull$$$0(222);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return this.myBranches.isValidVcsBranch(string, bl);
    }

    @NotNull
    public Collection<String> getOwnVcsBranchNames(boolean bl) {
        Collection collection;
        try {
            collection = this.myBranches.getOwnVcsBranchNames(bl);
            if (collection == null) {
                BuildTypeImpl.$$$reportNull$$$0(223);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return collection;
    }

    public boolean hasOwnBranches() {
        boolean bl;
        List<BranchEx> list = this.getBranches(BranchesPolicy.ALL_BRANCHES, false);
        try {
            bl = list.size() > 1;
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return bl;
    }

    @NotNull
    public List<BranchEx> getActiveBranches() {
        List<BranchEx> list;
        try {
            list = this.getBranches(BranchesPolicy.ACTIVE_HISTORY_AND_ACTIVE_VCS_BRANCHES, false);
            if (list == null) {
                BuildTypeImpl.$$$reportNull$$$0(224);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return list;
    }

    @NotNull
    public BranchSpec getBranchSpec(@NotNull LVcsRootInstance lVcsRootInstance) {
        BranchSpec branchSpec;
        try {
            if (lVcsRootInstance == null) {
                BuildTypeImpl.$$$reportNull$$$0(225);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            branchSpec = this.getBranchSpec((VcsRootInstance)lVcsRootInstance);
            if (branchSpec == null) {
                BuildTypeImpl.$$$reportNull$$$0(226);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return branchSpec;
    }

    @NotNull
    public List<BranchSpec> getAdditionalBranchSpecs(@NotNull LVcsRootInstance lVcsRootInstance, @NotNull VcsRepositoryState vcsRepositoryState) {
        List<BranchSpec> list;
        try {
            if (lVcsRootInstance == null) {
                BuildTypeImpl.$$$reportNull$$$0(227);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            if (vcsRepositoryState == null) {
                BuildTypeImpl.$$$reportNull$$$0(228);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            list = this.getAdditionalBranchSpecs((VcsRootInstance)lVcsRootInstance, vcsRepositoryState);
            if (list == null) {
                BuildTypeImpl.$$$reportNull$$$0(229);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return list;
    }

    @NotNull
    public Set<String> getAdditionalBranchesForChangesCollecting(@NotNull LVcsRootInstance lVcsRootInstance, @NotNull VcsRepositoryState vcsRepositoryState) {
        HashSet<String> hashSet;
        try {
            if (lVcsRootInstance == null) {
                BuildTypeImpl.$$$reportNull$$$0(230);
            }
        }
        catch (Throwable throwable) {
            throw BuildTypeImpl.a(throwable);
        }
        try {
            if (vcsRepositoryState == null) {
                BuildTypeImpl.$$$reportNull$$$0(231);
            }
        }
        catch (Throwable throwable) {
            throw BuildTypeImpl.a(throwable);
        }
        HashSet<String> hashSet2 = new HashSet<String>();
        for (BranchSpecProvider branchSpecProvider : this.myContext.getExtensionsProvider().getExtensions(BranchSpecProvider.class)) {
            try {
                hashSet2.addAll(branchSpecProvider.getBranchesForChangesCollecting((BuildTypeEx)this, (VcsRootInstance)lVcsRootInstance, vcsRepositoryState));
            }
            catch (Throwable throwable) {
                LOG.warnAndDebugDetails("Failed to obtain additional branches for changes collecting from a branch spec provider", throwable);
            }
        }
        try {
            hashSet = hashSet2;
            if (hashSet == null) {
                BuildTypeImpl.$$$reportNull$$$0(232);
            }
        }
        catch (Throwable throwable) {
            throw BuildTypeImpl.a(throwable);
        }
        return hashSet;
    }

    @NotNull
    public BranchSpec getBranchSpec(@NotNull VcsRootInstance vcsRootInstance) {
        List<BranchSpec> list;
        BranchSpec branchSpec;
        block7: {
            BranchSpec branchSpec2;
            block8: {
                try {
                    if (vcsRootInstance == null) {
                        BuildTypeImpl.$$$reportNull$$$0(233);
                    }
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildTypeImpl.a(illegalStateException);
                }
                branchSpec = this.getBranchSpec(vcsRootInstance.getParent());
                4 var3_3 = new /* Unavailable Anonymous Inner Class!! */;
                list = this.getAdditionalBranchSpecs(vcsRootInstance, (Supplier<VcsRepositoryState>)var3_3, true);
                try {
                    try {
                        if (!list.isEmpty()) break block7;
                        branchSpec2 = branchSpec;
                        if (branchSpec2 != null) break block8;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildTypeImpl.a(illegalStateException);
                    }
                    BuildTypeImpl.$$$reportNull$$$0(234);
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildTypeImpl.a(illegalStateException);
                }
            }
            return branchSpec2;
        }
        ArrayList<BranchSpec> arrayList = new ArrayList<BranchSpec>(list.size() + 1);
        arrayList.add(branchSpec);
        arrayList.addAll(list);
        return new CompositeBranchSpec(arrayList);
    }

    @NotNull
    public BranchSpec getBranchSpec(@NotNull SVcsRoot sVcsRoot) {
        BranchSpec branchSpec;
        BranchSpec branchSpec2;
        try {
            if (sVcsRoot == null) {
                BuildTypeImpl.$$$reportNull$$$0(235);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        BranchSpec branchSpec3 = ((SVcsRootEx)sVcsRoot).getBranchSpec(this.getValueResolver());
        try {
            branchSpec2 = branchSpec3 == null ? this.myContext.getBranchSpecs().createDefault() : branchSpec3;
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            branchSpec = branchSpec2;
            if (branchSpec2 == null) {
                BuildTypeImpl.$$$reportNull$$$0(236);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return branchSpec;
    }

    @NotNull
    public BranchSpec getLastUsedBranchSpec(@NotNull SVcsRoot sVcsRoot) {
        BranchSpec branchSpec;
        String string;
        block10: {
            BranchSpec branchSpec2;
            block11: {
                try {
                    if (sVcsRoot == null) {
                        BuildTypeImpl.$$$reportNull$$$0(237);
                    }
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildTypeImpl.a(illegalStateException);
                }
                CustomDataStorage customDataStorage = this.getCustomDataStorage("jetbrains.buildServer.buildTriggers.vcs.VcsRootChangesLoader");
                string = customDataStorage.getValue("prevBranchSpec-" + sVcsRoot.getId());
                try {
                    try {
                        if (string != null) break block10;
                        branchSpec2 = this.getBranchSpec(sVcsRoot);
                        if (branchSpec2 != null) break block11;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildTypeImpl.a(illegalStateException);
                    }
                    BuildTypeImpl.$$$reportNull$$$0(238);
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildTypeImpl.a(illegalStateException);
                }
            }
            return branchSpec2;
        }
        try {
            branchSpec = this.myContext.getBranchSpecs().parse(string);
            if (branchSpec == null) {
                BuildTypeImpl.$$$reportNull$$$0(239);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return branchSpec;
    }

    public boolean rememberLastUsedRootInstanceAndBranchSpec(@NotNull LVcsRootInstance lVcsRootInstance, @NotNull BranchSpec branchSpec) {
        block10: {
            try {
                if (lVcsRootInstance == null) {
                    BuildTypeImpl.$$$reportNull$$$0(240);
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildTypeImpl.a(illegalStateException);
            }
            try {
                if (branchSpec == null) {
                    BuildTypeImpl.$$$reportNull$$$0(241);
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildTypeImpl.a(illegalStateException);
            }
            CustomDataStorage customDataStorage = this.getCustomDataStorage("jetbrains.buildServer.buildTriggers.vcs.VcsRootChangesLoader");
            String string = "prevBranchSpec-" + lVcsRootInstance.getParentId();
            String string2 = "prevRootInstance-" + lVcsRootInstance.getParentId();
            String string3 = customDataStorage.getValue(string);
            String string4 = customDataStorage.getValue(string2);
            try {
                try {
                    if (branchSpec.asString().equals(string3) && String.valueOf(lVcsRootInstance.getId()).equals(string4)) break block10;
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildTypeImpl.a(illegalStateException);
                }
                customDataStorage.putValue(string, branchSpec.asString());
                customDataStorage.putValue(string2, String.valueOf(lVcsRootInstance.getId()));
                customDataStorage.flush(CustomDataStorage.ConflictResolution.IGNORE_THEIRS);
                return true;
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildTypeImpl.a(illegalStateException);
            }
        }
        return false;
    }

    public void branchesChanged(@NotNull Date date) {
        try {
            if (date == null) {
                BuildTypeImpl.$$$reportNull$$$0(242);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        BuildTypeImpl.branchesChanged(Collections.singletonList(this), date);
    }

    public static void branchesChanged(@NotNull Collection<BuildTypeEx> collection, @NotNull Date date) {
        try {
            if (collection == null) {
                BuildTypeImpl.$$$reportNull$$$0(243);
            }
        }
        catch (Exception exception) {
            throw BuildTypeImpl.a(exception);
        }
        try {
            if (date == null) {
                BuildTypeImpl.$$$reportNull$$$0(244);
            }
        }
        catch (Exception exception) {
            throw BuildTypeImpl.a(exception);
        }
        try {
            if (collection.isEmpty()) {
                return;
            }
        }
        catch (Exception exception) {
            throw BuildTypeImpl.a(exception);
        }
        for (BuildTypeEx buildTypeEx : collection) {
            try {
                if (!(buildTypeEx instanceof BuildTypeImpl)) continue;
                ((BuildTypeImpl)buildTypeEx).getBuildTypeBranches().branchesChanged(date);
            }
            catch (Exception exception) {
                Loggers.SERVER.warnAndDebugDetails("Failed to process 'branchesChanged' event for the build configuration: " + LogUtil.describe((BuildTypeEx)buildTypeEx), (Throwable)exception);
            }
        }
    }

    @NotNull
    public Map<VcsRootInstance, BranchSpec> getLastUsedBranchSpecMap() {
        Map<VcsRootInstance, BranchSpec> map;
        try {
            map = this.getLastUsedBranchSpecMap(false);
            if (map == null) {
                BuildTypeImpl.$$$reportNull$$$0(245);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return map;
    }

    @NotNull
    public Map<VcsRootInstance, BranchSpec> getLastUsedBranchSpecMap(boolean bl) {
        HashMap<VcsRootInstance, BranchSpec> hashMap;
        HashMap<VcsRootInstance, BranchSpec> hashMap2;
        block21: {
            SVcsRoot sVcsRoot;
            HashSet<SVcsRoot> hashSet = new HashSet<SVcsRoot>(this.getVcsRoots());
            CustomDataStorage customDataStorage = this.getCustomDataStorage("jetbrains.buildServer.buildTriggers.vcs.VcsRootChangesLoader");
            HashMap hashMap3 = customDataStorage.getValues();
            if (hashMap3 == null) {
                hashMap3 = new HashMap();
            }
            hashMap2 = new HashMap<VcsRootInstance, BranchSpec>();
            boolean bl2 = false;
            Iterator<Object> iterator = hashSet.iterator();
            while (iterator.hasNext()) {
                long l;
                String string;
                String string2;
                block20: {
                    sVcsRoot = (SVcsRoot)iterator.next();
                    String string3 = "prevBranchSpec-" + sVcsRoot.getId();
                    String string4 = "prevRootInstance-" + sVcsRoot.getId();
                    string2 = (String)hashMap3.get(string3);
                    string = (String)hashMap3.get(string4);
                    try {
                        if (string != null && string2 != null) break block20;
                    }
                    catch (NumberFormatException numberFormatException) {
                        throw BuildTypeImpl.a(numberFormatException);
                    }
                    bl2 = true;
                    continue;
                }
                try {
                    l = Long.parseLong(string);
                }
                catch (NumberFormatException numberFormatException) {
                    bl2 = true;
                    continue;
                }
                BranchSpec branchSpec = this.myContext.getBranchSpecs().parse(string2);
                VcsRootInstance vcsRootInstance = this.myContext.getVcsRootInstancesManager().findRootInstanceById(l);
                if (vcsRootInstance == null) {
                    bl2 = true;
                    continue;
                }
                hashMap2.put(vcsRootInstance, branchSpec);
            }
            if (bl2) {
                iterator = this.getVcsRootInstances().iterator();
                while (iterator.hasNext()) {
                    sVcsRoot = (VcsRootInstance)iterator.next();
                    try {
                        if (hashMap2.containsKey(sVcsRoot)) {
                            continue;
                        }
                    }
                    catch (NumberFormatException numberFormatException) {
                        throw BuildTypeImpl.a(numberFormatException);
                    }
                    hashMap2.put((VcsRootInstance)sVcsRoot, this.getLastUsedBranchSpec(sVcsRoot.getParent()));
                }
            }
            if (bl && (iterator = this.myContext.getSettingsRootManager().getVersionedSettingsProject((SProject)this.myProject)) != null) {
                sVcsRoot = ((ProjectEx)iterator).getSettingsRootSpec();
                try {
                    try {
                        if (sVcsRoot == null || hashMap2.containsKey(sVcsRoot.first)) break block21;
                    }
                    catch (NumberFormatException numberFormatException) {
                        throw BuildTypeImpl.a(numberFormatException);
                    }
                    hashMap2.put((VcsRootInstance)sVcsRoot.first, (BranchSpec)sVcsRoot.second);
                }
                catch (NumberFormatException numberFormatException) {
                    throw BuildTypeImpl.a(numberFormatException);
                }
            }
        }
        try {
            hashMap = hashMap2;
            if (hashMap == null) {
                BuildTypeImpl.$$$reportNull$$$0(246);
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw BuildTypeImpl.a(numberFormatException);
        }
        return hashMap;
    }

    @NotNull
    public List<BranchSpec> getAdditionalBranchSpecs(@NotNull VcsRootInstance vcsRootInstance, @NotNull VcsRepositoryState vcsRepositoryState) {
        try {
            if (vcsRootInstance == null) {
                BuildTypeImpl.$$$reportNull$$$0(247);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            if (vcsRepositoryState == null) {
                BuildTypeImpl.$$$reportNull$$$0(248);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return this.getAdditionalBranchSpecs(vcsRootInstance, () -> vcsRepositoryState, false);
    }

    @NotNull
    private List<BranchSpec> getAdditionalBranchSpecs(@NotNull VcsRootInstance vcsRootInstance, @NotNull Supplier<VcsRepositoryState> supplier, boolean bl) {
        ArrayList<BranchSpec> arrayList;
        try {
            if (vcsRootInstance == null) {
                BuildTypeImpl.$$$reportNull$$$0(249);
            }
        }
        catch (Throwable throwable) {
            throw BuildTypeImpl.a(throwable);
        }
        try {
            if (supplier == null) {
                BuildTypeImpl.$$$reportNull$$$0(250);
            }
        }
        catch (Throwable throwable) {
            throw BuildTypeImpl.a(throwable);
        }
        ArrayList<BranchSpec> arrayList2 = new ArrayList<BranchSpec>();
        boolean bl2 = TeamCityProperties.getBooleanOrTrue((String)"teamcity.buildType.branchSpec.sortExtensionsSpecs");
        for (BranchSpecProvider branchSpecProvider : this.myContext.getExtensionsProvider().getExtensions(BranchSpecProvider.class)) {
            try {
                try {
                    if (bl && !branchSpecProvider.includeIntoOwnBranchSpecsOf((BuildTypeEx)this)) continue;
                }
                catch (Throwable throwable) {
                    throw BuildTypeImpl.a(throwable);
                }
                ArrayList arrayList3 = new ArrayList(branchSpecProvider.getBranchSpecs((BuildTypeEx)this, vcsRootInstance, supplier.get()));
                try {
                    if (bl2) {
                        arrayList3.sort(String::compareTo);
                    }
                }
                catch (Throwable throwable) {
                    throw BuildTypeImpl.a(throwable);
                }
                for (String string : arrayList3) {
                    arrayList2.add(this.myContext.getBranchSpecs().parse(string));
                }
            }
            catch (Throwable throwable) {
                LOG.warnAndDebugDetails("Failed to obtain additional branch specs from a branch spec provider", throwable);
            }
        }
        try {
            arrayList = arrayList2;
            if (arrayList == null) {
                BuildTypeImpl.$$$reportNull$$$0(251);
            }
        }
        catch (Throwable throwable) {
            throw BuildTypeImpl.a(throwable);
        }
        return arrayList;
    }

    public boolean isBranchSpecDefined(boolean bl) {
        return this.isBranchSpecDefined(bl, false);
    }

    public boolean isBranchSpecDefined(boolean bl, boolean bl2) {
        Object object;
        try {
            if (BuildTypeImpl.isBranchSpecDefined(this.getVcsRoots(), this.getValueResolver())) {
                return true;
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        Collection collection = this.myContext.getExtensionsProvider().getExtensions(BranchSpecProvider.class);
        for (BranchSpecProvider branchSpecProvider : collection) {
            try {
                if (!branchSpecProvider.includeIntoOwnBranchSpecsOf((BuildTypeEx)this)) continue;
                return true;
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildTypeImpl.a(illegalStateException);
            }
        }
        if (bl2) {
            object = this.myContext.getSettingsRootManager().getVersionedSettingsVcsRoot((SProject)this.myProject);
            try {
                if (BuildTypeImpl.isBranchSpecDefined(Collections.singletonList(object), this.myProject.getValueResolver())) {
                    return true;
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildTypeImpl.a(illegalStateException);
            }
        }
        if (bl) {
            object = new AtomicBoolean();
            this.traverseDependencies((DependencyConsumer<BuildTypeEx>)((DependencyConsumer)arg_0 -> BuildTypeImpl.lambda$isBranchSpecDefined$16((AtomicBoolean)object, collection, arg_0)));
            return ((AtomicBoolean)object).get();
        }
        return false;
    }

    public static boolean isBranchSpecDefined(@NotNull Collection<? extends VcsRoot> collection, @NotNull ValueResolver valueResolver) {
        try {
            if (collection == null) {
                BuildTypeImpl.$$$reportNull$$$0(252);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            if (valueResolver == null) {
                BuildTypeImpl.$$$reportNull$$$0(253);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        for (VcsRoot vcsRoot : collection) {
            try {
                if (!(vcsRoot instanceof SVcsRootEx)) {
                    continue;
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildTypeImpl.a(illegalStateException);
            }
            SVcsRootEx sVcsRootEx = (SVcsRootEx)vcsRoot;
            try {
                if (!sVcsRootEx.isBranchSpecDefined(valueResolver)) continue;
                return true;
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildTypeImpl.a(illegalStateException);
            }
        }
        return false;
    }

    @NotNull
    public BuildTypeOrderedBuildsImpl getBuildTypeOrderedBuilds() {
        BuildTypeOrderedBuildsImpl buildTypeOrderedBuildsImpl;
        try {
            buildTypeOrderedBuildsImpl = this.myBuildTypeOrderedBuilds;
            if (buildTypeOrderedBuildsImpl == null) {
                BuildTypeImpl.$$$reportNull$$$0(254);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return buildTypeOrderedBuildsImpl;
    }

    public void clearPrevPromotionCache(@Nullable Collection<BuildPromotion> collection) {
        this.myBranches.clearPrevPromotionCache(collection);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void resetTagsCache() {
        Object object = this.myTagsLock;
        synchronized (object) {
            this.myTagsData = null;
        }
    }

    @Used
    public boolean isCheckoutOnServer() {
        boolean bl;
        try {
            bl = this.getCheckoutType() == BuildTypeDescriptor.CheckoutType.ON_SERVER;
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return bl;
    }

    @Used
    public boolean isCheckoutOnAgent() {
        boolean bl;
        try {
            bl = this.getCheckoutType() == BuildTypeDescriptor.CheckoutType.ON_AGENT;
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return bl;
    }

    @NotNull
    public List<SVcsRoot> getVcsRoots() {
        List list;
        try {
            list = this.myBuildTypeSettings.getVcsRoots();
            if (list == null) {
                BuildTypeImpl.$$$reportNull$$$0(255);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return list;
    }

    public boolean containsVcsRoot(long l) {
        return this.myBuildTypeSettings.containsVcsRoot(l);
    }

    public boolean containsOwnVcsRoot(long l) {
        return this.myBuildTypeSettings.containsOwnVcsRoot(l);
    }

    public void updateRevisionsForOwnBranches(@NotNull Collection<String> collection) {
        try {
            if (collection == null) {
                BuildTypeImpl.$$$reportNull$$$0(256);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        this.myBranches.updateRevisionsForOwnBranches(collection);
    }

    @NotNull
    public List<VcsRootEntry> getVcsRootEntries() {
        List list;
        try {
            list = this.myBuildTypeSettings.getVcsRootEntries();
            if (list == null) {
                BuildTypeImpl.$$$reportNull$$$0(257);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return list;
    }

    public boolean addVcsRoot(@NotNull SVcsRoot sVcsRoot) {
        try {
            if (sVcsRoot == null) {
                BuildTypeImpl.$$$reportNull$$$0(258);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        this.beforeModification();
        boolean bl = this.myBuildTypeSettings.addVcsRoot(sVcsRoot);
        try {
            if (bl) {
                this.markEdited();
                this.setDirtyCompatibility();
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return bl;
    }

    private void registerVcsRoot(SVcsRoot sVcsRoot) {
        this.executeIfInModel(() -> this.myContext.getVcsRootAssociationHolder().attachTo((SBuildType)this, sVcsRoot));
    }

    public boolean removeVcsRoot(@NotNull SVcsRoot sVcsRoot) {
        try {
            if (sVcsRoot == null) {
                BuildTypeImpl.$$$reportNull$$$0(259);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        this.beforeModification();
        boolean bl = this.myBuildTypeSettings.removeVcsRoot(sVcsRoot);
        try {
            if (bl) {
                this.markEdited();
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return bl;
    }

    public boolean replaceVcsRoot(@NotNull SVcsRoot sVcsRoot, @NotNull SVcsRoot sVcsRoot2) {
        try {
            if (sVcsRoot == null) {
                BuildTypeImpl.$$$reportNull$$$0(260);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            if (sVcsRoot2 == null) {
                BuildTypeImpl.$$$reportNull$$$0(261);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        this.beforeModification();
        boolean bl = this.myBuildTypeSettings.replaceVcsRoot(sVcsRoot, sVcsRoot2);
        try {
            if (bl) {
                this.markEdited();
                this.setDirtyCompatibility();
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return bl;
    }

    public boolean setVcsRoots(@NotNull Collection<SVcsRoot> collection) {
        try {
            if (collection == null) {
                BuildTypeImpl.$$$reportNull$$$0(262);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        this.beforeModification();
        boolean bl = this.myBuildTypeSettings.setVcsRoots(collection);
        try {
            if (bl) {
                this.markEdited();
                this.setDirtyCompatibility();
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return bl;
    }

    public void setEditable(boolean bl) {
        new ReadOnlyUtil(this.myContext.getSettingsRootManager()).setEditable((SPersistentEntity)this, bl);
    }

    public void beforeAddVcsRoot(@NotNull SVcsRoot sVcsRoot) {
        try {
            if (sVcsRoot == null) {
                BuildTypeImpl.$$$reportNull$$$0(263);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        sVcsRoot.checkAccessibleFrom((SBuildType)this);
    }

    public void afterAddVcsRoot(@NotNull SVcsRoot sVcsRoot) {
        try {
            if (sVcsRoot == null) {
                BuildTypeImpl.$$$reportNull$$$0(264);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        this.registerVcsRoot(sVcsRoot);
        this.resetDummyBuilds();
    }

    public void beforeRemoveVcsRoot(@NotNull SVcsRoot sVcsRoot) {
        try {
            if (sVcsRoot == null) {
                BuildTypeImpl.$$$reportNull$$$0(265);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
    }

    public void beforeAddDependency(@NotNull Dependency dependency) {
        try {
            if (dependency == null) {
                BuildTypeImpl.$$$reportNull$$$0(266);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            if (this.myAttachedToModel.get()) {
                this.myProject.checkCyclicDependency(Collections.singletonList(this), dependency);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
    }

    public void afterRemoveVcsRoot(@NotNull SVcsRoot sVcsRoot) {
        try {
            if (sVcsRoot == null) {
                BuildTypeImpl.$$$reportNull$$$0(267);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        this.executeIfInModel(() -> {
            try {
                if (!this.containsVcsRoot(sVcsRoot.getId())) {
                    this.myContext.getVcsRootAssociationHolder().detachFrom((SBuildType)this, sVcsRoot);
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildTypeImpl.a(illegalStateException);
            }
        });
        this.resetDummyBuilds();
    }

    public void checkoutRulesChanged(@NotNull SVcsRoot sVcsRoot, @Nullable CheckoutRules checkoutRules, @NotNull CheckoutRules checkoutRules2) {
        try {
            if (sVcsRoot == null) {
                BuildTypeImpl.$$$reportNull$$$0(268);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            if (checkoutRules2 == null) {
                BuildTypeImpl.$$$reportNull$$$0(269);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
    }

    public void afterAddDependency(@NotNull Dependency dependency) {
        try {
            if (dependency == null) {
                BuildTypeImpl.$$$reportNull$$$0(270);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        this.executeIfInModel(() -> {
            this.myContext.getBuildTypeDependencies().dependencyAdded((SBuildType)this, dependency);
            this.resetDummyBuilds();
        });
    }

    public void dependencyReplaced(@NotNull Dependency dependency, @NotNull Dependency dependency2) {
        try {
            if (dependency == null) {
                BuildTypeImpl.$$$reportNull$$$0(271);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            if (dependency2 == null) {
                BuildTypeImpl.$$$reportNull$$$0(272);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        this.executeIfInModel(() -> {
            this.myContext.getBuildTypeDependencies().dependencyRemoved((SBuildType)this, dependency);
            this.myContext.getBuildTypeDependencies().dependencyAdded((SBuildType)this, dependency2);
            this.resetDummyBuilds();
        });
    }

    public void artifactDependenciesChanged() {
        this.executeIfInModel(() -> this.myContext.getBuildTypesArtifactDependenciesCache().dependenciesChanged((SBuildType)this));
    }

    public void removeAllDependenciesFromDependedOnMeIndex() {
        this.executeIfInModel(() -> this.myContext.getBuildTypeDependencies().removeAllDependencies((SBuildType)this));
    }

    public void beforeRemoveDependency(@NotNull Dependency dependency) {
        try {
            if (dependency == null) {
                BuildTypeImpl.$$$reportNull$$$0(273);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
    }

    public void afterRemoveDependency(@NotNull Dependency dependency) {
        try {
            if (dependency == null) {
                BuildTypeImpl.$$$reportNull$$$0(274);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        this.executeIfInModel(() -> {
            try {
                if (!this.hasDependencyOnTheSameBuildType(dependency)) {
                    this.myContext.getBuildTypeDependencies().dependencyRemoved((SBuildType)this, dependency);
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildTypeImpl.a(illegalStateException);
            }
            this.resetDummyBuilds();
        });
    }

    private boolean hasDependencyOnTheSameBuildType(@NotNull Dependency dependency) {
        try {
            if (dependency == null) {
                BuildTypeImpl.$$$reportNull$$$0(275);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        for (Dependency dependency2 : this.getDependencies()) {
            block10: {
                try {
                    try {
                        if (!(dependency instanceof UnresolvedDependency) || !dependency2.getDependOnExternalId().equals(dependency.getDependOnExternalId())) break block10;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildTypeImpl.a(illegalStateException);
                    }
                    return true;
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildTypeImpl.a(illegalStateException);
                }
            }
            try {
                if (!dependency.getDependOnId().equals(dependency2.getDependOnId())) continue;
                return true;
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildTypeImpl.a(illegalStateException);
            }
        }
        return false;
    }

    public void beforeAddRequirement(@NotNull Requirement requirement) {
        try {
            if (requirement == null) {
                BuildTypeImpl.$$$reportNull$$$0(276);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
    }

    public void afterAddRequirement(@NotNull Requirement requirement) {
        try {
            if (requirement == null) {
                BuildTypeImpl.$$$reportNull$$$0(277);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        this.setDirtyCompatibility();
    }

    public void beforeRemoveRequirement(@NotNull Requirement requirement) {
        try {
            if (requirement == null) {
                BuildTypeImpl.$$$reportNull$$$0(278);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
    }

    public void afterRemoveRequirement(@NotNull Requirement requirement) {
        try {
            if (requirement == null) {
                BuildTypeImpl.$$$reportNull$$$0(279);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        this.setDirtyCompatibility();
    }

    public void afterAddBuildTrigger(@NotNull BuildTriggerDescriptor buildTriggerDescriptor) {
        try {
            if (buildTriggerDescriptor == null) {
                BuildTypeImpl.$$$reportNull$$$0(280);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        this.activateTriggerIfNeeded(buildTriggerDescriptor.getId());
    }

    private void activateTriggersIfSettingsChanged() {
        try {
            if (!TeamCityProperties.getBooleanOrTrue((String)"teamcity.buildTriggers.activateOnConfigReload")) {
                return;
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        this.executeIfInModel(() -> {
            try {
                if (!this.myContext.getProjectManager().isProjectsModelInitialized()) {
                    return;
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildTypeImpl.a(illegalStateException);
            }
            for (BuildTriggerDescriptor buildTriggerDescriptor : this.getResolvedSettings().getBuildTriggersCollection()) {
                try {
                    if (!this.isEnabled(buildTriggerDescriptor.getId())) {
                        continue;
                    }
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildTypeImpl.a(illegalStateException);
                }
                this.myContext.getBuildTriggersProcessor().activateTriggerIfSettingsChanged((SBuildType)this, buildTriggerDescriptor);
            }
        });
    }

    public void afterRemoveBuildTrigger(@NotNull BuildTriggerDescriptor buildTriggerDescriptor) {
        try {
            if (buildTriggerDescriptor == null) {
                BuildTypeImpl.$$$reportNull$$$0(281);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        this.deactivateTriggerIfNeeded(buildTriggerDescriptor);
        this.clearTriggerData(buildTriggerDescriptor);
    }

    public void afterUpdateBuildTrigger(@NotNull BuildTriggerDescriptor buildTriggerDescriptor, @NotNull BuildTriggerDescriptor buildTriggerDescriptor2) {
        try {
            if (buildTriggerDescriptor == null) {
                BuildTypeImpl.$$$reportNull$$$0(282);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            if (buildTriggerDescriptor2 == null) {
                BuildTypeImpl.$$$reportNull$$$0(283);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
    }

    public void newBuildRunnersOrderApplied(@NotNull String[] stringArray) {
        try {
            if (stringArray == null) {
                BuildTypeImpl.$$$reportNull$$$0(284);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
    }

    public void settingEnabledOrDisabled(@NotNull String string, boolean bl) {
        block9: {
            block8: {
                try {
                    if (string == null) {
                        BuildTypeImpl.$$$reportNull$$$0(285);
                    }
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildTypeImpl.a(illegalStateException);
                }
                try {
                    this.setDirtyCompatibility();
                    if (this.isPaused()) {
                        return;
                    }
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildTypeImpl.a(illegalStateException);
                }
                try {
                    if (!bl) break block8;
                    this.activateTriggerIfNeeded(string);
                    break block9;
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildTypeImpl.a(illegalStateException);
                }
            }
            this.deactivateBuildTriggers(string, false);
        }
    }

    private void clearTriggerData(@NotNull BuildTriggerDescriptor buildTriggerDescriptor) {
        try {
            if (buildTriggerDescriptor == null) {
                BuildTypeImpl.$$$reportNull$$$0(286);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        BuildTriggerDescriptor buildTriggerDescriptor2 = this.resolveBuildTriggersParameters(this.getDummyBuild().getParametersProvider(), Collections.singletonList(buildTriggerDescriptor)).iterator().next();
        this.myContext.getBuildTriggersProcessor().clearTriggerData((SBuildType)this, buildTriggerDescriptor2);
    }

    private void activateTriggerIfNeeded(@NotNull String string) {
        block10: {
            try {
                if (string == null) {
                    BuildTypeImpl.$$$reportNull$$$0(287);
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildTypeImpl.a(illegalStateException);
            }
            try {
                try {
                    if (this.isEnabled(string) && this.myAttachedToModel.get()) break block10;
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildTypeImpl.a(illegalStateException);
                }
                return;
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildTypeImpl.a(illegalStateException);
            }
        }
        for (BuildTriggerDescriptor buildTriggerDescriptor : this.getResolvedSettings().getBuildTriggersCollection()) {
            try {
                if (!buildTriggerDescriptor.getId().equals(string)) continue;
                this.activateOrDeactivateTrigger(buildTriggerDescriptor, true);
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildTypeImpl.a(illegalStateException);
            }
        }
    }

    private void deactivateTriggerIfNeeded(@NotNull BuildTriggerDescriptor buildTriggerDescriptor) {
        block7: {
            try {
                if (buildTriggerDescriptor == null) {
                    BuildTypeImpl.$$$reportNull$$$0(288);
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildTypeImpl.a(illegalStateException);
            }
            try {
                try {
                    if (this.isEnabled(buildTriggerDescriptor.getId()) && !this.isPaused()) break block7;
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildTypeImpl.a(illegalStateException);
                }
                return;
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildTypeImpl.a(illegalStateException);
            }
        }
        this.activateOrDeactivateTrigger(buildTriggerDescriptor, false);
    }

    private void deactivateBuildTriggers(@Nullable String string, boolean bl) {
        for (BuildTriggerDescriptor buildTriggerDescriptor : this.getResolvedBuildTriggerDescriptors(bl)) {
            try {
                try {
                    if (string != null && !buildTriggerDescriptor.getId().equals(string)) continue;
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildTypeImpl.a(illegalStateException);
                }
                this.activateOrDeactivateTrigger(buildTriggerDescriptor, false);
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildTypeImpl.a(illegalStateException);
            }
        }
    }

    private void activateOrDeactivateTrigger(@NotNull BuildTriggerDescriptor buildTriggerDescriptor, boolean bl) {
        block10: {
            try {
                if (buildTriggerDescriptor == null) {
                    BuildTypeImpl.$$$reportNull$$$0(289);
                }
            }
            catch (Throwable throwable) {
                throw BuildTypeImpl.a(throwable);
            }
            try {
                block9: {
                    try {
                        if (!bl) break block9;
                        this.myContext.getBuildTriggersProcessor().activateTrigger((SBuildType)this, buildTriggerDescriptor);
                        break block10;
                    }
                    catch (Throwable throwable) {
                        throw BuildTypeImpl.a(throwable);
                    }
                }
                this.myContext.getBuildTriggersProcessor().deactivateTrigger((SBuildType)this, buildTriggerDescriptor);
            }
            catch (Throwable throwable) {
                String string;
                try {
                    string = bl ? "" : "de";
                }
                catch (Throwable throwable2) {
                    throw BuildTypeImpl.a(throwable2);
                }
                String string2 = "Failed to " + string + "activate build trigger";
                Loggers.SERVER.warnAndDebugDetails(string2, throwable);
            }
        }
    }

    public void textValueChanged() {
        this.setDirtyCompatibility();
    }

    public void resetDummyBuilds() {
        try {
            if (this.myBranches != null) {
                this.myBranches.resetDummyBuilds();
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
    }

    @NotNull
    public BuildTypeBranches getBuildTypeBranches() {
        BuildTypeBranches buildTypeBranches;
        try {
            buildTypeBranches = this.myBranches;
            if (buildTypeBranches == null) {
                BuildTypeImpl.$$$reportNull$$$0(290);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return buildTypeBranches;
    }

    public CheckoutRules getCheckoutRules(@NotNull VcsRoot vcsRoot) {
        try {
            if (vcsRoot == null) {
                BuildTypeImpl.$$$reportNull$$$0(291);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return this.myBuildTypeSettings.getCheckoutRules(vcsRoot);
    }

    public boolean setCheckoutRules(@NotNull VcsRoot vcsRoot, @NotNull CheckoutRules checkoutRules) {
        try {
            if (vcsRoot == null) {
                BuildTypeImpl.$$$reportNull$$$0(292);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            if (checkoutRules == null) {
                BuildTypeImpl.$$$reportNull$$$0(293);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            this.beforeModification();
            if (this.myBuildTypeSettings.setCheckoutRules(vcsRoot, checkoutRules)) {
                this.markEdited();
                this.resetDummyBuilds();
                return true;
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return false;
    }

    @Deprecated
    @NotNull
    public String getVcsSettingsHash(@NotNull List<VcsRootInstanceEntry> list) {
        String string;
        BuildTypeDescriptor.CheckoutType checkoutType;
        String string2;
        BuildTypeImpl buildTypeImpl;
        List<VcsRootInstanceEntry> list2;
        try {
            if (list == null) {
                BuildTypeImpl.$$$reportNull$$$0(294);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        String string3 = this.getResolvedSettings().getCheckoutDirectory();
        BuildTypeDescriptor.CheckoutType checkoutType2 = this.getCheckoutType();
        try {
            list2 = list;
            buildTypeImpl = this;
            string2 = string3;
            checkoutType = checkoutType2 != BuildTypeDescriptor.CheckoutType.AUTO ? checkoutType2 : BuildTypeDescriptor.CheckoutType.ON_AGENT;
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            string = Long.toHexString(Hash.calc((String)BuildTypeImpl.computeSignature(list2, (SBuildType)buildTypeImpl, string2, checkoutType)));
            if (string == null) {
                BuildTypeImpl.$$$reportNull$$$0(295);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return string;
    }

    @Deprecated
    @NotNull
    public String getVcsSettingsHash() {
        String string;
        try {
            string = this.getVcsSettingsHash(this.getVcsRootInstanceEntries());
            if (string == null) {
                BuildTypeImpl.$$$reportNull$$$0(296);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return string;
    }

    @Deprecated
    @NotNull
    public String getVcsRootsHash() {
        String string;
        BuildTypeDescriptor.CheckoutType checkoutType;
        String string2;
        BuildTypeImpl buildTypeImpl;
        List<VcsRootInstanceEntry> list;
        BuildTypeDescriptor.CheckoutType checkoutType2 = this.getCheckoutType();
        try {
            list = this.getVcsRootInstanceEntries();
            buildTypeImpl = this;
            string2 = null;
            checkoutType = checkoutType2 != BuildTypeDescriptor.CheckoutType.AUTO ? checkoutType2 : BuildTypeDescriptor.CheckoutType.ON_AGENT;
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            string = Long.toHexString(Hash.calc((String)BuildTypeImpl.computeSignature(list, (SBuildType)buildTypeImpl, string2, checkoutType)));
            if (string == null) {
                BuildTypeImpl.$$$reportNull$$$0(297);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return string;
    }

    @NotNull
    public String getVcsRootsHash(BuildTypeDescriptor.CheckoutType checkoutType) {
        String string;
        try {
            if (checkoutType == BuildTypeDescriptor.CheckoutType.AUTO) {
                throw new IllegalArgumentException("Vcs roots hash can't be calculated for auto checkout type.");
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            string = BuildTypeImpl.getDefaultVcsSettingsHash(this.getVcsRootInstanceEntries(), (SBuildType)this, checkoutType);
            if (string == null) {
                BuildTypeImpl.$$$reportNull$$$0(298);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return string;
    }

    static String getDefaultVcsSettingsHash(@NotNull List<VcsRootInstanceEntry> list, @NotNull SBuildType sBuildType, BuildTypeDescriptor.CheckoutType checkoutType) {
        try {
            if (list == null) {
                BuildTypeImpl.$$$reportNull$$$0(299);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            if (sBuildType == null) {
                BuildTypeImpl.$$$reportNull$$$0(300);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return Long.toHexString(Hash.calc((String)BuildTypeImpl.computeSignature(list, sBuildType, null, checkoutType)));
    }

    private static String computeSignature(List<VcsRootInstanceEntry> list, SBuildType sBuildType, @Nullable String string, BuildTypeDescriptor.CheckoutType checkoutType) {
        StringBuilder stringBuilder;
        block16: {
            block14: {
                block15: {
                    try {
                        if (checkoutType == BuildTypeDescriptor.CheckoutType.AUTO) {
                            throw new IllegalArgumentException("Vcs settings hash can't be calculated for AUTO checkout type.");
                        }
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildTypeImpl.a(illegalStateException);
                    }
                    stringBuilder = new StringBuilder();
                    try {
                        if (string != null) {
                            stringBuilder.append(string);
                        }
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildTypeImpl.a(illegalStateException);
                    }
                    try {
                        try {
                            stringBuilder.append(checkoutType);
                            if (list.size() != 0) break block14;
                            if (!sBuildType.isPersonal()) break block15;
                        }
                        catch (IllegalStateException illegalStateException) {
                            throw BuildTypeImpl.a(illegalStateException);
                        }
                        stringBuilder.append(((RemoteBuildType)sBuildType).getSourceBuildType().getBuildTypeId());
                        break block16;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildTypeImpl.a(illegalStateException);
                    }
                }
                stringBuilder.append(sBuildType.getBuildTypeId());
                break block16;
            }
            ArrayList<String> arrayList = new ArrayList<String>(list.size());
            for (VcsRootInstanceEntry object : list) {
                boolean bl;
                VcsRootInstanceEntry vcsRootInstanceEntry;
                ArrayList<String> arrayList2;
                try {
                    arrayList2 = arrayList;
                    vcsRootInstanceEntry = object;
                    bl = checkoutType == BuildTypeDescriptor.CheckoutType.ON_SERVER;
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildTypeImpl.a(illegalStateException);
                }
                arrayList2.add(vcsRootInstanceEntry.getSignature(bl));
            }
            Collections.sort(arrayList);
            for (String string2 : arrayList) {
                stringBuilder.append(string2);
            }
        }
        return stringBuilder.toString();
    }

    public void releaseSources() {
        this.myContext.getCleanCheckoutEnforcer().enforceCleanCheckoutForBuildType((BuildType)this);
    }

    public void releaseSources(@NotNull SBuildAgent sBuildAgent) {
        try {
            if (sBuildAgent == null) {
                BuildTypeImpl.$$$reportNull$$$0(301);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        this.myContext.getCleanCheckoutEnforcer().enforceCleanCheckout((BuildType)this, (BuildAgent)sBuildAgent);
    }

    @NotNull
    public List<SBuildAgent> getAgentsWhereBuildConfigurationBuilt() {
        Object object;
        BuildAgentEx buildAgentEx;
        HashMap<Integer, BuildAgentEx> hashMap = new HashMap<Integer, BuildAgentEx>();
        List list = (List)this.myContext.getSQLRunner().withDB(dBFunctions -> dBFunctions.queryForStrings("select distinct agent_name from history where build_type_id=? AND is_personal=0 AND agent_type_id > 0", new Object[]{this.getInternalId()}));
        for (String string : list) {
            buildAgentEx = this.myContext.getBuildAgentManager().findAgentByName(string, true);
            try {
                if (buildAgentEx == null) {
                    continue;
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildTypeImpl.a(illegalStateException);
            }
            hashMap.put(buildAgentEx.getId(), buildAgentEx);
        }
        for (SRunningBuild sRunningBuild : this.getRunningBuilds()) {
            buildAgentEx = sRunningBuild.getAgent();
            try {
                if (buildAgentEx.getId() <= 0) {
                    continue;
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildTypeImpl.a(illegalStateException);
            }
            hashMap.put(buildAgentEx.getId(), buildAgentEx);
        }
        ArrayList arrayList = new ArrayList(hashMap.values());
        try {
            Collections.sort(arrayList);
            object = arrayList;
            if (object == null) {
                BuildTypeImpl.$$$reportNull$$$0(302);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return object;
    }

    public List<String> getTags() {
        return TagDataUtil.getPublicTags(this.getTagsData());
    }

    public long getBuildDuration(@NotNull BuildAgent buildAgent) {
        try {
            if (buildAgent == null) {
                BuildTypeImpl.$$$reportNull$$$0(303);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return this.calculateBuildDuration(BuildDurationEstimateContext.forBuildType((String)this.getBuildTypeId()), buildAgent);
    }

    public long getBuildDuration(@NotNull BuildPromotionInfo buildPromotionInfo, @NotNull BuildAgent buildAgent) {
        BuildDurationEstimateContext buildDurationEstimateContext;
        try {
            if (buildPromotionInfo == null) {
                BuildTypeImpl.$$$reportNull$$$0(304);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            if (buildAgent == null) {
                BuildTypeImpl.$$$reportNull$$$0(305);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        if (buildPromotionInfo instanceof BuildPromotionEx) {
            BuildPromotionEx buildPromotionEx = (BuildPromotionEx)buildPromotionInfo;
            buildDurationEstimateContext = BuildDurationEstimateContext.fromBuildPromotion((BuildPromotionEx)buildPromotionEx);
        } else {
            buildDurationEstimateContext = BuildDurationEstimateContext.forBuildType((String)this.getBuildTypeId());
        }
        BuildDurationEstimateContext buildDurationEstimateContext2 = buildDurationEstimateContext;
        return this.calculateBuildDuration(buildDurationEstimateContext2, buildAgent);
    }

    private long calculateBuildDuration(@NotNull BuildDurationEstimateContext buildDurationEstimateContext, @NotNull BuildAgent buildAgent) {
        try {
            if (buildDurationEstimateContext == null) {
                BuildTypeImpl.$$$reportNull$$$0(306);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            if (buildAgent == null) {
                BuildTypeImpl.$$$reportNull$$$0(307);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        if (buildAgent instanceof BuildAgentEx) {
            BuildAgentEx buildAgentEx = (BuildAgentEx)buildAgent;
            try {
                if (buildAgentEx.isFakeAgent()) {
                    return this.myContext.getBuildDurationCalculator().calculateBuildDuration(buildDurationEstimateContext);
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildTypeImpl.a(illegalStateException);
            }
        }
        return this.myContext.getBuildDurationCalculator().calculateBuildDuration(buildDurationEstimateContext.withAgent(buildAgent), buildAgent);
    }

    @NotNull
    public VcsLabelingSettings.LabelingType getLabelingType() {
        VcsLabelingSettings.LabelingType labelingType;
        try {
            labelingType = this.myBuildTypeSettings.getLabelingType();
            if (labelingType == null) {
                BuildTypeImpl.$$$reportNull$$$0(308);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return labelingType;
    }

    @NotNull
    public String getLabelPattern() {
        String string;
        try {
            string = this.myBuildTypeSettings.getLabelPattern();
            if (string == null) {
                BuildTypeImpl.$$$reportNull$$$0(309);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return string;
    }

    public void forceCheckingForChanges() {
        this.forceCheckingForChanges(OperationRequestor.UNKNOWN);
    }

    public void forceCheckingForChanges(@NotNull OperationRequestor operationRequestor) {
        try {
            if (operationRequestor == null) {
                BuildTypeImpl.$$$reportNull$$$0(310);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        HashSet<VcsRootInstance> hashSet = new HashSet<VcsRootInstance>(this.getVcsRootInstances());
        this.traverseDependencies((DependencyConsumer<BuildTypeEx>)((DependencyConsumer)buildTypeEx -> {
            hashSet.addAll(buildTypeEx.getVcsRootInstances());
            return DependencyConsumer.Result.CONTINUE;
        }));
        this.myContext.getChangesCheckingService().forceCheckingFor(hashSet, operationRequestor);
    }

    @NotNull
    public BuildPromotionEx createBuildPromotion() {
        return this.createPromotionGraph(null, null, null);
    }

    @NotNull
    public BuildPromotionEx createBuildPromotion(@NotNull String string) {
        try {
            if (string == null) {
                BuildTypeImpl.$$$reportNull$$$0(311);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return this.createPromotionGraph(string, null, null);
    }

    @NotNull
    public BuildPromotionEx createBuildPromotion(@Nullable String string, @Nullable BuildPromotionInitializer buildPromotionInitializer) {
        return this.createPromotionGraph(string, buildPromotionInitializer, null);
    }

    @NotNull
    public BuildPromotionEx createBuildPromotion(@Nullable String string, @Nullable BuildPromotionInitializer buildPromotionInitializer, @Nullable DependenciesSupplier dependenciesSupplier) {
        return this.createPromotionGraph(string, buildPromotionInitializer, dependenciesSupplier);
    }

    @NotNull
    public BuildCustomizer createBuildCustomizer(@Nullable SUser sUser) {
        BuildCustomizer buildCustomizer;
        try {
            buildCustomizer = this.myContext.getBuildCustomizerFactory().createBuildCustomizer((SBuildType)this, sUser);
            if (buildCustomizer == null) {
                BuildTypeImpl.$$$reportNull$$$0(312);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return buildCustomizer;
    }

    @NotNull
    private BuildPromotionEx createPromotionGraph(@Nullable String string, @Nullable BuildPromotionInitializer buildPromotionInitializer, @Nullable DependenciesSupplier dependenciesSupplier) {
        BuildPromotionEx buildPromotionEx;
        Object object;
        Object object2;
        try {
            object2 = string != null ? " in branch " + string : "";
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        Object object3 = object2;
        try {
            object = buildPromotionInitializer != null ? " using initializer " + String.valueOf(buildPromotionInitializer) : "";
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        String string2 = object;
        try {
            buildPromotionEx = (BuildPromotionEx)NamedThreadFactory.executeWithNewThreadNameFuncThrow((String)("Creating build promotion for build type " + String.valueOf((Object)this) + (String)object3 + string2), () -> {
                BuildPromotionGraphFactory buildPromotionGraphFactory = new BuildPromotionGraphFactory(this.myContext.getSecurityContext(), (ProjectManager)this.myContext.getProjectManager(), (buildTypeEx, collection) -> this.myContext.getBuildPromotionFactory().createNewPromotion((SBuildType)buildTypeEx));
                BuildPromotionEx buildPromotionEx = dependenciesSupplier == null ? buildPromotionGraphFactory.createGraph((BuildTypeEx)this) : buildPromotionGraphFactory.createGraph((BuildTypeEx)this, dependenciesSupplier);
                BuildTypeImpl.postProcessCreatedGraph(buildPromotionEx, string, buildPromotionInitializer, this.myContext.getSecurityContext(), this.myContext.getBuildTypeIdentityIdentifiersManager(), this.myContext.getSettingsRootManager(), this.myContext.getParameterFactory());
                return buildPromotionEx;
            });
            if (buildPromotionEx == null) {
                BuildTypeImpl.$$$reportNull$$$0(313);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return buildPromotionEx;
    }

    static void postProcessCreatedGraph(@NotNull BuildPromotionEx buildPromotionEx, @Nullable String string, @Nullable BuildPromotionInitializer buildPromotionInitializer, @NotNull SecurityContextEx securityContextEx, @NotNull BuildTypeIdentifiersManager buildTypeIdentifiersManager, @NotNull VersionedSettingsManager versionedSettingsManager, @NotNull ParameterFactory parameterFactory) {
        try {
            if (buildPromotionEx == null) {
                BuildTypeImpl.$$$reportNull$$$0(314);
            }
        }
        catch (Throwable throwable) {
            throw BuildTypeImpl.a(throwable);
        }
        try {
            if (securityContextEx == null) {
                BuildTypeImpl.$$$reportNull$$$0(315);
            }
        }
        catch (Throwable throwable) {
            throw BuildTypeImpl.a(throwable);
        }
        try {
            if (buildTypeIdentifiersManager == null) {
                BuildTypeImpl.$$$reportNull$$$0(316);
            }
        }
        catch (Throwable throwable) {
            throw BuildTypeImpl.a(throwable);
        }
        try {
            if (versionedSettingsManager == null) {
                BuildTypeImpl.$$$reportNull$$$0(317);
            }
        }
        catch (Throwable throwable) {
            throw BuildTypeImpl.a(throwable);
        }
        try {
            if (parameterFactory == null) {
                BuildTypeImpl.$$$reportNull$$$0(318);
            }
        }
        catch (Throwable throwable) {
            throw BuildTypeImpl.a(throwable);
        }
        try {
            securityContextEx.runAsSystem(() -> {
                try {
                    BuildTypeImpl.setBranchNameToPromotionGraph(buildPromotionEx, string);
                    if (buildPromotionInitializer != null) {
                        buildPromotionEx.traverseSelfAndDependencies(buildPromotionEx -> {
                            buildPromotionInitializer.init(buildPromotionEx);
                            return DependencyConsumer.Result.CONTINUE;
                        });
                    }
                }
                catch (Throwable throwable) {
                    throw BuildTypeImpl.a(throwable);
                }
                new ReverseDepParametersProcessor(buildTypeIdentifiersManager, versionedSettingsManager, parameterFactory).buildPromotionGraphCreated(buildPromotionEx);
            });
        }
        catch (Throwable throwable) {
            ExceptionUtil.rethrowAsRuntimeException((Throwable)throwable);
        }
    }

    private static void setBranchNameToPromotionGraph(@NotNull BuildPromotionEx buildPromotionEx, @Nullable String string) {
        boolean bl;
        block8: {
            block7: {
                try {
                    if (buildPromotionEx == null) {
                        BuildTypeImpl.$$$reportNull$$$0(319);
                    }
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildTypeImpl.a(illegalStateException);
                }
                try {
                    try {
                        if (string != null && !"<default>".equals(string)) break block7;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildTypeImpl.a(illegalStateException);
                    }
                    bl = true;
                    break block8;
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildTypeImpl.a(illegalStateException);
                }
            }
            bl = false;
        }
        boolean bl2 = bl;
        buildPromotionEx.traverseSelfAndDependencies(buildPromotionEx2 -> {
            String string2;
            block11: {
                block13: {
                    block14: {
                        BuildTypeEx buildTypeEx;
                        block12: {
                            string2 = string;
                            buildTypeEx = buildPromotionEx2.getParentBuildType();
                            try {
                                try {
                                    if (!bl2) break block11;
                                    if (buildTypeEx != null) break block12;
                                }
                                catch (IllegalStateException illegalStateException) {
                                    throw BuildTypeImpl.a(illegalStateException);
                                }
                                return DependencyConsumer.Result.CONTINUE;
                            }
                            catch (IllegalStateException illegalStateException) {
                                throw BuildTypeImpl.a(illegalStateException);
                            }
                        }
                        try {
                            try {
                                if (buildTypeEx.isBranchSpecDefined(false)) break block13;
                                if (buildTypeEx.isVirtual()) break block14;
                            }
                            catch (IllegalStateException illegalStateException) {
                                throw BuildTypeImpl.a(illegalStateException);
                            }
                            return DependencyConsumer.Result.CONTINUE;
                        }
                        catch (IllegalStateException illegalStateException) {
                            throw BuildTypeImpl.a(illegalStateException);
                        }
                    }
                    try {
                        if (buildPromotionEx.getAttribute("teamcity.build.desiredBranchName") == null) {
                            return DependencyConsumer.Result.CONTINUE;
                        }
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildTypeImpl.a(illegalStateException);
                    }
                }
                string2 = "<default>";
            }
            buildPromotionEx2.setDesiredBranchName(string2);
            return DependencyConsumer.Result.CONTINUE;
        });
    }

    @NotNull
    public RemoteBuildTypeEx createPersonalBuildType(@NotNull User user, @Nullable String string) {
        RemoteBuildTypeEx remoteBuildTypeEx;
        try {
            if (user == null) {
                BuildTypeImpl.$$$reportNull$$$0(320);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        this.checkPersonalBuildsAllowed();
        long l = this.myContext.getPersonalBuildManager().publishEmptyChangeList(user);
        try {
            remoteBuildTypeEx = this.myContext.getBuildTypeFactory().createRemoteBuildType((BuildTypeEx)this, l, user.getId());
            if (remoteBuildTypeEx == null) {
                BuildTypeImpl.$$$reportNull$$$0(321);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return remoteBuildTypeEx;
    }

    private void checkPersonalBuildsAllowed() {
        try {
            if (!((Boolean)this.getOption(BT_ALLOW_PERSONAL_BUILD_TRIGGERING)).booleanValue()) {
                throw new IllegalPersonalBuildException("Personal builds are not allowed in build configuration with id: " + this.getExternalId());
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
    }

    @NotNull
    public RemoteBuildTypeEx createPersonalBuildType(@NotNull User user, long l) {
        RemoteBuildTypeEx remoteBuildTypeEx;
        try {
            if (user == null) {
                BuildTypeImpl.$$$reportNull$$$0(322);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            this.checkPersonalBuildsAllowed();
            remoteBuildTypeEx = this.myContext.getBuildTypeFactory().createRemoteBuildType((BuildTypeEx)this, l, user.getId());
            if (remoteBuildTypeEx == null) {
                BuildTypeImpl.$$$reportNull$$$0(323);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return remoteBuildTypeEx;
    }

    @NotNull
    public RemoteBuildTypeEx createPersonalBuildType(@NotNull User user, @Nullable String string, @NotNull Map<VcsRootInstance, VcsRootInstance> map) {
        RemoteBuildTypeEx remoteBuildTypeEx;
        try {
            if (user == null) {
                BuildTypeImpl.$$$reportNull$$$0(324);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            if (map == null) {
                BuildTypeImpl.$$$reportNull$$$0(325);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        this.checkPersonalBuildsAllowed();
        RemoteBuildTypeEx remoteBuildTypeEx2 = this.createPersonalBuildType(user, string);
        try {
            remoteBuildTypeEx2.setPromotionCustomizationStrategy(remoteBuildTypeEx2.getOverriddenRootsPromotionStrategy(map));
            remoteBuildTypeEx = remoteBuildTypeEx2;
            if (remoteBuildTypeEx == null) {
                BuildTypeImpl.$$$reportNull$$$0(326);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return remoteBuildTypeEx;
    }

    @NotNull
    public List<VcsRoot> getLabelingRoots() {
        List list;
        try {
            list = this.myBuildTypeSettings.getLabelingRoots();
            if (list == null) {
                BuildTypeImpl.$$$reportNull$$$0(327);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return list;
    }

    @Nullable
    public PathMapping mapVcsPath(@NotNull String string) {
        try {
            if (string == null) {
                BuildTypeImpl.$$$reportNull$$$0(328);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return this.getVcsAccess().mapVcsPath(string);
    }

    private void executeIfInModel(@NotNull Runnable runnable) {
        block6: {
            block5: {
                try {
                    if (runnable == null) {
                        BuildTypeImpl.$$$reportNull$$$0(329);
                    }
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildTypeImpl.a(illegalStateException);
                }
                try {
                    if (this.myAttachedToModel.get()) break block5;
                    this.myDelayedTasks.add(runnable);
                    break block6;
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildTypeImpl.a(illegalStateException);
                }
            }
            runnable.run();
        }
    }

    @NotNull
    public String getExtendedName() {
        return BuildTypeImpl.getFullName(this.myProject.getExtendedName(), this.getName());
    }

    @NotNull
    public String getExtendedFullName() {
        return BuildTypeImpl.getFullName(this.myProject.getExtendedFullName(), this.getName());
    }

    @NotNull
    public byte[] getFileContent(@NotNull String string) throws VcsException {
        byte[] byArray;
        try {
            if (string == null) {
                BuildTypeImpl.$$$reportNull$$$0(330);
            }
        }
        catch (VcsException vcsException) {
            throw BuildTypeImpl.a(vcsException);
        }
        try {
            byArray = this.getVcsAccess().getFileContent(string);
            if (byArray == null) {
                BuildTypeImpl.$$$reportNull$$$0(331);
            }
        }
        catch (VcsException vcsException) {
            throw BuildTypeImpl.a(vcsException);
        }
        return byArray;
    }

    @NotNull
    public byte[] getFileContent(@NotNull String string, boolean bl) throws VcsException {
        byte[] byArray;
        try {
            if (string == null) {
                BuildTypeImpl.$$$reportNull$$$0(332);
            }
        }
        catch (VcsException vcsException) {
            throw BuildTypeImpl.a(vcsException);
        }
        try {
            byArray = this.getVcsAccess().getFileContent(string, bl);
            if (byArray == null) {
                BuildTypeImpl.$$$reportNull$$$0(333);
            }
        }
        catch (VcsException vcsException) {
            throw BuildTypeImpl.a(vcsException);
        }
        return byArray;
    }

    protected void beforeParameterModification(@NotNull String string) {
        try {
            if (string == null) {
                BuildTypeImpl.$$$reportNull$$$0(334);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        ReadOnlyUtil.checkParameterModificationAllowed((SPersistentEntity)this, (String)string);
    }

    public void addConfigParameter(@NotNull Parameter parameter) {
        try {
            if (parameter == null) {
                BuildTypeImpl.$$$reportNull$$$0(335);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        this.beforeParameterModification(parameter.getName());
        this.myProject.getEnforcedSettings().validate(parameter);
        this.markEdited();
        this.myBuildTypeSettings.addConfigParameter(parameter);
    }

    public void removeConfigParameter(@NotNull String string) {
        try {
            if (string == null) {
                BuildTypeImpl.$$$reportNull$$$0(336);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        this.beforeParameterModification(string);
        this.markEdited();
        this.myBuildTypeSettings.removeConfigParameter(string);
    }

    public void addParameter(@NotNull Parameter parameter) {
        try {
            if (parameter == null) {
                BuildTypeImpl.$$$reportNull$$$0(337);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        this.beforeParameterModification(parameter.getName());
        this.myProject.getEnforcedSettings().validate(parameter);
        this.markEdited();
        this.myBuildTypeSettings.addParameter(parameter);
    }

    public void removeParameter(@NotNull String string) {
        try {
            if (string == null) {
                BuildTypeImpl.$$$reportNull$$$0(338);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        this.beforeParameterModification(string);
        this.markEdited();
        this.myBuildTypeSettings.removeParameter(string);
    }

    @NotNull
    public Collection<Parameter> getParametersCollection() {
        Collection collection;
        try {
            collection = this.myBuildTypeSettings.getParametersCollection();
            if (collection == null) {
                BuildTypeImpl.$$$reportNull$$$0(339);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return collection;
    }

    @Nullable
    public Parameter getParameter(@NotNull String string) {
        try {
            if (string == null) {
                BuildTypeImpl.$$$reportNull$$$0(340);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return this.myBuildTypeSettings.getParameter(string);
    }

    @NotNull
    public Collection<Parameter> getConfigParametersCollection() {
        Collection collection;
        try {
            collection = this.myBuildTypeSettings.getConfigParametersCollection();
            if (collection == null) {
                BuildTypeImpl.$$$reportNull$$$0(341);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return collection;
    }

    @NotNull
    public Map<String, String> getConfigParameters() {
        Map map;
        try {
            map = this.myBuildTypeSettings.getConfigParameters();
            if (map == null) {
                BuildTypeImpl.$$$reportNull$$$0(342);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return map;
    }

    @NotNull
    public List<String> getUndefinedParameters() {
        List list;
        try {
            list = this.myBuildTypeSettings.getUndefinedParameters();
            if (list == null) {
                BuildTypeImpl.$$$reportNull$$$0(343);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return list;
    }

    public boolean isTemplateBased() {
        return this.myBuildTypeSettings.isTemplateBased();
    }

    @Deprecated
    public BuildTypeTemplateEx getTemplate() {
        BuildTypeTemplateEx buildTypeTemplateEx;
        List list = this.myBuildTypeSettings.getOwnTemplates();
        try {
            buildTypeTemplateEx = list.isEmpty() ? null : (BuildTypeTemplateEx)list.iterator().next();
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return buildTypeTemplateEx;
    }

    @NotNull
    public List<BuildTypeTemplateEx> getTemplates() {
        List list;
        try {
            list = this.myBuildTypeSettings.getTemplates();
            if (list == null) {
                BuildTypeImpl.$$$reportNull$$$0(344);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return list;
    }

    @NotNull
    public List<String> getTemplateIds() {
        List list;
        try {
            list = this.myBuildTypeSettings.getTemplateIds();
            if (list == null) {
                BuildTypeImpl.$$$reportNull$$$0(345);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return list;
    }

    @NotNull
    public List<String> getOwnTemplateIds() {
        List list;
        try {
            list = this.myBuildTypeSettings.getOwnTemplateIds();
            if (list == null) {
                BuildTypeImpl.$$$reportNull$$$0(346);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return list;
    }

    @Deprecated
    public String getTemplateId() {
        List list = this.myBuildTypeSettings.getTemplateIds();
        try {
            if (list.isEmpty()) {
                return null;
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return (String)list.get(0);
    }

    public void attachToTemplate(@NotNull BuildTypeTemplate buildTypeTemplate, boolean bl) throws InvalidVcsRootScopeException {
        try {
            if (buildTypeTemplate == null) {
                BuildTypeImpl.$$$reportNull$$$0(347);
            }
        }
        catch (InvalidVcsRootScopeException invalidVcsRootScopeException) {
            throw BuildTypeImpl.a(invalidVcsRootScopeException);
        }
        this.setTemplates(Collections.singletonList(buildTypeTemplate), true);
    }

    public void addTemplate(@NotNull BuildTypeTemplate buildTypeTemplate, boolean bl) {
        try {
            if (buildTypeTemplate == null) {
                BuildTypeImpl.$$$reportNull$$$0(348);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        this.beforeModification();
        this.checkTemplateProject(buildTypeTemplate);
        ArrayList<BuildTypeTemplateEx> arrayList = new ArrayList<BuildTypeTemplateEx>(this.myBuildTypeSettings.getOwnTemplates());
        arrayList.add((BuildTypeTemplateEx)buildTypeTemplate);
        BuildTypeSettingsEx buildTypeSettingsEx = this.getNewTemplateBasedSettings(arrayList, bl);
        this.markEdited();
        this.changeSettingsTo(buildTypeSettingsEx);
    }

    public void setTemplates(@NotNull List<? extends BuildTypeTemplate> list, boolean bl) {
        try {
            if (list == null) {
                BuildTypeImpl.$$$reportNull$$$0(349);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        list.forEach(buildTypeTemplate -> this.checkTemplateProject((BuildTypeTemplate)buildTypeTemplate));
        this.beforeModification();
        BuildTypeSettingsEx buildTypeSettingsEx = this.getNewTemplateBasedSettings(list, bl);
        this.markEdited();
        this.changeSettingsTo(buildTypeSettingsEx);
    }

    public void setSettings(@NotNull BuildTypeSettingsEx buildTypeSettingsEx) {
        try {
            if (buildTypeSettingsEx == null) {
                BuildTypeImpl.$$$reportNull$$$0(350);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        BuildTypeSettingsEx buildTypeSettingsEx2 = buildTypeSettingsEx.createCopy();
        buildTypeSettingsEx2.setProject(this.getProject());
        this.changeSettingsTo(buildTypeSettingsEx2);
    }

    public void attachToTemplate(@NotNull BuildTypeTemplate buildTypeTemplate) throws CannotAttachToTemplateException {
        try {
            if (buildTypeTemplate == null) {
                BuildTypeImpl.$$$reportNull$$$0(351);
            }
        }
        catch (CannotAttachToTemplateException cannotAttachToTemplateException) {
            throw BuildTypeImpl.a(cannotAttachToTemplateException);
        }
        this.setTemplates(Collections.singletonList((BuildTypeTemplateEx)buildTypeTemplate), true);
    }

    private BuildTypeSettingsEx getNewTemplateBasedSettings(@NotNull List<BuildTypeTemplateEx> list, boolean bl) {
        BuildTypeSettingsEx buildTypeSettingsEx;
        try {
            if (list == null) {
                BuildTypeImpl.$$$reportNull$$$0(352);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        if (bl) {
            buildTypeSettingsEx = this.myContext.getBuildTypeSettingsFactory().createTemplateBasedSettings(this.getProject(), list);
            buildTypeSettingsEx.copySettings(this.myBuildTypeSettings, new BuildTypeCopySettings(EnumSet.of(BuildTypeSettingsCopyOptions.COPY_OWN_ONLY, BuildTypeSettingsCopyOptions.PRESERVE_OVERRIDING)));
        } else {
            buildTypeSettingsEx = this.myBuildTypeSettings;
            buildTypeSettingsEx.setTemplateIds(list.stream().map(BuildTypeTemplate::getId).distinct().collect(Collectors.toList()));
        }
        return buildTypeSettingsEx;
    }

    private void checkTemplateProject(@NotNull BuildTypeTemplate buildTypeTemplate) {
        try {
            if (buildTypeTemplate == null) {
                BuildTypeImpl.$$$reportNull$$$0(353);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            if (TeamCityProperties.getBoolean((String)"teamcity.template.ignoreDeps")) {
                return;
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            if (!buildTypeTemplate.isAccessibleFrom((SProject)this.getProject())) {
                throw new CannotAttachToTemplateException("Build configuration cannot be attached to this template because template does not belong to build configuration project or parent projects");
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
    }

    public void removeTemplates(@NotNull Collection<? extends BuildTypeTemplate> collection, boolean bl) {
        BuildTypeSettingsImpl buildTypeSettingsImpl;
        BuildTypeSettingsImpl buildTypeSettingsImpl2;
        List list;
        block14: {
            block12: {
                block13: {
                    try {
                        if (collection == null) {
                            BuildTypeImpl.$$$reportNull$$$0(354);
                        }
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildTypeImpl.a(illegalStateException);
                    }
                    try {
                        this.beforeModification();
                        if (!this.isTemplateBased()) {
                            return;
                        }
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildTypeImpl.a(illegalStateException);
                    }
                    list = this.myBuildTypeSettings.getOwnTemplates().stream().filter(buildTypeTemplateEx -> {
                        boolean bl;
                        try {
                            bl = !collection.contains(buildTypeTemplateEx);
                        }
                        catch (IllegalStateException illegalStateException) {
                            throw BuildTypeImpl.a(illegalStateException);
                        }
                        return bl;
                    }).collect(Collectors.toList());
                    buildTypeSettingsImpl2 = this.myContext.getBuildTypeSettingsFactory().createProjectBasedSettings(this.getProject());
                    try {
                        try {
                            if (!bl) break block12;
                            if (!CleanupProperties.keepRulesInTemplatesSupported()) break block13;
                        }
                        catch (IllegalStateException illegalStateException) {
                            throw BuildTypeImpl.a(illegalStateException);
                        }
                        buildTypeSettingsImpl2.copySettings(this.myBuildTypeSettings, new BuildTypeCopySettings(EnumSet.of(BuildTypeSettingsCopyOptions.PRESERVE_OVERRIDING)));
                        break block14;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildTypeImpl.a(illegalStateException);
                    }
                }
                buildTypeSettingsImpl2.copySettings(this.myBuildTypeSettings, new BuildTypeCopySettings(EnumSet.of(BuildTypeSettingsCopyOptions.PRESERVE_OVERRIDING, BuildTypeSettingsCopyOptions.IGNORE_CLEANUP_KEEP_RULES)));
                buildTypeSettingsImpl2.copySettings(this.myBuildTypeSettings, new BuildTypeCopySettings(EnumSet.of(BuildTypeSettingsCopyOptions.COPY_OWN_ONLY, BuildTypeSettingsCopyOptions.PRESERVE_OVERRIDING, BuildTypeSettingsCopyOptions.COPY_CLEANUP_KEEP_RULES_ONLY)));
                break block14;
            }
            buildTypeSettingsImpl2.copySettings(this.myBuildTypeSettings, new BuildTypeCopySettings(EnumSet.of(BuildTypeSettingsCopyOptions.COPY_OWN_ONLY)));
        }
        if (list.isEmpty()) {
            buildTypeSettingsImpl = buildTypeSettingsImpl2;
        } else {
            buildTypeSettingsImpl = this.myContext.getBuildTypeSettingsFactory().createTemplateBasedSettings(this.getProject(), list);
            buildTypeSettingsImpl.copySettings((BuildTypeSettingsEx)buildTypeSettingsImpl2, new BuildTypeCopySettings(EnumSet.of(BuildTypeSettingsCopyOptions.COPY_OWN_ONLY, BuildTypeSettingsCopyOptions.PRESERVE_OVERRIDING)));
        }
        this.changeSettingsTo((BuildTypeSettingsEx)buildTypeSettingsImpl);
        this.markEdited();
    }

    public void detachFromTemplate() {
        this.removeTemplates(this.myBuildTypeSettings.getOwnTemplates(), true);
    }

    public void setTemplatesOrder(List<String> list) {
        block2: {
            try {
                this.beforeModification();
                if (!this.isTemplateBased() || !(this.myBuildTypeSettings instanceof BuildTypeSettingsImpl)) break block2;
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildTypeImpl.a(illegalStateException);
            }
            BuildTypeSettingsImpl buildTypeSettingsImpl = (BuildTypeSettingsImpl)this.myBuildTypeSettings;
            buildTypeSettingsImpl.setTemplatesOrder(list);
            this.markEdited();
        }
    }

    public List<BuildTypeTemplateEx> getOwnTemplates() {
        return this.myBuildTypeSettings.getOwnTemplates();
    }

    @NotNull
    public BuildTypeEx inline() {
        BuildTypeImpl buildTypeImpl;
        block7: {
            BuildTypeEx buildTypeEx;
            block8: {
                try {
                    try {
                        if (!this.getProject().isPipeline()) break block7;
                        buildTypeEx = this.inlinePipeline(((PipelineProject)this.getProject()).getPipelineYaml());
                        if (buildTypeEx != null) break block8;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildTypeImpl.a(illegalStateException);
                    }
                    BuildTypeImpl.$$$reportNull$$$0(355);
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildTypeImpl.a(illegalStateException);
                }
            }
            return buildTypeEx;
        }
        BuildTypeSettingsImpl buildTypeSettingsImpl = this.myContext.getBuildTypeSettingsFactory().createEmptySettings(this.getProject());
        buildTypeSettingsImpl.copyAllSettings(this.myBuildTypeSettings);
        BuildTypeImpl buildTypeImpl2 = new BuildTypeImpl(this.myContext, (EntityId<String>)this.myEntityId, this.myName, (BuildTypeSettingsEx)buildTypeSettingsImpl, this.myProject, this.myEmptyUuid);
        try {
            buildTypeImpl2.getSettings().setSecureDataStorage((SecureDataStorage)new /* Unavailable Anonymous Inner Class!! */);
            buildTypeImpl = buildTypeImpl2;
            if (buildTypeImpl == null) {
                BuildTypeImpl.$$$reportNull$$$0(356);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return buildTypeImpl;
    }

    @NotNull
    public BuildTypeEx inlinePipeline(@NotNull String string) {
        BuildTypeImpl buildTypeImpl;
        try {
            if (string == null) {
                BuildTypeImpl.$$$reportNull$$$0(357);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            if (!this.getProject().isPipeline()) {
                throw new IllegalArgumentException("Cannot inline pipeline build type into non-pipeline project");
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        BuildTypeSettingsEx buildTypeSettingsEx = this.myBuildTypeSettings;
        buildTypeSettingsEx = this.getSettings().createCopy();
        PipelineProject pipelineProject = (PipelineProject)this.getProject();
        BuildTypeImpl.saveYamlAsBuildFeature(buildTypeSettingsEx, string);
        PipelineDescription pipelineDescription = this.myContext.getPipelineYamlParser().parse(string);
        for (Map.Entry object22 : pipelineDescription.parameters.entrySet()) {
            buildTypeSettingsEx.addParameter((Parameter)new SimpleParameter((String)object22.getKey(), (String)object22.getValue()));
        }
        for (Map.Entry entry : pipelineDescription.secrets.entrySet()) {
            String string2 = this.getProject().getSecureValue((String)entry.getValue(), "parameter with name '" + (String)entry.getKey() + "' in pipeline with name '" + pipelineProject.getName() + "'");
            buildTypeSettingsEx.addParameter(this.myContext.getParameterFactory().createTypedParameter((String)entry.getKey(), string2, new PasswordType().getParameterType()));
        }
        this.setPipelineBuildTypeOptions(buildTypeSettingsEx, pipelineDescription);
        BuildTypeSettingsImpl buildTypeSettingsImpl = this.myContext.getBuildTypeSettingsFactory().createEmptySettings(this.getProject());
        buildTypeSettingsImpl.copyAllSettings(buildTypeSettingsEx);
        BuildTypeImpl buildTypeImpl2 = new BuildTypeImpl(this.myContext, (EntityId<String>)this.myEntityId, this.myName, (BuildTypeSettingsEx)buildTypeSettingsImpl, this.myProject, this.myEmptyUuid);
        try {
            buildTypeImpl2.getSettings().setSecureDataStorage((SecureDataStorage)new /* Unavailable Anonymous Inner Class!! */);
            buildTypeImpl = buildTypeImpl2;
            if (buildTypeImpl == null) {
                BuildTypeImpl.$$$reportNull$$$0(358);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return buildTypeImpl;
    }

    public static void saveYamlAsBuildFeature(@NotNull BuildTypeSettingsEx buildTypeSettingsEx, @NotNull String string) {
        try {
            if (buildTypeSettingsEx == null) {
                BuildTypeImpl.$$$reportNull$$$0(359);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            if (string == null) {
                BuildTypeImpl.$$$reportNull$$$0(360);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        buildTypeSettingsEx.addBuildFeature("tc-pipeline", Collections.singletonMap("yaml", string));
    }

    public void unregister() {
        block10: {
            try {
                this.markEdited();
                if (this.myContext.getServerResponsibility().canManageBuilds()) {
                    this.myContext.getQueue().removeAllFromQueue(this.getInternalId());
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildTypeImpl.a(illegalStateException);
            }
            try {
                try {
                    this.myProject.getProjectDataModel().removeBuildType(this.getInternalId());
                    ((BuildServerListener)this.myContext.getEventDispatcher().getMulticaster()).buildTypeUnregistered((SBuildType)this);
                    if (!this.myContext.getServerResponsibility().canManageProjectsConfiguration() || this.isPaused()) break block10;
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildTypeImpl.a(illegalStateException);
                }
                this.deactivateBuildTriggers(null, true);
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildTypeImpl.a(illegalStateException);
            }
        }
        try {
            this.dispose();
            if (this.myContext.getServerResponsibility().canCheckForChanges()) {
                this.myContext.getVcsSettingsTracker().forgetSettingsRevisions((SBuildType)this);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
    }

    protected boolean isTemplate() {
        return false;
    }

    protected void logRemovingToAudit(@NotNull ConfigAction configAction) {
        try {
            if (configAction == null) {
                BuildTypeImpl.$$$reportNull$$$0(361);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        this.myContext.getAuditLogFactory().createForProject((SProject)this.getProject()).logUserAction(configAction, ActionType.BUILD_TYPE_DELETE, configAction.getDescription(), null, new Object[]{this});
    }

    @Used(value="jsp")
    public boolean isTemplateAccessible() {
        return this.myBuildTypeSettings.isTemplateAccessible();
    }

    public boolean replaceInValues(@NotNull Pattern pattern, @NotNull String string) {
        try {
            if (pattern == null) {
                BuildTypeImpl.$$$reportNull$$$0(362);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            if (string == null) {
                BuildTypeImpl.$$$reportNull$$$0(363);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        this.beforeModification();
        boolean bl = this.myBuildTypeSettings.replaceInValues(pattern, string);
        try {
            if (bl) {
                this.markEdited();
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return bl;
    }

    public boolean textValueMatches(@NotNull Pattern pattern) {
        try {
            if (pattern == null) {
                BuildTypeImpl.$$$reportNull$$$0(364);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return this.myBuildTypeSettings.textValueMatches(pattern);
    }

    public boolean replaceInValues(@NotNull String string, @NotNull String string2) {
        try {
            if (string == null) {
                BuildTypeImpl.$$$reportNull$$$0(365);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            if (string2 == null) {
                BuildTypeImpl.$$$reportNull$$$0(366);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        this.beforeModification();
        boolean bl = this.myBuildTypeSettings.replaceInValues(string, string2);
        try {
            if (bl) {
                this.markEdited();
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return bl;
    }

    @NotNull
    public SBuildFeatureDescriptor addBuildFeature(@NotNull String string, @NotNull Map<String, String> map) {
        SBuildFeatureDescriptor sBuildFeatureDescriptor;
        try {
            if (string == null) {
                BuildTypeImpl.$$$reportNull$$$0(367);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            if (map == null) {
                BuildTypeImpl.$$$reportNull$$$0(368);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            this.beforeModification();
            this.markEdited();
            sBuildFeatureDescriptor = this.myBuildTypeSettings.addBuildFeature(string, map);
            if (sBuildFeatureDescriptor == null) {
                BuildTypeImpl.$$$reportNull$$$0(369);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return sBuildFeatureDescriptor;
    }

    public void addBuildFeature(@NotNull SBuildFeatureDescriptor sBuildFeatureDescriptor) {
        try {
            if (sBuildFeatureDescriptor == null) {
                BuildTypeImpl.$$$reportNull$$$0(370);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        this.beforeModification();
        this.markEdited();
        this.myBuildTypeSettings.addBuildFeature(sBuildFeatureDescriptor);
    }

    @NotNull
    public Collection<SBuildFeatureDescriptor> getBuildFeatures() {
        Collection collection;
        try {
            collection = this.myBuildTypeSettings.getBuildFeatures();
            if (collection == null) {
                BuildTypeImpl.$$$reportNull$$$0(371);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return collection;
    }

    @NotNull
    public Collection<SBuildFeatureDescriptor> getBuildFeaturesOfType(@NotNull String string) {
        Collection collection;
        try {
            if (string == null) {
                BuildTypeImpl.$$$reportNull$$$0(372);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            collection = this.myBuildTypeSettings.getBuildFeaturesOfType(string);
            if (collection == null) {
                BuildTypeImpl.$$$reportNull$$$0(373);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return collection;
    }

    public SBuildFeatureDescriptor removeBuildFeature(@NotNull String string) {
        try {
            if (string == null) {
                BuildTypeImpl.$$$reportNull$$$0(374);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        this.beforeModification();
        SBuildFeatureDescriptor sBuildFeatureDescriptor = this.myBuildTypeSettings.removeBuildFeature(string);
        try {
            if (sBuildFeatureDescriptor != null) {
                this.markEdited();
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return sBuildFeatureDescriptor;
    }

    public boolean updateBuildFeature(@NotNull String string, @NotNull String string2, @NotNull Map<String, String> map) {
        try {
            if (string == null) {
                BuildTypeImpl.$$$reportNull$$$0(375);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            if (string2 == null) {
                BuildTypeImpl.$$$reportNull$$$0(376);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            if (map == null) {
                BuildTypeImpl.$$$reportNull$$$0(377);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        this.beforeModification();
        boolean bl = this.myBuildTypeSettings.updateBuildFeature(string, string2, map);
        try {
            if (bl) {
                this.markEdited();
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return bl;
    }

    @Nullable
    public SBuildFeatureDescriptor findBuildFeatureById(@NotNull String string) {
        try {
            if (string == null) {
                BuildTypeImpl.$$$reportNull$$$0(378);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return this.myBuildTypeSettings.findBuildFeatureById(string);
    }

    public void setEnabled(@NotNull String string, boolean bl) {
        try {
            if (string == null) {
                BuildTypeImpl.$$$reportNull$$$0(379);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        this.beforeModification();
        this.myProject.getEnforcedSettings().validateParameterDescriptorState(string, bl);
        this.markEdited();
        this.myBuildTypeSettings.setEnabled(string, bl);
        this.setDirtyCompatibility();
    }

    public boolean isEnabled(@NotNull String string) {
        try {
            if (string == null) {
                BuildTypeImpl.$$$reportNull$$$0(380);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return this.myBuildTypeSettings.isEnabled(string);
    }

    public void removeAllBuildRunners() {
        this.beforeModification();
        this.markEdited();
        this.myBuildTypeSettings.removeAllBuildRunners();
    }

    @Nullable
    public AuditLogAction getLastConfigModificationAction() {
        return this.myContext.getAuditLogProvider().findLastAction(this.auditLog().getObject(), ActionTypeSet.BUILD_TYPE_EDIT_SETTINGS);
    }

    @Nullable
    public VcsRootInstance getVcsRootInstanceForParent(@NotNull SVcsRoot sVcsRoot) {
        VcsRootInstance vcsRootInstance;
        try {
            if (sVcsRoot == null) {
                BuildTypeImpl.$$$reportNull$$$0(381);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        VcsRootInstanceEntry vcsRootInstanceEntry = this.getVcsRootInstanceEntryForParent(sVcsRoot);
        try {
            vcsRootInstance = vcsRootInstanceEntry == null ? null : vcsRootInstanceEntry.getVcsRoot();
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return vcsRootInstance;
    }

    @Nullable
    public VcsRootInstanceEntry getVcsRootInstanceEntryForParent(@NotNull SVcsRoot sVcsRoot) {
        try {
            if (sVcsRoot == null) {
                BuildTypeImpl.$$$reportNull$$$0(382);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        for (VcsRootEntry vcsRootEntry : this.getVcsRootEntries()) {
            if (vcsRootEntry.getVcsRoot().getId() != sVcsRoot.getId()) continue;
            List<VcsRootInstanceEntry> list = this.resolveEntries(Collections.singletonList(vcsRootEntry));
            try {
                if (list.size() == 1) {
                    return list.get(0);
                }
                break;
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildTypeImpl.a(illegalStateException);
            }
        }
        return null;
    }

    @NotNull
    public List<VcsRootInstanceEntry> getVcsRootInstanceEntries() {
        List<VcsRootInstanceEntry> list;
        try {
            list = this.getVcsRootInstanceEntries(false);
            if (list == null) {
                BuildTypeImpl.$$$reportNull$$$0(383);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return list;
    }

    @NotNull
    public List<VcsRootInstanceEntry> getVcsRootInstanceEntries(boolean bl) {
        List<VcsRootInstanceEntry> list;
        List<VcsRootInstanceEntry> list2;
        block8: {
            list2 = this.resolveEntries(this.getVcsRootEntries());
            if (bl) {
                VcsRootInstanceEntry vcsRootInstanceEntry = this.myContext.getSettingsRootManager().getVersionedSettingsVcsRootInstanceEntry((SBuildType)this);
                try {
                    try {
                        if (vcsRootInstanceEntry == null || this.containsRoot(list2, vcsRootInstanceEntry.getVcsRoot())) break block8;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildTypeImpl.a(illegalStateException);
                    }
                    list2.add(vcsRootInstanceEntry);
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildTypeImpl.a(illegalStateException);
                }
            }
        }
        try {
            list = list2;
            if (list == null) {
                BuildTypeImpl.$$$reportNull$$$0(384);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return list;
    }

    public boolean isImplicitSettingsRoot(@NotNull VcsRootInstance vcsRootInstance) {
        try {
            if (vcsRootInstance == null) {
                BuildTypeImpl.$$$reportNull$$$0(385);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        VcsRootInstance vcsRootInstance2 = this.myContext.getSettingsRootManager().getVersionedSettingsVcsRootInstance((SProject)this.getProject());
        try {
            if (!vcsRootInstance.equals((Object)vcsRootInstance2)) {
                return false;
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        for (VcsRootEntry vcsRootEntry : this.getVcsRootEntries()) {
            if (vcsRootEntry.getVcsRoot().getId() != vcsRootInstance.getParentId()) continue;
            List<VcsRootInstanceEntry> list = this.resolveEntries(Collections.singletonList(vcsRootEntry));
            try {
                if (list.isEmpty()) {
                    continue;
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildTypeImpl.a(illegalStateException);
            }
            VcsRootInstance vcsRootInstance3 = list.get(0).getVcsRoot();
            try {
                if (vcsRootInstance3.getId() != vcsRootInstance.getId()) continue;
                return false;
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildTypeImpl.a(illegalStateException);
            }
        }
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NotNull
    private List<VcsRootInstanceEntry> resolveEntries(@NotNull List<VcsRootEntry> list) {
        Object object;
        Object object2;
        HashMap<Long, VcsRootEntry> hashMap;
        block26: {
            VcsRootEntry vcsRootEntry;
            List<VcsRootEntry> list2;
            Map<Long, VcsRootInstanceEntry> map;
            block27: {
                block24: {
                    try {
                        if (list == null) {
                            BuildTypeImpl.$$$reportNull$$$0(386);
                        }
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildTypeImpl.a(illegalStateException);
                    }
                    try {
                        if (list.isEmpty()) {
                            return new ArrayList<VcsRootInstanceEntry>();
                        }
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildTypeImpl.a(illegalStateException);
                    }
                    hashMap = new HashMap<Long, VcsRootEntry>();
                    map = this.myFullyResolvedVcsRootInstancesCache.get();
                    try {
                        if (map.isEmpty() || !TeamCityProperties.getBooleanOrTrue((String)"teamcity.buildType.vcsRootInstancesCache.enabled")) break block24;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildTypeImpl.a(illegalStateException);
                    }
                    list2 = new ArrayList<VcsRootEntry>();
                    for (VcsRootEntry object3 : list) {
                        vcsRootEntry = map.get(object3.getVcsRoot().getId());
                        try {
                            if (vcsRootEntry != null) {
                                hashMap.put(object3.getVcsRoot().getId(), vcsRootEntry);
                                continue;
                            }
                        }
                        catch (IllegalStateException illegalStateException) {
                            throw BuildTypeImpl.a(illegalStateException);
                        }
                        list2.add(object3);
                    }
                    break block27;
                }
                list2 = list;
            }
            if (!list2.isEmpty()) {
                object2 = this.myContext.getSettingsRootManager().getVersionedSettingsVcsRoot((SProject)this.getProject());
                List<VcsRootEntry> list3 = BuildTypeImpl.resolveCheckoutRulesConflict(new ArrayList<VcsRootEntry>(list2), (SVcsRoot)object2);
                this.enterVcsRootsResolve();
                try {
                    vcsRootEntry = this.createValueResolver();
                    HashMap<Long, VcsRootInstanceEntry> hashMap2 = new HashMap<Long, VcsRootInstanceEntry>(map);
                    List list4 = (List)this.myContext.getSecurityContext().runAsSystemUnchecked(() -> this.lambda$resolveEntries$34(list3, (LazyValueResolver)vcsRootEntry));
                    list4.forEach(arg_0 -> this.lambda$resolveEntries$35(hashMap, (LazyValueResolver)vcsRootEntry, hashMap2, arg_0));
                    this.myFullyResolvedVcsRootInstancesCache.compareAndSet(map, hashMap2);
                }
                finally {
                    this.exitVcsRootsResolve();
                }
            }
            try {
                try {
                    if ($assertionsDisabled || list.size() == hashMap.size()) break block26;
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildTypeImpl.a(illegalStateException);
                }
                throw new AssertionError();
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildTypeImpl.a(illegalStateException);
            }
        }
        object2 = new ArrayList();
        for (VcsRootEntry vcsRootEntry : list) {
            object2.add((VcsRootInstanceEntry)hashMap.get(vcsRootEntry.getVcsRoot().getId()));
        }
        try {
            object = object2;
            if (object == null) {
                BuildTypeImpl.$$$reportNull$$$0(387);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return object;
    }

    private boolean parametersRequireResolving(@NotNull SVcsRoot sVcsRoot) {
        try {
            if (sVcsRoot == null) {
                BuildTypeImpl.$$$reportNull$$$0(388);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            if (LazyValueResolver.needsResolving((String)this.getCheckoutRules((VcsRoot)sVcsRoot).getAsString())) {
                return true;
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return sVcsRoot.getProperties().entrySet().stream().filter(entry -> {
            boolean bl;
            try {
                bl = !((String)entry.getKey()).equals("teamcity:branchSpec");
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildTypeImpl.a(illegalStateException);
            }
            return bl;
        }).noneMatch(entry -> LazyValueResolver.needsResolving((String)((String)entry.getValue())));
    }

    public static boolean isInsideVcsRootsResolving() {
        boolean bl;
        AtomicInteger atomicInteger = ourResolvingCounter.get();
        try {
            bl = atomicInteger != null;
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return bl;
    }

    private void enterVcsRootsResolve() {
        block3: {
            AtomicInteger atomicInteger;
            block2: {
                atomicInteger = ourResolvingCounter.get();
                try {
                    if (atomicInteger != null) break block2;
                    ourResolvingCounter.set(new AtomicInteger());
                    break block3;
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildTypeImpl.a(illegalStateException);
                }
            }
            atomicInteger.incrementAndGet();
        }
    }

    private void exitVcsRootsResolve() {
        AtomicInteger atomicInteger;
        block7: {
            atomicInteger = ourResolvingCounter.get();
            try {
                try {
                    if ($assertionsDisabled || atomicInteger != null) break block7;
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildTypeImpl.a(illegalStateException);
                }
                throw new AssertionError();
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildTypeImpl.a(illegalStateException);
            }
        }
        try {
            if (atomicInteger.decrementAndGet() < 0) {
                ourResolvingCounter.remove();
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
    }

    @NotNull
    public List<VcsRootInstanceEntry> getVcsRootInstanceEntries(@NotNull Set<String> set, boolean bl) {
        List list;
        block20: {
            List<VcsRootInstanceEntry> list2;
            block21: {
                boolean bl2;
                block17: {
                    boolean bl3;
                    block19: {
                        block18: {
                            try {
                                if (set == null) {
                                    BuildTypeImpl.$$$reportNull$$$0(389);
                                }
                            }
                            catch (IllegalStateException illegalStateException) {
                                throw BuildTypeImpl.a(illegalStateException);
                            }
                            bl2 = false;
                            for (SVcsRoot sVcsRoot : this.getVcsRoots()) {
                                if (!set.contains(sVcsRoot.getVcsName())) continue;
                                bl2 = true;
                                break;
                            }
                            try {
                                if (bl2 || !bl) break block17;
                            }
                            catch (IllegalStateException illegalStateException) {
                                throw BuildTypeImpl.a(illegalStateException);
                            }
                            SVcsRoot sVcsRoot = this.myContext.getSettingsRootManager().getVersionedSettingsVcsRoot((SProject)this.getProject());
                            try {
                                try {
                                    if (sVcsRoot == null || !set.contains(sVcsRoot.getVcsName())) break block18;
                                }
                                catch (IllegalStateException illegalStateException) {
                                    throw BuildTypeImpl.a(illegalStateException);
                                }
                                bl3 = true;
                                break block19;
                            }
                            catch (IllegalStateException illegalStateException) {
                                throw BuildTypeImpl.a(illegalStateException);
                            }
                        }
                        bl3 = false;
                    }
                    bl2 = bl3;
                }
                try {
                    try {
                        if (bl2) break block20;
                        list2 = Collections.emptyList();
                        if (list2 != null) break block21;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildTypeImpl.a(illegalStateException);
                    }
                    BuildTypeImpl.$$$reportNull$$$0(390);
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildTypeImpl.a(illegalStateException);
                }
            }
            return list2;
        }
        try {
            list = (List)FilterUtil.filterCollection(this.getVcsRootInstanceEntries(bl), vcsRootInstanceEntry -> set.contains(vcsRootInstanceEntry.getVcsName()));
            if (list == null) {
                BuildTypeImpl.$$$reportNull$$$0(391);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return list;
    }

    @NotNull
    public BuildTypeEx createEditableCopy(boolean bl) {
        Object object;
        try {
            object = bl ? this.myBuildTypeSettings.createCopy() : this.myContext.getBuildTypeSettingsFactory().createProjectBasedSettings(this.getProject());
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        BuildTypeSettingsEx buildTypeSettingsEx = object;
        return new EditableBuildTypeCopy(this.myContext, this.myProject, this.myEntityId, buildTypeSettingsEx, this.myName, this.myDescription, this.myEmptyUuid);
    }

    private boolean containsRoot(@NotNull List<VcsRootInstanceEntry> list, @NotNull VcsRootInstance vcsRootInstance) {
        try {
            if (list == null) {
                BuildTypeImpl.$$$reportNull$$$0(392);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            if (vcsRootInstance == null) {
                BuildTypeImpl.$$$reportNull$$$0(393);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        for (VcsRootInstanceEntry vcsRootInstanceEntry : list) {
            try {
                if (!vcsRootInstance.equals((Object)vcsRootInstanceEntry.getVcsRoot())) continue;
                return true;
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildTypeImpl.a(illegalStateException);
            }
        }
        return false;
    }

    private static boolean needsResolving(@NotNull Collection<String> collection) {
        try {
            if (collection == null) {
                BuildTypeImpl.$$$reportNull$$$0(394);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        for (String string : collection) {
            try {
                if (string == null) {
                    throw new IllegalArgumentException("Invalid value: null");
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildTypeImpl.a(illegalStateException);
            }
            try {
                if (!ReferencesResolverUtil.mayContainReference((String)string)) continue;
                return true;
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildTypeImpl.a(illegalStateException);
            }
        }
        return false;
    }

    @NotNull
    public List<VcsRootInstance> getVcsRootInstances() {
        ArrayList<VcsRootInstance> arrayList;
        ArrayList<VcsRootInstance> arrayList2 = new ArrayList<VcsRootInstance>();
        for (VcsRootInstanceEntry vcsRootInstanceEntry : this.getVcsRootInstanceEntries()) {
            arrayList2.add(vcsRootInstanceEntry.getVcsRoot());
        }
        try {
            arrayList = arrayList2;
            if (arrayList == null) {
                BuildTypeImpl.$$$reportNull$$$0(395);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return arrayList;
    }

    @Nullable
    public String[] getRunnersOrder() {
        return this.myBuildTypeSettings.getRunnersOrder();
    }

    public void applyRunnersOrder(@NotNull String[] stringArray) {
        try {
            if (stringArray == null) {
                BuildTypeImpl.$$$reportNull$$$0(396);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        this.beforeModification();
        this.markEdited();
        this.myBuildTypeSettings.applyRunnersOrder(stringArray);
    }

    public void resetRunnersOrder() {
        this.myBuildTypeSettings.resetRunnersOrder();
    }

    public boolean equals(Object object) {
        boolean bl;
        block5: {
            block4: {
                try {
                    try {
                        if (!(object instanceof BuildTypeEx) || !this.getBuildTypeId().equals(((BuildTypeEx)object).getBuildTypeId())) break block4;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildTypeImpl.a(illegalStateException);
                    }
                    bl = true;
                    break block5;
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildTypeImpl.a(illegalStateException);
                }
            }
            bl = false;
        }
        return bl;
    }

    public int hashCode() {
        return this.getBuildTypeId().hashCode();
    }

    @NotNull
    public BuildSettings getBuildSettings() {
        BuildSettings buildSettings;
        try {
            buildSettings = this.getDummyBuildPromotion().getBuildSettings();
            if (buildSettings == null) {
                BuildTypeImpl.$$$reportNull$$$0(397);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return buildSettings;
    }

    @NotNull
    protected ConfigAction createExternalIdChangedConfigAction() {
        ConfigActionEx configActionEx;
        try {
            configActionEx = this.myContext.getConfigActionFactory().createAction((SProject)this.getProject(), "external id of '" + this.getName() + "' build configuration was updated");
            if (configActionEx == null) {
                BuildTypeImpl.$$$reportNull$$$0(398);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return configActionEx;
    }

    public void sendExtIdChangedEvents(@NotNull ConfigAction configAction, @NotNull String string, @NotNull String string2) {
        try {
            if (configAction == null) {
                BuildTypeImpl.$$$reportNull$$$0(399);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            if (string == null) {
                BuildTypeImpl.$$$reportNull$$$0(400);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            if (string2 == null) {
                BuildTypeImpl.$$$reportNull$$$0(401);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        ((BuildServerListener)this.myContext.getEventDispatcher().getMulticaster()).buildTypeExternalIdChanged((SBuildType)this, string, string2);
        ((ConfigActionsServerListener)this.myContext.getConfigActionsDispatcher().getMulticaster()).buildTypeExternalIdChanged(configAction, (SBuildType)this, string, string2);
    }

    protected void auditExternalIdChanged(@NotNull ConfigAction configAction, @NotNull String string, @NotNull String string2) {
        try {
            if (configAction == null) {
                BuildTypeImpl.$$$reportNull$$$0(402);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            if (string == null) {
                BuildTypeImpl.$$$reportNull$$$0(403);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            if (string2 == null) {
                BuildTypeImpl.$$$reportNull$$$0(404);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        this.auditLog().logUserAction(configAction, ActionType.BUILD_TYPE_EXTERNAL_ID_CHANGE, null, null, new Object[]{string, string2});
    }

    @NotNull
    private VcsWorkspaceAccess getVcsAccess() {
        VcsWorkspaceAccess vcsWorkspaceAccess;
        try {
            vcsWorkspaceAccess = this.myContext.getVcsAccessFactory().createWorkspaceAccess(this.getVcsRootInstanceEntries());
            if (vcsWorkspaceAccess == null) {
                BuildTypeImpl.$$$reportNull$$$0(405);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return vcsWorkspaceAccess;
    }

    @NotNull
    public List<VcsFile> listFiles(@NotNull String string) throws VcsException {
        List list;
        try {
            if (string == null) {
                BuildTypeImpl.$$$reportNull$$$0(406);
            }
        }
        catch (VcsException vcsException) {
            throw BuildTypeImpl.a(vcsException);
        }
        try {
            list = this.getVcsAccess().listFiles(string);
            if (list == null) {
                BuildTypeImpl.$$$reportNull$$$0(407);
            }
        }
        catch (VcsException vcsException) {
            throw BuildTypeImpl.a(vcsException);
        }
        return list;
    }

    @NotNull
    public Browser getVcsFilesBrowser() {
        Browser browser;
        try {
            browser = this.getVcsAccess().getVcsFilesBrowser();
            if (browser == null) {
                BuildTypeImpl.$$$reportNull$$$0(408);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return browser;
    }

    @NotNull
    public Browser getVcsFoldersBrowser() {
        Browser browser;
        try {
            browser = this.getVcsAccess().getVcsFoldersBrowser();
            if (browser == null) {
                BuildTypeImpl.$$$reportNull$$$0(409);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return browser;
    }

    public boolean canListFiles() {
        return this.getVcsAccess().canListFiles();
    }

    @NotNull
    public List<VcsRootEntry> getOwnVcsRootEntries() {
        List list;
        try {
            list = this.myBuildTypeSettings.getOwnVcsRootEntries();
            if (list == null) {
                BuildTypeImpl.$$$reportNull$$$0(410);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return list;
    }

    @NotNull
    public List<String> getTags(@NotNull SUser sUser) {
        List<String> list;
        List<String> list2;
        try {
            if (sUser == null) {
                BuildTypeImpl.$$$reportNull$$$0(411);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        List<String> list3 = (List<String>)TagDataUtil.getPrivateTags(this.getTagsData()).get(sUser);
        try {
            list2 = list3 != null ? list3 : Collections.emptyList();
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            list = list2;
            if (list2 == null) {
                BuildTypeImpl.$$$reportNull$$$0(412);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return list;
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
    private Set<TagData> getTagsData() {
        Object object = this.myTagsLock;
        // MONITORENTER : object
        if (this.myTagsData == null) {
            this.myTagsData = this.myContext.getTagsManager().getAllTagDatas((BuildType)this);
        }
        Set<TagData> set = this.myTagsData;
        // MONITOREXIT : object
        if (set != null) return set;
        BuildTypeImpl.$$$reportNull$$$0(413);
        return set;
    }

    @NotNull
    public List<File> getArtifactDirectories() {
        return this.getArtifactDirectories(BuildTypeImpl.getArtifactDirName(this.myName));
    }

    @NotNull
    public static String getArtifactDirName(String string) {
        String string2;
        try {
            string2 = FileUtil.fixDirectoryName((String)string);
            if (string2 == null) {
                BuildTypeImpl.$$$reportNull$$$0(414);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return string2;
    }

    @NotNull
    private List<File> getArtifactDirectories(@NotNull String string) {
        ArrayList<File> arrayList;
        try {
            if (string == null) {
                BuildTypeImpl.$$$reportNull$$$0(415);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        List list = this.myProject.getArtifactDirectories();
        ArrayList<File> arrayList2 = new ArrayList<File>();
        for (int i = 0; i < list.size(); ++i) {
            File file = new File((File)list.get(i), string);
            try {
                try {
                    if (i != 0 && !file.isDirectory()) continue;
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildTypeImpl.a(illegalStateException);
                }
                arrayList2.add(file);
                continue;
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildTypeImpl.a(illegalStateException);
            }
        }
        try {
            arrayList = arrayList2;
            if (arrayList == null) {
                BuildTypeImpl.$$$reportNull$$$0(416);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return arrayList;
    }

    public <T> T getDeclaredOption(@NotNull Option<T> option) {
        try {
            if (option == null) {
                BuildTypeImpl.$$$reportNull$$$0(417);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return (T)this.myBuildTypeSettings.getDeclaredOption(option);
    }

    public Parameter getOwnParameter(@NotNull String string) {
        try {
            if (string == null) {
                BuildTypeImpl.$$$reportNull$$$0(418);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return this.myBuildTypeSettings.getOwnParameter(string);
    }

    @NotNull
    public Collection<Parameter> getOwnParametersWithoutInheritedSpec() {
        Collection collection;
        try {
            collection = this.myBuildTypeSettings.getOwnParametersWithoutInheritedSpec();
            if (collection == null) {
                BuildTypeImpl.$$$reportNull$$$0(419);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return collection;
    }

    @NotNull
    public Collection<Parameter> getInheritedParametersCollection() {
        Collection collection;
        try {
            collection = this.myBuildTypeSettings.getInheritedParametersCollection();
            if (collection == null) {
                BuildTypeImpl.$$$reportNull$$$0(420);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return collection;
    }

    @Nullable
    public Parameter getOwnNonInheritedParameter(@NotNull String string) {
        try {
            if (string == null) {
                BuildTypeImpl.$$$reportNull$$$0(421);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return this.myBuildTypeSettings.getOwnNonInheritedParameter(string);
    }

    @NotNull
    public Set<Long> getExcludedDefaultRootInstanceIds() {
        Set set;
        try {
            set = this.myBranches.getExcludedDefaultRootInstanceIds();
            if (set == null) {
                BuildTypeImpl.$$$reportNull$$$0(422);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return set;
    }

    public boolean isDeployment() {
        return this.myBuildTypeSettings.isDeployment();
    }

    public String getRunBuildActionName() {
        String string = this.getParameterValue("teamcity.ui.runButton.caption");
        try {
            if (string != null) {
                return string;
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            if (this.isDeployment()) {
                return "Deploy";
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return "Run";
    }

    public boolean isCompositeBuildType() {
        return this.myBuildTypeSettings.isCompositeBuildType();
    }

    public boolean isAgentLessBuildType() {
        return this.myBuildTypeSettings.isAgentLessBuildType();
    }

    public boolean isDefaultBranchExcluded() {
        boolean bl;
        try {
            bl = !this.getBuildTypeBranchFilter().accept((Branch)new BranchImpl("<default>"));
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return bl;
    }

    @NotNull
    public BranchFilter getBuildTypeBranchFilter() {
        BranchFilter branchFilter;
        try {
            branchFilter = this.myBuildTypeSettings.getBuildTypeBranchFilter(this.getValueResolver());
            if (branchFilter == null) {
                BuildTypeImpl.$$$reportNull$$$0(423);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return branchFilter;
    }

    @NotNull
    public BranchFilter getBuildTypeBranchFilterUnsafe() throws InvalidBranchSpecException {
        BranchFilter branchFilter;
        try {
            branchFilter = this.myBuildTypeSettings.getBuildTypeBranchFilterUnsafe(this.getValueResolver());
            if (branchFilter == null) {
                BuildTypeImpl.$$$reportNull$$$0(424);
            }
        }
        catch (InvalidBranchSpecException invalidBranchSpecException) {
            throw BuildTypeImpl.a(invalidBranchSpecException);
        }
        return branchFilter;
    }

    @NotNull
    public Set<Long> getRelatedVcsRootInstanceIds() {
        TLongHashSet tLongHashSet = this.myContext.getBuildTypeVcsRoots().getRelatedRootIds((SBuildType)this);
        return new TLongSetDecorator((TLongSet)tLongHashSet);
    }

    public boolean textValueMatches(@NotNull Pattern pattern, boolean bl, @NotNull MatchedSettingsVisitor matchedSettingsVisitor) {
        try {
            if (pattern == null) {
                BuildTypeImpl.$$$reportNull$$$0(425);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            if (matchedSettingsVisitor == null) {
                BuildTypeImpl.$$$reportNull$$$0(426);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return this.myBuildTypeSettings.textValueMatches(pattern, bl, matchedSettingsVisitor);
    }

    public void resetArchivedBuildTypeCaches() {
        this.myBuildTypeOrderedBuilds.resetBuilds();
        this.myBranches.resetCaches();
        this.resetTagsCache();
        this.resetResolvedVcsRootInstancesCache();
        this.myContext.getVcsHistory().invalidateBuildTypesModifications(Collections.singleton(this));
    }

    public boolean isVirtual() {
        return this.myProject.isVirtual();
    }

    public boolean isEmptyUuid() {
        return this.myEmptyUuid;
    }

    public void reassignIntId(@NotNull String string) {
        block5: {
            try {
                if (string == null) {
                    BuildTypeImpl.$$$reportNull$$$0(427);
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildTypeImpl.a(illegalStateException);
            }
            String string2 = this.getBuildTypeId();
            this.updateIntId("Reassign internalId " + string + " to buildType " + LogUtil.describe((BuildTypeEx)this), customIdChangeApplier -> {
                BuildTypeImpl buildTypeImpl = (BuildTypeImpl)((Object)((Object)this.myContext.getProjectManager().getProjectDataModel().getBuildTypes().find((Object)string2)));
                try {
                    if (buildTypeImpl == null) {
                        LOG.warn("Cannot assign new internal id: " + string + " to the build configuration: " + LogUtil.describe((BuildTypeEx)this) + " because the build configuration no longer exists");
                        return;
                    }
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildTypeImpl.a(illegalStateException);
                }
                try {
                    if (buildTypeImpl.getInternalId().equals(string)) {
                        LOG.warn("Skip changing internal id of the build configuration: " + LogUtil.describe((BuildTypeEx)buildTypeImpl) + " because it's internal id is already set to: " + string);
                        return;
                    }
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildTypeImpl.a(illegalStateException);
                }
                this.myContext.getBuildTypeIdentityIdentifiersManager().reassignInternalId(buildTypeImpl.getEntityId(), (Object)string, customIdChangeApplier);
            });
            BuildTypeImpl buildTypeImpl = (BuildTypeImpl)((Object)this.myContext.getProjectManager().getProjectDataModel().getBuildTypes().find((Object)string));
            try {
                if (buildTypeImpl == null || !buildTypeImpl.getExternalId().equals(this.getExternalId())) break block5;
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildTypeImpl.a(illegalStateException);
            }
            AuditLog auditLog = this.myContext.getAuditLogFactory().createForBuildTypeWithId(string);
            auditLog.logUserAction(ActionType.BUILD_TYPE_INTERNAL_ID_CHANGE, null, null, new Object[]{string2, string});
        }
    }

    private void updateIntId(@NotNull String string, @NotNull Consumer<CustomIdChangeApplier<String>> consumer) {
        try {
            if (string == null) {
                BuildTypeImpl.$$$reportNull$$$0(428);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        try {
            if (consumer == null) {
                BuildTypeImpl.$$$reportNull$$$0(429);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        this.myContext.getProjectPersistingHandler().executeTask(string, () -> {
            try {
                this.myContext.getProjectManager().runWithExclusiveProjectModelLock(() -> {
                    this.myContext.getVersionedSettingsUpdater().runAsUpdateFromVcs(this.getProjectExternalId(), () -> consumer.accept((string, string2) -> {
                        try {
                            this.myContext.getSecurityContext().runAsSystem(() -> {
                                ReloadProjectsConfig reloadProjectsConfig = new ReloadProjectsConfig();
                                reloadProjectsConfig.setChangedFiles(Collections.singletonList(this.getConfigurationFile()));
                                reloadProjectsConfig.setExpectedVersions(this.myContext.getProjectManager().getProjectDataModel().getVersionSnapshot());
                                this.myContext.getProjectsLoader().reloadProjects(reloadProjectsConfig);
                                for (SBuildType sBuildType : this.getArtifactsReferences()) {
                                    ((BuildTypeEx)sBuildType).getSettings().markDependenciesAsUnresolved((SBuildType)this);
                                }
                                this.myContext.getMultiNodesEvents().publish("buildTypeInternalIdChanged", string + ":" + string2);
                            });
                        }
                        catch (Throwable throwable) {
                            try {
                                if (throwable instanceof IOException) {
                                    throw (IOException)throwable;
                                }
                            }
                            catch (Throwable throwable2) {
                                throw BuildTypeImpl.a(throwable2);
                            }
                            throw new IOException(throwable);
                        }
                    }));
                    return null;
                });
            }
            catch (Throwable throwable) {
                LOG.warnAndDebugDetails("Failed to change internal id in the build configuration: " + LogUtil.describe((BuildTypeEx)this), throwable);
                ExceptionUtil.rethrowAsRuntimeException((Throwable)throwable);
            }
        });
    }

    public void resetResolvedVcsRootInstancesCache() {
        this.myFullyResolvedVcsRootInstancesCache.set(new HashMap());
    }

    public boolean isInModel() {
        return this.myAttachedToModel.get();
    }

    @NotNull
    public static List<VcsRootEntry> resolveCheckoutRulesConflict(@NotNull List<VcsRootEntry> list, @Nullable SVcsRoot sVcsRoot) {
        List<VcsRootEntry> list2;
        block31: {
            List<VcsRootEntry> list3;
            block32: {
                block29: {
                    List<VcsRootEntry> list4;
                    block30: {
                        block27: {
                            List<VcsRootEntry> list5;
                            block28: {
                                try {
                                    if (list == null) {
                                        BuildTypeImpl.$$$reportNull$$$0(430);
                                    }
                                }
                                catch (IllegalStateException illegalStateException) {
                                    throw BuildTypeImpl.a(illegalStateException);
                                }
                                try {
                                    try {
                                        try {
                                            if (sVcsRoot != null && !list.isEmpty()) break block27;
                                        }
                                        catch (IllegalStateException illegalStateException) {
                                            throw BuildTypeImpl.a(illegalStateException);
                                        }
                                        list5 = list;
                                        if (list5 != null) break block28;
                                    }
                                    catch (IllegalStateException illegalStateException) {
                                        throw BuildTypeImpl.a(illegalStateException);
                                    }
                                    BuildTypeImpl.$$$reportNull$$$0(431);
                                }
                                catch (IllegalStateException illegalStateException) {
                                    throw BuildTypeImpl.a(illegalStateException);
                                }
                            }
                            return list5;
                        }
                        try {
                            try {
                                try {
                                    if (list.size() != 1 || !list.get(0).getCheckoutRules().isIncludeAll()) break block29;
                                }
                                catch (IllegalStateException illegalStateException) {
                                    throw BuildTypeImpl.a(illegalStateException);
                                }
                                list4 = list;
                                if (list4 != null) break block30;
                            }
                            catch (IllegalStateException illegalStateException) {
                                throw BuildTypeImpl.a(illegalStateException);
                            }
                            BuildTypeImpl.$$$reportNull$$$0(432);
                        }
                        catch (IllegalStateException illegalStateException) {
                            throw BuildTypeImpl.a(illegalStateException);
                        }
                    }
                    return list4;
                }
                try {
                    try {
                        if (TeamCityProperties.getBooleanOrTrue((String)"teamcity.buildType.vcsRootInstance.resolveSettingsRevisionConflict")) break block31;
                        list3 = list;
                        if (list3 != null) break block32;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw BuildTypeImpl.a(illegalStateException);
                    }
                    BuildTypeImpl.$$$reportNull$$$0(433);
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildTypeImpl.a(illegalStateException);
                }
            }
            return list3;
        }
        for (int i = 0; i < list.size(); ++i) {
            VcsRootEntry vcsRootEntry = list.get(i);
            try {
                try {
                    if (vcsRootEntry.getVcsRoot().getId() != sVcsRoot.getId() || vcsRootEntry.getCheckoutRules().isIncludeAll()) continue;
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildTypeImpl.a(illegalStateException);
                }
                if (!(vcsRootEntry.getVcsRoot() instanceof SVcsRootEx)) continue;
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildTypeImpl.a(illegalStateException);
            }
            SVcsRootEx sVcsRootEx = (SVcsRootEx)vcsRootEntry.getVcsRoot();
            HashMap<String, String> hashMap = new HashMap<String, String>(sVcsRootEx.getProperties());
            hashMap.put("##teamcity:settingsRevisionConflict", "true");
            SVcsRoot sVcsRoot2 = sVcsRootEx.createCopyWithCustomProperties(hashMap);
            list.set(i, new VcsRootEntry((VcsRoot)sVcsRoot2, vcsRootEntry.getCheckoutRules()));
        }
        try {
            list2 = list;
            if (list2 == null) {
                BuildTypeImpl.$$$reportNull$$$0(434);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        return list2;
    }

    private void setPipelineBuildTypeOptions(BuildTypeSettingsEx buildTypeSettingsEx, PipelineDescription pipelineDescription) {
        block4: {
            try {
                try {
                    if (!TeamCityProperties.getBoolean((String)"teamcity.pipelines.executionTimeouts.enabled") || pipelineDescription.executionTimeoutMinutes == null) break block4;
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildTypeImpl.a(illegalStateException);
                }
                buildTypeSettingsEx.setOption(BuildTypeOptions.BT_EXECUTION_TIMEOUT, (Object)pipelineDescription.executionTimeoutMinutes);
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildTypeImpl.a(illegalStateException);
            }
        }
    }

    private /* synthetic */ void lambda$resolveEntries$35(Map map, LazyValueResolver lazyValueResolver, Map map2, VcsRootInstanceEntry vcsRootInstanceEntry) {
        block7: {
            try {
                map.put(vcsRootInstanceEntry.getVcsRoot().getParentId(), vcsRootInstanceEntry);
                if (!this.containsVcsRoot(vcsRootInstanceEntry.getVcsRoot().getParentId())) {
                    return;
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildTypeImpl.a(illegalStateException);
            }
            try {
                try {
                    if (lazyValueResolver.isValuesNeededResolving() && this.parametersRequireResolving(vcsRootInstanceEntry.getVcsRoot().getParent())) break block7;
                }
                catch (IllegalStateException illegalStateException) {
                    throw BuildTypeImpl.a(illegalStateException);
                }
                map2.put(vcsRootInstanceEntry.getVcsRoot().getParentId(), vcsRootInstanceEntry);
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildTypeImpl.a(illegalStateException);
            }
        }
    }

    private /* synthetic */ List lambda$resolveEntries$34(List list, LazyValueResolver lazyValueResolver) throws Throwable {
        return this.myContext.getVcsRootInstancesManager().getVcsRootInstanceEntries(list, (ValueResolver)lazyValueResolver);
    }

    private static /* synthetic */ DependencyConsumer.Result lambda$isBranchSpecDefined$16(AtomicBoolean atomicBoolean, Collection collection, BuildTypeEx buildTypeEx) {
        try {
            if (BuildTypeImpl.isBranchSpecDefined(buildTypeEx.getVcsRoots(), buildTypeEx.getValueResolver())) {
                atomicBoolean.set(true);
                return DependencyConsumer.Result.STOP;
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        for (BranchSpecProvider branchSpecProvider : collection) {
            try {
                if (!branchSpecProvider.includeIntoOwnBranchSpecsOf(buildTypeEx)) continue;
                atomicBoolean.set(true);
                return DependencyConsumer.Result.STOP;
            }
            catch (IllegalStateException illegalStateException) {
                throw BuildTypeImpl.a(illegalStateException);
            }
        }
        return DependencyConsumer.Result.CONTINUE;
    }

    static {
        boolean bl;
        try {
            bl = !BuildTypeImpl.class.desiredAssertionStatus();
        }
        catch (IllegalStateException illegalStateException) {
            throw BuildTypeImpl.a(illegalStateException);
        }
        $assertionsDisabled = bl;
        LOG = Logger.getInstance((String)BuildTypeImpl.class.getName());
        NULL_COMMENT = new NullComment();
        ourResolvingCounter = new ThreadLocal();
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
