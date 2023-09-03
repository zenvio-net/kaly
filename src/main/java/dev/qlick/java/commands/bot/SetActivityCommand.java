package dev.qlick.java.commands.bot;

import lombok.Getter;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;

import java.util.Arrays;
import java.util.List;

public class SetActivityCommand extends ListenerAdapter {

    @Getter
    private static final CommandData commandData = Commands.slash("set_activity", "Changes the Activity of Kaly")
            .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MANAGE_SERVER, Permission.BAN_MEMBERS));

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getName().equals(commandData.getName())) return;

        TextInput reason = TextInput.create("activity-text", "Activity Text", TextInputStyle.SHORT)
                .setMinLength(5)
                .setMaxLength(100)
                .setRequired(true)
                .setValue(event.getJDA().getPresence().getActivity().getName())
                .build();

        TextInput messageURL = TextInput.create("activity-type", "Activity Type", TextInputStyle.SHORT)
                .setMinLength(5)
                .setMaxLength(10)
                .setRequired(true)
                .setValue(event.getJDA().getPresence().getActivity().getType().name().toUpperCase())
                .build();

        event.replyModal(Modal.create("activity-modal", "Change Activity of Kaly")
                .addActionRow(reason)
                .addActionRow(messageURL)
                .build()).queue();
    }

}
