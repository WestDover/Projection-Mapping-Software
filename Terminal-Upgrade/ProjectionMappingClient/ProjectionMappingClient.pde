/**
  * Projector Keystoning Software for Skewed Surface Projection -- WestDoverLabs
  * Client Version - v0.2.0
 */

import deadpixel.keystone.*;
import processing.net.*;
import processing.video.*;
import controlP5.*;

//Define Client
Client c;

String IP = "127.0.0.1"; 

Keystone ks;
CornerPinSurface surface;

PGraphics offscreen;

ControlP5 cp5;

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

Boolean terminalVisible = false;

//Define Videos
Movie Video_1;

//Define change integer
int slideSelect = 0;

void setup() {
  // Keystone will only work with P3D or OPENGL renderers, 
  // since it relies on texture mapping to deform
  size(displayWidth, displayHeight, P3D);
  
  //size(1000, 1000, P3D);
  
  //frame.setLocation(-displayWidth,0);
  
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
  
  //Set Font
  PFont font = createFont("arial",20);
  
  //Create Instance
  cp5 = new ControlP5(this);

  cp5.addTextfield("terminal")
     .setPosition(30,30)
     .setSize(displayWidth/2,50)
     .setFont(font)
     .setFocus(true)
     .setColor(color(255,0,0))
     .setVisible(false)
     ;

  
  //Create Server Instance
  frameRate(30);
  setClient();
}

void setClient() {
  c = new Client(this, IP, 12006);
}


public void init() {
  frame.removeNotify();
  frame.setUndecorated(true);
  frame.addNotify();
  super.init();
}

void draw() {

  PVector surfaceMouse = surface.getTransformedMouse();
  
  if (c.available() > 0) { 
    slideSelect = c.read(); 
  }

  offscreen.beginDraw();
  offscreen.background(0);
  
  if(slideSelect == 0) {
    offscreen.image(Image, 0, 0);
    
  } else if(slideSelect == 1) {
    offscreen.image(Image_2, 0, 0);
    
  } else if(slideSelect == 2) {
    Video_1.pause();
    offscreen.image(Image_3, 0, 0);
    
  } else if(slideSelect == 3) {
    Video_1.loop();
    offscreen.image(Video_1, 0, 0);
    
  } else if(slideSelect == 4) {
    offscreen.image(Image_5, 0, 0);
    Video_1.pause();
    
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

void keyPressed() {
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
    
    
  case 't':
    //loads the terminal
    loadTerminal();
    break;
    
  case 'u':
    //updates IP
    loadNetwork();
    break;
    
  }
}


void loadTerminal() {
  if(terminalVisible == false) {
    cp5.get(Textfield.class,"terminal").setVisible(true);
  } else {
    cp5.get(Textfield.class,"terminal").setVisible(false);
  }
}

void loadNetwork() {
  IP = cp5.get(Textfield.class,"terminal").getText();
  cp5.get(Textfield.class,"terminal").clear();
  setClient(); 
}


void movieEvent(Movie m) {
  m.read();
}
