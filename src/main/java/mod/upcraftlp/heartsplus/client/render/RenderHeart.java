package mod.upcraftlp.heartsplus.client.render;

import mod.upcraftlp.heartsplus.entity.EntityHeart;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

/**
 * @author UpcraftLP
 */
public class RenderHeart extends Render<EntityHeart> {

    protected RenderHeart(RenderManager renderManager) {
        super(renderManager);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityHeart entity) {
        return entity.getType().getTextureLocation();
    }
}
