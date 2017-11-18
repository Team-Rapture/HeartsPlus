package mod.upcraftlp.heartsplus.entity;

import mod.upcraftlp.heartsplus.util.EnumHeartType;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
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

    }

    public EntityHeart(World worldIn, double x, double y, double z) {
        this(worldIn);
        this.setPosition(x, y, z);
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

    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound) {

    }

    public EnumHeartType getType() {
        return this.type;
    }
}
