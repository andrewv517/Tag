package commands;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tag.Arena;
import tag.TagMain;

public class Challenge implements CommandExecutor {

    private final TagMain tagMain;

    public Challenge() {
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

        if (!serverHasPlayer(args[0], world)) {
            player.sendMessage(ChatColor.RED + "Player not found!");
            return true;
        }

        // Implement checking if executor is in a game or the challenged is in one
        for (Arena a : this.tagMain.getArenaManager().getArenas()) {
            if (a.getChallenged().getName().equals(player.getName()) || a.getExecutor().getName().equals(player.getName())) {
                player.sendMessage(ChatColor.RED + "Cannot send challenge right now!");
                return true;
            }
        }


        Player challenged = getPlayer(args[0], world);
        challenged.sendMessage(ChatColor.GOLD + "You have been challenged to a game of Tag by " + player.getName() +
                " Type /accept " + player.getName() + " to accept, or /deny " + player.getName() + " to deny!");

        Arena arena = new Arena(player, challenged);
        tagMain.getArenaManager().addPotential(arena);

        player.sendMessage(ChatColor.GOLD + "Sent Challenge to " + args[0]);


        return true;
    }


    public boolean serverHasPlayer(String playerName, World world) {

        for (Player p : world.getPlayers()) {
            if (p.getName().equalsIgnoreCase(playerName)) {
                return true;
            }
        }

        return false;
    }

    public Player getPlayer(String playerName, World world) {

        for (Player p : world.getPlayers()) {
            if (p.getName().equalsIgnoreCase(playerName)) {
                return p;
            }
        }
        return null;

    }

}



















