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
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

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
}
