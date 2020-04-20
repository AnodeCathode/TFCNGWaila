package tfcngwaila.compat.waila;

import java.util.List;
import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.TextComponentTranslation;


import mcp.MethodsReturnNonnullByDefault;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.dries007.tfc.api.types.Ore;
import net.dries007.tfc.objects.blocks.stone.BlockOreTFC;


@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public final class WailaBlockOreTFC implements IWailaDataProvider
{
    public static void callbackRegister(IWailaRegistrar registrar)
    {

        registrar.registerBodyProvider(new WailaBlockOreTFC(), BlockOreTFC.class);

    }

    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        Block b = accessor.getBlock();
        TileEntity te = accessor.getTileEntity();

        if (b instanceof BlockOreTFC)
        {

            int metadata = accessor.getMetadata();
            Ore.Grade gradevalue= Ore.Grade.valueOf(metadata);
            String orename = ((BlockOreTFC) b).ore.toString();
            String key;
            if (gradevalue == Ore.Grade.NORMAL){
                key = "item.tfc.ore." + orename + ".name";
            }
            else
            {
                String gradename = gradevalue.toString().toLowerCase();
                key = "item.tfc.ore." + orename + "." + gradename + ".name";
            }
            String text = new TextComponentTranslation(key).getFormattedText();
            currenttip.add(text);
        }

        return currenttip;
    }

}
