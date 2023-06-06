package com.project.newsaggregator;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.project.newsaggregator.databinding.ActivityMainBinding;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";
    private Menu optionsMenu;
    private ActionBarDrawerToggle mDrawerToggle;
    private ArrayAdapter<String> arrayAdapter;
    private HashMap<String, ArrayList<Source>> SourcesData = new HashMap<>();
    private HashMap<String, String> id = new HashMap<>();
    private ArrayList<String> sourcesDisplay = new ArrayList<>();
    private ArrayList<String> currentCategories;
    private String currentSource;
    private ArrayList<Article> a = new ArrayList<>();
    private Map<String, Integer> topicIntMap;
    private Adapter adapter;
    private ArrayList<Article> alist = new ArrayList<>();
    private ActivityMainBinding binding;
    private int[] topicColors;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        arrayAdapter = new ArrayAdapter<>(this, R.layout.draweritem, sourcesDisplay);
        binding.drawerlist.setAdapter(arrayAdapter);
        adapter = new Adapter(this, alist);
        topicColors = getResources().getIntArray(R.array.topicColors);
        binding.viewpager.setAdapter(adapter);
        binding.drawerlist.setOnItemClickListener(
                (parent, view, position, id) -> {
                    selectItem(position);
                    binding.drawerlayout.closeDrawer(binding.drawerlist);
                }
        );
        // Create the drawer toggle
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                binding.drawerlayout,         /* DrawerLayout object */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        );
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        // load data
        if (SourcesData.isEmpty()) {
            SourceDownloader.download(this, "");
        }
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void selectItem(int pos) {
        binding.viewpager.setBackground(null);
        currentSource = sourcesDisplay.get(pos);
        alist.clear();
        a=ArticleDownloader.doDownload(this,id.get(currentSource));
        if (a == null) {
            Toast.makeText(this,
                    MessageFormat.format("No countries found for {0}", currentSource),
                    Toast.LENGTH_LONG).show();
            return;
        }
        alist.addAll(a);
        adapter.notifyDataSetChanged();
        binding.viewpager.setCurrentItem(0);

        binding.drawerlayout.closeDrawer(binding.drawerlist);

        setTitle(currentSource);

    }




    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        sourcesDisplay.clear();
        ArrayList<Source>s=SourcesData.get(item.getTitle().toString());
        if(s!=null) {
            for(Source s1:s)
                sourcesDisplay.add(s1.getName());
        }
        setTitle("News Gateway ("+sourcesDisplay.size()+")");
        arrayAdapter.notifyDataSetChanged();
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.optionsMenu = menu;
        return true;
    }



    public void updateData(ArrayList<Source> sourceArrayList, ArrayList<String> categoryArrayList) {

        if (sourceArrayList != null) {
            for (Source s : sourceArrayList) {
                if (!SourcesData.containsKey(s.getCategory())) {
                    SourcesData.put(s.getCategory(), new ArrayList<>());
                }
                ArrayList<Source> list = SourcesData.get(s.getCategory());
                if (list != null)
                    list.add(s);
            }
        }
       SourcesData.put("all", sourceArrayList);
        ArrayList<String> temp = new ArrayList<>(SourcesData.keySet());
        Collections.sort(temp);
int i=0;
        for (String s : temp) {
            SpannableString categoryString = new SpannableString(s);
            categoryString.setSpan(new ForegroundColorSpan(topicColors[i]), 0, categoryString.length(), 0);
            i++;
            optionsMenu.add(categoryString);
        }

        for (Source s : sourceArrayList) {
            sourcesDisplay.add(s.getName());
            id.put(s.getName(), s.getId());
        }

        Log.d(TAG,sourcesDisplay+"");
        arrayAdapter = new ArrayAdapter<>(this, R.layout.draweritem, sourcesDisplay);
        binding.drawerlist.setAdapter(arrayAdapter);

        arrayAdapter.notifyDataSetChanged();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        setTitle("News Gateway ("+sourcesDisplay.size()+")");
        binding.progress.setVisibility(View.GONE);

    }

    public void updateArticleData(ArrayList<Article> articleArrayList) {
        for (int i = 0; i < adapter.getItemCount(); i++)
            adapter.notifyItemChanged(i);
        alist.clear();
        for (int i = 0; i < articleArrayList.size(); i++) {
           alist.add(articleArrayList.get(i));
        }
        adapter.notifyDataSetChanged();
        binding.viewpager.setCurrentItem(0);
        binding.drawerlayout.closeDrawer(binding.drawerlist);

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
}