/** <a href="http://www.cpupk.com/decompiler">Eclipse Class Decompiler</a> plugin, Copyright (c) 2017 Chen Chao. **/
package net.quarkcraft.api;

import net.quarkcraft.api.commands.AmigosComando;
import net.quarkcraft.api.commands.MensajeComando;
import net.quarkcraft.api.commands.PremiumComando;
import net.quarkcraft.api.commands.RangosComando;
import net.quarkcraft.api.events.Events;
import net.quarkcraft.api.mysql.MySQL;
import net.quarkcraft.api.mysql.MySQLAmigos;
import net.quarkcraft.api.mysql.MySQLArenaPvP;
import net.quarkcraft.api.peticiones.AmigosPeticionesHandler;

import org.bukkit.plugin.java.JavaPlugin;

public class CoreAPI extends JavaPlugin {
	
	private static CoreAPI instance;
	private static MySQL mysql;
	private static MySQLArenaPvP mysql1;
	private static MySQLAmigos mysql2;
	private static AmigosPeticionesHandler amigos;
	
	public void onEnable() {
		instance = this;
		mysql = new MySQL("jdbc:mysql://", "localhost", "quarkcraft", "root", "magicpuffs");
		mysql.Conectar();
		mysql1 = new MySQLArenaPvP("jdbc:mysql://", "localhost", "quarkcraft", "root", "magicpuffs");
		mysql1.connection();
		mysql2 = new MySQLAmigos("jdbc:mysql://", "localhost", "quarkcraft", "root", "magicpuffs");
		mysql2.Conectar();
		this.getCommand("rangos").setExecutor(new RangosComando());
		this.getCommand("premium").setExecutor(new PremiumComando());
		this.getCommand("msg").setExecutor(new MensajeComando());
		this.getCommand("amigos").setExecutor(new AmigosComando());
		this.getServer().getPluginManager().registerEvents(new Events(), this);
		
		amigos = new AmigosPeticionesHandler(this);
	}

	public void onDisable() {
		mysql.Desconectar();
		mysql1.disconnect();
		mysql2.Desconectar();
	}

	public static CoreAPI getInstance() {
		return instance;
	}

	public static MySQL getMySQL() {
		return mysql;
	}

	public static MySQLAmigos getAmigosMySQL() {
		return mysql2;
	}
	
	public static MySQLArenaPvP getArenaPvPMySQL() {
		return mysql1;
	}
	
	public AmigosPeticionesHandler getAmigosPeticiones() {
		return amigos;
	}
}