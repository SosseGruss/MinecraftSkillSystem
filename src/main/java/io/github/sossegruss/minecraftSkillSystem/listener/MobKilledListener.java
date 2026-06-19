package io.github.sossegruss.minecraftSkillSystem.listener;

import io.github.sossegruss.minecraftSkillSystem.manager.ResourceXPmanager;
import io.github.sossegruss.minecraftSkillSystem.player.PlayerManager;
import io.github.sossegruss.minecraftSkillSystem.player.PlayerWithSkills;
import io.github.sossegruss.minecraftSkillSystem.skill.SkillType;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.Map;

public class MobKilledListener implements Listener {
    @EventHandler
    public void mobKilledListener(EntityDeathEvent event){
        if(!(event.getEntity() instanceof Monster)){
            return;
        }
        if(!(event.getDamageSource().getCausingEntity() instanceof Player)){
            return;
        }

        PlayerWithSkills player = PlayerManager.getPlayer(event.getDamageSource().getCausingEntity().getUniqueId());
        Map<String, Double> combatXPforMob = ResourceXPmanager.getCombatxp();

        player.addSkillXP(SkillType.COMBAT, combatXPforMob.get(event.getEntity()));

    }
}
