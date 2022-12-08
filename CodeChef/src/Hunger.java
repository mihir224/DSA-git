import java.util.Scanner;

public class Hunger {
    public static void main(String[] args) {
            Scanner scanner=new Scanner(System.in);
            {
                int t= scanner.nextInt();
                for(int i=0;i<t;i++)
                {
                    int balance=scanner.nextInt();
                    int pizzaPrice=scanner.nextInt();
                    int burgerPrice=scanner.nextInt();
                    if(pizzaPrice<=balance)
                    {
                        System.out.println("PIZZA");
                    }
                    else if(burgerPrice<=balance)
                    {
                        System.out.println("BURGER");
                    }
                    else if(burgerPrice==pizzaPrice)
                    {
                        System.out.println("PIZZA");
                    }
                    else
                        System.out.println("NOTHING");
                }
            }scanner.close();
    }
}
