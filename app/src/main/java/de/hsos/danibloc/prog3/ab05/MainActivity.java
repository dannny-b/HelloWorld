package de.hsos.danibloc.prog3.ab05;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private Canvas canvas;
    private Bitmap bitmap;
    private Paint paint;
    private final int width = 800, height = 800, textSize = 50, offset = 100;
    private Timer timer;

    int grenzeLinks = 30;
    int grenzeRechts = 770;
    int grenzeOben = 600;
    int grenzeUnten = 770;

    int ballRadius = 20;
    float ballX = 100f;
    float ballY = 700f;
    int factor = 3;
    float velociteX = 0.3f*factor;
    float velociteY = 4.5f*factor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.bitmap = Bitmap.createBitmap(this.width, this.height, Bitmap.Config.ARGB_8888);
        this.canvas = new Canvas(this.bitmap);
        this.imageView = new ImageView(this);
        this.imageView.setImageBitmap(this.bitmap);
        this.paint = new Paint();
        this.setContentView(imageView);
        this.canvas.drawColor(Color.argb(255, 0, 0, 255));
        this.paint.setTextSize(this.textSize);
        this.helloWorld();
        this.helloNeighbours();
        this.zeichneSmiley(100);
        timer = new Timer();
        this.timer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        theJumpingPoint();
                    }
                }
                , 0, 17);
    }

    private void helloWorld() {
        String text = "Hello World!";
        float textWidth = this.paint.measureText(text);
        this.paint.setColor(Color.WHITE);
        this.canvas.drawText(text, width / 2 - textWidth / 2, 100, this.paint);
    }

    private void centralizeText(String text, int y) {
        float textWidth = this.paint.measureText(text);
        this.canvas.drawText(text, width / 2 - textWidth / 2, y, this.paint);
    }

    private void helloNeighbours() {
        centralizeText("Hello Max + Tom!", 100 + textSize);
    }

    public void zeichneSmiley(int radius) {
        int midCircle = 100 + 2 * textSize + offset + radius;
        this.paint.setColor(Color.GREEN);
        this.canvas.drawCircle(width / 2, midCircle, radius, this.paint);
        this.paint.setColor(Color.BLACK);
        Point topLeft = new Point(midCircle - (2 * radius / 3), midCircle - (2 * radius / 3));
        Point topLeft_right = new Point(midCircle + (radius / 3), midCircle - (2 * radius / 3));
        this.canvas.drawRect(topLeft.x, topLeft.y, topLeft.x + radius / 3, topLeft.y + radius / 3, this.paint);
        this.canvas.drawRect(topLeft_right.x, topLeft_right.y, topLeft_right.x + radius / 3, topLeft_right.y + radius / 3, this.paint);
        Point l1 = new Point(midCircle - (radius / 2), midCircle + (radius / 2));
        Point r1 = new Point(l1.x + radius, l1.y);
        Point mid1 = new Point(l1.x + radius / 4, l1.y + radius / 4);
        this.canvas.drawLine(l1.x, l1.y, mid1.x, mid1.y, this.paint);
        this.canvas.drawLine(mid1.x, mid1.y, r1.x, r1.y, this.paint);
    }

    private void theJumpingPoint() {
        Log.i("YOLO", "LOL");
        this.paint.setColor(Color.BLUE);
        this.canvas.drawCircle(ballX, ballY, ballRadius, this.paint);
       if(ballY - velociteY <= grenzeOben || ballY - velociteY >= grenzeUnten){
           velociteY*=-1;
       }
       if(ballX + velociteX >= grenzeRechts || ballX + velociteX <= grenzeLinks){
           velociteX *= -1;
       }

        ballX += velociteX;
        ballY -= velociteY;
        this.paint.setColor(Color.RED);
        this.canvas.drawCircle(ballX, ballY, ballRadius, this.paint);
        this.imageView.postInvalidate();
    }

}