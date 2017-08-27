/** <a href="http://www.cpupk.com/decompiler">Eclipse Class Decompiler</a> plugin, Copyright (c) 2017 Chen Chao. **/
package net.quarkcraft.api.enums;

import org.bukkit.ChatColor;

public enum DivisionesAPI {
	BronceI("Bronce I", "[B-I]", ChatColor.GOLD), BronceII("Bronce II", "[B-II]", ChatColor.GOLD), BronceIII(
			"Bronce III", "[B-III]", ChatColor.GOLD), BronceIV("Bronce IV", "[B-IV]", ChatColor.GOLD), OroI("Oro I",
					"[O-I]", ChatColor.YELLOW), OroII("Oro II", "[O-II]", ChatColor.YELLOW), OroIII("Oro III",
							"[O-III]", ChatColor.YELLOW), OroIV("Oro IV", "[O-IV]", ChatColor.YELLOW), DiamanteI(
									"Diamante I", "[D-I]",
									ChatColor.AQUA), DiamanteII("Diamante II", "[D-II]", ChatColor.AQUA), DiamanteIII(
											"Diamante III", "[D-III]", ChatColor.AQUA), DiamanteIV("Diamante IV",
													"[D-IV]", ChatColor.AQUA), MaestroI("Maestro I", "[M-I]",
															ChatColor.DARK_PURPLE), MaestroII("Maestro II", "[M-II]",
																	ChatColor.DARK_PURPLE), MaestroIII("Maestro III",
																			"[M-III]",
																			ChatColor.DARK_PURPLE), MaestroIV(
																					"Maestro IV", "[M-IV]",
																					ChatColor.DARK_PURPLE);

	private String nombre;
	private String nombretab;
	private ChatColor color;

	private DivisionesAPI(String nombre, String tabnombre, ChatColor color) {
		this.nombre = nombre;
		this.nombretab = tabnombre;
		this.color = color;
	}

	public String getNombre() {
		return this.nombre;
	}

	public String getNombreTab() {
		return this.nombretab;
	}

	public ChatColor getDivisionColor() {
		return this.color;
	}

	public static DivisionesAPI getByName(String name) {
		DivisionesAPI[] arg3;
		int arg2 = (arg3 = values()).length;

		for (int arg1 = 0; arg1 < arg2; ++arg1) {
			DivisionesAPI group = arg3[arg1];
			if (group.name().equalsIgnoreCase(name)) {
				return group;
			}
		}

		return null;
	}

	public static boolean contains(String test) {
		DivisionesAPI[] arg3;
		int arg2 = (arg3 = values()).length;

		for (int arg1 = 0; arg1 < arg2; ++arg1) {
			DivisionesAPI c = arg3[arg1];
			if (c.name().equals(test)) {
				return true;
			}
		}

		return false;
	}
}