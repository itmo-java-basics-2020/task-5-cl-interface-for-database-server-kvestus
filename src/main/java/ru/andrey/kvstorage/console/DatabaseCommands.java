package ru.andrey.kvstorage.console;

import ru.andrey.kvstorage.console.commands.CreateDatabaseCommand;
import ru.andrey.kvstorage.console.commands.CreateTableCommand;
import ru.andrey.kvstorage.console.commands.ReadKeyCommand;
import ru.andrey.kvstorage.console.commands.UpdateKeyCommand;
import ru.andrey.kvstorage.exception.DatabaseException;
import ru.andrey.kvstorage.logic.DefaultDatabase;

public enum DatabaseCommands {
    CREATE_DATABASE {
        @Override
        public DatabaseCommand getCommand(ExecutionEnvironment env, String... args) throws DatabaseException {
            if (args.length != 1) {
                throw new DatabaseException("Wrong amount of arguments");
            }
            return new CreateDatabaseCommand(env, args[0], new DefaultDatabase());
        }
    },
    CREATE_TABLE {
        @Override
        public DatabaseCommand getCommand(ExecutionEnvironment env, String... args) throws DatabaseException {
            if (args.length != 2) {
                throw new DatabaseException("Wrong amount of arguments");
            }
            return new CreateTableCommand(env, args[0], args[1]);
        }
    },
    UPDATE_KEY {
        @Override
        public DatabaseCommand getCommand(ExecutionEnvironment env, String... args) throws DatabaseException {
            if (args.length != 4) {
                throw new DatabaseException("Wrong amount of arguments");
            }
            return new UpdateKeyCommand(env, args[0], args[1], args[2], args[3]);
        }
    },
    READ_KEY {
        @Override
        public DatabaseCommand getCommand(ExecutionEnvironment env, String... args) throws DatabaseException {
            if (args.length != 3) {
                throw new DatabaseException("Wrong amount of arguments");
            }
            return new ReadKeyCommand(env, args[0], args[1], args[2]);
        }
    };

    public abstract DatabaseCommand getCommand(ExecutionEnvironment env, String... args) throws DatabaseException;
}
