package listeners;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import tag.Arena;
import tag.TagMain;

public class EntityListener implements Listener {

    private static TagMain tagMain;

    public EntityListener() {
        tagMain = TagMain.tagMain;
        tagMain.getServer().getPluginManager().registerEvents(this, tagMain);
    }

    @EventHandler
    public void onEntityInteract(PlayerInteractEvent event) {

        if (event.getItem() == null) {
            return;
        }

        if (!event.getItem().hasItemMeta()) {
            return;
        }

        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        if (event.getItem().getType() != Material.COMPASS) {
            return;
        }


        Player p = event.getPlayer();
        Arena arena = tagMain.getArenaManager().getArenaWithPlayer(p);
        Player challenged = arena.getChallenged();
        Player executor = arena.getExecutor();

        Location location = challenged.getLocation();
        double x = Math.round(location.getX());
        double z = Math.round(location.getZ());


        p.setCompassTarget(challenged.getLocation());
        p.sendMessage(ChatColor.GREEN + challenged.getName() + ": x=" + x + " z=" + z);

    }

    @EventHandler
    public void onPvp(EntityDamageByEntityEvent event) {

        Entity ent = event.getEntity();
        Entity ent1 = event.getDamager();

        if (ent instanceof Player && ent1 instanceof Player) {

            Player damager = (Player) ent1;
            Player receiver = (Player) ent;

            Arena arena = tagMain.getArenaManager().getArenaWithPlayer(damager);

            if (damager.getName().equals(arena.getExecutor().getName())) {
                arena.end(false);
                damager.sendMessage(ChatColor.GOLD + "You win!");
                receiver.sendMessage(ChatColor.GOLD + "You lost.");
            }
        }

    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {

        Player p = e.getEntity();

        if (tagMain.getArenaManager().getArenaWithPlayer(p) != null) {

            Arena a = tagMain.getArenaManager().getArenaWithPlayer(p);

            if (p.getName().equals(a.getExecutor().getName())) {

                a.getExecutor().sendMessage(ChatColor.GOLD + "You lose.");
                a.getChallenged().sendMessage(ChatColor.GOLD + "You win!");

            } else if (p.getName().equals(a.getChallenged().getName())) {

                a.getExecutor().sendMessage(ChatColor.GOLD + "You win!");
                a.getChallenged().sendMessage(ChatColor.GOLD + "You lose.");

            }

            a.end(false);

        }

    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {

        Player p = event.getPlayer();

        if (tagMain.getArenaManager().getArenaWithPlayer(p) != null) {

            Arena a = tagMain.getArenaManager().getArenaWithPlayer(p);

            if (p.getName().equals(a.getExecutor().getName())) {

                a.getChallenged().sendMessage(ChatColor.GOLD + "You win!");

            } else if (p.getName().equals(a.getChallenged().getName())) {

                a.getExecutor().sendMessage(ChatColor.GOLD + "You win!");

            }

            a.end(false);

        }

    }


}
