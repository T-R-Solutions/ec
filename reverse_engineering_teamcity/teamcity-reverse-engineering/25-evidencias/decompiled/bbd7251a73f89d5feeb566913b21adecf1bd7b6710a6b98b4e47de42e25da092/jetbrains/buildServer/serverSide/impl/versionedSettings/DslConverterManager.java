/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jetbrains.buildServer.TeamCityExtension
 *  org.jetbrains.annotations.NotNull
 */
package jetbrains.buildServer.serverSide.impl.versionedSettings;

import jetbrains.buildServer.TeamCityExtension;
import org.jetbrains.annotations.NotNull;

public interface DslConverterManager
extends TeamCityExtension {
    public boolean hasConverter(int var1);

    @NotNull
    public String getConverterDescription(int var1);
}
