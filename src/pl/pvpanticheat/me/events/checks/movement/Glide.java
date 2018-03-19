package pl.pvpanticheat.me.events.checks.movement;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import pl.pvpanticheat.me.Main;
import pl.pvpanticheat.me.events.checks.CheckTypes;
import pl.pvpanticheat.me.events.utils.Detections;
import pl.pvpanticheat.me.settings.Colors;
import pl.pvpanticheat.me.settings.Messages;



public class Glide implements Listener {
	

	@EventHandler
	public void onMove(PlayerMoveEvent e){
		if((e.getTo().getY() - e.getFrom().getY() == -0.125D) 
			&& e.getTo().clone().subtract(0.0D, 1.0D, 0.0D).getBlock().getType().equals(Material.AIR)){
			e.setCancelled(true);
			e.getPlayer().teleport(e.getPlayer());
			Main.getDetections().set("Player." + e.getPlayer().getName() + ".CheckType", CheckTypes.GLIDE.toString());
			Main.getDetections().set("Player." + e.getPlayer().getName() + ".DetectionType", Detections.FAILED.toString());
			Main.getDetections().save();
			for(Player staff : Bukkit.getOnlinePlayers()){
				if(staff.hasPermission("pac.staff")){
					staff.sendMessage(Colors.format(Messages.PREFIX + "Player &c" + e.getPlayer().getName() + "&f tried to &3&l" + CheckTypes.GLIDE + " &fDetectionType: &4&l" + Detections.FAILED));
				}
			}
		}
	}
}
