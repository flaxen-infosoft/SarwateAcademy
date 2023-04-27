package com.flaxeninfosoft.sarwateAcademy.views.userFragments.mycourse;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.flaxeninfosoft.sarwateAcademy.R;
import com.flaxeninfosoft.sarwateAcademy.api.ApiEndpoints;
import com.flaxeninfosoft.sarwateAcademy.databinding.FragmentNotesBinding;
import com.flaxeninfosoft.sarwateAcademy.models.StudyMaterial;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.net.URL;

import io.paperdb.Paper;


public class NotesFragment extends Fragment {


    public NotesFragment() {
        // Required empty public constructor
    }


    FragmentNotesBinding binding;
    StudyMaterial studyMaterial;
    public static final String TAG = "SHOW-STUDY-MATERIAL-LOG";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_notes, container, false);
        studyMaterial = Paper.book().read("Current_StudyMaterial");
        binding.downloadNotesButton.setOnClickListener(this::onClickDownload);
        Log.i(TAG, studyMaterial.getFilePath());
        showFile(studyMaterial.getFilePath());

        return binding.getRoot();
    }

    private void showFile(String url) {

        if (url == null) {
            Navigation.findNavController(binding.getRoot()).navigateUp();
        } else {

            if (getExtension(url).equals(".pdf")) {

            } else if (getExtension(url).equals(".png")) {
                Picasso.get().load(ApiEndpoints.BASE_URL + url).placeholder(R.drawable.sarwate_logo).into(binding.imageViewImage);
            } else if (getExtension(url).equals(".jpeg")) {
                Picasso.get().load(ApiEndpoints.BASE_URL + url).placeholder(R.drawable.sarwate_logo).into(binding.imageViewImage);
            } else if (getExtension(url).equals(".jpg")) {
                Picasso.get().load(ApiEndpoints.BASE_URL + url).placeholder(R.drawable.sarwate_logo).into(binding.imageViewImage);
            } else {
                Toast.makeText(getContext(), "File Not Found.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void onClickDownload(View view) {
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
        } else if (getExtension(url).equals(".jpg")) {
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName + ".jpg"); // Save the file to the Downloads folder with the name "filename.pdf"
        } else {
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
}