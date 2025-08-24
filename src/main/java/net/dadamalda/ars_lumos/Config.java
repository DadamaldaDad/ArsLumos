package net.dadamalda.ars_lumos;

import net.dadamalda.ars_lumos.config.ResourcePack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = Ars_lumos.MODID, bus = EventBusSubscriber.Bus.MOD)
public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.EnumValue<ResourcePack> RESOURCE_PACK = BUILDER.defineEnum("resource_pack", ResourcePack.DETECT);

    private static final ModConfigSpec.BooleanValue ENABLE_TEST = BUILDER.define("enable_test", true);

    public static final ModConfigSpec SPEC = BUILDER.build();

    public static ResourcePack resourcePack;

    public static boolean enableTest;

    @SubscribeEvent
    static void onLoad(ModConfigEvent event) {
        resourcePack = RESOURCE_PACK.get();
        enableTest = ENABLE_TEST.get();
    }
}
