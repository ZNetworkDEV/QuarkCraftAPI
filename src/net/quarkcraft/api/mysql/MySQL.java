/** <a href="http://www.cpupk.com/decompiler">Eclipse Class Decompiler</a> plugin, Copyright (c) 2017 Chen Chao. **/
package net.quarkcraft.api.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import net.quarkcraft.api.enums.Rangos;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class MySQL {
	public Connection connection;
	private String urlbase;
	private String host;
	private String database;
	private String user;
	private String pass;

	public MySQL(String urlbase, String host, String database, String user, String pass) {
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
						"CREATE TABLE IF NOT EXISTS quarkcraftapi(uuid varchar(200) PRIMARY KEY NOT NULL, nombre varchar(36), rango varchar(36), premium varchar(36), sombrero varchar(36), mascota varchar(36), nombremascota varchar(36), banner varchar(36), particula varchar(36), colorchat varchar(36), disfras varchar(36), jugadores varchar(36), chat varchar(36), volar varchar(36),quarks int,puntos int)");
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

	public Rangos obtenerRango(Player player) {
		try {
			PreparedStatement e = this.connection.prepareStatement("SELECT rango FROM quarkcraftapi WHERE uuid = ?");
			e.setString(1, player.getUniqueId().toString());
			String power = "";

			for (ResultSet rs = e.executeQuery(); rs.next(); power = rs.getString("rango")) {
				;
			}

			e.close();
			return Rangos.getRango(power);
		} catch (SQLException arg4) {
			arg4.printStackTrace();
			return Rangos.USUARIO;
		}
	}

	public int getQuarks(Player player) {
		try {
			PreparedStatement e = this.connection.prepareStatement("SELECT quarks FROM quarkcraftapi WHERE uuid = ?");
			e.setString(1, player.getUniqueId().toString());
			int balance = 0;

			for (ResultSet rs = e.executeQuery(); rs.next(); balance = rs.getInt("quarks")) {
				;
			}

			e.close();
			return balance;
		} catch (SQLException arg4) {
			arg4.printStackTrace();
			return 0;
		}
	}

	public String getNombreMascota(Player player) {
		try {
			PreparedStatement e = this.connection
					.prepareStatement("SELECT nombremascota FROM quarkcraftapi WHERE uuid = ?");
			e.setString(1, player.getUniqueId().toString());
			String balance = "";

			for (ResultSet rs = e.executeQuery(); rs.next(); balance = rs.getString("nombremascota")) {
				;
			}

			e.close();
			return balance;
		} catch (SQLException arg4) {
			arg4.printStackTrace();
			return "No";
		}
	}

	public int getPuntos(Player player) {
		try {
			PreparedStatement e = this.connection.prepareStatement("SELECT puntos FROM quarkcraftapi WHERE uuid = ?");
			e.setString(1, player.getUniqueId().toString());
			int balance = 0;

			for (ResultSet rs = e.executeQuery(); rs.next(); balance = rs.getInt("puntos")) {
				;
			}

			e.close();
			return balance;
		} catch (SQLException arg4) {
			arg4.printStackTrace();
			return 0;
		}
	}

	public void CrearCuenta(Player player) {
		if (!this.TieneCuenta(player.getUniqueId().toString())) {
			try {
				PreparedStatement e = this.connection.prepareStatement(
						"INSERT INTO quarkcraftapi(uuid,nombre,rango,premium,sombrero,mascota,banner,particula,colorchat,disfras,nombremascota,jugadores,chat,volar,quarks,puntos) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				e.setString(1, player.getUniqueId().toString());
				e.setString(2, player.getName());
				e.setString(3, "Usuario");
				e.setString(4, "No");
				e.setString(5, "No");
				e.setString(6, "No");
				e.setString(7, "No");
				e.setString(8, "No");
				e.setString(9, "No");
				e.setString(10, "No");
				e.setString(11, "No");
				e.setString(12, "Si");
				e.setString(13, "Si");
				e.setString(14, "No");
				e.setInt(15, 0);
				e.setInt(16, 0);
				e.execute();
				e.close();
			} catch (SQLException arg2) {
				arg2.printStackTrace();
			}
		}

	}
	
	

	public boolean TieneCuenta(String player) {
		try {
			PreparedStatement e = this.connection.prepareStatement("SELECT uuid FROM quarkcraftapi WHERE uuid = ?");
			e.setString(1, player);
			ResultSet resultat = e.executeQuery();
			if (resultat.next()) {
				e.close();
				return true;
			} else {
				return false;
			}
		} catch (SQLException arg3) {
			arg3.printStackTrace();
			return false;
		}
	}

	public ChatColor obtenerColorDeChat(Player player) {
		try {
			PreparedStatement e = this.connection
					.prepareStatement("SELECT colorchat FROM quarkcraftapi WHERE uuid = ?");
			e.setString(1, player.getUniqueId().toString());
			ChatColor balance = ChatColor.valueOf("");

			for (ResultSet rs = e.executeQuery(); rs.next(); balance = ChatColor.valueOf(rs.getString("colorchat"))) {
				;
			}

			e.close();
			return balance;
		} catch (SQLException arg4) {
			arg4.printStackTrace();
			return null;
		}
	}

	public String ObtenerColorDeChatEquipado(Player player) {
		try {
			PreparedStatement e = this.connection.prepareStatement("SELECT * FROM `quarkcraftapi` WHERE uuid=?;");
			e.setString(1, player.getUniqueId().toString());
			ResultSet localResultSet = e.executeQuery();
			localResultSet.next();
			String localObject1 = localResultSet.getString("colorchat");
			return localObject1;
		} catch (SQLException arg4) {
			arg4.printStackTrace();
			return "No";
		}
	}

	public String ObtenerSombrero(Player player) {
		try {
			PreparedStatement e = this.connection.prepareStatement("SELECT * FROM `quarkcraftapi` WHERE uuid=?;");
			e.setString(1, player.getUniqueId().toString());
			ResultSet localResultSet = e.executeQuery();
			localResultSet.next();
			String localObject1 = localResultSet.getString("sombrero");
			return localObject1;
		} catch (SQLException arg4) {
			arg4.printStackTrace();
			return "No";
		}
	}
	public String ObtenerChat(Player player) {
		try {
			PreparedStatement e = this.connection.prepareStatement("SELECT * FROM `quarkcraftapi` WHERE uuid=?;");
			e.setString(1, player.getUniqueId().toString());
			ResultSet localResultSet = e.executeQuery();
			localResultSet.next();
			String localObject1 = localResultSet.getString("chat");
			return localObject1;
		} catch (SQLException arg4) {
			arg4.printStackTrace();
			return "No";
		}
	}
	
	public void PonerVerJugadores(Player player, String particula) {
		try {
			PreparedStatement e = this.connection
					.prepareStatement("UPDATE quarkcraftapi SET jugadores = ? WHERE uuid = ?");
			e.setString(1, particula);
			e.setString(2, player.getUniqueId().toString());
			e.executeUpdate();
			e.close();
		} catch (SQLException arg3) {
			arg3.printStackTrace();
		}

	}
	
	public String ObtenerVolar(Player player) {
		try {
			PreparedStatement e = this.connection.prepareStatement("SELECT * FROM `quarkcraftapi` WHERE uuid=?;");
			e.setString(1, player.getUniqueId().toString());
			ResultSet localResultSet = e.executeQuery();
			localResultSet.next();
			String localObject1 = localResultSet.getString("volar");
			return localObject1;
		} catch (SQLException arg4) {
			arg4.printStackTrace();
			return "No";
		}
	}
	
	public void PonerVolar(Player player, String particula) {
		try {
			PreparedStatement e = this.connection
					.prepareStatement("UPDATE quarkcraftapi SET volar = ? WHERE uuid = ?");
			e.setString(1, particula);
			e.setString(2, player.getUniqueId().toString());
			e.executeUpdate();
			e.close();
		} catch (SQLException arg3) {
			arg3.printStackTrace();
		}

	}
	
	public String ObtenerJugadores(Player player) {
		try {
			PreparedStatement e = this.connection.prepareStatement("SELECT * FROM `quarkcraftapi` WHERE uuid=?;");
			e.setString(1, player.getUniqueId().toString());
			ResultSet localResultSet = e.executeQuery();
			localResultSet.next();
			String localObject1 = localResultSet.getString("jugadores");
			return localObject1;
		} catch (SQLException arg4) {
			arg4.printStackTrace();
			return "No";
		}
	}

	public String ObtenerParticula(Player player) {
		try {
			PreparedStatement e = this.connection.prepareStatement("SELECT * FROM `quarkcraftapi` WHERE uuid=?;");
			e.setString(1, player.getUniqueId().toString());
			ResultSet localResultSet = e.executeQuery();
			localResultSet.next();
			String localObject1 = localResultSet.getString("particula");
			return localObject1;
		} catch (SQLException arg4) {
			arg4.printStackTrace();
			return "No";
		}
	}

	public String ObtenermASCOTA(Player player) {
		try {
			PreparedStatement e = this.connection.prepareStatement("SELECT * FROM `quarkcraftapi` WHERE uuid=?;");
			e.setString(1, player.getUniqueId().toString());
			ResultSet localResultSet = e.executeQuery();
			localResultSet.next();
			String localObject1 = localResultSet.getString("mascota");
			return localObject1;
		} catch (SQLException arg4) {
			arg4.printStackTrace();
			return "No";
		}
	}

	public String ObtenerDIsfras(Player player) {
		try {
			PreparedStatement e = this.connection.prepareStatement("SELECT * FROM `quarkcraftapi` WHERE uuid=?;");
			e.setString(1, player.getUniqueId().toString());
			ResultSet localResultSet = e.executeQuery();
			localResultSet.next();
			String localObject1 = localResultSet.getString("disfras");
			return localObject1;
		} catch (SQLException arg4) {
			arg4.printStackTrace();
			return "No";
		}
	}

	public String ObtenerBanner(Player player) {
		try {
			PreparedStatement e = this.connection.prepareStatement("SELECT * FROM `quarkcraftapi` WHERE uuid=?;");
			e.setString(1, player.getUniqueId().toString());
			ResultSet localResultSet = e.executeQuery();
			localResultSet.next();
			String localObject1 = localResultSet.getString("banner");
			return localObject1;
		} catch (SQLException arg4) {
			arg4.printStackTrace();
			return "No";
		}
	}

	public String EsPremium(String player) {
		try {
			PreparedStatement e = this.connection.prepareStatement("SELECT * FROM `quarkcraftapi` WHERE uuid=?;");
			e.setString(1, player);
			ResultSet localResultSet = e.executeQuery();
			localResultSet.next();
			String localObject1 = localResultSet.getString("premium");
			return localObject1;
		} catch (SQLException arg4) {
			arg4.printStackTrace();
			return "No";
		}
	}

	public String TieneSombrero(String player) {
		try {
			PreparedStatement e = this.connection.prepareStatement("SELECT * FROM `quarkcraftapi` WHERE uuid=?;");
			e.setString(1, player);
			ResultSet localResultSet = e.executeQuery();
			localResultSet.next();
			String localObject1 = localResultSet.getString("sombrero");
			return localObject1;
		} catch (SQLException arg4) {
			arg4.printStackTrace();
			return "No";
		}
	}

	public String TieneMascota(String player) {
		try {
			PreparedStatement e = this.connection.prepareStatement("SELECT * FROM `quarkcraftapi` WHERE uuid=?;");
			e.setString(1, player);
			ResultSet localResultSet = e.executeQuery();
			localResultSet.next();
			String localObject1 = localResultSet.getString("mascota");
			return localObject1;
		} catch (SQLException arg4) {
			arg4.printStackTrace();
			return "No";
		}
	}

	public String TieneBanner(String player) {
		try {
			PreparedStatement e = this.connection.prepareStatement("SELECT * FROM `quarkcraftapi` WHERE uuid=?;");
			e.setString(1, player);
			ResultSet localResultSet = e.executeQuery();
			localResultSet.next();
			String localObject1 = localResultSet.getString("banner");
			return localObject1;
		} catch (SQLException arg4) {
			arg4.printStackTrace();
			return "No";
		}
	}

	public String TieneParticula(String player) {
		try {
			PreparedStatement e = this.connection.prepareStatement("SELECT * FROM `quarkcraftapi` WHERE uuid=?;");
			e.setString(1, player);
			ResultSet localResultSet = e.executeQuery();
			localResultSet.next();
			String localObject1 = localResultSet.getString("particula");
			return localObject1;
		} catch (SQLException arg4) {
			arg4.printStackTrace();
			return "No";
		}
	}

	public String TieneColorChat(String player) {
		try {
			PreparedStatement e = this.connection.prepareStatement("SELECT * FROM `quarkcraftapi` WHERE uuid=?;");
			e.setString(1, player);
			ResultSet localResultSet = e.executeQuery();
			localResultSet.next();
			String localObject1 = localResultSet.getString("colorchat");
			return localObject1;
		} catch (SQLException arg4) {
			arg4.printStackTrace();
			return "No";
		}
	}

	public String TieneDisfras(String player) {
		try {
			PreparedStatement e = this.connection.prepareStatement("SELECT * FROM `quarkcraftapi` WHERE uuid=?;");
			e.setString(1, player);
			ResultSet localResultSet = e.executeQuery();
			localResultSet.next();
			String localObject1 = localResultSet.getString("disfras");
			return localObject1;
		} catch (SQLException arg4) {
			arg4.printStackTrace();
			return "No";
		}
	}

	public String TieneNombreMascota(String player) {
		try {
			PreparedStatement e = this.connection.prepareStatement("SELECT * FROM `quarkcraftapi` WHERE uuid=?;");
			e.setString(1, player);
			ResultSet localResultSet = e.executeQuery();
			localResultSet.next();
			String localObject1 = localResultSet.getString("nombremascota");
			return localObject1;
		} catch (SQLException arg4) {
			arg4.printStackTrace();
			return "No";
		}
	}

	public void PonerTieneSombrero(Player player, String tiene) {
		try {
			PreparedStatement e = this.connection
					.prepareStatement("UPDATE quarkcraftapi SET sombrero = ? WHERE uuid = ?");
			e.setString(1, tiene);
			e.setString(2, player.getUniqueId().toString());
			e.executeUpdate();
			e.close();
		} catch (SQLException arg3) {
			arg3.printStackTrace();
		}

	}

	public void PonerTieneMascota(Player player, String tiene) {
		try {
			PreparedStatement e = this.connection
					.prepareStatement("UPDATE quarkcraftapi SET mascota = ? WHERE uuid = ?");
			e.setString(1, tiene);
			e.setString(2, player.getUniqueId().toString());
			e.executeUpdate();
			e.close();
		} catch (SQLException arg3) {
			arg3.printStackTrace();
		}

	}
	
	public void PonerChat(Player player, String tiene) {
		try {
			PreparedStatement e = this.connection
					.prepareStatement("UPDATE quarkcraftapi SET chat = ? WHERE uuid = ?");
			e.setString(1, tiene);
			e.setString(2, player.getUniqueId().toString());
			e.executeUpdate();
			e.close();
		} catch (SQLException arg3) {
			arg3.printStackTrace();
		}

	}
	
	public void PonerJugadores(Player player, String tiene) {
		try {
			PreparedStatement e = this.connection
					.prepareStatement("UPDATE quarkcraftapi SET jugadores = ? WHERE uuid = ?");
			e.setString(1, tiene);
			e.setString(2, player.getUniqueId().toString());
			e.executeUpdate();
			e.close();
		} catch (SQLException arg3) {
			arg3.printStackTrace();
		}

	}



	public void PonerTieneBanner(Player player, String tiene) {
		try {
			PreparedStatement e = this.connection
					.prepareStatement("UPDATE quarkcraftapi SET banner = ? WHERE uuid = ?");
			e.setString(1, tiene);
			e.setString(2, player.getUniqueId().toString());
			e.executeUpdate();
			e.close();
		} catch (SQLException arg3) {
			arg3.printStackTrace();
		}

	}

	public void PonerTieneParticula(Player player, String tiene) {
		try {
			PreparedStatement e = this.connection
					.prepareStatement("UPDATE quarkcraftapi SET particula = ? WHERE uuid = ?");
			e.setString(1, tiene);
			e.setString(2, player.getUniqueId().toString());
			e.executeUpdate();
			e.close();
		} catch (SQLException arg3) {
			arg3.printStackTrace();
		}

	}

	public void PonerTieneColorChat(Player player, String tiene) {
		try {
			PreparedStatement e = this.connection
					.prepareStatement("UPDATE quarkcraftapi SET colorchat = ? WHERE uuid = ?");
			e.setString(1, tiene);
			e.setString(2, player.getUniqueId().toString());
			e.executeUpdate();
			e.close();
		} catch (SQLException arg3) {
			arg3.printStackTrace();
		}

	}

	public void PonerNombreMascota(Player player, String tiene) {
		try {
			PreparedStatement e = this.connection
					.prepareStatement("UPDATE quarkcraftapi SET nombremascota = ? WHERE uuid = ?");
			e.setString(1, tiene);
			e.setString(2, player.getUniqueId().toString());
			e.executeUpdate();
			e.close();
		} catch (SQLException arg3) {
			arg3.printStackTrace();
		}

	}

	public void PonerTieneDisfras(Player player, String tiene) {
		try {
			PreparedStatement e = this.connection
					.prepareStatement("UPDATE quarkcraftapi SET disfras = ? WHERE uuid = ?");
			e.setString(1, tiene);
			e.setString(2, player.getUniqueId().toString());
			e.executeUpdate();
			e.close();
		} catch (SQLException arg3) {
			arg3.printStackTrace();
		}

	}

	public String Jugadores(String player) {
		try {
			PreparedStatement e = this.connection.prepareStatement("SELECT * FROM `quarkcraftapi` WHERE uuid=?;");
			e.setString(1, player);
			ResultSet localResultSet = e.executeQuery();
			localResultSet.next();
			String localObject1 = localResultSet.getString("jugadores");
			return localObject1;
		} catch (SQLException arg4) {
			arg4.printStackTrace();
			return "No";
		}
	}

	public void PonerRango(Player player, Rangos rango) {
		try {
			PreparedStatement e = this.connection.prepareStatement("UPDATE quarkcraftapi SET rango = ? WHERE uuid = ?");
			e.setString(1, rango.getNombre());
			e.setString(2, player.getUniqueId().toString());
			e.executeUpdate();
			e.close();
		} catch (SQLException arg3) {
			arg3.printStackTrace();
		}

	}

	public void PonerJugadores(Player player, boolean ver) {
		try {
			PreparedStatement e = this.connection
					.prepareStatement("UPDATE quarkcraftapi SET jugadores = ? WHERE uuid = ?");
			if (ver) {
				e.setString(1, "Si");
			} else {
				e.setString(1, "No");
			}

			e.setString(2, player.getUniqueId().toString());
			e.executeUpdate();
			e.close();
		} catch (SQLException arg3) {
			arg3.printStackTrace();
		}

	}

	public void PonerPremium(Player player, String premium) {
		try {
			PreparedStatement e = this.connection
					.prepareStatement("UPDATE quarkcraftapi SET premium = ? WHERE uuid = ?");
			e.setString(1, premium);
			e.setString(2, player.getUniqueId().toString());
			e.executeUpdate();
			e.close();
		} catch (SQLException arg3) {
			arg3.printStackTrace();
		}

	}
}