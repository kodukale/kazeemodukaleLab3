package kazeem.odukale.s301021750.ui.dashboard;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import kazeem.odukale.s301021750.R;

public class OdukaleFragment extends Fragment {

    private OdukaleViewModel dashboardViewModel;
    int reasonableDuration;
    AnimationDrawable mframeAnimation;
    ImageView img;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(OdukaleViewModel.class);
         final View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        img = (ImageView) root.findViewById(R.id.ImageView_Gym);
        Button start = root.findViewById(R.id.ButtonStart);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spinner speedSpinner = root.findViewById(R.id.kazeem_frame_speed);
                TextView speedSpinnerText = (TextView) speedSpinner.getSelectedView();
                String text = speedSpinnerText.getText().toString();
                switch (text) {
                    case "0.2 Sec" : reasonableDuration = 200;
                    break;
                    case "0.25 Sec" : reasonableDuration = 250;
                    break;
                    case "0.3 Sec" : reasonableDuration = 300;
                    break;
                    case "0.35 Sec" : reasonableDuration = 350;
                    break;
                    default:break;
                }
                startAnimation();
            }
        });
        // implement stop button
        Button stop = root.findViewById(R.id.ButtonStop);
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopAnimation();
            }
        });
        return root;
    }

    //

    private void startAnimation()
    {

        BitmapDrawable frame1 = (BitmapDrawable)getResources().getDrawable(R.drawable.gym1);
        BitmapDrawable frame2 = (BitmapDrawable)getResources().getDrawable(R.drawable.gym2);
        BitmapDrawable frame3 = (BitmapDrawable)getResources().getDrawable(R.drawable.gym3);
        BitmapDrawable frame4 = (BitmapDrawable)getResources().getDrawable(R.drawable.gym4);
        BitmapDrawable frame5 = (BitmapDrawable)getResources().getDrawable(R.drawable.gym5);
        BitmapDrawable frame6 = (BitmapDrawable)getResources().getDrawable(R.drawable.gym6);

        // Get the background, which has been compiled to an AnimationDrawable object.
        mframeAnimation = new AnimationDrawable();
        mframeAnimation.setOneShot(false);	// loop continuously
        mframeAnimation.addFrame(frame1, reasonableDuration);
        mframeAnimation.addFrame(frame2, reasonableDuration);
        mframeAnimation.addFrame(frame3, reasonableDuration);
        mframeAnimation.addFrame(frame4, reasonableDuration);
        mframeAnimation.addFrame(frame5, reasonableDuration);
        mframeAnimation.addFrame(frame6, reasonableDuration);
        mframeAnimation.addFrame(frame6, reasonableDuration);
        mframeAnimation.addFrame(frame6, reasonableDuration);

        img.setBackground(mframeAnimation);

        mframeAnimation.setVisible(true,true);
        mframeAnimation.start();
    }
    private void stopAnimation()
    {
        mframeAnimation.stop();
        mframeAnimation.setVisible(false,false);
    }
}