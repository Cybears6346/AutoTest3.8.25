
package frc.robot.Subroutines;

import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;

import java.security.PrivateKey;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkRelativeEncoder;
import edu.wpi.first.wpilibj.Timer;
import com.revrobotics.spark.SparkAbsoluteEncoder;
import edu.wpi.first.wpilibj.XboxController;



import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

public class Elevator {
    SparkMax m_elevatorLeft = new SparkMax(5, MotorType.kBrushless); //states the existance of a SparkMax with a CANid of 5
    SparkMax m_elevatorRight = new SparkMax(6, MotorType.kBrushless); //states the existance of a SparkMax with a CANid of 6
    DigitalInput ElevatorBottomLS = new DigitalInput(0);
    DigitalInput ElevatorTopLS = new DigitalInput(1);
    DigitalInput elevatorInput = new DigitalInput(9);
    DutyCycleEncoder elevatorAbsoluteEncoder = new DutyCycleEncoder(elevatorInput);
    
    // Through Bore encoder we will be using; These two are seperate objects.  I dont know the physical configuration of the motor
    
    AbsoluteEncoder e_Encoder = m_elevatorLeft.getAbsoluteEncoder(); // How we will reference this encoder
    
    
    //This is for the relative encoder 
    //RelativeEncoder e_Encoder = m_elevatorLeft.getEncoder(); 
   // e_Encoder.setPosition(0);
    // Commented to avoid driver station spam


    public void Initial()
    {
        SparkConfigure.ElevatorInit(m_elevatorLeft, m_elevatorRight);//m_elevatorRight
        // m_elevatorLeft.setInverted(true);
         SmartDashboard.putBoolean("Elevator Bottom LS", ElevatorBottomLS.get());
         SmartDashboard.putBoolean("Elevator Top LS", ElevatorTopLS.get());
         
    }

    public double getElevatorEncoderValue() {
        return elevatorAbsoluteEncoder.get();
    }

    double encoderRevolutions = 0;
    double value = 0;
    public double getEncoderRevCount(XboxController ArmController)
    { // ~ 4.3 revolutions for 49 inches of vertical height
        double relativeVal = elevatorAbsoluteEncoder.get();
        if (elevatorAbsoluteEncoder.get() == 0.99 && ArmController.getRightY() > 0.05) // Increment with positive reading from right joystick
        {
            encoderRevolutions++;
        } else if (elevatorAbsoluteEncoder.get() == 0.01 && ArmController.getRightY() < -0.05)
        {
            encoderRevolutions--;
        }

        // value += elevatorAbsoluteEncoder.get();

        double value = relativeVal + encoderRevolutions;
        return value;
    }
    
    public void SetSpeed (double speed){

        // if (ElevatorBottomLS.get()) {
        //     m_elevatorRight.set(-0.5);
        //     m_elevatorLeft.set(-0.5);
        //     System.out.println("Bottom Elevator LS triggered");
        //   }
        //   else if (ElevatorTopLS.get()) {
        //     m_elevatorRight.set(0.5);
        //     m_elevatorLeft.set(0.5);
        //     System.out.println("Top Elevator LS triggered");
        //   } else {
            m_elevatorLeft.set(-speed);
            m_elevatorRight.set(-speed);
        //   }
       

    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// ENCODER TESTING
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//This is for the relative encoder
// public double getEncoder()
    // {
    //     return e_Encoder.getPosition(); 
    // }

//Terry Test/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//https://www.revrobotics.com/rev-11-1271/ refernce this for specifcations  

//Given the limited time, any time you see e_Encoder keep in mind it might be the wrong object declared, refer back to lines 30-31 for delcaration.  Idk which one to use


    
    // Values we need to keep in mind pls
    private final double diameter_GEAR = 1.45;
    private final double circumference = diameter_GEAR * Math.PI;
    private final double ratio_GEAR = 1 / 27;

    //This method is sus cause positionRotation returns a value between 1-1024 pulses pls do not use for now.  Eventually we will be using this method
    // public double getAbsolutePositionInInches(){ 
    //     double positionRotation = e_Encoder.getPosition();
    //     double positionInInches = positionRotation * ratio_GEAR * circumference;
    //     return positionInInches;
    // }

    // This code hasnt accounted for the 1:27 gear ratio, pls test later
    public double getAbsoluteAngle(){
        double rawPulse = e_Encoder.getPosition();
        return ((rawPulse-1)/1024) * 360; // Convets it to degrees by using the ratio of the pulse and multiplying it by 360 degrees
    }

    // We NEED a reference point regardless.  So we MUST set the robot in the lowest position to work.  We call this method during the initialization process
    private double zeroAngle = 0.0;// This variable will be important 
    public void setZeroPosition() { // This method will only be using once in Robot.java robotInit()
        zeroAngle = getAbsoluteAngle();  // Store current absolute position as "zero"
    }

    // 
    public double getRelativeAngle() {
        double relativeAngle = getAbsoluteAngle() - zeroAngle;
        return (relativeAngle + 360) % 360;  // Keep within 0-360 range and accounts in for engative values
    }

    public double getPositionInInches() {
        // Calculate the distance moved based on the angle
        double inchesMoved = (getRelativeAngle() / 360) * (circumference) * ratio_GEAR;  // Factor in 1:27 gear ratio, circcumference, and the relative angle (It's dividing by 360 bc it's telling us the revolution of the motor)
        return inchesMoved;
    }
    
    public double getEncoderToInches() // Note, 1:27 ratio and 1.45 working circumference 
    {
        return elevatorAbsoluteEncoder.get() / ratio_GEAR * circumference; 
    }

    // //Movement test; use this to make the macros alter
    // public void moveToPosition(double targetPositionInches) {
    //     // Convert target inches to an angle
    //     double targetAngle = (targetPositionInches / (1.45 * Math.PI) / 27) * 360;
        
    //     // Move the elevator to the target position
    //     while (Math.abs(getRelativeAngle() - targetAngle) > 1) {
    //         double motorSpeed = (targetAngle - getRelativeAngle()) < 0 ? -0.5 : 0.5;  // Move up or down
    //         setMotorSpeed(motorSpeed);
    //     }
    //     setMotorSpeed(0);  // Stop once the target position is reached
    // }



//Terry Test END/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//    public double getEncoderToInches()
//     {
//         // System.out.print(e_Encoder.getPosition() / 27 * 4.55530934771);
//         // return e_Encoder.getPosition() / (27 * 117) * 4.55530934771;
//         double Distance_Feet = 2; 
//         double Wheel_Diameter = 1.45 ; // in inches
//         double Gear_Ratio = 27; 

//         double enc_WheelCurrent = m_elevatorLeft.getEncoder().getPosition();
//         double enc_WheelTarget = enc_WheelCurrent + (Distance_Feet * 12 * (1 / (Wheel_Diameter * Math.PI)));
    
//         System.out.println(enc_WheelTarget);
//         return enc_WheelTarget;
//     }

    // public double getEncoderToInches2()
    // {
    //     System.out.print(e_Encoder.getPosition() / 27 * 4.55530934771);
    //     return e_Encoder.getPosition() / 27 * 4.55530934771;
    // }
//     public double getEncoderToInches2() {
//     double gearRatio = 27; 
//     double conversionFactor = 4.55530934771; // Inches per motor rotation

//     double inches = (e_Encoder.getPosition() / gearRatio) * conversionFactor;
//     System.out.println("Encoder Inches: " + inches); // Use println for a new line

//     return inches;
// }


    // public double getBetterEncoderToInches()
    // {
    //     // System.out.println(En_Elevator.get());
    //     return En_Elevator.get();
    // }
    
}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// ENCODER TESTING END
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    

// public void StageUp(double speed)
    // {
    //     if (StagingCount != 4)
    //     {
    //         while (getEncoder() < levels[StagingCount])
    //         {
    //             m_elevatorLeft.set(-speed * speedPercentage); // Due to positive encoder readings signaling down
    //         }
            
    //         StagingCount++;
    //     } else
    //     {
    //         System.out.println("Maximum level reached");
    //     }
    // }

    // public void StageDown(double speed)
    // {
    //     if (StagingCount != 0)
    //     {
    //         while (getEncoder() > levels[StagingCount])
    //         {
    //             m_elevatorLeft.set(speed * speedPercentage); // Due to negative encoder readings signaling up
    //         }
    //         StagingCount--;
    //     } else
    //     {
    //         System.out.println("Minimum level reached");
    //     }
    // }
    

    // public void CalibrateUpward(double speed)
    // {
    //     while (getEncoder() < 100) // MUST BE CHANGED IN FUTURE TO REFLECT TRUE CONVERSION FACTOR
    //     {
    //         m_elevatorLeft.set(-speed * speedPercentage);
    //     }
    //     StagingCount = 4;
    // }

    // public void CalibrateDownward(double speed)
    // {
    //     while (getEncoder() > 0)
    //     {
    //         m_elevatorLeft.set(speed * speedPercentage);
    //     }
    //     StagingCount = 0;
    // }

    // public void LevelAlign() // In case we want free control with joystick
    // {
    //     if ()
    //     {

    //     }
    // }

    // public void TestMotor (double speed, int CAN_ID){
    //     //Used to test arm motors individually. Call on this function and supply speed and CAN_ID 5-6        
    //     if (CAN_ID <= 6 || CAN_ID >= 9){
    //         String S = Integer.toString(CAN_ID);
    //         System.out.println("CAN_ID #" + S + " is not assigned to an winch motor");
    //     }
    //     else{
    //         CANIDs[(CAN_ID-7)].set(speed);
    //     }
    // }

    // public void zeroWinch(Timer TimerSub){
    //     while(LS_Winch.get() == true && TimerSub.get() < 15){
    //         SetSpeed(-0.1, false);
    //         if (LS_Winch.get() == false)
    //         {
    //             enc_Winch_Zeroed = getEncoder();
    //             enc_Winch_Top = enc_Winch_Zeroed - 58.5;
    //             //SmartDashboard.putNumber("Winch Top", enc_Winch_Top);
    //             SetSpeed(0, false);
    //         }
    //     }
    // }

//     public double getEncoder(){
//         //Note: Up negative encoder, Down positive encoder
//         return e_Encoder.getPosition();
//     }
// }
