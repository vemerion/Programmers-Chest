package mod.vemerion.programmerschest.network;

import java.util.function.Supplier;

import mod.vemerion.programmerschest.computer.Console;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

public class PrintlnMessage {
	private String line;

	public PrintlnMessage(String line) {
		this.line = line;
	}

	public static void encode(final PrintlnMessage msg, final PacketBuffer buffer) {
		buffer.writeString(msg.line);

	}

	public static PrintlnMessage decode(final PacketBuffer buffer) {
		return new PrintlnMessage(buffer.readString(32767));
	}

	public static void handle(final PrintlnMessage msg, final Supplier<NetworkEvent.Context> supplier) {
		final NetworkEvent.Context context = supplier.get();
		context.setPacketHandled(true);
		context.enqueueWork(() -> DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> Println.println(msg.line)));
	}

	private static class Println {
		private static DistExecutor.SafeRunnable println(String line) {
			return new DistExecutor.SafeRunnable() {
				private static final long serialVersionUID = 1L;

				@Override
				public void run() {
					Minecraft mc = Minecraft.getInstance();
					if (mc != null && mc.player != null && mc.currentScreen != null
							&& mc.currentScreen instanceof Console)
						((Console) mc.currentScreen).println(line, mc.player);
				}
			};
		}
	}
}
