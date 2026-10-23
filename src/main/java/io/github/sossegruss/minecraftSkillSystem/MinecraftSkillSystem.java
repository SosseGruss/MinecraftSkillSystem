package io.github.sossegruss.minecraftSkillSystem;

import io.github.sossegruss.minecraftSkillSystem.Commands.OpenXpInfoInv;
import org.bukkit.plugin.java.JavaPlugin;

public final class MinecraftSkillSystem extends JavaPlugin {

    private static MinecraftSkillSystem instanz;

    @Override
    public void onEnable() {
        instanz = this;
        registerCommand("levelinfo", new OpenXpInfoInv());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static MinecraftSkillSystem getInstanz() {
        return instanz;
    }
}
