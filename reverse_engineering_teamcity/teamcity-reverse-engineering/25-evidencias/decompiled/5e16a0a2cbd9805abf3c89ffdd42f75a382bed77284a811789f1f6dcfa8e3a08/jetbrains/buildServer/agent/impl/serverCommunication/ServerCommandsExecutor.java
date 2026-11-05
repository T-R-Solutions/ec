/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jetbrains.buildServer.AlreadyRunningBuildException
 *  jetbrains.buildServer.ShutdownInProgressException
 *  jetbrains.buildServer.agent.AgentLifeCycleListener
 *  jetbrains.buildServer.agent.BuildAgent
 *  jetbrains.buildServer.agent.impl.serverCommunication.PollingBasedServerCommandsHandlersRegistry
 *  jetbrains.buildServer.agent.impl.serverCommunication.ServerCommandsExecutor$NotSupportedCommandException
 *  jetbrains.buildServer.agent.impl.serverCommunication.ServerCommandsPoller$Command
 *  jetbrains.buildServer.agentServer.AgentBuild
 *  jetbrains.buildServer.agentServer.polling.Error
 *  jetbrains.buildServer.agentServer.polling.PollingAgentLogger
 *  jetbrains.buildServer.agentServer.polling.PollingProtocolCommon
 *  jetbrains.buildServer.agentServer.polling.RunBuildCommandResult
 *  jetbrains.buildServer.messages.XStreamHolder
 *  jetbrains.buildServer.util.Action
 *  jetbrains.buildServer.util.EventDispatcher
 *  jetbrains.buildServer.util.NamedDaemonThreadFactory
 *  jetbrains.buildServer.util.StringUtil
 *  jetbrains.buildServer.xstream.XStreamWrapper
 *  org.apache.commons.beanutils.MethodUtils
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package jetbrains.buildServer.agent.impl.serverCommunication;

import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.util.EventListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import jetbrains.buildServer.AlreadyRunningBuildException;
import jetbrains.buildServer.ShutdownInProgressException;
import jetbrains.buildServer.agent.AgentLifeCycleListener;
import jetbrains.buildServer.agent.BuildAgent;
import jetbrains.buildServer.agent.impl.serverCommunication.PollingBasedServerCommandsHandlersRegistry;
import jetbrains.buildServer.agent.impl.serverCommunication.ServerCommandsExecutor;
import jetbrains.buildServer.agent.impl.serverCommunication.ServerCommandsPoller;
import jetbrains.buildServer.agentServer.AgentBuild;
import jetbrains.buildServer.agentServer.polling.Error;
import jetbrains.buildServer.agentServer.polling.PollingAgentLogger;
import jetbrains.buildServer.agentServer.polling.PollingProtocolCommon;
import jetbrains.buildServer.agentServer.polling.RunBuildCommandResult;
import jetbrains.buildServer.messages.XStreamHolder;
import jetbrains.buildServer.util.Action;
import jetbrains.buildServer.util.EventDispatcher;
import jetbrains.buildServer.util.NamedDaemonThreadFactory;
import jetbrains.buildServer.util.StringUtil;
import jetbrains.buildServer.xstream.XStreamWrapper;
import org.apache.commons.beanutils.MethodUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ServerCommandsExecutor {
    private final XStreamHolder myXStreamHolder;
    private final PollingBasedServerCommandsHandlersRegistry myServerCommandsRegistry;
    private final ExecutorService myExecutor;
    private volatile BuildAgent myAgent;
    private volatile Action<ServerCommandsPoller.Command> myInterceptor;

    public ServerCommandsExecutor(@NotNull EventDispatcher<AgentLifeCycleListener> agentDispatcher, PollingBasedServerCommandsHandlersRegistry serverCommandsRegistry) {
        if (agentDispatcher == null) {
            ServerCommandsExecutor.$$$reportNull$$$0(0);
        }
        this.myXStreamHolder = new XStreamHolder();
        this.myExecutor = Executors.newSingleThreadExecutor((ThreadFactory)new NamedDaemonThreadFactory("ServerCommandsExecutor"));
        this.myServerCommandsRegistry = serverCommandsRegistry;
        agentDispatcher.addListener((EventListener)new /* Unavailable Anonymous Inner Class!! */);
    }

    public Future<String> executeAsync(ServerCommandsPoller.Command command) {
        return this.myExecutor.submit(new /* Unavailable Anonymous Inner Class!! */);
    }

    @NotNull
    private String runBuild(Reader serializedBuild) {
        String string;
        try {
            string = new RunBuildCommandResult(this.myAgent.runBuild(AgentBuild.deserialize((Reader)serializedBuild))).serialize();
        }
        catch (AlreadyRunningBuildException e) {
            String string2 = new RunBuildCommandResult(new Error(AlreadyRunningBuildException.class.getSimpleName(), e.getMessage())).serialize();
            if (string2 == null) {
                ServerCommandsExecutor.$$$reportNull$$$0(2);
            }
            return string2;
        }
        catch (ShutdownInProgressException e) {
            String string3 = new RunBuildCommandResult(new Error(ShutdownInProgressException.class.getSimpleName(), e.getMessage())).serialize();
            if (string3 == null) {
                ServerCommandsExecutor.$$$reportNull$$$0(3);
            }
            return string3;
        }
        if (string == null) {
            ServerCommandsExecutor.$$$reportNull$$$0(1);
        }
        return string;
    }

    @NotNull
    private String stopBuild(String buildId) {
        if (StringUtil.isEmptyOrSpaces((String)buildId)) {
            String string = Boolean.toString(this.myAgent.stopBuild());
            if (string == null) {
                ServerCommandsExecutor.$$$reportNull$$$0(4);
            }
            return string;
        }
        String currentlyRunningBuild = this.myAgent.getBuildId();
        if (currentlyRunningBuild != null && !buildId.equals(currentlyRunningBuild)) {
            PollingAgentLogger.getLogger().warn("Attempt to stop build with id '" + buildId + "' while agent is running build '" + currentlyRunningBuild + "'");
            return "false";
        }
        String string = Boolean.toString(this.myAgent.stopBuild());
        if (string == null) {
            ServerCommandsExecutor.$$$reportNull$$$0(5);
        }
        return string;
    }

    @NotNull
    private String upgrade() {
        String string = "" + this.myAgent.upgrade(null);
        if (string == null) {
            ServerCommandsExecutor.$$$reportNull$$$0(6);
        }
        return string;
    }

    @NotNull
    protected String testLocal(String filePath) {
        String code = PollingProtocolCommon.readLocalCheckFile((String)filePath);
        return code != null ? code : "";
    }

    @Nullable
    private String doCustomCommand(ServerCommandsPoller.Command command) throws Exception {
        if (command.getType().lastIndexOf(".") == -1) {
            return null;
        }
        String handlerName = command.getType().substring(0, command.getType().lastIndexOf("."));
        String methodName = command.getType().substring(command.getType().lastIndexOf(".") + 1);
        Object handler = this.myServerCommandsRegistry.getHandler(handlerName);
        Object[] args = (Object[])XStreamWrapper.deserializeObject((Reader)command.getBodyReader(), (XStreamHolder)this.myXStreamHolder);
        if (args == null || handler == null) {
            return null;
        }
        try {
            Object result = MethodUtils.invokeMethod((Object)handler, (String)methodName, (Object[])args);
            return XStreamWrapper.serializeObject((Object)result, (XStreamHolder)this.myXStreamHolder);
        }
        catch (NoSuchMethodException e) {
            PollingAgentLogger.getLogger().warnAndDebugDetails("Unexpected command was received " + String.valueOf(command), (Throwable)e);
            throw new NotSupportedCommandException();
        }
        catch (InvocationTargetException e) {
            throw new Exception(e.getCause());
        }
        catch (RuntimeException e) {
            if (!"java.lang.reflect.InaccessibleObjectException".equals(e.getClass().getName())) {
                throw e;
            }
            PollingAgentLogger.getLogger().warnAndDebugDetails("Unexpected command was received " + String.valueOf(command), (Throwable)e);
            throw new NotSupportedCommandException();
        }
    }

    public void setInterceptor(Action<ServerCommandsPoller.Command> beforeExecute) {
        this.myInterceptor = beforeExecute;
    }

    private static /* synthetic */ void $$$reportNull$$$0(int n) {
        Object[] objectArray;
        Object[] objectArray2;
        Object[] objectArray3 = new Object[switch (n) {
            default -> 3;
            case 1, 2, 3, 4, 5, 6 -> 2;
        }];
        switch (n) {
            default: {
                objectArray2 = objectArray3;
                objectArray3[0] = "agentDispatcher";
                break;
            }
            case 1: 
            case 2: 
            case 3: 
            case 4: 
            case 5: 
            case 6: {
                objectArray2 = objectArray3;
                objectArray3[0] = "jetbrains/buildServer/agent/impl/serverCommunication/ServerCommandsExecutor";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray2;
                objectArray2[1] = "jetbrains/buildServer/agent/impl/serverCommunication/ServerCommandsExecutor";
                break;
            }
            case 1: 
            case 2: 
            case 3: {
                objectArray = objectArray2;
                objectArray2[1] = "runBuild";
                break;
            }
            case 4: 
            case 5: {
                objectArray = objectArray2;
                objectArray2[1] = "stopBuild";
                break;
            }
            case 6: {
                objectArray = objectArray2;
                objectArray2[1] = "upgrade";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray;
                objectArray[2] = "<init>";
                break;
            }
            case 1: 
            case 2: 
            case 3: 
            case 4: 
            case 5: 
            case 6: {
                break;
            }
        }
        String string = String.format(v0, objectArray);
        throw switch (n) {
            default -> new IllegalArgumentException(string);
            case 1, 2, 3, 4, 5, 6 -> new IllegalStateException(string);
        };
    }
}
