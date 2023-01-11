public class BarkingDog {
    public static boolean shouldWakeUp(boolean barking, int hourOfDay)
    {
        if(hourOfDay <= 23 && hourOfDay >= 0) {
            if (hourOfDay < 8 || hourOfDay > 22) {

                if (barking) {
                    return true;
                } else {
                    return false;
                }
            }
            return false;
        }
        return false;
    }
}
