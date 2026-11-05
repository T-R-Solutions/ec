/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.intellij.openapi.util.Pair
 *  jetbrains.buildServer.buildTriggers.triggerRules.TriggerRules
 *  jetbrains.buildServer.buildTriggers.triggerRules.TriggerRulesFactory
 *  jetbrains.buildServer.pipeline.PipelineProject
 *  jetbrains.buildServer.serverSide.BuildPromotionEx
 *  jetbrains.buildServer.serverSide.BuildRevision
 *  jetbrains.buildServer.serverSide.BuildServerListener
 *  jetbrains.buildServer.serverSide.BuildTypeEx
 *  jetbrains.buildServer.serverSide.BuildTypeTemplate
 *  jetbrains.buildServer.serverSide.CheckoutRulesFactory
 *  jetbrains.buildServer.serverSide.ConfigAction
 *  jetbrains.buildServer.serverSide.ConfigActionEx
 *  jetbrains.buildServer.serverSide.ConfigActionsServerListener
 *  jetbrains.buildServer.serverSide.PersistTask
 *  jetbrains.buildServer.serverSide.ProjectManager
 *  jetbrains.buildServer.serverSide.SBuildType
 *  jetbrains.buildServer.serverSide.SProject
 *  jetbrains.buildServer.serverSide.TeamCityProperties
 *  jetbrains.buildServer.serverSide.impl.ConfigActionFactoryEx
 *  jetbrains.buildServer.serverSide.impl.ProjectEx
 *  jetbrains.buildServer.serverSide.impl.versionedSettings.ConverterChangesStorage
 *  jetbrains.buildServer.serverSide.impl.versionedSettings.ProjectSettingsGenerator
 *  jetbrains.buildServer.serverSide.impl.versionedSettings.ProjectSettingsGeneratorConfig
 *  jetbrains.buildServer.serverSide.impl.versionedSettings.ProjectSettingsGeneratorRegistry
 *  jetbrains.buildServer.serverSide.impl.versionedSettings.ProjectSettingsPaths
 *  jetbrains.buildServer.serverSide.impl.versionedSettings.VersionedSettingsConfig
 *  jetbrains.buildServer.serverSide.impl.versionedSettings.VersionedSettingsConfig$BuildSettingsMode
 *  jetbrains.buildServer.serverSide.impl.versionedSettings.VersionedSettingsConfig$DslExecutionMode
 *  jetbrains.buildServer.serverSide.impl.versionedSettings.VersionedSettingsConfigStorage
 *  jetbrains.buildServer.serverSide.impl.versionedSettings.VersionedSettingsDBVersion
 *  jetbrains.buildServer.serverSide.impl.versionedSettings.VersionedSettingsOptions
 *  jetbrains.buildServer.serverSide.impl.versionedSettings.VersionedSettingsOptionsEx
 *  jetbrains.buildServer.serverSide.impl.versionedSettings.VersionedSettingsStatusTracker
 *  jetbrains.buildServer.serverSide.impl.versionedSettings.VersionedSettingsUtil
 *  jetbrains.buildServer.serverSide.versionedSettings.VersionedSettingsManager
 *  jetbrains.buildServer.util.EventDispatcher
 *  jetbrains.buildServer.util.FileUtil
 *  jetbrains.buildServer.util.NamedThreadFactory
 *  jetbrains.buildServer.util.StringPool
 *  jetbrains.buildServer.vcs.CheckoutRules
 *  jetbrains.buildServer.vcs.SVcsRoot
 *  jetbrains.buildServer.vcs.VcsRoot
 *  jetbrains.buildServer.vcs.VcsRootEntry
 *  jetbrains.buildServer.vcs.VcsRootInstance
 *  jetbrains.buildServer.vcs.VcsRootInstanceEntry
 *  jetbrains.buildServer.vcs.VcsRootInstancesManagerEx
 *  jetbrains.buildServer.vcs.VcsRootsManager
 *  jetbrains.buildServer.vcs.impl.DeletedVcsRoot
 *  jetbrains.buildServer.zip.ZipFactory
 *  jetbrains.buildServer.zip.ZipWriter
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package jetbrains.buildServer.serverSide.impl.versionedSettings;

import com.intellij.openapi.util.Pair;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EventListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;
import jetbrains.buildServer.buildTriggers.triggerRules.TriggerRules;
import jetbrains.buildServer.buildTriggers.triggerRules.TriggerRulesFactory;
import jetbrains.buildServer.pipeline.PipelineProject;
import jetbrains.buildServer.serverSide.BuildPromotionEx;
import jetbrains.buildServer.serverSide.BuildRevision;
import jetbrains.buildServer.serverSide.BuildServerListener;
import jetbrains.buildServer.serverSide.BuildTypeEx;
import jetbrains.buildServer.serverSide.BuildTypeTemplate;
import jetbrains.buildServer.serverSide.CheckoutRulesFactory;
import jetbrains.buildServer.serverSide.ConfigAction;
import jetbrains.buildServer.serverSide.ConfigActionEx;
import jetbrains.buildServer.serverSide.ConfigActionsServerListener;
import jetbrains.buildServer.serverSide.PersistTask;
import jetbrains.buildServer.serverSide.ProjectManager;
import jetbrains.buildServer.serverSide.SBuildType;
import jetbrains.buildServer.serverSide.SProject;
import jetbrains.buildServer.serverSide.TeamCityProperties;
import jetbrains.buildServer.serverSide.impl.ConfigActionFactoryEx;
import jetbrains.buildServer.serverSide.impl.ProjectEx;
import jetbrains.buildServer.serverSide.impl.versionedSettings.ConverterChangesStorage;
import jetbrains.buildServer.serverSide.impl.versionedSettings.ProjectSettingsGenerator;
import jetbrains.buildServer.serverSide.impl.versionedSettings.ProjectSettingsGeneratorConfig;
import jetbrains.buildServer.serverSide.impl.versionedSettings.ProjectSettingsGeneratorRegistry;
import jetbrains.buildServer.serverSide.impl.versionedSettings.ProjectSettingsPaths;
import jetbrains.buildServer.serverSide.impl.versionedSettings.VersionedSettingsConfig;
import jetbrains.buildServer.serverSide.impl.versionedSettings.VersionedSettingsConfigStorage;
import jetbrains.buildServer.serverSide.impl.versionedSettings.VersionedSettingsDBVersion;
import jetbrains.buildServer.serverSide.impl.versionedSettings.VersionedSettingsOptions;
import jetbrains.buildServer.serverSide.impl.versionedSettings.VersionedSettingsOptionsEx;
import jetbrains.buildServer.serverSide.impl.versionedSettings.VersionedSettingsStatusTracker;
import jetbrains.buildServer.serverSide.impl.versionedSettings.VersionedSettingsUtil;
import jetbrains.buildServer.serverSide.versionedSettings.VersionedSettingsManager;
import jetbrains.buildServer.util.EventDispatcher;
import jetbrains.buildServer.util.FileUtil;
import jetbrains.buildServer.util.NamedThreadFactory;
import jetbrains.buildServer.util.StringPool;
import jetbrains.buildServer.vcs.CheckoutRules;
import jetbrains.buildServer.vcs.SVcsRoot;
import jetbrains.buildServer.vcs.VcsRoot;
import jetbrains.buildServer.vcs.VcsRootEntry;
import jetbrains.buildServer.vcs.VcsRootInstance;
import jetbrains.buildServer.vcs.VcsRootInstanceEntry;
import jetbrains.buildServer.vcs.VcsRootInstancesManagerEx;
import jetbrains.buildServer.vcs.VcsRootsManager;
import jetbrains.buildServer.vcs.impl.DeletedVcsRoot;
import jetbrains.buildServer.zip.ZipFactory;
import jetbrains.buildServer.zip.ZipWriter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Exception performing whole class analysis ignored.
 */
public class VersionedSettingsManagerImpl
implements VersionedSettingsManager {
    public static final String USE_VERSIONED_SETTINGS_PROJECT_FOR_VCS_ROOT_PARAMS_RESOLVE = "teamcity.versionedSettings.vcsRootParametersResolve.useVersionedSettingsProject";
    public static final String VERSIONED_SETTINGS_STATUS_CACHE_PROP = "teamcity.versionedSettings.statusCache.enabled";
    private final ProjectManager myProjectManager;
    private final VersionedSettingsConfigStorage myRootIdStorage;
    private final VcsRootInstancesManagerEx myVcsRootsManager;
    private final VcsRootsManager myParentRootManager;
    private final VersionedSettingsOptions myOptions;
    private final ProjectSettingsGeneratorRegistry myGenerators;
    private final ZipFactory myZipFactory;
    private final ConfigActionFactoryEx myConfigActionFactory;
    private VersionedSettingsStatusTracker myVersionedSettingsStatusTracker;
    @NotNull
    private final VersionedSettingsDBVersion myVersionedSettingsDBVersion;
    @NotNull
    private final VersionedSettingsOptionsEx myVersionedSettingsOptions;
    @NotNull
    private ConverterChangesStorage myConverterChangesStorage;
    private final Map<String, Set<String>> myVersionedSettingsVcsRootsCache;
    private final AtomicBoolean myEnableSettingsInProgress;
    private static final String ENABLE_VERSIONED_SETTINGS_MESSAGE_PREFIX = "TeamCity settings change: convert settings";

    public VersionedSettingsManagerImpl(@NotNull ProjectManager projectManager, @NotNull VersionedSettingsConfigStorage versionedSettingsConfigStorage, @NotNull EventDispatcher<ConfigActionsServerListener> eventDispatcher, @NotNull EventDispatcher<BuildServerListener> eventDispatcher2, @NotNull VcsRootInstancesManagerEx vcsRootInstancesManagerEx, @NotNull VcsRootsManager vcsRootsManager, @NotNull VersionedSettingsOptions versionedSettingsOptions, @NotNull ProjectSettingsGeneratorRegistry projectSettingsGeneratorRegistry, @NotNull ZipFactory zipFactory, @NotNull ConfigActionFactoryEx configActionFactoryEx, @NotNull VersionedSettingsDBVersion versionedSettingsDBVersion, @NotNull VersionedSettingsOptionsEx versionedSettingsOptionsEx) {
        if (projectManager == null) {
            VersionedSettingsManagerImpl.$$$reportNull$$$0(0);
        }
        if (versionedSettingsConfigStorage == null) {
            VersionedSettingsManagerImpl.$$$reportNull$$$0(1);
        }
        if (eventDispatcher == null) {
            VersionedSettingsManagerImpl.$$$reportNull$$$0(2);
        }
        if (eventDispatcher2 == null) {
            VersionedSettingsManagerImpl.$$$reportNull$$$0(3);
        }
        if (vcsRootInstancesManagerEx == null) {
            VersionedSettingsManagerImpl.$$$reportNull$$$0(4);
        }
        if (vcsRootsManager == null) {
            VersionedSettingsManagerImpl.$$$reportNull$$$0(5);
        }
        if (versionedSettingsOptions == null) {
            VersionedSettingsManagerImpl.$$$reportNull$$$0(6);
        }
        if (projectSettingsGeneratorRegistry == null) {
            VersionedSettingsManagerImpl.$$$reportNull$$$0(7);
        }
        if (zipFactory == null) {
            VersionedSettingsManagerImpl.$$$reportNull$$$0(8);
        }
        if (configActionFactoryEx == null) {
            VersionedSettingsManagerImpl.$$$reportNull$$$0(9);
        }
        if (versionedSettingsDBVersion == null) {
            VersionedSettingsManagerImpl.$$$reportNull$$$0(10);
        }
        if (versionedSettingsOptionsEx == null) {
            VersionedSettingsManagerImpl.$$$reportNull$$$0(11);
        }
        this.myVersionedSettingsVcsRootsCache = new ConcurrentHashMap<String, Set<String>>();
        this.myEnableSettingsInProgress = new AtomicBoolean();
        this.myProjectManager = projectManager;
        this.myRootIdStorage = versionedSettingsConfigStorage;
        this.myVcsRootsManager = vcsRootInstancesManagerEx;
        this.myParentRootManager = vcsRootsManager;
        this.myOptions = versionedSettingsOptions;
        this.myGenerators = projectSettingsGeneratorRegistry;
        this.myZipFactory = zipFactory;
        this.myConfigActionFactory = configActionFactoryEx;
        this.myVersionedSettingsDBVersion = versionedSettingsDBVersion;
        this.myVersionedSettingsOptions = versionedSettingsOptionsEx;
        eventDispatcher.addListener((EventListener)new /* Unavailable Anonymous Inner Class!! */);
        eventDispatcher2.addListener((EventListener)new /* Unavailable Anonymous Inner Class!! */);
    }

    public void setVersionedSettingsStatusTracker(@Nullable VersionedSettingsStatusTracker versionedSettingsStatusTracker) {
        this.myVersionedSettingsStatusTracker = versionedSettingsStatusTracker;
    }

    private boolean isVersionedSettingsStatusCacheEnabled() {
        return TeamCityProperties.getBooleanOrTrue((String)"teamcity.versionedSettings.statusCache.enabled");
    }

    private boolean isVersionedSettingsEnabledForVcsRoot(@NotNull VcsRoot vcsRoot) {
        try {
            if (vcsRoot == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(12);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        try {
            if (!this.isVersionedSettingsStatusCacheEnabled()) {
                return true;
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        return this.myVersionedSettingsVcsRootsCache.containsKey(vcsRoot.getExternalId());
    }

    private void updateVersionedSettingsStatusCache(@NotNull SProject sProject) {
        block14: {
            String string2;
            block13: {
                try {
                    if (sProject == null) {
                        VersionedSettingsManagerImpl.$$$reportNull$$$0(13);
                    }
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw VersionedSettingsManagerImpl.a(illegalArgumentException);
                }
                try {
                    if (!this.isVersionedSettingsStatusCacheEnabled()) {
                        this.myVersionedSettingsVcsRootsCache.clear();
                        return;
                    }
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw VersionedSettingsManagerImpl.a(illegalArgumentException);
                }
                VersionedSettingsConfig versionedSettingsConfig = this.myRootIdStorage.readConfig(sProject);
                string2 = versionedSettingsConfig.getVcsRootExternalId();
                try {
                    try {
                        if (!versionedSettingsConfig.isEnabled() || string2 == null) break block13;
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        throw VersionedSettingsManagerImpl.a(illegalArgumentException);
                    }
                    this.myVersionedSettingsVcsRootsCache.compute(string2, (string, hashSet) -> {
                        HashSet<String> hashSet2;
                        HashSet<String> hashSet3;
                        HashSet<String> hashSet4;
                        block6: {
                            try {
                                try {
                                    if (hashSet == null || !hashSet.contains(sProject.getProjectId())) break block6;
                                }
                                catch (IllegalArgumentException illegalArgumentException) {
                                    throw VersionedSettingsManagerImpl.a(illegalArgumentException);
                                }
                                return hashSet;
                            }
                            catch (IllegalArgumentException illegalArgumentException) {
                                throw VersionedSettingsManagerImpl.a(illegalArgumentException);
                            }
                        }
                        try {
                            HashSet<String> hashSet5;
                            hashSet4 = hashSet5;
                            hashSet3 = hashSet5;
                            hashSet2 = hashSet == null ? Collections.emptySet() : hashSet;
                        }
                        catch (IllegalArgumentException illegalArgumentException) {
                            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
                        }
                        hashSet4(hashSet2);
                        hashSet = hashSet3;
                        hashSet.add(sProject.getProjectId());
                        return Collections.unmodifiableSet(hashSet);
                    });
                    break block14;
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw VersionedSettingsManagerImpl.a(illegalArgumentException);
                }
            }
            try {
                if (string2 != null) {
                    this.myVersionedSettingsVcsRootsCache.compute(string2, (string, hashSet) -> {
                        try {
                            if (hashSet == null) {
                                return null;
                            }
                        }
                        catch (IllegalArgumentException illegalArgumentException) {
                            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
                        }
                        try {
                            if (!hashSet.contains(sProject.getProjectId())) {
                                return hashSet;
                            }
                        }
                        catch (IllegalArgumentException illegalArgumentException) {
                            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
                        }
                        try {
                            if (hashSet.size() == 1) {
                                return null;
                            }
                        }
                        catch (IllegalArgumentException illegalArgumentException) {
                            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
                        }
                        hashSet = new HashSet(hashSet);
                        hashSet.remove(sProject.getProjectId());
                        return Collections.unmodifiableSet(hashSet);
                    });
                }
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw VersionedSettingsManagerImpl.a(illegalArgumentException);
            }
        }
    }

    @Nullable
    public SVcsRoot getVersionedSettingsVcsRoot(@NotNull SProject sProject) {
        try {
            if (sProject == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(14);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        Pair<SProject, VersionedSettingsConfig> pair = this.findProjectWithConfiguredSettingsRoot(sProject);
        try {
            if (pair == null) {
                return null;
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        try {
            if (pair.second == null) {
                return null;
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        String string = ((VersionedSettingsConfig)pair.getSecond()).getVcsRootExternalId();
        try {
            if (string == null) {
                return null;
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        return this.findAccessibleVcsRoot(sProject, string);
    }

    @NotNull
    public Set<SProject> getProjectsWithOwnVersionedSettings() {
        HashSet<SProject> hashSet;
        HashSet<SProject> hashSet2 = new HashSet<SProject>();
        for (SProject sProject : this.myProjectManager.getProjects()) {
            try {
                if (this.getOwnVersionedSettingsVcsRoot(sProject) == null) continue;
                hashSet2.add(sProject);
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw VersionedSettingsManagerImpl.a(illegalArgumentException);
            }
        }
        try {
            hashSet = hashSet2;
            if (hashSet == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(15);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        return hashSet;
    }

    @Nullable
    public SVcsRoot getOwnVersionedSettingsVcsRoot(@NotNull SProject sProject) {
        try {
            if (sProject == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(16);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        VersionedSettingsConfig versionedSettingsConfig = this.myRootIdStorage.readConfig(sProject);
        try {
            if (!versionedSettingsConfig.isEnabled()) {
                return null;
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        try {
            if (versionedSettingsConfig.isSameSettingsAsParent()) {
                return null;
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        String string = versionedSettingsConfig.getVcsRootExternalId();
        try {
            if (string == null) {
                return null;
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        return this.findAccessibleVcsRoot(sProject, string);
    }

    @Nullable
    public SProject getVersionedSettingsProject(@NotNull SProject sProject) {
        try {
            if (sProject == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(17);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        Pair<SProject, VersionedSettingsConfig> pair = this.findProjectWithConfiguredSettingsRoot(sProject);
        try {
            if (pair == null) {
                return null;
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        return (SProject)pair.getFirst();
    }

    public boolean hasVersionedSettings(@NotNull SProject sProject) {
        boolean bl;
        try {
            if (sProject == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(18);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        try {
            bl = this.getVersionedSettingsProject(sProject) != null;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        return bl;
    }

    @Nullable
    private Pair<SProject, VersionedSettingsConfig> findProjectWithConfiguredSettingsRoot(@NotNull SProject sProject) {
        try {
            if (sProject == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(19);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        VersionedSettingsConfig versionedSettingsConfig = this.readConfig(sProject);
        try {
            if (!versionedSettingsConfig.isEnabled()) {
                return null;
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        if (versionedSettingsConfig.isSameSettingsAsParent()) {
            Pair<SProject, VersionedSettingsConfig> pair;
            SProject sProject2 = sProject.getParentProject();
            try {
                pair = sProject2 == null ? null : this.findProjectWithConfiguredSettingsRoot(sProject2);
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw VersionedSettingsManagerImpl.a(illegalArgumentException);
            }
            return pair;
        }
        return new Pair((Object)sProject, (Object)versionedSettingsConfig);
    }

    @Nullable
    public VcsRootInstance getVersionedSettingsVcsRootInstance(@NotNull SProject sProject) {
        try {
            if (sProject == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(20);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        Pair<SProject, VersionedSettingsConfig> pair = this.findProjectWithConfiguredSettingsRoot(sProject);
        try {
            if (pair == null) {
                return null;
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        return this.getVersionedSettingsVcsRootInstance(pair, sProject);
    }

    @Nullable
    private VcsRootInstance getVersionedSettingsVcsRootInstance(@NotNull Pair<SProject, VersionedSettingsConfig> pair, @NotNull SProject sProject) {
        try {
            if (pair == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(21);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        try {
            if (sProject == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(22);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        String string = ((VersionedSettingsConfig)pair.second).getVcsRootExternalId();
        try {
            if (string == null) {
                return null;
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        SVcsRoot sVcsRoot = this.findAccessibleVcsRoot(sProject, string);
        try {
            if (sVcsRoot == null) {
                return null;
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        SProject sProject2 = sProject;
        if (VersionedSettingsManagerImpl.resolveVcsRootInstanceViaParametersOfConfigOwner()) {
            sProject2 = (SProject)pair.first;
        }
        return this.resolveRoot(sProject2, sVcsRoot);
    }

    private static boolean resolveVcsRootInstanceViaParametersOfConfigOwner() {
        return TeamCityProperties.getBooleanOrTrue((String)"teamcity.versionedSettings.vcsRootParametersResolve.useVersionedSettingsProject");
    }

    @NotNull
    public Set<SProject> getProjectsBySettingsRoot(@NotNull VcsRoot vcsRoot) {
        HashSet<SProject> hashSet;
        SVcsRoot sVcsRoot;
        HashSet<SProject> hashSet2;
        block18: {
            HashSet<SProject> hashSet3;
            block19: {
                block16: {
                    HashSet<SProject> hashSet4;
                    block17: {
                        try {
                            if (vcsRoot == null) {
                                VersionedSettingsManagerImpl.$$$reportNull$$$0(23);
                            }
                        }
                        catch (IllegalArgumentException illegalArgumentException) {
                            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
                        }
                        hashSet2 = new HashSet<SProject>();
                        sVcsRoot = this.myParentRootManager.findRootById(vcsRoot.getId());
                        try {
                            try {
                                try {
                                    if (sVcsRoot != null && !(sVcsRoot instanceof DeletedVcsRoot)) break block16;
                                }
                                catch (IllegalArgumentException illegalArgumentException) {
                                    throw VersionedSettingsManagerImpl.a(illegalArgumentException);
                                }
                                hashSet4 = hashSet2;
                                if (hashSet4 != null) break block17;
                            }
                            catch (IllegalArgumentException illegalArgumentException) {
                                throw VersionedSettingsManagerImpl.a(illegalArgumentException);
                            }
                            VersionedSettingsManagerImpl.$$$reportNull$$$0(24);
                        }
                        catch (IllegalArgumentException illegalArgumentException) {
                            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
                        }
                    }
                    return hashSet4;
                }
                try {
                    try {
                        if (this.isVersionedSettingsEnabledForVcsRoot((VcsRoot)sVcsRoot)) break block18;
                        hashSet3 = hashSet2;
                        if (hashSet3 != null) break block19;
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        throw VersionedSettingsManagerImpl.a(illegalArgumentException);
                    }
                    VersionedSettingsManagerImpl.$$$reportNull$$$0(25);
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw VersionedSettingsManagerImpl.a(illegalArgumentException);
                }
            }
            return hashSet3;
        }
        String string = sVcsRoot.getExternalId();
        try {
            this.traverse(sVcsRoot.getProject(), (sProject, versionedSettingsConfig) -> {
                block6: {
                    try {
                        try {
                            try {
                                if (versionedSettingsConfig == null || !versionedSettingsConfig.isEnabled()) break block6;
                            }
                            catch (IllegalArgumentException illegalArgumentException) {
                                throw VersionedSettingsManagerImpl.a(illegalArgumentException);
                            }
                            if (!string.equals(versionedSettingsConfig.getVcsRootExternalId())) break block6;
                        }
                        catch (IllegalArgumentException illegalArgumentException) {
                            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
                        }
                        hashSet2.add((SProject)sProject);
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        throw VersionedSettingsManagerImpl.a(illegalArgumentException);
                    }
                }
            });
            hashSet = hashSet2;
            if (hashSet == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(26);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        return hashSet;
    }

    @NotNull
    public Set<SProject> getProjectsByOwnSettingsRoot(@NotNull VcsRoot vcsRoot) {
        HashSet<SProject> hashSet;
        HashSet<SProject> hashSet2;
        block13: {
            HashSet<SProject> hashSet3;
            block14: {
                try {
                    if (vcsRoot == null) {
                        VersionedSettingsManagerImpl.$$$reportNull$$$0(27);
                    }
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw VersionedSettingsManagerImpl.a(illegalArgumentException);
                }
                hashSet2 = new HashSet<SProject>();
                try {
                    try {
                        if (this.isVersionedSettingsEnabledForVcsRoot(vcsRoot)) break block13;
                        hashSet3 = hashSet2;
                        if (hashSet3 != null) break block14;
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        throw VersionedSettingsManagerImpl.a(illegalArgumentException);
                    }
                    VersionedSettingsManagerImpl.$$$reportNull$$$0(28);
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw VersionedSettingsManagerImpl.a(illegalArgumentException);
                }
            }
            return hashSet3;
        }
        for (SProject sProject : this.getPossibleUsages(vcsRoot)) {
            try {
                if (!vcsRoot.equals((Object)this.getOwnVersionedSettingsVcsRoot(sProject))) continue;
                hashSet2.add(sProject);
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw VersionedSettingsManagerImpl.a(illegalArgumentException);
            }
        }
        try {
            hashSet = hashSet2;
            if (hashSet == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(29);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        return hashSet;
    }

    @NotNull
    public Set<SProject> getProjectsByOwnSettingsRootInstance(@NotNull VcsRootInstance vcsRootInstance) {
        HashSet<SProject> hashSet;
        HashSet<SProject> hashSet2;
        block10: {
            HashSet<SProject> hashSet3;
            block11: {
                try {
                    if (vcsRootInstance == null) {
                        VersionedSettingsManagerImpl.$$$reportNull$$$0(30);
                    }
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw VersionedSettingsManagerImpl.a(illegalArgumentException);
                }
                hashSet2 = new HashSet<SProject>();
                try {
                    try {
                        if (this.isVersionedSettingsEnabledForVcsRoot((VcsRoot)vcsRootInstance)) break block10;
                        hashSet3 = hashSet2;
                        if (hashSet3 != null) break block11;
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        throw VersionedSettingsManagerImpl.a(illegalArgumentException);
                    }
                    VersionedSettingsManagerImpl.$$$reportNull$$$0(31);
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw VersionedSettingsManagerImpl.a(illegalArgumentException);
                }
            }
            return hashSet3;
        }
        try {
            this.visitProjectsInSettingsRootInstance(vcsRootInstance, (sProject, versionedSettingsConfig) -> {
                try {
                    if (sProject.getExternalId().equals(versionedSettingsConfig.getConfigOwnerExternalId())) {
                        hashSet2.add((SProject)sProject);
                    }
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw VersionedSettingsManagerImpl.a(illegalArgumentException);
                }
            });
            hashSet = hashSet2;
            if (hashSet == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(32);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        return hashSet;
    }

    @NotNull
    public Set<SProject> getProjectsBySettingsRootInstance(@NotNull VcsRootInstance vcsRootInstance) {
        HashSet<SProject> hashSet;
        try {
            if (vcsRootInstance == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(33);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        HashSet<SProject> hashSet2 = new HashSet<SProject>();
        try {
            this.visitProjectsInSettingsRootInstance(vcsRootInstance, (sProject, versionedSettingsConfig) -> hashSet2.add((SProject)sProject));
            hashSet = hashSet2;
            if (hashSet == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(34);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        return hashSet;
    }

    public void visitProjectsInSettingsRootInstance(@NotNull VcsRootInstance vcsRootInstance, @NotNull BiConsumer<SProject, VersionedSettingsConfig> biConsumer) {
        try {
            if (vcsRootInstance == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(35);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        try {
            if (biConsumer == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(36);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        SVcsRoot sVcsRoot = vcsRootInstance.getParent();
        try {
            if (sVcsRoot instanceof DeletedVcsRoot) {
                return;
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        String string = sVcsRoot.getExternalId();
        try {
            if (!this.isVersionedSettingsEnabledForVcsRoot((VcsRoot)sVcsRoot)) {
                return;
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        HashMap hashMap = new HashMap();
        boolean bl = VersionedSettingsManagerImpl.resolveVcsRootInstanceViaParametersOfConfigOwner();
        this.traverse(sVcsRoot.getProject(), (sProject, versionedSettingsConfig) -> {
            block22: {
                block23: {
                    Boolean bl2;
                    block21: {
                        block20: {
                            try {
                                block19: {
                                    try {
                                        try {
                                            if (versionedSettingsConfig == null || !versionedSettingsConfig.isEnabled()) break block19;
                                        }
                                        catch (IllegalArgumentException illegalArgumentException) {
                                            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
                                        }
                                        if (string.equals(versionedSettingsConfig.getVcsRootExternalId())) break block20;
                                    }
                                    catch (IllegalArgumentException illegalArgumentException) {
                                        throw VersionedSettingsManagerImpl.a(illegalArgumentException);
                                    }
                                }
                                return;
                            }
                            catch (IllegalArgumentException illegalArgumentException) {
                                throw VersionedSettingsManagerImpl.a(illegalArgumentException);
                            }
                        }
                        bl2 = (Boolean)hashMap.get(versionedSettingsConfig.getConfigOwnerExternalId());
                        try {
                            try {
                                if (bl2 == null || bl2.booleanValue()) break block21;
                            }
                            catch (IllegalArgumentException illegalArgumentException) {
                                throw VersionedSettingsManagerImpl.a(illegalArgumentException);
                            }
                            return;
                        }
                        catch (IllegalArgumentException illegalArgumentException) {
                            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
                        }
                    }
                    try {
                        try {
                            try {
                                if (bl2 != null || vcsRootInstance.equals((Object)this.getVersionedSettingsVcsRootInstance((SProject)sProject))) break block22;
                            }
                            catch (IllegalArgumentException illegalArgumentException) {
                                throw VersionedSettingsManagerImpl.a(illegalArgumentException);
                            }
                            if (!bl) break block23;
                        }
                        catch (IllegalArgumentException illegalArgumentException) {
                            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
                        }
                        hashMap.put(versionedSettingsConfig.getConfigOwnerExternalId(), false);
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        throw VersionedSettingsManagerImpl.a(illegalArgumentException);
                    }
                }
                return;
            }
            try {
                biConsumer.accept((SProject)sProject, (VersionedSettingsConfig)versionedSettingsConfig);
                if (versionedSettingsConfig.getConfigOwnerExternalId() != null) {
                    hashMap.put(versionedSettingsConfig.getConfigOwnerExternalId(), true);
                }
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw VersionedSettingsManagerImpl.a(illegalArgumentException);
            }
        });
    }

    private void traverse(@NotNull SProject sProject, @NotNull BiConsumer<SProject, VersionedSettingsConfig> biConsumer) {
        VersionedSettingsConfig versionedSettingsConfig;
        SProject sProject2;
        VersionedSettingsManagerImpl versionedSettingsManagerImpl;
        Pair<SProject, VersionedSettingsConfig> pair;
        try {
            if (sProject == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(37);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        try {
            if (biConsumer == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(38);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        SProject sProject3 = sProject.getParentProject();
        try {
            pair = sProject3 != null ? this.findProjectWithConfiguredSettingsRoot(sProject3) : null;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        Pair<SProject, VersionedSettingsConfig> pair2 = pair;
        try {
            versionedSettingsManagerImpl = this;
            sProject2 = sProject;
            versionedSettingsConfig = pair2 != null ? (VersionedSettingsConfig)pair2.second : null;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        versionedSettingsManagerImpl.traverse(sProject2, versionedSettingsConfig, biConsumer);
    }

    private void traverse(@NotNull SProject sProject, @Nullable VersionedSettingsConfig versionedSettingsConfig, @NotNull BiConsumer<SProject, VersionedSettingsConfig> biConsumer) {
        VersionedSettingsConfig versionedSettingsConfig2;
        try {
            if (sProject == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(39);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        try {
            if (biConsumer == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(40);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        VersionedSettingsConfig versionedSettingsConfig3 = this.readConfig(sProject);
        try {
            versionedSettingsConfig2 = versionedSettingsConfig3.isSameSettingsAsParent() ? versionedSettingsConfig : versionedSettingsConfig3;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        VersionedSettingsConfig versionedSettingsConfig4 = versionedSettingsConfig2;
        biConsumer.accept(sProject, versionedSettingsConfig4);
        for (SProject sProject2 : sProject.getOwnProjects()) {
            this.traverse(sProject2, versionedSettingsConfig4, biConsumer);
        }
    }

    @NotNull
    private Collection<SProject> getPossibleUsages(@NotNull VcsRoot vcsRoot) {
        SVcsRoot sVcsRoot;
        block7: {
            List<SProject> list;
            block8: {
                try {
                    if (vcsRoot == null) {
                        VersionedSettingsManagerImpl.$$$reportNull$$$0(41);
                    }
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw VersionedSettingsManagerImpl.a(illegalArgumentException);
                }
                sVcsRoot = this.myParentRootManager.findRootById(vcsRoot.getId());
                try {
                    try {
                        if (sVcsRoot != null) break block7;
                        list = Collections.emptyList();
                        if (list != null) break block8;
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        throw VersionedSettingsManagerImpl.a(illegalArgumentException);
                    }
                    VersionedSettingsManagerImpl.$$$reportNull$$$0(42);
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw VersionedSettingsManagerImpl.a(illegalArgumentException);
                }
            }
            return list;
        }
        return this.getPossibleUsages(sVcsRoot);
    }

    @NotNull
    private Collection<SProject> getPossibleUsages(@NotNull SVcsRoot sVcsRoot) {
        ArrayList<SProject> arrayList;
        ArrayList<SProject> arrayList2;
        block10: {
            ArrayList<SProject> arrayList3;
            block11: {
                try {
                    if (sVcsRoot == null) {
                        VersionedSettingsManagerImpl.$$$reportNull$$$0(43);
                    }
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw VersionedSettingsManagerImpl.a(illegalArgumentException);
                }
                arrayList2 = new ArrayList<SProject>();
                try {
                    try {
                        if (!(sVcsRoot instanceof DeletedVcsRoot)) break block10;
                        arrayList3 = arrayList2;
                        if (arrayList3 != null) break block11;
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        throw VersionedSettingsManagerImpl.a(illegalArgumentException);
                    }
                    VersionedSettingsManagerImpl.$$$reportNull$$$0(44);
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw VersionedSettingsManagerImpl.a(illegalArgumentException);
                }
            }
            return arrayList3;
        }
        SProject sProject = sVcsRoot.getProject();
        try {
            arrayList2.add(sProject);
            arrayList2.addAll(sProject.getProjects());
            arrayList = arrayList2;
            if (arrayList == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(45);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        return arrayList;
    }

    @Nullable
    public VcsRootInstanceEntry getVersionedSettingsVcsRootInstanceEntry(@NotNull SBuildType sBuildType) {
        VcsRootInstanceEntry vcsRootInstanceEntry;
        try {
            if (sBuildType == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(46);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        SProject sProject = sBuildType.getProject();
        VcsRootInstance vcsRootInstance = this.getVersionedSettingsVcsRootInstance(sProject);
        try {
            if (vcsRootInstance == null) {
                return null;
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        CheckoutRules checkoutRules = this.getSettingsRootCheckoutRules(sBuildType, vcsRootInstance);
        try {
            vcsRootInstanceEntry = checkoutRules != null ? new VcsRootInstanceEntry(vcsRootInstance, checkoutRules) : null;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        return vcsRootInstanceEntry;
    }

    private String getFormat(@Nullable VersionedSettingsConfig versionedSettingsConfig) {
        try {
            if (versionedSettingsConfig == null) {
                return "xml";
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        return versionedSettingsConfig.getFormat();
    }

    @NotNull
    public ProjectSettingsGenerator getEffectiveGenerator(@NotNull SProject sProject) throws IllegalArgumentException {
        ProjectSettingsGenerator projectSettingsGenerator;
        try {
            if (sProject == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(47);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        String string = this.getEffectiveFormatOrDefault(sProject);
        ProjectSettingsGenerator projectSettingsGenerator2 = this.myGenerators.findGenerator(string);
        try {
            if (projectSettingsGenerator2 == null) {
                throw new IllegalArgumentException("Config generator not found: '" + string + "'");
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        try {
            projectSettingsGenerator = projectSettingsGenerator2;
            if (projectSettingsGenerator == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(48);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        return projectSettingsGenerator;
    }

    @Nullable
    public ProjectSettingsGenerator getEffectiveGeneratorOrNull(@NotNull SProject sProject) {
        try {
            if (sProject == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(49);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        String string = this.getEffectiveFormatOrDefault(sProject);
        return this.myGenerators.findGenerator(string);
    }

    @Nullable
    public String getEffectiveFormat(@NotNull SProject sProject) {
        String string;
        try {
            if (sProject == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(50);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        Pair<SProject, VersionedSettingsConfig> pair = this.findProjectWithConfiguredSettingsRoot(sProject);
        try {
            string = pair != null ? ((VersionedSettingsConfig)pair.second).getFormat() : null;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        return string;
    }

    @Nullable
    public VersionedSettingsConfig.DslExecutionMode getEffectiveExecutionMode(@NotNull SProject sProject) {
        VersionedSettingsConfig.DslExecutionMode dslExecutionMode;
        try {
            if (sProject == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(51);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        VersionedSettingsConfig versionedSettingsConfig = this.getEffectiveBuildSettingsConfig(sProject);
        try {
            dslExecutionMode = versionedSettingsConfig == null ? null : VersionedSettingsUtil.getEffectiveExecutionMode((VersionedSettingsConfig)versionedSettingsConfig, (VersionedSettingsOptions)this.getOptions());
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        return dslExecutionMode;
    }

    @NotNull
    private String getEffectiveFormatOrDefault(@NotNull SProject sProject) {
        String string;
        try {
            if (sProject == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(52);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        String string2 = this.getEffectiveFormat(sProject);
        try {
            string = string2 != null ? string2 : "xml";
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        return string;
    }

    public boolean isProjectWithCustomSettingsFormat(@NotNull SProject sProject) {
        boolean bl;
        try {
            if (sProject == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(53);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        try {
            bl = !"xml".equals(this.getEffectiveFormatOrDefault(sProject));
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        return bl;
    }

    public boolean isProjectWithReadOnlySettingsFormat(@NotNull SProject sProject) {
        boolean bl;
        block14: {
            block13: {
                try {
                    if (sProject == null) {
                        VersionedSettingsManagerImpl.$$$reportNull$$$0(54);
                    }
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw VersionedSettingsManagerImpl.a(illegalArgumentException);
                }
                Pair<SProject, VersionedSettingsConfig> pair = this.getEffectiveProjectConfig(sProject);
                try {
                    if (pair == null) {
                        return false;
                    }
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw VersionedSettingsManagerImpl.a(illegalArgumentException);
                }
                ProjectSettingsGenerator projectSettingsGenerator = this.myGenerators.findGenerator(((VersionedSettingsConfig)pair.second).getFormat());
                try {
                    if (projectSettingsGenerator == null) {
                        return false;
                    }
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw VersionedSettingsManagerImpl.a(illegalArgumentException);
                }
                try {
                    try {
                        if (projectSettingsGenerator.isUIEditingEnabled((SProject)pair.first) && projectSettingsGenerator.isUIEditingSupported(sProject, (SProject)pair.first)) break block13;
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        throw VersionedSettingsManagerImpl.a(illegalArgumentException);
                    }
                    bl = true;
                    break block14;
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw VersionedSettingsManagerImpl.a(illegalArgumentException);
                }
            }
            bl = false;
        }
        return bl;
    }

    @NotNull
    public Collection<ProjectSettingsGenerator> getGenerators() {
        Collection collection;
        try {
            collection = this.myGenerators.getGenerators();
            if (collection == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(55);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        return collection;
    }

    @Nullable
    public ProjectSettingsGenerator findGenerator(@NotNull String string) {
        try {
            if (string == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(56);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        return this.myGenerators.findGenerator(string);
    }

    @Nullable
    public CheckoutRules getSettingsRootCheckoutRules(@NotNull SBuildType sBuildType, @NotNull VcsRootInstance vcsRootInstance) {
        try {
            if (sBuildType == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(57);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        try {
            if (vcsRootInstance == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(58);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        return this.computeSettingsRootCheckoutRules(Collections.singleton(sBuildType), vcsRootInstance).get(sBuildType);
    }

    @NotNull
    public Map<SBuildType, CheckoutRules> getSettingsRootCheckoutRules(@NotNull Collection<SBuildType> collection, @NotNull VcsRootInstance vcsRootInstance) {
        try {
            if (collection == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(59);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        try {
            if (vcsRootInstance == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(60);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        return this.computeSettingsRootCheckoutRules(collection, vcsRootInstance);
    }

    @NotNull
    private Map<SBuildType, CheckoutRules> computeSettingsRootCheckoutRules(@NotNull Collection<SBuildType> collection, @NotNull VcsRootInstance vcsRootInstance2) {
        HashMap<SBuildType, CheckoutRules> hashMap;
        try {
            if (collection == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(61);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        try {
            if (vcsRootInstance2 == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(62);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        HashMap<String, VcsRootInstance> hashMap2 = new HashMap<String, VcsRootInstance>();
        HashMap<SBuildType, CheckoutRules> hashMap3 = new HashMap<SBuildType, CheckoutRules>();
        HashMap<String, VcsRootInstance> hashMap4 = new HashMap<String, VcsRootInstance>();
        for (SBuildType sBuildType : collection) {
            SProject sProject = sBuildType.getProject();
            Pair<SProject, VersionedSettingsConfig> pair = this.findProjectWithConfiguredSettingsRoot(sProject);
            try {
                if (pair == null) {
                    hashMap2.put(sProject.getProjectId(), null);
                    continue;
                }
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw VersionedSettingsManagerImpl.a(illegalArgumentException);
            }
            ProjectSettingsGenerator projectSettingsGenerator = this.myGenerators.findGenerator(this.getFormat((VersionedSettingsConfig)pair.second));
            try {
                if (projectSettingsGenerator == null) {
                    hashMap2.put(sProject.getProjectId(), null);
                    continue;
                }
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw VersionedSettingsManagerImpl.a(illegalArgumentException);
            }
            String string2 = ((VersionedSettingsConfig)pair.second).getVcsRootExternalId();
            try {
                if (!vcsRootInstance2.getParent().getExternalId().equals(string2)) {
                    continue;
                }
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw VersionedSettingsManagerImpl.a(illegalArgumentException);
            }
            VcsRootInstance vcsRootInstance3 = hashMap4.compute(sProject.getProjectId() + ":" + string2, (string, vcsRootInstance) -> {
                if (vcsRootInstance == null) {
                    vcsRootInstance = this.getVersionedSettingsVcsRootInstance(pair, sProject);
                }
                return vcsRootInstance;
            });
            try {
                if (!vcsRootInstance2.equals((Object)vcsRootInstance3)) {
                    continue;
                }
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw VersionedSettingsManagerImpl.a(illegalArgumentException);
            }
            ProjectSettingsPaths projectSettingsPaths = this.getProjectSettingsPaths(projectSettingsGenerator, (VersionedSettingsConfig)pair.second);
            ArrayList<String> arrayList = new ArrayList<String>();
            this.includeBuildType(projectSettingsGenerator, sBuildType, projectSettingsPaths, arrayList);
            this.includeTemplates(projectSettingsGenerator, sBuildType, vcsRootInstance2, projectSettingsPaths, arrayList, hashMap2);
            this.includeAttachedRoots(projectSettingsGenerator, sBuildType, vcsRootInstance2, projectSettingsPaths, arrayList, hashMap2);
            this.includeProjectsConfigs(projectSettingsGenerator, sProject, vcsRootInstance2, projectSettingsPaths, arrayList, hashMap2);
            arrayList.replaceAll(StringPool::getFromPool);
            hashMap3.put(sBuildType, this.createCheckoutRules(arrayList));
        }
        try {
            hashMap = hashMap3;
            if (hashMap == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(63);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        return hashMap;
    }

    @NotNull
    private CheckoutRules createCheckoutRules(@NotNull List<String> list) {
        CheckoutRules checkoutRules;
        try {
            if (list == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(64);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        try {
            checkoutRules = CheckoutRulesFactory.createFromLines(list);
            if (checkoutRules == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(65);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        return checkoutRules;
    }

    private void includeBuildType(@NotNull ProjectSettingsGenerator projectSettingsGenerator, @NotNull SBuildType sBuildType, @NotNull ProjectSettingsPaths projectSettingsPaths, @NotNull Collection<String> collection) {
        try {
            if (projectSettingsGenerator == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(66);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        try {
            if (sBuildType == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(67);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        try {
            if (projectSettingsPaths == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(68);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        try {
            if (collection == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(69);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        collection.addAll(projectSettingsGenerator.getBuildTypeConfigPaths(sBuildType, projectSettingsPaths));
    }

    private void includeTemplates(@NotNull ProjectSettingsGenerator projectSettingsGenerator, @NotNull SBuildType sBuildType, @NotNull VcsRootInstance vcsRootInstance, @NotNull ProjectSettingsPaths projectSettingsPaths, @NotNull Collection<String> collection, @NotNull Map<String, VcsRootInstance> map) {
        try {
            if (projectSettingsGenerator == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(70);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        try {
            if (sBuildType == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(71);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        try {
            if (vcsRootInstance == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(72);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        try {
            if (projectSettingsPaths == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(73);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        try {
            if (collection == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(74);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        try {
            if (map == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(75);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        for (BuildTypeTemplate buildTypeTemplate : sBuildType.getTemplates()) {
            if (buildTypeTemplate == null) continue;
            SProject sProject = buildTypeTemplate.getProject();
            VcsRootInstance vcsRootInstance2 = this.getCachedVersionedSettingsVcsRootInstance(sProject, map);
            try {
                if (!vcsRootInstance.equals((Object)vcsRootInstance2)) continue;
                collection.addAll(projectSettingsGenerator.getTemplateConfigPaths(buildTypeTemplate, projectSettingsPaths));
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw VersionedSettingsManagerImpl.a(illegalArgumentException);
            }
        }
    }

    private void includeAttachedRoots(@NotNull ProjectSettingsGenerator projectSettingsGenerator, @NotNull SBuildType sBuildType, @NotNull VcsRootInstance vcsRootInstance, @NotNull ProjectSettingsPaths projectSettingsPaths, @NotNull Collection<String> collection, @NotNull Map<String, VcsRootInstance> map) {
        try {
            if (projectSettingsGenerator == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(76);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        try {
            if (sBuildType == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(77);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        try {
            if (vcsRootInstance == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(78);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        try {
            if (projectSettingsPaths == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(79);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        try {
            if (collection == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(80);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        try {
            if (map == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(81);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        for (SVcsRoot sVcsRoot : sBuildType.getVcsRoots()) {
            SProject sProject = sVcsRoot.getProject();
            VcsRootInstance vcsRootInstance2 = this.getCachedVersionedSettingsVcsRootInstance(sProject, map);
            try {
                if (!vcsRootInstance.equals((Object)vcsRootInstance2)) continue;
                collection.addAll(projectSettingsGenerator.getVcsRootConfigPaths(sVcsRoot, projectSettingsPaths));
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw VersionedSettingsManagerImpl.a(illegalArgumentException);
            }
        }
    }

    private void includeProjectsConfigs(@NotNull ProjectSettingsGenerator projectSettingsGenerator, @NotNull SProject sProject, @NotNull VcsRootInstance vcsRootInstance, @NotNull ProjectSettingsPaths projectSettingsPaths, @NotNull Collection<String> collection, @NotNull Map<String, VcsRootInstance> map) {
        try {
            if (projectSettingsGenerator == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(82);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        try {
            if (sProject == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(83);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        try {
            if (vcsRootInstance == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(84);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        try {
            if (projectSettingsPaths == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(85);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        try {
            if (collection == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(86);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        try {
            if (map == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(87);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        collection.addAll(projectSettingsGenerator.getProjectConfigPaths(sProject, projectSettingsPaths));
        for (SProject sProject2 = sProject.getParentProject(); sProject2 != null; sProject2 = sProject2.getParentProject()) {
            VcsRootInstance vcsRootInstance2 = this.getCachedVersionedSettingsVcsRootInstance(sProject2, map);
            try {
                if (!vcsRootInstance.equals((Object)vcsRootInstance2)) continue;
                collection.addAll(projectSettingsGenerator.getProjectConfigPaths(sProject2, projectSettingsPaths));
                continue;
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw VersionedSettingsManagerImpl.a(illegalArgumentException);
            }
        }
    }

    @Nullable
    private VcsRootInstance getCachedVersionedSettingsVcsRootInstance(@NotNull SProject sProject, @NotNull Map<String, VcsRootInstance> map) {
        VcsRootInstance vcsRootInstance;
        try {
            if (sProject == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(88);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        try {
            if (map == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(89);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        if (map.containsKey(sProject.getProjectId())) {
            vcsRootInstance = map.get(sProject.getProjectId());
        } else {
            vcsRootInstance = this.getVersionedSettingsVcsRootInstance(sProject);
            map.put(sProject.getProjectId(), vcsRootInstance);
        }
        return vcsRootInstance;
    }

    @Nullable
    private VcsRootInstance resolveRoot(@NotNull SProject sProject, @Nullable SVcsRoot sVcsRoot) {
        try {
            if (sProject == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(90);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        try {
            if (sVcsRoot == null) {
                return null;
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        List list = this.myVcsRootsManager.getVcsRootInstanceEntries(Collections.singletonList(new VcsRootEntry((VcsRoot)sVcsRoot, CheckoutRules.DEFAULT)), sProject.getValueResolver());
        try {
            if (list.isEmpty()) {
                return null;
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        return ((VcsRootInstanceEntry)list.getFirst()).getVcsRoot();
    }

    @NotNull
    public VersionedSettingsConfig readConfig(@NotNull SProject sProject) {
        VersionedSettingsConfig versionedSettingsConfig;
        try {
            if (sProject == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(91);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        try {
            versionedSettingsConfig = this.myRootIdStorage.readConfig(sProject);
            if (versionedSettingsConfig == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(92);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        return versionedSettingsConfig;
    }

    @Nullable
    public VersionedSettingsConfig.BuildSettingsMode getEffectiveBuildSettingsMode(@NotNull SBuildType sBuildType) {
        try {
            if (sBuildType == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(93);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        return this.getEffectiveBuildSettingsMode(sBuildType.getProject());
    }

    @Nullable
    public VersionedSettingsConfig getEffectiveBuildSettingsConfig(@NotNull SBuildType sBuildType) {
        try {
            if (sBuildType == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(94);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        return this.getEffectiveBuildSettingsConfig(sBuildType.getProject());
    }

    @Nullable
    public VersionedSettingsConfig getEffectiveBuildSettingsConfig(@NotNull SProject sProject) {
        VersionedSettingsConfig versionedSettingsConfig;
        try {
            if (sProject == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(95);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        Pair<SProject, VersionedSettingsConfig> pair = this.findProjectWithConfiguredSettingsRoot(sProject);
        try {
            versionedSettingsConfig = pair != null ? (VersionedSettingsConfig)pair.second : null;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        return versionedSettingsConfig;
    }

    @Nullable
    public Pair<SProject, VersionedSettingsConfig> getEffectiveProjectConfig(@NotNull SProject sProject) {
        try {
            if (sProject == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(96);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        return this.findProjectWithConfiguredSettingsRoot(sProject);
    }

    @Nullable
    public VersionedSettingsConfig.BuildSettingsMode getEffectiveBuildSettingsMode(@NotNull SProject sProject) {
        VersionedSettingsConfig.BuildSettingsMode buildSettingsMode;
        try {
            if (sProject == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(97);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        Pair<SProject, VersionedSettingsConfig> pair = this.findProjectWithConfiguredSettingsRoot(sProject);
        try {
            buildSettingsMode = pair != null ? ((VersionedSettingsConfig)pair.second).getBuildSettingsMode() : null;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        return buildSettingsMode;
    }

    @NotNull
    public List<PersistTask> writeConfig(@NotNull ConfigAction configAction, @NotNull SProject sProject, @NotNull VersionedSettingsConfig versionedSettingsConfig) {
        List<PersistTask> list;
        block19: {
            try {
                if (configAction == null) {
                    VersionedSettingsManagerImpl.$$$reportNull$$$0(98);
                }
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw VersionedSettingsManagerImpl.a(illegalArgumentException);
            }
            try {
                if (sProject == null) {
                    VersionedSettingsManagerImpl.$$$reportNull$$$0(99);
                }
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw VersionedSettingsManagerImpl.a(illegalArgumentException);
            }
            try {
                if (versionedSettingsConfig == null) {
                    VersionedSettingsManagerImpl.$$$reportNull$$$0(100);
                }
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw VersionedSettingsManagerImpl.a(illegalArgumentException);
            }
            if (((ProjectEx)sProject).isPipeline()) {
                Pair<SProject, VersionedSettingsConfig> pair = this.getEffectiveProjectConfig(sProject);
                try {
                    if (pair == null || sProject.equals(pair.getFirst())) break block19;
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw VersionedSettingsManagerImpl.a(illegalArgumentException);
                }
                ProjectSettingsGenerator projectSettingsGenerator = this.getEffectiveGeneratorOrNull(sProject);
                if (projectSettingsGenerator != null) {
                    String string = projectSettingsGenerator.getReadOnlyReason(sProject);
                    try {
                        if (string != null) {
                            throw new IllegalStateException("Cannot write config for read-only pipeline project: " + string);
                        }
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        throw VersionedSettingsManagerImpl.a(illegalArgumentException);
                    }
                }
            }
        }
        try {
            list = this.writeConfigsInternal(configAction, sProject, versionedSettingsConfig);
            if (list == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(101);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        return list;
    }

    @NotNull
    public List<PersistTask> setContextParameters(@NotNull SProject sProject, @NotNull Map<String, String> map) {
        List<PersistTask> list;
        try {
            if (sProject == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(102);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        try {
            if (map == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(103);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        VersionedSettingsConfig versionedSettingsConfig = this.readConfig(sProject);
        versionedSettingsConfig.setDslContextParameters(map);
        ConfigActionEx configActionEx = this.myConfigActionFactory.createAction(sProject, "DSL context parameters updated");
        try {
            configActionEx.setCommitEnabled(false);
            list = this.writeConfigsInternal((ConfigAction)configActionEx, sProject, versionedSettingsConfig);
            if (list == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(104);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        return list;
    }

    private List<PersistTask> writeConfigsInternal(@NotNull ConfigAction configAction, @NotNull SProject sProject, @NotNull VersionedSettingsConfig versionedSettingsConfig) {
        boolean bl;
        block21: {
            VersionedSettingsConfig versionedSettingsConfig2;
            block20: {
                try {
                    if (configAction == null) {
                        VersionedSettingsManagerImpl.$$$reportNull$$$0(105);
                    }
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw VersionedSettingsManagerImpl.a(illegalArgumentException);
                }
                try {
                    if (sProject == null) {
                        VersionedSettingsManagerImpl.$$$reportNull$$$0(106);
                    }
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw VersionedSettingsManagerImpl.a(illegalArgumentException);
                }
                try {
                    if (versionedSettingsConfig == null) {
                        VersionedSettingsManagerImpl.$$$reportNull$$$0(107);
                    }
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw VersionedSettingsManagerImpl.a(illegalArgumentException);
                }
                versionedSettingsConfig2 = this.readConfig(sProject);
                bl = false;
                try {
                    if (!versionedSettingsConfig.isEnabled() || versionedSettingsConfig2.isEnabled()) break block20;
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw VersionedSettingsManagerImpl.a(illegalArgumentException);
                }
                bl = true;
                break block21;
            }
            if (!Objects.equals(versionedSettingsConfig2.getCredentialsStorageType(), versionedSettingsConfig.getCredentialsStorageType())) {
                bl = true;
            } else if (!versionedSettingsConfig2.getFormat().equals(versionedSettingsConfig.getFormat())) {
                bl = true;
            } else if (!Objects.equals(versionedSettingsConfig2.getVcsRootExternalId(), versionedSettingsConfig.getVcsRootExternalId())) {
                bl = true;
            } else if (versionedSettingsConfig2.isUseRelativeIds() != versionedSettingsConfig.isUseRelativeIds()) {
                bl = true;
            } else if (!versionedSettingsConfig2.getDslContextParameters().equals(versionedSettingsConfig.getDslContextParameters())) {
                bl = TeamCityProperties.getBoolean((String)"teamcity.versionedSettings.persistSubProjectOnContextParametersChanges");
            }
        }
        return this.myRootIdStorage.writeConfig(configAction, sProject, versionedSettingsConfig, bl);
    }

    @NotNull
    public VersionedSettingsOptions getOptions() {
        VersionedSettingsOptions versionedSettingsOptions;
        try {
            versionedSettingsOptions = this.myOptions;
            if (versionedSettingsOptions == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(108);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        return versionedSettingsOptions;
    }

    public boolean shouldTakeSettingsFromVcs(@NotNull SBuildType sBuildType, @NotNull BuildPromotionEx buildPromotionEx) {
        block23: {
            try {
                if (sBuildType == null) {
                    VersionedSettingsManagerImpl.$$$reportNull$$$0(109);
                }
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw VersionedSettingsManagerImpl.a(illegalArgumentException);
            }
            try {
                if (buildPromotionEx == null) {
                    VersionedSettingsManagerImpl.$$$reportNull$$$0(110);
                }
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw VersionedSettingsManagerImpl.a(illegalArgumentException);
            }
            if (this.myOptions.isFreezeSettingsEnabled()) {
                boolean bl;
                block25: {
                    block24: {
                        Boolean bl2;
                        VersionedSettingsConfig.BuildSettingsMode buildSettingsMode;
                        block19: {
                            boolean bl3;
                            block22: {
                                block21: {
                                    buildSettingsMode = this.getEffectiveBuildSettingsMode(sBuildType);
                                    bl2 = buildPromotionEx.getFreezeSettings();
                                    try {
                                        block20: {
                                            try {
                                                try {
                                                    if (buildSettingsMode != VersionedSettingsConfig.BuildSettingsMode.PREFER_VCS) break block19;
                                                    if (bl2 == null) break block20;
                                                }
                                                catch (IllegalArgumentException illegalArgumentException) {
                                                    throw VersionedSettingsManagerImpl.a(illegalArgumentException);
                                                }
                                                if (!bl2.booleanValue()) break block21;
                                            }
                                            catch (IllegalArgumentException illegalArgumentException) {
                                                throw VersionedSettingsManagerImpl.a(illegalArgumentException);
                                            }
                                        }
                                        bl3 = true;
                                        break block22;
                                    }
                                    catch (IllegalArgumentException illegalArgumentException) {
                                        throw VersionedSettingsManagerImpl.a(illegalArgumentException);
                                    }
                                }
                                bl3 = false;
                            }
                            return bl3;
                        }
                        try {
                            try {
                                try {
                                    if (buildSettingsMode != VersionedSettingsConfig.BuildSettingsMode.PREFER_CURRENT) break block23;
                                    if (bl2 == null) break block24;
                                }
                                catch (IllegalArgumentException illegalArgumentException) {
                                    throw VersionedSettingsManagerImpl.a(illegalArgumentException);
                                }
                                if (!bl2.booleanValue()) break block24;
                            }
                            catch (IllegalArgumentException illegalArgumentException) {
                                throw VersionedSettingsManagerImpl.a(illegalArgumentException);
                            }
                            bl = true;
                            break block25;
                        }
                        catch (IllegalArgumentException illegalArgumentException) {
                            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
                        }
                    }
                    bl = false;
                }
                return bl;
            }
        }
        return false;
    }

    @NotNull
    public Set<SProject> getSubProjectsWithSameSettings(@NotNull SProject sProject) {
        HashSet<SProject> hashSet;
        try {
            if (sProject == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(111);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        HashSet<SProject> hashSet2 = new HashSet<SProject>();
        Collection collection = sProject.getOwnProjects();
        while (!collection.isEmpty()) {
            HashSet hashSet3 = new HashSet();
            for (SProject sProject2 : collection) {
                VersionedSettingsConfig versionedSettingsConfig = this.readConfig(sProject2);
                try {
                    if (!versionedSettingsConfig.isSameSettingsAsParent()) continue;
                    hashSet2.add(sProject2);
                    hashSet3.addAll(sProject2.getOwnProjects());
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw VersionedSettingsManagerImpl.a(illegalArgumentException);
                }
            }
            collection = hashSet3;
        }
        try {
            hashSet = hashSet2;
            if (hashSet == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(112);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        return hashSet;
    }

    public boolean isShowSettingsChanges(@NotNull SProject sProject) {
        try {
            if (sProject == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(113);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        Pair<SProject, VersionedSettingsConfig> pair = this.findProjectWithConfiguredSettingsRoot(sProject);
        try {
            if (pair == null) {
                return false;
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        return ((VersionedSettingsConfig)pair.getSecond()).isShowSettingsChanges();
    }

    public boolean isShowBranchesFromVersionedSettingsVcsRoot(@NotNull SBuildType sBuildType) {
        boolean bl;
        block11: {
            block10: {
                try {
                    if (sBuildType == null) {
                        VersionedSettingsManagerImpl.$$$reportNull$$$0(114);
                    }
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw VersionedSettingsManagerImpl.a(illegalArgumentException);
                }
                try {
                    if (!this.getOptions().isSettingBranchesEnabled()) {
                        return false;
                    }
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw VersionedSettingsManagerImpl.a(illegalArgumentException);
                }
                VersionedSettingsConfig.BuildSettingsMode buildSettingsMode = this.getEffectiveBuildSettingsMode(sBuildType);
                try {
                    try {
                        if (buildSettingsMode == null || buildSettingsMode == VersionedSettingsConfig.BuildSettingsMode.ALWAYS_USE_CURRENT) break block10;
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        throw VersionedSettingsManagerImpl.a(illegalArgumentException);
                    }
                    bl = true;
                    break block11;
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw VersionedSettingsManagerImpl.a(illegalArgumentException);
                }
            }
            bl = false;
        }
        return bl;
    }

    @Nullable
    public String getCredentialsStorageType(@NotNull SProject sProject) {
        try {
            if (sProject == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(115);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        Pair<SProject, VersionedSettingsConfig> pair = this.findProjectWithConfiguredSettingsRoot(sProject);
        try {
            if (pair == null) {
                return TeamCityProperties.getPropertyOrNull((String)"teamcity.projectCredentials.defaultStorageType");
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        return ((VersionedSettingsConfig)pair.getSecond()).getCredentialsStorageType();
    }

    @NotNull
    public TriggerRules excludeSettingRoots(@Nullable String string, @NotNull Collection<SBuildType> collection) {
        TriggerRules triggerRules;
        try {
            if (collection == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(116);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        TriggerRules triggerRules2 = TriggerRulesFactory.fromString((String)string);
        StringBuilder stringBuilder = new StringBuilder();
        try {
            if (string != null) {
                stringBuilder.append(string).append("\n");
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        Set<VcsRootInstance> set = this.getSettingsRootsToBeExcluded(collection, triggerRules2);
        for (VcsRootInstance vcsRootInstance : set) {
            stringBuilder.append("-:").append("rootInstance").append("=").append(vcsRootInstance.getId()).append(":**");
        }
        try {
            triggerRules = TriggerRulesFactory.fromString((String)stringBuilder.toString());
            if (triggerRules == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(117);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        return triggerRules;
    }

    @NotNull
    private Set<VcsRootInstance> getSettingsRootsToBeExcluded(@NotNull Collection<SBuildType> collection, TriggerRules triggerRules) {
        HashSet<VcsRootInstance> hashSet;
        try {
            if (collection == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(118);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        Set<VcsRootInstance> set = this.getRegularRoots(collection);
        HashSet<VcsRootInstance> hashSet2 = new HashSet<VcsRootInstance>();
        HashSet<SProject> hashSet3 = new HashSet<SProject>();
        for (SBuildType sBuildType : collection) {
            SProject sProject = sBuildType.getProject();
            try {
                if (!hashSet3.add(sProject)) {
                    continue;
                }
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw VersionedSettingsManagerImpl.a(illegalArgumentException);
            }
            VcsRootInstance vcsRootInstance = this.getVersionedSettingsVcsRootInstance(sProject);
            try {
                if (vcsRootInstance == null) {
                    continue;
                }
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw VersionedSettingsManagerImpl.a(illegalArgumentException);
            }
            try {
                if (hashSet2.contains(vcsRootInstance)) {
                    continue;
                }
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw VersionedSettingsManagerImpl.a(illegalArgumentException);
            }
            try {
                if (set.contains(vcsRootInstance)) {
                    continue;
                }
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw VersionedSettingsManagerImpl.a(illegalArgumentException);
            }
            try {
                if (triggerRules.getUsedVcsRootsIds().contains(vcsRootInstance.getParent().getExternalId())) {
                    continue;
                }
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw VersionedSettingsManagerImpl.a(illegalArgumentException);
            }
            hashSet2.add(vcsRootInstance);
        }
        try {
            hashSet = hashSet2;
            if (hashSet == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(119);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        return hashSet;
    }

    @NotNull
    public Set<VcsRootInstance> getExcludedSettingRoots(@Nullable String string, @NotNull Collection<SBuildType> collection) {
        try {
            if (collection == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(120);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        return this.getSettingsRootsToBeExcluded(collection, TriggerRulesFactory.fromString((String)string));
    }

    @NotNull
    private Set<VcsRootInstance> getRegularRoots(@NotNull Collection<SBuildType> collection) {
        HashSet<VcsRootInstance> hashSet;
        try {
            if (collection == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(121);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        HashSet<VcsRootInstance> hashSet2 = new HashSet<VcsRootInstance>();
        for (SBuildType sBuildType : collection) {
            hashSet2.addAll(sBuildType.getVcsRootInstances());
        }
        try {
            hashSet = hashSet2;
            if (hashSet == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(122);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        return hashSet;
    }

    @NotNull
    private List<String> generateSettings(@NotNull ProjectSettingsGenerator projectSettingsGenerator, @NotNull SProject sProject, @NotNull File file, @NotNull ProjectSettingsGeneratorConfig projectSettingsGeneratorConfig) {
        List<String> list;
        HashMap hashMap;
        block19: {
            ArrayList<String> arrayList;
            block20: {
                try {
                    if (projectSettingsGenerator == null) {
                        VersionedSettingsManagerImpl.$$$reportNull$$$0(123);
                    }
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw VersionedSettingsManagerImpl.a(illegalArgumentException);
                }
                try {
                    if (sProject == null) {
                        VersionedSettingsManagerImpl.$$$reportNull$$$0(124);
                    }
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw VersionedSettingsManagerImpl.a(illegalArgumentException);
                }
                try {
                    if (file == null) {
                        VersionedSettingsManagerImpl.$$$reportNull$$$0(125);
                    }
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw VersionedSettingsManagerImpl.a(illegalArgumentException);
                }
                try {
                    if (projectSettingsGeneratorConfig == null) {
                        VersionedSettingsManagerImpl.$$$reportNull$$$0(126);
                    }
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw VersionedSettingsManagerImpl.a(illegalArgumentException);
                }
                hashMap = new HashMap();
                ArrayList<String> arrayList2 = new ArrayList<String>();
                try {
                    try {
                        projectSettingsGenerator.ensureConfigCompatibility(sProject, arrayList2, hashMap, projectSettingsGeneratorConfig.getOptions());
                        if (arrayList2.isEmpty()) break block19;
                        arrayList = arrayList2;
                        if (arrayList != null) break block20;
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        throw VersionedSettingsManagerImpl.a(illegalArgumentException);
                    }
                    VersionedSettingsManagerImpl.$$$reportNull$$$0(127);
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw VersionedSettingsManagerImpl.a(illegalArgumentException);
                }
            }
            return arrayList;
        }
        try {
            projectSettingsGeneratorConfig.setIdsToSubstitute(hashMap);
            projectSettingsGenerator.writeProjectConfigs(sProject, file, projectSettingsGeneratorConfig);
            list = Collections.emptyList();
            if (list == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(128);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        return list;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NotNull
    public List<String> generateSettingsZip(@NotNull SProject sProject, @NotNull File file, @NotNull String string, @NotNull ProjectSettingsGeneratorConfig projectSettingsGeneratorConfig) throws IOException {
        List<String> list;
        ProjectSettingsGenerator projectSettingsGenerator;
        ZipWriter zipWriter;
        File file2;
        block48: {
            block47: {
                List<String> list2;
                List<String> list3;
                try {
                    if (sProject == null) {
                        VersionedSettingsManagerImpl.$$$reportNull$$$0(129);
                    }
                }
                catch (IOException iOException) {
                    throw VersionedSettingsManagerImpl.a(iOException);
                }
                try {
                    if (file == null) {
                        VersionedSettingsManagerImpl.$$$reportNull$$$0(130);
                    }
                }
                catch (IOException iOException) {
                    throw VersionedSettingsManagerImpl.a(iOException);
                }
                try {
                    if (string == null) {
                        VersionedSettingsManagerImpl.$$$reportNull$$$0(131);
                    }
                }
                catch (IOException iOException) {
                    throw VersionedSettingsManagerImpl.a(iOException);
                }
                try {
                    if (projectSettingsGeneratorConfig == null) {
                        VersionedSettingsManagerImpl.$$$reportNull$$$0(132);
                    }
                }
                catch (IOException iOException) {
                    throw VersionedSettingsManagerImpl.a(iOException);
                }
                file2 = null;
                zipWriter = null;
                try {
                    projectSettingsGenerator = this.findGenerator(string);
                    try {
                        if (projectSettingsGenerator == null) {
                            throw new IllegalArgumentException("Unknown format: '" + string + "'");
                        }
                    }
                    catch (IOException iOException) {
                        throw VersionedSettingsManagerImpl.a(iOException);
                    }
                    file2 = FileUtil.createTempDirectory((String)"projectSettings", (String)"");
                    List<String> list4 = this.generateSettings(projectSettingsGenerator, sProject, file2, projectSettingsGeneratorConfig);
                    if (list4.isEmpty()) break block47;
                    list3 = list4;
                }
                catch (Throwable throwable) {
                    try {
                        if (zipWriter != null) {
                            zipWriter.close();
                        }
                    }
                    catch (IOException iOException) {
                        throw VersionedSettingsManagerImpl.a(iOException);
                    }
                    try {
                        if (file2 != null) {
                            FileUtil.delete(file2);
                        }
                    }
                    catch (IOException iOException) {
                        throw VersionedSettingsManagerImpl.a(iOException);
                    }
                    throw throwable;
                }
                try {
                    if (zipWriter != null) {
                        zipWriter.close();
                    }
                }
                catch (IOException iOException) {
                    throw VersionedSettingsManagerImpl.a(iOException);
                }
                try {
                    if (file2 != null) {
                        FileUtil.delete((File)file2);
                    }
                }
                catch (IOException iOException) {
                    throw VersionedSettingsManagerImpl.a(iOException);
                }
                try {
                    list2 = list3;
                    if (list2 == null) {
                        VersionedSettingsManagerImpl.$$$reportNull$$$0(133);
                    }
                }
                catch (IOException iOException) {
                    throw VersionedSettingsManagerImpl.a(iOException);
                }
                return list2;
            }
            zipWriter = this.myZipFactory.createZipArchive(file.getAbsolutePath());
            ProjectSettingsPaths projectSettingsPaths = this.getEffectiveProjectSettingsPaths(sProject);
            File file3 = new File(file2, projectSettingsPaths.getSettingsDirName());
            try {
                if (!file3.isDirectory() || !FileUtil.isAncestor((File)file2, (File)file3.getCanonicalFile(), (boolean)false)) break block48;
            }
            catch (IOException iOException) {
                throw VersionedSettingsManagerImpl.a(iOException);
            }
            file2 = new File(file2, projectSettingsPaths.getSettingsDirName());
        }
        String string2 = projectSettingsGenerator.getZipReadme();
        try {
            if (string2 != null) {
                FileUtil.writeFileAndReportErrors((File)new File(file2, "README"), (String)string2);
            }
        }
        catch (IOException iOException) {
            throw VersionedSettingsManagerImpl.a(iOException);
        }
        this.zipDir(zipWriter, file2, projectSettingsGenerator, sProject);
        List<String> list5 = Collections.emptyList();
        try {
            if (zipWriter != null) {
                zipWriter.close();
            }
        }
        catch (IOException iOException) {
            throw VersionedSettingsManagerImpl.a(iOException);
        }
        try {
            if (file2 != null) {
                FileUtil.delete((File)file2);
            }
        }
        catch (IOException iOException) {
            throw VersionedSettingsManagerImpl.a(iOException);
        }
        try {
            list = list5;
            if (list == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(134);
            }
        }
        catch (IOException iOException) {
            throw VersionedSettingsManagerImpl.a(iOException);
        }
        return list;
    }

    /*
     * Exception decompiling
     */
    private void zipDir(@NotNull ZipWriter var1_1, @NotNull File var2_2, @NotNull ProjectSettingsGenerator var3_3, @NotNull SProject var4_4) throws IOException {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [6[TRYBLOCK]], but top level block is 21[UNCONDITIONALDOLOOP]
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

    @Nullable
    private SVcsRoot findAccessibleVcsRoot(@NotNull SProject sProject, @NotNull String string) {
        Object object;
        try {
            if (sProject == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(139);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        try {
            if (string == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(140);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        SVcsRoot sVcsRoot = this.myProjectManager.findVcsRootByExternalId(string);
        try {
            if (sVcsRoot == null) {
                return null;
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        try {
            object = sVcsRoot.isAccessibleFrom(sProject) ? sVcsRoot : null;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        return object;
    }

    @NotNull
    public ConfigAction createEnableConfigActionAfterUpgrade(int n, int n2) {
        ConfigActionEx configActionEx;
        block9: {
            ConfigActionEx configActionEx2;
            block10: {
                try {
                    try {
                        try {
                            if (n != -1 && n != n2) break block9;
                        }
                        catch (IllegalArgumentException illegalArgumentException) {
                            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
                        }
                        configActionEx2 = this.myConfigActionFactory.createAction("TeamCity settings change: convert settings to version " + n2);
                        if (configActionEx2 != null) break block10;
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        throw VersionedSettingsManagerImpl.a(illegalArgumentException);
                    }
                    VersionedSettingsManagerImpl.$$$reportNull$$$0(141);
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw VersionedSettingsManagerImpl.a(illegalArgumentException);
                }
            }
            return configActionEx2;
        }
        try {
            configActionEx = this.myConfigActionFactory.createAction("TeamCity settings change: convert settings from version " + n + " to version " + n2);
            if (configActionEx == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(142);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        return configActionEx;
    }

    public boolean isEnableSettingsActionAfterUpgrade(@NotNull ConfigAction configAction) {
        try {
            if (configAction == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(143);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        return configAction.getDescription().startsWith("TeamCity settings change: convert settings");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean enableVersionedSettings() {
        if (this.myEnableSettingsInProgress.compareAndSet(false, true)) {
            try {
                this.setStatus("Enabling versioned settings...");
                int n = this.myVersionedSettingsDBVersion.getLastUsedVersion();
                int n2 = this.myVersionedSettingsDBVersion.getCurrentVersion();
                this.myVersionedSettingsOptions.resetConvertedProjectsIds();
                this.myVersionedSettingsDBVersion.setLastUsedVersion(n2);
                this.commitAllProjectConfigs(n, n2);
                this.setStatus("Versioned settings enabled");
                boolean bl = true;
                return bl;
            }
            finally {
                this.myEnableSettingsInProgress.set(false);
            }
        }
        return false;
    }

    @NotNull
    public ProjectSettingsPaths getEffectiveProjectSettingsPaths(@NotNull SProject sProject) {
        try {
            if (sProject == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(144);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        ProjectSettingsGenerator projectSettingsGenerator = this.myGenerators.getDefaultGenerator();
        Pair<SProject, VersionedSettingsConfig> pair = this.getEffectiveProjectConfig(sProject);
        if (pair != null) {
            ProjectSettingsGenerator projectSettingsGenerator2 = this.myGenerators.findGenerator(((VersionedSettingsConfig)pair.second).getFormat());
            if (projectSettingsGenerator2 == null) {
                projectSettingsGenerator2 = projectSettingsGenerator;
            }
            return this.getProjectSettingsPaths(projectSettingsGenerator2, (VersionedSettingsConfig)pair.second);
        }
        return this.getProjectSettingsPaths(projectSettingsGenerator, null);
    }

    @NotNull
    private ProjectSettingsPaths getProjectSettingsPaths(@NotNull ProjectSettingsGenerator projectSettingsGenerator, @Nullable VersionedSettingsConfig versionedSettingsConfig) {
        ProjectSettingsPaths projectSettingsPaths;
        try {
            if (projectSettingsGenerator == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(145);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        String string = null;
        if (versionedSettingsConfig != null) {
            string = versionedSettingsConfig.getSettingsPath();
        }
        try {
            projectSettingsPaths = projectSettingsGenerator.getSettingsPathsProvider().getProjectSettingsPaths(string);
            if (projectSettingsPaths == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(146);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        return projectSettingsPaths;
    }

    @Nullable
    public BuildRevision getSettingsRootRevision(@NotNull BuildPromotionEx buildPromotionEx) {
        try {
            if (buildPromotionEx == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(147);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        BuildTypeEx buildTypeEx = buildPromotionEx.getParentBuildType();
        try {
            if (buildTypeEx == null) {
                return null;
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        ProjectEx projectEx = buildTypeEx.getProject();
        VcsRootInstance vcsRootInstance = this.getVersionedSettingsVcsRootInstance((SProject)projectEx);
        try {
            if (vcsRootInstance == null) {
                return null;
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        String string = buildPromotionEx.getVersionedSettingsRevision(vcsRootInstance);
        try {
            if (string == null) {
                return null;
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        ProjectSettingsPaths projectSettingsPaths = this.getEffectiveProjectSettingsPaths((SProject)projectEx);
        return new BuildRevision(vcsRootInstance, string, "+:" + projectSettingsPaths.getSettingsDirName(), string);
    }

    private void setStatus(@NotNull String string) {
        try {
            if (string == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(148);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        this.setStatus(this.myProjectManager.getProjects(), string);
    }

    private void setStatus(@NotNull Collection<SProject> collection, @NotNull String string) {
        try {
            if (collection == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(149);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        try {
            if (string == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(150);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        for (SProject sProject : collection) {
            try {
                if (this.getVersionedSettingsVcsRoot(sProject) == null) continue;
                this.myVersionedSettingsStatusTracker.setStatus(sProject, string);
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw VersionedSettingsManagerImpl.a(illegalArgumentException);
            }
        }
    }

    private void commitAllProjectConfigs(int n, int n2) {
        ConfigAction configAction = this.createEnableConfigActionAfterUpgrade(n, n2);
        for (SProject sProject : this.getProjectsWithOwnVersionedSettings()) {
            this.commitProjectConfigs(sProject, configAction);
        }
    }

    private void commitProjectConfigs(@NotNull SProject sProject, @NotNull ConfigAction configAction) {
        try {
            if (sProject == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(151);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        try {
            if (configAction == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(152);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        Set<SProject> set = this.getSubProjectsWithSameSettings(sProject);
        set.add(sProject);
        String string = "Commit VCS project settings for " + sProject.describe(false) + " with " + set.size() + " subprojects";
        NamedThreadFactory.executeWithNewThreadName((String)string, () -> {
            for (SProject sProject2 : set) {
                sProject2.persist(configAction);
            }
            this.myConverterChangesStorage.resetConverterChanges(sProject);
        });
    }

    public void setConverterChangesStorage(@NotNull ConverterChangesStorage converterChangesStorage) {
        try {
            if (converterChangesStorage == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(153);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        this.myConverterChangesStorage = converterChangesStorage;
    }

    public void setUseParentSettingsForDisabledPipelines(@NotNull SProject sProject, @NotNull ConfigAction configAction) {
        try {
            if (sProject == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(154);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        try {
            if (configAction == null) {
                VersionedSettingsManagerImpl.$$$reportNull$$$0(155);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw VersionedSettingsManagerImpl.a(illegalArgumentException);
        }
        Set<SProject> set = this.getSubProjectsWithSameSettings(sProject);
        set.add(sProject);
        for (SProject sProject2 : set) {
            ProjectEx projectEx = (ProjectEx)sProject2;
            try {
                if (projectEx.isPipeline()) {
                    continue;
                }
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw VersionedSettingsManagerImpl.a(illegalArgumentException);
            }
            for (PipelineProject pipelineProject : projectEx.getOwnPipelines()) {
                VersionedSettingsConfig versionedSettingsConfig = this.readConfig((SProject)pipelineProject);
                try {
                    if (versionedSettingsConfig.isEnabled() || pipelineProject.isVirtual()) continue;
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw VersionedSettingsManagerImpl.a(illegalArgumentException);
                }
                VersionedSettingsConfig versionedSettingsConfig2 = new VersionedSettingsConfig();
                versionedSettingsConfig2.setEnabled(true);
                versionedSettingsConfig2.setVcsRootExternalId(null);
                this.writeConfig(configAction, (SProject)pipelineProject, versionedSettingsConfig2);
            }
        }
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
