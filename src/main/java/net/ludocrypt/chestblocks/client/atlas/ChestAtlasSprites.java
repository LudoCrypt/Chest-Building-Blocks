package net.ludocrypt.chestblocks.client.atlas;

import java.util.ArrayList;

import net.ludocrypt.chestblocks.ChestBlocks;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;

public class ChestAtlasSprites {

	public static final Identifier CHEST_BLOCK_ATLAS_TEXTURE = ChestBlocks.id("textures/atlas/chest_blocks.png");

	public static final SpriteIdentifier NORMAL_LATCH = new SpriteIdentifier(CHEST_BLOCK_ATLAS_TEXTURE, ChestBlocks.id("chest_blocks/normal/latch"));
	public static final SpriteIdentifier ENDER_LATCH = new SpriteIdentifier(CHEST_BLOCK_ATLAS_TEXTURE, ChestBlocks.id("chest_blocks/ender/latch"));
	public static final SpriteIdentifier CHRISTMAS_LATCH = new SpriteIdentifier(CHEST_BLOCK_ATLAS_TEXTURE, ChestBlocks.id("chest_blocks/christmas/latch"));

	public static final ArrayList<SpriteIdentifier> NORMAL_CHESTS = new ArrayList<SpriteIdentifier>();
	public static final ArrayList<SpriteIdentifier> ENDER_CHESTS = new ArrayList<SpriteIdentifier>();
	public static final ArrayList<SpriteIdentifier> CHRISTMAS_CHESTS = new ArrayList<SpriteIdentifier>();

	public static void init() {

		for (int i = 0; i <= 27; i++) {
			NORMAL_CHESTS.add(new SpriteIdentifier(CHEST_BLOCK_ATLAS_TEXTURE, ChestBlocks.id("chest_blocks/normal/" + i)));
			ENDER_CHESTS.add(new SpriteIdentifier(CHEST_BLOCK_ATLAS_TEXTURE, ChestBlocks.id("chest_blocks/ender/" + i)));
			CHRISTMAS_CHESTS.add(new SpriteIdentifier(CHEST_BLOCK_ATLAS_TEXTURE, ChestBlocks.id("chest_blocks/christmas/" + i)));
		}

	}

}
