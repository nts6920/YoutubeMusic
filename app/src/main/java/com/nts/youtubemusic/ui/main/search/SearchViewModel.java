package com.nts.youtubemusic.ui.main.search;

import androidx.lifecycle.MutableLiveData;

import com.nts.youtubemusic.common.Constant;
import com.nts.youtubemusic.data.model.channel.Channel;
import com.nts.youtubemusic.data.model.recent.Item;
import com.nts.youtubemusic.data.model.recent.Search;
import com.nts.youtubemusic.data.model.table.Recent;
import com.nts.youtubemusic.data.model.table.VideoChannel;
import com.nts.youtubemusic.data.model.video.VideoYoutube;
import com.nts.youtubemusic.data.remote.YoutubeAPI;
import com.nts.youtubemusic.data.repository.RecentRepository;
import com.nts.youtubemusic.data.repository.YoutubeRepository;
import com.nts.youtubemusic.ui.base.BaseViewModel;
import com.nts.youtubemusic.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import timber.log.Timber;

@HiltViewModel
public class SearchViewModel extends BaseViewModel {
    public YoutubeRepository youtubeRepository;
    public RecentRepository recentRepository;
    public YoutubeAPI youtubeAPI;
    public MutableLiveData<List<VideoChannel>> videoTrendingLiveDataVN = new MutableLiveData<>();
    public MutableLiveData<Search> searchMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<List<Recent>> listMutableLiveData = new MutableLiveData<>();
    public List<Item> itemsList = new ArrayList<>();
    //
    public List<String> listApi = Utils.setListApi();
    public int index = 0;

    @Inject
    public SearchViewModel(YoutubeRepository youtubeRepository, RecentRepository recentRepository, YoutubeAPI youtubeAPI) {
        this.youtubeRepository = youtubeRepository;
        this.recentRepository = recentRepository;
        this.youtubeAPI = youtubeAPI;

    }

    public void searchTitle(String q, int maxResults) {
        youtubeRepository.getVideoSearch(Constant.API_KEY, Constant.PART, q, null, maxResults).subscribe(new SingleObserver<Search>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onSuccess(@NonNull Search search) {
                searchMutableLiveData.postValue(search);

            }

            @Override
            public void onError(@NonNull Throwable e) {
                Timber.e(e);
                searchMutableLiveData.postValue(null);
            }
        });
    }

    public void getRecent() {
        recentRepository.getRecent().subscribe(new SingleObserver<List<Recent>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onSuccess(@NonNull List<Recent> recents) {
                Collections.reverse(recents);
                listMutableLiveData.postValue(recents);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Timber.e(e);
            }
        });

    }


    public void insert(Recent recent) {
        recentRepository.insertRecent(recent);
    }

    public void delete(Recent recent) {
        recentRepository.deleteRecent(recent);
    }

    public void searchVideo(String q) {
        List<VideoChannel> videoChannels = new ArrayList<>();

        youtubeRepository.getVideoSearch(Constant.API_KEY, Constant.PART, q, Constant.TYPE, Constant.MAX_RESULTS).subscribe(new SingleObserver<Search>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onSuccess(@NonNull Search search) {
                itemsList = search.getItems();
                Constant.API_KEY = listApi.get(index);
                youtubeRepository.getChannel(itemsList.get(0).getSnippet().getChannelId(), Constant.API_KEY)
                        .flatMap(channel ->
                                setChannel(videoChannels, channel, 0))
                        .flatMap(channel -> setChannel(videoChannels, channel, 1))
                        .flatMap(channel -> setChannel(videoChannels, channel, 2))
                        .flatMap(channel -> setChannel(videoChannels, channel, 3))
                        .flatMap(channel -> setChannel(videoChannels, channel, 4))
                        .flatMap(channel -> setChannel(videoChannels, channel, 5))
                        .flatMap(channel -> setChannel(videoChannels, channel, 6))
                        .flatMap(channel -> setChannel(videoChannels, channel, 7))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new SingleObserver<Channel>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                compositeDisposable.add(d);
                            }
                            @Override
                            public void onSuccess(@NonNull Channel channel) {
                                videoChannels.add(new VideoChannel(null, itemsList.get(8), channel));
                                Constant.API_KEY = listApi.get(index);
                                youtubeAPI.getVideo(itemsList.get(0).getId().getVideoId(), Constant.API_KEY).flatMap(videoYoutube ->
                                        setVideo(videoChannels, videoYoutube, 0)
                                ).flatMap(videoYoutube ->
                                        setVideo(videoChannels, videoYoutube, 1)
                                ).flatMap(videoYoutube ->
                                        setVideo(videoChannels, videoYoutube, 2)
                                ).flatMap(videoYoutube ->
                                        setVideo(videoChannels, videoYoutube, 3)
                                ).flatMap(videoYoutube ->
                                        setVideo(videoChannels, videoYoutube, 4)
                                ).flatMap(videoYoutube ->
                                        setVideo(videoChannels, videoYoutube, 5)
                                ).flatMap(videoYoutube ->
                                        setVideo(videoChannels, videoYoutube, 6)
                                ).flatMap(videoYoutube ->
                                        setVideo(videoChannels, videoYoutube, 7)
                                )
                                        .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<VideoYoutube>() {
                                    @Override
                                    public void onSubscribe(@NonNull Disposable d) {
                                        compositeDisposable.add(d);
                                    }

                                    @Override
                                    public void onSuccess(@NonNull VideoYoutube videoYoutube) {
                                        videoChannels.get(8).setItem(videoYoutube.getItems().get(0));
                                        videoTrendingLiveDataVN.postValue(videoChannels);
                                    }

                                    @Override
                                    public void onError(@NonNull Throwable e) {
                                        Timber.e(e);
                                        index = (index + 1) % listApi.size();
                                        videoTrendingLiveDataVN.postValue(null);
                                    }
                                });
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                index = (index + 1) % listApi.size();
                                videoTrendingLiveDataVN.postValue(null);
                                Timber.e(e);
                            }
                        });

            }

            @Override
            public void onError(@NonNull Throwable e) {
                index = (index + 1) % listApi.size();
                videoTrendingLiveDataVN.postValue(null);
                Timber.e(e);
            }
        });

    }

    private Single<Channel> setChannel(List<VideoChannel> videoChannels, Channel channel, int position) {
        videoChannels.add(new VideoChannel(null, itemsList.get(position), channel));
        return youtubeRepository.getChannel(itemsList.get(position + 1).getSnippet().getChannelId(), Constant.API_KEY);
    }

    private Single<VideoYoutube> setVideo(List<VideoChannel> videoChannels, VideoYoutube videoYoutube, int position) {
        videoChannels.get(position).setItem(videoYoutube.getItems().get(0));
        return youtubeAPI.getVideo(itemsList.get(position + 1).getId().getVideoId(), Constant.API_KEY);
    }
}

