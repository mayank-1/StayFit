package com.stayfit.app.stayfitBharat;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link activitiesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link activitiesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class activitiesFragment extends Fragment implements SensorEventListener, StepListener  {

    //Test
    private TextView textView;
    private StepDetector simpleStepDetector;
    private SensorManager sensorManager;
    private Sensor accel;
    private static final String TEXT_NUM_STEPS = "";
    private int numSteps;
    private TextView TvSteps,TvWeight,TvHeight;
    private Button BtnStart, BtnStop, BtnNearby;

    /*- 01 Class Variables -------------------------------------------------------------- */
    private View mainView;
    private Cursor listCursor;

    // Action buttons on toolbar
    private String currentName;

    Context context;



    /*- 02 Fragment Variables ----------------------------------------------------------- */
    // Nessesary for making fragment run
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /*- 03 Constructur ------------------------------------------------------------------ */
    // Nessesary for having Fragment as class
    public activitiesFragment() {
        // Required empty public constructor
    }


    /*- 04 Creating Fragment ------------------------------------------------------------- */
    public static activitiesFragment newInstance(String param1, String param2) {
        activitiesFragment fragment = new activitiesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }



    /*- 05 on Activity Created ---------------------------------------------------------- */
    // Run methods when started
    // Set toolbar menu items
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        /* Set title */
        ((FragmentActivity)getActivity()).getSupportActionBar().setTitle("Activities");


    } // onActivityCreated


    /*- 06 On create view ---------------------------------------------------------------- */
    // Sets main View variable to the view, so we can change views in fragment
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_activities, container, false);
        context = mainView.getContext();
        // Get an instance of the SensorManager
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        simpleStepDetector = new StepDetector();
        simpleStepDetector.registerListener(this);


        TvSteps = (TextView) mainView.findViewById(R.id.tv_steps);
        BtnStart = (Button) mainView.findViewById(R.id.btn_start);
        BtnStop = (Button) mainView.findViewById(R.id.btn_stop);



        BtnStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                //showing start message to the user
                Snackbar snackbar = Snackbar.make(arg0, "Step Tracking Started!", Snackbar.LENGTH_LONG);
                snackbar.show();
                numSteps = 0;
                sensorManager.registerListener(activitiesFragment.this, accel, SensorManager.SENSOR_DELAY_FASTEST);
            }
        });


        BtnStop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                //show stop message to the user
                Snackbar snackbar = Snackbar.make(arg0, "Step Tracking Stopped!", Snackbar.LENGTH_LONG);
                snackbar.show();
                sensorManager.unregisterListener(activitiesFragment.this);
            }
        });

        Button nearby_btn = (Button) mainView.findViewById(R.id.button_nearby);
        nearby_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MapsActivity.class);
                startActivity(intent);
            }
        });
        setHasOptionsMenu(true);
        return mainView;
    }



    /*- Fragment  methods -*/


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }



    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            simpleStepDetector.updateAccel(
                    event.timestamp, event.values[0], event.values[1], event.values[2]);
        }
    }

    @Override
    public void step(long timeNs) {
        numSteps++;
        TvSteps.setText(TEXT_NUM_STEPS + numSteps);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
