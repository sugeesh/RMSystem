package org.hashtag.rms.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Sugeesh Chandraweera
 */
public class KOTNumberGenerator {
    static Properties prop = new Properties();

    public static String getNextKOTNumber() {

        FileInputStream input = null;
        try {
            input = new FileInputStream("./src/main/resources/kotconfig.properties");
            // load a properties file
            prop.load(input);

            int lastId = Integer.parseInt(prop.getProperty("kotNumber").substring(3));
            String strLatId = Integer.toString(lastId);
            String prefix = "KOT";
            String result = prefix;

            if (strLatId.length() < 4) {
                for (int i = 0; i < 4 - strLatId.length(); i++) {
                    result += "0";
                }
            }
            input.close();

            return result + (lastId + 1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void increaseOrderId(String nextKOTNumber) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("./src/main/resources/kotconfig.properties");
            // set the properties value
            prop.setProperty("kotNumber", nextKOTNumber);
            // save properties to project root folder
            prop.store(fileOutputStream, null);
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
