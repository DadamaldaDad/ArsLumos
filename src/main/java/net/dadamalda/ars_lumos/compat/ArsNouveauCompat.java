package net.dadamalda.ars_lumos.compat;

import com.hollingsworth.arsnouveau.client.renderer.entity.WealdWalkerModel;
import com.hollingsworth.arsnouveau.client.renderer.item.GenericItemBlockRenderer;
import com.hollingsworth.arsnouveau.client.renderer.tile.ArcaneCoreRenderer;
import com.hollingsworth.arsnouveau.client.renderer.tile.BasicTurretRenderer;
import com.hollingsworth.arsnouveau.common.block.BasicSpellTurret;
import com.hollingsworth.arsnouveau.common.block.tile.BasicSpellTurretTile;
import com.hollingsworth.arsnouveau.common.entity.WealdWalker;
import com.hollingsworth.arsnouveau.common.items.RendererBlockItem;
import com.hollingsworth.arsnouveau.setup.registry.BlockRegistry;
import com.hollingsworth.arsnouveau.setup.registry.ItemsRegistry;
import com.hollingsworth.arsnouveau.setup.registry.ModEntities;
import net.dadamalda.ars_lumos.Ars_lumos;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

import java.util.function.Supplier;

public class ArsNouveauCompat {

    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers evt) {
        Ars_lumos.LOGGER.info("Ars Nouveau detected");

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

        evt.registerBlockEntityRenderer(BlockRegistry.BASIC_SPELL_TURRET_TILE.get(), ctx -> {
            RendererBlockItem renderer = new RendererBlockItem(BlockRegistry.BASIC_SPELL_TURRET, ItemsRegistry.defaultItemProperties()) {
                @Override
                public Supplier<BlockEntityWithoutLevelRenderer> getRenderer() {
                    return () -> {
                        GenericItemBlockRenderer renderer = BasicTurretRenderer.getISTER();

                        renderer.addRenderLayer(new AutoGlowingGeoLayer<>(renderer));

                        return renderer;
                    };
                }
            };
            
            return renderer;
        });
    }
}
