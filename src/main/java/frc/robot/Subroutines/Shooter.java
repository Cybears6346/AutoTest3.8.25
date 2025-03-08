
package frc.robot.Subroutines;

import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkRelativeEncoder;
import edu.wpi.first.wpilibj.Timer;


import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

public class Shooter {
    SparkMax m_shooterLeft = new SparkMax(7, MotorType.kBrushless); //states the existance of a SparkMax with a CANid of 5
    SparkMax m_shooterRight = new SparkMax(8, MotorType.kBrushless); //states the existance of a SparkMax with a CANid of 6
   
    SparkMax[] CANIDs= {m_shooterLeft, m_shooterRight};

    // MotorControllerGroup m_winch = new MotorControllerGroup(m_winchLeft, m_winchRight);

    RelativeEncoder s_Encoder = m_shooterLeft.getEncoder();


    public void Initial()
    {
        SparkConfigure.ShooterInit(m_shooterLeft, m_shooterRight);
        // m_elevatorLeft.setInverted(true);
        // SmartDashboard.putBoolean("Winch LS", LS_Elevator.get());
    }
    
    public void SetSpeed (double speed){

        m_shooterLeft.set(speed);
        m_shooterRight.set(speed);
    }

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

    public double getEncoder(){
        //Note: Up negative encoder, Down positive encoder
        return s_Encoder.getPosition();
    }

    public double getEncoderToInches()
    {
        return s_Encoder.getPosition(); // Arbitrary x2, dunno the gear ratio for the shooter
    }
}
