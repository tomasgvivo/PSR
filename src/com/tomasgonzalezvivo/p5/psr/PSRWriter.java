/**
* @author Tomas Gonzalez Vivo
* @see <a href="http://tomasgonzalezvivo.com/">http://tomasgonzalezvivo.com/</a>
* @version 1.0
*/

package com.tomasgonzalezvivo.p5.psr;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import processing.core.PApplet;

public class PSRWriter
{

    public static final int PSR_PASIVE = 0;
    public static final int PSR_ACTIVE = 1;
    
    private boolean active = false;
    private FileOutputStream output;
    private boolean started = false;
    private PApplet app;

    private int frame = 0;

/**
* @param mode
* If is set to PSRWriter.PSR_PASIVE, it wont recreate any graphics.
* If is set to PSRWriter.PSR_ACTIVE, it would recreate parsed commands in the spesified PApplet.
* @param file The path to the .psr output file.
* @param app The PApplet of your sketch.
*/
    public PSRWriter(int mode, String file, PApplet app)
    {
        if(mode == PSR_ACTIVE) active = true;
        File f = new File(file);
        if(!f.exists())
        {
            try
            {
              f.createNewFile();
            } catch(Exception ex){app.println(ex);}
        }
        try
        {
          output = new FileOutputStream(f);
        } catch(Exception ex){app.println(ex);}
        write(getStringBytes("PSR"));
        this.app = app;
        app.registerMethod("dispose", this);
    }
/**
* Call this method to start recording.
*/
    public void start()
    {
        write((byte) 0xF1);
        started = true;
    }
/**
* Call this method to stop recording.
*/
    public void stop()
    {
        write((byte) 0xF0);
        started = false;
        try
        {
            output.flush();
        } catch (IOException ex){}
    }

/**
* Creates a new frame.
*/
    public void newFrame()
    {
        write((byte) 0xF2);
        write(getIntBytes(frame));
        frame++;
    }
    
    public void setVar(byte i, int val)
    {
        write((byte) 0xF3);
        write(i);
        write(getIntBytes(val));
    }
    
    private void write(byte b)
    {
        try
        {
            output.write(b);
        } catch (IOException ex) {}
    }
    
    private void write(byte[] b)
    {
        try
        {
            output.write(b);
        } catch (IOException ex) {}
    }
    
    private byte[] getIntBytes(int i)
    {
        return new byte[] {
            (byte)  (i >>> 24),
            (byte)  (i >>> 16),
            (byte)  (i >>> 8),
            (byte)   i
        };
    }
    
    private byte[] getFloatBytes(float f)
    {
        return getIntBytes(Float.floatToIntBits(f));
    }
    
    private byte[] getStringBytes(String s)
    {
        return s.getBytes();
    }
/**
* Would run automaticaly when the window is closed to save any changes in the .psr file.
*/
    protected void dispose()
    {
        write((byte)0xF0);
        try
        {
            output.flush();
        } catch (IOException ex) {app.println(ex);}
    }

    public void ellipse(float x, float y, float w, float h)
    {
        write((byte)0x01);
        write(getIntBytes((int)x));
        write(getIntBytes((int)y));
        write(getIntBytes((int)w));
        write(getIntBytes((int)h));
        if(active) app.ellipse(x, y, w, h);
    }
    
    public void line(float x, float y, float x2, float y2)
    {
        write((byte)0x02);
        write(getIntBytes((int)x));
        write(getIntBytes((int)y));
        write(getIntBytes((int)x2));
        write(getIntBytes((int)y2));
        if(active) app.line(x, y, x2, y2);
    }
    
    public void point(float x, float y)
    {
        write((byte)0x03);
        write(getIntBytes((int)x));
        write(getIntBytes((int)y));
        if(active) app.point(x, y);
    }
    
    public void rect(float x, float y, float w, float h)
    {
        write((byte)0x04);
        write(getIntBytes((int)x));
        write(getIntBytes((int)y));
        write(getIntBytes((int)w));
        write(getIntBytes((int)h));
        write(getIntBytes((int)0));
        write(getIntBytes((int)0));
        write(getIntBytes((int)0));
        write(getIntBytes((int)0));
        write(getIntBytes((int)0));
        if(active) app.rect(x, y, w, h);
    }
    
    public void triangle(float x, float y, float x2, float y2, float x3, float y3)
    {
        write((byte)0x05);
        write(getIntBytes((int)x));
        write(getIntBytes((int)y));
        write(getIntBytes((int)x2));
        write(getIntBytes((int)y2));
        write(getIntBytes((int)x3));
        write(getIntBytes((int)y3));
        if(active) app.triangle(x, y, x2, y2, x3, y3);
    }
    
    public void quad(float x, float y, float x2, float y2, float x3, float y3, float x4, float y4)
    {
        write((byte)0x06);
        write(getIntBytes((int)x));
        write(getIntBytes((int)y));
        write(getIntBytes((int)x2));
        write(getIntBytes((int)y2));
        write(getIntBytes((int)x3));
        write(getIntBytes((int)y3));
        write(getIntBytes((int)x4));
        write(getIntBytes((int)y4));
        if(active) app.quad(x, y, x2, y2, x3, y3, x4, y4);
    }
    
    public void arc(float x, float y, float w, float h, int b, int e, int m)
    {
        write((byte)0x07);
        write(getIntBytes((int)x));
        write(getIntBytes((int)y));
        write(getIntBytes((int)w));
        write(getIntBytes((int)h));
        write(getIntBytes(b));
        write(getIntBytes(e));
        write(getIntBytes(m));
        if(active) app.arc(x, y, w, h, b, e, m);
    }
    
    public void set(int x, int y, int r, int g, int b)
    {
        write((byte)0x08);
        write(getIntBytes(x));
        write(getIntBytes(y));
        write((byte)r);
        write((byte)g);
        write((byte)b);
        if(active) app.set(x, y, app.color(r, g, b));
    }
    
    public void background(int r, int g, int b)
    {
        write((byte)0x09);
        write((byte)r);
        write((byte)g);
        write((byte)b);
        if(active) app.background(r, g, b);
    }
    public void background(int g)
    {
      background(g, g, g);
    }
    
    public void ellipseMode(int m)
    {
        write((byte)0x10);
        write(getIntBytes(m));
        if(active) app.ellipseMode(m);
    }
    
    public void rectMode(int m)
    {
        write((byte)0x11);
        write(getIntBytes(m));
        if(active) app.rectMode(m);
    }
    
    public void strokeCap(int v)
    {
        write((byte)0x12);
        write(getIntBytes(v));
        if(active) app.strokeCap(v);
    }
    
    public void strokeJoin(int v)
    {
        write((byte)0x13);
        write(getIntBytes(v));
        if(active) app.strokeJoin(v);
    }
    
    public void strokeWeight(int v)
    {
        write((byte)0x14);
        write(getIntBytes(v));
        if(active) app.strokeWeight(v);
    }
    
    public void noStroke()
    {
        write((byte)0x20);
        if(active) app.noStroke();
    }
    
    public void stroke(int r, int g, int b, int a)
    {
        write((byte)0x21);
        write((byte)r);
        write((byte)g);
        write((byte)b);
        write((byte)a);
        if(active) app.stroke(r, g, b, a);
    }
    public void stroke(int g)
    {
      stroke(g, g, g, 255);
    }
    public void stroke(int g, int a)
    {
      stroke(g, g, g, a);
    }
    public void stroke(int r, int g, int b)
    {
      stroke(r, g, b, 255);
    }
    
    public void fill(int r, int g, int b, int a)
    {
        write((byte)0x22);
        write((byte)r);
        write((byte)g);
        write((byte)b);
        write((byte)a);
        if(active) app.fill(r, g, b, a);
    }
    public void fill(int g)
    {
      fill(g, g, g, 255);
    }
    public void fill(int g, int a)
    {
      fill(g, g, g, a);
    }
    public void fill(int r, int g, int b)
    {
      fill(r, g, b, 255);
    }
    
    public void noFill()
    {
        write((byte)0x23);
        if(active) app.noFill();
    }
    
    public void text(String s, int x, int y)
    {
        write((byte)0x90);
        write(getIntBytes(x));
        write(getIntBytes(y));
        write(getIntBytes(s.length()));
        write(getStringBytes(s));
        if(active) app.text(s, x, y);
    }
    public void text(int s, int x, int y)
    {
        text("" + s, x, y);
    }
    public void text(float s, int x, int y)
    {
        text("" + s, x, y);
    }
    public void text(boolean s, int x, int y)
    {
        text("" + s, x, y);
    }
    
    public void textAlign(int v)
    {
        write((byte)0x91);
        write(getIntBytes(v));
        if(active) app.textAlign(v);
    }
    
    public void textLeading(int v)
    {
        write((byte)0x92);
        write(getIntBytes(v));
        if(active) app.textLeading(v);
    }
    
    public void textMode(int v)
    {
        write((byte)0x93);
        write(getIntBytes(v));
        if(active) app.textMode(v);
    }
    
    public void textSize(int v)
    {
        write((byte)0x94);
        write(getIntBytes(v));
        if(active) app.textSize(v);
    }
    
}
