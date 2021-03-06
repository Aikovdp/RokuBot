package com.rokucraft.RokuBot.entities;

import com.rokucraft.RokuBot.Main;
import com.rokucraft.RokuBot.Settings;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.kohsuke.github.GHRepository;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import static com.rokucraft.RokuBot.Constants.BLUE;
import static com.rokucraft.RokuBot.util.EmbedUtil.createErrorEmbed;

@ConfigSerializable
public class MarkdownSection extends AbstractEntity {
    private String title;
    private String repoName;
    private String filePath;
    private String thumbnailUrl;
    private String url;
    private String footer;

    public static MarkdownSection find(String name) {
        return (MarkdownSection) find(name, Settings.markdownSectionList);
    }

    public MessageEmbed toEmbed(String footerIconUrl) {
        EmbedBuilder builder;
        if (thumbnailUrl == null) {
            thumbnailUrl = "https://raw.githubusercontent.com/twitter/twemoji/master/assets/72x72/2139.png";
        }
        try {
            builder = new EmbedBuilder()
                    .setColor(BLUE)
                    .setTitle(title.replaceAll("#+ ", ""), url)
                    .setThumbnail(thumbnailUrl)
                    .setFooter(footer, footerIconUrl)
                    .setDescription(getContents());
        } catch (IOException e) {
            e.printStackTrace();
            builder = createErrorEmbed()
                    .setTitle("❌ Couldn't find info");
        }
        return builder.build();
    }

    public String getContents() throws IOException {
        GHRepository repository = Main.github.getRepository(repoName);
        InputStream inputStream = repository.getFileContent(filePath).read();
        String fullText = new BufferedReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining("\n"));

        return fullText.substring(fullText.indexOf(title))
                .replaceFirst(".*", "") // Remove title
                .replaceAll("(?s)#+ .*", ""); // Remove everything starting from new title
    }
}
