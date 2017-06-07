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

    public static Double getTaxDineIn() {
        FileInputStream input = null;
        try {
//          input = new FileInputStream("/opt/tomcat9/webapps/restaurantApp-1.0-SNAPSHOT/WEB-INF/classes/kotconfig.properties");
            input = new FileInputStream("./src/main/resources/kotconfig.properties");
            prop.load(input);

            return Double.parseDouble(prop.getProperty("tax_dinein"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    public static Double getSChargeDineIn() {
        FileInputStream input = null;
        try {
//          input = new FileInputStream("/opt/tomcat9/webapps/restaurantApp-1.0-SNAPSHOT/WEB-INF/classes/kotconfig.properties");
            input = new FileInputStream("./src/main/resources/kotconfig.properties");
            prop.load(input);

            return Double.parseDouble(prop.getProperty("s_charge_dinein"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    public static Double getTaxTakeAway() {
        FileInputStream input = null;
        try {
//          input = new FileInputStream("/opt/tomcat9/webapps/restaurantApp-1.0-SNAPSHOT/WEB-INF/classes/kotconfig.properties");
            input = new FileInputStream("./src/main/resources/kotconfig.properties");
            prop.load(input);

            return Double.parseDouble(prop.getProperty("tax_takeaway"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    public static Double getSChargeTakeAway() {
        FileInputStream input = null;
        try {
//          input = new FileInputStream("/opt/tomcat9/webapps/restaurantApp-1.0-SNAPSHOT/WEB-INF/classes/kotconfig.properties");
            input = new FileInputStream("./src/main/resources/kotconfig.properties");
            prop.load(input);

            return Double.parseDouble(prop.getProperty("s_charge_takeaway"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    public static void save(double tax_dinein, double s_charge_dinein, double tax_takeaway, double s_charge_takeaway) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("./src/main/resources/kotconfig.properties");
//            FileOutputStream fileOutputStream = new FileOutputStream("/opt/tomcat9/webapps/restaurantApp-1.0-SNAPSHOT/WEB-INF/classes/kotconfig.properties");

            // set the properties value
            prop.setProperty("tax_dinein", String.valueOf(tax_dinein));
            prop.setProperty("s_charge_dinein", String.valueOf(s_charge_dinein));
            prop.setProperty("tax_takeaway", String.valueOf(tax_takeaway));
            prop.setProperty("s_charge_takeaway", String.valueOf(s_charge_takeaway));
            // save properties to project root folder
            prop.store(fileOutputStream, null);
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
