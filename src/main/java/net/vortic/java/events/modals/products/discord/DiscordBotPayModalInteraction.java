package net.vortic.java.events.modals.products.discord;

import com.stripe.exception.CardException;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethod;
import com.stripe.param.PaymentIntentConfirmParams;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.PaymentMethodCreateParams;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.vortic.java.Pricing;
import net.vortic.java.Secrets;
import net.vortic.java.types.Tuple;

import java.awt.*;
import java.util.EnumSet;

public class DiscordBotPayModalInteraction extends ListenerAdapter {

    @Override
    public void onModalInteraction(ModalInteractionEvent event) {
        if (!event.getModalId().startsWith("product_db_buy_cc")) return;

        String[] split = event.getModalId().split("_");
        String packageId = split[4];
        boolean hosting = split.length == 6;
        Tuple<Integer, Integer> pricing = Pricing.getDiscordBotPricing(packageId);

        try {
            String[] cardExpiration = event.getValue("product_db_card_expiration").getAsString().split("/");
            PaymentMethod paymentMethod = PaymentMethod.create(
                    PaymentMethodCreateParams.builder()
                            .setType(PaymentMethodCreateParams.Type.CARD)
                            .setCard(
                                    PaymentMethodCreateParams.CardDetails.builder()
                                            .setNumber(event.getValue("product_db_card_number").getAsString())
                                            .setExpMonth(Long.parseLong(cardExpiration[0]))
                                            .setExpYear(Long.parseLong(cardExpiration[1]))
                                            .setCvc(event.getValue("product_db_card_cvc").getAsString())
                                            .build()
                            )
                            .build()
            );
            PaymentIntent.create(
                    PaymentIntentCreateParams.builder()
                            .setAmount(Long.valueOf(pricing.getFirst()))
                            .setCurrency("eur")
                            .build()
            ).confirm(
                    PaymentIntentConfirmParams.builder()
                            .setPaymentMethod(paymentMethod.getId())
                            .build()
            );

            event.getGuild().createTextChannel(String.format("bought-db-%s", packageId)).queue(channel -> {
                channel.getManager().setTopic(String.format("[**Discord Bot %s**] - %s bought a Discord Bot.", packageId.substring(0, 1).toUpperCase() + packageId.substring(1), event.getUser().getName()))
                        .putPermissionOverride(event.getMember(), EnumSet.of(Permission.VIEW_CHANNEL, Permission.MESSAGE_SEND), null)
                        .putPermissionOverride(event.getGuild().getPublicRole(), null, EnumSet.of(Permission.VIEW_CHANNEL, Permission.MESSAGE_SEND))
                        .putPermissionOverride(event.getGuild().getRoleById(Secrets.getTeamRoleId()), EnumSet.of(Permission.VIEW_CHANNEL, Permission.MESSAGE_SEND), null)
                        .setParent(event.getGuild().getCategoryById(Secrets.getBoughtProductDiscussionCategoryId()))
                        .queue();

                MessageEmbed embed = new EmbedBuilder()
                        .setTitle("Ticket created!")
                        .setColor(new Color(90, 5, 227))
                        .addField("User", event.getUser().getAsMention(), true)
                        .addField("Package", packageId, true)
                        .addField("With hosting", (hosting + "").replace("false", "No").replace("true", "Yes"), false)
                        .setFooter("Made by qlick", "https://raw.githubusercontent.com/qlick/.github/main/assets/Logo.png")
                        .build();

                channel.sendMessageEmbeds(embed).queue();
                event.reply("Thank you for your purchase!")
                        .setEphemeral(true)
                        .queue();
            });
        } catch (StripeException exception) {
            event.reply(String.format("Something didn't work. \n> %s", exception.getUserMessage()))
                    .setEphemeral(true)
                    .queue();
        }
    }
}
