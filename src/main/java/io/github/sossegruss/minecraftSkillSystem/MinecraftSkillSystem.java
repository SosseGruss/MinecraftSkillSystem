package io.github.sossegruss.minecraftSkillSystem;

import io.github.sossegruss.minecraftSkillSystem.Commands.OpenXpInfoInv;
import io.github.sossegruss.minecraftSkillSystem.listener.*;
import io.github.sossegruss.minecraftSkillSystem.player.PlayerManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class MinecraftSkillSystem extends JavaPlugin {

    private static MinecraftSkillSystem instanz;
    private static PlayerManager playerManager;

    @Override
    public void onEnable() {
        instanz = this;
        playerManager = new PlayerManager(getDataFolder().toPath());
        getServer().getPluginManager().registerEvents(new LoginListener(), this);
        getServer().getPluginManager().registerEvents(new LogoutListener(), this);
        getServer().getPluginManager().registerEvents(new BlockBrokenListener(), this);
        getServer().getPluginManager().registerEvents(new CropHarvestListener(), this);
        getServer().getPluginManager().registerEvents(new FishedListener(), this);
        getServer().getPluginManager().registerEvents(new MobKilledListener(), this);
        getServer().getPluginManager().registerEvents(new InventoryClickListener(), this);
        registerCommand("levelinfo", new OpenXpInfoInv());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static MinecraftSkillSystem getInstanz() {
        return instanz;
    }
    public static PlayerManager getPlayerManager(){return playerManager;}
}
