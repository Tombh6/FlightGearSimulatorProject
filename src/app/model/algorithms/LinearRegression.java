package app.model.algorithms;


import app.CorrelatedFeaturesLine;
import app.model.statlib.Point;
import app.model.statlib.Line;
import app.model.statlib.StatLib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class LinearRegression implements TimeSeriesAnomalyDetector {

   
    private HashMap<String, CorrelatedFeaturesLine> hashMap;
   
    public LinearRegression() {
        
        hashMap=new HashMap<String, CorrelatedFeaturesLine>();
      
        
    }

    @Override
    public void learnNormal(TimeSeries ts) {
    	
       
        float maxp, t, maxdev, threshold;
        float[] arrayX, arrayY;
        int x, y, i, j;
        int size = ts.data.get(0).length;
        Point[] temp;
        Line lin_reg;
        for (i = 0; i < size; i++)
        {
            maxp = -1;
            x = i;
            y = i;
            arrayX = ts.dataOfFeaturerByNum(i);
            for (j = i + 1; j < size; j++) {
                arrayY = ts.dataOfFeaturerByNum(j);
                t = StatLib.pearson(arrayX, arrayY);

                if (Math.abs(t) > maxp ) {
                    y = j;
                    maxp = Math.abs(t) ;
                }
            }

            threshold = -1;
            if (maxp >= 0) {
                temp = StatLib.ArrayOfPoint(ts.dataOfFeaturerByNum(x), ts.dataOfFeaturerByNum(y));
                lin_reg = StatLib.linear_reg(temp);
                for (Point point : temp) {
                    maxdev = StatLib.dev(point, lin_reg);
                    if (maxdev > threshold)
                        threshold = maxdev;
                }
                threshold = Math.abs(threshold);
                
               hashMap.put(ts.namesOfFeatures.get(x),new CorrelatedFeaturesLine(ts.namesOfFeatures.get(x), ts.namesOfFeatures.get(y), maxp, lin_reg, threshold));
           
            }

        }
    }

    @Override
    public   HashMap<String, List<Integer>> detect(TimeSeries ts) {
        
        Point temp;
        HashMap<String, List<Integer>> map = new HashMap<>();
        
        for(String f: ts.namesOfFeatures) 
        {
        	if(hashMap.containsKey(f)) {
        	float[] fcorrelate1 = ts.dataOfFeatureByName(f);
            String correlate2 = new String(hashMap.get(f).feature2);
            float[] fcorrelate2 = ts.dataOfFeatureByName(correlate2);
            for (int z = 0; z < fcorrelate1.length; z++) {

                temp = new Point(fcorrelate1[z], fcorrelate2[z]);
                if (StatLib.dev(temp, hashMap.get(f).lin_reg) > hashMap.get(f).threshold ) {
                    

                    List<Integer> tempList;
                    if(map.get(f)==null)
                    {
                        tempList= new ArrayList<>();

                    }
                    else{
                        tempList=map.get(f);
                    }
                    tempList.add(z+1);
                    map.put( f, tempList);

                }
            	}
        	}
        }
        return map;
    }

    public HashMap<String, CorrelatedFeaturesLine> getHashMap() {
        return hashMap;
    }

    public void setHashMap(HashMap<String, CorrelatedFeaturesLine> hashMap) {
        this.hashMap = hashMap;
    }

    static public class time {
        public long startTime;
        public long endTime;
        public int alarm;

        public time(long timeStep, long timeStep2) {
            super();
            this.startTime = timeStep;
            this.endTime = timeStep2;
            this.alarm = 1;
        }

        public long getStartTime() {
            return startTime;
        }

        public void setStartTime(long startTime) {
            this.startTime = startTime;
        }

        public long getEndTime() {
            return endTime;
        }

        public void setEndTime(long timeStep) {
            this.endTime = timeStep;
        }

    }

   
}
