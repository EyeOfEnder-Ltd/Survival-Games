package com.eyeofender.survivalgames.listener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.WeakHashMap;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Chest;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import com.eyeofender.survivalgames.SurvivalGames;
import com.eyeofender.survivalgames.handlers.EnchantHandler;

public class ChestListener implements Listener {
    SurvivalGames plugin;
    
    private List<ItemStack> chestsItems = new ArrayList<ItemStack>();

    public ChestListener(SurvivalGames plugin) {
        this.plugin = plugin;
        loadChestItems();
    }

    public void loadChestItems() {
        List<String> rawChestItems = plugin.getConfig().getStringList("ChestItems");

        for (String raw : rawChestItems) {
            ItemStack[] is = parseItem(raw);
            ItemStack theItem = is[0];
            theItem.setAmount(is.length);

            chestsItems.add(theItem);
        }
    }

    public ItemStack[] parseItem(String string) {
        if (string == null) return new ItemStack[1];
        String[] args = string.split(" ");
        try {
            double amount = Integer.parseInt(args[2]);
            ItemStack[] items = new ItemStack[(int) Math.ceil(amount / 64.0D)];
            if (items.length <= 0) return new ItemStack[1];
            for (int i = 0; i < items.length; i++) {
                @SuppressWarnings("deprecation")
                ItemStack item = new ItemStack(Integer.parseInt(args[0]), (int) amount, (short) Integer.parseInt(args[1]));
                int cur = 3;
                while (args.length > cur + 1) {
                    item.addUnsafeEnchantment(Enchantment.getByName(args[cur]), Integer.parseInt(args[(cur + 1)]));
                    cur += 2;
                }
                item = EnchantHandler.updateEnchants(item);
                amount -= 64.0D;
                items[i] = item;
            }
            return items;
        } catch (NumberFormatException nfe) {
            plugin.getLogger().severe("Error with string " + StringUtils.join(args, " "));
            plugin.getLogger().severe(nfe.getMessage());
        }
        return null;

    }

    public ItemStack[] getRandomItems() {
        Random random = new Random();
        int ran = random.nextInt(5);
        if (ran < 3) {
            ran = 3;
        }
        ItemStack[] items = new ItemStack[ran];
        for (int i = 0; i < ran; i++) {
            int id = random.nextInt(chestsItems.size() - 1);
            items[i] = chestsItems.get(id);
        }
        return items;
    }

    private Map<World, Set<Vector>> resetChests = new WeakHashMap<World, Set<Vector>>();

    @EventHandler
    public void onChestRightClick(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock().getType() == Material.CHEST) {
	        	if(plugin.getGm().getIsAlive().contains(e.getPlayer())){
	            Vector vector = e.getClickedBlock().getLocation().toVector();
	            Set<Vector> set = getVectorSet(e.getPlayer().getWorld());
	
	            if (!set.contains(vector)) {
	                Chest chest = ((Chest) e.getClickedBlock().getState());
	                chest.getBlockInventory().setContents(getRandomItems());
	                set.add(vector);
	            }
        	}
        }
    }

    private Set<Vector> getVectorSet(World world) {
        Set<Vector> result = resetChests.get(world);

        if (result == null) {
            result = new HashSet<Vector>();
            resetChests.put(world, result);
        }
        return result;
    }

    public List<Location> getChests() {
        return chests;
    }

    public void setChests(List<Location> chests) {
        this.chests = chests;
    }

    private List<Location> chests = new ArrayList<Location>();

    public List<ItemStack> getChestsItems() {
        return chestsItems;
    }

    public Map<World, Set<Vector>> getResetChests() {
        return resetChests;
    }

    public void setChestsItems(List<ItemStack> chestsItems) {
        this.chestsItems = chestsItems;
    }

    public void setResetChests(Map<World, Set<Vector>> resetChests) {
        this.resetChests = resetChests;
    }
}
