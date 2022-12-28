package me.fudy.explosivebow;

import me.fudy.explosivebow.commands.GetBowCommand;
import me.fudy.explosivebow.events.ArrowLandEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

public final class Explosivebow extends JavaPlugin {
    public NamespacedKey bowkey = new NamespacedKey(this, "explosivebow");
    public NamespacedKey arrowkey = new NamespacedKey(this, "explosivearrow");
    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getServer().sendMessage(Component.text("[Explosive bow] Explosive arrows have been initialized!").color(TextColor.color(0,255,0)));
        this.getCommand("getexplosivebow").setExecutor(new GetBowCommand(bowkey));
        this.getServer().getPluginManager().registerEvents(new ArrowLandEvent(bowkey, arrowkey), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getServer().sendMessage(Component.text("Explosive arrows is shutting down!").color(TextColor.color(255,0,0)));
    }
}
