package mod.vemerion.programmerschest.computer.filesystem;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.items.ItemStackHandler;

public class ItemStackFile implements File {
	
	private ItemStackHandler handler;
	private Folder parent;
	
	public ItemStackFile(Folder parent) {
		this.parent = parent;
		handler = new ItemStackHandler() {
			public int getSlotLimit(int slot) {
				return Integer.MAX_VALUE;
			}
		};
	}

	@Override
	public CompoundNBT save() {
		return handler.serializeNBT();
	}

	@Override
	public void load(CompoundNBT compound) {
		handler.deserializeNBT(compound);
	}

	@Override
	public String getName() {
		return File.toFileName(handler.getStackInSlot(0).getDisplayName().getString());
	}
	
	public int getCount() {
		return handler.getStackInSlot(0).getCount();
	}

	@Override
	public boolean isEmpty() {
		return handler.getStackInSlot(0).isEmpty();
	}

	@Override
	public Folder parent() {
		return parent;
	}

	@Override
	public String path() {
		return parent.path() + "/" + getName();
	}

	@Override
	public ItemStack put(ItemStack stack) {
		return handler.insertItem(0, stack, false);
	}

}
