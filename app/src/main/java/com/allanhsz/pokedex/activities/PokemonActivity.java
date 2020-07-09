package com.allanhsz.pokedex.activities;

import android.content.Context;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.allanhsz.pokedex.Components.LoadingFullScreen;
import com.allanhsz.pokedex.PokemonService;
import com.allanhsz.pokedex.R;
import com.allanhsz.pokedex.adapters.TypeAdapter;
import com.allanhsz.pokedex.model.Pokemon;
import com.allanhsz.pokedex.utils.LayoutFocusControl;
import com.allanhsz.pokedex.utils.StringUtils;
import com.allanhsz.pokedex.utils.Types;
import com.allanhsz.pokedex.utils.Utils;
import com.allanhsz.pokedex.utils.Validation;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PokemonActivity extends AppCompatActivity {

    private char oper;
    private Pokemon pokemon;
    private ImageView preview;
    private AppBarLayout appbar;
    private TextInputLayout imageLayout, nameLayout, numberLayout, type1Layout;
    private TextInputEditText image, name, number;
    private AutoCompleteTextView type1, type2;
    private SparseArray<String> allTypes;
    private Button action;

    private int screenHeight;
    private int previewInitialHeight;

    private LayoutFocusControl layoutFocusControl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon);

        appbar = findViewById(R.id.AppBar);
        preview = findViewById(R.id.Preview);

        imageLayout = findViewById(R.id.ImageLayout);
        image = findViewById(R.id.Image);
        image.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                loadImage();
            }
        });

        nameLayout = findViewById(R.id.NameLayout);
        name = findViewById(R.id.Name);
        name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                TextInputEditText text = (TextInputEditText) layoutFocusControl.getLastFlocus();
                if (hasFocus && layoutFocusControl.getOcultArea() > 0 && text.getId() == image.getId())
                    layoutFocusControl.focusIn(name, layoutFocusControl.getOcultArea());
            }
        });

        numberLayout = findViewById(R.id.NumberLayout);
        number = findViewById(R.id.Number);

        type1 = findViewById(R.id.Type1);
        type1Layout = findViewById(R.id.Type1Layout);
        type2 = findViewById(R.id.Type2);
        action = findViewById(R.id.Action);

        configHeader();

        allTypes = Types.getHashType(this);

        TypeAdapter typeAdapter2 = new TypeAdapter(this, allTypes);
        type2.setAdapter(typeAdapter2);
        type2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int p, long ld) {
                pokemon.getTypes()[1] = allTypes.keyAt(p);
            }
        });

        View.OnFocusChangeListener closeOnFocus = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (manager != null) {
                        appbar.setExpanded(true, true);
                        manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                }
            }
        };

        type1.setOnFocusChangeListener(closeOnFocus);
        type2.setOnFocusChangeListener(closeOnFocus);

        final SparseArray<String> types = allTypes.clone();
        types.remove(Types.NENHUM);
        TypeAdapter typeAdapter1 = new TypeAdapter(this, types);
        type1.setAdapter(typeAdapter1);
        type1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int p, long ld) {
                pokemon.getTypes()[0] = types.keyAt(p);
            }
        });

        setOper();

        action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validForm()) {
                    final LoadingFullScreen loading = new LoadingFullScreen(PokemonActivity.this);
                    loading.show();

                    pokemon.setName(Objects.requireNonNull(name.getText()).toString());
                    pokemon.setNumber(Integer.parseInt(Objects.requireNonNull(number.getText()).toString()));
                    pokemon.setImage(Objects.requireNonNull(image.getText()).toString());

                    (oper == 'I' ? PokemonService.reference.insert(pokemon) : PokemonService.reference.update(pokemon.getId(), pokemon)).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (loading.isShowing())
                                loading.dismiss();

                            finish();
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            if (loading.isShowing())
                                loading.dismiss();

                            Toast.makeText(getBaseContext(), "Não foi possível fazer a conexão", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });
        findViewById(R.id.Back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public void clear() {
        image.setText("");
        name.setText("");
        number.setText("");
        type1.setText("");
        type2.setText("");
    }

    public void setOper() {
        Bundle extras = getIntent().getExtras();

        if (extras == null) {
            oper = 'I';
            pokemon = new Pokemon();
            action.setText(getString(R.string.adicionar));
            return;
        }

        oper = 'E';
        pokemon = extras.getParcelable("pokemon");
        action.setText(getString(R.string.salvar));

        image.setText(pokemon.getImage());
        name.setText(pokemon.getName());
        number.setText(String.valueOf(pokemon.getNumber()));
        number.setEnabled(false);
        type1.setText(allTypes.get(pokemon.getType(0)), false);
        type2.setText(allTypes.get(pokemon.getType(1)), false);
        loadImage();
    }

    public boolean validForm() {
        if (Objects.requireNonNull(name.getText()).toString().trim().isEmpty()) {
            nameLayout.setError("Preenchimento obrigatorio");
            nameLayout.requestFocus();
            return false;
        }

        nameLayout.setErrorEnabled(false);

        if (Objects.requireNonNull(number.getText()).toString().trim().isEmpty()) {
            numberLayout.setError("Obrigatorio");
            numberLayout.requestFocus();
            return false;
        }

        numberLayout.setErrorEnabled(false);

        if (pokemon.getType(0) > 0) {
            type1Layout.setErrorEnabled(false);
            return true;

        }

        type1Layout.setError("Obrigatorio");
        type1Layout.requestFocus();
        return false;
    }

    public void loadImage() {
        if (Validation.isEmpty(image)) {
            preview.setImageResource(R.drawable.ic_question);
            imageLayout.setErrorEnabled(false);
            return;
        }

        String url = StringUtils.valueOrEmpty(image.getText());

        if (StringUtils.isNotEmpty(url)) {
            Picasso.get()
                    .load(url)
                    .into(preview, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                        }

                        @Override
                        public void onError(Exception e) {
                            imageLayout.setError("Url invido");
                            preview.setImageResource(R.drawable.ic_question);
                        }
                    });

            imageLayout.setErrorEnabled(false);
            return;
        }

        imageLayout.setError("Url inválido");
        preview.setImageResource(R.drawable.ic_question);
    }

    public void configHeader() {

        final CoordinatorLayout parent = findViewById(R.id.Container);
        final ViewGroup.LayoutParams previewParams = preview.getLayoutParams();

        appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                previewParams.height = (int) (previewInitialHeight * (1f - verticalOffset * -1 / (float) appBarLayout.getTotalScrollRange()));
                preview.setLayoutParams(previewParams);
            }
        });

        parent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                parent.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                screenHeight = parent.getHeight();
                previewInitialHeight = (int) (screenHeight * 0.45f);
                Utils.setViewHeight(appbar, previewInitialHeight);

                layoutFocusControl = new LayoutFocusControl(PokemonActivity.this, appbar, screenHeight, previewInitialHeight);
            }
        });
    }

}
