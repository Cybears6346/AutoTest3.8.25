
package frc.robot.Subroutines;

import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkRelativeEncoder;
import edu.wpi.first.wpilibj.Timer;


import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AlgaeArm {
    SparkFlex m_algaearm = new SparkFlex(9, MotorType.kBrushless); 
    DigitalInput ArmBottomLS = new DigitalInput(4);
    DigitalInput ArmTopLS = new DigitalInput(5);
   
    // SparkFlex[] CANIDs= {m_algaewheel};

    // RelativeEncoder e_Encoder = m_algaewheel.getEncoder();
    // DigitalInput LS_Elevator = new DigitalInput(2);


    public void Initial()
    {
        // SparkConfigure.AlgaeWheelInit(m_algaewheel);
        SparkConfigure.AlgaeArmInit(m_algaearm);
        // m_elevatorLeft.setInverted(true);
        // SmartDashboard.putBoolean("Winch LS", LS_Elevator.get());
    }

    public DigitalInput getArmBottomLS()
    {
        return ArmBottomLS;
    }

    public DigitalInput getArmTopLS()
    {
        return ArmTopLS;
    }
    
    public void SetSpeed (double speed)
    {
        // SmartDashboard.putBoolean("Winch LS", LS_Elevator.get());
        // SmartDashboard.putNumber("Winch Encoder", getEncoder());
       
        m_algaearm.set(-speed * 0.5);
        // if (ArmBottomLS.get()) {
        //     m_algaearm.set(0.05);
        //   }
        //   else if (ArmTopLS.get()) {
        //     m_algaearm.set(-0.05);
        //   } else {
        //     m_algaearm.set(-speed * 0.5);
        //   }
        
        
        // System.out.println(e_Encoder);
            // m_elevatorLeft.set(0);
    }

    // public double getEncoder(){
    //     //Note: Up negative encoder, Down positive encoder
    //     return e_Encoder.getPosition();
    // }
}
