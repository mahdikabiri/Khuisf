package com.example.khuisf;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.khuisf.week.days.FridayFragment;
import com.example.khuisf.week.days.MondayFragment;
import com.example.khuisf.week.days.SaturdayFragment;
import com.example.khuisf.week.days.SondayFragment;
import com.example.khuisf.week.TabAdapter;
import com.example.khuisf.week.days.ThursdayFragment;
import com.example.khuisf.week.days.TuesdayFragment;
import com.example.khuisf.week.days.WednesdayFragment;
import com.gauravk.bubblenavigation.BubbleNavigationLinearView;
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener;


public class FragmentWeek extends Fragment {
    private ViewPager vpMain;
    BubbleNavigationLinearView bubbleNavigationLinearView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test, container, false);
          init(view);

        TabAdapter adapter = new TabAdapter(getChildFragmentManager());

        adapter.addFragment(6, true, new FridayFragment(), "جمعه");
        adapter.addFragment(5, true, new ThursdayFragment(), "پنج شنبه");
        adapter.addFragment(4, true, new WednesdayFragment(), "چهارشنبه");
        adapter.addFragment(3, true, new TuesdayFragment(), "سه شنبه");
        adapter.addFragment(2, true, new MondayFragment(), "دوشنبه");
        adapter.addFragment(1, true, new SondayFragment(), " یکشنبه");
        adapter.addFragment(0, true, new SaturdayFragment(), "شنبه");

        vpMain.setAdapter(adapter);
        vpMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                bubbleNavigationLinearView.setCurrentActiveItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        bubbleNavigationLinearView.setNavigationChangeListener(new BubbleNavigationChangeListener() {
            @Override
            public void onNavigationChanged(View view, int position) {
                vpMain.setCurrentItem(position, true);
            }
        });

        vpMain.setAdapter(adapter);
        ///storage how much fragment
        vpMain.setOffscreenPageLimit(6);
        vpMain.setCurrentItem(6);

        return view;
    }

    private void init(View view) {
        vpMain=view.findViewById(R.id.fragment_week_viewpager);
         bubbleNavigationLinearView = view.findViewById(R.id.fragment_week_bottom_navigation_view_linear);

    }
}
