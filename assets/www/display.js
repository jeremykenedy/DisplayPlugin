var display = {
    setBrightness: function(brightness, successCallback, errorCallback) {
        cordova.exec(
            successCallback, // success callback function
            errorCallback, // error callback function
            'DisplayPlugin', // mapped to our native Java class called "CalendarPlugin"
            'setBrightness', // with this action name
            [{                  // and this array of custom arguments to create our entry
                "brightness": brightness
            }]
        ); 
    }
}
module.exports = display;