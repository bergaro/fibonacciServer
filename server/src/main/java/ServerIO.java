import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * Ввиду того, что как у клиента, так и у сервера отсутствуют промежуточные действия
 * которые могла бы аффектить блокировка стрима, считаю справедливым использование
 * IO вместо NIO. Именно по данной причине решил не усложнять код реализацией NIO и
 * реализовал в более привычном, для меня, виде.
 */
public class ServerIO {
    private static ServerIO instance;
    private ServerIO() { }

    protected static ServerIO getInstance() {
        if(instance == null) {
            instance = new ServerIO();
        }
        return instance;
    }

    protected void doWork() {
        ServerSocket serverSocket;
        int port = 24001;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server opening on port: " + port + ".");
            createSocketWork(serverSocket);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    protected void createSocketWork(ServerSocket serverSocket) {
        String line;
        try(Socket socket = serverSocket.accept()){
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while ((line = in.readLine()) != null) {
                if(line.equals("end")) {
                    System.out.println("Server closed.");
                    break;
                }
                out.println(getFibNum(line));
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private int getFibNum(String num) {
        int result = 0;
        try {
            int position = Integer.parseInt(num);
            result = calculateFibonacciValue(position);
        } catch (NumberFormatException ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }

    private int calculateFibonacciValue(int position) {
        if(position <= 1) {
            return position;
        }
        return calculateFibonacciValue(position - 1) + calculateFibonacciValue(position - 2);
    }
}
