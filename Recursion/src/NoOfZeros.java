public class NoOfZeros {
    public static void main(String[] args) {
        System.out.println(count(1008770090));
    }
    static int count(int n){
        return helper(n,0);
    }
    static int helper(int n, int count){
        if(n==0){ //base condition
            return count;
        }
        int rem=n%10;
        if(rem==0){
            return helper(n/10,count+1);
        }
        else{
            return helper(n/10,count);
        }
    }
}
