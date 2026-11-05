/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.intellij.openapi.diagnostic.Logger
 *  jetbrains.buildServer.BuildAgent
 *  jetbrains.buildServer.log.Loggable
 *  jetbrains.buildServer.serverSide.Parameter
 *  jetbrains.buildServer.serverSide.SBuildType
 *  jetbrains.buildServer.serverSide.SProject
 *  jetbrains.buildServer.serverSide.TeamCityProperties
 *  jetbrains.buildServer.serverSide.buildDistribution.BuildConfigurationInfo
 *  jetbrains.buildServer.serverSide.buildDistribution.BuildDistributorInput
 *  jetbrains.buildServer.serverSide.buildDistribution.QueuedBuildInfo
 *  jetbrains.buildServer.serverSide.buildDistribution.SimpleWaitReason
 *  jetbrains.buildServer.serverSide.buildDistribution.StartBuildPrecondition
 *  jetbrains.buildServer.serverSide.buildDistribution.WaitReason
 *  jetbrains.buildServer.serverSide.impl.LogUtil
 *  jetbrains.buildServer.serverSide.impl.ProjectEx
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.StartingBuildsLimitPrecondition$StartingBuildsLimit
 *  jetbrains.buildServer.util.positioning.PositionAware
 *  jetbrains.buildServer.util.positioning.PositionConstraint
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package jetbrains.buildServer.serverSide.impl.buildDistribution;

import com.intellij.openapi.diagnostic.Logger;
import java.util.List;
import java.util.Map;
import jetbrains.buildServer.BuildAgent;
import jetbrains.buildServer.log.Loggable;
import jetbrains.buildServer.serverSide.Parameter;
import jetbrains.buildServer.serverSide.SBuildType;
import jetbrains.buildServer.serverSide.SProject;
import jetbrains.buildServer.serverSide.TeamCityProperties;
import jetbrains.buildServer.serverSide.buildDistribution.BuildConfigurationInfo;
import jetbrains.buildServer.serverSide.buildDistribution.BuildDistributorInput;
import jetbrains.buildServer.serverSide.buildDistribution.QueuedBuildInfo;
import jetbrains.buildServer.serverSide.buildDistribution.SimpleWaitReason;
import jetbrains.buildServer.serverSide.buildDistribution.StartBuildPrecondition;
import jetbrains.buildServer.serverSide.buildDistribution.WaitReason;
import jetbrains.buildServer.serverSide.impl.LogUtil;
import jetbrains.buildServer.serverSide.impl.ProjectEx;
import jetbrains.buildServer.serverSide.impl.buildDistribution.StartingBuildsLimitPrecondition;
import jetbrains.buildServer.util.positioning.PositionAware;
import jetbrains.buildServer.util.positioning.PositionConstraint;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Exception performing whole class analysis ignored.
 */
public class StartingBuildsLimitPrecondition
implements StartBuildPrecondition,
PositionAware {
    private static final Logger LOG = Logger.getInstance((String)StartingBuildsLimitPrecondition.class.getName());
    public static final String QUEUE_STARTING_BUILDS_LIMIT_ENABLED = "teamcity.queue.startingBuildsLimit.enabled";

    @Nullable
    public WaitReason canStart(@NotNull QueuedBuildInfo queuedBuildInfo, @NotNull Map<QueuedBuildInfo, BuildAgent> map, @NotNull BuildDistributorInput buildDistributorInput, boolean bl) {
        try {
            if (queuedBuildInfo == null) {
                StartingBuildsLimitPrecondition.$$$reportNull$$$0(0);
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw StartingBuildsLimitPrecondition.a(numberFormatException);
        }
        try {
            if (map == null) {
                StartingBuildsLimitPrecondition.$$$reportNull$$$0(1);
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw StartingBuildsLimitPrecondition.a(numberFormatException);
        }
        try {
            if (buildDistributorInput == null) {
                StartingBuildsLimitPrecondition.$$$reportNull$$$0(2);
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw StartingBuildsLimitPrecondition.a(numberFormatException);
        }
        try {
            if (!TeamCityProperties.getBoolean((String)"teamcity.queue.startingBuildsLimit.enabled")) {
                return null;
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw StartingBuildsLimitPrecondition.a(numberFormatException);
        }
        try {
            if (queuedBuildInfo.getBuildPromotionInfo().isAgentLessBuild()) {
                return null;
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw StartingBuildsLimitPrecondition.a(numberFormatException);
        }
        StartingBuildsLimit startingBuildsLimit = this.getLimit(queuedBuildInfo);
        try {
            if (startingBuildsLimit == null) {
                return null;
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw StartingBuildsLimitPrecondition.a(numberFormatException);
        }
        try {
            if (startingBuildsLimit.myLimit == 0) {
                return this.getWaitReason(startingBuildsLimit.myLimit);
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw StartingBuildsLimitPrecondition.a(numberFormatException);
        }
        int n = startingBuildsLimit.myLimit;
        for (QueuedBuildInfo queuedBuildInfo2 : map.keySet()) {
            try {
                if (queuedBuildInfo2.getBuildPromotionInfo().isAgentLessBuild()) {
                    continue;
                }
            }
            catch (NumberFormatException numberFormatException) {
                throw StartingBuildsLimitPrecondition.a(numberFormatException);
            }
            try {
                try {
                    if (!this.sameParentProject(startingBuildsLimit.myLimitedProject, queuedBuildInfo2) || --n > 0) continue;
                }
                catch (NumberFormatException numberFormatException) {
                    throw StartingBuildsLimitPrecondition.a(numberFormatException);
                }
                return this.getWaitReason(startingBuildsLimit.myLimit);
            }
            catch (NumberFormatException numberFormatException) {
                throw StartingBuildsLimitPrecondition.a(numberFormatException);
            }
        }
        return null;
    }

    private boolean sameParentProject(@NotNull SProject sProject, @NotNull QueuedBuildInfo queuedBuildInfo) {
        try {
            if (sProject == null) {
                StartingBuildsLimitPrecondition.$$$reportNull$$$0(3);
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw StartingBuildsLimitPrecondition.a(numberFormatException);
        }
        try {
            if (queuedBuildInfo == null) {
                StartingBuildsLimitPrecondition.$$$reportNull$$$0(4);
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw StartingBuildsLimitPrecondition.a(numberFormatException);
        }
        BuildConfigurationInfo buildConfigurationInfo = queuedBuildInfo.getBuildConfiguration();
        if (buildConfigurationInfo instanceof SBuildType) {
            SBuildType sBuildType = (SBuildType)buildConfigurationInfo;
            SProject sProject2 = sBuildType.getProject();
            while (true) {
                block12: {
                    try {
                        try {
                            if (sProject2 == null) break;
                            if (!sProject2.equals((Object)sProject)) break block12;
                        }
                        catch (NumberFormatException numberFormatException) {
                            throw StartingBuildsLimitPrecondition.a(numberFormatException);
                        }
                        return true;
                    }
                    catch (NumberFormatException numberFormatException) {
                        throw StartingBuildsLimitPrecondition.a(numberFormatException);
                    }
                }
                sProject2 = sProject2.getParentProject();
            }
        }
        return false;
    }

    @NotNull
    private SimpleWaitReason getWaitReason(int n) {
        return new SimpleWaitReason("Reached the limit for a number of simultaneously starting builds in this project: " + n);
    }

    @Nullable
    private StartingBuildsLimit getLimit(@NotNull QueuedBuildInfo queuedBuildInfo) {
        try {
            if (queuedBuildInfo == null) {
                StartingBuildsLimitPrecondition.$$$reportNull$$$0(5);
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw StartingBuildsLimitPrecondition.a(numberFormatException);
        }
        BuildConfigurationInfo buildConfigurationInfo = queuedBuildInfo.getBuildConfiguration();
        if (buildConfigurationInfo instanceof SBuildType) {
            SBuildType sBuildType = (SBuildType)buildConfigurationInfo;
            List list = sBuildType.getProject().getProjectPath();
            String string = "teamcity.internal.queue.subProjects.startingBuildsLimit";
            for (int i = list.size() - 2; i >= 0; --i) {
                ProjectEx projectEx = (ProjectEx)list.get(i);
                Parameter parameter = projectEx.getOwnNonInheritedParameter(string);
                try {
                    if (parameter == null) {
                        continue;
                    }
                }
                catch (NumberFormatException numberFormatException) {
                    throw StartingBuildsLimitPrecondition.a(numberFormatException);
                }
                try {
                    int n = Integer.parseInt(parameter.getValue());
                    return new StartingBuildsLimit((SProject)list.get(i + 1), n);
                }
                catch (NumberFormatException numberFormatException) {
                    try {
                        if (!LOG.isDebugEnabled()) continue;
                        LOG.debug("Could not parse parameter value, parameter: " + parameter.getName() + " defined in project: " + LogUtil.describe((Loggable)projectEx));
                        continue;
                    }
                    catch (NumberFormatException numberFormatException2) {
                        throw StartingBuildsLimitPrecondition.a(numberFormatException2);
                    }
                }
            }
        }
        return null;
    }

    @NotNull
    public String getOrderId() {
        String string;
        try {
            string = StartingBuildsLimitPrecondition.class.getName();
            if (string == null) {
                StartingBuildsLimitPrecondition.$$$reportNull$$$0(6);
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw StartingBuildsLimitPrecondition.a(numberFormatException);
        }
        return string;
    }

    @NotNull
    public PositionConstraint getConstraint() {
        PositionConstraint positionConstraint;
        try {
            positionConstraint = PositionConstraint.last();
            if (positionConstraint == null) {
                StartingBuildsLimitPrecondition.$$$reportNull$$$0(7);
            }
        }
        catch (NumberFormatException numberFormatException) {
            throw StartingBuildsLimitPrecondition.a(numberFormatException);
        }
        return positionConstraint;
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

    private static NumberFormatException a(NumberFormatException numberFormatException) {
        return numberFormatException;
    }
}
