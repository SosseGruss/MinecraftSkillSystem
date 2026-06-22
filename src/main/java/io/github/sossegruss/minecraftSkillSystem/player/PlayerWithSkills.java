package io.github.sossegruss.minecraftSkillSystem.player;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import io.github.sossegruss.minecraftSkillSystem.MinecraftSkillSystem;
import io.github.sossegruss.minecraftSkillSystem.skill.SkillType;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * The class represents a player with an UUID and also conntains their Skill XP
 *
 * @author phack
 * @version 16.06.2026
 */
public class PlayerWithSkills {
    private final UUID uuid;
    private final Map<SkillType, Double> skills = new HashMap<>(); //HashMap in order to save the players Skill XP while the server is running
    private final Map<SkillType, Integer> skillLevel = new HashMap<>();
    private static final long[] LEVEL_THRASHHOLD = {0, 500, 1200, 2180, 3550, 5470, 8160, 11920, 17190, 24560,
                                                    34880, 49330, 69560, 97880, 137530, 193040, 270750, 379550, 531870, 745120, 1050000, 1500000, 2100000, 3000000,
                                                    Long.MAX_VALUE};

    private final Path xpFolder = Path.of("src/main/resources/playerdata/XP");
    private final Path lvlFolder = Path.of("src/main/resources/playerdata/LVL");

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();


    public PlayerWithSkills(UUID uuid) {
        this.uuid = uuid;
    }

    /**
     * adds certain amount of XP to the total of a player Skill XP for a certain skill
     *
     * @param addXP     the amont of XP added to the total
     * @param skillType wich skills XP gets increased
     */
    public void addSkillXP(SkillType skillType, double addXP) {
        double currentXP = this.skills.get(skillType) + addXP;
        int level = 1;

        for(int i = 1; i < LEVEL_THRASHHOLD.length; i++){
            if(currentXP >= LEVEL_THRASHHOLD[i]){
                level = i + 1;
            }
            else{
                break;
            }
        }
        skillLevel.put(skillType, level);


    }

    /**
     * returns the total amount of XP for a certain skill
     *
     * @param skillType which skills XP gets returned
     * @return return the amount of XP for a certain skill
     */
    public double getXPfromSkill(SkillType skillType) {
        if (this.skills.get(skillType) == null) return 0.0;

        return this.skills.get(skillType);
    }

    public UUID getUuid() {
        return uuid;
    }

    public void writeXPToFile() {
        try {
            Path file = xpFolder.resolve(uuid + ".json");
            Files.writeString(file, gson.toJson(skills));
        } catch (IOException e) {
            MinecraftSkillSystem.getInstanz().getSLF4JLogger().error("could not write to file");
        }

        MinecraftSkillSystem instanz = MinecraftSkillSystem.getInstanz();
    }

    public void writeLvlToFile(){
        try {
            Path file = lvlFolder.resolve(uuid + ".json");
            Files.writeString(file, gson.toJson(skillLevel));
        } catch (IOException e) {
            MinecraftSkillSystem.getInstanz().getSLF4JLogger().error("could not write to file");
        }
    }

    public void loadXPFromFile() throws IOException {
        Path file = xpFolder.resolve(uuid + ".json");

        try {
            String json = Files.readString(file);

            Type type = new TypeToken<HashMap<SkillType, Integer>>() {
            }.getType();

            Map<SkillType, Double> readMap = new HashMap<>();
            readMap = gson.fromJson(json, type);

            skills.putAll(readMap);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadLvlFromFile() throws IOException {
        Path file = lvlFolder.resolve(uuid + ".json");

        try {
            String json = Files.readString(file);

            Type type = new TypeToken<HashMap<SkillType, Integer>>() {
            }.getType();

            Map<SkillType, Integer> readMap = new HashMap<>();
            readMap = gson.fromJson(json, type);

            skillLevel.putAll(readMap);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}