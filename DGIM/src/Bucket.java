import java.util.ArrayList;
import java.util.List;

public class Bucket {

    private int count;
    private List<Integer> data;
    private int size;
    private int type;

    public Bucket ( int size , int type ){
        this.data = new ArrayList<>();
        this.count = 0;
        this.size = size;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public int getSize(){
        return size;
    }

    public List<Integer> getData() {
        return data;
    }

    public void setData(List<Integer> data) {
        this.data = data;
    }

}
