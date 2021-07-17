package app.model.algorithms;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import app.CorrelatedFeature;
import app.model.statlib.StatLib;


public class TimeSeries {

    public ArrayList<String> namesOfFeatures = new ArrayList<String>();
    public ArrayList<float[]> data = new ArrayList<float[]>();
    public float coral; 
    public int totalTime;


    public TimeSeries(String Path) {

        this.coral = (float) 0.9;
        String line = "";
        try {

            Path p = Paths.get(Path);
            BufferedReader br = new BufferedReader(new FileReader(p.toString()));
            if ((line = br.readLine()) != null) {
                

                String[] s1 = line.split(",");
                int len = s1.length;
                for (int c = 0; c < len; c++) {
                    namesOfFeatures.add(s1[c]);//what will be the feature

                }
                int counter = 0;
                while ((line = br.readLine()) != null) {

                    data.add(new float[len]);//add new line
                    totalTime++;
                    s1 = line.split(",");
                    for (int i = 0; i < len; i++) {
                        data.get(counter)[i] = (Float.parseFloat(s1[i]));//fill the line with data

                    }
                    counter++;
                }
                this.totalTime = counter;

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void addNewFeatureAndData(String fName, float[] values) {

        if (fName != null && values.length > 0) {
            namesOfFeatures.add(fName);
            data.add(values);
        }
    }

    public int featurePlace(String s) {

        return namesOfFeatures.indexOf(s);
    }

    public float[] dataOfFeatureByName(String s) {
        int n = featurePlace(s);
        float[] f = new float[data.size()];
        int i = 0;
        for (float[] element : data) {
            f[i] = element[n];
            i++;
        }

        return f;

    }

    public float[] dataOfFeaturerByNum(int s) {
        float[] f = new float[data.size()];
        int i = 0;
        for (float[] element : data) {
            f[i] = element[s];
            i++;
        }

        return f;

    }

    public float getMaxByFeature(String s){
        float t[]=this.dataOfFeatureByName(s);
        float max=t[0];
        for (float value:t) {
            if(max<value){
                max=value;
            }
        }
        return max;


    }

    public float getMinByFeature(String s){
        float t[]=this.dataOfFeatureByName(s);
        float min=t[0];
        for (float value:t) {
            if(min>value){
                min=value;
            }
        }
        return min;


    }



    public float getValAtSpecificTime(int time, String request)
    
    {

        int sPlace = featurePlace(request);
        if (sPlace == -1) {
            return -1;
        } else return data.get(time - 1)[sPlace];

    }

    public float getValAtSpecificTime(int time, int request)
    
    {


        if (request == -1) {
            return -1;
        } else return data.get(time - 1)[request-1];

    }

    public float getCoral() {
        return coral;
    }

    public void setCoral(float coral) {
        this.coral = coral;
    }


  
}
