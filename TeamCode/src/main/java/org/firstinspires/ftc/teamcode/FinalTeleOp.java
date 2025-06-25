package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Subsystems.Braco;
import org.firstinspires.ftc.teamcode.Subsystems.Garra;

@TeleOp
        (name = "TeleOp Multithread")
public class FinalTeleOp extends LinearOpMode {

    private Braco braco;
    private Garra garra;

    @Override
    public void runOpMode() {

        DcMotor l1 = hardwareMap.get(DcMotor.class, "Liberdade1");
        DcMotor l2 = hardwareMap.get(DcMotor.class, "Liberdade2");
        Servo clawServo = hardwareMap.get(Servo.class, "clawServo");

        braco = new Braco(l1, l2);
        garra = new Garra(clawServo);

        telemetry.addLine("Ready");
        telemetry.update();

        waitForStart();

        Thread bracoThread = new Thread(() -> {
            while (opModeIsActive()) {
                if (gamepad1.a) {
                    braco.setSetpoints(336, 30);
                } else if (gamepad1.y) {
                    braco.setSetpoints(186, 87);
                } else if (gamepad1.b) {
                    braco.setSetpoints(5, 0);
                }

                braco.update();
                sleep(10);
            }
        });

        Thread garraThread = new Thread(() -> {
            while (opModeIsActive()) {
                garra.update(gamepad1.x);
                sleep(50);
            }
        });

        // Starta Threads
        bracoThread.start();
        garraThread.start();

        while (opModeIsActive()) {
            telemetry.addData("Braco1 Pos", braco.getPos1());
            telemetry.addData("Braco2 Pos", braco.getPos2());
            telemetry.update();
            sleep(100);
        }

        // Emergência: Para as Threads
        bracoThread.interrupt();
        garraThread.interrupt();
    }
}