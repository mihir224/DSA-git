import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String sst="mihir";

        System.out.println(sst);
    }
    public static List<List<Integer>> findPrimePairs(int n) {
        int x=1;
        int y=9;
        List<List<Integer>> ans=new ArrayList<>();
        while(x<=y){
            if(x+y==n){
                if(!isPrime(x)||!isPrime(y)){
                    continue;
                }
                List<Integer> list=new ArrayList<>();
                list.add(x);
                list.add(y);
                ans.add(list);
            }
            x++;
            y--;
        }
        return ans;
    }
    //if for N numbers we try to check whether they are prime or not, complexity would be O(N*sqrt(N)), thus we use the sieve of erathosthenes
    public static boolean isPrime(int a){
        if(a==0||a==1){
            return false;
        }
        for(int i=2;i<=Math.sqrt(a);i++){
            if(a%i==0){
                return false;
            }
        }
        return true;
    }
}
