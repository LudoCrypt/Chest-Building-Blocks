package net.ludocrypt.chestblocks.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.ludocrypt.chestblocks.block.ChestBlock;
import net.ludocrypt.chestblocks.block.EnderChestBlock;
import net.minecraft.block.Block;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.EnderChestBlockEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

@Environment(EnvType.CLIENT)
@Mixin(BuiltinModelItemRenderer.class)
public class BuiltinModelItemRendererMixin {

	@Shadow
	private final ChestBlockEntity renderChestNormal = new ChestBlockEntity();

	@Shadow
	private final EnderChestBlockEntity renderChestEnder = new EnderChestBlockEntity();

	@Inject(method = "render", at = @At("HEAD"))
	private void render(ItemStack stack, ModelTransformation.Mode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, CallbackInfo ci) {
		Item item = stack.getItem();
		if (item instanceof BlockItem) {
			Block block = ((BlockItem) item).getBlock();
			if (block instanceof ChestBlock) {
				BlockEntityRenderDispatcher.INSTANCE.renderEntity(renderChestNormal, matrices, vertexConsumers, light, overlay);
			} else if (block instanceof EnderChestBlock) {
				BlockEntityRenderDispatcher.INSTANCE.renderEntity(renderChestEnder, matrices, vertexConsumers, light, overlay);
			}
		}
	}
}
