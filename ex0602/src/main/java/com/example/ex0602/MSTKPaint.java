package com.example.ex0602;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MSTKPaint extends  View implements OnClickListener{

	
    private  static Paint pCircle = null;
    private  static Paint pText = null;
    private  static Paint pLine=null;
    private  Button btnKruskal=null;
    
    
    public static float downX1=190; 		 
   	public static float downY1=90;
   	public static float moveX1=120;
   	public static float moveY1=160;
   	
    public static float downX2=200;  		 
   	public static float downY2=90;
   	public static float moveX2=200;
   	public static float moveY2=230;
   	
    public static float downX3=210;   		
   	public static float downY3=90;
   	public static float moveX3=290;
   	public static float moveY3=160;
   	
    public static float downX4=110;  		
   	public static float downY4=200;
   	public static float moveX4=110;
   	public static float moveY4=320;
   	
    public static float downX5=120; 		
   	public static float downY5=200;
   	public static float moveX5=180;
   	public static float moveY5=250;
   	
    public static float downX6=220;  		
   	public static float downY6=250;
   	public static float moveX6=280;
   	public static float moveY6=200;
   	
    public static float downX7=290;   		
   	public static float downY7=200;
   	public static float moveX7=290;
   	public static float moveY7=320;
   	
    public static float downX8=180;   		
   	public static float downY8=260;
   	public static float moveX8=130;
   	public static float moveY8=330;
   	
    public static float downX9=220;   		
   	public static float downY9=260;
   	public static float moveX9=270;
   	public static float moveY9=330;
    
    public static float downX10=130;      	
	public static float downY10=340;
	public static float moveX10=270;
	public static float moveY10=340;
   
	
     public MSTKPaint(Context context,AttributeSet attrs){
    	super(context,attrs);
    	pCircle=new Paint();
        pText=new Paint();
    	pLine=new Paint();
    	
    	setOnClickListener(this);
    }     
    
     public void onClick(View v) {     	 
    		
    	    MSTKPaint.downX1 = 1170;
			MSTKPaint.downY1 = 1190;
			MSTKPaint.moveX1 = 1190;
			MSTKPaint.moveY1 = 1155;
			
			MSTKPaint.downX3 =1180;
			MSTKPaint.downY3 = 1190;
			MSTKPaint.moveX3 = 1310;
			MSTKPaint.moveY3 = 1155;
			
			MSTKPaint.downX6 = 1110;
			MSTKPaint.downY6 = 1195;
			MSTKPaint.moveX6 = 1130;
			MSTKPaint.moveY6 = 1270;
			
			MSTKPaint.downX8 = 3120;
			MSTKPaint.downY8 = 1195;
			MSTKPaint.moveX8 = 3125;
			MSTKPaint.moveY8 = 2170;
			
			MSTKPaint.downX10 = 3130;
			MSTKPaint.downY10 = 1195;
			MSTKPaint.moveX10 = 3170;
			MSTKPaint.moveY10 = 2170;				
			
			MSTKPaint.downX10 = 4125;
			MSTKPaint.downY10 = 4180;
			MSTKPaint.moveX10 = 4180;
			MSTKPaint.moveY10 = 4180;
            
			invalidate();  
    	 
     }  
    
    
    protected  void onDraw(Canvas canvas){    	
    	  super.onDraw(canvas);
          
    	  pCircle.setAntiAlias(true);					
    	  pCircle.setColor(Color.BLACK);    	  
    	  pCircle.setStrokeWidth((float)1.0);
    	  pCircle.setStyle(Style.STROKE);
    	  
    	  pText.setAntiAlias(true);					
    	  pText.setColor(Color.BLACK);    	 
    	  pText.setStrokeWidth((float)1.0);
    	  pText.setStyle(Style.STROKE);
          pText.setTextSize(20);

    	  pLine.setAntiAlias(true);				
    	  pLine.setColor(Color.BLACK);    	 
    	  pLine.setStrokeWidth((float)1.0);
    	  pLine.setStyle(Style.STROKE);  	   
    	  
    	  
    	  canvas.drawColor(Color.WHITE);					
    	  canvas.drawCircle(200, 70, 20, pCircle);			
    	  canvas.drawText("0", 195, 75, pText);				
    	  
    	  canvas.drawCircle(110,180, 20, pCircle);
    	  canvas.drawText("1", 105, 185, pText);
    	  
    	  canvas.drawCircle(290, 180, 20, pCircle);
    	  canvas.drawText("3", 285, 182, pText);
    	  
    	  canvas.drawCircle(200, 250, 20, pCircle);
    	  canvas.drawText("2", 195, 255, pText);
    	  
    	  canvas.drawCircle(110, 340, 20, pCircle);
    	  canvas.drawText("4", 105, 345, pText);
    	  
    	  canvas.drawCircle(290, 340, 20, pCircle);
    	  canvas.drawText("5", 285, 345, pText);
    	  
    	 canvas.drawText("6", 155, 125, pText);
    	 canvas.drawText("1", 200, 160, pText);
    	 canvas.drawText("5", 250, 125, pText);
    	 canvas.drawText("3", 110, 260, pText);
    	 canvas.drawText("5", 150, 225, pText);
    	 canvas.drawText("7", 250, 225, pText);
    	 canvas.drawText("2", 290, 260, pText);
    	 canvas.drawText("5", 160, 295, pText);
    	 canvas.drawText("4", 245, 295, pText);
    	 canvas.drawText("6", 200, 340, pText);
    	  
    	 
    	 
    	  canvas.drawLine(downX1, downY1, moveX1, moveY1, pLine);
    	  canvas.drawLine(downX2, downY2, moveX2, moveY2, pLine);
    	  canvas.drawLine(downX3, downY3, moveX3, moveY3, pLine);
    	  canvas.drawLine(downX4, downY4, moveX4, moveY4, pLine);
    	  canvas.drawLine(downX5, downY5, moveX5, moveY5, pLine);
    	  canvas.drawLine(downX6, downY6, moveX6, moveY6, pLine);
    	  canvas.drawLine(downX7, downY7, moveX7, moveY7, pLine);
    	  canvas.drawLine(downX8, downY8, moveX8, moveY8, pLine);
    	  canvas.drawLine(downX9, downY9, moveX9, moveY9, pLine);
    	  canvas.drawLine(downX10, downY10, moveX10, moveY10, pLine);
    	  
    	  
    }

}
