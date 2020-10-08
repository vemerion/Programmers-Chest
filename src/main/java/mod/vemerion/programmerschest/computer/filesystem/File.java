package mod.vemerion.programmerschest.computer.filesystem;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public interface File {
	public CompoundNBT save();

	public void load(CompoundNBT compound);
	
	public String getName();
	
	public boolean isEmpty();
	
	public Folder parent();
	
	public String path();
	
	public ItemStack put(ItemStack stack);
	
	public static String toFileName(String name) {
		return name.replace(" ", "_");
	}
}
