package app.tests;
import java.util.List;

import app.model.algorithms.*;
public class test {

	public static void main(String[] args) 
	{

		TimeSeries ts1=new TimeSeries("C:\\Users\\tombh\\eclipse-workspace2\\FlightSimulator\\src\\files\\anomalyTrain.csv");
		TimeSeries ts2=new TimeSeries("C:\\Users\\tombh\\eclipse-workspace2\\FlightSimulator\\src\\files\\anomalyTest.csv");
	
		ZScore z=new ZScore();
		z.learnNormal(ts1);
		
		  System.out.println("done");
		
	}

}
