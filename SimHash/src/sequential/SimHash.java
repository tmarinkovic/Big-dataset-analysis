package sequential;

public class SimHash {
    /*
     * Input files are proprietary so unfortunately they are not available
     */
    public static void main(String[] args) throws Exception {
        Sequential sequential = new Sequential();

        String hashed[] = sequential.getHashed("SimHash/data/input_sequential.txt");
        String querys[] = sequential.getQuery("SimHash/data/input_sequential.txt");

        sequential.query(hashed, querys);
    }
}
