package me.diego.botzinho.commands.help;

import me.diego.botzinho.Botzinho;
import me.diego.botzinho.annotations.CommandDescription;
import me.diego.botzinho.annotations.CommandName;
import me.diego.botzinho.annotations.DevCommand;
import me.diego.botzinho.commands.Command;
import me.diego.botzinho.commands.handler.CommandHandler;
import me.diego.botzinho.config.ConfigManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.apache.commons.text.CaseUtils;

import java.awt.*;
import java.util.*;
import java.util.List;

@CommandName("help")
@CommandDescription("return all commands and descriptions")
@DevCommand
public class Help extends Command {
    private final HashMap<String, Command> commands = CommandHandler.getInstance().getCommands();
    private final String prefix = ConfigManager.getInstance().getPrefix();
    private final HashMap<String, List<String>> groups = new HashMap<>();

    @Override
    public void messageHandle(MessageReceivedEvent event) {
        EmbedBuilder eb = commandResponse();
        event.getChannel().sendMessageEmbeds(eb.build()).setActionRows(ActionRow.of(getButtons())).queue();
    }

    @Override
    public void slashHandle(SlashCommandInteractionEvent event) {
        EmbedBuilder eb = commandResponse();
        event.deferReply().queue();
        event.getHook().sendMessageEmbeds(eb.build()).addActionRow(getButtons()).queue();
    }

    private EmbedBuilder commandResponse() {
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
}
