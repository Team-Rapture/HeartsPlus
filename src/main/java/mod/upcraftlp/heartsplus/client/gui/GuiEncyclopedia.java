package mod.upcraftlp.swep.client.gui;

import com.google.common.collect.Lists;
import jline.internal.Nullable;
import mod.upcraftlp.swep.Reference;
import mod.upcraftlp.swep.util.ClientUtil;
import mod.upcraftlp.swep.util.EncyclopediaReader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.command.CommandBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraftforge.common.util.Constants;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.io.IOException;
import java.util.List;

/**
 * @author UpcraftLP
 */
public class GuiEncyclopedia extends GuiScreen {

    private static final Minecraft MC = Minecraft.getMinecraft();
    private static final ResourceLocation BOOK_GUI_TEXTURES = new ResourceLocation("textures/gui/book.png");
    private static final ResourceLocation INDEX_PAGE = getPageFor("index");
    private static final int PAGE_WIDTH = 192;
    private static final int PAGE_HEIGHT = 192;
    private static final int ENTRY_HEIGHT = 18;
    private static final int ENTRY_WIDTH = 121;
    private static ResourceLocation last = INDEX_PAGE;

    private static final int TEXT_COLOR = 0x000000; //black
    private static final int TITLE_COLOR = 0xFF0000; //red
    private static final int ENTRY_COLOR = 0xFFFFFF; //white
    private static final float MOUSE_SCROLL_MULTIPLIER = 10.0F;

    private ResourceLocation currentPage;                       //Page order:
    private String title;                                       //title
    private ResourceLocation image;                             //image
    private int[] imageData = new int[2];                       //
    private final List<Entry> entries = Lists.newArrayList();   //list entries
    private boolean hasText;                                    //text
    private String text;                                        //
    private int listY = 0;

    private static ResourceLocation getPageFor(String page) {
        return new ResourceLocation(Reference.MODID, page);
    }

    private NBTTagCompound pageNBT;

    public GuiEncyclopedia(@Nullable ResourceLocation page) {
        if(page == null || page.equals(TextureMap.LOCATION_MISSING_TEXTURE)) { //TODO open last page, only open index if it's the first time opening the book.
            page = INDEX_PAGE;
        }
        this.pageNBT = EncyclopediaReader.readJsonToNbt(page);
        currentPage = page;
    }

    @Override
    public void onGuiClosed() {
        last = this.currentPage;
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void initGui() {
        this.entries.clear();
        if(this.pageNBT.hasKey("elements", Constants.NBT.TAG_LIST)) {
            NBTTagList page = this.pageNBT.getTagList("elements", Constants.NBT.TAG_COMPOUND);
            for(int index = 0; index < page.tagCount(); index++) {
                entries.add(new Entry(page.getCompoundTagAt(index)));
            }
        }
        else {
            this.hasText = pageNBT.getBoolean("text");
            if(pageNBT.hasKey("image", Constants.NBT.TAG_STRING)) {
                this.image = ClientUtil.loadTexture(new ResourceLocation(pageNBT.getString("image")), imageData);
            }
        }
        if(this.pageNBT.getBoolean("displayTitle")) {
            this.title = I18n.format("page." + this.pageNBT.getString("name") + ".title");
        }
        this.addButton(new GuiButton(0, (this.width + PAGE_WIDTH) / 2 - 50, (this.height + PAGE_HEIGHT) / 2 - 30, 70, 20, ""));
        this.text = I18n.format("page." + this.pageNBT.getString("name") + ".text");
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if(button.id == 0) {
            if(this.currentPage == INDEX_PAGE) MC.displayGuiScreen(null); //close screen
            else if(GuiScreen.isShiftKeyDown() || last == null) MC.displayGuiScreen(new GuiEncyclopedia(INDEX_PAGE));
            else MC.displayGuiScreen(new GuiEncyclopedia(last));
        }
        super.actionPerformed(button);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.buttonList.get(0).displayString = this.currentPage == INDEX_PAGE ? TextFormatting.RED + I18n.format("button.close.name") : isShiftKeyDown() || last == null ? TextFormatting.ITALIC.toString() + TextFormatting.AQUA.toString() + I18n.format("button.index.name") : I18n.format("button.back.name");
        MC.getTextureManager().bindTexture(BOOK_GUI_TEXTURES);
        int x = (this.width - PAGE_WIDTH) / 2;
        int y = 2;
        this.drawTexturedModalRect(x, y, 0, 0, PAGE_WIDTH, PAGE_HEIGHT); //background
        if(this.title != null) {
            int fontHeight = fontRenderer.FONT_HEIGHT;
            fontRenderer.FONT_HEIGHT = 10;
            fontRenderer.drawString(this.title, (this.width - fontRenderer.getStringWidth(this.title)) / 2, y + 15, TITLE_COLOR);
            fontRenderer.FONT_HEIGHT = fontHeight;
        }
        //(x,y) = top-left corner of actual page
        x += 32;
        y+= 10;
        if(this.image != null && this.image != TextureMap.LOCATION_MISSING_TEXTURE) {
            y += 20;
            float width = this.pageNBT.getInteger("imageSize");
            if(width <= 0) width = ENTRY_WIDTH;
            float height = imageData[1] * (width / imageData[0]);
            GlStateManager.pushMatrix();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            MC.renderEngine.bindTexture(this.image);
            drawScaledCustomSizeModalRect(x, y, 0, 0, imageData[0], imageData[1], (int) width, (int) height, imageData[0], imageData[1]);
            GlStateManager.popMatrix();
            y+= height + 2;
        }
        if(!this.entries.isEmpty()) {
            for(int index = 0; index < entries.size(); index++) {
                Entry entry = entries.get(index);
                y += ENTRY_HEIGHT + 1;
                entry.drawEntry(index, x, y, ENTRY_WIDTH, ENTRY_HEIGHT, mouseX, mouseY, false, MC.getRenderPartialTicks());
            }
            y +=  2;
        }
        x += 1;
        if(this.hasText) {
            int textWidth = ENTRY_WIDTH - 1;
            int lineHeight = fontRenderer.FONT_HEIGHT + 1;
            List<String> pageStrings = fontRenderer.listFormattedStringToWidth(this.text, textWidth);
            int offsetLines = this.listY / lineHeight;
            int drawIndex = 0;
            for(int index = offsetLines; index < pageStrings.size(); index++) {
                int drawY = y + drawIndex++ * lineHeight;
                if(drawY > PAGE_HEIGHT - 25) break;
                String text = pageStrings.get(index);
                if(!text.isEmpty()) fontRenderer.drawString(text, x, drawY, TEXT_COLOR);
            }
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
        for (Entry entry : entries) {
            if (entry.hoverText != null && entry.isMouseOver(ENTRY_WIDTH, ENTRY_HEIGHT, mouseX, mouseY)) {
                drawHoveringText(entry.hoverText, mouseX, mouseY);
                break;
            }
        }
    }

    @Override
    public void drawDefaultBackground() {
        super.drawDefaultBackground();
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        int scroll = (int) - (Math.signum(Mouse.getEventDWheel()) * MOUSE_SCROLL_MULTIPLIER);
        this.listY = MathHelper.clamp(this.listY + scroll, 0, fontRenderer.getWordWrappedHeight(this.text, ENTRY_WIDTH - 1) - 50);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        int x = (this.width - PAGE_WIDTH) / 2 + 32;
        int y = 32;
        for (int i = 0; i < this.entries.size(); i++) {
            Entry e = this.entries.get(i);
            if(e.isMouseOver(ENTRY_WIDTH, ENTRY_HEIGHT, mouseX, mouseY)) {
                e.mousePressed(i, mouseX, mouseY, mouseButton, mouseX, mouseY);
                break;
            }
        }
    }

    private static class Entry implements GuiListExtended.IGuiListEntry {

        private String caption;
        private ClickEvent clickEvent;
        private final String hoverText;
        private final ItemStack icon;
        private int x, y;

        Entry(NBTTagCompound entryNBT) {
            String elementBaseText = "entry." + entryNBT.getString("name");
            caption = I18n.format( elementBaseText + ".caption");
            if(entryNBT.hasKey("link")) {
                this.clickEvent = new ClickEvent(entryNBT.getBoolean("isLink") ? ClickEvent.Action.OPEN_URL : ClickEvent.Action.CHANGE_PAGE, entryNBT.getString("link"));
            }
            this.hoverText = entryNBT.getBoolean("hover") ? I18n.format(elementBaseText + ".hover") : null;
            if(entryNBT.hasKey("icon")) {
                ItemStack stack = null;
                try {
                    Item item = CommandBase.getItemByText(null, entryNBT.getString("icon"));
                    stack = new ItemStack(item);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                icon = stack != null ? stack : ItemStack.EMPTY;
            }
            else icon = ItemStack.EMPTY;
        }

        boolean isMouseOver(int width, int height, int mouseX, int mouseY) {
            return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
        }

        @Override
        public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected, float partialTicks) {
            this.x = x;
            this.y = y;
            drawRect(x, y - 1, x + listWidth, y, Color.WHITE.getRGB());
            drawRect(x, y, x + listWidth, y + slotHeight, this.isMouseOver(listWidth, slotHeight, mouseX, mouseY) ? Color.BLACK.getRGB() : Color.GRAY.getRGB());
            MC.fontRenderer.drawString(caption, x + 18, y + (slotHeight - MC.fontRenderer.FONT_HEIGHT) / 2, ENTRY_COLOR);
            if(!this.icon.isEmpty()) {
                GlStateManager.pushMatrix();
                RenderHelper.enableGUIStandardItemLighting();
                MC.getRenderItem().renderItemAndEffectIntoGUI(this.icon, x, y + 1);
                GlStateManager.popMatrix();
            }
        }

        @Override
        public boolean mousePressed(int slotIndex, int mouseX, int mouseY, int mouseEvent, int relativeX, int relativeY) {
            MC.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            if(this.clickEvent.getAction() == ClickEvent.Action.OPEN_URL) ClientUtil.openLink(this.clickEvent.getValue());
            else MC.displayGuiScreen(new GuiEncyclopedia(new ResourceLocation(this.clickEvent.getValue())));
            return true;
        }

        @Override
        public void updatePosition(int slotIndex, int x, int y, float partialTicks) {
            //NO-OP
        }

        @Override
        public void mouseReleased(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY) {
            //NO-OP
        }

    }

}
