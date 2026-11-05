/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jetbrains.buildServer.configs.ParameterBuilderImpl
 *  jetbrains.buildServer.configs.ProjectsBuilder
 *  jetbrains.buildServer.configs.dsl.ClasspathUtil
 *  jetbrains.buildServer.configs.dsl.DslErrorsSerializer
 *  jetbrains.buildServer.configs.dsl.DslGeneratorProcess
 *  jetbrains.buildServer.configs.dsl.PluginDslDigests
 *  jetbrains.buildServer.configs.dsl.PluginDslLogsConsoleProcessor
 *  jetbrains.buildServer.configs.dsl.extensions.PluginDslLogsProcessor
 *  jetbrains.buildServer.configs.dsl.kotlin.KotlinCompiler
 *  jetbrains.buildServer.configs.dsl.kotlin.KotlinLibs
 *  jetbrains.buildServer.configs.dsl.kotlin.KotlinLibsEnv
 *  jetbrains.buildServer.serverSide.impl.versionedSettings.VersionedSettingsError
 *  jetbrains.buildServer.serverSide.impl.versionedSettings.VersionedSettingsException
 *  org.jdom.Element
 *  org.jetbrains.annotations.NotNull
 */
package jetbrains.buildServer.configs.dsl;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import jetbrains.buildServer.configs.ParameterBuilderImpl;
import jetbrains.buildServer.configs.ProjectsBuilder;
import jetbrains.buildServer.configs.dsl.ClasspathUtil;
import jetbrains.buildServer.configs.dsl.DslErrorsSerializer;
import jetbrains.buildServer.configs.dsl.DslGeneratorProcess;
import jetbrains.buildServer.configs.dsl.PluginDslDigests;
import jetbrains.buildServer.configs.dsl.PluginDslLogsConsoleProcessor;
import jetbrains.buildServer.configs.dsl.extensions.PluginDslLogsProcessor;
import jetbrains.buildServer.configs.dsl.kotlin.KotlinCompiler;
import jetbrains.buildServer.configs.dsl.kotlin.KotlinLibs;
import jetbrains.buildServer.configs.dsl.kotlin.KotlinLibsEnv;
import jetbrains.buildServer.serverSide.impl.versionedSettings.VersionedSettingsError;
import jetbrains.buildServer.serverSide.impl.versionedSettings.VersionedSettingsException;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;

public class DslCompiler {
    @NotNull
    private final File myPluginsDslDir;
    @NotNull
    private final KotlinCompiler myKotlinCompiler;
    @NotNull
    private final PluginDslDigests myOldDigest;
    @NotNull
    private final PluginDslDigests myNewDigest;

    public DslCompiler(@NotNull File pluginsDslDir, @NotNull KotlinCompiler kotlinCompiler, @NotNull File digestsFile) throws IOException {
        if (pluginsDslDir == null) {
            DslCompiler.$$$reportNull$$$0(0);
        }
        if (kotlinCompiler == null) {
            DslCompiler.$$$reportNull$$$0(1);
        }
        if (digestsFile == null) {
            DslCompiler.$$$reportNull$$$0(2);
        }
        this.myPluginsDslDir = pluginsDslDir;
        this.myKotlinCompiler = kotlinCompiler;
        PluginDslLogsConsoleProcessor logsProcessor = new PluginDslLogsConsoleProcessor();
        this.myOldDigest = new PluginDslDigests(digestsFile, (PluginDslLogsProcessor)logsProcessor);
        this.myNewDigest = new PluginDslDigests(digestsFile, (PluginDslLogsProcessor)logsProcessor);
        this.myNewDigest.cleanPluginsSrcDigests();
    }

    public static void main(String ... args) throws Exception {
        DslGeneratorProcess.disableLog4j();
        DslGeneratorProcess.initTeamCityProperties();
        System.setProperty("java.awt.headless", "true");
        System.setProperty("kotlin.colors.enabled", "false");
        String pluginsDslPath = args[0];
        File pluginsDslDir = new File(pluginsDslPath);
        if (!pluginsDslDir.isDirectory()) {
            return;
        }
        String digestsPath = args[1];
        File digestsFile = new File(digestsPath);
        KotlinCompiler compiler = new KotlinCompiler((KotlinLibs)new KotlinLibsEnv(System.getenv()));
        try {
            List<Throwable> exceptions = new DslCompiler(pluginsDslDir, compiler, digestsFile).run();
            if (!exceptions.isEmpty()) {
                exceptions.forEach(DslCompiler::printException);
                new DslErrorsSerializer().saveExceptions(exceptions, pluginsDslDir);
                System.exit(1);
            }
        }
        catch (Throwable e) {
            DslCompiler.printException(e);
            new DslErrorsSerializer().saveException(e, pluginsDslDir);
            System.exit(1);
        }
    }

    private static void printException(@NotNull Throwable e) {
        if (e == null) {
            DslCompiler.$$$reportNull$$$0(3);
        }
        if (e instanceof VersionedSettingsException) {
            VersionedSettingsException ex = (VersionedSettingsException)e;
            List errors = ex.getErrors();
            if (errors.isEmpty()) {
                e.printStackTrace(System.err);
            } else {
                for (VersionedSettingsError error : errors) {
                    String location = error.getLocation();
                    if (location != null) {
                        System.err.println(String.format("%s%s %s", error.getSource(), location, error.getMessage()));
                        continue;
                    }
                    System.err.println(String.format("%s: %s", error.getSource(), error.getMessage()));
                }
            }
        } else {
            e.printStackTrace(System.err);
        }
    }

    protected List<Throwable> run() {
        LinkedList<Throwable> exceptions = new LinkedList<Throwable>();
        File[] files = this.myPluginsDslDir.listFiles();
        if (files == null || files.length == 0) {
            return exceptions;
        }
        for (File pluginDsl : files) {
            if (this.myOldDigest.updatePluginSrcDigestIfChanged(pluginDsl)) {
                try {
                    this.compilePluginDsl(pluginDsl);
                }
                catch (Exception e) {
                    exceptions.add(e);
                }
            }
            this.myNewDigest.copyPluginSrcDigest(this.myOldDigest, pluginDsl);
        }
        this.myNewDigest.save();
        return exceptions;
    }

    private void compilePluginDsl(@NotNull File pluginDsl) throws Exception {
        if (pluginDsl == null) {
            DslCompiler.$$$reportNull$$$0(4);
        }
        File jar = new File(pluginDsl, "generated-dsl.jar");
        String classpath = this.getClassPath();
        List<File> ktFiles = this.getKotlinFiles(pluginDsl);
        this.myKotlinCompiler.run(pluginDsl, Arrays.asList("-cp", classpath), ktFiles, jar, "1.9");
    }

    @NotNull
    private List<File> getKotlinFiles(@NotNull File dir) {
        List<File> list;
        if (dir == null) {
            DslCompiler.$$$reportNull$$$0(5);
        }
        try {
            list = Files.walk(dir.toPath(), new FileVisitOption[0]).map(Path::toFile).filter(f -> f.isFile() && f.getName().endsWith(".kt")).collect(Collectors.toList());
        }
        catch (IOException e) {
            return new ArrayList<File>();
        }
        if (list == null) {
            DslCompiler.$$$reportNull$$$0(6);
        }
        return list;
    }

    @NotNull
    private String getClassPath() throws ClassNotFoundException {
        String string = ClasspathUtil.composeClasspath((Class[])new Class[]{DslCompiler.class.getClassLoader().loadClass("kotlin.jvm.functions.Function1"), ProjectsBuilder.class, ParameterBuilderImpl.class, Element.class, DslCompiler.class.getClassLoader().loadClass("jetbrains.buildServer.configs.kotlin.Context")});
        if (string == null) {
            DslCompiler.$$$reportNull$$$0(7);
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
            case 6: 
            case 7: {
                string = "@NotNull method %s.%s must not return null";
                break;
            }
        }
        switch (n) {
            default: {
                n2 = 3;
                break;
            }
            case 6: 
            case 7: {
                n2 = 2;
                break;
            }
        }
        Object[] objectArray3 = new Object[n2];
        switch (n) {
            default: {
                objectArray2 = objectArray3;
                objectArray3[0] = "pluginsDslDir";
                break;
            }
            case 1: {
                objectArray2 = objectArray3;
                objectArray3[0] = "kotlinCompiler";
                break;
            }
            case 2: {
                objectArray2 = objectArray3;
                objectArray3[0] = "digestsFile";
                break;
            }
            case 3: {
                objectArray2 = objectArray3;
                objectArray3[0] = "e";
                break;
            }
            case 4: {
                objectArray2 = objectArray3;
                objectArray3[0] = "pluginDsl";
                break;
            }
            case 5: {
                objectArray2 = objectArray3;
                objectArray3[0] = "dir";
                break;
            }
            case 6: 
            case 7: {
                objectArray2 = objectArray3;
                objectArray3[0] = "jetbrains/buildServer/configs/dsl/DslCompiler";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray2;
                objectArray2[1] = "jetbrains/buildServer/configs/dsl/DslCompiler";
                break;
            }
            case 6: {
                objectArray = objectArray2;
                objectArray2[1] = "getKotlinFiles";
                break;
            }
            case 7: {
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
            case 3: {
                objectArray = objectArray;
                objectArray[2] = "printException";
                break;
            }
            case 4: {
                objectArray = objectArray;
                objectArray[2] = "compilePluginDsl";
                break;
            }
            case 5: {
                objectArray = objectArray;
                objectArray[2] = "getKotlinFiles";
                break;
            }
            case 6: 
            case 7: {
                break;
            }
        }
        String string2 = String.format(string, objectArray);
        switch (n) {
            default: {
                runtimeException = new IllegalArgumentException(string2);
                break;
            }
            case 6: 
            case 7: {
                runtimeException = new IllegalStateException(string2);
                break;
            }
        }
        throw runtimeException;
    }
}
