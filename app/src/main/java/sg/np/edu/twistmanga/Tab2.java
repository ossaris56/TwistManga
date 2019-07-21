package sg.np.edu.twistmanga;

import android.animation.TypeConverter;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpResponse;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Tab2.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Tab2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tab2 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String URL_DATA = "https://www.mangaeden.com/api/manga/";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View view;
    RecyclerView rv;
    ArrayList<ChapterInfo> chapterInfos;
    ChapterAdapter adapter;
    private OnFragmentInteractionListener mListener;

    public Tab2() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tab2.
     */
    // TODO: Rename and change types and number of parameters
    public static Tab2 newInstance(String param1, String param2) {
        Tab2 fragment = new Tab2();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
        loadUrlJson();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_tab2, container, false);
        return view;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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

    private void loadUrlJson() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        Manga manga = getActivity().getIntent().getExtras().getParcelable("Manga");
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_DATA + manga.getId() , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray array = jsonObject.getJSONArray("chapters");
                    chapterInfos = new ArrayList<ChapterInfo>();
                    for (int i=0; i < array.length(); i++) {
                            JSONArray innerchap = array.getJSONArray(i);
                            List<String> list = new ArrayList<String>();
                            for(int m =0;m< innerchap.length();m++){
                            list.add(innerchap.get(m).toString());}
                            double num = Double.parseDouble((list.get(0)));
                            int number = (int)num;
                            //int number = Integer.parseInt(list.get(0));
                            ChapterInfo chapter = new ChapterInfo(number,list.get(1),list.get(2),list.get(3));
                            chapterInfos.add(chapter);
//                        for (int y = 0; y < array.length(); y++) {
//                            JSONArray innerchap = array.getJSONArray(y);
//                            List<String> list = new ArrayList<String>();
//                            for(int m =0;m< innerchap.length();m++){
//                            list.add(innerchap.get(m).toString());}
//                            double num = Double.parseDouble((list.get(0)));
//                            int number = (int)num;
//                            //int number = Integer.parseInt(list.get(0));
//                            ChapterInfo chapter = new ChapterInfo(number,list.get(1),list.get(2),list.get(3));
//                            chapterInfos.add(chapter);
//                        }
                    }
                    rv = view.findViewById(R.id.chaptersrv);
                    ArrayList<String> chapternumber = new ArrayList<String>();
                    for(int i = 0; i<chapterInfos.size();i++){
                        chapternumber.add(""+chapterInfos.get(i).getChapterNumber());
                    }
                    adapter = new ChapterAdapter(chapternumber, getContext());
                    LinearLayoutManager layout = new LinearLayoutManager(getActivity().getApplicationContext());
                    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rv.getContext(),
                            layout.getOrientation());
                    rv.addItemDecoration(dividerItemDecoration);

                    rv.setLayoutManager(layout);
                    rv.setAdapter(adapter);
                }

                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error ) {
                Toast.makeText(getActivity().getApplicationContext(), "Error" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);
    }

}
