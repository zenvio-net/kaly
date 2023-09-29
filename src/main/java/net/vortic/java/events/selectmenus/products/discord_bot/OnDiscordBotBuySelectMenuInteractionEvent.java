package net.vortic.java.events.selectmenus.products.discord_bot;

import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;

public class OnDiscordBotBuySelectMenuInteractionEvent extends ListenerAdapter {

    @Override
    public void onStringSelectInteraction(StringSelectInteractionEvent event) {
        if (!event.getComponentId().startsWith("product_db_buy")) return;

        String packageId = event.getComponentId().split("_")[3];
        boolean hosting = event.getSelectedOptions().get(0).getValue().equals("yes");

        event.replyModal(
                Modal.create(String.format("product_db_buy_cc_%s%s", packageId, hosting ? "_hosting" : ""), "Pay your Discord Bot")
                        .addActionRow(
                                TextInput.create("product_db_card_number", "Credit Card Number", TextInputStyle.SHORT)
                                        .setPlaceholder("1111 2222 3333 4444")
                                        .setMinLength(16)
                                        .setMaxLength(19)
                                        .setRequired(true)
                                        .build()
                        )
                        .addActionRow(
                                TextInput.create("product_db_card_expiration", "Credit Card Expiration", TextInputStyle.SHORT)
                                        .setPlaceholder("MM/YY")
                                        .setMinLength(5)
                                        .setMaxLength(5)
                                        .setRequired(true)
                                        .build()
                        )
                        .addActionRow(
                                TextInput.create("product_db_card_cvc", "Credit Card Security Code", TextInputStyle.SHORT)
                                        .setPlaceholder("123")
                                        .setMinLength(3)
                                        .setMaxLength(3)
                                        .setRequired(true)
                                        .build()
                        )
                        .build()
        ).queue();
    }
}
