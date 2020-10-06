package mod.vemerion.programmerschest.console;

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
	public void run(Console console, PlayerEntity user) {
		if (args.length > 1 && args[1].equals("help")) {
			console.println("Prints out the content of the player inventory.");
		} else {
			StringBuilder sb = new StringBuilder();
			for (ItemStack stack : user.inventory.mainInventory) {
				if (!stack.isEmpty())
					sb.append(stack.getCount() + ":" + stack.getItem().toString() + "    ");
			}
			console.println(sb.toString());
		}
	}

}
