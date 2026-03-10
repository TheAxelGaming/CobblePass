package com.jacksonalejandro.cobblepass.quests;

import com.cobblemon.mod.common.api.Priority;
import com.cobblemon.mod.common.api.events.CobblemonEvents;
import com.cobblemon.mod.common.battles.actor.PlayerBattleActor;
import kotlin.Unit;
import net.advancedplugins.bp.impl.actions.containers.ExternalActionContainer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.block.Action;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class CobblemonQuests extends ExternalActionContainer {

    private final JavaPlugin plugin;

    public CobblemonQuests(JavaPlugin plugin) {
        // "cobblemon" will be the prefix for our quests.
        // E.g. "cobblemon_catch", "cobblemon_craft", "cobblemon_berry"
        super(plugin, "cobblemon");
        this.plugin = plugin;

        // Register the Cobblemon specific events natively using the Cobblemon Event Bus
        registerCobblemonEvents();
    }

    private void registerCobblemonEvents() {
        // Subscribe to Cobblemon's PokemonCapturedEvent
        CobblemonEvents.POKEMON_CAPTURED.subscribe(Priority.NORMAL, event -> {
            try {
                // Get owner UUID when captured
                UUID uuid = event.getPokemon().getOwnerUUID();
                if (uuid != null) {
                    Player player = Bukkit.getPlayer(uuid);
                    if (player != null) {
                        String pokemonName = event.getPokemon().getSpecies().getName().toLowerCase();

                        Bukkit.getScheduler().runTask(plugin, () -> {
                            this.executionBuilder("catch")
                                    .player(player)
                                    .root(pokemonName)
                                    .progressSingle()
                                    .canBeAsync()
                                    .buildAndExecute();
                        });
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return Unit.INSTANCE;
        });

        // Subscribe to Cobblemon's BattleVictoryEvent
        CobblemonEvents.BATTLE_VICTORY.subscribe(Priority.NORMAL, event -> {
            try {
                for (com.cobblemon.mod.common.api.battles.model.actor.BattleActor winner : event.getWinners()) {
                    if (winner instanceof PlayerBattleActor) {
                        PlayerBattleActor playerActor = (PlayerBattleActor) winner;
                        for (UUID uuid : playerActor.getPlayerUUIDs()) {
                            Player player = Bukkit.getPlayer(uuid);
                            if (player != null) {
                                Bukkit.getScheduler().runTask(plugin, () -> {
                                    this.executionBuilder("battle")
                                            .player(player)
                                            .progressSingle()
                                            .canBeAsync()
                                            .buildAndExecute();
                                });
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return Unit.INSTANCE;
        });
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onCraft(CraftItemEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();

            // Check if crafted item is from the cobblemon namespace
            if (event.getRecipe() != null && event.getRecipe().getResult() != null) {
                String namespace = event.getRecipe().getResult().getType().getKey().getNamespace().toLowerCase();
                if (namespace.equals("cobblemon")) {
                    String itemKey = event.getRecipe().getResult().getType().getKey().getKey().toLowerCase();

                    this.executionBuilder("craft")
                            .player(player)
                            .root(itemKey) // Root allows filtering by the specific cobblemon item type
                            .progressSingle()
                            .canBeAsync()
                            .buildAndExecute();
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onInteract(PlayerInteractEvent event) {
        // For detecting berries: we check if they right-click a block and that block is
        // a berry block or apricorn bush
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock() != null) {
            String materialName = event.getClickedBlock().getType().name().toLowerCase();
            if (materialName.contains("berry_bush") || materialName.contains("apricorn_bush")) {
                if (event.getClickedBlock().getBlockData() instanceof org.bukkit.block.data.Ageable) {
                    org.bukkit.block.data.Ageable ageable = (org.bukkit.block.data.Ageable) event.getClickedBlock()
                            .getBlockData();
                    if (ageable.getAge() == ageable.getMaximumAge()) {
                        this.executionBuilder("berry")
                                .player(event.getPlayer())
                                .root(materialName)
                                .progressSingle()
                                .canBeAsync()
                                .buildAndExecute();
                    }
                }
            }
        }
    }
}
