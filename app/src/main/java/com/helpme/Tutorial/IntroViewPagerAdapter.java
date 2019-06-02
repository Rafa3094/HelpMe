package com.helpme.Tutorial;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.helpme.R;

import java.util.List;

public class IntroViewPagerAdapter extends PagerAdapter {

    Context nContext;
    List<ScreenItem> nListScreen;

    public IntroViewPagerAdapter(Context nContext, List<ScreenItem> nListScreen) {
        this.nContext = nContext;
        this.nListScreen = nListScreen;
    }

    @Override
    public int getCount() {
        return nListScreen.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return (view == o);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) nContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layoutScreen = inflater.inflate(R.layout.layout_screen, null);
        ImageView imageview = layoutScreen.findViewById(R.id.introimg);
        TextView title = layoutScreen.findViewById(R.id.introtext);
        TextView descr = layoutScreen.findViewById(R.id.introdescr);

        title.setText(nListScreen.get(position).getTitle());
        descr.setText(nListScreen.get(position).getDescription());
        imageview.setImageResource(nListScreen.get(position).getScreenImg());

        container.addView(layoutScreen);
        return layoutScreen;
    }
}
