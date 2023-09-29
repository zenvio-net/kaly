package net.vortic.java.events.messages;

import net.vortic.java.Kaly;
import net.vortic.java.Secrets;
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
                .setColor(Color.orange)
                .addField("Author", message.getAuthor().getAsMention(), true)
                .addField("Channel", message.getChannel().getAsMention(), true)
                .addBlankField(true)
                .addField("Message ID", message.getId(), true)
                .addField("Message", String.format("```\n%s\n```", message.getContentRaw()), true)
                .addField("Message", String.format("```\n%s\n```", event.getMessage().getContentRaw()), true)
                .setFooter("Made by qlick", "https://raw.githubusercontent.com/qlick/.github/main/assets/Logo.png")
                .build();

        event.getGuild().getTextChannelById(Secrets.getLogChannelId()).sendMessageEmbeds(embed).queue();
    }

}
