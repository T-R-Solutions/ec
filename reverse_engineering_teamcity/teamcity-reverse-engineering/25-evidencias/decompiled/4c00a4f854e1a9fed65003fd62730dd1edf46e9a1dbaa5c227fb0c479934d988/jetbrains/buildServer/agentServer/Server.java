/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jetbrains.buildServer.agentServer.AgentLogListener
 */
package jetbrains.buildServer.agentServer;

import jetbrains.buildServer.agentServer.AgentLogListener;

public interface Server
extends AgentLogListener {
    @Deprecated
    public static final String BUILD_SERVER = "buildServer";

    public boolean ping();

    public String updateAgentParameteres(String var1);

    @Deprecated
    public boolean unregister(int var1);

    public boolean unregister(int var1, String var2);
}
