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
import java.util.Set;

public final class DynAssetPlanner {
    public static final AssetResourceCache ASSET_CACHE =
            ResourceCache.register(new AssetResourceCache(ResourceLocation.fromNamespaceAndPath(Ars_lumos.MODID, "dynamic")));
    public static final ResourceLocation EMPTY_TEXTURE = ResourceLocation.fromNamespaceAndPath("dynamic_asset_generator", "empty");
    public static final ResourceLocation EMISSIVE_PROPERTIES = ResourceLocation.parse("minecraft:optifine/emissive.properties");

    public static void plan() {
        ASSET_CACHE.planSource(new PathAwareInputStreamSource() {
            @Override
            public @NotNull Set<ResourceLocation> getLocations(ResourceGenerationContext resourceGenerationContext) {
                if(Config.enableTest) {
                    return Set.of(
                            EMISSIVE_PROPERTIES,
                            ResourceLocation.parse("ars_nouveau:textures/block/blue_archwood_log_e.png"),
                            ResourceLocation.parse("ars_nouveau:textures/block/blue_archwood_log_e.png.mcmeta")
                    );
                } else {
                    return Set.of(EMISSIVE_PROPERTIES);
                }
            }

            @Override
            @Nullable
            public IoSupplier<InputStream> get(ResourceLocation resourceLocation, ResourceGenerationContext resourceGenerationContext) {
                if(resourceLocation.equals(EMISSIVE_PROPERTIES)) {
                    return () -> new ByteArrayInputStream("suffix.emissive=_e".getBytes(StandardCharsets.UTF_8));
                } else if(resourceLocation.equals(ResourceLocation.parse("ars_nouveau:textures/block/blue_archwood_log_e.png"))) {
                    return resourceGenerationContext.getResourceSource()
                            .getResource(ResourceLocation.parse("ars_lumos:textures/block/blue_archwood_log_e.png"));
                }
                return resourceGenerationContext.getResourceSource()
                        .getResource(ResourceLocation.parse("ars_lumos:textures/block/blue_archwood_log_e.png.mcmeta"));
            }
        });
    }
}
