package me.fudy.explosivebow.events;

import com.destroystokyo.paper.ParticleBuilder;
import me.fudy.explosivebow.Explosivebow;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.entity.Arrow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

public class ArrowHandler implements Listener {
    private final NamespacedKey bowkey;
    private final NamespacedKey arrowkey;

    private static Explosivebow plugin;

    public ArrowHandler(NamespacedKey bowkey, NamespacedKey arrowkey, Explosivebow plugin) {
        this.bowkey = bowkey;
        this.arrowkey = arrowkey;
        this.plugin = plugin;
    }

    @EventHandler
    public void onArrowLand(ProjectileHitEvent event){
        String PDC = event.getEntity().getPersistentDataContainer().get(arrowkey, PersistentDataType.STRING);
        if (!PDC.equals("explosivearrow") || PDC == null) return;

        //Get hit location
        Location location = null;
        if(event.getHitBlock() != null) location = event.getHitBlock().getLocation();
        if(event.getHitEntity() != null) location = event.getHitEntity().getLocation();

        //Explosion particles
        ParticleBuilder particles = new ParticleBuilder(Particle.CLOUD).count(100);
        particles.location(location);
        particles.spawn();
        event.getEntity().remove();
        location.createExplosion(2, false, false);
    }

    @EventHandler
    public void onArrowShot(EntityShootBowEvent event){
        String PDC = event.getBow().getItemMeta().getPersistentDataContainer().get(bowkey, PersistentDataType.STRING);
        if(!PDC.equals("explosivebow") || PDC == null) return;

        event.getProjectile().getPersistentDataContainer().set(arrowkey, PersistentDataType.STRING, "explosivearrow");
        Arrow arrow = (Arrow) event.getProjectile();
        arrow.setCritical(false);

        ParticleBuilder particles = new ParticleBuilder(Particle.CRIT_MAGIC).count(1);
        new BukkitRunnable(){
            @Override
            public void run() {
                if(arrow.isDead()){
                    cancel();
                } else {
                    particles.location(arrow.getLocation()).spawn();
                }
            }
        }.runTaskTimer(plugin, 0, 0);
    }
}
