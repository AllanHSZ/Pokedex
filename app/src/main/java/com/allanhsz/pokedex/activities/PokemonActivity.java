package com.allanhsz.pokedex.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.webkit.URLUtil;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.allanhsz.pokedex.R;
import com.allanhsz.pokedex.Types;
import com.allanhsz.pokedex.model.Pokemon;
import com.allanhsz.pokedex.service.PokemonService;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PokemonActivity extends AppCompatActivity {

    private Pokemon pokemon;
    private ImageView preview;
    private View solidDeco, wave;
    private TextInputLayout imageLayout;
    private TextInputEditText name, number, image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon);

        final CoordinatorLayout parent = findViewById(R.id.Container);
        final ConstraintLayout content = findViewById(R.id.Content);

        parent.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                parent.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                AppBarLayout appbar = findViewById(R.id.AppBar);

                ViewGroup.LayoutParams toolbarParams = appbar.getLayoutParams();
                toolbarParams.height = (int) (parent.getHeight() * 0.45);
                appbar.setLayoutParams(toolbarParams);

                content.setMinHeight((int) (parent.getHeight() * 0.55));

            }
        });

        Bundle extras = getIntent().getExtras();
        char oper;
        if (extras == null) {
            oper = 'I';
            pokemon = new Pokemon();
        } else {
            oper = 'E';
            pokemon = extras.getParcelable("pokemon");
        }

        ImageView back = findViewById(R.id.Back);
        back.setOnClickListener(view -> onBackPressed());

        preview = findViewById(R.id.Preview);

        imageLayout = findViewById(R.id.ImageLayout);
        image = findViewById(R.id.Image);
        name = findViewById(R.id.Name);
        number = findViewById(R.id.Number);
        AutoCompleteTextView type1 = findViewById(R.id.Type1);
        AutoCompleteTextView type2 = findViewById(R.id.Type2);

        Button action = findViewById(R.id.Action);

        image.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) {
                imageLayout.setErrorEnabled(false);
                return;
            }

            Editable text = image.getText();
            if (text == null || !URLUtil.isValidUrl(text.toString())) {
                imageLayout.setError("Url inválida");
                return;
            }

            Picasso.get()
                    .load(text.toString())
                    .into(preview, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError(Exception e) {
                            imageLayout.setError("Url inválida");
                        }
                    });
        });

        List<String> allTypes = Types.getTypeNames(this);

        final ArrayAdapter<String> adapterType1 = new ArrayAdapter<>(this, R.layout.dropdown_menu_popup_item, allTypes);
        type1.setAdapter(adapterType1);
        type1.setOnItemClickListener((adapterView, view, p, ld) -> pokemon.getTipo()[0] = p);

        allTypes.add(0, getString(R.string.type0));
        ArrayAdapter<String> adapterType2 = new ArrayAdapter<>(this, R.layout.dropdown_menu_popup_item, allTypes);
        type2.setAdapter(adapterType2);
        type2.setOnItemClickListener((adapterView, view, p, ld) -> pokemon.getTipo()[1] = p);

        action.setOnClickListener(view -> {
            final ProgressDialog dialog = new ProgressDialog(PokemonActivity.this);
            dialog.setMessage("Carregando...");
            dialog.setCancelable(false);
            dialog.show();

            pokemon.setNome(Objects.requireNonNull(name.getText()).toString());
            pokemon.setNumero(Integer.parseInt(Objects.requireNonNull(number.getText()).toString()));

            if (pokemon.getImagem() != null) {
                pokemon.setImagem(pokemon.getImagem());
            }

            PokemonService.reference.insert(pokemon).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (dialog.isShowing()) dialog.dismiss();

                    Toast.makeText(getBaseContext(), "Pokemon inserido com sucesso", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    if (dialog.isShowing()) dialog.dismiss();

                    Toast.makeText(getBaseContext(), "Não foi possível fazer a conexão", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}
