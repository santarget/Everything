package com.ssy.everything.feature.news.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ssy.everything.R;
import com.ssy.everything.bean.NewsInfo;
import com.ssy.everything.util.ListUtils;
import com.ssy.everything.util.StringUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ssy on 2017/6/6.
 */

public class NewsAdapter extends RecyclerView.Adapter {
    private static final int TYPE_FOOTER = 0;
    private static final int TYPE_NO_PIC = 1;
    private static final int TYPE_ONE_PIC = 2;
    private static final int TYPE_THREE_PIC = 3;
    private static final int TYPE_AD = 4;
    
    private final Context context;
    private ArrayList<NewsInfo> newsInfos;
    private OnRecyclerViewItemClickListener mOnItemClickListener;

    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, NewsInfo info);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public NewsAdapter(Context context, ArrayList<NewsInfo> newsInfos) {
        this.context = context;
        if (ListUtils.isEmpty(newsInfos)) {
            this.newsInfos = new ArrayList<>();
        } else {
            this.newsInfos = new ArrayList<>(newsInfos);
        }
    }

    public void setNewsInfos(ArrayList<NewsInfo> newsInfos, boolean isFoot) {
        if (this.newsInfos == null) {
            this.newsInfos = new ArrayList<>(newsInfos);
            notifyDataSetChanged();
        } else if (isFoot) {
            this.newsInfos.addAll(newsInfos);
            notifyDataSetChanged();
            notifyItemRemoved(getItemCount());
        } else {
            this.newsInfos.addAll(0, newsInfos);
            notifyItemRangeInserted(0, newsInfos.size());
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case TYPE_FOOTER:
//                holder = new FootViewHolder(new FooterView(context));
                view = LayoutInflater.from(context).inflate(R.layout.footer_view, parent, false);
                holder = new FootViewHolder(view);
                break;
            case TYPE_NO_PIC:
                view = LayoutInflater.from(context).inflate(R.layout.item_news_no_pic, parent, false);
                holder = new ViewHolderNoPic(view);
                break;
            case TYPE_ONE_PIC:
                view = LayoutInflater.from(context).inflate(R.layout.item_news_one_pic, parent, false);
                holder = new ViewHolderOnePic(view);
                break;
            case TYPE_THREE_PIC:
                view = LayoutInflater.from(context).inflate(R.layout.item_news_three_pic, parent, false);
                holder = new ViewHolderThreePic(view);
                break;
            case TYPE_AD:
                view = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_2, parent, false);
                holder = new ViewHolderAd(view);
                break;
            default:
                break;

        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (!(holder instanceof FootViewHolder)) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(v, newsInfos.get(position));
                    }
                }
            });
            NewsInfo info = newsInfos.get(position);
            if (holder instanceof ViewHolderNoPic) {
                ViewHolderNoPic viewHolder = (ViewHolderNoPic) holder;
                viewHolder.tvTitle.setText(info.getTitle());
                viewHolder.tvDesc.setText(info.getAuthor_name() + "  " + info.getDate());
            } else if (holder instanceof ViewHolderOnePic) {
                ViewHolderOnePic viewHolder = (ViewHolderOnePic) holder;
                viewHolder.tvTitle.setText(info.getTitle());
                viewHolder.tvDesc.setText(info.getAuthor_name() + "  " + info.getDate());
                loadImage(info.getThumbnail_pic_s(), viewHolder.ivPic);
            } else if (holder instanceof ViewHolderThreePic) {
                ViewHolderThreePic viewHolder = (ViewHolderThreePic) holder;
                viewHolder.tvTitle.setText(info.getTitle());
                viewHolder.tvDesc.setText(info.getAuthor_name() + "  " + info.getDate());
                loadImage(info.getThumbnail_pic_s(), viewHolder.ivPic1);
                loadImage(info.getThumbnail_pic_s02(), viewHolder.ivPic2);
                loadImage(info.getThumbnail_pic_s03(), viewHolder.ivPic3);
            } else if (holder instanceof ViewHolderAd) {

            }
        }
    }


    @Override
    public int getItemCount() {
        return newsInfos.size() == 0 ? 0 : newsInfos.size() + 1;

    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            NewsInfo info = newsInfos.get(position);
            if (StringUtils.isEmpty(info.getThumbnail_pic_s())) {
                return TYPE_NO_PIC;
            } else if (!StringUtils.isEmpty(info.getThumbnail_pic_s02()) && !StringUtils.isEmpty(info.getThumbnail_pic_s03())) {
                return TYPE_THREE_PIC;
            } else {
                return TYPE_ONE_PIC;
            }
        }

    }

    private void loadImage(String url, ImageView iv) {
        Glide.with(context).load(url).placeholder(R.mipmap.place_holder_news).error(R.mipmap.error_news)
                .fitCenter().dontAnimate().into(iv);
    }

    static class FootViewHolder extends RecyclerView.ViewHolder {

        public FootViewHolder(View view) {
            super(view);
        }
    }

    static class ViewHolderNoPic extends RecyclerView.ViewHolder {
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvDesc)
        TextView tvDesc;

        public ViewHolderNoPic(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class ViewHolderOnePic extends RecyclerView.ViewHolder {
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.ivPic)
        ImageView ivPic;
        @BindView(R.id.tvDesc)
        TextView tvDesc;

        public ViewHolderOnePic(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class ViewHolderThreePic extends RecyclerView.ViewHolder {
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.ivPic1)
        ImageView ivPic1;
        @BindView(R.id.ivPic2)
        ImageView ivPic2;
        @BindView(R.id.ivPic3)
        ImageView ivPic3;
        @BindView(R.id.tvDesc)
        TextView tvDesc;

        public ViewHolderThreePic(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class ViewHolderAd extends RecyclerView.ViewHolder {
        public ViewHolderAd(View itemView) {
            super(itemView);
        }
    }

}
