package me.diego.botzinho.commands;

import me.diego.botzinho.Botzinho;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Ping {
    public Ping(MessageReceivedEvent event, String[] args) {
        event.getTextChannel().sendMessage(Botzinho.jda.getGatewayPing() + " ms").queue();
    }
}
