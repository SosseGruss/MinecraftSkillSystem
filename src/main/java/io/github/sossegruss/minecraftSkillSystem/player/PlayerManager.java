package io.github.sossegruss.minecraftSkillSystem.player;

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
        PLAYERS.put(uuid, player);
    }

    public static void unloadPlayer(UUID uuid){
        if(!PLAYERS.containsKey(uuid)){
            throw new IllegalArgumentException("Player already loaded");
        }

        PLAYERS.remove(uuid);
    }

    public static void loadSkillsFromFile(UUID uuid){
        PlayerWithSkills player = PLAYERS.get(uuid);

        try{
            player.loadFromFile();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
