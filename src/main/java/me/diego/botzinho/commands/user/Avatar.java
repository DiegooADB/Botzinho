package me.diego.botzinho.commands.user;

import me.diego.botzinho.Botzinho;
import me.diego.botzinho.annotations.CommandDescription;
import me.diego.botzinho.annotations.CommandName;
import me.diego.botzinho.commands.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.requests.RestAction;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@CommandName("avatar")
@CommandDescription("retrieve avatar image")
public final class Avatar extends Command {
    private final List<OptionData> options = new ArrayList<>();

    public List<OptionData> getOptions() {
        if(!options.isEmpty()) return options;
        options.add(new OptionData(OptionType.USER, "user", "hamood", false, false));
        return options;
    }

    @Override
    public void messageHandle(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");

        User user = args.length == 1 ?
                event.getAuthor() :
                Botzinho.jda.getUserById(args[1].replace("<@", "").replace(">", ""));
        EmbedBuilder eb = commandResponse(user);
        event.getMessage().replyEmbeds(eb.build())
                .setActionRow(Button.link(user.getAvatarUrl() + "?size=2048", "Abrir avatar no navegador"))
                .queue();
    }

    @Override
    public void slashHandle(SlashCommandInteractionEvent event) {
        OptionMapping option = event.getOption("user");
        User user = option == null ? event.getUser() : option.getAsUser();
        EmbedBuilder eb = commandResponse(user);
        event.deferReply().queue();
        event.getHook().sendMessageEmbeds(eb.build())
                .addActionRow(Button.link(user.getAvatarUrl() + "?size=2048", "Abrir avatar no navegador"))
                .queue();
    }

    private EmbedBuilder commandResponse(User user) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle(":frame_photo: %s".formatted(user.getName()));
        eb.setImage(user.getAvatarUrl() + "?size=2048");
        eb.setColor(new Color(0,133,216));
        return eb;
    }
}
