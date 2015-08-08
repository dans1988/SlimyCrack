package pl.dans.plugins.sc.generator;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;
import pl.dans.plugins.sc.SlimyCrack;


public class SlimyCrackGenerator {

    private static final int CHUNK_HEIGHT_LIMIT = 128;
    private static final int BLOCKS_PER_CHUNK = 16;
    private static final int STAIRCASE_START = 16;
    
    private final int width;
    private final int length;
    private final String worldName;
    private final int speed;
    private final SlimyCrack slimyCrack;

    public SlimyCrackGenerator(int width, int length, String worldName, int speed, SlimyCrack slimyCrack) {
        this.width = width;
        this.length = length;
        this.slimyCrack = slimyCrack;
        this.worldName = worldName;
        this.speed = speed;
    }

    public void generate() {
        final World world = Bukkit.getWorld(worldName);
        
        //first chunk with the lowest x
        
        slimyCrack.setGenerationInProgress(true);
        
        int xChunk;
        if (length % BLOCKS_PER_CHUNK == 0) {
            xChunk = length / BLOCKS_PER_CHUNK;
        } else {
            xChunk = (length / BLOCKS_PER_CHUNK) + 1;
        }

        int xMaxChunk = xChunk;
        xChunk = xChunk * -1;
        
        int zChunk;
        if (width % BLOCKS_PER_CHUNK == 0) {
            zChunk = (width + STAIRCASE_START) / BLOCKS_PER_CHUNK;
        } else {
            zChunk = ((width + STAIRCASE_START) / BLOCKS_PER_CHUNK) + 1;
        }
        
        int zMaxChunk = zChunk;
        zChunk = zChunk * -1;
        
        int delayMultiplier = 0;
        for (int x = xChunk; x <= xMaxChunk; x++) {
            for (int z = zChunk; z <= zMaxChunk; z++) {
                final Chunk chunk = world.getChunkAt(x, z);
                new BukkitRunnable() {

                    public void run() {
                        populate(world, chunk);
                        Bukkit.broadcastMessage("Populated chunk at x = " + chunk.getX() + ", z = " + chunk.getZ() + ".");
                    }
                }.runTaskLater(slimyCrack, delayMultiplier * speed);
                delayMultiplier++;
            }
        }
        
        new BukkitRunnable() {

            public void run() {
                slimyCrack.setGenerationInProgress(false);
                Bukkit.broadcastMessage("SlimyCrack generation finished!");
            }
        }.runTaskLater(slimyCrack, delayMultiplier * speed);
    }

    public void populate(World world, Chunk chunk) {
        chunk.load();
        for (int x = 0; x < BLOCKS_PER_CHUNK; x++) {
            for (int z = 0; z < BLOCKS_PER_CHUNK; z++) {
                for (int y = CHUNK_HEIGHT_LIMIT - 1; y >= 0; y--) {

                    
                    Block block = chunk.getBlock(x, y, z);
                    Location location = block.getLocation();
                    
                    int xLocation = location.getBlockX();
                    int yLocation = location.getBlockY();
                    int zLocation = location.getBlockZ();
                    
                    
                    int stairWidth = width + STAIRCASE_START - y;
                    
                    if (zLocation >= (width * -1) && zLocation <= width && xLocation <= length && xLocation >= (length * -1) && yLocation > STAIRCASE_START ) {
                        block.setType(Material.AIR);
                    } else if (y <= STAIRCASE_START && y != 1) {
                        
                        if (zLocation >= (stairWidth * -1) && zLocation <= stairWidth && xLocation <= length && xLocation >= (length * -1)) {
                            block.setType(Material.AIR);
                        }
                    } else if (y == 1) {

                         if (zLocation >= (width * -1) && zLocation <= width && xLocation <= length && xLocation >= (length * -1)) {
                            block.setType(Material.SLIME_BLOCK);
                        } else if (zLocation >= (stairWidth * -1) && zLocation <= stairWidth && xLocation <= length && xLocation >= (length * -1)) {
                            block.setType(Material.AIR);
                        }
                    }

                }
            }

        }

    }
    


}
