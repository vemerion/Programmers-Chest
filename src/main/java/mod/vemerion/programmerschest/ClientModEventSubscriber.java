package mod.vemerion.programmerschest;

import mod.vemerion.programmerschest.screen.ProgrammersChestScreen;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = Main.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEventSubscriber {
	
	@SubscribeEvent
	public static void onClientSetupEvent(FMLClientSetupEvent event) {
		RenderTypeLookup.setRenderLayer(Main.PROGRAMMERS_CHEST_BLOCK, RenderType.getSolid());
		
		ScreenManager.registerFactory(Main.PROGRAMMERS_CHEST_CONTAINER_TYPE, ProgrammersChestScreen::new);
	}      

}
