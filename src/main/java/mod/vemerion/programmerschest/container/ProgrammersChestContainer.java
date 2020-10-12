package mod.vemerion.programmerschest.container;

import mod.vemerion.programmerschest.Main;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

public class ProgrammersChestContainer extends Container {

	private BlockPos pos;

	public ProgrammersChestContainer(int id, PlayerInventory playerInv, PacketBuffer buffer) {
		this(id, playerInv, buffer.readBlockPos());
	}

	public ProgrammersChestContainer(int id, PlayerInventory playerInv, BlockPos pos) {
		super(Main.PROGRAMMERS_CHEST_CONTAINER_TYPE, id);
		this.pos = pos;

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

	@Override
	public void onContainerClosed(PlayerEntity playerIn) {
		if (!playerIn.world.isRemote) {
			TileEntity tileEntity = playerIn.world.getTileEntity(getPos());
			if (tileEntity != null)
				tileEntity.getCapability(Main.COMPUTER).ifPresent(c -> c.logout(playerIn));
		}
		super.onContainerClosed(playerIn);
	}

	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		TileEntity tileEntity = playerIn.world.getTileEntity(pos);
		if (tileEntity == null)
			return false;
		return playerIn.getDistanceSq(Vector3d.copyCentered(pos)) < 64
				&& tileEntity.getCapability(Main.COMPUTER).isPresent();
	}

	@Override
	public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
		ItemStack copy = ItemStack.EMPTY;
		Slot slot = inventorySlots.get(index);
		if (slot != null && slot.getHasStack()) {
			ItemStack stack = slot.getStack();
			copy = stack.copy();
			if (index >= 9 * 3) {
				if (!mergeItemStack(stack, 0, 9 * 3, false)) {
					return ItemStack.EMPTY;
				}
			} else if (index < 9 * 3) {
				if (!mergeItemStack(stack, 9 * 3, 9 * 4, false)) {
					return ItemStack.EMPTY;
				}
			}

			if (stack.isEmpty()) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}

			if (stack.getCount() == copy.getCount()) {
				return ItemStack.EMPTY;
			}

			slot.onTake(playerIn, stack);
		}

		return copy;
	}

	public BlockPos getPos() {
		return pos;
	}

}
