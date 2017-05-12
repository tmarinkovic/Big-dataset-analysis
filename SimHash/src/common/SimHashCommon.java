package common;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigInteger;

public class SimHashCommon {

    public int textNumber;

    public String[] getHashed(String ulazPath) throws Exception {
        int j = 0;
        String[] temp;
        try (BufferedReader br = new BufferedReader(new FileReader(ulazPath))) {
            String line = br.readLine();
            textNumber = Integer.parseInt(line);
            temp = new String[textNumber];
            while (j != textNumber) {
                line = br.readLine();
                temp[j] = simHash(line);
                j++;
            }
        }
        return temp;
    }

    public String simHash(String text) {

        int max = 128;
        int[] sh = new int[max];
        String[] jedinke = text.split("\\s+");
        StringBuilder rez = new StringBuilder();
        for (String each : jedinke) {
            String binary = full128(hexToBin(md5String(each)));
            for (int i = 0; i < binary.length(); i++) {
                if (binary.charAt(i) == '1') {
                    sh[i]++;
                } else {
                    sh[i]--;
                }
            }
        }

        for (int i = 0; i < max; i++) {
            if (sh[i] >= 0) sh[i] = 1;
            else sh[i] = 0;
            rez.append(sh[i]);
        }
        return full32(new BigInteger(rez.toString(), 2).toString(16));
    }


    public String[] getQuery(String inputPath) throws Exception {
        int j = 0;
        String[] temp;
        try (BufferedReader br = new BufferedReader(new FileReader(inputPath))) {
            while (j != textNumber + 1) {
                br.readLine();
                j++;
            }
            int brojQuerya = Integer.parseInt(br.readLine());
            temp = new String[brojQuerya];
            String line = br.readLine();
            j = 0;
            while (line != null) {
                temp[j] = line;
                line = br.readLine();
                j++;
            }
        }
        return temp;
    }


    public String full128(String binary) {
        if (binary.length() != 128) {
            int number = 128 % binary.length();
            StringBuilder binaryBuilder = new StringBuilder(binary);
            for (int i = 0; i < number; i++) {
                binaryBuilder.insert(0, "0");
            }
            binary = binaryBuilder.toString();
        }
        return binary;
    }

    public String full32(String hex) {
        if (hex.length() != 32) {
            int number = 32 % hex.length();
            StringBuilder hexBuilder = new StringBuilder(hex);
            for (int i = 0; i < number; i++) {
                hexBuilder.insert(0, "0");
            }
            hex = hexBuilder.toString();
        }
        return hex;
    }

    public String md5String(String text) {
        return DigestUtils.md5Hex(text);
    }

    public String hexToBin(String hex) {
        return new BigInteger(hex, 16).toString(2);
    }
}
