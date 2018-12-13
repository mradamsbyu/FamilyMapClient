package map.family.familymapclient.fragments.map;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashSet;
import java.util.List;

import map.family.familymapclient.R;
import map.family.familymapclient.activities.person.PersonActivity;
import map.family.familymapclient.memberobjects.Event;
import map.family.familymapclient.memberobjects.Person;
import map.family.familymapclient.model.Model;

/**
 * Created by mradams on 11/19/18.
 */

public class EventMapFragment extends Fragment {
    private GoogleMap map;
    private TextView textViewName;
    private TextView textViewDetails;
    private Drawable genderIcon;
    private ImageView genderImageView;
    private MapView mapView;
    private View event_display;
    private static final float DEFAULT_LINE_WIDTH = 10;
    private static final float WIDE_LINE_WIDTH = 25;
    private static final float ANCESTOR_LINE_DECREMENT = 7;
    private static final float SMALLEST_LINE_WIDTH = 3;
    private HashSet<Polyline> relationshipLines = new HashSet<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top_level_map, container, false);
        textViewName = view.findViewById(R.id.topMapName);
        textViewDetails = view.findViewById(R.id.topMapDetails);
        genderImageView = view.findViewById(R.id.topMapGenderImage);
        event_display = view.findViewById(R.id.event_info);
        event_display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PersonActivity.class);
                startActivity(intent);
            }
        });
        mapView = view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
                initMap();
            }
        });
        textViewName.setText(Model.getInstance().getCurrentPerson().getFirstName() + " " + Model.getInstance().getCurrentPerson().getLastName());
        textViewDetails.setText(Model.getInstance().getCurrentEvent().getEventType() + ": " + Model.getInstance().getCurrentEvent().getCity() + ", " +
                Model.getInstance().getCurrentEvent().getCountry() + " (" + Model.getInstance().getCurrentEvent().getYear() + ")");
        if (Model.getInstance().getCurrentPerson().getGender().equals("m")) {
            genderIcon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_male).colorRes(R.color.male_icon).sizeDp(40);
        }
        else {
            genderIcon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_female).colorRes(R.color.female_icon).sizeDp(40);
        }
        genderImageView.setImageDrawable(genderIcon);
        return  view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mapView != null) {
            mapView.onStart();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mapView != null) {
            mapView.onStop();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mapView != null) {
            mapView.onPause();

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mapView != null) {
            mapView.onDestroy();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mapView != null) {
            mapView.onResume();

        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (mapView != null) {
            mapView.onLowMemory();
        }
    }

    private void initMap() {
        initMarkers();
        setMarkerListener();
        LatLng markerLatLong = new LatLng(Model.getInstance().getCurrentEvent().getLatitude(), Model.getInstance().getCurrentEvent().getLongitude());
        CameraUpdate update = CameraUpdateFactory.newLatLng(markerLatLong);
        setRelationshipLines();
        map.setMapType(Model.getInstance().getMapType());
        map.moveCamera(update);
    }

    private void initMarkers() {
        Marker marker;
        for (Event event : Model.getInstance().getEvents()) {
            LatLng eventLatLng = new LatLng(event.getLatitude(), event.getLongitude());
            marker = map.addMarker(new MarkerOptions().position(eventLatLng).icon(BitmapDescriptorFactory
                    .defaultMarker(Model.getInstance().getEventTypeColor(event.getEventType()))));
            Model.getInstance().setEventMarker(event, marker);
        }
    }

    void setMarkerListener() {
        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Event event = Model.getInstance().getEventFromMarker(marker);
                Person person = Model.getInstance().getPersonFromEvent(event);
                Model.getInstance().setCurrentEvent(event);
                Model.getInstance().setCurrentPerson(person);
                setRelationshipLines();
                textViewName.setText(person.getFirstName() + " " + person.getLastName());
                textViewDetails.setText(event.getEventType() + ": " + event.getCity() + ", " +
                        event.getCountry() + " (" + event.getYear() + ")");
                if (person.getGender().equals("m")) {
                    genderIcon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_male).colorRes(R.color.male_icon).sizeDp(40);
                }
                else {
                    genderIcon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_female).colorRes(R.color.female_icon).sizeDp(40);
                }
                genderImageView.setImageDrawable(genderIcon);
                return true;
            }
        });
    }

    private void setRelationshipLines() {
        for (Polyline line : relationshipLines) {
            line.remove();
        }
        if (Model.getInstance().isSpouseLinesEnabled()) {
            setSpouseLine();
        }
        if (Model.getInstance().isFamilyTreeLinesEnabled()) {
            Event currentEvent = Model.getInstance().getCurrentEvent();
            Person currentPerson = Model.getInstance().getCurrentPerson();
            LatLng currentEventLatLng = new LatLng(currentEvent.getLatitude(), currentEvent.getLongitude());
            setFamilyTreeLines(currentPerson, currentEventLatLng, WIDE_LINE_WIDTH);
        }
        if (Model.getInstance().isLifeStoryLinesEnabled()) {
            setLifeStoryLines();
        }
    }

    private void setSpouseLine() {
        Event currentEvent = Model.getInstance().getCurrentEvent();
        Person currentPerson = Model.getInstance().getCurrentPerson();
        LatLng spouseLatLng = null;
        LatLng currentLatLng = new LatLng(currentEvent.getLatitude(), currentEvent.getLongitude());
        if (currentPerson.getSpouse() != null) {
            Person spouse = Model.getInstance().getPersonFromId(currentPerson.getSpouse());
            List<Event> spouseEvents = Model.getInstance().getEventsFromPersonId(spouse.getPersonID());
            for (Event event : spouseEvents) {
                if (!Model.getInstance().isFiltered(event)) {
                    spouseLatLng = new LatLng(event.getLatitude(), event.getLongitude());
                    break;
                }
            }
        }
        if (spouseLatLng != null) {
            drawLine(currentLatLng, spouseLatLng, Model.getInstance().getSpouseLineColor(), DEFAULT_LINE_WIDTH);
        }
    }

    private void setFamilyTreeLines (Person person, LatLng descendantLatLng, float descendantLineWidth) {
        Event fatherEarliestEvent = null;
        Event motherEarliestEvent = null;
        Person father = Model.getInstance().getPersonFromId(person.getFather());
        Person mother = Model.getInstance().getPersonFromId(person.getMother());
        Float nextLineWidth = descendantLineWidth - ANCESTOR_LINE_DECREMENT;
        if (nextLineWidth < SMALLEST_LINE_WIDTH) {
            nextLineWidth = SMALLEST_LINE_WIDTH;
        }
        if (father != null) {
            List<Event> fatherEvents = Model.getInstance().getEventsFromPersonId(father.getPersonID());
            for (Event event : fatherEvents) {
                if (!Model.getInstance().isFiltered(event)) {
                    fatherEarliestEvent = event;
                    break;
                }
            }
            if (fatherEarliestEvent != null) {
                LatLng fatherLatLng = new LatLng(fatherEarliestEvent.getLatitude(), fatherEarliestEvent.getLongitude());
                drawLine(descendantLatLng, fatherLatLng, Model.getInstance().getFamilyTreeLineColor(), descendantLineWidth);
                setFamilyTreeLines(father, fatherLatLng, nextLineWidth);
            }
        }
        if (mother != null) {
            List<Event> motherEvents = Model.getInstance().getEventsFromPersonId(mother.getPersonID());
            for (Event event : motherEvents) {
                if (!Model.getInstance().isFiltered(event)) {
                    motherEarliestEvent = event;
                    break;
                }
            }
            if (motherEarliestEvent != null) {
                LatLng motherLatLng = new LatLng(motherEarliestEvent.getLatitude(), motherEarliestEvent.getLongitude());
                drawLine(descendantLatLng, motherLatLng, Model.getInstance().getFamilyTreeLineColor(), descendantLineWidth);
                setFamilyTreeLines(mother, motherLatLng, nextLineWidth);
            }
        }
    }

    private void setLifeStoryLines() {
        Person currentPerson = Model.getInstance().getCurrentPerson();
        List<Event> events = Model.getInstance().getEventsFromPersonId(currentPerson.getPersonID());
        LatLng lastEvent = null;
        for (Event event : events) {
            if (!Model.getInstance().isFiltered(event)) {
                LatLng currentEvent = new LatLng(event.getLatitude(), event.getLongitude());
                if (lastEvent != null)
                    drawLine(lastEvent, currentEvent, Model.getInstance().getLifeStoryLineColor(), DEFAULT_LINE_WIDTH);
                lastEvent = currentEvent;
            }
        }
    }

    private void drawLine(LatLng point1, LatLng point2, int color, float width) {
        PolylineOptions options =
                new PolylineOptions().add(point1, point2)
                        .color(color).width(width);
        relationshipLines.add(map.addPolyline(options));
    }
}
