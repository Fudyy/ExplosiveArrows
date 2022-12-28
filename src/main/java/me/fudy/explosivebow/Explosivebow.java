package me.fudy.explosivebow;

import me.fudy.explosivebow.commands.GetBowCommand;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Explosivebow extends JavaPlugin {
    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getServer().sendMessage(Component.text("Explosive arrows have been initialized!").color(TextColor.color(0,255,0)));
        this.getCommand("getexplosivebow").setExecutor(new GetBowCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getServer().sendMessage(Component.text("Explosive arrows is shutting down!").color(TextColor.color(255,0,0)));
    }
}
