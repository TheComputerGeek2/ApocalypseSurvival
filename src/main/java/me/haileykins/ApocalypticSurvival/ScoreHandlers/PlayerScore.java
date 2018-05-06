package me.haileykins.ApocalypticSurvival.ScoreHandlers;

import java.text.MessageFormat;
import java.util.UUID;
import java.util.regex.Pattern;

public class PlayerScore {

    private static final Pattern csvSplit = Pattern.compile("\\s*,\\s*");

    // fred, 1, 2

    public UUID playerId;
    public int killCount;
    public int deathCount;

    public PlayerScore(UUID id) {
        playerId = id;
        killCount = 0;
        deathCount = 0;
    }

    private PlayerScore(String idString, String killString, String deathString) {
        playerId = UUID.fromString(idString);
        killCount = Integer.parseInt(killString);
        deathCount = Integer.parseInt(deathString);
    }

    public static PlayerScore reparseCSV(final String line) {
        String[] fields = csvSplit.split(line);

        if (fields.length < 3) {
            System.out.println("PlayerScore.reparseCSV: only found " + fields.length + " fields");
            return null;
        }

        return new PlayerScore(fields[0], fields[1], fields[2]);
    }

    public String toCSV() {
        return MessageFormat.format("{0},{1},{2}\n", playerId, killCount, deathCount);
    }
}
