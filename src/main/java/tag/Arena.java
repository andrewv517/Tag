package tag;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.Random;

public class Arena implements Listener {

    private Player challenged;
    private Player executor;
    private World world;
    private WorldBorder wb;
    private static TagMain tagMain;
    private Random random = new Random();
    private boolean started = false;
    private int timer;
    private int taskID;

    public Arena(Player executor, Player challenged) {
        this.challenged = challenged;
        this.executor = executor;
        this.world = challenged.getWorld();
        this.wb = this.world.getWorldBorder();
        tagMain = TagMain.tagMain;
    }

    public Player getExecutor() {
        return executor;
    }

    public Player getChallenged() {
        return challenged;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public void start() {

        this.started = true;

        int x = random.nextInt(1000);
        int z = random.nextInt(1000);

        wb.setCenter(x, z);
        wb.setSize(100, 0);

        this.challenged.teleport(new Location(world, x, this.world.getHighestBlockYAt(x, z-45) +3, z - 45));
        this.executor.teleport(new Location(world, x, this.world.getHighestBlockYAt(x, z+45)+3, z + 45));

        this.challenged.getInventory().clear();
        this.executor.getInventory().clear();

        ItemStack[] items = {new ItemStack(Material.DIAMOND_SHOVEL), new ItemStack(Material.DIAMOND_AXE), new ItemStack(Material.DIAMOND_PICKAXE)};

        this.challenged.getInventory().addItem(items);
        this.executor.getInventory().addItem(items);

        this.challenged.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 64));
        this.executor.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 64));


        this.challenged.sendMessage(ChatColor.GOLD + this.challenged.getName() + ", don't let " +
                this.executor.getName() + " hit you or else they win! 2 minutes!");
        this.executor.sendMessage(ChatColor.GOLD + "You have 2 minutes to hit " + this.challenged.getName() + ", " +
                "good luck!");

        ItemStack compass = new ItemStack(Material.COMPASS);
        ItemMeta compassM = compass.getItemMeta();
        compassM.setDisplayName(ChatColor.GREEN + "Player Tracker");
        compass.setItemMeta(compassM);


        this.executor.getInventory().addItem(compass);
        this.executor.setCompassTarget(this.executor.getLocation());

        setTimer(120);
        startTimer();

    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

    public void stopTimer() {
        Bukkit.getScheduler().cancelTask(taskID);
    }

    public void startTimer() {
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        taskID = scheduler.scheduleSyncRepeatingTask(tagMain, new Runnable() {
            @Override
            public void run() {
                if (timer == 0) {
                    end(true);
                    stopTimer();
                    return;
                }

                if (timer == 120 || timer == 90 || timer == 60 || timer == 30 || timer <= 10) {
                    Bukkit.broadcastMessage(ChatColor.GOLD + "Time remaining " + timer + " seconds!");
                }

                timer--;

            }
        }, 0L, 20L);

    }

    public void end(boolean ranOut) {
        this.challenged.getInventory().clear();
        this.executor.getInventory().clear();
        tagMain.getArenaManager().getArenas().remove(this);
        stopTimer();
        wb.reset();

        if (ranOut) {
            this.challenged.sendMessage(ChatColor.GOLD + "You won!");
            this.executor.sendMessage(ChatColor.GOLD + "You lost.");
        }

    }


}
