package com.toeikanta.acidworldgenerator;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.world.block.BaseBlock;
import com.sk89q.worldedit.world.block.BlockState;
import com.sk89q.worldedit.world.registry.BlockMaterial;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.util.BlockVector;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

public class LandGenerator extends ChunkGenerator{
    int currentHeight = 64;
    AcidWorldGenerator plugin;

    public LandGenerator(AcidWorldGenerator instance) {
        plugin = instance;
    }

    @Override
    public Location getFixedSpawnLocation(World world, Random random) {
        return new Location(world,0,0,0);
    }

    public Clipboard loadSchematic(File file) {
        ClipboardFormat format = ClipboardFormats.findByFile(file);
        try (ClipboardReader reader = format.getReader(new FileInputStream(file))) {
            return reader.read();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ChunkData generateChunkData(World world, Random random, int x, int z, BiomeGrid biome) {
        ChunkData chunk = createChunkData(world);
        //Water
//        for(x=0;x<16;x++){
//            for(z=0;z<16;z++){
//                Clipboard clipboard = loadSchematic(new File(plugin.getDataFolder(),"island.schematic"));
//                //chunk.getBlockData(x, 0, z, Material.BEDROCK);
//                //chunk.setBlock(x, 1, z, Material.SAND);
//                ///chunk.setBlock(X, currentHeight, Z, Material.GRASS);
//                //chunk.setBlock(X, currentHeight-1, Z, Material.DIRT);
//                BaseBlock block = clipboard.getBlock(new BlockVector(x, y, z));
//                for (int i = currentHeight-2; i > 1; i--)
//                    chunk.setBlock(x, i, z, Material.WATER);
//            }
//        }
        // island
        Clipboard clipboard = loadSchematic(new File(plugin.getDataFolder(),"island.schematic"));
        //
        int xRegion;
        if (x % 2 == 0) {
            xRegion = 0;
        } else {
            xRegion = 1;
        }
        //
        int zRegion;
        if (z % 2 == 0) {
            zRegion = 0;
        } else {
            zRegion = 1;
        }
        for(x=0;x<16;x++){
            int xBlock = (xRegion * 16) + x;
            for(z=0;z<16;z++){
                int zBlock = (zRegion * 16) + z;
                for(int y=0;y<80;y++){
                    try {
                        BaseBlock block = clipboard.getFullBlock(BlockVector3.at(xBlock, y, zBlock));
                        Material material = BukkitAdapter.adapt(block.getBlockType());
                        //plugin.logger.info(material.toString());
                       // Material material = Material.getMaterial(block.getNbtId());
                        chunk.setBlock(x, y, z, material);
                    }catch (Exception ex){
                        //
                    }
                }
            }
        }
        return chunk;
    }
}
