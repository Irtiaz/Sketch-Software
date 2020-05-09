import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import javax.swing.JOptionPane; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class SketchSoftware extends PApplet {



PImage pic;
int cols, rows;
float w, h;

PImage[][] parts;
PImage current = null;

int stretch = 4;

public void settings() {
  String picName = JOptionPane.showInputDialog(null, "Enter the name of the image with extension");
  pic = loadImage(picName);
  
  String colsAndRows = JOptionPane.showInputDialog(null, "Enter colums,rows");
  String[] colsRowsArray = colsAndRows.split(",");
  cols = Integer.parseInt(colsRowsArray[0]);
  rows = Integer.parseInt(colsRowsArray[1]);
  
  size(pic.width, pic.height);
}

public void setup() {
  surface.setTitle("Sketch Helper");
  
  parts = new PImage[rows][cols];

  w = (float) width/cols;
  h = (float) height/rows;

  for (int i = 0; i < rows; i ++) {
    for (int j = 0; j < cols; j ++) {
      int startX = PApplet.parseInt(j * w);
      int startY = PApplet.parseInt(i * h);
      parts[i][j] = pic.get(startX, startY, PApplet.parseInt(w), PApplet.parseInt(h));
    }
  }
  
  textSize(20);
}

public void draw() {
  if (current == null) {
    background(pic);
    showGrid();
    showCoordinate();
  } else {
    background(220);
    image(current, 0, 0, w * stretch, h * stretch);
    showStretch();
  }
}


public void mousePressed() {
  if (current == null) {
    int i = floor(mouseY/h);
    int j = floor(mouseX/w);
    current = parts[i][j];
  }
}

public void keyPressed() {
  if (current != null) {
    if (keyCode == BACKSPACE) current = null;
    else if (keyCode == UP) stretch ++;
    else if (keyCode == DOWN) stretch --;
  }
}


public void showCoordinate() {
  int j = floor(mouseX/w);
  int i = floor(mouseY/h);
  //println(i, j);
  fill(0);
  text("(" + i + "," + j + ")", 0, height - 10);
}

public void showStretch() {
  fill(0);
  text("" + stretch + "X", 0, height - 10);
}

public void showGrid() {
  for (float x = 0; x < width; x += w) {
    line(x, 0, x, height);
  }
  for (float y = 0; y < height; y += h) {
    line(0, y, width, y);
  }
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "SketchSoftware" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
