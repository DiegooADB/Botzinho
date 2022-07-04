package me.diego.botzinho;

import me.diego.botzinho.commands.Ping;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Teste {
    static JSONParser parser = new JSONParser();

    public static void main(String[] args) throws IOException, ParseException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        /*try (Stream<Path> paths = Files.walk(Paths.get("./src/main/java/me/diego/botzinho/commands"))) {
            String commandUsed = "ping";
            Optional<File> first = paths
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .filter(file -> file.getName().equalsIgnoreCase(commandUsed+".java"))
                    .findAny();

            System.out.println(first);
        }

        String path = "./config.json";

        JSONObject object = new JSONObject(new FileReader("json/config.json"));
        System.out.println(object);
        JSONObject arr = object.getJSONObject("commands");
        System.out.println(arr);

        try (InputStream is = Teste.class.getResourceAsStream("json/config.json")) {

        }
        ClassLoader classLoader = Teste.class.getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource("config.json")).getFile());

        Object obj = parser.parse(new FileReader(file));
        JSONObject jsonObject = (JSONObject) obj;
        JSONObject o = (JSONObject) jsonObject.get("commands");
        System.out.println(o.keySet()); // each entry set name

        ClassLoader classLoader = JsonReader.class.getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource("config.json")).getFile());

        Object obj = parser.parse(new FileReader(file));
        JSONObject jsonFile = (JSONObject) obj;
        JSONObject commands = (JSONObject) jsonFile.get("commands");
        @SuppressWarnings("unchecked")
        Set<String> set = (Set<String>) commands.keySet();

        ClassLoader classLoader = JsonReader.class.getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource("config.json")).getFile());

        Object obj = parser.parse(new FileReader(file));
        JSONObject jsonFile = (JSONObject) obj;
        JSONObject commands = (JSONObject) jsonFile.get("commands");
        Set set = commands.keySet();


        Set<String> string = new HashSet<>();

        set.forEach(command -> {
            string.add(command.toString());
        });

        List<String> str = List.of("hamood", "test", "ping", "habibi");

        StringJoiner msg = new StringJoiner(", ");

        str.forEach(msg::add);

        System.out.println(msg);
         */

        List<String> strs = List.of("hamood", "test", "Ping", "!habibi");

        String str = strs.get(3);
        String path = "me.diego.botzinho.commands.";

//        Class cls = Class.forName(path + s);

//        cls.getDeclaredConstructor(MessageReceivedEvent.class, String.class).newInstance();

//        System.out.println(str.replace("!", ""));
//        String cap = str.substring(0, 1).toUpperCase() + str.substring(1);
//        System.out.println(cap);

        System.out.println(formatString(str));
    }

    private static String formatString(String string) {
        String newString = string.replace("!", "");
        return newString.substring(0, 1).toUpperCase() + newString.substring(1);
    }
}
