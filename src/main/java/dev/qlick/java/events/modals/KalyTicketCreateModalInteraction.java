package dev.qlick.java.events.modals;

import dev.qlick.java.Secrets;
import dev.qlick.java.enums.TicketType;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.apache.commons.lang3.EnumUtils;

import java.awt.*;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.stream.Collectors;

public class KalyTicketCreateModalInteraction extends ListenerAdapter {

    @Override
    public void onModalInteraction(ModalInteractionEvent event) {
        if (!event.getModalId().equals("ticket-modal")) return;

        String type = event.getValue("ticket-type").getAsString().toUpperCase();

        if (!EnumUtils.isValidEnum(TicketType.class, type.replace(" ", "_"))) {
            event.reply(String.format("`%s` is not a valid Ticket Type! \n Choose from those: %s", type, Arrays.stream(TicketType.values()).map(Enum::toString).collect(Collectors.joining(", ")).replace("_", " "))).setEphemeral(true).queue();
            return;
        }

        String problem = event.getValue("ticket-problem").getAsString();

        TextChannel channel = event.getGuild().createTextChannel(String.format("ticket-%s-%s", type, event.getUser().getName())).complete();
        channel.getManager()
                .setTopic(String.format("[**%s**] - Ticket created by %s", type, event.getUser().getName()))
                .putPermissionOverride(event.getMember(), EnumSet.of(Permission.VIEW_CHANNEL, Permission.MESSAGE_SEND), null)
                .putPermissionOverride(event.getGuild().getPublicRole(), null, EnumSet.of(Permission.VIEW_CHANNEL, Permission.MESSAGE_SEND))
                .putPermissionOverride(event.getGuild().getRoleById(Secrets.getTeamRoleId()), EnumSet.of(Permission.VIEW_CHANNEL, Permission.MESSAGE_SEND), null)
                .queue();

        MessageEmbed embed = new EmbedBuilder()
                .setTitle("Ticket created!")
                .setColor(new Color(90, 5, 227))
                .addField("User", event.getUser().getAsMention(), true)
                .addField("Type", type, true)
                .addField("Problem", problem, false)
                .setFooter("Made by qlick", "https://raw.githubusercontent.com/qlick/.github/main/assets/Logo.png")
                .build();

        channel.sendMessageEmbeds(embed).queue();
        event.reply(String.format("Ticket created! <#%s>", channel.getId())).setEphemeral(true).queue();
    }
}
