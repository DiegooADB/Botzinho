package me.diego.botzinho.commands.handler;

import me.diego.botzinho.Botzinho;
import me.diego.botzinho.commands.Command;
import me.diego.botzinho.config.ConfigManager;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class EventHandler extends ListenerAdapter {
    public void init() {
        CommandHandler.getInstance().registerCommand();
    }

    private final String prefix = ConfigManager.getInstance().getPrefix();
    private final HashMap<String, Command> commands = CommandHandler.getInstance().getCommands();

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getAuthor().isBot() || event.isWebhookMessage()) return;

        String commandRaw = event.getMessage().getContentRaw();
        if (!commandRaw.startsWith(prefix)) return;

        String[] args = commandRaw.split(" ");
        String cmdUsed = args[0].replace(prefix, "");

        String guildName;
        if (event.isFromGuild()) {
            guildName = event.getGuild().getName();
        } else {
            guildName = "(DM)";
        }

        if (ConfigManager.getInstance().isOnDebugMode()) {
            Botzinho.logger.info("User: %s | Guild: %s | Command: %s | Command type: Message".formatted(event.getAuthor().getAsTag(), guildName, commandRaw));
        }

        Command command = commands.get(cmdUsed);
        if (command == null) return;

        command.messageHandle(event);
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        String guildName;
        if (event.isFromGuild()) {
            guildName = event.getGuild().getName();
        } else {
            guildName = "(DM)";
        }

        if(ConfigManager.getInstance().isOnDebugMode()) {
            Botzinho.logger.info("User: %s | Guild: %s | Command: %s | Command type: Slash"
                    .formatted(event.getMember().getUser().getAsTag(), guildName, event.getCommandPath()));
        }

        Command command = commands.get(event.getCommandPath());
        command.slashHandle(event);
    }

    private static EventHandler instance;
    public static EventHandler getInstance() {
        if (instance == null) instance = new EventHandler();
        return instance;
    }
}
