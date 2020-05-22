package ru.andrey.kvstorage.console.commands;

import ru.andrey.kvstorage.console.DatabaseCommand;
import ru.andrey.kvstorage.console.DatabaseCommandResult;
import ru.andrey.kvstorage.console.ExecutionEnvironment;
import ru.andrey.kvstorage.exception.DatabaseException;

import static ru.andrey.kvstorage.console.DatabaseCommandResult.DefaultDatabaseCommandResult;

public class ReadKeyCommand implements DatabaseCommand {
    private final ExecutionEnvironment env;
    private final String databaseName;
    private final String tableName;
    private final String key;

    public ReadKeyCommand(ExecutionEnvironment env,
                   String databaseName,
                   String tableName,
                   String key) {
        this.env = env;
        this.databaseName = databaseName;
        this.tableName = tableName;
        this.key = key;
    }

    @Override
    public DatabaseCommandResult execute() {
        return env.getDatabase(databaseName).map(database -> {
                    try {
                        return DefaultDatabaseCommandResult.success(database.read(tableName, key));
                    } catch (DatabaseException e) {
                        return DefaultDatabaseCommandResult.error(e.getMessage());
                    }
                }
        ).orElseGet(() -> DefaultDatabaseCommandResult.error(
                String.format("Database with name \"%s\" doesn't exist", databaseName)
                )
        );
    }
}
