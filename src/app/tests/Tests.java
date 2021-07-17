package app.tests;

import app.model.AppModel;
import app.model.FlightSettings;
import app.viewModel.AppViewModel;

public class Tests {

    public static void main(String[] args) {

        AppModel am = new AppModel();
        AppViewModel avm = new AppViewModel(am);

        FlightSettings fs = avm.getAppModel().getFlightSettings();
        System.out.println(fs.getSimulatorPort());
    }

}
