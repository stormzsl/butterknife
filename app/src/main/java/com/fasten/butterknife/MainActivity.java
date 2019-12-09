package com.fasten.butterknife;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.fasten.butterknife.annotations.BindView;
import com.fasten.butterknife.api.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.first_tv)
    TextView mFirstTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mFirstTextView.setText("butterknife");
    }
}
