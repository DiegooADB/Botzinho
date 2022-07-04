package me.diego.botzinho.scripts;

import me.diego.botzinho.Botzinho;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

public class CommandExecutor extends ListenerAdapter {
    //TODO create a commandReader that get all commands inside a config.yml, and loads when application start in string array and verify if commands exists

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        String prefix = "!";
        String[] args = event.getMessage().getContentRaw().split(" ");

        String path = "me.diego.botzinho.commands.";
        Set<String> commands = Botzinho.getCommands();

        String commandToFind = args[0].replace(prefix, "");

        if (commands.contains(commandToFind)) {
            try {
                Class<?> cls = Class.forName(path + formatString(commandToFind));
                cls.getDeclaredConstructor(MessageReceivedEvent.class, String[].class).newInstance(event, args);

            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private String formatString(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }
}
