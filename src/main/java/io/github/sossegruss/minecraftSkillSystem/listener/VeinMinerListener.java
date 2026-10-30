package io.github.sossegruss.minecraftSkillSystem.listener;

import io.github.sossegruss.minecraftSkillSystem.player.PlayerManager;
import io.github.sossegruss.minecraftSkillSystem.player.PlayerWithSkills;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class VeinMinerListener implements Listener{

    private final Set<UUID> activePlayers = new HashSet<>();

    @EventHandler
    public void onVeinMinerEvent(BlockBreakEvent event){
        Player player = event.getPlayer();
        PlayerWithSkills playerWithSkills = PlayerManager.getPlayer(player.getUniqueId());
        double veinMineChance;

        if(activePlayers.contains(player.getUniqueId())) return;

        veinMineChance = playerWithSkills.getVeinMiningChance();
        int blocksToMineCount =(int) Math.floor(veinMineChance);
        double chance = veinMineChance - blocksToMineCount;
        if (ThreadLocalRandom.current().nextDouble() < chance) {
            blocksToMineCount++;
        }
        if(blocksToMineCount > playerWithSkills.getVeinMiningBlockCap()){
            blocksToMineCount = playerWithSkills.getVeinMiningBlockCap();
        }
        BlockFace facing = player.getFacing();
        float pitch = player.getPitch();
        Set<Block> blocksToMine = collectVein(event.getBlock(), blocksToMineCount, facing, pitch);

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

    private Set<Block> collectVein(Block start, int maxBlocks, BlockFace facing, float pitch){
        Set<Block> toMine = new HashSet<>();
        toMine.add(start);
        if(pitch > -45 && pitch < 45){
            if (facing == BlockFace.NORTH || facing == BlockFace.SOUTH) {

                for (int y = -1; y <= 1; y++) {
                    for (int x = -1; x <= 1; x++) {
                        Block nextBlock = start.getRelative(x, y, 0);
                        if (!toMine.contains(nextBlock)) {
                            if (toMine.size() >= maxBlocks) {
                                return toMine;
                            }
                            toMine.add(nextBlock);
                        }
                    }
                }
            } else if (facing == BlockFace.EAST || facing == BlockFace.WEST) {

                for (int y = -1; y <= 1; y++) {
                    for (int z = -1; z <= 1; z++) {
                        Block nextBlock = start.getRelative(0, y, z);
                        if (!toMine.contains(nextBlock)) {
                            if (toMine.size() >= maxBlocks) {
                                return toMine;
                            }
                            toMine.add(nextBlock);
                        }
                    }
                }
            }
        }
        if ((pitch < -45 || pitch > 45)) {

            for(int x = -1; x <= 1; x++){
                for(int z = -1; z <= 1; z++){
                    Block nextBlock = start.getRelative(x, 0, z);
                    if(!toMine.contains(nextBlock)) {
                        if (toMine.size() >= maxBlocks) {
                            return toMine;
                        }
                        toMine.add(nextBlock);
                    }
                }
            }

        }
        return toMine;
    }
}
