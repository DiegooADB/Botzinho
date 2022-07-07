package me.diego.botzinho.handler;

import me.diego.botzinho.Teste;
import me.diego.botzinho.annotations.CommandDescription;
import me.diego.botzinho.annotations.CommandName;
import me.diego.botzinho.commands.Command;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Set;

public class CommandHandler extends ListenerAdapter {
    private final HashMap<String, Command> commands = new HashMap<>();

    public final HashMap<String, Command> getCommands() {
        return commands;
    }

    private static CommandHandler instance;

    public static CommandHandler getInstance() {
        if (instance == null) instance = new CommandHandler();
        return instance;
    }

    private static final String prefix = "!";

    public void init() {
        registerCommand();
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        String commandRaw = event.getMessage().getContentRaw();
        String[] args = commandRaw.split(" ");
        String cmdUsed = args[0].replace(prefix, "");

        if (event.getAuthor().isBot() ||
                !commandRaw.startsWith(prefix)) return;

        Command command = commands.get(cmdUsed);

        if (command == null) return;

        command.handle(event);
    }

    public void registerCommand() {
        Reflections reflections = new Reflections(Teste.class.getPackage().getName() + ".commands");
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

                classes.forEach(e -> commands.put(commandName, command));
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                     InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
