package dev.qlick.java.commands.admin;

import dev.qlick.java.Secrets;
import lombok.Getter;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import java.util.concurrent.TimeUnit;

public class KickCommand extends ListenerAdapter {

    @Getter
    private static final CommandData commandData = Commands.slash("kick", "Kick a User")
            .addOption(OptionType.USER, "user", "The user to kick.", true)
            .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MANAGE_SERVER, Permission.KICK_MEMBERS));

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getInteraction().getName().equals(commandData.getName())) return;

        User target = event.getInteraction().getOption("user").getAsUser();

        if (event.getGuild().retrieveMember(target).complete().hasPermission(Permission.ADMINISTRATOR)) {
            event.reply("You can't kick an administrator.").setEphemeral(true).queue();
            return;
        }

        event.getGuild().kick(target).queue();
        event.getGuild().getTextChannelById(Secrets.getLogChannelId()).sendMessage(String.format("The %s <@%s> was kicked.", target.isBot() ? "Bot" : "User", target.getId())).queue();
        event.reply(String.format("The %s <@%s> was kicked.", target.isBot() ? "Bot" : "User", target.getId())).queue();
    }

}
