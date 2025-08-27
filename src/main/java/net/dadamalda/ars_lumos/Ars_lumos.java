package net.dadamalda.ars_lumos;

import net.dadamalda.ars_lumos.compat.ArsElementalCompat;
import net.dadamalda.ars_lumos.compat.ArsNouveauCompat;
import net.dadamalda.ars_lumos.dynamic.DynAssetPlanner;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(Ars_lumos.MODID)
public class Ars_lumos {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "ars_lumos";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogManager.getLogger("ArsLumos");

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public Ars_lumos(IEventBus modEventBus, ModContainer modContainer) {
        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (Ars_lumos) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        // NeoForge.EVENT_BUS.register(this);

        modContainer.registerConfig(ModConfig.Type.CLIENT, Config.SPEC);

        DynAssetPlanner.plan();
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers evt) {
            LOGGER.info("Registering renderers");

            if(ModList.get().isLoaded("ars_nouveau")) {
                ArsNouveauCompat.registerRenderers(evt);
            }
            if(ModList.get().isLoaded("ars_elemental")) {
                ArsElementalCompat.registerRenderers(evt);
            }
        }
    }
}
