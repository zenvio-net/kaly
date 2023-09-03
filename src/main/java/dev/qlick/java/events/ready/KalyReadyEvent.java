package dev.qlick.java.events.ready;


import dev.qlick.java.Secrets;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class KalyReadyEvent extends ListenerAdapter {

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        MessageEmbed embed = new EmbedBuilder()
                .setTitle(":green_circle: Ready!")
                .setColor(new Color(90, 5, 227))
                .setFooter("Made by qlick", "https://raw.githubusercontent.com/qlick/.github/main/assets/Logo.png")
                .build();

        event.getJDA().getTextChannelById(Secrets.getBotStatusChannelId()).sendMessageEmbeds(embed).queue();
    }
}
