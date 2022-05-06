import java.awt.*;
import java.util.*;

public class SandLab
{
  public static void main(String[] args)
  {
    SandLab lab = new SandLab(120, 80);
    lab.run();
  }
  
  //add constants for particle types here
  public static final int EMPTY = 0;
  public static final int METAL = 1;
  public static final int SAND = 2;
  
  //do not add any more fields
  private int[][] grid;
  private SandDisplay display;
  
  public SandLab(int numRows, int numCols)
  {
    String[] names;
    names = new String[3];
    names[EMPTY] = "Empty";
    names[METAL] = "Metal";
    names[SAND] = "Sand";
    display = new SandDisplay("Falling Sand", numRows, numCols, names);
    grid = new int[numRows][numCols];
  }
  
  //called when the user clicks on a location using the given tool
  private void locationClicked(int row, int col, int tool)
  { grid[row][col] = tool;
  }

  //copies each element of grid into the display
  public void updateDisplay()
  {for(int r = 0; r < grid.length; r ++) {
    for (int c = 0; c < grid[0].length; c++) {
      if (grid[r][c] == EMPTY) {
        display.setColor(r, c, Color.black);
      }
      if (grid[r][c] == METAL) {
        display.setColor(r, c, Color.gray);
      }
      if (grid[r][c] == SAND) {
        display.setColor(r, c, new Color(255, 204, 51));
      }
    }
  }
  }

  //called repeatedly.
  //causes one random particle to maybe do something.
  public void step()
  { int randomRow = (int)(Math.random() * (grid.length-1));
    int randomCol = (int)(Math.random() * (grid[0].length-1));
    if (grid[randomRow][randomCol] == SAND && grid[randomRow+1][randomCol] == EMPTY){
      grid[randomRow][randomCol] = EMPTY;
      grid[randomRow+1][randomCol] = SAND;
    }
  }
  
  //do not modify
  public void run()
  {
    while (true)
    {
      for (int i = 0; i < display.getSpeed(); i++)
        step();
      updateDisplay();
      display.repaint();
      display.pause(1);  //wait for redrawing and for mouse
      int[] mouseLoc = display.getMouseLocation();
      if (mouseLoc != null)  //test if mouse clicked
        locationClicked(mouseLoc[0], mouseLoc[1], display.getTool());
    }
  }
}
