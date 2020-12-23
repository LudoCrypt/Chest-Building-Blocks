package net.ludocrypt.chestblocks.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.ludocrypt.chestblocks.ChestBlocks;
import net.ludocrypt.chestblocks.client.atlas.ChestAtlasSprites;
import net.ludocrypt.chestblocks.client.entity.ChestBlockRenderer;
import net.ludocrypt.chestblocks.client.entity.EnderChestBlockRenderer;

public class ChestBlocksClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {

		BlockEntityRendererRegistry.INSTANCE.register(ChestBlocks.CHEST_BLOCK_ENTITY, ChestBlockRenderer::new);
		BlockEntityRendererRegistry.INSTANCE.register(ChestBlocks.ENDER_CHEST_BLOCK_ENTITY, EnderChestBlockRenderer::new);
		ChestAtlasSprites.init();

	}

}
