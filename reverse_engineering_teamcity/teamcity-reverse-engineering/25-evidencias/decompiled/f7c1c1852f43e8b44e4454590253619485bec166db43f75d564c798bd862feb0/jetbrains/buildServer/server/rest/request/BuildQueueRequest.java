/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.intellij.openapi.diagnostic.Logger
 *  com.intellij.util.Function
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  io.swagger.annotations.ApiParam
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
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
 *  javax.ws.rs.core.UriInfo
 *  jetbrains.buildServer.ServiceLocator
 *  jetbrains.buildServer.queueManager.settings.Actor
 *  jetbrains.buildServer.queueManager.settings.QueueState
 *  jetbrains.buildServer.queueManager.settings.QueueStateManager
 *  jetbrains.buildServer.server.rest.ApiUrlBuilder
 *  jetbrains.buildServer.server.rest.data.DataProvider
 *  jetbrains.buildServer.server.rest.data.Locator
 *  jetbrains.buildServer.server.rest.data.PagedSearchResult
 *  jetbrains.buildServer.server.rest.data.build.TagFinder
 *  jetbrains.buildServer.server.rest.data.finder.impl.AgentFinder
 *  jetbrains.buildServer.server.rest.data.finder.impl.BuildPromotionFinder
 *  jetbrains.buildServer.server.rest.data.finder.impl.QueuedBuildFinder
 *  jetbrains.buildServer.server.rest.data.finder.impl.UserFinder
 *  jetbrains.buildServer.server.rest.data.finder.syntax.BuildDimensions
 *  jetbrains.buildServer.server.rest.errors.AuthorizationFailedException
 *  jetbrains.buildServer.server.rest.errors.BadRequestException
 *  jetbrains.buildServer.server.rest.errors.NotFoundException
 *  jetbrains.buildServer.server.rest.errors.OperationException
 *  jetbrains.buildServer.server.rest.errors.PartialUpdateError
 *  jetbrains.buildServer.server.rest.model.Fields
 *  jetbrains.buildServer.server.rest.model.PagerData
 *  jetbrains.buildServer.server.rest.model.PagerDataImpl
 *  jetbrains.buildServer.server.rest.model.agent.Agents
 *  jetbrains.buildServer.server.rest.model.build.Build
 *  jetbrains.buildServer.server.rest.model.build.BuildCancelRequest
 *  jetbrains.buildServer.server.rest.model.build.Builds
 *  jetbrains.buildServer.server.rest.model.build.Tags
 *  jetbrains.buildServer.server.rest.model.build.approval.ApprovalInfo
 *  jetbrains.buildServer.server.rest.model.build.approval.ApprovalInfo$ApprovalStatus
 *  jetbrains.buildServer.server.rest.model.buildQueue.BuildQueueUpdate
 *  jetbrains.buildServer.server.rest.request.BuildRequest
 *  jetbrains.buildServer.server.rest.util.BeanContext
 *  jetbrains.buildServer.server.rest.util.BuildQueuePositionDescriptor
 *  jetbrains.buildServer.server.rest.util.BuildQueuePostitionModifier
 *  jetbrains.buildServer.serverSide.BuildHistory
 *  jetbrains.buildServer.serverSide.BuildPromotion
 *  jetbrains.buildServer.serverSide.BuildPromotionEx
 *  jetbrains.buildServer.serverSide.BuildPromotionOwner
 *  jetbrains.buildServer.serverSide.BuildQueue
 *  jetbrains.buildServer.serverSide.SBuild
 *  jetbrains.buildServer.serverSide.SQueuedBuild
 *  jetbrains.buildServer.serverSide.TagData
 *  jetbrains.buildServer.serverSide.auth.AccessDeniedException
 *  jetbrains.buildServer.serverSide.auth.AuthorityHolder
 *  jetbrains.buildServer.serverSide.auth.Permission
 *  jetbrains.buildServer.serverSide.impl.approval.ApprovableBuildManager
 *  jetbrains.buildServer.tags.TagsManager
 *  jetbrains.buildServer.users.SUser
 *  jetbrains.buildServer.util.CollectionsUtil
 *  jetbrains.buildServer.util.Converter
 *  jetbrains.buildServer.util.StringUtil
 *  jetbrains.buildServer.web.util.QueueWebUtil
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package jetbrains.buildServer.server.rest.request;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.util.Function;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import javax.ws.rs.core.UriInfo;
import jetbrains.buildServer.ServiceLocator;
import jetbrains.buildServer.queueManager.settings.Actor;
import jetbrains.buildServer.queueManager.settings.QueueState;
import jetbrains.buildServer.queueManager.settings.QueueStateManager;
import jetbrains.buildServer.server.rest.ApiUrlBuilder;
import jetbrains.buildServer.server.rest.data.DataProvider;
import jetbrains.buildServer.server.rest.data.Locator;
import jetbrains.buildServer.server.rest.data.PagedSearchResult;
import jetbrains.buildServer.server.rest.data.build.TagFinder;
import jetbrains.buildServer.server.rest.data.finder.impl.AgentFinder;
import jetbrains.buildServer.server.rest.data.finder.impl.BuildPromotionFinder;
import jetbrains.buildServer.server.rest.data.finder.impl.QueuedBuildFinder;
import jetbrains.buildServer.server.rest.data.finder.impl.UserFinder;
import jetbrains.buildServer.server.rest.data.finder.syntax.BuildDimensions;
import jetbrains.buildServer.server.rest.errors.AuthorizationFailedException;
import jetbrains.buildServer.server.rest.errors.BadRequestException;
import jetbrains.buildServer.server.rest.errors.NotFoundException;
import jetbrains.buildServer.server.rest.errors.OperationException;
import jetbrains.buildServer.server.rest.errors.PartialUpdateError;
import jetbrains.buildServer.server.rest.model.Fields;
import jetbrains.buildServer.server.rest.model.PagerData;
import jetbrains.buildServer.server.rest.model.PagerDataImpl;
import jetbrains.buildServer.server.rest.model.agent.Agents;
import jetbrains.buildServer.server.rest.model.build.Build;
import jetbrains.buildServer.server.rest.model.build.BuildCancelRequest;
import jetbrains.buildServer.server.rest.model.build.Builds;
import jetbrains.buildServer.server.rest.model.build.Tags;
import jetbrains.buildServer.server.rest.model.build.approval.ApprovalInfo;
import jetbrains.buildServer.server.rest.model.buildQueue.BuildQueueUpdate;
import jetbrains.buildServer.server.rest.request.BuildRequest;
import jetbrains.buildServer.server.rest.util.BeanContext;
import jetbrains.buildServer.server.rest.util.BuildQueuePositionDescriptor;
import jetbrains.buildServer.server.rest.util.BuildQueuePostitionModifier;
import jetbrains.buildServer.serverSide.BuildHistory;
import jetbrains.buildServer.serverSide.BuildPromotion;
import jetbrains.buildServer.serverSide.BuildPromotionEx;
import jetbrains.buildServer.serverSide.BuildPromotionOwner;
import jetbrains.buildServer.serverSide.BuildQueue;
import jetbrains.buildServer.serverSide.SBuild;
import jetbrains.buildServer.serverSide.SQueuedBuild;
import jetbrains.buildServer.serverSide.TagData;
import jetbrains.buildServer.serverSide.auth.AccessDeniedException;
import jetbrains.buildServer.serverSide.auth.AuthorityHolder;
import jetbrains.buildServer.serverSide.auth.Permission;
import jetbrains.buildServer.serverSide.impl.approval.ApprovableBuildManager;
import jetbrains.buildServer.tags.TagsManager;
import jetbrains.buildServer.users.SUser;
import jetbrains.buildServer.util.CollectionsUtil;
import jetbrains.buildServer.util.Converter;
import jetbrains.buildServer.util.StringUtil;
import jetbrains.buildServer.web.util.QueueWebUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Path(value="/app/rest/buildQueue")
@Api(value="BuildQueue")
public class BuildQueueRequest {
    private static final Logger LOG = Logger.getInstance((String)BuildRequest.class.getName());
    public static final String API_BUILD_QUEUE_URL = "/app/rest/buildQueue";
    public static final String COMPATIBLE_AGENTS = "/compatibleAgents";
    @Context
    @NotNull
    private QueuedBuildFinder myQueuedBuildFinder;
    @Context
    @NotNull
    private BuildPromotionFinder myBuildPromotionFinder;
    @Context
    @NotNull
    private AgentFinder myAgentFinder;
    @Context
    @NotNull
    private ApiUrlBuilder myApiUrlBuilder;
    @Context
    @NotNull
    private ServiceLocator myServiceLocator;
    @Context
    @NotNull
    private BeanContext myBeanContext;

    @NotNull
    public static String getHref() {
        return API_BUILD_QUEUE_URL;
    }

    @NotNull
    public static String getQueuedBuildHref(SQueuedBuild build) {
        String string = "/app/rest/buildQueue/" + QueuedBuildFinder.getLocator((SQueuedBuild)build);
        if (string == null) {
            BuildQueueRequest.$$$reportNull$$$0(0);
        }
        return string;
    }

    void initForTests(@NotNull BeanContext beanContext) {
        if (beanContext == null) {
            BuildQueueRequest.$$$reportNull$$$0(1);
        }
        this.myQueuedBuildFinder = (QueuedBuildFinder)beanContext.getSingletonService(QueuedBuildFinder.class);
        this.myBuildPromotionFinder = (BuildPromotionFinder)beanContext.getSingletonService(BuildPromotionFinder.class);
        this.myAgentFinder = (AgentFinder)beanContext.getSingletonService(AgentFinder.class);
        this.myApiUrlBuilder = (ApiUrlBuilder)beanContext.getSingletonService(ApiUrlBuilder.class);
        this.myServiceLocator = (ServiceLocator)beanContext.getSingletonService(ServiceLocator.class);
        this.myBeanContext = beanContext;
    }

    @GET
    @Produces(value={"application/xml", "application/json"})
    @ApiOperation(value="Get all queued builds.", nickname="getAllQueuedBuilds")
    public Builds getBuilds(@ApiParam(format="BuildQueueLocator") @QueryParam(value="locator") String locator, @QueryParam(value="fields") String fields, @Context UriInfo uriInfo, @Context HttpServletRequest request) {
        PagedSearchResult result = this.myQueuedBuildFinder.getItems(locator);
        List builds = CollectionsUtil.convertCollection((Collection)result.getEntries(), BuildPromotionOwner::getBuildPromotion);
        return Builds.createFromPrefilteredBuildPromotions((List)builds, (PagerData)new PagerDataImpl(uriInfo.getRequestUriBuilder(), request.getContextPath(), result, locator, "locator"), (Fields)new Fields(fields), (BeanContext)this.myBeanContext);
    }

    @DELETE
    @ApiOperation(value="Delete all queued builds.", nickname="deleteAllQueuedBuilds")
    public void deleteBuildsExperimental(@ApiParam(format="BuildQueueLocator") @QueryParam(value="locator") String locator, @QueryParam(value="fields") String fields, @Context UriInfo uriInfo, @Context HttpServletRequest request) {
        PagedSearchResult result = this.myQueuedBuildFinder.getItems(locator);
        ArrayList<Object> errors = new ArrayList<Object>();
        BuildQueue buildQueue = (BuildQueue)this.myServiceLocator.getSingletonService(BuildQueue.class);
        List itemIds = CollectionsUtil.convertCollection((Collection)result.getEntries(), (Converter)new /* Unavailable Anonymous Inner Class!! */);
        buildQueue.removeItems((Collection)itemIds, ((UserFinder)this.myServiceLocator.getSingletonService(UserFinder.class)).getCurrentUser(), null);
        for (String itemId : itemIds) {
            if (buildQueue.findQueued(itemId) == null) continue;
            errors.add(new AuthorizationFailedException("Build was not canceled. Probably not sufficient permissions."));
        }
        for (SQueuedBuild build : result.getEntries()) {
            SBuild associatedBuild = build.getBuildPromotion().getAssociatedBuild();
            if (associatedBuild == null) {
                errors.add(new OperationException("After canceling a build with promotion id '" + build.getBuildPromotion().getId() + "' , no canceled build found to delete."));
                continue;
            }
            DataProvider.deleteBuild((SBuild)associatedBuild, (BuildHistory)((BuildHistory)this.myBeanContext.getSingletonService(BuildHistory.class)));
        }
        if (errors.size() > 0) {
            throw new PartialUpdateError("Some builds were not deleted", errors);
        }
    }

    @PUT
    @Consumes(value={"application/xml", "application/json"})
    @Produces(value={"application/xml", "application/json"})
    @ApiOperation(value="replaceBuilds", hidden=true)
    public Builds replaceBuilds(Builds builds, @QueryParam(value="fields") String fields, @Context UriInfo uriInfo, @Context HttpServletRequest request) {
        Map<Build, Exception> buildsWithErrors;
        if (builds == null) {
            throw new BadRequestException("List of builds should be posted.");
        }
        if (builds.getSubmittedBuilds() == null) {
            throw new BadRequestException("Posted element should contain 'builds' sub-element.");
        }
        BuildQueue buildQueue = (BuildQueue)this.myServiceLocator.getSingletonService(BuildQueue.class);
        List queuedBuildPromotions = CollectionsUtil.convertCollection((Collection)buildQueue.getItems(), (Converter)new /* Unavailable Anonymous Inner Class!! */);
        List queuedBuildIds = CollectionsUtil.convertCollection((Collection)buildQueue.getItems(), (Converter)new /* Unavailable Anonymous Inner Class!! */);
        buildQueue.removeItems((Collection)queuedBuildIds, ((UserFinder)this.myServiceLocator.getSingletonService(UserFinder.class)).getCurrentUser(), null);
        if (!buildQueue.isQueueEmpty()) {
            throw new AuthorizationFailedException("Some builds were not canceled. Probably not sufficient permissions.");
        }
        for (BuildPromotion queuedBuildPromotion : queuedBuildPromotions) {
            SBuild associatedBuild = queuedBuildPromotion.getAssociatedBuild();
            if (associatedBuild == null) {
                throw new OperationException("After canceling a build with promotion id '" + queuedBuildPromotion.getId() + "' , no canceled build found to delete.");
            }
            DataProvider.deleteBuild((SBuild)associatedBuild, (BuildHistory)((BuildHistory)this.myBeanContext.getSingletonService(BuildHistory.class)));
        }
        SUser user = ((UserFinder)this.myServiceLocator.getSingletonService(UserFinder.class)).getCurrentUser();
        HashMap<Long, Long> buildPromotionIdReplacements = new HashMap<Long, Long>();
        ArrayList<Build> buildsToTrigger = builds.getSubmittedBuilds();
        while (!(buildsWithErrors = this.triggerBuilds((List<Build>)buildsToTrigger, user, buildPromotionIdReplacements)).isEmpty() && buildsToTrigger.size() > buildsWithErrors.size()) {
            buildsToTrigger = new ArrayList<Build>(buildsWithErrors.keySet());
            LOG.info("There was an error triggering " + buildsToTrigger.size() + " builds, will try again. Affected build ids: " + this.listBuildIds(buildsToTrigger));
        }
        if (buildsWithErrors.size() != 0) {
            StringBuilder buildListDetails = new StringBuilder();
            for (Map.Entry<Build, Exception> buildExceptionEntry : buildsWithErrors.entrySet()) {
                Build build = buildExceptionEntry.getKey();
                buildListDetails.append("Not able to add build").append((String)(build.getPromotionIdOfSubmittedBuild() != null ? " with id '" + build.getPromotionIdOfSubmittedBuild() + "'" : "")).append(" to the build queue due to error: ").append(buildExceptionEntry.getValue().toString());
                buildListDetails.append("\n");
            }
            throw new BadRequestException("Error triggering " + buildsWithErrors.size() + " out of " + builds.getSubmittedBuilds().size() + " builds: \n" + buildListDetails.substring(0, buildListDetails.length() - "\n".length()));
        }
        return this.getBuilds(null, fields, uriInfo, request);
    }

    @NotNull
    private String listBuildIds(@NotNull Collection<Build> buildsToTrigger) {
        if (buildsToTrigger == null) {
            BuildQueueRequest.$$$reportNull$$$0(2);
        }
        String string = StringUtil.join(buildsToTrigger, (Function)new /* Unavailable Anonymous Inner Class!! */, (String)", ");
        if (string == null) {
            BuildQueueRequest.$$$reportNull$$$0(3);
        }
        return string;
    }

    @NotNull
    private Map<Build, Exception> triggerBuilds(@NotNull List<Build> builds, @Nullable SUser user, @NotNull Map<Long, Long> buildPromotionIdReplacements) {
        if (builds == null) {
            BuildQueueRequest.$$$reportNull$$$0(4);
        }
        if (buildPromotionIdReplacements == null) {
            BuildQueueRequest.$$$reportNull$$$0(5);
        }
        LinkedHashMap<Build, Exception> buildsWithErrors = new LinkedHashMap<Build, Exception>();
        for (Build build : builds) {
            try {
                SQueuedBuild queuedBuild = build.triggerBuild(user, this.myServiceLocator, buildPromotionIdReplacements);
                if (build.getPromotionIdOfSubmittedBuild() == null) continue;
                buildPromotionIdReplacements.put(build.getPromotionIdOfSubmittedBuild(), queuedBuild.getBuildPromotion().getId());
            }
            catch (Exception e) {
                buildsWithErrors.put(build, e);
                LOG.debug("Got error trying to add build" + (String)(build.getPromotionIdOfSubmittedBuild() != null ? " with id '" + build.getPromotionIdOfSubmittedBuild() + "'" : "") + " to the build queue. Details: " + e.toString(), (Throwable)e);
            }
        }
        LinkedHashMap<Build, Exception> linkedHashMap = buildsWithErrors;
        if (linkedHashMap == null) {
            BuildQueueRequest.$$$reportNull$$$0(6);
        }
        return linkedHashMap;
    }

    @GET
    @Path(value="/{queuedBuildLocator}")
    @Produces(value={"application/xml", "application/json"})
    @ApiOperation(value="Get a queued matching build.", nickname="getQueuedBuild")
    public Build getBuild(@ApiParam(format="BuildQueueLocator") @PathParam(value="queuedBuildLocator") String queuedBuildLocator, @QueryParam(value="fields") String fields, @Context UriInfo uriInfo, @Context HttpServletResponse response) {
        BuildPromotion buildPromotion = this.myQueuedBuildFinder.getBuildPromotionByBuildQueueLocator(queuedBuildLocator);
        return new Build(buildPromotion, new Fields(fields), this.myBeanContext);
    }

    @DELETE
    @Path(value="/{queuedBuildLocator}")
    @ApiOperation(value="Delete a queued matching build.", nickname="deleteQueuedBuild")
    public void deleteQueuedBuild(@ApiParam(format="BuildQueueLocator") @PathParam(value="queuedBuildLocator") String queuedBuildLocator) {
        SQueuedBuild build = (SQueuedBuild)this.myQueuedBuildFinder.getItem(queuedBuildLocator);
        this.cancelQueuedBuild(build, null);
        SBuild associatedBuild = build.getBuildPromotion().getAssociatedBuild();
        if (associatedBuild == null) {
            throw new OperationException("After canceling a build with promotion id '" + build.getBuildPromotion().getId() + "' , no canceled build found to delete.");
        }
        DataProvider.deleteBuild((SBuild)associatedBuild, (BuildHistory)((BuildHistory)this.myBeanContext.getSingletonService(BuildHistory.class)));
    }

    @GET
    @ApiOperation(value="getBuildCancelRequestQueue", hidden=true)
    @Path(value="/{buildLocator}/example/buildCancelRequest")
    @Produces(value={"application/xml", "application/json"})
    public BuildCancelRequest getBuildCancelRequestQueue(@ApiParam(format="BuildQueueLocator") @PathParam(value="buildLocator") String buildLocator) {
        return new BuildCancelRequest("example build cancel comment", false);
    }

    @POST
    @Path(value="/{queuedBuildLocator}")
    @Consumes(value={"application/xml", "application/json"})
    @Produces(value={"application/xml", "application/json"})
    @ApiOperation(value="Cancel a queued matching build.", nickname="cancelQueuedBuild")
    public Build cancelBuild(@ApiParam(format="BuildQueueLocator") @PathParam(value="queuedBuildLocator") String queuedBuildLocator, BuildCancelRequest cancelRequest) {
        if (cancelRequest.readdIntoQueue) {
            throw new BadRequestException("Restore in queue is not supported for queued builds.");
        }
        SQueuedBuild build = (SQueuedBuild)this.myQueuedBuildFinder.getItem(queuedBuildLocator);
        this.cancelQueuedBuild(build, cancelRequest.comment);
        return new Build(build.getBuildPromotion(), Fields.LONG, this.myBeanContext);
    }

    private void cancelQueuedBuild(@NotNull SQueuedBuild build, @Nullable String comment) {
        if (build == null) {
            BuildQueueRequest.$$$reportNull$$$0(7);
        }
        BuildQueue buildQueue = (BuildQueue)this.myServiceLocator.getSingletonService(BuildQueue.class);
        String itemId = build.getItemId();
        buildQueue.removeItems(Collections.singleton(itemId), ((UserFinder)this.myServiceLocator.getSingletonService(UserFinder.class)).getCurrentUser(), comment);
        if (buildQueue.findQueued(itemId) != null) {
            throw new AuthorizationFailedException("Build was not canceled. Probably not sufficient permissions.");
        }
    }

    @GET
    @Path(value="/{queuedBuildLocator}/compatibleAgents")
    @Produces(value={"application/xml", "application/json"})
    @ApiOperation(value="Get compatible agents for a queued matching build.", nickname="getCompatibleAgentsForBuild")
    public Agents serveCompatibleAgents(@ApiParam(format="BuildQueueLocator") @PathParam(value="queuedBuildLocator") String queuedBuildLocator, @QueryParam(value="fields") String fields) {
        return new Agents(AgentFinder.getCompatibleAgentsLocator((BuildPromotion)((SQueuedBuild)this.myQueuedBuildFinder.getItem(queuedBuildLocator)).getBuildPromotion()), null, new Fields(fields), this.myBeanContext);
    }

    @GET
    @Path(value="/{buildLocator}/tags/")
    @Produces(value={"application/xml", "application/json"})
    @ApiOperation(value="Get tags of the queued matching build.", nickname="getQueuedBuildTags")
    public Tags serveTags(@ApiParam(format="BuildQueueLocator") @PathParam(value="buildLocator") String buildLocator, @QueryParam(value="locator") String tagLocator, @QueryParam(value="fields") String fields) {
        BuildPromotion buildPromotion = (BuildPromotion)this.myBuildPromotionFinder.getItem(Locator.createLocator((String)buildLocator, (Locator)this.getBuildPromotionLocatorDefaults(), null).getStringRepresentation());
        return new Tags(new TagFinder((UserFinder)this.myBeanContext.getSingletonService(UserFinder.class), buildPromotion).getItems(tagLocator, TagFinder.getDefaultLocator()).getEntries(), new Fields(fields), this.myBeanContext);
    }

    @PUT
    @Path(value="/{buildLocator}/tags/")
    @Consumes(value={"application/xml", "application/json"})
    @Produces(value={"application/xml", "application/json"})
    @ApiOperation(value="replaceTags", hidden=true)
    public Tags replaceTags(@ApiParam(format="BuildQueueLocator") @PathParam(value="buildLocator") String buildLocator, @ApiParam(format="TagLocator") @QueryParam(value="locator") String tagLocator, Tags tags, @QueryParam(value="fields") String fields, @Context HttpServletRequest request) {
        BuildPromotion buildPromotion = (BuildPromotion)this.myBuildPromotionFinder.getItem(Locator.createLocator((String)buildLocator, (Locator)this.getBuildPromotionLocatorDefaults(), null).getStringRepresentation());
        TagFinder tagFinder = new TagFinder((UserFinder)this.myBeanContext.getSingletonService(UserFinder.class), buildPromotion);
        TagsManager tagsManager = (TagsManager)this.myBeanContext.getSingletonService(TagsManager.class);
        tagsManager.removeTagDatas(buildPromotion, (Collection)tagFinder.getItems(tagLocator, TagFinder.getDefaultLocator()).getEntries());
        tagsManager.addTagDatas(buildPromotion, (Collection)tags.getFromPosted((UserFinder)this.myBeanContext.getSingletonService(UserFinder.class)));
        return new Tags(tagFinder.getItems(null, TagFinder.getDefaultLocator()).getEntries(), new Fields(fields), this.myBeanContext);
    }

    @POST
    @Path(value="/{buildLocator}/tags/")
    @Consumes(value={"application/xml", "application/json"})
    @ApiOperation(value="Add tags to the matching build.", nickname="addTagsToBuild")
    public void addTags(@ApiParam(format="BuildLocator") @PathParam(value="buildLocator") String buildLocator, Tags tags, @Context HttpServletRequest request) {
        BuildPromotion buildPromotion = (BuildPromotion)this.myBuildPromotionFinder.getItem(Locator.createLocator((String)buildLocator, (Locator)this.getBuildPromotionLocatorDefaults(), null).getStringRepresentation());
        TagsManager tagsManager = (TagsManager)this.myBeanContext.getSingletonService(TagsManager.class);
        tagsManager.addTagDatas(buildPromotion, (Collection)tags.getFromPosted((UserFinder)this.myBeanContext.getSingletonService(UserFinder.class)));
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
        BuildPromotion buildPromotion = (BuildPromotion)this.myBuildPromotionFinder.getItem(Locator.createLocator((String)buildLocator, (Locator)this.getBuildPromotionLocatorDefaults(), null).getStringRepresentation());
        TagsManager tagsManager = (TagsManager)this.myBeanContext.getSingletonService(TagsManager.class);
        tagsManager.addTagDatas(buildPromotion, Collections.singleton(TagData.createPublicTag((String)tagName)));
        return tagName;
    }

    @NotNull
    private Locator getBuildPromotionLocatorDefaults() {
        Locator defaultLocator = Locator.createEmptyLocator((String[])new String[0]);
        defaultLocator.setDimension(BuildDimensions.STATE, "queued");
        defaultLocator.addIgnoreUnusedDimensions(new String[]{BuildDimensions.STATE.getName()});
        Locator locator = defaultLocator;
        if (locator == null) {
            BuildQueueRequest.$$$reportNull$$$0(8);
        }
        return locator;
    }

    @GET
    @Path(value="/{buildLocator}/{field}")
    @Produces(value={"text/plain"})
    @ApiOperation(value="serveBuildFieldByBuildOnly", hidden=true)
    public String serveBuildFieldByBuildOnly(@ApiParam(format="BuildLocator") @PathParam(value="buildLocator") String buildLocator, @PathParam(value="field") String field) {
        BuildPromotion buildPromotion = this.myQueuedBuildFinder.getBuildPromotionByBuildQueueLocator(buildLocator);
        return Build.getFieldValue((BuildPromotion)buildPromotion, (String)field, (BeanContext)this.myBeanContext);
    }

    @POST
    @Consumes(value={"application/xml", "application/json"})
    @Produces(value={"application/xml", "application/json"})
    @ApiOperation(value="Add a new build to the queue.", nickname="addBuildToQueue")
    public Build queueNewBuild(Build build, @QueryParam(value="moveToTop") Boolean moveToTop, @Context HttpServletRequest request, @Context HttpServletResponse response) {
        if (QueueWebUtil.processLargeQueueCase((HttpServletRequest)request, (HttpServletResponse)response, (BuildQueue)((BuildQueue)this.myServiceLocator.getSingletonService(BuildQueue.class)))) {
            return null;
        }
        SUser user = ((UserFinder)this.myServiceLocator.getSingletonService(UserFinder.class)).getCurrentUser();
        SQueuedBuild queuedBuild = build.triggerBuild(user, this.myServiceLocator, new HashMap());
        if (moveToTop != null && moveToTop.booleanValue()) {
            BuildQueue buildQueue = (BuildQueue)this.myServiceLocator.getSingletonService(BuildQueue.class);
            buildQueue.moveTop(queuedBuild.getItemId());
        }
        return new Build(queuedBuild.getBuildPromotion(), Fields.LONG, this.myBeanContext);
    }

    @PUT
    @Path(value="/order")
    @Consumes(value={"application/xml", "application/json"})
    @Produces(value={"application/xml", "application/json"})
    @ApiOperation(value="Update the build queue order.", nickname="setQueuedBuildsOrder")
    public Builds setBuildQueueOrder(Builds builds, @QueryParam(value="fields") String fields) {
        if (builds.getSubmittedBuilds() == null) {
            throw new BadRequestException("No new builds order specified. Should post a collection of builds, each with id or locator");
        }
        LinkedHashSet<String> ids = new LinkedHashSet<String>();
        for (Build build : builds.getSubmittedBuilds()) {
            try {
                List items = this.myBuildPromotionFinder.getItems(build.getLocatorFromPosted(Collections.emptyMap()), new Locator(this.getBuildPromotionLocatorDefaults())).getEntries();
                for (BuildPromotion buildPromotion : items) {
                    SQueuedBuild queuedBuild = buildPromotion.getQueuedBuild();
                    if (queuedBuild == null) continue;
                    ids.add(String.valueOf(queuedBuild.getItemId()));
                }
            }
            catch (NotFoundException notFoundException) {
            }
        }
        BuildQueue buildQueue = (BuildQueue)this.myServiceLocator.getSingletonService(BuildQueue.class);
        buildQueue.applyOrder((String[])CollectionsUtil.toArray(ids, String.class));
        return Builds.createFromPrefilteredBuildPromotions((List)this.myBuildPromotionFinder.getItems(this.getBuildPromotionLocatorDefaults().getStringRepresentation()).getEntries(), null, (Fields)new Fields(fields), (BeanContext)this.myBeanContext);
    }

    @GET
    @Path(value="/order/{queuePosition}")
    @Consumes(value={"application/xml", "application/json"})
    @Produces(value={"application/xml", "application/json"})
    @ApiOperation(value="Get the queue position of a queued matching build.", nickname="getQueuedBuildPosition")
    public Build getBuildQueuePosition(@PathParam(value="queuePosition") String queuePosition, @QueryParam(value="fields") String fields) {
        int actualPosition;
        int queuePositionNumber = this.getQueuePositionNumber(queuePosition);
        if (queuePositionNumber < 1) {
            throw new BadRequestException("Unsupported value of queuePosition \"" + queuePosition + "\": should be greater than 0");
        }
        if (queuePositionNumber == Integer.MAX_VALUE) {
            BuildQueue buildQueue = (BuildQueue)this.myServiceLocator.getSingletonService(BuildQueue.class);
            actualPosition = buildQueue.getNumberOfItems() - 1;
        } else {
            actualPosition = queuePositionNumber - 1;
        }
        Locator locator = this.getBuildPromotionLocatorDefaults().setDimension("start", String.valueOf(actualPosition));
        return new Build((BuildPromotion)this.myBuildPromotionFinder.getItem(locator.getStringRepresentation()), new Fields(fields), this.myBeanContext);
    }

    private int getQueuePositionNumber(@PathParam(value="queuePosition") String queuePosition) {
        try {
            if ("first".equals(queuePosition)) {
                return 1;
            }
            if ("last".equals(queuePosition)) {
                return Integer.MAX_VALUE;
            }
            return Integer.parseInt(queuePosition);
        }
        catch (NumberFormatException e) {
            throw new BadRequestException("Error parsing queuePosition \"" + queuePosition + "\": should be a number, \"first\" or \"last\"");
        }
    }

    @PUT
    @Path(value="/order/{queuePosition}")
    @Consumes(value={"application/xml", "application/json"})
    @Produces(value={"application/xml", "application/json"})
    @ApiOperation(value="Update the queue position of a queued matching build.", nickname="setQueuedBuildPosition")
    public Build setBuildQueuePosition(Build build, @PathParam(value="queuePosition") String queuePosition, @QueryParam(value="fields") String fields) {
        BuildPromotion buildToMove = build.getFromPosted(this.myBuildPromotionFinder, Collections.emptyMap());
        SQueuedBuild queuedBuild = buildToMove.getQueuedBuild();
        if (queuedBuild == null) {
            throw new BadRequestException("Cannot move build which is not queued");
        }
        BuildQueuePositionDescriptor newPosition = BuildQueuePositionDescriptor.parse((String)queuePosition);
        if (newPosition == null) {
            throw new BadRequestException("Unsupported value of queuePosition \"" + queuePosition + "\": only \"1\", \"first\" and \"last\" are supported");
        }
        BuildQueuePostitionModifier buildQueuePostitionModifier = (BuildQueuePostitionModifier)this.myServiceLocator.getSingletonService(BuildQueuePostitionModifier.class);
        buildQueuePostitionModifier.moveBuild(queuedBuild, newPosition);
        return new Build(queuedBuild.getBuildPromotion(), new Fields(fields), this.myBeanContext);
    }

    @GET
    @Path(value="/{buildLocator}/approvalInfo")
    @Produces(value={"application/xml", "application/json"})
    @ApiOperation(value="Get approval info of a queued matching build.", nickname="getApprovalInfo")
    public ApprovalInfo getApprovalInfo(@ApiParam(format="BuildLocator") @PathParam(value="buildLocator") String buildLocator, @QueryParam(value="fields") String fields) {
        BuildPromotionEx buildPromotionEx = (BuildPromotionEx)this.myBuildPromotionFinder.getBuildPromotion(null, buildLocator);
        return new ApprovalInfo(buildPromotionEx, new Fields(fields), this.myBeanContext);
    }

    @POST
    @Path(value="/{buildLocator}/approve")
    @Produces(value={"application/xml", "application/json"})
    @ApiOperation(value="Approve queued build with approval feature enabled.", nickname="approveQueuedBuild")
    public ApprovalInfo approveQueuedBuild(@ApiParam(format="BuildLocator") @PathParam(value="buildLocator") String buildLocator, String requestor, @QueryParam(value="fields") String fields, @QueryParam(value="approveAll") Boolean approveAll) {
        ApprovableBuildManager approvableBuildManager = (ApprovableBuildManager)this.myServiceLocator.getSingletonService(ApprovableBuildManager.class);
        BuildPromotionEx buildPromotionEx = (BuildPromotionEx)this.myBuildPromotionFinder.getBuildPromotion(null, buildLocator);
        ApprovalInfo.ApprovalStatus status = ApprovalInfo.ApprovalStatus.resolve((BuildPromotionEx)buildPromotionEx, (ApprovableBuildManager)approvableBuildManager);
        if (status != ApprovalInfo.ApprovalStatus.waitingForApproval) {
            throw new BadRequestException("This build cannot be approved, approval status is: " + String.valueOf(status));
        }
        SUser user = ((UserFinder)this.myServiceLocator.getSingletonService(UserFinder.class)).getCurrentUser();
        try {
            approvableBuildManager.addApprovedByUser(buildPromotionEx, user, approveAll != null && approveAll != false);
        }
        catch (IllegalStateException | AccessDeniedException e) {
            throw new BadRequestException(e.getMessage());
        }
        return new ApprovalInfo(buildPromotionEx, new Fields(fields), this.myBeanContext);
    }

    @PUT
    @Path(value="/pausedState")
    @Consumes(value={"application/xml", "application/json"})
    @ApiOperation(value="Change status of the build queue.", nickname="updateBuildQueueStatus")
    public void pauseBuildQueue(BuildQueueUpdate buildQueueUpdate) {
        if (buildQueueUpdate == null || buildQueueUpdate.paused == null) {
            throw new BadRequestException("It is required to specify the status of the build queue.");
        }
        SUser currentUser = ((UserFinder)this.myServiceLocator.getSingletonService(UserFinder.class)).getCurrentUser();
        if (!this.isUserAllowedToPauseBuildQueue(currentUser)) {
            throw new AccessDeniedException((AuthorityHolder)currentUser, "The input user is null or is not authorized to pause/resume a build queue. Please check your permission.");
        }
        QueueStateManager queueStateManager = (QueueStateManager)this.myServiceLocator.getSingletonService(QueueStateManager.class);
        QueueState newQueueState = new QueueState(buildQueueUpdate.paused == false, currentUser, this.parseBuildQueueReason(buildQueueUpdate.reason, buildQueueUpdate.paused), Date.from(Instant.now()), Actor.USER);
        queueStateManager.writeQueueState(newQueueState);
    }

    private String parseBuildQueueReason(@Nullable String reason, boolean isBuildPaused) {
        if (reason == null || reason.isEmpty()) {
            return "Build queue processing was " + (isBuildPaused ? "paused" : "resumed") + " via REST API.";
        }
        return reason;
    }

    private boolean isUserAllowedToPauseBuildQueue(@Nullable SUser currentUser) {
        return currentUser != null && currentUser.isPermissionGrantedGlobally(Permission.ENABLE_DISABLE_AGENT);
    }

    private static /* synthetic */ void $$$reportNull$$$0(int n) {
        Object[] objectArray;
        Object[] objectArray2;
        Object[] objectArray3 = new Object[switch (n) {
            default -> 2;
            case 1, 2, 4, 5, 7 -> 3;
        }];
        switch (n) {
            default: {
                objectArray2 = objectArray3;
                objectArray3[0] = "jetbrains/buildServer/server/rest/request/BuildQueueRequest";
                break;
            }
            case 1: {
                objectArray2 = objectArray3;
                objectArray3[0] = "beanContext";
                break;
            }
            case 2: {
                objectArray2 = objectArray3;
                objectArray3[0] = "buildsToTrigger";
                break;
            }
            case 4: {
                objectArray2 = objectArray3;
                objectArray3[0] = "builds";
                break;
            }
            case 5: {
                objectArray2 = objectArray3;
                objectArray3[0] = "buildPromotionIdReplacements";
                break;
            }
            case 7: {
                objectArray2 = objectArray3;
                objectArray3[0] = "build";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray2;
                objectArray2[1] = "getQueuedBuildHref";
                break;
            }
            case 1: 
            case 2: 
            case 4: 
            case 5: 
            case 7: {
                objectArray = objectArray2;
                objectArray2[1] = "jetbrains/buildServer/server/rest/request/BuildQueueRequest";
                break;
            }
            case 3: {
                objectArray = objectArray2;
                objectArray2[1] = "listBuildIds";
                break;
            }
            case 6: {
                objectArray = objectArray2;
                objectArray2[1] = "triggerBuilds";
                break;
            }
            case 8: {
                objectArray = objectArray2;
                objectArray2[1] = "getBuildPromotionLocatorDefaults";
                break;
            }
        }
        switch (n) {
            default: {
                break;
            }
            case 1: {
                objectArray = objectArray;
                objectArray[2] = "initForTests";
                break;
            }
            case 2: {
                objectArray = objectArray;
                objectArray[2] = "listBuildIds";
                break;
            }
            case 4: 
            case 5: {
                objectArray = objectArray;
                objectArray[2] = "triggerBuilds";
                break;
            }
            case 7: {
                objectArray = objectArray;
                objectArray[2] = "cancelQueuedBuild";
                break;
            }
        }
        String string = String.format(v0, objectArray);
        throw switch (n) {
            default -> new IllegalStateException(string);
            case 1, 2, 4, 5, 7 -> new IllegalArgumentException(string);
        };
    }
}
