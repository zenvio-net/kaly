package net.vortic.java.events.buttons.welcome_message;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class MessageButtonInteractionEvent extends ListenerAdapter {

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        if (!event.getComponentId().startsWith("message_welcome_message")) return;
        String id = event.getComponentId().split("_")[3];

        TextInput message = TextInput.create("message_user_message", "Your Message", TextInputStyle.SHORT)
                .setMinLength(3)
                .setMaxLength(500)
                .setRequired(true)
                .build();

        event.replyModal(Modal.create(String.format("message_user_modal_%s", id), String.format("Send %s a message",event.getGuild().retrieveMemberById(id).complete().getUser().getName()))
                .addActionRow(message)
                .build()).queue();
    }
}