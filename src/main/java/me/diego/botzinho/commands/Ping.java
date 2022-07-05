package me.diego.botzinho.commands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;


public final class Ping extends Command {
    @Override
    public void handle(MessageReceivedEvent event) {
        event.getMessage().reply("hamood").queue();
    }
}
