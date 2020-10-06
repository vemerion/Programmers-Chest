package mod.vemerion.programmerschest.console;

import net.minecraft.entity.player.PlayerEntity;

public class InvalidProgram extends Program {

	public InvalidProgram(String[] args) {
		super(args);
	}

	@Override
	public boolean isClientOnlyProgram() {
		return true;
	}

	@Override
	public void run(Console console, PlayerEntity user) {
		if (args.length != 0 && !args[0].trim().equals(""))
			console.println("err: Invalid command " + args[0] + " (type 'help' for a list of commands)");
	}

}
