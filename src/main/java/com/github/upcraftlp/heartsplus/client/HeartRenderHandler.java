package com.github.upcraftlp.heartsplus.client;

import com.github.upcraftlp.heartsplus.ModConfig;
import com.github.upcraftlp.heartsplus.Reference;
import com.github.upcraftlp.heartsplus.util.HeartProvider;
import com.github.upcraftlp.heartsplus.util.IExtraHearts;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
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
    private static final ResourceLocation HEARTS_GUI_TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/overlay/hearts.png");

    @SubscribeEvent
    public static void onRenderHealth(RenderGameOverlayEvent.Pre event) {
        if(event.getType() == RenderGameOverlayEvent.ElementType.HEALTH) {
            mc.mcProfiler.startSection(Reference.MODID + ":hearts");

            EntityPlayer player = mc.player;

            IExtraHearts extraHearts = player.getCapability(HeartProvider.HEARTS_CAPABILITY, null);
            float black = extraHearts.getBlackHearts();

            int left = event.getResolution().getScaledWidth() / 2 - 91;
            int top = event.getResolution().getScaledHeight() - GuiIngameForge.left_height;


            int vanilla = MathHelper.ceil(player.getMaxHealth()) + MathHelper.ceil(player.getAbsorptionAmount());

            int vanillaRows = vanilla / 20;
            int vanillaleft = MathHelper.ceil(vanilla % 20 / 2.0F);
            int rowHeight = Math.max(10 - (vanillaRows - 1), 3);

            int x = left + vanillaleft * 8;
            int y = top - vanillaRows * rowHeight;

            GlStateManager.enableBlend();
            GlStateManager.translate(0, 0, -100);

            if(extraHearts.hasWhiteHeart()) {
                mc.renderEngine.bindTexture(Gui.ICONS);
                Gui.drawModalRectWithCustomSizedTexture(x, y, 16, 9 * (mc.world.getWorldInfo().isHardcoreModeEnabled() ? 5 : 0), 9, 9, 256, 256);
                mc.renderEngine.bindTexture(HEARTS_GUI_TEXTURE);
                Gui.drawModalRectWithCustomSizedTexture(x + 1, y + 1, 7, 0, 4, 7, 16, 16); //render 0.5 white hearts
                x += 8;
                if(vanillaleft + 1 == 10) y-= rowHeight;
            }


            if(ModConfig.reducedRender) {
                y -= 10;
                x = left;
                if (black > 0) {

                    mc.renderEngine.bindTexture(Gui.ICONS);
                    Gui.drawModalRectWithCustomSizedTexture(x, y, 16, 9 * (mc.world.getWorldInfo().isHardcoreModeEnabled() ? 5 : 0), 9, 9, 256, 256);
                    mc.renderEngine.bindTexture(HEARTS_GUI_TEXTURE);
                    Gui.drawModalRectWithCustomSizedTexture(x + 1, y + 1, 0, 7, 7, 7, 16, 16);
                    String blackHearts = black > 99 ? "99+" : String.format("%.1f", black);
                    mc.fontRenderer.drawStringWithShadow(I18n.format("gui.heartsplus.blackHeartCount", blackHearts), x + 10, y + (10 - mc.fontRenderer.FONT_HEIGHT) / 2, Color.WHITE.getRGB());
                }

            }
            else {
                int currentIndex = vanillaleft;
                if(extraHearts.hasWhiteHeart()) {
                    currentIndex += 1;
                    currentIndex %= 10.0F;
                }
                while(black > 0) {
                    mc.renderEngine.bindTexture(Gui.ICONS);
                    Gui.drawModalRectWithCustomSizedTexture(x, y, 16, (mc.world.getWorldInfo().isHardcoreModeEnabled() ? 45 : 0), 9, 9, 256, 256);
                    mc.renderEngine.bindTexture(HEARTS_GUI_TEXTURE);
                    if(black < 1) {
                        if(black >= 0.5F) {
                            Gui.drawModalRectWithCustomSizedTexture(x + 1, y + 1, 0, 7, 4, 7, 16, 16);
                        }
                        break;
                    }

                    Gui.drawModalRectWithCustomSizedTexture(x + 1, y + 1, 0, 7, 7, 7, 16, 16);

                    x += 8;
                    if((currentIndex + 1) % 10 == 0) {
                        x = left;
                        y -= rowHeight;
                    }
                    currentIndex++;
                    black -= 1.0F;
                }

            }

            GlStateManager.translate(0, 0, 100);

            //cleanup
            mc.mcProfiler.endSection();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            mc.renderEngine.bindTexture(Gui.ICONS);





            /*
            for (int i = MathHelper.ceil(all / 2.0F) - 1; black > 0 || white > 0; --i) {
                int row = MathHelper.ceil((float)(i + 1) / 10.0F) - 1;
                int x = left + i % 10 * 8;
                int y = top - row * rowHeight;
                mc.renderEngine.bindTexture(Gui.ICONS);
                Gui.drawModalRectWithCustomSizedTexture(x, y, 16, 9 * (mc.world.getWorldInfo().isHardcoreModeEnabled() ? 5 : 0), 9, 9, 256, 256);
                mc.renderEngine.bindTexture(HEARTS_GUI_TEXTURE);
                if(black > 0) {
                    black -= 1;
                    Gui.drawModalRectWithCustomSizedTexture(x + 1, y + 1, 0, 7, 7, 7, 16, 16);
                }
                else {
                    Gui.drawModalRectWithCustomSizedTexture(x + 1, y + 1, 7, 0, 4, 7, 16, 16);
                    break;
                }
            }

            */

        }
    }
}
