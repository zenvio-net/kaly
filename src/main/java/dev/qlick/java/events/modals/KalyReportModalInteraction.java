package dev.qlick.java.events.modals;

import dev.qlick.java.Kaly;
import dev.qlick.java.Secrets;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class KalyReportModalInteraction extends ListenerAdapter {

    @Override
    public void onModalInteraction(ModalInteractionEvent event) {
        if (!event.getModalId().startsWith("report-modal-")) return;

        User target = event.getGuild().retrieveMemberById(event.getModalId().split("-")[2]).complete().getUser();

        String message = event.getValue("report-messageURL").getAsString();
        if (message.isBlank() || message.isEmpty()) message = "No message provided";

        MessageEmbed embed = new EmbedBuilder()
                .setTitle("Someone got reported!")
                .setThumbnail("https://raw.githubusercontent.com/qlick/.github/main/assets/report_alarm_image.png")
                .setColor(Color.red)
                .addField("Reported by", event.getUser().getAsMention(), true)
                .addField("Reported user", target.getAsMention(), true)
                .addField("Message", message, true)
                .addField("Reported at", String.format("<t:%s>", event.getInteraction().getTimeCreated().toInstant().toEpochMilli()), true)
                .addField("Reason", event.getValue("report-reason").getAsString(), true)
                .setFooter("Made by qlick", "https://raw.githubusercontent.com/qlick/.github/main/assets/Logo.png")
                .build();

        event.getGuild().getTextChannelById(Secrets.getReportChannelId()).sendMessageEmbeds(embed).queue();
        event.reply(String.format("Successfully reported user <@%s>", target.getId())).setEphemeral(true).queue();
    }
}
