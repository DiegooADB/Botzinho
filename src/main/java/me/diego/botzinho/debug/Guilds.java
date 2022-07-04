package me.diego.botzinho.debug;

import me.diego.botzinho.Botzinho;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static me.diego.botzinho.Botzinho.jda;

public class Guilds {
    private static Logger logger = LoggerFactory.getLogger(Botzinho.class);
    public static Map<Long, String> mapGuildName = new HashMap<>();

    public static void showGuilds() {
        try {
            jda.awaitReady().getGuilds().forEach(guild -> {
                mapGuildName.put(guild.getIdLong(), guild.getName());
                logger.info("Botzinho is on server %d %s".formatted(guild.getIdLong(), guild.getName()));
            });
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
