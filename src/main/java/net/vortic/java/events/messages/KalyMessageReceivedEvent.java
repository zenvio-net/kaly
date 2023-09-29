package net.vortic.java.events.messages;

import net.vortic.java.Kaly;
import net.vortic.java.Secrets;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class KalyMessageReceivedEvent extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;

        Kaly.getMessages().put(event.getMessageId(), event.getMessage());

        String message = event.getMessage().getContentRaw();

        MessageEmbed embed = new EmbedBuilder()
                .setTitle("New Message Received")
                .setColor(Color.green)
                .addField("Author", event.getAuthor().getAsMention(), true)
                .addField("Channel", event.getChannel().getAsMention(), true)
                .addBlankField(true)
                .addField("Message", String.format("```\n%s\n```", message), true)
                .addField("Message ID", event.getMessageId(), true)
                .setFooter("Made by qlick", "https://raw.githubusercontent.com/qlick/.github/main/assets/Logo.png")
                .build();

        event.getGuild().getTextChannelById(Secrets.getLogChannelId()).sendMessageEmbeds(embed).queue();
    }

}
