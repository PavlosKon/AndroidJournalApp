package gr.aueb.cf.finalproject01.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import androidx.fragment.app.FragmentTransaction;
import gr.aueb.cf.finalproject01.R;
import gr.aueb.cf.finalproject01.databinding.ActivityMainBinding;
import gr.aueb.cf.finalproject01.fragments.HomeFragment;
import gr.aueb.cf.finalproject01.fragments.PoolFragment;
import gr.aueb.cf.finalproject01.fragments.ProfileFragment;

public class MainActivity extends AppCompatActivity {
    private static final String SELECTED_ITEM_ID = "selected_item_id";
    private int selectedItemId;
    private ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());

        if (savedInstanceState == null) {
            selectedItemId = R.id.homeItem; // Default to search
            replaceFragment(new HomeFragment());
        } else {
            selectedItemId = savedInstanceState.getInt(SELECTED_ITEM_ID, R.id.homeItem);
            Fragment selectedFragment;
            if (selectedItemId == R.id.homeItem) {
                selectedFragment = new HomeFragment();
            } else if (selectedItemId == R.id.poolItem) {
                selectedFragment = new PoolFragment();
            } else {
                selectedFragment = new ProfileFragment();
            }
            replaceFragment(selectedFragment);
        }

        activityMainBinding.bottomNV.setOnItemSelectedListener(item -> {
            selectedItemId = item.getItemId();
            if (selectedItemId == R.id.homeItem) {
                replaceFragment(new HomeFragment());
            } else if (selectedItemId == R.id.poolItem) {
                replaceFragment(new PoolFragment());
            } else if (selectedItemId == R.id.profileItem) {
                replaceFragment(new ProfileFragment());
            }

            return true;
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SELECTED_ITEM_ID, selectedItemId);
    }
}
