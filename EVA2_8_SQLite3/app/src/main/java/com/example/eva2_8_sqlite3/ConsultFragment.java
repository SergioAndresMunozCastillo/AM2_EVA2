package com.example.eva2_8_sqlite3;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ConsultFragment extends Fragment {


    public ConsultFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_consult, container, false);
        Bundle b2 = getArguments();
        int cont = b2.getInt("contador");

        final String[] datosQuery = new String[cont];
        for (int i = 0; i < cont; i ++){
            datosQuery[i] = b2.getStringArray("querydatos")[i];
        }
        final ListView listaQuery = (ListView) v.findViewById(R.id.lstVwConsulta);

        ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, datosQuery);
        listaQuery.setAdapter(listAdapter);
        listaQuery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        return v;
    }


}