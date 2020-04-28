package commands;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tag.Arena;
import tag.TagMain;

public class Accept implements CommandExecutor {

    private final TagMain tagMain;

    public Accept() {
        tagMain = TagMain.tagMain;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "You must be a player to execute this command!");
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + "Incorrect command usage!");
            return true;
        }

        Player player = (Player) sender;
        World world = player.getWorld();
        Player challenger = getPlayer(args[0], world);


        // Check if args[0] challenged player or if args[0] isn't even on the server

        if (challenger == null) {
            sender.sendMessage(ChatColor.RED + "Cannot find player!");
            return true;
        }




        for (Arena a : tagMain.getArenaManager().getPotentials()) {
            if (a.getExecutor().getName().equalsIgnoreCase(challenger.getName()) &&
                    a.getChallenged().getName().equals(player.getName())) {

                tagMain.getArenaManager().addArena(a);
                tagMain.getArenaManager().removePotential(a);
                a.start();

                sender.sendMessage(ChatColor.GOLD + "Challenge accepted! Teleporting...");
                return true;
            }
        }

        sender.sendMessage(ChatColor.RED + args[0] + " did not challenge you!");
        return true;

    }

    public Player getPlayer(String name, World world) {

        for(Player p : world.getPlayers()) {
            if (p.getName().equalsIgnoreCase(name)) {
                return p;
            }
        }
        return null;
    }



}
