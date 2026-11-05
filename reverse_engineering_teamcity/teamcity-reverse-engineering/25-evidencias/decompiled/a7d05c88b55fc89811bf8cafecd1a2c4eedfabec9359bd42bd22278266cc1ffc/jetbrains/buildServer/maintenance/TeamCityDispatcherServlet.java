/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.intellij.openapi.diagnostic.Logger
 *  javax.servlet.GenericServlet
 *  javax.servlet.Servlet
 *  javax.servlet.ServletConfig
 *  javax.servlet.ServletContext
 *  javax.servlet.ServletException
 *  javax.servlet.ServletRequest
 *  javax.servlet.ServletResponse
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  jetbrains.buildServer.maintenance.ApplicationCreatorAndDestroyer
 *  jetbrains.buildServer.maintenance.StartupContext
 *  jetbrains.buildServer.maintenance.StartupContextHolder
 *  jetbrains.buildServer.maintenance.StartupProcessor
 *  jetbrains.buildServer.maintenance.TeamCityDispatcherServlet$WebApplicationCreatorAndDestroyer
 *  jetbrains.buildServer.maintenance.WebDispatcherServlet
 *  jetbrains.buildServer.serverSide.CurrentNodeInfo
 *  jetbrains.buildServer.web.util.WebUtil
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 *  org.springframework.web.context.WebApplicationContext
 */
package jetbrains.buildServer.maintenance;

import com.intellij.openapi.diagnostic.Logger;
import java.io.IOException;
import javax.servlet.GenericServlet;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jetbrains.buildServer.maintenance.ApplicationCreatorAndDestroyer;
import jetbrains.buildServer.maintenance.StartupContext;
import jetbrains.buildServer.maintenance.StartupContextHolder;
import jetbrains.buildServer.maintenance.StartupProcessor;
import jetbrains.buildServer.maintenance.TeamCityDispatcherServlet;
import jetbrains.buildServer.maintenance.WebDispatcherServlet;
import jetbrains.buildServer.serverSide.CurrentNodeInfo;
import jetbrains.buildServer.web.util.WebUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.web.context.WebApplicationContext;

/*
 * Exception performing whole class analysis ignored.
 */
public class TeamCityDispatcherServlet
extends GenericServlet {
    public static volatile WebApplicationContext SPRING_CONTEXT;
    public static volatile WebDispatcherServlet webDispatcherServlet;
    private static final String MAIN_SERVER_SPRING_CONFIG = "classpath*:/META-INF/shared-agent-server-spring.xml\n        classpath*:/META-INF/buildServerPluginsSupport.xml\n        classpath*:/META-INF/buildServerPermissions.xml\n        classpath*:/META-INF/buildServerSpring.xml\n        classpath*:/META-INF/buildServerSpring?*.xml\n        classpath*:/META-INF/buildServerCompatMode.xml\n        classpath*:/META-INF/server-issue-tracker-impl.xml\n        classpath*:/META-INF/build-server-declarative-extensibility.xml\n        /WEB-INF/buildServerPluginsSupportWeb.xml\n        /WEB-INF/buildServerSpringWeb.xml\n        /WEB-INF/buildServerSpringWebDiag.xml\n        /WEB-INF/build-server-plugin*.xml\n        classpath*:/META-INF/build-server-plugin*.xml\n        /WEB-INF/buildServerConfigurator.xml\n        /WEB-INF/buildServerSpringStatistics.xml\n        classpath*:/META-INF/*SpringContextLoader.xml";
    @NotNull
    private final Logger myLogger = StartupContextHolder.ourLogger;
    @NotNull
    private final StartupContext myContext = StartupContextHolder.ourContext;
    private ServletConfig myServletConfig;
    @Nullable
    private volatile Servlet myMainServlet;
    private volatile boolean myServletIsReady;
    static final /* synthetic */ boolean $assertionsDisabled;

    public void init(ServletConfig servletConfig) throws ServletException {
        ServletContext servletContext;
        block8: {
            block7: {
                block6: {
                    this.myServletConfig = servletConfig;
                    super.init(servletConfig);
                    servletContext = servletConfig.getServletContext();
                    try {
                        try {
                            if ($assertionsDisabled || servletContext != null) break block6;
                        }
                        catch (ServletException servletException) {
                            throw TeamCityDispatcherServlet.a((Exception)((Object)servletException));
                        }
                        throw new AssertionError((Object)"Servlet context must exist");
                    }
                    catch (ServletException servletException) {
                        throw TeamCityDispatcherServlet.a((Exception)((Object)servletException));
                    }
                }
                this.myContext.setServletContainerInfo(servletContext.getServerInfo());
                this.myContext.setServletContextPath(servletContext.getContextPath());
                String string = servletContext.getRealPath("/");
                try {
                    if (string == null) break block7;
                    this.myContext.setServletRealPath(string);
                    break block8;
                }
                catch (ServletException servletException) {
                    throw TeamCityDispatcherServlet.a((Exception)((Object)servletException));
                }
            }
            this.myLogger.warn("Failed to obtain the servlet real path!");
        }
        this.initCurrentNodeInfo(servletContext);
        new StartupProcessor(this.myContext, servletContext, (ApplicationCreatorAndDestroyer)new WebApplicationCreatorAndDestroyer(this, "classpath*:/META-INF/shared-agent-server-spring.xml\n        classpath*:/META-INF/buildServerPluginsSupport.xml\n        classpath*:/META-INF/buildServerPermissions.xml\n        classpath*:/META-INF/buildServerSpring.xml\n        classpath*:/META-INF/buildServerSpring?*.xml\n        classpath*:/META-INF/buildServerCompatMode.xml\n        classpath*:/META-INF/server-issue-tracker-impl.xml\n        classpath*:/META-INF/build-server-declarative-extensibility.xml\n        /WEB-INF/buildServerPluginsSupportWeb.xml\n        /WEB-INF/buildServerSpringWeb.xml\n        /WEB-INF/buildServerSpringWebDiag.xml\n        /WEB-INF/build-server-plugin*.xml\n        classpath*:/META-INF/build-server-plugin*.xml\n        /WEB-INF/buildServerConfigurator.xml\n        /WEB-INF/buildServerSpringStatistics.xml\n        classpath*:/META-INF/*SpringContextLoader.xml"));
    }

    private void initCurrentNodeInfo(@NotNull ServletContext servletContext) {
        block8: {
            block7: {
                try {
                    if (servletContext == null) {
                        TeamCityDispatcherServlet.$$$reportNull$$$0(0);
                    }
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw TeamCityDispatcherServlet.a(illegalArgumentException);
                }
                try {
                    try {
                        if (servletContext.getInitParameter("teamcity.server.nodeId") == null || servletContext.getInitParameter("teamcity.server.rootURL") == null) break block7;
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        throw TeamCityDispatcherServlet.a(illegalArgumentException);
                    }
                    CurrentNodeInfo.init((String)servletContext.getInitParameter("teamcity.server.nodeId"), (String)servletContext.getInitParameter("teamcity.server.rootURL"));
                    break block8;
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw TeamCityDispatcherServlet.a(illegalArgumentException);
                }
            }
            CurrentNodeInfo.init();
        }
    }

    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        block7: {
            HttpServletRequest httpServletRequest = (HttpServletRequest)servletRequest;
            HttpServletResponse httpServletResponse = (HttpServletResponse)servletResponse;
            try {
                if (this.handleHealthCheckEndpoints(httpServletRequest, httpServletResponse)) {
                    return;
                }
            }
            catch (ServletException servletException) {
                throw TeamCityDispatcherServlet.a((Exception)((Object)servletException));
            }
            try {
                try {
                    if (!this.myServletIsReady || !this.processedByMainServlet(servletRequest, servletResponse)) break block7;
                }
                catch (ServletException servletException) {
                    throw TeamCityDispatcherServlet.a((Exception)((Object)servletException));
                }
                return;
            }
            catch (ServletException servletException) {
                throw TeamCityDispatcherServlet.a((Exception)((Object)servletException));
            }
        }
        this.processMaintenancePagesService(servletRequest, servletResponse);
    }

    private boolean handleHealthCheckEndpoints(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        block7: {
            int n;
            HttpServletResponse httpServletResponse2;
            block9: {
                block8: {
                    String string = httpServletRequest.getServletPath() + httpServletRequest.getPathInfo();
                    try {
                        if (string.equals("/healthCheck/healthy")) {
                            httpServletResponse.setStatus(200);
                            return true;
                        }
                    }
                    catch (IOException iOException) {
                        throw TeamCityDispatcherServlet.a(iOException);
                    }
                    try {
                        try {
                            if (!string.equals("/healthCheck/ready")) break block7;
                            httpServletResponse2 = httpServletResponse;
                            if (!this.myServletIsReady) break block8;
                        }
                        catch (IOException iOException) {
                            throw TeamCityDispatcherServlet.a(iOException);
                        }
                        n = 200;
                        break block9;
                    }
                    catch (IOException iOException) {
                        throw TeamCityDispatcherServlet.a(iOException);
                    }
                }
                n = 503;
            }
            httpServletResponse2.setStatus(n);
            return true;
        }
        return false;
    }

    private boolean processedByMainServlet(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        Servlet servlet = this.myMainServlet;
        try {
            if (servlet != null) {
                servlet.service(servletRequest, servletResponse);
                return true;
            }
        }
        catch (ServletException servletException) {
            throw TeamCityDispatcherServlet.a((Exception)((Object)servletException));
        }
        return false;
    }

    private void processMaintenancePagesService(ServletRequest servletRequest, ServletResponse servletResponse) {
        int n;
        HttpServletResponse httpServletResponse = (HttpServletResponse)servletResponse;
        try {
            WebUtil.preventCaching((HttpServletResponse)httpServletResponse);
            n = this.myContext.isShutdownRequested() ? 410 : 503;
        }
        catch (IOException iOException) {
            throw TeamCityDispatcherServlet.a(iOException);
        }
        int n2 = n;
        try {
            httpServletResponse.sendError(n2);
        }
        catch (IOException iOException) {
            this.myLogger.error("Unexpected IOException when attempted to send HTTP error " + n2 + ": " + iOException.getMessage(), (Throwable)iOException);
        }
    }

    public void destroy() {
        this.myContext.requestShutdown();
        this.myContext.waitWhileProcessingInfinitely();
        super.destroy();
    }

    public boolean isServletReady() {
        return this.myServletIsReady;
    }

    private static void disableJasperAnnotations(@NotNull ServletConfig servletConfig) {
        try {
            if (servletConfig == null) {
                TeamCityDispatcherServlet.$$$reportNull$$$0(1);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw TeamCityDispatcherServlet.a(illegalArgumentException);
        }
        servletConfig.getServletContext().setAttribute("org.apache.AnnotationProcessor", null);
        TeamCityDispatcherServlet.setPropertyIfNotDefined("org.apache.jasper.runtime.JspFactoryImpl.USE_POOL", "false");
        TeamCityDispatcherServlet.setPropertyIfNotDefined("org.apache.jasper.runtime.BodyContentImpl.LIMIT_BUFFER", "true");
    }

    private static void setPropertyIfNotDefined(@NotNull String string, @NotNull String string2) {
        try {
            if (string == null) {
                TeamCityDispatcherServlet.$$$reportNull$$$0(2);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw TeamCityDispatcherServlet.a(illegalArgumentException);
        }
        try {
            if (string2 == null) {
                TeamCityDispatcherServlet.$$$reportNull$$$0(3);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw TeamCityDispatcherServlet.a(illegalArgumentException);
        }
        String string3 = System.getProperty(string);
        try {
            if (string3 == null) {
                System.setProperty(string, string2);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw TeamCityDispatcherServlet.a(illegalArgumentException);
        }
    }

    static {
        boolean bl;
        try {
            bl = !TeamCityDispatcherServlet.class.desiredAssertionStatus();
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw TeamCityDispatcherServlet.a(illegalArgumentException);
        }
        $assertionsDisabled = bl;
        SPRING_CONTEXT = null;
        webDispatcherServlet = null;
    }

    /*
     * Exception decompiling
     */
    private static /* synthetic */ void $$$reportNull$$$0(int var0) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 2[SWITCH]
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

    private static Exception a(Exception exception) {
        return exception;
    }
}
