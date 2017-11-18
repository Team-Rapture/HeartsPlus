package mod.upcraftlp.heartsplus.util;

import mod.upcraftlp.heartsplus.Reference;
import mod.upcraftlp.heartsplus.item.ItemHeart;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.Locale;

/**
 * @author UpcraftLP
 */
public enum EnumHeartType {

    RED,
    BLACK,
    WHITE,
    ROTTEN; //TODO rotten type

    private final ResourceLocation texture;

    EnumHeartType() {
        this.texture = new ResourceLocation(Reference.MODID, "textures/items/" + this.name().toLowerCase(Locale.ROOT) + "_heart.png");
    }

    public ResourceLocation getTextureLocation() {
        return this.texture;
    }

    public static EnumHeartType getTypeFromStack(ItemStack stack) {
        return ((ItemHeart) stack.getItem()).heartType;
    }
}
