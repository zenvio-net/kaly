package dev.qlick.java.events.messages;

import dev.qlick.java.Kaly;
import dev.qlick.java.Secrets;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageDeleteEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class KalyMessageDeleteEvent extends ListenerAdapter {

    @Override
    public void onMessageDelete(@NotNull MessageDeleteEvent event) {
        if (!Kaly.getMessages().containsKey(event.getMessageId())) return;

        Message message = Kaly.getMessages().get(event.getMessageId());
        MessageEmbed embed = new EmbedBuilder()
                .setTitle("Message Deleted")
                .setColor(Color.red)
                .addField("Author", message.getAuthor().getAsMention(), true)
                .addField("Channel", message.getChannel().getAsMention(), true)
                .addBlankField(true)
                .addField("Message", message.getContentRaw(), true)
                .addField("Message ID", message.getId(), true)
                .setFooter("Made by qlick", "https://raw.githubusercontent.com/qlick/.github/main/assets/Logo.png")
                .build();
        event.getGuild().getTextChannelById(Secrets.getLogChannelId()).sendMessageEmbeds(embed).queue();
        Kaly.getMessages().remove(event.getMessageId());
    }

}
