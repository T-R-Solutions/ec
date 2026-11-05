/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Stopwatch
 *  com.intellij.execution.configurations.GeneralCommandLine
 *  com.intellij.openapi.diagnostic.Logger
 *  com.intellij.openapi.util.Pair
 *  jetbrains.buildServer.ExecResult
 *  jetbrains.buildServer.ResourceUtil
 *  jetbrains.buildServer.SimpleCommandLineProcessRunner
 *  jetbrains.buildServer.SimpleCommandLineProcessRunner$RunCommandEvents
 *  jetbrains.buildServer.configs.ParameterBuilderImpl
 *  jetbrains.buildServer.configs.ProjectsBuilder
 *  jetbrains.buildServer.configs.dsl.ClasspathUtil
 *  jetbrains.buildServer.configs.dsl.DslClassPath
 *  jetbrains.buildServer.configs.dsl.DslCompilerProvider
 *  jetbrains.buildServer.configs.dsl.DslErrorsSerializer
 *  jetbrains.buildServer.configs.dsl.DslLauncher
 *  jetbrains.buildServer.configs.dsl.DslPluginManager
 *  jetbrains.buildServer.configs.dsl.PluginDslCompilationResult
 *  jetbrains.buildServer.configs.dsl.PluginDslCompilationResult$Status
 *  jetbrains.buildServer.configs.dsl.extensions.PluginDslLogsProcessor
 *  jetbrains.buildServer.configs.dsl.kotlin.DslApiPackagesType
 *  jetbrains.buildServer.configs.dsl.kotlin.KotlinCompiler
 *  jetbrains.buildServer.configs.dsl.kotlin.KotlinLibs
 *  jetbrains.buildServer.configs.dsl.kotlin.KotlinLibsEnv
 *  jetbrains.buildServer.messages.serviceMessages.MapSerializerUtil
 *  jetbrains.buildServer.serverSide.TeamCityProperties
 *  jetbrains.buildServer.serverSide.crypt.EncryptionUtils
 *  jetbrains.buildServer.serverSide.impl.projects.ConfigGenerationException
 *  jetbrains.buildServer.serverSide.impl.versionedSettings.VersionedSettingsError
 *  jetbrains.buildServer.serverSide.impl.versionedSettings.VersionedSettingsException
 *  jetbrains.buildServer.util.Disposable
 *  jetbrains.buildServer.util.FileUtil
 *  jetbrains.buildServer.util.StringUtil
 *  jetbrains.buildServer.util.TimePrinter
 *  jetbrains.buildServer.util.filters.Filter
 *  org.apache.commons.codec.EncoderException
 *  org.apache.log4j.Logger
 *  org.apache.logging.log4j.Logger
 *  org.apache.logging.log4j.core.LoggerContext
 *  org.apache.xerces.util.XMLGrammarPoolImpl
 *  org.jdom.Element
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package jetbrains.buildServer.configs.dsl;

import com.google.common.base.Stopwatch;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.util.Pair;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import jetbrains.buildServer.ExecResult;
import jetbrains.buildServer.ResourceUtil;
import jetbrains.buildServer.SimpleCommandLineProcessRunner;
import jetbrains.buildServer.configs.ParameterBuilderImpl;
import jetbrains.buildServer.configs.ProjectsBuilder;
import jetbrains.buildServer.configs.dsl.ClasspathUtil;
import jetbrains.buildServer.configs.dsl.DslClassPath;
import jetbrains.buildServer.configs.dsl.DslCompilerProvider;
import jetbrains.buildServer.configs.dsl.DslErrorsSerializer;
import jetbrains.buildServer.configs.dsl.DslLauncher;
import jetbrains.buildServer.configs.dsl.DslPluginManager;
import jetbrains.buildServer.configs.dsl.PluginDslCompilationResult;
import jetbrains.buildServer.configs.dsl.extensions.PluginDslLogsProcessor;
import jetbrains.buildServer.configs.dsl.kotlin.DslApiPackagesType;
import jetbrains.buildServer.configs.dsl.kotlin.KotlinCompiler;
import jetbrains.buildServer.configs.dsl.kotlin.KotlinLibs;
import jetbrains.buildServer.configs.dsl.kotlin.KotlinLibsEnv;
import jetbrains.buildServer.messages.serviceMessages.MapSerializerUtil;
import jetbrains.buildServer.serverSide.TeamCityProperties;
import jetbrains.buildServer.serverSide.crypt.EncryptionUtils;
import jetbrains.buildServer.serverSide.impl.projects.ConfigGenerationException;
import jetbrains.buildServer.serverSide.impl.versionedSettings.VersionedSettingsError;
import jetbrains.buildServer.serverSide.impl.versionedSettings.VersionedSettingsException;
import jetbrains.buildServer.util.Disposable;
import jetbrains.buildServer.util.FileUtil;
import jetbrains.buildServer.util.StringUtil;
import jetbrains.buildServer.util.TimePrinter;
import jetbrains.buildServer.util.filters.Filter;
import org.apache.commons.codec.EncoderException;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.xerces.util.XMLGrammarPoolImpl;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.w3c.dom.ElementTraversal;

public class PluginDslCompiler {
    private static final String PLUGINS_COMPILATION_TIMEOUT = "teamcity.kotlinConfigsDsl.pluginsCompilationTimeout";
    private static final String PLUGINS_COMPILATION_XMX = "teamcity.kotlinConfigsDsl.pluginsCompilationXmx";
    private final KotlinLibs myKotlinLibs;
    private final DslClassPath myKotlinClassPath;
    private final PluginDslLogsProcessor myPluginDslLogsProcessor;
    private final DslCompilerProvider myDslCompilerProvider;

    public PluginDslCompiler(@NotNull KotlinLibs kotlinLibs, @NotNull DslClassPath kotlinClassPath, @NotNull PluginDslLogsProcessor pluginDslLogsProcessor, @NotNull DslCompilerProvider dslCompilerProvider) {
        if (kotlinLibs == null) {
            PluginDslCompiler.$$$reportNull$$$0(0);
        }
        if (kotlinClassPath == null) {
            PluginDslCompiler.$$$reportNull$$$0(1);
        }
        if (pluginDslLogsProcessor == null) {
            PluginDslCompiler.$$$reportNull$$$0(2);
        }
        if (dslCompilerProvider == null) {
            PluginDslCompiler.$$$reportNull$$$0(3);
        }
        this.myKotlinLibs = kotlinLibs;
        this.myKotlinClassPath = kotlinClassPath;
        this.myPluginDslLogsProcessor = pluginDslLogsProcessor;
        this.myDslCompilerProvider = dslCompilerProvider;
    }

    public PluginDslCompilationResult compileGeneratedKotlinSources(@NotNull File kotlinSrcDir, @NotNull File jarsDir, @Nullable File oldJarsDir) throws IOException {
        if (kotlinSrcDir == null) {
            PluginDslCompiler.$$$reportNull$$$0(4);
        }
        if (jarsDir == null) {
            PluginDslCompiler.$$$reportNull$$$0(5);
        }
        PluginDslCompilationResult result = PluginDslCompilationResult.createSuccessfulDslCompilationResult();
        File[] versions = kotlinSrcDir.listFiles();
        if (versions == null || versions.length == 0) {
            return result;
        }
        for (DslApiPackagesType dslApiPackagesType : DslApiPackagesType.values()) {
            File dir = new File(kotlinSrcDir, dslApiPackagesType.getDirName());
            if (!dir.exists() || !dir.isDirectory()) continue;
            result.updateResult(this.runCompiler(dir, dslApiPackagesType));
        }
        jarsDir.mkdirs();
        for (DslApiPackagesType packagesType : DslApiPackagesType.values()) {
            File[] pluginsDirs;
            File pluginsDir = new File(kotlinSrcDir, packagesType.getDirName());
            if (!pluginsDir.exists() || (pluginsDirs = pluginsDir.listFiles()) == null || pluginsDirs.length == 0) continue;
            for (File generatedPerPlugin : pluginsDirs) {
                File oldCompiledJar;
                File compiledJar = new File(generatedPerPlugin, "generated-dsl.jar");
                File dstJar = new File(jarsDir, generatedPerPlugin.getName() + File.separator + "generated-dsl.jar");
                if (compiledJar.exists()) {
                    FileUtil.rename((File)compiledJar, (File)dstJar);
                    continue;
                }
                if (oldJarsDir == null || !(oldCompiledJar = new File(oldJarsDir, generatedPerPlugin.getName() + File.separator + "generated-dsl.jar")).exists()) continue;
                FileUtil.rename((File)oldCompiledJar, (File)dstJar);
            }
        }
        return result;
    }

    private PluginDslCompilationResult runCompiler(@NotNull File kotlinSrcDir, @NotNull DslApiPackagesType dslApiPackagesType) {
        if (kotlinSrcDir == null) {
            PluginDslCompiler.$$$reportNull$$$0(6);
        }
        if (dslApiPackagesType == null) {
            PluginDslCompiler.$$$reportNull$$$0(7);
        }
        try {
            Stopwatch action = Stopwatch.createStarted();
            GeneralCommandLine cmd = new GeneralCommandLine();
            cmd.setExePath(System.getProperty("java.home") + File.separator + "bin" + File.separator + "java");
            cmd.addParameter("-server");
            cmd.addParameter("-Xmx" + TeamCityProperties.getProperty((String)PLUGINS_COMPILATION_XMX, (String)"768m"));
            cmd.addParameters(new String[]{"-cp", this.getClassPath(dslApiPackagesType)});
            cmd.addParameter(this.myDslCompilerProvider.getDslCompilerClass().getName());
            cmd.addParameter(kotlinSrcDir.getAbsolutePath());
            File digestFile = new File(kotlinSrcDir.toPath().getParent().getParent().toFile(), DslPluginManager.getPluginsDigestFileName((DslApiPackagesType)dslApiPackagesType));
            cmd.addParameter(digestFile.getAbsolutePath());
            cmd.setEnvParams(KotlinLibsEnv.buildEnv((KotlinLibs)this.myKotlinLibs));
            cmd.setPassParentEnvs(true);
            ExecResult execResult = SimpleCommandLineProcessRunner.runCommand((GeneralCommandLine)cmd, null, (SimpleCommandLineProcessRunner.RunCommandEvents)new /* Unavailable Anonymous Inner Class!! */);
            if (execResult.getExitCode() != 0 || execResult.getException() != null) {
                List exceptions = new DslErrorsSerializer().retrieveExceptions(kotlinSrcDir);
                if (!exceptions.isEmpty()) {
                    for (VersionedSettingsException exception : exceptions) {
                        List errors = exception.getErrors();
                        if (errors.isEmpty()) {
                            this.myPluginDslLogsProcessor.error(String.format("Error while compiling generated Kotlin DSL: %s", exception));
                            continue;
                        }
                        for (VersionedSettingsError error : errors) {
                            this.myPluginDslLogsProcessor.error(String.format("Error while compiling generated Kotlin DSL %s", error.getDescription()));
                        }
                    }
                    return new PluginDslCompilationResult(PluginDslCompilationResult.Status.COMPILATION_ERROR, exceptions.stream().map(e -> e).collect(Collectors.toList()));
                }
                String msg = "DSL plugin compilation failed\n" + DslLauncher.buildMessage((ExecResult)execResult);
                this.myPluginDslLogsProcessor.error(msg);
                return new PluginDslCompilationResult(PluginDslCompilationResult.Status.FAILED, (Throwable)new Exception(msg));
            }
            this.myPluginDslLogsProcessor.info("Plugins Kotlin DSL for " + dslApiPackagesType + " compiled in " + TimePrinter.formatMillis((long)action.elapsed(TimeUnit.MILLISECONDS)));
            return PluginDslCompilationResult.createSuccessfulDslCompilationResult();
        }
        catch (Exception e2) {
            this.myPluginDslLogsProcessor.error("Error while compiling generated Kotlin DSL", (Throwable)e2);
            return new PluginDslCompilationResult(PluginDslCompilationResult.Status.FAILED, (Throwable)e2);
        }
    }

    @NotNull
    private String getClassPath(@NotNull DslApiPackagesType dslApiPackagesType) {
        Class<?> KProjectClass;
        if (dslApiPackagesType == null) {
            PluginDslCompiler.$$$reportNull$$$0(8);
        }
        String baseClassPath = this.myKotlinClassPath.getBaseClassPath();
        try {
            KProjectClass = this.getClass().getClassLoader().loadClass(dslApiPackagesType.getRepresentativeClassName());
        }
        catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        Class[] classes = new Class[]{KProjectClass, ResourceUtil.class, KotlinCompiler.class, ClasspathUtil.class, ProjectsBuilder.class, ParameterBuilderImpl.class, Element.class, org.apache.log4j.Logger.class, org.apache.logging.log4j.Logger.class, LoggerContext.class, Pair.class, Logger.class, ConfigGenerationException.class, TeamCityProperties.class, EncryptionUtils.class, XMLGrammarPoolImpl.class, ElementTraversal.class, StringUtil.class, MapSerializerUtil.class, EncoderException.class, Disposable.class, EncryptionUtils.class, Filter.class, this.myDslCompilerProvider.getDslCompilerClass(), this.getClass()};
        if (StringUtil.isEmpty((String)baseClassPath)) {
            String string = ClasspathUtil.composeClasspath((Class[])classes);
            if (string == null) {
                PluginDslCompiler.$$$reportNull$$$0(9);
            }
            return string;
        }
        String string = baseClassPath + File.pathSeparator + ClasspathUtil.composeClasspath((Class[])classes);
        if (string == null) {
            PluginDslCompiler.$$$reportNull$$$0(10);
        }
        return string;
    }

    private static /* synthetic */ void $$$reportNull$$$0(int n) {
        RuntimeException runtimeException;
        Object[] objectArray;
        Object[] objectArray2;
        int n2;
        String string;
        switch (n) {
            default: {
                string = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
            }
            case 9: 
            case 10: {
                string = "@NotNull method %s.%s must not return null";
                break;
            }
        }
        switch (n) {
            default: {
                n2 = 3;
                break;
            }
            case 9: 
            case 10: {
                n2 = 2;
                break;
            }
        }
        Object[] objectArray3 = new Object[n2];
        switch (n) {
            default: {
                objectArray2 = objectArray3;
                objectArray3[0] = "kotlinLibs";
                break;
            }
            case 1: {
                objectArray2 = objectArray3;
                objectArray3[0] = "kotlinClassPath";
                break;
            }
            case 2: {
                objectArray2 = objectArray3;
                objectArray3[0] = "pluginDslLogsProcessor";
                break;
            }
            case 3: {
                objectArray2 = objectArray3;
                objectArray3[0] = "dslCompilerProvider";
                break;
            }
            case 4: 
            case 6: {
                objectArray2 = objectArray3;
                objectArray3[0] = "kotlinSrcDir";
                break;
            }
            case 5: {
                objectArray2 = objectArray3;
                objectArray3[0] = "jarsDir";
                break;
            }
            case 7: 
            case 8: {
                objectArray2 = objectArray3;
                objectArray3[0] = "dslApiPackagesType";
                break;
            }
            case 9: 
            case 10: {
                objectArray2 = objectArray3;
                objectArray3[0] = "jetbrains/buildServer/configs/dsl/PluginDslCompiler";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray2;
                objectArray2[1] = "jetbrains/buildServer/configs/dsl/PluginDslCompiler";
                break;
            }
            case 9: 
            case 10: {
                objectArray = objectArray2;
                objectArray2[1] = "getClassPath";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray;
                objectArray[2] = "<init>";
                break;
            }
            case 4: 
            case 5: {
                objectArray = objectArray;
                objectArray[2] = "compileGeneratedKotlinSources";
                break;
            }
            case 6: 
            case 7: {
                objectArray = objectArray;
                objectArray[2] = "runCompiler";
                break;
            }
            case 8: {
                objectArray = objectArray;
                objectArray[2] = "getClassPath";
                break;
            }
            case 9: 
            case 10: {
                break;
            }
        }
        String string2 = String.format(string, objectArray);
        switch (n) {
            default: {
                runtimeException = new IllegalArgumentException(string2);
                break;
            }
            case 9: 
            case 10: {
                runtimeException = new IllegalStateException(string2);
                break;
            }
        }
        throw runtimeException;
    }
}
