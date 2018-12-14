package map.family.familymapclient.activities.filter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import map.family.familymapclient.R;
import map.family.familymapclient.model.Model;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Set;
import java.util.TreeSet;

/**
 * Created by mradams on 11/19/18.
 */

public class FilterActivity extends AppCompatActivity {
    private TextView textView;
    private RecyclerView recyclerView;
    private Adapter adapter;
    private Switch fathersSideSwitch;
    private Switch mothersSideSwitch;
    private Switch maleSwitch;
    private Switch femaleSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);

        recyclerView = findViewById(R.id.filter_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        updateUI(Model.getInstance().getEventTypes().toArray(new String[Model.getInstance().getEventTypes().size()]));
        fathersSideSwitch = findViewById(R.id.fatherFilterSwitch);
        fathersSideSwitch.setChecked(!Model.getInstance().isFatherSideFiltered());
        mothersSideSwitch = findViewById(R.id.motherFilterSwitch);
        mothersSideSwitch.setChecked(!Model.getInstance().isMotherSideFiltered());
        maleSwitch = findViewById(R.id.maleFilterSwitch);
        maleSwitch.setChecked(!Model.getInstance().isMaleFiltered());
        femaleSwitch = findViewById(R.id.femaleFilterSwitch);
        femaleSwitch.setChecked(!Model.getInstance().isFemaleFiltered());
        fathersSideSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Model.getInstance().setFatherSideFiltered(!fathersSideSwitch.isChecked());
            }
        });
        mothersSideSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Model.getInstance().setMotherSideFiltered(!mothersSideSwitch.isChecked());
            }
        });
        maleSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Model.getInstance().setMaleFiltered(!maleSwitch.isChecked());
            }
        });
        femaleSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Model.getInstance().setFemaleFiltered(!femaleSwitch.isChecked());
            }
        });
    }

    void updateUI(String[] items) {

        //selected.clear();
        //updateSelected();

        adapter = new Adapter(this, items);
        recyclerView.setAdapter(adapter);

    }

    //void updateSelected() {
    //    textView.setText(selected.toString());
   //}

    class Adapter extends RecyclerView.Adapter<Holder> {

        private String[] items;
        private LayoutInflater inflater;

        public Adapter(Context context, String[] items) {
            this.items = items;
            inflater = LayoutInflater.from(context);
        }
        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.filter_item, parent, false);
            return new Holder(view);
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {
            String item = items[position];
            holder.bind(item);
        }

        @Override
        public int getItemCount() {
            return items.length;
        }

    }

    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView titleText;
        private TextView descriptionText;
        private Switch filterSwitch;
        String item;

        public Holder(View view) {
            super(view);
            titleText = view.findViewById(R.id.filterTitle);
            descriptionText = view.findViewById(R.id.filterDescription);
            filterSwitch = view.findViewById(R.id.filterSwitch);
            filterSwitch.setOnClickListener(this);
        }

        void bind(String item) {
            this.item = item;
            String title = item + " events";
            String description = "Filter by " + item + " events";
            titleText.setText(title);
            descriptionText.setText(description);
            filterSwitch.setChecked(!Model.getInstance().typeIsFiltered(item));
        }

        @Override
        public void onClick(View view) {
            Model.getInstance().setFilterItem(item, !filterSwitch.isChecked());
        }
    }
}

