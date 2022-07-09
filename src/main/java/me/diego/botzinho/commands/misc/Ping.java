package me.diego.botzinho.commands.misc;

import me.diego.botzinho.Botzinho;
import me.diego.botzinho.annotations.CommandDescription;
import me.diego.botzinho.annotations.CommandName;
import me.diego.botzinho.annotations.DevCommand;
import me.diego.botzinho.commands.Command;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@CommandName("ping")
@CommandDescription("retrieve bot ping")
@DevCommand
public final class Ping extends Command {
    @Override
    public void messageHandle(MessageReceivedEvent event) {
        event.getMessage().reply(Botzinho.jda.getGatewayPing() + " ms").queue();
    }

    @Override
    public void slashHandle(SlashCommandInteractionEvent event) {
        event.deferReply().queue();
        event.getHook().sendMessage(Botzinho.jda.getGatewayPing() + " ms").setEphemeral(true).queue();
    }
}
