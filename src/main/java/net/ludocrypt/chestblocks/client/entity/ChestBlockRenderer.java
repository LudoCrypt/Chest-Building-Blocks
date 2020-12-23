package net.ludocrypt.chestblocks.client.entity;

import java.util.ArrayList;
import java.util.Calendar;

import net.ludocrypt.chestblocks.block.ChestBlock;
import net.ludocrypt.chestblocks.block.entity.ChestBlockBuilderEntity;
import net.ludocrypt.chestblocks.client.atlas.ChestAtlasSprites;
import net.ludocrypt.chestblocks.config.ChestBlocksConfig;
import net.minecraft.block.Block;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class ChestBlockRenderer extends BlockEntityRenderer<ChestBlockBuilderEntity> {

	private boolean christmas;

	private boolean Up;
	private boolean Down;
	private boolean North;
	private boolean East;
	private boolean South;
	private boolean West;

	private boolean NorthEast;
	private boolean SouthEast;
	private boolean NorthWest;
	private boolean SouthWest;

	private boolean NorthUp;
	private boolean EastUp;
	private boolean SouthUp;
	private boolean WestUp;

	private boolean NorthDown;
	private boolean EastDown;
	private boolean SouthDown;
	private boolean WestDown;

	private ArrayList<SpriteIdentifier> CHESTS = ChestAtlasSprites.NORMAL_CHESTS;

	public ChestBlockRenderer(BlockEntityRenderDispatcher dispatcher) {
		super(dispatcher);
		Calendar calendar = Calendar.getInstance();
		if (calendar.get(2) + 1 == 12 && calendar.get(5) >= 24 && calendar.get(5) <= 26) {
			this.christmas = true;
		}
	}

	@Override
	public void render(ChestBlockBuilderEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
		Block block = entity.getCachedState().getBlock();
		World world = entity.getWorld();
		BlockPos pos = entity.getPos();
		Up = world.getBlockState(pos.up()).getBlock() == block;
		Down = world.getBlockState(pos.down()).getBlock() == block;
		North = world.getBlockState(pos.north()).getBlock() == block;
		East = world.getBlockState(pos.east()).getBlock() == block;
		South = world.getBlockState(pos.south()).getBlock() == block;
		West = world.getBlockState(pos.west()).getBlock() == block;

		NorthEast = world.getBlockState(pos.north().east()).getBlock() == block && North && East;
		SouthEast = world.getBlockState(pos.south().east()).getBlock() == block && South && East;
		NorthWest = world.getBlockState(pos.north().west()).getBlock() == block && North && West;
		SouthWest = world.getBlockState(pos.south().west()).getBlock() == block && South && West;

		NorthUp = world.getBlockState(pos.north().up()).getBlock() == block && North && Up;
		EastUp = world.getBlockState(pos.east().up()).getBlock() == block && East && Up;
		SouthUp = world.getBlockState(pos.south().up()).getBlock() == block && South && Up;
		WestUp = world.getBlockState(pos.west().up()).getBlock() == block && West && Up;

		NorthDown = world.getBlockState(pos.north().down()).getBlock() == block && North && Down;
		EastDown = world.getBlockState(pos.east().down()).getBlock() == block && East && Down;
		SouthDown = world.getBlockState(pos.south().down()).getBlock() == block && South && Down;
		WestDown = world.getBlockState(pos.west().down()).getBlock() == block && West && Down;
		boolean shouldUp = shouldUpFull(world, pos, block, ChestBlocksConfig.getInstance().expensiveRendering);

		if (christmas) {
			CHESTS = ChestAtlasSprites.CHRISTMAS_CHESTS;
		}

		renderNorthern(entity, tickDelta, matrices, vertexConsumers, light, overlay, shouldUp);
		renderSouthern(entity, tickDelta, matrices, vertexConsumers, light, overlay, shouldUp);
		renderEastern(entity, tickDelta, matrices, vertexConsumers, light, overlay, shouldUp);
		renderWestern(entity, tickDelta, matrices, vertexConsumers, light, overlay, shouldUp);
		renderUpper(entity, tickDelta, matrices, vertexConsumers, light, overlay, shouldUp);
		renderLower(entity, tickDelta, matrices, vertexConsumers, light, overlay, shouldUp);

		if (entity.latched && shouldUp) {
			SpriteIdentifier spriteIdentifier = christmas ? ChestAtlasSprites.CHRISTMAS_LATCH : ChestAtlasSprites.NORMAL_LATCH;
			VertexConsumer vertexConsumer = spriteIdentifier.getVertexConsumer(vertexConsumers, RenderLayer::getEntityCutout);

			matrices.push();

			ModelPart modelPart = new ModelPart(8, 8, 0, 0);

			modelPart.setTextureOffset(0, 0);

			switch (entity.getCachedState().get(ChestBlock.FACING)) {
			case EAST:
				modelPart.addCuboid(7, 7, -16, 2, 4, 1);
				modelPart.yaw = -0.7854F;
				break;
			case NORTH:
				modelPart.addCuboid(7, 7, 0, 2, 4, 1);
				modelPart.yaw = 0;
				break;
			case SOUTH:
				modelPart.addCuboid(7, 7, 15, 2, 4, 1);
				modelPart.yaw = 0;
				break;
			case WEST:
				modelPart.addCuboid(7, 7, -1, 2, 4, 1);
				modelPart.yaw = -0.7854F;
				break;
			default:
				modelPart.addCuboid(7, 7, 0, 2, 4, 1);
				modelPart.yaw = 0;
				break;
			}

			modelPart.rotate(matrices);

			modelPart.render(matrices, vertexConsumer, light, overlay);

			matrices.pop();
		}

	}

	private void renderNorthern(ChestBlockBuilderEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, boolean shouldUp) {

		// Bottom Right

		int sprite = 0;

		if (Down && !West && !WestDown) {
			sprite = 8;
		}

		if (!Down && West && !WestDown) {
			sprite = 12;
		}

		if (Down && West && !WestDown) {
			sprite = 16;
		}

		if (Down && West && WestDown) {
			sprite = 4;
		}

		SpriteIdentifier spriteIdentifier = CHESTS.get(sprite);
		VertexConsumer vertexConsumer = spriteIdentifier.getVertexConsumer(vertexConsumers, RenderLayer::getEntityCutout);

		renderNorthernCuboid(matrices, vertexConsumer, light, overlay, 0, 0, 0, 0, 1);

		// Bottom Left

		sprite = 2;

		if (Down && !East && !EastDown) {
			sprite = 10;
		}

		if (!Down && East && !EastDown) {
			sprite = 14;
		}

		if (Down && East && !EastDown) {
			sprite = 18;
		}

		if (Down && East && EastDown) {
			sprite = 6;
		}

		spriteIdentifier = CHESTS.get(sprite);
		vertexConsumer = spriteIdentifier.getVertexConsumer(vertexConsumers, RenderLayer::getEntityCutout);

		renderNorthernCuboid(matrices, vertexConsumer, light, overlay, 0, 0, 8, 0, 1);

		// Top Right

		sprite = 1;

		if (shouldUp) {
			if (entity.latched && entity.getCachedState().get(ChestBlock.FACING) == Direction.NORTH) {
				sprite = 60;
			} else {
				sprite = 61;
			}
		}

		if (Up && !West && !WestUp) {
			sprite = 9;
		}

		if (!Up && West && !WestUp) {

			if (shouldUp) {
				if (entity.latched && entity.getCachedState().get(ChestBlock.FACING) == Direction.NORTH) {
					sprite = 64;
				} else {
					sprite = 65;
				}
			} else {
				sprite = 13;
			}

		}

		if (Up && West && !WestUp) {
			sprite = 17;
		}

		if (Up && West && WestUp) {
			sprite = 5;
		}

		spriteIdentifier = CHESTS.get(sprite);
		vertexConsumer = spriteIdentifier.getVertexConsumer(vertexConsumers, RenderLayer::getEntityCutout);

		renderNorthernCuboid(matrices, vertexConsumer, light, overlay, 0, 0, 0, 8, 1);

		// Top Left

		sprite = 3;

		if (shouldUp) {
			if (entity.latched && entity.getCachedState().get(ChestBlock.FACING) == Direction.NORTH) {
				sprite = 62;
			} else {
				sprite = 63;
			}
		}

		if (Up && !East && !EastUp) {
			sprite = 11;
		}

		if (!Up && East && !EastUp) {

			if (shouldUp) {
				if (entity.latched && entity.getCachedState().get(ChestBlock.FACING) == Direction.NORTH) {
					sprite = 66;
				} else {
					sprite = 67;
				}
			} else {
				sprite = 15;
			}

		}

		if (Up && East && !EastUp) {
			sprite = 19;
		}

		if (Up && East && EastUp) {
			sprite = 7;
		}

		spriteIdentifier = CHESTS.get(sprite);
		vertexConsumer = spriteIdentifier.getVertexConsumer(vertexConsumers, RenderLayer::getEntityCutout);

		renderNorthernCuboid(matrices, vertexConsumer, light, overlay, 0, 0, 8, 8, 1);
	}

	private boolean shouldUpFull(World world, BlockPos pos, Block block, int recursion) {
		boolean shouldUp = shouldUp(world, pos, block);

		int current = recursion - 1;

		if (current > 0) {
			BlockPos.Mutable mutPos = pos.mutableCopy();

			while (world.getBlockState(mutPos.north()).getBlock() == block && shouldUp) {
				mutPos.move(Direction.NORTH);
				shouldUp = shouldUpFull(world, mutPos, block, current);
			}

			mutPos = pos.mutableCopy();

			while (world.getBlockState(mutPos.east()).getBlock() == block && shouldUp) {
				mutPos.move(Direction.EAST);
				shouldUp = shouldUpFull(world, mutPos, block, current);
			}

			mutPos = pos.mutableCopy();

			while (world.getBlockState(mutPos.south()).getBlock() == block && shouldUp) {
				mutPos.move(Direction.SOUTH);
				shouldUp = shouldUpFull(world, mutPos, block, current);
			}

			mutPos = pos.mutableCopy();

			while (world.getBlockState(mutPos.west()).getBlock() == block && shouldUp) {
				mutPos.move(Direction.WEST);
				shouldUp = shouldUpFull(world, mutPos, block, current);
			}
		} else {
			BlockPos.Mutable mutPos = pos.mutableCopy();

			while (world.getBlockState(mutPos.north()).getBlock() == block && shouldUp) {
				mutPos.move(Direction.NORTH);
				shouldUp = shouldUp(world, mutPos, block);
			}

			mutPos = pos.mutableCopy();

			while (world.getBlockState(mutPos.east()).getBlock() == block && shouldUp) {
				mutPos.move(Direction.EAST);
				shouldUp = shouldUp(world, mutPos, block);
			}

			mutPos = pos.mutableCopy();

			while (world.getBlockState(mutPos.south()).getBlock() == block && shouldUp) {
				mutPos.move(Direction.SOUTH);
				shouldUp = shouldUp(world, mutPos, block);
			}

			mutPos = pos.mutableCopy();

			while (world.getBlockState(mutPos.west()).getBlock() == block && shouldUp) {
				mutPos.move(Direction.WEST);
				shouldUp = shouldUp(world, mutPos, block);
			}
		}

		return shouldUp;
	}

	private boolean shouldUp(World world, BlockPos pos, Block block) {
		BlockPos.Mutable mutPos = pos.mutableCopy();

		boolean shouldUp = !(world.getBlockState(mutPos.up()).getBlock() == block);

		while (world.getBlockState(mutPos.east()).getBlock() == block && shouldUp) {

			shouldUp = !(world.getBlockState(mutPos.east().up()).getBlock() == block);
			mutPos.move(Direction.EAST);

		}

		mutPos = pos.mutableCopy();

		while (world.getBlockState(mutPos.west()).getBlock() == block && shouldUp) {

			shouldUp = !(world.getBlockState(mutPos.west().up()).getBlock() == block);
			mutPos.move(Direction.WEST);

		}

		mutPos = pos.mutableCopy();

		while (world.getBlockState(mutPos.north()).getBlock() == block && shouldUp) {

			shouldUp = !(world.getBlockState(mutPos.north().up()).getBlock() == block);
			mutPos.move(Direction.NORTH);

		}

		mutPos = pos.mutableCopy();

		while (world.getBlockState(mutPos.south()).getBlock() == block && shouldUp) {

			shouldUp = !(world.getBlockState(mutPos.south().up()).getBlock() == block);
			mutPos.move(Direction.SOUTH);

		}

		return shouldUp;
	}

	private void renderNorthernCuboid(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int u, int v, int x, int y, int z) {
		matrices.push();

		ModelPart modelPart = new ModelPart(8, 8, 0, 0);

		modelPart.setTextureOffset(u, v);

		modelPart.addCuboid(x, y - 1, z, 8, 8, 0);

		modelPart.render(matrices, vertexConsumer, light, overlay);

		matrices.pop();
	}

	private void renderSouthern(ChestBlockBuilderEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, boolean shouldUp) {

		// Bottom Right

		int sprite = 0;

		if (Down && !East && !EastDown) {
			sprite = 8;
		}

		if (!Down && East && !EastDown) {
			sprite = 12;
		}

		if (Down && East && !EastDown) {
			sprite = 16;
		}

		if (Down && East && EastDown) {
			sprite = 4;
		}

		SpriteIdentifier spriteIdentifier = CHESTS.get(sprite);
		VertexConsumer vertexConsumer = spriteIdentifier.getVertexConsumer(vertexConsumers, RenderLayer::getEntityCutout);

		renderSouthernCuboid(matrices, vertexConsumer, light, overlay, 0, 0, 0, 0, 1);

		// Bottom Left

		sprite = 2;

		if (Down && !West && !WestDown) {
			sprite = 10;
		}

		if (!Down && West && !WestDown) {
			sprite = 14;
		}

		if (Down && West && !WestDown) {
			sprite = 18;
		}

		if (Down && West && WestDown) {
			sprite = 6;
		}

		spriteIdentifier = CHESTS.get(sprite);
		vertexConsumer = spriteIdentifier.getVertexConsumer(vertexConsumers, RenderLayer::getEntityCutout);

		renderSouthernCuboid(matrices, vertexConsumer, light, overlay, 0, 0, 8, 0, 1);

		// Top Right

		sprite = 1;

		if (shouldUp) {
			if (entity.latched && entity.getCachedState().get(ChestBlock.FACING) == Direction.SOUTH) {
				sprite = 60;
			} else {
				sprite = 61;
			}
		}

		if (Up && !East && !EastUp) {
			sprite = 9;
		}

		if (!Up && East && !EastUp) {
			if (shouldUp) {
				if (entity.latched && entity.getCachedState().get(ChestBlock.FACING) == Direction.SOUTH) {
					sprite = 64;
				} else {
					sprite = 65;
				}
			} else {
				sprite = 13;
			}
		}

		if (Up && East && !EastUp) {
			sprite = 17;
		}

		if (Up && East && EastUp) {
			sprite = 5;
		}

		spriteIdentifier = CHESTS.get(sprite);
		vertexConsumer = spriteIdentifier.getVertexConsumer(vertexConsumers, RenderLayer::getEntityCutout);

		renderSouthernCuboid(matrices, vertexConsumer, light, overlay, 0, 0, 0, 8, 1);

		// Top Left

		sprite = 3;

		if (shouldUp) {
			if (entity.latched && entity.getCachedState().get(ChestBlock.FACING) == Direction.SOUTH) {
				sprite = 62;
			} else {
				sprite = 63;
			}
		}

		if (Up && !West && !WestUp) {
			sprite = 11;
		}

		if (!Up && West && !WestUp) {

			if (shouldUp) {
				if (entity.latched && entity.getCachedState().get(ChestBlock.FACING) == Direction.SOUTH) {
					sprite = 66;
				} else {
					sprite = 67;
				}
			} else {
				sprite = 15;
			}
		}

		if (Up && West && !WestUp) {
			sprite = 19;
		}

		if (Up && West && WestUp) {
			sprite = 7;
		}

		spriteIdentifier = CHESTS.get(sprite);
		vertexConsumer = spriteIdentifier.getVertexConsumer(vertexConsumers, RenderLayer::getEntityCutout);

		renderSouthernCuboid(matrices, vertexConsumer, light, overlay, 0, 0, 8, 8, 1);
	}

	private void renderSouthernCuboid(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int u, int v, int x, int y, int z) {
		matrices.push();

		ModelPart modelPart = new ModelPart(8, 8, 0, 0);

		modelPart.setTextureOffset(u, v);

		modelPart.addCuboid(x - 16, y - 1, z - 16, 8, 8, 0);

		modelPart.yaw = 1.5708F;

		modelPart.rotate(matrices);

		modelPart.render(matrices, vertexConsumer, light, overlay);

		matrices.pop();
	}

	private void renderEastern(ChestBlockBuilderEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, boolean shouldUp) {

		// Bottom Right

		int sprite = 0;

		if (Down && !North && !NorthDown) {
			sprite = 8;
		}

		if (!Down && North && !NorthDown) {
			sprite = 12;
		}

		if (Down && North && !NorthDown) {
			sprite = 16;
		}

		if (Down && North && NorthDown) {
			sprite = 4;
		}

		SpriteIdentifier spriteIdentifier = CHESTS.get(sprite);
		VertexConsumer vertexConsumer = spriteIdentifier.getVertexConsumer(vertexConsumers, RenderLayer::getEntityCutout);

		renderEasternCuboid(matrices, vertexConsumer, light, overlay, 0, 0, 0, 0, 1);

		// Bottom Left

		sprite = 2;

		if (Down && !South && !SouthDown) {
			sprite = 10;
		}

		if (!Down && South && !SouthDown) {
			sprite = 14;
		}

		if (Down && South && !SouthDown) {
			sprite = 18;
		}

		if (Down && South && SouthDown) {
			sprite = 6;
		}

		spriteIdentifier = CHESTS.get(sprite);
		vertexConsumer = spriteIdentifier.getVertexConsumer(vertexConsumers, RenderLayer::getEntityCutout);

		renderEasternCuboid(matrices, vertexConsumer, light, overlay, 0, 0, 8, 0, 1);

		// Top Right

		sprite = 1;

		if (shouldUp) {
			if (entity.latched && entity.getCachedState().get(ChestBlock.FACING) == Direction.EAST) {
				sprite = 60;
			} else {
				sprite = 61;
			}
		}

		if (Up && !North && !NorthUp) {
			sprite = 9;
		}

		if (!Up && North && !NorthUp) {
			if (shouldUp) {
				if (entity.latched && entity.getCachedState().get(ChestBlock.FACING) == Direction.EAST) {
					sprite = 64;
				} else {
					sprite = 65;
				}
			} else {
				sprite = 13;
			}
		}

		if (Up && North && !NorthUp) {
			sprite = 17;
		}

		if (Up && North && NorthUp) {
			sprite = 5;
		}

		spriteIdentifier = CHESTS.get(sprite);
		vertexConsumer = spriteIdentifier.getVertexConsumer(vertexConsumers, RenderLayer::getEntityCutout);

		renderEasternCuboid(matrices, vertexConsumer, light, overlay, 0, 0, 0, 8, 1);

		// Top Left

		sprite = 3;

		if (shouldUp) {
			if (entity.latched && entity.getCachedState().get(ChestBlock.FACING) == Direction.EAST) {
				sprite = 62;
			} else {
				sprite = 63;
			}
		}

		if (Up && !South && !SouthUp) {
			sprite = 11;
		}

		if (!Up && South && !SouthUp) {
			if (shouldUp) {
				if (entity.latched && entity.getCachedState().get(ChestBlock.FACING) == Direction.EAST) {
					sprite = 66;
				} else {
					sprite = 67;
				}
			} else {
				sprite = 15;
			}
		}

		if (Up && South && !SouthUp) {
			sprite = 19;
		}

		if (Up && South && SouthUp) {
			sprite = 7;
		}

		spriteIdentifier = CHESTS.get(sprite);
		vertexConsumer = spriteIdentifier.getVertexConsumer(vertexConsumers, RenderLayer::getEntityCutout);

		renderEasternCuboid(matrices, vertexConsumer, light, overlay, 0, 0, 8, 8, 1);

	}

	private void renderEasternCuboid(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int u, int v, int x, int y, int z) {
		matrices.push();

		ModelPart modelPart = new ModelPart(8, 8, 0, 0);

		modelPart.setTextureOffset(u, v);

		modelPart.addCuboid(x, y - 1, z - 16, 8, 8, 0);

		modelPart.yaw = -0.7854F;

		modelPart.rotate(matrices);

		modelPart.render(matrices, vertexConsumer, light, overlay);

		matrices.pop();
	}

	private void renderWestern(ChestBlockBuilderEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, boolean shouldUp) {

		// Bottom Right

		int sprite = 0;

		if (Down && !South && !SouthDown) {
			sprite = 8;
		}

		if (!Down && South && !SouthDown) {
			sprite = 12;
		}

		if (Down && South && !SouthDown) {
			sprite = 16;
		}

		if (Down && South && SouthDown) {
			sprite = 4;
		}

		SpriteIdentifier spriteIdentifier = CHESTS.get(sprite);
		VertexConsumer vertexConsumer = spriteIdentifier.getVertexConsumer(vertexConsumers, RenderLayer::getEntityCutout);

		renderWesternCuboid(matrices, vertexConsumer, light, overlay, 0, 0, 0, 0, 1);

		// Bottom Left

		sprite = 2;

		if (Down && !North && !NorthDown) {
			sprite = 10;
		}

		if (!Down && North && !NorthDown) {
			sprite = 14;
		}

		if (Down && North && !NorthDown) {
			sprite = 18;
		}

		if (Down && North && NorthDown) {
			sprite = 6;
		}

		spriteIdentifier = CHESTS.get(sprite);
		vertexConsumer = spriteIdentifier.getVertexConsumer(vertexConsumers, RenderLayer::getEntityCutout);

		renderWesternCuboid(matrices, vertexConsumer, light, overlay, 0, 0, 8, 0, 1);

		// Top Right

		sprite = 1;

		if (shouldUp) {
			if (entity.latched && entity.getCachedState().get(ChestBlock.FACING) == Direction.WEST) {
				sprite = 60;
			} else {
				sprite = 61;
			}
		}

		if (Up && !South && !SouthUp) {
			sprite = 9;
		}

		if (!Up && South && !SouthUp) {
			if (shouldUp) {
				if (entity.latched && entity.getCachedState().get(ChestBlock.FACING) == Direction.WEST) {
					sprite = 64;
				} else {
					sprite = 65;
				}
			} else {
				sprite = 13;
			}
		}

		if (Up && South && !SouthUp) {
			sprite = 17;
		}

		if (Up && South && SouthUp) {
			sprite = 5;
		}

		spriteIdentifier = CHESTS.get(sprite);
		vertexConsumer = spriteIdentifier.getVertexConsumer(vertexConsumers, RenderLayer::getEntityCutout);

		renderWesternCuboid(matrices, vertexConsumer, light, overlay, 0, 0, 0, 8, 1);

		// Top Left

		sprite = 3;

		if (shouldUp) {
			if (entity.latched && entity.getCachedState().get(ChestBlock.FACING) == Direction.WEST) {
				sprite = 62;
			} else {
				sprite = 63;
			}
		}

		if (Up && !North && !NorthUp) {
			sprite = 11;
		}

		if (!Up && North && !NorthUp) {
			if (shouldUp) {
				if (entity.latched && entity.getCachedState().get(ChestBlock.FACING) == Direction.WEST) {
					sprite = 66;
				} else {
					sprite = 67;
				}
			} else {
				sprite = 15;
			}
		}

		if (Up && North && !NorthUp) {
			sprite = 19;
		}

		if (Up && North && NorthUp) {
			sprite = 7;
		}

		spriteIdentifier = CHESTS.get(sprite);
		vertexConsumer = spriteIdentifier.getVertexConsumer(vertexConsumers, RenderLayer::getEntityCutout);

		renderWesternCuboid(matrices, vertexConsumer, light, overlay, 0, 0, 8, 8, 1);

	}

	private void renderWesternCuboid(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int u, int v, int x, int y, int z) {
		matrices.push();

		ModelPart modelPart = new ModelPart(8, 8, 0, 0);

		modelPart.setTextureOffset(u, v);

		modelPart.addCuboid(x - 16, y - 1, z, 8, 8, 0);

		modelPart.yaw = 0.7854F;

		modelPart.rotate(matrices);

		modelPart.render(matrices, vertexConsumer, light, overlay);

		matrices.pop();
	}

	private void renderUpper(ChestBlockBuilderEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, boolean shouldUp) {

		// Bottom Right

		int sprite = 20;

		if (North && !West && !NorthWest) {
			sprite = 28;
		}

		if (!North && West && !NorthWest) {
			sprite = 32;
		}

		if (North && West && !NorthWest) {
			sprite = 36;
		}

		if (North && West && NorthWest) {
			sprite = 24;
		}

		SpriteIdentifier spriteIdentifier = CHESTS.get(sprite);
		VertexConsumer vertexConsumer = spriteIdentifier.getVertexConsumer(vertexConsumers, RenderLayer::getEntityCutout);

		renderUpperCuboid(matrices, vertexConsumer, light, overlay, 0, 0, 0, 0, 1);

		// Bottom Left

		sprite = 22;

		if (North && !East && !NorthEast) {
			sprite = 30;
		}

		if (!North && East && !NorthEast) {
			sprite = 34;
		}

		if (North && East && !NorthEast) {
			sprite = 38;
		}

		if (North && East && NorthEast) {
			sprite = 26;
		}

		spriteIdentifier = CHESTS.get(sprite);
		vertexConsumer = spriteIdentifier.getVertexConsumer(vertexConsumers, RenderLayer::getEntityCutout);

		renderUpperCuboid(matrices, vertexConsumer, light, overlay, 0, 0, 8, 0, 1);

		// Top Right

		sprite = 21;

		if (South && !West && !SouthWest) {
			sprite = 29;
		}

		if (!South && West && !SouthWest) {
			sprite = 33;
		}

		if (South && West && !SouthWest) {
			sprite = 37;
		}

		if (South && West && SouthWest) {
			sprite = 25;
		}

		spriteIdentifier = CHESTS.get(sprite);
		vertexConsumer = spriteIdentifier.getVertexConsumer(vertexConsumers, RenderLayer::getEntityCutout);

		renderUpperCuboid(matrices, vertexConsumer, light, overlay, 0, 0, 0, 8, 1);

		// Top Left

		sprite = 23;

		if (South && !East && !SouthEast) {
			sprite = 31;
		}

		if (!South && East && !SouthEast) {
			sprite = 35;
		}

		if (South && East && !SouthEast) {
			sprite = 39;
		}

		if (South && East && SouthEast) {
			sprite = 27;
		}

		spriteIdentifier = CHESTS.get(sprite);
		vertexConsumer = spriteIdentifier.getVertexConsumer(vertexConsumers, RenderLayer::getEntityCutout);

		renderUpperCuboid(matrices, vertexConsumer, light, overlay, 0, 0, 8, 8, 1);

	}

	private void renderUpperCuboid(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int u, int v, int x, int y, int z) {
		matrices.push();

		ModelPart modelPart = new ModelPart(8, 8, 0, 0);

		modelPart.setTextureOffset(u, v);

		modelPart.addCuboid(x, y, z - 15, 8, 8, 0);

		modelPart.pitch = 0.7854F;

		modelPart.rotate(matrices);

		modelPart.render(matrices, vertexConsumer, light, overlay);

		matrices.pop();
	}

	private void renderLower(ChestBlockBuilderEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, boolean shouldUp) {

		// Bottom Right

		int sprite = 40;

		if (South && !West && !SouthWest) {
			sprite = 48;
		}

		if (!South && West && !SouthWest) {
			sprite = 52;
		}

		if (South && West && !SouthWest) {
			sprite = 56;
		}

		if (South && West && SouthWest) {
			sprite = 44;
		}

		SpriteIdentifier spriteIdentifier = CHESTS.get(sprite);
		VertexConsumer vertexConsumer = spriteIdentifier.getVertexConsumer(vertexConsumers, RenderLayer::getEntityCutout);

		renderLowerCuboid(matrices, vertexConsumer, light, overlay, 0, 0, 0, 0, 1);

		// Bottom Left

		sprite = 42;

		if (South && !East && !SouthEast) {
			sprite = 50;
		}

		if (!South && East && !SouthEast) {
			sprite = 54;
		}

		if (South && East && !SouthEast) {
			sprite = 58;
		}

		if (South && East && SouthEast) {
			sprite = 46;
		}

		spriteIdentifier = CHESTS.get(sprite);
		vertexConsumer = spriteIdentifier.getVertexConsumer(vertexConsumers, RenderLayer::getEntityCutout);

		renderLowerCuboid(matrices, vertexConsumer, light, overlay, 0, 0, 8, 0, 1);

		// Top Right

		sprite = 41;

		if (North && !West && !NorthWest) {
			sprite = 49;
		}

		if (!North && West && !NorthWest) {
			sprite = 53;
		}

		if (North && West && !NorthWest) {
			sprite = 57;
		}

		if (North && West && NorthWest) {
			sprite = 45;
		}

		spriteIdentifier = CHESTS.get(sprite);
		vertexConsumer = spriteIdentifier.getVertexConsumer(vertexConsumers, RenderLayer::getEntityCutout);

		renderLowerCuboid(matrices, vertexConsumer, light, overlay, 0, 0, 0, 8, 1);

		// Top Left

		sprite = 43;

		if (North && !East && !NorthEast) {
			sprite = 51;
		}

		if (!North && East && !NorthEast) {
			sprite = 55;
		}

		if (North && East && !NorthEast) {
			sprite = 59;
		}

		if (North && East && NorthEast) {
			sprite = 47;
		}

		spriteIdentifier = CHESTS.get(sprite);
		vertexConsumer = spriteIdentifier.getVertexConsumer(vertexConsumers, RenderLayer::getEntityCutout);

		renderLowerCuboid(matrices, vertexConsumer, light, overlay, 0, 0, 8, 8, 1);

	}

	private void renderLowerCuboid(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int u, int v, int x, int y, int z) {
		matrices.push();

		ModelPart modelPart = new ModelPart(8, 8, 0, 0);

		modelPart.setTextureOffset(u, v);

		modelPart.addCuboid(x, y - 16, z - 1, 8, 8, 0);

		modelPart.pitch = -0.7854F;

		modelPart.rotate(matrices);

		modelPart.render(matrices, vertexConsumer, light, overlay);

		matrices.pop();
	}

}
