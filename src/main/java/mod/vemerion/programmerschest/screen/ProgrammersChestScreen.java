package mod.vemerion.programmerschest.screen;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.mojang.blaze3d.matrix.MatrixStack;

import mod.vemerion.programmerschest.Main;
import mod.vemerion.programmerschest.computer.Console;
import mod.vemerion.programmerschest.computer.Parser;
import mod.vemerion.programmerschest.computer.filesystem.DummyFileSystem;
import mod.vemerion.programmerschest.computer.filesystem.FileSystem;
import mod.vemerion.programmerschest.computer.program.Program;
import mod.vemerion.programmerschest.container.ProgrammersChestContainer;
import mod.vemerion.programmerschest.network.Network;
import mod.vemerion.programmerschest.network.ProgramMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.fonts.TextInputUtil;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.network.PacketDistributor;

public class ProgrammersChestScreen extends ContainerScreen<ProgrammersChestContainer> implements Console {

	private static final int CONSOLE_X = 8;
	private static final int CONSOLE_Y = 8;
	private static final int CONSOLE_WIDTH = 240;
	private static final int CONSOLE_HEIGHT = 114;
	private static final int WHITE = Color.WHITE.getRGB();
	private static final int BLACK = Color.BLACK.getRGB();

	private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(Main.MODID,
			"textures/gui/programmers_chest_screen_big.png");

	private String inputText = "";
	private String path = "home>";
	private List<IReorderingProcessor> output;
	private int linePosition;

	private Animations animations;

	private FileSystem dummy = new DummyFileSystem();

	private TextInputUtil input = new TextInputUtil(() -> inputText, (s) -> inputText = s,
			() -> TextInputUtil.getClipboardText(minecraft), (s) -> TextInputUtil.setClipboardText(minecraft, s),
			(s) -> {
				return font.getWordWrappedHeight(path + s, CONSOLE_WIDTH) < CONSOLE_HEIGHT;
			});

	public ProgrammersChestScreen(ProgrammersChestContainer screenContainer, PlayerInventory inv,
			ITextComponent titleIn) {
		super(screenContainer, inv, titleIn);
	}

	@Override
	protected void init() {
		xSize = 256;
		ySize = 210;
		super.init();
		output = new ArrayList<>();
		animations = new Animations();
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
			case 257:
			case 335:
				enterInput();
				return true;
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

	private void enterInput() {
		List<IReorderingProcessor> inputLines = font.trimStringToWidth(new StringTextComponent(path + inputText),
				CONSOLE_WIDTH);
		for (IReorderingProcessor line : inputLines) {
			output.add(line);
		}
		if (inputText.length() > 200) {
			println("Input text too long!", minecraft.player);
			inputText = "";
			return;
		}
		Parser parser = new Parser();
		Program program = parser.parse(inputText);
		if (program.isClientOnlyProgram()) {
			program.run(this, dummy, Minecraft.getInstance().player);
		} else {
			Network.INSTANCE.send(PacketDistributor.SERVER.noArg(), new ProgramMessage(inputText, container.getPos()));
		}
		inputText = "";
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
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		animations.mouseClicked(mouseX, mouseY, button);
		return super.mouseClicked(mouseX, mouseY, button);
	}

	@Override
	public boolean mouseReleased(double mouseX, double mouseY, int button) {
		animations.mouseReleased(mouseX, mouseY, button);
		return super.mouseReleased(mouseX, mouseY, button);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int mouseX,
			int mouseY) {
		Minecraft.getInstance().getTextureManager().bindTexture(GUI_TEXTURE);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		blit(matrixStack, x, y, 0, 0, xSize, ySize);

		// Console text
		List<IReorderingProcessor> inputLines = font.trimStringToWidth(new StringTextComponent(path + inputText),
				CONSOLE_WIDTH);
		while ((output.size() + inputLines.size() - linePosition) * font.FONT_HEIGHT > CONSOLE_HEIGHT)
			linePosition++;

		int index = 0;
		int cursorX = guiLeft + CONSOLE_X;
		for (int i = linePosition; i < output.size(); i++) {
			font.func_238407_a_(matrixStack, output.get(i), guiLeft + CONSOLE_X,
					guiTop + CONSOLE_Y + font.FONT_HEIGHT * index, WHITE);
			index++;
		}

		for (IReorderingProcessor line : inputLines) {
			cursorX = font.func_238407_a_(matrixStack, line, guiLeft + CONSOLE_X,
					guiTop + CONSOLE_Y + font.FONT_HEIGHT * index, WHITE);
			index++;
		}

		// Draw cursor
		int cursorY = guiTop + CONSOLE_Y + font.FONT_HEIGHT * (index - (inputLines.size() == 0 ? 0 : 1));
		fill(matrixStack, cursorX, cursorY - 1, cursorX + 1, cursorY + font.FONT_HEIGHT, WHITE);

		animations.draw(matrixStack, mouseX, mouseY);
	}

	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		super.render(matrixStack, mouseX, mouseY, partialTicks);

		renderHoveredTooltip(matrixStack, mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int x, int y) { // Override to get rid of
																							// 'Inventory' text
	}

	@Override
	public void println(String s, PlayerEntity user) {
		List<IReorderingProcessor> lines = font.trimStringToWidth(new StringTextComponent(s), CONSOLE_WIDTH);
		for (IReorderingProcessor line : lines) {
			output.add(line);
		}
	}

	@Override
	public void close() {
		closeScreen();
	}

	@Override
	public void setPath(String path, PlayerEntity user) {
		this.path = path + ">";
	}

	// This got a bit out of hand
	private class Animations {
		private static final int TIMER_X = 10;
		private static final int TIMER_Y = 136;

		private static final int POSTIT_X = 11;
		private static final int POSTIT_Y = 166;
		private static final int POSTIT_LENGTH = 24;

		private static final int PICTURE_X = 216;
		private static final int PICTURE_Y = 136;
		private static final int PICTURE_U = 0;
		private static final int PICTURE_V = 210;
		private static final int PICTURE_WIDTH = 29;
		private static final int PICTURE_HEIGHT = 23;

		private static final int ARROW_WIDTH = 13;
		private static final int ARROW_HEIGHT = 10;
		private static final int ARROW_X = PICTURE_X + PICTURE_WIDTH / 2 - ARROW_WIDTH / 2;
		private static final int ARROW_Y = PICTURE_Y + PICTURE_HEIGHT;
		private static final int ARROW_U = 0;
		private static final int ARROW_V = 233;

		private static final int KEY_U = 13;
		private static final int KEY_V = 233;
		private static final int KEY_WIDTH = 18;
		private static final int KEY_HEIGHT = 10;
		private static final int KEY_X = PICTURE_X + PICTURE_WIDTH / 2 - KEY_WIDTH / 2;
		private static final int KEY_Y = PICTURE_Y + PICTURE_HEIGHT / 2 - KEY_HEIGHT / 2;

		private static final int CHEST_X = 217;
		private static final int CHEST_Y = 182;
		private static final int CHEST_U = 31;
		private static final int CHEST_V = 242;
		private static final int CHEST_WIDTH = 23;
		private static final int CHEST_HEIGHT = 14;

		private Set<Point> drawing;

		private int pictureIndex;
		private boolean isArrowDown;
		private boolean hasKey;
		private boolean isChestOpen;

		private Animations() {
			drawing = new HashSet<>();
		}

		private void draw(MatrixStack matrix, int mouseX, int mouseY) {
			// Timer
			font.drawString(matrix, String.format("%tM %tS", Calendar.getInstance(), Calendar.getInstance()),
					guiLeft + TIMER_X, guiTop + TIMER_Y, WHITE);

			// Postit
			for (Point p : drawing) {
				fill(matrix, p.x, p.y, p.x + 1, p.y + 1, BLACK);
			}

			// Picture
			Minecraft.getInstance().getTextureManager().bindTexture(GUI_TEXTURE);
			blit(matrix, guiLeft + PICTURE_X, guiTop + PICTURE_Y, PICTURE_U + pictureIndex * PICTURE_WIDTH, PICTURE_V,
					PICTURE_WIDTH, PICTURE_HEIGHT);
			blit(matrix, guiLeft + ARROW_X, guiTop + ARROW_Y, ARROW_U, ARROW_V + (isArrowDown ? ARROW_HEIGHT : 0),
					ARROW_WIDTH, ARROW_HEIGHT);

			// Chest
			blit(matrix, guiLeft + CHEST_X, guiTop + CHEST_Y, CHEST_U + (isChestOpen ? CHEST_WIDTH : 0), CHEST_V,
					CHEST_WIDTH, CHEST_HEIGHT);

			// Key
			if (pictureIndex == 3 && !hasKey)
				blit(matrix, guiLeft + KEY_X, guiTop + KEY_Y, KEY_U, KEY_V, KEY_WIDTH, KEY_HEIGHT);

			if (hasKey)
				blit(matrix, mouseX - KEY_WIDTH / 2, mouseY - KEY_HEIGHT / 2, KEY_U, KEY_V, KEY_WIDTH, KEY_HEIGHT);

		}

		private void mouseClicked(double mouseX, double mouseY, int button) {
			if (button == 0 && isInsidePostit(mouseX, mouseY)) {
				drawing.add(new Point((int) mouseX, (int) mouseY));
			} else if (button == 0 && isInsideArrow(mouseX, mouseY)) {
				pictureIndex = (pictureIndex + 1) % 4;
				isArrowDown = true;
			} else if (pictureIndex == 3 && button == 0
					&& isInside(PICTURE_X, PICTURE_Y, PICTURE_WIDTH, PICTURE_HEIGHT, mouseX, mouseY)) {
				hasKey = !hasKey;
			} else if (hasKey && button == 0 && isInside(CHEST_X, CHEST_Y, CHEST_WIDTH, CHEST_HEIGHT, mouseX, mouseY)) {
				isChestOpen = true;
			}
		}

		public void mouseReleased(double mouseX, double mouseY, int button) {
			isArrowDown = false;
		}

		private boolean isInside(int left, int top, int width, int height, double x, double y) {
			return x > left + guiLeft && x < left + guiLeft + width && y > guiTop + top && y < guiTop + top + height;
		}

		private boolean isInsidePostit(double x, double y) {
			return isInside(POSTIT_X, POSTIT_Y, POSTIT_LENGTH, POSTIT_LENGTH, x, y);
		}

		private boolean isInsideArrow(double x, double y) {
			return isInside(ARROW_X, ARROW_Y, ARROW_WIDTH, ARROW_HEIGHT, x, y);
		}
	}

	private static class Point {
		private int x, y;

		private Point(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof Point) {
				Point other = (Point) obj;
				return other.x == x && other.y == y;
			}
			return false;
		}

		@Override
		public int hashCode() {
			return Objects.hash(x, y);
		}
	}

}
