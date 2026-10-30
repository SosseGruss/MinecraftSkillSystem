package io.github.sossegruss.minecraftSkillSystem.Commands;

import io.github.sossegruss.minecraftSkillSystem.player.PlayerManager;
import io.github.sossegruss.minecraftSkillSystem.player.PlayerWithSkills;
import io.papermc.paper.command.brigadier.BasicCommand;

public class SetVeinMiningBlockCap implements BasicCommand{
    @Override
    public void execute(io.papermc.paper.command.brigadier.CommandSourceStack source, String[] args) {
        int newCap = Integer.parseInt(args[0]);
        PlayerWithSkills player = PlayerManager.getPlayer(source.getExecutor().getUniqueId());
        player.setVeinMiningBlockCap(newCap);
    }
}
