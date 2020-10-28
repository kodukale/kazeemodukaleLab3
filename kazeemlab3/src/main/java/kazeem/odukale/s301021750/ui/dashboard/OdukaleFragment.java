package kazeem.odukale.s301021750.ui.dashboard;

import android.Manifest;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.OperationApplicationException;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;

import kazeem.odukale.s301021750.R;

public class OdukaleFragment extends Fragment {

    private OdukaleViewModel dashboardViewModel;
    int reasonableDuration;
    AnimationDrawable mframeAnimation;
    ImageView img;
    private static final String TAG = "Contacts";
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    //private final String LOG = getContext().getSimpleName();


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

        // implement permission
        Button buttonPermission = root.findViewById(R.id.kazeem_btn_permission);
        buttonPermission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertDummyContactWrapper();
                //insertDummyContact();
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

    private void insertDummyContactWrapper() {
        int hasWriteContactsPermission = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            hasWriteContactsPermission = getContext().checkSelfPermission(Manifest.permission.WRITE_CONTACTS);

            if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_CONTACTS},
                        REQUEST_CODE_ASK_PERMISSIONS);
                return;
            }
        }
        insertDummyContact();
    }

    private void insertDummyContact() {
        // Two operations are needed to insert a new contact.
        ArrayList<ContentProviderOperation> operations = new ArrayList<ContentProviderOperation>(2);

        // First, set up a new raw contact.
        ContentProviderOperation.Builder op =
                ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                        .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                        .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null);
        operations.add(op.build());

        // Next, set the name for the contact.
        op = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
                        "__DUMMY CONTACT from runtime permissions sample");
        operations.add(op.build());

        // Apply the operations.
        ContentResolver resolver = getContext().getContentResolver();
        try {
            resolver.applyBatch(ContactsContract.AUTHORITY, operations);
            Toast.makeText(getContext(), "New contact inserted!", Toast.LENGTH_SHORT)
                    .show();
        } catch (RemoteException e) {
            Log.d(TAG, "Could not add a new contact: " + e.getMessage());
        } catch (OperationApplicationException e) {
            Log.d(TAG, "Could not add a new contact: " + e.getMessage());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    Toast.makeText(getContext(), "WRITE_CONTACTS allowed", Toast.LENGTH_SHORT)
                            .show();
                    insertDummyContact();
                } else {
                    // Permission Denied
                    Toast.makeText(getContext(), "WRITE_CONTACTS Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(getContext())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
}