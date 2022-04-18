package com.nts.youtubemusic.utils;

import android.annotation.SuppressLint;
import android.content.Context;

import com.nts.youtubemusic.App;
import com.nts.youtubemusic.R;
import com.nts.youtubemusic.data.model.channel.ItemChannel;
import com.nts.youtubemusic.data.model.video.Items;

import java.time.OffsetDateTime;
import java.util.Calendar;

public class ConvertTime {
    public ConvertTime() {
    }

    @SuppressLint("SetTextI18n")
    public static String convertLikeCount(Items items) {
        String textView = null;
        String like = items.getStatistics().getLikeCount();
        if (like == null) {
            textView = "0";
        } else {
            if (items != null) {
                long likeCount = Integer.parseInt(like);
                if (likeCount < 1000) {
                    textView = (likeCount + "");
                } else if (likeCount / 1000 < 1000) {
                    textView = ((likeCount) / 1000 + "K");
                } else if ((likeCount / 1000000) < 1000 && (likeCount / 1000000) > 0) {
                    textView = ((likeCount) / 1000000 + "M");
                } else {
                    textView = ((likeCount) / 1000000000 + "B");
                }
            }
        }
        return textView;
    }

    @SuppressLint("SetTextI18n")
    public static String convertCommentView(Items videoYoutube) {
        String textView = null;
        if (videoYoutube != null) {
            String comment = videoYoutube.getStatistics().getCommentCount();
            if (comment == null) {
                textView = "0";
            } else {
                long likeCount = Integer.parseInt(videoYoutube.getStatistics().getCommentCount());
                if (likeCount < 1000) {
                    textView = (likeCount + "");
                } else if (likeCount / 1000 < 1000) {
                    textView = ((likeCount) / 1000 + "K");
                } else if ((likeCount / 1000000) < 1000 && (likeCount / 1000000) > 0) {
                    textView = ((likeCount) / 1000000 + "M");
                } else {
                    textView = ((likeCount) / 1000000000 + "B");
                }
            }
        }
        return textView;
    }

    public static String convertDisLikeCount(Items items) {
        String textView = null;
        if (items != null) {
            String like = items.getStatistics().getDislikeCount();
            if (like == null) {
                textView = "0";
            } else {
                long likeCount = Integer.parseInt(like);
                if (likeCount < 1000) {
                    textView = (likeCount + "");
                } else if (likeCount / 1000 < 1000) {
                    textView = ((likeCount) / 1000 + "K");
                } else if ((likeCount / 1000000) < 1000 && (likeCount / 1000000) > 0) {
                    textView = ((likeCount) / 1000000 + "M");
                } else {
                    textView = ((likeCount) / 1000000000 + "B");
                }
            }
        }
        return textView;
    }


    @SuppressLint("SetTextI18n")
    public static String convertViewCount(Context context, String view) {
        String textView = null;
        if (view == null) {
            textView = "0";
        } else {
            long viewCount = Long.parseLong(view);
            if (viewCount < 1000) {
                textView = (viewCount + " " + App.getContext().getString(R.string.view_count));
            }
            if (viewCount >= 1000 && viewCount < 1000000) {
                textView = (viewCount / 1000 + App.getContext().getString(R.string.view_count_k));
            }
            if (viewCount >= 1000000 && viewCount < 1000000000) {
                textView = (viewCount / 1000000 + App.getContext().getString(R.string.view_count_m));
            }
            if (viewCount >= 1000000000) {
                textView = (viewCount / 1000000000 + App.getContext().getString(R.string.view_count_b));
            }
        }
        return textView;
    }

    @SuppressLint("SetTextI18n")
    public static String convertSub(Context context, ItemChannel items) {
        String textView = null;
        String view = items.getStatistics().getSubscriberCount();
        if (items.getStatistics() != null && view == null) {
            textView = "0";
        } else {
            long viewCount = Long.parseLong(view);

            if (viewCount >= 1000 && viewCount < 1000000) {
                textView = (viewCount / 1000 + " " + context.getResources().getString(R.string.view_count_k));
            }
            if (viewCount >= 1000000 && viewCount < 1000000000) {
                textView = (viewCount / 1000000 + " " + context.getResources().getString(R.string.view_count_m));
            }
            if (viewCount >= 1000000000) {
                textView = (viewCount / 1000000000 + " " + context.getResources().getString(R.string.view_count_b));
            }
        }
        return textView;
    }


    public static String convertTime(Context context, String myDate) {
        String time = null;
        Calendar today = Calendar.getInstance();
//        String myDate = item.getSnippet().getPublishedAt();
        String inputModified = myDate.replace(" ", "T");
        int lengthOfAbbreviatedOffset = 3;
        if (inputModified.indexOf("+") == (inputModified.length() - lengthOfAbbreviatedOffset)) {
            // If third character from end is a PLUS SIGN, append ':00'.
            inputModified = inputModified + ":00";
        }
        if (inputModified.indexOf("-") == (inputModified.length() - lengthOfAbbreviatedOffset)) {
            // If third character from end is a PLUS SIGN, append ':00'.
            inputModified = inputModified + ":00";
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            OffsetDateTime odt = OffsetDateTime.parse(inputModified);
            long millis = odt
                    .toInstant().toEpochMilli();
            long diff = today.getTimeInMillis() - millis;
            long days = diff / (24 * 60 * 60 * 1000);

            long year = days / 365;
            long month = (days - year * 365) / 30;
            long day = (days - year * 365 - month * 30);
            long hour = diff / (60 * 60 * 1000) - year * 365 * 24 - month * 30 * 24 - day * 24;
            long minute = diff / (60 * 1000) - year * 365 * 24 * 60 - month * 30 * 24 * 60 - day * 24 * 60 - hour * 60;

            if (year > 0) {
                time = year + " " + context.getString(R.string.year_ago);
            } else if (month > 0 && year == 0) {
                time = month + " " + context.getString(R.string.mon_ago);
            } else if (day > 0 && month == 0 && year == 0) {
                time = day + " " + context.getString(R.string.day_ago);
            } else if (hour > 0 && day == 0 && month == 0 && year == 0) {
                time = hour + " " + context.getString(R.string.hour_ago);
            } else if (minute > 0 && hour == 0 && day == 0 && month == 0 && year == 0) {
                time = minute + " " + context.getString(R.string.minute_ago);
            }
        }
        return time;
    }

    public static String convertDuration(long time) {
        String duration = "01:52:33";
        if (time > 0 && time < 60) {
            if (time < 10) {
                duration = "00:0" + time;
            } else {
                duration = "00:" + time;
            }
        }
        if (time >= 60 && time < 3600) {
            if ((time / 60) < 10) {
                if ((time % 60) < 10) {
                    duration = "0" + (time / 60) + ":0" + (time % 60);
                } else {
                    duration = "0" + (time / 60) + ":" + (time % 60);
                }
            } else {
                if ((time % 60) < 10) {
                    duration = (time / 60) + ":0" + (time % 60);
                } else {
                    duration = (time / 60) + ":" + (time % 60);
                }
            }
        }
        if (time >= 3600) {
            if ((time / 3600) < 10) {
                if (((time % 3600) / 60) < 10) {
                    if (((time % 3600) % 60) < 10) {
                        duration = "0" + (time / 3600) + ":0" + ((time % 3600) / 60) + ":0" + (((time % 3600) % 60));
                    } else {
                        duration = "0" + (time / 3600) + ":0" + ((time % 3600) / 60) + ":" + (((time % 3600) % 60));
                    }
                } else {
                    if (((time % 3600) % 60) < 10) {
                        duration = "0" + (time / 3600) + ":" + ((time % 3600) / 60) + ":0" + (((time % 3600) % 60));
                    } else {
                        duration = "0" + (time / 3600) + ":" + ((time % 3600) / 60) + ":" + (((time % 3600) % 60));
                    }
                }
            } else {
                if (((time % 3600) / 60) < 10) {
                    if (((time % 3600) % 60) < 10) {
                        duration = (time / 3600) + ":0" + ((time % 3600) / 60) + ":0" + (((time % 3600) % 60));
                    } else {
                        duration = (time / 3600) + ":0" + ((time % 3600) / 60) + ":" + (((time % 3600) % 60));
                    }
                } else {
                    if (((time % 3600) % 60) < 10) {
                        duration = (time / 3600) + ":" + ((time % 3600) / 60) + ":0" + (((time % 3600) % 60));
                    } else {
                        duration = (time / 3600) + ":" + ((time % 3600) / 60) + ":" + (((time % 3600) % 60));
                    }
                }
            }
        }
        return duration;
    }


}
