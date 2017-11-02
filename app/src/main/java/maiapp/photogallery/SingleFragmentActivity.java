package maiapp.photogallery;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;


public abstract class SingleFragmentActivity extends AppCompatActivity {

protected abstract Fragment createFragment();

    @LayoutRes
    protected int getLayoutResId(){ //returns the ID of the layout that the activity will inflate.
        return R.layout.activity_fragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //called on super class which is FragmentActivity
        //setContentView(R.layout.activity_fragment);
        setContentView(getLayoutResId());

        FragmentManager fragmentManager = getSupportFragmentManager(); //using support library and the AppCompatActivity class. Also gets fragment manager

        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);

        if(fragment == null){
            fragment = createFragment();
            fragmentManager.beginTransaction().add(R.id.fragment_container, fragment).commit(); //creates and returns an instance of FragmentTransaction
        }

    }

}
