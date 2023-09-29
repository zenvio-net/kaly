package net.vortic.java.interactions.user;

import net.vortic.java.Modals;
import lombok.Getter;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

public class ReportUserInteraction extends ListenerAdapter {

    @Getter
    private static final CommandData commandData = Commands.user("Report");

    @Override
    public void onUserContextInteraction(UserContextInteractionEvent event) {
        if (!event.getName().equals(commandData.getName())) return;
        var target = event.getTarget();
        event.replyModal(Modals.getReportModal(target)).queue();
    }

}
