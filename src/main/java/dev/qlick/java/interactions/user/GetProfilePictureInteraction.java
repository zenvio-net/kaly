package dev.qlick.java.interactions.user;

import lombok.Getter;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

public class GetProfilePictureInteraction extends ListenerAdapter {

    @Getter
    private static final CommandData commandData = Commands.user("Get Profile Picture");

    @Override
    public void onUserContextInteraction(UserContextInteractionEvent event) {
        if (!event.getName().equals(commandData.getName())) return;
        event.reply(event.getTarget().getEffectiveAvatarUrl()).queue();
    }

}
