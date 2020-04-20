package tfcngwaila.compat.waila;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.annotation.ParametersAreNonnullByDefault;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.builder.ToStringBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.nbt.NBTTagCompound;


import mcp.MethodsReturnNonnullByDefault;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.api.types.Ore;
import net.dries007.tfc.api.types.Rock;
import net.dries007.tfc.objects.Gem;
import net.dries007.tfc.objects.blocks.devices.BlockPitKiln;
import net.dries007.tfc.objects.blocks.stone.BlockOreTFC;
import net.dries007.tfc.objects.items.metal.ItemOreTFC;
import net.dries007.tfc.objects.te.TEPitKiln;
import net.dries007.tfc.util.calendar.CalendarTFC;


@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public final class WailaPlugin implements IWailaDataProvider
{
    public static void callbackRegister(IWailaRegistrar registrar)
    {
        WailaPlugin dataProvider = new WailaPlugin();
        registrar.registerBodyProvider(dataProvider, BlockOreTFC.class);
        registrar.registerBodyProvider(dataProvider, BlockPitKiln.class);
        registrar.registerBodyProvider(dataProvider, TEPitKiln.class);



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
        if (b instanceof BlockPitKiln)
        {
            NBTTagCompound tag = accessor.getNBTData();
            Long litTick = tag.getLong("litTick");
            long remainingHours = ((long) ConfigTFC.GENERAL.pitKilnTime - (CalendarTFC.PLAYER_TIME.getTicks() - litTick) / 72000);
            String key = remainingHours + new TextComponentTranslation("tfcngwaila.hoursRemaining").getFormattedText();

            currenttip.add(key);

        }
        return currenttip;
    }
}
