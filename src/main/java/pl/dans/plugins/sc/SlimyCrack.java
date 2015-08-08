package pl.dans.plugins.sc;

import pl.dans.plugins.sc.commands.GenerateCommand;
import java.util.logging.Level;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import pl.dans.plugins.sc.listeners.FlowListener;


public class SlimyCrack extends JavaPlugin {
    
    private boolean generationInProgress = false;

    public boolean isGenerationInProgress() {
        return generationInProgress;
    }

    public void setGenerationInProgress(boolean generationInProgress) {
        this.generationInProgress = generationInProgress;
    }

    @Override
    public void onEnable() {
        getLogger().log(Level.INFO, "{0}onEnable", ChatColor.RED);
        
        getServer().getPluginManager().registerEvents(new FlowListener(this), this);
        
        GenerateCommand command = new GenerateCommand(this);
        getCommand("scstart").setExecutor(command);
    }

    @Override
    public void onDisable() {
        super.onDisable(); //To change body of generated methods, choose Tools | Templates.
    }


    
    
}
