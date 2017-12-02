package mod.upcraftlp.heartsplus.client.render;

import mod.upcraftlp.heartsplus.Reference;
import mod.upcraftlp.heartsplus.entity.EntityHeart;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

/**
 * @author UpcraftLP
 */
public class RenderHeart extends Render<EntityHeart> {

    public static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/overlay/hearts.png");

    protected RenderHeart(RenderManager renderManager) {
        super(renderManager);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityHeart entity) {
        //return entity.getType().getTextureLocation(); //TODO re-add texture
        return TEXTURE;
    }
}
