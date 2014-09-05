var lifesenseBLEPlugin = {
    startScanning: function(successCallback, errorCallback) {
        cordova.exec(
            successCallback, // success callback function
            errorCallback, // error callback function
            'LifesenseBLEPlugin', // mapped to our native Java class called "LifesenseBLEPlugin"
            'startScanning', // with this action name
            [{}]
        ); 
     },
     stopScanning: function(successCallback, errorCallback) {
        cordova.exec(
            successCallback, 
            errorCallback, 
            'LifesenseBLEPlugin',
            'stopScanning',
            [{}]
        ); 
     },
     getCurrentDevices:function(successCallback, errorCallback) {
        cordova.exec(
            successCallback, 
            errorCallback, 
            'LifesenseBLEPlugin', 
            'getCurrentDevices',
            [{}]
        ); 
     },
     pairDevice:function(successCallback, errorCallback, device) {
        cordova.exec(
            successCallback,
            errorCallback, 
            'LifesenseBLEPlugin',
            'pairDevice', 
            [{device:device}]
        ); 
        console.log(JSON.stringify(device));
     },
     getPairedDevice:function(successCallback, errorCallback) {
        cordova.exec(
            successCallback, 
            errorCallback, 
            'LifesenseBLEPlugin', 
            'getPairedDevice',
            [{}]
        ); 
     },
     askForData:function(successCallback, errorCallback) {
        cordova.exec(
            successCallback, 
            errorCallback, 
            'LifesenseBLEPlugin', 
            'askForData',
            [{}]
        ); 
     },
     getData:function(successCallback, errorCallback) {
        cordova.exec(
            successCallback, 
            errorCallback, 
            'LifesenseBLEPlugin', 
            'getData',
            [{}]
        ); 
     },
}
module.exports = lifesenseBLEPlugin;