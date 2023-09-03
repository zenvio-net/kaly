package dev.qlick.java.commands.tickets;

import lombok.Getter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.emoji.CustomEmoji;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class SendTicketEmbedCommand extends ListenerAdapter {
    @Getter
    private static final CommandData commandData = Commands.slash("ticket_embed", "Sends the Embed to create a ticket.")
            .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MANAGE_SERVER));

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (!event.getInteraction().getName().equals(commandData.getName())) return;

        MessageEmbed embed = new EmbedBuilder()
                .setTitle("Create a Ticket")
                .setDescription("Click the button below to create a ticket.")
                .setColor(new Color(90, 5, 227))
                .setFooter("Made by qlick", "https://raw.githubusercontent.com/qlick/.github/main/assets/Logo.png")
                .build();

        Button button = Button.primary("create_ticket", Emoji.fromUnicode("U+1F3AB"));

        event.replyEmbeds(embed).addActionRow(button).queue();
    }
}
