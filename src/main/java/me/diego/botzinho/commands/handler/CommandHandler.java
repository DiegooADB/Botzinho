package me.diego.botzinho.commands.handler;

import me.diego.botzinho.Teste;
import me.diego.botzinho.annotations.CommandDescription;
import me.diego.botzinho.annotations.CommandName;
import me.diego.botzinho.annotations.DevCommand;
import me.diego.botzinho.commands.Command;
import me.diego.botzinho.config.ConfigManager;
import net.dv8tion.jda.api.entities.Guild;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Set;

import static me.diego.botzinho.Botzinho.jda;

public class CommandHandler {
    private final HashMap<String, Command> commands = new HashMap<>();
    public final HashMap<String, Command> getCommands() {
        return commands;
    }

    private static CommandHandler instance;
    public static CommandHandler getInstance() {
        if (instance == null) instance = new CommandHandler();
        return instance;
    }

    public void registerCommand() {
        Reflections reflections = new Reflections(Command.class.getPackage().getName());
        Set<Class<? extends Command>> classes = reflections.getSubTypesOf(Command.class);

        for (Class<? extends Command> claz : classes) {
            try {
                Command command = claz.getDeclaredConstructor().newInstance();
                String commandName = claz.getAnnotation(CommandName.class).value();

                String groupName = claz.getPackage().getName();
                String[] groups = groupName.split("\\.");
                command.setGroup(groups[groups.length - 1]);

                String commandDescription = claz.getAnnotation(CommandDescription.class).value();
                command.setDescription(commandDescription);

                if (claz.getAnnotation(DevCommand.class) != null) {
                    command.setDevCommand();
                }

                classes.forEach(e -> commands.put(commandName, command));

                if(command.isDevCommand()) {
                    Guild testServer = jda.getGuildById(ConfigManager.getInstance().getDevServerId());
                    if(testServer == null) return;
                    testServer.upsertCommand(commandName, commandDescription).queue();
                }
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                     InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
