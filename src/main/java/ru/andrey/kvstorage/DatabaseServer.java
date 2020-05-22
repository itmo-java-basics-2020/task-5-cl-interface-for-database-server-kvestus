package ru.andrey.kvstorage;

import ru.andrey.kvstorage.console.DatabaseCommandResult;
import ru.andrey.kvstorage.console.DatabaseCommands;
import ru.andrey.kvstorage.console.ExecutionEnvironment;
import ru.andrey.kvstorage.exception.DatabaseException;

import java.util.Arrays;

public class DatabaseServer {

    private final ExecutionEnvironment env;

    public DatabaseServer(ExecutionEnvironment env) {
        this.env = env;
    }

    public static void main(String[] args) {

    }

    DatabaseCommandResult executeNextCommand(String commandText) {
        try {
            if (commandText == null) {
                throw new IllegalArgumentException();
            }
            String[] arrayCommand = commandText.split(" ");
            String command = arrayCommand[0];
            String[] args = Arrays.copyOfRange(arrayCommand, 1, arrayCommand.length);
            return DatabaseCommands.valueOf(command).getCommand(env, args).execute();
        } catch (IllegalArgumentException ex) {
            return DatabaseCommandResult.DefaultDatabaseCommandResult.error(
                    String.format("Command doesn't exist")
            );
        } catch (DatabaseException ex) {
            return DatabaseCommandResult.DefaultDatabaseCommandResult.error(ex.getMessage());
        }
    }
}
