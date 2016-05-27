import javax.swing.*;
import java.awt.*;

/**
 * Created by apple on 24.04.2016.
 */
public class Main {

    public static void main(String[] args){

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Mesh mesh =new Mesh();
                JFrame frame=new Frame(mesh);
                frame.setVisible(true);
            }
        });
    }
}
