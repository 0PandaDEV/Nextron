package tk.pandadev.nextron.utils;

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
                plugin.getLogger().info("A newer version of the plugin is available: " + latestReleaseTag);
                // perform update logic here
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

            // Find the first release that's not a draft or a pre-release
            JsonParser parser = new JsonParser();
            JsonArray releases = parser.parse(responseBody).getAsJsonArray();
            for (JsonElement releaseElement : releases) {
                JsonObject release = releaseElement.getAsJsonObject();
                boolean isDraft = release.get("draft").getAsBoolean();
                boolean isPrerelease = release.get("prerelease").getAsBoolean();
                if (!isDraft && !isPrerelease) {
                    String tagName = release.get("tag_name").getAsString();
                    return tagName;
                }
            }

            // If no non-draft, non-pre-release releases were found, return the latest tag
            for (JsonElement releaseElement : releases) {
                JsonObject release = releaseElement.getAsJsonObject();
                boolean isDraft = release.get("draft").getAsBoolean();
                boolean isPrerelease = release.get("prerelease").getAsBoolean();
                if (!isDraft && isPrerelease) {
                    String tagName = release.get("tag_name").getAsString();
                    return tagName;
                }
            }

            // If no releases were found at all, throw an exception
            throw new IOException("Failed to retrieve release information from GitHub API.");
        } else {
            throw new IOException("Failed to retrieve release information from GitHub API. Status code: " + statusCode);
        }
    }

    public static boolean isVersionNewer(String version1, String version2) {
        String[] v1 = version1.replaceAll("[^0-9.]+", "").split("\\.");
        String[] v2 = version2.replaceAll("[^0-9.]+", "").split("\\.");

        for (int i = 0; i < Math.max(v1.length, v2.length); i++) {
            int n1 = i < v1.length ? Integer.parseInt(v1[i]) : 0;
            int n2 = i < v2.length ? Integer.parseInt(v2[i]) : 0;
            if (n1 != n2) {
                return n1 > n2;
            }
        }

        return false;
    }

}
