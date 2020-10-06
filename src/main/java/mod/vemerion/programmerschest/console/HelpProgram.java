package mod.vemerion.programmerschest.console;

import net.minecraft.entity.player.PlayerEntity;

public class HelpProgram extends Program {

	public HelpProgram(String[] args) {
		super(args);
	}

	@Override
	public boolean isClientOnlyProgram() {
		return true;
	}

	@Override
	public void run(Console console, PlayerEntity user) {
		if (args.length > 1 && args[1].equals("help")) {
			console.println("HELP!!");
		} else {
			console.println("List of commands: help, inv, exit");
			console.println("Type [command] help to get help about specific command");
		}
	}

}
