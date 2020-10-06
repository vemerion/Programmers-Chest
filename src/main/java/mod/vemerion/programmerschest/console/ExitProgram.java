package mod.vemerion.programmerschest.console;

import net.minecraft.entity.player.PlayerEntity;

public class ExitProgram extends Program {

	public ExitProgram(String[] args) {
		super(args);
	}

	@Override
	public boolean isClientOnlyProgram() {
		return true;
	}

	@Override
	public void run(Console console, PlayerEntity user) {
		if (args.length > 1 && args[1].equals("help")) {
			console.println("Exits the chest");
		} else {
			console.close();
		}
	}

}
