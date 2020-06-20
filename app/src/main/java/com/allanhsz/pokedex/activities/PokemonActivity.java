package com.allanhsz.pokedex.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
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
import com.allanhsz.pokedex.utils.HeightProvider;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

public class PokemonActivity extends AppCompatActivity implements View.OnFocusChangeListener {

    private char oper;
    private Pokemon pokemon;
    private ImageView back, preview;
    private Button action;
//    private View solidDeco, wave;
    private TextInputLayout imageLayout;
    private TextInputEditText name, number, image;
    private AutoCompleteTextView type1, type2;
    private ArrayList<String> allTypes;

    private boolean afterResize = false;
    private int screenHeight;
    private float previewInitialHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon);

        final CoordinatorLayout parent = findViewById(R.id.Container);
        final ConstraintLayout content = findViewById(R.id.Content);
        final AppBarLayout appbar = findViewById(R.id.AppBar);
        final NestedScrollView scroll = findViewById(R.id.Scroll);

        preview = findViewById(R.id.Preview);

        imageLayout = findViewById(R.id.ImageLayout);
        image = findViewById(R.id.Image);
        image.setOnFocusChangeListener(this);

        name = findViewById(R.id.Name);
        number = findViewById(R.id.Number);
        type1 = findViewById(R.id.Type1);
        type2 = findViewById(R.id.Type2);

        action = findViewById(R.id.Action);

        final ViewGroup.LayoutParams previewParams = preview.getLayoutParams();

        appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                previewParams.height = (int) (previewInitialHeight*(1f-verticalOffset*-1/(float)appBarLayout.getTotalScrollRange()));
                preview.setLayoutParams(previewParams);
            }
        });

        new HeightProvider(PokemonActivity.this).init().setHeightListener(new HeightProvider.HeightListener() {
            @Override
            public void onHeightChanged(int height) {
                if ( getCurrentFocus() instanceof  TextInputEditText || getCurrentFocus() instanceof  TextInputLayout){
                    int[] location = new int[2];
                    View view = getCurrentFocus();
                    view.getLocationOnScreen(location);
                    if(screenHeight-height > location[1]+view.getHeight()){
                        if(afterResize){
                            afterResize = false;
                        } else {
                            Toast.makeText(PokemonActivity.this, " NOT HIDE ", Toast.LENGTH_SHORT).show();
                            setViewHeight(appbar, (int) previewInitialHeight);
                        }
                    } else{
                        afterResize = true;
                        Toast.makeText(PokemonActivity.this, " HIDE ", Toast.LENGTH_SHORT).show();
                        setViewHeight(appbar, (int) previewInitialHeight-(location[1]+view.getHeight()-(screenHeight-height))-1);
                    }
                }

            }
        });

        parent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                parent.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                screenHeight = parent.getHeight();
                previewInitialHeight = screenHeight * 0.45f;

                previewParams.height = (int) (previewInitialHeight);
                preview.setLayoutParams(previewParams);

                //Make AppBarLayout 45% of screen
                setViewHeight(appbar, (int) previewInitialHeight);
            }
        });

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

        allTypes = Types.getTypes(this);

//        final ArrayAdapter<String> adapterType1 = new ArrayAdapter<>(this, R.layout.dropdown_menu_popup_item, allTypes);
//        type1.setAdapter(adapterType1);
//        type1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int p, long ld) {
//                pokemon.getTipo()[0] = p;
//            }
//        });
//
//        allTypes.add(0, getString(R.string.type0));
//        ArrayAdapter<String> adapterType2 = new ArrayAdapter<>(this, R.layout.dropdown_menu_popup_item, allTypes);
//        type2.setAdapter(adapterType2);
//        type2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int p, long ld) {
//                    pokemon.getTipo()[1] = p;
//            }
//        });
//
//        action.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                final ProgressDialog dialog = new ProgressDialog(PokemonActivity.this);
//                dialog.setMessage("Carregando...");
//                dialog.setCancelable(false);
//                dialog.show();
//
//                pokemon.setNome(Objects.requireNonNull(name.getText()).toString());
//                pokemon.setNumero(Integer.parseInt(Objects.requireNonNull(number.getText()).toString()));
//
//                pokemon.setImagem(pokemon.getImagem());
//
//                PokemonService.reference.insert(pokemon).enqueue(new Callback<Void>() {
//                    @Override
//                    public void onResponse(Call<Void> call, Response<Void> response) {
//                        if (dialog.isShowing())
//                            dialog.dismiss();
//                        Toast.makeText(getBaseContext(), "Pokemon inserido com sucesso", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onFailure(Call<Void> call, Throwable t) {
//                        if (dialog.isShowing())
//                            dialog.dismiss();
//                        Toast.makeText(getBaseContext(), "Não foi possível fazer a conexão", Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        });
    }

    public void setViewHeight(View view, int height){
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = height;
        view.setLayoutParams(params);
    }

    public void loadPReview(String url){
        Picasso.get()
                .load(url)
                .into(preview, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {
                        imageLayout.setError("Url invido");
                    }
                });
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        if(view instanceof TextInputEditText){
            if (hasFocus) {
                if(view.getParent() instanceof TextInputLayout)
                    ((TextInputLayout)view.getParent()).setErrorEnabled(false);
            } else {
                if (!isEmpty(view)){
                    if (view.getId() == R.id.Image) {
                        String url = Objects.requireNonNull(image.getText()).toString();
                        if (URLUtil.isValidUrl(Objects.requireNonNull(image.getText()).toString())) {
                            loadPReview(url);
                            ((TextInputLayout)view.getParent()).setErrorEnabled(false);
                        } else {
                            imageLayout.setError("Url invido");
                        }
                    } else {
                        imageLayout.setError("Preenchimento obrigatorio");
                    }
                }
            }
        }
    }

    public boolean isEmpty(View view){
        TextInputEditText et = ((TextInputEditText)view);
        return et.getText() == null || et.getText().toString().trim().isEmpty();
    }
}
