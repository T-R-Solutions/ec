/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jetbrains.buildServer.Used
 *  jetbrains.buildServer.agent.AgentConfiguration
 *  jetbrains.buildServer.agent.AgentOperationMode
 *  jetbrains.buildServer.agent.AgentOperationModeEx
 *  jetbrains.buildServer.agent.BuildAgentConfigurationEx
 *  jetbrains.buildServer.agent.BuildAgentSystemInfo
 *  jetbrains.buildServer.agent.BuildParametersMap
 *  jetbrains.buildServer.agent.impl.BuildAgentStartFailedException
 *  jetbrains.buildServer.agent.impl.BuildParametersMapsFactory
 *  jetbrains.buildServer.agent.impl.config.AgentContextEx
 *  jetbrains.buildServer.agent.impl.config.AgentContextImpl
 *  jetbrains.buildServer.agent.impl.config.BuildAgentConfigurablePaths
 *  jetbrains.buildServer.agent.impl.config.BuildAgentConfigurationImplicitParameters
 *  jetbrains.buildServer.agent.impl.config.BuildAgentConfigurationInitializer
 *  jetbrains.buildServer.agent.impl.config.BuildAgentConfigurationInitializerRegistry$PropertyHandler
 *  jetbrains.buildServer.agent.impl.config.BuildAgentConfigurationPersister
 *  jetbrains.buildServer.agent.impl.config.BuildAgentConfigurationWellKnownAgentProperties
 *  jetbrains.buildServer.agent.impl.operationModes.AgentOperationModeHolder
 *  jetbrains.buildServer.agent.impl.parameters.AgentEnvironmentParametersProvider
 *  jetbrains.buildServer.agent.impl.parameters.factory.AgentUnresolvedParametersFactory
 *  jetbrains.buildServer.agentServer.AgentRegistrationDetails
 *  jetbrains.buildServer.http.SimpleCredentials
 *  jetbrains.buildServer.log.Loggers
 *  jetbrains.buildServer.parameters.ValueResolver
 *  jetbrains.buildServer.util.StringUtil
 *  org.jetbrains.annotations.NonNls
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package jetbrains.buildServer.agent.impl.config;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import jetbrains.buildServer.Used;
import jetbrains.buildServer.agent.AgentConfiguration;
import jetbrains.buildServer.agent.AgentOperationMode;
import jetbrains.buildServer.agent.AgentOperationModeEx;
import jetbrains.buildServer.agent.BuildAgentConfigurationEx;
import jetbrains.buildServer.agent.BuildAgentSystemInfo;
import jetbrains.buildServer.agent.BuildParametersMap;
import jetbrains.buildServer.agent.impl.BuildAgentStartFailedException;
import jetbrains.buildServer.agent.impl.BuildParametersMapsFactory;
import jetbrains.buildServer.agent.impl.config.AgentContextEx;
import jetbrains.buildServer.agent.impl.config.AgentContextImpl;
import jetbrains.buildServer.agent.impl.config.BuildAgentConfigurablePaths;
import jetbrains.buildServer.agent.impl.config.BuildAgentConfigurationImplicitParameters;
import jetbrains.buildServer.agent.impl.config.BuildAgentConfigurationInitializer;
import jetbrains.buildServer.agent.impl.config.BuildAgentConfigurationInitializerRegistry;
import jetbrains.buildServer.agent.impl.config.BuildAgentConfigurationPersister;
import jetbrains.buildServer.agent.impl.config.BuildAgentConfigurationWellKnownAgentProperties;
import jetbrains.buildServer.agent.impl.operationModes.AgentOperationModeHolder;
import jetbrains.buildServer.agent.impl.parameters.AgentEnvironmentParametersProvider;
import jetbrains.buildServer.agent.impl.parameters.factory.AgentUnresolvedParametersFactory;
import jetbrains.buildServer.agentServer.AgentRegistrationDetails;
import jetbrains.buildServer.http.SimpleCredentials;
import jetbrains.buildServer.log.Loggers;
import jetbrains.buildServer.parameters.ValueResolver;
import jetbrains.buildServer.util.StringUtil;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BuildAgentConfigurationImpl
implements BuildAgentConfigurationEx,
AgentConfiguration {
    public static final int OWN_PORT = 9090;
    private final BuildAgentSystemInfo mySystemInfo;
    private final BuildParametersMapsFactory myMapsFactory;
    private final BuildAgentConfigurablePaths myPaths;
    private final BuildAgentConfigurationWellKnownAgentProperties myWellknown;
    private final BuildAgentConfigurationInitializer myInitializer;
    private final BuildAgentConfigurationPersister myPersister;
    private final BuildAgentConfigurationImplicitParameters myParameters;
    private final String myPingCode;
    private final AgentContextImpl myContext;
    private final List<String> myAlternativeAddresses;
    private File myPropertiesFile;
    private final Set<String> myPropertyNamesFromFile;
    private final AgentOperationModeHolder myAgentOperationModeHolder;

    public BuildAgentConfigurationImpl(@NotNull BuildAgentSystemInfo systemInfo, @NotNull BuildParametersMapsFactory mapsFactory, @NotNull AgentUnresolvedParametersFactory parametersFactory, @NotNull BuildAgentConfigurablePaths paths, @NotNull BuildAgentConfigurationWellKnownAgentProperties wellknown, @NotNull BuildAgentConfigurationInitializer initializer, @NotNull BuildAgentConfigurationPersister persister, @NotNull BuildAgentConfigurationImplicitParameters parameters, @NotNull AgentOperationModeHolder agentOperationModeHolder) {
        if (systemInfo == null) {
            BuildAgentConfigurationImpl.$$$reportNull$$$0(0);
        }
        if (mapsFactory == null) {
            BuildAgentConfigurationImpl.$$$reportNull$$$0(1);
        }
        if (parametersFactory == null) {
            BuildAgentConfigurationImpl.$$$reportNull$$$0(2);
        }
        if (paths == null) {
            BuildAgentConfigurationImpl.$$$reportNull$$$0(3);
        }
        if (wellknown == null) {
            BuildAgentConfigurationImpl.$$$reportNull$$$0(4);
        }
        if (initializer == null) {
            BuildAgentConfigurationImpl.$$$reportNull$$$0(5);
        }
        if (persister == null) {
            BuildAgentConfigurationImpl.$$$reportNull$$$0(6);
        }
        if (parameters == null) {
            BuildAgentConfigurationImpl.$$$reportNull$$$0(7);
        }
        if (agentOperationModeHolder == null) {
            BuildAgentConfigurationImpl.$$$reportNull$$$0(8);
        }
        this.myAlternativeAddresses = new CopyOnWriteArrayList<String>();
        this.myPropertyNamesFromFile = new CopyOnWriteArraySet<String>();
        this.mySystemInfo = systemInfo;
        this.myMapsFactory = mapsFactory;
        this.myPaths = paths;
        this.myWellknown = wellknown;
        this.myInitializer = initializer;
        this.myPersister = persister;
        this.myParameters = parameters;
        this.myAgentOperationModeHolder = agentOperationModeHolder;
        this.myPingCode = StringUtil.generateUniqueHashQuickly();
        this.myContext = new AgentContextImpl(parametersFactory.createAgentUnresolvedParameters((AgentEnvironmentParametersProvider)this.myParameters));
        this.myInitializer.setDefaultHandler((BuildAgentConfigurationInitializerRegistry.PropertyHandler)new /* Unavailable Anonymous Inner Class!! */);
    }

    @NotNull
    public Map<String, String> getCustomProperties() {
        Map map = this.myMapsFactory.createBuildParametersMap();
        map.putAll(this.myContext.getConfigurationParameters());
        map.putAll(this.myContext.getBuildParameters().getAllParameters());
        Map<String, String> map2 = Collections.unmodifiableMap(map);
        if (map2 == null) {
            BuildAgentConfigurationImpl.$$$reportNull$$$0(9);
        }
        return map2;
    }

    @NotNull
    public Map<String, String> getConfigurationParameters() {
        Map map = this.myContext.getConfigurationParameters();
        if (map == null) {
            BuildAgentConfigurationImpl.$$$reportNull$$$0(10);
        }
        return map;
    }

    @NotNull
    public ValueResolver getParametersResolver() {
        ValueResolver valueResolver = this.myContext.getParametersResolver();
        if (valueResolver == null) {
            BuildAgentConfigurationImpl.$$$reportNull$$$0(11);
        }
        return valueResolver;
    }

    @NotNull
    public AgentOperationMode getOperationMode() {
        AgentOperationModeEx agentOperationModeEx = this.myAgentOperationModeHolder.getOperationMode();
        if (agentOperationModeEx == null) {
            BuildAgentConfigurationImpl.$$$reportNull$$$0(12);
        }
        return agentOperationModeEx;
    }

    public void setWorkDir(@NotNull String pathToWorkDir) {
        if (pathToWorkDir == null) {
            BuildAgentConfigurationImpl.$$$reportNull$$$0(13);
        }
        this.myPaths.setWorkDir(pathToWorkDir);
    }

    @NotNull
    public String getAuthorizationToken() {
        String string = this.myWellknown.getAuthorizationToken();
        if (string == null) {
            BuildAgentConfigurationImpl.$$$reportNull$$$0(14);
        }
        return string;
    }

    @NotNull
    public String getPingCode() {
        String string = this.myPingCode;
        if (string == null) {
            BuildAgentConfigurationImpl.$$$reportNull$$$0(15);
        }
        return string;
    }

    public void updateConfiguration(@NotNull AgentRegistrationDetails details) {
        if (details == null) {
            BuildAgentConfigurationImpl.$$$reportNull$$$0(16);
        }
        this.myPersister.updateConfiguration(this.getPropertiesFile(), details);
    }

    @Nullable
    public String getInternalProperty(@NotNull String name, String def) {
        String value;
        if (name == null) {
            BuildAgentConfigurationImpl.$$$reportNull$$$0(17);
        }
        if ((value = System.getProperty(name)) == null) {
            value = this.getCustomProperties().get(name);
        }
        return value == null ? def : value;
    }

    public boolean isUpgradeDisabled() {
        return "true".equalsIgnoreCase(this.getInternalProperty("teamcity.agent.disable.autoupgrade", "false"));
    }

    public int getServerConnectionTimeout() {
        String timeoutStr = this.getInternalProperty("teamcity.agentServer.connectionTimeout", String.valueOf(180));
        try {
            int result = Integer.parseInt(timeoutStr);
            if (result < 0) {
                Loggers.AGENT.warn("Negative value specified for teamcity.agentServer.connectionTimeout property: " + timeoutStr + ". Property ignored.");
                return 0;
            }
            return result;
        }
        catch (NumberFormatException e) {
            Loggers.AGENT.warn("Invalid value specified for teamcity.agentServer.connectionTimeout property: " + timeoutStr + ". Property ignored.");
            return 0;
        }
    }

    public void addAlternativeAgentAddress(@NotNull String address) {
        if (address == null) {
            BuildAgentConfigurationImpl.$$$reportNull$$$0(18);
        }
        if (!this.myAlternativeAddresses.contains(address)) {
            this.myAlternativeAddresses.add(0, address);
        }
    }

    @NotNull
    public Collection<String> getAlternativeAddresses() {
        Collection<String> collection = Collections.unmodifiableCollection(this.myAlternativeAddresses);
        if (collection == null) {
            BuildAgentConfigurationImpl.$$$reportNull$$$0(19);
        }
        return collection;
    }

    @NotNull
    public AgentContextEx getAgentContext() {
        AgentContextImpl agentContextImpl = this.myContext;
        if (agentContextImpl == null) {
            BuildAgentConfigurationImpl.$$$reportNull$$$0(20);
        }
        return agentContextImpl;
    }

    @NotNull
    public File getAgentInfoFile() {
        return new File(this.getCacheDirectory(".teamcity-agent"), this.getUpdateContentsXmlName());
    }

    @NotNull
    public File getAgentPluginsUnpackingInfoFile() {
        return new File(this.getCacheDirectory(".teamcity-agent"), "unpacked-plugins.xml");
    }

    @NotNull
    public File getAgentUpgradeFiltersFile() {
        File file = this.myPaths.getUpgradeFiltersFile();
        if (file == null) {
            BuildAgentConfigurationImpl.$$$reportNull$$$0(21);
        }
        return file;
    }

    @NotNull
    public File getVcsCheckoutdirStateFile(@NotNull File checkoutDir) {
        if (checkoutDir == null) {
            BuildAgentConfigurationImpl.$$$reportNull$$$0(22);
        }
        String configFileName = this.getWorkDirectory().equals(checkoutDir.getParentFile()) ? checkoutDir.getName() : String.format("%08x_%s", checkoutDir.hashCode(), checkoutDir.getName());
        return new File(this.getCacheDirectory("checkoutdir-revisions"), configFileName + ".xml");
    }

    @NotNull
    public File[] getVcsCheckoutdirStateFiles() {
        File cacheDirectory = this.getCacheDirectory("checkoutdir-revisions");
        File[] files = cacheDirectory.listFiles((FilenameFilter)new /* Unavailable Anonymous Inner Class!! */);
        if (files != null) {
            if (files == null) {
                BuildAgentConfigurationImpl.$$$reportNull$$$0(23);
            }
            return files;
        }
        throw new BuildAgentStartFailedException("Unable to access checkoutdir-revisions folder.");
    }

    public void validateParameters() throws BuildAgentStartFailedException {
        String url = this.getServerUrl();
        if (StringUtil.isEmptyOrSpaces((String)url)) {
            throw new BuildAgentStartFailedException("Server url was not set. Please check 'serverUrl' key in buildAgent.properties file");
        }
    }

    @Nullable
    public String getServerProxyHost() {
        return this.getConfigurationParameters().get("teamcity.http.proxyHost");
    }

    public int getServerProxyPort() {
        String proxyPort = this.getConfigurationParameters().get("teamcity.http.proxyPort");
        if (proxyPort == null) {
            return -1;
        }
        return StringUtil.parseInt((String)proxyPort, (int)-1);
    }

    @Nullable
    public SimpleCredentials getServerProxyCredentials() {
        String login = this.getConfigurationParameters().get("teamcity.http.proxyLogin");
        String pass = this.getConfigurationParameters().get("teamcity.http.proxyPassword");
        if (StringUtil.isNotEmpty((String)login) && StringUtil.isNotEmpty((String)pass)) {
            return new SimpleCredentials(login, pass);
        }
        return null;
    }

    public void setTempDir(@NotNull String pathToTempDir) {
        if (pathToTempDir == null) {
            BuildAgentConfigurationImpl.$$$reportNull$$$0(24);
        }
        this.myPaths.setTempDir(pathToTempDir);
    }

    @NotNull
    public BuildAgentSystemInfo getSystemInfo() {
        BuildAgentSystemInfo buildAgentSystemInfo = this.mySystemInfo;
        if (buildAgentSystemInfo == null) {
            BuildAgentConfigurationImpl.$$$reportNull$$$0(25);
        }
        return buildAgentSystemInfo;
    }

    @NotNull
    public File getWorkDirectory() {
        File file = this.myPaths.getWorkDirectory();
        if (file == null) {
            BuildAgentConfigurationImpl.$$$reportNull$$$0(26);
        }
        return file;
    }

    @NotNull
    public File getTempDirectory() {
        File file = this.myPaths.getTempDirectory();
        if (file == null) {
            BuildAgentConfigurationImpl.$$$reportNull$$$0(27);
        }
        return file;
    }

    @NotNull
    public File getSystemDirectory() {
        File file = this.myPaths.getSystemDirectory();
        if (file == null) {
            BuildAgentConfigurationImpl.$$$reportNull$$$0(28);
        }
        return file;
    }

    @NotNull
    public File getBuildTempDirectory() {
        File file = this.myPaths.getBuildTempDirectory();
        if (file == null) {
            BuildAgentConfigurationImpl.$$$reportNull$$$0(29);
        }
        return file;
    }

    @NotNull
    public File getAgentTempDirectory() {
        File file = this.myPaths.getAgentTempDirectory();
        if (file == null) {
            BuildAgentConfigurationImpl.$$$reportNull$$$0(30);
        }
        return file;
    }

    @NotNull
    public File getAgentToolsDirectory() {
        File file = this.myPaths.getAgentToolsDirectory();
        if (file == null) {
            BuildAgentConfigurationImpl.$$$reportNull$$$0(31);
        }
        return file;
    }

    @NotNull
    public File getCacheDirectory() {
        File file = this.myPaths.getCacheDirectory();
        if (file == null) {
            BuildAgentConfigurationImpl.$$$reportNull$$$0(32);
        }
        return file;
    }

    @NotNull
    public File getCacheDirectory(@NotNull String key) {
        if (key == null) {
            BuildAgentConfigurationImpl.$$$reportNull$$$0(33);
        }
        File file = this.myPaths.getCacheDirectory(key);
        if (file == null) {
            BuildAgentConfigurationImpl.$$$reportNull$$$0(34);
        }
        return file;
    }

    @NonNls
    public String getServerUrl() {
        return this.myWellknown.getServerUrl();
    }

    public void addCustomProperty(@NotNull String name, @NotNull String value) {
        if (name == null) {
            BuildAgentConfigurationImpl.$$$reportNull$$$0(35);
        }
        if (value == null) {
            BuildAgentConfigurationImpl.$$$reportNull$$$0(36);
        }
        if (name.startsWith("system.")) {
            this.myContext.addSystemProperty(name.substring("system.".length()), value);
        } else if (name.startsWith("env.")) {
            this.myContext.addEnvironmentVariable(name.substring("env.".length()), value);
        } else {
            this.myContext.addConfigurationParameter(name, value);
        }
    }

    public void addConfigurationParameter(@NotNull String name, @NotNull String value) {
        if (name == null) {
            BuildAgentConfigurationImpl.$$$reportNull$$$0(37);
        }
        if (value == null) {
            BuildAgentConfigurationImpl.$$$reportNull$$$0(38);
        }
        this.myContext.addConfigurationParameter(name, value);
    }

    public synchronized boolean removeConfigurationParameter(@NotNull String name) {
        if (name == null) {
            BuildAgentConfigurationImpl.$$$reportNull$$$0(39);
        }
        if (this.myPropertyNamesFromFile.contains(name)) {
            return false;
        }
        return this.myContext.removeConfigurationParameter(name);
    }

    public int getOwnPort() {
        return this.myWellknown.getOwnPort();
    }

    public String getOwnAddress() {
        return this.myWellknown.getOwnAddress();
    }

    public void setOwnAddress(@NotNull String ownAddress) {
        if (ownAddress == null) {
            BuildAgentConfigurationImpl.$$$reportNull$$$0(40);
        }
        this.myWellknown.setOwnAddress(ownAddress);
    }

    public void initFrom(File propertiesFile) {
        this.myPropertiesFile = propertiesFile;
        this.myInitializer.initFrom(propertiesFile);
    }

    @Used(value="tests")
    public File getPropertiesFile() {
        return this.myPropertiesFile;
    }

    public String getName() {
        return this.myWellknown.getName();
    }

    public void setServerUrl(String serverUrl) {
        this.myWellknown.setServerUrl(serverUrl);
    }

    public void setOwnPort(int ownPort) {
        this.myWellknown.setOwnPort(ownPort);
    }

    public void setName(String name) {
        this.myWellknown.setName(name);
    }

    public void setAuthorizationToken(@NotNull String token) {
        if (token == null) {
            BuildAgentConfigurationImpl.$$$reportNull$$$0(41);
        }
        this.myWellknown.setAuthorizationToken(token);
    }

    public boolean shouldSkipPluginsHashCheck() {
        return Boolean.valueOf(this.getInternalProperty("teamcity.agent.skip.plugins.hash.check", "false"));
    }

    public boolean isLoadingToolsOnDemandAllowing() {
        String param = this.getConfigurationParameters().get("teamcity.tools.loadingOnDemand.enabled");
        if (param == null) {
            return true;
        }
        return Boolean.parseBoolean(param);
    }

    public boolean isAgentUpgradeWithoutRestartAllowed() {
        return Boolean.parseBoolean(this.getConfigurationParameters().getOrDefault("teamcity.agent.upgradeWithoutRestart.allowed", "true"));
    }

    public boolean isToolsRemovalWithoutRestartAllowed() {
        return Boolean.parseBoolean(this.getConfigurationParameters().getOrDefault("teamcity.agent.toolsRemovalWithoutRestart.allowed", "true"));
    }

    public void addEnvironmentVariable(@NotNull String key, @NotNull String value) {
        if (key == null) {
            BuildAgentConfigurationImpl.$$$reportNull$$$0(42);
        }
        if (value == null) {
            BuildAgentConfigurationImpl.$$$reportNull$$$0(43);
        }
        this.myContext.addEnvironmentVariable(key, value);
    }

    public void addSystemProperty(@NotNull String key, @NotNull String value) {
        if (key == null) {
            BuildAgentConfigurationImpl.$$$reportNull$$$0(44);
        }
        if (value == null) {
            BuildAgentConfigurationImpl.$$$reportNull$$$0(45);
        }
        this.myContext.addSystemProperty(key, value);
    }

    @NotNull
    public BuildParametersMap getBuildParameters() {
        BuildParametersMap buildParametersMap = this.myContext.getBuildParameters();
        if (buildParametersMap == null) {
            BuildAgentConfigurationImpl.$$$reportNull$$$0(46);
        }
        return buildParametersMap;
    }

    @NotNull
    public File getAgentConfDirectory() {
        File file = this.myPaths.getAgentConfDirectory();
        if (file == null) {
            BuildAgentConfigurationImpl.$$$reportNull$$$0(47);
        }
        return file;
    }

    @NotNull
    public File getAgentLibDirectory() {
        File file = this.myPaths.getAgentLibDirectory();
        if (file == null) {
            BuildAgentConfigurationImpl.$$$reportNull$$$0(48);
        }
        return file;
    }

    @NotNull
    public File getAgentPluginsDirectory() {
        File file = this.myPaths.getAgentPluginsDirectory();
        if (file == null) {
            BuildAgentConfigurationImpl.$$$reportNull$$$0(49);
        }
        return file;
    }

    @NotNull
    public File getAgentUpdateDirectory() {
        File file = this.myPaths.getAgentUpdateDirectory();
        if (file == null) {
            BuildAgentConfigurationImpl.$$$reportNull$$$0(50);
        }
        return file;
    }

    @NotNull
    public File getAgentHomeDirectory() {
        File file = this.myPaths.getAgentHomeDirectory();
        if (file == null) {
            BuildAgentConfigurationImpl.$$$reportNull$$$0(51);
        }
        return file;
    }

    @NotNull
    public File getAgentLogsDirectory() {
        File file = this.myPaths.getAgentLogsDirectory();
        if (file == null) {
            BuildAgentConfigurationImpl.$$$reportNull$$$0(52);
        }
        return file;
    }

    @NotNull
    public String getAgentZipName() {
        return "buildAgent.zip";
    }

    @NotNull
    public String getUpdateContentsXmlName() {
        return "teamcity-agent.xml";
    }

    @NotNull
    public String getUpdatePluginsBasePath() {
        return "/update/plugins/";
    }

    private static /* synthetic */ void $$$reportNull$$$0(int n) {
        Object[] objectArray;
        Object[] objectArray2;
        Object[] objectArray3 = new Object[switch (n) {
            default -> 3;
            case 9, 10, 11, 12, 14, 15, 19, 20, 21, 23, 25, 26, 27, 28, 29, 30, 31, 32, 34, 46, 47, 48, 49, 50, 51, 52 -> 2;
        }];
        switch (n) {
            default: {
                objectArray2 = objectArray3;
                objectArray3[0] = "systemInfo";
                break;
            }
            case 1: {
                objectArray2 = objectArray3;
                objectArray3[0] = "mapsFactory";
                break;
            }
            case 2: {
                objectArray2 = objectArray3;
                objectArray3[0] = "parametersFactory";
                break;
            }
            case 3: {
                objectArray2 = objectArray3;
                objectArray3[0] = "paths";
                break;
            }
            case 4: {
                objectArray2 = objectArray3;
                objectArray3[0] = "wellknown";
                break;
            }
            case 5: {
                objectArray2 = objectArray3;
                objectArray3[0] = "initializer";
                break;
            }
            case 6: {
                objectArray2 = objectArray3;
                objectArray3[0] = "persister";
                break;
            }
            case 7: {
                objectArray2 = objectArray3;
                objectArray3[0] = "parameters";
                break;
            }
            case 8: {
                objectArray2 = objectArray3;
                objectArray3[0] = "agentOperationModeHolder";
                break;
            }
            case 9: 
            case 10: 
            case 11: 
            case 12: 
            case 14: 
            case 15: 
            case 19: 
            case 20: 
            case 21: 
            case 23: 
            case 25: 
            case 26: 
            case 27: 
            case 28: 
            case 29: 
            case 30: 
            case 31: 
            case 32: 
            case 34: 
            case 46: 
            case 47: 
            case 48: 
            case 49: 
            case 50: 
            case 51: 
            case 52: {
                objectArray2 = objectArray3;
                objectArray3[0] = "jetbrains/buildServer/agent/impl/config/BuildAgentConfigurationImpl";
                break;
            }
            case 13: {
                objectArray2 = objectArray3;
                objectArray3[0] = "pathToWorkDir";
                break;
            }
            case 16: {
                objectArray2 = objectArray3;
                objectArray3[0] = "details";
                break;
            }
            case 17: 
            case 35: 
            case 37: 
            case 39: {
                objectArray2 = objectArray3;
                objectArray3[0] = "name";
                break;
            }
            case 18: {
                objectArray2 = objectArray3;
                objectArray3[0] = "address";
                break;
            }
            case 22: {
                objectArray2 = objectArray3;
                objectArray3[0] = "checkoutDir";
                break;
            }
            case 24: {
                objectArray2 = objectArray3;
                objectArray3[0] = "pathToTempDir";
                break;
            }
            case 33: 
            case 42: 
            case 44: {
                objectArray2 = objectArray3;
                objectArray3[0] = "key";
                break;
            }
            case 36: 
            case 38: 
            case 43: 
            case 45: {
                objectArray2 = objectArray3;
                objectArray3[0] = "value";
                break;
            }
            case 40: {
                objectArray2 = objectArray3;
                objectArray3[0] = "ownAddress";
                break;
            }
            case 41: {
                objectArray2 = objectArray3;
                objectArray3[0] = "token";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray2;
                objectArray2[1] = "jetbrains/buildServer/agent/impl/config/BuildAgentConfigurationImpl";
                break;
            }
            case 9: {
                objectArray = objectArray2;
                objectArray2[1] = "getCustomProperties";
                break;
            }
            case 10: {
                objectArray = objectArray2;
                objectArray2[1] = "getConfigurationParameters";
                break;
            }
            case 11: {
                objectArray = objectArray2;
                objectArray2[1] = "getParametersResolver";
                break;
            }
            case 12: {
                objectArray = objectArray2;
                objectArray2[1] = "getOperationMode";
                break;
            }
            case 14: {
                objectArray = objectArray2;
                objectArray2[1] = "getAuthorizationToken";
                break;
            }
            case 15: {
                objectArray = objectArray2;
                objectArray2[1] = "getPingCode";
                break;
            }
            case 19: {
                objectArray = objectArray2;
                objectArray2[1] = "getAlternativeAddresses";
                break;
            }
            case 20: {
                objectArray = objectArray2;
                objectArray2[1] = "getAgentContext";
                break;
            }
            case 21: {
                objectArray = objectArray2;
                objectArray2[1] = "getAgentUpgradeFiltersFile";
                break;
            }
            case 23: {
                objectArray = objectArray2;
                objectArray2[1] = "getVcsCheckoutdirStateFiles";
                break;
            }
            case 25: {
                objectArray = objectArray2;
                objectArray2[1] = "getSystemInfo";
                break;
            }
            case 26: {
                objectArray = objectArray2;
                objectArray2[1] = "getWorkDirectory";
                break;
            }
            case 27: {
                objectArray = objectArray2;
                objectArray2[1] = "getTempDirectory";
                break;
            }
            case 28: {
                objectArray = objectArray2;
                objectArray2[1] = "getSystemDirectory";
                break;
            }
            case 29: {
                objectArray = objectArray2;
                objectArray2[1] = "getBuildTempDirectory";
                break;
            }
            case 30: {
                objectArray = objectArray2;
                objectArray2[1] = "getAgentTempDirectory";
                break;
            }
            case 31: {
                objectArray = objectArray2;
                objectArray2[1] = "getAgentToolsDirectory";
                break;
            }
            case 32: 
            case 34: {
                objectArray = objectArray2;
                objectArray2[1] = "getCacheDirectory";
                break;
            }
            case 46: {
                objectArray = objectArray2;
                objectArray2[1] = "getBuildParameters";
                break;
            }
            case 47: {
                objectArray = objectArray2;
                objectArray2[1] = "getAgentConfDirectory";
                break;
            }
            case 48: {
                objectArray = objectArray2;
                objectArray2[1] = "getAgentLibDirectory";
                break;
            }
            case 49: {
                objectArray = objectArray2;
                objectArray2[1] = "getAgentPluginsDirectory";
                break;
            }
            case 50: {
                objectArray = objectArray2;
                objectArray2[1] = "getAgentUpdateDirectory";
                break;
            }
            case 51: {
                objectArray = objectArray2;
                objectArray2[1] = "getAgentHomeDirectory";
                break;
            }
            case 52: {
                objectArray = objectArray2;
                objectArray2[1] = "getAgentLogsDirectory";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray;
                objectArray[2] = "<init>";
                break;
            }
            case 9: 
            case 10: 
            case 11: 
            case 12: 
            case 14: 
            case 15: 
            case 19: 
            case 20: 
            case 21: 
            case 23: 
            case 25: 
            case 26: 
            case 27: 
            case 28: 
            case 29: 
            case 30: 
            case 31: 
            case 32: 
            case 34: 
            case 46: 
            case 47: 
            case 48: 
            case 49: 
            case 50: 
            case 51: 
            case 52: {
                break;
            }
            case 13: {
                objectArray = objectArray;
                objectArray[2] = "setWorkDir";
                break;
            }
            case 16: {
                objectArray = objectArray;
                objectArray[2] = "updateConfiguration";
                break;
            }
            case 17: {
                objectArray = objectArray;
                objectArray[2] = "getInternalProperty";
                break;
            }
            case 18: {
                objectArray = objectArray;
                objectArray[2] = "addAlternativeAgentAddress";
                break;
            }
            case 22: {
                objectArray = objectArray;
                objectArray[2] = "getVcsCheckoutdirStateFile";
                break;
            }
            case 24: {
                objectArray = objectArray;
                objectArray[2] = "setTempDir";
                break;
            }
            case 33: {
                objectArray = objectArray;
                objectArray[2] = "getCacheDirectory";
                break;
            }
            case 35: 
            case 36: {
                objectArray = objectArray;
                objectArray[2] = "addCustomProperty";
                break;
            }
            case 37: 
            case 38: {
                objectArray = objectArray;
                objectArray[2] = "addConfigurationParameter";
                break;
            }
            case 39: {
                objectArray = objectArray;
                objectArray[2] = "removeConfigurationParameter";
                break;
            }
            case 40: {
                objectArray = objectArray;
                objectArray[2] = "setOwnAddress";
                break;
            }
            case 41: {
                objectArray = objectArray;
                objectArray[2] = "setAuthorizationToken";
                break;
            }
            case 42: 
            case 43: {
                objectArray = objectArray;
                objectArray[2] = "addEnvironmentVariable";
                break;
            }
            case 44: 
            case 45: {
                objectArray = objectArray;
                objectArray[2] = "addSystemProperty";
                break;
            }
        }
        String string = String.format(v0, objectArray);
        throw switch (n) {
            default -> new IllegalArgumentException(string);
            case 9, 10, 11, 12, 14, 15, 19, 20, 21, 23, 25, 26, 27, 28, 29, 30, 31, 32, 34, 46, 47, 48, 49, 50, 51, 52 -> new IllegalStateException(string);
        };
    }
}
