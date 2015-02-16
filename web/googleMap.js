var map;
var markers = {};
var geocoder;

function initialize() {
    var mapOptions = {
        zoom: 12,
        center: new google.maps.LatLng(59.9342802, 30.335098600000038),
        mapTypeId: google.maps.MapTypeId.ROADMAP
    };

    geocoder = new google.maps.Geocoder();
    map = new google.maps.Map(document.getElementById('map-canvas'),
        mapOptions);
    var input = /** @type {HTMLInputElement} */(
        document.getElementById('pac-input'));


    map.controls[google.maps.ControlPosition.TOP_LEFT].push(input);


    var autocomplete = new google.maps.places.Autocomplete(input);
    autocomplete.bindTo('bounds', map);

    var infowindow = new google.maps.InfoWindow();

    google.maps.event.addListener(map, 'click', function(event){
        var lat = event.latLng.lat();
        var lng = event.latLng.lng();
        addMarker(lat, lng)
    });


    google.maps.event.addListener(autocomplete, 'place_changed', function() {
        var marker = new google.maps.Marker({
            map: map,
            anchorPoint: new google.maps.Point(0, -29)
        });
        infowindow.close();
        marker.setVisible(false);
        var place = autocomplete.getPlace();
        if (!place.geometry) {
            return;
        }

        // If the place has a geometry, then present it on a map.
        if (place.geometry.viewport) {
            map.fitBounds(place.geometry.viewport);
        } else {
            map.setCenter(place.geometry.location);
            map.setZoom(17);
        }

        var lat = place.geometry.location.lat();
        var lng = place.geometry.location.lng();
        addMarker(lat, lng);
        marker.setVisible(true);

        var address = '';
        if (place.address_components) {
            address = [
                (place.address_components[0] && place.address_components[0].short_name || ''),
                (place.address_components[1] && place.address_components[1].short_name || ''),
                (place.address_components[2] && place.address_components[2].short_name || '')
            ].join(' ');
        }
        infowindow.setPosition(place.geometry.location);
        infowindow.setContent('<div><strong>' + place.name + '</strong><br>' + address);
        infowindow.open(map, marker);
    });
}

var getMarkerUniqueId= function(lat, lng) {
    return lat + '_' + lng;
};

var getLatLng = function(lat, lng) {
    return new google.maps.LatLng(lat, lng);
};

var addMarker = function(lat, lng) {
    var markerId = getMarkerUniqueId(lat, lng);
    var marker = new google.maps.Marker({
        position: getLatLng(lat, lng),
        map: map,
        id: 'marker_' + markerId
    });
    markers[markerId] = marker; // cache marker in markers object
    bindMarkerEvents(marker); // bind right click event to marker
    addField("input-table", markerId);
    var hiddenFiled =  document.getElementById('hiddenField');
    hiddenFiled.value += marker.position +"_";
};


function addField(tableName, markerId){
    var newrow = document.createElement('tr');
    newrow.id = "row_"+markerId;
    var cell1 = newrow.insertCell(0);
    var cell2 = newrow.insertCell(1);
    var cell3 = newrow.insertCell(2);
    cell1.innerHTML = "<input type='text' class = 'address' name='data-in' id= 'address_"+markerId+"' disabled>";
    cell2.innerHTML = "<input type='text' class = 'volume' name='data-in' id= 'transpVal_"+markerId+"'>";
    cell3.innerHTML = "<input type='radio' class = 'radio' name='data-in' id= 'radio_'"+markerId+"'>";
    document.getElementById(tableName).appendChild(newrow);
    codeLatLng(markerId); // geocode latitude and longitude into address

}

var bindMarkerEvents = function(marker) {
    google.maps.event.addListener(marker, "rightclick", function (point) {
        var markerId = getMarkerUniqueId(point.latLng.lat(), point.latLng.lng()); // get marker id by using clicked point's coordinate
        var marker = markers[markerId]; // find marker
        removeMarker(marker, markerId); // remove it
    });
};

var removeMarker = function(marker, markerId) {
    marker.setMap(null); // set markers setMap to null to remove it from map
    delete markers[markerId]; // delete marker instance from markers object
    removeField("input-table",markerId);
};

function removeField(tableName, markerId){
    var child = document.getElementById("row_"+markerId);
    var parent = document.getElementById(tableName);
    parent.removeChild(child);
}

function codeLatLng(markerId){
    var latlng = markers[markerId].position
    geocoder.geocode({'latLng': latlng}, function(results, status) {
        if (status == google.maps.GeocoderStatus.OK) {
            if (results[1]) {
                var field = document.getElementById("address_" + markerId);
                field.value = results[0].formatted_address;
            }
        } else {
            alert("Geocoder failed due to: " + status);
        }
    });
}

function removeAll(){
    for (var id in markers){
        var marker = markers[id];
        removeMarker(marker, id);
    }
}

google.maps.event.addDomListener(window, 'load', initialize);
