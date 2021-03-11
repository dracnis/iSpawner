package iskallia.ispawner.block.render;

import iskallia.ispawner.block.entity.SpawnerBlockEntity;
import iskallia.ispawner.init.ModItems;
import iskallia.ispawner.util.Color;
import iskallia.ispawner.world.spawner.SpawnerController;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

import java.util.LinkedHashMap;
import java.util.Map;

public class SpawnerBlockRenderer extends BlockEntityRenderer<SpawnerBlockEntity> {

	public static final Map<Integer, Color> COLOR_PER_WEIGHT = new LinkedHashMap<>();

	static {
		COLOR_PER_WEIGHT.put(0, new Color(0, 0, 0));
		COLOR_PER_WEIGHT.put(1, new Color(255, 0, 0));
		COLOR_PER_WEIGHT.put(2, new Color(0, 255, 0));
		COLOR_PER_WEIGHT.put(3, new Color(0, 0, 255));
		COLOR_PER_WEIGHT.put(4, new Color(255, 255, 0));
		COLOR_PER_WEIGHT.put(5, new Color(255, 0, 255));
		COLOR_PER_WEIGHT.put(6, new Color(0, 255, 255));
		COLOR_PER_WEIGHT.put(7, new Color(255, 255, 255));
	}

	public SpawnerBlockRenderer(BlockEntityRenderDispatcher dispatcher) {
		super(dispatcher);
	}

	@Override
	public void render(SpawnerBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
		ClientPlayerEntity player = MinecraftClient.getInstance().player;
		if(player == null)return;

		boolean rendered = tryRender(entity, matrices, vertexConsumers, player.getStackInHand(Hand.MAIN_HAND))
				|| tryRender(entity, matrices, vertexConsumers, player.getStackInHand(Hand.OFF_HAND));
	}

	public boolean tryRender(SpawnerBlockEntity entity, MatrixStack matrices, VertexConsumerProvider vertexConsumers, ItemStack stack) {
		if(stack.getItem() == ModItems.SPAWNER_CONTROLLER) {
			SpawnerController controller = new SpawnerController(stack.getOrCreateSubTag("Controller"));

			if(controller.getTarget().isPresent() && controller.getTarget().get().equals(entity.getPos())) {
				entity.renderer.refresh(entity);
				entity.renderer.render(matrices, vertexConsumers.getBuffer(RenderLayer.getLines()), entity);
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean rendersOutsideBoundingBox(SpawnerBlockEntity blockEntity) {
		return true;
	}

	public static Color getColorFor(int weight) {
		Color color = null;

		for(Map.Entry<Integer, Color> entry: COLOR_PER_WEIGHT.entrySet()) {
			if(entry.getKey() > weight)break;
			color = entry.getValue();
		}

		return color;
	}

}
