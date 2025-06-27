package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Subsystems.Braco;
import org.firstinspires.ftc.teamcode.Subsystems.Garra;
import org.firstinspires.ftc.teamcode.Subsystems.PIDController;

@TeleOp
        (name = "TeleOp Multithread")
public class FinalTeleOp extends LinearOpMode {

    public DcMotor Left;
    public DcMotor Right;

    private double Drive;
    private double Turnner;

    private Braco braco;
    private Garra garra;

    @Override
    public void runOpMode() {

        DcMotor l1 = hardwareMap.get(DcMotor.class, "Liberdade1");
        DcMotor l2 = hardwareMap.get(DcMotor.class, "Liberdade2");
        Servo clawServo = hardwareMap.get(Servo.class, "clawServo");
        Left = hardwareMap.get(DcMotor.class, "Left");
        Right = hardwareMap.get(DcMotor.class, "Right");

        Right.setDirection(DcMotor.Direction.REVERSE);

        braco = new Braco(l1, l2);
        garra = new Garra(clawServo);

        waitForStart();


        while (opModeIsActive()) {
            garra.update(gamepad1.x);

            if (gamepad1.a) {
                braco.setSetpoints(700, 0);
            } else if (gamepad1.y) {
                braco.setSetpoints(2100, 100);
            } else if (gamepad1.b) {
                braco.setSetpoints(2337, 33);
            }
            braco.update();

            Drive = gamepad1.left_stick_y;
            Turnner = -gamepad1.left_stick_x;
            double rightPower = Turnner + Drive;
            double leftPower = -Turnner + Drive;
            Right.setPower(rightPower);
            Left.setPower(leftPower);

            telemetry.addData("Braco1 Pos", braco.getPos1());
            telemetry.addData("Braco2 Pos", braco.getPos2());

            telemetry.update();
        }

    }
}