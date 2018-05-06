package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    ImageView ingredientsIv;

    TextView originLabelTv;
    TextView originTv;

    TextView descriptionLabelTv;
    TextView descriptionTv;

    TextView ingredientsLabelTv;
    TextView ingredientsTv;

    TextView alsoKnownTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        // initialize the variables that will point to the different fields:
        initializeLocators();
        setValues(sandwich);
    }


    private void initializeLocators() {

        ingredientsIv = findViewById(R.id.image_iv);

        originLabelTv = findViewById(R.id.origin_label_tv);
        originTv = findViewById(R.id.origin_tv);

        descriptionLabelTv = findViewById(R.id.description_label_tv);
        descriptionTv = findViewById(R.id.description_tv);

        ingredientsLabelTv = findViewById(R.id.ingredients_label_tv);
        ingredientsTv = findViewById(R.id.ingredients_tv);

        alsoKnownTv = findViewById(R.id.also_known_tv);
    }

    private void setValues(Sandwich sandwich) {
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());

        List<String> alsoKnownAs = sandwich.getAlsoKnownAs();
        String akaNames = TextUtils.join(", ", alsoKnownAs);
        alsoKnownTv.setText(getString(R.string.detail_also_known_as_label)
                .concat(" ")
                .concat(akaNames));
        if (akaNames.isEmpty()) {
            alsoKnownTv.setText("");
        }
        String placeOfOrigin = sandwich.getPlaceOfOrigin();
        originTv.setText(placeOfOrigin);
        if (placeOfOrigin.isEmpty()) {
            originLabelTv.setText("");
            originTv.setText("");
        }

        List<String> ingredients = sandwich.getIngredients();
        ingredientsTv.setText(TextUtils.join(", ", ingredients));
        if (ingredients.isEmpty()) {
            ingredientsLabelTv.setText("");
            ingredientsTv.setText("");
        }

        String description = sandwich.getDescription();
        descriptionTv.setText(description);
        if (description.isEmpty()) {
            descriptionLabelTv.setText("");
            descriptionTv.setText("");
        }
    }
}
