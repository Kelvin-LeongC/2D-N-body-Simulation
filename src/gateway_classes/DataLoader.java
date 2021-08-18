package gateway_classes;

import entities.Star;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataLoader {

    public Star[] loadData(String filePath) throws IOException{
        BufferedReader reader = new BufferedReader(new FileReader(filePath));

        List<Star> starsList = new ArrayList<>();

        int N = 0;
        String line;
        String[] info;

        while((line = reader.readLine()) != null){
            info = line.split("/");
            addNewStarToList(starsList, info);
            N++;
        }

        return convertListToArray(starsList, N);
    }

    private void addNewStarToList(List<Star> starsList, String[] info){
        double x = Double.parseDouble(info[0]);
        double y = Double.parseDouble(info[1]);
        double vx = Double.parseDouble(info[2]);
        double vy = Double.parseDouble(info[3]);
        double mass = Double.parseDouble(info[4]);

        Star star = new Star(x, y, vx, vy, mass);
        starsList.add(star);
    }

    private Star[] convertListToArray(List<Star> starsList, int numBody){
        Star[] starsArray = new Star[numBody];
        for(int i = 0; i < numBody; i++){
            starsArray[i] = starsList.get(i);
        }
        return starsArray;
    }
}
