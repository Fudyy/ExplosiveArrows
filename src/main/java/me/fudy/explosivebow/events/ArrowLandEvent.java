package me.fudy.explosivebow.events;

import com.destroystokyo.paper.ParticleBuilder;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.persistence.PersistentDataType;

import javax.xml.parsers.SAXParser;

public class ArrowLandEvent implements Listener {
    private final NamespacedKey bowkey;
    private final NamespacedKey arrowkey;

    public ArrowLandEvent(NamespacedKey bowkey, NamespacedKey arrowkey) {
        this.bowkey = bowkey;
        this.arrowkey = arrowkey;
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
    }

    @EventHandler
    public void onArrowShot(EntityShootBowEvent event){
        String PDC = event.getBow().getItemMeta().getPersistentDataContainer().get(bowkey, PersistentDataType.STRING);
        if(!PDC.equals("explosivebow") || PDC == null) return;

        event.getProjectile().getPersistentDataContainer().set(arrowkey, PersistentDataType.STRING, "explosivearrow");
        Arrow arrow = (Arrow) event.getProjectile();
        arrow.setCritical(false);
    }
}
