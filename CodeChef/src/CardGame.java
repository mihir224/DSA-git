import java.util.Scanner;

public class CardGame {
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        {
            int t=scanner.nextInt();
            for(int i=0; i<t;i++) {
                int cards = 0;
                int k = scanner.nextInt();
                System.out.println(52 % k);

            }

        }scanner.close();
    }
}
