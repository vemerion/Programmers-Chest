package mod.vemerion.programmerschest.computer.filesystem;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.items.ItemStackHandler;

public class ItemStackFile implements File {
	
	private ItemStackHandler handler;
	private Folder parent;
	
	public ItemStackFile(Folder parent) {
		this.parent = parent;
		handler = new ItemStackHandler();
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
	public String name() {
		return handler.toString();
	}

	@Override
	public boolean isEmpty() {
		return handler.getStackInSlot(0).isEmpty();
	}

	@Override
	public Folder parent() {
		return parent;
	}

}
