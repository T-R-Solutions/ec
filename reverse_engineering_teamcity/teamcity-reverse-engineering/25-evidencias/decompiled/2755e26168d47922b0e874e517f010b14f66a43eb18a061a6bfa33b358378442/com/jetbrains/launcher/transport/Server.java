/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jetbrains.launcher.log.ErrorLogger
 *  com.jetbrains.launcher.transport.AbstractServer
 *  com.jetbrains.launcher.transport.Request
 *  com.jetbrains.launcher.transport.RequestHandler
 *  com.jetbrains.launcher.transport.Response
 *  org.jetbrains.annotations.NotNull
 */
package com.jetbrains.launcher.transport;

import com.jetbrains.launcher.log.ErrorLogger;
import com.jetbrains.launcher.transport.AbstractServer;
import com.jetbrains.launcher.transport.Request;
import com.jetbrains.launcher.transport.RequestHandler;
import com.jetbrains.launcher.transport.Response;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

public class Server
extends AbstractServer {
    @NotNull
    private final Map<String, RequestHandler> myHandlers;
    @NotNull
    private final ErrorLogger myErrorLogger;

    public Server(@NotNull String threadName, @NotNull ErrorLogger errorLogger) throws IOException {
        if (threadName == null) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "threadName", "com/jetbrains/launcher/transport/Server", "<init>"));
        }
        if (errorLogger == null) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "errorLogger", "com/jetbrains/launcher/transport/Server", "<init>"));
        }
        super(threadName);
        this.myHandlers = new HashMap<String, RequestHandler>();
        this.myErrorLogger = errorLogger;
    }

    public void addHandler(@NotNull String url, @NotNull RequestHandler handler) {
        if (url == null) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "url", "com/jetbrains/launcher/transport/Server", "addHandler"));
        }
        if (handler == null) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "handler", "com/jetbrains/launcher/transport/Server", "addHandler"));
        }
        this.myHandlers.put(url, handler);
    }

    @NotNull
    protected Response doProcessRequest(@NotNull Request request) {
        if (request == null) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "request", "com/jetbrains/launcher/transport/Server", "doProcessRequest"));
        }
        String url = request.getURL();
        RequestHandler handler = this.myHandlers.get(url);
        if (handler != null) {
            Response response;
            try {
                response = handler.processRequest(request);
            }
            catch (Throwable e) {
                this.myErrorLogger.internalError("Failed to process request: " + Server.describeRequest((Request)request), e);
                Response response2 = new Response(500, e.getMessage());
                if (response2 == null) {
                    throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "com/jetbrains/launcher/transport/Server", "doProcessRequest"));
                }
                return response2;
            }
            if (response == null) {
                throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "com/jetbrains/launcher/transport/Server", "doProcessRequest"));
            }
            return response;
        }
        Response response = new Response(404, "URL \"" + url + "\" is not found");
        if (response == null) {
            throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "com/jetbrains/launcher/transport/Server", "doProcessRequest"));
        }
        return response;
    }
}
