/** <a href="http://www.cpupk.com/decompiler">Eclipse Class Decompiler</a> plugin, Copyright (c) 2017 Chen Chao. **/
package net.quarkcraft.api.events;

import net.quarkcraft.api.CoreAPI;
import net.quarkcraft.api.utils.Utilidades;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Events implements Listener {
	@EventHandler
	public void PJoin(PlayerJoinEvent event) {
		event.setJoinMessage((String) null);
		if (!CoreAPI.getMySQL().TieneCuenta(event.getPlayer().getUniqueId().toString())) {
			CoreAPI.getMySQL().CrearCuenta(event.getPlayer());	
		}
	}

	@EventHandler
	public void PQuit(PlayerQuitEvent event) {
		event.setQuitMessage((String) null);
	}
	
	@EventHandler
	public void PChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		
		if (CoreAPI.getMySQL().ObtenerChat(player).equals("No")) {
			player.sendMessage(Utilidades.Color("&c¡Oops , tienes el chat desactivado!"));
			event.setCancelled(true);
			return;
			
			
			
		}
	}
}