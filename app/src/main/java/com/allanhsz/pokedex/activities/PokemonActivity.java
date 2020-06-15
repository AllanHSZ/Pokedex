package com.allanhsz.pokedex.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.allanhsz.pokedex.model.Pokemon;
import com.allanhsz.pokedex.PokemonService;
import com.allanhsz.pokedex.R;
import com.allanhsz.pokedex.utils.Utils;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PokemonActivity extends AppCompatActivity {

    private char oper;
    private Pokemon pokemon;
    private ImageView back;
    private Button action;
    private View solidDeco, wave;
    private TextInputEditText name, number;
    private AutoCompleteTextView type1, type2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon);
        
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getColor(R.color.colorPrimary));

        back = findViewById(R.id.Back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        solidDeco = findViewById(R.id.SolidDeco);
        wave = findViewById(R.id.Wave);

        name = findViewById(R.id.Name);
        number = findViewById(R.id.Number);
        type1 = findViewById(R.id.Type1);
        type2 = findViewById(R.id.Type2);

        action = findViewById(R.id.Action);

        ArrayAdapter<String> adapterType1 = new ArrayAdapter<>(this, R.layout.dropdown_menu_popup_item, Utils.getTypes(this));
        type1.setAdapter(adapterType1);

        ArrayAdapter<String> adapterType2 = new ArrayAdapter<>(this, R.layout.dropdown_menu_popup_item, Utils.getSecondTypes(this));
        type2.setAdapter(adapterType2);

        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            oper = 'A';
            //setColor(getColor(R.color.nenhum), getColor(R.color.black));
        } else {
            oper = 'E';
            pokemon = extras.getParcelable("pokemon");

            //final int resourceId = getResources().getIdentifier(pokemon.getType().get(0).toLowerCase(), "color", getPackageName());
            //setColor(getColor(resourceId), getColor(R.color.white));
        }


        action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog dialog = new ProgressDialog(PokemonActivity.this);
                dialog.setMessage("Carregando...");
                dialog.setCancelable(false);
                dialog.show();

                Pokemon newPokemon = new Pokemon();
                newPokemon.setNome(Objects.requireNonNull(name.getText()).toString());
                newPokemon.setNumero(Integer.parseInt(Objects.requireNonNull(number.getText()).toString()));

                ArrayList<String> types = new ArrayList<>();
                types.add(type1.getText().toString());
                types.add(type2.getText().toString());
                newPokemon.setType(types);

                PokemonService service = PokemonService.retrofit.create(PokemonService.class);
                final Call<Void> call = service.insertPokemon(newPokemon);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (dialog.isShowing())
                            dialog.dismiss();
                        Toast.makeText(getBaseContext(), "Pokemon inserido com sucesso", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        if (dialog.isShowing())
                            dialog.dismiss();
                        Toast.makeText(getBaseContext(), "Não foi possível fazer a conexão", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
