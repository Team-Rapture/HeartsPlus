package mod.upcraftlp.heartsplus.net;

import io.netty.buffer.ByteBuf;
import mod.upcraftlp.heartsplus.util.HeartProvider;
import mod.upcraftlp.heartsplus.util.IExtraHearts;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author UpcraftLP
 */
public class PacketExtraHearts implements IMessage {

    private NBTTagCompound data;

    public PacketExtraHearts() {
        //NO-OP
    }

    public PacketExtraHearts(IExtraHearts hearts) {
        this.data = (NBTTagCompound) HeartProvider.HEARTS_CAPABILITY.getStorage().writeNBT(HeartProvider.HEARTS_CAPABILITY, hearts, null);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.data = ByteBufUtils.readTag(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeTag(buf, this.data);
    }

    public static class Handler implements IMessageHandler<PacketExtraHearts, IMessage> {

        @SideOnly(Side.CLIENT)
        @Override
        public IMessage onMessage(PacketExtraHearts message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> HeartProvider.HEARTS_CAPABILITY.getStorage().readNBT(HeartProvider.HEARTS_CAPABILITY, Minecraft.getMinecraft().player.getCapability(HeartProvider.HEARTS_CAPABILITY, null), null, message.data));
            return null;
        }
    }
}
