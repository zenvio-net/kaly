package net.vortic.java.events.buttons.tickets;

import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;
import org.jetbrains.annotations.NotNull;


public class CreateTicketButtonInteractionEvent extends ListenerAdapter {

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        if (!event.getComponentId().equals("create_ticket")) return;

        TextInput type = TextInput.create("ticket_type", "Type (Bug, Feature Request, Support, Other)", TextInputStyle.SHORT)
                .setMinLength(3)
                .setMaxLength(500)
                .setRequired(true)
                .build();

        TextInput problem = TextInput.create("ticket_problem", "Problem", TextInputStyle.PARAGRAPH)
                .setMinLength(5)
                .setMaxLength(400)
                .setRequired(true)
                .build();

        event.replyModal(Modal.create("ticket_modal", "Create a Ticket")
                .addActionRow(type)
                .addActionRow(problem)
                .build()).queue();
    }
}
