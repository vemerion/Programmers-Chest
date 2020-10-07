package mod.vemerion.programmerschest.computer;

import mod.vemerion.programmerschest.network.Network;
import mod.vemerion.programmerschest.network.PrintlnMessage;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.fml.network.PacketDistributor;

public class ServerConsole implements Console {

	@Override
	public void println(String s, PlayerEntity user) {
		Network.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) user),
				new PrintlnMessage(s));
	}

	@Override
	public void close() {
	}

}
