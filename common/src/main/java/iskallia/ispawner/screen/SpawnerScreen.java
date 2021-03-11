package iskallia.ispawner.screen;

import iskallia.ispawner.screen.handler.SpawnerScreenHandler;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;

public class SpawnerScreen extends HandledScreen<SpawnerScreenHandler> {

	public SpawnerScreen(SpawnerScreenHandler handler, PlayerInventory inventory, Text title) {
		super(handler, inventory, title);
	}

	@Override
	protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
		this.renderBackground(matrices);
	}

	@Override
	public boolean isPauseScreen() {
		return false;
	}

}
