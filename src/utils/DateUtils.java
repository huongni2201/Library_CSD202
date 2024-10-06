/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Huong
 */
public class DateUtils {

    public static Date parseDate(String date, String regex) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(regex);
        try {
            return simpleDateFormat.parse(date);
        } catch (ParseException e) {
            System.out.println("Error parsing date: " + e.getMessage());
            return null; 
        }
    }
}
