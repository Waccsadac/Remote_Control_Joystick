package com.waccsadac.remotecontroljoystick.model;

import android.util.Log;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class FGPlayer {
    Socket fg;
    PrintWriter out;
    Executor executor;
    public FGPlayer(String ip,int port) {
        executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            try {
                Log.d("trilili",ip);
                Log.d("trilili",Integer.toString(port));
                fg = new Socket(ip, port);
                out = new PrintWriter(fg.getOutputStream(),true);
                Log.d("trilili", "succeeded");
                out.print("set /controls/flight/current-engine/throttle " + 1 + "\r\n");
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    public void update_aileron(float v) {
        executor.execute(() -> {
            out.print("set /controls/flight/aileron " + v + "\r\n");
            out.flush();
            Log.d("trilili", "set /controls/flight/aileron " + v + "\r\n");
        });
    }

    public void update_elevator(float v) {
        executor.execute(() -> {
            out.print("set /controls/flight/elevator " + v + "\r\n");
            out.flush();
            Log.d("trilili", "set /controls/flight/elevator " + v + "\r\n");
        });
    }

    public void update_rudder(float v) {
        executor.execute(() -> {
            out.print("set /controls/flight/rudder " + v + "\r\n");
            out.flush();
            Log.d("trilili", "set /controls/flight/rudder " + v + "\r\n");
        });
    }

    public void update_throttle(float v) {
        executor.execute(() -> {
            out.print("set /controls/engines/current-engine/throttle " + v + "\r\n");
            out.flush();
            Log.d("trilili", "set /controls/engines/current-engine/throttle " + v + "\r\n");
        });
    }
}
