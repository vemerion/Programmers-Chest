package mod.vemerion.programmerschest.container;

import mod.vemerion.programmerschest.Main;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;

public class ProgrammersChestContainer extends Container {

	public ProgrammersChestContainer(int id, PlayerInventory playerInv, PacketBuffer buffer) {
		this(id, playerInv);
	}

	public ProgrammersChestContainer(int id, PlayerInventory playerInv) {
		super(Main.PROGRAMMERS_CHEST_CONTAINER_TYPE, id);

		// Player inventory
		for (int y = 0; y < 3; ++y) {
			for (int x = 0; x < 9; ++x) {
				this.addSlot(new Slot(playerInv, x + y * 9 + 9, 46 + x * 18, 128 + y * 18));
			}
		}

		// Player hotbar
		for (int x = 0; x < 9; ++x) {
			this.addSlot(new Slot(playerInv, x, 46 + x * 18, 186));
		}
	}

	// TODO: implement
	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		return true;
	}

	// TODO: implement
	@Override
	public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
		return ItemStack.EMPTY;
	}

}
