package edu.sdsu.cs160L.unittest.airplane;

import edu.sdsu.cs160L.unittest.airplane.engine.Engine;
import edu.sdsu.cs160L.unittest.airplane.wheel.Wheel;
import edu.sdsu.cs160L.unittest.airplane.wheel.WheelStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doReturn;

/**
 * Annotate your test class with @RunWith(MockitoJUnitRunner.class)
 */
@RunWith(MockitoJUnitRunner.class)
public class AirplaneTest {

  /**
   * The class under test in this case, Airplane, is always annotated with @InjectMocks
   */
  @InjectMocks
  private Airplane airplane;

  /**
   * All dependencies(Classes which you class under test(Airplane) uses) will be annotated with @Mock
   * In this case Airplane depends on Wheel and Engine.
   */
  @Mock
  private Wheel wheel;
  @Mock
  private Engine engine;

  /**
   * Any test function is always "public void <testScenarioName>()"
   * and annotated with @Test
   * the general syntax for mocking is
   * do<action>().when(<mockobject>).functionCall()
   * eg: doReturn(true).when(engine).isRunning();
   * notice below you can chain multiple doReturn() to return different values if a function is called multiple times in same function
   */
  @Test
  public void startPlaneSuccessfully() throws Exception {
    doReturn(false)
       .doReturn(true).when(engine).isRunning();
    doReturn(true).when(engine).ignition();
    assertThat(airplane.start())
       .isTrue();
  }

  @Test
  public void startPlaneWhenEngineIsRunning() {
    doReturn(true).when(engine).isRunning();
    //notice how we can test exceptions using assertJ
    assertThatThrownBy(() -> airplane.start())
       .isInstanceOf(RuntimeException.class);
  }

  @Test
  public void takeOffTheAirplane() {
    WheelStatus wheelStatus = new WheelStatus(true, true); //test doubles
    doReturn(wheelStatus).when(wheel).getWheelStatus();
    assertThat(airplane.takeOff()).isEqualTo(wheelStatus);
  }

  @Test
  public void landAirplane() {
    WheelStatus wheelStatus = new WheelStatus(false, false);
    doReturn(wheelStatus).when(wheel).getWheelStatus();
    assertThat(airplane.land()).isEqualTo(wheelStatus);
  }
  
  

}
