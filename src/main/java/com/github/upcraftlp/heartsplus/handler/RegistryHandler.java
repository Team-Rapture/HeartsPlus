package com.github.upcraftlp.heartsplus.handler;

import core.upcraftlp.craftdev.api.util.ModHelper;
import core.upcraftlp.craftdev.api.util.RegistryUtils;
import com.github.upcraftlp.heartsplus.Reference;
import com.github.upcraftlp.heartsplus.init.HeartsBlocks;
import com.github.upcraftlp.heartsplus.init.HeartsItems;
import com.github.upcraftlp.heartsplus.init.HeartsSounds;
import com.github.upcraftlp.heartsplus.init.HeartsTabs;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.lang.reflect.Field;
import java.util.Locale;

/**
 * @author UpcraftLP
 */
@Mod.EventBusSubscriber(modid = Reference.MODID)
public class RegistryHandler {

    @SubscribeEvent
    public static void onRegisterItems(RegistryEvent.Register<Item> event) {
        RegistryUtils.createRegistryEntries(Item.class, event, HeartsItems.class, Reference.MODID, HeartsTabs.HEARTSPLUS_TAB);
    }

    @SubscribeEvent
    public static void onRegisterBlocks(RegistryEvent.Register<Block> event) {
        RegistryUtils.createRegistryEntries(Block.class, event, HeartsBlocks.class, Reference.MODID, HeartsTabs.HEARTSPLUS_TAB);
    }

    @SubscribeEvent
    public static void onRegisterSounds(RegistryEvent.Register<SoundEvent> event) {
        for(Field f : HeartsSounds.class.getFields()) { //TODO move to core --> single line for mods
            ModHelper.registerSound(f.getName().toLowerCase(Locale.ROOT), Reference.MODID);
        }
    }
}
