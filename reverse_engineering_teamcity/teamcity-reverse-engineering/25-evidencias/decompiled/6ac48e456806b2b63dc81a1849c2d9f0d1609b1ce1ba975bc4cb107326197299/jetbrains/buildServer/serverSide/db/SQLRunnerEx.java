/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jetbrains.buildServer.serverSide.SQLRunner
 *  jetbrains.buildServer.serverSide.db.DBFunctionsProvider
 *  jetbrains.buildServer.serverSide.db.DatabaseType
 *  org.intellij.lang.annotations.Language
 *  org.jetbrains.annotations.NotNull
 *  org.springframework.jdbc.UncategorizedSQLException
 *  org.springframework.transaction.support.TransactionTemplate
 */
package jetbrains.buildServer.serverSide.db;

import jetbrains.buildServer.serverSide.SQLRunner;
import jetbrains.buildServer.serverSide.db.DBFunctionsProvider;
import jetbrains.buildServer.serverSide.db.DatabaseType;
import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.transaction.support.TransactionTemplate;

public interface SQLRunnerEx
extends SQLRunner,
DBFunctionsProvider {
    public DatabaseType getDatabaseType();

    public TransactionTemplate getTransactionTemplate();

    public void runDdl(@NotNull @Language(value="SQL") String var1, @NotNull String var2) throws UncategorizedSQLException;

    public void runDdls(@NotNull @Language(value="SQL") String[] var1) throws UncategorizedSQLException;

    public void runDmls(@NotNull @Language(value="SQL") String[] var1) throws UncategorizedSQLException;
}
