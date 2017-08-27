/** <a href="http://www.cpupk.com/decompiler">Eclipse Class Decompiler</a> plugin, Copyright (c) 2017 Chen Chao. **/
package net.quarkcraft.api.enums;

import org.bukkit.ChatColor;

public enum Rangos {
   USUARIO("Usuario", ChatColor.GRAY, ChatColor.WHITE, false),
   AYUDANTE("Ayudante", ChatColor.GREEN, ChatColor.WHITE, true),
   YOUTUBER("Youtuber", ChatColor.RED, ChatColor.WHITE, true),
   MODERADOR("Mod", ChatColor.AQUA, ChatColor.WHITE, true),
   ADMINISTRADOR("Admin", ChatColor.RED, ChatColor.WHITE, true),
   Dueño("Dueño", ChatColor.DARK_RED, ChatColor.RED, true);

   private String nombre;
   private ChatColor color;
   private ChatColor colorchat;
   private boolean esrango;

   private Rangos(String nombre, ChatColor color, ChatColor colorchat, boolean esrango) {
      this.nombre = nombre;
      this.color = color;
      this.colorchat = colorchat;
      this.esrango = esrango;
   }

   public String getNombre() {
      return this.nombre;
   }

   public ChatColor getColor() {
      return this.color;
   }

   public boolean esRango() {
      return this.esrango;
   }

   public ChatColor getChatColor() {
      return this.colorchat;
   }

   public static Rangos getRango(String name) {
      Rangos[] arg3;
      int arg2 = (arg3 = values()).length;

      for(int arg1 = 0; arg1 < arg2; ++arg1) {
         Rangos test = arg3[arg1];
         if(test.getNombre().equalsIgnoreCase(name)) {
            return test;
         }
      }

      return null;
   }
}