/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.modules.playerinfo.commands;

import io.github.nucleuspowered.nucleus.argumentparsers.NicknameArgument;
import io.github.nucleuspowered.nucleus.internal.annotations.Permissions;
import io.github.nucleuspowered.nucleus.internal.annotations.RegisterCommand;
import io.github.nucleuspowered.nucleus.internal.command.AbstractCommand;
import io.github.nucleuspowered.nucleus.internal.permissions.PermissionInformation;
import io.github.nucleuspowered.nucleus.internal.permissions.SuggestedLevel;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.Map;

@Permissions(suggestedLevel = SuggestedLevel.MOD)
@RegisterCommand("getpos")
public class GetPosCommand extends AbstractCommand<CommandSource> {

    private final String playerKey = "player";

    @Override
    protected Map<String, PermissionInformation> permissionSuffixesToRegister() {
        Map<String, PermissionInformation> mspi = super.permissionSuffixesToRegister();
        mspi.put("other", new PermissionInformation(
                plugin.getMessageProvider().getMessageWithFormat("permission.getpos.others"),
                SuggestedLevel.MOD
        ));
        return mspi;
    }

    @Override
    public CommandElement[] getArguments() {
        return new CommandElement[] {
            GenericArguments.onlyOne(
                    GenericArguments.requiringPermission(
                    GenericArguments.optional(
                    new NicknameArgument(Text.of(playerKey),
                            plugin.getUserDataManager(),
                            NicknameArgument.UnderlyingType.PLAYER,
                            true)), permissions.getPermissionWithSuffix("other")))
        };
    }

    @Override
    public CommandResult executeCommand(CommandSource src, CommandContext args) throws Exception {
        Player target = getUserFromArgs(Player.class, src, playerKey, args);

        Location<World> location = target.getLocation();
        if (target.equals(src)) {
            src.sendMessage(
                plugin.getMessageProvider()
                    .getTextMessageWithFormat(
                        "command.getpos.location.self",
                        location.getExtent().getName(),
                        String.valueOf(location.getBlockPosition().getX()),
                        String.valueOf(location.getBlockPosition().getY()),
                        String.valueOf(location.getBlockPosition().getZ())
                    )
            );
        } else {
            src.sendMessage(
                plugin.getMessageProvider()
                    .getTextMessageWithFormat(
                        "command.getpos.location.other",
                        plugin.getNameUtil().getSerialisedName(target),
                        location.getExtent().getName(),
                        String.valueOf(location.getBlockPosition().getX()),
                        String.valueOf(location.getBlockPosition().getY()),
                        String.valueOf(location.getBlockPosition().getZ())
                )
            );
        }

        return CommandResult.success();
    }
}
