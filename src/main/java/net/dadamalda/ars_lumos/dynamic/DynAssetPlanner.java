package net.dadamalda.ars_lumos.dynamic;

import com.mojang.logging.LogUtils;
import dev.lukebemish.dynamicassetgenerator.api.PathAwareInputStreamSource;
import dev.lukebemish.dynamicassetgenerator.api.ResourceCache;
import dev.lukebemish.dynamicassetgenerator.api.ResourceGenerationContext;
import dev.lukebemish.dynamicassetgenerator.api.client.AssetResourceCache;
import net.dadamalda.ars_lumos.Ars_lumos;
import net.dadamalda.ars_lumos.Config;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.IoSupplier;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class DynAssetPlanner {
    public static final AssetResourceCache ASSET_CACHE =
            ResourceCache.register(new AssetResourceCache(ResourceLocation.fromNamespaceAndPath(Ars_lumos.MODID, "dynamic")));
    public static final ResourceLocation EMPTY_TEXTURE = ResourceLocation.fromNamespaceAndPath("dynamic_asset_generator", "empty");
    public static final ResourceLocation EMISSIVE_PROPERTIES = ResourceLocation.parse("minecraft:optifine/emissive.properties");

    public static Set<ResourceLocation> ASSETS = new HashSet<>();
    public static Map<ResourceLocation, ResourceLocation> ASSET_MAP = new HashMap<>();

    public static void plan() {
        ASSET_CACHE.planSource(new PathAwareInputStreamSource() {
            @Override
            public @NotNull Set<ResourceLocation> getLocations(ResourceGenerationContext resourceGenerationContext) {
                ASSETS.clear();
                ASSETS.add(EMISSIVE_PROPERTIES);
                ASSET_MAP.clear();

                if(Config.enableTest) {
                    addTexture(ResourceLocation.parse("ars_nouveau:textures/block/blue_archwood_log_e"), true);
                }

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
}
