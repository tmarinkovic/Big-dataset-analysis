package lsh;

import common.Hamming;
import common.SimHashCommon;
import java.io.PrintWriter;
import java.util.*;

public class LSH extends SimHashCommon {

    public static int BAND_NUMBER = 8;


    private int hash2int ( String hash , int pojas){
        String binary = full128(hexToBin(hash));
        String cut = binary.substring( binary.length()-(16*pojas), binary.length()-(16*(pojas-1)));
        return Integer.parseInt(cut, 2);
    }
    private HashMap <Integer ,List<Integer>> getCandidates(String[] hashed ){
        HashMap <Integer ,List<Integer>> candidates = new HashMap<>();
        for (int pojas = 1; pojas <= BAND_NUMBER; pojas++) {
            HashMap <Integer ,List<Integer>> slots = new HashMap<>();
            for ( int i = 0 ; i < hashed.length ; i++){
                String hash = hashed[i];
                int val = hash2int(hash , pojas);
                List<Integer> textsInSlot;
                if ( slots.get(val) != null){
                    textsInSlot = slots.get(val);
                    for ( int j = 0 ; j < textsInSlot.size() ; j++){
                        //
                        if ( candidates.get(i) == null){
                            candidates.put(i , new ArrayList<>());
                        }
                        candidates.get(i).add(textsInSlot.get(j));
                        //
                        if ( candidates.get(textsInSlot.get(j)) == null){
                            candidates.put(textsInSlot.get(j) , new ArrayList<>());
                        }
                        candidates.get(textsInSlot.get(j)).add(i);
                    }
                }else{
                    textsInSlot = new ArrayList<>();
                }
                textsInSlot.add(i);
                slots.put(val , textsInSlot);
            }
        }
        return candidates;
    }


    public void go( String[] hashed , String[] querys , String outputPath) throws Exception{
        HashMap <Integer ,List<Integer>> candidates = getCandidates( hashed );

        PrintWriter writer = new PrintWriter(outputPath);
        for ( String query : querys){
            String [] info  = query.split("\\s+");
            int broj        = Integer.parseInt(info[0]);
            int slicnost    = Integer.parseInt(info[1]);
            writer.println(solveQuery(hashed , candidates , broj , slicnost));
        }
        writer.close();
    }


    private String solveQuery ( String[] hashed ,  HashMap <Integer ,List<Integer>> candidates , int broj , int slicnost){
        int count = 0;
        Integer[] hamm = getHamming ( candidates , broj ,hashed );
        if ( hamm == null ) return String.valueOf(0);
        for ( Integer each : hamm){
            if (each <= slicnost) {
                count++;
            }
        }
        return String.valueOf(count);
    }

    private Integer[] getHamming ( HashMap <Integer ,List<Integer>> candidates , int broj , String[] hashed){
        if ( candidates.get(broj) == null) return null;
        List<Integer> textovi = candidates.get(broj);
        Set<Integer> stextovi = new LinkedHashSet<>(textovi);
        Integer[] res = new Integer[stextovi.size()];
        int i = 0;
        for ( Integer each : stextovi ){
            res[i] = new Hamming(hashed[each] , hashed[broj]).getHammingDistance();
            i++;
        }
        return res;
    }
}
