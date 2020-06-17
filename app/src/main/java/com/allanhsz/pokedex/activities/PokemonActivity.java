package com.allanhsz.pokedex.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.allanhsz.pokedex.model.Pokemon;
import com.allanhsz.pokedex.PokemonService;
import com.allanhsz.pokedex.R;
import com.allanhsz.pokedex.Types;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PokemonActivity extends AppCompatActivity {

    private char oper;
    private Pokemon pokemon;
    private ImageView back, preview;
    private Button action;
    private View solidDeco, wave;
    private TextInputLayout imageLayout;
    private TextInputEditText name, number, image;
    private AutoCompleteTextView type1, type2;
    private ArrayList<String> allTypes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon);

        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            oper = 'I';
            pokemon = new Pokemon();
        } else {
            oper = 'E';
            pokemon = extras.getParcelable("pokemon");
        }

        back = findViewById(R.id.Back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        solidDeco = findViewById(R.id.SolidDeco);
        wave = findViewById(R.id.Wave);

        preview = findViewById(R.id.Preview);

        imageLayout = findViewById(R.id.ImageLayout);
        image = findViewById(R.id.Image);
        name = findViewById(R.id.Name);
        number = findViewById(R.id.Number);
        type1 = findViewById(R.id.Type1);
        type2 = findViewById(R.id.Type2);

        action = findViewById(R.id.Action);

        image.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus) {
                    String url = Objects.requireNonNull(image.getText()).toString();
                    if (!url.isEmpty()) {
                        if(URLUtil.isValidUrl(Objects.requireNonNull(image.getText()).toString())) {
                            Picasso.get()
                                    .load(image.getText().toString())
                                    .into(preview, new com.squareup.picasso.Callback() {
                                        @Override
                                        public void onSuccess() {

                                        }

                                        @Override
                                        public void onError(Exception e) {
                                            imageLayout.setError("Url invido");
                                        }
                                    });
                        } else {
                            imageLayout.setError("Url invido");
                        }
                    }
                } else {
                    imageLayout.setErrorEnabled(false);
                }
            }
        });

        allTypes = Types.getTypes(this);

        final ArrayAdapter<String> adapterType1 = new ArrayAdapter<>(this, R.layout.dropdown_menu_popup_item, allTypes);
        type1.setAdapter(adapterType1);
        type1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int p, long ld) {
                pokemon.getTipo()[0] = p;
            }
        });

        allTypes.add(0, getString(R.string.type0));
        ArrayAdapter<String> adapterType2 = new ArrayAdapter<>(this, R.layout.dropdown_menu_popup_item, allTypes);
        type2.setAdapter(adapterType2);
        type2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int p, long ld) {
                    pokemon.getTipo()[1] = p;
            }
        });

        action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog dialog = new ProgressDialog(PokemonActivity.this);
                dialog.setMessage("Carregando...");
                dialog.setCancelable(false);
                dialog.show();

                pokemon.setNome(Objects.requireNonNull(name.getText()).toString());
                pokemon.setNumero(Integer.parseInt(Objects.requireNonNull(number.getText()).toString()));

                if(pokemon.getImagem() != null){
                    pokemon.setImagem(pokemon.getImagem());
                }

                PokemonService.reference.insert(pokemon).enqueue(new Callback<Void>() {
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
