package com.github.upcraftlp.heartsplus.client.render;

import com.github.upcraftlp.heartsplus.entity.EntityHeart;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;

/**
 * @author UpcraftLP
 */
public class RenderHeart extends Render<EntityHeart> {

    public static final Factory FACTORY = new Factory();

    private static class Factory implements IRenderFactory<EntityHeart> {

        @Override
        public Render<? super EntityHeart> createRenderFor(RenderManager manager) {
            return new RenderHeart(manager);
        }
    }

    private static final ResourceLocation[] TEXTURES = {
            //new ResourceLocation(Reference.MODID, "textures/entity/heart/heart_red_full.png"),
            //new ResourceLocation(Reference.MODID, "textures/entity/heart/heart_black_full.png"),
            //new ResourceLocation(Reference.MODID, "textures/entity/heart/heart_white.png")
    };

    protected RenderHeart(RenderManager renderManager) {
        super(renderManager);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityHeart entity) {
        int index = entity.getType().ordinal();
        if (index < TEXTURES.length) return TEXTURES[index];
        else return TextureMap.LOCATION_MISSING_TEXTURE;
    }


    @Override
    public void doRender(EntityHeart entity, double x, double y, double z, float entityYaw, float partialTicks) {
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
        GlStateManager.pushMatrix();
        GlStateManager.translate(0, 0, 0);
        //GlStateManager.rotate(Minecraft.getMinecraft().player. 0, 1, 0);//TODO make texture always face the player!
        bindEntityTexture(entity);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);

        //TODO render quad always facing the player
        //bufferBuilder.pos().tex();

        tessellator.draw();

        GlStateManager.popMatrix();
    }
}
