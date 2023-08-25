package com.iosflashscreen.phonecallerid.screencaller.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.iosflashscreen.phonecallerid.screencaller.R;
import com.iosflashscreen.phonecallerid.screencaller.adapter.ImagesAdapter;
import com.iosflashscreen.phonecallerid.screencaller.adapter.LiveVideoAdapter;
import com.iosflashscreen.phonecallerid.screencaller.adapter.VideoAdapter;
import com.iosflashscreen.phonecallerid.screencaller.model.Images;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ThemeCategoryActivity extends AppCompatActivity {
    ImagesAdapter trendingAdapter, kpopAdapter, neonAdapter, loveAdapter, callofdutyAdapter, animeAdapter, soccerAdapter, cutefunnyAdapter, modernAdapter, natureAdapter, animalAdapter, christmasAdapter = null;
    LiveVideoAdapter livetrendingAdapter, livekpopAdapter, liveneonAdapter, liveloveAdapter, livecallofdutyAdapter, liveanimeAdapter, livesoccerAdapter, livecutefunnyAdapter, livemodernAdapter, livenatureAdapter, liveanimalAdapter, livechristmasAdapter = null;
    private RecyclerView recyclerView;
    private Timer fetchTimer;
    private ArrayList<Images> trendingList = new ArrayList<>();
    private ArrayList<Images> livetrendingList = new ArrayList<>();
    private ArrayList<Images> kpoplist = new ArrayList<>();
    private ArrayList<Images> livekpoplist = new ArrayList<>();
    private ArrayList<Images> neonList = new ArrayList<>();
    private ArrayList<Images> liveneonList = new ArrayList<>();
    private ArrayList<Images> loveList = new ArrayList<>();
    private ArrayList<Images> liveloveList = new ArrayList<>();
    private ArrayList<Images> callofdutyList = new ArrayList<>();
    private ArrayList<Images> livecallofdutyList = new ArrayList<>();
    private ArrayList<Images> animeList = new ArrayList<>();
    private ArrayList<Images> liveanimeList = new ArrayList<>();
    private ArrayList<Images> soccerList = new ArrayList<>();
    private ArrayList<Images> livesoccerList = new ArrayList<>();
    private ArrayList<Images> cutefunnyList = new ArrayList<>();
    private ArrayList<Images> livecutefunnyList = new ArrayList<>();
    private ArrayList<Images> modernList = new ArrayList<>();
    private ArrayList<Images> livemodernList = new ArrayList<>();
    private ArrayList<Images> natureList = new ArrayList<>();
    private ArrayList<Images> livenatureList = new ArrayList<>();
    private ArrayList<Images> animalsList = new ArrayList<>();
    private ArrayList<Images> liveanimalsList = new ArrayList<>();
    private ArrayList<Images> christmaslist = new ArrayList<>();
    private ArrayList<Images> livechristmaslist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme_category);
        recyclerView = findViewById(R.id.neon_recycler);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        try {
            InputStream inputStream = getAssets().open("category.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String jsonConfig = new String(buffer, "UTF-8");

            JSONObject jsonObject = new JSONObject(jsonConfig);
            JSONObject categoriesObject = jsonObject.getJSONObject("Categories");

            processCategory(categoriesObject, "Trending", trendingList);
            processCategory(categoriesObject, "LiveTrending", livetrendingList);
            processCategory(categoriesObject, "Christmas", christmaslist);
            processCategory(categoriesObject, "LiveChristmas", livechristmaslist);
            processCategory(categoriesObject, "Kpop", kpoplist);
            processCategory(categoriesObject, "LiveKpop", livekpoplist);
            processCategory(categoriesObject, "Neon", neonList);
            processCategory(categoriesObject, "LiveNeon", liveneonList);
            processCategory(categoriesObject, "Love", loveList);
            processCategory(categoriesObject, "LiveLove", liveloveList);
            processCategory(categoriesObject, "CallOfDuty", callofdutyList);
            processCategory(categoriesObject, "LiveCallOfDuty", livecallofdutyList);
            processCategory(categoriesObject, "Anime", animeList);
            processCategory(categoriesObject, "LiveAnime", liveanimeList);
            processCategory(categoriesObject, "Soccer", soccerList);
            processCategory(categoriesObject, "LiveSoccer", livesoccerList);
            processCategory(categoriesObject, "CuteAndFunny", cutefunnyList);
            processCategory(categoriesObject, "LiveCuteAndFunny", livecutefunnyList);
            processCategory(categoriesObject, "Modern", modernList);
            processCategory(categoriesObject, "LiveModern", livemodernList);
            processCategory(categoriesObject, "Nature", natureList);
            processCategory(categoriesObject, "LiveNature", livenatureList);
            processCategory(categoriesObject, "Animal", animalsList);
            processCategory(categoriesObject, "LiveAnimal", liveanimalsList);

            // Notify adapter of data changes
            ArrayList<Images> categoryIdentifier = getIntent().getParcelableArrayListExtra("categoryIdentifier");
            if (categoryIdentifier != null) {
                for (Images image : categoryIdentifier) {
                    String category1 = image.getUrl();

                    switch (category1) {
                        case "Trending":
                            trendingAdapter = new ImagesAdapter(ThemeCategoryActivity.this, trendingList);
                            recyclerView.setAdapter(trendingAdapter);
                            break;
                        case "LiveTrending":
                            livetrendingAdapter = new LiveVideoAdapter(ThemeCategoryActivity.this, livetrendingList);
                            recyclerView.setAdapter(livetrendingAdapter);
                            break;
                        case "Christmas":
                            christmasAdapter = new ImagesAdapter(ThemeCategoryActivity.this, christmaslist);
                            recyclerView.setAdapter(christmasAdapter);
                            break;
                        case "LiveChristmas":
                            livechristmasAdapter = new LiveVideoAdapter(ThemeCategoryActivity.this, livechristmaslist);
                            recyclerView.setAdapter(livechristmasAdapter);
                            break;
                        case "Kpop":
                            kpopAdapter = new ImagesAdapter(ThemeCategoryActivity.this, kpoplist);
                            recyclerView.setAdapter(kpopAdapter);
                            break;
                        case "LiveKpop":
                            livekpopAdapter = new LiveVideoAdapter(ThemeCategoryActivity.this, livekpoplist);
                            recyclerView.setAdapter(livekpopAdapter);
                            break;
                        case "Neon":
                            neonAdapter = new ImagesAdapter(ThemeCategoryActivity.this, neonList);
                            recyclerView.setAdapter(neonAdapter);
                            break;
                        case "LiveNeon":
                            liveneonAdapter = new LiveVideoAdapter(ThemeCategoryActivity.this, liveneonList);
                            recyclerView.setAdapter(liveneonAdapter);
                            break;
                        case "Love":
                            loveAdapter = new ImagesAdapter(ThemeCategoryActivity.this, loveList);
                            recyclerView.setAdapter(loveAdapter);
                            break;
                        case "LiveLove":
                            liveloveAdapter = new LiveVideoAdapter(ThemeCategoryActivity.this, liveloveList);
                            recyclerView.setAdapter(liveloveAdapter);
                            break;
                        case "CallOfDuty":
                            callofdutyAdapter = new ImagesAdapter(ThemeCategoryActivity.this, callofdutyList);
                            recyclerView.setAdapter(callofdutyAdapter);
                            break;
                        case "LiveCallOfDuty":
                            livecallofdutyAdapter = new LiveVideoAdapter(ThemeCategoryActivity.this, livecallofdutyList);
                            recyclerView.setAdapter(livecallofdutyAdapter);
                            break;
                        case "Anime":
                            animeAdapter = new ImagesAdapter(ThemeCategoryActivity.this, animeList);
                            recyclerView.setAdapter(animeAdapter);
                            break;
                        case "LiveAnime":
                            liveanimeAdapter = new LiveVideoAdapter(ThemeCategoryActivity.this, liveanimeList);
                            recyclerView.setAdapter(liveanimeAdapter);
                            break;
                        case "Soccer":
                            soccerAdapter = new ImagesAdapter(ThemeCategoryActivity.this, soccerList);
                            recyclerView.setAdapter(soccerAdapter);
                            break;
                        case "LiveSoccer":
                            livesoccerAdapter = new LiveVideoAdapter(ThemeCategoryActivity.this, livesoccerList);
                            recyclerView.setAdapter(livesoccerAdapter);
                            break;
                        case "CuteAndFunny":
                            cutefunnyAdapter = new ImagesAdapter(ThemeCategoryActivity.this, cutefunnyList);
                            recyclerView.setAdapter(cutefunnyAdapter);
                            break;
                        case "LiveCuteAndFunny":
                            livecutefunnyAdapter = new LiveVideoAdapter(ThemeCategoryActivity.this, livecutefunnyList);
                            recyclerView.setAdapter(livecutefunnyAdapter);
                            break;
                        case "Modern":
                            modernAdapter = new ImagesAdapter(ThemeCategoryActivity.this, modernList);
                            recyclerView.setAdapter(modernAdapter);
                            break;
                        case "LiveModern":
                            livemodernAdapter = new LiveVideoAdapter(ThemeCategoryActivity.this, livemodernList);
                            recyclerView.setAdapter(livemodernAdapter);
                            break;
                        case "Nature":
                            natureAdapter = new ImagesAdapter(ThemeCategoryActivity.this, natureList);
                            recyclerView.setAdapter(natureAdapter);
                            break;
                        case "LiveNature":
                            livenatureAdapter = new LiveVideoAdapter(ThemeCategoryActivity.this, livenatureList);
                            recyclerView.setAdapter(livenatureAdapter);
                            break;
                        case "Animal":
                            animalAdapter = new ImagesAdapter(ThemeCategoryActivity.this, animalsList);
                            recyclerView.setAdapter(animalAdapter);
                            break;
                        case "LiveAnimal":
                            liveanimalAdapter = new LiveVideoAdapter(ThemeCategoryActivity.this, liveanimalsList);
                            recyclerView.setAdapter(liveanimalAdapter);
                            break;
                    }
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }


        fetchTimer = new Timer();
        fetchTimer.schedule(new TimerTask() {
            @Override
            public void run() {
            }
        }, 0, 1000);
    }


    @Override
    public void onBackPressed() {

        ThemeCategoryActivity.super.onBackPressed();

    }


    private void processCategory(JSONObject categoriesObject, String categoryName, ArrayList<Images> categoryList) throws JSONException {
        if (categoriesObject.has(categoryName)) {
            JSONObject categoryObject = categoriesObject.getJSONObject(categoryName);
            JSONArray urlsArray = categoryObject.getJSONArray("urls");
            categoryList.clear();
            for (int i = 0; i < urlsArray.length(); i++) {
                String url = urlsArray.getString(i);
                Images themes = new Images();
                themes.setUrl(url);
                categoryList.add(themes);
            }
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        if (fetchTimer != null) {
            fetchTimer.cancel();
        }
    }
}