package mod.vemerion.programmerschest.screen;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;

import mod.vemerion.programmerschest.Main;
import mod.vemerion.programmerschest.computer.Console;
import mod.vemerion.programmerschest.computer.Parser;
import mod.vemerion.programmerschest.computer.filesystem.DummyFileSystem;
import mod.vemerion.programmerschest.computer.filesystem.FileSystem;
import mod.vemerion.programmerschest.computer.program.Program;
import mod.vemerion.programmerschest.container.ProgrammersChestContainer;
import mod.vemerion.programmerschest.network.Network;
import mod.vemerion.programmerschest.network.PrintlnMessage;
import mod.vemerion.programmerschest.network.ProgramMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.fonts.TextInputUtil;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
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

	private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(Main.MODID,
			"textures/gui/programmers_chest_screen_big.png");

	private String inputText = "";
	private String path = "home>";
	private List<IReorderingProcessor> output;

	private FileSystem dummy = new DummyFileSystem();

	private TextInputUtil input = new TextInputUtil(() -> inputText, (s) -> inputText = s,
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
		xSize = 256;
		ySize = 210;
		super.init();
		output = new ArrayList<>();
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
	protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int mouseX,
			int mouseY) {
		Minecraft.getInstance().getTextureManager().bindTexture(GUI_TEXTURE);
		int x = (width - xSize) / 2;
		int cursorY = (height - ySize) / 2;
		blit(matrixStack, x, cursorY, 0, 0, xSize, ySize);

		// Console text
		int i = 0;
		int cursorX = guiLeft + CONSOLE_X;
		for (IReorderingProcessor line : output) {
			font.func_238407_a_(matrixStack, line, guiLeft + CONSOLE_X, guiTop + CONSOLE_Y + font.FONT_HEIGHT * i,
					WHITE);
			i++;
		}

		List<IReorderingProcessor> inputLines = font.trimStringToWidth(new StringTextComponent(path + inputText),
				CONSOLE_WIDTH);
		for (IReorderingProcessor line : inputLines) {
			cursorX = font.func_238407_a_(matrixStack, line, guiLeft + CONSOLE_X,
					guiTop + CONSOLE_Y + font.FONT_HEIGHT * i, WHITE);
			i++;
		}

		// Draw cursor
		cursorY = guiTop + CONSOLE_Y + font.FONT_HEIGHT * (i - (inputLines.size() == 0 ? 0 : 1));
		fill(matrixStack, cursorX, cursorY - 1, cursorX + 1, cursorY + font.FONT_HEIGHT, WHITE);
	}

	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		super.render(matrixStack, mouseX, mouseY, partialTicks);

		renderHoveredTooltip(matrixStack, mouseX, mouseY);
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

}
