package mod.vemerion.programmerschest.computer.program;

import mod.vemerion.programmerschest.computer.Console;
import mod.vemerion.programmerschest.computer.filesystem.FileSystem;
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
	public void run(Console console, FileSystem fileSystem, PlayerEntity user) {
		if (needHelp()) {
			console.println("HELP!!", user);
		} else {
			console.println("List of commands: help, inv, exit, cd, get, ls, mkdir, put, rmdir", user);
			console.println("Type [command] help to get help about specific command", user);
		}
	}

}
