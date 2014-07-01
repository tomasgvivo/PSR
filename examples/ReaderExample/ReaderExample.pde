import com.tomasgonzalezvivo.p5.psr.PSRReader;

//declare reader object
PSRReader reader;

public void setup()
{
  size(400, 300);
  //create a new instance of a PSRReader with the PSR file "example.psr" located at the sketch folder
  reader = new PSRReader("example.psr", this);
  reader.start();
}

public void draw()
{
  //reads and render the next frame
  reader.nextFrame();
  if(reader.isEOF())
  {
    //this would happen if the reader reaches the end of the file
    println("End of file!");
    background(30);
    noLoop();
  }
}
