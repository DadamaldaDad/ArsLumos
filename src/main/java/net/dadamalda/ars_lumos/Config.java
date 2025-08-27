package net.dadamalda.ars_lumos;

import net.dadamalda.ars_lumos.config.ResourcePack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = Ars_lumos.MODID, bus = EventBusSubscriber.Bus.MOD)
public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.BooleanValue ENABLE_ALL = BUILDER
            .comment("Press F3+T after changing this config")
            .comment("Disabling a category disables everything in that category")
            .define("enable_all", true);

    private static final ModConfigSpec.EnumValue<ResourcePack> RESOURCE_PACK = BUILDER
            .comment("Change this to override the automatic detection")
            .defineEnum("resource_pack", ResourcePack.DETECT);

    private static final ModConfigSpec.BooleanValue ENABLE_ARCHWOOD = BUILDER
            .define("enable_archwood", true);

    private static final ModConfigSpec.BooleanValue ENABLE_LOGS = BUILDER
            .push("archwood")
            .define("enable_logs", true);

    private static final ModConfigSpec.BooleanValue ENABLE_WEALD_WALKERS = BUILDER
            .define("enable_weald_walkers", true);

    private static final ModConfigSpec.BooleanValue ENABLE_WEALD_WADDLERS = BUILDER
            .define("enable_weald_waddlers", true);

    private static final ModConfigSpec.BooleanValue ENABLE_BARK = BUILDER
            .comment("For Ars Nouveau Flavors & Delights")
            .define("enable_bark", true);

    private static final ModConfigSpec.BooleanValue ENABLE_CASCADING = BUILDER
            .define("enable_cascading", true);

    private static final ModConfigSpec.BooleanValue ENABLE_FLOURISHING = BUILDER
            .define("enable_flourishing", true);

    private static final ModConfigSpec.BooleanValue ENABLE_VEXING = BUILDER
            .define("enable_vexing", true);

    private static final ModConfigSpec.BooleanValue ENABLE_BLAZING = BUILDER
            .define("enable_blazing", true);

    private static final ModConfigSpec.BooleanValue ENABLE_FLASHING = BUILDER
            .comment("For Ars Elemental")
            .define("enable_flashing", true);

    private static final ModConfigSpec.BooleanValue ENABLE_WARP_PORTALS = BUILDER
            .pop()
            .comment("Only affects nether-style portals, use a dominion wand to switch")
            .define("enable_warp_portals", true);

    private static final ModConfigSpec.BooleanValue ENABLE_SOURCE_JARS = BUILDER
            .define("enable_source_jars", true);

    public static final ModConfigSpec SPEC = BUILDER
            .build();

    public static ResourcePack resourcePack;

    public static boolean enableAll;

    public static boolean enableArchwood;
    public static boolean enableLogs;
    public static boolean enableWealdWalkers;
    public static boolean enableWealdWaddlers;
    public static boolean enableBark;
    public static boolean enableCascading;
    public static boolean enableFlourishing;
    public static boolean enableVexing;
    public static boolean enableBlazing;
    public static boolean enableFlashing;

    public static boolean enableWarpPortals;

    public static boolean enableSourceJars;

    @SubscribeEvent
    static void onLoad(ModConfigEvent event) {
        resourcePack = RESOURCE_PACK.get();

        enableAll = ENABLE_ALL.get();
        enableArchwood = ENABLE_ARCHWOOD.get();
        enableLogs = ENABLE_LOGS.get();
        enableWealdWalkers = ENABLE_WEALD_WALKERS.get();
        enableWealdWaddlers = ENABLE_WEALD_WADDLERS.get();

        enableBark = ENABLE_BARK.get();
        enableCascading = ENABLE_CASCADING.get();
        enableFlourishing = ENABLE_FLOURISHING.get();
        enableVexing = ENABLE_VEXING.get();
        enableBlazing = ENABLE_BLAZING.get();
        enableFlashing = ENABLE_FLASHING.get();

        enableWarpPortals = ENABLE_WARP_PORTALS.get();

        enableSourceJars = ENABLE_SOURCE_JARS.get();
    }
}
