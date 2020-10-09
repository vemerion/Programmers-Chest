package mod.vemerion.programmerschest.tileentity;

import mod.vemerion.programmerschest.Main;
import mod.vemerion.programmerschest.computer.BasicComputer;
import mod.vemerion.programmerschest.computer.Computer;
import mod.vemerion.programmerschest.container.ProgrammersChestContainer;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

public class ProgrammersChestTileEntity extends TileEntity implements INamedContainerProvider {

	private Computer computer = new BasicComputer();
	
	public ProgrammersChestTileEntity() {
		super(Main.PROGRAMMERS_CHEST_TILE_ENTITY_TYPE);
	}
	
	@Override
	public Container createMenu(int id, PlayerInventory playerInv, PlayerEntity player) {
		return new ProgrammersChestContainer(id, playerInv, getPos());
	}

	@Override
	public ITextComponent getDisplayName() {
		return StringTextComponent.EMPTY;
	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		compound.put("computer", computer.shutdown());
		return super.write(compound);
	}

	@Override
	public void read(BlockState state, CompoundNBT nbt) {
		computer.startup(nbt.getCompound("computer"));
		super.read(state, nbt);
	}
	
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		return Main.COMPUTER.orEmpty(cap, LazyOptional.of(() -> computer));
	}

}
