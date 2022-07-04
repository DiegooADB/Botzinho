package me.diego.botzinho.commands;

import me.diego.botzinho.Botzinho;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class Ping {
    public Ping(MessageReceivedEvent event, String[] args) {
        event.getTextChannel().sendMessage(Botzinho.jda.getGatewayPing() + " ms").queue();
    }
}
