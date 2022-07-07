package me.diego.botzinho.commands.misc;

import me.diego.botzinho.annotations.CommandDescription;
import me.diego.botzinho.annotations.CommandName;
import me.diego.botzinho.commands.Command;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@CommandName("test")
@CommandDescription("only test command")
public class Test extends Command {

    @Override
    public void handle(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");

        event.getMessage().reply(args[1]).queue();
    }
}
