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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.annotation.Documented;
import java.util.ArrayList;


public class HackathonFragment extends Fragment {

    RecyclerView recyclerView;
    HackathonAdapter hackathonAdapter;
    ProgressBar progressBar;
    public ArrayList<Hackathon> hackathonArrayList = new ArrayList<>();
    Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_hackathon, container, false);

        recyclerView = view.findViewById(R.id.hackathon_recyclerview);
        progressBar = view.findViewById(R.id.hackathon_progress_bar);
        context = view.getContext();

        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        hackathonAdapter = new HackathonAdapter(hackathonArrayList, context);
        recyclerView.setAdapter(hackathonAdapter);

        Content content = new Content();
        content.execute();

        return view;
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
            hackathonAdapter.notifyDataSetChanged();
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {

                String url = "https://mlh.io/seasons/2021/events";
                Document doc = Jsoup.connect(url).get();

                Elements data = doc.select("div.event-wrapper");

                int size = data.size();
                for (int i=0; i<size; i++) {
                    String imgUrl = data.select("div.image-wrap")
                            .select("img")
                            .eq(i)
                            .attr("src");

                    String title = data.select("h3.event-name")
                            .eq(i)
                            .text();

                    String date = data.select("p.event-date")
                            .eq(i)
                            .text();

                    String linkurl = data.select("a.event-link")
                            .attr("href");
                    String logo = data.select("div.event-logo")
                            .select("img")
                            .eq(i)
                            .attr("src");
                    Hackathon hackathon = new Hackathon(title, imgUrl, date, linkurl, logo);
                    hackathonArrayList.add(hackathon);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}