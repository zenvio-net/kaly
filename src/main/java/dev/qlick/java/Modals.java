package dev.qlick.java;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;

public class Modals {

    public static Modal getReportModal(User user) {
        TextInput reason = TextInput.create("report-reason", "Reason", TextInputStyle.SHORT)
                .setMinLength(5)
                .setMaxLength(500)
                .setRequired(true)
                .build();

        TextInput messageURL = TextInput.create("report-messageURL", "Message URL", TextInputStyle.PARAGRAPH)
                .setMinLength(40)
                .setMaxLength(100)
                .setRequired(false)
                .build();

        return Modal.create(String.format("report-modal-%s", user.getId()), String.format("Report %s", user.getName()))
                .addActionRow(reason)
                .addActionRow(messageURL)
                .build();
    }

}
