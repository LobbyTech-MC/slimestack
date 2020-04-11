package me.hal989.halsaddon;

import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.handlers.ItemUseHandler;
import me.mrCookieSlime.Slimefun.SlimefunPlugin;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.inventory.ItemStack;

public class EnderStaff2 extends SlimefunItem {

    public EnderStaff2(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe);
    }

    @Override
    public void preRegister() {

        ItemUseHandler itemUseHandler = this::onItemRightClick;
        addItemHandler(itemUseHandler);
    }

    private void onItemRightClick(PlayerRightClickEvent event) {
        Player p = event.getPlayer();
        event.cancel();
        if(p.getFoodLevel()>=2) {
            if (p.getGameMode() != GameMode.CREATIVE) {
                FoodLevelChangeEvent drainFood = new FoodLevelChangeEvent(p, p.getFoodLevel() - 2);
                Bukkit.getPluginManager().callEvent(drainFood); //drain food
                p.setFoodLevel(drainFood.getFoodLevel());
            }
            double xposition = (Math.random() * ((16) + 1)) + 0; //randomizing position
            double zposition = (Math.random() * ((16) + 1)) + 0; //and now for the z
            if (Math.random() > 0.5) {
                xposition *= -1; //positive or negative x
            }
            if (Math.random() > 0.5) {
                zposition *= -1; //same for z
            }
            p.teleport(new Location(p.getWorld(), p.getLocation().getX()+xposition, p.getLocation().getY(), p.getLocation().getZ()+zposition, p.getLocation().getYaw(), p.getLocation().getPitch()));
            p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
        }
        else {
            SlimefunPlugin.getLocal().sendMessage(p, "messages.hungry", true); //hunger message
        }
    }
}