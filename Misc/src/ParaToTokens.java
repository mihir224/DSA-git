import java.util.StringTokenizer;
class myException1 extends Exception{
    public myException1(String s){
        super(s);
    }
}
public class ParaToTokens {
    public static void main(String[] args) {
        ParaToTokens paraToTokens=new ParaToTokens("mi");
       paraToTokens.printTokens();
        try{

            throw new myException1("mihir");
        }
        catch(myException1 ex){
            System.out.println("Caught");
            System.out.println(ex.getMessage());
        }

    }
    String str;
    StringTokenizer st;

    public ParaToTokens(String str) {
        this.str = str;
        this.st = new StringTokenizer(str,",");
    }
    public void printTokens(){
        while(st.hasMoreTokens()){
            System.out.println(st.nextToken());

        }
    }


}
