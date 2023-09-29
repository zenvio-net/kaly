package net.vortic.java.events.modals;

import net.vortic.java.Secrets;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class ReportModalInteraction extends ListenerAdapter {

    @Override
    public void onModalInteraction(ModalInteractionEvent event) {
        if (!event.getModalId().startsWith("report_modal_")) return;

        User target = event.getGuild().retrieveMemberById(event.getModalId().split("_")[2]).complete().getUser();

        String message = event.getValue("report_messageURL").getAsString();
        if (message.isBlank() || message.isEmpty()) message = "No message provided";

        MessageEmbed embed = new EmbedBuilder()
                .setTitle("Someone got reported!")
                .setThumbnail("https://raw.githubusercontent.com/qlick/.github/main/assets/report_alarm_image.png")
                .setColor(Color.red)
                .addField("Reported by", event.getUser().getAsMention(), true)
                .addField("Reported user", target.getAsMention(), true)
                .addField("Message", message, true)
                .addField("Reason", event.getValue("report_reason").getAsString(), true)
                .setFooter("Made by vortic", "https://cdn.discordapp.com/icons/1120792476723191980/feaf9e89ed168bd22cb144ada0b68b42.webp?size=1024&width=0&height=256")
                .build();

        event.getGuild().getTextChannelById(Secrets.getReportChannelId()).sendMessageEmbeds(embed).queue();
        event.reply(String.format("Successfully reported user <@%s>", target.getId())).setEphemeral(true).queue();
    }
}
