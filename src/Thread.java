import javax.swing.*;

/**
 * Created by apple on 24.04.2016.
 */
public class Thread extends java.lang.Thread {

    private Mesh mesh;
    private Component component;
    private JFrame frame;


    public Thread(Mesh mesh, Component component, JFrame frame){
        this.component = component;
        this.mesh = mesh;
        this.frame=frame;
    }
    public void run(){
        while(mesh.isStarted() && !mesh.isFilled()){
            mesh.nextRound();
            component.repaint();
            try {
                sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        frame.repaint();
    }
}
