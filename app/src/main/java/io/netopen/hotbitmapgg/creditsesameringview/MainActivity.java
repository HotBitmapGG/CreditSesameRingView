package io.netopen.hotbitmapgg.creditsesameringview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import io.netopen.hotbitmapgg.view.CreditSesameRingView;

public class MainActivity extends AppCompatActivity
{

    private Button mButton;

    private CreditSesameRingView mCreditSesameRingView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton = (Button) findViewById(R.id.btn);
        mCreditSesameRingView = (CreditSesameRingView) findViewById(R.id.credit_sesame_ring_view);
        mButton.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View view)
            {

                mCreditSesameRingView.setSesameData(670);
            }
        });
    }
}
