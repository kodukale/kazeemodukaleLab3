package kazeem.odukale.s301021750.ui.home;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import kazeem.odukale.s301021750.IOnBackPressed;
import kazeem.odukale.s301021750.KazeemActivity;
import kazeem.odukale.s301021750.R;

public class KazeemFragment extends Fragment /*implements IOnBackPressed */{

    private KazeemViewModel kazeemViewModel;
    private CanvasView canvasView;
    Color selectedColor;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        kazeemViewModel =
                ViewModelProviders.of(this).get(KazeemViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_home, container, false);
        canvasView = (CanvasView) root.findViewById(R.id.drawing_canvas);
        Button buttonSelection = root.findViewById(R.id.pen_selection);
        buttonSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spinner colorSpinner = root.findViewById(R.id.kaeem_pen_color);
                TextView colorSpinnerText = (TextView) colorSpinner.getSelectedView();
                Spinner thicknessSpinner = root.findViewById(R.id.kaeem_pen_thickness);
                TextView thicknessSpinnerText = (TextView)thicknessSpinner.getSelectedView();
                String thickness = thicknessSpinnerText.getText().toString();
                String color = colorSpinnerText.getText().toString();
                canvasView.getSelectedOptions(color, thickness);
            }
        });

        Button clearButton = root.findViewById(R.id.pen_clear);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                canvasView.clearPath();
            }
        });
        return root;
    }
}