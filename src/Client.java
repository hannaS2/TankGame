import processing.core.PApplet;

import java.io.IOException;
import java.io.InputStream;

interface OnReceived {
    void onReceive(String packet);
}

class ReaderThread extends Thread {
    private InputStream is;
    private OnReceived onReceived;

    public ReaderThread(InputStream is) {
        this.is = is;
    }

    void setOnReceived(OnReceived onReceived) {
        this.onReceived = onReceived;
    }

    @Override
    public void run() {
        byte[] data = new byte[1024];
        try {
            while (true) {
                int len = is.read(data);
                if (len == -1)
                    break;

                String message = new String(data, 0, len);
                System.out.println(message);
                if (onReceived != null) {
                    onReceived.onReceive(message);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

public class Client extends PApplet{

    private OnReceived onReceived = new OnReceived() {
        @Override
        public void onReceive(String packet) {
            
        }
    };

    @Override
    public void settings() {
        size(800, 600);
    }

    @Override
    public void setup() {
        background(255);
    }

    @Override
    public void draw() {

    }

    public static void main(String[] args) {
        PApplet.main("Client");
    }


}
