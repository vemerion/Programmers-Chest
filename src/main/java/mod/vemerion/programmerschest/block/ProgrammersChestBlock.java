package mod.vemerion.programmerschest.block;

import mod.vemerion.programmerschest.Main;
import mod.vemerion.programmerschest.tileentity.ProgrammersChestTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class ProgrammersChestBlock extends Block {

	public ProgrammersChestBlock(Properties properties) {
		super(properties);
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new ProgrammersChestTileEntity();
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean eventReceived(BlockState state, World worldIn, BlockPos pos, int id, int param) {
		super.eventReceived(state, worldIn, pos, id, param);
		TileEntity tileentity = worldIn.getTileEntity(pos);
		return tileentity == null ? false : tileentity.receiveClientEvent(id, param);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		if (state.getBlock() != newState.getBlock()) {
			TileEntity tileentity = worldIn.getTileEntity(pos);
			if (tileentity != null) {
				tileentity.getCapability(Main.COMPUTER).ifPresent(c -> c.destroy(worldIn, Vector3d.copyCentered(pos)));
			}
			super.onReplaced(state, worldIn, pos, newState, isMoving);
		}
	}  
	
	@Override
	public INamedContainerProvider getContainer(BlockState state, World worldIn, BlockPos pos) {
		TileEntity tileentity = worldIn.getTileEntity(pos);
		return tileentity instanceof INamedContainerProvider ? (INamedContainerProvider) tileentity : null;
	}
	
	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player,
			Hand handIn, BlockRayTraceResult hit) {
		if (!worldIn.isRemote) {
			TileEntity tileentity = worldIn.getTileEntity(pos);
			if (tileentity != null) {
				tileentity.getCapability(Main.COMPUTER).ifPresent(c -> {
					if (!c.isOccupied()) {
						c.login(player);
						INamedContainerProvider containerProvider = getContainer(state, worldIn, pos);
						NetworkHooks.openGui((ServerPlayerEntity) player, containerProvider, pos);
					}
				});
			}				
		}
		return ActionResultType.SUCCESS;
	}  

}
