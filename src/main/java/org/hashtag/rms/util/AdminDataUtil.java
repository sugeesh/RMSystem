package org.hashtag.rms.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Sugeesh Chandraweera
 */
public class AdminDataUtil {
    static Properties prop = new Properties();

    public static Double getTax(){
        FileInputStream input = null;
        try {
//          input = new FileInputStream("/opt/tomcat9/webapps/restaurantApp-1.0-SNAPSHOT/WEB-INF/classes/kotconfig.properties");
            input = new FileInputStream("./src/main/resources/kotconfig.properties");
            prop.load(input);

            return Double.parseDouble(prop.getProperty("tax"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    public static Double getSCharge(){
        FileInputStream input = null;
        try {
//          input = new FileInputStream("/opt/tomcat9/webapps/restaurantApp-1.0-SNAPSHOT/WEB-INF/classes/kotconfig.properties");
            input = new FileInputStream("./src/main/resources/kotconfig.properties");
            prop.load(input);

            return Double.parseDouble(prop.getProperty("s_charge"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0.0;
    }


    public static void save(double tax, double s_charge) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("./src/main/resources/kotconfig.properties");
//            FileOutputStream fileOutputStream = new FileOutputStream("/opt/tomcat9/webapps/restaurantApp-1.0-SNAPSHOT/WEB-INF/classes/kotconfig.properties");

            // set the properties value
            prop.setProperty("tax", String.valueOf(tax));
            prop.setProperty("s_charge", String.valueOf(s_charge));
            // save properties to project root folder
            prop.store(fileOutputStream, null);
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
