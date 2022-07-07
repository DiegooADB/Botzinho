package me.diego.botzinho.commands.misc;

import me.diego.botzinho.annotations.CommandDescription;
import me.diego.botzinho.annotations.CommandName;
import me.diego.botzinho.commands.Command;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@CommandName("ping")
@CommandDescription("retrieve bot ping")
public final class Ping extends Command {
    @Override
    public void handle(MessageReceivedEvent event) {
        event.getMessage().reply("hamood").queue();
    }
}
