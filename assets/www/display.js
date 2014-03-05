var display = {
    setBrightness: function(brightness, successCallback, errorCallback) {
        cordova.exec(
            successCallback, // success callback function
            errorCallback, // error callback function
            'Display', // mapped to our native Java class called "DisplayPlugin"
            'setBrightness', // with this action name
            [{                  // and this array of custom arguments to create our entry
                "brightness": brightness
            }]
        ); 
    },
	
	setOrientation: function(orientation, successCallback, errorCallback) {
        cordova.exec(
            successCallback, // success callback function
            errorCallback, // error callback function
            'Display', // mapped to our native Java class called "DisplayPlugin"
            'setOrientation', // with this action name
            [{                  // and this array of custom arguments to create our entry
                "orientation": orientation
            }]
        ); 
    },
	
	setColors: function(red, green, blue, successCallback, errorCallback) {
        cordova.exec(
            successCallback, // success callback function
            errorCallback, // error callback function
            'Display', // mapped to our native Java class called "DisplayPlugin"
            'setColors', // with this action name
            [{                  // and this array of custom arguments to create our entry
                "red": red,
				"green": green,
				"blue": blue
            }]
        ); 
    },
	
	setAlpha: function(alpha, successCallback, errorCallback) {
        cordova.exec(
            successCallback, // success callback function
            errorCallback, // error callback function
            'Display', // mapped to our native Java class called "DisplayPlugin"
            'setAlpha', // with this action name
            [{                  // and this array of custom arguments to create our entry
                "alpha": alpha
            }]
        ); 
    },
	
	setDim: function(dim, successCallback, errorCallback) {
        cordova.exec(
            successCallback, // success callback function
            errorCallback, // error callback function
            'Display', // mapped to our native Java class called "DisplayPlugin"
            'setDim', // with this action name
            [{                  // and this array of custom arguments to create our entry
                "dim": dim
            }]
        ); 
    }
}

module.exports = display;