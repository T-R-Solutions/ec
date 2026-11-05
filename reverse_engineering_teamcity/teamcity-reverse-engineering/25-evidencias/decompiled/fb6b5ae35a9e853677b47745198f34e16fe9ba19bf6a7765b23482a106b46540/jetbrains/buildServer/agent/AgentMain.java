/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jetbrains.buildServer.agent.AgentMain$SkipClassLoadingException
 *  jetbrains.buildServer.agent.ClasspathUtil
 *  jetbrains.buildServer.agent.impl.upgrade.AgentExitCode
 *  jetbrains.buildServer.util.FileUtil
 *  org.jetbrains.annotations.NotNull
 */
package jetbrains.buildServer.agent;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import jetbrains.buildServer.agent.AgentMain;
import jetbrains.buildServer.agent.ClasspathUtil;
import jetbrains.buildServer.agent.impl.upgrade.AgentExitCode;
import jetbrains.buildServer.util.FileUtil;
import org.jetbrains.annotations.NotNull;

public class AgentMain {
    private static final String CUSTOM_PATH = "teamcity.agent.debug.custom.lib.path";
    private static final String AGENT_HOME = "agent.home";

    public static void main(String[] args) {
        String newAgentMainClass = AgentMain.class.getName() + "2";
        try {
            URLClassLoader agentLoader = new URLClassLoader(AgentMain.getAgentLibraries(), AgentMain.class.getClassLoader().getParent());
            Thread.currentThread().setContextClassLoader(agentLoader);
            Class<?> main = agentLoader.loadClass(newAgentMainClass);
            Method mainMethod = main.getMethod("main", String[].class);
            mainMethod.invoke(null, new Object[]{args});
        }
        catch (SkipClassLoadingException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            AgentExitCode.CLASS_LOADING_ERROR.exitApplication();
        }
        catch (ClassNotFoundException e) {
            System.err.println("Failed to find build agent class " + newAgentMainClass + ". " + e.getMessage());
            e.printStackTrace();
        }
        catch (NoSuchMethodException e) {
            System.err.println("Failed to find main method in agent class " + newAgentMainClass + ". " + e.getMessage());
            e.printStackTrace();
        }
        catch (IllegalAccessException e) {
            System.err.println("Failed to call main method in agent class " + newAgentMainClass + ". " + e.getMessage());
            e.printStackTrace();
        }
        catch (InvocationTargetException e) {
            System.err.println("main method of agent class " + newAgentMainClass + " has failed. " + e.getMessage());
            e.printStackTrace();
        }
        catch (RuntimeException e) {
            if (!"java.lang.reflect.InaccessibleObjectException".equals(e.getClass().getName())) {
                throw e;
            }
            System.err.println("Failed to find main method in agent class " + newAgentMainClass + ". " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static URL[] getAgentLibraries() throws SkipClassLoadingException {
        File libFolder = AgentMain.getAgentLibrariesFolder();
        File[] files = libFolder.listFiles((FilenameFilter)new /* Unavailable Anonymous Inner Class!! */);
        if (files == null || files.length == 0) {
            throw new SkipClassLoadingException("Failed to locate build agent libraries at " + String.valueOf(libFolder));
        }
        try {
            ArrayList<URL> urls = new ArrayList<URL>();
            for (File file : files) {
                urls.add(file.toURI().toURL());
            }
            return urls.toArray(new URL[urls.size()]);
        }
        catch (MalformedURLException e) {
            throw new SkipClassLoadingException("Failed to build classpath for build agent. " + e.getMessage(), (Throwable)e);
        }
    }

    private static File getAgentLibrariesFolder() throws SkipClassLoadingException {
        String lib = System.getProperty(CUSTOM_PATH);
        if (lib != null) {
            System.out.println("Agent is loading from:" + lib);
            return new File(lib);
        }
        File mainJar = AgentMain.getAgentJar();
        if (!mainJar.isFile() || !mainJar.exists()) {
            throw new SkipClassLoadingException("Build agent should be packed with jar");
        }
        File libFolder = mainJar.getParentFile();
        if (libFolder == null || !libFolder.exists()) {
            throw new SkipClassLoadingException("Failed to locate build agent libraries at " + String.valueOf(libFolder));
        }
        return libFolder;
    }

    private static File getAgentJar() throws SkipClassLoadingException {
        try {
            String entry = ClasspathUtil.getClasspathEntry(AgentMain.class);
            if (entry == null) {
                throw new SkipClassLoadingException("Failed to find AgentMain class location");
            }
            return new File(entry);
        }
        catch (IOException e) {
            throw new SkipClassLoadingException("Failed to get filesystem location of " + String.valueOf(AgentMain.class));
        }
    }

    @NotNull
    public static File getAgentHome() throws RuntimeException {
        File file;
        File agentHomeFile;
        String agentHome = System.getProperty(AGENT_HOME);
        if (agentHome != null && (agentHomeFile = new File(agentHome)).exists()) {
            File file2;
            try {
                file2 = FileUtil.getCanonicalFile((File)agentHomeFile);
            }
            catch (Throwable t) {
                System.err.println("Failed to get agent.home from custom path: " + agentHome + ". " + t.getMessage());
            }
            if (file2 == null) {
                AgentMain.$$$reportNull$$$0(0);
            }
            return file2;
        }
        try {
            file = FileUtil.getCanonicalFile((File)AgentMain.getAgentJar().getParentFile().getParentFile());
        }
        catch (SkipClassLoadingException e) {
            throw new RuntimeException("Failed to find agent home folder. " + e.getMessage(), e);
        }
        if (file == null) {
            AgentMain.$$$reportNull$$$0(1);
        }
        return file;
    }

    private static /* synthetic */ void $$$reportNull$$$0(int n) {
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "jetbrains/buildServer/agent/AgentMain", "getAgentHome"));
    }
}
