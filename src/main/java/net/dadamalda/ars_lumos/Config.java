package net.dadamalda.ars_lumos;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;

@EventBusSubscriber(modid = Ars_lumos.MODID, bus = EventBusSubscriber.Bus.MOD)
public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.BooleanValue ENABLE_ALL = BUILDER
            .comment("Press F3+T after changing this config")
            .comment("Disabling a category disables everything in that category")
            .define("enable_all", true);

    private static final ModConfigSpec.ConfigValue<List<? extends String>> DISABLED_TEXTURES = BUILDER
            .comment("Advanced feature, use this to specifically disable textures",
                    "Open the mod jar as zip and find paths under assets/ars_lumos/textures/configurable",
                    "Example: \"ars_nouveau:textures/block/blue_archwood_log_2_e\"")
            .defineListAllowEmpty("disabled_textures", List.of(), (final Object obj) -> true);

    private static final ModConfigSpec.BooleanValue DETECT_RESOURCE_PACKS = BUILDER
            .push("resource_packs")
            .comment("Disable this to override resource pack detection, then use the options below")
            .define("detect_resource_packs", true);

    private static final ModConfigSpec.BooleanValue USE_LOAFERS = BUILDER
            .comment("For Ars Loafers, irrelevant if resource pack detection is enabled")
            .define("use_loafers", false);

    private static final ModConfigSpec.BooleanValue USE_REFRESH = BUILDER
            .comment("For Ars Nouveau Refresh, irrelevant if resource pack detection is enabled")
            .define("use_refresh", false);

    private static final ModConfigSpec.BooleanValue ENABLE_ARCHWOOD = BUILDER
            .pop()
            .define("enable_archwood", true);

    private static final ModConfigSpec.BooleanValue ENABLE_LOGS = BUILDER
            .push("archwood")
            .define("enable_logs", true);

    private static final ModConfigSpec.BooleanValue ENABLE_WEALD_WALKERS = BUILDER
            .define("enable_weald_walkers", true);

    private static final ModConfigSpec.BooleanValue ENABLE_WEALD_WADDLERS = BUILDER
            .define("enable_weald_waddlers", true);

    private static final ModConfigSpec.BooleanValue ENABLE_RITUAL_TABLETS = BUILDER
            .define("enable_ritual_tablets", true);

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

    private static final ModConfigSpec.BooleanValue ENABLE_DAWNING = BUILDER
            .comment("For Archwood Good")
            .define("enable_dawning", true);

    private static final ModConfigSpec.BooleanValue ENABLE_BLINDING = BUILDER
            .comment("For Archwood Good")
            .define("enable_blinding", true);

    private static final ModConfigSpec.BooleanValue ENABLE_WARP_PORTALS = BUILDER
            .pop()
            .comment("Only affects nether-style portals, use a dominion wand to switch")
            .define("enable_warp_portals", true);

    private static final ModConfigSpec.BooleanValue ENABLE_SOURCE_JARS = BUILDER
            .define("enable_source_jars", true);

    public static final ModConfigSpec SPEC = BUILDER
            .build();

    public static boolean detectResourcePacks;

    public static boolean useLoafers;
    public static boolean useRefresh;

    public static boolean enableAll;

    public static List<ResourceLocation> disabledTextures;

    public static boolean enableArchwood;
    public static boolean enableLogs;
    public static boolean enableWealdWalkers;
    public static boolean enableWealdWaddlers;
    public static boolean enableRitualTablets;
    public static boolean enableBark;
    public static boolean enableCascading;
    public static boolean enableFlourishing;
    public static boolean enableVexing;
    public static boolean enableBlazing;
    public static boolean enableFlashing;
    public static boolean enableDawning;
    public static boolean enableBlinding;

    public static boolean enableWarpPortals;

    public static boolean enableSourceJars;

    @SubscribeEvent
    static void onLoad(ModConfigEvent event) {
        detectResourcePacks = DETECT_RESOURCE_PACKS.get();

        useLoafers = USE_LOAFERS.get();
        useRefresh = USE_REFRESH.get();

        enableAll = ENABLE_ALL.get();

        disabledTextures = DISABLED_TEXTURES.get().stream().map(ResourceLocation::parse).toList();

        enableArchwood = ENABLE_ARCHWOOD.get();
        enableLogs = ENABLE_LOGS.get();
        enableWealdWalkers = ENABLE_WEALD_WALKERS.get();
        enableWealdWaddlers = ENABLE_WEALD_WADDLERS.get();
        enableRitualTablets = ENABLE_RITUAL_TABLETS.get();
        enableBark = ENABLE_BARK.get();

        enableCascading = ENABLE_CASCADING.get();
        enableFlourishing = ENABLE_FLOURISHING.get();
        enableVexing = ENABLE_VEXING.get();
        enableBlazing = ENABLE_BLAZING.get();
        enableFlashing = ENABLE_FLASHING.get();
        enableDawning = ENABLE_DAWNING.get();
        enableBlinding = ENABLE_BLINDING.get();

        enableWarpPortals = ENABLE_WARP_PORTALS.get();

        enableSourceJars = ENABLE_SOURCE_JARS.get();
    }
}
