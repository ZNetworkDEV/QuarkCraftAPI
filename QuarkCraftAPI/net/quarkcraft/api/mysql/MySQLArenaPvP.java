/** <a href="http://www.cpupk.com/decompiler">Eclipse Class Decompiler</a> plugin, Copyright (c) 2017 Chen Chao. **/
package net.quarkcraft.api.mysql;

import com.google.common.collect.Maps;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import net.quarkcraft.api.enums.DivisionesAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class MySQLArenaPvP {
	public Connection connection;
	private String urlbase;
	private String host;
	private String database;
	private String user;
	private String pass;
	static ArrayList<String> topasesinatos = new ArrayList();
	static Statement statement = null;

	public MySQLArenaPvP(String urlbase, String host, String database, String user, String pass) {
		this.urlbase = urlbase;
		this.host = host;
		this.database = database;
		this.user = user;
		this.pass = pass;
	}

	public void connection() {
		if (!this.isConnected()) {
			try {
				this.connection = DriverManager.getConnection(this.urlbase + this.host + "/" + this.database, this.user,
						this.pass);
				System.out.println("quarkcraftarenapvp CONECCION MYSQL ESTADISTICAS CONECTADA");
				Statement e = this.connection.createStatement();
				e.execute(
						"CREATE TABLE IF NOT EXISTS quarkcraftarenapvp(uuid VARCHAR(36) PRIMARY KEY NOT NULL, nombre VARCHAR(36), particula VARCHAR(36), efecto VARCHAR(36), colorchat VARCHAR(36), peticiones VARCHAR(36) ,puntos DECIMAL(65),ganadas DECIMAL(65),kills DECIMAL(65),muertes DECIMAL(65),rankeds DECIMAL(65),rankedstiempo timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,kitstiempo timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP, division VARCHAR(32))");
			} catch (SQLException arg1) {
				arg1.printStackTrace();
			}
		}

	}

	public void disconnect() {
		if (this.isConnected()) {
			try {
				this.connection.close();
				System.out.println("quarkcraftarenapvp CONECCION MYSQL ESTADISTICAS DESCONECTADA");
			} catch (SQLException arg1) {
				arg1.printStackTrace();
			}
		}

	}

	public boolean isConnected() {
		return this.connection != null;
	}

	public void createAccount(Player player) {
		if (!this.hasAccount(player)) {
			try {
				PreparedStatement e = this.connection.prepareStatement(
						"INSERT INTO quarkcraftarenapvp(uuid,nombre,efecto,particula,peticiones,puntos,ganadas,muertes,kills,division,rankeds) VALUES (?,?,?,?,?,?,?,?,?,?,?)");
				e.setString(1, player.getUniqueId().toString());
				e.setString(2, player.getName());
				e.setString(3, "Normal");
				e.setString(4, "Normal");
				e.setString(5, "Inventario");
				e.setInt(6, 1000);
				e.setInt(7, 0);
				e.setInt(8, 0);
				e.setInt(9, 0);
				e.setString(10, "OroIV");
				e.setInt(11, 20);
				e.execute();
				e.close();
				this.PonerRankedsTiempo(player);
			} catch (SQLException arg2) {
				arg2.printStackTrace();
			}
		}

	}

	public Map<String, Integer> getTopElo(int limit) {
		LinkedHashMap topScore = Maps.newLinkedHashMap();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			StringBuilder sqlException = new StringBuilder();
			sqlException.append("SELECT ");
			sqlException.append("`nombre`, `puntos` ");
			sqlException.append("FROM ");
			sqlException.append("`quarkcraftarenapvp` ");
			sqlException.append("ORDER BY `puntos` DESC LIMIT ?;");
			preparedStatement = this.connection.prepareStatement(sqlException.toString());
			preparedStatement.setInt(1, limit);
			resultSet = preparedStatement.executeQuery();

			while (resultSet != null && resultSet.next()) {
				topScore.put(resultSet.getString("nombre"), Integer.valueOf(resultSet.getInt("puntos")));
			}
		} catch (SQLException arg17) {
			arg17.printStackTrace();
		} finally {
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException arg16) {
					;
				}
			}

			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException arg15) {
					;
				}
			}

		}

		return topScore;
	}

	public void PonerDuelos(Player player, String particula) {
		try {
			PreparedStatement e = this.connection
					.prepareStatement("UPDATE quarkcraftarenapvp SET peticiones = ? WHERE uuid = ?");
			e.setString(1, particula);
			e.setString(2, player.getUniqueId().toString());
			e.executeUpdate();
			e.close();
		} catch (SQLException arg3) {
			arg3.printStackTrace();
		}

	}


	public DivisionesAPI getDivision(Player player) {
		try {
			PreparedStatement e = this.connection
					.prepareStatement("SELECT division FROM quarkcraftarenapvp WHERE uuid = ?");
			e.setString(1, player.getUniqueId().toString());
			String power = "";

			for (ResultSet rs = e.executeQuery(); rs.next(); power = rs.getString("division")) {
				;
			}

			e.close();
			return DivisionesAPI.getByName(power);
		} catch (SQLException arg4) {
			arg4.printStackTrace();
			return DivisionesAPI.BronceI;
		}
	}

	public DivisionesAPI getDivisionByUuid(String player) {
		try {
			PreparedStatement e = this.connection
					.prepareStatement("SELECT division FROM quarkcraftarenapvp WHERE nombre = ?");
			e.setString(1, player);
			String power = "";

			for (ResultSet rs = e.executeQuery(); rs.next(); power = rs.getString("division")) {
				;
			}

			e.close();
			return DivisionesAPI.getByName(power);
		} catch (SQLException arg4) {
			arg4.printStackTrace();
			return DivisionesAPI.BronceI;
		}
	}

	public void setDivision(Player player, DivisionesAPI rango) {
		try {
			PreparedStatement e = this.connection
					.prepareStatement("UPDATE quarkcraftarenapvp SET division = ? WHERE uuid = ?");
			e.setString(1, rango.toString());
			e.setString(2, player.getUniqueId().toString());
			e.executeUpdate();
			e.close();
		} catch (SQLException arg3) {
			arg3.printStackTrace();
		}

	}

	public boolean hasAccount(Player player) {
		try {
			PreparedStatement e = this.connection
					.prepareStatement("SELECT uuid FROM quarkcraftarenapvp WHERE uuid = ?");
			e.setString(1, player.getUniqueId().toString());
			ResultSet resultat = e.executeQuery();
			boolean hasAccount = resultat.next();
			e.close();
			return hasAccount;
		} catch (SQLException arg4) {
			arg4.printStackTrace();
			return false;
		}
	}

	public int getELO(Player player) {
		try {
			PreparedStatement e = this.connection
					.prepareStatement("SELECT puntos FROM quarkcraftarenapvp WHERE uuid = ?");
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

	public void addELO(Player player, int amount) {
		int balance = this.getELO(player);
		int newbalance = balance + amount;

		try {
			PreparedStatement e = this.connection
					.prepareStatement("UPDATE quarkcraftarenapvp SET puntos = ? WHERE uuid = ?");
			e.setInt(1, newbalance);
			e.setString(2, player.getUniqueId().toString());
			e.executeUpdate();
			e.close();
		} catch (SQLException arg5) {
			arg5.printStackTrace();
		}

	}

	public void addGanadas(Player player, int amount) {
		int balance = this.getGanadas(player);
		int newbalance = balance + amount;

		try {
			PreparedStatement e = this.connection
					.prepareStatement("UPDATE quarkcraftarenapvp SET ganadas = ? WHERE uuid = ?");
			e.setInt(1, newbalance);
			e.setString(2, player.getUniqueId().toString());
			e.executeUpdate();
			e.close();
		} catch (SQLException arg5) {
			arg5.printStackTrace();
		}

	}

	public int getGanadas(Player player) {
		try {
			PreparedStatement e = this.connection
					.prepareStatement("SELECT ganadas FROM quarkcraftarenapvp WHERE uuid = ?");
			e.setString(1, player.getUniqueId().toString());
			int balance = 0;

			for (ResultSet rs = e.executeQuery(); rs.next(); balance = rs.getInt("ganadas")) {
				;
			}

			e.close();
			return balance;
		} catch (SQLException arg4) {
			arg4.printStackTrace();
			return 0;
		}
	}

	public void removeRankeds(Player player, int amount) {
		int balance = this.getRankeds(player);
		int newbalance = balance - amount;

		try {
			PreparedStatement e = this.connection
					.prepareStatement("UPDATE quarkcraftarenapvp SET rankeds = ? WHERE uuid = ?");
			e.setInt(1, newbalance);
			e.setString(2, player.getUniqueId().toString());
			e.executeUpdate();
			e.close();
		} catch (SQLException arg5) {
			arg5.printStackTrace();
		}

	}

	public void addRankeds(Player player, int amount) {
		int balance = this.getRankeds(player);
		int newbalance = balance + amount;

		try {
			PreparedStatement e = this.connection
					.prepareStatement("UPDATE quarkcraftarenapvp SET rankeds = ? WHERE uuid = ?");
			e.setInt(1, newbalance);
			e.setString(2, player.getUniqueId().toString());
			e.executeUpdate();
			e.close();
		} catch (SQLException arg5) {
			arg5.printStackTrace();
		}

	}

	public void setRankeds(Player player, int amount) {
		int newbalance = amount;

		try {
			PreparedStatement e = this.connection
					.prepareStatement("UPDATE quarkcraftarenapvp SET rankeds = ? WHERE uuid = ?");
			e.setInt(1, newbalance);
			e.setString(2, player.getUniqueId().toString());
			e.executeUpdate();
			e.close();
		} catch (SQLException arg4) {
			arg4.printStackTrace();
		}

	}

	public void PonerTiempo(Player player) {
		try {
			PreparedStatement e = this.connection.prepareStatement(
					"UPDATE `quarkcraftarenapvp` SET kitstiempo=(now()+ INTERVAL 1 DAY) WHERE uuid=?;");
			e.setString(1, player.getUniqueId().toString());
			e.executeUpdate();
		} catch (SQLException arg2) {
			arg2.printStackTrace();
		}

	}

	public void PonerEfecto(Player player, String efecto) {
		try {
			PreparedStatement e = this.connection
					.prepareStatement("UPDATE quarkcraftarenapvp SET efecto = ? WHERE uuid = ?");
			e.setString(1, efecto);
			e.setString(2, player.getUniqueId().toString());
			e.executeUpdate();
			e.close();
		} catch (SQLException arg3) {
			arg3.printStackTrace();
		}

	}

	public void PonerParticula(Player player, String efecto) {
		try {
			PreparedStatement e = this.connection
					.prepareStatement("UPDATE quarkcraftarenapvp SET particula = ? WHERE uuid = ?");
			e.setString(1, efecto);
			e.setString(2, player.getUniqueId().toString());
			e.executeUpdate();
			e.close();
		} catch (SQLException arg3) {
			arg3.printStackTrace();
		}

	}


	public void PonerTiempoKits(Player player) {
		try {
			PreparedStatement e = this.connection.prepareStatement(
					"UPDATE `quarkcraftarenapvp` SET kitstiempo=(now()+ INTERVAL 1 DAY) WHERE uuid=?;");
			e.setString(1, player.getUniqueId().toString());
			e.executeUpdate();
		} catch (SQLException arg2) {
			arg2.printStackTrace();
		}

	}

	public void PonerRankedsTiempo(Player player) {
		try {
			PreparedStatement e = this.connection.prepareStatement(
					"UPDATE `quarkcraftarenapvp` SET rankedstiempo=(now()+ INTERVAL 1 DAY) WHERE uuid=?;");
			e.setString(1, player.getUniqueId().toString());
			e.executeUpdate();
		} catch (SQLException arg2) {
			arg2.printStackTrace();
		}

	}

	public void ObtenerTiempoMensaje(Player player) {
		try {
			PreparedStatement e = this.connection
					.prepareStatement("SELECT rankedstiempo FROM `quarkcraftarenapvp` WHERE uuid=?;");
			e.setString(1, player.getUniqueId().toString());
			ResultSet localResultSet = e.executeQuery();
			localResultSet.next();
			Timestamp localTimestamp2 = localResultSet.getTimestamp("rankedstiempo");
			long l2 = localTimestamp2.getTime() - System.currentTimeMillis();
			long l4 = TimeUnit.MILLISECONDS.toSeconds(l2) % 60L;
			long l6 = TimeUnit.MILLISECONDS.toHours(l2);
			long l8 = TimeUnit.MILLISECONDS.toMinutes(l2) / 24L;
			player.sendMessage(
					ChatColor.RED + "Todavia tienes que esperar " + ChatColor.GRAY + l6 + "h " + l8 + "m " + l4 + "s ");
		} catch (SQLException arg12) {
			arg12.printStackTrace();
		}

	}

	public int getRankeds(Player player) {
		try {
			PreparedStatement e = this.connection
					.prepareStatement("SELECT rankeds FROM quarkcraftarenapvp WHERE uuid = ?");
			e.setString(1, player.getUniqueId().toString());
			int balance = 0;

			for (ResultSet rs = e.executeQuery(); rs.next(); balance = rs.getInt("rankeds")) {
				;
			}

			e.close();
			return balance;
		} catch (SQLException arg4) {
			arg4.printStackTrace();
			return 0;
		}
	}

	public String TieneParticula(String player) {
		try {
			PreparedStatement e = this.connection.prepareStatement("SELECT * FROM `quarkcraftarenapvp` WHERE uuid=?;");
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
	
	public String TieneEfecto(String player) {
		try {
			PreparedStatement e = this.connection.prepareStatement("SELECT * FROM `quarkcraftarenapvp` WHERE uuid=?;");
			e.setString(1, player);
			ResultSet localResultSet = e.executeQuery();
			localResultSet.next();
			String localObject1 = localResultSet.getString("efecto");
			return localObject1;
		} catch (SQLException arg4) {
			arg4.printStackTrace();
			return "No";
		}
	}

	public String TienePeticiones(String player) {
		try {
			PreparedStatement e = this.connection.prepareStatement("SELECT * FROM `quarkcraftarenapvp` WHERE uuid=?;");
			e.setString(1, player);
			ResultSet localResultSet = e.executeQuery();
			localResultSet.next();
			String localObject1 = localResultSet.getString("peticiones");
			return localObject1;
		} catch (SQLException arg4) {
			arg4.printStackTrace();
			return "No";
		}
	}

	public void addMuertes(Player player, int amount) {
		int balance = this.getMuertes(player);
		int newbalance = balance + amount;

		try {
			PreparedStatement e = this.connection
					.prepareStatement("UPDATE quarkcraftarenapvp SET muertes = ? WHERE uuid = ?");
			e.setInt(1, newbalance);
			e.setString(2, player.getUniqueId().toString());
			e.executeUpdate();
			e.close();
		} catch (SQLException arg5) {
			arg5.printStackTrace();
		}

	}

	public int getMuertes(Player player) {
		try {
			PreparedStatement e = this.connection
					.prepareStatement("SELECT muertes FROM quarkcraftarenapvp WHERE uuid = ?");
			e.setString(1, player.getUniqueId().toString());
			int balance = 0;

			for (ResultSet rs = e.executeQuery(); rs.next(); balance = rs.getInt("muertes")) {
				;
			}

			e.close();
			return balance;
		} catch (SQLException arg4) {
			arg4.printStackTrace();
			return 0;
		}
	}

	public void addKills(Player player, int amount) {
		int balance = this.getKills(player);
		int newbalance = balance + amount;

		try {
			PreparedStatement e = this.connection
					.prepareStatement("UPDATE quarkcraftarenapvp SET kills = ? WHERE uuid = ?");
			e.setInt(1, newbalance);
			e.setString(2, player.getUniqueId().toString());
			e.executeUpdate();
			e.close();
		} catch (SQLException arg5) {
			arg5.printStackTrace();
		}

	}

	public int getKills(Player player) {
		try {
			PreparedStatement e = this.connection
					.prepareStatement("SELECT kills FROM quarkcraftarenapvp WHERE uuid = ?");
			e.setString(1, player.getUniqueId().toString());
			int balance = 0;

			for (ResultSet rs = e.executeQuery(); rs.next(); balance = rs.getInt("kills")) {
				;
			}

			e.close();
			return balance;
		} catch (SQLException arg4) {
			arg4.printStackTrace();
			return 0;
		}
	}

	public void setELO(Player player, int amount) {
		int newbalance = amount;

		try {
			PreparedStatement e = this.connection
					.prepareStatement("UPDATE quarkcraftarenapvp SET puntos = ? WHERE uuid = ?");
			e.setInt(1, newbalance);
			e.setString(2, player.getUniqueId().toString());
			e.executeUpdate();
			e.close();
		} catch (SQLException arg4) {
			arg4.printStackTrace();
		}

	}
}