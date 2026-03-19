# CobblePass

CobblePass is a Spigot/Arclight native Java Addon that registers custom quest types for the **Cobblemon** mod into **BattlePass** v5.0.5. It bridges the gap between Minecraft Pokémon tracking and your server's BattlePass progression system.

##  Features

This addon registers the `cobblemon` action prefix in BattlePass, allowing you to create the following quest types natively using the Developer API. We connect to the event listeners directly avoiding unnecessary Spigot/Fabric reflection.

### Available Quest Types

- **Catch a Pokémon**: Triggered when a player catches a Pokémon.
  ```yaml
  type: cobblemon:catch
  root: "pikachu" # Optional: specify a pokemon name to require a specific catch
  ```
  
- **Win a Battle**: Triggered when a player wins a Cobblemon battle.
  ```yaml
  type: cobblemon:battle
  ```

- **Craft a Cobblemon Item**: Triggered when a player crafts an item from the `cobblemon` namespace.
  ```yaml
  type: cobblemon:craft
  root: "poke_ball" # Optional: specific item ID
  ```
  
- **Harvest Berries/Apricorns**: Triggered when a player right-clicks an ageable, fully grown berry or apricorn bush block.
  ```yaml
  type: cobblemon:berry
  root: "oran_berry_bush" # Optional: specify the specific bush block name
  ```

##  Usage in Quests

In your quest lore or `lang.yml`, ensure you use these variables, which are natively supported for BattlePass variables:
- `%progress%` - The current progress of the player in the quest.
- `%required_progress%` - The maximum required progress to complete the quest.

Example quest:
```yaml
quests:
  catch_pikachu:
    material: "PLAYER_HEAD"
    type: cobblemon:catch
    root: "pikachu"
    required_progress: 5
    name: "Catch 5 Pikachu"
    lore:
      - "&7Progress: &e%progress% / %required_progress%"
```

##  Requirements

- **Minecraft Server**: 1.21.1 (Arclight or compatible Hybrid server).
- **Java**: 21
- **Mods/Plugins**:
  - [Cobblemon 1.7.1](https://modrinth.com/mod/cobblemon)
  - BattlePass 5.0.5

##  Build from Source

This project uses **Maven** to build. Local dependencies (Cobblemon and BattlePass) are included in the `/libs` directory and imported via system paths.

1. Clone the repository.
2. Put the required `BattlePass-5.0.5.jar` and `Cobblemon-fabric-1.7.1+1.21.1.jar` inside the `libs/` folder.
3. Run Maven:
   ```bash
   mvn clean package
   ```
4. Find the output jar in the `target/` directory.
