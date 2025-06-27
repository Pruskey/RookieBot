package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Subsystems.PIDController;

public class Braco {
    private DcMotor Liberdade1;
    private DcMotor Liberdade2;

    private org.firstinspires.ftc.teamcode.Subsystems.PIDController.PID pid1;
    private org.firstinspires.ftc.teamcode.Subsystems.PIDController.PID pid2;

    private volatile int setpoint1 = 0;
    private volatile int setpoint2 = 0;

    public Braco(DcMotor l1, DcMotor l2) {
        this.Liberdade1 = l1;
        this.Liberdade2 = l2;

        //1: p 0.0006, i 0.0, d 0.00004
        //2: p 0.003, i 0.0, d 0.00002

        pid1 = new PIDController.PID(0.0008, 0.0, 0.00004, 0.16);
        pid2 = new PIDController.PID(0.003, 0.0, 0.00002, 0);

        Liberdade1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Liberdade2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        Liberdade1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Liberdade2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void setSetpoints(int s1, int s2) {
        setpoint1 = s1;
        setpoint2 = s2;
    }

    public void update() {
        double actual1 = Liberdade1.getCurrentPosition();
        double actual2 = Liberdade2.getCurrentPosition();

        double power1 = pid1.getValue(setpoint1 - actual1, Liberdade1.getCurrentPosition());
        double power2 = pid2.getValue(setpoint2 - actual2, Liberdade2.getCurrentPosition());

        Liberdade1.setPower(power1);
        Liberdade2.setPower(power2);
    }

    public int getPos1() { return Liberdade1.getCurrentPosition(); }
    public int getPos2() { return Liberdade2.getCurrentPosition(); }
}