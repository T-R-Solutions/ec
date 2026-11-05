/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jetbrains.buildServer.serverSide.agentTypes.AgentType
 *  jetbrains.buildServer.serverSide.agentTypes.AgentTypeKey
 *  jetbrains.buildServer.serverSide.agentTypes.AgentTypePolicy
 *  jetbrains.buildServer.serverSide.agentTypes.CacheClearPolicy
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package jetbrains.buildServer.serverSide.agentTypes;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import jetbrains.buildServer.serverSide.agentTypes.AgentType;
import jetbrains.buildServer.serverSide.agentTypes.AgentTypeKey;
import jetbrains.buildServer.serverSide.agentTypes.AgentTypePolicy;
import jetbrains.buildServer.serverSide.agentTypes.CacheClearPolicy;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface AgentTypeStorage {
    public int getOrCreateAgentTypeId(@NotNull AgentTypeKey var1);

    public int getOrCreateAgentTypeId(@NotNull AgentTypeKey var1, int var2);

    @Nullable
    public AgentType findAgentTypeById(int var1);

    @NotNull
    public Map<Integer, AgentType> findAgentTypesByIds(@NotNull Collection<Integer> var1);

    @Nullable
    public AgentType findAgentTypeByKey(@NotNull AgentTypeKey var1);

    @NotNull
    public Set<Integer> getAgentTypeIds();

    public Map<Integer, AgentTypeKey> getAgentTypePairs();

    @NotNull
    public Collection<Integer> getAgentTypeIdsByPool(int var1);

    @NotNull
    public AgentTypePolicy getPolicy(int var1);

    public void setPolicyKind(int var1, int var2);

    public void includeBuildTypesToAllowed(int var1, @NotNull Set<String> var2);

    public void excludeBuildTypesFromAllowed(int var1, @NotNull Set<String> var2);

    public boolean isAllowedToRun(int var1, @NotNull String var2, @NotNull String var3);

    public void removeBuildTypeFromAllLists(@NotNull String var1);

    public void copyAgentTypeRestrictions(@NotNull String var1, @NotNull String var2);

    public void invalidateCacheForAllAgentTypes();

    public void invalidateCacheForAgentTypesFromPools(@NotNull Set<Integer> var1, @NotNull CacheClearPolicy var2);

    public void invalidateCacheForOneAgentType(int var1, @NotNull CacheClearPolicy var2);

    public void updateAgentTypesFromDB(@NotNull Collection<Integer> var1);

    public void deleteAgentType(int var1) throws IllegalStateException;

    public int getAgentTypeInvalidationCounter(int var1);
}
