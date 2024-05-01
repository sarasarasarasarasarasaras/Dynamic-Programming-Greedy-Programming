import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Task implements Comparable {
    public String name;
    public String start;
    public int duration;
    public int importance;
    public boolean urgent;
    public Duration Duration;

    /*
        Getter methods
     */
    public String getName() {
        return this.name;
    }

    public String getStartTime() {
        return this.start;
    }

    public int getDuration() {
        return this.duration;
    }

    public int getImportance() {
        return this.importance;
    }

    public boolean isUrgent() {
        return this.urgent;
    }




    public String getFinishTime() {
        // YOUR CODE HERE
        LocalTime startTime = LocalTime.parse(start);
        Duration durationObj = Duration.ofHours(duration);
        LocalTime finishTime = startTime.plus(durationObj);
        return finishTime.format(DateTimeFormatter.ofPattern("HH:mm"));


    }


    public double getWeight() {
        double weight = importance * (urgent ? 2000 : 1) / (double) duration;
        return weight;
    }





    @Override
    public int compareTo(Object o) {
        // YOUR CODE HERE
        Task other = (Task) o;
        return this.getFinishTime().compareTo(other.getFinishTime());
    }
    public String toString() {
        return "Task name: " + name + ", start time: " + getStartTime() + ", finish time: " + getFinishTime();
    }
}
