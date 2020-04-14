package com.toeikanta.acidworldgenerator;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MVWorldManager;
import com.onarandombox.MultiverseCore.api.MultiverseWorld;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkPopulateEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AcidWorldGenerator extends JavaPlugin{

    Logger logger = Bukkit.getLogger();
    private static Settings settings;
    private static String worldName = "test10";
    private static World overWorld;
    private static AcidWorldGenerator plugin;
    private AcidTask acidTask;

    @Override
    public void onEnable() {
        this.plugin = this;
        BukkitScheduler scheduler = getServer().getScheduler();
        scheduler.scheduleSyncDelayedTask(this, new Runnable() {
            @Override
            public void run() {
                // Plugin startup logic
                logger.info("=============================================");
                logger.info("AcidWorldGenerator: Enable AcidWorldGenerator");
                logger.info("============================================");
                overWorld = Bukkit.getWorld(AcidWorldGenerator.worldName);
                settings = new Settings();
//                Bukkit.getPluginManager().registerEvents(plugin, plugin);
                // Acid Effects
                registerListener(new AcidEffect(plugin));
                // Burn everything
                acidTask = new AcidTask(plugin);
            }
        }, 20L);

    }

    /**
     * Register a listener
     * @param listener - listener
     */
    public void registerListener(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, this);
//        listeners.computeIfAbsent(addon, k -> new ArrayList<>()).add(listener);
    }

    @Override
    public void onDisable() {
        logger.info("============================================");
        logger.info("AcidWorldGenerator: Disable AcidWorldGenerator");
        logger.info("============================================");
        if (acidTask != null) acidTask.cancelTasks();
    }

    @Override
    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
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
