package io.github.sossegruss.minecraftSkillSystem.Commands;

import io.github.sossegruss.minecraftSkillSystem.inventories.XPInfoInventory;
import io.github.sossegruss.minecraftSkillSystem.player.PlayerManager;
import io.github.sossegruss.minecraftSkillSystem.player.PlayerWithSkills;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.entity.Entity;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class OpenXpInfoInv implements BasicCommand {

    private Map<Player, XPInfoInventory> data = new HashMap<>();

    @Override
    public void execute(CommandSourceStack source, String[] args) {
        CommandSender sender = source.getSender();
        Entity entity = source.getExecutor();

        if(entity instanceof Player){
            Player player = ((Player) entity).getPlayer();
            UUID uuid = player.getUniqueId();
            PlayerWithSkills playerWithSkills = PlayerManager.getPlayer(uuid);
            XPInfoInventory inv = data.computeIfAbsent(player, p -> new XPInfoInventory(playerWithSkills));
            player.openInventory(inv.getInventory());
        }
        else {
            sender.sendMessage("Der executer ist kein Spieler");
        }
    }
}
