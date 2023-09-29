package net.vortic.java.embeds;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.interactions.components.ItemComponent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.vortic.java.types.Tuple;

public class ProductEmbeds {

    public static Tuple<MessageEmbed, ItemComponent[]> get(String product) {
        switch (product) {
            case "discord_bot":
                return getDiscordBotEmbed();
            default:
                return null;
        }
    }

    private static Tuple<MessageEmbed, ItemComponent[]> getDiscordBotEmbed() {
        MessageEmbed embed = new EmbedBuilder()
                .setTitle("Discord Bot Packages")
                .addField("Basic", """
                        > 5 Commands
                        > 5 Events
                        > 2 Context Menus
                        > No Source Code
                        
                        Delivery Time: ***4-5 Days***
                        Price: ***€5***
                        With Hosting: ***€5*** and ***€2.50/mo***
                        """, true)
                .addField("Starter", """
                        > 8 Commands
                        > 8 Events
                        > 5 Context Menus
                        > Obfuscated Source Code
                        
                        Delivery Time: ***5-6 Days***
                        Price: ***€8***
                        With Hosting: ***€8*** and ***€5/mo***
                        """, true)
                .addField("Advanced", """
                        > 12 Commands
                        > 12 Events
                        > 8 Context Menus
                        > Obfuscated Source Code
                        
                        Delivery Time: ***6-8 Days***
                        Price: ***€12***
                        With Hosting: ***€12*** and ***€7.50/mo***
                        """, true)
                .addField("Professional", """
                        > ♾️ Commands
                        > ♾️ Events
                        > ♾️ Context Menus
                        > Source Code
                        
                        Delivery Time: ***Unknown Days***
                        Price: We discuss the price
                        With Hosting: Unknown and ***Unknown/mo***
                        """, true)
                .build();

        Button buyBasicButton = Button.primary("product_db_buy_basic", "Buy Basic");
        Button buyStarterButton = Button.primary("product_db_buy_starter", "Buy Starter");
        Button buyAdvancedButton = Button.primary("product_db_buy_advanced", "Buy Advanced");
        Button buyProfessionalButton = Button.primary("product_db_buy_professional", "Buy Professional");

        return new Tuple<>(embed, new ItemComponent[]{
                buyBasicButton, buyStarterButton, buyAdvancedButton, buyProfessionalButton
        });
    }

    public static Tuple<MessageEmbed, ItemComponent[]> getDiscordBotConfirmationEmbed(String pkg) {
        Button buyWithCreditCard = Button.primary("product_db_buy_basic", "Buy with Credit Card");
        //Button buyWithPaysafecard = Button.primary("product_db_buy_starter", "Buy with Paysafecard");

        return switch (pkg) {
            case "basic" -> new Tuple<>(
                    new EmbedBuilder()
                            .setTitle("Confirm Purchase")
                            .addField("Product", "Discord Bot - Basic", true)
                            .addField("Price", "€5, with Hosting €5 and €2.50/mo", true)
                            .addField("Product Description", """
                        > 5 Commands
                        > 5 Events
                        > 2 Context Menus
                        > No Source Code
                        
                        Delivery Time: ***4-5 Days***
                        Price: ***€5***
                        With Hosting: ***€5*** and ***€2.50/mo***
                        """, false)
                            .build(),
                    new ItemComponent[]{
                            buyWithCreditCard.withId("product_db_buy_basic_credit_card"),
                            //buyWithPaysafecard.withId("product_db_buy_basic_paysafecard")
                    }
            );
            case "starter" -> new Tuple<>(
                    new EmbedBuilder()
                            .setTitle("Confirm Purchase")
                            .addField("Product", "Discord Bot - Starter", true)
                            .addField("Price", "€8, with Hosting €8 and €5/mo", true)
                            .addField("Product Description", """
                        > 8 Commands
                        > 8 Events
                        > 5 Context Menus
                        > Obfuscated Source Code
                        
                        Delivery Time: ***5-6 Days***
                        Price: ***€8***
                        With Hosting: ***€8*** and ***€5/mo***
                        """, false)
                            .build(),
                    new ItemComponent[]{
                            buyWithCreditCard.withId("product_db_buy_starter_credit_card"),
                            //buyWithPaysafecard.withId("product_db_buy_starter_paysafecard")
                    }
            );
            case "advanced" -> new Tuple<>(
                    new EmbedBuilder()
                            .setTitle("Confirm Purchase")
                            .addField("Product", "Discord Bot - Basic", true)
                            .addField("Price", "€12, with Hosting €12 and €7.50/mo", true)
                            .addField("Product Description", """
                        > 12 Commands
                        > 12 Events
                        > 8 Context Menus
                        > Obfuscated Source Code
                        
                        Delivery Time: ***6-8 Days***
                        Price: ***€12***
                        With Hosting: ***€12*** and ***€7.50/mo***
                        """, false)
                            .build(),
                    new ItemComponent[]{
                            buyWithCreditCard.withId("product_db_buy_advanced_credit_card"),
                            //buyWithPaysafecard.withId("product_db_buy_advanced_paysafecard")
                    }
            );
            default -> null;
        };
    }
}
