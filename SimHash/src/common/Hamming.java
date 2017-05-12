package common;

import sequential.Sequential;

public class Hamming {

    private String compOne;
    private String compTwo;

    public Hamming(String one, String two)
    {
        Sequential help = new Sequential();
        compOne = help.full128(help.hexToBin(one));
        compTwo = help.full128(help.hexToBin(two));
    }



    ///
    //  Calculating the common.Hamming Distance for two strings requires the string to be of the same length.
    ///
    public int getHammingDistance()
    {
        if (compOne.length() != compTwo.length())
        {
            return -1;
        }

        int counter = 0;

        for (int i = 0; i < compOne.length(); i++)
        {
            if (compOne.charAt(i) != compTwo.charAt(i)) counter++;
        }

        return counter;
    }

}