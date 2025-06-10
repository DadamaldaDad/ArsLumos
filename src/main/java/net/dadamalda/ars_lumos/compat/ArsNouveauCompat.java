package net.dadamalda.ars_lumos.compat;

import com.hollingsworth.arsnouveau.client.renderer.entity.WealdWalkerModel;
import com.hollingsworth.arsnouveau.common.entity.WealdWalker;
import com.hollingsworth.arsnouveau.setup.registry.ModEntities;
import com.mojang.logging.LogUtils;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class ArsNouveauCompat {

    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers evt) {
        LogUtils.getLogger().info("[Ars Lumos] Ars Nouveau detected");

        evt.registerEntityRenderer(ModEntities.ENTITY_BLAZING_WEALD.get(), ctx -> {
            GeoEntityRenderer<WealdWalker> renderer = new GeoEntityRenderer<>(ctx, new WealdWalkerModel<>("blazing_weald"));

            renderer.addRenderLayer(new AutoGlowingGeoLayer<>(renderer));

            return renderer;
        });

        evt.registerEntityRenderer(ModEntities.ENTITY_CASCADING_WEALD.get(), ctx -> {
            GeoEntityRenderer<WealdWalker> renderer = new GeoEntityRenderer<>(ctx, new WealdWalkerModel<>("cascading_weald"));

            renderer.addRenderLayer(new AutoGlowingGeoLayer<>(renderer));

            return renderer;
        });

        evt.registerEntityRenderer(ModEntities.ENTITY_FLOURISHING_WEALD.get(), ctx -> {
            GeoEntityRenderer<WealdWalker> renderer = new GeoEntityRenderer<>(ctx, new WealdWalkerModel<>("flourishing_weald"));

            renderer.addRenderLayer(new AutoGlowingGeoLayer<>(renderer));

            return renderer;
        });

        evt.registerEntityRenderer(ModEntities.ENTITY_VEXING_WEALD.get(), ctx -> {
            GeoEntityRenderer<WealdWalker> renderer = new GeoEntityRenderer<>(ctx, new WealdWalkerModel<>("vexing_weald"));

            renderer.addRenderLayer(new AutoGlowingGeoLayer<>(renderer));

            return renderer;
        });
    }
}
