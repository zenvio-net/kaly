package dev.qlick.java.events.modals;

import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.apache.commons.lang3.EnumUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class KalySetActivityModalInteraction extends ListenerAdapter {

    @Override
    public void onModalInteraction(ModalInteractionEvent event) {
        if (!event.getModalId().equals("activity-modal")) return;

        String activity = event.getValue("activity-type").getAsString().toUpperCase();
        if (EnumUtils.isValidEnum(Activity.ActivityType.class, activity)) {
            event.reply(String.format("`%s` is not a valid Activity Type! \n Choose from those: %s", activity, Arrays.stream(Activity.ActivityType.values()).map(Enum::toString).collect(Collectors.joining(", ")))).setEphemeral(true).queue();
            return;
        }

        event.getJDA().getPresence().setActivity(
                Activity.of(
                        Activity.ActivityType.valueOf(activity),
                        event.getValue("activity-text").getAsString()
                )
        );

        event.reply(String.format(
                "Successfully changed activity of Kaly to `%s` `%s`",
                event.getValue("activity-type").getAsString().toUpperCase(),
                event.getValue("activity-text").getAsString()
        )).setEphemeral(true).queue();
    }

}
