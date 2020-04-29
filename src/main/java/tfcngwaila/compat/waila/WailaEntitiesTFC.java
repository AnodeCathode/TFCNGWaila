package tfcngwaila.compat.waila;

import java.util.List;
import javax.annotation.ParametersAreNonnullByDefault;

import net.dries007.tfc.objects.entity.animal.EntityAnimalMammal;
import net.dries007.tfc.util.calendar.CalendarTFC;
import net.dries007.tfc.util.calendar.ICalendar;
import net.dries007.tfc.util.calendar.ICalendarFormatted;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentTranslation;

import mcp.MethodsReturnNonnullByDefault;
import mcp.mobius.waila.api.*;
import net.dries007.tfc.api.types.IAnimalTFC;

@SuppressWarnings("unused")
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
    public List<String> getWailaBody(Entity entity, List<String> currenttip, IWailaEntityAccessor accessor, IWailaConfigHandler config)
    {
        IAnimalTFC animal = (IAnimalTFC) entity;
        boolean familiarized = animal.getFamiliarity() > 0.15f;
        currenttip.add(new TextComponentTranslation(familiarized ? "familiarized" : "notfamiliarized").getFormattedText());
        switch (animal.getAge())
        {
            case CHILD:
                currenttip.add(new TextComponentTranslation("childhoodend").getFormattedText()
                    + ": " + ICalendarFormatted.getTimeAndDate(
                    ICalendar.TICKS_IN_DAY * (animal.getBirthDay() + animal.getDaysToAdulthood())
                    , CalendarTFC.CALENDAR_TIME.getDaysInMonth()));
                break;
            case OLD:
                currenttip.add(new TextComponentTranslation("old").getFormattedText());
                break;
            case ADULT:
                if (familiarized)
                {
                    if (animal.isReadyToMate())
                    {
                        currenttip.add(new TextComponentTranslation("getbusy").getFormattedText());
                    }
                    if (animal.isFertilized())
                    {
                        NBTTagCompound nbt = accessor.getNBTData();
                        long pregnancyDate = nbt.getLong("pregnant");
                        if (pregnancyDate > 0)
                            currenttip.add(new TextComponentTranslation("pregnancyend").getFormattedText()
                                + ": " + ICalendarFormatted.getTimeAndDate(
                                ICalendar.TICKS_IN_DAY * (pregnancyDate + 240), CalendarTFC.CALENDAR_TIME.getDaysInMonth()));
                    }
                    if (animal.getGender() == IAnimalTFC.Gender.FEMALE && animal.isReadyForAnimalProduct())
                    {
                        currenttip.add(new TextComponentTranslation(
                            (animal instanceof EntityAnimalMammal) ? "milked" : "haseggs").getFormattedText());
                    }
                }
                break;
        }
        return currenttip;
    }
}