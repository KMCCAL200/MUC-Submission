package gcu.ac.uk.gamenews;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

public class DrawDisk extends Activity {

    public class SampleView extends View {

        // Declare variables
        private Animation spin;
        Bitmap disk;

        public SampleView(Context context) {
            super(context);
            //get image to display
            disk = BitmapFactory.decodeResource(getResources(), R.drawable.dvd);
        }

        private void createAnimation(Canvas canvas) {
            //how much to rotate
            spin = new RotateAnimation(0, 360, getWidth()/2, getHeight()/2);
            //how long to rotate
            spin.setRepeatMode(Animation.INFINITE);
            spin.setRepeatCount(Animation.INFINITE);
            //how long one rotation takes
            spin.setDuration(10000L);
            //start rotating
            startAnimation(spin);
        }

        protected void onDraw(Canvas canvas) {
            //starts the animation to rotate the disk
            if (spin == null)
                createAnimation(canvas);
            //position of disk
                canvas.drawBitmap(disk, 112, 350, null);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        //Create the view
        super.onCreate(savedInstanceState);
        setContentView(new SampleView(this));
    }
}