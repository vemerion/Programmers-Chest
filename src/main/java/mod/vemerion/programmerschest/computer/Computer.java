package mod.vemerion.programmerschest.computer;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;

public interface Computer {
	
	public boolean login(PlayerEntity player);

	public void logout(PlayerEntity player);
	
	public void run(String argString, PlayerEntity user);
	
	public boolean isOccupied();
	
	public CompoundNBT shutdown();
	
	public void startup(CompoundNBT compound);
}
