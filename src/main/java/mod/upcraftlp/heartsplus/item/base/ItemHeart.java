package mod.upcraftlp.heartsplus.item.base;

import core.upcraftlp.craftdev.api.item.Item;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * @author UpcraftLP
 */
public abstract class ItemHeart extends Item {

    public ItemHeart(String name) {
        super(name + "_heart");
    }

    @Override
    public boolean hasCustomEntity(ItemStack stack) {
        return true;
    }

    @Nullable
    @Override
    public abstract Entity createEntity(World world, Entity location, ItemStack itemstack);

}
