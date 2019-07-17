package sg.np.edu.twistmanga;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {

    int NoOfTabs;

    public PagerAdapter(FragmentManager fm, int NumberOfTabs)
    {
        super(fm);
        this.NoOfTabs = NumberOfTabs;
    }
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
