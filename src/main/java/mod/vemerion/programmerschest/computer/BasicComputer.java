package mod.vemerion.programmerschest.computer;

import java.util.List;

import mod.vemerion.programmerschest.computer.filesystem.FileSystem;
import mod.vemerion.programmerschest.computer.filesystem.FileSystemException;
import mod.vemerion.programmerschest.computer.filesystem.TreeFileSystem;
import mod.vemerion.programmerschest.computer.program.Program;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

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

	@Override
	public void destroy(World world, Vector3d position) {
		try {
			List<ItemStack> items = fileSystem.get(".*", Integer.MAX_VALUE);
			for (int i = 0; i < Math.min(300, items.size()); i++) { // Cap at 300 to avoid massive lag
				ItemEntity drop = new ItemEntity(world, position.x, position.y, position.z, items.get(i));
				world.addEntity(drop);
			}
		} catch (FileSystemException e) { // This should not happen, but if it does, then we simply do nothing
			
		}
	}

}
