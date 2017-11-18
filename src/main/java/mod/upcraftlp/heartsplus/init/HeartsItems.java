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

    public static final Item BLACK_HEART = new ItemHeart(BLACK);
    public static final Item WHITE_HEART = new ItemHeart(WHITE);
    public static final Item RED_HEART = new ItemHeart(RED);
}
