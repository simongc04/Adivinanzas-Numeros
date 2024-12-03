package servidoradivinanza;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

/**
 *
 * @author simon
 */
public class ServidorAdivinanza {
    public static void main(String[] args) {
        int port = 12345;

        try {
            ServerSocket server = new ServerSocket(port);
            System.out.println("Servidor iniciado en el puerto " + port + ".");

            while (true) {
                Socket client = server.accept();
                System.out.println("Cliente conectado: " + client.getInetAddress());

                // Generar un número aleatorio entre 1 y 100 para este cliente
                Random random = new Random();
                int numeroAleatorio = random.nextInt(100) + 1;
                System.out.println("Número secreto para este cliente: " + numeroAleatorio);

                BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));
                PrintWriter output = new PrintWriter(client.getOutputStream(), true);

                String message;
                boolean adivinado = false;

                while (!adivinado && (message = input.readLine()) != null) {
                    try {
                        int suposicion = Integer.parseInt(message);
                        if (suposicion < numeroAleatorio) {
                            output.println("El número es mayor.");
                        } else if (suposicion > numeroAleatorio) {
                            output.println("El número es menor.");
                        } else {
                            output.println("¡Correcto! Has adivinado el número.");
                            adivinado = true;
                        }
                    } catch (NumberFormatException e) {
                        output.println("Por favor, envía un número válido.");
                    }
                }
                client.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}







