/**
 * Created by apple on 16.05.2016.
 */
public class Field {

    private int id;
    private double ro;
    private boolean recrystalized;
    private boolean onBorder;


    public Field(){
        id=0;
        ro=0;
        recrystalized=false;
        onBorder=false;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getRo() {
        return ro;
    }

    public void setRo(double ro) {
        this.ro = ro;
    }

    public boolean isRecrystalized() {
        return recrystalized;
    }

    public void setRecrystalized(boolean recrystalized) {
        this.recrystalized = recrystalized;
    }

    public boolean isOnBorder() {
        return onBorder;
    }

    public void setOnBorder(boolean onBorder) {
        this.onBorder = onBorder;
    }
}
