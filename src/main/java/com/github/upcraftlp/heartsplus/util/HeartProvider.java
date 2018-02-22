package com.github.upcraftlp.heartsplus.util;

import core.upcraftlp.craftdev.api.net.NetworkHandler;
import com.github.upcraftlp.heartsplus.net.PacketExtraHearts;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author UpcraftLP
 */
public class HeartProvider implements ICapabilitySerializable<NBTBase> {

    @CapabilityInject(IExtraHearts.class)
    public static final Capability<IExtraHearts> HEARTS_CAPABILITY = null;

    private IExtraHearts instance = HEARTS_CAPABILITY.getDefaultInstance();

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == HEARTS_CAPABILITY;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == HEARTS_CAPABILITY ? HEARTS_CAPABILITY.cast(this.instance) : null;
    }

    @Override
    public NBTBase serializeNBT() {
        return HEARTS_CAPABILITY.getStorage().writeNBT(HEARTS_CAPABILITY, this.instance, null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt) {
        HEARTS_CAPABILITY.getStorage().readNBT(HEARTS_CAPABILITY, this.instance, null, nbt);
    }

    public static void sync(EntityPlayer player) {
        if(player instanceof EntityPlayerMP) NetworkHandler.INSTANCE.sendTo(new PacketExtraHearts(player.getCapability(HeartProvider.HEARTS_CAPABILITY, null)), (EntityPlayerMP) player);
    }
}
