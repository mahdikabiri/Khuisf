package com.example.khuisf.week;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.khuisf.R;
import com.example.khuisf.entitys.note.Note;
import com.example.khuisf.notes.NoteAdapter;
import com.example.khuisf.week.TabAdapter;
import com.example.khuisf.week.days.FridayFragment;
import com.example.khuisf.week.days.MondayFragment;
import com.example.khuisf.week.days.SaturdayFragment;
import com.example.khuisf.week.days.SundayFragment;
import com.example.khuisf.week.days.ThursdayFragment;
import com.example.khuisf.week.days.TuesdayFragment;
import com.example.khuisf.week.days.WednesdayFragment;
import com.gauravk.bubblenavigation.BubbleNavigationLinearView;
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener;

import java.util.Calendar;


public class FragmentWeek extends Fragment   {
    private ViewPager vpMain;
    BubbleNavigationLinearView bubbleNavigationLinearView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_week, container, false);
        init(view);
        TabAdapter adapter = new TabAdapter(getChildFragmentManager());

        adapter.addFragment(6, true, new FridayFragment(), "جمعه");
        adapter.addFragment(5, true, new ThursdayFragment(), "پنج شنبه");
        adapter.addFragment(4, true, new WednesdayFragment(), "چهارشنبه");
        adapter.addFragment(3, true, new TuesdayFragment(), "سه شنبه");
        adapter.addFragment(2, true, new MondayFragment(), "دوشنبه");
        adapter.addFragment(1, true, new SundayFragment(), " یکشنبه");
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
        goToToday();
        vpMain.setOffscreenPageLimit(6);

        return view;
    }

    private void goToToday() {
        Calendar calender =Calendar.getInstance();
        int day=calender.get(Calendar.DAY_OF_WEEK);
        switch (day) {
            case Calendar.SATURDAY:
                vpMain.setCurrentItem(6);
                break;
            case Calendar.SUNDAY:
                vpMain.setCurrentItem(5);
                break;
            case Calendar.MONDAY:
                vpMain.setCurrentItem(4);
                break;
            case Calendar.TUESDAY:
                vpMain.setCurrentItem(3);
                break;
            case Calendar.WEDNESDAY:
                vpMain.setCurrentItem(2);
                break;
            case Calendar.THURSDAY:
                vpMain.setCurrentItem(1);
                break;
            case Calendar.FRIDAY:
                vpMain.setCurrentItem(0);
                break;
        }
    }

    private void init(View view) {
        vpMain = view.findViewById(R.id.fragment_week_viewpager);
        bubbleNavigationLinearView = view.findViewById(R.id.fragment_week_bottom_navigation_view_linear);
    }

}
