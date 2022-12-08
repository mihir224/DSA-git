import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Collections {
    public static void main(String[] args) {
        Theatre theatre =new Theatre("Wave", 9, 10);
        List<Theatre.Seat> seatCopy=new ArrayList<>(theatre.seats); //to copy contents of one list to another
        printList(seatCopy);

    }
    public static void printList(List<Theatre.Seat> list){ //to access a class inside a class
        for(Theatre.Seat seat:list){
            System.out.print(" " + seat.getSeatNo());

        }
    }
}
class Theatre{
    private String theatreName;
     Collection<Seat> seats=new ArrayList<>();
    public Theatre(String theatreName, int numRows, int seatsPerRow){
        this.theatreName=theatreName;
        int lastRow='A'+(numRows-1); //stores ascii value of A plus the no. of rows - 1
        for(char row='A';row<=lastRow;row++){ //iterates from A till the char of Ascii value of last row
            for(int seatNum=1;seatNum<=seatsPerRow;seatNum++){
                Seat seat=new Seat(row+String.format("%02d",seatNum));
                seats.add(seat);
            }
        }
    }
    public String getTheatreName() {
        return theatreName;
    }
    public boolean reserveSeat(String seatNo){
        Seat requestedSeat=null;
        for(Seat seat:seats){
            if(seat.getSeatNo().equals(seatNo)){
                requestedSeat=seat;
                break;
            }
        }
        if(requestedSeat==null){
            System.out.println("Sorry, this seat is not available");
            return false;
        }
        return requestedSeat.reserve();
    }
    public void getSeats(){ //for testing purposes
        for(Seat seat:seats){
            System.out.println(seat.getSeatNo());
        }
    }
    class Seat implements Comparable<Seat>{
        private String seatNo;
        private boolean reserve=false;
        public Seat(String seatNo){
            this.seatNo=seatNo;
        }

        @Override
        public int compareTo(Seat seat) {
            return this.seatNo.compareToIgnoreCase(seat.getSeatNo());
        }

        public boolean reserve(){

            if(!this.reserve){
                this.reserve=true;
                System.out.println("Seat " + seatNo + " reserved.");
                return true;
            }
            else{
                return false;
            }
        }
        public boolean cancel(){
            if(this.reserve){
                this.reserve=false;
                System.out.println("Seat " + seatNo + " cancelled.");
                return true;
            }
            else {
                return false;
            }
        }
        public String getSeatNo(){
            return seatNo;
        }


   }
}

