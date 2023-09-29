package net.vortic.java.events.guild;

import net.vortic.java.Secrets;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.awt.*;
import java.time.format.DateTimeFormatter;

public class OnGuildMemberJoinEvent extends ListenerAdapter {

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        MessageEmbed embed = new EmbedBuilder()
                .setTitle("We got a new Member!")
                .setColor(Color.green)
                .addField("Name", event.getUser().getAsMention(), true)
                .addField("ID", event.getUser().getId(), true)
                .addBlankField(true)
                .addField("Avatar", event.getUser().getAvatarUrl() == null ? "Has none" : String.format("[click here](%s)", event.getUser().getAvatarUrl()), true)
                .addField("Creation date", event.getUser().getTimeCreated().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")), true)
                .setFooter("Made by vortic", "https://cdn.discordapp.com/icons/1120792476723191980/feaf9e89ed168bd22cb144ada0b68b42.webp?size=1024&width=0&height=256")
                .build();

        event.getGuild().getTextChannelById(Secrets.getWelcomeChannelId()).sendMessageEmbeds(embed).addActionRow(
                /* Ban Action */ Button.danger(String.format("ban_welcome_message_%s", event.getMember().getId()), "Ban"),
                /* Kick Action*/ Button.danger(String.format("kick_welcome_message_%s", event.getMember().getId()), "Kick"),
                /* Message Action*/ Button.primary(String.format("message_welcome_message_%s", event.getMember().getId()), "Message")
        ).queue();
    }
}
