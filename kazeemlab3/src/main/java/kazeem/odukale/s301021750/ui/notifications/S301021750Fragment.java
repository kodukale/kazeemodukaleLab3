package kazeem.odukale.s301021750.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import kazeem.odukale.s301021750.R;

public class S301021750Fragment extends Fragment {

    private S301021750ViewModel s301021750ViewModel;
    private ImageView imageMoon;
    private Animation an;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        s301021750ViewModel =
                ViewModelProviders.of(this).get(S301021750ViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        Button start = root.findViewById(R.id.btnStartAnim);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageMoon = root.findViewById(R.id.moon);
                // Load the appropriate animation
                an =  AnimationUtils.loadAnimation(getContext(), R.anim.set);
                // Start the animation
                imageMoon.startAnimation(an);
            }
        });

        Button stop = root.findViewById(R.id.btnStopAnim);
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageMoon.clearAnimation();
            }
        });

        return root;
    }
}