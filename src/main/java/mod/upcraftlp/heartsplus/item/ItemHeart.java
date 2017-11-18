package mod.upcraftlp.heartsplus.item.base;

import core.upcraftlp.craftdev.api.item.Item;
import mod.upcraftlp.heartsplus.util.EnumHeartType;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Locale;

/**
 * @author UpcraftLP
 */
public abstract class ItemHeart extends Item {

    public ItemHeart(EnumHeartType type) {
        super(type.name().toLowerCase(Locale.ROOT) + "_heart");
    }

    @Override
    public boolean hasCustomEntity(ItemStack stack) {
        return true;
    }

    @Nullable
    @Override
    public abstract Entity createEntity(World world, Entity location, ItemStack itemstack);

}
