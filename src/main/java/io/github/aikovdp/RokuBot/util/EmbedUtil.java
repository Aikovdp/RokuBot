package io.github.aikovdp.RokuBot.util;

import net.dv8tion.jda.api.EmbedBuilder;

import java.io.IOException;

public class EmbedUtil {
    public static EmbedBuilder createInfoEmbed() {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(0x0FFFFF);
        builder.setThumbnail("https://raw.githubusercontent.com/twitter/twemoji/master/assets/72x72/2139.png");
        builder.setFooter("Click the title for more information");

        return builder;
    }

    public static EmbedBuilder createGHInfoEmbed(String filePath, String title) {
        try {
            EmbedBuilder builder = createInfoEmbed();
            RepoUtil repoUtil = new RepoUtil("Rokucraft/Rokucraft", filePath, title);
            builder.setTitle(repoUtil.infoTitle, repoUtil.infoURL);
            builder.setDescription(repoUtil.infoContent);
            return builder;
        } catch (IOException e) {
            e.printStackTrace();
            EmbedBuilder builder = createErrorEmbed();
            builder.setTitle("❌ Couldn't find info");
            return builder;
        }
    }

    public static EmbedBuilder createPluginEmbed() {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(0xFF7F00);
        builder.setThumbnail("https://raw.githubusercontent.com/twitter/twemoji/master/assets/72x72/1f4e6.png");
        builder.setFooter("Click the title for more information");

        return builder;
    }

    public static EmbedBuilder createErrorEmbed() {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(0xFF0000);

        return builder;
    }
}
