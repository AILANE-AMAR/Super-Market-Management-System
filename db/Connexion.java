package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connexion {

    private static Connection connection;

    public static Connection connect() {
        try {
            String url = "jdbc:postgresql://localhost:5432/rental_car";
            String user = "postgres";
            String password = "240975";

            // ✅ PAS DE "Connection" DEVANT
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connexion réussie !");

        } catch (SQLException e) {
            System.err.println("Erreur de connexion à la base de données : " + e.getMessage());
        }

        return connection;
    }

    public static void close() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Connexion fermée.");
            } catch (SQLException e) {
                System.err.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
            }
        }
    }
}
