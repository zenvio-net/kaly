package net.vortic.java.events.buttons.welcome_message;

import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class KickButtonInteractionEvent extends ListenerAdapter {

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        if (!event.getComponentId().startsWith("kick_welcome_message")) return;

        event.getGuild().retrieveMemberById(event.getComponentId().split("_")[3]).flatMap(member -> {
            member.kick().queue();
            event.reply(String.format("Successfully kicked the User %s!", member.getAsMention())).setEphemeral(true).queue();
            return null;
        }).queue();
    }
}