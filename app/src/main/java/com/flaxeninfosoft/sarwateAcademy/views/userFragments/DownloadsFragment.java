package com.flaxeninfosoft.sarwateAcademy.views.userFragments;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flaxeninfosoft.sarwateAcademy.R;
import com.flaxeninfosoft.sarwateAcademy.adapters.DownloadViewPagerAdapter;
import com.flaxeninfosoft.sarwateAcademy.databinding.FragmentDownloadsBinding;

import javax.xml.namespace.NamespaceContext;

public class DownloadsFragment extends Fragment {
    //TODO
    public DownloadsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    FragmentDownloadsBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_downloads, container, false);


        FragmentManager fragmentManager = getChildFragmentManager();
       FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        DownloadViewPagerAdapter viewPagerAdapter = new DownloadViewPagerAdapter(fragmentManager);

        fragmentTransaction.add(new DownloadedVideoFragment(),"Video");
        fragmentTransaction.add(new DownloadedDocumentFragment(),"Notes");

        fragmentTransaction.commit();
        binding.viewPager.setAdapter(viewPagerAdapter);
        binding.tabLayout.setupWithViewPager(binding.viewPager);
        binding.backImageView.setOnClickListener(this::onClickBack);




        return binding.getRoot();
    }

    private void onClickBack(View view) {
        Navigation.findNavController(view).navigateUp();
    }
}