package lsh;

public class SimHashBuckets {
    /*
     * Input files are proprietary so unfortunately they are not available
     */
    public static void main(String[] args) throws Exception {
        LSH lsh = new LSH();
        String hashed[] = lsh.getHashed("SimHash/data/input_lsh.txt");
        String querys[] = lsh.getQuery("SimHash/data/input_lsh.txt");
        lsh.go(hashed, querys, "SimHash/data/output_lsh.txt");

    }
}
