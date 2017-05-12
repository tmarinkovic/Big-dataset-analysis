import java.io.*;
import java.util.*;

public class ParkChen {

    private int threshold;
    private String slotNumber;

    private BufferedReader init() throws Exception {
        /*
         * Input files are proprietary so unfortunately they are not available
         */
        return new BufferedReader(new FileReader("ParkChenYu/data/input.in"));
    }

    public void go() throws Exception {

        BufferedReader br = init();
        String bucketNumber = br.readLine();
        String s = br.readLine();
        slotNumber = br.readLine();
        threshold = (int) (Float.parseFloat(s) * Integer.parseInt(bucketNumber));
        List<String> buckets = getBuckets(br);
        // first pass
        int[] numberOfItems = firstPass(buckets);
        int A = getA( numberOfItems );
        // second pass
        int[] slots = new int[Integer.parseInt(slotNumber)];
        slots = secondPass( buckets , numberOfItems , slots );
        // third pass
        // construct all pairs
        HashMap<List<Integer> , Integer> pairs = new HashMap<>();
        for ( int i = 1 ; i <= 100 ; i++ ){
            for ( int j = i+1 ; j <= 100 ; j++){
                List<Integer> pair = new ArrayList<>();
                pair.add(i);
                pair.add(j);
                pairs.put(pair , 0);
            }
        }
        pairs = thirdPass( pairs , buckets , numberOfItems , slots );
        List<Integer> result = new ArrayList<>();

        for(List<Integer> key : pairs.keySet()){
            if ( pairs.get(key) != 0){
                result.add(pairs.get(key));
            }
        }
        Collections.sort(result);

        // output
        System.out.println(A);
        System.out.println(result.size());
        for ( int i = result.size()-1 ; i >= 0 ; i--){
            System.out.println(result.get(i));
        }

        // create file with results
        PrintWriter writer;
        writer = new PrintWriter("ParkChenYu/data/output.out", "UTF-8");
        writer.println(pairs.size());
        writer.println(result.size());
        for ( int i = result.size()-1 ; i >= 0 ; i--){
            writer.println(result.get(i));
        }
        writer.close();



    }

    private int getA ( int[] brPredmeta ){
        int m = 0;
        for ( int predmet : brPredmeta ){
            if ( predmet != 0) m++;
        }

        return (m*(m-1))/2;

    }

    private HashMap<List<Integer> , Integer> thirdPass(HashMap<List<Integer> , Integer> pairs , List<String> buckets , int[] numberOfItems , int[] slots){
        for ( String bucket : buckets){
            String[] itemsInBucket = bucket.split(" ");
            pairs = countPairs(itemsInBucket, pairs , numberOfItems , slots );
        }
        return pairs;
    }

    private HashMap<List<Integer> , Integer> countPairs ( String[] itemsInBucket , HashMap<List<Integer> , Integer> pairs , int[] numberOfItems , int[] slots){
        for ( int i = 0 ; i < itemsInBucket.length ; i++ ){
            for ( int j = i+1 ; j < itemsInBucket.length ; j++){
                Integer first = Integer.parseInt(itemsInBucket[i]);
                Integer second = Integer.parseInt(itemsInBucket[j]);
                if ( !first.equals(second) ) {
                    if (numberOfItems[first] >= threshold && numberOfItems[second] >= threshold) {
                        int k = ((first * numberOfItems.length + second) % Integer.parseInt(slotNumber));
                        if( slots[k] >= threshold){
                            List<Integer> pair = new ArrayList<>();
                            pair.add(first);
                            pair.add(second);
                            pairs.put(pair, pairs.get(pair) + 1);
                        }
                    }
                }
            }
        }
        return pairs;
    }

    private List<String> getBuckets(BufferedReader br ) throws Exception{
        List<String> buckets = new ArrayList<>();
        String bucket = br.readLine();
        while (bucket != null) {
            buckets.add(bucket);
            bucket = br.readLine();
        }
        return buckets;
    }


    private int getMax ( List<String> buckets ) {
        int max = 0;
        for ( String bucket : buckets){
            String[] itemsInBucket = bucket.split(" ");
            for ( String item : itemsInBucket){
                if ( Integer.parseInt(item) > max) max = Integer.parseInt(item);
            }
        }
        return max;
    }

    private int[] firstPass(List<String> buckets ){
        int[] numberOfItems = new int[getMax(buckets) + 1];
        for ( String bucket : buckets){
            String[] itemsInBucket = bucket.split(" ");
            for ( String item : itemsInBucket){
                numberOfItems[Integer.parseInt(item)]++;
            }
        }
        return numberOfItems;
    }


    private int[] secondPass(List<String> buckets , int[] numberOfItems , int[] slots ){
        for ( String bucket : buckets){
            String[] itemsInBucket = bucket.split(" ");
            slots = getPairs ( itemsInBucket , numberOfItems , slots );
        }
        return slots;
    }

    private int[] getPairs( String[] itemsInBucket , int[] numberOfItems , int[] slots ){
        for ( int i = 0 ; i < itemsInBucket.length ; i++ ){
            for ( int j = i+1 ; j < itemsInBucket.length ; j++){
                Integer first = Integer.parseInt(itemsInBucket[i]);
                Integer second = Integer.parseInt(itemsInBucket[j]);
                if ( !first.equals(second) ) {
                        if (numberOfItems[first] >= threshold && numberOfItems[second] >= threshold) {
                            int k = ((first * numberOfItems.length + second) % Integer.parseInt(slotNumber));
                            slots[k]++;
                        }
                }
            }
        }
        return slots;
    }



}
