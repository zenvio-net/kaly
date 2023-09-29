package net.vortic.java.interactions.messages;

import net.vortic.java.Secrets;
import lombok.Getter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;

import java.awt.*;

public class ReportMessageInteraction extends ListenerAdapter {

    @Getter
    private static final CommandData commandData = Commands.message("Report");

    @Override
    public void onMessageContextInteraction(MessageContextInteractionEvent event) {
        if (!event.getName().equals(commandData.getName())) return;

        var message = event.getTarget();
        var target = message.getAuthor();

        TextInput reason = TextInput.create("report_message_reason", "Reason", TextInputStyle.SHORT)
                .setMinLength(5)
                .setMaxLength(500)
                .setRequired(true)
                .build();

        event.replyModal(Modal.create(
                String.format("report_message_modal_%s_%s_%s_%s", target.getId(), message.getGuild().getId(), message.getChannel().getId(), message.getId()),
                        String.format("Report %s", target.getName()))
                .addActionRow(reason)
                .build()).queue();
    }

    @Override
    public void onModalInteraction(ModalInteractionEvent event) {
        if (!event.getModalId().startsWith("report_message_modal_")) return;

        String[] splitModalId = event.getModalId().split("_");
        User target = event.getGuild().retrieveMemberById(splitModalId[3]).complete().getUser();

        String message = String.format("https://discord.com/channels/%s/%s/%s", splitModalId[4], splitModalId[5], splitModalId[6]);

        MessageEmbed embed = new EmbedBuilder()
                .setTitle("Someone got reported!")
                .setThumbnail("https://raw.githubusercontent.com/qlick/.github/main/assets/report_alarm_image.png")
                .setColor(Color.red)
                .addField("Reported by", event.getUser().getAsMention(), true)
                .addField("Reported user", target.getAsMention(), true)
                .addField("Message", message, true)
                .addField("Reported at", String.format("<t:%s>", event.getInteraction().getTimeCreated().toInstant().toEpochMilli()), true)
                .addField("Reason", event.getValue("report_message_reason").getAsString(), true)
                .setFooter("Made by qlick", "https://raw.githubusercontent.com/qlick/.github/main/assets/Logo.png")
                .build();

        event.getGuild().getTextChannelById(Secrets.getReportChannelId()).sendMessageEmbeds(embed).queue();
        event.reply(String.format("Successfully reported user <@%s>", target.getId())).setEphemeral(true).queue();
    }

}
