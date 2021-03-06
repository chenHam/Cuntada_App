package com.example.chen.cuntada_app.app.View;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.chen.cuntada_app.app.Model.Model;
import com.example.chen.cuntada_app.app.Model.Recipe;
import com.example.chen.cuntada_app.app.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class MyRecipesFragment extends Fragment {

    ListView list;
    MyAdapter myAdapter2 = new MyAdapter();
    RecipesByPublisherIdListViewModel dataModel;
    public String publisherId;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    final String userId = firebaseAuth.getCurrentUser().getUid();


    public static MyRecipesFragment newInstance(String publisherId) {
        MyRecipesFragment fragment = new MyRecipesFragment();
        //fragment.publisherId = publisherId;
        return fragment;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d("Tokyo", "before the userId");
        Log.d("Tokyo", userId);
        dataModel = ViewModelProviders.of(this).get(RecipesByPublisherIdListViewModel.class);
        dataModel.getData(userId).observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipes) {
                myAdapter2.notifyDataSetChanged();
                Log.d("TAG","notifyDataSetChanged" + recipes.size());
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Model.instance.cancellGetAllRecipes();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_recipes_list, container, false);

        list = view.findViewById(R.id.recipesListView);
        list.setAdapter(myAdapter2);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                TextView name = (TextView) view.findViewById(R.id.recipeNameTextView);
                TextView cateogry = (TextView) view.findViewById(R.id.recipeCategoryTextView);
                TextView ingredients = (TextView) view.findViewById(R.id.recipeIngredientsTextView);
                TextView instructions = (TextView) view.findViewById(R.id.recipeInstructionsTextView);
                ImageView avatar = (ImageView) view.findViewById(R.id.recipeImage);
                Bitmap bitmap = ((BitmapDrawable)avatar.getDrawable()).getBitmap();

                Intent intent = new Intent("EDIT_RECIPE");
                intent.putExtra("name", name.getText().toString());
                intent.putExtra("cateogry", cateogry.getText().toString());
                intent.putExtra("ingredients", ingredients.getText().toString());
                intent.putExtra("instructions", instructions.getText().toString());
                intent.putExtra("avatar", bitmap);
                getActivity().sendBroadcast(intent);
            }
        });
        return view;
    }
    @Override
    public void onDetach() {
        super.onDetach();
    }


    class MyAdapter extends BaseAdapter {
        public MyAdapter(){
        }

        @Override
        public int getCount() {
            return dataModel.getData(userId).getValue().size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null){
                view = LayoutInflater.from(getActivity()).inflate(R.layout.recipe_list_item,null);
            }

            final Recipe recipe = dataModel.getData(userId).getValue().get(i);

            TextView recipeNameTextView = view.findViewById(R.id.recipeNameTextView);
            TextView recipeCategoryTextView = view.findViewById(R.id.recipeCategoryTextView);
            TextView recipeIngredientsTextView = view.findViewById(R.id.recipeIngredientsTextView);
            TextView recipeInstructionsTextView = view.findViewById(R.id.recipeInstructionsTextView);
            final ImageView avatarView = view.findViewById(R.id.recipeImage);

            recipeNameTextView.setText(recipe.name);
            recipeCategoryTextView.setText(recipe.category);
            recipeIngredientsTextView.setText(recipe.ingredients);
            recipeInstructionsTextView.setText(recipe.instructions);

            if (recipe.avatar != null){
                Model.instance.getImage(recipe.avatar, new Model.GetImageListener() {
                    @Override
                    public void onDone(Bitmap imageBitmap) {
                        //if (recipe.id.equals(avatarView.getTag()) && imageBitmap != null) {
                        avatarView.setImageBitmap(imageBitmap);
                        //}
                    }
                });
            }
            return view;
        }
    }
}