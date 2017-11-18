package mod.upcraftlp.heartsplus.item;

import core.upcraftlp.craftdev.api.item.Item;
import mod.upcraftlp.heartsplus.entity.EntityHeart;
import mod.upcraftlp.heartsplus.handler.HeartsHandler;
import mod.upcraftlp.heartsplus.util.EnumHeartType;
import mod.upcraftlp.heartsplus.util.HeartProvider;
import mod.upcraftlp.heartsplus.util.IExtraHearts;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Locale;

/**
 * @author UpcraftLP
 */
public class ItemHeart extends Item {

    public final EnumHeartType heartType;

    public ItemHeart(EnumHeartType type) {
        super(type.name().toLowerCase(Locale.ROOT) + "_heart");
        this.heartType = type;
    }

    @Override
    public boolean hasCustomEntity(ItemStack stack) {
        return true;
    }

    @Nullable
    @Override
    public Entity createEntity(World world, Entity location, ItemStack itemstack) {
        return new EntityHeart(world, location.posX, location.posY, location.posZ).setHeartType(this.heartType);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        HeartsHandler.addHearts(playerIn, worldIn, this.heartType, 1);
        if(!playerIn.isCreative()) stack.shrink(1);
        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }

}
