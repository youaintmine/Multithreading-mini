import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static java.lang.Math.min;

public class App {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int[] nums = {1,23, 23, 1, 5, 1, 6, 7, 90, 12, 11, 124, 15, 156, 1356, 1156, 11, 6454, 64, 31, 1545, 96, 102, 1002, 11, 77, 33, 11, 76, 324, 57 ,27, 87, 242, 63, 143, 868,348, 84, 454 ,234};
        int groupSize = 4;
        int sze = (nums.length / groupSize)  + 1;
        System.out.println(sze);
        ExecutorService executor = Executors.newFixedThreadPool(2);
        List<Object> answer = new ArrayList<>();
        //Send the array for computation using
        //Future<Object> The callable sends : executor.submit(new Callable <return-type> () { Override the call ret-type method }
        for(int i = 0; i<sze; i++){
            int start = i*groupSize;
            int end = min((start + groupSize - 1),nums.length-1);
//            System.out.println("For Iter : " + (i+1) + " Start : " + start + " and End : " + end);
            Future<Integer> future= executor.submit(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    Integer val = Summation.sum(start, end, nums);
                    return val;
                }
            });
            answer.add(future);
        }

        System.out.println(syncSummation(answer));
        return;
    }

    private static int syncSummation(List<Object> answer) throws ExecutionException, InterruptedException {
        int Sum = 0;
        for(int i =0; i<answer.size(); i++){

           Future<Integer> future= (Future<Integer>) answer.get(i);
           while (!future.isDone()){
               Thread.yield();
           }
//            System.out.println(future.get());
            Sum = Sum + future.get();
        }
        return Sum;
    }
}
