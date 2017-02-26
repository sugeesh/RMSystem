package org.hashtag.rms.util;

/**
 * @author Sugeesh Chandraweera
 */
public class KOTNumberGenerator {

    public static String getNextKOTNumber(String lastKOT) {
        int lastId = Integer.parseInt(lastKOT.substring(3));
        String strLatId = Integer.toString(lastId);
        String prefix = "KOT";
        String result = prefix;

        if (strLatId.length() < 4) {
            for (int i = 0; i < 4 - strLatId.length(); i++) {
                result += "0";
            }
        }
        return result+(lastId+1);
    }
}
