package com.nts.youtubemusic.ui.main.search;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;

import com.nts.youtubemusic.R;
import com.nts.youtubemusic.common.Constant;
import com.nts.youtubemusic.common.MessageEvent;
import com.nts.youtubemusic.data.model.table.Recent;
import com.nts.youtubemusic.databinding.FragmentSearchBinding;
import com.nts.youtubemusic.ui.adapter.HomeAdapter;
import com.nts.youtubemusic.ui.adapter.RecentAdapter;
import com.nts.youtubemusic.ui.base.BaseBindingFragment;
import com.nts.youtubemusic.ui.main.MainActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import timber.log.Timber;

public class SearchFragment extends BaseBindingFragment<FragmentSearchBinding, SearchViewModel> {
    public String querySave;
    ActivityResultLauncher<Intent> launcherVoice = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        ArrayList<String> results = data.getStringArrayListExtra(
                                RecognizerIntent.EXTRA_RESULTS);
                        binding.search.setQuery(results.get(0), true);
                    }
                }
            });
    private EditText searchText;
    private HomeAdapter searchAdapter;
    private RecentAdapter searchTitleAdapter;
    private RecentAdapter recentAdapter;
    private List<Recent> recentList = new ArrayList<>();

    @Override
    protected Class<SearchViewModel> getViewModel() {
        return SearchViewModel.class;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_search;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Constant.SAVE_QUERY, querySave);
    }


    @Override
    protected void onCreatedView(View view, Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            querySave = savedInstanceState.getString(Constant.SAVE_QUERY);
            if (querySave != null) {
                binding.rvTitleSearch.setVisibility(View.GONE);
                showSearch(querySave);
            }
        }
        initView();
        observerData();
    }


    private void initView() {
        ((MainActivity) requireActivity()).setTextTitle(binding.include.txtTitleMain, requireContext().getString(R.string.ab_search), null);
        initAdapter();
        initListener();
    }

    private void initListener() {

        binding.imgVoice.setOnClickListener(v -> {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
            launcherVoice.launch(intent);
        });
        

        binding.search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                for (int i = 0; i < recentList.size(); i++) {
                    if (query.trim().equals(recentList.get(i).getName())) {
                        viewModel.delete(recentList.get(i));
                    }
                }
                viewModel.insert(new Recent(query.trim()));
                searchText = binding.search.findViewById(binding.search.getContext().getResources().getIdentifier("android:id/search_src_text", null, null));
                searchText.setSelection(0);
                searchText.setCursorVisible(false);
                searchText.setEllipsize(TextUtils.TruncateAt.END);
                searchText.setBackgroundColor(Color.TRANSPARENT);
                EventBus.getDefault().post(new MessageEvent(Constant.LOAD_DATA_VIDEO));
                showSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!newText.equals("")) {
                    viewModel.searchTitle(newText, Constant.MAX_RESULTS_TITLE);
                }
                viewModel.getRecent();
                setVisibilityView(!newText.equals("") ? View.GONE : View.VISIBLE, !newText.equals("") ? View.VISIBLE : View.GONE);
                return true;
            }
        });


        binding.search.setOnClickListener(v -> searchText.setCursorVisible(true));
        searchAdapter.setListen(items -> ((MainActivity) requireActivity()).showPlayVideo(items));
        recentAdapter.setListen(suggestion -> binding.search.setQuery(suggestion, true));
        searchTitleAdapter.setListen(suggestion -> binding.search.setQuery(suggestion, true));
    }

    private void setVisibilityView(int gone, int visible) {
        binding.imgVoice.setVisibility(gone);
        binding.txtRecent.setVisibility(View.VISIBLE);
        binding.rvVideoSearch.setVisibility(View.GONE);
        binding.txtError.setVisibility(View.GONE);
        binding.rvVideoHistory.setVisibility(gone);
        binding.rvTitleSearch.setVisibility(visible);
    }

    private void initAdapter() {
        recentAdapter = new RecentAdapter();
        searchTitleAdapter = new RecentAdapter();
        searchAdapter = new HomeAdapter();
        searchAdapter.setType(Constant.TYPE_TRENDING);
        recentAdapter.setType(Constant.TYPE_HOME);
        searchTitleAdapter.setType(Constant.TYPE_TRENDING);
        binding.rvVideoSearch.setAdapter(searchAdapter);
        binding.rvTitleSearch.setAdapter(searchTitleAdapter);
        binding.rvVideoHistory.setAdapter(recentAdapter);
    }

    private void observerData() {
        viewModel.videoTrendingLiveDataVN.observe(getViewLifecycleOwner(), videoChannels -> {
            if (videoChannels != null && videoChannels.size() > 0) {
                searchAdapter.setItems(videoChannels);
                binding.rvVideoSearch.setVisibility(View.VISIBLE);
                binding.rvTitleSearch.setVisibility(View.GONE);
                binding.rvVideoHistory.setVisibility(View.GONE);
                binding.txtError.setVisibility(View.GONE);
                binding.txtRecent.setVisibility(View.GONE);
                ((MainActivity) requireActivity()).hideKeyboard(requireActivity());
            } else {
                searchAdapter.setItems(null);
                binding.txtError.setVisibility(View.VISIBLE);
                Timber.e("video null");
            }


        });
        viewModel.getRecent();
        viewModel.listMutableLiveData.observe(getViewLifecycleOwner(), suggestions -> {
            if (suggestions != null) {
                recentList = suggestions;
                recentAdapter.setRecentList(recentList);
            } else {
                recentAdapter.setRecentList(null);
            }
        });
        viewModel.searchMutableLiveData.observe(getViewLifecycleOwner(), search -> {
            if (search != null) {
                searchTitleAdapter.setItems(search.getItems());
            }
        });
    }

    public void showSearch(String query) {
        querySave = query;
        viewModel.searchVideo(query);
        binding.rvVideoHistory.setVisibility(View.GONE);
        binding.rvTitleSearch.setVisibility(View.GONE);
        binding.txtRecent.setVisibility(View.GONE);
        binding.rvVideoSearch.setVisibility(View.VISIBLE);
        ((MainActivity) requireActivity()).hideKeyboard(requireActivity());
    }

    @Override
    protected void onPermissionGranted() {

    }


}

