package mod.upcraftlp.heartsplus.handler;

import mod.upcraftlp.heartsplus.Main;
import mod.upcraftlp.heartsplus.Reference;
import mod.upcraftlp.heartsplus.init.HeartsSounds;
import mod.upcraftlp.heartsplus.util.EnumHeartType;
import mod.upcraftlp.heartsplus.util.HeartProvider;
import mod.upcraftlp.heartsplus.util.IExtraHearts;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.Clone;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.util.UUID;

/**
 * @author UpcraftLP
 */
@Mod.EventBusSubscriber(modid = Reference.MODID)
public class HeartsHandler {

    private static final ResourceLocation HEARTS_CAP = new ResourceLocation(Reference.MODID, "hearts");

    /**
     * Opcodes: 0 = addition, 1 = additive scaling, 2 = multiplicative scaling
     */
    private static final UUID HEART_MODIFIER = UUID.fromString("a2ec6104-0d9d-45ae-8c27-616775886220");

    @SubscribeEvent
    public static void onAttachCapability(AttachCapabilitiesEvent<Entity> event) {
        if(event.getObject() instanceof EntityPlayer) {
            event.addCapability(HEARTS_CAP, new HeartProvider());
        }
    }

    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) { //PlayerEvent$Clone
        HeartProvider.sync(event.player);
    }

    @SubscribeEvent
    public static void onUpdate(TickEvent.PlayerTickEvent event) {
        if(event.side == Side.CLIENT || event.phase != TickEvent.Phase.START) return;
        EntityPlayer player = event.player;
        IExtraHearts extraHearts = player.getCapability(HeartProvider.HEARTS_CAPABILITY, null);
        IAttributeInstance health = player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);
        AttributeModifier health_modifier = health.getModifier(HEART_MODIFIER);
        float amount = extraHearts.getRedHearts() * 2; //needs to be this way, *shrug*
        if(health_modifier != null) {
            if(health_modifier.getAmount() == amount) return;
            health.removeModifier(HEART_MODIFIER);
        }
        health.applyModifier(new AttributeModifier(HEART_MODIFIER, Reference.MODID + ":red_hearts", amount, 0));
        player.setHealth(player.getMaxHealth());
    }

    @SubscribeEvent
    public static void onPlayerDeath(Clone event) { //PlayerEvent$Clone
        EntityPlayer player = event.getEntityPlayer();
        IExtraHearts extraHearts = event.getOriginal().getCapability(HeartProvider.HEARTS_CAPABILITY, null);
        IExtraHearts extraHearts1 = player.getCapability(HeartProvider.HEARTS_CAPABILITY, null);
        extraHearts1.setHearts(extraHearts.getRedHearts(), 0, false);
        HeartProvider.sync(player);
    }

    @SubscribeEvent
    public static void onPlayerSleepSuccessful(PlayerWakeUpEvent event) {
        if(event.shouldSetSpawn()) { //successfull sleep, night was skipped
            EntityPlayer player = event.getEntityPlayer();
            IExtraHearts extraHearts = player.getCapability(HeartProvider.HEARTS_CAPABILITY, null);
            if(extraHearts.hasWhiteHeart()) {
                addHearts(player, player.world, EnumHeartType.WHITE, 1);
                HeartProvider.sync(player);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerDamage(LivingHurtEvent event) {
        if(event.getEntityLiving() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getEntityLiving();
            IExtraHearts extraHearts = player.getCapability(HeartProvider.HEARTS_CAPABILITY, null);
            float amount = event.getAmount();
            Main.getLogger().warn(amount);
            Main.getLogger().warn(player.getHealth());
            float blackHearts = extraHearts.getBlackHearts() - amount;
            if(blackHearts >= 0) {
                amount = 0;
            }
            else {
                amount = -blackHearts;
                if(extraHearts.hasWhiteHeart()) {
                    amount -= 0.5F;
                    extraHearts.setWhiteHeart(false);
                }
            }
            extraHearts.setBlackHearts(blackHearts > 0.0F ? blackHearts : 0.0F);
            event.setAmount(amount);
            HeartProvider.sync(player);
        }
    }

    public static void addHearts(EntityPlayer player, World world, EnumHeartType heartType, float amount) {
        addHearts(player, world, heartType, amount, true);
    }

    public static void addHearts(EntityPlayer player, World world, EnumHeartType heartType, float amount, boolean playSound) {
        IExtraHearts extraHearts = player.getCapability(HeartProvider.HEARTS_CAPABILITY, null);
        switch (heartType) {
            case BLACK:
                extraHearts.addBlackHearts(amount);
                if(playSound && amount > 0) world.playSound(null, player.posX, player.posY, player.posZ, HeartsSounds.COLLECT_BLACK_HEART, SoundCategory.PLAYERS, 0.7F, 0.6F + player.getRNG().nextFloat() * 0.4F);
                break;
            case RED:
                extraHearts.addRedHearts(amount);
                if(playSound && amount > 0) world.playSound(null, player.posX, player.posY, player.posZ, HeartsSounds.COLLECT_RED_HEART, SoundCategory.PLAYERS, 0.7F, 0.6F + player.getRNG().nextFloat() * 0.4F);
                break;
            case WHITE:
                if(amount > 0) {
                    if(extraHearts.hasWhiteHeart()) {
                        extraHearts.setWhiteHeart(false);
                        extraHearts.addRedHeart();
                        if(playSound)world.playSound(null, player.posX, player.posY, player.posZ, HeartsSounds.EVOLVE_HEART, SoundCategory.PLAYERS, 0.7F, 0.6F + player.getRNG().nextFloat() * 0.4F);
                        player.sendStatusMessage(new TextComponentString("you just evolved your white heart into a red one!"), true); //TODO remove debug output
                    }
                    else {
                        extraHearts.setWhiteHeart(true);
                        if(playSound) world.playSound(null, player.posX, player.posY, player.posZ, HeartsSounds.COLLECT_WHITE_HEART, SoundCategory.PLAYERS, 0.7F, 0.6F + player.getRNG().nextFloat() * 0.4F);
                    }
                }
                else extraHearts.setWhiteHeart(false);
                break;
                //TODO add rotten heart?
        }
        HeartProvider.sync(player);
    }

}
