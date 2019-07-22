//Chris Quevedo

package tests;

/**
 * This class is used to create games that can be easily tested when you
 * turn the rooms to always visible.  This way you can see yourself traversing the map
 * and all of the contents of the rooms and know what behavior is expected when 
 * traveling to each room.
 * 
 */

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;

import model.Map;

public class MapTest {

  @Test
  public void test() { 
    Map game1 = new Map();
    game1.playGame();
  }

}
