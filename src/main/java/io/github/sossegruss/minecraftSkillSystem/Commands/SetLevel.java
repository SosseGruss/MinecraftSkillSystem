package io.github.sossegruss.minecraftSkillSystem.Commands;

import io.github.sossegruss.minecraftSkillSystem.player.PlayerManager;
import io.github.sossegruss.minecraftSkillSystem.player.PlayerWithSkills;
import io.github.sossegruss.minecraftSkillSystem.skill.SkillType;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class SetLevel implements BasicCommand {
    @Override
    public void execute(CommandSourceStack source, String[] args) {
        Player player = (Player) source.getExecutor();
        Entity entity = (Entity) source.getExecutor();
        if(entity instanceof Player){
            PlayerWithSkills playerWithSkills = PlayerManager.getPlayer(player.getUniqueId());
            playerWithSkills.setSkillLevel(SkillType.valueOf(args[0]), Integer.parseInt(args[1]));
        }
    }
}
