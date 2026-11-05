/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.intellij.openapi.diagnostic.Logger
 *  com.intellij.openapi.util.Pair
 *  jetbrains.buildServer.DevelopmentMode
 *  jetbrains.buildServer.log.Loggers
 *  jetbrains.buildServer.plugins.NotLoadedPlugin
 *  jetbrains.buildServer.plugins.PluginClassesLoadedListener
 *  jetbrains.buildServer.plugins.PluginData
 *  jetbrains.buildServer.plugins.PluginDisabledInfoProvider
 *  jetbrains.buildServer.plugins.PluginInfoLoadedListener
 *  jetbrains.buildServer.plugins.PluginLifecycleListener
 *  jetbrains.buildServer.plugins.PluginManager
 *  jetbrains.buildServer.plugins.PluginManagerListener
 *  jetbrains.buildServer.plugins.PluginVersionComparator
 *  jetbrains.buildServer.plugins.PluginsCollection
 *  jetbrains.buildServer.plugins.PluginsLookupCollector
 *  jetbrains.buildServer.plugins.ProcessPlugins
 *  jetbrains.buildServer.plugins.ReloadablePlugins
 *  jetbrains.buildServer.plugins.bean.PluginDependencyInfo
 *  jetbrains.buildServer.plugins.bean.PluginInfo
 *  jetbrains.buildServer.plugins.classLoaders.PluginClassLoadingInfo
 *  jetbrains.buildServer.plugins.classLoaders.SharedClassloaderFactory
 *  jetbrains.buildServer.plugins.classLoaders.StandaloneClassloaderFactory
 *  jetbrains.buildServer.plugins.classLoaders.TeamCityClassLoader
 *  jetbrains.buildServer.plugins.exceptions.LoadPluginException
 *  jetbrains.buildServer.plugins.exceptions.PluginNotLoadedException
 *  jetbrains.buildServer.plugins.exceptions.UnloadPluginException
 *  jetbrains.buildServer.plugins.files.PluginFileSystemLookup
 *  jetbrains.buildServer.plugins.impl.PluginLifecycleEventDispatcher
 *  jetbrains.buildServer.plugins.impl.PluginManagerEventDispatcher
 *  jetbrains.buildServer.plugins.impl.PluginsStateProviderImpl
 *  jetbrains.buildServer.plugins.loaders.PluginInfosBuilder
 *  jetbrains.buildServer.serverSide.TeamCityProperties
 *  jetbrains.buildServer.util.CaseInsensitiveStringComparator
 *  jetbrains.buildServer.util.EventDispatcher
 *  jetbrains.buildServer.util.graph.DAG
 *  jetbrains.buildServer.util.graph.DAGs
 *  jetbrains.buildServer.version.ServerVersionHolder
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 *  org.springframework.context.ConfigurableApplicationContext
 */
package jetbrains.buildServer.plugins;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.util.Pair;
import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EventListener;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import jetbrains.buildServer.DevelopmentMode;
import jetbrains.buildServer.log.Loggers;
import jetbrains.buildServer.plugins.NotLoadedPlugin;
import jetbrains.buildServer.plugins.PluginClassesLoadedListener;
import jetbrains.buildServer.plugins.PluginData;
import jetbrains.buildServer.plugins.PluginDisabledInfoProvider;
import jetbrains.buildServer.plugins.PluginInfoLoadedListener;
import jetbrains.buildServer.plugins.PluginLifecycleListener;
import jetbrains.buildServer.plugins.PluginManager;
import jetbrains.buildServer.plugins.PluginManagerListener;
import jetbrains.buildServer.plugins.PluginVersionComparator;
import jetbrains.buildServer.plugins.PluginsCollection;
import jetbrains.buildServer.plugins.PluginsLookupCollector;
import jetbrains.buildServer.plugins.ProcessPlugins;
import jetbrains.buildServer.plugins.ReloadablePlugins;
import jetbrains.buildServer.plugins.bean.PluginDependencyInfo;
import jetbrains.buildServer.plugins.bean.PluginInfo;
import jetbrains.buildServer.plugins.classLoaders.PluginClassLoadingInfo;
import jetbrains.buildServer.plugins.classLoaders.SharedClassloaderFactory;
import jetbrains.buildServer.plugins.classLoaders.StandaloneClassloaderFactory;
import jetbrains.buildServer.plugins.classLoaders.TeamCityClassLoader;
import jetbrains.buildServer.plugins.exceptions.LoadPluginException;
import jetbrains.buildServer.plugins.exceptions.PluginNotLoadedException;
import jetbrains.buildServer.plugins.exceptions.UnloadPluginException;
import jetbrains.buildServer.plugins.files.PluginFileSystemLookup;
import jetbrains.buildServer.plugins.impl.PluginLifecycleEventDispatcher;
import jetbrains.buildServer.plugins.impl.PluginManagerEventDispatcher;
import jetbrains.buildServer.plugins.impl.PluginsStateProviderImpl;
import jetbrains.buildServer.plugins.loaders.PluginInfosBuilder;
import jetbrains.buildServer.serverSide.TeamCityProperties;
import jetbrains.buildServer.util.CaseInsensitiveStringComparator;
import jetbrains.buildServer.util.EventDispatcher;
import jetbrains.buildServer.util.graph.DAG;
import jetbrains.buildServer.util.graph.DAGs;
import jetbrains.buildServer.version.ServerVersionHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.context.ConfigurableApplicationContext;

public class PluginManagerImpl
implements PluginManager {
    private static final Logger LOG = Logger.getInstance((String)PluginManagerImpl.class.getName());
    private final EventDispatcher<PluginInfoLoadedListener> myPluginInfoLoadedListeners;
    private final EventDispatcher<PluginClassesLoadedListener> myPluginClassesLoadedListeners;
    @NotNull
    private PluginFileSystemLookup myLookup;
    @NotNull
    private final StandaloneClassloaderFactory myStandaloneClassloaderFactory;
    @NotNull
    private final SharedClassloaderFactory mySharedClassloaderFactory;
    @NotNull
    private final EventDispatcher<PluginManagerListener> myPluginLifecycleListeners;
    @NotNull
    private final PluginsStateProviderImpl myPluginsStateProvider;
    @NotNull
    private final ReloadablePlugins myReloadablePlugins;
    @NotNull
    private final PluginsLookupCollector myCollector;

    public PluginManagerImpl(@NotNull StandaloneClassloaderFactory standaloneClassloaderFactory, @NotNull SharedClassloaderFactory sharedClassloaderFactory, @NotNull PluginVersionComparator versionComparator, @NotNull PluginFileSystemLookup lookup, @NotNull PluginDisabledInfoProvider pluginDisabledInfoProvider, @NotNull PluginsStateProviderImpl pluginsStateProvider, @NotNull ReloadablePlugins reloadablePlugins) {
        if (standaloneClassloaderFactory == null) {
            PluginManagerImpl.$$$reportNull$$$0(0);
        }
        if (sharedClassloaderFactory == null) {
            PluginManagerImpl.$$$reportNull$$$0(1);
        }
        if (versionComparator == null) {
            PluginManagerImpl.$$$reportNull$$$0(2);
        }
        if (lookup == null) {
            PluginManagerImpl.$$$reportNull$$$0(3);
        }
        if (pluginDisabledInfoProvider == null) {
            PluginManagerImpl.$$$reportNull$$$0(4);
        }
        if (pluginsStateProvider == null) {
            PluginManagerImpl.$$$reportNull$$$0(5);
        }
        if (reloadablePlugins == null) {
            PluginManagerImpl.$$$reportNull$$$0(6);
        }
        this.myPluginInfoLoadedListeners = EventDispatcher.create(PluginInfoLoadedListener.class);
        this.myPluginClassesLoadedListeners = EventDispatcher.create(PluginClassesLoadedListener.class);
        this.myPluginLifecycleListeners = new PluginManagerEventDispatcher();
        this.myStandaloneClassloaderFactory = standaloneClassloaderFactory;
        this.mySharedClassloaderFactory = sharedClassloaderFactory;
        this.myLookup = lookup;
        this.myPluginsStateProvider = pluginsStateProvider;
        this.myReloadablePlugins = reloadablePlugins;
        this.myCollector = new PluginsLookupCollector(versionComparator, pluginDisabledInfoProvider, reloadablePlugins);
    }

    public EventDispatcher<PluginManagerListener> getPluginLifecycleEventDispatcher() {
        return this.myPluginLifecycleListeners;
    }

    public void loadPlugin(@NotNull PluginInfo pluginInfo) throws LoadPluginException {
        block15: {
            String errorMessage;
            if (pluginInfo == null) {
                PluginManagerImpl.$$$reportNull$$$0(7);
            }
            if ((errorMessage = this.myReloadablePlugins.canLoadPluginInRuntime(pluginInfo)) != null) {
                throw new LoadPluginException("Cannot load plugin " + pluginInfo.getPluginName() + ": " + errorMessage);
            }
            ArrayList<PluginData> parents = new ArrayList<PluginData>();
            for (PluginDependencyInfo dependency : pluginInfo.getDependencies()) {
                PluginData pluginData = this.myPluginsStateProvider.findPluginData(dependency.getName());
                if (pluginData == null) {
                    throw new LoadPluginException("Plugin dependency " + dependency.getName() + " is not loaded yet");
                }
                PluginClassLoadingInfo classloadingInfo = pluginData.getPluginInfo().getClassloadingInfo();
                if (classloadingInfo == null || !classloadingInfo.useStandaloneClassloader()) {
                    throw new LoadPluginException("Plugin dependency " + dependency.getName() + " uses shared classloader");
                }
                parents.add(pluginData);
            }
            try {
                PluginsCollection collectionFromInfo = this.myCollector.toPluginsCollection(Collections.singletonList(pluginInfo), parents, true);
                if (!collectionFromInfo.getNotLoadedPlugins().isEmpty()) {
                    this.lifecycleMulticaster().beforePluginLoaded(pluginInfo);
                    this.lifecycleMulticaster().pluginLoadFailed(pluginInfo);
                    throw new LoadPluginException("Cannot create PluginData for " + pluginInfo.getPluginName() + ": " + ((NotLoadedPlugin)collectionFromInfo.getNotLoadedPlugins().get(0)).getReason());
                }
                PluginData plugin = this.findPluginData(collectionFromInfo, pluginInfo);
                if (plugin == null) {
                    throw new RuntimeException("Cannot get PluginData for " + pluginInfo.getPluginName());
                }
                plugin.setShouldAddPersonalDispatcher(true);
                this.doLoadPluginsCollection(collectionFromInfo);
                for (PluginData pluginData : collectionFromInfo.getPluginDatas()) {
                    this.lifecycleMulticaster().pluginLoadedInRuntime(pluginData);
                }
                this.myPluginsStateProvider.addLoadedPlugin(plugin);
                this.myPluginsStateProvider.removeNotLoadedPlugin(pluginInfo);
                this.updateOtherVersionNotLoadedPlugins(pluginInfo);
                if (!plugin.getLoadErrors().isEmpty()) {
                    throw new LoadPluginException((Throwable)plugin.getLoadErrors().iterator().next());
                }
                if (plugin.getSpringContext() == null) break block15;
                PluginLifecycleEventDispatcher bean = null;
                try {
                    bean = (PluginLifecycleEventDispatcher)plugin.getSpringContext().getBean(PluginLifecycleEventDispatcher.class);
                }
                catch (Exception exception) {
                    // empty catch block
                }
                if (bean != null) {
                    ((PluginLifecycleListener)bean.getMulticaster()).pluginLoaded();
                }
            }
            catch (LoadPluginException ex) {
                throw ex;
            }
            catch (Throwable ex) {
                this.lifecycleMulticaster().pluginLoadFailed(pluginInfo);
                throw new LoadPluginException(ex);
            }
        }
    }

    @Nullable
    private PluginData findPluginData(@NotNull PluginsCollection pluginsCollection, @NotNull PluginInfo pluginInfo) {
        if (pluginsCollection == null) {
            PluginManagerImpl.$$$reportNull$$$0(8);
        }
        if (pluginInfo == null) {
            PluginManagerImpl.$$$reportNull$$$0(9);
        }
        AtomicReference loadingPluginData = new AtomicReference();
        pluginsCollection.foreachPlugins((ProcessPlugins)new /* Unavailable Anonymous Inner Class!! */);
        return (PluginData)loadingPluginData.get();
    }

    private void updateOtherVersionNotLoadedPlugins(@NotNull PluginInfo pluginInfo) {
        if (pluginInfo == null) {
            PluginManagerImpl.$$$reportNull$$$0(10);
        }
        ArrayList<PluginInfo> notLoadedPlugins = new ArrayList<PluginInfo>();
        Collection oldNotLoadedPlugins = this.myPluginsStateProvider.getPluginsState().findNotLoadedPlugins(pluginInfo.getPluginName());
        for (NotLoadedPlugin notLoadedPlugin : oldNotLoadedPlugins) {
            notLoadedPlugins.add(notLoadedPlugin.getPlugin());
        }
        if (!notLoadedPlugins.isEmpty()) {
            notLoadedPlugins.add(pluginInfo);
            PluginsCollection pluginsCollection = this.myCollector.toPluginsCollection(notLoadedPlugins, Collections.emptyList(), false);
            List updatedNotLoadedPlugins = pluginsCollection.getNotLoadedPlugins();
            for (NotLoadedPlugin nlp : oldNotLoadedPlugins) {
                this.myPluginsStateProvider.removeNotLoadedPlugin(nlp.getPlugin());
            }
            for (NotLoadedPlugin nlp : updatedNotLoadedPlugins) {
                this.myPluginsStateProvider.addNotLoadedPlugin(nlp);
            }
        }
    }

    public List<PluginInfo> unloadPlugin(@NotNull PluginInfo pluginInfo) throws UnloadPluginException {
        PluginData pluginData;
        if (pluginInfo == null) {
            PluginManagerImpl.$$$reportNull$$$0(11);
        }
        if ((pluginData = this.myPluginsStateProvider.findPluginData(pluginInfo.getPluginName())) == null) {
            throw new PluginNotLoadedException();
        }
        LinkedList<PluginInfo> list = new LinkedList<PluginInfo>();
        ArrayList<Pair> edges = new ArrayList<Pair>();
        list.add(pluginInfo);
        edges.add(Pair.create((Object)pluginInfo, null));
        while (!list.isEmpty()) {
            PluginInfo process = (PluginInfo)list.remove(0);
            if (process.getClassloadingInfo() == null || !process.getClassloadingInfo().useStandaloneClassloader()) {
                throw new UnloadPluginException("Loading plugins with shared classloader is not supported yet");
            }
            String errorMessage = this.myReloadablePlugins.canUnloadPluginInRuntime(process);
            if (errorMessage != null) {
                throw new UnloadPluginException("Cannot unload plugin " + process.getPluginName() + ": " + errorMessage);
            }
            for (PluginInfo pi : this.getDetectedPlugins()) {
                PluginData loadedPlugin = this.myPluginsStateProvider.findPluginData(pi.getPluginName());
                if (loadedPlugin == null || loadedPlugin.getPluginName().equals(process.getPluginName())) continue;
                for (PluginDependencyInfo dependency : loadedPlugin.getPluginInfo().getDependencies()) {
                    if (!dependency.getName().equals(process.getPluginName())) continue;
                    edges.add(Pair.create((Object)process, (Object)loadedPlugin.getPluginInfo()));
                    list.add(loadedPlugin.getPluginInfo());
                }
            }
        }
        return this.unloadPlugins((DAG<PluginInfo>)DAGs.createFromEdges(edges));
    }

    private List<PluginInfo> unloadPlugins(DAG<PluginInfo> toUnload) throws UnloadPluginException {
        List sorted = toUnload.toposort();
        for (PluginInfo pluginInfo : sorted) {
            PluginData pluginData = this.myPluginsStateProvider.findPluginData(pluginInfo.getPluginName());
            if (pluginData == null) {
                throw new PluginNotLoadedException();
            }
            try {
                this.lifecycleMulticaster().beforePluginUnloaded(pluginInfo);
                if (pluginData.getSpringContext() != null) {
                    this.fireBeforePluginUnloaded(pluginData.getSpringContext());
                }
                this.doUnloadPlugin(pluginData);
                this.myPluginsStateProvider.removeLoadedPlugin(pluginData);
                PluginInfo recreated = this.myLookup.loadPluginInfo(pluginInfo.getPluginRoot());
                this.myPluginsStateProvider.addNotLoadedPlugin(new NotLoadedPlugin(recreated, "unloaded in runtime"));
                this.lifecycleMulticaster().pluginUnloaded(pluginInfo);
            }
            catch (Throwable ex) {
                this.lifecycleMulticaster().pluginUnloadFailed(pluginInfo);
                throw new UnloadPluginException(ex);
            }
        }
        return sorted;
    }

    private void fireBeforePluginUnloaded(@NotNull ConfigurableApplicationContext springContext) {
        if (springContext == null) {
            PluginManagerImpl.$$$reportNull$$$0(12);
        }
        Collection beans = springContext.getBeansOfType(PluginLifecycleEventDispatcher.class).values();
        for (PluginLifecycleEventDispatcher bean : beans) {
            ((PluginLifecycleListener)bean.getMulticaster()).beforePluginUnloaded();
        }
    }

    private boolean waitForUnload() {
        return TeamCityProperties.getBoolean((String)"teamcity.plugins.alwaysWaitForFullUnload");
    }

    private boolean doUnloadPlugin(@NotNull PluginData pluginData) {
        if (pluginData == null) {
            PluginManagerImpl.$$$reportNull$$$0(13);
        }
        boolean classLoaderExists = pluginData.hasClassloader();
        ReferenceQueue singing = new ReferenceQueue();
        PhantomReference<TeamCityClassLoader> canaryCage = new PhantomReference<TeamCityClassLoader>(pluginData.getClassloader(), singing);
        this.disposeSpringContext(pluginData);
        if (classLoaderExists) {
            pluginData.setClassloader(null);
        }
        this.lifecycleMulticaster().pluginResourcesCleaned(pluginData.getPluginInfo());
        if (classLoaderExists && (DevelopmentMode.isEnabled || this.waitForUnload())) {
            try {
                Reference poll;
                LinkedList<SoftReference<byte[]>> list = new LinkedList<SoftReference<byte[]>>();
                long freeMemory = Runtime.getRuntime().freeMemory() * 2L / 3L;
                while ((poll = singing.poll()) == null) {
                    list.add(new SoftReference<byte[]>(new byte[Math.min(100000000, Math.max(1000000, (int)freeMemory))]));
                    if (((SoftReference)list.get(0)).get() != null) continue;
                    poll = singing.poll();
                    break;
                }
                if (poll == null) {
                    Loggers.SERVER.warn("Plugin " + pluginData.getPluginName() + " was not fully unloaded: ClassLoader was not released");
                    return false;
                }
                Loggers.SERVER.info("Plugin " + pluginData.getPluginName() + " was fully unloaded");
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return true;
    }

    private void disposeSpringContext(@NotNull PluginData pluginData) {
        ConfigurableApplicationContext springContext;
        if (pluginData == null) {
            PluginManagerImpl.$$$reportNull$$$0(14);
        }
        if ((springContext = pluginData.getSpringContext()) != null) {
            springContext.close();
            pluginData.getLoadErrors().clear();
            pluginData.removeSpringContext();
        }
    }

    public void setLookup(@NotNull PluginFileSystemLookup lookup) {
        if (lookup == null) {
            PluginManagerImpl.$$$reportNull$$$0(15);
        }
        this.myLookup = lookup;
    }

    @NotNull
    public PluginFileSystemLookup getLookup() {
        PluginFileSystemLookup pluginFileSystemLookup = this.myLookup;
        if (pluginFileSystemLookup == null) {
            PluginManagerImpl.$$$reportNull$$$0(16);
        }
        return pluginFileSystemLookup;
    }

    public void addPluginInfoLoadedListener(@NotNull PluginInfoLoadedListener listener) {
        if (listener == null) {
            PluginManagerImpl.$$$reportNull$$$0(17);
        }
        this.myPluginInfoLoadedListeners.addListener((EventListener)listener);
    }

    public void addPluginClassesLoadedListener(@NotNull PluginClassesLoadedListener listener) {
        if (listener == null) {
            PluginManagerImpl.$$$reportNull$$$0(18);
        }
        this.myPluginClassesLoadedListeners.addListener((EventListener)listener);
    }

    private PluginManagerListener lifecycleMulticaster() {
        return (PluginManagerListener)this.myPluginLifecycleListeners.getMulticaster();
    }

    public void loadPlugins(Collection<PluginInfo> pluginsToLoad) {
        this.loadPlugins(pluginsToLoad, ex -> false);
    }

    public void loadPlugins(Collection<PluginInfo> pluginsToLoad, Function<LoadPluginException, Boolean> shouldStop) {
        PluginsCollection pluginsCollection = this.myCollector.toPluginsCollection(pluginsToLoad, this.myPluginsStateProvider.getPluginsState().getLoadedPluginsData(), true);
        AtomicBoolean stop = new AtomicBoolean();
        pluginsCollection.foreachPlugins(plugin -> {
            if (stop.get()) {
                return;
            }
            PluginInfo pluginInfo = plugin.getPluginInfo();
            try {
                this.loadPlugin(pluginInfo);
            }
            catch (LoadPluginException e) {
                LOG.error("Could not load the plugin " + String.valueOf(pluginInfo), (Throwable)e);
                stop.set((Boolean)shouldStop.apply(e));
            }
        });
    }

    public void loadPlugins() {
        this.logSeparator();
        LOG.info("Plugins initialization started...");
        PluginsCollection plugins = this.lookupPlugins();
        this.logFoundPlugins(plugins);
        this.doLoadPluginsCollection(plugins);
        this.setDetectedPluginInfos(plugins);
        this.setNotLoadedPlugins(plugins);
        this.logSeparator();
    }

    @NotNull
    private PluginsCollection lookupPlugins() {
        this.lookupPlugins((PluginInfosBuilder)this.myCollector);
        PluginsCollection pluginsCollection = this.myCollector.toPluginsCollection();
        if (pluginsCollection == null) {
            PluginManagerImpl.$$$reportNull$$$0(19);
        }
        return pluginsCollection;
    }

    protected void doLoadPluginsCollection(PluginsCollection plugins) {
        plugins.foreachPlugins((ProcessPlugins)new /* Unavailable Anonymous Inner Class!! */);
        this.firePluginInfoLoaded(plugins);
        this.fireNotLoadedPluginInfo(plugins);
        this.loadPluginsClasses(plugins);
        this.logPluginsWithoutClassloaders(plugins);
        this.firePluginClassesLoaded(plugins);
        plugins.foreachPlugins((ProcessPlugins)new /* Unavailable Anonymous Inner Class!! */);
        this.logLoadedPlugins(plugins);
    }

    private void fireNotLoadedPluginInfo(PluginsCollection plugins) {
        plugins.getNotLoadedPlugins().forEach(it -> ((PluginInfoLoadedListener)this.myPluginInfoLoadedListeners.getMulticaster()).notLoadedPluginFound(it.getPlugin()));
    }

    @NotNull
    public Collection<PluginInfo> getDetectedPlugins() {
        Collection collection = this.myPluginsStateProvider.getPluginsState().getLoadedPlugins();
        if (collection == null) {
            PluginManagerImpl.$$$reportNull$$$0(20);
        }
        return collection;
    }

    @NotNull
    public Collection<NotLoadedPlugin> getNotLoadedPlugins() {
        Collection collection = this.myPluginsStateProvider.getPluginsState().getNotLoadedPlugins();
        if (collection == null) {
            PluginManagerImpl.$$$reportNull$$$0(21);
        }
        return collection;
    }

    private void logSeparator() {
        LOG.info("===========================================================");
    }

    private void loadPluginsClasses(PluginsCollection plugins) {
        plugins.createClassloaders(this.myStandaloneClassloaderFactory, this.mySharedClassloaderFactory);
    }

    private void lookupPlugins(PluginInfosBuilder plugins) {
        LOG.info("Scanning plugins folders");
        this.myLookup.loadPluginsFrom(plugins);
    }

    private void logPluginsWithoutClassloaders(PluginsCollection plugins) {
        plugins.foreachPlugins((ProcessPlugins)new /* Unavailable Anonymous Inner Class!! */);
    }

    private void firePluginClassesLoaded(PluginsCollection plugins) {
        plugins.foreachLoadedPluginsAsync((ProcessPlugins)new /* Unavailable Anonymous Inner Class!! */, TeamCityProperties.getInteger((String)"teamcity.pluginsManager.initPlugins.threadsNum", (int)3));
    }

    private void firePluginInfoLoaded(PluginsCollection plugins) {
        plugins.foreachPlugins((ProcessPlugins)new /* Unavailable Anonymous Inner Class!! */);
    }

    private void logFoundPlugins(PluginsCollection plugins) {
        TreeSet bundledPlugins = new TreeSet(new CaseInsensitiveStringComparator());
        TreeSet otherPlugins = new TreeSet(new CaseInsensitiveStringComparator());
        plugins.foreachPlugins((ProcessPlugins)new /* Unavailable Anonymous Inner Class!! */);
        if (!otherPlugins.isEmpty()) {
            LOG.info("Found " + otherPlugins.size() + " non bundled plugins: " + String.valueOf(otherPlugins));
        }
        LOG.info("Found " + bundledPlugins.size() + " bundled plugins: " + String.valueOf(bundledPlugins));
    }

    public static boolean isBundled(@NotNull PluginData plugin) {
        if (plugin == null) {
            PluginManagerImpl.$$$reportNull$$$0(22);
        }
        String pluginVersion = plugin.getPluginInfo().getPluginVersion();
        if (DevelopmentMode.isEnabled && "@Plugin_Version@".equals(pluginVersion)) {
            return true;
        }
        return ServerVersionHolder.getVersion().getBuildNumber().equals(pluginVersion);
    }

    private void logLoadedPlugins(PluginsCollection plugins) {
        TreeSet pluginNames = new TreeSet(new CaseInsensitiveStringComparator());
        plugins.foreachLoadedPlugins((ProcessPlugins)new /* Unavailable Anonymous Inner Class!! */);
        LOG.info("Plugins initialization completed (" + pluginNames.size() + " plugins loaded): " + String.valueOf(pluginNames));
    }

    private String getPluginCaption(@NotNull PluginData plugin) {
        if (plugin == null) {
            PluginManagerImpl.$$$reportNull$$$0(23);
        }
        String version = plugin.getPluginInfo().getPluginVersion();
        return plugin.getPluginInfo().getPluginName() + (String)(version != null ? " (ver:" + version + ")" : "");
    }

    private void setDetectedPluginInfos(PluginsCollection plugins) {
        ArrayList list = new ArrayList();
        plugins.foreachPlugins((ProcessPlugins)new /* Unavailable Anonymous Inner Class!! */);
        Collections.sort(list, new /* Unavailable Anonymous Inner Class!! */);
        this.myPluginsStateProvider.setLoadedPlugins(list);
    }

    private void setNotLoadedPlugins(@NotNull PluginsCollection plugins) {
        if (plugins == null) {
            PluginManagerImpl.$$$reportNull$$$0(24);
        }
        List disabled = plugins.getNotLoadedPlugins();
        this.myPluginsStateProvider.setNotLoadedPlugins(disabled);
    }

    private static /* synthetic */ void $$$reportNull$$$0(int n) {
        Object[] objectArray;
        Object[] objectArray2;
        Object[] objectArray3 = new Object[switch (n) {
            default -> 3;
            case 16, 19, 20, 21 -> 2;
        }];
        switch (n) {
            default: {
                objectArray2 = objectArray3;
                objectArray3[0] = "standaloneClassloaderFactory";
                break;
            }
            case 1: {
                objectArray2 = objectArray3;
                objectArray3[0] = "sharedClassloaderFactory";
                break;
            }
            case 2: {
                objectArray2 = objectArray3;
                objectArray3[0] = "versionComparator";
                break;
            }
            case 3: 
            case 15: {
                objectArray2 = objectArray3;
                objectArray3[0] = "lookup";
                break;
            }
            case 4: {
                objectArray2 = objectArray3;
                objectArray3[0] = "pluginDisabledInfoProvider";
                break;
            }
            case 5: {
                objectArray2 = objectArray3;
                objectArray3[0] = "pluginsStateProvider";
                break;
            }
            case 6: {
                objectArray2 = objectArray3;
                objectArray3[0] = "reloadablePlugins";
                break;
            }
            case 7: 
            case 9: 
            case 10: 
            case 11: {
                objectArray2 = objectArray3;
                objectArray3[0] = "pluginInfo";
                break;
            }
            case 8: {
                objectArray2 = objectArray3;
                objectArray3[0] = "pluginsCollection";
                break;
            }
            case 12: {
                objectArray2 = objectArray3;
                objectArray3[0] = "springContext";
                break;
            }
            case 13: 
            case 14: {
                objectArray2 = objectArray3;
                objectArray3[0] = "pluginData";
                break;
            }
            case 16: 
            case 19: 
            case 20: 
            case 21: {
                objectArray2 = objectArray3;
                objectArray3[0] = "jetbrains/buildServer/plugins/PluginManagerImpl";
                break;
            }
            case 17: 
            case 18: {
                objectArray2 = objectArray3;
                objectArray3[0] = "listener";
                break;
            }
            case 22: 
            case 23: {
                objectArray2 = objectArray3;
                objectArray3[0] = "plugin";
                break;
            }
            case 24: {
                objectArray2 = objectArray3;
                objectArray3[0] = "plugins";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray2;
                objectArray2[1] = "jetbrains/buildServer/plugins/PluginManagerImpl";
                break;
            }
            case 16: {
                objectArray = objectArray2;
                objectArray2[1] = "getLookup";
                break;
            }
            case 19: {
                objectArray = objectArray2;
                objectArray2[1] = "lookupPlugins";
                break;
            }
            case 20: {
                objectArray = objectArray2;
                objectArray2[1] = "getDetectedPlugins";
                break;
            }
            case 21: {
                objectArray = objectArray2;
                objectArray2[1] = "getNotLoadedPlugins";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray;
                objectArray[2] = "<init>";
                break;
            }
            case 7: {
                objectArray = objectArray;
                objectArray[2] = "loadPlugin";
                break;
            }
            case 8: 
            case 9: {
                objectArray = objectArray;
                objectArray[2] = "findPluginData";
                break;
            }
            case 10: {
                objectArray = objectArray;
                objectArray[2] = "updateOtherVersionNotLoadedPlugins";
                break;
            }
            case 11: {
                objectArray = objectArray;
                objectArray[2] = "unloadPlugin";
                break;
            }
            case 12: {
                objectArray = objectArray;
                objectArray[2] = "fireBeforePluginUnloaded";
                break;
            }
            case 13: {
                objectArray = objectArray;
                objectArray[2] = "doUnloadPlugin";
                break;
            }
            case 14: {
                objectArray = objectArray;
                objectArray[2] = "disposeSpringContext";
                break;
            }
            case 15: {
                objectArray = objectArray;
                objectArray[2] = "setLookup";
                break;
            }
            case 16: 
            case 19: 
            case 20: 
            case 21: {
                break;
            }
            case 17: {
                objectArray = objectArray;
                objectArray[2] = "addPluginInfoLoadedListener";
                break;
            }
            case 18: {
                objectArray = objectArray;
                objectArray[2] = "addPluginClassesLoadedListener";
                break;
            }
            case 22: {
                objectArray = objectArray;
                objectArray[2] = "isBundled";
                break;
            }
            case 23: {
                objectArray = objectArray;
                objectArray[2] = "getPluginCaption";
                break;
            }
            case 24: {
                objectArray = objectArray;
                objectArray[2] = "setNotLoadedPlugins";
                break;
            }
        }
        String string = String.format(v0, objectArray);
        throw switch (n) {
            default -> new IllegalArgumentException(string);
            case 16, 19, 20, 21 -> new IllegalStateException(string);
        };
    }
}
