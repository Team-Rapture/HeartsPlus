package mod.upcraftlp.heartsplus.client;

import mod.upcraftlp.heartsplus.Reference;
import mod.upcraftlp.heartsplus.util.HeartProvider;
import mod.upcraftlp.heartsplus.util.IExtraHearts;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.awt.*;

/**
 * @author UpcraftLP
 */
@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(modid = Reference.MODID, value = {Side.CLIENT})
public class HeartRenderHandler {

    private static final Minecraft mc = Minecraft.getMinecraft();

    @SubscribeEvent
    public static void onRenderHotbar(RenderGameOverlayEvent.Pre event) {
        if(event.getType() == RenderGameOverlayEvent.ElementType.HEALTH) {
            //TODO render black and white hearts
            mc.mcProfiler.startSection(Reference.MODID + ":hearts");
            EntityPlayer player = mc.player;

            IExtraHearts extraHearts = player.getCapability(HeartProvider.HEARTS_CAPABILITY, null);
            IAttributeInstance attrMaxHealth = player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);
            float healthMax = (float)attrMaxHealth.getAttributeValue();
            float absorb = MathHelper.ceil(player.getAbsorptionAmount());

            float black = extraHearts.getBlackHearts();
            int white = extraHearts.hasWhiteHeart() ? 1 : 0;

            float all = healthMax + absorb + extraHearts.getBlackHearts() + white;

            int healthRows = MathHelper.ceil(all / 2.0F / 10.0F);
            int rowHeight = Math.max(10 - (healthRows - 2), 3);

            int left = event.getResolution().getScaledWidth() / 2 - 91;
            int top = event.getResolution().getScaledHeight() - GuiIngameForge.left_height;


            //TODO replace
            FontRenderer fr = mc.fontRenderer;
            int fontHeight = fr.FONT_HEIGHT;
            fr.FONT_HEIGHT = 10;

            for (int i = MathHelper.ceil(all / 2.0F) - 1; black > 0 || white > 0; --i) {
                int row = MathHelper.ceil((float)(i + 1) / 10.0F) - 1;
                int x = left + i % 10 * 8;
                int y = top - row * rowHeight;

                if(black > 0) {
                    black -= 1;
                    fr.drawStringWithShadow("B", x, y, Color.GRAY.getRGB()); //TODO draw black heart texture
                }
                else {
                    fr.drawStringWithShadow("W", left, y, Color.WHITE.getRGB());  //TODO draw white heart texture
                    white = 0;
                }
            }

            //TODO remove
            fr.FONT_HEIGHT = fontHeight;

            mc.mcProfiler.endSection();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            mc.renderEngine.bindTexture(Gui.ICONS);
        }
    }
}
