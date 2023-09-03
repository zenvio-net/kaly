package dev.qlick.java;

import dev.qlick.java.commands.admin.BanCommand;
import dev.qlick.java.commands.admin.KickCommand;
import dev.qlick.java.commands.other.ReportCommand;
import dev.qlick.java.commands.bot.SetActivityCommand;
import dev.qlick.java.commands.tickets.SendTicketEmbedCommand;
import dev.qlick.java.commands.tickets.TicketCloseCommand;
import dev.qlick.java.events.modals.KalySetActivityModalInteraction;
import dev.qlick.java.events.modals.KalyTicketCreateModalInteraction;
import dev.qlick.java.events.ready.KalyGuildReadyEvent;
import dev.qlick.java.events.ready.KalyReadyEvent;
import dev.qlick.java.events.messages.KalyMessageDeleteEvent;
import dev.qlick.java.events.messages.KalyMessageReceivedEvent;
import dev.qlick.java.events.messages.KalyMessageUpdateEvent;
import dev.qlick.java.events.modals.KalyReportModalInteraction;
import dev.qlick.java.events.tickets.buttons.CreateTicketButtonInteractionEvent;
import dev.qlick.java.interactions.messages.ReportMessageInteraction;
import dev.qlick.java.interactions.user.*;
import lombok.Getter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class Kaly {

    @Getter
    private static final String path = Kaly.class.getProtectionDomain().getCodeSource().getLocation().getPath();

    @Getter
    private static final Map<String, Message> messages = new HashMap<>();

    public static void main(String[] args) throws InterruptedException, URISyntaxException {
        JDA jda = JDABuilder.createDefault(Secrets.getToken())
                .disableCache(CacheFlag.ACTIVITY, CacheFlag.VOICE_STATE, CacheFlag.ROLE_TAGS)
                .enableIntents(GatewayIntent.getIntents(GatewayIntent.ALL_INTENTS))
                .addEventListeners(
                        // Commands
                        new BanCommand(), new KickCommand(),
                        new SetActivityCommand(),
                        new ReportCommand(),
                        new SendTicketEmbedCommand(), new TicketCloseCommand(),

                        // Interactions
                        new BanUserInteraction(), new GetProfilePictureInteraction(), new KickUserInteraction(), new ReportUserInteraction(),

                        new ReportMessageInteraction(),

                        // Events
                        new KalyMessageDeleteEvent(), new KalyMessageReceivedEvent(), new KalyMessageUpdateEvent(),
                        new KalyReportModalInteraction(), new KalySetActivityModalInteraction(), new KalyTicketCreateModalInteraction(),
                        new KalyReadyEvent(), new KalyGuildReadyEvent(),
                        new CreateTicketButtonInteractionEvent()
                )
                .setActivity(Activity.watching("qlick coding"))
                .build();

        jda.awaitReady();
    }
}
