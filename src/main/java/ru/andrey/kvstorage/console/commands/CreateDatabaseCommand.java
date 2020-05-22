package ru.andrey.kvstorage.console.commands;

import ru.andrey.kvstorage.console.DatabaseCommand;
import ru.andrey.kvstorage.console.DatabaseCommandResult;
import ru.andrey.kvstorage.console.ExecutionEnvironment;
import ru.andrey.kvstorage.logic.Database;

import static ru.andrey.kvstorage.console.DatabaseCommandResult.DefaultDatabaseCommandResult;

public class CreateDatabaseCommand implements DatabaseCommand {
    private final ExecutionEnvironment env;
    private final String databaseName;
    private final Database database;

    public CreateDatabaseCommand(ExecutionEnvironment env, String databaseName, Database database) {
        this.env = env;
        this.databaseName = databaseName;
        this.database = database;
    }

    @Override
    public DatabaseCommandResult execute() {
        return env.getDatabase(databaseName).map(x ->
                DefaultDatabaseCommandResult.error(
                        String.format("Database with name \"%s\" already exists", databaseName)
                )
        ).orElseGet(() -> {
            env.addDatabase(database);
            return DefaultDatabaseCommandResult.success(
                    String.format("Database \"%s\" is created successfully", databaseName)
            );
        });
    }
}
