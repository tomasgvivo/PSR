/**
* @author Tomas Gonzalez Vivo
* @see <a href="http://tomasgonzalezvivo.com/">http://tomasgonzalezvivo.com/</a>
* @version 1.0
*/

package com.tomasgonzalezvivo.p5.psr;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import processing.core.PApplet;

public class PSRReader
{
    private FileInputStream input;
    private ByteBuffer buffer;
    private boolean eof = false;
    private PApplet app;
    private int[] var = new int[256];
/**
* @param file The path to the .psr file.
* @param app The PApplet of your sketch.
*/
    public PSRReader(String file, PApplet app)
    {
        try
        {
          input = new FileInputStream(file);
        } catch(Exception ex){app.println(ex);}
        if(!bytesToString(read(3)).equals("PSR")) System.err.println(file + " is not a PSR file..");
        this.app = app;
        
    }
/**
* Start reading the file and get ready for reproducing the recorded sketch.
*/
    public void start()
    {
        byte res = 0;
        while(res != (byte)0xF1 && !eof)
        {
            res = read();
        }
    }
/**
* Render the next frame in the .psr file by reading the psr commands and route them to the corresponding methods until it reachs the next frame or the EOF.
*/
    public void nextFrame()
    {
        byte res = 0;
        while(res != (byte)0xF2 && !eof)
        {
            res = read();
            route(res);
        }
    }
/**
* Routes commands to the corresponding methods.
* @param cmd A byte with a PSR command.
*/
    public void route(byte cmd)
    {
        switch(cmd)
        {
            case (byte)0x01: ellipse(); break;
            case (byte)0x02: line(); break;
            case (byte)0x03: point(); break;
            case (byte)0x04: rect(); break;
            case (byte)0x05: triangle(); break;
            case (byte)0x06: quad(); break;
            case (byte)0x07: arc(); break;
            case (byte)0x08: set(); break;
            case (byte)0x09: background(); break;
            
            case (byte)0x10: ellipseMode(); break;
            case (byte)0x11: rectMode(); break;
            case (byte)0x12: strokeCap(); break;
            case (byte)0x13: strokeJoin(); break;
            case (byte)0x14: strokeWeight(); break;
            
            case (byte)0x20: app.noStroke(); break;
            case (byte)0x21: stroke(); break;
            case (byte)0x22: fill(); break;
            case (byte)0x23: app.noFill(); break;
            
            case (byte)0x30: applyMatrix(); break;
            case (byte)0x31: app.resetMatrix(); break;
            case (byte)0x32: app.popMatrix(); break;
            case (byte)0x33: app.pushMatrix(); break;
            case (byte)0x34: break;
            case (byte)0x35: break;
            case (byte)0x36: break;
            case (byte)0x37: break;
            case (byte)0x38: break;
            case (byte)0x39: break;
            case (byte)0x3A: break;
            case (byte)0x3B: break;
            
            case (byte)0x90: text(); break;
            case (byte)0x91: textAlign(); break;
            case (byte)0x92: textLeading(); break;
            case (byte)0x93: textMode(); break;
            case (byte)0x94: textSize(); break;
            
            case (byte)0xF0: stop();
            case (byte)0xF3: var(); break;
            
            case (byte)0xF2: System.out.println("frame " + bytesToInt(read(4))); break;
        }
    }
    
    private void ellipse()
    {
        app.ellipse(bytesToInt(read(4)), bytesToInt(read(4)), bytesToInt(read(4)), bytesToInt(read(4)));
    }
    private void line()
    {
        app.line(bytesToInt(read(4)), bytesToInt(read(4)), bytesToInt(read(4)), bytesToInt(read(4)));
    }
    private void point()
    {
        app.point(bytesToInt(read(4)), bytesToInt(read(4)));
    }
    private void rect()
    {
        app.rect(bytesToInt(read(4)), bytesToInt(read(4)), bytesToInt(read(4)), bytesToInt(read(4)));
    }
    private void triangle()
    {
        app.triangle(bytesToInt(read(4)), bytesToInt(read(4)), bytesToInt(read(4)), bytesToInt(read(4)), bytesToInt(read(4)), bytesToInt(read(4)));
    }
    private void quad()
    {
        app.quad(bytesToInt(read(4)), bytesToInt(read(4)), bytesToInt(read(4)), bytesToInt(read(4)), bytesToInt(read(4)), bytesToInt(read(4)), bytesToInt(read(4)), bytesToInt(read(4)));
    }
    private void arc()
    {
        app.arc(bytesToInt(read(4)), bytesToInt(read(4)), bytesToInt(read(4)), bytesToInt(read(4)), bytesToInt(read(4)), bytesToInt(read(4)), bytesToInt(read(4)));
    }
    private void set()
    {
        app.set(bytesToInt(read(4)), bytesToInt(read(4)), app.color(byteToInt(read()), byteToInt(read()), byteToInt(read())));
    }
    private void background()
    {
        app.background(byteToInt(read()), byteToInt(read()), byteToInt(read()));
    }
    private void ellipseMode()
    {
        app.ellipseMode(bytesToInt(read(4)));
    }
    private void rectMode()
    {
        app.rectMode(bytesToInt(read(4)));
    }
    private void strokeCap()
    {
        app.strokeCap(bytesToInt(read(4)));
    }
    private void strokeJoin()
    {
        app.strokeJoin(bytesToInt(read(4)));
    }
    private void strokeWeight()
    {
        app.strokeWeight(bytesToInt(read(4)));
    }
    private void applyMatrix()
    {
        app.applyMatrix(this.bytesToFloat(read(4)), this.bytesToFloat(read(4)), this.bytesToFloat(read(4)), this.bytesToFloat(read(4)),
                        this.bytesToFloat(read(4)), this.bytesToFloat(read(4)), this.bytesToFloat(read(4)), this.bytesToFloat(read(4)),
                        this.bytesToFloat(read(4)), this.bytesToFloat(read(4)), this.bytesToFloat(read(4)), this.bytesToFloat(read(4)),
                        this.bytesToFloat(read(4)), this.bytesToFloat(read(4)), this.bytesToFloat(read(4)), this.bytesToFloat(read(4)));
    }
    private void stroke()
    {
        app.stroke(app.color(byteToInt(read()), byteToInt(read()), byteToInt(read()), byteToInt(read())));
    }
    private void fill()
    {
        app.fill(app.color(byteToInt(read()), byteToInt(read()), byteToInt(read()), byteToInt(read())));
    }
    private void text()
    {
        int x = bytesToInt(read(4));
        int y = bytesToInt(read(4));
        int l = bytesToInt(read(4));
        String s = bytesToString(read(l));
        app.text(s, x, y);
    }
    private void textAlign()
    {
        app.textAlign(bytesToInt(read(4)), bytesToInt(read(4)));
    }
    private void textLeading()
    {
        app.textLeading(bytesToInt(read(4)));
    }
    private void textMode()
    {
        app.textMode(bytesToInt(read(4)));
    }
    private void textSize()
    {
        app.textSize(bytesToInt(read(4)));
    }
    private void var()
    {
        var[byteToInt(read())] = bytesToInt(read(4));
    }
    
    private byte read()
    {
        try
        {
            if(input.available() == 0)
            {
                eof = true;
                return 0;
            }
            if(input.available() > 0)
            return (byte)input.read();
        } catch (IOException ex){}
        return 0;
    }
    
    private byte[] read(int l)
    {
        byte[] res = new byte[l];
        int i = 0;
        try
        {
            if(input.available() == 0)
            {
                eof = true;
                return res;
            }
            while(input.available() > 0 && i < l)
            {
                res[i] = (byte)input.read();
                i++;
            }
            return res;
        } catch (IOException ex){}
        return res;
    }
    
    private int bytesToInt(byte[] bytes)
    {
        return bytes[0] << 24 | (bytes[1] & 0xFF) << 16 | (bytes[2] & 0xFF) << 8 | (bytes[3] & 0xFF);
    }
    private float bytesToFloat(byte[] bytes)
    {
        return Float.intBitsToFloat(bytesToInt(bytes));
    }
    private int byteToInt(byte b)
    {
        return 0x0 << 24 | (0x0 & 0xFF) << 16 | (0x0 & 0xFF) << 8 | (b & 0xFF);
    }
    private String bytesToString(byte[] bytes)
    {
        String s = "";
        for(byte b : bytes) s += "" + (char)b;
        return s;
    }

    private void stop()
    {
        eof = true;
    }
/**
* Returns true if the input reader reaches the end of the file.
*/
    public boolean isEOF()
    {
        return eof;
    }
    
}
