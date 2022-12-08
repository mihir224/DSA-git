import java.util.Scanner;

public class KeplersLaw {
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        int count=0;
        int T=scanner.nextInt();
        if(T>0&&T<10000) {
            while(count<=T) {
                double T1 = scanner.nextInt();
                double T2= scanner.nextInt();
                double R1= scanner.nextInt();
                double R2= scanner.nextInt();
                if((T1<=10&&T2>=1)&&(T2<=10&&T2>=1)&&(R1<=10&&R1>=1)&&(R2<=10&R2>=1))
                {
                    double t=Math.pow(T1,3)/Math.pow(R1,2);
                    double r=Math.pow(T2,3)/Math.pow(R2,2);
                    if(t==r)
                    {
                        System.out.println("Yes");
                    }
                    else
                    {
                        System.out.println("No");
                    }
                    count++;
                    scanner.nextLine();
                }
//                if (T1 <= 10 && T1 >= 1) {
//                    int T2 = scanner.nextInt();
//                    if (T2 <= 10 && T2 >= 1) {
//                        int R1 = scanner.nextInt();
//                        if (R1 <= 10 && R1 >= 1) {
//                            int R2 = scanner.nextInt();
//                            if (R2 <= 10 && R2 >= 1) {
//                                if ((T1 * T1) / (R1 * R1 * R1) == (T2 * T2) / (R2 * R2 * R2)) {
//                                    System.out.println("Yes");
//                                } else System.out.println("No");
//                                count++;
//                                scanner.nextLine();
//
//                            }
//                        }
//                    }
//                }
            }
        }
        scanner.close();
    }

}
