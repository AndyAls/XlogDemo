package com.kcode.bottommenututorial;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.kcode.bottomlib.BottomDialog;
import com.kcode.bottommenututorial.bottomsheet.BottomSheetActivity;

/**
 * Created by caik on 2016/9/25.
 */

public class MainFragment extends Fragment {

    private static final String TAG = "MainFragment";

    public static MainFragment newInstance() {
        
        Bundle args = new Bundle();
        
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Button showDialogFragment = (Button) view.findViewById(R.id.showDialogFragment);
        showDialogFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogFragment();
            }
        });

        Button showBottomSheet = (Button) view.findViewById(R.id.showBottomSheet);
        showBottomSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheet();
            }
        });
    }

    public void showDialogFragment(){
        Log.i(TAG,"showDialogFragment");

        BottomDialog dialog = BottomDialog.newInstance("导航到这里去",new String[]{"高德地图","百度地图"});

        /**
         *
         * BottomDialog dialog = BottomDialog.newInstance("titleText","cancelText",new String[]{"item1","item2"});
         *
         * use public static BottomDialog newInstance(String titleText,String cancelText, String[] items)
         * set cancel text
         */

        dialog.show(getChildFragmentManager(),"dialog");
        dialog.setListener(new BottomDialog.OnClickListener() {
            @Override
            public void click(int position) {
                Toast.makeText(getContext(), "" + position, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void showBottomSheet() {
        Log.i(TAG,"showBottomSheet");
        startActivity(BottomSheetActivity.newIntent(getActivity()));
    }

}
