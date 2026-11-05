/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jetbrains.buildServer.BuildAgent
 *  jetbrains.buildServer.serverSide.BuildPromotionEx
 *  jetbrains.buildServer.serverSide.BuildServerEx
 *  jetbrains.buildServer.serverSide.SBuildAgent
 *  jetbrains.buildServer.serverSide.buildDistribution.BuildDistributorInput
 *  jetbrains.buildServer.serverSide.buildDistribution.QueuedBuildInfo
 *  jetbrains.buildServer.serverSide.buildDistribution.SimpleWaitReason
 *  jetbrains.buildServer.serverSide.buildDistribution.StartBuildPrecondition
 *  jetbrains.buildServer.serverSide.buildDistribution.WaitReason
 *  jetbrains.buildServer.serverSide.impl.LicensingInfo
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.BuildDistributorInputEx
 *  jetbrains.buildServer.util.positioning.PositionAware
 *  jetbrains.buildServer.util.positioning.PositionConstraint
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package jetbrains.buildServer.serverSide.impl.buildDistribution;

import java.util.Map;
import jetbrains.buildServer.BuildAgent;
import jetbrains.buildServer.serverSide.BuildPromotionEx;
import jetbrains.buildServer.serverSide.BuildServerEx;
import jetbrains.buildServer.serverSide.SBuildAgent;
import jetbrains.buildServer.serverSide.buildDistribution.BuildDistributorInput;
import jetbrains.buildServer.serverSide.buildDistribution.QueuedBuildInfo;
import jetbrains.buildServer.serverSide.buildDistribution.SimpleWaitReason;
import jetbrains.buildServer.serverSide.buildDistribution.StartBuildPrecondition;
import jetbrains.buildServer.serverSide.buildDistribution.WaitReason;
import jetbrains.buildServer.serverSide.impl.LicensingInfo;
import jetbrains.buildServer.serverSide.impl.buildDistribution.BuildDistributorInputEx;
import jetbrains.buildServer.util.positioning.PositionAware;
import jetbrains.buildServer.util.positioning.PositionConstraint;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Exception performing whole class analysis ignored.
 */
public class LicensePrecondition
implements StartBuildPrecondition,
PositionAware {
    private static final String CONCURRENT_BUILDS_CUSTOM_KEY = "concurrentBuilds";
    public static final String ORDER_ID = "licensePrecondition";
    private final BuildServerEx myBuildServer;

    public LicensePrecondition(@NotNull BuildServerEx buildServerEx) {
        if (buildServerEx == null) {
            LicensePrecondition.$$$reportNull$$$0(0);
        }
        this.myBuildServer = buildServerEx;
    }

    @Nullable
    public WaitReason canStart(@NotNull QueuedBuildInfo queuedBuildInfo, @NotNull Map<QueuedBuildInfo, BuildAgent> map, @NotNull BuildDistributorInput buildDistributorInput, boolean bl) {
        block22: {
            try {
                if (queuedBuildInfo == null) {
                    LicensePrecondition.$$$reportNull$$$0(1);
                }
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw LicensePrecondition.a(illegalArgumentException);
            }
            try {
                if (map == null) {
                    LicensePrecondition.$$$reportNull$$$0(2);
                }
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw LicensePrecondition.a(illegalArgumentException);
            }
            try {
                if (buildDistributorInput == null) {
                    LicensePrecondition.$$$reportNull$$$0(3);
                }
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw LicensePrecondition.a(illegalArgumentException);
            }
            LicensingInfo licensingInfo = this.myBuildServer.getLicensingInfo();
            try {
                if (licensingInfo.isMaxNumberOfAuthorizedAgentsExceeded()) {
                    return new SimpleWaitReason("Maximum number of authorized agents exceeded");
                }
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw LicensePrecondition.a(illegalArgumentException);
            }
            try {
                if (licensingInfo.isMaxNumberOfBuildTypesExceeded()) {
                    return new SimpleWaitReason("Maximum number of build configurations exceeded");
                }
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw LicensePrecondition.a(illegalArgumentException);
            }
            try {
                if (licensingInfo.isMaxNumberOfPipelinesExceeded()) {
                    return new SimpleWaitReason("Maximum number of pipelines exceeded");
                }
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw LicensePrecondition.a(illegalArgumentException);
            }
            try {
                try {
                    if (!((BuildPromotionEx)queuedBuildInfo.getBuildPromotionInfo()).occupiesLicenseSlot() || !this.isMaxNumberOfConcurrentBuildsReached(map, buildDistributorInput)) break block22;
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw LicensePrecondition.a(illegalArgumentException);
                }
                return new SimpleWaitReason("Maximum number of concurrent builds reached");
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw LicensePrecondition.a(illegalArgumentException);
            }
        }
        return null;
    }

    private boolean isMaxNumberOfConcurrentBuildsReached(@NotNull Map<QueuedBuildInfo, BuildAgent> map, @NotNull BuildDistributorInput buildDistributorInput) {
        boolean bl;
        try {
            if (map == null) {
                LicensePrecondition.$$$reportNull$$$0(4);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw LicensePrecondition.a(illegalArgumentException);
        }
        try {
            if (buildDistributorInput == null) {
                LicensePrecondition.$$$reportNull$$$0(5);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw LicensePrecondition.a(illegalArgumentException);
        }
        long l = this.myBuildServer.getLicensingPolicy().getMaxNumberOfAuthorizedAgents();
        try {
            if (l == -1L) {
                return false;
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw LicensePrecondition.a(illegalArgumentException);
        }
        Long l2 = this.getRunningBuildsCount((BuildDistributorInputEx)buildDistributorInput);
        if (l2 == null) {
            l2 = buildDistributorInput.getRunningBuilds().stream().filter(runningBuildInfo -> ((BuildPromotionEx)runningBuildInfo.getBuildPromotionInfo()).occupiesLicenseSlot()).count();
            this.saveRunningBuildsCount((BuildDistributorInputEx)buildDistributorInput, l2);
        }
        try {
            if (l2 + (long)map.size() < l) {
                return false;
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw LicensePrecondition.a(illegalArgumentException);
        }
        long l3 = map.entrySet().stream().filter(entry -> {
            boolean bl;
            block5: {
                block4: {
                    try {
                        try {
                            if (!(entry.getValue() instanceof SBuildAgent) && !((BuildPromotionEx)((QueuedBuildInfo)entry.getKey()).getBuildPromotionInfo()).occupiesLicenseSlot()) break block4;
                        }
                        catch (IllegalArgumentException illegalArgumentException) {
                            throw LicensePrecondition.a(illegalArgumentException);
                        }
                        bl = true;
                        break block5;
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        throw LicensePrecondition.a(illegalArgumentException);
                    }
                }
                bl = false;
            }
            return bl;
        }).count();
        try {
            bl = l2 + l3 >= l;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw LicensePrecondition.a(illegalArgumentException);
        }
        return bl;
    }

    private void saveRunningBuildsCount(@NotNull BuildDistributorInputEx buildDistributorInputEx, long l) {
        try {
            if (buildDistributorInputEx == null) {
                LicensePrecondition.$$$reportNull$$$0(6);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw LicensePrecondition.a(illegalArgumentException);
        }
        buildDistributorInputEx.setCustomData("concurrentBuilds", (Object)l);
    }

    private Long getRunningBuildsCount(@NotNull BuildDistributorInputEx buildDistributorInputEx) {
        try {
            if (buildDistributorInputEx == null) {
                LicensePrecondition.$$$reportNull$$$0(7);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw LicensePrecondition.a(illegalArgumentException);
        }
        return (Long)buildDistributorInputEx.getCustomData("concurrentBuilds", Long.class);
    }

    @NotNull
    public PositionConstraint getConstraint() {
        PositionConstraint positionConstraint;
        try {
            positionConstraint = PositionConstraint.first();
            if (positionConstraint == null) {
                LicensePrecondition.$$$reportNull$$$0(8);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw LicensePrecondition.a(illegalArgumentException);
        }
        return positionConstraint;
    }

    @NotNull
    public String getOrderId() {
        return "licensePrecondition";
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

    private static IllegalArgumentException a(IllegalArgumentException illegalArgumentException) {
        return illegalArgumentException;
    }
}
