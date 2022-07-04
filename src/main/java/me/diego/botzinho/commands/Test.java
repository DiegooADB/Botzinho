package me.diego.botzinho.commands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Test {
    public Test(MessageReceivedEvent event, String[] args) {
        if (args.length == 1) {
            event.getTextChannel().sendMessage("Hamood").queue();
        } else {
            event.getTextChannel().sendMessage("Hamood " + args[1]).queue();
        }
    }
}
