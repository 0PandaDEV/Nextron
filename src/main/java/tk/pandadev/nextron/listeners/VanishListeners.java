package tk.pandadev.nextron.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import tk.pandadev.nextron.utils.VanishAPI;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class VanishListeners implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onDamage(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player)) return;
        if (event.getEntity() == null) return;
        Player player = (Player) event.getDamager();
        if (VanishAPI.isVanish(player.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (VanishAPI.isVanish(player.getPlayer())) {
            event.setDeathMessage(null);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        Player player = (Player) event.getEntity();
        if (VanishAPI.isVanish(player.getPlayer()) && event.getFoodLevel() <= player.getFoodLevel()) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        if (VanishAPI.isVanish(player.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onTarget(EntityTargetEvent event) {
        if (!(event.getTarget() instanceof Player player)) return;
        if (VanishAPI.isVanish(Objects.requireNonNull(player.getPlayer()))) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onItemPickUp(PlayerPickupItemEvent event) {
        Player player = event.getPlayer();
        if (VanishAPI.isVanish(Objects.requireNonNull(player.getPlayer()))) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerCropTrample(PlayerInteractEvent e) {
        if (!VanishAPI.isVanish(e.getPlayer())) return;

        if (e.getAction() != Action.PHYSICAL) return;
        if (e.getClickedBlock() != null && e.getClickedBlock().getType().toString().matches("SOIL|FARMLAND"))
            e.setCancelled(true);

    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onInteract(PlayerInteractEvent e) {
        if (!VanishAPI.isVanish(e.getPlayer())) return;

        if (e.getAction().equals(Action.PHYSICAL) && e.getClickedBlock() != null) {
            String material = e.getClickedBlock().getType().toString();
            List<String> disallowedMaterials = Arrays.asList("STONE_PLATE", "GOLD_PLATE", "IRON_PLATE",
                    "WOOD_PLATE"/* <- LEGACY*/, "TRIPWIRE", "PRESSURE_PLATE");
            for (String disallowedMaterial : disallowedMaterials)
                if (material.equals(disallowedMaterial) || material.contains(disallowedMaterial)) {
                    e.setCancelled(true);
                }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlace(BlockPlaceEvent e) {
        if (!VanishAPI.isVanish(e.getPlayer())) return;
        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBreak(BlockBreakEvent e) {
        if (!VanishAPI.isVanish(e.getPlayer())) return;
        e.setCancelled(true);
    }

}
