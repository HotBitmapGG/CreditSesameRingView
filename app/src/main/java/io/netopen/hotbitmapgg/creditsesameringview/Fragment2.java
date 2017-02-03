package io.netopen.hotbitmapgg.creditsesameringview;

import io.netopen.hotbitmapgg.view.OldCreditSesameView;
import java.util.Random;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by hcc on 16/9/2 23:31
 * 100332338@qq.com
 */
public class Fragment2 extends Fragment {

  private OldCreditSesameView oldCreditSesameView;

  private Random random = new Random();


  public static Fragment2 newInstance() {

    return new Fragment2();
  }


  @Nullable
  @Override
  @SuppressLint("InflateParams")
  public View onCreateView(LayoutInflater inflater,
                           @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.fragment_2, null);
    oldCreditSesameView = (OldCreditSesameView) view.findViewById(R.id.sesame_view);
    ImageView mButton = (ImageView) view.findViewById(R.id.btn);
    mButton.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View view) {

        int i = random.nextInt(950);
        oldCreditSesameView.setSesameValues(i);
      }
    });

    return view;
  }
}
