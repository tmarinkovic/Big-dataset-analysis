package sequential;

import common.Hamming;
import common.SimHashCommon;
import java.io.FileNotFoundException;



public class Sequential extends SimHashCommon {

    public void query ( String[] hashed , String[] querys  ) throws FileNotFoundException {
        for ( String query : querys){
            String [] info  = query.split("\\s+");
            int number        = Integer.parseInt(info[0]);
            int similarity    = Integer.parseInt(info[1]);
            System.out.println(solveQuery(hashed, number, similarity));
        }
    }


    private String solveQuery ( String[] hashed , int broj , int slicnost){
        int count = 0;
        Integer[] hamm = getHamming ( hashed , broj );
        for ( Integer each : hamm){
                if (each <= slicnost) {
                    count++;
                }
        }
        return String.valueOf(count-1);
    }


    private Integer[] getHamming ( String[] hashed , int broj){
        Integer[] res = new Integer[hashed.length];
        int i = 0;
        for ( String each : hashed ){
            res[i] = new Hamming(each , hashed[broj]).getHammingDistance();
            i++;
        }
        return res;
    }




}
