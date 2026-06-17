package io.github.sossegruss.minecraftSkillSystem.listener;

import io.github.sossegruss.minecraftSkillSystem.player.PlayerManager;
import io.github.sossegruss.minecraftSkillSystem.player.PlayerWithSkills;
import io.github.sossegruss.minecraftSkillSystem.skill.SkillType;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerHarvestBlockEvent;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.UUID;

public class CropHarvestListener implements Listener {
    @EventHandler
    public void onCropHarvest(PlayerHarvestBlockEvent event){
        UUID uuid = event.getPlayer().getUniqueId();
        PlayerWithSkills player = PlayerManager.getPlayer(uuid);

        Block block = event.getHarvestedBlock();
        String blockType = block.getType().name().toLowerCase();

        Properties harvestableBlocks = new Properties();

        try{
            FileInputStream input = new FileInputStream("src/main/resources/skillValidBlocks/farmingXPinteractive/cropsGrantXPonHarvest.properties");
            harvestableBlocks.load(input);

            if(harvestableBlocks.containsKey(blockType)){
                player.addSkillXP(SkillType.FARMING, Double.parseDouble(harvestableBlocks.getProperty(blockType)));
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
