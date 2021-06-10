package net.ludocrypt.chestblocks;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.ludocrypt.chestblocks.block.ChestBlock;
import net.ludocrypt.chestblocks.block.EnderChestBlock;
import net.ludocrypt.chestblocks.block.TrappedChestBlock;
import net.ludocrypt.chestblocks.block.entity.ChestBlockBuilderEntity;
import net.ludocrypt.chestblocks.block.entity.EnderChestBlockBuilderEntity;
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

	public static final Block CHEST_BLOCK = Registry.register(Registry.BLOCK, id("chest_block"), new ChestBlock(FabricBlockSettings.copy(Blocks.CHEST)));
	public static final BlockItem CHEST_BLOCK_ITEM = Registry.register(Registry.ITEM, id("chest_block"), new BlockItem(CHEST_BLOCK, new Item.Settings().group(ItemGroup.DECORATIONS)));

	public static final Block TRAPPED_CHEST_BLOCK = Registry.register(Registry.BLOCK, id("trapped_chest_block"), new TrappedChestBlock(FabricBlockSettings.copy(Blocks.CHEST)));
	public static final BlockItem TRAPPED_CHEST_BLOCK_ITEM = Registry.register(Registry.ITEM, id("trapped_chest_block"), new BlockItem(TRAPPED_CHEST_BLOCK, new Item.Settings().group(ItemGroup.DECORATIONS)));

	public static final BlockEntityType<ChestBlockBuilderEntity> CHEST_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, id("chest_block"), FabricBlockEntityTypeBuilder.create(ChestBlockBuilderEntity::new, CHEST_BLOCK, TRAPPED_CHEST_BLOCK).build());

	public static final Block ENDER_CHEST_BLOCK = Registry.register(Registry.BLOCK, id("ender_chest_block"), new EnderChestBlock(FabricBlockSettings.copy(Blocks.ENDER_CHEST)));
	public static final BlockItem ENDER_CHEST_BLOCK_ITEM = Registry.register(Registry.ITEM, id("ender_chest_block"), new BlockItem(ENDER_CHEST_BLOCK, new Item.Settings().group(ItemGroup.DECORATIONS)));
	public static final BlockEntityType<EnderChestBlockBuilderEntity> ENDER_CHEST_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, id("ender_chest_block"), FabricBlockEntityTypeBuilder.create(EnderChestBlockBuilderEntity::new, ENDER_CHEST_BLOCK).build());

	@Override
	public void onInitialize() {

	}

	public static Identifier id(String id) {
		return new Identifier("chestblocks", id);
	}

}
