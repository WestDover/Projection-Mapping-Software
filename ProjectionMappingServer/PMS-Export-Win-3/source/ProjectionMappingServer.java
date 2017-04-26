import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import deadpixel.keystone.*; 
import processing.net.*; 
import processing.video.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class ProjectionMappingServer extends PApplet {

/**
  * Projector Keystoning Software for Skewed Surface Projection -- WestDoverLabs
  * Server Edition v0.2.0
 */






//Define Server
Server s;

Keystone ks;
CornerPinSurface surface;

PGraphics offscreen;

//Define Images
PImage Image;
PImage Image_2;
PImage Image_3;
PImage Image_4;
PImage Image_5;
PImage Image_6;
PImage Image_7;
PImage Image_8;
PImage Image_9;
PImage Image_10;
PImage Image_11;
PImage Image_13;
PImage Image_14;
PImage Image_15;
PImage Image_16;
PImage Image_18;
PImage Image_19;
PImage Image_20;

//Define Videos
Movie Video_1;

//Define change integer
int slideSelect = 0;

public void setup() {
  // Keystone will only work with P3D or OPENGL renderers, 
  // since it relies on texture mapping to deform
  
  //size(640, 480, P3D);
  //frame.setLocation(-1024, 0);
  
  size(displayWidth, displayHeight, P3D);
  
  //frame.setLocation(0,0);
  
  //fs = new FullScreen(this, 2); 
  
  //fs.enter();
  
  //Define Image Locations
  Image = loadImage("slide1.jpg");
  Image_2 = loadImage("slide2.jpg");
  Image_3 = loadImage("slide3.jpg");
  Image_4 = loadImage("slide4.jpg");
  Image_5 = loadImage("slide5.jpg");
  Image_6 = loadImage("slide6.jpg");
  Image_7 = loadImage("slide7.jpg");
  Image_8 = loadImage("slide8.jpg");
  Image_9 = loadImage("slide9.jpg");
  Image_10 = loadImage("slide10.jpg");
  Image_11 = loadImage("slide11.jpg");
  Image_13 = loadImage("slide13.jpg");
  Image_14 = loadImage("slide14.jpg");
  Image_15 = loadImage("slide15.jpg");
  Image_16 = loadImage("slide16.jpg");
  Image_18 = loadImage("slide18.jpg");
  Image_19 = loadImage("slide19.jpg");
  Image_20 = loadImage("slide20.jpg");
  
  //Define Video Locations
  Video_1 = new Movie(this, "video1.mov");

  ks = new Keystone(this);
  surface = ks.createCornerPinSurface(960, 720, 20);
  
  offscreen = createGraphics(960, 720, P3D);
  
  //Create Server Instance
  frameRate(30);
  s = new Server(this, 12006);
  
  //Write the '0' status to the server out
  s.write(slideSelect);
}

//Remove frame graphics -- for fullscreen mode
public void init() {
  frame.removeNotify();
  frame.setUndecorated(true);
  frame.addNotify();
  super.init();
}


public void draw() {

  PVector surfaceMouse = surface.getTransformedMouse();

  offscreen.beginDraw();
  offscreen.background(0);
  
  //Detect the value of slideSelect and change the slide accordingly
  if(slideSelect == 0) {
    offscreen.image(Image, 0, 0);
    
  } else if(slideSelect == 1) {
    offscreen.image(Image_2, 0, 0);
    
  } else if(slideSelect == 2) {
    Video_1.stop();
    offscreen.image(Image_3, 0, 0);
    
  } else if(slideSelect == 3) {
    Video_1.loop();
    offscreen.image(Video_1, 0, 0);
    
  } else if(slideSelect == 4) {
    offscreen.image(Image_5, 0, 0);
    Video_1.stop();
    
  } else if(slideSelect == 5) {
    offscreen.image(Image_6, 0, 0);
    
  } else if(slideSelect == 6) {
    offscreen.image(Image_7, 0, 0);
    
  } else if(slideSelect == 7) {
    offscreen.image(Image_8, 0, 0);
    
  } else if(slideSelect == 8) {
    offscreen.image(Image_9, 0, 0);
    
  } else if(slideSelect == 9) {
    offscreen.image(Image_10, 0, 0);
    
  } else if(slideSelect == 10) {
    offscreen.image(Image_11, 0, 0);
    
  } else if(slideSelect == 11) {
    offscreen.image(Image_13, 0, 0);
    
  } else if(slideSelect == 12) {
    offscreen.image(Image_14, 0, 0);
    
  } else if(slideSelect == 13) {
    offscreen.image(Image_15, 0, 0);
    
  } else if(slideSelect == 14) {
    offscreen.image(Image_16, 0, 0);
    
  } else if(slideSelect == 15) {
    offscreen.image(Image_18, 0, 0);
    
  } else if(slideSelect == 16) {
    offscreen.image(Image_19, 0, 0);
    
  } else if(slideSelect == 17) {
    offscreen.image(Image_20, 0, 0);
    
  } else {
    
  }
  
  offscreen.endDraw();

  background(0);
 
  // render the scene, transformed using the corner pin surface
  surface.render(offscreen);
}

public void keyPressed() {
  switch(key) {
  case 'c':
    // enter/leave calibration mode, where surfaces can be warped 
    // and moved
    ks.toggleCalibration();
    break;

  case 'l':
    // loads the saved layout
    ks.load();
    break;

  case 's':
    // saves the layout
    ks.save();
    break;
    
  case ',':
    //backwards the slides
    backwardSlide();
    break;
    
  case '.':
    //forwards the slides
    forwardSlide();
    break;
  }
}

//Function to remove one from the value of slideSelect -- also writes out value to the server
public void backwardSlide() {
  if(slideSelect > 0) {
   slideSelect = slideSelect - 1;
   s.write(slideSelect);
  } else {
  }
}

//Function to add one from the value of slideSelect -- also writes out value to the server
public void forwardSlide() {
  if(slideSelect < 17) {
   slideSelect = slideSelect + 1;
   s.write(slideSelect);
  } else {
  }
}


//Read the Movie -- Frame but Frame
public void movieEvent(Movie m) {
  m.read();
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--full-screen", "--bgcolor=#666666", "--hide-stop", "ProjectionMappingServer" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
