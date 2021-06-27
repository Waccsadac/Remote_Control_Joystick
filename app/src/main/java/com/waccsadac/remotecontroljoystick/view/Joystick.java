package com.waccsadac.remotecontroljoystick.view;

import android.content.Context;
import android.view.View;

public class Joystick extends View {
    public OnChangeStrategy onChange;
    public Joystick(Context context) {
        super(context);
    }
}
