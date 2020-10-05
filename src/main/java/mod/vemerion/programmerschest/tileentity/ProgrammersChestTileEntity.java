package mod.vemerion.programmerschest.tileentity;

import mod.vemerion.programmerschest.Main;
import mod.vemerion.programmerschest.container.ProgrammersChestContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class ProgrammersChestTileEntity extends TileEntity implements INamedContainerProvider {

	public ProgrammersChestTileEntity() {
		super(Main.PROGRAMMERS_CHEST_TILE_ENTITY_TYPE);
	}

	@Override
	public Container createMenu(int id, PlayerInventory playerInv, PlayerEntity player) {
		return new ProgrammersChestContainer(id, playerInv);
	}

	@Override
	public ITextComponent getDisplayName() {
		return StringTextComponent.EMPTY;
	}

}
