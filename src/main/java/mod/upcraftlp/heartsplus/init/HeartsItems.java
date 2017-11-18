package mod.upcraftlp.heartsplus.init;

import mod.upcraftlp.heartsplus.Reference;
import mod.upcraftlp.heartsplus.item.ItemBlackHeart;
import mod.upcraftlp.heartsplus.item.ItemRedHeart;
import mod.upcraftlp.heartsplus.item.ItemWhiteHeart;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * @author UpcraftLP
 */
@GameRegistry.ObjectHolder(Reference.MODID)
public class HeartsItems {

    public static final Item BLACK_HEART = new ItemBlackHeart();
    public static final Item WHITE_HEART = new ItemWhiteHeart();
    public static final Item RED_HEART = new ItemRedHeart();
}
