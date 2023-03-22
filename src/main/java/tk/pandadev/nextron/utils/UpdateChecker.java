package tk.pandadev.nextron.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class UpdateChecker {
    private final String username;
    private final String repository;

    public UpdateChecker(String username, String repository) {
        this.username = username;
        this.repository = repository;
    }

    public boolean isNewerVersion(String currentVersion) {
        System.out.println(getLatestVersion());
        System.out.println(currentVersion);
        String latestVersion = getLatestVersion();

        if (latestVersion == null) {
            return false;
        }

        // Split version strings into major, minor, patch, and pre-release parts
        String[] current = currentVersion.split("[.-]");
        String[] latest = latestVersion.split("[.-]");

        // Parse major, minor, and patch version numbers
        int currentMajor = Integer.parseInt(current[0]);
        int currentMinor = current.length > 1 ? Integer.parseInt(current[1]) : 0;
        int currentPatch = current.length > 2 ? Integer.parseInt(current[2]) : 0;

        int latestMajor = Integer.parseInt(latest[0]);
        int latestMinor = latest.length > 1 ? Integer.parseInt(latest[1]) : 0;
        int latestPatch = latest.length > 2 ? Integer.parseInt(latest[2]) : 0;

        // Compare major, minor, and patch version numbers
        if (latestMajor > currentMajor ||
                (latestMajor == currentMajor && latestMinor > currentMinor) ||
                (latestMajor == currentMajor && latestMinor == currentMinor && latestPatch > currentPatch)) {
            return true;
        }

        // If major, minor, and patch version numbers are equal, compare pre-release parts
        if (latest.length > 3 && current.length > 3) {
            String currentPreRelease = current[3];
            String latestPreRelease = latest[3];

            if (currentPreRelease.isEmpty() && !latestPreRelease.isEmpty()) {
                return true;
            } else if (!currentPreRelease.isEmpty() && latestPreRelease.isEmpty()) {
                return false;
            } else if (!currentPreRelease.isEmpty() && !latestPreRelease.isEmpty()) {
                return currentPreRelease.compareTo(latestPreRelease) < 0;
            }
        }

        return false;
    }


    private String getLatestVersion() {
        try {
            URL url = new URL("https://api.github.com/repos/" + username + "/" + repository + "/releases/latest");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Accept", "application/vnd.github.v3+json");

            Scanner scanner = new Scanner(con.getInputStream());
            String response = scanner.useDelimiter("\\A").next();
            scanner.close();

            System.out.println(response.split("\"tag_name\":\"")[1].split("\"")[0].replace("v", ""));

            return response.split("\"tag_name\":\"")[1].split("\"")[0].replace("v", "");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
