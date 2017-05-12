import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

class DG extends fileReader {

    private List<Bucket> buckets = new ArrayList<>();
    private int N = 0;
    private int globalType = 0;
    private int globalReverse = 0;


    private BufferedReader init() throws Exception {
        /*
            * Input files are proprietary so unfortunately they are not available
        */
        return new BufferedReader(new FileReader("entry.txt"));
    }

    private void write() {
        for (Bucket each : buckets) {
            System.out.print("BUCKET ( size: " + each.getData().size() + " ) DATA: ");
            System.out.print(each.getData());
            System.out.println();
        }
    }

    void solve() throws Exception {
        BufferedReader br = init();

        String line = br.readLine();
        Integer window = Integer.parseInt(line);
        //max = getMaxBucketSize( window );
        line = br.readLine();


        while (line != null) {
            if (line.contains("q")) {
                solveQuery(read(line, " ").get(1));
            } else {
                moreBits(line, window);
            }
            line = br.readLine();
        }

    }

    private void solveQuery(String size) {
        int solved = 0;
        int count = 0;
        int i;
        for (i = buckets.size() - 1; i >= 0; i--) {
            if (Integer.parseInt(size) >= count) {
                count += buckets.get(i).getData().size();
            } else {
                break;
            }
        }
        i++;


        for (int j = i; j < buckets.size(); j++) {

            if (buckets.get(j).getData().size() == 1 && buckets.get(j).getData().get(0) == 0) {
                i++;
                continue;
            }

            if (j == i) {
                solved += (int) Math.ceil(countOnes(buckets.get(j).getData()) / 2);
            } else {
                solved += countOnes(buckets.get(j).getData());
            }
        }
        System.out.println(solved);
    }

    private int countOnes(List<Integer> data) {
        int count = 0;
        for (int each : data) {
            if (each == 1) {
                count++;
            }
        }
        return count;
    }

    private void moreBits(String line, int window) {
        for (int i = 0; i < line.length(); i++) {
            Integer each = Integer.parseInt(String.valueOf(line.charAt(i)));
            if (countInAllBuckets() > window && oneExtra(window)) {
                buckets.remove(0);
            }
            buckets.add(new Bucket(0, each));
            List<Integer> entry = new ArrayList<>();
            entry.add(each);
            buckets.get(buckets.size() - 1).setData(entry);
            if (needMerge()) {
                merge();
            }
            N = (N + 1) % window;
        }
    }

    private boolean oneExtra(int window) {
        int count = countInAllBuckets();
        return count - buckets.get(0).getData().size() > window;
    }

    private int countInAllBuckets() {
        int count = 0;
        for (Bucket each : buckets) {
            count += each.getData().size();
        }
        return count;
    }

    private void merge() {
        int sizeOfBucket = 0;
        while (numberOfBuckets(sizeOfBucket) == 3) {
            int[] index = getIndex(sizeOfBucket, globalType);
            doMerge(index, sizeOfBucket);
            sizeOfBucket++;
        }
    }

    private void doMerge(int[] index, int bucketSize) {
        globalType = 1;
        int newSize = bucketSize + 1;
        List<Integer> result = new ArrayList<>();
        for (int i = index[0]; i <= index[1]; i++) {
            result.addAll(buckets.get(i).getData());
        }

        buckets.add(new Bucket(newSize, 1));
        buckets.get(buckets.size() - 1).setData(result);
        for (int i = index[1]; i >= index[0]; i--) {
            buckets.remove(i);
        }

        reverse(index[0]);
    }

    private void reverse(int pos) {
        List<Bucket> temp = new ArrayList<>();

        if (globalReverse == 0) {
            for (int i = buckets.size() - 1; i >= 0; i--) {
                temp.add(buckets.get(i));
            }
        }

        if (globalReverse == 1) {
            temp = buckets;
            Bucket newBucket = temp.get(temp.size() - 1);
            temp.add(pos, newBucket);
            temp.remove(temp.size() - 1);
        }


        buckets = temp;
        globalReverse = 1;
    }

    private int[] getIndex(int size, int type) {
        int prvi = 1;
        int drugi = 2;
        if (type == 1) {
            prvi = 0;
            drugi = 1;
        }
        int[] result = new int[2];
        int count = 0;
        int index = 0;
        for (Bucket each : buckets) {
            if (each.getSize() == size && each.getType() == 1) {
                if (count == prvi) {
                    result[0] = index;
                }
                if (count == drugi) {
                    result[1] = index;
                }
                count++;
            }
            index++;
        }
        return result;
    }

    private boolean needMerge() {
        return numberOfBuckets(0) > 2;
    }

    private int numberOfBuckets(int size) {
        int count = 0;
        for (Bucket each : buckets) {
            if (each.getSize() == size && each.getType() == 1) {
                count++;
            }
        }
        return count;
    }

}
