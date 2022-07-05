package me.diego.botzinho.handler;

import me.diego.botzinho.Teste;
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
    public final HashMap<String, Command> getCommands() { return commands; }

    private static CommandHandler instance;
    public static CommandHandler getInstance() {
        if (instance == null) instance = new CommandHandler();
        return instance;
    }

    public void init() {
        registerCommand();
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        String commandRaw = event.getMessage().getContentRaw();
        String[] cmdArgs = commandRaw.substring(1).split(" ");

        if(!commandRaw.startsWith("!")) return;

        Command command = commands.get(cmdArgs[0]);
        command.handle(event);
    }

    public void registerCommand() {
        Reflections reflections = new Reflections(Teste.class.getPackage().getName() + ".commands");
        Set<Class<? extends Command>> classes = reflections.getSubTypesOf(Command.class);

        for (Class<? extends Command> claz : classes) {
            try {
                Command command = claz.getDeclaredConstructor().newInstance();

                String[] groupNameSplit = claz.getName().toLowerCase().split("\\.");
                String className = groupNameSplit[groupNameSplit.length - 1];

                classes.forEach(e -> commands.put(className, command));
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
