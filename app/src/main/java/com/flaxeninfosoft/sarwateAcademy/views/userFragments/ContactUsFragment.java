package com.flaxeninfosoft.sarwateAcademy.views.userFragments;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.flaxeninfosoft.sarwateAcademy.R;
import com.flaxeninfosoft.sarwateAcademy.databinding.FragmentContactUsBinding;


public class ContactUsFragment extends Fragment {

    public ContactUsFragment() {
        // Required empty public constructor
    }



   FragmentContactUsBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_contact_us, container, false);


        binding.callUsImageView.setOnClickListener(this::onClickCall);
        binding.facebookCardView.setOnClickListener(this::onClickFaceBook);
        binding.twitterCardView.setOnClickListener(this::onClickTwitter);
        binding.googlePcardView.setOnClickListener(this::onClickGooglePlus);
        binding.instagramCardView.setOnClickListener(this::onClickInstagram);
        binding.linkedInCardView.setOnClickListener(this::onClickLinkedIn);
        binding.youtubeCardView.setOnClickListener(this::onClickYoutube);
        binding.emailTextView.setOnClickListener(this::onClickEmail);
        binding.backImageView.setOnClickListener(this::onClickBack);


        return binding.getRoot();
    }

    private void onClickBack(View view) {
        Navigation.findNavController(view).navigateUp();
    }

    private void onClickEmail(View view) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"sarwateacademy40@gmail.com"});
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getActivity(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    private void onClickYoutube(View view) {
        Intent intent=null;
        try {
            intent =new Intent(Intent.ACTION_VIEW);
            intent.setPackage("com.google.android.youtube");
            intent.setData(Uri.parse("https://www.youtube.com/"));
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://www.youtube.com/"));
            startActivity(intent);
        }
    }

    private void onClickLinkedIn(View view) {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/")));
        }
        catch (ActivityNotFoundException e){
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/")));
        }
    }

    private void onClickInstagram(View view) {
        Uri uri = Uri.parse("http://instagram.com/");
        Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

        likeIng.setPackage("com.instagram.android");

        try {
            startActivity(likeIng);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://instagram.com/")));
        }
    }

    private void onClickGooglePlus(View view) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setClassName("com.google.android.apps.plus",
                    "com.google.android.apps.plus.phone.UrlGatewayActivity");
            intent.putExtra("customAppUri", "name");
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://plus.google.com/" + "name" + "/posts")));
        }
    }

    private void onClickTwitter(View view) {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=" + "name")));
        }catch (Exception e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/#!/" + "name")));
        }
    }

    private void onClickFaceBook(View view) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/"));
            startActivity(intent);
        } catch(Exception e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com/")));
        }
    }

    private void onClickCall(View view) {
        Uri uri = Uri.parse("tel:" + "9009214540");
        Intent intent = new Intent(Intent.ACTION_DIAL, uri);
        startActivity(intent);
    }
}