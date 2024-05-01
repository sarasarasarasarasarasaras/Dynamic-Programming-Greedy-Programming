import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class Planner {

    public final Task[] taskArray;
    public final Integer[] compatibility;
    public final Double[] maxWeight;
    public final ArrayList<Task> planDynamic;
    public final ArrayList<Task> planGreedy;

    int[] test;

    public Planner(Task[] taskArray) {



        this.taskArray = taskArray;
        this.compatibility = new Integer[taskArray.length];
        maxWeight = new Double[taskArray.length];
        test = new int[1];
        this.planDynamic = new ArrayList<>();
        this.planGreedy = new ArrayList<>();
    }

    /**
     * @param index of the {@link Task}
     * @return Returns the index of the last compatible {@link Task},
     * returns -1 if there are no compatible {@link Task}s.
     */
    public int binarySearch(int index) {
        int left = 0;
        int right = index - 1;
        int latestTask = -1;

        while (left <= right) {
            int mid = (left + right) / 2;

            if (taskArray[mid].getFinishTime().compareTo(taskArray[index].getStartTime()) <= 0) {
                latestTask = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return latestTask;
    }



    /**
     * {@link #compatibility} must be filled after calling this method
     */
    public void calculateCompatibility() {

        Arrays.sort(taskArray, Comparator.comparing(Task::getFinishTime));
        // YOUR CODE HERE
        for (int i = 0; i < taskArray.length; i++) {

            compatibility[i] = binarySearch(i);

        }

    }


    /**
     * Uses {@link #taskArray} property
     * This function is for generating a plan using the dynamic programming approach.
     *
     * @return Returns a list of planned tasks.
     */
    public ArrayList<Task> planDynamic() {
        calculateCompatibility();


        System.out.println("Calculating max array\n---------------------");


            calculateMaxWeight(taskArray.length-1);

        System.out.println();

        System.out.println("Calculating the dynamic solution\n--------------------------------");


        solveDynamic(taskArray.length - 1);
        System.out.println();


        System.out.println("Dynamic Schedule\n----------------");
        Collections.reverse(planDynamic);
        for (int i = 0; i < planDynamic.size(); i++){
            System.out.println("At " + planDynamic.get(i).start + ", " + planDynamic.get(i).name+".");
        }

        return planDynamic;



    }

    /**
     * {@link #planDynamic} must be filled after calling this method
     */
    public void solveDynamic(int i) {

      if (i < 0 ) {


           return;

       }

       if( i==1 ){

           System.out.println("Called solveDynamic(" + i + ")");
           planDynamic.add(taskArray[i]);
           solveDynamic(compatibility[i]);

           return;
       }

        System.out.println("Called solveDynamic(" + i + ")");


        if (i == 0 || (taskArray[i].getWeight() + calMax(compatibility[i])) >= calMax(i - 1)) {
            planDynamic.add(taskArray[i]);
            solveDynamic(compatibility[i]);

        } else {
            solveDynamic(i - 1);
        }

    }

    /**
     * {@link #maxWeight} must be filled after calling this method
     */

    public Double calculateMaxWeight(int i) {

        if (i < 0) {
            //System.out.println("a");

            System.out.println("Called calculateMaxWeight(-1)");

            return 0.0;
        }

        System.out.println("Called calculateMaxWeight(" + i + ")");



        if (i != 0 && maxWeight[i] != null) {


            return maxWeight[i];
        }

        int prev = binarySearch(i);
        if (prev < 0){

            calculateMaxWeight(prev);
        }

        double include = taskArray[i].getWeight() + ((prev == -1) ? 0 : calculateMaxWeight(prev));
        double exclude = calculateMaxWeight(i - 1);
        double result = Math.max(include, exclude);
        maxWeight[i] = result;
        return result;

    }
    public Double calMax(int i) {

        if (i == -1) {

            return 0.0;
        }


        if (i != 0 && maxWeight[i] != null) {

            return maxWeight[i];
        }

        int prev = binarySearch(i);

        double include = taskArray[i].getWeight() + ((prev == -1) ? 0 : calculateMaxWeight(prev));
        double exclude = calculateMaxWeight(i - 1);
        double result = Math.max(include, exclude);
        maxWeight[i] = result;
        return result;

    }

    public ArrayList<Task> planGreedy() {
        // YOUR CODE HERE
        int n = taskArray.length;
        int last = 0;

        planGreedy.add(taskArray[last]);

        for (int i = 1; i < n; i++) {
            if (taskArray[i].getStartTime().compareTo(taskArray[last].getFinishTime()) >= 0) {
                planGreedy.add(taskArray[i]);
                last = i;
            }
        }

        System.out.println("Greedy Schedule\n---------------");
        for (Task task : planGreedy) {
            System.out.println("At "+ task.start + ", " + task.name+".");

        }
        return planGreedy;

    }

    }



