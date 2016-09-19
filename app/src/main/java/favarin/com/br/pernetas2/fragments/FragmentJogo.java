package favarin.com.br.pernetas2.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import favarin.com.br.pernetas2.R;
import favarin.com.br.pernetas2.activity.ActivityFutebol;

public class FragmentJogo extends android.support.v4.app.Fragment implements View.OnClickListener {
    private android.support.design.widget.CoordinatorLayout layout;
    private FloatingActionButton fabFutebol, fabFutsal, fabSociety, fabPersonalizado;
    private FloatingActionMenu fabMenu;

    public FragmentJogo() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        layout = (android.support.design.widget.CoordinatorLayout) inflater.inflate(R.layout.fragment_jogo, container, false);

        fabFutebol = (FloatingActionButton) layout.findViewById(R.id.fabFutebol);
        fabFutsal = (FloatingActionButton) layout.findViewById(R.id.fabFutsal);
        fabSociety = (FloatingActionButton) layout.findViewById(R.id.fabSociety);
        fabPersonalizado = (FloatingActionButton) layout.findViewById(R.id.fabPersonalizado);
        fabMenu = (FloatingActionMenu) layout.findViewById(R.id.fabMenu);

        fabFutebol.setOnClickListener((View.OnClickListener) this);
        fabFutsal.setOnClickListener((View.OnClickListener) this);
        fabSociety.setOnClickListener((View.OnClickListener) this);
        fabPersonalizado.setOnClickListener((View.OnClickListener) this);
        fabMenu.setClosedOnTouchOutside(true);

        return layout;
    }

    @Override
    public void onClick(View v) {
        fabMenu.close(true);
        switch (v.getId()) {
            case R.id.fabFutebol:
                Intent it1 = new Intent(getActivity(), ActivityFutebol.class);
                startActivity(it1);
                break;
            case R.id.fabFutsal:
                Intent it2 = new Intent(getActivity(), ActivityFutebol.class);
                startActivity(it2);
                break;
            case R.id.fabSociety:
                Intent it3 = new Intent(getActivity(), ActivityFutebol.class);
                startActivity(it3);
                break;
            case R.id.fabPersonalizado:
                Intent it4 = new Intent(getActivity(), ActivityFutebol.class);
                startActivity(it4);
                break;
        }
    }
}
