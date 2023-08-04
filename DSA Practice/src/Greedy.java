import java.util.*;

class Greedy{

    //N meetings in one room
    //https://practice.geeksforgeeks.org/problems/n-meetings-in-one-room-1587115620/1

    //approach: put the st,ft and number of all meetings in a list and then sort that list acc to increasing ft. This is
    // because if we do the meetings which finish early in the day, then we can accommodate more meetings. We take initial
    // count as 1, assuming the first meeting has been started, and we store that meeting's end time, and start iterating
    // through the list from the second meeting and simply check if it is possible to do the current meeting after the checking
    // the prev meeting's finish time and if it is, we increase the count and update prevFt to current meeting's ft


    //O(NlogN) tc, O(N) sc

    static class Pair{
        int st;
        int ft;
        public Pair(int st, int ft){
            this.st=st;
            this.ft=ft;
        }
    }
    public static int maxMeetings(int start[], int end[], int n)
    {
        List<Pair> list=new ArrayList<>();
        for(int i=0;i<n;i++){
            list.add(new Pair(start[i],end[i]));
        }
        Collections.sort(list,(a, b)->(a.ft-b.ft));
        int prevFt=list.get(0).ft;
        int count=1;
        for(int i=1;i<n;i++){
            if(list.get(i).st>prevFt){
                count++;
                prevFt=list.get(i).ft;
            }
        }
        return count;
    }

    //min platforms
    //https://practice.geeksforgeeks.org/problems/minimum-platforms-1587115620/1#
    //the reason for sorting the arrays was because we're trying to scroll through time to calculate the number of
    // platforms occupied at any point of time. This is only possible if the times are sorted (then only we'll be able to
    // make co relation between an arriving train and a departing train and assign to calculate the platforms occupied in
    // the most optimal way)
    //O(2NlogN) + O(2N) time


    //first ask the interviewer if the arrival time is sorted. that would automatically sort the departure time and then
    // we can apply our approach directly. just assume in this case that first train has arrived for a much more intuitive
    // approach
    static int findPlatform(int arr[], int dep[], int n)
    {
        Arrays.sort(arr);
        Arrays.sort(dep);
        int minPlatforms=1;
        int i=0;
        int j=0;
        int platformsOcc=0;
        while(i<n&&j<n){
            if(arr[i]<=dep[j]){
                platformsOcc++;
                i++;
            }
            else {
                platformsOcc--;
                j++;
            }
            if(platformsOcc>minPlatforms){
                minPlatforms=platformsOcc;
            }
        }
        return minPlatforms;
    }

    //job sequencing problem
    //https://practice.geeksforgeeks.org/problems/job-sequencing-problem-1587115620/1

    //since we have to perform the jobs to get the max profit, we sort the given job array in decreasing order of profits
    // so that we can perform the jobs with higher profits at first. This is what we can think of greedily. Now to find when
    // to perform a specific job, we can use the analogy that if we try to perform each job on the last day of its deadline,
    // then we'd be able to accommodate doing more jobs in the days prior to the last day of that job's deadline. Thus, we find
    // the max deadline m among all jobs and create an array of size m+1 where each item denotes a day from 1 to last day of
    // max deadline. This array is filled with -1 denoting that no job has been done yet on any day. Then we simply traverse
    // through all jobs and for each job we check if we can do that job on the last day of its deadline ie if no other job
    // has been done on the same day. If we can, we just fill that day in the deadline array with the index of the current
    // job. Otherwise, we check days prior to that day and fill the one that's empty. Each time we do a job on a particular
    // day (ie fill that day in deadline array), we add that job's profit to the total profit yet and simultaneously count
    // the number of jobs done till now. Then we break from the inner loop through which we were traversing the days prior
    // to the last day of current job's deadline to move onto the next job. Through this approach, we try to do each job
    // on the last day of its deadline, and we do the jobs with higher profits first in order to maximize the profits,
    // counting the number of jobs that we could do along the way.

    //intuition: we try to perform the job with the longer deadline as late as possible so that we can perform another job
    // with shorted deadline in the earlier days (this way we can make best use of all jobs)

    //we can also return the sequence in which jobs were performed if asked, basically using the order in which we did each job

    //tc O(NLogN + N*M) considering that size of the longest deadline is M
    //sc O(M)

    class Job {
        int id, profit, deadline;
        Job(int x, int y, int z){
            this.id = x;
            this.deadline = y;
            this.profit = z;
        }
    }
    int[] JobScheduling(Job arr[], int n) {
        Arrays.sort(arr, (a, b) -> (b.profit - a.profit));
        int maxDeadline = -1;
        int count = 0;
        int maxProfit = 0;
        for (Job job : arr) {
            if (job.deadline > maxDeadline) {
                maxDeadline = job.deadline;
            }
        }
        int[] deadline = new int[maxDeadline + 1];
        Arrays.fill(deadline, -1);
        for (Job job : arr) {
            for (int i = job.deadline; i >= 1; i--) {
                if (deadline[i] == -1) {
                    deadline[i] = job.id;
                    maxProfit += job.profit;
                    count++;
                    break;
                }
            }
        }
        return new int[]{count, maxProfit};
    }

    //fractional knapsack
    //https://practice.geeksforgeeks.org/problems/fractional-knapsack-1587115620/1

    //approach: we are allowed to take fraction of the given elements. Now in order to maximize profit, we can greedily
    // think of an analogy that if we start filling elements with higher profit or value per unit weight then by the time
    // the bag is filled, we will surely have the max profit. Thus, we first sort the given items in decreasing order of
    // their val/wt, and then we start filling the knapsack. In each iteration, if it is possible to take the whole item,
    // we take it. This is because we are allowed to take fraction of elements, and it is better to take the entire item
    // with higher value/wt than taking a fraction of it. Moreover, there might be a possibility that the total value of
    // the item would seem greater but actually its value/wt would be lesser and in case of fractional knapsack val/wt
    // matters. Thus, we always try taking the entire item and if we cannot, we take a fraction of the item (with wt equal
    // to the remaining capacity of the knapsack). The fraction of the item we pick at the last was the one with the least
    // val/wt among all the items we took previously. This ensured that we took items in a way that the profit had been
    // maximized.

    //intuition: losing even a fraction of an item of higher val/wt would have a much higher impact on reducing the profit rather than
    // losing fraction of an item with lower val/wt

    //O(NlogN+N) time
    class Item {
        int value, weight;
        Item(int x, int y){
            this.value = x;
            this.weight = y;
        }
    }
    class ItemComparator implements Comparator<Item> { //a class that implements a functional interface
        @Override
        public int compare(Item a, Item b) {
            double vpw1=(double)a.value/(double)a.weight;
            double vpw2=(double)b.value/(double)b.weight;
            if(vpw1>vpw2)
            {
                return -1;
            }
            else if(vpw1<vpw2){
                return 1;
            }
            else{
                return 0;
            }
        }
    }

    double fractionalKnapsack(int W, Item arr[], int n)
    {
        double profit=0.0;
        Arrays.sort(arr,new ItemComparator()); //by using the instance of the item comparator class here, we're basically
        // telling the sort function to use the implementation of the compare function in this class to sort the elements
        // in the given array
        for(Item item:arr){
            if(item.weight<=W){
                profit+=item.value;
                W-=item.weight;
            }
            else{ //ie item's wt > W so we take a fraction of it
                profit+=(double)W*((double)(item.value)/(double)(item.weight));
                break;
            }
        }
        return profit;
    }

    //minimum number of coins
    //https://practice.geeksforgeeks.org/problems/-minimum-number-of-coins4426/1

    //tc (min number of coins)

    //why greedy works for this case only:

    //greedy algo works for this case only because of the way these denominations have been designed. We can see that sum
    // of any two notes/coins do not exceed any coins/notes beyond them. Thus, we can optimally find the minimum number of
    // coins required for change. This is because if the denomination was different, say array was like : {1,5,6,9} and
    // if we apply the same approach here to find min number of coins for a change of 11, then we'll get 3 (9+1+1). But
    // the actual ans is 2 (5+6). Since 5+6>9 ie an element beyond 5 & 6, then it means that their sum can be equal to
    // the change required and if we apply greedy here, these elements will be skipped after we take 9 and thus greedy
    // doesn't work here

    static List<Integer> minPartition(int N)
    {
        int[] arr={1,2,5,10,20,50,100,200,500,2000};
        List<Integer> list=new ArrayList<>();
        for(int i=arr.length-1;i>=0;i--){
            if(arr[i]>N){
                continue;
            }
            while(arr[i]<=N){
                N-=arr[i];
                list.add(arr[i]);
            }

        }
        return list;
    }

}