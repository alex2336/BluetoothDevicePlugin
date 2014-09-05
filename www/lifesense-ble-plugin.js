var lifesenseBLEPlugin = {
    startScanning: function(successCallback, errorCallback) {
        cordova.exec(
            successCallback, // success callback function
            errorCallback, // error callback function
            'LifesenseBLEPlugin', // mapped to our native Java class called "CalendarPlugin"
            'startScanning', // with this action name
            [{}]
        ); 
     },
     stopScanning: function(successCallback, errorCallback) {
        cordova.exec(
            successCallback, // success callback function
            errorCallback, // error callback function
            'LifesenseBLEPlugin', // mapped to our native Java class called "CalendarPlugin"
            'stopScanning', // with this action name
            [{}]
        ); 
     },
     getCurrentDevices:function(successCallback, errorCallback) {
        cordova.exec(
            successCallback, // success callback function
            errorCallback, // error callback function
            'LifesenseBLEPlugin', // mapped to our native Java class called "CalendarPlugin"
            'getCurrentDevices', // with this action name
            [{}]
        ); 
     }
}
module.exports = lifesenseBLEPlugin;