/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.intellij.openapi.diagnostic.Logger
 *  jetbrains.buildServer.agent.AgentLifeCycleListener
 *  jetbrains.buildServer.agent.BuildAgentConfiguration
 *  jetbrains.buildServer.agent.impl.BuildAgentImpl
 *  jetbrains.buildServer.agent.impl.serverCommunication.ServerCommandsExecutor
 *  jetbrains.buildServer.agent.impl.serverCommunication.ServerCommandsExecutor$NotSupportedCommandException
 *  jetbrains.buildServer.agent.impl.serverCommunication.ServerCommandsPoller$Command
 *  jetbrains.buildServer.agent.impl.serverCommunication.ServerCommandsPoller$ExitException
 *  jetbrains.buildServer.agent.impl.serverCommunication.ServerCommandsPoller$OnExit
 *  jetbrains.buildServer.agent.impl.serverCommunication.ServerCommandsPoller$OnExit$Reason
 *  jetbrains.buildServer.agentServer.polling.AgentServerResponse
 *  jetbrains.buildServer.agentServer.polling.Error
 *  jetbrains.buildServer.agentServer.polling.PollingAgentLogger
 *  jetbrains.buildServer.http.HttpApi
 *  jetbrains.buildServer.http.HttpApi$HeaderPair
 *  jetbrains.buildServer.http.HttpApi$Response
 *  jetbrains.buildServer.util.EventDispatcher
 *  jetbrains.buildServer.util.NamedDaemonThreadFactory
 *  jetbrains.buildServer.util.NamedThreadFactory
 *  jetbrains.buildServer.util.ThreadUtil
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package jetbrains.buildServer.agent.impl.serverCommunication;

import com.intellij.openapi.diagnostic.Logger;
import java.io.IOException;
import java.util.EventListener;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import jetbrains.buildServer.agent.AgentLifeCycleListener;
import jetbrains.buildServer.agent.BuildAgentConfiguration;
import jetbrains.buildServer.agent.impl.BuildAgentImpl;
import jetbrains.buildServer.agent.impl.serverCommunication.ServerCommandsExecutor;
import jetbrains.buildServer.agent.impl.serverCommunication.ServerCommandsPoller;
import jetbrains.buildServer.agentServer.polling.AgentServerResponse;
import jetbrains.buildServer.agentServer.polling.Error;
import jetbrains.buildServer.agentServer.polling.PollingAgentLogger;
import jetbrains.buildServer.http.HttpApi;
import jetbrains.buildServer.util.EventDispatcher;
import jetbrains.buildServer.util.NamedDaemonThreadFactory;
import jetbrains.buildServer.util.NamedThreadFactory;
import jetbrains.buildServer.util.ThreadUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ServerCommandsPoller {
    private static final String GET_COMMAND_PATH = "/app/agents/v1/commands/next";
    private static final String COMMAND_RESULT_PATH = "/app/agents/v1/commands/result";
    private static final String COMMAND_PROGRESS_PATH = "/app/agents/v1/commands/inProgress";
    private static final String COMMAND_EXECUTION_ERROR_PATH = "/app/agents/v1/commands/error";
    public static final String DELAY_AFTER_NO_COMMAND = "teamcity.agent.serverPolling.delayAfterNoCommand";
    public static final String DELAY_AFTER_COMMAND_RECEIVED = "teamcity.agent.serverPolling.delayAfterCommandReceived";
    public static final String DELAY_AFTER_FAILED_COMMAND_CONFIRMATION_REQUEST = "teamcity.agent.serverPolling.delayAfterFailedConfirmationRequest";
    public static final String LONG_COMMAND_PROGRESS_INTERVAL = "teamcity.agent.serverPolling.longCommandProgressInterval";
    private static final Logger LOGGER = PollingAgentLogger.getLogger();
    private final BuildAgentConfiguration myConfiguration;
    private final HttpApi myServerApi;
    private volatile BuildAgentImpl myAgent;
    private final ServerCommandsExecutor myServerCommandsExecutor;
    private final NamedThreadFactory myThreadFactory;
    private volatile Thread myThread;
    private volatile String mySessionId;
    private volatile OnExit myOnExit;
    private volatile boolean myExecutingCommand;
    private volatile boolean myStopped;
    private volatile String myConnectError;

    public ServerCommandsPoller(@NotNull BuildAgentConfiguration configuration, @NotNull ServerCommandsExecutor serverCommandsExecutor, @NotNull HttpApi serverApi, @NotNull EventDispatcher<AgentLifeCycleListener> agentDispatcher) {
        if (configuration == null) {
            ServerCommandsPoller.$$$reportNull$$$0(0);
        }
        if (serverCommandsExecutor == null) {
            ServerCommandsPoller.$$$reportNull$$$0(1);
        }
        if (serverApi == null) {
            ServerCommandsPoller.$$$reportNull$$$0(2);
        }
        if (agentDispatcher == null) {
            ServerCommandsPoller.$$$reportNull$$$0(3);
        }
        this.myStopped = false;
        this.myConnectError = null;
        this.myConfiguration = configuration;
        this.myServerApi = serverApi;
        this.myServerCommandsExecutor = serverCommandsExecutor;
        this.myThreadFactory = new NamedDaemonThreadFactory("Server Commands Poller");
        agentDispatcher.addListener((EventListener)new /* Unavailable Anonymous Inner Class!! */);
    }

    public synchronized void start(@NotNull String sessionId, BuildAgentImpl agent, OnExit onExit) {
        if (sessionId == null) {
            ServerCommandsPoller.$$$reportNull$$$0(4);
        }
        if (this.myThread != null && this.myThread.isAlive() && !this.myStopped && this.myAgent == agent) {
            this.mySessionId = sessionId;
            return;
        }
        this.stop(true);
        this.myAgent = agent;
        this.mySessionId = sessionId;
        this.myOnExit = onExit;
        this.myStopped = false;
        this.myThread = this.myThreadFactory.newThread((Runnable)new /* Unavailable Anonymous Inner Class!! */);
        this.myThread.start();
    }

    private synchronized void stop(boolean join) {
        if (this.myThread != null) {
            this.myStopped = true;
            this.myThread.interrupt();
            if (join) {
                try {
                    this.myThread.join();
                }
                catch (InterruptedException e) {
                    LOGGER.info("Stopping server commands poller was interrupted", (Throwable)e);
                }
            }
            this.myThread = null;
        }
    }

    public void run() {
        LOGGER.info("Start polling server for commands");
        long sleepMillis = this.getIntegerProperty(DELAY_AFTER_NO_COMMAND, 5000);
        while (!this.myStopped) {
            try (Command command = this.getNextCommand();){
                if (this.processOneCommand(command)) {
                    sleepMillis = this.getIntegerProperty(DELAY_AFTER_COMMAND_RECEIVED, 500);
                }
            }
            catch (ExitException e) {
                this.myOnExit.onExit(e.reason);
            }
            catch (Throwable e) {
                LOGGER.warnAndDebugDetails("Error while polling server for commands, continue polling", e);
            }
            if (this.myStopped) continue;
            try {
                Thread.sleep(sleepMillis);
            }
            catch (InterruptedException interruptedException) {}
        }
        LOGGER.info("Stop polling server for commands");
        this.myOnExit.onExit(OnExit.Reason.NORMAL_EXIT);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean processOneCommand(@Nullable Command command) {
        if (command == null) {
            return false;
        }
        LOGGER.info("New command is received from server " + String.valueOf(command));
        try {
            this.myExecutingCommand = true;
            try {
                Future resultFuture = this.myServerCommandsExecutor.executeAsync(command);
                while (true) {
                    try {
                        String result = (String)resultFuture.get(this.getIntegerProperty(LONG_COMMAND_PROGRESS_INTERVAL, 5000), TimeUnit.MILLISECONDS);
                        LOGGER.info("Posting result for command: " + String.valueOf(command) + " result: " + result);
                        this.postCommandResult(command, result);
                        LOGGER.info("Finished posting result for command: " + String.valueOf(command));
                    }
                    catch (TimeoutException e) {
                        LOGGER.info("Posting status in progress for command: " + String.valueOf(command));
                        this.postCommandInProgress(command);
                        LOGGER.info("Finished posting in progress for command: " + String.valueOf(command));
                        continue;
                    }
                    break;
                }
            }
            catch (InterruptedException e) {
                throw new ExitException(OnExit.Reason.NORMAL_EXIT);
            }
            catch (ExecutionException e) {
                LOGGER.warn("Posting status error for command: " + String.valueOf(command), (Throwable)e);
                if (e.getCause() instanceof ServerCommandsExecutor.NotSupportedCommandException) {
                    this.postCommandError(command, new Error("UnknownCommandError", "Agent doesn't have registered handler for the next command: " + command.type));
                } else {
                    this.postCommandError(command, new Error("UnexpectedError", e.getCause().toString()));
                }
                LOGGER.warn("Finished posting status error for command: " + String.valueOf(command));
            }
            catch (Throwable t) {
                LOGGER.warn("Command execution failed: " + String.valueOf(command), t);
                throw t;
            }
        }
        finally {
            this.myExecutingCommand = false;
        }
        return true;
    }

    boolean isExecutingCommand() {
        return this.myExecutingCommand;
    }

    private int getIntegerProperty(String propName, int defaultVal) {
        try {
            return Integer.parseInt((String)this.myConfiguration.getConfigurationParameters().get(propName));
        }
        catch (NumberFormatException e) {
            return defaultVal;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Nullable
    private Command getNextCommand() {
        String nextCommandUrl = this.myConfiguration.getServerUrl() + GET_COMMAND_PATH;
        LOGGER.debug("Asking server for the new command by GET " + nextCommandUrl);
        Command c = null;
        HttpApi.Response response = null;
        try {
            response = this.myServerApi.get(nextCommandUrl, new HttpApi.HeaderPair[]{new HttpApi.HeaderPair("TeamCity-AgentSessionId", (Object)this.mySessionId)});
            LOGGER.debug("Server answered with status " + response.getStatusCode() + " on the next command request GET " + nextCommandUrl + ", body: ");
            this.myConnectError = null;
            this.checkInterrupted();
            this.checkUnauthorized(response, "asking next command");
            if (response.getStatusCode() == 200) {
                this.myAgent.ping2();
                String commandName = response.getHeader("TeamCity-AgentCommandName");
                String commandIdHeader = response.getHeader("TeamCity-AgentCommandId");
                if (commandName == null || commandIdHeader == null) {
                    LOGGER.warn("Error while asking server for the next command by GET " + nextCommandUrl + ". Will continue polling. Details: missing TeamCity-AgentCommandName or TeamCity-AgentCommandId headers.");
                    Command command = null;
                    return command;
                }
                c = new Command(this, Integer.valueOf(Integer.parseInt(commandIdHeader)), commandName, response);
            } else if (response.getStatusCode() == 204) {
                this.myAgent.ping2();
            } else {
                LOGGER.warn("Error while asking server for the next command by GET " + nextCommandUrl + ". Will continue polling. Details: unexpected response status code " + response.getStatusCode() + (!LOGGER.isDebugEnabled() ? " (enable debug to see full response body)" : ""));
                LOGGER.debug(response.getBody());
            }
        }
        catch (IOException e) {
            if (!e.getMessage().equals(this.myConnectError)) {
                LOGGER.warnAndDebugDetails("Error while asking server for the next command by GET " + nextCommandUrl + ". Will continue polling", (Throwable)e);
                this.myConnectError = e.getMessage();
            } else {
                LOGGER.debug("Error while asking server for the next command by GET " + nextCommandUrl + ". Will continue polling", (Throwable)e);
            }
        }
        finally {
            if (c == null && response != null) {
                response.close();
            }
        }
        return c;
    }

    private void postCommandResult(Command command, String result) {
        String url = this.myConfiguration.getServerUrl() + COMMAND_RESULT_PATH;
        LOGGER.debug("Sending command '" + String.valueOf(command) + "' result to the server by POST " + url);
        this.postConfirmationUntilSuccessful(command, url, result, "application/xml");
    }

    private void postCommandInProgress(Command command) {
        String url = this.myConfiguration.getServerUrl() + COMMAND_PROGRESS_PATH;
        LOGGER.debug("Sending command '" + String.valueOf(command) + "' progress to the server by POST " + url);
        this.postConfirmationUntilSuccessful(command, url, "<progress/>", "application/xml");
    }

    private void postCommandError(Command command, Error error) {
        String url = this.myConfiguration.getServerUrl() + COMMAND_EXECUTION_ERROR_PATH;
        LOGGER.debug("Sending command " + String.valueOf(command) + " execution error to the server: " + String.valueOf(error) + " by POST " + url);
        this.postConfirmationUntilSuccessful(command, url, error.toXml(), "application/xml");
    }

    private void postConfirmationUntilSuccessful(Command command, String url, String data, String contentType) {
        while (true) {
            int sleepMillis = this.getIntegerProperty(DELAY_AFTER_FAILED_COMMAND_CONFIRMATION_REQUEST, 3000);
            LOGGER.debug("Posting command " + String.valueOf(command) + " confirmation POST " + url);
            try (HttpApi.Response response = this.myServerApi.post(url, data, contentType, "UTF-8", new HttpApi.HeaderPair[]{new HttpApi.HeaderPair("TeamCity-AgentSessionId", (Object)this.mySessionId), new HttpApi.HeaderPair("TeamCity-AgentCommandId", (Object)command.id)});){
                this.checkInterrupted();
                this.checkUnauthorized(response, "posting command " + String.valueOf(command) + " confirmation.");
                if (response.getStatusCode() == 200) {
                    AgentServerResponse parsedResponse = AgentServerResponse.deserialize((String)response.getBody());
                    if (parsedResponse != null && parsedResponse.getError() != null) {
                        LOGGER.warn("Command " + String.valueOf(command) + " confirmation was delivered to the server by POST " + url + ", but server returns error: " + String.valueOf(parsedResponse.getError()) + ". Will continue polling.");
                    }
                    this.myAgent.ping2();
                    return;
                }
                LOGGER.warn("Error while sending command " + String.valueOf(command) + " confirmation to the server by POST " + url + ". Will retry in " + sleepMillis + " millis. Details: unexpected response status code " + response.getStatusCode() + (!LOGGER.isDebugEnabled() ? " (enable debug to see full response body)" : ""));
                LOGGER.debug(response.getBody());
            }
            catch (IOException e) {
                LOGGER.warnAndDebugDetails("Error while sending command " + String.valueOf(command) + " confirmation to the server by POST " + url + ". Will retry in " + sleepMillis + "  millis", (Throwable)e);
            }
            ThreadUtil.sleep((long)sleepMillis);
        }
    }

    private void checkInterrupted() {
        if (this.myStopped) {
            throw new ExitException(OnExit.Reason.NORMAL_EXIT);
        }
    }

    private void checkUnauthorized(HttpApi.Response response, String operationName) {
        if (response.getStatusCode() == 401) {
            LOGGER.info("Agent has been unregistered from server. Current operation: " + operationName);
            throw new ExitException(OnExit.Reason.UNAUTHORIZED);
        }
    }

    private static /* synthetic */ void $$$reportNull$$$0(int n) {
        Object[] objectArray;
        Object[] objectArray2;
        Object[] objectArray3 = new Object[3];
        switch (n) {
            default: {
                objectArray2 = objectArray3;
                objectArray3[0] = "configuration";
                break;
            }
            case 1: {
                objectArray2 = objectArray3;
                objectArray3[0] = "serverCommandsExecutor";
                break;
            }
            case 2: {
                objectArray2 = objectArray3;
                objectArray3[0] = "serverApi";
                break;
            }
            case 3: {
                objectArray2 = objectArray3;
                objectArray3[0] = "agentDispatcher";
                break;
            }
            case 4: {
                objectArray2 = objectArray3;
                objectArray3[0] = "sessionId";
                break;
            }
        }
        objectArray2[1] = "jetbrains/buildServer/agent/impl/serverCommunication/ServerCommandsPoller";
        switch (n) {
            default: {
                objectArray = objectArray2;
                objectArray2[2] = "<init>";
                break;
            }
            case 4: {
                objectArray = objectArray2;
                objectArray2[2] = "start";
                break;
            }
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objectArray));
    }
}
