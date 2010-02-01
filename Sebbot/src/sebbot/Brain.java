package sebbot;
import java.lang.Math;

import sebbot.strategy.BasicStrategy;
import sebbot.strategy.GoToBallAndShoot;
import sebbot.strategy.Strategy;
import sebbot.strategy.UniformCovering;

/**
 * @author Sebastien Lentz
 *
 */
public class Brain extends Thread
{
    private Sebbot        sebbot;        // For communicating with the server 
    private FullstateInfo fullstateInfo; // Contains all info about the
                                         //     current state of the game
    private Player        player;        // The player this brain controls

    /**
     * Constructor.
     * 
     * @param sebbot
     * @param teamSide
     * @param playerNumber
     */
    public Brain(Sebbot sebbot, boolean leftSide, int playerNumber)
    {
        this.sebbot = sebbot;
        this.fullstateInfo = new FullstateInfo("");
        this.player = leftSide ? fullstateInfo.getLeftTeam()[playerNumber -1]
                               : fullstateInfo.getRightTeam()[playerNumber -1];
    }

    /**
     * @return the fullstateInfo
     */
    public FullstateInfo getFullstateInfo()
    {
        return fullstateInfo;
    }

    /**
     * @param fullstateInfo
     *            the fullstateInfo to set
     */
    public void setFullstateInfo(FullstateInfo fullstateInfo)
    {
        this.fullstateInfo = fullstateInfo;
    }

    /**
     * This is the main function of the Brain. The strategy is straight forward:
     * 
     * If the ball is the agent's kickable margin, then kick it in the direction
     * of the opposite goal.     * 
     * If not, turn in the direction of the ball then run towards it.
     */
    public void run()
    {
        // Before kick off, position the player somewhere in his side.
        sebbot.move(-Math.random() * 52.5, Math.random() * 34.0);

        
        Strategy s1 = new UniformCovering(5);
//        int lastTimeStep = 0;
        while (true) // TODO: change according to the play mode.
        {
            // TODO: debug agent skipping some steps.
//            if (fullstateInfo.getStepTime() - lastTimeStep != 1)
//            {
//                System.out.println("Agent info: " + playerNumber + " "
//                        + teamSide);
//                System.out.println("Brain Last time: " + lastTimeStep);
//                System.out.println("Brain Current time: "
//                        + fullstateInfo.getStepTime());
//            }
//            lastTimeStep = fullstateInfo.getStepTime();
            
            s1.doAction(sebbot, fullstateInfo, player);

            // Wait for next cycle before sending another command.
            try
            {
                Thread.sleep(SoccerParams.SIMULATOR_STEP);
            }
            catch (Exception e)
            {
                System.err.println(e);
            }
        }

    }
}