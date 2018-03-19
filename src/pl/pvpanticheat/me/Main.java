package pl.pvpanticheat.me;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import pl.pvpanticheat.me.events.Join;
import pl.pvpanticheat.me.events.checks.Speed;
import pl.pvpanticheat.me.events.checks.combat.FastBow;
import pl.pvpanticheat.me.events.checks.combat.KillAura;
import pl.pvpanticheat.me.events.checks.movement.Flight;
import pl.pvpanticheat.me.events.checks.movement.Glide;
import pl.pvpanticheat.me.events.checks.movement.NoFall;
import pl.pvpanticheat.me.events.checks.movement.WaterWalk;
import pl.pvpanticheat.me.settings.Configurations;




public class Main extends JavaPlugin implements Listener {
	
	public static CommandSender cs = Bukkit.getConsoleSender();
	
	
	private static Main instance;
	private static Configurations players;
	private static Configurations detections;
	public static FileConfiguration config;


	public static File conf;
	
	public void onEnable(){
		
		
		cs.sendMessage("Loading PvPAntiCheat V1.0");
	
		
		cs.sendMessage("Retrieving Player Logs and Detections.");
		
	
		
		instance = this;
		config = getConfig();
		config.options().copyDefaults(true);
		conf = new  File(getDataFolder(), "config.yml");
		players = new Configurations(this, "players");
		detections = new Configurations(this, "detections");
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		saveConfig();
		saveDefaultConfig();
		register();
		
		
		
		
	
		
	}
	public static Main getInstance(){
		
    	return instance;
	}
	public void register(){
		Bukkit.getServer().getPluginManager().registerEvents(new Join(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new Speed(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new KillAura(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new Flight(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new Glide(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new WaterWalk(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new NoFall(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new FastBow(), this);
	}
	public static Configurations getPlayers() {
		return players;
	}
	public static Configurations getDetections() {
		return detections;
	}
}