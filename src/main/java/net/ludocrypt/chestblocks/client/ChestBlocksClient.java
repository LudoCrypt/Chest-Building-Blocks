package net.ludocrypt.chestblocks.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.ludocrypt.chestblocks.ChestBlocks;
import net.ludocrypt.chestblocks.block.entity.ChestBlockBuilderEntity;
import net.ludocrypt.chestblocks.block.entity.EnderChestBlockBuilderEntity;
import net.ludocrypt.chestblocks.client.atlas.ChestAtlasSprites;
import net.ludocrypt.chestblocks.client.entity.ChestBlockRenderer;
import net.ludocrypt.chestblocks.config.ChestBlocksConfig;

public class ChestBlocksClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		ChestBlocksConfig.init();
		BlockEntityRendererRegistry.INSTANCE.register(ChestBlocks.CHEST_BLOCK_ENTITY, (ctx) -> new ChestBlockRenderer<ChestBlockBuilderEntity>(ChestAtlasSprites.NORMAL));
		BlockEntityRendererRegistry.INSTANCE.register(ChestBlocks.ENDER_CHEST_BLOCK_ENTITY, (ctx) -> new ChestBlockRenderer<EnderChestBlockBuilderEntity>(ChestAtlasSprites.ENDER));
	}

}
