import javax.swing.*;
import java.lang.*;

/**
 * Created by apple on 16.05.2016.
 */
public class ThreadRec extends java.lang.Thread{
    private Mesh mesh;
    private Component component;
    private JFrame frame;


    public ThreadRec(Mesh mesh, Component component, JFrame frame){
        this.component = component;
        this.mesh = mesh;
        this.frame=frame;
    }
    public void run(){
        while(mesh.isRecStarted() && !mesh.isRecrystalized()){
            mesh.nextRoundRec();
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
