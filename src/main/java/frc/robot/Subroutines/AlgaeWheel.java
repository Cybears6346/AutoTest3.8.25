
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

public class AlgaeWheel {
    SparkMax m_algaewheel = new SparkMax(10, MotorType.kBrushless); //states the existance of a sparkflex with a CANid of 10
    // SparkFlex m_algaearm = new SparkFlex(9, MotorType.kBrushless); 
   
    SparkMax[] CANIDs= {m_algaewheel};

    RelativeEncoder e_Encoder = m_algaewheel.getEncoder();
    DigitalInput LS_Elevator = new DigitalInput(2);


    public void Initial()
    {
        // SparkConfigure.AlgaeWheelInit(m_algaewheel);
        SparkConfigure.AlgaeWheelInit(m_algaewheel);
        // m_elevatorLeft.setInverted(true);
        // SmartDashboard.putBoolean("Winch LS", LS_Elevator.get());
    }
    
    public void SetSpeed (double speed)
    {
        // SmartDashboard.putBoolean("Winch LS", LS_Elevator.get());
        // SmartDashboard.putNumber("Winch Encoder", getEncoder());
        
        m_algaewheel.set(speed);
        // m_algaearm.set(-speed);
        //System.out.println(e_Encoder);
            // m_elevatorLeft.set(0);
    }

    public double getEncoder(){
        //Note: Up negative encoder, Down positive encoder
        return e_Encoder.getPosition();
    }
}
