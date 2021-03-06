import com.tomasgonzalezvivo.p5.psr.PSRWriter;

//declare writer object
PSRWriter writer;

public void setup()
{
  size(400, 300);
  //create a new instance of a PSRWriter as an Active writer and save the file in the sketch folder as "example.psr"
  writer = new PSRWriter(PSRWriter.PSR_ACTIVE, "example.psr", this);
  //start recording
  writer.start();
}

public void draw()
{
  //draw some fancy stuff
  writer.background(60, 60, 60);
  writer.fill(200, 100, 100);
  writer.noStroke();
  writer.rect(100, 50, 200, 200);
  writer.stroke(200);
  
  //cross at cursor position
  writer.line(mouseX, mouseY-10, mouseX, mouseY+10);
  writer.line(mouseX-10, mouseY, mouseX+10, mouseY);
  
  //text() without "writer." won't be recorded
  text("This text wont be recorded", 20, 20);
  
  //generate a new frame in the .psr file
  writer.newFrame();
  
  if(frameCount == 60*10)
  {
    //stop recording at 10 seconds
    writer.stop();
    exit();
  }
}
