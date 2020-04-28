package tag;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ArenaManager {

    List<Arena> arenas;
    List<Arena> potentials;

    public ArenaManager() {
        arenas = new ArrayList<>();
        this.potentials = new ArrayList<>();
    }

    public void addArena(Arena arena) {
        this.arenas.add(arena);
    }

    public void addPotential(Arena arena) {
        this.potentials.add(arena);
    }

    public void removePotential(Arena arena) {
        this.potentials.remove(arena);
    }

    public List<Arena> getPotentials() {
        return potentials;
    }

    public List<Arena> getArenas() {
        return arenas;
    }

    public void removeArena(Arena a) {
        this.arenas.remove(a);
    }

    public Arena getArenaWithPlayer(Player p) {

        for (Arena a : this.getArenas()) {

            if (a.getChallenged() == p || a.getExecutor() == p) {
                return a;
            }

        }

        return null;

    }


}
