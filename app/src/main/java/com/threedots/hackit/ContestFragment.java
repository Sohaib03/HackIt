package com.threedots.hackit;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class ContestFragment extends Fragment {
    RecyclerView recyclerView;
    ContestAdapter contestAdapter;
    ProgressBar progressBar;
    ArrayList<Contest> contestList = new ArrayList<>();
    Context context;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contest, container, false);

        recyclerView = view.findViewById(R.id.contest_recycler_view);
        progressBar = view.findViewById(R.id.contest_progress_bar);
        context = view.getContext();

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        contestAdapter = new ContestAdapter(context, contestList);
        recyclerView.setAdapter(contestAdapter);

        getContent();

        return view;
    }

    public void getContent() {
        progressBar.setVisibility(View.VISIBLE);
        progressBar.startAnimation(AnimationUtils.loadAnimation(context, android.R.anim.fade_in));


        OkHttpClient client = new OkHttpClient();
        String url = " https://codeforces.com/api/contest.list";

        final Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String myRes = response.body().string();
                    try {
                        JSONObject object = new JSONObject(myRes);
                        JSONArray results = object.getJSONArray("result");
                        for (int i=0; i<results.length(); i++) {
                            JSONObject contestData = results.getJSONObject(i);
                            //Log.i("TAG", "onResponse: " + contestData.getString("phase"));
                            String phase =  contestData.getString("phase");
                            if (phase.charAt(0) == 'F')
                                break;

                            String name = contestData.getString("name");
                            long time = contestData.getLong("startTimeSeconds");
                            Date d = new Date(time * 1000);
                            DateFormat df = new SimpleDateFormat("dd MMM hh:mm zzz");

                            Log.i("TAG", "onResponse: " + name + " " +  d.toString());
                            Contest contest = new Contest(name,df.format(d).toString(), " ", " ");
                            contestList.add(contest);
                        }
                        Collections.reverse(contestList);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setVisibility(View.GONE);
                                progressBar.startAnimation(AnimationUtils.loadAnimation(context, android.R.anim.fade_out));
                                contestAdapter.notifyDataSetChanged();
                            }
                        });


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }

    private class Content extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            progressBar.startAnimation(AnimationUtils.loadAnimation(context, android.R.anim.fade_in));
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressBar.setVisibility(View.GONE);
            progressBar.startAnimation(AnimationUtils.loadAnimation(context, android.R.anim.fade_out));
            contestAdapter.notifyDataSetChanged();
            Log.i("TAG", "onPostExecute: " + "Data changed");
            for (Contest i: contestList) {
                Log.i("TAG", "onPostExecute: " + i.title);
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected Void doInBackground(Void... voids) {



            return null;
        }
    }
}