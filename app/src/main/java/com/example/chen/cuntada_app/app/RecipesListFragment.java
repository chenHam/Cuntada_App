package com.example.chen.cuntada_app.app;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.chen.cuntada_app.app.Model.Model;
import com.example.chen.cuntada_app.app.Model.Recipe;

import java.util.List;


public class RecipesListFragment extends Fragment {
    //private OnFragmentInteractionListener mListener;

    ListView list;
    MyAdapter myAdapter = new MyAdapter();;
    RecipeListViewModel dataModel;

    public static RecipesListFragment newInstance() {
        RecipesListFragment fragment = new RecipesListFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dataModel = ViewModelProviders.of(this).get(RecipeListViewModel.class);
        dataModel.getData().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipes) {
                myAdapter.notifyDataSetChanged();
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
        list.setAdapter(myAdapter);
        /*list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("TAG","item selected:" + i);
            }
        });*/
        return view;
    }



    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }


    class MyAdapter extends BaseAdapter {
        public MyAdapter(){
        }

        @Override
        public int getCount() {
            Log.d("TAG","list size:" + dataModel.getData().getValue().size());

            return dataModel.getData().getValue().size();

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

            Log.d("London", "getView is called!");

            if (view == null){
                view = LayoutInflater.from(getActivity()).inflate(R.layout.recipe_list_item,null);

                /*final CheckBox cb = view.findViewById(R.id.stListItem_check_cb);
                cb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int index = (int) cb.getTag();
                        Student s = dataModel.getData().getValue().get(index);
                        s.checked = !s.checked;
                    }
                });*/
            }

            final Recipe recipe = dataModel.getData().getValue().get(i);

            TextView recipeNameTextView = view.findViewById(R.id.recipeNameTextView);
            TextView recipeCategoryTextView = view.findViewById(R.id.recipeCategoryTextView);
            TextView recipeIngredientsTextView = view.findViewById(R.id.recipeIngredientsTextView);
            TextView recipeInstructionsTextView = view.findViewById(R.id.recipeInstructionsTextView);
            final ImageView avatarView = view.findViewById(R.id.recipeImage);

            //cb.setTag(i);

            recipeNameTextView.setText(recipe.name);
            recipeCategoryTextView.setText(recipe.category);
            recipeIngredientsTextView.setText(recipe.ingredients);
            recipeInstructionsTextView.setText(recipe.instructions);

            /*nameTv.setText(s.name);
            idTv.setText(s.id);
            cb.setChecked(s.checked);*/
            //avatarView.setImageResource(R.drawable.avatar); -- default value!!
            //avatarView.setTag(s.id);*/
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