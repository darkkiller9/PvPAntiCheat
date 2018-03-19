package pl.pvpanticheat.me.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import pl.pvpanticheat.me.Main;
import pl.pvpanticheat.me.settings.Messages;

public class Join implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e){
	Player p = e.getPlayer();
	String uuid = p.getUniqueId().toString();
	if(!p.hasPlayedBefore()){
		System.out.println(Messages.PREFIX + " Player " + p.getName() + " joined for the first time!");
		if(!Main.getPlayers().contains(p.getName())){
			Main.getPlayers().set("Player." + p.getName() + ".UUID", uuid);
			Main.getPlayers().save();
		}
	}
	System.out.println(Messages.PREFIX + " Player " + p.getName() + " has joined the game.");
	Main.getPlayers().set("Player." + p.getName() + ".UUID", uuid);
	Main.getPlayers().save();
	}
}
			
		