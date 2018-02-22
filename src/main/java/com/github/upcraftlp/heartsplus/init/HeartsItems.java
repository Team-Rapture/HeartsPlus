package com.github.upcraftlp.heartsplus.init;

import com.github.upcraftlp.heartsplus.Reference;
import com.github.upcraftlp.heartsplus.item.ItemHeart;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * @author UpcraftLP
 */
@GameRegistry.ObjectHolder(Reference.MODID)
public class HeartsItems {

    public static final Item HEART = new ItemHeart();
}
