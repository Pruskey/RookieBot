package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.util.ElapsedTime;

public class PIDController {
    public static class PID {
        private double kP, kI, kD, kF;
        private double integralSum = 0.0;
        private double lastError = 0.0;
        private double ticks_in_degree = 8192 / 360.0;
        private ElapsedTime timer;

        public PID(double p, double i, double d, double ff) {
            this.kF = ff;
            this.kP = p;
            this.kI = i;
            this.kD = d;
            timer = new ElapsedTime();
            timer.reset();
        }

        public double getValue(double error, double currentPosition) {
            double dT = timer.seconds();
            double derivative = (error - lastError) / dT;
            integralSum += (error * dT);
            double output = (kP * error) + (kI * integralSum) + (kD * derivative);
            double angleDegrees = currentPosition / ticks_in_degree;
            double angleRadians = Math.toRadians(angleDegrees);
            double feedf = kF * Math.cos(angleRadians);
            lastError = error;
            timer.reset();
            return output + feedf;
        }

    }
}
