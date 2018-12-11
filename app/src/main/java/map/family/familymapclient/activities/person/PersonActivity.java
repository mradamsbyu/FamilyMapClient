package map.family.familymapclient.activities.person;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ChildViewHolder;
import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.ParentViewHolder;
import com.bignerdranch.expandablerecyclerview.model.Parent;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import map.family.familymapclient.R;
import map.family.familymapclient.memberobjects.Event;
import map.family.familymapclient.memberobjects.Person;
import map.family.familymapclient.model.Model;

public class PersonActivity extends AppCompatActivity {
    private TextView firstName;
    private TextView lastName;
    private TextView gender;
    private Person person;
    private Event event;

    // model related items (should be in Model class)
    private ArrayList<String> lifeEventsInfo = new ArrayList<>();
    private ArrayList<String> familyInfo = new ArrayList<>();

    // groups of items that can expand/collapse
    List<Group> groups = Arrays.asList(
            new Group("LIFE EVENTS", lifeEventsInfo.toArray(new String[lifeEventsInfo.size()])),
            new Group("FAMILY", familyInfo.toArray(new String[familyInfo.size()]))
    );

    public class Group implements Parent<String> {

        String name;
        String secondLine;
        String[] values;

        Group(String name, String[] values) {
            this.name = name;
            this.values = values;
        }
        @Override
        public List<String> getChildList() {
            return Arrays.asList(values);
        }
        @Override
        public boolean isInitiallyExpanded() {
            return false;
        }

    }

    private RecyclerView recyclerView;
    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        person = Model.getInstance().getCurrentPerson(); //Will this be a problem when you press back from another person view and the current person is different
        firstName = findViewById(R.id.PersonFirstName);
        lastName = findViewById(R.id.PersonLastName);
        gender = findViewById(R.id.PersonGender);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        setPersonData();
        updateUI();
    }

    private void updateUI() {
        firstName.setText(person.getFirstName());
        lastName.setText(person.getLastName());
        if (person.getGender().equals("m")) {
            gender.setText("Male");
        }
        else {
            gender.setText("Female");
        }
        adapter = new Adapter(this, groups);
        recyclerView.setAdapter(adapter);
    }

    private void setPersonData() {
        List<Event> events;
        events = Model.getInstance().getEvents(person.getPersonID());
        for (Event event : events) {
            lifeEventsInfo.add(event.getEventType() + ": " + event.getCity() + ", " + event.getCountry() +
                    "(" + event.getYear() + ")\n" + person.getFirstName() + " " + person.getLastName());
        }
    }

    class Adapter extends ExpandableRecyclerAdapter<Group, String, GroupHolder, Holder> {

        private LayoutInflater inflater;

        public Adapter(Context context, List<Group> groups) {
            super(groups);
            inflater = LayoutInflater.from(context);
        }

        @Override
        public GroupHolder onCreateParentViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = inflater.inflate(R.layout.list_group_person, viewGroup, false);
            return new GroupHolder(view);
        }

        @Override
        public Holder onCreateChildViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = inflater.inflate(R.layout.list_life_events, viewGroup, false);
            return new Holder(view);
        }

        @Override
        public void onBindParentViewHolder(@NonNull GroupHolder holder, int i, Group group) {
            holder.bind(group);
        }

        @Override
        public void onBindChildViewHolder(@NonNull Holder holder, int i, int j, String item) {
            holder.bind(item);
        }

    }

    class GroupHolder extends ParentViewHolder {

        private TextView textView;

        public GroupHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.group_title);
        }

        void bind(Group group) {
            textView.setText(group.name);
        }

    }

    class Holder extends ChildViewHolder implements View.OnClickListener {

        private TextView descriptiveText;
        private Drawable icon;
        private ImageView iconView;

        public Holder(View view) {
            super(view);
            descriptiveText = view.findViewById(R.id.personEventInfo);
            iconView = view.findViewById(R.id.personLocationImage);
        }

        void bind(String text) {
            descriptiveText.setText(text);
            if (person.getGender().equals("m")) {
                icon = new IconDrawable(recyclerView.getContext(), FontAwesomeIcons.fa_male).colorRes(R.color.male_icon).sizeDp(40);
            }
            else {
                icon = new IconDrawable(recyclerView.getContext(), FontAwesomeIcons.fa_female).colorRes(R.color.female_icon).sizeDp(40);
            }
            iconView.setImageDrawable(icon);
        }

        @Override
        public void onClick(View view) {
        }

    }

}
