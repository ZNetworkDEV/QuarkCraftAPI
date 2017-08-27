/** <a href="http://www.cpupk.com/decompiler">Eclipse Class Decompiler</a> plugin, Copyright (c) 2017 Chen Chao. **/
package net.quarkcraft.api.commands;

import mkremins.fanciful.FancyMessage;
import net.quarkcraft.api.CoreAPI;
import net.quarkcraft.api.utils.Utilidades;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MensajeComando implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
		Player player = (Player) sender;
		
	    if (args.length > 1){
	        Player target = Bukkit.getServer().getPlayer(args[0]);
	        
	        if (target == null) {
	        	player.sendMessage(Utilidades.Color("&e¡El jugador&b " + target.getName() + " &eno se encuentra conectado!"));
	        	return true;
	        }
	        
	        if (target == player) {
	        	player.sendMessage(Utilidades.Color("&e¡No te puedes enviar mensajes a ti mismo!"));
	        	return true;
	        }
	        
	        String sm = "";

	        for (int i = 1; i < args.length; i++){
	            String test = (args[i] + " ");
	            sm = (sm + test);
	        }

	        target.sendMessage(Utilidades.Color("&eMensaje de " + CoreAPI.getMySQL().obtenerRango(player).getColor() + player.getName() + "&7:&f " + sm));
	        player.sendMessage(Utilidades.Color("&eEnviado a " + CoreAPI.getMySQL().obtenerRango(target).getColor() + target.getName() + "&7:&f " + sm));
	    }else{
	    player.sendMessage(Utilidades.Color("&bUsa: &e/msg <jugador> <mensaje>"));
	    }
		return true;
	}
}