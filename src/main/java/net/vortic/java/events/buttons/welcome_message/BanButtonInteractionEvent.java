package net.vortic.java.events.buttons.welcome_message;

import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class BanButtonInteractionEvent extends ListenerAdapter {

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        if (!event.getComponentId().startsWith("ban_welcome_message")) return;

        event.getGuild().retrieveMemberById(event.getComponentId().split("_")[3]).onSuccess(member -> {
            member.ban(0, TimeUnit.DAYS).queue();
            event.reply(String.format("Successfully banned the User %s!", member.getAsMention())).setEphemeral(true).queue();
            event.getMessage().delete().queue();
        }).queue();
    }
}