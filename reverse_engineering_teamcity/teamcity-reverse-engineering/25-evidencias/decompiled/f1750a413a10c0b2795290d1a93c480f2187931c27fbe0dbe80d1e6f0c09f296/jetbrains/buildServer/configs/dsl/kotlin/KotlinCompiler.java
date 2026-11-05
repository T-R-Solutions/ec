/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.intellij.openapi.util.Pair
 *  jetbrains.buildServer.configs.dsl.kotlin.KotlinLibs
 *  jetbrains.buildServer.serverSide.impl.versionedSettings.VersionedSettingsError
 *  jetbrains.buildServer.serverSide.impl.versionedSettings.VersionedSettingsError$Type
 *  jetbrains.buildServer.serverSide.impl.versionedSettings.VersionedSettingsException
 *  jetbrains.buildServer.util.FileUtil
 *  jetbrains.buildServer.util.StringUtil
 *  jetbrains.buildServer.util.XmlUtil
 *  org.jdom.Element
 *  org.jetbrains.annotations.NotNull
 */
package jetbrains.buildServer.configs.dsl.kotlin;

import com.intellij.openapi.util.Pair;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import jetbrains.buildServer.configs.dsl.kotlin.KotlinLibs;
import jetbrains.buildServer.serverSide.impl.versionedSettings.VersionedSettingsError;
import jetbrains.buildServer.serverSide.impl.versionedSettings.VersionedSettingsException;
import jetbrains.buildServer.util.FileUtil;
import jetbrains.buildServer.util.StringUtil;
import jetbrains.buildServer.util.XmlUtil;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;

public class KotlinCompiler {
    private final KotlinLibs myLibs;
    private final Class<?> myCompilerClass;
    private final Class<?> myExitCodeClass;
    private final Class<?> myServicesClass;

    public KotlinCompiler(@NotNull KotlinLibs libs) throws Exception {
        if (libs == null) {
            KotlinCompiler.$$$reportNull$$$0(0);
        }
        this.myLibs = libs;
        ArrayList<URL> urls = new ArrayList<URL>();
        for (File f : this.myLibs.getKotlinLibJars()) {
            urls.add(f.getAbsoluteFile().toURI().toURL());
        }
        URLClassLoader loader = new URLClassLoader(urls.toArray(new URL[0]), null);
        this.myCompilerClass = loader.loadClass("org.jetbrains.kotlin.cli.jvm.K2JVMCompiler");
        this.myExitCodeClass = loader.loadClass("org.jetbrains.kotlin.cli.common.ExitCode");
        this.myServicesClass = loader.loadClass("org.jetbrains.kotlin.config.Services");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void run(@NotNull File baseDir, @NotNull List<String> args, List<File> kotlinFiles, @NotNull File classesDirOrJarFile, @NotNull String languageVersion) throws Exception {
        if (baseDir == null) {
            KotlinCompiler.$$$reportNull$$$0(1);
        }
        if (args == null) {
            KotlinCompiler.$$$reportNull$$$0(2);
        }
        if (classesDirOrJarFile == null) {
            KotlinCompiler.$$$reportNull$$$0(3);
        }
        if (languageVersion == null) {
            KotlinCompiler.$$$reportNull$$$0(4);
        }
        Method main = this.myCompilerClass.getMethod("execAndOutputXml", PrintStream.class, this.myServicesClass, String[].class);
        Method name = this.myExitCodeClass.getMethod("name", new Class[0]);
        Object compiler = this.myCompilerClass.newInstance();
        ByteArrayOutputStream osErrors = new ByteArrayOutputStream();
        PrintStream errorsStream = new PrintStream(osErrors);
        Pair<File, Boolean> kotlinHome = KotlinCompiler.getKotlinHomeDir(this.myLibs);
        args = new ArrayList<String>(args);
        try {
            args.add(0, "-kotlin-home");
            args.add(1, ((File)kotlinHome.first).getAbsolutePath());
            args.add(2, "-jvm-target");
            args.add(3, "1.8");
            args.add(4, "-Xallow-any-scripts-in-source-roots");
            args.add(5, "-language-version");
            args.add(6, languageVersion);
            args.add(7, "-Xuse-fir-lt=false");
            args.add(8, "-d");
            args.add(9, classesDirOrJarFile.getAbsolutePath());
            args.addAll(kotlinFiles.stream().map(File::getAbsolutePath).collect(Collectors.toList()));
            Object result = main.invoke(compiler, errorsStream, this.myServicesClass.getField("EMPTY").get(null), args.toArray(new String[0]));
            String errorCode = (String)name.invoke(result, new Object[0]);
            if (!errorCode.equals("OK")) {
                String errorOutput = osErrors.toString("UTF8");
                List<VersionedSettingsError> errors = KotlinCompiler.getCompilationErrors(baseDir, errorOutput);
                throw new VersionedSettingsException(StringUtil.pluralize((String)"Kotlin DSL compilation error", (int)errors.size()), errors);
            }
        }
        finally {
            if (((Boolean)kotlinHome.second).booleanValue()) {
                FileUtil.delete((File)((File)kotlinHome.first));
            }
        }
    }

    @NotNull
    public static Pair<File, Boolean> getKotlinHomeDir(@NotNull KotlinLibs kotlinLibs) throws Exception {
        List libJars;
        if (kotlinLibs == null) {
            KotlinCompiler.$$$reportNull$$$0(5);
        }
        if (KotlinCompiler.inSameDir((File)(libJars = kotlinLibs.getKotlinLibJars()).get(0), libJars.subList(1, libJars.size()).toArray(new File[0])) && ((File)libJars.get(0)).getParentFile().getName().equals("lib")) {
            Pair pair = Pair.create((Object)((File)libJars.get(0)).getParentFile().getParentFile(), (Object)false);
            if (pair == null) {
                KotlinCompiler.$$$reportNull$$$0(6);
            }
            return pair;
        }
        File tmp = FileUtil.createTempDirectory((String)"kotlin", (String)"home");
        File lib = new File(tmp, "lib");
        lib.mkdirs();
        for (File jar : libJars) {
            String jarName = jar.getName().replaceAll("-[0-9]+\\.[0-9]+\\.[0-9]+(-RC[0-9]?)*", "");
            FileUtil.copy((File)jar, (File)new File(lib, jarName));
        }
        Pair pair = Pair.create((Object)tmp, (Object)true);
        if (pair == null) {
            KotlinCompiler.$$$reportNull$$$0(7);
        }
        return pair;
    }

    private static boolean inSameDir(@NotNull File f, File ... files) {
        if (f == null) {
            KotlinCompiler.$$$reportNull$$$0(8);
        }
        if (files == null) {
            KotlinCompiler.$$$reportNull$$$0(9);
        }
        File parent = f.getParentFile();
        for (File file : files) {
            if (parent.equals(file.getParentFile())) continue;
            return false;
        }
        return true;
    }

    @NotNull
    public static List<VersionedSettingsError> getCompilationErrors(@NotNull File baseDir, @NotNull String errorsInXml) {
        String basePath;
        if (baseDir == null) {
            KotlinCompiler.$$$reportNull$$$0(10);
        }
        if (errorsInXml == null) {
            KotlinCompiler.$$$reportNull$$$0(11);
        }
        try {
            basePath = baseDir.getCanonicalPath();
        }
        catch (IOException e) {
            basePath = "";
        }
        ArrayList<VersionedSettingsError> errors = new ArrayList<VersionedSettingsError>();
        Element xmlErrors = XmlUtil.from_s((String)errorsInXml);
        for (Object o : xmlErrors.getChildren()) {
            Element xmlEl;
            if (!(o instanceof Element) || !(xmlEl = (Element)o).getName().equals("error") && !xmlEl.getName().equals("exception")) continue;
            String source = xmlEl.getAttributeValue("path");
            if (source == null) {
                source = "";
            }
            if (source.startsWith(basePath)) {
                source = source.substring(basePath.length() + 1);
            }
            String msg = xmlEl.getText();
            String line = xmlEl.getAttributeValue("line");
            String column = xmlEl.getAttributeValue("column");
            String location = line != null && column != null ? String.format("[%s:%s]", line, column) : null;
            List pathComponents = StringUtil.split((String)source, (String)File.separator);
            String extId = pathComponents.size() > 0 ? (String)pathComponents.get(0) : "";
            errors.add(new VersionedSettingsError(VersionedSettingsError.Type.COMPILATION_ERROR, extId, source, location, msg));
        }
        ArrayList<VersionedSettingsError> arrayList = errors;
        if (arrayList == null) {
            KotlinCompiler.$$$reportNull$$$0(12);
        }
        return arrayList;
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
            case 7: 
            case 12: {
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
            case 7: 
            case 12: {
                n2 = 2;
                break;
            }
        }
        Object[] objectArray3 = new Object[n2];
        switch (n) {
            default: {
                objectArray2 = objectArray3;
                objectArray3[0] = "libs";
                break;
            }
            case 1: 
            case 10: {
                objectArray2 = objectArray3;
                objectArray3[0] = "baseDir";
                break;
            }
            case 2: {
                objectArray2 = objectArray3;
                objectArray3[0] = "args";
                break;
            }
            case 3: {
                objectArray2 = objectArray3;
                objectArray3[0] = "classesDirOrJarFile";
                break;
            }
            case 4: {
                objectArray2 = objectArray3;
                objectArray3[0] = "languageVersion";
                break;
            }
            case 5: {
                objectArray2 = objectArray3;
                objectArray3[0] = "kotlinLibs";
                break;
            }
            case 6: 
            case 7: 
            case 12: {
                objectArray2 = objectArray3;
                objectArray3[0] = "jetbrains/buildServer/configs/dsl/kotlin/KotlinCompiler";
                break;
            }
            case 8: {
                objectArray2 = objectArray3;
                objectArray3[0] = "f";
                break;
            }
            case 9: {
                objectArray2 = objectArray3;
                objectArray3[0] = "files";
                break;
            }
            case 11: {
                objectArray2 = objectArray3;
                objectArray3[0] = "errorsInXml";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray2;
                objectArray2[1] = "jetbrains/buildServer/configs/dsl/kotlin/KotlinCompiler";
                break;
            }
            case 6: 
            case 7: {
                objectArray = objectArray2;
                objectArray2[1] = "getKotlinHomeDir";
                break;
            }
            case 12: {
                objectArray = objectArray2;
                objectArray2[1] = "getCompilationErrors";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray;
                objectArray[2] = "<init>";
                break;
            }
            case 1: 
            case 2: 
            case 3: 
            case 4: {
                objectArray = objectArray;
                objectArray[2] = "run";
                break;
            }
            case 5: {
                objectArray = objectArray;
                objectArray[2] = "getKotlinHomeDir";
                break;
            }
            case 6: 
            case 7: 
            case 12: {
                break;
            }
            case 8: 
            case 9: {
                objectArray = objectArray;
                objectArray[2] = "inSameDir";
                break;
            }
            case 10: 
            case 11: {
                objectArray = objectArray;
                objectArray[2] = "getCompilationErrors";
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
            case 7: 
            case 12: {
                runtimeException = new IllegalStateException(string2);
                break;
            }
        }
        throw runtimeException;
    }
}
