package com.toeikanta.acidworldgenerator;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.world.block.BaseBlock;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;

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
        Clipboard clipboard = loadSchematic(new File(plugin.getDataFolder(),Settings.islandSchemName));
        Boolean genIsland = new Random().nextInt(100-1)+1 < 5 ? true : false;
//        int xRegion; //= Math.abs(x % 2);
//        if (x % 2 == 0) {
//            xRegion = 0;
//        } else {
//            xRegion = 1;
//        }
//        //
//        int zRegion; //= Math.abs(z % 2);
//        if (z % 2 == 0) {
//            zRegion = 0;
//        } else {
//            zRegion = 1;
//        }
        //init
        int highRand = new Random().nextInt(100-1)+1;
        for(x=0;x<16;x++){
//            int xBlock = (xRegion * 16) + x;
            for(z=0;z<16;z++){
//                int zBlock = (zRegion * 16) + z;
                for(int y=0;y<80;y++){
                    try {
                        if(y<=clipboard.getMaximumPoint().getY() && genIsland){
                            //BaseBlock block = clipboard.getFullBlock(BlockVector3.at(xBlock, y, zBlock));
                            BaseBlock block = new ClipboardHolder(clipboard).getClipboard().getFullBlock(BlockVector3.at(x+5, y, z));
                            Material material = BukkitAdapter.adapt(block.getBlockType());
                            chunk.setBlock(x, y+currentHeight-9+highRand, z, material);
                        }
                        if(y<currentHeight){
                            if(chunk.getType(x,y,z) == Material.AIR){
                                chunk.setBlock(x,y,z,Material.WATER);
                            }
                        }
                    }catch (Exception ex){
                        //
                    }
                }

            }
        }
        return chunk;
    }
}
