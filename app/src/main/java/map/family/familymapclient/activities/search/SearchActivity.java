package map.family.familymapclient.activities.search;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import map.family.familymapclient.R;
import map.family.familymapclient.activities.event.EventActivity;
import map.family.familymapclient.activities.person.PersonActivity;
import map.family.familymapclient.memberobjects.Event;
import map.family.familymapclient.memberobjects.Person;
import map.family.familymapclient.model.Model;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import java.util.ArrayList;


/**
 * Created by mradams on 11/19/18.
 */

public class SearchActivity extends AppCompatActivity {

    private SearchView searchBox;
    private RecyclerView recyclerView;
    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchBox = findViewById(R.id.search_box);
        recyclerView = findViewById(R.id.search_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        searchBox.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                updateUI(newText);
                return false;
            }
        });
    }

    void updateUI(String searchItem) {
        searchItem = searchItem.toLowerCase();
        ArrayList<String> foundItems = new ArrayList<>();
        for (Person person : Model.getInstance().getPersons()) {
            if (person.getFirstName().toLowerCase().contains(searchItem) || person.getLastName().toLowerCase().contains(searchItem)) {
                foundItems.add("p" + person.getPersonID());
            }
        }
        for (Event event : Model.getInstance().getEvents()) {
            if (event.getCountry().toLowerCase().contains(searchItem) || event.getCity().toLowerCase().contains(searchItem)) {
                if (!Model.getInstance().isFiltered(event)) {
                    foundItems.add("e" + event.getEventID());
                }
            }
        }
        adapter = new Adapter(this, foundItems.toArray(new String[foundItems.size()]));
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
            View view = inflater.inflate(R.layout.list_person, parent, false);
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

        private TextView descriptiveText;
        String textDescription;
        private Drawable icon;
        private ImageView iconView;
        private Person personRelative;
        private Event personEvent;
        private View expandedItem;

        public Holder(View view) {
            super(view);
            descriptiveText = view.findViewById(R.id.personEventInfo);
            iconView = view.findViewById(R.id.personLocationImage);
            expandedItem = view.findViewById(R.id.expandedPersonItem);
            expandedItem.setOnClickListener(this);
        }

        void bind(String text) {
            if (text.charAt(0) == 'p') {
                personRelative = Model.getInstance().getPersonFromId(text.substring(1));
                textDescription = personRelative.getFirstName() + " " + personRelative.getLastName();
                if (personRelative.getGender().equals("m")) {
                    icon = new IconDrawable(recyclerView.getContext(), FontAwesomeIcons.fa_male).colorRes(R.color.male_icon).sizeDp(40);
                }
                else {
                    icon = new IconDrawable(recyclerView.getContext(), FontAwesomeIcons.fa_female).colorRes(R.color.female_icon).sizeDp(40);
                }
            }
            else if (text.charAt(0) == 'e'){
                personEvent = Model.getInstance().getEventFromEventId(text.substring(1));
                personRelative = Model.getInstance().getPersonFromEvent(personEvent);
                textDescription = personEvent.getEventType() + ": " + personEvent.getCity() + ", " + personEvent.getCountry() + "(" + personEvent.getYear() + ")\n" + personRelative.getFirstName() + " " + personRelative.getLastName();
                icon = new IconDrawable(recyclerView.getContext(), FontAwesomeIcons.fa_map_marker).colorRes(R.color.event_icon).sizeDp(40);
            }
            iconView.setImageDrawable(icon);
            descriptiveText.setText(textDescription);
        }

        @Override
        public void onClick(View view) {
            if (personEvent != null) {
                Model.getInstance().setCurrentEvent(personEvent);
                Model.getInstance().setCurrentPerson(personRelative);
                Intent intent = new Intent(SearchActivity.this, EventActivity.class);
                startActivity(intent);
            }
            else {
                Model.getInstance().setCurrentPerson(personRelative);
                Intent intent = new Intent(SearchActivity.this, PersonActivity.class);
                startActivity(intent);
            }
        }
    }
}
