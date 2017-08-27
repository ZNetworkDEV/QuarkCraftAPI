/** <a href="http://www.cpupk.com/decompiler">Eclipse Class Decompiler</a> plugin, Copyright (c) 2017 Chen Chao. **/
package net.quarkcraft.api.commands;

import net.quarkcraft.api.CoreAPI;
import net.quarkcraft.api.enums.Rangos;
import net.quarkcraft.api.peticiones.AmigosPeticiones;
import net.quarkcraft.api.utils.Utilidades;

import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


import mkremins.fanciful.FancyMessage;

public class AmigosComando implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
		Player player = (Player) sender;

			if (args.length == 0) {
				player.sendMessage(Utilidades.Color("&7&m-------------------------"));
				player.sendMessage(Utilidades.Color("&6&lAmigos"));
				player.sendMessage(Utilidades.Color("&e/amigos añadir <jugador>"));
				player.sendMessage(Utilidades.Color("&e/amigos eliminar <jugador>"));
				player.sendMessage(Utilidades.Color("&e/amigos aceptar <jugador>"));
				player.sendMessage(Utilidades.Color("&e/amigos rechazar <jugador>"));
				player.sendMessage(Utilidades.Color("&e/amigos lista"));
				player.sendMessage(Utilidades.Color("&e/amigos peticiones <recibidas:enviadas>"));
				player.sendMessage(Utilidades.Color("&7&m-------------------------"));
				return true;
			}
			
			if (args.length == 2) {
				if (args[0].equalsIgnoreCase("añadir")) {
					Player paraañadir = Bukkit.getPlayer(args[1]);
					
					if (paraañadir == null) {
						player.sendMessage(Utilidades.Color("&e¡El jugador &b" + paraañadir.getName() + "&e no esta conectado!"));
						return true;
					}
					
					if (CoreAPI.getInstance().getAmigosPeticiones().encontrarPeticion(player , paraañadir) == null) {
						new AmigosPeticiones(player, paraañadir);	
					} else {
						player.sendMessage(Utilidades.Color("&e¡Ya has enviado una peticion a el jugador &b" + paraañadir.getName() + "&e!"));
					}
				}
			}
			if (args.length == 2) {
				if (args[0].equalsIgnoreCase("rechazar")) {
					Player paraañadir = Bukkit.getPlayer(args[1]);
					
					if (paraañadir == null) {
						player.sendMessage(Utilidades.Color("&e¡El jugador &b" + paraañadir.getName() + "&e no esta conectado!"));
						return true;
					}
					
					if (CoreAPI.getInstance().getAmigosPeticiones().encontrarPeticion(paraañadir , player) != null) {
						CoreAPI.getInstance().getAmigosPeticiones().removerPeticion(CoreAPI.getInstance().getAmigosPeticiones().encontrarPeticion(paraañadir, player));
						CoreAPI.getInstance().getAmigosPeticiones().getPeticionesGeneral().remove(paraañadir, player);
						CoreAPI.getInstance().getAmigosPeticiones().getPeticionesGeneral().remove(player, paraañadir);
						CoreAPI.getInstance().getAmigosPeticiones().getPeticionesGeneral2().remove(paraañadir, player);
						CoreAPI.getInstance().getAmigosPeticiones().getPeticionesGeneral2().remove(player, paraañadir);
						
						player.sendMessage(Utilidades.Color("&e¡Has rechazado la peticion de amistad de &b" + paraañadir.getName() + "&e!"));
					} else {
						player.sendMessage(Utilidades.Color("&e¡No tienes ninguna peticion de amistad del jugador de &b" + paraañadir.getName() +  "&e!"));
					}
				}
			}
			if (args.length == 2) {
				if (args[0].equalsIgnoreCase("aceptar")) {
					Player paraañadir = Bukkit.getPlayer(args[1]);
					
					if (paraañadir == null) {
						player.sendMessage(Utilidades.Color("&e¡El jugador &b" + paraañadir.getName() + "&e no esta conectado!"));
						return true;
					}
					
					if (CoreAPI.getInstance().getAmigosPeticiones().encontrarPeticion(paraañadir , player) != null) {
						CoreAPI.getInstance().getAmigosPeticiones().removerPeticion(CoreAPI.getInstance().getAmigosPeticiones().encontrarPeticion(paraañadir, player));
						CoreAPI.getInstance().getAmigosPeticiones().getPeticionesGeneral().remove(paraañadir, player);
						CoreAPI.getInstance().getAmigosPeticiones().getPeticionesGeneral().remove(player, paraañadir);
						CoreAPI.getInstance().getAmigosPeticiones().getPeticionesGeneral2().remove(paraañadir, player);
						CoreAPI.getInstance().getAmigosPeticiones().getPeticionesGeneral2().remove(player, paraañadir);
						
						CoreAPI.getAmigosMySQL().AgregarAmigo(paraañadir, player);
					} else {
						player.sendMessage(Utilidades.Color("&e¡No tienes ninguna peticion de amistad del jugador de &b" + paraañadir.getName() +  "&e!"));
					}
				}
			}
			if (args.length == 1) {
				if (args[0].equalsIgnoreCase("lista")) {
					player.sendMessage(Utilidades.Color("&7&m-------------------------"));
					player.sendMessage(Utilidades.Color("&6&lMis Amigos"));
					if (CoreAPI.getAmigosMySQL().ObtenerListaDeAmigos(player.getName()).size() != 0) {
						List<String> amigos = CoreAPI.getAmigosMySQL().ObtenerListaDeAmigos(player.getName());
					    for (String amigoz : amigos)
					    {
					    	player.sendMessage(Utilidades.Color("&b" + amigoz));
					    }
					} else {
						player.sendMessage(Utilidades.Color("&e¡Sin Amigos!"));
					}
					player.sendMessage(Utilidades.Color("&7&m-------------------------"));
				}
			}
			if (args.length == 2) {
				if (args[0].equalsIgnoreCase("eliminar")) {
					Player test = Bukkit.getPlayer(args[1]);
					
					if (test == null) {
						player.sendMessage(Utilidades.Color("&e¡El jugador &b" + test.getName() + "&e no esta conectado!"));
						return true;
					}
					
					CoreAPI.getAmigosMySQL().RemoverAmigo(player, test);
				}
			}
			if (args.length == 2) {
				if (args[0].equalsIgnoreCase("peticiones")) {
					if (args[1].equalsIgnoreCase("recibidas")) {
						Iterator recibidas = CoreAPI.getInstance().getAmigosPeticiones().getPeticionesGeneral().get(player).iterator();
						
						if (!CoreAPI.getInstance().getAmigosPeticiones().getPeticionesGeneral().containsKey(player)) {
							player.sendMessage(Utilidades.Color("&c¡No tienes peticiones de amigo!"));
							return true;
						}
						
						player.sendMessage(Utilidades.Color("&7&m---------------------------------------------"));
						player.sendMessage(Utilidades.Color("&c&lPeticiones de Amigos"));
						while (recibidas.hasNext()) {
							Player test2 = (Player) recibidas.next();
							FancyMessage fMsg = new FancyMessage("* " + test2.getName() + " ").color(ChatColor.WHITE);
					        fMsg.then("ACEPTAR ");
					        fMsg.tooltip(ChatColor.YELLOW + "Aceptar la peticion de amigo de " +ChatColor.AQUA + test2.getName());
					        fMsg.command("/amigos aceptar " + test2.getName());
					        fMsg.color(ChatColor.GREEN);
					        fMsg.style(ChatColor.BOLD);
					        fMsg.then("RECHAZAR ");
					        fMsg.tooltip(ChatColor.YELLOW + "Rechazar la peticion de amigo de " + ChatColor.AQUA + test2.getName());
					        fMsg.command("/amigos rechazar " + test2.getName());
					        fMsg.color(ChatColor.RED);
					        fMsg.style(ChatColor.BOLD);
					        fMsg.send(player);
						}
						player.sendMessage(Utilidades.Color("&7&m---------------------------------------------"));
					} else if (args[1].equalsIgnoreCase("enviadas")) {
						if (!CoreAPI.getInstance().getAmigosPeticiones().getPeticionesGeneral2().containsKey(player)) {
							player.sendMessage(Utilidades.Color("&c¡No has enviado peticiones de amigo!"));
							return true;
						}
						
						Iterator recibidas = CoreAPI.getInstance().getAmigosPeticiones().getPeticionesGeneral2().get(player).iterator();
						
						player.sendMessage(Utilidades.Color("&7&m---------------------------------------------"));
						player.sendMessage(Utilidades.Color("&c&lPeticiones Enviadas de Amigos"));
						while (recibidas.hasNext()) {
							Player test2 = (Player) recibidas.next();
						FancyMessage fMsg = new FancyMessage("* " + test2.getName() + " ").color(ChatColor.WHITE);
				        fMsg.send(player);
						}
						player.sendMessage(Utilidades.Color("&7&m---------------------------------------------"));
					}
				}
			}
			return false;
	}
}
