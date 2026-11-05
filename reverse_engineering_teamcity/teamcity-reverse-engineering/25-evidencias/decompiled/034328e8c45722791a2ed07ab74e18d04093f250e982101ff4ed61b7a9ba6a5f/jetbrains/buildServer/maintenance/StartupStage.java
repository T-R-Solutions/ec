/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package jetbrains.buildServer.maintenance;

import org.jetbrains.annotations.NotNull;

/*
 * Exception performing whole class analysis ignored.
 */
public enum StartupStage {
    INITIAL_STAGE(true, false, false, "startup-init", "Initializing Startup subsystem", "icon-refresh icon-spin ring-loader-inline"),
    FATAL_ERROR(false, false, false, "fatal-error", "Fatal error", "stage-unrecoverable-error  tc-icon icon16 tc-icon_attention tc-icon_attention_red"),
    LOOKING_FOR_DATA_DIR(true, false, false, "startup-in-progress", "Looking for the TeamCity Data Directory", "icon-refresh icon-spin ring-loader-inline"),
    ENCRYPTION_CONFIGURATION_ERROR(false, true, false, "encryption-configuration-warning", "One or more encryption keys are missing", "stage-unrecoverable-error  tc-icon icon16 tc-icon_attention tc-icon_attention_red"),
    XMX_WARNING(false, true, false, "xmx-warning", "Review TeamCity server memory settings", "stage-admin-required-to-proceed tc-icon icon16 tc-icon_attention tc-icon_attention_yellow"),
    WAITING_FOR_DATA_DIRECTORY_INITIALIZATION(true, true, false, "waiting-for-data-directory-initialization", "Waiting for TeamCity Data Directory to initialize", "stage-admin-required-to-proceed tc-icon icon16 tc-icon_attention tc-icon_attention_yellow"),
    FIRST_START_SCREEN(false, true, false, "first-start", "Confirming TeamCity first start", "stage-admin-required-to-proceed tc-icon icon16 tc-icon_attention tc-icon_attention_yellow"),
    PROCESS_DATA_DIR_SELECTED_BY_USER(true, false, false, "startup-in-progress", "Initializing the TeamCity Data Directory", "icon-refresh icon-spin ring-loader-inline"),
    ASK_NODE_TYPE(false, true, false, "ask-node-type", "Confirming the TeamCity node type", "stage-admin-required-to-proceed tc-icon icon16 tc-icon_attention tc-icon_attention_yellow"),
    CREATE_NEW_DATA_DIR(true, false, false, "startup-in-progress", "Initializing the TeamCity Data Directory", "icon-refresh icon-spin ring-loader-inline"),
    LOOKING_FOR_DB_SETTINGS(true, false, false, "startup-in-progress", "Looking for the database configuration", "icon-refresh icon-spin ring-loader-inline"),
    RECREATE_DB_SCREEN(false, true, false, "recreate-db", "Database is empty or doesn't exist", "stage-admin-required-to-proceed tc-icon icon16 tc-icon_attention tc-icon_attention_yellow"),
    DB_SETTINGS_SCREEN(false, true, false, "db-settings", "Setting up database connection", "stage-admin-required-to-proceed tc-icon icon16 tc-icon_attention tc-icon_attention_yellow"),
    CREATE_NEW_DB(true, false, false, "startup-in-progress", "Creating a new database", "icon-refresh icon-spin ring-loader-inline"),
    DB_CONNECT(true, false, false, "startup-in-progress", "Connecting to the database", "icon-refresh icon-spin ring-loader-inline"),
    DB_CONNECT_CONTINUE(true, true, false, "connect-continue", "Continuing attempts to connect to the database", "icon-refresh icon-spin ring-loader-inline"),
    DB_CHECKING(true, false, false, "startup-in-progress", "Checking the database", "icon-refresh icon-spin ring-loader-inline"),
    SECOND_INSTANCE(false, true, false, "second-instance", "Second TeamCity instance detected", "stage-unrecoverable-error  tc-icon icon16 tc-icon_attention tc-icon_attention_red"),
    CORRUPTED_INSTALLATION(false, true, false, "corrupted-installation", "Corrupted TeamCity installation", "stage-unrecoverable-error  tc-icon icon16 tc-icon_attention tc-icon_attention_red"),
    OUTDATED_SECONDARY_NODE(false, true, false, "outdated-secondary-node", "TeamCity Node upgrade is required", "stage-unrecoverable-error  tc-icon icon16 tc-icon_attention tc-icon_attention_red"),
    WAITING_FOR_UPGRADE_LOCK(true, true, false, "waiting-for-upgrade-lock", "Data upgrade is required", "stage-admin-required-to-proceed tc-icon icon16 tc-icon_attention tc-icon_attention_yellow"),
    WAITING_FOR_OTHER_NODE_SHUTDOWN(true, true, false, "waiting-for-other-version-bmp-shutdown", "Secondary node of a different version detected", "stage-admin-required-to-proceed tc-icon icon16 tc-icon_attention tc-icon_attention_yellow"),
    MAIN_SERVER_OF_A_DIFFERENT_VERSION_ERROR(false, true, false, "main-server-of-different-version-detected", "Active main server of a different version detected", "stage-unrecoverable-error  tc-icon icon16 tc-icon_attention tc-icon_attention_red"),
    OTHER_NODE_WITH_SAME_ID_IS_ACTIVE(false, true, false, "other-node-with-same-id", "The node id is already in use", "stage-unrecoverable-error  tc-icon icon16 tc-icon_attention tc-icon_attention_red"),
    OTHER_NODE_WITH_DIFFERENT_DATA_DIRECTORY(false, true, false, "other-node-with-different-data-directory", "The active secondary node is using a different Data Directory", "stage-unrecoverable-error  tc-icon icon16 tc-icon_attention tc-icon_attention_red"),
    MAIN_SERVER_WITH_DIFFERENT_DATA_DIRECTORY(false, true, false, "main-server-with-different-data-directory", "The active main server is using a different Data Directory", "stage-unrecoverable-error  tc-icon icon16 tc-icon_attention tc-icon_attention_red"),
    WAITING_FOR_UPGRADE_END(true, true, false, "waiting-for-upgrade-end", "Data upgrade is required", "stage-admin-required-to-proceed tc-icon icon16 tc-icon_attention tc-icon_attention_yellow"),
    TEAMCITY_NODE_ID_NOT_SPECIFIED(false, true, false, "node-id-isnt-specified", "TeamCity node id is not specified", "stage-admin-required-to-proceed tc-icon icon16 tc-icon_attention tc-icon_attention_yellow"),
    LICENSE_AGREEMENT_SCREEN(false, true, false, "license-agreement", "Review and accept TeamCity license agreement to continue using the product", "stage-admin-required-to-proceed tc-icon icon16 tc-icon_attention tc-icon_attention_yellow"),
    LICENSE_AGREEMENT_SAVE(true, false, false, "startup-in-progress", "Saving License Agreement consent", "icon-refresh icon-spin ring-loader-inline"),
    UPGRADE_SCREEN(false, true, false, "upgrade", "Data upgrade is required", "stage-admin-required-to-proceed tc-icon icon16 tc-icon_attention tc-icon_attention_yellow"),
    BACKUP(true, false, false, "backup-progress", "Backup", "icon-refresh icon-spin ring-loader-inline"),
    UPGRADE(true, false, false, "startup-in-progress", "Upgrade", "icon-refresh icon-spin ring-loader-inline"),
    RESTORE_SETTINGS_SCREEN(false, true, false, "restore-settings", "Restore settings", "stage-admin-required-to-proceed tc-icon icon16 tc-icon_attention tc-icon_attention_yellow"),
    BACKUP_FILE_CHECKING(true, false, false, "startup-in-progress", "Checking backup file", "icon-refresh icon-spin ring-loader-inline"),
    RESTORE_DATABASE_SETTINGS_SCREEN(false, true, false, "restore-db-settings", "Target database settings", "stage-admin-required-to-proceed tc-icon icon16 tc-icon_attention tc-icon_attention_yellow"),
    RESTORE_DB_CHECKING(true, false, false, "startup-in-progress", "Checking the target database", "icon-refresh icon-spin ring-loader-inline"),
    RESTORE(true, true, false, "restore-progress", "Restore", "stage-admin-required-to-proceed tc-icon icon16 tc-icon_attention tc-icon_attention_yellow"),
    COPIED_OR_MOVED_INSTALLATION_SCREEN(false, true, false, "copied-or-moved-installation", "Current server seems to be copied or moved", "stage-admin-required-to-proceed tc-icon icon16 tc-icon_attention tc-icon_attention_yellow"),
    APPLICATION_STARTING(true, false, false, "startup-in-progress", "Initializing TeamCity server components", "icon-refresh icon-spin ring-loader-inline"),
    READY(false, false, false, "ready", "System is ready", "stage-system-is-ready tc-icon icon16 tc-icon_success"),
    EXCEPTION(false, true, false, "exception", "TeamCity server startup error", "stage-unrecoverable-error  tc-icon icon16 tc-icon_attention tc-icon_attention_red"),
    SHUTTING_DOWN(true, false, true, "startup-in-progress", "TeamCity server is shutting down", "icon-refresh icon-spin ring-loader-inline"),
    END(false, false, true, "startup-in-progress", "Stopped", "stage-server-stopped tc-icon icon16 tc-icon_cancelled"),
    DOWNLOADING_JDBC_DRIVERS(true, false, false, "downloading-jdbc-drivers", "Downloading JDBC drivers", "icon-refresh icon-spin ring-loader-inline");

    public final boolean active;
    public final boolean adminRequires;
    public final boolean shuttingDown;
    @NotNull
    public final String page;
    @NotNull
    public final String description;
    @NotNull
    public final String iconCode;

    private StartupStage(boolean bl, @NotNull boolean bl2, @NotNull boolean bl3, @NotNull String string2, String string3, String string4) {
        if (string2 == null) {
            StartupStage.$$$reportNull$$$0(0);
        }
        if (string3 == null) {
            StartupStage.$$$reportNull$$$0(1);
        }
        if (string4 == null) {
            StartupStage.$$$reportNull$$$0(2);
        }
        this.active = bl;
        this.adminRequires = bl2;
        this.shuttingDown = bl3;
        this.page = string2;
        this.description = string3;
        this.iconCode = string4;
    }

    /*
     * Exception decompiling
     */
    private static /* synthetic */ void $$$reportNull$$$0(int var0) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 1[SWITCH]
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
