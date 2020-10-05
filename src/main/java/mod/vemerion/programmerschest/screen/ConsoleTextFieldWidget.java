package mod.vemerion.programmerschest.screen;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.util.text.StringTextComponent;

public class ConsoleTextFieldWidget extends TextFieldWidget {
	private FontRenderer font;

	public ConsoleTextFieldWidget(FontRenderer font, int x, int y, int width, int height) {
		super(font, x, y, width, height, StringTextComponent.EMPTY);
		this.font = font;
	}

	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
		if (super.keyPressed(keyCode, scanCode, modifiers)) {
			if (font.getStringWidth(getText()) > width) {
				setText(getText() + "\n");
			}
		}
		return false;
	}

}
