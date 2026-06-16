package io.github.sossegruss.minecraftSkillSystem.listener;

import io.github.sossegruss.minecraftSkillSystem.player.PlayerManager;
import io.github.sossegruss.minecraftSkillSystem.player.PlayerWithSkills;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class logoutListener implements Listener {
    @EventHandler
    public void onLogout(PlayerQuitEvent event){
        UUID uuid = event.getPlayer().getUniqueId();

        PlayerWithSkills player = PlayerManager.getPlayer(uuid);
        try{
            player.writeToFile();
        } catch (Exception e){
            e.printStackTrace();
        }

        PlayerManager.unloadPlayer(uuid);

    }
}
