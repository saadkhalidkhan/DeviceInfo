package device_info.utils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.droidgeeks.deviceinfo.R;

import java.util.List;

public class FragmentUtil {

    AppCompatActivity mActivity;

    public FragmentUtil(AppCompatActivity mActivity) {
        this.mActivity = mActivity;
    }
    //

    public void addChildFragment(final Fragment fragment, final Fragment parentFragment, final int containerId, final boolean isAddToBackStack, final boolean shouldAnimate) {
        pushFragment(fragment, parentFragment, containerId, isAddToBackStack, true, shouldAnimate, false);
    }

    public void replaceChildFragment(final Fragment fragment, final Fragment parentFragment, final int containerId, final boolean isAddToBackStack, final boolean shouldAnimate) {
        pushFragment(fragment, parentFragment, containerId, isAddToBackStack, false, shouldAnimate, false);
    }

    public void addChildFragmentIgnorIfCurrent(final Fragment fragment, final Fragment parentFragment, final int containerId, final boolean isAddToBackStack, final boolean shouldAnimate) {
        pushFragment(fragment, parentFragment, containerId, isAddToBackStack, true, shouldAnimate, true);
    }

    public void replaceChildFragmentIgnorIfCurrent(final Fragment fragment, final Fragment parentFragment, final int containerId, final boolean isAddToBackStack, final boolean shouldAnimate) {
        pushFragment(fragment, parentFragment, containerId, isAddToBackStack, false, shouldAnimate, true);
    }


    private void pushFragment(final Fragment fragment, final Fragment parentFragment, final int containerId, boolean isAddToBackStack, boolean isJustAdd, final boolean shouldAnimate, final boolean ignorIfCurrent) {
        if (fragment == null)
            return;
        // Add the fragment to the 'fragment_container' FrameLayout
        final FragmentManager fragmentManager;// = getSupportFragmentManager();

        if (parentFragment != null) {
            fragmentManager = parentFragment.getChildFragmentManager();
        } else {
            fragmentManager = mActivity.getSupportFragmentManager();
        }


        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (shouldAnimate) {
//            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out);
        } else {
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        }

        if (isAddToBackStack) {
            fragmentTransaction.addToBackStack(fragment.getClass().getCanonicalName());
        } else {

        }

        if (isJustAdd) {
            fragmentTransaction.add(containerId, fragment, fragment.getClass().getCanonicalName());
        } else {
            fragmentTransaction.replace(containerId, fragment, fragment.getClass().getCanonicalName());
        }


        try {
//            fragmentTransaction.commit();
            fragmentTransaction.commitAllowingStateLoss();

            if (!isAddToBackStack) {
//                fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

            }

            Methods.hideKeyboard(mActivity);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void clearBackStackFragmets() {

       /* if (1==1)
            return;*/
        try {
            // in my case I get the support fragment manager, it should work with the native one too
            FragmentManager fragmentManager = mActivity.getSupportFragmentManager();
            // this will clear the back stack and displays no animation on the screen
            fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            // fragmentManager.popBackStackImmediate(SplashFragment.class.getCanonicalName(),FragmentManager.POP_BACK_STACK_INCLUSIVE);
            List<Fragment> fragmentList = fragmentManager.getFragments();
            if (fragmentList != null && !fragmentList.isEmpty()) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                for (Fragment fragment : fragmentList) {
                    if (fragment != null) {
                        fragmentTransaction.remove(fragment);
                    }
                }
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                fragmentTransaction.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void clearBackStackFragmets(FragmentManager fragmentManager) {
        // in my case I get the support fragment manager, it should work with the native one too
//        FragmentManager fragmentManager = getSupportFragmentManager();
        // this will clear the back stack and displays no animation on the screen
        fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        // fragmentManager.popBackStackImmediate(SplashFragment.class.getCanonicalName(),FragmentManager.POP_BACK_STACK_INCLUSIVE);

        List<Fragment> fragmentList = fragmentManager.getFragments();
        if (fragmentList != null && !fragmentList.isEmpty()) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            for (Fragment fragment : fragmentList) {
                if (fragment != null) {
                    fragmentTransaction.remove(fragment);
                }
            }
            fragmentTransaction.commit();
        }

//        Methods.hideKeyboard();


    }

    public void clearBackStackFragmets(final String tag) {
        // in my case I get the support fragment manager, it should work with the native one too
        FragmentManager fragmentManager = mActivity.getSupportFragmentManager();
        // this will clear the back stack and displays no animation on the screen
        fragmentManager.popBackStackImmediate(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        // fragmentManager.popBackStackImmediate(SplashFragment.class.getCanonicalName(),FragmentManager.POP_BACK_STACK_INCLUSIVE);

//        Methods.hideKeyboard();
    }

}
