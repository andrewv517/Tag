package tag;

import commands.Accept;
import commands.Challenge;
import commands.Deny;
import commands.StopGame;
import listeners.EntityListener;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class TagMain extends JavaPlugin {

    private ArenaManager arenaManager;
    public static TagMain tagMain;

    public static void main(String[] args) {}

    @Override
    public void onEnable() {
        // Plugin startup logic
        tagMain = this;
        this.arenaManager = new ArenaManager();

        Objects.requireNonNull(this.getCommand("challenge")).setExecutor(new Challenge());
        Objects.requireNonNull(this.getCommand("deny")).setExecutor(new Deny());
        Objects.requireNonNull(this.getCommand("accept")).setExecutor(new Accept());
        Objects.requireNonNull(this.getCommand("stopgame")).setExecutor(new StopGame());

        Listener rightClick = new EntityListener();

    }
    

    public ArenaManager getArenaManager() {
        return arenaManager;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
