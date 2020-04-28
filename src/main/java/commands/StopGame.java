package commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tag.Arena;
import tag.TagMain;

public class StopGame implements CommandExecutor {

    private static TagMain tagMain;

    public StopGame() {
        tagMain = TagMain.tagMain;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "You must be a player to execute this command!");
            return true;
        }

        Player player = (Player) sender;

        for (Arena a : tagMain.getArenaManager().getArenas()) {
            if (a.getExecutor().getName().equals(player.getName())) {
                a.end(true);
                player.sendMessage(ChatColor.GOLD + "Quit game.");
                a.getChallenged().sendMessage(ChatColor.GOLD + "Game ended by host.");
                return true;
            }
        }

        player.sendMessage(ChatColor.RED + "Not in a game!");
        return true;

    }



}
