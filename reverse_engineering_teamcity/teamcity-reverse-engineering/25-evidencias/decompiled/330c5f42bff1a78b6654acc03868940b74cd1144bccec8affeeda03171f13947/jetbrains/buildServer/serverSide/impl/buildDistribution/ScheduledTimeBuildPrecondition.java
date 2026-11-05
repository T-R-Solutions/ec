/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jetbrains.buildServer.BuildAgent
 *  jetbrains.buildServer.ExtensionHolder
 *  jetbrains.buildServer.TeamCityExtension
 *  jetbrains.buildServer.log.Loggers
 *  jetbrains.buildServer.serverSide.BuildPromotionEx
 *  jetbrains.buildServer.serverSide.ChangesCollectionCondition
 *  jetbrains.buildServer.serverSide.buildDistribution.BuildDistributorInput
 *  jetbrains.buildServer.serverSide.buildDistribution.QueuedBuildInfo
 *  jetbrains.buildServer.serverSide.buildDistribution.SimpleWaitReason
 *  jetbrains.buildServer.serverSide.buildDistribution.StartBuildPrecondition
 *  jetbrains.buildServer.serverSide.buildDistribution.WaitReason
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.ScheduledTimeBuildPrecondition$PauseChangesCollection
 *  jetbrains.buildServer.util.positioning.PositionAware
 *  jetbrains.buildServer.util.positioning.PositionConstraint
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package jetbrains.buildServer.serverSide.impl.buildDistribution;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;
import jetbrains.buildServer.BuildAgent;
import jetbrains.buildServer.ExtensionHolder;
import jetbrains.buildServer.TeamCityExtension;
import jetbrains.buildServer.log.Loggers;
import jetbrains.buildServer.serverSide.BuildPromotionEx;
import jetbrains.buildServer.serverSide.ChangesCollectionCondition;
import jetbrains.buildServer.serverSide.buildDistribution.BuildDistributorInput;
import jetbrains.buildServer.serverSide.buildDistribution.QueuedBuildInfo;
import jetbrains.buildServer.serverSide.buildDistribution.SimpleWaitReason;
import jetbrains.buildServer.serverSide.buildDistribution.StartBuildPrecondition;
import jetbrains.buildServer.serverSide.buildDistribution.WaitReason;
import jetbrains.buildServer.serverSide.impl.buildDistribution.ScheduledTimeBuildPrecondition;
import jetbrains.buildServer.util.positioning.PositionAware;
import jetbrains.buildServer.util.positioning.PositionConstraint;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Exception performing whole class analysis ignored.
 */
public class ScheduledTimeBuildPrecondition
implements StartBuildPrecondition,
PositionAware {
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd MMM HH:mm:ss z");

    public ScheduledTimeBuildPrecondition(@NotNull ExtensionHolder extensionHolder) {
        if (extensionHolder == null) {
            ScheduledTimeBuildPrecondition.$$$reportNull$$$0(0);
        }
        extensionHolder.registerExtension(ChangesCollectionCondition.class, "ChangesCollectionCondition", (TeamCityExtension)new PauseChangesCollection(this));
    }

    @Nullable
    public WaitReason canStart(@NotNull QueuedBuildInfo queuedBuildInfo, @NotNull Map<QueuedBuildInfo, BuildAgent> map, @NotNull BuildDistributorInput buildDistributorInput, boolean bl) {
        try {
            if (queuedBuildInfo == null) {
                ScheduledTimeBuildPrecondition.$$$reportNull$$$0(1);
            }
        }
        catch (DateTimeParseException dateTimeParseException) {
            throw ScheduledTimeBuildPrecondition.a(dateTimeParseException);
        }
        try {
            if (map == null) {
                ScheduledTimeBuildPrecondition.$$$reportNull$$$0(2);
            }
        }
        catch (DateTimeParseException dateTimeParseException) {
            throw ScheduledTimeBuildPrecondition.a(dateTimeParseException);
        }
        try {
            if (buildDistributorInput == null) {
                ScheduledTimeBuildPrecondition.$$$reportNull$$$0(3);
            }
        }
        catch (DateTimeParseException dateTimeParseException) {
            throw ScheduledTimeBuildPrecondition.a(dateTimeParseException);
        }
        ZonedDateTime zonedDateTime = this.shouldWaitUntil((BuildPromotionEx)queuedBuildInfo.getBuildPromotionInfo());
        try {
            if (zonedDateTime != null) {
                return new SimpleWaitReason("This build is scheduled for " + zonedDateTime.format(FORMATTER));
            }
        }
        catch (DateTimeParseException dateTimeParseException) {
            throw ScheduledTimeBuildPrecondition.a(dateTimeParseException);
        }
        return null;
    }

    @Nullable
    private ZonedDateTime shouldWaitUntil(@NotNull BuildPromotionEx buildPromotionEx) {
        try {
            if (buildPromotionEx == null) {
                ScheduledTimeBuildPrecondition.$$$reportNull$$$0(4);
            }
        }
        catch (DateTimeParseException dateTimeParseException) {
            throw ScheduledTimeBuildPrecondition.a(dateTimeParseException);
        }
        Object object = buildPromotionEx.getAttribute("teamcity.build.scheduled.time");
        if (object != null) {
            try {
                ZonedDateTime zonedDateTime = ZonedDateTime.parse(object.toString());
                if (zonedDateTime.toInstant().isAfter(Instant.now())) {
                    return zonedDateTime;
                }
            }
            catch (DateTimeParseException dateTimeParseException) {
                Loggers.SERVER.warn("Unexpected scheduled time attribute '" + String.valueOf(object) + "' for the build id=" + buildPromotionEx.getId());
                return null;
            }
        }
        return null;
    }

    @NotNull
    public String getOrderId() {
        String string;
        try {
            string = this.getClass().getSimpleName();
            if (string == null) {
                ScheduledTimeBuildPrecondition.$$$reportNull$$$0(5);
            }
        }
        catch (DateTimeParseException dateTimeParseException) {
            throw ScheduledTimeBuildPrecondition.a(dateTimeParseException);
        }
        return string;
    }

    @NotNull
    public PositionConstraint getConstraint() {
        PositionConstraint positionConstraint;
        try {
            positionConstraint = PositionConstraint.first();
            if (positionConstraint == null) {
                ScheduledTimeBuildPrecondition.$$$reportNull$$$0(6);
            }
        }
        catch (DateTimeParseException dateTimeParseException) {
            throw ScheduledTimeBuildPrecondition.a(dateTimeParseException);
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

    private static DateTimeParseException a(DateTimeParseException dateTimeParseException) {
        return dateTimeParseException;
    }
}
