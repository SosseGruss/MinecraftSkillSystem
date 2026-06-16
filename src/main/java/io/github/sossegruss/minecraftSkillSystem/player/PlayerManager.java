package io.github.sossegruss.minecraftSkillSystem.player;

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
}
