package me.diego.botzinho.commands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Collections;
import java.util.Set;

public abstract class Command {
    String name;
    String description;
    Set<String> aliases = Collections.emptySet();

    public abstract void handle(MessageReceivedEvent event);
}
