package info.loenwind.excessive.handlers;

import java.util.List;

import info.loenwind.excessive.ExcessiveMod;
import info.loenwind.excessive.config.Config;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@EventBusSubscriber(modid = ExcessiveMod.MODID, value = Side.SERVER)
public class JoinHandler {

  private static long silentUntil = -1;
  private static int silentRejects = 0;

  @SuppressWarnings("null")
  @SubscribeEvent
  public static void onJoin(EntityJoinWorldEvent event) {
    Entity entity = event.getEntity();
    if (entity instanceof EntityPlayer) {
      return;
    }
    World world = event.getWorld();
    AxisAlignedBB bb = new AxisAlignedBB(entity.posX, entity.posY, entity.posZ, entity.posX, entity.posY, entity.posZ).grow(Config.checkRadius.getFloat());
    List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(entity, bb);
    if (list.size() > Config.maxEntities.getInt()) {
      event.setCanceled(true);

      if (entity instanceof EntityXPOrb) {
        for (Entity other : list) {
          if (other instanceof EntityXPOrb && !other.isDead) {
            ((EntityXPOrb) other).xpValue += ((EntityXPOrb) entity).xpValue;
            return;
          }
        }
      }

      long now = world.getTotalWorldTime();
      if (now > silentUntil) {
        silentUntil = now + 5 * 20;
        ((WorldServer) world).getMinecraftServer().getPlayerList()
            .sendMessage(new TextComponentString("Excessive entities at " + ((int) entity.posX) + "/" + ((int) entity.posY) + "/" + ((int) entity.posZ)
                + " in dim " + entity.world.provider.getDimension() + (silentRejects == 0 ? "" : " (" + silentRejects + " more)")));
        silentRejects = 0;
      } else {
        silentRejects++;
      }
    }

  }

}
