package dev.qlick.java.events.tickets.buttons;

import dev.qlick.java.Secrets;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.selections.SelectMenu;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.EnumSet;

public class CreateTicketButtonInteractionEvent extends ListenerAdapter {

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        if (!event.getComponentId().equals("create_ticket")) return;

        TextInput type = TextInput.create("ticket-type", "Type (Bug, Feature Request, Support, Other)", TextInputStyle.SHORT)
                .setMinLength(3)
                .setMaxLength(500)
                .setRequired(true)
                .build();

        TextInput problem = TextInput.create("ticket-problem", "Problem", TextInputStyle.PARAGRAPH)
                .setMinLength(5)
                .setMaxLength(400)
                .setRequired(true)
                .build();

        event.replyModal(Modal.create("ticket-modal", "Create a Ticket")
                .addActionRow(type)
                .addActionRow(problem)
                .build()).queue();
    }
}
