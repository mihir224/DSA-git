import java.util.ArrayList;

public class MobilePhone {
    private String myNumber;
    private static ArrayList<Contact> myContact=new ArrayList<Contact>();

    public MobilePhone(String phoneNum) {
        this.myNumber = phoneNum;
        this.myContact = new ArrayList<>();
    }
    public static boolean addNewContact(Contact contact){
        if(findContact(contact)!=-1){
            return false;
        }
        else return myContact.add(contact);
    }
    public static boolean updateContact(Contact num, Contact newNum){
        if(findContact(num)!=1){
            myContact.set(findContact(num), newNum);
            return true;
        }
        else return false;

    }
    public static boolean removeContact(Contact contact){
        if(findContact(contact)!=-1){
            myContact.remove(contact);
            return true;
        }
        else return false;

    }

    public static int findContact(Contact contact){
    if(myContact.contains(contact)){
        return myContact.indexOf(contact);
    }
    else return -1;
    public static Contact queryContact(){}

}}