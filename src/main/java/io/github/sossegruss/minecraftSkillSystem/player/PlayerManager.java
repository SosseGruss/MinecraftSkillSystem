package io.github.sossegruss.minecraftSkillSystem.player;

import io.github.sossegruss.minecraftSkillSystem.MinecraftSkillSystem;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerManager {
    private final static Map<UUID, PlayerWithSkills> PLAYERS = new HashMap<>();

    public static PlayerWithSkills getPlayer(UUID uuid){
        return PLAYERS.get(uuid);
    }

    public static void loadPlayer(UUID uuid){
        if(PLAYERS.containsKey(uuid)){
            throw new IllegalArgumentException("Player already loaded");
        }

        PlayerWithSkills player = new PlayerWithSkills(uuid);
        loadSkillsFromFile(uuid);
        PLAYERS.put(uuid, player);
    }

    public static void unloadPlayer(UUID uuid){
        if(!PLAYERS.containsKey(uuid)){
            throw new IllegalArgumentException("Player doesn't exist");
        }

        try{
            PLAYERS.get(uuid).writeToFile();
        } catch (Exception e){
            MinecraftSkillSystem.getInstanz().getSLF4JLogger().error("could not save player to the file.", e);
        }

        PLAYERS.remove(uuid);
    }

    public static void loadSkillsFromFile(UUID uuid){
        PlayerWithSkills player = PLAYERS.get(uuid);

        try{
            player.loadFromFile();
        }catch (IOException e){
            MinecraftSkillSystem.getInstanz().getSLF4JLogger().error("could not load player from the file.", e);
        }
    }
}
