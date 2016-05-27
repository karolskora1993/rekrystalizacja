import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.*;

/**
 * Created by apple on 24.04.2016.
 */
public class Frame extends JFrame {

    public static final int WIDTH=900;
    public static final int HEIGHT=900;

    private Mesh mesh;

    private Component component;
    private JPanel settingsPanel;
    JButton buttonStart;
    JButton buttonRecrystalize;


    public Frame(Mesh mesh){
        this.mesh = mesh;
        initializeComponent();
    }

    @Override
    public void repaint() {
        super.repaint();
        String name;
        if(mesh.isStarted())
            name="STOP";
        else
            name="START";

        buttonStart.setText(name);

        String recName;

        if(mesh.isRecStarted())
            recName="STOP";
        else
            recName="rekrystalizuj";

        buttonRecrystalize.setText(recName);

    }

    private void initializeComponent(){

        setSize(WIDTH,HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("Model rekrystalizacji dynamicznej");

        setLayout(new BorderLayout());

        settingsPanel=new JPanel();

        JLabel label=new JLabel("wybierz rodzaj sasiedztwa:");
        settingsPanel.add(label);

        JComboBox chooseNeighourMethod=new JComboBox(new String[]{
                "Moore'a",
                "Von Neumanna",
                "Hexagonalne lewe ",
                "Hexagonalne prawe ",
                "Hexagonalne losowe ",
                "Pentagonalne losowe",
                "pseudolosowe"
        });

        chooseNeighourMethod.setSelectedIndex(0);
        chooseNeighourMethod.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JComboBox source=(JComboBox)e.getSource();
                int selectedIndex=source.getSelectedIndex();

                switch(selectedIndex){
                    case 0:

                        mesh.setMethod(Mesh.Method.MOORE);
                        break;
                    case 1:
                        mesh.setMethod(Mesh.Method.VON_NEUMANN);
                        break;
                    case 2:
                        mesh.setMethod(Mesh.Method.HEX_LEFT);
                        break;
                    case 3:
                        mesh.setMethod(Mesh.Method.HEX_RIGHT);
                        break;
                    case 4:
                        mesh.setMethod(Mesh.Method.HEX_RAND);
                        break;
                    case 5:
                        mesh.setMethod(Mesh.Method.PENT);
                        break;
                    case 6: {
                        int r=Integer.parseInt(JOptionPane.showInputDialog("Wprowadz promień"));
                        mesh.setRandomCaR(r);
                        mesh.setMethod(Mesh.Method.RAND_CA);
                    }
                        break;
                }
            }
        });

        settingsPanel.add(chooseNeighourMethod);


        JButton buttonClear=new JButton("wyczyść");
        buttonClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Frame.this.mesh.clear();
                component.repaint();
            }
        });
        settingsPanel.add(buttonClear);

        JButton buttonPeriod=new JButton("włącz periodyczne");
        buttonPeriod.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton b=(JButton)e.getSource();
                if(Frame.this.mesh.isPeriod()) {
                    b.setText("włącz periodyczne");
                    Frame.this.mesh.setPeriod(false);
                }
                else{
                    b.setText("wyłącz periodyczne");
                    Frame.this.mesh.setPeriod(true);
                }
            }
        });
        settingsPanel.add(buttonPeriod);

        String start;
        if(mesh.isStarted())
            start="STOP";
        else
        start="START";

        buttonStart=new JButton(start);

        buttonStart.addActionListener(new ButtonStartListener());
        settingsPanel.add(buttonStart);

        String rec;
        if(mesh.isRecStarted())
            rec="STOP";
        else
            rec="Rekrystalizuj";

        buttonRecrystalize=new JButton(rec);

        buttonRecrystalize.addActionListener(new ButtonRecrystalizeListener());
        settingsPanel.add(buttonRecrystalize);

        add(settingsPanel, BorderLayout.NORTH);

        component =new Component(mesh);

        add(component, BorderLayout.CENTER);


        JPanel genPanel=new JPanel();
        genPanel.add(new JLabel("wygeneruj zarodki:"));

        JButton rand=new JButton("losowo");
        rand.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                mesh.generateRand();
                component.repaint();
            }
        });

        genPanel.add(rand);
        JButton r=new JButton("z promieniem");
        r.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int r=Integer.parseInt(JOptionPane.showInputDialog("Wprowadz promień"));
                mesh.generateR(r);
                component.repaint();

            }
        });

        genPanel.add(r);

        JButton con=new JButton("równomernie");
        con.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int distance=Integer.parseInt(JOptionPane.showInputDialog("Wprowadz odleglosc miedzy zarodkami"));
                mesh.generateConst(distance);
                component.repaint();;

            }
        });

        genPanel.add(con);

        add(genPanel, BorderLayout.SOUTH);



    }


    class ButtonStartListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e){
            JButton b=(JButton)e.getSource();

            if(Frame.this.mesh.isStarted()){
                b.setText("START");
                mesh.stop();
            }
            else
            {
                mesh.start();
                Thread t=new Thread(mesh, component, Frame.this);
                t.start();
                b.setText("STOP");
            }

        }
    }

    class ButtonRecrystalizeListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e){
            JButton b=(JButton)e.getSource();

            if(Frame.this.mesh.isRecStarted()){
                b.setText("rekrystalizuj");
                System.out.println("Zatrzymano proces rekrystalizacji");
                mesh.setRecStarted(false);
            }
            else
            {
                System.out.println("Uruchomiono proces rekrystalizacji");
                mesh.startRecrystalization();
                ThreadRec t=new ThreadRec(mesh, component, Frame.this);
                t.start();
                b.setText("STOP");
            }
        }
    }
}

