package com.example.topcoder.pettingzoo.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.topcoder.pettingzoo.Fragments.AlertOptionsDialogFragment;
import com.example.topcoder.pettingzoo.R;

public class FixtureActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fixture);
        setResult(1);
        findViewById(R.id.fixureDetailSetting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
                AlertOptionsDialogFragment dialog =
                        AlertOptionsDialogFragment.newInstance("Edit Fixture Options",
                                new AlertOptionsDialogFragment.OptionsSelectedItem() {
                                    @Override
                                    public void onEditItem() {
                                        Intent data = new Intent();
                                        data.putExtra("title", "Edit made to Fixture 7593874");
                                        setResult(0, data);
                                        finish();
                                    }
                                });
                dialog.show(fragmentManager, null);
            }
        });
    }
}
