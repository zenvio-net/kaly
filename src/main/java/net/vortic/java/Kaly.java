package net.vortic.java;

import com.stripe.Stripe;
import net.vortic.java.commands.admin.BanCommand;
import net.vortic.java.commands.admin.KickCommand;
import net.vortic.java.commands.admin.products.SendProductEmbedCommand;
import net.vortic.java.commands.other.ReportCommand;
import net.vortic.java.commands.bot.SetActivityCommand;
import net.vortic.java.commands.tickets.SendTicketEmbedCommand;
import net.vortic.java.commands.tickets.TicketCloseCommand;
import net.vortic.java.events.buttons.products.discord_bots.DiscordBotBuyButtonsInteractionEvent;
import net.vortic.java.events.buttons.welcome_message.BanButtonInteractionEvent;
import net.vortic.java.events.buttons.welcome_message.KickButtonInteractionEvent;
import net.vortic.java.events.buttons.welcome_message.MessageButtonInteractionEvent;
import net.vortic.java.events.channels.OnWaitingChannelJoinEvent;
import net.vortic.java.events.guild.OnGuildMemberJoinEvent;
import net.vortic.java.events.modals.MessageUserModalInteraction;
import net.vortic.java.events.modals.SetActivityModalInteraction;
import net.vortic.java.events.modals.TicketCreateModalInteraction;
import net.vortic.java.events.modals.products.discord.DiscordBotPayModalInteraction;
import net.vortic.java.events.ready.KalyGuildReadyEvent;
import net.vortic.java.events.ready.KalyReadyEvent;
import net.vortic.java.events.messages.KalyMessageDeleteEvent;
import net.vortic.java.events.messages.KalyMessageReceivedEvent;
import net.vortic.java.events.messages.KalyMessageUpdateEvent;
import net.vortic.java.events.modals.ReportModalInteraction;
import net.vortic.java.events.buttons.tickets.CreateTicketButtonInteractionEvent;
import net.vortic.java.events.selectmenus.products.discord_bot.OnDiscordBotBuySelectMenuInteractionEvent;
import net.vortic.java.interactions.messages.ReportMessageInteraction;
import lombok.Getter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import net.vortic.java.interactions.user.BanUserInteraction;
import net.vortic.java.interactions.user.GetProfilePictureInteraction;
import net.vortic.java.interactions.user.KickUserInteraction;
import net.vortic.java.interactions.user.ReportUserInteraction;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class Kaly {

    @Getter
    private static final String path = Kaly.class.getProtectionDomain().getCodeSource().getLocation().getPath();

    @Getter
    private static final Map<String, Message> messages = new HashMap<>();

    public static void main(String[] args) throws InterruptedException, URISyntaxException {
        Stripe.apiKey = Secrets.getStripeToken();

        JDA jda = JDABuilder.createDefault(Secrets.getToken())
                .disableCache(CacheFlag.ACTIVITY, CacheFlag.ROLE_TAGS)
                .enableIntents(GatewayIntent.getIntents(GatewayIntent.ALL_INTENTS))
                .addEventListeners(
                        // Commands
                        new SendProductEmbedCommand(),
                        new BanCommand(), new KickCommand(),

                        new SetActivityCommand(),

                        new ReportCommand(),

                        new SendTicketEmbedCommand(), new TicketCloseCommand(),

                        // Interactions
                        new BanUserInteraction(), new GetProfilePictureInteraction(), new KickUserInteraction(), new ReportUserInteraction(),

                        new ReportMessageInteraction(),

                        // Events
                        new DiscordBotBuyButtonsInteractionEvent(),
                        new CreateTicketButtonInteractionEvent(),
                        new BanButtonInteractionEvent(), new KickButtonInteractionEvent(), new MessageButtonInteractionEvent(),

                        new OnWaitingChannelJoinEvent(),

                        new OnGuildMemberJoinEvent(),

                        new KalyMessageDeleteEvent(), new KalyMessageReceivedEvent(), new KalyMessageUpdateEvent(),

                        new DiscordBotPayModalInteraction(),
                        new MessageUserModalInteraction(), new ReportModalInteraction(), new SetActivityModalInteraction(), new TicketCreateModalInteraction(),

                        new KalyReadyEvent(), new KalyGuildReadyEvent(),

                        new OnDiscordBotBuySelectMenuInteractionEvent()
                )
                .setActivity(Activity.watching("qlick coding"))
                .build();

        jda.awaitReady();
    }
}
