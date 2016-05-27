import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by apple on 24.04.2016.
 */
public class Component extends JComponent {
    private int x=400;
    private int y=400;


    private int rectSize;

    private Mesh mesh;

    public Component(Mesh mesh){

        this.rectSize=800/x;
        this.mesh = mesh;
        addMouseListener(new EventHandler());
    }



    public void paintComponent(Graphics g) {
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                if (mesh.isAlive(i,j)==1) {
                    Color color;
                    int id=mesh.getId(i,j);
                    color=resolveColor(id);

                    g.setColor(color);

                } else {
                    g.setColor(Color.BLACK);
                }
                g.fillRect(rectSize * j, rectSize * i, rectSize, rectSize);
            }
        }
    }

    class EventHandler extends MouseAdapter{
        @Override
        public void mouseClicked(MouseEvent e) {

            int y=e.getX();
            int x=e.getY();

            int i=x/rectSize;
            int j=y/rectSize;

            System.out.println("i: "+ i +" j:"+j);

            mesh.setAlive(i,j);
            Component.this.repaint();

        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(600,600);
    }

    public Color resolveColor(int id){

        if(id==0)
            return Color.BLACK;
        else {
            int r,g,b;
                if ((id % 2) == 0) {
                    r = (255 - 15 * id) % 255;
                    g = (20 + 22 * id) % 255;
                    b = (100 - 17 * id) % 255;
                } else if ((id %3 ) == 0) {
                    r = (100 + 15 * id) % 255;
                    g = (255 - 22 * id) % 255;
                    b = (20 + 17 * id) % 255;
                }
                else if ((id %5 ) == 0) {
                    r = (200 - 5 * id) % 255;
                    g = (40 - 18 * id) % 255;
                    b = (120 + 17 * id) % 255;
                }
                else{
                    r = (100 - 15 * id) % 255;
                    g = (255 - 5 * id) % 255;
                    b = (20 + 14 * id) % 255;
                }

            if(r<0)
                r=0;
            if(g<0)
                g=0;
            if(b<0)
                b=0;

            return new Color(r,g,b);
        }
    }
}

