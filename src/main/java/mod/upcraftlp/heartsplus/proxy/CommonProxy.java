package mod.upcraftlp.heartsplus.proxy;

import core.upcraftlp.craftdev.api.net.NetworkHandler;
import core.upcraftlp.craftdev.api.util.UpdateChecker;
import mod.upcraftlp.heartsplus.Reference;
import mod.upcraftlp.heartsplus.net.PacketExtraHearts;
import mod.upcraftlp.heartsplus.util.CommandHearts;
import mod.upcraftlp.heartsplus.util.ExtraHearts;
import mod.upcraftlp.heartsplus.util.IExtraHearts;
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
        CapabilityManager.INSTANCE.register(IExtraHearts.class, new ExtraHearts.Storage(), ExtraHearts.class);
        NetworkHandler.registerPacket(PacketExtraHearts.Handler.class, PacketExtraHearts.class, Side.CLIENT);
    }

    public void init(FMLInitializationEvent event) {
    }

    public void postInit(FMLPostInitializationEvent event) {

    }

    public void serverStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandHearts());
    }
}
