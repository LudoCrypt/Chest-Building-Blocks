package net.ludocrypt.chestblocks.client.atlas;

import java.util.HashMap;
import java.util.Map;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.ludocrypt.chestblocks.ChestBlocks;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class ChestAtlasSprites {

	public static final Map<Identifier, SpriteIdentifier> CHEST_SPRITES = new HashMap<Identifier, SpriteIdentifier>();

	public static final Identifier CHEST_BLOCK_ATLAS_TEXTURE = ChestBlocks.id("textures/atlas/chest_blocks.png");

	public static final SpriteIdentifier NORMAL = add("chest_blocks/normal");
	public static final SpriteIdentifier TRAPPED = add("chest_blocks/trapped");
	public static final SpriteIdentifier ENDER = add("chest_blocks/ender");
	public static final SpriteIdentifier CHRISTMAS = add("chest_blocks/christmas");

	private static SpriteIdentifier add(String id) {
		Identifier realId = ChestBlocks.id(id);
		SpriteIdentifier sprite = new SpriteIdentifier(CHEST_BLOCK_ATLAS_TEXTURE, realId);
		CHEST_SPRITES.put(realId, sprite);
		return sprite;
	}

}
