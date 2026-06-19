package io.github.sossegruss.minecraftSkillSystem.listener;

import io.github.sossegruss.minecraftSkillSystem.player.PlayerManager;
import io.github.sossegruss.minecraftSkillSystem.player.PlayerWithSkills;
import io.github.sossegruss.minecraftSkillSystem.skill.SkillType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;

public class FishedListener implements Listener {
    @EventHandler
    public void fishUp(PlayerFishEvent event){
        if(event.getState() != PlayerFishEvent.State.CAUGHT_FISH){return;}

        PlayerWithSkills player = PlayerManager.getPlayer(event.getPlayer().getUniqueId());

        player.addSkillXP(SkillType.FISHING, 5.0);
    }
}
