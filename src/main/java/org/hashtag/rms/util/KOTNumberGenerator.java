package org.hashtag.rms.util;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 *
 * You need to change the relative paths to absolute path when you run on ec2 machine
 *
 * @author Sugeesh Chandraweera
 */
public class KOTNumberGenerator {
    static Properties prop = new Properties();

    public static String getNextKOTNumber() {

        FileInputStream input = null;
        try {
          input = new FileInputStream("/opt/tomcat9/webapps/restaurantApp-1.0-SNAPSHOT/WEB-INF/classes/kotconfig.properties");
//            input = new FileInputStream("./src/main/resources/kotconfig.properties");
            prop.load(input);
//
            SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
            Date now = new Date();
            String nowDate = sdfDate.format(now);
            String preDate = prop.getProperty("date");

            String prefix = "KOT";
            String result = prefix;
            int lastId = 0;

            if(nowDate.equals(preDate)) {
                lastId = Integer.parseInt(prop.getProperty("kotNumber").substring(3));
            }
            String strLatId = Integer.toString(lastId);

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
//            FileOutputStream fileOutputStream = new FileOutputStream("./src/main/resources/kotconfig.properties");
            FileOutputStream fileOutputStream = new FileOutputStream("/opt/tomcat9/webapps/restaurantApp-1.0-SNAPSHOT/WEB-INF/classes/kotconfig.properties");

            // set the properties value
            prop.setProperty("kotNumber", nextKOTNumber);

            // set Date
            SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
            Date now = new Date();
            String strDate = sdfDate.format(now);
            prop.setProperty("date", strDate);

            // save properties to project root folder
            prop.store(fileOutputStream, null);
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
