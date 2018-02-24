package com.github.upcraftlp.heartsplus.proxy;

import core.upcraftlp.craftdev.api.net.NetworkHandler;
import core.upcraftlp.craftdev.api.util.ModHelper;
import core.upcraftlp.craftdev.api.util.UpdateChecker;
import com.github.upcraftlp.heartsplus.Reference;
import com.github.upcraftlp.heartsplus.net.PacketExtraHearts;
import com.github.upcraftlp.heartsplus.util.CommandHearts;
import com.github.upcraftlp.heartsplus.util.ExtraHearts;
import com.github.upcraftlp.heartsplus.util.IExtraHearts;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.relauncher.Side;

/**
 * @author UpcraftLP
 */
public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        UpdateChecker.registerMod(Reference.MODID);
        CapabilityManager.INSTANCE.register(IExtraHearts.class, new ExtraHearts.Storage(), ExtraHearts::new);
        NetworkHandler.registerPacket(PacketExtraHearts.Handler.class, PacketExtraHearts.class, Side.CLIENT);
    }

    public void init(FMLInitializationEvent event) {
    }

    public void postInit(FMLPostInitializationEvent event) {

    }

    public void serverStarting(FMLServerStartingEvent event) {
        if(ModHelper.isDebugMode()) event.registerServerCommand(new CommandHearts());
    }
}
