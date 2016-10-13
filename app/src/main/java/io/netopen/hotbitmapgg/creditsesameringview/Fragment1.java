package io.netopen.hotbitmapgg.creditsesameringview;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Random;

import io.netopen.hotbitmapgg.view.NewCreditSesameView;

/**
 * Created by hcc on 16/9/2 23:31
 * 100332338@qq.com
 */
public class Fragment1 extends Fragment
{

    private final int[] mColors = new int[]{
            0xFFFF80AB,
            0xFFFF4081,
            0xFFFF5177,
            0xFFFF7997
    };

    private RelativeLayout mLayout;

    private NewCreditSesameView newCreditSesameView;

    private Random random = new Random();

    public static Fragment1 newInstance()
    {

        return new Fragment1();
    }


    @Nullable
    @Override
    @SuppressLint("InflateParams")
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {

        View view = inflater.inflate(R.layout.fragment_1, null);
        mLayout = (RelativeLayout) view.findViewById(R.id.layout);
        ImageView mButton = (ImageView) view.findViewById(R.id.btn);
        newCreditSesameView = (NewCreditSesameView) view.findViewById(R.id.sesame_view);
        mLayout.setBackgroundColor(mColors[0]);
        mButton.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View view)
            {

                int i = random.nextInt(950);
                newCreditSesameView.setSesameValues(i);
                startColorChangeAnim();
            }
        });
        return view;
    }


    public void startColorChangeAnim()
    {

        ObjectAnimator animator = ObjectAnimator.ofInt(mLayout, "backgroundColor", mColors);
        animator.setDuration(3000);
        animator.setEvaluator(new ArgbEvaluator());
        animator.start();
    }
}
