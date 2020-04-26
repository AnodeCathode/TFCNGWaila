package tfcngwaila.compat.waila;

import java.util.List;
import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;


import mcp.MethodsReturnNonnullByDefault;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.api.types.ICrop;
import net.dries007.tfc.api.types.Ore;
import net.dries007.tfc.objects.blocks.agriculture.*;
import net.dries007.tfc.objects.blocks.devices.*;
import net.dries007.tfc.objects.blocks.stone.BlockFarmlandTFC;
import net.dries007.tfc.objects.blocks.stone.BlockOreTFC;
import net.dries007.tfc.objects.blocks.wood.BlockBarrel;
import net.dries007.tfc.objects.items.metal.ItemOreTFC;
import net.dries007.tfc.objects.te.*;
import net.dries007.tfc.util.agriculture.Crop;
import net.dries007.tfc.util.calendar.CalendarTFC;


@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public final class WailaBlocksTFC implements IWailaDataProvider
{
    public static void callbackRegister(IWailaRegistrar registrar)
    {
        WailaBlocksTFC dataProvider = new WailaBlocksTFC();

        //Stack
        registrar.registerStackProvider(dataProvider, BlockOreTFC.class);
        //Head
        registrar.registerHeadProvider(dataProvider, BlockOreTFC.class);
        //Body
        registrar.registerBodyProvider(dataProvider, BlockOreTFC.class);
        registrar.registerBodyProvider(dataProvider, TEPitKiln.class);
        registrar.registerBodyProvider(dataProvider, TEFirePit.class);
        registrar.registerBodyProvider(dataProvider, TEBloomery.class);
        registrar.registerBodyProvider(dataProvider, TEBlastFurnace.class);
        registrar.registerBodyProvider(dataProvider, TECharcoalForge.class);
        registrar.registerBodyProvider(dataProvider, TEBarrel.class);
        registrar.registerBodyProvider(dataProvider, TELogPile.class);
            //Agriculture
        registrar.registerBodyProvider(dataProvider, TECropBase.class);
        registrar.registerBodyProvider(dataProvider, BlockFarmlandTFC.class);
        registrar.registerBodyProvider(dataProvider, BlockFruitTreeLeaves.class);
        registrar.registerBodyProvider(dataProvider, BlockBerryBush.class);
        registrar.registerBodyProvider(dataProvider, BlockCropDead.class);






    }

    @Override
    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        Block b = accessor.getBlock();
        ItemStack itemstack = ItemStack.EMPTY;

        if (b instanceof BlockOreTFC)
        {
            itemstack = oreTFCStack(accessor, config);
        }
        return itemstack;
    }

    @Override
    public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        Block b = accessor.getBlock();

        if (b instanceof BlockOreTFC) currenttip = oreTFCHead(itemStack, currenttip, accessor, config);
        return currenttip;
    }

    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        Block b = accessor.getBlock();
        TileEntity te = accessor.getTileEntity();

        // Mechanics
        if (b instanceof BlockOreTFC) currenttip = oreTFCBody(itemStack, currenttip, accessor, config);
        else if (te instanceof TEPitKiln) currenttip = pitKilnBody(itemStack, currenttip, accessor, config);
        else if (te instanceof TECharcoalForge) currenttip = charcoalForgeBody(itemStack, currenttip, accessor, config);
        else if (te instanceof TELogPile) currenttip = logPileBody(itemStack, currenttip, accessor, config);
        else if (te instanceof TEBarrel) currenttip = barrelBody(itemStack, currenttip, accessor, config);
        else if (te instanceof TEBloomery) currenttip = bloomeryBody(itemStack, currenttip, accessor, config);
        else if (te instanceof TEFirePit) currenttip = firepitBody(itemStack, currenttip, accessor, config);
        // Crops and Trees
        else if (te instanceof TECropBase) currenttip = teCropBaseBody(itemStack, currenttip, accessor, config);
        else if (te instanceof TECropSpreading) currenttip = teCropSpreadingBody(itemStack, currenttip, accessor, config);
        else if (b instanceof BlockFruitTreeLeaves) currenttip = blockFruitTreeLeavesBody(itemStack, currenttip, accessor, config);
        else if (b instanceof BlockBerryBush) currenttip = blockBerryBushBody(itemStack, currenttip, accessor, config);
        else if (b instanceof BlockFarmlandTFC) currenttip = blockFarmlandTFCBody(itemStack, currenttip, accessor, config);
        else if (b instanceof BlockCropDead) currenttip = blockCropDeadBody(itemStack, currenttip, accessor, config);
        


        return currenttip;
    }

    private List<String> blockCropDeadBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        return currenttip;
    }

    private List<String> blockFarmlandTFCBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        return currenttip;
    }

    private List<String> blockBerryBushBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        return currenttip;
    }

    private List<String> blockFruitTreeLeavesBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        return currenttip;
    }

    public List<String> firepitBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        return currenttip;
    }

    public List<String> pitKilnBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        Block b = accessor.getBlock();
        TEPitKiln te = (TEPitKiln) accessor.getTileEntity();
        String key;
        Boolean isLit = te.isLit();
        Long litTick = te.getLitTick();

        if (isLit)
        {
            long remainingMinutes = Math.round(((long) ConfigTFC.GENERAL.pitKilnTime - (CalendarTFC.PLAYER_TIME.getTicks() - litTick)) / 1200);

            key = remainingMinutes + " " + new TextComponentTranslation("remaining").getFormattedText();
        }
        else
        {
            Integer straw = te.getStrawCount();
            Integer logs = te.getLogCount();
            if (straw == 8 && logs == 8)
            {
                key = new TextComponentTranslation("unlit").getFormattedText();
            }
            else
            {
                key = straw + " " + new TextComponentTranslation("straw").getFormattedText() + " " + logs + " " + new TextComponentTranslation("logs").getFormattedText();

            }

        }
        currenttip.add(key);

        return currenttip;
    }

    public List<String> oreTFCBody(ItemStack itemStack, List<java.lang.String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
    {

        Block b = accessor.getBlock();
        int metadata = accessor.getMetadata();
        Ore.Grade gradevalue = Ore.Grade.valueOf(metadata);
        String orename = ((BlockOreTFC) b).ore.toString();
        String key;
        if (gradevalue == Ore.Grade.NORMAL)
        {
            key = "item.tfc.ore." + orename + ".name";
        }
        else
        {
            String gradename = gradevalue.toString().toLowerCase();
            key = "item.tfc.ore." + orename + "." + gradename + ".name";
        }
        String text = new TextComponentTranslation(key).getFormattedText();
        currenttip.add(text);

        return currenttip;
    }

    public ItemStack oreTFCStack(IWailaDataAccessor accessor, IWailaConfigHandler config)
    {

        BlockOreTFC b = (BlockOreTFC) accessor.getBlock();
        BlockStateContainer state = b.getBlockState();
        ItemStack itemstack = ItemOreTFC.get(b.ore, 1);

        return itemstack;
    }

    private List<String> teCropSpreadingBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
    {

        return currenttip;
    }

    private List<String> teCropBaseBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        TECropBase te = (TECropBase) accessor.getTileEntity();
        BlockCropSimple bs = (BlockCropSimple) accessor.getBlock();
        ICrop crop = bs.getCrop();
        Integer maxStage = crop.getMaxStage();
        Float totalGrowthTime = crop.getGrowthTime();
        IBlockState blockstate = accessor.getBlockState();
        Integer curStage = blockstate.getValue(bs.getStageProperty());
        long tick = te.getLastUpdateTick();
        Float totalTime = totalGrowthTime * maxStage;
        Float currentTime = (curStage * totalGrowthTime ) + (CalendarTFC.PLAYER_TIME.getTicks() - tick);
        int completionPerc = Math.round(currentTime/totalTime * 100);
        String text;
        if (completionPerc <= 100)
        {
            text = completionPerc + " " + new TextComponentTranslation("percent").getFormattedText();
        }
            else
        {
            text = new TextComponentTranslation("mature").getFormattedText();
        }

        currenttip.add(text);





        return currenttip;
    }

    private List<String> bloomeryBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        return currenttip;
    }

    private List<String> barrelBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        return currenttip;
    }

    private List<String> logPileBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        return currenttip;
    }

    private List<String> charcoalForgeBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        return currenttip;
    }

    private List<String> oreTFCHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        BlockOreTFC b = (BlockOreTFC) accessor.getBlock();
        currenttip.add(new TextComponentTranslation(b.getTranslationKey() + ".name").getFormattedText());
        return currenttip;
    }

}
