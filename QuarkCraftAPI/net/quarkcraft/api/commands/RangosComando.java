/** <a href="http://www.cpupk.com/decompiler">Eclipse Class Decompiler</a> plugin, Copyright (c) 2017 Chen Chao. **/
package net.quarkcraft.api.commands;

import net.quarkcraft.api.CoreAPI;
import net.quarkcraft.api.enums.Rangos;
import net.quarkcraft.api.utils.Utilidades;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RangosComando implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
		Player player = (Player) sender;
		if (!player.isOp()) {
			player.sendMessage(Utilidades.Color("&cPara hacer esto necesitas el rango Admin o Superior!"));
			return true;
		} else {
			if (args.length == 0) {
				player.sendMessage(Utilidades.Color("&7&m-------------------------"));
				player.sendMessage(Utilidades.Color("&6&lRangos"));
				player.sendMessage(Utilidades.Color("&7/rangos poner <jugador> <rango>"));
				player.sendMessage(Utilidades.Color(""));
				player.sendMessage(Utilidades.Color("&7Rangos disponibles:"));
				player.sendMessage(Utilidades.Color("&6&lUsuario"));
				player.sendMessage(Utilidades.Color("&6&lAyudante"));
				player.sendMessage(Utilidades.Color("&6&lYoutuber"));
				player.sendMessage(Utilidades.Color("&6&lMod"));
				player.sendMessage(Utilidades.Color("&6&lAdmin"));
				player.sendMessage(Utilidades.Color("&6&lDueño"));
				player.sendMessage(Utilidades.Color("&7&m-------------------------"));
			}

			if (args.length == 3 && args[0].equalsIgnoreCase("poner")) {
				Player jugadordar = Bukkit.getPlayer(args[1]);
				Rangos rango = Rangos.getRango(args[2]);
				if (jugadordar == null) {
					player.sendMessage(Utilidades.Color("&cEste jugador no esta conectado!"));
					return true;
				}

				if (rango == null) {
					player.sendMessage(Utilidades.Color("&cEl rango no existe!"));
					return true;
				}

				CoreAPI.getMySQL().PonerRango(jugadordar, rango);
				player.sendMessage(Utilidades.Color(
						"&aHas dado el rango &e" + rango.getNombre() + " &aa el jugador &e" + jugadordar.getName()));
			}

			return false;
		}
	}
}