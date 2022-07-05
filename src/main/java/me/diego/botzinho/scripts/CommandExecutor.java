package me.diego.botzinho.scripts;

import me.diego.botzinho.Botzinho;
import me.diego.botzinho.config.Prefix;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CommandExecutor extends ListenerAdapter {
    private final HashMap<String, Set<String>> commands = Botzinho.getCommands();
    private final String prefix = Botzinho.getPrefix().toString();

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");

        String path = "me.diego.botzinho.commands.";

        String commandToFind = args[0].replace(prefix, "");

        System.out.println(prefix);
        System.out.println(commandToFind);
        System.out.println(commands);
        System.out.println(commands.values().stream().anyMatch(list -> list.contains(commandToFind)));

        if (commands.values().stream().anyMatch(list -> list.contains(commandToFind))) {
            try {
                Class<?> cls = Class.forName(path + getKey(commands, commandToFind));
                cls.getDeclaredConstructor(MessageReceivedEvent.class, String[].class).newInstance(event, args);

            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

//    private String formatString(String string) {
//        return string.substring(0, 1).toUpperCase() + string.substring(1);
//    }

    public static String getKey(HashMap<String, Set<String>> commands, String str) {
        for (Map.Entry<String, Set<String>> entry : commands.entrySet()) {
            Set<String> list = entry.getValue();
            String key = entry.getKey();

            if(list.contains(str)) {
                return key;
            }
        }
        return null;
    }
}
