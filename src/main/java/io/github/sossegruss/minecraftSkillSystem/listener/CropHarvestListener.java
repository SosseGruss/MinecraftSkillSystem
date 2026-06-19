package io.github.sossegruss.minecraftSkillSystem.listener;

import io.github.sossegruss.minecraftSkillSystem.manager.ResourceXPmanager;
import io.github.sossegruss.minecraftSkillSystem.player.PlayerManager;
import io.github.sossegruss.minecraftSkillSystem.player.PlayerWithSkills;
import io.github.sossegruss.minecraftSkillSystem.skill.SkillType;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerHarvestBlockEvent;

import java.util.Map;
import java.util.UUID;

public class CropHarvestListener implements Listener {
    @EventHandler
    public void onCropHarvest(PlayerHarvestBlockEvent event){
        UUID uuid = event.getPlayer().getUniqueId();
        PlayerWithSkills player = PlayerManager.getPlayer(uuid);

        Block block = event.getHarvestedBlock();
        String blockType = block.getType().name().toLowerCase();
        Map<String, Double> resourceToXP = ResourceXPmanager.getFarmingxpharest();

        if(resourceToXP.containsKey(blockType)){
            player.addSkillXP(SkillType.FARMING, resourceToXP.get(blockType));
        }
    }
}
