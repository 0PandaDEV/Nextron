package net.pandadev.nextron.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class UpdateChecker {
    private final JavaPlugin plugin;
    private final String repoName;

    public UpdateChecker(JavaPlugin plugin, String repoName) {
        this.plugin = plugin;
        this.repoName = repoName;
    }

    public void checkForUpdates() {
        try {
            String latestReleaseTag = getLatestReleaseTag();
            String currentVersion = plugin.getDescription().getVersion();
            if (isVersionNewer(latestReleaseTag, currentVersion)) {
                System.out.println(" ");
                System.out.println("§8---------------< §x§b§1§8§0§f§f§lNextron Update §8>---------------");
                System.out.println(" ");
                System.out.println("§7Current version: §cv" + currentVersion);
                System.out.println("§7Latest version: §a" + latestReleaseTag);
                System.out.println(" ");
                System.out.println("§8------------------------------------------------");
                System.out.println(" ");
            } else {
                plugin.getLogger().info("Plugin is up to date.");
            }
        } catch (IOException e) {
            plugin.getLogger().warning("Failed to check for updates: " + e.getMessage());
        }
    }

    private String getLatestReleaseTag() throws IOException {
        String url = "https://api.github.com/repos/" + repoName + "/releases";
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");

        int statusCode = connection.getResponseCode();
        if (statusCode == HttpURLConnection.HTTP_OK) {
            Scanner scanner = new Scanner(connection.getInputStream());
            String responseBody = scanner.useDelimiter("\\A").next();
            scanner.close();
            // Find the first release that's not a draft
            JsonArray releases = JsonParser.parseString(responseBody).getAsJsonArray();
            for (JsonElement releaseElement : releases) {
                JsonObject release = releaseElement.getAsJsonObject();
                boolean isDraft = release.get("draft").getAsBoolean();
                if (!isDraft) {
                    return release.get("tag_name").getAsString();
                }
            }

            // If no non-draft releases were found, return the latest tag
            if (!releases.isJsonNull() && !releases.isEmpty()) {
                JsonObject latestRelease = releases.get(0).getAsJsonObject();
                return latestRelease.get("tag_name").getAsString();
            }

            // If no releases were found at all, throw an exception
            throw new IOException("Failed to retrieve release information from GitHub API.");
        } else {
            throw new IOException("Failed to retrieve release information from GitHub API. Status code: " + statusCode);
        }
    }

    public static boolean isVersionNewer(String remoteVersion, String currentVersion) {
        int remote = Integer.parseInt(remoteVersion.replaceAll("\\D+", ""));
        int current = Integer.parseInt(currentVersion.replaceAll("\\D+", ""));

        if (String.valueOf(current).length() == 4 && String.valueOf(remote).length() == 4) return remote > current;

        else if (String.valueOf(remote).length() == 4)
            return !(Integer.parseInt(String.valueOf(remote).substring(0, String.valueOf(remote).length() - 1)) <= current);

        else if (String.valueOf(current).length() == 4)
            return remote >= Integer.parseInt(String.valueOf(current).substring(0, String.valueOf(current).length() - 1));

        return remote > current;
    }

}
