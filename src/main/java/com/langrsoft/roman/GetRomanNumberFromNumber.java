package com.langrsoft.roman;

public class GetRomanNumberFromNumber {
    public static String[] singleDigit = {"I","II","III","IV","V","VI","VII","VIII","IX"};
    public static String[] twoDigit = {"X","XX","XXX","XL","L","LX","LXX","LXXX","XC"};
    public static String[] threeDigit = {"C","CC","CCC","CD","D","DC","DCC","DCCC","CM"};
    public static String[] fourDigit = {"M","MM","MMM","MMM","V"};
    public String getRomanString1(int number){
        String value = String.valueOf(number);

        for(int ind = value.length()-1;ind>=1;ind--){
            int modValue = 1;
            for(int ind1 =0;ind1<ind;ind1++){
                modValue *= 10;
            }
            if(ind == value.length()){

            }
        }
        return null;
    }
    public String getRomanString(int number){
        StringBuilder romanStr = new StringBuilder();
        String temp = String.valueOf(number);
        String reverse ="";
        for(int ind=temp.length();ind>=1;ind--){
            reverse = reverse+String.valueOf(temp.charAt(ind-1));
        }
        temp = reverse;
        int index;
        for(int ind=temp.length();ind>=1;ind--){
            System.out.println(ind+":::"+String.valueOf(temp.charAt(ind-1)));
            int charIndex = Integer.parseInt(String.valueOf(temp.charAt(ind-1)))-1;
            boolean isHaveZero = false;
            if(String.valueOf(temp.charAt(ind-1)).equals("0"))
            {
                if(ind==1){
                    continue;
                }
                charIndex = Integer.parseInt(String.valueOf(temp.charAt(ind-2)))-1;
                isHaveZero = true;

            }
            //System.out.println(ind+":::"+String.valueOf(temp.charAt(ind-1)));

            if(ind == 4){
                romanStr.append(fourDigit[charIndex]);
            }else if(ind == 3){
                romanStr.append(threeDigit[charIndex]);
            }else if(ind == 2){
                romanStr.append(twoDigit[charIndex]);
            }else if(ind == 1){
                romanStr.append(singleDigit[charIndex]);
            }
            if(isHaveZero){
                ind--;
            }
        }
        return romanStr.toString();
    }

    public static void main(String[] arg){
        System.out.println("TETrrrTTT:"+new GetRomanNumberFromNumber().getRomanString(50));
    }
}
