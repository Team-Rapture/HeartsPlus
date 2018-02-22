package com.github.upcraftlp.heartsplus.util;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

/**
 * @author UpcraftLP
 */
public class ExtraHearts implements IExtraHearts {

    private float redHearts, blackHearts;
    private boolean whiteHeart;

    @Override
    public void addRedHearts(float toAdd) {
        this.redHearts += toAdd;
    }

    @Override
    public float getRedHearts() {
        return this.redHearts;
    }

    @Override
    public float getBlackHearts() {
        return this.blackHearts;
    }

    @Override
    public void addBlackHearts(float toAdd) {
        this.blackHearts += toAdd;
    }

    @Override
    public void setBlackHearts(float blackHearts) {
        this.blackHearts = blackHearts;
    }

    @Override
    public void setHearts(float red, float black, boolean white) {
        this.redHearts = red;
        this.blackHearts = black;
        this.whiteHeart = white;
    }

    @Override
    public boolean hasWhiteHeart() {
        return this.whiteHeart;
    }

    @Override
    public void setWhiteHeart(boolean whiteHeart) {
        this.whiteHeart = whiteHeart;
    }

    public static class Storage implements Capability.IStorage<IExtraHearts> {

        @Nullable
        @Override
        public NBTBase writeNBT(Capability<IExtraHearts> capability, IExtraHearts instance, EnumFacing side) {
            NBTTagCompound nbt = new NBTTagCompound();
            nbt.setFloat("red", instance.getRedHearts());
            nbt.setFloat("black", instance.getBlackHearts());
            nbt.setBoolean("white", instance.hasWhiteHeart());
            return nbt;
        }

        @Override
        public void readNBT(Capability<IExtraHearts> capability, IExtraHearts instance, EnumFacing side, NBTBase nbt) {
            NBTTagCompound compound = (NBTTagCompound) nbt;
            instance.setHearts(compound.getFloat("red"), compound.getFloat("black"), compound.getBoolean("white"));
        }
    }
}
