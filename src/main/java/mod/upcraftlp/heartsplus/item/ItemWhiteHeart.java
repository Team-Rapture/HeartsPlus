package mod.upcraftlp.heartsplus.item;

import mod.upcraftlp.heartsplus.item.base.ItemHeart;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * @author UpcraftLP
 */
public class ItemWhiteHeart extends ItemHeart {


    public ItemWhiteHeart() {
        super("white");
    }

    @Nullable
    @Override
    public Entity createEntity(World world, Entity location, ItemStack itemstack) {
        return null;
    }
}
