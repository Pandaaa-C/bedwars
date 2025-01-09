package com.panda0day.bedwars.teams;

import com.panda0day.bedwars.Main;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TeamSelector {
    public static final Map<String, Material> teamWoolColours = new HashMap<>();

    static {
        Set<String> teamNames = Main.getTeamConfig().getTeams();
        for (String team : teamNames) {
            String woolColour = Main.getTeamConfig().getFileConfiguration().getString(team + ".material");
            if (woolColour == null)
                woolColour = "LIME_WOOL";
            Material teamWool = Material.getMaterial(woolColour);
            teamWoolColours.put(team.toLowerCase(), teamWool);
        }
    }

    public static Material getTeamWool(String team) {
        return teamWoolColours.getOrDefault(team.toLowerCase(), Material.GRAY_WOOL);
    }
}
