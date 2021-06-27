package com.waccsadac.remotecontroljoystick.view_model;

import android.widget.Toast;

import com.waccsadac.remotecontroljoystick.model.FGPlayer;
import com.waccsadac.remotecontroljoystick.view.MainActivity;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.Duration;

public class ViewModel {
    FGPlayer model;
    public ViewModel(String ip, String port) {
        model = new FGPlayer(ip,Integer.parseInt(port));
    }

    public void update_aileron(double a) {
        model.update_aileron((float)a);
    }

    public void update_elevator(double e) {
        model.update_elevator((float)e);
    }

    public void update_throttle(int progress) {
        model.update_throttle((float)(0.01 * progress));
    }

    public void update_rudder(int progress) {
        model.update_rudder((float)(-1 + 0.02 * progress));
    }
}
