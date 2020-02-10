
public class Main {
    public static void main(String[] args) {
        long start = System.nanoTime();
        /*
         First param is range, second is inputCount and the last one is step . 
         For more detail read buildChart class.
        */
        new buildChart(100000, 1000000, 0).startTesting();
        long result = System.nanoTime();

        System.out.println("\nDuration : " + (long) (result - start) / 1000000000 + " second");
    }
}
