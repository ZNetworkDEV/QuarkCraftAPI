/** <a href="http://www.cpupk.com/decompiler">Eclipse Class Decompiler</a> plugin, Copyright (c) 2017 Chen Chao. **/
package net.quarkcraft.api.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import net.quarkcraft.api.enums.Rangos;
import net.quarkcraft.api.utils.Utilidades;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class MySQLAmigos {
	public Connection connection;
	private String urlbase;
	private String host;
	private String database;
	private String user;
	private String pass;

	public MySQLAmigos(String urlbase, String host, String database, String user, String pass) {
		this.urlbase = urlbase;
		this.host = host;
		this.database = database;
		this.user = user;
		this.pass = pass;
	}

	public void Conectar() {
		if (!this.HayConeccion()) {
			try {
				this.connection = DriverManager.getConnection(this.urlbase + this.host + "/" + this.database, this.user,
						this.pass);
				Statement e = this.connection.createStatement();
				e.execute(
						"CREATE TABLE IF NOT EXISTS quarkcraftamigos(uuid varchar(200) PRIMARY KEY NOT NULL, uuid1 varchar(36), nombre varchar(36), nombre1 varchar(36))");
			} catch (SQLException arg1) {
				arg1.printStackTrace();
			}
		}

	}

	public void Desconectar() {
		if (this.HayConeccion()) {
			try {
				this.connection.close();
			} catch (SQLException arg1) {
				arg1.printStackTrace();
			}
		}

	}

	public boolean HayConeccion() {
		return this.connection != null;
	}

	public void AgregarAmigo(Player player , Player paraagregar) {
		if (this.SonAmigos2(paraagregar, player)) {
			paraagregar.sendMessage(Utilidades.Color("&e¡Has aceptado la peticion de amistad del jugador&b " + player.getName() + "&e!"));
			player.sendMessage(Utilidades.Color("&e¡El jugador &b" + paraagregar.getName() + "&e ha aceptado tu peticion de amistad!"));
			try {
				PreparedStatement e = this.connection.prepareStatement("INSERT INTO quarkcraftamigos(uuid,uuid1,nombre,nombre1) VALUES (?,?,?,?)");
				e.setString(1, player.getUniqueId().toString());
				e.setString(2, paraagregar.getUniqueId().toString());
				e.setString(3, player.getName());
				e.setString(4, paraagregar.getName());
				e.execute();
				e.close();
				return;
			} catch (SQLException arg2) {
				arg2.printStackTrace();
			}
		} else {
			paraagregar.sendMessage(Utilidades.Color("&e¡Ya eres amigo con el jugador &b" + player.getName() + "&e!"));
			return;
		}
		if (!this.SonAmigos(player, paraagregar)) {
			paraagregar.sendMessage(Utilidades.Color("&e¡Has aceptado la peticion de amistad del jugador&b " + player.getName() + "&e!"));
			player.sendMessage(Utilidades.Color("&e¡El jugador &b" + paraagregar.getName() + "&e ha aceptado tu peticion de amistad!"));
			try {
				PreparedStatement e = this.connection.prepareStatement("INSERT INTO quarkcraftamigos(uuid,uuid1,nombre,nombre1) VALUES (?,?,?,?)");
				e.setString(1, player.getUniqueId().toString());
				e.setString(2, paraagregar.getUniqueId().toString());
				e.setString(3, player.getName());
				e.setString(4, paraagregar.getName());
				e.execute();
				e.close();
				return;
			} catch (SQLException arg2) {
				arg2.printStackTrace();
			}
		} else {
			paraagregar.sendMessage(Utilidades.Color("&e¡Ya eres amigo con el jugador &b" + player.getName() + "&e!"));
			return;
		}

	}

	 public List<String> ObtenerListaDeAmigos(String nombrejugador)
	  {
	    List<String> list = new ArrayList();
	    try
	    {
	      PreparedStatement e = this.connection.prepareStatement("SELECT * FROM quarkcraftamigos WHERE nombre = ?");
	      e.setString(1, nombrejugador);
	      ResultSet test = e.executeQuery();
	      while (test.next())
	      {
	        String pl = test.getString("nombre1");
	        list.add(pl);
	      }
	    }
	    catch (SQLException e)
	    {
	      e.printStackTrace();
	    }
	    return list;
	  }
	
	public boolean SonAmigos(Player player , Player paraagregar) {
		try {
			PreparedStatement e = this.connection.prepareStatement("SELECT * FROM quarkcraftamigos WHERE nombre = ? AND nombre1 = ?");
			e.setString(1, player.getName());
			e.setString(2, paraagregar.getName());
			ResultSet resultat = e.executeQuery();
			boolean hasAccount = resultat.next();
			e.close();
			return hasAccount;
		} catch (SQLException arg4) {
			arg4.printStackTrace();
			return false;
		}
	}
	
	public boolean SonAmigos2(Player player , Player paraagregar) {
		try {
			PreparedStatement e = this.connection.prepareStatement("SELECT * FROM quarkcraftamigos WHERE nombre = ? AND nombre1 = ?");
			e.setString(1, paraagregar.getName());
			e.setString(2, player.getName());
			ResultSet resultat = e.executeQuery();
			boolean hasAccount = resultat.next();
			e.close();
			return hasAccount;
		} catch (SQLException arg4) {
			arg4.printStackTrace();
			return false;
		}
	}
	
	public void RemoverAmigo(Player player , Player paraagregar) {
		if (this.SonAmigos(player, paraagregar)) {
			player.sendMessage(Utilidades.Color("&e¡Has removido de tu lista de amigos a el jugador &b" + paraagregar.getName() + "&e!"));
			 String eliminar = "delete from quarkcraftamigos where nombre = ? AND nombre1 = ?";
             try {
                 PreparedStatement preparedStmt = (PreparedStatement) this.connection.prepareStatement(eliminar);
                 preparedStmt.setString(1, player.getName());
                 preparedStmt.setString(2, paraagregar.getName());
                 preparedStmt.executeUpdate();
                 return;
             } catch (SQLException e) {
                 e.printStackTrace();
             }
		} else {
			player.sendMessage(Utilidades.Color("&e¡No tienes como amigo a el jugador &b" + paraagregar.getName() + "&e!"));
			return;
		}
		if (this.SonAmigos2(paraagregar, player)) {
			player.sendMessage(Utilidades.Color("&e¡Has removido de tu lista de amigos a el jugador &b" + paraagregar.getName() + "&e!"));
			 String eliminar = "delete from quarkcraftamigos where nombre = ? AND nombre1 = ?";
             try {
                 PreparedStatement preparedStmt = (PreparedStatement) this.connection.prepareStatement(eliminar);
                 preparedStmt.setString(1, paraagregar.getName());
                 preparedStmt.setString(2, player.getName());
                 preparedStmt.executeUpdate();
                 return;
             } catch (SQLException e) {
                 e.printStackTrace();
             }
	} else {
		player.sendMessage(Utilidades.Color("&e¡No tienes como amigo a el jugador &b" + paraagregar.getName() + "&e!"));
		return;
	}
	}
}