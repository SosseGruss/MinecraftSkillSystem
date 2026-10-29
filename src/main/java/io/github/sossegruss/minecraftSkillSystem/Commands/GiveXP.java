package io.github.sossegruss.minecraftSkillSystem.Commands;

import io.github.sossegruss.minecraftSkillSystem.player.PlayerWithSkills;
import io.github.sossegruss.minecraftSkillSystem.skill.SkillType;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;

public class GiveXP implements BasicCommand{
    @Override
    public void execute(CommandSourceStack source, String[] args) {
        PlayerWithSkills player = (PlayerWithSkills) source.getExecutor();
        double amount = Double.parseDouble(args[0]);
        player.addSkillXP(SkillType.valueOf(args[1]), amount);
    } {
}
