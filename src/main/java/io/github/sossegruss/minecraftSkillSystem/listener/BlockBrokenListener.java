package io.github.sossegruss.minecraftSkillSystem.listener;

import io.github.sossegruss.minecraftSkillSystem.manager.ResourceXPmanager;
import io.github.sossegruss.minecraftSkillSystem.player.PlayerManager;
import io.github.sossegruss.minecraftSkillSystem.player.PlayerWithSkills;
import io.github.sossegruss.minecraftSkillSystem.skill.SkillType;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Map;
import java.util.UUID;

public class BlockBrokenListener implements Listener {
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){

        Block block = event.getBlock();
        String blockType = block.getType().name().toLowerCase();

        UUID uuid = event.getPlayer().getUniqueId();
        PlayerWithSkills player = PlayerManager.getPlayer(uuid);

        Map<String, Double> resourceToXP = ResourceXPmanager.getMiningxp();

        if(resourceToXP.containsKey(blockType)){
            player.addSkillXP(SkillType.MINING, resourceToXP.get(blockType));
        }

        resourceToXP = ResourceXPmanager.getFarmingxpbreak();

        if(resourceToXP.containsKey(blockType)){
            player.addSkillXP(SkillType.FARMING, resourceToXP.get(blockType));
        }

        resourceToXP = ResourceXPmanager.getForagingxp();

        if(resourceToXP.containsKey(blockType)){
            player.addSkillXP(SkillType.FORAGING, resourceToXP.get(blockType));
        }
    }
}
