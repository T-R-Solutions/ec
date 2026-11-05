/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jetbrains.buildServer.plugins.PluginClassesLoadedListener
 *  org.jetbrains.annotations.NotNull
 */
package jetbrains.buildServer.plugins;

import jetbrains.buildServer.plugins.PluginClassesLoadedListener;
import org.jetbrains.annotations.NotNull;

public interface PluginClassesLoader {
    public void addPluginClassesLoadedListener(@NotNull PluginClassesLoadedListener var1);
}
