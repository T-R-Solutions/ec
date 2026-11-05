/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableSortedSet
 *  com.intellij.execution.ExecutionException
 *  com.intellij.execution.configurations.GeneralCommandLine
 *  com.intellij.openapi.diagnostic.Logger
 *  com.intellij.openapi.util.TCSystemInfo
 *  com.intellij.openapi.util.text.CharFilter
 *  com.intellij.openapi.util.text.StringUtil
 *  javax.servlet.ServletContext
 *  jetbrains.buildServer.CommandLineExecutor
 *  jetbrains.buildServer.DevelopmentMode
 *  jetbrains.buildServer.LineAwareByteArrayOutputStream
 *  jetbrains.buildServer.LineAwareByteArrayOutputStream$LineListener
 *  jetbrains.buildServer.NetworkUtil
 *  jetbrains.buildServer.diagnostic.PeriodicThreadDumpSaver
 *  jetbrains.buildServer.diagnostic.ServerThreadDumpUtils
 *  jetbrains.buildServer.diagnostic.TeamCityDiagnosticService
 *  jetbrains.buildServer.diagnostic.TeamCityDiagnosticService$Task
 *  jetbrains.buildServer.http.SecondaryHttpConfigurator
 *  jetbrains.buildServer.https.HttpsConfigurationException
 *  jetbrains.buildServer.https.HttpsConfigurator
 *  jetbrains.buildServer.https.HttpsConfiguratorImpl
 *  jetbrains.buildServer.https.HttpsConnectorManager
 *  jetbrains.buildServer.https.HttpsConnectorManagerImpl
 *  jetbrains.buildServer.log.LogInitializationException
 *  jetbrains.buildServer.log.LogInitializer
 *  jetbrains.buildServer.log.LogUtil
 *  jetbrains.buildServer.log.Loggers
 *  jetbrains.buildServer.maintenance.ApplicationCreatorAndDestroyer
 *  jetbrains.buildServer.maintenance.EnvAnalyzer
 *  jetbrains.buildServer.maintenance.ServerLoggingConfiguration
 *  jetbrains.buildServer.maintenance.SingleRowTableFixer
 *  jetbrains.buildServer.maintenance.StartupConsts
 *  jetbrains.buildServer.maintenance.StartupContext
 *  jetbrains.buildServer.maintenance.StartupHelper
 *  jetbrains.buildServer.maintenance.StartupProcessor$UpgradePerformer
 *  jetbrains.buildServer.maintenance.StartupStage
 *  jetbrains.buildServer.maintenance.StartupUtil
 *  jetbrains.buildServer.maintenance.TeamCityDataDirectoryManager
 *  jetbrains.buildServer.maintenance.TomcatManagementUtil
 *  jetbrains.buildServer.maintenance.VersionFile
 *  jetbrains.buildServer.maintenance.XmxWarningUtil
 *  jetbrains.buildServer.maintenance.exceptions.DBExceptionAdapter
 *  jetbrains.buildServer.maintenance.exceptions.DataDirectoryException
 *  jetbrains.buildServer.maintenance.exceptions.DataPartsInconsistentError
 *  jetbrains.buildServer.maintenance.exceptions.DatabaseIsNotEmptyError
 *  jetbrains.buildServer.maintenance.exceptions.DatabaseVersionMismatchError
 *  jetbrains.buildServer.maintenance.exceptions.KnownNonRecoverableStartupError
 *  jetbrains.buildServer.maintenance.exceptions.KnownRecoverableDatabaseSettingsError
 *  jetbrains.buildServer.maintenance.exceptions.KnownRecoverableError
 *  jetbrains.buildServer.maintenance.exceptions.KnownStartupError
 *  jetbrains.buildServer.maintenance.exceptions.SecondTeamCityInstanceError
 *  jetbrains.buildServer.maintenance.exceptions.ServerStartupError
 *  jetbrains.buildServer.maintenance.exceptions.StartupDatabaseAccessingError
 *  jetbrains.buildServer.maintenance.exceptions.UnknownServerStartupError
 *  jetbrains.buildServer.maintenance.exceptions.UnknownUpgradeError
 *  jetbrains.buildServer.processes.KillProcessDetails
 *  jetbrains.buildServer.processes.ProcessFilter
 *  jetbrains.buildServer.processes.ProcessTreeTerminator
 *  jetbrains.buildServer.serverSide.CurrentNodeInfo
 *  jetbrains.buildServer.serverSide.DataDirectoryLocationResolver
 *  jetbrains.buildServer.serverSide.IOGuardInitializer
 *  jetbrains.buildServer.serverSide.InstallationInfo
 *  jetbrains.buildServer.serverSide.InstallationsManager
 *  jetbrains.buildServer.serverSide.InternalPropertiesProvider
 *  jetbrains.buildServer.serverSide.LicenseAgreement
 *  jetbrains.buildServer.serverSide.LicenseAgreementImpl
 *  jetbrains.buildServer.serverSide.NodeResponsibility
 *  jetbrains.buildServer.serverSide.NodeSpecificConfigs
 *  jetbrains.buildServer.serverSide.ServerPaths
 *  jetbrains.buildServer.serverSide.StartupConfiguration
 *  jetbrains.buildServer.serverSide.TeamCityInstallationInfo
 *  jetbrains.buildServer.serverSide.TeamCityProperties
 *  jetbrains.buildServer.serverSide.TeamCityPropertiesProvider
 *  jetbrains.buildServer.serverSide.TeamCityServerPropertiesModel
 *  jetbrains.buildServer.serverSide.crypt.CustomKeyEncryptionStrategy
 *  jetbrains.buildServer.serverSide.crypt.EncryptionKeysStorage
 *  jetbrains.buildServer.serverSide.crypt.EncryptionKeysStorageProvider
 *  jetbrains.buildServer.serverSide.crypt.EncryptionStrategy
 *  jetbrains.buildServer.serverSide.crypt.EncryptionUtilsHelper
 *  jetbrains.buildServer.serverSide.crypt.EnvVarBasedEncryptionKeysStorage
 *  jetbrains.buildServer.serverSide.crypt.FileBasedEncryptionConfigStorage
 *  jetbrains.buildServer.serverSide.db.ConnectionUtil
 *  jetbrains.buildServer.serverSide.db.CouldNotConnectToDatabaseException
 *  jetbrains.buildServer.serverSide.db.DBAction
 *  jetbrains.buildServer.serverSide.db.DBException
 *  jetbrains.buildServer.serverSide.db.DBFacade
 *  jetbrains.buildServer.serverSide.db.DBFunctions
 *  jetbrains.buildServer.serverSide.db.DBFunctionsProvider
 *  jetbrains.buildServer.serverSide.db.DBSchema
 *  jetbrains.buildServer.serverSide.db.DatabaseAccessDeniedException
 *  jetbrains.buildServer.serverSide.db.DatabaseDriverMissingException
 *  jetbrains.buildServer.serverSide.db.DatabaseIsNotReachableException
 *  jetbrains.buildServer.serverSide.db.DatabaseIsOutOfSpaceException
 *  jetbrains.buildServer.serverSide.db.DatabasePropertiesFile
 *  jetbrains.buildServer.serverSide.db.DatabaseSettings
 *  jetbrains.buildServer.serverSide.db.DatabaseType
 *  jetbrains.buildServer.serverSide.db.DatabaseTypeUtil
 *  jetbrains.buildServer.serverSide.db.DefaultUrlConnectionFactory
 *  jetbrains.buildServer.serverSide.db.GuidGenerator
 *  jetbrains.buildServer.serverSide.db.HSQL.HsqlPossibilityVerifier
 *  jetbrains.buildServer.serverSide.db.HSQLDBSpecific
 *  jetbrains.buildServer.serverSide.db.Heartbeat
 *  jetbrains.buildServer.serverSide.db.Heartbeat$AppType
 *  jetbrains.buildServer.serverSide.db.Heartbeat$ExclusiveLockTakenError
 *  jetbrains.buildServer.serverSide.db.Heartbeat$MainServerWithDifferentVersionError
 *  jetbrains.buildServer.serverSide.db.Heartbeat$OtherNodeUsesDifferentDataDir
 *  jetbrains.buildServer.serverSide.db.Heartbeat$OtherNodeWithDifferentVersionError
 *  jetbrains.buildServer.serverSide.db.Heartbeat$OtherNodeWithSameIdIsActive
 *  jetbrains.buildServer.serverSide.db.IncorrectConnectionSettingsException
 *  jetbrains.buildServer.serverSide.db.JdbcConnectionString
 *  jetbrains.buildServer.serverSide.db.JdbcDriverDownloadProgressListener
 *  jetbrains.buildServer.serverSide.db.JdbcDriverDownloader
 *  jetbrains.buildServer.serverSide.db.JdbcDriverDownloader$ProgressListener
 *  jetbrains.buildServer.serverSide.db.JdbcDriverDownloader$UrlConnectionFactory
 *  jetbrains.buildServer.serverSide.db.JdbcDriverIsTooOldException
 *  jetbrains.buildServer.serverSide.db.SecondTeamCityInstanceException
 *  jetbrains.buildServer.serverSide.db.SessionInfo
 *  jetbrains.buildServer.serverSide.db.TeamCityDatabaseManager
 *  jetbrains.buildServer.serverSide.db.jdbcLoader.JdbcDrivers
 *  jetbrains.buildServer.serverSide.impl.CommandLineNodeConfig
 *  jetbrains.buildServer.serverSide.impl.NodeConfig
 *  jetbrains.buildServer.serverSide.impl.NodesConfigUtil
 *  jetbrains.buildServer.serverSide.impl.SecondaryNodeDBAccess
 *  jetbrains.buildServer.serverSide.impl.SecondaryNodeSecurityManager
 *  jetbrains.buildServer.serverSide.maintenance.BackupArchiveHasNoVersionFileError
 *  jetbrains.buildServer.serverSide.maintenance.BackupConfig
 *  jetbrains.buildServer.serverSide.maintenance.BackupProcess
 *  jetbrains.buildServer.serverSide.maintenance.BackupProcessInfo
 *  jetbrains.buildServer.serverSide.maintenance.BackupProcessManager
 *  jetbrains.buildServer.serverSide.maintenance.BackupProcessManagerImpl
 *  jetbrains.buildServer.serverSide.maintenance.BackupSettingsPersistor
 *  jetbrains.buildServer.serverSide.maintenance.BackupUtil
 *  jetbrains.buildServer.serverSide.maintenance.BackupVersionFileSerializer
 *  jetbrains.buildServer.serverSide.maintenance.MaintenanceProcessAlreadyRunningException
 *  jetbrains.buildServer.serverSide.maintenance.ProgressStatus
 *  jetbrains.buildServer.serverSide.maintenance.RestoreProcessor
 *  jetbrains.buildServer.serverSide.maintenance.UnsupportedVersionError
 *  jetbrains.buildServer.serverSide.versioning.DataDirectoryVersionException
 *  jetbrains.buildServer.serverSide.versioning.KnownConvertException
 *  jetbrains.buildServer.serverSide.versioning.VersionManager
 *  jetbrains.buildServer.util.CollectionsUtil
 *  jetbrains.buildServer.util.Converter
 *  jetbrains.buildServer.util.Dates
 *  jetbrains.buildServer.util.FilePersisterUtil
 *  jetbrains.buildServer.util.FileUtil
 *  jetbrains.buildServer.util.NamedThreadFactory
 *  jetbrains.buildServer.util.PropertiesUtil
 *  jetbrains.buildServer.util.Property$Listener
 *  jetbrains.buildServer.util.StringUtil
 *  jetbrains.buildServer.util.ThreadUtil
 *  jetbrains.buildServer.util.TimePrinter
 *  jetbrains.buildServer.util.Version
 *  jetbrains.buildServer.util.XStreamWhiteListInitializer
 *  jetbrains.buildServer.util.filters.Filter
 *  jetbrains.buildServer.util.http.HttpUrlConnectionFactory
 *  jetbrains.buildServer.version.ServerVersionHolder
 *  jetbrains.buildServer.version.ServerVersionInfo
 *  jetbrains.buildServer.web.util.WebUtil
 *  jetbrains.buildServer.xstream.JdbcDriver
 *  jetbrains.buildServer.zip.BigFileZipFactory
 *  jetbrains.buildServer.zip.ZipFactory
 *  jetbrains.buildServer.zip.ZipReader
 *  org.apache.log4j.LogManager
 *  org.jdom.Document
 *  org.jdom.JDOMException
 *  org.jetbrains.annotations.NonNls
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package jetbrains.buildServer.maintenance;

import com.google.common.collect.ImmutableSortedSet;
import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.util.TCSystemInfo;
import com.intellij.openapi.util.text.CharFilter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.management.ObjectName;
import javax.servlet.ServletContext;
import jetbrains.buildServer.CommandLineExecutor;
import jetbrains.buildServer.DevelopmentMode;
import jetbrains.buildServer.LineAwareByteArrayOutputStream;
import jetbrains.buildServer.NetworkUtil;
import jetbrains.buildServer.diagnostic.PeriodicThreadDumpSaver;
import jetbrains.buildServer.diagnostic.ServerThreadDumpUtils;
import jetbrains.buildServer.diagnostic.TeamCityDiagnosticService;
import jetbrains.buildServer.http.SecondaryHttpConfigurator;
import jetbrains.buildServer.https.HttpsConfigurationException;
import jetbrains.buildServer.https.HttpsConfigurator;
import jetbrains.buildServer.https.HttpsConfiguratorImpl;
import jetbrains.buildServer.https.HttpsConnectorManager;
import jetbrains.buildServer.https.HttpsConnectorManagerImpl;
import jetbrains.buildServer.log.LogInitializationException;
import jetbrains.buildServer.log.LogInitializer;
import jetbrains.buildServer.log.LogUtil;
import jetbrains.buildServer.log.Loggers;
import jetbrains.buildServer.maintenance.ApplicationCreatorAndDestroyer;
import jetbrains.buildServer.maintenance.EnvAnalyzer;
import jetbrains.buildServer.maintenance.ServerLoggingConfiguration;
import jetbrains.buildServer.maintenance.SingleRowTableFixer;
import jetbrains.buildServer.maintenance.StartupConsts;
import jetbrains.buildServer.maintenance.StartupContext;
import jetbrains.buildServer.maintenance.StartupHelper;
import jetbrains.buildServer.maintenance.StartupProcessor;
import jetbrains.buildServer.maintenance.StartupStage;
import jetbrains.buildServer.maintenance.StartupUtil;
import jetbrains.buildServer.maintenance.TeamCityDataDirectoryManager;
import jetbrains.buildServer.maintenance.TomcatManagementUtil;
import jetbrains.buildServer.maintenance.VersionFile;
import jetbrains.buildServer.maintenance.XmxWarningUtil;
import jetbrains.buildServer.maintenance.exceptions.DBExceptionAdapter;
import jetbrains.buildServer.maintenance.exceptions.DataDirectoryException;
import jetbrains.buildServer.maintenance.exceptions.DataPartsInconsistentError;
import jetbrains.buildServer.maintenance.exceptions.DatabaseIsNotEmptyError;
import jetbrains.buildServer.maintenance.exceptions.DatabaseVersionMismatchError;
import jetbrains.buildServer.maintenance.exceptions.KnownNonRecoverableStartupError;
import jetbrains.buildServer.maintenance.exceptions.KnownRecoverableDatabaseSettingsError;
import jetbrains.buildServer.maintenance.exceptions.KnownRecoverableError;
import jetbrains.buildServer.maintenance.exceptions.KnownStartupError;
import jetbrains.buildServer.maintenance.exceptions.SecondTeamCityInstanceError;
import jetbrains.buildServer.maintenance.exceptions.ServerStartupError;
import jetbrains.buildServer.maintenance.exceptions.StartupDatabaseAccessingError;
import jetbrains.buildServer.maintenance.exceptions.UnknownServerStartupError;
import jetbrains.buildServer.maintenance.exceptions.UnknownUpgradeError;
import jetbrains.buildServer.processes.KillProcessDetails;
import jetbrains.buildServer.processes.ProcessFilter;
import jetbrains.buildServer.processes.ProcessTreeTerminator;
import jetbrains.buildServer.serverSide.CurrentNodeInfo;
import jetbrains.buildServer.serverSide.DataDirectoryLocationResolver;
import jetbrains.buildServer.serverSide.IOGuardInitializer;
import jetbrains.buildServer.serverSide.InstallationInfo;
import jetbrains.buildServer.serverSide.InstallationsManager;
import jetbrains.buildServer.serverSide.InternalPropertiesProvider;
import jetbrains.buildServer.serverSide.LicenseAgreement;
import jetbrains.buildServer.serverSide.LicenseAgreementImpl;
import jetbrains.buildServer.serverSide.NodeResponsibility;
import jetbrains.buildServer.serverSide.NodeSpecificConfigs;
import jetbrains.buildServer.serverSide.ServerPaths;
import jetbrains.buildServer.serverSide.StartupConfiguration;
import jetbrains.buildServer.serverSide.TeamCityInstallationInfo;
import jetbrains.buildServer.serverSide.TeamCityProperties;
import jetbrains.buildServer.serverSide.TeamCityPropertiesProvider;
import jetbrains.buildServer.serverSide.TeamCityServerPropertiesModel;
import jetbrains.buildServer.serverSide.crypt.CustomKeyEncryptionStrategy;
import jetbrains.buildServer.serverSide.crypt.EncryptionKeysStorage;
import jetbrains.buildServer.serverSide.crypt.EncryptionKeysStorageProvider;
import jetbrains.buildServer.serverSide.crypt.EncryptionStrategy;
import jetbrains.buildServer.serverSide.crypt.EncryptionUtilsHelper;
import jetbrains.buildServer.serverSide.crypt.EnvVarBasedEncryptionKeysStorage;
import jetbrains.buildServer.serverSide.crypt.FileBasedEncryptionConfigStorage;
import jetbrains.buildServer.serverSide.db.ConnectionUtil;
import jetbrains.buildServer.serverSide.db.CouldNotConnectToDatabaseException;
import jetbrains.buildServer.serverSide.db.DBAction;
import jetbrains.buildServer.serverSide.db.DBException;
import jetbrains.buildServer.serverSide.db.DBFacade;
import jetbrains.buildServer.serverSide.db.DBFunctions;
import jetbrains.buildServer.serverSide.db.DBFunctionsProvider;
import jetbrains.buildServer.serverSide.db.DBSchema;
import jetbrains.buildServer.serverSide.db.DatabaseAccessDeniedException;
import jetbrains.buildServer.serverSide.db.DatabaseDriverMissingException;
import jetbrains.buildServer.serverSide.db.DatabaseIsNotReachableException;
import jetbrains.buildServer.serverSide.db.DatabaseIsOutOfSpaceException;
import jetbrains.buildServer.serverSide.db.DatabasePropertiesFile;
import jetbrains.buildServer.serverSide.db.DatabaseSettings;
import jetbrains.buildServer.serverSide.db.DatabaseType;
import jetbrains.buildServer.serverSide.db.DatabaseTypeUtil;
import jetbrains.buildServer.serverSide.db.DefaultUrlConnectionFactory;
import jetbrains.buildServer.serverSide.db.GuidGenerator;
import jetbrains.buildServer.serverSide.db.HSQL.HsqlPossibilityVerifier;
import jetbrains.buildServer.serverSide.db.HSQLDBSpecific;
import jetbrains.buildServer.serverSide.db.Heartbeat;
import jetbrains.buildServer.serverSide.db.IncorrectConnectionSettingsException;
import jetbrains.buildServer.serverSide.db.JdbcConnectionString;
import jetbrains.buildServer.serverSide.db.JdbcDriverDownloadProgressListener;
import jetbrains.buildServer.serverSide.db.JdbcDriverDownloader;
import jetbrains.buildServer.serverSide.db.JdbcDriverIsTooOldException;
import jetbrains.buildServer.serverSide.db.SecondTeamCityInstanceException;
import jetbrains.buildServer.serverSide.db.SessionInfo;
import jetbrains.buildServer.serverSide.db.TeamCityDatabaseManager;
import jetbrains.buildServer.serverSide.db.jdbcLoader.JdbcDrivers;
import jetbrains.buildServer.serverSide.impl.CommandLineNodeConfig;
import jetbrains.buildServer.serverSide.impl.NodeConfig;
import jetbrains.buildServer.serverSide.impl.NodesConfigUtil;
import jetbrains.buildServer.serverSide.impl.SecondaryNodeDBAccess;
import jetbrains.buildServer.serverSide.impl.SecondaryNodeSecurityManager;
import jetbrains.buildServer.serverSide.maintenance.BackupArchiveHasNoVersionFileError;
import jetbrains.buildServer.serverSide.maintenance.BackupConfig;
import jetbrains.buildServer.serverSide.maintenance.BackupProcess;
import jetbrains.buildServer.serverSide.maintenance.BackupProcessInfo;
import jetbrains.buildServer.serverSide.maintenance.BackupProcessManager;
import jetbrains.buildServer.serverSide.maintenance.BackupProcessManagerImpl;
import jetbrains.buildServer.serverSide.maintenance.BackupSettingsPersistor;
import jetbrains.buildServer.serverSide.maintenance.BackupUtil;
import jetbrains.buildServer.serverSide.maintenance.BackupVersionFileSerializer;
import jetbrains.buildServer.serverSide.maintenance.MaintenanceProcessAlreadyRunningException;
import jetbrains.buildServer.serverSide.maintenance.ProgressStatus;
import jetbrains.buildServer.serverSide.maintenance.RestoreProcessor;
import jetbrains.buildServer.serverSide.maintenance.UnsupportedVersionError;
import jetbrains.buildServer.serverSide.versioning.DataDirectoryVersionException;
import jetbrains.buildServer.serverSide.versioning.KnownConvertException;
import jetbrains.buildServer.serverSide.versioning.VersionManager;
import jetbrains.buildServer.util.CollectionsUtil;
import jetbrains.buildServer.util.Converter;
import jetbrains.buildServer.util.Dates;
import jetbrains.buildServer.util.FilePersisterUtil;
import jetbrains.buildServer.util.FileUtil;
import jetbrains.buildServer.util.NamedThreadFactory;
import jetbrains.buildServer.util.PropertiesUtil;
import jetbrains.buildServer.util.Property;
import jetbrains.buildServer.util.StringUtil;
import jetbrains.buildServer.util.ThreadUtil;
import jetbrains.buildServer.util.TimePrinter;
import jetbrains.buildServer.util.Version;
import jetbrains.buildServer.util.XStreamWhiteListInitializer;
import jetbrains.buildServer.util.filters.Filter;
import jetbrains.buildServer.util.http.HttpUrlConnectionFactory;
import jetbrains.buildServer.version.ServerVersionHolder;
import jetbrains.buildServer.version.ServerVersionInfo;
import jetbrains.buildServer.web.util.WebUtil;
import jetbrains.buildServer.xstream.JdbcDriver;
import jetbrains.buildServer.zip.BigFileZipFactory;
import jetbrains.buildServer.zip.ZipFactory;
import jetbrains.buildServer.zip.ZipReader;
import org.apache.log4j.LogManager;
import org.jdom.Document;
import org.jdom.JDOMException;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Exception performing whole class analysis ignored.
 */
class StartupProcessor {
    private static final String VERSION_FILE_NAME;
    private static final String TEAMCITY_PID_FILE_NAME = "teamcity.pid";
    private static final String TEAMCITY_PID_FILE_PATH_ENV = "TEAMCITY_PID_FILE_PATH";
    private static final String UNKNOWN_DISTRIBUTION_TYPE = "unknown";
    private static final String DISTRIBUTION_TYPE_FILE_PATH = "/WEB-INF/DistributionType.txt";
    private static final String RESTORE_STATUS_MARKER_FILE_NAME = "restoreStatusMarker.txt";
    @NonNls
    @NotNull
    private static final String DATABASE_NOT_STARTED_YET = "Could not reach the database, it may be the database server is not started yet or connection properties are not valid.";
    static final long RECOMMENDED_MEM_SETTING_VALUE = 1300000000L;
    private static final String RECOMMENDED_XMX = "-Xmx1500m";
    public static final String TEAMCITY_MAINTENANCE_UPGRADE_ALWAYS_TAKES_EXCLUSIVE_LOCK = "teamcity.maintenance.upgrade.alwaysTakesExclusiveLock";
    @NotNull
    protected final Logger myLogger;
    @Nullable
    private static StartupProcessor ourInstance;
    @NotNull
    private final StartupContext myContext;
    @NotNull
    private final ServletContext myServletContext;
    @NotNull
    private final Thread myStartupThread;
    @Nullable
    private final ApplicationCreatorAndDestroyer myApplicationCreatorAndDestroyer;
    @Nullable
    private DBFacade myDBFacade;
    @Nullable
    private BackupProcessManager myBackupProcessManager;
    @Nullable
    private VersionFile myVersionFile;
    @Nullable
    private Instant myDbConnectStart;
    private volatile UpgradePerformer myUpgradePerformer;
    static final /* synthetic */ boolean $assertionsDisabled;

    StartupProcessor(@NotNull StartupContext startupContext, @NotNull ServletContext servletContext, @Nullable ApplicationCreatorAndDestroyer applicationCreatorAndDestroyer) {
        Object object;
        if (startupContext == null) {
            StartupProcessor.$$$reportNull$$$0(0);
        }
        if (servletContext == null) {
            StartupProcessor.$$$reportNull$$$0(1);
        }
        this.myUpgradePerformer = new UpgradePerformer();
        this.myServletContext = servletContext;
        if (ourInstance != null) {
            throw new IllegalStateException("The starter already created.");
        }
        this.initServerPropertiesAndLogging();
        this.myLogger = startupContext.getLogger();
        ourInstance = this;
        this.myContext = startupContext;
        this.myApplicationCreatorAndDestroyer = applicationCreatorAndDestroyer;
        if (TeamCityProperties.getBooleanOrTrue((String)"teamcity.setDefaultUncaughtExceptionHandler")) {
            object = Thread.getDefaultUncaughtExceptionHandler();
            Thread.setDefaultUncaughtExceptionHandler((arg_0, arg_1) -> StartupProcessor.lambda$new$0((Thread.UncaughtExceptionHandler)object, arg_0, arg_1));
        }
        object = StartupProcessor.constructThreadName(this.myContext);
        this.myStartupThread = new Thread((Runnable)new /* Unavailable Anonymous Inner Class!! */, (String)object);
        this.myStartupThread.start();
        this.myContext.myMaintenanceAuthenticationToken.attachListener((Property.Listener)new /* Unavailable Anonymous Inner Class!! */);
    }

    private void initServerPropertiesAndLogging() {
        File file = DataDirectoryLocationResolver.determineDataDir();
        File file2 = NodeSpecificConfigs.getNodeDataDirectory((File)file);
        try {
            if (!file.equals(file2)) {
                TeamCityServerPropertiesModel.initNodeSpecificProperties((File)file2);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupProcessor.a(illegalStateException);
        }
        ServerLoggingConfiguration.init();
    }

    private static String constructThreadName(StartupContext startupContext) {
        ServerVersionInfo serverVersionInfo = ServerVersionHolder.getVersion();
        String string = serverVersionInfo.getDisplayVersion() + " build " + serverVersionInfo.getBuildNumber();
        return "TeamCity Server (version " + string + " started " + LogUtil.describe((Date)startupContext.getServerStartupTimestamp()) + "), node id: " + CurrentNodeInfo.getNodeId();
    }

    private void processTeamCityLifecycle() {
        while (true) {
            StartupStage startupStage = this.myContext.getCurrStage();
            try {
                if (startupStage.adminRequires) {
                    this.askAdminToLoginUsingMaintenanceAuthenticationToken();
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw StartupProcessor.a(illegalStateException);
            }
            try {
                if (startupStage.active) {
                    this.processConcreteStageSafe(startupStage);
                    continue;
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw StartupProcessor.a(illegalStateException);
            }
            try {
                if (this.myContext.getNextStage() != null) {
                    this.myContext.switchToTheNextStage();
                    continue;
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw StartupProcessor.a(illegalStateException);
            }
            try {
                if (startupStage == StartupStage.END) {
                    break;
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw StartupProcessor.a(illegalStateException);
            }
            this.myContext.waitForFollowingStage();
        }
    }

    private void processConcreteStageSafe(@NotNull StartupStage startupStage) {
        try {
            if (startupStage == null) {
                StartupProcessor.$$$reportNull$$$0(2);
            }
        }
        catch (DBException dBException) {
            throw StartupProcessor.a(dBException);
        }
        try {
            try {
                this.processConcreteStage(startupStage);
            }
            catch (DBException dBException) {
                throw new DBExceptionAdapter(dBException);
            }
        }
        catch (KnownRecoverableError knownRecoverableError) {
            try {
                this.logErrorOnStage(startupStage, knownRecoverableError);
                if (!knownRecoverableError.recover(this.myContext)) {
                    this.goToExceptionStage(knownRecoverableError);
                }
            }
            catch (DBException dBException) {
                throw StartupProcessor.a(dBException);
            }
        }
        catch (Throwable throwable) {
            this.logErrorOnStage(startupStage, throwable);
            this.goToExceptionStage(throwable);
        }
        this.myContext.switchToTheNextStage();
    }

    private void goToExceptionStage(Throwable throwable) {
        this.myContext.addException(throwable);
        this.myContext.proposeNextStage(StartupStage.EXCEPTION, new StartupStage[0]);
        this.myContext.setStarting(false);
    }

    private void logErrorOnStage(StartupStage startupStage, Throwable throwable) {
        block3: {
            block2: {
                try {
                    if (!(throwable instanceof KnownStartupError)) break block2;
                    this.myLogger.error(throwable.getMessage());
                    this.myLogger.debug(throwable);
                    break block3;
                }
                catch (IllegalStateException illegalStateException) {
                    throw StartupProcessor.a(illegalStateException);
                }
            }
            this.myLogger.error("Exception " + throwable.getClass().getSimpleName() + " in stage " + startupStage.name() + ": " + throwable.getMessage(), throwable);
        }
    }

    private void processConcreteStage(@NotNull StartupStage startupStage) throws Exception {
        try {
            if (startupStage == null) {
                StartupProcessor.$$$reportNull$$$0(3);
            }
        }
        catch (Exception exception) {
            throw StartupProcessor.a(exception);
        }
        NamedThreadFactory.executeWithNewThreadName((String)("Stage: " + startupStage.description), (Callable)new /* Unavailable Anonymous Inner Class!! */);
    }

    protected void doInitialStage() throws ServerStartupError {
        String string;
        File file;
        block23: {
            String string2;
            Logger logger;
            Object object;
            this.myContext.setSoftwareVersion(DBSchema.getCurrentVersion());
            this.myContext.setDevelopmentMode(TeamCityProperties.getBoolean((String)"teamcity.development.mode"));
            this.myContext.setBypassingMaintenanceQuestions(StartupUtil.isBypassingMaintenanceQuestions());
            try {
                file = LogInitializer.getLogDir();
            }
            catch (LogInitializationException logInitializationException) {
                this.myContext.addException((Throwable)logInitializationException);
                this.proposeNextStage(StartupStage.FATAL_ERROR, new StartupStage[0]);
                return;
            }
            XStreamWhiteListInitializer.initializeWhiteList();
            this.registerWritablePathsForSecondaryNode(file);
            EnvAnalyzer.logSystemInvariants();
            this.myLogger.info("Web server version: " + this.myContext.getServletContainerInfo());
            TomcatManagementUtil.setConnectorMultipartLimits((Logger)this.myLogger);
            String string3 = "-unknown-";
            try {
                object = this.detectServerIPs();
                this.myContext.setIPAddress(object[0].getHostAddress());
                string3 = Arrays.stream(object).map(inetAddress -> inetAddress.getHostAddress()).collect(Collectors.joining(","));
            }
            catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
                this.myLogger.warn("Could not determine local IP address: it seems the network is disabled.");
                this.myContext.setIPAddress("-unknown-");
            }
            catch (Throwable throwable) {
                this.myLogger.warn("Could not determine local IP address: " + String.valueOf(throwable), throwable);
                this.myContext.setIPAddress("-unknown-");
            }
            this.myLogger.info("Server IP addresses: " + string3);
            object = this.fetchWebServerConfigurationDetails();
            try {
                if (object != null) {
                    this.myLogger.info("Web server ports: " + (String)object);
                }
            }
            catch (LogInitializationException logInitializationException) {
                throw StartupProcessor.a(logInitializationException);
            }
            try {
                logger = this.myLogger;
                string2 = StringUtil.isEmpty((String)this.myContext.getServletContextPath()) ? "/" : this.myContext.getServletContextPath();
            }
            catch (LogInitializationException logInitializationException) {
                throw StartupProcessor.a(logInitializationException);
            }
            logger.info("Context path: \"" + string2 + "\", servlet real path: \"" + this.myContext.getServletRealPath() + "\"");
            string = this.doGetDistributionType();
            try {
                try {
                    if (!CurrentNodeInfo.isSecondaryNode() || !string.equals("war")) break block23;
                }
                catch (LogInitializationException logInitializationException) {
                    throw StartupProcessor.a(logInitializationException);
                }
                throw new KnownNonRecoverableStartupError("Secondary node can't be started from the .war distribution");
            }
            catch (LogInitializationException logInitializationException) {
                throw StartupProcessor.a(logInitializationException);
            }
        }
        try {
            this.myLogger.info("TeamCity distribution type: " + string);
            this.myContext.setDistributionType(string);
            this.myContext.setStartingAsMain(CurrentNodeInfo.isMainNode());
            this.myLogger.info("TeamCity server node id: " + CurrentNodeInfo.getNodeId());
            this.createPidFile(file);
            if (this.checkUnsupportedJava()) {
                return;
            }
        }
        catch (LogInitializationException logInitializationException) {
            throw StartupProcessor.a(logInitializationException);
        }
        try {
            if (this.checkServerRole()) {
                return;
            }
        }
        catch (LogInitializationException logInitializationException) {
            throw StartupProcessor.a(logInitializationException);
        }
        try {
            this.checkThatNecessaryDirectoriesAreWritable();
            if (this.checkDirtyUpgrade()) {
                return;
            }
        }
        catch (LogInitializationException logInitializationException) {
            throw StartupProcessor.a(logInitializationException);
        }
        this.startAutoThreadDumpService();
        this.checkRootUrlSpecifiedForSecondaryNode();
        this.initSecurityChecksForSecondaryNode();
        IOGuardInitializer.init();
        this.proposeNextStage(StartupStage.LOOKING_FOR_DATA_DIR, new StartupStage[0]);
    }

    private InetAddress[] detectServerIPs() throws SocketException, UnknownHostException {
        InetAddress[] inetAddressArray;
        block3: {
            block2: {
                InetAddress[] inetAddressArray2 = NetworkUtil.getSelfAddresses(inetAddress -> inetAddress instanceof Inet4Address);
                try {
                    if (inetAddressArray2.length <= 0) break block2;
                    inetAddressArray = inetAddressArray2;
                    break block3;
                }
                catch (SocketException socketException) {
                    throw StartupProcessor.a(socketException);
                }
            }
            InetAddress[] inetAddressArray3 = new InetAddress[1];
            inetAddressArray = inetAddressArray3;
            inetAddressArray3[0] = InetAddress.getLocalHost();
        }
        return inetAddressArray;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void checkRootUrlSpecifiedForSecondaryNode() {
        block12: {
            try {
                if (CurrentNodeInfo.isMainNode()) {
                    return;
                }
            }
            catch (MalformedURLException malformedURLException) {
                throw StartupProcessor.a(malformedURLException);
            }
            String string = CurrentNodeInfo.getStartupPropertiesRootURL();
            try {
                if (string == null) break block12;
                try {
                    new URL(string);
                    return;
                }
                catch (MalformedURLException malformedURLException) {
                    Loggers.STARTUP.error("Specified root URL (" + string + ") cannot be parsed. Error message is: '" + malformedURLException.getMessage() + "'. Please specify the correct root URL using the following JVM argument: -Dteamcity.server.rootURL=<" + CurrentNodeInfo.getNodeId() + " URL>");
                    return;
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw StartupProcessor.a(illegalStateException);
            }
        }
        try {
            if (CurrentNodeInfo.getSupportedResponsibilities().contains(NodeResponsibility.CAN_PROCESS_BUILD_MESSAGES)) {
                throw new IllegalStateException("Root URL is not specified for " + CurrentNodeInfo.getNodeId() + ". Please specify root URL using the following JVM argument: -Dteamcity.server.rootURL=<" + CurrentNodeInfo.getNodeId() + " URL>");
            }
        }
        catch (MalformedURLException malformedURLException) {
            throw StartupProcessor.a(malformedURLException);
        }
        try {
            if (!CurrentNodeInfo.isSecondaryNode()) return;
            Loggers.STARTUP.info("Root URL is not explicitly specified, the node URL will be detected automatically from the first request and may be not accurate. Please specify root URL using the following JVM argument: -Dteamcity.server.rootURL=<this node URL>");
            return;
        }
        catch (MalformedURLException malformedURLException) {
            throw StartupProcessor.a(malformedURLException);
        }
    }

    private void initSecurityChecksForSecondaryNode() {
        try {
            if (CurrentNodeInfo.isMainNode()) {
                return;
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupProcessor.a(illegalStateException);
        }
        SecondaryNodeDBAccess.init(() -> Collections.emptySet());
        SecondaryNodeSecurityManager.init(() -> Collections.emptySet());
    }

    private void registerWritablePathsForSecondaryNode(@NotNull File file) {
        block18: {
            String string;
            block17: {
                try {
                    if (file == null) {
                        StartupProcessor.$$$reportNull$$$0(4);
                    }
                }
                catch (IllegalStateException illegalStateException) {
                    throw StartupProcessor.a(illegalStateException);
                }
                try {
                    if (CurrentNodeInfo.isMainNode()) {
                        return;
                    }
                }
                catch (IllegalStateException illegalStateException) {
                    throw StartupProcessor.a(illegalStateException);
                }
                try {
                    SecondaryNodeSecurityManager.addWritablePath((File)file);
                    SecondaryNodeSecurityManager.addWritablePath((File)new File(FileUtil.getTempDirectory()));
                    if (StartupConfiguration.getLocation() != null) {
                        SecondaryNodeSecurityManager.addWritablePath((File)new File(StartupConfiguration.getLocation()));
                    }
                }
                catch (IllegalStateException illegalStateException) {
                    throw StartupProcessor.a(illegalStateException);
                }
                string = TeamCityProperties.getPropertyOrNull((String)"catalina.base");
                try {
                    if (string != null) break block17;
                    this.myLogger.warn("Failed to add installation directory to the writable directories list: missing 'catalina.base' system property");
                    break block18;
                }
                catch (IllegalStateException illegalStateException) {
                    throw StartupProcessor.a(illegalStateException);
                }
            }
            SecondaryNodeSecurityManager.addWritablePath((File)new File(string));
        }
        String string = this.myContext.getServletRealPath();
        try {
            if (string != null) {
                SecondaryNodeSecurityManager.addWritablePath((File)new File(string));
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupProcessor.a(illegalStateException);
        }
        String string2 = TeamCityProperties.getPropertyOrNull((String)"teamcity.webinf.plugins.path");
        try {
            if (string2 != null) {
                SecondaryNodeSecurityManager.addWritablePath((File)new File(string2));
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupProcessor.a(illegalStateException);
        }
    }

    @NotNull
    private String doGetDistributionType() {
        block7: {
            String string;
            try {
                File file;
                block6: {
                    String string2 = this.myServletContext.getRealPath("/WEB-INF/DistributionType.txt");
                    if (string2 == null) {
                        return "unknown";
                    }
                    file = new File(string2);
                    try {
                        if (file.exists() && file.isFile()) break block6;
                    }
                    catch (IOException iOException) {
                        throw StartupProcessor.a(iOException);
                    }
                    return "unknown";
                }
                String string3 = StringUtil.stripLeftAndRight((String)FileUtil.readText((File)file, (String)"UTF-8"), (CharFilter)CharFilter.WHITESPACE_FILTER);
                if (StringUtil.isEmptyOrSpaces((String)string3)) break block7;
                string = string3;
            }
            catch (IOException iOException) {
                this.myLogger.warnAndDebugDetails("Exception during TeamCity distribution type detection", (Throwable)iOException);
                return "unknown";
            }
            if (string == null) {
                StartupProcessor.$$$reportNull$$$0(5);
            }
            return string;
        }
        return "unknown";
    }

    private void startAutoThreadDumpService() {
        TeamCityDiagnosticService teamCityDiagnosticService = TeamCityDiagnosticService.getInstance();
        PeriodicThreadDumpSaver periodicThreadDumpSaver = PeriodicThreadDumpSaver.init();
        teamCityDiagnosticService.scheduleTask((TeamCityDiagnosticService.Task)periodicThreadDumpSaver);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Nullable
    private String fetchWebServerConfigurationDetails() {
        var1_1 = new StringBuilder();
        try {
            var2_2 = ManagementFactory.getPlatformMBeanServer();
            var3_4 = var2_2.queryNames(new ObjectName("*:type=Connector,*"), null);
            for (ObjectName var5_6 : var3_4) {
                block21: {
                    block22: {
                        var6_8 = var2_2.getAttribute(var5_6, "scheme");
                        var7_9 = String.valueOf(var2_2.getAttribute(var5_6, "protocol"));
                        var8_10 = var5_6.getKeyProperty("port");
                        try {
                            if (this.myContext.getPort() != null) break block21;
                            if (var8_10 == null) break block22;
                            ** GOTO lbl17
                        }
                        catch (NumberFormatException v0) {
                            try {
                                throw StartupProcessor.a(v0);
lbl17:
                                // 2 sources

                                this.myContext.setPort(Integer.valueOf(Integer.parseInt(var8_10)));
                                break block22;
                            }
                            catch (IllegalStateException v1) {
                                throw StartupProcessor.a(v1);
                            }
                        }
                        catch (NumberFormatException var9_11) {
                            // empty catch block
                        }
                    }
                    try {
                        v2 = this.myContext;
                        v3 = var6_8 != null ? String.valueOf(var6_8) : "http";
                    }
                    catch (NumberFormatException v4) {
                        throw StartupProcessor.a(v4);
                    }
                    v2.setScheme(v3);
                }
                try {
                    if (var1_1.length() > 0) {
                        var1_1.append(", ");
                    }
                }
                catch (NumberFormatException v5) {
                    throw StartupProcessor.a(v5);
                }
                var1_1.append(var8_10).append(" (protocol: ").append(var7_9).append(", scheme: ").append(var6_8).append(')');
            }
            var4_5 = var2_2.queryNames(new ObjectName("*:type=Server,*"), null);
            var5_7 = var4_5.iterator();
            while (var5_7.hasNext() != false) {
                var6_8 = (ObjectName)var5_7.next();
                var7_9 = String.valueOf(var2_2.getAttribute((ObjectName)var6_8, "port"));
                try {
                    if (var1_1.length() > 0) {
                        var1_1.append(", ");
                    }
                }
                catch (NumberFormatException v6) {
                    throw StartupProcessor.a(v6);
                }
                var1_1.append(var7_9).append(" (shutdown port)");
            }
            return var1_1.toString();
        }
        catch (Throwable var2_3) {
            try {
                this.myLogger.debug("Could not get web server settings", var2_3);
                if (var1_1.length() <= 0) return null;
                v7 = var1_1.append("<could not get all the details>").toString();
                return v7;
            }
            catch (NumberFormatException v8) {
                throw StartupProcessor.a(v8);
            }
        }
    }

    private boolean checkDirtyUpgrade() {
        block7: {
            Object object;
            Logger logger;
            Collection<String> collection;
            block9: {
                block8: {
                    String string = this.myServletContext.getRealPath(VERSION_FILE_NAME);
                    try {
                        if (string != null) {
                            this.myVersionFile = new VersionFile(string, this.myLogger);
                            this.myVersionFile.lock();
                        }
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw StartupProcessor.a(illegalStateException);
                    }
                    collection = this.findPreviousVersionFiles();
                    try {
                        try {
                            if (collection.isEmpty()) break block7;
                            logger = this.myLogger;
                            if (this.myContext.getServletRealPath() == null) break block8;
                        }
                        catch (IllegalStateException illegalStateException) {
                            throw StartupProcessor.a(illegalStateException);
                        }
                        object = "\"" + this.myContext.getServletRealPath() + "\"";
                        break block9;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw StartupProcessor.a(illegalStateException);
                    }
                }
                object = "\"<TeamCity server home>/webapps\"";
            }
            logger.error("Corrupted TeamCity installation is detected. Reinstall TeamCity server ensuring no files from previous installation are present under " + (String)object + " directory.", (Throwable)null);
            this.myLogger.debug("Detected " + StringUtil.pluralize((String)"file", (int)collection.size()) + " from previous installation: " + String.valueOf(collection));
            this.myContext.setPreviousInstallationFiles(collection);
            this.proposeNextStage(StartupStage.CORRUPTED_INSTALLATION, new StartupStage[0]);
            return true;
        }
        return false;
    }

    private boolean checkUnsupportedJava() {
        if (com.intellij.openapi.util.text.StringUtil.compareVersionNumbers((String)TCSystemInfo.JAVA_VERSION, (String)"1.8.0") < 0) {
            String string = "TeamCity server doesn't support Java with versions less than 1.8. \nPlease upgrade Java and run TeamCity server again. \nCurrent Java version: " + TCSystemInfo.JAVA_VERSION + "\nCurrent Java location: " + System.getProperty("java.home");
            this.myLogger.error(string);
            System.out.println(string);
            this.myContext.setUnsupportedJava(TCSystemInfo.JAVA_VERSION, System.getProperty("java.home"));
            this.proposeNextStage(StartupStage.FATAL_ERROR, new StartupStage[0]);
            return true;
        }
        return false;
    }

    private boolean checkServerRole() {
        String string = TeamCityProperties.getPropertyOrNull((String)"teamcity.server.role");
        if (string != null) {
            String string2 = "Property teamcity.server.role is no longer supported. Please review command line properties and server memory settings";
            this.myLogger.error("Property teamcity.server.role is no longer supported. Please review command line properties and server memory settings");
            System.out.println("Property teamcity.server.role is no longer supported. Please review command line properties and server memory settings");
            this.myContext.setUnsupportedServerRole(string);
            this.proposeNextStage(StartupStage.FATAL_ERROR, new StartupStage[0]);
            return true;
        }
        return false;
    }

    private Collection<String> findPreviousVersionFiles() {
        Set set = this.myServletContext.getResourcePaths("/");
        try {
            if (set == null) {
                this.myLogger.warn("Failed to obtain servlet resource paths.");
                return Collections.emptySet();
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupProcessor.a(illegalStateException);
        }
        List list = CollectionsUtil.filterCollection((Collection)set, (Filter)new /* Unavailable Anonymous Inner Class!! */);
        return CollectionsUtil.convertCollection((Collection)list, (Converter)new /* Unavailable Anonymous Inner Class!! */);
    }

    private void createPidFile(@NotNull File file) {
        try {
            if (file == null) {
                StartupProcessor.$$$reportNull$$$0(6);
            }
        }
        catch (Exception exception) {
            throw StartupProcessor.a(exception);
        }
        File file2 = null;
        String string = this.getEnv("TEAMCITY_PID_FILE_PATH");
        if (string != null) {
            file2 = new File(string);
        } else if (TeamCityProperties.getBoolean((String)"teamcity.server.pidFile.alwaysCreate")) {
            file2 = new File(file, "teamcity.pid");
        }
        if (file2 != null) {
            long l = -1L;
            try {
                l = (Long)SecondaryNodeSecurityManager.executeSafeCommandLine(ProcessTreeTerminator::getCurrentPid);
            }
            catch (Exception exception) {
                this.myLogger.warnAndDebugDetails("Failed to determine TeamCity server process ID", (Throwable)exception);
            }
            if (l != -1L) {
                try {
                    file2 = FileUtil.getCanonicalFile((File)file2);
                    FileUtil.writeFile((File)file2, (String)String.valueOf(l), (String)"US-ASCII");
                    this.myLogger.info("TeamCity server process ID (" + l + ") stored in: " + file2.getAbsolutePath());
                }
                catch (Exception exception) {
                    this.myLogger.warn("Failed to write process ID into file: " + file2.getAbsolutePath() + ", error: " + String.valueOf(exception));
                }
            }
        }
    }

    private void checkThatNecessaryDirectoriesAreWritable() throws ServerStartupError {
        String string;
        block12: {
            File file;
            block11: {
                String string2 = this.myContext.getServletRealPath();
                try {
                    if (string2 == null) {
                        throw new UnknownServerStartupError("Could not obtain the servlet real path. Check the servlet engine settings.");
                    }
                }
                catch (ServerStartupError serverStartupError) {
                    throw StartupProcessor.a(serverStartupError);
                }
                file = new File(string2);
                try {
                    try {
                        if (file.exists() && file.isDirectory()) break block11;
                    }
                    catch (ServerStartupError serverStartupError) {
                        throw StartupProcessor.a(serverStartupError);
                    }
                    throw new UnknownServerStartupError("The servlet real path (" + string2 + ") is not a directory");
                }
                catch (ServerStartupError serverStartupError) {
                    throw StartupProcessor.a(serverStartupError);
                }
            }
            File file2 = new File(file, "WEB-INF");
            this.ensureWritableDir(file2);
            File file3 = new File(file2, "plugins");
            this.ensureWritableDir(file3);
            File file4 = new File(file3, ".unpacked");
            this.ensureWritableDir(file4);
            string = System.getProperty("java.io.tmpdir");
            try {
                try {
                    if (string != null && string.length() != 0) break block12;
                }
                catch (ServerStartupError serverStartupError) {
                    throw StartupProcessor.a(serverStartupError);
                }
                throw new KnownNonRecoverableStartupError("The temporary directory is not specified. Use the TMP environment variable or specify it via -Djava.io.tmpdir.");
            }
            catch (ServerStartupError serverStartupError) {
                throw StartupProcessor.a(serverStartupError);
            }
        }
        File file = new File(string);
        this.ensureWritableDir(file);
    }

    private void ensureWritableDir(File file) throws ServerStartupError {
        block10: {
            try {
                if (file.exists() && file.isDirectory()) break block10;
            }
            catch (Exception exception) {
                throw StartupProcessor.a(exception);
            }
            try {
                file.mkdirs();
            }
            catch (Exception exception) {
                throw new KnownNonRecoverableStartupError("Failed to create directory inside (" + String.valueOf(file) + "): " + exception.getMessage() + ". This directory must be available to TeamCity on write.", (Throwable)exception);
            }
            try {
                if (!file.exists()) {
                    throw new KnownNonRecoverableStartupError("Failed to create directory inside (" + String.valueOf(file) + "). This directory must be available to TeamCity on write.");
                }
            }
            catch (Exception exception) {
                throw StartupProcessor.a(exception);
            }
        }
        try {
            File file2 = new File(file, "testfile.txt");
            FileUtil.writeFileAndReportErrors((File)file2, (String)"Drop this file. Please");
            FileUtil.delete((File)file2);
        }
        catch (IOException iOException) {
            throw new KnownNonRecoverableStartupError("It seems the directory (" + String.valueOf(file.getAbsoluteFile()) + ") is not writable. TeamCity requires it to be writable.", (Throwable)iOException);
        }
        catch (Exception exception) {
            throw new UnknownServerStartupError("It seems the directory (" + String.valueOf(file.getAbsoluteFile()) + ") is not accessible. TeamCity requires it to be accessible and writable.", (Throwable)exception);
        }
    }

    private void doLookingForDataDir() throws ServerStartupError, IOException {
        Object object;
        if (!this.myContext.isDataDirSpecified()) {
            object = DataDirectoryLocationResolver.determineDataDir((Properties)System.getProperties(), (Map)this.myContext.getEnv(), (Logger)this.myLogger);
            object = this.resolveCanonicalDataDirPath((File)object);
            this.myContext.setDataDir((File)object);
        }
        object = this.getDataDirectoryManager();
        this.logExistence("The TeamCity Data Directory", object.getRootDir(), object.isRootDirExist());
        this.analyseDataDirectory();
    }

    private void analyseDataDirectory() throws KnownNonRecoverableStartupError, IOException {
        block14: {
            block15: {
                TeamCityDataDirectoryManager teamCityDataDirectoryManager;
                block13: {
                    block10: {
                        block12: {
                            block11: {
                                teamCityDataDirectoryManager = this.getDataDirectoryManager();
                                try {
                                    try {
                                        if (!CurrentNodeInfo.isSecondaryNode()) break block10;
                                        if (!teamCityDataDirectoryManager.isDataDirInitialized()) break block11;
                                    }
                                    catch (KnownNonRecoverableStartupError knownNonRecoverableStartupError) {
                                        throw StartupProcessor.a(knownNonRecoverableStartupError);
                                    }
                                    this.doProcessDataDirectoryContainingTeamCity();
                                    break block12;
                                }
                                catch (KnownNonRecoverableStartupError knownNonRecoverableStartupError) {
                                    throw StartupProcessor.a(knownNonRecoverableStartupError);
                                }
                            }
                            this.confirmDataDirectoryLocation();
                        }
                        return;
                    }
                    try {
                        if (!this.isRestoreMarkerExists()) break block13;
                        this.myContext.setSelectedDirError("Restoring TeamCity from the backup failed or did not finish. As a result, the Data Directory and database may contain corrupted data. Please clean them manually to continue.");
                        this.myContext.setFirstStart(true);
                        this.confirmDataDirectoryLocation();
                        break block14;
                    }
                    catch (KnownNonRecoverableStartupError knownNonRecoverableStartupError) {
                        throw StartupProcessor.a(knownNonRecoverableStartupError);
                    }
                }
                try {
                    try {
                        if (!teamCityDataDirectoryManager.isRootDirExist() || !teamCityDataDirectoryManager.containsConfiguredTeamcity()) break block15;
                    }
                    catch (KnownNonRecoverableStartupError knownNonRecoverableStartupError) {
                        throw StartupProcessor.a(knownNonRecoverableStartupError);
                    }
                    this.myContext.setFirstStart(false);
                    this.doProcessDataDirectoryContainingTeamCity();
                    break block14;
                }
                catch (KnownNonRecoverableStartupError knownNonRecoverableStartupError) {
                    throw StartupProcessor.a(knownNonRecoverableStartupError);
                }
            }
            this.myContext.setFirstStart(true);
            this.doProcessFirstStart();
        }
    }

    private void doProcessFirstStart() {
        block5: {
            block6: {
                block4: {
                    try {
                        if (!this.getDataDirectoryManager().getDatabasePropertiesFile().exists()) break block4;
                        this.myLogger.info("Bypassing the First Start screen because the 'database.properties' file exists");
                        this.proposeNextStage(StartupStage.CREATE_NEW_DATA_DIR, new StartupStage[0]);
                        break block5;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw StartupProcessor.a(illegalStateException);
                    }
                }
                try {
                    if (!this.myContext.isBypassingMaintenanceQuestions()) break block6;
                    this.myLogger.info("Bypassing the First Start screen in developer mode");
                    this.proposeNextStage(StartupStage.CREATE_NEW_DATA_DIR, new StartupStage[0]);
                    break block5;
                }
                catch (IllegalStateException illegalStateException) {
                    throw StartupProcessor.a(illegalStateException);
                }
            }
            this.confirmDataDirectoryLocation();
        }
    }

    private void confirmDataDirectoryLocation() {
        block3: {
            block2: {
                try {
                    if (!this.canPersistDataDirLocation()) break block2;
                    this.askUserForDataDirectoryPath();
                    break block3;
                }
                catch (IllegalStateException illegalStateException) {
                    throw StartupProcessor.a(illegalStateException);
                }
            }
            this.askUserToConfirmDataDirectoryPath();
        }
    }

    private void askUserForDataDirectoryPath() {
        block4: {
            try {
                try {
                    if (!TCSystemInfo.isWindows || this.getEnv("ALLUSERSPROFILE") == null) break block4;
                }
                catch (IllegalStateException illegalStateException) {
                    throw StartupProcessor.a(illegalStateException);
                }
                this.myContext.setSuggestedFirstStartDataDirLocation(this.getEnv("ALLUSERSPROFILE") + "\\JetBrains\\TeamCity");
            }
            catch (IllegalStateException illegalStateException) {
                throw StartupProcessor.a(illegalStateException);
            }
        }
        this.myLogger.info("Asking user to select the TeamCity Data Directory path from UI");
        this.myContext.setAllowOverrideDataDirectory(true);
        this.showFirstStartScreen();
    }

    private void askUserToConfirmDataDirectoryPath() {
        this.myLogger.info("Asking user to confirm first start with the predefined TeamCity Data Directory path");
        this.showFirstStartScreen();
    }

    private void showFirstStartScreen() {
        this.proposeNextStage(StartupStage.FIRST_START_SCREEN, new StartupStage[0]);
        String string = "Startup confirmation is required. Open TeamCity web page in the browser. Server is running at " + StartupProcessor.constructServerAddress(this.myContext);
        this.myLogger.info(string);
        System.out.println(string);
    }

    private static String constructServerAddress(@NotNull StartupContext startupContext) {
        StringBuilder stringBuilder;
        Integer n;
        String string;
        block9: {
            try {
                if (startupContext == null) {
                    StartupProcessor.$$$reportNull$$$0(7);
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw StartupProcessor.a(illegalStateException);
            }
            String string2 = startupContext.getScheme();
            string = startupContext.getIPAddress();
            n = startupContext.getPort();
            stringBuilder = new StringBuilder();
            try {
                if (!StringUtil.isEmpty((String)string2)) {
                    stringBuilder.append(string2).append("://");
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw StartupProcessor.a(illegalStateException);
            }
            if (!StringUtil.isEmpty((String)string)) {
                string = "localhost";
            }
            try {
                if (n == null || !WebUtil.isStandardPort((int)n, (boolean)"https".equals(string2))) break block9;
            }
            catch (IllegalStateException illegalStateException) {
                throw StartupProcessor.a(illegalStateException);
            }
            n = null;
        }
        stringBuilder.append(WebUtil.hostAndPort((String)string, (Integer)n));
        stringBuilder.append(startupContext.getServletContextPath());
        return stringBuilder.toString();
    }

    /*
     * Loose catch block
     */
    private void doProcessDataDirSelectedByUser() throws ServerStartupError, IOException {
        File file;
        block25: {
            block24: {
                block17: {
                    block29: {
                        block18: {
                            block23: {
                                block22: {
                                    block19: {
                                        block21: {
                                            block20: {
                                                block27: {
                                                    block26: {
                                                        file = this.overrideDataDirLocationFromUserInput(this.myContext.getDataDirPathSelectedFromUI());
                                                        try {
                                                            if (this.isRestoreMarkerExists()) {
                                                                this.myContext.setSelectedDirError("Restoring TeamCity from a backup failed or did not finish. The Data Directory and database may contain corrupted data. Please clean Data Directory and database manually to continue.");
                                                                this.proposeNextStage(StartupStage.FIRST_START_SCREEN, new StartupStage[0]);
                                                                return;
                                                            }
                                                        }
                                                        catch (Exception exception) {
                                                            throw StartupProcessor.a(exception);
                                                        }
                                                        if (this.myContext.isStartingAsMain()) break block17;
                                                        if (this.myContext.getDataDirManager().isDataDirInitialized()) break block18;
                                                        break block26;
                                                        catch (Exception exception) {
                                                            throw StartupProcessor.a(exception);
                                                        }
                                                    }
                                                    if (this.myContext.isAskedForNodeType()) break block19;
                                                    break block27;
                                                    catch (Exception exception) {
                                                        throw StartupProcessor.a(exception);
                                                    }
                                                }
                                                try {
                                                    block28: {
                                                        if (!this.myContext.isBypassingMaintenanceQuestions()) break block20;
                                                        break block28;
                                                        catch (Exception exception) {
                                                            throw StartupProcessor.a(exception);
                                                        }
                                                    }
                                                    this.myContext.setAskedForNodeType(true);
                                                    this.myContext.setStartingAsMain(true);
                                                    this.proposeNextStage(StartupStage.PROCESS_DATA_DIR_SELECTED_BY_USER, new StartupStage[0]);
                                                    break block21;
                                                }
                                                catch (Exception exception) {
                                                    throw StartupProcessor.a(exception);
                                                }
                                            }
                                            this.proposeNextStage(StartupStage.ASK_NODE_TYPE, new StartupStage[0]);
                                        }
                                        return;
                                    }
                                    try {
                                        this.myLogger.info("The Data Directory " + String.valueOf(this.myContext.getDataDirManager().getRootDir()) + " doesn't contain configured TeamCity");
                                        if (this.myContext.getDataDirManager().isRootDirExist()) break block22;
                                        this.myContext.setSelectedDirError("TeamCity Data Directory was not found or is not accessible by the specified path.");
                                        break block23;
                                    }
                                    catch (Exception exception) {
                                        throw StartupProcessor.a(exception);
                                    }
                                }
                                this.myContext.setSelectedDirError("TeamCity Data Directory is found but doesn't contain configured TeamCity. Ensure that the Data Directory is initialized by the main TeamCity Server.");
                            }
                            this.proposeNextStage(StartupStage.FIRST_START_SCREEN, new StartupStage[0]);
                            break block29;
                        }
                        this.persistSelectedDataDir(file);
                        this.doProcessDataDirectoryContainingTeamCity();
                    }
                    return;
                }
                try {
                    if (this.myContext.isRestoreProcess()) break block24;
                    this.createNewDataDirectory();
                    break block25;
                }
                catch (Exception exception) {
                    throw StartupProcessor.a(exception);
                }
            }
            try {
                this.checkConfigFolderIsEmpty();
            }
            catch (Exception exception) {
                this.myContext.addException((Throwable)exception);
                this.proposeNextStage(StartupStage.FIRST_START_SCREEN, new StartupStage[0]);
                return;
            }
            this.proposeNextStage(StartupStage.RESTORE_SETTINGS_SCREEN, new StartupStage[0]);
        }
        this.persistSelectedDataDir(file);
        this.setJdbcDriverMetadata();
    }

    private boolean isRestoreMarkerExists() {
        Path path = Paths.get(String.valueOf(this.myContext.getDataDirManager().getRootDir()) + File.separator + "restoreStatusMarker.txt", new String[0]);
        return Files.exists(path, new LinkOption[0]);
    }

    private void setJdbcDriverMetadata() {
        if (JdbcDriverDownloader.isEnabled()) {
            JdbcDriverDownloader jdbcDriverDownloader = new JdbcDriverDownloader(this.myLogger, StartupProcessor.newUrlConnectionFactory(), (TeamCityPropertiesProvider)new InternalPropertiesProvider());
            try {
                Map map = jdbcDriverDownloader.listLatest();
                this.myContext.setJdbcDriverMetadata(map);
            }
            catch (IOException iOException) {
                block5: {
                    try {
                        if (!this.myLogger.isDebugEnabled()) break block5;
                        this.myLogger.warn((Throwable)iOException);
                    }
                    catch (IOException iOException2) {
                        throw StartupProcessor.a(iOException2);
                    }
                }
                this.myLogger.warn(iOException.getMessage());
            }
        }
    }

    private void persistSelectedDataDir(File file) throws KnownNonRecoverableStartupError {
        try {
            if (file != null) {
                this.persistDataDirLocation(file);
            }
        }
        catch (KnownNonRecoverableStartupError knownNonRecoverableStartupError) {
            throw StartupProcessor.a(knownNonRecoverableStartupError);
        }
        File file2 = DataDirectoryLocationResolver.determineDataDir((Properties)System.getProperties(), (Map)this.myContext.getEnv());
        TeamCityServerPropertiesModel.initTeamCityProperties((File)file2);
    }

    private void createNewDataDirectory() throws ServerStartupError, IOException {
        block6: {
            block5: {
                try {
                    if (this.myContext.isBypassingMaintenanceQuestions()) {
                        this.myContext.setStartingAsMain(true);
                        this.myContext.setAskedForNodeType(true);
                    }
                }
                catch (ServerStartupError serverStartupError) {
                    throw StartupProcessor.a(serverStartupError);
                }
                TeamCityDataDirectoryManager teamCityDataDirectoryManager = this.myContext.getDataDirManager();
                try {
                    if (!teamCityDataDirectoryManager.containsConfiguredTeamcity()) break block5;
                    this.doProcessDataDirectoryContainingTeamCity();
                    break block6;
                }
                catch (ServerStartupError serverStartupError) {
                    throw StartupProcessor.a(serverStartupError);
                }
            }
            this.doCreateNewDataDir();
        }
    }

    private void doCreateNewDataDir() throws ServerStartupError, IOException {
        block14: {
            block15: {
                block13: {
                    try {
                        if (!this.myContext.isStartingAsMain()) {
                            throw this.notFoundDataDirException();
                        }
                    }
                    catch (ServerStartupError serverStartupError) {
                        throw StartupProcessor.a(serverStartupError);
                    }
                    TeamCityDataDirectoryManager teamCityDataDirectoryManager = this.myContext.getDataDirManager();
                    teamCityDataDirectoryManager.ensureExistence();
                    this.initNodeSpecificDataDir();
                    List list = teamCityDataDirectoryManager.listNonTeamcityFolders();
                    try {
                        if (!list.isEmpty()) {
                            this.myLogger.warn("Data directory '" + String.valueOf(teamCityDataDirectoryManager.getRootDir()) + "' contains non-TeamCity files and directories: " + String.valueOf(list));
                        }
                    }
                    catch (ServerStartupError serverStartupError) {
                        throw StartupProcessor.a(serverStartupError);
                    }
                    try {
                        if (!StartupUtil.askUserForDBSettings()) {
                            this.activateDataDirectory();
                        }
                    }
                    catch (ServerStartupError serverStartupError) {
                        throw StartupProcessor.a(serverStartupError);
                    }
                    try {
                        this.myContext.refreshDatabasePropertiesFile();
                        this.initializeHttps();
                        this.initializeSecondaryHttp();
                        if (!this.myContext.isDatabaseExplicitlyConfigured()) break block13;
                        this.myLogger.info("Bypassing asking a user for DB settings because database parameters were specified (via 'database.properties' file or environment)");
                        this.proposeNextStage(StartupStage.CREATE_NEW_DB, new StartupStage[0]);
                        break block14;
                    }
                    catch (ServerStartupError serverStartupError) {
                        throw StartupProcessor.a(serverStartupError);
                    }
                }
                try {
                    if (StartupUtil.askUserForDBSettings()) break block15;
                    this.myLogger.info("Silently bypassing asking a user for DB settings, will create default ones");
                    this.proposeNextStage(StartupStage.CREATE_NEW_DB, new StartupStage[0]);
                    break block14;
                }
                catch (ServerStartupError serverStartupError) {
                    throw StartupProcessor.a(serverStartupError);
                }
            }
            this.proposeNextStage(StartupStage.DB_SETTINGS_SCREEN, new StartupStage[0]);
        }
    }

    private File overrideDataDirLocationFromUserInput(String string) throws ServerStartupError {
        try {
            if (string == null) {
                return null;
            }
        }
        catch (IOException iOException) {
            throw StartupProcessor.a(iOException);
        }
        File file = new File(string);
        this.myLogger.info("The TeamCity Data Directory path configured by the user from the UI: '" + string + "'");
        File file2 = this.resolveCanonicalDataDirPath(file);
        if (!file2.getPath().equals(string)) {
            file = file2;
        }
        try {
            this.myContext.setDataDir(file);
        }
        catch (IOException iOException) {
            throw new DataDirectoryException(iOException.getMessage(), (Throwable)iOException);
        }
        return file;
    }

    /*
     * Loose catch block
     */
    protected void doProcessDataDirectoryContainingTeamCity() throws KnownNonRecoverableStartupError, IOException {
        block23: {
            block24: {
                block22: {
                    block21: {
                        List list;
                        block20: {
                            String string;
                            Logger logger;
                            DatabasePropertiesFile databasePropertiesFile;
                            TeamCityDataDirectoryManager teamCityDataDirectoryManager;
                            block19: {
                                File file;
                                block18: {
                                    this.processNodesConfigFile();
                                    if (this.myContext.isStartingAsMain()) break block18;
                                    try {
                                        block25: {
                                            if (CurrentNodeInfo.isNodeIdSpecified()) break block18;
                                            break block25;
                                            catch (LogInitializationException logInitializationException) {
                                                throw StartupProcessor.a(logInitializationException);
                                            }
                                        }
                                        this.proposeNextStage(StartupStage.TEAMCITY_NODE_ID_NOT_SPECIFIED, new StartupStage[0]);
                                        return;
                                    }
                                    catch (LogInitializationException logInitializationException) {
                                        throw StartupProcessor.a(logInitializationException);
                                    }
                                }
                                CurrentNodeInfo.setMainNode((boolean)this.myContext.isStartingAsMain());
                                try {
                                    file = LogInitializer.getLogDir();
                                }
                                catch (LogInitializationException logInitializationException) {
                                    this.myContext.addException((Throwable)logInitializationException);
                                    this.proposeNextStage(StartupStage.FATAL_ERROR, new StartupStage[0]);
                                    return;
                                }
                                this.reRegisterSecurityManagers(file);
                                this.initNodeSpecificDataDir();
                                teamCityDataDirectoryManager = this.getDataDirectoryManager();
                                String string2 = HSQLDBSpecific.determineRealHsqlMajorVersion((File)teamCityDataDirectoryManager.getInternalDatabaseDataFile());
                                this.myContext.setInternalDatabaseFileVersion(string2);
                                this.logExistence("The main configuration file", teamCityDataDirectoryManager.getMainConfigFile(), teamCityDataDirectoryManager.getMainConfigFile().exists());
                                databasePropertiesFile = this.myContext.getDatabasePropertiesFile();
                                try {
                                    try {
                                        if ($assertionsDisabled || databasePropertiesFile != null) break block19;
                                    }
                                    catch (LogInitializationException logInitializationException) {
                                        throw StartupProcessor.a(logInitializationException);
                                    }
                                    throw new AssertionError();
                                }
                                catch (LogInitializationException logInitializationException) {
                                    throw StartupProcessor.a(logInitializationException);
                                }
                            }
                            try {
                                this.logExistence("The database properties file", databasePropertiesFile.getFile(), databasePropertiesFile.isFileExists());
                                this.logExistence("The internal database data file", teamCityDataDirectoryManager.getInternalDatabaseDataFile(), teamCityDataDirectoryManager.getInternalDatabaseDataFile().exists());
                                logger = this.myLogger;
                                string = databasePropertiesFile.isConfigurationPresent() ? "present" : "not present";
                            }
                            catch (LogInitializationException logInitializationException) {
                                throw StartupProcessor.a(logInitializationException);
                            }
                            logger.info("An explicit database configuration is " + string);
                            this.retrieveDataDirVersion();
                            list = teamCityDataDirectoryManager.listNonTeamcityFolders();
                            try {
                                if (!list.isEmpty()) break block20;
                                this.myLogger.debug("The Data Directory contains TeamCity files only");
                                break block21;
                            }
                            catch (LogInitializationException logInitializationException) {
                                throw StartupProcessor.a(logInitializationException);
                            }
                        }
                        this.myLogger.warn("The Data Directory contains non-TeamCity files and directories: " + String.valueOf(list));
                    }
                    try {
                        this.logInternalProperties();
                        this.logNodeType();
                        this.initializeHttps();
                        this.initializeSecondaryHttp();
                        if (this.isEncryptionConfigurationValid()) break block22;
                        this.proposeNextStage(StartupStage.ENCRYPTION_CONFIGURATION_ERROR, new StartupStage[0]);
                        break block23;
                    }
                    catch (LogInitializationException logInitializationException) {
                        throw StartupProcessor.a(logInitializationException);
                    }
                }
                try {
                    if (!this.shouldShowXMXWarning()) break block24;
                    this.myContext.setRecommendedMemorySettings("-Xmx1500m");
                    this.proposeNextStage(StartupStage.XMX_WARNING, new StartupStage[0]);
                    break block23;
                }
                catch (LogInitializationException logInitializationException) {
                    throw StartupProcessor.a(logInitializationException);
                }
            }
            this.proposeNextStage(StartupStage.LOOKING_FOR_DB_SETTINGS, new StartupStage[0]);
        }
    }

    private boolean isEncryptionConfigurationValid() throws IOException {
        String string;
        StartupContext startupContext;
        try {
            if (!TeamCityProperties.getBooleanOrTrue((String)"teamcity.startup.validateEncryptionConfig")) {
                return true;
            }
        }
        catch (IOException iOException) {
            throw StartupProcessor.a(iOException);
        }
        ServerPaths serverPaths = this.myContext.getServerPaths();
        Set set = EncryptionUtilsHelper.readKeysHashes((ServerPaths)serverPaths);
        EncryptionKeysStorageProvider encryptionKeysStorageProvider = new EncryptionKeysStorageProvider(serverPaths);
        EncryptionKeysStorage encryptionKeysStorage = encryptionKeysStorageProvider.getKeysStorage();
        try {
            startupContext = this.myContext;
            string = encryptionKeysStorage instanceof EnvVarBasedEncryptionKeysStorage ? "ENV" : "INTERNAL_CONFIG";
        }
        catch (IOException iOException) {
            throw StartupProcessor.a(iOException);
        }
        startupContext.setEncryptionKeysSource(string);
        encryptionKeysStorage.reloadSettings();
        Set set2 = encryptionKeysStorage.getKeys().keySet();
        return set2.containsAll(set);
    }

    private void generateCustomEncryptionKeyOnFirstStart() {
        ServerPaths serverPaths = this.myContext.getServerPaths();
        EncryptionKeysStorageProvider encryptionKeysStorageProvider = new EncryptionKeysStorageProvider(serverPaths);
        EncryptionKeysStorage encryptionKeysStorage = encryptionKeysStorageProvider.getKeysStorage();
        try {
            if (!(encryptionKeysStorage instanceof FileBasedEncryptionConfigStorage)) {
                this.myLogger.info("Skip generating custom encryption key on first start for keyStorage=" + String.valueOf(encryptionKeysStorage));
                return;
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupProcessor.a(illegalStateException);
        }
        encryptionKeysStorage.reloadSettings();
        CustomKeyEncryptionStrategy customKeyEncryptionStrategy = new CustomKeyEncryptionStrategy(encryptionKeysStorageProvider);
        this.myLogger.info("Generating custom encryption key on first start, using strategy=" + customKeyEncryptionStrategy.getName());
        StartupUtil.writeNewEncryptionSettings((StartupContext)this.myContext, (EncryptionKeysStorage)encryptionKeysStorage, (EncryptionStrategy)customKeyEncryptionStrategy);
    }

    private void initializeHttps() {
        TeamCityDataDirectoryManager teamCityDataDirectoryManager = this.getDataDirectoryManager();
        HttpsConfiguratorImpl httpsConfiguratorImpl = new HttpsConfiguratorImpl((HttpsConnectorManager)new HttpsConnectorManagerImpl());
        try {
            httpsConfiguratorImpl.initBeforeSpring(teamCityDataDirectoryManager);
        }
        catch (HttpsConfigurationException httpsConfigurationException) {
            this.myLogger.error("Unable to initialize HttpsConfigurator", (Throwable)httpsConfigurationException);
        }
        this.myContext.setHttpsConfigurator((HttpsConfigurator)httpsConfiguratorImpl);
    }

    private void initializeSecondaryHttp() {
        SecondaryHttpConfigurator.init();
    }

    private void logNodeType() {
        String string;
        try {
            string = this.myContext.isStartingAsMain() ? "main" : "secondary";
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupProcessor.a(illegalStateException);
        }
        String string2 = string;
        this.myLogger.info("TeamCity " + string2 + " node (id: " + CurrentNodeInfo.getNodeId() + ") is starting...");
    }

    private void reRegisterSecurityManagers(File file) {
        try {
            if (this.myContext.isStartingAsMain()) {
                SecondaryNodeSecurityManager.reset();
                SecondaryNodeDBAccess.reset();
                return;
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupProcessor.a(illegalStateException);
        }
        this.registerWritablePathsForSecondaryNode(file);
        this.initSecurityChecksForSecondaryNode();
    }

    private void processNodesConfigFile() throws KnownNonRecoverableStartupError, IOException {
        Map<String, NodeConfig> map;
        String string;
        File file;
        block23: {
            boolean bl;
            block21: {
                block22: {
                    boolean bl2;
                    block20: {
                        block19: {
                            String string2 = "MAIN_SERVER";
                            file = this.getDataDirectoryManager().getNodesConfigFile();
                            string = CurrentNodeInfo.getNodeId();
                            try {
                                try {
                                    if (!this.myContext.isAskedForNodeType() || !this.myContext.isStartingAsMain()) break block19;
                                }
                                catch (KnownNonRecoverableStartupError knownNonRecoverableStartupError) {
                                    throw StartupProcessor.a(knownNonRecoverableStartupError);
                                }
                                bl2 = true;
                                break block20;
                            }
                            catch (KnownNonRecoverableStartupError knownNonRecoverableStartupError) {
                                throw StartupProcessor.a(knownNonRecoverableStartupError);
                            }
                        }
                        bl2 = false;
                    }
                    bl = bl2;
                    try {
                        try {
                            try {
                                if (bl || "MAIN_SERVER".equals(string)) break block21;
                            }
                            catch (KnownNonRecoverableStartupError knownNonRecoverableStartupError) {
                                throw StartupProcessor.a(knownNonRecoverableStartupError);
                            }
                            if (file.exists()) break block22;
                        }
                        catch (KnownNonRecoverableStartupError knownNonRecoverableStartupError) {
                            throw StartupProcessor.a(knownNonRecoverableStartupError);
                        }
                        this.myContext.setStartingAsMain(false);
                        return;
                    }
                    catch (KnownNonRecoverableStartupError knownNonRecoverableStartupError) {
                        throw StartupProcessor.a(knownNonRecoverableStartupError);
                    }
                }
                NodeConfig nodeConfig = this.readNodesConfig().get(string);
                try {
                    if (nodeConfig == null) {
                        this.myContext.setStartingAsMain(false);
                        return;
                    }
                }
                catch (KnownNonRecoverableStartupError knownNonRecoverableStartupError) {
                    throw StartupProcessor.a(knownNonRecoverableStartupError);
                }
                this.setStartingContextFrom(nodeConfig);
                return;
            }
            if (!file.exists()) {
                this.myLogger.info("Nodes config file " + String.valueOf(file) + " doesn't exist. The file will be created");
                map = new HashMap<String, NodeConfig>();
            } else {
                map = this.readNodesConfig();
            }
            NodeConfig nodeConfig = map.get(string);
            try {
                try {
                    if (nodeConfig == null || bl) break block23;
                }
                catch (KnownNonRecoverableStartupError knownNonRecoverableStartupError) {
                    throw StartupProcessor.a(knownNonRecoverableStartupError);
                }
                this.setStartingContextFrom(nodeConfig);
                return;
            }
            catch (KnownNonRecoverableStartupError knownNonRecoverableStartupError) {
                throw StartupProcessor.a(knownNonRecoverableStartupError);
            }
        }
        NodeConfig nodeConfig = new NodeConfig(string, "#", NodeResponsibility.assignableResponsibilities(), new HashMap());
        map.put(string, nodeConfig);
        FilePersisterUtil.saveDocument((Document)NodesConfigUtil.createNodesConfigXmlDoc(map), (File)file);
        this.setStartingContextFrom(nodeConfig);
    }

    private void setStartingContextFrom(@NotNull NodeConfig nodeConfig) {
        try {
            if (nodeConfig == null) {
                StartupProcessor.$$$reportNull$$$0(8);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupProcessor.a(illegalStateException);
        }
        this.myContext.setStartingAsMain(nodeConfig.getEnabledResponsibilities().contains(NodeResponsibility.MAIN_NODE));
    }

    @NotNull
    private Map<String, NodeConfig> readNodesConfig() throws KnownNonRecoverableStartupError {
        Map map;
        Map map2;
        File file = this.getDataDirectoryManager().getNodesConfigFile();
        try {
            map2 = NodesConfigUtil.readConfigurationFromDisk((File)file);
        }
        catch (JDOMException jDOMException) {
            throw new KnownNonRecoverableStartupError("Could not parse the nodes configuration file: " + file.getAbsolutePath(), (Throwable)jDOMException);
        }
        catch (IOException iOException) {
            throw new KnownNonRecoverableStartupError("Could not read the nodes configuration file: " + file.getAbsolutePath(), (Throwable)iOException);
        }
        try {
            map = map2;
            if (map == null) {
                StartupProcessor.$$$reportNull$$$0(9);
            }
        }
        catch (JDOMException jDOMException) {
            throw StartupProcessor.a(jDOMException);
        }
        return map;
    }

    private void logInternalProperties() {
        Map map = TeamCityProperties.getModel().getUserDefinedProperties();
        try {
            if (!map.isEmpty()) {
                this.myLogger.info("Server internal properties (" + map.size() + "): " + map.entrySet().stream().map(entry -> (String)entry.getKey() + "=" + (String)entry.getValue()).collect(Collectors.joining("; ")));
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupProcessor.a(illegalStateException);
        }
    }

    private boolean shouldShowXMXWarning() {
        boolean bl;
        try {
            if (!TCSystemInfo.isWindows) {
                return false;
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupProcessor.a(illegalStateException);
        }
        try {
            if (this.warningWasShown()) {
                return false;
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupProcessor.a(illegalStateException);
        }
        try {
            if (!TCSystemInfo.is64Bit) {
                return false;
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupProcessor.a(illegalStateException);
        }
        try {
            if (!this.isCustomXmxUsed()) {
                return false;
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupProcessor.a(illegalStateException);
        }
        long l = Runtime.getRuntime().maxMemory();
        try {
            bl = l < 1300000000L;
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupProcessor.a(illegalStateException);
        }
        return bl;
    }

    private boolean warningWasShown() {
        File file;
        File file2 = this.myContext.getDataDirManager().getRootDir();
        try {
            file = CurrentNodeInfo.isMainNode() ? file2 : NodeSpecificConfigs.getNodeDataDirectory((File)file2);
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupProcessor.a(illegalStateException);
        }
        File file3 = file;
        return XmxWarningUtil.isXmxWarningFileExist((File)file3);
    }

    private boolean isCustomXmxUsed() {
        boolean bl;
        try {
            bl = this.getEnv("TEAMCITY_SERVER_MEM_OPTS") != null;
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupProcessor.a(illegalStateException);
        }
        return bl;
    }

    private void initNodeSpecificDataDir() throws IOException {
        File file;
        block11: {
            block12: {
                boolean bl;
                try {
                    bl = !"MAIN_SERVER".equals(CurrentNodeInfo.getNodeId());
                }
                catch (IOException iOException) {
                    throw StartupProcessor.a(iOException);
                }
                boolean bl2 = bl;
                File file2 = this.myContext.getDataDirManager().getRootDir();
                boolean bl3 = this.myContext.isStartingAsMain();
                try {
                    if (!bl3) {
                        SecondaryNodeSecurityManager.addReadOnlyPath((File)this.myContext.getDataDirManager().getConfigDir());
                    }
                }
                catch (IOException iOException) {
                    throw StartupProcessor.a(iOException);
                }
                file = NodeSpecificConfigs.getNodeDataDirectory((File)file2);
                try {
                    try {
                        try {
                            Loggers.STARTUP.info("Node specific data directory location: " + String.valueOf(file));
                            if (bl3) break block11;
                            if (!bl2) break block12;
                        }
                        catch (IOException iOException) {
                            throw StartupProcessor.a(iOException);
                        }
                        SecondaryNodeSecurityManager.addWritablePath((File)file);
                        if (file.exists()) break block11;
                    }
                    catch (IOException iOException) {
                        throw StartupProcessor.a(iOException);
                    }
                    SecondaryNodeSecurityManager.runSafeDiskWriteOperation(() -> FileUtil.createDir((File)file));
                    this.myLogger.info("The node's data directory was created: " + String.valueOf(file));
                    break block11;
                }
                catch (IOException iOException) {
                    throw StartupProcessor.a(iOException);
                }
            }
            SecondaryNodeSecurityManager.addWritablePath((File)new File(this.myContext.getServerPaths().getCachesDir()));
        }
        this.myContext.setNodeSpecificConfigs(file);
    }

    private File resolveCanonicalDataDirPath(File file) throws DataDirectoryException {
        try {
            return file.getCanonicalFile();
        }
        catch (IOException iOException) {
            throw new DataDirectoryException("Could not use the TeamCity Data Directory \"" + String.valueOf(file) + "\": " + iOException.getMessage(), (Throwable)iOException);
        }
        catch (SecurityException securityException) {
            throw new DataDirectoryException("Could not access the TeamCity Data Directory \"" + String.valueOf(file) + "\": " + securityException.getMessage(), (Throwable)securityException);
        }
    }

    private void retrieveDataDirVersion() throws KnownNonRecoverableStartupError {
        block5: {
            try {
                block4: {
                    int n = VersionManager.loadVersionFromDisk((ServerPaths)this.getServerPaths());
                    try {
                        this.myContext.setDataDirVersion(n);
                        if (n <= 0) break block4;
                        this.myLogger.info("Data Directory version: " + n);
                        break block5;
                    }
                    catch (DataDirectoryVersionException dataDirectoryVersionException) {
                        throw StartupProcessor.a(dataDirectoryVersionException);
                    }
                }
                this.myLogger.warn("Could not detect Data Directory version");
            }
            catch (DataDirectoryVersionException dataDirectoryVersionException) {
                throw new KnownNonRecoverableStartupError((Exception)((Object)dataDirectoryVersionException));
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void activateDataDirectory() throws DataDirectoryException {
        TeamCityDataDirectoryManager teamCityDataDirectoryManager = this.myContext.getDataDirManager();
        try {
            VersionManager.writeVersionIntoMainConfigFile((String)teamCityDataDirectoryManager.getConfigDir().getAbsolutePath(), (int)this.myContext.getSoftwareVersion());
            String string = StartupUtil.writeNewServerIdIntoMainConfigFile((File)teamCityDataDirectoryManager.getConfigDir(), (StartupContext)this.myContext, (boolean)false);
            this.generateCustomEncryptionKeyOnFirstStart();
            this.createNodesConfigFile();
            InstallationsManager installationsManager = this.myContext.getInstallationsManager();
            try {
                if (installationsManager.isEnabled()) {
                    try {
                        installationsManager.persist((InstallationInfo)new TeamCityInstallationInfo(string, teamCityDataDirectoryManager.getRootDir()));
                    }
                    catch (IOException iOException) {
                        this.myLogger.info("Cannot access server installation info storage.");
                    }
                }
            }
            catch (DataDirectoryException dataDirectoryException) {
                throw StartupProcessor.a(dataDirectoryException);
            }
        }
        catch (IOException iOException) {
            throw new DataDirectoryException("Failed to write version into the main config file", (Throwable)iOException);
        }
        try {
            try {
                if ($assertionsDisabled) return;
                if (teamCityDataDirectoryManager.containsConfiguredTeamcity()) return;
                throw new AssertionError((Object)"The main config file should be created in the 'StartupProcessor.activateDataDirectory()' method.");
            }
            catch (IOException iOException) {
                throw StartupProcessor.a(iOException);
            }
        }
        catch (IOException iOException) {
            throw StartupProcessor.a(iOException);
        }
    }

    private void createNodesConfigFile() throws IOException {
        String string;
        String string2;
        NodeConfig nodeConfig;
        NodeConfig nodeConfig2;
        String string3;
        HashMap<String, NodeConfig> hashMap;
        String string4 = CurrentNodeInfo.getStartupPropertiesRootURL();
        HashMap<String, NodeConfig> hashMap2 = new HashMap<String, NodeConfig>();
        try {
            NodeConfig nodeConfig3;
            hashMap = hashMap2;
            string3 = CurrentNodeInfo.getNodeId();
            nodeConfig2 = nodeConfig3;
            nodeConfig = nodeConfig3;
            string2 = CurrentNodeInfo.getNodeId();
            string = string4 != null ? string4 : "#";
        }
        catch (IOException iOException) {
            throw StartupProcessor.a(iOException);
        }
        nodeConfig2(string2, string, NodeResponsibility.assignableResponsibilities(), new HashMap());
        hashMap.put(string3, nodeConfig);
        FilePersisterUtil.saveDocument((Document)NodesConfigUtil.createNodesConfigXmlDoc(hashMap2), (File)this.getDataDirectoryManager().getNodesConfigFile());
    }

    /*
     * Loose catch block
     */
    private void doLookingForDatabaseSettings() throws ServerStartupError {
        block30: {
            block29: {
                boolean bl;
                block33: {
                    Object object;
                    File file;
                    DatabasePropertiesFile databasePropertiesFile;
                    block27: {
                        boolean bl2;
                        block26: {
                            TeamCityDataDirectoryManager teamCityDataDirectoryManager;
                            block25: {
                                teamCityDataDirectoryManager = this.getDataDirectoryManager();
                                databasePropertiesFile = this.myContext.getDatabasePropertiesFile();
                                if ($assertionsDisabled) break block25;
                                try {
                                    block31: {
                                        if (databasePropertiesFile != null) break block25;
                                        break block31;
                                        catch (IncorrectConnectionSettingsException incorrectConnectionSettingsException) {
                                            throw StartupProcessor.a(incorrectConnectionSettingsException);
                                        }
                                    }
                                    throw new AssertionError();
                                }
                                catch (IncorrectConnectionSettingsException incorrectConnectionSettingsException) {
                                    throw StartupProcessor.a(incorrectConnectionSettingsException);
                                }
                            }
                            this.myContext.refreshDatabasePropertiesFile();
                            boolean bl3 = databasePropertiesFile.isFileExists();
                            this.logExistence("Database properties file", databasePropertiesFile.getFile(), bl3);
                            file = teamCityDataDirectoryManager.getInternalDatabaseDataFile();
                            if (!file.exists()) break block26;
                            try {
                                block32: {
                                    if (!file.isFile()) break block26;
                                    break block32;
                                    catch (IncorrectConnectionSettingsException incorrectConnectionSettingsException) {
                                        throw StartupProcessor.a(incorrectConnectionSettingsException);
                                    }
                                }
                                bl2 = true;
                                break block27;
                            }
                            catch (IncorrectConnectionSettingsException incorrectConnectionSettingsException) {
                                throw StartupProcessor.a(incorrectConnectionSettingsException);
                            }
                        }
                        bl2 = bl = false;
                    }
                    if (bl) {
                        object = HSQLDBSpecific.determineRealHsqlMajorVersion((File)file);
                        this.myContext.setInternalDatabaseFileVersion((String)object);
                        this.myLogger.info("Internal HSQL database file (" + file.getPath() + ") exists, version: " + (String)object);
                    } else {
                        this.myContext.setInternalDatabaseFileVersion(null);
                        this.myLogger.info("Internal HSQL database file (" + file.getPath() + ") doesn't exist");
                    }
                    if (!databasePropertiesFile.isConfigurationPresent()) break block33;
                    try {
                        Object object2;
                        Logger logger;
                        String string;
                        Logger logger2;
                        block28: {
                            object = databasePropertiesFile.getProperties();
                            if ($assertionsDisabled) break block28;
                            try {
                                block34: {
                                    if (object != null) break block28;
                                    break block34;
                                    catch (IncorrectConnectionSettingsException incorrectConnectionSettingsException) {
                                        throw StartupProcessor.a(incorrectConnectionSettingsException);
                                    }
                                }
                                throw new AssertionError((Object)"The properties file should be loaded");
                            }
                            catch (IncorrectConnectionSettingsException incorrectConnectionSettingsException) {
                                throw StartupProcessor.a(incorrectConnectionSettingsException);
                            }
                        }
                        String string2 = ((Properties)object).getProperty("connectionUrl");
                        try {
                            logger2 = this.myLogger;
                            string = string2 != null ? string2 : "<undefined>";
                        }
                        catch (IncorrectConnectionSettingsException incorrectConnectionSettingsException) {
                            throw StartupProcessor.a(incorrectConnectionSettingsException);
                        }
                        try {
                            logger2.info("Database connection URL: " + string);
                            if (string2 == null) {
                                throw new StartupDatabaseAccessingError("The 'connectionUrl' is not defined in the 'database.properties' file.");
                            }
                        }
                        catch (IncorrectConnectionSettingsException incorrectConnectionSettingsException) {
                            throw StartupProcessor.a(incorrectConnectionSettingsException);
                        }
                        JdbcConnectionString jdbcConnectionString = new JdbcConnectionString(string2);
                        boolean bl4 = DatabaseTypeUtil.isDatabaseExternal((JdbcConnectionString)jdbcConnectionString);
                        try {
                            this.myContext.setDatabaseExternal(bl4);
                            logger = this.myLogger;
                            object2 = bl4 ? "Using external (" + String.valueOf(jdbcConnectionString.getDBType()) + ") database" : "Using internal (HSQL) database";
                        }
                        catch (IncorrectConnectionSettingsException incorrectConnectionSettingsException) {
                            throw StartupProcessor.a(incorrectConnectionSettingsException);
                        }
                        logger.info((String)object2);
                        this.proposeNextStage(StartupStage.DB_CONNECT, new StartupStage[0]);
                    }
                    catch (IncorrectConnectionSettingsException incorrectConnectionSettingsException) {
                        throw new KnownNonRecoverableStartupError((Exception)((Object)incorrectConnectionSettingsException));
                    }
                }
                try {
                    if (!bl) break block29;
                    this.myLogger.info("Using internal (HSQL) database");
                    this.myContext.setDatabaseExternal(false);
                    this.proposeNextStage(StartupStage.DB_CONNECT, new StartupStage[0]);
                    break block30;
                }
                catch (IncorrectConnectionSettingsException incorrectConnectionSettingsException) {
                    throw StartupProcessor.a(incorrectConnectionSettingsException);
                }
            }
            this.myLogger.info("Cound not find neither explicit database configuration nor an internal database.");
            this.myContext.setDatabaseExternal(false);
            this.handleEmptyDatabase(this.myContext.getSoftwareVersion(), this.myContext.getDataDirVersion(), false);
        }
    }

    private void doCreateNewDatabase() throws ServerStartupError {
        block35: {
            block34: {
                JdbcConnectionString jdbcConnectionString;
                String string;
                block33: {
                    block32: {
                        block31: {
                            try {
                                if (!this.myContext.isStartingAsMain()) {
                                    throw this.notFoundDataDirException();
                                }
                            }
                            catch (DatabaseIsNotReachableException databaseIsNotReachableException) {
                                throw StartupProcessor.a(databaseIsNotReachableException);
                            }
                            this.myLogger.info("Creating a new database");
                            TeamCityDatabaseManager teamCityDatabaseManager = this.myContext.getDatabaseManager();
                            try {
                                if (teamCityDatabaseManager != null) {
                                    teamCityDatabaseManager.disconnect();
                                    this.myContext.setDatabaseManager(null);
                                }
                            }
                            catch (DatabaseIsNotReachableException databaseIsNotReachableException) {
                                throw StartupProcessor.a(databaseIsNotReachableException);
                            }
                            DatabasePropertiesFile databasePropertiesFile = this.myContext.getDatabasePropertiesFile();
                            try {
                                if (databasePropertiesFile == null || !databasePropertiesFile.isConfigurationPresent()) break block31;
                            }
                            catch (DatabaseIsNotReachableException databaseIsNotReachableException) {
                                throw StartupProcessor.a(databaseIsNotReachableException);
                            }
                            string = databasePropertiesFile.getConnectionUrl();
                            if (!string.isEmpty()) {
                                jdbcConnectionString = new JdbcConnectionString(string);
                                boolean bl = DatabaseTypeUtil.isDatabaseExternal((JdbcConnectionString)jdbcConnectionString);
                                this.myContext.setDatabaseExternal(bl);
                            }
                        }
                        try {
                            this.performConnectToDatabase();
                        }
                        catch (DatabaseIsNotReachableException databaseIsNotReachableException) {
                            try {
                                if (this.myContext.getPreviousStage() == StartupStage.CREATE_NEW_DATA_DIR) {
                                    this.logDBError((Exception)((Object)databaseIsNotReachableException));
                                    this.myLogger.info("Could not reach the database, it may be the database server is not started yet or connection properties are not valid.");
                                    this.proposeNextStage(StartupStage.DB_CONNECT_CONTINUE, StartupStage.CREATE_NEW_DB);
                                    return;
                                }
                            }
                            catch (DatabaseIsNotReachableException databaseIsNotReachableException2) {
                                throw StartupProcessor.a(databaseIsNotReachableException2);
                            }
                            throw databaseIsNotReachableException;
                        }
                        string = this.myContext.getDatabaseManager();
                        try {
                            try {
                                if ($assertionsDisabled || string != null) break block32;
                            }
                            catch (DatabaseIsNotReachableException databaseIsNotReachableException) {
                                throw StartupProcessor.a(databaseIsNotReachableException);
                            }
                            throw new AssertionError();
                        }
                        catch (DatabaseIsNotReachableException databaseIsNotReachableException) {
                            throw StartupProcessor.a(databaseIsNotReachableException);
                        }
                    }
                    try {
                        try {
                            if ($assertionsDisabled || string.isConnected()) break block33;
                        }
                        catch (DatabaseIsNotReachableException databaseIsNotReachableException) {
                            throw StartupProcessor.a(databaseIsNotReachableException);
                        }
                        throw new AssertionError();
                    }
                    catch (DatabaseIsNotReachableException databaseIsNotReachableException) {
                        throw StartupProcessor.a(databaseIsNotReachableException);
                    }
                }
                this.analyseDatabase();
                this.checkDatabaseIsEmpty();
                this.performInitDatabaseAndCreateSchema();
                jdbcConnectionString = this.myContext.getLicenseAgreement();
                if (jdbcConnectionString == null) {
                    this.initializeLicenseAgreement();
                    jdbcConnectionString = this.myContext.getLicenseAgreement();
                }
                try {
                    if (!string.isHeartbeatActive()) {
                        this.performDatabaseLockIfNeeded();
                    }
                }
                catch (DatabaseIsNotReachableException databaseIsNotReachableException) {
                    throw StartupProcessor.a(databaseIsNotReachableException);
                }
                try {
                    if (this.myContext.isDatabaseSettingsFromUISpecified()) {
                        this.createDatabasePropertiesFile(this.myContext.getConnectionString());
                    }
                }
                catch (DatabaseIsNotReachableException databaseIsNotReachableException) {
                    throw StartupProcessor.a(databaseIsNotReachableException);
                }
                try {
                    this.prefillDataDirectoryBeforeStartIfNeeded();
                    if (!TeamCityProperties.getBoolean((String)"teamcity.licenseAgreement.accepted")) break block34;
                    jdbcConnectionString.accept();
                    this.proposeNextStage(StartupStage.APPLICATION_STARTING, new StartupStage[0]);
                    break block35;
                }
                catch (DatabaseIsNotReachableException databaseIsNotReachableException) {
                    throw StartupProcessor.a(databaseIsNotReachableException);
                }
            }
            this.proposeNextStage(StartupStage.LICENSE_AGREEMENT_SCREEN, new StartupStage[0]);
        }
    }

    private void checkDatabaseIsEmpty() throws KnownRecoverableDatabaseSettingsError {
        block9: {
            block10: {
                String string;
                block12: {
                    block11: {
                        try {
                            if (!this.myContext.isDatabaseAccessible()) {
                                throw new StartupDatabaseAccessingError("Database is not accessible");
                            }
                        }
                        catch (KnownRecoverableDatabaseSettingsError knownRecoverableDatabaseSettingsError) {
                            throw StartupProcessor.a(knownRecoverableDatabaseSettingsError);
                        }
                        int n = this.myContext.getCountOfTables();
                        try {
                            try {
                                try {
                                    if (n == 0) break block9;
                                    if (!this.myContext.isSchemaBelongsToTeamcity()) break block10;
                                }
                                catch (KnownRecoverableDatabaseSettingsError knownRecoverableDatabaseSettingsError) {
                                    throw StartupProcessor.a(knownRecoverableDatabaseSettingsError);
                                }
                                if (!this.myContext.isDatabaseExternal()) break block11;
                            }
                            catch (KnownRecoverableDatabaseSettingsError knownRecoverableDatabaseSettingsError) {
                                throw StartupProcessor.a(knownRecoverableDatabaseSettingsError);
                            }
                            string = "Found a TeamCity schema when expected an empty database";
                            break block12;
                        }
                        catch (KnownRecoverableDatabaseSettingsError knownRecoverableDatabaseSettingsError) {
                            throw StartupProcessor.a(knownRecoverableDatabaseSettingsError);
                        }
                    }
                    string = "Found a TeamCity internal database when no system directory or an empty system directory is expected";
                }
                String string2 = string;
                throw new StartupDatabaseAccessingError(string2);
            }
            this.reportNonTeamcityDatabase();
        }
    }

    /*
     * Unable to fully structure code
     */
    private File createDatabasePropertiesFile(@NotNull JdbcConnectionString var1_1) throws KnownNonRecoverableStartupError {
        block18: {
            block17: {
                block19: {
                    try {
                        if (var1_1 == null) {
                            StartupProcessor.$$$reportNull$$$0(10);
                        }
                    }
                    catch (IOException v0) {
                        throw StartupProcessor.a(v0);
                    }
                    var2_2 = var1_1.getDBType();
                    if (var1_1.getPassword() != null) break block17;
                    if (var2_2 == DatabaseType.ORACLE) ** GOTO lbl21
                    break block19;
                    catch (IOException v1) {
                        throw StartupProcessor.a(v1);
                    }
                }
                try {
                    block20: {
                        if (var2_2 != DatabaseType.HSQLDB2) break block17;
                        break block20;
                        catch (IOException v2) {
                            throw StartupProcessor.a(v2);
                        }
                    }
                    v3 = true;
                    break block18;
                }
                catch (IOException v4) {
                    throw StartupProcessor.a(v4);
                }
            }
            v3 = false;
        }
        var3_3 = v3;
        try {
            var4_4 = this.myContext.getDataDirManager().getDatabasePropertiesFile();
            try {
                if (var4_4.exists()) {
                    this.myLogger.debug("The database.properties file already exists, rewriting");
                }
            }
            catch (IOException v5) {
                throw StartupProcessor.a(v5);
            }
            var5_6 = new Properties();
            try {
                StartupProcessor.setPropertiesProperty(var5_6, "connectionUrl", var1_1.build(var3_3));
                if (!var3_3) {
                    StartupProcessor.setPropertiesProperty(var5_6, "connectionProperties.user", var1_1.getUser());
                    StartupProcessor.setPropertiesProperty(var5_6, "connectionProperties.password", var1_1.getPassword());
                }
            }
            catch (IOException v6) {
                throw StartupProcessor.a(v6);
            }
            PropertiesUtil.storeProperties((Properties)var5_6, (File)var4_4, null);
            this.myLogger.info("Created a new database.properties file with settings specified from Web UI.");
            return var4_4;
        }
        catch (IOException var4_5) {
            throw new KnownNonRecoverableStartupError("Failed to create the 'database.properties' file: " + var4_5.getMessage(), (Throwable)var4_5);
        }
    }

    private static void setPropertiesProperty(@NotNull Properties properties, @NotNull String string, @Nullable String string2) {
        block9: {
            block8: {
                try {
                    if (properties == null) {
                        StartupProcessor.$$$reportNull$$$0(11);
                    }
                }
                catch (IllegalStateException illegalStateException) {
                    throw StartupProcessor.a(illegalStateException);
                }
                try {
                    if (string == null) {
                        StartupProcessor.$$$reportNull$$$0(12);
                    }
                }
                catch (IllegalStateException illegalStateException) {
                    throw StartupProcessor.a(illegalStateException);
                }
                try {
                    if (string2 == null) break block8;
                    properties.setProperty(string, string2);
                    break block9;
                }
                catch (IllegalStateException illegalStateException) {
                    throw StartupProcessor.a(illegalStateException);
                }
            }
            properties.remove(string);
        }
    }

    private void reportNonTeamcityDatabase() throws KnownRecoverableDatabaseSettingsError {
        String string = "It looks like the database doesn't belong to TeamCity.\nThe database contains tables: " + CollectionsUtil.asString((Collection)this.myContext.getDatabaseTables(), (int)10);
        throw new DatabaseIsNotEmptyError(string);
    }

    /*
     * Loose catch block
     */
    private void performInitDatabaseAndCreateSchema() throws ServerStartupError {
        TeamCityDatabaseManager teamCityDatabaseManager;
        block7: {
            this.myLogger.info("Creating database schema");
            teamCityDatabaseManager = this.myContext.getDatabaseManager();
            if ($assertionsDisabled) break block7;
            try {
                block8: {
                    if (teamCityDatabaseManager != null) break block7;
                    break block8;
                    catch (DatabaseIsOutOfSpaceException databaseIsOutOfSpaceException) {
                        throw StartupProcessor.a(databaseIsOutOfSpaceException);
                    }
                }
                throw new AssertionError();
            }
            catch (DatabaseIsOutOfSpaceException databaseIsOutOfSpaceException) {
                throw StartupProcessor.a(databaseIsOutOfSpaceException);
            }
        }
        try {
            this.performCreateNewSchema();
        }
        catch (DatabaseIsOutOfSpaceException databaseIsOutOfSpaceException) {
            throw new KnownNonRecoverableStartupError((Exception)((Object)databaseIsOutOfSpaceException));
        }
        catch (DatabaseAccessDeniedException databaseAccessDeniedException) {
            throw new KnownNonRecoverableStartupError((Exception)((Object)databaseAccessDeniedException));
        }
        teamCityDatabaseManager.reconnectMaintenanceDBF();
        this.analyseDatabase();
        this.myContext.setDatabaseCreatedOnStartup(true);
    }

    private void performCreateNewSchema() throws DBException {
        TeamCityDatabaseManager teamCityDatabaseManager;
        block4: {
            teamCityDatabaseManager = this.myContext.getDatabaseManager();
            try {
                try {
                    if ($assertionsDisabled || teamCityDatabaseManager != null) break block4;
                }
                catch (DBException dBException) {
                    throw StartupProcessor.a(dBException);
                }
                throw new AssertionError();
            }
            catch (DBException dBException) {
                throw StartupProcessor.a(dBException);
            }
        }
        teamCityDatabaseManager.withDBNoResult(dBFunctions -> DBSchema.createSchema((DBFunctions)dBFunctions, (GuidGenerator)teamCityDatabaseManager.getGuidGenerator(), (boolean)true));
        VersionManager.setDatabaseCreatedOnStartup((boolean)true);
    }

    private void analyseDatabase() throws ServerStartupError {
        TeamCityDatabaseManager teamCityDatabaseManager;
        block4: {
            teamCityDatabaseManager = this.myContext.getDatabaseManager();
            try {
                try {
                    if ($assertionsDisabled || teamCityDatabaseManager != null) break block4;
                }
                catch (ServerStartupError serverStartupError) {
                    throw StartupProcessor.a(serverStartupError);
                }
                throw new AssertionError();
            }
            catch (ServerStartupError serverStartupError) {
                throw StartupProcessor.a(serverStartupError);
            }
        }
        DatabaseType databaseType = teamCityDatabaseManager.getDatabaseType();
        StartupProcessor.verifyDatabaseType(teamCityDatabaseManager);
        StartupProcessor.verifyMySQLVersion(teamCityDatabaseManager);
        teamCityDatabaseManager.withDBNoResult(dBFunctions -> {
            Set set;
            block20: {
                block22: {
                    String string;
                    String string2;
                    Logger logger;
                    String string3 = dBFunctions.getSchemaNameForMetadata();
                    try {
                        if (string3 != null) {
                            this.myLogger.info("Using schema: " + string3);
                        }
                    }
                    catch (DBException dBException) {
                        throw StartupProcessor.a(dBException);
                    }
                    set = dBFunctions.retrieveSchemaTableNames(true, dBFunctions.dialect().isTemporaryDeclarationPersistent(), true);
                    try {
                        this.myContext.setDatabaseAccessible(true);
                        this.myContext.setDatabaseTables(set);
                        logger = this.myLogger;
                        string2 = string3 == null ? "Database" : "Schema";
                    }
                    catch (DBException dBException) {
                        throw StartupProcessor.a(dBException);
                    }
                    try {
                        string = set.isEmpty() ? "no" : Integer.toString(set.size());
                    }
                    catch (DBException dBException) {
                        throw StartupProcessor.a(dBException);
                    }
                    try {
                        block21: {
                            try {
                                try {
                                    try {
                                        logger.info(string2 + " contains " + string + " tables.");
                                        if (!set.isEmpty()) break block20;
                                        this.myContext.setSchemaBelongsToTeamcity(false);
                                        this.myContext.setDatabaseVersion(0);
                                        this.myContext.setDatabaseVersionIsGuessed(false);
                                        if (databaseType == DatabaseType.MSSQL) break block21;
                                    }
                                    catch (DBException dBException) {
                                        throw StartupProcessor.a(dBException);
                                    }
                                    if (databaseType == DatabaseType.ORACLE) break block21;
                                }
                                catch (DBException dBException) {
                                    throw StartupProcessor.a(dBException);
                                }
                                if (databaseType != DatabaseType.POSTGRESQL) break block22;
                            }
                            catch (DBException dBException) {
                                throw StartupProcessor.a(dBException);
                            }
                        }
                        this.lookForTablesInOtherSchemas(dBFunctions);
                    }
                    catch (DBException dBException) {
                        throw StartupProcessor.a(dBException);
                    }
                }
                return;
            }
            boolean bl = DBSchema.guessSchemaBelongsToTeamcity((Set)set);
            this.myContext.setSchemaBelongsToTeamcity(bl);
            if (bl) {
                int n;
                block23: {
                    n = DBSchema.loadDatabaseVersion((DBFunctions)dBFunctions);
                    if (n == 0) {
                        int n2 = this.myContext.getDataDirVersion();
                        try {
                            if (n2 < 73 || n2 >= 282) break block23;
                        }
                        catch (DBException dBException) {
                            throw StartupProcessor.a(dBException);
                        }
                        n = n2;
                        this.myContext.setDatabaseVersionIsGuessed(true);
                    }
                }
                this.myContext.setDatabaseVersion(n);
                this.myContext.setIncompatibleDatabaseVersion(DBSchema.hasIncompatibleSchemaChanges((DBFunctions)dBFunctions, (int)DBSchema.getCurrentVersion()));
                this.myLogger.info(this.myContext.getDatabaseVersionString());
            } else {
                this.myContext.setDatabaseVersion(0);
                this.myContext.setDatabaseVersionIsGuessed(false);
            }
        });
    }

    private static void verifyDatabaseType(@NotNull TeamCityDatabaseManager teamCityDatabaseManager) throws ServerStartupError {
        DatabaseType databaseType;
        DatabaseType databaseType2;
        block10: {
            try {
                if (teamCityDatabaseManager == null) {
                    StartupProcessor.$$$reportNull$$$0(13);
                }
            }
            catch (ServerStartupError serverStartupError) {
                throw StartupProcessor.a(serverStartupError);
            }
            databaseType2 = teamCityDatabaseManager.getDatabaseType();
            databaseType = (DatabaseType)teamCityDatabaseManager.withDB(dBFunctions -> ConnectionUtil.detectDatabaseType((Connection)dBFunctions.getConnection()));
            try {
                try {
                    if ($assertionsDisabled || databaseType != null) break block10;
                }
                catch (ServerStartupError serverStartupError) {
                    throw StartupProcessor.a(serverStartupError);
                }
                throw new AssertionError();
            }
            catch (ServerStartupError serverStartupError) {
                throw StartupProcessor.a(serverStartupError);
            }
        }
        try {
            if (databaseType != databaseType2) {
                throw new KnownNonRecoverableStartupError("Database type mismatch: specified " + databaseType2.humanReadableName + ", but found " + databaseType.humanReadableName);
            }
        }
        catch (ServerStartupError serverStartupError) {
            throw StartupProcessor.a(serverStartupError);
        }
    }

    private static void verifyMySQLVersion(@NotNull TeamCityDatabaseManager teamCityDatabaseManager) throws ServerStartupError {
        block10: {
            try {
                if (teamCityDatabaseManager == null) {
                    StartupProcessor.$$$reportNull$$$0(14);
                }
            }
            catch (ServerStartupError serverStartupError) {
                throw StartupProcessor.a(serverStartupError);
            }
            try {
                if (!TeamCityProperties.getBooleanOrTrue((String)"teamcity.startup.mysql.checkVersion")) {
                    return;
                }
            }
            catch (ServerStartupError serverStartupError) {
                throw StartupProcessor.a(serverStartupError);
            }
            Version version = teamCityDatabaseManager.getDatabaseProductVersion();
            try {
                try {
                    if (version.compareTo(new int[]{5, 1}) < 0 || version.compareTo(new int[]{5, 1, 49}) != -1) break block10;
                }
                catch (ServerStartupError serverStartupError) {
                    throw StartupProcessor.a(serverStartupError);
                }
                throw new KnownNonRecoverableStartupError("Your MySQL server version (" + String.valueOf(version) + ") has a bug (http://bugs.mysql.com/bug.php?id=51431) that may prevent TeamCity from running reliably. \nPlease upgrade your MySQL server to version 5.1.49 or higher.");
            }
            catch (ServerStartupError serverStartupError) {
                throw StartupProcessor.a(serverStartupError);
            }
        }
    }

    /*
     * Loose catch block
     */
    private void doDatabaseConnect() throws ServerStartupError {
        block17: {
            block19: {
                block18: {
                    if (!CurrentNodeInfo.isSecondaryNode()) break block17;
                    if (this.myContext.isDatabaseExternal()) break block17;
                    break block18;
                    catch (DatabaseIsNotReachableException databaseIsNotReachableException) {
                        throw StartupProcessor.a(databaseIsNotReachableException);
                    }
                }
                if (DevelopmentMode.isEnabled) break block17;
                break block19;
                catch (DatabaseIsNotReachableException databaseIsNotReachableException) {
                    throw StartupProcessor.a(databaseIsNotReachableException);
                }
            }
            try {
                block20: {
                    if (TeamCityProperties.getBoolean((String)"teamcity.startup.allowHSQLDBForSecondaryNode")) break block17;
                    break block20;
                    catch (DatabaseIsNotReachableException databaseIsNotReachableException) {
                        throw StartupProcessor.a(databaseIsNotReachableException);
                    }
                }
                throw new KnownNonRecoverableStartupError("The internal database (HSQLDB) is not supported in several nodes setup. You should migrate your TeamCity server to an external database.");
            }
            catch (DatabaseIsNotReachableException databaseIsNotReachableException) {
                throw StartupProcessor.a(databaseIsNotReachableException);
            }
        }
        try {
            if (this.myDbConnectStart == null) {
                this.myDbConnectStart = Instant.now();
            }
        }
        catch (DatabaseIsNotReachableException databaseIsNotReachableException) {
            throw StartupProcessor.a(databaseIsNotReachableException);
        }
        try {
            this.performConnectToDatabase();
        }
        catch (DatabaseIsNotReachableException databaseIsNotReachableException) {
            try {
                this.logDBError((Exception)((Object)databaseIsNotReachableException));
                if (this.myContext.isDatabaseSettingsFromUISpecified()) {
                    throw new StartupDatabaseAccessingError(databaseIsNotReachableException.getMessage(), (Throwable)databaseIsNotReachableException);
                }
            }
            catch (DatabaseIsNotReachableException databaseIsNotReachableException2) {
                throw StartupProcessor.a(databaseIsNotReachableException2);
            }
            this.myLogger.info("Could not reach the database, it may be the database server is not started yet or connection properties are not valid.");
            this.proposeNextStage(StartupStage.DB_CONNECT_CONTINUE, StartupStage.DB_CHECKING);
            return;
        }
        catch (SecondTeamCityInstanceError secondTeamCityInstanceError) {
            this.logDBError((Exception)((Object)secondTeamCityInstanceError));
            this.proposeNextStage(StartupStage.SECOND_INSTANCE, new StartupStage[0]);
            return;
        }
        this.myContext.recordStartupPhase("dbConnect", this.myDbConnectStart, Instant.now());
        this.proposeNextStage(StartupStage.DB_CHECKING, new StartupStage[0]);
    }

    private void logDBError(@NotNull Exception exception) {
        try {
            if (exception == null) {
                StartupProcessor.$$$reportNull$$$0(15);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupProcessor.a(illegalStateException);
        }
        this.myLogger.warnAndDebugDetails("Database connection error", (Throwable)exception);
    }

    /*
     * Unable to fully structure code
     */
    private void doDatabaseConnectContinue() throws ServerStartupError {
        var1_1 = 2;
        while (true) lbl-1000:
        // 3 sources

        {
            try {
                Thread.sleep(10000L);
            }
            catch (InterruptedException var2_3) {
                Thread.currentThread().interrupt();
            }
            try {
                this.performConnectToDatabaseInternal();
                this.myLogger.info("Connected successfully (after the " + var1_1 + " attempt)");
                var2_4 = this.myContext.getNextStage();
                try {
                    v0 = this;
                    v1 = var2_4 == null ? StartupStage.DB_CHECKING : var2_4;
                }
                catch (InterruptedException v2) {
                    throw StartupProcessor.a(v2);
                }
                v0.proposeNextStage(v1, new StartupStage[0]);
                this.myContext.setMaintenanceAsked(false);
                return;
            }
            catch (DatabaseIsNotReachableException var2_2) {
                try {
                    ++var1_1;
                    if (!this.myLogger.isDebugEnabled()) continue;
                    this.myLogger.debug("Could not connect: " + var2_2.getMessage());
                    continue;
                }
                catch (InterruptedException v3) {
                    throw StartupProcessor.a(v3);
                }
            }
            ** while (true)
            catch (SecondTeamCityInstanceError var2_5) {
                this.proposeNextStage(StartupStage.SECOND_INSTANCE, new StartupStage[0]);
                return;
            }
            break;
        }
    }

    private void performConnectToDatabase() throws SecondTeamCityInstanceError, StartupDatabaseAccessingError, DBException {
        StartupHelper.reloadJdbcDriversIfNeeded((StartupContext)this.myContext);
        if (StartupUtil.shouldDownloadJdbcDriver((StartupContext)this.myContext, (DatabaseType)this.myContext.getJdbcDriverType())) {
            try {
                JdbcDriverDownloader jdbcDriverDownloader = new JdbcDriverDownloader(this.myLogger, StartupProcessor.newUrlConnectionFactory(), (TeamCityPropertiesProvider)new InternalPropertiesProvider());
                Map map = jdbcDriverDownloader.listLatest();
                this.doDownloadJdbcDriversInternal(map);
            }
            catch (IOException iOException) {
                this.myLogger.warnAndDebugDetails("Failed to download JDBC drivers", (Throwable)iOException);
            }
        }
        try {
            this.performSetupDatabaseManager();
            if (this.myContext.getDatabaseManager() != null) {
                this.performConnectToDatabaseInternal();
            }
        }
        catch (IOException iOException) {
            throw StartupProcessor.a(iOException);
        }
    }

    private void prepareSecurityManagerForSecondaryNode(@NotNull TeamCityDatabaseManager teamCityDatabaseManager) {
        block7: {
            try {
                if (teamCityDatabaseManager == null) {
                    StartupProcessor.$$$reportNull$$$0(16);
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw StartupProcessor.a(illegalStateException);
            }
            try {
                if (!CurrentNodeInfo.isSecondaryNode() || this.myContext.isDatabaseExternal()) break block7;
            }
            catch (IllegalStateException illegalStateException) {
                throw StartupProcessor.a(illegalStateException);
            }
            String string = HsqlPossibilityVerifier.getPathToFileForInProcessMode((String)teamCityDatabaseManager.getConnectionUrl().trim());
            if (string != null) {
                for (String string2 : HsqlPossibilityVerifier.getInternalDatabaseFiles((String)string)) {
                    SecondaryNodeSecurityManager.addWritablePath((File)new File(string2));
                }
            }
        }
    }

    private void performSetupDatabaseManager() {
        block33: {
            StartupStage startupStage;
            StartupContext startupContext;
            block29: {
                JdbcDrivers jdbcDrivers;
                block26: {
                    jdbcDrivers = this.myContext.getJdbcDrivers();
                    try {
                        try {
                            if ($assertionsDisabled || jdbcDrivers != null) break block26;
                        }
                        catch (IllegalStateException illegalStateException) {
                            throw StartupProcessor.a(illegalStateException);
                        }
                        throw new AssertionError();
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw StartupProcessor.a(illegalStateException);
                    }
                }
                File file = this.myContext.getServerPaths().getDataDirectory();
                String string = "IP: " + this.myContext.getIPAddress();
                if (this.myContext.getPort() != null) {
                    string = string + ", Port: " + this.myContext.getPort();
                }
                if (!StringUtil.isEmptyOrSpaces((String)System.getProperty("catalina.base"))) {
                    string = string + ", Installation Directory: " + String.valueOf(FileUtil.getCanonicalFile((File)new File(System.getProperty("catalina.base"))));
                } else if (!StringUtil.isEmptyOrSpaces((String)this.myContext.getServletRealPath())) {
                    string = string + ", Installation Directory: " + this.myContext.getServletRealPath();
                }
                string = string + ", Data Directory: " + String.valueOf(file);
                if (this.myContext.isDatabaseSettingsFromUISpecified()) {
                    JdbcConnectionString jdbcConnectionString;
                    block27: {
                        jdbcConnectionString = this.myContext.getConnectionString();
                        try {
                            try {
                                if ($assertionsDisabled || jdbcConnectionString.getDBType() != DatabaseType.UNKNOWN) break block27;
                            }
                            catch (IllegalStateException illegalStateException) {
                                throw StartupProcessor.a(illegalStateException);
                            }
                            throw new AssertionError((Object)"Database must be specified");
                        }
                        catch (IllegalStateException illegalStateException) {
                            throw StartupProcessor.a(illegalStateException);
                        }
                    }
                    File file2 = new File(file, "system");
                    TeamCityDatabaseManager teamCityDatabaseManager = new TeamCityDatabaseManager(jdbcDrivers, this.myContext.getIPAddress(), string, this.myContext.getProductVersion(), jdbcConnectionString, file2, (DBAction)new /* Unavailable Anonymous Inner Class!! */);
                    this.myContext.setDatabaseManager(teamCityDatabaseManager);
                } else {
                    DatabasePropertiesFile databasePropertiesFile;
                    block32: {
                        block31: {
                            block30: {
                                block28: {
                                    try {
                                        try {
                                            if (this.myContext.isDatabaseExplicitlyConfigured() || this.myContext.isInternalDatabaseFileExists()) break block28;
                                        }
                                        catch (IllegalStateException illegalStateException) {
                                            throw StartupProcessor.a(illegalStateException);
                                        }
                                        if (!this.myContext.isBypassingMaintenanceQuestions()) break block29;
                                    }
                                    catch (IllegalStateException illegalStateException) {
                                        throw StartupProcessor.a(illegalStateException);
                                    }
                                }
                                databasePropertiesFile = this.myContext.getDatabasePropertiesFile();
                                try {
                                    try {
                                        if ($assertionsDisabled || databasePropertiesFile != null) break block30;
                                    }
                                    catch (IllegalStateException illegalStateException) {
                                        throw StartupProcessor.a(illegalStateException);
                                    }
                                    throw new AssertionError();
                                }
                                catch (IllegalStateException illegalStateException) {
                                    throw StartupProcessor.a(illegalStateException);
                                }
                            }
                            try {
                                if (!databasePropertiesFile.isFileExists()) break block31;
                                this.myLogger.debug("Setting up database manager using the existent 'database.properties' file");
                                break block32;
                            }
                            catch (IllegalStateException illegalStateException) {
                                throw StartupProcessor.a(illegalStateException);
                            }
                        }
                        this.myLogger.debug("Setting up database manager for the existent internal database (the 'database.properties' file not found)");
                    }
                    DatabaseSettings databaseSettings = DatabaseSettings.create((DatabasePropertiesFile)databasePropertiesFile, (boolean)true);
                    this.myContext.setConnectionString(databaseSettings.getJdbcConnectionString());
                    TeamCityDatabaseManager teamCityDatabaseManager = new TeamCityDatabaseManager(jdbcDrivers, this.myContext.getIPAddress(), string, this.myContext.getProductVersion(), databasePropertiesFile, databaseSettings, (DBAction)new /* Unavailable Anonymous Inner Class!! */);
                    this.myContext.setDatabaseManager(teamCityDatabaseManager);
                }
                break block33;
            }
            try {
                startupContext = this.myContext;
                startupStage = this.myContext.getDataDirManager().containsConfiguredTeamcity() ? StartupStage.DB_SETTINGS_SCREEN : StartupStage.FIRST_START_SCREEN;
            }
            catch (IllegalStateException illegalStateException) {
                throw StartupProcessor.a(illegalStateException);
            }
            startupContext.proposeNextStage(startupStage, new StartupStage[0]);
        }
    }

    /*
     * Loose catch block
     */
    private void performConnectToDatabaseInternal() throws SecondTeamCityInstanceError, StartupDatabaseAccessingError, DatabaseIsNotReachableException {
        TeamCityDatabaseManager teamCityDatabaseManager;
        block15: {
            this.myLogger.debug("Attempt to connect to the database");
            teamCityDatabaseManager = this.myContext.getDatabaseManager();
            if ($assertionsDisabled) break block15;
            try {
                block16: {
                    if (teamCityDatabaseManager != null) break block15;
                    break block16;
                    catch (DatabaseDriverMissingException databaseDriverMissingException) {
                        throw StartupProcessor.a(databaseDriverMissingException);
                    }
                }
                throw new AssertionError();
            }
            catch (DatabaseDriverMissingException databaseDriverMissingException) {
                throw StartupProcessor.a(databaseDriverMissingException);
            }
        }
        this.prepareSecurityManagerForSecondaryNode(teamCityDatabaseManager);
        try {
            teamCityDatabaseManager.connect(true);
        }
        catch (DatabaseDriverMissingException databaseDriverMissingException) {
            throw new StartupDatabaseAccessingError(databaseDriverMissingException.getMessage(), (Throwable)databaseDriverMissingException);
        }
        catch (DatabaseIsNotReachableException databaseIsNotReachableException) {
            throw databaseIsNotReachableException;
        }
        catch (CouldNotConnectToDatabaseException couldNotConnectToDatabaseException) {
            throw new StartupDatabaseAccessingError(couldNotConnectToDatabaseException.getMessage(), (Throwable)couldNotConnectToDatabaseException);
        }
        catch (DatabaseAccessDeniedException databaseAccessDeniedException) {
            throw new StartupDatabaseAccessingError(databaseAccessDeniedException.getMessage(), (Throwable)databaseAccessDeniedException);
        }
        catch (SecondTeamCityInstanceException secondTeamCityInstanceException) {
            throw new SecondTeamCityInstanceError(secondTeamCityInstanceException);
        }
        catch (JdbcDriverIsTooOldException jdbcDriverIsTooOldException) {
            throw new StartupDatabaseAccessingError((Throwable)jdbcDriverIsTooOldException);
        }
        catch (DBException dBException) {
            try {
                if (this.myLogger.isDebugEnabled()) {
                    this.myLogger.error("Unexpected error during connecting to the database: " + dBException.getMessage(), (Throwable)dBException);
                }
            }
            catch (DatabaseDriverMissingException databaseDriverMissingException) {
                throw StartupProcessor.a(databaseDriverMissingException);
            }
            throw dBException;
        }
        this.myLogger.info("JDBC driver version: " + String.valueOf(teamCityDatabaseManager.getDriverVersion()) + " (" + teamCityDatabaseManager.getDriverName() + ")");
        this.myLogger.info("Database system version: " + String.valueOf(teamCityDatabaseManager.getDatabaseProductVersion()) + " (" + teamCityDatabaseManager.getDatabaseProductName() + ")");
        this.myLogger.info("Connected to the database successfully");
    }

    private void doDatabaseChecking() throws ServerStartupError {
        boolean bl;
        StartupContext startupContext;
        int n;
        int n2;
        block70: {
            block69: {
                boolean bl2;
                int n3;
                block63: {
                    block65: {
                        TeamCityDatabaseManager teamCityDatabaseManager;
                        String string;
                        block68: {
                            String string2;
                            block67: {
                                block66: {
                                    block60: {
                                        StartupStage startupStage;
                                        StartupProcessor startupProcessor;
                                        boolean bl3;
                                        block62: {
                                            block61: {
                                                boolean bl4;
                                                this.analyseDatabase();
                                                n2 = this.myContext.getSoftwareVersion();
                                                n3 = this.myContext.getDataDirVersion();
                                                n = this.myContext.getDatabaseVersion();
                                                try {
                                                    bl4 = n3 == n;
                                                }
                                                catch (ServerStartupError serverStartupError) {
                                                    throw StartupProcessor.a(serverStartupError);
                                                }
                                                bl2 = bl4;
                                                try {
                                                    try {
                                                        try {
                                                            try {
                                                                if (n3 != 0 || n != 0) break block60;
                                                            }
                                                            catch (ServerStartupError serverStartupError) {
                                                                throw StartupProcessor.a(serverStartupError);
                                                            }
                                                            if (!this.myContext.isDatabaseSettingsFromUISpecified()) break block61;
                                                        }
                                                        catch (ServerStartupError serverStartupError) {
                                                            throw StartupProcessor.a(serverStartupError);
                                                        }
                                                        if (!this.myContext.isDatabaseAccessible()) break block61;
                                                    }
                                                    catch (ServerStartupError serverStartupError) {
                                                        throw StartupProcessor.a(serverStartupError);
                                                    }
                                                    bl3 = true;
                                                    break block62;
                                                }
                                                catch (ServerStartupError serverStartupError) {
                                                    throw StartupProcessor.a(serverStartupError);
                                                }
                                            }
                                            bl3 = false;
                                        }
                                        boolean bl5 = bl3;
                                        try {
                                            startupProcessor = this;
                                            startupStage = bl5 ? StartupStage.CREATE_NEW_DB : StartupStage.DB_SETTINGS_SCREEN;
                                        }
                                        catch (ServerStartupError serverStartupError) {
                                            throw StartupProcessor.a(serverStartupError);
                                        }
                                        startupProcessor.proposeNextStage(startupStage, new StartupStage[0]);
                                        return;
                                    }
                                    try {
                                        try {
                                            try {
                                                block64: {
                                                    try {
                                                        try {
                                                            try {
                                                                if (bl2 || !this.myContext.isDatabaseSettingsFromUISpecified()) break block63;
                                                            }
                                                            catch (ServerStartupError serverStartupError) {
                                                                throw StartupProcessor.a(serverStartupError);
                                                            }
                                                            if (this.myContext.isFirstStart()) break block64;
                                                        }
                                                        catch (ServerStartupError serverStartupError) {
                                                            throw StartupProcessor.a(serverStartupError);
                                                        }
                                                        if (n3 != 0) break block65;
                                                    }
                                                    catch (ServerStartupError serverStartupError) {
                                                        throw StartupProcessor.a(serverStartupError);
                                                    }
                                                }
                                                if (this.myContext.getDatabaseTables().isEmpty()) break block65;
                                            }
                                            catch (ServerStartupError serverStartupError) {
                                                throw StartupProcessor.a(serverStartupError);
                                            }
                                            if (n != 0) break block66;
                                        }
                                        catch (ServerStartupError serverStartupError) {
                                            throw StartupProcessor.a(serverStartupError);
                                        }
                                        string2 = "The specified database contains tables of another application. TeamCity server requires an empty database.";
                                        break block67;
                                    }
                                    catch (ServerStartupError serverStartupError) {
                                        throw StartupProcessor.a(serverStartupError);
                                    }
                                }
                                string2 = "The specified database contains data from another TeamCity installation. New TeamCity server requires a new empty database.";
                            }
                            string = string2;
                            teamCityDatabaseManager = this.myContext.getDatabaseManager();
                            try {
                                try {
                                    if ($assertionsDisabled || teamCityDatabaseManager != null) break block68;
                                }
                                catch (ServerStartupError serverStartupError) {
                                    throw StartupProcessor.a(serverStartupError);
                                }
                                throw new AssertionError();
                            }
                            catch (ServerStartupError serverStartupError) {
                                throw StartupProcessor.a(serverStartupError);
                            }
                        }
                        if (teamCityDatabaseManager.getDatabaseType() == DatabaseType.ORACLE) {
                            string = string.replace("database", "database schema");
                        }
                        throw new DatabaseIsNotEmptyError(string);
                    }
                    throw new DatabaseVersionMismatchError("The version of the specified database is not the same as the version of the Data Directory. Probably a database from another TeamCity installation is specified.", n2, n3, n);
                }
                try {
                    if (n3 == 0) {
                        throw new DatabaseVersionMismatchError("Could not determine configuration version.", n2, n3, n);
                    }
                }
                catch (ServerStartupError serverStartupError) {
                    throw StartupProcessor.a(serverStartupError);
                }
                try {
                    if (this.myContext.getCountOfTables() == 0) {
                        this.handleEmptyDatabase(n2, n3, true);
                        return;
                    }
                }
                catch (ServerStartupError serverStartupError) {
                    throw StartupProcessor.a(serverStartupError);
                }
                try {
                    if (!this.myContext.isSchemaBelongsToTeamcity()) {
                        this.reportNonTeamcityDatabase();
                        return;
                    }
                }
                catch (ServerStartupError serverStartupError) {
                    throw StartupProcessor.a(serverStartupError);
                }
                try {
                    if (n == 0) {
                        throw new DatabaseVersionMismatchError("Could not determine database version.", n2, n3, n);
                    }
                }
                catch (ServerStartupError serverStartupError) {
                    throw StartupProcessor.a(serverStartupError);
                }
                try {
                    if (n3 < 73) {
                        throw new DatabaseVersionMismatchError("The data directory belongs to a very old version of TeamCity. Upgrade to the current version could not be done.", n2, n3, n);
                    }
                }
                catch (ServerStartupError serverStartupError) {
                    throw StartupProcessor.a(serverStartupError);
                }
                try {
                    if (this.myContext.isIncompatibleDatabaseVersion()) {
                        this.doDowngradeRequired(n2, n3, n);
                        return;
                    }
                }
                catch (ServerStartupError serverStartupError) {
                    throw StartupProcessor.a(serverStartupError);
                }
                try {
                    if (!bl2) {
                        throw new DatabaseVersionMismatchError(String.format("Version mismatch: Data format of the data directory (%d) and the database (%d) do not match.\nPlease ensure that the data directory and the database in use were upgraded/restored consistently and were always used together.", n3, n), n2, n3, n);
                    }
                }
                catch (ServerStartupError serverStartupError) {
                    throw StartupProcessor.a(serverStartupError);
                }
                try {
                    try {
                        startupContext = this.myContext;
                        if (n < 390 || n > n2) break block69;
                    }
                    catch (ServerStartupError serverStartupError) {
                        throw StartupProcessor.a(serverStartupError);
                    }
                    bl = true;
                    break block70;
                }
                catch (ServerStartupError serverStartupError) {
                    throw StartupProcessor.a(serverStartupError);
                }
            }
            bl = false;
        }
        try {
            startupContext.setBackupPossible(bl);
            this.initializeLicenseAgreement();
            if (this.checkLicenseAgreementRequired()) {
                return;
            }
        }
        catch (ServerStartupError serverStartupError) {
            throw StartupProcessor.a(serverStartupError);
        }
        try {
            if (this.checkUpgradeRequired(n2, n)) {
                return;
            }
        }
        catch (ServerStartupError serverStartupError) {
            throw StartupProcessor.a(serverStartupError);
        }
        this.checkTablesAndStartApplication();
    }

    private void initializeLicenseAgreement() {
        TeamCityDatabaseManager teamCityDatabaseManager;
        block4: {
            teamCityDatabaseManager = this.myContext.getDatabaseManager();
            try {
                try {
                    if ($assertionsDisabled || teamCityDatabaseManager != null) break block4;
                }
                catch (IllegalStateException illegalStateException) {
                    throw StartupProcessor.a(illegalStateException);
                }
                throw new AssertionError();
            }
            catch (IllegalStateException illegalStateException) {
                throw StartupProcessor.a(illegalStateException);
            }
        }
        this.myContext.setLicenseAgreement((LicenseAgreement)new LicenseAgreementImpl((DBFunctionsProvider)teamCityDatabaseManager));
    }

    private void checkTablesAndStartApplication() throws ServerStartupError {
        try {
            if (!this.tryLockDatabase()) {
                return;
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupProcessor.a(illegalStateException);
        }
        catch (SecondTeamCityInstanceError secondTeamCityInstanceError) {
            this.proposeNextStage(StartupStage.SECOND_INSTANCE, new StartupStage[0]);
            return;
        }
        this.performDatabaseTablesCheck();
        this.proposeNextStage(StartupStage.APPLICATION_STARTING, new StartupStage[0]);
    }

    private boolean checkLicenseAgreementRequired() {
        boolean bl;
        LicenseAgreement licenseAgreement;
        block20: {
            block19: {
                try {
                    if (!TeamCityProperties.getBooleanOrTrue((String)"teamcity.internal.licenseAgreementV2.enabled")) {
                        return false;
                    }
                }
                catch (IllegalStateException illegalStateException) {
                    throw StartupProcessor.a(illegalStateException);
                }
                try {
                    if (CurrentNodeInfo.isSecondaryNode()) {
                        return false;
                    }
                }
                catch (IllegalStateException illegalStateException) {
                    throw StartupProcessor.a(illegalStateException);
                }
                TeamCityDatabaseManager teamCityDatabaseManager = this.myContext.getDatabaseManager();
                try {
                    try {
                        if ($assertionsDisabled || teamCityDatabaseManager != null) break block19;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw StartupProcessor.a(illegalStateException);
                    }
                    throw new AssertionError();
                }
                catch (IllegalStateException illegalStateException) {
                    throw StartupProcessor.a(illegalStateException);
                }
            }
            licenseAgreement = this.myContext.getLicenseAgreement();
            try {
                try {
                    if ($assertionsDisabled || licenseAgreement != null) break block20;
                }
                catch (IllegalStateException illegalStateException) {
                    throw StartupProcessor.a(illegalStateException);
                }
                throw new AssertionError();
            }
            catch (IllegalStateException illegalStateException) {
                throw StartupProcessor.a(illegalStateException);
            }
        }
        try {
            bl = !licenseAgreement.isAccepted();
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupProcessor.a(illegalStateException);
        }
        boolean bl2 = bl;
        try {
            if (bl2) {
                this.proposeNextStage(StartupStage.LICENSE_AGREEMENT_SCREEN, new StartupStage[0]);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupProcessor.a(illegalStateException);
        }
        return bl2;
    }

    protected boolean checkUpgradeRequired(int n, int n2) throws ServerStartupError {
        block9: {
            String string;
            Logger logger;
            block11: {
                block10: {
                    block8: {
                        try {
                            try {
                                if (!CurrentNodeInfo.isSecondaryNode() || !TeamCityProperties.getBoolean((String)"teamcity.readOnlyNode.ignoreUpgradeCheck")) break block8;
                            }
                            catch (ServerStartupError serverStartupError) {
                                throw StartupProcessor.a(serverStartupError);
                            }
                            return false;
                        }
                        catch (ServerStartupError serverStartupError) {
                            throw StartupProcessor.a(serverStartupError);
                        }
                    }
                    try {
                        try {
                            if (n2 >= n) break block9;
                            this.myLogger.info("Upgrade from version " + n2 + " to version " + n + " is required");
                            logger = this.myLogger;
                            if (!this.myContext.isBackupPossible()) break block10;
                        }
                        catch (ServerStartupError serverStartupError) {
                            throw StartupProcessor.a(serverStartupError);
                        }
                        string = "";
                        break block11;
                    }
                    catch (ServerStartupError serverStartupError) {
                        throw StartupProcessor.a(serverStartupError);
                    }
                }
                string = "not ";
            }
            logger.info("Backup of this version is " + string + "possible");
            this.doUpgradeRequired(this.myUpgradePerformer.requiresExclusiveLock(n2, n));
            return true;
        }
        return false;
    }

    private void doDowngradeRequired(int n, int n2, int n3) throws DatabaseVersionMismatchError {
        try {
            if (CurrentNodeInfo.isSecondaryNode()) {
                this.proposeNextStage(StartupStage.OUTDATED_SECONDARY_NODE, new StartupStage[0]);
                return;
            }
        }
        catch (DatabaseVersionMismatchError databaseVersionMismatchError) {
            throw StartupProcessor.a(databaseVersionMismatchError);
        }
        throw new DatabaseVersionMismatchError("TeamCity configuration format version corresponds to a more recent TeamCity version: " + n2 + " while the version required by the current software is " + n + ".\nTeamCity cannot start as data downgrade is not supported. Please start a TeamCity version that matches the data version or restore from a backup matching the current TeamCity version.", n, n2, n3);
    }

    private boolean tryLockDatabase() throws ServerStartupError {
        try {
            this.performDatabaseLockIfNeeded();
            return true;
        }
        catch (Heartbeat.OtherNodeWithDifferentVersionError otherNodeWithDifferentVersionError) {
            this.myLogger.info("The database is currently used by " + otherNodeWithDifferentVersionError.getAnotherSessionRecord().getAppType().describe() + " of a different version: '" + otherNodeWithDifferentVersionError.getAnotherSessionRecord().displayVersion + "'. Please shutdown the " + otherNodeWithDifferentVersionError.getAnotherSessionRecord().getAppType().describe() + " to continue");
            this.myContext.setCompetitorSession((SessionInfo)otherNodeWithDifferentVersionError.getAnotherSessionRecord());
            this.proposeNextStage(StartupStage.WAITING_FOR_OTHER_NODE_SHUTDOWN, new StartupStage[0]);
            return false;
        }
        catch (Heartbeat.MainServerWithDifferentVersionError mainServerWithDifferentVersionError) {
            this.myLogger.info("The database is currently used by " + mainServerWithDifferentVersionError.getMainServerSession().getAppType().describe() + " of a different version: '" + mainServerWithDifferentVersionError.getMainServerSession().getDisplayVersion() + "'.");
            this.myContext.setCompetitorSession(mainServerWithDifferentVersionError.getMainServerSession());
            this.proposeNextStage(StartupStage.MAIN_SERVER_OF_A_DIFFERENT_VERSION_ERROR, new StartupStage[0]);
            return false;
        }
        catch (Heartbeat.OtherNodeUsesDifferentDataDir otherNodeUsesDifferentDataDir) {
            block8: {
                block7: {
                    try {
                        this.myLogger.info("The database is currently used by " + otherNodeUsesDifferentDataDir.getOtherNode().getAppType().describe() + " working with the different Data Directory");
                        this.myContext.setCompetitorSession(otherNodeUsesDifferentDataDir.getOtherNode());
                        if (otherNodeUsesDifferentDataDir.getOtherNode().getAppType() != Heartbeat.AppType.MAIN_SERVER) break block7;
                        this.proposeNextStage(StartupStage.MAIN_SERVER_WITH_DIFFERENT_DATA_DIRECTORY, new StartupStage[0]);
                        break block8;
                    }
                    catch (Heartbeat.OtherNodeWithDifferentVersionError otherNodeWithDifferentVersionError) {
                        throw StartupProcessor.a(otherNodeWithDifferentVersionError);
                    }
                }
                this.proposeNextStage(StartupStage.OTHER_NODE_WITH_DIFFERENT_DATA_DIRECTORY, new StartupStage[0]);
            }
            return false;
        }
        catch (Heartbeat.OtherNodeWithSameIdIsActive otherNodeWithSameIdIsActive) {
            this.myLogger.info("The database is currently used by " + otherNodeWithSameIdIsActive.getOtherNode().getAppType().describe() + " with the same id: " + otherNodeWithSameIdIsActive.getOtherNode().getId());
            this.myContext.setCompetitorSession((SessionInfo)otherNodeWithSameIdIsActive.getOtherNode());
            this.proposeNextStage(StartupStage.OTHER_NODE_WITH_SAME_ID_IS_ACTIVE, new StartupStage[0]);
            return false;
        }
    }

    private void doUpgradeRequired(boolean bl) throws ServerStartupError {
        block12: {
            block11: {
                try {
                    if (CurrentNodeInfo.isSecondaryNode()) {
                        this.myLogger.info("Upgrade procedure is not available on the " + CurrentNodeInfo.getNodeId());
                        this.proposeNextStage(StartupStage.WAITING_FOR_UPGRADE_END, new StartupStage[0]);
                        return;
                    }
                }
                catch (ServerStartupError serverStartupError) {
                    throw StartupProcessor.a(serverStartupError);
                }
                try {
                    if (!this.tryLockDatabase()) {
                        return;
                    }
                }
                catch (ServerStartupError serverStartupError) {
                    throw StartupProcessor.a(serverStartupError);
                }
                try {
                    if (!bl) {
                        this.proposeNextStage(StartupStage.UPGRADE_SCREEN, new StartupStage[0]);
                        return;
                    }
                }
                catch (ServerStartupError serverStartupError) {
                    throw StartupProcessor.a(serverStartupError);
                }
                try {
                    if (!this.lockDatabaseExclusively()) break block11;
                    this.proposeNextStage(StartupStage.UPGRADE_SCREEN, new StartupStage[0]);
                    break block12;
                }
                catch (ServerStartupError serverStartupError) {
                    throw StartupProcessor.a(serverStartupError);
                }
            }
            this.myLogger.info("Exclusive database lock can't be taken, will continue attempts");
            this.proposeNextStage(StartupStage.WAITING_FOR_UPGRADE_LOCK, new StartupStage[0]);
        }
    }

    private void doWaitForUpgradeLock() {
        try {
            while (true) {
                try {
                    try {
                        if (this.lockDatabaseExclusively() || Thread.currentThread().isInterrupted()) break;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw StartupProcessor.a(illegalStateException);
                    }
                    if (this.myContext.isShutdownRequested()) break;
                }
                catch (IllegalStateException illegalStateException) {
                    throw StartupProcessor.a(illegalStateException);
                }
                ThreadUtil.sleep((long)300L);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupProcessor.a(illegalStateException);
        }
        this.proposeNextStage(StartupStage.UPGRADE_SCREEN, new StartupStage[0]);
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void doWaitForOtherNodeShutdown() throws ServerStartupError {
        while (true) {
            try {
                if (Thread.currentThread().isInterrupted()) ** GOTO lbl8
                ** GOTO lbl12
            }
            catch (Heartbeat.OtherNodeWithDifferentVersionError v0) {
                try {
                    throw StartupProcessor.a(v0);
lbl8:
                    // 3 sources

                    while (true) {
                        this.doDatabaseChecking();
                        return;
                    }
lbl12:
                    // 2 sources

                    if (this.myContext.isShutdownRequested()) ** GOTO lbl8
                }
                catch (ServerStartupError v1) {
                    throw StartupProcessor.a(v1);
                }
                {
                    try {}
                    catch (Heartbeat.OtherNodeWithDifferentVersionError var1_1) {
                        ThreadUtil.sleep((long)300L);
                        continue;
                    }
                }
            }
            break;
        }
        this.performDatabaseLockIfNeeded();
        ** while (true)
    }

    private void handleEmptyDatabase(int n, int n2, boolean bl) throws ServerStartupError {
        block9: {
            block8: {
                block6: {
                    block7: {
                        try {
                            try {
                                if (this.myContext.isStartingAsMain()) break block6;
                                if (!bl) break block7;
                            }
                            catch (ServerStartupError serverStartupError) {
                                throw StartupProcessor.a(serverStartupError);
                            }
                            throw new KnownNonRecoverableStartupError("TeamCity database is empty. Please start main TeamCity Server to initialize the database first.");
                        }
                        catch (ServerStartupError serverStartupError) {
                            throw StartupProcessor.a(serverStartupError);
                        }
                    }
                    throw new KnownNonRecoverableStartupError("TeamCity database configuration cannot be found. Please ensure you pass database parameters either via database.properties file or by environment variables.");
                }
                try {
                    if (n != n2) break block8;
                    this.proposeNextStage(StartupStage.RECREATE_DB_SCREEN, new StartupStage[0]);
                    break block9;
                }
                catch (ServerStartupError serverStartupError) {
                    throw StartupProcessor.a(serverStartupError);
                }
            }
            throw new DataPartsInconsistentError("Data parts are inconsistent: the Data Directory exists (from another version of TeamCity) but the database does not.");
        }
    }

    @NotNull
    private KnownNonRecoverableStartupError notFoundDataDirException() {
        return new KnownNonRecoverableStartupError("TeamCity Data Directory can't be created by secondary TeamCity nodes. Please restart the node using an existing Data Directory.");
    }

    private void lookForTablesInOtherSchemas(@NotNull DBFunctions dBFunctions) {
        String string;
        Collection collection;
        block8: {
            try {
                if (dBFunctions == null) {
                    StartupProcessor.$$$reportNull$$$0(17);
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw StartupProcessor.a(illegalStateException);
            }
            collection = dBFunctions.retrieveSchemasWithTables();
            string = dBFunctions.getSchemaNameForMetadata();
            try {
                try {
                    if (string != null && string.length() != 0) break block8;
                }
                catch (IllegalStateException illegalStateException) {
                    throw StartupProcessor.a(illegalStateException);
                }
                return;
            }
            catch (IllegalStateException illegalStateException) {
                throw StartupProcessor.a(illegalStateException);
            }
        }
        collection.remove(string);
        if (!collection.isEmpty()) {
            String string2 = StringUtil.join((Collection)collection, (String)", ");
            String string3 = "Note: there are tables in other schemas found.\nSchemas (where tables were found): " + string2 + ".";
            this.myLogger.info(string3);
        }
    }

    /*
     * Loose catch block
     */
    private void performDatabaseLockIfNeeded() throws ServerStartupError {
        block20: {
            TeamCityDatabaseManager teamCityDatabaseManager;
            block17: {
                teamCityDatabaseManager = this.myContext.getDatabaseManager();
                if ($assertionsDisabled) break block17;
                try {
                    block21: {
                        if (teamCityDatabaseManager != null) break block17;
                        break block21;
                        catch (SecondTeamCityInstanceException secondTeamCityInstanceException) {
                            throw StartupProcessor.a(secondTeamCityInstanceException);
                        }
                    }
                    throw new AssertionError((Object)"The Database Manager must be created.");
                }
                catch (SecondTeamCityInstanceException secondTeamCityInstanceException) {
                    throw StartupProcessor.a(secondTeamCityInstanceException);
                }
            }
            try {
                if (teamCityDatabaseManager.isHeartbeatActive()) {
                    return;
                }
            }
            catch (SecondTeamCityInstanceException secondTeamCityInstanceException) {
                throw StartupProcessor.a(secondTeamCityInstanceException);
            }
            try {
                block18: {
                    block19: {
                        Heartbeat.AppType appType;
                        File file = Heartbeat.getStartingCodeFileDir((ServerPaths)this.myContext.getServerPaths());
                        try {
                            SecondaryNodeSecurityManager.addWritablePath((File)file);
                            appType = this.myContext.isStartingAsMain() ? Heartbeat.AppType.MAIN_SERVER : Heartbeat.AppType.SECONDARY_NODE;
                        }
                        catch (SecondTeamCityInstanceException secondTeamCityInstanceException) {
                            throw StartupProcessor.a(secondTeamCityInstanceException);
                        }
                        Heartbeat.AppType appType2 = appType;
                        boolean bl = teamCityDatabaseManager.activateHeartbeat(CurrentNodeInfo.getNodeId(), CurrentNodeInfo.getSupportedResponsibilities(), CommandLineNodeConfig.isEnabled(), this.myContext.getServerPaths(), appType2, this.myContext.getMaintenanceAuthenticationToken(), this.myContext.getUUID(), this.myContext.getServerStartupTimestamp());
                        if (!bl) break block18;
                        try {
                            block22: {
                                if (this.myContext.getOtherNodes().isEmpty()) break block19;
                                break block22;
                                catch (SecondTeamCityInstanceException secondTeamCityInstanceException) {
                                    throw StartupProcessor.a(secondTeamCityInstanceException);
                                }
                            }
                            this.myLogger.info("The database lock is taken, other nodes connected: " + String.valueOf(this.myContext.getOtherNodes()));
                            break block18;
                        }
                        catch (SecondTeamCityInstanceException secondTeamCityInstanceException) {
                            throw StartupProcessor.a(secondTeamCityInstanceException);
                        }
                    }
                    this.myLogger.info("The database lock is taken, no other nodes connected");
                }
                Heartbeat heartbeat = teamCityDatabaseManager.getHeartbeat();
                try {
                    if (!CurrentNodeInfo.isSecondaryNode() || heartbeat == null) break block20;
                }
                catch (SecondTeamCityInstanceException secondTeamCityInstanceException) {
                    throw StartupProcessor.a(secondTeamCityInstanceException);
                }
                Supplier<Set> supplier = () -> heartbeat.getEffectiveResponsibilities();
                SecondaryNodeDBAccess.init(supplier);
                SecondaryNodeSecurityManager.init(supplier);
            }
            catch (SecondTeamCityInstanceException secondTeamCityInstanceException) {
                this.myLogger.info("The database lock can't be taken, database is used by another server: " + secondTeamCityInstanceException.getAdditionalInfo());
                throw new SecondTeamCityInstanceError(secondTeamCityInstanceException);
            }
        }
    }

    private boolean lockDatabaseExclusively() {
        TeamCityDatabaseManager teamCityDatabaseManager;
        block7: {
            teamCityDatabaseManager = this.myContext.getDatabaseManager();
            try {
                try {
                    if ($assertionsDisabled || teamCityDatabaseManager != null) break block7;
                }
                catch (IllegalStateException illegalStateException) {
                    throw StartupProcessor.a(illegalStateException);
                }
                throw new AssertionError();
            }
            catch (IllegalStateException illegalStateException) {
                throw StartupProcessor.a(illegalStateException);
            }
        }
        boolean bl = teamCityDatabaseManager.tryGetExclusiveLock();
        try {
            if (bl) {
                this.myLogger.info("Exclusive database lock was taken");
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupProcessor.a(illegalStateException);
        }
        return bl;
    }

    private void releaseExclusiveLock() {
        TeamCityDatabaseManager teamCityDatabaseManager;
        block9: {
            block8: {
                teamCityDatabaseManager = this.myContext.getDatabaseManager();
                try {
                    try {
                        if ($assertionsDisabled || teamCityDatabaseManager != null) break block8;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw StartupProcessor.a(illegalStateException);
                    }
                    throw new AssertionError();
                }
                catch (IllegalStateException illegalStateException) {
                    throw StartupProcessor.a(illegalStateException);
                }
            }
            Heartbeat heartbeat = teamCityDatabaseManager.getHeartbeat();
            try {
                try {
                    if ($assertionsDisabled || heartbeat != null) break block9;
                }
                catch (IllegalStateException illegalStateException) {
                    throw StartupProcessor.a(illegalStateException);
                }
                throw new AssertionError();
            }
            catch (IllegalStateException illegalStateException) {
                throw StartupProcessor.a(illegalStateException);
            }
        }
        teamCityDatabaseManager.getHeartbeat().releaseExclusiveLock();
        this.myLogger.info("Exclusive database lock was released");
    }

    private void performDatabaseTablesCheck() throws ServerStartupError {
        TeamCityDatabaseManager teamCityDatabaseManager;
        block10: {
            teamCityDatabaseManager = this.myContext.getDatabaseManager();
            try {
                try {
                    if ($assertionsDisabled || teamCityDatabaseManager != null) break block10;
                }
                catch (ServerStartupError serverStartupError) {
                    throw StartupProcessor.a(serverStartupError);
                }
                throw new AssertionError();
            }
            catch (ServerStartupError serverStartupError) {
                throw StartupProcessor.a(serverStartupError);
            }
        }
        ImmutableSortedSet immutableSortedSet = teamCityDatabaseManager.getExistingPermanentTables();
        try {
            if (immutableSortedSet == null) {
                throw new StartupDatabaseAccessingError("List fo tables is not retrieved. Looks like an unexpected problem encountered during starting up");
            }
        }
        catch (ServerStartupError serverStartupError) {
            throw StartupProcessor.a(serverStartupError);
        }
        try {
            if (immutableSortedSet.isEmpty()) {
                throw new StartupDatabaseAccessingError("Database is empty");
            }
        }
        catch (ServerStartupError serverStartupError) {
            throw StartupProcessor.a(serverStartupError);
        }
    }

    /*
     * Loose catch block
     */
    private void doBackup() throws ServerStartupError {
        block26: {
            ProgressStatus progressStatus;
            BackupProcess backupProcess;
            block27: {
                block25: {
                    TeamCityDatabaseManager teamCityDatabaseManager;
                    block23: {
                        try {
                            if (CurrentNodeInfo.isSecondaryNode()) {
                                throw new KnownNonRecoverableStartupError("Backup can't be run on the " + CurrentNodeInfo.getNodeId());
                            }
                        }
                        catch (MaintenanceProcessAlreadyRunningException maintenanceProcessAlreadyRunningException) {
                            throw StartupProcessor.a(maintenanceProcessAlreadyRunningException);
                        }
                        teamCityDatabaseManager = this.myContext.getDatabaseManager();
                        if ($assertionsDisabled) break block23;
                        try {
                            block28: {
                                if (teamCityDatabaseManager != null) break block23;
                                break block28;
                                catch (MaintenanceProcessAlreadyRunningException maintenanceProcessAlreadyRunningException) {
                                    throw StartupProcessor.a(maintenanceProcessAlreadyRunningException);
                                }
                            }
                            throw new AssertionError();
                        }
                        catch (MaintenanceProcessAlreadyRunningException maintenanceProcessAlreadyRunningException) {
                            throw StartupProcessor.a(maintenanceProcessAlreadyRunningException);
                        }
                    }
                    BigFileZipFactory bigFileZipFactory = BigFileZipFactory.newInstance((boolean)true, (boolean)false);
                    BackupSettingsPersistor backupSettingsPersistor = new BackupSettingsPersistor(this.myContext.getServerPaths());
                    try {
                        if (this.myBackupProcessManager == null) {
                            this.myBackupProcessManager = new BackupProcessManagerImpl(this.myContext.getServerPaths(), (DBFunctionsProvider)teamCityDatabaseManager, (ZipFactory)bigFileZipFactory, null, backupSettingsPersistor, null, null, true);
                        }
                    }
                    catch (MaintenanceProcessAlreadyRunningException maintenanceProcessAlreadyRunningException) {
                        throw StartupProcessor.a(maintenanceProcessAlreadyRunningException);
                    }
                    BackupConfig backupConfig = new BackupConfig();
                    backupConfig.setFileName("TeamCity_Before_Upgrade");
                    backupConfig.setIncludeDatabase(true);
                    backupConfig.setIncludeConfiguration(true);
                    backupConfig.setIncludeSupplementaryData(true);
                    backupConfig.setIncludeRunningBuilds(true);
                    this.myContext.setBackupConfig(backupConfig);
                    try {
                        backupProcess = this.myBackupProcessManager.startBackup(backupConfig);
                        this.myContext.setBackupProcess(backupProcess);
                    }
                    catch (MaintenanceProcessAlreadyRunningException maintenanceProcessAlreadyRunningException) {
                        throw new UnknownServerStartupError("Could not start backup: " + maintenanceProcessAlreadyRunningException.getMessage(), (Throwable)maintenanceProcessAlreadyRunningException);
                    }
                    while (true) {
                        block24: {
                            try {
                                while (!backupProcess.isFinished()) {
                                    ThreadUtil.sleep((long)1000L);
                                    if (!this.myContext.isShutdownRequested()) continue;
                                    break block24;
                                }
                                break;
                            }
                            catch (MaintenanceProcessAlreadyRunningException maintenanceProcessAlreadyRunningException) {
                                throw StartupProcessor.a(maintenanceProcessAlreadyRunningException);
                            }
                        }
                        if (backupProcess.getProgressStatus() != ProgressStatus.Running) continue;
                        this.myLogger.warn("Cancelling backup process because of shut down");
                        backupProcess.cancel();
                        ThreadUtil.sleep((long)100L);
                    }
                    BackupProcessInfo backupProcessInfo = backupProcess.getBriefInfo();
                    this.myContext.setBackupProcessInfo(backupProcessInfo);
                    progressStatus = backupProcessInfo.getStatus();
                    try {
                        if (!this.myContext.isShutdownRequested()) break block25;
                        break block26;
                    }
                    catch (MaintenanceProcessAlreadyRunningException maintenanceProcessAlreadyRunningException) {
                        throw StartupProcessor.a(maintenanceProcessAlreadyRunningException);
                    }
                }
                try {
                    if (progressStatus != ProgressStatus.Finished) break block27;
                    this.proposeNextStage(StartupStage.UPGRADE, new StartupStage[0]);
                    break block26;
                }
                catch (MaintenanceProcessAlreadyRunningException maintenanceProcessAlreadyRunningException) {
                    throw StartupProcessor.a(maintenanceProcessAlreadyRunningException);
                }
            }
            if (backupProcess.hasExceptions()) {
                for (Exception exception : backupProcess.getExceptions()) {
                    this.myContext.addException((Throwable)exception);
                }
                this.proposeNextStage(StartupStage.EXCEPTION, new StartupStage[0]);
            } else {
                String string = "Backup process is unexpectedly interrupted in backup state " + String.valueOf(progressStatus);
                throw new UnknownServerStartupError(string);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     */
    private void doBackupChecking() {
        try {
            Path path;
            block20: {
                path = this.myContext.getBackupFilePath();
                if ($assertionsDisabled) break block20;
                try {
                    block23: {
                        if (path != null) break block20;
                        break block23;
                        catch (BackupArchiveHasNoVersionFileError backupArchiveHasNoVersionFileError) {
                            throw StartupProcessor.a(backupArchiveHasNoVersionFileError);
                        }
                    }
                    throw new AssertionError();
                }
                catch (BackupArchiveHasNoVersionFileError backupArchiveHasNoVersionFileError) {
                    throw StartupProcessor.a(backupArchiveHasNoVersionFileError);
                }
            }
            BigFileZipFactory bigFileZipFactory = BigFileZipFactory.newInstance((boolean)false, (boolean)false);
            try (ZipReader zipReader = bigFileZipFactory.openZipArchive(path.toAbsolutePath().toString());){
                File file;
                block21: {
                    try {
                        this.checkBackupMetadataVersion(zipReader);
                        this.checkBackupDataVersion(zipReader);
                        this.checkBackupContainsDatabaseAndConfiguration(zipReader);
                        this.checkBackupDataVersion(zipReader);
                        this.checkFoldersAreEmpty(zipReader);
                        if (zipReader.doesFileExist("config/database.properties")) {
                            this.myContext.setConnectionStringFromBackup(this.parseConnectionString(zipReader.openBinaryFile("config/database.properties")));
                        }
                    }
                    catch (BackupArchiveHasNoVersionFileError backupArchiveHasNoVersionFileError) {
                        throw StartupProcessor.a(backupArchiveHasNoVersionFileError);
                    }
                    file = this.myContext.getDataDirManager().getJdbcDir();
                    if (file.mkdirs()) break block21;
                    try {
                        block24: {
                            if (file.isDirectory()) break block21;
                            break block24;
                            catch (BackupArchiveHasNoVersionFileError backupArchiveHasNoVersionFileError) {
                                throw StartupProcessor.a(backupArchiveHasNoVersionFileError);
                            }
                        }
                        throw new RuntimeException("Could not create JDBC directory " + String.valueOf(file));
                    }
                    catch (BackupArchiveHasNoVersionFileError backupArchiveHasNoVersionFileError) {
                        throw StartupProcessor.a(backupArchiveHasNoVersionFileError);
                    }
                }
                BackupUtil.extractJdbcDriversToDir((File)file, (ZipReader)zipReader, (String)"jdbc");
                this.setJdbcDriverMetadata();
            }
            this.proposeNextStage(StartupStage.RESTORE_DATABASE_SETTINGS_SCREEN, new StartupStage[0]);
        }
        catch (BackupArchiveHasNoVersionFileError backupArchiveHasNoVersionFileError) {
            try {
                if (backupArchiveHasNoVersionFileError.logMessage != null) {
                    this.myLogger.warn(String.format("Diagnostics info on backup chack failure: %s.", backupArchiveHasNoVersionFileError.logMessage));
                }
            }
            catch (BackupArchiveHasNoVersionFileError backupArchiveHasNoVersionFileError2) {
                throw StartupProcessor.a(backupArchiveHasNoVersionFileError2);
            }
            this.handleBackupCheckFailure(backupArchiveHasNoVersionFileError);
        }
        catch (Throwable throwable) {
            this.handleBackupCheckFailure(throwable);
        }
    }

    private void handleBackupCheckFailure(Throwable throwable) {
        this.myLogger.warn(String.format("Backup check failed with message: %s. Go back to '%s' stage.", throwable.getMessage(), StartupStage.RESTORE_SETTINGS_SCREEN.description));
        this.myContext.addException(throwable);
        this.proposeNextStage(StartupStage.RESTORE_SETTINGS_SCREEN, new StartupStage[0]);
    }

    private void checkFoldersAreEmpty(ZipReader zipReader) {
        this.checkConfigFolderIsEmpty();
        String string = this.myContext.getServerPaths().getSystemDir();
        try {
            if (zipReader.doesFileExist(Pattern.compile("^system/pluginData/.*"))) {
                this.checkFolderIsEmpty(string + "/pluginData", "supplementary data");
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupProcessor.a(illegalStateException);
        }
        try {
            if (zipReader.doesFileExist(Pattern.compile("^system/messages/.*"))) {
                this.checkFolderIsEmpty(string + "/messages", "build logs");
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupProcessor.a(illegalStateException);
        }
        try {
            if (zipReader.doesFileExist(Pattern.compile("^system/artifacts/.*"))) {
                this.checkFolderIsEmpty(string + "/artifacts", "build logs");
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupProcessor.a(illegalStateException);
        }
        try {
            if (zipReader.doesFileExist(Pattern.compile("^system/changes/.*"))) {
                this.checkFolderIsEmpty(string + "/changes", "personal changes");
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupProcessor.a(illegalStateException);
        }
    }

    private void checkFolderIsEmpty(@NotNull String string, @NotNull String string2) {
        try {
            if (string == null) {
                StartupProcessor.$$$reportNull$$$0(18);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupProcessor.a(illegalStateException);
        }
        try {
            if (string2 == null) {
                StartupProcessor.$$$reportNull$$$0(19);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupProcessor.a(illegalStateException);
        }
        try {
            if (!BackupUtil.checkFolderIsEnoughEmptyToRestoreFromBackup((File)new File(string), Collections.emptySet())) {
                throw new RuntimeException(String.format("The %s directory (%s) is not empty.", string2, string));
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupProcessor.a(illegalStateException);
        }
    }

    private void checkBackupDataVersion(ZipReader zipReader) {
        block6: {
            try {
                if (zipReader.doesFileExist("version.txt")) {
                    int n = BackupVersionFileSerializer.extractDataFormatVersion((ZipReader)zipReader);
                    int n2 = DBSchema.getCurrentVersion();
                    try {
                        if (n > n2) {
                            throw new UnsupportedVersionError("The backup archive is produced by the newer version of TeamCity.");
                        }
                        break block6;
                    }
                    catch (IOException iOException) {
                        throw StartupProcessor.a(iOException);
                    }
                }
                throw new UnsupportedVersionError("The backup archive is corrupted.");
            }
            catch (IOException iOException) {
                throw new RuntimeException(iOException.getMessage());
            }
        }
    }

    private void checkBackupMetadataVersion(ZipReader zipReader) {
        int n = RestoreProcessor.readBackupZipVersion((ZipReader)zipReader);
        try {
            if (n == 0) {
                throw new UnsupportedVersionError("The backup archive is corrupted.");
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupProcessor.a(illegalStateException);
        }
        try {
            if (n < 2) {
                throw new UnsupportedVersionError("The backup archive is too old. This version of maintainDB can restore backup archives version 6.0 or later.");
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupProcessor.a(illegalStateException);
        }
    }

    private void checkBackupContainsDatabaseAndConfiguration(ZipReader zipReader) {
        try {
            if (!zipReader.doesFileExist(Pattern.compile("^database_dump/.*"))) {
                throw new RuntimeException("Invalid backup file structure - database dump was not found.");
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupProcessor.a(illegalStateException);
        }
        try {
            if (!zipReader.doesFileExist("config/main-config.xml")) {
                throw new RuntimeException("Invalid backup file structure - config backup was not found.");
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupProcessor.a(illegalStateException);
        }
    }

    private void checkConfigFolderIsEmpty() {
        File file = this.myContext.getDataDirManager().getConfigDir();
        try {
            if (!BackupUtil.checkFolderIsEnoughEmptyToRestoreFromBackup((File)file, (Set)CollectionsUtil.setOf((Object[])new String[]{"database.properties"}))) {
                throw new RuntimeException(String.format("The configuration directory (%s) is not empty.", file));
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupProcessor.a(illegalStateException);
        }
    }

    private JdbcConnectionString parseConnectionString(InputStream inputStream) throws IOException {
        Properties properties = PropertiesUtil.loadProperties((InputStream)inputStream);
        DatabasePropertiesFile.removeExtraSpacesFromPropertiesValues((Properties)properties);
        String string = properties.getProperty("connectionUrl", "").trim();
        String string2 = properties.getProperty("connectionProperties.user", "").trim();
        String string3 = properties.getProperty("connectionProperties.password", "").trim();
        try {
            if (string.isEmpty()) {
                throw new RuntimeException("The file \"" + String.valueOf(inputStream) + "\" doesn't contain the connectionUrl property. Ignore settings from this file.");
            }
        }
        catch (IOException iOException) {
            throw StartupProcessor.a(iOException);
        }
        JdbcConnectionString jdbcConnectionString = new JdbcConnectionString(string);
        jdbcConnectionString.setUser(string2);
        jdbcConnectionString.setPassword(string3);
        return jdbcConnectionString;
    }

    /*
     * Loose catch block
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void doTargetDatabaseChecking() {
        this.myLogger.info("Try to connect to the target database");
        this.performConnectToDatabase();
        this.analyseDatabase();
        this.checkDatabaseIsEmpty();
        this.proposeNextStage(StartupStage.RESTORE, new StartupStage[0]);
        TeamCityDatabaseManager teamCityDatabaseManager = this.myContext.getDatabaseManager();
        try {
            if (teamCityDatabaseManager == null) return;
            this.myLogger.debug("Disconnecting from the Database");
            teamCityDatabaseManager.disconnect();
            this.myContext.setDatabaseManager(null);
            return;
        }
        catch (Throwable throwable) {
            throw StartupProcessor.a(throwable);
        }
        catch (Throwable throwable) {
            try {
                this.myLogger.warn(String.format("Target database settings check failed with message: %s. Go back to '%s' stage.", throwable.getMessage(), StartupStage.RESTORE_DATABASE_SETTINGS_SCREEN.description));
                this.myContext.addException(throwable);
                this.proposeNextStage(StartupStage.RESTORE_DATABASE_SETTINGS_SCREEN, new StartupStage[0]);
            }
            catch (Throwable throwable2) {
                TeamCityDatabaseManager teamCityDatabaseManager2 = this.myContext.getDatabaseManager();
                try {
                    if (teamCityDatabaseManager2 == null) throw throwable2;
                    this.myLogger.debug("Disconnecting from the Database");
                    teamCityDatabaseManager2.disconnect();
                    this.myContext.setDatabaseManager(null);
                    throw throwable2;
                }
                catch (Throwable throwable3) {
                    throw StartupProcessor.a(throwable3);
                }
            }
            TeamCityDatabaseManager teamCityDatabaseManager3 = this.myContext.getDatabaseManager();
            try {
                if (teamCityDatabaseManager3 == null) return;
                this.myLogger.debug("Disconnecting from the Database");
                teamCityDatabaseManager3.disconnect();
                this.myContext.setDatabaseManager(null);
                return;
            }
            catch (Throwable throwable4) {
                throw StartupProcessor.a(throwable4);
            }
        }
    }

    private void doRestore() throws KnownNonRecoverableStartupError, IOException {
        block21: {
            Integer n;
            block20: {
                String string;
                block17: {
                    try {
                        try {
                            if ($assertionsDisabled || this.myContext.getBackupFilePath() != null) break block17;
                        }
                        catch (KnownNonRecoverableStartupError knownNonRecoverableStartupError) {
                            throw StartupProcessor.a(knownNonRecoverableStartupError);
                        }
                        throw new AssertionError();
                    }
                    catch (KnownNonRecoverableStartupError knownNonRecoverableStartupError) {
                        throw StartupProcessor.a(knownNonRecoverableStartupError);
                    }
                }
                this.myContext.getDataDirManager().getConfigDir().mkdirs();
                File file = this.createDatabasePropertiesFile(this.myContext.getConnectionString());
                GeneralCommandLine generalCommandLine = new GeneralCommandLine();
                try {
                    string = TCSystemInfo.isWindows ? "cmd" : "sh";
                }
                catch (KnownNonRecoverableStartupError knownNonRecoverableStartupError) {
                    throw StartupProcessor.a(knownNonRecoverableStartupError);
                }
                String string2 = string;
                generalCommandLine.setExePath(String.format("%s%s%s.%s", System.getenv("TEAMCITY_BIN_DIRECTORY"), File.separator, "maintainDB", string2));
                generalCommandLine.addParameter("restore");
                generalCommandLine.addParameters(new String[]{"-F", this.myContext.getBackupFilePath().toAbsolutePath().toString()});
                generalCommandLine.addParameters(new String[]{"-A", this.myContext.getServerPaths().getDataDirectory().getAbsolutePath()});
                generalCommandLine.addParameters(new String[]{"-T", file.getAbsolutePath()});
                generalCommandLine.addParameters(new String[]{"--all"});
                this.myLogger.info("Starting restore process " + String.valueOf(generalCommandLine));
                File file2 = this.createRestoreMarker(generalCommandLine.toString());
                CommandLineExecutor commandLineExecutor = new CommandLineExecutor(generalCommandLine);
                ByteArrayOutputStream byteArrayOutputStream = this.createOutputStream(this.myContext.getRestoreProcessInfo());
                CompletableFuture<Integer> completableFuture = this.runRestoreProcess(commandLineExecutor, byteArrayOutputStream);
                while (true) {
                    block19: {
                        block18: {
                            try {
                                try {
                                    if (completableFuture.isDone()) break;
                                    if (!this.myContext.isShutdownRequested()) break block18;
                                }
                                catch (KnownNonRecoverableStartupError knownNonRecoverableStartupError) {
                                    throw StartupProcessor.a(knownNonRecoverableStartupError);
                                }
                                this.myLogger.warn("Cancelling restore process because of shut down");
                                this.killRestoreProcess();
                            }
                            catch (KnownNonRecoverableStartupError knownNonRecoverableStartupError) {
                                throw StartupProcessor.a(knownNonRecoverableStartupError);
                            }
                        }
                        try {
                            try {
                                if (!this.myContext.isCancelRestoreRequested()) break block19;
                                this.myContext.setCancelRestoreRequested(false);
                                this.myLogger.warn("Cancelling restore process because of user request");
                                if (!this.killRestoreProcess()) break block19;
                            }
                            catch (KnownNonRecoverableStartupError knownNonRecoverableStartupError) {
                                throw StartupProcessor.a(knownNonRecoverableStartupError);
                            }
                            this.myContext.getRestoreProcessInfo().add("MARK_CANCELED");
                        }
                        catch (KnownNonRecoverableStartupError knownNonRecoverableStartupError) {
                            throw StartupProcessor.a(knownNonRecoverableStartupError);
                        }
                    }
                    ThreadUtil.sleep((long)1000L);
                }
                n = completableFuture.getNow(-1);
                try {
                    if (n != 0) break block20;
                    this.myLogger.info("Restore completed successfully.");
                    this.myContext.getRestoreProcessInfo().add("MARK_FINISHED");
                    this.removeRestoreMarker(file2);
                    break block21;
                }
                catch (KnownNonRecoverableStartupError knownNonRecoverableStartupError) {
                    throw StartupProcessor.a(knownNonRecoverableStartupError);
                }
            }
            this.myLogger.error(String.format("Restore failed with exit code: %s. maintainDB log location is %s.", n, this.myContext.getLogsDir() + "teamcity-maintenance.log"));
            this.myContext.getRestoreProcessInfo().add("MARK_FAILED");
        }
        this.myContext.waitForFollowingStage();
    }

    @NotNull
    private CompletableFuture<Integer> runRestoreProcess(CommandLineExecutor commandLineExecutor, ByteArrayOutputStream byteArrayOutputStream) {
        CompletableFuture<Integer> completableFuture;
        try {
            completableFuture = CompletableFuture.supplyAsync(() -> {
                try {
                    return commandLineExecutor.runProcess(null, 0, byteArrayOutputStream, byteArrayOutputStream).getExitCode();
                }
                catch (ExecutionException executionException) {
                    this.myContext.addException((Throwable)executionException);
                    return -1;
                }
            });
            if (completableFuture == null) {
                StartupProcessor.$$$reportNull$$$0(20);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupProcessor.a(illegalStateException);
        }
        return completableFuture;
    }

    private boolean killRestoreProcess() {
        boolean bl = ProcessTreeTerminator.kill((KillProcessDetails)new KillProcessDetails((ProcessFilter)new /* Unavailable Anonymous Inner Class!! */, true));
        try {
            if (!bl) {
                this.myContext.addException((Throwable)new RuntimeException("Can not kill restore process."));
                this.myLogger.error("Restore process canceling failed");
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupProcessor.a(illegalStateException);
        }
        return bl;
    }

    private File createRestoreMarker(String string) throws IOException {
        Path path = Paths.get(String.valueOf(this.myContext.getDataDirManager().getRootDir()) + File.separator + "restoreStatusMarker.txt", new String[0]);
        List<String> list = Arrays.asList("Restoring TeamCity from backup started at " + String.valueOf(LocalDateTime.now()), " by executing command " + string);
        Files.write(path, list, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
        return path.toFile();
    }

    private void removeRestoreMarker(File file) throws IOException {
        Files.deleteIfExists(file.toPath());
    }

    @NotNull
    private ByteArrayOutputStream createOutputStream(List<String> list) {
        return new LineAwareByteArrayOutputStream(Charset.forName("UTF-8"), (LineAwareByteArrayOutputStream.LineListener)new /* Unavailable Anonymous Inner Class!! */);
    }

    /*
     * Loose catch block
     */
    private void doUpgrade() throws ServerStartupError {
        int n;
        int n2;
        block15: {
            block14: {
                try {
                    if (CurrentNodeInfo.isSecondaryNode()) {
                        throw new KnownNonRecoverableStartupError("Upgrade can't be run on the " + CurrentNodeInfo.getNodeId());
                    }
                }
                catch (KnownConvertException knownConvertException) {
                    throw StartupProcessor.a(knownConvertException);
                }
                n2 = this.myContext.getSoftwareVersion();
                n = this.myContext.getDatabaseVersion();
                if (!this.myUpgradePerformer.requiresExclusiveLock(n, n2)) break block14;
                try {
                    block16: {
                        if (this.lockDatabaseExclusively()) break block14;
                        break block16;
                        catch (KnownConvertException knownConvertException) {
                            throw StartupProcessor.a(knownConvertException);
                        }
                    }
                    this.proposeNextStage(StartupStage.WAITING_FOR_UPGRADE_LOCK, new StartupStage[0]);
                    return;
                }
                catch (KnownConvertException knownConvertException) {
                    throw StartupProcessor.a(knownConvertException);
                }
            }
            this.myLogger.info("Going to upgrade from version " + n + " to version " + n2);
            if ($assertionsDisabled) break block15;
            try {
                block17: {
                    if (this.myContext.getDatabaseManager() != null) break block15;
                    break block17;
                    catch (KnownConvertException knownConvertException) {
                        throw StartupProcessor.a(knownConvertException);
                    }
                }
                throw new AssertionError();
            }
            catch (KnownConvertException knownConvertException) {
                throw StartupProcessor.a(knownConvertException);
            }
        }
        Instant instant = Instant.now();
        try {
            this.myContext.getDatabaseManager().withMaintenanceDBF(dBFunctions -> this.myUpgradePerformer.doUpgrade(n, n2, dBFunctions, this.myContext.getServerPaths()));
        }
        catch (KnownConvertException knownConvertException) {
            throw new KnownNonRecoverableStartupError((Exception)((Object)knownConvertException));
        }
        catch (Exception exception) {
            throw new UnknownUpgradeError("Upgrading error: " + exception.getMessage(), (Throwable)exception);
        }
        this.myContext.recordStartupPhase("dbUpgrade", instant, Instant.now());
        this.retrieveDataDirVersion();
        this.analyseDatabase();
        this.releaseExclusiveLock();
        this.performDatabaseLockIfNeeded();
        this.proposeNextStage(StartupStage.APPLICATION_STARTING, new StartupStage[0]);
    }

    private void doLicenseAgreementSave() throws ServerStartupError {
        LicenseAgreement licenseAgreement;
        block15: {
            block14: {
                TeamCityDatabaseManager teamCityDatabaseManager = this.myContext.getDatabaseManager();
                try {
                    try {
                        if ($assertionsDisabled || teamCityDatabaseManager != null) break block14;
                    }
                    catch (ServerStartupError serverStartupError) {
                        throw StartupProcessor.a(serverStartupError);
                    }
                    throw new AssertionError();
                }
                catch (ServerStartupError serverStartupError) {
                    throw StartupProcessor.a(serverStartupError);
                }
            }
            licenseAgreement = this.myContext.getLicenseAgreement();
            try {
                try {
                    if ($assertionsDisabled || licenseAgreement != null) break block15;
                }
                catch (ServerStartupError serverStartupError) {
                    throw StartupProcessor.a(serverStartupError);
                }
                throw new AssertionError();
            }
            catch (ServerStartupError serverStartupError) {
                throw StartupProcessor.a(serverStartupError);
            }
        }
        try {
            licenseAgreement.accept();
            this.myContext.setLicenseAgreementAccepted();
            if (this.myContext.isDatabaseCreatedOnStartup()) {
                this.proposeNextStage(StartupStage.APPLICATION_STARTING, new StartupStage[0]);
                return;
            }
        }
        catch (ServerStartupError serverStartupError) {
            throw StartupProcessor.a(serverStartupError);
        }
        int n = this.myContext.getSoftwareVersion();
        int n2 = this.myContext.getDatabaseVersion();
        try {
            if (this.checkUpgradeRequired(n, n2)) {
                return;
            }
        }
        catch (ServerStartupError serverStartupError) {
            throw StartupProcessor.a(serverStartupError);
        }
        this.checkTablesAndStartApplication();
    }

    private void doApplicationStarting() throws ServerStartupError, IOException {
        block26: {
            block25: {
                String string;
                block24: {
                    this.prefillDataDirectoryBeforeStartIfNeeded();
                    InstallationsManager installationsManager = this.myContext.getInstallationsManager();
                    if (installationsManager.isEnabled()) {
                        string = this.myContext.getUUID();
                        if (string == null) {
                            string = StartupUtil.parseServerUUID((TeamCityDataDirectoryManager)this.myContext.getDataDirManager(), (StartupContext)this.myContext);
                        }
                        if (string != null) {
                            InstallationInfo installationInfo = installationsManager.findInstallation(string);
                            Collection collection = new TeamCityInstallationInfo(string, this.myContext.getServerPaths().getDataDirectory()).validate(installationInfo);
                            try {
                                if (!collection.isEmpty()) {
                                    this.proposeNextStage(StartupStage.COPIED_OR_MOVED_INSTALLATION_SCREEN, new StartupStage[0]);
                                    return;
                                }
                                break block24;
                            }
                            catch (ServerStartupError serverStartupError) {
                                throw StartupProcessor.a(serverStartupError);
                            }
                        }
                        this.myLogger.info("Skipping server installation check - UUID is not set yet");
                    } else {
                        this.myLogger.debug("Skipping server installation check");
                    }
                }
                string = this.myContext.getDataDirManager();
                try {
                    try {
                        if ($assertionsDisabled || string.containsConfiguredTeamcity()) break block25;
                    }
                    catch (ServerStartupError serverStartupError) {
                        throw StartupProcessor.a(serverStartupError);
                    }
                    throw new AssertionError((Object)"The main config file should be created so far");
                }
                catch (ServerStartupError serverStartupError) {
                    throw StartupProcessor.a(serverStartupError);
                }
            }
            try {
                try {
                    this.myLogger.debug("Creating DBFacade");
                    if ($assertionsDisabled || this.myContext.getDatabaseManager() != null) break block26;
                }
                catch (ServerStartupError serverStartupError) {
                    throw StartupProcessor.a(serverStartupError);
                }
                throw new AssertionError();
            }
            catch (ServerStartupError serverStartupError) {
                throw StartupProcessor.a(serverStartupError);
            }
        }
        try {
            this.myDBFacade = new DBFacade(this.myContext.getDatabaseManager());
            ServerThreadDumpUtils.setDatabaseType((String)this.myDBFacade.getDatabaseType().humanReadableName);
            if (CurrentNodeInfo.isSecondaryNode()) {
                ServerThreadDumpUtils.setNodeId((String)CurrentNodeInfo.getNodeId());
            }
        }
        catch (ServerStartupError serverStartupError) {
            throw StartupProcessor.a(serverStartupError);
        }
        try {
            ServerThreadDumpUtils.setStartupTime((Date)this.myContext.getServerStartupTimestamp());
            this.fixSingleRowTable();
            TomcatManagementUtil.setSocketBuffersSize((Logger)this.myLogger);
            TomcatManagementUtil.setMaxConnections((Logger)this.myLogger);
            if (this.myApplicationCreatorAndDestroyer != null) {
                this.myLogger.info("Starting the main application");
                this.myApplicationCreatorAndDestroyer.createApplication(this.myContext.getDatabaseManager(), this.myDBFacade);
            }
        }
        catch (ServerStartupError serverStartupError) {
            throw StartupProcessor.a(serverStartupError);
        }
        try {
            if (CurrentNodeInfo.isMainNode()) {
                this.cleanTemporaryFiles();
            }
        }
        catch (ServerStartupError serverStartupError) {
            throw StartupProcessor.a(serverStartupError);
        }
        this.proposeNextStage(StartupStage.READY, new StartupStage[0]);
        this.myContext.setServerStartupFinishedTimestamp(Dates.now());
        this.printStartupDuration();
    }

    private void cleanTemporaryFiles() throws IOException {
        this.removeRestoreMarker(Paths.get(String.valueOf(this.myContext.getDataDirManager().getRootDir()) + File.separator + "restoreStatusMarker.txt", new String[0]).toFile());
    }

    private void prefillDataDirectoryBeforeStartIfNeeded() throws DataDirectoryException {
        block17: {
            TeamCityDataDirectoryManager teamCityDataDirectoryManager;
            block15: {
                try {
                    if (!this.myContext.getDataDirPrefilled().compareAndSet(false, true)) {
                        return;
                    }
                }
                catch (DataDirectoryException dataDirectoryException) {
                    throw StartupProcessor.a(dataDirectoryException);
                }
                teamCityDataDirectoryManager = this.myContext.getDataDirManager();
                try {
                    try {
                        block16: {
                            try {
                                try {
                                    if (!this.myContext.isStartingAsMain()) break block15;
                                    teamCityDataDirectoryManager.ensureExistence();
                                    teamCityDataDirectoryManager.extractPresets();
                                    if (this.myContext.isFirstStart()) break block16;
                                }
                                catch (DataDirectoryException dataDirectoryException) {
                                    throw StartupProcessor.a(dataDirectoryException);
                                }
                                if (!this.myContext.isRestoreProcess()) break block15;
                            }
                            catch (DataDirectoryException dataDirectoryException) {
                                throw StartupProcessor.a(dataDirectoryException);
                            }
                        }
                        if (XmxWarningUtil.createXmxWarningFile((File)this.myContext.getDataDirManager().getRootDir())) break block15;
                    }
                    catch (DataDirectoryException dataDirectoryException) {
                        throw StartupProcessor.a(dataDirectoryException);
                    }
                    this.myLogger.warn("Unable to create xmx-warning marker file, warning can be shown after restart");
                }
                catch (DataDirectoryException dataDirectoryException) {
                    throw StartupProcessor.a(dataDirectoryException);
                }
            }
            try {
                try {
                    ServerLoggingConfiguration.dataDirectoryIsResolved((File)teamCityDataDirectoryManager.getRootDir());
                    if (!this.myContext.isStartingAsMain() || teamCityDataDirectoryManager.containsConfiguredTeamcity()) break block17;
                }
                catch (DataDirectoryException dataDirectoryException) {
                    throw StartupProcessor.a(dataDirectoryException);
                }
                this.activateDataDirectory();
            }
            catch (DataDirectoryException dataDirectoryException) {
                throw StartupProcessor.a(dataDirectoryException);
            }
        }
    }

    private void fixSingleRowTable() {
        block7: {
            try {
                if (CurrentNodeInfo.isSecondaryNode()) {
                    return;
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw StartupProcessor.a(illegalStateException);
            }
            try {
                try {
                    if ($assertionsDisabled || this.myDBFacade != null) break block7;
                }
                catch (IllegalStateException illegalStateException) {
                    throw StartupProcessor.a(illegalStateException);
                }
                throw new AssertionError();
            }
            catch (IllegalStateException illegalStateException) {
                throw StartupProcessor.a(illegalStateException);
            }
        }
        new SingleRowTableFixer((DBFunctionsProvider)this.myDBFacade).checkAndFix();
    }

    private void printStartupDuration() {
        long l = this.myContext.getServerStartupDuration();
        this.myLogger.info("Startup duration: " + TimePrinter.formatSecondsMinimal((long)(l / 1000L)));
    }

    private void doWaitUntilDataDirectoryIsReady() throws IOException, KnownNonRecoverableStartupError {
        TeamCityDataDirectoryManager teamCityDataDirectoryManager;
        block13: {
            while (true) {
                block12: {
                    try {
                        try {
                            if (!Thread.currentThread().isInterrupted() && !this.myContext.isShutdownRequested()) break block12;
                        }
                        catch (IOException iOException) {
                            throw StartupProcessor.a(iOException);
                        }
                        return;
                    }
                    catch (IOException iOException) {
                        throw StartupProcessor.a(iOException);
                    }
                }
                teamCityDataDirectoryManager = this.myContext.getDataDirManager();
                try {
                    if (teamCityDataDirectoryManager.isDataDirInitialized()) {
                        break;
                    }
                }
                catch (IOException iOException) {
                    throw StartupProcessor.a(iOException);
                }
                ThreadUtil.sleep((long)2000L);
            }
            this.myLogger.info("Data Directory was initialized by the main TeamCity Server.");
            teamCityDataDirectoryManager = this.myContext.getDatabasePropertiesFile();
            try {
                try {
                    if ($assertionsDisabled || teamCityDataDirectoryManager != null) break block13;
                }
                catch (IOException iOException) {
                    throw StartupProcessor.a(iOException);
                }
                throw new AssertionError();
            }
            catch (IOException iOException) {
                throw StartupProcessor.a(iOException);
            }
        }
        teamCityDataDirectoryManager.refreshState(false);
        this.doProcessDataDirectoryContainingTeamCity();
    }

    /*
     * Loose catch block
     */
    private void doWaitForUpgradeEnd() throws ServerStartupError {
        block19: {
            this.myLogger.info("Waiting for the data upgrade...");
            AtomicBoolean atomicBoolean = new AtomicBoolean(false);
            while (true) {
                block18: {
                    TeamCityDatabaseManager teamCityDatabaseManager;
                    block17: {
                        if (atomicBoolean.get()) break;
                        try {
                            block20: {
                                if (Thread.currentThread().isInterrupted()) break;
                                break block20;
                                catch (Heartbeat.ExclusiveLockTakenError exclusiveLockTakenError) {
                                    throw StartupProcessor.a(exclusiveLockTakenError);
                                }
                            }
                            if (this.myContext.isShutdownRequested()) break;
                        }
                        catch (Heartbeat.ExclusiveLockTakenError exclusiveLockTakenError) {
                            throw StartupProcessor.a(exclusiveLockTakenError);
                        }
                        teamCityDatabaseManager = this.myContext.getDatabaseManager();
                        if ($assertionsDisabled) break block17;
                        try {
                            block21: {
                                if (teamCityDatabaseManager != null) break block17;
                                break block21;
                                catch (Heartbeat.ExclusiveLockTakenError exclusiveLockTakenError) {
                                    throw StartupProcessor.a(exclusiveLockTakenError);
                                }
                            }
                            throw new AssertionError();
                        }
                        catch (Heartbeat.ExclusiveLockTakenError exclusiveLockTakenError) {
                            throw StartupProcessor.a(exclusiveLockTakenError);
                        }
                    }
                    try {
                        teamCityDatabaseManager.withDBNoResult(dBFunctions -> {
                            boolean bl;
                            AtomicBoolean atomicBoolean2;
                            try {
                                atomicBoolean2 = atomicBoolean;
                                bl = this.myContext.getSoftwareVersion() == DBSchema.loadDatabaseVersion((DBFunctions)dBFunctions);
                            }
                            catch (DBException dBException) {
                                throw StartupProcessor.a(dBException);
                            }
                            atomicBoolean2.set(bl);
                        });
                        if (!atomicBoolean.get()) break block18;
                        this.performDatabaseLockIfNeeded();
                        break block18;
                    }
                    catch (ServerStartupError serverStartupError) {
                        throw StartupProcessor.a(serverStartupError);
                    }
                    {
                        catch (Heartbeat.ExclusiveLockTakenError exclusiveLockTakenError) {
                            atomicBoolean.set(false);
                        }
                    }
                }
                ThreadUtil.sleep((long)1000L);
            }
            try {
                try {
                    if (!Thread.currentThread().isInterrupted() && !this.myContext.isShutdownRequested()) break block19;
                }
                catch (Heartbeat.ExclusiveLockTakenError exclusiveLockTakenError) {
                    throw StartupProcessor.a(exclusiveLockTakenError);
                }
                return;
            }
            catch (Heartbeat.ExclusiveLockTakenError exclusiveLockTakenError) {
                throw StartupProcessor.a(exclusiveLockTakenError);
            }
        }
        this.myLogger.info("Data was upgraded.");
        this.retrieveDataDirVersion();
        this.analyseDatabase();
        this.proposeNextStage(StartupStage.APPLICATION_STARTING, new StartupStage[0]);
    }

    protected void doShuttingDown() {
        try {
            this.doShuttingDownInternal();
        }
        catch (Exception exception) {
            this.myContext.addException((Throwable)exception);
        }
        catch (Throwable throwable) {
            this.myContext.addException((Throwable)new RuntimeException(throwable));
        }
        finally {
            this.proposeNextStage(StartupStage.END, new StartupStage[0]);
        }
        long l = (Dates.now().getTime() - this.myContext.getServerStartupTimestamp().getTime()) / Dates.ONE_SECOND;
        try {
            this.myLogger.info("Server shutdown complete. Uptime was " + TimePrinter.formatSecondsMinimal((long)l));
            if (!LogInitializer.isUnitTest()) {
                LogManager.shutdown();
            }
        }
        catch (Exception exception) {
            throw StartupProcessor.a(exception);
        }
    }

    private void doShuttingDownInternal() {
        TeamCityDatabaseManager teamCityDatabaseManager = this.myContext.getDatabaseManager();
        try {
            if (teamCityDatabaseManager != null) {
                teamCityDatabaseManager.beforeDisconnect();
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupProcessor.a(illegalStateException);
        }
        try {
            if (this.myApplicationCreatorAndDestroyer != null) {
                this.myLogger.info("Stopping the main application");
                this.myApplicationCreatorAndDestroyer.destroyApplication();
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupProcessor.a(illegalStateException);
        }
        try {
            if (this.myDBFacade != null) {
                this.myLogger.debug("Closing DBFacade");
                this.myDBFacade.shutdown();
                this.myDBFacade = null;
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupProcessor.a(illegalStateException);
        }
        try {
            if (teamCityDatabaseManager != null) {
                this.myLogger.debug("Disconnecting from the Database");
                teamCityDatabaseManager.disconnect();
                this.myContext.setDatabaseManager(null);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupProcessor.a(illegalStateException);
        }
        JdbcDrivers jdbcDrivers = this.myContext.getJdbcDrivers();
        try {
            if (jdbcDrivers != null) {
                this.myContext.setJdbcDrivers(null);
                jdbcDrivers.unregisterAll();
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupProcessor.a(illegalStateException);
        }
        try {
            if (this.myVersionFile != null) {
                this.myVersionFile.unlock();
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupProcessor.a(illegalStateException);
        }
    }

    private void doDownloadJdbcDrivers() {
        try {
            this.doDownloadJdbcDriversInternal(this.myContext.getJdbcDriverMetadata());
        }
        finally {
            this.proposeNextStage(this.myContext.getPreviousStage(), new StartupStage[0]);
        }
    }

    private void doDownloadJdbcDriversInternal(@NotNull Map<DatabaseType, JdbcDriver> map) {
        DatabaseType databaseType;
        block29: {
            try {
                if (map == null) {
                    StartupProcessor.$$$reportNull$$$0(21);
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw StartupProcessor.a(illegalStateException);
            }
            databaseType = this.myContext.getJdbcDriverType();
            try {
                try {
                    if (StartupUtil.shouldDownloadJdbcDriver((StartupContext)this.myContext, (DatabaseType)databaseType) && map.containsKey(databaseType)) break block29;
                }
                catch (IllegalStateException illegalStateException) {
                    throw StartupProcessor.a(illegalStateException);
                }
                return;
            }
            catch (IllegalStateException illegalStateException) {
                throw StartupProcessor.a(illegalStateException);
            }
        }
        JdbcDrivers jdbcDrivers = this.myContext.getJdbcDrivers();
        try {
            if (jdbcDrivers != null) {
                jdbcDrivers.unregisterAll();
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupProcessor.a(illegalStateException);
        }
        try {
            if (!this.myContext.getDataDirManager().isJdbcDirWritable()) {
                throw new IllegalStateException("Can't download JDBC driver as `" + String.valueOf(this.myContext.getDataDirManager().getJdbcDir()) + "` not writable.");
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupProcessor.a(illegalStateException);
        }
        JdbcDriverDownloader jdbcDriverDownloader = new JdbcDriverDownloader(this.myLogger, StartupProcessor.newUrlConnectionFactory(), (TeamCityPropertiesProvider)new InternalPropertiesProvider());
        File file = this.myContext.getDataDirManager().getJdbcDir();
        JdbcDriverDownloadProgressListener jdbcDriverDownloadProgressListener = new JdbcDriverDownloadProgressListener();
        this.myContext.setJdbcDriverDownloadProgressListener((JdbcDriverDownloader.ProgressListener)jdbcDriverDownloadProgressListener);
        ArrayList arrayList = new ArrayList();
        jdbcDriverDownloader.download(databaseType, file, false, arrayList, (JdbcDriverDownloader.ProgressListener)jdbcDriverDownloadProgressListener, map);
        StartupHelper.reloadJdbcDriversIfNeeded((StartupContext)this.myContext);
        if (!arrayList.isEmpty()) {
            boolean bl = this.myLogger.isDebugEnabled();
            LinkedHashSet<String> linkedHashSet = new LinkedHashSet<String>();
            for (Exception exception : arrayList) {
                String string;
                block31: {
                    block32: {
                        block30: {
                            string = exception.getMessage();
                            try {
                                try {
                                    if (string != null && !string.isEmpty()) break block30;
                                }
                                catch (IllegalStateException illegalStateException) {
                                    throw StartupProcessor.a(illegalStateException);
                                }
                                this.myContext.addException((Throwable)exception);
                                this.myLogger.warn((Throwable)exception);
                                continue;
                            }
                            catch (IllegalStateException illegalStateException) {
                                throw StartupProcessor.a(illegalStateException);
                            }
                        }
                        try {
                            try {
                                if (!bl) break block31;
                                if (!linkedHashSet.add(string)) break block32;
                            }
                            catch (IllegalStateException illegalStateException) {
                                throw StartupProcessor.a(illegalStateException);
                            }
                            this.myContext.addException((Throwable)exception);
                        }
                        catch (IllegalStateException illegalStateException) {
                            throw StartupProcessor.a(illegalStateException);
                        }
                    }
                    this.myLogger.warn((Throwable)exception);
                    continue;
                }
                try {
                    if (linkedHashSet.add(string)) {
                        this.myContext.addException((Throwable)new IOException(string));
                    }
                }
                catch (IllegalStateException illegalStateException) {
                    throw StartupProcessor.a(illegalStateException);
                }
                this.myLogger.warn(String.format("%s: %s", exception.getClass().getName(), string));
            }
            return;
        }
        String string = JdbcDriverDownloader.getLicenseFileName((DatabaseType)databaseType, map);
        try {
            if (string != null) {
                this.myLogger.info(String.format("Be sure to review and accept the license of the JDBC driver, located at %s.", new File(file, string)));
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupProcessor.a(illegalStateException);
        }
    }

    protected void proposeNextStage(@NotNull StartupStage startupStage, StartupStage ... startupStageArray) {
        StartupStage startupStage2;
        try {
            if (startupStage == null) {
                StartupProcessor.$$$reportNull$$$0(22);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupProcessor.a(illegalStateException);
        }
        try {
            if (startupStageArray == null) {
                StartupProcessor.$$$reportNull$$$0(23);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupProcessor.a(illegalStateException);
        }
        if (this.myContext.isBypassingMaintenanceQuestions()) {
            StartupStage startupStage3 = (StartupStage)StartupConsts.BYPASS_STAGES.get(startupStage);
            if (startupStage3 != null) {
                startupStage2 = startupStage3;
                this.myLogger.info("Bypassing the " + String.valueOf(startupStage) + " in no-maintenance-screens mode");
            } else {
                startupStage2 = startupStage;
            }
            int n = startupStageArray.length;
            for (int i = 0; i < n; ++i) {
                StartupStage startupStage4 = (StartupStage)StartupConsts.BYPASS_STAGES.get(startupStageArray[i]);
                try {
                    if (startupStage4 == null) continue;
                    startupStageArray[i] = startupStage4;
                    continue;
                }
                catch (IllegalStateException illegalStateException) {
                    throw StartupProcessor.a(illegalStateException);
                }
            }
        } else {
            startupStage2 = startupStage;
        }
        this.myContext.proposeNextStage(startupStage2, startupStageArray);
    }

    @NotNull
    private TeamCityDataDirectoryManager getDataDirectoryManager() {
        TeamCityDataDirectoryManager teamCityDataDirectoryManager;
        try {
            teamCityDataDirectoryManager = this.myContext.getDataDirManager();
            if (teamCityDataDirectoryManager == null) {
                StartupProcessor.$$$reportNull$$$0(24);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupProcessor.a(illegalStateException);
        }
        return teamCityDataDirectoryManager;
    }

    @NotNull
    private ServerPaths getServerPaths() {
        ServerPaths serverPaths;
        try {
            serverPaths = this.myContext.getServerPaths();
            if (serverPaths == null) {
                StartupProcessor.$$$reportNull$$$0(25);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupProcessor.a(illegalStateException);
        }
        return serverPaths;
    }

    private void logExistence(@NotNull String string, @NotNull File file, boolean bl) {
        String string2;
        try {
            if (string == null) {
                StartupProcessor.$$$reportNull$$$0(26);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupProcessor.a(illegalStateException);
        }
        try {
            if (file == null) {
                StartupProcessor.$$$reportNull$$$0(27);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupProcessor.a(illegalStateException);
        }
        try {
            string2 = bl ? "exists" : "doesn't exist";
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupProcessor.a(illegalStateException);
        }
        String string3 = string2;
        String string4 = string + " \"" + file.getAbsolutePath() + "\" " + string3;
        this.myLogger.info(string4);
    }

    private void askAdminToLoginUsingMaintenanceAuthenticationToken() {
        try {
            if (this.myContext.isMaintenanceAsked()) {
                return;
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupProcessor.a(illegalStateException);
        }
        this.myContext.setMaintenanceAsked(true);
        if (!this.myContext.isFirstStart()) {
            String string = "Administrator can login from web UI using super user authentication token (better use a private browser window)";
            this.myLogger.info(string);
            System.out.println("[TeamCity] " + string);
            this.myContext.logAuthenticationToken();
        }
    }

    @NotNull
    Thread getStartupThread() {
        Thread thread;
        try {
            thread = this.myStartupThread;
            if (thread == null) {
                StartupProcessor.$$$reportNull$$$0(28);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupProcessor.a(illegalStateException);
        }
        return thread;
    }

    static void clearStaticReferences() {
        ourInstance = null;
    }

    @Nullable
    private String getEnv(@NotNull String string) {
        try {
            if (string == null) {
                StartupProcessor.$$$reportNull$$$0(29);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupProcessor.a(illegalStateException);
        }
        return (String)this.myContext.getEnv().get(string);
    }

    private boolean canPersistDataDirLocation() {
        boolean bl;
        block7: {
            block6: {
                try {
                    try {
                        try {
                            if (!StartupConfiguration.canSave() || System.getProperty("teamcity.data.path") != null) break block6;
                        }
                        catch (IllegalStateException illegalStateException) {
                            throw StartupProcessor.a(illegalStateException);
                        }
                        if (this.getEnv("TEAMCITY_DATA_PATH") != null) break block6;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw StartupProcessor.a(illegalStateException);
                    }
                    bl = true;
                    break block7;
                }
                catch (IllegalStateException illegalStateException) {
                    throw StartupProcessor.a(illegalStateException);
                }
            }
            bl = false;
        }
        return bl;
    }

    private void persistDataDirLocation(@NotNull File file) throws KnownNonRecoverableStartupError {
        try {
            if (file == null) {
                StartupProcessor.$$$reportNull$$$0(30);
            }
        }
        catch (IOException iOException) {
            throw StartupProcessor.a(iOException);
        }
        try {
            try {
                if (!this.canPersistDataDirLocation()) {
                    throw new IllegalStateException("We can't persist data directory location");
                }
            }
            catch (IOException iOException) {
                throw StartupProcessor.a(iOException);
            }
            StartupConfiguration.saveProperty((String)"teamcity.data.path", (String)file.getAbsolutePath());
            String string = StartupConfiguration.getLocation();
            this.myLogger.info("The TeamCity Data Directory path '" + file.getAbsolutePath() + "' is stored in '" + string + "'");
        }
        catch (IOException iOException) {
            throw new KnownNonRecoverableStartupError("Error while saving the Data Directory path", (Throwable)iOException);
        }
    }

    void setUpgradePerformer(UpgradePerformer upgradePerformer) {
        this.myUpgradePerformer = upgradePerformer;
    }

    @NotNull
    private static JdbcDriverDownloader.UrlConnectionFactory newUrlConnectionFactory() {
        Object object;
        try {
            object = TeamCityProperties.getBoolean((String)"teamcity.maintenance.jdbcDrivers.allowNonHttpUrls") ? new DefaultUrlConnectionFactory("GET", true) : new HttpUrlConnectionFactory("GET");
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupProcessor.a(illegalStateException);
        }
        return object;
    }

    private static /* synthetic */ void lambda$new$0(Thread.UncaughtExceptionHandler uncaughtExceptionHandler, Thread thread, Throwable throwable) {
        try {
            if (uncaughtExceptionHandler != null) {
                uncaughtExceptionHandler.uncaughtException(thread, throwable);
            }
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupProcessor.a(illegalStateException);
        }
        Loggers.SERVER.error("Got unexpected exception, thread \"" + thread.getName() + "\" is terminated. Error: " + throwable.toString(), throwable);
    }

    static {
        boolean bl;
        try {
            bl = !StartupProcessor.class.desiredAssertionStatus();
        }
        catch (IllegalStateException illegalStateException) {
            throw StartupProcessor.a(illegalStateException);
        }
        $assertionsDisabled = bl;
        VERSION_FILE_NAME = "/BUILD_" + ServerVersionHolder.getVersion().getBuildNumber();
        System.setProperty("hsqldb.method_class_names", "abc");
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

    private static Throwable a(Throwable throwable) {
        return throwable;
    }
}
