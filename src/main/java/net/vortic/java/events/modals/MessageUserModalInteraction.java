package net.vortic.java.events.modals;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class MessageUserModalInteraction extends ListenerAdapter {

    @Override
    public void onModalInteraction(ModalInteractionEvent event) {
        if (!event.getModalId().startsWith("message_user_modal_")) return;

        String message = event.getValue("message_user_message").getAsString();
        User target = event.getGuild().retrieveMemberById(event.getModalId().split("_")[3]).complete().getUser();

        MessageEmbed embed = new EmbedBuilder()
                .setTitle(String.format("%s send you a message!", event.getUser().getName()))
                .setColor(Color.ORANGE)
                .addField("Sender", event.getUser().getAsMention(), true)
                .addField("Guild", String.format("[%s](https://discord.gg/VtZVJqHEUE)", event.getGuild().getName()), true)
                .addBlankField(true)
                .addField("Message", message, true)
                .setFooter("Made by vortic", "https://cdn.discordapp.com/icons/1120792476723191980/feaf9e89ed168bd22cb144ada0b68b42.webp?size=1024&width=0&height=256")
                .build();

        target.openPrivateChannel().flatMap(channel -> channel.sendMessageEmbeds(embed)).queue();
        event.reply(String.format("Successfully send your message (||\"`%s`\"||) to <@%s>", message, target.getId())).setEphemeral(true).queue();
    }
}
