package io.github.sossegruss.minecraftSkillSystem.listener;

import io.github.sossegruss.minecraftSkillSystem.player.PlayerWithSkills;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

public class VeinMinerListener implements Listener{

    private final Set<UUID> activePlayers = new HashSet<>();

    @EventHandler
    public void onVeinMinerEvent(BlockBreakEvent event){
        Player player = event.getPlayer();
        PlayerWithSkills playerWithSkills =  (PlayerWithSkills) event.getPlayer();
        double veinMineChance;

        if(activePlayers.contains(player.getUniqueId())) return;

        veinMineChance = playerWithSkills.getVeinMiningChance();
        int blocksToMineCount =(int) Math.floor(veinMineChance);
        double chance = veinMineChance - blocksToMineCount;
        Random random = new Random();
        if(random.nextInt(3)+1 <= chance*4){
            blocksToMineCount++;
        }
        Set<Block> blocksToMine = collectVein(event.getBlock(), blocksToMineCount);

        blocksToMine.remove(event.getBlock());
        activePlayers.add(player.getUniqueId());

        try{
            for(int i = 0; i < blocksToMine.size(); i++){
                Block currentBlock = (Block) blocksToMine.toArray()[i];
                player.breakBlock(currentBlock);
            }
        }
        finally {
            activePlayers.remove(player.getUniqueId());
        }
    }

    private Set<Block> collectVein(Block start, int maxBlocks){
        Set<Block> toMine = new HashSet<>();
        toMine.add(start);

            for(int x = -1; x <= 1; x++){
                for(int y = -1; y <= 1; y++){
                    Block nextBlock = start.getRelative(x, y, 0);
                    if(!toMine.contains(nextBlock)) {
                        toMine.add(nextBlock);
                        if(toMine.size() >= maxBlocks) {
                            return toMine;
                        }
                    }
                }
            }
        return toMine;
    }
}
