package com.github.upcraftlp.heartsplus.client;

import com.github.upcraftlp.heartsplus.Reference;
import com.github.upcraftlp.heartsplus.util.HeartProvider;
import com.github.upcraftlp.heartsplus.util.IExtraHearts;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author UpcraftLP
 */
@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(modid = Reference.MODID, value = {Side.CLIENT})
public class HeartRenderHandler {

    private static final Minecraft mc = Minecraft.getMinecraft();
    private static final ResourceLocation HEARTS_GUI_TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/overlay/hearts.png");

    @SubscribeEvent
    public static void onRenderHotbar(RenderGameOverlayEvent.Pre event) {
        if(event.getType() == RenderGameOverlayEvent.ElementType.HEALTH) {
            EntityPlayer player = mc.player;

            IExtraHearts extraHearts = player.getCapability(HeartProvider.HEARTS_CAPABILITY, null);
            float black = extraHearts.getBlackHearts();
            int white = extraHearts.hasWhiteHeart() ? 1 : 0;
            float all = player.getMaxHealth() + MathHelper.ceil(player.getAbsorptionAmount()) + extraHearts.getBlackHearts() + white;

            int healthRows = MathHelper.ceil(all / 20.0F);
            int rowHeight = Math.max(10 - (healthRows - 2), 3);

            int left = event.getResolution().getScaledWidth() / 2 - 91;
            int top = event.getResolution().getScaledHeight() - GuiIngameForge.left_height;
            mc.mcProfiler.startSection(Reference.MODID + ":hearts");
            GlStateManager.enableBlend();
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
            GlStateManager.enableBlend();
            mc.mcProfiler.endSection();

            //cleanup
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            mc.renderEngine.bindTexture(Gui.ICONS);
        }
    }
}
