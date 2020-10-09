package mod.vemerion.programmerschest.computer;

import mod.vemerion.programmerschest.computer.filesystem.FileSystem;
import mod.vemerion.programmerschest.computer.filesystem.FileSystemException;
import mod.vemerion.programmerschest.computer.filesystem.TreeFileSystem;
import mod.vemerion.programmerschest.computer.program.Program;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;

public class BasicComputer implements Computer {

	private FileSystem fileSystem = new TreeFileSystem();
	private Console console = new ServerConsole();
	private PlayerEntity user;

	@Override
	public void run(String argumentString, PlayerEntity player) {
		if (player == user) {
			Parser parser = new Parser();
			Program program = parser.parse(argumentString);
			if (!program.isClientOnlyProgram())
				program.run(console, fileSystem, player);
		}
	}

	@Override
	public void logout(PlayerEntity player) {
		if (player == user) {
			user = null;
			try {
				fileSystem.cd("");
			} catch (FileSystemException e) { // Should never happen, and even if it does, it does not matter
			}
		}
	}

	@Override
	public boolean login(PlayerEntity player) {
		if (user == null) {
			user = player;
			return true;
		}
		return false;
	}

	@Override
	public boolean isOccupied() {
		return user != null;
	}

	@Override
	public CompoundNBT shutdown() {
		return fileSystem.save();
	}

	@Override
	public void startup(CompoundNBT compound) {
		fileSystem.load(compound);
	}

}
