package dev.qlick.java.events.messages;

import dev.qlick.java.Kaly;
import dev.qlick.java.Secrets;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class KalyMessageUpdateEvent extends ListenerAdapter {

    @Override
    public void onMessageUpdate(@NotNull MessageUpdateEvent event) {
        if (event.getAuthor().isBot()) return;

        Message message = Kaly.getMessages().get(event.getMessageId());

        MessageEmbed embed = new EmbedBuilder()
                .setTitle("New Message Received")
                .setColor(new Color(254, 102, 1))
                .addField("Author", message.getAuthor().getAsMention(), true)
                .addField("Channel", message.getChannel().getAsMention(), true)
                .addBlankField(true)
                .addField("Message ID", message.getId(), true)
                .addField("Original Message", message.getContentRaw(), true)
                .addField("Edited Message", event.getMessage().getContentRaw(), true)
                .setFooter("Made by qlick", "https://raw.githubusercontent.com/qlick/.github/main/assets/Logo.png")
                .build();

        event.getGuild().getTextChannelById(Secrets.getLogChannelId()).sendMessageEmbeds(embed).queue();
    }

}
