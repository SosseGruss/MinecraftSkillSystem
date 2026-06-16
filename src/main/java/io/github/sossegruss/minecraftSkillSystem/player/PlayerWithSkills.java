package io.github.sossegruss.minecraftSkillSystem.player;

import io.github.sossegruss.minecraftSkillSystem.MinecraftSkillSystem;
import io.github.sossegruss.minecraftSkillSystem.skill.SkillType;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerWithSkills {
    private final UUID uuid;

    private final Map<SkillType, Integer> skills = new HashMap<>();

    public PlayerWithSkills(UUID uuid) {
        this.uuid = uuid;
    }

    public void addSkillXP(SkillType skillType, int addXP){
        this.skills.put(skillType, this.skills.get(skillType) + addXP);
    }

    public int getXPfromSkill(SkillType skillType){
        if(this.skills.get(skillType) == null) return 0;

        return this.skills.get(skillType);
    }

    public UUID getUuid() {
        return uuid;
    }

    public void writeToFile() throws IOException {
        MinecraftSkillSystem instanz = MinecraftSkillSystem.getInstanz();
    }

    public void loadFromFile() throws IOException{

    }
}
