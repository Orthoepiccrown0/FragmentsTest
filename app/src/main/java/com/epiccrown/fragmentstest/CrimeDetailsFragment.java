package com.epiccrown.fragmentstest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class CrimeDetailsFragment extends Fragment {

    protected String titleText;
    protected String descText;
    protected Context mContext;
    public Crime crime;
    public File mPhotoFile;
    public Button date_button;
    public Button time_button;
    public Button suspect_button;
    public Button sendreport_button;
    public ImageButton camera_button;
    public ImageView photo;
    private static final int DATE_REQUEST = 0;
    private static final int TIME_REQUEST = 1;
    private static final int CONTACT_REQUEST = 2;
    private static final int PHOTO_REQUEST = 3;


    public static CrimeDetailsFragment newInstance(UUID uuid) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("key", uuid);

        CrimeDetailsFragment fragment = new CrimeDetailsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_crime_details, container, false);
        getActivity().setTitle("Crime");
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        if (getArguments() != null) {
            UUID uuid = (UUID) getArguments().get("key");
            crime = CrimeLab.get(getActivity()).getCrime(uuid);
            mPhotoFile = CrimeLab.get(getActivity()).getPhotoFile(crime);
            EditText title = v.findViewById(R.id.title_edit_box);
            EditText description = v.findViewById(R.id.details_edit_box);
            date_button = v.findViewById(R.id.date_button);
            time_button = v.findViewById(R.id.time_button);
            suspect_button = v.findViewById(R.id.suspect_button);
            sendreport_button = v.findViewById(R.id.send_report_button);
            camera_button = v.findViewById(R.id.imageButton);
            photo = v.findViewById(R.id.photoPreview_box);
            CheckBox solved = v.findViewById(R.id.solved_details);

            titleText = crime.getmTitle();
            descText = crime.getmDescription();

            updatePhoto();

            title.setText(crime.getmTitle());
            title.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    crime.setmTitle(s.toString());
                    CrimeLab.get(getActivity()).updateCrime(crime);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            description.setText(crime.getmDescription());
            description.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    crime.setmDescription(s.toString());
                    CrimeLab.get(getActivity()).updateCrime(crime);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            SimpleDateFormat formatter = new SimpleDateFormat("EEE d MMM yyyy");
            String date = formatter.format(crime.getDate());

            date_button.setText(date);
            date_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager fm = getFragmentManager();
                    Bundle args = new Bundle();
                    args.putSerializable("date", crime.getDate());
                    DatePickerFragment dialog = new DatePickerFragment();
                    dialog.setArguments(args);
                    dialog.setTargetFragment(CrimeDetailsFragment.this, DATE_REQUEST);
                    dialog.show(fm, "DialogDate");
                }
            });
            formatter = new SimpleDateFormat("HH:mm");
            String time = formatter.format(crime.getDate());
            time_button.setText(time);
            time_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager fm = getFragmentManager();
                    Bundle args = new Bundle();
                    args.putSerializable("date", crime.getDate());
                    TimePickerFragment dialog = new TimePickerFragment();
                    dialog.setArguments(args);
                    dialog.setTargetFragment(CrimeDetailsFragment.this, TIME_REQUEST);
                    dialog.show(fm, "DialogTime");
                }
            });
            solved.setChecked(crime.ismSolved());
            solved.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    crime.setmSolved(isChecked);
                    CrimeLab.get(getActivity()).updateCrime(crime);
                }
            });


            final Intent pickContact = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
            //pickContact.addCategory(Intent.CATEGORY_HOME);
            suspect_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivityForResult(pickContact, CONTACT_REQUEST);
                }
            });

            if (crime.getmSuspect() != null)
                suspect_button.setText(crime.getmSuspect());

            PackageManager manager = getActivity().getPackageManager();
            if (manager.resolveActivity(pickContact, PackageManager.MATCH_DEFAULT_ONLY) == null)
                suspect_button.setEnabled(false);

            sendreport_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent=ShareCompat.IntentBuilder.from(getActivity())
//                            .setType("text/plain")
//                            .setChooserTitle("Send report via")
//                            .createChooserIntent();
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_TEXT, getReport());
                    intent = Intent.createChooser(intent, "Send report via");
                    startActivity(intent);
                }
            });

            final Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (manager.resolveActivity(takePicture, PackageManager.MATCH_DEFAULT_ONLY) == null)
                camera_button.setEnabled(false);
            camera_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uri = Uri.fromFile(mPhotoFile);
                    takePicture.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                    startActivityForResult(takePicture, PHOTO_REQUEST);
                }
            });

            photo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager fm = getFragmentManager();
                    Bundle args = new Bundle();
                    if (!(mPhotoFile == null || !mPhotoFile.exists())) {
                        args.putSerializable("photo", crime);
                        PhotoInspect dialog = new PhotoInspect();
                        dialog.setArguments(args);
                        dialog.show(fm, "PhotoInspectation");
                    }

                }
            });
        }
        return v;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == DATE_REQUEST) {
            Date date = (Date) data.getExtras().get("date");
            SimpleDateFormat formatter = new SimpleDateFormat("EEE d MMM yyyy");

            crime.setDate(date);
            CrimeLab.get(getActivity()).updateCrime(crime);
            String date_string = formatter.format(crime.getDate());
            date_button.setText(date_string);
        } else if (resultCode == Activity.RESULT_OK && requestCode == TIME_REQUEST) {
            Date date = (Date) data.getExtras().get("date");
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");

            crime.setDate(date);
            CrimeLab.get(getActivity()).updateCrime(crime);
            String time = formatter.format(crime.getDate());
            time_button.setText(time);
        } else if (resultCode == Activity.RESULT_OK && requestCode == CONTACT_REQUEST) {
            Uri contactUri = data.getData();
            // Определение полей, значения которых должны быть
            // возвращены запросом.
            String[] queryFields = new String[]{
                    ContactsContract.Contacts.DISPLAY_NAME
            };
            // Выполнение запроса - contactUri здесь выполняет функции
            // условия "where"
            Cursor c = getActivity().getContentResolver()
                    .query(contactUri, queryFields, null, null, null);
            try {
                // Проверка получения результатов
                if (c.getCount() == 0) {
                    return;
                }
                // Извлечение первого столбца данных - имени подозреваемого.
                c.moveToFirst();
                String suspect = c.getString(0);
                crime.setmSuspect(suspect);
                suspect_button.setText(suspect);
            } finally {
                c.close();
            }
            CrimeLab.get(getActivity()).updateCrime(crime);
        } else if (resultCode == Activity.RESULT_OK && requestCode == PHOTO_REQUEST) {
            updatePhoto();
        }
    }


    public String getReport() {
        String dateString;
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm EEE d MMM yyyy");
        dateString = formatter.format(crime.getDate());

        String suspect;
        if (crime.getmSuspect() != null) suspect = crime.getmSuspect();
        else suspect = "no suspect selected";

        String solved;
        if (crime.ismSolved()) solved = "solved";
        else solved = "not solved";
        return "The crime was registered at " + dateString + "\nTitle: " + crime.getmTitle() + "\nDescription: " + crime.getmDescription() +
                "\nSuspect: " + suspect + "\nThe crime is " + solved;
    }


    public void updatePhoto() {
        if (!(mPhotoFile == null || !mPhotoFile.exists())) {
            //photo.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background));
            Bitmap bitmap = PictureUtils.getScaledBitmap(mPhotoFile.getPath(), getActivity());
            photo.setImageBitmap(bitmap);
        }

    }
}
