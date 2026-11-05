/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.util.concurrent.Striped
 *  jetbrains.buildServer.Build
 *  jetbrains.buildServer.BuildAgent
 *  jetbrains.buildServer.log.Loggers
 *  jetbrains.buildServer.messages.Status
 *  jetbrains.buildServer.problems.BuildProblemUtil
 *  jetbrains.buildServer.serverSide.BuildPromotionEx
 *  jetbrains.buildServer.serverSide.BuildServerListener
 *  jetbrains.buildServer.serverSide.CurrentNodeInfo
 *  jetbrains.buildServer.serverSide.QueuedBuildEx
 *  jetbrains.buildServer.serverSide.WebLinks
 *  jetbrains.buildServer.serverSide.buildDistribution.BuildDistributorInput
 *  jetbrains.buildServer.serverSide.buildDistribution.QueuedBuildInfo
 *  jetbrains.buildServer.serverSide.buildDistribution.SimpleWaitReason
 *  jetbrains.buildServer.serverSide.buildDistribution.StartBuildPrecondition
 *  jetbrains.buildServer.serverSide.buildDistribution.WaitReason
 *  jetbrains.buildServer.serverSide.buildLog.BuildLogEx
 *  jetbrains.buildServer.serverSide.buildLog.MessageAttrs
 *  jetbrains.buildServer.serverSide.executors.ExecutorServices
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.CheckingForChangesPrecondition
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.UntrustedBuildsPrecondition$3
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.WaitApprovalBuildPrecondition
 *  jetbrains.buildServer.serverSide.impl.untrustedBuilds.UntrustedBuildReason
 *  jetbrains.buildServer.serverSide.impl.untrustedBuilds.UntrustedBuildsConstants
 *  jetbrains.buildServer.serverSide.impl.untrustedBuilds.UntrustedBuildsManager
 *  jetbrains.buildServer.serverSide.impl.untrustedBuilds.UntrustedBuildsSettings
 *  jetbrains.buildServer.serverSide.impl.untrustedBuilds.UntrustedBuildsSettings$DefaultAction
 *  jetbrains.buildServer.serverSide.impl.untrustedBuilds.UntrustedCheckResult
 *  jetbrains.buildServer.util.EventDispatcher
 *  jetbrains.buildServer.util.positioning.PositionAware
 *  jetbrains.buildServer.util.positioning.PositionConstraint
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package jetbrains.buildServer.serverSide.impl.buildDistribution;

import com.google.common.util.concurrent.Striped;
import java.util.Collection;
import java.util.Collections;
import java.util.EventListener;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.locks.Lock;
import java.util.stream.Collectors;
import jetbrains.buildServer.Build;
import jetbrains.buildServer.BuildAgent;
import jetbrains.buildServer.log.Loggers;
import jetbrains.buildServer.messages.Status;
import jetbrains.buildServer.problems.BuildProblemUtil;
import jetbrains.buildServer.serverSide.BuildPromotionEx;
import jetbrains.buildServer.serverSide.BuildServerListener;
import jetbrains.buildServer.serverSide.CurrentNodeInfo;
import jetbrains.buildServer.serverSide.QueuedBuildEx;
import jetbrains.buildServer.serverSide.WebLinks;
import jetbrains.buildServer.serverSide.buildDistribution.BuildDistributorInput;
import jetbrains.buildServer.serverSide.buildDistribution.QueuedBuildInfo;
import jetbrains.buildServer.serverSide.buildDistribution.SimpleWaitReason;
import jetbrains.buildServer.serverSide.buildDistribution.StartBuildPrecondition;
import jetbrains.buildServer.serverSide.buildDistribution.WaitReason;
import jetbrains.buildServer.serverSide.buildLog.BuildLogEx;
import jetbrains.buildServer.serverSide.buildLog.MessageAttrs;
import jetbrains.buildServer.serverSide.executors.ExecutorServices;
import jetbrains.buildServer.serverSide.impl.buildDistribution.CheckingForChangesPrecondition;
import jetbrains.buildServer.serverSide.impl.buildDistribution.UntrustedBuildsPrecondition;
import jetbrains.buildServer.serverSide.impl.buildDistribution.WaitApprovalBuildPrecondition;
import jetbrains.buildServer.serverSide.impl.untrustedBuilds.UntrustedBuildReason;
import jetbrains.buildServer.serverSide.impl.untrustedBuilds.UntrustedBuildsConstants;
import jetbrains.buildServer.serverSide.impl.untrustedBuilds.UntrustedBuildsManager;
import jetbrains.buildServer.serverSide.impl.untrustedBuilds.UntrustedBuildsSettings;
import jetbrains.buildServer.serverSide.impl.untrustedBuilds.UntrustedCheckResult;
import jetbrains.buildServer.util.EventDispatcher;
import jetbrains.buildServer.util.positioning.PositionAware;
import jetbrains.buildServer.util.positioning.PositionConstraint;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Exception performing whole class analysis ignored.
 */
public class UntrustedBuildsPrecondition
implements StartBuildPrecondition,
PositionAware {
    public static final String ORDER_ID = UntrustedBuildsPrecondition.class.getSimpleName();
    @NotNull
    private final UntrustedBuildsManager myUntrustedBuildsManager;
    @NotNull
    private final WebLinks myWebLinks;
    @NotNull
    private final ExecutorService myExecutorService;
    @NotNull
    private final Map<Long, Boolean> myUntrustedBuildNotLogged;
    private final Striped<Lock> myLocks;

    public UntrustedBuildsPrecondition(@NotNull UntrustedBuildsManager untrustedBuildsManager, @NotNull WebLinks webLinks, @NotNull EventDispatcher<BuildServerListener> eventDispatcher, @NotNull ExecutorServices executorServices) {
        if (untrustedBuildsManager == null) {
            UntrustedBuildsPrecondition.$$$reportNull$$$0(0);
        }
        if (webLinks == null) {
            UntrustedBuildsPrecondition.$$$reportNull$$$0(1);
        }
        if (eventDispatcher == null) {
            UntrustedBuildsPrecondition.$$$reportNull$$$0(2);
        }
        if (executorServices == null) {
            UntrustedBuildsPrecondition.$$$reportNull$$$0(3);
        }
        this.myLocks = Striped.lock((int)1000);
        this.myUntrustedBuildsManager = untrustedBuildsManager;
        this.myWebLinks = webLinks;
        this.myExecutorService = executorServices.getLowPriorityExecutorService();
        this.myUntrustedBuildNotLogged = Collections.synchronizedMap(new /* Unavailable Anonymous Inner Class!! */);
        eventDispatcher.addListener((EventListener)new /* Unavailable Anonymous Inner Class!! */);
    }

    @Nullable
    public WaitReason canStart(@NotNull QueuedBuildInfo queuedBuildInfo, @NotNull Map<QueuedBuildInfo, BuildAgent> map, @NotNull BuildDistributorInput buildDistributorInput, boolean bl) {
        block30: {
            BuildPromotionEx buildPromotionEx;
            block31: {
                try {
                    if (queuedBuildInfo == null) {
                        UntrustedBuildsPrecondition.$$$reportNull$$$0(4);
                    }
                }
                catch (RejectedExecutionException rejectedExecutionException) {
                    throw UntrustedBuildsPrecondition.a(rejectedExecutionException);
                }
                try {
                    if (map == null) {
                        UntrustedBuildsPrecondition.$$$reportNull$$$0(5);
                    }
                }
                catch (RejectedExecutionException rejectedExecutionException) {
                    throw UntrustedBuildsPrecondition.a(rejectedExecutionException);
                }
                try {
                    if (buildDistributorInput == null) {
                        UntrustedBuildsPrecondition.$$$reportNull$$$0(6);
                    }
                }
                catch (RejectedExecutionException rejectedExecutionException) {
                    throw UntrustedBuildsPrecondition.a(rejectedExecutionException);
                }
                try {
                    if (!UntrustedBuildsConstants.isEnabled()) {
                        return null;
                    }
                }
                catch (RejectedExecutionException rejectedExecutionException) {
                    throw UntrustedBuildsPrecondition.a(rejectedExecutionException);
                }
                buildPromotionEx = (BuildPromotionEx)queuedBuildInfo.getBuildPromotionInfo();
                UntrustedBuildsSettings untrustedBuildsSettings = this.myUntrustedBuildsManager.getUntrustedBuildsSettings(buildPromotionEx);
                try {
                    if (untrustedBuildsSettings == null) {
                        return null;
                    }
                }
                catch (RejectedExecutionException rejectedExecutionException) {
                    throw UntrustedBuildsPrecondition.a(rejectedExecutionException);
                }
                try {
                    if (untrustedBuildsSettings.getDefaultAction() != UntrustedBuildsSettings.DefaultAction.CANCEL && !untrustedBuildsSettings.shouldLogUntrustedBuilds()) break block30;
                }
                catch (RejectedExecutionException rejectedExecutionException) {
                    throw UntrustedBuildsPrecondition.a(rejectedExecutionException);
                }
                UntrustedCheckResult untrustedCheckResult = this.myUntrustedBuildsManager.isUntrusted(buildPromotionEx);
                try {
                    if (untrustedCheckResult == UntrustedCheckResult.WAITING) {
                        return new SimpleWaitReason("Failed to determine if build is untrusted");
                    }
                }
                catch (RejectedExecutionException rejectedExecutionException) {
                    throw UntrustedBuildsPrecondition.a(rejectedExecutionException);
                }
                try {
                    try {
                        try {
                            if (untrustedCheckResult != UntrustedCheckResult.UNTRUSTED || untrustedBuildsSettings.getDefaultAction() != UntrustedBuildsSettings.DefaultAction.CANCEL) break block30;
                        }
                        catch (RejectedExecutionException rejectedExecutionException) {
                            throw UntrustedBuildsPrecondition.a(rejectedExecutionException);
                        }
                        if (!bl) break block31;
                    }
                    catch (RejectedExecutionException rejectedExecutionException) {
                        throw UntrustedBuildsPrecondition.a(rejectedExecutionException);
                    }
                    return new SimpleWaitReason("Build will be canceled");
                }
                catch (RejectedExecutionException rejectedExecutionException) {
                    throw UntrustedBuildsPrecondition.a(rejectedExecutionException);
                }
            }
            if (queuedBuildInfo instanceof QueuedBuildEx) {
                List list = this.myUntrustedBuildsManager.getUntrustedReasons(buildPromotionEx);
                try {
                    if (!list.isEmpty()) {
                        this.failQueuedBuild((QueuedBuildEx)queuedBuildInfo, "Build was detected as untrusted: " + this.joinReasons(list));
                        return new SimpleWaitReason("Build will be canceled");
                    }
                }
                catch (RejectedExecutionException rejectedExecutionException) {
                    throw UntrustedBuildsPrecondition.a(rejectedExecutionException);
                }
            }
        }
        return null;
    }

    @NotNull
    public String getOrderId() {
        String string;
        try {
            string = ORDER_ID;
            if (string == null) {
                UntrustedBuildsPrecondition.$$$reportNull$$$0(7);
            }
        }
        catch (RejectedExecutionException rejectedExecutionException) {
            throw UntrustedBuildsPrecondition.a(rejectedExecutionException);
        }
        return string;
    }

    @NotNull
    public PositionConstraint getConstraint() {
        PositionConstraint positionConstraint;
        try {
            positionConstraint = PositionConstraint.after((String[])new String[]{CheckingForChangesPrecondition.ORDER_ID, WaitApprovalBuildPrecondition.ORDER_ID});
            if (positionConstraint == null) {
                UntrustedBuildsPrecondition.$$$reportNull$$$0(8);
            }
        }
        catch (RejectedExecutionException rejectedExecutionException) {
            throw UntrustedBuildsPrecondition.a(rejectedExecutionException);
        }
        return positionConstraint;
    }

    private void failQueuedBuild(@NotNull QueuedBuildEx queuedBuildEx, String string) {
        try {
            if (queuedBuildEx == null) {
                UntrustedBuildsPrecondition.$$$reportNull$$$0(9);
            }
        }
        catch (RejectedExecutionException rejectedExecutionException) {
            throw UntrustedBuildsPrecondition.a(rejectedExecutionException);
        }
        queuedBuildEx.terminate(false, "Build was detected as untrusted", BuildProblemUtil.createBuildProblem((String)"PREPARATION_FAILURE_TYPE", (String)string));
    }

    public String getBuildUrl(@NotNull BuildPromotionEx buildPromotionEx) {
        try {
            if (buildPromotionEx == null) {
                UntrustedBuildsPrecondition.$$$reportNull$$$0(10);
            }
        }
        catch (RejectedExecutionException rejectedExecutionException) {
            throw UntrustedBuildsPrecondition.a(rejectedExecutionException);
        }
        Object object = this.myWebLinks.getViewResultsUrl((Build)buildPromotionEx.getRealOrDummyBuild());
        if (((String)object).endsWith("-1")) {
            object = ((String)object).substring(0, ((String)object).length() - 2) + buildPromotionEx.getId();
        }
        return object;
    }

    private void submitLoggingTask(@NotNull BuildPromotionEx buildPromotionEx, @NotNull UntrustedBuildsSettings.DefaultAction defaultAction) {
        try {
            if (buildPromotionEx == null) {
                UntrustedBuildsPrecondition.$$$reportNull$$$0(11);
            }
        }
        catch (RejectedExecutionException rejectedExecutionException) {
            throw UntrustedBuildsPrecondition.a(rejectedExecutionException);
        }
        try {
            if (defaultAction == null) {
                UntrustedBuildsPrecondition.$$$reportNull$$$0(12);
            }
        }
        catch (RejectedExecutionException rejectedExecutionException) {
            throw UntrustedBuildsPrecondition.a(rejectedExecutionException);
        }
        try {
            this.myExecutorService.submit(() -> this.logToServerAndBuildLog(buildPromotionEx, defaultAction));
        }
        catch (RejectedExecutionException rejectedExecutionException) {
            this.logToServerAndBuildLog(buildPromotionEx, defaultAction);
        }
    }

    private void logToServerAndBuildLog(@NotNull BuildPromotionEx buildPromotionEx, @NotNull UntrustedBuildsSettings.DefaultAction defaultAction) {
        block10: {
            try {
                if (buildPromotionEx == null) {
                    UntrustedBuildsPrecondition.$$$reportNull$$$0(13);
                }
            }
            catch (RejectedExecutionException rejectedExecutionException) {
                throw UntrustedBuildsPrecondition.a(rejectedExecutionException);
            }
            try {
                if (defaultAction == null) {
                    UntrustedBuildsPrecondition.$$$reportNull$$$0(14);
                }
            }
            catch (RejectedExecutionException rejectedExecutionException) {
                throw UntrustedBuildsPrecondition.a(rejectedExecutionException);
            }
            List list = this.myUntrustedBuildsManager.getUntrustedReasons(buildPromotionEx);
            try {
                try {
                    if (list.isEmpty()) break block10;
                    this.logUntrustedBuild(this.getBuildUrl(buildPromotionEx), buildPromotionEx, list);
                    if (!UntrustedBuildsConstants.isBuildLogEnabled()) break block10;
                }
                catch (RejectedExecutionException rejectedExecutionException) {
                    throw UntrustedBuildsPrecondition.a(rejectedExecutionException);
                }
                if (!CurrentNodeInfo.isMainNode()) break block10;
            }
            catch (RejectedExecutionException rejectedExecutionException) {
                throw UntrustedBuildsPrecondition.a(rejectedExecutionException);
            }
            String string = this.getBuildLogMessage(list, defaultAction);
            BuildLogEx buildLogEx = buildPromotionEx.getBuildLog();
            buildLogEx.messageAsync(string, Status.WARNING, MessageAttrs.serverMessage());
            buildLogEx.flush();
        }
    }

    private String getBuildLogMessage(@NotNull List<UntrustedBuildReason> list, @NotNull UntrustedBuildsSettings.DefaultAction defaultAction) {
        try {
            if (list == null) {
                UntrustedBuildsPrecondition.$$$reportNull$$$0(15);
            }
        }
        catch (RejectedExecutionException rejectedExecutionException) {
            throw UntrustedBuildsPrecondition.a(rejectedExecutionException);
        }
        try {
            if (defaultAction == null) {
                UntrustedBuildsPrecondition.$$$reportNull$$$0(16);
            }
        }
        catch (RejectedExecutionException rejectedExecutionException) {
            throw UntrustedBuildsPrecondition.a(rejectedExecutionException);
        }
        return (switch (3.$SwitchMap$jetbrains$buildServer$serverSide$impl$untrustedBuilds$UntrustedBuildsSettings$DefaultAction[defaultAction.ordinal()]) {
            case 1 -> "Build is untrusted, so it won't be started until it is approved";
            default -> "Build was detected as untrusted";
        }) + ". Reasons: " + this.joinReasons(list);
    }

    private void logUntrustedBuild(@NotNull String string, @NotNull BuildPromotionEx buildPromotionEx, @NotNull List<UntrustedBuildReason> list) {
        try {
            if (string == null) {
                UntrustedBuildsPrecondition.$$$reportNull$$$0(17);
            }
        }
        catch (RejectedExecutionException rejectedExecutionException) {
            throw UntrustedBuildsPrecondition.a(rejectedExecutionException);
        }
        try {
            if (buildPromotionEx == null) {
                UntrustedBuildsPrecondition.$$$reportNull$$$0(18);
            }
        }
        catch (RejectedExecutionException rejectedExecutionException) {
            throw UntrustedBuildsPrecondition.a(rejectedExecutionException);
        }
        try {
            if (list == null) {
                UntrustedBuildsPrecondition.$$$reportNull$$$0(19);
            }
        }
        catch (RejectedExecutionException rejectedExecutionException) {
            throw UntrustedBuildsPrecondition.a(rejectedExecutionException);
        }
        Loggers.SERVER.warn(String.format("Build(promotion id: %d, configuration id: %s) detected as untrusted. Reasons: %s Build URL: %s", buildPromotionEx.getId(), buildPromotionEx.getBuildTypeExternalId(), list.stream().map(untrustedBuildReason -> untrustedBuildReason.getLogDescription()).collect(Collectors.joining(" | ", "{", "}")), string));
    }

    private String joinReasons(@NotNull Collection<UntrustedBuildReason> collection) {
        try {
            if (collection == null) {
                UntrustedBuildsPrecondition.$$$reportNull$$$0(20);
            }
        }
        catch (RejectedExecutionException rejectedExecutionException) {
            throw UntrustedBuildsPrecondition.a(rejectedExecutionException);
        }
        return collection.stream().map(untrustedBuildReason -> untrustedBuildReason.getLogDescription()).collect(Collectors.joining(", "));
    }

    /*
     * Exception decompiling
     */
    private static /* synthetic */ void $$$reportNull$$$0(int var0) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 6[SWITCH]
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:435)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:484)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1050)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doClass(Driver.java:84)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:78)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    private static RejectedExecutionException a(RejectedExecutionException rejectedExecutionException) {
        return rejectedExecutionException;
    }
}
