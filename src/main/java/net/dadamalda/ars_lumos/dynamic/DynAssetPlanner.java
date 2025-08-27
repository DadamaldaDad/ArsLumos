package net.dadamalda.ars_lumos.dynamic;

import dev.lukebemish.dynamicassetgenerator.api.PathAwareInputStreamSource;
import dev.lukebemish.dynamicassetgenerator.api.ResourceCache;
import dev.lukebemish.dynamicassetgenerator.api.ResourceGenerationContext;
import dev.lukebemish.dynamicassetgenerator.api.client.AssetResourceCache;
import net.dadamalda.ars_lumos.Ars_lumos;
import net.dadamalda.ars_lumos.Config;
import net.dadamalda.ars_lumos.config.ResourcePack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.IoSupplier;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

public final class DynAssetPlanner {
    public static final AssetResourceCache ASSET_CACHE =
            ResourceCache.register(new AssetResourceCache(ResourceLocation.fromNamespaceAndPath(Ars_lumos.MODID, "dynamic")));
    public static final ResourceLocation EMPTY_TEXTURE =
            ResourceLocation.fromNamespaceAndPath("dynamic_asset_generator", "textures/empty.png");
    public static final ResourceLocation EMISSIVE_PROPERTIES = ResourceLocation.parse("minecraft:optifine/emissive.properties");

    public static Set<ResourceLocation> ASSETS = new HashSet<>();
    public static Map<ResourceLocation, ResourceLocation> ASSET_MAP = new HashMap<>();
    public static String resourcePack = "default";

    public static void plan() {
        ASSET_CACHE.planSource(new PathAwareInputStreamSource() {
            @Override
            public @NotNull Set<ResourceLocation> getLocations(ResourceGenerationContext resourceGenerationContext) {
                resourcePack = "default";
                if(Config.resourcePack == ResourcePack.DETECT) {
                    try {
                        InputStream footprint = Objects.requireNonNull(resourceGenerationContext.getResourceSource().getResource(
                                ResourceLocation.parse("ars_nouveau:textures/block/blue_archwood_log.png")
                        )).get();
                        InputStream loafersFootprint = Objects.requireNonNull(resourceGenerationContext.getResourceSource().getResource(
                                ResourceLocation.fromNamespaceAndPath(Ars_lumos.MODID, "textures/configurable/loafers/footprint.png")
                        )).get();

                        if (IOUtils.contentEquals(footprint, loafersFootprint)) {
                            resourcePack = "loafers";
                            Ars_lumos.LOGGER.info("Ars Loafers detected, if this is wrong, change the config");
                        } else {
                            Ars_lumos.LOGGER.info("No supported resource pack detected, if this is wrong, change the config");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    resourcePack = Config.resourcePack.getVariantId();
                }

                ASSETS.clear();
                ASSETS.add(EMISSIVE_PROPERTIES);
                ASSET_MAP.clear();

                addWealdWalker("cascading", Config.enableCascading);
                addWealdWalker("flourishing", Config.enableFlourishing);
                addWealdWalker("vexing", Config.enableVexing);
                addWealdWalker("blazing", Config.enableBlazing);
                addWealdWalker("flashing", Config.enableFlashing);

                if(!Config.enableAll) return ASSETS;

                if(Config.enableCascading) addArchwood(ResourceLocation.parse("ars_nouveau:blue"), "cascading");
                if(Config.enableFlourishing) addArchwood(ResourceLocation.parse("ars_nouveau:green"), "flourishing");
                if(Config.enableVexing) addArchwood(ResourceLocation.parse("ars_nouveau:purple"), "vexing");
                if(Config.enableBlazing) addArchwood(ResourceLocation.parse("ars_nouveau:red"), "blazing");
                if(Config.enableFlashing) {
                    addArchwood(ResourceLocation.parse("ars_elemental:yellow"), "flashing", false);
                    addTexture(ResourceLocation.parse("ars_elemental:textures/block/yellow_archwood_log_top_e"),
                            resourcePack, !resourcePack.equals("loafers"));
                }

                if(Config.enableWarpPortals) addTexture(ResourceLocation.parse("ars_nouveau:textures/block/warp_portal_e"), true);
                if(Config.enableSourceJars) addTexture(
                        ResourceLocation.parse("ars_nouveau:textures/block/mana_still_e"), resourcePack, true);

                return ASSETS;
            }

            @Override
            @Nullable
            public IoSupplier<InputStream> get(ResourceLocation resourceLocation, ResourceGenerationContext resourceGenerationContext) {
                if(resourceLocation.equals(EMISSIVE_PROPERTIES)) {
                    return () -> new ByteArrayInputStream("suffix.emissive=_e".getBytes(StandardCharsets.UTF_8));
                } else {
                    return resourceGenerationContext.getResourceSource().getResource(ASSET_MAP.getOrDefault(resourceLocation, EMPTY_TEXTURE));
                }
            }
        });
    }

    private static void addAsset(ResourceLocation output, ResourceLocation input) {
        ASSETS.add(output);
        ASSET_MAP.put(output, input);
    }

    private static void addTexture(ResourceLocation location, String variant, boolean hasMcmeta) {
        ResourceLocation input = ResourceLocation.fromNamespaceAndPath(Ars_lumos.MODID,
                "textures/configurable/"+variant+"/"+location.getNamespace()+"/"+location.getPath());

        addAsset(ResourceLocation.fromNamespaceAndPath(location.getNamespace(), location.getPath()+".png"),
                ResourceLocation.fromNamespaceAndPath(input.getNamespace(), input.getPath()+".png"));
        if(hasMcmeta) {
            addAsset(ResourceLocation.fromNamespaceAndPath(location.getNamespace(), location.getPath()+".png.mcmeta"),
                    ResourceLocation.fromNamespaceAndPath(input.getNamespace(), input.getPath()+".png.mcmeta"));
        }
    }

    private static void addTexture(ResourceLocation location, String variant) {
        addTexture(location, variant, false);
    }

    private static void addTexture(ResourceLocation location) {
        addTexture(location, "default", false);
    }

    private static void addTexture(ResourceLocation location, boolean hasMcmeta) {
        addTexture(location, "default", hasMcmeta);
    }

    private static void addArchwood(ResourceLocation typeColor, String typeName, boolean hasBark) {
        if(Config.enableLogs) {
            for (String permutation : List.of("", "_1", "_2", "_3")) {
                addTexture(typeColor.withPath("textures/block/" + typeColor.getPath() + "_archwood_log"+permutation+"_e"),
                        resourcePack, !resourcePack.equals("loafers"));
            }
        }

        if(Config.enableBark && hasBark) {
            addTexture(ResourceLocation.fromNamespaceAndPath(
                    "arsdelight", "textures/item/ingredient/"+typeName+"_bark_e"));
        }
    }

    private static void addArchwood(ResourceLocation typeColor, String typeName) {
        addArchwood(typeColor, typeName, true);
    }

    private static void addWealdWalker(String typeName, boolean typeEnabled) {
        addTexture(ResourceLocation.fromNamespaceAndPath(
                "ars_nouveau", "textures/entity/" + typeName + "_weald_walker_glowmask"),
                Config.enableAll && Config.enableWealdWalkers && typeEnabled ? "default" : "disabled");

        addTexture(ResourceLocation.fromNamespaceAndPath(
                        "ars_nouveau", "textures/entity/" + typeName + "_weald_waddler_glowmask"),
                Config.enableAll && Config.enableWealdWaddlers && typeEnabled ? "default" : "disabled");
    }
}
