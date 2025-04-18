package by.dima.model.server;

import by.dima.model.data.command.model.CommandManager;

public interface Serverable {
    void startServer();

    void setCommandManager(CommandManager commandManager);
}
