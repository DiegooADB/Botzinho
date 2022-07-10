package me.diego.botzinho.handler;

import me.diego.botzinho.annotations.Aliases;
import me.diego.botzinho.annotations.CommandDescription;
import me.diego.botzinho.annotations.CommandName;
import me.diego.botzinho.annotations.DevCommand;
import me.diego.botzinho.commands.Command;
import me.diego.botzinho.config.ConfigManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.requests.restaction.CommandCreateAction;
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

                if (claz.getAnnotation(Aliases.class) != null) {
                    for (String aliase : claz.getAnnotation(Aliases.class).value()) {
                        command.addAliases(aliase);
                    }
                }

                classes.forEach(e -> commands.put(commandName, command));

                if (ConfigManager.getInstance().isOnDevMode()) {
                    Guild testServer = jda.getGuildById(ConfigManager.getInstance().getDevServerId());
                    if (testServer == null) return;
                    CommandCreateAction commandCreateAction = testServer.upsertCommand(commandName, commandDescription);
                    if (command.getOptions().size() > 0) {
                        command.getOptions().forEach(data -> {
                            commandCreateAction.addOption(
                                    data.getType(),
                                    data.getName(),
                                    data.getDescription(),
                                    data.isRequired(),
                                    data.isAutoComplete()).queue();
                        });
                    } else {
                        testServer.upsertCommand(commandName, commandDescription).queue();
                    }
                } else {
                    jda.upsertCommand(commandName, commandDescription).queue();
                }
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                     InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
