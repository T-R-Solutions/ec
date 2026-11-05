/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jetbrains.buildServer.agent.BuildAgentConfiguration
 *  jetbrains.buildServer.agent.impl.RegistrationLogger
 *  jetbrains.buildServer.agent.impl.serverCommunication.CommunicationProtocol
 *  jetbrains.buildServer.agent.impl.serverCommunication.IServerCommunicationProtocolResolver
 *  jetbrains.buildServer.agent.impl.serverCommunication.PollingProtocol
 *  jetbrains.buildServer.http.HttpApi
 *  jetbrains.buildServer.messages.XStreamHolder
 *  jetbrains.buildServer.xstream.XStreamWrapper
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package jetbrains.buildServer.agent.impl.serverCommunication;

import java.util.ArrayList;
import java.util.List;
import jetbrains.buildServer.agent.BuildAgentConfiguration;
import jetbrains.buildServer.agent.impl.RegistrationLogger;
import jetbrains.buildServer.agent.impl.serverCommunication.CommunicationProtocol;
import jetbrains.buildServer.agent.impl.serverCommunication.IServerCommunicationProtocolResolver;
import jetbrains.buildServer.agent.impl.serverCommunication.PollingProtocol;
import jetbrains.buildServer.http.HttpApi;
import jetbrains.buildServer.messages.XStreamHolder;
import jetbrains.buildServer.xstream.XStreamWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class HttpBasedProtocolResolver
implements IServerCommunicationProtocolResolver {
    private static final String PROTOCOLS_PATH = "/app/agents/protocols";
    private final XStreamHolder myXStreamHolder;
    private final BuildAgentConfiguration myConfiguration;
    private final PollingProtocol myPollingProtocol;
    private final HttpApi myServerApi;

    public HttpBasedProtocolResolver(@NotNull BuildAgentConfiguration configuration, @NotNull PollingProtocol pollingProtocol, @NotNull HttpApi serverApi) {
        if (configuration == null) {
            HttpBasedProtocolResolver.$$$reportNull$$$0(0);
        }
        if (pollingProtocol == null) {
            HttpBasedProtocolResolver.$$$reportNull$$$0(1);
        }
        if (serverApi == null) {
            HttpBasedProtocolResolver.$$$reportNull$$$0(2);
        }
        this.myXStreamHolder = new XStreamHolder();
        this.myConfiguration = configuration;
        this.myPollingProtocol = pollingProtocol;
        this.myServerApi = serverApi;
    }

    /*
     * Exception decompiling
     */
    @NotNull
    public Iterable<CommunicationProtocol> resolve() {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 5 blocks at once
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doClass(Driver.java:84)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:78)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    @NotNull
    private List<CommunicationProtocol> fromIds(@NotNull List<String> protocolsIds) {
        if (protocolsIds == null) {
            HttpBasedProtocolResolver.$$$reportNull$$$0(10);
        }
        ArrayList<CommunicationProtocol> result = new ArrayList<CommunicationProtocol>();
        for (String id : protocolsIds) {
            if (!this.myPollingProtocol.getId().equals(id)) continue;
            result.add((CommunicationProtocol)this.myPollingProtocol);
        }
        ArrayList<CommunicationProtocol> arrayList = result;
        if (arrayList == null) {
            HttpBasedProtocolResolver.$$$reportNull$$$0(11);
        }
        return arrayList;
    }

    @Nullable
    private List<String> tryParse(String responseBody) {
        try {
            return (List)XStreamWrapper.deserializeObject((String)responseBody, (XStreamHolder)this.myXStreamHolder);
        }
        catch (Exception e) {
            RegistrationLogger.warn((String)"Error while parsing communication protocols returned by the server", (Throwable)e);
            return null;
        }
    }

    private static /* synthetic */ void $$$reportNull$$$0(int n) {
        Object[] objectArray;
        Object[] objectArray2;
        Object[] objectArray3 = new Object[switch (n) {
            default -> 3;
            case 3, 4, 5, 6, 7, 8, 9, 11 -> 2;
        }];
        switch (n) {
            default: {
                objectArray2 = objectArray3;
                objectArray3[0] = "configuration";
                break;
            }
            case 1: {
                objectArray2 = objectArray3;
                objectArray3[0] = "pollingProtocol";
                break;
            }
            case 2: {
                objectArray2 = objectArray3;
                objectArray3[0] = "serverApi";
                break;
            }
            case 3: 
            case 4: 
            case 5: 
            case 6: 
            case 7: 
            case 8: 
            case 9: 
            case 11: {
                objectArray2 = objectArray3;
                objectArray3[0] = "jetbrains/buildServer/agent/impl/serverCommunication/HttpBasedProtocolResolver";
                break;
            }
            case 10: {
                objectArray2 = objectArray3;
                objectArray3[0] = "protocolsIds";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray2;
                objectArray2[1] = "jetbrains/buildServer/agent/impl/serverCommunication/HttpBasedProtocolResolver";
                break;
            }
            case 3: 
            case 4: 
            case 5: 
            case 6: 
            case 7: 
            case 8: 
            case 9: {
                objectArray = objectArray2;
                objectArray2[1] = "resolve";
                break;
            }
            case 11: {
                objectArray = objectArray2;
                objectArray2[1] = "fromIds";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray;
                objectArray[2] = "<init>";
                break;
            }
            case 3: 
            case 4: 
            case 5: 
            case 6: 
            case 7: 
            case 8: 
            case 9: 
            case 11: {
                break;
            }
            case 10: {
                objectArray = objectArray;
                objectArray[2] = "fromIds";
                break;
            }
        }
        String string = String.format(v0, objectArray);
        throw switch (n) {
            default -> new IllegalArgumentException(string);
            case 3, 4, 5, 6, 7, 8, 9, 11 -> new IllegalStateException(string);
        };
    }
}
