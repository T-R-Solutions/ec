/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.intellij.openapi.diagnostic.Logger
 *  jetbrains.buildServer.plugins.PluginClassesLoadedListener
 *  jetbrains.buildServer.plugins.PluginData
 *  jetbrains.buildServer.plugins.PluginManager
 *  jetbrains.buildServer.plugins.bean.PluginInfo
 *  jetbrains.buildServer.plugins.classLoaders.SpringScriptingClassloadingUtil
 *  jetbrains.buildServer.plugins.spring.PluginContextUpdater
 *  jetbrains.buildServer.plugins.spring.PluginSpringConfigsProvider
 *  jetbrains.buildServer.plugins.spring.PluginSpringContextDependenciesInjector
 *  jetbrains.buildServer.plugins.spring.PluginSpringContextListener
 *  jetbrains.buildServer.plugins.spring.PluginSpringContextLoader
 *  jetbrains.buildServer.plugins.spring.SpringPluginConfigExtracter
 *  jetbrains.buildServer.plugins.spring.SpringPluginInfo
 *  jetbrains.buildServer.plugins.spring.SpringPluginLoader$ConfigLocations
 *  jetbrains.buildServer.plugins.spring.SpringPluginLoader$PluginLoadError
 *  jetbrains.buildServer.plugins.spring.SpringPluginLoader$TeamCityPluginContext
 *  jetbrains.buildServer.plugins.spring.SpringPluginLoader$TeamCityPluginIntermediateContext
 *  jetbrains.buildServer.serverSide.TeamCityProperties
 *  jetbrains.buildServer.util.Disposable
 *  jetbrains.buildServer.util.EventDispatcher
 *  jetbrains.buildServer.util.NamedDaemonThreadFactory
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.ConfigurableApplicationContext
 *  org.springframework.util.ClassUtils
 */
package jetbrains.buildServer.plugins.spring;

import com.intellij.openapi.diagnostic.Logger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EventListener;
import java.util.Map;
import jetbrains.buildServer.plugins.PluginClassesLoadedListener;
import jetbrains.buildServer.plugins.PluginData;
import jetbrains.buildServer.plugins.PluginManager;
import jetbrains.buildServer.plugins.bean.PluginInfo;
import jetbrains.buildServer.plugins.classLoaders.SpringScriptingClassloadingUtil;
import jetbrains.buildServer.plugins.spring.PluginContextUpdater;
import jetbrains.buildServer.plugins.spring.PluginSpringConfigsProvider;
import jetbrains.buildServer.plugins.spring.PluginSpringContextDependenciesInjector;
import jetbrains.buildServer.plugins.spring.PluginSpringContextListener;
import jetbrains.buildServer.plugins.spring.PluginSpringContextLoader;
import jetbrains.buildServer.plugins.spring.SpringPluginConfigExtracter;
import jetbrains.buildServer.plugins.spring.SpringPluginInfo;
import jetbrains.buildServer.plugins.spring.SpringPluginLoader;
import jetbrains.buildServer.serverSide.TeamCityProperties;
import jetbrains.buildServer.util.Disposable;
import jetbrains.buildServer.util.EventDispatcher;
import jetbrains.buildServer.util.NamedDaemonThreadFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.ClassUtils;

public class SpringPluginLoader
implements PluginClassesLoadedListener,
PluginSpringContextLoader {
    private static final Logger LOG = Logger.getInstance((String)SpringPluginLoader.class.getName());
    @NotNull
    private final Collection<PluginSpringConfigsProvider> myConfigProviders;
    @NotNull
    private final Collection<PluginContextUpdater> myProxyBeansProviders;
    @NotNull
    private final PluginSpringContextDependenciesInjector myInjector;
    @Nullable
    private final ApplicationContext myRootContext;
    @NotNull
    private final EventDispatcher<PluginSpringContextListener> myEvent;

    public SpringPluginLoader(@Nullable ApplicationContext rootContext, @NotNull PluginManager pluginClassesLoader, @NotNull Collection<PluginSpringConfigsProvider> configProviders, @NotNull PluginSpringContextDependenciesInjector injector, @Nullable Collection<PluginSpringContextListener> listeners, @NotNull Collection<PluginContextUpdater> proxyBeansProviders) {
        if (pluginClassesLoader == null) {
            SpringPluginLoader.$$$reportNull$$$0(0);
        }
        if (configProviders == null) {
            SpringPluginLoader.$$$reportNull$$$0(1);
        }
        if (injector == null) {
            SpringPluginLoader.$$$reportNull$$$0(2);
        }
        if (proxyBeansProviders == null) {
            SpringPluginLoader.$$$reportNull$$$0(3);
        }
        this.myEvent = EventDispatcher.create(PluginSpringContextListener.class);
        this.myRootContext = rootContext;
        this.myConfigProviders = configProviders;
        this.myInjector = injector;
        this.myProxyBeansProviders = new ArrayList<PluginContextUpdater>(proxyBeansProviders);
        pluginClassesLoader.addPluginClassesLoadedListener((PluginClassesLoadedListener)this);
        if (listeners != null) {
            for (PluginSpringContextListener listener : listeners) {
                this.addSpringListener(listener);
            }
        }
    }

    public SpringPluginLoader(@Nullable ApplicationContext rootContext, @NotNull PluginManager pluginClassesLoader, @NotNull Collection<PluginSpringConfigsProvider> configProviders, @NotNull PluginSpringContextDependenciesInjector injector, @Nullable Collection<PluginSpringContextListener> listeners) {
        if (pluginClassesLoader == null) {
            SpringPluginLoader.$$$reportNull$$$0(4);
        }
        if (configProviders == null) {
            SpringPluginLoader.$$$reportNull$$$0(5);
        }
        if (injector == null) {
            SpringPluginLoader.$$$reportNull$$$0(6);
        }
        this(rootContext, pluginClassesLoader, configProviders, injector, listeners, Collections.emptyList());
    }

    public void addSpringListener(@NotNull PluginSpringContextListener listener) {
        if (listener == null) {
            SpringPluginLoader.$$$reportNull$$$0(7);
        }
        this.myEvent.addListener((EventListener)listener);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void pluginClassesLoaded(@NotNull PluginData _plugin) {
        if (_plugin == null) {
            SpringPluginLoader.$$$reportNull$$$0(8);
        }
        Disposable disp = NamedDaemonThreadFactory.patchThreadName((String)("Initializing Spring context for plugin: " + _plugin.getPluginName()));
        long startLoadingContext = System.currentTimeMillis();
        try {
            PluginInfo info = _plugin.getPluginInfo();
            SpringPluginInfo plugin = info.getSpringPluginInfo();
            if (plugin == null) {
                return;
            }
            SpringPluginConfigExtracter extracter = this.createExtracter();
            Map pluginConfig = extracter.extractSpringConfigFiles(plugin);
            ArrayList configs = new ArrayList();
            ArrayList singletons = new ArrayList();
            for (PluginSpringConfigsProvider configProvider : this.myConfigProviders) {
                configs.addAll(configProvider.getPluginResources(info));
                singletons.addAll(configProvider.getPluginSingletons(info));
            }
            ConfigLocations locations = new ConfigLocations(configs, new ArrayList(plugin.getExplicitSpringConfigs()), pluginConfig);
            Object intermediate = !_plugin.getParents().isEmpty() ? new TeamCityPluginIntermediateContext(this.myRootContext, _plugin, this.myInjector) : null;
            TeamCityPluginContext ctx = new TeamCityPluginContext((ApplicationContext)(intermediate != null ? intermediate : this.myRootContext), _plugin, singletons, locations, this.myProxyBeansProviders);
            ArrayList springConfigs = new ArrayList();
            springConfigs.addAll(locations.getPerPluginConfigLocations());
            springConfigs.addAll(locations.getGlobalConfigLocations());
            LOG.debug("Setting up plugin Application context on: " + String.valueOf(springConfigs));
            ((PluginSpringContextListener)this.myEvent.getMulticaster()).contextCreated((ApplicationContext)ctx, info);
            SpringScriptingClassloadingUtil.extendClassLoaderForScriptingLanguagesInSpring((PluginData)_plugin);
            ClassLoader threadContextClassLoader = Thread.currentThread().getContextClassLoader();
            try {
                if (intermediate != null) {
                    intermediate.refresh();
                }
                _plugin.setSpringContext((ConfigurableApplicationContext)ctx);
                if (TeamCityProperties.getBoolean((String)"teamcity.internal.plugins.springWeb.security.enable")) {
                    ClassUtils.overrideThreadContextClassLoader((ClassLoader)_plugin.getClassloader());
                }
                ctx.refresh();
                ((PluginSpringContextListener)this.myEvent.getMulticaster()).contextLoaded((ApplicationContext)ctx, info);
            }
            catch (PluginLoadError t) {
                _plugin.addLoadError((Throwable)t);
                LOG.error("Error loading plugin '" + info.getPluginName() + "': Failed to initialize spring context: " + t.getMessage());
                ((PluginSpringContextListener)this.myEvent.getMulticaster()).contextFailedToLoad((ApplicationContext)ctx, info, (Throwable)t);
            }
            catch (Throwable t) {
                _plugin.addLoadError(t);
                LOG.error("Error loading plugin '" + info.getPluginName() + "': Failed to initialize spring context: " + t.getMessage(), t);
                ((PluginSpringContextListener)this.myEvent.getMulticaster()).contextFailedToLoad((ApplicationContext)ctx, info, t);
                if (TeamCityProperties.getBoolean((String)"teamcity.internal.plugins.springWeb.security.enable")) {
                    ClassUtils.overrideThreadContextClassLoader((ClassLoader)threadContextClassLoader);
                }
            }
            finally {
                if (TeamCityProperties.getBoolean((String)"teamcity.internal.plugins.springWeb.security.enable")) {
                    ClassUtils.overrideThreadContextClassLoader((ClassLoader)threadContextClassLoader);
                }
            }
        }
        finally {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Loading plugin '" + _plugin.getPluginInfo().getPluginName() + "' takes " + (System.currentTimeMillis() - startLoadingContext) + "ms.");
            }
            disp.dispose();
        }
    }

    protected SpringPluginConfigExtracter createExtracter() {
        return new SpringPluginConfigExtracter();
    }

    private static /* synthetic */ void $$$reportNull$$$0(int n) {
        Object[] objectArray;
        Object[] objectArray2;
        Object[] objectArray3 = new Object[3];
        switch (n) {
            default: {
                objectArray2 = objectArray3;
                objectArray3[0] = "pluginClassesLoader";
                break;
            }
            case 1: 
            case 5: {
                objectArray2 = objectArray3;
                objectArray3[0] = "configProviders";
                break;
            }
            case 2: 
            case 6: {
                objectArray2 = objectArray3;
                objectArray3[0] = "injector";
                break;
            }
            case 3: {
                objectArray2 = objectArray3;
                objectArray3[0] = "proxyBeansProviders";
                break;
            }
            case 7: {
                objectArray2 = objectArray3;
                objectArray3[0] = "listener";
                break;
            }
            case 8: {
                objectArray2 = objectArray3;
                objectArray3[0] = "_plugin";
                break;
            }
        }
        objectArray2[1] = "jetbrains/buildServer/plugins/spring/SpringPluginLoader";
        switch (n) {
            default: {
                objectArray = objectArray2;
                objectArray2[2] = "<init>";
                break;
            }
            case 7: {
                objectArray = objectArray2;
                objectArray2[2] = "addSpringListener";
                break;
            }
            case 8: {
                objectArray = objectArray2;
                objectArray2[2] = "pluginClassesLoaded";
                break;
            }
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objectArray));
    }
}
