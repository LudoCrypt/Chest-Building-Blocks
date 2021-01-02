package net.ludocrypt.chestblocks.mixin;

import java.util.function.Consumer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.ludocrypt.chestblocks.client.atlas.ChestAtlasSprites;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.util.SpriteIdentifier;

@Environment(EnvType.CLIENT)
@Mixin(TexturedRenderLayers.class)
public class TexturedRenderLayersMixin {

	@Inject(method = "addDefaultTextures", at = @At("TAIL"))
	private static void addDefaultTextures(Consumer<SpriteIdentifier> adder, CallbackInfo ci) {
		ChestAtlasSprites.CHEST_SPRITES.forEach((id, sprite) -> {
			adder.accept(sprite);
		});
	}

}
