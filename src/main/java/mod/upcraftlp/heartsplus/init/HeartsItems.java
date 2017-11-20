package mod.upcraftlp.heartsplus.init;

import mod.upcraftlp.heartsplus.Reference;
import mod.upcraftlp.heartsplus.item.ItemHeart;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

import static mod.upcraftlp.heartsplus.util.EnumHeartType.*;

/**
 * @author UpcraftLP
 */
@GameRegistry.ObjectHolder(Reference.MODID)
public class HeartsItems {

    public static final Item HEART = new ItemHeart();
}
