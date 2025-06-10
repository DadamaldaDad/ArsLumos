package net.dadamalda.ars_lumos.compat;

import alexthw.ars_elemental.registry.ModEntities;
import com.hollingsworth.arsnouveau.client.renderer.entity.WealdWalkerModel;
import com.hollingsworth.arsnouveau.common.entity.WealdWalker;
import com.mojang.logging.LogUtils;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class ArsElementalCompat {
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers evt) {
        LogUtils.getLogger().info("[Ars Lumos] Ars Elemental detected");

        evt.registerEntityRenderer(ModEntities.FLASHING_WEALD_WALKER.get(), ctx -> {
            GeoEntityRenderer<WealdWalker> renderer = new GeoEntityRenderer<>(ctx, new WealdWalkerModel<>("flashing_weald"));

            renderer.addRenderLayer(new AutoGlowingGeoLayer<>(renderer));

            return renderer;
        });
    }
}
