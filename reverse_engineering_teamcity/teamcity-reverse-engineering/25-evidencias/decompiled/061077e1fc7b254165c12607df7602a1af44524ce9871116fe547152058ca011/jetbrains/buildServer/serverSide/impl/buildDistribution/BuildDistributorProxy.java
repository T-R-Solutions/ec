/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.intellij.openapi.diagnostic.Logger
 *  jetbrains.buildServer.ExtensionsProvider
 *  jetbrains.buildServer.StampedExtensionsSupplier
 *  jetbrains.buildServer.serverSide.SBuildAgent
 *  jetbrains.buildServer.serverSide.TeamCityProperties
 *  jetbrains.buildServer.serverSide.buildDistribution.QueuedBuildInfo
 *  jetbrains.buildServer.serverSide.buildDistribution.WaitReason
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.AgentlessBuildStarter
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.BuildDistributor
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.BuildDistributor$QueuedBuildOptimizer
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.BuildDistributor$QueuedBuildTerminator
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.BuildDistributorInputEx
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.BuildStarter
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.CompositeBuildStarter
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.DistributionResult
 *  jetbrains.buildServer.serverSide.impl.buildDistribution.SetWaitReasonCallback
 *  jetbrains.buildServer.serverSide.impl.executors.ExecutorDescription
 *  jetbrains.buildServer.serverSide.impl.virtualAgent.VirtualAgent
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package jetbrains.buildServer.serverSide.impl.buildDistribution;

import com.intellij.openapi.diagnostic.Logger;
import java.util.Collection;
import java.util.Map;
import jetbrains.buildServer.ExtensionsProvider;
import jetbrains.buildServer.StampedExtensionsSupplier;
import jetbrains.buildServer.serverSide.SBuildAgent;
import jetbrains.buildServer.serverSide.TeamCityProperties;
import jetbrains.buildServer.serverSide.buildDistribution.QueuedBuildInfo;
import jetbrains.buildServer.serverSide.buildDistribution.WaitReason;
import jetbrains.buildServer.serverSide.impl.buildDistribution.AgentlessBuildStarter;
import jetbrains.buildServer.serverSide.impl.buildDistribution.BuildDistributor;
import jetbrains.buildServer.serverSide.impl.buildDistribution.BuildDistributorInputEx;
import jetbrains.buildServer.serverSide.impl.buildDistribution.BuildStarter;
import jetbrains.buildServer.serverSide.impl.buildDistribution.CompositeBuildStarter;
import jetbrains.buildServer.serverSide.impl.buildDistribution.DistributionResult;
import jetbrains.buildServer.serverSide.impl.buildDistribution.SetWaitReasonCallback;
import jetbrains.buildServer.serverSide.impl.executors.ExecutorDescription;
import jetbrains.buildServer.serverSide.impl.virtualAgent.VirtualAgent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Exception performing whole class analysis ignored.
 */
public class BuildDistributorProxy
implements BuildDistributor {
    private static final Logger LOG = Logger.getInstance((String)BuildDistributorProxy.class.getName());
    public static final String TEAMCITY_BUILD_DISTRIBUTOR = "teamcity.build.distributor";
    @NotNull
    private final StampedExtensionsSupplier<String, BuildDistributor> myDistributorExtensionSupplier;

    public BuildDistributorProxy(@NotNull BuildDistributor buildDistributor, @NotNull ExtensionsProvider extensionsProvider) {
        if (buildDistributor == null) {
            BuildDistributorProxy.$$$reportNull$$$0(0);
        }
        if (extensionsProvider == null) {
            BuildDistributorProxy.$$$reportNull$$$0(1);
        }
        this.myDistributorExtensionSupplier = extensionsProvider.getStampedExtensionsSupplier(BuildDistributor.class, storedData -> {
            String string = (String)storedData.context;
            try {
                if (string == null) {
                    LOG.info("Default build distributor will be used");
                    return buildDistributor;
                }
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw BuildDistributorProxy.a(illegalArgumentException);
            }
            for (BuildDistributor buildDistributor2 : (Collection)storedData.data) {
                try {
                    if (!buildDistributor2.getClass().getName().equals(string)) continue;
                    LOG.info("Required build distributor found and will be used: " + String.valueOf(buildDistributor2));
                    return buildDistributor2;
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw BuildDistributorProxy.a(illegalArgumentException);
                }
            }
            LOG.warn("Build distributor was not found, default will be used instead of required " + string);
            return buildDistributor;
        });
    }

    @NotNull
    public BuildDistributor getActualDistributor() {
        BuildDistributor buildDistributor;
        try {
            buildDistributor = (BuildDistributor)this.myDistributorExtensionSupplier.get((Object)TeamCityProperties.getPropertyOrNull((String)"teamcity.build.distributor"));
            if (buildDistributor == null) {
                BuildDistributorProxy.$$$reportNull$$$0(2);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildDistributorProxy.a(illegalArgumentException);
        }
        return buildDistributor;
    }

    @NotNull
    public DistributionResult<SBuildAgent> assignBuildsToAgents(@NotNull BuildDistributorInputEx buildDistributorInputEx, @Nullable Map<QueuedBuildInfo, WaitReason> map, @NotNull BuildStarter<SBuildAgent> buildStarter, @Nullable BuildStarter<VirtualAgent> buildStarter2, @Nullable BuildStarter<ExecutorDescription> buildStarter3, @NotNull SetWaitReasonCallback setWaitReasonCallback, @NotNull CompositeBuildStarter compositeBuildStarter, @NotNull AgentlessBuildStarter agentlessBuildStarter, @NotNull BuildDistributor.QueuedBuildOptimizer queuedBuildOptimizer, @NotNull BuildDistributor.QueuedBuildTerminator queuedBuildTerminator, boolean bl) {
        DistributionResult distributionResult;
        try {
            if (buildDistributorInputEx == null) {
                BuildDistributorProxy.$$$reportNull$$$0(3);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildDistributorProxy.a(illegalArgumentException);
        }
        try {
            if (buildStarter == null) {
                BuildDistributorProxy.$$$reportNull$$$0(4);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildDistributorProxy.a(illegalArgumentException);
        }
        try {
            if (setWaitReasonCallback == null) {
                BuildDistributorProxy.$$$reportNull$$$0(5);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildDistributorProxy.a(illegalArgumentException);
        }
        try {
            if (compositeBuildStarter == null) {
                BuildDistributorProxy.$$$reportNull$$$0(6);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildDistributorProxy.a(illegalArgumentException);
        }
        try {
            if (agentlessBuildStarter == null) {
                BuildDistributorProxy.$$$reportNull$$$0(7);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildDistributorProxy.a(illegalArgumentException);
        }
        try {
            if (queuedBuildOptimizer == null) {
                BuildDistributorProxy.$$$reportNull$$$0(8);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildDistributorProxy.a(illegalArgumentException);
        }
        try {
            if (queuedBuildTerminator == null) {
                BuildDistributorProxy.$$$reportNull$$$0(9);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildDistributorProxy.a(illegalArgumentException);
        }
        try {
            distributionResult = this.getActualDistributor().assignBuildsToAgents(buildDistributorInputEx, map, buildStarter, buildStarter2, buildStarter3, setWaitReasonCallback, compositeBuildStarter, agentlessBuildStarter, queuedBuildOptimizer, queuedBuildTerminator, bl);
            if (distributionResult == null) {
                BuildDistributorProxy.$$$reportNull$$$0(10);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw BuildDistributorProxy.a(illegalArgumentException);
        }
        return distributionResult;
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
