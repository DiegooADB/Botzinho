package me.diego.botzinho.commands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public abstract class Command {
    public abstract void handle(MessageReceivedEvent event);
    private String description;
    private String group;

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
}
