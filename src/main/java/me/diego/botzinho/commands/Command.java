package me.diego.botzinho.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.ArrayList;
import java.util.List;

public abstract class Command {
    public abstract void messageHandle(MessageReceivedEvent event);
    public abstract void slashHandle(SlashCommandInteractionEvent event);
    private String description;
    private String group;
    private boolean devCommand = false;
    private final List<OptionData> options = List.of();
    private final List<String> aliases = new ArrayList<>();

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public boolean isDevCommand() {
        return devCommand;
    }

    public void setDevCommand() {
        this.devCommand = true;
    }

    public List<OptionData> getOptions() {
        return options;
    }

    public List<String> getAliases() {
        return aliases;
    }

    public void addAliases(String aliasesName) {
        aliases.add(aliasesName);
    }

}
