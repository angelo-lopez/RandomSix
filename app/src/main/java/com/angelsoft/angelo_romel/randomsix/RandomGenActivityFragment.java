package com.angelsoft.angelo_romel.randomsix;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class RandomGenActivityFragment extends Fragment {
    private AnimatorSet mSetRightOut;
    private AnimatorSet mSetLeftIn;
    private boolean mIsBackVisible = false;
    private RandomColor randomColor;
    private SharedPreferences pref;
    private View rootView;
    //first card layout
    private View mCard1FrontLayout;
    private View mCard1BackLayout;
    //second card layout
    private View mCard2FrontLayout;
    private View mCard2BackLayout;
    //third card layout
    private View mCard3FrontLayout;
    private View mCard3BackLayout;
    //fourth card layout
    private View mCard4FrontLayout;
    private View mCard4BackLayout;
    //fifth card layout
    private View mCard5FrontLayout;
    private View mCard5BackLayout;
    //sixth cardlayout
    private View mCard6FrontLayout;
    private View mCard6BackLayout;
    //card flip offset delay/duration
    private int delay1 = 10;
    private int delay2 = 40;
    private int delay3 = 80;
    private int delay4 = 120;
    private int delay5 = 160;
    private int delay6 = 200;

    public RandomGenActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        randomColor = new RandomColor();//Initialize the random color object.
        pref = PreferenceManager.getDefaultSharedPreferences(getActivity());//Initialize the preference object.
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.menu_random_gen, menu);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_random_gen, container, false);
        this.rootView = rootView;

        findCardViews(rootView);
        loadCardAnimations();
        changeCameraDistance();

        //FAB onclick listener.
        FloatingActionButton randomizeFab = (FloatingActionButton)rootView.findViewById(R.id.fabRandomize);
        randomizeFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                * RandomSet params: 1.min value, 2. max value, 3. number of set, 4. no-repeat/repeat
                * min value default = 1 (specified in the integer.xml file)
                * max value default = 50 (specified in the integer.xml file)
                * number of set default = 6 (specified in the integer.xml file)
                * no-repeat default = true
                * Values can be found in the integer.xml resource file in the values subfolder.
                * */
                RandomSet randomNumbers = new RandomSet(
                        pref.getInt("minValue", getResources().getInteger(R.integer.min_value)),
                        pref.getInt("maxValue", getResources().getInteger(R.integer.max_value)),
                        pref.getInt("numberSet", getResources().getInteger(R.integer.number_set)),
                        pref.getBoolean("noRepeat", pref.getBoolean("noRepeat", true)));
                randomNumbers.draw();//Generate random numbers.
                randomFlipCard(rootView, randomNumbers.getNumberSet());
            }
        });

        return rootView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            //Launch the settings/preferences activity.
            Intent intent = new Intent(getActivity(), SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Update the status text every time the fragment is resumed.
    @Override
    public void onResume() {
        super.onResume();
        TextView statusTextView = (TextView)this.rootView.findViewById(R.id.status_textview);
        StringBuilder statusText = new StringBuilder();
        statusText.append("Min: " +
                pref.getInt("minValue", getResources().getInteger(R.integer.min_value)) + ", ");
        statusText.append("Max: " +
                pref.getInt("maxValue", getResources().getInteger(R.integer.max_value)) + ", ");

        if(pref.getBoolean("noRepeat", true)) {
            statusText.append(" No-Repeat");
        }
        else {
            statusText.append(" Repeat-Allowed");
        }
        //Update the status textview in the layout.
        statusTextView.setText(statusText);
    }

    //Increase the camera distance to make the flipping animation appear smooth.
    private void changeCameraDistance() {
        int distance = 8000;
        float scale = getResources().getDisplayMetrics().density * distance;
        //first card
        mCard1FrontLayout.setCameraDistance(scale);
        mCard1BackLayout.setCameraDistance(scale);
        //second card
        mCard2FrontLayout.setCameraDistance(scale);
        mCard2BackLayout.setCameraDistance(scale);
        //third card
        mCard3FrontLayout.setCameraDistance(scale);
        mCard3BackLayout.setCameraDistance(scale);
        //fourth card
        mCard4FrontLayout.setCameraDistance(scale);
        mCard4BackLayout.setCameraDistance(scale);
        //fifth card
        mCard5FrontLayout.setCameraDistance(scale);
        mCard5BackLayout.setCameraDistance(scale);
        //sixth card
        mCard6FrontLayout.setCameraDistance(scale);
        mCard6BackLayout.setCameraDistance(scale);
    }

    //Initialize the AnimatorSet objects to run simultaneously. The offset is defined in the
    //randomFlipCard() method.
    private void loadCardAnimations() {
        mSetRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.animation_out_full);
        mSetLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.animation_in_full);
    }

    /**
     * Initialize the layouts used by the cards. The View parameter is the root view of this fragment.
     * @param view = The root view of this fragment which is in the fragment_random_gen.xml.
     */

    private void findCardViews(View view) {
        //first card
        mCard1BackLayout = view.findViewById(R.id.back1);
        mCard1FrontLayout = view.findViewById(R.id.front1);
        //second card
        mCard2BackLayout = view.findViewById(R.id.back2);
        mCard2FrontLayout = view.findViewById(R.id.front2);
        //third card
        mCard3BackLayout = view.findViewById(R.id.back3);
        mCard3FrontLayout = view.findViewById(R.id.front3);
        //fourth card
        mCard4BackLayout = view.findViewById(R.id.back4);
        mCard4FrontLayout = view.findViewById(R.id.front4);
        //fifth card
        mCard5BackLayout = view.findViewById(R.id.back5);
        mCard5FrontLayout = view.findViewById(R.id.front5);
        //sixth card
        mCard6BackLayout = view.findViewById(R.id.back6);
        mCard6FrontLayout = view.findViewById(R.id.front6);
    }

    /**
     * Rotates the view between the different layouts (fragment_rendom_gen.xml) that shows the front
     //and back of the card. The random color and random number is generated and assigned the the views
     //before the card is flipped.
     * @param view = The root view of this fragment which is in the fragment_random_gen.xml.
     * @param numberSet = The array containing the random numbers returned by the RandomSet class.
     */
    public void randomFlipCard(View view, int[] numberSet) {
        ImageView imgView;
        TextView txtView;
        if (!mIsBackVisible) {
            //first card.
            mSetRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.animation_out_full);
            mSetLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.animation_in_full);
            mSetRightOut.setStartDelay(delay1);
            mSetLeftIn.setStartDelay(delay1);
            imgView = (ImageView)view.findViewById(R.id.back1_image);
            imgView.setColorFilter(Color.parseColor(randomColor.getRandomColor(getActivity())));
            txtView = (TextView)view.findViewById(R.id.back1_text);
            txtView.setText(Integer.toString(numberSet[0]));
            mSetRightOut.setTarget(mCard1FrontLayout);
            mSetLeftIn.setTarget(mCard1BackLayout);
            mSetRightOut.start();
            mSetLeftIn.start();
            //second card.
            mSetRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.animation_out_full);
            mSetLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.animation_in_full);
            mSetRightOut.setStartDelay(delay2);
            mSetLeftIn.setStartDelay(delay2);
            imgView = (ImageView)view.findViewById(R.id.back2_image);
            imgView.setColorFilter(Color.parseColor(randomColor.getRandomColor(getActivity())));
            txtView = (TextView)view.findViewById(R.id.back2_text);
            txtView.setText(Integer.toString(numberSet[1]));
            mSetRightOut.setTarget(mCard2FrontLayout);
            mSetLeftIn.setTarget(mCard2BackLayout);
            mSetRightOut.start();
            mSetLeftIn.start();
            //third card.
            mSetRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.animation_out_full);
            mSetLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.animation_in_full);
            mSetRightOut.setStartDelay(delay3);
            mSetLeftIn.setStartDelay(delay3);
            imgView = (ImageView)view.findViewById(R.id.back3_image);
            imgView.setColorFilter(Color.parseColor(randomColor.getRandomColor(getActivity())));
            txtView = (TextView)view.findViewById(R.id.back3_text);
            txtView.setText(Integer.toString(numberSet[2]));
            mSetRightOut.setTarget(mCard3FrontLayout);
            mSetLeftIn.setTarget(mCard3BackLayout);
            mSetRightOut.start();
            mSetLeftIn.start();
            ////fourth card.
            mSetRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.animation_out_full);
            mSetLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.animation_in_full);
            mSetRightOut.setStartDelay(delay4);
            mSetLeftIn.setStartDelay(delay4);
            imgView = (ImageView)view.findViewById(R.id.back4_image);
            imgView.setColorFilter(Color.parseColor(randomColor.getRandomColor(getActivity())));
            txtView = (TextView)view.findViewById(R.id.back4_text);
            txtView.setText(Integer.toString(numberSet[3]));
            mSetRightOut.setTarget(mCard4FrontLayout);
            mSetLeftIn.setTarget(mCard4BackLayout);
            mSetRightOut.start();
            mSetLeftIn.start();
            //fifth card.
            mSetRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.animation_out_full);
            mSetLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.animation_in_full);
            mSetRightOut.setStartDelay(delay5);
            mSetLeftIn.setStartDelay(delay5);
            imgView = (ImageView)view.findViewById(R.id.back5_image);
            imgView.setColorFilter(Color.parseColor(randomColor.getRandomColor(getActivity())));
            txtView = (TextView)view.findViewById(R.id.back5_text);
            txtView.setText(Integer.toString(numberSet[4]));
            mSetRightOut.setTarget(mCard5FrontLayout);
            mSetLeftIn.setTarget(mCard5BackLayout);
            mSetRightOut.start();
            mSetLeftIn.start();
            //sixth card.
            mSetRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.animation_out_full);
            mSetLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.animation_in_full);
            mSetRightOut.setStartDelay(delay6);
            mSetLeftIn.setStartDelay(delay6);
            imgView = (ImageView)view.findViewById(R.id.back6_image);
            imgView.setColorFilter(Color.parseColor(randomColor.getRandomColor(getActivity())));
            txtView = (TextView)view.findViewById(R.id.back6_text);
            txtView.setText(Integer.toString(numberSet[5]));
            mSetRightOut.setTarget(mCard6FrontLayout);
            mSetLeftIn.setTarget(mCard6BackLayout);
            mSetRightOut.start();
            mSetLeftIn.start();
            mIsBackVisible = true;
        }
        else {
            //first card.
            mSetRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.animation_out_full);
            mSetLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.animation_in_full);
            mSetRightOut.setStartDelay(delay1);
            mSetLeftIn.setStartDelay(delay1);
            imgView = (ImageView)view.findViewById(R.id.front1_image);
            imgView.setColorFilter(Color.parseColor(randomColor.getRandomColor(getActivity())));
            txtView = (TextView)view.findViewById(R.id.front1_text);
            txtView.setText(Integer.toString(numberSet[0]));
            mSetRightOut.setTarget(mCard1BackLayout);
            mSetLeftIn.setTarget(mCard1FrontLayout);
            mSetRightOut.start();
            mSetLeftIn.start();
            //second card.
            mSetRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.animation_out_full);
            mSetLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.animation_in_full);
            mSetRightOut.setStartDelay(delay2);
            mSetLeftIn.setStartDelay(delay2);
            imgView = (ImageView)view.findViewById(R.id.front2_image);
            imgView.setColorFilter(Color.parseColor(randomColor.getRandomColor(getActivity())));
            txtView = (TextView)view.findViewById(R.id.front2_text);
            txtView.setText(Integer.toString(numberSet[1]));
            mSetRightOut.setTarget(mCard2BackLayout);
            mSetLeftIn.setTarget(mCard2FrontLayout);
            mSetRightOut.start();
            mSetLeftIn.start();
            //third card.
            mSetRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.animation_out_full);
            mSetLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.animation_in_full);
            mSetRightOut.setStartDelay(delay3);
            mSetLeftIn.setStartDelay(delay3);
            imgView = (ImageView)view.findViewById(R.id.front3_image);
            imgView.setColorFilter(Color.parseColor(randomColor.getRandomColor(getActivity())));
            txtView = (TextView)view.findViewById(R.id.front3_text);
            txtView.setText(Integer.toString(numberSet[2]));
            mSetRightOut.setTarget(mCard3BackLayout);
            mSetLeftIn.setTarget(mCard3FrontLayout);
            mSetRightOut.start();
            mSetLeftIn.start();
            //fourth card.
            mSetRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.animation_out_full);
            mSetLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.animation_in_full);
            mSetRightOut.setStartDelay(delay4);
            mSetLeftIn.setStartDelay(delay4);
            imgView = (ImageView)view.findViewById(R.id.front4_image);
            imgView.setColorFilter(Color.parseColor(randomColor.getRandomColor(getActivity())));
            txtView = (TextView)view.findViewById(R.id.front4_text);
            txtView.setText(Integer.toString(numberSet[3]));
            mSetRightOut.setTarget(mCard4BackLayout);
            mSetLeftIn.setTarget(mCard4FrontLayout);
            mSetRightOut.start();
            mSetLeftIn.start();
            //fifth card.
            mSetRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.animation_out_full);
            mSetLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.animation_in_full);
            mSetRightOut.setStartDelay(delay5);
            mSetLeftIn.setStartDelay(delay5);
            imgView = (ImageView)view.findViewById(R.id.front5_image);
            imgView.setColorFilter(Color.parseColor(randomColor.getRandomColor(getActivity())));
            txtView = (TextView)view.findViewById(R.id.front5_text);
            txtView.setText(Integer.toString(numberSet[4]));
            mSetRightOut.setTarget(mCard5BackLayout);
            mSetLeftIn.setTarget(mCard5FrontLayout);
            mSetRightOut.start();
            mSetLeftIn.start();
            //sixth card.
            mSetRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.animation_out_full);
            mSetLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.animation_in_full);
            mSetRightOut.setStartDelay(delay6);
            mSetLeftIn.setStartDelay(delay6);
            imgView = (ImageView)view.findViewById(R.id.front6_image);
            imgView.setColorFilter(Color.parseColor(randomColor.getRandomColor(getActivity())));
            txtView = (TextView)view.findViewById(R.id.front6_text);
            txtView.setText(Integer.toString(numberSet[5]));
            mSetRightOut.setTarget(mCard6BackLayout);
            mSetLeftIn.setTarget(mCard6FrontLayout);
            mSetRightOut.start();
            mSetLeftIn.start();
            mIsBackVisible = false;
        }
    }
}
