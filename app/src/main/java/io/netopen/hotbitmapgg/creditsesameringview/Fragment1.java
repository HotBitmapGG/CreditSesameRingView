package io.netopen.hotbitmapgg.creditsesameringview;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

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

    private ImageView mButton;

    private NewCreditSesameView newCreditSesameView;


    public static Fragment1 newInstance()
    {

        return new Fragment1();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {

        View view = inflater.inflate(R.layout.fragment_1, null);
        mLayout = (RelativeLayout) view.findViewById(R.id.layout);
        mButton = (ImageView) view.findViewById(R.id.btn);
        newCreditSesameView = (NewCreditSesameView) view.findViewById(R.id.sesame_view);
        mLayout.setBackgroundColor(mColors[0]);
        mButton.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View view)
            {

                newCreditSesameView.setSesameValues(639);
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
