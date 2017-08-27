package net.quarkcraft.api.peticiones;

import java.util.Iterator;
import java.util.LinkedHashSet;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

public class AmigosPeticionesHandler {
	
	private AmigosPeticionesHandler instance;
	private Plugin plugin;
	private LinkedHashSet<AmigosPeticiones> amigos = new LinkedHashSet();
	private Multimap<Player, Player> peticiones;
	private Multimap<Player, Player> peticiones2;
	
	public AmigosPeticionesHandler(Plugin plugin) {
		instance = this;
		this.plugin = plugin;
		this.peticiones = HashMultimap.create();
		this.peticiones2 = HashMultimap.create();
	}
	
	public void addPeticion(AmigosPeticiones match) {
		this.amigos.add(match);
	}
	
	public void removerPeticion(AmigosPeticiones match) {
		this.amigos.remove(match);
	}
	
	public AmigosPeticiones encontrarPeticion(Player player , Player player2) {
		Iterator arg1 = this.amigos.iterator();

		AmigosPeticiones duel;
		do {
			if (!arg1.hasNext()) {
				return null;
			}

			duel = (AmigosPeticiones) arg1.next();
		} while (!duel.getPlayer1().equals(player) && !duel.getPlayer2().equals(player2));

		return duel;
	}
	
	public Multimap<Player, Player> getPeticionesGeneral() {
		return this.peticiones;
	}
	
	public Multimap<Player, Player> getPeticionesGeneral2() {
		return this.peticiones2;
	}
}
