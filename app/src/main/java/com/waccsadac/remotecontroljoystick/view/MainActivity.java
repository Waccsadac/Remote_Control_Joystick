package com.waccsadac.remotecontroljoystick.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import com.waccsadac.remotecontroljoystick.R;
import com.waccsadac.remotecontroljoystick.databinding.ActivityMainBinding;
import com.waccsadac.remotecontroljoystick.view_model.ViewModel;

public class MainActivity extends AppCompatActivity {
    //Initialise variable
    ActivityMainBinding binding;
    Joystick joystick;
    ViewModel vm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Assign variable
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.vertSb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                //Todo convert this value to a float from -1 to 1 in viewmodel
                vm.update_throttle(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        binding.horizSb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                //Todo convert this value to a float from -1 to 1 in viewmodel
                vm.update_rudder(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        binding.connectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vm = new ViewModel(binding.ipTb.getText().toString(), binding.portTb.getText().toString());

                binding.horizSb.setEnabled(true);
                binding.vertSb.setEnabled(true);
            }
        });
    }
}