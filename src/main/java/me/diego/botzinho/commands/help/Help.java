package me.diego.botzinho.commands.help;

import me.diego.botzinho.Botzinho;
import me.diego.botzinho.annotations.CommandDescription;
import me.diego.botzinho.annotations.CommandName;
import me.diego.botzinho.commands.Command;
import me.diego.botzinho.handler.CommandHandler;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.apache.commons.text.CaseUtils;

import java.awt.*;
import java.util.*;
import java.util.List;

@CommandName("help")
@CommandDescription("return all commands and descriptions")
public class Help extends Command {
    private final HashMap<String, Command> commands = CommandHandler.getInstance().getCommands();
    private final String prefix = "!";
    private final HashMap<String, List<String>> groups = new HashMap<>();

    @Override
    public void handle(MessageReceivedEvent event) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Command List");
        eb.setAuthor("Command List", null, Botzinho.jda.getSelfUser().getAvatarUrl());

        addCommandList();
        groups.forEach((groupName, commandList) -> {
            eb.addField(CaseUtils.toCamelCase(groupName, true), listToStringFormatter(commandList), false);
        });

        eb.setColor(new Color(206, 129, 45));
        eb.setFooter("To get more info on command, do !help command name");
        event.getChannel().sendMessageEmbeds(eb.build()).queue();
    }

    private void addCommandList() {
        if (!groups.isEmpty()) return;
        commands.forEach((commandName, command) -> {
            List<String> commandList = groups.get(command.getGroup());
            if (commandList == null) {
                commandList = new ArrayList<>();
            }
            commandList.add(commandName);
            groups.put(command.getGroup(), commandList);
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
