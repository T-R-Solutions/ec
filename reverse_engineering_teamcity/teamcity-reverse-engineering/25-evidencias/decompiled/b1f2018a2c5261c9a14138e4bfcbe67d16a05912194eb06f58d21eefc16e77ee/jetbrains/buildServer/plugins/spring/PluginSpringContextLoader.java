/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jetbrains.buildServer.plugins.spring.PluginSpringContextListener
 *  org.jetbrains.annotations.NotNull
 */
package jetbrains.buildServer.plugins.spring;

import jetbrains.buildServer.plugins.spring.PluginSpringContextListener;
import org.jetbrains.annotations.NotNull;

public interface PluginSpringContextLoader {
    public void addSpringListener(@NotNull PluginSpringContextListener var1);
}
