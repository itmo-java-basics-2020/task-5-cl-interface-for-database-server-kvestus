package ru.andrey.kvstorage.console.commands;

import ru.andrey.kvstorage.console.DatabaseCommand;
import ru.andrey.kvstorage.console.DatabaseCommandResult;
import ru.andrey.kvstorage.console.ExecutionEnvironment;
import ru.andrey.kvstorage.exception.DatabaseException;

import static ru.andrey.kvstorage.console.DatabaseCommandResult.DefaultDatabaseCommandResult;

public class UpdateKey implements DatabaseCommand {

    private final ExecutionEnvironment env;
    private final String databaseName;
    private final String tableName;
    private final String key;
    private final String value;

    public UpdateKey(ExecutionEnvironment env,
                     String databaseName,
                     String tableName,
                     String key,
                     String value) {
        this.env = env;
        this.databaseName = databaseName;
        this.tableName = tableName;
        this.key = key;
        this.value = value;
    }

    @Override
    public DatabaseCommandResult execute() {
        return env.getDatabase(databaseName).map(database -> {
            try {
                database.write(tableName, key, value);
                return DefaultDatabaseCommandResult.success(
                        String.format("Key \"%s\" has value \"%s\" in table \"%s\" in database \"%s\"",
                                key,
                                value,
                                tableName,
                                databaseName)
                );
            } catch (DatabaseException ex) {
                return DefaultDatabaseCommandResult.error(ex.getMessage());
            }
        }).orElseGet(() ->
                DefaultDatabaseCommandResult.error(
                        String.format("Database with name \"%s\" doesn't exist", databaseName)
                )
        );
    }
}
