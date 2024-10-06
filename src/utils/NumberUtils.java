/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author khang
 */
public class NumberUtils {

    public static int getRandomNumberInRange(int min, int max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }

    public static int getInt(String mess, int min, int max) {
        int ret;
        Scanner scan = new Scanner(System.in);
        System.out.println(mess);
        do {
            try {
                ret = Integer.parseInt(scan.nextLine());
                if (ret < min || ret > max) {
                    throw new IndexOutOfBoundsException();
                }
                return ret;
            } catch (NumberFormatException e) {
                System.out.println("Only allowed to enter integer numbers");
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Please enter integer number in range [" + min + "," + max + "]");
            } catch (Exception e) {
                e.printStackTrace();
            }

        } while (true);

    }

    public static int getInt(String mess) {
        return getInt(mess, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public static double getDouble(String mess, double min, double max) {
        double ret;
        Scanner scan = new Scanner(System.in);
        System.out.println(mess);
        do {
            try {
                ret = Double.parseDouble(scan.nextLine());
                if (ret <= min || ret > max) {
                    throw new IndexOutOfBoundsException();
                }
                return ret;
            } catch (NumberFormatException e) {
                System.out.println("Only allowed to enter decimal numbers");
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Please enter a decimal number in range [" + min + "," + max + "]");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } while (true);
    }

    public static double getDouble(String mess) {
        return getDouble(mess, Double.MIN_VALUE, Double.MAX_VALUE);
    }

    public static String getString(String mess, String error) {
        Scanner scanner = new Scanner(System.in);
        String input;

        while (true) {
            try {
                System.out.print(mess); // Display the prompt message
                input = scanner.nextLine().trim(); // Get the input and remove any leading/trailing spaces

                if (!input.isEmpty()) {
                    return input; // If input is valid, return it
                }
                throw new Exception(error);
            } catch (Exception e) {
                System.out.println(error);
            }
        }
    }

    public static Date parseDate(String dateStr, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setLenient(false); // Ensure strict parsing
        try {
            Date date = sdf.parse(dateStr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            // Get the year and check if it's two digits
            int year = calendar.get(Calendar.YEAR);
            if (year < 100) { // Handle two-digit years
                // Adjust the year to be in the 21st century
                year += (year < 50) ? 2000 : 1900; // You can adjust this logic as necessary
                calendar.set(Calendar.YEAR, year);
            }

            return calendar.getTime();
        } catch (ParseException e) {
            System.err.println("Date parsing error: " + e.getMessage());
            return null; // Handle invalid date case
        }
    }

}
