package mod.vemerion.programmerschest.computer.program;


import mod.vemerion.programmerschest.computer.Console;
import mod.vemerion.programmerschest.computer.filesystem.File;
import mod.vemerion.programmerschest.computer.filesystem.FileSystem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class InvProgram extends Program {

	public InvProgram(String[] args) {
		super(args);
	}

	@Override
	public boolean isClientOnlyProgram() {
		return true;
	}

	@Override
	public void run(Console console, FileSystem fileSystem, PlayerEntity user) {
		if (needHelp()) {
			console.println("Prints out the content of the player inventory.", user);
		} else {
			StringBuilder sb = new StringBuilder();
			for (ItemStack stack : user.inventory.mainInventory) {
				if (!stack.isEmpty())
					sb.append(stack.getCount() + ":" + File.toFileName(stack.getDisplayName().getString()) + "    ");
			}
			console.println(sb.toString(), user);
		}
	}

}
