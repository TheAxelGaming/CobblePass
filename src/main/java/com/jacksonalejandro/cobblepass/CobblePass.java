package com.jacksonalejandro.cobblepass;

import com.jacksonalejandro.cobblepass.quests.CobblemonQuests;
import net.advancedplugins.bp.impl.actions.ActionRegistry;
import org.bukkit.plugin.java.JavaPlugin;

public class CobblePass extends JavaPlugin {

    @Override
    public void onEnable() {
        // Init our custom Quest action container
        CobblemonQuests quests = new CobblemonQuests(this);

        // We register our ExternalActionContainer (which implements Listener natively)
        // ActionRegistry has a method 'quest' to register listeners into Bukkit if
        // needed,
        // but since we extends ExternalActionContainer we can use
        // ActionRegistry.getRegistry().hook()
        // or just register the events to Bukkit directly!
        getServer().getPluginManager().registerEvents(quests, this);

        // Log silently
        getLogger().info("CobblePass successfully hooked into BattlePass!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("CobblePass disabled.");
    }
}
