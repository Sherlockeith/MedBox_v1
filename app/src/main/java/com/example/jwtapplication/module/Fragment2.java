package com.example.jwtapplication.module;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.alibaba.fastjson.JSON;
import com.example.jwtapplication.R;
import com.example.jwtapplication.dao.Data;
import com.example.jwtapplication.dao.DataDao;
import com.example.jwtapplication.dao.LoginDao;
import com.example.jwtapplication.dao.Token;
import com.example.jwtapplication.util.OkHttpUtils;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment2 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    TextView button;
    OkHttpUtils okHttpUtils = new OkHttpUtils();
    ProgressDialog progressDialog;
    String ResponseString;
    DataDao loginDao;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment2.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment2 newInstance(String param1, String param2) {
        Fragment2 fragment = new Fragment2();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Requesting...");


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_2, container, false);

        button = view.findViewById(R.id.bt_test);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new NetWork().execute();
            }
        });
        // Inflate the layout for this fragment
        return view;
    }
    class NetWork extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();

        }

        @Override
        protected Void doInBackground(Void... voids) {

            HashMap map = new HashMap<>();
            map.put("token", Token.getToken().getKey());
            map.put("msg", "token test!!!");

            try {
                ResponseString = okHttpUtils.PostResult("/testJWT", map);
                loginDao = JSON.parseObject(ResponseString, DataDao.class);
            } catch (Exception e) {

            }

            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            if (loginDao.success.equals("1")) {
                new AlertDialog.Builder(getContext()).setMessage("Test Success!")
                        .show();
            } else {
                new AlertDialog.Builder(getContext()).setMessage("Test Failed!")
                        .show();
            }
            progressDialog.dismiss();

        }
    }
}