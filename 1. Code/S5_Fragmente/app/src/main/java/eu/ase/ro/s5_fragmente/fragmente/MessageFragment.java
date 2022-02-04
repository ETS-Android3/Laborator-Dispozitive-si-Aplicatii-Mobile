package eu.ase.ro.s5_fragmente.fragmente;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import eu.ase.ro.s5_fragmente.Constante;
import eu.ase.ro.s5_fragmente.R;
import eu.ase.ro.s5_fragmente.activitati.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MessageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MessageFragment extends Fragment {


    private View view;
    private Button btnSend;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MessageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MessageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MessageFragment newInstance(String param1, String param2) {
        MessageFragment fragment = new MessageFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       view =  inflater.inflate(R.layout.fragment_message, container, false);
       btnSend = view.findViewById(R.id.messageFragment_btn_stringSend);
       clickBtnSend();
       return view;
    }

    private void clickBtnSend(){
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //PAS 1: Stocarea datelor intr-un Bundle
                Bundle bundle = new Bundle();
                String stringSend = "Bun venit, Adriana!";
                bundle.putString(Constante.SEND_MESSAGE_CHAT_STRING, stringSend);

                //PAS 2: Creare fragment catre care ne indreptam
                ChatFragment chatFragment = new ChatFragment();
                chatFragment.setArguments(bundle);

                //PAS 3: La click se deschide si Chat Fragment automat
                //Pentru ca suntem la nivel de fragment, trebuie sa preluam Fragment Managerul de la
                //nivelul actiitatii
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction =  fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_frameLayout_container, chatFragment);
                fragmentTransaction.commit();
            }
        });
    }
}