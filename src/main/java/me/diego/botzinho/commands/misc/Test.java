package me.diego.botzinho.commands.misc;

import me.diego.botzinho.annotations.CommandDescription;
import me.diego.botzinho.annotations.CommandName;
import me.diego.botzinho.annotations.DevCommand;
import me.diego.botzinho.commands.Command;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@CommandName("test")
@CommandDescription("only test command")
@DevCommand
public class Test extends Command {
    @Override
    public void messageHandle(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");

        event.getMessage().reply(args[1]).queue();
    }

    @Override
    public void slashHandle(SlashCommandInteractionEvent event) {
        event.deferReply().queue();
        event.getHook().sendMessage("hamood").queue();
    }
}
