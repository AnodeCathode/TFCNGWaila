package tfcngwaila.compat.waila;

import java.util.function.Function;
import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import mcjty.theoneprobe.api.*;
import net.dries007.tfc.util.Helpers;


import static tfcngwaila.TFCNGWaila.MODID;

public final class TOPPlugin implements Function<ITheOneProbe, Void>, IProbeInfoProvider
{
    @Override
    public String getID()
    {
        return MODID + ":top_provider";
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, EntityPlayer entityPlayer, World world, IBlockState iBlockState, IProbeHitData iProbeHitData)
    {

    }

    @Nullable
    @Override
    public Void apply(ITheOneProbe iTheOneProbe)
    {
        iTheOneProbe.registerProvider(this);
        return null;
    }
}
