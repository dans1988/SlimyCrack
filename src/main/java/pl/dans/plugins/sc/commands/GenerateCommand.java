package pl.dans.plugins.sc.commands;

import org.bukkit.ChatColor;
import pl.dans.plugins.sc.SlimyCrack;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import pl.dans.plugins.sc.generator.SlimyCrackGenerator;

public class GenerateCommand implements CommandExecutor {

    private final SlimyCrack slimyCrack;

    public GenerateCommand(SlimyCrack slimyCrack) {
        this.slimyCrack = slimyCrack;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().compareToIgnoreCase("scstart") == 0) {

            if (args.length < 3) {
                return false;
            }

            int width;
            int length;
            String worldName;
            int speed;
            try {
                width = Integer.parseInt(args[0]);
                length = Integer.parseInt(args[1]);
                worldName = args[2];
                speed = Integer.parseInt(args[3]);
            } catch (NumberFormatException ex) {
                sender.sendMessage(ChatColor.RED + "With, length and speed need to be integers!");
                return true;
            }

            SlimyCrackGenerator populator = new SlimyCrackGenerator(width, length, worldName, speed, slimyCrack);
            populator.generate();
        }
        return true;
    }

    private String getMessageStart() {
        return ChatColor.RED + "[" + ChatColor.LIGHT_PURPLE + "SlimyCrack"
                + ChatColor.RED + "] " + ChatColor.YELLOW;
    }

}
