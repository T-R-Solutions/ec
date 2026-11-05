/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.intellij.openapi.diagnostic.Logger
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  io.swagger.annotations.ApiParam
 *  javax.servlet.ServletContext
 *  javax.servlet.http.HttpServletRequest
 *  javax.ws.rs.Consumes
 *  javax.ws.rs.DELETE
 *  javax.ws.rs.GET
 *  javax.ws.rs.POST
 *  javax.ws.rs.PUT
 *  javax.ws.rs.Path
 *  javax.ws.rs.PathParam
 *  javax.ws.rs.Produces
 *  javax.ws.rs.QueryParam
 *  javax.ws.rs.core.Context
 *  javax.ws.rs.core.Response
 *  javax.ws.rs.core.Response$ResponseBuilder
 *  javax.ws.rs.core.Response$Status
 *  javax.ws.rs.core.StreamingOutput
 *  javax.ws.rs.core.UriBuilder
 *  javax.ws.rs.core.UriInfo
 *  jetbrains.buildServer.Build
 *  jetbrains.buildServer.BuildProblemData
 *  jetbrains.buildServer.QueuedBuild
 *  jetbrains.buildServer.ServiceLocator
 *  jetbrains.buildServer.controllers.ActionErrors
 *  jetbrains.buildServer.controllers.ActionErrors$Error
 *  jetbrains.buildServer.controllers.FileSecurityUtil
 *  jetbrains.buildServer.controllers.actions.ChangeBuildStatusProcessor
 *  jetbrains.buildServer.log.Loggers
 *  jetbrains.buildServer.messages.DefaultMessagesInfo
 *  jetbrains.buildServer.messages.Status
 *  jetbrains.buildServer.parameters.ParametersProvider
 *  jetbrains.buildServer.parameters.ProcessingResult
 *  jetbrains.buildServer.parameters.ReferencesResolverUtil
 *  jetbrains.buildServer.server.rest.data.BranchData
 *  jetbrains.buildServer.server.rest.data.PagedSearchResult
 *  jetbrains.buildServer.server.rest.data.PermissionChecker
 *  jetbrains.buildServer.server.rest.data.TimeWithPrecision
 *  jetbrains.buildServer.server.rest.data.build.BuildParametersUtil
 *  jetbrains.buildServer.server.rest.data.build.TagFinder
 *  jetbrains.buildServer.server.rest.data.finder.Finder
 *  jetbrains.buildServer.server.rest.data.finder.impl.BuildFinder
 *  jetbrains.buildServer.server.rest.data.finder.impl.BuildPromotionFinder
 *  jetbrains.buildServer.server.rest.data.finder.impl.BuildTypeFinder
 *  jetbrains.buildServer.server.rest.data.finder.impl.UserFinder
 *  jetbrains.buildServer.server.rest.data.finder.impl.VcsRootInstanceFinder
 *  jetbrains.buildServer.server.rest.data.problem.ProblemOccurrenceFinder
 *  jetbrains.buildServer.server.rest.data.problem.TestOccurrenceFinder
 *  jetbrains.buildServer.server.rest.errors.AuthorizationFailedException
 *  jetbrains.buildServer.server.rest.errors.BadRequestException
 *  jetbrains.buildServer.server.rest.errors.InvalidStateException
 *  jetbrains.buildServer.server.rest.errors.NotFoundException
 *  jetbrains.buildServer.server.rest.errors.OperationException
 *  jetbrains.buildServer.server.rest.model.Comment
 *  jetbrains.buildServer.server.rest.model.Fields
 *  jetbrains.buildServer.server.rest.model.MultipleOperationResult
 *  jetbrains.buildServer.server.rest.model.MultipleOperationResult$Data
 *  jetbrains.buildServer.server.rest.model.OperationResult$Data
 *  jetbrains.buildServer.server.rest.model.PagerData
 *  jetbrains.buildServer.server.rest.model.PagerDataImpl
 *  jetbrains.buildServer.server.rest.model.Properties
 *  jetbrains.buildServer.server.rest.model.RelatedEntity$Entity
 *  jetbrains.buildServer.server.rest.model.Util
 *  jetbrains.buildServer.server.rest.model.build.Build
 *  jetbrains.buildServer.server.rest.model.build.BuildCancelRequest
 *  jetbrains.buildServer.server.rest.model.build.BuildStatusUpdate
 *  jetbrains.buildServer.server.rest.model.build.BuildStatusUpdateResult
 *  jetbrains.buildServer.server.rest.model.build.Builds
 *  jetbrains.buildServer.server.rest.model.build.PinInfo
 *  jetbrains.buildServer.server.rest.model.build.Tags
 *  jetbrains.buildServer.server.rest.model.build.VcsLabel
 *  jetbrains.buildServer.server.rest.model.build.VcsLabels
 *  jetbrains.buildServer.server.rest.model.buildType.BuildTypeUtil
 *  jetbrains.buildServer.server.rest.model.change.BuildChanges
 *  jetbrains.buildServer.server.rest.model.issue.IssueUsages
 *  jetbrains.buildServer.server.rest.model.problem.ProblemOccurrence
 *  jetbrains.buildServer.server.rest.model.problem.ProblemOccurrences
 *  jetbrains.buildServer.server.rest.model.problem.TestOccurrences
 *  jetbrains.buildServer.server.rest.request.BuildRequest$4
 *  jetbrains.buildServer.server.rest.request.BuildRequest$BuildArtifactsProvider
 *  jetbrains.buildServer.server.rest.request.BuildRequest$BuildIconStatus
 *  jetbrains.buildServer.server.rest.request.BuildRequest$BuildIconStatus$Value
 *  jetbrains.buildServer.server.rest.request.FilesSubResource
 *  jetbrains.buildServer.server.rest.request.FilesSubResource$Provider
 *  jetbrains.buildServer.server.rest.request.ParametersSubResource
 *  jetbrains.buildServer.server.rest.request.ProblemOccurrenceRequest
 *  jetbrains.buildServer.server.rest.request.StatusIconLocatorValidator
 *  jetbrains.buildServer.server.rest.request.TestOccurrenceRequest
 *  jetbrains.buildServer.server.rest.util.BeanContext
 *  jetbrains.buildServer.serverSide.Branch
 *  jetbrains.buildServer.serverSide.BuildHistory
 *  jetbrains.buildServer.serverSide.BuildHistoryEx
 *  jetbrains.buildServer.serverSide.BuildPromotion
 *  jetbrains.buildServer.serverSide.BuildPromotionEx
 *  jetbrains.buildServer.serverSide.BuildQueue
 *  jetbrains.buildServer.serverSide.BuildQueueEx
 *  jetbrains.buildServer.serverSide.BuildStatistics
 *  jetbrains.buildServer.serverSide.QueuedBuildEx
 *  jetbrains.buildServer.serverSide.RunningBuildEx
 *  jetbrains.buildServer.serverSide.RunningBuildsManager
 *  jetbrains.buildServer.serverSide.SAgentRestrictor
 *  jetbrains.buildServer.serverSide.SBuild
 *  jetbrains.buildServer.serverSide.SBuildType
 *  jetbrains.buildServer.serverSide.SFinishedBuild
 *  jetbrains.buildServer.serverSide.SQueuedBuild
 *  jetbrains.buildServer.serverSide.SRunningBuild
 *  jetbrains.buildServer.serverSide.ShortStatistics
 *  jetbrains.buildServer.serverSide.TagData
 *  jetbrains.buildServer.serverSide.TeamCityProperties
 *  jetbrains.buildServer.serverSide.TriggeredBy
 *  jetbrains.buildServer.serverSide.TriggeredByBuilder
 *  jetbrains.buildServer.serverSide.auth.AccessDeniedException
 *  jetbrains.buildServer.serverSide.auth.AuthUtil
 *  jetbrains.buildServer.serverSide.auth.AuthorityHolder
 *  jetbrains.buildServer.serverSide.auth.Permission
 *  jetbrains.buildServer.serverSide.auth.SecurityContext
 *  jetbrains.buildServer.serverSide.crypt.EncryptUtil
 *  jetbrains.buildServer.serverSide.impl.BaseBuild
 *  jetbrains.buildServer.serverSide.impl.BuildAgentMessagesQueue
 *  jetbrains.buildServer.serverSide.impl.BuildAgentMessagesQueue$BuildMessagesQueueFullException
 *  jetbrains.buildServer.serverSide.impl.LogUtil
 *  jetbrains.buildServer.serverSide.impl.RunningBuildState
 *  jetbrains.buildServer.serverSide.impl.auth.ServerAuthUtil
 *  jetbrains.buildServer.serverSide.vcs.VcsLabelManager
 *  jetbrains.buildServer.tags.TagsManager
 *  jetbrains.buildServer.users.SUser
 *  jetbrains.buildServer.users.User
 *  jetbrains.buildServer.util.FileUtil
 *  jetbrains.buildServer.util.StringUtil
 *  jetbrains.buildServer.util.TCStreamUtil
 *  jetbrains.buildServer.util.TimeService
 *  jetbrains.buildServer.vcs.VcsException
 *  jetbrains.buildServer.vcs.VcsManager
 *  jetbrains.buildServer.vcs.VcsRootInstance
 *  jetbrains.buildServer.vcs.VcsRootInstanceEntry
 *  jetbrains.buildServer.web.util.SessionUser
 *  jetbrains.buildServer.web.util.WebUtil
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package jetbrains.buildServer.server.rest.request;

import com.intellij.openapi.diagnostic.Logger;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import jetbrains.buildServer.Build;
import jetbrains.buildServer.BuildProblemData;
import jetbrains.buildServer.QueuedBuild;
import jetbrains.buildServer.ServiceLocator;
import jetbrains.buildServer.controllers.ActionErrors;
import jetbrains.buildServer.controllers.FileSecurityUtil;
import jetbrains.buildServer.controllers.actions.ChangeBuildStatusProcessor;
import jetbrains.buildServer.log.Loggers;
import jetbrains.buildServer.messages.DefaultMessagesInfo;
import jetbrains.buildServer.messages.Status;
import jetbrains.buildServer.parameters.ParametersProvider;
import jetbrains.buildServer.parameters.ProcessingResult;
import jetbrains.buildServer.parameters.ReferencesResolverUtil;
import jetbrains.buildServer.server.rest.data.BranchData;
import jetbrains.buildServer.server.rest.data.PagedSearchResult;
import jetbrains.buildServer.server.rest.data.PermissionChecker;
import jetbrains.buildServer.server.rest.data.TimeWithPrecision;
import jetbrains.buildServer.server.rest.data.build.BuildParametersUtil;
import jetbrains.buildServer.server.rest.data.build.TagFinder;
import jetbrains.buildServer.server.rest.data.finder.Finder;
import jetbrains.buildServer.server.rest.data.finder.impl.BuildFinder;
import jetbrains.buildServer.server.rest.data.finder.impl.BuildPromotionFinder;
import jetbrains.buildServer.server.rest.data.finder.impl.BuildTypeFinder;
import jetbrains.buildServer.server.rest.data.finder.impl.UserFinder;
import jetbrains.buildServer.server.rest.data.finder.impl.VcsRootInstanceFinder;
import jetbrains.buildServer.server.rest.data.problem.ProblemOccurrenceFinder;
import jetbrains.buildServer.server.rest.data.problem.TestOccurrenceFinder;
import jetbrains.buildServer.server.rest.errors.AuthorizationFailedException;
import jetbrains.buildServer.server.rest.errors.BadRequestException;
import jetbrains.buildServer.server.rest.errors.InvalidStateException;
import jetbrains.buildServer.server.rest.errors.NotFoundException;
import jetbrains.buildServer.server.rest.errors.OperationException;
import jetbrains.buildServer.server.rest.model.Comment;
import jetbrains.buildServer.server.rest.model.Fields;
import jetbrains.buildServer.server.rest.model.MultipleOperationResult;
import jetbrains.buildServer.server.rest.model.OperationResult;
import jetbrains.buildServer.server.rest.model.PagerData;
import jetbrains.buildServer.server.rest.model.PagerDataImpl;
import jetbrains.buildServer.server.rest.model.Properties;
import jetbrains.buildServer.server.rest.model.RelatedEntity;
import jetbrains.buildServer.server.rest.model.Util;
import jetbrains.buildServer.server.rest.model.build.BuildCancelRequest;
import jetbrains.buildServer.server.rest.model.build.BuildStatusUpdate;
import jetbrains.buildServer.server.rest.model.build.BuildStatusUpdateResult;
import jetbrains.buildServer.server.rest.model.build.Builds;
import jetbrains.buildServer.server.rest.model.build.PinInfo;
import jetbrains.buildServer.server.rest.model.build.Tags;
import jetbrains.buildServer.server.rest.model.build.VcsLabel;
import jetbrains.buildServer.server.rest.model.build.VcsLabels;
import jetbrains.buildServer.server.rest.model.buildType.BuildTypeUtil;
import jetbrains.buildServer.server.rest.model.change.BuildChanges;
import jetbrains.buildServer.server.rest.model.issue.IssueUsages;
import jetbrains.buildServer.server.rest.model.problem.ProblemOccurrence;
import jetbrains.buildServer.server.rest.model.problem.ProblemOccurrences;
import jetbrains.buildServer.server.rest.model.problem.TestOccurrences;
import jetbrains.buildServer.server.rest.request.BuildRequest;
import jetbrains.buildServer.server.rest.request.FilesSubResource;
import jetbrains.buildServer.server.rest.request.ParametersSubResource;
import jetbrains.buildServer.server.rest.request.ProblemOccurrenceRequest;
import jetbrains.buildServer.server.rest.request.StatusIconLocatorValidator;
import jetbrains.buildServer.server.rest.request.TestOccurrenceRequest;
import jetbrains.buildServer.server.rest.util.BeanContext;
import jetbrains.buildServer.serverSide.Branch;
import jetbrains.buildServer.serverSide.BuildHistory;
import jetbrains.buildServer.serverSide.BuildHistoryEx;
import jetbrains.buildServer.serverSide.BuildPromotion;
import jetbrains.buildServer.serverSide.BuildPromotionEx;
import jetbrains.buildServer.serverSide.BuildQueue;
import jetbrains.buildServer.serverSide.BuildQueueEx;
import jetbrains.buildServer.serverSide.BuildStatistics;
import jetbrains.buildServer.serverSide.QueuedBuildEx;
import jetbrains.buildServer.serverSide.RunningBuildEx;
import jetbrains.buildServer.serverSide.RunningBuildsManager;
import jetbrains.buildServer.serverSide.SAgentRestrictor;
import jetbrains.buildServer.serverSide.SBuild;
import jetbrains.buildServer.serverSide.SBuildType;
import jetbrains.buildServer.serverSide.SFinishedBuild;
import jetbrains.buildServer.serverSide.SQueuedBuild;
import jetbrains.buildServer.serverSide.SRunningBuild;
import jetbrains.buildServer.serverSide.ShortStatistics;
import jetbrains.buildServer.serverSide.TagData;
import jetbrains.buildServer.serverSide.TeamCityProperties;
import jetbrains.buildServer.serverSide.TriggeredBy;
import jetbrains.buildServer.serverSide.TriggeredByBuilder;
import jetbrains.buildServer.serverSide.auth.AccessDeniedException;
import jetbrains.buildServer.serverSide.auth.AuthUtil;
import jetbrains.buildServer.serverSide.auth.AuthorityHolder;
import jetbrains.buildServer.serverSide.auth.Permission;
import jetbrains.buildServer.serverSide.auth.SecurityContext;
import jetbrains.buildServer.serverSide.crypt.EncryptUtil;
import jetbrains.buildServer.serverSide.impl.BaseBuild;
import jetbrains.buildServer.serverSide.impl.BuildAgentMessagesQueue;
import jetbrains.buildServer.serverSide.impl.LogUtil;
import jetbrains.buildServer.serverSide.impl.RunningBuildState;
import jetbrains.buildServer.serverSide.impl.auth.ServerAuthUtil;
import jetbrains.buildServer.serverSide.vcs.VcsLabelManager;
import jetbrains.buildServer.tags.TagsManager;
import jetbrains.buildServer.users.SUser;
import jetbrains.buildServer.users.User;
import jetbrains.buildServer.util.FileUtil;
import jetbrains.buildServer.util.StringUtil;
import jetbrains.buildServer.util.TCStreamUtil;
import jetbrains.buildServer.util.TimeService;
import jetbrains.buildServer.vcs.VcsException;
import jetbrains.buildServer.vcs.VcsManager;
import jetbrains.buildServer.vcs.VcsRootInstance;
import jetbrains.buildServer.vcs.VcsRootInstanceEntry;
import jetbrains.buildServer.web.util.SessionUser;
import jetbrains.buildServer.web.util.WebUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Exception performing whole class analysis ignored.
 */
@Path(value="/app/rest/builds")
@Api(value="Build")
public class BuildRequest {
    private static final Logger LOG = Logger.getInstance((String)BuildRequest.class.getName());
    private static final String IMG_STATUS_WIDGET_ROOT_DIRECTORY = "/img/statusWidget";
    private static final String IMG_STATUS_WIDGET_DEFAULT_SUFFIX = ".svg";
    private static final Pattern NON_ALPHA_NUM_PATTERN = Pattern.compile("[^a-zA-Z0-9-#.]+");
    private static final String REST_BUILD_REQUEST_DELETE_LIMIT = "rest.buildRequest.delete.limit";
    public static final String RELATED_ISSUES = "/relatedIssues";
    public static final String TESTS = "testOccurrences";
    public static final String STATISTICS = "/statistics";
    public static final String BUILDS_ROOT_REQUEST_PATH = "/builds";
    public static final String API_BUILDS_URL = "/app/rest/builds";
    public static final String ARTIFACTS = "/artifacts";
    @Context
    @NotNull
    private BuildFinder myBuildFinder;
    @Context
    @NotNull
    private BuildPromotionFinder myBuildPromotionFinder;
    @Context
    @NotNull
    private BuildTypeFinder myBuildTypeFinder;
    @Context
    @NotNull
    private PermissionChecker myPermissionChecker;
    @Context
    @NotNull
    private ServiceLocator myServiceLocator;
    @Context
    @NotNull
    private BeanContext myBeanContext;
    @Context
    @NotNull
    private UserFinder myUserFinder;

    @NotNull
    private TagsManager getTagsManager() {
        TagsManager tagsManager = (TagsManager)this.myBeanContext.getSingletonService(TagsManager.class);
        if (tagsManager == null) {
            BuildRequest.$$$reportNull$$$0(0);
        }
        return tagsManager;
    }

    public static String getHref() {
        return "/app/rest/builds";
    }

    public static String getHref(@NotNull String locator) {
        if (locator == null) {
            BuildRequest.$$$reportNull$$$0(1);
        }
        return "/app/rest/builds/" + locator;
    }

    public static String getBuildHref(@NotNull SBuild build) {
        if (build == null) {
            BuildRequest.$$$reportNull$$$0(2);
        }
        return BuildRequest.getHref(BuildRequest.getBuildLocator(build));
    }

    public static String getBuildHref(@NotNull BuildPromotion build) {
        if (build == null) {
            BuildRequest.$$$reportNull$$$0(3);
        }
        return BuildRequest.getHref(BuildRequest.getBuildLocator(build));
    }

    @Nullable
    public static String getBuildsHref(@NotNull BranchData branch, @Nullable String buildsLocator) {
        SBuildType buildType;
        if (branch == null) {
            BuildRequest.$$$reportNull$$$0(4);
        }
        if ((buildType = branch.getBuildType()) == null) {
            return null;
        }
        return "/app/rest/builds?locator=" + Util.encodeUrlParamValue((String)BuildPromotionFinder.getLocator((SBuildType)buildType, (Branch)branch, (String)buildsLocator));
    }

    public static String getBuildLocator(@NotNull SBuild build) {
        if (build == null) {
            BuildRequest.$$$reportNull$$$0(5);
        }
        return BuildRequest.getBuildLocator(build.getBuildPromotion());
    }

    public static String getBuildLocator(@NotNull BuildPromotion buildPromotion) {
        if (buildPromotion == null) {
            BuildRequest.$$$reportNull$$$0(6);
        }
        return BuildPromotionFinder.getLocator((BuildPromotion)buildPromotion);
    }

    public static String getBuildIssuesHref(SBuild build) {
        return BuildRequest.getBuildHref(build) + "/relatedIssues";
    }

    @NotNull
    public static String getBuildArtifactsHref(@NotNull BuildPromotion build) {
        if (build == null) {
            BuildRequest.$$$reportNull$$$0(7);
        }
        String string = Util.concatenatePath((String[])new String[]{BuildRequest.getBuildHref(build), "/artifacts"});
        if (string == null) {
            BuildRequest.$$$reportNull$$$0(8);
        }
        return string;
    }

    @GET
    @Produces(value={"application/xml", "application/json"})
    @ApiOperation(value="Get all builds.", nickname="getAllBuilds")
    public Builds serveAllBuilds(@ApiParam(hidden=true) @QueryParam(value="buildType") String buildTypeLocator, @ApiParam(hidden=true) @QueryParam(value="status") String status, @ApiParam(hidden=true) @QueryParam(value="triggeredByUser") String userLocator, @ApiParam(hidden=true) @QueryParam(value="includePersonal") boolean includePersonal, @ApiParam(hidden=true) @QueryParam(value="includeCanceled") boolean includeCanceled, @ApiParam(hidden=true) @QueryParam(value="onlyPinned") boolean onlyPinned, @ApiParam(hidden=true) @QueryParam(value="tag") List<String> tags, @ApiParam(hidden=true) @QueryParam(value="agentName") String agentName, @ApiParam(hidden=true) @QueryParam(value="sinceBuild") String sinceBuildLocator, @ApiParam(hidden=true) @QueryParam(value="sinceDate") String sinceDate, @ApiParam(hidden=true) @QueryParam(value="start") Long start, @ApiParam(hidden=true) @QueryParam(value="count") Integer count, @ApiParam(format="BuildLocator") @QueryParam(value="locator") String locator, @QueryParam(value="fields") String fields, @Context UriInfo uriInfo, @Context HttpServletRequest request) {
        return this.myBuildFinder.getBuildsForRequest(this.myBuildTypeFinder.getBuildTypeIfNotNull(buildTypeLocator), status, userLocator, includePersonal, includeCanceled, onlyPinned, tags, agentName, sinceBuildLocator, sinceDate, start, count, locator, "locator", uriInfo, request, new Fields(fields), this.myBeanContext);
    }

    @DELETE
    @ApiOperation(value="deleteBuilds", hidden=true)
    @Produces(value={"application/xml", "application/json"})
    public void deleteBuilds(@ApiParam(format="BuildLocator") @QueryParam(value="locator") String locator, @Context HttpServletRequest request) {
        if (locator == null) {
            throw new BadRequestException("Empty 'locator' parameter specified.");
        }
        List builds = this.myBuildPromotionFinder.getBuildPromotionsWithLegacyFallback(null, locator).getEntries();
        int deleteLimit = TeamCityProperties.getInteger((String)"rest.buildRequest.delete.limit", (int)10);
        if (builds.size() > deleteLimit) {
            throw new BadRequestException("Refusing to delete more than " + deleteLimit + " builds as a precaution measure. The limit is set via 'rest.buildRequest.delete.limit' internal property on the server.");
        }
        SUser user = SessionUser.getUser((HttpServletRequest)request);
        Map<Long, RuntimeException> errors = this.deleteBuilds(builds, user, null);
        if (!errors.isEmpty()) {
            throw errors.values().iterator().next();
        }
    }

    @Path(value="/{buildLocator}/attributes")
    @ApiOperation(value="getAttributes", nickname="getAttributes", hidden=true)
    public ParametersSubResource getAttributes(@ApiParam(format="BuildLocator") @PathParam(value="buildLocator") String buildLocator, @QueryParam(value="fields") String fields) {
        BuildPromotionEx build = (BuildPromotionEx)this.myBuildPromotionFinder.getItem(buildLocator);
        this.myPermissionChecker.checkPermission(Permission.EDIT_PROJECT, (BuildPromotion)build);
        return new /* Unavailable Anonymous Inner Class!! */;
    }

    @GET
    @Path(value="/{buildLocator}")
    @Produces(value={"application/xml", "application/json"})
    @ApiOperation(value="Get build matching the locator.", nickname="getBuild")
    public jetbrains.buildServer.server.rest.model.build.Build serveBuild(@ApiParam(format="BuildLocator") @PathParam(value="buildLocator") String buildLocator, @QueryParam(value="fields") String fields, @Context HttpServletRequest request) {
        BuildPromotion build = this.myBuildFinder.getBuildPromotion(null, buildLocator);
        return new jetbrains.buildServer.server.rest.model.build.Build(build, new Fields(fields), this.myBeanContext);
    }

    @GET
    @Path(value="/{buildLocator}/resulting-properties")
    @Produces(value={"application/xml", "application/json"})
    @ApiOperation(value="Get actual build parameters of the matching build.", nickname="getBuildActualParameters")
    public Properties serveBuildActualParameters(@ApiParam(format="BuildLocator") @PathParam(value="buildLocator") String buildLocator, @QueryParam(value="fields") String fields) {
        BuildPromotion build = (BuildPromotion)this.myBuildPromotionFinder.getItem(buildLocator);
        this.myPermissionChecker.checkPermission(Permission.VIEW_BUILD_RUNTIME_DATA, build);
        return new Properties(BuildParametersUtil.getResultingParameters((BuildPromotion)build), null, new Fields(fields), this.myBeanContext);
    }

    @GET
    @Path(value="/{buildLocator}/resulting-properties/{propertyName}")
    @Produces(value={"text/plain"})
    @ApiOperation(value="Returns the final value that a given parameter had after the build finished.", nickname="getBuildResultingProperties")
    public String getParameter(@ApiParam(format="BuildLocator") @PathParam(value="buildLocator") String buildLocator, @PathParam(value="propertyName") String propertyName) {
        BuildPromotion build = (BuildPromotion)this.myBuildPromotionFinder.getItem(buildLocator);
        this.myPermissionChecker.checkPermission(Permission.VIEW_BUILD_RUNTIME_DATA, build);
        return BuildTypeUtil.getParameter((String)propertyName, (ParametersProvider)BuildParametersUtil.getResultingParameters((BuildPromotion)build), (boolean)true, (boolean)true, (ServiceLocator)this.myServiceLocator);
    }

    @GET
    @Path(value="/{buildLocator}/output-parameters")
    @Produces(value={"application/xml", "application/json"})
    @ApiOperation(value="Get output parameters published by the build.", nickname="getBuildOutputParameters")
    public Properties serveBuildOutputParameters(@ApiParam(format="BuildLocator") @PathParam(value="buildLocator") String buildLocator, @QueryParam(value="fields") String fields) {
        BuildPromotion build = (BuildPromotion)this.myBuildPromotionFinder.getItem(buildLocator);
        this.myPermissionChecker.checkPermission(Permission.VIEW_BUILD_RUNTIME_DATA, build);
        return new Properties(BuildParametersUtil.getOutputParameters((BuildPromotion)build), null, new Fields(fields), this.myBeanContext);
    }

    @GET
    @Path(value="/{buildLocator}/output-parameters/{propertyName}")
    @Produces(value={"text/plain"})
    @ApiOperation(value="Returns the value of a build output parameter.", nickname="getBuildOutputParameters")
    public String getOutputParameter(@ApiParam(format="BuildLocator") @PathParam(value="buildLocator") String buildLocator, @PathParam(value="propertyName") String propertyName) {
        BuildPromotion build = (BuildPromotion)this.myBuildPromotionFinder.getItem(buildLocator);
        this.myPermissionChecker.checkPermission(Permission.VIEW_BUILD_RUNTIME_DATA, build);
        return BuildTypeUtil.getParameter((String)propertyName, (ParametersProvider)BuildParametersUtil.getOutputParameters((BuildPromotion)build), (boolean)true, (boolean)true, (ServiceLocator)this.myServiceLocator);
    }

    @DELETE
    @Path(value="/{buildLocator}/caches/finishProperties")
    @Produces(value={"application/xml", "application/json"})
    @ApiOperation(value="Remove build parameters from the matching build.", nickname="resetBuildFinishProperties")
    public void resetBuildFinishParameters(@ApiParam(format="BuildLocator") @PathParam(value="buildLocator") String buildLocator) {
        SBuild build = this.myBuildFinder.getBuild(null, buildLocator);
        this.myPermissionChecker.checkPermission(Permission.EDIT_PROJECT, build.getBuildPromotion());
        try {
            ((BaseBuild)build).resetBuildFinalParameters();
        }
        catch (ClassCastException classCastException) {
            // empty catch block
        }
    }

    @GET
    @Path(value="/{buildLocator}/resolved/{value}")
    @Produces(value={"text/plain"})
    @ApiOperation(value="Get the resolvement status of the matching build.", nickname="getBuildResolved")
    public String getResolvedParameter(@ApiParam(format="BuildLocator") @PathParam(value="buildLocator") String buildLocator, @PathParam(value="value") String value) {
        return this.getResolvedIfNecessary((BuildPromotion)this.myBuildPromotionFinder.getItem(buildLocator), value, true);
    }

    @Path(value="/{buildLocator}/artifacts")
    public FilesSubResource getFilesSubResource(@ApiParam(format="BuildLocator") @PathParam(value="buildLocator") String buildLocator, @QueryParam(value="resolveParameters") Boolean resolveParameters, @QueryParam(value="logBuildUsage") Boolean logBuildUsage) {
        BuildPromotion buildPromotion = this.myBuildFinder.getBuildPromotion(null, buildLocator);
        String urlPrefix = BuildRequest.getBuildArtifactsHref(buildPromotion);
        return new FilesSubResource((FilesSubResource.Provider)new BuildArtifactsProvider(this, buildPromotion, resolveParameters, logBuildUsage, urlPrefix), urlPrefix, this.myBeanContext, true);
    }

    @NotNull
    private String getBuildFileName(@NotNull BuildPromotion buildPromotion, @NotNull String path) {
        SBuild build;
        if (buildPromotion == null) {
            BuildRequest.$$$reportNull$$$0(9);
        }
        if (path == null) {
            BuildRequest.$$$reportNull$$$0(10);
        }
        if ((build = buildPromotion.getAssociatedBuild()) != null) {
            String string = WebUtil.getFilename((SBuild)build) + this.replaceNonAlphaNum(path);
            if (string == null) {
                BuildRequest.$$$reportNull$$$0(11);
            }
            return string;
        }
        String string = this.replaceNonAlphaNum(buildPromotion.getBuildTypeExternalId() + "_id" + buildPromotion.getId() + path);
        if (string == null) {
            BuildRequest.$$$reportNull$$$0(12);
        }
        return string;
    }

    private String replaceNonAlphaNum(String s) {
        return NON_ALPHA_NUM_PATTERN.matcher(s).replaceAll("_");
    }

    @NotNull
    private String getResolvedIfNecessary(@NotNull BuildPromotion buildPromotion, @Nullable String value, @Nullable Boolean resolveSupported) {
        SBuild build;
        if (buildPromotion == null) {
            BuildRequest.$$$reportNull$$$0(13);
        }
        if ((build = buildPromotion.getAssociatedBuild()) == null || resolveSupported == null || !resolveSupported.booleanValue() || StringUtil.isEmpty((String)value)) {
            String string = value == null ? "" : value;
            if (string == null) {
                BuildRequest.$$$reportNull$$$0(14);
            }
            return string;
        }
        try {
            this.myPermissionChecker.checkPermission(Permission.VIEW_BUILD_RUNTIME_DATA, buildPromotion);
        }
        catch (AuthorizationFailedException e) {
            String withResolvedBuildNumber = StringUtil.replace((String)value, (String)ReferencesResolverUtil.makeReference((String)"build.number"), (String)build.getBuildNumber());
            if (!withResolvedBuildNumber.equals(value) && !build.getValueResolver().resolve(withResolvedBuildNumber).isModified()) {
                String string = withResolvedBuildNumber;
                if (string == null) {
                    BuildRequest.$$$reportNull$$$0(15);
                }
                return string;
            }
            ProcessingResult resolveResult = build.getValueResolver().resolve(value);
            if (!resolveResult.isModified() && resolveResult.isFullyResolved()) {
                String string = value;
                if (string == null) {
                    BuildRequest.$$$reportNull$$$0(16);
                }
                return string;
            }
            this.myPermissionChecker.checkPermission(Permission.VIEW_BUILD_RUNTIME_DATA, buildPromotion);
        }
        ProcessingResult resolveResult = build.getValueResolver().resolve(value);
        String string = resolveResult.getResult();
        if (string == null) {
            BuildRequest.$$$reportNull$$$0(17);
        }
        return string;
    }

    @GET
    @Path(value="/{buildLocator}/sources/files/{fileName:.+}")
    @Produces(value={"application/octet-stream"})
    @ApiOperation(value="Get a source file of the matching build.", nickname="getBuildSourceFile")
    public Response serveSourceFile(@ApiParam(format="BuildLocator") @PathParam(value="buildLocator") String buildLocator, @PathParam(value="fileName") String fileName) {
        byte[] fileContent;
        SBuild build = this.myBuildFinder.getBuild(null, buildLocator);
        try {
            fileContent = ((VcsManager)this.myBeanContext.getSingletonService(VcsManager.class)).getFileContent((Build)build, fileName);
        }
        catch (VcsException e) {
            throw new OperationException("Error while retrieving file content from VCS", (Exception)((Object)e));
        }
        return Response.ok().entity((Object)fileContent).build();
    }

    @GET
    @ApiOperation(value="serveBuildRelatedIssuesOld", hidden=true)
    @Path(value="/{buildLocator}/related-issues")
    @Produces(value={"application/xml", "application/json"})
    public IssueUsages serveBuildRelatedIssuesOld(@ApiParam(format="BuildLocator") @PathParam(value="buildLocator") String buildLocator, @QueryParam(value="fields") String fields) {
        return this.serveBuildRelatedIssues(buildLocator, fields);
    }

    @GET
    @Path(value="/{buildLocator}/relatedIssues")
    @Produces(value={"application/xml", "application/json"})
    @ApiOperation(value="Get related issues of the matching build.", nickname="getBuildRelatedIssues")
    public IssueUsages serveBuildRelatedIssues(@ApiParam(format="BuildLocator") @PathParam(value="buildLocator") String buildLocator, @QueryParam(value="fields") String fields) {
        SBuild build = this.myBuildFinder.getBuild(null, buildLocator);
        return new IssueUsages(build, new Fields(fields), this.myBeanContext);
    }

    @Nullable
    private SBuild getBuild(@NotNull BuildPromotion promotion) {
        SQueuedBuild queuedBuild;
        if (promotion == null) {
            BuildRequest.$$$reportNull$$$0(18);
        }
        return (queuedBuild = promotion.getQueuedBuild()) != null ? null : promotion.getAssociatedBuild();
    }

    @GET
    @Path(value="/{buildLocator}/number")
    @Produces(value={"text/plain"})
    @ApiOperation(value="Get the number of the matching build.", nickname="getBuildNumber")
    public String getBuildNumber(@ApiParam(format="BuildLocator") @PathParam(value="buildLocator") String buildLocator) {
        SBuild build = this.getBuild(this.myBuildFinder.getBuildPromotion(null, buildLocator));
        return build == null ? null : build.getBuildNumber();
    }

    @PUT
    @Path(value="/{buildLocator}/number")
    @Consumes(value={"text/plain"})
    @Produces(value={"text/plain"})
    @ApiOperation(value="Update the number of the matching build.", nickname="setBuildNumber")
    public String setBuildNumber(@ApiParam(format="BuildLocator") @PathParam(value="buildLocator") String buildLocator, String value) {
        SRunningBuild runningBuild = jetbrains.buildServer.server.rest.model.build.Build.getRunningBuild((BuildPromotion)this.myBuildFinder.getBuildPromotion(null, buildLocator), (ServiceLocator)this.myServiceLocator);
        if (runningBuild == null) {
            throw new BadRequestException("Cannot set number for a build which is not running");
        }
        try {
            runningBuild.setBuildNumber(value);
        }
        catch (UnsupportedOperationException e) {
            throw new BadRequestException("Cannot set build number for the build: " + e.getMessage());
        }
        Loggers.ACTIVITIES.info("Build number is changed via REST request by user " + this.myPermissionChecker.getCurrentUserDescription() + ". Build: " + LogUtil.describe((Build)runningBuild));
        return runningBuild.getBuildNumber();
    }

    @GET
    @Path(value="/{buildLocator}/vcsLabels")
    @Produces(value={"application/xml", "application/json"})
    @ApiOperation(value="Get VCS labels of the matching build.", nickname="getBuildVcsLabels")
    public VcsLabels getVcsLabels(@ApiParam(format="BuildLocator") @PathParam(value="buildLocator") String buildLocator, @QueryParam(value="fields") String fields) {
        SBuild build = this.getBuild(this.myBuildFinder.getBuildPromotion(null, buildLocator));
        if (build == null) {
            throw new NotFoundException("Cannot find a build using locator: " + buildLocator);
        }
        VcsLabelManager labelManager = (VcsLabelManager)this.myServiceLocator.getSingletonService(VcsLabelManager.class);
        Fields returnFields = new Fields(fields);
        return new VcsLabels(labelManager.getLabels((Build)build).stream().map(l -> new VcsLabel(l, returnFields, this.myBeanContext)).collect(Collectors.toList()), returnFields);
    }

    @POST
    @Path(value="/{buildLocator}/vcsLabels")
    @Consumes(value={"text/plain"})
    @Produces(value={"application/xml", "application/json"})
    @ApiOperation(value="Add a VCS label to the matching build.", nickname="addBuildVcsLabel")
    public VcsLabels setVcsLabel(@ApiParam(format="BuildLocator") @PathParam(value="buildLocator") String buildLocator, @ApiParam(format="VcsRootInstanceLocator") @QueryParam(value="locator") String vcsRootLocator, @QueryParam(value="fields") String fields, String labelValue) {
        List<Object> roots;
        if (StringUtil.isEmpty((String)labelValue)) {
            throw new BadRequestException("Label can not empty.");
        }
        SBuild build = this.getBuild(this.myBuildFinder.getBuildPromotion(null, buildLocator));
        if (build == null) {
            throw new NotFoundException("Cannot find a build using locator: " + buildLocator);
        }
        VcsLabelManager labelManager = (VcsLabelManager)this.myServiceLocator.getSingletonService(VcsLabelManager.class);
        if (vcsRootLocator == null) {
            roots = build.getVcsRootEntries().stream().map(VcsRootInstanceEntry::getVcsRoot).collect(Collectors.toList());
        } else {
            VcsRootInstanceFinder rootInstanceFinder = (VcsRootInstanceFinder)this.myBeanContext.getSingletonService(VcsRootInstanceFinder.class);
            roots = Collections.singletonList((VcsRootInstance)rootInstanceFinder.getItem(vcsRootLocator));
        }
        try {
            build.setLabel(labelValue, roots);
        }
        catch (VcsException e) {
            LOG.warn("Couldn't set a vcs label.", (Throwable)e);
        }
        Fields returnFields = new Fields(fields);
        return new VcsLabels(labelManager.getLabels((Build)build).stream().filter(l -> l.getLabelText().equals(labelValue)).map(l -> new VcsLabel(l, returnFields, this.myBeanContext)).collect(Collectors.toList()), returnFields);
    }

    @GET
    @Path(value="/{buildLocator}/statusText")
    @Produces(value={"text/plain"})
    @ApiOperation(value="Get the build status text of the matching build.", nickname="getBuildStatusText")
    public String getBuildStatusText(@ApiParam(format="BuildLocator") @PathParam(value="buildLocator") String buildLocator) {
        SBuild build = this.getBuild(this.myBuildFinder.getBuildPromotion(null, buildLocator));
        return build == null ? null : build.getStatusDescriptor().getText();
    }

    @PUT
    @Path(value="/{buildLocator}/statusText")
    @Consumes(value={"text/plain"})
    @Produces(value={"text/plain"})
    @ApiOperation(value="Update the build status of the matching build.", nickname="setBuildStatusText")
    public String setBuildStatusText(@ApiParam(format="BuildLocator") @PathParam(value="buildLocator") String buildLocator, String value) {
        RunningBuildEx runningBuild = (RunningBuildEx)jetbrains.buildServer.server.rest.model.build.Build.getRunningBuild((BuildPromotion)this.myBuildFinder.getBuildPromotion(null, buildLocator), (ServiceLocator)this.myServiceLocator);
        if (runningBuild == null) {
            throw new BadRequestException("Cannot set status text for a build which is not running");
        }
        runningBuild.setCustomStatusText(value);
        Loggers.ACTIVITIES.info("Build status text is changed via REST request by user " + this.myPermissionChecker.getCurrentUserDescription() + ". Build: " + LogUtil.describe((Build)runningBuild));
        return runningBuild.getStatusDescriptor().getText();
    }

    @POST
    @Path(value="/{buildLocator}/status")
    @Consumes(value={"application/xml", "application/json"})
    @Produces(value={"application/xml", "application/json"})
    @ApiOperation(value="Change status of the build.", nickname="setBuildStatus")
    public Response setBuildStatus(@ApiParam(format="BuildLocator") @PathParam(value="buildLocator") String buildLocator, @QueryParam(value="fields") String fields, BuildStatusUpdate statusUpdate, @Context HttpServletRequest request) {
        BuildPromotion build = (BuildPromotion)this.myBuildPromotionFinder.getItem(buildLocator);
        AuthorityHolder authorityHolder = ((SecurityContext)this.myBeanContext.getSingletonService(SecurityContext.class)).getAuthorityHolder();
        ChangeBuildStatusProcessor processor = new ChangeBuildStatusProcessor(build.getAssociatedBuild(), (SUser)authorityHolder.getAssociatedUser(), statusUpdate.status, statusUpdate.comment);
        ActionErrors errors = processor.doChangeStatus();
        if (errors.hasErrors()) {
            ArrayList<String> errorStrings = new ArrayList<String>();
            for (ActionErrors.Error e : errors.getErrors()) {
                errorStrings.add(e.getMessage());
            }
            return Response.status((Response.Status)Response.Status.BAD_REQUEST).entity((Object)new BuildStatusUpdateResult(build, errorStrings, new Fields(fields), this.myBeanContext)).build();
        }
        return Response.ok().entity((Object)new BuildStatusUpdateResult(build, Collections.emptyList(), new Fields(fields), this.myBeanContext)).build();
    }

    @GET
    @Path(value="/{buildLocator}/status")
    @Produces(value={"text/plain"})
    @ApiOperation(value="Get status of the matching build.", nickname="getBuildStatus")
    public String serveBuildStatus(@ApiParam(format="BuildLocator") @PathParam(value="buildLocator") String buildLocator) {
        return jetbrains.buildServer.server.rest.model.build.Build.getFieldValue((BuildPromotion)this.myBuildFinder.getBuildPromotion(null, buildLocator), (String)"status", (BeanContext)this.myBeanContext);
    }

    @GET
    @Path(value="/{buildLocator}/statistics/")
    @Produces(value={"application/xml", "application/json"})
    @ApiOperation(value="Get all statistical values of the matching build.", nickname="getBuildStatisticValues")
    public Properties serveBuildStatisticValues(@ApiParam(format="BuildLocator") @PathParam(value="buildLocator") String buildLocator, @QueryParam(value="fields") String fields) {
        SBuild build = this.myBuildFinder.getBuild(null, buildLocator);
        return new Properties(jetbrains.buildServer.server.rest.model.build.Build.getBuildStatisticsValues((SBuild)build), null, new Fields(fields), this.myBeanContext);
    }

    @GET
    @Path(value="/{buildLocator}/statistics/{name}")
    @Produces(value={"text/plain"})
    @ApiOperation(value="Get a statistical value of the matching build.", nickname="getBuildStatisticValue")
    public String serveBuildStatisticValue(@ApiParam(format="BuildLocator") @PathParam(value="buildLocator") String buildLocator, @PathParam(value="name") String statisticValueName) {
        SBuild build = this.myBuildFinder.getBuild(null, buildLocator);
        return jetbrains.buildServer.server.rest.model.build.Build.getBuildStatisticValue((SBuild)build, (String)statisticValueName);
    }

    @GET
    @Path(value="/{buildLocator}/tags/")
    @Produces(value={"application/xml", "application/json"})
    @ApiOperation(value="Get tags of the matching build.", nickname="getBuildTags")
    public Tags serveTagsFromBuild(@ApiParam(format="BuildLocator") @PathParam(value="buildLocator") String buildLocator, @ApiParam(format="TagLocator") @QueryParam(value="locator") String tagLocator, @QueryParam(value="fields") String fields) {
        BuildPromotion build = this.myBuildFinder.getBuildPromotion(null, buildLocator);
        return new Tags(new TagFinder(this.myUserFinder, build).getItems(tagLocator, TagFinder.getDefaultLocator()).getEntries(), new Fields(fields), this.myBeanContext);
    }

    @PUT
    @Path(value="/{buildLocator}/tags/")
    @Consumes(value={"application/xml", "application/json"})
    @Produces(value={"application/xml", "application/json"})
    @ApiOperation(value="Update tags of the matching build.", nickname="setBuildTags")
    public Tags replaceTagsOnBuild(@ApiParam(format="BuildLocator") @PathParam(value="buildLocator") String buildLocator, @ApiParam(format="TagLocator") @QueryParam(value="locator") String tagLocator, Tags tags, @QueryParam(value="fields") String fields, @Context HttpServletRequest request) {
        BuildPromotion build = this.myBuildFinder.getBuildPromotion(null, buildLocator);
        TagFinder tagFinder = new TagFinder(this.myUserFinder, build);
        TagsManager tagsManager = this.getTagsManager();
        tagsManager.removeTagDatas(build, (Collection)tagFinder.getItems(tagLocator, TagFinder.getDefaultLocator()).getEntries());
        List postedTagDatas = tags.getFromPosted(this.myUserFinder);
        tagsManager.addTagDatas(build, (Collection)postedTagDatas);
        return new Tags(postedTagDatas, new Fields(fields), this.myBeanContext);
    }

    @POST
    @Path(value="/{buildLocator}/tags/")
    @Consumes(value={"application/xml", "application/json"})
    @Produces(value={"application/xml", "application/json"})
    @ApiOperation(value="Add tags to the matching build.", nickname="addTagsToBuild")
    public Tags addTagsToBuild(@ApiParam(format="BuildLocator") @PathParam(value="buildLocator") String buildLocator, Tags tags, @QueryParam(value="fields") String fields) {
        BuildPromotion build = this.myBuildFinder.getBuildPromotion(null, buildLocator);
        TagsManager tagsManager = this.getTagsManager();
        List tagsPosted = tags.getFromPosted(this.myUserFinder);
        tagsManager.addTagDatas(build, (Collection)tagsPosted);
        return new Tags(tagsPosted, new Fields(fields), this.myBeanContext);
    }

    @POST
    @Path(value="/{buildLocator}/tags/")
    @Consumes(value={"text/plain"})
    @Produces(value={"text/plain"})
    @ApiOperation(hidden=true, value="Use addTags instead")
    public String addTag(@ApiParam(format="BuildLocator") @PathParam(value="buildLocator") String buildLocator, String tagName, @Context HttpServletRequest request) {
        if (StringUtil.isEmpty((String)tagName)) {
            throw new BadRequestException("Cannot apply empty tag, should have non empty request body");
        }
        BuildPromotion build = this.myBuildFinder.getBuildPromotion(null, buildLocator);
        TagsManager tagsManager = this.getTagsManager();
        tagsManager.addTagDatas(build, Collections.singleton(TagData.createPublicTag((String)tagName)));
        return tagName;
    }

    @GET
    @Path(value="/{buildLocator}/pinInfo/")
    @Produces(value={"application/xml", "application/json"})
    @ApiOperation(value="Check if the matching build is pinned.", nickname="getBuildPinInfo")
    public PinInfo getPinData(@ApiParam(format="BuildLocator") @PathParam(value="buildLocator") String buildLocator, @QueryParam(value="fields") String fields, @Context HttpServletRequest request) {
        SBuild build = ((BuildPromotion)this.myBuildPromotionFinder.getItem(buildLocator)).getAssociatedBuild();
        return new PinInfo(build, new Fields(fields), this.myBeanContext);
    }

    @PUT
    @Path(value="/{buildLocator}/pinInfo/")
    @Consumes(value={"application/xml", "application/json"})
    @Produces(value={"application/xml", "application/json"})
    @ApiOperation(value="Update the pin info of the matching build.", nickname="setBuildPinInfo")
    public PinInfo setBuildPinData(@ApiParam(format="BuildLocator") @PathParam(value="buildLocator") String buildLocator, PinInfo pinStatus, @QueryParam(value="fields") String fields, @Context HttpServletRequest request) {
        Boolean newStatus = pinStatus.getStatusFromPosted();
        if (newStatus == null) {
            throw new BadRequestException("Pin status should be specified in the payload");
        }
        BuildPromotion buildPromotion = (BuildPromotion)this.myBuildPromotionFinder.getItem(buildLocator);
        this.pinBuild(buildPromotion, SessionUser.getUser((HttpServletRequest)request), pinStatus.getCommentTextFromPosted(), newStatus);
        SBuild build = buildPromotion.getAssociatedBuild();
        return new PinInfo(build, new Fields(fields), this.myBeanContext);
    }

    @GET
    @ApiOperation(value="getPinned", hidden=true)
    @Path(value="/{buildLocator}/pin/")
    @Produces(value={"text/plain"})
    public String getPinned(@ApiParam(format="BuildLocator") @PathParam(value="buildLocator") String buildLocator, @Context HttpServletRequest request) {
        SBuild build = this.myBuildFinder.getBuild(null, buildLocator);
        return Boolean.toString(build.isPinned());
    }

    @PUT
    @ApiOperation(value="pinBuild", hidden=true)
    @Path(value="/{buildLocator}/pin/")
    @Consumes(value={"text/plain"})
    public void pinBuild(@ApiParam(format="BuildLocator") @PathParam(value="buildLocator") String buildLocator, String comment, @Context HttpServletRequest request) {
        SBuild build = this.myBuildFinder.getBuild(null, buildLocator);
        this.pinBuild(build.getBuildPromotion(), SessionUser.getUser((HttpServletRequest)request), comment, true);
    }

    private void pinBuild(@NotNull BuildPromotion buildPromotion, @Nullable SUser user, @Nullable String comment, boolean newPinState) {
        SBuild build;
        if (buildPromotion == null) {
            BuildRequest.$$$reportNull$$$0(19);
        }
        if (!((build = buildPromotion.getAssociatedBuild()) instanceof SFinishedBuild)) {
            throw new BadRequestException("Cannot " + (newPinState ? "pin" : "unpin") + " build that is not finished.");
        }
        ((SFinishedBuild)build).setPinned(newPinState, (User)user, comment);
    }

    @DELETE
    @ApiOperation(value="unpinBuild", hidden=true)
    @Path(value="/{buildLocator}/pin/")
    @Consumes(value={"text/plain"})
    public void unpinBuild(@ApiParam(format="BuildLocator") @PathParam(value="buildLocator") String buildLocator, String comment, @Context HttpServletRequest request) {
        SBuild build = this.myBuildFinder.getBuild(null, buildLocator);
        this.pinBuild(build.getBuildPromotion(), SessionUser.getUser((HttpServletRequest)request), comment, false);
    }

    @PUT
    @Path(value="/{buildLocator}/comment")
    @Consumes(value={"text/plain"})
    @ApiOperation(value="Update the comment on the matching build.", nickname="setBuildComment")
    public void replaceComment(@ApiParam(format="BuildLocator") @PathParam(value="buildLocator") String buildLocator, String text, @Context HttpServletRequest request) {
        BuildPromotion build = this.myBuildFinder.getBuildPromotion(null, buildLocator);
        SUser user = SessionUser.getUser((HttpServletRequest)request);
        this.setBuildComment(build, text, user);
    }

    @DELETE
    @Path(value="/{buildLocator}/comment")
    @ApiOperation(value="Remove the build comment matching the locator.", nickname="deleteBuildComment")
    public void deleteComment(@ApiParam(format="BuildLocator") @PathParam(value="buildLocator") String buildLocator, @Context HttpServletRequest request) {
        BuildPromotion build = this.myBuildFinder.getBuildPromotion(null, buildLocator);
        SUser user = SessionUser.getUser((HttpServletRequest)request);
        this.setBuildComment(build, null, user);
    }

    private void setBuildComment(@NotNull BuildPromotion build, @Nullable String text, @Nullable SUser user) {
        if (build == null) {
            BuildRequest.$$$reportNull$$$0(20);
        }
        if (user == null) {
            throw new BadRequestException("Cannot change comment when there is no current user");
        }
        build.setBuildComment((User)user, text);
    }

    @GET
    @Path(value="/{buildLocator}/canceledInfo")
    @Produces(value={"application/xml", "application/json"})
    @ApiOperation(value="Check if the matching build is canceled.", nickname="getCanceledInfo")
    public Comment getCanceledInfo(@ApiParam(format="BuildLocator") @PathParam(value="buildLocator") String buildLocator, @QueryParam(value="fields") String fields) {
        SBuild build = this.myBuildFinder.getBuild(null, buildLocator);
        return jetbrains.buildServer.server.rest.model.build.Build.getCanceledComment((SBuild)build, (Fields)new Fields(fields), (BeanContext)this.myBeanContext);
    }

    @GET
    @ApiOperation(value="getBuildCancelRequest", hidden=true)
    @Path(value="/{buildLocator}/example/buildCancelRequest")
    @Produces(value={"application/xml", "application/json"})
    public BuildCancelRequest getBuildCancelRequest(@ApiParam(format="BuildLocator") @PathParam(value="buildLocator") String buildLocator, @Context HttpServletRequest request) {
        return new BuildCancelRequest("example build cancel comment", false);
    }

    @GET
    @Path(value="/{buildLocator}/problemOccurrences")
    @Produces(value={"application/xml", "application/json"})
    @ApiOperation(value="Get build problems of the matching build.", nickname="getBuildProblems")
    public ProblemOccurrences getProblems(@ApiParam(format="BuildLocator") @PathParam(value="buildLocator") String buildLocator, @QueryParam(value="fields") String fields) {
        BuildPromotion build = this.myBuildFinder.getBuildPromotion(null, buildLocator);
        List buildProblems = ((BuildPromotionEx)build).getBuildProblems();
        return new ProblemOccurrences(buildProblems, ProblemOccurrenceRequest.getHref((BuildPromotion)build), null, new Fields(fields), this.myBeanContext);
    }

    @POST
    @Path(value="/{buildLocator}/problemOccurrences")
    @Consumes(value={"text/plain"})
    @Produces(value={"application/xml", "application/json"})
    @ApiOperation(value="Add a build problem to the matching build.", nickname="addProblemToBuild")
    public ProblemOccurrence addProblemToBuild(@ApiParam(format="BuildLocator") @PathParam(value="buildLocator") String buildLocator, String problemDetails, @QueryParam(value="fields") String fields) {
        BuildPromotion buildPromotion = this.myBuildFinder.getBuildPromotion(null, buildLocator);
        SBuild build = buildPromotion.getAssociatedBuild();
        if (build == null) {
            throw new NotFoundException("No finished build associated with promotion id " + buildPromotion.getId());
        }
        User user = this.myPermissionChecker.getCurrent().getAssociatedUser();
        if (user == null) {
            throw new BadRequestException("Cannot perform operation: no current user");
        }
        BuildProblemData problemData = build.addUserBuildProblem((SUser)user, problemDetails);
        return new ProblemOccurrence(((ProblemOccurrenceFinder)this.myBeanContext.getSingletonService(ProblemOccurrenceFinder.class)).getProblem(build, problemData), null, this.myBeanContext, new Fields(fields));
    }

    @POST
    @Path(value="/{buildLocator}/problemOccurrences")
    @Consumes(value={"application/xml", "application/json"})
    @Produces(value={"application/xml", "application/json"})
    @ApiOperation(value="addProblem", nickname="addProblem", hidden=true)
    public ProblemOccurrence addProblem(@ApiParam(format="BuildLocator") @PathParam(value="buildLocator") String buildLocator, ProblemOccurrence problemOccurrence, @QueryParam(value="fields") String fields) {
        BuildPromotion buildPromotion = this.myBuildFinder.getBuildPromotion(null, buildLocator);
        this.checkBuildOperationPermission(buildPromotion);
        SBuild build = buildPromotion.getAssociatedBuild();
        if (build == null) {
            throw new NotFoundException("No finished build associated with promotion id " + buildPromotion.getId());
        }
        BuildProblemData problemDetails = problemOccurrence.getFromPosted(this.myServiceLocator);
        build.addBuildProblem(problemDetails);
        return new ProblemOccurrence(((ProblemOccurrenceFinder)this.myBeanContext.getSingletonService(ProblemOccurrenceFinder.class)).getProblem(build, problemDetails), null, this.myBeanContext, new Fields(fields));
    }

    @GET
    @Path(value="/{buildLocator}/testOccurrences")
    @Produces(value={"application/xml", "application/json"})
    @ApiOperation(value="Get test occurrences of the matching build.", nickname="getBuildTestOccurrences")
    public TestOccurrences getTests(@ApiParam(format="BuildLocator") @PathParam(value="buildLocator") String buildLocator, @QueryParam(value="fields") String fields) {
        SBuild build = this.myBuildFinder.getBuild(null, buildLocator);
        BuildStatistics statistics = TestOccurrenceFinder.getBuildStatistics((SBuild)build, null);
        List tests = statistics.getAllTests();
        return new TestOccurrences((Collection)tests, (ShortStatistics)statistics, TestOccurrenceRequest.getHref((SBuild)build), null, new Fields(fields), this.myBeanContext);
    }

    @GET
    @Path(value="/{buildLocator}/artifactsDirectory")
    @Produces(value={"text/plain"})
    @ApiOperation(value="Get the artifacts' directory of the matching build.", nickname="getArtifactsDirectory")
    public String getArtifactsDirectory(@ApiParam(format="BuildLocator") @PathParam(value="buildLocator") String buildLocator) {
        this.myPermissionChecker.checkGlobalPermission(Permission.VIEW_SERVER_SETTINGS);
        BuildPromotion build = this.myBuildFinder.getBuildPromotion(null, buildLocator);
        return build.getArtifactsDirectory().getAbsolutePath();
    }

    @GET
    @Path(value="/{buildLocator}/artifactDependencyChanges")
    @Produces(value={"application/xml", "application/json"})
    @ApiOperation(value="Get artifact dependency changes of the matching build.", nickname="getArtifactDependencyChanges")
    public BuildChanges getArtifactDependencyChanges(@ApiParam(format="BuildLocator") @PathParam(value="buildLocator") String buildLocator, @QueryParam(value="fields") String fields) {
        BuildPromotion build = this.myBuildFinder.getBuildPromotion(null, buildLocator);
        return jetbrains.buildServer.server.rest.model.build.Build.getArtifactDependencyChangesNode((BuildPromotion)build, (Fields)new Fields(fields), (BeanContext)this.myBeanContext);
    }

    @POST
    @Path(value="/{buildLocator}")
    @Consumes(value={"application/xml", "application/json"})
    @Produces(value={"application/xml", "application/json"})
    @ApiOperation(value="cancelBuild")
    public jetbrains.buildServer.server.rest.model.build.Build cancelBuild(@ApiParam(format="BuildLocator") @PathParam(value="buildLocator") String buildLocator, BuildCancelRequest cancelRequest, @QueryParam(value="fields") String fields, @Context HttpServletRequest request) {
        SUser currentUser = SessionUser.getUser((HttpServletRequest)request);
        BuildPromotion build = this.myBuildFinder.getBuildPromotion(null, buildLocator);
        LinkedHashMap<Long, RuntimeException> errors = this.cancelBuilds(Collections.singletonList(build), cancelRequest, currentUser);
        if (!errors.isEmpty()) {
            throw errors.entrySet().iterator().next().getValue();
        }
        SBuild associatedBuild = build.getAssociatedBuild();
        if (associatedBuild == null) {
            return null;
        }
        return new jetbrains.buildServer.server.rest.model.build.Build(associatedBuild, new Fields(fields), this.myBeanContext);
    }

    private void restoreInQueue(SRunningBuild runningBuild, User user) {
        SAgentRestrictor agentRestrictor = ((RunningBuildEx)runningBuild).getQueuedAgentRestrictor();
        TriggeredBy origTriggeredBy = runningBuild.getTriggeredBy();
        BuildPromotionEx promotionEx = (BuildPromotionEx)runningBuild.getBuildPromotion();
        TriggeredByBuilder tbb = new TriggeredByBuilder();
        tbb.addParameters(origTriggeredBy.getParameters());
        tbb.addParameter("reAddedAfterStop", String.valueOf(user.getId()));
        tbb.addParameter("reAddedInsteadOf", String.valueOf(runningBuild.getBuildId()));
        tbb.addParameter("type", "reAddedOnStop");
        tbb.addParameter("origin", "rest");
        ((BuildQueueEx)this.myBeanContext.getSingletonService(BuildQueueEx.class)).restoreInQueue(promotionEx, agentRestrictor, tbb.toString());
    }

    @DELETE
    @Path(value="/{buildLocator}")
    @ApiOperation(value="Delete build matching the locator.", nickname="deleteBuild")
    public void deleteBuild(@ApiParam(format="BuildLocator") @PathParam(value="buildLocator") String buildLocator, @Context HttpServletRequest request) {
        BuildPromotion build = this.myBuildFinder.getBuildPromotion(null, buildLocator);
        Map<Long, RuntimeException> errors = this.deleteBuilds(Collections.singletonList(build), SessionUser.getUser((HttpServletRequest)request), null);
        if (!errors.isEmpty()) {
            throw errors.get(build.getId());
        }
    }

    private Map<Long, RuntimeException> deleteBuilds(@NotNull List<BuildPromotion> builds, @Nullable SUser user, @Nullable String comment) {
        if (builds == null) {
            BuildRequest.$$$reportNull$$$0(21);
        }
        BuildHistoryEx buildHistory = (BuildHistoryEx)this.myBeanContext.getSingletonService(BuildHistory.class);
        LinkedHashMap<Long, RuntimeException> errors = new LinkedHashMap<Long, RuntimeException>(this.cancelBuilds(builds.stream().filter(buildPromotion -> {
            SBuild build = buildPromotion.getAssociatedBuild();
            return build == null || !build.isFinished();
        }).collect(Collectors.toList()), new BuildCancelRequest(comment, false), user));
        for (BuildPromotion build : builds) {
            SBuild associatedBuild = build.getAssociatedBuild();
            if (associatedBuild != null) {
                if (associatedBuild.isFinished()) {
                    errors.remove(build.getId());
                    try {
                        if (associatedBuild instanceof SFinishedBuild) {
                            buildHistory.removeEntry((SFinishedBuild)associatedBuild, comment);
                            continue;
                        }
                        buildHistory.removeEntry(associatedBuild.getBuildId(), comment);
                    }
                    catch (RuntimeException e) {
                        if (build.getAssociatedBuild() == null) continue;
                        errors.putIfAbsent(build.getId(), e);
                    }
                    continue;
                }
                errors.putIfAbsent(build.getId(), (RuntimeException)new OperationException("Failed to delete running build"));
                continue;
            }
            if (build.getQueuedBuild() == null) continue;
            errors.putIfAbsent(build.getId(), (RuntimeException)((Object)new AuthorizationFailedException("Failed to delete queued build. Probably not sufficient permissions.")));
        }
        return errors;
    }

    @NotNull
    private LinkedHashMap<Long, RuntimeException> cancelBuilds(@NotNull List<BuildPromotion> builds, @NotNull BuildCancelRequest cancelRequest, @Nullable SUser currentUser) {
        if (builds == null) {
            BuildRequest.$$$reportNull$$$0(22);
        }
        if (cancelRequest == null) {
            BuildRequest.$$$reportNull$$$0(23);
        }
        LinkedHashMap<Long, RuntimeException> errors = new LinkedHashMap<Long, RuntimeException>();
        if (cancelRequest.readdIntoQueue && currentUser == null) {
            throw new BadRequestException("Cannot re-add build into queue when no current user is present. Please make sure the operation is performed under a regular user.");
        }
        BuildQueueEx buildQueue = (BuildQueueEx)this.myBeanContext.getSingletonService(BuildQueue.class);
        Set queuedBuildIds = builds.stream().map(BuildPromotion::getQueuedBuild).filter(Objects::nonNull).map(QueuedBuild::getItemId).collect(Collectors.toSet());
        if (!queuedBuildIds.isEmpty()) {
            buildQueue.removeItems(queuedBuildIds, currentUser, cancelRequest.comment);
        }
        ArrayList<SRunningBuild> stoppedBuilds = new ArrayList<SRunningBuild>();
        RunningBuildsManager runningBuildsManager = (RunningBuildsManager)this.myBeanContext.getSingletonService(RunningBuildsManager.class);
        for (BuildPromotion build2 : builds) {
            SBuild sBuild = build2.getAssociatedBuild();
            if (sBuild == null) {
                if (build2.getQueuedBuild() == null) continue;
                if (currentUser != null && !AuthUtil.hasPermissionToStopBuild((AuthorityHolder)currentUser, (BuildPromotion)build2)) {
                    errors.put(build2.getId(), (RuntimeException)((Object)new AuthorizationFailedException("You do not have enough permissions to cancel the build")));
                    continue;
                }
                errors.putIfAbsent(build2.getId(), (RuntimeException)new OperationException("Failed to cancel queued build"));
                continue;
            }
            if (sBuild.isFinished()) {
                if (sBuild.getCanceledInfo() != null) continue;
                errors.put(build2.getId(), (RuntimeException)new BadRequestException("Cannot cancel finished build"));
                continue;
            }
            SRunningBuild runningBuild = runningBuildsManager.findRunningBuildById(sBuild.getBuildId());
            if (runningBuild == null) {
                errors.put(build2.getId(), (RuntimeException)new BadRequestException("Cannot cancel not running build"));
                continue;
            }
            try {
                String comment;
                if (cancelRequest.readdIntoQueue) {
                    stoppedBuilds.add(runningBuild);
                }
                String string = comment = cancelRequest.comment == null ? "Canceled via REST API" : cancelRequest.comment;
                if (!cancelRequest.readdIntoQueue && currentUser == null) {
                    ((RunningBuildEx)runningBuild).stop(RunningBuildState.INTERRUPTED_BY_USER, null, comment, cancelRequest.readdIntoQueue);
                    continue;
                }
                runningBuild.stop((User)currentUser, comment);
            }
            catch (RuntimeException e) {
                errors.putIfAbsent(build2.getId(), e);
            }
        }
        if (cancelRequest.readdIntoQueue && !stoppedBuilds.isEmpty()) {
            stoppedBuilds.forEach(build -> {
                try {
                    this.restoreInQueue((SRunningBuild)build, (User)currentUser);
                }
                catch (RuntimeException e) {
                    errors.putIfAbsent(build.getBuildPromotion().getId(), e);
                }
            });
        }
        LinkedHashMap<Long, RuntimeException> linkedHashMap = errors;
        if (linkedHashMap == null) {
            BuildRequest.$$$reportNull$$$0(24);
        }
        return linkedHashMap;
    }

    @GET
    @Path(value="/multiple/{buildLocator}")
    @Produces(value={"application/xml", "application/json"})
    @ApiOperation(value="Get multiple builds matching the locator.", nickname="getMultipleBuilds")
    public Builds getMultiple(@ApiParam(format="BuildLocator") @PathParam(value="buildLocator") String buildLocator, @QueryParam(value="fields") String fields, @Context UriInfo uriInfo, @Context HttpServletRequest request) {
        PagedSearchResult pagedResult = this.myBuildPromotionFinder.getItems(buildLocator);
        UriBuilder uriBuilder = uriInfo.getRequestUriBuilder();
        UriBuilder mainRequestUriBuilder = uriBuilder.replacePath(uriBuilder.build(new Object[0]).getPath().replace("/multiple/" + buildLocator, "")).queryParam("locator", new Object[]{buildLocator});
        PagerDataImpl pagerData = new PagerDataImpl(mainRequestUriBuilder, request.getContextPath(), pagedResult, buildLocator, "locator");
        return Builds.createFromPrefilteredBuildPromotions((List)pagedResult.getEntries(), (PagerData)pagerData, (Fields)new Fields(fields), (BeanContext)this.myBeanContext);
    }

    @DELETE
    @Path(value="/multiple/{buildLocator}")
    @Produces(value={"application/xml", "application/json"})
    @ApiOperation(value="Delete multiple builds matching the locator.", nickname="deleteMultipleBuilds")
    public MultipleOperationResult deleteMultiple(@ApiParam(format="BuildLocator") @PathParam(value="buildLocator") String buildLocator, @QueryParam(value="fields") String fields, @Context HttpServletRequest request) {
        if (buildLocator == null) {
            throw new BadRequestException("Empty locator specified.");
        }
        List builds = this.myBuildPromotionFinder.getItems(buildLocator).getEntries();
        return new MultipleOperationResult(this.getResultData(builds, this.deleteBuilds(builds, SessionUser.getUser((HttpServletRequest)request), null)), new Fields(fields), this.myBeanContext);
    }

    @NotNull
    private MultipleOperationResult.Data getResultData(@NotNull List<BuildPromotion> allBuilds, @NotNull Map<Long, RuntimeException> reportedErrors) {
        if (allBuilds == null) {
            BuildRequest.$$$reportNull$$$0(25);
        }
        if (reportedErrors == null) {
            BuildRequest.$$$reportNull$$$0(26);
        }
        int[] errorsCount = new int[]{0};
        return new MultipleOperationResult.Data(allBuilds.stream().map(build -> {
            RuntimeException exception = (RuntimeException)reportedErrors.get(build.getId());
            if (exception == null) {
                return OperationResult.Data.createSuccess((RelatedEntity.Entity)new RelatedEntity.Entity(build));
            }
            errorsCount[0] = errorsCount[0] + 1;
            return OperationResult.Data.createError((String)exception.getMessage(), (RelatedEntity.Entity)new RelatedEntity.Entity(build));
        }).collect(Collectors.toList()), errorsCount[0]);
    }

    @PUT
    @Path(value="/multiple/{buildLocator}/pinInfo/")
    @Consumes(value={"application/xml", "application/json"})
    @Produces(value={"application/xml", "application/json"})
    @ApiOperation(value="Pin multiple matching builds.", nickname="pinMultipleBuilds")
    public MultipleOperationResult pinMultiple(@ApiParam(format="BuildLocator") @PathParam(value="buildLocator") String buildLocator, PinInfo pinStatus, @QueryParam(value="fields") String fields, @Context HttpServletRequest request) {
        Boolean newStatus = pinStatus.getStatusFromPosted();
        if (newStatus == null) {
            throw new BadRequestException("Pin status should be specified in the payload");
        }
        String commentText = pinStatus.getCommentTextFromPosted();
        SUser user = SessionUser.getUser((HttpServletRequest)request);
        return this.processMultiple(buildLocator, build -> this.pinBuild((BuildPromotion)build, user, commentText, newStatus), new Fields(fields));
    }

    @POST
    @Path(value="/multiple/{buildLocator}/tags/")
    @Consumes(value={"application/xml", "application/json"})
    @Produces(value={"application/xml", "application/json"})
    @ApiOperation(value="Add tags to multiple matching builds.", nickname="addTagsToMultipleBuilds")
    public MultipleOperationResult addTagsMultipleToBuild(@ApiParam(format="BuildLocator") @PathParam(value="buildLocator") String buildLocator, Tags tags, @QueryParam(value="fields") String fields) {
        TagsManager tagsManager = this.getTagsManager();
        List tagsPosted = tags.getFromPosted(this.myUserFinder);
        return this.processMultiple(buildLocator, build -> tagsManager.addTagDatas(build, (Collection)tagsPosted), new Fields(fields));
    }

    @DELETE
    @Path(value="/multiple/{buildLocator}/tags/")
    @Consumes(value={"application/xml", "application/json"})
    @Produces(value={"application/xml", "application/json"})
    @ApiOperation(value="Remove tags from multiple matching builds.", nickname="removeMultipleBuildTags")
    public MultipleOperationResult removeTagsMultiple(@ApiParam(format="BuildLocator") @PathParam(value="buildLocator") String buildLocator, Tags tags, @QueryParam(value="fields") String fields) {
        TagsManager tagsManager = this.getTagsManager();
        List tagsPosted = tags.getFromPosted(this.myUserFinder);
        return this.processMultiple(buildLocator, build -> tagsManager.removeTagDatas(build, (Collection)tagsPosted), new Fields(fields));
    }

    @PUT
    @Path(value="/multiple/{buildLocator}/comment")
    @Consumes(value={"text/plain"})
    @Produces(value={"application/xml", "application/json"})
    @ApiOperation(value="Update comments in multiple matching builds.", nickname="setMultipleBuildComments")
    public MultipleOperationResult replaceCommentMultiple(@ApiParam(format="BuildLocator") @PathParam(value="buildLocator") String buildLocator, String text, @QueryParam(value="fields") String fields, @Context HttpServletRequest request) {
        SUser user = SessionUser.getUser((HttpServletRequest)request);
        return this.processMultiple(buildLocator, build -> this.setBuildComment((BuildPromotion)build, text, user), new Fields(fields));
    }

    @DELETE
    @Path(value="/multiple/{buildLocator}/comment")
    @Produces(value={"application/xml", "application/json"})
    @ApiOperation(value="Delete comments of multiple matching builds.", nickname="deleteMultipleBuildComments")
    public MultipleOperationResult deleteCommentMultiple(@ApiParam(format="BuildLocator") @PathParam(value="buildLocator") String buildLocator, @QueryParam(value="fields") String fields, @Context HttpServletRequest request) {
        SUser user = SessionUser.getUser((HttpServletRequest)request);
        return this.processMultiple(buildLocator, build -> this.setBuildComment((BuildPromotion)build, null, user), new Fields(fields));
    }

    @POST
    @Path(value="/multiple/{buildLocator}")
    @Consumes(value={"application/xml", "application/json"})
    @Produces(value={"application/xml", "application/json"})
    @ApiOperation(value="cancelMultipleBuilds")
    public MultipleOperationResult cancelMultiple(@ApiParam(format="BuildLocator") @PathParam(value="buildLocator") String buildLocator, BuildCancelRequest cancelRequest, @QueryParam(value="fields") String fields, @Context HttpServletRequest request) {
        if (buildLocator == null) {
            throw new BadRequestException("Empty locator specified.");
        }
        List builds = this.myBuildPromotionFinder.getItems(buildLocator).getEntries();
        return new MultipleOperationResult(this.getResultData(builds, this.cancelBuilds(builds, cancelRequest, SessionUser.getUser((HttpServletRequest)request))), new Fields(fields), this.myBeanContext);
    }

    @NotNull
    private MultipleOperationResult processMultiple(@Nullable String buildLocator, @NotNull Consumer<BuildPromotion> action, @NotNull Fields fields) {
        if (action == null) {
            BuildRequest.$$$reportNull$$$0(27);
        }
        if (fields == null) {
            BuildRequest.$$$reportNull$$$0(28);
        }
        return new MultipleOperationResult(MultipleOperationResult.Data.process((String)buildLocator, (Finder)this.myBuildPromotionFinder, action), fields, this.myBeanContext);
    }

    @GET
    @Path(value="/{buildLocator}/statusIcon{suffix:(.*)?}")
    @ApiOperation(value="Get the status icon (in specified format) of the matching build.", nickname="getBuildStatusIcon")
    public Response serveBuildStatusIcon(@ApiParam(format="BuildLocator") @PathParam(value="buildLocator") String buildLocator, @PathParam(value="suffix") String suffix, @Context HttpServletRequest request) {
        try {
            StatusIconLocatorValidator.validate((String)buildLocator);
        }
        catch (BadRequestException e) {
            return this.processIconRequest(BuildIconStatus.INTERNAL_ERROR.getIconName(), suffix, request);
        }
        BuildIconStatus stateName = this.getStatus(buildLocator);
        return this.processIconRequest(stateName.getIconName(), suffix, request);
    }

    @GET
    @Path(value="/aggregated/{buildLocator}/statusIcon{suffix:(.*)?}")
    @ApiOperation(value="Get the status icon (in specified format) of aggregated matching builds.", nickname="getAggregatedBuildStatusIcon")
    public Response serveAggregatedBuildStatusIcon(@ApiParam(format="BuildLocator") @PathParam(value="buildLocator") String locator, @PathParam(value="suffix") String suffix, @Context HttpServletRequest request) {
        try {
            StatusIconLocatorValidator.validate((String)locator);
        }
        catch (BadRequestException e) {
            return this.processIconRequest(BuildIconStatus.INTERNAL_ERROR.getIconName(), suffix, request);
        }
        BuildIconStatus stateName = this.getAggregatedStatus(locator);
        return this.processIconRequest(stateName.getIconName(), suffix, request);
    }

    @GET
    @Path(value="/aggregated/{buildLocator}/status")
    @ApiOperation(value="Get the build status of aggregated matching builds.", nickname="getAggregatedBuildStatus")
    public String serveAggregatedBuildStatus(@ApiParam(format="BuildLocator") @PathParam(value="buildLocator") String locator) {
        PagedSearchResult builds = this.myBuildPromotionFinder.getItems(locator);
        Status resultingStatus = Status.UNKNOWN;
        for (BuildPromotion buildPromotion : builds.getEntries()) {
            SBuild build = buildPromotion.getAssociatedBuild();
            if (build == null) continue;
            Status status = build.getStatusDescriptor().getStatus();
            resultingStatus = Status.getWorstStatus((Status)resultingStatus, (Status)status);
        }
        return resultingStatus.getText();
    }

    @ApiOperation(value="serveAggregatedBuildArtifacts", hidden=true)
    @Path(value="/aggregated/{buildLocator}/artifacts")
    public FilesSubResource serveAggregatedBuildArtifacts(@ApiParam(format="BuildLocator") @PathParam(value="buildLocator") String locator, @QueryParam(value="logBuildUsage") Boolean logBuildUsage) {
        if (logBuildUsage != null && logBuildUsage.booleanValue()) {
            throw new BadRequestException("Logging usage of the artifacts is not supported for aggregated build request");
        }
        PagedSearchResult builds = this.myBuildPromotionFinder.getItems(locator);
        String urlPrefix = Util.concatenatePath((String[])new String[]{this.myBeanContext.getApiUrlBuilder().transformRelativePath("/app/rest/builds"), "/aggregated", locator, "/artifacts"});
        return new FilesSubResource((FilesSubResource.Provider)new /* Unavailable Anonymous Inner Class!! */, urlPrefix, this.myBeanContext, true);
    }

    @PUT
    @Path(value="/{buildLocator}/runningData")
    @Consumes(value={"text/plain"})
    @Produces(value={"application/xml", "application/json"})
    @ApiOperation(value="Starts the queued build as an agent-less build and returns the corresponding running build.", nickname="markBuildAsRunning")
    public jetbrains.buildServer.server.rest.model.build.Build markBuildAsRunning(@ApiParam(format="BuildLocator") @PathParam(value="buildLocator") String buildLocator, String requestor, @QueryParam(value="fields") String fields) {
        BuildPromotion buildPromotion = this.myBuildPromotionFinder.getBuildPromotion(null, buildLocator);
        this.checkBuildOperationPermission(buildPromotion);
        QueuedBuildEx build = (QueuedBuildEx)buildPromotion.getQueuedBuild();
        if (build != null) {
            try {
                build.startBuild(requestor);
            }
            catch (IllegalStateException e) {
                throw new BadRequestException("Error on attempt to mark the build as running: " + e.getMessage(), (Throwable)e);
            }
        } else {
            throw new BadRequestException("Build with promotion id " + buildPromotion.getId() + " is not a queued build");
        }
        return new jetbrains.buildServer.server.rest.model.build.Build(buildPromotion, new Fields(fields), this.myBeanContext);
    }

    @POST
    @Path(value="/{buildLocator}/log")
    @Consumes(value={"text/plain"})
    @ApiOperation(value="Adds a message to the build log. Service messages are accepted.", nickname="addLogMessageToBuild")
    public void addLogMessage(@ApiParam(format="BuildLocator") @PathParam(value="buildLocator") String buildLocator, String logLines, @QueryParam(value="fields") String fields) {
        BuildPromotion buildPromotion = this.myBuildPromotionFinder.getBuildPromotion(null, buildLocator);
        this.checkBuildOperationPermission(buildPromotion);
        SBuild build = buildPromotion.getAssociatedBuild();
        if (build == null) {
            throw new NotFoundException("Build with id " + buildPromotion.getId() + " is not in the running or finished state");
        }
        this.logMessage(build, logLines);
    }

    private void logMessage(@NotNull SBuild build, String lines) {
        if (build == null) {
            BuildRequest.$$$reportNull$$$0(29);
        }
        if (build.isFinished() || !(build instanceof RunningBuildEx)) {
            throw new NotFoundException("Build with id " + build.getBuildId() + " is already finished");
        }
        RunningBuildEx runningBuild = (RunningBuildEx)build;
        try {
            ((BuildAgentMessagesQueue)this.myBeanContext.getSingletonService(BuildAgentMessagesQueue.class)).processMessages(runningBuild, Collections.singletonList(DefaultMessagesInfo.createTextMessage((String)lines).updateTags("tc:rest")));
        }
        catch (InterruptedException e) {
            throw new OperationException("Got interrupted", (Exception)e);
        }
        catch (BuildAgentMessagesQueue.BuildMessagesQueueFullException e) {
            throw new OperationException("Failed to add messages as the queue is full", (Exception)((Object)e));
        }
    }

    @PUT
    @Path(value="/{buildLocator}/finish")
    @Consumes(value={"text/plain"})
    @Produces(value={"text/plain"})
    @ApiOperation(value="Marks the running build as finished by passing agent the current time of the build to finish.")
    public String setFinishedTime(@ApiParam(format="BuildLocator") @PathParam(value="buildLocator") String buildLocator) {
        return this.finishBuild(buildLocator, new Date());
    }

    @PUT
    @Path(value="/{buildLocator}/finishDate")
    @Consumes(value={"text/plain"})
    @Produces(value={"text/plain"})
    @ApiOperation(value="Marks the running build as finished by passing agent the current time of the build to finish.", nickname="setBuildFinishDate")
    public String setFinishedTime(@ApiParam(format="BuildLocator") @PathParam(value="buildLocator") String buildLocator, String date) {
        if (StringUtil.isEmpty((String)date)) {
            throw new BadRequestException("Body should contain finish date");
        }
        TimeService timeService = (TimeService)this.myServiceLocator.getSingletonService(TimeService.class);
        Date finishTime = !StringUtil.isEmpty((String)date) ? TimeWithPrecision.parse((String)date, (TimeService)timeService).getTime() : new Date(timeService.now());
        return this.finishBuild(buildLocator, finishTime);
    }

    @NotNull
    private String finishBuild(@NotNull String buildLocator, @NotNull Date finishTime) {
        if (buildLocator == null) {
            BuildRequest.$$$reportNull$$$0(30);
        }
        if (finishTime == null) {
            BuildRequest.$$$reportNull$$$0(31);
        }
        BuildPromotion buildPromotion = this.myBuildPromotionFinder.getBuildPromotion(null, buildLocator);
        this.checkBuildOperationPermission(buildPromotion);
        SBuild build = buildPromotion.getAssociatedBuild();
        if (build == null) {
            throw new NotFoundException("Build with id " + buildPromotion.getId() + " is not in the running or finished state");
        }
        if (build.isFinished() || !(build instanceof RunningBuildEx)) {
            throw new NotFoundException("Build with id " + buildPromotion.getId() + " is already finished");
        }
        this.logMessage(build, "Build finish request received via REST endpoint" + (build.isDetachedFromAgent() ? ". Finishing detached build." : ""));
        RunningBuildEx runningBuild = (RunningBuildEx)build;
        ((BuildAgentMessagesQueue)this.myBeanContext.getSingletonService(BuildAgentMessagesQueue.class)).buildFinished(runningBuild, finishTime, false);
        String string = Util.formatTime((Date)finishTime);
        if (string == null) {
            BuildRequest.$$$reportNull$$$0(32);
        }
        return string;
    }

    private void checkBuildOperationPermission(@NotNull BuildPromotion buildPromotion) {
        SBuild build;
        AuthorityHolder user;
        Long buildId;
        if (buildPromotion == null) {
            BuildRequest.$$$reportNull$$$0(33);
        }
        if ((buildId = ServerAuthUtil.getBuildIdIfBuildAuthority((AuthorityHolder)(user = this.myPermissionChecker.getCurrent()))) != null && (build = buildPromotion.getAssociatedBuild()) != null && buildId.longValue() == build.getBuildId()) {
            return;
        }
        try {
            this.myPermissionChecker.checkPermission(Permission.EDIT_PROJECT, buildPromotion);
        }
        catch (AuthorizationFailedException e) {
            throw new AuthorizationFailedException("Should use build authentication or have \"" + String.valueOf(Permission.EDIT_PROJECT) + "\" permission to do the build operation", (Throwable)e);
        }
    }

    @GET
    @Path(value="/{buildLocator}/finishDate")
    @Produces(value={"text/plain"})
    @ApiOperation(value="Get the finish date of the matching build.", nickname="getBuildFinishDate")
    public String getBuildFinishDate(@ApiParam(format="BuildLocator") @PathParam(value="buildLocator") String buildLocator) {
        return this.serveBuildFieldFromBuildOnly(buildLocator, "finishDate");
    }

    @GET
    @Path(value="/{buildLocator}/{field}")
    @Produces(value={"text/plain"})
    @ApiOperation(value="Get a field of the matching build.", nickname="getBuildField")
    public String serveBuildFieldFromBuildOnly(@ApiParam(format="BuildLocator") @PathParam(value="buildLocator") String buildLocator, @PathParam(value="field") String field) {
        return jetbrains.buildServer.server.rest.model.build.Build.getFieldValue((BuildPromotion)this.myBuildFinder.getBuildPromotion(null, buildLocator), (String)field, (BeanContext)this.myBeanContext);
    }

    @NotNull
    private BuildIconStatus getAggregatedStatus(@Nullable String multipleBuildsLocator) {
        BuildIconStatus resultState = BuildIconStatus.NOT_FOUND;
        boolean[] hasNext = new boolean[]{true};
        4 buildPromotionRetriever = new /* Unavailable Anonymous Inner Class!! */;
        while (hasNext[0]) {
            BuildIconStatus stateName = BuildIconStatus.create((BeanContext)this.myBeanContext, (BuildIconStatus.Value)buildPromotionRetriever);
            if (resultState.compareTo((Enum)stateName) >= 0) continue;
            resultState = stateName;
        }
        BuildIconStatus buildIconStatus = resultState;
        if (buildIconStatus == null) {
            BuildRequest.$$$reportNull$$$0(34);
        }
        return buildIconStatus;
    }

    @NotNull
    private BuildIconStatus getStatus(@Nullable String buildLocator) {
        BuildIconStatus buildIconStatus = BuildIconStatus.create((BeanContext)this.myBeanContext, () -> this.myBuildFinder.getBuildPromotion(null, buildLocator));
        if (buildIconStatus == null) {
            BuildRequest.$$$reportNull$$$0(35);
        }
        return buildIconStatus;
    }

    private Response processIconRequest(String stateName, String suffix, @Context HttpServletRequest request) {
        String resultIconFileName;
        String iconFileName = "/img/statusWidget/" + stateName + (StringUtil.isEmpty((String)suffix) ? ".svg" : suffix);
        try {
            resultIconFileName = this.getRealFileName(iconFileName);
        }
        catch (AccessDeniedException e) {
            if (StringUtil.isEmpty((String)suffix)) {
                throw new InvalidStateException("Error retrieving requested resource", (Throwable)e);
            }
            throw new BadRequestException("Wrong suffix '" + suffix + "' is specified. Try omitting it.");
        }
        if (resultIconFileName == null || !new File(resultIconFileName).isFile()) {
            LOG.debug("Resource file not found: '" + iconFileName + "'" + (String)(StringUtil.isEmpty((String)suffix) ? "" : ", using custom suffix '" + suffix + "' from request"));
            throw new NotFoundException("There is no resource file with relative path '" + iconFileName + "'" + (String)(StringUtil.isEmpty((String)suffix) ? " (installation corrupted?)" : ", try omitting '" + suffix + "' suffix"));
        }
        File resultIconFile = new File(resultIconFileName);
        StreamingOutput streamingOutput = output -> {
            BufferedInputStream inputStream = null;
            try {
                inputStream = new BufferedInputStream(new FileInputStream(resultIconFile));
                TCStreamUtil.writeBinary((InputStream)inputStream, (OutputStream)output);
            }
            catch (IOException e) {
                try {
                    throw new OperationException("Error while retrieving file '" + resultIconFile.getName() + "': " + e.getMessage(), (Exception)e);
                }
                catch (Throwable throwable) {
                    FileUtil.close(inputStream);
                    throw throwable;
                }
            }
            FileUtil.close((Closeable)inputStream);
        };
        String mediaType = WebUtil.getMimeType((HttpServletRequest)request, (String)resultIconFileName);
        Response.ResponseBuilder response = Response.ok((Object)streamingOutput, (String)mediaType).header("Cache-Control", (Object)"no-cache, private");
        response.header("ETag", (Object)("W/\"" + EncryptUtil.md5((String)String.valueOf(stateName)) + "\""));
        if (WebUtil.isIE10OrLower((HttpServletRequest)request)) {
            response.header("Cache-Control", (Object)"private,must-revalidate");
            response.header("Pragma", (Object)"private");
        }
        return response.build();
    }

    private String getRealFileName(String relativePath) {
        ServletContext servletContext = (ServletContext)this.myBeanContext.getSingletonService(ServletContext.class);
        String realPath = servletContext.getRealPath(relativePath);
        FileSecurityUtil.checkInsideDirectory((File)new File(realPath), (File)new File(servletContext.getRealPath("/")));
        return realPath;
    }

    void initForTests(@NotNull BeanContext beanContext) {
        if (beanContext == null) {
            BuildRequest.$$$reportNull$$$0(36);
        }
        this.myBeanContext = beanContext;
        this.myBuildFinder = (BuildFinder)this.myBeanContext.getSingletonService(BuildFinder.class);
        this.myBuildPromotionFinder = (BuildPromotionFinder)this.myBeanContext.getSingletonService(BuildPromotionFinder.class);
        this.myBuildTypeFinder = (BuildTypeFinder)this.myBeanContext.getSingletonService(BuildTypeFinder.class);
        this.myPermissionChecker = (PermissionChecker)this.myBeanContext.getSingletonService(PermissionChecker.class);
        this.myServiceLocator = (ServiceLocator)this.myBeanContext.getSingletonService(ServiceLocator.class);
    }

    private static /* synthetic */ void $$$reportNull$$$0(int n) {
        Object[] objectArray;
        Object[] objectArray2;
        Object[] objectArray3 = new Object[switch (n) {
            default -> 2;
            case 1, 2, 3, 4, 5, 6, 7, 9, 10, 13, 18, 19, 20, 21, 22, 23, 25, 26, 27, 28, 29, 30, 31, 33, 36 -> 3;
        }];
        switch (n) {
            default: {
                objectArray2 = objectArray3;
                objectArray3[0] = "jetbrains/buildServer/server/rest/request/BuildRequest";
                break;
            }
            case 1: {
                objectArray2 = objectArray3;
                objectArray3[0] = "locator";
                break;
            }
            case 2: 
            case 3: 
            case 5: 
            case 7: 
            case 20: 
            case 29: {
                objectArray2 = objectArray3;
                objectArray3[0] = "build";
                break;
            }
            case 4: {
                objectArray2 = objectArray3;
                objectArray3[0] = "branch";
                break;
            }
            case 6: 
            case 9: 
            case 13: 
            case 19: 
            case 33: {
                objectArray2 = objectArray3;
                objectArray3[0] = "buildPromotion";
                break;
            }
            case 10: {
                objectArray2 = objectArray3;
                objectArray3[0] = "path";
                break;
            }
            case 18: {
                objectArray2 = objectArray3;
                objectArray3[0] = "promotion";
                break;
            }
            case 21: 
            case 22: {
                objectArray2 = objectArray3;
                objectArray3[0] = "builds";
                break;
            }
            case 23: {
                objectArray2 = objectArray3;
                objectArray3[0] = "cancelRequest";
                break;
            }
            case 25: {
                objectArray2 = objectArray3;
                objectArray3[0] = "allBuilds";
                break;
            }
            case 26: {
                objectArray2 = objectArray3;
                objectArray3[0] = "reportedErrors";
                break;
            }
            case 27: {
                objectArray2 = objectArray3;
                objectArray3[0] = "action";
                break;
            }
            case 28: {
                objectArray2 = objectArray3;
                objectArray3[0] = "fields";
                break;
            }
            case 30: {
                objectArray2 = objectArray3;
                objectArray3[0] = "buildLocator";
                break;
            }
            case 31: {
                objectArray2 = objectArray3;
                objectArray3[0] = "finishTime";
                break;
            }
            case 36: {
                objectArray2 = objectArray3;
                objectArray3[0] = "beanContext";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray2;
                objectArray2[1] = "getTagsManager";
                break;
            }
            case 1: 
            case 2: 
            case 3: 
            case 4: 
            case 5: 
            case 6: 
            case 7: 
            case 9: 
            case 10: 
            case 13: 
            case 18: 
            case 19: 
            case 20: 
            case 21: 
            case 22: 
            case 23: 
            case 25: 
            case 26: 
            case 27: 
            case 28: 
            case 29: 
            case 30: 
            case 31: 
            case 33: 
            case 36: {
                objectArray = objectArray2;
                objectArray2[1] = "jetbrains/buildServer/server/rest/request/BuildRequest";
                break;
            }
            case 8: {
                objectArray = objectArray2;
                objectArray2[1] = "getBuildArtifactsHref";
                break;
            }
            case 11: 
            case 12: {
                objectArray = objectArray2;
                objectArray2[1] = "getBuildFileName";
                break;
            }
            case 14: 
            case 15: 
            case 16: 
            case 17: {
                objectArray = objectArray2;
                objectArray2[1] = "getResolvedIfNecessary";
                break;
            }
            case 24: {
                objectArray = objectArray2;
                objectArray2[1] = "cancelBuilds";
                break;
            }
            case 32: {
                objectArray = objectArray2;
                objectArray2[1] = "finishBuild";
                break;
            }
            case 34: {
                objectArray = objectArray2;
                objectArray2[1] = "getAggregatedStatus";
                break;
            }
            case 35: {
                objectArray = objectArray2;
                objectArray2[1] = "getStatus";
                break;
            }
        }
        switch (n) {
            default: {
                break;
            }
            case 1: {
                objectArray = objectArray;
                objectArray[2] = "getHref";
                break;
            }
            case 2: 
            case 3: {
                objectArray = objectArray;
                objectArray[2] = "getBuildHref";
                break;
            }
            case 4: {
                objectArray = objectArray;
                objectArray[2] = "getBuildsHref";
                break;
            }
            case 5: 
            case 6: {
                objectArray = objectArray;
                objectArray[2] = "getBuildLocator";
                break;
            }
            case 7: {
                objectArray = objectArray;
                objectArray[2] = "getBuildArtifactsHref";
                break;
            }
            case 9: 
            case 10: {
                objectArray = objectArray;
                objectArray[2] = "getBuildFileName";
                break;
            }
            case 13: {
                objectArray = objectArray;
                objectArray[2] = "getResolvedIfNecessary";
                break;
            }
            case 18: {
                objectArray = objectArray;
                objectArray[2] = "getBuild";
                break;
            }
            case 19: {
                objectArray = objectArray;
                objectArray[2] = "pinBuild";
                break;
            }
            case 20: {
                objectArray = objectArray;
                objectArray[2] = "setBuildComment";
                break;
            }
            case 21: {
                objectArray = objectArray;
                objectArray[2] = "deleteBuilds";
                break;
            }
            case 22: 
            case 23: {
                objectArray = objectArray;
                objectArray[2] = "cancelBuilds";
                break;
            }
            case 25: 
            case 26: {
                objectArray = objectArray;
                objectArray[2] = "getResultData";
                break;
            }
            case 27: 
            case 28: {
                objectArray = objectArray;
                objectArray[2] = "processMultiple";
                break;
            }
            case 29: {
                objectArray = objectArray;
                objectArray[2] = "logMessage";
                break;
            }
            case 30: 
            case 31: {
                objectArray = objectArray;
                objectArray[2] = "finishBuild";
                break;
            }
            case 33: {
                objectArray = objectArray;
                objectArray[2] = "checkBuildOperationPermission";
                break;
            }
            case 36: {
                objectArray = objectArray;
                objectArray[2] = "initForTests";
                break;
            }
        }
        String string = String.format(v0, objectArray);
        throw switch (n) {
            default -> new IllegalStateException(string);
            case 1, 2, 3, 4, 5, 6, 7, 9, 10, 13, 18, 19, 20, 21, 22, 23, 25, 26, 27, 28, 29, 30, 31, 33, 36 -> new IllegalArgumentException(string);
        };
    }
}
