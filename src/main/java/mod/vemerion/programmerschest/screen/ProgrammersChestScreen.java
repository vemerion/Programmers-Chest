package mod.vemerion.programmerschest.screen;

import java.awt.Color;
import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;

import mod.vemerion.programmerschest.Main;
import mod.vemerion.programmerschest.container.ProgrammersChestContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.fonts.TextInputUtil;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class ProgrammersChestScreen extends ContainerScreen<ProgrammersChestContainer> {

	private static final int CONSOLE_X = 8;
	private static final int CONSOLE_Y = 8;
	private static final int CONSOLE_WIDTH = 160;
	private static final int CONSOLE_HEIGHT = 70;
	private static final int WHITE = Color.WHITE.getRGB();

	private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(Main.MODID,
			"textures/gui/programmers_chest_screen.png");

	private String text = "";

	private TextInputUtil input = new TextInputUtil(() -> text, (s) -> text = s,
			() -> TextInputUtil.getClipboardText(minecraft), (s) -> TextInputUtil.setClipboardText(minecraft, s),
			(s) -> {
				return s.length() < 1024 && font.getWordWrappedHeight(s, 114) <= 128;
			});

	public ProgrammersChestScreen(ProgrammersChestContainer screenContainer, PlayerInventory inv,
			ITextComponent titleIn) {
		super(screenContainer, inv, titleIn);
	}

	@Override
	protected void init() {
		super.init();
	}

	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
		if (Screen.isSelectAll(keyCode)) {
			input.selectAll();
			return true;
		} else if (Screen.isCopy(keyCode)) {
			input.copySelectedText();
			return true;
		} else if (Screen.isPaste(keyCode)) {
			input.insertClipboardText();
			return true;
		} else if (Screen.isCut(keyCode)) {
			input.cutText();
			return true;
		} else {
			switch (keyCode) {
			case 259:
				input.deleteCharAtSelection(-1);
				return true;
			case 261:
				input.deleteCharAtSelection(1);
				return true;
//			case 262:
//				input.moveCursorByChar(1, Screen.hasShiftDown());
//				return true;
//			case 263:
//				input.moveCursorByChar(-1, Screen.hasShiftDown());
//				return true;
			case 256:
				return super.keyPressed(keyCode, scanCode, modifiers);
			default:
				return false;
			}
		}
	}

	@Override
	public boolean charTyped(char codePoint, int modifiers) {
		if (super.charTyped(codePoint, modifiers)) {
			return true;
		} else if (SharedConstants.isAllowedCharacter(codePoint)) {
			input.putText(Character.toString(codePoint));
			return true;
		}
		return false;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int mouseX,
			int mouseY) {
		Minecraft.getInstance().getTextureManager().bindTexture(GUI_TEXTURE);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		blit(matrixStack, x, y, 0, 0, xSize, ySize);

		List<IReorderingProcessor> lines = font.trimStringToWidth(new StringTextComponent(text), CONSOLE_WIDTH);
		for (int i = 0; i < lines.size(); i++) {
			font.func_238407_a_(matrixStack, lines.get(i), guiLeft + CONSOLE_X, guiTop + CONSOLE_Y + font.FONT_HEIGHT * i, WHITE);
		}
	}

	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		super.render(matrixStack, mouseX, mouseY, partialTicks);

		renderHoveredTooltip(matrixStack, mouseX, mouseY);
	}

}
