/** <a href="http://www.cpupk.com/decompiler">Eclipse Class Decompiler</a> plugin, Copyright (c) 2017 Chen Chao. **/
package net.quarkcraft.api.commands;

import mkremins.fanciful.FancyMessage;
import net.quarkcraft.api.CoreAPI;
import net.quarkcraft.api.utils.Utilidades;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PremiumComando implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
		Player player = (Player) sender;

			FancyMessage fMsg = new FancyMessage("ACEPTAR");
			fMsg.color(ChatColor.GREEN);
			fMsg.style(new ChatColor[]{ChatColor.BOLD});
			fMsg.command("/premium aceptar");
			fMsg.then(" RECHAZAR").color(ChatColor.RED).style(new ChatColor[]{ChatColor.BOLD})
					.command("/premium rechazar");
			if (args.length == 0) {
				player.sendMessage(Utilidades.Color("&7&m-------------------------"));
				player.sendMessage(Utilidades.Color("&6&l¿Estas Seguro?"));
				player.sendMessage(Utilidades.Color("&7Si eres premium activalo , de lo contrario no podras ,"));
				player.sendMessage(Utilidades.Color("&7Entrar mas al servidor!"));
				player.sendMessage(Utilidades.Color("&7"));
				fMsg.send(player);
				player.sendMessage(Utilidades.Color("&7&m-------------------------"));
			}

			if (args.length == 1 && args[0].equalsIgnoreCase("aceptar")) {
				CoreAPI.getMySQL().PonerPremium(player, "Si");
				player.sendMessage(Utilidades.Color("&aAhora eres Premium , relogea para ver los cambios!"));
			}

			if (args.length == 1 && args[0].equalsIgnoreCase("rechazar")) {
				player.sendMessage(Utilidades.Color("&aRechazado , has cancelado el modo premium!"));
			}

			return false;
		}
}