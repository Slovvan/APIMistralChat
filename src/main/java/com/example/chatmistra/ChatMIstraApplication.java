package com.example.chatmistra;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;
import java.util.Scanner;

public class ChatMIstraApplication {
    public static void main(String[] args) {
        String apiKey = "JdA4giGQC4SGMkDIiKTn47dHJeR8UTMT";
        // Tentative de lecture depuis le fichier config.properties
        Properties properties = new Properties();
        try (FileInputStream input = new
                FileInputStream("config.properties")) {
            properties.load(input);
            apiKey = properties.getProperty("MISTRAL_KEY");
            System.out.println("Clé API trouvée dans config.properties");
        } catch (IOException e) {
            System.out.println("Fichier config.properties non trouvé, tentative avec variable d'environnement...");
        }
        // Si pas trouvée dans le fichier, essayer la variabled'environnement
        if (apiKey == null || apiKey.trim().isEmpty()) {
            apiKey = System.getenv("MISTRAL_KEY");
            if (apiKey != null && !apiKey.trim().isEmpty()) {
                System.out.println("Clé API trouvée dans les variables d'environnement");
            }
        }
        // Vérification finale
        if (apiKey == null || apiKey.trim().isEmpty()) {
            System.err.println("ERREUR: Clé API OpenAI non trouvée!");
            System.err.println("Veuillez soit:");
            System.err.println("1. Créer un fichier 'config.properties' avec: OPENAI_KEY_JAVA=votre_cle_api");
            System.err.println("2. Ou définir la variable d'environnement OPENAI_KEY_JAVA");
            System.exit(1);
        }
        // Utilisez le token API ici
        System.out.println("API Key chargée avec succès: " + apiKey.substring(0, Math.min(apiKey.length(), 10)) + "...");

        // 🗣️ Comienza el chat
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("\nTú: ");
            String prompt = scanner.nextLine();
            if (prompt.equalsIgnoreCase("quit")) break;

            try {
                String jsonInput = "{ \"model\": \"mistral-small-latest\", \"messages\": [{\"role\": \"user\", \"content\": \"" + prompt + "\"}] }";

                URL url = new URL("https://api.mistral.ai/v1/chat/completions");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Authorization", "Bearer " + apiKey);
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);

                try (OutputStream os = conn.getOutputStream()) {
                    os.write(jsonInput.getBytes());
                }

                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null) {
                    response.append(line);
                }
                in.close();

                // Mostrar la JSon de respuesta
                System.out.println("🤖 Mistral: " + response.toString());

            } catch (Exception e) {
                System.err.println("Error al comunicarse con la API: " + e.getMessage());
            }
        }

        System.out.println("Chat Terminado.");
    }

}

