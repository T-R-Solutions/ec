/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.intellij.openapi.util.Pair
 *  jetbrains.buildServer.AlreadyExistsException
 *  jetbrains.buildServer.InvalidRunningBuildException
 *  jetbrains.buildServer.agent.BuildAgentConfiguration
 *  jetbrains.buildServer.agent.impl.BuildAgentImpl
 *  jetbrains.buildServer.agent.impl.RegistrationLogger
 *  jetbrains.buildServer.agent.impl.serverCommunication.CommunicationProtocol
 *  jetbrains.buildServer.agent.impl.serverCommunication.ProtocolDoesNotWorkException
 *  jetbrains.buildServer.agent.impl.serverCommunication.ServerCommandsPoller
 *  jetbrains.buildServer.agent.impl.serverCommunication.ServerCommandsPoller$OnExit
 *  jetbrains.buildServer.agentServer.AgentDetails
 *  jetbrains.buildServer.agentServer.AgentRegistrationDetails
 *  jetbrains.buildServer.agentServer.polling.AgentServerResponse
 *  jetbrains.buildServer.agentServer.polling.Error
 *  jetbrains.buildServer.http.HttpApi
 *  jetbrains.buildServer.http.HttpApi$HeaderPair
 *  jetbrains.buildServer.http.HttpApi$Response
 *  jetbrains.buildServer.log.Loggers
 *  jetbrains.buildServer.util.XmlUtil
 *  org.jdom.Element
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package jetbrains.buildServer.agent.impl.serverCommunication;

import com.intellij.openapi.util.Pair;
import java.io.IOException;
import java.net.URL;
import java.net.UnknownHostException;
import jetbrains.buildServer.AlreadyExistsException;
import jetbrains.buildServer.InvalidRunningBuildException;
import jetbrains.buildServer.agent.BuildAgentConfiguration;
import jetbrains.buildServer.agent.impl.BuildAgentImpl;
import jetbrains.buildServer.agent.impl.RegistrationLogger;
import jetbrains.buildServer.agent.impl.serverCommunication.CommunicationProtocol;
import jetbrains.buildServer.agent.impl.serverCommunication.ProtocolDoesNotWorkException;
import jetbrains.buildServer.agent.impl.serverCommunication.ServerCommandsPoller;
import jetbrains.buildServer.agentServer.AgentDetails;
import jetbrains.buildServer.agentServer.AgentRegistrationDetails;
import jetbrains.buildServer.agentServer.polling.AgentServerResponse;
import jetbrains.buildServer.agentServer.polling.Error;
import jetbrains.buildServer.http.HttpApi;
import jetbrains.buildServer.log.Loggers;
import jetbrains.buildServer.util.XmlUtil;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PollingProtocol
implements CommunicationProtocol {
    static final String PROTOCOL_PATH_PREFIX = "/app/agents/v1/";
    private static final String REGISTER_PATH = "/app/agents/v1/register";
    private final BuildAgentConfiguration myConfiguration;
    private final ServerCommandsPoller myServerCommandsPoller;
    private final HttpApi myServerApi;

    public PollingProtocol(BuildAgentConfiguration configuration, ServerCommandsPoller serverCommandsPoller, HttpApi serverApi) {
        this.myConfiguration = configuration;
        this.myServerCommandsPoller = serverCommandsPoller;
        this.myServerApi = serverApi;
    }

    @NotNull
    public String getId() {
        return "polling";
    }

    @NotNull
    public synchronized AgentRegistrationDetails registerOnServer(@NotNull AgentDetails agentDetails, @NotNull BuildAgentImpl buildAgent) throws ProtocolDoesNotWorkException {
        if (agentDetails == null) {
            PollingProtocol.$$$reportNull$$$0(0);
        }
        if (buildAgent == null) {
            PollingProtocol.$$$reportNull$$$0(1);
        }
        Pair<String, AgentRegistrationDetails> sessionInAndRegistrationDetails = this.register(agentDetails);
        this.myServerCommandsPoller.start((String)sessionInAndRegistrationDetails.getFirst(), buildAgent, (ServerCommandsPoller.OnExit)new /* Unavailable Anonymous Inner Class!! */);
        AgentRegistrationDetails agentRegistrationDetails = (AgentRegistrationDetails)sessionInAndRegistrationDetails.getSecond();
        if (agentRegistrationDetails == null) {
            PollingProtocol.$$$reportNull$$$0(2);
        }
        return agentRegistrationDetails;
    }

    /*
     * Loose catch block
     */
    @NotNull
    private Pair<String, AgentRegistrationDetails> register(@NotNull AgentDetails agentDetails) throws ProtocolDoesNotWorkException {
        Pair<String, AgentRegistrationDetails> pair;
        HttpApi.Response response;
        block12: {
            if (agentDetails == null) {
                PollingProtocol.$$$reportNull$$$0(3);
            }
            String registrationUrl = this.myConfiguration.getServerUrl() + REGISTER_PATH;
            RegistrationLogger.debug((String)"Determining the id of the main node");
            String mainNodeId = this.getMainNodeId();
            if (mainNodeId != null) {
                this.myServerApi.addCookie("X-TeamCity-Node-Id-Cookie", mainNodeId, new URL(registrationUrl).getHost(), 60);
            }
            RegistrationLogger.debug((String)("Sending polling protocol registration request: POST " + registrationUrl));
            response = this.myServerApi.post(registrationUrl, agentDetails.serializeToString(), "application/xml", "UTF-8", new HttpApi.HeaderPair[0]);
            pair = this.parseRegistrationResponse(registrationUrl, response);
            if (response == null) break block12;
            response.close();
        }
        Pair<String, AgentRegistrationDetails> pair2 = pair;
        if (pair2 == null) {
            PollingProtocol.$$$reportNull$$$0(4);
        }
        return pair2;
        {
            catch (Throwable throwable) {
                try {
                    if (response != null) {
                        try {
                            response.close();
                        }
                        catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                    }
                    throw throwable;
                }
                catch (UnknownHostException e) {
                    throw new ProtocolDoesNotWorkException("IP address of a host could not be determined");
                }
                catch (IOException e) {
                    throw new ProtocolDoesNotWorkException((Throwable)e);
                }
            }
        }
    }

    private Pair<String, AgentRegistrationDetails> parseRegistrationResponse(String registrationUrl, HttpApi.Response response) throws ProtocolDoesNotWorkException {
        AgentServerResponse registrationAnswer;
        if (response.getStatusCode() != 200) {
            RegistrationLogger.debug((String)("Error while registering using polling protocol by POST " + registrationUrl + ". Details: unexpected response status code '" + response.getStatusCode() + "'"));
            RegistrationLogger.debug((String)response.getBody());
            throw new ProtocolDoesNotWorkException("Unexpected server response '" + response.getStatusCode() + " " + response.getStatusText() + "'");
        }
        try {
            registrationAnswer = AgentServerResponse.deserialize((String)response.getBody());
        }
        catch (Exception e) {
            RegistrationLogger.warn((String)("Error while registering using polling protocol by POST " + registrationUrl + ". Details: unexpected server response: \n" + response.getBody()));
            throw new ProtocolDoesNotWorkException("Unexpected server response", (Throwable)e);
        }
        if (registrationAnswer.getData() != null) {
            RegistrationLogger.debug((String)("Server confirmed registration, id = " + ((AgentRegistrationDetails)registrationAnswer.getData()).getAgentId()));
            return new Pair((Object)response.getHeader("TeamCity-AgentSessionId"), (Object)((AgentRegistrationDetails)registrationAnswer.getData()));
        }
        Error error = registrationAnswer.getError();
        assert (error != null);
        RegistrationLogger.debug((String)("Server rejected registration, error: " + String.valueOf(error)));
        if (error.getType().equals(AlreadyExistsException.class.getSimpleName())) {
            int parsedId;
            try {
                parsedId = error.getAdditionalData() == null ? -1 : Integer.parseInt(error.getAdditionalData());
            }
            catch (NumberFormatException e) {
                RegistrationLogger.warn((String)("Could not parse agent id from the error additional details: " + error.getAdditionalData()));
                parsedId = -1;
            }
            throw new AlreadyExistsException(error.getMessage(), parsedId);
        }
        if (error.getType().equals(InvalidRunningBuildException.class.getSimpleName())) {
            throw new InvalidRunningBuildException(error.getMessage());
        }
        throw new ProtocolDoesNotWorkException(error.toString());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Nullable
    private String getMainNodeId() {
        String nodesRestAPI = this.myConfiguration.getServerUrl() + "/app/rest/server/nodes";
        try (HttpApi.Response response = this.myServerApi.get(nodesRestAPI, new HttpApi.HeaderPair[]{new HttpApi.HeaderPair("Content-type", (Object)"application/xml")});){
            String responseBody = response.getBody();
            if (response.getStatusCode() != 200) {
                throw new IOException("Invalid response code: " + response.getStatusCode() + ", response body: " + responseBody);
            }
            Element parsed = XmlUtil.from_s((String)responseBody);
            if (!parsed.getName().equals("nodes")) {
                throw new RuntimeException("Unexpected response: " + responseBody);
            }
            Element mainNode = XmlUtil.selectElement((Element)parsed, (String)"//nodes/node[@state='online' and @role='main_node']");
            if (mainNode == null) {
                String string2 = null;
                return string2;
            }
            String string = mainNode.getAttributeValue("id");
            return string;
        }
        catch (Throwable e) {
            Loggers.REGISTRATION.infoAndDebugDetails("Could not determine the id of the main node, HTTP GET request for URL: " + nodesRestAPI + " failed", e);
            return null;
        }
    }

    private static /* synthetic */ void $$$reportNull$$$0(int n) {
        Object[] objectArray;
        Object[] objectArray2;
        Object[] objectArray3 = new Object[switch (n) {
            default -> 3;
            case 2, 4 -> 2;
        }];
        switch (n) {
            default: {
                objectArray2 = objectArray3;
                objectArray3[0] = "agentDetails";
                break;
            }
            case 1: {
                objectArray2 = objectArray3;
                objectArray3[0] = "buildAgent";
                break;
            }
            case 2: 
            case 4: {
                objectArray2 = objectArray3;
                objectArray3[0] = "jetbrains/buildServer/agent/impl/serverCommunication/PollingProtocol";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray2;
                objectArray2[1] = "jetbrains/buildServer/agent/impl/serverCommunication/PollingProtocol";
                break;
            }
            case 2: {
                objectArray = objectArray2;
                objectArray2[1] = "registerOnServer";
                break;
            }
            case 4: {
                objectArray = objectArray2;
                objectArray2[1] = "register";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray;
                objectArray[2] = "registerOnServer";
                break;
            }
            case 2: 
            case 4: {
                break;
            }
            case 3: {
                objectArray = objectArray;
                objectArray[2] = "register";
                break;
            }
        }
        String string = String.format(v0, objectArray);
        throw switch (n) {
            default -> new IllegalArgumentException(string);
            case 2, 4 -> new IllegalStateException(string);
        };
    }
}
