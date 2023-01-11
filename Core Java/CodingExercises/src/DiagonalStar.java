public class DiagonalStar {
    public static void printSquareStar(int number)
    {
        if(number<5)
        {
            System.out.println("Invalid Value");
        }
        else
        for(int i=0;i<number;i++)
        {
            for(int j=0;j<number;j++)
            {
                if(i==0||i==number-1//for first and last row
                        ||j==0||j==number-1//for first and second column
                        ||j==i||j==number-1-i//for first and second diagonal
                ) System.out.print("*");
                    else
                    System.out.print(" ");
            }
            System.out.println("");
        }
    }
}
