package com.reviling.filamentandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class IntroViewPagerAdapter extends PagerAdapter {

    Context context;
    List<ScreenItem> mListScreen;

    public IntroViewPagerAdapter(Context context, List<ScreenItem> mListScreen) {
        this.context = context;
        this.mListScreen = mListScreen;
    }

    @Override
    public int getCount() {
        return mListScreen.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layoutScreen = inflater.inflate(R.layout.layout_screen, null);


        layoutScreen.setBackgroundResource(mListScreen.get(position).getBackgroundImg());

        TextView title = layoutScreen.findViewById(R.id.intro_title);
        TextView description = layoutScreen.findViewById(R.id.intro_description);
        TextView description2 = layoutScreen.findViewById(R.id.intro_description2);
        ImageView imgSlide = layoutScreen.findViewById(R.id.intro_img);

        title.setText(mListScreen.get(position).getTitle());
        description.setText(mListScreen.get(position).getDescription());
        imgSlide.setImageResource(mListScreen.get(position).getScreenImg());

        // Set text color
        title.setTextColor(mListScreen.get(position).getTextColor());
        title.setShadowLayer(10, 3, 3, mListScreen.get(position).getShadowColor());


        // Apply shadow properties
        title.setShadowLayer(25, 0, 0, mListScreen.get(position).getShadowColor());

        // Show description2 only for the third slide
        if (mListScreen.get(position).getDescription2() != null) {
            description2.setText(mListScreen.get(position).getDescription2());
            description2.setVisibility(View.VISIBLE);
        } else {
            description2.setVisibility(View.GONE);
        }

        container.addView(layoutScreen);
        return layoutScreen;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

}
