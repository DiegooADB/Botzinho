package me.diego.botzinho;

import io.github.cdimascio.dotenv.Dotenv;
import me.diego.botzinho.scripts.CommandExecutor;
import me.diego.botzinho.debug.Guilds;
import me.diego.botzinho.scripts.CommandReader;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.json.simple.parser.ParseException;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.util.*;

public class Botzinho {

    public static JDA jda;
    private static final Set<String> commands = new HashSet<>();

    public static void main(String[] args) throws LoginException, IOException, ParseException {
        Dotenv dotenv = null;
        dotenv = Dotenv.configure().load();

        jda = JDABuilder.create(dotenv.get("TOKEN"),
                EnumSet.allOf(GatewayIntent.class)).build();

        jda.addEventListener(new CommandExecutor());
        Guilds.showGuilds();
        onStart();
    }

    public static void onStart() throws IOException, ParseException {
        commands.addAll(CommandReader.readJson());
    }

    public static Set<String> getCommands() {
        return commands;
    }
}
