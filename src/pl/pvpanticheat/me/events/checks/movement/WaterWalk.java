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




public class WaterWalk implements Listener
{
	
	@EventHandler
	public void onMove(PlayerMoveEvent e)
	{
		Player p = e.getPlayer();
		Location to = e.getTo();
		Location from = e.getFrom();
		Vector vec = to.toVector();
		double i = vec.distance(from.toVector());
		if(p.getGameMode().equals(GameMode.CREATIVE))
		{
			return;
		}
		if(p.getEntityId()==
				100){
			
			return;
		}
		if(p.getVehicle() !=null){
			return;
		}
		if(p.getAllowFlight() == true){
			return;
		}
		if((i > CheckDoubles.MIN_WATER_WALK) && (i < 
				CheckDoubles.MAX_WATER_WALK)){
			if(p.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == 
					Material.WATER){
				return;
			}
			if(p.getLocation().getBlock().getRelative(BlockFace.DOWN).isLiquid())
			{
				p.setHealth(0);
				Main.getDetections().set("Player." + e.getPlayer().getName() + ".CheckType", CheckTypes.WATER_WALK.toString());
				Main.getDetections().set("Player." + e.getPlayer().getName() + ".DetectionType", Detections.FAILED.toString());
				Main.getDetections().save();
				for(Player staff : Bukkit.getOnlinePlayers()){
					if(staff.hasPermission("pac.staff")){
						staff.sendMessage(Colors.format(Messages.PREFIX + "Player &c" + e.getPlayer().getName() + "&f tried to &3&l" + CheckTypes.WATER_WALK + " &fDetectionType: &4&l" + Detections.FAILED));
					}
				}
			}
		}
	}

}
