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
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import pl.pvpanticheat.me.Main;
import pl.pvpanticheat.me.events.checks.CheckTypes;
import pl.pvpanticheat.me.events.utils.Detections;
import pl.pvpanticheat.me.settings.CheckDoubles;
import pl.pvpanticheat.me.settings.Colors;
import pl.pvpanticheat.me.settings.Messages;




public class Flight implements Listener {
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onMove(PlayerMoveEvent e)
	{
		Player p = e.getPlayer();
		Location to = e.getTo();
		Location from = e.getFrom();
		Vector vec = to.toVector();
		double i = vec.distance(from.toVector());
		if(p.getLocation().getBlock().getRelative(BlockFace.DOWN).getType().equals(Material.SPONGE)){
			return;
		}
		if(p.getGameMode().equals(GameMode.CREATIVE))
		{
			return;
		}
		if(p.getActivePotionEffects().equals(PotionEffectType.SPEED))
		{
			return;
		}
		if(p.getEntityId()==100)
		{
			return;
		}
		if(p.getVehicle() !=null)
		{
			return;
		}
		if(p.getAllowFlight() == true)
		{
			return;
		}
		if((p.getFallDistance() == 0.0F) && 
				(p.getLocation().getBlock().getRelative(BlockFace.UP).getType().equals(Material.AIR)))
		{
			if(i > CheckDoubles.MAX_FLIGHT)
			{
				if(p.isOnGround())
				{
					return;
				}
				e.setCancelled(true);
				p.teleport(e.getFrom());
				Main.getDetections().set("Player." + p.getName() + ".CheckType", CheckTypes.FLIGHT.toString());
				Main.getDetections().set("Player." + p.getName() + ".DetectionType", Detections.NORMAL.toString());
				Main.getDetections().save();
				for(Player staff : Bukkit.getOnlinePlayers()){
					if(staff.hasPermission("pac.staff")){
						staff.sendMessage(Colors.format(Messages.PREFIX + "Player &c" + p.getName() + "&f tried to &3&l" + CheckTypes.FLIGHT + " &fDetectionType: &6&l" + Detections.NORMAL));
					}
				}
			}
		}
	}
}