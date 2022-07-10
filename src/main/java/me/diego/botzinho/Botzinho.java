package me.diego.botzinho;

import io.github.cdimascio.dotenv.Dotenv;
import me.diego.botzinho.handler.EventHandler;
import me.diego.botzinho.config.ConfigManager;
import me.diego.botzinho.debug.Guilds;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.json.simple.parser.ParseException;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.util.logging.Logger;

public class Botzinho {
    public static JDA jda;
    public static final Logger logger = Logger.getLogger("Botzinho");

    public Botzinho() throws IOException, ParseException, LoginException, InterruptedException {
        Dotenv dotenv = null;
        dotenv = Dotenv.configure().load();

        jda = JDABuilder
                .createDefault(dotenv.get("TOKEN"))
                .build().awaitReady();

        onStart();
    }

    private static void onStart() {
        EventHandler.getInstance().init();
        jda.addEventListener(EventHandler.getInstance());
        ConfigManager.getInstance();
        Guilds.showGuilds();
    }

    public static void main(String[] args) {
        try {
            new Botzinho();
        } catch (LoginException | IOException | ParseException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
