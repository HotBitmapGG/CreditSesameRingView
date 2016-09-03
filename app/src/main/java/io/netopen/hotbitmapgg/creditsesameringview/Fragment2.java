package io.netopen.hotbitmapgg.creditsesameringview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import io.netopen.hotbitmapgg.view.OldCreditSesameView;

/**
 * Created by hcc on 16/9/2 23:31
 * 100332338@qq.com
 */
public class Fragment2 extends Fragment
{

    private OldCreditSesameView oldCreditSesameView;

    private Button mButton;

    public static Fragment2 newInstance()
    {

        return new Fragment2();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {

        View view = inflater.inflate(R.layout.fragment_2, null);
        oldCreditSesameView = (OldCreditSesameView) view.findViewById(R.id.sesame_view);
        mButton = (Button) view.findViewById(R.id.btn);
        mButton.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View view)
            {

                oldCreditSesameView.setSesameValues(639);
            }
        });

        return view;
    }
}
