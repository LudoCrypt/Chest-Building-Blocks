package net.ludocrypt.chestblocks;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.ludocrypt.chestblocks.block.ChestBlock;
import net.ludocrypt.chestblocks.block.entity.ChestBlockEntity;
import net.ludocrypt.chestblocks.config.ChestBlocksConfig;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ChestBlocks implements ModInitializer {

	public static final Logger LOGGER = LogManager.getLogger("ChestBlocks");

	public static final Block CHEST_BLOCK = Registry.register(Registry.BLOCK, id("chestblock"), new ChestBlock(FabricBlockSettings.copy(Blocks.CHEST)));
	public static final BlockItem CHEST_BLOCK_ITEM = Registry.register(Registry.ITEM, id("chestblock"), new BlockItem(CHEST_BLOCK, new Item.Settings().group(ItemGroup.DECORATIONS)));
	public static BlockEntityType<ChestBlockEntity> CHEST_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, id("chestblock"), BlockEntityType.Builder.create(ChestBlockEntity::new, CHEST_BLOCK).build(null));

	@Override
	public void onInitialize() {
		ChestBlocksConfig.init();
	}

	public static Identifier id(String id) {
		return new Identifier("chestblocks", id);
	}

	public static void warn(String str) {
		LOGGER.warn(str);
	}

}
