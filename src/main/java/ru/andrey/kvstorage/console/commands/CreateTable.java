package ru.andrey.kvstorage.console.commands;

import ru.andrey.kvstorage.console.DatabaseCommand;
import ru.andrey.kvstorage.console.DatabaseCommandResult;
import ru.andrey.kvstorage.console.ExecutionEnvironment;
import ru.andrey.kvstorage.exception.DatabaseException;

import static ru.andrey.kvstorage.console.DatabaseCommandResult.DefaultDatabaseCommandResult;

public class CreateTable implements DatabaseCommand {
    private final ExecutionEnvironment env;
    private final String databaseName;
    private final String tableName;

    public CreateTable(ExecutionEnvironment env, String databaseName, String tableName) {
        this.env = env;
        this.databaseName = databaseName;
        this.tableName = tableName;
    }

    @Override
    public DatabaseCommandResult execute() {
        return env.getDatabase(databaseName).map(database -> {
            try {
                database.createTableIfNotExists(tableName);
                return DefaultDatabaseCommandResult.success(
                        String.format("Table \"%s\" in database \"%s\" is created successfully", tableName, databaseName)
                );
            } catch (DatabaseException ex) {
                return DefaultDatabaseCommandResult.error(ex.getMessage());
            }
        }).orElseGet(() -> DefaultDatabaseCommandResult.error(
                String.format("Database \"%s\" doesn't exist", databaseName)
        ));
    }
}
