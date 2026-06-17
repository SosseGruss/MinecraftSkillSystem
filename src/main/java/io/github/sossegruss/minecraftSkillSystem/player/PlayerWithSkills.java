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

import static java.lang.Character.getType;

/**
 * The class represents a player with an UUID and also conntains their Skill XP
 *
 * @author phack
 * @version 16.06.2026
 */
public class PlayerWithSkills {
    private final UUID uuid;
    private final Map<SkillType, Double> skills = new HashMap<>(); //HashMap in order to save the players Skill XP while the server is running

    private final Path folder = Path.of("src/main/resources/playerdata"); //The Path to the folder where all the players data is stored permanently in a json file
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();


    public PlayerWithSkills(UUID uuid) {
        this.uuid = uuid;
    }

    /**
     * adds certain amount of XP to the total of a player Skill XP for a certain skill
     *
     * @param addXP the amont of XP added to the total
     * @param skillType wich skills XP gets increased
     */
    public void addSkillXP(SkillType skillType, double addXP){
        this.skills.put(skillType, this.skills.get(skillType) + addXP);
    }

    /**
     * returns the total amount of XP for a certain skill
     *
     * @param skillType which skills XP gets returned
     * @return return the amount of XP for a certain skill
     */
    public double getXPfromSkill(SkillType skillType){
        if(this.skills.get(skillType) == null) return 0.0;

        return this.skills.get(skillType);
    }

    public UUID getUuid() {
        return uuid;
    }

    public void writeToFile() throws IOException {
        try {
            Path file = folder.resolve(uuid + ".json");
            Files.writeString(file, gson.toJson(skills));
        }catch (IOException e){
            e.printStackTrace();;
        }

        MinecraftSkillSystem instanz = MinecraftSkillSystem.getInstanz();
    }

    public void loadFromFile() throws IOException{
        Path file = folder.resolve(uuid + ".json");

        try {
            String json = Files.readString(file);

            Type type = new TypeToken<HashMap<SkillType, Integer>>(){}.getType();

            Map<SkillType, Double> readMap = new HashMap<>();
            readMap = gson.fromJson(json, type);

            skills.putAll(readMap);

        } catch (IOException e){
            e.printStackTrace();
        }
    }
}