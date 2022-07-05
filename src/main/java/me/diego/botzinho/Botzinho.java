package me.diego.botzinho;

import io.github.cdimascio.dotenv.Dotenv;
import me.diego.botzinho.handler.CommandHandler;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.json.simple.parser.ParseException;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

public class Botzinho {
    public static JDA jda;
    private static final HashMap<String, Set<String>> commands = new HashMap<>();

    public Botzinho() throws IOException, ParseException, LoginException, InterruptedException {
        Dotenv dotenv = null;
        dotenv = Dotenv.configure().load();

        jda = JDABuilder
                .createDefault(dotenv.get("TOKEN"))
                .build().awaitReady();

        onStart();
    }

    private static void onStart() throws IOException, ParseException {
        //jda.addEventListener(new CommandExecutor());
        CommandHandler.getInstance().init();
        jda.addEventListener(CommandHandler.getInstance());
//        Guilds.showGuilds();
//        commands.putAll(CommandReader.readCommands());
//        prefix.setPrefix(CommandReader.readConfig("prefix"));

    }

    public static HashMap<String, Set<String>> getCommands() {
        return commands;
    }

    public static void main(String[] args) {
        try {
            new Botzinho();
        } catch (LoginException | IOException | ParseException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
