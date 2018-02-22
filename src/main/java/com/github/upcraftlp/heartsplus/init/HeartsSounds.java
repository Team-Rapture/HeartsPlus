package com.github.upcraftlp.heartsplus.init;

import com.github.upcraftlp.heartsplus.Reference;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * @author UpcraftLP
 */
@GameRegistry.ObjectHolder(Reference.MODID)
public class HeartsSounds {

    //sounds are created by initializing a field for them to null
    public static final SoundEvent COLLECT_RED_HEART = null; //pick up red heart
    public static final SoundEvent COLLECT_BLACK_HEART = null; //pick up black heart
    public static final SoundEvent COLLECT_WHITE_HEART = null; //pick up white heart
    public static final SoundEvent EVOLVE_HEART = null; //transform white heart into red heart ("holy" sound)
}
