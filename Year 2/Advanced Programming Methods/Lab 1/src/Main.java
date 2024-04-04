public class Main {
    public static void main(String[] args) {
        int sum = 0;
        try {
            for (String arg : args) {
                sum = sum + Integer.parseInt(arg);
            }
            try {
                int media = sum / args.length;
                System.out.println(media);
            } catch (java.lang.ArithmeticException e) {
                System.out.println("Please enter some numbers!");
            }
        } catch (java.lang.NumberFormatException e) {
            System.out.println("There is an invalid character in the string!");
        }
    }
}