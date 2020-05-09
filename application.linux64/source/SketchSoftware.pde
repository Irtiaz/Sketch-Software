import javax.swing.JOptionPane;

PImage pic;
int cols, rows;
float w, h;

PImage[][] parts;
PImage current = null;

int stretch = 4;

void settings() {
  String picName = JOptionPane.showInputDialog(null, "Enter the name of the image with extension");
  pic = loadImage(picName);
  
  String colsAndRows = JOptionPane.showInputDialog(null, "Enter colums,rows");
  String[] colsRowsArray = colsAndRows.split(",");
  cols = Integer.parseInt(colsRowsArray[0]);
  rows = Integer.parseInt(colsRowsArray[1]);
  
  size(pic.width, pic.height);
}

void setup() {
  surface.setTitle("Sketch Helper");
  
  parts = new PImage[rows][cols];

  w = (float) width/cols;
  h = (float) height/rows;

  for (int i = 0; i < rows; i ++) {
    for (int j = 0; j < cols; j ++) {
      int startX = int(j * w);
      int startY = int(i * h);
      parts[i][j] = pic.get(startX, startY, int(w), int(h));
    }
  }
  
  textSize(20);
}

void draw() {
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


void mousePressed() {
  if (current == null) {
    int i = floor(mouseY/h);
    int j = floor(mouseX/w);
    current = parts[i][j];
  }
}

void keyPressed() {
  if (current != null) {
    if (keyCode == BACKSPACE) current = null;
    else if (keyCode == UP) stretch ++;
    else if (keyCode == DOWN) stretch --;
  }
}


void showCoordinate() {
  int j = floor(mouseX/w);
  int i = floor(mouseY/h);
  //println(i, j);
  fill(0);
  text("(" + i + "," + j + ")", 0, height - 10);
}

void showStretch() {
  fill(0);
  text("" + stretch + "X", 0, height - 10);
}

void showGrid() {
  for (float x = 0; x < width; x += w) {
    line(x, 0, x, height);
  }
  for (float y = 0; y < height; y += h) {
    line(0, y, width, y);
  }
}
