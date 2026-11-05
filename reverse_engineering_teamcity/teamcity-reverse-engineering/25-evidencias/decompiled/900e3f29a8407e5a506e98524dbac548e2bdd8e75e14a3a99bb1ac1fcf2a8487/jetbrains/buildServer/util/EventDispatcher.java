/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.intellij.openapi.diagnostic.Logger
 *  jetbrains.buildServer.serverSide.TeamCityProperties
 *  jetbrains.buildServer.util.EventDispatcher$2
 *  jetbrains.buildServer.util.EventDispatcher$ErrorHandler
 *  jetbrains.buildServer.util.EventDispatcher$WrapException
 *  jetbrains.buildServer.util.EventDispatcherHandlers
 *  jetbrains.buildServer.util.ExceptionUtil
 *  jetbrains.buildServer.util.TimePrinter
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package jetbrains.buildServer.util;

import com.intellij.openapi.diagnostic.Logger;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import jetbrains.buildServer.serverSide.TeamCityProperties;
import jetbrains.buildServer.util.EventDispatcher;
import jetbrains.buildServer.util.EventDispatcherHandlers;
import jetbrains.buildServer.util.ExceptionUtil;
import jetbrains.buildServer.util.TimePrinter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EventDispatcher<T extends EventListener> {
    private static final Logger LOG = Logger.getInstance((String)EventDispatcher.class.getName());
    private static final long THRESHOLD = 10000L;
    private final T myMulticaster;
    @NotNull
    private final EventDispatcherHandlers<T> myHandlers;
    @NotNull
    private final Class<T> myListenerClass;
    @Nullable
    private volatile Map<Method, List<T>> myMethodsToCall;
    @NotNull
    private final List<EventDispatcher<?>> myParentDispatchers;
    @Nullable
    private ErrorHandler myErrorHandler;
    public static final ErrorHandler DEFAULT_ERROR_HANDLER = new /* Unavailable Anonymous Inner Class!! */;

    public static <T extends EventListener> EventDispatcher<T> create(@NotNull Class<T> listenerClass) {
        if (listenerClass == null) {
            EventDispatcher.$$$reportNull$$$0(0);
        }
        return new EventDispatcher<T>(listenerClass);
    }

    protected EventDispatcher(@NotNull Class<T> listenerClass) {
        if (listenerClass == null) {
            EventDispatcher.$$$reportNull$$$0(1);
        }
        this.myMethodsToCall = null;
        this.myParentDispatchers = new CopyOnWriteArrayList();
        this.myErrorHandler = DEFAULT_ERROR_HANDLER;
        this.myHandlers = new EventDispatcherHandlers(listenerClass);
        this.myListenerClass = listenerClass;
        2 handler = new /* Unavailable Anonymous Inner Class!! */;
        this.myMulticaster = (EventListener)listenerClass.cast(Proxy.newProxyInstance(listenerClass.getClassLoader(), new Class[]{listenerClass}, (InvocationHandler)handler));
    }

    public T getMulticaster() {
        return this.myMulticaster;
    }

    @Nullable
    public ErrorHandler getErrorHandler() {
        return this.myErrorHandler;
    }

    public void setErrorHandler(@Nullable ErrorHandler errorHandler) {
        this.myErrorHandler = errorHandler;
    }

    public void clearErrorHandler() {
        this.myErrorHandler = null;
    }

    protected Object dispatch(Object proxy, Method method, Object[] args) {
        if (method.getDeclaringClass().getName().equals("java.lang.Object")) {
            String methodName = method.getName();
            if (methodName.equals("toString")) {
                return "Multicaster";
            }
            if (methodName.equals("hashCode")) {
                return System.identityHashCode(proxy);
            }
            if (methodName.equals("equals")) {
                return proxy == args[0] ? Boolean.TRUE : Boolean.FALSE;
            }
            LOG.error("Incorrect Object's method invoked for proxy:" + methodName);
            return null;
        }
        long startTimeTotal = System.nanoTime();
        List<T> listeners = this.getCachedListeners().get(method);
        if (listeners == null) {
            return null;
        }
        String methodDetails = method.getDeclaringClass().getSimpleName() + "." + method.getName();
        this.invokeListeners(method, args, listeners, methodDetails);
        long elapsedMillis = TimeUnit.MILLISECONDS.convert(System.nanoTime() - startTimeTotal, TimeUnit.NANOSECONDS);
        int listenersCount = listeners.size();
        if (listenersCount > 1 && elapsedMillis >= TeamCityProperties.getLong((String)"teamcity.event.total.logProcessingThreshold.ms", (long)30000L)) {
            LOG.info("Event listeners (" + listenersCount + ") took " + TimePrinter.formatMillis((long)elapsedMillis) + " to complete for method " + methodDetails);
        }
        return null;
    }

    private void invokeListeners(Method method, Object[] args, List<T> listeners, String methodDetails) {
        for (EventListener listener : listeners) {
            try {
                long startTime = System.nanoTime();
                method.invoke((Object)listener, args);
                long elapsedMillis = TimeUnit.MILLISECONDS.convert(System.nanoTime() - startTime, TimeUnit.NANOSECONDS);
                if (elapsedMillis < this.getLogThresholdMs(listener.getClass())) continue;
                LOG.info("Event listener took " + TimePrinter.formatMillis((long)elapsedMillis) + " to complete. Method " + methodDetails + " in listener " + listener.getClass().getName());
            }
            catch (AbstractMethodError e) {
                LOG.debug("Method " + methodDetails + " is not implemented in " + listener.getClass().getName() + ". Consider updating the plugin for newer TeamCity API");
            }
            catch (Throwable e) {
                Throwable error;
                Throwable throwable = error = e.getCause() != null ? e.getCause() : e;
                if (error instanceof AbstractMethodError) {
                    LOG.debug("Method " + methodDetails + " is not implemented in " + listener.getClass().getName() + ". Consider updating the plugin for newer TeamCity API");
                    continue;
                }
                ErrorHandler eh = this.myErrorHandler;
                if (eh == null) {
                    ExceptionUtil.rethrowAsRuntimeException((Throwable)error);
                }
                eh.handle((Throwable)new WrapException("Error calling method " + methodDetails + " for listener " + listener.getClass().getName(), error));
            }
        }
    }

    private long getLogThresholdMs(@NotNull Class clazz) {
        String ignorePrefix;
        if (clazz == null) {
            EventDispatcher.$$$reportNull$$$0(2);
        }
        if ((ignorePrefix = TeamCityProperties.getPropertyOrNull((String)"teamcity.event.listener.logProcessingThreshold.ignorePrefix")) != null && clazz.getName().startsWith(ignorePrefix)) {
            return Long.MAX_VALUE;
        }
        return TeamCityProperties.getLong((String)"teamcity.event.listener.logProcessingThreshold.ms", (long)10000L);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @NotNull
    private Map<Method, List<T>> getCachedListeners() {
        Map<Method, List<T>> methodsToCall = this.myMethodsToCall;
        if (methodsToCall != null) {
            Map<Method, List<T>> map = methodsToCall;
            if (map != null) return map;
            EventDispatcher.$$$reportNull$$$0(3);
            return map;
        }
        EventDispatcher eventDispatcher = this;
        // MONITORENTER : eventDispatcher
        methodsToCall = this.myMethodsToCall;
        if (methodsToCall != null) {
            Map<Method, List<T>> map = methodsToCall;
            // MONITOREXIT : eventDispatcher
            if (map != null) return map;
            EventDispatcher.$$$reportNull$$$0(4);
            return map;
        }
        this.myMethodsToCall = this.myHandlers.serialize();
        // MONITOREXIT : eventDispatcher
        if (this.myMethodsToCall != null) return this.myMethodsToCall;
        EventDispatcher.$$$reportNull$$$0(5);
        return this.myMethodsToCall;
    }

    public synchronized void addListener(@NotNull T listener) {
        if (listener == null) {
            EventDispatcher.$$$reportNull$$$0(6);
        }
        this.myHandlers.addListener(listener, false);
        this.resetListenersCache();
        for (EventDispatcher<?> parentDispatcher : this.myParentDispatchers) {
            parentDispatcher.addListenerPrivate((EventListener)listener);
        }
    }

    private synchronized void addListenerPrivate(EventListener listener) {
        this.myHandlers.addListener(listener, true);
        this.resetListenersCache();
        for (EventDispatcher<?> parentDispatcher : this.myParentDispatchers) {
            parentDispatcher.addListenerPrivate(listener);
        }
    }

    private void resetListenersCache() {
        this.myMethodsToCall = null;
    }

    public synchronized void removeListener(@NotNull T listener) {
        if (listener == null) {
            EventDispatcher.$$$reportNull$$$0(7);
        }
        this.myHandlers.removeListener(listener);
        this.resetListenersCache();
        for (EventDispatcher<?> parentDispatcher : this.myParentDispatchers) {
            parentDispatcher.removeListenerPrivate((EventListener)listener);
        }
    }

    private synchronized void removeListenerPrivate(EventListener listener) {
        this.myHandlers.removeListener(listener);
        this.resetListenersCache();
        for (EventDispatcher<?> parentDispatcher : this.myParentDispatchers) {
            parentDispatcher.removeListenerPrivate(listener);
        }
    }

    @NotNull
    public synchronized List<T> getListeners() {
        List<EventListener> listenersPrivate = this.getAllListeners();
        ArrayList<EventListener> res = new ArrayList<EventListener>(listenersPrivate.size());
        for (EventListener t : listenersPrivate) {
            if (!this.myListenerClass.isAssignableFrom(t.getClass())) continue;
            res.add(t);
        }
        ArrayList<EventListener> arrayList = res;
        if (arrayList == null) {
            EventDispatcher.$$$reportNull$$$0(8);
        }
        return arrayList;
    }

    @NotNull
    public synchronized List<EventListener> getAllListeners() {
        List list = this.myHandlers.getListeners();
        if (list == null) {
            EventDispatcher.$$$reportNull$$$0(9);
        }
        return list;
    }

    public synchronized boolean hasListeners() {
        return this.myHandlers.hasListeners();
    }

    public synchronized void dispose() {
        for (EventDispatcher<?> parentDispatcher : this.myParentDispatchers) {
            for (EventListener listener : this.getAllListeners()) {
                parentDispatcher.removeListenerPrivate(listener);
            }
        }
        this.myHandlers.clear();
        this.resetListenersCache();
    }

    public void attachParentDispatcher(@NotNull EventDispatcher<?> parentDispatcher) {
        if (parentDispatcher == null) {
            EventDispatcher.$$$reportNull$$$0(10);
        }
        this.myParentDispatchers.add(parentDispatcher);
    }

    private static /* synthetic */ void $$$reportNull$$$0(int n) {
        Object[] objectArray;
        Object[] objectArray2;
        Object[] objectArray3 = new Object[switch (n) {
            default -> 3;
            case 3, 4, 5, 8, 9 -> 2;
        }];
        switch (n) {
            default: {
                objectArray2 = objectArray3;
                objectArray3[0] = "listenerClass";
                break;
            }
            case 2: {
                objectArray2 = objectArray3;
                objectArray3[0] = "clazz";
                break;
            }
            case 3: 
            case 4: 
            case 5: 
            case 8: 
            case 9: {
                objectArray2 = objectArray3;
                objectArray3[0] = "jetbrains/buildServer/util/EventDispatcher";
                break;
            }
            case 6: 
            case 7: {
                objectArray2 = objectArray3;
                objectArray3[0] = "listener";
                break;
            }
            case 10: {
                objectArray2 = objectArray3;
                objectArray3[0] = "parentDispatcher";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray2;
                objectArray2[1] = "jetbrains/buildServer/util/EventDispatcher";
                break;
            }
            case 3: 
            case 4: 
            case 5: {
                objectArray = objectArray2;
                objectArray2[1] = "getCachedListeners";
                break;
            }
            case 8: {
                objectArray = objectArray2;
                objectArray2[1] = "getListeners";
                break;
            }
            case 9: {
                objectArray = objectArray2;
                objectArray2[1] = "getAllListeners";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray;
                objectArray[2] = "create";
                break;
            }
            case 1: {
                objectArray = objectArray;
                objectArray[2] = "<init>";
                break;
            }
            case 2: {
                objectArray = objectArray;
                objectArray[2] = "getLogThresholdMs";
                break;
            }
            case 3: 
            case 4: 
            case 5: 
            case 8: 
            case 9: {
                break;
            }
            case 6: {
                objectArray = objectArray;
                objectArray[2] = "addListener";
                break;
            }
            case 7: {
                objectArray = objectArray;
                objectArray[2] = "removeListener";
                break;
            }
            case 10: {
                objectArray = objectArray;
                objectArray[2] = "attachParentDispatcher";
                break;
            }
        }
        String string = String.format(v0, objectArray);
        throw switch (n) {
            default -> new IllegalArgumentException(string);
            case 3, 4, 5, 8, 9 -> new IllegalStateException(string);
        };
    }
}
