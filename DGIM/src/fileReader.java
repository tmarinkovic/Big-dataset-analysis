import java.io.BufferedReader;
import java.util.*;

public class fileReader {
    public List<String> read(String red, String delimiter){
        List<String> response = new ArrayList<>();
        String[] split = red.split("\\"+delimiter);
        Collections.addAll(response, split);
        return response;
    }

    public List<Integer> readInt(String red, String delimiter){
        Scanner scanner = new Scanner(red);
        List<Integer> list = new ArrayList<>();
        while (scanner.hasNextInt()) {
            list.add(scanner.nextInt());
        }
        return list;
    }

    public LinkedHashMap<List<String> , List<String>> getOverflow(BufferedReader br) throws Exception{
        LinkedHashMap<List<String> , List<String>> overflows = new LinkedHashMap<>();
        String line = br.readLine();
        while (line != null) {
            List<String> overflow = read(line, "->");
            overflows.put(read(overflow.get(0), ","), read(overflow.get(1), ","));
            line = br.readLine();
        }
        return overflows;
    }
    public List<String> entry(String currentState , String alphabetSymbol){
        List<String> res = new ArrayList<>();
        res.add(currentState);
        res.add(alphabetSymbol);
        return res;
    }
    public boolean isInList (String value , List<String> list){
        for ( String each : list){
            if ( each.equals(value)) return true;
        }
        return false;
    }

    public String formatResponse ( String response , List<String> states ){
        String formated = "";
        List<String> lines = read( response , "|");
        for ( String line : lines){
            List<String> por = read ( line , ",");
            if ( por.size() == 1){
                formated +=line+"|";
            }else{
                HashMap<Integer, String> order = getOrder( por , states );
                for (HashMap.Entry<Integer, String> entry : order.entrySet()) {
                    formated += entry.getValue()+",";
                }
                formated = removeLastChar(formated);
                formated += "|";
            }
        }
        return removeLastChar(formated);
    }

    public String removeLastChar(String str) {
        return str.substring(0,str.length()-1);
    }


    private HashMap<Integer, String> getOrder(List<String> por , List<String> states){
        HashMap<Integer, String> order = new HashMap<>();
        for ( String state : por){
            order.put(states.indexOf(state) , state);
        }
        return order;
    }

}
