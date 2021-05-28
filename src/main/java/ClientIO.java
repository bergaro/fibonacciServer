import java.io.*;
import java.net.Socket;
import java.util.Scanner;
/**
 * Ввиду того, что как у клиента, так и у сервера отсутствуют промежуточные действия
 * которые могла бы аффектить блокировка стрима, считаю справедливым использование
 * IO вместо NIO. Именно по данной причине решил не усложнять код реализацией NIO и
 * реализовал в более привычном, для меня, виде.
 */
public class ClientIO {

    protected void doWork() {
        String host = "127.0.0.1";
        int port = 24001;
        try {
            Socket socket = new Socket(host, port);
            System.out.println("Connecting... (" + host + ", " + port +")");
            createStreamIO(socket);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void createStreamIO(Socket socket) {
        try(BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            Scanner scanner = new Scanner(System.in)) {

            String msg;
            while(true) {
                System.out.println("Enter the position of the fibonacci number or 'end' to complete");
                msg = scanner.nextLine();
                out.println(msg);
                if(msg.equals("end")) break;
                System.out.println("FIBONACCI VALUE: " + in.readLine());
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
