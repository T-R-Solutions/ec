/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.ApiModelProperty
 *  javax.xml.bind.annotation.XmlAttribute
 *  javax.xml.bind.annotation.XmlElement
 *  javax.xml.bind.annotation.XmlRootElement
 *  javax.xml.bind.annotation.XmlType
 *  jetbrains.buildServer.RootUrlHolder
 *  jetbrains.buildServer.ServiceLocator
 *  jetbrains.buildServer.maintenance.StartupContext
 *  jetbrains.buildServer.server.rest.ApiUrlBuilder
 *  jetbrains.buildServer.server.rest.data.DataProvider
 *  jetbrains.buildServer.server.rest.errors.NotFoundException
 *  jetbrains.buildServer.server.rest.model.Fields
 *  jetbrains.buildServer.server.rest.model.Href
 *  jetbrains.buildServer.server.rest.model.Util
 *  jetbrains.buildServer.server.rest.model.nodes.Node
 *  jetbrains.buildServer.server.rest.request.AgentPoolRequest
 *  jetbrains.buildServer.server.rest.request.BuildQueueRequest
 *  jetbrains.buildServer.server.rest.request.InvestigationRequest
 *  jetbrains.buildServer.server.rest.request.MuteRequest
 *  jetbrains.buildServer.server.rest.request.NodesRequest
 *  jetbrains.buildServer.server.rest.swagger.annotations.ModelDescription
 *  jetbrains.buildServer.server.rest.util.ValueWithDefault
 *  jetbrains.buildServer.serverSide.SBuildServer
 *  jetbrains.buildServer.serverSide.ServerPaths
 *  jetbrains.buildServer.serverSide.ServerSettings
 *  jetbrains.buildServer.serverSide.TeamCityNode
 *  jetbrains.buildServer.serverSide.TeamCityNodes
 *  jetbrains.buildServer.serverSide.auth.Permission
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package jetbrains.buildServer.server.rest.model.server;

import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import jetbrains.buildServer.RootUrlHolder;
import jetbrains.buildServer.ServiceLocator;
import jetbrains.buildServer.maintenance.StartupContext;
import jetbrains.buildServer.server.rest.ApiUrlBuilder;
import jetbrains.buildServer.server.rest.data.DataProvider;
import jetbrains.buildServer.server.rest.errors.NotFoundException;
import jetbrains.buildServer.server.rest.model.Fields;
import jetbrains.buildServer.server.rest.model.Href;
import jetbrains.buildServer.server.rest.model.Util;
import jetbrains.buildServer.server.rest.model.nodes.Node;
import jetbrains.buildServer.server.rest.request.AgentPoolRequest;
import jetbrains.buildServer.server.rest.request.BuildQueueRequest;
import jetbrains.buildServer.server.rest.request.InvestigationRequest;
import jetbrains.buildServer.server.rest.request.MuteRequest;
import jetbrains.buildServer.server.rest.request.NodesRequest;
import jetbrains.buildServer.server.rest.swagger.annotations.ModelDescription;
import jetbrains.buildServer.server.rest.util.ValueWithDefault;
import jetbrains.buildServer.serverSide.SBuildServer;
import jetbrains.buildServer.serverSide.ServerPaths;
import jetbrains.buildServer.serverSide.ServerSettings;
import jetbrains.buildServer.serverSide.TeamCityNode;
import jetbrains.buildServer.serverSide.TeamCityNodes;
import jetbrains.buildServer.serverSide.auth.Permission;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@XmlRootElement(name="server")
@XmlType(name="server", propOrder={"version", "versionMajor", "versionMinor", "startTime", "currentTime", "buildNumber", "buildDate", "internalId", "role", "webUrl", "projects", "vcsRoots", "builds", "users", "userGroups", "agents", "buildQueue", "agentPools", "investigations", "mutes", "artifactsUrl", "nodes"})
@ModelDescription(value="Stores extensive information about the current TeamCity server instance.")
public class Server {
    private SBuildServer myServer;
    private ServerSettings myServerSettings;
    private Fields myFields;
    private ServiceLocator myServiceLocator;
    private ApiUrlBuilder myApiUrlBuilder;

    public Server() {
    }

    public Server(@NotNull Fields fields, ServiceLocator serviceLocator, ApiUrlBuilder apiUrlBuilder) {
        if (fields == null) {
            Server.$$$reportNull$$$0(0);
        }
        this.myFields = fields;
        this.myServiceLocator = serviceLocator;
        this.myServer = (SBuildServer)serviceLocator.getSingletonService(SBuildServer.class);
        this.myServerSettings = (ServerSettings)serviceLocator.getSingletonService(ServerSettings.class);
        this.myApiUrlBuilder = apiUrlBuilder;
    }

    @XmlAttribute
    @ApiModelProperty(value="The full TeamCity server version, including the build number and, if present, the EAP (early access preview) flag.", example="2025.07 EAP (build 194451)")
    public String getVersion() {
        return (String)ValueWithDefault.decideIncludeByDefault((Boolean)this.myFields.isIncluded("version"), (Object)this.myServer.getFullServerVersion());
    }

    @XmlAttribute
    @ApiModelProperty(value="The major TeamCity version that points to the year when this version was released.", example="2025")
    public Integer getVersionMajor() {
        return (Integer)ValueWithDefault.decideIncludeByDefault((Boolean)this.myFields.isIncluded("versionMajor"), (Object)this.myServer.getVersion().getDisplayVersionMajor());
    }

    @XmlAttribute
    @ApiModelProperty(value="The major TeamCity version that points to the month of `versionMajor` when this version was released.", example="7")
    public Integer getVersionMinor() {
        return (Integer)ValueWithDefault.decideIncludeByDefault((Boolean)this.myFields.isIncluded("versionMinor"), (Object)this.myServer.getVersion().getDisplayVersionMinor());
    }

    @XmlAttribute
    @ApiModelProperty(value="The TeamCity build number.", example="194451")
    public String getBuildNumber() {
        return (String)ValueWithDefault.decideIncludeByDefault((Boolean)this.myFields.isIncluded("buildNumber"), (Object)this.myServer.getBuildNumber());
    }

    @XmlAttribute
    @ApiModelProperty(value="The most recent date this TeamCity server was started.", example="20250515T114245+0200")
    public String getStartTime() {
        try {
            StartupContext startupContext = (StartupContext)((DataProvider)this.myServiceLocator.getSingletonService(DataProvider.class)).getBean(StartupContext.class);
            return (String)ValueWithDefault.decideIncludeByDefault((Boolean)this.myFields.isIncluded("startTime"), (Object)Util.formatTime((Date)startupContext.getServerStartupTimestamp()));
        }
        catch (Exception e) {
            return null;
        }
    }

    @XmlAttribute
    @ApiModelProperty(value="The current TeamCity server time, which corresponds to the server's machine time.", example="20250522T122203+0200")
    public String getCurrentTime() {
        return (String)ValueWithDefault.decideIncludeByDefault((Boolean)this.myFields.isIncluded("currentTime"), (Object)Util.formatTime((Date)new Date()));
    }

    @XmlAttribute
    @ApiModelProperty(value="The date when this TeamCity version was built by JetBrains.", example="20250514T000000+0200")
    public String getBuildDate() {
        return (String)ValueWithDefault.decideIncludeByDefault((Boolean)this.myFields.isIncluded("buildDate"), (Object)Util.formatTime((Date)this.myServer.getBuildDate()));
    }

    @XmlAttribute
    @ApiModelProperty(value="The internally used read-only server ID.", example="5218cd10a-9ed0-4e9c-9150-094039adadd0")
    public String getInternalId() {
        return (String)ValueWithDefault.decideIncludeByDefault((Boolean)this.myFields.isIncluded("internalId"), (Object)this.myServerSettings.getServerUUID());
    }

    @XmlAttribute
    @ApiModelProperty(value="In a multi-node setup, returns the role of the current TeamCity server. Returns `main_node` for single-node setups.", example="main_node")
    public String getRole() {
        TeamCityNode currentNode = ((TeamCityNodes)this.myServiceLocator.getSingletonService(TeamCityNodes.class)).getCurrentNode();
        if (!currentNode.isMainNode()) {
            return (String)ValueWithDefault.decideIncludeByDefault((Boolean)this.myFields.isIncluded("role"), (Object)Node.getNodeRole((TeamCityNode)currentNode).name());
        }
        return (String)ValueWithDefault.decideDefault((Boolean)this.myFields.isIncluded("role"), (Object)Node.getNodeRole((TeamCityNode)currentNode).name());
    }

    @XmlAttribute
    @ApiModelProperty(value="Returns the regular web URL of the server, including the server port.", example="https://mybuild.farm.net:8111")
    public String getWebUrl() {
        return (String)ValueWithDefault.decideIncludeByDefault((Boolean)this.myFields.isIncluded("webUrl"), (Object)this.myServer.getRootUrl());
    }

    @XmlAttribute
    @ApiModelProperty(value="Returns the artifacts isolation URL: the URL of a separate domain that stores build artifacts and mitigates the risk of TeamCity server attacks carried out by users who access these artifacts. If the isolation URL is not set (default setup), returns an empty string.", example="")
    public String getArtifactsUrl() {
        return (String)ValueWithDefault.decideIncludeByDefault((Boolean)this.myFields.isIncluded("artifactsUrl"), (Object)this.myServerSettings.getArtifactsRootUrl());
    }

    @XmlElement
    @ApiModelProperty(value="The relative (without TeamCity server URL) endpoint that allows you to access projects managed by this server.", example="{'href': '/app/rest/projects'}")
    public Href getProjects() {
        return (Href)ValueWithDefault.decideIncludeByDefault((Boolean)this.myFields.isIncluded("projects"), (Object)new Href("/app/rest/projects", this.myApiUrlBuilder));
    }

    @XmlElement
    @ApiModelProperty(value="The relative (without TeamCity server URL) endpoint that allows you to access VCS roots owned by this server.", example="{'href': '/app/rest/vcs-roots'}")
    public Href getVcsRoots() {
        return (Href)ValueWithDefault.decideIncludeByDefault((Boolean)this.myFields.isIncluded("vcsRoots"), (Object)new Href("/app/rest/vcs-roots", this.myApiUrlBuilder));
    }

    @XmlElement
    @ApiModelProperty(value="The relative (without TeamCity server URL) endpoint that allows you to access builds processed by this server.", example="{'href': '/app/rest/builds'}")
    public Href getBuilds() {
        return (Href)ValueWithDefault.decideIncludeByDefault((Boolean)this.myFields.isIncluded("builds"), (Object)new Href("/app/rest/builds", this.myApiUrlBuilder));
    }

    @XmlElement
    @ApiModelProperty(value="The relative (without TeamCity server URL) endpoint that allows you to view and edit users of this server.", example="{'href': '/app/rest/users'}")
    public Href getUsers() {
        return (Href)ValueWithDefault.decideIncludeByDefault((Boolean)this.myFields.isIncluded("users"), (Object)new Href("/app/rest/users", this.myApiUrlBuilder));
    }

    @XmlElement
    @ApiModelProperty(value="The relative (without TeamCity server URL) endpoint that allows you to view and edit usergroups.", example="{'href': '/app/rest/userGroup'}")
    public Href getUserGroups() {
        return (Href)ValueWithDefault.decideIncludeByDefault((Boolean)this.myFields.isIncluded("userGroups"), (Object)new Href("/app/rest/userGroups", this.myApiUrlBuilder));
    }

    @XmlElement
    @ApiModelProperty(value="The relative (without TeamCity server URL) endpoint that allows you to access build agents connected to this server.", example="{'href': '/app/rest/agents'}")
    public Href getAgents() {
        return (Href)ValueWithDefault.decideIncludeByDefault((Boolean)this.myFields.isIncluded("agents"), (Object)new Href("/app/rest/agents", this.myApiUrlBuilder));
    }

    @XmlElement
    @ApiModelProperty(value="The relative (without TeamCity server URL) endpoint that allows you to access a build queue managed by this server.", example="{'href': '/app/rest/buildQueue'}")
    public Href getBuildQueue() {
        return (Href)ValueWithDefault.decideIncludeByDefault((Boolean)this.myFields.isIncluded("buildQueue"), (Object)new Href(BuildQueueRequest.getHref(), this.myApiUrlBuilder));
    }

    @XmlElement
    @ApiModelProperty(value="The relative (without TeamCity server URL) endpoint that allows you to access agent pools.", example="{'href': '/app/rest/agentPools'}")
    public Href getAgentPools() {
        return (Href)ValueWithDefault.decideIncludeByDefault((Boolean)this.myFields.isIncluded("agentPools"), (Object)new Href(AgentPoolRequest.getHref(), this.myApiUrlBuilder));
    }

    @XmlElement
    @ApiModelProperty(value="The relative (without TeamCity server URL) endpoint that allows you to access problem investigations.", example="{'href': '/app/rest/investigations'}")
    public Href getInvestigations() {
        return (Href)ValueWithDefault.decideIncludeByDefault((Boolean)this.myFields.isIncluded("investigations"), (Object)new Href(InvestigationRequest.getHref(), this.myApiUrlBuilder));
    }

    @XmlElement
    @ApiModelProperty(value="The relative (without TeamCity server URL) endpoint that allows you to access muted problems.", example="{'href': '/app/rest/mutes'}")
    public Href getMutes() {
        return (Href)ValueWithDefault.decideIncludeByDefault((Boolean)this.myFields.isIncluded("mutes"), (Object)new Href(MuteRequest.getHref(), this.myApiUrlBuilder));
    }

    @XmlElement
    @ApiModelProperty(value="The relative (without TeamCity server URL) endpoint that allows you to access all nodes of a setup to which the current server belongs.", example="{'href': '/app/rest/nodes'}")
    public Href getNodes() {
        return (Href)ValueWithDefault.decideIncludeByDefault((Boolean)this.myFields.isIncluded("nodes"), (Object)new Href(NodesRequest.getHref(), this.myApiUrlBuilder));
    }

    @Nullable
    public static String getFieldValue(@Nullable String field, @NotNull ServiceLocator serviceLocator) {
        if (serviceLocator == null) {
            Server.$$$reportNull$$$0(1);
        }
        if ("version".equals(field)) {
            return ((SBuildServer)serviceLocator.getSingletonService(SBuildServer.class)).getFullServerVersion();
        }
        if ("buildNumber".equals(field) || "build".equals(field)) {
            return ((SBuildServer)serviceLocator.getSingletonService(SBuildServer.class)).getBuildNumber();
        }
        if ("versionMajor".equals(field) || "majorVersion".equals(field)) {
            return Integer.toString(((SBuildServer)serviceLocator.getSingletonService(SBuildServer.class)).getVersion().getDisplayVersionMajor());
        }
        if ("versionMinor".equals(field) || "minorVersion".equals(field)) {
            return Integer.toString(((SBuildServer)serviceLocator.getSingletonService(SBuildServer.class)).getVersion().getDisplayVersionMinor());
        }
        if ("startTime".equals(field)) {
            return Util.formatTime((Date)((DataProvider)serviceLocator.getSingletonService(DataProvider.class)).getServerStartTime());
        }
        if ("currentTime".equals(field)) {
            return Util.formatTime((Date)new Date());
        }
        if ("internalId".equals(field)) {
            return ((ServerSettings)serviceLocator.getSingletonService(ServerSettings.class)).getServerUUID();
        }
        if ("superUserToken".equals(field)) {
            ((DataProvider)serviceLocator.getSingletonService(DataProvider.class)).checkGlobalPermission(Permission.MANAGE_SERVER_INSTALLATION);
            return ((StartupContext)((DataProvider)serviceLocator.getSingletonService(DataProvider.class)).getBean(StartupContext.class)).getMaintenanceAuthenticationToken();
        }
        if ("dataDirectoryPath".equals(field)) {
            ((DataProvider)serviceLocator.getSingletonService(DataProvider.class)).checkGlobalPermission(Permission.VIEW_SERVER_SETTINGS);
            return ((ServerPaths)((DataProvider)serviceLocator.getSingletonService(DataProvider.class)).getBean(ServerPaths.class)).getDataDirectory().getAbsolutePath();
        }
        if ("role".equals(field)) {
            return Node.getNodeRole((TeamCityNode)((TeamCityNodes)serviceLocator.getSingletonService(TeamCityNodes.class)).getCurrentNode()).name();
        }
        if ("webUrl".equals(field) || "url".equals(field)) {
            return ((RootUrlHolder)serviceLocator.getSingletonService(RootUrlHolder.class)).getRootUrl();
        }
        if ("artifactsUrl".equals(field)) {
            return ((ServerSettings)serviceLocator.getSingletonService(ServerSettings.class)).getArtifactsRootUrl();
        }
        throw new NotFoundException("Field '" + field + "' is not supported. Supported are: version, versionMajor, versionMinor, buildNumber, startTime, currentTime, internalId, role, artifactsUrl.");
    }

    private static /* synthetic */ void $$$reportNull$$$0(int n) {
        Object[] objectArray;
        Object[] objectArray2;
        Object[] objectArray3 = new Object[3];
        switch (n) {
            default: {
                objectArray2 = objectArray3;
                objectArray3[0] = "fields";
                break;
            }
            case 1: {
                objectArray2 = objectArray3;
                objectArray3[0] = "serviceLocator";
                break;
            }
        }
        objectArray2[1] = "jetbrains/buildServer/server/rest/model/server/Server";
        switch (n) {
            default: {
                objectArray = objectArray2;
                objectArray2[2] = "<init>";
                break;
            }
            case 1: {
                objectArray = objectArray2;
                objectArray2[2] = "getFieldValue";
                break;
            }
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objectArray));
    }
}
