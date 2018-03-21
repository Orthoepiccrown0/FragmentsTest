package com.epiccrown.fragmentstest;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import java.io.File;

/**
 * Created by Epiccrown on 21.03.2018.
 */

public class PhotoInspect extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.photo_inspectation,null);
        Crime crime = (Crime)getArguments().get("photo");
        File mPhotoFile = CrimeLab.get(getActivity()).getPhotoFile(crime);
        Bitmap bitmap = PictureUtils.getScaledBitmap(mPhotoFile.getPath(), getActivity());

        ImageView image = v.findViewById(R.id.photo_inspectation_box);
        image.setImageBitmap(bitmap);
        return new AlertDialog.Builder(getActivity())
                .setTitle("Photo inspectation")
                .setView(v)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNeutralButton("DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create();

    }
}
