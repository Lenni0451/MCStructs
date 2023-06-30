package net.lenni0451.mcstructs.data.v1_11;

import net.lenni0451.mcstructs.data.Block;

import java.util.ArrayList;
import java.util.List;

public class Block_v1_11 implements Block {

    public static final List<Block_v1_11> BLOCK_LIST = new ArrayList<>();

    public static final Block_v1_11 stone = new Block_v1_11(1, "stone", true, 1.5F, 30.0F);
    public static final Block_v1_11 grass = new Block_v1_11(2, "grass", true, 0.6F, 3.0F);
    public static final Block_v1_11 dirt = new Block_v1_11(3, "dirt", true, 0.5F, 2.5F);
    public static final Block_v1_11 cobblestone = new Block_v1_11(4, "cobblestone", true, 2.0F, 30.0F);
    public static final Block_v1_11 planks = new Block_v1_11(5, "planks", true, 2.0F, 15.0F);
    public static final Block_v1_11 sapling = new Block_v1_11(6, "sapling", false, 0.0F, 0.0F);
    public static final Block_v1_11 bedrock = new Block_v1_11(7, "bedrock", true, -1.0F, 1.8E7F);
    public static final Block_v1_11 flowing_water = new Block_v1_11(8, "flowing_water", true, 100.0F, 500.0F);
    public static final Block_v1_11 water = new Block_v1_11(9, "water", true, 100.0F, 500.0F);
    public static final Block_v1_11 flowing_lava = new Block_v1_11(10, "flowing_lava", true, 100.0F, 500.0F);
    public static final Block_v1_11 lava = new Block_v1_11(11, "lava", true, 100.0F, 500.0F);
    public static final Block_v1_11 sand = new Block_v1_11(12, "sand", true, 0.5F, 2.5F);
    public static final Block_v1_11 gravel = new Block_v1_11(13, "gravel", true, 0.6F, 3.0F);
    public static final Block_v1_11 gold_ore = new Block_v1_11(14, "gold_ore", true, 3.0F, 15.0F);
    public static final Block_v1_11 iron_ore = new Block_v1_11(15, "iron_ore", true, 3.0F, 15.0F);
    public static final Block_v1_11 coal_ore = new Block_v1_11(16, "coal_ore", true, 3.0F, 15.0F);
    public static final Block_v1_11 log = new Block_v1_11(17, "log", true, 2.0F, 10.0F);
    public static final Block_v1_11 leaves = new Block_v1_11(18, "leaves", true, 0.2F, 1.0F);
    public static final Block_v1_11 sponge = new Block_v1_11(19, "sponge", true, 0.6F, 3.0F);
    public static final Block_v1_11 glass = new Block_v1_11(20, "glass", true, 0.3F, 1.5F);
    public static final Block_v1_11 lapis_ore = new Block_v1_11(21, "lapis_ore", true, 3.0F, 15.0F);
    public static final Block_v1_11 lapis_block = new Block_v1_11(22, "lapis_block", true, 3.0F, 15.0F);
    public static final Block_v1_11 dispenser = new Block_v1_11(23, "dispenser", true, 3.5F, 17.5F);
    public static final Block_v1_11 sandstone = new Block_v1_11(24, "sandstone", true, 0.8F, 4.0F);
    public static final Block_v1_11 noteblock = new Block_v1_11(25, "noteblock", true, 0.8F, 4.0F);
    public static final Block_v1_11 bed = new Block_v1_11(26, "bed", true, 0.2F, 1.0F);
    public static final Block_v1_11 golden_rail = new Block_v1_11(27, "golden_rail", false, 0.7F, 3.5F);
    public static final Block_v1_11 detector_rail = new Block_v1_11(28, "detector_rail", false, 0.7F, 3.5F);
    public static final Block_v1_11 sticky_piston = new Block_v1_11(29, "sticky_piston", true, 0.5F, 2.5F);
    public static final Block_v1_11 web = new Block_v1_11(30, "web", true, 4.0F, 20.0F);
    public static final Block_v1_11 tallgrass = new Block_v1_11(31, "tallgrass", false, 0.0F, 0.0F);
    public static final Block_v1_11 deadbush = new Block_v1_11(32, "deadbush", false, 0.0F, 0.0F);
    public static final Block_v1_11 piston = new Block_v1_11(33, "piston", true, 0.5F, 2.5F);
    public static final Block_v1_11 piston_head = new Block_v1_11(34, "piston_head", true, 0.5F, 2.5F);
    public static final Block_v1_11 wool = new Block_v1_11(35, "wool", true, 0.8F, 4.0F);
    public static final Block_v1_11 piston_extension = new Block_v1_11(36, "piston_extension", true, -1.0F, 0.0F);
    public static final Block_v1_11 yellow_flower = new Block_v1_11(37, "yellow_flower", false, 0.0F, 0.0F);
    public static final Block_v1_11 red_flower = new Block_v1_11(38, "red_flower", false, 0.0F, 0.0F);
    public static final Block_v1_11 brown_mushroom = new Block_v1_11(39, "brown_mushroom", false, 0.0F, 0.0F);
    public static final Block_v1_11 red_mushroom = new Block_v1_11(40, "red_mushroom", false, 0.0F, 0.0F);
    public static final Block_v1_11 gold_block = new Block_v1_11(41, "gold_block", true, 3.0F, 30.0F);
    public static final Block_v1_11 iron_block = new Block_v1_11(42, "iron_block", true, 5.0F, 30.0F);
    public static final Block_v1_11 double_stone_slab = new Block_v1_11(43, "double_stone_slab", true, 2.0F, 30.0F);
    public static final Block_v1_11 stone_slab = new Block_v1_11(44, "stone_slab", true, 2.0F, 30.0F);
    public static final Block_v1_11 brick_block = new Block_v1_11(45, "brick_block", true, 2.0F, 30.0F);
    public static final Block_v1_11 tnt = new Block_v1_11(46, "tnt", true, 0.0F, 0.0F);
    public static final Block_v1_11 bookshelf = new Block_v1_11(47, "bookshelf", true, 1.5F, 7.5F);
    public static final Block_v1_11 mossy_cobblestone = new Block_v1_11(48, "mossy_cobblestone", true, 2.0F, 30.0F);
    public static final Block_v1_11 obsidian = new Block_v1_11(49, "obsidian", true, 50.0F, 6000.0F);
    public static final Block_v1_11 torch = new Block_v1_11(50, "torch", false, 0.0F, 0.0F);
    public static final Block_v1_11 fire = new Block_v1_11(51, "fire", false, 0.0F, 0.0F);
    public static final Block_v1_11 mob_spawner = new Block_v1_11(52, "mob_spawner", true, 5.0F, 25.0F);
    public static final Block_v1_11 oak_stairs = new Block_v1_11(53, "oak_stairs", true, 2.0F, 15.0F);
    public static final Block_v1_11 chest = new Block_v1_11(54, "chest", true, 2.5F, 12.5F);
    public static final Block_v1_11 redstone_wire = new Block_v1_11(55, "redstone_wire", false, 0.0F, 0.0F);
    public static final Block_v1_11 diamond_ore = new Block_v1_11(56, "diamond_ore", true, 3.0F, 15.0F);
    public static final Block_v1_11 diamond_block = new Block_v1_11(57, "diamond_block", true, 5.0F, 30.0F);
    public static final Block_v1_11 crafting_table = new Block_v1_11(58, "crafting_table", true, 2.5F, 12.5F);
    public static final Block_v1_11 wheat = new Block_v1_11(59, "wheat", false, 0.0F, 0.0F);
    public static final Block_v1_11 farmland = new Block_v1_11(60, "farmland", true, 0.6F, 3.0F);
    public static final Block_v1_11 furnace = new Block_v1_11(61, "furnace", true, 3.5F, 17.5F);
    public static final Block_v1_11 lit_furnace = new Block_v1_11(62, "lit_furnace", true, 3.5F, 17.5F);
    public static final Block_v1_11 standing_sign = new Block_v1_11(63, "standing_sign", true, 1.0F, 5.0F);
    public static final Block_v1_11 wooden_door = new Block_v1_11(64, "wooden_door", true, 3.0F, 15.0F);
    public static final Block_v1_11 ladder = new Block_v1_11(65, "ladder", false, 0.4F, 2.0F);
    public static final Block_v1_11 rail = new Block_v1_11(66, "rail", false, 0.7F, 3.5F);
    public static final Block_v1_11 stone_stairs = new Block_v1_11(67, "stone_stairs", true, 2.0F, 30.0F);
    public static final Block_v1_11 wall_sign = new Block_v1_11(68, "wall_sign", true, 1.0F, 5.0F);
    public static final Block_v1_11 lever = new Block_v1_11(69, "lever", false, 0.5F, 2.5F);
    public static final Block_v1_11 stone_pressure_plate = new Block_v1_11(70, "stone_pressure_plate", true, 0.5F, 2.5F);
    public static final Block_v1_11 iron_door = new Block_v1_11(71, "iron_door", true, 5.0F, 25.0F);
    public static final Block_v1_11 wooden_pressure_plate = new Block_v1_11(72, "wooden_pressure_plate", true, 0.5F, 2.5F);
    public static final Block_v1_11 redstone_ore = new Block_v1_11(73, "redstone_ore", true, 3.0F, 15.0F);
    public static final Block_v1_11 lit_redstone_ore = new Block_v1_11(74, "lit_redstone_ore", true, 3.0F, 15.0F);
    public static final Block_v1_11 unlit_redstone_torch = new Block_v1_11(75, "unlit_redstone_torch", false, 0.0F, 0.0F);
    public static final Block_v1_11 redstone_torch = new Block_v1_11(76, "redstone_torch", false, 0.0F, 0.0F);
    public static final Block_v1_11 stone_button = new Block_v1_11(77, "stone_button", false, 0.5F, 2.5F);
    public static final Block_v1_11 snow_layer = new Block_v1_11(78, "snow_layer", false, 0.1F, 0.5F);
    public static final Block_v1_11 ice = new Block_v1_11(79, "ice", true, 0.5F, 2.5F);
    public static final Block_v1_11 snow = new Block_v1_11(80, "snow", true, 0.2F, 1.0F);
    public static final Block_v1_11 cactus = new Block_v1_11(81, "cactus", true, 0.4F, 2.0F);
    public static final Block_v1_11 clay = new Block_v1_11(82, "clay", true, 0.6F, 3.0F);
    public static final Block_v1_11 reeds = new Block_v1_11(83, "reeds", false, 0.0F, 0.0F);
    public static final Block_v1_11 jukebox = new Block_v1_11(84, "jukebox", true, 2.0F, 30.0F);
    public static final Block_v1_11 fence = new Block_v1_11(85, "fence", true, 2.0F, 15.0F);
    public static final Block_v1_11 pumpkin = new Block_v1_11(86, "pumpkin", true, 1.0F, 5.0F);
    public static final Block_v1_11 netherrack = new Block_v1_11(87, "netherrack", true, 0.4F, 2.0F);
    public static final Block_v1_11 soul_sand = new Block_v1_11(88, "soul_sand", true, 0.5F, 2.5F);
    public static final Block_v1_11 glowstone = new Block_v1_11(89, "glowstone", true, 0.3F, 1.5F);
    public static final Block_v1_11 portal = new Block_v1_11(90, "portal", false, -1.0F, 0.0F);
    public static final Block_v1_11 lit_pumpkin = new Block_v1_11(91, "lit_pumpkin", true, 1.0F, 5.0F);
    public static final Block_v1_11 cake = new Block_v1_11(92, "cake", true, 0.5F, 2.5F);
    public static final Block_v1_11 unpowered_repeater = new Block_v1_11(93, "unpowered_repeater", false, 0.0F, 0.0F);
    public static final Block_v1_11 powered_repeater = new Block_v1_11(94, "powered_repeater", false, 0.0F, 0.0F);
    public static final Block_v1_11 stained_glass = new Block_v1_11(95, "stained_glass", true, 0.3F, 1.5F);
    public static final Block_v1_11 trapdoor = new Block_v1_11(96, "trapdoor", true, 3.0F, 15.0F);
    public static final Block_v1_11 monster_egg = new Block_v1_11(97, "monster_egg", true, 0.75F, 3.75F);
    public static final Block_v1_11 stonebrick = new Block_v1_11(98, "stonebrick", true, 1.5F, 30.0F);
    public static final Block_v1_11 brown_mushroom_block = new Block_v1_11(99, "brown_mushroom_block", true, 0.2F, 1.0F);
    public static final Block_v1_11 red_mushroom_block = new Block_v1_11(100, "red_mushroom_block", true, 0.2F, 1.0F);
    public static final Block_v1_11 iron_bars = new Block_v1_11(101, "iron_bars", true, 5.0F, 30.0F);
    public static final Block_v1_11 glass_pane = new Block_v1_11(102, "glass_pane", true, 0.3F, 1.5F);
    public static final Block_v1_11 melon_block = new Block_v1_11(103, "melon_block", true, 1.0F, 5.0F);
    public static final Block_v1_11 pumpkin_stem = new Block_v1_11(104, "pumpkin_stem", false, 0.0F, 0.0F);
    public static final Block_v1_11 melon_stem = new Block_v1_11(105, "melon_stem", false, 0.0F, 0.0F);
    public static final Block_v1_11 vine = new Block_v1_11(106, "vine", false, 0.2F, 1.0F);
    public static final Block_v1_11 fence_gate = new Block_v1_11(107, "fence_gate", true, 2.0F, 15.0F);
    public static final Block_v1_11 brick_stairs = new Block_v1_11(108, "brick_stairs", true, 2.0F, 30.0F);
    public static final Block_v1_11 stone_brick_stairs = new Block_v1_11(109, "stone_brick_stairs", true, 1.5F, 30.0F);
    public static final Block_v1_11 mycelium = new Block_v1_11(110, "mycelium", true, 0.6F, 3.0F);
    public static final Block_v1_11 waterlily = new Block_v1_11(111, "waterlily", false, 0.0F, 0.0F);
    public static final Block_v1_11 nether_brick = new Block_v1_11(112, "nether_brick", true, 2.0F, 30.0F);
    public static final Block_v1_11 nether_brick_fence = new Block_v1_11(113, "nether_brick_fence", true, 2.0F, 30.0F);
    public static final Block_v1_11 nether_brick_stairs = new Block_v1_11(114, "nether_brick_stairs", true, 2.0F, 30.0F);
    public static final Block_v1_11 nether_wart = new Block_v1_11(115, "nether_wart", false, 0.0F, 0.0F);
    public static final Block_v1_11 enchanting_table = new Block_v1_11(116, "enchanting_table", true, 5.0F, 6000.0F);
    public static final Block_v1_11 brewing_stand = new Block_v1_11(117, "brewing_stand", true, 0.5F, 2.5F);
    public static final Block_v1_11 cauldron = new Block_v1_11(118, "cauldron", true, 2.0F, 10.0F);
    public static final Block_v1_11 end_portal = new Block_v1_11(119, "end_portal", false, -1.0F, 1.8E7F);
    public static final Block_v1_11 end_portal_frame = new Block_v1_11(120, "end_portal_frame", true, -1.0F, 1.8E7F);
    public static final Block_v1_11 end_stone = new Block_v1_11(121, "end_stone", true, 3.0F, 45.0F);
    public static final Block_v1_11 dragon_egg = new Block_v1_11(122, "dragon_egg", true, 3.0F, 45.0F);
    public static final Block_v1_11 redstone_lamp = new Block_v1_11(123, "redstone_lamp", true, 0.3F, 1.5F);
    public static final Block_v1_11 lit_redstone_lamp = new Block_v1_11(124, "lit_redstone_lamp", true, 0.3F, 1.5F);
    public static final Block_v1_11 double_wooden_slab = new Block_v1_11(125, "double_wooden_slab", true, 2.0F, 15.0F);
    public static final Block_v1_11 wooden_slab = new Block_v1_11(126, "wooden_slab", true, 2.0F, 15.0F);
    public static final Block_v1_11 cocoa = new Block_v1_11(127, "cocoa", false, 0.2F, 15.0F);
    public static final Block_v1_11 sandstone_stairs = new Block_v1_11(128, "sandstone_stairs", true, 0.8F, 4.0F);
    public static final Block_v1_11 emerald_ore = new Block_v1_11(129, "emerald_ore", true, 3.0F, 15.0F);
    public static final Block_v1_11 ender_chest = new Block_v1_11(130, "ender_chest", true, 22.5F, 3000.0F);
    public static final Block_v1_11 tripwire_hook = new Block_v1_11(131, "tripwire_hook", false, 0.0F, 0.0F);
    public static final Block_v1_11 tripwire = new Block_v1_11(132, "tripwire", false, 0.0F, 0.0F);
    public static final Block_v1_11 emerald_block = new Block_v1_11(133, "emerald_block", true, 5.0F, 30.0F);
    public static final Block_v1_11 spruce_stairs = new Block_v1_11(134, "spruce_stairs", true, 2.0F, 15.0F);
    public static final Block_v1_11 birch_stairs = new Block_v1_11(135, "birch_stairs", true, 2.0F, 15.0F);
    public static final Block_v1_11 jungle_stairs = new Block_v1_11(136, "jungle_stairs", true, 2.0F, 15.0F);
    public static final Block_v1_11 command_block = new Block_v1_11(137, "command_block", true, -1.0F, 1.8E7F);
    public static final Block_v1_11 beacon = new Block_v1_11(138, "beacon", true, 3.0F, 15.0F);
    public static final Block_v1_11 cobblestone_wall = new Block_v1_11(139, "cobblestone_wall", true, 2.0F, 30.0F);
    public static final Block_v1_11 flower_pot = new Block_v1_11(140, "flower_pot", false, 0.0F, 0.0F);
    public static final Block_v1_11 carrots = new Block_v1_11(141, "carrots", false, 0.0F, 0.0F);
    public static final Block_v1_11 potatoes = new Block_v1_11(142, "potatoes", false, 0.0F, 0.0F);
    public static final Block_v1_11 wooden_button = new Block_v1_11(143, "wooden_button", false, 0.5F, 2.5F);
    public static final Block_v1_11 skull = new Block_v1_11(144, "skull", false, 1.0F, 5.0F);
    public static final Block_v1_11 anvil = new Block_v1_11(145, "anvil", true, 5.0F, 6000.0F);
    public static final Block_v1_11 trapped_chest = new Block_v1_11(146, "trapped_chest", true, 2.5F, 12.5F);
    public static final Block_v1_11 light_weighted_pressure_plate = new Block_v1_11(147, "light_weighted_pressure_plate", true, 0.5F, 2.5F);
    public static final Block_v1_11 heavy_weighted_pressure_plate = new Block_v1_11(148, "heavy_weighted_pressure_plate", true, 0.5F, 2.5F);
    public static final Block_v1_11 unpowered_comparator = new Block_v1_11(149, "unpowered_comparator", false, 0.0F, 0.0F);
    public static final Block_v1_11 powered_comparator = new Block_v1_11(150, "powered_comparator", false, 0.0F, 0.0F);
    public static final Block_v1_11 daylight_detector = new Block_v1_11(151, "daylight_detector", true, 0.2F, 1.0F);
    public static final Block_v1_11 redstone_block = new Block_v1_11(152, "redstone_block", true, 5.0F, 30.0F);
    public static final Block_v1_11 quartz_ore = new Block_v1_11(153, "quartz_ore", true, 3.0F, 15.0F);
    public static final Block_v1_11 hopper = new Block_v1_11(154, "hopper", true, 3.0F, 24.0F);
    public static final Block_v1_11 quartz_block = new Block_v1_11(155, "quartz_block", true, 0.8F, 4.0F);
    public static final Block_v1_11 quartz_stairs = new Block_v1_11(156, "quartz_stairs", true, 0.8F, 4.0F);
    public static final Block_v1_11 activator_rail = new Block_v1_11(157, "activator_rail", false, 0.7F, 3.5F);
    public static final Block_v1_11 dropper = new Block_v1_11(158, "dropper", true, 3.5F, 17.5F);
    public static final Block_v1_11 stained_hardened_clay = new Block_v1_11(159, "stained_hardened_clay", true, 1.25F, 21.0F);
    public static final Block_v1_11 stained_glass_pane = new Block_v1_11(160, "stained_glass_pane", true, 0.3F, 1.5F);
    public static final Block_v1_11 leaves2 = new Block_v1_11(161, "leaves2", true, 0.2F, 1.0F);
    public static final Block_v1_11 log2 = new Block_v1_11(162, "log2", true, 2.0F, 10.0F);
    public static final Block_v1_11 acacia_stairs = new Block_v1_11(163, "acacia_stairs", true, 2.0F, 15.0F);
    public static final Block_v1_11 dark_oak_stairs = new Block_v1_11(164, "dark_oak_stairs", true, 2.0F, 15.0F);
    public static final Block_v1_11 slime = new Block_v1_11(165, "slime", true, 0.0F, 0.0F);
    public static final Block_v1_11 barrier = new Block_v1_11(166, "barrier", false, -1.0F, 1.8000004E7F);
    public static final Block_v1_11 iron_trapdoor = new Block_v1_11(167, "iron_trapdoor", true, 5.0F, 25.0F);
    public static final Block_v1_11 prismarine = new Block_v1_11(168, "prismarine", true, 1.5F, 30.0F);
    public static final Block_v1_11 sea_lantern = new Block_v1_11(169, "sea_lantern", true, 0.3F, 1.5F);
    public static final Block_v1_11 hay_block = new Block_v1_11(170, "hay_block", true, 0.5F, 2.5F);
    public static final Block_v1_11 carpet = new Block_v1_11(171, "carpet", false, 0.1F, 0.5F);
    public static final Block_v1_11 hardened_clay = new Block_v1_11(172, "hardened_clay", true, 1.25F, 21.0F);
    public static final Block_v1_11 coal_block = new Block_v1_11(173, "coal_block", true, 5.0F, 30.0F);
    public static final Block_v1_11 packed_ice = new Block_v1_11(174, "packed_ice", true, 0.5F, 2.5F);
    public static final Block_v1_11 double_plant = new Block_v1_11(175, "double_plant", false, 0.0F, 0.0F);
    public static final Block_v1_11 standing_banner = new Block_v1_11(176, "standing_banner", true, 1.0F, 5.0F);
    public static final Block_v1_11 wall_banner = new Block_v1_11(177, "wall_banner", true, 1.0F, 5.0F);
    public static final Block_v1_11 daylight_detector_inverted = new Block_v1_11(178, "daylight_detector_inverted", true, 0.2F, 1.0F);
    public static final Block_v1_11 red_sandstone = new Block_v1_11(179, "red_sandstone", true, 0.8F, 4.0F);
    public static final Block_v1_11 red_sandstone_stairs = new Block_v1_11(180, "red_sandstone_stairs", true, 0.8F, 4.0F);
    public static final Block_v1_11 double_stone_slab2 = new Block_v1_11(181, "double_stone_slab2", true, 2.0F, 30.0F);
    public static final Block_v1_11 stone_slab2 = new Block_v1_11(182, "stone_slab2", true, 2.0F, 30.0F);
    public static final Block_v1_11 spruce_fence_gate = new Block_v1_11(183, "spruce_fence_gate", true, 2.0F, 15.0F);
    public static final Block_v1_11 birch_fence_gate = new Block_v1_11(184, "birch_fence_gate", true, 2.0F, 15.0F);
    public static final Block_v1_11 jungle_fence_gate = new Block_v1_11(185, "jungle_fence_gate", true, 2.0F, 15.0F);
    public static final Block_v1_11 dark_oak_fence_gate = new Block_v1_11(186, "dark_oak_fence_gate", true, 2.0F, 15.0F);
    public static final Block_v1_11 acacia_fence_gate = new Block_v1_11(187, "acacia_fence_gate", true, 2.0F, 15.0F);
    public static final Block_v1_11 spruce_fence = new Block_v1_11(188, "spruce_fence", true, 2.0F, 15.0F);
    public static final Block_v1_11 birch_fence = new Block_v1_11(189, "birch_fence", true, 2.0F, 15.0F);
    public static final Block_v1_11 jungle_fence = new Block_v1_11(190, "jungle_fence", true, 2.0F, 15.0F);
    public static final Block_v1_11 dark_oak_fence = new Block_v1_11(191, "dark_oak_fence", true, 2.0F, 15.0F);
    public static final Block_v1_11 acacia_fence = new Block_v1_11(192, "acacia_fence", true, 2.0F, 15.0F);
    public static final Block_v1_11 spruce_door = new Block_v1_11(193, "spruce_door", true, 3.0F, 15.0F);
    public static final Block_v1_11 birch_door = new Block_v1_11(194, "birch_door", true, 3.0F, 15.0F);
    public static final Block_v1_11 jungle_door = new Block_v1_11(195, "jungle_door", true, 3.0F, 15.0F);
    public static final Block_v1_11 acacia_door = new Block_v1_11(196, "acacia_door", true, 3.0F, 15.0F);
    public static final Block_v1_11 dark_oak_door = new Block_v1_11(197, "dark_oak_door", true, 3.0F, 15.0F);
    public static final Block_v1_11 end_rod = new Block_v1_11(198, "end_rod", false, 0.0F, 0.0F);
    public static final Block_v1_11 chorus_plant = new Block_v1_11(199, "chorus_plant", false, 0.4F, 2.0F);
    public static final Block_v1_11 chorus_flower = new Block_v1_11(200, "chorus_flower", false, 0.4F, 2.0F);
    public static final Block_v1_11 purpur_block = new Block_v1_11(201, "purpur_block", true, 1.5F, 30.0F);
    public static final Block_v1_11 purpur_pillar = new Block_v1_11(202, "purpur_pillar", true, 1.5F, 30.0F);
    public static final Block_v1_11 purpur_stairs = new Block_v1_11(203, "purpur_stairs", true, 1.5F, 30.0F);
    public static final Block_v1_11 purpur_double_slab = new Block_v1_11(204, "purpur_double_slab", true, 2.0F, 30.0F);
    public static final Block_v1_11 purpur_slab = new Block_v1_11(205, "purpur_slab", true, 2.0F, 30.0F);
    public static final Block_v1_11 end_bricks = new Block_v1_11(206, "end_bricks", true, 0.8F, 4.0F);
    public static final Block_v1_11 beetroots = new Block_v1_11(207, "beetroots", false, 0.0F, 0.0F);
    public static final Block_v1_11 grass_path = new Block_v1_11(208, "grass_path", true, 0.65F, 3.25F);
    public static final Block_v1_11 end_gateway = new Block_v1_11(209, "end_gateway", false, -1.0F, 1.8E7F);
    public static final Block_v1_11 repeating_command_block = new Block_v1_11(210, "repeating_command_block", true, -1.0F, 1.8E7F);
    public static final Block_v1_11 chain_command_block = new Block_v1_11(211, "chain_command_block", true, -1.0F, 1.8E7F);
    public static final Block_v1_11 frosted_ice = new Block_v1_11(212, "frosted_ice", true, 0.5F, 2.5F);
    public static final Block_v1_11 magma = new Block_v1_11(213, "magma", true, 0.5F, 2.5F);
    public static final Block_v1_11 nether_wart_block = new Block_v1_11(214, "nether_wart_block", true, 1.0F, 5.0F);
    public static final Block_v1_11 red_nether_brick = new Block_v1_11(215, "red_nether_brick", true, 2.0F, 30.0F);
    public static final Block_v1_11 bone_block = new Block_v1_11(216, "bone_block", true, 2.0F, 10.0F);
    public static final Block_v1_11 structure_void = new Block_v1_11(217, "structure_void", false, 0.0F, 0.0F);
    public static final Block_v1_11 observer = new Block_v1_11(218, "observer", true, 3.0F, 15.0F);
    public static final Block_v1_11 white_shulker_box = new Block_v1_11(219, "white_shulker_box", true, 2.0F, 10.0F);
    public static final Block_v1_11 orange_shulker_box = new Block_v1_11(220, "orange_shulker_box", true, 2.0F, 10.0F);
    public static final Block_v1_11 magenta_shulker_box = new Block_v1_11(221, "magenta_shulker_box", true, 2.0F, 10.0F);
    public static final Block_v1_11 light_blue_shulker_box = new Block_v1_11(222, "light_blue_shulker_box", true, 2.0F, 10.0F);
    public static final Block_v1_11 yellow_shulker_box = new Block_v1_11(223, "yellow_shulker_box", true, 2.0F, 10.0F);
    public static final Block_v1_11 lime_shulker_box = new Block_v1_11(224, "lime_shulker_box", true, 2.0F, 10.0F);
    public static final Block_v1_11 pink_shulker_box = new Block_v1_11(225, "pink_shulker_box", true, 2.0F, 10.0F);
    public static final Block_v1_11 gray_shulker_box = new Block_v1_11(226, "gray_shulker_box", true, 2.0F, 10.0F);
    public static final Block_v1_11 silver_shulker_box = new Block_v1_11(227, "silver_shulker_box", true, 2.0F, 10.0F);
    public static final Block_v1_11 cyan_shulker_box = new Block_v1_11(228, "cyan_shulker_box", true, 2.0F, 10.0F);
    public static final Block_v1_11 purple_shulker_box = new Block_v1_11(229, "purple_shulker_box", true, 2.0F, 10.0F);
    public static final Block_v1_11 blue_shulker_box = new Block_v1_11(230, "blue_shulker_box", true, 2.0F, 10.0F);
    public static final Block_v1_11 brown_shulker_box = new Block_v1_11(231, "brown_shulker_box", true, 2.0F, 10.0F);
    public static final Block_v1_11 green_shulker_box = new Block_v1_11(232, "green_shulker_box", true, 2.0F, 10.0F);
    public static final Block_v1_11 red_shulker_box = new Block_v1_11(233, "red_shulker_box", true, 2.0F, 10.0F);
    public static final Block_v1_11 black_shulker_box = new Block_v1_11(234, "black_shulker_box", true, 2.0F, 10.0F);
    public static final Block_v1_11 structure_block = new Block_v1_11(255, "structure_block", true, -1.0F, 1.8E7F);

    public static Block_v1_11 getById(final int blockId) {
        for (Block_v1_11 block : BLOCK_LIST) {
            if (block.blockId() == blockId) return block;
        }
        return null;
    }

    public static Block_v1_11 getByName(final String name) {
        for (Block_v1_11 block : BLOCK_LIST) {
            if (block.name().equals(name)) return block;
        }
        return null;
    }


    private final int blockId;
    private final String name;
    private final boolean opaque;
    private final float hardness;
    private final float resistance;

    private Block_v1_11(final int blockId, final String name, final boolean opaque, final float hardness, final float resistance) {
        this.blockId = blockId;
        this.name = name;
        this.opaque = opaque;
        this.hardness = hardness;
        this.resistance = resistance;

        BLOCK_LIST.add(this);
    }

    @Override
    public int blockId() {
        return this.blockId;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public boolean opaque() {
        return this.opaque;
    }

    @Override
    public float hardness() {
        return this.hardness;
    }

    @Override
    public float resistance() {
        return this.resistance;
    }

}

/*
String out = "";
for (Object rawBlock : REGISTRY) {
    Block block = (Block) rawBlock;
    int id = REGISTRY.getIDForObject(block);
    String name = REGISTRY.getNameForObject(block).getResourcePath();
    out += "public static final Block_v1_11 ";
    out += name;
    out += " = new Block_v1_11(" + id;
    out += ", \"" + name + "\"";
    out += ", " + !block.translucent;
    out += ", " + block.blockHardness + "F";
    out += ", " + block.blockResistance + "F";
    out += ");\n";
}
System.out.println(out);
 */
