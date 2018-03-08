package info.loenwind.excessive.commands;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import info.loenwind.excessive.config.Config;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

public class ExcessiveCommand extends CommandBase {

  public ExcessiveCommand() {
  }

  @Override
  public int getRequiredPermissionLevel() {
    return 2;
  }

  @Override
  public @Nonnull String getName() {
    return "excessive";
  }

  @Override
  public @Nonnull String getUsage(@Nonnull ICommandSender sender) {
    return "/excessive status|max <max>|radius <radius>";
  }

  @Override
  public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) throws CommandException {
    if (args.length < 1 || args[0].length() == 0) {
      throw new WrongUsageException(getUsage(sender));
    }
    if ("status".equals(args[0])) {
      if (args.length != 1) {
        throw new WrongUsageException(getUsage(sender));
      }
      sender.sendMessage(new TextComponentString("max=" + Config.maxEntities.getInt() + ", radius=" + Config.checkRadius.getInt()));
    } else if ("max".equals(args[0])) {
      if (args.length != 2 || args[1] == null) {
        throw new WrongUsageException(getUsage(sender));
      }
      @SuppressWarnings("null")
      int newMax = parseInt(args[1]);
      Config.maxEntities.setField(newMax);
      sender.sendMessage(new TextComponentString("max=" + Config.maxEntities.getInt() + ", radius=" + Config.checkRadius.getInt()));
    } else if ("radius".equals(args[0])) {
      if (args.length != 2 || args[1] == null) {
        throw new WrongUsageException(getUsage(sender));
      }
      @SuppressWarnings("null")
      int newRadius = parseInt(args[1]);
      Config.checkRadius.setField(newRadius);
      sender.sendMessage(new TextComponentString("max=" + Config.maxEntities.getInt() + ", radius=" + Config.checkRadius.getInt()));
    } else {
      throw new WrongUsageException(getUsage(sender));
    }
  }

  @SuppressWarnings("null")
  @Override
  public @Nonnull List<String> getTabCompletions(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args,
      @Nullable BlockPos targetPos) {
    if (args.length <= 1) {
      return getListOfStringsMatchingLastWord(args, "status", "max", "radius");
    }
    return Collections.emptyList();
  }
}
