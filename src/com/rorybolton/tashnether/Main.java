package com.rorybolton.tashnether;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
	
	Random randomGenerator = new Random();
	public RoryUtil roryUtilities = new RoryUtil();
	Main plugin = this;
	
	public boolean netherFind(Entity sender, World nether){
		Location location = new Location(nether, 10 + randomGenerator.nextInt(99980), 0, 10 + randomGenerator.nextInt(99980), ((Entity) sender).getLocation().getYaw(), ((Entity) sender).getLocation().getPitch());
		for(int i = 118; i > 0; i--){
			location.setY(i);
			if ((location.getBlock().getType() == Material.NETHERRACK) || (location.getBlock().getType() == Material.NETHER_BRICK) || (location.getBlock().getType() == Material.QUARTZ_ORE)){
				if ((location.add(0, 1, 0).getBlock().getType() == Material.AIR) && (location.add(0, 1, 0).getBlock().getType() == Material.AIR) && (location.add(0, 1, 0).getBlock().getType() == Material.AIR)){
					((Entity) sender).teleport(location.add(0.5, -3, 0.5));
					return false;
				}
				else{
					location.add(0, -3, 0);
				}
			}
		}
		return true;
	}
	
	@Override
	public void onEnable(){
		getLogger().info("TashNether ENABLED");
		getServer().getPluginManager().registerEvents(this, this);
		roryUtilities.loadConfiguration(plugin);
	}
	
	@Override
	public void onDisable(){
		getLogger().info("TashNether DISABLED");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if ((cmd.getName().equalsIgnoreCase("nether"))){
			//((Entity) sender).sendMessage(args.toString());
			if (args.length == 0){
				((Entity) sender).sendMessage(plugin.getConfig().getString("data.text.netherwarning"));
				return true;
			}
			if (args[0].contains("y")){
				String overworldName = plugin.getConfig().getString("data.world.overworld");
				World overworld = plugin.getServer().getWorld(overworldName);
				String netherworldName = plugin.getConfig().getString("data.world.netherworld");
				World nether = plugin.getServer().getWorld(netherworldName);
				
				World playerWorld = ((Entity) sender).getLocation().getWorld();
				//getLogger().info(playerWorld.getName().toString());
				
				if (playerWorld != nether){
					while(netherFind(((Entity) sender), nether)){
						
					}
					
				}
				
				if (playerWorld == nether){
					((Entity) sender).teleport(overworld.getSpawnLocation());
				}
				return true;
			}
			
		}
		return false;
	}
}
