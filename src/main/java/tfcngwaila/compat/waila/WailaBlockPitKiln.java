package tfcngwaila.compat.waila;

import java.util.List;
import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import net.minecraft.util.text.TextComponentTranslation;


import mcp.MethodsReturnNonnullByDefault;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.dries007.tfc.objects.blocks.devices.BlockPitKiln;
import net.dries007.tfc.util.calendar.CalendarTFC;
import net.dries007.tfc.ConfigTFC;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public final class WailaBlockPitKiln implements IWailaDataProvider
{
    public static void callbackRegister(IWailaRegistrar registrar)
    {

        registrar.registerBodyProvider(new WailaBlockPitKiln(), BlockPitKiln.class);

    }
    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        Block b = accessor.getBlock();
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
