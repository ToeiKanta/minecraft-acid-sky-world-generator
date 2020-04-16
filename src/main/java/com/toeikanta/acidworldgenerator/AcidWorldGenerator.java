package com.toeikanta.acidworldgenerator;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.Listener;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AcidWorldGenerator extends JavaPlugin{

    Logger logger = Bukkit.getLogger();
    private static Settings settings;
    private static String worldName;
    private static World overWorld = null;
    private static AcidWorldGenerator plugin;
    private AcidTask acidTask;

    @Override
    public void onEnable() {
        this.plugin = this;
        settings = new Settings();
        worldName = settings.getWorldName();
        BukkitScheduler scheduler = getServer().getScheduler();
        File dataFolder = new File(plugin.getDataFolder(),this.getPlugin().getName());
        if(!dataFolder.exists()){
            logger.info("&bFolder does not exists, Creating!");
            dataFolder.mkdir();
            this.saveResource( settings.getIslandSchemName(), false);
        }
        scheduler.scheduleSyncDelayedTask(this, new Runnable() {
            @Override
            public void run() {
                // Plugin startup logic
                logger.info("=============================================");
                logger.info("AcidWorldGenerator: Enable AcidWorldGenerator");
                logger.info("============================================");
                overWorld = Bukkit.getWorld(AcidWorldGenerator.worldName);
                if(overWorld != null){
                    //                Bukkit.getPluginManager().registerEvents(plugin, plugin);
                    // Acid Effects
                    registerListener(new AcidEffect(plugin));
                    // Burn everything
                    acidTask = new AcidTask(plugin);
                }else{
                    logger.log(Level.SEVERE, worldName + " NotFound!! please create with multiverse-core and rerun server.");
                }
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
