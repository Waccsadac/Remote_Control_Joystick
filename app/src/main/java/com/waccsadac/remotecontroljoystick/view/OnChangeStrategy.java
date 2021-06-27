package com.waccsadac.remotecontroljoystick.view;
@FunctionalInterface
public interface OnChangeStrategy {
    public void onChange(double a, double e);
}
