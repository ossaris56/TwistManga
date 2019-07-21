package sg.np.edu.twistmanga;

import android.nfc.Tag;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PagerAdapter extends FragmentPagerAdapter {

    //int NoOfTabs;
    public PagerAdapter(FragmentManager fm)
    {
        super(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

    }
    //, int NumberOfTabs
    //this.NoOfTabs = NumberOfTabs;
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new Tab1();
            case 1:
                return new Tab2();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
