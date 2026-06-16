package io.github.sossegruss.minecraftSkillSystem;

import org.bukkit.plugin.java.JavaPlugin;

public final class MinecraftSkillSystem extends JavaPlugin {

    private static MinecraftSkillSystem instanz;

    @Override
    public void onEnable() {
        instanz = this;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static MinecraftSkillSystem getInstanz() {
        return instanz;
    }
}
