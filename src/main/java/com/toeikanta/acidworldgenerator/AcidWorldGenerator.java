package com.toeikanta.acidworldgenerator;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkPopulateEvent;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class AcidWorldGenerator extends JavaPlugin {

    Logger logger = Bukkit.getLogger();
    Settings settings;
    String worldName;
    private World overWorld;
    private AcidWorldGenerator plugin;
    @Override
    public void onEnable() {
        // Plugin startup logic
        logger.info("============================================");
        logger.info("AcidWorldGenerator: Enable AcidWorldGenerator");
        logger.info("============================================");
    }

    @Override
    public void onDisable() {
        logger.info("============================================");
        logger.info("AcidWorldGenerator: Disable AcidWorldGenerator");
        logger.info("============================================");
    }

    @Override
    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
        this.overWorld = Bukkit.getWorld(worldName);
        this.worldName = worldName;
        return new LandGenerator(this);
    }

    public Settings getSettings() {
        return settings;
    }

    public World getOverWorld() {
        return overWorld;
    }

    public AcidWorldGenerator getPlugin() {
        return plugin;
    }
}
