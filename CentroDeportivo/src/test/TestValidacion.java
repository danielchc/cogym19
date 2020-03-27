package test;

public class TestValidacion {


    public static boolean isCorrectoDNI(String dni){
        if(dni.length()!=9 || !Character.isLetter(dni.charAt(8))) return false;
        for(int i=0;i<dni.length()-1;i++){
            if(!Character.isDigit(dni.charAt(i))) return false;
        }
        return true;
    }

    public static boolean isCorrectoIBAN(String iban){
        if(iban.length()!=22) return false;
        if(!Character.isLetter(iban.charAt(0)) || !Character.isLetter(iban.charAt(1))) return false;
        if(!Character.isDigit(iban.charAt(2)) || !Character.isDigit(iban.charAt(3))) return false;
        for(int i=4;i<8;i++){
            if(!Character.isLetter(iban.charAt(i))) return false;
        }
        for(int i=8;i<22;i++){
            if(!Character.isDigit(iban.charAt(i))) return false;
        }
        return true;
    }

    public static boolean isCorrectoNUSS(String nuss){
        if(nuss.length()<9 || nuss.length()>12) return false;
        for(int i=0;i<nuss.length();i++){
            if(!Character.isDigit(nuss.charAt(i))) return false;
        }
        return true;
    }





    public static void main(String[] args) {
        System.out.println(isCorrectoDNI("45903322O"));
        System.out.println(isCorrectoDNI("4590332XO"));
        System.out.println(isCorrectoDNI("45903322O2"));
        System.out.println(isCorrectoDNI("459 3322O"));
        System.out.println(isCorrectoDNI("45903322%"));
        System.out.println("----");
        System.out.println(isCorrectoIBAN("GB15MIDL40001212345678"));
        System.out.println(isCorrectoIBAN("GB15M2DL40001212345678"));
        System.out.println(isCorrectoIBAN("G115MIDL40001212B45678"));
        System.out.println(isCorrectoIBAN("GBA5MIDL40001212345678"));
        System.out.println("----");
        System.out.println(isCorrectoNUSS("123456789123"));
        System.out.println(isCorrectoNUSS("1234567891222"));
        System.out.println(isCorrectoNUSS("123456789112211"));


    }



}
