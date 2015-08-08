package pl.dans.plugins.sc.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import pl.dans.plugins.sc.SlimyCrack;


public class FlowListener implements Listener {
    private final SlimyCrack slimyCrack;

    public FlowListener(SlimyCrack slimyCrack) {
        this.slimyCrack = slimyCrack;
    }
    
    @EventHandler(ignoreCancelled = true)
    public void onFlow(BlockFromToEvent event) {
        if (slimyCrack.isGenerationInProgress()) {
            event.setCancelled(true);
        }
    }
}
