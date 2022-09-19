import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Scanner;

public class LinkedLis {
    public static void main(String[] args) {
        LinkedList<String> ll=new LinkedList<String>();
        addInAlphOrder(ll, "Haridwar");
        addInAlphOrder(ll, "Delhi");
        addInAlphOrder(ll, "Roorkee");
        addInAlphOrder(ll, "Dehradun");
        addInAlphOrder(ll, "Noida");
        addInAlphOrder(ll, "Gwalior");
        print(ll);
        vacation(ll);


//        ll.add("HARIDWAR");
//        ll.add("DELHI");
//        ll.add("ROORKEE");
//        ll.add("DEHRADUN");
//        ll.add("NOIDA");
//        print(ll);
//        ll.add(1, "Gwalior");
//        print(ll);
//        ll.remove(4);
//        print(ll);
    }

        private static void print(LinkedList<String> linkedList){
            Iterator<String> i=linkedList.iterator();
            System.out.println("Places to visit: ");
            while (i.hasNext()){
                System.out.println(i.next()); // here next() points i to the very first element of the list
            }
    }
        private static boolean addInAlphOrder(LinkedList<String> linkedList, String newCity){
            ListIterator<String> stringListIterator=linkedList.listIterator();
            while(stringListIterator.hasNext()){
                int compare=stringListIterator.next().compareTo(newCity);
                if(compare==0){
                    System.out.println("This city is already included as a destination. ");
                    return false;
                }
                else if(compare>0){ //entered string comes before the current entry in alphabetic order
//                    System.out.println("Adding " + newCity + " as a destination... ");
                    stringListIterator.previous(); //moves to the entry before the current entry
                    stringListIterator.add(newCity);
                    return true;
                }
                else if(compare<0){
                    //entered string comes after the current entry in alphabetic order
                    //entry moves to the next element automatically as the next function is used above
                }
            }
            stringListIterator.add(newCity);
            return true;
    }
    private static void printVacationMenu(){
        System.out.println("Press");
        System.out.println("1. To move to the next city");
        System.out.println("2. To go back to the next city");
        System.out.println("3. To go back to the menu");
    }
        private static void vacation(LinkedList<String> linkedList){
            Scanner scanner=new Scanner(System.in);
        ListIterator<String> listIterator=linkedList.listIterator();
        boolean quit=false;
        boolean goingFwd=true;
        printVacationMenu();
        while(!quit){
            int choice=scanner.nextInt();
            switch(choice){
                case 0:
                    System.out.println("Thanks for visiting. Come back soon!");
                    quit=true;
                    break;
                case 1:
                    if(!goingFwd){
                        if(listIterator.hasNext()){
                            listIterator.next();
                        }
                        goingFwd=true;
                    }
                    if(listIterator.hasNext()){
                        System.out.println("Going to " + listIterator.next());
                    }else{
                        System.out.println("Reached the end of the list. No more cities left to visit!");
                        goingFwd=false;
                    }
                    break;
                case 2:
                    if(goingFwd){
                        if(listIterator.hasPrevious()){
                            listIterator.previous();
                        }
                        goingFwd=false;
                    }
                    if(listIterator.hasPrevious()){
                        System.out.println("Going back to: " + listIterator.previous());
                    }
                    else
                    {
                        System.out.println("Back to where we started. ");
                        goingFwd=true;
                    }
                    break;
                case 3:
                    printVacationMenu();
                    break;
            }

        }
        }
}

