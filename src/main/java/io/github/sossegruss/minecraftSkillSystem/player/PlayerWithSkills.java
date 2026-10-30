package io.github.sossegruss.minecraftSkillSystem.player;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import io.github.sossegruss.minecraftSkillSystem.MinecraftSkillSystem;
import io.github.sossegruss.minecraftSkillSystem.skill.SkillType;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

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
    private final Map<SkillType, Double> skillXP = new HashMap<>(); //HashMap in order to save the players Skill XP while the server is running
    private final Map<SkillType, Integer> skillLevel = new HashMap<>();
    private static final long[] LEVEL_THRASHHOLD = {0, 500, 1200, 2180, 3550, 5470, 8160, 11920, 17190, 24560,
                                                    34880, 49330, 69560, 97880, 137530, 193040, 270750, 379550,
                                                    531870, 745120, 1050000, 1500000, 2100000, 3000000, 4260000,
                                                    6024000, 8494000, 11951000, 16791000, 23568000, 33055000,
                                                    Long.MAX_VALUE};
    private double veinMiningChance = 0;
    private int veinMiningBlockCap = 9;

    private final Path xpFolder;
    private final Path lvlFolder;
    private final Path vcFolder;

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();


    public PlayerWithSkills(UUID uuid, Path dataFolder) {
        this.uuid = uuid;
        this.xpFolder = dataFolder.resolve("playerdata").resolve("XP");
        this.lvlFolder = dataFolder.resolve("playerdata").resolve("LVL");
        this.vcFolder = dataFolder.resolve("playerdata").resolve("VeinChance");

        skillXP.put(SkillType.MINING, 0.0);
        skillXP.put(SkillType.FARMING, 0.0);
        skillXP.put(SkillType.COMBAT, 0.0);
        skillXP.put(SkillType.FISHING, 0.0);
        skillXP.put(SkillType.FORAGING, 0.0);

        skillLevel.put(SkillType.MINING, 1);
        skillLevel.put(SkillType.FARMING, 1);
        skillLevel.put(SkillType.COMBAT, 1);
        skillLevel.put(SkillType.FISHING, 1);
        skillLevel.put(SkillType.FORAGING, 1);
    }

    /**
     * adds certain amount of XP to the total of a player Skill XP for a certain skill
     *
     * @param addXP     the amont of XP added to the total
     * @param skillType wich skills XP gets increased
     */
    public void addSkillXP(SkillType skillType, double addXP) {
        this.skillXP.compute(skillType, (k, currentXP) -> currentXP + addXP);
        Player player = getBukkingPlayer();
        if(player != null) {
            player.sendActionBar(Component.text("+"+addXP+" " + skillType.name()));
        }

        boolean levelup;
        do{
            levelup = lvlUp(skillType);
        }while(levelup);
    }

    public boolean lvlUp(SkillType skillType){
        Player player = getBukkingPlayer();
        double xp = skillXP.get(skillType);
        int currentLvl = skillLevel.get(skillType);
        int newLvl = currentLvl + 1;

        if(xp >= LEVEL_THRASHHOLD[currentLvl]){
            skillXP.replace(skillType, (xp-LEVEL_THRASHHOLD[currentLvl]));
            skillLevel.replace(skillType, newLvl);
            if(player != null) {
                player.sendMessage("§1 Level " + (newLvl) + " §6" + skillType.name());
            }
            if(skillType == SkillType.MINING) {
                veinMiningChance += 0.3;
            }
            return true;
        }
        return false;
    }

    private Player getBukkingPlayer() {
        return Bukkit.getPlayer(uuid);
    }

    public long getNextLvlReq(SkillType skillType){
        int currentLvl = this.skillLevel.get(skillType);
        return this.LEVEL_THRASHHOLD[currentLvl];
    }

    /**
     * returns the total amount of XP for a certain skill
     *
     * @param skillType which skills XP gets returned
     * @return return the amount of XP for a certain skill
     */
    public double getXPfromSkill(SkillType skillType) {
        if (this.skillXP.get(skillType) == null) return 0.0;
        return this.skillXP.get(skillType);
    }

    public int getSkillLevel(SkillType skillType) {
        if (this.skillLevel.get(skillType) == null) return 0;
        return this.skillLevel.get(skillType);
    }

    public double getVeinMiningChance() {
        return veinMiningChance;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setSkillLevel(SkillType skillType, int newlevel){
        this.skillLevel.compute(skillType, (k, currentLvl) -> newlevel);
        this.skillXP.compute(skillType, (k, currentXP) -> 0.0);

        if(skillType == SkillType.MINING) {
            veinMiningChance = 0.3 * newlevel;
        }
    }

    public void writeXPToFile() {
        try {
            Path file = xpFolder.resolve(uuid + ".json");
            Files.writeString(file, gson.toJson(skillXP));
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

    public void writeVeinChanceToFile(){
        try {
            Path file = vcFolder.resolve(uuid + ".json");
            Files.writeString(file, String.valueOf(veinMiningChance));
        } catch (IOException e) {
            MinecraftSkillSystem.getInstanz().getSLF4JLogger().error("could not write to file");
        }
    }

    public void loadXPFromFile() throws IOException {
        Path file = xpFolder.resolve(uuid + ".json");



        try {
            if(!Files.exists(file)){
                Files.createDirectories(xpFolder);
                Files.writeString(file, gson.toJson(skillXP));
                return;
            }
            String json = Files.readString(file);

            Type type = new TypeToken<HashMap<SkillType, Double>>() {}.getType();

            Map<SkillType, Double> readMap = new HashMap<>();
            readMap = gson.fromJson(json, type);

            skillXP.putAll(readMap);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadLvlFromFile() throws IOException {
        Path file = lvlFolder.resolve(uuid + ".json");

        try {
            if(!Files.exists(file)){
                Files.createDirectories(lvlFolder);
                Files.writeString(file, gson.toJson(skillLevel));
                return;
            }
            String json = Files.readString(file);

            Type type = new TypeToken<HashMap<SkillType, Integer>>() {}.getType();

            Map<SkillType, Integer> readMap = new HashMap<>();
            readMap = gson.fromJson(json, type);

            skillLevel.putAll(readMap);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadVeinChanceFromFile() throws IOException {
        Path file = vcFolder.resolve(uuid + ".json");

        try {
            if(!Files.exists(file)){
                Files.createDirectories(vcFolder);
                Files.writeString(file, String.valueOf(veinMiningChance));
                return;
            }
            String json = Files.readString(file);

            veinMiningChance = Double.parseDouble(json);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setVeinMiningBlockCap(int newCap) {
        if(newCap > 0 && newCap <= 9){
            this.veinMiningBlockCap = newCap;
        }
        else {
            MinecraftSkillSystem.getInstanz().getSLF4JLogger().error("Vein Mining Block Cap must be between 1 and 9");
        }
    }

    public int getVeinMiningBlockCap() {
        return veinMiningBlockCap;
    }
}