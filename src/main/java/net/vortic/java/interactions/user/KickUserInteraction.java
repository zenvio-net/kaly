package net.vortic.java.interactions.user;

import net.vortic.java.Secrets;
import lombok.Getter;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

public class KickUserInteraction extends ListenerAdapter {

    @Getter
    private static final CommandData commandData = Commands.user("Kick")
            .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MANAGE_SERVER, Permission.KICK_MEMBERS));

    @Override
    public void onUserContextInteraction(UserContextInteractionEvent event) {
        if (!event.getName().equals(commandData.getName())) return;

        var target = event.getTarget();

        if (event.getGuild().retrieveMember(target).complete().hasPermission(Permission.ADMINISTRATOR)) {
            event.reply("You can't kick an administrator.").setEphemeral(true).queue();
            return;
        }

        event.getGuild().kick(target).queue();
        event.getGuild().getTextChannelById(Secrets.getLogChannelId()).sendMessage(String.format("The %s <@%s> was kicked.", target.isBot() ? "Bot" : "User", target.getId())).queue();
        event.reply(String.format("The %s <@%s> was kicked.", target.isBot() ? "Bot" : "User", target.getId())).setEphemeral(true).queue();
    }

}
