package net.vortic.java.events.ready;

import net.vortic.java.commands.admin.BanCommand;
import net.vortic.java.commands.admin.KickCommand;
import net.vortic.java.commands.admin.products.SendProductEmbedCommand;
import net.vortic.java.commands.other.ReportCommand;
import net.vortic.java.commands.bot.SetActivityCommand;
import net.vortic.java.commands.tickets.SendTicketEmbedCommand;
import net.vortic.java.commands.tickets.TicketCloseCommand;
import net.vortic.java.interactions.messages.ReportMessageInteraction;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.vortic.java.interactions.user.BanUserInteraction;
import net.vortic.java.interactions.user.GetProfilePictureInteraction;
import net.vortic.java.interactions.user.KickUserInteraction;
import net.vortic.java.interactions.user.ReportUserInteraction;
import org.jetbrains.annotations.NotNull;

public class KalyGuildReadyEvent extends ListenerAdapter {

    @Override
    public void onGuildReady(@NotNull GuildReadyEvent event) {
        event.getGuild().updateCommands().addCommands(
                // Commands
                SendProductEmbedCommand.getCommandData(),
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
