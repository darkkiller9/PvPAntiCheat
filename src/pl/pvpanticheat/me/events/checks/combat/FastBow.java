package pl.pvpanticheat.me.events.checks.combat;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;

import pl.pvpanticheat.me.Main;
import pl.pvpanticheat.me.events.checks.CheckTypes;
import pl.pvpanticheat.me.events.utils.Detections;
import pl.pvpanticheat.me.settings.Colors;
import pl.pvpanticheat.me.settings.Messages;


public class FastBow implements Listener{
	
	static HashMap<Player, Long> lastbow = new HashMap<>();
	
	@EventHandler
	public void onShot(EntityShootBowEvent e){
		
	
		if(!(e.getEntity() instanceof Player)){
			return;
		}
		
		Player p = (Player) e.getEntity();
		if(!lastbow.containsKey(p)){
			lastbow.put(p, 0L);
		}
		if(e.getForce() !=1.0D){
			return;
		}
		if(lastbow.get(p) == 0L){
			lastbow.put(p, System.currentTimeMillis());
			return;
		}
		if(System.currentTimeMillis() - lastbow.get(p) < 500L){
			e.getProjectile().remove();
			e.setCancelled(true);
			Main.getDetections().set("Player." + p.getName() + ".CheckType", CheckTypes.FAST_BOW.toString());
			Main.getDetections().set("Player." + p.getName() + ".DetectionType", Detections.FAILED.toString());
			Main.getDetections().save();
			for(Player staff : Bukkit.getOnlinePlayers()){
				if(staff.hasPermission("pac.staff")){
					staff.sendMessage(Colors.format(Messages.PREFIX + "Player &c" + p.getName() + "&f tried to &3&l" + CheckTypes.FAST_BOW + " &fDetectionType: &4&l" + Detections.FAILED));
				}
			}
		}
	}
	
}