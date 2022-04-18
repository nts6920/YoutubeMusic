package com.nts.youtubemusic.ui.main.home;

import androidx.lifecycle.MutableLiveData;

import com.nts.youtubemusic.common.Constant;
import com.nts.youtubemusic.data.model.channel.Channel;
import com.nts.youtubemusic.data.model.table.VideoChannel;
import com.nts.youtubemusic.data.model.video.Items;
import com.nts.youtubemusic.data.model.video.VideoYoutube;
import com.nts.youtubemusic.data.remote.YoutubeAPI;
import com.nts.youtubemusic.data.repository.YoutubeRepository;
import com.nts.youtubemusic.ui.base.BaseViewModel;
import com.nts.youtubemusic.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import timber.log.Timber;


@HiltViewModel
public class HomeViewModel extends BaseViewModel {

    public YoutubeAPI youtubeAPI;
    public YoutubeRepository youtubeRepository;
    public MutableLiveData<List<VideoChannel>> videoTrendingLiveDataVN = new MutableLiveData<>();
    public List<Items> itemsList = new ArrayList<>();
    //
    public List<String> listApi = Utils.setListApi();
    public int index = 0;
    @Inject
    public HomeViewModel(YoutubeRepository youtubeRepository, YoutubeAPI youtubeAPI) {
        this.youtubeRepository = youtubeRepository;
        this.youtubeAPI = youtubeAPI;
    }

    public void getVideoHome( String requestCode) {
        youtubeRepository.getVideoTrending(Constant.MAX_RESULTS, Constant.API_KEY, requestCode).subscribe(new SingleObserver<VideoYoutube>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull VideoYoutube videoYoutube) {
                        List<VideoChannel> videoChannels = new ArrayList<>();
                        itemsList = videoYoutube.getItems();
                        Constant.API_KEY = listApi.get(index);
                        youtubeAPI.getChannel(itemsList.get(0).getSnippet().getChannelId(), Constant.API_KEY)
                        .flatMap(channel -> {
                            videoChannels.add(new VideoChannel(itemsList.get(0), null, channel));
                            return youtubeAPI.getChannel(itemsList.get(1).getSnippet().getChannelId(), Constant.API_KEY);
                        })
                        .flatMap(channel -> {
                            videoChannels.add(new VideoChannel(itemsList.get(1), null, channel));
                            return youtubeAPI.getChannel(itemsList.get(2).getSnippet().getChannelId(), Constant.API_KEY);
                        })
                        .flatMap(channel -> {
                            videoChannels.add(new VideoChannel(itemsList.get(2), null, channel));
                            return youtubeAPI.getChannel(itemsList.get(3).getSnippet().getChannelId(), Constant.API_KEY);
                        })
                        .flatMap(channel -> {
                            videoChannels.add(new VideoChannel(itemsList.get(3), null, channel));
                            return youtubeAPI.getChannel(itemsList.get(4).getSnippet().getChannelId(), Constant.API_KEY);
                        })
                        .flatMap(channel -> {
                            videoChannels.add(new VideoChannel(itemsList.get(4), null, channel));
                            return youtubeAPI.getChannel(itemsList.get(5).getSnippet().getChannelId(), Constant.API_KEY);
                        })
                        .flatMap(channel -> {
                            videoChannels.add(new VideoChannel(itemsList.get(5), null, channel));
                            return youtubeAPI.getChannel(itemsList.get(6).getSnippet().getChannelId(), Constant.API_KEY);
                        })
                        .flatMap(channel -> {
                            videoChannels.add(new VideoChannel(itemsList.get(6), null, channel));
                            return youtubeAPI.getChannel(itemsList.get(7).getSnippet().getChannelId(), Constant.API_KEY);
                        })
                        .flatMap(channel -> {
                            videoChannels.add(new VideoChannel(itemsList.get(7), null, channel));
                            return youtubeAPI.getChannel(itemsList.get(8).getSnippet().getChannelId(), Constant.API_KEY);
                        })
                        .flatMap(channel -> {
                            videoChannels.add(new VideoChannel(itemsList.get(8), null, channel));
                            return youtubeAPI.getChannel(itemsList.get(9).getSnippet().getChannelId(), Constant.API_KEY);
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new SingleObserver<Channel>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                compositeDisposable.add(d);
                            }

                            @Override
                            public void onSuccess(@NonNull Channel channel) {
                                Constant.API_KEY = listApi.get(index);
                                videoChannels.add(new VideoChannel(itemsList.get(9), null, channel));
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
                e.printStackTrace();
                index = (index + 1) % listApi.size();
                videoTrendingLiveDataVN.postValue(null);

            }
        });

    }

}


