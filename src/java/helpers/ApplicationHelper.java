package helpers;

import java.io.IOException;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

public class ApplicationHelper {
    
    private static final char[] VALID_CHARACTERS = "123456879".toCharArray();
    
    public static ExternalContext getExternalContext() {
        return FacesContext.getCurrentInstance().getExternalContext();
    }
    
    public static Map<String, String> getRequestParameterMap() {
        return getExternalContext().getRequestParameterMap();
    }
    
    public static void redirect(String externalPath, boolean keepMessage) {
        ExternalContext ec = getExternalContext();
        try {
            ec.getFlash().setKeepMessages(keepMessage);
            ec.redirect(ec.getRequestContextPath() + externalPath);
            FacesContext.getCurrentInstance().responseComplete();
        } catch (IOException e) {
        }
        
    }
    
    public static void navigate(String externalPath){
        FacesContext fc = FacesContext.getCurrentInstance();
        NavigationHandler nh = fc.getApplication().getNavigationHandler();
        nh.handleNavigation(fc, null, externalPath);
    }
    
    public static void addMessage(String message) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message));
    }
    
    public static boolean isInteger(String number) {
        try {
            Integer.parseInt(number);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public static boolean isLong(String number) {
        try {
            Long.parseLong(number);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public static String secureRandomString(int numChars) {
        SecureRandom srand = new SecureRandom();
        Random rand = new Random();
        char[] buff = new char[numChars];
        
        for (int i = 0; i < numChars; ++i) {
            // reseed rand once you've used up all available entropy bits
            if ((i % 10) == 0) {
                rand.setSeed(srand.nextLong()); // 64 bits of random!
            }
            buff[i] = VALID_CHARACTERS[rand.nextInt(VALID_CHARACTERS.length)];
        }
        return new String(buff);
    }
    
    public static String formatDate(Date date, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        String formated_date = format.format(date);
        return formated_date;
    }
    
    public static Date parseDate(String stringDate, String pattern) {
        SimpleDateFormat parse = new SimpleDateFormat(pattern);
        try {
            Date date = parse.parse(stringDate);
            return date;
        } catch (ParseException ex) {
            ex.printStackTrace();
            return null;
        }
        
    }
    
    public static int getCurrentPage(String page) {
        int current_page = 1;
        if (page != null && !page.isEmpty()) {
            
            if (!isInteger(page)) {
                ApplicationHelper.redirect("/404.xhtml", true);
                
            }
            current_page = Integer.parseInt(page);
            current_page = current_page == 0 ? 1 : current_page;
        }
        return current_page;
    }
    
}
