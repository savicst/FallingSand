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
  public static final int WATER = 3;
  public static final int LAVA = 4;
  
  //do not add any more fields
  private int[][] grid;
  private SandDisplay display;
  
  public SandLab(int numRows, int numCols)
  {
    String[] names;
    names = new String[5];
    names[EMPTY] = "Empty";
    names[METAL] = "Metal";
    names[SAND] = "Sand";
    names[WATER] = "Water";
    names[LAVA] = "Lava" ;

    display = new SandDisplay("Falling Sand", numRows, numCols, names);
    grid = new int[numRows][numCols];
  }
  
  //called when the user clicks on a location using the given tool
  private void locationClicked(int row, int col, int tool)
  { grid[row][col] = tool;
  }

  //copies each element of grid into the display
  public void updateDisplay() {
    for (int r = 0; r < grid.length; r++) {
      for (int c = 0; c < grid[0].length; c++) {
        if (grid[r][c] == EMPTY) {
          display.setColor(r, c, new Color(16, 16, 17, 255));
        }
        if (grid[r][c] == METAL) {
          display.setColor(r, c, new Color(58, 26, 84));
        }
        if (grid[r][c] == SAND) {
          display.setColor(r, c, new Color(199, 171, 82));
        }
        if (grid[r][c] == WATER) {
          display.setColor(r, c, new Color(65, 180, 187, 178));
        }
        if (grid[r][c] == LAVA) {
          display.setColor(r, c, new Color(246, 49, 95));
        }
      }
    }
  }

  //called repeatedly.
  //causes one random particle to maybe do something.
  public void step()
  { int randomRow = (int)(Math.random() * (grid.length-1));
    int randomCol = (int)(Math.random() * (grid[0].length-1));
    //Sand procedure
    if (grid[randomRow][randomCol] == SAND && (grid[randomRow+1][randomCol] == EMPTY || grid[randomRow+1][randomCol] == WATER || grid[randomRow+1][randomCol]== LAVA)){
      grid[randomRow][randomCol] = grid[randomRow+1][randomCol];
      grid[randomRow+1][randomCol] = SAND;
    }

    //Water procedure
    int left = 0;int right = 1;
    if (grid[randomRow][randomCol] == WATER){
      int direction = (int)(Math.random()+.5);
      if (grid[randomRow+1][randomCol] == EMPTY){
        grid[randomRow][randomCol] = EMPTY;
        grid[randomRow+1][randomCol] = WATER;
      }
      else if (direction==left && randomCol>0 && grid[randomRow][randomCol-1]==EMPTY){
        grid[randomRow][randomCol] = EMPTY;
        grid[randomRow][randomCol-1] = WATER;
      }
      else if (direction==right && randomCol<grid.length-1 && grid[randomRow][randomCol+1]==EMPTY){
        grid[randomRow][randomCol] = EMPTY;
        grid[randomRow][randomCol+1] = WATER;
      }
    }

    //Lava procedure
    if (grid[randomRow][randomCol] == LAVA){

      int direction = (int)(Math.random()+.5);
      //down
      if (grid[randomRow+1][randomCol] == EMPTY){
        grid[randomRow][randomCol] = EMPTY;
        grid[randomRow+1][randomCol] = LAVA;
      }
      else if (grid[randomRow+1][randomCol] == WATER){
        grid[randomRow][randomCol] = EMPTY;
        grid[randomRow+1][randomCol] = METAL;
      }
      //left
      else if (direction==left && randomCol>0) {
        if (grid[randomRow][randomCol - 1] == EMPTY) {
          grid[randomRow][randomCol] = EMPTY;
          grid[randomRow][randomCol-1] = LAVA;
        }
        else if (grid[randomRow][randomCol - 1] == WATER){
          grid[randomRow][randomCol] = EMPTY;
          grid[randomRow][randomCol-1] = METAL;
        }
      }
      //right
      else if (direction==right && randomCol<grid.length-1){
        if(grid[randomRow][randomCol+1]==EMPTY) {
          grid[randomRow][randomCol] = EMPTY;
          grid[randomRow][randomCol + 1] = LAVA;
        }
        else if (grid[randomRow][randomCol+1] == WATER){
          grid[randomRow][randomCol] = EMPTY;
          grid[randomRow][randomCol+1] = METAL;
        }
      }
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
