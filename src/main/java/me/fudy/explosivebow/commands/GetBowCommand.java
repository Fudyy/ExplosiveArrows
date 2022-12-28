package me.fudy.explosivebow.commands;

import me.fudy.explosivebow.Explosivebow;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class GetBowCommand implements CommandExecutor {

    private NamespacedKey key;
    public GetBowCommand(NamespacedKey bowkey) {
        this.key = bowkey;
    }

    private Component bowname(){
        Component name = Component.text("---").color(TextColor.color(0,0,0))
                .append(Component.text("Explosive bow").color(TextColor.color(255,106,0)).decoration(TextDecoration.BOLD, true))
                .append(Component.text("---").color(TextColor.color(0,0,0)));
        return name;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player = null;

        //Verify if the console is sending the command
        if (!(sender instanceof Player) && args.length < 1) {
            sender.sendMessage(ChatColor.RED + "[Explosive bow] Specify a player to give the bow with: /getexplosivebow <Player>.");
            return true;
        }
        if (!(sender instanceof Player) && args.length > 0) player = Bukkit.getServer().getPlayer(args[0]);

        //Logic if a player is sending the command
        if ((sender instanceof Player) && args.length < 1){
                player = ((Player) sender).getPlayer();
        }
        if ((sender instanceof Player) && args.length > 0){
            player = Bukkit.getServer().getPlayer(args[0]);
        }

        //Player verification
        if (player == null || !player.isOnline()) {
            sender.sendMessage(ChatColor.RED + "The specified player is not online!");
            return true;
        }

        ItemStack bow = new ItemStack(Material.BOW);
        ItemMeta bowmeta = bow.getItemMeta();

        //PDC to differentiate this bow from normal bows.

        bowmeta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "explosivebow");

        bowmeta.displayName(bowname());
        bow.setItemMeta(bowmeta);

        player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1, 1);
        player.getInventory().addItem(bow);
        return true;
    }
}
