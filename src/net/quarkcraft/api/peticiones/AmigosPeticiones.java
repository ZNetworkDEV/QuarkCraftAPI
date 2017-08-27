package net.quarkcraft.api.peticiones;

import org.bukkit.entity.Player;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import mkremins.fanciful.FancyMessage;
import net.quarkcraft.api.CoreAPI;
import net.quarkcraft.api.utils.Utilidades;
import static org.bukkit.ChatColor.*;

import java.util.HashMap;
import java.util.LinkedHashSet;

import org.bukkit.ChatColor;

public class AmigosPeticiones {

	private Player player1;
	private Player player2;
	
	public AmigosPeticiones(Player player , Player player1) {
		CoreAPI.getInstance().getAmigosPeticiones().addPeticion(this);
		CoreAPI.getInstance().getAmigosPeticiones().getPeticionesGeneral().put(player1, player);
		CoreAPI.getInstance().getAmigosPeticiones().getPeticionesGeneral2().put(player, player1);
		
		this.player1 = player;
		this.player2 = player1;
		
		player.sendMessage(Utilidades.Color("&e¡Has enviado una peticion de amistad a el jugador &b" + player1.getName() + "&e!"));
		player1.sendMessage(Utilidades.Color("&e¡Has recibido una peticion de amistad de el jugador &b" + player.getName() + "&e!"));
		
		FancyMessage fMsg = new FancyMessage("");
        fMsg.then("ACEPTAR ");
        fMsg.command("/amigos aceptar " + player.getName());
        fMsg.color(ChatColor.GREEN);
        fMsg.style(ChatColor.BOLD);
        fMsg.then("RECHAZAR ");
        fMsg.command("/amigos rechazar " + player.getName());
        fMsg.color(ChatColor.RED);
        fMsg.style(ChatColor.BOLD);
        fMsg.send(player1);
	}
	
	public Player getPlayer1() {
		return this.player1;
	}
	
	public Player getPlayer2() {
		return this.player2;
	}
}