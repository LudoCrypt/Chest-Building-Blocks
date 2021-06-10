package net.ludocrypt.chestblocks.client.entity;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import com.google.common.collect.Lists;

import net.ludocrypt.chestblocks.block.ChestBlock;
import net.ludocrypt.chestblocks.client.atlas.ChestAtlasSprites;
import net.ludocrypt.chestblocks.config.ChestBlocksConfig;
import net.ludocrypt.chestblocks.util.BlockContext;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPart.Cuboid;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class ChestBlockRenderer<T extends BlockEntity> implements BlockEntityRenderer<T> {

	private final SpriteIdentifier sprite;

	public ChestBlockRenderer(SpriteIdentifier sprite) {
		super();
		Calendar calendar = Calendar.getInstance();
		if (calendar.get(2) + 1 == 12 && calendar.get(5) >= 24 && calendar.get(5) <= 26) {
			this.sprite = ChestAtlasSprites.CHRISTMAS;
		} else {
			this.sprite = sprite;
		}
	}

	@Override
	public void render(T entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
		BlockContext context = new BlockContext(entity.getWorld(), entity.getPos(), entity.getCachedState().getBlock());

		renderLatch(entity, matrices, vertexConsumers, light, overlay);

		boolean shouldUp = shouldUpFull(entity.getWorld(), entity.getPos(), entity.getCachedState().getBlock(), ChestBlocksConfig.getInstance().expensiveRendering);

		renderNorth(entity, matrices, vertexConsumers, light, overlay, context, shouldUp);
		renderEast(entity, matrices, vertexConsumers, light, overlay, context, shouldUp);
		renderSouth(entity, matrices, vertexConsumers, light, overlay, context, shouldUp);
		renderWest(entity, matrices, vertexConsumers, light, overlay, context, shouldUp);
		renderUp(entity, matrices, vertexConsumers, light, overlay, context, shouldUp);
		renderLower(entity, matrices, vertexConsumers, light, overlay, context, shouldUp);

	}

	private void renderNorth(T entity, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, BlockContext context, boolean shouldUp) {
		int u = 0;
		int v = 0;

		if (!context.Down && !context.West && !context.WestDown) {
			u = 0;
			v = 0;
		}

		if (context.Down && !context.West && !context.WestDown) {
			u = 4;
			v = 0;
		}

		if (!context.Down && context.West && !context.WestDown) {
			u = 6;
			v = 0;
		}

		if (context.Down && context.West && !context.WestDown) {
			u = 8;
			v = 0;
		}

		if (context.Down && context.West && context.WestDown) {
			u = 2;
			v = 0;
		}

		renderPartNorthern(entity, matrices, vertexConsumers, light, overlay, 0, 0, 1, u * 8, v * 8);

		if (!context.Down && !context.East && !context.EastDown) {
			u = 1;
			v = 0;
		}

		if (context.Down && !context.East && !context.EastDown) {
			u = 5;
			v = 0;
		}

		if (!context.Down && context.East && !context.EastDown) {
			u = 7;
			v = 0;
		}

		if (context.Down && context.East && !context.EastDown) {
			u = 9;
			v = 0;
		}

		if (context.Down && context.East && context.EastDown) {
			u = 3;
			v = 0;
		}

		renderPartNorthern(entity, matrices, vertexConsumers, light, overlay, 8, 0, 1, u * 8, v * 8);

		if (!context.Up && !context.West && !context.WestUp) {
			u = 0;
			v = 1;
		}

		if (shouldUp) {
			if (entity.getCachedState().get(ChestBlock.FACING) == Direction.NORTH) {
				u = 0;
				v = 12;
			} else {
				u = 0;
				v = 13;
			}
		}

		if (context.Up && !context.West && !context.WestUp) {
			u = 4;
			v = 1;
		}

		if (!context.Up && context.West && !context.WestUp) {

			if (shouldUp) {
				if (entity.getCachedState().get(ChestBlock.FACING) == Direction.NORTH) {
					u = 2;
					v = 12;
				} else {
					u = 2;
					v = 13;
				}
			} else {
				u = 6;
				v = 1;
			}

		}

		if (context.Up && context.West && !context.WestUp) {
			u = 8;
			v = 1;
		}

		if (context.Up && context.West && context.WestUp) {
			u = 2;
			v = 1;
		}

		renderPartNorthern(entity, matrices, vertexConsumers, light, overlay, 0, 8, 1, u * 8, v * 8);

		if (!context.Up && !context.East && !context.EastUp) {
			u = 1;
			v = 1;
		}

		if (shouldUp) {
			if (entity.getCachedState().get(ChestBlock.FACING) == Direction.NORTH) {
				u = 1;
				v = 12;
			} else {
				u = 1;
				v = 13;
			}
		}

		if (context.Up && !context.East && !context.EastUp) {
			u = 5;
			v = 1;
		}

		if (!context.Up && context.East && !context.EastUp) {
			if (shouldUp) {
				if (entity.getCachedState().get(ChestBlock.FACING) == Direction.NORTH) {
					u = 3;
					v = 12;
				} else {
					u = 3;
					v = 13;
				}
			} else {
				u = 7;
				v = 1;
			}

		}

		if (context.Up && context.East && !context.EastUp) {
			u = 9;
			v = 1;
		}

		if (context.Up && context.East && context.EastUp) {
			u = 3;
			v = 1;
		}

		renderPartNorthern(entity, matrices, vertexConsumers, light, overlay, 8, 8, 1, u * 8, v * 8);
	}

	private void renderEast(T entity, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, BlockContext context, boolean shouldUp) {
		int u = 0;
		int v = 0;

		if (!context.Down && !context.North && !context.NorthDown) {
			u = 0;
			v = 0;
		}

		if (context.Down && !context.North && !context.NorthDown) {
			u = 4;
			v = 0;
		}

		if (!context.Down && context.North && !context.NorthDown) {
			u = 6;
			v = 0;
		}

		if (context.Down && context.North && !context.NorthDown) {
			u = 8;
			v = 0;
		}

		if (context.Down && context.North && context.NorthDown) {
			u = 2;
			v = 0;
		}

		renderPartEastern(entity, matrices, vertexConsumers, light, overlay, 0, 0, 1, u * 8, v * 8);

		if (!context.Down && !context.South && !context.SouthDown) {
			u = 1;
			v = 0;
		}

		if (context.Down && !context.South && !context.SouthDown) {
			u = 5;
			v = 0;
		}

		if (!context.Down && context.South && !context.SouthDown) {
			u = 7;
			v = 0;
		}

		if (context.Down && context.South && !context.SouthDown) {
			u = 9;
			v = 0;
		}

		if (context.Down && context.South && context.SouthDown) {
			u = 3;
			v = 0;
		}

		renderPartEastern(entity, matrices, vertexConsumers, light, overlay, 8, 0, 1, u * 8, v * 8);

		if (!context.Up && !context.North && !context.NorthUp) {
			u = 0;
			v = 1;
		}

		if (shouldUp) {
			if (entity.getCachedState().get(ChestBlock.FACING) == Direction.EAST) {
				u = 0;
				v = 12;
			} else {
				u = 0;
				v = 13;
			}
		}

		if (context.Up && !context.North && !context.NorthUp) {
			u = 4;
			v = 1;
		}

		if (!context.Up && context.North && !context.NorthUp) {

			if (shouldUp) {
				if (entity.getCachedState().get(ChestBlock.FACING) == Direction.EAST) {
					u = 2;
					v = 12;
				} else {
					u = 2;
					v = 13;
				}
			} else {
				u = 6;
				v = 1;
			}

		}

		if (context.Up && context.North && !context.NorthUp) {
			u = 8;
			v = 1;
		}

		if (context.Up && context.North && context.NorthUp) {
			u = 2;
			v = 1;
		}

		renderPartEastern(entity, matrices, vertexConsumers, light, overlay, 0, 8, 1, u * 8, v * 8);

		if (!context.Up && !context.South && !context.SouthUp) {
			u = 1;
			v = 1;
		}

		if (shouldUp) {
			if (entity.getCachedState().get(ChestBlock.FACING) == Direction.EAST) {
				u = 1;
				v = 12;
			} else {
				u = 1;
				v = 13;
			}
		}

		if (context.Up && !context.South && !context.SouthUp) {
			u = 5;
			v = 1;
		}

		if (!context.Up && context.South && !context.SouthUp) {
			if (shouldUp) {
				if (entity.getCachedState().get(ChestBlock.FACING) == Direction.EAST) {
					u = 3;
					v = 12;
				} else {
					u = 3;
					v = 13;
				}
			} else {
				u = 7;
				v = 1;
			}

		}

		if (context.Up && context.South && !context.SouthUp) {
			u = 9;
			v = 1;
		}

		if (context.Up && context.South && context.SouthUp) {
			u = 3;
			v = 1;
		}

		renderPartEastern(entity, matrices, vertexConsumers, light, overlay, 8, 8, 1, u * 8, v * 8);
	}

	private void renderSouth(T entity, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, BlockContext context, boolean shouldUp) {
		int u = 0;
		int v = 0;

		if (!context.Down && !context.East && !context.EastDown) {
			u = 0;
			v = 0;
		}

		if (context.Down && !context.East && !context.EastDown) {
			u = 4;
			v = 0;
		}

		if (!context.Down && context.East && !context.EastDown) {
			u = 6;
			v = 0;
		}

		if (context.Down && context.East && !context.EastDown) {
			u = 8;
			v = 0;
		}

		if (context.Down && context.East && context.EastDown) {
			u = 2;
			v = 0;
		}

		renderPartSouthern(entity, matrices, vertexConsumers, light, overlay, 0, 0, 1, u * 8, v * 8);

		if (!context.Down && !context.West && !context.WestDown) {
			u = 1;
			v = 0;
		}

		if (context.Down && !context.West && !context.WestDown) {
			u = 5;
			v = 0;
		}

		if (!context.Down && context.West && !context.WestDown) {
			u = 7;
			v = 0;
		}

		if (context.Down && context.West && !context.WestDown) {
			u = 9;
			v = 0;
		}

		if (context.Down && context.West && context.WestDown) {
			u = 3;
			v = 0;
		}

		renderPartSouthern(entity, matrices, vertexConsumers, light, overlay, 8, 0, 1, u * 8, v * 8);

		if (!context.Up && !context.East && !context.EastUp) {
			u = 0;
			v = 1;
		}

		if (shouldUp) {
			if (entity.getCachedState().get(ChestBlock.FACING) == Direction.SOUTH) {
				u = 0;
				v = 12;
			} else {
				u = 0;
				v = 13;
			}
		}

		if (context.Up && !context.East && !context.EastUp) {
			u = 4;
			v = 1;
		}

		if (!context.Up && context.East && !context.EastUp) {

			if (shouldUp) {
				if (entity.getCachedState().get(ChestBlock.FACING) == Direction.SOUTH) {
					u = 2;
					v = 12;
				} else {
					u = 2;
					v = 13;
				}
			} else {
				u = 6;
				v = 1;
			}

		}

		if (context.Up && context.East && !context.EastUp) {
			u = 8;
			v = 1;
		}

		if (context.Up && context.East && context.EastUp) {
			u = 2;
			v = 1;
		}

		renderPartSouthern(entity, matrices, vertexConsumers, light, overlay, 0, 8, 1, u * 8, v * 8);

		if (!context.Up && !context.West && !context.WestUp) {
			u = 1;
			v = 1;
		}

		if (shouldUp) {
			if (entity.getCachedState().get(ChestBlock.FACING) == Direction.SOUTH) {
				u = 1;
				v = 12;
			} else {
				u = 1;
				v = 13;
			}
		}

		if (context.Up && !context.West && !context.WestUp) {
			u = 5;
			v = 1;
		}

		if (!context.Up && context.West && !context.WestUp) {
			if (shouldUp) {
				if (entity.getCachedState().get(ChestBlock.FACING) == Direction.SOUTH) {
					u = 3;
					v = 12;
				} else {
					u = 3;
					v = 13;
				}
			} else {
				u = 7;
				v = 1;
			}

		}

		if (context.Up && context.West && !context.WestUp) {
			u = 9;
			v = 1;
		}

		if (context.Up && context.West && context.WestUp) {
			u = 3;
			v = 1;
		}

		renderPartSouthern(entity, matrices, vertexConsumers, light, overlay, 8, 8, 1, u * 8, v * 8);
	}

	private void renderWest(T entity, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, BlockContext context, boolean shouldUp) {
		int u = 0;
		int v = 0;

		if (!context.Down && !context.South && !context.SouthDown) {
			u = 0;
			v = 0;
		}

		if (context.Down && !context.South && !context.SouthDown) {
			u = 4;
			v = 0;
		}

		if (!context.Down && context.South && !context.SouthDown) {
			u = 6;
			v = 0;
		}

		if (context.Down && context.South && !context.SouthDown) {
			u = 8;
			v = 0;
		}

		if (context.Down && context.South && context.SouthDown) {
			u = 2;
			v = 0;
		}

		renderPartWestern(entity, matrices, vertexConsumers, light, overlay, 0, 0, 1, u * 8, v * 8);

		if (!context.Down && !context.North && !context.NorthDown) {
			u = 1;
			v = 0;
		}

		if (context.Down && !context.North && !context.NorthDown) {
			u = 5;
			v = 0;
		}

		if (!context.Down && context.North && !context.NorthDown) {
			u = 7;
			v = 0;
		}

		if (context.Down && context.North && !context.NorthDown) {
			u = 9;
			v = 0;
		}

		if (context.Down && context.North && context.NorthDown) {
			u = 3;
			v = 0;
		}

		renderPartWestern(entity, matrices, vertexConsumers, light, overlay, 8, 0, 1, u * 8, v * 8);

		if (!context.Up && !context.South && !context.SouthUp) {
			u = 0;
			v = 1;
		}

		if (shouldUp) {
			if (entity.getCachedState().get(ChestBlock.FACING) == Direction.WEST) {
				u = 0;
				v = 12;
			} else {
				u = 0;
				v = 13;
			}
		}

		if (context.Up && !context.South && !context.SouthUp) {
			u = 4;
			v = 1;
		}

		if (!context.Up && context.South && !context.SouthUp) {

			if (shouldUp) {
				if (entity.getCachedState().get(ChestBlock.FACING) == Direction.WEST) {
					u = 2;
					v = 12;
				} else {
					u = 2;
					v = 13;
				}
			} else {
				u = 6;
				v = 1;
			}

		}

		if (context.Up && context.South && !context.SouthUp) {
			u = 8;
			v = 1;
		}

		if (context.Up && context.South && context.SouthUp) {
			u = 2;
			v = 1;
		}

		renderPartWestern(entity, matrices, vertexConsumers, light, overlay, 0, 8, 1, u * 8, v * 8);

		if (!context.Up && !context.North && !context.NorthUp) {
			u = 1;
			v = 1;
		}

		if (shouldUp) {
			if (entity.getCachedState().get(ChestBlock.FACING) == Direction.WEST) {
				u = 1;
				v = 12;
			} else {
				u = 1;
				v = 13;
			}
		}

		if (context.Up && !context.North && !context.NorthUp) {
			u = 5;
			v = 1;
		}

		if (!context.Up && context.North && !context.NorthUp) {
			if (shouldUp) {
				if (entity.getCachedState().get(ChestBlock.FACING) == Direction.WEST) {
					u = 3;
					v = 12;
				} else {
					u = 3;
					v = 13;
				}
			} else {
				u = 7;
				v = 1;
			}

		}

		if (context.Up && context.North && !context.NorthUp) {
			u = 9;
			v = 1;
		}

		if (context.Up && context.North && context.NorthUp) {
			u = 3;
			v = 1;
		}

		renderPartWestern(entity, matrices, vertexConsumers, light, overlay, 8, 8, 1, u * 8, v * 8);
	}

	private void renderUp(T entity, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, BlockContext context, boolean shouldUp) {
		int u = 0;
		int v = 0;

		if (!context.North && !context.West && !context.NorthWest) {
			u = 0;
			v = 0;
		}

		if (context.North && !context.West && !context.NorthWest) {
			u = 4;
			v = 0;
		}

		if (!context.North && context.West && !context.NorthWest) {
			u = 6;
			v = 0;
		}

		if (context.North && context.West && !context.NorthWest) {
			u = 8;
			v = 0;
		}

		if (context.North && context.West && context.NorthWest) {
			u = 2;
			v = 0;
		}

		renderPartUpper(entity, matrices, vertexConsumers, light, overlay, 0, 0, 1, u * 8, v * 8);

		if (!context.North && !context.East && !context.NorthEast) {
			u = 1;
			v = 0;
		}

		if (context.North && !context.East && !context.NorthEast) {
			u = 5;
			v = 0;
		}

		if (!context.North && context.East && !context.NorthEast) {
			u = 7;
			v = 0;
		}

		if (context.North && context.East && !context.NorthEast) {
			u = 9;
			v = 0;
		}

		if (context.North && context.East && context.NorthEast) {
			u = 3;
			v = 0;
		}

		renderPartUpper(entity, matrices, vertexConsumers, light, overlay, 8, 0, 1, u * 8, v * 8);

		if (!context.South && !context.West && !context.SouthWest) {
			u = 0;
			v = 1;
		}

		if (context.South && !context.West && !context.SouthWest) {
			u = 4;
			v = 1;
		}

		if (!context.South && context.West && !context.SouthWest) {
			u = 6;
			v = 1;
		}

		if (context.South && context.West && !context.SouthWest) {
			u = 8;
			v = 1;
		}

		if (context.South && context.West && context.SouthWest) {
			u = 2;
			v = 1;
		}

		renderPartUpper(entity, matrices, vertexConsumers, light, overlay, 0, 8, 1, u * 8, v * 8);

		if (!context.South && !context.East && !context.SouthEast) {
			u = 1;
			v = 1;
		}

		if (context.South && !context.East && !context.SouthEast) {
			u = 5;
			v = 1;
		}

		if (!context.South && context.East && !context.SouthEast) {
			u = 7;
			v = 1;
		}

		if (context.South && context.East && !context.SouthEast) {
			u = 9;
			v = 1;
		}

		if (context.South && context.East && context.SouthEast) {
			u = 3;
			v = 1;
		}

		renderPartUpper(entity, matrices, vertexConsumers, light, overlay, 8, 8, 1, u * 8, v * 8);
	}

	private void renderLower(T entity, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, BlockContext context, boolean shouldUp) {
		int u = 0;
		int v = 0;

		if (!context.South && !context.West && !context.SouthWest) {
			u = 0;
			v = 0;
		}

		if (context.South && !context.West && !context.SouthWest) {
			u = 4;
			v = 0;
		}

		if (!context.South && context.West && !context.SouthWest) {
			u = 6;
			v = 0;
		}

		if (context.South && context.West && !context.SouthWest) {
			u = 8;
			v = 0;
		}

		if (context.South && context.West && context.SouthWest) {
			u = 2;
			v = 0;
		}

		renderPartLower(entity, matrices, vertexConsumers, light, overlay, 0, 0, 1, u * 8, v * 8);

		if (!context.South && !context.East && !context.SouthEast) {
			u = 1;
			v = 0;
		}

		if (context.South && !context.East && !context.SouthEast) {
			u = 5;
			v = 0;
		}

		if (!context.South && context.East && !context.SouthEast) {
			u = 7;
			v = 0;
		}

		if (context.South && context.East && !context.SouthEast) {
			u = 9;
			v = 0;
		}

		if (context.South && context.East && context.SouthEast) {
			u = 3;
			v = 0;
		}

		renderPartLower(entity, matrices, vertexConsumers, light, overlay, 8, 0, 1, u * 8, v * 8);

		if (!context.North && !context.West && !context.NorthWest) {
			u = 0;
			v = 1;
		}

		if (context.North && !context.West && !context.NorthWest) {
			u = 4;
			v = 1;
		}

		if (!context.North && context.West && !context.NorthWest) {
			u = 6;
			v = 1;
		}

		if (context.North && context.West && !context.NorthWest) {
			u = 8;
			v = 1;
		}

		if (context.North && context.West && context.NorthWest) {
			u = 2;
			v = 1;
		}

		renderPartLower(entity, matrices, vertexConsumers, light, overlay, 0, 8, 1, u * 8, v * 8);

		if (!context.North && !context.East && !context.NorthEast) {
			u = 1;
			v = 1;
		}

		if (context.North && !context.East && !context.NorthEast) {
			u = 5;
			v = 1;
		}

		if (!context.North && context.East && !context.NorthEast) {
			u = 7;
			v = 1;
		}

		if (context.North && context.East && !context.NorthEast) {
			u = 9;
			v = 1;
		}

		if (context.North && context.East && context.NorthEast) {
			u = 3;
			v = 1;
		}

		renderPartLower(entity, matrices, vertexConsumers, light, overlay, 8, 8, 1, u * 8, v * 8);
	}

	private void renderPartNorthern(T entity, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, int x, int y, int z, int u, int v) {
		Direction face = entity.getCachedState().get(ChestBlock.FACING);

		if (v != 96 && v != 104) {
			switch (face) {
			case EAST:
				v += 48;
				break;
			case SOUTH:
				v += 32;
				break;
			case WEST:
				v += 16;
				break;
			default:
				v += 0;
				break;
			}
		}

		ModelPart modelPart = new ModelPart(Lists.newArrayList(new Cuboid(u * 2, v, x, y - 1, z, 8, 8, 0, 0, 0, 0, false, 256, 256)), new HashMap<String, ModelPart>());

		modelPart.render(matrices, sprite.getVertexConsumer(vertexConsumers, RenderLayer::getEntityCutout), light, overlay);
	}

	private void renderPartEastern(T entity, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, int x, int y, int z, int u, int v) {
		Direction face = entity.getCachedState().get(ChestBlock.FACING);

		if (v != 96 && v != 104) {
			switch (face) {
			case NORTH:
				v += 16;
				break;
			case WEST:
				v += 32;
				break;
			case SOUTH:
				v += 48;
				break;
			default:
				v += 0;
				break;
			}
		}

		matrices.push();
		ModelPart modelPart = new ModelPart(Lists.newArrayList(new Cuboid(u * 2, v, x, y - 1, z - 16, 8, 8, 0, 0, 0, 0, false, 256, 256)), new HashMap<String, ModelPart>());

		modelPart.yaw += (float) Math.toRadians(-90) / 2;
		modelPart.rotate(matrices);

		modelPart.render(matrices, sprite.getVertexConsumer(vertexConsumers, RenderLayer::getEntityCutout), light, overlay);
		matrices.pop();
	}

	private void renderPartSouthern(T entity, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, int x, int y, int z, int u, int v) {
		Direction face = entity.getCachedState().get(ChestBlock.FACING);

		if (v != 96 && v != 104) {
			switch (face) {
			case NORTH:
				v += 32;
				break;
			case WEST:
				v += 48;
				break;
			case EAST:
				v += 16;
				break;
			default:
				v += 0;
				break;
			}
		}

		matrices.push();
		ModelPart modelPart = new ModelPart(Lists.newArrayList(new Cuboid(u * 2, v, x - 16, y - 1, z - 16, 8, 8, 0, 0, 0, 0, false, 256, 256)), new HashMap<String, ModelPart>());

		modelPart.yaw += (float) Math.toRadians(180) / 2;
		modelPart.rotate(matrices);

		modelPart.render(matrices, sprite.getVertexConsumer(vertexConsumers, RenderLayer::getEntityCutout), light, overlay);
		matrices.pop();
	}

	private void renderPartWestern(T entity, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, int x, int y, int z, int u, int v) {
		Direction face = entity.getCachedState().get(ChestBlock.FACING);

		if (v != 96 && v != 104) {
			switch (face) {
			case NORTH:
				v += 48;
				break;
			case EAST:
				v += 32;
				break;
			case SOUTH:
				v += 16;
				break;
			default:
				v += 0;
				break;
			}
		}

		matrices.push();
		ModelPart modelPart = new ModelPart(Lists.newArrayList(new Cuboid(u * 2, v, x - 16, y - 1, z, 8, 8, 0, 0, 0, 0, false, 256, 256)), new HashMap<String, ModelPart>());

		modelPart.yaw += (float) Math.toRadians(90) / 2;
		modelPart.rotate(matrices);

		modelPart.render(matrices, sprite.getVertexConsumer(vertexConsumers, RenderLayer::getEntityCutout), light, overlay);
		matrices.pop();
	}

	private void renderPartUpper(T entity, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, int x, int y, int z, int u, int v) {
		if (v != 96 && v != 104) {
			v += 64;
		}

		matrices.push();
		ModelPart modelPart = new ModelPart(Lists.newArrayList(new Cuboid(u * 2, v, x, y, z - 15, 8, 8, 0, 0, 0, 0, false, 256, 256)), new HashMap<String, ModelPart>());

		modelPart.pitch += (float) Math.toRadians(90) / 2;
		modelPart.rotate(matrices);

		modelPart.render(matrices, sprite.getVertexConsumer(vertexConsumers, RenderLayer::getEntityCutout), light, overlay);
		matrices.pop();
	}

	private void renderPartLower(T entity, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, int x, int y, int z, int u, int v) {
		if (v != 96 && v != 104) {
			v += 80;
		}

		matrices.push();
		ModelPart modelPart = new ModelPart(Lists.newArrayList(new Cuboid(u * 2, v, x, y - 16, z - 1, 8, 8, 0, 0, 0, 0, false, 256, 256)), new HashMap<String, ModelPart>());

		modelPart.pitch += (float) Math.toRadians(-90) / 2;
		modelPart.rotate(matrices);

		modelPart.render(matrices, sprite.getVertexConsumer(vertexConsumers, RenderLayer::getEntityCutout), light, overlay);
		matrices.pop();
	}

	private void renderLatch(T entity, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
		if (shouldUpFull(entity.getWorld(), entity.getPos(), entity.getCachedState().getBlock(), ChestBlocksConfig.getInstance().expensiveRendering)) {
			VertexConsumer vertexConsumer = sprite.getVertexConsumer(vertexConsumers, RenderLayer::getEntityCutout);

			matrices.push();

			List<Cuboid> list = Lists.newArrayList();

			switch (entity.getCachedState().get(ChestBlock.FACING)) {
			case EAST:
				list.add(new Cuboid(0, 112, 7, 7, -16, 2, 4, 1, 0, 0, 0, false, 256, 256));
				break;
			case NORTH:
				list.add(new Cuboid(0, 112, 7, 7, 0, 2, 4, 1, 0, 0, 0, false, 256, 256));
				break;
			case SOUTH:
				list.add(new Cuboid(0, 112, 7, 7, 15, 2, 4, 1, 0, 0, 0, false, 256, 256));
				break;
			case WEST:
				list.add(new Cuboid(0, 112, 7, 7, -1, 2, 4, 1, 0, 0, 0, false, 256, 256));
				break;
			default:
				list.add(new Cuboid(0, 112, 7, 7, 0, 2, 4, 1, 0, 0, 0, false, 256, 256));
				break;
			}

			ModelPart modelPart = new ModelPart(list, new HashMap<String, ModelPart>());

			switch (entity.getCachedState().get(ChestBlock.FACING)) {
			case EAST:
				modelPart.yaw = -0.7854F;
				break;
			case NORTH:
				modelPart.yaw = 0;
				break;
			case SOUTH:
				modelPart.yaw = 0;
				break;
			case WEST:
				modelPart.yaw = -0.7854F;
				break;
			default:
				modelPart.yaw = 0;
				break;
			}

			modelPart.rotate(matrices);

			modelPart.render(matrices, vertexConsumer, light, overlay);

			matrices.pop();
		}
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

}
