package net.vortic.java.events.buttons.products.discord_bots;

import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;

public class DiscordBotBuyButtonsInteractionEvent extends ListenerAdapter {

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        if (!event.getComponentId().startsWith("product_db_buy")) return;
        String packageId = event.getComponentId().split("_")[3];

        event.reply("Do you want it with hosting?\n||REMINDER: If you don't trust us with your details, you can look at our [bots source code](https://github.com/---/kaly)||")
                .addActionRow(
                StringSelectMenu.create(String.format("product_db_buy_%s", packageId))
                        .addOption("Yes, please", "yes", "You get the bot hosted by us.")
                        .addOption("No, thanks", "no", "You need to host the bot yourself.")
                        .build()
                )
                .setEphemeral(true)
                .queue();
    }
}
