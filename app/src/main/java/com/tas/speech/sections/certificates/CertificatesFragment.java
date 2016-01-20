package com.tas.speech.sections.certificates;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;

import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;

import com.tas.speech.R;
import com.tas.speech.sections.certificates.client.CertificatesApi;
import com.tas.speech.client.ApiTypes;
import com.tas.speech.SpeechApp;
import com.tas.speech.sections.certificates.models.Certificate;
import com.tas.speech.sections.certificates.models.CertificatesNavTreeResponse;
import com.tas.speech.sections.topic.TopicFragment;
import com.tas.speech.sections.main.MainActivity;

import java.util.ArrayList;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class CertificatesFragment extends Fragment {

    private static final String TAG = "CertificatesFragment";

    // ExpandableListView containing certificates
    private ExpandableListView elvCertificates;
    private ProgressBar pbLoading;

    // Certificates ExpandableListView associated adapter
    private CertificatesAdapter certificatesAdapter;

    // Certificates Navigation Tree service callback method
    private Callback<CertificatesNavTreeResponse> certNavTreeCallback;

    private ArrayList<Certificate> certificates;

    public CertificatesFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_certificates, container, false);
        ((MainActivity)getActivity()).changeTitle(getResources().getString(R.string.certificates_title));

        elvCertificates = (ExpandableListView)view.findViewById(R.id.elv_certificates);

        pbLoading = (ProgressBar)view.findViewById(R.id.pb_loading);

        certNavTreeCallback = new Callback<CertificatesNavTreeResponse>() {

            @Override
            public void success(CertificatesNavTreeResponse certNavResponse, Response response) {

                Log.d(TAG, "SUCCESS > certNavResponse.getMessage(): " + certNavResponse.getMessage());
                Log.d(TAG, "SUCCESS > certNavResponse.getStatusCode(): " + certNavResponse.getStatusCode());
                Log.d(TAG, "SUCCESS > certNavResponse.getData().getCertificates().size(): " + certNavResponse.getData().getCertificates().size());

                int statusCode;
                if(certNavResponse.getStatusCode() != null) {
                    statusCode = Integer.parseInt(certNavResponse.getStatusCode());

                    if(statusCode == 0) {
                        certificates = certNavResponse.getData().getCertificates();

                        certificatesAdapter = new CertificatesAdapter(getActivity(), certificates);
                        elvCertificates.setAdapter(certificatesAdapter);
                    }
                }
                // Hiding the loading indicator
                pbLoading.setVisibility(View.GONE);
            }

            @Override
            public void failure(RetrofitError error) {

            }
        };

        Log.d(TAG, "Request certificatesNavigationTree");

        // Displaying the loading animation
        pbLoading.setVisibility(View.VISIBLE);

        // Calling the Certificates Service
        ((CertificatesApi)SpeechApp.getApiOfType(ApiTypes.CERTIFICATES_API))
                .certificatesNavigationTree(certNavTreeCallback);

        elvCertificates.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {

            }
        });

        elvCertificates.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                String certName, certId, certSubcategId;

                if (certificates.get(groupPosition).getSubcategories().isEmpty()) {
                    certName = certificates.get(groupPosition).getName();
                    certId = certificates.get(groupPosition).getId();
                    certSubcategId = "";
                } else {
                    certName = certificates.get(groupPosition).getSubcategories().get(childPosition).getName();
                    certId = certificates.get(groupPosition).getId();
                    certSubcategId = certificates.get(groupPosition).getSubcategories().get(childPosition).getId();
                }

                openTopicFragment(certName, certId, certSubcategId);

                return false;
            }
        });

        elvCertificates.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                if(certificates.get(groupPosition).getSubcategories().isEmpty()) {
                    openTopicFragment(certificates.get(groupPosition).getName(), certificates.get(groupPosition).getId(), "");
                }

                return false;
            }
        });

        return view;
    }

    // Method that attaches Topic Fragment to the Main Activity's fragment container
    private void openTopicFragment(String certificateName, String certificateId, String certificateSubcategoryId) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        TopicFragment topicFragment = TopicFragment.newInstance(certificateName, certificateId, certificateSubcategoryId);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(((MainActivity) getActivity()).getFragmentContainerId(), topicFragment);

        // Adding fragment to back stack in order to allow the user to return to it on back button
        // pressed
        fragmentTransaction.addToBackStack(null);

        fragmentTransaction.commit();
    }

}
