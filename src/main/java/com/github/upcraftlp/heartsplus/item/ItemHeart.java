package com.github.upcraftlp.heartsplus.item;

import core.upcraftlp.craftdev.api.item.Item;
import com.github.upcraftlp.heartsplus.entity.EntityHeart;
import com.github.upcraftlp.heartsplus.handler.HeartsHandler;
import com.github.upcraftlp.heartsplus.util.EnumHeartType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * @author UpcraftLP
 */
public class ItemHeart extends Item {

    public ItemHeart() {
        super("heart");
        this.setSubItems(EnumHeartType.values().length - 1);
    }

    @Override
    public boolean hasCustomEntity(ItemStack stack) {
        return true;
    }

    @Nullable
    @Override
    public Entity createEntity(World world, Entity location, ItemStack itemstack) {
        return new EntityHeart(itemstack, world, location.posX, location.posY, location.posZ);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        HeartsHandler.addHearts(playerIn, worldIn, EnumHeartType.getTypeFromStack(stack), 1);
        if(!playerIn.isCreative()) stack.shrink(1);
        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }

}
