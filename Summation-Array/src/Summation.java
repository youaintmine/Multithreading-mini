public class Summation {
    public static Integer sum(int l, int r, int[] arr) {
        int Sum = 0;
        for(int i = l; i<=r; i++){
            Sum += arr[i];
        }
        return Sum;
    }
}
