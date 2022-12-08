import java.util.Scanner;

public class codeF {
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        {
           int n=scanner.nextInt();
           int t=scanner.nextInt();
           for(int i=0;i<n;i++)
           {
               for(int j=0;j<t;j++) {
                   int[] arr = new int[t];
                   arr[j]=scanner.nextInt();
                   if (arr[j] < t) {
                       System.out.println(n/t);
                   } else System.out.println(n);
               }
           }
        }scanner.close();
    }
}
