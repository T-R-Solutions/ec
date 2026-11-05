/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.intellij.openapi.util.Pair
 *  jetbrains.buildServer.pipeline.Pipeline
 *  jetbrains.buildServer.pipeline.PipelineProject
 *  jetbrains.buildServer.pipeline.versionedSettings.PipelineVersionedSettings
 *  jetbrains.buildServer.pipeline.versionedSettings.yaml.YamlPipelineCommitter
 *  jetbrains.buildServer.pipeline.versionedSettings.yaml.YamlPipelineCommitter$YamlCommitOutcome
 *  jetbrains.buildServer.serverSide.ConfigAction
 *  jetbrains.buildServer.serverSide.ParametersSupport
 *  jetbrains.buildServer.serverSide.SProject
 *  jetbrains.buildServer.serverSide.SecurityContextEx
 *  jetbrains.buildServer.serverSide.impl.versionedSettings.CommitNotSupported
 *  jetbrains.buildServer.serverSide.impl.versionedSettings.VersionedSettingsConfig
 *  jetbrains.buildServer.serverSide.impl.versionedSettings.VersionedSettingsConfig$BuildSettingsMode
 *  jetbrains.buildServer.serverSide.impl.versionedSettings.VersionedSettingsConfig$DslExecutionMode
 *  jetbrains.buildServer.serverSide.impl.versionedSettings.VersionedSettingsConfigUpdater
 *  jetbrains.buildServer.serverSide.impl.versionedSettings.VersionedSettingsConfigUpdater$Result
 *  jetbrains.buildServer.serverSide.impl.versionedSettings.VersionedSettingsConfigUpdater$Result$ResultStatus
 *  jetbrains.buildServer.serverSide.impl.versionedSettings.VersionedSettingsUpdateConfig
 *  jetbrains.buildServer.serverSide.impl.versionedSettings.VersionedSettingsUpdater
 *  jetbrains.buildServer.serverSide.versionedSettings.VersionedSettingsManager
 *  jetbrains.buildServer.users.SUser
 *  jetbrains.buildServer.util.Util
 *  jetbrains.buildServer.vcs.VcsException
 *  jetbrains.buildServer.vcs.VcsRootInstance
 *  jetbrains.buildServer.vcs.VcsRootInstancesManagerEx
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 *  org.springframework.stereotype.Service
 */
package jetbrains.buildServer.pipeline.impl.manager;

import com.intellij.openapi.util.Pair;
import java.io.IOException;
import java.util.Collections;
import java.util.Objects;
import jetbrains.buildServer.pipeline.Pipeline;
import jetbrains.buildServer.pipeline.PipelineProject;
import jetbrains.buildServer.pipeline.versionedSettings.PipelineVersionedSettings;
import jetbrains.buildServer.pipeline.versionedSettings.yaml.YamlPipelineCommitter;
import jetbrains.buildServer.serverSide.ConfigAction;
import jetbrains.buildServer.serverSide.ParametersSupport;
import jetbrains.buildServer.serverSide.SProject;
import jetbrains.buildServer.serverSide.SecurityContextEx;
import jetbrains.buildServer.serverSide.impl.versionedSettings.CommitNotSupported;
import jetbrains.buildServer.serverSide.impl.versionedSettings.VersionedSettingsConfig;
import jetbrains.buildServer.serverSide.impl.versionedSettings.VersionedSettingsConfigUpdater;
import jetbrains.buildServer.serverSide.impl.versionedSettings.VersionedSettingsUpdateConfig;
import jetbrains.buildServer.serverSide.impl.versionedSettings.VersionedSettingsUpdater;
import jetbrains.buildServer.serverSide.versionedSettings.VersionedSettingsManager;
import jetbrains.buildServer.users.SUser;
import jetbrains.buildServer.util.Util;
import jetbrains.buildServer.vcs.VcsException;
import jetbrains.buildServer.vcs.VcsRootInstance;
import jetbrains.buildServer.vcs.VcsRootInstancesManagerEx;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;

/*
 * Exception performing whole class analysis ignored.
 */
@Service
public class PipelineVersionedSettingsManager {
    @NotNull
    private final VersionedSettingsManager myVersionedSettingsManager;
    @NotNull
    private final VersionedSettingsConfigUpdater myVersionedSettingsConfigUpdater;
    @NotNull
    private final VersionedSettingsUpdater myVersionedSettingsUpdater;
    @NotNull
    private final VcsRootInstancesManagerEx myVcsRootInstancesManager;
    @NotNull
    private final SecurityContextEx mySecurityContext;
    @NotNull
    private final YamlPipelineCommitter myYamlPipelineCommitter;

    public PipelineVersionedSettingsManager(@NotNull VersionedSettingsManager versionedSettingsManager, @NotNull VersionedSettingsConfigUpdater versionedSettingsConfigUpdater, @NotNull VersionedSettingsUpdater versionedSettingsUpdater, @NotNull VcsRootInstancesManagerEx vcsRootInstancesManagerEx, @NotNull SecurityContextEx securityContextEx, @NotNull YamlPipelineCommitter yamlPipelineCommitter) {
        if (versionedSettingsManager == null) {
            PipelineVersionedSettingsManager.$$$reportNull$$$0(0);
        }
        if (versionedSettingsConfigUpdater == null) {
            PipelineVersionedSettingsManager.$$$reportNull$$$0(1);
        }
        if (versionedSettingsUpdater == null) {
            PipelineVersionedSettingsManager.$$$reportNull$$$0(2);
        }
        if (vcsRootInstancesManagerEx == null) {
            PipelineVersionedSettingsManager.$$$reportNull$$$0(3);
        }
        if (securityContextEx == null) {
            PipelineVersionedSettingsManager.$$$reportNull$$$0(4);
        }
        if (yamlPipelineCommitter == null) {
            PipelineVersionedSettingsManager.$$$reportNull$$$0(5);
        }
        this.myVersionedSettingsManager = versionedSettingsManager;
        this.myVersionedSettingsConfigUpdater = versionedSettingsConfigUpdater;
        this.myVersionedSettingsUpdater = versionedSettingsUpdater;
        this.myVcsRootInstancesManager = vcsRootInstancesManagerEx;
        this.mySecurityContext = securityContextEx;
        this.myYamlPipelineCommitter = yamlPipelineCommitter;
    }

    @NotNull
    public PipelineVersionedSettings getVersionedSettings(@NotNull PipelineProject pipelineProject) {
        try {
            if (pipelineProject == null) {
                PipelineVersionedSettingsManager.$$$reportNull$$$0(6);
            }
        }
        catch (RuntimeException runtimeException) {
            throw PipelineVersionedSettingsManager.a(runtimeException);
        }
        VersionedSettingsConfig versionedSettingsConfig = this.myVersionedSettingsManager.readConfig((SProject)pipelineProject);
        return new PipelineVersionedSettings(this.myVersionedSettingsManager.hasVersionedSettings((SProject)pipelineProject), versionedSettingsConfig.getSettingsPath(), versionedSettingsConfig.getVcsRootExternalId(), null, this.myVersionedSettingsManager.getEffectiveFormat((SProject)pipelineProject));
    }

    public boolean setAndPersistVersionedSettings(@NotNull Pipeline pipeline, @NotNull PipelineVersionedSettings pipelineVersionedSettings) {
        String string;
        SUser sUser;
        PipelineProject pipelineProject;
        VersionedSettingsConfigUpdater versionedSettingsConfigUpdater;
        String string2;
        SUser sUser2;
        PipelineProject pipelineProject2;
        block33: {
            block31: {
                block32: {
                    block29: {
                        block30: {
                            try {
                                if (pipeline == null) {
                                    PipelineVersionedSettingsManager.$$$reportNull$$$0(7);
                                }
                            }
                            catch (RuntimeException runtimeException) {
                                throw PipelineVersionedSettingsManager.a(runtimeException);
                            }
                            try {
                                if (pipelineVersionedSettings == null) {
                                    PipelineVersionedSettingsManager.$$$reportNull$$$0(8);
                                }
                            }
                            catch (RuntimeException runtimeException) {
                                throw PipelineVersionedSettingsManager.a(runtimeException);
                            }
                            this.mySecurityContext.getAccessChecker().checkCanEditProject((SProject)pipeline.getProject());
                            pipelineProject2 = pipeline.getProject();
                            sUser2 = (SUser)this.mySecurityContext.getAuthorityHolder().getAssociatedUser();
                            try {
                                if (this.isStoredInParentProject((SProject)pipelineProject2)) {
                                    return false;
                                }
                            }
                            catch (RuntimeException runtimeException) {
                                throw PipelineVersionedSettingsManager.a(runtimeException);
                            }
                            try {
                                try {
                                    try {
                                        PipelineVersionedSettings.validateFilename((String)pipelineVersionedSettings.getFilename());
                                        if (pipelineVersionedSettings.isEnabled() || !"import".equals(pipelineVersionedSettings.getConfirmationDecision())) break block29;
                                    }
                                    catch (RuntimeException runtimeException) {
                                        throw PipelineVersionedSettingsManager.a(runtimeException);
                                    }
                                    if (pipeline.getVcsRoot() != null) break block30;
                                }
                                catch (RuntimeException runtimeException) {
                                    throw PipelineVersionedSettingsManager.a(runtimeException);
                                }
                                throw new RuntimeException("Cannot import settings from repository. Main vcs root is not set.");
                            }
                            catch (RuntimeException runtimeException) {
                                throw PipelineVersionedSettingsManager.a(runtimeException);
                            }
                        }
                        this.loadPipelineFromVcsOnce(pipeline, pipelineVersionedSettings, (SProject)pipelineProject2, sUser2);
                        return true;
                    }
                    VersionedSettingsConfig versionedSettingsConfig = this.myVersionedSettingsManager.readConfig((SProject)pipelineProject2);
                    try {
                        try {
                            if (this.myVersionedSettingsManager.hasVersionedSettings((SProject)pipelineProject2) != pipelineVersionedSettings.isEnabled()) break block31;
                            if (!Objects.equals(pipelineVersionedSettings.getFilename(), versionedSettingsConfig.getSettingsPath())) break block32;
                        }
                        catch (RuntimeException runtimeException) {
                            throw PipelineVersionedSettingsManager.a(runtimeException);
                        }
                        return false;
                    }
                    catch (RuntimeException runtimeException) {
                        throw PipelineVersionedSettingsManager.a(runtimeException);
                    }
                }
                try {
                    if (pipelineVersionedSettings.isEnabled()) {
                        throw new IllegalStateException("Cannot update Versioned Settings YAML file path for already enabled Versioned Settings");
                    }
                }
                catch (RuntimeException runtimeException) {
                    throw PipelineVersionedSettingsManager.a(runtimeException);
                }
            }
            string2 = pipelineVersionedSettings.getVcsRootExtId();
            try {
                if (string2 != null || pipeline.getVcsRoot() == null) break block33;
            }
            catch (RuntimeException runtimeException) {
                throw PipelineVersionedSettingsManager.a(runtimeException);
            }
            string2 = pipeline.getVcsRoot().getRoot().getExternalId();
        }
        try {
            versionedSettingsConfigUpdater = this.myVersionedSettingsConfigUpdater;
            pipelineProject = pipelineProject2;
            sUser = sUser2;
            string = pipelineVersionedSettings.isEnabled() ? "enabled" : "disabled";
        }
        catch (RuntimeException runtimeException) {
            throw PipelineVersionedSettingsManager.a(runtimeException);
        }
        VersionedSettingsConfigUpdater.Result result = versionedSettingsConfigUpdater.updateConfig((SProject)pipelineProject, sUser, string, string2, false, true, (String)Util.ofNullable((Object)pipelineVersionedSettings.getConfirmationDecision(), (Object)"override"), "pipelines-yaml", true, true, true, pipelineVersionedSettings.getFilename(), VersionedSettingsConfig.BuildSettingsMode.PREFER_VCS.name(), false, VersionedSettingsConfig.DslExecutionMode.SANDBOX);
        try {
            if (result.getStatus().equals((Object)VersionedSettingsConfigUpdater.Result.ResultStatus.ERROR)) {
                throw new IllegalStateException(result.getMessage());
            }
        }
        catch (RuntimeException runtimeException) {
            throw PipelineVersionedSettingsManager.a(runtimeException);
        }
        return "import".equals(pipelineVersionedSettings.getConfirmationDecision());
    }

    private void loadPipelineFromVcsOnce(@NotNull Pipeline pipeline, @NotNull PipelineVersionedSettings pipelineVersionedSettings, SProject sProject, SUser sUser) {
        String string;
        try {
            if (pipeline == null) {
                PipelineVersionedSettingsManager.$$$reportNull$$$0(9);
            }
        }
        catch (VcsException vcsException) {
            throw PipelineVersionedSettingsManager.a((Exception)((Object)vcsException));
        }
        try {
            if (pipelineVersionedSettings == null) {
                PipelineVersionedSettingsManager.$$$reportNull$$$0(10);
            }
        }
        catch (VcsException vcsException) {
            throw PipelineVersionedSettingsManager.a((Exception)((Object)vcsException));
        }
        VersionedSettingsConfig versionedSettingsConfig = new VersionedSettingsConfig();
        versionedSettingsConfig.setUseRelativeIds(true);
        versionedSettingsConfig.setEnabled(false);
        versionedSettingsConfig.setFormat("pipelines-yaml");
        versionedSettingsConfig.setSettingsPath(pipelineVersionedSettings.getFilename());
        this.myVersionedSettingsManager.writeConfig(sProject.createConfigAction(sUser, "Import pipeline from repository yaml without sync"), sProject, versionedSettingsConfig);
        VcsRootInstance vcsRootInstance = this.myVcsRootInstancesManager.getVcsRootInstance(pipeline.getVcsRoot().getRoot(), (ParametersSupport)sProject);
        try {
            string = vcsRootInstance.getCurrentRevision().getVersion();
        }
        catch (VcsException vcsException) {
            throw new RuntimeException("Cannot import settings from repository", vcsException);
        }
        VersionedSettingsUpdateConfig versionedSettingsUpdateConfig = new VersionedSettingsUpdateConfig(vcsRootInstance.getId(), string);
        versionedSettingsUpdateConfig.importProject(sProject.getExternalId(), versionedSettingsConfig);
        versionedSettingsUpdateConfig.updateSpecifiedProjects(Collections.singleton(sProject.getExternalId()));
        versionedSettingsUpdateConfig.setSettingsFormat("pipelines-yaml");
        this.myVersionedSettingsUpdater.updateFromVcs(versionedSettingsUpdateConfig, null);
    }

    public boolean shouldCommitUpdatesToVersionedSettings(@NotNull Pipeline pipeline) {
        try {
            if (pipeline == null) {
                PipelineVersionedSettingsManager.$$$reportNull$$$0(11);
            }
        }
        catch (RuntimeException runtimeException) {
            throw PipelineVersionedSettingsManager.a(runtimeException);
        }
        try {
            if (pipeline.getVersionedSettings().isEnabled()) {
                return true;
            }
        }
        catch (RuntimeException runtimeException) {
            throw PipelineVersionedSettingsManager.a(runtimeException);
        }
        PipelineProject pipelineProject = pipeline.getProject();
        return this.isStoredInParentProject((SProject)pipelineProject);
    }

    private boolean isStoredInParentProject(@NotNull SProject sProject) {
        try {
            if (sProject == null) {
                PipelineVersionedSettingsManager.$$$reportNull$$$0(12);
            }
        }
        catch (RuntimeException runtimeException) {
            throw PipelineVersionedSettingsManager.a(runtimeException);
        }
        Pair pair = this.myVersionedSettingsManager.getEffectiveProjectConfig(sProject);
        if (pair != null) {
            boolean bl;
            SProject sProject2 = (SProject)pair.getFirst();
            try {
                bl = !sProject.equals((Object)sProject2);
            }
            catch (RuntimeException runtimeException) {
                throw PipelineVersionedSettingsManager.a(runtimeException);
            }
            return bl;
        }
        return false;
    }

    @NotNull
    public YamlPipelineCommitter.YamlCommitOutcome commitPipelineYamlToBranch(@NotNull PipelineProject pipelineProject, @NotNull String string, @NotNull ConfigAction configAction, @Nullable String string2, @Nullable String string3) throws VcsException, CommitNotSupported, IOException {
        YamlPipelineCommitter.YamlCommitOutcome yamlCommitOutcome;
        try {
            if (pipelineProject == null) {
                PipelineVersionedSettingsManager.$$$reportNull$$$0(13);
            }
        }
        catch (VcsException vcsException) {
            throw PipelineVersionedSettingsManager.a((Exception)((Object)vcsException));
        }
        try {
            if (string == null) {
                PipelineVersionedSettingsManager.$$$reportNull$$$0(14);
            }
        }
        catch (VcsException vcsException) {
            throw PipelineVersionedSettingsManager.a((Exception)((Object)vcsException));
        }
        try {
            if (configAction == null) {
                PipelineVersionedSettingsManager.$$$reportNull$$$0(15);
            }
        }
        catch (VcsException vcsException) {
            throw PipelineVersionedSettingsManager.a((Exception)((Object)vcsException));
        }
        try {
            this.mySecurityContext.getAccessChecker().checkCanEditProject((SProject)pipelineProject);
            yamlCommitOutcome = this.myYamlPipelineCommitter.commitToBranch(pipelineProject, string, configAction, string2, string3);
            if (yamlCommitOutcome == null) {
                PipelineVersionedSettingsManager.$$$reportNull$$$0(16);
            }
        }
        catch (VcsException vcsException) {
            throw PipelineVersionedSettingsManager.a((Exception)((Object)vcsException));
        }
        return yamlCommitOutcome;
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

    private static Exception a(Exception exception) {
        return exception;
    }
}
