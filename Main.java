import java.io.*;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        String file = args[0];
        Task[] tasks = parseJSON(file);


        Arrays.sort(tasks);
        Planner planner = new Planner(tasks);


        planner.planDynamic();
        System.out.println();
       planner.planGreedy();

    }

    public static Task[] parseJSON(String filename) throws FileNotFoundException {

        Gson gson = new Gson();
        JsonReader jr = new JsonReader(new FileReader(filename));
        return gson.fromJson(jr, Task[].class);
    }
}
