package net.vortic.java.events.channels;

import net.vortic.java.Secrets;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;

import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;

public class OnWaitingChannelJoinEvent extends ListenerAdapter {

    @Override
    public void onGuildVoiceUpdate(@NotNull GuildVoiceUpdateEvent event) {
        if (event.getChannelLeft() != null && event.getChannelLeft().getName().startsWith("\uD83D\uDD09")) {
            event.getGuild().getChannels().forEach(channel -> {
                if (channel.getName().startsWith("\uD83D\uDD09") && ((VoiceChannel) channel).getMembers().isEmpty()) channel.delete().queue();
            });
            return;
        }

        if (event.getChannelJoined() == null || !event.getChannelJoined().getId().equals(Secrets.getWaitingChannelId())) return;

        VoiceChannel channel = event.getGuild().createVoiceChannel(String.format("\uD83D\uDD09 %s needs help!", event.getMember().getUser().getName()), event.getGuild().getCategoryById(Secrets.getSupportCategoryId())).complete();
        channel.getManager()
                .putPermissionOverride(event.getMember(), EnumSet.of(Permission.VIEW_CHANNEL, Permission.VOICE_CONNECT, Permission.VOICE_SPEAK), null)
                .putPermissionOverride(event.getGuild().getPublicRole(), null, EnumSet.of(Permission.VIEW_CHANNEL, Permission.VOICE_CONNECT, Permission.VOICE_SPEAK))
                .putPermissionOverride(event.getGuild().getRoleById(Secrets.getTeamRoleId()), EnumSet.of(Permission.VIEW_CHANNEL, Permission.VOICE_CONNECT, Permission.VOICE_SPEAK, Permission.PRIORITY_SPEAKER, Permission.VOICE_MUTE_OTHERS, Permission.VOICE_DEAF_OTHERS, Permission.VOICE_MOVE_OTHERS), null)
                .queue();

        event.getGuild().moveVoiceMember(event.getMember(), channel).queue();
    }

}
