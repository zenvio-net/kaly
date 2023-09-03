package dev.qlick.java.commands.tickets;

import lombok.Getter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class TicketCloseCommand extends ListenerAdapter {
    @Getter
    private static final CommandData commandData = Commands.slash("close_ticket", "Closes the current ticket.")
            .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MANAGE_SERVER));

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (!event.getInteraction().getName().equals(commandData.getName())) return;

        if (!event.getChannel().getName().startsWith("ticket-")) {
            event.reply("You can only use this command in a ticket channel.").setEphemeral(true).queue();
            return;
        }

        event.getChannel().delete().queue();
    }
}
