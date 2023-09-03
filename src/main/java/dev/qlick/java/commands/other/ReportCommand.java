package dev.qlick.java.commands.other;

import dev.qlick.java.Modals;
import lombok.Getter;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

public class ReportCommand extends ListenerAdapter {

    @Getter
    private static final CommandData commandData = Commands.slash("report", "Report a User")
            .addOption(OptionType.USER, "user", "The user to report", true);

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getInteraction().getName().equals(commandData.getName())) return;
        var target = event.getOption("user").getAsUser();
        event.replyModal(Modals.getReportModal(target)).queue();
    }
}
