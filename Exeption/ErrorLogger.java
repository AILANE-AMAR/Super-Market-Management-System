package Exeption;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class ErrorLogger {

    // Méthode pour enregistrer les erreurs générales
    public static void logError(String message, Exception e) {
        try (FileWriter writer = new FileWriter("error.log", true)) {
            writer.write(LocalDateTime.now() + " - " + message + "\n");
            writer.write("Erreur : " + e.toString() + "\n");
            for (StackTraceElement element : e.getStackTrace()) {
                writer.write("\tat " + element + "\n");
            }
            writer.write("\n");
        } catch (IOException ioException) {
            System.err.println("Erreur lors de l'écriture dans le fichier log : " + ioException.getMessage());
        }
    }

    // Méthode spécifique pour les erreurs SQL
    public static void logError(String message, SQLException e) {
        logError(message, (Exception) e);
    }
}
