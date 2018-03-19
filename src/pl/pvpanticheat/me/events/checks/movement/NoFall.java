package pl.pvpanticheat.me.events.checks.movement;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import pl.pvpanticheat.me.Main;
import pl.pvpanticheat.me.events.checks.CheckTypes;
import pl.pvpanticheat.me.events.utils.Detections;
import pl.pvpanticheat.me.settings.CheckDoubles;
import pl.pvpanticheat.me.settings.Colors;
import pl.pvpanticheat.me.settings.Messages;



public class NoFall implements Listener{
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onMove(PlayerMoveEvent e){
		Player p = e.getPlayer();
		Location to = e.getTo();
		Location From = e.getFrom();
		
		Vector vec = to.toVector().setY(0.0D);
		double i = vec.distance(From.toVector().setY(0.0D));
		if(p.getLocation().getBlock().getRelative(BlockFace.DOWN).getType().equals(Material.SPONGE)){
			return;
		}
		if(p.getGameMode().equals(GameMode.CREATIVE)){
			return;
		}
		if(p.getEntityId() == 100){
			return;
		}
		if(p.getVehicle() !=null){
			return;
		}
		if(p.getAllowFlight() == true){
			return;
		}
		if(From.getY() < to.getY()){
			return;
		}
		if((p.getFallDistance() == 0.0F) && 
				(i > CheckDoubles.MAX_FALL) && p.isOnGround()){
			e.setCancelled(true);
			p.setHealth(0);
			Main.getDetections().set("Player." + e.getPlayer().getName() + ".CheckType", CheckTypes.NO_FALL.toString());
			Main.getDetections().set("Player." + e.getPlayer().getName() + ".DetectionType", Detections.NORMAL.toString());
			Main.getDetections().save();
			for(Player staff : Bukkit.getOnlinePlayers()){
				if(staff.hasPermission("pac.staff")){
					staff.sendMessage(Colors.format(Messages.PREFIX + "Player &c" + e.getPlayer().getName() + "&f tried to &3&l" + CheckTypes.NO_FALL + " &fDetectionType: &6&l" + Detections.NORMAL));
				}
			}
		}
	}

}