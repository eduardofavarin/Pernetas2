package favarin.com.br.pernetas2.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import favarin.com.br.pernetas2.R;
import favarin.com.br.pernetas2.activity.ActivityFutebol;

public class FragmentFinanceiro extends android.support.v4.app.Fragment implements View.OnClickListener{

    private CoordinatorLayout layout;
    private FloatingActionButton fabRelatorio, fabAdicionarDinheiro, fabConsultarDinheiro;
    private FloatingActionMenu fabMenu;
    private Spinner spnJogos;
    public FragmentFinanceiro() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        layout = (CoordinatorLayout) inflater.inflate(R.layout.fragment_financeiro, container, false);

        fabRelatorio = (FloatingActionButton) layout.findViewById(R.id.fabRelatorio);
        fabAdicionarDinheiro = (FloatingActionButton) layout.findViewById(R.id.fabAdicionarDinheiro);
        fabConsultarDinheiro = (FloatingActionButton) layout.findViewById(R.id.fabConsultarDinheiro);
        fabMenu = (FloatingActionMenu) layout.findViewById(R.id.fabMenuF);
        spnJogos = (Spinner) layout.findViewById(R.id.spnSelecionarJogo);

        fabRelatorio.setOnClickListener((View.OnClickListener) this);
        fabAdicionarDinheiro.setOnClickListener((View.OnClickListener) this);
        fabConsultarDinheiro.setOnClickListener((View.OnClickListener) this);
        fabMenu.setClosedOnTouchOutside(true);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getContext(), R.array.spnJogos, R.layout.spinner_jogos_financeiro);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnJogos.setAdapter(adapter);

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
