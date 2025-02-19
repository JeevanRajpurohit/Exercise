import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

class LoggingPromotion {
    public static void logPromotion(String role, int id, String name) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("promotion_logHistory.txt", true))) {
            writer.write("User with name " + name + " and Id " + id + " was promoted.");
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Cannot Able to write in promotion File : " + e.getMessage());
        }
    }
}



