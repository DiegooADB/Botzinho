package me.diego.botzinho.commands.info;

import me.diego.botzinho.Botzinho;
import me.diego.botzinho.annotations.Aliases;
import me.diego.botzinho.annotations.CommandDescription;
import me.diego.botzinho.annotations.CommandName;
import me.diego.botzinho.commands.Command;
import me.diego.botzinho.config.ConfigManager;
import me.diego.botzinho.handler.CommandHandler;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.apache.commons.text.CaseUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringJoiner;

@CommandName("help")
@CommandDescription("Provides you with the commands list.\n" +
        "Do !help <command> for extended command info.")
@Aliases({"cmds", "commands"})
public final class Help extends Command {
    private final HashMap<String, Command> commands = CommandHandler.getInstance().getCommands();
    private final String prefix = ConfigManager.getInstance().getPrefix();
    private final HashMap<String, List<String>> groups = new HashMap<>();
    private final List<String> aliases = new ArrayList<>();

    public List<OptionData> getOptions() {
        return List.of();
    }

    @Override
    public void messageHandle(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");
        if (args.length == 1) {
            EmbedBuilder eb = response();
            event.getChannel().sendMessageEmbeds(eb.build()).setActionRows(ActionRow.of(getButtons())).queue();
        } else {
            EmbedBuilder eb = commandResponse(args[1]);
            event.getChannel().sendMessageEmbeds(eb.build()).queue();
        }
    }

    @Override
    public void slashHandle(SlashCommandInteractionEvent event) {
        EmbedBuilder eb = response();
        event.deferReply(true).queue();
        event.getHook().sendMessageEmbeds(eb.build()).addActionRow(getButtons()).queue();
    }

    private EmbedBuilder response() {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Command List");
        eb.setAuthor("Command List", null, Botzinho.jda.getSelfUser().getAvatarUrl());

        addCommandList();
        groups.forEach((groupName, commandList) -> {
            eb.addField(":hammer_pick:" + CaseUtils.toCamelCase(groupName, true),
                    listToStringFormatter(commandList), false);
        });

        eb.setColor(new Color(206, 129, 45));
        eb.setFooter("To get more info on command, do %shelp command name".formatted(prefix));
        return eb;
    }

    private EmbedBuilder commandResponse(String arg) {
        Command command = commands.get(arg);
        EmbedBuilder eb = new EmbedBuilder();
        if (command == null ) {
            eb.setTitle(":x: Command not found");
            eb.setColor(Color.red);
        } else {
            eb.addField(arg, command.getDescription(), false);
            StringJoiner joiner = new StringJoiner(", ");
            command.getAliases().forEach(aliase -> {
                joiner.add("`%s`".formatted(aliase));
            });
            eb.addField("Aliases", joiner.toString(), false);
        }
        return eb;
    }

    private List<Button> getButtons() {
        List<Button> buttons = new ArrayList<>();
        buttons.add(Button.link("https://discord.com/invite/loud", "Support server"));
        buttons.add(Button.link("https://discord.com/api/oauth2/authorize?client_id=689154602658168853&permissions=8&scope=bot", "Bot invite"));
        return buttons;
    }

    private void addCommandList() {
        if (!groups.isEmpty()) return;
        commands.forEach((commandName, command) -> {
            if (!command.isDevCommand()) {
                List<String> commandList = groups.get(command.getGroup());
                if (commandList == null) {
                    commandList = new ArrayList<>();
                }
                commandList.add(commandName);
                groups.put(command.getGroup(), commandList);
            }
        });
    }

    private String listToStringFormatter(List<String> commandList) {
        StringJoiner joiner = new StringJoiner(",");

        commandList.forEach(commandName -> {
            joiner.add("`%s`".formatted(commandName));
        });
        return joiner.toString();
    }

    public void addAliases(String aliasesName) {
        aliases.add(aliasesName);
    }

    public List<String> getAliases() {
        return aliases;
    }
}
