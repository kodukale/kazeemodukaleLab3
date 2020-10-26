package kazeem.odukale.s301021750.ui.notifications;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.view.View;

import kazeem.odukale.s301021750.R;

public class ViewWithBitmap extends View {

    // default constructor
    public ViewWithBitmap(Context context) {
        super(context);

    }

    @Override protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLUE);
        Bitmap firstnameBitmap = BitmapFactory.decodeResource(getResources(), R.string.firstnmae);
        Bitmap lastnameBitmap = BitmapFactory.decodeResource(getResources(), R.string.lastname);
        Matrix topLeft = new Matrix();
        topLeft.preRotate(45);
        Matrix topRight = new Matrix();
        topRight.preRotate(-45);

        Bitmap firstnameTopLeft = Bitmap.createBitmap(firstnameBitmap, 0, 0, firstnameBitmap.getWidth(), firstnameBitmap.getHeight(), topLeft, false);
        Bitmap lastnameTopRight = Bitmap.createBitmap(lastnameBitmap, 0,0, lastnameBitmap.getWidth(), lastnameBitmap.getHeight(), topRight, false);


        // Free up some memory by dumping bitmaps we don't need anymore
        firstnameBitmap.recycle();
        lastnameBitmap.recycle();
        //draw the bitmap in the canvas
        canvas.drawBitmap(firstnameTopLeft, 30, 30, null);
        canvas.drawBitmap(lastnameTopRight, 1025, 30, null);
    }
}
