package io.github.sossegruss.minecraftSkillSystem.listener;

import io.github.sossegruss.minecraftSkillSystem.player.PlayerManager;
import io.github.sossegruss.minecraftSkillSystem.player.PlayerWithSkills;
import io.github.sossegruss.minecraftSkillSystem.skill.SkillType;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.UUID;

public class BlockBrokenListener implements Listener {
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){

        Block block = event.getBlock();
        String blockType = block.getType().name().toLowerCase();

        UUID uuid = event.getPlayer().getUniqueId();
        PlayerWithSkills player = PlayerManager.getPlayer(uuid);

        Properties mineableBlocks = new Properties();


        try {
            FileInputStream input = new FileInputStream("src/main/resources/skillValidBlocks/miningXPblocks.properties");
            mineableBlocks.load(input);
            if(mineableBlocks.containsKey(blockType)){
                String xpValue = mineableBlocks.getProperty(blockType);
                player.addSkillXP(SkillType.MINING, Double.parseDouble(xpValue));
            }

            input = new FileInputStream("src/main/resources/skillValidBlocks/farmingXPinteractive/cropsGrantingXP.properties");
            mineableBlocks.load(input);

            if(mineableBlocks.containsKey(blockType)){
                String xpValue = mineableBlocks.getProperty(blockType);
                player.addSkillXP(SkillType.MINING, Double.parseDouble(xpValue));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



    }
}
