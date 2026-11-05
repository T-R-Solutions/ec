/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jetbrains.buildServer.configs.dsl.DslClassPath
 *  jetbrains.buildServer.configs.dsl.DslCompilerProvider
 *  jetbrains.buildServer.configs.dsl.DslDocs
 *  jetbrains.buildServer.configs.dsl.DslDocsGenerator
 *  jetbrains.buildServer.configs.dsl.DslExtensionKtGenerator
 *  jetbrains.buildServer.configs.dsl.DslPluginManager
 *  jetbrains.buildServer.configs.dsl.PluginDslCompiler
 *  jetbrains.buildServer.configs.dsl.PluginDslCompilerProcess$1
 *  jetbrains.buildServer.configs.dsl.PluginDslDigests
 *  jetbrains.buildServer.configs.dsl.PluginDslLogsConsoleProcessor
 *  jetbrains.buildServer.configs.dsl.PluginManagerAdapter
 *  jetbrains.buildServer.configs.dsl.extensions.KotlinDslExtensionsImpl
 *  jetbrains.buildServer.configs.dsl.extensions.PluginDslLogsProcessor
 *  jetbrains.buildServer.configs.dsl.extensions.parser.PluginDslExtensionParser
 *  jetbrains.buildServer.configs.dsl.extensions.parser.PluginDslExtensionParser$ParsedExtensions
 *  jetbrains.buildServer.configs.dsl.extensions.parser.ServerDslResourcesProvider
 *  jetbrains.buildServer.configs.dsl.kotlin.KotlinClassPath
 *  jetbrains.buildServer.configs.dsl.kotlin.KotlinLibs
 *  jetbrains.buildServer.configs.dsl.kotlin.KotlinLibsProd
 *  jetbrains.buildServer.configs.dsl.kotlin.PredefinedKotlinDslExtensions
 *  jetbrains.buildServer.log.LogInitializer
 *  jetbrains.buildServer.plugins.files.PluginFilesUtil
 *  jetbrains.buildServer.plugins.files.PluginFilesUtil$UnpackHandler
 *  jetbrains.buildServer.util.FileUtil
 *  org.apache.logging.log4j.Level
 *  org.jetbrains.annotations.NotNull
 */
package jetbrains.buildServer.configs.dsl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import jetbrains.buildServer.configs.dsl.DslClassPath;
import jetbrains.buildServer.configs.dsl.DslCompilerProvider;
import jetbrains.buildServer.configs.dsl.DslDocs;
import jetbrains.buildServer.configs.dsl.DslDocsGenerator;
import jetbrains.buildServer.configs.dsl.DslExtensionKtGenerator;
import jetbrains.buildServer.configs.dsl.DslPluginManager;
import jetbrains.buildServer.configs.dsl.PluginDslCompiler;
import jetbrains.buildServer.configs.dsl.PluginDslCompilerProcess;
import jetbrains.buildServer.configs.dsl.PluginDslDigests;
import jetbrains.buildServer.configs.dsl.PluginDslLogsConsoleProcessor;
import jetbrains.buildServer.configs.dsl.PluginManagerAdapter;
import jetbrains.buildServer.configs.dsl.extensions.KotlinDslExtensionsImpl;
import jetbrains.buildServer.configs.dsl.extensions.PluginDslLogsProcessor;
import jetbrains.buildServer.configs.dsl.extensions.parser.PluginDslExtensionParser;
import jetbrains.buildServer.configs.dsl.extensions.parser.ServerDslResourcesProvider;
import jetbrains.buildServer.configs.dsl.kotlin.KotlinClassPath;
import jetbrains.buildServer.configs.dsl.kotlin.KotlinLibs;
import jetbrains.buildServer.configs.dsl.kotlin.KotlinLibsProd;
import jetbrains.buildServer.configs.dsl.kotlin.PredefinedKotlinDslExtensions;
import jetbrains.buildServer.log.LogInitializer;
import jetbrains.buildServer.plugins.files.PluginFilesUtil;
import jetbrains.buildServer.util.FileUtil;
import org.apache.logging.log4j.Level;
import org.jetbrains.annotations.NotNull;

public class PluginDslCompilerProcess {
    private static final String TMP_DIR_PREFIX = "pluginDslCompile";
    private final PluginDslLogsProcessor myPluginDslLogsProcessor;
    private final File myResultDir;

    private PluginDslCompilerProcess(@NotNull String resultDirPath) throws IOException {
        if (resultDirPath == null) {
            PluginDslCompilerProcess.$$$reportNull$$$0(0);
        }
        this.myPluginDslLogsProcessor = new PluginDslLogsConsoleProcessor();
        this.myResultDir = FileUtil.createDir((File)new File(resultDirPath));
    }

    public static void main(String[] args) throws Exception {
        PluginDslCompilerProcess.disableLog4j();
        PluginDslCompilerProcess.initTeamCityProperties();
        if (args.length < 2) {
            throw new IllegalArgumentException("Args: [dist plugins dir] [result dir]");
        }
        String distPluginsDir = args[0];
        String resultDir = args[1];
        PluginDslCompilerProcess pluginDslCompilerProcess = new PluginDslCompilerProcess(resultDir);
        pluginDslCompilerProcess.run(distPluginsDir);
    }

    private void run(@NotNull String distPluginsDir) throws Exception {
        if (distPluginsDir == null) {
            PluginDslCompilerProcess.$$$reportNull$$$0(1);
        }
        File[] pluginsDirs = this.preparePluginsDir(distPluginsDir).listFiles();
        Objects.requireNonNull(pluginsDirs);
        1 pluginManagerAdapter = new /* Unavailable Anonymous Inner Class!! */;
        File dslSrc = this.prepareDslSrc((PluginManagerAdapter)pluginManagerAdapter);
        KotlinLibs kotlinLibs = this.prepareKotlinLibs((PluginManagerAdapter)pluginManagerAdapter);
        File jarsDir = FileUtil.createDir((File)new File(this.myResultDir, "jars"));
        ArrayList<File> pluginsJars = new ArrayList<File>();
        DslDocsGenerator dslDocsGenerator = this.getDslDocsGenerator((PluginManagerAdapter)pluginManagerAdapter);
        File digestFile = this.createDigest((PluginManagerAdapter)pluginManagerAdapter, dslSrc, kotlinLibs, pluginsJars, dslDocsGenerator);
        System.out.println(FileUtil.readText((File)digestFile));
        this.compileDslSrc(dslSrc, kotlinLibs, jarsDir);
        File docs = FileUtil.createDir((File)new File(this.myResultDir, "doc"));
        dslDocsGenerator.generateDocs(dslSrc, docs, null, true);
        System.out.println("Docs generated in " + docs.getAbsolutePath());
        FileUtil.delete((File)dslSrc);
        File zipFile = this.packDsl(jarsDir, docs);
        System.out.println("Successfully packed in " + zipFile.getAbsolutePath());
    }

    @NotNull
    private File packDsl(File ... dirsToPack) throws IOException {
        File zipFile = new File(this.myResultDir, "bundledDSL.zip");
        URI zipFileUri = URI.create("jar:" + zipFile.toPath().toAbsolutePath().toUri());
        try (FileSystem fs = FileSystems.newFileSystem(zipFileUri, Collections.singletonMap("create", "true"));){
            Path zipRoot = fs.getPath("/", new String[0]);
            ArrayDeque<File> filesToPack = new ArrayDeque<File>(Arrays.asList(dirsToPack));
            while (!filesToPack.isEmpty()) {
                File currentFile = (File)filesToPack.poll();
                String archivedFileName = FileUtil.getRelativePath((File)this.myResultDir, (File)currentFile);
                if (archivedFileName == null) {
                    throw new FileNotFoundException("Connot resolve path for DSL packing");
                }
                Path archiveFieldPath = zipRoot.resolve(archivedFileName);
                if (currentFile.isDirectory()) {
                    Files.createDirectories(archiveFieldPath, new FileAttribute[0]);
                    File[] listFiles = currentFile.listFiles();
                    if (listFiles == null) continue;
                    filesToPack.addAll(Arrays.asList(listFiles));
                    continue;
                }
                FileInputStream currentFileStream = new FileInputStream(currentFile);
                Files.copy(currentFileStream, archiveFieldPath, StandardCopyOption.REPLACE_EXISTING);
                ((InputStream)currentFileStream).close();
            }
        }
        for (File dir : dirsToPack) {
            FileUtil.delete((File)dir);
        }
        File file = zipFile;
        if (file == null) {
            PluginDslCompilerProcess.$$$reportNull$$$0(2);
        }
        return file;
    }

    @NotNull
    private DslDocsGenerator getDslDocsGenerator(@NotNull PluginManagerAdapter pluginManager) {
        if (pluginManager == null) {
            PluginDslCompilerProcess.$$$reportNull$$$0(3);
        }
        return new DslDocsGenerator(this.myPluginDslLogsProcessor, pluginManager, (DslDocs)new /* Unavailable Anonymous Inner Class!! */);
    }

    @NotNull
    private File createDigest(@NotNull PluginManagerAdapter pluginManagerAdapter, @NotNull File dslSrc, @NotNull KotlinLibs kotlinLibs, @NotNull List<File> pluginsJars, @NotNull DslDocsGenerator dslDocsGenerator) throws IOException {
        if (pluginManagerAdapter == null) {
            PluginDslCompilerProcess.$$$reportNull$$$0(4);
        }
        if (dslSrc == null) {
            PluginDslCompilerProcess.$$$reportNull$$$0(5);
        }
        if (kotlinLibs == null) {
            PluginDslCompilerProcess.$$$reportNull$$$0(6);
        }
        if (pluginsJars == null) {
            PluginDslCompilerProcess.$$$reportNull$$$0(7);
        }
        if (dslDocsGenerator == null) {
            PluginDslCompilerProcess.$$$reportNull$$$0(8);
        }
        File digestFile = new File(this.myResultDir, "digests.properties");
        PluginDslDigests digests = new PluginDslDigests(digestFile, this.myPluginDslLogsProcessor);
        File configsDslRoot = pluginManagerAdapter.getOwnPluginRoot();
        if (configsDslRoot == null) {
            throw new FileNotFoundException("configs-dsl plugin is not found");
        }
        digests.updateMainDslSrcDigestIfChanged(DslPluginManager.getMainDslSourceDirectories((File)configsDslRoot));
        digests.updatePluginsDslSrcDigestIfChanged(dslSrc);
        digests.updateKotlinLibsDigestIfChanged(kotlinLibs);
        digests.updatePluginsJarsDigestIfChanged(pluginsJars);
        digests.updateDokkaJarDigestIfChanged(dslDocsGenerator.findDokkaJar());
        digests.updateDokkaPluginDigestIfChanged(dslDocsGenerator.findDokkaPlugin());
        digests.save();
        File file = digestFile;
        if (file == null) {
            PluginDslCompilerProcess.$$$reportNull$$$0(9);
        }
        return file;
    }

    private void compileDslSrc(@NotNull File dslSrc, @NotNull KotlinLibs kotlinLibs, @NotNull File jarsDir) throws Exception {
        if (dslSrc == null) {
            PluginDslCompilerProcess.$$$reportNull$$$0(10);
        }
        if (kotlinLibs == null) {
            PluginDslCompilerProcess.$$$reportNull$$$0(11);
        }
        if (jarsDir == null) {
            PluginDslCompilerProcess.$$$reportNull$$$0(12);
        }
        PluginDslCompiler pluginDslCompiler = new PluginDslCompiler(kotlinLibs, (DslClassPath)new KotlinClassPath(kotlinLibs), this.myPluginDslLogsProcessor, new DslCompilerProvider());
        pluginDslCompiler.compileGeneratedKotlinSources(dslSrc, jarsDir, null);
    }

    @NotNull
    private File preparePluginsDir(@NotNull String distPluginsDir) throws IOException {
        if (distPluginsDir == null) {
            PluginDslCompilerProcess.$$$reportNull$$$0(13);
        }
        File distPluginsDirFile = new File(distPluginsDir);
        File pluginsDir = FileUtil.createTempDirectory((String)TMP_DIR_PREFIX, (String)"pluginsUnpacked");
        File[] distPlugins = distPluginsDirFile.listFiles();
        if (distPlugins == null) {
            throw new FileNotFoundException("Dist plugins directory not found");
        }
        for (File plugin : distPlugins) {
            if (plugin.isDirectory()) {
                FileUtil.copyDir((File)plugin, (File)new File(pluginsDir, plugin.getName()));
                continue;
            }
            if (!plugin.isFile() || !plugin.getName().endsWith(".zip")) continue;
            File destinationDir = new File(pluginsDir, plugin.getName().substring(0, plugin.getName().length() - 4));
            destinationDir.mkdir();
            try {
                PluginFilesUtil.unpackPlugin((File)plugin, (File)destinationDir, (PluginFilesUtil.UnpackHandler)new /* Unavailable Anonymous Inner Class!! */);
            }
            catch (Throwable e) {
                this.myPluginDslLogsProcessor.error("Failed to unpack plugin " + plugin.getName(), e);
            }
        }
        File file = pluginsDir;
        if (file == null) {
            PluginDslCompilerProcess.$$$reportNull$$$0(14);
        }
        return file;
    }

    @NotNull
    private KotlinLibs prepareKotlinLibs(@NotNull PluginManagerAdapter pluginManagerAdapter) throws IOException {
        File configsDlsPluginRoot;
        if (pluginManagerAdapter == null) {
            PluginDslCompilerProcess.$$$reportNull$$$0(15);
        }
        if ((configsDlsPluginRoot = pluginManagerAdapter.getOwnPluginRoot()) == null) {
            throw new FileNotFoundException("No configs-dsl plugin found");
        }
        File kotlinRuntimeDir = new File(configsDlsPluginRoot, "kotlin_runtime");
        if (!(kotlinRuntimeDir.canRead() && kotlinRuntimeDir.exists() && kotlinRuntimeDir.isDirectory())) {
            throw new FileNotFoundException("Cannot read kotlin_runtime directory from configs-dsl plugins root");
        }
        return new KotlinLibsProd(kotlinRuntimeDir);
    }

    @NotNull
    private File prepareDslSrc(@NotNull PluginManagerAdapter pluginManagerAdapter) throws IOException {
        if (pluginManagerAdapter == null) {
            PluginDslCompilerProcess.$$$reportNull$$$0(16);
        }
        PluginDslExtensionParser pluginDslExtensionParser = new PluginDslExtensionParser(this.myPluginDslLogsProcessor, new ServerDslResourcesProvider(this.myPluginDslLogsProcessor));
        File srcDir = FileUtil.createDir((File)new File(this.myResultDir, "src"));
        KotlinDslExtensionsImpl kotlinDslExtensions = new KotlinDslExtensionsImpl();
        PredefinedKotlinDslExtensions.initilize((KotlinDslExtensionsImpl)kotlinDslExtensions);
        PluginDslExtensionParser.ParsedExtensions parsedExtensions = pluginDslExtensionParser.registerExtensionsFromDescriptors(kotlinDslExtensions, pluginManagerAdapter.getPluginDirs());
        new DslExtensionKtGenerator(this.myPluginDslLogsProcessor).generate(parsedExtensions, srcDir);
        pluginDslExtensionParser.deleteEmptyPluginDirs(srcDir);
        File file = srcDir;
        if (file == null) {
            PluginDslCompilerProcess.$$$reportNull$$$0(17);
        }
        return file;
    }

    private static void disableLog4j() {
        LogInitializer.reconfigureLog4j((loggerContext, configuration) -> configuration.getRootLogger().setLevel(Level.OFF));
    }

    private static void initTeamCityProperties() {
        new /* Unavailable Anonymous Inner Class!! */;
    }

    static /* synthetic */ PluginDslLogsProcessor access$000(PluginDslCompilerProcess x0) {
        return x0.myPluginDslLogsProcessor;
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
            case 2: 
            case 9: 
            case 14: 
            case 17: {
                string = "@NotNull method %s.%s must not return null";
                break;
            }
        }
        switch (n) {
            default: {
                n2 = 3;
                break;
            }
            case 2: 
            case 9: 
            case 14: 
            case 17: {
                n2 = 2;
                break;
            }
        }
        Object[] objectArray3 = new Object[n2];
        switch (n) {
            default: {
                objectArray2 = objectArray3;
                objectArray3[0] = "resultDirPath";
                break;
            }
            case 1: 
            case 13: {
                objectArray2 = objectArray3;
                objectArray3[0] = "distPluginsDir";
                break;
            }
            case 2: 
            case 9: 
            case 14: 
            case 17: {
                objectArray2 = objectArray3;
                objectArray3[0] = "jetbrains/buildServer/configs/dsl/PluginDslCompilerProcess";
                break;
            }
            case 3: {
                objectArray2 = objectArray3;
                objectArray3[0] = "pluginManager";
                break;
            }
            case 4: 
            case 15: 
            case 16: {
                objectArray2 = objectArray3;
                objectArray3[0] = "pluginManagerAdapter";
                break;
            }
            case 5: 
            case 10: {
                objectArray2 = objectArray3;
                objectArray3[0] = "dslSrc";
                break;
            }
            case 6: 
            case 11: {
                objectArray2 = objectArray3;
                objectArray3[0] = "kotlinLibs";
                break;
            }
            case 7: {
                objectArray2 = objectArray3;
                objectArray3[0] = "pluginsJars";
                break;
            }
            case 8: {
                objectArray2 = objectArray3;
                objectArray3[0] = "dslDocsGenerator";
                break;
            }
            case 12: {
                objectArray2 = objectArray3;
                objectArray3[0] = "jarsDir";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray2;
                objectArray2[1] = "jetbrains/buildServer/configs/dsl/PluginDslCompilerProcess";
                break;
            }
            case 2: {
                objectArray = objectArray2;
                objectArray2[1] = "packDsl";
                break;
            }
            case 9: {
                objectArray = objectArray2;
                objectArray2[1] = "createDigest";
                break;
            }
            case 14: {
                objectArray = objectArray2;
                objectArray2[1] = "preparePluginsDir";
                break;
            }
            case 17: {
                objectArray = objectArray2;
                objectArray2[1] = "prepareDslSrc";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray;
                objectArray[2] = "<init>";
                break;
            }
            case 1: {
                objectArray = objectArray;
                objectArray[2] = "run";
                break;
            }
            case 2: 
            case 9: 
            case 14: 
            case 17: {
                break;
            }
            case 3: {
                objectArray = objectArray;
                objectArray[2] = "getDslDocsGenerator";
                break;
            }
            case 4: 
            case 5: 
            case 6: 
            case 7: 
            case 8: {
                objectArray = objectArray;
                objectArray[2] = "createDigest";
                break;
            }
            case 10: 
            case 11: 
            case 12: {
                objectArray = objectArray;
                objectArray[2] = "compileDslSrc";
                break;
            }
            case 13: {
                objectArray = objectArray;
                objectArray[2] = "preparePluginsDir";
                break;
            }
            case 15: {
                objectArray = objectArray;
                objectArray[2] = "prepareKotlinLibs";
                break;
            }
            case 16: {
                objectArray = objectArray;
                objectArray[2] = "prepareDslSrc";
                break;
            }
        }
        String string2 = String.format(string, objectArray);
        switch (n) {
            default: {
                runtimeException = new IllegalArgumentException(string2);
                break;
            }
            case 2: 
            case 9: 
            case 14: 
            case 17: {
                runtimeException = new IllegalStateException(string2);
                break;
            }
        }
        throw runtimeException;
    }
}
