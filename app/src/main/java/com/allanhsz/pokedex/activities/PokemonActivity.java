package com.allanhsz.pokedex.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.URLUtil;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.allanhsz.pokedex.Components.LoadingFullScreen;
import com.allanhsz.pokedex.PokemonService;
import com.allanhsz.pokedex.TypeAdapter;
import com.allanhsz.pokedex.model.Pokemon;
import com.allanhsz.pokedex.R;
import com.allanhsz.pokedex.Types;
import com.allanhsz.pokedex.model.Type;
import com.allanhsz.pokedex.utils.HeightProvider;
import com.allanhsz.pokedex.utils.Utils;
import com.allanhsz.pokedex.utils.Validation;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PokemonActivity extends AppCompatActivity {

    private AppBarLayout appbar;
    private char oper;
    private Pokemon pokemon;
    private ImageView preview;
    private TextInputLayout imageLayout, nameLayout, numberLayout, type1Layout, type1Layou2;
    private TextInputEditText image,  name, number;
    private AutoCompleteTextView type1, type2;
    private Button action;
    private ArrayList<Type> allTypes;
    private boolean afterResize = false;
    private int screenHeight;
    private int previewInitialHeight;

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
        name.setFilters(new InputFilter[]{new Utils.EmojiExcludeFilter()});

        numberLayout = findViewById(R.id.NumberLayout);
        number = findViewById(R.id.Number);

        type1 = findViewById(R.id.Type1);
        type1Layout = findViewById(R.id.Type1Layout);
        type2 = findViewById(R.id.Type2);

        action = findViewById(R.id.Action);

        configHeader();

        Bundle extras = getIntent().getExtras();

        if(extras == null) {
            oper = 'I';
            pokemon = new Pokemon();
            action.setText(getString(R.string.adicionar));
        } else {
            oper = 'E';
            pokemon = extras.getParcelable("pokemon");
            action.setText(getString(R.string.salvar));
        }

        allTypes = Types.getTypes(this);

        TypeAdapter typeAdapter1 = new TypeAdapter(this, allTypes);
        type1.setAdapter(typeAdapter1);
        type1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int p, long ld) {
                pokemon.getTypes()[0] = allTypes.get(p).getType();
            }
        });

        final ArrayList<Type> secondTypes = new ArrayList<>(allTypes);
        secondTypes.add(0, new Type(getString(R.string.type0), 0));
        TypeAdapter typeAdapter2 = new TypeAdapter(this, secondTypes);
        type2.setAdapter(typeAdapter2);
        type2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int p, long ld) {
                pokemon.getTypes()[1] = secondTypes.get(p).getType();
            }
        });




        action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validForm()) {
                    final LoadingFullScreen loading = new LoadingFullScreen(PokemonActivity.this);
                    loading.show();

                    pokemon.setName(Objects.requireNonNull(name.getText()).toString());
                    pokemon.setNumber(Integer.parseInt(Objects.requireNonNull(number.getText()).toString()));
                    pokemon.setImage(Objects.requireNonNull(image.getText()).toString());

                    if (oper == 'I') {
                        PokemonService.reference.insert(pokemon).enqueue(new Callback<Void>() {
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
            }
        });
        findViewById(R.id.Back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public void clear(){
        image.setText("");
        name.setText("");
        number.setText("");
        type1.setText("");
        type2.setText("");
    }

    public void setAppBarHeight(int height){
        Utils.setViewHeight(appbar, height);
        Utils.setViewHeight(preview, height);
    }

    public boolean validForm(){
        if (Objects.requireNonNull(name.getText()).toString().trim().isEmpty()) {
            nameLayout.setError("Preenchimento obrigatorio");
            nameLayout.requestFocus();
            return false;
        } else {
            nameLayout.setErrorEnabled(false);
        }

        if (Objects.requireNonNull(number.getText()).toString().trim().isEmpty()) {
            numberLayout.setError("Obrigatorio");
            numberLayout.requestFocus();
            return false;
        } else {
            numberLayout.setErrorEnabled(false);
        }

        if (pokemon.getType(0) > 0) {
            type1Layout.setErrorEnabled(false);
        } else {
            type1Layout.setError("Obrigatorio");
            type1Layout.requestFocus();
            return false;
        }

        return true;
    }

    public void loadImage(){

        if (Validation.isEmpty(image)) {
            imageLayout.setErrorEnabled(false);
            return;
        }

        String url = Objects.requireNonNull(image.getText()).toString().trim();
        if (URLUtil.isValidUrl(Objects.requireNonNull(image.getText()).toString())) {

            Picasso.get()
                    .load(url)
                    .into(preview, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {}

                        @Override
                        public void onError(Exception e) {
                            imageLayout.setError("Url invido");
                        }
                    });

            imageLayout.setErrorEnabled(false);
        } else {
            imageLayout.setError("Url invido");
        }
    }

    public void configHeader(){

        final CoordinatorLayout parent = findViewById(R.id.Container);
        final ViewGroup.LayoutParams previewParams = preview.getLayoutParams();

        appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                previewParams.height = (int) (previewInitialHeight*(1f-verticalOffset*-1/(float)appBarLayout.getTotalScrollRange()));
                preview.setLayoutParams(previewParams);
            }
        });

        // Change appbar height when keyboard open, avoiding keyboard hide input
        new HeightProvider(PokemonActivity.this).init().setHeightListener(new HeightProvider.HeightListener() {
            @Override
            public void onHeightChanged(int height) {
                if ( getCurrentFocus() instanceof  TextInputEditText || getCurrentFocus() instanceof  TextInputLayout){
                    int[] location = new int[2];
                    View view = getCurrentFocus();
                    view.getLocationOnScreen(location);
                    if(screenHeight-height > location[1]+((View)view.getParent()).getHeight()){
                        if(afterResize)
                            afterResize = false;
                        else
                            setAppBarHeight(previewInitialHeight);
                    } else{
                        afterResize = true;
                        setAppBarHeight((int) (previewInitialHeight-(location[1]+view.getHeight()*1.4-(screenHeight-height))-1));
                    }
                }

            }
        });

        parent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                parent.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                screenHeight = parent.getHeight();
                previewInitialHeight = (int) (screenHeight * 0.45f);
                //Make AppBarLayout 45% of screen
                setAppBarHeight(previewInitialHeight);
            }
        });
    }
}
