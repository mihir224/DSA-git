import java.util.*;

public class GreedyAlgorithms {

    //N meetings in one room
    //https://practice.geeksforgeeks.org/problems/n-meetings-in-one-room-1587115620/1

    //approach: put the st,ft and number of all meetings in a list and then sort that list acc to increasing ft. This is
    // because if we do the meetings which finish early in the day, then we can accommodate more meetings. We take initial
    // count as 1, assuming the first meeting has been started, and we store that meeting's end time, and start iterating
    // through the list from the second meeting and simply check if it is possible to do the current meeting after the checking
    // the prev meeting's finish time and if it is, we increase the count and update prevFt to current meeting's ft

    //O(NlogN) tc, O(N) sc
    static class Triad{
        int st;
        int ft;
        int num;
        public Triad(int st, int ft, int num){
            this.st=st;
            this.ft=ft;
            this.num=num;
        }
    }
    public static int maxMeetings(int start[], int end[], int n)
    {
        ArrayList<Triad> list=new ArrayList<Triad>();
        int count=1; //initial count is 1, since we start iterating through the
        //list after taking the first meeting into account
        for(int i=0;i<n;i++){
            list.add(new Triad(start[i],end[i],i+1));
        }
        Collections.sort(list,(a,b)->a.ft-b.ft);
        int prevFt=list.get(0).ft;
        for(int i=1;i<n;i++){
            if(list.get(i).st>prevFt){ //we didn't place equal here because it is given that start time of one meeting
                // cannot be equal to end time of other
                count++;
                prevFt=list.get(i).ft;
            }
        }
        return count;
    }

    //min platforms
    //https://practice.geeksforgeeks.org/problems/minimum-platforms-1587115620/1#

    //we need to find the min number of platforms required in a day so that no train is kept waiting. This means that we
    // have to calculate the max number of platforms that were occupied at any given time throughout the day. Now what
    // matters to us is when the train is arriving because that would help us identify whether to assign a new platform
    // to that train or not. So we first sort both the arrival and departure times so that the trains arriving and departing
    // earlier come first. We don't have to worry about the fact that it would distort the co-relation between the arrival
    // and departure times at a given index. This is because we just scroll through the time in the day ie if a train arrives,
    // a platform is assigned to it and if a train departs, it leaves a platform empty. We don't deal with the departure
    // and arrival of the same train simultaneously. So first we assume that the first train (the one with the least arrival
    // time) has arrived at the station and has taken a platform (thus the platforms occupied is 1 initially).
    // Then, we take two pointers i and j - one at the arrival array and another at the departure array. Now since we
    // have assumed that 1st train has already arrived, we start i from 1 index ie the arrival of second train. So we
    // iterate through both the arrays using a while loop and if the arrival time of the train to which i is pointing
    // to is <=departure time of train that j is pointing to, then it means that ith train arrives at a time before jth
    // train leaves. Equal sign is necessary since it was given in the question that if the arrival of a train equals departure
    // of another, then also we need to assign a new platform to the arriving train. Thus, in this case, platforms occupied
    // is increased by 1, and we move to the next arriving train time. Contrary to this if the arrival time of a train is>
    // departure of another, then it means that a train will depart before the other train arrives. Thus, we reduce the
    // platformsOccupied by 1 and move to the next departing train time. Out of all iterations, we store the max value of
    // platforms occupied at any time


    //O(2NlogN + 2n) tc, O(1) sc
    static int findPlatform(int arr[], int dep[], int n)
    {
        Arrays.sort(arr);
        Arrays.sort(dep);
        int platformsOcc=1; // considering 1st train has arrived
        int minPlatforms=1;
        int i=1;
        int j=0;
        while(i<n&&j<n){
            if(arr[i]<=dep[j]){ // if at any time arrival time of a train equals departure time of another train, we
                // cannot put them on the same platform (this is specified in the problem) and thus we use the equal sign here
                platformsOcc++;
                i++;
            }
            else if(arr[i]>dep[j]){
                platformsOcc--;
                j++;
            }
            //tricky part
            if(platformsOcc>minPlatforms){ //since we need to find min required, we have to look for the max amount of
                // platforms that were occupied at any given time to prepare for worst case
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

    //tc O(NLogN + N*M) considering that size of the longest deadline is M
    //sc O(M)

    class Job {
        int id, profit, deadline;
        Job(int x, int y, int z){
            this.id = x;
            this.deadline = y;
            this.profit = z;
        }
        int[] JobScheduling(Job arr[], int n)
        {
            Arrays.sort(arr,(a,b)->b.profit-a.profit);
            int maxDeadline=-1;
            int count=0;
            int maxProfit=0;
            for(int i=0;i<n;i++){
                if(arr[i].deadline>maxDeadline){
                    maxDeadline=arr[i].deadline;
                }
            }
            int[] deadline=new int[maxDeadline+1];
            Arrays.fill(deadline,-1);
            for(int i=0;i<n;i++){
                for(int j=arr[i].deadline;j>0;j--){
                    if(deadline[j]==-1){
                        deadline[j]=i;
                        maxProfit+=arr[i].profit;
                        count++;
                        break;
                    }
                }
            }
            return new int[] {count,maxProfit};
        }
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

    //Intuition: If we take a fraction of an item with higher value per unit weight than it will return in more loss of profit
    // than if we take a fraction of an item with lesser value per unit weight. Consider the eg: if we have an item with wt 30
    // and value 120 (ie val/wt=4) and another item with wt 10 and value 60 (ie val/wt=6) then you can see that in a bag of
    // capacity 30 if we take the whole item with wt 30, profit would be 120 but if we took the whole item with wt 10 and a
    // fraction of the other item ((120/30)*20=80) then the total profit would've been 140. Thus, not taking the entire
    // item with wt 10 resulted in a loss of 20 in profit. Therefore, we always prioritize the items with higher val/wt


    //O(NlogN+N)tc, O(1)sc
    class Item {
        int value, weight;
        Item(int x, int y){
            this.value = x;
            this.weight = y;
        }

    }
    class ItemComparator implements Comparator<Item>{
        @Override
        public int compare(Item a, Item b) {
            double vpw1=(double)a.value/(double)a.weight;
            double vpw2=(double)b.value/(double)b.weight;
            if(vpw1<vpw2){
                return 1;
            }
            else if(vpw1>vpw2){
                return -1;
            }
            return 0;
        }
    }
    double fractionalKnapsack(int W, Item arr[], int n)
    {
        double maxVal=0.0;
        Arrays.sort(arr,new ItemComparator());
        for(int i=0;i<n;i++){
            if(arr[i].weight<=W){
                W-=arr[i].weight;
                maxVal+=arr[i].value;
            }
            else if(arr[i].weight>W){
                double vpw=(double)arr[i].value/(double)arr[i].weight;
                double netValue=(double)W*vpw;
                maxVal+=netValue;
                break;
            }
        }
        return maxVal;
    }

    //minimum number of coins
    //https://practice.geeksforgeeks.org/problems/-minimum-number-of-coins4426/1

    //tc O(V) wc (since we're using 1 rupee denomination)

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
        List<Integer> ans=new ArrayList<>();
        int[] arr = { 1, 2, 5, 10, 20, 50, 100, 200, 500, 2000 };
        int n=arr.length;
        for(int i=n-1;i>=0;i--){
            if(N==0){
                break;
            }
            while(arr[i]<=N){
                N-=arr[i];
                ans.add(arr[i]);
            }
        }
        return ans;
    }
}


