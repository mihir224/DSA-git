import java.util.Scanner;

public class MaxArray {
    public static int maxofArr(int[] arr,int n)
    {
        int max;
        max=arr[0];
        for(int i=0;i<n;i++)
        {
            if(arr[i]>max)
            {
                max=arr[i];
                return max;
            }
            else {
                return -1;
            }
        }
      return -1;
    }

    public static void main(String[] args) {
        int n;
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter the number of terms: ");
        n=sc.nextInt();
        int[] arr=new int[n];
        for(int i=0;i<n;i++)
        {
            System.out.println("Enter num " + i);
            arr[i]=sc.nextInt();
        }
        int ans=maxofArr(arr,n);
        System.out.println(ans);
    }
}
