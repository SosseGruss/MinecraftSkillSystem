package io.github.sossegruss.minecraftSkillSystem.inventories;

import io.github.sossegruss.minecraftSkillSystem.player.PlayerWithSkills;
import io.github.sossegruss.minecraftSkillSystem.skill.SkillType;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class XPInfoInventory implements InventoryHolder {
    private final Inventory inv;

    public XPInfoInventory(PlayerWithSkills player){
        ItemStack miningItem = newXpItem(SkillType.MINING, player);
        ItemStack farmingItem = newXpItem(SkillType.FARMING, player);
        ItemStack combatItem = newXpItem(SkillType.COMBAT, player);
        ItemStack fishingItem = newXpItem(SkillType.FISHING, player);
        ItemStack foragingItem = newXpItem(SkillType.FORAGING, player);
        inv = Bukkit.createInventory(this, 36, Component.text("Skill Level"));

        inv.setItem(4, newInfoItem("Level"));
        inv.setItem(20, miningItem);
        inv.setItem(21, farmingItem);
        inv.setItem(22, combatItem);
        inv.setItem(23, fishingItem);
        inv.setItem(24, foragingItem);
    }
    public ItemStack newInfoItem(String titel){
        ItemStack infoItem = new ItemStack(Material.PAPER);
        ItemMeta meta = infoItem.getItemMeta();
        meta.setDisplayName("§a"+titel);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        infoItem.setItemMeta(meta);

        return infoItem;
    }
    public ItemStack newXpItem(SkillType skillType, PlayerWithSkills player){
        ItemStack item = null;
        switch (skillType){
            case MINING:
               item  = new ItemStack(Material.STONE);
               break;
            case FARMING:
                item  = new ItemStack(Material.WHEAT);
                break;
            case COMBAT:
                item  = new ItemStack(Material.IRON_SWORD);
                break;
            case FISHING:
                item  = new ItemStack(Material.COD);
                break;
            case FORAGING:
                item  = new ItemStack(Material.OAK_LOG);
                break;
        }
        ItemMeta meta = item.getItemMeta();

        double xp = player.getXPfromSkill(skillType);
        int level = player.getSkillLevel(skillType);
        long needed = player.getNextLvlReq(skillType);

        switch (skillType){
            case MINING:
                meta.setDisplayName("§6Mining §7- Level §e" + level);
                break;
            case FARMING:
                meta.setDisplayName("§6Farming §7- Level §e" + level);
                break;
            case COMBAT:
                meta.setDisplayName("§6Combat §7- Level §e" + level);
                break;
            case FISHING:
                meta.setDisplayName("§6Fishing §7- Level §e" + level);
                break;
            case FORAGING:
                meta.setDisplayName("§6Foraging §7- Level §e" + level);
                break;
        }

        List<String> lore = new ArrayList<>();
        lore.add("§7XP: §a" + xp + " §7/ §a" + needed);
        meta.setLore(lore);

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);

        return item;
    }

    @Override
    public @NotNull Inventory getInventory() {
        return this.inv;
    }
}
