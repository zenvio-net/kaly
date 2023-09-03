package dev.qlick.java.events.ready;

import dev.qlick.java.commands.admin.BanCommand;
import dev.qlick.java.commands.admin.KickCommand;
import dev.qlick.java.commands.other.ReportCommand;
import dev.qlick.java.commands.bot.SetActivityCommand;
import dev.qlick.java.commands.tickets.SendTicketEmbedCommand;
import dev.qlick.java.commands.tickets.TicketCloseCommand;
import dev.qlick.java.interactions.messages.ReportMessageInteraction;
import dev.qlick.java.interactions.user.*;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class KalyGuildReadyEvent extends ListenerAdapter {

    @Override
    public void onGuildReady(@NotNull GuildReadyEvent event) {
        event.getGuild().updateCommands().addCommands(
                // Commands
                BanCommand.getCommandData(),
                KickCommand.getCommandData(),
                SetActivityCommand.getCommandData(),
                ReportCommand.getCommandData(),
                SendTicketEmbedCommand.getCommandData(),
                TicketCloseCommand.getCommandData(),

                // Interactions - User
                BanUserInteraction.getCommandData(),
                GetProfilePictureInteraction.getCommandData(),
                KickUserInteraction.getCommandData(),
                ReportUserInteraction.getCommandData(),

                // Interactions - Messages
                ReportMessageInteraction.getCommandData()
        ).queue();
    }

}
