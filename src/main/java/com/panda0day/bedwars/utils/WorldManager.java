package com.panda0day.bedwars.utils;

import com.panda0day.bedwars.Main;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class WorldManager {

    public void loadWorld(String worldName) {
        World world = Bukkit.getWorld(worldName);

        if (world == null) {
            world = Bukkit.createWorld(new WorldCreator(worldName).environment(World.Environment.NORMAL));
            Main.getInstance().getLogger().info("World " + worldName + " created or loaded.");

            if (world != null) GameRuleManager.setDefaultGameRules(world);
        }
    }

    public void unloadWorld(String worldName) {
        World world = Bukkit.getWorld(worldName);
        if (world != null) {
            Bukkit.unloadWorld(world, false);
            Main.getInstance().getLogger().info("World " + worldName + " removed.");
        }
    }

    public boolean isWorldLoaded(String worldName) {
        World world = Bukkit.getWorld(worldName);
        return world != null;
    }

    public void checkAndRestoreBackup(String gameFolderName, String backupFolderName) {
        File worldFolder = new File(Bukkit.getServer().getWorldContainer(), gameFolderName);
        File backupFolder = new File(Bukkit.getServer().getWorldContainer(), backupFolderName);

        if (backupFolder.exists()) {
            if (worldFolder.exists()) {
                deleteWorld(worldFolder);
            }

            renameBackupToGame(backupFolder, worldFolder);

            backupWorld(worldFolder, backupFolderName);
        } else {
            Main.getInstance().getLogger().warning("No backup folder found, skipping restore.");
        }
    }

    private void deleteWorld(File worldFolder) {
        try {
            World world = Bukkit.getServer().getWorld(worldFolder.getName());
            if (world != null) {
                world.getPlayers().forEach(player -> player.kickPlayer("The world is being reset"));
                Bukkit.getServer().unloadWorld(world, false);
            }

            deleteFolderRecursively(worldFolder);
            Main.getInstance().getLogger().info("World folder deleted successfully.");
        } catch (IOException e) {
            Main.getInstance().getLogger().warning("Failed to delete the world folder.");
            e.printStackTrace();
        }
    }

    private void deleteFolderRecursively(File folder) throws IOException {
        if (folder.exists()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        deleteFolderRecursively(file);
                    } else {
                        Files.delete(file.toPath());
                    }
                }
            }
            Files.delete(folder.toPath());
        }
    }

    private void renameBackupToGame(File backupFolder, File worldFolder) {
        try {
            if (worldFolder.exists()) {
                deleteFolderRecursively(worldFolder);
            }

            Files.move(backupFolder.toPath(), worldFolder.toPath(), StandardCopyOption.REPLACE_EXISTING);
            Main.getInstance().getLogger().info("Backup world restored and renamed to 'game'.");
        } catch (IOException e) {
            Main.getInstance().getLogger().warning("Failed to rename backup folder to 'game'.");
            e.printStackTrace();
        }
    }

    private void backupWorld(File worldFolder, String backupFolderName) {
        File backupFolder = new File(Bukkit.getServer().getWorldContainer(), backupFolderName);

        try {
            if (backupFolder.exists()) {
                deleteFolderRecursively(backupFolder);
            }

            copyFolderRecursively(worldFolder, backupFolder);
            Main.getInstance().getLogger().info("New world backup created.");
        } catch (IOException e) {
            Main.getInstance().getLogger().warning("Failed to create a backup of the world.");
            e.printStackTrace();
        }
    }

    private void copyFolderRecursively(File sourceFolder, File destinationFolder) throws IOException {
        if (!destinationFolder.exists()) {
            destinationFolder.mkdirs();
        }

        File[] files = sourceFolder.listFiles();
        if (files != null) {
            for (File file : files) {
                File destinationFile = new File(destinationFolder, file.getName());
                if (file.isDirectory()) {
                    copyFolderRecursively(file, destinationFile);
                } else {
                    Files.copy(file.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }
            }
        }
    }
}
