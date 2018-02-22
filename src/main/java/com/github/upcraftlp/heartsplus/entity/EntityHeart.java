package mod.upcraftlp.heartsplus.entity;

import mod.upcraftlp.heartsplus.handler.HeartsHandler;
import mod.upcraftlp.heartsplus.util.EnumHeartType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

/**
 * @author UpcraftLP
 */
public class EntityHeart extends Entity {

    private EnumHeartType type;

    public EntityHeart(World worldIn) {
        super(worldIn);
    }


    @Override
    protected void entityInit() {
        //NO-OP
    }

    public EntityHeart(World worldIn, double x, double y, double z) {
        this(worldIn);
        this.setPosition(x, y, z);
    }

    public EntityHeart(ItemStack stack, World worldIn, double x, double y, double z) {
        this(worldIn, x, y, z);
        this.setHeartType(EnumHeartType.getTypeFromStack(stack));
    }

    /**
     * set the {@link EnumHeartType}
     *
     * @return the {@link EntityHeart} itself for method chaining
     */
    public EntityHeart setHeartType(EnumHeartType type) {
        this.type = type;
        return this;
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound) {
        this.type = EnumHeartType.values()[MathHelper.clamp(compound.getInteger("type"), 0, EnumHeartType.values().length - 1)];
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound) {
        compound.setInteger("type", this.type.ordinal());
    }

    public EnumHeartType getType() {
        return this.type;
    }

    @Override
    public void onCollideWithPlayer(EntityPlayer entityIn) {
        HeartsHandler.addHearts(entityIn, this.world, this.type, 1.0F);
        this.setDead();
    }
}
