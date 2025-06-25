package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.util.ElapsedTime;

public class PIDController {
    public static class PID {
        private double kP, kI, kD, tolerance;
        private double integralSum = 0.0;
        private double lastError = 0.0;
        private ElapsedTime timer;

        public PID(double p, double i, double d, double tolerance) {
            this.kP = p;
            this.kI = i;
            this.kD = d;
            this.tolerance = tolerance;
            timer = new ElapsedTime();
            timer.reset();
        }

        public double getValue(double error) {
            double dT = timer.seconds();
            double derivative = (error - lastError) / dT;
            integralSum += (error * dT);
            double output = (kP * error) + (kI * integralSum) + (kD * derivative);
            lastError = error;
            timer.reset();
            return output;
        }

        public boolean atTarget(double error) {
            return Math.abs(error) <= tolerance;
        }
    }
}
