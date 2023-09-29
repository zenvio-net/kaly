package net.vortic.java.events.modals;

import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Arrays;

public class SetActivityModalInteraction extends ListenerAdapter {

    @Override
    public void onModalInteraction(ModalInteractionEvent event) {
        if (!event.getModalId().equals("activity_modal")) return;

        String activity = event.getValue("activity_type").getAsString().toUpperCase();
        if (Arrays.asList(Activity.ActivityType.values()).contains(activity)) {
            event.reply(String.format("`%s` is not a valid Activity Type! \n Choose from those: %s", activity, "WATCHING, PLAYING, LISTENING, STREAMING, COMPETING")).setEphemeral(true).queue();
            return;
        }

        event.getJDA().getPresence().setActivity(
                Activity.of(
                        Activity.ActivityType.valueOf(activity),
                        event.getValue("activity_text").getAsString()
                )
        );

        event.reply(String.format(
                "Successfully changed activity of Kaly to `%s` `%s`",
                event.getValue("activity_type").getAsString().toUpperCase(),
                event.getValue("activity_text").getAsString()
        )).setEphemeral(true).queue();
    }

}
