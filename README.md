# Code Reengineering Final Project

Based on https://github.com/BryanWidjaja/BtardewWalley

---

Our Group :

1. 2802389222 - Bryan Widjaja
2. 2802397722 - Danielson
3. 2802401594 - Jeremy Auriel Zhang
4. 2802392210 - Jonathan Hanska Susanto
5. 2802397590 - Kristopher Nathanael

# Btardew Walley

Welcome to **Btardew Walley**, a terminal-based farming and animal husbandry simulator! Cultivate crops, raise livestock, and build a booming agricultural empire straight from your console.

## Getting Started

Before entering the game world, you must create an account:

- **Username**: Must be at least 8 characters long.
- **Password**: Must be at least 8 characters long, contain at least 1 letter and 1 number.

Once logged in, you start with **$1,000** and begin on the **Home Map**.

## The World

The game world is split into three interconnected maps.

1. **Home Map**: Where you sleep, buy tools, and access the animal shop.
2. **Plant Farm Map**: Located to the North, this is where you buy seeds and plant your crops.
3. **Animal Farm Map**: Located to the East, this acts as the grazing pasture for your livestock.

## Controls

Interaction revolves around moving your character (`P`) onto points of interest using your keyboard.

- `w` / `a` / `s` / `d`: Move Up, Left, Down, and Right.
- `e`: Open the Inventory Menu.
- `q`: Save and exit the game.
- Press `Enter` on blank prompts to continue dialogues.

---

## Game Mechanics

### 1. Plant Farming

Head North to the **Plant Farm** map.

- **Farm Shop**: Step on the shop tile to access the menu. You can buy seeds (like Wheat and Beetroot) or sell harvested Farm Products here.
- **Planting**: Step onto any empty dirt tile (`.`) to open the planting menu. Select a seed from your inventory to plant it. **Note:** You must have a `Hoe` in your inventory to plant seeds. You can also select the exit option (enter `0`) in the prompt if you decide not to plant.
- **Harvesting**: Plants take time to grow. Once their `growthTime` expires (after sleeping enough days), the lowercase crop symbol will mature into an **UPPERCASE** letter. Step on the mature crop to automatically harvest it!

### 2. Animal Husbandry

Head to the **Home Map** to find the **Animal Shop**. Here, you can buy or sell animals such as Chickens (`c`), Cows (`C`), and Sheep (`S`).

- Purchased animals will automatically spawn in the **Animal Farm** map located to the East. Note that each animal must be given a unique name (max 15 characters)!
- **Visuals**: Animals and the player are rendered with distinct colors on the map to easily identify them. The player (`P`) appears in green, Chickens (`c`) in yellow, Cows (`C`) in brown, and Sheep (`S`) in bright white.
- **Collecting Goods**: Animals produce products over time. When an animal is ready to be harvested, stepping on it will prompt you to collect its goods.
  - **Chickens**: Produce Eggs every 1 day. No tools required.
  - **Cows**: Produce Milk every 2 days. Requires a `Bucket` in your inventory.
  - **Sheep**: Produce Wool every 5 days. Requires `Shears` in your inventory.

### 3. Buying Tools

You can purchase essential equipment from the **Tool Shop** located on the **Home Map**. The available tools are:

| Tool   | Price  |
|--------|--------|
| Bucket | $1,000 |
| Shears | $1,500 |
| Hoe    | $3,000 |

Make sure to buy a Hoe for planting, and a Bucket and Shears as early as possible if you plan on raising Cows and Sheep!

### 4. Sleeping & Time Progression

Time in Btardew Walley progresses when you sleep. Head to your bed on the **Home Map** and confirm to sleep.
Sleeping advances the required "days" for animal harvest cycles, crop maturation, and freshness decay.

### 5. Inventory

Press `e` at any time to open the **Inventory Menu**, where you can view:

1. Animal Products (with grade and quantity)
2. Farm Products (with freshness and quantity)
3. Animals (with name and type)
4. Tools
5. Plant Seeds (with quantity)

### 6. Saving & Loading

Your progress is automatically saved when you exit the game via `q`. All inventory, animals, plants, map position, and day count are persisted. When you log back in, everything is restored — including animals on the Animal Farm and planted crops on the Plant Farm.

---

## Advanced Economics

### Freshness (Farm Products)

Farm products don't last forever. Freshness starts at **5** when harvested and decreases by 1 each day you sleep.

- **Freshness 4-5**: Sells at full price (1x multiplier).
- **Freshness 3**: Sells at half price (0.5x multiplier).
- **Freshness 1-2**: Sells at a massive discount (0.25x multiplier).
- **Freshness 0**: The item rots and disappears entirely from your inventory. Sell your crops quickly!

### Grade Quality (Animal Products)

When harvesting animal products, they are assigned a random Grade (1, 2, or 3). The longer you play (higher day count), the better your chances are of securing high-grade products!

- Grade 1: Sells at 1x base price.
- Grade 2: Sells at 2x base price.
- Grade 3: Sells at 5x base price.

### Seed & Product Prices

| Seeds    | Buy Price |
|----------|-----------|
| Wheat    | $50       |
| Beetroot | $100      |

| Animal Products | Base Sell Price |
|-----------------|-----------------|
| Egg             | $100            |
| Milk             | $300            |
| Wool             | $800            |

| Animals  | Buy/Sell Price |
|----------|----------------|
| Chicken  | $200           |
| Cow      | $300           |
| Sheep    | $500           |

---

## Developer Mode

Type `devmode` instead of a directional movement to toggle developer privileges. This grants you $1,000,000 and the following hotkeys:

- `1`, `2`, `3`: Quick teleport to the Plant Map, Home Map, and Animal Map respectively.
- `r`: Sleep instantly anywhere.
- `g`: Sleep 20 days instantly.
- `u`: Mass spawn animals into your pasture.
- `t`: Acquire all standard tools instantly.
- `p`: Force-spawn a stack of animal products depending on current RNG grade.
- `k`: Acquire a large stack of Wheat seeds instantly.