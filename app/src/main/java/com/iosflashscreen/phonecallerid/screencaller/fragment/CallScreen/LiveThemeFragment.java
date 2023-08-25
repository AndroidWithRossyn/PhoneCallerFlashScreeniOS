package com.iosflashscreen.phonecallerid.screencaller.fragment.CallScreen;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.iosflashscreen.phonecallerid.screencaller.adapter.VideoAdapter;
import com.iosflashscreen.phonecallerid.screencaller.databinding.FragmentLiveThemeBinding;
import com.iosflashscreen.phonecallerid.screencaller.model.Images;
import com.iosflashscreen.phonecallerid.screencaller.ui.ThemeCategoryActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;


public class LiveThemeFragment extends Fragment {
    FragmentLiveThemeBinding binding;
    private VideoAdapter trendingAdapter, kpopAdapter, neonAdapter, loveAdapter, callofdutyAdapter, animeAdapter,soccerAdapter,cutefunnyAdapter,modernAdapter,natureAdapter,animalAdapter,christmasAdapter;
    private ArrayList<Images> categoryIdentifier, kpoplist, neonList, loveList, callofdutyList, animeList, soccerList, cutefunnyList, modernList, natureList, animalsList, christmaslist, trendingList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLiveThemeBinding.inflate(inflater, container, false);
        binding.kpopSee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                categoryIdentifier.add(new Images("LiveKpop"));
                Intent intent = new Intent(getActivity(), ThemeCategoryActivity.class);
                intent.putParcelableArrayListExtra("categoryIdentifier", categoryIdentifier);
                startActivity(intent);


            }
        });
        binding.trendingSee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                categoryIdentifier.add(new Images("LiveTrending"));
                Intent intent = new Intent(getActivity(), ThemeCategoryActivity.class);
                intent.putExtra("categoryIdentifier", categoryIdentifier);
                startActivity(intent);


            }
        });
        binding.christmasSee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                categoryIdentifier.add(new Images("LiveChristmas"));
                Intent intent = new Intent(getActivity(), ThemeCategoryActivity.class);
                intent.putExtra("categoryIdentifier", categoryIdentifier);
                startActivity(intent);


            }
        });
        binding.animalsSee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                categoryIdentifier.add(new Images("LiveAnimal"));
                Intent intent = new Intent(getActivity(), ThemeCategoryActivity.class);
                intent.putExtra("categoryIdentifier", categoryIdentifier);
                startActivity(intent);


            }
        });
        binding.natureSee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                categoryIdentifier.add(new Images("LiveNature"));
                Intent intent = new Intent(getActivity(), ThemeCategoryActivity.class);
                intent.putExtra("categoryIdentifier", categoryIdentifier);
                startActivity(intent);


            }
        });
        binding.modernSee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                categoryIdentifier.add(new Images("LiveModern"));
                Intent intent = new Intent(getActivity(), ThemeCategoryActivity.class);
                intent.putExtra("categoryIdentifier", categoryIdentifier);
                startActivity(intent);


            }
        });
        binding.cutefunnySee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                categoryIdentifier.add(new Images("LiveCuteAndFunny"));
                Intent intent = new Intent(getActivity(), ThemeCategoryActivity.class);
                intent.putExtra("categoryIdentifier", categoryIdentifier);
                startActivity(intent);


            }
        });
        binding.soccerSee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                categoryIdentifier.add(new Images("LiveSoccer"));
                Intent intent = new Intent(getActivity(), ThemeCategoryActivity.class);
                intent.putExtra("categoryIdentifier", categoryIdentifier);
                startActivity(intent);


            }
        });
        binding.neonSee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                categoryIdentifier.add(new Images("LiveNeon"));
                Intent intent = new Intent(getActivity(), ThemeCategoryActivity.class);
                intent.putExtra("categoryIdentifier", categoryIdentifier);
                startActivity(intent);


            }
        });
        binding.loveSee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                categoryIdentifier.add(new Images("LiveLove"));
                Intent intent = new Intent(getActivity(), ThemeCategoryActivity.class);
                intent.putExtra("categoryIdentifier", categoryIdentifier);
                startActivity(intent);


            }
        });
        binding.callOfDutySee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryIdentifier.add(new Images("LiveCallOfDuty"));
                Intent intent = new Intent(getActivity(), ThemeCategoryActivity.class);
                intent.putExtra("categoryIdentifier", categoryIdentifier);
                startActivity(intent);


            }
        });
        binding.animalsSee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryIdentifier.add(new Images("LiveAnime"));
                Intent intent = new Intent(getActivity(), ThemeCategoryActivity.class);
                intent.putExtra("categoryIdentifier", categoryIdentifier);
                startActivity(intent);


            }
        });


        binding.trendingRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.trendingRecycler.setHasFixedSize(true);
        binding.trendingRecycler.setNestedScrollingEnabled(false);


        binding.kpopRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.kpopRecycler.setHasFixedSize(true);
        binding.kpopRecycler.setNestedScrollingEnabled(false);


        binding.neonRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.neonRecycler.setHasFixedSize(true);
        binding.neonRecycler.setNestedScrollingEnabled(false);


        binding.loveRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.loveRecycler.setHasFixedSize(true);
        binding.loveRecycler.setNestedScrollingEnabled(false);


        binding.callOfDutyRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.callOfDutyRecycler.setHasFixedSize(true);
        binding.callOfDutyRecycler.setNestedScrollingEnabled(false);


        binding.animeRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.animeRecycler.setHasFixedSize(true);
        binding.animeRecycler.setNestedScrollingEnabled(false);

        binding.soccerRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.soccerRecycler.setHasFixedSize(true);
        binding.soccerRecycler.setNestedScrollingEnabled(false);

        binding.cutefunnyRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.cutefunnyRecycler.setHasFixedSize(true);
        binding.cutefunnyRecycler.setNestedScrollingEnabled(false);

        binding.modernRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.modernRecycler.setHasFixedSize(true);
        binding.modernRecycler.setNestedScrollingEnabled(false);

        binding.natureRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.natureRecycler.setHasFixedSize(true);
        binding.natureRecycler.setNestedScrollingEnabled(false);

        binding.animalsRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.animalsRecycler.setHasFixedSize(true);
        binding.animalsRecycler.setNestedScrollingEnabled(false);

        binding.christmasRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.christmasRecycler.setHasFixedSize(true);
        binding.christmasRecycler.setNestedScrollingEnabled(false);

        categoryIdentifier = new ArrayList<>();
        trendingList = new ArrayList<>();
        kpoplist = new ArrayList<>();
        neonList = new ArrayList<>();
        loveList = new ArrayList<>();
        callofdutyList = new ArrayList<>();
        animeList = new ArrayList<>();
        soccerList = new ArrayList<>();
        cutefunnyList = new ArrayList<>();
        modernList = new ArrayList<>();
        natureList = new ArrayList<>();
        animalsList = new ArrayList<>();
        christmaslist = new ArrayList<>();


        trendingAdapter = new VideoAdapter(getActivity(), (ArrayList<Images>) trendingList);
        kpopAdapter = new VideoAdapter(requireActivity(), (ArrayList<Images>) kpoplist);
        neonAdapter = new VideoAdapter(requireActivity(), (ArrayList<Images>) neonList);
        loveAdapter = new VideoAdapter(requireActivity(), (ArrayList<Images>) loveList);
        callofdutyAdapter = new VideoAdapter(requireActivity(), (ArrayList<Images>) callofdutyList);
        animeAdapter = new VideoAdapter(requireActivity(), (ArrayList<Images>) animeList);
        soccerAdapter = new VideoAdapter(requireActivity(), (ArrayList<Images>) soccerList);
        cutefunnyAdapter = new VideoAdapter(requireActivity(), (ArrayList<Images>) cutefunnyList);
        modernAdapter = new VideoAdapter(requireActivity(), (ArrayList<Images>) modernList);
        natureAdapter = new VideoAdapter(requireActivity(), (ArrayList<Images>) natureList);
        animalAdapter = new VideoAdapter(requireActivity(), (ArrayList<Images>) animalsList);
        christmasAdapter = new VideoAdapter(requireActivity(), (ArrayList<Images>) christmaslist);


        binding.trendingRecycler.setAdapter(trendingAdapter);
        binding.kpopRecycler.setAdapter(kpopAdapter);
        binding.neonRecycler.setAdapter(neonAdapter);
        binding.loveRecycler.setAdapter(loveAdapter);
        binding.callOfDutyRecycler.setAdapter(callofdutyAdapter);
        binding.animeRecycler.setAdapter(animeAdapter);
        binding.soccerRecycler.setAdapter(soccerAdapter);
        binding.cutefunnyRecycler.setAdapter(cutefunnyAdapter);
        binding.modernRecycler.setAdapter(modernAdapter);
        binding.natureRecycler.setAdapter(natureAdapter);
        binding.animalsRecycler.setAdapter(animalAdapter);
        binding.christmasRecycler.setAdapter(christmasAdapter);
        try {
            InputStream inputStream = requireActivity().getAssets().open("category.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String jsonConfig = new String(buffer, StandardCharsets.UTF_8);

            JSONObject jsonObject = new JSONObject(jsonConfig);
            JSONObject categoriesObject = jsonObject.getJSONObject("Categories");

            processCategory(categoriesObject, "LiveTrending", trendingList);
            processCategory(categoriesObject, "LiveChristmas", christmaslist);
            processCategory(categoriesObject, "LiveKpop", kpoplist);
            processCategory(categoriesObject, "LiveNeon", neonList);
            processCategory(categoriesObject, "LiveLove", loveList);
            processCategory(categoriesObject, "LiveCallOfDuty", callofdutyList);
            processCategory(categoriesObject, "LiveAnime", animeList);
            processCategory(categoriesObject, "LiveSoccer", soccerList);
            processCategory(categoriesObject, "LiveCuteAndFunny", cutefunnyList);
            processCategory(categoriesObject, "LiveModern", modernList);
            processCategory(categoriesObject, "LiveNature", natureList);
            processCategory(categoriesObject, "LiveAnimal", animalsList);

            trendingAdapter.notifyDataSetChanged();
            christmasAdapter.notifyDataSetChanged();
            kpopAdapter.notifyDataSetChanged();
            neonAdapter.notifyDataSetChanged();
            loveAdapter.notifyDataSetChanged();
            callofdutyAdapter.notifyDataSetChanged();
            animalAdapter.notifyDataSetChanged();
            animeAdapter.notifyDataSetChanged();
            soccerAdapter.notifyDataSetChanged();
            cutefunnyAdapter.notifyDataSetChanged();
            natureAdapter.notifyDataSetChanged();
            modernAdapter.notifyDataSetChanged();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return binding.getRoot();
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

}