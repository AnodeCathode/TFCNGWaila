package tfcngwaila.compat.waila;

import java.util.List;
import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import mcp.MethodsReturnNonnullByDefault;
import mcp.mobius.waila.api.*;
import net.dries007.tfc.api.types.IAnimalTFC;
import net.dries007.tfc.api.types.ICreatureTFC;
import net.dries007.tfc.objects.entity.EntitiesTFC;
import net.dries007.tfc.objects.entity.animal.EntityCowTFC;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class WailaEntitiesTFC implements IWailaEntityProvider
{
    public static void callbackRegister(IWailaRegistrar registrar)
    {
        WailaEntitiesTFC dataProvider = new WailaEntitiesTFC();
        registrar.registerBodyProvider(dataProvider, IAnimalTFC.class);




    }
    @Override
    public Entity getWailaOverride(IWailaEntityAccessor accessor, IWailaConfigHandler config)
    {
        return null;
    }

    @Override
    public List<String> getWailaHead(Entity entity, List<String> currenttip, IWailaEntityAccessor accessor, IWailaConfigHandler config)
    {
        return null;
    }

    @Override
    public List<String> getWailaBody(Entity entity, List<String> currenttip, IWailaEntityAccessor accessor, IWailaConfigHandler config)
    {

        if (entity instanceof EntityCowTFC) currenttip = EntityCowBody(entity, currenttip, accessor, config);
        return currenttip;
    }

    private List<String> EntityCowBody(Entity entity, List<String> currenttip, IWailaEntityAccessor accessor, IWailaConfigHandler config)
    {
        EntityCowTFC cow = (EntityCowTFC) entity;
        NBTTagCompound nbt = accessor.getNBTData();

        float familiarity = Math.max(0.0F, Math.min(1.0F, cow.getFamiliarity()));
        IAnimalTFC.Gender gender = cow.getGender();
        if (cow.getPercentToAdulthood() < 1)
        {
            currenttip.add(new TextComponentTranslation("baby").getFormattedText());
        }
        if (cow.isReadyToMate())
        {
            currenttip.add(new TextComponentTranslation("getbusy").getFormattedText());
        }
        if (gender == IAnimalTFC.Gender.FEMALE )
        {
            currenttip.add(new TextComponentTranslation("female").getFormattedText());
            if (cow.isReadyForAnimalProduct())
            {
                currenttip.add(new TextComponentTranslation("milked").getFormattedText());
            }
            if (cow.isFertilized())
            {
                currenttip.add(new TextComponentTranslation("pregnant").getFormattedText());
            }
        }
        else
        {
            currenttip.add(new TextComponentTranslation("male").getFormattedText());
        }
        String foo = cow.getCustomNameTag();
        return currenttip;
    }

    @Override
    public List<String> getWailaTail(Entity entity, List<String> currenttip, IWailaEntityAccessor accessor, IWailaConfigHandler config) {
        return null;
    }
    @Override
    public NBTTagCompound getNBTData(EntityPlayerMP player, Entity ent, NBTTagCompound tag, World world) {
        return ent.getEntityData();
    }


}
