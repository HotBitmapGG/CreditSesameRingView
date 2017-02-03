package io.netopen.hotbitmapgg.creditsesameringview;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

  private ViewPager mViewPager;

  private Fragment[] fragments = new Fragment[] { Fragment1.newInstance(),
      Fragment2.newInstance() };


  @Override
  protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mViewPager = (ViewPager) findViewById(R.id.view_pager);
    mViewPager.setAdapter(new PagerAdapter(getSupportFragmentManager(), fragments));
  }


  private static class PagerAdapter extends FragmentStatePagerAdapter {

    private Fragment[] fragments;


    public PagerAdapter(FragmentManager fm, Fragment[] fragments) {

      super(fm);
      this.fragments = fragments;
    }


    @Override
    public Fragment getItem(int position) {

      return fragments[position];
    }


    @Override
    public int getCount() {

      return 2;
    }
  }
}
