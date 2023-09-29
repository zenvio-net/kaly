package net.vortic.java.commands.admin.products;

import lombok.Getter;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.middleman.GuildMessageChannel;
import net.dv8tion.jda.api.entities.channel.middleman.StandardGuildMessageChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.components.ItemComponent;
import net.vortic.java.Secrets;
import net.vortic.java.embeds.ProductEmbeds;
import net.vortic.java.types.Tuple;

import java.util.concurrent.TimeUnit;

public class SendProductEmbedCommand extends ListenerAdapter {

    @Getter
    private static final CommandData commandData = Commands.slash("send_product_embed", "Sends the Product Embed(s).")
            .addOption(OptionType.CHANNEL, "channel", "The channel to send this in.", false)
            .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MANAGE_SERVER, Permission.BAN_MEMBERS));

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getInteraction().getName().equals(commandData.getName())) return;

        Tuple<MessageEmbed, ItemComponent[]> embed = ProductEmbeds.get("discord_bot");

        if (event.getOption("channel") == null)
            event.replyEmbeds(embed.getFirst()).addActionRow(embed.getSecond()).queue();
        else if (event.getOption("channel").getAsChannel() instanceof StandardGuildMessageChannel) {
            ((StandardGuildMessageChannel) event.getOption("channel").getAsChannel()).sendMessageEmbeds(embed.getFirst()).addActionRow(embed.getSecond()).queue();
            event.reply(String.format("Send the Product Embed(s) into the channel %s.", event.getOption("channel").getAsChannel().getAsMention())).setEphemeral(true).queue();
        }
        else
            event.reply("The channel you specified is not a text channel.").setEphemeral(true).queue();
    }

}
