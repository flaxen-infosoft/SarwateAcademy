package com.flaxeninfosoft.sarwateAcademy.views.teacherFragments;

import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.flaxeninfosoft.sarwateAcademy.R;
import com.flaxeninfosoft.sarwateAcademy.databinding.FragmentStudyMaterialBinding;
import com.flaxeninfosoft.sarwateAcademy.databinding.FragmentStudyMaterialDetailsBinding;
import com.flaxeninfosoft.sarwateAcademy.models.StudyMaterial;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Path;

import io.paperdb.Paper;


public class StudyMaterialDetailsFragment extends Fragment {


    public StudyMaterialDetailsFragment() {
        // Required empty public constructor
    }


    FragmentStudyMaterialDetailsBinding binding;
    StudyMaterial studyMaterial;

    public static final String TAG = "STUDY-MATERIAL-DETAIL-LOG";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_study_material_details, container, false);
        studyMaterial = Paper.book().read("Current_StudyMaterial");
        binding.setStudymaterial(studyMaterial);
        binding.editFloatButton.setOnClickListener(this::onClickEdit);
        binding.playVideoButton.setOnClickListener(this::onClickOpenNotes);
        binding.backImageView.setOnClickListener(this::onClickBack);
        binding.downloadNotesButton.setOnClickListener(this::onClickDownloadNote);


        return binding.getRoot();
    }

    private void onClickDownloadNote(View view) {
        downloadFile();
    }

    private void downloadFile() {
        Toast.makeText(getContext(), "Downloading please wait.", Toast.LENGTH_SHORT).show();
        String url = "http://103.118.17.202/~mkeducation/MK_API/User/" + studyMaterial.getFilePath(); // Replace this with the URL of the file
        String folderName = "SarwateAcademy"; // Replace this with the desired name of the new folder
        String fileName = studyMaterial.getFileName(); // Replace this with the desired name of the downloaded file
        File folder = new File(Environment.getExternalStorageDirectory() + "/" + folderName); // Create a new directory in the external storage
        if (!folder.exists()) {
            folder.mkdir();
        }
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setTitle(studyMaterial.getFileName()); // Set the title of the download notification
        request.setDescription("Downloading"); // Set the description of the download notification
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED); // Show a notification when the download is complete
        if (getExtension(url).equals(".pdf")) {
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName + ".pdf"); // Save the file to the Downloads folder with the name "filename.pdf"
        } else if (getExtension(url).equals(".png")) {
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName + ".png"); // Save the file to the Downloads folder with the name "filename.pdf"
        } else if (getExtension(url).equals(".jpeg")) {
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName + ".jpeg"); // Save the file to the Downloads folder with the name "filename.pdf"
        } else if (getExtension(url).equals(".txt")) {
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName + ".txt"); // Save the file to the Downloads folder with the name "filename.pdf"
        }
        else {
            Toast.makeText(getContext(), "File Not Found.", Toast.LENGTH_SHORT).show();
        }
        DownloadManager manager = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);

    }

    public String getExtension(String urlString) {
        String fileExtension = "";
        try {
            URL url1 = new URL(urlString);
            String fileName = url1.getFile();
            fileExtension = fileName.substring(fileName.lastIndexOf('.') + 1);
            System.out.println("File extension: " + fileExtension);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileExtension;
    }

    private void onClickOpenNotes(View view) {
//        Navigation.findNavController(view).navigate(R.id.action_studyMaterialDetailsFragment_to_notesOverviewFragment);
        String url = "http://103.118.17.202/~mkeducation/MK_API/User/" + studyMaterial.getFilePath();
        Paper.book().write("StudyMaterialUrl",url);
        openFile(url,getMimeType(url));
    }

    public void openFile(String fileUrl, String mimeType) {
        Toast.makeText(getContext(), "Please Wait File is opening.", Toast.LENGTH_SHORT).show();
        try {
            // Create a new Intent to view the file
            Intent intent = new Intent(Intent.ACTION_VIEW);
            // Set the data and MIME type of the file
            intent.setDataAndType(Uri.parse(fileUrl), mimeType);
            // Set the flags to grant read permission to other apps
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Try to start the intent
            startActivity(intent);
        } catch (Exception e) {
            // Handle any exceptions here
            e.printStackTrace();
        }
    }

    public String getMimeType(String url) {
        String type = null;
        try {
            URL u = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();
            conn.setRequestMethod("HEAD");
            type = conn.getContentType();
        } catch (Exception e) {
            // Handle any exceptions here
            e.printStackTrace();
        }
        return type;
    }


    private void onClickBack(View view) {
        Navigation.findNavController(view).navigateUp();
    }

    private void onClickEdit(View view) {
        Navigation.findNavController(view).navigate(R.id.action_studyMaterialDetailsFragment_to_updateStudyMaterialFragment2);
    }
}