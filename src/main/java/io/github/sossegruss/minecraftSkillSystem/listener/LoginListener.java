package io.github.sossegruss.minecraftSkillSystem.listener;


import io.github.sossegruss.minecraftSkillSystem.player.PlayerManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;


public class LoginListener implements Listener {
    @EventHandler
    public void onLogin(PlayerJoinEvent event){
        UUID uuid = event.getPlayer().getUniqueId();

        PlayerManager.loadPlayer(uuid);
    }
}
