package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.Servo;

public class Garra {
    private Servo servo;
    private boolean toggle = false;
    private boolean previousState = false;

    public Garra(Servo servo) {
        this.servo = servo;
    }

    public void update(boolean buttonPressed) {
        if (buttonPressed && !previousState) {
            toggle = !toggle;
        }
        previousState = buttonPressed;

        if (toggle) {
            servo.setPosition(0);  // Fechado
        } else {
            servo.setPosition(1);  // Aberto
        }
    }
}