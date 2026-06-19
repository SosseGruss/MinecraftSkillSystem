package io.github.sossegruss.minecraftSkillSystem.manager;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import io.github.sossegruss.minecraftSkillSystem.MinecraftSkillSystem;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ResourceXPmanager {
    private static final Map<String, Double> MININGXP = loadMap("skillValidSource/miningXPblocks.properties");
    private static final Map<String, Double> FARMINGXPBREAK = loadMap("skillValidSource/farmingXPinteractive/cropsGrantingXP.properties");
    private static final Map<String, Double> FARMINGXPHAREST = loadMap("skillValidSource/farmingXPinteractive/cropsGrantXPonHarvest.properties");
    private static final Map<String, Double> COMBATXP = loadMapfromJson("skillValidSource/mobsGiveingXP.json");
    private static final Map<String, Double> FORAGINGXP = loadMap("skillValidSource/foragingXPblocks.properties");

    private static Map<String, Double> loadMap(String path) {
        InputStream input = ResourceXPmanager.class.getClassLoader().getResourceAsStream(path);
        Properties xpTranslation = new Properties();
        try {
            xpTranslation.load(input);
        } catch (IOException e) {
            return Map.of();
        }

        Map<String, Double> resourceToXP = new HashMap<>();
        xpTranslation.forEach((key, value) -> {
            try {
                resourceToXP.put(key.toString(), Double.parseDouble(value.toString()));
            } catch (NumberFormatException e) {
                MinecraftSkillSystem.getInstanz().getSLF4JLogger().warn("File " + path + " contains not a number");
            }
        });
        return resourceToXP;

    }

    private static Map<String, Double> loadMapfromJson(String path){

        try {
            String json = Files.readString(Path.of(path));
            Type mapType = new TypeToken<Map<String, Double>>(){}.getType();
            return new Gson().fromJson(json, mapType);
        } catch (IOException e) {
            return new HashMap<>();
        }


    }

    public static Map<String, Double> getMiningxp(){
        return MININGXP;
    }
    public static Map<String, Double> getFarmingxpbreak(){
        return FARMINGXPBREAK;
    }
    public static Map<String, Double> getFarmingxpharest(){
        return FARMINGXPHAREST;
    }
    public static Map<String, Double> getCombatxp(){
        return COMBATXP;
    }
    public static Map<String, Double> getForagingxp(){
        return FORAGINGXP;
    }
}
